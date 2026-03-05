package com.company.saas.controller;

import com.company.saas.dto.LoginDTO;
import com.company.saas.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")

public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO dto){

        return authService.login(dto);
    }

}