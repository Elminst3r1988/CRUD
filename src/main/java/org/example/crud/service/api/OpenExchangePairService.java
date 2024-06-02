package org.example.crud.service.api;

import org.example.crud.client.exchangerateapi.OpenExchangeRatesApiClient;
import org.example.crud.dto.PairRateDTO;
import org.example.crud.dto.openexchangerates.OpenExchangeRatesDTO;
import org.example.crud.service.CurrencyClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.crud.properties.PropertyConfig;

import static java.lang.Double.parseDouble;


@Service
public class OpenExchangePairService implements CurrencyClientService {
    @Autowired
    private PropertyConfig propertyConfig;
    @Autowired
    private OpenExchangeRatesApiClient openExchangeRatesApiClient;

    public OpenExchangeRatesDTO getExchangeRates(String base, String symbols) {
        return openExchangeRatesApiClient.getData(base, symbols, propertyConfig.getOpenExchangeApiKey());
    }

    @Override
    public PairRateDTO getData(String firstCurrency, String secondCurrency) {
        PairRateDTO pairRateDTO = new PairRateDTO();
        pairRateDTO.setSiteName("openexchange");
        getExchangeRates(firstCurrency, secondCurrency).getRates().forEach((key, value) -> {
            pairRateDTO.setValue(parseDouble(value));
        });
        return pairRateDTO;
    }
}




