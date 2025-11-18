package com.example.EcmUploadApplication;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/documents/upload").hasRole("ICN_USER")
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer().jwt();
    }
}
