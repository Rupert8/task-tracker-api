package pet.project.code.task.tracker.api.dto.projects;

import java.time.Instant;

public record ProjectFilter(
        String name,
        Instant createdAt) {
}
