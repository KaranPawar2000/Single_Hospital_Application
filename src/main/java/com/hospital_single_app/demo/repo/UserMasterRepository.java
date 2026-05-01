package com.hospital_single_app.demo.repo;

import com.hospital_single_app.demo.entity.TbUserMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserMasterRepository extends JpaRepository<TbUserMaster, Long> {

    Optional<TbUserMaster> findByEmailIdAndPassword(String emailId, String password);

}
