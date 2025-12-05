CREATE TABLE address(
    id              UUID PRIMARY KEY,
    profile_id      UUID UNIQUE NOT NULL REFERENCES profile(id) ON DELETE CASCADE,
    accessibility   VARCHAR(20) NOT NULL,
    address         VARCHAR(300)
);
CREATE INDEX idx_address_profile_id on address(profile_id);

CREATE TABLE phone_number(
    id                  UUID PRIMARY KEY,
    profile_id          UUID UNIQUE NOT NULL REFERENCES profile(id) ON DELETE CASCADE,
    accessibility       VARCHAR(20) NOT NULL,
    phone_number        VARCHAR(20)
);
CREATE INDEX idx_phone_number_profile_id on phone_number(profile_id);

CREATE TABLE birth_date(
    id                  UUID PRIMARY KEY,
    profile_id          UUID UNIQUE NOT NULL REFERENCES profile(id) ON DELETE CASCADE,
    accessibility       VARCHAR(20) NOT NULL,
    birth_date          DATE
);
CREATE INDEX idx_birth_date_profile_id on birth_date(profile_id);

CREATE TABLE gender(
    id                  UUID PRIMARY KEY,
    profile_id          UUID UNIQUE NOT NULL REFERENCES profile(id) ON DELETE CASCADE,
    accessibility       VARCHAR(20) NOT NULL,
    gender              VARCHAR(10)
);
CREATE INDEX idx_gender_profile_id on gender(profile_id);

CREATE TABLE avatar(
    id                  UUID PRIMARY KEY,
    profile_id          UUID NOT NULL REFERENCES profile(id) ON DELETE CASCADE,
    original_url  VARCHAR(300) NOT NULL,
    medium_url    VARCHAR(300) NOT NULL,
    thumbnail_url       VARCHAR(300) NOT NULL,
    created_at          TIMESTAMPTZ NOT NULL,
    is_current          BOOLEAN NOT NULL
);
CREATE INDEX idx_avatar_profile_id on avatar(profile_id);
CREATE INDEX inx_avatar_is_current on avatar(is_current);