package com.pos.streamline.service;

import com.pos.streamline.entity.Project;
import com.pos.streamline.entity.Task;
import com.pos.streamline.repository.ProjectRepository;
import com.pos.streamline.repository.TaskRepository;
import com.pos.streamline.serviceimpl.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ProjectService implements ProjectServiceImpl {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TaskRepository taskRepository;

    /**
     * method for calculating the schedule
     * @param project
     */
    public void calculateSchedule(Project project) {
        // add a CopyOnWriteArrayList to able to prevent check For Comodification error
        List<Task> tasks = new CopyOnWriteArrayList<>(project.getTask());
        List<Task> dependencies = new CopyOnWriteArrayList<>();
        Task taskLastRecord = null;
        Map<Task, LocalDate> startDates = new HashMap<>();
        LocalDate dependencyStartDate = null;
        Long savedID = 0L;
        if (!tasks.isEmpty()) {
            // First pass: Calculate the initial start dates for each task
            for (Task task : tasks) {
                if(CollectionUtils.isEmpty(task.getDependencies())){
                    task.setStartDate(calculateStartDate(task, startDates)); // Set the start date from pre-calculated map
                    task.calculatedEndDate();                // Calculate end date based on the start date

                }

                if (!CollectionUtils.isEmpty(task.getDependencies())) {
                    LocalDate firstDependencyStartDate = null;
                    for (Task dependency : task.getDependencies()) {
                        Task taskDependency = taskRepository.findById(dependency.getId()).orElse(null);
                        if (taskDependency != null) {
                            // check if there's a first dependency start date value
                            if(firstDependencyStartDate == null){
                                dependencyStartDate  = taskDependency.getEndDate().plusDays(taskDependency.getDuration());
                                firstDependencyStartDate = dependencyStartDate;
                                startDates.put(task, firstDependencyStartDate);
                                dependencies.add(taskDependency);
                            }
                            else{
                                // pass the first dependency start date value
                                dependencyStartDate  = firstDependencyStartDate.plusDays(taskDependency.getDuration());
                                startDates.put(task, dependencyStartDate);
                                dependencies.add(taskDependency);
                            }

                        }
                    }
                    task.setStartDate(startDates.get(task)); // Set the start date from pre-calculated map
                    task.calculatedEndDate();                // Calculate end date based on the start date
                    task.setProject(project);
                    task.setDependencies(dependencies);
                    taskRepository.save(task);

                }
                else{
                    task.setStartDate(startDates.get(task)); // Set the start date from pre-calculated map
                    task.calculatedEndDate();                // Calculate end date based on the start date
                    task.setProject(project);
                    taskRepository.save(task);
                }



            }
        }

    }

    /**
     * method for calculating the start date.
     * @param task
     * @param startDates
     * @return
     */
    @Override
    public LocalDate calculateStartDate(Task task, Map<Task, LocalDate> startDates) {
        if(startDates.containsKey(task)){
            return startDates.get(task);
        }
        LocalDate startDate = LocalDate.now();

        startDates.put(task,startDate);
        return startDate;
    }

    /**
     * method for creating a project
     * @param project
     * @return
     */
    @Override
    public Project createProject(Project project) {
        return  projectRepository.save(project);
    }

    /**
     * method to get project by id
     * @param id
     * @return
     */
    @Override
    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
    }

    /**
     * method for fetching all the project stored in the database.
     * @return
     */
    @Override
    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }

}
