package ssc2.amigo.taskmanager.dto;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateTaskRequest {

    @NotBlank(message = "Title không được để trống")
    @Size(max = 100, message = "Title tối đa 100 ký tự")
    private String title;

    @Size(max = 1000, message = "Description tối đa 1000 ký tự")
    private String description;

    @FutureOrPresent(message = "Due date phải là hiện tại hoặc tương lai")
    private LocalDate dueDate;
}

