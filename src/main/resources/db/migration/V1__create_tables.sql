create TABLE IF NOT EXISTS restapiproject.users (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255)
);

create TABLE IF NOT EXISTS restapiproject.files (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    file_path VARCHAR(255),
    deleted BOOLEAN,
    pay_load LONGBLOB,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

create TABLE IF NOT EXISTS restapiproject.events (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created TIMESTAMP,
    status VARCHAR(255),
    user_id BIGINT NOT NULL,
    file_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (file_id) REFERENCES files (id)
);