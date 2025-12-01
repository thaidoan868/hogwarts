package vn.conguyetduong.hogwarts.app.api.v1.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizart.RegisterWizardRequest;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizart.WizardPatchUpdateRequest;
import vn.conguyetduong.hogwarts.app.transfer.mapper.WizardMapper;
import vn.conguyetduong.hogwarts.business.service.WizardService;
import vn.conguyetduong.hogwarts.infra.model.Wizard;
import vn.conguyetduong.hogwarts.infra.model.WizardImage;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/wizards")
@RequiredArgsConstructor
public class AdminWizardController {
    private final WizardService wizardService;
    private final WizardMapper wizardMapper;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid RegisterWizardRequest registerWizardRequest) {
        Wizard createWizard = wizardMapper.toWizard(registerWizardRequest);
        Wizard createdWizard = wizardService.create(createWizard);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/wizards/{id}")
                .buildAndExpand(createdWizard.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putUpdate(@RequestBody @Valid RegisterWizardRequest request, @PathVariable UUID id) {
        Wizard wizard = wizardService.getWizard(id);

        // mapping
        Wizard updateWizard = wizardMapper.putUpdate(request, wizard);
        List<WizardImage> images = wizardMapper.toWizardImageList(request.getImages());
        if (images != null) {
            images.forEach(image -> image.setWizard(wizard));
        }

        if (wizard.getImages() != null) {
            wizard.getImages().clear();
            if(images != null) {
                wizard.getImages().addAll(images);
            }
        } else {
            wizard.setImages(images);
        }

        // updating
        Wizard updatedWizard = wizardService.update(updateWizard);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchUpdate(@RequestBody @Valid WizardPatchUpdateRequest request, @PathVariable UUID id) {
        Wizard wizard = wizardService.getWizard(id);

        // mapping
        Wizard updateWizard = wizardMapper.patchUpdateWizard(request, wizard);
        List<WizardImage> images = wizardMapper.toWizardImageList(request.getImages());
        if (images != null) {
            images.forEach(image -> image.setWizard(wizard));
        }

        if (wizard.getImages() != null) {
            if (images != null ) {
                wizard.getImages().clear();
                wizard.getImages().addAll(images);
            }
        } else {
            wizard.setImages(images);
        }

        // updating
        Wizard updatedWizard = wizardService.update(updateWizard);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWizard(@PathVariable UUID id) {
        wizardService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
