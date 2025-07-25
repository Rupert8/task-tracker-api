package pet.project.code.task.tracker.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import pet.project.code.task.tracker.api.dto.taskStates.TaskStateDto;
import pet.project.code.task.tracker.store.entities.TaskStateEntity;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {TaskMapper.class})
public interface TaskStateMapper {
    @Mapping(target = "leftTaskState", source = "taskState.leftTaskState.id") // Мапінг для leftTaskState
    @Mapping(target = "rightTaskState", source = "taskState.rightTaskState.id") // Мапінг для rightTaskState
    TaskStateDto toTaskStateDto(TaskStateEntity taskState);

    //TaskStateEntity toTaskStateEntity(TaskStateDto taskStateDto);

    @Mapping(target = "leftTaskState", source = "taskState.leftTaskState.id") // Мапінг для leftTaskState
    @Mapping(target = "rightTaskState", source = "taskState.rightTaskState.id")// Мапінг для rightTaskState
    List<TaskStateDto> toListTaskStateDto(List<TaskStateEntity> taskStateEntities);

}
