package com.voteroid.SAG;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.voteroid.SAG.configurations.SystemConfiguration;

@SpringBootApplication
@EnableDiscoveryClient
public class SagApplication {

	public static void main(String[] args) {
		SpringApplication.run(SagApplication.class, args);
	}

}
