DROP DATABASE IF EXISTS moviedb;
CREATE DATABASE moviedb;
USE moviedb;

DROP TABLE IF EXISTS USER;

CREATE TABLE USER (
  ID       BIGINT       NOT NULL AUTO_INCREMENT,
  username VARCHAR(255) NOT NULL,
  login    VARCHAR(60)  NOT NULL,
  password VARCHAR(255) NOT NULL,
  isadmin  TINYINT      NOT NULL DEFAULT 0,
  isbanned TINYINT      NOT NULL DEFAULT 0,
  PRIMARY KEY (ID)
);

DROP TABLE IF EXISTS MOVIE;

CREATE TABLE MOVIE (
  ID          BIGINT       NOT NULL AUTO_INCREMENT,
  moviename   VARCHAR(255) NOT NULL,
  director    VARCHAR(255),
  releasedate DATE,
  posterurl   VARCHAR(255),
  trailerurl  VARCHAR(255),
  rating      DOUBLE,
  description TEXT,
  PRIMARY KEY (ID)
);

DROP TABLE IF EXISTS REVIEW;

CREATE TABLE REVIEW (
  ID          BIGINT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (ID),
  userID      BIGINT NOT NULL,
  FOREIGN KEY (userID)
  REFERENCES USER (ID)
    ON DELETE CASCADE,
  movieID     BIGINT NOT NULL,
  FOREIGN KEY (movieID)
  REFERENCES MOVIE (ID)
    ON DELETE CASCADE,
  postdate    DATE,
  reviewtitle VARCHAR(100),
  rating      INT,
  reviewtext  TEXT
);

INSERT INTO USER (username, login, password, isadmin, isbanned)
VALUES ('Lothar', '123@gmail.com', 'MTI5MTIz$/Pyqs8aQ8eaq5PLxzH+VTB97f+w9xd5+G9EBMwThdOA=', 0, 0),
  ('Medivh', 'qwe@gmail.com', 'MTI5MTIz$/Pyqs8aQ8eaq5PLxzH+VTB97f+w9xd5+G9EBMwThdOA=', 0, 1),
  ('Neltarion', 'asd@gmail.com', 'MTI5MTIz$/Pyqs8aQ8eaq5PLxzH+VTB97f+w9xd5+G9EBMwThdOA=', 1, 0),
  ('Lothar1', '1231@gmail.com', 'MTI5MTIz$/Pyqs8aQ8eaq5PLxzH+VTB97f+w9xd5+G9EBMwThdOA=', 0, 0),
  ('Lothar2', '1232@gmail.com', 'MTI5MTIz$/Pyqs8aQ8eaq5PLxzH+VTB97f+w9xd5+G9EBMwThdOA=', 0, 0),
  ('Lothar3', '1233@gmail.com', 'MTI5MTIz$/Pyqs8aQ8eaq5PLxzH+VTB97f+w9xd5+G9EBMwThdOA=', 0, 0),
  ('Lothar4', '1234@gmail.com', 'MTI5MTIz$/Pyqs8aQ8eaq5PLxzH+VTB97f+w9xd5+G9EBMwThdOA=', 0, 0),
  ('Lothar5', '1235@gmail.com', 'MTI5MTIz$/Pyqs8aQ8eaq5PLxzH+VTB97f+w9xd5+G9EBMwThdOA=', 0, 0),
  ('Lothar6', '1236@gmail.com', 'MTI5MTIz$/Pyqs8aQ8eaq5PLxzH+VTB97f+w9xd5+G9EBMwThdOA=', 0, 0),
  ('Lothar7', '1237@gmail.com', 'MTI5MTIz$/Pyqs8aQ8eaq5PLxzH+VTB97f+w9xd5+G9EBMwThdOA=', 0, 0),
  ('Lothar8', '1238@gmail.com', 'MTI5MTIz$/Pyqs8aQ8eaq5PLxzH+VTB97f+w9xd5+G9EBMwThdOA=', 0, 0),
  ('Lothar9', '1239@gmail.com', 'MTI5MTIz$/Pyqs8aQ8eaq5PLxzH+VTB97f+w9xd5+G9EBMwThdOA=', 0, 0),
  ('Lothar10', '12310@gmail.com', 'MTI5MTIz$/Pyqs8aQ8eaq5PLxzH+VTB97f+w9xd5+G9EBMwThdOA=', 0, 0),
  ('Lothar11', '12311@gmail.com', 'MTI5MTIz$/Pyqs8aQ8eaq5PLxzH+VTB97f+w9xd5+G9EBMwThdOA=', 0, 0),
  ('Lothar12', '12312@gmail.com', 'MTI5MTIz$/Pyqs8aQ8eaq5PLxzH+VTB97f+w9xd5+G9EBMwThdOA=', 0, 0),
  ('Lothar13', '12313@gmail.com', 'MTI5MTIz$/Pyqs8aQ8eaq5PLxzH+VTB97f+w9xd5+G9EBMwThdOA=', 0, 0),
  ('Lothar14', '12314@gmail.com', 'MTI5MTIz$/Pyqs8aQ8eaq5PLxzH+VTB97f+w9xd5+G9EBMwThdOA=', 0, 0),
  ('Lothar15', '12315@gmail.com', 'MTI5MTIz$/Pyqs8aQ8eaq5PLxzH+VTB97f+w9xd5+G9EBMwThdOA=', 0, 0),
  ('Lothar16', '12316@gmail.com', 'MTI5MTIz$/Pyqs8aQ8eaq5PLxzH+VTB97f+w9xd5+G9EBMwThdOA=', 0, 0),
  ('Lothar17', '12317@gmail.com', 'MTI5MTIz$/Pyqs8aQ8eaq5PLxzH+VTB97f+w9xd5+G9EBMwThdOA=', 0, 0),
  ('Lothar18', '12318@gmail.com', 'MTI5MTIz$/Pyqs8aQ8eaq5PLxzH+VTB97f+w9xd5+G9EBMwThdOA=', 0, 0),
  ('Lothar19', '12319@gmail.com', 'MTI5MTIz$/Pyqs8aQ8eaq5PLxzH+VTB97f+w9xd5+G9EBMwThdOA=', 0, 0);

INSERT INTO MOVIE (moviename, director, releasedate, trailerurl, rating, description, posterurl)
VALUES ('Warcraft', 'Duncan Jones', '2016-06-10', 'https://www.youtube.com/embed/RhFMIRuHAL4', 7.4,
        'As an Orc horde invades the planet Azeroth using a magic portal, a few human heroes and dissenting Orcs must attempt to stop the true evil behind this war.',
        'https://upload.wikimedia.org/wikipedia/ru/2/21/Warcraft_poster.jpg'),
  ('Tempo', 'Random Dude', '2015-03-12', 'https://www.youtube.com/embed/RhFMIRuHAL4', 6.6, 'Nothing to say here', ''),
  ('Dragon', 'Another Random Dude', '2014-09-22', 'https://www.youtube.com/embed/RhFMIRuHAL4', 5.4, 'Nice movie', '');

INSERT INTO REVIEW (userID, movieID, postdate, reviewtitle, rating, reviewtext)
VALUES (1, 1, '2016-06-11', 'Best shit ever', 10, 'Still best shit I have ever seen'),
  (1, 3, '2016-06-11', 'Best shit ever', 10, 'Still best shit I have ever seen'),
  (2, 2, '2015-06-05', 'Nice', 7, 'Nice movie'),
  (3, 1, '2015-04-20', 'So so', 6, 'Not so nice'),
  (3, 2, '2015-07-01', 'Bad', 3, 'Garbage');




