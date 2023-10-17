CREATE DATABASE IF NOT EXISTS appDB;
CREATE USER IF NOT EXISTS 'user'@'%' IDENTIFIED BY 'password';
GRANT SELECT,UPDATE,INSERT,DELETE ON appDB.* TO 'user'@'%';
FLUSH PRIVILEGES;

USE appDB;

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `user_id` mediumint(8) unsigned NOT NULL auto_increment,
  `username` varchar(255) default NULL,
  `user_email` varchar(255) default NULL,
  `user_status` mediumint default NULL,
  PRIMARY KEY (`user_id`)
) AUTO_INCREMENT=1;

INSERT INTO `users` (`username`,`user_email`,`user_status`)
VALUES
  ("Jenna","donec.elementum@google.net",2),
  ("Seth","nulla.in.tincidunt@aol.couk",3),
  ("Lenore","ut.nulla@icloud.com",7),
  ("Ruby","suspendisse@hotmail.org",8),
  ("Hilary","lacinia.vitae@yahoo.net",3),
  ("Linus","feugiat.lorem@aol.ca",5),
  ("Susan","metus.in@protonmail.net",1),
  ("Georgia","enim.diam@outlook.edu",10),
  ("Mark","sed.tortor@google.couk",6),
  ("Arden","cursus.diam@google.org",7),
  ("Kalia","pede.suspendisse@outlook.edu",3),
  ("Igor","vel.venenatis@hotmail.edu",2),
  ("Breanna","magna@icloud.couk",2),
  ("Keaton","vel.arcu.curabitur@protonmail.ca",10),
  ("Camden","cursus.purus@protonmail.org",1);