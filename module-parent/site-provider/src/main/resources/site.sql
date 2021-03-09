create sequence IF NOT EXISTS master_seq;

CREATE TABLE IF NOT EXISTS SHOP_CATEGORIES (
    id bigint primary key,
    idParent bigint,
    title varchar(512)
);

-- alter table SITE add IMAGE varchar(256);

CREATE TABLE IF NOT EXISTS PIANA_CATEGORIES (
    ID bigint primary key,
    HEX_VIEW char(16),
    BINARY_VIEW char(64),
    PARENT_ID bigint,
    TITLE varchar(512),
    IMAGE varchar(32)
);
