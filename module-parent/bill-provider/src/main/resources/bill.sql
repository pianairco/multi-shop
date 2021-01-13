create sequence IF NOT EXISTS master_seq;

CREATE TABLE IF NOT EXISTS bill_type (
    ID bigint default master_seq.nextval primary key,
    name varchar(32)
);

insert into bill_type select * from (
    select master_seq.nextval id, 'cart' name union
    select master_seq.nextval, 'charge'
) where not exists(select * from bill_type);

CREATE TABLE IF NOT EXISTS bill (
    ID bigint default master_seq.nextval primary key,
    bill_number varchar(36) not null,
    bill_type_id number not null,
    price number not null,
    reference_id char(36) not null,
    user_number char(36) not null,
    mobile char(11),
    email varchar(256),
    description varchar(256),
    creation_date char(10),
    creation_time char(8),
    payment_date char(10),
    payment_time char(8),
    is_payed number(1) default 0,
    foreign key (bill_type_id) references bill_type(id)
);
