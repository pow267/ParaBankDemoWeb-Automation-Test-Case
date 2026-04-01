package com.parasoft.parabank.web.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import jakarta.annotation.Resource;
import jakarta.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.parasoft.parabank.domain.Account;
import com.parasoft.parabank.domain.Customer;
import com.parasoft.parabank.domain.Transaction;
import com.parasoft.parabank.domain.logic.AdminManager;
import com.parasoft.parabank.service.ParaBankServiceException;
import com.parasoft.parabank.util.AccessModeController;
import com.parasoft.parabank.util.Constants;
import com.parasoft.parabank.util.SessionParam;
import com.parasoft.parabank.web.UserSession;
import com.parasoft.parabank.web.form.FindTransactionForm;

/**
 * Controller for searching transactions
 */
@Controller("secure_findtrans")
@SessionAttributes(Constants.FINDTRANSACTIONFORM)
@RequestMapping("/findtrans.htm")
public class FindTransactionController extends AbstractValidatingBankController {
    private static final Logger log = LoggerFactory.getLogger(FindTransactionController.class);

    //private List<Transaction> transactions = null;

    @Resource(name = "accessModeController")
    private AccessModeController accessModeController;

    @Resource(name = "adminManager")
    private AdminManager adminManager;

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Get account IDs for the customer in the user session</DD>
     * <DT>Date:</DT>
     * <DD>Oct 10, 2015</DD>
     * </DL>
     *
     * @param userSession
     * @return
     */
    @ModelAttribute("accounts")
    public List<Integer> getAccountIds(@SessionParam(Constants.USERSESSION) final UserSession userSession)
            throws ParaBankServiceException, IOException, JAXBException {
        //final UserSession userSession = (UserSession) WebUtils.getRequiredSessionAttribute(request, Constants.USERSESSION);

        final Customer customer = Objects.requireNonNull(userSession).getCustomer();

        String accessMode = null;
        if (adminManager != null) {
            accessMode = adminManager.getParameter("accessmode");
        }

        List<Account> accounts;
        if (accessMode != null && !accessMode.equalsIgnoreCase("jdbc")) {
            accounts = Objects.requireNonNull(accessModeController).doGetAccounts(customer);
        } else {
            accounts = bankManager.getAccountsForCustomer(Objects.requireNonNull(customer));
        }

        final List<Integer> accountIds = new ArrayList<>();
        for (final Account account : Objects.requireNonNull(accounts)) {
            accountIds.add(account.getId());
        }
        return accountIds;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Handle GET request for find transaction form</DD>
     * <DT>Date:</DT>
     * <DD>Oct 10, 2015</DD>
     * </DL>
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getFindTransactionForm(final Model model) throws Exception {
        final ModelAndView mav = super.prepForm(model);
        return mav;
    }

    /** {@inheritDoc} */
    @Override
    protected void initBinder(final WebDataBinder binder) {
        super.initBinder(binder);
        Objects.requireNonNull(binder).registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM-dd-yyyy"), true));
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Handle POST request for transaction search</DD>
     * <DT>Date:</DT>
     * <DD>Oct 10, 2015</DD>
     * </DL>
     *
     * @param findTransactionForm
     * @param errors
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onSubmit(
        @Validated @ModelAttribute(Constants.FINDTRANSACTIONFORM) final FindTransactionForm findTransactionForm,
        final BindingResult errors) throws ParaBankServiceException, IOException, JAXBException, ParseException {
        //final FindTransactionForm findTransactionForm = (FindTransactionForm) command;
        if (errors.hasErrors()) {
            return new ModelAndView(Objects.requireNonNull(getFormView()), errors.getModel());
        }

        String accessMode = null;

        if (adminManager != null) {
            accessMode = adminManager.getParameter("accessmode");
        }

        List<Transaction> transactions = null;
        if (accessMode != null && !accessMode.equalsIgnoreCase("jdbc")) {
            final Integer transactionId = Objects.requireNonNull(findTransactionForm).getCriteria().getTransactionId();
            if (log.isDebugEnabled()) {
                log.debug("processing TransactionId: " + transactionId);
            }
            final Account account = Objects.requireNonNull(accessModeController).doGetAccount(findTransactionForm.getAccountId());
            transactions = accessModeController.getTransactionsForAccount(account, findTransactionForm.getCriteria());
        } else {
            final Account account = bankManager.getAccount(Objects.requireNonNull(findTransactionForm).getAccountId());
            transactions = bankManager.getTransactionsForAccount(Objects.requireNonNull(account).getId(), Objects.requireNonNull(findTransactionForm).getCriteria());
        }

        final Map<String, Object> model = new HashMap<>();
        model.put("transactions", transactions);

        return new ModelAndView("transactionResults", "model", model);
    }

    /** {@inheritDoc} */
    @Override
    public void setAccessModeController(final AccessModeController accessModeController) {
        this.accessModeController = accessModeController;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Set the admin manager</DD>
     * <DT>Date:</DT>
     * <DD>Oct 10, 2015</DD>
     * </DL>
     *
     * @param adminManager
     */
    public void setAdminManager(final AdminManager adminManager) {
        this.adminManager = adminManager;
    }

    /** {@inheritDoc} */
    @Override
    @Resource(name = "classFindTransactionForm")
    public void setCommandClass(final Class<?> aCommandClass) {
        super.setCommandClass(aCommandClass);
    }

    /** {@inheritDoc} */
    @Override
    @Resource(name = Constants.FINDTRANSACTIONFORM)
    public void setCommandName(final String aCommandName) {
        super.setCommandName(aCommandName);
    }

    /** {@inheritDoc} */
    @Override
    @Resource(name = Constants.FINDTRANS)
    public void setFormView(final String aFormView) {
        super.setFormView(aFormView);
    }

    /** {@inheritDoc} */
    @Override
    @Resource(name = "findTransactionFormValidator")
    public void setValidator(final Validator aValidator) {
        validator = aValidator;
    }

}
