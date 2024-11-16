package com.pos.streamline.repository;

import com.pos.streamline.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ProjectRepository extends JpaRepository<Project, Long> {
}
