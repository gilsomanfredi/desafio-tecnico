package io.github.gilsomanfredi.cadastropessoa.config.i18n;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class I18n {

    private final @NonNull
    MessageSource messageSource;

    public String getMessage(String key, Object... args) {

        return messageSource.getMessage(key, args, getLocale());
    }

    public String getMessage(MessageSourceResolvable error) {

        return messageSource.getMessage(error, getLocale());
    }

    private Locale getLocale() {

        return LocaleContextHolder.getLocale();
    }
}
