package com.loststars.cinemaboot.field;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
@MapperScan("com.loststars.cinemaboot.field.dao")
@EnableTransactionManagement
@EnableDubbo
public class FieldApplication {

	public static void main(String[] args) {
		SpringApplication.run(FieldApplication.class, args);
	}
}
