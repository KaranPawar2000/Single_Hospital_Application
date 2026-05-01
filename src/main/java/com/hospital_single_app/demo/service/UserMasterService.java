package com.hospital_single_app.demo.service;

import com.hospital_single_app.demo.dto.UserLoginRequestDTO;
import com.hospital_single_app.demo.dto.UserMasterDTO;
import com.hospital_single_app.demo.entity.TbRoleMaster;
import com.hospital_single_app.demo.entity.TbUserMaster;
import com.hospital_single_app.demo.repo.UserMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMasterService {

    private final UserMasterRepository userRepo;

    public UserMasterDTO addUser(TbUserMaster user) {

        // Attach only Role ID
        if (user.getRole() != null) {
            TbRoleMaster role = new TbRoleMaster();
            role.setPkRoleId(user.getRole().getPkRoleId());
            user.setRole(role);
        }

        // Internal fields
        user.setCreatedModifiedDate(LocalDateTime.now());
        user.setReadOnly("N");
        user.setArchiveFlag("F");

        TbUserMaster saved = userRepo.save(user);
        return toDTO(saved);
    }


    public List<UserMasterDTO> getAllUsers() {
        return userRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public UserMasterDTO getById(Long id) {
        TbUserMaster user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return toDTO(user);
    }

    public UserMasterDTO updateUser(Long id, TbUserMaster newUser) {

        TbUserMaster existing = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existing.setName(newUser.getName());
        existing.setEmailId(newUser.getEmailId());
        existing.setMobileNo(newUser.getMobileNo());
        existing.setPassword(newUser.getPassword());
        existing.setIsActive(newUser.getIsActive());
        existing.setCreatedModifiedDate(LocalDateTime.now());

        if (newUser.getRole() != null) {
            TbRoleMaster role = new TbRoleMaster();
            role.setPkRoleId(newUser.getRole().getPkRoleId());
            existing.setRole(role);
        }


        TbUserMaster updated = userRepo.save(existing);
        return toDTO(updated);
    }

    public UserMasterDTO login(String email, String password) {

        TbUserMaster user = userRepo.findByEmailIdAndPassword(email, password)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!user.getIsActive()) {
            throw new RuntimeException("Account is inactive");
        }

        return toDTO(user);
    }



    private UserMasterDTO toDTO(TbUserMaster user) {
        UserMasterDTO dto = new UserMasterDTO();

        dto.setPkUserId(user.getPkUserId());
        dto.setName(user.getName());
        dto.setEmailId(user.getEmailId());
        dto.setMobileNo(user.getMobileNo());
        dto.setIsActive(user.getIsActive());
        dto.setPassword(user.getPassword());
        dto.setCreatedModifiedDate(user.getCreatedModifiedDate());
        dto.setReadOnly(user.getReadOnly());
        dto.setArchiveFlag(user.getArchiveFlag());

        // ROLE
        if (user.getRole() != null) {
            dto.setRoleId(user.getRole().getPkRoleId());
            dto.setRoleName(user.getRole().getName());
        }



        return dto;
    }


}
