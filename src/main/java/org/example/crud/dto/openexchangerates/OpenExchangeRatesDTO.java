package org.example.crud.dto.openexchangerates;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;
import java.util.Optional;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenExchangeRatesDTO {
    @JsonProperty("rates")
    private Map<String, String> rates;
}
