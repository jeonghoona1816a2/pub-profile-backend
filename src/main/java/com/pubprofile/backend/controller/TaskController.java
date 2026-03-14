package com.pubprofile.backend.controller;

import com.pubprofile.backend.domain.Task;
import com.pubprofile.backend.service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.util.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getTasks(
            @RequestParam(required = false) String keyword,
            @RequestParam(name = "Keyword", required = false) String legacyKeyword,
            @RequestParam(required = false) String title
    ) {
        if (StringUtils.hasText(keyword)) {
            return taskService.getTasks(keyword);
        }
        if (StringUtils.hasText(legacyKeyword)) {
            return taskService.getTasks(legacyKeyword);
        }
        return taskService.getTasks(title);
    }
}
