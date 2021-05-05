DROP SCHEMA IF EXISTS TEXOIT CASCADE;
CREATE SCHEMA TEXOIT AUTHORIZATION SA;
CREATE MEMORY TABLE TEXOIT.MOVIE (
                                 ID integer identity primary key,
                                 YEAR integer not null,
                                 TITLE varchar(200) not null,
                                 STUDIOS varchar(200) not null,
                                 PRODUCERS varchar(200) not null,
                                 WINNER varchar(200) not null
);