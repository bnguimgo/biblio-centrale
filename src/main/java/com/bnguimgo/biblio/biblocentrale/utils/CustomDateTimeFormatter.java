package com.bnguimgo.biblio.biblocentrale.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Cette classe est nécessaire sinon, impossible de parser les dates
 */
@Configuration
// configure a default date format in our application.
// We have to define a bean and override its customize method to set the desired format.
// l’avantage est que cette configuration fonctionne à la fois pour Java 8 et les versions JAVA inférieures à java-8.
public class CustomDateTimeFormatter {

    private static final String dateFormat = "yyyy-MM-dd";
    private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

/*    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.simpleDateFormat(dateTimeFormat);
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
        };
    }*/

    @Bean
    public ObjectMapper objectMapper() {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);

        JavaTimeModule module = new JavaTimeModule();

        module.addSerializer(new LocalDateSerializer(dateFormatter));
        module.addSerializer(new LocalDateTimeSerializer(dateTimeFormatter));

        module.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));


        return JsonMapper.builder()
                .addModule(module)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
    }

}