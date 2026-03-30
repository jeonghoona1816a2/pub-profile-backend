package com.pubprofile.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "search_filter")
public class SearchFilter {

    @Id
    private Long id;

    @Column(name = "page_id")
    private Long pageId;

    @Column(name = "filter_key")
    private String filterKey;

    @Column(name = "filter_label")
    private String filterLabel;

    @Column(name = "filter_type")
    private String filterType;

    private String placeholder;

    @Column(name = "option_source")
    private String optionSource;

    @Column(name = "query_code")
    private String queryCode;

    @Column(name = "parent_filter_key")
    private String parentFilterKey;

    @Column(name = "visible_yn")
    private Boolean visibleYn;

    @Column(name = "required_yn")
    private Boolean requiredYn;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "data_column")
    private String dataColumn;

    @Column(name = "default_value")
    private String defaultValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public String getFilterKey() {
        return filterKey;
    }

    public void setFilterKey(String filterKey) {
        this.filterKey = filterKey;
    }

    public String getFilterLabel() {
        return filterLabel;
    }

    public void setFilterLabel(String filterLabel) {
        this.filterLabel = filterLabel;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getOptionSource() {
        return optionSource;
    }

    public void setOptionSource(String optionSource) {
        this.optionSource = optionSource;
    }

    public String getQueryCode() {
        return queryCode;
    }

    public void setQueryCode(String queryCode) {
        this.queryCode = queryCode;
    }

    public String getParentFilterKey() {
        return parentFilterKey;
    }

    public void setParentFilterKey(String parentFilterKey) {
        this.parentFilterKey = parentFilterKey;
    }

    public Boolean getVisibleYn() {
        return visibleYn;
    }

    public void setVisibleYn(Boolean visibleYn) {
        this.visibleYn = visibleYn;
    }

    public Boolean getRequiredYn() {
        return requiredYn;
    }

    public void setRequiredYn(Boolean requiredYn) {
        this.requiredYn = requiredYn;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDataColumn() {
        return dataColumn;
    }

    public void setDataColumn(String dataColumn) {
        this.dataColumn = dataColumn;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
