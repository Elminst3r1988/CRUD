package org.example.crud.dto.currate;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Optional;

@Data
public class CurrateApiDTO {
    private int status;
    private String message;
    private Map<String, String> data;


    public Optional<String> getDataKey() {
        return data.keySet().stream().findFirst();
    }

    public Optional<String> getDataValue() {
        return data.values().stream().findFirst();
    }
}
