package com.hospital_single_app.demo.service;

import com.hospital_single_app.demo.dto.PatientDTO;
import com.hospital_single_app.demo.entity.TbPatient;
import com.hospital_single_app.demo.repo.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepo;

    private PatientDTO toDTO(TbPatient p) {

        PatientDTO dto = new PatientDTO();

        dto.setPatientId(p.getPatientId());
        dto.setFullName(p.getFullName());
        dto.setPhone(p.getPhone());
        dto.setGender(p.getGender());
        dto.setDob(p.getDob());
        dto.setAddress(p.getAddress());
        dto.setStatus(p.getStatus());

        return dto;
    }


    @Transactional
    public TbPatient addPatient(PatientDTO dto) {



//        long existingCount = patientRepo.countByClientIdAndPhone(
//                clientId,
//                dto.getPhone()
//        );

//        // ✅ MAIN VALIDATION
//        if (existingCount >= allowedCount) {
//            throw new ResponseStatusException(
//                    HttpStatus.BAD_REQUEST,
//                    "Patient limit is full for this mobile number"
//            );
//        }

        TbPatient p = new TbPatient();
        p.setFullName(dto.getFullName());
        p.setPhone(dto.getPhone());
        p.setGender(dto.getGender());
        p.setDob(dto.getDob());
        p.setAddress(dto.getAddress());
        p.setStatus(dto.getStatus());

        return patientRepo.save(p);
    }





}
