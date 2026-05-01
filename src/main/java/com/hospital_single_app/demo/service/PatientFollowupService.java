package com.hospital_single_app.demo.service;


import com.hospital_single_app.demo.dto.PatientFollowupDTO;
import com.hospital_single_app.demo.entity.TbPatientFollowUp;
import com.hospital_single_app.demo.repo.PatientFollowupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientFollowupService {

    private final PatientFollowupRepository repository;


    public TbPatientFollowUp saveFollowup(PatientFollowupDTO dto) {

        TbPatientFollowUp followUp = new TbPatientFollowUp();

        // patient reference
        followUp.setPatientId(dto.getPatientId());

        // patient details (snapshot)
        followUp.setFullName(dto.getFullName());
        followUp.setPhone(dto.getPhone());
        followUp.setGender(dto.getGender());
        followUp.setDob(dto.getDob());
        followUp.setAddress(dto.getAddress());
        followUp.setFollowUpDate(dto.getFollowUpDate());

        return repository.save(followUp);
    }

    public List<TbPatientFollowUp> getByPatientId(Long patientId) {
        return repository.findByPatientId(patientId);
    }


    public List<TbPatientFollowUp> getAllFollowUp() {
        return repository.findAll();
    }


    // =========================
    // TODAY REMINDERS
    // =========================
//    public List<TbPatientFollowUp> getTodayReminders(Long clientId) {
//        return repository.findByClientIdAndNextFollowUpDate(
//                clientId,
//                LocalDate.now()
//        );
//    }




}
