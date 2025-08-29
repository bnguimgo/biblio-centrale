package com.bnguimgo.biblio.biblocentrale.components;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class PropertiesServiceConfig {

/*    @Value("${spring.security.oauth2.client.registration.cognito.domain}")
    private String domain;*/
    @Value("${spring.security.oauth2.client.registration.cognito.client-id}")
    private String clientId;
/*    @Value("${spring.security.oauth2.client.registration.cognito.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.registration.cognito.redirect-uri}")
    private String redirectUri;
    @Value("${spring.security.oauth2.client.registration.cognito.scope}")
    private String scope;
    @Value("${spring.security.oauth2.client.registration.cognito.logout.success.redirect-url}")
    private String logoutRedirectUrl;*/
    @Value("${spring.security.oauth2.client.provider.cognito.issuerUri}")
    private String issuerUri;
    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;
/*    @Value("${spring.security.oauth2.client.provider.cognito.token-uri}")
    private String tokenUri;
    @Value("${spring.security.oauth2.client.provider.cognito.authorization-uri}")
    private String authorizationUri;
    @Value("${spring.security.oauth2.client.registration.cognito.user-info}")
    private String userInfo;
    @Value("${application.server.base.url}")
    private String serverUrl;
    @Value("${application.client.base.url}")
    private String clientUrl;*/

}