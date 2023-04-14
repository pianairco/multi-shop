create sequence IF NOT EXISTS master_seq;

CREATE TABLE IF NOT EXISTS PRODUCT_CATEGORIZATION (
    ID bigint primary key,
    TITLE varchar(256),
    ROUTER_LINK varchar(64),
    ORDERS number(3),
    SITE_ID bigint,
    constraint FK_PRODUCT_CATEGORIZATION_SITE_ID foreign key (SITE_ID) references SITE(ID),
    constraint UK_PRODUCT_CATEGORIZATION_TITLE unique (SITE_ID, TITLE),
    constraint UK_PRODUCT_CATEGORIZATION_ROUTER_LINK unique (SITE_ID, ROUTER_LINK),
    constraint UK_PRODUCT_CATEGORIZATION_ORDERS unique (SITE_ID, ORDERS)
);

INSERT INTO PRODUCT_CATEGORIZATION select * from (
    select 1 ID, 'گروه یک' TITLE, 'pack1' ROUTER_LINK, 1 ORDERS, 1 SITE_ID UNION
    select 2 ID, 'گروه دو' TITLE, 'pack2' ROUTER_LINK, 2 ORDERS, 1 SITE_ID UNION
    select 3 ID, 'گروه سه' TITLE, 'pack3' ROUTER_LINK, 3 ORDERS, 1 SITE_ID
) where not exists(select * from PRODUCT_CATEGORIZATION);

CREATE TABLE IF NOT EXISTS PRODUCT (
    ID bigint primary key,
    TITLE varchar(256),
    DESCRIPTION varchar(1024),
    IMAGE varchar(256),
    MEASUREMENT bigint,
    MEASUREMENT_UNIT varchar(64),
    PRICE bigint,
    CURRENCY varchar(64),
    PERCENT bigint,
    SITE_ID bigint,
    CATEGORY_ID bigint,
    constraint FK_PRODUCT_SITE_ID foreign key (SITE_ID) references SITE(ID),
    constraint FK_PRODUCT_CATEGORY_ID foreign key (CATEGORY_ID) references PRODUCT_CATEGORIZATION(ID),
    constraint UK_PRODUCT_TITLE unique (SITE_ID, TITLE)
);

CREATE TABLE IF NOT EXISTS header (
    ID bigint primary key,
    PATH varchar(128),
    ORDERS number(1),
    SITE_ID bigint,
    constraint FK_HEADER_SITE_ID foreign key (SITE_ID) references SITE(ID)
);

CREATE TABLE IF NOT EXISTS samples (
    id bigint default master_seq.nextval primary key,
    title char(128),
    description varchar(256) NOT NULL,
    image_src varchar(256),
    SITE_ID bigint,
    constraint FK_SAMPLES_SITE_ID foreign key (SITE_ID) references SITE(ID)
);

CREATE TABLE IF NOT EXISTS samples_session (
    id bigint default master_seq.nextval primary key,
    samples_id bigint,
    title char(128),
    description varchar(256),
    detail varchar(4096) ,
    icon_src varchar(256),
    orders int,
    SITE_ID bigint,
    constraint FK_SAMPLES_SESSION_SITE_ID foreign key (SITE_ID) references SITE(ID),
    CONSTRAINT FK_SESSION_TO_SAMPLES FOREIGN KEY ( samples_id ) REFERENCES samples( id )
);

CREATE TABLE IF NOT EXISTS samples_session_image (
    id bigint default master_seq.nextval primary key,
    samples_session_id bigint,
    orders int,
    image_src varchar(256),
    SITE_ID bigint,
    CONSTRAINT FK_SAMPLES_SESSION_IMAGE_SITE_ID foreign key (SITE_ID) references SITE(ID),
    CONSTRAINT FK_SESSION_IMAGE_TO_SAMPLES_SESSION FOREIGN KEY ( samples_session_id ) REFERENCES samples_session( id )
);

CREATE TABLE IF NOT EXISTS images (
    id bigint default master_seq.nextval primary key,
    image_src char(70),
    image_type char(10),
    image_data binary(100000),
    SITE_ID bigint,
    constraint FK_IMAGES_SITE_ID foreign key (SITE_ID) references SITE(ID)
);
