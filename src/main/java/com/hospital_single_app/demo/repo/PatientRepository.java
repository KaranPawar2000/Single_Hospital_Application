package com.hospital_single_app.demo.repo;

import com.hospital_single_app.demo.entity.TbPatient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<TbPatient,Long> {
    
}
