package pet.project.code.task.tracker.store.repositories.filter;

import pet.project.code.task.tracker.api.dto.projects.ProjectFilter;
import pet.project.code.task.tracker.store.entities.ProjectEntity;

import java.util.List;

public interface FilterProjectRepository {
    List<ProjectEntity> findAllByFilter(ProjectFilter filter);
}
