package pet.project.filters;

import java.time.Instant;

public record ProjectFilter(
        String name,
        Instant createdAt) {
}
