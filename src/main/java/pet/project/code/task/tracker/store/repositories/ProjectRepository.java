package pet.project.code.task.tracker.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import pet.project.code.task.tracker.store.entities.ProjectEntity;
import pet.project.code.task.tracker.store.repositories.filter.FilterProjectRepository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends
        JpaRepository<ProjectEntity, Long>,
        QuerydslPredicateExecutor<ProjectEntity> {

    Optional<ProjectEntity> findByName(String name);
}
