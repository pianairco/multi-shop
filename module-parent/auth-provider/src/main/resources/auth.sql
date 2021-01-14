create sequence IF NOT EXISTS master_seq;

CREATE TABLE IF NOT EXISTS users (
  id bigint default master_seq.nextval primary key,
  user_id char(36),
  email varchar(256) NOT NULL,
  mobile char(11),
  password char(128),
  email_verified number(1),
  name varchar(64),
  picture_url varchar(256),
  locale char(2),
  family_name varchar(64),
  given_name varchar(64)
);

INSERT INTO users select * from (
    master_seq.nextval id, 'admin' user_id, 'admin' email, 1 email_verified, '$2a$10$kcXK1Vjmy79dMr.T7j5AJuWAlrGTqKWu/dk7kPFYESJGHqdCdO4.K' password union
    master_seq.nextval, 'manager', 'manager', 1, '$2a$10$kcXK1Vjmy79dMr.T7j5AJuWAlrGTqKWu/dk7kPFYESJGHqdCdO4.K'
) where not exists(select * from users);
