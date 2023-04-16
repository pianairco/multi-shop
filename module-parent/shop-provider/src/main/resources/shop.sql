create sequence IF NOT EXISTS master_seq;

CREATE TABLE IF NOT EXISTS PRODUCT (
    ID bigint primary key,
    registrar_ID bigint,
    piana_CATEGORY_ID bigint,
    TITLE varchar(256),
    DESCRIPTION varchar(1024),
    IMAGE varchar(256),
    is_confirmed number(1) default 0,
    register_time timestamp default CURRENT_TIMESTAMP,
    constraint FK_PRODUCT_registrar_ID foreign key (registrar_ID) references users(ID),
    constraint FK_PRODUCT_piana_CATEGORY_ID foreign key (piana_CATEGORY_ID) references piana_CATEGORIes(ID)
);

CREATE TABLE IF NOT EXISTS PRODUCT_CATEGORIZATION (
    ID bigint primary key,
    TITLE varchar(256),
    ROUTER_LINK varchar(64),
    ORDERS number(3),
    SITE_ID bigint,
    PIANA_CATEGORY_ID bigint,
    constraint FK_PRODUCT_CATEGORIZATION_SITE_ID foreign key (SITE_ID) references SITE(ID),
    constraint UK_PRODUCT_CATEGORIZATION_TITLE unique (SITE_ID, TITLE),
    constraint UK_PRODUCT_CATEGORIZATION_ROUTER_LINK unique (SITE_ID, ROUTER_LINK),
    constraint UK_PRODUCT_CATEGORIZATION_ORDERS unique (SITE_ID, ORDERS)
);

INSERT INTO PRODUCT_CATEGORIZATION select * from (
    select 1 ID, 'گروه یک' TITLE, 'pack1' ROUTER_LINK, 1 ORDERS, 1 SITE_ID, 4629771610314768384 PIANA_CATEGORY_ID UNION
    select 2 ID, 'گروه دو' TITLE, 'pack2' ROUTER_LINK, 2 ORDERS, 1 SITE_ID, 4629771612462252032 UNION
    select 3 ID, 'گروه سه' TITLE, 'pack3' ROUTER_LINK, 3 ORDERS, 1 SITE_ID, 4629771619978444800
) where not exists(select * from PRODUCT_CATEGORIZATION);

CREATE TABLE IF NOT EXISTS store_pool (
    ID bigint primary key,
    PRODUCT_ID bigint primary key,
    SITE_ID bigint,
    PIANA_CATEGORY_ID bigint primary key,
    CATEGORY_ID bigint,
    TITLE varchar(256),
    DESCRIPTION varchar(1024),
    IMAGE varchar(256),
    MEASUREMENT_UNIT varchar(64),
    MEASUREMENT_UNIT_RATIO int,
    inventory bigint,
    PRICE bigint,
    PERCENTAGE int,
    constraint FK_store_pool_PRODUCT_ID foreign key (product_id) references product(ID),
    constraint FK_store_pool_SITE_ID foreign key (SITE_ID) references SITE(ID),
    constraint FK_store_pool_piana_CATEGORY_ID foreign key (PIANA_CATEGORY_ID) references PIANA_CATEGORIES(ID),
    constraint FK_store_pool_CATEGORY_ID foreign key (CATEGORY_ID) references PRODUCT_CATEGORIZATION(ID),
    constraint UK_store_pool_TITLE_measurement unique (SITE_ID, TITLE, MEASUREMENT_UNIT)
);

CREATE TABLE IF NOT EXISTS sale_price (
    ID bigint primary key,
    product_ID bigint,
    store_pool_ID bigint,
    SITE_ID bigint,
    changer_id bigint,
    change_time timestamp,
    PRICE bigint,
    percentage int,
    constraint FK_sale_price_PRODUCT_ID foreign key (product_id) references product(ID),
    constraint FK_sale_price_store_pool_ID foreign key (store_pool_id) references store_pool(ID),
    constraint FK_sale_price_site_ID foreign key (SITE_ID) references Site(ID),
    constraint FK_sale_price_changer_id foreign key (changer_id) references users(ID)
);

CREATE TABLE IF NOT EXISTS purchase_invoice (
    ID bigint primary key,
    product_ID bigint,
    store_pool_ID bigint,
    SITE_ID bigint,
    buyer_id bigint,
    buy_date char(10),
    buy_time timestamp,
    quantity int,
    MEASUREMENT_UNIT varchar(64),
    MEASUREMENT_UNIT_RATIO int,
    unit_price bigint,
    total_price bigint,
    constraint FK_sale_price_PRODUCT_ID foreign key (product_id) references product(ID),
    constraint FK_sale_price_store_pool_ID foreign key (store_pool_id) references store_pool(ID),
    constraint FK_sale_price_site_ID foreign key (SITE_ID) references Site(ID),
    constraint FK_sale_price_changer_id foreign key (buyer_id) references users(ID)
);

CREATE TABLE IF NOT EXISTS sale_invoice (
    ID bigint primary key,
    product_ID bigint,
    store_pool_ID bigint,
    SITE_ID bigint,
    seller_id bigint,
    sell_date char(10),
    sell_time timestamp,
    quantity int,
    MEASUREMENT_UNIT varchar(64),
    MEASUREMENT_UNIT_RATIO int,
    unit_price bigint,
    total_price bigint,
    constraint FK_sale_price_PRODUCT_ID foreign key (product_id) references product(ID),
    constraint FK_sale_price_store_pool_ID foreign key (store_pool_id) references store_pool(ID),
    constraint FK_sale_price_site_ID foreign key (SITE_ID) references Site(ID),
    constraint FK_sale_price_changer_id foreign key (seller_id) references users(ID)
);
