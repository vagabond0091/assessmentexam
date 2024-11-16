package com.pos.streamline.controller;

import com.pos.streamline.entity.Project;
import com.pos.streamline.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    /**
     * api end point for creating a project and calculating schedule.
     * @param project
     * @return
     */
    @PostMapping("/createProject")
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        Project projectData = null;
        try {
            projectData = projectService.createProject(project);
            projectService.calculateSchedule(projectData);
            return new ResponseEntity<>(projectData, HttpStatus.OK);
        }
        catch(Exception e) {
            //  Block of code to handle errors
            e.printStackTrace();

        }
        return new ResponseEntity<>(projectData, HttpStatus.OK);
    }

    /**
     * api end point for getting a single project using by project id.
     * @param projectId
     * @return
     */
    @GetMapping("/{projectId}")
    public Project getProject(@PathVariable Long projectId) {
        return projectService.getProjectById(projectId);
    }

    /**
     * api end point for fetching all the projects created in the database.
     * @return
     */
    @GetMapping("/getAllProjects")
    public List<Project> getAllProjects() {
        return projectService.findAllProjects();
    }



}
