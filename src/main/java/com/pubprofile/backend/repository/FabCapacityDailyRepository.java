package com.pubprofile.backend.repository;

import com.pubprofile.backend.domain.FabCapacityDaily;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FabCapacityDailyRepository extends JpaRepository<FabCapacityDaily, Long> {

    List<FabCapacityDaily> findAllByOrderByTargetDateAscIdAsc();

    List<FabCapacityDaily> findByCapacityIdOrderByTargetDateAscIdAsc(Long capacityId);
}
