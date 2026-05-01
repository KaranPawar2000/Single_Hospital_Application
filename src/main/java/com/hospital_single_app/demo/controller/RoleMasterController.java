package com.hospital_single_app.demo.controller;

import com.hospital_single_app.demo.dto.RoleMasterDTO;
import com.hospital_single_app.demo.service.RoleMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleMasterController {

    private final RoleMasterService service;

    @PostMapping("/add")
    public ResponseEntity<RoleMasterDTO> addRole(@RequestBody RoleMasterDTO dto) {
        return ResponseEntity.ok(service.addRole(dto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RoleMasterDTO>> getAllRoles() {
        return ResponseEntity.ok(service.getAllRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleMasterDTO> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getRoleById(id));
    }

}
