package com.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/**
 * Created by rodrigo.chaves on 27/06/2017.
 */
@SpringBootApplication
@EnableReactiveMongoRepositories
public class ReactiveAccountRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReactiveAccountRestApplication.class, args);
    }
}
