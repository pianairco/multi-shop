create sequence IF NOT EXISTS master_seq;

CREATE TABLE IF NOT EXISTS currency_unit (
    ID bigint primary key,
    parent_id bigint,
    is_primary number(1) default (0),
    english_name varchar(128),
    persian_name varchar(128),
    ratio number(12, 8)
);

INSERT INTO currency_unit select * from (
    select 1 ID, 0 parent_id, 1 is_primary, 'Rial' english_name, 'ریال' persian_name, 1 ratio UNION
    select 2, 1, 0, 'Toman', 'تومان', 10 UNION
    select 3, 1, 1, 'Dollar', 'دلار', 1
) where not exists(select * from currency_unit);

CREATE TABLE IF NOT EXISTS measurement_unit (
    ID bigint primary key,
    parent_id bigint,
    is_primary number(1) default (0),
    english_name varchar(128),
    persian_name varchar(128),
    ratio number(12, 8)
);

INSERT INTO measurement_unit select * from (
    select 1 ID, 0 parent_id, 1 is_primary, 'kilogram' english_name, 'کیلوگرم' persian_name, 1 ratio UNION
    select 2, 1, 0, 'gram', 'گرم', 0.001 UNION
    select 3, 1, 0, 'Shekel', 'مثقال', 0.004608 UNION
    select 4, 1, 0, 'ton', 'تن', 1000 UNION
    select 5, 1, 0, 'sir', 'سیر', 0.075 UNION
    select 6, 1, 0, 'sot', 'سوت', 0.000001 UNION
    select 7, 0, 1, 'meter', 'متر', 1 UNION
    select 8, 7, 0, 'centimeter', 'سانتیمتر', 0.01 UNION
    select 9, 7, 0, 'Inch', 'اینچ', 0.0254 UNION
    select 10, 7, 0, 'kilometer', 'کیلومتر', 1000 UNION
    select 11, 7, 0, 'militimeter', 'میلیمتر', 0.001 UNION
    select 12, 7, 0, 'gaz', 'گز', 1.0400000017 UNION
    select 13, 7, 0, 'zar', 'ذرع', 1.0400000017 UNION
    select 14, 7, 0, 'vajab', 'وجب', 0.2250000002 UNION
    select 15, 7, 0, 'yard', 'یارد', 0.9144 UNION
    select 16, 7, 0, 'mile', 'مایل', 1609.3439798948 UNION
    select 17, 0, 1, 'liter', 'لیتر', 1 UNION
    select 18, 17, 0, 'cc', 'سی سی', 0.001 UNION
    select 19, 17, 0, 'cubic-meter', 'متر مکعب', 1000 UNION
    select 20, 17, 0, 'mililiter', 'میلی لیتر', 0.001 UNION
    select 21, 0, 1, 'quantity', 'عدد', 1
) where not exists(select * from measurement_unit);

CREATE TABLE IF NOT EXISTS PRODUCT (
    ID bigint primary key,
    registrar_site_ID bigint,
    piana_CATEGORY_ID bigint,
    measurement_unit_id bigint,
    TITLE varchar(256),
    DESCRIPTION varchar(1024),
    IMAGE varchar(256),
    is_confirmed number(1) default 0,
    register_time timestamp default CURRENT_TIMESTAMP,
    constraint FK_PRODUCT_registrar_ID foreign key (registrar_site_ID) references site(ID),
    constraint FK_PRODUCT_piana_CATEGORY_ID foreign key (piana_CATEGORY_ID) references piana_CATEGORIes(ID),
    constraint FK_PRODUCT_measurement_unit_id foreign key (measurement_unit_id) references measurement_unit(ID)
);

INSERT INTO PRODUCT (id, registrar_site_ID, PIANA_CATEGORY_ID, measurement_unit_id, title, description, image) select * from (
    select 1 ID, 1 registrar_site_ID, 4629770785681047552 PIANA_CATEGORY_ID, 21 measurement_unit_id, 'a' TITLE, 'aaa aaa' description, '/assets/products/a.jpg' image UNION
    select 2 , 1 , 4629841154425225216, 21, 'a1', 'aaa aaa', '/assets/products/a1.jpg' UNION
    select 3 , 1 , 4629911523169402880, 21, 'a2', 'aaa aaa', '/assets/products/a2.jpg' UNION
    select 4 , 1 , 4629981891913580544, 21, 'a3', 'aaa aaa', '/assets/products/a3.jpg'  UNION
    select 5 , 1 , 4630052260657758208, 21, 'a4', 'aaa aaa', '/assets/products/a4.jpg'  UNION
    select 6 , 1 , 4630122629401935872, 21, 'a5', 'aaa aaa', '/assets/products/a5.jpg'  UNION
    select 7 , 1 , 4630192998146113536, 21, 'a6', 'aaa aaa', '/assets/products/a6.jpg'  UNION
    select 8 , 1 , 4630263366890291200, 21, 'a7', 'aaa aaa', '/assets/products/a7.jpg'  UNION
    select 9 , 1 , 4630333735634468864, 21, 'a8', 'aaa aaa', '/assets/products/a8.jpg'  UNION
    select 10 , 1 , 4630404104378646528, 21, 'b', 'aaa aaa', '/assets/products/b.jpg'  UNION
    select 11 , 1 , 4630474473122824192, 21, 'c', 'aaa aaa', '/assets/products/c.jpg'  UNION
    select 12 , 1 , 4630544841867001856, 21, 'd', 'aaa aaa', '/assets/products/d.jpg'  UNION
    select 13 , 1 , 4630615210611179520, 21, 'e', 'aaa aaa', '/assets/products/e.jpg'  UNION
    select 14 , 1 , 4647785184190529536, 21, 'f', 'aaa aaa', '/assets/products/f.jpg'  UNION
    select 15 , 1 , 4647855552934707200, 21, 'g', 'aaa aaa', '/assets/products/g.jpg'  UNION
    select 16 , 1 , 4647925921678884864, 21, 'h', 'aaa aaa', '/assets/products/h.jpg'  UNION
    select 17 , 1 , 4647996290423062528, 21, 'i', 'aaa aaa', '/assets/products/i.jpg'  UNION
    select 18 , 1 , 4648066659167240192, 21, 'j', 'aaa aaa', '/assets/products/j.jpg'  UNION
    select 19 , 1 , 4648137027911417856, 21, 'k', 'aaa aaa', '/assets/products/k.jpg'  UNION
    select 20 , 1 , 4648137027911417856, 21, 'l', 'aaa aaa', '/assets/products/l.jpg'  UNION
    select 21 , 1 , 4648207396655595520, 21, 'm', 'aaa aaa', '/assets/products/m.jpg'  UNION
    select 22 , 1 , 4648277765399773184, 21, 'n', 'aaa aaa', '/assets/products/n.jpg'  UNION
    select 23 , 1 , 4648348134143950848, 21, 'o', 'aaa aaa', '/assets/products/o.jpg'  UNION
    select 24 , 1 , 4648418502888128512, 21, 'p', 'aaa aaa', '/assets/products/p.jpg'  UNION
    select 25 , 1 , 4648488871632306176, 21, 'q', 'aaa aaa', '/assets/products/q.jpg'  UNION
    select 26 , 1 , 4648559240376483840, 21, 'r', 'aaa aaa', '/assets/products/r.jpg'  UNION
    select 27 , 1 , 4665799582700011520, 21, 's', 'aaa aaa', '/assets/products/s.jpg'  UNION
    select 28 , 1 , 4665869951444189184, 21, 't', 'aaa aaa', '/assets/products/t.jpg'  UNION
    select 29 , 1 , 4665940320188366848, 21, 'u', 'aaa aaa', '/assets/products/u.jpg'  UNION
    select 30 , 1 , 4629771060558954496, 21, 'v', 'aaa aaa', '/assets/products/v.jpg'  UNION
    select 31 , 1 , 4629771061632696320, 21, 'w', 'aaa aaa', '/assets/products/w.jpg'  UNION
    select 32 , 1 , 4629771062706438144, 21, 'x', 'aaa aaa', '/assets/products/x.jpg'  UNION
    select 33 , 1 , 4629771063780179968, 21, 'y', 'aaa aaa', '/assets/products/y.jpg'  UNION
    select 34 , 1 , 4629771064853921792, 21, 'z', 'aaa aaa', '/assets/products/z.jpg'
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
    select 1 ID, 'گروه یک' TITLE, 'pack1' ROUTER_LINK, 1 ORDERS, 1 SITE_ID, 4629770785681047552 PIANA_CATEGORY_ID UNION
    select 2 ID, 'گروه دو' TITLE, 'pack2' ROUTER_LINK, 2 ORDERS, 1 SITE_ID, 4629700416936869888 UNION
    select 3 ID, 'گروه سه' TITLE, 'pack3' ROUTER_LINK, 3 ORDERS, 1 SITE_ID, 4647714815446351872
) where not exists(select * from PRODUCT_CATEGORIZATION);

CREATE TABLE IF NOT EXISTS repository (
    ID bigint primary key,
    PRODUCT_ID bigint,
    SITE_ID bigint,
    PIANA_CATEGORY_ID bigint,
    CATEGORY_ID bigint,
    MEASUREMENT_UNIT_id bigint,
    TITLE varchar(256),
    DESCRIPTION varchar(1024),
    IMAGE varchar(256),
    amount bigint,
    change_time timestamp default CURRENT_TIMESTAMP,
    constraint FK_repository_PRODUCT_ID foreign key (product_id) references product(ID),
    constraint FK_repository_SITE_ID foreign key (SITE_ID) references SITE(ID),
    constraint FK_repository_piana_CATEGORY_ID foreign key (PIANA_CATEGORY_ID) references PIANA_CATEGORIES(ID),
    constraint FK_repository_CATEGORY_ID foreign key (CATEGORY_ID) references PRODUCT_CATEGORIZATION(ID),
    constraint FK_repository_measurement_unit_id foreign key (measurement_unit_id) references measurement_unit(ID)
);

INSERT INTO repository (id, PRODUCT_ID, SITE_ID, PIANA_CATEGORY_ID, CATEGORY_ID, measurement_unit_id, title, description, image, amount) select * from (
    select 1 ID, 1 PRODUCT_ID, 1 site_ID, 4629770785681047552 PIANA_CATEGORY_ID, 2 category_id, 21 measurement_unit_id, 'a' TITLE, 'aaa aaa' description, '/assets/products/a.jpg' image, 100 amount UNION
    select 2 , 2, 1 , 4629841154425225216, 2, 21, 'a1', 'aaa aaa', '/assets/products/a1.jpg', 100 UNION
    select 3 , 3, 1 , 4629911523169402880, 2, 21, 'a2', 'aaa aaa', '/assets/products/a2.jpg', 100 UNION
    select 4 , 4, 1 , 4629981891913580544, 2, 21, 'a3', 'aaa aaa', '/assets/products/a3.jpg', 100 UNION
    select 5 , 5, 1 , 4630052260657758208, 2, 21, 'a4', 'aaa aaa', '/assets/products/a4.jpg', 100 UNION
    select 6 , 6, 1 , 4630122629401935872, 2, 21, 'a5', 'aaa aaa', '/assets/products/a5.jpg', 100 UNION
    select 7 , 7, 1 , 4630192998146113536, 2, 21, 'a6', 'aaa aaa', '/assets/products/a6.jpg', 100 UNION
    select 8 , 8, 1 , 4630263366890291200, 2, 21, 'a7', 'aaa aaa', '/assets/products/a7.jpg', 100 UNION
    select 9 , 9, 1 , 4630333735634468864, 2, 21, 'a8', 'aaa aaa', '/assets/products/a8.jpg', 100 UNION
    select 10 , 10, 1 , 4630404104378646528, 2, 21, 'b', 'aaa aaa', '/assets/products/b.jpg', 100 UNION
    select 11 , 11, 1 , 4630474473122824192, 2, 21, 'c', 'aaa aaa', '/assets/products/c.jpg', 100 UNION
    select 12 , 12, 1 , 4630544841867001856, 2, 21, 'd', 'aaa aaa', '/assets/products/d.jpg', 100 UNION
    select 13 , 13, 1 , 4630615210611179520, 2,  21, 'e', 'aaa aaa', '/assets/products/e.jpg', 100 UNION
    select 14 , 14, 1 , 4647785184190529536, 3,  21, 'f', 'aaa aaa', '/assets/products/f.jpg', 100 UNION
    select 15 , 15, 1 , 4647855552934707200, 3,  21, 'g', 'aaa aaa', '/assets/products/g.jpg', 100 UNION
    select 16 , 16, 1 , 4647925921678884864, 3,  21, 'h', 'aaa aaa', '/assets/products/h.jpg', 100 UNION
    select 17 , 17, 1 , 4647996290423062528, 3,  21, 'i', 'aaa aaa', '/assets/products/i.jpg', 100 UNION
    select 18 , 18, 1 , 4648066659167240192, 3,  21, 'j', 'aaa aaa', '/assets/products/j.jpg', 100 UNION
    select 19 , 19, 1 , 4648137027911417856, 3,  21, 'k', 'aaa aaa', '/assets/products/k.jpg', 100 UNION
    select 20 , 20, 1 , 4648137027911417856, 3,  21, 'l', 'aaa aaa', '/assets/products/l.jpg', 100 UNION
    select 21 , 21, 1 , 4648207396655595520, 3,  21, 'm', 'aaa aaa', '/assets/products/m.jpg', 100 UNION
    select 22 , 22, 1 , 4648277765399773184, 3,  21, 'n', 'aaa aaa', '/assets/products/n.jpg', 100 UNION
    select 23 , 23, 1 , 4648348134143950848, 3,  21, 'o', 'aaa aaa', '/assets/products/o.jpg', 100 UNION
    select 24 , 24, 1 , 4648418502888128512, 3,  21, 'p', 'aaa aaa', '/assets/products/p.jpg', 100 UNION
    select 25 , 25, 1 , 4648488871632306176, 3,  21, 'q', 'aaa aaa', '/assets/products/q.jpg', 100 UNION
    select 26 , 26, 1 , 4648559240376483840, 3,  21, 'r', 'aaa aaa', '/assets/products/r.jpg', 100 UNION
    select 27 , 27, 1 , 4665799582700011520, 1,  21, 's', 'aaa aaa', '/assets/products/s.jpg', 100 UNION
    select 28 , 28, 1 , 4665869951444189184, 1,  21, 't', 'aaa aaa', '/assets/products/t.jpg', 100 UNION
    select 29 , 29, 1 , 4665940320188366848, 1,  21, 'u', 'aaa aaa', '/assets/products/u.jpg', 100 UNION
    select 30 , 30, 1 , 4629771060558954496, 2,  21, 'v', 'aaa aaa', '/assets/products/v.jpg', 100 UNION
    select 31 , 31, 1 , 4629771061632696320, 2,  21, 'w', 'aaa aaa', '/assets/products/w.jpg', 100 UNION
    select 32 , 32, 1 , 4629771062706438144, 2,  21, 'x', 'aaa aaa', '/assets/products/x.jpg', 100 UNION
    select 33 , 33, 1 , 4629771063780179968, 2,  21, 'y', 'aaa aaa', '/assets/products/y.jpg', 100 UNION
    select 34 , 34, 1 , 4629771064853921792, 2,  21, 'z', 'aaa aaa', '/assets/products/z.jpg', 100
) where not exists(select * from repository);

CREATE TABLE IF NOT EXISTS store_pool (
    ID bigint primary key,
    PRODUCT_ID bigint,
    SITE_ID bigint,
    PIANA_CATEGORY_ID bigint,
    CATEGORY_ID bigint,
    MEASUREMENT_UNIT_id bigint,
    MEASUREMENT_UNIT_NAME varchar(128),
    MEASUREMENT_UNIT_RATIO number(12, 8),
    currency_unit_id bigint,
    TITLE varchar(256),
    DESCRIPTION varchar(1024),
    IMAGE varchar(256),
    amount bigint,
    PRICE bigint,
    PERCENTAGE int,
    available number(1) default 1,
    constraint FK_store_pool_PRODUCT_ID foreign key (product_id) references product(ID),
    constraint FK_store_pool_SITE_ID foreign key (SITE_ID) references SITE(ID),
    constraint FK_store_pool_piana_CATEGORY_ID foreign key (PIANA_CATEGORY_ID) references PIANA_CATEGORIES(ID),
    constraint FK_store_pool_CATEGORY_ID foreign key (CATEGORY_ID) references PRODUCT_CATEGORIZATION(ID),
    constraint FK_store_pool_measurement_unit_id foreign key (measurement_unit_id) references measurement_unit(ID),
    constraint FK_store_pool_currency_unit_id foreign key (currency_unit_id) references currency_unit(ID),
    constraint UK_store_pool_TITLE_measurement unique (SITE_ID, TITLE, MEASUREMENT_UNIT_id)
);

INSERT INTO store_pool (id, PRODUCT_ID, SITE_ID, PIANA_CATEGORY_ID, CATEGORY_ID, measurement_unit_id, MEASUREMENT_UNIT_NAME, MEASUREMENT_UNIT_RATIO, currency_unit_id, title, description, image, amount, PRICE, PERCENTAGE) select * from (
    select 1 ID, 1 PRODUCT_ID, 1 site_ID, 4629770785681047552 PIANA_CATEGORY_ID, 1 category_id, 21 measurement_unit_id, 'عدد' MEASUREMENT_UNIT_NAME, 1 MEASUREMENT_UNIT_RATIO, 2 currency_unit_id, 'a' TITLE, 'aaa aaa' description, '/assets/products/a.jpg' image, 100 amount, 10000 price, 0 percentage UNION
    select 2 , 2, 1 , 4629841154425225216, 2,  21, 'عدد', 1, 2, 'a1', 'aaa aaa', '/assets/products/a1.jpg', 100, 10000, 0 UNION
    select 3 , 3, 1 , 4629911523169402880, 2,  21, 'عدد', 1, 2, 'a2', 'aaa aaa', '/assets/products/a2.jpg', 100, 10000, 0 UNION
    select 4 , 4, 1 , 4629981891913580544, 2,  21, 'عدد', 1, 2, 'a3', 'aaa aaa', '/assets/products/a3.jpg', 100, 10000, 0 UNION
    select 5 , 5, 1 , 4630052260657758208, 2,  21, 'عدد', 1, 2, 'a4', 'aaa aaa', '/assets/products/a4.jpg', 100, 10000, 0 UNION
    select 6 , 6, 1 , 4630122629401935872, 2,  21, 'عدد', 1, 2, 'a5', 'aaa aaa', '/assets/products/a5.jpg', 100, 10000, 0 UNION
    select 7 , 7, 1 , 4630192998146113536, 2,  21, 'عدد', 1, 2, 'a6', 'aaa aaa', '/assets/products/a6.jpg', 100, 10000, 0 UNION
    select 8 , 8, 1 , 4630263366890291200, 2,  21, 'عدد', 1, 2, 'a7', 'aaa aaa', '/assets/products/a7.jpg', 100, 10000, 0 UNION
    select 9 , 9, 1 , 4630333735634468864, 2,  21, 'عدد', 1, 2, 'a8', 'aaa aaa', '/assets/products/a8.jpg', 100, 10000, 0 UNION
    select 10 , 10, 1 , 4630404104378646528, 2,  21, 'عدد', 1, 2, 'b', 'aaa aaa', '/assets/products/b.jpg', 100, 10000, 0 UNION
    select 11 , 11, 1 , 4630474473122824192, 2,  21, 'عدد', 1, 2, 'c', 'aaa aaa', '/assets/products/c.jpg', 100, 10000, 0 UNION
    select 12 , 12, 1 , 4630544841867001856, 2,  21, 'عدد', 1, 2, 'd', 'aaa aaa', '/assets/products/d.jpg', 100, 10000, 0 UNION
    select 13 , 13, 1 , 4630615210611179520, 2,  21, 'عدد', 1, 2, 'e', 'aaa aaa', '/assets/products/e.jpg', 100, 10000, 0 UNION
    select 14 , 14, 1 , 4647785184190529536, 3,  21, 'عدد', 1, 2, 'f', 'aaa aaa', '/assets/products/f.jpg', 100, 10000, 0 UNION
    select 15 , 15, 1 , 4647855552934707200, 3,  21, 'عدد', 1, 2, 'g', 'aaa aaa', '/assets/products/g.jpg', 100, 10000, 0 UNION
    select 16 , 16, 1 , 4647925921678884864, 3,  21, 'عدد', 1, 2, 'h', 'aaa aaa', '/assets/products/h.jpg', 100, 10000, 0 UNION
    select 17 , 17, 1 , 4647996290423062528, 3,  21, 'عدد', 1, 2, 'i', 'aaa aaa', '/assets/products/i.jpg', 100, 10000, 0 UNION
    select 18 , 18, 1 , 4648066659167240192, 3,  21, 'عدد', 1, 2, 'j', 'aaa aaa', '/assets/products/j.jpg', 100, 10000, 0 UNION
    select 19 , 19, 1 , 4648137027911417856, 3,  21, 'عدد', 1, 2, 'k', 'aaa aaa', '/assets/products/k.jpg', 100, 10000, 0 UNION
    select 20 , 20, 1 , 4648137027911417856, 3,  21, 'عدد', 1, 2, 'l', 'aaa aaa', '/assets/products/l.jpg', 100, 10000, 0 UNION
    select 21 , 21, 1 , 4648207396655595520, 3,  21, 'عدد', 1, 2, 'm', 'aaa aaa', '/assets/products/m.jpg', 100, 10000, 0 UNION
    select 22 , 22, 1 , 4648277765399773184, 3,  21, 'عدد', 1, 2, 'n', 'aaa aaa', '/assets/products/n.jpg', 100, 10000, 0 UNION
    select 23 , 23, 1 , 4648348134143950848, 3,  21, 'عدد', 1, 2, 'o', 'aaa aaa', '/assets/products/o.jpg', 100, 10000, 0 UNION
    select 24 , 24, 1 , 4648418502888128512, 3,  21, 'عدد', 1, 2, 'p', 'aaa aaa', '/assets/products/p.jpg', 100, 10000, 0 UNION
    select 25 , 25, 1 , 4648488871632306176, 3,  21, 'عدد', 1, 2, 'q', 'aaa aaa', '/assets/products/q.jpg', 100, 10000, 0 UNION
    select 26 , 26, 1 , 4648559240376483840, 3,  21, 'عدد', 1, 2, 'r', 'aaa aaa', '/assets/products/r.jpg', 100, 10000, 0 UNION
    select 27 , 27, 1 , 4665799582700011520, 1,  21, 'عدد', 1, 2, 's', 'aaa aaa', '/assets/products/s.jpg', 100, 10000, 0 UNION
    select 28 , 28, 1 , 4665869951444189184, 1,  21, 'عدد', 1, 2, 't', 'aaa aaa', '/assets/products/t.jpg', 100, 10000, 0 UNION
    select 29 , 29, 1 , 4665940320188366848, 1,  21, 'عدد', 1, 2, 'u', 'aaa aaa', '/assets/products/u.jpg', 100, 10000, 0 UNION
    select 30 , 30, 1 , 4629771060558954496, 2,  21, 'عدد', 1, 2, 'v', 'aaa aaa', '/assets/products/v.jpg', 100, 10000, 0 UNION
    select 31 , 31, 1 , 4629771061632696320, 2,  21, 'عدد', 1, 2, 'w', 'aaa aaa', '/assets/products/w.jpg', 100, 10000, 0 UNION
    select 32 , 32, 1 , 4629771062706438144, 2,  21, 'عدد', 1, 2, 'x', 'aaa aaa', '/assets/products/x.jpg', 100, 10000, 0 UNION
    select 33 , 33, 1 , 4629771063780179968, 2,  21, 'عدد', 1, 2, 'y', 'aaa aaa', '/assets/products/y.jpg', 100, 10000, 0 UNION
    select 34 , 34, 1 , 4629771064853921792, 2,  21, 'عدد', 1, 2, 'z', 'aaa aaa', '/assets/products/z.jpg', 100, 10000, 0
) where not exists(select * from store_pool);

CREATE TABLE IF NOT EXISTS sale_price (
    ID bigint primary key,
    product_ID bigint,
    store_pool_ID bigint,
    SITE_ID bigint,
    changer_id bigint,
    change_time timestamp,
    currency_unit_id bigint,
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
    MEASUREMENT_UNIT_id bigint,
    currency_unit_id bigint,
    unit_price bigint,
    total_price bigint,
    constraint FK_purchase_invoice_PRODUCT_ID foreign key (product_id) references product(ID),
    constraint FK_purchase_invoice_store_pool_ID foreign key (store_pool_id) references store_pool(ID),
    constraint FK_purchase_invoice_site_ID foreign key (SITE_ID) references Site(ID),
    constraint FK_purchase_invoice_changer_id foreign key (buyer_id) references users(ID),
    constraint FK_purchase_invoice_currency_unit_id foreign key (currency_unit_id) references currency_unit(ID),
    constraint FK_purchase_invoice_measurement_unit_id foreign key (measurement_unit_id) references measurement_unit(ID)
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
    MEASUREMENT_UNIT_id bigint,
    currency_unit_id bigint,
    unit_price bigint,
    total_price bigint,
    constraint FK_sale_invoice_PRODUCT_ID foreign key (product_id) references product(ID),
    constraint FK_sale_invoice_store_pool_ID foreign key (store_pool_id) references store_pool(ID),
    constraint FK_sale_invoice_site_ID foreign key (SITE_ID) references Site(ID),
    constraint FK_sale_invoice_changer_id foreign key (seller_id) references users(ID),
    constraint FK_sale_invoice_currency_unit_id foreign key (currency_unit_id) references currency_unit(ID),
    constraint FK_sale_invoice_measurement_unit_id foreign key (measurement_unit_id) references measurement_unit(ID)
);
