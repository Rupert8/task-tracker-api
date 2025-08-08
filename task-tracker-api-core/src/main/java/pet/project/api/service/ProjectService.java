package pet.project.api.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.project.filters.ProjectFilter;
import pet.project.dto.projects.ProjectReadDto;
import pet.project.entities.ProjectEntity;
import pet.project.filters.QPredicates;
import pet.project.api.exceptions.BadRequestException;
import pet.project.api.exceptions.NotFoundException;
import pet.project.mapper.ProjectMapper;
import pet.project.jpaRepositories.ProjectRepository;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static pet.project.entities.QProjectEntity.projectEntity;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public List<ProjectReadDto> findAllProjects(@NotNull ProjectFilter filter) {
        var predicate = QPredicates.builder()
                .add(filter.name(), projectEntity.name::containsIgnoreCase)
                .add(filter.createdAt(), projectEntity.createdAt::before)
                .build();

        Iterable<ProjectEntity> projectEntities = projectRepository.findAll(predicate);
        log.info("list: {}", projectEntities);

        List<ProjectEntity> projectEntityList = StreamSupport
                .stream(projectEntities.spliterator(), false)
                .collect(Collectors.toList());

        return projectMapper.toListProjectDto(projectEntityList);
    }

    public ProjectReadDto findProjectById(@NotNull Long projectId) {
        return projectMapper.toProjectDto(projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException(String.format("Project with id - \"%s\" doesn't exist", projectId))));
    }

    @Transactional
    public ProjectReadDto createProject(String name) {
        projectRepository
                .findByName(name)
                .ifPresent(project -> {
                    throw new BadRequestException(String.format("Project \"%s\" Already exist", name));
                });

        ProjectEntity entity = ProjectEntity.builder()
                .name(name).build();

        projectRepository.saveAndFlush(entity);
        return projectMapper.toProjectDto(entity);
    }

    @Transactional
    public ProjectReadDto updateProject(Long projectId, String name) {
        ProjectEntity project = projectRepository
                .findById(projectId)
                .orElseThrow(() -> new NotFoundException(String.format("Project with name - \"%s\" doesn't exist", name)));

        projectRepository
                .findByName(name)
                .filter(anotherProject -> !Objects.equals(anotherProject.getId(), projectId))
                .ifPresent(anotherProject -> {
                    throw new BadRequestException("Project already - \"%s\"exist");
                });

        project.setName(name);

        project = projectRepository.saveAndFlush(project);
        return projectMapper.toProjectDto(project);
    }

    @Transactional
    public void deleteAllProject() {
        projectRepository.deleteAll();
    }

    @Transactional
    public void deleteProjectById(Long projectId) {
        ProjectEntity project = projectRepository
                .findById(projectId)
                .orElseThrow(() -> new NotFoundException(String.format("Project with id - \"%s\" doesn't exist", projectId)));

        projectRepository.delete(project);
    }
}
