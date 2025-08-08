package pet.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import pet.project.dto.tasks.TaskDto;
import pet.project.entities.TaskEntity;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {
    @Mapping(target = "taskStateId", source = "taskState.id")
    TaskDto toTaskDto(TaskEntity taskEntity);

    TaskEntity toTaskEntity(TaskDto taskDto);

    @Mapping(target = "taskStateId", source = "taskState.id")
    List<TaskDto> toListTaskDto(List<TaskEntity> tasks);
}
