package com.vaccination.service.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.vaccination.service.dto.AddVaccinationRequest;
import com.vaccination.service.entity.Vaccination;
import com.vaccination.service.entity.enums.DoseNumber;
import com.vaccination.service.exception.BusinessValidationException;
import com.vaccination.service.repository.VaccinationRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VaccinationServiceImplTest {

    @Mock
    private VaccinationRepository vaccinationRepository;

    @InjectMocks
    private VaccinationServiceImpl vaccinationService;

    private UUID userId;

    @BeforeEach
    void setup() {
        userId = UUID.randomUUID();
    }

    @Test
    void shouldFailWhenAddingDose2BeforeDose1() {
        AddVaccinationRequest request = new AddVaccinationRequest();
        request.setUserId(userId);
        request.setDoseNumber(DoseNumber.DOSE_2);
        request.setVaccineType("Covishield");
        request.setDate(LocalDate.now());
        request.setCenter("ABC Hospital");

        when(vaccinationRepository.countByUserId(userId)).thenReturn(0L);
        when(vaccinationRepository.findByUserIdAndDoseNumber(userId, DoseNumber.DOSE_2)).thenReturn(Optional.empty());
        when(vaccinationRepository.findByUserIdAndDoseNumber(userId, DoseNumber.DOSE_1)).thenReturn(Optional.empty());

        assertThrows(BusinessValidationException.class, () -> vaccinationService.addVaccinationRecord(request));
    }

    @Test
    void shouldFailWhenDuplicateDose() {
        AddVaccinationRequest request = new AddVaccinationRequest();
        request.setUserId(userId);
        request.setDoseNumber(DoseNumber.DOSE_1);
        request.setVaccineType("Covaxin");
        request.setDate(LocalDate.now());
        request.setCenter("XYZ Hospital");

        when(vaccinationRepository.countByUserId(userId)).thenReturn(1L);
        when(vaccinationRepository.findByUserIdAndDoseNumber(userId, DoseNumber.DOSE_1))
                .thenReturn(Optional.of(new Vaccination()));

        assertThrows(BusinessValidationException.class, () -> vaccinationService.addVaccinationRecord(request));
    }
}
