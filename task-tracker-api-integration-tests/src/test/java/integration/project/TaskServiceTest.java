package integration.project;

import annotation.IT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import pet.project.api.service.TaskService;
import pet.project.api.service.TaskStateService;
import pet.project.dto.taskStates.TaskStateDto;
import pet.project.dto.tasks.TaskDto;
import pet.project.entities.ProjectEntity;
import pet.project.entities.TaskEntity;
import pet.project.jpaRepositories.ProjectRepository;
import pet.project.jpaRepositories.TaskRepository;

import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
@Slf4j
public class TaskServiceTest {

    private final TaskService taskService;
    private final TaskRepository taskRepository;

    private final TaskStateService taskStateService;

    private final ProjectRepository projectRepository;

    @Test
    public void createTask() {
        TaskStateDto saveTaskState = getTaskStateDto();

        TaskDto newTask = taskService.createTask(saveTaskState.getId(), "TestTask", "Description" );

        assertNotNull(newTask);

        boolean exists = taskRepository.findByNameAndTaskStateId(newTask.getName(), saveTaskState.getId()).isPresent();

        assertTrue(exists);
    }

    @Test
    public void updateTask() {
        TaskStateDto saveTaskState = getTaskStateDto();

        TaskDto newTask = taskService.createTask(saveTaskState.getId(), "TestTask", "Description");

        assertNotNull(newTask);

        TaskDto updateTaskState = taskService.updateTaskInfo(newTask.getId(), "TestTaskAfterUpdate", "Description");

        assertNotNull(updateTaskState);
        boolean update = newTask.getName().equals(updateTaskState.getName());
        assertFalse(update);
    }

    @Test
    public void moveTaskToTaskState(){
        TaskStateDto saveTaskState = getTaskStateDto();
        TaskStateDto moveTaskState = taskStateService.createTaskState(saveTaskState.getProject(), "MoveTaskState");

        assertNotNull(saveTaskState);
        assertNotNull(moveTaskState);

        TaskDto newTask = taskService.createTask(saveTaskState.getId(), "TestTask", "Description");

        TaskDto moveTask = taskService.moveTaskToTaskState(newTask.getId(), moveTaskState.getId());

        assertNotNull(moveTask);
        assertNotEquals(newTask.getTaskStateId(), moveTask.getTaskStateId());

    }

    @Test
    public void deleteTask(){
        TaskStateDto saveTaskState = getTaskStateDto();

        assertNotNull(saveTaskState);

        TaskDto newTask = taskService.createTask(saveTaskState.getId(), "TestTask", "Description");

        assertNotNull(newTask);

        taskService.deleteTask(newTask.getId());

        boolean exist = taskRepository.findByNameAndTaskStateId(newTask.getName(), saveTaskState.getId()).isPresent();
        assertFalse(exist);
    }

    private TaskStateDto getTaskStateDto() {
        ProjectEntity newProject = ProjectEntity.builder()
                .name("TestProject")
                .build();
        ProjectEntity saveProject = projectRepository.saveAndFlush(newProject);

        assertNotNull(saveProject);

        TaskStateDto saveTaskState = taskStateService.createTaskState(saveProject.getId(), "TestTaskState");

        assertNotNull(saveTaskState);
        return saveTaskState;
    }
}
