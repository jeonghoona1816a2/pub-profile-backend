package com.pubprofile.backend.controller;

import com.pubprofile.backend.domain.StepMove;
import com.pubprofile.backend.service.StepMoveService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/step-move")
public class StepMoveController {

    private final StepMoveService stepMoveService;
    private static final long STEP_MOVE_PAGE_ID = 1L;

    public StepMoveController(StepMoveService stepMoveService) {
        this.stepMoveService = stepMoveService;
    }

    @GetMapping("/filters")
    public List<StepMoveService.StepMoveSearchFilterItem> getStepMoveSearchFilters() {
        return stepMoveService.getStepMoveSearchFilters(STEP_MOVE_PAGE_ID);
    }

    @GetMapping
    public List<StepMove> getStepMoves(
            @RequestParam(required = false) String fab,
            @RequestParam(required = false) String lotId,
            @RequestParam(required = false) String product,
            @RequestParam(required = false) String status
    ) {
        return stepMoveService.getStepMoves(fab, lotId, product, status);
    }
}
