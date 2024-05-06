USE client;

DROP TABLE Authentications;

CREATE TABLE IF NOT EXISTS Authentications (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(250) UNIQUE,
  password CHAR(60) BINARY NOT NULL  -- Use a fixed-length data type for password
);


INSERT INTO Authentications (username, password) VALUES
  ('tuturuu', 'password123'),
  ('user2', 'password123'),
  ('user3', 'password123');

SELECT * FROM Authentications;