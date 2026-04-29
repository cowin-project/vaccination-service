package com.vaccination.service.controller;

import com.vaccination.service.dto.AddVaccinationRequest;
import com.vaccination.service.dto.VaccinationStatusResponse;
import com.vaccination.service.service.VaccinationService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vaccinations")
@RequiredArgsConstructor
public class VaccinationController {

    private final VaccinationService vaccinationService;

    @PostMapping
    public ResponseEntity<VaccinationStatusResponse> addVaccination(@Valid @RequestBody AddVaccinationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vaccinationService.addVaccinationRecord(request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<VaccinationStatusResponse> getVaccinationRecords(@PathVariable UUID userId) {
        return ResponseEntity.ok(vaccinationService.getVaccinationsByUserId(userId));
    }
}
