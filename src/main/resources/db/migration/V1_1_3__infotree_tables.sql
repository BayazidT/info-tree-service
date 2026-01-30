-- Enable required extensions<<<<<
-- This is the key line: install the types/functions into your app schema

-- Optional but good: also install topology if you ever need it
-- CREATE EXTENSION IF NOT EXISTS postgis_topology SCHEMA infotree;

-- =============================================
-- 1. Domains and Categories
-- =============================================

CREATE TYPE domain_enum AS ENUM ('CIVIC', 'MARKETPLACE', 'PLACE', 'CULTURE');

CREATE TABLE infotree.categories (
                                     id SERIAL PRIMARY KEY,
                                     domain domain_enum NOT NULL,
                                     name VARCHAR(100) NOT NULL,
                                     parent_id INTEGER REFERENCES infotree.categories(id),
                                     description TEXT,
                                     UNIQUE(domain, name)
);

-- =============================================
-- 2. Business Models (closed set)
-- =============================================

CREATE TYPE business_model_enum AS ENUM ('SERVICE', 'RENT', 'SALE', 'EVENT', 'OTHER');

CREATE TABLE infotree.listing_types (
                                        id SERIAL PRIMARY KEY,
                                        business_model business_model_enum NOT NULL,
                                        name VARCHAR(100) NOT NULL UNIQUE,
                                        description VARCHAR
);

-- =============================================
-- 3. Cities
-- =============================================

CREATE TABLE infotree.cities (
                                 id SERIAL PRIMARY KEY,
                                 name VARCHAR(100) NOT NULL,
                                 country VARCHAR(100) NOT NULL DEFAULT 'Germany',
                                 admin_level VARCHAR(50),                    -- e.g., 'state', 'district'
                                 population INTEGER,
--                                  location GEOMETRY(Point, 4326),
                                 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 deleted_at TIMESTAMP NULL,                  -- When soft-deleted
                                 created_by UUID NULL,
                                 updated_by UUID NULL,
                                 deleted_by UUID NULL,
                                 UNIQUE(name, country)
);

-- Partial unique indexes (these replace the invalid constraint and support WHERE)
CREATE UNIQUE INDEX idx_users_username_active
    ON infotree.users(LOWER(username))
    WHERE is_deleted = FALSE AND is_active = TRUE;

CREATE UNIQUE INDEX idx_users_email_active
    ON infotree.users(LOWER(email))
    WHERE email IS NOT NULL AND is_deleted = FALSE AND is_active = TRUE;

CREATE INDEX idx_users_active
    ON infotree.users(id)
    WHERE is_deleted = FALSE AND is_active = TRUE;

-- updated_at trigger for users
CREATE OR REPLACE FUNCTION infotree.trigger_set_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    IF OLD.* IS DISTINCT FROM NEW.* THEN
        NEW.updated_at = CURRENT_TIMESTAMP;
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_users_set_updated_at
    BEFORE UPDATE ON infotree.users
    FOR EACH ROW
    EXECUTE FUNCTION infotree.trigger_set_updated_at();

-- =============================================
-- 5. Base Entities (shared core)
-- =============================================

CREATE TABLE infotree.base_entities (
                                        id BIGSERIAL PRIMARY KEY,
                                        category_id INTEGER NOT NULL REFERENCES infotree.categories(id),
                                        listing_type_id INTEGER REFERENCES infotree.listing_types(id),  -- Required for marketplace
                                        title VARCHAR(200) NOT NULL,
                                        description TEXT,
                                        address TEXT,
                                        city_id INTEGER NOT NULL REFERENCES infotree.cities(id),
--                                         location GEOMETRY(Point, 4326) NOT NULL,
                                        created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                        updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                        is_active BOOLEAN DEFAULT TRUE
);

-- Indexes
CREATE INDEX idx_base_entities_category ON infotree.base_entities(category_id);
CREATE INDEX idx_base_entities_city ON infotree.base_entities(city_id);
-- CREATE INDEX idx_base_entities_location ON infotree.base_entities USING GIST(location);
CREATE INDEX idx_base_entities_created ON infotree.base_entities(created_at);

-- updated_at trigger (shared)
CREATE TRIGGER trg_base_entities_set_updated_at
    BEFORE UPDATE ON infotree.base_entities
    FOR EACH ROW
    WHEN (OLD.* IS DISTINCT FROM NEW.*)
    EXECUTE FUNCTION infotree.trigger_set_updated_at();

-- =============================================
-- 6. Domain: Civic / Emergency Services
-- =============================================

CREATE TABLE infotree.civic_services (
                                         base_id BIGINT PRIMARY KEY REFERENCES infotree.base_entities(id) ON DELETE CASCADE,
                                         contact_phone VARCHAR(50) NOT NULL,
                                         contact_email VARCHAR(100),
                                         is_24_7 BOOLEAN DEFAULT FALSE,
                                         last_verified TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                         extra_attributes JSONB DEFAULT '{}'::jsonb
);

-- =============================================
-- 7. Domain: Marketplace Listings
-- =============================================

CREATE TABLE infotree.marketplace_listings (
                                               base_id INTEGER PRIMARY KEY REFERENCES infotree.base_entities(id) ON DELETE CASCADE,
                                               user_id UUID NOT NULL REFERENCES infotree.users(id),
                                               price DECIMAL(12,2) NOT NULL CHECK (price > 0),
                                               currency VARCHAR(3) DEFAULT 'EUR',
                                               expires_at TIMESTAMPTZ NOT NULL,
                                               extra_attributes JSONB DEFAULT '{}'::jsonb
);

-- Trigger: expires_at must be after created_at
CREATE OR REPLACE FUNCTION infotree.check_marketplace_expires_after_created()
RETURNS TRIGGER AS $$
DECLARE
base_created TIMESTAMPTZ;
BEGIN
SELECT created_at INTO base_created
FROM infotree.base_entities
WHERE id = NEW.base_id;

IF NEW.expires_at <= base_created THEN
        RAISE EXCEPTION 'expires_at (%) must be after created_at (%)', NEW.expires_at, base_created;
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_marketplace_expires
    BEFORE INSERT OR UPDATE ON infotree.marketplace_listings
                         FOR EACH ROW
                         EXECUTE FUNCTION infotree.check_marketplace_expires_after_created();

-- =============================================
-- 8. Domain: Venues / Places
-- =============================================

CREATE TABLE infotree.venues (
                                 base_id BIGINT PRIMARY KEY REFERENCES infotree.base_entities(id) ON DELETE CASCADE,
                                 opening_hours TEXT,
                                 contact_phone VARCHAR(50),
                                 website VARCHAR(300),
                                 extra_attributes JSONB DEFAULT '{}'::jsonb
);

-- =============================================
-- 9. Media (normalized)
-- =============================================

CREATE TABLE infotree.media (
                                id BIGSERIAL PRIMARY KEY,
                                base_entity_id BIGINT NOT NULL REFERENCES infotree.base_entities(id) ON DELETE CASCADE,
                                url VARCHAR(500) NOT NULL,
                                alt_text VARCHAR(300),
                                position INTEGER DEFAULT 0,
                                uploaded_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_media_entity ON infotree.media(base_entity_id);

-- =============================================
-- 10. Domain Views (highly recommended)
-- =============================================

CREATE VIEW infotree.v_civic_services AS
SELECT
    b.*,
    c.domain,
    c.name AS category_name,
    cs.contact_phone,
    cs.contact_email,
    cs.is_24_7,
    cs.last_verified,
    cs.extra_attributes
FROM infotree.base_entities b
         JOIN infotree.categories c ON b.category_id = c.id
         JOIN infotree.civic_services cs ON b.id = cs.base_id
WHERE b.is_active = TRUE;

CREATE VIEW infotree.v_marketplace_listings AS
SELECT
    b.*,
    c.domain,
    c.name AS category_name,
    lt.business_model,
    m.user_id,
    m.price,
    m.currency,
    m.expires_at,
    m.extra_attributes
FROM infotree.base_entities b
         JOIN infotree.categories c ON b.category_id = c.id
         LEFT JOIN infotree.listing_types lt ON b.listing_type_id = lt.id
         JOIN infotree.marketplace_listings m ON b.id = m.base_id
WHERE b.is_active = TRUE
  AND m.expires_at > CURRENT_TIMESTAMP;

CREATE VIEW infotree.v_venues AS
SELECT
    b.*,
    c.domain,
    c.name AS category_name,
    v.opening_hours,
    v.contact_phone,
    v.website,
    v.extra_attributes
FROM infotree.base_entities b
         JOIN infotree.categories c ON b.category_id = c.id
         JOIN infotree.venues v ON b.id = v.base_id
WHERE b.is_active = TRUE;

-- =============================================
-- Optional: Enforce listing_type required for marketplace
-- =============================================

CREATE OR REPLACE FUNCTION infotree.enforce_marketplace_listing_type()
RETURNS TRIGGER AS $$
BEGIN
    IF (SELECT domain FROM infotree.categories WHERE id = NEW.category_id) = 'MARKETPLACE'
                                                 AND NEW.listing_type_id IS NULL THEN
    RAISE EXCEPTION 'Marketplace entities must have a listing_type_id';
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_enforce_marketplace_listing_type
    BEFORE INSERT OR UPDATE ON infotree.base_entities
                         FOR EACH ROW
                         EXECUTE FUNCTION infotree.enforce_marketplace_listing_type();

-- =============================================
-- End of Schema
-- =============================================