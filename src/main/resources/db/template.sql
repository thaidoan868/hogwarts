CREATE TABLE wizard_change_request (
    id                  UUID PRIMARY KEY,

    wizard_id           UUID REFERENCES wizard(id) ON DELETE SET NULL,
    action              VARCHAR(20) NOT NULL,         -- CREATE / UPDATE / DELETE
    status              VARCHAR(20) NOT NULL DEFAULT 'PENDING',  -- PENDING / APPROVED / REJECTED

    payload             JSONB NOT NULL,

    created_by          UUID NOT NULL,
    created_at          TIMESTAMPTZ NOT NULL,
    contributor_comment  TEXT,

    reviewed_by         UUID,
    reviewed_at         TIMESTAMPTZ,
    review_comment      TEXT
);

CREATE INDEX idx_wcr_status ON wizard_change_request(status);
CREATE INDEX idx_wcr_created_by on wizard_change_request(created_by);

