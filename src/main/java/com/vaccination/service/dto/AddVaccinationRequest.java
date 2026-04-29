package com.vaccination.service.dto;

import com.vaccination.service.entity.enums.DoseNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddVaccinationRequest {

    @NotNull
    private UUID userId;

    @NotNull
    private DoseNumber doseNumber;

    @NotBlank
    private String vaccineType;

    @NotNull
    private LocalDate date;

    @NotBlank
    private String center;
}
