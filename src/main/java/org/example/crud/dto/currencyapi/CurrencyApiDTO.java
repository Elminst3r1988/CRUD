package org.example.crud.dto.currencyapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyApiDTO {

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CurrencyData {
        @JsonProperty("code")
        private String code;

        @JsonProperty("value")
        private double value;
    }

    @JsonProperty("data")
    private Map<String, CurrencyData> data;
}
