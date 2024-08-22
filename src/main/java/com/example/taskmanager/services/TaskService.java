package com.example.taskmanager.services;

import com.example.taskmanager.models.Task;
import com.example.taskmanager.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository){
        this.taskRepository=taskRepository;
    }
    public Task saveTask(Task task){
        return taskRepository.save(task);
    }
    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }
    public  Task createTask(Task task){
        return taskRepository.save(task);
    }
}
