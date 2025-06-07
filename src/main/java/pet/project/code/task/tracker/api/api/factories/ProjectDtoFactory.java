package pet.project.code.task.tracker.api.api.factories;

import org.springframework.stereotype.Component;
import pet.project.code.task.tracker.api.api.dto.ProjectDto;
import pet.project.code.task.tracker.api.store.entities.ProjectEntity;

@Component
public class ProjectDtoFactory {

    public ProjectDto makeProjectDto(ProjectEntity project){
        return ProjectDto.builder()
                .id(project.getId())
                .name(project.getName())
                .createAt(project.getCreatedAt())
                .updateAt(project.getUpdateAt()).build();
    }
}
