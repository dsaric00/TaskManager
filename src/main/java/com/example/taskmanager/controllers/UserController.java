package com.example.taskmanager.controllers;

import com.example.taskmanager.models.Task;
import com.example.taskmanager.models.User;
import com.example.taskmanager.models.UserDetails;
import com.example.taskmanager.repositories.TaskRepository;
import com.example.taskmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    //Da korisnik može mjenajti podatke

    @Autowired
    private UserService userService;
    @PostMapping("/profile")
    public String showProfil(Model model, String username){
        User user = userService.getCurrentUser(username);
        model.addAttribute("user",user);
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("user") User user, @RequestParam("password") String password, @RequestParam("confirmPassword") String confirmPassword) {
        if (!password.isEmpty() && password.equals(confirmPassword)) {
            user.setLozinka(password); // Zamijenite metodom za hashiranje lozinke ako je potrebno
        }
        userService.updateUser(user);
        return "redirect:/user/profile";
    }






}
