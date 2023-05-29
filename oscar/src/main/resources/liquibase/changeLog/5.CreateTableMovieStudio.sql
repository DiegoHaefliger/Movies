create table MOVIE_STUDIO (
  	id              INT     PRIMARY KEY     AUTO_INCREMENT,
  	id_movie        INT     not null,
    id_studio       INT     not null
);

ALTER TABLE MOVIE_STUDIO ADD FOREIGN KEY (id_movie) REFERENCES MOVIE(id);
ALTER TABLE MOVIE_STUDIO ADD FOREIGN KEY (id_studio) REFERENCES STUDIO(id);
