package com.bancocuscatlan.mcvs.coworking.reservas.security;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
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
                .requestMatchers("/api/users/**").permitAll()
                .requestMatchers("/api/space/**").permitAll()
                .requestMatchers("/api/reservation/**").permitAll()

                /*
                 * .requestMatchers(HttpMethod.POST,"/api/users").hasRole("ADMIN")
                 * .requestMatchers(HttpMethod.GET,"/api/products","/api/products/{id}").
                 * hasAnyRole("USER")
                 * .requestMatchers(HttpMethod.POST,"/api/products").hasRole("ADMIN")
                 * .requestMatchers(HttpMethod.PUT,"/api/products/{id}").hasRole("ADMIN")
                 * .requestMatchers(HttpMethod.DELETE,"/api/products/{id}").hasRole("ADMIN")
                 */
                .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/users").permitAll()
                .requestMatchers("/actuator/circuitbreakers","/actuator/circuitbreakerevents","/actuator/health").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()
                .anyRequest().authenticated())
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationManager()))
                .csrf(config -> config.disable())
                // .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(man -> man.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
