DROP TABLE places;
DROP TABLE sessions;
DROP TABLE users IF EXISTS;
create table PLACES (OCCUPATION_DATE varchar(255) not null, ID integer not null, EMAIL_OCCUPANT varchar(255), primary key (OCCUPATION_DATE, ID));
create table SESSIONS (SESSION_ID integer not null, COOKIE varchar(255), CREATION_DATE timestamp, EXPIRATION_DATE timestamp, USER_ID integer, primary key (SESSION_ID));
create table USERS (ID integer not null, EMAIL varchar(255), APIKEY varchar(255), PLACE_ID integer, PASSWORD varchar(255), TOKEN_MAIL varchar(255), TOKEN_PASSWORD varchar(255), primary key (ID));