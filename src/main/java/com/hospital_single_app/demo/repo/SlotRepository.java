package com.hospital_single_app.demo.repo;

import com.hospital_single_app.demo.entity.TbSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SlotRepository extends JpaRepository<TbSlot, Long>  {

    List<TbSlot> findByStatusTrue();

}
