package pet.project.code.task.tracker.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.project.code.task.tracker.api.dto.taskStates.TaskStateDto;
import pet.project.code.task.tracker.api.exceptions.BadRequestException;
import pet.project.code.task.tracker.api.exceptions.NotFoundException;
import pet.project.code.task.tracker.api.mapper.TaskStateMapper;
import pet.project.code.task.tracker.store.entities.ProjectEntity;
import pet.project.code.task.tracker.store.entities.TaskStateEntity;
import pet.project.code.task.tracker.store.repositories.ProjectRepository;
import pet.project.code.task.tracker.store.repositories.TaskStateRepository;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskStateService {
    private final TaskStateRepository taskStateRepository;
    private final TaskStateMapper taskStateMapper;

    private final ProjectRepository projectRepository;

    public List<TaskStateDto> findAllTaskStates(Long projectId) {
        return taskStateMapper.toListTaskStateDto(taskStateRepository
                .streamAllByProjectId(projectId).toList());
    }

    @Transactional
    public TaskStateDto createTaskState(Long projectId, String taskStateName) {
        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("TaskState not found"));

        taskStateRepository.findByName(taskStateName).ifPresent(taskState -> {
            throw new BadRequestException(String.format("TaskState \"%s\" Already exist", taskStateName));
        });

        TaskStateEntity rightTaskState = taskStateRepository.findByProjectIdAndRightTaskStateIsNull(projectId)
                .orElse(null);

        TaskStateEntity createTaskState = TaskStateEntity.builder()
                .name(taskStateName)
                .leftTaskState(rightTaskState)
                .rightTaskState(null)
                .project(projectEntity)
                .build();

        TaskStateEntity savedTaskState = taskStateRepository.saveAndFlush(createTaskState);
        log.info("createTaskState {}", createTaskState);
        if (rightTaskState != null) {
            rightTaskState.setRightTaskState(createTaskState);
            taskStateRepository.saveAndFlush(rightTaskState);
        }

        projectEntity.addTaskState(savedTaskState);

        return taskStateMapper.toTaskStateDto(createTaskState);
    }

    @Transactional
    public TaskStateDto updateInfoTaskState(Long taskStateId, String taskStateName) {
        if (taskStateName.trim().isEmpty()) {
            throw new BadRequestException("TaskState name cannot be empty");
        }

        taskStateRepository.findByName(taskStateName)
                .filter(anotherTaskState -> !Objects.equals(anotherTaskState.getId(), taskStateId))
                .ifPresent(taskStateEntity -> {
                    throw new BadRequestException(String.format("TaskState \"%s\" Already exist", taskStateName));
                });

        TaskStateEntity changeTaskState = getTaskStateOrThrow(taskStateId);

        changeTaskState.setName(taskStateName);

        return taskStateMapper.toTaskStateDto(changeTaskState);
    }


    @Transactional
    public List<TaskStateDto> moveTaskState(Long movedTaskStateId, Long beforeMovedTaskStateId, Long projectId) {
        TaskStateEntity movedTaskState = detachTaskStateFromNeighbor(movedTaskStateId);

        boolean beforeMovedRightTaskStateNotNull = getTaskStateOrThrow(beforeMovedTaskStateId).getRightTaskState() == null;

        if (beforeMovedTaskStateId == null) {
            movedFirst(projectId, movedTaskState);
        } else if (beforeMovedRightTaskStateNotNull) {
            movedEnd(projectId, movedTaskState);
        } else {
            movedCenter(beforeMovedTaskStateId, movedTaskState);
        }

        return taskStateMapper.toListTaskStateDto(taskStateRepository.streamAllByProjectId(projectId).toList());
    }

    private TaskStateEntity detachTaskStateFromNeighbor(Long movedTaskStateId) {
        TaskStateEntity movedTaskState = getTaskStateOrThrow(movedTaskStateId);

        TaskStateEntity leftTaskState = movedTaskState.getLeftTaskState();

        TaskStateEntity rightTaskState = movedTaskState.getRightTaskState();

        if (leftTaskState != null) {
            leftTaskState.setRightTaskState(rightTaskState);
            taskStateRepository.saveAndFlush(leftTaskState);
        }

        if (rightTaskState != null) {
            rightTaskState.setLeftTaskState(leftTaskState);
            taskStateRepository.saveAndFlush(rightTaskState);
        }

        return movedTaskState;
    }

    private void movedCenter(Long beforeMovedTaskStateId, TaskStateEntity movedTaskState) {
        TaskStateEntity beforeTaskState = getTaskStateOrThrow(beforeMovedTaskStateId);
        TaskStateEntity beforeRightTaskState = beforeTaskState.getRightTaskState();

        beforeTaskState.setRightTaskState(movedTaskState);
        taskStateRepository.saveAndFlush(beforeTaskState);
        beforeRightTaskState.setLeftTaskState(movedTaskState);
        taskStateRepository.saveAndFlush(beforeRightTaskState);

        movedTaskState.setLeftTaskState(beforeTaskState);
        movedTaskState.setRightTaskState(beforeRightTaskState);
        taskStateRepository.saveAndFlush(movedTaskState);
    }

    private void movedEnd(Long projectId, TaskStateEntity movedTaskState) {
        TaskStateEntity lastTaskState = taskStateRepository.findByProjectIdAndRightTaskStateIsNull(projectId)
                .orElseThrow(() -> new NotFoundException(String.format("Last Task State doesn't exist")));

        lastTaskState.setRightTaskState(movedTaskState);
        taskStateRepository.save(lastTaskState);

        movedTaskState.setLeftTaskState(lastTaskState);
        movedTaskState.setRightTaskState(null);
    }

    private void movedFirst(Long projectId, TaskStateEntity movedTaskState) {
        TaskStateEntity firstTaskState = taskStateRepository.findByProjectIdAndLeftTaskStateIsNull(projectId)
                .orElseThrow(() -> new NotFoundException(String.format("First Task State doesn't exist")));

        firstTaskState.setLeftTaskState(movedTaskState);
        taskStateRepository.save(firstTaskState);


        movedTaskState.setLeftTaskState(null);
        movedTaskState.setRightTaskState(firstTaskState);
        taskStateRepository.save(movedTaskState);
    }

    @Transactional
    public List<TaskStateDto> deleteTaskState(Long taskStateId, Long projectId) {
        TaskStateEntity deleteTaskState = detachTaskStateFromNeighbor(taskStateId);

        taskStateRepository.delete(deleteTaskState);

        return taskStateMapper.toListTaskStateDto(taskStateRepository
                .streamAllByProjectId(projectId).toList());
    }
//    @Transactional
//    public TaskStateDto updateTaskState(String name, Long projectId, Long beforeCurrentTaskId, Long taskStateId) {
//        TaskStateEntity movedTaskState = taskStateRepository.findById(taskStateId)
//                .orElseThrow(() -> new NotFoundException("TaskState not found"));
//
//        if (beforeCurrentTaskId == null) {
//            var firstTaskState = taskStateRepository.findByProjectIdAndLeftTaskStateIsNull(projectId)
//                    .ifPresentOrElse(taskStateEntity -> {
//                        firstTaskState.setLeftTaskState(movedTaskState);
//                        taskStateRepository.saveAndFlush(firstTaskState);
//
//                        movedTaskState.setLeftTaskState(null);
//                        movedTaskState.setRightTaskState(firstTaskState);
//                        taskStateRepository.saveAndFlush(movedTaskState);
//                    });
//
//            return taskStateMapper.toTaskStateDto(movedTaskState);
//        }
//
//        TaskStateEntity beforeMovedTaskState = taskStateRepository.findById(beforeCurrentTaskId)
//                .orElse(null);
//
//        taskStateRepository.findByName(name)
//                .filter(anotherTaskState -> !Objects.equals(anotherTaskState.getId(), taskStateId))
//                .ifPresent(anotherTaskState -> {
//                    throw new BadRequestException("TaskState already - \"%s\"exist");
//                });
//
//        Optional<TaskStateEntity> movedRightTaskState = Optional.ofNullable(movedTaskState.getRightTaskState());
//
//        Optional<TaskStateEntity> movedLeftTaskState = Optional.ofNullable(movedTaskState.getLeftTaskState());
//
//        movedLeftTaskState.ifPresent(leftTaskState -> {
//            leftTaskState.setRightTaskState(movedTaskState.getRightTaskState());
//            taskStateRepository.saveAndFlush(leftTaskState);
//        });
//
//        movedRightTaskState.ifPresent(rightTaskState -> {
//            rightTaskState.setLeftTaskState(movedTaskState.getLeftTaskState());
//            taskStateRepository.saveAndFlush(rightTaskState);
//        });
//
//        if (beforeMovedTaskState.getRightTaskState() == null) {
//            beforeMovedTaskState.setRightTaskState(movedTaskState);
//            taskStateRepository.saveAndFlush(beforeMovedTaskState);
//
//            movedTaskState.setRightTaskState(null);
//            movedTaskState.setLeftTaskState(beforeMovedTaskState);
//            taskStateRepository.saveAndFlush(movedTaskState);
//
//            return taskStateMapper.toTaskStateDto(movedTaskState);
//        } else {
//            TaskStateEntity beforeMovedRightTaskState = beforeMovedTaskState.getRightTaskState();
//
//            beforeMovedTaskState.setRightTaskState(movedTaskState);
//            taskStateRepository.saveAndFlush(beforeMovedTaskState);
//
//            movedTaskState.setName(name);
//            movedTaskState.setLeftTaskState(beforeMovedTaskState);
//            movedTaskState.setRightTaskState(beforeMovedRightTaskState);
//            taskStateRepository.saveAndFlush(movedTaskState);
//
//            return taskStateMapper.toTaskStateDto(movedTaskState);
//        }
//    }

    private TaskStateEntity getTaskStateOrThrow(Long taskStateId) {
        return taskStateRepository.findById(taskStateId)
                .orElseThrow(() -> new NotFoundException
                        (String.format("Task State with '%d' doesn't exist ", taskStateId)));
    }
}
