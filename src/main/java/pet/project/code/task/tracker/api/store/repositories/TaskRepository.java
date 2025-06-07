package pet.project.code.task.tracker.api.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.project.code.task.tracker.api.store.entities.TaskEntity;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
