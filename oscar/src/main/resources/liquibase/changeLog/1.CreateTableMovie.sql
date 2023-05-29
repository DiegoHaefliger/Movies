create table MOVIE (
  	id              INT     PRIMARY KEY     AUTO_INCREMENT,
  	year_movie      INT             not null,
  	title           varchar(200)    not null,
  	winner          BOOLEAN         not null
);