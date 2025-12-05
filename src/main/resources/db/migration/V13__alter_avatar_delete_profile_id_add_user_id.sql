ALTER TABLE avatar
    ADD COLUMN user_id  UUID,
    DROP COLUMN profile_id;

CREATE INDEX idx_avatar_user_id ON avatar(user_id);