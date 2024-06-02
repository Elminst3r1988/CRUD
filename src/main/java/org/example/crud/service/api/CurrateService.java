package org.example.crud.service.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.crud.dto.PairRateDTO;
import org.example.crud.dto.currate.CurrateApiDTO;
import org.example.crud.properties.PropertyConfig;
import org.example.crud.service.CurrencyClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static java.lang.Double.parseDouble;

@Service
public class CurrateService implements CurrencyClientService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private PropertyConfig propertyConfig;

    public CurrateApiDTO getRatesFromCurrate(String firstCurrency, String secondCurrency) throws JsonProcessingException {
        String apiKey = System.getenv("currate.ru");
        String url = propertyConfig.getUrls().get("currate")
                + firstCurrency
                + secondCurrency
                + "&key="
                + apiKey;

        String jsonResponse = restTemplate.getForObject(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(jsonResponse, CurrateApiDTO.class);
    }


    @Override
    public PairRateDTO getData(String firstCurrency, String secondCurrency) throws JsonProcessingException {
        PairRateDTO pairRateDTO = new PairRateDTO();
        pairRateDTO.setSiteName("currate");

        getRatesFromCurrate(firstCurrency, secondCurrency).getData().forEach((key, value) -> {
            pairRateDTO.setValue(parseDouble(value));
        });

        return pairRateDTO;

    }
}
