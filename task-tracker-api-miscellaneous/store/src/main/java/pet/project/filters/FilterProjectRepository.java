package pet.project.filters;

import pet.project.entities.ProjectEntity;

import java.util.List;

public interface FilterProjectRepository {
    List<ProjectEntity> findAllByFilter(ProjectFilter filter);
}
