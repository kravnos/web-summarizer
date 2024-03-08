drop database if exists Web_Summarizer_UAT;

create database Web_Summarizer_UAT;

CREATE TABLE users (
                       uid serial PRIMARY KEY NOT NULL,
                       first_name varchar(25) NOT NULL,
                       last_name varchar(25) NOT NULL,
                       email varchar(50) NOT NULL,
                       password varchar(25) NOT NULL,
                       phone_number bigint,
                       request_token varchar(50),
                       UNIQUE(email),
                       UNIQUE(phone_number),
                       UNIQUE(request_token)
);

CREATE TABLE history (
                         HID integer PRIMARY KEY NOT NULL,
                         UID integer,
                         history_content varchar(100000),
                         link varchar(200),
                         upload_time timestamp,
                         FOREIGN KEY(UID) REFERENCES users(UID)
);

INSERT INTO users (first_name, last_name, email, password, phone_number)
SELECT
    substr(md5(random()::text), 0, 20) AS first_name,
    substr(md5(random()::text), 0, 20) AS last_name,
    substr(md5(random()::text), 0, 20) || '@example.com' AS email,
    substr(md5(random()::text), 0, 20) AS password,
    floor(random() * 10000000000)::bigint AS phone_number

FROM generate_series(1, 10);