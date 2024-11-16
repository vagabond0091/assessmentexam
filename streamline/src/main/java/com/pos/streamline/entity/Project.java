package com.pos.streamline.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "project_tbl")
public class Project {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long projectId;
    private String projectName;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Task> task;

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<Task> getTask() {
        return task;
    }

    public void setTask(List<Task> task) {
        this.task = task;
    }
}
