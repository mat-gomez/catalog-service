CREATE TABLE catalog_sync_state (
                                    id BIGINT PRIMARY KEY,
                                    current_version BIGINT NOT NULL,
                                    last_synced_at TIMESTAMP WITH TIME ZONE,
                                    status VARCHAR(50) NOT NULL
);


CREATE TABLE professional_category (
                                       id BIGINT PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL,
                                       description TEXT,
                                       enabled BOOLEAN NOT NULL DEFAULT TRUE,
                                       created_at TIMESTAMP WITH TIME ZONE,
                                       updated_at TIMESTAMP WITH TIME ZONE
);


CREATE TABLE professional (
                              id BIGINT PRIMARY KEY,
                              category_id BIGINT NOT NULL,
                              first_name VARCHAR(100) NOT NULL,
                              last_name VARCHAR(100) NOT NULL,
                              enabled BOOLEAN NOT NULL DEFAULT TRUE,
                              created_at TIMESTAMP WITH TIME ZONE,
                              updated_at TIMESTAMP WITH TIME ZONE,
                              CONSTRAINT fk_professional_category FOREIGN KEY (category_id) REFERENCES professional_category(id)
);


CREATE TABLE weekly_schedule (
                                 id BIGINT PRIMARY KEY,
                                 professional_id BIGINT NOT NULL,
                                 day_of_week VARCHAR(20) NOT NULL,
                                 start_time TIME NOT NULL,
                                 end_time TIME NOT NULL,
                                 slot_duration_minutes INT NOT NULL,
                                 enabled BOOLEAN NOT NULL DEFAULT TRUE,
                                 created_at TIMESTAMP WITH TIME ZONE,
                                 updated_at TIMESTAMP WITH TIME ZONE,
                                 CONSTRAINT fk_schedule_professional FOREIGN KEY (professional_id) REFERENCES professional(id)
);