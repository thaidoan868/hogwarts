package vn.conguyetduong.hogwarts.app.api.v1;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import vn.conguyetduong.hogwarts.app.transfer.dto.page.PageDto;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizart.ShortWizardResponse;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizart.WizardResponse;
import vn.conguyetduong.hogwarts.app.transfer.mapper.PageMapper;
import vn.conguyetduong.hogwarts.app.transfer.mapper.WizardMapper;
import vn.conguyetduong.hogwarts.business.service.WizardService;
import vn.conguyetduong.hogwarts.business.service.external.KeycloakService;
import vn.conguyetduong.hogwarts.infra.model.Wizard;
import vn.conguyetduong.hogwarts.infra.specification.WizardSearchCriteria;
import vn.conguyetduong.hogwarts.infra.specification.WizardSearchSpecs;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wizards")
@RequiredArgsConstructor
public class PublicWizardController {
    private final WizardService service;
    private final WizardMapper mapper;
    private final KeycloakService keycloakService;
    private final PageMapper pageMapper;

    @PostMapping
    @WithSpan
    public ResponseEntity<PageDto<ShortWizardResponse>> findAll(
            @PageableDefault(size = 20, page = 0, sort = "name")
            @ParameterObject Pageable pageable,
            @Valid @RequestBody WizardSearchCriteria criteria
            ) {
        // convert criteria to a specification
        Specification<Wizard> spec = WizardSearchSpecs.of(criteria);

        // get all active wizards
        Page<Wizard> wizardPage = service.findAll(spec, pageable);

        // mapping
        Page<ShortWizardResponse> wizardDtoPage = wizardPage.map(mapper::toShortWizardResponse);

        wizardDtoPage.forEach(wizardDto -> {
            String url = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/v1/wizards/{id}")
                    .buildAndExpand(wizardDto.getId())
                    .toUriString();
            wizardDto.setUrl(url);
        });

        PageDto<ShortWizardResponse> pageDto = pageMapper.toPageDto(wizardDtoPage);
        return ResponseEntity.ok(pageDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WizardResponse> getWizard(@PathVariable UUID id) {
        Wizard wizard = service.getWizard(id);
        WizardResponse wizardResponse = mapper.toWizardResponse(wizard, keycloakService);

        return ResponseEntity.ok(wizardResponse);
    }
}
