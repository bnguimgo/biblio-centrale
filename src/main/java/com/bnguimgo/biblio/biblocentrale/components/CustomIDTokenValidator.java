package com.bnguimgo.biblio.biblocentrale.components;

import com.bnguimgo.biblio.biblocentrale.exception.BiblioErrorEnum;
import com.bnguimgo.biblio.biblocentrale.exception.BiblioRuntimeException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.id.Issuer;
import com.nimbusds.openid.connect.sdk.claims.IDTokenClaimsSet;
import com.nimbusds.openid.connect.sdk.validators.IDTokenValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

/**
 * Source: <a href="https://connect2id.com/blog/how-to-validate-an-openid-connect-id-token">CustomIDTokenValidator</a>
 */
@Component
@Slf4j
public class CustomIDTokenValidator {

    @Autowired
    PropertiesServiceConfig properties;

    public IDTokenValidator buildValidator() throws MalformedURLException {

        // The required parameters
        Issuer iss = new Issuer(properties.getIssuerUri());
        ClientID clientID = new ClientID(properties.getClientId());
        JWSAlgorithm jwsAlg = JWSAlgorithm.RS256;
        URL jwkSetURL = new URL(properties.getJwkSetUri());
        return new IDTokenValidator(iss, clientID, jwsAlg, jwkSetURL);
    }

    public void validate (String token) throws ParseException, MalformedURLException {
        // Parse the ID token
        JWT idToken = JWTParser.parse(token);

        // Set the expected nonce, leave null if none
        IDTokenClaimsSet claims;

        try {
            claims = buildValidator ().validate(idToken, null);
            log.info("Logged in user " + claims.getSubject());
            log.info("Credentials on server side validated successfully ....");
        } catch (BadJOSEException e) {
            log.error("IDTokenValidator BadJOSEException " + e.getMessage());
            throw new BiblioRuntimeException(BiblioErrorEnum.TOKEN_INVALID, HttpStatus.UNAUTHORIZED, "BadJOSEException, Invalid token, try with idToken instead of accesToken "+e.getMessage());
        } catch (JOSEException e) {
            log.error("IDTokenValidator JOSEException " + e.getMessage());
            throw new BiblioRuntimeException(BiblioErrorEnum.TOKEN_INVALID, HttpStatus.UNAUTHORIZED, "JOSEException, Invalid token "+e.getMessage());
        }

    }
}