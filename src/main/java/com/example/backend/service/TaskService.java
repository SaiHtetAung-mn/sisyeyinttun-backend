package com.example.backend.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.backend.model.Task;

@Service
public class TaskService {
    public List<Task> generateSchedule(Integer timePerDay, List<Task> tasks) {
        List<Task> sortedTasks = getSortedTaskListByWeight(tasks);
        return sortedTasks;
    }

    private List<Task> getSortedTaskListByWeight(List<Task> tasks) {
        List<Task> sortedTasks = tasks;
        Collections.sort(sortedTasks, new Comparator<Task>() {
            public int compare(Task t1, Task t2) {
                return t2.getTotalWeight().compareTo(t1.getTotalWeight());
            }
        });

        return sortedTasks;
    }
}
