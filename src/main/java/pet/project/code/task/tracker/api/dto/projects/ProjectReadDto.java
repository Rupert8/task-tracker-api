package pet.project.code.task.tracker.api.dto.projects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class ProjectReadDto {
    @NotNull
    private Long id;

    @NotBlank
    @Size(min = 1, max = 255)
    private String name;

    @NotNull
    @JsonProperty("create_date")
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss", timezone = "UTC")
    private Instant createdAt;

    @NotNull
    @JsonProperty("update_date")
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss", timezone = "UTC")
    private Instant updateAt;

}
