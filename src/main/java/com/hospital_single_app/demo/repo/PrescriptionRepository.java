package com.hospital_single_app.demo.repo;

import com.hospital_single_app.demo.entity.TbPrescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<TbPrescription, Long> {

}
