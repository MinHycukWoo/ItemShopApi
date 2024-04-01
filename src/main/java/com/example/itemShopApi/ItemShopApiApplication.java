package com.example.itemShopApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
//@EnableGlobalMethodSecurity(prePostEnabled = true) //2.6 x
@EnableMethodSecurity //3.1x
public class ItemShopApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemShopApiApplication.class, args);
	}

}
