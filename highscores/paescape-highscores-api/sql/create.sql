DROP TABLE IF EXISTS players;
DROP TABLE IF EXISTS skills;
DROP TABLE IF EXISTS totals;
DROP TABLE IF EXISTS killcounts;

CREATE TABLE players (
  id integer PRIMARY KEY AUTOINCREMENT,
  username text UNIQUE NOT NULL,
  staffRights test NOT NULL,
  gameMode text NOT NULL,
  dead integer NOT NULL default FALSE,
  hidden integer NOT NULL default FALSE,
  prestige integer NOT NULL,
  createdAt datetime NOT NULL default current_timestamp,
  updatedAt datetime NOT NULL default current_timestamp
);

CREATE TABLE skills (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  playerId INTEGER NOT NULL REFERENCES players(id) ON DELETE CASCADE ON UPDATE CASCADE,
  skillId integer NOT NULL,
  experience INTEGER NOT NULL,
  level INTEGER NOT NULL,
  createdAt datetime NOT NULL default current_timestamp,
  updatedAt datetime NOT NULL default current_timestamp
);

CREATE TABLE totals (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  playerId INTEGER NOT NULL REFERENCES players(id) ON DELETE CASCADE ON UPDATE CASCADE,
  experience INTEGER NOT NULL,
  level INTEGER NOT NULL,
  createdAt datetime NOT NULL default current_timestamp,
  updatedAt datetime NOT NULL default current_timestamp
);

CREATE TABLE killcounts (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  playerId INTEGER NOT NULL REFERENCES players(id) ON DELETE CASCADE ON UPDATE CASCADE,
  bossId INTEGER NOT NULL,
  kills INTEGER NOT NULL,
  createdAt datetime NOT NULL default current_timestamp,
  updatedAt datetime NOT NULL default current_timestamp
);

CREATE TABLE statistics (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  playerId INTEGER NOT NULL REFERENCES players(id) ON DELETE CASCADE ON UPDATE CASCADE,
  statId INTEGER NOT NULL,
  statValue INTEGER NOT NULL,
  createdAt datetime NOT NULL default current_timestamp,
  updatedAt datetime NOT NULL default current_timestamp
);

DROP TRIGGER IF EXISTS updateTotalsAfterUpdateSkills;

CREATE TRIGGER updateTotalsAfterUpdateSkills after update on skills
BEGIN
  UPDATE totals set (experience, level, updatedAt) = ((select sum(skills.experience) from skills where skills.playerId = OLD.playerId), (select sum(skills.level) from skills where playerId = OLD.playerId), datetime()) where totals.playerId = OLD.playerId;
END;