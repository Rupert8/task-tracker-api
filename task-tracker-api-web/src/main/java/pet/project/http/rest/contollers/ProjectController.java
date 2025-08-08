package pet.project.http.rest.contollers;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pet.project.api.service.ProjectService;
import pet.project.filters.ProjectFilter;
import pet.project.dto.projects.ProjectReadDto;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Validated
public class ProjectController {
    private final ProjectService projectService;

    public static final String CREATE_PROJECT = "projects";
    public static final String EDIT_PROJECT = "project/{project_id}";
    public static final String DELETE_PROJECT = "project/{project_id}";
    public static final String DELETE_ALL_PROJECTS = "projects";
    public static final String FIND_PROJECT = "project/{project_id}";
    public static final String FIND_ALL_PROJECTS = "projects";

    @GetMapping(FIND_ALL_PROJECTS)
    public List<ProjectReadDto> findAllProjects(ProjectFilter filter) {
        return projectService.findAllProjects(filter);
    }

    @GetMapping(FIND_PROJECT)
    public ProjectReadDto findProject(@PathVariable Long project_id) {
        return projectService.findProjectById(project_id);
    }

    @PostMapping(CREATE_PROJECT)
    public ProjectReadDto createProject(@RequestParam
                                        @NotBlank(message = "Project name cannot be empty") String name) {
        return projectService.createProject(name);
    }

    @PatchMapping(EDIT_PROJECT)
    public ProjectReadDto updateProject(
            @PathVariable("project_id") Long projectId,
            @RequestParam @NotBlank(message = "Project name cannot be empty") String name) {
        return projectService.updateProject(projectId, name);
    }

    @DeleteMapping(DELETE_ALL_PROJECTS)
    public void deleteAllProjects() {
        projectService.deleteAllProject();
    }

    @DeleteMapping(DELETE_PROJECT)
    public void deleteProject(
            @PathVariable("project_id") Long projectId) {
        projectService.deleteProjectById(projectId);
    }
}
