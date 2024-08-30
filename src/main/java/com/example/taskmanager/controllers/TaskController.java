package com.example.taskmanager.controllers;

import com.example.taskmanager.models.Task;
import com.example.taskmanager.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/task")
    public String getTaskPage(Model model){
        return "user/task";
    }
    @GetMapping("/user/task")
    public String showTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "user/task";
    }

    @GetMapping("/tasklist")
    public String getTaskList(Model model){
        return  "user/tasklist";
    }

    @PostMapping("/task/add")
    public String addTask(@ModelAttribute("task") Task task, BindingResult result) {
        if (result.hasErrors()) {
            // Ako ima grešaka, vrati se na obrazac za dodavanje zadatka
            return "user/task";
        }
        taskRepository.save(task); // Spremi zadatak u bazu podataka
        return "redirect:/task/tasklist"; // Preusmjeri korisnika na popis zadataka
    }


    // Ovdje bi dodao metode za editovanje i brisanje
}
