package ssc2.amigo.taskmanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class Task {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime dueDate;

    public enum TaskStatus {
        TODO,
        IN_PROGRESS,
        DONE
    }
}

