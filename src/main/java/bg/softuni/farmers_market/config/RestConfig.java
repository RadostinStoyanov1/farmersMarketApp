package bg.softuni.farmers_market.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;

@Configuration
public class RestConfig {
    @Bean("offersRestClient")
    public RestClient offersRestClient(OfferApiConfig offersApiConfig) {
        return RestClient
                .builder()
                .baseUrl(offersApiConfig.getBaseUrl())
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
