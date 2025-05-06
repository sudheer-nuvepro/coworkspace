//package com.nuvepro.coworkspacebooking.Service;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig {
//
////    @Autowired
////    private CustomEmployeeDetailsService userDetailsService;
//
//    @Bean
//    public UserDetailsService userDetailsService(){
//        return new CustomUserDetailsService();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder1() {
//        return new BCryptPasswordEncoder();
//    }
//    @Autowired
//    private OAuthLoginSuccessHandler oAuthLoginSuccessHandler;
//
//    @Autowired
//    private CustomOAuth2UserService oauthUserService;
//
//    @Autowired
//    private DatabaseLoginSuccessHandler databaseLoginSuccessHandler;
//
//    public DaoAuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService());
//        authProvider.setPasswordEncoder(passwordEncoder1());
//        return authProvider;
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
//
//        System.out.println("Hii from Websecurityconfig");
//
//        return http.authorizeRequests()
//                .requestMatchers(HttpMethod.GET,"/public/**").permitAll()
//                .requestMatchers(HttpMethod.GET,"/protected/**").hasAnyAuthority("ADMIN","USER")
//                .requestMatchers(HttpMethod.GET,"/private/**").hasAuthority("ADMIN")
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .failureUrl("/login")
//                .loginProcessingUrl("/admin_login")
//                .defaultSuccessUrl("/")
//                .permitAll()
//                .successHandler(databaseLoginSuccessHandler)
//                .and()
//                .authorizeRequests()
//                .requestMatchers("/oauth/**").permitAll()
//                .and()
//                .oauth2Login().loginPage("/login").userInfoEndpoint()
//                .userService(oauthUserService).and()
//                .successHandler(oAuthLoginSuccessHandler)
//                .and().cors()
//                .and()
//                .csrf()
//                .disable().build();
//
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder1());
//    }
//
//}
