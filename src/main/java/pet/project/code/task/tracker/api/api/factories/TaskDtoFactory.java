package pet.project.code.task.tracker.api.api.factories;

import org.springframework.stereotype.Component;
import pet.project.code.task.tracker.api.api.dto.TaskDto;
import pet.project.code.task.tracker.api.store.entities.TaskEntity;

@Component
public class TaskDtoFactory {

    public TaskDto makeTasDto(TaskEntity task){
        return TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .createdAt(task.getCreatedAt())
                .description(task.getDescription()).build();
    }
}
