create table MOVIE_PRODUCERS (
  	id              INT     PRIMARY KEY     AUTO_INCREMENT,
  	id_movie        INT     not null,
    id_producer     INT     not null
);

ALTER TABLE MOVIE_PRODUCERS ADD FOREIGN KEY (id_movie) REFERENCES MOVIE(id);
ALTER TABLE MOVIE_PRODUCERS ADD FOREIGN KEY (id_producer) REFERENCES PRODUCER(id);
