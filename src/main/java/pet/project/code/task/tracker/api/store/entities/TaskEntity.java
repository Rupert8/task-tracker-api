package pet.project.code.task.tracker.api.store.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "task")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String name;

    private String description;

    @Builder.Default
    private Instant createdAt = Instant.now();
}
