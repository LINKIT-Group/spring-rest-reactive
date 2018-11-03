package com.account.rest;

import com.account.domain.entities.Account;
import com.account.domain.enums.Currency;
import com.account.domain.repositories.ReactiveAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by rodrigo.chaves on 20/06/2017.
 */
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private ReactiveAccountRepository reactiveAccountRepository;

    @Autowired
    public AccountController(ReactiveAccountRepository accountRepository) {
        reactiveAccountRepository = accountRepository;
    }

    @RequestMapping(value = "/currency/{currency}", method = RequestMethod.GET)
    Flux<Account> findByCurrency(@PathVariable String currency) {
        return reactiveAccountRepository.findByCurrency(Currency.fromValue(currency));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    Mono<Account> findById(@PathVariable String id) {
        return reactiveAccountRepository.findById(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    Mono<Account> save(@RequestBody Account account) {
        return reactiveAccountRepository.save(account);
    }

    @RequestMapping(value = "/batch", method = RequestMethod.POST)
    Flux<Account> saveAll(@RequestBody Flux<Account> accounts) {
        return reactiveAccountRepository.saveAll(accounts);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    Flux<Account> findAll() {
        return reactiveAccountRepository.findAll();
    }
}
