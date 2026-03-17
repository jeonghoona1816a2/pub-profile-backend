package com.pubprofile.backend.repository;

import com.pubprofile.backend.domain.FabCapacity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FabCapacityRepository extends JpaRepository<FabCapacity, Long> {

    List<FabCapacity> findAllByOrderByYearDescIdDesc();
}
