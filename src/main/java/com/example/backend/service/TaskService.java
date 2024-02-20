package com.example.backend.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.backend.model.Task;

@Service
public class TaskService {
    public List<Map<String, Task>> generateSchedule(List<Map<String, Task>> data) {
        return data;
    }
}
