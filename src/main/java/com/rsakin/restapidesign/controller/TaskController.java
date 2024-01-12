package com.rsakin.restapidesign.controller;

import com.rsakin.restapidesign.model.Task;
import com.rsakin.restapidesign.model.TaskCreateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final List<Task> tasks = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody TaskCreateRequest taskRequest) {
        String taskHeadline = taskRequest.headline();
        String taskDetail = taskRequest.detail();

        long taskId = counter.incrementAndGet();
        Task newTask = new Task(taskId, taskHeadline, taskDetail);
        tasks.add(newTask);

        return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable long id,
                                           @RequestBody TaskCreateRequest taskRequest) {

        for (Task task : tasks) {
            if (task.id() == id) {
                task = new Task(task.id(), taskRequest.headline(), taskRequest.detail());
                return ResponseEntity.ok(task);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable long id) {
        tasks.removeIf(task -> task.id() == id);
        return ResponseEntity.noContent().build();
    }

}