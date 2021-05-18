package main;

import main.model.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    @GetMapping("/tasks/")
    public List<Task> list() {
        return Storage.getAllTasks();
    }

    @PostMapping("/tasks/")
    public int add(Task task) {
        return Storage.addTask(task);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity get(@PathVariable int id) {
        Task task = Storage.getTask(id);
        if(task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(task, HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        if(Storage.delete(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @DeleteMapping("/tasks/")
    public ResponseEntity deleteAll() {
        return Storage.deleteAll();
    }
}
