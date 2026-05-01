package com.hospital_single_app.demo.repo;

import com.hospital_single_app.demo.entity.TbPatient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<TbPatient,Long> {

    Optional<TbPatient> findByPhone(String phone);
}
