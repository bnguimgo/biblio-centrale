package com.bnguimgo.biblio.biblocentrale.utils;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

/**
 * Cette classe est nécessaire sinon, impossible de parser les dates
 */
@Configuration
//Source: https://www.baeldung.com/spring-boot-formatting-json-dates
// configure a default date format in our application.
// We have to define a bean and override its customize method to set the desired format.
// l’avantage est que cette configuration fonctionne à la fois pour Java 8 et les versions JAVA inférieures à java-8.
public class CustomDateTimeFormatter {

    private static final String dateFormat = "yyyy-MM-dd";
    private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.simpleDateFormat(dateTimeFormat);
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
        };
    }

}