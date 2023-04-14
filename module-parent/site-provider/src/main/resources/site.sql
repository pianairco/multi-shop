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

CREATE TABLE IF NOT EXISTS SITE_INFO (
    ID bigint primary key,
    TENANT_ID varchar(32) not null,
    TITLE varchar(128),
    DESCRIPTION varchar(512),
    TIP_TITLE varchar(128),
    TIP_DESCRIPTION varchar(512),
    HEADER_IMAGE varchar(128)
);

INSERT INTO SITE_INFO (ID, TENANT_ID, TITLE, DESCRIPTION,  TIP_TITLE, TIP_DESCRIPTION) select * from (
    select 1 ID, 'shop.piana.ir' TENANT_ID, 'PianaShop' TITLE,
           'When you are visiting a business Piana Shop, you can use the Message button to start a conversation with the business about a product that you are viewing.' DESCRIPTION,
           'Shop Tip' TIP_TITLE,
           'If you have a product idea or shop tip that you would like to share, count on us' TIP_DESCRIPTION
) where not exists(select * from SITE_INFO);

CREATE TABLE IF NOT EXISTS SITE_INFO_BOX (
    ID bigint primary key,
    TENANT_ID varchar(32) not null,
    TITLE varchar(128),
    DESCRIPTION varchar(512),
    IMAGE varchar(128)
);
