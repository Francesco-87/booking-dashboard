-- V1__init.sql
-- Booking & Operations Admin Dashboard (v1)

-- Optional: keep everything in public schema for simplicity (default)
-- ---------- USERS ----------
CREATE TABLE IF NOT EXISTS users (
  id              BIGSERIAL PRIMARY KEY,
  full_name       VARCHAR(120) NOT NULL,
  email           VARCHAR(255) NOT NULL UNIQUE,
  role            VARCHAR(20) NOT NULL CHECK (role IN ('ADMIN', 'STAFF', 'CUSTOMER')),
  password_hash   VARCHAR(255),
  is_active       BOOLEAN NOT NULL DEFAULT TRUE,
  created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  updated_at      TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_users_role ON users(role);

-- ---------- STAFF PROFILES (PERFORMERS) ----------
CREATE TABLE IF NOT EXISTS staff_profiles (
  id              BIGSERIAL PRIMARY KEY,
  user_id         BIGINT NOT NULL UNIQUE,
  display_name    VARCHAR(120) NOT NULL,
  is_active       BOOLEAN NOT NULL DEFAULT TRUE,
  created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  updated_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  CONSTRAINT fk_staff_profiles_user
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ---------- SERVICES ----------
CREATE TABLE IF NOT EXISTS services (
  id                BIGSERIAL PRIMARY KEY,
  name              VARCHAR(120) NOT NULL,
  description       TEXT,
  duration_minutes  INTEGER NOT NULL CHECK (duration_minutes > 0),
  price_cents       INTEGER CHECK (price_cents >= 0),
  is_active         BOOLEAN NOT NULL DEFAULT TRUE,
  created_at        TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  updated_at        TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_services_active ON services(is_active);

-- ---------- BOOKINGS ----------
CREATE TABLE IF NOT EXISTS bookings (
  id                   BIGSERIAL PRIMARY KEY,

  service_id           BIGINT NOT NULL,
  staff_profile_id     BIGINT NOT NULL,
  created_by_user_id   BIGINT NOT NULL,

  customer_user_id     BIGINT,
  customer_name        VARCHAR(120),
  customer_email       VARCHAR(255),

  start_time           TIMESTAMPTZ NOT NULL,
  end_time             TIMESTAMPTZ NOT NULL,

  status               VARCHAR(20) NOT NULL DEFAULT 'REQUESTED'
    CHECK (status IN ('REQUESTED', 'CONFIRMED', 'CANCELLED', 'COMPLETED')),

  notes                TEXT,

  created_at           TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  updated_at           TIMESTAMPTZ NOT NULL DEFAULT NOW(),

  CONSTRAINT fk_bookings_service
    FOREIGN KEY (service_id) REFERENCES services(id) ON DELETE RESTRICT,

  CONSTRAINT fk_bookings_staff
    FOREIGN KEY (staff_profile_id) REFERENCES staff_profiles(id) ON DELETE RESTRICT,

  CONSTRAINT fk_bookings_created_by
    FOREIGN KEY (created_by_user_id) REFERENCES users(id) ON DELETE RESTRICT,

  CONSTRAINT fk_bookings_customer
    FOREIGN KEY (customer_user_id) REFERENCES users(id) ON DELETE SET NULL,

  CONSTRAINT chk_bookings_time_order CHECK (end_time > start_time)
);

CREATE INDEX IF NOT EXISTS idx_bookings_staff_time
  ON bookings(staff_profile_id, start_time, end_time);

CREATE INDEX IF NOT EXISTS idx_bookings_status
  ON bookings(status);

CREATE INDEX IF NOT EXISTS idx_bookings_service
  ON bookings(service_id);