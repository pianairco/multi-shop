create sequence IF NOT EXISTS master_seq;
--
-- CREATE TABLE TEMPORARY_ID (
--     ASSIGN varchar,
--     ID bigint,
--     constraint UK_TEMPORARY_ID_ASSIGN unique (ASSIGN)
-- );
--
-- INSERT INTO TEMPORARY_ID (ASSIGN, ID) values ('admin', master_seq.nextval);

CREATE TABLE IF NOT EXISTS APP_INFO (
    ID bigint primary key,
    APP_PARAM varchar(64) not null,
    PARAM_VALUE varchar(64) not null,
    constraint UK_APP_APP_PARAM unique (APP_PARAM)
);

INSERT INTO APP_INFO select * from (
    select 1 ID, 'APP_DOMAIN' APP_PARAM, 'piana.ir' PARAM_VALUE UNION
    select 2, 'APP_TYPE', 'SHOP'
) where not exists(select * from APP_INFO);


CREATE TABLE IF NOT EXISTS AGENT (
    ID bigint primary key,
    USERNAME varchar(64) not null,
    constraint UK_AGENT_USERNAME unique (USERNAME)
);

INSERT INTO AGENT select * from (
    select 1 ID, 'admin' USERNAME UNION
    select 2, 'assistant'
) where not exists(select * from AGENT);

CREATE TABLE IF NOT EXISTS SITE (
    ID bigint primary key,
    AGENT_ID bigint not null,
    TENANT_ID varchar(32) not null,
    IS_ACTIVE number not null default 1,
    CREATION_DATE CHAR(10) not null default '0000/00/00',
    CREATION_TIME CHAR(8) not null default '00:00:00',
    MODIFICATION_DATE CHAR(10) not null default '0000/00/00',
    MODIFICATION_TIME CHAR(8) not null default '00:00:00',
    constraint FK_SITE_AGENT_ID foreign key (AGENT_ID) references AGENT(ID),
    constraint UK_SITE_TENANT_ID unique (TENANT_ID)
);

INSERT INTO SITE (ID, AGENT_ID, TENANT_ID, IS_ACTIVE) select * from (
   select 1 ID, 1 AGENT_ID, 'vavishka.piana.ir' TENANT_ID, 1 IS_ACTIVE union
   select 2, 1, 'shop.piana.ir', 1 union
   select 3, 1, 'ring.ir', 1
) where not exists(select * from SITE);

CREATE TABLE IF NOT EXISTS SITE_ROLE (
    ID bigint primary key,
    NAME varchar(64) not null,
    constraint UK_SITE_ROLE_NAME unique (NAME)
);

INSERT INTO SITE_ROLE (ID, NAME) select * from (
    select 1 ID, 'ROLE_SITE_ADMIN' NAME union
    select 2, 'ROLE_SITE_ASSISTANT'
) where not exists(select * from SITE_ROLE);


CREATE TABLE IF NOT EXISTS SITE_USER (
    ID bigint primary key,
    AGENT_ID bigint not null,
    SITE_ID bigint not null,
    constraint FK_SITE_USER_AGENT_ID foreign key (AGENT_ID) references AGENT(ID),
    constraint FK_SITE_USER_SITE_ID foreign key (SITE_ID) references SITE(ID)
);

INSERT INTO SITE_USER (ID, AGENT_ID, SITE_ID) select * from (
    select 1 ID, 1 AGENT_ID, 1 SITE_ID union
    select 2, 2, 1 union
    select 3, 1, 2 union
    select 4, 2, 2 union
    select 5, 1, 3 union
    select 6, 2, 3
) where not exists(select * from SITE_USER);

CREATE TABLE IF NOT EXISTS SITE_USER_ROLE (
    ID bigint primary key,
    SITE_USER_ID bigint not null,
    SITE_ROLE_ID bigint not null,
    constraint FK_SITE_USER_ROLE_SITE_USER_ID foreign key (SITE_USER_ID) references SITE_USER(ID),
    constraint FK_SITE_USER_ROLE_SITE_ROLE_ID foreign key (SITE_ROLE_ID) references SITE_ROLE(ID)
);

INSERT INTO SITE_USER_ROLE (ID, SITE_USER_ID, SITE_ROLE_ID) select * from (
    select 1 ID, 1 SITE_USER_ID, 1 SITE_ROLE_ID union
    select 2, 2, 2 union
    select 3, 3, 1 union
    select 4, 4, 2 union
    select 5, 5, 1 union
    select 6, 6, 2
) where not exists(select * from SITE_USER_ROLE);
