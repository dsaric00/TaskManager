package com.example.taskmanager.controllers;

import com.example.taskmanager.models.Task;
import com.example.taskmanager.models.User;
import com.example.taskmanager.repositories.TaskRepository;
import com.example.taskmanager.services.TaskService;
import com.example.taskmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @GetMapping("/task")
    public String getTaskPage(Model model){
        model.addAttribute("task", new Task());
        return "user/task";
    }

    @GetMapping("/tasklist")
    public String getTaskList(Model model) {
        List<Task> tasks = taskService.getTaskSortedByStatusForCurrentUser(); // Dohvati sve zadatke iz tablice 'task'
        model.addAttribute("tasks", tasks);
        return "user/tasklist"; // Vrati se na tasklist.html
    }


    @GetMapping("/add")
    public String showTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "user/task";
    }


    @PostMapping("/add")
    public String addTask(@ModelAttribute("task") Task task, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        task.setUser(user);
        taskRepository.save(task);
        return "redirect:/task/tasklist";
    }
    @PostMapping("/tasks/updateStatus")
    public String updateTaskStatus(@RequestParam("taskId") Long taskId, @RequestParam("status") String status) {
        Task task = taskService.getTaskById(taskId);
        if (task != null) {
            // Konvertiraj String u Task.Status
            Task.Status newStatus = Task.Status.valueOf(status);
            task.setStatus(newStatus);
            taskService.saveTask(task);
        }
        return "redirect:/task/tasklist";
    }

    @GetMapping("/edit/{taskId}")
    public String showEditTaskForm(@PathVariable Long taskId, Model model) {
        Task task = taskService.getTaskById(taskId);
        if (task == null) {
            // Ako zadatak ne postoji, baci iznimku ili preusmjeri na stranicu s greškom
            throw new RuntimeException("Task not found");
        }
        model.addAttribute("task", task);
        return "user/editTask";
    }


    @PostMapping("/edit")
    public String editTask(@ModelAttribute Task task) {
        if (task.getId() == null) {
            throw new IllegalArgumentException("The given id must not be null");
        }
        taskService.updateTask(task);
        return "redirect:/task/tasklist";
    }



    @PostMapping("/delete/{taskId}")
    public String deleteTask(@PathVariable Long taskId) {
        taskRepository.deleteById(taskId);
        return "redirect:/task/tasklist";
    }



}
