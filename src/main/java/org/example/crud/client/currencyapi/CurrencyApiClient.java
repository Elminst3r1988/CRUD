package org.example.crud.client.currencyapi;

import org.example.crud.dto.currate.CurrateApiDTO;
import org.example.crud.dto.currencyapi.CurrencyApiDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "currateApiClient", url = "https://api.currencyapi.com/v3/latest")
public interface CurrencyApiClient {
    @GetMapping
    CurrencyApiDTO getData(@RequestParam("base_currency") String baseCurrency,
                           @RequestParam("currencies[]") String pairs,
                           @RequestParam("apikey") String apiKey
    );


}
