CREATE TABLE wizard (
    id              UUID PRIMARY KEY,
    name            VARCHAR(255) NOT NULL,
    description     TEXT,
    status          VARCHAR(20) NOT NULL, -- ACTIVE / DELETED
    view_count      BIGINT NOT NULL DEFAULT 0,

    created_at      TIMESTAMPTZ NOT NULL,
    created_by      UUID NOT NULL,
    updated_at      TIMESTAMPTZ NOT NULL,
    updated_by      UUID NOT NULL
);

CREATE INDEX idx_wizard_status ON wizard(status);
CREATE INDEX idx_wizard_view_count ON wizard(view_count);

CREATE TABLE wizard_image (
    id              UUID PRIMARY KEY,
    wizard_id       UUID NOT NULL REFERENCES wizard(id) ON DELETE CASCADE,

    url             TEXT NOT NULL,
    alt_text        TEXT,
    sort_order      INT NOT NULL,

    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by      UUID
);

CREATE INDEX idx_wizard_image_wizard_id ON wizard_image(wizard_id);