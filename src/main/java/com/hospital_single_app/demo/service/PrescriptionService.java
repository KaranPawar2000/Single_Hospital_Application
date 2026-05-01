package com.hospital_single_app.demo.service;


import com.hospital_single_app.demo.dto.PrescriptionDTO;
import com.hospital_single_app.demo.entity.Medicine;
import com.hospital_single_app.demo.entity.TbPatient;
import com.hospital_single_app.demo.entity.TbPrescription;
import com.hospital_single_app.demo.repo.PrescriptionRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final EntityManager entityManager;
    private final PrescriptionRepository prescriptionRepository;


    private PrescriptionDTO mapToDTO(TbPrescription p) {

        PrescriptionDTO dto = new PrescriptionDTO();

        dto.setPrescriptionId(p.getPkPrescriptionId());

        // Medicine
        dto.setMedicineId(p.getMedicineDoctor().getMedicineId()); // ✅ FIX
        dto.setMedicineName(p.getMedicineDoctor().getMedicineName());

        // Patient
        dto.setPatientId(p.getPatient().getPatientId());
        dto.setPatientName(p.getPatient().getFullName()); // ✅ FIX

        dto.setDose(p.getDose());
        dto.setQuantity(p.getQuantity());
        dto.setInstruction(p.getInstruction());

        dto.setMorning(p.getMorning());
        dto.setAfternoon(p.getAfternoon());
        dto.setEvening(p.getEvening());
        dto.setNight(p.getNight());

        return dto;
    }



    public PrescriptionDTO addPrescription(PrescriptionDTO dto) {

        Medicine medicine = entityManager.getReference(Medicine.class, dto.getMedicineId());
        TbPatient patient = entityManager.getReference(TbPatient.class, dto.getPatientId());

        TbPrescription prescription = TbPrescription.builder()
                .medicineDoctor(medicine)
                .patient(patient)
                .dose(dto.getDose())
                .quantity(dto.getQuantity())
                .instruction(dto.getInstruction())
                .morning(dto.getMorning())
                .afternoon(dto.getAfternoon())
                .evening(dto.getEvening())
                .night(dto.getNight())
                .build();

        TbPrescription saved = prescriptionRepository.save(prescription);
        return mapToDTO(saved);
    }

    // =========================
    public List<PrescriptionDTO> getAllPrescriptions() {
        return prescriptionRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }



}
