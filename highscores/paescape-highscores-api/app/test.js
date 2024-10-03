const fs = require("fs");
const sqlite = require("better-sqlite3");
const constants = require("./constants");

function createDB() {
    const dbf = new sqlite("./highscores.db");
    dbf.pragma("journal_mode = WAL");
    dbf.pragma("foreign_keys = ON");
    const createDB = fs.readFileSync("./sql/create.sql", "utf8");
    dbf.exec(createDB);
    dbf.close();
}

function getRandomInt(max) {
    return Math.floor(Math.random() * max);
}

function randGameMode() {
    const mode = getRandomInt(4);
    switch (mode) {
        case 0:
            return "NORMAL";
        case 1:
            return "IRONMAN";
        case 2:
            return "HARDCORE_IRONMAN";
        case 3:
            return "ULTIMATE_IRONMAN";
    }
}

const VALID_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
function randUsername() {
    let name = "";
    for (let i = 0; i < 5+getRandomInt(9); i++) {
        name += VALID_CHARACTERS[getRandomInt(VALID_CHARACTERS.length)];
    }
    return name;
}

function randKillcounts() {
    let kcs = {};
    for (let kc in constants.KILLCOUNTS_GS_TO_DB) {
        kcs[kc] = getRandomInt(1000000);
    }
    return kcs;
}

const EXPERIENCE_FOR_120 = 104273167;
const EXP_ARRAY = [
    0, 83, 174, 276, 388, 512, 650, 801, 969, 1154, 1358, 1584, 1833, 2107, 2411, 2746, 3115, 3523,
    3973, 4470, 5018, 5624, 6291, 7028, 7842, 8740, 9730, 10824, 12031, 13363, 14833, 16456, 18247,
    20224, 22406, 24815, 27473, 30408, 33648, 37224, 41171, 45529, 50339, 55649, 61512, 67983, 75127,
    83014, 91721, 101333, 111945, 123660, 136594, 150872, 166636, 184040, 203254, 224466, 247886,
    273742, 302288, 333804, 368599, 407015, 449428, 496254, 547953, 605032, 668051, 737627, 814445,
    899257, 992895, 1096278, 1210421, 1336443, 1475581, 1629200, 1798808, 1986068, 2192818, 2421087,
    2673114, 2951373, 3258594, 3597792, 3972294, 4385776, 4842295, 5346332, 5902831, 6517253, 7195629,
    7944614, 8771558, 9684577, 10692629, 11805606, 13034431, 14391160,
    15889109, 17542976, 19368992, 21385073, 23611006, 26068632, 28782069,
    31777943, 35085654, 38737661, 42769801, 47221641, 52136869,
    57563718, 63555443, 70170840, 77474828, 85539082, 94442737, 104273167
];
function getLevelForExperience(experience, prestige) {

    let maxLevel = 99 + (prestige * 2);

    if (prestige > 9)
        maxLevel = 120;

    if (experience <= EXPERIENCE_FOR_120) {
        for (let j = 119; j >= 0; j--) {
            if (EXP_ARRAY[j] <= experience) {

                if (j + 1 >= maxLevel)
                    return maxLevel;
                else
                    return j + 1;
            }
        }
    } else {
        let points = 0, output = 0;
        for (let lvl = 1; lvl <= maxLevel; lvl++) {
            points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
            output = Math.floor(points / 4);
            if (output >= experience) {

                if (lvl >= maxLevel)
                    return maxLevel;
                else
                    return lvl;
            }
        }
    }

    return maxLevel;

    //return 120;
}

function randSkills(prestige) {
    let maxLevels = [];
    let exps = [];
    for (let i = 0; i < constants.SKILLS.length; i++) {
        exps.push(getRandomInt(constants.MAX_EXP));
        maxLevels.push(getLevelForExperience(exps[i], prestige));
        if (i === 5 || i === 3) {
            maxLevels[i] *= 10;
        }
    }
    
    return {
        experience: exps,
        maxLevel: maxLevels
    };
}

function getTotalXP(skills) {
    let totalXp = 0;
    for (let xp of skills.experience) {
        totalXp += xp;
    }
    return totalXp;
}

function getTotalKCs(kcs) {
    let totalKCs = 0;
    for (let kc in kcs) {
        totalKCs += kcs[kc];
    }
    return totalKCs;
}

function createAndPopulateDBTest() {
    if (fs.existsSync("./highscores.db")) {
        console.log("ENV SET TO DEVELOPMENT")
        console.log("highscores db exists. make sure this is exactly what you want to do.")
        return;
    }
    createDB();

    const db = require("./db");
    for (var i = 0; i < 1000; i++) {
        const prestige = getRandomInt(11);
        const skills = randSkills(prestige);
        const kcs = randKillcounts();
        let player = {
            username: randUsername(),
            "staff-rights": "PLAYER",
            "game-mode": randGameMode(),
            prestige: prestige,
            killcounts: kcs,
            skills: skills,
            statistics: {
                total_xp: getTotalXP(skills),
                total_boss_kills: getTotalKCs(kcs)
            }
        };
        for (let j = 2; j < constants.STATS.length; j++) {
            player.statistics[constants.STATS[j]] = getRandomInt(20000);
        }
        player = db.mapToDbObject(player);
        db.createPlayer(player);
    }

}

module.exports = { createAndPopulateDBTest };