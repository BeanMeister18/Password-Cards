CREATE TABLE `CREDENTIAL` (
  id integer NOT NULL AUTO_INCREMENT,
  website varchar(45) NOT NULL,
  username varchar(16) NOT NULL,
  password varchar(45) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO CREDENTIAL (id, website, username, password) VALUES (1, 'site-1', 'user-1', 'psw-1');
INSERT INTO CREDENTIAL (id, website, username, password) VALUES (2, 'site-2', 'user-2', 'psw-2');
INSERT INTO CREDENTIAL (id, website, username, password) VALUES (3, 'site-3', 'user-3', 'psw-3');