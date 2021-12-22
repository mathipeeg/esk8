package dk.mathi.esk8.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    JwtAuthenticationConverter keycloakJwtAuthenticationConverter = new JwtAuthenticationConverter();

    keycloakJwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
      (Jwt jwt) ->
        jwt.<Map<String, Map<String, List<String>>>>getClaim("resource_access")
          .getOrDefault("esk8", Collections.emptyMap())
          .getOrDefault("roles", Collections.emptyList())
          .stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
          .collect(Collectors.toList())
    );

    http
      .httpBasic().disable()
      .formLogin(AbstractHttpConfigurer::disable)
      .csrf(AbstractHttpConfigurer::disable)
      .sessionManagement(
              sessionManagement ->
                      sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      .oauth2ResourceServer(
              httpSecurityOAuth2ResourceServerConfigurer ->
                      httpSecurityOAuth2ResourceServerConfigurer
                              .jwt()
                              .jwtAuthenticationConverter(keycloakJwtAuthenticationConverter)
      )
      .authorizeRequests(
              authorizeRequestsCustomizer ->
                      authorizeRequestsCustomizer
                              .anyRequest().permitAll()
      );
  }
}
