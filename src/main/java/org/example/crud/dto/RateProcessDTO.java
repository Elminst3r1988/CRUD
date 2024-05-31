package org.example.crud.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RateProcessDTO {
    private String firstSiteName;
    private String secondSiteName;
    private double firstValue;
    private double secondValue;
    private double differencePercent;
}
