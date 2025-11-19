-- =========================================================
-- V1__init_wizard_and_image.sql
-- Wizard core + proposals + images (general image table)
-- PostgreSQL
-- =========================================================

-- 1) WIZARD: main live entity
CREATE TABLE wizard (
    id              BIGSERIAL PRIMARY KEY,
    name            VARCHAR(255) NOT NULL,
    description     TEXT,
    status          VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',  -- ACTIVE / ARCHIVED / DELETED
    view_count      BIGINT NOT NULL DEFAULT 0,
    version         INT NOT NULL DEFAULT 0,                 -- optimistic locking / proposal target version

    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by      BIGINT,
    updated_at      TIMESTAMPTZ,
    updated_by      BIGINT
);

CREATE INDEX idx_wizard_status ON wizard(status);
CREATE INDEX idx_wizard_view_count ON wizard(view_count);

ALTER TABLE wizard
    ADD CONSTRAINT chk_wizard_status
    CHECK (status IN ('ACTIVE', 'ARCHIVED', 'DELETED'));


-- 2) WIZARD_CHANGE_REQUEST: proposals (create/update/delete)
CREATE TABLE wizard_change_request (
    id                  BIGSERIAL PRIMARY KEY,

    wizard_id           BIGINT REFERENCES wizard(id) ON DELETE SET NULL,
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


-- 3) WIZARD_IMAGE: images attached to a wizard (after approval)
-- For now this table keeps url directly. Later you can switch to FK -> image.id if you want.
CREATE TABLE wizard_image (
    id              BIGSERIAL PRIMARY KEY,
    wizard_id       BIGINT NOT NULL REFERENCES wizard(id) ON DELETE CASCADE,

    url             TEXT NOT NULL,       -- public or internal URL to MinIO object
    alt_text        TEXT,
    sort_order      INT NOT NULL DEFAULT 0,
    is_primary      BOOLEAN NOT NULL DEFAULT FALSE,
    status          VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',  -- ACTIVE / DELETED

    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by      BIGINT
);

CREATE INDEX idx_wizard_image_wizard_id ON wizard_image(wizard_id);
CREATE INDEX idx_wizard_image_status ON wizard_image(status);

ALTER TABLE wizard_image
    ADD CONSTRAINT chk_wizard_image_status
    CHECK (status IN ('ACTIVE', 'DELETED'));


-- 4) IMAGE: general image table (replaces wizard_temp_image)
-- Used as upload library; proposals can reference image.id in their payload.
CREATE TABLE image (
    id              UUID PRIMARY KEY,
    uploader_id     UUID,
    bucket          VARCHAR(100) NOT NULL,
    object_name     TEXT NOT NULL,
    url             TEXT NOT NULL,
    content_type    VARCHAR(255),
    size_bytes      BIGINT,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW()
);