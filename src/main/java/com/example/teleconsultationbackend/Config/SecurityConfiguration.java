package com.example.teleconsultationbackend.Config;

import com.example.teleconsultationbackend.Filter.JwtFilter;
import com.example.teleconsultationbackend.Service.UserAuthenticationService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
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
import java.util.List;

@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"

)
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
        http.cors(corsConfig ->corsConfig.configurationSource(corsConfigurationSource()));

        http.csrf().disable()
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers("/**").permitAll();
                    authorize.requestMatchers("/authenticate/**").permitAll();
                    authorize.requestMatchers("/prescription/addPrescription/**").hasRole("DOCTOR");
                    authorize.requestMatchers("/prescription/getPrescriptionsPatient/{patientId}/**").hasRole("PATIENT");
                    authorize.requestMatchers("/prescription/getPrescriptionsDoctor/{patientId}/**").hasRole("DOCTOR");
                    authorize.requestMatchers("/patient/**").hasRole("PATIENT");
                    authorize.requestMatchers("/addPrescription/**").hasRole("DOCTOR");
                    authorize.requestMatchers("/addPrescription/**").hasRole("PATIENT");
                    authorize.requestMatchers("/global_admin/login/**").permitAll();
//                    authorize.requestMatchers("/global_admin/**").hasRole("GLOBALADMIN");
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
//        CorsConfiguration config = new CorsConfiguration();
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        config.addAllowedMethod("OPTIONS");
//        config.setExposedHeaders(Collections.singletonList("Authorization"));
//        config.setAllowedHeaders(List.of("Content-Type", "text/plain","Authorization"));
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//        return source;
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:3001", "http://localhost:3002", "http://localhost:3003"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("Content-Type", "Authorization"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}