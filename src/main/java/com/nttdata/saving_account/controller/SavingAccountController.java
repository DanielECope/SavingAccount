package com.nttdata.saving_account.controller;

import com.nttdata.saving_account.models.SavingAccount;
import com.nttdata.saving_account.service.SavingAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/SavingAccount")
public class SavingAccountController {
    @Autowired
    SavingAccountService service;

    @GetMapping("/list")
    public Flux<SavingAccount> list(){
        log.info("Saving_account: controller list() method ");
        return service.findAll();
    }
    @GetMapping("/list/{id}")
    public Mono<SavingAccount> findById(@PathVariable String id) throws Exception {
        log.info("Saving_account: controller findById() method ");
        return service.findById(id);
    }
    @GetMapping("/findByAccountNumber/{accountNumber}")
    public Mono<SavingAccount> findByAccountNumber(@PathVariable String accountNumber){
        log.info("Saving_account: controller findById() method ");
        return service.findByAccountNumber(accountNumber);
    }
    @PostMapping(path = "/create")
    public Mono<SavingAccount> create(@RequestBody SavingAccount account) throws Exception {
        log.info("Saving_account: controller create() method : {}",account.toString());
        return service.save(account);
    }
    @PutMapping(path = "/update")
    public Mono<SavingAccount> update(@RequestBody SavingAccount account)
    {
        log.info("Saving_account: controller update() method : {}",account.toString());
        return service.update(account);
    }
    @DeleteMapping(path = "/delete/{id}")
    public Mono<String> delete(@PathVariable String id)
    {
        log.info("Saving_account: controller delete() method : {}",id);
        service.delete(id).map(obj->{
            if (obj){
                return Mono.just("Cuenta de ahorro eliminada");
            }else{
                return Mono.just("La Cuenta de ahorro no puedo ser eliminada");
            }
        });
        return Mono.just("La Cuenta de ahorro no puedo ser eliminada");
    }
}
