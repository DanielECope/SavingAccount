package com.nttdata.saving_account.serviceImpl;

import com.nttdata.saving_account.exception.ModelNotFoundException;
import com.nttdata.saving_account.models.Customer;
import com.nttdata.saving_account.models.SavingAccount;
import com.nttdata.saving_account.repository.SavingAccountRepository;
import com.nttdata.saving_account.service.SavingAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@Slf4j
public class SavingAccountImpl implements SavingAccountService {
    @Autowired
    private SavingAccountRepository repository;

    @Override
    public Flux<SavingAccount> findAll(){
        return repository.findAll();
    }

    @Override
    public Mono<SavingAccount> findByCustomerId(String dni) {
        return null;
    }

    @Override
    public Mono<SavingAccount> findByCustomerId(Customer obj) {
        return repository.findByCustomerDni(obj.getDni());
    }

    @Override
    public Mono<SavingAccount> findById(String id){
        return repository.findById(id);
    }
    @Override
    public Mono<SavingAccount> save(SavingAccount account){
        account.setRegistration_date(LocalDateTime.now());
        log.info("Saving_account: implements save() method : {}",account.toString());
        Customer customer;
        Mono<SavingAccount> obj=repository.findByCustomerDni(account.getCustomer().getDni());
        obj
                .map(c -> {
                    log.info("Datos: " + c.getAccountNumber());
                    Mono.just(Mono.error(new ModelNotFoundException("the account number already existed")));
                    if (! (account.getCustomer().getDni().equals(c.getCustomer().getDni())) ) {
                        account.setCustomer(c.getCustomer());
                    }
                    return c;
                })
                .switchIfEmpty(repository.save(account))
                .subscribe(c -> {
                    if (c.getAccountNumber().equals(account.getAccountNumber())){
                        log.info("son iguales");
                        throw new ModelNotFoundException("the account number already existed");
                        //Mono.just(Mono.error(new ModelNotFoundException("the account number is already registered")));
                    }
                });


        return obj;
    }
    @Override
    @ExceptionHandler(IOException.class)
    public Mono<SavingAccount> update(SavingAccount account){
        log.info("Saving_account: implements update() method : {}",account.toString());
        Mono<SavingAccount> obj=repository.findById(account.getAccountNumber());
        try {

            obj
                    .flatMap(c -> {
                        return repository.save(account);
                    })
                    .switchIfEmpty(Mono.error(() ->
                            new RuntimeException("Could not resolve view with name '" + "'")))
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
}
