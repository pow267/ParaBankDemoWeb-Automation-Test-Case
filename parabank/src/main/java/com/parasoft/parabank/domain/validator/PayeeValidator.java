package com.parasoft.parabank.domain.validator;

import java.util.Objects;
import jakarta.annotation.Resource;

import org.springframework.lang.NonNull;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.parasoft.parabank.domain.Payee;

/**
 * Provides basic empty field validation for <code>Payee</code> object
 */
public class PayeeValidator implements Validator {
    @Resource(name = "addressValidator")
    private Validator addressValidator;

    public void setAddressValidator(final Validator addressValidator) {
        this.addressValidator = addressValidator;
    }

    @Override
    public boolean supports(@NonNull final Class<?> clazz) {
        return Payee.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull final Object obj, @NonNull final Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "error.payee.name.required");
        ValidationUtils.rejectIfEmpty(errors, "phoneNumber", "error.phone.number.required");
        ValidationUtils.rejectIfEmpty(errors, "accountNumber", "error.account.number.required");

        final Payee payee = (Payee) obj;
        final Validator localAddressValidator = addressValidator;
        if (payee != null && localAddressValidator != null && payee.getAddress() != null) {
            try {
                errors.pushNestedPath("address");
                ValidationUtils.invokeValidator(localAddressValidator, Objects.requireNonNull(payee.getAddress()), errors);
            } finally {
                errors.popNestedPath();
            }
        }
    }
}
