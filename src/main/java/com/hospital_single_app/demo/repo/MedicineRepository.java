package com.hospital_single_app.demo.repo;

import com.hospital_single_app.demo.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository  extends JpaRepository<Medicine, Long> {
}
