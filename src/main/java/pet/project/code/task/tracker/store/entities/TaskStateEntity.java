package pet.project.code.task.tracker.store.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;

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
    
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "left_task_state_id")
    private TaskStateEntity leftTaskState;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "right_task_state_id")
    private TaskStateEntity rightTaskState;

    @Builder.Default
    private Instant createdAt = Instant.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private ProjectEntity project;

    @Builder.Default
    @OneToMany(mappedBy = "taskState")
    private List<TaskEntity> taskList = new ArrayList<>();
}
