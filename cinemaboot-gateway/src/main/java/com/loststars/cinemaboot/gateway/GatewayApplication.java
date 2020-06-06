package com.loststars.cinemaboot.gateway;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.loststars.cinemaboot.api.user.UserServiceAPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableHystrixDashboard
@EnableCircuitBreaker
@EnableHystrix
@EnableDubbo
public class GatewayApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext run =  SpringApplication.run(GatewayApplication.class, args);
	}
}