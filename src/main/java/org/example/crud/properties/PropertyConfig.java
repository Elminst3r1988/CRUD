package org.example.crud.properties;

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
}