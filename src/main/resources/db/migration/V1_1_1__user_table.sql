CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE users (
                       id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    -- Core identity & authentication
                       username            VARCHAR(30)     NOT NULL UNIQUE,
                       email               VARCHAR(255)    NOT NULL UNIQUE,
                       password_hash       TEXT            NOT NULL,               -- bcrypt / argon2id / scrypt

    -- Profile / display info
                       display_name        VARCHAR(100),                           -- flexible (nickname, full name, etc.)
                       bio                 TEXT,
                       profile_picture_url TEXT,
                       cover_photo_url     TEXT,
                       website_url         TEXT,
                       location            VARCHAR(255),

    -- Account state
                       status              VARCHAR(20)     NOT NULL DEFAULT 'ACTIVE'
                           CHECK (status IN ('ACTIVE', 'INACTIVE', 'SUSPENDED', 'BANNED', 'DELETED')),

                       email_verified      BOOLEAN         NOT NULL DEFAULT FALSE,
                       phone_verified      BOOLEAN         NOT NULL DEFAULT FALSE,     -- if you support phone auth

    -- Security tracking
                       failed_login_attempts   INTEGER         NOT NULL DEFAULT 0,
                       last_login_at           TIMESTAMPTZ,
                       last_password_change_at TIMESTAMPTZ,
                       locked_until            TIMESTAMPTZ,                        -- temp lock after failed attempts

    -- Audit timestamps
                       created_at          TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
                       updated_at          TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
                       deleted_at          TIMESTAMPTZ,                            -- soft delete support

    -- Very useful extras for social/feed apps
                       last_active_at      TIMESTAMPTZ                             -- for "active now", sorting, analytics

    -- Note: NO role column here anymore!
);

CREATE INDEX idx_users_username    ON users(username);
CREATE INDEX idx_users_email       ON users(email);
CREATE INDEX idx_users_status      ON users(status);
CREATE INDEX idx_users_last_active ON users(last_active_at DESC) WHERE status = 'ACTIVE';