package com.hospital_single_app.demo.service;

import com.hospital_single_app.demo.dto.RoleMasterDTO;
import com.hospital_single_app.demo.entity.TbRoleMaster;
import com.hospital_single_app.demo.repo.RoleMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RoleMasterService {

    private final RoleMasterRepository repo;

    public RoleMasterDTO addRole(RoleMasterDTO dto) {
        TbRoleMaster role = new TbRoleMaster();

        role.setName(dto.getName());
        role.setStatus(dto.getStatus());
        role.setDescription(dto.getDescription());
        role.setCreatedModifiedDate(LocalDateTime.now());
        role.setReadOnly("N");
        role.setArchiveFlag("F");

        TbRoleMaster saved = repo.save(role);
        return toDTO(saved);
    }

    public List<RoleMasterDTO> getAllRoles() {
        return repo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public RoleMasterDTO getRoleById(Long id) {
        TbRoleMaster role = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        return toDTO(role);
    }


    private RoleMasterDTO toDTO(TbRoleMaster role) {
        RoleMasterDTO dto = new RoleMasterDTO();

        dto.setPkRoleId(role.getPkRoleId());
        dto.setName(role.getName());
        dto.setStatus(role.getStatus());
        dto.setDescription(role.getDescription());
        dto.setCreatedModifiedDate(role.getCreatedModifiedDate());
        dto.setReadOnly(role.getReadOnly());
        dto.setArchiveFlag(role.getArchiveFlag());

        return dto;
    }

}
