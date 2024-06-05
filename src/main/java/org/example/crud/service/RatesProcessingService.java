package org.example.crud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.crud.dto.CompareRateDTO;
import org.example.crud.dto.PairRateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RatesProcessingService {

    @Autowired
    private List<CurrencyClientService> currencyClientServices;

    public List<CompareRateDTO> getRangedRates(String firstCurrency, String secondCurrency) throws JsonProcessingException {

        List<CompareRateDTO> compareRateDTOS = new ArrayList<>();
        List<PairRateDTO> pairRateDTOS = new ArrayList<>();

        for (CurrencyClientService currencyClientService : currencyClientServices) {
            pairRateDTOS.add(currencyClientService.getData(firstCurrency, secondCurrency));
        }

        for (int i = 0; i < pairRateDTOS.size(); i++) {
            for (int j = 0; j < pairRateDTOS.size(); j++) {
                if (i != j) {
                    CompareRateDTO compareRateDTO = new CompareRateDTO();
                    compareRateDTO.setFirstSiteName(pairRateDTOS.get(i).getSiteName());
                    compareRateDTO.setFirstValue(pairRateDTOS.get(i).getValue());
                    compareRateDTO.setSecondSiteName(pairRateDTOS.get(j).getSiteName());
                    compareRateDTO.setSecondValue(pairRateDTOS.get(j).getValue());

                    double differencePercent = (100 - (compareRateDTO.getFirstValue() / compareRateDTO.getSecondValue()) * 100);
                    differencePercent = Math.round(differencePercent * 1000.0) / 1000.0;

                    compareRateDTO.setDifferencePercent(differencePercent);
                    if (differencePercent > 0) {
                        compareRateDTOS.add(compareRateDTO);
                    }
                }
            }
        }
        compareRateDTOS.sort((o1, o2) -> Double.compare(o2.getDifferencePercent(), o1.getDifferencePercent()));
        return compareRateDTOS;
    }
}
