package pet.project.code.task.tracker.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pet.project.code.task.tracker.store.entities.TaskStateEntity;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface TaskStateRepository extends JpaRepository<TaskStateEntity, Long> {
    Stream<TaskStateEntity> streamAllByProjectId(Long projectId);

    Optional<TaskStateEntity> findByName(String name);

    Optional<TaskStateEntity> findByProjectIdAndRightTaskStateIsNull(Long projectId);

    Optional<TaskStateEntity> findByProjectIdAndLeftTaskStateIsNull(Long projectId);

    Optional<TaskStateEntity> findByLeftTaskStateId(Long leftTaskStateId);

    Optional<TaskStateEntity> findByRightTaskStateId(Long rightTaskStateId);

}
