create sequence IF NOT EXISTS master_seq;
-- alter sequence master_seq restart with 5000;

--
-- CREATE TABLE TEMPORARY_ID (
--     ASSIGN varchar,
--     ID bigint,
--     constraint UK_TEMPORARY_ID_ASSIGN unique (ASSIGN)
-- );
--
-- INSERT INTO TEMPORARY_ID (ASSIGN, ID) values ('admin', master_seq.nextval);

CREATE TABLE IF NOT EXISTS APP_INFO
(
    ID          bigint primary key,
    APP_PARAM   varchar(64) not null,
    PARAM_VALUE varchar(64) not null,
    constraint UK_APP_APP_PARAM unique (APP_PARAM)
);

INSERT INTO APP_INFO
select *
from (
         select 1 ID, 'APP_DOMAIN' APP_PARAM, 'piana.ir' PARAM_VALUE
         UNION
         select 2, 'APP_TYPE', 'SHOP'
     )
where not exists(select * from APP_INFO);

CREATE TABLE IF NOT EXISTS users
(
    id             bigint primary key,
    user_uuid      char(36),
    email          varchar(256) NOT NULL,
    mobile         char(11),
    password       char(128),
    form_password  char(128),
    email_verified number(1),
    name           varchar(64),
    picture_url    varchar(256),
    locale         char(2),
    family_name    varchar(64),
    given_name     varchar(64)
);

-- image => https://lh3.googleusercontent.com/a-/AOh14Gg8K7kIHhlEo0-oJjPmGBG73ciHeRQnMFuRWRjQ4A=s96-c
INSERT INTO users (id, user_uuid, email, email_verified, password, form_password, locale, given_name,
                   picture_url)
select *
from (
         select 1                                                                                           id,
                'admin'                                                                                     user_uuid,
                'rahmatii1366@gmail.com'                                                                    email,
                1                                                                                           email_verified,
                '$2a$10$9VrVPJ4IfzrbO82TUxNJCOUt9hPlj4ZLP/i2i7rSscYaYetkTmxoi'                              password,
                '$2a$10$9VrVPJ4IfzrbO82TUxNJCOUt9hPlj4ZLP/i2i7rSscYaYetkTmxoi'                              form_password,
                'fa'                                                                                        locale,
                'admin'                                                                                     given_name,
                'https://lh3.googleusercontent.com/a-/AOh14Gg8K7kIHhlEo0-oJjPmGBG73ciHeRQnMFuRWRjQ4A=s96-c' picture_url
     )
where not exists(select * from users);

CREATE TABLE IF NOT EXISTS SITE
(
    ID                bigint primary key,
    OWNER_ID          bigint       not null,
    TENANT_ID         varchar(32)  not null,
    CATEGORY          bigint       not null,
    TITLE             varchar(128) not null,
    INSTAGRAM_LINK    varchar(256),
    WHATSAPP_LINK     varchar(256),
    FACEBOOK_LINK     varchar(256),
    TEL_NUMBER        varchar(11),
    IS_ACTIVE         number       not null default 1,
    IMAGE             varchar(256),
    CREATION_DATE     CHAR(10)     not null default '0000/00/00',
    CREATION_TIME     CHAR(8)      not null default '00:00:00',
    MODIFICATION_DATE CHAR(10)     not null default '0000/00/00',
    MODIFICATION_TIME CHAR(8)      not null default '00:00:00',
    constraint FK_SITE_OWNER_ID foreign key (OWNER_ID) references USERS (ID),
    constraint UK_SITE_TENANT_ID unique (TENANT_ID)
);

INSERT INTO SITE (ID, OWNER_ID, TENANT_ID, CATEGORY, TITLE, IS_ACTIVE)
select *
from (
         select 1 ID, 1 OWNER_ID, 'shop.piana.ir' TENANT_ID, 0x4000000000000000 CATEGORY, 'shop' TITLE, 1 IS_ACTIVE
     )
where not exists(select * from SITE);

CREATE TABLE IF NOT EXISTS DOMAINS
(
    ID      bigint primary key,
    SITE_ID bigint       not null,
    DOMAIN  varchar(128) not null,
    constraint UK_DOMAINS_DOMAIN unique (DOMAIN),
    constraint FK_DOMAINS_SITE_ID foreign KEY (SITE_ID) references SITE (ID)
);

INSERT INTO DOMAINS (ID, SITE_ID, domain)
select *
from (
         select 1 ID, 1 SITE_ID, 'shop.piana.ir' domain
     )
where not exists(select * from DOMAINS);

CREATE TABLE IF NOT EXISTS AGENT
(
    ID      bigint primary key,
    USER_ID bigint not null,
    SITE_ID bigint not null,
    constraint FK_AGENT_USER foreign key (user_id) references users (id),
    constraint FK_AGENT_SITE foreign key (site_id) references site (id)
);

INSERT INTO AGENT
select *
from (
         select 1 ID, 1 USER_ID, 1 SITE_ID
     )
where not exists(select * from AGENT);

CREATE TABLE IF NOT EXISTS SITE_ROLE
(
    ID   bigint primary key,
    NAME varchar(64) not null,
    constraint UK_SITE_ROLE_NAME unique (NAME)
);

INSERT INTO SITE_ROLE (ID, NAME)
select *
from (
         select 1 ID, 'ROLE_SITE_OWNER' NAME
         union
         select 2 ID, 'ROLE_SITE_ADMIN' NAME
         union
         select 3, 'ROLE_SITE_ASSISTANT'
     )
where not exists(select * from SITE_ROLE);

CREATE TABLE IF NOT EXISTS SITE_AGENT_ROLE
(
    ID           bigint primary key,
    AGENT_ID     bigint not null,
    SITE_ROLE_ID bigint not null,
    constraint FK_SITE_AGENT_ROLE_AGENT_ID foreign key (AGENT_ID) references AGENT (ID),
    constraint FK_SITE_USER_ROLE_SITE_ROLE_ID foreign key (SITE_ROLE_ID) references SITE_ROLE (ID)
);

INSERT INTO SITE_AGENT_ROLE (ID, AGENT_ID, SITE_ROLE_ID)
select *
from (
         select 1 ID, 1 AGENT_ID, 1 SITE_ROLE_ID
         union
         select 2, 1, 2
     )
where not exists(select * from SITE_AGENT_ROLE);
