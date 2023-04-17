create sequence IF NOT EXISTS master_seq;

CREATE TABLE IF NOT EXISTS PRODUCT (
    ID bigint primary key,
    registrar_site_ID bigint,
    piana_CATEGORY_ID bigint,
    TITLE varchar(256),
    DESCRIPTION varchar(1024),
    IMAGE varchar(256),
    is_confirmed number(1) default 0,
    register_time timestamp default CURRENT_TIMESTAMP,
    constraint FK_PRODUCT_registrar_ID foreign key (registrar_site_ID) references site(ID),
    constraint FK_PRODUCT_piana_CATEGORY_ID foreign key (piana_CATEGORY_ID) references piana_CATEGORIes(ID)
);

INSERT INTO PRODUCT (id, registrar_site_ID, PIANA_CATEGORY_ID, title, description, image) select * from (
    select 1 ID, 1 registrar_site_ID, 4629770785681047552 PIANA_CATEGORY_ID, 'a' TITLE, 'aaa aaa' description, '/assets/products/a.jpg' image UNION
    select 2 , 1 , 4629841154425225216, 'a1', 'aaa aaa', '/assets/products/a1.jpg' UNION
    select 3 , 1 , 4629911523169402880, 'a2', 'aaa aaa', '/assets/products/a2.jpg' UNION
    select 4 , 1 , 4629981891913580544, 'a3', 'aaa aaa', '/assets/products/a3.jpg'  UNION
    select 5 , 1 , 4630052260657758208, 'a4', 'aaa aaa', '/assets/products/a4.jpg'  UNION
    select 6 , 1 , 4630122629401935872, 'a5', 'aaa aaa', '/assets/products/a5.jpg'  UNION
    select 7 , 1 , 4630192998146113536, 'a6', 'aaa aaa', '/assets/products/a6.jpg'  UNION
    select 8 , 1 , 4630263366890291200, 'a7', 'aaa aaa', '/assets/products/a7.jpg'  UNION
    select 9 , 1 , 4630333735634468864, 'a8', 'aaa aaa', '/assets/products/a8.jpg'  UNION
    select 10 , 1 , 4630404104378646528, 'b', 'aaa aaa', '/assets/products/b.jpg'  UNION
    select 11 , 1 , 4630474473122824192, 'c', 'aaa aaa', '/assets/products/c.jpg'  UNION
    select 12 , 1 , 4630544841867001856, 'd', 'aaa aaa', '/assets/products/d.jpg'  UNION
    select 13 , 1 , 4630615210611179520, 'e', 'aaa aaa', '/assets/products/e.jpg'  UNION
    select 14 , 1 , 4647785184190529536, 'f', 'aaa aaa', '/assets/products/f.jpg'  UNION
    select 15 , 1 , 4647855552934707200, 'g', 'aaa aaa', '/assets/products/g.jpg'  UNION
    select 16 , 1 , 4647925921678884864, 'h', 'aaa aaa', '/assets/products/h.jpg'  UNION
    select 17 , 1 , 4647996290423062528, 'i', 'aaa aaa', '/assets/products/i.jpg'  UNION
    select 18 , 1 , 4648066659167240192, 'j', 'aaa aaa', '/assets/products/j.jpg'  UNION
    select 19 , 1 , 4648137027911417856, 'k', 'aaa aaa', '/assets/products/k.jpg'  UNION
    select 20 , 1 , 4648137027911417856, 'l', 'aaa aaa', '/assets/products/l.jpg'  UNION
    select 21 , 1 , 4648207396655595520, 'm', 'aaa aaa', '/assets/products/m.jpg'  UNION
    select 22 , 1 , 4648277765399773184, 'n', 'aaa aaa', '/assets/products/n.jpg'  UNION
    select 23 , 1 , 4648348134143950848, 'o', 'aaa aaa', '/assets/products/o.jpg'  UNION
    select 24 , 1 , 4648418502888128512, 'p', 'aaa aaa', '/assets/products/p.jpg'  UNION
    select 25 , 1 , 4648488871632306176, 'q', 'aaa aaa', '/assets/products/q.jpg'  UNION
    select 26 , 1 , 4648559240376483840, 'r', 'aaa aaa', '/assets/products/r.jpg'  UNION
    select 27 , 1 , 4665799582700011520, 's', 'aaa aaa', '/assets/products/s.jpg'  UNION
    select 28 , 1 , 4665869951444189184, 't', 'aaa aaa', '/assets/products/t.jpg'  UNION
    select 29 , 1 , 4665940320188366848, 'u', 'aaa aaa', '/assets/products/u.jpg'  UNION
    select 30 , 1 , 4629771060558954496, 'v', 'aaa aaa', '/assets/products/v.jpg'  UNION
    select 31 , 1 , 4629771061632696320, 'w', 'aaa aaa', '/assets/products/w.jpg'  UNION
    select 32 , 1 , 4629771062706438144, 'x', 'aaa aaa', '/assets/products/x.jpg'  UNION
    select 33 , 1 , 4629771063780179968, 'y', 'aaa aaa', '/assets/products/y.jpg'  UNION
    select 34 , 1 , 4629771064853921792, 'z', 'aaa aaa', '/assets/products/z.jpg'
) where not exists(select * from PRODUCT);



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
    PRODUCT_ID bigint,
    SITE_ID bigint,
    PIANA_CATEGORY_ID bigint,
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
    constraint FK_purchase_invoice_PRODUCT_ID foreign key (product_id) references product(ID),
    constraint FK_purchase_invoice_store_pool_ID foreign key (store_pool_id) references store_pool(ID),
    constraint FK_purchase_invoice_site_ID foreign key (SITE_ID) references Site(ID),
    constraint FK_purchase_invoice_changer_id foreign key (buyer_id) references users(ID)
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
    constraint FK_sale_invoice_PRODUCT_ID foreign key (product_id) references product(ID),
    constraint FK_sale_invoice_store_pool_ID foreign key (store_pool_id) references store_pool(ID),
    constraint FK_sale_invoice_site_ID foreign key (SITE_ID) references Site(ID),
    constraint FK_sale_invoice_changer_id foreign key (seller_id) references users(ID)
);
