package com.hospital_single_app.demo.service;


import com.hospital_single_app.demo.dto.HolidayDTO;
import com.hospital_single_app.demo.entity.TbHoliday;
import com.hospital_single_app.demo.repo.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HolidayService {


    private final HolidayRepository holidayRepo;

    private HolidayDTO toDTO(TbHoliday h) {
        HolidayDTO dto = new HolidayDTO();

        dto.setHolidayId(h.getHolidayId());
        dto.setHolidayDate(h.getHolidayDate());
        dto.setReason(h.getReason());
        dto.setStatus(h.getStatus());

        return dto;
    }


    public HolidayDTO addHoliday(HolidayDTO dto) {

        if (dto.getHolidayDate() == null)
            throw new IllegalArgumentException("holidayDate cannot be null");

        if (dto.getReason() == null || dto.getReason().trim().isEmpty())
            throw new IllegalArgumentException("reason cannot be empty");


        TbHoliday holiday = TbHoliday.builder()
                .holidayDate(dto.getHolidayDate())
                .reason(dto.getReason())
                .status(dto.getStatus() == null ? Boolean.TRUE : dto.getStatus())
                .build();

        return toDTO(holidayRepo.save(holiday));
    }



    public HolidayDTO updateHoliday(Long id, HolidayDTO dto) {

        TbHoliday holiday = holidayRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Holiday not found with ID: " + id));

        if (dto.getHolidayDate() == null)
            throw new IllegalArgumentException("holidayDate cannot be null");

        if (dto.getReason() == null || dto.getReason().trim().isEmpty())
            throw new IllegalArgumentException("reason cannot be empty");


        holiday.setHolidayDate(dto.getHolidayDate());
        holiday.setReason(dto.getReason());
        holiday.setStatus(dto.getStatus());

        return toDTO(holidayRepo.save(holiday));
    }

    public List<HolidayDTO> getAll() {
        return holidayRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }




}
