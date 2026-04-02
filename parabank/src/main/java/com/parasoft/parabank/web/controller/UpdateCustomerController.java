package com.parasoft.parabank.web.controller;

import java.io.IOException;
import java.util.Objects;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.parasoft.parabank.domain.logic.AdminManager;
import com.parasoft.parabank.service.ParaBankServiceException;
import com.parasoft.parabank.util.AccessModeController;
import com.parasoft.parabank.util.Constants;
import com.parasoft.parabank.util.SessionParam;
import com.parasoft.parabank.web.UserSession;
import com.parasoft.parabank.web.form.CustomerForm;

/**
 * Controller for updating customer information
 */
@Controller("secure_updateprofile")
@SessionAttributes(Constants.CUSTOMERFORMUPDATE)
@RequestMapping("/updateprofile.htm")
public class UpdateCustomerController extends AbstractValidatingBankController {
    private static final Logger log = LoggerFactory.getLogger(UpdateCustomerController.class);

    @Resource(name = "accessModeController")
    private AccessModeController accessModeController;

    @Resource(name = "adminManager")
    private AdminManager adminManager;

    public Customer getCustomer(final int custId) throws ParaBankServiceException, IOException, JAXBException {
        Customer cu;
        cu = Objects.requireNonNull(accessModeController).doGetCustomer(custId);
        return cu;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getLookupForm(@SessionParam(Constants.USERSESSION) final UserSession userSession,
        final Model model) throws ParaBankServiceException, IOException, JAXBException {
        String accessMode = null;
        if (adminManager != null) {
            accessMode = adminManager.getParameter("accessmode");
        }

        Customer customer;
        if (accessMode != null && !accessMode.equalsIgnoreCase("jdbc")) {
            customer = Objects.requireNonNull(accessModeController).doGetCustomer(Objects.requireNonNull(userSession).getCustomer().getId());
        } else {
            customer = bankManager.getCustomer(Objects.requireNonNull(userSession).getCustomer().getId());
        }

        final CustomerForm form = new CustomerForm(Objects.requireNonNull(customer));
        form.setRepeatedPassword(form.getCustomer().getPassword());
        final ModelAndView mv = super.prepForm(model, form);
        mv.addObject("customerId", userSession.getCustomer().getId());
        mv.addObject("username", userSession.getCustomer().getUsername());
        mv.addObject("password", userSession.getCustomer().getPassword());
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onSubmit(
        @Validated @ModelAttribute(Constants.CUSTOMERFORMUPDATE) final CustomerForm customerForm,
        final BindingResult errors, final HttpSession session) throws Exception {
        if (errors.hasErrors()) {
            return new ModelAndView(Objects.requireNonNull(getFormView()), errors.getModel());
        }

        String accessMode = null;

        if (adminManager != null) {
            accessMode = adminManager.getParameter("accessmode");
        }
        log.info("Updating Customer Contact Information");
        if (accessMode != null && !accessMode.equalsIgnoreCase("jdbc")) {
            Objects.requireNonNull(accessModeController).updateCustomer(Objects.requireNonNull(customerForm).getCustomer());
            final UserSession userSession = new UserSession(getCustomer(customerForm.getCustomer().getId()));
            session.setAttribute(Constants.USERSESSION, userSession);
        }

        else {
            bankManager.updateCustomer(Objects.requireNonNull(Objects.requireNonNull(customerForm).getCustomer()));
            final UserSession userSession =
                new UserSession(bankManager.getCustomer(Objects.requireNonNull(customerForm.getCustomer()).getId()));
            session.setAttribute(Constants.USERSESSION, userSession);
        }

        return new ModelAndView("updateprofileConfirm", "customer", Objects.requireNonNull(Objects.requireNonNull(customerForm).getCustomer()));
    }

    @Override
    public void setAccessModeController(final AccessModeController accessModeController) {
        this.accessModeController = accessModeController;
    }

    public void setAdminManager(final AdminManager adminManager) {
        this.adminManager = adminManager;
    }

    /** {@inheritDoc} */
    @Override
    @Resource(name = "classCustomerForm")
    public void setCommandClass(final Class<?> aCommandClass) {
        super.setCommandClass(aCommandClass);
    }

    /** {@inheritDoc} */
    @Override
    @Resource(name = Constants.CUSTOMERFORMUPDATE)
    public void setCommandName(final String aCommandName) {
        super.setCommandName(aCommandName);
    }

    /** {@inheritDoc} */
    @Override
    @Resource(name = Constants.UPDATEPROFILE)
    public void setFormView(final String aFormView) {
        super.setFormView(aFormView);
    }

    /** {@inheritDoc} */
    @Override
    @Resource(name = "customerFormValidator")
    public void setValidator(final Validator aValidator) {
        validator = aValidator;
    }

}
