package pet.project.jpaRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import pet.project.entities.ProjectEntity;

import java.util.Optional;

@Repository
public interface ProjectRepository extends
        JpaRepository<ProjectEntity, Long>,
        QuerydslPredicateExecutor<ProjectEntity> {

    Optional<ProjectEntity> findByName(String name);
}
