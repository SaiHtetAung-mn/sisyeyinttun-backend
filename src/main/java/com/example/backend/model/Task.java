package com.example.backend.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Task {
    private static Double importanceWeight = 0.3;
    private static Double urgencyWeight = 0.5;
    private static Double easinessWeight = 0.2;

    public Integer orderId;
    public String description;
    public Integer importance;
    public Integer urgency;
    public Integer easiness;
    public Integer estimatedTime;
    public String dependency;

    public Double getTotalWeight() {
        return (
            (Task.importanceWeight*this.importance)*
            (Task.urgencyWeight*this.urgency)*
            (Task.easinessWeight*this.easiness)
        );
    }

    public static List<Task> convertToTaskList(List<Map<String, Object>> data) {
        List<Task> output = new ArrayList<>();
        for(Integer i=0; i<data.size(); i++) {
            Task task = new Task();
            task.orderId = (Integer)data.get(i).get("orderId");
            task.description = (String)data.get(i).get("description");
            task.importance = (Integer)data.get(i).get("importance");
            task.urgency = (Integer)data.get(i).get("urgency");
            task.easiness = (Integer)data.get(i).get("easiness");
            task.estimatedTime = (Integer)data.get(i).get("estimatedTime");
            task.dependency = (String)data.get(i).get("dependency");

            output.add(task);
        }

        return output;
    }
}
