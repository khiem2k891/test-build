package ssc2.amigo.taskmanager.service;

import ssc2.amigo.taskmanager.dto.CreateTaskRequest;
import ssc2.amigo.taskmanager.dto.TaskResponse;
import ssc2.amigo.taskmanager.model.Task;
import ssc2.amigo.taskmanager.model.Task.TaskStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {

    private final Map<Long, Task> tasks = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    // ✅ Controller gọi getAllTasks()
    public List<TaskResponse> getAllTasks() {
        List<TaskResponse> result = new ArrayList<>();
        for (Task task : tasks.values()) {
            result.add(toResponse(task));
        }
        return result;
    }

    // ✅ Controller gọi getTaskById(Long)
    public TaskResponse getTaskById(Long id) {
        Task task = tasks.get(id);
        if (task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task không tồn tại");
        }
        return toResponse(task);
    }

    // ✅ Controller gọi createTask(CreateTaskRequest)
    public TaskResponse createTask(CreateTaskRequest request) {
        Long id = idGenerator.incrementAndGet();

        Task task = Task.builder()
                .id(id)
                .title(request.getTitle())
                .description(request.getDescription())
                .status(TaskStatus.TODO)
                .createdAt(LocalDateTime.now())
                .dueDate(request.getDueDate())
                .build();

        tasks.put(id, task);
        return toResponse(task);
    }

    // ✅ Controller gọi updateStatus(Long, TaskStatus)
    public TaskResponse updateStatus(Long id, TaskStatus newStatus) {
        Task task = tasks.get(id);
        if (task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task không tồn tại");
        }

        task.setStatus(newStatus);
        tasks.put(id, task);

        return toResponse(task);
    }

    // ✅ Controller gọi deleteTask(Long)
    public void deleteTask(Long id) {
        if (!tasks.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task không tồn tại");
        }
        tasks.remove(id);
    }

    // helper convert entity -> response DTO
    private TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt(),
                task.getDueDate()
        );
    }
}

