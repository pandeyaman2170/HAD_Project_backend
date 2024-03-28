package com.example.teleconsultationbackend.Config;

import com.example.teleconsultationbackend.Filter.JwtFilter;
import com.example.teleconsultationbackend.Service.UserAuthenticationService;
import org.apache.http.HttpHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Configuration
public class SecurityConfiguration {

    private final UserAuthenticationService userService;

    private final JwtFilter jwtFilter;

    private final JwtEntryPoint jwtEntryPoint;

    public SecurityConfiguration(UserAuthenticationService userService, JwtFilter jwtFilter, JwtEntryPoint jwtEntryPoint) {
        this.userService = userService;
        this.jwtFilter = jwtFilter;
        this.jwtEntryPoint = jwtEntryPoint;
    }


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    @SuppressWarnings("removal")
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeHttpRequests((authorize) -> {
//                    authorize.requestMatchers("/global_admin/getGlobalAdminDetails/**").permitAll();
//                    authorize.requestMatchers("/**").permitAll();
                    authorize.requestMatchers("/authenticate/**").permitAll();
                    authorize.requestMatchers("/addPrescription/**").hasRole("DOCTOR");
                    authorize.requestMatchers("/addPrescription/**").hasRole("PATIENT");
                    authorize.requestMatchers("/global_admin/login/**").permitAll();
                    authorize.requestMatchers("/global_admin/**").hasRole("GLOBALADMIN");
//                    authorize.requestMatchers("/login/**").permitAll();
//                    authorize.requestMatchers("/patient/addPatient/**").permitAll();
//                    authorize.requestMatchers("/doctor/registerDoctor/**").permitAll();
                    authorize.anyRequest().authenticated();


                })
                .exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();


    }
//    @SuppressWarnings("removal")
//    @Bean
//    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http.csrf().disable()
//                .authorizeHttpRequests((authorize) -> {
//                    authorize.requestMatchers()
//                            .mvcMatchers("/OnlineDoctors/totalOnline").permitAll()
//                            .mvcMatchers("/consultation/getAllConsultationsCount/**").permitAll()
//                            .mvcMatchers("/authenticate/**").permitAll()
//                            .mvcMatchers("/login/**").permitAll()
//                            .mvcMatchers("/department/**").permitAll()
//                            .mvcMatchers("/doctor/registerDoctor/**").permitAll()
//                            .mvcMatchers("/patient/addPatient/**").permitAll()
//                            .anyRequest().authenticated();
//                });
//
//        http.exceptionHandling().authenticationEntryPoint(jwtEntryPoint);
//
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setExposedHeaders(Collections.singletonList("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}