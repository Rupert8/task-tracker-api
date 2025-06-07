package pet.project.code.task.tracker.api.api.contollers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pet.project.code.task.tracker.api.api.dto.ProjectDto;
import pet.project.code.task.tracker.api.api.exceptions.BadRequestException;
import pet.project.code.task.tracker.api.api.factories.ProjectDtoFactory;
import pet.project.code.task.tracker.api.api.factories.TaskStateDtoFactory;
import pet.project.code.task.tracker.api.store.entities.ProjectEntity;
import pet.project.code.task.tracker.api.store.repositories.ProjectRepository;

@RequiredArgsConstructor
@RestController
public class ProjectController {
    private final ProjectDtoFactory projectDtoFactory;
    private final ProjectRepository projectRepository;

    public static final String CREATE_PROJECT = "/api/projects";
    public static final String EDIT_PROJECT = "/apo/project/{project_id}";

    @PostMapping(CREATE_PROJECT)
    @Transactional
    public ProjectDto createProject(@RequestParam String name) {

        projectRepository
                .findByName(name)
                .ifPresent(project -> {
                    throw new BadRequestException(String.format("Project \"%s\" Already exist", name ));
                });

        ProjectEntity entity = ProjectEntity.builder()
                .name(name).build();

        projectRepository.saveAndFlush(entity);
        return projectDtoFactory.makeProjectDto(entity);

    }
}
