package main;

import main.model.Task;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Storage {

    private static int currentId = 1;
    private static HashMap<Integer, Task> tasks = new HashMap<>();

    public static List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public static int addTask(Task task) {
        int id = currentId++;
        task.setId(id);
        tasks.put(id, task);
        return id;
    }

    public static Task getTask (int taskId) {
        if(tasks.containsKey(taskId)) {
            return tasks.get(taskId);
        }
        return null;
    }

    public static ResponseEntity deleteAll() {
        tasks.clear();
        return null;
    }

    public static boolean delete(int id) {
        return tasks.remove(id,tasks.get(id));
    }
}
