-- V1__init.sql
-- Booking & Operations Admin Dashboard (v1)

-- Optional: keep everything in public schema for simplicity (default)

-- ---------- ENUM TYPES ----------
DO $$ BEGIN
  CREATE TYPE user_role AS ENUM ('ADMIN', 'STAFF');
EXCEPTION WHEN duplicate_object THEN NULL; END $$;

DO $$ BEGIN
  CREATE TYPE slot_status AS ENUM ('AVAILABLE', 'BLOCKED');
EXCEPTION WHEN duplicate_object THEN NULL; END $$;

DO $$ BEGIN
  CREATE TYPE booking_status AS ENUM ('REQUESTED', 'CONFIRMED', 'CANCELLED', 'COMPLETED');
EXCEPTION WHEN duplicate_object THEN NULL; END $$;

-- ---------- USERS ----------
CREATE TABLE IF NOT EXISTS users (
  id              BIGSERIAL PRIMARY KEY,
  full_name       VARCHAR(120) NOT NULL,
  email           VARCHAR(255) NOT NULL,
  role            user_role NOT NULL,
  password_hash   VARCHAR(255) NOT NULL,
  is_active       BOOLEAN NOT NULL DEFAULT TRUE,
  created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  updated_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  CONSTRAINT uq_users_email UNIQUE (email)
);

CREATE INDEX IF NOT EXISTS idx_users_role ON users(role);

-- ---------- STAFF PROFILES (1:1 with users for STAFF) ----------
CREATE TABLE IF NOT EXISTS staff_profiles (
  id              BIGSERIAL PRIMARY KEY,
  user_id         BIGINT NOT NULL,
  display_name    VARCHAR(120) NOT NULL,
  is_active       BOOLEAN NOT NULL DEFAULT TRUE,
  created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  updated_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  CONSTRAINT uq_staff_profiles_user UNIQUE (user_id),
  CONSTRAINT fk_staff_profiles_user FOREIGN KEY (user_id)
    REFERENCES users(id) ON DELETE CASCADE
);

-- ---------- SERVICES ----------
CREATE TABLE IF NOT EXISTS services (
  id                BIGSERIAL PRIMARY KEY,
  name              VARCHAR(120) NOT NULL,
  description       TEXT,
  duration_minutes  INTEGER NOT NULL,
  price_cents       INTEGER, -- optional: keep nullable; if used, store in cents
  is_active         BOOLEAN NOT NULL DEFAULT TRUE,
  created_at        TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  updated_at        TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  CONSTRAINT chk_services_duration_positive CHECK (duration_minutes > 0),
  CONSTRAINT chk_services_price_nonneg CHECK (price_cents IS NULL OR price_cents >= 0)
);

CREATE INDEX IF NOT EXISTS idx_services_active ON services(is_active);

-- ---------- STAFF <-> SERVICES (many-to-many) ----------
CREATE TABLE IF NOT EXISTS staff_services (
  staff_profile_id  BIGINT NOT NULL,
  service_id        BIGINT NOT NULL,
  created_at        TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  PRIMARY KEY (staff_profile_id, service_id),
  CONSTRAINT fk_staff_services_staff FOREIGN KEY (staff_profile_id)
    REFERENCES staff_profiles(id) ON DELETE CASCADE,
  CONSTRAINT fk_staff_services_service FOREIGN KEY (service_id)
    REFERENCES services(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_staff_services_service ON staff_services(service_id);

-- ---------- AVAILABILITY SLOTS ----------
CREATE TABLE IF NOT EXISTS availability_slots (
  id               BIGSERIAL PRIMARY KEY,
  staff_profile_id BIGINT NOT NULL,
  start_time       TIMESTAMPTZ NOT NULL,
  end_time         TIMESTAMPTZ NOT NULL,
  status           slot_status NOT NULL DEFAULT 'AVAILABLE',
  note             VARCHAR(255),
  created_at       TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  updated_at       TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  CONSTRAINT fk_slots_staff FOREIGN KEY (staff_profile_id)
    REFERENCES staff_profiles(id) ON DELETE CASCADE,
  CONSTRAINT chk_slots_time_order CHECK (end_time > start_time)
);

-- for filtering by staff + date range
CREATE INDEX IF NOT EXISTS idx_slots_staff_time
  ON availability_slots(staff_profile_id, start_time, end_time);

-- ---------- BOOKINGS ----------
CREATE TABLE IF NOT EXISTS bookings (
  id               BIGSERIAL PRIMARY KEY,
  service_id       BIGINT NOT NULL,
  staff_profile_id BIGINT NOT NULL,
  customer_name    VARCHAR(120) NOT NULL,
  customer_email   VARCHAR(255),
  start_time       TIMESTAMPTZ NOT NULL,
  end_time         TIMESTAMPTZ NOT NULL,
  status           booking_status NOT NULL DEFAULT 'REQUESTED',
  notes            TEXT,
  created_at       TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  updated_at       TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  CONSTRAINT fk_bookings_service FOREIGN KEY (service_id)
    REFERENCES services(id) ON DELETE RESTRICT,
  CONSTRAINT fk_bookings_staff FOREIGN KEY (staff_profile_id)
    REFERENCES staff_profiles(id) ON DELETE RESTRICT,
  CONSTRAINT chk_bookings_time_order CHECK (end_time > start_time)
);

-- common list filters: staff + date range + status
CREATE INDEX IF NOT EXISTS idx_bookings_staff_time
  ON bookings(staff_profile_id, start_time, end_time);

CREATE INDEX IF NOT EXISTS idx_bookings_status
  ON bookings(status);

CREATE INDEX IF NOT EXISTS idx_bookings_service
  ON bookings(service_id);