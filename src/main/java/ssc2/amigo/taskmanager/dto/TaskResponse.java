package ssc2.amigo.taskmanager.dto;

import ssc2.amigo.taskmanager.model.Task.TaskStatus;

import java.time.LocalDateTime;

public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime dueDate;

    public TaskResponse(Long id, String title, String description,
                        TaskStatus status, LocalDateTime createdAt, LocalDateTime dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.dueDate = dueDate;
    }

    // getters ...
}

