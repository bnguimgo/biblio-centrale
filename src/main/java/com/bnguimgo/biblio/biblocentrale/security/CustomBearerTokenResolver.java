package com.bnguimgo.biblio.biblocentrale.security;

import com.bnguimgo.biblio.biblocentrale.components.CustomIDTokenValidator;
import com.bnguimgo.biblio.biblocentrale.exception.BiblioErrorEnum;
import com.bnguimgo.biblio.biblocentrale.exception.BiblioRuntimeException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.text.ParseException;

/**
 * Source: <a href="https://medium.com/@pravinghumre/how-to-decode-jwt-tokens-in-a-spring-boot-based-java-application-b0a27b6c90a6">how-to-decode-jwt-tokens-in-a-spring-boot-based-java-application</a>
 */
@Service
public class CustomBearerTokenResolver implements BearerTokenResolver {

    @Autowired
    CustomIDTokenValidator customIDTokenValidator;

    @Override
    public String resolve(HttpServletRequest request) {
        String customHeader = request.getHeader("Authorization");
        System.out.println("Authorization: " + customHeader);

        if (StringUtils.hasText(customHeader)) {

            String token = customHeader.substring(7);
            try {//FIXME ceci est optionnel, on profite juste pour valider le token à chaque requête
                customIDTokenValidator.validate(token);
            } catch (ParseException | MalformedURLException e) {
                //throw new RuntimeException(e);
                throw new BiblioRuntimeException(BiblioErrorEnum.TOKEN_INVALID, HttpStatus.UNAUTHORIZED, "Invalid token "+e.getMessage());
            }
            return token;
        } else {
            throw new BiblioRuntimeException(BiblioErrorEnum.TOKEN_NULL, HttpStatus.UNAUTHORIZED, "Token cannot be null");
        }
    }
}