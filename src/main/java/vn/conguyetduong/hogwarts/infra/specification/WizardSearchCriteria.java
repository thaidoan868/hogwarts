package vn.conguyetduong.hogwarts.infra.specification;

import lombok.Data;

import java.util.UUID;

@Data
public class WizardSearchCriteria {
    UUID id;
    String name;
    String description;
}
