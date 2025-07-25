package pet.project.code.task.tracker.api.contollers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pet.project.code.task.tracker.api.dto.taskStates.TaskStateDto;
import pet.project.code.task.tracker.api.mapper.TaskStateMapper;
import pet.project.code.task.tracker.api.service.TaskStateService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
@Validated
public class TaskStateController {
    private final TaskStateService taskStateService;

    private static final String CREATE_TASK_STATE = "/{project_id}/task-state";
    private static final String FIND_ALL_TASK_STATE = "/{project_id}/task-state";
    private static final String CHANGE_TASK_STATE_INFO = "/task-state/change";
    private static final String CHANGE_TASK_STATE_POSITION = "/{project_id}/task-state/change-position";
    private static final String DELETE_TASK_STATE = "/task-state/{project_id}/task-state/delete";

    @PostMapping(CREATE_TASK_STATE)
    public TaskStateDto createTaskState(@PathVariable("project_id") Long projectId,
                                        @RequestParam @NotBlank(message = "Назва категорії не може бути порожньою") String name) {
        return taskStateService.createTaskState(projectId, name);
    }

    @GetMapping(FIND_ALL_TASK_STATE)
    public List<TaskStateDto> findAllTaskState(@PathVariable("project_id") Long projectId) {
        var taskState = taskStateService.findAllTaskStates(projectId);
        log.info("taskStates: {}", taskState);
        return taskState;
    }

    @PatchMapping(CHANGE_TASK_STATE_INFO)
    public TaskStateDto changeTaskStateInfo(@RequestParam Long taskStateId,
                                            @RequestParam String name) {
        return taskStateService.updateInfoTaskState(taskStateId, name);
    }

    @PatchMapping(CHANGE_TASK_STATE_POSITION)
    public List<TaskStateDto> changeTaskStatePosition(@RequestParam Long movedTaskStateId,
                                                      @RequestParam(required = false) Long beforeTaskStateId,
                                                      @PathVariable("project_id") Long projectId) {
        return taskStateService.moveTaskState(movedTaskStateId,beforeTaskStateId,projectId);
    }

    @DeleteMapping(DELETE_TASK_STATE)
    public List<TaskStateDto> deleteTaskState(@RequestParam Long taskStateId,
                                              @PathVariable("project_id") Long projectId) {
        return taskStateService.deleteTaskState(taskStateId,projectId);
    }
}
