package org.example.crud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.crud.dto.PairRateDTO;
import org.example.crud.dto.UnifiedDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RatesProcessingService {

    @Autowired
    private List<SiteRateService> siteRateServices;

    public List<PairRateDTO> getRangedRates(String firstCurrency, String secondCurrency) throws JsonProcessingException {

        List<PairRateDTO> pairRateDTOS = new ArrayList<>();
        List<UnifiedDTO> unifiedDTOS = new ArrayList<>();

        for (SiteRateService siteRateService : siteRateServices) {
            unifiedDTOS.add(siteRateService.getData(firstCurrency, secondCurrency));
        }

        for (int i = 0; i < unifiedDTOS.size(); i++) {
            for (int j = 0; j < unifiedDTOS.size(); j++) {
                if (i != j) {
                    PairRateDTO pairRateDTO = new PairRateDTO();
                    pairRateDTO.setFirstSiteName(unifiedDTOS.get(i).getSiteName());
                    pairRateDTO.setFirstValue(unifiedDTOS.get(i).getValue());
                    pairRateDTO.setSecondSiteName(unifiedDTOS.get(j).getSiteName());
                    pairRateDTO.setSecondValue(unifiedDTOS.get(j).getValue());

                    double differencePercent = (100 - (pairRateDTO.getFirstValue() / pairRateDTO.getSecondValue()) * 100);
                    differencePercent = Math.round(differencePercent * 1000.0) / 1000.0;

                    pairRateDTO.setDifferencePercent(differencePercent);
                    pairRateDTOS.add(pairRateDTO);
                }
            }
        }
        pairRateDTOS.sort((o1, o2) -> Double.compare(o2.getDifferencePercent(), o1.getDifferencePercent()));
        return pairRateDTOS;
    }
}
