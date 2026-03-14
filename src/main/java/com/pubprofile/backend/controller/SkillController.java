package com.pubprofile.backend.controller;

import com.pubprofile.backend.domain.Skill;
import com.pubprofile.backend.repository.SkillRepository;
import com.pubprofile.backend.service.SkillService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {
    private static final Logger log = LoggerFactory.getLogger(SkillController.class);

    private final SkillRepository skillRepository;
    private final SkillService skillService;

    public SkillController(SkillRepository skillRepository, SkillService skillService) {
        this.skillRepository = skillRepository;
        this.skillService = skillService;
    }

    @GetMapping
    public List<Skill> getSkills() {
        return skillService.getSkills();
    }

    @PostMapping
    public Skill createSkill(@RequestBody Skill skill) {
        return skillRepository.save(skill);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateSkill(@PathVariable Long id, @RequestBody Skill updatedSkill) {
        return skillRepository.findById(id)
                .map(skill -> {
                    skill.setName(updatedSkill.getName());
                    skill.setCategory(updatedSkill.getCategory());
                    skill.setLevel(updatedSkill.getLevel());
                    skill.setYears(updatedSkill.getYears());
                    skill.setLastUsed(updatedSkill.getLastUsed());

                    Skill savedSkill = skillRepository.save(skill);
                    return ResponseEntity.ok(savedSkill);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSkill(@PathVariable Long id) {
        if (!skillRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        try {
            skillRepository.deleteById(id);
            skillRepository.flush();
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            log.error("Failed to delete skill {}", id, e);
            return ResponseEntity.status(409).body("Delete failed. This skill may be referenced by other data.");
        } catch (Exception e) {
            log.error("Failed to delete skill {}", id, e);
            return ResponseEntity.status(500).body("Delete failed: " + e.getMessage());
        }
    }

}
