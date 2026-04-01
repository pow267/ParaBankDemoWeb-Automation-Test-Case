package com.parasoft.parabank.domain.validator;

import org.springframework.lang.NonNull;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.parasoft.parabank.web.form.TransferForm;

/**
 * Provides basic empty field validation for TransferForm object
 */
public class TransferFormValidator implements Validator {
    @Override
    public boolean supports(@NonNull final Class<?> clazz) {
        return TransferForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull final Object obj, @NonNull final Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "amount", "error.amount.empty");
    }
}
