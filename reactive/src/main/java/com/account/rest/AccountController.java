package com.account.rest;

import com.account.domain.entities.Account;
import com.account.domain.repositories.ReactiveAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

/**
 * Created by rodrigo.chaves on 20/06/2017.
 */
@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private ReactiveAccountRepository reactiveAccountRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    Flux<Account> findByCurrencyReactive() {
        return reactiveAccountRepository.findAll();
    }

    @RequestMapping(value = "/batch", method = RequestMethod.POST)
    Flux<Account> insertAll(@RequestBody Flux<Account> accounts) {
        return reactiveAccountRepository.saveAll(accounts);
    }

}
