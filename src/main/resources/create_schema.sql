CREATE TABLE users (id bigint(20) NOT NULL AUTO_INCREMENT, enabled bit(1) NOT NULL, password varchar(255) NOT NULL,  username varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_r43af9ap4edm43mmtq01oddj6 (username)
);

CREATE TABLE entry_types (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  main_type varchar(255) NOT NULL,
  sub_type varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UKcdktk5yreychkb3tu57jxnb3m (main_type,sub_type)
) ;

CREATE TABLE entries (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  amount float NOT NULL,
  create_date date DEFAULT NULL,
  description varchar(255) NOT NULL,
  fk_user bigint(20) DEFAULT NULL,
  fk_type bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK4bm28spy6m4ne1jcdp32j1ctx FOREIGN KEY (fk_user) REFERENCES users (id),
  CONSTRAINT FKrhbgqyy07mqkp9fcrolpnnhu8 FOREIGN KEY (fk_type) REFERENCES entry_types (id)
);
