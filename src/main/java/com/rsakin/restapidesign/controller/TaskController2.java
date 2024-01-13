package com.rsakin.restapidesign.controller;

import com.rsakin.restapidesign.model.Task2;
import com.rsakin.restapidesign.model.TaskCreateRequest;
import com.rsakin.restapidesign.model.TaskStatus;
import com.rsakin.restapidesign.model.exception.MissingFieldsException;
import com.rsakin.restapidesign.model.exception.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/v2/tasks")
public class TaskController2 {

    private final List<Task2> tasks = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    @PostMapping
    public ResponseEntity<Task2> addTask(@RequestBody TaskCreateRequest taskRequest) {
        String taskHeadline = taskRequest.headline();
        String taskDetail = taskRequest.detail();
        TaskStatus status = taskRequest.status();

        if (status == null) {
            throw new MissingFieldsException("Task status field is required");
        }

        long taskId = counter.incrementAndGet();
        Task2 newTask = new Task2(taskId, taskHeadline, taskDetail, status);
        tasks.add(newTask);

        return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
    }

    @GetMapping
    public ResponseEntity<Object> getAllTasks() {
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task2> getTaskV2(@PathVariable long id) {
        Task2 task = tasks.stream()
                .filter(t -> t.id() == id)
                .findFirst()
                .orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + id));

        return ResponseEntity.ok(task);
    }

}