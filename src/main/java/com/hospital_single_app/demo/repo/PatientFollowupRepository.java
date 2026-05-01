package com.hospital_single_app.demo.repo;

import com.hospital_single_app.demo.entity.TbPatientFollowUp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientFollowupRepository extends JpaRepository<TbPatientFollowUp, Long> {

    List<TbPatientFollowUp> findByPatientId(Long patientId);


}
