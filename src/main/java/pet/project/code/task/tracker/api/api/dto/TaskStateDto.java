package pet.project.code.task.tracker.api.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import pet.project.code.task.tracker.api.store.entities.TaskEntity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskStateDto {
    @NonNull
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private Long ordinal;

    @NonNull
    @JsonProperty("create_date")
    private Instant createdAt;

}
