package com.pubprofile.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "fab_capacity")
public class FabCapacity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year")
    private Integer year;

    @Column(name = "plan_gbn_cd")
    private String planGbnCd;

    private Integer rev;
    private String site;
    private String fab;
    @Column(name = "family")
    private String family;
    private String tech;
    private String device;

    @Column(name = "lot_cd")
    private String lotCd;

    private String pg;
    private String remark;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getPlanGbnCd() {
        return planGbnCd;
    }

    public void setPlanGbnCd(String planGbnCd) {
        this.planGbnCd = planGbnCd;
    }

    public Integer getRev() {
        return rev;
    }

    public void setRev(Integer rev) {
        this.rev = rev;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getFab() {
        return fab;
    }

    public void setFab(String fab) {
        this.fab = fab;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getTech() {
        return tech;
    }

    public void setTech(String tech) {
        this.tech = tech;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getLotCd() {
        return lotCd;
    }

    public void setLotCd(String lotCd) {
        this.lotCd = lotCd;
    }

    public String getPg() {
        return pg;
    }

    public void setPg(String pg) {
        this.pg = pg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
