package vn.conguyetduong.hogwarts.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.conguyetduong.hogwarts.business.exception.ApiException;
import vn.conguyetduong.hogwarts.business.exception.ErrorCode;
import vn.conguyetduong.hogwarts.infra.constant.WizardStatus;
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

    private ApiException notFoundException(UUID id) {
        return new ApiException(
                ErrorCode.NOT_FOUND,
                "Wizard with id '%s' not found".formatted(id)
        );
    }

    private Wizard findWizardById(UUID id) {
        return wizardRepo.findById(id).orElseThrow(() -> notFoundException(id));
    }

    @Transactional
    public Wizard create(Wizard wizard) {
        Wizard createdWizard;
        createdWizard = wizardRepo.save(wizard);
        return  createdWizard;
    }

    public List<Wizard> getActiveWizards() {
        List<Wizard> wizards = wizardRepo.findByStatus(WizardStatus.ACTIVE);
        return wizards;
    }

    public Wizard getWizard(UUID id) {
        Wizard wizard = findWizardById(id);
        return  wizard;
    }

    @Transactional
    public Wizard putUpdate(UUID id, Wizard newWizard) {
        Wizard oldWizard = findWizardById(id);

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
        Wizard wizard = findWizardById(id);
        wizard.setStatus(WizardStatus.DELETED);
        wizardRepo.save(wizard);
    }
}
