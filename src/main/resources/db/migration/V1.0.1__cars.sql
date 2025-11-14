CREATE TABLE IF NOT EXISTS cars
(
    id
    BIGINT
    GENERATED
    ALWAYS AS
    IDENTITY
    PRIMARY
    KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    make VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    year INTEGER,
    engine VARCHAR(255),
    fuel_type VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT now()
);