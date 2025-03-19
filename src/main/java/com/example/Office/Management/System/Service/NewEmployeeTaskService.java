package com.example.Office.Management.System.Service;

import com.example.Office.Management.System.Entity.NewEmployeeTask;
import com.example.Office.Management.System.Repository.NewEmployeeTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NewEmployeeTaskService {

    @Autowired
    private NewEmployeeTaskRepository taskRepository;

    public NewEmployeeTask addTask(NewEmployeeTask task) {
        return taskRepository.save(task);
    }

    public List<NewEmployeeTask> getTasksForUser(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    public Optional<NewEmployeeTask> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public void updateTaskStatus(Long id, NewEmployeeTask task) {
        Optional<NewEmployeeTask> existingTask = taskRepository.findById(id);
        existingTask.ifPresent(t -> {
            t.setStatus(task.getStatus());
            taskRepository.save(t);
        });
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}

