package com.parasoft.parabank.web.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

import com.parasoft.parabank.util.AccessModeController;
import com.parasoft.parabank.util.Constants;
import com.parasoft.parabank.web.form.ContactForm;

/**
 * Controller for collecting customer support messages (currently ignores the message)
 */
@Controller("/contact.htm")
@SessionAttributes(Constants.CONTACTFORM)
@RequestMapping("/contact.htm")
public class ContactController extends AbstractValidatingBankController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getContactForm(final Model model) throws Exception {
        final ModelAndView mv = super.prepForm(model);
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onSubmit(
        @Validated @ModelAttribute(Constants.CONTACTFORM) final ContactForm customerServiceForm,
        final BindingResult errors) throws Exception {
        if (errors.hasErrors()) {
            return new ModelAndView(Objects.requireNonNull(getFormView()), errors.getModel());
        }

        final Map<String, Object> model = new HashMap<>();
        model.put("name", Objects.requireNonNull(customerServiceForm).getName());

        return new ModelAndView("contactConfirm", "model", model);
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

    @Override
    public void setValidator(final Validator aValidator) {
        validator = aValidator;
    }

}
