package com.luizmedeirosn.futs3.configs;

import com.luizmedeirosn.futs3.security.jwt.JwtAuthFilter;
import com.luizmedeirosn.futs3.security.jwt.JwtService;
import com.luizmedeirosn.futs3.security.jwt.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final HandlerExceptionResolver handlerExceptionResolver;

    public SecurityConfig(
            JwtService jwtService,
            UserDetailsServiceImpl userDetailsServiceImpl,
            HandlerExceptionResolver handlerExceptionResolver
    ) {
        this.jwtService = jwtService;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/auth/signin", "/playerspictures/**").permitAll()
                                .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(Customizer.withDefaults())
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtService, userDetailsServiceImpl, handlerExceptionResolver);
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    UserDetailsService userDetailsService() {
        return userDetailsServiceImpl;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
