package com.example.taskmanager.controllers;

import com.example.taskmanager.models.Task;
import com.example.taskmanager.models.User;
import com.example.taskmanager.repositories.TaskRepository;
import com.example.taskmanager.services.UserDetailsService;
import com.example.taskmanager.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/task")
    public String getTaskPage(Model model){
        model.addAttribute("task", new Task());
        return "user/task";
    }

    @GetMapping("/tasklist")
    public String getTaskList(Model model){
        return "user/tasklist";
    }

    @GetMapping("/add")
    public String showTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "user/task";
    }

    // Ova metoda sada odgovara URL-u kojeg koristiš u formi
    @PostMapping("/add")
    public String addTask(@ModelAttribute("task") Task task, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        task.setUser(user);
        taskRepository.save(task);
        return "redirect:/task/tasklist";
    }

    // Ovdje bi dodao metode za editovanje i brisanje
}
