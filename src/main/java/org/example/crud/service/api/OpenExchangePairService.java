package org.example.crud.service.api;

import org.example.crud.client.exchangerateapi.OpenExchangeRatesApiClient;
import org.example.crud.dto.UnifiedDTO;
import org.example.crud.dto.openexchangerates.OpenExchangeRatesDTO;
import org.example.crud.service.SiteRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.crud.properties.PropertyConfig;

import static java.lang.Double.parseDouble;


@Service
public class OpenExchangePairService implements SiteRateService {
    @Autowired
    private PropertyConfig propertyConfig;
    @Autowired
    private OpenExchangeRatesApiClient openExchangeRatesApiClient;

    public OpenExchangeRatesDTO getExchangeRates(String base, String symbols) {
        return openExchangeRatesApiClient.getData(base, symbols, propertyConfig.getOpenExchangeApiKey());
    }

    @Override
    public UnifiedDTO getData(String firstCurrency, String secondCurrency) {
        UnifiedDTO unifiedDTO = new UnifiedDTO();
        unifiedDTO.setSiteName("openexchange");
        getExchangeRates(firstCurrency, secondCurrency).getRates().forEach((key, value) -> {
            unifiedDTO.setValue(parseDouble(value));
        });
        return unifiedDTO;
    }
}




