package com.hospital_single_app.demo.service;



import com.hospital_single_app.demo.dto.SlotDTO;
import com.hospital_single_app.demo.entity.TbSlot;
import com.hospital_single_app.demo.repo.BookingRepository;
import com.hospital_single_app.demo.repo.SlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class SlotService {

    private final SlotRepository slotRepo;
    private final BookingRepository bookingRepo;


    // Add Slot
    public SlotDTO addSlot(SlotDTO dto) {

        TbSlot slot = TbSlot.builder()
                .slotName(dto.getSlotName())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .capacity(dto.getCapacity())
                .seqNo(dto.getSeqNo())
                .status(dto.getStatus())
                .build();

        return toDTO(slotRepo.save(slot));
    }

    // Update Slot
    public SlotDTO updateSlot(Long id, SlotDTO dto) {

        TbSlot slot = slotRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        slot.setSlotName(dto.getSlotName());
        slot.setStartTime(dto.getStartTime());
        slot.setEndTime(dto.getEndTime());
        slot.setCapacity(dto.getCapacity());
        slot.setSeqNo(dto.getSeqNo());
        slot.setStatus(dto.getStatus());


        return toDTO(slotRepo.save(slot));
    }

    // Get All
    public List<SlotDTO> getAll() {
        return slotRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    public List<SlotDTO> getAvailableSlotsByDoctorAndDate(LocalDate date) {

        return  slotRepo.findByStatusTrue()   // 🔥 removed doctor filter
                .stream()
                .filter(slot -> {
                    long bookedCount =
                            bookingRepo.countBySlot_PkSlotIdAndBookingDate(
                                    slot.getPkSlotId(), date
                            );

                    // ✅ SKIP SLOT IF FULL FOR THAT DATE
                    return bookedCount < slot.getCapacity();
                })
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    private SlotDTO toDTO(TbSlot slot) {
        SlotDTO dto = new SlotDTO();

        dto.setPkSlotId(slot.getPkSlotId());
        dto.setSlotName(slot.getSlotName());
        dto.setStartTime(slot.getStartTime());
        dto.setEndTime(slot.getEndTime());
        dto.setCapacity(slot.getCapacity());
        dto.setSeqNo(slot.getSeqNo());
        dto.setStatus(slot.getStatus());

        return dto;
    }

}
