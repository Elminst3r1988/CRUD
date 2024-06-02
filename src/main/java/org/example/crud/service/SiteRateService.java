package org.example.crud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.crud.dto.UnifiedDTO;
import org.springframework.stereotype.Service;

@Service
public interface SiteRateService {
    UnifiedDTO getData(String firstCurrency, String secondCurrency) throws JsonProcessingException;
}
