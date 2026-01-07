package com.example.taskmanager.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.example.taskmanager.dto.TaskPatchRequest;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    // PUT — full update
    public Task updateTask(long id, Task updatedTask) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found with id: " + id));

        task.setTitle(updatedTask.getTitle());
        task.setCompleted(updatedTask.isCompleted());

        return taskRepository.save(task);
    }

    // PATCH — partial update (industry-correct)
    public Task patchTask(long id, TaskPatchRequest patch) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found with id: " + id));

        if (patch.getTitle() != null) {
            task.setTitle(patch.getTitle());
        }

        if (patch.getCompleted() != null) {
            task.setCompleted(patch.getCompleted());
        }

        return taskRepository.save(task);
    }

    public void deleteTask(long id) {
        if (!taskRepository.existsById(id)) {
            throw new NoSuchElementException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }
}
