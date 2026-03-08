package com.pubprofile.backend.repository;

import com.pubprofile.backend.domain.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
