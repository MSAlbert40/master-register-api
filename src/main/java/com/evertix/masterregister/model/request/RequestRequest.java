package com.evertix.masterregister.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RequestRequest {
    @NotNull(message = "Description cannot be null")
    @NotBlank(message = "Description cannot be blank")
    @Size(max = 200)
    private String description;
}
