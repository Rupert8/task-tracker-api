package pet.project.code.task.tracker.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import pet.project.code.task.tracker.api.dto.projects.ProjectReadDto;
import pet.project.code.task.tracker.store.entities.ProjectEntity;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectMapper {
    ProjectReadDto toProjectDto(ProjectEntity project);

    ProjectEntity toProjectEntity(ProjectReadDto projectReadDto);

    List<ProjectReadDto> toListProjectDto(List<ProjectEntity> projects);
}
