package org.example.crud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.crud.dto.RateProcessDTO;
import org.example.crud.dto.UnifiedDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RatesProcessingService {
    @Autowired
    CurrencyPairService currencyPairService;

    public List<RateProcessDTO> getRangedRates(String firstCurrency, String secondCurrency) throws JsonProcessingException {
        List<UnifiedDTO> unifiedDTOS = new ArrayList<>();
        unifiedDTOS.add(currencyPairService.getRatesFromCurrate(firstCurrency, secondCurrency));
        unifiedDTOS.add(currencyPairService.getRatesFromCurrencyApi(firstCurrency, secondCurrency));
        unifiedDTOS.add(currencyPairService.getRatesFromOpenExchangeRate(firstCurrency, secondCurrency));

        List<RateProcessDTO> rateProcessDTOS = new ArrayList<>();
        for (int i = 0; i < unifiedDTOS.size(); i++) {
            for (int j = 0; j < unifiedDTOS.size(); j++) {
                if (i != j) {
                    RateProcessDTO rateProcessDTO = new RateProcessDTO();
                    rateProcessDTO.setFirstSiteName(unifiedDTOS.get(i).getSiteName());
                    rateProcessDTO.setFirstValue(unifiedDTOS.get(i).getValue());
                    rateProcessDTO.setSecondSiteName(unifiedDTOS.get(j).getSiteName());
                    rateProcessDTO.setSecondValue(unifiedDTOS.get(j).getValue());
                    double differencePercent = (rateProcessDTO.getSecondValue() - rateProcessDTO.getFirstValue()) * 100;
                    differencePercent = Math.round(differencePercent * 1000.0) / 1000.0;
                    rateProcessDTO.setDifferencePercent(differencePercent);
                    rateProcessDTOS.add(rateProcessDTO);
                }
            }
        }
        rateProcessDTOS.sort((o1, o2) -> Double.compare(o2.getDifferencePercent(), o1.getDifferencePercent()));
        return rateProcessDTOS;
    }
}
