--Data Definition Language (DDL)
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
  creationdate date NOT NULL
);

--Data Manipulation Language (DML) 
INSERT INTO EMKO_USERS_TBL (USER_ID, USERNAME, HASHPASS, EMAIL, USERALIAS, LASTLOGINDATE, REGDATE) 
  VALUES (EMKO_USERS_ID_SEQ.nextval, 'bobkoo','qwerty12345','john@doe.dk', 'swaggerBoy', 
    TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'), 
    TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss' ) 
  );
  
INSERT INTO EMKO_USERS_TBL (USER_ID, USERNAME, HASHPASS, EMAIL, USERALIAS, LASTLOGINDATE, REGDATE) 
  VALUES (EMKO_USERS_ID_SEQ.nextval, 'emko','barona','john@doe.dk', 'Pedja Medenica', 
    TO_DATE('2004/05/03 22:02:44', 'yyyy/mm/dd hh24:mi:ss'), 
    TO_DATE('2004/05/03 22:02:44', 'yyyy/mm/dd hh24:mi:ss' ) 
  );
  
INSERT INTO EMKO_ARTICLETYPES_TBL( ARTICLETYPE_ID, ARTICLETYPENAME)
 VALUES ( EMKO_ARTICLETYPES_ID_SEQ.nextval, 'News' );

INSERT INTO EMKO_ARTICLETYPES_TBL( ARTICLETYPE_ID, ARTICLETYPENAME)
  VALUES ( EMKO_ARTICLETYPES_ID_SEQ.nextval, 'Exercises' );

INSERT INTO EMKO_ARTICLES_TBL(ARTICLE_ID,USER_ID,TITLE, TYPE_ID, TEXT, CREATIONDATE)
 VALUES( EMKO_ARTICLES_ID_SEQ.nextval, 1, 'Lorem ipsum', 1, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc,  Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc,', 
  TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss' ) 
 );
 
 INSERT INTO EMKO_ARTICLES_TBL(ARTICLE_ID,USER_ID,TITLE, TYPE_ID, TEXT, CREATIONDATE)
  VALUES( EMKO_ARTICLES_ID_SEQ.nextval, 1, 'Cicero (en)', 2, 'But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure? On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure? On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure? On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee', 
  TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss' ) 
 );
 
  INSERT INTO EMKO_ARTICLES_TBL(ARTICLE_ID,USER_ID,TITLE, TYPE_ID, TEXT, CREATIONDATE)
 VALUES( EMKO_ARTICLES_ID_SEQ.nextval, 2, 'Cicero', 1, 'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur? At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur? At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere', 
  TO_DATE('2003/05/03 21:02:55', 'yyyy/mm/dd hh24:mi:ss' ) 
 );
 
  INSERT INTO EMKO_ARTICLES_TBL(ARTICLE_ID,USER_ID,TITLE, TYPE_ID, TEXT, CREATIONDATE)
  VALUES( EMKO_ARTICLES_ID_SEQ.nextval, 2, 'Li Europan lingues ', 2, 'Li Europan lingues es membres del sam familie. Lor separat existentie es un myth. Por scientie, musica, sport etc, litot Europa usa li sam vocabular. Li lingues differe solmen in li grammatica, li pronunciation e li plu commun vocabules. Omnicos directe al desirabilite de un nov lingua franca: On refusa continuar payar custosi traductores. At solmen va esser necessi far uniform grammatica, pronunciation e plu sommun paroles. Ma quande lingues coalesce, li grammatica del resultant lingue es plu simplic e regulari quam ti del coalescent lingues. Li nov lingua franca va esser plu simplic e regulari quam li existent Europan lingues. It va esser tam simplic quam Occidental in fact, it va esser Occidental. A un Angleso it va semblar un simplificat Angles, quam un skeptic Cambridge amico dit me que Occidental es.Li Europan lingues es membres del sam familie. Lor separat existentie es un myth. Por scientie, musica, sport etc, litot Europa usa li sam vocabular. Li lingues differe solmen in li grammatica, li pronunciation e li plu commun vocabules. Omnicos directe al desirabilite de un nov lingua franca: On refusa continuar payar custosi traductores. At solmen va esser necessi far uniform grammatica, pronunciation e plu sommun paroles. Li Europan lingues es membres del sam familie. Lor separat existentie es un myth. Por scientie, musica, sport etc, litot Europa usa li sam vocabular. Li lingues differe solmen in li grammatica, li pronunciation e li plu commun vocabules. Omnicos directe al desirabilite de un nov lingua franca: On refusa continuar payar custosi traductores. At solmen va esser necessi far uniform grammatica, pronunciation e plu sommun paroles. Ma quande lingues coalesce, li grammatica del resultant lingue es plu simplic e regulari quam ti del coalescent lingues. Li nov lingua franca va esser plu simplic e regulari quam li existent Europan lingues. It va esser tam simplic quam Occidental in fact, it va esser Occidental. A un Angleso it va semblar un simplificat Angles, quam un skeptic Cambridge amico dit me que Occidental es.Li Europan lingues es membres del sam familie. Lor separat existentie es un myth. Por scientie, musica, sport etc, litot Europa usa li sam vocabular. Li lingues differe solmen in li grammatica, li pronunciation e li plu commun vocabules. Omnicos directe al desirabilite de un nov lingua franca: On refusa continuar payar custosi traductores. At solmen va esser necessi far uniform grammatica, pronunciation e plu sommun paroles. Li Europan lingues es membres del sam familie. Lor separat existentie es un myth. Por scientie, musica, sport etc, litot Europa usa li sam vocabular. Li lingues differe solmen in li grammatica, li pronunciation e li plu commun vocabules. Omnicos directe al desirabilite de un nov lingua franca: On refusa continuar payar custosi traductores. At solmen va esser necessi far uniform grammatica, pronunciation e plu sommun paroles. Ma quande lingues coalesce, li grammatica del resultant lingue es plu simplic e regulari quam ti del coalescent lingues. Li nov lingua franca va esser plu simplic e regulari quam li existent Europan lingues. It va esser tam simplic quam Occidental in fact, it va esser Occidental. A un Angleso it va semblar un simplificat Angles, quam un skeptic Cambridge amico dit me que Occidental es.Li Europan lingues es membres del sam familie. Lor separat existentie es un myth. Por scientie, musica, sport etc, litot Europa usa li sam vocabular. Li lingues differe solmen in li grammatica, li pronunciation e li plu commun vocabules. Omnicos directe al desirabilite de un nov lingua franca: On refusa continuar payar custosi traductores. At solmen va esser necessi far uniform grammatica, pronunciation e plu sommun paroles.', 
  TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss' ) 
 );
 
 INSERT INTO EMKO_GALLERY_TBL(GALERY_ID, USER_ID, IMAGEPATH, CREATIONDATE)
  VALUES ( EMKO_GALLERY_ID_SEQ.nextval, 1, 'src/images/couplecats.jpg',
  TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss' ) );
  
INSERT INTO EMKO_GALLERY_TBL(GALERY_ID, USER_ID, IMAGEPATH, CREATIONDATE)
  VALUES ( EMKO_GALLERY_ID_SEQ.nextval, 2, 'src/images/hiddencat.jpg',
  TO_DATE('2003/05/04 21:02:44', 'yyyy/mm/dd hh24:mi:ss' ) );
  
INSERT INTO EMKO_GALLERY_TBL(GALERY_ID, USER_ID, IMAGEPATH, CREATIONDATE)
  VALUES ( EMKO_GALLERY_ID_SEQ.nextval, 2, 'src/images/runningdog.jpg',
  TO_DATE('2003/05/04 21:02:44', 'yyyy/mm/dd hh24:mi:ss' ) );
  
INSERT INTO EMKO_GALLERY_TBL(GALERY_ID, USER_ID, IMAGEPATH, CREATIONDATE)
  VALUES ( EMKO_GALLERY_ID_SEQ.nextval, 2, 'src/images/nature.jpg',
  TO_DATE('2003/05/04 21:02:44', 'yyyy/mm/dd hh24:mi:ss' ) );
  
INSERT INTO EMKO_TICKETTYPES_TBL( TICKETTYPE_ID, TICKETTYPENAME)
 VALUES ( EMKO_TICKETTYPES_ID_SEQ.nextval, 'Contacts' );
 
INSERT INTO EMKO_TICKETTYPES_TBL( TICKETTYPE_ID, TICKETTYPENAME)
 VALUES ( EMKO_TICKETTYPES_ID_SEQ.nextval, 'Diets' );
 
INSERT INTO EMKO_TICKETS_TBL(TICKET_ID,TITLE, TYPE_ID, MESSAGE, HANDLER_ID,STATUS, SENDER_EMAIL, SENDER_NAME, CREATIONDATE)
  VALUES( EMKO_TICKETS_ID_SEQ.nextval, 'Kak da buda qk', 1, 'Pokaji mi shamane', 1, 'open','sender@email.one' ,'GoSHo QkiQ',
  TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss' ) 
 );
 
 INSERT INTO EMKO_TICKETS_TBL(TICKET_ID,TITLE, TYPE_ID, MESSAGE, HANDLER_ID,STATUS, SENDER_EMAIL, SENDER_NAME, CREATIONDATE)
  VALUES( EMKO_TICKETS_ID_SEQ.nextval, 'Kvo da qm', 2, 'Pushaci pushaci', 1, 'open','sender@email.one' ,'PESH0 silnIQ',
  TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss' ) 
 );
 
INSERT INTO EMKO_GUESTBOOK_TBL(GB_ID,GUESTNAME, MESSAGE, IMAGEPATH, CREATIONDATE)
  VALUES( EMKO_GUESTBOOK_ID_SEQ.nextval, 'Pesho', 'Blagodarenie na teb sum mnogo qk', 'src/images/couplecats.jpg', 
  TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss' ) 
 );
 
 INSERT INTO EMKO_GUESTBOOK_TBL(GB_ID,GUESTNAME, MESSAGE, IMAGEPATH, CREATIONDATE)
  VALUES( EMKO_GUESTBOOK_ID_SEQ.nextval, 'Nasko', 'Veche se chuvstvam mnogo po-dobre', 'src/images/hiddencat.jpg', 
  TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss' ) 
 );
 
 INSERT INTO EMKO_GUESTBOOK_TBL(GB_ID,GUESTNAME, MESSAGE, IMAGEPATH, CREATIONDATE)
  VALUES( EMKO_GUESTBOOK_ID_SEQ.nextval, 'Desi', 'Dobre che si ti <3', 'src/images/runningdog.jpg', 
  TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss' ) 
 );
 
 INSERT INTO EMKO_GUESTBOOK_TBL(GB_ID,GUESTNAME, MESSAGE, IMAGEPATH, CREATIONDATE)
  VALUES( EMKO_GUESTBOOK_ID_SEQ.nextval, 'Aleks', 'Blagodaria ti', 'src/images/nature.jpg', 
  TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss' ) 
 );
 
 INSERT INTO EMKO_GUESTBOOK_TBL(GB_ID,GUESTNAME, MESSAGE, IMAGEPATH, CREATIONDATE)
  VALUES( EMKO_GUESTBOOK_ID_SEQ.nextval, 'Emko', 'Shamana pravi vsichko tova vuzmojno', 'src/images/couplecats.jpg', 
  TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss' ) 
 );
 
 INSERT INTO EMKO_GUESTBOOK_TBL(GB_ID,GUESTNAME, MESSAGE, IMAGEPATH, CREATIONDATE)
  VALUES( EMKO_GUESTBOOK_ID_SEQ.nextval, 'Genkov', 'Nerealno dobur trener @@@PRO', 'src/images/runningdog.jpg', 
  TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss' ) 
 );
 
 INSERT INTO EMKO_GUESTBOOK_TBL(GB_ID,GUESTNAME, MESSAGE, IMAGEPATH, CREATIONDATE)
  VALUES( EMKO_GUESTBOOK_ID_SEQ.nextval, 'Gonko', 'Mnogo silen choEK, BAH TIQ RYKI', 'src/images/hiddencat.jpg', 
  TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss' ) 
 );
 
INSERT INTO EMKO_GUESTBOOK_TBL(GB_ID,GUESTNAME, MESSAGE, IMAGEPATH, CREATIONDATE)
  VALUES( EMKO_GUESTBOOK_ID_SEQ.nextval, 'Gosho', 'Dneska fen sum ti', '', 
  TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss' ) 
);

INSERT INTO EMKO_GUESTBOOK_TBL(GB_ID,GUESTNAME, MESSAGE, IMAGEPATH, CREATIONDATE)
  VALUES( EMKO_GUESTBOOK_ID_SEQ.nextval, 'Ivan', 'Mnogo sum dovolen', '', 
  TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss' ) 
);

INSERT INTO EMKO_GUESTBOOK_TBL(GB_ID,GUESTNAME, MESSAGE, IMAGEPATH, CREATIONDATE)
  VALUES( EMKO_GUESTBOOK_ID_SEQ.nextval, 'George', 'Really cool guy', '', 
  TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss' ) 
);

INSERT INTO EMKO_GUESTBOOK_TBL(GB_ID,GUESTNAME, MESSAGE, IMAGEPATH, CREATIONDATE)
  VALUES( EMKO_GUESTBOOK_ID_SEQ.nextval, 'Gergana', 'Mnogo raboten', '', 
  TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss' ) 
);

INSERT INTO EMKO_GUESTBOOK_TBL(GB_ID,GUESTNAME, MESSAGE, IMAGEPATH, CREATIONDATE)
  VALUES( EMKO_GUESTBOOK_ID_SEQ.nextval, 'Misho', 'Nerealno dobur trenyor', '', 
  TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss' ) 
);

INSERT INTO EMKO_GUESTBOOK_TBL(GB_ID,GUESTNAME, MESSAGE, IMAGEPATH, CREATIONDATE)
  VALUES( EMKO_GUESTBOOK_ID_SEQ.nextval, 'Bobanka', 'MLG EPS OMFG OVER 9000 DMG brb hf', '', 
  TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss' ) 
);
 
COMMIT;
