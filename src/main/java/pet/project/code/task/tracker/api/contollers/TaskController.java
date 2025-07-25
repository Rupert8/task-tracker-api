package pet.project.code.task.tracker.api.contollers;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pet.project.code.task.tracker.api.dto.tasks.TaskDto;
import pet.project.code.task.tracker.api.service.TaskService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project/task-state/")
@Validated
public class TaskController {
    private final TaskService taskService;

    private static final String CREATE_TASK = "/{task_state_id}/tasks";
    private static final String UPDATE_TASK_INFO = "/tasks/{task_id}/change";
    private static final String UPDATE_TASK_POSITION = "/{task_state_id}/tasks/{task_id}/change-position";
    private static final String DELETE_TASK = "/tasks/{task_id}";

    @PostMapping(CREATE_TASK)
    public TaskDto createTask(@RequestParam @NotBlank(message = "Task name cannot be empty") String name,
                              @RequestParam(required = false) String description,
                              @PathVariable("task_state_id") Long taskStateId) {
        return taskService.createTask(taskStateId,name,description);
    }

    @PatchMapping(UPDATE_TASK_INFO)
    public TaskDto updateTask(@RequestParam @NotBlank(message = "Task name cannot be empty") String name,
                              @RequestParam(required = false) String description,
                              @PathVariable("task_id") Long taskId) {
        return taskService.updateTaskInfo(taskId,name,description);
    }

    @PatchMapping(UPDATE_TASK_POSITION)
    public TaskDto updateTaskPosition(@PathVariable Long task_id,
                                      @PathVariable Long task_state_id) {
        return taskService.moveTaskToTaskState(task_id,task_state_id);
    }

    @DeleteMapping(DELETE_TASK)
    public ResponseEntity<Void> deleteTask(@PathVariable Long task_id) {
        taskService.deleteTask(task_id);
        return ResponseEntity.noContent().build();
    }
}
