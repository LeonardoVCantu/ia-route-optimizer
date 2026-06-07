CREATE TABLE IF NOT EXISTS route_requests (
    internal_id UUID PRIMARY KEY,
    external_request_id VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    finished_at TIMESTAMP,
    result_path VARCHAR(255)
);