package com.example.speedywheels.config;

import com.example.speedywheels.listener.CustomAuthenticationSuccessHandler;
import com.example.speedywheels.listener.CustomLogoutSuccessHandler;
import com.example.speedywheels.model.enums.Role;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    private final CustomLogoutSuccessHandler logoutSuccessHandler;

    public SecurityConfiguration(CustomAuthenticationSuccessHandler authenticationSuccessHandler, CustomLogoutSuccessHandler logoutSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(
                authorizeRequests -> authorizeRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/", "/user/login", "/user/register", "/user/login-error","/about","/contact-us/**","/error").permitAll()
                        .requestMatchers("/home","/vehicles/**","/cars/**","/motorcycles/**", "/comments/**","/user/**").hasRole(Role.USER.name())
                        .requestMatchers("/**").hasRole(Role.ADMIN.name())
                        .anyRequest().authenticated()
        ).formLogin(
                formLogin -> {
                    formLogin
                            .loginPage("/user/login")
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .successHandler(authenticationSuccessHandler)
                            .failureForwardUrl("/user/login-error");
                }
        ).logout(
                logout -> {
                    logout
                            .logoutUrl("/user/logout")
                            .logoutSuccessHandler(logoutSuccessHandler)
                            .logoutSuccessUrl("/")
                            .invalidateHttpSession(true)
                            .deleteCookies("JSESSIONID");
                }
        ).build();
    }
}
