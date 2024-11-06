package com.example.health_guardian_server.components;

import java.util.Locale;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Translator {

  static ResourceBundleMessageSource messageSource;

  @Autowired
  public Translator(ResourceBundleMessageSource messageSource) {
    Translator.messageSource = messageSource;
  }

  public static String getLocalizedMessage(String messageKey, Object... args)
      throws NoSuchMessageException {
    Locale locale = LocaleContextHolder.getLocale();
    return messageSource.getMessage(messageKey, args, locale);
  }
}
