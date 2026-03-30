package com.pubprofile.backend.repository;

import com.pubprofile.backend.domain.StepMove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface StepMoveRepository extends JpaRepository<StepMove, Long>, JpaSpecificationExecutor<StepMove> {

    List<StepMove> findAllByOrderByCreatedAtDescIdDesc();
}
