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
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/actuator/health"
                        ).permitAll()
                        .requestMatchers(POST, "/api/auth/bootstrap/admin").permitAll()
                        .requestMatchers(POST, "/api/auth/dashboard/login").permitAll()
                        .requestMatchers(POST, "/api/auth/ecommerce/login").permitAll()
                        .requestMatchers(POST, "/api/auth/ecommerce/register").permitAll()
                        .requestMatchers(GET, "/api/auth/dashboard/users").hasRole("ADMIN")
                        .requestMatchers(POST, "/api/auth/dashboard/users").hasRole("ADMIN")
                        .requestMatchers(POST, "/api/payments/infinitepay/checkouts").permitAll()
                        .requestMatchers(POST, "/api/payments/webhooks/infinitepay").permitAll()
                        .requestMatchers(GET, "/api/customers/**").hasAnyRole("ADMIN", "CUSTOMER")
                        .requestMatchers(PUT, "/api/customers/**").hasAnyRole("ADMIN", "CUSTOMER")
                        .requestMatchers(POST, "/api/_temp/bootstrap/admin").permitAll()
                        .requestMatchers(POST, "/api/_temp/bootstrap/dashboard/users").hasRole("ADMIN")
                        .requestMatchers(OPTIONS, "/**").permitAll()
                        .requestMatchers(GET, "/api/sellable-stocks/**").permitAll()
                        .requestMatchers(POST, "/api/sales-orders").hasAnyRole("ADMIN", "EMPLOYEE")
                        .requestMatchers(GET, "/api/sales-orders/**").hasAnyRole("ADMIN", "EMPLOYEE")
                        .requestMatchers(GET, "/api/admin/sellable-stocks/**").hasRole("ADMIN")
                        .requestMatchers(PUT, "/api/admin/sellable-stocks/**").hasRole("ADMIN")
                        .requestMatchers("/api/partners/**").hasRole("ADMIN")
                        .requestMatchers("/api/financial/**", "/api/financial-closings/**").hasRole("ADMIN")
                        .requestMatchers(
                                "/api/dashboard/**",
                                "/api/items/**",
                                "/api/lots/**",
                                "/api/operational-costs/**",
                                "/api/operational-losses/**",
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
