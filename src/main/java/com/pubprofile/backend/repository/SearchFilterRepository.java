package com.pubprofile.backend.repository;

import com.pubprofile.backend.domain.SearchFilter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchFilterRepository extends JpaRepository<SearchFilter, Long> {

    List<SearchFilter> findByPageIdOrderBySortOrderAscIdAsc(Long pageId);
}
