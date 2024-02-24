package com.example.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.model.Task;
import com.example.backend.service.TaskService;

@RestController
public class TaskController {
    @Autowired
    TaskService taskService;

    @PostMapping("/api/generate")
    public ResponseEntity<List<Task>> computeSchedule(@RequestBody Map<String, Object> payload) {
        Integer timePerDay = (Integer)payload.get("timePerDay");
        List<Task> tasks = Task.convertToTaskList((List<Map<String, Object>>)payload.get("tasks"));
        return ResponseEntity.ok().body(this.taskService.generateSchedule(timePerDay, tasks));
    }
}
