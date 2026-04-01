package com.parasoft.parabank.domain.validator;

import org.springframework.lang.NonNull;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.parasoft.parabank.web.form.AdminForm;

public class AdminFormValidator implements Validator {
    @SuppressWarnings("unused")
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AdminFormValidator.class);

    @Override
    public boolean supports(@NonNull final Class<?> aParamClass) {
        return AdminForm.class.isAssignableFrom(aParamClass);
    }

    @Override
    public void validate(@NonNull final Object adminFormObj, @NonNull final Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "initialBalance", "error.initial.balance.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "minimumBalance", "error.minimum.balance.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "loanProcessorThreshold",
            "error.loan.processor.threshold.required");
    }

}
