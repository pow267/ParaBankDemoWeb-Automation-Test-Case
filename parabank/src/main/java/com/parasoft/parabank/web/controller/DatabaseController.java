package com.parasoft.parabank.web.controller;

import java.util.Objects;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.parasoft.parabank.util.Constants;
import com.parasoft.parabank.util.Util;
import com.parasoft.parabank.web.form.AdminForm;

/**
 * Controller for manipulating database entries
 */
@Controller("/db.htm")
@RequestMapping("/db.htm")
public class DatabaseController extends AbstractBaseAdminController {
    @Override
    @ModelAttribute(Constants.ADMINFORM)
    public AdminForm getForm() throws Exception {
        return super.getForm();
    }

    /**
     * @param action
     * @param model
     * @throws java.lang.Exception
     * @return
     *
     */
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView handleRequest(@RequestParam("action") final String action, final Model model) throws Exception {
        final ModelAndView modelAndView = new ModelAndView(Objects.requireNonNull(getFormView()));
        modelAndView.addAllObjects(Objects.requireNonNull(model).asMap());

        final AdminForm form = (AdminForm) model.asMap().get(getCommandName());

        if (Util.isEmpty(action)) {
            log.warn("Empty action parameter");
            modelAndView.addObject("error", "error.invalid.action.parameter");
        } else if ("INIT".equalsIgnoreCase(action)) {
            getAdminManager().initializeDB();
            saveAdminSettings(Objects.requireNonNull(form));
            modelAndView.addObject("message", "database.initialize.success");
        } else if ("CLEAN".equalsIgnoreCase(action)) {
            getAdminManager().cleanDB();
            saveAdminSettings(Objects.requireNonNull(form));
            modelAndView.addObject("message", "database.clean.success");
        } else {
            log.warn("Unrecognized database action: {}", action);
            modelAndView.addObject("error", "error.invalid.action.parameter");
        }

        return modelAndView;
    }

}
