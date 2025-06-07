package pet.project.code.task.tracker.api.store.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task_state")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskStateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String name;

    private Long ordinal;

    @Builder.Default
    private Instant createdAt = Instant.now();

    @Builder.Default
    @OneToMany(fetch =  FetchType.LAZY)
    @JoinColumn(name = "task_state_id",referencedColumnName = "id")
    private List<TaskEntity> taskList = new ArrayList<>();
}
