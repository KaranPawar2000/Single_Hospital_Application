package com.hospital_single_app.demo.repo;

import com.hospital_single_app.demo.entity.TbHoliday;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidayRepository extends JpaRepository<TbHoliday, Long> {


}
