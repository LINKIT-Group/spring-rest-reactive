package com.account.domain.entities;

import com.account.domain.enums.Currency;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by rodrigo on 04/01/2017.
 */
@Getter
@Setter
@Document(collection = "accounts")
public class Account {

    @Id
    private String id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date creationDate;

    private Double amount;

    private Currency currency;
}