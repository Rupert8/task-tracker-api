package pet.project.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "project")
@Getter
@Setter
@ToString(exclude = "taskStates")
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

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private List<TaskStateEntity> taskStates = new ArrayList<>();


    public void addTaskState(TaskStateEntity taskState) {
        if (taskStates == null) {
            taskStates = new ArrayList<>();
        }
        taskStates.add(taskState);
        taskState.setProject(this);
    }
}
