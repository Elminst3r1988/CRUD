package org.example.crud.properties;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "property")
@Getter
@Setter
public class PropertyConfig {
    private List<String> names;
    private Map<String, String> urls;
    private String openExchangeApiKey;
    private String currencyApiKey;
    private String currateApiKey;
    private String adminPass;

    @PostConstruct
    public void init() {
        System.out.println("OpenExchangeApiKey: " + openExchangeApiKey);
        System.out.println("CurrencyApiKey: " + currencyApiKey);
        System.out.println("CurrateApiKey: " + currateApiKey);
        System.out.println("AdminPass: " + adminPass);
    }
}
