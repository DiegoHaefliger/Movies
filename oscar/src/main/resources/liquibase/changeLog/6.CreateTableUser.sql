create table USER_TEST (
  	id              INT     PRIMARY KEY     AUTO_INCREMENT,
  	name            varchar(200)    not null,
    login           varchar(200)    not null,
    password        varchar(200)    not null,
    role            varchar(100)    not null
);