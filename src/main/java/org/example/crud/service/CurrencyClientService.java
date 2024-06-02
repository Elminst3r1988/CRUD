package org.example.crud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.crud.dto.PairRateDTO;
import org.springframework.stereotype.Service;

@Service
public interface CurrencyClientService {
    PairRateDTO getData(String firstCurrency, String secondCurrency) throws JsonProcessingException;
}
