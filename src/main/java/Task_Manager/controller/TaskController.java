package Task_Manager.controller;

import Task_Manager.dto.CreateTaskRequest;
import Task_Manager.dto.TaskDTO;
import Task_Manager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(Authentication authentication, @RequestBody CreateTaskRequest request) {
        String username = authentication.getName();
        TaskDTO task = taskService.createTask(username, request);
        return ResponseEntity.ok(task);
    }

    @GetMapping
    public ResponseEntity<Page<TaskDTO>> getUserTasks(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        String username = authentication.getName();
        Pageable pageable = PageRequest.of(page, size);
        Page<TaskDTO> tasks = taskService.getUserTasks(username, pageable);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(Authentication authentication, @PathVariable Long id) {
        String username = authentication.getName();
        TaskDTO task = taskService.getTaskById(username, id);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(
            Authentication authentication,
            @PathVariable Long id,
            @RequestBody CreateTaskRequest request) {
        String username = authentication.getName();
        TaskDTO task = taskService.updateTask(username, id, request);
        return ResponseEntity.ok(task);
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<TaskDTO> toggleTask(Authentication authentication, @PathVariable Long id) {
        String username = authentication.getName();
        TaskDTO task = taskService.toggleTask(username, id);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(Authentication authentication, @PathVariable Long id) {
        String username = authentication.getName();
        taskService.deleteTask(username, id);
        return ResponseEntity.noContent().build();
    }
}
