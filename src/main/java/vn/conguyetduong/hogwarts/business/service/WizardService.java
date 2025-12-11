package vn.conguyetduong.hogwarts.business.service;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.conguyetduong.hogwarts.business.exception.ApiException;
import vn.conguyetduong.hogwarts.business.exception.ErrorCode;
import vn.conguyetduong.hogwarts.infra.constant.WizardStatus;
import vn.conguyetduong.hogwarts.infra.model.Wizard;
import vn.conguyetduong.hogwarts.infra.repository.WizardRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WizardService {
    private final WizardRepository wizardRepo;
    private final Tracer tracer;

    @Transactional
    public Wizard create(Wizard wizard) {
        Wizard createdWizard;
        createdWizard = wizardRepo.save(wizard);
        return  createdWizard;
    }

    public List<Wizard> getActiveWizards() {
        Span span = tracer.spanBuilder(this.getClass().getSimpleName() + ".getActiveWizards").startSpan();
        List<Wizard> wizards = wizardRepo.findByStatus(WizardStatus.ACTIVE);
        span.setAttribute("status", "success");
        span.end();
        return  wizards;
    }

    public Wizard getWizard(UUID id) {
        Wizard wizard = wizardRepo.findById(id).orElseThrow(() ->
                new ApiException(
                        ErrorCode.NOT_FOUND,
                        "Wizard with id '%s' not found".formatted(id)
                )
        );
        wizard.setViewCount(wizard.getViewCount() + 1);
        return  wizard;
    }

    @Transactional
    public Wizard update(Wizard newWizard) {
        return wizardRepo.save(newWizard);
    }

    @Transactional
    public void delete(UUID id) {
        Wizard wizard = getWizard(id);
        wizard.setStatus(WizardStatus.DELETED);
        wizardRepo.save(wizard);
    }
}
