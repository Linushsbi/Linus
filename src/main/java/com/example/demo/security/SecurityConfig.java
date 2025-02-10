package com.example.demo.security;

// Author: Delbrin Alazo
// Created: 2024-12-07
// Last Updated: 2024-12-07
// Modified by: Delbrin Alazo
// Description: Configuration for Spring Security

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.views.login.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder für sichere Passwort-Hashing wird im
        // MitgliedDetailsService implementiert
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Allow CSS Files
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(new AntPathRequestMatcher("/**/*.css")).permitAll());

        // Allow pictures
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(new AntPathRequestMatcher("/images/*.png")).permitAll());

        // Allow SVC Icons
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(new AntPathRequestMatcher("/line-awesome/**/*.svg")).permitAll());

        // Vaadin-Integration and Login-View
        setLoginView(http, LoginView.class);

        http.formLogin(form -> form
                .loginPage("/login")
                .successHandler(customAuthenticationSuccessHandler)
                .permitAll());

        // overwrite default config, to avoid errors.
        super.configure(http);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Vaadin-spezifische statische Ressourcen werden von Spring Security ignoriert.
        // Können sonst ggf. blockiert werden
        web.ignoring().requestMatchers(VaadinWebSecurity.getDefaultWebSecurityIgnoreMatcher());
        super.configure(web);
    }
}

/* -mit dieser Config besiegt man unteranderem den fehler beim NutitionPlanView. Ebenfalls war ein Convert von Long zu string ein riesen problem das wurde aber gelöst am 10.02 um 9:25 Uhr. Sobald man oben die Klasse bearbeitet startet das Programm nicht mehr. Dachte Lange-
das es an dieser Klasse nicht liegen kann bis ich aus verzweifelung heute morgen einfach mal alles erlaubt habe. Dann gings. Ärgerlich das es nicht fertig geowrden ist aber ich bin froh nach so vielen Stunden endlich meinen NutitionPlanView wieder gesehen zu haben.
 * 
 * package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.vaadin.flow.spring.security.AuthenticationContext;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // CSRF-Schutz deaktivieren (nur für Tests!)
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()  // Alle Anfragen erlauben
            );

        return http.build();
    }

    // ✅ Diese Bean stellt sicher, dass AuthenticationContext verfügbar ist
    @Bean
    public AuthenticationContext authenticationContext() {
        return new AuthenticationContext();
    }
} 
*/