package vn.conguyetduong.hogwarts.business.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.conguyetduong.hogwarts.business.exception.ApiException;
import vn.conguyetduong.hogwarts.business.exception.ErrorCode;
import vn.conguyetduong.hogwarts.infra.model.Wizard;
import vn.conguyetduong.hogwarts.infra.model.WizardImage;
import vn.conguyetduong.hogwarts.infra.repository.WizardRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WizardService {
    private final WizardRepository wizardRepo;

    @Transactional
    public Wizard create(Wizard wizard) {
        Wizard createdWizard;
        createdWizard = wizardRepo.save(wizard);
        return  createdWizard;
    }

    public List<Wizard> getWizards() {
        List<Wizard> wizards = wizardRepo.findAll();
        return wizards;
    }

    public Wizard getWizard(UUID id) {
        Wizard wizard = wizardRepo.findById(id).orElseThrow(() ->
                new ApiException(
                        ErrorCode.NOT_FOUND,
                        "Wizard with id '%s' not found".formatted(id)
                )
        );
        return  wizard;
    }

    @Transactional
    public Wizard putUpdate(UUID id, Wizard newWizard) {
        Wizard oldWizard = wizardRepo.findById(id).orElseThrow(() ->
            new ApiException(
                    ErrorCode.NOT_FOUND,
                    "Wizard with id '%s' not found".formatted(id)
            )
        );

        // update new values
        oldWizard.setName(newWizard.getName());
        oldWizard.setDescription(newWizard.getDescription());

        List<WizardImage> managedImages = oldWizard.getImages(); // this is the Hibernate-managed collection

        // remove all old images
        managedImages.clear();

        // add new images to the SAME collection
        if (newWizard.getImages() != null) {
            for (WizardImage img : newWizard.getImages()) {
                img.setWizard(oldWizard);
                managedImages.add(img);
            }
        }

        return wizardRepo.save(oldWizard);
    }

    @Transactional
    public void delete(UUID id) {
        Wizard oldWizard = wizardRepo.findById(id).orElseThrow(() ->
                new ApiException(
                        ErrorCode.NOT_FOUND,
                        "Wizard with id '%s' not found".formatted(id)
                )
        );
        wizardRepo.delete(oldWizard);
    }
}
