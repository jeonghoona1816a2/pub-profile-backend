package com.pubprofile.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "step_move")
public class StepMove {

    @Id
    private Long id;

    private String site;
    private String fab;

    @Column(name = "lot_id")
    private String lotId;

    private String product;

    @Column(name = "from_step")
    private String fromStep;

    @Column(name = "to_step")
    private String toStep;

    @Column(name = "move_qty")
    private Integer moveQty;

    private String status;
    private String route;

    @Column(name = "version_no")
    private String versionNo;

    private String remark;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getFromStep() {
        return fromStep;
    }

    public void setFromStep(String fromStep) {
        this.fromStep = fromStep;
    }

    public String getToStep() {
        return toStep;
    }

    public void setToStep(String toStep) {
        this.toStep = toStep;
    }

    public Integer getMoveQty() {
        return moveQty;
    }

    public void setMoveQty(Integer moveQty) {
        this.moveQty = moveQty;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
