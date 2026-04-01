package com.parasoft.parabank.web.controller;

import java.util.Objects;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.parasoft.parabank.domain.Customer;
import com.parasoft.parabank.util.AccessModeController;
import com.parasoft.parabank.util.Constants;
import com.parasoft.parabank.web.UserSession;
import com.parasoft.parabank.web.form.LookupForm;

/**
 * Controller for retrieving a bank customer's login credentials
 */
@Controller("lookup.htm")
@SessionAttributes(Constants.LOOKUPFORM)
@RequestMapping("/lookup.htm")
public class CustomerLookupController extends AbstractValidatingBankController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getLookupForm(final Model model) throws Exception {
        final ModelAndView mv = super.prepForm(model);
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onSubmit(@Validated @ModelAttribute(Constants.LOOKUPFORM) final LookupForm lookupForm,
        final BindingResult errors, final HttpSession session) throws Exception {
        if (errors.hasErrors()) {
            return new ModelAndView(Objects.requireNonNull(getFormView()), errors.getModel());
        }

        final Customer customer = bankManager.getCustomer(Objects.requireNonNull(lookupForm).getSsn());
        if (customer == null) {
            errors.reject("error.customer.not.found");
            return new ModelAndView(Objects.requireNonNull(getFormView()), errors.getModel());
        }

        final UserSession userSession = new UserSession(customer);
        session.setAttribute(Constants.USERSESSION, userSession);

        return new ModelAndView("customerLookUpConfirm", "customer", customer);
    }

    @Override
    public void setAccessModeController(final AccessModeController aAccessModeController) {
        // not yet implemented
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
