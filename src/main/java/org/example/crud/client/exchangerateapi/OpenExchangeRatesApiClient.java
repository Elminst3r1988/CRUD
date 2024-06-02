package org.example.crud.client.exchangerateapi;

import org.example.crud.dto.openexchangerates.OpenExchangeRatesDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "openExchangeRatesApiClient", url = "https://openexchangerates.org/api/latest.json")
public interface OpenExchangeRatesApiClient {

    @GetMapping
    OpenExchangeRatesDTO getData(@RequestParam("base") String base,
                                 @RequestParam("symbols") String symbols,
                                 @RequestParam("app_id") String app_id);

}
