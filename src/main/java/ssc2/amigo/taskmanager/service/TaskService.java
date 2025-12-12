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

    // Lấy toàn bộ task
    public List<TaskResponse> getAllTasks() {
        List<TaskResponse> result = new ArrayList<>();
        for (Task task : tasks.values()) {
            result.add(toResponse(task));
        }
        return result;
    }

    // Lấy task theo ID
    public TaskResponse getTaskById(Long id) {
        Task task = tasks.get(id);
        if (task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task không tồn tại");
        }
        return toResponse(task);
    }

    // Tạo task
    public TaskResponse createTask(CreateTaskRequest request) {
        Long id = idGenerator.incrementAndGet();

        Task task = Task.builder()
                .id(id)
                .title(request.getTitle())
                .description(request.getDescription())
                .status(TaskStatus.TODO)
                .createdAt(LocalDateTime.now())
                .dueDate(request.getDueDate().atStartOfDay())   // ⭐ QUAN TRỌNG
                .build();

        tasks.put(id, task);
        return toResponse(task);
    }

    // Cập nhật trạng thái task
    public TaskResponse updateStatus(Long id, TaskStatus newStatus) {
        Task task = tasks.get(id);
        if (task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task không tồn tại");
        }

        task.setStatus(newStatus);
        tasks.put(id, task);

        return toResponse(task);
    }

    // Xoá task
    public void deleteTask(Long id) {
        if (!tasks.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task không tồn tại");
        }
        tasks.remove(id);
    }

    // Helper: convert entity → response
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
