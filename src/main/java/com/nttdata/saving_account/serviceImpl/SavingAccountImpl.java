package com.nttdata.saving_account.serviceImpl;

import com.nttdata.saving_account.exception.ModelNotFoundException;
import com.nttdata.saving_account.models.Customer;
import com.nttdata.saving_account.models.SavingAccount;
import com.nttdata.saving_account.models.TypeCustomer;
import com.nttdata.saving_account.repository.SavingAccountRepository;
import com.nttdata.saving_account.service.SavingAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

@Component
@Slf4j
public class SavingAccountImpl implements SavingAccountService {
    private final WebClient webClientCustomer= WebClient.create("http://localhost:8080/customer");
    @Autowired
    private SavingAccountRepository repository;


    @Override
    public Flux<SavingAccount> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<SavingAccount> findByCustomerId(String dni) {
        return null;
    }

    @Override
    public Mono<SavingAccount> findById(String id){
        return repository.findById(id);
    }
    @Override
    public Mono<SavingAccount> save(SavingAccount account) {
        account.setRegistration_date(LocalDateTime.now());
        log.info("Saving_account: implements save() method : {}", account.toString());
        Mono<Customer> customer = findByDocument(account.getCustomer().getDocument());
        Customer client = new Customer();
        try {
            client.setDocument(customer.block().getDocument());
            client.setName(customer.block().getName());
            client.setLastName(customer.block().getLastName());
            client.setDocument(customer.block().getDocument());
            client.setTypeCustomer(customer.block().getTypeCustomer());
            client.setId(customer.block().getId());
            account.setCustomer(client);
        } catch (NullPointerException e) {
            throw new RuntimeException("Customer does not exist");
        }

        Mono<SavingAccount> obj = repository.findByCustomerDocument(account.getCustomer().getDocument());
        try {
            obj.map(o -> {
                if (client.getTypeCustomer() == TypeCustomer.PERSONAL) {
                    o.setAccountNumber(null);
                    o.setCustomer(null);
                    throw new RuntimeException("The customer already has an account. Customer has a PERSONAL account");
                } else if (client.getTypeCustomer() == TypeCustomer.EMPRESARIAL) {
                    throw new RuntimeException("Cuenta empresarial");
                }
                this.repository.save(account);
                return null;
            }).subscribe();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        //.switchIfEmpty(obj=repository.save(account))
        //obj=repository.save(account);
        return obj;
    }
    @Override
    @ExceptionHandler(IOException.class)
    public Mono<SavingAccount> update(SavingAccount account){
        log.info("Saving_account: implements update() method : {}",account.toString());
        Mono<SavingAccount> obj=repository.findByAccountNumber(account.getAccountNumber());
        try {
            obj
                    .flatMap(c -> {
                        return repository.save(account);
                    })
                    .switchIfEmpty(Mono.error(() ->
                            new RuntimeException("Account number incorrect")))
                    .subscribe();
        }catch (Exception e)
        {
            log.info("catch: "+e.getLocalizedMessage());
            throw new RuntimeException("The customer does not exist");
        }
        return obj;
    }
    @Override
    public Mono<Boolean> delete(String id) {
        return  repository.findById(id)
                .flatMap(obj -> repository.delete(obj)
                                .then(Mono.just(Boolean.TRUE))
                )
                .defaultIfEmpty(Boolean.FALSE);
    }

    @Override
    public Mono<Customer> findByDocument(String document) {
        log.info("Saving_account: implements findByDocument() method : {}",document);
        return webClientCustomer.get().uri("/findDcoument/"+document)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Customer.class);
    }

    @Override
    public Mono<SavingAccount> findByAccountNumber(String accountNumber) {
        return repository.findByAccountNumber(accountNumber);
    }
}
