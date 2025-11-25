-- 1) WIZARD: main live entity



-- 2) WIZARD_CHANGE_REQUEST: proposals (create/update/delete)
CREATE TABLE wizard_change_request (
    id                  UUID PRIMARY KEY,

    wizard_id           UUID REFERENCES wizard(id) ON DELETE SET NULL,
    action              VARCHAR(20) NOT NULL,         -- CREATE / UPDATE / DELETE
    status              VARCHAR(20) NOT NULL DEFAULT 'PENDING',  -- PENDING / APPROVED / REJECTED / EXPIRED

    payload             JSONB NOT NULL,               -- full desired state (incl. image ids etc.)
    payload_version     INT NOT NULL DEFAULT 1,       -- schema version of payload
    target_version      INT,                          -- wizard.version at proposal time (for UPDATE)

    created_by          BIGINT NOT NULL,              -- contributor
    created_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    reviewed_by         BIGINT,
    reviewed_at         TIMESTAMPTZ,
    review_comment      TEXT
);

CREATE INDEX idx_wcr_status ON wizard_change_request(status);
CREATE INDEX idx_wcr_wizard_id ON wizard_change_request(wizard_id);

ALTER TABLE wizard_change_request
    ADD CONSTRAINT chk_wcr_action
    CHECK (action IN ('CREATE', 'UPDATE', 'DELETE'));

ALTER TABLE wizard_change_request
    ADD CONSTRAINT chk_wcr_status
    CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED', 'EXPIRED'));



