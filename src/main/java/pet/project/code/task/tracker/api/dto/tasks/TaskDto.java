package pet.project.code.task.tracker.api.dto.tasks;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    @NotNull
    private Long id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    @JsonProperty("task_state_id")
    private Long taskStateId;

    @NotNull
    @JsonProperty("create_date")
    private Instant createdAt;
}
