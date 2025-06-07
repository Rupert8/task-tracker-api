package pet.project.code.task.tracker.api.api.factories;

import org.springframework.stereotype.Component;
import pet.project.code.task.tracker.api.api.dto.TaskStateDto;
import pet.project.code.task.tracker.api.store.entities.TaskStateEntity;

@Component
public class TaskStateDtoFactory {

    public TaskStateDto makeTaskStateDto(TaskStateEntity taskState){
        return TaskStateDto.builder()
                .id(taskState.getId())
                .name(taskState.getName())
                .createdAt(taskState.getCreatedAt())
                .ordinal(taskState.getOrdinal()).build();
    }
}

