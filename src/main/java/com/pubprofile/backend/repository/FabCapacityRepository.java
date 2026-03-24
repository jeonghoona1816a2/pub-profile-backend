package com.pubprofile.backend.repository;

import com.pubprofile.backend.domain.FabCapacity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface FabCapacityRepository extends JpaRepository<FabCapacity, Long>, JpaSpecificationExecutor<FabCapacity> {

    List<FabCapacity> findAllByOrderByYearDescIdDesc();
}
