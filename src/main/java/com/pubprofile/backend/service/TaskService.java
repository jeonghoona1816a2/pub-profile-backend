package com.pubprofile.backend.service;

import com.pubprofile.backend.domain.Task;
import com.pubprofile.backend.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final DatabaseReadExecutor databaseReadExecutor;

    public TaskService(TaskRepository taskRepository, DatabaseReadExecutor databaseReadExecutor) {
        this.taskRepository = taskRepository;
        this.databaseReadExecutor = databaseReadExecutor;
    }

    public List<Task> getTasks(String keyword) {
        String normalizedKeyword = keyword == null ? null : keyword.trim();

        if (!StringUtils.hasText(normalizedKeyword)) {
            return databaseReadExecutor.execute("getTasks", taskRepository::findAll);
        }

        return databaseReadExecutor.execute(
                "getTasksByKeyword",
                () -> taskRepository.searchByKeyword(normalizedKeyword)
        );
    }
}
