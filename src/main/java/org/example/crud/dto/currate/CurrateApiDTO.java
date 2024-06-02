package org.example.crud.dto.currate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Optional;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrateApiDTO {
    private int status;
    private String message;
    private Map<String, String> data;
}
