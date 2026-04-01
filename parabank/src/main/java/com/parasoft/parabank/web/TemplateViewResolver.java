package com.parasoft.parabank.web;

import java.util.Locale;
import java.util.Objects;

import org.springframework.lang.NonNull;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Override default view resolver behavior to forward to template page
 */
public class TemplateViewResolver extends InternalResourceViewResolver {
    static final String VIEW_ATTRIBUTE = "view";
    static final String TEMPLATE_VIEW_NAME = "template";

    @Override
    public View resolveViewName(@NonNull String viewName, @NonNull Locale locale)
            throws Exception {
        getAttributesMap().put(VIEW_ATTRIBUTE, Objects.requireNonNull(viewName));
        return super.resolveViewName(TEMPLATE_VIEW_NAME, Objects.requireNonNull(locale));
    }

    @Override
    public boolean isCache() {
        return false;
    }
}
