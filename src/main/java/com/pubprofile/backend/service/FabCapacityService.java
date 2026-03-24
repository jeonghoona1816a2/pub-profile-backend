package com.pubprofile.backend.service;

import com.pubprofile.backend.domain.FabCapacity;
import com.pubprofile.backend.repository.FabCapacityRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class FabCapacityService {

    private final FabCapacityRepository fabCapacityRepository;
    private final DatabaseReadExecutor databaseReadExecutor;

    public FabCapacityService(
            FabCapacityRepository fabCapacityRepository,
            DatabaseReadExecutor databaseReadExecutor
    ) {
        this.fabCapacityRepository = fabCapacityRepository;
        this.databaseReadExecutor = databaseReadExecutor;
    }

    public List<FabCapacity> getFabCapacities(String family, String site, String device, String category) {
        String normalizedFamily = normalizeFilter(family);
        String normalizedSite = normalizeFilter(site);
        String normalizedDevice = normalizeFilter(device);
        String normalizedCategory = normalizeFilter(category);

        if (normalizedFamily == null
                && normalizedSite == null
                && normalizedDevice == null
                && normalizedCategory == null) {
            return databaseReadExecutor.execute(
                    "getFabCapacities",
                    fabCapacityRepository::findAllByOrderByYearDescIdDesc
            );
        }

        return databaseReadExecutor.execute(
                "getFabCapacitiesByFilters",
                () -> fabCapacityRepository.findAll((root, query, criteriaBuilder) -> {
                    List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();

                    if (normalizedFamily != null) {
                        predicates.add(criteriaBuilder.equal(
                                criteriaBuilder.lower(root.get("family")),
                                normalizedFamily.toLowerCase(Locale.ROOT)
                        ));
                    }
                    if (normalizedSite != null) {
                        predicates.add(criteriaBuilder.equal(
                                criteriaBuilder.lower(root.get("site")),
                                normalizedSite.toLowerCase(Locale.ROOT)
                        ));
                    }
                    if (normalizedDevice != null) {
                        predicates.add(criteriaBuilder.equal(
                                criteriaBuilder.lower(root.get("device")),
                                normalizedDevice.toLowerCase(Locale.ROOT)
                        ));
                    }
                    if (normalizedCategory != null) {
                        predicates.add(criteriaBuilder.equal(
                                criteriaBuilder.lower(root.get("planGbnCd")),
                                normalizedCategory.toLowerCase(Locale.ROOT)
                        ));
                    }

                    return criteriaBuilder.and(predicates.toArray(jakarta.persistence.criteria.Predicate[]::new));
                }, Sort.by(Sort.Direction.DESC, "year", "id"))
        );
    }

    public FabCapacity getFabCapacity(Long id) {
        return databaseReadExecutor.execute(
                "getFabCapacity",
                () -> findFabCapacity(id)
        );
    }

    public FabCapacity createFabCapacity(FabCapacity fabCapacity) {
        return fabCapacityRepository.save(fabCapacity);
    }

    public FabCapacity replaceFabCapacity(Long id, FabCapacity replacement) {
        FabCapacity existing = findFabCapacity(id);
        existing.setYear(replacement.getYear());
        existing.setPlanGbnCd(replacement.getPlanGbnCd());
        existing.setRev(replacement.getRev());
        existing.setSite(replacement.getSite());
        existing.setFab(replacement.getFab());
        existing.setFamily(replacement.getFamily());
        existing.setTech(replacement.getTech());
        existing.setDevice(replacement.getDevice());
        existing.setLotCd(replacement.getLotCd());
        existing.setPg(replacement.getPg());
        existing.setRemark(replacement.getRemark());
        return fabCapacityRepository.save(existing);
    }

    public FabCapacity patchFabCapacity(Long id, FabCapacity patch) {
        FabCapacity existing = findFabCapacity(id);

        if (patch.getYear() != null) {
            existing.setYear(patch.getYear());
        }
        if (patch.getPlanGbnCd() != null) {
            existing.setPlanGbnCd(patch.getPlanGbnCd());
        }
        if (patch.getRev() != null) {
            existing.setRev(patch.getRev());
        }
        if (patch.getSite() != null) {
            existing.setSite(patch.getSite());
        }
        if (patch.getFab() != null) {
            existing.setFab(patch.getFab());
        }
        if (patch.getFamily() != null) {
            existing.setFamily(patch.getFamily());
        }
        if (patch.getTech() != null) {
            existing.setTech(patch.getTech());
        }
        if (patch.getDevice() != null) {
            existing.setDevice(patch.getDevice());
        }
        if (patch.getLotCd() != null) {
            existing.setLotCd(patch.getLotCd());
        }
        if (patch.getPg() != null) {
            existing.setPg(patch.getPg());
        }
        if (patch.getRemark() != null) {
            existing.setRemark(patch.getRemark());
        }

        return fabCapacityRepository.save(existing);
    }

    public void deleteFabCapacity(Long id) {
        if (!fabCapacityRepository.existsById(id)) {
            throw notFound(id);
        }

        fabCapacityRepository.deleteById(id);
        fabCapacityRepository.flush();
    }

    private FabCapacity findFabCapacity(Long id) {
        return fabCapacityRepository.findById(id)
                .orElseThrow(() -> notFound(id));
    }

    private ResponseStatusException notFound(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "fab_capacity not found: " + id);
    }

    private String normalizeFilter(String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }

        String normalized = value.trim();
        return "all".equalsIgnoreCase(normalized) ? null : normalized;
    }
}
