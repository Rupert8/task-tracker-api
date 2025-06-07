package pet.project.code.task.tracker.api.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.lang.NonNull;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    @NonNull
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    @JsonProperty("create_date")
    private Instant createdAt;
}
