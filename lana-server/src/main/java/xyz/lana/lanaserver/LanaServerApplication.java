package xyz.lana.lanaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class LanaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(LanaServerApplication.class, args);
    }
}
