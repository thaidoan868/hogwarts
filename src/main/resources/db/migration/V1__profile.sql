CREATE TABLE IF NOT EXISTS profile (
  id                UUID PRIMARY KEY,
  keycloak_user_id  UUID NOT NULL,
  keycloak_realm    VARCHAR(100) NOT NULL DEFAULT 'hogwarts',
  keycloak_username VARCHAR(30) NOT NULL,

  full_name         VARCHAR(150) NOT NULL,
  phone_number      VARCHAR(20),
  address           TEXT,
  avatar_url        TEXT,
  date_of_birth     DATE,
  gender            VARCHAR(10) CHECK (gender IN ('MALE','FEMALE','OTHER')),

  created_at        TIMESTAMP NOT NULL DEFAULT now(),
  updated_at        TIMESTAMP NOT NULL DEFAULT now(),

  CONSTRAINT uq_profile_kc_user_id UNIQUE (keycloak_user_id),
  CONSTRAINT uq_profile_kc_username UNIQUE (keycloak_username)

);

CREATE INDEX IF NOT EXISTS idx_profile_kc_user_id ON profile (keycloak_user_id);