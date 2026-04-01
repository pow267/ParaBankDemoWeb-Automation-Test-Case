package com.parasoft.parabank.domain.validator;

import java.util.Objects;
import jakarta.annotation.Resource;

import org.springframework.lang.NonNull;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.parasoft.parabank.domain.Customer;
import com.parasoft.parabank.web.form.LookupForm;

/**
 * Provides basic empty field validation for <code>Payee</code> object
 */
public class LookupFormValidator implements Validator {
    @Resource(name = "addressValidator")
    private Validator addressValidator;

    @Resource(name = "customerValidator")
    private Validator customerValidator;

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the customerValidator property</DD>
     * <DT>Date:</DT>
     * <DD>Oct 15, 2015</DD>
     * </DL>
     *
     * @return the value of customerValidator field
     */
    public Validator getCustomerValidator() {
        return customerValidator;
    }

    public void setAddressValidator(final Validator addressValidator) {
        this.addressValidator = addressValidator;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the customerValidator property</DD>
     * <DT>Date:</DT>
     * <DD>Oct 15, 2015</DD>
     * </DL>
     *
     * @param aCustomerValidator
     *            new value for the customerValidator property
     */
    public void setCustomerValidator(final Validator aCustomerValidator) {
        customerValidator = aCustomerValidator;
    }

    @Override
    public boolean supports(@NonNull final Class<?> clazz) {
        return LookupForm.class.isAssignableFrom(clazz) || Customer.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull final Object obj, @NonNull final Errors errors) {
        if (obj instanceof Customer) {
            final Validator localCustomerValidator = getCustomerValidator();
            if (localCustomerValidator != null) {
                localCustomerValidator.validate(obj, errors);
            }
        } else if (obj instanceof LookupForm) {
            ValidationUtils.rejectIfEmpty(errors, "firstName", "error.first.name.required");
            ValidationUtils.rejectIfEmpty(errors, "lastName", "error.last.name.required");
            ValidationUtils.rejectIfEmpty(errors, "ssn", "error.ssn.required");

            final LookupForm lookup = (LookupForm) obj;
            final Validator localAddressValidator = addressValidator;
            if (localAddressValidator != null && lookup.getAddress() != null) {
                try {
                    errors.pushNestedPath("address");
                    ValidationUtils.invokeValidator(localAddressValidator, Objects.requireNonNull(lookup.getAddress()), errors);
                } finally {
                    errors.popNestedPath();
                }
            }
        }
    }
}
