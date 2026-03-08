package com.pubprofile.backend.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "skill_inventory")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;
    private String level;
    private Integer years;

    @Column(name = "last_used")
    private String lastUsed;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public Integer getYears() { return years; }
    public void setYears(Integer years) { this.years = years; }

    public String getLastUsed() { return lastUsed; }
    public void setLastUsed(String lastUsed) { this.lastUsed = lastUsed; }
}
