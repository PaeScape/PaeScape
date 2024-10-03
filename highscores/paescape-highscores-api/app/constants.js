module.exports = {
  PLAYERS_PER_PAGE: 25,
  MAX_EXP: 1_000_000_000, // 1 billion
  MAX_LEVEL: 120,
  SKILLS: [
    "attack",
    "defence",
    "strength",
    "constitution",
    "ranged",
    "prayer",
    "magic",
    "cooking",
    "woodcutting",
    "fletching",
    "fishing",
    "firemaking",
    "crafting",
    "smithing",
    "mining",
    "herblore",
    "agility",
    "thieving",
    "slayer",
    "farming",
    "runecrafting",
    "skiller",
    "hunter",
    "summoning",
    "dungeoneering",
  ],
  SKILLS_IDX: {
    attack: 0,
    defence: 1,
    strength: 2,
    constitution: 3,
    ranged: 4,
    prayer: 5,
    magic: 6,
    cooking: 7,
    woodcutting: 8,
    fletching: 9,
    fishing: 10,
    firemaking: 11,
    crafting: 12,
    smithing: 13,
    mining: 14,
    herblore: 15,
    agility: 16,
    thieving: 17,
    slayer: 18,
    farming: 19,
    runecrafting: 20,
    skiller: 21,
    hunter: 22,
    summoning: 23,
    dungeoneering: 24,
  },
  STAT_FROM_KC: {
    "clue_scrolls_easy": "-9",
    "clue_scrolls_medium": "-10",
    "clue_scrolls_hard": "-11",
    "clue_scrolls_elite": "-12",
    "clue_scrolls_master": "-13",
  },
  STATS: [
    "total_xp",
    "total_boss_kills",
    "crystal_chests",
    "rare_candies",
    "slayer_tasks",
    "skiller_tasks",
    "wilderness_kills",
    "wilderness_deaths",
    "clue_scrolls_easy",
    "clue_scrolls_medium",
    "clue_scrolls_hard",
    "clue_scrolls_elite",
    "clue_scrolls_master",
    "bonus_prestiges",
    "completed_clogs",
    "achievement_points"
  ],
  STATS_IDX: {
    "total_xp": 0,
    "total_boss_kills": 1,
    "crystal_chests": 2,
    "rare_candies": 3,
    "slayer_tasks": 4,
    "skiller_tasks": 5,
    "wilderness_kills": 6,
    "wilderness_deaths": 7,
    "clue_scrolls_easy": 8,
    "clue_scrolls_medium": 9,
    "clue_scrolls_hard": 10,
    "clue_scrolls_elite": 11,
    "clue_scrolls_master": 12,
    "bonus_prestiges": 13,
    "completed_clogs": 14,
    "achievement_points": 15
  },
  KILLCOUNTS_GS_TO_DB: {
    "-1": "cox", //raids
    "-2": "tob",
    "-4": "gwdraid",
    "-5": "chaos",
    "-3": "barrows",
    2881: "dagannoth_supreme", //bosses
    2882: "dagannoth_prime",
    2883: "dagannoth_rex",
    50: "king_black_dragon",
    1999: "cerberus",
    6203: "kril_tsutsaroth",
    6222: "kreearra",
    6247: "commander_zilyana",
    6260: "general_graardor",
    8133: "corporeal_beast",
    5886: "abyssal_sire",
    8349: "tormented_demon",
    2044: "zulrah",
    1159: "kalphite_queen",
    4540: "glacio",
    3334: "wildywyrm",
    3340: "giant_mole",
    499: "thermonuclear_smoke_devil",
    23425: "nightmare",
    2745: "tztok_jad",
    "-22": "superior_slayer",
    2000: "venenatis", //wildy
    3200: "chaos_elemental",
    2001: "scorpia",
    2006: "vetion",
    2009: "callisto",
    6691: "revenant_dark_beast",
    6725: "revenant_ork",
    6701: "revenant_werewolf",
    6716: "revenant_goblin",
    6715: "revenant_impling",
    22061: "vorkath"
  },
  KILLCOUNTS: [
    "cox", //raids
    "tob",
    "gwdraid",
    "chaos",
    "barrows",
    "dagannoth_supreme", //bosses
    "dagannoth_prime",
    "dagannoth_rex",
    "king_black_dragon",
    "cerberus",
    "kril_tsutsaroth",
    "kreearra",
    "commander_zilyana",
    "general_graardor",
    "corporeal_beast",
    "abyssal_sire",
    "tormented_demon",
    "zulrah",
    "kalphite_queen",
    "glacio",
    "wildywyrm",
    "giant_mole",
    "thermonuclear_smoke_devil",
    "nightmare",
    "venenatis", //wildy
    "chaos_elemental",
    "scorpia",
    "vetion",
    "callisto",
    "revenant_dark_beast",
    "revenant_ork",
    "revenant_werewolf",
    "revenant_goblin",
    "revenant_impling",
    "vorkath",
    "superior_slayer",
    "tztok_jad"
  ],
  KILLCOUNTS_IDX: {
    cox: 0, //raids
    tob: 1,
    gwdraid: 2,
    chaos: 3,
    barrows: 4,
    dagannoth_supreme: 5, //bosses
    dagannoth_prime: 6,
    dagannoth_rex: 7,
    king_black_dragon: 8,
    cerberus: 9,
    kril_tsutsaroth: 10,
    kreearra: 11,
    commander_zilyana: 12,
    general_graardor: 13,
    corporeal_beast: 14,
    abyssal_sire: 15,
    tormented_demon: 16,
    zulrah: 17,
    kalphite_queen: 18,
    glacio: 19,
    wildywyrm: 20,
    giant_mole: 21,
    thermonuclear_smoke_devil: 22,
    nightmare: 23,
    venenatis: 24, //wildy
    chaos_elemental: 25,
    scorpia: 26,
    vetion: 27,
    callisto: 28,
    revenant_dark_beast: 29,
    revenant_ork: 30,
    revenant_werewolf: 31,
    revenant_goblin: 32,
    revenant_impling: 33,
    vorkath: 34,
    superior_slayer: 35,
    tztok_jad: 36
  },
};
