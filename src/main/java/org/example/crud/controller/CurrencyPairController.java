package org.example.crud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.crud.dto.CompareRateDTO;
import org.example.crud.properties.PropertyConfig;
import org.example.crud.service.RatesProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(CurrencyPairController.class);


    @GetMapping("/currencies")
    public List<String> getCurrencies() {
        return propertyConfig.getNames();
    }

    @PostMapping("/calculate")
    public ResponseEntity<?> calculate(@RequestBody Map<String, String> request) throws JsonProcessingException {
        String firstCurrency = request.get("firstCurrency");
        String secondCurrency = request.get("secondCurrency");

        logger.info("Received request to calculate rates for {} and {}", firstCurrency, secondCurrency);

        List<CompareRateDTO> rates = ratesProcessingService.getRangedRates(firstCurrency, secondCurrency);

        logger.info("Calculated rates for {} and {}: {}", firstCurrency, secondCurrency, rates);

        return ResponseEntity.ok(rates);
    }
}
