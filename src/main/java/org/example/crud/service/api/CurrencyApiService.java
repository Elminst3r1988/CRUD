package org.example.crud.service.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.crud.client.currencyapi.CurrencyApiClient;
import org.example.crud.dto.PairRateDTO;
import org.example.crud.dto.currencyapi.CurrencyApiDTO;
import org.example.crud.properties.PropertyConfig;
import org.example.crud.service.CurrencyClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyApiService implements CurrencyClientService {
    @Autowired
    private PropertyConfig propertyConfig;
    @Autowired
    private CurrencyApiClient currencyApiClient;

    public CurrencyApiDTO getRatesFromCurrencyApi(String firstCurrency, String secondCurrency) {
        return currencyApiClient.getData(firstCurrency, secondCurrency, propertyConfig.getCurrencyApiKey());
    }

    @Override
    public PairRateDTO getData(String firstCurrency, String secondCurrency) {
        PairRateDTO pairRateDTO = new PairRateDTO();
        pairRateDTO.setSiteName("currencyapi");
        getRatesFromCurrencyApi(firstCurrency, secondCurrency).getData().values().stream().findFirst().ifPresent(currencyData -> {
            pairRateDTO.setValue(currencyData.getValue());
        });
        return pairRateDTO;
    }
}
