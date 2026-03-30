package com.pubprofile.backend.service;

import com.pubprofile.backend.domain.SearchFilter;
import com.pubprofile.backend.domain.StepMove;
import com.pubprofile.backend.repository.SearchFilterRepository;
import com.pubprofile.backend.repository.StepMoveRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StepMoveService {

    private static final Set<String> FORCED_SELECT_FILTER_KEYS = Set.of("lot_id", "product");

    private final StepMoveRepository stepMoveRepository;
    private final SearchFilterRepository searchFilterRepository;
    private final DatabaseReadExecutor databaseReadExecutor;
    private final EntityManager entityManager;

    public StepMoveService(
            StepMoveRepository stepMoveRepository,
            SearchFilterRepository searchFilterRepository,
            DatabaseReadExecutor databaseReadExecutor,
            EntityManager entityManager
    ) {
        this.stepMoveRepository = stepMoveRepository;
        this.searchFilterRepository = searchFilterRepository;
        this.databaseReadExecutor = databaseReadExecutor;
        this.entityManager = entityManager;
    }

    public List<StepMove> getStepMoves(String fab, String lotId, String product, String status) {
        String normalizedFab = normalizeFilter(fab);
        String normalizedLotId = normalizeFilter(lotId);
        String normalizedProduct = normalizeFilter(product);
        String normalizedStatus = normalizeFilter(status);

        if (normalizedFab == null
                && normalizedLotId == null
                && normalizedProduct == null
                && normalizedStatus == null) {
            return databaseReadExecutor.execute(
                    "getStepMoves",
                    stepMoveRepository::findAllByOrderByCreatedAtDescIdDesc
            );
        }

        return databaseReadExecutor.execute(
                "getStepMovesByFilters",
                () -> stepMoveRepository.findAll((root, query, criteriaBuilder) -> {
                    List<Predicate> predicates = new ArrayList<>();

                    if (normalizedFab != null) {
                        predicates.add(criteriaBuilder.equal(
                                criteriaBuilder.lower(root.get("fab")),
                                normalizedFab.toLowerCase(Locale.ROOT)
                        ));
                    }
                    if (normalizedLotId != null) {
                        predicates.add(criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("lotId")),
                                "%" + normalizedLotId.toLowerCase(Locale.ROOT) + "%"
                        ));
                    }
                    if (normalizedProduct != null) {
                        predicates.add(criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("product")),
                                "%" + normalizedProduct.toLowerCase(Locale.ROOT) + "%"
                        ));
                    }
                    if (normalizedStatus != null) {
                        predicates.add(criteriaBuilder.equal(
                                criteriaBuilder.lower(root.get("status")),
                                normalizedStatus.toLowerCase(Locale.ROOT)
                        ));
                    }

                    return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
                }, Sort.by(Sort.Direction.DESC, "createdAt", "id"))
        );
    }

    public List<StepMoveSearchFilterItem> getStepMoveSearchFilters(Long pageId) {
        return databaseReadExecutor.execute(
                "getStepMoveSearchFilters",
                () -> searchFilterRepository.findByPageIdOrderBySortOrderAscIdAsc(pageId).stream()
                        .filter(this::isVisible)
                        .map(this::toSearchFilterItem)
                        .collect(Collectors.toList())
        );
    }

    private String normalizeFilter(String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }

        String normalized = value.trim();
        return "all".equalsIgnoreCase(normalized) ? null : normalized;
    }

    private boolean isVisible(SearchFilter searchFilter) {
        return searchFilter.getVisibleYn() == null || Boolean.TRUE.equals(searchFilter.getVisibleYn());
    }

    private StepMoveSearchFilterItem toSearchFilterItem(SearchFilter searchFilter) {
        String resolvedFilterType = resolveFilterType(searchFilter);

        return new StepMoveSearchFilterItem(
                searchFilter.getId(),
                searchFilter.getFilterKey(),
                searchFilter.getFilterLabel(),
                resolvedFilterType,
                searchFilter.getPlaceholder(),
                searchFilter.getOptionSource(),
                searchFilter.getQueryCode(),
                searchFilter.getParentFilterKey(),
                searchFilter.getDataColumn(),
                searchFilter.getDefaultValue(),
                resolveOptions(searchFilter, resolvedFilterType)
        );
    }

    private String resolveFilterType(SearchFilter searchFilter) {
        if (FORCED_SELECT_FILTER_KEYS.contains(searchFilter.getFilterKey())) {
            return "select";
        }

        return searchFilter.getFilterType();
    }

    private List<SearchFilterOption> resolveOptions(SearchFilter searchFilter, String resolvedFilterType) {
        if (!"select".equalsIgnoreCase(resolvedFilterType)) {
            return List.of();
        }

        if (!StringUtils.hasText(searchFilter.getDataColumn())) {
            return List.of(new SearchFilterOption("All", ""));
        }

        String propertyName = toPropertyName(searchFilter.getDataColumn());

        List<String> rawOptions = databaseReadExecutor.execute(
                "getStepMoveSearchFilterOptions:" + searchFilter.getFilterKey(),
                () -> {
                    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
                    CriteriaQuery<String> query = criteriaBuilder.createQuery(String.class);
                    Root<StepMove> root = query.from(StepMove.class);

                    query.select(root.get(propertyName).as(String.class))
                            .distinct(true)
                            .where(criteriaBuilder.isNotNull(root.get(propertyName)))
                            .orderBy(criteriaBuilder.asc(root.get(propertyName).as(String.class)));

                    return entityManager.createQuery(query).getResultList();
                }
        );

        List<SearchFilterOption> options = new ArrayList<>();
        options.add(new SearchFilterOption("All", ""));

        rawOptions.stream()
                .filter(StringUtils::hasText)
                .forEach(value -> options.add(new SearchFilterOption(value, value)));

        return options;
    }

    private String toPropertyName(String dataColumn) {
        String[] parts = dataColumn.split("_");
        StringBuilder builder = new StringBuilder(parts[0]);

        for (int index = 1; index < parts.length; index++) {
            if (parts[index].isEmpty()) {
                continue;
            }

            builder.append(Character.toUpperCase(parts[index].charAt(0)))
                    .append(parts[index].substring(1));
        }

        return builder.toString();
    }

    public record SearchFilterOption(String label, String value) {
    }

    public record StepMoveSearchFilterItem(
            Long id,
            String filterKey,
            String filterLabel,
            String filterType,
            String placeholder,
            String optionSource,
            String queryCode,
            String parentFilterKey,
            String dataColumn,
            String defaultValue,
            List<SearchFilterOption> options
    ) {
    }
}
