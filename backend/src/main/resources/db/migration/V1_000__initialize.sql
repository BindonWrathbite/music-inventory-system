CREATE TABLE student
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name TEXT,
    last_name  TEXT
);

CREATE TABLE location
(
    id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name TEXT
);

CREATE TABLE instrument
(
    id               BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    type             TEXT   NOT NULL,
    brand            TEXT,
    serial_number    TEXT,
    inventory_number TEXT   NOT NULL,
    repairs          TEXT,
    condition        TEXT   NOT NULL,
    purchase_date    DATE,
    purchase_price   NUMERIC(10, 2),
    notes            TEXT,
    location_id      BIGINT REFERENCES location (id) ON DELETE SET NULL,
    student_id       BIGINT REFERENCES student (id) ON DELETE SET NULL
);
