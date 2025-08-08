package pet.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import pet.project.dto.projects.ProjectReadDto;
import pet.project.entities.ProjectEntity;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectMapper {
    ProjectReadDto toProjectDto(ProjectEntity project);

    ProjectEntity toProjectEntity(ProjectReadDto projectReadDto);

    List<ProjectReadDto> toListProjectDto(List<ProjectEntity> projects);
}
