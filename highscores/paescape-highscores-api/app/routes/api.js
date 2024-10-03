const express = require("express");
const constants = require("../constants");
const router = express.Router();
const db = require("../db");

const validUsername = /^[a-z0-9_]+$/;
const validSkillOrBoss = /^[a-z_]+$/;
const validGameMode = /^[A-Z_]+$/;

/* POST player stats */
router.post("/player", (req, res, next) => {
  const ip = req.ip;
  // only game server should hit this
  if (ip !== process.env.GAMESERVER_IP) {
    res.sendStatus(403);
    return;
  }
  req.body.username = req.body.username.toLowerCase().replace(/ /g, "_");
  if (!validUsername.test(req.body.username)) {
    res.status(400).send({ error: true, message: "Invalid request" });
    return;
  }
  req.params["game-mode"] = req.body["game-mode"].toUpperCase();
  if (!validGameMode.test(req.body["game-mode"])) {
    res.status(400).send({ error: true, message: "Invalid gameMode" });
    return;
  }
  const dbPlayer = db.getPlayer(req.body.username, req.body["game-mode"]);
  const player = db.mapToDbObject(req.body);

  for (let i = 0; i < constants.SKILLS.length; i++) {
    if (
      (player[`${constants.SKILLS[i]}Exp`] < 0 ||
        player[`${constants.SKILLS[i]}Exp`] > constants.MAX_EXP) &&
      (player[`${constants.SKILLS[i]}Level`] < 0 ||
        player[`${constants.SKILLS[i]}Level`] > constants.MAX_LEVEL)
    ) {
      res.status(400).send({ error: true, message: "Invalid skills" });
      return;
    }
  }

  for (let i = 0; i < constants.KILLCOUNTS.length; i++) {
    if (player[constants.KILLCOUNTS[i]] < 0) {
      res.status(400).send({ error: true, message: "Invalid kcs" });
      return;
    }
  }

  for (let i = 0; i < constants.STATS.length; i++) {
    if (player[constants.STATS[i]] < 0) {
      res.status(400).send({ error: true, message: "Invalid stats" });
      return;
    }
  }

  if (dbPlayer === undefined) {
    const p = db.createPlayer(player);
    if (p.playerId === undefined) {
      res.status(400).send({
        error: true,
        message: `failed to create player ${player.username}`,
      });
      return;
    }
    res.status(200).send({ playerId: p.playerId });
  } else {
    db.updatePlayer(player, dbPlayer);
    res.status(200).send({ playerId: dbPlayer.playerId });
  }
});

router.post("/death", (req, res, next) => {
  const ip = req.ip;
  // only game server should hit this
  if (ip !== process.env.GAMESERVER_IP) {
    res.sendStatus(403);
    return;
  }
  if (!validUsername.test(req.body.username)) {
    res.status(400).send({ error: true, message: "Invalid username" });
    return;
  }
  req.body["game-mode"] = req.body["game-mode"].toUpperCase();
  if (!validGameMode.test(req.body["game-mode"])) {
    res.status(400).send({ error: true, message: "Invalid gameMode" });
    return;
  }

  const p = db.togglePlayerDead(req.body.username, req.body["game-mode"]);
  if (p === undefined || p.playerId === undefined) {
    res.status(400).send({
      error: true,
      message: `failed to toggle death ${req.body["game-mode"]} ${req.body.username}`,
    });
  }
  res.status(200).send({ playerId: p.playerId, dead: p.dead });
});

router.post("/hide", (req, res, next) => {
  const ip = req.ip;
  // only game server should hit this
  if (ip !== process.env.GAMESERVER_IP) {
    res.sendStatus(403);
    return;
  }
  if (!validUsername.test(req.body.username)) {
    res.status(400).send({ error: true, message: "Invalid username" });
    return;
  }
  req.body["game-mode"] = req.body["game-mode"].toUpperCase();
  if (!validGameMode.test(req.body["game-mode"])) {
    res.status(400).send({ error: true, message: "Invalid gameMode" });
    return;
  }

  const p = db.togglePlayerHidden(req.body.username, req.body["game-mode"]);
  if (p === undefined || p.playerId === undefined) {
    res.status(400).send({
      error: true,
      message: `failed to toggle hidden ${req.body["game-mode"]} ${req.body.username}`,
    });
  }
  res.status(200).send({ playerId: p.playerId, hidden: p.hidden });
});

router.delete("/player/:gameMode/:username", (req, res, next) => {
  const ip = req.ip;
  // only game server should hit this
  if (ip !== process.env.GAMESERVER_IP) {
    res.sendStatus(403);
    return;
  }
  if (!validUsername.test(req.params.username)) {
    res.status(400).send({ error: true, message: "Invalid username" });
    return;
  }
  req.params.gameMode = req.params.gameMode.toUpperCase();
  if (!validGameMode.test(req.params.gameMode)) {
    res.status(400).send({ error: true, message: "Invalid gameMode" });
    return;
  }

  const p = db.deletePlayer(req.params.username, req.params.gameMode);
  if (p === undefined || p.id === undefined) {
    res.status(400).send({
      error: true,
      message: `failed to delete ${req.params.gameMode} ${req.params.username}`,
    });
  }
  res.status(200).send({ playerId: p.id });
});

router.get("/overall/:gameMode/:page", (req, res, next) => {
  // paginated front page/overall high scores
  const page = parseInt(req.params.page);
  if (isNaN(page) || page < 0) {
    res.send({ rows: [] });
    return;
  }

  req.params.gameMode = req.params.gameMode.toUpperCase();
  if (!validGameMode.test(req.params.gameMode)) {
    res.send({ rows: [] });
    return;
  }

  const toReturn = {
    page: page,
    gameMode: req.params.gameMode,
  };

  if (page > 1) {
    toReturn.prevPage = page - 1;
  } else {
    toReturn.prevPage = 0;
  }

  const totalPages = db.getNumberOfOverallPages(req.params.gameMode, page);
  if (page < totalPages.pages) {
    toReturn.nextPage = page + 1;
  } else {
    toReturn.nextPage = 0;
  }

  if (page <= totalPages.pages) {
    toReturn.rows = db.getOverallRanked(req.params.gameMode, page);
  } else {
    toReturn.rows = [];
  }

  res.send(toReturn);
});

router.get("/skill/:gameMode/:skill/:page", (req, res, next) => {
  const page = parseInt(req.params.page);

  if (isNaN(page) || page < 0) {
    res.send({ rows: [] });
    return;
  }

  req.params.gameMode = req.params.gameMode.toUpperCase();
  if (!validGameMode.test(req.params.gameMode)) {
    res.send({ rows: [] });
    return;
  }

  req.params.skill = req.params.skill.toLowerCase();
  if (!validSkillOrBoss.test(req.params.skill)) {
    res.send({ rows: [] });
    return;
  }

  const toReturn = {
    page: page,
    gameMode: req.params.gameMode,
    skill: req.params.skill,
  };

  if (page > 1) {
    toReturn.prevPage = page - 1;
  } else {
    toReturn.prevPage = 0;
  }

  const totalPages = db.getNumberOfSkillPages(
    req.params.gameMode,
    req.params.skill,
    page
  );

  if (page < totalPages.pages) {
    toReturn.nextPage = page + 1;
  } else {
    toReturn.nextPage = 0;
  }

  if (page <= totalPages.pages) {
    toReturn.rows = db.getSkillRanked(
      req.params.gameMode,
      req.params.skill,
      page
    );
  } else {
    toReturn.rows = [];
  }

  res.send(toReturn);
});

router.get("/stat/:gameMode/:stat/:page", (req, res, next) => {
  const page = parseInt(req.params.page);
  if (isNaN(page) || page < 0) {
    res.send({ rows: [] });
    return;
  }

  req.params.gameMode = req.params.gameMode.toUpperCase();
  if (!validGameMode.test(req.params.gameMode)) {
    res.send({ rows: [] });
    return;
  }

  req.params.stat = req.params.stat.toLowerCase();
  if (!validSkillOrBoss.test(req.params.stat)) {
    res.send({ rows: [] });
    return;
  }

  const toReturn = {
    page: page,
    gameMode: req.params.gameMode,
    stat: req.params.stat,
  };

  if (page > 1) {
    toReturn.prevPage = page - 1;
  } else {
    toReturn.prevPage = 0;
  }

  const totalPages = db.getNumberOfStatPages(
    req.params.gameMode,
    req.params.stat,
    page
  );

  if (page < totalPages.pages) {
    toReturn.nextPage = page + 1;
  } else {
    toReturn.nextPage = 0;
  }

  if (page <= totalPages.pages) {
    toReturn.rows = db.getStatRanked(
      req.params.gameMode,
      req.params.stat,
      page
    );
  } else {
    toReturn.rows = [];
  }

  res.send(toReturn);
});

router.get("/boss/:gameMode/:boss/:page", (req, res, next) => {
  const page = parseInt(req.params.page);
  if (isNaN(page) || page < 0) {
    res.send({ rows: [] });
    return;
  }

  req.params.gameMode = req.params.gameMode.toUpperCase();
  if (!validGameMode.test(req.params.gameMode)) {
    res.send({ rows: [] });
    return;
  }

  req.params.boss = req.params.boss.toLowerCase();
  if (!validSkillOrBoss.test(req.params.boss)) {
    res.send({ rows: [] });
    return;
  }

  const toReturn = {
    page: page,
    gameMode: req.params.gameMode,
    boss: req.params.boss,
  };

  if (page > 1) {
    toReturn.prevPage = page - 1;
  } else {
    toReturn.prevPage = 0;
  }

  const totalPages = db.getNumberOfBossPages(
    req.params.gameMode,
    req.params.boss,
    page
  );

  if (page < totalPages.pages) {
    toReturn.nextPage = page + 1;
  } else {
    toReturn.nextPage = 0;
  }

  if (page <= totalPages.pages) {
    toReturn.rows = db.getBossRanked(
      req.params.gameMode,
      req.params.boss,
      page
    );
  } else {
    toReturn.rows = [];
  }

  res.send(toReturn);
});

router.get("/personal/:gameMode/:username", (req, res, next) => {
  if (!validUsername.test(req.params.username)) {
    res.send({});
    return;
  }

  req.params.gameMode = req.params.gameMode.toUpperCase();
  if (!validGameMode.test(req.params.gameMode)) {
    res.send({});
    return;
  }

  const player = db.getPlayerRanked(
    req.params.username.toLowerCase().replace(/ /g, "_"),
    req.params.gameMode
  );
  res.send({
    player: player,
  });
});

router.get("/compare/:username1/:username2", (req, res, next) => {
  // TODO: compare two users' stats
});

module.exports = router;
