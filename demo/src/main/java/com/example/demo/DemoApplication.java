package com.example.demo;


import com.example.demo.Services.CredentialsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients(basePackages = "com.example.demo.Clients")
@SpringBootApplication
public class DemoApplication
{

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		CredentialsService.configurePrivateKeyPath();
		CredentialsService.configureAppId();

		System.out.println("Your data has been saved!\nNow you can use the API.");
	}


}
