package com.epam.esm.controller.exception;

import com.epam.esm.controller.error_handler.ProjectError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Class provides methods, returning exception objects with localized messages
 */
@Component
public class ExceptionProvider {

    /**
     * Source for localized messages
     */
    private final ResourceBundleMessageSource messageSource;

    /**
     * Instantiates a new Exception provider.
     *
     * @param messageSource the message source
     */
    @Autowired
    public ExceptionProvider(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Method returns WrongParameterFormatException with localized message
     *
     * @param error the error
     * @return the wrong parameter format exception
     */
    public WrongParameterFormatException wrongParameterFormatException(ProjectError error) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(error.getMessageKey(), null, locale);
        return new WrongParameterFormatException(message, error.getErrorCode());
    }

    /**
     * Method returns GiftEntityNotFoundException with localized message
     *
     * @param error the error
     * @return the gift entity not found exception
     */
    public GiftEntityNotFoundException giftEntityNotFoundException(ProjectError error) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(error.getMessageKey(), null, locale);
        return new GiftEntityNotFoundException(message, error.getErrorCode());
    }
}
