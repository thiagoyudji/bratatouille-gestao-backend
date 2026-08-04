// TODO move this class to src/main/java/br/com/bratatouille/management/auth/security/SecurityConfig.java when directory creation is available.
package br.com.bratatouille.management.auth.security;

import br.com.bratatouille.management.common.error.ApiAccessDeniedHandler;
import br.com.bratatouille.management.common.error.ApiAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpMethod.OPTIONS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter,
            ApiAuthenticationEntryPoint authenticationEntryPoint,
            ApiAccessDeniedHandler accessDeniedHandler
    ) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/**",
                                "/api/_temp/**",
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/actuator/health"
                        ).permitAll()
                        .requestMatchers(OPTIONS, "/**").permitAll()
                        .requestMatchers(GET, "/api/sellable-stocks/**").hasAnyRole("ADMIN", "EMPLOYEE", "CUSTOMER")
                        .requestMatchers(POST, "/api/sales-orders").hasAnyRole("ADMIN", "EMPLOYEE", "CUSTOMER")
                        .requestMatchers(GET, "/api/sales-orders/**").hasAnyRole("ADMIN", "EMPLOYEE")
                        .requestMatchers(PUT, "/api/sellable-stocks/**").hasAnyRole("ADMIN", "EMPLOYEE")
                        .requestMatchers("/api/financial/**", "/api/financial-closings/**").hasRole("ADMIN")
                        .requestMatchers(
                                "/api/dashboard/**",
                                "/api/items/**",
                                "/api/lots/**",
                                "/api/operational-costs/**",
                                "/api/operational-losses/**",
                                "/api/partners/**",
                                "/api/productions/**",
                                "/api/production-simulation/**",
                                "/api/purchases/**",
                                "/api/recipes/**",
                                "/api/sales-reports/**",
                                "/api/stocks/**",
                                "/api/zero-cost-entries/**"
                        ).hasAnyRole("ADMIN", "EMPLOYEE")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
