package com.nuvepro.coworkspacebooking;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.lang.String;

@SpringBootApplication
//@EnableAutoConfiguration
//@ComponentScan(basePackages = "com.nuvepro.coworkspacebooking")
public class CoWorkspaceBookingApplication {
	@Value("${stripe.secret.key}")
	private String stripeApiKey;

	@PostConstruct
	public void setup(){
		Stripe.apiKey = stripeApiKey;
	}

	public static void main(String[] args) {

		SpringApplication.run(CoWorkspaceBookingApplication.class, args);

	}


	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {

		return new BCryptPasswordEncoder();
	}


}
