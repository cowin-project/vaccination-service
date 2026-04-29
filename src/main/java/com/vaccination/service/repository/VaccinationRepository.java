package com.vaccination.service.repository;

import com.vaccination.service.entity.Vaccination;
import com.vaccination.service.entity.enums.DoseNumber;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccinationRepository extends JpaRepository<Vaccination, UUID> {

    List<Vaccination> findByUserIdOrderByVaccinationDateAsc(UUID userId);

    Optional<Vaccination> findByUserIdAndDoseNumber(UUID userId, DoseNumber doseNumber);

    long countByUserId(UUID userId);
}
