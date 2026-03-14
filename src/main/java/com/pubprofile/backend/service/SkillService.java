package com.pubprofile.backend.service;

import com.pubprofile.backend.domain.Skill;
import com.pubprofile.backend.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    private final SkillRepository skillRepository;
    private final DatabaseReadExecutor databaseReadExecutor;

    public SkillService(SkillRepository skillRepository, DatabaseReadExecutor databaseReadExecutor) {
        this.skillRepository = skillRepository;
        this.databaseReadExecutor = databaseReadExecutor;
    }

    public List<Skill> getSkills() {
        return databaseReadExecutor.execute("getSkills", skillRepository::findAll);
    }
}
