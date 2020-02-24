package com.santander.userregistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import brave.sampler.Sampler;

@EnableFeignClients("com.santander.userregistration")
@EnableDiscoveryClient
@EnableHystrix
@SpringBootApplication
@EnableCaching
public class UserRegistrationApplication { 

	public static void main(String[] args) {
		SpringApplication.run(UserRegistrationApplication.class, args);
	}
	
	@Bean
	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
