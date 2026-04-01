package com.parasoft.parabank.domain.validator;

import org.springframework.lang.NonNull;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.parasoft.parabank.domain.Address;

/**
 * Provides basic empty field validation for Address object
 */
public class AddressValidator implements Validator {
    @Override
    public boolean supports(@NonNull final Class<?> clazz) {
        return Address.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull final Object obj, @NonNull final Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "street", "error.address.required");
        ValidationUtils.rejectIfEmpty(errors, "city", "error.city.required");
        ValidationUtils.rejectIfEmpty(errors, "state", "error.state.required");
        ValidationUtils.rejectIfEmpty(errors, "zipCode", "error.zip.code.required");
    }
}
