DROP SCHEMA IF EXISTS TEXOIT CASCADE;
CREATE SCHEMA TEXOIT AUTHORIZATION SA;
--MOVIE
CREATE MEMORY TABLE TEXOIT.MOVIE (
                                     ID integer identity primary key,
                                     YEAR integer not null,
                                     TITLE varchar(200) not null,
                                     WINNER varchar(200) not null
);
--STUDIO
CREATE MEMORY TABLE TEXOIT.STUDIO (
                                      ID integer identity primary key,
                                      NAME varchar(200) not null
);
CREATE TABLE TEXOIT.MOVIE_STUDIO
(
    ID        integer identity primary key,
    ID_MOVIE  integer references TEXOIT.MOVIE(ID),
    ID_STUDIO integer references TEXOIT.STUDIO(ID)
);
--PRODUCER
CREATE MEMORY TABLE TEXOIT.PRODUCER (
                                        ID integer identity primary key,
                                        NAME varchar(200) not null
);
CREATE TABLE TEXOIT.MOVIE_PRODUCER
(
    ID        integer identity primary key,
    ID_MOVIE  integer references TEXOIT.MOVIE(ID),
    ID_PRODUCER integer references TEXOIT.PRODUCER(ID)
);