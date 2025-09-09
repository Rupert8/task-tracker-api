package integration.project;

import annotation.IT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import pet.project.api.service.TaskStateService;
import pet.project.dto.taskStates.TaskStateDto;
import pet.project.entities.ProjectEntity;
import pet.project.entities.TaskStateEntity;
import pet.project.jpaRepositories.ProjectRepository;
import pet.project.jpaRepositories.TaskStateRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
@Slf4j
public class TaskStateServiceTest {

    private final ProjectRepository projectRepository;

    private final TaskStateService taskStateService;
    private final TaskStateRepository taskStateRepository;

    @Test
    public void findAllTaskState() {
        ProjectEntity newProject = ProjectEntity.builder()
                .name("Project")
                .build();

        ProjectEntity saveProject = projectRepository.saveAndFlush(newProject);

        assertNotNull(saveProject);

        TaskStateEntity newTaskState = TaskStateEntity.builder()
                .name("TaskState")
                .build();

        TaskStateEntity saveTaskState = taskStateRepository.saveAndFlush(newTaskState);

        assertNotNull(saveTaskState);

        List<TaskStateDto> allTaskStates = taskStateService.findAllTaskStates(newProject.getId());

        assertNotNull(allTaskStates);
    }


    @Test
    public void createTaskState() {
        ProjectEntity newProject = ProjectEntity.builder()
                .name("Project")
                .build();

        ProjectEntity saveProject = projectRepository.saveAndFlush(newProject);

        assertNotNull(saveProject);

        TaskStateDto saveTaskState = taskStateService.createTaskState(saveProject.getId(), "CreateTaskState");

        assertNotNull(saveTaskState);
        assertEquals("CreateTaskState", saveTaskState.getName());

        boolean exist = taskStateRepository.findByName("CreateTaskState").isPresent();

        assertTrue(exist);

    }

    @Test
    public void updateTaskInfoState() {
        ProjectEntity newProject = ProjectEntity.builder()
                .name("Project")
                .build();

        ProjectEntity saveProject = projectRepository.saveAndFlush(newProject);

        TaskStateDto saveTaskState = taskStateService.createTaskState(saveProject.getId(), "CreateTaskState");

        assertNotNull(saveTaskState);

        TaskStateDto updateTaskState = taskStateService.updateTaskStateInfo(saveTaskState.getId(), "UpdateTaskState");

        assertNotNull(updateTaskState);
        assertEquals(saveTaskState.getId(), updateTaskState.getId());
        assertEquals("UpdateTaskState", updateTaskState.getName());

    }

    @Test
    public void shouldMoveTaskStateToBeginning_whenLeftTaskStateNull() {
        ProjectEntity newProject = ProjectEntity.builder()
                .name("Project")
                .build();

        ProjectEntity saveProject = projectRepository.saveAndFlush(newProject);

        TaskStateDto saveTaskState = taskStateService.createTaskState(saveProject.getId(), "LeftMoveTaskState");

        assertNotNull(saveTaskState);

        List<TaskStateDto> allTaskStates = taskStateService.moveTaskState(saveTaskState.getId(), null, saveProject.getId());

        assertNotNull(allTaskStates);

        boolean ifFirstTaskState = taskStateRepository.findByProjectIdAndLeftTaskStateIsNull(saveProject.getId())
                .filter(taskStateEntity -> taskStateEntity.getName().equals("LeftMoveTaskState"))
                .isPresent();
        assertTrue(ifFirstTaskState);

    }

    @Test
    public void shouldMoveTaskStateToEnd_whenRightTaskStateNull() {
        ProjectEntity newProject = ProjectEntity.builder()
                .name("Project")
                .build();

        ProjectEntity saveProject = projectRepository.saveAndFlush(newProject);

        TaskStateDto firstTaskState = taskStateService.createTaskState(saveProject.getId(), "LeftMoveTaskState");
        TaskStateDto lastTaskState = taskStateService.createTaskState(saveProject.getId(), "RightMoveTaskState");

        assertNotNull(firstTaskState);
        assertNotNull(lastTaskState);

        List<TaskStateDto> allTaskStates = taskStateService.moveTaskState(firstTaskState.getId(), lastTaskState.getId(), saveProject.getId());

        assertNotNull(allTaskStates);

        boolean exist = taskStateRepository.findByProjectIdAndRightTaskStateIsNull(saveProject.getId())
                .filter(taskStateEntity -> taskStateEntity.getName().equals("LeftMoveTaskState"))
                .isPresent();

        assertTrue(exist);
    }

    @Test
    public void shouldMoveTaskStateToCenter_whenRightTaskStateAndLeftTaskStateNotNull() {
        ProjectEntity newProject = ProjectEntity.builder()
                .name("Project")
                .build();

        ProjectEntity saveProject = projectRepository.saveAndFlush(newProject);

        TaskStateDto firstTaskState = taskStateService.createTaskState(saveProject.getId(), "LeftMoveTaskState");
        TaskStateDto centerTaskState = taskStateService.createTaskState(saveProject.getId(), "CenterTaskState");
        TaskStateDto lastTaskState = taskStateService.createTaskState(saveProject.getId(), "RightMoveTaskState");

        assertNotNull(firstTaskState);
        assertNotNull(centerTaskState);
        assertNotNull(lastTaskState);

        List<TaskStateDto> allTaskStates = taskStateService.moveTaskState(firstTaskState.getId(), centerTaskState.getId(), saveProject.getId());

        assertNotNull(allTaskStates);

        boolean exist = taskStateRepository.findByProjectIdAndName(saveProject.getId(), firstTaskState.getName())
                .filter(taskStateEntity -> taskStateEntity.getName().equals("LeftMoveTaskState"))
                .isPresent();

        assertTrue(exist);
    }
}
