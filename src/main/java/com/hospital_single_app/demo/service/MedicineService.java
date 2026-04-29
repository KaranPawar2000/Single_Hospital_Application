package com.hospital_single_app.demo.service;

import com.hospital_single_app.demo.dto.MedicineDTO;
import com.hospital_single_app.demo.entity.Medicine;
import com.hospital_single_app.demo.repo.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepository medicineRepository;

    public MedicineDTO addMedicine(MedicineDTO dto) {


        Medicine medicine = new Medicine();
        medicine.setMedicineName(dto.getMedicineName());
        medicine.setUnit(dto.getUnit());   // ✅ ONLY UNIT (mg / ml)

        Medicine saved = medicineRepository.save(medicine);

        MedicineDTO response = new MedicineDTO();
        response.setMedicineId(saved.getMedicineId());
        response.setMedicineName(saved.getMedicineName());
        response.setUnit(saved.getUnit());
        return response;
    }


    // 🔹 GET MEDICINES BY DOCTOR ID
    public List<MedicineDTO> getMedicines() {
     System.out.println("I am in service");
        return medicineRepository.findAll()
                .stream()
                .map(m -> {
                    MedicineDTO dto = new MedicineDTO();
                    dto.setMedicineId(m.getMedicineId());
                    dto.setMedicineName(m.getMedicineName());
                    dto.setUnit(m.getUnit());
                    return dto;
                })
                .collect(Collectors.toList());
    }


}
