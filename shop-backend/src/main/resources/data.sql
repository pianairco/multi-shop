create sequence IF NOT EXISTS master_seq;

CREATE TABLE IF NOT EXISTS DATASOURCES (
    ID bigint default master_seq.nextval primary key,
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
-- (master_seq.nextval, 'vavishka.ir', 'vavishka', 'org.h2.Driver', 'jdbc:h2:file:./data/vavishka', 'vavishka', 'v', 5, 1);
INSERT INTO DATASOURCES select * from (
   select master_seq.nextval id, 'vavishka.ir' tenant_id, 'vavishka' db_schema, '/databases/vavishka.sql' db_script, 'org.h2.Driver' db_driver, 'jdbc:h2:file:./data/vavishka' db_url, 'vavishka' db_username, 'v' db_password, 5 MAX_PULL_SIZE, 1 IS_ACTIVE union
--    select UNIQUE_SEQUENCE.nextval id, 'vavishka.ir' tenant_id, 'vavishka' db_schema, 'classpath:databases/vavishka.sql' db_script, 'org.h2.Driver' db_driver, 'jdbc:h2:file:./data/vavishka' db_url, 'vavishka' db_username, 'v' db_password, 5 MAX_PULL_SIZE, 1 IS_ACTIVE union
   select master_seq.nextval, 'piana.ir', 'piana', '/databases/piana.sql', 'org.h2.Driver', 'jdbc:h2:file:./data/piana', 'piana', 'p', 5, 1) x
--    select UNIQUE_SEQUENCE.nextval, 'piana.ir', 'piana', 'classpath:databases/piana.sql', 'org.h2.Driver', 'jdbc:h2:file:./data/piana', 'piana', 'p', 5, 1) x
where not exists(select * from datasources);
