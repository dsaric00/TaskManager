package com.example.taskmanager.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAnyAuthority('USER')")
public class UserController {
    @GetMapping
    public String userIndex(Model model){
        return "user/dashboard";
    }
}
