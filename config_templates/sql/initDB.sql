DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS user_seq;
DROP TYPE IF EXISTS user_flag;

CREATE TYPE user_flag AS ENUM ('active', 'deleted', 'superuser');
CREATE SEQUENCE user_seq START 100000;
CREATE TABLE users (
  id        INTEGER PRIMARY KEY DEFAULT nextval('user_seq'),
  full_name TEXT NOT NULL,
  email     TEXT NOT NULL,
  flag      user_flag NOT NULL,
  city_id   INTEGER,
  FOREIGN KEY (city_id) REFERENCES cities (Id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX email_idx ON users (email);


CREATE SEQUENCE city_seq START 100000;
CREATE TABLE cities (
                     id     INTEGER PRIMARY KEY DEFAULT nextval('city_seq'),
                     name   TEXT NOT NULL
);
CREATE UNIQUE INDEX city_idx ON cities (name);


CREATE SEQUENCE project_seq START 100000;
CREATE TABLE projects (
                      id     INTEGER PRIMARY KEY DEFAULT nextval('project_seq'),
                      name   TEXT NOT NULL
);
CREATE UNIQUE INDEX project_idx ON projects (name);


CREATE SEQUENCE group_seq START 100000;
CREATE TABLE project_groups (
                        id            INTEGER PRIMARY KEY DEFAULT nextval('group_seq'),
                        name          TEXT NOT NULL,
                        project_id    INTEGER,
                        FOREIGN KEY (project_id) REFERENCES projects (Id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX group_idx ON project_groups (name);