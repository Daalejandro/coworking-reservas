package com.bancocuscatlan.mcvs.coworking.reservas.security;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.micrometer.observation.autoconfigure.ObservationProperties.Http;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.bancocuscatlan.mcvs.coworking.reservas.security.filter.JwtAuthenticationFilter;
import com.bancocuscatlan.mcvs.coworking.reservas.security.filter.JwtValidationFilter;

@Configuration
public class SpringSecurityConfig {

    private AuthenticationConfiguration authenticationConfiguration;

    public SpringSecurityConfig(AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests((authz) -> authz
                .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/users/create").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/users/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/space/{id}","/api/space").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/space").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/space/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/space/{id}").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/reportes").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/reservation").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/reservation/validate").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/reservation").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/reservation/list").hasAnyRole("USER")
                .requestMatchers(HttpMethod.PUT, "/api/reservation/cancelar/{id}").hasAnyRole("USER")
                .requestMatchers(HttpMethod.PUT, "/api/reservation/{id}").hasAnyRole("ADMIN")


                .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                .requestMatchers("/actuator/circuitbreakers", "/actuator/circuitbreakerevents", "/actuator/health").permitAll()

                .anyRequest().authenticated())
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationManager()))
                .csrf(config -> config.disable())
                .sessionManagement(man -> man.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
