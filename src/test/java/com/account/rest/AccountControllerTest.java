package com.account.rest;

import com.account.domain.entities.Account;
import com.account.domain.repositories.ReactiveAccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;

//https://itnext.io/reactive-microservices-with-spring-5-95c5f8cd03d0

//https://www.logicbig.com/tutorials/spring-framework/spring-webflux/web-client.html

//https://itnext.io/reactive-microservices-with-spring-5-95c5f8cd03d0
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AccountControllerTest {

    private WebTestClient webTestClient;


    private List<Account> expectedAccounts;

    @Autowired
    private ReactiveAccountRepository reactiveAccountRepository;

    @Before
    public void setup() {
        webTestClient = WebTestClient.bindToController(new AccountController(reactiveAccountRepository)).build();
        expectedAccounts = reactiveAccountRepository.findAll().collectList().block();
    }

    @Test
    public void findAllAccountsTest() {

        webTestClient.get().uri("/accounts/")
                .accept(APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON_UTF8)
                .expectBodyList(Account.class).isEqualTo(expectedAccounts);
        //Mono<String> result =

//        WebClient webClient = WebClient.create("http://localhost:8080/accounts/");
//                webClient.get()
//                .uri("/accounts/")
//                .retrieve()
//                .bodyToMono(String.class);

    }

    @Test
    public void streamAllAccountsTest() throws Exception {
        webTestClient.get()
                .uri("/accounts/")
                .accept(TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                //TODO .expectHeader().contentType(TEXT_EVENT_STREAM)
                .returnResult(Account.class);
    }

    @Test
    public void streamAllAccountsByCurrencyTest() throws Exception {
        webTestClient.get()
                .uri("/accounts/?currency=EUR")
                .accept(TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                //TODO .expectHeader().contentType(TEXT_EVENT_STREAM)
                .returnResult(Account.class);
    }
}