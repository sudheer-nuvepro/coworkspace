package com.nuvepro.coworkspacebooking.security;

import com.nuvepro.coworkspacebooking.security.filter.AuthenticationFilter;
import com.nuvepro.coworkspacebooking.security.filter.ExceptionHandlerFilter;
import com.nuvepro.coworkspacebooking.security.filter.JWTAuthorizationFilter;
import com.nuvepro.coworkspacebooking.security.manager.CustomAuthenticationManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private CustomAuthenticationManager customAuthenticationManager;



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter=new AuthenticationFilter(customAuthenticationManager);
        authenticationFilter.setFilterProcessesUrl("/authenticate");
        http
                .cors(Customizer.withDefaults())
                .headers().frameOptions().disable() // New Line: the h2 console runs on a "frame". By default, Spring Security prevents rendering within an iframe. This line disables its prevention.
                .and()
                .csrf().disable()
                .authorizeRequests()
                .requestMatchers("/h2/**").permitAll() // New Line: allows us to access the h2 console without the need to authenticate. ' ** '  instead of ' * ' because multiple path levels will follow /h2.
                .requestMatchers(HttpMethod.POST, SecurityConstants.REGISTER_PATH).permitAll() //Make signup req public to all
                .requestMatchers(new CustomRequestMatcher()).permitAll()
                .requestMatchers(HttpMethod.GET, "/checkout.js", "/checkout.css","/checkout.html").permitAll()
                .requestMatchers(HttpMethod.POST, "/create-payment-intent").permitAll()
//                .requestMatchers(HttpMethod.GET, "/favicon.ico").permitAll()
//                .requestMatchers(new CustomRequestMatcherPaymentIntent()).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class) //befor attemptAuthenticate method is invoked from AuthenticationFilter class.ExceptionHandlerFilter is called
                .addFilter(authenticationFilter)
                .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }


//    public class CustomRequestMatcherPaymentIntent implements RequestMatcher {
//        @Override
//        public boolean matches(HttpServletRequest request) {
//            String path = request.getServletPath();
//
//            // Check if the request matches the '/create-payment-intent' path and if it's a POST request
//            if (path.equals("/create-payment-intent") && "POST".equals(request.getMethod())) {
//                // Check if 'paymentAmount' is present in the request body as a parameter
//                String paymentAmount = request.getParameter("paymentAmount");
//                return paymentAmount != null;
//            }
//
//            return false;
//        }
//    }


    private static class CustomRequestMatcher implements RequestMatcher {
        @Override
        public boolean matches(HttpServletRequest request) {
            String path = request.getServletPath();
            String amount = request.getParameter("amount");
            String bookingId = request.getParameter("bookingId");

            // Customize the matching criteria based on your requirements
            // For example, you can use regular expressions or other conditions here
            return path.equals("/payment") && amount != null && bookingId != null;
        }
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type", "Access-Control-Allow-Origin", "Access-Control-Allow-Headers", "Origin", "Accept", "X-Requested-With", "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;



    }
}

