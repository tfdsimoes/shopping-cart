package xyz.lana.lanaclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("xyz.lana.lanaclient")
public class LanaClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(LanaClientApplication.class, args);
	}

}
