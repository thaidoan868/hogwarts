package vn.conguyetduong.hogwarts.infra.specification;

import org.springframework.data.jpa.domain.Specification;
import vn.conguyetduong.hogwarts.infra.constant.WizardStatus;
import vn.conguyetduong.hogwarts.infra.model.Wizard;

import java.util.UUID;

public class WizardSearchSpecs {
    private static Specification<Wizard> hasId(UUID id) {
        return (r, q, cb) -> cb.equal(r.get("id"), id);
    };

    private static Specification<Wizard> containsName(String name) {
        return (r, q, cb) -> cb.like(cb.lower(r.get("name")), "%" + name.trim().toLowerCase() + "%");
    }

    private static Specification<Wizard> containsDescription(String description) {
        return (r, q, cb) -> cb.like(cb.lower(r.get("description")), "%" + description.trim().toLowerCase() + "%");
    }

    private static Specification<Wizard> hasStatus(WizardStatus status) {
        return (r, q, cb) -> cb.equal(r.get("status"), status);
    }

    public static Specification<Wizard> of(WizardSearchCriteria criteria) {
        Specification<Wizard> spec = hasStatus(WizardStatus.ACTIVE);

        if (criteria.id != null) {
            spec = spec.and(hasId(criteria.id));
        }
        if (criteria.name != null || criteria.name.isBlank()) {
            spec = spec.and(containsName(criteria.name));
        }
        if (criteria.description != null || criteria.description.isBlank()) {
            spec = spec.and(containsDescription(criteria.description));
        }

        return spec;
    }
}
