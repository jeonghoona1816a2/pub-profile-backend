package com.pubprofile.backend.controller;

import com.pubprofile.backend.domain.Skill;
import com.pubprofile.backend.repository.SkillRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@CrossOrigin(origins = "http://localhost:5173")
public class SkillController {

    private final SkillRepository skillRepository;

    public SkillController(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @GetMapping
    public List<Skill> getSkills() {
        return skillRepository.findAll();
    }
}
