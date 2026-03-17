package com.pubprofile.backend.service;

import com.pubprofile.backend.domain.FabCapacityDaily;
import com.pubprofile.backend.repository.FabCapacityDailyRepository;
import com.pubprofile.backend.repository.FabCapacityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FabCapacityDailyService {

    private final FabCapacityDailyRepository fabCapacityDailyRepository;
    private final FabCapacityRepository fabCapacityRepository;
    private final DatabaseReadExecutor databaseReadExecutor;

    public FabCapacityDailyService(
            FabCapacityDailyRepository fabCapacityDailyRepository,
            FabCapacityRepository fabCapacityRepository,
            DatabaseReadExecutor databaseReadExecutor
    ) {
        this.fabCapacityDailyRepository = fabCapacityDailyRepository;
        this.fabCapacityRepository = fabCapacityRepository;
        this.databaseReadExecutor = databaseReadExecutor;
    }

    public List<FabCapacityDaily> getFabCapacityDailyRecords(Long capacityId) {
        if (capacityId == null) {
            return databaseReadExecutor.execute(
                    "getFabCapacityDailyRecords",
                    fabCapacityDailyRepository::findAllByOrderByTargetDateAscIdAsc
            );
        }

        ensureCapacityExists(capacityId);
        return databaseReadExecutor.execute(
                "getFabCapacityDailyRecordsByCapacityId",
                () -> fabCapacityDailyRepository.findByCapacityIdOrderByTargetDateAscIdAsc(capacityId)
        );
    }

    public FabCapacityDaily getFabCapacityDaily(Long id) {
        return databaseReadExecutor.execute(
                "getFabCapacityDaily",
                () -> findFabCapacityDaily(id)
        );
    }

    public FabCapacityDaily createFabCapacityDaily(FabCapacityDaily fabCapacityDaily) {
        Long capacityId = fabCapacityDaily.getCapacityId();
        if (capacityId == null) {
            throw badRequest("capacityId is required.");
        }

        ensureCapacityExists(capacityId);
        return fabCapacityDailyRepository.save(fabCapacityDaily);
    }

    public FabCapacityDaily replaceFabCapacityDaily(Long id, FabCapacityDaily replacement) {
        Long capacityId = replacement.getCapacityId();
        if (capacityId == null) {
            throw badRequest("capacityId is required.");
        }

        ensureCapacityExists(capacityId);

        FabCapacityDaily existing = findFabCapacityDaily(id);
        existing.setCapacityId(capacityId);
        existing.setBaseDate(replacement.getBaseDate());
        existing.setTargetDate(replacement.getTargetDate());
        existing.setQty(replacement.getQty());
        return fabCapacityDailyRepository.save(existing);
    }

    public FabCapacityDaily patchFabCapacityDaily(Long id, FabCapacityDaily patch) {
        FabCapacityDaily existing = findFabCapacityDaily(id);

        if (patch.getCapacityId() != null) {
            ensureCapacityExists(patch.getCapacityId());
            existing.setCapacityId(patch.getCapacityId());
        }
        if (patch.getBaseDate() != null) {
            existing.setBaseDate(patch.getBaseDate());
        }
        if (patch.getTargetDate() != null) {
            existing.setTargetDate(patch.getTargetDate());
        }
        if (patch.getQty() != null) {
            existing.setQty(patch.getQty());
        }

        return fabCapacityDailyRepository.save(existing);
    }

    public void deleteFabCapacityDaily(Long id) {
        if (!fabCapacityDailyRepository.existsById(id)) {
            throw notFound(id);
        }

        fabCapacityDailyRepository.deleteById(id);
        fabCapacityDailyRepository.flush();
    }

    private FabCapacityDaily findFabCapacityDaily(Long id) {
        return fabCapacityDailyRepository.findById(id)
                .orElseThrow(() -> notFound(id));
    }

    private void ensureCapacityExists(Long capacityId) {
        if (!fabCapacityRepository.existsById(capacityId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "fab_capacity not found: " + capacityId);
        }
    }

    private ResponseStatusException notFound(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "fab_capacity_daily not found: " + id);
    }

    private ResponseStatusException badRequest(String message) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
    }
}
