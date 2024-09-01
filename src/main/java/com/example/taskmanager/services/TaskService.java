package com.example.taskmanager.services;

import com.example.taskmanager.models.Task;
import com.example.taskmanager.models.User;
import com.example.taskmanager.repositories.TaskRepository;
import com.example.taskmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class TaskService {

    private final TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository){
        this.taskRepository=taskRepository;
    }
    public Task saveTask(Task task){
        return taskRepository.save(task);
    }

    public List<Task> getTasksSortedByStatus() {
        return taskRepository.findAll(Sort.by("status").ascending());
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));
    }

   public List<Task> getTasksForCurrentUser(){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       User currentUser = userRepository.findByEmail(authentication.getName());
       return  taskRepository.findByUser(currentUser);
   }

   public List<Task> getTaskSortedByStatusForCurrentUser(){
        List<Task> tasks = getTasksForCurrentUser();
        tasks.sort((t1, t2) -> t1.getStatus().compareTo(t2.getStatus()));
        return tasks;
   }

}
