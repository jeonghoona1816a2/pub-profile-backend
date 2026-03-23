package com.pubprofile.backend.controller;

import com.pubprofile.backend.domain.FabCapacity;
import com.pubprofile.backend.domain.FabCapacityDaily;
import com.pubprofile.backend.service.FabCapacityDailyService;
import com.pubprofile.backend.service.FabCapacityService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/fab-capacity")
public class FabCapacityController {

    private static final Logger log = LoggerFactory.getLogger(FabCapacityController.class);

    private final FabCapacityService fabCapacityService;
    private final FabCapacityDailyService fabCapacityDailyService;

    public FabCapacityController(
            FabCapacityService fabCapacityService,
            FabCapacityDailyService fabCapacityDailyService
    ) {
        this.fabCapacityService = fabCapacityService;
        this.fabCapacityDailyService = fabCapacityDailyService;
    }

    @GetMapping
    public List<FabCapacity> getFabCapacities(@RequestParam(required = false) String family) {
        return fabCapacityService.getFabCapacities(family);
    }

    @GetMapping("/{id}")
    public FabCapacity getFabCapacity(@PathVariable Long id) {
        return fabCapacityService.getFabCapacity(id);
    }

    @GetMapping("/{id}/daily")
    public List<FabCapacityDaily> getFabCapacityDailyRecords(@PathVariable Long id) {
        return fabCapacityDailyService.getFabCapacityDailyRecords(id);
    }

    @PostMapping
    public ResponseEntity<FabCapacity> createFabCapacity(@RequestBody FabCapacity fabCapacity) {
        FabCapacity createdFabCapacity = fabCapacityService.createFabCapacity(fabCapacity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFabCapacity);
    }

    @PutMapping("/{id}")
    public FabCapacity replaceFabCapacity(@PathVariable Long id, @RequestBody FabCapacity fabCapacity) {
        return fabCapacityService.replaceFabCapacity(id, fabCapacity);
    }

    @PatchMapping("/{id}")
    public FabCapacity patchFabCapacity(@PathVariable Long id, @RequestBody FabCapacity fabCapacity) {
        return fabCapacityService.patchFabCapacity(id, fabCapacity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFabCapacity(@PathVariable Long id) {
        try {
            fabCapacityService.deleteFabCapacity(id);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            log.error("Failed to delete fab_capacity {}", id, e);
            return ResponseEntity.status(409)
                    .body("Delete failed. This fab_capacity may be referenced by fab_capacity_daily.");
        }
    }
}
