package com.account.domain.enums;

/**
 * Created by rodrigo.chaves on 20/06/2017.
 */
public enum Currency {
    USD, EUR, BR;

    public static Currency getDefault() {
        return BR;
    }
}
