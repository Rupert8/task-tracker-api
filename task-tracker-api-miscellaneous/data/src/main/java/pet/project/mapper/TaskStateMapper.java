package pet.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import pet.project.dto.taskStates.TaskStateDto;
import pet.project.entities.TaskStateEntity;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {TaskMapper.class})
public interface TaskStateMapper {
    @Mapping(target = "leftTaskState", source = "taskState.leftTaskState.id")
    @Mapping(target = "rightTaskState", source = "taskState.rightTaskState.id")
    TaskStateDto toTaskStateDto(TaskStateEntity taskState);

    @Mapping(target = "leftTaskState", source = "taskState.leftTaskState.id")
    @Mapping(target = "rightTaskState", source = "taskState.rightTaskState.id")
    List<TaskStateDto> toListTaskStateDto(List<TaskStateEntity> taskStateEntities);

}
