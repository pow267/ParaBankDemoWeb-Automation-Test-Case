package com.parasoft.parabank.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import com.parasoft.parabank.domain.Account.AccountType;

/**
 * Class for converting a string (from an input form) to an AccountType
 */
public class AccountTypeConverter implements Converter<String, AccountType> {
    @Override
    public AccountType convert(@NonNull String key) {
        if (key == null || key.trim().isEmpty()) {
            return null;
        }
        return AccountType.valueOf(key);
    }
}
