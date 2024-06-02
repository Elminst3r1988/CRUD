package org.example.crud.dto;

import lombok.Data;

@Data
public class PairRateDTO {
    private String firstSiteName;
    private String secondSiteName;
    private double firstValue;
    private double secondValue;
    private double differencePercent;
}
