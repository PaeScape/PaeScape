const sqlite = require("better-sqlite3");
const constants = require("./constants");
const db = new sqlite("./highscores.db");

db.pragma("journal_mode = WAL");
db.pragma("foreign_keys = ON");

// create
const insertPlayerStmt = db.prepare(
  "insert into players (username, staffRights, gameMode, prestige) values " +
    "(:username, :staffRights, :gameMode, :prestige) " +
    "returning id"
);

const insertSkillsStmt = db.prepare(
  "insert into skills (playerId, skillid, experience, level) values " +
    "(:playerId, 0, :attackExp, :attackLevel)," +
    "(:playerId, 1, :defenceExp, :defenceLevel)," +
    "(:playerId, 2, :strengthExp, :strengthLevel)," +
    "(:playerId, 3, :constitutionExp, :constitutionLevel)," +
    "(:playerId, 4, :rangedExp, :rangedLevel)," +
    "(:playerId, 5, :prayerExp, :prayerLevel)," +
    "(:playerId, 6, :magicExp, :magicLevel)," +
    "(:playerId, 7, :cookingExp, :cookingLevel)," +
    "(:playerId, 8, :woodcuttingExp, :woodcuttingLevel)," +
    "(:playerId, 9, :fletchingExp, :fletchingLevel)," +
    "(:playerId, 10, :fishingExp, :fishingLevel)," +
    "(:playerId, 11, :firemakingExp, :firemakingLevel)," +
    "(:playerId, 12, :craftingExp, :craftingLevel)," +
    "(:playerId, 13, :smithingExp, :smithingLevel)," +
    "(:playerId, 14, :miningExp, :miningLevel)," +
    "(:playerId, 15, :herbloreExp, :herbloreLevel)," +
    "(:playerId, 16, :agilityExp, :agilityLevel)," +
    "(:playerId, 17, :thievingExp, :thievingLevel)," +
    "(:playerId, 18, :slayerExp, :slayerLevel)," +
    "(:playerId, 19, :farmingExp, :farmingLevel)," +
    "(:playerId, 20, :runecraftingExp, :runecraftingLevel)," +
    "(:playerId, 21, :skillerExp, :skillerLevel)," +
    "(:playerId, 22, :hunterExp, :hunterLevel)," +
    "(:playerId, 23, :summoningExp, :summoningLevel)," +
    "(:playerId, 24, :dungeoneeringExp, :dungeoneeringLevel)"
);
const insertTotalStmt = db.prepare(
  "insert into totals (playerId, experience, level) values " +
    "(:playerId, (select sum(skills.experience) from skills where skills.playerId = :playerId), (select sum(skills.level) from skills where playerId = :playerId))"
);
const insertKCsStmt = db.prepare(
  "insert into killcounts (playerId, bossId, kills) values (:playerId, :bossId, :kills)"
);
const insertStatsStmt = db.prepare(
  "insert into statistics (playerId, statId, statValue) values (:playerId, :statId, :statValue)"
);
// read
const getPlayerStmt = db.prepare(
  "select players.id as playerId, username, staffRights, gameMode, dead, hidden, prestige, totals.experience as totalExp, totals.level as totalLevel from players inner join totals on totals.playerId = players.id where players.username = :username and players.gameMode = :gameMode"
);
const getPlayerSkillRanksStmt = db.prepare(
  `WITH cte AS (SELECT players.username, players.prestige, totals.experience, totals.level, -1 AS skillId, Rank() OVER (ORDER BY players.prestige DESC, totals.level DESC, totals.experience DESC, totals.updatedat ASC) skillRank FROM totals INNER JOIN players ON players.id = totals.playerid WHERE players.gamemode = :gameMode AND players.prestige > 0 and players.hidden = 0 UNION SELECT players.username, players.prestige, skills.experience, skills.level, skills.skillid AS skillId, Rank() OVER (PARTITION BY skills.skillid ORDER BY players.prestige DESC, skills.level DESC, skills.experience DESC, skills.updatedat ASC) skillRank FROM skills INNER JOIN players ON players.id = skills.playerid WHERE skills.skillid BETWEEN 0 and ${
    constants.SKILLS.length - 1
  } AND players.gamemode = :gameMode AND skills.experience > 0) SELECT * FROM cte WHERE username = :username order by skillId;`
);
const getPlayerBossRanksStmt = db.prepare(
  `WITH cte AS (SELECT players.username, killcounts.kills, killcounts.bossId, Rank() OVER (PARTITION BY killcounts.bossId ORDER BY killcounts.kills DESC, killcounts.updatedat ASC) bossRank FROM killcounts INNER JOIN players ON players.id = killcounts.playerid WHERE  players.gamemode = :gameMode AND killcounts.kills > 0) SELECT * FROM   cte WHERE  username = :username ORDER BY bossId`
);
const getPlayerStatRanksStmt = db.prepare(
  `WITH cte AS (SELECT players.username, statistics.statValue, statistics.statId, Rank() OVER (PARTITION BY statistics.statId ORDER BY statistics.statValue DESC, statistics.updatedat ASC) statRank FROM statistics INNER JOIN players ON players.id = statistics.playerid WHERE  players.gamemode = :gameMode AND statistics.statValue > 0) SELECT * FROM   cte WHERE  username = :username ORDER BY statId`
);
const getSkillsStmt = db.prepare(
  "select skillId, experience, level from skills where playerId = :playerId"
);
const getKCsStmt = db.prepare(
  "select bossId, kills from killcounts where playerId = :playerId"
);
const getStatsStmt = db.prepare(
  "select statId, statValue from statistics where playerId = :playerId"
);
const skillRankStmt = db.prepare(
  `select players.username, players.dead, players.prestige, skills.experience, skills.level, RANK() over (order by players.prestige DESC, skills.level DESC, skills.experience DESC, skills.updatedAt ASC) playerRank from skills inner join players on players.id = skills.playerId where skills.skillId = :skillId and players.gameMode = :gameMode and skills.experience > 0 limit ${constants.PLAYERS_PER_PAGE} offset (:page-1)*${constants.PLAYERS_PER_PAGE}`
);
const totalRankStmt = db.prepare(
  `select players.username, players.dead, players.prestige, totals.experience, totals.level, RANK() over (order by players.prestige DESC, totals.level DESC, totals.experience DESC, totals.updatedAt ASC) playerRank from totals inner join players on players.id = totals.playerId where players.gameMode = :gameMode and players.prestige > 0 and players.hidden = 0 limit ${constants.PLAYERS_PER_PAGE} offset (:page-1)*${constants.PLAYERS_PER_PAGE}`
);
const statRankStmt = db.prepare(
  `select players.username, players.dead, players.prestige, statistics.statValue, statistics.updatedAt, RANK() over (order by statistics.statValue DESC, statistics.updatedAt ASC) playerRank from statistics inner join players on players.id = statistics.playerId where statistics.statId = :statId and players.gameMode = :gameMode and statistics.statValue > 0 limit ${constants.PLAYERS_PER_PAGE} offset (:page-1)*${constants.PLAYERS_PER_PAGE}`
);
const bossRankStmt = db.prepare(
  `select players.username, players.dead, players.prestige, killcounts.kills, killcounts.updatedAt, RANK() over (order by killcounts.kills DESC, killcounts.updatedAt ASC) playerRank from killcounts inner join players on players.id = killcounts.playerId where killcounts.bossId = :bossId and players.gameMode = :gameMode and killcounts.kills > 0 limit ${constants.PLAYERS_PER_PAGE} offset (:page-1)*${constants.PLAYERS_PER_PAGE}`
);
const numSkillRankPagesStmt = db.prepare(
  `select ceil(cast(count(*) as real)/${constants.PLAYERS_PER_PAGE}) as pages from skills inner join players on players.id = skills.playerId where skills.skillId = :skillId and players.gameMode = :gameMode and players.prestige > 0 and players.hidden = 0`
);
const numTotalRankPagesStmt = db.prepare(
  `select ceil(cast(count(*) as real)/${constants.PLAYERS_PER_PAGE}) as pages from totals inner join players on players.id = totals.playerId where players.gameMode = :gameMode and players.prestige > 0 and players.hidden = 0`
);
const numStatRankPagesStmt = db.prepare(
  `select ceil(cast(count(*) as real)/${constants.PLAYERS_PER_PAGE}) as pages from statistics inner join players on players.id = statistics.playerId where statistics.statId = :statId and players.gameMode = :gameMode and statistics.statValue > 0 and players.hidden = 0`
);
const numBossRankPagesStmt = db.prepare(
  `select ceil(cast(count(*) as real)/${constants.PLAYERS_PER_PAGE}) as pages from killcounts inner join players on players.id = killcounts.playerId where killcounts.bossId = :bossId and players.gameMode = :gameMode and killcounts.kills > 0 and players.hidden = 0`
);

// update
const updatePlayerStmt = db.prepare(
  "update players set (staffRights, prestige, updatedAt) = (:staffRights, :prestige, DATETIME()) where id = :playerId and (staffRights != :staffRights OR prestige != :prestige)"
);
const updateSkillStmt = db.prepare(
  "update skills set (experience, level, updatedAt) = (:experience, :level, datetime()) where playerId = :playerId and skillId = :skillId"
);
const updateKCsStmt = db.prepare(
  "update killcounts set (kills, updatedAt) = (:kills, DATETIME()) where playerId = :playerId and bossId = :bossId"
);
const updateStatsStmt = db.prepare(
  "update statistics set (statValue, updatedAt) = (:statValue, DATETIME()) where playerId = :playerId and statId = :statId"
);
const updateDeathStmt = db.prepare(
  "update players set dead = :dead where username = :username and gameMode = :gameMode returning id as playerId, dead"
);
const updateHiddenStmt = db.prepare(
  "update players set hidden = :hidden where username = :username and gameMode = :gameMode returning id as playerId, hidden"
);

// delete
const deletePlayerStmt = db.prepare(
  "delete from players where username = :username and gameMode = :gameMode returning *"
);

// translate game server object to db object
function mapToDbObject(player) {
  let dbPlayer = {};
  dbPlayer.username = player.username.toLowerCase();
  dbPlayer.staffRights = player["staff-rights"];
  dbPlayer.gameMode = player["game-mode"];
  dbPlayer.prestige = player.prestige;
  if (player.killcounts) {
    for (let kc in constants.KILLCOUNTS_GS_TO_DB) {
      dbPlayer[constants.KILLCOUNTS_GS_TO_DB[kc]] = player.killcounts[kc];
    }
    for (let stat in constants.STAT_FROM_KC) {
      player.statistics[stat] = player.killcounts[constants.STAT_FROM_KC[stat]];
    }
  }
  if (player.statistics) {
    for (let stat of constants.STATS) {
      dbPlayer[stat] = player.statistics[stat];
      if(stat == "total_xp") {
        dbPlayer[stat] = parseInt(player.statistics[stat], 10);
      }
    }
  }
  
  for (let i = 0; i < player.skills.maxLevel.length; i++) {
    if (i === 3 || i === 5) {
      // prayer and constitution need to be divided by 10
      player.skills.maxLevel[i] /= 10;
    }
    dbPlayer[`${constants.SKILLS[i]}Level`] = player.skills.maxLevel[i];
    dbPlayer[`${constants.SKILLS[i]}Exp`] = player.skills.experience[i];
  }
  return dbPlayer;
}

const createPlayer = db.transaction((player) => {
  const row = insertPlayerStmt.get(player);
  player.playerId = row.id;
  insertSkillsStmt.run(player);
  insertTotalStmt.run(player);
  for (let i = 0; i < constants.STATS.length; i++) {
    const stat = constants.STATS[i];
    if (player[stat]) {
      insertStatsStmt.run({
        playerId: player.playerId,
        statId: i,
        statValue: player[stat],
      });
    }
  }
  for (let i = 0; i < constants.KILLCOUNTS.length; i++) {
    const killcount = constants.KILLCOUNTS[i];
    if (player[killcount]) {
      insertKCsStmt.run({
        playerId: player.playerId,
        bossId: i,
        kills: player[killcount],
      });
    }
  }
  return player;
});

const updatePlayer = db.transaction((player, dbPlayer) => {
  player.playerId = dbPlayer.playerId;
  updatePlayerStmt.run(player);
  if (player.prestige !== 10) {
    for (let i = 0; i < constants.SKILLS.length; i++) {
      const skill = constants.SKILLS[i];
      if (player[`${skill}Exp`] !== dbPlayer[`${skill}Exp`]) {
        updateSkillStmt.run({
          playerId: player.playerId,
          skillId: i,
          experience: player[`${skill}Exp`],
          level: player[`${skill}Level`],
        });
      }
    }
  } else {
    for (let i = 0; i < constants.SKILLS.length; i++) {
      const skill = constants.SKILLS[i];
      if (player[`${skill}Exp`] > dbPlayer[`${skill}Exp`]) {
        updateSkillStmt.run({
          playerId: player.playerId,
          skillId: i,
          experience: player[`${skill}Exp`],
          level: player[`${skill}Level`],
        });
      }
    }
  }

  for (let i = 0; i < constants.STATS.length; i++) {
    const stat = constants.STATS[i];
    if (player[stat] !== undefined) {
      if (dbPlayer[stat] === undefined) {
        insertStatsStmt.run({
          playerId: player.playerId,
          statId: i,
          statValue: player[stat],
        });
      } else {
        updateStatsStmt.run({
          playerId: player.playerId,
          statId: i,
          statValue: player[stat],
        });
      }
    }
  }

  for (let i = 0; i < constants.KILLCOUNTS.length; i++) {
    const killcount = constants.KILLCOUNTS[i];
    if (player[killcount] !== undefined) {
      if (dbPlayer[killcount] === undefined) {
        insertKCsStmt.run({
          playerId: player.playerId,
          bossId: i,
          kills: player[killcount],
        });
      } else {
        updateKCsStmt.run({
          playerId: player.playerId,
          bossId: i,
          kills: player[killcount],
        });
      }
    }
  }
  return player;
});

function togglePlayerDead(username, gameMode) {
  const player = getPlayerStmt.get({ username, gameMode });
  if (player.dead !== 0) {
    return updateDeathStmt.get({
      username: username,
      gameMode: gameMode,
      dead: 0,
    });
  } else {
    return updateDeathStmt.get({
      username: username,
      gameMode: gameMode,
      dead: 1,
    });
  }
}

function togglePlayerHidden(username, gameMode) {
  const player = getPlayerStmt.get({ username, gameMode });
  if (player.hidden !== 0) {
    return updateHiddenStmt.get({
      username: username,
      gameMode: gameMode,
      hidden: 0,
    });
  } else {
    return updateHiddenStmt.get({
      username: username,
      gameMode: gameMode,
      hidden: 1,
    });
  }
}

function deletePlayer(username, gameMode) {
  return deletePlayerStmt.get({ username, gameMode });
}

function getPlayer(username, gameMode) {
  const player = getPlayerStmt.get({ username, gameMode });
  if (player === undefined) return undefined;

  const killcountRows = getKCsStmt.all(player);
  for (let killcount of killcountRows) {
    player[constants.KILLCOUNTS[killcount.bossId]] = killcount.kills;
  }

  const skillRows = getSkillsStmt.all(player);
  if (skillRows.length === 0) return undefined; // TODO: error. handle this
  for (let skill of skillRows) {
    player[`${constants.SKILLS[skill.skillId]}Level`] = skill.level;
    player[`${constants.SKILLS[skill.skillId]}Exp`] = skill.experience;
  }
  const statRows = getStatsStmt.all(player);
  for (let stat of statRows) {
    player[constants.STATS[stat.statId]] = stat.statValue;
  }

  return player;
}

function getPlayerRanked(username, gameMode) {
  const player = getPlayer(username, gameMode);
  if (player === undefined) return undefined;
  const skillRanks = getPlayerSkillRanksStmt.all({
    username: player.username,
    gameMode: player.gameMode,
  });
  player.totalRank = skillRanks.shift().skillRank;
  for (let skillRankRow of skillRanks) {
    player[`${constants.SKILLS[skillRankRow.skillId]}Rank`] =
      skillRankRow.skillRank;
  }
  const bossRanks = getPlayerBossRanksStmt.all({
    username: player.username,
    gameMode: player.gameMode,
  });
  for (let bossRankRow of bossRanks) {
    player[`${constants.KILLCOUNTS[bossRankRow.bossId]}Rank`] =
      bossRankRow.bossRank;
  }
  const statRanks = getPlayerStatRanksStmt.all({
    username: player.username,
    gameMode: player.gameMode,
  });
  for (let statRankRow of statRanks) {
    player[`${constants.STATS[statRankRow.statId]}Rank`] = statRankRow.statRank;
  }

  return player;
}

function getOverallRanked(gameMode, page) {
  return totalRankStmt.all({ gameMode: gameMode, page: page });
}

function getSkillRanked(gameMode, skill, page) {
  return skillRankStmt.all({
    gameMode: gameMode,
    skillId: constants.SKILLS_IDX[skill],
    page: page,
  });
}

function getStatRanked(gameMode, stat, page) {
  return statRankStmt.all({
    gameMode: gameMode,
    statId: constants.STATS_IDX[stat],
    page: page,
  });
}

function getBossRanked(gameMode, boss, page) {
  return bossRankStmt.all({
    gameMode: gameMode,
    bossId: constants.KILLCOUNTS_IDX[boss],
    page: page,
  });
}

function getNumberOfOverallPages(gameMode, page) {
  return numTotalRankPagesStmt.get({ gameMode, page });
}

function getNumberOfSkillPages(gameMode, skill, page) {
  return numSkillRankPagesStmt.get({
    gameMode: gameMode,
    skillId: constants.SKILLS_IDX[skill],
    page: page,
  });
}

function getNumberOfStatPages(gameMode, skill, page) {
  return numStatRankPagesStmt.get({
    gameMode: gameMode,
    statId: constants.STATS_IDX[skill],
    page: page,
  });
}

function getNumberOfBossPages(gameMode, boss, page) {
  return numBossRankPagesStmt.get({
    gameMode: gameMode,
    bossId: constants.KILLCOUNTS_IDX[boss],
    page: page,
  });
}

module.exports = {
  db,
  getPlayer,
  getPlayerRanked,
  createPlayer,
  updatePlayer,
  togglePlayerDead,
  togglePlayerHidden,
  deletePlayer,
  getOverallRanked,
  getSkillRanked,
  getStatRanked,
  getBossRanked,
  getNumberOfOverallPages,
  getNumberOfSkillPages,
  getNumberOfStatPages,
  getNumberOfBossPages,
  mapToDbObject,
};
