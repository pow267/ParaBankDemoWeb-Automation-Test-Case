package com.parasoft.parabank.domain.validator;

import jakarta.annotation.Resource;

import org.springframework.lang.NonNull;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.parasoft.parabank.domain.Customer;
import com.parasoft.parabank.web.form.CustomerForm;

/**
 * Provides basic empty field validation for Customer object
 */
public class CustomerFormValidator implements Validator {
    @Resource(name = "customerValidator")
    private Validator customerValidator;

    public void setCustomerValidator(final Validator addressValidator) {
        customerValidator = addressValidator;
    }

    @Override
    public boolean supports(@NonNull final Class<?> clazz) {
        return CustomerForm.class.isAssignableFrom(clazz) || Customer.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull final Object obj, @NonNull final Errors errors) {
        final Validator localCustomerValidator = customerValidator;
        if (localCustomerValidator == null) {
            return;
        }

        if (obj instanceof Customer) {
            localCustomerValidator.validate(obj, errors);
        } else {
            final CustomerForm customerForm = (CustomerForm) obj;
            final Customer customer = customerForm.getCustomer();
            if (customer != null) {
                try {
                    errors.pushNestedPath("customer");
                    ValidationUtils.invokeValidator(localCustomerValidator, customer, errors);
                } finally {
                    errors.popNestedPath();
                }
                if (customerForm.getRepeatedPassword() == null || customerForm.getRepeatedPassword().length() <= 0) {
                    errors.rejectValue("repeatedPassword", "error.password.confirmation.required");
                } else if (customer.getPassword() != null
                    && customer.getPassword().length() > 0
                    && !customer.getPassword().equals(customerForm.getRepeatedPassword())) {
                    errors.rejectValue("repeatedPassword", "error.password.mismatch");
                }
            }
        }

    }
}
