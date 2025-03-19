package com.example.Office.Management.System.Repository;

import com.example.Office.Management.System.Entity.NewEmployeeTask;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NewEmployeeTaskRepository extends JpaRepository<NewEmployeeTask, Long> {
    List<NewEmployeeTask> findByUserId(Long userId);
}
