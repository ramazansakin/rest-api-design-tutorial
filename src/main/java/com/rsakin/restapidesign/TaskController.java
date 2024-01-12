package com.rsakin.restapidesign;

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
    public ResponseEntity<Task> addTask(@RequestBody TaskRequest taskRequest) {
        String taskDescription = taskRequest.getTask();
        if (taskDescription == null || taskDescription.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        long taskId = counter.incrementAndGet();
        Task newTask = new Task(taskId, taskDescription);
        tasks.add(newTask);

        return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable long id, @RequestBody TaskRequest taskRequest) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setTask(taskRequest.getTask());
                return ResponseEntity.ok(task);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable long id) {
        tasks.removeIf(task -> task.getId() == id);
        return ResponseEntity.noContent().build();
    }

    static class TaskRequest {
        private String task;

        public String getTask() {
            return task;
        }

        public void setTask(String task) {
            this.task = task;
        }
    }

    static class Task {
        private final long id;
        private String task;

        public Task(long id, String task) {
            this.id = id;
            this.task = task;
        }

        public long getId() {
            return id;
        }

        public String getTask() {
            return task;
        }

        public void setTask(String task) {
            this.task = task;
        }
    }

}