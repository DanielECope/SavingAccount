package com.nttdata.saving_account.repository;

import com.nttdata.saving_account.models.Customer;
import com.nttdata.saving_account.models.SavingAccount;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface SavingAccountRepository extends ReactiveMongoRepository<SavingAccount, String> {

    public Mono<SavingAccount> findByCustomerDocument(String document);
    //@Query("{ 'accountNumber' :  ?0 }")
    public Mono<SavingAccount> findByAccountNumber(String accountNumber);
}
