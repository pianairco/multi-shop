create sequence IF NOT EXISTS UNIQUE_SEQUENCE;

CREATE TABLE IF NOT EXISTS users (
  id bigint default UNIQUE_SEQUENCE.nextval primary key,
  user_id char(30),
  email varchar(256) NOT NULL,
  mobile char(11),
  password char(128),
  email_verified number(1),
  name varchar(64),
  picture_url varchar(256),
  locale char(2),
  family_name varchar(64),
  given_name varchar(64)
);

delete from users where id in(1, 2);

INSERT INTO users (id, user_id, email, email_verified, password) values
(1, 'admin', 'admin', 1, '$2a$10$kcXK1Vjmy79dMr.T7j5AJuWAlrGTqKWu/dk7kPFYESJGHqdCdO4.K'),
(2, 'seo', 'seo', 1, '$2a$10$kcXK1Vjmy79dMr.T7j5AJuWAlrGTqKWu/dk7kPFYESJGHqdCdO4.K');

CREATE TABLE IF NOT EXISTS DATASOURCES (
    ID bigint default UNIQUE_SEQUENCE.nextval primary key,
    TENANT_ID varchar(32) not null,
    DB_SCHEMA varchar(64) not null,
    DB_SCRIPT varchar(256) not null,
    DB_DRIVER varchar(64) not null,
    DB_URL varchar(256) not null,
    DB_USERNAME varchar(64) not null,
    DB_PASSWORD varchar(256) not null,
    MAX_PULL_SIZE number not null default 5,
    IS_ACTIVE number not null default 1
);
-- INSERT INTO DATASOURCES (id, TENANT_ID, DB_SCHEMA, DB_DRIVER, DB_URL, DB_USERNAME, DB_PASSWORD, MAX_PULL_SIZE, IS_ACTIVE) values
-- (UNIQUE_SEQUENCE.nextval, 'vavishka.ir', 'vavishka', 'org.h2.Driver', 'jdbc:h2:file:./data/vavishka', 'vavishka', 'v', 5, 1);
INSERT INTO DATASOURCES select * from (
   select UNIQUE_SEQUENCE.nextval id, 'vavishka.ir' tenant_id, 'vavishka' db_schema, '/databases/vavishka.sql' db_script, 'org.h2.Driver' db_driver, 'jdbc:h2:file:./data/vavishka' db_url, 'vavishka' db_username, 'v' db_password, 5 MAX_PULL_SIZE, 1 IS_ACTIVE union
--    select UNIQUE_SEQUENCE.nextval id, 'vavishka.ir' tenant_id, 'vavishka' db_schema, 'classpath:databases/vavishka.sql' db_script, 'org.h2.Driver' db_driver, 'jdbc:h2:file:./data/vavishka' db_url, 'vavishka' db_username, 'v' db_password, 5 MAX_PULL_SIZE, 1 IS_ACTIVE union
   select UNIQUE_SEQUENCE.nextval, 'piana.ir', 'piana', '/databases/piana.sql', 'org.h2.Driver', 'jdbc:h2:file:./data/piana', 'piana', 'p', 5, 1) x
--    select UNIQUE_SEQUENCE.nextval, 'piana.ir', 'piana', 'classpath:databases/piana.sql', 'org.h2.Driver', 'jdbc:h2:file:./data/piana', 'piana', 'p', 5, 1) x
where not exists(select * from datasources);
