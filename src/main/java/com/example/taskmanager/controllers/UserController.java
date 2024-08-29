package com.example.taskmanager.controllers;

import com.example.taskmanager.models.Task;
import com.example.taskmanager.models.User;
import com.example.taskmanager.models.UserDetails;
import com.example.taskmanager.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAnyAuthority('USER')")
public class UserController {
    @Autowired
    private TaskRepository taskRepository;
    @GetMapping
    public String userIndex(Model model){
        return "user/dashboard";
    }
    @GetMapping("/dashboard")
    public String userDashboard(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails =(UserDetails) authentication.getPrincipal();
        User currentUser = userDetails.getUser();
        Task task = new Task();
        task.setUser(currentUser);
        model.addAttribute("task", task);
        return "user/dashboard";
    }
    @GetMapping("/profile")
    public String getProfilePage(Model model) {
        return "user/profile";
    }

    @GetMapping("/task")
    public String getTaskPage(Model model){
        List<Task> tasks = taskRepository.findAll();
        model.addAttribute(tasks);
        return "user/task";
    }
    @GetMapping("/tasklist")
    public String getTaskList(Model model){
        List<Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);
        return  "user/tasklist";
    }




}
