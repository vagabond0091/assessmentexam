package com.pos.streamline.repository;

import com.pos.streamline.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface TaskRepository extends JpaRepository<Task, Long> {
}
