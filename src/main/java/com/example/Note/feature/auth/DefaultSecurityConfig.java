package com.example.Note.feature.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@RequiredArgsConstructor
@EnableWebSecurity
public class DefaultSecurityConfig {
    private final CustomAuthProvider authProvider;


    @Bean
    public SecurityFilterChain FilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/note/*")
                .authorizeRequests(authorize -> authorize
                        .anyRequest().hasAnyRole("USER", "ADMIN")
                )
                .httpBasic(withDefaults());
        return http.build();
    }





//    @Bean
//    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//        http
////                    .authorizeRequests()
////                    .antMatchers("/api/v1/**").permitAll()  // от що це все таке?
////                .and()
//                .authorizeRequests()
//                    .anyRequest()
//                    .authenticated()
//                .and()
//                    .httpBasic()
//                .and()
//                    .formLogin(Customizer.withDefaults());
//        return http.build();
//    }

    @Autowired   // AuthenticationManagerBuilder - звідки брати юзерів, і як їх можно автинтифікувати: по логіну і паролю, чи по смс
    public void injectCustomAuthProvider(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }
}