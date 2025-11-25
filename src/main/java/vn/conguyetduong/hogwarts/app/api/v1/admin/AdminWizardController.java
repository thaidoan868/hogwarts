package vn.conguyetduong.hogwarts.app.api.v1.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizart.RegisterWizardRequest;
import vn.conguyetduong.hogwarts.app.transfer.mapper.WizardMapper;
import vn.conguyetduong.hogwarts.business.service.WizardService;
import vn.conguyetduong.hogwarts.infra.model.Wizard;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/wizards")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminWizardController {
    private final WizardService wizardService;
    private final WizardMapper wizardMapper;

    @PostMapping
    public ResponseEntity<?> createWizard(@RequestBody @Valid RegisterWizardRequest registerWizardRequest) {
        Wizard createWizard = wizardMapper.toWizard(registerWizardRequest);
        Wizard createdWizard = wizardService.create(createWizard);
        URI location = URI.create("/api/v1/wizards/%s".formatted(createdWizard.getId()));

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWizard(@RequestBody @Valid RegisterWizardRequest registerWizardRequest, @PathVariable UUID id) {
        Wizard updateWizard = wizardMapper.toWizard(registerWizardRequest);
        Wizard updatedWizard = wizardService.putUpdate(id,  updateWizard);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWizard(@PathVariable UUID id) {
        wizardService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
