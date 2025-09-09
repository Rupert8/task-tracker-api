package integration.project;

import annotation.IT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import pet.project.api.service.ProjectService;
import pet.project.dto.projects.ProjectReadDto;
import pet.project.entities.ProjectEntity;
import pet.project.jpaRepositories.ProjectRepository;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceTest {

    private final ProjectService projectService;
    private final ProjectRepository projectRepository;

    @Test
    public void updateProjectTest(){
        ProjectEntity project = ProjectEntity.builder()
                .name("TestUpdateProject")
                .taskStates(new ArrayList<>())
                .build();

        ProjectEntity saveProject = projectRepository.saveAndFlush(project);

        assertNotNull(saveProject);

        ProjectReadDto updateProject = projectService.updateProject(saveProject.getId(), "Update Project");

        log.info("{}", updateProject);
        assertNotNull(updateProject);
        assertEquals("Update Project",updateProject.getName());
    }

    @Test
    public void createProjectTest() {
        String newProject = "Test Project";

        ProjectReadDto saveProject = projectService.createProject(newProject);

        assertNotNull(saveProject);
        assertEquals(newProject, saveProject.getName());

        boolean exists = projectRepository.findByName(newProject).isPresent();
        assertTrue(exists);
    }

    @Test
    public void deleteProjectTest(){
        ProjectEntity newProject = ProjectEntity.builder()
                .name("TestUpdateProject")
                .taskStates(new ArrayList<>())
                .build();
        ProjectEntity saveProject = projectRepository.saveAndFlush(newProject);

        assertNotNull(saveProject);

        projectService.deleteProjectById(saveProject.getId());

        assertFalse(projectRepository.findById(saveProject.getId()).isPresent());
    }
}
