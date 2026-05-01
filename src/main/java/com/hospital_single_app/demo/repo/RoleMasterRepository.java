package com.hospital_single_app.demo.repo;

import com.hospital_single_app.demo.entity.TbRoleMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleMasterRepository extends JpaRepository<TbRoleMaster, Long> {
}
