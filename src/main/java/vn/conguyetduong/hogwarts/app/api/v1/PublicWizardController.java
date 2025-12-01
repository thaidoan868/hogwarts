package vn.conguyetduong.hogwarts.app.api.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizart.ShortWizardResponse;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizart.WizardResponse;
import vn.conguyetduong.hogwarts.app.transfer.mapper.WizardMapper;
import vn.conguyetduong.hogwarts.business.service.WizardService;
import vn.conguyetduong.hogwarts.business.service.external.KeycloakService;
import vn.conguyetduong.hogwarts.infra.model.Wizard;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wizards")
@RequiredArgsConstructor
public class PublicWizardController {
    private final WizardService wizardService;
    private final WizardMapper wizardMapper;
    private final KeycloakService keycloakService;

    @GetMapping
    public ResponseEntity<List<ShortWizardResponse>> getWizards() {
        List<Wizard> wizards = wizardService.getActiveWizards();
        List<ShortWizardResponse> responseWizards = wizardMapper.toShortWizardResponses(wizards);
        responseWizards.forEach(wizard -> {
            String url = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/v1/wizards/{id}")
                    .buildAndExpand(wizard.getId())
                    .toUriString();
            wizard.setUrl(url);
        });

        return ResponseEntity.ok(responseWizards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WizardResponse> getWizard(@PathVariable UUID id) {
        Wizard wizard = wizardService.getWizard(id);
        WizardResponse wizardResponse = wizardMapper.toWizardResponse(wizard, keycloakService);

        return ResponseEntity.ok(wizardResponse);
    }
}
