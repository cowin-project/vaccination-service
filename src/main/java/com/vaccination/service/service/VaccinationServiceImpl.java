package com.vaccination.service.service;

import com.vaccination.service.dto.AddVaccinationRequest;
import com.vaccination.service.dto.VaccinationRecordResponse;
import com.vaccination.service.dto.VaccinationStatusResponse;
import com.vaccination.service.entity.Vaccination;
import com.vaccination.service.entity.enums.DoseNumber;
import com.vaccination.service.entity.enums.VaccinationState;
import com.vaccination.service.exception.BusinessValidationException;
import com.vaccination.service.repository.VaccinationRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VaccinationServiceImpl implements VaccinationService {

    private final VaccinationRepository vaccinationRepository;

    @Override
    @Transactional
    public VaccinationStatusResponse addVaccinationRecord(AddVaccinationRequest request) {
        UUID userId = request.getUserId();
        DoseNumber doseNumber = request.getDoseNumber();

        log.info("Adding vaccination record for userId={}, dose={}", userId, doseNumber);

        long doseCount = vaccinationRepository.countByUserId(userId);
        if (doseCount >= 2) {
            throw new BusinessValidationException("Maximum of 2 doses already recorded for this user");
        }

        if (vaccinationRepository.findByUserIdAndDoseNumber(userId, doseNumber).isPresent()) {
            throw new BusinessValidationException("Duplicate dose entry is not allowed");
        }

        if (doseNumber == DoseNumber.DOSE_2
                && vaccinationRepository.findByUserIdAndDoseNumber(userId, DoseNumber.DOSE_1).isEmpty()) {
            throw new BusinessValidationException("Dose 2 cannot be added before Dose 1");
        }

        Vaccination vaccination = Vaccination.builder()
                .userId(userId)
                .doseNumber(doseNumber)
                .vaccineType(request.getVaccineType())
                .vaccinationDate(request.getDate())
                .center(request.getCenter())
                .build();

        vaccinationRepository.save(vaccination);

        return getVaccinationsByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public VaccinationStatusResponse getVaccinationsByUserId(UUID userId) {
        List<Vaccination> records = vaccinationRepository.findByUserIdOrderByVaccinationDateAsc(userId);
        int totalDoses = records.size();

        VaccinationState status = switch (totalDoses) {
            case 0 -> VaccinationState.NOT_VACCINATED;
            case 1 -> VaccinationState.PARTIALLY_VACCINATED;
            default -> VaccinationState.FULLY_VACCINATED;
        };

        List<VaccinationRecordResponse> recordResponses = records.stream()
                .map(record -> VaccinationRecordResponse.builder()
                        .id(record.getId())
                        .userId(record.getUserId())
                        .doseNumber(record.getDoseNumber())
                        .vaccineType(record.getVaccineType())
                        .vaccinationDate(record.getVaccinationDate())
                        .center(record.getCenter())
                        .createdAt(record.getCreatedAt())
                        .build())
                .toList();

        return VaccinationStatusResponse.builder()
                .userId(userId)
                .totalDoses(totalDoses)
                .status(status)
                .records(recordResponses)
                .build();
    }
}
