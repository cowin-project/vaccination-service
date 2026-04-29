package com.vaccination.service.service;

import com.vaccination.service.dto.AddVaccinationRequest;
import com.vaccination.service.dto.VaccinationStatusResponse;
import java.util.UUID;

public interface VaccinationService {
    VaccinationStatusResponse addVaccinationRecord(AddVaccinationRequest request);

    VaccinationStatusResponse getVaccinationsByUserId(UUID userId);
}
