package pet.project.code.task.tracker.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import pet.project.code.task.tracker.api.dto.tasks.TaskDto;
import pet.project.code.task.tracker.store.entities.TaskEntity;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {
    @Mapping(target = "taskStateId", source = "taskState.id")
    TaskDto toTaskDto(TaskEntity taskEntity);

    TaskEntity toTaskEntity(TaskDto taskDto);

    @Mapping(target = "taskStateId", source = "taskState.id")
    List<TaskDto> toListTaskDto(List<TaskEntity> tasks);
}
