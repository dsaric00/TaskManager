package com.example.taskmanager.controllers;

import com.example.taskmanager.models.Task;
import com.example.taskmanager.repositories.TaskRepository;
import com.example.taskmanager.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskRepository taskRepository, TaskService taskService){
        this.taskRepository=taskRepository;
        this.taskService=taskService;
    }
    @PostMapping("/create")
    protected String createTask(@Valid Task task){
        taskRepository.save(task);
        return "redirect:/tasks";
    }
    @GetMapping
    public String getAllTasks(Model model){
        List <Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks",tasks);
        return "tasks/index";
    }
    @GetMapping("/{id}")
    public String getTaskById(@PathVariable Long id, Model model) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task ID: " + id));

        model.addAttribute("task", task);
        return "tasks/detail";

    }
    @PostMapping("/edit/{id}")
    public String updateTask(@PathVariable Long id,@Valid Task updateTask, Model model){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task ID: "+id));
    task.setTitle(updateTask.getTitle());
    task.setDescription(updateTask.getDescription());
    task.setStatus(updateTask.getStatus());
    task.setEndDate(updateTask.getEndDate());

    taskRepository.save(task);
    return "redirect:/tasks";
    }
    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return "redirect:/tasks";
    }
}
