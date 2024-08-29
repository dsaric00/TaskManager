package com.example.taskmanager.controllers;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
public class HomeController {
    @GetMapping("/")
    public String home(Model model){

        return "/user/dashboard.html";
    }
}
