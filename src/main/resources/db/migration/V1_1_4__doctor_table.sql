CREATE TABLE infotree.doctors (
                                  base_id BIGINT PRIMARY KEY
                                      REFERENCES infotree.base_entities(id) ON DELETE CASCADE,

    -- Personal / Professional
                                  first_name      VARCHAR(100) NOT NULL,
                                  last_name       VARCHAR(100) NOT NULL,
                                  title           VARCHAR(50),
                                  gender          VARCHAR(1),               -- 'M', 'F', 'D', 'X' → length 1 is enough

    -- Professional info
                                  id_number       VARCHAR(20) UNIQUE,       -- assuming approbation / license is unique
                                  specialties     TEXT[] NOT NULL DEFAULT ARRAY[]::TEXT[],
                                  languages       TEXT[] NOT NULL DEFAULT ARRAY[]::TEXT[],

    -- Insurance & availability
                                  accepts_new_patients    BOOLEAN NOT NULL DEFAULT TRUE,
                                  private_patients_only   BOOLEAN NOT NULL DEFAULT FALSE,
                                  telemedicine            BOOLEAN NOT NULL DEFAULT FALSE,
                                  emergency_appointments  BOOLEAN NOT NULL DEFAULT FALSE,
                                  telemedicine_available  BOOLEAN NOT NULL DEFAULT FALSE,

    -- Contact & scheduling
                                  appointment_url         VARCHAR(300),
                                  consultation_hours      JSONB NOT NULL DEFAULT '{}'::JSONB,

    -- Flexible data
                                  extra_attributes        JSONB NOT NULL DEFAULT '{}'::JSONB,

    -- Optional: who created/updated (if you have users table)
                                  created_by UUID REFERENCES infotree.users(id),
                                  updated_by UUID REFERENCES infotree.users(id),

    -- Constraints for data quality
                                  CONSTRAINT chk_gender_valid CHECK (gender IN ('M', 'F', 'D', 'X') OR gender IS NULL),
                                  CONSTRAINT chk_first_name_not_empty CHECK (TRIM(first_name) <> ''),
                                  CONSTRAINT chk_last_name_not_empty  CHECK (TRIM(last_name) <> '')
);

-- Indexes for performance (add these after table creation)
CREATE INDEX idx_doctors_specialties ON infotree.doctors USING GIN (specialties);
CREATE INDEX idx_doctors_languages   ON infotree.doctors USING GIN (languages);
CREATE INDEX idx_doctors_id_number   ON infotree.doctors (id_number);