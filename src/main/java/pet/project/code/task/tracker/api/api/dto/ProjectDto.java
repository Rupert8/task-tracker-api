package pet.project.code.task.tracker.api.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.lang.NonNull;
import pet.project.code.task.tracker.api.store.entities.TaskStateEntity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {
    @NonNull
    private Long id;

    @NonNull
    private String name;

    @NonNull
    @JsonProperty("create_date")
    private Instant createAt;

    @NonNull
    @JsonProperty("update_date")
    private Instant updateAt;

}
