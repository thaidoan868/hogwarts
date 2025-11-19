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