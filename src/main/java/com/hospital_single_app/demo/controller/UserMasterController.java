package com.hospital_single_app.demo.controller;


import com.hospital_single_app.demo.dto.UserLoginRequestDTO;
import com.hospital_single_app.demo.dto.UserMasterDTO;
import com.hospital_single_app.demo.entity.TbUserMaster;
import com.hospital_single_app.demo.service.UserMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserMasterController {

    private final UserMasterService userService;

    @PostMapping("/add")
    public ResponseEntity<UserMasterDTO> addUser(@RequestBody TbUserMaster user) {
        return ResponseEntity.ok(userService.addUser(user));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserMasterDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserMasterDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserMasterDTO> updateUser(@PathVariable Long id,
                                                    @RequestBody TbUserMaster user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<UserMasterDTO> login(@RequestBody UserLoginRequestDTO request) {
        System.out.println("login request");
        return ResponseEntity.ok(
                userService.login(request.getEmailId(), request.getPassword())
        );
    }


}
