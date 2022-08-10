package com.nttdata.saving_account.service;

import com.nttdata.saving_account.models.Customer;
import com.nttdata.saving_account.models.SavingAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SavingAccountService {
    public Flux<SavingAccount> findAll();

    Mono<SavingAccount> findByCustomerId(String dni);

    //public Mono<SavingAccount> findByCustomerId(String dni);
    public Mono<SavingAccount> findById(String id) throws Exception;
    public Mono<SavingAccount> save(SavingAccount account) throws Exception;
    public Mono<SavingAccount> update(SavingAccount account);
    public Mono<Boolean> delete(String id);
    public Mono<Customer> findByDocument(String document);
}
