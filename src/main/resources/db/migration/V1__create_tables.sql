CREATE TABLE IF NOT EXISTS restapiproject.file_entity (
id BIGINT NOT NULL,
file_name VARCHAR(255),
PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS restapiproject.event_entity (
id BIGINT NOT NULL,
event_date DATETIME,
file_id BIGINT,
PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS restapiproject.user_entity (
id BIGINT NOT NULL,
first_name VARCHAR(255),
last_name VARCHAR(255),
PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS restapiproject.users_events_entity (
user_id BIGINT NOT NULL,
event_id BIGINT NOT NULL
);


ALTER TABLE restapiproject.event_entity
ADD CONSTRAINT events_files_id_fkey FOREIGN KEY (file_id) REFERENCES restapiproject.file_entity (id);

ALTER TABLE restapiproject.users_events_entity
ADD CONSTRAINT users_events_id_fk1 FOREIGN KEY (user_id) REFERENCES restapiproject.user_entity (id);

ALTER TABLE restapiproject.users_events_entity
ADD CONSTRAINT users_events_id_fk2 FOREIGN KEY (event_id) REFERENCES restapiproject.event_entity (id);


