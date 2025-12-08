CREATE TABLE token(
    id          UUID PRIMARY KEY,
    email       VARCHAR(100) NOT NULL,
    code        VARCHAR(20) NOT NULL,
    expires_at  TIMESTAMPTZ NOT NULL
);
CREATE INDEX idx_email_code_expires_at ON token(email, code, expires_at);