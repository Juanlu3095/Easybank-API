package com.jcooldevelopment.easybank_api.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.jcooldevelopment.easybank_api.contracts.enums.UserRole;
import com.jcooldevelopment.easybank_api.filter.JwtAuthFilter;
import com.jcooldevelopment.easybank_api.handler.AccessDeniedExceptionHandler;
import com.jcooldevelopment.easybank_api.handler.AuthorizationExceptionHandler;

@Configuration // This annotation will make Spring to see the beans
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authProvider;
    private final Environment env;

    public SecurityConfig(
        JwtAuthFilter jwtAuthFilter,
        AuthenticationProvider authProvider,
        Environment env
    ) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authProvider = authProvider;
        this.env = env;
    }

    // https://docs.spring.io/spring-security/reference/reactive/integrations/cors.html
    // https://www.geeksforgeeks.org/advance-java/spring-security-cors-configuration/
    // https://stackoverflow.com/questions/76682586/allow-cors-with-spring-security-6-1-1-with-authenticated-requests
    @Bean
    UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // This @Bean makes Spring execute the method, and it will use it when necesary. This method will be executed first because Spring
    // Security configuration. With SecurityFilterChain class in this method, Spring Security will use it for http filter.
    @Bean
    SecurityFilterChain securityFilterChain (HttpSecurity http) {
        http
            .csrf(csrf -> csrf.disable()) // By default, if not disabled, API will return 403 status code if there is no CSRF token in requests

            // https://stackoverflow.com/questions/30643029/spring-security-anonymous-401-instead-of-403
            .exceptionHandling(exception -> exception
                // For 403 exception, when authenticated user has no authority
                .accessDeniedHandler(new AccessDeniedExceptionHandler())
                // For 401 exception, when user is not authenticated
                .authenticationEntryPoint(new AuthorizationExceptionHandler())
            )

            // https://www.baeldung.com/spring-security-expressions
            .authorizeHttpRequests(authRequest -> // Lambda expression for more than one configuration for authorize requests
                authRequest

                    /* PUBLIC ENDPOINTS */
                    .requestMatchers(HttpMethod.POST,
                        // Activation code
                        "/api/activate/**",

                        // Auth
                        "/api/auth/login",
                        "/api/auth/register",
                        "/api/auth/forgot-password",
                        "/api/auth/reset-password/**",

                        // Message
                        "/api/message"
                    ).permitAll() // This routes will be public

                    /* PRIVATE ENDPOINTS */
                    // Both roles
                    .requestMatchers(HttpMethod.GET,
                        "/api/accounttype/**",
                        "/api/country/**",
                        "/api/incidence/**",
                        "/api/incidencetype/**",
                        "/api/operationreceipt/**"
                    ).authenticated()

                    // Client role
                    .requestMatchers(
                        "/api/client/account/**",
                        "/api/client/branch/**",
                        "/api/client/operation/**"
                    ).hasRole(UserRole.CLIENT.toString())

                    .requestMatchers(HttpMethod.GET,
                        "/error",
                        "/api/operationreceipt/**"
                    ).hasRole(UserRole.CLIENT.toString())

                    .requestMatchers(HttpMethod.POST,
                        "/api/auth/change-password",
                        "/api/user/pin"
                    ).hasRole(UserRole.CLIENT.toString())

                    .requestMatchers(HttpMethod.PUT,
                        "/"
                    ).hasRole(UserRole.CLIENT.toString())

                    .requestMatchers(HttpMethod.DELETE,
                        "/"
                    ).hasRole(UserRole.CLIENT.toString())

                    // Admin role
                    .requestMatchers(
                        "/api/admin/account/**",
                        "/api/admin/branch/**",
                        "/api/admin/operation/**"
                    ).hasRole(UserRole.ADMIN.toString())

                    .requestMatchers(HttpMethod.GET,
                        "/api/incidence",
                        "/api/message/**",
                        "/api/user/**"
                    ).hasRole(UserRole.ADMIN.toString())

                    .requestMatchers(HttpMethod.POST,
                        "/api/accounttype",
                        "/api/country",
                        "/api/incidencetype",
                        "/api/user"
                    ).hasRole(UserRole.ADMIN.toString())

                    .requestMatchers(HttpMethod.PUT,
                        "/api/accounttype/**",
                        "/api/country/**",
                        "/api/incidence/**",
                        "/api/incidencetype/**",
                        "/api/message/**",
                        "/api/user/**"
                    ).hasRole(UserRole.ADMIN.toString())

                    .requestMatchers(HttpMethod.DELETE,
                        "/api/accounttype/**",
                        "/api/country/**",
                        "/api/incidence/**",
                        "/api/incidencetype/**",
                        "/api/message/**",
                        "/api/user/**"
                    ).hasRole(UserRole.ADMIN.toString())
            )
            .sessionManagement(sessionManager ->
                sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            ) // Disables sessions
            .authenticationProvider(authProvider)
            .addFilterBefore(this.jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Executes the filter when a request is received

            if(this.env.getProperty("BANK.ENVIRONMENT").equals("DEVELOPMENT")){
                http.authorizeHttpRequests(authRequest ->
                    authRequest
                        .requestMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-ui.html",
                        "/api/accounttype/**",
                        "/api/country/**",
                        "/api/branch/**",
                        "/api/auth/forgot-password"
                    ).permitAll()
                );
            }

            return http.build();
            // JWT for authentication not authorization
    }
}
