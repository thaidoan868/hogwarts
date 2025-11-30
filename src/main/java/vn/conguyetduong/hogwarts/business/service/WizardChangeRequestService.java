package vn.conguyetduong.hogwarts.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.conguyetduong.hogwarts.business.exception.ApiException;
import vn.conguyetduong.hogwarts.business.exception.ErrorCode;
import vn.conguyetduong.hogwarts.business.util.UserUtil;
import vn.conguyetduong.hogwarts.infra.model.WizardChangeRequest;
import vn.conguyetduong.hogwarts.infra.repository.WizardChangeRequestRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WizardChangeRequestService {
    private final WizardChangeRequestRepository repo;

    private WizardChangeRequest findRequestById(UUID id) {
        return repo.findById(id).orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Wizard change request with id " + id + " not found"));
    }

    @Transactional
    public WizardChangeRequest create(WizardChangeRequest request) {
        return repo.save(request);
    }

    public List<WizardChangeRequest> list() {
        // list all wizard change requests created by the current user
        return repo.findAllByCreatedBy(UserUtil.getCurrentUserId());
    }

    public List<WizardChangeRequest> listAll() {
        return repo.findAll();
    }

    public WizardChangeRequest get(UUID id) {
        return findRequestById(id);
    }

    @Transactional
    public WizardChangeRequest update(WizardChangeRequest updateRequest) { return repo.save(updateRequest); }
}
