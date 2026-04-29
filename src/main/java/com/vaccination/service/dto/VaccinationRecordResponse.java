package com.vaccination.service.dto;

import com.vaccination.service.entity.enums.DoseNumber;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VaccinationRecordResponse {
    private UUID id;
    private UUID userId;
    private DoseNumber doseNumber;
    private String vaccineType;
    private LocalDate vaccinationDate;
    private String center;
    private LocalDateTime createdAt;
}
