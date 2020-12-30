package xyz.lana.lanaclient.client;

import org.springframework.cloud.openfeign.FeignClient;
import xyz.lana.lanaclient.config.FeignServiceConfiguration;

@FeignClient(name = "lana-client", url = "${lana-server.url}", configuration = FeignServiceConfiguration.class)
public interface LanaClient {
}
