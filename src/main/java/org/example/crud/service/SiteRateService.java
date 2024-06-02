package org.example.crud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.crud.dto.UnifiedDTO;
import org.springframework.stereotype.Service;

@Service
public interface SiteRateService {
    UnifiedDTO getData(String sieName, String secondCurrency) throws JsonProcessingException;
}
