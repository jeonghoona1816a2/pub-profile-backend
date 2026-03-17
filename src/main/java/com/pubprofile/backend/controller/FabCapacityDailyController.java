package com.pubprofile.backend.controller;

import com.pubprofile.backend.domain.FabCapacityDaily;
import com.pubprofile.backend.service.FabCapacityDailyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/fab-capacity-daily")
public class FabCapacityDailyController {

    private static final Logger log = LoggerFactory.getLogger(FabCapacityDailyController.class);

    private final FabCapacityDailyService fabCapacityDailyService;

    public FabCapacityDailyController(FabCapacityDailyService fabCapacityDailyService) {
        this.fabCapacityDailyService = fabCapacityDailyService;
    }

    @GetMapping
    public List<FabCapacityDaily> getFabCapacityDailyRecords(
            @RequestParam(required = false) Long capacityId
    ) {
        return fabCapacityDailyService.getFabCapacityDailyRecords(capacityId);
    }

    @GetMapping("/{id}")
    public FabCapacityDaily getFabCapacityDaily(@PathVariable Long id) {
        return fabCapacityDailyService.getFabCapacityDaily(id);
    }

    @PostMapping
    public ResponseEntity<FabCapacityDaily> createFabCapacityDaily(@RequestBody FabCapacityDaily fabCapacityDaily) {
        FabCapacityDaily createdFabCapacityDaily = fabCapacityDailyService.createFabCapacityDaily(fabCapacityDaily);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFabCapacityDaily);
    }

    @PutMapping("/{id}")
    public FabCapacityDaily replaceFabCapacityDaily(
            @PathVariable Long id,
            @RequestBody FabCapacityDaily fabCapacityDaily
    ) {
        return fabCapacityDailyService.replaceFabCapacityDaily(id, fabCapacityDaily);
    }

    @PatchMapping("/{id}")
    public FabCapacityDaily patchFabCapacityDaily(
            @PathVariable Long id,
            @RequestBody FabCapacityDaily fabCapacityDaily
    ) {
        return fabCapacityDailyService.patchFabCapacityDaily(id, fabCapacityDaily);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFabCapacityDaily(@PathVariable Long id) {
        try {
            fabCapacityDailyService.deleteFabCapacityDaily(id);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            log.error("Failed to delete fab_capacity_daily {}", id, e);
            return ResponseEntity.status(409)
                    .body("Delete failed. This fab_capacity_daily may be referenced by other data.");
        }
    }
}
