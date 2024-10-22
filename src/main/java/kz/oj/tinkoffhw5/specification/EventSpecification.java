package kz.oj.tinkoffhw5.specification;

import jakarta.persistence.criteria.JoinType;
import kz.oj.tinkoffhw5.entity.Event;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.UUID;

public class EventSpecification {

    public static Specification<Event> hasNameLike(String name) {
        return (root, query, criteriaBuilder) ->
                (name == null || name.isEmpty()) ? criteriaBuilder.conjunction() : criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Event> isInPlaceWithId(UUID placeId) {
        return (root, query, criteriaBuilder) -> {
            if (placeId == null) {
                return criteriaBuilder.conjunction();
            }
            root.fetch("place", JoinType.INNER);
            return criteriaBuilder.equal(root.get("place").get("id"), placeId);
        };
    }

    public static Specification<Event> isAfter(LocalDate fromDate) {
        return (root, query, criteriaBuilder) ->
                fromDate == null ? criteriaBuilder.conjunction() : criteriaBuilder.greaterThanOrEqualTo(root.get("date"), fromDate);
    }

    public static Specification<Event> isBefore(LocalDate toDate) {
        return (root, query, criteriaBuilder) ->
                toDate == null ? criteriaBuilder.conjunction() : criteriaBuilder.lessThanOrEqualTo(root.get("date"), toDate);
    }
}
