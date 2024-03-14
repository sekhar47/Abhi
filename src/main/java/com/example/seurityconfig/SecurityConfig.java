package com.example.seurityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.serviceauth.CustomFailureHandler;
import com.example.serviceauth.CustomSuccessHandler;
import com.example.serviceauth.CustomUserDetailsService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    public CustomSuccessHandler customSuccessHandler;

    @Autowired
    public CustomFailureHandler failureHandler;
    
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
System.out.println("Entered into security config");
        http.csrf(c -> c.disable())

            .authorizeHttpRequests(request -> request
                .requestMatchers("/admin-page").hasAuthority("ADMIN")
                .requestMatchers("/user-page").hasAuthority("USER")
                .requestMatchers("/superadmin-page","/privilage","/manage-employees","/deleteEmployees").hasAuthority("SADMIN")
                .requestMatchers("user-page","/registration", "/forgot-password", "/reset-password", "/confirm-account", "/superadmin-page", "/", "/css/**").permitAll()
                .anyRequest().authenticated())

            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                 .failureHandler(failureHandler)
                .successHandler(customSuccessHandler)
                .permitAll())

            .logout(form -> form
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll());

        return http.build();

    }

//    @Autowired
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
//    }
}
