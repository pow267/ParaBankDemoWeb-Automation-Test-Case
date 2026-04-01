package com.parasoft.parabank.web.controller;

import java.util.Objects;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.parasoft.parabank.util.AccessModeController;
import com.parasoft.parabank.util.Constants;
import com.parasoft.parabank.web.UserSession;
import com.parasoft.parabank.web.form.CustomerForm;

/**
 * Controller for creating a new bank customer
 */
@Controller("/register.htm")
@SessionAttributes(Constants.CUSTOMERFORM)
@RequestMapping("/register.htm")
public class RegisterCustomerController extends AbstractValidatingBankController {
    private static final Logger log = LoggerFactory.getLogger(RegisterCustomerController.class);

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getCustomerForm(final Model model) throws Exception {
        final ModelAndView mv = super.prepForm(model);
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onSubmit(@Valid @ModelAttribute(Constants.CUSTOMERFORM) final CustomerForm customerForm,
        final BindingResult errors, final HttpSession session) throws Exception {
        if (errors.hasErrors()) {
            return new ModelAndView(Objects.requireNonNull(getFormView()), errors.getModel());
        }

        try {
            Objects.requireNonNull(bankManager).createCustomer(Objects.requireNonNull(customerForm.getCustomer()));
        } catch (final DataIntegrityViolationException ex) {
            log.warn("Username " + Objects.requireNonNull(customerForm.getCustomer()).getUsername() + " already exists in database");
            errors.rejectValue("customer.username", "error.username.already.exists");
            final ModelAndView mav = new ModelAndView(Objects.requireNonNull(getFormView()), errors.getModel());
            return mav;
        }

        final UserSession userSession = new UserSession(Objects.requireNonNull(bankManager).getCustomer(Objects.requireNonNull(customerForm.getCustomer()).getId()));
        session.setAttribute(Constants.USERSESSION, userSession);

        return new ModelAndView("registerConfirm", "customer", Objects.requireNonNull(customerForm.getCustomer()));
    }

    @Override
    public void setAccessModeController(final AccessModeController aAccessModeController) {
        // not implemented
    }

    /** {@inheritDoc} */
    @Override
    public void setCommandClass(final Class<?> aCommandClass) {
        super.setCommandClass(aCommandClass);
    }

    /** {@inheritDoc} */
    @Override
    public void setCommandName(final String aCommandName) {
        super.setCommandName(aCommandName);
    }

    /** {@inheritDoc} */
    @Override
    public void setFormView(final String aFormView) {
        super.setFormView(aFormView);
    }

    /** {@inheritDoc} */
    @Override
    public void setValidator(final Validator aValidator) {
        validator = aValidator;

    }

}
