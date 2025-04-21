CREATE TABLE "user"
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username    TEXT    NOT NULL UNIQUE,
    password    TEXT    NOT NULL,
    role        TEXT    NOT NULL,
    location_id BIGINT  REFERENCES location(id) ON DELETE SET NULL
);
