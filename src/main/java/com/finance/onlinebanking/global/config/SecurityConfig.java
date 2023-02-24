package com.finance.onlinebanking.global.config;


import com.finance.onlinebanking.domain.user.entity.Role;
import com.finance.onlinebanking.global.config.security.CustomAccessDeniedHandler;
import com.finance.onlinebanking.global.config.security.CustomAuthenticationEntryPoint;
import com.finance.onlinebanking.global.config.security.jwt.JwtSecurityConfig;
import com.finance.onlinebanking.global.config.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers("/h2-console/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        final String PREFIX_URL = "/api/v1";

        http.cors();
        http.csrf().disable();
        http.httpBasic().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.formLogin().disable()
                .logout().disable();

        http.exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler);

        http.apply(new JwtSecurityConfig(tokenProvider));

        http.authorizeRequests()
                // User
                .antMatchers(HttpMethod.POST,  PREFIX_URL + "/users").anonymous()
                .antMatchers( PREFIX_URL + "/users/**").authenticated()
                // Auth
                .antMatchers(HttpMethod.POST, PREFIX_URL + "/auth/login").anonymous()
                .antMatchers(HttpMethod.POST, PREFIX_URL + "/auth/logout").authenticated()
                // Passbook
                .antMatchers(PREFIX_URL + "/passbooks/**").authenticated()
                // Transaction
                .antMatchers( PREFIX_URL + "/transactions/**").authenticated()
                // Product
                .antMatchers(HttpMethod.POST, PREFIX_URL + "/products/**").hasAuthority(Role.ADMIN.toString())
                // Bank
                .antMatchers(HttpMethod.POST, PREFIX_URL + "/banks/**").hasAuthority(Role.ADMIN.toString())
                // Else
                .anyRequest().permitAll();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
