create sequence IF NOT EXISTS master_seq;

CREATE TABLE IF NOT EXISTS SHOP_CATEGORIES (
    id bigint primary key,
    idParent bigint,
    title varchar(512)
);
