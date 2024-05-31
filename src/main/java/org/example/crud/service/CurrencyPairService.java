package org.example.crud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.crud.dto.UnifiedDTO;
import org.example.crud.dto.currencyapi.CurrencyApiDTO;
import org.example.crud.dto.openexchangerates.OpenExchangeRatesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.example.crud.dto.currate.CurrateApiDTO;
import org.example.crud.properties.PropertyConfig;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.Double.parseDouble;


@Service
public class CurrencyPairService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    PropertyConfig propertyConfig;


    public UnifiedDTO getRatesFromCurrate(String firstCurrency, String secondCurrency) throws JsonProcessingException {
        String apiKey = System.getenv("currate.ru");
//        Дата не работает на сайте =(
//        LocalDateTime midnightDateTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
//        String formattedDateTime = midnightDateTime.format(formatter);
        String url = propertyConfig.getUrls().get("currate")
                + firstCurrency
                + secondCurrency
                + "&key="
                + apiKey;

        String jsonResponse = restTemplate.getForObject(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        CurrateApiDTO currateApiDTO = objectMapper.readValue(jsonResponse, CurrateApiDTO.class);
        UnifiedDTO unifiedDTO = new UnifiedDTO();
        unifiedDTO.setSiteName("currate");

        currateApiDTO.getData().forEach((key, value) -> {
            unifiedDTO.setValue(parseDouble(value));
        });

        return unifiedDTO;
    }

    public UnifiedDTO getRatesFromCurrencyApi(String firstCurrency, String secondCurrency)
            throws JsonProcessingException {
        String apikey = System.getenv("app.currencyapi.com");
        String url = propertyConfig.getUrls().get("currencyapi") + "base_currency=" + firstCurrency +
                "&currencies[]=" + secondCurrency + "&apikey=" + apikey;

        String jsonResponse = restTemplate.getForObject(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        CurrencyApiDTO currencyApiDTO = objectMapper.readValue(jsonResponse, CurrencyApiDTO.class);
        UnifiedDTO unifiedDTO = new UnifiedDTO();
        unifiedDTO.setSiteName("currencyapi");
        currencyApiDTO.getData().values().stream().findFirst().ifPresent(currencyData -> {
            unifiedDTO.setValue(currencyData.getValue());
        });
        return unifiedDTO;
    }

    public UnifiedDTO getRatesFromOpenExchangeRate(String firstCurrency, String secondCurrency) throws JsonProcessingException {
        String apikey = System.getenv("openexchangerates.org");
        String url = propertyConfig.getUrls().get("openexchange")
                + firstCurrency + "&symbols=" + secondCurrency + "&app_id=" + apikey;

        String jsonResponse = restTemplate.getForObject(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        OpenExchangeRatesDTO openExchangeRatesDTO = objectMapper.readValue(jsonResponse, OpenExchangeRatesDTO.class);
        UnifiedDTO unifiedDTO = new UnifiedDTO();
        unifiedDTO.setSiteName("openexchange");
        openExchangeRatesDTO.getRates().forEach((key, value) -> {
            unifiedDTO.setValue(parseDouble(value));
        });
        return unifiedDTO;
    }
}




