package com.example.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.model.Task;
import com.example.backend.service.TaskService;

@RestController
@CrossOrigin(origins = "*")
public class TaskController {
    @Autowired
    TaskService taskService;

    @PostMapping("/api/generate")
    public ResponseEntity<Map<String, Object>> computeSchedule(@RequestBody Map<String, Object> payload) {
        Integer timePerDay = (Integer)payload.get("timePerDay");
        List<Task> tasks = Task.convertToTaskList((List<Map<String, Object>>)payload.get("tasks"));
        Map<String, Object> res = Map.of("timePerDay", (Object)timePerDay, "tasks", this.taskService.generateSchedule(timePerDay, tasks));
        
        return ResponseEntity.ok().body(res);
    }
}
