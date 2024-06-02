package org.example.crud.service.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.crud.client.currencyapi.CurrencyApiClient;
import org.example.crud.dto.UnifiedDTO;
import org.example.crud.dto.currencyapi.CurrencyApiDTO;
import org.example.crud.properties.PropertyConfig;
import org.example.crud.service.SiteRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyApiService implements SiteRateService {
    @Autowired
    private PropertyConfig propertyConfig;
    @Autowired
    private CurrencyApiClient currencyApiClient;

    public CurrencyApiDTO getRatesFromCurrencyApi(String firstCurrency, String secondCurrency) {
        return currencyApiClient.getData(firstCurrency, secondCurrency, propertyConfig.getCurrencyApiKey());
    }

    @Override
    public UnifiedDTO getData(String firstCurrency, String secondCurrency) throws JsonProcessingException {
        UnifiedDTO unifiedDTO = new UnifiedDTO();
        unifiedDTO.setSiteName("currencyapi");
        getRatesFromCurrencyApi(firstCurrency, secondCurrency).getData().values().stream().findFirst().ifPresent(currencyData -> {
            unifiedDTO.setValue(currencyData.getValue());
        });
        return unifiedDTO;
    }
}
