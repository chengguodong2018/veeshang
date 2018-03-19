package com.vee.shang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.vee.shang.mapper")
@EnableTransactionManagement
@EnableCaching
@EnableScheduling
public class VeeShangApplication {

	public static void main(String[] args) {
		SpringApplication.run(VeeShangApplication.class, args);
	}

}
