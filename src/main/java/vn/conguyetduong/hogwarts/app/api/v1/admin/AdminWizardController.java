package vn.conguyetduong.hogwarts.app.api.v1.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizart.WizardRequest;
import vn.conguyetduong.hogwarts.app.transfer.mapper.WizardMapper;
import vn.conguyetduong.hogwarts.business.service.WizardService;
import vn.conguyetduong.hogwarts.infra.model.Wizard;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminWizardController {
    private final WizardService wizardService;
    private final WizardMapper wizardMapper;

    @PostMapping("/wizards")
    public ResponseEntity<?> createWizard(@RequestBody @Valid WizardRequest wizardRequest) {
        Wizard createWizard = wizardMapper.toWizard(wizardRequest);
        Wizard createdWizard = wizardService.create(createWizard);
        URI location = URI.create("/api/v1/wizards/%s".formatted(createdWizard.getId()));

        return ResponseEntity.created(location).build();
    }
}
