package com.rsakin.restapidesign.controller;

import com.rsakin.restapidesign.model.Task;
import com.rsakin.restapidesign.model.TaskCreateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
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
    public ResponseEntity<Object> getAllTasks(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int count) {

        // Validate offset and limit is not sub-zero
        if (page < 1 || count < 1) {
            return ResponseEntity.badRequest().body("Error occurred. (page) and (count) needs to be positive");
        }

        // Apply pagination
        List<Task> paginatedTasks = paginateList(tasks, page, count);

        return ResponseEntity.ok(paginatedTasks);
    }

    public static <T> List<T> paginateList(List<T> list, int page, int count) {
        page -= 1; // Page starts from 1, index from 0
        int startIndex = page * count;
        if (startIndex >= list.size()) {
            return Collections.emptyList(); // Handle empty result for out-of-bounds pages
        }
        int endIndex = Math.min(startIndex + count, list.size());
        return list.subList(startIndex, endIndex);
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