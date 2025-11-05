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
import org.springframework.security.web.SecurityFilterChain;

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
                oauth2.jwt((jwt) ->jwt.decoder(jwtDecoder()));
                oauth2.bearerTokenResolver(customBearerTokenResolver);
            });
        return http.build();
    }

    /**
     * On a besoin de JwtDecoder pour décoder le token
     * @return renvoie jwtDecoder
     */
    @Bean
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
    }

    //Si on n'a pas besoin de loguer le token et le claims
/*    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder//.withJwkSetUri(jwksetUri)
                .withIssuerLocation(issuerUri)
                .build();
    }*/
}