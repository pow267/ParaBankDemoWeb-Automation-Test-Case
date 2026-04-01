package com.parasoft.parabank.web.controller;

import java.util.Objects;
import jakarta.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.parasoft.parabank.util.Constants;
import com.parasoft.parabank.util.Util;
import com.parasoft.parabank.web.form.AdminForm;

/**
 * Controller for starting/stopping JMS listener
 */
@Controller("/jms.htm")
@RequestMapping("/jms.htm")
public class JmsListenerController extends AbstractBaseAdminController implements DisposableBean {

    private static final Logger log = LoggerFactory.getLogger(JmsListenerController.class);

    @Override
    @PreDestroy
    public void destroy() {
        try {
            log.info("JMS Broker Shutdown sequence initiated");
            getAdminManager().shutdownJmsListener();
            log.info("JMS Broker Shutdown sequence completed");
        } catch (final Exception ex) {
            log.error("caught {} Error : ", ex.getClass().getSimpleName() //$NON-NLS-1$
                , ex);
        }
    }
    @Override
    @ModelAttribute(Constants.ADMINFORM)
    public AdminForm getForm() throws Exception {
        return super.getForm();
    }

    @RequestMapping
    public ModelAndView handleRequest(@RequestParam("shutdown") final String sShutdown, final Model model)
            throws Exception {
        final ModelAndView modelAndView = new ModelAndView(Objects.requireNonNull(getFormView()), Objects.requireNonNull(model).asMap());
        if (Util.isEmpty(sShutdown)) {
            log.warn("Empty shutdown parameter");
            modelAndView.addObject("error", "error.empty.shutdown.parameter");
        } else {
            final boolean shutdown = Boolean.parseBoolean(sShutdown);
            if (shutdown) {
                getAdminManager().shutdownJmsListener();
                log.info("Using regular JDBC connection. AccessModeController not implemented.");
                modelAndView.addObject("message", "jms.shutdown.success");
            } else {
                getAdminManager().startupJmsListener();
                log.info("Using regular JDBC connection. AccessModeController not implemented.");
                modelAndView.addObject("message", "jms.startup.success");
            }
        }
        modelAndView.setView(new RedirectView("/admin.htm", true));
        return modelAndView;
    }

}
