package pet.project.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.project.api.exceptions.BadRequestException;
import pet.project.api.exceptions.NotFoundException;
import pet.project.dto.tasks.TaskDto;
import pet.project.entities.TaskEntity;
import pet.project.entities.TaskStateEntity;
import pet.project.jpaRepositories.TaskRepository;
import pet.project.jpaRepositories.TaskStateRepository;
import pet.project.mapper.TaskMapper;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    private final TaskStateRepository taskStateRepository;

    @Transactional
    public TaskDto createTask(Long taskStateId, String name, String description) {
        taskRepository.findByNameAndTaskStateId(name, taskStateId).ifPresent(taskEntity -> {
            throw new BadRequestException(String.format("Task with name: '%s' already exists", name));
        });

        TaskStateEntity taskState = getTaskStateOrThrow(taskStateId);

        TaskEntity taskEntity = TaskEntity.builder()
                .name(name)
                .taskState(taskState)
                .description(description)
                .build();

        return taskMapper.toTaskDto(taskRepository.saveAndFlush(taskEntity));
    }

    private TaskStateEntity getTaskStateOrThrow(Long taskStateId) {
        return taskStateRepository.findById(taskStateId)
                .orElseThrow(() -> new NotFoundException(String.format("Task with id: '%s' not found", taskStateId)));
    }

    @Transactional
    public TaskDto updateTaskInfo(Long taskId, String name, String description) {
        TaskEntity updateTaskEntity = getTaskOrThrow(taskId);

        taskRepository.findByNameAndTaskStateId(name, updateTaskEntity.getTaskState().getId())
                .filter(taskEntity -> !taskEntity.getId().equals(updateTaskEntity.getId()))
                .ifPresent(task -> {
                    throw new BadRequestException(String.format("Task with name: '%s' already exists", name));
                });

        updateTaskEntity.setName(name);
        updateTaskEntity.setDescription(description);

        return taskMapper.toTaskDto(taskRepository.saveAndFlush(updateTaskEntity));
    }

    @Transactional
    public TaskDto moveTaskToTaskState(Long taskId, Long taskStateId) {
        TaskEntity task = getTaskOrThrow(taskId);

        TaskStateEntity taskState = getTaskStateOrThrow(taskStateId);

        task.setTaskState(taskState);

        return taskMapper.toTaskDto(taskRepository.save(task));
    }

    @Transactional
    public void deleteTask(Long taskId) {
        TaskEntity task = getTaskOrThrow(taskId);

        taskRepository.delete(task);
    }

    private TaskEntity getTaskOrThrow(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new NotFoundException
                                (String.format("Task with id: '%s' not found", taskId)));
    }
}
