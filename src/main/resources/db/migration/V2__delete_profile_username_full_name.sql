ALTER TABLE profile
    DROP CONSTRAINT IF EXISTS uq_profile_kc_username,
    DROP COLUMN IF EXISTS keycloak_username,
    DROP COLUMN IF EXISTS full_name;