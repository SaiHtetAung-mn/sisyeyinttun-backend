package com.example.backend.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.backend.model.Task;

@Service
public class TaskService {
    public List<List<Task>> generateSchedule(Integer timePerDay, List<Task> tasks) {
        List<Task> sortedTasks = getSortedTaskListByWeight(tasks);
        sortedTasks = getSortedTaskListByUrgency(sortedTasks);
        sortedTasks = getSortedTaskListByDependency(sortedTasks);
        return arrangeTaskInDay(timePerDay, sortedTasks);
        // List<List<Task>> t = new ArrayList<>();
        // t.add(sortedTasks);
        // return t;
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

    private List<Task> getSortedTaskListByUrgency(List<Task> tasks) {
        List<Task> sortedTasks = tasks;
        Collections.sort(sortedTasks, new Comparator<Task>() {
            public int compare(Task t1, Task t2) {
                if(t1.getTotalWeight() == t2.getTotalWeight()) {
                    return t2.urgency.compareTo(t1.urgency);
                }

                return 0;
            }
        });

        return sortedTasks;
    }

    private List<Task> getSortedTaskListByDependency(List<Task> tasks) {
        List<Task> sortedTasks = tasks;
        Boolean isSatisfied = false;
        Integer listSize = tasks.size();
        while (!isSatisfied) {
            for(Integer i=0; i<listSize; i++) {
                Task task = sortedTasks.get(i);
                if(task.dependency != null) {
                    int dependencyIndex = tasks.indexOf(new Task() {
                        public boolean equals(Object object) {
                            return ((Task)object).orderId == task.dependency;
                        }
                    });
                    
                    if(dependencyIndex > i) {
                        Task dependencyTask = sortedTasks.get(dependencyIndex);
                        sortedTasks.remove(dependencyIndex);
                        sortedTasks.add(i, dependencyTask);
                        break;
                    }
                }

                if(i == listSize-1) {
                    isSatisfied = true;
                }
            }
        }

        return sortedTasks;
    }

    private List<List<Task>> arrangeTaskInDay(Integer timePerDay, List<Task> tasks) {
        List arrangedTasks = new ArrayList<>();

        try {
            Double numOfHours = 0.0;
            for(int i=0; i<tasks.size(); i++) {
                numOfHours += tasks.get(i).estimatedTime;
            }

            int numOfDays = (int) Math.ceil((double) numOfHours/timePerDay);
            int startTaskIndex = 0;

            for(int i=0; i<numOfDays; i++) {
                List<Task> taskInOneDay = new ArrayList<>();
                Integer totalHours = 0;
                for(int j=startTaskIndex; j<tasks.size(); j++) {
                    Integer time = tasks.get(j).estimatedTime;

                    if(time <= timePerDay-totalHours) {
                        taskInOneDay.add(tasks.get(j));
                        startTaskIndex = j+1;
                        totalHours += time;
                    }
                    else {
                        Integer timeToDivide = timePerDay-totalHours;
                        Task dividedTask = (Task)tasks.get(j).clone();
                        dividedTask.estimatedTime = timeToDivide;
                        tasks.get(j).estimatedTime -= timeToDivide;
                        taskInOneDay.add(dividedTask);
                        totalHours += timeToDivide;
                        startTaskIndex = j;
                    }

                    if(totalHours.equals(timePerDay)) {
                        totalHours = 0;
                        break;
                    }
                }

                arrangedTasks.add(taskInOneDay);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        

        return arrangedTasks;
    }
}
