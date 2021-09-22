package com.evertix.masterregister.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class SaveRequestRequest {
    private String description;
    private LocalDate date;
    private BigDecimal cash;
    private String typeRequest;
}
