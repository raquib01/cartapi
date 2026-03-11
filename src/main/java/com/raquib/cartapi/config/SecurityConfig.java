package com.raquib.cartapi.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        return http
                // ALWAYS: server creates HttpSession and stores it, in the server memory or db
                // and sends session id to the client as a cookie
                // client sends the cookie for every request, and the server authenticate the user
                // using session id from the cookie, no auth cred needed everytime

                // STATELESS: server does not create or stores any HttpSession
                // and expects jwt token or credential based login at every request
                .sessionManagement(c->c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // disabling csrf
                .csrf(c ->c.disable())

                //  conditions for authentication
                .authorizeHttpRequests(c-> c
                                .requestMatchers("/api/carts/**").authenticated()
                                .requestMatchers("/api/products/**").authenticated()
                                .requestMatchers(HttpMethod.GET,"/api/user/{id}").authenticated()
                                .anyRequest().permitAll())

                // enabling default login form
                .formLogin(Customizer.withDefaults())

                // enabling basic auth for postman
                .httpBasic(Customizer.withDefaults())

                .build();


    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
