package xyz.lana.lanaclient.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class of configuration of feign client
 */
@Configuration
public class FeignServiceConfiguration {

    @Value("${lana-server.security.username}")
    private String securityUsername;

    @Value("${lana-server.security.password}")
    private String securityPassword;

    /**
     * Configuration of basic auth
     *
     * @return basic auth interceptor
     */
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {

        return new BasicAuthRequestInterceptor(securityUsername, securityPassword);
    }
}
