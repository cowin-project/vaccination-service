package com.vaccination.service.dto;

import com.vaccination.service.entity.enums.VaccinationState;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VaccinationStatusResponse {
    private UUID userId;
    private int totalDoses;
    private VaccinationState status;
    private List<VaccinationRecordResponse> records;
}
