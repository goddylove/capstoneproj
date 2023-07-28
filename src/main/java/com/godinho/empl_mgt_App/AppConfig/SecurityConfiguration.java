package com.godinho.empl_mgt_App.AppConfig;

import com.godinho.empl_mgt_App.repo.DepartmentRepo;
import com.godinho.empl_mgt_App.service.DepartmentService;
import com.godinho.empl_mgt_App.service.DepartmentServiceImpl;
import com.godinho.empl_mgt_App.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
@ComponentScan(basePackages = "com.godinho.empl_mgt_App")
public class SecurityConfiguration{


    // We will create userService class in upcoming step
    @Autowired
    private UserService userService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().requestMatchers(
                        "/signup**",
                        "/js/**",
                        "/static/css/**",
                        "/img/**",
                        "/webjars/**").permitAll().anyRequest().authenticated().and().formLogin()
                .loginPage("/login").permitAll().and().logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout").permitAll();
        return http.build();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

}