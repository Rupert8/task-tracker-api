package pet.project.code.task.tracker.api.store.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "project")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String name;

    @Builder.Default
    private Instant createdAt = Instant.now();

    @Builder.Default
    private Instant updateAt = Instant.now();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private List<TaskStateEntity> taskStates = new ArrayList<>();
}
