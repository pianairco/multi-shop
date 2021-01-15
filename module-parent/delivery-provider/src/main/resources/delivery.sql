create sequence IF NOT EXISTS master_seq;

CREATE TABLE IF NOT EXISTS zarinpal_account (
    ID bigint default master_seq.nextval primary key,
    TENANT_ID varchar(256) not null,
    MERCHANT_ID varchar(256) not null
);
