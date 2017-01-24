package com.palmithor.musicapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Component for providing access to message sources
 *
 * @author palmithor
 * @since 24.1.2017.
 */
@Component
public class MessageByLocaleService {

    @Autowired private MessageSource messageSource;


    public String getMessage(final String key) {
        final Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, null, locale);
    }

    /**
     * The subclasses here should map to the prefix names (lower case) in the message resource file
     */
    public static class Errors {
        public static final String MBID_NOT_FOUND = "error.mbid_not_found";
        public static final String INTERNAL_SERVER_ERROR = "error.internal_server_error";

        private Errors() {

        }
    }

}
