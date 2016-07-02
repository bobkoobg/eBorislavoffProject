--Data Definition Language (DDL)
DROP TABLE EMKO_FS_GALLERY_TBL;
DROP SEQUENCE EMKO_FS_GALLERY_ID_SEQ;
DROP TABLE EMKO_FLEXIBLE_SECTIONS_TBL;
DROP SEQUENCE EMKO_FLEXIBLE_SECTIONS_ID_SEQ;
DROP TABLE EMKO_GUESTBOOK_TBL;
DROP SEQUENCE EMKO_GUESTBOOK_ID_SEQ;
DROP TABLE EMKO_TICKETS_TBL;
DROP SEQUENCE EMKO_TICKETS_ID_SEQ;
DROP TABLE EMKO_TICKETTYPES_TBL;
DROP SEQUENCE EMKO_TICKETTYPES_ID_SEQ;
DROP TABLE EMKO_GALLERY_TBL;
DROP SEQUENCE EMKO_GALLERY_ID_SEQ;
DROP TABLE EMKO_ARTICLES_TBL;
DROP SEQUENCE EMKO_ARTICLES_ID_SEQ;
DROP TABLE EMKO_ARTICLETYPES_TBL;
DROP SEQUENCE EMKO_ARTICLETYPES_ID_SEQ;
DROP TABLE EMKO_USERS_TBL;
DROP SEQUENCE EMKO_USERS_ID_SEQ;

CREATE SEQUENCE EMKO_USERS_ID_SEQ
  MINVALUE 1
  MAXVALUE 99999999999999
  START WITH 1
  INCREMENT BY 1;

CREATE TABLE EMKO_USERS_TBL (
  user_id NUMBER NOT NULL PRIMARY KEY,
  username VARCHAR2(20) NOT NULL,
  hashpass VARCHAR2(255) NOT NULL,
  email VARCHAR2(255) NOT NULL,
  useralias VARCHAR2(255),
  lastlogindate Date,
  regDate date NOT NULL
);

CREATE SEQUENCE EMKO_ARTICLETYPES_ID_SEQ
  MINVALUE 1
  MAXVALUE 99999999999999
  START WITH 1
  INCREMENT BY 1;
  
CREATE TABLE EMKO_ARTICLETYPES_TBL (
  articletype_id NUMBER NOT NULL PRIMARY KEY,
  articletypename VARCHAR2(255) NOT NULL
);

CREATE SEQUENCE EMKO_ARTICLES_ID_SEQ
  MINVALUE 1
  MAXVALUE 99999999999999
  START WITH 1
  INCREMENT BY 1;

CREATE TABLE EMKO_ARTICLES_TBL (
  article_id NUMBER NOT NULL PRIMARY KEY,
  user_id NUMBER NOT NULL REFERENCES EMKO_USERS_TBL(user_id),
  title VARCHAR2(255) NOT NULL,
  type_id NUMBER NOT NULL REFERENCES EMKO_ARTICLETYPES_TBL(articletype_id),
  text VARCHAR2(4000) NOT NULL,
  creationdate date NOT NULL
);

CREATE SEQUENCE EMKO_GALLERY_ID_SEQ
  MINVALUE 1
  MAXVALUE 99999999999999
  START WITH 1
  INCREMENT BY 1;

CREATE TABLE EMKO_GALLERY_TBL (
  galery_id NUMBER NOT NULL PRIMARY KEY,
  user_id NUMBER NOT NULL REFERENCES EMKO_USERS_TBL(user_id),
  imagepath VARCHAR2(255) NOT NULL,
  creationdate date NOT NULL
);

CREATE SEQUENCE EMKO_TICKETTYPES_ID_SEQ
  MINVALUE 1
  MAXVALUE 99999999999999
  START WITH 1
  INCREMENT BY 1;
  
CREATE TABLE EMKO_TICKETTYPES_TBL (
  tickettype_id NUMBER NOT NULL PRIMARY KEY,
  tickettypename VARCHAR2(255) NOT NULL
);

CREATE SEQUENCE EMKO_TICKETS_ID_SEQ
  MINVALUE 1
  MAXVALUE 99999999999999
  START WITH 1
  INCREMENT BY 1;

CREATE TABLE EMKO_TICKETS_TBL (
  ticket_id NUMBER NOT NULL PRIMARY KEY,
  title VARCHAR2(255) NOT NULL,
  type_id NUMBER NOT NULL REFERENCES EMKO_TICKETTYPES_TBL(tickettype_id),
  message VARCHAR2(4000) NOT NULL,
  handler_id NUMBER NULL REFERENCES EMKO_USERS_TBL(user_id),
  status VARCHAR(30) NOT NULL,
  sender_email VARCHAR(255) NOT NULL,
  sender_name VARCHAR(255) NOT NULL,
  creationdate date NOT NULL
);

CREATE SEQUENCE EMKO_GUESTBOOK_ID_SEQ
  MINVALUE 1
  MAXVALUE 99999999999999
  START WITH 1
  INCREMENT BY 1;

CREATE TABLE EMKO_GUESTBOOK_TBL (
  gb_id NUMBER NOT NULL PRIMARY KEY,
  guestname VARCHAR2(255) NOT NULL,
  message VARCHAR2(4000) NOT NULL,
  imagepath VARCHAR2(255),
  ip VARCHAR(45),
  creationdate date NOT NULL
);

CREATE SEQUENCE EMKO_FLEXIBLE_SECTIONS_ID_SEQ
  MINVALUE 1
  MAXVALUE 99999999999999
  START WITH 1
  INCREMENT BY 1;

CREATE TABLE EMKO_FLEXIBLE_SECTIONS_TBL (
  fs_id NUMBER NOT NULL PRIMARY KEY,
  fs_purpose VARCHAR(30) NOT NULL,
  title VARCHAR2(255) NOT NULL,
  message VARCHAR2(4000) NOT NULL,
  user_id NUMBER NOT NULL REFERENCES EMKO_USERS_TBL(user_id),
  creationdate date NOT NULL
);

CREATE SEQUENCE EMKO_FS_GALLERY_ID_SEQ
  MINVALUE 1
  MAXVALUE 99999999999999
  START WITH 1
  INCREMENT BY 1;

CREATE TABLE EMKO_FS_GALLERY_TBL (
  fs_gallery_id NUMBER NOT NULL PRIMARY KEY,
  fs_id NUMBER NOT NULL REFERENCES EMKO_FLEXIBLE_SECTIONS_TBL(fs_id),
  user_id NUMBER NOT NULL REFERENCES EMKO_USERS_TBL(user_id),
  imagepath VARCHAR2(255) NOT NULL,
  creationdate date NOT NULL
);

--Data Manipulation Language (DML) 
INSERT INTO EMKO_USERS_TBL (USER_ID, USERNAME, HASHPASS, EMAIL, USERALIAS, LASTLOGINDATE, REGDATE) 
  VALUES (EMKO_USERS_ID_SEQ.nextval, 'bobkoo','qwerty12345','john@doe.dk', 'swaggerBoy', 
    TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'), 
    TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss' ) 
  );
  
COMMIT;