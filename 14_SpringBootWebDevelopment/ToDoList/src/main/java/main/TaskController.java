package main;

import main.model.Task;
import main.model.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/tasks/")
    public List<Task> list() {

        List<Task> tasks = (List<Task>) taskRepository.findAll();
        return tasks;
    }

    @PostMapping("/tasks/")
    public int add(Task task) {
        Task newTask = taskRepository.save(task);
        return newTask.getId();
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {
        Optional optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity<>(optionalTask.get(), HttpStatus.OK);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<?> save(@PathVariable int id, Task newTask) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        taskRepository.save(newTask);
        return new ResponseEntity<>(newTask, HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<?> deleteId(@PathVariable int id) {
        Optional optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        taskRepository.deleteById(id);
        return new ResponseEntity<>(optionalTask.get(), HttpStatus.OK);

    }

    @DeleteMapping("/tasks/")
    public void deleteAll() {
        taskRepository.deleteAll();
    }
}
