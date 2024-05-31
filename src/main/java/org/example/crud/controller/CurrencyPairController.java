package org.example.crud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.crud.dto.RateProcessDTO;
import org.example.crud.dto.UnifiedDTO;
import org.example.crud.dto.currate.CurrateApiDTO;
import org.example.crud.dto.currencyapi.CurrencyApiDTO;
import org.example.crud.properties.PropertyConfig;
import org.example.crud.service.CurrencyPairService;
import org.example.crud.service.RatesProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trade")
public class CurrencyPairController {
    @Autowired
    private RatesProcessingService ratesProcessingService;
    @Autowired
    private PropertyConfig propertyConfig;


    @GetMapping("/currencies")
    public List<String> getCurrencies() {
        return propertyConfig.getNames();
    }

    @PostMapping("/calculate")
    public ResponseEntity<?> calculate(@RequestBody Map<String, String> request) throws JsonProcessingException {
        String firstCurrency = request.get("firstCurrency");
        String secondCurrency = request.get("secondCurrency");
        List<RateProcessDTO> rates = ratesProcessingService.getRangedRates(firstCurrency, secondCurrency);
        return ResponseEntity.ok(rates);
    }
}