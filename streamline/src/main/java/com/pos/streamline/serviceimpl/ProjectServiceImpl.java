package com.pos.streamline.serviceimpl;

import com.pos.streamline.entity.Project;
import com.pos.streamline.entity.Task;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ProjectServiceImpl {
    LocalDate calculateStartDate(Task task, Map<Task, LocalDate> startDates);
    Project createProject(Project project);
    Project getProjectById(Long id);
    List<Project> findAllProjects();
}
