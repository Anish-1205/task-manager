package com.example.taskmanager.service;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;

@SpringBootTest
@Transactional
class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void addTask_savesTaskToDatabase() {
        Task task = new Task(null, "Service test task", false);

        Task saved = taskService.addTask(task);

        List<Task> tasks = taskRepository.findAll();

        assertThat(saved.getId()).isNotNull();
        assertThat(tasks)
                .extracting(Task::getTitle)
                .contains("Service test task");
    }
}
