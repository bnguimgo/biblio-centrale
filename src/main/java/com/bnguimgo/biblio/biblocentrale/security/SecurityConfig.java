package com.bnguimgo.biblio.biblocentrale.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwksetUri;

    @Autowired
    CustomBearerTokenResolver customBearerTokenResolver;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authz -> authz
                    //.requestMatchers("/api/v1/authors/**").hasAuthority("ADMIN")
                    .requestMatchers("/").permitAll()
                    .anyRequest()
                    .authenticated())
            .oauth2ResourceServer(oauth2 -> {
                //oauth2.jwt((jwt) ->jwt.decoder(jwtDecoder()));
                oauth2.jwt(jwt ->jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()));

                oauth2.bearerTokenResolver(customBearerTokenResolver);
            });
        return http.build();
    }

    /**
     * On a besoin de JwtDecoder pour décoder le token
     * @return renvoie jwtDecoder
     */
/*    @Bean
    public JwtDecoder jwtDecoder() {

        //FIXME On prépare le JWT Decodeur et on lui passe le token à décoder
        JwtDecoder jwtDecoder = NimbusJwtDecoder.withIssuerLocation(issuerUri).build();
        //JwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwksetUri).build();

        return token -> {
            log.info("token: " + token);
            Jwt jwt = jwtDecoder.decode(token);
            log.info("jwt claims: " + jwt.getClaims().toString());
            return jwt;
        };
    }*/

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            log.info("token: " + jwt.getTokenValue()); // ATTENTION à la sécurité, ne pas loguer ceci en production
            log.info("JWT claims: " + jwt.getClaims()); // ATTENTION à la sécurité, ne pas loguer ceci en production
            return List.of(); // On peut aussi transformer les claims en rôles ici
        });
        return converter;
    }
}