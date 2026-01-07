package com.example.taskmanager.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanager.dto.TaskPatchRequest;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;

import jakarta.validation.Valid;

@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Health check
    @GetMapping("/ping")
    public String ping() {
        return "Task Manager API is running";
    }

    // Get all tasks
    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return taskService.getAllTasks();
    }

    // Create task (VALIDATED)
    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@Valid @RequestBody Task task) {
        return taskService.addTask(task);
    }

    // PUT - full update (VALIDATED)
    @PutMapping("/tasks/{id}")
    public Task updateTask(
            @PathVariable long id,
            @Valid @RequestBody Task task
    ) {
        return taskService.updateTask(id, task);
    }

    // PATCH - partial update (DTO-based, industry-correct)
    @PatchMapping("/tasks/{id}")
    public Task patchTask(
            @PathVariable long id,
            @RequestBody TaskPatchRequest patch
    ) {
        return taskService.patchTask(id, patch);
    }

    // DELETE task
    @DeleteMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable long id) {
        taskService.deleteTask(id);
    }

    // 404 handler
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(NoSuchElementException ex) {
        return ex.getMessage();
    }
}
