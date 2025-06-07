package pet.project.code.task.tracker.api.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.project.code.task.tracker.api.store.entities.ProjectEntity;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    Optional<ProjectEntity> findByName(String name);
}
