package com.project.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//@EnableJpaRepositories(basePackages = "com.project.bookstore.dao")
@SpringBootApplication
@EnableWebMvc
public class BookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedHeaders("*");
//								.allowedOrigins("https://main.dg6br18dfokcj.amplifyapp.com")
//								.allowedHeaders("Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method",
//												"Access-Control-Request-Headers", "Access-Control-Allow-Origin")
//								.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
//								.allowCredentials(true).maxAge(3600);
			}
		};
	}

	@Bean
	public PasswordEncoder encoder(){
		return new BCryptPasswordEncoder();
	}

}
