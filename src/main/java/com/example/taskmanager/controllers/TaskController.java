package com.example.taskmanager.controllers;

import com.example.taskmanager.models.Task;
import com.example.taskmanager.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskRepository.findAll());
        return "task-list";
    }

    @PostMapping("/add")
    public String addTask(Task task) {
        taskRepository.save(task);
        return "redirect:/tasks";
    }

    // Ovdje bi dodao metode za editovanje i brisanje
}
