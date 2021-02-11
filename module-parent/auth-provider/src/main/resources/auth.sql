create sequence IF NOT EXISTS master_seq START WITH 1000 INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS users (
  id bigint primary key,
  user_uuid char(36),
  email varchar(256) NOT NULL,
  mobile char(11),
  password char(128),
  form_password char(128),
  email_verified number(1),
  name varchar(64),
  picture_url varchar(256),
  locale char(2),
  family_name varchar(64),
  given_name varchar(64)
);

-- image => https://lh3.googleusercontent.com/a-/AOh14Gg8K7kIHhlEo0-oJjPmGBG73ciHeRQnMFuRWRjQ4A=s96-c
INSERT INTO users (id, user_uuid, email, email_verified, password, form_password, locale, given_name, picture_url) select * from (
    select 1 id, 'admin' user_uuid, 'rahmatii1366@gmail.com' email, 1 email_verified,
           '$2a$10$kcXK1Vjmy79dMr.T7j5AJuWAlrGTqKWu/dk7kPFYESJGHqdCdO4.K' password, '$2a$10$kcXK1Vjmy79dMr.T7j5AJuWAlrGTqKWu/dk7kPFYESJGHqdCdO4.K' form_password, 'fa' locale,
           'admin' given_name, 'https://lh3.googleusercontent.com/a-/AOh14Gg8K7kIHhlEo0-oJjPmGBG73ciHeRQnMFuRWRjQ4A=s96-c' picture_url
) where not exists(select * from users);

CREATE TABLE IF NOT EXISTS user_roles (
    id bigint default master_seq.nextval primary key,
    user_id bigint not null,
    role_name varchar(64) NOT NULL,
    constraint fk_user_roles_2_user_by_id FOREIGN KEY (user_id)
        REFERENCES users(id)
);

INSERT INTO user_roles (id, user_id, role_name) select * from (
    select 1 id, 1 user_id, 'ROLE_ADMIN' role_name union
    select 2, 1, 'ROLE_USER'
) where not exists(select * from user_roles);
