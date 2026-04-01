package com.parasoft.parabank.domain.validator;

import jakarta.annotation.Resource;

import org.springframework.lang.NonNull;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


import com.parasoft.parabank.domain.Payee;
import com.parasoft.parabank.web.form.BillPayForm;

/**
 * Provides basic empty field validation for <code>BillPayForm</code> object
 */
public class BillPayFormValidator implements Validator {
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AdminFormValidator.class);

    @Resource(name = "payeeValidator")
    private Validator payeeValidator;

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the payeeValidator property</DD>
     * <DT>Date:</DT>
     * <DD>Oct 10, 2015</DD>
     * </DL>
     *
     * @return the value of payeeValidator field
     */
    public Validator getPayeeValidator() {
        return payeeValidator;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the payeeValidator property</DD>
     * <DT>Date:</DT>
     * <DD>Oct 10, 2015</DD>
     * </DL>
     *
     * @param aPayeeValidator
     *            new value for the payeeValidator property
     */
    public void setPayeeValidator(@NonNull final Validator aPayeeValidator) {
        payeeValidator = aPayeeValidator;
    }

    @Override
    public boolean supports(@NonNull final Class<?> clazz) {
        return BillPayForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull final Object obj, @NonNull final Errors errors) {
        final BillPayForm billPayForm = (BillPayForm) obj;
        final Payee payee = billPayForm.getPayee();
        if (payee == null) {
            log.error("Payee not found in BillPayForm");
            return;
        }

        ValidationUtils.rejectIfEmpty(errors, "verifyAccount", "error.account.number.required");
        if (billPayForm.getFromAccountId() <= 0) {
            errors.rejectValue("fromAccountId", "from.account.number.required");
        }
        ValidationUtils.rejectIfEmpty(errors, "amount", "error.amount.empty");


        try {
            errors.pushNestedPath("payee");
            final Validator localPayeeValidator = payeeValidator;
            if (localPayeeValidator != null) {
                ValidationUtils.invokeValidator(localPayeeValidator, payee, errors);
            }
        } finally {
            errors.popNestedPath();
        }

        if (payee.getAccountNumber() != null && !payee.getAccountNumber().equals(billPayForm.getVerifyAccount())) {
            errors.rejectValue("verifyAccount", "error.account.number.mismatch");
        }
    }
}
