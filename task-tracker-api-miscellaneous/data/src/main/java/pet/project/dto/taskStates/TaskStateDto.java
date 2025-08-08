package pet.project.dto.taskStates;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import pet.project.dto.tasks.TaskDto;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class TaskStateDto {
    @NotNull
    private Long id;

    @NotBlank()
    private String name;

    @JsonProperty("left_task_state")
    private Long leftTaskState;

    @JsonProperty("right_task_state")
    private Long rightTaskState;

    @JsonProperty("create_date")
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss", timezone = "UTC")
    private Instant createdAt;

    @NotNull
    List<TaskDto> taskList;

}
