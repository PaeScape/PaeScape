package online.paescape.cache.media.interfaces;

import online.paescape.Client;
import online.paescape.cache.CacheArchive;
import online.paescape.cache.def.ItemDef;
import online.paescape.cache.def.NPCDef;
import online.paescape.cache.media.TextDrawingArea;
import online.paescape.cache.media.sprite.Sprite;
import online.paescape.cache.media.sprite.SpriteLoader;
import online.paescape.collection.MemCache;
import online.paescape.collection.QueueNode;
import online.paescape.media.DrawingArea;
import online.paescape.media.FrameReader;
import online.paescape.media.animable.Model;
import online.paescape.media.animable.entity.Player;
import online.paescape.net.Stream;
import online.paescape.util.Configuration;
import online.paescape.util.DataType;
import online.paescape.util.Skills;
import online.paescape.util.TextClass;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("all")
public class RSInterface {

    public static final int WRAPPED_TEXT = 19; //type
    public final static int GOLD_CHARM = 12158, GREEN_CHARM = 12159, CRIMSON_CHARM = 12160, ABYSSAL_CHARM = 12161, TALON_BEAST_CHARM = 12162, BLUE_CHARM = 12163, RAVAGER_CHARM = 12164, SHIFTER_CHARM = 12165, SPINNER_CHARM = 12166, TORCHER_CHARM = 12167, OBSIDIAN_CHARM = 12168;
    public static final int SHARD_ID = 18016;
    public static final int POUCH_ID = 12155;
    private final static int MEDIUM_BUTTON = 674;
    private static final int WHITE_TEXT = 0xFFFFFF;
    private static final int GREY_TEXT = 0xB9B855;
    private static final int ORANGE_TEXT = 0xFF981F;
    private static final int CLOSE_BUTTON = 141, CLOSE_BUTTON_HOVER = 142;
    private static final int DRAW_REGULAR_MODEL = 1, DRAW_NPC_MODEL = 2, DRAW_PLAYER_MODEL = 3, DRAW_ITEM_MODEL = 4;
    private static final MemCache modelCache = new MemCache(30);
    /*
     * /* Start of 602 Skill Tab
     */
    public static TextDrawingArea[] fonts;
    public static int[] summoningLevelRequirements = {1, 4, 10, 13, 16, 17, 18, 19, 22, 23, 25, 28, 29, 31, 32, 33, 34, 34, 34, 34, 36, 40, 41, 42, 43, 43, 43, 43, 43, 43, 43, 46, 46, 47, 49, 52, 54, 55, 56, 56, 57, 57, 57, 58, 61, 62, 63, 64, 66, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 76, 77, 78, 79, 79, 79, 80, 83, 83, 85, 86, 88, 89, 92, 93, 95, 96, 99};
    public static int[] pouchItems = {12047,// Spirit_wolf_pouch
            12043,// Dreadfowl_pouch
            12059,// Spirit_spider_pouch
            12019,// Thorny_snail_pouch
            12009,// Granite_crab_pouch
            12778,// Spirit_mosquito_pouch
            12049,// Desert_wyrm_pouch
            12055,// Spirit_scorpion_pouch
            12808,// Spirit_tz-kih_pouch
            12067,// Albino_rat_pouch
            12063,// Spirit_kalphite_pouch
            12091,// Compost_mound_pouch
            12800,// Giant_chinchompa_pouch
            12053,// Vampire_bat_pouch
            12065,// Honey_badger_pouch
            12021,// Beaver_pouch
            12818,// Void_ravager_pouch
            12780,// Void_spinner_pouch
            12798,// Void_torcher_pouch
            12814,// Void_shifter_pouch
            12073,// Bronze_minotaur_pouch
            12087,// Bull_ant_pouch
            12071,// Macaw_pouch
            12051,// Evil_turnip_pouch
            12095,// Sp._cockatrice_pouch
            12097,// Sp._guthatrice_pouch
            12099,// Sp._saratrice_pouch
            12101,// Sp._zamatrice_pouch
            12103,// Sp._pengatrice_pouch
            12105,// Sp._coraxatrice_pouch
            12107,// Sp._vulatrice_pouch
            12075,// Iron_minotaur_pouch
            12816,// Pyrelord_pouch
            12041,// Magpie_pouch
            12061,// Bloated_leech_pouch
            12007,// Spirit_terrorbird_pouch
            12035,// Abyssal_parasite_pouch
            12027,// Spirit_jelly_pouch
            12077,// Steel_minotaur_pouch
            12531,// Ibis_pouch
            12810,// Spirit_graahk_pouch
            12812,// Spirit_kyatt_pouch
            12784,// Spirit_larupia_pouch
            12023,// Karam._overlord_pouch
            12085,// Smoke_devil_pouch
            12037,// Abyssal_lurker_pouch
            12015,// Spirit_cobra_pouch
            12045,// Stranger_plant_pouch
            12079,// Mithril_minotaur_pouch
            12123,// Barker_toad_pouch
            12031,// War_tortoise_pouch
            12029,// Bunyip_pouch
            12033,// Fruit_bat_pouch
            12820,// Ravenous_locust_pouch
            12057,// Arctic_bear_pouch
            14623,// Phoenix_pouch
            12792,// Obsidian_golem_pouch
            12069,// Granite_lobster_pouch
            12011,// Praying_mantis_pouch
            12081,// Adamant_minotaur_pouch
            12782,// Forge_regent_pouch
            12794,// Talon_beast_pouch
            12013,// Giant_ent_pouch
            12802,// Fire_titan_pouch
            12804,// Moss_titan_pouch
            12806,// Ice_titan_pouch
            12025,// Hydra_pouch
            12017,// Spirit_dagannoth_pouch
            12788,// Lava_titan_pouch
            12776,// Swamp_titan_pouch
            12083,// Rune_minotaur_pouch
            12039,// Unicorn_stallion_pouch
            12786,// Geyser_titan_pouch
            12089,// Wolpertinger_pouch
            12796,// Abyssal_titan_pouch
            12822,// Iron_titan_pouch
            12093,// Pack_yak_pouch
            12790,// Steel_titan_pouch
    };
    public static int[] scrollItems = {12425,// Howl_scroll
            12445,// Dreadfowl_strike_scroll
            12428,// Egg_spawn_scroll
            12459,// Slime_spray_scroll
            12533,// Stony_shell_scroll
            12838,// Pester_scroll
            12460,// Electric_lash_scroll
            12432,// Venom_shot_scroll
            12839,// Fireball_assault_scroll
            12430,// Cheese_feast_scroll
            12446,// Sandstorm_scroll
            12440,// Generate_compost_scroll
            12834,// Explode_scroll
            12447,// Vampire_touch_scroll
            12433,// Insane_ferocity_scroll
            12429,// Multichop_scroll
            12443,// Call_to_arms_scroll
            12443,// Call_to_arms_scroll
            12443,// Call_to_arms_scroll
            12443,// Call_to_arms_scroll
            12461,// Bronze_bull_rush_scroll
            12431,// Unburden_scroll
            12422,// Herbcall_scroll
            12448,// Evil_flames_scroll
            12458,// Petrifying_gaze_scroll
            12458,// Petrifying_gaze_scroll
            12458,// Petrifying_gaze_scroll
            12458,// Petrifying_gaze_scroll
            12458,// Petrifying_gaze_scroll
            12458,// Petrifying_gaze_scroll
            12458,// Petrifying_gaze_scroll
            12462,// Iron_bull_rush_scroll
            12829,// Immense_heat_scroll
            12426,// Thieving_fingers_scroll
            12444,// Blood_drain_scroll
            12441,// Tireless_run_scroll
            12454,// Abyssal_drain_scroll
            12453,// Dissolve_scroll,//,//
            12463,// Steel_bull_rush_scroll
            12424,// Fish_rain_scroll
            12835,// Goad_scroll
            12836,// Ambush_scroll
            12840,// Rending_scroll
            12455,// Doomsphere_scroll
            12468,// Dust_cloud_scroll
            12427,// Abyssal_stealth_scroll
            12436,// Oph._incubation_scroll
            12467,// Poisonous_blast_scroll
            12464,// Mith_bull_rush_scroll
            12452,// Toad_bark_scroll
            12439,// Testudo_scroll
            12438,// Swallow_whole_scroll
            12423,// Fruitfall_scroll
            12830,// Famine_scroll
            12451,// Arctic_blast_scroll
            14622,// Rise_from_the_ashes_scroll
            12826,// Volcanic_str._scroll
            12449,// Crushing_claw_scroll
            12450,// Mantis_strike_scroll
            12465,// Addy_bull_rush_scroll
            12841,// Inferno_scroll
            12831,// Deadly_claw_scroll
            12457,// Acorn_missile_scroll
            12824,// Titan's_con._scroll
            12824,// Titan's_con._scroll
            12824,// Titan's_con._scroll
            12442,// Regrowth_scroll
            12456,// Spike_shot_scroll
            12837,// Ebon_thunder_scroll
            12832,// Swamp_plague_scroll
            12466,// Rune_bull_rush_scroll
            12434,// Healing_aura_scroll
            12833,// Boil_scroll
            12437,// Magic_focus_scroll
            12827,// Essence_shipment_scroll
            12828,// Iron_within_scroll
            12435,// Winter_storage_scroll
            12825,// Steel_of_legends_scroll
    };
    public static String[] scrollNames = {"Howl", "Dreadfowl Strike", "Egg Spawn", "Slime Spray", "Stony Shell", "Pester", "Electric Lash", "Venom Shot", "Fireball Assault", "Cheese Feast", "Sandstorm", "Generate Compost", "Explode", "Vampire Touch", "Insane Ferocity", "Multichop", "Call of Arms", "Call of Arms", "Call of Arms", "Call of Arms", "Bronze Bull Rush", "Unburden", "Herbcall", "Evil Flames", "Petrifying gaze", "Petrifying gaze", "Petrifying gaze", "Petrifying gaze", "Petrifying gaze", "Petrifying gaze", "Petrifying gaze", "Iron Bull Rush", "Immense Heat", "Thieving Fingers", "Blood Drain", "Tireless Run", "Abyssal Drain", "Dissolve", "Steel Bull Rush", "Fish Rain", "Goad", "Ambush", "Rending", "Doomsphere Device", "Dust Cloud", "Abyssal Stealth", "Ophidian Incubation", "Poisonous Blast", "Mithril Bull Rush", "Toad Bark", "Testudo", "Swallow Whole", "Fruitfall", "Famine", "Arctic Blast", "Rise from the Ashes", "Volcanic Strength", "Crushing Claw", "Mantis Strike", "Adamant Bull Rush", "Inferno", "Deadly Claw", "Acorn Missile", "Titan's Consitution", "Titan's Consitution", "Titan's Consitution", "Regrowth", "Spike Shot", "Ebon Thunder", "Swamp Plague", "Rune Bull Rush", "Healing Aura", "Boil", "Magic Focus", "Essence Shipment", "Iron Within", "Winter Storage", "Steel of Legends",};
    public static String[] pouchNames = {"Spirit wolf", "Dreadfowl", "Spirit spider", "Thorny snail", "Granite crab", "Spirit mosquito", "Desert wyrm", "Spirit scorpion", "Spirit tz-kih", "Albino rat", "Spirit kalphite", "Compost mound", "Giant chinchompa", "Vampire bat", "Honey badger", "Beaver", "Void ravager", "Void spinner", "Void torcher", "Void shifter", "Bronze minotaur", "Bull ant", "Macaw", "Evil turnip", "Sp. cockatrice", "Sp. guthatrice", "Sp. saratrice", "Sp. zamatrice", "Sp. pengatrice", "Sp. coraxatrice", "Sp. vulatrice", "Iron minotaur", "Pyrelord", "Magpie", "Bloated leech", "Spirit terrorbird", "Abyssal parasite", "Spirit jelly", "Steel minotaur", "Ibis", "Spirit graahk", "Spirit kyatt", "Spirit larupia", "Karam. overlord", "Smoke devil", "Abyssal lurker", "Spirit cobra", "Stranger plant", "Mithril minotaur", "Barker toad", "War tortoise", "Bunyip", "Fruit bat", "Ravenous locust", "Arctic bear", "Phoenix", "Obsidian golem", "Granite lobster", "Praying mantis", "Adamant minotaur", "Forge regent", "Talon beast", "Giant ent", "Fire titan", "Moss titan", "Ice titan", "Hydra", "Spirit dagannoth", "Lava titan", "Swamp titan", "Rune minotaur", "Unicorn stallion", "Geyser titan", "Wolpertinger", "Abyssal titan", "Iron titan", "Pack yak", "Steel titan",};
    public static CacheArchive cacheArchive;
    public static RSInterface interfaceCache[];
    public static int summoningItemRequirements[][] = {{12158, 2859, -1}, // Wolf pouch
            {12158, 2138, -1}, // Dreadfowl pouch
            {12158, 6291, -1}, // Spider pouch
            {12158, 3363, -1}, // Snail pouch
            {12158, 440, -1}, // Granite pouch
            {12158, 6319, -1}, // Spirit mosquito pouch
            {12159, 1783, -1}, // Wyrm pouch
            {12160, 3095, -1}, // Scorpion pouch
            {12160, 12168, -1}, // Tz-kih pouch
            {12163, 2134, -1}, // Albino rat pouch
            {12163, 3138, -1}, // Spirit Kalphite pouch
            {12159, 6032, -1}, // Compost Mound pouch
            {12163, 9976, -1}, // Giant Chinchompa pouch
            {12160, 3325, -1}, // Vampyre Bat pouch
            {12160, 12156, -1}, // Honey Badger pouch
            {12159, 1519, -1}, // Beaver pouch
            {12159, 12164, -1}, // Void Ravager pouch
            {12163, 12166, -1}, // Void Spinner pouch
            {12163, 12167, -1}, // Void Torcher pouch
            {12163, 12165, -1}, // Void Shifter pouch
            {12163, 2349, -1}, // Bronze Minotaur pouch
            {12158, 6010, -1}, // Bull Ant pouch
            {12159, 249, -1}, // Macaw pouch
            {12160, 12153, -1}, // Evil Turnip pouch
            {12159, 12109, -1}, // Spirit/Sp. Cockatrice pouch
            {12159, 12111, -1}, // Spirit/Sp. Guthatrice pouch
            {12159, 12113, -1}, // Spirit/Sp. Saratrice pouch
            {12159, 12115, -1}, // Spirit/Sp. Zamatrice pouch
            {12159, 12117, -1}, // Spirit/Sp. Pengatrice pouch
            {12159, 12119, -1}, // Spirit/Sp. Coraxatrice pouch
            {12159, 12121, -1}, // Spirit/Sp. Vulatrice pouch
            {12163, 2351, -1}, // Iron Minotaur pouch
            {12160, 590, -1}, // Pyrelord pouch
            {12159, 1635, -1}, // Magpie pouch
            {12160, 2132, -1}, // Bloated Leech pouch
            {12158, 9978, -1}, // Spirit Terrorbird pouch
            {12159, 12161, -1}, // Abysaal Parasite pouch
            {12163, 1937, -1}, // Spirit Jelly pouch
            {12163, 2353, -1}, // Steel Minotaur pouch
            {12159, 311, -1}, // Ibis pouch
            {12163, 10099, -1}, // Spirit Graahk pouch
            {12163, 10103, -1}, // Spirit Kyatt pouch
            {12163, 10095, -1}, // Spirit Larupia pouch
            {12163, 6667, -1}, // Karamthuhlu/Karam. Overlord pouch
            {12160, 9736, -1}, // Smoke Devil pouch
            {12159, 12161, -1}, // Abyssal Lurker pouch
            {12160, 6287, -1}, // Spirit Cobra pouch
            {12160, 8431, -1}, // Stranger Plant pouch
            {12163, 2359, -1}, // Mithril Minotaur pouch
            {12158, 2150, -1}, // Barker Toad pouch
            {12158, 7939, -1}, // War Tortoise pouch
            {12159, 383, -1}, // Bunnyip pouch
            {12159, 1963, -1}, // Fruit Bat Pouch
            {12160, 1933, -1}, // Ravenous Locust pouch
            {12158, 10117, -1}, // Arctic Bear pouch
            {12160, 14616, -1}, // Phoenix pouch
            {12163, 12168, -1}, // Obsidian Golem pouch
            {12160, 6979, -1}, // Granite Lobster pouch
            {12160, 2460, -1}, // Praying Mantis pouch
            {12163, 2361, -1}, // Adamant Minotaur pouch
            {12159, 10020, -1}, // Forge Regent pouch
            {12160, 12162, -1}, // Talon Beast pouch
            {12159, 5933, -1}, // Giant Ent pouch
            {12163, 1442, -1}, // Fire Titan pouch
            {12163, 1440, -1}, // Moss Titan pouch
            {12163, 1438, 1444}, // Ice Titan pouch
            {12159, 571, -1}, // Hydra pouch
            {12160, 6155, -1}, // Spirit Dagannoth pouch
            {12163, 12168, -1}, // Lava titan pouch
            {12160, 10149, -1}, // Swamp titan pouch
            {12163, 2363, -1}, // Rune minotaur pouch
            {12159, 237, -1}, // Unicorn stallion pouch
            {12163, 1444, -1}, // Geyser titan pouch
            {12160, 3226, 2859}, // Wolpertinger pouch
            {12159, 12161, -1}, // Abyssal titan pouch
            {12160, 1115, -1}, // Iron titan pouch
            {12160, 10818, -1}, // Pack yak pouch
            {12163, 1119, -1}, // Steel titan pouch
    };
    public static int summoningItemAmountRequirements[][] = { // 23471
            {1, 1, 0}, // Wolf pouch
            {1, 1, 0}, // Dreadfowl pouch
            {1, 1, 0}, // Spider pouch
            {1, 1, 0}, // Snail pouch
            {1, 1, 0}, // Granite pouch
            {1, 1, 0}, // Spirit mosquito pouch
            {1, 1, 0}, // Wyrm mosquito pouch
            {1, 1, 0}, // Scorpion pouch
            {1, 1, 0}, // Tz-kih pouch
            {1, 1, 0}, // Albino rat pouch
            {1, 1, 0}, // Spirit Kalphite pouch
            {1, 1, 0}, // Compost Mound pouch
            {1, 1, 0}, // Giant Chinchompa pouch
            {1, 1, 0}, // Vampyre Bat pouch
            {1, 1, 0}, // Honey Badger pouch
            {1, 1, 0}, // Beaver pouch
            {1, 1, 0}, // Void Ravager pouch
            {1, 1, 0}, // Void Spinner pouch
            {1, 1, 0}, // Void Torcher pouch
            {1, 1, 0}, // Void Shifter pouch
            {1, 1, 0}, // Bronze Minotaur pouch
            {1, 1, 0}, // Bull Ant pouch
            {1, 1, 0}, // Macaw pouch
            {1, 1, 0}, // Evil Turnip pouch
            {1, 1, 0}, // Spirit/Sp. Cockatrice pouch
            {1, 1, 0}, // Spirit/Sp. Guthatrice pouch
            {1, 1, 0}, // Spirit/Sp. Saratrice pouch
            {1, 1, 0}, // Spirit/Sp. Zamatrice pouch
            {1, 1, 0}, // Spirit/Sp. Pengatrice pouch
            {1, 1, 0}, // Spirit/Sp. Coraxatrice pouch
            {1, 1, 0}, // Spirit/Sp. Vulatrice pouch
            {1, 1, 0}, // Iron Minotaur pouch
            {1, 1, 0}, // Pyrelord pouch
            {1, 1, 0}, // Magpie pouch
            {1, 1, 0}, // Bloated Leech pouch
            {1, 1, 0}, // Spirit Terrorbird pouch
            {1, 1, 0}, // Abysaal Parasite pouch
            {1, 1, 0}, // Spirit Jelly pouch
            {1, 1, 0}, // Steel Minotaur pouch
            {1, 1, 0}, // Ibis pouch
            {1, 1, 0}, // Spirit Graahk pouch
            {1, 1, 0}, // Spirit Kyatt pouch
            {1, 1, 0}, // Spirit Larupia pouch
            {1, 1, 0}, // Karamthuhlu/Karam. Overlord pouch
            {1, 1, 0}, // Smoke Devil pouch
            {1, 1, 0}, // Abyssal Lurker pouch
            {1, 1, 0}, // Spirit Cobra pouch
            {1, 1, 0}, // Stranger Plant pouch
            {1, 1, 0}, // Mithril Minotaur pouch
            {1, 1, 0}, // Barker Toad pouch
            {1, 1, 0}, // War Tortoise pouch
            {1, 1, 0}, // Bunnyip pouch
            {1, 1, 0}, // Bunnyip pouch
            {1, 1, 0}, // Ravenous Locust pouch
            {1, 1, 0}, // Arctic Bear pouch
            {1, 1, 0}, // Phoenix pouch
            {1, 1, 0}, // Obsidian Golem pouch
            {1, 1, 0}, // Granite Lobster pouch
            {1, 1, 0}, // Praying Mantis pouch
            {1, 1, 0}, // Adamant Minotaur pouch
            {1, 1, 0}, // Forge Regent pouch
            {1, 1, 0}, // Talon Beast pouch
            {1, 1, 0}, // Giant Ent pouch
            {1, 1, 0}, // Fire Titan pouch
            {1, 1, 0}, // Moss Titan pouch
            {1, 1, 1}, // Ice Titan pouch
            {1, 1, 0}, // Hydra pouch
            {1, 1, 0}, // Spirit dagannoth pouch
            {1, 1, 0}, // Lava titan pouch
            {1, 1, 0}, // Swamp titan pouch
            {1, 1, 0}, // Rune minotaur pouch
            {1, 1, 0}, // Unicorn stallion titan pouch
            {1, 1, 0}, // Geyser titan pouch
            {1, 1, 1}, // Wolpertinger pouch
            {1, 1, 0}, // Abyssal titan pouch
            {1, 1, 0}, // Iron titan pouch
            {1, 1, 0}, // Pack yak pouch
            {1, 1, 0}, // Steel titan pouch
    };
    public static int shards[][] = {
            // AMOUNT OF SHARDS, X COORD, Y COORD
            {7, 75, 40}, // Wolf pouch
            {8, 127, 40}, // Dreadfowl pouch
            {8, 180, 40}, // Spider pouch
            {9, 233, 40}, // Snail pouch
            {7, 287, 40}, // Granite pouch
            {1, 342, 40}, // Mosquito pouch
            {45, 389, 40}, // Wyrm pouch
            {57, 441, 40}, // Scorpion pouch
            {64, 70, 97}, // Tz-kih pouch
            {75, 124, 97}, // Albino rat pouch
            {51, 176, 97}, // Spirit Kalphite pouch
            {47, 229, 97}, // Compost Mound pouch
            {84, 282, 97}, // Giant Chinchompa pouch
            {81, 335, 97}, // Vampyre Bat pouch
            {84, 388, 97}, // Honey Badger pouch
            {72, 441, 97}, // Beaver pouch
            {74, 71, 154}, // Void Ravager pouch
            {74, 124, 154}, // Void Spinner pouch
            {74, 177, 154}, // Void Torcher pouch
            {74, 230, 154}, // Void Shifter pouch
            {102, 277, 154}, // Bronze Minotaur pouch
            {11, 336, 154}, // Bull Ant pouch
            {78, 389, 154}, // Macaw pouch
            {104, 436, 154}, // Evil Turnip pouch
            {88, 68, 211}, // Spirit/Sp. Cockatrice pouch
            {88, 121, 211}, // Spirit/Sp. Guthatrice pouch
            {88, 174, 211}, // Spirit/Sp. Saratrice pouch
            {88, 227, 211}, // Spirit/Sp. Zamatrice pouch
            {88, 280, 211}, // Spirit/Sp. Pengatrice pouch
            {88, 333, 211}, // Spirit/Sp. Coraxatrice pouch
            {88, 386, 211}, // Spirit/Sp. Vulatrice pouch
            {125, 435, 211}, // Iron Minotaur pouch
            {111, 69, 268}, // Pyrelord pouch
            {88, 122, 268}, // Magpie pouch
            {117, 175, 268}, // Bloated Leech pouch
            {12, 229, 268}, // Spirit Terrorbird pouch
            {106, 276, 268}, // Abysaal Parasite pouch
            {151, 332, 268}, // Spirit Jelly pouch
            {141, 387, 268}, // Steel Minotaur pouch
            {109, 435, 268}, // Ibis pouch
            {154, 67, 325}, // Spirit Graahk pouch
            {153, 119, 325}, // Spirit Kyatt pouch
            {155, 171, 325}, // Spirit Larupia pouch
            {144, 227, 325}, // Karamthuhlu/Karam. Overlord pouch
            {141, 280, 325}, // Smoke Devil pouch
            {119, 333, 325}, // Abyssal Lurker pouch
            {116, 386, 325}, // Spirit Cobra pouch
            {128, 436, 325}, // Stranger Plant pouch
            {152, 66, 382}, // Mithril Minotaur pouch
            {11, 125, 382}, // Barker Toad pouch
            {1, 181, 382}, // War Tortoise pouch
            {110, 225, 382}, // Bunnyip pouch
            {130, 278, 382}, // Fruit Bat pouch
            {79, 334, 382}, // Ravenous Locust pouch
            {14, 388, 382}, // Arctic Bear pouch
            {165, 437, 382}, // Phoenix pouch

            {195, 66, 439}, // Obsidian Golem pouch
            {166, 118, 439}, // Granite Lobster pouch
            {168, 170, 439}, // Praying Mantis pouch
            {144, 225, 439}, // Adamant Minotaur pouch
            {141, 278, 439}, // Forge Regent pouch
            {174, 331, 439}, // Talon Beast pouch
            {124, 384, 439}, // Giant Ent pouch
            {198, 436, 439}, // Fire Titan pouch
            {202, 62, 496}, // Moss Titan pouc
            {198, 116, 496}, // Ice Titan pouch
            {128, 168, 496}, // Hydra pouch
            {1, 235, 496}, // Spirit dagannoth pouch
            {219, 276, 496}, // Lava titan pouch
            {150, 330, 496}, // Swamp titan pouch
            {1, 393, 496}, // Rune minotaur pouch
            {140, 436, 496}, // Unicorn stallion pouch
            {222, 62, 553}, // Geyser titan pouch
            {203, 116, 553}, // Wolpertinger pouch
            {113, 168, 553}, // Abyssal titan pouch
            {198, 225, 553}, // Iron titan pouch
            {211, 276, 553}, // Pack yak pouch
            {178, 330, 553}, // Steel titan pouch
    };
    public static RSInterface[] spells = new RSInterface[6];
    public static int currentInputFieldId;
    public static int collectionLogItems;
    public static int marketPlaceInv, marketPlaceShop;
    public static int marketPlaceShop2;
    public static int marketPlaceShop3;

    /*
     * Normal Magic Spellbook Configs
     * @author Levi Patton
     */
    public static int DeathsCoffersShop;
    static int[] req = {1, 2, 3, 4};
    private static MemCache spriteCache;
    public boolean superShadow;
    public int viewableColumns;
    public String popupString;
    public int transparency;
    public boolean advancedSprite = false;
    public int summonReq;
    public String hoverText;
    public int enabledSpriteId, disabledSpriteId;
    public boolean drawsTransparent;
    public Sprite disabledSprite;
    public byte transparancy;
    public int frameTimer;
    public Sprite sprites[];
    public int requiredValues[];
    public int contentType;
    public int spritesX[];
    public int disabledMouseOverColor;
    public int atActionType;
    public String spellName;
    public int enabledColor;
    public int width;
    public int hoverType;
    public int itemSpriteId1;
    public int itemSpriteId2;
    public int itemSpriteZoom1;
    public int itemSpriteZoom2;
    public int itemSpriteIndex;
    public String tooltip;
    public String selectedActionName;
    public boolean centerText;
    public int scrollPosition;
    public String actions[];
    public int valueIndexArray[][];
    public boolean filled;
    public String enabledMessage;
    public int invSpritePadX;
    public int disabledColor;
    public int mediaType;
    public int mediaID;
    public boolean dragDeletes;
    public int parentID;
    public int spellUsableOn;
    public int enabledMouseOverColor;
    public Sprite savedSprite[] = new Sprite[10];
    public int children[];
    public int childX[];
    public boolean usableItemInterface;
    public TextDrawingArea textDrawingAreas;
    public int invSpritePadY;
    public int valueCompareType[];
    public int currentFrame;
    public int spritesY[];
    public String message;
    public boolean isInventoryInterface;
    public int x, y;
    public int id;
    public int invStackSizes[];
    public int inv[];
    public byte opacity;
    public int childToIntersect = 0;
    public int childToIntersect2 = 0;
    public int childToIntersect3 = 0;
    public int positionX, positionY;
    /*
     * Quest tab [PLAYER PANEL]
     */
    /*
     * Drop log Configurations
     * @author levi patton
     * @www.rune-server.org/members/auguryps
     * @param tda
     */
    public int customOpacity = 0;
    public int disabledAnimationId;
    public Sprite displayedSprite;
    public int enabledAnimationId;
    public boolean deleteOnDrag2;
    public Sprite enabledSprite;
    public int scrollMax;
    public int type;
    public int xOffset;
    public int yOffset;
    public boolean hidden;
    public int height;
    public boolean shadowed;
    public int modelZoom;
    public int modelRotation1;
    public int modelRotation2;
    public int childY[];
    public boolean inventoryHover;
    public boolean greyScale;
    public boolean drawInfinity;
    public int[] togglers;
    public boolean toggled = false;
    public boolean isToggler = false;
    public Sprite sprite1;
    public Sprite sprite2;
    public int characterLimit;
    public boolean isInFocus;
    public int entityDisplay;
    public int entityAnim;
    boolean displayAsterisks;
    private int plrJaw = 0, gender = 0;
    private int enabledMediaType;
    private int enabledMediaID;

    public RSInterface() {
        enabledSpriteId = disabledSpriteId = -1;
    }

    /**
     * Adds newlines to a text for a certain TextDrawingArea so each line is never longer than width.
     *
     * @param tda   The textDrawing Area for the text, basically the font
     * @param text  The text to convert to wrapped text
     * @param width The width above which wrapping is applied
     * @return The wrapped text
     */
    public static String getWrappedText(TextDrawingArea tda, String text, int width) {
        if (text.contains("\\n") || tda.getTextWidth(text) <= width) {
            return text;
        }
        int spaceWidth = tda.getTextWidth(" ");
        StringBuilder result = new StringBuilder(text.length());
        StringBuilder line = new StringBuilder();
        int lineLength = 0;
        int curIndex = 0;
        while (true) {
            int spaceIndex = text.indexOf(' ', curIndex);
            int newLength = lineLength;
            boolean last = false;
            String curWord;
            if (spaceIndex < 0) {
                last = true;
                curWord = text.substring(curIndex);
            } else {
                curWord = text.substring(curIndex, spaceIndex);
                newLength += spaceWidth;
            }
            curIndex = spaceIndex + 1;
            int w = tda.getTextWidth(curWord);
            newLength += w;
            if (newLength > width) {
                result.append(line);
                result.append("\\n");
                line = new StringBuilder(curWord);
                line.append(' ');
                lineLength = w;
            } else {
                line.append(curWord);
                line.append(' ');
                lineLength = newLength;
            }
            if (last) {
                break;
            }
        }
        result.append(line);
        return result.toString();
    }

    /**
     * Adds text with the specified properties. Automatically wraps text so it doesn't exceed width.
     * Only use for dynamic interfaces as there is some computation to check if wrapping is required.
     * If static text, use another addText method and pass the text into RSInterface.getWrappedText() firstly.
     *
     * @param id         The child id for the text
     * @param text       The text message
     * @param tda        The tdas available
     * @param idx        The index of the tda to use
     * @param color      The text color
     * @param center     Whether the text is centered
     * @param shadow     Whether the text has shadow
     * @param width      The maximum width of each line before wrapping applies
     * @param lineHeight The height between lines
     * @return
     */
    public static RSInterface addWrappingText(int id, String text, TextDrawingArea tda[], int idx, int color, boolean center, boolean shadow, int width, int lineHeight) {
        RSInterface tab = addTabInterface(id);
        tab.parentID = id;
        tab.id = id;
        tab.type = WRAPPED_TEXT;
        tab.atActionType = 0;
        tab.width = width;
        tab.height = lineHeight;
        tab.contentType = 0;
        tab.opacity = 0;
        tab.hoverType = -1;
        tab.centerText = center;
        tab.shadowed = shadow;
        tab.textDrawingAreas = tda[idx];
        tab.message = getWrappedText(tab.textDrawingAreas, text, tab.width);
        tab.enabledMessage = "";
        tab.disabledColor = color;
        tab.enabledColor = 0;
        tab.disabledMouseOverColor = 0;
        tab.enabledMouseOverColor = 0;
        return tab;
    }

    public static void addButtonWithHover(int id, int sid, String tooltip, int w, int h, int hoverId) {
        RSInterface tab = interfaceCache[id] = new RSInterface();
        tab.id = id;
        tab.parentID = id;
        tab.type = 5;
        tab.atActionType = 1;
        tab.contentType = 0;
        tab.opacity = (byte) 0;
        tab.disabledSprite = SpriteLoader.sprites[sid];
        tab.enabledSprite = SpriteLoader.sprites[sid];
        tab.width = w;
        tab.height = h;
        tab.tooltip = tooltip;
        interfaceCache[id].hoverType = hoverId;
    }

    /**
     * Adds text with the specified properties. Automatically wraps text so it doesn't exceed width.
     * Only use for dynamic interfaces as there is some computation to check if wrapping is required.
     * If static text, use another addText method and pass the text into RSInterface.getWrappedText() firstly.
     *
     * @param id     The child id for the text
     * @param text   The text message
     * @param tda    The tdas available
     * @param idx    The index of the tda to use
     * @param color  The text color
     * @param center Whether the text is centered
     * @param shadow Whether the text has shadow
     * @param width  The maximum width of each line before wrapping applies
     * @return
     */
    public static RSInterface addWrappingText(int id, String text, TextDrawingArea tda[], int idx, int color, boolean center, boolean shadow, int width) {
        RSInterface tab = addWrappingText(id, text, tda, idx, color, center, shadow, width, tda[idx].textLineHeight);
        return tab;
    }

    public static void addSingleHoverSprite(int id, int sprite, int width, int height) {
        RSInterface tab = addTabInterface(id);
        tab.id = id;
        tab.type = 5;
        tab.hidden = true;
        tab.contentType = 0;
        tab.width = width;
        tab.height = height;
        tab.enabledSprite = SpriteLoader.sprites[sprite];
        tab.disabledSprite = SpriteLoader.sprites[sprite];
    }

    public static void addSpriteToggleButton(int id, int parentId, int spriteOn, int spriteOff, int width, int height, String tooltip, int actionType, int configFrame, int hoverInt) {
        RSInterface tab = addTabInterface(id);
        tab.parentID = parentId;
        tab.id = id;
        tab.type = 5;
        tab.atActionType = actionType;
        tab.contentType = 0;
        tab.width = width;
        tab.height = height;
        tab.hoverType = hoverInt;
        tab.valueCompareType = new int[1];
        tab.requiredValues = new int[1];
        tab.valueCompareType[0] = 1;
        tab.requiredValues[0] = 0;
        tab.valueIndexArray = new int[1][3];
        tab.valueIndexArray[0][0] = 5;
        tab.valueIndexArray[0][1] = configFrame;
        tab.valueIndexArray[0][2] = 0;
        tab.enabledSprite = SpriteLoader.sprites[spriteOn];
        tab.disabledSprite = SpriteLoader.sprites[spriteOff];
        tab.tooltip = tooltip;
    }

    public static void raids(TextDrawingArea[] tda) {
        RSInterface tab = addInterface(65000);
        int c = 0;
        int x = 0;
        int y = 0;
        tab.totalChildren(11);
        int id = 65001;
        id++;
        tab.child(c++, 45002, 0, 33);
        tab.child(c++, 45003, 0, 36);

        addText(id, "Raiding Party: 0", fonts, 2, 16750623, true, true);
        tab.child(c++, id++, 95 + x, 10 + y);

        tab.child(c++, 65015, 2 + x, 35 + y);

        addHoverButtonWSpriteLoader(id, 1026, 72, 24, "Invite", -1, id + 1, 1);
        tab.child(c++, id++, 19 + x, 233 + y);
        addHoveredImageWSpriteLoader(id, 1027, 72, 24, id + 1);
        tab.child(c++, id++, 19 + x, 233 + y);
        id++;

        addHoverButtonWSpriteLoader(id, 1026, 72, 24, "Leave", -1, id + 1, 1);
        tab.child(c++, id++, 99 + x, 233 + y);
        addHoveredImageWSpriteLoader(id, 1027, 72, 24, id + 1);
        tab.child(c++, id++, 99 + x, 233 + y);
        id++;

        addText(id, "Create", tda, 1, 0xFFA500, true, true);
        tab.child(c++, id++, 55 + x, 238 + y);

        addText(id, "Leave", tda, 1, 0xFFA500, true, true);
        tab.child(c++, id++, 135 + x, 238 + y);

        tab.child(c++, 45002, 0, 225 + y);

        RSInterface scroll = addInterface(65015);

        scroll.totalChildren(48);
        scroll.width = 173;
        scroll.height = 190;
        scroll.scrollMax = 12 * 20;
        y = 0;
        c = 0;
        id = 65016;

        for (int i = 0; i < 12; i++) {

            int color = i % 2 == 0 ? 0x564c42 : 0x483f33;

            RSInterface.addRectangleClickable(id, 135, color, true, 198, 20);
            //RSInterface.interfaceCache[id].hovers = true;
            RSInterface.interfaceCache[id].opacity = 100;
            RSInterface.interfaceCache[id].hoverType = id;
            RSInterface.interfaceCache[id].enabledColor = 0x000000;
            scroll.child(c++, id++, 0, 0 + y);

            addText(id, "", tda, 0, 0xFFA500, false, true);
            scroll.child(c++, id++, 10, 4 + y);

            addText(id, "", tda, 0, 0xFFA500, true, true);
            scroll.child(c++, id++, 100 + x, 4 + y);

            addText(id, "", tda, 0, 0xFFA500, true, true);
            scroll.child(c++, id++, 152 + x, 4 + y);
            y += 20;
        }
    }

    public static void itemGroupAutoScroll(int id, int itemsId, int w, int h, int x, int y, int viewableColumns) {
        RSInterface items = RSInterface.addInterface(id);

        RSInterface rsi = addInterface(itemsId);
        rsi.parentID = id;
        rsi.width = w;
        rsi.height = h;
        rsi.inv = new int[w * h];
        rsi.invStackSizes = new int[w * h];
        rsi.usableItemInterface = false;
        rsi.isInventoryInterface = false;
        rsi.invSpritePadX = x;
        rsi.invSpritePadY = y;
        rsi.spritesX = new int[20];
        rsi.spritesY = new int[20];
        rsi.sprites = new Sprite[20];
        rsi.type = 2;
        rsi.contentType = 6060;
        rsi.viewableColumns = viewableColumns; // reused variable
        int scrollItemAmount = w - viewableColumns;
        rsi.scrollMax = (32 + x) * scrollItemAmount;

        items.width = 32 * viewableColumns + (viewableColumns - 1) * x;

        items.totalChildren(1);
        items.child(0, itemsId, 0, 0);
    }

    public static void addHoverImage2(int id, int spriteOn, int spriteOff, int width, int height, int configFrame) {
        RSInterface tab = addTabInterface(id);
        tab.id = id;
        tab.type = 5;
        tab.hidden = true;
        tab.contentType = 0;
        tab.width = width;
        tab.height = height;
        tab.valueCompareType = new int[1];
        tab.requiredValues = new int[1];
        tab.valueCompareType[0] = 1;
        tab.requiredValues[0] = 0;
        tab.valueIndexArray = new int[1][3];
        tab.valueIndexArray[0][0] = 5;
        tab.valueIndexArray[0][1] = configFrame;
        tab.valueIndexArray[0][2] = 0;
        tab.enabledSprite = SpriteLoader.sprites[spriteOn];
        tab.disabledSprite = SpriteLoader.sprites[spriteOff];
    }

    public static void addConfigButtonWSpriteLoaderWithHover(int ID, int pID, int bID, int bID2, int width, int height, String tT, int configID, int aT, int configFrame, int hoverId) {
        addConfigButtonWSpriteLoader(ID, pID, bID, bID2, width, height, tT, configID, aT, configFrame);
        interfaceCache[ID].hoverType = hoverId;
    }

    public static void addColorBox(int interfaceID, int width, int height, int color) {
        RSInterface rsi = addTabInterface(interfaceID);
        rsi.type = 13;
        rsi.height = height;
        rsi.width = width;
        rsi.enabledColor = color;
    }

    public static void addMaxCape(int interfaceID, int width, int height, int zoom) {
        RSInterface rsi = addTabInterface(interfaceID);
        rsi.type = 6;
        rsi.mediaType = RSInterface.DRAW_REGULAR_MODEL;
        rsi.mediaID = 65300;
        rsi.modelZoom = zoom;
        rsi.modelRotation1 = 0;
        rsi.modelRotation2 = 1020;
        rsi.height = height;
        rsi.width = width;
    }

    public static void Pets(TextDrawingArea atextdrawingarea[]) {
        RSInterface rsinterface = addScreenInterface(3200);
        addSprite(3201, 0, "Pets/Bg");
        addHoverButton(3202, "Pets/Close", 0, 16, 16, "Close", 250, 3203, 3);
        addHoveredButton(3203, "Pets/Close", 1, 16, 16, 20881);
        addHoverButton(3204, "Pets/Pack", 1, 100, 74, "Buy @or2@Rare Package", -1, 3205, 1);
        addHoveredButton(3205, "Pets/Pack", 2, 101, 74, 20882);
        addHoverButton(3206, "Pets/Pack", 3, 100, 74, "Buy @or2@Extreme Package", -1, 3207, 1);
        addHoveredButton(3207, "Pets/Pack", 4, 101, 74, 20883);
        addHoverButton(3208, "Pets/Pack", 5, 100, 74, "Buy @or2@Uncommon Package", -1, 3209, 1);
        addHoveredButton(3209, "Pets/Pack", 6, 101, 74, 20884);
        addHoverButton(3225, "Pets/Pack", 7, 100, 74, "Buy @or2@Common Package", -1, 3226, 1);
        addHoveredButton(3226, "Pets/Pack", 8, 101, 74, 20885);
        addHoverButton(3235, "Pets/Pack", 9, 100, 74, "Value @or2@Legendary Package", -1, 3236, 1);
        addHoveredButton(3236, "Pets/Pack", 10, 101, 74, 20886);
        addText(3210, "PaeScape Point Shop", atextdrawingarea, 3, 0xffcc33, false, true);
        addText(3211, "RP:", atextdrawingarea, 3, 0xffcc33, false, true);
        addHoverButton(3215, "Pets/Pack", 3, 100, 74, "Value @or2@Extreme Package", -1, 3216, 1);
        addHoveredButton(3216, "Pets/Pack", 4, 101, 74, 3217);
        addHoverButton(3218, "Pets/Pack", 5, 100, 74, "Value @or2@Uncommon Package", -1, 3219, 1);
        addHoveredButton(3219, "Pets/Pack", 6, 101, 74, 3220);
        addHoverButton(3240, "Pets/Pack", 9, 100, 74, "Buy @or2@Legendary Package", -1, 3241, 1);
        addHoveredButton(3241, "Pets/Pack", 10, 101, 74, 3241);
        addHoverButton(3221, "Pets/Pack", 1, 100, 74, "Value @or2@Rare Package", -1, 3222, 1);
        addHoveredButton(3222, "Pets/Pack", 2, 101, 74, 3223);
        addHoverButton(3229, "Pets/Pack", 7, 100, 74, "Value @or2@Common Package", -1, 3230, 1);
        addHoveredButton(3230, "Pets/Pack", 8, 101, 74, 3231);
        addHoverButton(3260, "Pets/Pack", 11, 100, 74, "@gre@Learn More", -1, 3261, 1);
        addHoveredButton(3261, "Pets/Pack", 12, 101, 74, 3262);
        addText(3270, "Rare Package", atextdrawingarea, 0, 0xffcc33, false, true);
        addText(3271, "Common Package", atextdrawingarea, 0, 0xffcc33, false, true);
        addText(3272, "Extreme Package", atextdrawingarea, 0, 0xffcc33, false, true);
        addText(3273, "Legendary Package", atextdrawingarea, 0, 0xffcc33, false, true);
        addText(3274, "Uncommon Package", atextdrawingarea, 0, 0xffcc33, false, true);
        addText(3275, "Learn More", atextdrawingarea, 0, 0x19f50e, false, true);
        addText(3276, "Kill ::ruwarriors To Obtain PaeScape Points!", atextdrawingarea, 3, 0xffcc33, false, true);
        rsinterface.totalChildren(34);
        rsinterface.child(0, 3201, 12, 46);
        rsinterface.child(1, 3202, 475, 51);
        rsinterface.child(2, 3203, 475, 51);
        rsinterface.child(13, 3218, 337, 110);
        rsinterface.child(14, 3219, 337, 110);
        rsinterface.child(7, 3208, 337, 110);
        rsinterface.child(8, 3209, 337, 110);
        rsinterface.child(9, 3210, 164, 48);
        rsinterface.child(10, 3211, 381, 77);
        rsinterface.child(5, 3206, 200, 110);
        rsinterface.child(6, 3207, 200, 110);
        rsinterface.child(11, 3215, 200, 110);
        rsinterface.child(12, 3216, 200, 110);
        rsinterface.child(3, 3204, 63, 110);
        rsinterface.child(4, 3205, 63, 110);
        rsinterface.child(15, 3221, 63, 110);
        rsinterface.child(16, 3222, 63, 110);
        rsinterface.child(17, 3225, 63, 200);
        rsinterface.child(18, 3226, 63, 200);
        rsinterface.child(19, 3229, 63, 200);
        rsinterface.child(20, 3230, 63, 200);
        rsinterface.child(21, 3240, 200, 200);
        rsinterface.child(22, 3241, 200, 200);
        rsinterface.child(23, 3235, 200, 200);
        rsinterface.child(24, 3236, 200, 200);
        rsinterface.child(25, 3260, 337, 200);
        rsinterface.child(26, 3261, 337, 200);
        rsinterface.child(27, 3270, 80, 185);
        rsinterface.child(28, 3271, 77, 275);
        rsinterface.child(29, 3272, 216, 185);
        rsinterface.child(30, 3273, 215, 275);
        rsinterface.child(31, 3274, 349, 185);
        rsinterface.child(32, 3275, 370, 275);
        rsinterface.child(33, 3276, 40, 77);
    }

    public static void YellCustomizer(TextDrawingArea[] tda) {
        RSInterface tab = addTabInterface(74000);


    }

    public static void PasswordChanger(TextDrawingArea[] tda) {
        RSInterface tab = addTabInterface(75000);

    }

    public static void customization(TextDrawingArea[] tda) {

        RSInterface tab = addTabInterface(60000);
        addSprite(60001, 0, "Interfaces/customization/BG");
        addButton(60002, -1, "", 16, 16, "Close", 5);
        addMaxCape(60003, 75, 50, 420);
        addColorBox(60004, 41, 41, 0x92343A);
        addButton(60005, -1, "", 71, 59, "Edit detail (top) colour", 5);
        addColorBox(60006, 41, 41, 0x601C1C);
        addButton(60007, -1, "", 71, 59, "Edit background (top) colour", 5);
        addColorBox(60008, 41, 41, 0x441414);
        addButton(60009, -1, "", 71, 59, "Edit detail (bottom) colour", 5);
        addColorBox(60010, 41, 41, 0x261015);
        addButton(60011, -1, "", 71, 59, "Edit background (bottom) colour", 5);
        addButton(60012, -1, "", 136, 24, "Done", 5);

        addText(60013, "Cape Customization", fonts, 2, 0xff9040, true, true);

        addText(60014, "Choose a Color", fonts, 1, 0xff9040, true, true);

        addText(60015, "Detail (Top)", fonts, 0, 0xff9040, true, true);
        addText(60016, "Detail (Bottom)", fonts, 0, 0xff9040, true, true);
        addText(60017, "Background (Top)", fonts, 0, 0xff9040, true, true);
        addText(60018, "Background (Bottom)", fonts, 0, 0xff9040, true, true);

        tab.totalChildren(18);
        tab.child(0, 60001, 7, 15);
        tab.child(1, 60002, 484, 18);
        tab.child(2, 60003, 388, 252);
        tab.child(3, 60004, 71, 92);
        tab.child(4, 60005, 71, 87);
        tab.child(5, 60006, 226, 94);
        tab.child(6, 60007, 223, 87);
        tab.child(7, 60008, 71, 182);
        tab.child(8, 60009, 71, 169);
        tab.child(9, 60010, 226, 184);
        tab.child(10, 60011, 223, 169);
        tab.child(11, 60012, 148, 280);
        tab.child(12, 60013, 250, 19);
        tab.child(13, 60014, 177, 55);
        tab.child(14, 60015, 96, 150);
        tab.child(15, 60016, 96, 240);
        tab.child(16, 60017, 252, 150);
        tab.child(17, 60018, 252, 240);

    }

    public static void addConfigButton(int ID, int pID, int bID, int bID2, String bName, int width, int height, String tT, int configID, int aT, int configFrame) {
        RSInterface Tab = addTabInterface(ID);
        Tab.parentID = pID;
        Tab.id = ID;
        Tab.type = 5;
        Tab.atActionType = aT;
        Tab.contentType = 0;
        Tab.width = width;
        Tab.height = height;
        Tab.opacity = 0;
        Tab.hoverType = -1;
        Tab.valueCompareType = new int[1];
        Tab.requiredValues = new int[1];
        Tab.valueCompareType[0] = 1;
        Tab.requiredValues[0] = configID;
        Tab.valueIndexArray = new int[1][3];
        Tab.valueIndexArray[0][0] = 5;
        Tab.valueIndexArray[0][1] = configFrame;
        Tab.valueIndexArray[0][2] = 0;
        Tab.disabledSprite = imageLoader(bID, bName);
        Tab.enabledSprite = imageLoader(bID2, bName);
        Tab.tooltip = tT;
    }

    public static void playerOwnedShopInterface(TextDrawingArea[] tda) {
        RSInterface tab = addInterface(32600);
        RSInterface tab2 = addInterface(32620);
        RSInterface tab3 = addInterface(32622);
        String dir = "ok/ok";
        addSprite(32601, 0, dir);
        addConfigButton(32602, 32603, 3, 4, dir, 164, 22, "Search", 1, 4, 968);
        //addConfigButton(32604, 32605, 5, 6, dir, 164, 22, "Search for players", 1, 4, 969);
        addHoverButton(32606, dir, 1, 16, 16, "Close Window", 250, 32607, 3);
        addHoveredButton(32607, dir, 2, 16, 16, 32608);
        addText(32610, "Trading Post", tda, 2, 0xff7000, true, true);
        addText(32611, "Search", tda, 1, 0xff7000, false, true);
        //addText(32612, "Search a player", tda, 1, 0xff7000, false, true);

        addHover(54098, 1, 0, 54099, 0, "ok/SPRITE", 72, 32, "My Shop");
        addHovered(54099, 1, "ok/SPRITE", 72, 32, 54100);
        addText(54101, "My Shop", tda, 1, 0xff7000, false, true);

        int x = 10, y = 10;
        tab.totalChildren(11);
        tab.child(0, 32601, 6, 0 + y);
        tab.child(1, 32602, 87 + x, 273 + y);
        //tab.child(2, 32604, 360+x, 273+y);
        tab.child(2, 32606, 463 + x, 4 + y);
        tab.child(3, 32607, 463 + x, 4 + y);
        tab.child(4, 32611, 90 + x, 276 + y);
        //tab.child(6, 32612, 363+x, 276+y);
        tab.child(5, 32620, 9 + x, 22 + y);
        tab.child(6, 32622, 350 + x, 22 + y);
        tab.child(7, 32610, 242 + x, 5 + y);
        tab.child(8, 54098, 382, 272 + y);
        tab.child(9, 54099, 382, 272 + y);
        tab.child(10, 54101, 394, 275 + y);
        tab2.width = 317;
        tab2.height = 246;
        tab2.scrollMax = 400;
        itemGroup(32621, 7, 8, 10, 10);
        tab2.totalChildren(1);
        tab2.child(0, 32621, 14, 14);
        for (int i = 0; i < 100; i++) {
            addClickableText(32623 + i, "  " + i, "Select", tda, 0, 0xeb981f, false, true, 632);
        }
        tab3.width = 110;
        tab3.height = 246;
        tab3.scrollMax = 10 + (100 * 14);
        tab3.totalChildren(100);
        for (int i = 0; i < 100; i++) {
            tab3.child(i, 32623 + i, 10, 10 + (i * 14));
        }
    }

    public static void playerOwnedShopInterface2(TextDrawingArea[] tda) {
        RSInterface tab = addInterface(33600);
        RSInterface tab2 = addInterface(33620);
        String dir = "ok/ok";
        addSprite(33601, 7, dir);
        addHoverButton(33606, dir, 1, 16, 16, "Close Window", 250, 32607, 3);
        addHoveredButton(33607, dir, 2, 16, 16, 32608);
        addText(33610, "Your Shop", tda, 2, 0xff7000, true, true);
        int x = 10, y = 10;
        tab.totalChildren(5);
        tab.child(0, 33601, 0 + x, 0 + y);
        tab.child(1, 33606, 463 + x, 4 + y);
        tab.child(2, 33607, 463 + x, 4 + y);
        tab.child(3, 33620, 9 + x, 22 + y);
        tab.child(4, 33610, 242 + x, 5 + y);
        tab2.width = 455;
        tab2.height = 276;
        tab2.scrollMax = 400;
        itemGroup(33621, 10, 8, 10, 10);
        tab2.totalChildren(1);
        tab2.child(0, 33621, 20, 14);
    }

    public static void itemGroup(int id, int w, int h, int x, int y) {
        RSInterface rsi = addInterface(id);
        rsi.width = w;
        rsi.height = h;
        rsi.inv = new int[w * h];
        rsi.invStackSizes = new int[w * h];
        rsi.usableItemInterface = false;
        rsi.isInventoryInterface = false;
        rsi.invSpritePadX = x;
        rsi.invSpritePadY = y;
        rsi.spritesX = new int[20];
        rsi.spritesY = new int[20];
        rsi.sprites = new Sprite[20];
        rsi.type = 2;
    }

    public static void addToItemGroup(int id, int w, int h, int x, int y, boolean actions, String action1, String action2, String action3) {
        RSInterface rsi = addInterface(id);
        rsi.width = w;
        rsi.height = h;
        rsi.inv = new int[w * h];
        rsi.invStackSizes = new int[w * h];
        rsi.usableItemInterface = false;
        rsi.isInventoryInterface = false;
        rsi.hidden = false;
        rsi.invSpritePadX = x;
        rsi.invSpritePadY = y;
        rsi.spritesX = new int[20];
        rsi.spritesY = new int[20];
        rsi.sprites = new Sprite[20];
        rsi.actions = new String[5];
        if (actions) {
            rsi.actions[0] = action1;
            rsi.actions[1] = action2;
            rsi.actions[2] = action3;
        }
        rsi.type = 2;
    }

    static void playerOwnedShopInterface3(TextDrawingArea[] tda) {
        RSInterface rsi = addInterface(37000);
        addSprite(37001, 0, "Interfaces/ResourceBag/SPRITE");
        addSprite(37002, 1, "Interfaces/ResourceBag/SPRITE");
        for (int i = 0; i < 28; i++)
            addToItemGroup(37003 + i, 1, 1, 0, 0, true, new String[]{"Withdraw 1", "Withdraw 5", "Withdraw 10", "Withdraw all", "Withdraw all to bank"});
        setChildren(30, rsi);
        setBounds(37001, 147, 38, 0, rsi);
        setBounds(37002, 140, 31, 1, rsi);
        int x = 0;
        int y = 0;
        int counter = 0;
        for (int i = 0; i < 28; i++) {
            setBounds(37003 + i, 162 + x, 46 + y, 2 + i, rsi);
            x += 42;
            counter++;
            if (counter == 4) {
                x = 0;
                y += 34;
                counter = 0;
            }
        }
        RSInterface tab = addTabInterface(37053);
        addInventoryItemGroup2(37054, 7, 4);
        setChildren(1, tab);
        setBounds(37054, 0, 0, 0, tab);
    }

    public static void addToItemGroup(int id, int w, int h, int x, int y, boolean hasActions, String[] actions) {
        RSInterface rsi = addInterface(id);
        rsi.width = w;
        rsi.height = h;
        rsi.inv = new int[w * h];
        rsi.invStackSizes = new int[w * h];
        rsi.usableItemInterface = false;
        rsi.isInventoryInterface = false;
        rsi.hidden = false;
        rsi.invSpritePadX = x;
        rsi.invSpritePadY = y;
        rsi.spritesX = new int[20];
        rsi.spritesY = new int[20];
        rsi.sprites = new Sprite[20];
        if (hasActions) rsi.actions = actions;
        rsi.type = 2;
    }

    public static void opacityInterface() {
        RSInterface rsi = addTabInterface(35555);
        setChildren(1, rsi);
        addRectangle(35556, 128, 0x000000, true, 30, 34);
        setBounds(35556, 0, 0, 0, rsi);
    }

    /*
     * Spellbook interfaces redone
     * @author levi patton AuguryPS @r-s
     * 7/16/2016
     */
    public static void ancientMagicTab(TextDrawingArea[] tda) {
        RSInterface tab = addInterface(12855);


        addButton(1195, 430, "Cast @gre@Home Teleport", 19, 19);

        int[] itfChildren = {1195, 12939, 12987, 13035, 12901, 12861, 13045, 12963, 13011, 13053, 12919, 12881, 13061, 12951, 12999, 13069, 12911, 12871, 13079, 12975, 13023, 13087, 12929, 12891, 13095, 1196, 12940, 12988, 13036, 12902, 12862, 13046, 12964, 13012, 13054, 12920, 12882, 13062, 12952, 13000, 13070, 12912, 12872, 13080, 12976, 13024, 13088, 12930, 12892, 13096};
        tab.totalChildren(itfChildren.length);
        for (int i1 = 0, xPos = 18, yPos = 8; i1 < itfChildren.length; i1++, xPos += 45) {
            if (xPos > 175) {
                xPos = 18;
                yPos += 28;
            }
            if (i1 < 25) tab.child(i1, itfChildren[i1], xPos, yPos);

            if (i1 > 24) {
                yPos = i1 < 41 ? 181 : 1;
                tab.child(i1, itfChildren[i1], 4, yPos);
            }
        }
    }

    public static void magicTab(TextDrawingArea[] tda) {
        RSInterface tab = addTabInterface(1151);
        RSInterface homeHover = addTabInterface(1196);
        RSInterface spellButtons = interfaceCache[12424];
        int[] spellButton = {1196, 1199, 1206, 1215, 1224, 1231, 1240, 1249, 1258, 1267, 1274, 1283, 1573, 1290, 1299, 1308, 1315, 1324, 1333, 1340, 1349, 1358, 1367, 1374, 1381, 1388, 1397, 1404, 1583, 12038, 1414, 1421, 1430, 1437, 1446, 1453, 1460, 1469, 15878, 1602, 1613, 1624, 7456, 1478, 1485, 1494, 1503, 1512, 1521, 1530, 1544, 1553, 1563, 1593, 1635, 12426, 12436, 12446, 12456, 6004, 18471};
        tab.totalChildren(63);
        tab.child(0, 12424, 13, 24);

        for (int i1 = 0; i1 < spellButton.length; i1++) {
            int yPos = i1 > 34 ? 8 : 183;
            tab.child(1, 1195, 13, 24);
            tab.child(i1 + 2, spellButton[i1], 5, yPos);
            addButton(1195, 430, "Cast @gre@Home Teleport", 19, 19);
            RSInterface homeButton = interfaceCache[1195];
            homeButton.hoverType = 1196;
        }
        for (int i2 = 0; i2 < spellButton.length; i2++) {
            if (i2 < 60) spellButtons.childX[i2] = spellButtons.childX[i2] + 24;
            if (i2 == 6 || i2 == 12 || i2 == 19 || i2 == 35 || i2 == 41 || i2 == 44 || i2 == 49 || i2 == 51)
                spellButtons.childX[i2] = 0;
            spellButtons.childY[6] = 24;
            spellButtons.childY[12] = 48;
            spellButtons.childY[19] = 72;
            spellButtons.childY[49] = 96;
            spellButtons.childY[44] = 120;
            spellButtons.childY[51] = 144;
            spellButtons.childY[35] = 170;
            spellButtons.childY[41] = 192;
        }
        homeHover.hidden = true;
        addText(1197, "Level 0: Home Teleport", tda, 1, 0xFE981F, true, true);
        RSInterface homeLevel = interfaceCache[1197];
        homeLevel.width = 174;
        homeLevel.height = 68;
        addText(1198, "A teleport which requires no", tda, 0, 0xAF6A1A, true, true);
        addText(18998, "runes and no required level that", tda, 0, 0xAF6A1A, true, true);
        addText(18999, "teleports you to the main land.", tda, 0, 0xAF6A1A, true, true);
        homeHover.totalChildren(4);
        homeHover.child(0, 1197, 3, 4);
        homeHover.child(1, 1198, 91, 23);
        homeHover.child(2, 18998, 91, 34);
        homeHover.child(3, 18999, 91, 45);
        spellButtons.scrollMax = 0;
        spellButtons.height = 260;
        spellButtons.width = 190;
    }

    /* Add Container */
    public static RSInterface addContainer(int id, int contentType, int width, int height, int xPad, int yPad, boolean move, String... actions) {
        RSInterface container = addInterface(id);
        container.parentID = id;
        container.type = 2;
        container.contentType = contentType;
        container.width = width;
        container.height = height;
        container.sprites = new Sprite[20];
        container.spritesX = new int[20];
        container.spritesY = new int[20];
        container.invSpritePadX = xPad;
        container.invSpritePadY = yPad;
        container.inv = new int[width * height];
        container.invStackSizes = new int[width * height];
        container.actions = actions;
        container.deleteOnDrag2 = move;
        return container;
    }

    public static void newStarter(TextDrawingArea[] tda) {
        RSInterface tab = addInterface(52750);
        addSpriteLoader(52751, 960);
        addText(52752, "PaeScape Account Setup", 0xff9933, true, true, 52, tda, 3);
        addText(52753, "Starter Items:", 0xff9933, false, true, 52, tda, 1);
        addText(52754, "Modes: ", 0xff9933, false, true, 52, tda, 1);
        addText(52755, "Mode Description:", 0xff9933, true, true, 52, tda, 2);
        addConfigButtonWSpriteLoader(52756, 51750, 937, 938, 15, 15, "Select Normal", 0, 5, 1085);
        addConfigButtonWSpriteLoader(52757, 51750, 937, 938, 15, 15, "Select Ironman", 1, 5, 1085);
        addConfigButtonWSpriteLoader(52758, 51750, 937, 938, 15, 15, "Select Ultimate Ironman", 2, 5, 1085);
        addSpriteLoader(52759, 712);
        addSpriteLoader(52760, 711);
        addText(52761, "Normal", 0xff9933, false, true, 52, tda, 0);
        addText(52762, "  Ironman", 0xff9933, false, true, 52, tda, 0);
        addText(52763, "  Ultimate Iron", 0xff9933, false, true, 52, tda, 0);
        addText(52764, "Play PaeScape as an Iron man.", 0xff9933, true, true, 52, tda, 0);
        addText(52765, "You will be restricted from trading, staking and looting items from killed players.", 0xff9933, true, true, 52, tda, 0);
        addText(52766, "You will not get a npc drop if another player has done more damage.", 0xff9933, true, true, 52, tda, 0);
        addText(52767, "You will have to rely on your starter, skilling, pvming, and shops.", 0xff9933, true, true, 52, tda, 0);
        addText(52768, "This game mode is for players that love a challenge.", 0xff9933, true, true, 52, tda, 0);
        addHoverButtonWSpriteLoader(35769, 961, 123, 30, "Confirm selection", -1, 35770, 1);
        addHoveredImageWSpriteLoader(35770, 962, 123, 30, 35771);
        addText(52772, "Confirm", 0xF7AA25, true, true, 52, tda, 3);
        tab.totalChildren(48);
        tab.child(0, 52751, 7, 8);
        tab.child(1, 52752, 250, 17);
        tab.child(2, 52753, 25, 48);
        tab.child(3, 52754, 347, 45);
        tab.child(4, 52755, 265, 220);
        tab.child(5, 52756, 366, 75);
        tab.child(6, 52757, 366, 115);
        tab.child(7, 52758, 366, 152);
        tab.child(8, 51759, 388, 115);
        tab.child(9, 52760, 388, 152);
        tab.child(10, 52761, 388, 78);
        tab.child(11, 52762, 397, 117);
        tab.child(12, 52763, 397, 154);
        tab.child(13, 52764, 265, 237);
        tab.child(14, 52765, 265, 247);
        tab.child(15, 52766, 265, 257);
        tab.child(16, 52767, 265, 267);
        tab.child(17, 52768, 265, 277);
        tab.child(18, 35769, 364, 181);
        tab.child(19, 35770, 364, 181);
        tab.child(20, 52772, 423, 185);
        /**
         * First row of items
         */
        int child = 21;
        int x = 24;
        for (int i = 59025; i < 59034; i++) {
            addItemOnInterface(i, 59000, new String[]{});
            tab.child(child, i, x, 70);
            child++;
            x += 34;
        }

        int child2 = 30;
        int x2 = 24;
        for (int i = 59034; i < 59043; i++) {
            addItemOnInterface(i, 59000, new String[]{});
            tab.child(child2, i, x2, 102);
            child2++;
            x2 += 34;
        }

        /**
         * third row of items
         */
        int child3 = 39;
        int x3 = 24;
        for (int i = 59043; i < 59052; i++) {
            addItemOnInterface(i, 59000, new String[]{});
            tab.child(child3, i, x3, 134);
            child3++;
            x3 += 34;
        }
    }

    public static void Starter(TextDrawingArea[] tda) {
        RSInterface tab = addInterface(51750);
        addSpriteLoader(51751, 944);
        addText(51752, "PaeScape Account Setup", 0xff9933, true, true, 52, tda, 3);
        addText(51753, "Starter Items:", 0xff9933, false, true, 52, tda, 1);
        addText(51754, "Modes: ", 0xff9933, false, true, 52, tda, 1);
        addText(51755, "Mode Description:", 0xff9933, true, true, 52, tda, 2);
        addConfigButtonWSpriteLoader(51756, 51750, 937, 938, 15, 15, "Select Normal", 0, 327, 1085);
        addConfigButtonWSpriteLoader(51757, 51750, 937, 938, 15, 15, "Select Ironman", 1, 327, 1085);
        addConfigButtonWSpriteLoader(51758, 51750, 937, 938, 15, 15, "Select Ultimate Ironman", 2, 327, 1085);
        addSpriteLoader(51759, 712);
        addSpriteLoader(51760, 711);
        addText(51761, "Normal", 0xff9933, false, true, 52, tda, 0);
        addText(51762, "  Ironman", 0xff9933, false, true, 52, tda, 0);
        addText(51763, "  Ultimate Iron", 0xff9933, false, true, 52, tda, 0);
        addText(51764, "Play Thanatos as an Iron man.", 0xff9933, true, true, 52, tda, 0);
        addText(51765, "You will be restricted from trading, staking and looting items from killed players.", 0xff9933, true, true, 52, tda, 0);
        addText(51766, "You will not get a npc drop if another player has done more damage.", 0xff9933, true, true, 52, tda, 0);
        addText(51767, "You will have to rely on your starter, skilling, pvming, and shops.", 0xff9933, true, true, 52, tda, 0);
        addText(51768, "This game mode is for players that love a challenge.", 0xff9933, true, true, 52, tda, 0);
        addHoverButtonWSpriteLoader(51769, 945, 123, 30, "Confirm selection", -1, 51770, 1);
        addHoveredImageWSpriteLoader(51770, 946, 123, 30, 51771);
        addText(51772, "Confirm", 0xF7AA25, true, true, 52, tda, 3);
        tab.totalChildren(22);
        tab.child(0, 51751, 7, 8);
        tab.child(1, 51752, 250, 17);
        tab.child(2, 51753, 25, 48);
        tab.child(3, 51754, 347, 45);
        tab.child(4, 51755, 265, 220);
        tab.child(5, 51756, 366, 75);
        tab.child(6, 51757, 366, 115);
        tab.child(7, 51758, 366, 152);
        tab.child(8, 51759, 388, 115);
        tab.child(9, 51760, 388, 152);
        tab.child(10, 51761, 388, 78);
        tab.child(11, 51762, 397, 117);
        tab.child(12, 51763, 397, 154);
        tab.child(13, 51749, 10, 65);
        tab.child(14, 51764, 265, 237);
        tab.child(15, 51765, 265, 247);
        tab.child(16, 51766, 265, 257);
        tab.child(17, 51767, 265, 267);
        tab.child(18, 51768, 265, 277);
        tab.child(19, 51769, 364, 181);
        tab.child(20, 51770, 364, 181);
        tab.child(21, 51772, 423, 185);
        RSInterface scrollInterface = addTabInterface(51749);
        scrollInterface.width = 306;
        scrollInterface.height = 141;
        scrollInterface.scrollMax = 150;
        setChildren(1, scrollInterface);
        addContainer(51748, 0, 7, 18, 11, 3, false, new String[]{null, null, null, null, null});
        setBounds(51748, 15, 10, 0, scrollInterface);

        scrollInterface = addTabInterface(51773);
        scrollInterface.width = 416;
        scrollInterface.height = 75;
        scrollInterface.scrollMax = 150;

        int y = 15;
        scrollInterface.totalChildren(28);
        for (int i = 0; i < 28; i++) {
            addText(51775 + i, "", 0xff9933, true, true, 52, tda, 0);
            scrollInterface.child(i, 51775 + i, 200, y);
            y += 18;
        }
    }

    public static void TeleTAB1() {
        RSInterface RSinterface = addTabInterface(37400);

        addSprite(37401, 0, "Interfaces/Tele/BG");

        addHoverButton(37402, "Interfaces/Tele/TAB/TABL", 0, 130, 55, "Training", -1, 37403, 1);
        addHoverButton(37404, "Interfaces/Tele/TAB/TAB", 1, 130, 55, "Dungeons", -1, 37405, 1);
        addHoverButton(37406, "Interfaces/Tele/TAB/TAB", 2, 130, 55, "Bosses", -1, 37407, 1);
        addHoverButton(37408, "Interfaces/Tele/TAB/TAB", 3, 130, 55, "Minigames", -1, 37409, 1);
        addHoverButton(37410, "Interfaces/Tele/TAB/TAB", 4, 130, 55, "Raids", -1, 37411, 1);

        addHoverButton(37412, "Interfaces/Tele/Close", 0, 16, 16, "Close", -1, 37413, 1);
        addHoveredButton(37413, "Interfaces/Tele/Close", 1, 16, 16, 37614);


        //buttons

        addHoverButton(37414, "Interfaces/Tele/TBUTTONS/B", 0, 81, 34, "", -1, 37415, 1);
        addHoverButton(37416, "Interfaces/Tele/TBUTTONS/B", 1, 81, 34, "", -1, 37417, 1);
        addHoverButton(37418, "Interfaces/Tele/TBUTTONS/B", 2, 81, 34, "", -1, 37419, 1);
        addHoverButton(37420, "Interfaces/Tele/TBUTTONS/B", 3, 81, 34, "", -1, 37421, 1);
        addHoverButton(37422, "Interfaces/Tele/TBUTTONS/B", 4, 81, 34, "", -1, 37423, 1);
        addHoverButton(37424, "Interfaces/Tele/TBUTTONS/B", 5, 81, 34, "", -1, 37425, 1);
        addHoverButton(37426, "Interfaces/Tele/TBUTTONS/B", 6, 81, 34, "", -1, 37427, 1);
        addHoverButton(37428, "Interfaces/Tele/TBUTTONS/B", 7, 81, 34, "", -1, 37429, 1);
        addHoverButton(37430, "Interfaces/Tele/TBUTTONS/B", 8, 81, 34, "", -1, 37431, 1);
        addHoverButton(37432, "Interfaces/Tele/TBUTTONS/B", 9, 81, 34, "", -1, 37433, 1);
        addHoverButton(37434, "Interfaces/Tele/TBUTTONS/B", 10, 81, 34, "", -1, 37435, 1);
        addHoverButton(37436, "Interfaces/Tele/TBUTTONS/B", 11, 81, 34, "", -1, 37437, 1);


        addHoveredButton(37415, "Interfaces/Tele/TBUTTONS/BH", 0, 81, 34, 37616);

        addHoveredButton(37417, "Interfaces/Tele/TBUTTONS/BH", 1, 81, 34, 37618);

        addHoveredButton(37419, "Interfaces/Tele/TBUTTONS/BH", 2, 81, 34, 37620);

        addHoveredButton(37421, "Interfaces/Tele/TBUTTONS/BH", 3, 81, 34, 37622);

        addHoveredButton(37423, "Interfaces/Tele/TBUTTONS/BH", 4, 81, 34, 37624);

        addHoveredButton(37425, "Interfaces/Tele/TBUTTONS/BH", 5, 81, 34, 37626);

        addHoveredButton(37427, "Interfaces/Tele/TBUTTONS/BH", 6, 81, 34, 37628);

        addHoveredButton(37429, "Interfaces/Tele/TBUTTONS/BH", 7, 81, 34, 37630);

        addHoveredButton(37431, "Interfaces/Tele/TBUTTONS/BH", 8, 81, 34, 37632);

        addHoveredButton(37433, "Interfaces/Tele/TBUTTONS/BH", 9, 81, 34, 37634);

        addHoveredButton(37435, "Interfaces/Tele/TBUTTONS/BH", 10, 81, 34, 37636);

        addHoveredButton(37437, "Interfaces/Tele/TBUTTONS/BH", 11, 81, 34, 37638);


        addText(50430, "PaeScape Teleports", fonts, 2, 0xff9040, true, true); //rename it whatever

        int last = 33;
        RSinterface.children = new int[last];
        RSinterface.childX = new int[last];
        RSinterface.childY = new int[last];
        setBounds(37401, 12, 12, 0, RSinterface);
        setBounds(37402, 19, 34, 1, RSinterface);
        setBounds(37404, 19, 89, 2, RSinterface);
        setBounds(37406, 19, 144, 3, RSinterface);
        setBounds(37408, 19, 199, 4, RSinterface);
        setBounds(37410, 19, 254, 5, RSinterface);

        setBounds(37412, 473, 16, 6, RSinterface);//close
        setBounds(37413, 473, 16, 19, RSinterface);//close

        //buttons
        int one = 180;
        int two = 285;
        int three = 390;


        setBounds(37414, one, 60, 7, RSinterface);
        setBounds(37415, one, 60, 20, RSinterface);
        setBounds(37416, two, 60, 8, RSinterface);
        setBounds(37417, two, 60, 21, RSinterface);
        setBounds(37418, three, 60, 9, RSinterface);
        setBounds(37419, three, 60, 22, RSinterface);

        setBounds(37420, one, 119, 10, RSinterface);
        setBounds(37421, one, 119, 23, RSinterface);
        setBounds(37422, two, 119, 11, RSinterface);
        setBounds(37423, two, 119, 24, RSinterface);
        setBounds(37424, three, 119, 12, RSinterface);
        setBounds(37425, three, 119, 25, RSinterface);

        setBounds(37426, one, 178, 13, RSinterface);
        setBounds(37427, one, 178, 26, RSinterface);
        setBounds(37428, two, 178, 14, RSinterface);
        setBounds(37429, two, 178, 27, RSinterface);
        setBounds(37430, three, 178, 15, RSinterface);
        setBounds(37431, three, 178, 28, RSinterface);

        setBounds(37432, one, 237, 16, RSinterface);
        setBounds(37433, one, 237, 29, RSinterface);
        setBounds(37434, two, 237, 17, RSinterface);
        setBounds(37435, two, 237, 30, RSinterface);
        setBounds(37436, three, 237, 18, RSinterface);
        setBounds(37437, three, 237, 31, RSinterface);
        setBounds(50430, two, 16, 32, RSinterface);

    }

    public static void TeleTAB2() {
        RSInterface RSinterface = addTabInterface(38400);

        addSprite(38401, 0, "Interfaces/Tele/BG");

        addHoverButton(38402, "Interfaces/Tele/TAB/TAB", 0, 130, 55, "Training", -1, 38403, 1);

        addHoverButton(38404, "Interfaces/Tele/TAB/TABL", 1, 130, 55, "Deungeon", -1, 38405, 1);
        addHoverButton(38406, "Interfaces/Tele/TAB/TAB", 2, 130, 55, "Boss", -1, 38407, 1);
        addHoverButton(38408, "Interfaces/Tele/TAB/TAB", 3, 130, 55, "MiniGame", -1, 38409, 1);
        addHoverButton(38410, "Interfaces/Tele/TAB/TAB", 4, 130, 55, "Wilderness", -1, 38411, 1);

        addHoverButton(38412, "Interfaces/Tele/Close", 0, 16, 16, "Close", -1, 38413, 1);
        addHoveredButton(38413, "Interfaces/Tele/Close", 1, 16, 16, 38614);


        //buttons

        addHoverButton(38414, "Interfaces/Tele/DBUTTONS/B", 0, 81, 34, "", -1, 38415, 1);
        addHoverButton(38416, "Interfaces/Tele/DBUTTONS/B", 1, 81, 34, "", -1, 38417, 1);
        addHoverButton(38418, "Interfaces/Tele/DBUTTONS/B", 2, 81, 34, "", -1, 38419, 1);
        addHoverButton(38420, "Interfaces/Tele/DBUTTONS/B", 3, 81, 34, "", -1, 38421, 1);
        addHoverButton(38422, "Interfaces/Tele/DBUTTONS/B", 4, 81, 34, "", -1, 38423, 1);
        addHoverButton(38424, "Interfaces/Tele/DBUTTONS/B", 5, 81, 34, "", -1, 38425, 1);
        addHoverButton(38426, "Interfaces/Tele/DBUTTONS/B", 6, 81, 34, "", -1, 38427, 1);
        addHoverButton(38428, "Interfaces/Tele/DBUTTONS/B", 7, 81, 34, "", -1, 38429, 1);
        addHoverButton(38430, "Interfaces/Tele/DBUTTONS/B", 8, 81, 34, "", -1, 38431, 1);
        addHoverButton(38432, "Interfaces/Tele/DBUTTONS/B", 9, 81, 34, "", -1, 38433, 1);


        addHoveredButton(38415, "Interfaces/Tele/DBUTTONS/BH", 0, 81, 34, 38616);
        addHoveredButton(38417, "Interfaces/Tele/DBUTTONS/BH", 1, 81, 34, 38618);
        addHoveredButton(38419, "Interfaces/Tele/DBUTTONS/BH", 2, 81, 34, 38620);
        addHoveredButton(38421, "Interfaces/Tele/DBUTTONS/BH", 3, 81, 34, 38622);
        addHoveredButton(38423, "Interfaces/Tele/DBUTTONS/BH", 4, 81, 34, 38624);
        addHoveredButton(38425, "Interfaces/Tele/DBUTTONS/BH", 5, 81, 34, 38626);
        addHoveredButton(38427, "Interfaces/Tele/DBUTTONS/BH", 6, 81, 34, 38628);
        addHoveredButton(38429, "Interfaces/Tele/DBUTTONS/BH", 7, 81, 34, 38630);
        addHoveredButton(38431, "Interfaces/Tele/DBUTTONS/BH", 8, 81, 34, 38632);
        addHoveredButton(38433, "Interfaces/Tele/DBUTTONS/BH", 9, 81, 34, 38634);


        addText(50430, "PaeScape Teleports", fonts, 2, 0xff9040, true, true);

        int last = 29;
        RSinterface.children = new int[last];
        RSinterface.childX = new int[last];
        RSinterface.childY = new int[last];
        setBounds(38401, 12, 12, 0, RSinterface);
        setBounds(38402, 19, 34, 1, RSinterface);
        setBounds(38404, 19, 89, 2, RSinterface);
        setBounds(38406, 19, 144, 3, RSinterface);
        setBounds(38408, 19, 199, 4, RSinterface);
        setBounds(38410, 19, 254, 5, RSinterface);

        setBounds(38412, 473, 16, 6, RSinterface);//close
        setBounds(38413, 473, 16, 7, RSinterface);//close

        //buttons
        int one = 180;
        int two = 285;
        int three = 390;


        setBounds(38414, one, 60, 8, RSinterface);
        setBounds(38415, one, 60, 9, RSinterface);
        setBounds(38416, two, 60, 10, RSinterface);
        setBounds(38417, two, 60, 11, RSinterface);
        setBounds(38418, three, 60, 12, RSinterface);
        setBounds(38419, three, 60, 13, RSinterface);

        setBounds(38420, one, 119, 14, RSinterface);
        setBounds(38421, one, 119, 15, RSinterface);
        setBounds(38422, two, 119, 16, RSinterface);
        setBounds(38423, two, 119, 17, RSinterface);
        setBounds(38424, three, 119, 18, RSinterface);
        setBounds(38425, three, 119, 19, RSinterface);

        setBounds(38426, one, 178, 20, RSinterface);
        setBounds(38427, one, 178, 21, RSinterface);
        setBounds(38428, two, 178, 22, RSinterface);
        setBounds(38429, two, 178, 23, RSinterface);

        setBounds(38430, three, 178, 24, RSinterface);
        setBounds(38431, three, 178, 25, RSinterface);
        setBounds(38432, one, 237, 26, RSinterface);
        setBounds(38433, one, 237, 27, RSinterface);

        setBounds(50430, two, 16, 28, RSinterface);

    }

    public static void TeleTAB3_1() {
        RSInterface RSinterface = addTabInterface(41400); //Interface id

        addSprite(41401, 0, "Interfaces/Tele/BG");

        addHoverButton(41402, "Interfaces/Tele/TAB/TAB", 0, 130, 55, "Training", -1, 41403, 1);
        addHoverButton(41404, "Interfaces/Tele/TAB/TAB", 1, 130, 55, "Dungeons", -1, 41405, 1);
        addHoverButton(41406, "Interfaces/Tele/TAB/TABL", 2, 130, 55, "Bosses", -1, 41407, 1);
        addHoverButton(41408, "Interfaces/Tele/TAB/TAB", 3, 130, 55, "Minigames", -1, 41409, 1);
        addHoverButton(41410, "Interfaces/Tele/TAB/TAB", 4, 130, 55, "Raids", -1, 41411, 1);

        addHoverButton(41412, "Interfaces/Tele/Close", 0, 16, 16, "Close", -1, 41413, 1);
        addHoveredButton(41413, "Interfaces/Tele/Close", 1, 16, 16, 41614);


        //buttons

        addHoverButton(41414, "Interfaces/Tele/BBUTTONS/B", 0, 81, 34, "", -1, 41415, 1);
        addHoverButton(41416, "Interfaces/Tele/BBUTTONS/B", 1, 81, 34, "", -1, 41417, 1);
        addHoverButton(41418, "Interfaces/Tele/BBUTTONS/B", 2, 81, 34, "", -1, 41419, 1);
        addHoverButton(41420, "Interfaces/Tele/BBUTTONS/B", 3, 81, 34, "", -1, 41421, 1);
        addHoverButton(41422, "Interfaces/Tele/BBUTTONS/B", 4, 81, 34, "", -1, 41423, 1);
        addHoverButton(41424, "Interfaces/Tele/BBUTTONS/B", 5, 81, 34, "", -1, 41425, 1);
        addHoverButton(41426, "Interfaces/Tele/BBUTTONS/B", 6, 81, 34, "", -1, 41427, 1);
        addHoverButton(41428, "Interfaces/Tele/BBUTTONS/B", 7, 81, 34, "", -1, 41429, 1);
        addHoverButton(41430, "Interfaces/Tele/BBUTTONS/B", 8, 81, 34, "", -1, 41431, 1);
        addHoverButton(41432, "Interfaces/Tele/BBUTTONS/B", 9, 81, 34, "", -1, 41433, 1);
        addHoverButton(41434, "Interfaces/Tele/BBUTTONS/B", 10, 81, 34, "", -1, 41435, 1);
        addHoverButton(41436, "Interfaces/Tele/BBUTTONS/B", 11, 81, 34, "", -1, 41437, 1);


        addHoveredButton(41415, "Interfaces/Tele/BBUTTONS/BH", 0, 81, 34, 41616);
        addHoveredButton(41417, "Interfaces/Tele/BBUTTONS/BH", 1, 81, 34, 41618);
        addHoveredButton(41419, "Interfaces/Tele/BBUTTONS/BH", 2, 81, 34, 41620);
        addHoveredButton(41421, "Interfaces/Tele/BBUTTONS/BH", 3, 81, 34, 41622);
        addHoveredButton(41423, "Interfaces/Tele/BBUTTONS/BH", 4, 81, 34, 41624);
        addHoveredButton(41425, "Interfaces/Tele/BBUTTONS/BH", 5, 81, 34, 41626);
        addHoveredButton(41427, "Interfaces/Tele/BBUTTONS/BH", 6, 81, 34, 41628);
        addHoveredButton(41429, "Interfaces/Tele/BBUTTONS/BH", 7, 81, 34, 41630);
        addHoveredButton(41431, "Interfaces/Tele/BBUTTONS/BH", 8, 81, 34, 41632);
        addHoveredButton(41433, "Interfaces/Tele/BBUTTONS/BH", 9, 81, 34, 41634);
        addHoveredButton(41435, "Interfaces/Tele/BBUTTONS/BH", 10, 81, 34, 41636);
        addHoveredButton(41437, "Interfaces/Tele/BBUTTONS/BH", 11, 81, 34, 41638);


        addHoverButton(41438, "Interfaces/Tele/ARROW", 1, 29, 24, "Next", -1, 41439, 1);
        //addHoverButton(41440, "Interfaces/Tele/ARROW", 0, 29, 24, "Back", -1, 41441, 1);


        addText(50430, "PaeScape Teleports", fonts, 2, 0xff9040, true, true);

        int last = 34;
        RSinterface.children = new int[last];
        RSinterface.childX = new int[last];
        RSinterface.childY = new int[last];
        setBounds(41401, 12, 12, 0, RSinterface);
        setBounds(41402, 19, 34, 1, RSinterface);
        setBounds(41404, 19, 89, 2, RSinterface);
        setBounds(41406, 19, 144, 3, RSinterface);
        setBounds(41408, 19, 199, 4, RSinterface);
        setBounds(41410, 19, 254, 5, RSinterface);

        setBounds(41412, 473, 16, 6, RSinterface);//close
        setBounds(41413, 473, 16, 19, RSinterface);//close

        //buttons
        int one = 180;
        int two = 285;
        int three = 390;


        setBounds(41414, one, 60, 7, RSinterface);
        setBounds(41415, one, 60, 20, RSinterface);
        setBounds(41416, two, 60, 8, RSinterface);
        setBounds(41417, two, 60, 21, RSinterface);
        setBounds(41418, three, 60, 9, RSinterface);
        setBounds(41419, three, 60, 22, RSinterface);
        setBounds(41420, one, 119, 10, RSinterface);
        setBounds(41421, one, 119, 23, RSinterface);
        setBounds(41422, two, 119, 11, RSinterface);
        setBounds(41423, two, 119, 24, RSinterface);
        setBounds(41424, three, 119, 12, RSinterface);
        setBounds(41425, three, 119, 25, RSinterface);
        setBounds(41426, one, 178, 13, RSinterface);
        setBounds(41427, one, 178, 26, RSinterface);
        setBounds(41428, two, 178, 14, RSinterface);
        setBounds(41429, two, 178, 27, RSinterface);
        setBounds(41430, three, 178, 15, RSinterface);
        setBounds(41431, three, 178, 28, RSinterface);
        setBounds(41432, one, 237, 16, RSinterface);
        setBounds(41433, one, 237, 29, RSinterface);
        setBounds(41434, two, 237, 17, RSinterface);
        setBounds(41435, two, 237, 30, RSinterface);
        setBounds(41436, three, 237, 18, RSinterface);
        setBounds(41437, three, 237, 31, RSinterface);
        setBounds(41438, 337, 280, 32, RSinterface);
        //setBounds(41440, 283, 280, 33,RSinterface);
        setBounds(50430, two, 16, 33, RSinterface);


    }

    public static void TeleTAB3_2() {
        RSInterface RSinterface = addTabInterface(42400);

        addSprite(42401, 0, "Interfaces/Tele/BG");

        addHoverButton(42402, "Interfaces/Tele/TAB/TAB", 0, 130, 55, "Training", -1, 42403, 1);
        addHoverButton(42404, "Interfaces/Tele/TAB/TAB", 1, 130, 55, "Dungeons", -1, 42405, 1);
        addHoverButton(42406, "Interfaces/Tele/TAB/TABL", 2, 130, 55, "Bosses", -1, 42407, 1);
        addHoverButton(42408, "Interfaces/Tele/TAB/TAB", 3, 130, 55, "Minigames", -1, 42409, 1);
        addHoverButton(42410, "Interfaces/Tele/TAB/TAB", 4, 130, 55, "Raids", -1, 42411, 1);

        addHoverButton(42412, "Interfaces/Tele/Close", 0, 16, 16, "Close", -1, 42413, 1);
        addHoveredButton(42413, "Interfaces/Tele/Close", 1, 16, 16, 42614);


        //buttons

        addHoverButton(42414, "Interfaces/Tele/BBUTTONS/B", 12, 81, 34, "", -1, 42415, 1);
        addHoverButton(42416, "Interfaces/Tele/BBUTTONS/B", 13, 81, 34, "", -1, 42417, 1);
        addHoverButton(42418, "Interfaces/Tele/BBUTTONS/B", 14, 81, 34, "", -1, 42419, 1);
        addHoverButton(42420, "Interfaces/Tele/BBUTTONS/B", 15, 81, 34, "", -1, 42421, 1);
        addHoverButton(42422, "Interfaces/Tele/BBUTTONS/B", 16, 81, 34, "", -1, 42423, 1);
        addHoverButton(42424, "Interfaces/Tele/BBUTTONS/B", 17, 81, 34, "", -1, 42425, 1);
        addHoverButton(42426, "Interfaces/Tele/BBUTTONS/B", 18, 81, 34, "", -1, 42427, 1);
        addHoverButton(42428, "Interfaces/Tele/BBUTTONS/B", 19, 81, 34, "", -1, 42429, 1);
        addHoverButton(42430, "Interfaces/Tele/BBUTTONS/B", 20, 81, 34, "", -1, 42431, 1);
        addHoverButton(42432, "Interfaces/Tele/BBUTTONS/B", 21, 81, 34, "", -1, 42433, 1);
        addHoverButton(42434, "Interfaces/Tele/BBUTTONS/B", 22, 81, 34, "", -1, 42435, 1);
        addHoverButton(42436, "Interfaces/Tele/BBUTTONS/B", 23, 81, 34, "", -1, 42437, 1);


        addHoveredButton(42415, "Interfaces/Tele/BBUTTONS/BH", 12, 81, 34, 42616);
        addHoveredButton(42417, "Interfaces/Tele/BBUTTONS/BH", 13, 81, 34, 42618);
        addHoveredButton(42419, "Interfaces/Tele/BBUTTONS/BH", 14, 81, 34, 42620);
        addHoveredButton(42421, "Interfaces/Tele/BBUTTONS/BH", 15, 81, 34, 42622);
        addHoveredButton(42423, "Interfaces/Tele/BBUTTONS/BH", 16, 81, 34, 42624);
        addHoveredButton(42425, "Interfaces/Tele/BBUTTONS/BH", 17, 81, 34, 42626);
        addHoveredButton(42427, "Interfaces/Tele/BBUTTONS/BH", 18, 81, 34, 42628);
        addHoveredButton(42429, "Interfaces/Tele/BBUTTONS/BH", 19, 81, 34, 42630);
        addHoveredButton(42431, "Interfaces/Tele/BBUTTONS/BH", 20, 81, 34, 42632);
        addHoveredButton(42433, "Interfaces/Tele/BBUTTONS/BH", 21, 81, 34, 42634);
        addHoveredButton(42435, "Interfaces/Tele/BBUTTONS/BH", 22, 81, 34, 42636);
        addHoveredButton(42437, "Interfaces/Tele/BBUTTONS/BH", 23, 81, 34, 42638);


        addHoverButton(42438, "Interfaces/Tele/ARROW", 1, 29, 24, "Next", -1, 42439, 1);
        addHoverButton(42440, "Interfaces/Tele/ARROW", 0, 29, 24, "Back", -1, 42441, 1);


        addText(50430, "PaeScape Teleports", fonts, 2, 0xff9040, true, true);

        int last = 35;
        RSinterface.children = new int[last];
        RSinterface.childX = new int[last];
        RSinterface.childY = new int[last];
        setBounds(42401, 12, 12, 0, RSinterface);
        setBounds(42402, 19, 34, 1, RSinterface);
        setBounds(42404, 19, 89, 2, RSinterface);
        setBounds(42406, 19, 144, 3, RSinterface);
        setBounds(42408, 19, 199, 4, RSinterface);
        setBounds(42410, 19, 254, 5, RSinterface);

        setBounds(42412, 473, 16, 6, RSinterface);//close
        setBounds(42413, 473, 16, 19, RSinterface);//close


        //buttons
        int one = 180;
        int two = 285;
        int three = 390;


        setBounds(42414, one, 60, 7, RSinterface);
        setBounds(42415, one, 60, 20, RSinterface);
        setBounds(42416, two, 60, 8, RSinterface);
        setBounds(42417, two, 60, 21, RSinterface);
        setBounds(42418, three, 60, 9, RSinterface);
        setBounds(42419, three, 60, 22, RSinterface);
        setBounds(42420, one, 119, 10, RSinterface);
        setBounds(42421, one, 119, 23, RSinterface);
        setBounds(42422, two, 119, 11, RSinterface);
        setBounds(42423, two, 119, 24, RSinterface);
        setBounds(42424, three, 119, 12, RSinterface);
        setBounds(42425, three, 119, 25, RSinterface);
        setBounds(42426, one, 178, 13, RSinterface);
        setBounds(42427, one, 178, 26, RSinterface);
        setBounds(42428, two, 178, 14, RSinterface);
        setBounds(42429, two, 178, 27, RSinterface);
        setBounds(42430, three, 178, 15, RSinterface);
        setBounds(42431, three, 178, 28, RSinterface);
        setBounds(42432, one, 237, 16, RSinterface);
        setBounds(42433, one, 237, 29, RSinterface);
        setBounds(42434, two, 237, 17, RSinterface);
        setBounds(42435, two, 237, 30, RSinterface);
        setBounds(42436, three, 237, 18, RSinterface);
        setBounds(42437, three, 237, 31, RSinterface);
        setBounds(42438, 337, 280, 32, RSinterface);
        setBounds(42440, 283, 280, 33, RSinterface);

        setBounds(50430, two, 16, 34, RSinterface);

    }

    public static void TeleTAB3_3() {
        RSInterface RSinterface = addTabInterface(43400);

        addSprite(43401, 0, "Interfaces/Tele/BG");

        addHoverButton(43402, "Interfaces/Tele/TAB/TAB", 0, 130, 55, "Training", -1, 43403, 1);
        addHoverButton(43404, "Interfaces/Tele/TAB/TAB", 1, 130, 55, "Dungeons", -1, 43405, 1);
        addHoverButton(43406, "Interfaces/Tele/TAB/TABL", 2, 130, 55, "Bosses", -1, 43407, 1);
        addHoverButton(43408, "Interfaces/Tele/TAB/TAB", 3, 130, 55, "Minigames", -1, 43409, 1);
        addHoverButton(43410, "Interfaces/Tele/TAB/TAB", 4, 130, 55, "Wilderness", -1, 43411, 1);

        addHoverButton(43412, "Interfaces/Tele/Close", 0, 16, 16, "Close", -1, 43413, 1);
        addHoveredButton(43413, "Interfaces/Tele/Close", 1, 16, 16, 43614);


        //buttons

        addHoverButton(43414, "Interfaces/Tele/BBUTTONS/B", 24, 81, 34, "", -1, 43415, 1);
        addHoverButton(43416, "Interfaces/Tele/BBUTTONS/B", 25, 81, 34, "", -1, 43417, 1);
        addHoverButton(43418, "Interfaces/Tele/BBUTTONS/B", 26, 81, 34, "", -1, 43419, 1);
        addHoverButton(43420, "Interfaces/Tele/BBUTTONS/B", 27, 81, 34, "", -1, 43421, 1);
        addHoverButton(43422, "Interfaces/Tele/BBUTTONS/B", 28, 81, 34, "", -1, 43423, 1);
        addHoverButton(43424, "Interfaces/Tele/BBUTTONS/B", 29, 81, 34, "", -1, 43425, 1);
        addHoverButton(43426, "Interfaces/Tele/BBUTTONS/B", 30, 81, 34, "", -1, 43427, 1);
        //addHoverButton(42428, "Interfaces/Tele/BBUTTONS/B", 19, 81, 34, "", -1, 42429, 1);
        //addHoverButton(42430, "Interfaces/Tele/BBUTTONS/B", 20, 81, 34, "", -1, 42431, 1);
        //addHoverButton(42432, "Interfaces/Tele/BBUTTONS/B", 21, 81, 34, "", -1, 42433, 1);
        //addHoverButton(42434, "Interfaces/Tele/BBUTTONS/B", 22, 81, 34, "", -1, 42435, 1);
        //addHoverButton(42436, "Interfaces/Tele/BBUTTONS/B", 23, 81, 34, "", -1, 42437, 1);


        addHoveredButton(43415, "Interfaces/Tele/BBUTTONS/BH", 24, 81, 34, 43616);
        addHoveredButton(43417, "Interfaces/Tele/BBUTTONS/BH", 25, 81, 34, 43618);
        addHoveredButton(43419, "Interfaces/Tele/BBUTTONS/BH", 26, 81, 34, 43620);
        addHoveredButton(43421, "Interfaces/Tele/BBUTTONS/BH", 27, 81, 34, 43622);
        addHoveredButton(43423, "Interfaces/Tele/BBUTTONS/BH", 28, 81, 34, 43624);
        addHoveredButton(43425, "Interfaces/Tele/BBUTTONS/BH", 29, 81, 34, 43626);
        addHoveredButton(43427, "Interfaces/Tele/BBUTTONS/BH", 30, 81, 34, 43628);
        //addHoveredButton(42429, "Interfaces/Tele/BBUTTONS/BH", 19, 81, 34, 42630);
        //addHoveredButton(42431, "Interfaces/Tele/BBUTTONS/BH", 20, 81, 34, 42632);
        //addHoveredButton(42433, "Interfaces/Tele/BBUTTONS/BH", 21, 81, 34, 42634);
        //addHoveredButton(42435, "Interfaces/Tele/BBUTTONS/BH", 22, 81, 34, 42636);
        //addHoveredButton(42437, "Interfaces/Tele/BBUTTONS/BH", 23, 81, 34, 42638);


        //addHoverButton(42438, "Interfaces/Tele/ARROW", 1, 29, 24, "Next", -1, 43439, 1);
        addHoverButton(43440, "Interfaces/Tele/ARROW", 0, 29, 24, "Back", -1, 43441, 1);


        addText(50430, "PaeScape Teleports", fonts, 2, 0xff9040, true, true);

        int last = 24;
        RSinterface.children = new int[last];
        RSinterface.childX = new int[last];
        RSinterface.childY = new int[last];
        setBounds(43401, 12, 12, 0, RSinterface);
        setBounds(43402, 19, 34, 1, RSinterface);
        setBounds(43404, 19, 89, 2, RSinterface);
        setBounds(43406, 19, 144, 3, RSinterface);
        setBounds(43408, 19, 199, 4, RSinterface);
        setBounds(43410, 19, 254, 5, RSinterface);

        setBounds(43412, 473, 16, 6, RSinterface);//close
        setBounds(43413, 473, 16, 7, RSinterface);//close


        //buttons
        int one = 180;
        int two = 285;
        int three = 390;


        setBounds(43414, one, 60, 8, RSinterface);
        setBounds(43415, one, 60, 9, RSinterface);
        setBounds(43416, two, 60, 10, RSinterface);
        setBounds(43417, two, 60, 11, RSinterface);
        setBounds(43418, three, 60, 12, RSinterface);
        setBounds(43419, three, 60, 13, RSinterface);
        setBounds(43420, one, 119, 14, RSinterface);
        setBounds(43421, one, 119, 15, RSinterface);
        setBounds(43422, two, 119, 16, RSinterface);
        setBounds(43423, two, 119, 17, RSinterface);
        setBounds(43424, three, 119, 18, RSinterface);
        setBounds(43425, three, 119, 19, RSinterface);
        setBounds(43426, one, 178, 20, RSinterface);
        setBounds(43427, one, 178, 21, RSinterface);
        //setBounds(42428, two, 178, 14,RSinterface);
        //setBounds(42429, two, 178, 27,RSinterface);
        //setBounds(42430, three, 178, 15,RSinterface);
        //setBounds(42431, three, 178, 28,RSinterface);
        //setBounds(42432, one, 237, 16,RSinterface);
        //setBounds(42433, one, 237, 29,RSinterface);
        //setBounds(42434, two, 237, 17,RSinterface);
        //setBounds(42435, two, 237, 30,RSinterface);
        //setBounds(42436, three, 237, 18,RSinterface);
        //setBounds(42437, three, 237, 31,RSinterface);
        //setBounds(42438, 337, 280, 32,RSinterface);
        setBounds(43440, 283, 280, 22, RSinterface);

        setBounds(50430, two, 16, 23, RSinterface);

    }

    public static void TeleTAB4() {
        RSInterface RSinterface = addTabInterface(39400);

        addSprite(39401, 0, "Interfaces/Tele/BG");

        addHoverButton(39402, "Interfaces/Tele/TAB/TAB", 0, 130, 55, "Training", -1, 39403, 1);


        addHoverButton(39404, "Interfaces/Tele/TAB/TAB", 1, 130, 55, "Deungeon", -1, 39405, 1);
        addHoverButton(39406, "Interfaces/Tele/TAB/TAB", 2, 130, 55, "Boss", -1, 39407, 1);
        addHoverButton(39408, "Interfaces/Tele/TAB/TABL", 3, 130, 55, "MiniGame", -1, 39409, 1);
        addHoverButton(39410, "Interfaces/Tele/TAB/TAB", 4, 130, 55, "Wilderness", -1, 39411, 1);

        addHoverButton(39412, "Interfaces/Tele/Close", 0, 16, 16, "Close", -1, 39413, 1);
        addHoveredButton(39413, "Interfaces/Tele/Close", 1, 16, 16, 39614);


        //buttons

        addHoverButton(39414, "Interfaces/Tele/MBUTTONS/B", 0, 81, 34, "", -1, 39415, 1);
        addHoverButton(39416, "Interfaces/Tele/MBUTTONS/B", 1, 81, 34, "", -1, 39417, 1);
        addHoverButton(39418, "Interfaces/Tele/MBUTTONS/B", 2, 81, 34, "", -1, 39419, 1);
        addHoverButton(39420, "Interfaces/Tele/MBUTTONS/B", 3, 81, 34, "", -1, 39421, 1);
        addHoverButton(39422, "Interfaces/Tele/MBUTTONS/B", 4, 81, 34, "", -1, 39423, 1);
        addHoverButton(39424, "Interfaces/Tele/MBUTTONS/B", 5, 81, 34, "", -1, 39425, 1);
        addHoverButton(39426, "Interfaces/Tele/MBUTTONS/B", 6, 81, 34, "", -1, 39427, 1);
        addHoverButton(39428, "Interfaces/Tele/MBUTTONS/B", 7, 81, 34, "", -1, 39429, 1);

        addHoveredButton(39415, "Interfaces/Tele/MBUTTONS/BH", 0, 81, 34, 39616);
        addHoveredButton(39417, "Interfaces/Tele/MBUTTONS/BH", 1, 81, 34, 39618);
        addHoveredButton(39419, "Interfaces/Tele/MBUTTONS/BH", 2, 81, 34, 39620);
        addHoveredButton(39421, "Interfaces/Tele/MBUTTONS/BH", 3, 81, 34, 39622);
        addHoveredButton(39423, "Interfaces/Tele/MBUTTONS/BH", 4, 81, 34, 39624);
        addHoveredButton(39425, "Interfaces/Tele/MBUTTONS/BH", 5, 81, 34, 39626);
        addHoveredButton(39427, "Interfaces/Tele/MBUTTONS/BH", 6, 81, 34, 39628);
        addHoveredButton(39429, "Interfaces/Tele/MBUTTONS/BH", 7, 81, 34, 39630);


        addText(50430, "PaeScape Teleports", fonts, 2, 0xff9040, true, true);


        int last = 25;
        RSinterface.children = new int[last];
        RSinterface.childX = new int[last];
        RSinterface.childY = new int[last];
        setBounds(39401, 12, 12, 0, RSinterface);
        setBounds(39402, 19, 34, 1, RSinterface);
        setBounds(39404, 19, 89, 2, RSinterface);
        setBounds(39406, 19, 144, 3, RSinterface);
        setBounds(39408, 19, 199, 4, RSinterface);
        setBounds(39410, 19, 254, 5, RSinterface);

        setBounds(39412, 473, 16, 6, RSinterface);//close
        setBounds(39413, 473, 16, 7, RSinterface);//close

        //buttons
        int one = 180;
        int two = 285;
        int three = 390;

        setBounds(39414, one, 60, 8, RSinterface);
        setBounds(39415, one, 60, 9, RSinterface);
        setBounds(39416, two, 60, 10, RSinterface);
        setBounds(39417, two, 60, 11, RSinterface);
        setBounds(39418, three, 60, 12, RSinterface);
        setBounds(39419, three, 60, 13, RSinterface);
        setBounds(39420, one, 119, 14, RSinterface);
        setBounds(39421, one, 119, 15, RSinterface);
        setBounds(39422, two, 119, 16, RSinterface);
        setBounds(39423, two, 119, 17, RSinterface);
        setBounds(39424, three, 119, 18, RSinterface);
        setBounds(39425, three, 119, 19, RSinterface);
        setBounds(39426, one, 178, 20, RSinterface);
        setBounds(39427, one, 178, 21, RSinterface);
        setBounds(39428, two, 178, 22, RSinterface);
        setBounds(39429, two, 178, 23, RSinterface);

        setBounds(50430, two, 16, 24, RSinterface);


    }

    public static void TeleTAB5() {
        RSInterface RSinterface = addTabInterface(40400);

        addSprite(40401, 0, "Interfaces/Tele/BG");

        addHoverButton(40402, "Interfaces/Tele/TAB/TAB", 0, 130, 55, "Training", -1, 40403, 1);


        //addHoveredButton(39003, "Interfaces/colorSelection/GoldBox", 0, 19, 20, 39004);

        addHoverButton(40404, "Interfaces/Tele/TAB/TAB", 1, 130, 55, "Deungeon", -1, 40405, 1);
        addHoverButton(40406, "Interfaces/Tele/TAB/TAB", 2, 130, 55, "Boss", -1, 40407, 1);
        addHoverButton(40408, "Interfaces/Tele/TAB/TAB", 3, 130, 55, "MiniGame", -1, 40409, 1);
        addHoverButton(40410, "Interfaces/Tele/TAB/TABL", 4, 130, 55, "Wilderness", -1, 40411, 1);

        addHoverButton(40412, "Interfaces/Tele/Close", 0, 16, 16, "Close", -1, 40413, 1);
        addHoveredButton(40413, "Interfaces/Tele/Close", 1, 16, 16, 40614);


        //buttons

        addHoverButton(40414, "Interfaces/Tele/WBUTTONS/B", 0, 81, 34, "", -1, 40415, 1);
        addHoverButton(40416, "Interfaces/Tele/WBUTTONS/B", 1, 81, 34, "", -1, 40417, 1);
        addHoverButton(40418, "Interfaces/Tele/WBUTTONS/B", 2, 81, 34, "", -1, 40419, 1);
        addHoverButton(40420, "Interfaces/Tele/WBUTTONS/B", 3, 81, 34, "", -1, 40421, 1);
        addHoverButton(40422, "Interfaces/Tele/WBUTTONS/B", 4, 81, 34, "", -1, 40423, 1);
        addHoverButton(40424, "Interfaces/Tele/WBUTTONS/B", 5, 81, 34, "", -1, 40425, 1);
        addHoverButton(40426, "Interfaces/Tele/WBUTTONS/B", 6, 81, 34, "", -1, 40427, 1);
        addHoverButton(40428, "Interfaces/Tele/WBUTTONS/B", 7, 81, 34, "", -1, 40429, 1);
        //addHoverButton(40430, "Interfaces/Tele/DBUTTONS/B", 8, 81, 34, "", -1, 40431, 1);
        //addHoverButton(40432, "Interfaces/Tele/TBUTTONS/B", 9, 81, 34, "", -1, 40433, 1);
        //addHoverButton(40434, "Interfaces/Tele/TBUTTONS/B", 10, 81, 34, "", -1, 40435, 1);
        //addHoverButton(40436, "Interfaces/Tele/TBUTTONS/B", 11, 81, 34, "", -1, 40439, 1);


        addHoveredButton(40415, "Interfaces/Tele/WBUTTONS/BH", 0, 81, 34, 40616);
        addHoveredButton(40417, "Interfaces/Tele/WBUTTONS/BH", 1, 81, 34, 40618);
        addHoveredButton(40419, "Interfaces/Tele/WBUTTONS/BH", 2, 81, 34, 40620);
        addHoveredButton(40421, "Interfaces/Tele/WBUTTONS/BH", 3, 81, 34, 40622);
        addHoveredButton(40423, "Interfaces/Tele/WBUTTONS/BH", 4, 81, 34, 40624);
        addHoveredButton(40425, "Interfaces/Tele/WBUTTONS/BH", 5, 81, 34, 40626);
        addHoveredButton(40427, "Interfaces/Tele/WBUTTONS/BH", 6, 81, 34, 40628);
        addHoveredButton(40429, "Interfaces/Tele/WBUTTONS/BH", 7, 81, 34, 40630);

        //addHoveredButton(40431, "Interfaces/Tele/TBUTTONS/BH", 8, 81, 34, 39632);

        //addHoveredButton(40433, "Interfaces/Tele/TBUTTONS/BH", 9, 81, 34, 39634);

        //addHoveredButton(40435, "Interfaces/Tele/TBUTTONS/BH", 10, 81, 34, 39636);

        //addHoveredButton(40439, "Interfaces/Tele/TBUTTONS/BH", 11, 81, 34, 39639);


        addText(50430, "PaeScape Teleports", fonts, 2, 0xff9040, true, true);

        int last = 25;
        RSinterface.children = new int[last];
        RSinterface.childX = new int[last];
        RSinterface.childY = new int[last];
        setBounds(40401, 12, 12, 0, RSinterface);
        setBounds(40402, 19, 34, 1, RSinterface);
        setBounds(40404, 19, 89, 2, RSinterface);
        setBounds(40406, 19, 144, 3, RSinterface);
        setBounds(40408, 19, 199, 4, RSinterface);
        setBounds(40410, 19, 254, 5, RSinterface);

        setBounds(40412, 473, 16, 6, RSinterface);//close
        setBounds(40413, 473, 16, 7, RSinterface);//close

        //buttons
        int one = 180;
        int two = 285;
        int three = 390;


        setBounds(40414, one, 60, 8, RSinterface);
        setBounds(40415, one, 60, 9, RSinterface);
        setBounds(40416, two, 60, 10, RSinterface);
        setBounds(40417, two, 60, 11, RSinterface);
        setBounds(40418, three, 60, 12, RSinterface);
        setBounds(40419, three, 60, 13, RSinterface);

        setBounds(40420, one, 119, 14, RSinterface);
        setBounds(40421, one, 119, 15, RSinterface);
        setBounds(40422, two, 119, 16, RSinterface);
        setBounds(40423, two, 119, 17, RSinterface);
        setBounds(40424, three, 119, 18, RSinterface);
        setBounds(40425, three, 119, 19, RSinterface);

        setBounds(40426, one, 178, 20, RSinterface);
        setBounds(40427, one, 178, 21, RSinterface);
        setBounds(40428, two, 178, 22, RSinterface);
        setBounds(40429, two, 178, 23, RSinterface);


        setBounds(50430, two, 16, 24, RSinterface);
        //setBounds(37430, three, 178, 15,RSinterface);
        //setBounds(37431, three, 178, 28,RSinterface);

        //setBounds(37432, one, 237, 16,RSinterface);
        //setBounds(37433, one, 237, 29,RSinterface);
        //setBounds(37434, two, 237, 17,RSinterface);
        //setBounds(37435, two, 237, 30,RSinterface);
        //setBounds(37436, three, 237, 18,RSinterface);
        //setBounds(37437, three, 237, 31,RSinterface);


    }

    public static void editClan(TextDrawingArea[] tda) {
        RSInterface tab = addTabInterface(40172);
        addSpriteLoader(47251, 736);
        addHoverSpriteLoaderButton(47252, 726, 150, 35, "Set name", 22222, 47253, 1);
        addHoveredSpriteLoaderButton(47253, 150, 35, 47254, 727);
        addHoverSpriteLoaderButton(47255, 726, 150, 35, "Anyone", -1, 47256, 1);
        addHoveredSpriteLoaderButton(47256, 150, 35, 47257, 727);

        addHoverButton(48000, "b", 1, 150, 35, "Only me", -1, 47999, 1);
        addHoverButton(48001, "b", 1, 150, 35, "General+", -1, 47999, 1);
        addHoverButton(48002, "b", 1, 150, 35, "Captain+", -1, 47999, 1);
        addHoverButton(48003, "b", 1, 150, 35, "Lieutenant+", -1, 47999, 1);
        addHoverButton(48004, "b", 1, 150, 35, "Sergeant+", -1, 47999, 1);
        addHoverButton(48005, "b", 1, 150, 35, "Corporal+", -1, 47999, 1);
        addHoverButton(48006, "b", 1, 150, 35, "Recruit+", -1, 47999, 1);
        addHoverButton(48007, "b", 1, 150, 35, "Any friends", -1, 47999, 1);

        addHoverSpriteLoaderButton(47258, 726, 150, 35, "Anyone", -1, 47259, 1);
        addHoveredSpriteLoaderButton(47259, 150, 35, 17260, 727);

        addHoverButton(48010, "b", 1, 150, 35, "Only me", -1, 47999, 1);
        addHoverButton(48011, "b", 1, 150, 35, "General+", -1, 47999, 1);
        addHoverButton(48012, "b", 1, 150, 35, "Captain+", -1, 47999, 1);
        addHoverButton(48013, "b", 1, 150, 35, "Lieutenant+", -1, 47999, 1);
        addHoverButton(48014, "b", 1, 150, 35, "Sergeant+", -1, 47999, 1);
        addHoverButton(48015, "b", 1, 150, 35, "Corporal+", -1, 47999, 1);
        addHoverButton(48016, "b", 1, 150, 35, "Recruit+", -1, 47999, 1);
        addHoverButton(48017, "b", 1, 150, 35, "Any friends", -1, 47999, 1);

        addHoverSpriteLoaderButton(47261, 726, 150, 35, "Only me", -1, 47262, 1);
        addHoveredSpriteLoaderButton(47262, 150, 35, 47263, 727);

        // addHoverButton(48020, "b", 1, 150, 35, "Only me", -1, 47999, 1);
        addHoverButton(48021, "b", 1, 150, 35, "General+", -1, 47999, 1);
        addHoverButton(48022, "b", 1, 150, 35, "Captain+", -1, 47999, 1);
        addHoverButton(48023, "b", 1, 150, 35, "Lieutenant+", -1, 47999, 1);
        addHoverButton(48024, "b", 1, 150, 35, "Sergeant+", -1, 47999, 1);
        addHoverButton(48025, "b", 1, 150, 35, "Corporal+", -1, 47999, 1);
        addHoverButton(48026, "b", 1, 150, 35, "Recruit+", -1, 47999, 1);

        addHoverSpriteLoaderButton(47267, 737, 16, 16, "Close", -1, 47268, 1);
        addHoveredSpriteLoaderButton(47268, 16, 16, 47269, 738);

        addText(47800, "Clan name:", tda, 0, 0xff981f, false, true);
        addText(47801, "Who can enter chat?", tda, 0, 0xff981f, false, true);
        addText(47812, "Who can talk in chat?", tda, 0, 0xff981f, false, true);
        addText(47813, "Who can kick from chat?", tda, 0, 0xff981f, false, true);
        addText(47814, "Name", tda, 0, 0xffffff, true, true);
        addText(47815, "Anyone", tda, 0, 0xffffff, true, true);
        addText(47816, "Anyone", tda, 0, 0xffffff, true, true);
        addText(47817, "Only me", tda, 0, 0xffffff, true, true);
        tab.totalChildren(40);
        tab.child(0, 47251, 180, 15);
        tab.child(1, 47252, 190, 47 + 20);
        tab.child(2, 47253, 190, 47 + 20);
        tab.child(3, 47267, 327, 22);
        tab.child(4, 47268, 327, 22);
        tab.child(5, 48000, 190, 87 + 25);
        tab.child(6, 48001, 190, 87 + 25);
        tab.child(7, 48002, 190, 87 + 25);
        tab.child(8, 48003, 190, 87 + 25);
        tab.child(9, 48004, 190, 87 + 25);
        tab.child(10, 48005, 190, 87 + 25);
        tab.child(11, 48006, 190, 87 + 25);
        tab.child(12, 48007, 190, 87 + 25);
        tab.child(13, 47255, 190, 87 + 25);
        tab.child(14, 47256, 190, 87 + 25);
        tab.child(15, 48010, 190, 128 + 30);
        tab.child(16, 48011, 190, 128 + 30);
        tab.child(17, 48012, 190, 128 + 30);
        tab.child(18, 48013, 190, 128 + 30);
        tab.child(19, 48014, 190, 128 + 30);
        tab.child(20, 48015, 190, 128 + 30);
        tab.child(21, 48016, 190, 128 + 30);
        tab.child(22, 48017, 190, 128 + 30);
        tab.child(23, 47258, 190, 128 + 30);
        //tab.child(24, 47259, 190, 128 + 30);
        // tab.child(25, 48020, 25, 168+35);
        tab.child(24, 48021, 190, 168 + 35);
        tab.child(25, 48022, 190, 168 + 35);
        tab.child(26, 48023, 190, 168 + 35);
        tab.child(27, 48024, 190, 168 + 35);
        tab.child(28, 48025, 190, 168 + 35);
        tab.child(29, 48026, 190, 168 + 35);
        tab.child(30, 47261, 190, 168 + 35);
        tab.child(31, 47262, 190, 168 + 35);
        tab.child(32, 47800, 238, 54 + 20);
        tab.child(33, 47801, 215, 95 + 25);
        tab.child(34, 47812, 215, 136 + 30);
        tab.child(35, 47813, 215, 177 + 35);
        tab.child(36, 47814, 265, 54 + 20 + 12);
        tab.child(37, 47815, 265, 95 + 25 + 12);
        tab.child(38, 47816, 265, 136 + 30 + 12);
        tab.child(39, 47817, 265, 177 + 35 + 12);

    }

    public static void addHoverText(int id, String text, String tooltip, TextDrawingArea tda[], int idx, int color, boolean center, boolean textShadowed, int width) {
        RSInterface rsinterface = addInterface(id);
        rsinterface.id = id;
        rsinterface.parentID = id;
        rsinterface.type = 4;
        rsinterface.atActionType = 1;
        rsinterface.width = width;
        rsinterface.height = 11;
        rsinterface.contentType = 0;
        rsinterface.opacity = 0;
        rsinterface.hoverType = -1;
        rsinterface.centerText = center;
        rsinterface.shadowed = textShadowed;
        rsinterface.textDrawingAreas = tda[idx];
        rsinterface.message = text;
        rsinterface.enabledMessage = "";
        rsinterface.disabledColor = color;
        rsinterface.tooltip = tooltip;
    }

    public static void playersOnline(TextDrawingArea[] tda) {
        RSInterface tab = addInterface(57000);
        addSpriteLoader(57001, 805);
        addText(57002, "Players Online", 0xff9933, true, true, -1, tda, 2);
        addText(57003, "", 0xff9933, true, true, -1, tda, 1);
        addText(57007, "PaeScape", 0xff9933, true, true, -1, tda, 0);
        addText(57008, "", 0xff9933, true, true, -1, tda, 3);
        addText(57009, "", 0xff9933, false, true, -1, tda, 0);
        addText(57010, "", 0xff9933, false, true, -1, tda, 0);
        addText(57011, "", 0xff9933, false, true, -1, tda, 0);
        addText(57012, "", 0xff9933, false, true, -1, tda, 0);
        addText(57013, "", 0xff9933, false, true, -1, tda, 0);
        addText(57014, "", 0xff9933, false, true, -1, tda, 0);
        addText(57015, "", 0xff9933, false, true, -1, tda, 0);
        addText(57016, "", 0xff9933, false, true, -1, tda, 0);
        addText(57017, "", 0xff9933, false, true, -1, tda, 0);
        addText(57018, "", 0xff9933, false, true, -1, tda, 0);
        addText(57019, "", 0xff9933, false, true, -1, tda, 0);
        addText(57020, "", 0xff9933, false, true, -1, tda, 0);
        addText(57021, "", 0xff9933, false, true, -1, tda, 0);
        addText(57022, "", 0xff9933, false, true, -1, tda, 0);
        addText(57023, "", 0xff9933, false, true, -1, tda, 0);
        addText(57024, "", 0xff9933, false, true, -1, tda, 0);
        addCloseButton(57004, 57005, 57006);
        addHoverButtonWSpriteLoader(57025, 806, 73, 35, "Add", -1, 57026, 3);
        addHoveredImageWSpriteLoader(57026, 807, 73, 35, 57027);
        addHoverButtonWSpriteLoader(57028, 806, 73, 35, "Ignore", -1, 57029, 3);
        addHoveredImageWSpriteLoader(57029, 807, 73, 35, 57030);
        addText(57031, "Add", 0xff9933, true, true, -1, tda, 0);
        addText(57032, "Ignore", 0xff9933, true, true, -1, tda, 0);
        tab.totalChildren(30);
        tab.child(0, 57001, 14, 5);
        tab.child(1, 57040, 176, 90);
        tab.child(2, 57002, 175, 18);
        tab.child(3, 57003, 415, 58);
        tab.child(4, 57004, 470, 16);
        tab.child(5, 57005, 470, 16);
        tab.child(6, 57007, 415, 20);
        tab.child(7, 57008, 175, 65);
        tab.child(8, 57009, 60, 110);
        tab.child(9, 57010, 60, 125);
        tab.child(10, 57011, 60, 140);
        tab.child(11, 57012, 60, 155);
        tab.child(12, 57013, 60, 170);
        tab.child(13, 57014, 60, 185);
        tab.child(14, 57015, 60, 200);
        tab.child(15, 57016, 60, 215);
        tab.child(16, 57017, 187, 110);
        tab.child(17, 57018, 187, 125);
        tab.child(18, 57019, 187, 140);
        tab.child(19, 57020, 187, 155);
        tab.child(20, 57021, 187, 170);
        tab.child(21, 57022, 187, 185);
        tab.child(22, 57023, 187, 200);
        tab.child(23, 57024, 187, 215);
        tab.child(24, 57025, 80, 262);
        tab.child(25, 57026, 80, 262);
        tab.child(26, 57028, 202, 262);
        tab.child(27, 57029, 202, 262);
        tab.child(28, 57031, 114, 272);
        tab.child(29, 57032, 238, 272);
        RSInterface scrollInterface = addTabInterface(57040);
        scrollInterface.width = 300;
        scrollInterface.height = 231;
        scrollInterface.scrollMax = 1330;
        setChildren(100, scrollInterface);
        /**
         * just need to remove that last line
         */


        int y = -4;
        for (int i = 0; i < 100; i++) {
            addHoverText(57041 + i, "", "Select", tda, 3, 0xff9633, false, true, 100, 14);
            setBounds(57041 + i, 178, y, i, scrollInterface);
            y += 15;
        }
    }

    public static void godWars() {
        RSInterface tab = addTabInterface(16210);
        addTransparentSpriteWSpriteLoader(16220, 958, 150);

        addText(16211, "   ", fonts, 2, 0x66FFFF, true, true);
        addText(16212, " Armadyl kills ", fonts, 2, 16750623, true, true);
        addText(16213, "Bandos Kills ", fonts, 2, 16750623, true, true);
        addText(16214, "     Saradomin Kills ", fonts, 2, 16750623, true, true);
        addText(16215, "  Zamorak Kills ", fonts, 2, 16750623, true, true);
        addText(16216, "0", fonts, 2, 16777215, true, true);
        addText(16217, "0", fonts, 2, 16777215, true, true);
        addText(16218, "0", fonts, 2, 16777215, true, true);
        addText(16219, "0", fonts, 2, 16777215, true, true);
        tab.totalChildren(10);
        tab.child(9, 16211, 454, 196);
        tab.child(1, 16212, 449, 206);
        tab.child(2, 16213, 449, 224);
        tab.child(3, 16214, 449, 241);
        tab.child(4, 16215, 449, 259);
        tab.child(5, 16216, 389, 206);
        tab.child(6, 16217, 389, 224);
        tab.child(7, 16218, 389, 241);
        tab.child(8, 16219, 389, 259);
        tab.child(0, 16220, 373, 195);

    }

    public static void shortCutInterface() {
        RSInterface scInterface = addTabInterface(17930);
        setChildren(14, scInterface);
        addSprite(17931, 0, "Interfaces/Shortcut/s");
        //row1
        addButton(17932, -1, "Empty Inventory", 93, 55, "Empty Inventory", 5);
        addButton(17933, -1, "Request Help", 93, 55, "Request Help", 5);
        addButton(17934, -1, "Change Password", 93, 55, "Change Password", 5);

        //row2
        addButton(17935, -1, "Set/Change Email", 93, 55, "Set/Change Email", 5);
        addButton(17936, -1, "Redeem Auth Code", 93, 55, "Redeem Auth Code", 5);
        addButton(17937, -1, "Redeem Purchase", 93, 55, "Redeem Purchase", 5);

        //row3
        addButton(17938, -1, "Vote", 93, 55, "Vote", 5);
        addButton(17939, -1, "Store", 93, 55, "Store", 5);
        addButton(17940, -1, "Highscores", 93, 55, "Highscores", 5);

        //row4
        addButton(17941, -1, "Support Us!", 93, 55, "Support Us!", 5);
        addButton(17942, -1, "Forum", 93, 55, "Forum", 5);
        addButton(17943, -1, "Rules", 93, 55, "Rules", 5);

        addHoverButton(17944, 141, 141, 21, 21, "Close", 250, 17944, 3);

        scInterface.setBounds(17931, 13, 6, 0, scInterface);
        scInterface.setBounds(17932, 48, 39, 1, scInterface);
        scInterface.setBounds(17933, 194, 39, 2, scInterface);
        scInterface.setBounds(17934, 343, 39, 3, scInterface);
        scInterface.setBounds(17935, 48, 107, 4, scInterface);
        scInterface.setBounds(17936, 194, 107, 5, scInterface);
        scInterface.setBounds(17937, 343, 107, 6, scInterface);
        scInterface.setBounds(17938, 48, 181, 7, scInterface);
        scInterface.setBounds(17939, 194, 181, 8, scInterface);
        scInterface.setBounds(17940, 343, 181, 9, scInterface);
        scInterface.setBounds(17941, 48, 248, 10, scInterface);
        scInterface.setBounds(17942, 194, 248, 11, scInterface);
        scInterface.setBounds(17943, 343, 248, 12, scInterface);
        scInterface.setBounds(17944, 477, 8, 13, scInterface);
    }

    public static void optionsInterface() {
        String[] options = new String[]{"N/A", "Old Hits", "10x Hits"};
        String[] options2 = new String[]{"Cursors", "Smilies", "Chat Filter"};
        String[] options3 = new String[]{"525 Frame", "HD Textures", "FOG"};
        String[] options4 = new String[]{"Absorption", "Save Input", "Anti Aliasing"};
        RSInterface rsi = addTabInterface(35560);
        setChildren((options.length * 3), rsi);
        int frame = 0, id = 35563, y = 0, x = 1;

        for (int i = 0; i < options.length; i++) {
            addRectangle(id, 128, 0x000000, true, 100, 20);
            setBounds(id, x, y, frame, rsi);
            id++;
            frame++;
            addText(id, options[i], fonts, 1, 0xff9b00, true);
            setBounds(id, x + 40, y + 3, frame, rsi);
            id++;
            frame++;
            addToggleButton(id, 132, 1200 + i, 15, 15, "Toggle");
            setBounds(id, x + 84, y + 3, frame, rsi);
            id++;
            frame++;
            y += 21;
        }

        RSInterface rsi2 = addTabInterface(35580);
        setChildren((options2.length * 3), rsi2);
        x = 29;
        y = 0;
        frame = 0;
        id = 35582;
        for (int i = 0; i < options2.length; i++) {
            addRectangle(id, 128, 0x000000, true, 100, 20);
            setBounds(id, x + 10, y, frame, rsi2);
            id++;
            frame++;
            addText(id, options2[i], fonts, 1, 0xff9b00, true);
            setBounds(id, x + 50, y + 3, frame, rsi2);
            id++;
            frame++;
            addToggleButton(id, 132, 1205 + i, 15, 15, "Toggle");
            setBounds(id, x + 94, y + 3, frame, rsi2);
            id++;
            frame++;
            y += 21;
        }

        RSInterface rsi3 = addTabInterface(35610);
        setChildren((options3.length * 3), rsi3);
        x = 29;
        y = 0;
        frame = 0;
        id = 35611;
        for (int i = 0; i < options3.length; i++) {
            addRectangle(id, 128, 0x000000, true, 100, 20);
            setBounds(id, x + 10, y, frame, rsi3);
            id++;
            frame++;
            addText(id, options3[i], fonts, 1, 0xff9b00, true);
            setBounds(id, x + 50, y + 3, frame, rsi3);
            id++;
            frame++;
            addToggleButton(id, 132, 1210 + i, 15, 15, "Toggle");
            setBounds(id, x + 94, y + 3, frame, rsi3);
            id++;
            frame++;
            y += 21;
        }

        RSInterface rsi4 = addTabInterface(35640);
        setChildren((options4.length * 3), rsi4);
        x = 29;
        y = 0;
        frame = 0;
        id = 35641;
        for (int i = 0; i < options4.length; i++) {
            addRectangle(id, 128, 0x000000, true, 100, 20);
            setBounds(id, x + 10, y, frame, rsi4);
            id++;
            frame++;
            addText(id, options4[i], fonts, 1, 0xff9b00, true);
            setBounds(id, x + 50, y + 3, frame, rsi4);
            id++;
            frame++;
            addToggleButton(id, 132, 1215 + i, 15, 15, "Toggle");
            setBounds(id, x + 94, y + 3, frame, rsi4);
            id++;
            frame++;
            y += 21;
        }
    }

    public static void addBackground(int id, int opacity, int color) {
        RSInterface tab = interfaceCache[id] = new RSInterface();
        tab.disabledColor = color;
        tab.id = id;
        tab.parentID = id;
        tab.type = 11;
        tab.atActionType = 0;
        tab.contentType = 0;
        tab.opacity = (byte) opacity;
    }

    public static void addRectangle(int id, int opacity, int color, boolean filled, int width, int height) {
        RSInterface tab = interfaceCache[id] = new RSInterface();
        tab.disabledColor = color;
        tab.filled = filled;
        tab.id = id;
        tab.parentID = id;
        tab.type = 3;
        tab.atActionType = 0;
        tab.contentType = 0;
        tab.opacity = (byte) opacity;
        tab.width = width;
        tab.height = height;
    }

    public static void addRectangleClickable(int id, int opacity, int color, boolean filled, int width, int height) {
        RSInterface tab = interfaceCache[id] = new RSInterface();
        tab.disabledColor = color;
        tab.filled = filled;
        tab.id = id;
        tab.parentID = id;
        tab.type = 3;
        tab.atActionType = 5;
        tab.contentType = 0;
        tab.opacity = (byte) opacity;
        tab.width = width;
        tab.height = height;
        tab.tooltip = "Select";
    }

    public static void setSelectableValues(int frame, int configId, int requiredValue) {
        RSInterface rsi = interfaceCache[frame];
        rsi.valueCompareType = new int[]{5};
        rsi.requiredValues = new int[]{requiredValue};
        rsi.valueIndexArray = new int[1][3];
        rsi.valueIndexArray[0][0] = 5;
        rsi.valueIndexArray[0][1] = configId;
        rsi.valueIndexArray[0][2] = 0;
    }

    public static void setSelectableValuesSwitchable(int frame, int configId, int requiredValue) {
        RSInterface rsi = interfaceCache[frame];
        rsi.valueCompareType = new int[]{1};
        rsi.requiredValues = new int[]{requiredValue};
        rsi.valueIndexArray = new int[1][3];
        rsi.valueIndexArray[0][0] = 5;
        rsi.valueIndexArray[0][1] = configId;
        rsi.valueIndexArray[0][2] = 0;
    }

    public static void roomChooser() {
        RSInterface tab = addTabInterface(28643);
        addHDSprite(28644, 3, -1);
        addButton(28645, -1, "", 16, 16, "Close", 5);
        setChildren(3, tab);
        int BASEX = 10, BASEY = 10;
        setBoundry(0, 28644, BASEX + 0, BASEY + 0, tab);
        setBoundry(1, 28645, BASEX + 431, BASEY + 5, tab);
        setBoundry(2, 28646, BASEX + 51, BASEY + 60, tab);

        tab = addTabInterface(28646);
        int totalRooms = 23;
        tab.scrollMax = totalRooms * 65;
        tab.width = 330;
        tab.height = 220;
        String[] names = new String[]{"Parlour: Lvl 1", "Garden: Lvl 1", "Kitchen: lvl 5", "Dining room: lvl 10", "Workshop: lvl 15", "Bedroom: Lvl 20", "Hall - Skill Trophies: Lvl 25", "Games Room: Lvl 30", "Combat room: Lvl 32", "Hall - Quest trophies: Lvl 35", "Menagarie: Lvl 37", "Study: Lvl 40", "Costume room: Lvl 42", "Chapel: Lvl 45", "Portal chamber: Lvl 50", "Formal garden: Lvl 55", "Throne room: Lvl 60", "Oubliette: Lvl 65", "Dungeon - corridor: Lvl 70", "Dungeon - junction: Lvl 70", "Dungeon - stairs: Lvl 70", "Dungeon - pit: Lvl 70", "Treasure room: Lvl 75"};
        int[] money = new int[]{1000, 1000, 5000, 5000, 10000, 10000, 15000, 25000, 25000, 25000, 30000, 50000, 50000, 50000, 100000, 75000, 150000, 150000, 7500, 7500, 7500, 10000, 250000};
        int y = 8, x = 4, bounds = 0, id = 28647;
        setChildren(totalRooms * 4, tab);
        for (int i = 0; i < names.length; i++) {
            addRectangleClickable(id, 0, 0x333333, false, 238, 61);
            setBoundry(bounds++, id++, x, y, tab);
            addHDSprite(id, i + 4, -1);
            setBoundry(bounds++, id++, x + 13, y + 5, tab);

            addText(id, names[i], names[i], 0xCCCCFF, 0xCCCCFF, false, true, 0, 1);
            setBoundry(bounds++, id++, x + 78, y + 22, tab);
            addText(id, money[i] + " Coins", money[i] + " Coins", 0xC80000, 0x00C800, false, true, 0, 1);
            interfaceCache[id].valueIndexArray = new int[1][];
            interfaceCache[id].requiredValues = new int[1];
            interfaceCache[id].valueCompareType = new int[1];
            interfaceCache[id].valueIndexArray[0] = new int[4];
            interfaceCache[id].valueIndexArray[0][0] = 4;
            interfaceCache[id].valueIndexArray[0][1] = 3214;
            interfaceCache[id].valueIndexArray[0][2] = 995;
            interfaceCache[id].valueIndexArray[0][3] = 0;
            interfaceCache[id].requiredValues[0] = money[i];
            interfaceCache[id].valueCompareType[0] = 10;
            setBoundry(bounds++, id++, x + 240, y + 25, tab);
            y += 64;

        }
    }

    public static void addFornitureX(int i) {
        RSInterface rsi = interfaceCache[i] = new RSInterface();
        rsi.id = i;
        rsi.parentID = i;
        rsi.type = 5;
        rsi.atActionType = 0;
        rsi.contentType = 0;
        rsi.opacity = 0;
        rsi.enabledSpriteId = 2;
        rsi.width = 512;
        rsi.height = 334;
        setSelectableValues(i, 1000 + i - 38324, 1);
    }

    public static void addFornitureModels(int i) {
        RSInterface Tab = interfaceCache[i] = new RSInterface();
        Tab.actions = new String[5];
        Tab.spritesX = new int[20];
        Tab.invStackSizes = new int[30];
        Tab.inv = new int[30];
        Tab.spritesY = new int[20];
        Tab.children = new int[0];
        Tab.childX = new int[0];
        Tab.childY = new int[0];
        Tab.filled = false;
        Tab.dragDeletes = false;
        Tab.usableItemInterface = false;
        Tab.isInventoryInterface = false;
        Tab.shadowed = false;
        Tab.invSpritePadX = 164;
        Tab.invSpritePadY = 37;
        Tab.type = 2;
        Tab.parentID = 38272;
        Tab.id = 38274;
        Tab.width = 2;
        Tab.height = 4;
    }

    public static void fornitureChooser(TextDrawingArea[] wid) {
        RSInterface rsi = addTabInterface(38272);
        setChildren(59, rsi);
        addHDSprite(38273, 1, -1);
        setBounds(38273, 0, 0, 0, rsi);
        addFornitureModels(38274);
        setBounds(38274, 82, 56, 1, rsi);

        addText(38275, "Name1", wid, 0, 0xff981f, false);
        setBounds(38275, 140, 56, 2, rsi);
        addText(38276, "Req1.1", wid, 0, 0xccccff, false);
        setBounds(38276, 140, 67, 3, rsi);
        addText(38277, "Req1.2", wid, 0, 0xccccff, false);
        setBounds(38277, 140, 76, 4, rsi);
        addText(38278, "Req1.3", wid, 0, 0xccccff, false);
        setBounds(38278, 140, 85, 5, rsi);
        addText(38279, "Req1.4", wid, 0, 0xccccff, false);
        setBounds(38279, 140, 94, 6, rsi);
        addText(38280, "lvl1", wid, 0, 0xff981f, false);
        setBounds(38280, 88, 89, 7, rsi);

        addText(38281, "Name2", wid, 0, 0xff981f, false);
        setBounds(38281, 336, 56, 8, rsi);
        addText(38282, "req2.1", wid, 0, 0xccccff, false);
        setBounds(38282, 336, 67, 9, rsi);
        addText(38283, "req2.2", wid, 0, 0xccccff, false);
        setBounds(38283, 336, 76, 10, rsi);
        addText(38284, "req2.3", wid, 0, 0xccccff, false);
        setBounds(38284, 336, 85, 11, rsi);
        addText(38285, "req2.4", wid, 0, 0xccccff, false);
        setBounds(38285, 336, 94, 12, rsi);
        addText(38286, "lvl2", wid, 0, 0xff981f, false);
        setBounds(38286, 284, 89, 13, rsi);

        addText(38287, "Name3", wid, 0, 0xff981f, false);
        setBounds(38287, 140, 126, 14, rsi);
        addText(38288, "Req3.1", wid, 0, 0xccccff, false);
        setBounds(38288, 140, 135, 15, rsi);
        addText(38289, "Req3.2", wid, 0, 0xccccff, false);
        setBounds(38289, 140, 144, 16, rsi);
        addText(38290, "Req3.3", wid, 0, 0xccccff, false);
        setBounds(38290, 140, 153, 17, rsi);
        addText(38291, "Req3.4", wid, 0, 0xccccff, false);
        setBounds(38291, 140, 162, 18, rsi);
        addText(38292, "lvl3", wid, 0, 0xff981f, false);
        setBounds(38292, 88, 158, 19, rsi);

        addText(38293, "Name4", wid, 0, 0xff981f, false);
        setBounds(38293, 336, 126, 20, rsi);
        addText(38294, "Req4.1", wid, 0, 0xccccff, false);
        setBounds(38294, 336, 135, 21, rsi);
        addText(38295, "Req4.2", wid, 0, 0xccccff, false);
        setBounds(38295, 336, 144, 22, rsi);
        addText(38296, "Req4.3", wid, 0, 0xccccff, false);
        setBounds(38296, 336, 153, 23, rsi);
        addText(38297, "Req4.4", wid, 0, 0xccccff, false);
        setBounds(38297, 336, 162, 24, rsi);
        addText(38298, "lvl4", wid, 0, 0xff981f, false);
        setBounds(38298, 284, 158, 25, rsi);

        addText(38299, "Name5", wid, 0, 0xff981f, false);
        setBounds(38299, 140, 196, 26, rsi);
        addText(38300, "req5.1", wid, 0, 0xccccff, false);
        setBounds(38300, 140, 205, 27, rsi);
        addText(38301, "req5.2", wid, 0, 0xccccff, false);
        setBounds(38301, 140, 214, 28, rsi);
        addText(38302, "req5.3", wid, 0, 0xccccff, false);
        setBounds(38302, 140, 223, 29, rsi);
        addText(38303, "req5.4", wid, 0, 0xccccff, false);
        setBounds(38303, 140, 232, 30, rsi);
        addText(38304, "lvl5", wid, 0, 0xff981f, false);
        setBounds(38304, 89, 228, 31, rsi);

        addText(38305, "Name6", wid, 0, 0xff981f, false);
        setBounds(38305, 336, 196, 32, rsi);
        addText(38306, "req6.1", wid, 0, 0xccccff, false);
        setBounds(38306, 336, 205, 33, rsi);
        addText(38307, "req6.2", wid, 0, 0xccccff, false);
        setBounds(38307, 336, 214, 34, rsi);
        addText(38308, "req6.3", wid, 0, 0xccccff, false);
        setBounds(38308, 336, 223, 35, rsi);
        addText(38309, "req6.4", wid, 0, 0xccccff, false);
        setBounds(38309, 336, 232, 36, rsi);
        addText(38310, "lvl6", wid, 0, 0xff981f, false);
        setBounds(38310, 284, 228, 37, rsi);

        addText(38311, "Name7", wid, 0, 0xff981f, false);
        setBounds(38311, 140, 265, 38, rsi);
        addText(38312, "req7.1", wid, 0, 0xccccff, false);
        setBounds(38312, 140, 274, 39, rsi);
        addText(38313, "req7.2", wid, 0, 0xccccff, false);
        setBounds(38313, 140, 283, 40, rsi);
        addText(38314, "req7.3", wid, 0, 0xccccff, false);
        setBounds(38314, 140, 292, 41, rsi);
        addText(38315, "req7.4", wid, 0, 0xccccff, false);
        setBounds(38315, 140, 301, 42, rsi);
        addText(38316, "lvl7", wid, 0, 0xff981f, false);
        setBounds(38316, 89, 297, 43, rsi);

        addText(38317, "Name8", wid, 0, 0xff981f, false);
        setBounds(38317, 336, 265, 44, rsi);
        addText(38318, "req8.1", wid, 0, 0xccccff, false);
        setBounds(38318, 336, 274, 45, rsi);
        addText(38319, "req8.2", wid, 0, 0xccccff, false);
        setBounds(38319, 336, 283, 46, rsi);
        addText(38320, "req8.3", wid, 0, 0xccccff, false);
        setBounds(38320, 336, 292, 47, rsi);
        addText(38321, "req8.4", wid, 0, 0xccccff, false);
        setBounds(38321, 336, 301, 48, rsi);
        addText(38322, "lvl8", wid, 0, 0xff981f, false);
        setBounds(38322, 284, 297, 49, rsi);
        addButton(38323, -1, "", 16, 16, "Close", 5);
        setBounds(38323, 460, 25, 50, rsi);

        addFornitureX(38324);
        setBounds(38324, 85, 58, 51, rsi);
        addFornitureX(38325);
        setBounds(38325, 282, 58, 52, rsi);
        addFornitureX(38326);
        setBounds(38326, 85, 127, 53, rsi);
        addFornitureX(38327);
        setBounds(38327, 281, 124, 54, rsi);
        addFornitureX(38328);
        setBounds(38328, 79, 194, 55, rsi);
        addFornitureX(38329);
        setBounds(38329, 278, 192, 56, rsi);
        addFornitureX(38330);
        setBounds(38330, 82, 262, 57, rsi);
        addFornitureX(38331);
        setBounds(38331, 276, 267, 58, rsi);
    }

    public static void constructionWaiting() {
        RSInterface tab = addTabInterface(28640);
        addBackground(28641, 0, 0x000000);
        addHDSprite(28642, 0, -1);
        setChildren(2, tab);
        setBoundry(0, 28641, 0, 0, tab);
        setBoundry(1, 28642, 0, 0, tab);

    }

    //clan wars
    public static void arena(TextDrawingArea[] tda) {
        RSInterface tab = addTabInterface(28612);
        tab.scrollMax = 140;
        tab.width = 200;
        tab.height = 75;
        addSpriteSelectable(28613, 132, 133, 15, 15, "Select");
        setSelectableValues(28613, 153, 1);
        addSpriteSelectable(28614, 132, 133, 15, 15, "Select");
        setSelectableValues(28614, 153, 2);
        addSpriteSelectable(28615, 132, 133, 15, 15, "Select");
        setSelectableValues(28615, 153, 3);
        addSpriteSelectable(28616, 132, 133, 15, 15, "Select");
        setSelectableValues(28616, 153, 4);
        addText(28617, "Clan wars classic", tda, 0, 0xC8AA64, false, true);
        addText(28618, "Plateau", tda, 0, 0xC8AA64, false, true);
        addText(28619, "Blasted forest", tda, 0, 0xC8AA64, false, true);
        addText(28620, "Turrets", tda, 0, 0xC8AA64, false, true);

        tab.totalChildren(8);
        tab.child(0, 28613, 0, 2);
        tab.child(1, 28614, 0, 20);
        tab.child(2, 28615, 0, 38);
        tab.child(3, 28616, 0, 56);
        tab.child(4, 28617, 22, 2);
        tab.child(5, 28618, 22, 20);
        tab.child(6, 28619, 22, 38);
        tab.child(7, 28620, 22, 56);
    }

    public static void timeLimit(TextDrawingArea[] tda) {
        RSInterface tab = addTabInterface(28608);
        tab.scrollMax = 140;
        tab.width = 66;
        tab.height = 54;
        addSpriteSelectable(28596, 132, 133, 15, 15, "Select");
        setSelectableValues(28596, 152, 1);
        addSpriteSelectable(28597, 132, 133, 15, 15, "Select");
        setSelectableValues(28597, 152, 2);
        addSpriteSelectable(28598, 132, 133, 15, 15, "Select");
        setSelectableValues(28598, 152, 3);
        addSpriteSelectable(28599, 132, 133, 15, 15, "Select");
        setSelectableValues(28599, 152, 4);
        addSpriteSelectable(28600, 132, 133, 15, 15, "Select");
        setSelectableValues(28600, 152, 5);
        addSpriteSelectable(28601, 132, 133, 15, 15, "Select");
        setSelectableValues(28601, 152, 6);
        addText(28602, "0:05", tda, 0, 0xC8AA64, false, true);
        addText(28603, "0:10", tda, 0, 0xC8AA64, false, true);
        addText(28604, "0:15", tda, 0, 0xC8AA64, false, true);
        addText(28605, "0:30", tda, 0, 0xC8AA64, false, true);
        addText(28606, "1:00", tda, 0, 0xC8AA64, false, true);
        addText(28607, "2:00", tda, 0, 0xC8AA64, false, true);

        tab.totalChildren(12);
        tab.child(0, 28596, 0, 2);
        tab.child(1, 28597, 0, 20);
        tab.child(2, 28598, 0, 38);
        tab.child(3, 28599, 0, 56);
        tab.child(4, 28600, 0, 72);
        tab.child(5, 28601, 0, 90);
        tab.child(6, 28602, 22, 2);
        tab.child(7, 28603, 22, 20);
        tab.child(8, 28604, 22, 38);
        tab.child(9, 28605, 22, 56);
        tab.child(10, 28606, 22, 72);
        tab.child(11, 28607, 22, 90);
    }

    public static void firstTeamTo(TextDrawingArea[] tda) {
        RSInterface tab = addTabInterface(28574);
        tab.scrollMax = 140;
        tab.width = 95;
        tab.height = 66;
        addSpriteSelectable(28575, 132, 133, 15, 15, "Select");
        setSelectableValues(28575, 150, 3);
        addSpriteSelectable(28576, 132, 133, 15, 15, "Select");
        setSelectableValues(28576, 150, 4);
        addSpriteSelectable(28577, 132, 133, 15, 15, "Select");
        setSelectableValues(28577, 150, 5);
        addSpriteSelectable(28578, 132, 133, 15, 15, "Select");
        setSelectableValues(28578, 150, 6);
        addSpriteSelectable(28579, 132, 133, 15, 15, "Select");
        setSelectableValues(28579, 150, 7);
        addSpriteSelectable(28580, 132, 133, 15, 15, "Select");
        setSelectableValues(28580, 150, 8);
        addSpriteSelectable(28581, 132, 133, 15, 15, "Select");
        setSelectableValues(28581, 150, 9);
        addSpriteSelectable(28582, 132, 133, 15, 15, "Select");
        setSelectableValues(28582, 150, 10);
        addText(28583, "25", tda, 0, 0xC8AA64, false, true);
        addText(28584, "50", tda, 0, 0xC8AA64, false, true);
        addText(28585, "100", tda, 0, 0xC8AA64, false, true);
        addText(28586, "200", tda, 0, 0xC8AA64, false, true);
        addText(28587, "500", tda, 0, 0xC8AA64, false, true);
        addText(28588, "1000", tda, 0, 0xC8AA64, false, true);
        addText(28589, "2000", tda, 0, 0xC8AA64, false, true);
        addText(28590, "5000", tda, 0, 0xC8AA64, false, true);

        tab.totalChildren(16);
        tab.child(0, 28575, 0, 2);
        tab.child(1, 28576, 0, 20);
        tab.child(2, 28577, 0, 38);
        tab.child(3, 28578, 0, 56);
        tab.child(4, 28579, 0, 72);
        tab.child(5, 28580, 0, 90);
        tab.child(6, 28581, 0, 108);
        tab.child(7, 28582, 0, 126);
        tab.child(8, 28583, 22, 2);
        tab.child(9, 28584, 22, 20);
        tab.child(10, 28585, 22, 38);
        tab.child(11, 28586, 22, 56);
        tab.child(12, 28587, 22, 72);
        tab.child(13, 28588, 22, 90);
        tab.child(14, 28589, 22, 108);
        tab.child(15, 28590, 22, 126);
    }

    public static void clanswarsOptions(TextDrawingArea[] tda) {
        RSInterface tab = addTabInterface(28560);
        addHDSprite(28561, 122, 122);
        addText(28562, "Clan wars options: Challenging someone", tda, 0, 0xFF981F, true, true);

        addHoverButton(28563, CLOSE_BUTTON, CLOSE_BUTTON, 16, 16, "Close", 250, 28564, 3);
        addHoveredButton(28564, CLOSE_BUTTON_HOVER, CLOSE_BUTTON_HOVER, 16, 16, 28565);

        addHoverButton(28566, MEDIUM_BUTTON, MEDIUM_BUTTON, 100, 35, "Accept", 0, 28567, 1);
        addHoveredButton(28567, MEDIUM_BUTTON, MEDIUM_BUTTON, 16, 16, 28568);

        addText(28569, "Accept", tda, 0, 0x6EC800, true, true);

        addSpriteSelectable(28570, 132, 133, 15, 15, "Select");
        setSelectableValues(28570, 150, 1);
        addText(28571, "Knock-out\\n(No run-ins)", tda, 0, 0xC8AA64, true, true);
        addSpriteSelectable(28572, 132, 133, 15, 15, "Select");
        setSelectableValues(28572, 150, 2);
        addText(28573, "Most kills at end", tda, 0, 0xC8AA64, true, true);
        addText(28591, "First team to...", tda, 0, 0xC8AA64, false, true);
        firstTeamTo(tda);

        addSpriteSelectable(28592, 132, 133, 15, 15, "Select");
        setSelectableValues(28592, 151, 1);
        addSpriteSelectable(28593, 132, 133, 15, 15, "Select");
        setSelectableValues(28593, 151, 2);
        addText(28594, "Kill 'em all", tda, 0, 0xC8AA64, false, true);
        addText(28595, "Ignore 5", tda, 0, 0xC8AA64, false, true);
        timeLimit(tda);
        addSpriteSelectable(28609, 132, 133, 15, 15, "Select");
        setSelectableValues(28609, 152, 7);
        addText(28610, "No limit", tda, 0, 0xC8AA64, false, true);
        addText(28611, "... You keep\\nyour items.", tda, 0, 0xC8AA64, false, true);
        arena(tda);

        addSpriteSelectable(28621, 128, 128, 52, 52, "Select");
        addSpriteSelectable(28622, 134, 133, 15, 15, "Select");
        setSelectableValuesSwitchable(28622, 155, 1);

        addSpriteSelectable(28623, 127, 127, 52, 52, "Select");
        addSpriteSelectable(28624, 134, 133, 15, 15, "Select");
        setSelectableValuesSwitchable(28624, 156, 1);

        addSpriteSelectable(28625, 129, 129, 52, 52, "Select");
        addSpriteSelectable(28626, 134, 133, 15, 15, "Select");
        setSelectableValuesSwitchable(28626, 157, 1);

        addSpriteSelectable(28627, 126, 126, 52, 52, "Select");
        addSpriteSelectable(28628, 134, 133, 15, 15, "Select");
        setSelectableValuesSwitchable(28628, 158, 1);

        addSpriteSelectable(28629, 125, 125, 52, 52, "Select");
        addSpriteSelectable(28630, 134, 133, 15, 15, "Select");
        setSelectableValuesSwitchable(28630, 159, 1);

        addSpriteSelectable(28631, 124, 124, 52, 52, "Select");
        addSpriteSelectable(28632, 134, 133, 15, 15, "Select");
        setSelectableValuesSwitchable(28632, 160, 1);

        addSpriteSelectable(28633, 123, 123, 52, 52, "Select");
        addSpriteSelectable(28634, 134, 133, 15, 15, "Select");
        setSelectableValuesSwitchable(28634, 161, 1);

        addSpriteSelectable(28635, 130, 131, 96, 76, "Switch");
        setSelectableValues(28635, 162, 1);

        final int BASE_X = 10, BASE_Y = 10;
        tab.totalChildren(37);
        tab.child(0, 28561, 0 + BASE_X, 0 + BASE_Y);
        tab.child(1, 28562, 232 + BASE_X, 10 + BASE_Y);
        tab.child(2, 28563, 463 + BASE_X, 10 + BASE_Y);
        tab.child(3, 28564, 463 + BASE_X, 10 + BASE_Y);
        tab.child(4, 28566, 139 + BASE_X, 261 + BASE_Y);
        tab.child(5, 28567, 139 + BASE_X, 261 + BASE_Y);
        tab.child(6, 28569, 189 + BASE_X, 270 + BASE_Y);
        tab.child(7, 28570, 16 + BASE_X, 78 + BASE_Y);
        tab.child(8, 28571, 65 + BASE_X, 73 + BASE_Y);
        tab.child(9, 28572, 16 + BASE_X, 196 + BASE_Y);
        tab.child(10, 28573, 77 + BASE_X, 196 + BASE_Y);
        tab.child(11, 28574, 16 + BASE_X, 121 + BASE_Y);
        tab.child(12, 28591, 16 + BASE_X, 107 + BASE_Y);
        tab.child(13, 28592, 16 + BASE_X, 258 + BASE_Y);
        tab.child(14, 28593, 16 + BASE_X, 278 + BASE_Y);
        tab.child(15, 28594, 37 + BASE_X, 258 + BASE_Y);
        tab.child(16, 28595, 37 + BASE_X, 278 + BASE_Y);
        tab.child(17, 28608, 143 + BASE_X, 72 + BASE_Y);
        tab.child(18, 28609, 144 + BASE_X, 127 + BASE_Y);
        tab.child(19, 28610, 166 + BASE_X, 127 + BASE_Y);
        tab.child(20, 28611, 166 + BASE_X, 223 + BASE_Y);
        tab.child(21, 28612, 245 + BASE_X, 219 + BASE_Y);

        tab.child(22, 28621, 245 + BASE_X, 71 + BASE_Y);
        tab.child(23, 28623, 302 + BASE_X, 71 + BASE_Y);
        tab.child(24, 28625, 362 + BASE_X, 71 + BASE_Y);
        tab.child(25, 28627, 423 + BASE_X, 71 + BASE_Y);
        tab.child(26, 28629, 262 + BASE_X, 128 + BASE_Y);
        tab.child(27, 28631, 337 + BASE_X, 128 + BASE_Y);
        tab.child(28, 28633, 412 + BASE_X, 128 + BASE_Y);

        tab.child(29, 28622, 284 + BASE_X, 110 + BASE_Y);
        tab.child(30, 28624, 342 + BASE_X, 110 + BASE_Y);
        tab.child(31, 28626, 400 + BASE_X, 110 + BASE_Y);
        tab.child(32, 28628, 458 + BASE_X, 110 + BASE_Y);
        tab.child(33, 28630, 299 + BASE_X, 165 + BASE_Y);
        tab.child(34, 28632, 371 + BASE_X, 165 + BASE_Y);
        tab.child(35, 28634, 443 + BASE_X, 165 + BASE_Y);
        tab.child(36, 28635, 140 + BASE_X, 180 + BASE_Y);
    }

    public static void clanswarsGame(TextDrawingArea[] tda) {
        RSInterface tab = addTabInterface(28540);
        addRectangle(28541, 128, 0x595144, true, 152, 78);
        addRectangle(28542, 0, 0x372F22, false, 152, 78);
        addRectangle(28543, 0, 0x595144, false, 150, 76);
        addRectangle(28544, 0, 0x000000, true, 2, 31);
        addRectangle(28545, 0, 0x372F22, true, 148, 1);
        addRectangle(28546, 0, 0x595144, true, 148, 1);

        addText(28547, "My clan:", tda, 0, 0xB17A37, true, true);
        addText(28548, "Opposing clan:", tda, 0, 0xB17A37, true, true);
        addText(28549, "Players:", tda, 0, 0xB17A37, true, true);
        addText(28550, "1", tda, 0, 0xFF981F, true, true);
        addText(28551, "1", tda, 0, 0xFF981F, true, true);
        addText(28552, "Countdown to battle:", tda, 0, 0xB17A37, true, true);
        addText(28553, "0m 10s", tda, 0, 0xFF981F, true, true);

        final int BASE_X = 355, BASE_Y = 3;
        tab.totalChildren(14);
        tab.child(0, 28541, 0 + BASE_X, 0 + BASE_Y);
        tab.child(1, 28542, 0 + BASE_X, 0 + BASE_Y);
        tab.child(2, 28543, 1 + BASE_X, 1 + BASE_Y);
        tab.child(3, 28544, 70 + BASE_X, 4 + BASE_Y);
        tab.child(4, 28545, 3 + BASE_X, 40 + BASE_Y);
        tab.child(5, 28546, 3 + BASE_X, 41 + BASE_Y);
        tab.child(6, 28547, 35 + BASE_X, 5 + BASE_Y);
        tab.child(7, 28548, 111 + BASE_X, 5 + BASE_Y);
        tab.child(8, 28549, 25 + BASE_X, 20 + BASE_Y);
        tab.child(9, 28549, 97 + BASE_X, 20 + BASE_Y);
        tab.child(10, 28550, 64 + BASE_X, 21 + BASE_Y);
        tab.child(11, 28551, 146 + BASE_X, 21 + BASE_Y);
        tab.child(12, 28552, 80 + BASE_X, 45 + BASE_Y);
        tab.child(13, 28553, 76 + BASE_X, 61 + BASE_Y);
    }

    public static void soulwarsGame(TextDrawingArea[] tda) {
        RSInterface tab = addTabInterface(28510);
        addHDSprite(28511, 135, 135);

        addRectangle(28512, 128, 0xF0F0F0, true, 30, 34);
        addRectangle(28513, 128, 0xF0F0F0, true, 30, 34);
        addRectangle(28514, 128, 0xF0F0F0, true, 30, 34);
        addRectangle(28515, 191, 0x0D0D0D, true, 88, 148);
        addRectangle(28516, 128, 0x009900, true, 14, 148);

        addText(28517, "Blue", tda, 0, 0x3366FF, true, true);
        addText(28518, "Red", tda, 0, 0xE12323, true, true);
        addText(28519, "Avatar deaths", tda, 0, 0xff9900, true, true);
        addText(28520, "0", tda, 0, 0x3366FF, true, true);
        addText(28521, "0", tda, 0, 0xE12323, true, true);
        addText(28522, "Avatar level", tda, 0, 0xff9900, true, true);
        addText(28523, "100", tda, 0, 0x3366FF, true, true);
        addText(28524, "100", tda, 0, 0xE12323, true, true);
        addText(28525, "Avatar health", tda, 0, 0xff9900, true, true);
        addText(28526, "100%", tda, 0, 0x3366FF, true, true);
        addText(28527, "100%", tda, 0, 0xE12323, true, true);
        addText(28528, "Time left", tda, 0, 0xff9900, true, true);
        addText(28529, "23 min", tda, 0, 0xff9900, true, true);

        tab.totalChildren(19);
        tab.child(0, 28516, 220 + 270, 3);
        tab.child(1, 28512, 3 + 270, 3);
        tab.child(2, 28513, 44 + 270, 3);
        tab.child(3, 28514, 85 + 270, 3);
        tab.child(4, 28515, 125 + 270, 3);
        tab.child(5, 28511, 0 + 270, 0);
        tab.child(6, 28517, 153 + 270, 8);
        tab.child(7, 28518, 185 + 270, 8);
        tab.child(8, 28519, 168 + 270, 27);
        tab.child(9, 28520, 153 + 270, 41);
        tab.child(10, 28521, 185 + 270, 41);
        tab.child(11, 28522, 168 + 270, 57);
        tab.child(12, 28523, 153 + 270, 72);
        tab.child(13, 28524, 185 + 270, 72);
        tab.child(14, 28525, 168 + 270, 88);
        tab.child(15, 28526, 153 + 270, 102);
        tab.child(16, 28527, 185 + 270, 102);
        tab.child(17, 28528, 168 + 270, 118);
        tab.child(18, 28529, 168 + 270, 134);
    }

    public static void soulwarsLobby(TextDrawingArea[] tda) {
        RSInterface tab = addTabInterface(28500);
        addText(28501, "Players available:", tda, 0, 0xff9900, false, true);
        addText(28502, "Blue:", tda, 0, 0x3366FF, false, true);
        addText(28503, "Red:", tda, 0, 0xE12323, false, true);
        addText(28504, "New game:", tda, 0, 0xff9900, false, true);
        addRectangle(28505, 0, 0x333333, true, 95, 1);
        tab.totalChildren(6);
        tab.child(0, 28501, 415, 273 - 20);
        tab.child(1, 28502, 435, 295 - 20);
        tab.child(2, 28503, 437, 311 - 20);
        tab.child(3, 28504, 413, 330 - 20);
        tab.child(4, 28505, 404, 288 - 20);
        tab.child(5, 28505, 404, 325 - 20);
    }

    public static void questInterface(TextDrawingArea[] TDA) {
        RSInterface Interface = addInterface(8134);
        Interface.centerText = true;
        addSprite(8135, 0, "QuestTab/QUESTBG");
        addSprite(8136, 1, "QuestTab/QUESTBG");
        addText(8144, "Quest Name", 0x000000, true, false, 52, TDA, 3);// 249 18
        addHover(8137, 3, 0, 8138, 0, "QuestTab/CLOSE", 26, 23, "Close");
        addHovered(8138, 1, "QuestTab/CLOSE", 26, 23, 8139);
        setChildren(6, Interface);
        setBounds(8136, 18, 4, 0, Interface);
        setBounds(8135, 18, 62, 1, Interface);
        setBounds(8144, 260, 15, 2, Interface);
        setBounds(8140, 50, 86, 3, Interface);
        setBounds(8137, 452, 63, 4, Interface);
        setBounds(8138, 452, 63, 5, Interface);
        Interface = addInterface(8140);
        Interface.height = 217;
        Interface.width = 404;
        Interface.scrollMax = 1300;
        setChildren(51, Interface);
        int Ypos = 18;
        int frameID = 0;
        for (int iD = 8145; iD <= 8195; iD++) {
            addText(iD, "j", 0x000080, true, false, 52, TDA, 1);
            setBounds(iD, 202, Ypos, frameID, Interface);
            frameID++;
            Ypos += 19;
            Ypos++;
        }
    }

    public static void unpack(CacheArchive streamLoader, TextDrawingArea textDrawingAreas[], CacheArchive streamLoader_1) {
        fonts = textDrawingAreas;
        spriteCache = new MemCache(50000);
        Stream stream = new Stream(streamLoader.getDataForName("data"));
        int parentId = -1;
        int totalInterfaces = stream.readUnsignedWord();
        interfaceCache = new RSInterface[85000];
        while (stream.currentOffset < stream.buffer.length) {
            int opcode = stream.readUnsignedWord();
            if (opcode == 65535) {
                parentId = stream.readUnsignedWord();
                opcode = stream.readUnsignedWord();
            }
            RSInterface rsInterface = interfaceCache[opcode] = new RSInterface();
            rsInterface.id = opcode;
            rsInterface.parentID = parentId;
            rsInterface.type = stream.readUnsignedByte();
            rsInterface.atActionType = stream.readUnsignedByte();
            rsInterface.contentType = stream.readUnsignedWord();
            rsInterface.width = stream.readUnsignedWord();
            rsInterface.height = stream.readUnsignedWord();
            rsInterface.opacity = (byte) stream.readUnsignedByte();
            rsInterface.hoverType = stream.readUnsignedByte();
            if (rsInterface.hoverType != 0)
                rsInterface.hoverType = (rsInterface.hoverType - 1 << 8) + stream.readUnsignedByte();
            else rsInterface.hoverType = -1;
            int valueCompareAmt = stream.readUnsignedByte();
            if (valueCompareAmt > 0) {
                rsInterface.valueCompareType = new int[valueCompareAmt];
                rsInterface.requiredValues = new int[valueCompareAmt];
                for (int idx = 0; idx < valueCompareAmt; idx++) {
                    rsInterface.valueCompareType[idx] = stream.readUnsignedByte();
                    rsInterface.requiredValues[idx] = stream.readUnsignedWord();
                }

            }
            int indexAmt = stream.readUnsignedByte();
            if (indexAmt > 0) {
                rsInterface.valueIndexArray = new int[indexAmt][];
                for (int idx = 0; idx < indexAmt; idx++) {
                    int value = stream.readUnsignedWord();
                    rsInterface.valueIndexArray[idx] = new int[value];
                    for (int idx1 = 0; idx1 < value; idx1++)
                        rsInterface.valueIndexArray[idx][idx1] = stream.readUnsignedWord();

                }

            }
            if (rsInterface.type == 0) {
                rsInterface.drawsTransparent = false;
                rsInterface.scrollMax = stream.readUnsignedWord();
                rsInterface.hidden = stream.readUnsignedByte() == 1;
                int i2 = stream.readUnsignedWord();
                rsInterface.children = new int[i2];
                rsInterface.childX = new int[i2];
                rsInterface.childY = new int[i2];
                for (int j3 = 0; j3 < i2; j3++) {
                    rsInterface.children[j3] = stream.readUnsignedWord();
                    rsInterface.childX[j3] = stream.readSignedWord();
                    rsInterface.childY[j3] = stream.readSignedWord();
                }
            }
            if (rsInterface.type == 1) {
                stream.readUnsignedWord();
                stream.readUnsignedByte();
            }
            if (rsInterface.type == 2) {
                rsInterface.inv = new int[rsInterface.width * rsInterface.height];
                rsInterface.invStackSizes = new int[rsInterface.width * rsInterface.height];
                rsInterface.deleteOnDrag2 = stream.readUnsignedByte() == 1;
                rsInterface.isInventoryInterface = stream.readUnsignedByte() == 1;
                rsInterface.usableItemInterface = stream.readUnsignedByte() == 1;
                rsInterface.dragDeletes = stream.readUnsignedByte() == 1;
                rsInterface.invSpritePadX = stream.readUnsignedByte();
                rsInterface.invSpritePadY = stream.readUnsignedByte();
                rsInterface.spritesX = new int[20];
                rsInterface.spritesY = new int[20];
                rsInterface.sprites = new Sprite[20];
                for (int j2 = 0; j2 < 20; j2++) {
                    int k3 = stream.readUnsignedByte();
                    if (k3 == 1) {
                        rsInterface.spritesX[j2] = stream.readSignedWord();
                        rsInterface.spritesY[j2] = stream.readSignedWord();
                        String s1 = stream.readString();
                        if (streamLoader_1 != null && s1.length() > 0) {
                            int i5 = s1.lastIndexOf(",");
                            rsInterface.sprites[j2] = getSprite(Integer.parseInt(s1.substring(i5 + 1)), streamLoader_1, s1.substring(0, i5));
                        }
                    }
                }
                rsInterface.actions = new String[5];
                for (int l3 = 0; l3 < 5; l3++) {
                    rsInterface.actions[l3] = stream.readString();
                    if (rsInterface.actions[l3].length() == 0) rsInterface.actions[l3] = null;
                    if (rsInterface.parentID == 3824) rsInterface.actions[4] = "Buy X";
                    if (rsInterface.parentID == 3822) {
                        rsInterface.actions[4] = "Sell X";
                    }
                    if (rsInterface.parentID == 1644) rsInterface.actions[2] = "Operate";
                    if (rsInterface.parentID == 5292)
                        rsInterface.actions = new String[]{"Withdraw-1", "Withdraw-5", "Withdraw-10", "Withdraw-All", "Withdraw-All but one", "Withdraw-X"};
                }
            }
            if (rsInterface.type == 3) rsInterface.filled = stream.readUnsignedByte() == 1;
            if (rsInterface.type == 4 || rsInterface.type == 1) {
                rsInterface.centerText = stream.readUnsignedByte() == 1;
                int k2 = stream.readUnsignedByte();
                if (textDrawingAreas != null) rsInterface.textDrawingAreas = textDrawingAreas[k2];
                rsInterface.shadowed = stream.readUnsignedByte() == 1;
            }
            if (rsInterface.type == 4) {
                rsInterface.message = stream.readString().replaceAll("RuneScape", "PaeScape");
                if (rsInterface.message.toLowerCase().equals("the bank of PaeScape")) {
                    rsInterface.message = "        The Bank of PaeScape";
                }
                rsInterface.enabledMessage = stream.readString();
            }
            if (rsInterface.type == 1 || rsInterface.type == 3 || rsInterface.type == 4)
                rsInterface.disabledColor = stream.readDWord();
            if (rsInterface.type == 3 || rsInterface.type == 4) {
                rsInterface.enabledColor = stream.readDWord();
                rsInterface.disabledMouseOverColor = stream.readDWord();
                rsInterface.enabledMouseOverColor = stream.readDWord();
            }
            if (rsInterface.type == 5) {
                rsInterface.drawsTransparent = false;
                String s = stream.readString();
                if (streamLoader_1 != null && s.length() > 0) {
                    int i4 = s.lastIndexOf(",");
                    rsInterface.disabledSprite = getSprite(Integer.parseInt(s.substring(i4 + 1)), streamLoader_1, s.substring(0, i4));
                }
                s = stream.readString();
                if (streamLoader_1 != null && s.length() > 0) {
                    int j4 = s.lastIndexOf(",");
                    rsInterface.enabledSprite = getSprite(Integer.parseInt(s.substring(j4 + 1)), streamLoader_1, s.substring(0, j4));
                }
            }
            if (rsInterface.type == 6) {
                int l = stream.readUnsignedByte();
                if (l != 0) {
                    rsInterface.mediaType = 1;
                    rsInterface.mediaID = (l - 1 << 8) + stream.readUnsignedByte();
                }
                l = stream.readUnsignedByte();
                if (l != 0) {
                    rsInterface.enabledMediaType = 1;
                    rsInterface.enabledMediaID = (l - 1 << 8) + stream.readUnsignedByte();
                }
                l = stream.readUnsignedByte();
                if (l != 0) {
                    rsInterface.disabledAnimationId = (l - 1 << 8) + stream.readUnsignedByte();
                } else rsInterface.disabledAnimationId = -1;
                l = stream.readUnsignedByte();
                if (l != 0) rsInterface.enabledAnimationId = (l - 1 << 8) + stream.readUnsignedByte();
                else rsInterface.enabledAnimationId = -1;
                rsInterface.modelZoom = stream.readUnsignedWord();
                rsInterface.modelRotation1 = stream.readUnsignedWord();
                rsInterface.modelRotation2 = stream.readUnsignedWord();
            }
            if (rsInterface.type == 7) {
                rsInterface.inv = new int[rsInterface.width * rsInterface.height];
                rsInterface.invStackSizes = new int[rsInterface.width * rsInterface.height];
                rsInterface.centerText = stream.readUnsignedByte() == 1;
                int l2 = stream.readUnsignedByte();
                if (textDrawingAreas != null) rsInterface.textDrawingAreas = textDrawingAreas[l2];
                rsInterface.shadowed = stream.readUnsignedByte() == 1;
                rsInterface.disabledColor = stream.readDWord();
                rsInterface.invSpritePadX = stream.readSignedWord();
                rsInterface.invSpritePadY = stream.readSignedWord();
                rsInterface.isInventoryInterface = stream.readUnsignedByte() == 1;
                rsInterface.actions = new String[5];
                for (int k4 = 0; k4 < 5; k4++) {
                    rsInterface.actions[k4] = stream.readString();
                    if (rsInterface.actions[k4].length() == 0) rsInterface.actions[k4] = null;
                }

            }
            if (rsInterface.atActionType == 2 || rsInterface.type == 2) {
                rsInterface.selectedActionName = stream.readString();
                rsInterface.spellName = stream.readString();
                rsInterface.spellUsableOn = stream.readUnsignedWord();
            }

            if (rsInterface.type == 8) rsInterface.message = stream.readString();

            if (rsInterface.atActionType == 1 || rsInterface.atActionType == 4 || rsInterface.atActionType == 5 || rsInterface.atActionType == 6) {
                rsInterface.tooltip = stream.readString();
                if (rsInterface.tooltip.length() == 0) {
                    if (rsInterface.atActionType == 1) rsInterface.tooltip = "Ok";
                    if (rsInterface.atActionType == 4) rsInterface.tooltip = "Select";
                    if (rsInterface.atActionType == 5) rsInterface.tooltip = "Select";
                    if (rsInterface.atActionType == 6) rsInterface.tooltip = "Continue";
                }
            }
        }
        cacheArchive = streamLoader;
        playersOnline(textDrawingAreas);
        roomChooser();
        fornitureChooser(textDrawingAreas);
        constructionWaiting();
        editClan(textDrawingAreas);
        questTabInterfaceNew();
        newStarter(textDrawingAreas);
        Starter(textDrawingAreas);
        staffTabInterface(textDrawingAreas);
        Buy(textDrawingAreas);
        Sell(textDrawingAreas);
        BuyandSell(textDrawingAreas);
        collectSell(textDrawingAreas);
        collectBuy(textDrawingAreas);
        dungeonInfo();
        formParty(textDrawingAreas);
        dungParty(textDrawingAreas);
        ExpRewardInterface();
        slayerBuyInterface();
        barrowsInterface();
        //chaosRaidInterface();
        TeleTAB1();
        TeleTAB2();
        TeleTAB3_1();
        TeleTAB3_2();
        TeleTAB3_3();
        TeleTAB4();
        TeleTAB5();
        pestControlInterfaces();
        skillTabInterface2();
        statistics(textDrawingAreas);
        optionsInterface();
        questTabInterface();
        questTabInterface2();
        friendsTabInterface(textDrawingAreas);
        ignoreTabInterface(textDrawingAreas);
        EquipmentTab(textDrawingAreas);
        equipmentScreenInterface();
        itemsKeptOnDeathInterface();
        clanChatTabInterface();
        redoSpellBooks();
        shopInterface(textDrawingAreas);
        bankInterface();
        prayerTabInterface();
        quickPrayersInterface();
        curseTabInterface();
        quickCursesInterface();
        summoningBoBInterface();
        summoningTabInterface();
        priceCheckerInterface();
        achievementsInterface();
        loyaltyShop();
        wildernessInterface();
        Sidebar0(textDrawingAreas);
        optionTab(textDrawingAreas);
        emoteTab();
        vidOptions(textDrawingAreas);
        prayerTab();
        pouchCreation();
        opacityInterface();
        levelUpInterfaces();
        vengTimer(textDrawingAreas);
        barrageTimer(textDrawingAreas);
        specButton(textDrawingAreas);
        entityInterface(textDrawingAreas);
        clueScrolls(textDrawingAreas);
        npcTracker(textDrawingAreas);
        bossTracker(textDrawingAreas);
        dropLog(textDrawingAreas);
        rareLog(textDrawingAreas);

        statistics1(textDrawingAreas);
        playerProfile(textDrawingAreas);

        loyaltyBox(textDrawingAreas);
        playerPanel(textDrawingAreas);
        warriorGuild(textDrawingAreas);
        dropPreview(textDrawingAreas);
        trainingTeleports(textDrawingAreas);
        minigameTeleports(textDrawingAreas);
        bossesTeleports(textDrawingAreas);
        playerKillingTeleports(textDrawingAreas);
        skillingTeleports(textDrawingAreas);
        donatorTeleports(textDrawingAreas);

        teleportWidget(textDrawingAreas);


        godWars();
        playerOwnedShopInterface(textDrawingAreas);
        Pets(textDrawingAreas);
        playerOwnedShopInterface2(textDrawingAreas);
        playerOwnedShopInterface3(textDrawingAreas);
        customization(textDrawingAreas);
        DropList.unpack(textDrawingAreas);
        Achievements.unpack(textDrawingAreas);
        Teleports.unpack(textDrawingAreas);
        raids(textDrawingAreas);
        collectionLog(textDrawingAreas);
        marketPlace(textDrawingAreas);
        DeathsCoffers(textDrawingAreas);
        mailboxInterface(textDrawingAreas);
        uimBankInterface(textDrawingAreas);
        bingoInterface(textDrawingAreas);
		/*spells[0] = interfaceCache[1572]; //Bind
		spells[1] = interfaceCache[1582]; //Snare
		spells[2] = interfaceCache[1592]; //Entangle
		spells[3] = interfaceCache[1162]; //Alch
		spells[4] = interfaceCache[1178]; //Alch2
		spells[5] = interfaceCache[1173]; //Superheat

	CustomInterfaces.skillTabInterface();
		CustomInterfaces.questTabInterface();
		CustomInterfaces.pointsHandlerInterface();
		CustomInterfaces.friendsTabInterface();
		CustomInterfaces.ignoreTabInterface();
		CustomInterfaces.levelUpInterfaces();*/
        //CustomInterfaces.EquipmentTab();
        //	CustomInterfaces.equipmentScreenInterface();
        //CustomInterfaces.itemsKeptOnDeathInterface();
        ///CustomInterfaces.clanChatTabInterface();
        //	CustomInterfaces.redoSpellBooks();
		/*	CustomInterfaces.prayerTab();
		CustomInterfaces.curseTabInterface();
		CustomInterfaces.quickPrayersInterface();
		CustomInterfaces.quickCursesInterface();
		CustomInterfaces.optionTab();
		CustomInterfaces.bankInterface();
		CustomInterfaces.shopInterface();*/
		/*	CustomInterfaces.pouchCreation();
		CustomInterfaces.summoningTabInterface();
		CustomInterfaces.summoningBoBInterface();*/
        //CustomInterfaces.priceCheckerInterface();
        //	CustomInterfaces.loyaltyShop();
        //	CustomInterfaces.achievementsInterface();

        spriteCache = null;

        if (Configuration.findFreeInterfaces) {
            int testValInd = 411;
            for (int i = 0; i < interfaceCache.length; i++) {
                RSInterface a = interfaceCache[i];
                if (a != null && a.valueIndexArray != null) {
                    if (a.valueIndexArray[0][1] == testValInd)
                        System.out.println("testValInd " + testValInd + " in use");
                }
            }
            int freeChildCount = 0;
            int longestRunCount = 0;
            int longestRunStart = -1;
            int curRunStart = 0;
            int curRunCount = 0;
            int sandwichId = -1;
            int lastUsedInterface = -1;
            for (int i = 0; i < 35000; i++) {
                if (interfaceCache[i] == null) {
                    //System.out.println(i);
                    freeChildCount++;

                    if (curRunStart == -1) {
                        curRunStart = i;
                    }
                    curRunCount++;
                } else {
                    lastUsedInterface = i;
                    if (curRunCount == 1 && sandwichId < 0) {
                        sandwichId = i - 1;
                    }
                    if (curRunCount > longestRunCount) {
                        longestRunCount = curRunCount;
                        longestRunStart = curRunStart;
                    }
                    curRunStart = -1;
                    curRunCount = 0;
                }
            }
            System.out.println(freeChildCount + " unused interface childs");
            System.out.println(longestRunStart + " onwards has " + longestRunCount + " unused interface childs in a row");
            System.out.println(sandwichId + " is a single unused interface child surrounded by used ones.");
            //System.out.println(lastUsedInterface + " is the last used interface, interfaceCache.length should be set to " + (lastUsedInterface+1));

            int[] checkIds = new int[]{40500, 40501, 40502, 40503, 40504, 40505, 40506, 40507, 40508, 40600, 40601, 40602, 40603, 40604, 40605, 40606, 40607, 40608};
            for (int i = 0; i < checkIds.length; i++) {
                int checkId = checkIds[i];
                if (interfaceCache[checkId] == null) {
                    System.out.println(checkId + " is free");
                } else {
                    System.out.println(checkId + " is used");
                }
            }
        }
    }

    public static void addToggleButton2(int id, int[] same, int sprite, int onSprite, String tooltip, boolean on) {
        RSInterface tab = addInterface(id);
        tab.type = 24;
        tab.atActionType = 1;
        tab.contentType = 0;
        tab.sprite1 = Client.cacheSprite[sprite];
        tab.sprite2 = Client.cacheSprite[onSprite];
        tab.width = tab.sprite1.myWidth;
        tab.height = tab.sprite2.myHeight;
        tab.tooltip = tooltip;
        tab.togglers = same;
        tab.toggled = on;
        tab.isToggler = true;
    }

    public static void dropLog(TextDrawingArea[] tda) {
        RSInterface tab = addInterface(65250);
        addSpriteLoader(69251, 994);
        addText(65252, "Drop Log", tda, 2, 0xff981f, true, true);
        addButtonWSpriteLoader(65253, 990, "Select", 57, 17);
        addButtonWSpriteLoader(65254, 989, "Select", 57, 17);
        addButtonWSpriteLoader(77265, 892, "Close Window", 16, 16);

        addText(65256, "Common", tda, 0, 0xff981f, true, true);
        addText(65257, "Rare", tda, 0, 0xff981f, true, true);


        int x = 70, y = 40;
        tab.totalChildren(8);
        tab.child(0, 69251, x, y);
        tab.child(1, 65252, 177 + x, 4 + y);
        tab.child(2, 65253, 23 + x, 32 + y);
        tab.child(3, 65254, 80 + x, 32 + y);
        tab.child(4, 65256, 51 + x, 36 + y);
        tab.child(5, 65257, 108 + x, 36 + y);
        tab.child(6, 65260, 23 + x, 50 + y);
        tab.child(7, 77265, 320 + x, 4 + y);


        addSpriteLoader(65259, 991);


        RSInterface normal = addInterface(65260);
        normal.width = 279;
        normal.height = 165;
        normal.scrollMax = 950;

        normal.totalChildren(100);
        for (int i = 0; i < 50; i++) {
            addText(65261 + i, " ", tda, 0, 0xffffff, false, true);
            normal.child(i, 65261 + i, 3, 6 + (i * 19));
            normal.child(i + 50, 65259, 0, 19 + (i * 19));

        }
    }

    public static void rareLog(TextDrawingArea[] tda) {
        RSInterface tab = addInterface(25250);
        addSpriteLoader(77651, 994);
        addText(25252, "Drop Log", tda, 2, 0xff981f, true, true);
        addButtonWSpriteLoader(25253, 989, "Select", 57, 17);
        addButtonWSpriteLoader(25254, 990, "Select", 57, 17);
        addButtonWSpriteLoader(78265, 892, "Close Window", 16, 16);

        addText(25256, "Common", tda, 0, 0xff981f, true, true);
        addText(25257, "Rare", tda, 0, 0xff981f, true, true);


        int x = 70, y = 40;
        tab.totalChildren(8);
        tab.child(0, 77651, x, y);
        tab.child(1, 25252, 177 + x, 4 + y);
        tab.child(2, 25253, 23 + x, 32 + y);
        tab.child(3, 25254, 80 + x, 32 + y);
        tab.child(4, 25256, 51 + x, 36 + y);
        tab.child(5, 25257, 108 + x, 36 + y);
        tab.child(6, 25260, 23 + x, 50 + y);
        tab.child(7, 78265, 320 + x, 4 + y);


        addSpriteLoader(25259, 991);

        RSInterface normal = addInterface(25260);
        normal.width = 279;
        normal.height = 165;
        normal.scrollMax = 950;

        normal.totalChildren(100);
        for (int i = 0; i < 50; i++) {
            addText(25261 + i, " ", tda, 0, 0xffffff, false, true);
            normal.child(i, 25261 + i, 3, 6 + (i * 19));
            normal.child(i + 50, 25259, 0, 19 + (i * 19));


        }


    }

    public static void bossTracker(TextDrawingArea[] tda) {
        RSInterface tab = addInterface(55250);
        addSpriteLoader(59251, 994);
        addText(55252, "Monster Kill Tracker", tda, 2, 0xff981f, true, true);
        addButtonWSpriteLoader(55253, 989, "Select", 57, 17);
        addButtonWSpriteLoader(55254, 990, "Select", 57, 17);
        addButtonWSpriteLoader(35255, 892, "Close Window", 16, 16);

        addText(55256, "Normal", tda, 0, 0xff981f, true, true);
        addText(55257, "Bosses", tda, 0, 0xff981f, true, true);


        int x = 70, y = 40;
        tab.totalChildren(8);
        tab.child(0, 59251, x, y);
        tab.child(1, 55252, 177 + x, 4 + y);
        tab.child(2, 55253, 23 + x, 32 + y);
        tab.child(3, 55254, 80 + x, 32 + y);
        tab.child(4, 55256, 51 + x, 36 + y);
        tab.child(5, 55257, 108 + x, 36 + y);
        tab.child(6, 55260, 23 + x, 50 + y);
        tab.child(7, 35255, 320 + x, 4 + y);
        addSpriteLoader(55259, 991);

        RSInterface normal = addInterface(55260);
        normal.width = 279;
        normal.height = 165;
        normal.scrollMax = 950;
        normal.totalChildren(100);
        for (int i = 0; i < 50; i++) {
            addText(55261 + i, " ", tda, 0, 0xffffff, false, true);
            normal.child(i, 55261 + i, 3, 6 + (i * 19));
            normal.child(i + 50, 55259, 0, 19 + (i * 19));
        }

    }

    public static void npcTracker(TextDrawingArea[] tda) {
        RSInterface tab = addInterface(35250);
        addSpriteLoader(39251, 994);
        addText(35252, "Monster Kill Tracker", tda, 2, 0xff981f, true, true);
        addButtonWSpriteLoader(35253, 990, "Select", 57, 17);
        addButtonWSpriteLoader(35254, 989, "Select", 57, 17);
        addButtonWSpriteLoader(35255, 892, "Close Window", 16, 16);

        addText(35256, "Normal", tda, 0, 0xff981f, true, true);
        addText(35257, "Bosses", tda, 0, 0xff981f, true, true);


        int x = 70, y = 40;
        tab.totalChildren(8);
        tab.child(0, 39251, x, y);
        tab.child(1, 35252, 177 + x, 4 + y);
        tab.child(2, 35253, 23 + x, 32 + y);
        tab.child(3, 35254, 80 + x, 32 + y);
        tab.child(4, 35256, 51 + x, 36 + y);
        tab.child(5, 35257, 108 + x, 36 + y);
        tab.child(6, 35260, 23 + x, 50 + y);
        tab.child(7, 35255, 320 + x, 4 + y);

        addSpriteLoader(35259, 991);

        RSInterface normal = addInterface(35260);
        normal.width = 279;
        normal.height = 165;
        normal.scrollMax = 950;
        normal.totalChildren(100);
        for (int i = 0; i < 50; i++) {
            addText(35261 + i, " ", tda, 0, 0xffffff, false, true);
            normal.child(i, 35261 + i, 3, 6 + (i * 19));
            normal.child(i + 50, 35259, 0, 19 + (i * 19));
        }

    }

    public static void loyaltyShop() { // Made quickly by gabbe
        RSInterface rsi = addTabInterface(43000);
        rsi.totalChildren(74);
        addSpriteLoader(43001, 868);
        addText(43002, "Loyalty Titles", fonts, 2, 16750623, false, true);
        addCloseButton(43003, 43121, 43122);
        rsi.child(0, 43001, 10, 20);
        rsi.child(1, 43002, 210, 22);
        rsi.child(2, 43003, 466, 19);

        /** BUY BUTTONS **/
        int id = 43004, child = 3;
        for (int i = 0; i < 12; i++) {
            int y = i == 1 ? 62 : i == 2 ? 83 : i == 3 ? 105 : i == 4 ? 127 : i == 5 ? 149 : i == 6 ? 171 : i == 7 ? 193 : i == 8 ? 215 : i == 9 ? 237 : i == 10 ? 259 : i == 11 ? 281 : -1;

            if (id != 43004) {
                addHoverButtonWSpriteLoader(id, 870, 32, 17, "Buy", -1, id + 1, 1);
                addHoveredImageWSpriteLoader(id + 1, 869, 32, 17, id + 2);
            } else {
                removeSomething(id);
                removeSomething(id + 1);
            }

            addText(id + 3, "", fonts, 0, 0xB9B855, false, true);

            rsi.child(child, id, 213, y);
            rsi.child(child + 1, id + 1, 213, y);
            rsi.child(child + 2, id + 3, 158, y + 5);
            child += 3;
            id += 4;
        }
        for (int i = 1; i < 12; i++) {
            int y = i == 1 ? 62 : i == 2 ? 83 : i == 3 ? 105 : i == 4 ? 127 : i == 5 ? 149 : i == 6 ? 171 : i == 7 ? 193 : i == 8 ? 215 : i == 9 ? 237 : i == 10 ? 259 : i == 11 ? 281 : -1;


            addHoverButtonWSpriteLoader(id, 870, 32, 17, "Buy", -1, id + 1, 1);
            addHoveredImageWSpriteLoader(id + 1, 869, 32, 17, id + 2);

            addText(id + 3, "", fonts, 0, 0xB9B855, false, true);

            rsi.child(child, id, 428, y);
            rsi.child(child + 1, id + 1, 428, y);
            rsi.child(child + 2, id + 3, 373, y + 5);

            child += 3;
            id += 4;
        }
        addText(43120, "Your Loyalty Points: 0", fonts, 0, 0xB9B855, false, true);
        rsi.child(72, 43120, 195, 43);
        rsi.child(73, 43121, 466, 19);
    }

    public static void playerPanel(TextDrawingArea wid[]) {
        RSInterface tab = addInterface(42500);
        addSpriteLoader(42501, 953);
        addChar(42502);

        tab.children(2);
        tab.child(0, 42501, 5, 10);
        tab.child(1, 42502, 125, 150);


    }

    public static void achievementsInterface() {
        RSInterface tab = addTabInterface(45000);
        RSInterface scroll = addTabInterface(45999);
        addText(45001, "Information", fonts, 2, 16750623, false, true);
        addSpriteLoader(45002, 722);
        addSpriteLoader(45003, 723);
        addSpriteLoader(45004, 722);
        tab.totalChildren(5);
        tab.child(0, 45001, 5, 3);
        tab.child(1, 45002, 0, 22);
        tab.child(2, 45003, 0, 25);
        tab.child(3, 45004, 0, 249);
        tab.child(4, 45999, 0, 25);

        scroll.totalChildren(200);
        scroll.width = 174;
        scroll.height = 224;
        scroll.scrollMax = 2000;//1790


        /** TEXT **/
        int k = 0;
        int y = 25;
        for (int i = 45005; i < 45204; i++) {
            scroll.child(k, i, 6, y);
            y += 16;
            k++;
            if (i == 45032 || i == 45060 || i == 45084 || i == 45108 || i == 45131 || i >= 45154) {
                addText(i, "", fonts, 1, 0xFF8900, false, true);
            } else {
                addClickableText(i, "", "Select", fonts, 1, 0xFF8900, 130, 13);
            }
        }

        addText(45777, "@whi@ Information", fonts, 1, 0xFF9900, false, true);
        scroll.child(k, 45777, 7, 6);
    }

    private static void soulwarsRewards(TextDrawingArea[] textDrawingAreas2) {
        RSInterface Interface = addTabInterface(31000);
        setChildren(24, Interface);
        addSprite(31001, 0, "Interfaces/Soul Wars/EXPERIENCE/MAIN_EXPERIENCE");
        addHoverButton(31002, "Interfaces/Soul Wars/EXPERIENCE/CLOSE", 0, 16, 16, "Close", -1, 31003, 3);
        addHoveredButton(31003, "Interfaces/Soul Wars/EXPERIENCE/CLOSE", 1, 16, 16, 31004);
        addHoverButton(31005, "Interfaces/Soul Wars/EXPERIENCE/ATTACK", 0, 64, 64, "Buy @or1@Attack XP", -1, 31006, 1);
        addHoveredButton(31006, "Interfaces/Soul Wars/EXPERIENCE/ATTACK", 1, 64, 64, 31007);
        addHoverButton(31008, "Interfaces/Soul Wars/EXPERIENCE/STRENGTH", 0, 64, 64, "Buy @or1@Strength XP", -1, 31009, 1);
        addHoveredButton(31009, "Interfaces/Soul Wars/EXPERIENCE/STRENGTH", 1, 64, 64, 31010);
        addHoverButton(31011, "Interfaces/Soul Wars/EXPERIENCE/DEFENCE", 0, 64, 64, "Buy @or1@Defence XP", -1, 31012, 1);
        addHoveredButton(31012, "Interfaces/Soul Wars/EXPERIENCE/DEFENCE", 1, 64, 64, 40013);
        addHoverButton(31014, "Interfaces/Soul Wars/EXPERIENCE/CONSTITUTION", 0, 64, 64, "Buy 50K @or1@Constitution XP", -1, 31015, 1);
        addHoveredButton(31015, "Interfaces/Soul Wars/EXPERIENCE/CONSTITUTION", 1, 64, 64, 31016);
        addHoverButton(31017, "Interfaces/Soul Wars/EXPERIENCE/RANGE", 0, 64, 64, "Buy @or1@Ranged XP", -1, 31018, 1);
        addHoveredButton(31018, "Interfaces/Soul Wars/EXPERIENCE/RANGE", 1, 64, 64, 31019);
        addHoverButton(31020, "Interfaces/Soul Wars/EXPERIENCE/MAGIC", 0, 64, 64, "Buy @or1@Magic XP", -1, 31021, 1);
        addHoveredButton(31021, "Interfaces/Soul Wars/EXPERIENCE/MAGIC", 1, 64, 64, 31022);
        addHoverButton(31023, "Interfaces/Soul Wars/EXPERIENCE/PRAYER", 0, 64, 64, "Buy @or1@Prayer XP", -1, 31024, 1);
        addHoveredButton(31024, "Interfaces/Soul Wars/EXPERIENCE/PRAYER", 1, 64, 64, 40025);
        addHoverButton(31026, "Interfaces/Soul Wars/EXPERIENCE/SLAYER", 0, 64, 64, "Buy @or1@Slayer XP", -1, 31027, 1);
        addHoveredButton(31027, "Interfaces/Soul Wars/EXPERIENCE/SLAYER", 1, 64, 64, 31028);
        addHoverButton(31029, "", 0, 64, 25, "Items", -1, 31030, 1);
        addHoveredButton(31030, "", 1, 64, 25, 31031);
        addHoverButton(31032, "", 0, 64, 25, "Other", -1, 31033, 1);
        addHoveredButton(31033, "", 1, 64, 25, 31034);
        addText(31092, "Zeal: 10", textDrawingAreas2, 0, 0xff9933, true, true);
        setBounds(31001, 1, 1, 0, Interface);
        setBounds(31002, 413, 63, 1, Interface);
        setBounds(31003, 413, 63, 2, Interface);
        setBounds(31005, 101, 114, 3, Interface);
        setBounds(31006, 101, 114, 4, Interface);
        setBounds(31008, 182, 114, 5, Interface);
        setBounds(31009, 182, 114, 6, Interface);
        setBounds(31011, 262, 114, 7, Interface);
        setBounds(31012, 262, 114, 8, Interface);
        setBounds(31014, 344, 114, 9, Interface);
        setBounds(31015, 344, 114, 10, Interface);
        setBounds(31017, 101, 193, 11, Interface);
        setBounds(31018, 101, 193, 12, Interface);
        setBounds(31020, 182, 192, 13, Interface);
        setBounds(31021, 182, 192, 14, Interface);
        setBounds(31023, 262, 192, 15, Interface);
        setBounds(31024, 262, 192, 16, Interface);
        setBounds(31026, 344, 192, 17, Interface);
        setBounds(31027, 344, 192, 18, Interface);
        setBounds(31029, 160, 71, 19, Interface);
        setBounds(31030, 160, 71, 20, Interface);
        setBounds(31032, 235, 71, 21, Interface);
        setBounds(31033, 235, 71, 22, Interface);
        setBounds(31092, 345, 66, 23, Interface);
        soulPointsCharms(textDrawingAreas2);
    }

    /**
     * Interface Cody c:
     */
    public static void soulPointsCharms(TextDrawingArea[] Soul) {
        RSInterface Interface = addTabInterface(31035);
        setChildren(16, Interface);
        addSprite(31036, 0, "Interfaces/Soul Wars/CHARMS/MAIN_CHARMS");
        addHoverButton(31037, "Interfaces/Soul Wars/CHARMS/CLOSE", 0, 16, 16, "Close", -1, 31038, 3);
        addHoveredButton(31038, "Interfaces/Soul Wars/CHARMS/CLOSE", 1, 16, 16, 31039);
        addHoverButton(31040, "", 0, 64, 64, "Buy @or1@Gold charms", -1, 31041, 1);
        addHoveredButton(31041, "Interfaces/Soul Wars/CHARMS/GOLD", 1, 64, 64, 31042);
        addHoverButton(31043, "", 0, 64, 64, "Buy @or1@Green charms", -1, 31044, 1);
        addHoveredButton(31044, "Interfaces/Soul Wars/CHARMS/GREEN", 1, 64, 64, 31045);
        addHoverButton(31046, "", 0, 64, 64, "Buy @or1@Chrimson charms", -1, 31047, 1);
        addHoveredButton(31047, "Interfaces/Soul Wars/CHARMS/CRIMSON", 1, 64, 64, 31048);
        addHoverButton(31049, "", 0, 64, 64, "Buy @or1@Blue charms", -1, 31050, 1);
        addHoveredButton(31050, "Interfaces/Soul Wars/CHARMS/BLUE", 1, 64, 64, 31051);
        addHoverButton(31052, "", 0, 64, 25, "Experience", -1, 31053, 1);
        addHoveredButton(31053, "", 1, 64, 25, 31054);
        addHoverButton(31055, "", 0, 64, 25, "Other", -1, 31056, 1);
        addHoveredButton(31056, "", 1, 64, 25, 31057);
        addText(31091, "Zeal: 10", Soul, 0, 0xff9933, true, true);
        setBounds(31036, 1, 1, 0, Interface);
        setBounds(31037, 413, 63, 1, Interface);
        setBounds(31038, 413, 63, 2, Interface);
        setBounds(31040, 101, 152, 3, Interface);
        setBounds(31041, 101, 152, 4, Interface);
        setBounds(31043, 182, 152, 5, Interface);
        setBounds(31044, 182, 152, 6, Interface);
        setBounds(31046, 262, 152, 7, Interface);
        setBounds(31047, 263, 152, 8, Interface);
        setBounds(31049, 344, 152, 9, Interface);
        setBounds(31050, 344, 152, 10, Interface);
        setBounds(31052, 85, 71, 11, Interface);
        setBounds(31053, 85, 71, 12, Interface);
        setBounds(31055, 235, 71, 13, Interface);
        setBounds(31056, 235, 71, 14, Interface);
        setBounds(31091, 345, 66, 15, Interface);
    }

    /*
     * Pest control
     */
    private static void pestControlInterfaces() {
        RSInterface rsinterface = addTabInterface(21100);
        addSpriteLoader(21101, 895);
        addSpriteLoader(21102, 896);
        addSpriteLoader(21103, 897);
        addSpriteLoader(21104, 898);
        addSpriteLoader(21105, 899);
        addSpriteLoader(21106, 900);
        addText(21107, "W", 0xcc00cc, false, true, 52, fonts, 1);
        addText(21108, "E", 0x0000FF, false, true, 52, fonts, 1);
        addText(21109, "SE", 0xffff44, false, true, 52, fonts, 1);
        addText(21110, "SW", 0xcc0000, false, true, 52, fonts, 1);
        addText(21111, "250", 0x99ff33, false, true, 52, fonts, 1);
        addText(21112, "250", 0x99ff33, false, true, 52, fonts, 1);
        addText(21113, "250", 0x99ff33, false, true, 52, fonts, 1);
        addText(21114, "250", 0x99ff33, false, true, 52, fonts, 1);
        addText(21115, "***", 0x99ff33, false, true, 52, fonts, 1);
        addText(21116, "***", 0x99ff33, false, true, 52, fonts, 1);
        addText(21117, "Time Remaining:", 0xffffff, false, true, 52, fonts, 0);
        addText(21118, "", 0xffffff, false, true, 52, fonts, 0);
        byte byte0 = 18;
        rsinterface.children = new int[byte0];
        rsinterface.childX = new int[byte0];
        rsinterface.childY = new int[byte0];
        setBounds(21101, 361, 26, 0, rsinterface);
        setBounds(21102, 396, 26, 1, rsinterface);
        setBounds(21103, 436, 26, 2, rsinterface);
        setBounds(21104, 474, 26, 3, rsinterface);
        setBounds(21105, 3, 21, 4, rsinterface);
        setBounds(21106, 3, 50, 5, rsinterface);
        setBounds(21107, 371, 60, 6, rsinterface);
        setBounds(21108, 409, 60, 7, rsinterface);
        setBounds(21109, 443, 60, 8, rsinterface);
        setBounds(21110, 479, 60, 9, rsinterface);
        setBounds(21111, 362, 10, 10, rsinterface);
        setBounds(21112, 398, 10, 11, rsinterface);
        setBounds(21113, 436, 10, 12, rsinterface);
        setBounds(21114, 475, 10, 13, rsinterface);
        setBounds(21115, 32, 32, 14, rsinterface);
        setBounds(21116, 32, 62, 15, rsinterface);
        setBounds(21117, 8, 88, 16, rsinterface);
        setBounds(21118, 87, 88, 17, rsinterface);
        RSInterface rsinterface2 = addTabInterface(21005);
        addText(21006, "Next Departure:", 0xCCCBCB, false, true, 52, fonts, 1);
        addText(21007, "Players Ready:", 0x5BD230, false, true, 52, fonts, 1);
        addText(21008, "(Need 5 to 25 players)", 0xDED36A, false, true, 52, fonts, 1);
        addText(21009, "Presents Opened:", 0x99FFFF, false, true, 52, fonts, 1);
        byte0 = 4;
        rsinterface2.children = new int[byte0];
        rsinterface2.childX = new int[byte0];
        rsinterface2.childY = new int[byte0];
        setBounds(21006, 15, 13, 0, rsinterface2);
        setBounds(21007, 15, 33, 1, rsinterface2);
        setBounds(21008, 15, 51, 2, rsinterface2);
        setBounds(21009, 15, 69, 3, rsinterface2);

        RSInterface tab1 = addTabInterface(18730);
        addSpriteLoader(18732, 901);

        addButtonWSpriteLoader(18733, 902, "Purchase", 142, 14);
        addButtonWSpriteLoader(18734, 902, "Purchase", 142, 14);
        addButtonWSpriteLoader(18735, 902, "Purchase", 142, 14);
        addButtonWSpriteLoader(18737, 902, "Purchase", 142, 14);
        addButtonWSpriteLoader(18740, 902, "Purchase", 142, 14);
        addButtonWSpriteLoader(18741, 902, "Purchase", 142, 14);
        addButtonWSpriteLoader(18742, 902, "Purchase", 142, 14);
        addButtonWSpriteLoader(18745, 902, "Purchase", 142, 14);
        addButtonWSpriteLoader(18743, 903, "Enchant");

        addButtonWSpriteLoader(18728, 892, "Close Window", 16, 16);

        addText(18729, "", fonts, 0, 0xFFA500, false, true);

        // addButton(18776, 0, "PestControl/X", 102, 22, "extra", 1);
        tab1.totalChildren(13);
        tab1.child(0, 18731, 4, 16);
        tab1.child(1, 18732, 4, 16);
        tab1.child(2, 18733, 30, 127);
        tab1.child(3, 18734, 30, 201);
        tab1.child(4, 18735, 184, 127);
        tab1.child(5, 18737, 184, 201);

        tab1.child(6, 18740, 184, 274);
        tab1.child(7, 18741, 338, 127);
        tab1.child(8, 18742, 338, 201);
        tab1.child(9, 18743, 56, 231);
        tab1.child(10, 18728, 480, 20);
        tab1.child(11, 18729, 370, 47);
        tab1.child(12, 18745, 338, 274);

        //tab1.child(17, 18776, 334, 46);
        /* Equipment Tab Void */
        RSInterface tab2 = addTabInterface(18746);

        addSpriteLoader(18747, 904);
        addButtonWSpriteLoader(18748, 905, "Back");

        addButtonWSpriteLoader(18749, 902, "Purchase", 142, 14);
        addButtonWSpriteLoader(18750, 902, "Purchase", 142, 14);

        addButtonWSpriteLoader(18728, 892, "Close Window", 16, 16);
        // addButton(18776, 0, "PestControl/X", 102, 22, "extra", 1);
        tab2.totalChildren(6);
        tab2.child(0, 18747, 4, 16);
        tab2.child(1, 18748, 56, 231);
        tab2.child(2, 18749, 30, 127);
        tab2.child(3, 18750, 184, 127);
        tab2.child(4, 18728, 480, 20);
        tab2.child(5, 18729, 370, 47);

    }

    public static void addTooltip(int id, String text) {
        RSInterface rsinterface = addTabInterface(id);
        rsinterface.parentID = id;
        rsinterface.type = 0;
        rsinterface.hidden = true;
        rsinterface.hoverType = -1;
        addTooltipBox(id + 1, text);
        rsinterface.totalChildren(1);
        rsinterface.child(0, id + 1, 0, 0);
    }

    /*
     * Wilderness interface
     */
    private static void wildernessInterface() {
        RSInterface tab = RSInterface.addTabInterface(25347);
        tab.totalChildren(8);
        addTransparentSpriteWSpriteLoader(25348, 742, 150);
        addText(25349, "Target:", fonts, 0, 0xCCCBCB, true, true);
        addText(25350, "None", fonts, 0, 0xCCCBCB, true, true);
        addText(25351, "Wilderness Level:", fonts, 0, 0xCCCBCB, false, true);
        addText(25352, "0", fonts, 0, 0xCCCBCB, true, true);
        addText(25353, "Target Percentage:", fonts, 0, 0xCCCBCB, false, true);
        addText(25354, "99%", fonts, 0, 0xCCCBCB, true, true);
        addText(25355, "Levels: 114 - 126", fonts, 1, 0xff9040, false, true);
        tab.child(0, 25348, 334, 2);
        tab.child(1, 25349, 356, 10);
        tab.child(2, 25350, 445, 10);
        tab.child(3, 25351, 340, 28);
        tab.child(4, 25352, 492, 28);
        tab.child(5, 25353, 340, 46);
        tab.child(6, 25354, 492, 46);
        tab.child(7, 25355, 411, 315);
    }

    /*
     * Emote Tab Interface
     */
    private static void emoteTabInterface() {
        RSInterface Interface = addTabInterface(147);
        setChildren(1, Interface);
        setBounds(665, 0, 0, 0, Interface);
        Interface = addTabInterface(665);
        int[] ids = {161, 162, 163, 164, 165, 167, 168, 169, 170, 171, 172, 173, 19140, 175, 176, 177, 178, 179, 180, 181, 182, 19141, 184, 185, 186, 187, 666, 667, 6522, 6532, 6533, 6540, 6541, 11101, 11102, 11103};
        setChildren(ids.length, Interface);
        String[] tooltips = {"Yes", "No", "Bow", "Angry", "Think", "Wave", "Shrug", "Cheer", "Beckon", "Laugh", "Jump For Joy", "Yawn", "Dance", "Jig", "Spin", "Headbang", "Cry", "Blow Kiss", "Panic", "Raspberry", "Clap", "Salute", "Goblin Bow", "Goblin Salute", "Glass Box", "Climb Rope", "Lean", "Glass Wall", "Zombie Walk", "Zombie Dance", "Zombie Hand", "Shield", "Skillcape", "Air Guitar", "Snowman Dance", "Freeze"};
        int[] X = {8, 47, 92, 138, 7, 46, 90, 134, 5, 49, 91, 137, 4, 48, 88, 133, 6, 49, 97, 135, 8, 51, 86, 136, 0, 47, 94 - 3, 139 - 3, 3, 49, 86, 130, 3, 49, 89, 130};
        int[] Y = {6, 5, 5, 6, 56, 54, 55, 54, 104, 104, 104, 104, 153, 153, 153, 152, 203, 202, 203, 202, 252, 253, 252, 252, 303, 303, 302, 302, 355, 355, 355, 355, 405, 405, 405, 405};
        int frame = 0;
        for (int i : ids) {
            addButtonWSpriteLoader(i, 816 + frame, tooltips[frame], 34, 34);
            setBounds(i, X[frame], Y[frame], frame, Interface);
            frame++;
        }
        Interface = interfaceCache[665];
        Interface.scrollMax = 460;
        Interface.height = 260;
        Interface.width = 174;
    }

    /*
     * Report player
     */
    private static void reportPlayerInterface() {
        int id = 10000;
        RSInterface rsi = addTabInterface(id);
        addSprite(id + 1, 0, "Interfaces/Report/REPORT");
        addButton(id + 2, 1, "report", "Select", 170, 30);
        addButton(id + 3, 1, "report", "Select", 440, 60);
        addText(id + 4, "", fonts, 0, 0xff9933, false, true);
        addText(id + 6, "", fonts, 0, 0xFFFFFF, false, false);
        addButton(id + 7, 0, "", "Close Window", 16, 16);
        addButton(id + 8, 1, "sub", "Submit Report", 120, 30);
        int[][] children = {{id + 1, 12, 10}, {id + 2, 172, 53}, {id + 3, 40, 105}, {id + 4, 175, 66}, {id + 6, 58, 121}, {id + 7, 474, 19}, {id + 8, 198, 254}};
        rsi.totalChildren(children.length);
        for (int i = 0; i < children.length; i++) {
            rsi.child(i, children[i][0], children[i][1], children[i][2]);
        }
    }

    public static void addClickableText(int id, String text, String tooltip, TextDrawingArea tda[], int idx, int color, int width, int height) {
        RSInterface Tab = addTabInterface(id);
        Tab.parentID = id;
        Tab.id = id;
        Tab.type = 4;
        Tab.atActionType = 1;
        Tab.width = width;
        Tab.height = height;
        Tab.contentType = 0;
        Tab.hoverType = 0;
        // Tab.mOverInterToTrigger = -1;
        Tab.centerText = false;
        // Tab.enabledText = true;
        Tab.textDrawingAreas = tda[idx];
        Tab.message = text;
        Tab.tooltip = tooltip;
        //Tab.aString228 = "";
        Tab.textColor(id, color);
    }

    public static void addClanChatListTextWithOptions(int id, String text, String ignore, boolean owner, TextDrawingArea tda[], int idx, int color, int width, int height) {
        RSInterface Tab = addTabInterface(id);
        Tab.parentID = id;
        Tab.id = id;
        Tab.type = 4;
        Tab.atActionType = 1;
        Tab.width = width;
        Tab.height = height;
        Tab.contentType = 0;
        Tab.hoverType = 0;
        // Tab.mOverInterToTrigger = -1;
        Tab.centerText = false;
        // Tab.enabledText = true;
        Tab.textDrawingAreas = tda[idx];
        Tab.message = text;
        //Tab.aString228 = "";
        Tab.textColor(id, color);
        String s = Tab.message;
        if (s.contains("<img")) {
            int prefix = s.indexOf("<img=");
            int suffix = s.indexOf(">");
            s = s.replaceAll(s.substring(prefix + 5, suffix), "");
            s = s.replaceAll("</img>", "");
            s = s.replaceAll("<img=>", "");
        }
        if (!s.equals(ignore)) {
            if (owner) {
                Tab.actions = new String[]{"Promote to Recruit @or1@" + s + "", "Promote to Corporal @or1@" + s + "", "Promote to Sergeant @or1@" + s + "", "Promote to Lieutenant @or1@" + s + "", "Promote to Captain @or1@" + s + "", "Promote to General @or1@" + s + "", "Demote @or1@" + s + "", "Kick @or1@" + s + ""};
            } else {
                Tab.actions = new String[]{"Kick @or1@" + s + ""};
            }
        }
    }

    public static void vengTimer(TextDrawingArea tda[]) {
        RSInterface tab = addInterface(41000);

        addTransparentSpriteWSpriteLoader(41003, 956, 60);
        addSpriteLoader(41001, 944);


        addText(41002, "READY", tda, 1, 0xffffff, true, true);

        tab.totalChildren(3);
        tab.child(0, 41001, 440, 266);
        tab.child(1, 41002, 480, 268);
        tab.child(2, 41003, 434, 262);

//
    }

    public static void barrageTimer(TextDrawingArea tda[]) {
        RSInterface tab = addInterface(44000);

        addTransparentSpriteWSpriteLoader(44003, 956, 60);
        addSpriteLoader(44001, 957);


        addText(44002, "READY", tda, 1, 0xffffff, true, true);

        tab.totalChildren(3);
        tab.child(0, 44001, 440, 238);
        tab.child(1, 44002, 480, 240);
        tab.child(2, 44003, 434, 234);

//
    }

    public static void specButton(TextDrawingArea tda[]) {
        RSInterface tab = addInterface(41005);
        tab.childToIntersect = 41006;
        addButtonWSpriteLoader(41006, 946, 945, "Activate special");

        RSInterface btn = interfaceCache[41006];
        btn.positionX = 463;
        btn.positionY = 170;
        btn.width = 48;
        btn.height = 48;

        tab.totalChildren(1);
        tab.child(0, 41006, 463, 170);

    }

    public static void loyaltyBox(TextDrawingArea wid[]) {
        RSInterface tab = addInterface(41500);
        addTransparentSpriteWSpriteLoader(41501, 949, 70);
        addText(41502, "24:36:18", wid, 0, 0xffffff, true, true);
        //  addTransparentSpriteWSpriteLoader(41503, 950, 175);
        addButtonWSpriteLoader(41503, 950, "Loyalty Box", 26, 29);


        tab.children(3);
        tab.child(0, 41501, 5, 288);
        tab.child(1, 41502, 23, 323);
        tab.child(2, 41503, 11, 290);


    }

    public static void nexBar(TextDrawingArea wid[]) {
        RSInterface tab = addInterface(7799);
        addTransparentSpriteWSpriteLoader(7800, 947, 150);
        addText(7801, "Rock crab", wid, 0, 0xffffff, true, true);
        tab.message = "0/0";
        tab.x = 150;
        tab.y = 305;
        tab.children(2);
        tab.child(0, 7800, 20, 200);
        tab.child(1, 7801, 68, 305);


    }

    public static void entityInterface(TextDrawingArea wid[]) {
        RSInterface tab = addInterface(41020);

        addTransparentSpriteWSpriteLoader(41021, 947, 150);
        addText(41022, " ", wid, 1, 0xffffff, true, true);

        tab.message = " ";
        tab.x = 2;
        tab.y = 9;
        tab.children(2);
        tab.child(0, 41021, 0, 20);
        tab.child(1, 41022, 63, 22);


    }

    public static void clueScrolls(TextDrawingArea wid[]) {
        RSInterface tab = addInterface(47700);

        addSpriteLoader(47701, 959);
        addText(47702, "Clues Completed", wid, 1, 16750623, true, true);
        addText(47703, "125", wid, 2, 0xffffff, true, true);
        addText(47704, "Dig somewhere in the Edgeville bank", wid, 1, 16750623, true, true);
        addCloseButton(47705, 18535, 18536);

        tab.children(5);
        tab.child(0, 47701, 35, 60);
        tab.child(1, 47702, 93, 100);
        tab.child(2, 47703, 103, 123);
        tab.child(3, 47704, 252, 222);
        tab.child(4, 47705, 453, 77);


    }

    public static void vidOptions(TextDrawingArea tda[]) {
        RSInterface tab = addTabInterface(40030);
        // RSInterface rsinterface = addTabInterface(40030);
        int i = 0;
        byte byte0 = 2;
        addHDSprite(40042, 375, 375);
        addHoverButton(40039, CLOSE_BUTTON, CLOSE_BUTTON, 16, 16, "Close Window", 0, 40040, 1);
        addHoveredButton(40040, CLOSE_BUTTON_HOVER, CLOSE_BUTTON_HOVER, 16, 16, 40027);
        addText(40041, "Game Settings", tda, 2, 0xff9b00, true, true);

        addConfigButton(906, 904, 387, 388, 32, 16, "Dark", 1, 5, 166);
        addConfigButton(908, 904, 389, 390, 32, 16, "Normal", 2, 5, 166);
        addConfigButton(910, 904, 391, 392, 32, 16, "Bright", 3, 5, 166);
        addConfigButton(912, 904, 393, 394, 32, 16, "Very Bright", 4, 5, 166);

        addConfigButton(941, 904, 395, 396, 26, 16, "Volume: 0", 4, 5, 169);
        addConfigButton(942, 904, 397, 398, 26, 16, "Volume: 1", 3, 5, 169);
        addConfigButton(943, 904, 399, 400, 26, 16, "Volume: 2", 2, 5, 169);
        addConfigButton(944, 904, 401, 402, 26, 16, "Volume: 3", 1, 5, 169);
        addConfigButton(945, 904, 403, 404, 24, 16, "Volume: 4", 0, 5, 169);

        addHDSprite(40036, 377, 377);
        addHDSprite(40037, 379, 379);

        addHoverButton(40043, 405, 405, 50, 39, "Fixed", 0, 40044, 1);
        addHoveredButton(40044, 406, 406, 50, 39, 40045);
        addHoverButton(40046, 407, 407, 50, 39, "Resizable", 0, 40047, 1);
        addHoveredButton(40047, 408, 408, 50, 39, 40048);
        addHoverButton(40049, 409, 409, 50, 39, "Fullscreen", 0, 40050, 1);
        addHoveredButton(40050, 410, 410, 50, 39, 40051);

        tab.totalChildren(25);
        tab.child(0, 40042, 25, 75 + byte0);
        tab.child(1, 906, 35, 140 + byte0);
        tab.child(2, 908, 61, 140 + byte0);
        tab.child(3, 910, 87, 140 + byte0);
        tab.child(4, 912, 113, 140 + byte0);

        tab.child(5, 40036, 75, 105);

        tab.child(6, 40039, 450, 80);
        tab.child(7, 40040, 450, 80);
        tab.child(8, 40041, 250, 80);

        tab.child(9, 40043, 165, 115);
        tab.child(10, 40044, 165, 115);
        tab.child(11, 40046, 220, 115);
        tab.child(12, 40047, 220, 115);
        tab.child(13, 40049, 275, 115);
        tab.child(14, 40050, 275, 115);

        tab.child(15, 40037, 390, 105);

        tab.child(16, 941, 340, 140 + byte0);
        tab.child(17, 942, 366, 140 + byte0);
        tab.child(18, 943, 392, 140 + byte0);
        tab.child(19, 944, 418, 140 + byte0);
        tab.child(20, 945, 444, 140 + byte0);

        tab.child(21, 35560, 40, 180);
        tab.child(22, 35580, 105, 180);
        tab.child(23, 35610, 208, 180);
        tab.child(24, 35640, 310, 180);
    }

    public static void addButton(int id, int sid, String spriteName, String tooltip, int mOver, int atAction, int width, int height) {
        RSInterface tab = interfaceCache[id] = new RSInterface();
        tab.id = id;
        tab.parentID = id;
        tab.type = 5;
        tab.atActionType = atAction;
        tab.contentType = 0;
        tab.hoverType = mOver;
        tab.disabledSprite = imageLoader(sid, spriteName);
        tab.enabledSprite = imageLoader(sid, spriteName);
        tab.width = width;
        tab.height = height;
        tab.tooltip = tooltip;
        // tab.inventoryhover = true;
    }

    /*
     * Price checker interface
     */
    private static void priceCheckerInterface() {
        RSInterface rsi = addTabInterface(42000);
        final String[] options = {"Remove 1", "Remove 5", "Remove 10", "Remove All", "Remove X"};
        addSpriteLoader(18245, 871);
        addCloseButton(18247, 18535, 18536);
        addItemOnInterface(18246, 4393, options);
        rsi.totalChildren(88);
        rsi.child(0, 18245, 10, 20);//was 10 so + 10
        rsi.child(1, 18246, 100, 50);
        rsi.child(2, 18247, 472, 21);
        addText(18248, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(3, 18248, 472, 23);
        addText(18350, "Total value:", fonts, 0, 0xFFFFFF, false, true);
        rsi.child(4, 18350, 225, 295); // Open Text
        addText(18351, "0", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(5, 18351, 251, 306);
        addText(18352, "", fonts, 0, 0xFFFFFF, false, true);
        rsi.child(6, 18352, 120, 150);
        addText(18353, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(7, 18353, 120, 85);
        addText(18354, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(8, 18354, 120, 95);
        addText(18355, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(9, 18355, 120, 105);
        addText(18356, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(10, 18356, 190, 85);
        addText(18357, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(11, 18357, 190, 95);
        addText(18358, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(12, 18358, 190, 105);
        addText(18359, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(13, 18359, 260, 85);
        addText(18360, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(14, 18360, 260, 95);
        addText(18361, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(15, 18361, 260, 105);
        addText(18362, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(16, 18362, 330, 85);
        addText(18363, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(17, 18363, 330, 95);
        addText(18364, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(18, 18364, 330, 105);
        addText(18365, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(19, 18365, 400, 85);
        addText(18366, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(20, 18366, 400, 95);
        addText(18367, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(21, 18367, 400, 105);
        addText(18368, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(22, 18368, 120, 145);
        addText(18369, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(23, 18369, 120, 155);
        addText(18370, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(24, 18370, 120, 165);
        addText(18371, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(25, 18371, 190, 145);
        addText(18372, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(26, 18372, 190, 155);
        addText(18373, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(27, 18373, 190, 165);
        addText(18374, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(28, 18374, 260, 145);
        addText(18375, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(29, 18375, 260, 155);
        addText(18376, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(30, 18376, 260, 165);
        addText(18377, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(31, 18377, 330, 145);
        addText(18378, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(32, 18378, 330, 155);
        addText(18379, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(33, 18379, 330, 165);
        addText(18380, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(34, 18380, 400, 145);
        addText(18381, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(35, 18381, 400, 155);
        addText(18382, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(36, 18382, 400, 165);
        addText(18383, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(37, 18383, 120, 205);
        addText(18384, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(38, 18384, 120, 215);
        addText(18385, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(39, 18385, 120, 225);
        addText(18386, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(40, 18386, 190, 205);
        addText(18387, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(41, 18387, 190, 215);
        addText(18388, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(42, 18388, 190, 225);
        addText(18389, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(43, 18389, 260, 205);
        addText(18390, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(44, 18390, 260, 215);
        addText(18391, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(45, 18391, 260, 225);
        addText(18392, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(46, 18392, 330, 205);
        addText(18393, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(47, 18393, 330, 215);
        addText(18394, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(48, 18394, 330, 225);
        addText(18395, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(49, 18395, 400, 205);
        addText(18396, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(50, 18396, 400, 215);
        addText(18397, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(51, 18397, 400, 225);
        addText(18398, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(52, 18398, 120, 260);
        addText(18399, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(53, 18399, 120, 270);
        addText(18400, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(54, 18400, 120, 280);
        addText(18401, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(55, 18401, 190, 260);
        addText(18402, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(56, 18402, 190, 270);
        addText(18403, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(57, 18403, 190, 280);
        addText(18404, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(58, 18404, 260, 260);
        addText(18405, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(59, 18405, 260, 270);
        addText(18406, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(60, 18406, 260, 280);
        addText(18407, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(61, 18407, 330, 260);
        addText(18408, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(62, 18408, 330, 270);
        addText(18409, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(63, 18409, 330, 280);
        addText(18410, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(64, 18410, 400, 260);
        addText(18411, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(65, 18411, 400, 270);
        addText(18412, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(66, 18412, 400, 280);
        addText(18413, "", fonts, 0, 0xFFFFFF, true, true);
        rsi.child(67, 18413, 260, 155);
        /**
         * First row of items
         */
        int child = 68;
        int x = 170;
        for (int i = 18500; i <= 18503; i++) {
            addItemOnInterface(i, 4393, options);
            rsi.child(child, i, x, 50);
            child++;
            x += 70;
        }
        /**
         * Second row of items
         */
        child = 72;
        x = 100;
        for (int i = 0; i <= 4; i++) {
            addItemOnInterface(18504 + i, 4393, options);
            rsi.child(child, 18504 + i, x, 110);
            child++;
            x += 70;
        }
        /*
         * Third row of items
         */
        child = 77;
        x = 100;
        for (int i = 0; i <= 4; i++) {
            addItemOnInterface(18509 + i, 4393, options);
            rsi.child(child, 18509 + i, x, 170);
            child++;
            x += 70;
        }
        /**
         * Fourth row of items
         */
        child = 82;
        x = 100;
        for (int i = 0; i <= 4; i++) {
            addItemOnInterface(18514 + i, 4393, options);
            rsi.child(child, 18514 + i, x, 230);
            child++;
            x += 70;
        }
        rsi.child(87, 18535, 472, 21);
    }

    private static void questTabInterfaceNew() {
        RSInterface tab = addTabInterface(26600);
        RSInterface scroll = addTabInterface(26700);
        addHoverButtonWSpriteLoader(26601, 965, 85, 20, "Staff Online", -1, 26602, 1);
        addHoveredImageWSpriteLoader(26602, 964, 85, 20, 26603);
        addText(26609, "Staff Online", fonts, 0, 16750623, false, true);
        addHoverButtonWSpriteLoader(26604, 965, 85, 20, "Kill Log", -1, 26605, 1);
        addHoveredImageWSpriteLoader(26605, 964, 85, 20, 26606);
        addText(26610, "Kill Log", fonts, 0, 16750623, false, true);
        addSpriteLoader(26607, 967);
        addText(26608, "Players Online: @gre@30", fonts, 2, 16750623, false, true);
        addHoverButtonWSpriteLoader(26611, 965, 85, 20, "Player Profiles", -1, 26612, 1);
        addHoveredImageWSpriteLoader(26612, 964, 85, 20, 26613);
        addText(26617, "Drop Tables", fonts, 0, 16750623, false, true);
        addHoverButtonWSpriteLoader(26614, 965, 85, 20, "Drop Log", -1, 26615, 1);
        addHoveredImageWSpriteLoader(26615, 964, 85, 20, 26616);
        addText(26618, "Drop Log", fonts, 0, 16750623, false, true);
        addSpriteLoader(26619, 722);

        addSpriteLoader(26624, 722);

        addSpriteLoader(26621, 966);
        addText(26622, "@or2@Crashed Star: @gre@ Home", fonts, 0, 16750623, false, true);
        addText(26623, "@or2@Well of Goodwill: @gre@ Active", fonts, 0, 16750623, false, true);
        addText(26625, "@or2@Evil Tree:@gre@ Home", fonts, 0, 16750623, false, true);
        addText(26626, "@or2@Bonus:@gre@ Active", fonts, 0, 16750623, false, true);


        tab.totalChildren(22);
        tab.child(0, 26601, 7, 25);
        tab.child(1, 26602, 7, 25);
        tab.child(2, 26604, 7, 47);
        tab.child(3, 26605, 7, 47);
        tab.child(4, 26607, 1 / 2, -3);
        tab.child(5, 26608, 33, 3);
        tab.child(6, 26609, 16, 29);
        tab.child(7, 26610, 30, 50);
        tab.child(8, 26611, 100, 25);
        tab.child(9, 26612, 100, 25);
        tab.child(10, 26614, 100, 47);
        tab.child(11, 26615, 100, 47);
        tab.child(12, 26617, 112, 29);
        tab.child(13, 26618, 122, 50);
        tab.child(14, 26621, 0, 125);
        tab.child(15, 26700, 0, 127);
        tab.child(16, 26619, 0, 125);
        tab.child(17, 26622, 5, 86);
        tab.child(18, 26623, 5, 99);
        tab.child(19, 26624, 0, 69);
        tab.child(20, 26625, 5, 73);
        tab.child(21, 26626, 5, 112);


        scroll.totalChildren(70);
        scroll.width = 174;
        scroll.height = 134;
        scroll.scrollMax = 420;
        int k = 0;
        int y = 4;
        for (int i = 26701; i < 26771; i++) {
            scroll.child(k, i, 6, y);
            y += 17;
            k++;
            if (i == 26723 || i == 26724) {
                addClickableText(i, "test", "Open Quest Log", fonts, 0, 0xff0000, 167, 13);
            } else {

                addText(i, " ", fonts, 0, 0xff0000, false, true);
            }
        }
    }

    public static void addClickText(final int interfaceID, final String line, final int color, final boolean center, final boolean shadow, final int hover, final TextDrawingArea[] typeFace, final int textType, final String tooltip) {
        final RSInterface text = addInterface(interfaceID);
        text.id = interfaceID;
        text.parentID = interfaceID;
        text.type = 4;
        text.atActionType = 4;
        text.contentType = 0;
        text.width = /* typeFace[textType].getTextWidth(line) */130;
        text.height = /* typeFace[textType].getTextHeight(line) */textType == 0 ? 14 : 21;
        text.hoverType = hover;
        text.centerText = center;
        text.shadowed = shadow;
        text.textDrawingAreas = typeFace[textType];
        text.message = line;
        text.enabledColor = color;
        text.tooltip = tooltip;
    }

    private static void addButton(int ID, int type, int hoverID, int dS, int eS, String NAME, int W, int H, String text, int configFrame, int configId) {
        RSInterface rsinterface = addInterface(ID);
        rsinterface.id = ID;
        rsinterface.parentID = ID;
        rsinterface.type = 5;
        rsinterface.atActionType = type;
        rsinterface.opacity = 0;
        rsinterface.hoverType = hoverID;
        rsinterface.enabledSprite = imageLoader(dS, NAME);
        rsinterface.disabledSprite = imageLoader(eS, NAME);
        rsinterface.width = W;
        rsinterface.height = H;
        rsinterface.tooltip = text;
        rsinterface.valueCompareType = new int[1];
        rsinterface.requiredValues = new int[1];
        rsinterface.valueCompareType[0] = 1;
        rsinterface.requiredValues[0] = configId;
        rsinterface.valueIndexArray = new int[1][3];
        rsinterface.valueIndexArray[0][0] = 5;
        rsinterface.valueIndexArray[0][1] = configFrame;
        rsinterface.valueIndexArray[0][2] = 0;
    }

    public static void addScroll(int id, int height, int width, int scrollAmount, int amount) {
        final RSInterface scroll = addInterface(id);
        final int ACHIEVEMENTS = amount;
        scroll.width = width;
        scroll.height = height;
        scroll.scrollMax = (int) (15.5 * amount);// scrollAmount;
        int x = 0;
        int y = 0;
        int frame = 0;
        scroll.totalChildren(ACHIEVEMENTS);
        for (int i = id + 20; i < id + 20 + ACHIEVEMENTS; i++) {
            addText(i, "" + i + "", 0xAF6A1B, false, true, 0, fonts, 0);
            scroll.child(frame, i, x, y);
            y += 15;
            frame++;
        }
    }

    public static void statistics1(final TextDrawingArea[] tda) {

        int id = 28901;

        final RSInterface tab = addInterface(id);

        final String directory = "Achievement/achievement";

        addScroll(id + 1, 200, 135, 620, 40);//is it delayed lol
        addScroll(id + 81, 200, 140, 600, 40);
        addScroll(id + 160, 190, 100, 200, 10);
        addSprite(id + 2, 0, directory);

        addText(id + 3, "PaeScape", 0xAF6A1B, false, true, 0, tda, 2);
        addText(id + 4, "100% Drops", 0xAF6A1B, false, true, 0, tda, 2);
        addText(id + 5, "Regular Drops: 20", 0xAF6A1B, true, true, 0, tda, 2);
        addText(id + 6, "Rare Drops: 10", 0xAF6A1B, true, true, 0, tda, 2);
        addText(id + 7, "Rare item drop rate: 1:300. Ring of wealth drop rate 1:200", 0xAF6A1B, true, true, 0, tda, 0);

        tab.totalChildren(10);

        int frame = 7;

        tab.child(0, id + 2, 1, 1);
        tab.child(1, id + 1, 160, 70);
        tab.child(2, 26132, 492, 4);
        tab.child(3, id + 81, 335, 70);
        tab.child(4, id + 3, 28, 45);
        tab.child(5, id + 4, 28, 90);
        tab.child(6, id + 160, 20, 120);
        tab.child(frame, id + 5, 230, 40);
        frame++;
        tab.child(frame, id + 6, 410, 40);
        frame++;
        tab.child(frame, id + 7, 260, 280);
        frame++;
    }

    public static void addCharPV(int ID) {
        RSInterface t = interfaceCache[ID] = new RSInterface();
        t.id = ID;
        t.parentID = ID;
        t.type = 6;
        t.atActionType = 0;
        t.contentType = 329;
        t.width = 136;
        t.height = 168;
        t.opacity = 0;
        t.hoverType = 0;
        t.modelZoom = 560;
        t.modelRotation1 = 150;
        t.modelRotation2 = 0;
        t.disabledAnimationId = -1;
        t.enabledAnimationId = -1;
    }

    /*
     * public static void addHovered(int i, int j, String imageName, int w, int
     * h, int IMAGEID) { RSInterface hover = addTabInterface(i); hover.parentID
     * = i; hover.id = i; hover.type = 0; hover.atActionType = 0; hover.width =
     * w; hover.height = h; hover.interfaceShown = true; hover.hoverType = -1;
     * addSprite(IMAGEID, j, imageName); setChildren(1, hover);
     * setBounds(IMAGEID, 0, 0, 0, hover); }
     */

    public static void playerProfile(TextDrawingArea[] tda) {
        //statistics(fonts);
        RSInterface tab = addInterface(36500);
        tab.totalChildren(39);
        addSprite(36501, 0, "Interfaces/PlayerProfile/BG");

        addHover(36502, 3, 0, 36503, 0, "Interfaces/PlayerProfile/CLOSE", 21, 21, "Close");
        addHovered(36503, 1, "Interfaces/PlayerProfile/CLOSE", 21, 21, 36504);

        addHover(36505, 1, 0, 36506, 0, "Interfaces/PlayerProfile/SPRITE", 72, 32, "Like");
        addHovered(36506, 1, "Interfaces/PlayerProfile/SPRITE", 72, 32, 36507);
        addHover(36508, 1, 0, 36509, 2, "Interfaces/PlayerProfile/SPRITE", 72, 32, "Dislike");
        addHovered(36509, 3, "Interfaces/PlayerProfile/SPRITE", 72, 32, 36510);

        addText(36511, "Player Profile", 0xff9933, true, false, -1, tda, 2);
        addText(36512, "Viewing: ", 0xff9933, true, false, -1, tda, 2);
        addText(36513, "Likes: 0", 0xff9933, true, false, -1, tda, 1);
        addText(36514, "Dislikes: 0", 0xff9933, true, false, -1, tda, 1);
        addText(36515, "Views: 0", 0xff9933, true, false, -1, tda, 1);
        addChar(36516);
        addSprite(36517, 4, "Interfaces/PlayerProfile/SPRITE");
        addSprite(36518, 5, "Interfaces/PlayerProfile/SPRITE");
        addText(36519, "Combat:", 0xff9933, false, false, -1, tda, 2);
        addText(36520, "Skilling:", 0xff9933, false, false, -1, tda, 2);
        addText(36521, "Kills: 0", 0xff9933, false, false, -1, tda, 1);
        addText(36522, "Deaths: 0", 0xff9933, false, false, -1, tda, 1);
        addText(36523, "KDR: 0", 0xff9933, false, false, -1, tda, 1);
        addText(36524, "Killstreak: 0", 0xff9933, false, false, -1, tda, 1);
        addText(36525, "PvP Points: 0", 0xff9933, false, false, -1, tda, 1);
        addText(36526, "EMPTY 6", 0xff9933, false, false, -1, tda, 1);
        addText(36527, "EMPTY 7", 0xff9933, false, false, -1, tda, 1);
        addText(36528, "EMPTY 8", 0xff9933, false, false, -1, tda, 1);
        addText(36529, "EMPTY 9", 0xff9933, false, false, -1, tda, 1);
        addText(36530, "EMPTY 10", 0xff9933, false, false, -1, tda, 1);
        addText(36531, "EMPTY 1", 0xff9933, false, false, -1, tda, 1);
        addText(36532, "EMPTY 2", 0xff9933, false, false, -1, tda, 1);
        addText(36533, "EMPTY 3", 0xff9933, false, false, -1, tda, 1);
        addText(36534, "EMPTY 4", 0xff9933, false, false, -1, tda, 1);
        addText(36535, "EMPTY 5", 0xff9933, false, false, -1, tda, 1);
        addText(36536, "EMPTY 6", 0xff9933, false, false, -1, tda, 1);
        addText(36537, "EMPTY 7", 0xff9933, false, false, -1, tda, 1);
        addText(36538, "EMPTY 8", 0xff9933, false, false, -1, tda, 1);
        addText(36539, "EMPTY 9", 0xff9933, false, false, -1, tda, 1);
        addText(36540, "EMPTY 10", 0xff9933, false, false, -1, tda, 1);

        addText(36541, "Like", 0xff9933, true, false, -1, tda, 1);
        addText(36542, "Dislike", 0xff9933, true, false, -1, tda, 1);

        tab.child(0, 36501, 14, 5);
        tab.child(1, 36502, 477, 8);
        tab.child(2, 36503, 477, 8);
        tab.child(3, 36505, 62, 50);
        tab.child(4, 36506, 62, 50);
        tab.child(5, 36508, 380, 50);
        tab.child(6, 36509, 380, 50);

        tab.child(7, 36511, 257, 8);
        tab.child(8, 36512, 257, 37);
        tab.child(9, 36513, 220, 70);
        tab.child(10, 36514, 302, 70);
        tab.child(11, 36515, 447, 305);
        tab.child(12, 36516, 170, 210);
        tab.child(13, 36517, 25, 95);
        tab.child(14, 36518, 343, 95);
        tab.child(15, 36519, 85, 105);
        tab.child(16, 36520, 370, 105);
        tab.child(17, 36521, 40, 150);
        tab.child(18, 36522, 40, 165);
        tab.child(19, 36523, 40, 180);
        tab.child(20, 36524, 40, 195);
        tab.child(21, 36525, 40, 210);
        tab.child(22, 36526, 40, 225);
        tab.child(23, 36527, 40, 240);
        tab.child(24, 36528, 40, 255);
        tab.child(25, 36529, 40, 270);
        tab.child(26, 36530, 40, 285);
        tab.child(27, 36531, 360, 150);
        tab.child(28, 36532, 360, 165);
        tab.child(29, 36533, 360, 180);
        tab.child(30, 36534, 360, 195);
        tab.child(31, 36535, 360, 210);
        tab.child(32, 36536, 360, 225);
        tab.child(33, 36537, 360, 240);
        tab.child(34, 36538, 360, 255);
        tab.child(35, 36539, 360, 270);
        tab.child(36, 36540, 360, 285);

        tab.child(37, 36541, 103, 58);
        tab.child(38, 36542, 423, 58);
    }

    public static void scrollChatBox(final TextDrawingArea[] fonts) {
        final RSInterface tab = addInterface(27777);

        addClickScroll(27778, 90, 275, 20);
        addText(27776, "Bosses", 0xAF6A1B, true, true, 0, fonts, 2);

        tab.totalChildren(3);

        tab.child(0, 27778, 155, 18);
        tab.child(1, 27776, 256, 0);
        tab.child(2, 2703, 430, 0);
    }

    public static void addClickScroll(int id, int height, int width, int amount) {
        final RSInterface scroll = addInterface(id);
        final int ACHIEVEMENTS = amount;
        scroll.width = width;
        scroll.height = height;
        scroll.scrollMax = (int) (15.5 * amount);// scrollAmount;
        int x = 25;
        int y = 0;
        int frame = 0;
        scroll.totalChildren(ACHIEVEMENTS);
        for (int i = (id + 20); i < (id + 20 + ACHIEVEMENTS); i++) {
            addClickableText(i, "" + (i), "Select", fonts, 1, 0, true, false, 150);
            scroll.child(frame, i, x, y);
            y += 15;
            frame++;
        }
    }

    public static void addClickableText(int id, String text, String tooltip, TextDrawingArea tda[], int idx, int color, boolean center, boolean shadow, int width) {
        RSInterface tab = addTabInterface(id);
        tab.parentID = id;
        tab.id = id;
        tab.type = 4;
        tab.atActionType = 1;
        tab.width = width;
        tab.height = 11;
        tab.contentType = 0;
        tab.opacity = 0;
        tab.hoverType = -1;
        tab.centerText = center;
        tab.shadowed = shadow;
        tab.textDrawingAreas = tda[idx];
        tab.message = text;
        tab.enabledMessage = "";
        tab.disabledColor = color;
        tab.enabledColor = 0xffffff;
        tab.tooltip = tooltip;
    }

    /*
     * public static void addHover(int i, int aT, int cT, int hoverid, int sId,
     * String NAME, int W, int H, String tip) { RSInterface hover =
     * addTabInterface(i); hover.id = i; hover.parentID = i; hover.type = 5;
     * hover.atActionType = aT; hover.contentType = cT; // hover.hoverType =
     * hoverid; hover.sprite1 = imageLoader(sId, NAME); hover.sprite2 =
     * imageLoader(sId, NAME); hover.width = W; hover.height = H; hover.tooltip
     * = tip; }
     */

    //End of Drop Table Interfaces
    private static void questTabInterface() {
        RSInterface tab = addTabInterface(639);
        RSInterface scroll = addTabInterface(15016);
        addText(39155, "PaeScape", fonts, 2, 16750623, false, true);
        addSpriteLoader(39156, 722);
        addSpriteLoader(39157, 723);
        addSpriteLoader(39158, 722);
//		addButtonWSpriteLoader(39150, 229, "Staff Tab");

        scroll.totalChildren(55);
        scroll.width = 174;
        scroll.height = 224;
        scroll.scrollMax = 810;
        tab.totalChildren(5);

        tab.child(0, 39155, 5, 3);
        tab.child(1, 39156, 0, 22);
        tab.child(2, 39157, 0, 25);
        tab.child(3, 39158, 0, 249);
        tab.child(4, 15016, 0, 25);
//		tab.child(5, 39150, 170, 6);

        int k = 0;
        int y = 0;
        for (int i = 39159; i < 39214; i++) {
            scroll.child(k++, i, 6, y);
            y += 16;
//			k++;
            addClickableText(i, "", "Select", fonts, 0, 0xff0000, false, true, 150);
        }
    }

    /*	private static void questTabInterface() {
		RSInterface tab = addTabInterface(639);
		RSInterface scroll = addTabInterface(15016);
		addText(39155, "Player Panel", fonts, 2, 16750623, false, true);
		addSpriteLoader(39156, 722);
		addSpriteLoader(39157, 723);
		addSpriteLoader(39158, 722);
		addButtonWSpriteLoader(39150, 229, "Staff Tab");

		scroll.totalChildren(55);
		scroll.width = 174; scroll.height = 224; scroll.scrollMax = 810;
		tab.totalChildren(6);

		tab.child(0, 39155, 5, 3);
		tab.child(1, 39156, 0, 22);
		tab.child(2, 39157, 0, 25);
		tab.child(3, 39158, 0, 249);
		tab.child(4, 15016, 0, 25);
		tab.child(5, 39150, 170, 6);

		int k = 0;
		int y = 0;
		for(int i = 39159; i < 39214; i++) {
			scroll.child(k, i, 6, y);
			y += 16;
			k++;
			if(i == 39160) {
				addClickableText(i, "", "View", fonts, 1, 0xff0000, 167, 13);
			} else if(i == 39163) {
				addClickableText(i, "", "Check", fonts, 1, 0xff0000, 167, 13);
			} else if(i == 39171 || i == 39172) {
				addClickableText(i, "", "Toggle", fonts, 1, 0xff0000, 167, 13);
			} else if(i == 39199 || i == 39200) {
				addClickableText(i, "", "Open Quest Log", fonts, 1, 0xff0000, 167, 13);
			} else if(i == 39190 || i ==39191 || i >= 39203 && i <= 39208) {
				addClickableText(i, "", "Open", fonts, 1, 0xff0000, 167, 13);
			} else {
				addText(i, "", fonts, 1, 0xff0000, false, true);
			}
		}
	}
*/
    private static void questTabInterface2() {
        RSInterface tab = addTabInterface(55000);
        RSInterface scroll = addTabInterface(55010);
        addText(55001, "Kills Tracker", fonts, 2, 16750623, false, true);
        addSpriteLoader(55002, 722);
        addSpriteLoader(55003, 723);
        addSpriteLoader(55004, 722);
        addButtonWSpriteLoader(55005, 725, "Go Back", 26, 26);

        scroll.totalChildren(44);
        scroll.width = 174;
        scroll.height = 224;
        scroll.scrollMax = 710;
        tab.totalChildren(6);

        tab.child(0, 55001, 5, 3);
        tab.child(1, 55002, 0, 22);
        tab.child(2, 55003, 0, 25);
        tab.child(3, 55004, 0, 249);
        tab.child(4, 55005, 164, 0);
        tab.child(5, 55010, 0, 25);

        int k = 0;
        int y = 0;
        for (int i = 55020; i < 55064; i++) {
            scroll.child(k, i, 6, y);
            y += 16;
            k++;
            addText(i, "", fonts, 1, 0xff0000, false, true);
        }
    }

    public static void setChildren(int total, RSInterface rsinterface) {
        rsinterface.children = new int[total];
        rsinterface.childX = new int[total];
        rsinterface.childY = new int[total];
    }

    public static void drawTooltip(int id, String text) {
        RSInterface rsinterface = addTabInterface(id);
        rsinterface.parentID = id;
        rsinterface.type = 0;
        rsinterface.hidden = true;
        rsinterface.hoverType = -1;
        addTooltipBox(id + 1, text);
        rsinterface.totalChildren(1);
        rsinterface.child(0, id + 1, 0, 0);
    }

    public static void addTooltipBox(int id, String text) {
        RSInterface rsi = addInterface(id);
        rsi.id = id;
        rsi.parentID = id;
        rsi.type = 8;
        rsi.popupString = text;
    }

    public static void addTooltipBox2(int id, String text) {
        RSInterface rsi = addInterface(id);
        rsi.id = id;
        rsi.parentID = id;
        rsi.type = 12;
        rsi.message = text;
    }

    public static void collectSell(TextDrawingArea[] TDA) {
        RSInterface rsinterface = addTabInterface(54700);
        int x = 9;
        addHDSprite(54701, 639, 639);

        addHoverButton(54702, CLOSE_BUTTON, CLOSE_BUTTON, 16, 16, "Close", 0, 54703, 1);
        addHoveredButton(54703, CLOSE_BUTTON_HOVER, CLOSE_BUTTON_HOVER, 16, 16, 54704);
        addHoverButton(54758, 607, 607, 29, 23, "Back", 0, 54759, 1);
        addHoveredButton(54759, 608, 608, 29, 23, 54760);
        addText(54769, "Choose an item to exchange", TDA, 0, 0x96731A, false, true);
        addText(54770, "Select an item from your invertory to sell.", TDA, 0, 0x958E60, false, true);
        addText(54771, "0", TDA, 0, 0xB58338, true, true);
        addText(54772, "1 gp", TDA, 0, 0xB58338, true, true);
        addText(54773, "0 gp", TDA, 0, 0xB58338, true, true);
        addHoverButton(54793, 653, 653, 40, 36, "[GE]", 0, 54794, 1);
        addHoveredButton(54794, 652, 652, 40, 36, 54795);
        addHoverButton(54796, 653, 653, 40, 36, "[GE]", 0, 54797, 1);
        addHoveredButton(54797, 652, 652, 40, 36, 54798);
        addItemOnInterface(54780, 2903, new String[]{null});
        addItemOnInterface(54781, 2903, new String[]{"Collect"});
        addItemOnInterface(54782, 2904, new String[]{"Collect"});
        addText(54784, "", TDA, 0, 0xFFFF00, false, true);
        addText(54785, "", TDA, 0, 0xFFFF00, false, true);
        addText(54787, "N/A", TDA, 0, 0xB58338, false, true);
        addText(54788, "", TDA, 0, 0xFFFF00, true, true);
        addText(54789, "", TDA, 0, 0xFFFF00, true, true);
        addHoverButton(54800, 640, 640, 20, 20, "Abort offer", 0, 54801, 1);
        addHoveredButton(54801, 641, 641, 20, 20, 54802);
        rsinterface.totalChildren(24);
        rsinterface.child(0, 54701, 4 + x, 23);// 385, 260
        rsinterface.child(1, 54702, 464 + x, 33);// 435, 260
        rsinterface.child(2, 54703, 464 + x, 33);
        rsinterface.child(3, 54758, 19 + x, 284);
        rsinterface.child(4, 54759, 19 + x, 284);
        rsinterface.child(5, 54769, 202 + x, 71);
        rsinterface.child(6, 54770, 202 + x, 98);
        rsinterface.child(7, 54771, 142 + x, 185);
        rsinterface.child(8, 54772, 354 + x, 185);
        rsinterface.child(9, 54773, 252 + x, 246);
        rsinterface.child(10, 54793, 386 + x, 256 + 23);
        rsinterface.child(11, 54794, 386 + x, 256 + 23);
        rsinterface.child(12, 54796, 435 + x, 256 + 23);
        rsinterface.child(13, 54797, 435 + x, 256 + 23);
        rsinterface.child(14, 54780, 97 + x, 97);
        rsinterface.child(15, 54781, 385 + 4 + x, 260 + 23);
        rsinterface.child(16, 54782, 435 + 4 + x, 260 + 23);
        rsinterface.child(17, 54784, 385 + 4 + x, 260 + 23);
        rsinterface.child(18, 54785, 435 + 4 + x, 260 + 23);
        rsinterface.child(19, 54787, 108, 136);
        rsinterface.child(20, 54788, 214 + x, 249 + 23);
        rsinterface.child(21, 54789, 214 + x, 263 + 23);
        rsinterface.child(22, 54800, 345 + x, 250 + 23);
        rsinterface.child(23, 54801, 345 + x, 250 + 23);
    }

    public static void collectBuy(TextDrawingArea[] TDA) {
        RSInterface rsinterface = addTabInterface(53700);
        int x = 9;
        addHDSprite(53701, 642, 642);
        addHoverButton(53702, CLOSE_BUTTON, CLOSE_BUTTON, 16, 16, "Close", 0, 53703, 1);
        addHoveredButton(53703, CLOSE_BUTTON_HOVER, CLOSE_BUTTON_HOVER, 16, 16, 53704);
        addHoverButton(53758, 607, 607, 29, 23, "Back", 0, 53759, 1);
        addHoveredButton(53759, 608, 608, 29, 23, 53760);
        addText(53769, "Choose an item to exchange", TDA, 0, 0x96731A, false, true);
        addText(53770, "Select an item from your invertory to sell.", TDA, 0, 0x958E60, false, true);
        addText(53771, "0", TDA, 0, 0xB58338, true, true);
        addText(53772, "1 gp", TDA, 0, 0xB58338, true, true);
        addText(53773, "0 gp", TDA, 0, 0xB58338, true, true);
        addHoverButton(53793, 653, 653, 40, 36, "[GE]", 0, 53794, 1);
        addHoveredButton(53794, 652, 652, 40, 36, 53795);
        addHoverButton(53796, 653, 653, 40, 36, "[GE]", 0, 53797, 1);
        addHoveredButton(53797, 652, 652, 40, 36, 53798);
        addItemOnInterface(53780, 2901, new String[]{null});
        addItemOnInterface(53781, 2901, new String[]{"Collect"});
        addItemOnInterface(53782, 2902, new String[]{"Collect"});
        addText(53784, "", TDA, 0, 0xFFFF00, false, true);
        addText(53785, "", TDA, 0, 0xFFFF00, false, true);
        addText(53787, "N/A", TDA, 0, 0xB58338, false, true);
        addText(53788, "", TDA, 0, 0xFFFF00, true, true);
        addText(53789, "", TDA, 0, 0xFFFF00, true, true);
        addHoverButton(53800, 640, 640, 20, 20, "Abort offer", 0, 53801, 1);
        addHoveredButton(53801, 641, 641, 20, 20, 53802);
        rsinterface.totalChildren(24);
        rsinterface.child(0, 53701, 4 + x, 23);// 385, 260
        rsinterface.child(1, 53702, 464 + x, 33);// 435, 260
        rsinterface.child(2, 53703, 464 + x, 33);
        rsinterface.child(3, 53758, 19 + x, 284);
        rsinterface.child(4, 53759, 19 + x, 284);
        rsinterface.child(5, 53769, 202 + x, 71);
        rsinterface.child(6, 53770, 202 + x, 98);
        rsinterface.child(7, 53771, 142 + x, 185);
        rsinterface.child(8, 53772, 354 + x, 185);
        rsinterface.child(9, 53773, 252 + x, 246);
        rsinterface.child(10, 53793, 386 + x, 256 + 23);
        rsinterface.child(11, 53794, 386 + x, 256 + 23);
        rsinterface.child(12, 53796, 435 + x, 256 + 23);
        rsinterface.child(13, 53797, 435 + x, 256 + 23);
        rsinterface.child(14, 53780, 97 + x, 97);
        rsinterface.child(15, 53781, 385 + 4 + x, 260 + 23);
        rsinterface.child(16, 53782, 435 + 4 + x, 260 + 23);
        rsinterface.child(17, 53784, 385 + 4 + x, 260 + 23);
        rsinterface.child(18, 53785, 435 + 4 + x, 260 + 23);
        rsinterface.child(19, 53787, 108, 136);
        rsinterface.child(20, 53788, 214 + x, 249 + 23);
        rsinterface.child(21, 53789, 214 + x, 263 + 23);
        rsinterface.child(22, 53800, 345 + x, 250 + 23);
        rsinterface.child(23, 53801, 345 + x, 250 + 23);
    }

    public static void quickPrayers(TextDrawingArea[] TDA) {
        int i = 0;
        RSInterface localRSInterface = addTabInterface(20000);
        addHDSprite(17201, 604, 604);
        addText(17240, "Select your quick prayers:", TDA, 0, 16750623, false, true);
        addTransparentSprite(17249, 603, 50);
        int j = 17202;
        for (int k = 630; (j <= 17231) || (k <= 659); ++k) {
            addConfigButton(j, 17200, 414, 413, 14, 15, "Select", 0, 1, k);
            j++;
        }
        addHoverButton(17241, 605, 605, 190, 24, "Confirm Selection", -1, 17242, 1);
        addHoveredButton(17242, 606, 606, 190, 24, 17243);
        setChildren(58, localRSInterface);
        setBounds(25001, 5, 28, i++, localRSInterface);
        setBounds(25003, 44, 28, i++, localRSInterface);
        setBounds(25005, 79, 31, i++, localRSInterface);
        setBounds(25007, 116, 30, i++, localRSInterface);
        setBounds(25009, 153, 29, i++, localRSInterface);
        setBounds(25011, 5, 68, i++, localRSInterface);
        setBounds(25013, 44, 67, i++, localRSInterface);
        setBounds(25015, 79, 69, i++, localRSInterface);
        setBounds(25017, 116, 70, i++, localRSInterface);
        setBounds(25019, 154, 70, i++, localRSInterface);
        setBounds(25021, 4, 104, i++, localRSInterface);
        setBounds(25023, 44, 107, i++, localRSInterface);
        setBounds(25025, 81, 105, i++, localRSInterface);
        setBounds(25027, 117, 105, i++, localRSInterface);
        setBounds(25029, 156, 107, i++, localRSInterface);
        setBounds(25031, 5, 145, i++, localRSInterface);
        setBounds(25033, 43, 144, i++, localRSInterface);
        setBounds(25035, 83, 144, i++, localRSInterface);
        setBounds(25037, 115, 141, i++, localRSInterface);
        setBounds(25039, 154, 144, i++, localRSInterface);
        setBounds(25041, 5, 180, i++, localRSInterface);
        setBounds(25043, 41, 178, i++, localRSInterface);
        setBounds(25045, 79, 183, i++, localRSInterface);
        setBounds(25047, 116, 178, i++, localRSInterface);
        setBounds(25049, 161, 180, i++, localRSInterface);
        // setBounds(18015, 4, 210, i++, localRSInterface);
        setBounds(25051, 5, 217, i++, localRSInterface);
        // setBounds(18061, 78, 212, i++, localRSInterface);
        // setBounds(18121, 116, 208, i++, localRSInterface);
        setBounds(17249, 0, 25, i++, localRSInterface);
        setBounds(17201, 0, 22, i++, localRSInterface);
        setBounds(17201, 0, 237, i++, localRSInterface);
        setBounds(17202, 2, 25, i++, localRSInterface);
        setBounds(17203, 41, 25, i++, localRSInterface);
        setBounds(17204, 76, 25, i++, localRSInterface);
        setBounds(17205, 113, 25, i++, localRSInterface);
        setBounds(17206, 150, 25, i++, localRSInterface);
        setBounds(17207, 2, 65, i++, localRSInterface);
        setBounds(17208, 41, 65, i++, localRSInterface);
        setBounds(17209, 76, 65, i++, localRSInterface);
        setBounds(17210, 113, 65, i++, localRSInterface);
        setBounds(17211, 150, 65, i++, localRSInterface);
        setBounds(17212, 2, 102, i++, localRSInterface);
        setBounds(17213, 41, 102, i++, localRSInterface);
        setBounds(17214, 76, 102, i++, localRSInterface);
        setBounds(17215, 113, 102, i++, localRSInterface);
        setBounds(17216, 150, 102, i++, localRSInterface);
        setBounds(17217, 2, 141, i++, localRSInterface);
        setBounds(17218, 41, 141, i++, localRSInterface);
        setBounds(17219, 76, 141, i++, localRSInterface);
        setBounds(17220, 113, 141, i++, localRSInterface);
        setBounds(17221, 150, 141, i++, localRSInterface);
        setBounds(17222, 2, 177, i++, localRSInterface);
        setBounds(17223, 41, 177, i++, localRSInterface);
        setBounds(17224, 76, 177, i++, localRSInterface);
        setBounds(17225, 113, 177, i++, localRSInterface);
        setBounds(17226, 150, 177, i++, localRSInterface);
        setBounds(17227, 1, 211, i++, localRSInterface);
        setBounds(17240, 5, 5, i++, localRSInterface);
        setBounds(17241, 0, 237, i++, localRSInterface);
        setBounds(17242, 0, 237, i++, localRSInterface);
    }

    public static void quickCurses(TextDrawingArea[] TDA) {
        int frame = 0;
        RSInterface tab = addTabInterface(22000);
        addHDSprite(17201, 604, 604);
        addText(17235, "Select your quick curses:", TDA, 0, 16750623, false, true);
        addTransparentSprite(17249, 603, 50);
        int j = 17202;
        for (int k = 630; (j <= 17222) || (k <= 656); k++) {
            addConfigButton(j, 17200, 414, 413, 14, 15, "Select", 0, 1, k);
            j++;
        }
        setChildren(46, tab);
        setBounds(22504, 5, 8 + 17, frame++, tab);
        setBounds(22506, 44, 8 + 20, frame++, tab);
        setBounds(22508, 79, 11 + 19, frame++, tab);
        setBounds(22510, 116, 10 + 18, frame++, tab);
        setBounds(22512, 153, 9 + 20, frame++, tab);
        setBounds(22514, 5, 48 + 18, frame++, tab);
        setBounds(22516, 44, 47 + 21, frame++, tab);
        setBounds(22518, 79, 49 + 20, frame++, tab);
        setBounds(22520, 116, 50 + 19, frame++, tab);
        setBounds(22522, 154, 50 + 20, frame++, tab);
        setBounds(22524, 4, 84 + 21, frame++, tab);
        setBounds(22526, 44, 87 + 19, frame++, tab);
        setBounds(22528, 81, 85 + 20, frame++, tab);
        setBounds(22530, 117, 85 + 20, frame++, tab);
        setBounds(22532, 156, 87 + 18, frame++, tab);
        setBounds(22534, 5, 125 + 19, frame++, tab);
        setBounds(22536, 43, 124 + 19, frame++, tab);
        setBounds(22538, 83, 124 + 20, frame++, tab);
        setBounds(22540, 115, 125 + 21, frame++, tab);
        setBounds(22542, 154, 126 + 22, frame++, tab);
        setBounds(17249, 0, 25, frame++, tab);
        setBounds(17201, 0, 22, frame++, tab);
        setBounds(17201, 0, 237, frame++, tab);
        setBounds(17202, 2, 25, frame++, tab);
        setBounds(17203, 41, 25, frame++, tab);
        setBounds(17204, 76, 25, frame++, tab);
        setBounds(17205, 113, 25, frame++, tab);
        setBounds(17206, 150, 25, frame++, tab);
        setBounds(17207, 2, 65, frame++, tab);
        setBounds(17208, 41, 65, frame++, tab);
        setBounds(17209, 76, 65, frame++, tab);
        setBounds(17210, 113, 65, frame++, tab);
        setBounds(17211, 150, 65, frame++, tab);
        setBounds(17212, 2, 102, frame++, tab);
        setBounds(17213, 41, 102, frame++, tab);
        setBounds(17214, 76, 102, frame++, tab);
        setBounds(17215, 113, 102, frame++, tab);
        setBounds(17216, 150, 102, frame++, tab);
        setBounds(17217, 2, 141, frame++, tab);
        setBounds(17218, 41, 141, frame++, tab);
        setBounds(17219, 76, 141, frame++, tab);
        setBounds(17220, 113, 141, frame++, tab);
        setBounds(17221, 150, 141, frame++, tab);
        setBounds(17235, 5, 5, frame++, tab);
        setBounds(17241, 0, 237, frame++, tab);
        setBounds(17242, 0, 237, frame++, tab);
    }

    public static void addTransparentSprite(int id, int spriteId, String spriteName, int opacity) {
        RSInterface tab = interfaceCache[id] = new RSInterface();
        tab.id = id;
        tab.parentID = id;
        tab.type = 9;
        tab.atActionType = 0;
        tab.contentType = 0;
        tab.opacity = (byte) opacity;
        tab.customOpacity = opacity;
        tab.hoverType = 52;
        tab.disabledSprite = imageLoader(spriteId, spriteName);
        tab.enabledSprite = imageLoader(spriteId, spriteName);
        tab.width = 512;
        tab.height = 334;
        tab.drawsTransparent = true;
    }

    public static void addTransparentSprite(int id, int spriteId, String spriteName, int w, int h, int opacity) {
        RSInterface tab = interfaceCache[id] = new RSInterface();
        tab.id = id;
        tab.parentID = id;
        tab.type = 9;
        tab.atActionType = 0;
        tab.contentType = 0;
        tab.opacity = (byte) opacity;
        tab.customOpacity = opacity;
        tab.hoverType = 52;
        tab.disabledSprite = imageLoader(spriteId, spriteName);
        tab.enabledSprite = imageLoader(spriteId, spriteName);
        tab.width = w;
        tab.height = h;
        tab.drawsTransparent = true;
    }

    public static void addHoverSpriteLoaderButton(int i, int spriteId, int width, int height, String text, int contentType, int hoverOver, int aT) {// hoverable
        // button
        RSInterface tab = addTabInterface(i);
        tab.id = i;
        tab.parentID = i;
        tab.type = 5;
        tab.atActionType = aT;
        tab.contentType = contentType;
        tab.opacity = 0;
        tab.hoverType = hoverOver;
        tab.disabledSprite = SpriteLoader.sprites[spriteId];
        tab.enabledSprite = SpriteLoader.sprites[spriteId];
        tab.width = width;
        tab.height = height;
        tab.tooltip = text;
    }

    public static void addHoveredSpriteLoaderButton(int i, int w, int h, int IMAGEID, int spriteId) {
        RSInterface tab = addTabInterface(i);
        tab.parentID = i;
        tab.id = i;
        tab.type = 0;
        tab.atActionType = 0;
        tab.width = w;
        tab.height = h;
        tab.hidden = true;
        tab.opacity = 0;
        tab.hoverType = -1;
        tab.scrollMax = 0;
        tab.disabledSprite = SpriteLoader.sprites[spriteId];
        tab.enabledSprite = SpriteLoader.sprites[spriteId];
        tab.totalChildren(1);
        tab.child(0, IMAGEID, 0, 0);
    }

    public static void addTransparentSprite(int id, int spriteId, int opacity) {
        RSInterface tab = interfaceCache[id] = new RSInterface();
        tab.id = id;
        tab.parentID = id;
        tab.type = 9;
        tab.atActionType = 0;
        tab.contentType = 0;
        tab.opacity = (byte) opacity;
        tab.hoverType = 52;
        tab.disabledSpriteId = spriteId;
        tab.enabledSpriteId = spriteId;
        tab.width = 512;
        tab.height = 334;
        tab.drawsTransparent = true;
    }

    public static void addText(int i, String disabledText, String enabledText, int disabledColor, int enabledColor, boolean centered, boolean shadow, int hoverType, int fontId) {
        try {
            RSInterface rsinterface = addTabInterface(i);
            rsinterface.parentID = i;
            rsinterface.id = i;
            rsinterface.type = 4;
            rsinterface.atActionType = 0;
            rsinterface.width = 0;
            rsinterface.height = 0;
            rsinterface.contentType = 0;
            rsinterface.transparancy = 0;
            rsinterface.hoverType = hoverType;
            rsinterface.centerText = centered;
            rsinterface.shadowed = shadow;
            rsinterface.textDrawingAreas = RSInterface.fonts[fontId];
            rsinterface.message = disabledText;
            rsinterface.enabledMessage = enabledText;
            rsinterface.disabledColor = disabledColor;
            rsinterface.enabledColor = enabledColor;
        } catch (Exception e) {
        }
    }

    public static void addText(int i, String s, int k, boolean l, boolean m, int a, int j) {
        try {
            RSInterface rsinterface = addTabInterface(i);
            rsinterface.parentID = i;
            rsinterface.id = i;
            rsinterface.type = 4;
            rsinterface.atActionType = 0;
            rsinterface.width = 0;
            rsinterface.height = 0;
            rsinterface.contentType = 0;
            rsinterface.transparancy = 0;
            rsinterface.hoverType = a;
            rsinterface.centerText = l;
            rsinterface.shadowed = m;
            rsinterface.textDrawingAreas = RSInterface.fonts[j];
            rsinterface.message = s;
            rsinterface.disabledColor = k;
        } catch (Exception e) {
        }
    }

    private static Sprite loadSprite(int i, String s) {

        Sprite sprite;
        try {
            sprite = new Sprite(s + " " + i);
            if (sprite != null) {
                return sprite;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
        return sprite;
    }

    public static void addFamiliarHead(int interfaceID, int width, int height, int zoom) {
        RSInterface rsi = addTabInterface(interfaceID);
        rsi.type = 6;
        rsi.mediaType = 2;
        rsi.mediaID = 4000;
        rsi.modelZoom = zoom;
        rsi.modelRotation1 = 40;
        rsi.modelRotation2 = 1800;
        rsi.height = height;
        rsi.width = width;
    }

    public static void HotZoneInterface(TextDrawingArea[] TDA) {
        RSInterface RSinterface = addInterface(21300);
        addHDSprite(21301, 452, 452);
        addText(21305, "1", 0xFF9933, true, true, 52, TDA, 1);
        addText(21306, "2", 0xFF9933, true, true, 52, TDA, 1);
        addText(21307, "3", 0xFF9933, true, true, 52, TDA, 1);
        int last = 4;
        RSinterface.children = new int[last];
        RSinterface.childX = new int[last];
        RSinterface.childY = new int[last];
        setBounds(21301, 402, 285, 0, RSinterface);
        setBounds(21305, 462, 318, 1, RSinterface);
        setBounds(21306, 462, 303, 2, RSinterface);
        setBounds(21307, 462, 288, 3, RSinterface);
    }

    public static void NonHotZoneInterface(TextDrawingArea[] TDA) {
        RSInterface RSinterface = addInterface(21310);
        addHDSprite(21311, 453, 453);
        addText(21315, "1", 0xFF9933, true, true, 52, TDA, 1);
        addText(21316, "2", 0xFF9933, true, true, 52, TDA, 1);
        addText(21317, "3", 0xFF9933, true, true, 52, TDA, 1);
        int last = 4;
        RSinterface.children = new int[last];
        RSinterface.childX = new int[last];
        RSinterface.childY = new int[last];
        setBounds(21311, 402, 285, 0, RSinterface);
        setBounds(21315, 462, 318, 1, RSinterface);
        setBounds(21316, 462, 303, 2, RSinterface);
        setBounds(21317, 462, 288, 3, RSinterface);
    }

    public static void Buy(TextDrawingArea[] TDA) {
        RSInterface rsinterface = addTabInterface(24600);
        int x = 9;
        addHDSprite(24601, 609, 609);
        addHoverButton(24602, CLOSE_BUTTON, CLOSE_BUTTON, 16, 16, "Close", 0, 24603, 1);
        addHoveredButton(24603, CLOSE_BUTTON_HOVER, CLOSE_BUTTON_HOVER, 16, 16, 24604);
        addHoverButton(24606, 610, 610, 13, 13, "Decrease Quantity", 0, 24607, 1);
        addHoveredButton(24607, 611, 611, 13, 13, 24608);
        addHoverButton(24610, 612, 612, 13, 13, "Increase Quantity", 0, 24611, 1);
        addHoveredButton(24611, 613, 613, 13, 13, 24612);
        addHoverButton(24614, 614, 614, 35, 25, "Add 1", 0, 24615, 1);
        addHoveredButton(24615, 615, 615, 35, 25, 24616);
        addHoverButton(24618, 616, 616, 35, 25, "Add 10", 0, 24619, 1);
        addHoveredButton(24619, 617, 617, 35, 25, 24620);
        addHoverButton(24622, 618, 618, 35, 25, "Add 100", 0, 24623, 1);
        addHoveredButton(24623, 619, 619, 35, 25, 24624);
        addHoverButton(24626, 622, 622, 35, 25, "Add 1000", 0, 24627, 1);
        addHoveredButton(24627, 623, 623, 35, 25, 24628);
        addHoverButton(24630, 624, 624, 35, 25, "Edit Quantity", -1, 24631, 1);
        addHoveredButton(24631, 625, 625, 35, 25, 24632);
        addHoverButton(24634, 626, 626, 35, 25, "Decrease Price", 0, 24635, 1);
        addHoveredButton(24635, 627, 627, 35, 25, 24636);
        addHoverButton(24638, 628, 628, 35, 25, "Offer Guild Price", 0, 24639, 1);
        addHoveredButton(24639, 629, 629, 35, 25, 24640);
        addHoverButton(24642, 624, 624, 35, 25, "Edit Price", -1, 24643, 1);
        addHoveredButton(24643, 625, 625, 35, 25, 24644);
        addHoverButton(24646, 630, 630, 35, 25, "Increase Price", 0, 24647, 1);
        addHoveredButton(24647, 631, 631, 35, 25, 24648);
        addHoverButton(24650, 632, 632, 120, 43, "Confirm Offer", 0, 24651, 1);
        addHoveredButton(24651, 633, 633, 120, 43, 24652);

        addHoverButton(24654, 634, 634, 40, 36, "Search", 0, 24655, 1);
        addHoveredButton(24655, 635, 635, 40, 36, 24656);

        addHoverButton(24658, 607, 607, 29, 23, "Back", 0, 24659, 1);
        addHoveredButton(24659, 608, 608, 29, 23, 24660);
        addHoverButton(24662, 610, 610, 13, 13, "Decrease Price", 0, 24663, 1);
        addHoveredButton(24663, 611, 611, 13, 13, 24664);
        addHoverButton(24665, 612, 612, 13, 13, "Increase Price", 0, 24666, 1);
        addHoveredButton(24666, 613, 613, 13, 13, 24667);
        addText(24669, "Choose an item to exchange", TDA, 0, 0x96731A, false, true);
        addText(24670, "Click the icon to the left to search for items.", TDA, 0, 0x958E60, false, true);
        addText(24671, "0", TDA, 0, 0xB58338, true, true);
        addText(24672, "1 gp", TDA, 0, 0xB58338, true, true);
        addText(24673, "0 gp", TDA, 0, 0xB58338, true, true);
        //RSInterface add = addInterface(24680);

        addItemOnInterface(24680, 3323, new String[]{null});
        //addToItemGroup(add, 1, 1, 24, 24, true, "[GE]", "[GE]", "[GE]");


        addText(24682, "N/A", TDA, 0, 0xB58338, false, true);
        rsinterface.totalChildren(42);
        rsinterface.child(0, 24601, 4 + x, 23);
        rsinterface.child(1, 24602, 464 + x, 33);
        rsinterface.child(2, 24603, 464 + x, 33);
        rsinterface.child(3, 24606, 46 + x, 184);
        rsinterface.child(4, 24607, 46 + x, 184);
        rsinterface.child(5, 24610, 226 + x, 184);
        rsinterface.child(6, 24611, 226 + x, 184);
        rsinterface.child(7, 24614, 43 + x, 208);
        rsinterface.child(8, 24615, 43 + x, 208);
        rsinterface.child(9, 24618, 84 + x, 208);
        rsinterface.child(10, 24619, 84 + x, 208);
        rsinterface.child(11, 24622, 125 + x, 208);
        rsinterface.child(12, 24623, 125 + x, 208);
        rsinterface.child(13, 24626, 166 + x, 208);
        rsinterface.child(14, 24627, 166 + x, 208);
        rsinterface.child(15, 24630, 207 + x, 208);
        rsinterface.child(16, 24631, 207 + x, 208);
        rsinterface.child(17, 24634, 260 + x, 208);
        rsinterface.child(18, 24635, 260 + x, 208);
        rsinterface.child(19, 24638, 316 + x, 208);
        rsinterface.child(20, 24639, 316 + x, 208);
        rsinterface.child(21, 24642, 357 + x, 208);
        rsinterface.child(22, 24643, 357 + x, 208);
        rsinterface.child(23, 24646, 413 + x, 208);
        rsinterface.child(24, 24647, 413 + x, 208);
        rsinterface.child(25, 24650, 191 + x, 273);
        rsinterface.child(26, 24651, 191 + x, 273);
        rsinterface.child(27, 24654, 93 + x, 95);
        rsinterface.child(28, 24655, 93 + x, 95);
        rsinterface.child(29, 24658, 19 + x, 284);
        rsinterface.child(30, 24659, 19 + x, 284);
        rsinterface.child(31, 24662, 260 + x, 184);
        rsinterface.child(32, 24663, 260 + x, 184);
        rsinterface.child(33, 24665, 435 + x, 184);
        rsinterface.child(34, 24666, 435 + x, 184);
        rsinterface.child(35, 24669, 202 + x, 71);
        rsinterface.child(36, 24670, 202 + x, 98);
        rsinterface.child(37, 24671, 142 + x, 185);
        rsinterface.child(38, 24672, 354 + x, 185);
        rsinterface.child(39, 24673, 252 + x, 246);
        rsinterface.child(40, 24680, 97 + x, 97);
        rsinterface.child(41, 24682, 121, 136);
    }

    public static void addToItemGroup(RSInterface rsi, int w, int h, int x, int y, boolean actions, String action1, String action2, String action3) {
        rsi.width = w;
        rsi.height = h;
        rsi.inv = new int[w * h];
        rsi.invStackSizes = new int[w * h];
        rsi.usableItemInterface = false;
        rsi.isInventoryInterface = false;
        rsi.invSpritePadX = x;
        rsi.invSpritePadY = y;
        rsi.spritesX = new int[20];
        rsi.spritesY = new int[20];
        rsi.sprites = new Sprite[20];
        rsi.actions = new String[5];
        if (actions) {
            rsi.actions[0] = action1;
            rsi.actions[1] = action2;
            rsi.actions[2] = action3;
        }
        rsi.type = 2;
    }

    public static void Sell(TextDrawingArea[] TDA) {
        RSInterface rsinterface = addTabInterface(24700);
        int x = 9;
        addHDSprite(24701, 636, 636);
        addHoverButton(24702, CLOSE_BUTTON, CLOSE_BUTTON_HOVER, 16, 16, "Close", 0, 24703, 1);
        addHoveredButton(24703, CLOSE_BUTTON_HOVER, CLOSE_BUTTON_HOVER, 16, 16, 24704);
        addHoverButton(24706, 610, 610, 13, 13, "Decrease Quantity", 0, 24707, 1);
        addHoveredButton(24707, 611, 611, 13, 13, 24708);
        addHoverButton(24710, 612, 612, 13, 13, "Increase Quantity", 0, 24711, 1);
        addHoveredButton(24711, 613, 613, 13, 13, 24712);
        addHoverButton(24714, 614, 614, 35, 25, "Sell 1", 0, 24715, 1);
        addHoveredButton(24715, 615, 615, 35, 25, 24716);
        addHoverButton(24718, 616, 616, 35, 25, "Sell 10", 0, 24719, 1);
        addHoveredButton(24719, 617, 617, 35, 25, 24720);
        addHoverButton(24722, 618, 618, 35, 25, "Sell 100", 0, 24723, 1);
        addHoveredButton(24723, 619, 619, 35, 25, 24724);
        addHoverButton(24726, 620, 620, 35, 25, "Sell All", 0, 24727, 1);
        addHoveredButton(24727, 621, 621, 35, 25, 24728);
        addHoverButton(24730, 624, 624, 35, 25, "Edit Quantity", -1, 24731, 1);
        addHoveredButton(24731, 625, 625, 35, 25, 24732);
        addHoverButton(24734, 626, 626, 35, 25, "Decrease Price", 0, 24735, 1);
        addHoveredButton(24735, 627, 627, 35, 25, 24736);
        addHoverButton(24738, 628, 628, 35, 25, "Offer Guild Price", 0, 24739, 1);
        addHoveredButton(24739, 629, 629, 35, 25, 24740);
        addHoverButton(24742, 624, 624, 35, 25, "Edit Price", -1, 24743, 1);
        addHoveredButton(24743, 625, 625, 35, 25, 24744);
        addHoverButton(24746, 630, 630, 35, 25, "Increase Price", 0, 24747, 1);
        addHoveredButton(24747, 631, 631, 35, 25, 24748);
        addHoverButton(24750, 632, 632, 120, 43, "Confirm Offer", 0, 24751, 1);
        addHoveredButton(24751, 633, 633, 120, 43, 24752);
        addHoverButton(24758, 607, 607, 29, 23, "Back", 0, 24759, 1);
        addHoveredButton(24759, 608, 608, 29, 23, 24760);
        addHoverButton(24762, 610, 610, 13, 13, "Decrease Price", 0, 24763, 1);
        addHoveredButton(24763, 611, 611, 13, 13, 24764);
        addHoverButton(24765, 612, 612, 13, 13, "Increase Price", 0, 24766, 1);
        addHoveredButton(24766, 613, 613, 13, 13, 24767);
        addText(24769, "Choose an item to exchange", TDA, 0, 0x96731A, false, true);
        addText(24770, "Select an item from your inventory to sell.", TDA, 0, 0x958E60, false, true);
        addText(24771, "0", TDA, 0, 0xB58338, true, true);
        addText(24772, "1 gp", TDA, 0, 0xB58338, true, true);
        addText(24773, "0 gp", TDA, 0, 0xB58338, true, true);
        addItemOnInterface(24780, 3323, new String[]{null});
        addText(24782, "N/A", TDA, 0, 0xB58338, false, true);
        rsinterface.totalChildren(40);
        rsinterface.child(0, 24701, 4 + x, 23);
        rsinterface.child(1, 24702, 464 + x, 33);
        rsinterface.child(2, 24703, 464 + x, 33);
        rsinterface.child(3, 24706, 46 + x, 184);
        rsinterface.child(4, 24707, 46 + x, 184);
        rsinterface.child(5, 24710, 226 + x, 184);
        rsinterface.child(6, 24711, 226 + x, 184);
        rsinterface.child(7, 24714, 43 + x, 208);
        rsinterface.child(8, 24715, 43 + x, 208);
        rsinterface.child(9, 24718, 84 + x, 208);
        rsinterface.child(10, 24719, 84 + x, 208);
        rsinterface.child(11, 24722, 125 + x, 208);
        rsinterface.child(12, 24723, 125 + x, 208);
        rsinterface.child(13, 24726, 166 + x, 208);
        rsinterface.child(14, 24727, 166 + x, 208);
        rsinterface.child(15, 24730, 207 + x, 208);
        rsinterface.child(16, 24731, 207 + x, 208);
        rsinterface.child(17, 24734, 260 + x, 208);
        rsinterface.child(18, 24735, 260 + x, 208);
        rsinterface.child(19, 24738, 316 + x, 208);
        rsinterface.child(20, 24739, 316 + x, 208);
        rsinterface.child(21, 24742, 357 + x, 208);
        rsinterface.child(22, 24743, 357 + x, 208);
        rsinterface.child(23, 24746, 413 + x, 208);
        rsinterface.child(24, 24747, 413 + x, 208);
        rsinterface.child(25, 24750, 191 + x, 273);
        rsinterface.child(26, 24751, 191 + x, 273);
        rsinterface.child(27, 24758, 19 + x, 284);
        rsinterface.child(28, 24759, 19 + x, 284);
        rsinterface.child(29, 24762, 260 + x, 184);
        rsinterface.child(30, 24763, 260 + x, 184);
        rsinterface.child(31, 24765, 435 + x, 184);
        rsinterface.child(32, 24766, 435 + x, 184);
        rsinterface.child(33, 24769, 202 + x, 71);
        rsinterface.child(34, 24770, 202 + x, 98);
        rsinterface.child(35, 24771, 142 + x, 185);
        rsinterface.child(36, 24772, 354 + x, 185);
        rsinterface.child(37, 24773, 252 + x, 246);
        rsinterface.child(38, 24780, 97 + x, 97);
        rsinterface.child(39, 24782, 121, 136);
    }

    public static void BuyandSell(TextDrawingArea[] TDA) {
        RSInterface Interface = addTabInterface(24500);
        setChildren(51, Interface);
        addHoverButton(24541, "b", 3, 138, 108, "Abort offer", 0, 24542, 1);
        addHoverButton(24543, "b", 3, 138, 108, "View offer", 0, 24544, 1);
        addHoverButton(24545, "b", 3, 138, 108, "Abort offer", 0, 24546, 1);
        addHoverButton(24547, "b", 3, 138, 108, "View offer", 0, 24548, 1);
        addHoverButton(24549, "b", 3, 138, 108, "Abort offer", 0, 24550, 1);
        addHoverButton(24551, "b", 3, 138, 108, "View offer", 0, 24552, 1);
        addHoverButton(24553, "b", 3, 138, 108, "Abort offer", 0, 24554, 1);
        addHoverButton(24555, "b", 3, 138, 108, "View offer", 0, 24556, 1);
        addHoverButton(24557, "b", 3, 138, 108, "Abort offer", 0, 24558, 1);
        addHoverButton(24559, "b", 3, 138, 108, "View offer", 0, 24560, 1);
        addHoverButton(24561, "b", 3, 138, 108, "Abort offer", 0, 24562, 1);
        addHoverButton(24563, "b", 3, 138, 108, "View offer", 0, 24564, 1);
        addSprite(1, 24579, 1, "b", false);
        addSprite(1, 24580, 1, "b", false);
        addSprite(1, 24581, 1, "b", false);
        addSprite(1, 24582, 1, "b", false);
        addSprite(1, 24583, 1, "b", false);
        addSprite(1, 24584, 1, "b", false);
        addHDSprite(24501, 658, 658);
        addHoverButton(24502, CLOSE_BUTTON, CLOSE_BUTTON, 21, 21, "Close", 250, 24503, 3);
        addHoveredButton(24503, CLOSE_BUTTON_HOVER, CLOSE_BUTTON_HOVER, 21, 21, 24504);
        addHoverButton(24505, 659, 659, 50, 50, "Buy", 0, 24506, 1);
        addHoveredButton(24506, 661, 661, 50, 50, 24507);
        addHoverButton(24508, 659, 659, 50, 50, "Buy", 0, 24509, 1);
        addHoveredButton(24509, 661, 661, 50, 50, 24510);
        addHoverButton(24514, 659, 659, 50, 50, "Buy", 0, 24515, 1);
        addHoveredButton(24515, 661, 661, 50, 50, 24516);
        addHoverButton(24517, 659, 659, 50, 50, "Buy", 0, 24518, 1);
        addHoveredButton(24518, 661, 661, 50, 50, 24519);
        addHoverButton(24520, 659, 659, 50, 50, "Buy", 0, 24521, 1);
        addHoveredButton(24521, 661, 661, 50, 50, 24522);
        addHoverButton(24523, 659, 659, 50, 50, "Buy", 0, 24524, 1);
        addHoveredButton(24524, 661, 661, 50, 50, 24525);
        addHoverButton(24511, 660, 660, 50, 50, "Sell", 0, 24512, 1);
        addHoveredButton(24512, 662, 662, 50, 50, 24513);
        addHoverButton(24526, 660, 660, 50, 50, "Sell", 0, 24527, 1);
        addHoveredButton(24527, 662, 662, 50, 50, 24528);
        addHoverButton(24529, 660, 660, 50, 50, "Sell", 0, 24530, 1);
        addHoveredButton(24530, 662, 662, 50, 50, 24531);
        addHoverButton(24532, 660, 660, 50, 50, "Sell", 0, 24533, 1);
        addHoveredButton(24533, 662, 662, 50, 50, 24534);
        addHoverButton(24535, 660, 660, 50, 50, "Sell", 0, 24536, 1);
        addHoveredButton(24536, 662, 662, 50, 50, 24537);
        addHoverButton(24538, 660, 660, 50, 50, "Sell", 0, 24539, 1);
        addHoveredButton(24539, 662, 662, 50, 50, 24540);

        RSInterface add = addInterface(24567);
        addToItemGroup(add, 1, 1, 24, 24, true, "[GE]", "[GE]", "[GE]");
        add = addInterface(24569);
        addToItemGroup(add, 1, 1, 24, 24, true, "[GE]", "[GE]", "[GE]");
        add = addInterface(24571);
        addToItemGroup(add, 1, 1, 24, 24, true, "[GE]", "[GE]", "[GE]");
        add = addInterface(24573);
        addToItemGroup(add, 1, 1, 24, 24, true, "[GE]", "[GE]", "[GE]");
        add = addInterface(24575);
        addToItemGroup(add, 1, 1, 24, 24, true, "[GE]", "[GE]", "[GE]");
        add = addInterface(24577);
        addToItemGroup(add, 1, 1, 24, 24, true, "[GE]", "[GE]", "[GE]");

        setBounds(24541, 30, 74, 0, Interface);
        setBounds(24543, 30, 74, 1, Interface);
        setBounds(24545, 186, 74, 2, Interface);
        setBounds(24547, 186, 74, 3, Interface);
        setBounds(24549, 342, 74, 4, Interface);
        setBounds(24551, 342, 74, 5, Interface);
        setBounds(24553, 30, 194, 6, Interface);
        setBounds(24555, 30, 194, 7, Interface);
        setBounds(24557, 186, 194, 8, Interface);
        setBounds(24559, 186, 194, 9, Interface);
        setBounds(24561, 339, 194, 10, Interface);
        setBounds(24563, 339, 194, 11, Interface);
        setBounds(24501, 4, 23, 12, Interface);
        setBounds(24579, 30 + 6, 74 + 30, 13, Interface);
        setBounds(24580, 186 + 6, 74 + 30, 14, Interface);
        setBounds(24581, 342 + 6, 74 + 30, 15, Interface);
        setBounds(24582, 30 + 6, 194 + 30, 16, Interface);
        setBounds(24583, 186 + 6, 194 + 30, 17, Interface);
        setBounds(24584, 342 + 6, 194 + 30, 18, Interface);
        setBounds(24502, 480, 32, 19, Interface);
        setBounds(24503, 480, 32, 20, Interface);
        setBounds(24505, 45, 115, 21, Interface);
        setBounds(24506, 45, 106, 22, Interface);
        setBounds(24508, 45, 234, 23, Interface);
        setBounds(24509, 45, 225, 24, Interface);
        setBounds(24511, 105, 115, 25, Interface);
        setBounds(24512, 105, 106, 26, Interface);
        setBounds(24514, 358, 115, 27, Interface);
        setBounds(24515, 358, 106, 28, Interface);
        setBounds(24517, 202, 234, 29, Interface);
        setBounds(24518, 202, 225, 30, Interface);
        setBounds(24520, 358, 234, 31, Interface);
        setBounds(24521, 358, 225, 32, Interface);
        setBounds(24523, 202, 115, 33, Interface);
        setBounds(24524, 202, 106, 34, Interface);
        setBounds(24526, 261, 115, 35, Interface);
        setBounds(24527, 261, 106, 36, Interface);
        setBounds(24529, 417, 115, 37, Interface);
        setBounds(24530, 417, 106, 38, Interface);
        setBounds(24532, 105, 234, 39, Interface);
        setBounds(24533, 105, 225, 40, Interface);
        setBounds(24535, 261, 234, 41, Interface);
        setBounds(24536, 261, 225, 42, Interface);
        setBounds(24538, 417, 234, 43, Interface);
        setBounds(24539, 417, 225, 44, Interface);

        setBounds(24567, 39, 106, 45, Interface);
        setBounds(24569, 46 + 156 - 7, 114 - 7, 46, Interface);
        setBounds(24571, 46 + 156 + 156 - 7, 114 - 7, 47, Interface);
        setBounds(24573, 39, 234 - 7, 48, Interface);
        setBounds(24575, 46 + 156 - 7, 234 - 7, 49, Interface);
        setBounds(24577, 46 + 156 + 156 - 7, 234 - 7, 50, Interface);

    }

    public static void addSprite(int a, int id, int spriteId, String spriteName, boolean l) {
        RSInterface tab = interfaceCache[id] = new RSInterface();
        tab.id = id;
        tab.parentID = id;
        tab.type = 5;
        tab.atActionType = 0;
        tab.contentType = 0;
        tab.opacity = (byte) 0;
        tab.hoverType = 52;
        tab.disabledSprite = imageLoader(spriteId, spriteName);
        tab.enabledSprite = imageLoader(spriteId, spriteName);
        tab.width = 512;
        tab.height = 334;
    }

    public static void beastOfBurden(TextDrawingArea[] wid) {
        RSInterface familiarInventory = addTabInterface(24000).totalChildrenReturn(4).childReturn(0, 24001, 104 - 25, 16);
        familiarInventory.addHDSprite(24001, 675, 675);
        familiarInventory.childReturn(1, 24002, 95, 59).addInventoryItemGroup(24002, 5, 6);
        familiarInventory.childReturn(2, 24003, 216 - 25, 25).addText(24003, "Familiar's Inventory", wid, 2, 0xFF8C00, false, true);
        familiarInventory.childReturn(3, 24004, 445 - 25, 27).addButton(24004, CLOSE_BUTTON, CLOSE_BUTTON, 16, 16, "Close", 5);
    }

    public static void beastOfBurden2(TextDrawingArea[] wid) {
        RSInterface familiarInventory = addTabInterface(24005).totalChildrenReturn(1).childReturn(0, 24006, 0, 0);
        familiarInventory.addInventoryItemGroup2(24006, 7, 4);
    }

    public static void addInventoryItemGroup(int id, int h, int w) {
        RSInterface Tab = interfaceCache[id] = new RSInterface();
        Tab.inv = new int[w * h];
        Tab.invStackSizes = new int[w * h];
        for (int i1 = 0; i1 < w * h; i1++) {
            Tab.invStackSizes[i1] = 0; // inv item stack size
            Tab.inv[i1] = 0; // inv item ids
        }
        Tab.spritesY = new int[30];
        Tab.spritesX = new int[30];
        int[] rowX = {0, 22, 44, 66, 88, 110, 0, 22, 44, 66, 88, 110, 0, 22, 44, 66, 88, 110, 0, 22, 44, 66, 88, 110, 0, 22, 44, 66, 88, 110};
        int[] rowY = {0, 0, 0, 0, 0, 0, 22, 22, 22, 22, 22, 22, 44, 44, 44, 44, 44, 44, 66, 66, 66, 66, 66, 66, 88, 88, 88, 88, 88, 88};
        for (int i2 = 0; i2 < 30; i2++) {
            Tab.spritesY[i2] = rowY[i2];
            Tab.spritesX[i2] = rowX[i2];
        }
        Tab.actions = new String[]{"Withdraw 1", "Withdraw 5", "Withdraw 10", "Withdraw All", null};
        Tab.width = w;
        Tab.hoverType = -1;
        Tab.parentID = id;
        Tab.id = id;
        Tab.scrollMax = 0;
        Tab.type = 2;
        Tab.height = h;
    }

    public static void addInventoryItemGroup2(int id, int h, int w) {
        RSInterface Tab = interfaceCache[id] = new RSInterface();
        Tab.inv = new int[w * h];
        Tab.invStackSizes = new int[w * h];
        for (int i1 = 0; i1 < w * h; i1++) {
            Tab.invStackSizes[i1] = 0; // inv item stack size
            Tab.inv[i1] = 0; // inv item ids
        }
        Tab.spritesY = new int[28];
        Tab.spritesX = new int[28];
        for (int i2 = 0; i2 < 28; i2++) {
            Tab.spritesY[i2] = 8;
            Tab.spritesX[i2] = 16;
        }
        Tab.invSpritePadX = 7;
        Tab.invSpritePadY = 4;
        Tab.actions = new String[]{"Store 1", "Store 5", "Store 10", "Store All", null};
        Tab.width = w;
        Tab.hoverType = -1;
        Tab.parentID = id;
        Tab.id = id;
        Tab.scrollMax = 0;
        Tab.type = 2;
        Tab.height = h;
    }

    //skill hovers

    public static void skillTabInterface2() {
        RSInterface skill = addTabInterface(3917);
        addSpriteLoader(28100, 753);
        skill.totalChildren(1);
        skill.child(0, 28100, 72, 237);
        int[] logoutID = {2450, 2451, 2452};
        int[] logoutID2 = {2458};
        for (int i : logoutID) {
            RSInterface Logout = interfaceCache[i];
            Logout.disabledColor = 0xFF981F;
            Logout.contentType = 0;
        }
        for (int i : logoutID2) {
            RSInterface Logout = interfaceCache[i];
            Logout.contentType = 0;
        }
        int[] buttons = {8654, 8655, 8656, 8657, 8658, 8659, 8660, 8861, 8662, 8663, 8664, 8665, 8666, 8667, 8668, 8669, 8670, 8671, 8672, 12162, 13928, 28177, 28178, 28179, 28180};
        int[] hovers = {4040, 4076, 4112, 4046, 4082, 4118, 4052, 4088, 4124, 4058, 4094, 4130, 4064, 4100, 4136, 4070, 4106, 4142, 4160, 2832, 13917, 28173, 28174, 28175, 28176};
        int[][] text = {{4004, 4005}, {4016, 4017}, {4028, 4029}, {4006, 4007}, {4018, 4019}, {4030, 4031}, {4008, 4009}, {4020, 4021}, {4032, 4033}, {4010, 4011}, {4022, 4023}, {4034, 4035}, {4012, 4013}, {4024, 4025}, {4036, 4037}, {4014, 4015}, {4026, 4027}, {4038, 4039}, {4152, 4153}, {12166, 12167}, {13926, 13927}, {28165, 28169}, {28166, 28170}, {28167, 28171}, {28168, 28172}};

        int[] icons = {3965, 3966, 3967, 3968, 3969, 3970, 3971, 3972, 3973, 3974, 3975, 3976, 3977, 3978, 3979, 3980, 3981, 3982, 4151, 12165, 13925, 28181, 28182, 28183, 28184};

        int[][] buttonCoords = {{3, 5}, {65, 5}, {127, 5}, {3, 33}, {65, 33}, {127, 33}, {3, 61}, {65, 61}, {127, 61}, {3, 89}, {65, 89}, {127, 89}, {3, 117}, {65, 117}, {127, 117}, {3, 145}, {65, 145}, {127, 145}, {3, 173}, {65, 173}, {127, 173}, {3, 201}, {65, 201}, {127, 201}, {3, 229}};
        int[][] iconCoords = {{5, 7}, {68, 8}, {130, 7}, {8, 35}, {67, 34}, {130, 37}, {8, 65}, {66, 64}, {130, 62}, {6, 92}, {67, 97}, {132, 91}, {5, 119}, {69, 121}, {129, 119}, {5, 148}, {68, 147}, {131, 147}, {5, 174}, {68, 174}, {129, 175}, {5, 203}, {68, 202}, {130, 203}, {5, 231}};
        int[][] textCoords = {{29, 7, 44, 19}, {91, 7, 106, 19}, {153, 7, 168, 19}, {29, 35, 44, 47}, {91, 35, 106, 47}, {153, 35, 168, 47}, {29, 63, 44, 75}, {91, 63, 106, 75}, {153, 63, 168, 75}, {29, 91, 44, 103}, {91, 91, 106, 103}, {153, 91, 168, 103}, {29, 119, 44, 131}, {91, 119, 106, 131}, {153, 119, 168, 131}, {29, 147, 44, 159}, {91, 147, 106, 159}, {153, 147, 168, 159}, {29, 175, 44, 187}, {91, 175, 106, 187}, {153, 175, 168, 187}, {29, 203, 44, 215}, {91, 203, 106, 215}, {153, 203, 168, 215}, {29, 231, 44, 243}};
        int[][] newText = {{28165, 28166, 28167, 28168}, {28169, 28170, 28171, 28172}};
        int[] spriteIds = {755, 766, 769, 775, 754, 774, 759, 765, 763, 771, 777, 757, 770, 758, 762, 768, 764, 778, 772, 773, 761, 756, 767, 776, 760};
        int frame = 0;

        for (int i = 0; i < hovers.length; i++) {
            addSkillButton(buttons[i], Skills.SKILL_NAMES[i]);
            createSkillHover(hovers[i], 205 + i);

            addHoverButtonWSpriteLoader(79924 + i, 752, 60, 27, "Set Level Goal", 1321, -1, 1);
            addHoverButtonWSpriteLoader(79949 + i, 752, 60, 27, "Set Exp Goal", 1322, -1, 1);
            addHoverButtonWSpriteLoader(79974 + i, 752, 60, 27, "Clear Goal", 1323, -1, 1);
            addHoverButtonWSpriteLoader(80000 + i, 752, 60, 27, "Reset", 5000 + i, -1, 1);
			/*
			addHoverButton(79924 + i, getSprite("Interfaces/Skilltab/Button"), 0, 60, 27, "Set Level Goal", 1321, -1, 1);
			addHoverButton(79949 + i, getSprite("Interfaces/Skilltab/Button"), 0, 60, 27, "Set Exp Goal", 1322, -1, 1);
			addHoverButton(79974 + i, getSprite("Interfaces/Skilltab/Button"), 0, 60, 27, "Clear Goal", 1323, -1, 1);
			addHoverButton(80000 + i, getSprite("Interfaces/Skilltab/Button"), 0, 60, 27, "Prestige", 5000+i, -1, 1);


			addSprite(icons[i], getSprite("Interfaces/Skilltab/" + spriteNames[i]));
			 */
            addSpriteLoader(icons[i], spriteIds[i]);
        }


        //new skill display error
        for (int i = 0; i < 4; i++) {
            addSkillText(newText[0][i], false, i + 21);
            addSkillText(newText[1][i], true, i + 21);
        }
        skill.children(icons.length + (text.length * 2) + hovers.length + buttons.length * 5 + 1);


        RSInterface totalLevel = addInterface(3984);
        addSpriteLoader(31196, 269);
        createHover(31192, 231, 120);
        addText(31199, "Total Level:", 0xFFEE33, false, true, 52, fonts, 0);
        addText(31200, "2475", 0xFFEE33, false, true, 52, fonts, 0);

        totalLevel.totalChildren(4);
        totalLevel.child(0, 31196, 65, 229);
        totalLevel.child(1, 31199, 106, 231);
        totalLevel.child(2, 31200, 117, 243);
        totalLevel.child(3, 31192, 38, 230);
        skill.child(frame, 3984, 0, 0);
        frame++;
        for (int i = 0; i < buttons.length; i++) {
            skill.child(frame++, 80000 + i, buttonCoords[i][0], buttonCoords[i][1]);
            skill.child(frame++, 79974 + i, buttonCoords[i][0], buttonCoords[i][1]);
            skill.child(frame++, 79949 + i, buttonCoords[i][0], buttonCoords[i][1]);
            skill.child(frame++, 79924 + i, buttonCoords[i][0], buttonCoords[i][1]);
            skill.child(frame, buttons[i], buttonCoords[i][0], buttonCoords[i][1]);
            frame++;
        }
        for (int i = 0; i < icons.length; i++) {
            skill.child(frame, icons[i], iconCoords[i][0], iconCoords[i][1]);
            frame++;
        }
        for (int i = 0; i < text.length; i++) {
            skill.child(frame, text[i][0], textCoords[i][0], textCoords[i][1]);
            frame++;
        }
        for (int i = 0; i < text.length; i++) {
            skill.child(frame, text[i][1], textCoords[i][2], textCoords[i][3]);
            frame++;
        }
        for (int i = 0; i < hovers.length; i++) {
            skill.child(frame, hovers[i], buttonCoords[i][0], buttonCoords[i][1]);
            frame++;
        }
    }

    public static void trainingTeleports(TextDrawingArea[] tda) {
        RSInterface rsInterface = addInterface(60600);
        addSprite(60601, 0, "Teleporting/Background");
        addHoverButton(60602, "Teleporting/Tab", 0, 114, 35, "Select", 0, 60603, 1);
        addHoveredButton(60603, "Teleporting/Tab", 1, 114, 35, 60604);
        addHoverButton(60605, "Teleporting/Tab", 0, 114, 35, "Select", 0, 60606, 1);
        addHoveredButton(60606, "Teleporting/Tab", 1, 114, 35, 60607);
        addHoverButton(60608, "Teleporting/Tab", 0, 114, 35, "Select", 0, 60609, 1);
        addHoveredButton(60609, "Teleporting/Tab", 1, 114, 35, 60610);
        addHoverButton(60611, "Teleporting/Tab", 0, 114, 35, "Select", 0, 60612, 1);
        addHoveredButton(60612, "Teleporting/Tab", 1, 114, 35, 60613);
        addHoverButton(60614, "Teleporting/Tab", 0, 114, 35, "Select", 0, 60615, 1);
        addHoveredButton(60615, "Teleporting/Tab", 1, 114, 35, 60616);
        addHoverButton(60617, "Teleporting/Tab", 0, 114, 35, "Select", 0, 60618, 1);
        addHoveredButton(60618, "Teleporting/Tab", 1, 114, 35, 60619);
        addHoverButton(60622, "Teleporting/Button", 0, 90, 32, "Teleport", 0, 60623, 1);
        addHoveredButton(60623, "Teleporting/Button", 1, 90, 32, 60624);
        addHoverButton(60625, "Teleporting/Button", 0, 90, 32, "Teleport", 0, 60626, 1);
        addHoveredButton(60626, "Teleporting/Button", 1, 90, 32, 60627);
        addHoverButton(60628, "Teleporting/Button", 0, 90, 32, "Teleport", 0, 60629, 1);
        addHoveredButton(60629, "Teleporting/Button", 1, 90, 32, 60630);
        addHoverButton(60631, "Teleporting/Button", 0, 90, 32, "Teleport", 0, 60632, 1);
        addHoveredButton(60632, "Teleporting/Button", 1, 90, 32, 60633);
        addHoverButton(60634, "Teleporting/Button", 0, 90, 32, "Teleport", 0, 60635, 1);
        addHoveredButton(60635, "Teleporting/Button", 1, 90, 32, 60636);
        addHoverButton(60637, "Teleporting/Button", 0, 90, 32, "Teleport", 0, 60638, 1);
        addHoveredButton(60638, "Teleporting/Button", 1, 90, 32, 60639);
        addHoverButton(60640, "Teleporting/Button", 0, 90, 32, "Teleport", 0, 60641, 1);
        addHoveredButton(60641, "Teleporting/Button", 1, 90, 32, 60642);
        addHoverButton(60643, "Teleporting/Button", 0, 90, 32, "Teleport", 0, 60644, 1);
        addHoveredButton(60644, "Teleporting/Button", 1, 90, 32, 60645);
        addHoverButton(60646, "Teleporting/Button", 0, 90, 32, "Teleport", 0, 60647, 1);
        addHoveredButton(60647, "Teleporting/Button", 1, 90, 32, 60648);
        //extra buttons
        addHoverButton(60691, "Teleporting/Button", 0, 90, 32, "Teleport", 0, 60692, 1);
        addHoveredButton(60692, "Teleporting/Button", 1, 90, 32, 60693);
        addHoverButton(60694, "Teleporting/Button", 0, 90, 32, "Teleport", 0, 60695, 1);
        addHoveredButton(60695, "Teleporting/Button", 1, 90, 32, 60696);
        addHoverButton(60697, "Teleporting/Button", 0, 90, 32, "Teleport", 0, 60698, 1);
        addHoveredButton(60698, "Teleporting/Button", 1, 90, 32, 60699);

        addHoverButton(60652, "Teleporting/Close", 0, 16, 16, "Close", 0, 60653, 3);
        addHoveredButton(60653, "Teleporting/Close", 1, 16, 16, 60654);


        addText(60656, "Cities", tda, 1, 0xF7FE2E, true, true);
        addText(60657, "Training", tda, 1, 0xF7FE2E, true, true);
        addText(60658, "Dungeons", tda, 1, 0xF7FE2E, true, true);
        addText(60659, "Bosses", tda, 1, 0xF7FE2E, true, true);
        addText(60660, "Minigames", tda, 1, 0xF7FE2E, true, true);
        addText(60661, "Raids", tda, 1, 0xF7FE2E, true, true);

        addText(60662, "", tda, 1, 0xff981f, true, true);
        addText(60663, "", tda, 1, 0xff981f, true, true);
        addText(60664, "", tda, 1, 0xff981f, true, true);
        addText(60665, "", tda, 1, 0xff981f, true, true);
        addText(60666, "", tda, 1, 0xff981f, true, true);
        addText(60667, "", tda, 1, 0xff981f, true, true);
        addText(60668, "", tda, 1, 0xff981f, true, true);
        addText(60669, "", tda, 1, 0xff981f, true, true);
        addText(60670, "", tda, 1, 0xff981f, true, true);
        addText(60671, "", tda, 1, 0xff981f, true, true);
        addText(60672, "", tda, 1, 0xff981f, true, true);
        addText(60673, "", tda, 1, 0xff981f, true, true);
        addText(60674, "", tda, 1, 0xff981f, true, true);
        addText(60675, "", tda, 1, 0xff981f, true, true);
        addText(60676, "", tda, 1, 0xff981f, true, true);
        addText(60677, "", tda, 1, 0xff981f, true, true);
        addText(60678, "", tda, 1, 0xff981f, true, true);
        addText(60679, "", tda, 1, 0xff981f, true, true);
        //new teleport texts
        addText(18374, "New ", tda, 1, 0xff981f, true, true);
        addText(60701, "Teleport", tda, 1, 0xff981f, true, true);
        addText(60702, "New", tda, 1, 0xff981f, true, true);
        addText(60703, "Teleport", tda, 1, 0xff981f, true, true);
        addText(60704, "New", tda, 1, 0xff981f, true, true);
        addText(60705, "Teleport", tda, 1, 0xff981f, true, true);

        //addText(60680, "", tda, 1, 0xff981f, true, true);
        //addText(60681, "", tda, 1, 0xff981f, true, true);
        addText(60690, " ", tda, 2, 0xff981f, true, true);
        setChildren(70, rsInterface);
        rsInterface.child(0, 60601, 6, 14); // Background
        rsInterface.child(1, 60602, 19, 41); // Tab 1 Hover
        rsInterface.child(2, 60603, 19, 41); // Tab 1
        rsInterface.child(3, 60605, 19, 76); // Tab 2 Hover
        rsInterface.child(4, 60606, 19, 76); // Tab 2
        rsInterface.child(5, 60608, 19, 111); // Tab 3 Hover
        rsInterface.child(6, 60609, 19, 111); // Tab 3
        rsInterface.child(7, 60611, 19, 146); // Tab 4 Hover
        rsInterface.child(8, 60612, 19, 146); // Tab 4
        rsInterface.child(9, 60614, 19, 181); // Tab 5 Hover
        rsInterface.child(10, 60615, 19, 181); // Tab 5
        rsInterface.child(11, 60617, 19, 216); // Tab 6 Hover
        rsInterface.child(12, 60618, 19, 216); // Tab 6
        rsInterface.child(13, 60622, 153, 60); // Button 1 Hover
        rsInterface.child(14, 60623, 153, 60); // Button 1
        rsInterface.child(15, 60625, 267, 60); // Button 2 Hover
        rsInterface.child(16, 60626, 267, 60); // Button 2
        rsInterface.child(17, 60628, 383, 60); // Button 3 Hover
        rsInterface.child(18, 60629, 383, 60); // Button 3
        rsInterface.child(19, 60631, 153, 108); // Button 4 Hover
        rsInterface.child(20, 60632, 153, 108); // Button 4
        rsInterface.child(21, 60634, 267, 108); // Button 5 Hover
        rsInterface.child(22, 60635, 267, 108); // Button 5
        rsInterface.child(23, 60637, 383, 108); // Button 6 Hover
        rsInterface.child(24, 60638, 383, 108); // Button 6
        rsInterface.child(25, 60640, 153, 156); // Button 7 Hover
        rsInterface.child(26, 60641, 153, 156); // Button 7
        rsInterface.child(27, 60643, 267, 156); // Button 8 Hover
        rsInterface.child(28, 60644, 267, 156); // Button 8
        rsInterface.child(29, 60646, 383, 156); // Button 9 Hover
        rsInterface.child(30, 60647, 383, 156); // Button 9

        rsInterface.child(31, 60652, 484, 17); // Close Hover
        rsInterface.child(32, 60653, 484, 17); // Close

        rsInterface.child(33, 60656, 75, 50); // Title 1
        rsInterface.child(34, 60657, 75, 85); // Title 2
        rsInterface.child(35, 60658, 75, 120); // Title 3
        rsInterface.child(36, 60659, 75, 155); // Title 4
        rsInterface.child(37, 60660, 75, 190); // Title 5
        rsInterface.child(38, 60661, 75, 225); // Title 6
        rsInterface.child(39, 60662, 198, 61); // Teleport Text 1
        rsInterface.child(40, 60663, 198, 75); // Teleport Text 1
        rsInterface.child(41, 60664, 313, 61); // Teleport Text 2
        rsInterface.child(42, 60665, 313, 75); // Teleport Text 2
        rsInterface.child(43, 60666, 429, 61); // Teleport Text 3
        rsInterface.child(44, 60667, 429, 75); // Teleport Text 3
        rsInterface.child(45, 60668, 198, 109); // Teleport Text 4
        rsInterface.child(46, 60669, 198, 122); // Teleport Text 4
        rsInterface.child(47, 60670, 313, 109); // Teleport Text 5
        rsInterface.child(48, 60671, 313, 123); // Teleport Text 5
        rsInterface.child(49, 60672, 429, 109); // Teleport Text 6
        rsInterface.child(50, 60673, 429, 123); // Teleport Text 6
        rsInterface.child(51, 60674, 198, 157); // Teleport Text 7
        rsInterface.child(52, 60675, 198, 171); // Teleport Text 7
        rsInterface.child(53, 60676, 313, 157); // Teleport Text 8
        rsInterface.child(54, 60677, 313, 171); // Teleport Text 8
        rsInterface.child(55, 60678, 429, 157); // Teleport Text 9
        rsInterface.child(56, 60679, 429, 171); // Teleport Text 9
        //rsInterface.child(68, 60680, 82, 233); // Teleport Text 10
        //rsInterface.child(69, 60681, 82, 247); // Teleport Text 10
        rsInterface.child(57, 60690, 258, 18); // Title
        rsInterface.child(58, 60691, 153, 204); // Button 10 Hover
        rsInterface.child(59, 60692, 153, 204); // Button 10
        rsInterface.child(60, 60694, 267, 204); // Button 11 Hover
        rsInterface.child(61, 60695, 267, 204); // Button 11
        rsInterface.child(62, 60697, 383, 204); // Button 12 Hover
        rsInterface.child(63, 60698, 383, 204); // Button 12
        rsInterface.child(64, 18374, 198, 205); // Teleport Text 10
        rsInterface.child(65, 60701, 198, 219); // Teleport Text 10
        rsInterface.child(66, 60702, 313, 205); // Teleport Text 11
        rsInterface.child(67, 60703, 313, 219); // Teleport Text 11
        rsInterface.child(68, 60704, 429, 205); // Teleport Text 12
        rsInterface.child(69, 60705, 429, 219); // Teleport Text 12
    }

    public static void minigameTeleports(TextDrawingArea[] tda) {
        RSInterface rsInterface = addInterface(60700); // sec

        setChildren(70, rsInterface);
        rsInterface.child(0, 60601, 6, 14); // Background
        rsInterface.child(1, 60602, 19, 41); // Tab 1 Hover
        rsInterface.child(2, 60603, 19, 41); // Tab 1
        rsInterface.child(3, 60605, 19, 76); // Tab 2 Hover
        rsInterface.child(4, 60606, 19, 76); // Tab 2
        rsInterface.child(5, 60608, 19, 111); // Tab 3 Hover
        rsInterface.child(6, 60609, 19, 111); // Tab 3
        rsInterface.child(7, 60611, 19, 146); // Tab 4 Hover
        rsInterface.child(8, 60612, 19, 146); // Tab 4
        rsInterface.child(9, 60614, 19, 181); // Tab 5 Hover
        rsInterface.child(10, 60615, 19, 181); // Tab 5
        rsInterface.child(11, 60617, 19, 216); // Tab 6 Hover
        rsInterface.child(12, 60618, 19, 216); // Tab 6
        rsInterface.child(13, 60622, 153, 60); // Button 1 Hover
        rsInterface.child(14, 60623, 153, 60); // Button 1
        rsInterface.child(15, 60625, 267, 60); // Button 2 Hover
        rsInterface.child(16, 60626, 267, 60); // Button 2
        rsInterface.child(17, 60628, 383, 60); // Button 3 Hover
        rsInterface.child(18, 60629, 383, 60); // Button 3
        rsInterface.child(19, 60631, 153, 108); // Button 4 Hover
        rsInterface.child(20, 60632, 153, 108); // Button 4
        rsInterface.child(21, 60634, 267, 108); // Button 5 Hover
        rsInterface.child(22, 60635, 267, 108); // Button 5
        rsInterface.child(23, 60637, 383, 108); // Button 6 Hover
        rsInterface.child(24, 60638, 383, 108); // Button 6
        rsInterface.child(25, 60640, 153, 156); // Button 7 Hover
        rsInterface.child(26, 60641, 153, 156); // Button 7
        rsInterface.child(27, 60643, 267, 156); // Button 8 Hover
        rsInterface.child(28, 60644, 267, 156); // Button 8
        rsInterface.child(29, 60646, 383, 156); // Button 9 Hover
        rsInterface.child(30, 60647, 383, 156); // Button 9

        rsInterface.child(31, 60652, 484, 17); // Close Hover
        rsInterface.child(32, 60653, 484, 17); // Close

        rsInterface.child(33, 60656, 75, 50); // Title 1
        rsInterface.child(34, 60657, 75, 85); // Title 2
        rsInterface.child(35, 60658, 75, 120); // Title 3
        rsInterface.child(36, 60659, 75, 155); // Title 4
        rsInterface.child(37, 60660, 75, 190); // Title 5
        rsInterface.child(38, 60661, 75, 225); // Title 6
        rsInterface.child(39, 60662, 198, 61); // Teleport Text 1
        rsInterface.child(40, 60663, 198, 75); // Teleport Text 1
        rsInterface.child(41, 60664, 313, 61); // Teleport Text 2
        rsInterface.child(42, 60665, 313, 75); // Teleport Text 2
        rsInterface.child(43, 60666, 429, 61); // Teleport Text 3
        rsInterface.child(44, 60667, 429, 75); // Teleport Text 3
        rsInterface.child(45, 60668, 198, 109); // Teleport Text 4
        rsInterface.child(46, 60669, 198, 122); // Teleport Text 4
        rsInterface.child(47, 60670, 313, 109); // Teleport Text 5
        rsInterface.child(48, 60671, 313, 123); // Teleport Text 5
        rsInterface.child(49, 60672, 429, 109); // Teleport Text 6
        rsInterface.child(50, 60673, 429, 123); // Teleport Text 6
        rsInterface.child(51, 60674, 198, 157); // Teleport Text 7
        rsInterface.child(52, 60675, 198, 171); // Teleport Text 7
        rsInterface.child(53, 60676, 313, 157); // Teleport Text 8
        rsInterface.child(54, 60677, 313, 171); // Teleport Text 8
        rsInterface.child(55, 60678, 429, 157); // Teleport Text 9
        rsInterface.child(56, 60679, 429, 171); // Teleport Text 9
        //rsInterface.child(68, 60680, 82, 233); // Teleport Text 10
        //rsInterface.child(69, 60681, 82, 247); // Teleport Text 10
        rsInterface.child(57, 60690, 258, 18); // Title
        rsInterface.child(58, 60691, 153, 204); // Button 10 Hover
        rsInterface.child(59, 60692, 153, 204); // Button 10
        rsInterface.child(60, 60694, 267, 204); // Button 11 Hover
        rsInterface.child(61, 60695, 267, 204); // Button 11
        rsInterface.child(62, 60697, 383, 204); // Button 12 Hover
        rsInterface.child(63, 60698, 383, 204); // Button 12
        rsInterface.child(64, 18374, 198, 205); // Teleport Text 10
        rsInterface.child(65, 60701, 198, 219); // Teleport Text 10
        rsInterface.child(66, 60702, 313, 205); // Teleport Text 11
        rsInterface.child(67, 60703, 313, 219); // Teleport Text 11
        rsInterface.child(68, 60704, 429, 205); // Teleport Text 12
        rsInterface.child(69, 60705, 429, 219); // Teleport Text 12
    }

    public static void bossesTeleports(TextDrawingArea[] tda) {
        RSInterface rsInterface = addInterface(60800);

        setChildren(70, rsInterface);
        rsInterface.child(0, 60601, 6, 14); // Background
        rsInterface.child(1, 60602, 19, 41); // Tab 1 Hover
        rsInterface.child(2, 60603, 19, 41); // Tab 1
        rsInterface.child(3, 60605, 19, 76); // Tab 2 Hover
        rsInterface.child(4, 60606, 19, 76); // Tab 2
        rsInterface.child(5, 60608, 19, 111); // Tab 3 Hover
        rsInterface.child(6, 60609, 19, 111); // Tab 3
        rsInterface.child(7, 60611, 19, 146); // Tab 4 Hover
        rsInterface.child(8, 60612, 19, 146); // Tab 4
        rsInterface.child(9, 60614, 19, 181); // Tab 5 Hover
        rsInterface.child(10, 60615, 19, 181); // Tab 5
        rsInterface.child(11, 60617, 19, 216); // Tab 6 Hover
        rsInterface.child(12, 60618, 19, 216); // Tab 6
        rsInterface.child(13, 60622, 153, 60); // Button 1 Hover
        rsInterface.child(14, 60623, 153, 60); // Button 1
        rsInterface.child(15, 60625, 267, 60); // Button 2 Hover
        rsInterface.child(16, 60626, 267, 60); // Button 2
        rsInterface.child(17, 60628, 383, 60); // Button 3 Hover
        rsInterface.child(18, 60629, 383, 60); // Button 3
        rsInterface.child(19, 60631, 153, 108); // Button 4 Hover
        rsInterface.child(20, 60632, 153, 108); // Button 4
        rsInterface.child(21, 60634, 267, 108); // Button 5 Hover
        rsInterface.child(22, 60635, 267, 108); // Button 5
        rsInterface.child(23, 60637, 383, 108); // Button 6 Hover
        rsInterface.child(24, 60638, 383, 108); // Button 6
        rsInterface.child(25, 60640, 153, 156); // Button 7 Hover
        rsInterface.child(26, 60641, 153, 156); // Button 7
        rsInterface.child(27, 60643, 267, 156); // Button 8 Hover
        rsInterface.child(28, 60644, 267, 156); // Button 8
        rsInterface.child(29, 60646, 383, 156); // Button 9 Hover
        rsInterface.child(30, 60647, 383, 156); // Button 9

        rsInterface.child(31, 60652, 484, 17); // Close Hover
        rsInterface.child(32, 60653, 484, 17); // Close

        rsInterface.child(33, 60656, 75, 50); // Title 1
        rsInterface.child(34, 60657, 75, 85); // Title 2
        rsInterface.child(35, 60658, 75, 120); // Title 3
        rsInterface.child(36, 60659, 75, 155); // Title 4
        rsInterface.child(37, 60660, 75, 190); // Title 5
        rsInterface.child(38, 60661, 75, 225); // Title 6
        rsInterface.child(39, 60662, 198, 61); // Teleport Text 1
        rsInterface.child(40, 60663, 198, 75); // Teleport Text 1
        rsInterface.child(41, 60664, 313, 61); // Teleport Text 2
        rsInterface.child(42, 60665, 313, 75); // Teleport Text 2
        rsInterface.child(43, 60666, 429, 61); // Teleport Text 3
        rsInterface.child(44, 60667, 429, 75); // Teleport Text 3
        rsInterface.child(45, 60668, 198, 109); // Teleport Text 4
        rsInterface.child(46, 60669, 198, 122); // Teleport Text 4
        rsInterface.child(47, 60670, 313, 109); // Teleport Text 5
        rsInterface.child(48, 60671, 313, 123); // Teleport Text 5
        rsInterface.child(49, 60672, 429, 109); // Teleport Text 6
        rsInterface.child(50, 60673, 429, 123); // Teleport Text 6
        rsInterface.child(51, 60674, 198, 157); // Teleport Text 7
        rsInterface.child(52, 60675, 198, 171); // Teleport Text 7
        rsInterface.child(53, 60676, 313, 157); // Teleport Text 8
        rsInterface.child(54, 60677, 313, 171); // Teleport Text 8
        rsInterface.child(55, 60678, 429, 157); // Teleport Text 9
        rsInterface.child(56, 60679, 429, 171); // Teleport Text 9
        //rsInterface.child(68, 60680, 82, 233); // Teleport Text 10
        //rsInterface.child(69, 60681, 82, 247); // Teleport Text 10
        rsInterface.child(57, 60690, 258, 18); // Title
        rsInterface.child(58, 60691, 153, 204); // Button 10 Hover
        rsInterface.child(59, 60692, 153, 204); // Button 10
        rsInterface.child(60, 60694, 267, 204); // Button 11 Hover
        rsInterface.child(61, 60695, 267, 204); // Button 11
        rsInterface.child(62, 60697, 383, 204); // Button 12 Hover
        rsInterface.child(63, 60698, 383, 204); // Button 12
        rsInterface.child(64, 18374, 198, 205); // Teleport Text 10
        rsInterface.child(65, 60701, 198, 219); // Teleport Text 10
        rsInterface.child(66, 60702, 313, 205); // Teleport Text 11
        rsInterface.child(67, 60703, 313, 219); // Teleport Text 11
        rsInterface.child(68, 60704, 429, 205); // Teleport Text 12
        rsInterface.child(69, 60705, 429, 219); // Teleport Text 12
    }

    public static void playerKillingTeleports(TextDrawingArea[] tda) {
        RSInterface rsInterface = addInterface(60900);

        setChildren(70, rsInterface);
        rsInterface.child(0, 60601, 6, 14); // Background
        rsInterface.child(1, 60602, 19, 41); // Tab 1 Hover
        rsInterface.child(2, 60603, 19, 41); // Tab 1
        rsInterface.child(3, 60605, 19, 76); // Tab 2 Hover
        rsInterface.child(4, 60606, 19, 76); // Tab 2
        rsInterface.child(5, 60608, 19, 111); // Tab 3 Hover
        rsInterface.child(6, 60609, 19, 111); // Tab 3
        rsInterface.child(7, 60611, 19, 146); // Tab 4 Hover
        rsInterface.child(8, 60612, 19, 146); // Tab 4
        rsInterface.child(9, 60614, 19, 181); // Tab 5 Hover
        rsInterface.child(10, 60615, 19, 181); // Tab 5
        rsInterface.child(11, 60617, 19, 216); // Tab 6 Hover
        rsInterface.child(12, 60618, 19, 216); // Tab 6
        rsInterface.child(13, 60622, 153, 60); // Button 1 Hover
        rsInterface.child(14, 60623, 153, 60); // Button 1
        rsInterface.child(15, 60625, 267, 60); // Button 2 Hover
        rsInterface.child(16, 60626, 267, 60); // Button 2
        rsInterface.child(17, 60628, 383, 60); // Button 3 Hover
        rsInterface.child(18, 60629, 383, 60); // Button 3
        rsInterface.child(19, 60631, 153, 108); // Button 4 Hover
        rsInterface.child(20, 60632, 153, 108); // Button 4
        rsInterface.child(21, 60634, 267, 108); // Button 5 Hover
        rsInterface.child(22, 60635, 267, 108); // Button 5
        rsInterface.child(23, 60637, 383, 108); // Button 6 Hover
        rsInterface.child(24, 60638, 383, 108); // Button 6
        rsInterface.child(25, 60640, 153, 156); // Button 7 Hover
        rsInterface.child(26, 60641, 153, 156); // Button 7
        rsInterface.child(27, 60643, 267, 156); // Button 8 Hover
        rsInterface.child(28, 60644, 267, 156); // Button 8
        rsInterface.child(29, 60646, 383, 156); // Button 9 Hover
        rsInterface.child(30, 60647, 383, 156); // Button 9

        rsInterface.child(31, 60652, 484, 17); // Close Hover
        rsInterface.child(32, 60653, 484, 17); // Close

        rsInterface.child(33, 60656, 75, 50); // Title 1
        rsInterface.child(34, 60657, 75, 85); // Title 2
        rsInterface.child(35, 60658, 75, 120); // Title 3
        rsInterface.child(36, 60659, 75, 155); // Title 4
        rsInterface.child(37, 60660, 75, 190); // Title 5
        rsInterface.child(38, 60661, 75, 225); // Title 6
        rsInterface.child(39, 60662, 198, 61); // Teleport Text 1
        rsInterface.child(40, 60663, 198, 75); // Teleport Text 1
        rsInterface.child(41, 60664, 313, 61); // Teleport Text 2
        rsInterface.child(42, 60665, 313, 75); // Teleport Text 2
        rsInterface.child(43, 60666, 429, 61); // Teleport Text 3
        rsInterface.child(44, 60667, 429, 75); // Teleport Text 3
        rsInterface.child(45, 60668, 198, 109); // Teleport Text 4
        rsInterface.child(46, 60669, 198, 122); // Teleport Text 4
        rsInterface.child(47, 60670, 313, 109); // Teleport Text 5
        rsInterface.child(48, 60671, 313, 123); // Teleport Text 5
        rsInterface.child(49, 60672, 429, 109); // Teleport Text 6
        rsInterface.child(50, 60673, 429, 123); // Teleport Text 6
        rsInterface.child(51, 60674, 198, 157); // Teleport Text 7
        rsInterface.child(52, 60675, 198, 171); // Teleport Text 7
        rsInterface.child(53, 60676, 313, 157); // Teleport Text 8
        rsInterface.child(54, 60677, 313, 171); // Teleport Text 8
        rsInterface.child(55, 60678, 429, 157); // Teleport Text 9
        rsInterface.child(56, 60679, 429, 171); // Teleport Text 9
        //rsInterface.child(68, 60680, 82, 233); // Teleport Text 10
        //rsInterface.child(69, 60681, 82, 247); // Teleport Text 10
        rsInterface.child(57, 60690, 258, 18); // Title
        rsInterface.child(58, 60691, 153, 204); // Button 10 Hover
        rsInterface.child(59, 60692, 153, 204); // Button 10
        rsInterface.child(60, 60694, 267, 204); // Button 11 Hover
        rsInterface.child(61, 60695, 267, 204); // Button 11
        rsInterface.child(62, 60697, 383, 204); // Button 12 Hover
        rsInterface.child(63, 60698, 383, 204); // Button 12
        rsInterface.child(64, 18374, 198, 205); // Teleport Text 10
        rsInterface.child(65, 60701, 198, 219); // Teleport Text 10
        rsInterface.child(66, 60702, 313, 205); // Teleport Text 11
        rsInterface.child(67, 60703, 313, 219); // Teleport Text 11
        rsInterface.child(68, 60704, 429, 205); // Teleport Text 12
        rsInterface.child(69, 60705, 429, 219); // Teleport Text 12
    }

    public static void skillingTeleports(TextDrawingArea[] tda) {
        RSInterface rsInterface = addInterface(61000);

        setChildren(70, rsInterface);
        rsInterface.child(0, 60601, 6, 14); // Background
        rsInterface.child(1, 60602, 19, 41); // Tab 1 Hover
        rsInterface.child(2, 60603, 19, 41); // Tab 1
        rsInterface.child(3, 60605, 19, 76); // Tab 2 Hover
        rsInterface.child(4, 60606, 19, 76); // Tab 2
        rsInterface.child(5, 60608, 19, 111); // Tab 3 Hover
        rsInterface.child(6, 60609, 19, 111); // Tab 3
        rsInterface.child(7, 60611, 19, 146); // Tab 4 Hover
        rsInterface.child(8, 60612, 19, 146); // Tab 4
        rsInterface.child(9, 60614, 19, 181); // Tab 5 Hover
        rsInterface.child(10, 60615, 19, 181); // Tab 5
        rsInterface.child(11, 60617, 19, 216); // Tab 6 Hover
        rsInterface.child(12, 60618, 19, 216); // Tab 6
        rsInterface.child(13, 60622, 153, 60); // Button 1 Hover
        rsInterface.child(14, 60623, 153, 60); // Button 1
        rsInterface.child(15, 60625, 267, 60); // Button 2 Hover
        rsInterface.child(16, 60626, 267, 60); // Button 2
        rsInterface.child(17, 60628, 383, 60); // Button 3 Hover
        rsInterface.child(18, 60629, 383, 60); // Button 3
        rsInterface.child(19, 60631, 153, 108); // Button 4 Hover
        rsInterface.child(20, 60632, 153, 108); // Button 4
        rsInterface.child(21, 60634, 267, 108); // Button 5 Hover
        rsInterface.child(22, 60635, 267, 108); // Button 5
        rsInterface.child(23, 60637, 383, 108); // Button 6 Hover
        rsInterface.child(24, 60638, 383, 108); // Button 6
        rsInterface.child(25, 60640, 153, 156); // Button 7 Hover
        rsInterface.child(26, 60641, 153, 156); // Button 7
        rsInterface.child(27, 60643, 267, 156); // Button 8 Hover
        rsInterface.child(28, 60644, 267, 156); // Button 8
        rsInterface.child(29, 60646, 383, 156); // Button 9 Hover
        rsInterface.child(30, 60647, 383, 156); // Button 9

        rsInterface.child(31, 60652, 484, 17); // Close Hover
        rsInterface.child(32, 60653, 484, 17); // Close

        rsInterface.child(33, 60656, 75, 50); // Title 1
        rsInterface.child(34, 60657, 75, 85); // Title 2
        rsInterface.child(35, 60658, 75, 120); // Title 3
        rsInterface.child(36, 60659, 75, 155); // Title 4
        rsInterface.child(37, 60660, 75, 190); // Title 5
        rsInterface.child(38, 60661, 75, 225); // Title 6
        rsInterface.child(39, 60662, 198, 61); // Teleport Text 1
        rsInterface.child(40, 60663, 198, 75); // Teleport Text 1
        rsInterface.child(41, 60664, 313, 61); // Teleport Text 2
        rsInterface.child(42, 60665, 313, 75); // Teleport Text 2
        rsInterface.child(43, 60666, 429, 61); // Teleport Text 3
        rsInterface.child(44, 60667, 429, 75); // Teleport Text 3
        rsInterface.child(45, 60668, 198, 109); // Teleport Text 4
        rsInterface.child(46, 60669, 198, 122); // Teleport Text 4
        rsInterface.child(47, 60670, 313, 109); // Teleport Text 5
        rsInterface.child(48, 60671, 313, 123); // Teleport Text 5
        rsInterface.child(49, 60672, 429, 109); // Teleport Text 6
        rsInterface.child(50, 60673, 429, 123); // Teleport Text 6
        rsInterface.child(51, 60674, 198, 157); // Teleport Text 7
        rsInterface.child(52, 60675, 198, 171); // Teleport Text 7
        rsInterface.child(53, 60676, 313, 157); // Teleport Text 8
        rsInterface.child(54, 60677, 313, 171); // Teleport Text 8
        rsInterface.child(55, 60678, 429, 157); // Teleport Text 9
        rsInterface.child(56, 60679, 429, 171); // Teleport Text 9
        //rsInterface.child(68, 60680, 82, 233); // Teleport Text 10
        //rsInterface.child(69, 60681, 82, 247); // Teleport Text 10
        rsInterface.child(57, 60690, 258, 18); // Title
        rsInterface.child(58, 60691, 153, 204); // Button 10 Hover
        rsInterface.child(59, 60692, 153, 204); // Button 10
        rsInterface.child(60, 60694, 267, 204); // Button 11 Hover
        rsInterface.child(61, 60695, 267, 204); // Button 11
        rsInterface.child(62, 60697, 383, 204); // Button 12 Hover
        rsInterface.child(63, 60698, 383, 204); // Button 12
        rsInterface.child(64, 18374, 198, 205); // Teleport Text 10
        rsInterface.child(65, 60701, 198, 219); // Teleport Text 10
        rsInterface.child(66, 60702, 313, 205); // Teleport Text 11
        rsInterface.child(67, 60703, 313, 219); // Teleport Text 11
        rsInterface.child(68, 60704, 429, 205); // Teleport Text 12
        rsInterface.child(69, 60705, 429, 219); // Teleport Text 12
    }

    public static void donatorTeleports(TextDrawingArea[] tda) {
        RSInterface rsInterface = addInterface(61100);

        setChildren(70, rsInterface);
        rsInterface.child(0, 60601, 6, 14); // Background
        rsInterface.child(1, 60602, 19, 41); // Tab 1 Hover
        rsInterface.child(2, 60603, 19, 41); // Tab 1
        rsInterface.child(3, 60605, 19, 76); // Tab 2 Hover
        rsInterface.child(4, 60606, 19, 76); // Tab 2
        rsInterface.child(5, 60608, 19, 111); // Tab 3 Hover
        rsInterface.child(6, 60609, 19, 111); // Tab 3
        rsInterface.child(7, 60611, 19, 146); // Tab 4 Hover
        rsInterface.child(8, 60612, 19, 146); // Tab 4
        rsInterface.child(9, 60614, 19, 181); // Tab 5 Hover
        rsInterface.child(10, 60615, 19, 181); // Tab 5
        rsInterface.child(11, 60617, 19, 216); // Tab 6 Hover
        rsInterface.child(12, 60618, 19, 216); // Tab 6
        rsInterface.child(13, 60622, 153, 60); // Button 1 Hover
        rsInterface.child(14, 60623, 153, 60); // Button 1
        rsInterface.child(15, 60625, 267, 60); // Button 2 Hover
        rsInterface.child(16, 60626, 267, 60); // Button 2
        rsInterface.child(17, 60628, 383, 60); // Button 3 Hover
        rsInterface.child(18, 60629, 383, 60); // Button 3
        rsInterface.child(19, 60631, 153, 108); // Button 4 Hover
        rsInterface.child(20, 60632, 153, 108); // Button 4
        rsInterface.child(21, 60634, 267, 108); // Button 5 Hover
        rsInterface.child(22, 60635, 267, 108); // Button 5
        rsInterface.child(23, 60637, 383, 108); // Button 6 Hover
        rsInterface.child(24, 60638, 383, 108); // Button 6
        rsInterface.child(25, 60640, 153, 156); // Button 7 Hover
        rsInterface.child(26, 60641, 153, 156); // Button 7
        rsInterface.child(27, 60643, 267, 156); // Button 8 Hover
        rsInterface.child(28, 60644, 267, 156); // Button 8
        rsInterface.child(29, 60646, 383, 156); // Button 9 Hover
        rsInterface.child(30, 60647, 383, 156); // Button 9

        rsInterface.child(31, 60652, 484, 17); // Close Hover
        rsInterface.child(32, 60653, 484, 17); // Close

        rsInterface.child(33, 60656, 75, 50); // Title 1
        rsInterface.child(34, 60657, 75, 85); // Title 2
        rsInterface.child(35, 60658, 75, 120); // Title 3
        rsInterface.child(36, 60659, 75, 155); // Title 4
        rsInterface.child(37, 60660, 75, 190); // Title 5
        rsInterface.child(38, 60661, 75, 225); // Title 6
        rsInterface.child(39, 60662, 198, 61); // Teleport Text 1
        rsInterface.child(40, 60663, 198, 75); // Teleport Text 1
        rsInterface.child(41, 60664, 313, 61); // Teleport Text 2
        rsInterface.child(42, 60665, 313, 75); // Teleport Text 2
        rsInterface.child(43, 60666, 429, 61); // Teleport Text 3
        rsInterface.child(44, 60667, 429, 75); // Teleport Text 3
        rsInterface.child(45, 60668, 198, 109); // Teleport Text 4
        rsInterface.child(46, 60669, 198, 122); // Teleport Text 4
        rsInterface.child(47, 60670, 313, 109); // Teleport Text 5
        rsInterface.child(48, 60671, 313, 123); // Teleport Text 5
        rsInterface.child(49, 60672, 429, 109); // Teleport Text 6
        rsInterface.child(50, 60673, 429, 123); // Teleport Text 6
        rsInterface.child(51, 60674, 198, 157); // Teleport Text 7
        rsInterface.child(52, 60675, 198, 171); // Teleport Text 7
        rsInterface.child(53, 60676, 313, 157); // Teleport Text 8
        rsInterface.child(54, 60677, 313, 171); // Teleport Text 8
        rsInterface.child(55, 60678, 429, 157); // Teleport Text 9
        rsInterface.child(56, 60679, 429, 171); // Teleport Text 9
        //rsInterface.child(68, 60680, 82, 233); // Teleport Text 10
        //rsInterface.child(69, 60681, 82, 247); // Teleport Text 10
        rsInterface.child(57, 60690, 258, 18); // Title
        rsInterface.child(58, 60691, 153, 204); // Button 10 Hover
        rsInterface.child(59, 60692, 153, 204); // Button 10
        rsInterface.child(60, 60694, 267, 204); // Button 11 Hover
        rsInterface.child(61, 60695, 267, 204); // Button 11
        rsInterface.child(62, 60697, 383, 204); // Button 12 Hover
        rsInterface.child(63, 60698, 383, 204); // Button 12
        rsInterface.child(64, 18374, 198, 205); // Teleport Text 10
        rsInterface.child(65, 60701, 198, 219); // Teleport Text 10
        rsInterface.child(66, 60702, 313, 205); // Teleport Text 11
        rsInterface.child(67, 60703, 313, 219); // Teleport Text 11
        rsInterface.child(68, 60704, 429, 205); // Teleport Text 12
        rsInterface.child(69, 60705, 429, 219); // Teleport Text 12
    }

    public static void addSkillButton(int id, String skillGuide) {
        RSInterface button = addTabInterface(id);
        button.type = 5;
        button.atActionType = 5;
        button.contentType = 0;
        button.width = 60;
        button.height = 28;
        button.disabledSprite = getSprite("Interfaces/Skilltab/Button");
        button.tooltip = "@whi@View @or1@" + skillGuide + " @whi@Options";
    }

    private static void addSprite(int id, Sprite sprite) {
        RSInterface component = interfaceCache[id] = new RSInterface();
        component.id = id;
        component.parentID = id;
        component.type = 5;
        component.atActionType = 0;
        component.contentType = 0;
        component.opacity = (byte) 0;
        component.hoverType = 52;
        component.disabledSprite = sprite;
        component.enabledSprite = sprite;
        component.width = 512;
        component.height = 334;
    }

    public static void addHoverButton(int i, Sprite sprite, int j, int width, int height, String text, int contentType, int hoverOver, int aT) {
        RSInterface tab = addTabInterface(i);
        tab.id = i;
        tab.parentID = i;
        tab.type = 5;
        tab.atActionType = aT;
        tab.contentType = contentType;
        tab.opacity = 0;
        tab.hoverType = hoverOver;
        tab.disabledSprite = sprite;
        tab.enabledSprite = sprite;
        tab.width = width;
        tab.height = height;
        tab.tooltip = text;
    }

    public static void createSkillHover(int id, int x) {
        RSInterface hover = addInterface(id);
        hover.type = 10;
        hover.contentType = x;
        hover.width = 60;
        hover.height = 28;
        hover.inventoryHover = true;
    }

    public static void createHover(int id, int x, int width) {
        RSInterface hover = addInterface(id);
        hover.type = 10;
        hover.contentType = x;
        hover.width = width;
        hover.height = 28;
        hover.inventoryHover = true;
    }

    public static void addImage(int id, int sID) {
        RSInterface image = addInterface(id);
        image.type = 5;
        image.atActionType = 0;
        image.contentType = 0;
        image.width = 100;
        image.height = 100;
        image.disabledSpriteId = sID;
    }

    public static void addSkillText(int id, boolean max, int skill) {
        RSInterface text = addInterface(id);
        text.id = id;
        text.parentID = id;
        text.type = 4;
        text.atActionType = 0;
        text.width = 15;
        text.height = 12;
        text.textDrawingAreas = fonts[0];
        text.shadowed = true;
        text.centerText = true;
        text.disabledColor = 16776960;
        if (!max) {
            text.valueIndexArray = new int[1][];
            text.valueIndexArray[0] = new int[3];
            text.valueIndexArray[0][0] = 1;
            text.valueIndexArray[0][1] = skill;
            text.valueIndexArray[0][2] = 0;
        } else {
            text.valueIndexArray = new int[2][];
            text.valueIndexArray[0] = new int[3];
            text.valueIndexArray[0][0] = 2;
            text.valueIndexArray[0][1] = skill;
            text.valueIndexArray[0][2] = 0;
            text.valueIndexArray[1] = new int[1];
            text.valueIndexArray[1][0] = 0;
        }
        text.message = "%1";
    }

    public static Sprite getSprite(String s) {
        Sprite image;
        try {
            image = new Sprite(s);
            if (image != null) {
                return image;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return image;
    }

    public static void addSkillButton(int id) {
        RSInterface button = addInterface(id);
        button.type = 5;
        button.atActionType = 5;
        button.contentType = 0;
        button.width = 60;
        button.height = 27;
        button.disabledSpriteId = 454;
        button.tooltip = "View";
    }

    /*
     * End of 602 Skill Tab
     */
    public static void warriorGuild(TextDrawingArea[] tda) {
        RSInterface tab = addInterface(54500);
        addItemOnInterface(54503, 7777, new String[]{});
        addSpriteLoader(54501, 970);
        //addTransparentSpriteWSpriteLoader(54501, 970, 130);
        addText(54502, "Warriors Guild", 0xff9933, true, true, -1, tda, 2);
        //addText(54503, "Current Defender: Rune", 0xff9933, false, true, -1, tda, 0);
        addText(54504, "Dropping:", 0xff9933, false, true, -1, tda, 0);
        tab.totalChildren(4);
        tab.child(0, 54501, 1, 247);
        tab.child(1, 54502, 75, 252);
        tab.child(2, 54503, 60, 292);
        tab.child(3, 54504, 54, 268);

    }

    public static void dropPreview(TextDrawingArea[] tda) {
        RSInterface tab = addInterface(47989);
        addItemOnInterface(47900, 8888, new String[]{"Information"});
        addSpriteLoader(47901, 970);
        tab.childToIntersect = 47905;
        tab.childToIntersect2 = 47906;
        tab.childToIntersect3 = 47907;
        addButtonWSpriteLoader(47905, 892, "Close Window", 16, 16);
        addButtonWSpriteLoader(47906, 992, "Previous", 16, 16);
        addButtonWSpriteLoader(47907, 993, "Next", 16, 16);

        RSInterface btn = interfaceCache[47905];
        RSInterface btn2 = interfaceCache[47906];
        RSInterface btn3 = interfaceCache[47907];
        btn.positionX = 135;
        btn.positionY = 248;
        btn.width = 18;
        btn.height = 18;

        btn2.width = 18;
        btn2.height = 18;
        btn3.width = 18;
        btn3.height = 18;

        btn2.positionX = 23;
        btn2.positionY = 297;
        btn3.positionX = 114;
        btn3.positionY = 297;

        //addTransparentSpriteWSpriteLoader(74501, 970, 130);
        addText(47902, "Drop Preview", 0xff9933, true, true, -1, tda, 2);
        //addText(74503, "Current Defender: Rune", 0xff9933, false, true, -1, tda, 0);
        addText(47904, "Dropping:", 0xff9933, false, true, -1, tda, 0);
        tab.totalChildren(7);
        tab.child(0, 47901, 1, 247);
        tab.child(1, 47902, 75, 250);
        tab.child(2, 47900, 61, 292);
        tab.child(3, 47904, 47, 270);
        tab.child(4, 47905, 135, 248);
        tab.child(5, 47906, 23, 297);
        tab.child(6, 47907, 114, 297);


    }

    private static void teleportWidget(TextDrawingArea[] tda) {
        RSInterface widget = addInterface(49112);

        addSprite(49113, SpriteLoader.sprites[968]);

        addHoverButtonWSpriteLoader(49114, 737, 16, 16, "Close", -1, 49115, 1);
        addHoveredImageWSpriteLoader(49115, 738, 16, 16, 49116);

        addHoverButtonWSpriteLoader(49117, 973, 90, 25, "Teleport", -1, 49118, 1);
        addHoveredImageWSpriteLoader(49118, 974, 90, 25, 49119);

        addSpriteLoader(49120, 969);
        addText(49121, "1,000,000 Coins", tda, 3, 0xFF981F, true, true);

        addSpriteLoader(49122, 970);
        addText(49123, "Dangerous", tda, 3, 0xFF981F, true, true);

        addSpriteLoader(49124, 971);
        addText(49125, "Inside Wilderness", tda, 3, 0xFF981F, true, true);

        addText(49126, "King Black Dragon", tda, 2, 0xFF981F, true, true);

        addText(49127, "Teleport", tda, 3, 0xFF981F, true, true);

        //addNpcToWidget(43006);
        widget.totalChildren(14);
        widget.child(0, 49113, 56, 5);
        widget.child(1, 49114, 443, 9);
        widget.child(2, 49115, 443, 9);
        widget.child(3, 49117, 125, 280);
        widget.child(4, 49118, 125, 280);
        widget.child(5, 49120, 80, 65);
        widget.child(6, 49121, 170, 65);
        widget.child(7, 49122, 85, 90);
        widget.child(8, 49123, 152, 90);
        widget.child(9, 49124, 83, 115);
        widget.child(10, 49125, 175, 115);
        widget.child(11, 49126, 140, 35);
        widget.child(12, 49127, 170, 283);
        widget.child(13, 49128, 185, 77);


        RSInterface scrollTab = addTabInterface(49128);
        scrollTab.width = 244;
        scrollTab.height = 230;
        scrollTab.scrollMax = 550;

        scrollTab.totalChildren(50);

        for (int i = 0; i < 50; i += 2) {
            addButtonWSpriteLoader(49129 + i, 975, "Select", 140, 22);
            scrollTab.child(i, 49129 + i, 105, i / 2 * 22);
            addText(49129 + i + 1, "King Black Dragon", tda, 1, 0xFF981F, true, true);
            scrollTab.child(i + 1, 49129 + i + 1, 175, 3 + (i / 2 * 22));
        }

    }

    public static void godwars(TextDrawingArea[] tda) {
        RSInterface tab = addTabInterface(14500);
        addText(14501, "NPC Killcount", tda, 1, 0xff9040, false, true);
        addText(14502, "Armadyl kills", tda, 1, 0xff9040, false, true);
        addText(14503, "Bandos kills", tda, 1, 0xff9040, false, true);
        addText(14504, "Saradomin kills", tda, 1, 0xff9040, false, true);
        addText(14505, "Zamorak kills", tda, 1, 0xff9040, false, true);
        addText(14510, "Zaros kills", tda, 1, 0xff9040, false, true);
        addText(14506, "", tda, 1, 0x66FFFF, true, true);
        addText(14507, "", tda, 1, 0x66FFFF, true, true);
        addText(14508, "", tda, 1, 0x66FFFF, true, true);
        addText(14509, "", tda, 1, 0x66FFFF, true, true);
        addText(14511, "1", tda, 1, 0x66FFFF, true, true);
        int[][] configs = {{14501, 381, 9}, {14502, 381, 33}, {14503, 381, 47}, {14504, 381, 61}, {14505, 381, 75}, {14506, 482, 33}, {14507, 482, 47}, {14508, 482, 61}, {14509, 482, 75}, {14510, 381, 90}, {14511, 482, 90}};
        tab.totalChildren(11);
        for (int i = 0; i < configs.length; i++) {
            tab.child(i, configs[i][0], configs[i][1], configs[i][2]);
        }
    }

    public static void addButton(int id, int sid, String spriteName, String tooltip) {
        RSInterface tab = interfaceCache[id] = new RSInterface();
        tab.id = id;
        tab.parentID = id;
        tab.type = 5;
        tab.atActionType = 1;
        tab.contentType = 0;
        tab.opacity = (byte) 0;
        tab.hoverType = 52;
        tab.disabledSprite = imageLoader(sid, spriteName);
        tab.enabledSprite = imageLoader(sid, spriteName);
        tab.width = tab.disabledSprite.myWidth;
        tab.height = tab.enabledSprite.myHeight;
        tab.tooltip = tooltip;
    }

    ;

    public static void addPrayerWithTooltip(int i, int configId, int configFrame, int requiredValues, int prayerSpriteID, int Hover, String tooltip) {
        RSInterface Interface = addTabInterface(i);
        Interface.id = i;
        Interface.parentID = 5608;
        Interface.type = 5;
        Interface.atActionType = 4;
        Interface.contentType = 0;
        Interface.opacity = 0;
        Interface.hoverType = Hover;
        Interface.disabledSpriteId = 480;
        Interface.enabledSpriteId = -1;
        Interface.width = 34;
        Interface.height = 34;
        Interface.valueCompareType = new int[1];
        Interface.requiredValues = new int[1];
        Interface.valueCompareType[0] = 1;
        Interface.requiredValues[0] = configId;
        Interface.valueIndexArray = new int[1][3];
        Interface.valueIndexArray[0][0] = 5;
        Interface.valueIndexArray[0][1] = configFrame;
        Interface.valueIndexArray[0][2] = 0;
        Interface.tooltip = tooltip;
        Interface = addTabInterface(i + 1);
        Interface.id = i + 1;
        Interface.parentID = 5608;
        Interface.type = 5;
        Interface.atActionType = 0;
        Interface.contentType = 0;
        Interface.opacity = 0;
        Interface.disabledSpriteId = 548 + prayerSpriteID;
        Interface.enabledSpriteId = 522 + prayerSpriteID;
        Interface.width = 34;
        Interface.height = 34;
        Interface.valueCompareType = new int[1];
        Interface.requiredValues = new int[1];
        Interface.valueCompareType[0] = 2;
        Interface.requiredValues[0] = requiredValues + 1;
        Interface.valueIndexArray = new int[1][3];
        Interface.valueIndexArray[0][0] = 2;
        Interface.valueIndexArray[0][1] = 5;
        Interface.valueIndexArray[0][2] = 0;
    }

    public static void addPrayerWithTooltipSpriteLoader(int i, int configId, int configFrame, int requiredValues, int spriteOn, int spriteOff, int Hover, String tooltip) {
        RSInterface Interface = addTabInterface(i);
        Interface.id = i;
        Interface.parentID = 5608;
        Interface.type = 5;
        Interface.atActionType = 4;
        Interface.contentType = 0;
        Interface.opacity = 0;
        Interface.hoverType = Hover;
        Interface.disabledSpriteId = 480;
        Interface.enabledSpriteId = -1;
        Interface.width = 34;
        Interface.height = 34;
        Interface.valueCompareType = new int[1];
        Interface.requiredValues = new int[1];
        Interface.valueCompareType[0] = 1;
        Interface.requiredValues[0] = configId;
        Interface.valueIndexArray = new int[1][3];
        Interface.valueIndexArray[0][0] = 5;
        Interface.valueIndexArray[0][1] = configFrame;
        Interface.valueIndexArray[0][2] = 0;
        Interface.tooltip = tooltip;
        Interface = addTabInterface(i + 1);
        Interface.id = i + 1;
        Interface.parentID = 5608;
        Interface.type = 5;
        Interface.atActionType = 0;
        Interface.contentType = 0;
        Interface.opacity = 0;
        Interface.disabledSprite = SpriteLoader.sprites[spriteOn];
        if (spriteOff != -1) Interface.enabledSprite = SpriteLoader.sprites[spriteOff];
        Interface.width = 34;
        Interface.height = 34;
        Interface.valueCompareType = new int[1];
        Interface.requiredValues = new int[1];
        Interface.valueCompareType[0] = 2;
        Interface.requiredValues[0] = requiredValues + 1;
        Interface.valueIndexArray = new int[1][3];
        Interface.valueIndexArray[0][0] = 2;
        Interface.valueIndexArray[0][1] = 5;
        Interface.valueIndexArray[0][2] = 0;
    }

    /**
     * Staff tab with help request
     *
     * @param tda
     * @author Zenyte
     */
    public static void staffTabInterface(TextDrawingArea[] tda) {
        RSInterface tab = addTabInterface(46343);
        RSInterface scroll = addTabInterface(32385);
        addText(32386, "Staff Members list", fonts, 2, 16750623, true, true);
        addText(32387, "Go Back", fonts, 3, 16750623, true, true);
        addHoverButtonWSpriteLoader(32388, 726, 150, 35, "Go Back", 205, 32389, 1);
        addHoveredImageWSpriteLoader(32389, 727, 150, 35, 32390);
        addText(32394, "Click a staff members name to", fonts, 0, 16750623, true, true);
        addText(32395, "request help.", fonts, 0, 16750623, true, true);

        addSpriteLoader(32391, 722);
        addSpriteLoader(32392, 723);
        addSpriteLoader(32393, 722);

        scroll.totalChildren(50);
        scroll.width = 174;
        scroll.height = 164;
        scroll.scrollMax = 1000;
        tab.totalChildren(11);

        tab.child(0, 32386, 94, 5);// title
        tab.child(1, 32392, 0, 25);// bg
        tab.child(2, 32391, 0, 25);// first line
        tab.child(3, 32393, 0, 193);// middle line
        tab.child(4, 32393, 0, 250);// bottom line
        tab.child(5, 32385, 0, 28);// scroll
        tab.child(6, 32388, 15, 205);
        tab.child(7, 32389, 15, 205);
        tab.child(8, 32387, 90, 215);
        tab.child(9, 32394, 87, 170);
        tab.child(10, 32395, 94, 180);

        int child = 0;
        int y = 3;
        for (int i = 0; i < 50; i++) {
            scroll.child(child + i, 32410 + i, 15, y);
            y += 16;
            addClickableText(32410 + i, "", "Select", fonts, 1, 0xFF8900, 130, 13);
            // addText(32410 + i, "Staff - " + (i + 1), fonts, 1, 16750623,
            // false, true);
        }
    }

    public static void prayerTab(TextDrawingArea[] tda) {
        RSInterface rsinterface = addInterface(5608);
        int i = 0;
        int j = 0;
        byte byte0 = 10;
        byte byte1 = 50;
        byte byte2 = 10;
        byte byte3 = 86;
        byte byte4 = 10;
        byte byte5 = 122;
        byte byte6 = 10;
        char c = '\237';
        byte byte7 = 10;
        byte byte8 = 86;
        int k = 1;
        byte byte9 = 52;
        addText(687, "", 0xff981f, false, true, -1, tda, 1);
        addHDSprite(25105, 481, 481);
        addPrayerWithTooltip(25000, 0, 83, 0, j, 25052, "Activate @lre@Thick Skin");
        j++;
        addPrayerWithTooltip(25002, 0, 84, 3, j, 25054, "Activate @lre@Burst of Strength");
        j++;
        addPrayerWithTooltip(25004, 0, 85, 6, j, 25056, "Activate @lre@Clarity of Thought");
        j++;
        addPrayerWithTooltip(25006, 0, 601, 7, j, 25058, "Activate @lre@Sharp Eye");
        j++;
        addPrayerWithTooltip(25008, 0, 602, 8, j, 25060, "Activate @lre@Mystic Will");
        j++;
        addPrayerWithTooltip(25010, 0, 86, 9, j, 25062, "Activate @lre@Rock Skin");
        j++;
        addPrayerWithTooltip(25012, 0, 87, 12, j, 25064, "Activate @lre@Superhuman Strength");
        j++;
        addPrayerWithTooltip(25014, 0, 88, 15, j, 25066, "Activate @lre@Improved Reflexes");
        j++;
        addPrayerWithTooltip(25016, 0, 89, 18, j, 25068, "Activate @lre@Rapid Restore");
        j++;
        addPrayerWithTooltip(25018, 0, 90, 21, j, 25070, "Activate @lre@Rapid Heal");
        j++;
        addPrayerWithTooltip(25020, 0, 91, 24, j, 25072, "Activate @lre@Protect Item");
        j++;
        addPrayerWithTooltip(25022, 0, 603, 25, j, 25074, "Activate @lre@Hawk Eye");
        j++;
        addPrayerWithTooltip(25024, 0, 604, 26, j, 25076, "Activate @lre@Mystic Lore");
        j++;
        addPrayerWithTooltip(25026, 0, 92, 27, j, 25078, "Activate @lre@Steel Skin");
        j++;
        addPrayerWithTooltip(25028, 0, 93, 30, j, 25080, "Activate @lre@Ultimate Strength");
        j++;
        addPrayerWithTooltip(25030, 0, 94, 33, j, 25082, "Activate @lre@Incredible Reflexes");
        j++;
        addPrayerWithTooltip(25032, 0, 95, 36, j, 25084, "Activate @lre@Protect from Magic");
        j++;
        addPrayerWithTooltip(25034, 0, 96, 39, j, 25086, "Activate @lre@Protect from Missles");
        j++;
        addPrayerWithTooltip(25036, 0, 97, 42, j, 25088, "Activate @lre@Protect from Melee");
        j++;
        addPrayerWithTooltip(25038, 0, 605, 43, j, 25090, "Activate @lre@Eagle Eye");
        j++;
        addPrayerWithTooltip(25040, 0, 606, 44, j, 25092, "Activate @lre@Mystic Might");
        j++;
        addPrayerWithTooltip(25042, 0, 98, 45, j, 25094, "Activate @lre@Retribution");
        j++;
        addPrayerWithTooltip(25044, 0, 99, 48, j, 25096, "Activate @lre@Redemption");
        j++;
        addPrayerWithTooltip(25046, 0, 100, 51, j, 25098, "Activate @lre@Smite");
        j++;
        addPrayerWithTooltip(25048, 0, 607, 59, j, 25100, "Activate @lre@Chivalry");
        j++;
        addPrayerWithTooltip(25050, 0, 608, 69, j, 25102, "Activate @lre@Piety");
        j++;
        drawTooltip(25052, "Level 01\nThick Skin\nIncreases your Defence by 5%");
        drawTooltip(25054, "Level 04\nBurst of Strength\nIncreases your Strength by 5%");
        drawTooltip(25056, "Level 07\nClarity of Thought\nIncreases your Attack by 5%");
        drawTooltip(25058, "Level 08\nSharp Eye\nIncreases your Ranged by 5%");
        drawTooltip(25060, "Level 09\nMystic Will\nIncreases your Magic by 5%");
        drawTooltip(25062, "Level 10\nRock Skin\nIncreases your Defence by 10%");
        drawTooltip(25064, "Level 13\nSuperhuman Strength\nIncreases your Strength by 10%");
        drawTooltip(25066, "Level 16\nImproved Reflexes\nIncreases your Attack by 10%");
        drawTooltip(25068, "Level 19\nRapid Restore\n2x restore rate for all stats\nexcept Hitpoints, Summon" + "ing\nand Prayer");
        drawTooltip(25070, "Level 22\nRapid Heal\n2x restore rate for the\nHitpoints stat");
        drawTooltip(25072, "Level 25\nProtect Item\nKeep 1 extra item if you die");
        drawTooltip(25074, "Level 26\nHawk Eye\nIncreases your Ranged by 10%");
        drawTooltip(25076, "Level 27\nMystic Lore\nIncreases your Magic by 10%");
        drawTooltip(25078, "Level 28\nSteel Skin\nIncreases your Defence by 15%");
        drawTooltip(25080, "Level 31\nUltimate Strength\nIncreases your Strength by 15%");
        drawTooltip(25082, "Level 34\nIncredible Reflexes\nIncreases your Attack by 15%");
        drawTooltip(25084, "Level 37\nProtect from Magic\nProtection from magical attacks");
        drawTooltip(25086, "Level 40\nProtect from Missles\nProtection from ranged attacks");
        drawTooltip(25088, "Level 43\nProtect from Melee\nProtection from melee attacks");
        drawTooltip(25090, "Level 44\nEagle Eye\nIncreases your Ranged by 15%");
        drawTooltip(25092, "Level 45\nMystic Might\nIncreases your Magic by 15%");
        drawTooltip(25094, "Level 46\nRetribution\nInflicts damage to nearby\ntargets if you die");
        drawTooltip(25096, "Level 49\nRedemption\nHeals you when damaged\nand Hitpoints falls\nbelow 10%");
        drawTooltip(25098, "Level 52\nSmite\n1/4 of damage dealt is\nalso removed from\nopponent's Prayer");
        drawTooltip(25100, "Level 60\nChivalry\nIncreases your Defence by 20%,\nStrength by 18%, and Attack " + "by\n15%");
        drawTooltip(25102, "Level 70\nPiety\nIncreases your Defence by 25%,\nStrength by 23%, and Attack by\n" + "20%");
        setChildren(80, rsinterface);
        setBounds(687, 85, 241, i, rsinterface);
        i++;
        setBounds(25105, 65, 241, i, rsinterface);
        i++;
        setBounds(25000, 2, 5, i, rsinterface);
        i++;
        setBounds(25001, 5, 8, i, rsinterface);
        i++;
        setBounds(25002, 40, 5, i, rsinterface);
        i++;
        setBounds(25003, 44, 8, i, rsinterface);
        i++;
        setBounds(25004, 76, 5, i, rsinterface);
        i++;
        setBounds(25005, 79, 11, i, rsinterface);
        i++;
        setBounds(25006, 113, 5, i, rsinterface);
        i++;
        setBounds(25007, 116, 10, i, rsinterface);
        i++;
        setBounds(25008, 150, 5, i, rsinterface);
        i++;
        setBounds(25009, 153, 9, i, rsinterface);
        i++;
        setBounds(25010, 2, 45, i, rsinterface);
        i++;
        setBounds(25011, 5, 48, i, rsinterface);
        i++;
        setBounds(25012, 39, 45, i, rsinterface);
        i++;
        setBounds(25013, 44, 47, i, rsinterface);
        i++;
        setBounds(25014, 76, 45, i, rsinterface);
        i++;
        setBounds(25015, 79, 49, i, rsinterface);
        i++;
        setBounds(25016, 113, 45, i, rsinterface);
        i++;
        setBounds(25017, 116, 50, i, rsinterface);
        i++;
        setBounds(25018, 151, 45, i, rsinterface);
        i++;
        setBounds(25019, 154, 50, i, rsinterface);
        i++;
        setBounds(25020, 2, 82, i, rsinterface);
        i++;
        setBounds(25021, 4, 84, i, rsinterface);
        i++;
        setBounds(25022, 40, 82, i, rsinterface);
        i++;
        setBounds(25023, 44, 87, i, rsinterface);
        i++;
        setBounds(25024, 77, 82, i, rsinterface);
        i++;
        setBounds(25025, 81, 85, i, rsinterface);
        i++;
        setBounds(25026, 114, 83, i, rsinterface);
        i++;
        setBounds(25027, 117, 85, i, rsinterface);
        i++;
        setBounds(25028, 153, 83, i, rsinterface);
        i++;
        setBounds(25029, 156, 87, i, rsinterface);
        i++;
        setBounds(25030, 2, 120, i, rsinterface);
        i++;
        setBounds(25031, 5, 125, i, rsinterface);
        i++;
        setBounds(25032, 40, 120, i, rsinterface);
        i++;
        setBounds(25033, 43, 124, i, rsinterface);
        i++;
        setBounds(25034, 78, 120, i, rsinterface);
        i++;
        setBounds(25035, 83, 124, i, rsinterface);
        i++;
        setBounds(25036, 114, 120, i, rsinterface);
        i++;
        setBounds(25037, 115, 121, i, rsinterface);
        i++;
        setBounds(25038, 151, 120, i, rsinterface);
        i++;
        setBounds(25039, 154, 124, i, rsinterface);
        i++;
        setBounds(25040, 2, 158, i, rsinterface);
        i++;
        setBounds(25041, 5, 160, i, rsinterface);
        i++;
        setBounds(25042, 39, 158, i, rsinterface);
        i++;
        setBounds(25043, 41, 158, i, rsinterface);
        i++;
        setBounds(25044, 76, 158, i, rsinterface);
        i++;
        setBounds(25045, 79, 163, i, rsinterface);
        i++;
        setBounds(25046, 114, 158, i, rsinterface);
        i++;
        setBounds(25047, 116, 158, i, rsinterface);
        i++;
        setBounds(25048, 153, 158, i, rsinterface);
        i++;
        setBounds(25049, 161, 160, i, rsinterface);
        i++;
        setBounds(25050, 2, 196, i, rsinterface);
        i++;
        setBounds(25051, 4, 207, i, rsinterface);
        setBoundry(++i, 25052, byte0 - 2, byte1, rsinterface);
        setBoundry(++i, 25054, byte0 - 5, byte1, rsinterface);
        setBoundry(++i, 25056, byte0, byte1, rsinterface);
        setBoundry(++i, 25058, byte0, byte1, rsinterface);
        setBoundry(++i, 25060, byte0, byte1, rsinterface);
        setBoundry(++i, 25062, byte2 - 9, byte3, rsinterface);
        setBoundry(++i, 25064, byte2 - 11, byte3, rsinterface);
        setBoundry(++i, 25066, byte2, byte3, rsinterface);
        setBoundry(++i, 25068, byte2, byte3, rsinterface);
        setBoundry(++i, 25070, byte2 + 25, byte3, rsinterface);
        setBoundry(++i, 25072, byte4, byte5, rsinterface);
        setBoundry(++i, 25074, byte4 - 2, byte5, rsinterface);
        setBoundry(++i, 25076, byte4, byte5, rsinterface);
        setBoundry(++i, 25078, byte4 - 7, byte5, rsinterface);
        setBoundry(++i, 25080, byte4 - 10, byte5, rsinterface);
        setBoundry(++i, 25082, byte6, c, rsinterface);
        setBoundry(++i, 25084, byte6 - 8, c, rsinterface);
        setBoundry(++i, 25086, byte6 - 7, c, rsinterface);
        setBoundry(++i, 25088, byte6 - 2, c, rsinterface);
        setBoundry(++i, 25090, byte6 - 2, c, rsinterface);
        setBoundry(++i, 25092, byte7, byte8, rsinterface);
        setBoundry(++i, 25094, byte7, byte8 - 20, rsinterface);
        setBoundry(++i, 25096, byte7, byte8 - 25, rsinterface);
        setBoundry(++i, 25098, byte7 + 15, byte8 - 25, rsinterface);
        setBoundry(++i, 25100, byte7 - 12, byte8 - 20, rsinterface);
        setBoundry(++i, 25102, k - 2, byte9, rsinterface);
        i++;
    }

    public static void setBoundry(int frame, int ID, int X, int Y, RSInterface RSInterface) {
        RSInterface.children[frame] = ID;
        RSInterface.childX[frame] = X;
        RSInterface.childY[frame] = Y;
    }

    public static void addText(int id, String text, TextDrawingArea tda[], int idx, int color, boolean center, boolean shadow, int contentType, int actionType) {
        RSInterface tab = addTabInterface(id);
        tab.parentID = id;
        tab.id = id;
        tab.type = 4;
        tab.atActionType = actionType;
        tab.width = 0;
        tab.height = 11;
        tab.contentType = contentType;
        tab.centerText = center;
        tab.shadowed = shadow;
        tab.textDrawingAreas = tda[idx];
        tab.message = text;
        tab.enabledMessage = "";
        tab.disabledColor = color;
        tab.enabledColor = 0;
        tab.disabledMouseOverColor = 0;
        tab.enabledMouseOverColor = 0;
    }

    public static void addNpc(int ID, int npcId) {
        RSInterface rs = interfaceCache[ID] = new RSInterface();
        rs.id = ID;
        rs.parentID = ID;
        rs.type = 6;
        rs.atActionType = 0;
        rs.contentType = 3291;
        rs.width = 136;
        rs.height = 168;
        rs.hoverType = 0;
        rs.modelZoom = 1400;
        rs.modelRotation1 = 150;
        rs.modelRotation2 = 0;
        rs.disabledAnimationId = -1;
        rs.enabledAnimationId = -1;
        rs.entityDisplay = npcId;
    }

    public static RSInterface addInterface(int id) {
        RSInterface rsi = interfaceCache[id] = new RSInterface();
        rsi.id = id;
        rsi.parentID = id;
        rsi.width = 512;
        rsi.height = 334;
        return rsi;
    }

    public static void addText(int id, String text, TextDrawingArea tda[], int idx, int color, boolean centered) {
        RSInterface rsi = interfaceCache[id] = new RSInterface();
        if (centered) rsi.centerText = true;
        rsi.shadowed = true;
        rsi.textDrawingAreas = tda[idx];
        rsi.message = text;
        rsi.disabledColor = color;
        rsi.id = id;
        rsi.type = 4;
    }

    public static void textColor(int id, int color) {
        RSInterface rsi = interfaceCache[id];
        rsi.disabledColor = color;
    }

    public static void textSize(int id, TextDrawingArea tda[], int idx) {
        RSInterface rsi = interfaceCache[id];
        rsi.textDrawingAreas = tda[idx];
    }

    public static void addCacheSprite(int id, int sprite1, int sprite2, String sprites) {
        RSInterface rsi = interfaceCache[id] = new RSInterface();
        rsi.disabledSprite = getSprite(sprite1, cacheArchive, sprites);
        rsi.enabledSprite = getSprite(sprite2, cacheArchive, sprites);
        rsi.parentID = id;
        rsi.id = id;
        rsi.type = 5;
    }

    public static void sprite1(int id, int sprite, int dum) {
        RSInterface class9 = interfaceCache[id];
        class9.disabledSpriteId = sprite;
    }

    public static void addActionButton(int id, int sprite, int sprite2, int width, int height, String s, int dum) {
        RSInterface rsi = interfaceCache[id] = new RSInterface();
        rsi.disabledSpriteId = sprite;
        if (sprite2 == sprite) rsi.enabledSpriteId = sprite + 1;
        else rsi.enabledSpriteId = sprite2;
        rsi.tooltip = s;
        rsi.contentType = 0;
        rsi.atActionType = 1;
        rsi.width = width;
        rsi.hoverType = 52;
        rsi.parentID = id;
        rsi.id = id;
        rsi.type = 5;
        rsi.height = height;
    }

    public static void addToggleButton(int id, int sprite, int setconfig, int width, int height, String s) {
        RSInterface rsi = addInterface(id);
        rsi.disabledSpriteId = sprite;
        rsi.enabledSpriteId = sprite + 1;
        rsi.requiredValues = new int[1];
        rsi.requiredValues[0] = 1;
        rsi.valueCompareType = new int[1];
        rsi.valueCompareType[0] = 1;
        rsi.valueIndexArray = new int[1][3];
        rsi.valueIndexArray[0][0] = 5;
        rsi.valueIndexArray[0][1] = setconfig;
        rsi.valueIndexArray[0][2] = 0;
        rsi.atActionType = 4;
        rsi.width = width;
        rsi.hoverType = -1;
        rsi.parentID = id;
        rsi.id = id;
        rsi.type = 5;
        rsi.height = height;
        rsi.tooltip = s;
    }

    public static void removeSomething(int id) {
        RSInterface rsi = interfaceCache[id] = new RSInterface();
    }

    public static void removeSomething(int[] id) {
        for (int i : id) {
            removeSomething(i);
        }
    }

    public static void Sidebar0(TextDrawingArea[] tda) {
        /*
         * Sidebar0a(id, id2, id3, "text1", "text2", "text3", "text4", str1x,
         * str1y, str2x, str2y, str3x, str3y, str4x, str4y, img1x, img1y, img2x,
         * img2y, img3x, img3y, img4x, img4y, tda);
         */
        Sidebar0a(1698, 1701, 7499, "Chop", "Hack", "Smash", "Block", 42, 75, 127, 75, 39, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
        Sidebar0a(2276, 2279, 7574, "Stab", "Lunge", "Slash", "Block", 43, 75, 124, 75, 41, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
        Sidebar0a(2423, 2426, 7599, "Chop", "Slash", "Lunge", "Block", 42, 75, 125, 75, 40, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
        Sidebar0a(3796, 3799, 7624, "Pound", "Pummel", "Spike", "Block", 39, 75, 121, 75, 41, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
        Sidebar0a(4679, 4682, 7674, "Lunge", "Swipe", "Pound", "Block", 40, 75, 124, 75, 39, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
        Sidebar0a(4705, 4708, 7699, "Chop", "Slash", "Smash", "Block", 42, 75, 125, 75, 39, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
        Sidebar0a(5570, 5573, 7724, "Spike", "Impale", "Smash", "Block", 41, 75, 123, 75, 39, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
        Sidebar0a(7762, 7765, 7800, "Chop", "Slash", "Lunge", "Block", 42, 75, 125, 75, 40, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
        /*
         * Sidebar0b(id, id2, "text1", "text2", "text3", "text4", str1x, str1y,
         * str2x, str2y, str3x, str3y, str4x, str4y, img1x, img1y, img2x, img2y,
         * img3x, img3y, img4x, img4y, tda);
         */
        Sidebar0b(776, 779, "Reap", "Chop", "Jab", "Block", 42, 75, 126, 75, 46, 128, 125, 128, 122, 103, 122, 50, 40, 103, 40, 50, tda);
        /*
         * Sidebar0c(id, id2, id3, "text1", "text2", "text3", str1x, str1y,
         * str2x, str2y, str3x, str3y, img1x, img1y, img2x, img2y, img3x, img3y,
         * tda);
         */
        Sidebar0c(425, 428, 7474, "Pound", "Pummel", "Block", 39, 75, 121, 75, 42, 128, 40, 103, 40, 50, 122, 50, tda);
        Sidebar0c(1749, 1752, 7524, "Accurate", "Rapid", "Longrange", 33, 75, 125, 75, 29, 128, 40, 103, 40, 50, 122, 50, tda);
        Sidebar0c(1764, 1767, 7549, "Accurate", "Rapid", "Longrange", 33, 75, 125, 75, 29, 128, 40, 103, 40, 50, 122, 50, tda);
        Sidebar0c(4446, 4449, 7649, "Accurate", "Rapid", "Longrange", 33, 75, 125, 75, 29, 128, 40, 103, 40, 50, 122, 50, tda);
        Sidebar0c(5855, 5857, 7749, "Punch", "Kick", "Block", 40, 75, 129, 75, 42, 128, 40, 50, 122, 50, 40, 103, tda);
        Sidebar0c(6103, 6132, 6117, "Bash", "Pound", "Block", 43, 75, 124, 75, 42, 128, 40, 103, 40, 50, 122, 50, tda);
        Sidebar0c(8460, 8463, 8493, "Jab", "Swipe", "Fend", 46, 75, 124, 75, 43, 128, 40, 103, 40, 50, 122, 50, tda);
        Sidebar0c(12290, 12293, 12323, "Flick", "Lash", "Deflect", 44, 75, 127, 75, 36, 128, 40, 50, 40, 103, 122, 50, tda);
        /*
         * Sidebar0d(id, id2, "text1", "text2", "text3", str1x, str1y, str2x,
         * str2y, str3x, str3y, img1x, img1y, img2x, img2y, img3x, img3y, tda);
         */
        Sidebar0d(328, 331, "Bash", "Pound", "Focus", 42, 66, 39, 101, 41, 136, 40, 120, 40, 50, 40, 85, tda);

        RSInterface rsi = addInterface(19300);
        addToggleButton(150, 312, 172, 150, 44, "Auto Retaliate");
        rsi.totalChildren(22, 22, 22);
        rsi.child(0, 19000, 92, 26); // Combat level
        rsi.child(2, 19001, 500, 500); // Constitution updating
        rsi.child(1, 150, 21, 153); // auto retaliate
        rsi.child(3, 34555, 500, 500);
        rsi.child(4, 34556, 500, 500);
        rsi.child(4, 19001, 500, 500);
        rsi.child(5, 32002, 500, 500);
        rsi.child(6, 32003, 500, 500);
        rsi.child(7, 32004, 500, 500);
        rsi.child(8, 32005, 500, 500);
        rsi.child(9, 32006, 500, 500);
        rsi.child(10, 33001, 500, 500);
        rsi.child(11, 33002, 500, 500);
        rsi.child(12, 33003, 500, 500);
        rsi.child(13, 33004, 500, 500);
        rsi.child(14, 33005, 500, 500);
        rsi.child(15, 33006, 500, 500);
        rsi.child(16, 33101, 500, 500);
        rsi.child(17, 33102, 500, 500);
        rsi.child(18, 33103, 500, 500);
        rsi.child(19, 33104, 500, 500);
        rsi.child(20, 33105, 500, 500);
        rsi.child(21, 33106, 500, 500);

        addText(34555, "", tda, 0, 0xffffff, true, false);
        addText(34556, "", tda, 0, 0xffffff, true, false);
        addText(19000, "Combat level:", tda, 0, 0xff981f, true, false);
        addText(19001, "lolquoi", tda, 0, 0xff981f, true, false);
        addText(32001, "", tda, 0, 0xff981f, true, false);
        addText(32002, "", tda, 0, 0xff981f, true, false);
        addText(32003, "", tda, 0, 0xff981f, true, false);
        addText(32004, "", tda, 0, 0xff981f, true, false);
        addText(32005, "", tda, 0, 0xff981f, true, false);
        addText(32006, "", tda, 0, 0xff981f, true, false);
        addText(33001, "", tda, 0, 0xff981f, true, false);
        addText(33002, "", tda, 0, 0xff981f, true, false);
        addText(33003, "", tda, 0, 0xff981f, true, false);
        addText(33004, "", tda, 0, 0xff981f, true, false);
        addText(33005, "", tda, 0, 0xff981f, true, false);
        addText(33006, "", tda, 0, 0xff981f, true, false);
        addText(33101, "", tda, 0, 0xff981f, true, false);
        addText(33102, "", tda, 0, 0xff981f, true, false);
        addText(33103, "", tda, 0, 0xff981f, true, false);
        addText(33104, "", tda, 0, 0xff981f, true, false);
        addText(33105, "", tda, 0, 0xff981f, true, false);
        addText(33106, "", tda, 0, 0xff981f, true, false);
    }

    public static void Sidebar0a(int id, int id2, int id3, String text1, String text2, String text3, String text4, int str1x, int str1y, int str2x, int str2y, int str3x, int str3y, int str4x, int str4y, int img1x, int img1y, int img2x, int img2y, int img3x, int img3y, int img4x, int img4y, TextDrawingArea[] tda) // 4button
    // spec
    {
        RSInterface rsi = addInterface(id); // 2423
        /* addText(ID, "Text", tda, Size, Colour, Centered); */
        addText(id2, "-2", tda, 3, 0xff981f, true); // 2426
        addText(id2 + 11, text1, tda, 0, 0xff981f, false);
        addText(id2 + 12, text2, tda, 0, 0xff981f, false);
        addText(id2 + 13, text3, tda, 0, 0xff981f, false);
        addText(id2 + 14, text4, tda, 0, 0xff981f, false);
        /* specialBar(ID); */
        rsi.specialBar(id3); // 7599

        rsi.width = 190;
        rsi.height = 261;

        int last = 15;
        int frame = 0;
        rsi.totalChildren(last, last, last);

        rsi.child(frame, id2 + 3, 21, 46);
        frame++; // 2429
        rsi.child(frame, id2 + 4, 104, 99);
        frame++; // 2430
        rsi.child(frame, id2 + 5, 21, 99);
        frame++; // 2431
        rsi.child(frame, id2 + 6, 105, 46);
        frame++; // 2432

        rsi.child(frame, id2 + 7, img1x, img1y);
        frame++; // bottomright 2433
        rsi.child(frame, id2 + 8, img2x, img2y);
        frame++; // topleft 2434
        rsi.child(frame, id2 + 9, img3x, img3y);
        frame++; // bottomleft 2435
        rsi.child(frame, id2 + 10, img4x, img4y);
        frame++; // topright 2436

        rsi.child(frame, id2 + 11, str1x, str1y);
        frame++; // chop 2437
        rsi.child(frame, id2 + 12, str2x, str2y);
        frame++; // slash 2438
        rsi.child(frame, id2 + 13, str3x, str3y);
        frame++; // lunge 2439
        rsi.child(frame, id2 + 14, str4x, str4y);
        frame++; // block 2440

        rsi.child(frame, 19300, 0, 0);
        frame++; // stuffs
        rsi.child(frame, id2, 94, 4);
        frame++; // weapon 2426
        rsi.child(frame, id3, 21, 205);
        frame++; // special attack 7599

        for (int i = id2 + 3; i < id2 + 7; i++) { // 2429 - 2433
            rsi = interfaceCache[i];
            rsi.disabledSpriteId = 299;
            rsi.enabledSpriteId = 300;
            rsi.width = 68;
            rsi.height = 44;
        }
    }

    public static void Sidebar0b(int id, int id2, String text1, String text2, String text3, String text4, int str1x, int str1y, int str2x, int str2y, int str3x, int str3y, int str4x, int str4y, int img1x, int img1y, int img2x, int img2y, int img3x, int img3y, int img4x, int img4y, TextDrawingArea[] tda) // 4button
    // nospec
    {
        RSInterface rsi = addInterface(id); // 2423
        /* addText(ID, "Text", tda, Size, Colour, Centered); */
        addText(id2, "-2", tda, 3, 0xff981f, true); // 2426
        addText(id2 + 11, text1, tda, 0, 0xff981f, false);
        addText(id2 + 12, text2, tda, 0, 0xff981f, false);
        addText(id2 + 13, text3, tda, 0, 0xff981f, false);
        addText(id2 + 14, text4, tda, 0, 0xff981f, false);

        rsi.width = 190;
        rsi.height = 261;

        int last = 14;
        int frame = 0;
        rsi.totalChildren(last, last, last);

        rsi.child(frame, id2 + 3, 21, 46);
        frame++; // 2429
        rsi.child(frame, id2 + 4, 104, 99);
        frame++; // 2430
        rsi.child(frame, id2 + 5, 21, 99);
        frame++; // 2431
        rsi.child(frame, id2 + 6, 105, 46);
        frame++; // 2432

        rsi.child(frame, id2 + 7, img1x, img1y);
        frame++; // bottomright 2433
        rsi.child(frame, id2 + 8, img2x, img2y);
        frame++; // topleft 2434
        rsi.child(frame, id2 + 9, img3x, img3y);
        frame++; // bottomleft 2435
        rsi.child(frame, id2 + 10, img4x, img4y);
        frame++; // topright 2436

        rsi.child(frame, id2 + 11, str1x, str1y);
        frame++; // chop 2437
        rsi.child(frame, id2 + 12, str2x, str2y);
        frame++; // slash 2438
        rsi.child(frame, id2 + 13, str3x, str3y);
        frame++; // lunge 2439
        rsi.child(frame, id2 + 14, str4x, str4y);
        frame++; // block 2440

        rsi.child(frame, 19300, 0, 0);
        frame++; // stuffs
        rsi.child(frame, id2, 94, 4);
        frame++; // weapon 2426

        for (int i = id2 + 3; i < id2 + 7; i++) { // 2429 - 2433
            rsi = interfaceCache[i];
            rsi.disabledSpriteId = 299;
            rsi.enabledSpriteId = 300;
            rsi.width = 68;
            rsi.height = 44;
        }
    }

    public static void Sidebar0c(int id, int id2, int id3, String text1, String text2, String text3, int str1x, int str1y, int str2x, int str2y, int str3x, int str3y, int img1x, int img1y, int img2x, int img2y, int img3x, int img3y, TextDrawingArea[] tda) // 3button
    // spec
    {
        RSInterface rsi = addInterface(id); // 2423
        /* addText(ID, "Text", tda, Size, Colour, Centered); */
        addText(id2, "-2", tda, 3, 0xff981f, true); // 2426
        addText(id2 + 9, text1, tda, 0, 0xff981f, false);
        addText(id2 + 10, text2, tda, 0, 0xff981f, false);
        addText(id2 + 11, text3, tda, 0, 0xff981f, false);
        /* specialBar(ID); */
        rsi.specialBar(id3); // 7599

        rsi.width = 190;
        rsi.height = 261;

        int last = 12;
        int frame = 0;
        rsi.totalChildren(last, last, last);

        rsi.child(frame, id2 + 3, 21, 99);
        frame++;
        rsi.child(frame, id2 + 4, 105, 46);
        frame++;
        rsi.child(frame, id2 + 5, 21, 46);
        frame++;

        rsi.child(frame, id2 + 6, img1x, img1y);
        frame++; // topleft
        rsi.child(frame, id2 + 7, img2x, img2y);
        frame++; // bottomleft
        rsi.child(frame, id2 + 8, img3x, img3y);
        frame++; // topright

        rsi.child(frame, id2 + 9, str1x, str1y);
        frame++; // chop
        rsi.child(frame, id2 + 10, str2x, str2y);
        frame++; // slash
        rsi.child(frame, id2 + 11, str3x, str3y);
        frame++; // lunge

        rsi.child(frame, 19300, 0, 0);
        frame++; // stuffs
        rsi.child(frame, id2, 94, 4);
        frame++; // weapon
        rsi.child(frame, id3, 21, 205);
        frame++; // special attack 7599

        for (int i = id2 + 3; i < id2 + 6; i++) {
            rsi = interfaceCache[i];
            rsi.disabledSpriteId = 299;
            rsi.enabledSpriteId = 300;
            rsi.width = 68;
            rsi.height = 44;
        }
    }

    public static void Sidebar0d(int id, int id2, String text1, String text2, String text3, int str1x, int str1y, int str2x, int str2y, int str3x, int str3y, int img1x, int img1y, int img2x, int img2y, int img3x, int img3y, TextDrawingArea[] tda) // 3button
    // nospec
    // (magic intf)
    {
        RSInterface rsi = addInterface(id); // 2423
        /* addText(ID, "Text", tda, Size, Colour, Centered); */
        addText(id2, "-2", tda, 3, 0xff981f, true); // 2426
        addText(id2 + 9, text1, tda, 0, 0xff981f, false);
        addText(id2 + 10, text2, tda, 0, 0xff981f, false);
        addText(id2 + 11, text3, tda, 0, 0xff981f, false);

        // addText(353, "Spell", tda, 0, 0xff981f, false);
        removeSomething(353);
        addText(354, "Spell", tda, 0, 0xff981f, false);

        addCacheSprite(337, 19, 0, "combaticons");
        addCacheSprite(338, 13, 0, "combaticons2");
        addCacheSprite(339, 14, 0, "combaticons2");

        removeSomething(349);
        addToggleButton(350, 314, 108, 68, 44, "Select");

        rsi.width = 190;
        rsi.height = 261;

        int last = 15;
        int frame = 0;
        rsi.totalChildren(last, last, last);

        rsi.child(frame, id2 + 3, 20, 115);
        frame++;
        rsi.child(frame, id2 + 4, 20, 80);
        frame++;
        rsi.child(frame, id2 + 5, 20, 45);
        frame++;

        rsi.child(frame, id2 + 6, img1x, img1y);
        frame++; // topleft
        rsi.child(frame, id2 + 7, img2x, img2y);
        frame++; // bottomleft
        rsi.child(frame, id2 + 8, img3x, img3y);
        frame++; // topright

        rsi.child(frame, id2 + 9, str1x, str1y);
        frame++; // bash
        rsi.child(frame, id2 + 10, str2x, str2y);
        frame++; // pound
        rsi.child(frame, id2 + 11, str3x, str3y);
        frame++; // focus

        rsi.child(frame, 349, 105, 46);
        frame++; // spell1
        rsi.child(frame, 350, 104, 106);
        frame++; // spell2

        rsi.child(frame, 353, 125, 74);
        frame++; // spell
        rsi.child(frame, 354, 125, 134);
        frame++; // spell

        rsi.child(frame, 19300, 0, 0);
        frame++; // stuffs
        rsi.child(frame, id2, 94, 4);
        frame++; // weapon
    }

    public static void EquipmentTab(TextDrawingArea[] tda) {
        RSInterface tab = interfaceCache[1644];

        addHoverButton(15201, 574, 574, 40, 40, "Show Equipment Screen", 0, 15202, 1);
        addHoveredButton(15202, 577, 577, 40, 40, 15203);
        addHoverButton(15204, 575, 575, 40, 40, "Items Kept on Death", 0, 15205, 1);
        addHoveredButton(15205, 576, 576, 40, 40, 15206);

        tab.child(23, 15201, 21, 210);
        // tab.child(1, 15226, 95, 250);
        tab.child(24, 15202, 21, 210);
        tab.child(25, 15204, 129, 210);
        tab.child(26, 15205, 129, 210);
        RSInterface Interface = addTabInterface(27650);
        removeSomething(15201);
        removeSomething(15202);
        removeSomething(15203);
        removeSomething(15204);
        removeSomething(15205);
        removeSomething(15206);
        addButtonWSpriteLoader(27651, 852, "Show Equipment Stats", 41, 40);
        //addTooltip(27659, "Show equipment stats");
        addButtonWSpriteLoader(27652, 853, "Save Overrides", 41, 40);
        //addTooltip(27655, "Open the Price checker");
        addButtonWSpriteLoader(27654, 854, "Load Preset", 41, 40);
        //addTooltip(27657, "Show items kept on death");
        addButtonWSpriteLoader(27658, 855, "Save Preset", 41, 40);
        //addTooltip(27660, "Lock or unlock exp");
        addTabInterface(21341);
        addTabInterface(23032);
        setChildren(5, Interface);
        setBounds(27651, 8, 205, 0, Interface);
        setBounds(27652, 53, 205, 1, Interface);
        setBounds(27654, 98, 205, 2, Interface);
        setBounds(27658, 143, 205, 3, Interface);
        setBounds(1644, 0, 0, 4, Interface);
        /* tab.child(3, 15109, 41+39+30, 212); */
    }

    public static void prayerTab() {

        RSInterface prayerTab = interfaceCache[5608];
        int[] oldChildren = prayerTab.children;
        int[] oldChildrenX = prayerTab.childX;
        int[] oldChildrenY = prayerTab.childY;

        prayerTab.totalChildren(88);


        addPrayer(18018, 0, 708, 71, 942, -1, "Toggle@or2@ Rigour");
        addHoverButton(18021, "", 1, 35, 35, "Toggle@or2@ Rigour", -1, 18022, 1);
        addTooltip(18022, "Level 80\nRigour\nIncreases your Ranging by 22%\nand Defence by 25%");

        addPrayer(18025, 0, 709, 72, 943, -1, "Toggle@or2@ Augury");
        addHoverButton(18027, "", 1, 35, 35, "Toggle@or2@ Augury", -1, 18028, 1);
        addTooltip(18028, "Level 80\nAugury\nIncreases your Magic by 22%\nand Defence by 25%");


        prayerTab.child(80, 18018, 41, 194);
        prayerTab.child(81, 18019, 44, 198);
        prayerTab.child(82, 18021, 44, 198);
        prayerTab.child(83, 18022, 0, 52);

        prayerTab.child(84, 18025, 78, 194);
        prayerTab.child(85, 18026, 81, 198);
        prayerTab.child(86, 18027, 81, 198);
        prayerTab.child(87, 18028, 0, 52);

        for (int i = 0; i < oldChildren.length; i++) {
            prayerTab.children[i] = oldChildren[i];
            prayerTab.childX[i] = oldChildrenX[i];
            prayerTab.childY[i] = oldChildrenY[i];
            RSInterface f = interfaceCache[prayerTab.children[i]];
            if (f != null && f.tooltip != null && f.tooltip.contains("Activate"))
                f.tooltip = f.tooltip.replace("Activate", "Toggle");
        }
    }

    public static void addPrayer(int i, int configId, int configFrame, int requiredValues, int spriteOn, int spriteOff, String tooltip) {
        RSInterface tab = addTabInterface(i);
        tab.id = i;
        tab.parentID = 5608;
        tab.type = 5;
        tab.atActionType = 4;
        tab.contentType = 0;
        tab.opacity = 0;
        tab.hoverType = -1;
        tab.disabledSprite = SpriteLoader.sprites[941];
        tab.enabledSprite = new Sprite("");
        tab.width = 34;
        tab.height = 34;
        tab.valueCompareType = new int[1];
        tab.requiredValues = new int[1];
        tab.valueCompareType[0] = 1;
        tab.requiredValues[0] = configId;
        tab.valueIndexArray = new int[1][3];
        tab.valueIndexArray[0][0] = 5;
        tab.valueIndexArray[0][1] = configFrame;
        tab.valueIndexArray[0][2] = 0;
        tab.tooltip = tooltip;
        RSInterface tab2 = addTabInterface(i + 1);
        tab2.id = i + 1;
        tab2.parentID = 5608;
        tab2.type = 5;
        tab2.atActionType = 0;
        tab2.contentType = 0;
        tab2.opacity = 0;
        tab2.hoverType = -1;
        tab2.disabledSprite = SpriteLoader.sprites[spriteOn];
        if (spriteOff != -1) tab2.enabledSprite = SpriteLoader.sprites[spriteOff];
        tab2.width = 34;
        tab2.height = 34;
        tab2.valueCompareType = new int[1];
        tab2.requiredValues = new int[1];
        tab2.valueCompareType[0] = 2;
        tab2.requiredValues[0] = requiredValues + 1;
        tab2.valueIndexArray = new int[1][3];
        tab2.valueIndexArray[0][0] = 2;
        tab2.valueIndexArray[0][1] = 5;
        tab2.valueIndexArray[0][2] = 0;
        //RSInterface tab3 = addTabInterface(i + 50);
    }

    public static void note(TextDrawingArea[] tda) {
        RSInterface tab = addTabInterface(173);
        addHDSprite(17351, 136, 136);
        addHoverButton(17352, 137, 137, 200, 30, "Add note", -1, 17353, 1);
        addHoveredButton(17353, 138, 138, 200, 30, 17354);
        addHoverButton(17355, 139, 139, 200, 30, "Delete all", -1, 17356, 1);
        addHoveredButton(17356, 140, 140, 200, 30, 17357);
        interfaceCache[17352].contentType = 6650;
        interfaceCache[17353].contentType = 6650;
        interfaceCache[17354].contentType = 6650;
        addText(17800, "No notes", tda, 0, 0xffffff, false, true);
        addText(17801, "", tda, 0, 0xff981f, false, true);
        addText(17812, "", tda, 0, 0xff981f, false, true);
        addText(17813, "", tda, 0, 0xff981f, false, true);
        addText(17814, "", tda, 0, 0xff981f, false, true);
        tab.totalChildren(7);
        tab.child(0, 17351, 0, 0);
        tab.child(1, 17352, 8, 2);
        tab.child(2, 17353, 8, 2);
        tab.child(3, 17355, 165, 237);
        tab.child(4, 17356, 165, 237);
        tab.child(5, 17800, 68, 78);
        tab.child(6, 14000, 0, 27);
        tab = addTabInterface(14000);
        tab.width = 174;
        tab.height = 201;
        tab.scrollMax = 0;
        for (int i = 14001; i <= 14030; i++) {
            addText(i, "", tda, 1, 0xffffff, false, true);
        }
        tab.totalChildren(30);
        int Child = 0;
        int Y = 9;
        for (int i = 14001; i <= 14030; i++) {
            tab.child(Child, i, 5, Y);
            Child++;
            Y += 13;
        }
    }

    public static void equipmentScreen(TextDrawingArea[] wid) {

        RSInterface tab = addTabInterface(15106);
        addHDSprite(15107, 677, 677);
        addHoverButton(15210, 141, 141, 21, 21, "Close", 250, 15211, 3);
        addHoveredButton(15211, 142, 142, 21, 21, 15212);
        addText(15111, "", wid, 2, 0xe4a146, false, true);
        int rofl = 3;
        addText(15112, "Attack bonuses", wid, 2, 0xFF8900, false, true);
        addText(15113, "Defence bonuses", wid, 2, 0xFF8900, false, true);
        addText(15114, "Other bonuses", wid, 2, 0xFF8900, false, true);
        //addText(19148, "Summoning: +0", wid, 1, 0xFF8900, false, true);
        //addText(19149, "Absorb Melee: +0%", wid, 1, 0xFF9200, false, true);
        //addText(19150, "Absorb Magic: +0%", wid, 1, 0xFF9200, false, true);
        //addText(19151, "Absorb Ranged: +0%", wid, 1, 0xFF9200, false, true);
        //addText(19152, "Ranged Strength: 0", wid, 1, 0xFF9200, false, true);
        //addText(19153, "Magic Damage: +0%", wid, 1, 0xFF9200, false, true);
        for (int i = 1675; i <= 1684; i++) {
            textSize(i, wid, 1);
        }
        textSize(1686, wid, 1);
        textSize(1687, wid, 1);
        addChar(15125);
        tab.totalChildren(50);
        tab.child(0, 15107, 15, 5);
        tab.child(1, 15210, 476, 8);
        tab.child(2, 15211, 476, 8);
        tab.child(3, 15111, 14, 30);
        int Child = 4;
        int Y = 45;
        tab.child(16, 15112, 24, 30 - rofl);
        for (int i = 1675; i <= 1679; i++) {
            tab.child(Child, i, 29, Y - rofl);
            Child++;
            Y += 14;
        }
        int edit = 7 + rofl;
        tab.child(18, 15113, 24, 122 - edit); // 147
        tab.child(9, 1680, 29, 137 - edit - 2); // 161
        tab.child(10, 1681, 29, 153 - edit - 3);
        tab.child(11, 1682, 29, 168 - edit - 3);
        tab.child(12, 1683, 29, 183 - edit - 3);
        tab.child(13, 1684, 29, 197 - edit - 3);
        tab.child(44, 19148, 29, 211 - edit - 3);
        tab.child(45, 19149, 29, 225 - edit - 3);
        tab.child(46, 19150, 29, 239 - edit - 3);
        tab.child(47, 19151, 29, 253 - edit - 3);
        /* bottom */
        int edit2 = 33 - rofl, edit3 = 2;
        tab.child(19, 15114, 24, 223 + edit2);
        tab.child(14, 1686, 29, 262 - 24 + edit2 - edit3);
        tab.child(17, 19152, 29, 276 - 24 + edit2 - edit3);
        tab.child(48, 1687, 29, 290 - 24 + edit2 - edit3);
        tab.child(49, 19153, 29, 304 - 24 + edit2 - edit3);

        tab.child(15, 15125, 170, 200);
        tab.child(20, 1645, 104 + 295, 149 - 52);
        tab.child(21, 1646, 399, 163);
        tab.child(22, 1647, 399, 163);
        tab.child(23, 1648, 399, 58 + 146);
        tab.child(24, 1649, 26 + 22 + 297 - 2, 110 - 44 + 118 - 13 + 5);
        tab.child(25, 1650, 321 + 22, 58 + 154);
        tab.child(26, 1651, 321 + 134, 58 + 118);
        tab.child(27, 1652, 321 + 134, 58 + 154);
        tab.child(28, 1653, 321 + 48, 58 + 81);
        tab.child(29, 1654, 321 + 107, 58 + 81);
        tab.child(30, 1655, 321 + 58, 58 + 42);
        tab.child(31, 1656, 321 + 112, 58 + 41);
        tab.child(32, 1657, 321 + 78, 58 + 4);
        tab.child(33, 1658, 321 + 37, 58 + 43);
        tab.child(34, 1659, 321 + 78, 58 + 43);
        tab.child(35, 1660, 321 + 119, 58 + 43);
        tab.child(36, 1661, 321 + 22, 58 + 82);
        tab.child(37, 1662, 321 + 78, 58 + 82);
        tab.child(38, 1663, 321 + 134, 58 + 82);
        tab.child(39, 1664, 321 + 78, 58 + 122);
        tab.child(40, 1665, 321 + 78, 58 + 162);
        tab.child(41, 1666, 321 + 22, 58 + 162);
        tab.child(42, 1667, 321 + 134, 58 + 162);
        tab.child(43, 1688, 50 + 297 - 2, 110 - 13 + 5);
        for (int i = 1675; i <= 1684; i++) {
            RSInterface rsi = interfaceCache[i];
            rsi.disabledColor = 0xFF9200;
            rsi.centerText = false;
        }
        for (int i = 1686; i <= 1687; i++) {
            RSInterface rsi = interfaceCache[i];
            rsi.disabledColor = 0xFF9200;
            rsi.centerText = false;
        }
    }

    public static void formParty(TextDrawingArea[] tda) {
        RSInterface tab = addTabInterface(27224);
        addHDSprite(27225, 143, 143);
		/*addHoverButton(27326, 141, 141, 16, 16, "Exit", 250, 27227, 5);
		addHoveredButton(27227, 142, 142, 16, 16, 27228);*/

        addHoverButton(27229, 144, 144, 180, 32, "Form Party", 250, 27230, 5);
        addHoveredButton(27230, 145, 145, 180, 32, 27231);

		/*addHoverButton(27132, 146, 146, 52, 25, "Reset", 250, 27133, 5);
		addHoveredButton(27133, 147, 147, 52, 25, 27134);*/

        addText(27235, "", tda, 1, 0xffffff, true, true);
        addText(27236, "", tda, 1, 0xffffff, true, true);
        addText(27237, "", tda, 1, 0xffffff, true, true);
        addText(27238, "", tda, 1, 0xffffff, true, true);
        addText(27239, "", tda, 1, 0xffffff, true, true);
        addText(27240, "", tda, 2, 0xffffff, false, true);
        addText(27241, "", tda, 2, 0xffffff, true, true);
        addText(27242, "-", tda, 1, 0xffffff, false, true);
        addText(27243, "-", tda, 1, 0xffffff, false, true);
        int[][] data = {{27225, 0, 0},/* { 27326, 171, 1 }, { 27227, 171, 1 },*/
                {27229, 5, 111}, {27230, 5, 111}, /*{ 27132, 132, 230 },
				{ 27133, 132, 230 }, */{27235, 91, 29}, {27236, 91, 44}, {27237, 91, 59}, {27238, 91, 75}, {27239, 91, 90}, {27240, 99, 156}, {27241, 103, 183}, {27242, 112, 229}, {27243, 112, 245}};
        tab.totalChildren(12); // 14, 16
        for (int i = 0; i < data.length; i++) {
            tab.child(i, data[i][0], data[i][1], data[i][2]);
        }
    }

    public static void statistics(final TextDrawingArea[] tda) {
        //statistics1(fonts);
        //scrollChatBox(fonts);

        final RSInterface tab = addInterface(26139);
        final RSInterface scroll = addInterface(26140);
        final String directory = "Achievement/achievement";

        addClickText(26131, "", 0xAF6A1B, false, true, 0, tda, 0, "Information");//is it delayed? ahhh ye

        addText(26110, "Drop Log", 0xAF6A1B, false, true, 0, tda, 2);

        addText(26111, "", 0xAF6A1B, true, true, 0, tda, 1);
        addText(26112, "", 0xAF6A1B, true, true, 0, tda, 1);

        addClickText(26113, "Newest to oldest", 0xAF6A1B, true, true, 0, tda, 1, "Vice Versa");
        addClickText(26114, "Reset whole drop log list", 0xAF6A1B, true, true, 0, tda, 1, "Delete Drop Log");

        addButton(26132, 3, 1, 1, 1, directory, 16, 15, "", 0, 0);
        addSprite(26138, 3, directory);
        tab.totalChildren(7);

        tab.child(0, 26138, 1, 1);
        tab.child(1, 26140, 28, 50);
        tab.child(2, 26131, 60, 310);
        tab.child(3, 26132, 492, 4);
        tab.child(4, 26110, 185, 6);

        tab.child(5, 26113, 100, 280);
        tab.child(6, 26114, 260, 280);

        final int ACHIEVEMENTS = 80;
        scroll.width = 430;
        scroll.height = 200;
        scroll.scrollMax = 1500;
        int x = 0;
        int y = 0;
        scroll.totalChildren(ACHIEVEMENTS);
        for (int i = 26141; i < 26141 + ACHIEVEMENTS; i++) {
            addText(i, "" + i + "", 0xAF6A1B, false, true, 0, tda, 0);
            scroll.child(i - 26141, i, x, y);
            y += 15;
        }
    }

    public static void dungParty(TextDrawingArea[] tda) {
        RSInterface tab = addTabInterface(26224);
        addHDSprite(26225, 143, 143);
        addHoverButton(26226, 141, 141, 16, 16, "Exit", 250, 26227, 5);
        addHoveredButton(26227, 142, 142, 16, 16, 26228);
        addHoverButton(26229, 148, 148, 90, 32, "Leave Party", 250, 26230, 5);
        addHoveredButton(26230, 149, 149, 90, 32, 26231);/*
		addHoverButton(26232, 146, 146, 52, 25, "Reset", 250, 26233, 5);
		addHoveredButton(26233, 147, 147, 52, 25, 26234);*/
        addText(26235, "", tda, 1, 0xffffff, true, true);
        addText(26236, "", tda, 1, 0xffffff, true, true);
        addText(26237, "", tda, 1, 0xffffff, true, true);
        addText(26238, "", tda, 1, 0xffffff, true, true);
        addText(26239, "", tda, 1, 0xffffff, true, true);
        addText(26240, "0", tda, 2, 0xffffff, false, true);
        addText(26241, "-", tda, 2, 0xffffff, true, true);
        addText(26242, "-", tda, 1, 0xffffff, false, true);
        addText(26243, "-", tda, 1, 0xffffff, false, true);
        addHoverButton(26244, 167, 167, 61, 20, "Change", 250, 26245, 5);
        addHoveredButton(26245, 159, 159, 61, 20, 26246);
        addHoverButton(26247, 167, 167, 61, 20, "Change", 250, 26248, 5);
        addHoveredButton(26248, 159, 159, 61, 20, 26249);
        addHoverButton(26250, 157, 157, 90, 32, "Invite player", 250, 26251, 5);
        addHoveredButton(26251, 158, 158, 90, 32, 26252);
        int[][] data = {{26225, 0, 0}, {26226, 171, 1}, {26227, 171, 1}, {26229, 5, 111}, {26230, 5, 111}, /*{ 26232, 132, 230 },
				{ 26233, 132, 230 },*/ {26235, 91, 29}, {26236, 91, 44}, {26237, 91, 59}, {26238, 91, 75}, {26239, 91, 90}, {26240, 99, 156}, {26241, 103, 183}, {26242, 112, 229}, {26243, 112, 245}, {26244, 121, 152}, {26245, 121, 152}, {26247, 121, 180}, {26248, 121, 180}, {26250, 95, 111}, {26251, 95, 111}};
        tab.totalChildren(20);
        for (int i = 0; i < data.length; i++) {
            tab.child(i, data[i][0], data[i][1], data[i][2]);
        }
    }

    public static void handleFloorMenus(int number, int interfaceId) {
        RSInterface rsInterface = addInterface(interfaceId);
        addHDSprite(interfaceId + 1, 160 + number, 160 + number);

        addHoverButton(interfaceId + 2, 141, 141, 16, 16, "Close", 250, interfaceId + 3, 3);
        addHoveredButton(interfaceId + 3, 142, 142, 16, 16, interfaceId + 4);
        addHoverButton(interfaceId + 5, -1, -1, 74, 30, "Confirm", 250, interfaceId + 6, 5);
        addHoveredButton(interfaceId + 6, 166, 166, 74, 30, interfaceId + 7);

        addButton(interfaceId + 8, -1, "", 21, 34, "Select", 1);
        addButton(interfaceId + 9, -1, "", 21, 34, "Select", 1);
        addButton(interfaceId + 10, -1, "", 21, 34, "Select", 1);
        addButton(interfaceId + 11, -1, "", 21, 34, "Select", 1);
        addButton(interfaceId + 12, -1, "", 21, 34, "Select", 1);
        addButton(interfaceId + 13, -1, "", 21, 34, "Select", 1);

        rsInterface.totalChildren(11);
        int[][] childs = {{interfaceId + 1, 0, 0}, {interfaceId + 2, 487, 4}, {interfaceId + 3, 487, 4}, {interfaceId + 5, 169, 264}, {interfaceId + 6, 169, 264}, {interfaceId + 8, 27, 37}, {interfaceId + 9, 66, 37}, {interfaceId + 10, 105, 37}, {interfaceId + 11, 141, 37}, {interfaceId + 12, 180, 37}, {interfaceId + 13, 219, 37}};
        for (int i = 0; i < childs.length; i++)
            rsInterface.child(i, childs[i][0], childs[i][1], childs[i][2]);
    }

    public static void floorMenus(TextDrawingArea[] TDA) {
        handleFloorMenus(1, 35233);
        handleFloorMenus(2, 35233 + 100);
        handleFloorMenus(3, 35233 + 200);
        handleFloorMenus(4, 35233 + 300);
        handleFloorMenus(5, 35233 + 400);
        handleFloorMenus(0, 35233 + 500);
    }

    public static void InvtoParty(TextDrawingArea[] TDA) {
        RSInterface rsInterface = addTabInterface(40224);
        addHDSprite(40225, 151, 151);
        addHoverButton(40226, 141, 141, 16, 16, "Close", 250, 40227, 3);
        addHoveredButton(40227, 142, 142, 16, 16, 26228);
        addHoverButton(40229, 155, 155, 72, 32, "Accept", 250, 40230, 5);
        addHoveredButton(40230, 152, 152, 72, 31, 40231);
        addHoverButton(40232, 156, 156, 72, 32, "Decline", 250, 40233, 5);
        addHoveredButton(40233, 153, 153, 72, 31, 40234);
        addText(40235, "", TDA, 1, 16777215, true, true);
        addText(40236, "", TDA, 1, 16777215, true, true);
        addText(40237, "", TDA, 1, 16777215, true, true);
        addText(40238, "", TDA, 1, 16777215, true, true);
        addText(40239, "", TDA, 1, 16777215, true, true);

        addText(40240, "0", TDA, 2, 16777215, false, true);
        addText(40241, "0", TDA, 2, 16777215, true, true);

        addText(40242, "", TDA, 1, 16777215, true, true);
        addText(40243, "", TDA, 1, 16777215, true, true);
        addText(40244, "", TDA, 1, 16777215, true, true);
        addText(40245, "", TDA, 1, 16777215, true, true);
        addText(40246, "", TDA, 1, 16777215, true, true);

        addText(40247, "", TDA, 1, 16777215, true, true);
        addText(40248, "", TDA, 1, 16777215, true, true);
        addText(40249, "", TDA, 1, 16777215, true, true);
        addText(40250, "", TDA, 1, 16777215, true, true);
        addText(40251, "", TDA, 1, 16777215, true, true);

        addText(40252, "", TDA, 1, 16777215, true, true);
        addText(40253, "", TDA, 1, 16777215, true, true);
        addText(40254, "", TDA, 1, 16777215, true, true);
        addText(40255, "", TDA, 1, 16777215, true, true);
        addText(40256, "", TDA, 1, 16777215, true, true);

        addText(40257, "", TDA, 1, 16777215, true, true);
        addText(40258, "", TDA, 1, 16777215, true, true);
        addText(40259, "", TDA, 1, 16777215, true, true);
        addText(40260, "", TDA, 1, 16777215, true, true);
        addText(40261, "", TDA, 1, 16777215, true, true);
        int[][] arrayOfInt = {{40225, 14, 20}, {40226, 468, 23}, {40227, 468, 23}, {40229, 128, 247}, {40230, 129, 248}, {40232, 218, 247}, {40233, 219, 248}, {40235, 93, 74}, {40236, 93, 93}, {40237, 93, 112}, {40238, 93, 131}, {40239, 93, 150}, {40240, 287, 173}, {40241, 290, 198}, {40242, 220, 74}, {40243, 220, 93}, {40244, 220, 112}, {40245, 220, 131}, {40246, 220, 150}, {40247, 290, 74}, {40248, 290, 93}, {40249, 290, 112}, {40250, 290, 131}, {40251, 290, 150}, {40252, 360, 74}, {40253, 360, 93}, {40254, 360, 112}, {40255, 360, 131}, {40256, 360, 150}, {40257, 440, 74}, {40258, 440, 93}, {40259, 440, 112}, {40260, 440, 131}, {40261, 440, 150}};

        rsInterface.totalChildren(34);
        for (int i = 0; i < arrayOfInt.length; i++)
            rsInterface.child(i, arrayOfInt[i][0], arrayOfInt[i][1], arrayOfInt[i][2]);
    }

    public static void itemsOnDeath(TextDrawingArea[] tda) {
        RSInterface rsinterface = addInterface(17100);
        addHDSprite(17101, 168, 168);
        addHoverButton(17102, 141, 141, 17, 17, "Close Window", 250, 10601, 3);
        addHoveredButton(10601, 142, 142, 17, 17, 10602);

        addText(17103, "Items Kept On Death", 0xff981f, false, true, 0, tda, 2);
        addText(17104, "Items you will keep on death (if not skulled):", 0xff981f, false, true, 0, tda, 2);
        addText(17105, "Items you will lose on death (if not skulled):", 0xff981f, false, true, 0, tda, 2);
        addText(17106, "Information", 0xff981f, false, true, 0, tda, 1);
        addText(17107, "Max items kept on death:", 0xff981f, false, true, 0, tda, 1);
        addText(17108, "~ 3 ~", 0xffcc33, false, true, 0, tda, 1);
        rsinterface.scrollMax = 0;
        rsinterface.hidden = false;
        rsinterface.children = new int[12];
        rsinterface.childX = new int[12];
        rsinterface.childY = new int[12];

        rsinterface.children[0] = 17101;
        rsinterface.childX[0] = 7;
        rsinterface.childY[0] = 8;
        rsinterface.children[1] = 17102;
        rsinterface.childX[1] = 480;
        rsinterface.childY[1] = 17;
        rsinterface.children[2] = 17103;
        rsinterface.childX[2] = 185;
        rsinterface.childY[2] = 18;
        rsinterface.children[3] = 17104;
        rsinterface.childX[3] = 22;
        rsinterface.childY[3] = 50;
        rsinterface.children[4] = 17105;
        rsinterface.childX[4] = 22;
        rsinterface.childY[4] = 110;
        rsinterface.children[5] = 17106;
        rsinterface.childX[5] = 347;
        rsinterface.childY[5] = 47;
        rsinterface.children[6] = 17107;
        rsinterface.childX[6] = 349;
        rsinterface.childY[6] = 270;
        rsinterface.children[7] = 17108;
        rsinterface.childX[7] = 398;
        rsinterface.childY[7] = 298;
        rsinterface.children[8] = 17115;
        rsinterface.childX[8] = 348;
        rsinterface.childY[8] = 64;
        rsinterface.children[9] = 10494;
        rsinterface.childX[9] = 26;
        rsinterface.childY[9] = 74;
        rsinterface.children[10] = 10600;
        rsinterface.childX[10] = 26;
        rsinterface.childY[10] = 133;
        rsinterface.children[11] = 10601;
        rsinterface.childX[11] = 480;
        rsinterface.childY[11] = 17;
        /*Fixing the placement of the items in the interface*/
        rsinterface = interfaceCache[10494];
        rsinterface.invSpritePadX = 6;
        rsinterface.invSpritePadY = 5;
        rsinterface = interfaceCache[10600];
        rsinterface.invSpritePadX = 6;
        rsinterface.invSpritePadY = 5;
    }

    public static void itemsOnDeathDATA(TextDrawingArea[] tda) {
        RSInterface RSinterface = addInterface(17115);
        addText(17109, "", 0xff981f, false, false, 0, tda, 0);
        addText(17110, "The normal amount of", 0xff981f, false, false, 0, tda, 0);
        addText(17111, "items kept is three.", 0xff981f, false, false, 0, tda, 0);
        addText(17112, "", 0xff981f, false, false, 0, tda, 0);
        addText(17113, "If you are skulled,", 0xff981f, false, false, 0, tda, 0);
        addText(17114, "you will lose all your", 0xff981f, false, false, 0, tda, 0);
        addText(17117, "items, unless an item", 0xff981f, false, false, 0, tda, 0);
        addText(17118, "protecting prayer is", 0xff981f, false, false, 0, tda, 0);
        addText(17119, "used.", 0xff981f, false, false, 0, tda, 0);
        addText(17120, "", 0xff981f, false, false, 0, tda, 0);
        addText(17121, "Item protecting prayers", 0xff981f, false, false, 0, tda, 0);
        addText(17122, "will allow you to keep", 0xff981f, false, false, 0, tda, 0);
        addText(17123, "one extra item.", 0xff981f, false, false, 0, tda, 0);
        addText(17124, "", 0xff981f, false, false, 0, tda, 0);
        addText(17125, "The items kept are", 0xff981f, false, false, 0, tda, 0);
        addText(17126, "selected by the server", 0xff981f, false, false, 0, tda, 0);
        addText(17127, "and include the most", 0xff981f, false, false, 0, tda, 0);
        addText(17128, "expensive items you're", 0xff981f, false, false, 0, tda, 0);
        addText(17129, "carrying.", 0xff981f, false, false, 0, tda, 0);
        addText(17130, "", 0xff981f, false, false, 0, tda, 0);
        RSinterface.parentID = 17115;
        RSinterface.id = 17115;
        RSinterface.type = 0;
        RSinterface.atActionType = 0;
        RSinterface.contentType = 0;
        RSinterface.width = 130;
        RSinterface.height = 197;
        RSinterface.opacity = 0;
        RSinterface.hoverType = -1;
        RSinterface.scrollMax = 280;
        RSinterface.children = new int[20];
        RSinterface.childX = new int[20];
        RSinterface.childY = new int[20];
        RSinterface.children[0] = 17109;
        RSinterface.childX[0] = 0;
        RSinterface.childY[0] = 0;
        RSinterface.children[1] = 17110;
        RSinterface.childX[1] = 0;
        RSinterface.childY[1] = 12;
        RSinterface.children[2] = 17111;
        RSinterface.childX[2] = 0;
        RSinterface.childY[2] = 24;
        RSinterface.children[3] = 17112;
        RSinterface.childX[3] = 0;
        RSinterface.childY[3] = 36;
        RSinterface.children[4] = 17113;
        RSinterface.childX[4] = 0;
        RSinterface.childY[4] = 48;
        RSinterface.children[5] = 17114;
        RSinterface.childX[5] = 0;
        RSinterface.childY[5] = 60;
        RSinterface.children[6] = 17117;
        RSinterface.childX[6] = 0;
        RSinterface.childY[6] = 72;
        RSinterface.children[7] = 17118;
        RSinterface.childX[7] = 0;
        RSinterface.childY[7] = 84;
        RSinterface.children[8] = 17119;
        RSinterface.childX[8] = 0;
        RSinterface.childY[8] = 96;
        RSinterface.children[9] = 17120;
        RSinterface.childX[9] = 0;
        RSinterface.childY[9] = 108;
        RSinterface.children[10] = 17121;
        RSinterface.childX[10] = 0;
        RSinterface.childY[10] = 120;
        RSinterface.children[11] = 17122;
        RSinterface.childX[11] = 0;
        RSinterface.childY[11] = 132;
        RSinterface.children[12] = 17123;
        RSinterface.childX[12] = 0;
        RSinterface.childY[12] = 144;
        RSinterface.children[13] = 17124;
        RSinterface.childX[13] = 0;
        RSinterface.childY[13] = 156;
        RSinterface.children[14] = 17125;
        RSinterface.childX[14] = 0;
        RSinterface.childY[14] = 168;
        RSinterface.children[15] = 17126;
        RSinterface.childX[15] = 0;
        RSinterface.childY[15] = 180;
        RSinterface.children[16] = 17127;
        RSinterface.childX[16] = 0;
        RSinterface.childY[16] = 192;
        RSinterface.children[17] = 17128;
        RSinterface.childX[17] = 0;
        RSinterface.childY[17] = 204;
        RSinterface.children[18] = 17129;
        RSinterface.childX[18] = 0;
        RSinterface.childY[18] = 216;
        RSinterface.children[19] = 17130;
        RSinterface.childX[19] = 0;
        RSinterface.childY[19] = 228;
    }

    private static void newBank() {
        RSInterface Interface = addTabInterface(5292);
        setChildren(19, Interface);
        addHDSprite(5293, 169, 169);
        setBounds(5293, 13, 13, 0, Interface);
        addHoverButton(5384, CLOSE_BUTTON, CLOSE_BUTTON, 17, 17, "Close Window", 250, 5380, 3);
        addHoveredButton(5380, CLOSE_BUTTON_HOVER, CLOSE_BUTTON_HOVER, 17, 17, 5379);
        setBounds(5384, 476, 16, 3, Interface);
        setBounds(5380, 476, 16, 4, Interface);
        addHoverButton(5294, 170, 170, 114, 25, "Set A Bank PIN", 250, 5295, 4);
        addHoveredButton(5295, 171, 171, 114, 25, 5296);
        setBounds(5294, 110, 285, 5, Interface);
        setBounds(5295, 110, 285, 6, Interface);
        addBankHover(21000, 4, 21001, 172, 175, 35, 25, 304, 1, "Swap Insert Mode", 21002, 174, 173, 21003, "Switch to insert items \nmode", "Switch to swap items \nmode.", 12, 20);
        setBounds(21000, 25, 285, 7, Interface);
        setBounds(21001, 10, 225, 8, Interface);
        addBankHover(21004, 4, 21005, 180, 182, 35, 25, 0, 1, "Search", 21006, 181, 183, 21007, "Click here to search your \nbank", "Click here to search your \nbank", 12, 20);
        setBounds(21004, 65, 285, 9, Interface);
        setBounds(21005, 50, 225, 10, Interface);
        addBankHover(21008, 4, 21009, 176, 178, 35, 25, 115, 1, "Swap Withdrawal Mode", 21010, 177, 179, 21011, "Switch to note withdrawal \nmode", "Switch to item withdrawal \nmode", 12, 20);
        setBounds(21008, 240, 285, 11, Interface);
        setBounds(21009, 225, 225, 12, Interface);
        addBankHover1(21012, 5, 21013, 184, 35, 25, "Deposit carried items", 21014, 185, 21015, "Empty your backpack into\nyour bank", 0, 20);
        setBounds(21012, 375, 285, 13, Interface);
        setBounds(21013, 360, 225, 14, Interface);
        addBankHover1(21016, 5, 21017, 186, 35, 25, "Deposit worn items", 21018, 187, 21019, "Empty the items your are\nwearing into your bank", 0, 20);
        setBounds(21016, 415, 285, 15, Interface);
        setBounds(21017, 400, 225, 16, Interface);
        addBankHover1(21020, 5, 21021, 188, 35, 25, "Deposit beast of burden inventory.", 21022, 189, 21023, "Empty your BoB's inventory\ninto your bank", 0, 20);
        setBounds(21020, 455, 285, 17, Interface);
        setBounds(21021, 440, 225, 18, Interface);
        setBounds(5383, 170, 15, 1, Interface);
        setBounds(5385, -4, 34, 2, Interface);
        Interface = interfaceCache[5385];
        Interface.height = 247;
        Interface.width = 480;
        Interface = interfaceCache[5382];
        Interface.width = 10;
        Interface.invSpritePadX = 12;
        Interface.height = 35;
    }

    public static void addBankHover(int interfaceID, int actionType, int hoverid, int spriteId, int spriteId2, int Width, int Height, int configFrame, int configId, String Tooltip, int hoverId2, int hoverSpriteId, int hoverSpriteId2, int hoverId3, String hoverDisabledText, String hoverEnabledText, int X, int Y) {
        RSInterface hover = addTabInterface(interfaceID);
        hover.id = interfaceID;
        hover.parentID = interfaceID;
        hover.type = 5;
        hover.atActionType = actionType;
        hover.contentType = 0;
        hover.opacity = 0;
        hover.hoverType = hoverid;
        hover.disabledSpriteId = spriteId;
        hover.enabledSpriteId = spriteId2;
        hover.width = Width;
        hover.tooltip = Tooltip;
        hover.height = Height;
        hover.valueCompareType = new int[1];
        hover.requiredValues = new int[1];
        hover.valueCompareType[0] = 1;
        hover.requiredValues[0] = configId;
        hover.valueIndexArray = new int[1][3];
        hover.valueIndexArray[0][0] = 5;
        hover.valueIndexArray[0][1] = configFrame;
        hover.valueIndexArray[0][2] = 0;
        hover = addTabInterface(hoverid);
        hover.parentID = hoverid;
        hover.id = hoverid;
        hover.type = 0;
        hover.atActionType = 0;
        hover.width = 550;
        hover.height = 334;
        hover.hidden = true;
        hover.hoverType = -1;
        addSprite(hoverId2, hoverSpriteId, hoverSpriteId2, configId, configFrame);
        addHoverBox(hoverId3, interfaceID, hoverDisabledText, hoverEnabledText, configId, configFrame);
        setChildren(2, hover);
        setBounds(hoverId2, 15, 60, 0, hover);
        setBounds(hoverId3, X, Y, 1, hover);
    }

    public static void addBankHover1(int interfaceID, int actionType, int hoverid, int spriteId, int Width, int Height, String Tooltip, int hoverId2, int hoverSpriteId, int hoverId3, String hoverDisabledText, int X, int Y) {
        RSInterface hover = addTabInterface(interfaceID);
        hover.id = interfaceID;
        hover.parentID = interfaceID;
        hover.type = 5;
        hover.atActionType = actionType;
        hover.contentType = 0;
        hover.opacity = 0;
        hover.hoverType = hoverid;
        hover.disabledSpriteId = spriteId;
        hover.width = Width;
        hover.tooltip = Tooltip;
        hover.height = Height;
        hover = addTabInterface(hoverid);
        hover.parentID = hoverid;
        hover.id = hoverid;
        hover.type = 0;
        hover.atActionType = 0;
        hover.width = 550;
        hover.height = 334;
        hover.hidden = true;
        hover.hoverType = -1;
        addSprite(hoverId2, hoverSpriteId, hoverSpriteId, 0, 0);
        addHoverBox(hoverId3, interfaceID, hoverDisabledText, hoverDisabledText, 0, 0);
        setChildren(2, hover);
        setBounds(hoverId2, 15, 60, 0, hover);
        setBounds(hoverId3, X, Y, 1, hover);
    }

    public static void addHoverBox(int id, int ParentID, String text, String text2, int configId, int configFrame) {
        RSInterface rsi = addTabInterface(id);
        rsi.id = id;
        rsi.parentID = ParentID;
        rsi.type = 8;
        rsi.message = text2;
        rsi.valueCompareType = new int[1];
        rsi.requiredValues = new int[1];
        rsi.valueCompareType[0] = 1;
        rsi.requiredValues[0] = configId;
        rsi.valueIndexArray = new int[1][3];
        rsi.valueIndexArray[0][0] = 5;
        rsi.valueIndexArray[0][1] = configFrame;
        rsi.valueIndexArray[0][2] = 0;
    }

    public static void addSprite(int ID, int i, int i2, int configId, int configFrame) {
        RSInterface Tab = addTabInterface(ID);
        Tab.id = ID;
        Tab.parentID = ID;
        Tab.type = 5;
        Tab.atActionType = 0;
        Tab.contentType = 0;
        Tab.width = 512;
        Tab.height = 334;
        Tab.opacity = 0;
        Tab.hoverType = -1;
        Tab.valueCompareType = new int[1];
        Tab.requiredValues = new int[1];
        Tab.valueCompareType[0] = 1;
        Tab.requiredValues[0] = configId;
        Tab.valueIndexArray = new int[1][3];
        Tab.valueIndexArray[0][0] = 5;
        Tab.valueIndexArray[0][1] = configFrame;
        Tab.valueIndexArray[0][2] = 0;
        Tab.disabledSpriteId = i;
        Tab.enabledSpriteId = i2;

    }

    public static void Shop(TextDrawingArea[] TDA) {
        RSInterface rsinterface = addTabInterface(3824);
        setChildren(8, rsinterface);
        addHDSprite(3825, 190, 190);
        addHoverButton(3902, CLOSE_BUTTON, CLOSE_BUTTON, 17, 17, "Close Window", 250, 3826, 3);
        addHoveredButton(3826, CLOSE_BUTTON_HOVER, CLOSE_BUTTON_HOVER, 17, 17, 3827);
        addText(19679, "", 0xff981f, false, true, 52, TDA, 1);
        addText(19680, "", 0xbf751d, false, true, 52, TDA, 1);
        addButton(19681, 2, null, 0, 0, "", 1);
        addSprite(19687, 1, null);
        setBounds(3825, 6, 8, 0, rsinterface);
        setBounds(3902, 478, 10, 1, rsinterface);
        setBounds(3826, 478, 10, 2, rsinterface);
        setBounds(3900, 26, 44, 3, rsinterface);
        setBounds(3901, 240, 11, 4, rsinterface);
        setBounds(19679, 42, 54, 5, rsinterface);
        setBounds(19680, 150, 54, 6, rsinterface);
        setBounds(19681, 129, 50, 7, rsinterface);
        rsinterface = interfaceCache[3900];
        setChildren(1, rsinterface);
        setBounds(19687, 6, 15, 0, rsinterface);
        rsinterface.invSpritePadX = 15;
        rsinterface.width = 10;
        rsinterface.height = 4;
        rsinterface.invSpritePadY = 25;
        rsinterface = addTabInterface(19682);
        addSprite(19683, 1, null);
        addText(19684, "Main Stock", 0xbf751d, false, true, 52, TDA, 1);
        addText(19685, "Store Info", 0xff981f, false, true, 52, TDA, 1);
        addButton(19686, 2, null, 95, 19, "Main Stock", 1);
        setChildren(7, rsinterface);
        setBounds(19683, 12, 12, 0, rsinterface);
        setBounds(3901, 240, 21, 1, rsinterface);
        setBounds(19684, 42, 54, 2, rsinterface);
        setBounds(19685, 150, 54, 3, rsinterface);
        setBounds(19686, 23, 50, 4, rsinterface);
        setBounds(3902, 471, 22, 5, rsinterface);
        setBounds(3826, 60, 85, 6, rsinterface);
    }

    public static void Trade(TextDrawingArea[] TDA) {
        RSInterface Interface = addInterface(3323);
        setChildren(19, Interface);
        addHDSprite(3324, 191, 191);
        addHoverButton(3442, CLOSE_BUTTON, CLOSE_BUTTON, 17, 17, "Close Window", 250, 3325, 3);
        addHoveredButton(3325, CLOSE_BUTTON_HOVER, CLOSE_BUTTON_HOVER, 17, 17, 3326);
        addText(3417, "Trading With:", 0xFF981F, true, true, 52, TDA, 2);
        addText(3418, "Trader's Offer", 0xFF981F, false, true, 52, TDA, 1);
        addText(3419, "Your Offer", 0xFF981F, false, true, 52, TDA, 1);
        addText(3421, "Accept", 0x00C000, true, true, 52, TDA, 1);
        addText(3423, "Decline", 0xC00000, true, true, 52, TDA, 1);

        addText(3431, "Waiting For Other Player", 0xFFFFFF, true, true, 52, TDA, 1);
        addText(53504, "Wealth transfer: 2147,000,000 coins' worth to Zezimablud12", 0xB9B855, true, true, -1, TDA, 0);
        addText(53505, "1 has\\n 28 free\\n inventory slots.", 0xFF981F, true, true, -1, TDA, 0);

        addText(53506, "Wealth transfer: 2147,000,000 coins' worth to Zezimablud12", 0xB9B855, false, true, -1, TDA, 0);
        addText(53507, "Wealth transfer: 2147,000,000 coins' worth to me", 0xB9B855, false, true, -1, TDA, 0);

        addHover(3420, 1, 0, 3327, 194, 65, 32, "Accept");
        addHovered(3327, 192, 65, 32, 3328);
        addHover(3422, 3, 0, 3329, 194, 65, 32, "Decline");
        addHovered(3329, 192, 65, 32, 3330);
        setBounds(3324, 0, 16, 0, Interface);
        setBounds(3442, 485, 24, 1, Interface);
        setBounds(3325, 485, 24, 2, Interface);
        setBounds(3417, 258, 25, 3, Interface);
        setBounds(3418, 355, 51, 4, Interface);
        setBounds(3419, 68, 51, 5, Interface);
        setBounds(3420, 223, 120, 6, Interface);
        setBounds(3327, 223, 120, 7, Interface);
        setBounds(3422, 223, 160, 8, Interface);
        setBounds(3329, 223, 160, 9, Interface);
        setBounds(3421, 256, 127, 10, Interface);
        setBounds(3423, 256, 167, 11, Interface);
        setBounds(3431, 256, 272, 12, Interface);
        setBounds(3415, 12, 64, 13, Interface);
        setBounds(3416, 321, 67, 14, Interface);

        setBounds(53505, 256, 67, 16, Interface);

        setBounds(53504, 255, 310, 15, Interface);
        setBounds(53506, 20, 310, 17, Interface);
        setBounds(53507, 380, 310, 18, Interface);

        Interface = addInterface(3443);
        setChildren(15, Interface);
        addHDSprite(3444, 193, 193);
        addButton(3546, 2, null, 63, 24, "Accept", 1);
        addButton(3548, 2, null, 63, 24, "Decline", 3);
        addText(3547, "Accept", 0x00C000, true, true, 52, TDA, 1);
        addText(3549, "Decline", 0xC00000, true, true, 52, TDA, 1);
        addText(3450, "Trading With:", 0x00FFFF, true, true, 52, TDA, 2);
        addText(3451, "Yourself", 0x00FFFF, true, true, 52, TDA, 2);
        setBounds(3444, 12, 20, 0, Interface);
        setBounds(3442, 470, 32, 1, Interface);
        setBounds(3325, 470, 32, 2, Interface);
        setBounds(3535, 130, 28, 3, Interface);
        setBounds(3536, 105, 47, 4, Interface);
        setBounds(3546, 189, 295, 5, Interface);
        setBounds(3548, 258, 295, 6, Interface);
        setBounds(3547, 220, 299, 7, Interface);
        setBounds(3549, 288, 299, 8, Interface);
        setBounds(3557, 71, 87, 9, Interface);
        setBounds(3558, 315, 87, 10, Interface);
        setBounds(3533, 64, 70, 11, Interface);
        setBounds(3534, 297, 70, 12, Interface);
        setBounds(3450, 95, 289, 13, Interface);
        setBounds(3451, 95, 304, 14, Interface);
    }

    private static void addHead2(int id, int w, int h, int zoom) {// tewst
        RSInterface rsinterface = interfaceCache[id] = new RSInterface();
        rsinterface.type = 6;
        rsinterface.mediaType = 2;
        rsinterface.mediaID = 4000;//
        rsinterface.modelZoom = zoom;
        rsinterface.modelRotation1 = 40;
        rsinterface.modelRotation2 = 1900;
        rsinterface.height = h;
        rsinterface.width = w;
    }

    public static void SummonTab(TextDrawingArea[] wid) {
        RSInterface Tab = addTabInterface(17011);
        addHDSprite(17012, 441, 441);
        addButton(17013, 442, "Select", 135, 13);
        addHDSprite(17014, 441, 441);
        addConfigButton(17015, 17032, 412, 411, 20, 30, "Familiar Special", 1, 5, 300);
        addHoverButton(17018, 438, 438, 38, 36, "Beast of burden Inventory", -1, 17028, 1);
        addHoveredButton(17028, 446, 446, 38, 36, 17029);
        addHoverButton(17022, 437, 437, 38, 36, "Call Familiar", -1, 17030, 1);
        addHoveredButton(17030, 447, 447, 38, 36, 17031);
        addHoverButton(17023, 439, 439, 38, 36, "Dismiss Familiar", -1, 17033, 1);
        addHoveredButton(17033, 448, 448, 38, 36, 17034);
        addHoverButton(17038, 450, 450, 38, 36, "Renew Summon", -1, 17039, 1);
        addHoveredButton(17039, 451, 451, 38, 36, 17041);
        addHDSprite(17016, 440, 440);
        addText(17017, "No familiar", wid, 2, 0xDAA520, true, true);
        addHDSprite(17019, 443, 443);
        addText(17021, "0:00", wid, 0, 0xFFA500, true, true);
        addHDSprite(17020, 444, 444);
        addHDSprite(17024, 445, 445);
        addText(17025, "49/50", wid, 0, 0xFFA500, false, true);
        addText(17026, "0", wid, 0, 0xFFA500, false, true);
        addText(17040, " ", wid, 0, 0xFFA500, false, true);
        addHead2(17027, 75, 55, 800);
        Tab.totalChildren(22);
        Tab.child(0, 17012, 10, 25);
        Tab.child(1, 17013, 24, 7);
        Tab.child(2, 17014, 10, 25);
        Tab.child(3, 17015, 11, 25);
        Tab.child(4, 17016, 15, 140);
        Tab.child(5, 17017, 95, 143);
        Tab.child(6, 17018, 20, 170);
        Tab.child(7, 17019, 115, 167);
        Tab.child(8, 17020, 143, 170);
        Tab.child(9, 17021, 145, 197);
        Tab.child(10, 17022, 20, 213);
        Tab.child(11, 17023, 67, 170);
        Tab.child(12, 17024, 135, 214);
        Tab.child(13, 17025, 135, 240);
        Tab.child(14, 17026, 21, 59);
        Tab.child(15, 17028, 20, 170);
        Tab.child(16, 17030, 20, 213);
        Tab.child(17, 17033, 67, 170);
        Tab.child(18, 17038, 67, 213);
        Tab.child(19, 17039, 67, 213);
        Tab.child(20, 17040, 30, 8);
        Tab.child(21, 17027, 75, 55);
    }

    public static void scrollCreation(TextDrawingArea[] TDA) {
        int totalScrolls = pouchItems.length;
        int xPadding = 53;
        int yPadding = 57;
        int xPos = 13;
        int yPos = 20;
        RSInterface rsinterface = addTabInterface(22760);
        setChildren(7, rsinterface);
        addHDSprite(22761, 663, 663);
        addButton(22762, 665, 665, 79, 20, "Infuse Pouches", 5);
        addHDSprite(22763, 667, 667);
        addHDSprite(22764, 666, 666);
        addHDSprite(22765, 670, 670);
        addInAreaHover(22766, CLOSE_BUTTON, CLOSE_BUTTON_HOVER, 16, 16, "Close", 250, 3);
        // Scroll section
        RSInterface scroll = addTabInterface(22767);
        setChildren(4 * totalScrolls, scroll);
        for (int i = 0; i < totalScrolls; i++) {
            addInAreaHover(22768 + (i * 9), 671, 672, 48, 52, "nothing", -1, 0);
            addScroll(22769 + (i * 9), pouchItems[i], 1, scrollItems[i], summoningLevelRequirements[i], scrollNames[i], TDA, i, 5);

            addSprite(22776 + (i * 9), pouchItems[i], null, 50, 50);
            setBounds(22768 + (i * 9), 36 + ((i % 8) * xPadding), 0 + (i / 8) * yPadding, 0 + (i * 3), scroll);
            setBounds(22769 + (i * 9), 43 + ((i % 8) * xPadding), 2 + (i / 8) * yPadding, 1 + (i * 3), scroll);
            setBounds(22776 + (i * 9), 28 + ((i % 8) * xPadding), 28 + (i / 8) * yPadding, 2 + (i * 3), scroll);
        }
        for (int i = 0; i < totalScrolls; i++) {
            int drawX = 5 + ((i % 8) * xPadding);
            if (drawX > 472 - 180) drawX -= 90;
            int drawY = 55 + (i / 8) * yPadding;
            if (drawY > 200 - 40) drawY -= 80;
            setBounds(22770 + (i * 9), drawX, drawY, 3 + ((totalScrolls - 1) * 3) + i, scroll);
        }
        scroll.parentID = 22767;
        scroll.id = 22767;
        scroll.atActionType = 0;
        scroll.contentType = 0;
        scroll.width = 452 + 22;
        scroll.height = 257;
        scroll.scrollMax = 570;
        //
        setBounds(22761, xPos, yPos, 0, rsinterface);
        setBounds(22762, xPos + 9, yPos + 9, 1, rsinterface);
        setBounds(22763, xPos + 29, yPos + 10, 2, rsinterface);

        setBounds(22764, xPos + 79, yPos + 9, 3, rsinterface);
        setBounds(22765, xPos + 106, yPos + 10, 4, rsinterface);
        setBounds(22766, xPos + 461, yPos + 10, 5, rsinterface);
        setBounds(22767, 0, yPos + 39, 6, rsinterface);
    }

    public static void addSprite(int id, int spriteId, String spriteName, int zoom1, int zoom2) {
        RSInterface tab = interfaceCache[id] = new RSInterface();
        tab.id = id;
        tab.parentID = id;
        tab.type = 5;
        tab.atActionType = 0;
        tab.contentType = 0;
        tab.opacity = (byte) 0;
        tab.hoverType = 52;
        if (spriteName == null) {
            tab.itemSpriteZoom1 = zoom1;
            tab.itemSpriteId1 = spriteId;
            tab.itemSpriteZoom2 = zoom2;
            tab.itemSpriteId2 = spriteId;
        } else {
            tab.disabledSprite = imageLoader(spriteId, spriteName);
            tab.enabledSprite = imageLoader(spriteId, spriteName);
        }
        tab.width = 512;
        tab.height = 334;
    }

    public static void addHDSprite(int id, int spriteId, int sprite2) {
        RSInterface tab = interfaceCache[id] = new RSInterface();
        tab.id = id;
        tab.parentID = id;
        tab.type = 5;
        tab.advancedSprite = true;
        tab.atActionType = 0;
        tab.contentType = 0;
        tab.opacity = (byte) 0;
        tab.hoverType = 52;
        tab.disabledSpriteId = spriteId;
        tab.enabledSpriteId = sprite2;

        tab.width = 512;
        tab.height = 1024;
    }

    public static void addSpriteSelectable(int id, int spriteId, int sprite2, int width, int height, String tooltip) {
        RSInterface tab = interfaceCache[id] = new RSInterface();
        tab.id = id;
        tab.parentID = id;
        tab.type = 5;
        tab.advancedSprite = true;
        tab.atActionType = 5;
        tab.contentType = 0;
        tab.opacity = (byte) 0;
        tab.hoverType = 52;
        tab.disabledSpriteId = spriteId;// = imageLoader(spriteId, spriteName);
        tab.enabledSpriteId = sprite2;// imageLoader(sprite2, spriteName);

        tab.width = width;
        tab.height = height;
        tab.tooltip = tooltip;
    }

    public static void addScroll(int ID, int r1, int ra1, int r2, int lvl, String name, TextDrawingArea[] TDA, int imageID, int type) {
        RSInterface rsInterface = addTabInterface(ID);
        rsInterface.id = ID;
        rsInterface.parentID = 1151;
        rsInterface.type = 5;
        rsInterface.atActionType = type;
        rsInterface.contentType = 0;
        rsInterface.hoverType = ID + 1;
        rsInterface.width = 32;
        rsInterface.height = 32;
        rsInterface.tooltip = "Transform @or1@" + name;// infuse for pouches
        rsInterface.spellName = name;
        rsInterface.valueCompareType = new int[2];
        rsInterface.requiredValues = new int[2];
        rsInterface.valueCompareType[0] = 10;
        rsInterface.requiredValues[0] = ra1;
        rsInterface.valueCompareType[1] = 10;
        rsInterface.requiredValues[1] = lvl - 1;
        rsInterface.valueIndexArray = new int[3][];
        rsInterface.valueIndexArray[0] = new int[4];
        rsInterface.valueIndexArray[0][0] = 4;
        rsInterface.valueIndexArray[0][1] = 3214;
        rsInterface.valueIndexArray[0][2] = r1;
        rsInterface.valueIndexArray[0][3] = 0;
        rsInterface.valueIndexArray[1] = new int[3];
        rsInterface.valueIndexArray[1][0] = 1;
        rsInterface.valueIndexArray[1][1] = 6;
        rsInterface.valueIndexArray[1][2] = 0;
        // rsInterface.sprite1 = null;
        rsInterface.itemSpriteId1 = r2;
        rsInterface.itemSpriteId2 = r2;
        rsInterface.itemSpriteZoom1 = 150;
        rsInterface.itemSpriteZoom2 = 150;
        rsInterface.itemSpriteIndex = imageID;
        rsInterface.greyScale = true;
        RSInterface hover = addTabInterface(ID + 1);// Hover interface ID
        hover.hoverType = -1;
        hover.hidden = true;
        setChildren(5, hover);
        addHDSprite(ID + 2, 289, 289);
        addText(ID + 3, "Level " + (lvl) + ": " + name, 0xFF981F, true, true, 52, 1);
        addText(ID + 4, "This item requires", 0xAF6A1A, true, true, 52, 0);
        addRuneText(ID + 5, ra1, r1, TDA);
        addSprite(ID + 6, r1, null);

        setBounds(ID + 2, 0, 0, 0, hover);
        setBounds(ID + 3, 90, 4, 1, hover);
        setBounds(ID + 4, 90, 19, 2, hover);
        setBounds(ID + 5, 87, 66, 3, hover);
        setBounds(ID + 6, 72, 33, 4, hover);// Rune
    }

    public static void addPouch(int ID, int r1[], int ra1, int r2, int lvl, String name, TextDrawingArea[] TDA, int imageID, int type) {
        RSInterface rsInterface = addTabInterface(ID);
        rsInterface.id = ID;
        rsInterface.parentID = 1151;
        rsInterface.type = 5;
        rsInterface.atActionType = type;
        rsInterface.contentType = 0;
        rsInterface.hoverType = ID + 1;
        rsInterface.width = 32;
        rsInterface.height = 32;
        rsInterface.tooltip = (new StringBuilder()).append("Infuse @or1@").append(name).toString();
        rsInterface.spellName = name;
        rsInterface.valueCompareType = new int[2];
        rsInterface.requiredValues = new int[2];
        rsInterface.valueCompareType[0] = 3;
        rsInterface.requiredValues[0] = ra1;
        rsInterface.valueCompareType[1] = 3;
        rsInterface.requiredValues[1] = lvl - 1;
        rsInterface.summonReq = lvl - 1;
        rsInterface.valueIndexArray = new int[2 + r1.length][];
        for (int i = 0; i < r1.length; i++) {
            rsInterface.valueIndexArray[i] = new int[4];
            rsInterface.valueIndexArray[i][0] = 4;
            rsInterface.valueIndexArray[i][1] = 3214;
            rsInterface.valueIndexArray[i][2] = r1[i];
            rsInterface.valueIndexArray[i][3] = 0;
        }
        rsInterface.valueIndexArray[1] = new int[3];
        rsInterface.valueIndexArray[1][0] = 1;
        rsInterface.valueIndexArray[1][1] = 6;
        rsInterface.valueIndexArray[1][2] = 0;
        rsInterface.itemSpriteZoom1 = 150;
        rsInterface.itemSpriteZoom2 = 150;
        rsInterface.itemSpriteId1 = r2;
        rsInterface.itemSpriteId2 = r2;
        rsInterface.itemSpriteIndex = imageID;
        // rsInterface.greyScale = true;
        RSInterface hover = addTabInterface(ID + 1);
        hover.hoverType = -1;
        hover.hidden = true;
        if (imageID < summoningItemRequirements.length) {
            addSprite(ID + 6, summoningItemRequirements[imageID][0], null, 150, 150);
            addSprite(ID - 1200, summoningItemRequirements[imageID][1], null, 150, 150);
            addSprite(ID - 1201, summoningItemRequirements[imageID][2], null, 150, 150);
            addRuneText(ID - 1202, summoningItemAmountRequirements[imageID][0], summoningItemRequirements[imageID][0], TDA);
            addRuneText(ID - 1203, summoningItemAmountRequirements[imageID][1], summoningItemRequirements[imageID][1], TDA);
            if (summoningItemAmountRequirements[imageID][2] > 0)
                addRuneText(ID - 1204, summoningItemAmountRequirements[imageID][2], summoningItemRequirements[imageID][2], TDA);
            setChildren(summoningItemAmountRequirements[imageID][2] > 0 ? 9 : 8, hover);
            setBounds(ID + 6, 14, 33, 3, hover);
            setBounds(ID - 1200, 70, 33, 4, hover);
            setBounds(ID - 1201, 120, 33, 5, hover);
            setBounds(ID - 1202, 30, 65, 6, hover);
            setBounds(ID - 1203, 85, 65, 7, hover);
            if (summoningItemAmountRequirements[imageID][2] > 0) setBounds(ID - 1204, 133, 65, 8, hover);
        } else setChildren(3, hover);
        addSprite(ID + 2, 0, "Interfaces/summoning/creation/BLACKBOX", -1, -1);
        addSpriteLoader(ID + 2, 894);
        addText(ID + 3, (new StringBuilder()).append("Level ").append(lvl).append(": ").append(name).toString(), 0xff981f, true, true, 52, 1);
        addText(ID + 4, "This item requires:", 0xaf6a1a, true, true, 52, 0);
        setBounds(ID + 2, 0, 0, 0, hover);
        setBounds(ID + 3, 90, 4, 1, hover);
        setBounds(ID + 4, 90, 19, 2, hover);
    }

    public static void addInAreaHover(int i, int sId, int sId2, int w, int h, String text, int contentType, int actionType) {// hoverable
        // button
        RSInterface tab = addTabInterface(i);
        tab.id = i;
        tab.parentID = i;
        tab.type = 5;// Sprite
        tab.atActionType = actionType;
        tab.contentType = contentType;
        tab.opacity = 0;
        tab.hoverType = i;
        if (sId != -1) tab.disabledSpriteId = sId;
        tab.enabledSpriteId = sId2;
        tab.width = w;
        tab.height = h;
        tab.tooltip = text;
    }

    public static void addText(int i, String s, int k, boolean l, boolean m, int a, TextDrawingArea[] TDA, int j, int dsc) {
        RSInterface rsinterface = addTabInterface(i);
        rsinterface.parentID = i;
        rsinterface.id = i;
        rsinterface.type = 4;
        rsinterface.atActionType = 1;
        rsinterface.width = 174;
        rsinterface.height = 11;
        rsinterface.contentType = 0;
        // rsinterface.aByte254 = 0;
        // rsinterface.hoverType = a;
        rsinterface.centerText = l;
        rsinterface.shadowed = m;
        rsinterface.textDrawingAreas = TDA[j];
        rsinterface.message = s;
        rsinterface.enabledMessage = "";
        rsinterface.enabledColor = 0;
        rsinterface.disabledColor = k;
        rsinterface.disabledMouseOverColor = dsc;
        rsinterface.tooltip = s;
    }

    public static void addHovered(int i, int j, String imageName, int w, int h, int IMAGEID) {
        addHoveredButton(i, imageName, j, w, h, IMAGEID);
    }

    public static void addHover(int i, int aT, int contentType, int hoverOver, int sId, String imageName, int width, int height, String text) {
        addHoverButton(i, imageName, sId, width, height, text, contentType, hoverOver, aT);
    }

    public static void addHovered(int i, int j, int w, int h, int IMAGEID) {
        addHoveredButton(i, j, j, w, h, IMAGEID);
    }

    public static void addHover(int i, int aT, int contentType, int hoverOver, int sId, int width, int height, String text) {
        addHoverButton(i, sId, sId, width, height, text, contentType, hoverOver, aT);
    }

    public static void addPrayer(int i, int configId, int configFrame, int requiredValues, int prayerSpriteID, String PrayerName, int Hover) {
        RSInterface Interface = addTabInterface(i);
        Interface.id = i;
        Interface.parentID = 22500;
        Interface.type = 5;
        Interface.atActionType = 4;
        Interface.contentType = 0;
        Interface.opacity = 0;
        Interface.hoverType = Hover;
        Interface.disabledSpriteId = 480;
        Interface.enabledSpriteId = -1;
        Interface.width = 34;
        Interface.height = 34;
        Interface.valueCompareType = new int[1];
        Interface.requiredValues = new int[1];
        Interface.valueCompareType[0] = 1;
        Interface.requiredValues[0] = configId;
        Interface.valueIndexArray = new int[1][3];
        Interface.valueIndexArray[0][0] = 5;
        Interface.valueIndexArray[0][1] = configFrame;
        Interface.valueIndexArray[0][2] = 0;
        Interface.tooltip = "Activate@lre@ " + PrayerName;
        Interface = addTabInterface(i + 1);
        Interface.id = i + 1;
        Interface.parentID = 22500;
        Interface.type = 5;
        Interface.atActionType = 0;
        Interface.contentType = 0;
        Interface.opacity = 0;
        Interface.disabledSpriteId = 501 + prayerSpriteID;
        Interface.enabledSpriteId = 481 + prayerSpriteID;
        Interface.width = 34;
        Interface.height = 34;
        Interface.valueCompareType = new int[1];
        Interface.requiredValues = new int[1];
        Interface.valueCompareType[0] = 2;
        Interface.requiredValues[0] = requiredValues + 1;
        Interface.valueIndexArray = new int[1][3];
        Interface.valueIndexArray[0][0] = 2;
        Interface.valueIndexArray[0][1] = 5;
        Interface.valueIndexArray[0][2] = 0;
    }

    public static void curseTab(TextDrawingArea[] TDA) {
        RSInterface Interface = addTabInterface(22500);
        int index = 0;
        addText(687, "99/99", 0xFF981F, false, false, -1, TDA, 1);
        addHDSprite(22502, 481, 481);
        addPrayer(22503, 0, 83, 49, 7, "Protect Item", 22582);
        addPrayer(22505, 0, 84, 49, 4, "Sap Warrior", 22544);
        addPrayer(22507, 0, 85, 51, 5, "Sap Ranger", 22546);
        addPrayer(22509, 0, 101, 53, 3, "Sap Mage", 22548);
        addPrayer(22511, 0, 102, 55, 2, "Sap Spirit", 22550);
        addPrayer(22513, 0, 86, 58, 18, "Berserker", 22552);
        addPrayer(22515, 0, 87, 61, 15, "Deflect Summoning", 22554);
        addPrayer(22517, 0, 88, 64, 17, "Deflect Magic", 22556);
        addPrayer(22519, 0, 89, 67, 16, "Deflect Missiles", 22558);
        addPrayer(22521, 0, 90, 70, 6, "Deflect Melee", 22560);
        addPrayer(22523, 0, 91, 73, 9, "Leech Attack", 22562);
        addPrayer(22525, 0, 103, 75, 10, "Leech Ranged", 22564);
        addPrayer(22527, 0, 104, 77, 11, "Leech Magic", 22566);
        addPrayer(22529, 0, 92, 79, 12, "Leech Defence", 22568);
        addPrayer(22531, 0, 93, 81, 13, "Leech Strength", 22570);
        addPrayer(22533, 0, 94, 83, 14, "Leech Energy", 22572);
        addPrayer(22535, 0, 95, 85, 19, "Leech Special Attack", 22574);
        addPrayer(22537, 0, 96, 88, 1, "Wrath", 22576);
        addPrayer(22539, 0, 97, 91, 8, "Soul Split", 22578);
        addPrayer(22541, 0, 105, 94, 20, "Turmoil", 22580);
        drawTooltip(22582, "Level 50\nProtect Item\nKeep 1 extra item if you die");
        drawTooltip(22544, "Level 50\nSap Warrior\nDrains 10% of enemy Attack,\nStrength and Defence,\nincreasing to 20% over time.");
        drawTooltip(22546, "Level 52\nSap Ranger\nDrains 10% of enemy Ranged\nand Defence, increasing to 20%\nover time.");
        drawTooltip(22548, "Level 54\nSap Mage\nDrains 10% of enemy Magic\nand Defence, increasing to 20%\nover time.");
        drawTooltip(22550, "Level 56\nSap Spirit\nDrains enenmy special attack\nenergy.");
        drawTooltip(22552, "Level 59\nBerserker\nBoosted stats last 15% longer.");
        drawTooltip(22554, "Level 62\nDeflect Summoning\nReduces damage dealt from\nSummoning scrolls, prevents the\nuse of a familiar's special\nattack, and can deflect some of\ndamage back to the attacker.");
        drawTooltip(22556, "Level 65\nDeflect Magic\nProtects against magical attacks\nand can deflect some of the\ndamage back to the attacker.");
        drawTooltip(22558, "Level 68\nDeflect Missiles\nProtects against ranged attacks\nand can deflect some of the\ndamage back to the attacker.");
        drawTooltip(22560, "Level 71\nDeflect Melee\nProtects against melee attacks\nand can deflect some of the\ndamage back to the attacker.");
        drawTooltip(22562, "Level 74\nLeech Attack\nBoosts Attack by 5%, increasing\nto 10% over time, while draining\nenemy Attack by 10%, increasing\nto 25% over time.");
        drawTooltip(22564, "Level 76\nLeech Ranged\nBoosts Ranged by 5%, increasing\nto 10% over time, while draining\nenemy Ranged by 10%,\nincreasing to 25% over\ntime.");
        drawTooltip(22566, "Level 78\nLeech Magic\nBoosts Magic by 5%, increasing\nto 10% over time, while draining\nenemy Magic by 10%, increasing\nto 25% over time.");
        drawTooltip(22568, "Level 80\nLeech Defence\nBoosts Defence by 5%, increasing\nto 10% over time, while draining\n enemy Defence by10%,\nincreasing to 25% over\ntime.");
        drawTooltip(22570, "Level 82\nLeech Strength\nBoosts Strength by 5%, increasing\nto 10% over time, while draining\nenemy Strength by 10%, increasing\n to 25% over time.");
        drawTooltip(22572, "Level 84\nLeech Energy\nDrains enemy run energy, while\nincreasing your own.");
        drawTooltip(22574, "Level 86\nLeech Special Attack\nDrains enemy special attack\nenergy, while increasing your\nown.");
        drawTooltip(22576, "Level 89\nWrath\nInflicts damage to nearby\ntargets if you die.");
        drawTooltip(22578, "Level 92\nSoul Split\n1/4 of damage dealt is also removed\nfrom opponent's Prayer and\nadded to your Hitpoints.");
        drawTooltip(22580, "Level 95\nTurmoil\nIncreases Attack and Defence\nby 15%, plus 15% of enemy's\nlevel, and Strength by 23% plus\n10% of enemy's level.");
        setChildren(62, Interface);

        setBounds(687, 85, 241, index, Interface);
        index++;
        setBounds(22502, 65, 241, index, Interface);
        index++;
        setBounds(22503, 2, 5, index, Interface);
        index++;
        setBounds(22504, 8, 8, index, Interface);
        index++;
        setBounds(22505, 40, 5, index, Interface);
        index++;
        setBounds(22506, 47, 12, index, Interface);
        index++;
        setBounds(22507, 76, 5, index, Interface);
        index++;
        setBounds(22508, 82, 11, index, Interface);
        index++;
        setBounds(22509, 113, 5, index, Interface);
        index++;
        setBounds(22510, 116, 8, index, Interface);
        index++;
        setBounds(22511, 150, 5, index, Interface);
        index++;
        setBounds(22512, 155, 10, index, Interface);
        index++;
        setBounds(22513, 2, 45, index, Interface);
        index++;
        setBounds(22514, 9, 48, index, Interface);
        index++;
        setBounds(22515, 39, 45, index, Interface);
        index++;
        setBounds(22516, 42, 47, index, Interface);
        index++;
        setBounds(22517, 76, 45, index, Interface);
        index++;
        setBounds(22518, 79, 48, index, Interface);
        index++;
        setBounds(22519, 113, 45, index, Interface);
        index++;
        setBounds(22520, 116, 48, index, Interface);
        index++;
        setBounds(22521, 151, 45, index, Interface);
        index++;
        setBounds(22522, 154, 48, index, Interface);
        index++;
        setBounds(22523, 2, 82, index, Interface);
        index++;
        setBounds(22524, 6, 86, index, Interface);
        index++;
        setBounds(22525, 40, 82, index, Interface);
        index++;
        setBounds(22526, 42, 86, index, Interface);
        index++;
        setBounds(22527, 77, 82, index, Interface);
        index++;
        setBounds(22528, 79, 86, index, Interface);
        index++;
        setBounds(22529, 114, 83, index, Interface);
        index++;
        setBounds(22530, 119, 87, index, Interface);
        index++;
        setBounds(22531, 153, 83, index, Interface);
        index++;
        setBounds(22532, 156, 86, index, Interface);
        index++;
        setBounds(22533, 2, 120, index, Interface);
        index++;
        setBounds(22534, 7, 125, index, Interface);
        index++;
        setBounds(22535, 40, 120, index, Interface);
        index++;
        setBounds(22536, 45, 124, index, Interface);
        index++;
        setBounds(22537, 78, 120, index, Interface);
        index++;
        setBounds(22538, 86, 124, index, Interface);
        index++;
        setBounds(22539, 114, 120, index, Interface);
        index++;
        setBounds(22540, 120, 125, index, Interface);
        index++;
        setBounds(22541, 151, 120, index, Interface);
        index++;
        setBounds(22542, 153, 127, index, Interface);
        index++;
        setBounds(22582, 10, 40, index, Interface);
        index++;
        setBounds(22544, 20, 40, index, Interface);
        index++;
        setBounds(22546, 20, 40, index, Interface);
        index++;
        setBounds(22548, 20, 40, index, Interface);
        index++;
        setBounds(22550, 20, 40, index, Interface);
        index++;
        setBounds(22552, 10, 80, index, Interface);
        index++;
        setBounds(22554, 10, 80, index, Interface);
        index++;
        setBounds(22556, 10, 80, index, Interface);
        index++;
        setBounds(22558, 10, 80, index, Interface);
        index++;
        setBounds(22560, 10, 80, index, Interface);
        index++;
        setBounds(22562, 10, 120, index, Interface);
        index++;
        setBounds(22564, 10, 120, index, Interface);
        index++;
        setBounds(22566, 10, 120, index, Interface);
        index++;
        setBounds(22568, 5, 120, index, Interface);
        index++;
        setBounds(22570, 5, 120, index, Interface);
        index++;
        setBounds(22572, 10, 160, index, Interface);
        index++;
        setBounds(22574, 10, 160, index, Interface);
        index++;
        setBounds(22576, 10, 160, index, Interface);
        index++;
        setBounds(22578, 10, 160, index, Interface);
        index++;
        setBounds(22580, 10, 160, index, Interface);
        index++;
    }

    public static void emoteTab() {
        RSInterface tab = addTabInterface(147);
        RSInterface scroll = addTabInterface(148);
        tab.totalChildren(1);
        tab.child(0, 148, 0, 1);
        addButton(168, 337, "Yes", 41, 47);
        addButton(169, 338, "No", 41, 47);
        addButton(164, 339, "Bow", 41, 47);
        addButton(165, 340, "Angry", 41, 47);
        addButton(162, 341, "Think", 41, 47);
        addButton(163, 342, "Wave", 41, 47);
        addButton(13370, 343, "Shrug", 41, 47);
        addButton(171, 344, "Cheer", 41, 47);
        addButton(167, 345, "Beckon", 41, 47);
        addButton(170, 346, "Laugh", 41, 47);
        addButton(13366, 347, "Jump for Joy", 41, 47);
        addButton(13368, 374, "Yawn", 41, 47);
        addButton(166, 348, "Dance", 41, 47);
        addButton(13363, 349, "Jig", 41, 47);
        addButton(13364, 350, "Spin", 41, 47);
        addButton(13365, 351, "Headbang", 41, 47);
        addButton(161, 352, "Cry", 41, 47);
        addButton(11100, 353, "Blow kiss", 41, 47);
        addButton(13362, 354, "Panic", 41, 47);
        addButton(13367, 355, "Raspberry", 41, 47);
        addButton(172, 356, "Clap", 41, 47);
        addButton(13369, 357, "Salute", 41, 47);
        addButton(13383, 358, "Goblin Bow", 41, 47);
        addButton(13384, 359, "Goblin Salute", 41, 47);
        addButton(667, 360, "Glass Box", 41, 47);
        addButton(6503, 361, "Climb Rope", 41, 47);
        addButton(6506, 362, "Lean On Air", 41, 47);
        addButton(666, 363, "Glass Wall", 41, 47);
        addButton(18464, 364, "Zombie Walk", 41, 47);
        addButton(18465, 365, "Zombie Dance", 41, 47);
        addButton(15166, 366, "Scared", 41, 47);
        addButton(18686, 367, "Rabbit Hop", 41, 47);
        addButton(154, 368, "Skillcape Emote", 41, 47);
        scroll.totalChildren(33);
        scroll.child(0, 168, 10, 7);
        scroll.child(1, 169, 54, 7);
        scroll.child(2, 164, 98, 14);
        scroll.child(3, 165, 137, 7);
        scroll.child(4, 162, 9, 56);
        scroll.child(5, 163, 48, 56);
        scroll.child(6, 13370, 95, 56);
        scroll.child(7, 171, 137, 56);
        scroll.child(8, 167, 7, 105);
        scroll.child(9, 170, 51, 105);
        scroll.child(10, 13366, 95, 104);
        scroll.child(11, 13368, 139, 105);
        scroll.child(12, 166, 6, 154);
        scroll.child(13, 13363, 50, 154);
        scroll.child(14, 13364, 90, 154);
        scroll.child(15, 13365, 135, 154);
        scroll.child(16, 161, 8, 204);
        scroll.child(17, 11100, 51, 203);
        scroll.child(18, 13362, 99, 204);
        scroll.child(19, 13367, 137, 203);
        scroll.child(20, 172, 10, 253);
        scroll.child(21, 13369, 53, 253);
        scroll.child(22, 13383, 88, 258);
        scroll.child(23, 13384, 138, 252);
        scroll.child(24, 667, 2, 303);
        scroll.child(25, 6503, 49, 302);
        scroll.child(26, 6506, 93, 302);
        scroll.child(27, 666, 137, 302);
        scroll.child(28, 18464, 9, 352);
        scroll.child(29, 18465, 50, 352);
        scroll.child(30, 15166, 94, 356);
        scroll.child(31, 18686, 141, 353);
        scroll.child(32, 154, 5, 401);
        scroll.width = 173;
        scroll.height = 258;
        scroll.scrollMax = 460;
    }

    public static void optionTab(TextDrawingArea[] tda) {
        RSInterface tab = addTabInterface(904);
        RSInterface energy = interfaceCache[149];
        energy.disabledColor = 0xff9933;
        addHDSprite(905, 377, 377);
        addHDSprite(907, 378, 378);
        addHDSprite(909, 379, 379);
        addHDSprite(951, 380, 380);
        addHDSprite(953, 381, 381);
        addHDSprite(955, 382, 382);
        addHDSprite(947, 383, 383);
        addHDSprite(949, 384, 384);
        // run button here
        addButton(950, 376, 376, 40, 39, "Graphic Options", 5);
        addConfigButton(152, 904, 385, 386, 40, 40, "Toggle-run", 1, 5, 173);
        addConfigButton(906, 904, 387, 388, 32, 16, "Dark", 1, 5, 166);
        addConfigButton(908, 904, 389, 390, 32, 16, "Normal", 2, 5, 166);
        addConfigButton(910, 904, 391, 392, 32, 16, "Bright", 3, 5, 166);
        addConfigButton(912, 904, 393, 394, 32, 16, "Very Bright", 4, 5, 166);
        addConfigButton(930, 904, 395, 396, 26, 16, "Music Off", 4, 5, 168);
        addConfigButton(931, 904, 397, 398, 26, 16, "Music Level-1", 3, 5, 168);
        addConfigButton(932, 904, 399, 400, 26, 16, "Music Level-2", 2, 5, 168);
        addConfigButton(933, 904, 401, 402, 26, 16, "Music Level-3", 1, 5, 168);
        addConfigButton(934, 904, 403, 404, 24, 16, "Music Level-4", 0, 5, 168);
        addConfigButton(941, 904, 395, 396, 26, 16, "Sound Effects Off", 4, 5, 169);
        addConfigButton(942, 904, 397, 398, 26, 16, "Sound Effects Level-1", 3, 5, 169);
        addConfigButton(943, 904, 399, 400, 26, 16, "Sound Effects Level-2", 2, 5, 169);
        addConfigButton(944, 904, 401, 402, 26, 16, "Sound Effects Level-3", 1, 5, 169);
        addConfigButton(945, 904, 403, 404, 24, 16, "Sound Effects Level-4", 0, 5, 169);
        addConfigButton(913, 904, 385, 386, 40, 40, "Toggle-Mouse Buttons", 0, 5, 170);
        addConfigButton(915, 904, 385, 386, 40, 40, "Toggle-Chat Effects", 0, 5, 171);
        addConfigButton(957, 904, 385, 386, 40, 40, "Toggle-Split Private Chat", 1, 5, 287);
        addConfigButton(12464, 904, 385, 386, 40, 40, "Toggle-Accept Aid", 0, 5, 427);
        tab.totalChildren(29);
        int x = 0;
        int y = 2;
        tab.child(0, 905, 13 + x, 10 + y);
        tab.child(1, 906, 48 + x, 18 + y);
        tab.child(2, 908, 80 + x, 18 + y);
        tab.child(3, 910, 112 + x, 18 + y);
        tab.child(4, 912, 144 + x, 18 + y);
        tab.child(5, 907, 14 + x, 55 + y);
        tab.child(6, 930, 49 + x, 61 + y);
        tab.child(7, 931, 75 + x, 61 + y);
        tab.child(8, 932, 101 + x, 61 + y);
        tab.child(9, 933, 127 + x, 61 + y);
        tab.child(10, 934, 151 + x, 61 + y);
        tab.child(11, 909, 13 + x, 99 + y);
        tab.child(12, 941, 49 + x, 104 + y);
        tab.child(13, 942, 75 + x, 104 + y);
        tab.child(14, 943, 101 + x, 104 + y);
        tab.child(15, 944, 127 + x, 104 + y);
        tab.child(16, 945, 151 + x, 104 + y);
        tab.child(17, 913, 15, 153);
        tab.child(18, 955, 19, 159);
        tab.child(19, 915, 75, 153);
        tab.child(20, 953, 79, 160);
        tab.child(21, 957, 135, 153);
        tab.child(22, 951, 139, 159);
        tab.child(23, 12464, 15, 208);
        tab.child(24, 949, 20, 213);
        tab.child(25, 152, 75, 208);
        tab.child(26, 947, 87, 212);
        tab.child(27, 149, 80, 231);
        tab.child(28, 950, 135, 208);
    }

    public static void addSprite(int id, int spriteId1, int spriteId2, String spriteName) {
        RSInterface tab = interfaceCache[id] = new RSInterface();
        tab.id = id;
        tab.parentID = id;
        tab.type = 5;
        tab.atActionType = 0;
        tab.contentType = 0;
        tab.opacity = (byte) 0;
        tab.hoverType = 52;
        tab.disabledSprite = imageLoader(spriteId1, spriteName);
        tab.enabledSprite = imageLoader(spriteId2, spriteName);
        for (int i = 0; i < 10; i++) {
            tab.savedSprite[i] = imageLoader(i, spriteName);
        }
        tab.width = 512;
        tab.height = 334;
    }

    /*
     * Clan chat tab interface
     */
    private static void clanChatTabInterface() {
        RSInterface tab = addInterface(29328);
        addHoverButtonWSpriteLoader(29329, 728, 18, 18, "Join Clan", -1, 29330, 1);
        addHoveredImageWSpriteLoader(29330, 729, 18, 18, 29331);
        addHoverButtonWSpriteLoader(29332, 730, 18, 18, "Leave Clan", -1, 29333, 1);
        addHoveredImageWSpriteLoader(29333, 731, 18, 18, 29334);
        addHoverButtonWSpriteLoader(29335, 732, 18, 18, "Settings", -1, 29336, 1);
        addHoveredImageWSpriteLoader(29336, 733, 18, 18, 29337);
        addButtonWSpriteLoader(29455, 739, "Toggle Lootshare");
        addText(29338, "Clan Chat", 0xff9b00, true, true, fonts[1]);
        addText(29340, "Talking in: @whi@Not in chat", 0xff9b00, false, true, fonts[0]);
        addText(29454, "Lootshare: @gre@On", 0xff9b00, false, true, 52, fonts, 0);
        addText(29450, "Owner: None", 0xff9b00, false, true, fonts[0]);
        addSpriteLoader(29339, 734);
        tab.totalChildren(15);
        tab.child(0, 16126, 0, 236);
        tab.child(1, 16126, 0, 62);
        tab.child(2, 29339, 0, 62);
        tab.child(3, 29343, 0, 62);
        tab.child(4, 29329, 8, 239);
        tab.child(5, 29330, 8, 239);
        tab.child(6, 29332, 25, 239);
        tab.child(7, 29333, 25, 239);
        tab.child(8, 29335, 42, 239);
        tab.child(9, 29336, 42, 239);
        tab.child(10, 29338, 95, 1);
        tab.child(11, 29340, 10, 15);
        tab.child(12, 29450, 10, 41);
        tab.child(13, 29454, 10, 28);
        tab.child(14, 29455, 150, 23);
        rebuildClanChatList(false, "", false);
    }

    public static void rebuildClanChatList(boolean clickable, String ignore, boolean owner) {
        /* Text area */
        for (int i = 29344; i <= 29444; i++) {
            if (clickable && RSInterface.interfaceCache[i].message.length() > 0) {
                addClanChatListTextWithOptions(i, RSInterface.interfaceCache[i].message, ignore, owner, RSInterface.fonts, 0, 0xffffff, 200, 11);
            } else {
                addText(i, RSInterface.interfaceCache[i] == null ? "" + i + "" : RSInterface.interfaceCache[i].message, fonts, 0, 0xffffff, false, true);
            }
        }
        RSInterface list = addInterface(29343);
        list.totalChildren(100);
        for (int id = 29344, i = 0; id <= 29443 && i <= 99; id++, i++) {
            list.child(id - 29344, id, 5, -1);
            for (int id2 = 29344, i2 = 1; id2 <= 29443 && i2 <= 99; id2++, i2++) {
                list.childY[0] = 2;
                list.childY[i2] = list.childY[i2 - 1] + 14;
            }
        }
        list.height = 174;
        list.width = 174;
        list.scrollMax = 1405;
    }

    public static void addText(int childId, String text, int color, boolean center, boolean shadow, TextDrawingArea rsFont) {
        RSInterface rsi = RSInterface.addInterface(childId);
        rsi.parentID = childId;
        rsi.id = childId;
        rsi.type = 4;
        rsi.atActionType = 0;
        rsi.width = 0;
        rsi.height = 11;
        rsi.contentType = 0;
        rsi.opacity = 0;
        rsi.hoverType = -1;
        rsi.centerText = center;
        rsi.shadowed = shadow;
        rsi.textDrawingAreas = rsFont;
        rsi.message = "";
        rsi.disabledColor = color;
    }

    public static void addHoverText(int id, String text, String tooltip, TextDrawingArea tda[], int idx, int color, boolean center, boolean textShadowed, int width, int height) {
        RSInterface rsinterface = addInterface(id);
        rsinterface.id = id;
        rsinterface.parentID = id;
        rsinterface.type = 4;
        rsinterface.atActionType = 1;
        rsinterface.width = width;
        rsinterface.height = height;
        rsinterface.contentType = 0;
        rsinterface.opacity = 0;
        rsinterface.hoverType = -1;
        rsinterface.centerText = center;
        rsinterface.shadowed = textShadowed;
        rsinterface.textDrawingAreas = tda[idx];
        rsinterface.message = text;
        rsinterface.enabledMessage = "";
        rsinterface.disabledColor = color;
        rsinterface.enabledColor = 0;
        rsinterface.disabledMouseOverColor = 0xffffff;
        rsinterface.enabledMouseOverColor = 0;
        rsinterface.tooltip = tooltip;
    }

    public static void addText(int id, String text, TextDrawingArea tda[], int idx, int color, boolean center, boolean shadow, boolean cc) {
        RSInterface tab = addTabInterface(id);
        tab.parentID = id;
        tab.id = id;
        tab.type = 4;
        tab.atActionType = 0;
        tab.width = 0;
        tab.height = 11;
        tab.contentType = 0;
        tab.opacity = 0;
        tab.hoverType = -1;
        tab.centerText = center;
        tab.shadowed = shadow;
        tab.textDrawingAreas = tda[idx];
        tab.message = text;
        tab.enabledMessage = "";
        tab.disabledColor = color;
        tab.enabledColor = 0;
        tab.disabledMouseOverColor = 0;
        tab.enabledMouseOverColor = 0;
    }

    public static void addText(int id, String message, int disabledColor, boolean centerText, boolean shadowed, int hoverType, TextDrawingArea[] TDA, int fontId) {
        RSInterface RSInterface = addInterface(id);
        RSInterface.parentID = id;
        RSInterface.id = id;
        RSInterface.type = 4;
        RSInterface.atActionType = 0;
        RSInterface.width = 0;
        RSInterface.height = 0;
        RSInterface.contentType = 0;
        RSInterface.opacity = 0;
        RSInterface.hoverType = hoverType;
        RSInterface.centerText = centerText;
        RSInterface.shadowed = shadowed;
        RSInterface.textDrawingAreas = TDA[fontId];
        RSInterface.message = message;
        RSInterface.enabledMessage = "";
        RSInterface.disabledColor = disabledColor;
    }

    public static void Pestpanel(TextDrawingArea[] tda) {
        RSInterface RSinterface = addInterface(21119);
        addText(21120, "What", 0x999999, false, true, 52, tda, 1);
        addText(21121, "What", 0x33cc00, false, true, 52, tda, 1);
        addText(21122, "(Need 5 to 25 players)", 0xFFcc33, false, true, 52, tda, 1);
        addText(21123, "Points", 0x33ccff, false, true, 52, tda, 1);
        int last = 4;
        RSinterface.children = new int[last];
        RSinterface.childX = new int[last];
        RSinterface.childY = new int[last];
        setBounds(21120, 15, 12, 0, RSinterface);
        setBounds(21121, 15, 30, 1, RSinterface);
        setBounds(21122, 15, 48, 2, RSinterface);
        setBounds(21123, 15, 66, 3, RSinterface);
    }

    public static void Pestpanel2(TextDrawingArea[] tda) {
        RSInterface RSinterface = addInterface(21100);
        addHDSprite(21101, 431, 431);
        addHDSprite(21102, 432, 432);
        addHDSprite(21103, 433, 433);
        addHDSprite(21104, 434, 434);
        addHDSprite(21105, 435, 435);
        addHDSprite(21106, 436, 436);
        addText(21107, "", 0xCC00CC, false, true, 52, tda, 1);
        addText(21108, "", 0x0000FF, false, true, 52, tda, 1);
        addText(21109, "", 0xFFFF44, false, true, 52, tda, 1);
        addText(21110, "", 0xCC0000, false, true, 52, tda, 1);
        addText(21111, "250", 0x99FF33, false, true, 52, tda, 1);// w purp
        addText(21112, "250", 0x99FF33, false, true, 52, tda, 1);// e blue
        addText(21113, "250", 0x99FF33, false, true, 52, tda, 1);// se yel
        addText(21114, "250", 0x99FF33, false, true, 52, tda, 1);// sw red
        addText(21115, "200", 0x99FF33, false, true, 52, tda, 1);// attacks
        addText(21116, "0", 0x99FF33, false, true, 52, tda, 1);// knights hp
        addText(21117, "Time Remaining:", 0xFFFFFF, false, true, 52, tda, 0);
        addText(21118, "", 0xFFFFFF, false, true, 52, tda, 0);
        int last = 18;
        RSinterface.children = new int[last];
        RSinterface.childX = new int[last];
        RSinterface.childY = new int[last];
        setBounds(21101, 361, 26, 0, RSinterface);
        setBounds(21102, 396, 26, 1, RSinterface);
        setBounds(21103, 436, 26, 2, RSinterface);
        setBounds(21104, 474, 26, 3, RSinterface);
        setBounds(21105, 3, 21, 4, RSinterface);
        setBounds(21106, 3, 50, 5, RSinterface);
        setBounds(21107, 371, 60, 6, RSinterface);
        setBounds(21108, 409, 60, 7, RSinterface);
        setBounds(21109, 443, 60, 8, RSinterface);
        setBounds(21110, 479, 60, 9, RSinterface);
        setBounds(21111, 362, 10, 10, RSinterface);
        setBounds(21112, 398, 10, 11, RSinterface);
        setBounds(21113, 436, 10, 12, RSinterface);
        setBounds(21114, 475, 10, 13, RSinterface);
        setBounds(21115, 32, 32, 14, RSinterface);
        setBounds(21116, 32, 62, 15, RSinterface);
        setBounds(21117, 8, 88, 16, RSinterface);
        setBounds(21118, 87, 88, 17, RSinterface);
    }

    public static void addHoverBox(int id, String text) {
        RSInterface rsi = interfaceCache[id];// addTabInterface(id);
        rsi.id = id;
        rsi.parentID = id;
        rsi.hidden = true;
        rsi.type = 8;
        rsi.hoverText = text;
    }

    public static RSInterface addText(int id, String text, TextDrawingArea tda[], int idx, int color, boolean center, boolean shadow) {
        RSInterface tab = addTabInterface(id);
        tab.parentID = id;
        tab.id = id;
        tab.type = 4;
        tab.atActionType = 0;
        tab.width = 0;
        tab.height = 11;
        tab.contentType = 0;
        tab.opacity = 0;
        tab.hoverType = -1;
        tab.centerText = center;
        tab.shadowed = shadow;
        tab.textDrawingAreas = tda[idx];
        tab.message = text;
        tab.enabledMessage = "";
        tab.disabledColor = color;
        tab.enabledColor = 0;
        tab.disabledMouseOverColor = 0;
        tab.enabledMouseOverColor = 0;
        return tab;
    }

    public static void addButton(int id, int sid, String spriteName, String tooltip, int w, int h) {
        RSInterface tab = interfaceCache[id] = new RSInterface();
        tab.id = id;
        tab.parentID = id;
        tab.type = 5;
        tab.atActionType = 1;
        tab.contentType = 0;
        tab.opacity = (byte) 0;
        tab.hoverType = 52;
        tab.disabledSprite = imageLoader(sid, spriteName);
        tab.enabledSprite = imageLoader(sid, spriteName);
        tab.width = w;
        tab.height = h;
        tab.tooltip = tooltip;
    }

    public static void addButtonWSpriteLoader(int id, int sprite, String tooltip, int w, int h) {
        RSInterface tab = interfaceCache[id] = new RSInterface();
        tab.id = id;
        tab.parentID = id;
        tab.type = 5;
        tab.atActionType = 1;
        tab.contentType = 0;
        tab.opacity = (byte) 0;
        tab.hoverType = 52;
        if (sprite != -1) {
            tab.disabledSprite = SpriteLoader.sprites[sprite];
            tab.enabledSprite = SpriteLoader.sprites[sprite];
        }
        tab.width = w;
        tab.height = h;
        tab.tooltip = tooltip;
    }

    public static void addButton(int id, int sid, String tooltip, int w, int h) {
        RSInterface tab = interfaceCache[id] = new RSInterface();
        tab.id = id;
        tab.parentID = id;
        tab.type = 5;
        tab.atActionType = 1;
        tab.contentType = 0;
        tab.opacity = (byte) 0;
        tab.hoverType = 52;
        tab.disabledSpriteId = sid;
        tab.enabledSpriteId = sid;
        tab.width = w;
        tab.height = h;
        tab.tooltip = tooltip;
    }

    public static void addConfigButton(int ID, int pID, int bID, int bID2, int width, int height, String tT, int configID, int aT, int configFrame) {
        RSInterface Tab = addTabInterface(ID);
        Tab.parentID = pID;
        Tab.id = ID;
        Tab.type = 5;
        Tab.atActionType = aT;
        Tab.contentType = 0;
        Tab.width = width;
        Tab.height = height;
        Tab.opacity = 0;
        Tab.hoverType = -1;
        Tab.valueCompareType = new int[1];
        Tab.requiredValues = new int[1];
        Tab.valueCompareType[0] = 1;
        Tab.requiredValues[0] = configID;
        Tab.valueIndexArray = new int[1][3];
        Tab.valueIndexArray[0][0] = 5;
        Tab.valueIndexArray[0][1] = configFrame;
        Tab.valueIndexArray[0][2] = 0;
        Tab.disabledSpriteId = bID;
        Tab.enabledSpriteId = bID2;
        Tab.tooltip = tT;
    }

    public static void addSprite(int id, int spriteId, String spriteName) {
        addSprite(id, spriteId, spriteName, -1, -1);
    }

    public static void addSkillChatSprite(int id, int skill) {
        addSpriteLoader(id, 779 + skill);
    }

    public static void addHoverButton(int i, int disabledSprite, int enabledSprite, int width, int height, String text, int contentType, int hoverOver, int aT) {// hoverable
        // button
        RSInterface tab = addTabInterface(i);
        tab.id = i;
        tab.parentID = i;
        tab.type = 5;
        tab.atActionType = aT;
        tab.contentType = contentType;
        tab.opacity = 0;
        tab.hoverType = hoverOver;
        tab.disabledSpriteId = disabledSprite;
        tab.enabledSpriteId = enabledSprite;
        tab.width = width;
        tab.height = height;
        tab.tooltip = text;
    }

    public static void addHoveredButton(int i, int disabledSprite, int enabledSprite, int w, int h, int IMAGEID) {
        RSInterface tab = addTabInterface(i);
        tab.parentID = i;
        tab.id = i;
        tab.type = 0;
        tab.atActionType = 0;
        tab.width = w;
        tab.height = h;
        tab.hidden = true;
        tab.opacity = 0;
        tab.hoverType = -1;
        tab.scrollMax = 0;
        addHoverImage(IMAGEID, disabledSprite, enabledSprite);
        tab.totalChildren(1);
        tab.child(0, IMAGEID, 0, 0);
    }

    public static void addHoverButton(int i, String imageName, int j, int width, int height, String text, int contentType, int hoverOver, int aT) {// hoverable
        // button
        RSInterface tab = addTabInterface(i);
        tab.id = i;
        tab.parentID = i;
        tab.type = 5;
        tab.atActionType = aT;
        tab.contentType = contentType;
        tab.opacity = 0;
        tab.hoverType = hoverOver;
        tab.disabledSprite = imageLoader(j, imageName);
        tab.enabledSprite = imageLoader(j, imageName);
        tab.width = width;
        tab.height = height;
        tab.tooltip = text;
    }

    public static void addTransparentSpriteWSpriteLoader(int id, int spriteId, int opacity) {
        RSInterface tab = interfaceCache[id] = new RSInterface();
        tab.id = id;
        tab.parentID = id;
        tab.type = 9;
        tab.atActionType = 0;
        tab.contentType = 0;
        tab.opacity = (byte) opacity;
        tab.customOpacity = opacity;
        tab.hoverType = 52;
        tab.disabledSprite = SpriteLoader.sprites[spriteId];
        tab.enabledSprite = SpriteLoader.sprites[spriteId];
        tab.width = 512;
        tab.height = 334;
        tab.drawsTransparent = true;
    }

    public static void addHoverButtonWSpriteLoader(int i, int spriteId, int width, int height, String text, int contentType, int hoverOver, int aT) {// hoverable
        // button
        RSInterface tab = addTabInterface(i);
        tab.id = i;
        tab.parentID = i;
        tab.type = 5;
        tab.atActionType = aT;
        tab.contentType = contentType;
        tab.opacity = 0;
        tab.hoverType = hoverOver;
        tab.disabledSprite = SpriteLoader.sprites[spriteId];
        tab.enabledSprite = SpriteLoader.sprites[spriteId];
        tab.width = width;
        tab.height = height;
        tab.tooltip = text;
    }

    public static void addButtonWSpriteLoader(int id, int spriteId, String tooltip) {
        RSInterface tab = interfaceCache[id] = new RSInterface();
        tab.id = id;
        tab.parentID = id;
        tab.type = 5;
        tab.atActionType = 1;
        tab.contentType = 0;
        tab.opacity = (byte) 0;
        tab.hoverType = 52;
        tab.disabledSprite = SpriteLoader.sprites[spriteId];
        tab.enabledSprite = SpriteLoader.sprites[spriteId];
        tab.width = tab.disabledSprite.myWidth;
        tab.height = tab.enabledSprite.myHeight - 2;
        tab.tooltip = tooltip;
    }

    public static void addButtonWSpriteLoader(int id, int spriteId, int spriteId2, String tooltip) {
        RSInterface tab = interfaceCache[id] = new RSInterface();
        tab.id = id;
        tab.parentID = id;
        tab.type = 5;
        tab.atActionType = 1;
        tab.contentType = 0;
        tab.opacity = (byte) 0;
        tab.hoverType = 52;
        tab.disabledSprite = SpriteLoader.sprites[spriteId2];
        tab.enabledSprite = SpriteLoader.sprites[spriteId];
        tab.width = tab.disabledSprite.myWidth;
        tab.height = tab.enabledSprite.myHeight - 2;
        tab.tooltip = tooltip;
    }

    public static void addHoveredImageWSpriteLoader(int i, int spriteId, int w, int h, int imgInterface) {
        RSInterface tab = addTabInterface(i);
        tab.id = i;
        tab.parentID = i;
        tab.type = 0;
        tab.atActionType = 0;
        tab.contentType = 0;
        tab.opacity = 0;
        tab.hoverType = -1;
        tab.scrollMax = 0;
        tab.hidden = true;
        tab.width = w;
        tab.height = h;
        addHoverImageWSpriteLoader(imgInterface, spriteId);
        tab.totalChildren(1);
        tab.child(0, imgInterface, 0, 0);
    }

    public static void addHoverImageWSpriteLoader(int i, int spriteId) {
        RSInterface tab = addTabInterface(i);
        tab.id = i;
        tab.parentID = i;
        tab.type = 5;
        tab.atActionType = 0;
        tab.contentType = 0;
        tab.width = 512;
        tab.height = 334;
        tab.opacity = 0;
        tab.hoverType = 52;
        tab.disabledSprite = SpriteLoader.sprites[spriteId];
        tab.enabledSprite = SpriteLoader.sprites[spriteId];
    }

    public static void addHoveredButton(int id, String imageName, int imgId, int w, int h, int imgIfaceId) {
        RSInterface tab = addTabInterface(id);
        tab.parentID = id;
        tab.id = id;
        tab.type = 0;
        tab.atActionType = 0;
        tab.contentType = 0;
        tab.width = w;
        tab.height = h;
        tab.hidden = true;
        tab.opacity = 0;
        tab.hoverType = -1;
        tab.scrollMax = 0;
        if (id != 24655) {
            addHoverImage(imgIfaceId, imgId, imgId, imageName);
        } else {
            addHoverImage(1, imgIfaceId, imgId, imgId, imageName);
        }
        tab.totalChildren(1);
        tab.child(0, imgIfaceId, 0, 0);
    }

    public static void addLunarSprite(int i, int j) {
        RSInterface RSInterface = addTabInterface(i);
        RSInterface.id = i;
        RSInterface.parentID = i;
        RSInterface.type = 5;
        RSInterface.atActionType = 0;
        RSInterface.contentType = 0;
        RSInterface.opacity = 0;
        RSInterface.hoverType = 52;
        RSInterface.disabledSpriteId = j;
        RSInterface.enabledSpriteId = j;
        RSInterface.width = 500;
        RSInterface.height = 500;
        RSInterface.tooltip = "";
    }

    public static void drawRune(int i, int id, String runeName) {
        RSInterface RSInterface = addTabInterface(i);
        RSInterface.type = 5;
        RSInterface.atActionType = 0;
        RSInterface.contentType = 0;
        RSInterface.opacity = 0;
        RSInterface.hoverType = 52;
        RSInterface.disabledSpriteId = 195 + id - 1;
        RSInterface.width = 500;
        RSInterface.height = 500;
    }

    public static void addRuneText(int ID, int runeAmount, int RuneID, TextDrawingArea[] font) {
        RSInterface rsInterface = addTabInterface(ID);
        rsInterface.id = ID;
        rsInterface.parentID = 1151;
        rsInterface.type = 4;
        rsInterface.atActionType = 0;
        rsInterface.contentType = 0;
        rsInterface.width = 0;
        rsInterface.height = 14;
        rsInterface.opacity = 0;
        rsInterface.hoverType = -1;
        rsInterface.valueCompareType = new int[1];
        rsInterface.requiredValues = new int[1];
        rsInterface.valueCompareType[0] = 3;
        rsInterface.requiredValues[0] = runeAmount;
        rsInterface.valueIndexArray = new int[1][4];
        rsInterface.valueIndexArray[0][0] = 4;
        rsInterface.valueIndexArray[0][1] = 3214;
        rsInterface.valueIndexArray[0][2] = RuneID;
        rsInterface.valueIndexArray[0][3] = 0;
        rsInterface.centerText = true;
        rsInterface.textDrawingAreas = font[0];
        rsInterface.shadowed = true;
        rsInterface.message = "%1/" + runeAmount + "";
        rsInterface.enabledMessage = "";
        rsInterface.disabledColor = 12582912;
        rsInterface.enabledColor = 49152;
    }

    public static void addLunar2RunesSmallBox(int ID, int r1, int r2, int ra1, int ra2, int rune1, int lvl, String name, String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
        RSInterface rsInterface = addTabInterface(ID);
        rsInterface.id = ID;
        rsInterface.parentID = 1151;
        rsInterface.type = 5;
        rsInterface.atActionType = type;
        rsInterface.contentType = 0;
        rsInterface.hoverType = ID + 1;
        rsInterface.spellUsableOn = suo;
        rsInterface.selectedActionName = "Cast On";
        rsInterface.width = 20;
        rsInterface.height = 20;
        rsInterface.tooltip = "Cast @gre@" + name;
        rsInterface.spellName = name;
        rsInterface.valueCompareType = new int[3];
        rsInterface.requiredValues = new int[3];
        rsInterface.valueCompareType[0] = 3;
        rsInterface.requiredValues[0] = ra1;
        rsInterface.valueCompareType[1] = 3;
        rsInterface.requiredValues[1] = ra2;
        rsInterface.valueCompareType[2] = 3;
        rsInterface.requiredValues[2] = lvl;
        rsInterface.valueIndexArray = new int[3][];
        rsInterface.valueIndexArray[0] = new int[4];
        rsInterface.valueIndexArray[0][0] = 4;
        rsInterface.valueIndexArray[0][1] = 3214;
        rsInterface.valueIndexArray[0][2] = r1;
        rsInterface.valueIndexArray[0][3] = 0;
        rsInterface.valueIndexArray[1] = new int[4];
        rsInterface.valueIndexArray[1][0] = 4;
        rsInterface.valueIndexArray[1][1] = 3214;
        rsInterface.valueIndexArray[1][2] = r2;
        rsInterface.valueIndexArray[1][3] = 0;
        rsInterface.valueIndexArray[2] = new int[3];
        rsInterface.valueIndexArray[2][0] = 1;
        rsInterface.valueIndexArray[2][1] = 6;
        rsInterface.valueIndexArray[2][2] = 0;
        rsInterface.enabledSpriteId = sid + 248;
        rsInterface.disabledSpriteId = sid + 209;
        RSInterface hover = addTabInterface(ID + 1);
        hover.hoverType = -1;
        hover.hidden = true;
        setChildren(7, hover);
        addLunarSprite(ID + 2, 289);
        setBounds(ID + 2, 0, 0, 0, hover);
        addText(ID + 3, "Level " + (lvl + 1) + ": " + name, 0xFF981F, true, true, 52, 1);
        setBounds(ID + 3, 90, 4, 1, hover);
        addText(ID + 4, descr, 0xAF6A1A, true, true, 52, 0);
        setBounds(ID + 4, 90, 19, 2, hover);
        setBounds(30016, 37, 35, 3, hover);// Rune
        setBounds(rune1, 112, 35, 4, hover);// Rune
        addRuneText(ID + 5, ra1 + 1, r1, TDA);
        setBounds(ID + 5, 50, 66, 5, hover);
        addRuneText(ID + 6, ra2 + 1, r2, TDA);
        setBounds(ID + 6, 123, 66, 6, hover);

    }

    public static void addLunar3RunesSmallBox(int ID, int r1, int r2, int r3, int ra1, int ra2, int ra3, int rune1, int rune2, int lvl, String name, String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
        RSInterface rsInterface = addTabInterface(ID);
        rsInterface.id = ID;
        rsInterface.parentID = 1151;
        rsInterface.type = 5;
        rsInterface.atActionType = type;
        rsInterface.contentType = 0;
        rsInterface.hoverType = ID + 1;
        rsInterface.spellUsableOn = suo;
        rsInterface.selectedActionName = "Cast on";
        rsInterface.width = 20;
        rsInterface.height = 20;
        rsInterface.tooltip = "Cast @gre@" + name;
        rsInterface.spellName = name;
        rsInterface.valueCompareType = new int[4];
        rsInterface.requiredValues = new int[4];
        rsInterface.valueCompareType[0] = 3;
        rsInterface.requiredValues[0] = ra1;
        rsInterface.valueCompareType[1] = 3;
        rsInterface.requiredValues[1] = ra2;
        rsInterface.valueCompareType[2] = 3;
        rsInterface.requiredValues[2] = ra3;
        rsInterface.valueCompareType[3] = 3;
        rsInterface.requiredValues[3] = lvl;
        rsInterface.valueIndexArray = new int[4][];
        rsInterface.valueIndexArray[0] = new int[4];
        rsInterface.valueIndexArray[0][0] = 4;
        rsInterface.valueIndexArray[0][1] = 3214;
        rsInterface.valueIndexArray[0][2] = r1;
        rsInterface.valueIndexArray[0][3] = 0;
        rsInterface.valueIndexArray[1] = new int[4];
        rsInterface.valueIndexArray[1][0] = 4;
        rsInterface.valueIndexArray[1][1] = 3214;
        rsInterface.valueIndexArray[1][2] = r2;
        rsInterface.valueIndexArray[1][3] = 0;
        rsInterface.valueIndexArray[2] = new int[4];
        rsInterface.valueIndexArray[2][0] = 4;
        rsInterface.valueIndexArray[2][1] = 3214;
        rsInterface.valueIndexArray[2][2] = r3;
        rsInterface.valueIndexArray[2][3] = 0;
        rsInterface.valueIndexArray[3] = new int[3];
        rsInterface.valueIndexArray[3][0] = 1;
        rsInterface.valueIndexArray[3][1] = 6;
        rsInterface.valueIndexArray[3][2] = 0;
        rsInterface.enabledSpriteId = sid + 248;
        rsInterface.disabledSpriteId = sid + 209;
        RSInterface hover = addTabInterface(ID + 1);
        hover.hoverType = -1;
        hover.hidden = true;
        setChildren(9, hover);
        addLunarSprite(ID + 2, 289);
        setBounds(ID + 2, 0, 0, 0, hover);
        addText(ID + 3, "Level " + (lvl + 1) + ": " + name, 0xFF981F, true, true, 52, 1);
        setBounds(ID + 3, 90, 4, 1, hover);
        addText(ID + 4, descr, 0xAF6A1A, true, true, 52, 0);
        setBounds(ID + 4, 90, 19, 2, hover);
        setBounds(30016, 14, 35, 3, hover);
        setBounds(rune1, 74, 35, 4, hover);
        setBounds(rune2, 130, 35, 5, hover);
        addRuneText(ID + 5, ra1 + 1, r1, TDA);
        setBounds(ID + 5, 26, 66, 6, hover);
        addRuneText(ID + 6, ra2 + 1, r2, TDA);
        setBounds(ID + 6, 87, 66, 7, hover);
        addRuneText(ID + 7, ra3 + 1, r3, TDA);
        setBounds(ID + 7, 142, 66, 8, hover);
    }

    public static void addLunar3RunesBigBox(int ID, int r1, int r2, int r3, int ra1, int ra2, int ra3, int rune1, int rune2, int lvl, String name, String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
        RSInterface rsInterface = addTabInterface(ID);
        rsInterface.id = ID;
        rsInterface.parentID = 1151;
        rsInterface.type = 5;
        rsInterface.atActionType = type;
        rsInterface.contentType = 0;
        rsInterface.hoverType = ID + 1;
        rsInterface.spellUsableOn = suo;
        rsInterface.selectedActionName = "Cast on";
        rsInterface.width = 20;
        rsInterface.height = 20;
        rsInterface.tooltip = "Cast @gre@" + name;
        rsInterface.spellName = name;
        rsInterface.valueCompareType = new int[4];
        rsInterface.requiredValues = new int[4];
        rsInterface.valueCompareType[0] = 3;
        rsInterface.requiredValues[0] = ra1;
        rsInterface.valueCompareType[1] = 3;
        rsInterface.requiredValues[1] = ra2;
        rsInterface.valueCompareType[2] = 3;
        rsInterface.requiredValues[2] = ra3;
        rsInterface.valueCompareType[3] = 3;
        rsInterface.requiredValues[3] = lvl;
        rsInterface.valueIndexArray = new int[4][];
        rsInterface.valueIndexArray[0] = new int[4];
        rsInterface.valueIndexArray[0][0] = 4;
        rsInterface.valueIndexArray[0][1] = 3214;
        rsInterface.valueIndexArray[0][2] = r1;
        rsInterface.valueIndexArray[0][3] = 0;
        rsInterface.valueIndexArray[1] = new int[4];
        rsInterface.valueIndexArray[1][0] = 4;
        rsInterface.valueIndexArray[1][1] = 3214;
        rsInterface.valueIndexArray[1][2] = r2;
        rsInterface.valueIndexArray[1][3] = 0;
        rsInterface.valueIndexArray[2] = new int[4];
        rsInterface.valueIndexArray[2][0] = 4;
        rsInterface.valueIndexArray[2][1] = 3214;
        rsInterface.valueIndexArray[2][2] = r3;
        rsInterface.valueIndexArray[2][3] = 0;
        rsInterface.valueIndexArray[3] = new int[3];
        rsInterface.valueIndexArray[3][0] = 1;
        rsInterface.valueIndexArray[3][1] = 6;
        rsInterface.valueIndexArray[3][2] = 0;
        rsInterface.enabledSpriteId = sid + 248;
        rsInterface.disabledSpriteId = sid + 209;
        RSInterface hover = addTabInterface(ID + 1);
        hover.hoverType = -1;
        hover.hidden = true;
        setChildren(9, hover);
        addLunarSprite(ID + 2, 294);
        setBounds(ID + 2, 0, 0, 0, hover);
        addText(ID + 3, "Level " + (lvl + 1) + ": " + name, 0xFF981F, true, true, 52, 1);
        setBounds(ID + 3, 90, 4, 1, hover);
        addText(ID + 4, descr, 0xAF6A1A, true, true, 52, 0);
        setBounds(ID + 4, 90, 21, 2, hover);
        setBounds(30016, 14, 48, 3, hover);
        setBounds(rune1, 74, 48, 4, hover);
        setBounds(rune2, 130, 48, 5, hover);
        addRuneText(ID + 5, ra1 + 1, r1, TDA);
        setBounds(ID + 5, 26, 79, 6, hover);
        addRuneText(ID + 6, ra2 + 1, r2, TDA);
        setBounds(ID + 6, 87, 79, 7, hover);
        addRuneText(ID + 7, ra3 + 1, r3, TDA);
        setBounds(ID + 7, 142, 79, 8, hover);
    }

    public static void addLunar3RunesLargeBox(int ID, int r1, int r2, int r3, int ra1, int ra2, int ra3, int rune1, int rune2, int lvl, String name, String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
        RSInterface rsInterface = addTabInterface(ID);
        rsInterface.id = ID;
        rsInterface.parentID = 1151;
        rsInterface.type = 5;
        rsInterface.atActionType = type;
        rsInterface.contentType = 0;
        rsInterface.hoverType = ID + 1;
        rsInterface.spellUsableOn = suo;
        rsInterface.selectedActionName = "Cast on";
        rsInterface.width = 20;
        rsInterface.height = 20;
        rsInterface.tooltip = "Cast @gre@" + name;
        rsInterface.spellName = name;
        rsInterface.valueCompareType = new int[4];
        rsInterface.requiredValues = new int[4];
        rsInterface.valueCompareType[0] = 3;
        rsInterface.requiredValues[0] = ra1;
        rsInterface.valueCompareType[1] = 3;
        rsInterface.requiredValues[1] = ra2;
        rsInterface.valueCompareType[2] = 3;
        rsInterface.requiredValues[2] = ra3;
        rsInterface.valueCompareType[3] = 3;
        rsInterface.requiredValues[3] = lvl;
        rsInterface.valueIndexArray = new int[4][];
        rsInterface.valueIndexArray[0] = new int[4];
        rsInterface.valueIndexArray[0][0] = 4;
        rsInterface.valueIndexArray[0][1] = 3214;
        rsInterface.valueIndexArray[0][2] = r1;
        rsInterface.valueIndexArray[0][3] = 0;
        rsInterface.valueIndexArray[1] = new int[4];
        rsInterface.valueIndexArray[1][0] = 4;
        rsInterface.valueIndexArray[1][1] = 3214;
        rsInterface.valueIndexArray[1][2] = r2;
        rsInterface.valueIndexArray[1][3] = 0;
        rsInterface.valueIndexArray[2] = new int[4];
        rsInterface.valueIndexArray[2][0] = 4;
        rsInterface.valueIndexArray[2][1] = 3214;
        rsInterface.valueIndexArray[2][2] = r3;
        rsInterface.valueIndexArray[2][3] = 0;
        rsInterface.valueIndexArray[3] = new int[3];
        rsInterface.valueIndexArray[3][0] = 1;
        rsInterface.valueIndexArray[3][1] = 6;
        rsInterface.valueIndexArray[3][2] = 0;
        rsInterface.enabledSpriteId = sid + 248;
        rsInterface.disabledSpriteId = sid + 209;
        RSInterface hover = addTabInterface(ID + 1);
        hover.hidden = true;
        hover.hoverType = -1;
        setChildren(9, hover);
        addLunarSprite(ID + 2, 292);
        setBounds(ID + 2, 0, 0, 0, hover);
        addText(ID + 3, "Level " + (lvl + 1) + ": " + name, 0xFF981F, true, true, 52, 1);
        setBounds(ID + 3, 90, 4, 1, hover);
        addText(ID + 4, descr, 0xAF6A1A, true, true, 52, 0);
        setBounds(ID + 4, 90, 34, 2, hover);
        setBounds(30016, 14, 61, 3, hover);
        setBounds(rune1, 74, 61, 4, hover);
        setBounds(rune2, 130, 61, 5, hover);
        addRuneText(ID + 5, ra1 + 1, r1, TDA);
        setBounds(ID + 5, 26, 92, 6, hover);
        addRuneText(ID + 6, ra2 + 1, r2, TDA);
        setBounds(ID + 6, 87, 92, 7, hover);
        addRuneText(ID + 7, ra3 + 1, r3, TDA);
        setBounds(ID + 7, 142, 92, 8, hover);
    }

    public static void lunarSpellbookInterface() {
        drawRune(30003, 1, "Fire");
        drawRune(30004, 2, "Water");
        drawRune(30005, 3, "Air");
        drawRune(30006, 4, "Earth");
        drawRune(30007, 5, "Mind");
        drawRune(30008, 6, "Body");
        drawRune(30009, 7, "Death");
        drawRune(30010, 8, "Nature");
        drawRune(30011, 9, "Chaos");
        drawRune(30012, 10, "Law");
        drawRune(30013, 11, "Cosmic");
        drawRune(30014, 12, "Blood");
        drawRune(30015, 13, "Soul");
        drawRune(30016, 14, "Astral");
        RSInterface lunarB = addTabInterface(29999);
        addLunar3RunesSmallBox(30017, 9075, 554, 555, 0, 4, 3, 30003, 30004, 64, "Bake Pie", "Bake pies without a stove", fonts, 0, 16, 2);
        addLunar2RunesSmallBox(30025, 9075, 557, 0, 7, 30006, 65, "Cure Plant", "Cure disease on farming patch", fonts, 1, 4, 2);
        addLunar3RunesBigBox(30032, 9075, 564, 558, 0, 0, 0, 30013, 30007, 65, "Monster Examine", "Detect the combat statistics of a\\nmonster", fonts, 2, 2, 2);
        addLunar3RunesSmallBox(30040, 9075, 564, 556, 0, 0, 1, 30013, 30005, 66, "NPC Contact", "Speak with varied NPCs", fonts, 3, 0, 2);
        addLunar3RunesSmallBox(30048, 9075, 563, 557, 0, 0, 9, 30012, 30006, 67, "Cure Other", "Cure poisoned players", fonts, 4, 8, 2);
        addLunar3RunesSmallBox(30056, 9075, 555, 554, 0, 2, 0, 30004, 30003, 67, "Humidify", "fills certain vessels with water", fonts, 5, 0, 5);

        addLunar3RunesSmallBox(30091, 9075, 564, 563, 1, 1, 0, 30013, 30012, 70, "Cure Me", "Cures Poison", fonts, 9, 0, 5);
        addLunar2RunesSmallBox(30099, 9075, 557, 1, 1, 30006, 70, "Hunter Kit", "Get a kit of hunting gear", fonts, 10, 0, 5);
        addLunar3RunesSmallBox(30122, 9075, 564, 563, 1, 1, 1, 30013, 30012, 73, "Cure Group", "Cures Poison on players", fonts, 13, 0, 5);
        addLunar3RunesBigBox(30130, 9075, 564, 559, 1, 1, 4, 30013, 30008, 74, "Stat Spy", "Cast on another player to see their\\nskill levels", fonts, 14, 8, 2);

        addLunar3RunesSmallBox(30154, 9075, 554, 556, 1, 5, 9, 30003, 30005, 76, "Superglass Make", "Make glass without a furnace", fonts, 17, 16, 2);
        addLunar3RunesBigBox(30178, 9075, 564, 559, 1, 0, 4, 30013, 30008, 78, "Dream", "Take a rest and restore hitpoints 3\\n times faster", fonts, 20, 0, 5);
        addLunar3RunesSmallBox(30186, 9075, 557, 555, 1, 9, 4, 30006, 30004, 79, "String Jewellery", "String amulets without wool", fonts, 21, 0, 5);
        addLunar3RunesLargeBox(30194, 9075, 557, 555, 1, 9, 9, 30006, 30004, 80, "Stat Restore Pot\\nShare", "Share a potion with up to 4 nearby\\nplayers", fonts, 22, 0, 5);
        addLunar3RunesSmallBox(30202, 9075, 554, 555, 1, 6, 6, 30003, 30004, 81, "Magic Imbue", "Combine runes without a talisman", fonts, 23, 0, 5);
        addLunar3RunesBigBox(30210, 9075, 561, 557, 2, 1, 14, 30010, 30006, 82, "Fertile Soil", "Fertilise a farming patch with super\\ncompost", fonts, 24, 4, 2);
        addLunar3RunesBigBox(30218, 9075, 557, 555, 2, 11, 9, 30006, 30004, 83, "Boost Potion Share", "Shares a potion with up to 4 nearby\\nplayers", fonts, 25, 0, 5);


        addLunar3RunesSmallBox(30242, 9075, 557, 561, 2, 14, 0, 30006, 30010, 85, "Plank Make", "Turn Logs into planks", fonts, 28, 16, 5);
        addLunar3RunesBigBox(30282, 9075, 563, 561, 2, 1, 0, 30012, 30010, 90, "Energy Transfer", "Spend hitpoints and SA Energy to\\n give another player hitpoints and run energy", fonts, 33, 8, 2);
        addLunar3RunesBigBox(30290, 9075, 563, 565, 2, 2, 0, 30012, 30014, 91, "Heal Other", "Transfer up to 75% of hitpoints\\n to another player", fonts, 34, 8, 2);
        addLunar3RunesBigBox(30298, 9075, 560, 557, 2, 1, 9, 30009, 30006, 92, "Vengeance Other", "Allows another player to rebound\\ndamage to an opponent", fonts, 35, 8, 2);
        addLunar3RunesSmallBox(30306, 9075, 560, 557, 3, 1, 9, 30009, 30006, 93, "Vengeance", "Rebound damage to an opponent", fonts, 36, 0, 5);
        addLunar3RunesBigBox(30314, 9075, 565, 563, 3, 2, 5, 30014, 30012, 94, "Heal Group", "Transfer up to 75% of hitpoints to a group", fonts, 37, 0, 5);
        addLunar3RunesBigBox(30322, 9075, 564, 563, 2, 1, 0, 30013, 30012, 95, "Spellbook Swap", "Change to another spellbook for 1\\nspell cast", fonts, 38, 0, 5);

        final int children = 24;
        int[] LunarIDs = {30017, 30025, 30032, 30040, 30048, 30056, 30091, 30099, 30122, 30130, 30154, 30178, 30186, 30194, 30202, 30210, 30218, 30242, 30282, 30290, 30298, 30306, 30314, 30322};

        lunarB.totalChildren(children);
        int y = 0, x = 11;
        for (int index = 0; index < children; index++) {
            if (index == 6 || index == 12 || index == 18) {
                y += 30;
                x = 11;
            }
            int xToSet = x;
            int yToSet = y;
            if (LunarIDs[index] == 30099) yToSet -= 2;
            lunarB.child(index, LunarIDs[index], x, yToSet);
            x += 30;
        }
        RSInterface newInterface = addInterface(11800);
        RSInterface spellButtons = interfaceCache[29999];
        newInterface.totalChildren(15);
        /**
         * Change spellbook
         */
        spellButtons.scrollMax = 0;
        spellButtons.height = 260;
        spellButtons.width = 190;
        newInterface.child(0, 29999, 0, 55);
        /**
         * Add teleports
         */
        addHoverButtonWSpriteLoader(11001, 906, 18, 18, "Select", -1, 11002, 1);

        addTooltip(11002, "Home Teleport\nTeleport to set home location.");
        newInterface.child(1, 11001, 8, 16);
        newInterface.child(2, 11002, 10, 39);
        addHoverButtonWSpriteLoader(11004, 907, 18, 18, "Select", -1, 11005, 1);
        addTooltip(11005, "City Teleports\nOpen options of different \ncity teleports.");
        newInterface.child(3, 11004, 34, 16);
        newInterface.child(4, 11005, 30, 39);
        addHoverButtonWSpriteLoader(11008, 908, 18, 18, "Select", -1, 11009, 1);
        addTooltip(11009, "Training Teleport\nOpen options of different \ntraining teleports.");
        newInterface.child(5, 11008, 60, 16);
        newInterface.child(6, 11009, 40, 39);
        addHoverButtonWSpriteLoader(11011, 909, 18, 18, "Select", -1, 11012, 1);
        addTooltip(11012, "Dungeon Teleport\nOpen options of different\ndungeon teleports.");
        newInterface.child(7, 11011, 86, 16);
        newInterface.child(8, 11012, 23, 39);
        addHoverButtonWSpriteLoader(11014, 910, 18, 18, "Select", -1, 11015, 1);
        addTooltip(11015, "Boss Teleport\nOpen options of different\nboss teleports.");
        newInterface.child(9, 11014, 112, 16);
        newInterface.child(10, 11015, 23, 39);
        addHoverButtonWSpriteLoader(11017, 911, 18, 18, "Select", -1, 11018, 1);
        addTooltip(11018, "Minigame Teleport\nOpen options of different\nminigame teleports.");
        newInterface.child(11, 11017, 138, 16);
        newInterface.child(12, 11018, 34, 39);
        addHoverButtonWSpriteLoader(11020, 912, 18, 18, "Select", -1, 11021, 1);
        addTooltip(11021, "Raids Teleport\nOpen options of different\nRaids teleports.");
        newInterface.child(13, 11020, 164, 16);
        newInterface.child(14, 11021, 40, 39);
    }

    public static void addHoverImage(int i, int j, int k) {
        RSInterface tab = addTabInterface(i);
        tab.id = i;
        tab.parentID = i;
        tab.type = 5;
        tab.atActionType = 0;
        tab.contentType = 0;
        tab.width = 512;
        tab.height = 334;
        tab.opacity = 0;
        tab.hoverType = 52;
        tab.disabledSpriteId = j;
        tab.enabledSpriteId = k;
    }

    public static void addHoverImage(int i, int j, int k, String name) {
        RSInterface tab = addTabInterface(i);
        tab.id = i;
        tab.parentID = i;
        tab.type = 5;
        tab.atActionType = 0;
        tab.contentType = 0;
        tab.width = 512;
        tab.height = 334;
        tab.opacity = 0;
        tab.hoverType = 52;
        tab.disabledSprite = imageLoader(j, name);
        tab.enabledSprite = imageLoader(k, name);
    }

    public static void addHoverImage(int a, int i, int j, int k, String name) {
        RSInterface tab = addTabInterface(i);
        tab.id = i;
        tab.parentID = i;
        tab.type = 5;
        tab.atActionType = 0;
        tab.contentType = 0;
        tab.width = 512;
        tab.height = 334;
        tab.opacity = 0;
        tab.hoverType = 52;
        tab.disabledSpriteId = j;
        tab.enabledSpriteId = k;
        // tab.setSprite = 638;
        // tab.savedFirstSpriteId = j;
    }

    public static void addTransparentSprite(int id, int spriteId, String spriteName) {
        RSInterface tab = interfaceCache[id] = new RSInterface();
        tab.id = id;
        tab.parentID = id;
        tab.type = 5;
        tab.atActionType = 0;
        tab.contentType = 0;
        tab.opacity = (byte) 0;
        tab.hoverType = 52;
        tab.disabledSprite = imageLoader(spriteId, spriteName);
        tab.enabledSprite = imageLoader(spriteId, spriteName);
        tab.width = 512;
        tab.height = 334;
        tab.drawsTransparent = true;
    }

    public static RSInterface addScreenInterface(int id) {
        RSInterface tab = interfaceCache[id] = new RSInterface();
        tab.id = id;
        tab.parentID = id;
        tab.type = 0;
        tab.atActionType = 0;
        tab.contentType = 0;
        tab.width = 512;
        tab.height = 334;
        tab.opacity = (byte) 0;
        tab.hoverType = 0;
        return tab;
    }

    public static RSInterface addTabInterface(int id) {
        RSInterface tab = interfaceCache[id] = new RSInterface();
        tab.id = id;// 250
        tab.parentID = id;// 236
        tab.type = 0;// 262
        tab.atActionType = 0;// 217
        tab.contentType = 0;
        tab.width = 512;// 220
        tab.height = 700;// 267
        tab.opacity = (byte) 0;
        tab.hoverType = -1;// Int 230
        return tab;
    }

    public static Sprite imageLoader(int i, String s) {
        long l = (TextClass.method585(s) << 8) + (long) i;
        Sprite sprite = (Sprite) spriteCache.get(l);
        if (sprite != null) return sprite;
        try {
            sprite = new Sprite(s + " " + i);
            spriteCache.put(sprite, l);
        } catch (Exception exception) {
            return null;
        }
        return sprite;
    }

    /*
     * Custom interfaces
     */

    private static Sprite getSprite(int spriteId, CacheArchive streamLoader, String spriteName) {
        if (spriteName.equals("headicons") && spriteId == 0) return null;
        long l = (TextClass.method585(spriteName) << 8) + (long) spriteId;
        Sprite sprite = (Sprite) spriteCache.get(l);
        if (sprite != null) return sprite;
        try {
            sprite = new Sprite(streamLoader, spriteName, spriteId);
            spriteCache.put(sprite, l);
        } catch (Exception _ex) {
            System.out.println("Unable to load sprite: " + spriteName + " " + spriteId);
            return null;
        }
        return sprite;
    }

    public static void clearModelCache(boolean flag, Model model) {
        int i = 0;// was parameter
        int j = 5;// was parameter
        if (flag) return;
        modelCache.clear();
        if (model != null && j != 4) modelCache.put(model, (j << 16) + i);
    }

    public static void clearModelCache() {
        modelCache.clear();
    }

    public static void addChar(int ID) {
        RSInterface t = interfaceCache[ID] = new RSInterface();
        t.id = ID;
        t.parentID = ID;
        t.type = 6;
        t.atActionType = 0;
        t.contentType = 328;
        t.width = 180;
        t.height = 190;
        t.transparancy = 0;
        t.hoverType = 0;
        t.modelZoom = 560;
        t.modelRotation1 = 30;
        t.modelRotation2 = 0;
        t.disabledAnimationId = -1;
        t.enabledAnimationId = -1;
    }

    public static void setBounds(int ID, int X, int Y, int frame, RSInterface RSinterface) {
        RSinterface.children[frame] = ID;
        RSinterface.childX[frame] = X;
        RSinterface.childY[frame] = Y;
    }

    public static void addButton(int i, int j, String name, int W, int H, String S, int AT) {
        RSInterface RSInterface = addInterface(i);
        RSInterface.id = i;
        RSInterface.parentID = i;
        RSInterface.type = 5;
        RSInterface.atActionType = AT;
        RSInterface.contentType = 0;
        RSInterface.opacity = 0;
        RSInterface.hoverType = 52;
        if (name == null) name = "";
        RSInterface.disabledSprite = imageLoader(j, name);
        RSInterface.enabledSprite = imageLoader(j, name);
        RSInterface.width = W;
        RSInterface.height = H;
        RSInterface.tooltip = S;
    }

    public static void addButton(int i, int j, int enabled, int W, int H, String S, int AT) {
        RSInterface RSInterface = addInterface(i);
        RSInterface.id = i;
        RSInterface.parentID = i;
        RSInterface.type = 5;
        RSInterface.atActionType = AT;
        RSInterface.contentType = 0;
        RSInterface.opacity = 0;
        RSInterface.hoverType = 52;
        RSInterface.disabledSpriteId = j;
        RSInterface.enabledSpriteId = enabled;
        RSInterface.width = W;
        RSInterface.height = H;
        RSInterface.tooltip = S;
    }

    public static void addCloseButton(int child, int hoverChild, int hoverImageChild) {
        addHoverButtonWSpriteLoader(child, 740, 21, 21, "Close", 250, hoverChild, 3);
        addHoveredImageWSpriteLoader(hoverChild, 741, 21, 21, hoverImageChild);
    }

    private static void equipmentScreenInterface() {
        RSInterface tab = addTabInterface(21172);
        addSpriteLoader(21173, 857);
        addCloseButton(15210, 15215, 15216);
        addText(15111, "", fonts, 2, 0xe4a146, false, true);
        int rofl = 3;
        addText(15112, "Attack bonuses", fonts, 2, 0xFF8900, false, true);
        addText(15113, "Defence bonuses", fonts, 2, 0xFF8900, false, true);
        addText(15114, "Other bonuses", fonts, 2, 0xFF8900, false, true);
        addText(66100, "Summoning: +0", fonts, 1, 0xFF8900, false, true);// 19148
        addText(66101, "Absorb Melee: +0%", fonts, 1, 0xFF9200, false, true);// 19149
        addText(66102, "Absorb Magic: +0%", fonts, 1, 0xFF9200, false, true);// 19150
        addText(66103, "Absorb Ranged: +0%", fonts, 1, 0xFF9200, false, true);// 19151
        addText(66104, "Ranged Strength: +0", fonts, 1, 0xFF9200, false, true);// 19152
        addText(66105, "Magic Damage: +0%", fonts, 1, 0xFF9200, false, true);// 19153


        for (int i = 1675; i <= 1684; i++) {
            textSize(i, fonts, 1);
        }
        textSize(1686, fonts, 1);
        textSize(1687, fonts, 1);
        addChar(15125);
        tab.totalChildren(51);
        tab.child(0, 21173, 15, 5);
        tab.child(1, 15210, 476, 6);
        tab.child(2, 15111, 14, 30);
        tab.child(3, 15111, 14, 30);
        int Child = 4;
        int Y = 45;
        tab.child(16, 15112, 24, 30 - rofl);
        for (int i = 1675; i <= 1679; i++) {
            tab.child(Child, i, 29, Y - rofl);
            Child++;
            Y += 14;
        }
        int edit = 7 + rofl;
        tab.child(18, 15113, 24, 122 - edit);
        tab.child(9, 1680, 29, 137 - edit - 2);
        tab.child(10, 1681, 29, 153 - edit - 3);
        tab.child(11, 1682, 29, 168 - edit - 3);
        tab.child(12, 1683, 29, 183 - edit - 3);
        tab.child(13, 1684, 29, 197 - edit - 3);
        tab.child(44, 66100, 29, 211 - edit - 3);
        tab.child(45, 66101, 29, 225 - edit - 3);
        tab.child(46, 66102, 29, 239 - edit - 3);
        tab.child(47, 66103, 29, 253 - edit - 3);
        /* bottom */
        int edit2 = 33 - rofl, edit3 = 2;
        tab.child(19, 15114, 24, 223 + edit2);
        tab.child(14, 1686, 29, 262 - 24 + edit2 - edit3);
        tab.child(17, 66104, 29, 276 - 24 + edit2 - edit3);
        tab.child(48, 1687, 29, 290 - 24 + edit2 - edit3);
        tab.child(49, 66105, 29, 304 - 24 + edit2 - edit3);
        tab.child(15, 15125, 170, 200);
        tab.child(20, 1645, 104 + 295, 149 - 52);
        tab.child(21, 1646, 399, 163);
        tab.child(22, 1647, 399, 163);
        tab.child(23, 1648, 399, 58 + 146);
        tab.child(24, 1649, 26 + 22 + 297 - 2, 110 - 44 + 118 - 13 + 5);
        tab.child(25, 1650, 321 + 22, 58 + 154);
        tab.child(26, 1651, 321 + 134, 58 + 118);
        tab.child(27, 1652, 321 + 134, 58 + 154);
        tab.child(28, 1653, 321 + 48, 58 + 81);
        tab.child(29, 1654, 321 + 107, 58 + 81);
        tab.child(30, 1655, 321 + 58, 58 + 42);
        tab.child(31, 1656, 321 + 112, 58 + 41);
        tab.child(32, 1657, 321 + 78, 58 + 4);
        tab.child(33, 1658, 321 + 37, 58 + 43);
        tab.child(34, 1659, 321 + 78, 58 + 43);
        tab.child(35, 1660, 321 + 119, 58 + 43);
        tab.child(36, 1661, 321 + 22, 58 + 82);
        tab.child(37, 1662, 321 + 78, 58 + 82);
        tab.child(38, 1663, 321 + 134, 58 + 82);
        tab.child(39, 1664, 321 + 78, 58 + 122);
        tab.child(40, 1665, 321 + 78, 58 + 162);
        tab.child(41, 1666, 321 + 22, 58 + 162);
        tab.child(42, 1667, 321 + 134, 58 + 162);
        tab.child(43, 1688, 50 + 297 - 2, 110 - 13 + 5);
        for (int i = 1675; i <= 1684; i++) {
            RSInterface rsi = interfaceCache[i];
            rsi.textColor(i, 0xFF9200);
            rsi.centerText = false;
        }
        for (int i = 1686; i <= 1687; i++) {
            RSInterface rsi = interfaceCache[i];
            rsi.textColor(i, 0xFF9200);
            rsi.centerText = false;
        }
        tab.child(50, 15215, 476, 6);
    }

    public static void addSpriteLoaderButtonWithTooltipBox(int childId, int spriteId, String tooltip, int hoverSpriteId, int tooltipBoxChildId, String tooltipBoxText, int tooltipx, int tooltipy) {
        RSInterface rsi = RSInterface.interfaceCache[childId] = new RSInterface();
        rsi.id = childId;
        rsi.parentID = childId;
        rsi.type = 5;
        rsi.atActionType = 1;
        rsi.contentType = 0;
        rsi.hoverType = tooltipBoxChildId;
        rsi.disabledSprite = SpriteLoader.sprites[spriteId];
        rsi.enabledSprite = SpriteLoader.sprites[spriteId];
        rsi.width = rsi.disabledSprite.myWidth;
        rsi.height = rsi.enabledSprite.myHeight - 2;
        rsi.tooltip = tooltip;
        //rsi.isFalseTooltip = true;
        addTooltip2(tooltipBoxChildId, tooltipBoxText, tooltipx, tooltipy);
    }

    public static void addSpriteLoader(int childId, int spriteId) {
        RSInterface rsi = RSInterface.interfaceCache[childId] = new RSInterface();
        rsi.id = childId;
        rsi.parentID = childId;
        rsi.type = 5;
        rsi.atActionType = 0;
        rsi.contentType = 0;
        rsi.disabledSprite = SpriteLoader.sprites[spriteId];
        rsi.enabledSprite = SpriteLoader.sprites[spriteId];


        //rsi.sprite1.spriteLoader = rsi.sprite2.spriteLoader = true;
        //rsi.hoverSprite1 = SpriteLoader.sprites[hoverSpriteId];
        //rsi.hoverSprite2 = SpriteLoader.sprites[hoverSpriteId];
        //rsi.hoverSprite1.spriteLoader = rsi.hoverSprite2.spriteLoader = true;
        //rsi.sprite1 = rsi.sprite2 = spriteId;
        //rsi.hoverSprite1Id = rsi.hoverSprite2Id = hoverSpriteId;
        rsi.width = rsi.disabledSprite.myWidth;
        rsi.height = rsi.enabledSprite.myHeight - 2;
        //rsi.isFalseTooltip = true;
    }

    public static void addTooltip2(int id, String text, int x, int y) {
        RSInterface rsinterface = addTabInterface(id);
        rsinterface.parentID = id;
        rsinterface.type = 0;
        rsinterface.hidden = true;
        rsinterface.hoverType = -1;
        addTooltipBox2(id + 1, text);
        rsinterface.totalChildren(1);
        rsinterface.child(0, id + 1, x, y);
    }

    private static void skillTabInterface() {
        RSInterface rsi = addTabInterface(31110);
        setChildren(104, rsi);

        addSpriteLoaderButtonWithTooltipBox(31111, 245, "View @or2@Attack @lre@Options", 268, 31112, "Attack: 1/1\nCurrent XP: 14000000\nNext Level: 14000000\nRemainder: 14000000", 0, 30);
        addText(31114, "99", 0xFFEE33, false, true, 52, fonts, 0);
        addText(31115, "99", 0xFFEE33, false, true, 52, fonts, 0);
        RSInterface.interfaceCache[31112].contentType = 1004;

        addSpriteLoaderButtonWithTooltipBox(31116, 265, "View @or2@Strength @lre@Options", 268, 31117, "Strength: 1/1\nCurrent XP: 14000000\nNext Level: 14000000\nRemainder: 14000000", 0, 30);
        addText(31119, "99", 0xFFEE33, false, true, 52, fonts, 0);
        addText(31120, "99", 0xFFEE33, false, true, 52, fonts, 0);
        RSInterface.interfaceCache[31117].contentType = 1007;

        addSpriteLoaderButtonWithTooltipBox(31121, 249, "View @or2@Defence @lre@Options", 268, 31122, "Defence: 1/1\nCurrent XP: 14000000\nNext Level: 14000000\nRemainder: 14000000", 0, 30);
        addText(31124, "99", 0xFFEE33, false, true, 52, fonts, 0);
        addText(31125, "99", 0xFFEE33, false, true, 52, fonts, 0);
        RSInterface.interfaceCache[31122].contentType = 1010;

        addSpriteLoaderButtonWithTooltipBox(31126, 261, "View @or2@Ranged @lre@Options", 268, 31127, "Ranged: 1/1\nCurrent XP: 14000000\nNext Level: 14000000\nRemainder: 14000000", 0, 30);
        addText(31129, "99", 0xFFEE33, false, true, 52, fonts, 0);
        addText(31130, "99", 0xFFEE33, false, true, 52, fonts, 0);
        RSInterface.interfaceCache[31127].contentType = 1013;

        addSpriteLoaderButtonWithTooltipBox(31131, 260, "View @or2@Prayer @lre@Options", 268, 31132, "Prayer: 1/1\nCurrent XP: 14000000\nNext Level: 14000000\nRemainder: 14000000", 0, 30);
        addText(31134, "99", 0xFFEE33, false, true, 52, fonts, 0);
        addText(31135, "99", 0xFFEE33, false, true, 52, fonts, 0);
        RSInterface.interfaceCache[31132].contentType = 1016;

        addSpriteLoaderButtonWithTooltipBox(31136, 258, "View @or2@Magic @lre@Options", 268, 31137, "Magic: 1/1\nCurrent XP: 14000000\nNext Level: 14000000\nRemainder: 14000000", 0, 30);
        addText(31139, "99", 0xFFEE33, false, true, 52, fonts, 0);
        addText(31140, "99", 0xFFEE33, false, true, 52, fonts, 0);
        RSInterface.interfaceCache[31137].contentType = 1019;

        addSpriteLoaderButtonWithTooltipBox(31141, 262, "View @or2@Runecrafting @lre@Options", 268, 31142, "Runecrafting: 1/1\nCurrent XP: 14000000\nNext Level: 14000000\nRemainder: 14000000", 0, 30);
        addText(31144, "99", 0xFFEE33, false, true, 52, fonts, 0);
        addText(31145, "99", 0xFFEE33, false, true, 52, fonts, 0);
        RSInterface.interfaceCache[31142].contentType = 1022;

        addSpriteLoaderButtonWithTooltipBox(31146, 246, "View @or2@Skiller @lre@Options", 268, 31147, "Skiller: 1/1\nCurrent XP: 14000000\nNext Level: 14000000\nRemainder: 14000000", 0, 30);
        addText(31149, "99", 0xFFEE33, false, true, 52, fonts, 0);
        addText(31150, "99", 0xFFEE33, false, true, 52, fonts, 0);
        RSInterface.interfaceCache[31147].contentType = 1025;

        addSpriteLoaderButtonWithTooltipBox(31151, 250, "View @or2@Dungeoneering @lre@Options", 268, 31152, "Dungeoneering: 1/1\nCurrent XP: 14000000\nNext Level: 14000000\nRemainder: 14000000", 60, 0);
        addText(31154, "99", 0xFFEE33, false, true, 52, fonts, 0);
        addText(31155, "99", 0xFFEE33, false, true, 52, fonts, 0);
        RSInterface.interfaceCache[31152].contentType = 1028;

        addSpriteLoaderButtonWithTooltipBox(31156, 256, "View @or2@Constitution @lre@Options", 268, 31157, "Constitution: 1/1\nCurrent XP: 14000000\nNext Level: 14000000\nRemainder: 14000000", 0, 30);
        addText(31159, "99", 0xFFEE33, false, true, 52, fonts, 0);
        addText(31160, "99", 0xFFEE33, false, true, 52, fonts, 0);
        RSInterface.interfaceCache[31157].contentType = 1005;

        addSpriteLoaderButtonWithTooltipBox(31161, 244, "View @or2@Agility @lre@Options", 268, 31162, "Agility: 1/1\nCurrent XP: 14000000\nNext Level: 14000000\nRemainder: 14000000", 0, 30);
        addText(31164, "99", 0xFFEE33, false, true, 52, fonts, 0);
        addText(31165, "99", 0xFFEE33, false, true, 52, fonts, 0);
        RSInterface.interfaceCache[31162].contentType = 1008;

        addSpriteLoaderButtonWithTooltipBox(31166, 255, "View @or2@Herblore @lre@Options", 268, 31167, "Herblore: 1/1\nCurrent XP: 14000000\nNext Level: 14000000\nRemainder: 14000000", 0, 30);
        addText(31169, "99", 0xFFEE33, false, true, 52, fonts, 0);
        addText(31170, "99", 0xFFEE33, false, true, 52, fonts, 0);
        RSInterface.interfaceCache[31167].contentType = 1011;

        addSpriteLoaderButtonWithTooltipBox(31171, 267, "View @or2@Thieving @lre@Options", 268, 31172, "Thieving: 1/1\nCurrent XP: 14000000\nNext Level: 14000000\nRemainder: 14000000", 0, 30);
        addText(31174, "99", 0xFFEE33, false, true, 52, fonts, 0);
        addText(31175, "99", 0xFFEE33, false, true, 52, fonts, 0);
        RSInterface.interfaceCache[31172].contentType = 1014;

        addSpriteLoaderButtonWithTooltipBox(31176, 248, "View @or2@Crafting @lre@Options", 268, 31177, "Crafting: 1/1\nCurrent XP: 14000000\nNext Level: 14000000\nRemainder: 14000000", 0, 30);
        addText(31179, "99", 0xFFEE33, false, true, 52, fonts, 0);
        addText(31180, "99", 0xFFEE33, false, true, 52, fonts, 0);
        RSInterface.interfaceCache[31177].contentType = 1017;

        addSpriteLoaderButtonWithTooltipBox(31181, 254, "View @or2@Fletching @lre@Options", 268, 31182, "Fletching: 1/1\nCurrent XP: 14000000\nNext Level: 14000000\nRemainder: 14000000", 0, 30);
        addText(31184, "99", 0xFFEE33, false, true, 52, fonts, 0);
        addText(31185, "99", 0xFFEE33, false, true, 52, fonts, 0);
        RSInterface.interfaceCache[31182].contentType = 1020;

        addSpriteLoaderButtonWithTooltipBox(31186, 263, "View @or2@Slayer @lre@Options", 268, 31187, "Slayer: 1/1\nCurrent XP: 14000000\nNext Level: 14000000\nRemainder: 14000000", 0, 30);
        addText(31189, "99", 0xFFEE33, false, true, 52, fonts, 0);
        addText(31190, "99", 0xFFEE33, false, true, 52, fonts, 0);
        RSInterface.interfaceCache[31187].contentType = 1023;

        addSpriteLoaderButtonWithTooltipBox(31191, 257, "View @or2@Hunter @lre@Options", 268, 31192, "Hunter: 1/1\nCurrent XP: 14000000\nNext Level: 14000000\nRemainder: 14000000", 0, 30);
        addText(31194, "99", 0xFFEE33, false, true, 52, fonts, 0);
        addText(31195, "99", 0xFFEE33, false, true, 52, fonts, 0);
        RSInterface.interfaceCache[31192].contentType = 1026;

        addSpriteLoaderButtonWithTooltipBox(31196, 269, "Total Level", 270, 31197, "Total Level: 1024\nTotal XP: 99999999", 0, 0);
        addText(31199, "Total Level:", 0xFFEE33, false, true, 52, fonts, 0);
        addText(31200, "2475", 0xFFEE33, false, true, 52, fonts, 0);
        RSInterface.interfaceCache[31197].contentType = 1029;

        addSpriteLoaderButtonWithTooltipBox(31201, 259, "View @or2@Mining @lre@Options", 268, 31202, "Mining: 1/1\nCurrent XP: 14000000\nNext Level: 14000000\nRemainder: 14000000", 0, 30);
        addText(31204, "99", 0xFFEE33, false, true, 52, fonts, 0);
        addText(31205, "99", 0xFFEE33, false, true, 52, fonts, 0);
        RSInterface.interfaceCache[31202].contentType = 1006;

        addSpriteLoaderButtonWithTooltipBox(31206, 264, "View @or2@Smithing @lre@Options", 268, 31207, "Smithing: 1/1\nCurrent XP: 14000000\nNext Level: 14000000\nRemainder: 14000000", 0, 30);
        addText(31209, "99", 0xFFEE33, false, true, 52, fonts, 0);
        addText(31210, "99", 0xFFEE33, false, true, 52, fonts, 0);
        RSInterface.interfaceCache[31207].contentType = 1009;

        addSpriteLoaderButtonWithTooltipBox(31211, 253, "View @or2@Fishing @lre@Options", 268, 31212, "Fishing: 1/1\nCurrent XP: 14000000\nNext Level: 14000000\nRemainder: 14000000", 0, 30);
        addText(31214, "99", 0xFFEE33, false, true, 52, fonts, 0);
        addText(31215, "99", 0xFFEE33, false, true, 52, fonts, 0);
        RSInterface.interfaceCache[31212].contentType = 1012;

        addSpriteLoaderButtonWithTooltipBox(31216, 247, "View @or2@Cooking @lre@Options", 268, 31217, "Cooking: 1/1\nCurrent XP: 14000000\nNext Level: 14000000\nRemainder: 14000000", 0, 30);
        addText(31219, "99", 0xFFEE33, false, true, 52, fonts, 0);
        addText(31220, "99", 0xFFEE33, false, true, 52, fonts, 0);
        RSInterface.interfaceCache[31217].contentType = 1015;

        addSpriteLoaderButtonWithTooltipBox(31221, 252, "View @or2@Firemaking @lre@Options", 268, 31222, "Firemaking: 1/1\nCurrent XP: 14000000\nNext Level: 14000000\nRemainder: 14000000", 0, 30);
        addText(31224, "99", 0xFFEE33, false, true, 52, fonts, 0);
        addText(31225, "99", 0xFFEE33, false, true, 52, fonts, 0);
        RSInterface.interfaceCache[31222].contentType = 1018;

        addSpriteLoaderButtonWithTooltipBox(31226, 243, "View @or2@Woodcutting @lre@Options", 268, 31227, "Woodcutting: 1/1\nCurrent XP: 14000000\nNext Level: 14000000\nRemainder: 14000000", 0, 30);
        addText(31229, "99", 0xFFEE33, false, true, 52, fonts, 0);
        addText(31230, "99", 0xFFEE33, false, true, 52, fonts, 0);
        RSInterface.interfaceCache[31227].contentType = 1021;

        addSpriteLoaderButtonWithTooltipBox(31231, 251, "View @or2@Farming @lre@Options", 268, 31232, "Farming: 1/1\nCurrent XP: 14000000\nNext Level: 14000000\nRemainder: 14000000", 0, 30);
        addText(31234, "99", 0xFFEE33, false, true, 52, fonts, 0);
        addText(31235, "99", 0xFFEE33, false, true, 52, fonts, 0);
        RSInterface.interfaceCache[31232].contentType = 1024;

        addSpriteLoaderButtonWithTooltipBox(31236, 266, "View @or2@Summoning @lre@Options", 268, 31237, "Summoning: 1/1\nCurrent XP: 14000000\nNext Level: 14000000\nRemainder: 14000000", 0, 30);
        addText(31239, "99", 0xFFEE33, false, true, 52, fonts, 0);
        addText(31240, "99", 0xFFEE33, false, true, 52, fonts, 0);
        RSInterface.interfaceCache[31237].contentType = 1027;
        int index = 0;
        rsi.child(index++, 31111, 3, 3);
        rsi.child(index++, 31114, 31, 6);
        rsi.child(index++, 31115, 43, 18);

        rsi.child(index++, 31116, 3, 31);
        rsi.child(index++, 31119, 31, 34);
        rsi.child(index++, 31120, 43, 46);

        rsi.child(index++, 31121, 3, 59);
        rsi.child(index++, 31124, 31, 62);
        rsi.child(index++, 31125, 43, 74);

        rsi.child(index++, 31126, 3, 87);
        rsi.child(index++, 31129, 31, 90);
        rsi.child(index++, 31130, 43, 102);

        rsi.child(index++, 31131, 3, 115);
        rsi.child(index++, 31134, 31, 118);
        rsi.child(index++, 31135, 43, 130);

        rsi.child(index++, 31136, 3, 143);
        rsi.child(index++, 31139, 31, 146);
        rsi.child(index++, 31140, 43, 158);

        rsi.child(index++, 31141, 3, 171);
        rsi.child(index++, 31144, 31, 174);
        rsi.child(index++, 31145, 43, 186);

        rsi.child(index++, 31146, 3, 199);
        rsi.child(index++, 31149, 31, 202);
        rsi.child(index++, 31150, 43, 214);

        rsi.child(index++, 31151, 3, 227);
        rsi.child(index++, 31154, 31, 230);
        rsi.child(index++, 31155, 43, 242);

        rsi.child(index++, 31156, 64, 3);
        rsi.child(index++, 31159, 92, 6);
        rsi.child(index++, 31160, 104, 18);

        rsi.child(index++, 31161, 64, 31);
        rsi.child(index++, 31164, 92, 34);
        rsi.child(index++, 31165, 104, 46);

        rsi.child(index++, 31166, 64, 59);
        rsi.child(index++, 31169, 92, 62);
        rsi.child(index++, 31170, 104, 74);

        rsi.child(index++, 31171, 64, 87);
        rsi.child(index++, 31174, 92, 90);
        rsi.child(index++, 31175, 104, 102);

        rsi.child(index++, 31176, 64, 115);
        rsi.child(index++, 31179, 92, 118);
        rsi.child(index++, 31180, 104, 130);

        rsi.child(index++, 31181, 64, 143);
        rsi.child(index++, 31184, 92, 146);
        rsi.child(index++, 31185, 104, 158);

        rsi.child(index++, 31186, 64, 171);
        rsi.child(index++, 31189, 92, 174);
        rsi.child(index++, 31190, 104, 186);

        rsi.child(index++, 31191, 64, 199);
        rsi.child(index++, 31194, 92, 202);
        rsi.child(index++, 31195, 104, 214);

        rsi.child(index++, 31201, 125, 3);
        rsi.child(index++, 31204, 153, 6);
        rsi.child(index++, 31205, 165, 18);

        rsi.child(index++, 31206, 125, 31);
        rsi.child(index++, 31209, 153, 34);
        rsi.child(index++, 31210, 165, 46);

        rsi.child(index++, 31211, 125, 59);
        rsi.child(index++, 31214, 153, 62);
        rsi.child(index++, 31215, 165, 74);

        rsi.child(index++, 31216, 125, 87);
        rsi.child(index++, 31219, 153, 90);
        rsi.child(index++, 31220, 165, 102);

        rsi.child(index++, 31221, 125, 115);
        rsi.child(index++, 31224, 153, 118);
        rsi.child(index++, 31225, 165, 130);

        rsi.child(index++, 31226, 125, 143);
        rsi.child(index++, 31229, 153, 146);
        rsi.child(index++, 31230, 165, 158);

        rsi.child(index++, 31231, 125, 171);
        rsi.child(index++, 31234, 153, 174);
        rsi.child(index++, 31235, 165, 186);

        rsi.child(index++, 31236, 125, 199);
        rsi.child(index++, 31239, 153, 202);
        rsi.child(index++, 31240, 165, 214);

        rsi.child(index++, 31196, 64, 227);
        rsi.child(index++, 31199, 105, 229);
        rsi.child(index++, 31200, 116, 241);

        rsi.child(index++, 31112, 3, 3);
        rsi.child(index++, 31117, 3, 31);
        rsi.child(index++, 31122, 3, 59);
        rsi.child(index++, 31127, 3, 87);
        rsi.child(index++, 31132, 3, 115);
        rsi.child(index++, 31137, 3, 143);
        rsi.child(index++, 31142, 3, 171);
        rsi.child(index++, 31147, 3, 199);
        rsi.child(index++, 31152, 3, 227);
        rsi.child(index++, 31157, 64, 3);
        rsi.child(index++, 31162, 64, 31);
        rsi.child(index++, 31167, 64, 59);
        rsi.child(index++, 31172, 64, 87);
        rsi.child(index++, 31177, 64, 115);
        rsi.child(index++, 31182, 64, 143);
        rsi.child(index++, 31187, 64, 171);
        rsi.child(index++, 31192, 64, 199);
        rsi.child(index++, 31202, 30, 3);
        rsi.child(index++, 31207, 30, 31);
        rsi.child(index++, 31212, 30, 59);
        rsi.child(index++, 31217, 30, 87);
        rsi.child(index++, 31222, 30, 115);
        rsi.child(index++, 31227, 30, 143);
        rsi.child(index++, 31232, 30, 171);
        rsi.child(index++, 31237, 30, 199);
        rsi.child(index++, 31197, 64, 190);
        //	RSInterface.isTabInterface(rsi, true);
    }

    public static void addItemOnInterface(int childId, int interfaceId, String[] options) {
        RSInterface rsi = interfaceCache[childId] = new RSInterface();
        rsi.actions = new String[10];
        rsi.spritesX = new int[20];
        rsi.inv = new int[30];
        rsi.invStackSizes = new int[25];
        rsi.spritesY = new int[20];
        rsi.children = new int[0];
        rsi.childX = new int[0];
        rsi.childY = new int[0];
        for (int i = 0; i < rsi.actions.length; i++) {
            if (i < options.length) {
                if (options[i] != null) {
                    rsi.actions[i] = options[i];
                }
            }
        }
        rsi.centerText = true;
        rsi.filled = false;
        rsi.dragDeletes = false;
        rsi.usableItemInterface = false;
        rsi.isInventoryInterface = false;
        rsi.deleteOnDrag2 = true;
        rsi.invSpritePadX = 23;
        rsi.invSpritePadY = 22;
        rsi.height = 5;
        rsi.width = 6;
        rsi.parentID = interfaceId;
        rsi.id = childId;
        rsi.type = 2;
    }

    public static void addSmallItemOnInterface(int childId, int interfaceId, String[] options) {
        RSInterface rsi = interfaceCache[childId] = new RSInterface();
        rsi.actions = new String[10];
        rsi.spritesX = new int[20];
        rsi.inv = new int[30];
        rsi.invStackSizes = new int[25];
        rsi.spritesY = new int[20];
        rsi.children = new int[0];
        rsi.childX = new int[0];
        rsi.childY = new int[0];
        for (int i = 0; i < rsi.actions.length; i++) {
            if (i < options.length) {
                if (options[i] != null) {
                    rsi.actions[i] = options[i];
                }
            }
        }
        rsi.centerText = true;
        rsi.filled = false;
        rsi.dragDeletes = false;
        rsi.usableItemInterface = false;
        rsi.isInventoryInterface = false;
        rsi.deleteOnDrag2 = true;
        rsi.invSpritePadX = 23;
        rsi.invSpritePadY = 22;
        rsi.height = 5;
        rsi.width = 6;
        rsi.parentID = interfaceId;
        rsi.id = childId;
        rsi.type = 2;
    }

    private static void tradeInterface() {
        RSInterface Interface = addTabInterface(3323);
        setChildren(26, Interface);
        addSprite(3324, 6, "Interfaces/Trade/TRADE");
        addCloseButton(3442, 3445, 3446);
        addHoveredButton(3325, "Interfaces/Trade/TRADE", 2, 17, 17, 3326);
        addText(3417, "Trading With:", 0xFF981F, true, true, 52, fonts, 2);
        addText(3418, "Trader's Offer", 0xFF981F, false, true, 52, fonts, 1);
        addText(3419, "Your Offer", 0xFF981F, false, true, 52, fonts, 1);
        addText(3421, "Accept", 0x00C000, true, true, 52, fonts, 1);
        addText(3423, "Decline", 0xC00000, true, true, 52, fonts, 1);
        addText(3431, "Waiting For Other Player", 0xFFFFFF, true, true, 52, fonts, 1);
        addText(12504, "Wealth transfer: 2147,000,000 coins' worth to Zezimablud12", 0xB9B855, true, true, -1, fonts, 0);
        addText(12505, "1 has\\n 28 free\\n inventory slots.", 0xFF981F, true, true, -1, fonts, 0);
        addText(12506, "Wealth transfer: 2147,000,000 coins' worth to Zezimablud12", 0xB9B855, false, true, -1, fonts, 0);
        addText(12507, "Wealth transfer: 2147,000,000 coins' worth to me", 0xB9B855, false, true, -1, fonts, 0);
        addHoverButton(3420, "Interfaces/Trade/TRADE", 5, 65, 32, "Accept", -1, 3327, 1);
        addHoveredButton(3327, "Interfaces/Trade/TRADE", 2, 65, 32, 3328);
				/*
	    addHover(3420, 1, 0, 3327, 5, "Trade/TRADE", 65, 32,
	    "Accept");
	    addHovered(3327, 2, "Trade/TRADE", 65, 32, 3328);
				 */
        addHoverButton(3422, "Interfaces/Trade/TRADE", 5, 65, 32, "Decline", -1, 3329, 1);
        addHoveredButton(3329, "Interfaces/Trade/TRADE", 2, 65, 32, 3330);
        addText(3452, "Loan:", 0xFF981F, false, true, 52, fonts, 1);
        addText(3453, "Loan:", 0xFF981F, false, true, 52, fonts, 1);
        String[] options = {"Remove"};
        addItemOnInterface(3454, 3323, options);
        addItemOnInterface(3455, 3324, options);
        addText(3456, "Hours:", 0xFF981F, false, true, 52, fonts, 1);
        addText(3457, "Hours:", 0xFF981F, false, true, 52, fonts, 1);
				/*addHover(3422, 3, 0, 3329, 5, "Trade/TRADE", 65, 32,
	    "Decline");*/
        //addHovered(3329, 2, "Trade/TRADE", 65, 32, 3330);
        setBounds(3324, 0, 16, 0, Interface);
        setBounds(3442, 485, 24, 1, Interface);
        setBounds(3325, 485, 24, 2, Interface);
        setBounds(3417, 258, 25, 3, Interface);
        setBounds(3418, 355, 51, 4, Interface);
        setBounds(3419, 68, 51, 5, Interface);
        setBounds(3420, 223, 120, 6, Interface);
        setBounds(3327, 223, 120, 7, Interface);
        setBounds(3422, 223, 160, 8, Interface);
        setBounds(3329, 223, 160, 9, Interface);
        setBounds(3421, 256, 127, 10, Interface);
        setBounds(3423, 256, 167, 11, Interface);
        setBounds(3431, 256, 272, 12, Interface);
        setBounds(3415, 12, 64, 13, Interface);
        setBounds(3416, 321, 67, 14, Interface);
        setBounds(12505, 256, 67, 16, Interface);
        setBounds(12504, 255, 310, 15, Interface);
        setBounds(12506, 20, 310, 17, Interface);
        setBounds(12507, 380, 310, 18, Interface);
        setBounds(3452, 195, 201, 19, Interface);
        setBounds(3453, 259, 201, 20, Interface);
        setBounds(3454, 197, 218, 21, Interface);
        setBounds(3455, 263, 218, 22, Interface);
        setBounds(3456, 197, 254, 23, Interface);
        setBounds(3457, 259, 254, 24, Interface);
        setBounds(3445, 485, 24, 25, Interface);
        Interface = addTabInterface(3443);
        setChildren(15, Interface);
        addSprite(3444, 3, "Interfaces/Trade/TRADE");
        //THE TRADE BUTTON IMAGES ARE NOT USED! I didn't like them. The interface looks better without them.
        addButton(3546, 1, "Interfaces/Trade/TRADE", "Accept", 65, 32);
        addButton(3548, 1, "Interfaces/Trade/TRADE", "Decline", 63, 24);
        addText(3547, "Accept", 0x00C000, true, true, 52, fonts, 1);
        addText(3549, "Decline", 0xC00000, true, true, 52, fonts, 1);
        addText(3450, "Trading With:", 0x00FFFF, true, true, 52, fonts, 2);
        addText(3451, "Partner", 0x00FFFF, true, true, 52, fonts, 2);
        setBounds(3444, 12, 20, 0, Interface);
        setBounds(3442, 470, 32, 1, Interface);
        setBounds(3325, 470, 32, 2, Interface);
        setBounds(3535, 130, 28, 3, Interface);
        setBounds(3536, 105, 47, 4, Interface);
        setBounds(3546, 189, 295, 5, Interface);
        setBounds(3548, 258, 295, 6, Interface);
        setBounds(3547, 220, 299, 7, Interface);
        setBounds(3549, 288, 299, 8, Interface);
        setBounds(3557, 71, 87, 9, Interface);
        setBounds(3558, 315, 87, 10, Interface);
        setBounds(3533, 64, 70, 11, Interface);
        setBounds(3534, 297, 70, 12, Interface);
        setBounds(3450, 95, 289, 13, Interface);
        setBounds(3451, 95, 304, 14, Interface);
    }

    /*
     * Items kept on death interface
     */
    private static void itemsKeptOnDeathInterface() {
        RSInterface rsinterface = addTabInterface(17100);
        RSInterface scroll = addTabInterface(17149);
        scroll.width = 300;
        scroll.height = 183;
        scroll.scrollMax = 220;
        addSpriteLoader(17101, 856);
        addCloseButton(16999, 17000, 17001);
        addText(17103, "Items Kept on Death", fonts, 2, 0xff981f, false, false);
        addText(17104, "Items you will keep on death:", fonts, 1, 0xff981f, false, false);
        addText(17105, "Items you will lose on death:", fonts, 1, 0xff981f, false, false);
        addText(17106, "Info", fonts, 1, 0xff981f, false, false);
        addText(17107, "", fonts, 1, 16711680, false, false);
        String[] options = {null};
        /*
         * Items on interface
         */

        //Top Row
        for (int top = 17108; top <= 17111; top++) {
            addItemOnInterface(top, top, options);
        }
        //1st row
        for (int top = 17112; top <= 17119; top++) {
            addItemOnInterface(top, top, options);
        }
        //2nd row
        for (int top = 17120; top <= 17127; top++) {
            addItemOnInterface(top, top, options);
        }
        //3rd row
        for (int top = 17128; top <= 17135; top++) {
            addItemOnInterface(top, top, options);
        }
        //4th row
        for (int top = 17135; top <= 17142; top++) {
            addItemOnInterface(top, top, options);
        }
        //5th row
        for (int top = 17150; top <= 17156; top++) {
            addItemOnInterface(top, top, options);
        }

        //6th row (4 items)
        for (int top = 17157; top <= 17160; top++) {
            addItemOnInterface(top, top, options);
        }

        for (int info = 17143; info <= 17148; info++) {
            addText(info, "" + info, fonts, 1, 16776960, false, false);
        }

        setChildren(19, rsinterface);
        setBounds(17101, 7, 8, 0, rsinterface);
        setBounds(16999, 478, 14, 1, rsinterface);
        setBounds(17103, 185, 18, 2, rsinterface);
        setBounds(17104, 22, 50, 3, rsinterface);
        setBounds(17105, 22, 110, 4, rsinterface);
        setBounds(17106, 347, 50, 5, rsinterface);
        setBounds(17107, 22, 84, 6, rsinterface);
        setBounds(17149, 23, 132, 7, rsinterface);
        int count = 0;
        for (int info = 17143; info <= 17148; info++) {
            setBounds(info, 350, 74 + (count * 22), 8 + count, rsinterface);
            count++;
        }


        //Positions for  item on interface (items kept on death)
        count = 0;
        for (int topPos = 26; topPos <= 158; topPos += 44) {
            setBounds(17108 + count, topPos, 72, 14 + count, rsinterface);
            count++;
        }
        setChildren(39, scroll);
        count = 0;
        //Positions for item on interface (1st row)
        for (int topPos = 0; topPos <= 264; topPos += 44) {
            setBounds(17112 + count, topPos, 0, 0 + count, scroll);
            count++;
        }
        count = 0;
        //Positions for item on interface (2nd row)
        for (int topPos = 0; topPos <= 264; topPos += 44) {
            setBounds(17120 + count, topPos, 35, 7 + count, scroll);
            count++;
        }
        count = 0;
        //Positions for item on interface (3rd row)
        for (int topPos = 0; topPos <= 264; topPos += 44) {
            setBounds(17128 + count, topPos, 70, 14 + count, scroll);
            count++;
        }
        count = 0;
        //Positions for item on interface (4th row)
        for (int topPos = 0; topPos <= 264; topPos += 44) {
            setBounds(17135 + count, topPos, 105, 21 + count, scroll);
            count++;
        }
        count = 0;
        // Positions for item on interface (5th row)
        for (int topPos = 0; topPos <= 264; topPos += 44) {
            setBounds(17150 + count, topPos, 140, 28 + count, scroll);
            count++;
        }
        count = 0;
        // Positions for item on interface (6th row)
        for (int topPos = 0; topPos <= 132; topPos += 44) {
            setBounds(17157 + count, topPos, 175, 35 + count, scroll);
            count++;
        }
        setBounds(17000, 478, 14, 18, rsinterface);
    }

    /*
     * Summoning BoB interface
     */
    private static void summoningBoBInterface() {
        RSInterface rsi = addTabInterface(2700);
        addSpriteLoader(2701, 872);
        addButtonWSpriteLoader(2735, 873, "Withdraw all items");
        addCloseButton(2734, 2736, 2737);
        rsi.totalChildren(36);
        /**
         * Bob storage, starting with first row.
         */
        for (int i = 2702; i < 2710; i++)
            addBobStorage(i);
        /**
         * Second row
         */
        for (int i = 2710; i < 2716; i++)
            addBobStorage(i);
        /**
         * Third row
         */
        for (int i = 2716; i < 2722; i++)
            addBobStorage(i);

        /**
         * Fourth row
         */
        for (int i = 2722; i < 2728; i++)
            addBobStorage(i);

        /**
         * Fifth row
         */
        for (int i = 2728; i < 2734; i++)
            addBobStorage(i);

        rsi.child(0, 2701, 90, 14);
        rsi.child(1, 2735, 424, 290);
        rsi.child(2, 2703, 431, 23);
        rsi.child(3, 2704, 427, 285);
        /**
         * Bob storage first row
         */
        rsi.child(4, 2702, 105, 56);
        rsi.child(5, 2705, 160, 56);
        rsi.child(6, 2706, 215, 56);
        rsi.child(7, 2707, 270, 56);
        rsi.child(8, 2708, 320, 56);
        rsi.child(9, 2709, 375, 56);

        /**
         * Bob storage second row
         */
        rsi.child(10, 2710, 105, 110);
        rsi.child(11, 2711, 160, 110);
        rsi.child(12, 2712, 215, 110);
        rsi.child(13, 2713, 270, 110);
        rsi.child(14, 2714, 320, 110);
        rsi.child(15, 2715, 375, 110);

        /**
         * Bob storage third row
         */
        rsi.child(16, 2716, 105, 163);
        rsi.child(17, 2717, 160, 163);
        rsi.child(18, 2718, 215, 163);
        rsi.child(19, 2719, 270, 163);
        rsi.child(20, 2720, 320, 163);
        rsi.child(21, 2721, 375, 163);

        /**
         * Bob storage fourth row
         */
        rsi.child(22, 2722, 105, 216);
        rsi.child(23, 2723, 160, 216);
        rsi.child(24, 2724, 215, 216);
        rsi.child(25, 2725, 270, 216);
        rsi.child(26, 2726, 320, 216);
        rsi.child(27, 2727, 375, 216);

        /**
         * Bob storage fifth row
         */
        rsi.child(28, 2728, 105, 269);
        rsi.child(29, 2729, 160, 269);
        rsi.child(30, 2730, 215, 269);
        rsi.child(31, 2731, 270, 269);
        rsi.child(32, 2732, 320, 269);
        rsi.child(33, 2733, 375, 269);

        //Close
        rsi.child(34, 2734, 429, 22);
        rsi.child(35, 2736, 429, 22);
    }

    public static void addBobStorage(int index) {
        RSInterface rsi = interfaceCache[index] = new RSInterface();
        rsi.actions = new String[5];
        rsi.spritesX = new int[20];
        rsi.invStackSizes = new int[30];
        rsi.inv = new int[30];
        rsi.spritesY = new int[20];

        rsi.children = new int[0];
        rsi.childX = new int[0];
        rsi.childY = new int[0];

        rsi.actions[0] = "Take 1";
        rsi.actions[1] = "Take 5";
        rsi.actions[2] = "Take 10";
        rsi.actions[3] = "Take All";
        rsi.actions[4] = "Take X";
        rsi.usableItemInterface = false;
        rsi.isInventoryInterface = false;
        // rsi.aBoolean251 = false;
        rsi.filled = false;
        rsi.dragDeletes = false;
        rsi.usableItemInterface = false;
        rsi.isInventoryInterface = false;
        rsi.deleteOnDrag2 = true;
        // rsi.interfaceShown = false;
        rsi.type = -1;
        rsi.invSpritePadX = 24;
        rsi.invSpritePadY = 24;
        rsi.height = 5;
        rsi.width = 6;
        rsi.parentID = 2702;
        rsi.id = index;
        rsi.type = 2;
    }

    public static void addBankHover(int interfaceID, int actionType, int hoverid, int spriteId, int spriteId2, String NAME, int Width, int Height, int configFrame, int configId, String Tooltip, int hoverId2, int hoverSpriteId, int hoverSpriteId2, String hoverSpriteName, int hoverId3, String hoverDisabledText, String hoverEnabledText, int X, int Y, int sprite1, int sprite2) {
        RSInterface hover = addTabInterface(interfaceID);
        hover.id = interfaceID;
        hover.parentID = interfaceID;
        hover.type = 5;
        hover.atActionType = actionType;
        hover.contentType = 0;
        hover.hoverType = hoverid;
        hover.disabledSprite = SpriteLoader.sprites[sprite1];
        hover.enabledSprite = SpriteLoader.sprites[sprite2];
        hover.width = Width;
        hover.tooltip = Tooltip;
        hover.height = Height;
        hover.valueCompareType = new int[1];
        hover.requiredValues = new int[1];
        hover.valueCompareType[0] = 1;
        hover.requiredValues[0] = configId;
        hover.valueIndexArray = new int[1][3];
        hover.valueIndexArray[0][0] = 5;
        hover.valueIndexArray[0][1] = configFrame;
        hover.valueIndexArray[0][2] = 0;
        hover = addTabInterface(hoverid);
        hover.parentID = hoverid;
        hover.id = hoverid;
        hover.type = 0;
        hover.atActionType = 0;
        hover.width = 550;
        hover.height = 334;
        hover.hidden = true;
        hover.hoverType = -1;
        addSprite(hoverId2, hoverSpriteId, hoverSpriteId2, hoverSpriteName, configId, configFrame, sprite1, sprite2);
        addHoverBox(hoverId3, interfaceID, hoverDisabledText, hoverEnabledText, configId, configFrame);
        setChildren(2, hover);
        setBounds(hoverId2, 15, 60, 0, hover);
        setBounds(hoverId3, X, Y, 1, hover);
    }

    public static void addSprite(int ID, int i, int i2, String name, int configId, int configFrame, int sprite1, int sprite2) {
        RSInterface Tab = addTabInterface(ID);
        Tab.id = ID;
        Tab.parentID = ID;
        Tab.type = 5;
        Tab.atActionType = 0;
        Tab.contentType = 0;
        Tab.width = 512;
        Tab.height = 334;
        Tab.hoverType = -1;
        Tab.valueCompareType = new int[1];
        Tab.requiredValues = new int[1];
        Tab.valueCompareType[0] = 1;
        Tab.requiredValues[0] = configId;
        Tab.valueIndexArray = new int[1][3];
        Tab.valueIndexArray[0][0] = 5;
        Tab.valueIndexArray[0][1] = configFrame;
        Tab.valueIndexArray[0][2] = 0;
        if (name == null) {
					/*Tab.itemSpriteZoom1 = -1;
				Tab.itemSpriteId1 = i;
				Tab.itemSpriteZoom2 = 70;
				Tab.itemSpriteId2 = i2;*/
        } else {
            //	Tab.disabledSprite = imageLoader(i, name);
            //Tab.enabledSprite = imageLoader(i2, name);
            Tab.disabledSprite = SpriteLoader.sprites[sprite1];
            Tab.enabledSprite = SpriteLoader.sprites[sprite2];
        }
    }

    public static void addBankItem(int index, Boolean hasOption) {
        RSInterface rsi = interfaceCache[index] = new RSInterface();
        rsi.actions = new String[5];
        rsi.spritesX = new int[20];
        rsi.invStackSizes = new int[30];
        rsi.inv = new int[30];
        rsi.spritesY = new int[20];

        rsi.children = new int[0];
        rsi.childX = new int[0];
        rsi.childY = new int[0];

        //rsi.hasExamine = false;

        rsi.invSpritePadX = 24;
        rsi.invSpritePadY = 24;
        rsi.height = 5;
        rsi.width = 6;
        rsi.parentID = 5292;
        rsi.id = index;
        rsi.type = 2;
    }


    public static void bingoInterface(TextDrawingArea[] tda) {
        int interfaceId = 33622;
        RSInterface bingoRoot = addInterface(interfaceId++);
        bingoRoot.childDynamic(interfaceId, 1, 1);
        addSprite(interfaceId++, 0, "Interfaces/Bingo/BACKGROUND");

        // exit
        bingoRoot.childDynamic(interfaceId, 485, 6);
        addHoverButton(interfaceId++, "Interfaces/Bingo/EXIT", 0, 17, 17, "Exit", -1, interfaceId, 1);
        bingoRoot.childDynamic(interfaceId, 485, 6);
        addHoveredButton(interfaceId++, "Interfaces/Bingo/EXIT", 1, 17, 17, interfaceId++);

        // title
        bingoRoot.childDynamic(interfaceId, 256, 7);
        addText(interfaceId++, "BINGO", 0xff981f, true, true, 52, tda, 2);


        int firstButtonColumnX = 400;
        int secondButtonColumnX = 450;
        final String[] description = {"Any line", "The \"P\"", "Blackout"};
        for (int i = 0; i < 3; i++) {
            int buttonRowY = (i + 1) * 100;

            // reward item
            bingoRoot.childDynamic(interfaceId, (firstButtonColumnX + secondButtonColumnX + 20) / 2, buttonRowY - 60);
            addItemOnInterface(interfaceId, interfaceId++, new String[]{});

            // tier description
            bingoRoot.childDynamic(interfaceId, (firstButtonColumnX + secondButtonColumnX + 47) / 2, buttonRowY - 20);
            addText(interfaceId++, description[i], 0xff981f, true, true, 52, tda, 1);

            // info/claim buttons
            bingoRoot.childDynamic(interfaceId, firstButtonColumnX, buttonRowY);
            addHoverButton(interfaceId++, "Interfaces/Bingo/BUTTON", 0, 47, 15, "Claim", -1, interfaceId, 1);
            bingoRoot.childDynamic(interfaceId, firstButtonColumnX, buttonRowY);
            addHoveredButton(interfaceId++, "Interfaces/Bingo/BUTTON", 1, 47, 15, interfaceId++);

            bingoRoot.childDynamic(interfaceId, firstButtonColumnX + 23, buttonRowY);
            addText(interfaceId++, "Claim", 0xff981f, true, true, 52, tda, 1);

            bingoRoot.childDynamic(interfaceId, secondButtonColumnX, buttonRowY);
            addHoverButton(interfaceId++, "Interfaces/Bingo/BUTTON", 0, 47, 15, "Info", -1, interfaceId, 1);
            bingoRoot.childDynamic(interfaceId, secondButtonColumnX, buttonRowY);
            addHoveredButton(interfaceId++, "Interfaces/Bingo/BUTTON", 1, 47, 15, interfaceId++);

            bingoRoot.childDynamic(interfaceId, secondButtonColumnX + 23, buttonRowY);
            addText(interfaceId++, "Info", 0xff981f, true, true, 52, tda, 1);
        }

        // box item elements
        int itemBaseX = 26;
        int itemBaseY = 47;
        int itemOffX = 76;
        int itemOffY = 60;
        for (int i = 0; i < 25; i++) {
            int itemX = itemBaseX + itemOffX * (i % 5);
            int itemY = itemBaseY + itemOffY * (i / 5);
            bingoRoot.childDynamic(interfaceId, itemX, itemY);
            addItemOnInterface(interfaceId, interfaceId++, new String[]{});
        }

        // box text elements
        int textBaseX = 41;
        int textBaseY = 38;
        int textOffX = 76;
        int textOffY = 60;
        for (int i = 0; i < 25; i++) {
            int textX = textBaseX + textOffX * (i % 5);
            int textY = textBaseY + textOffY * (i / 5);
            bingoRoot.childDynamic(interfaceId, textX, textY);
            addText(interfaceId++, "", tda, 1, 0xff981f, true, true);
            bingoRoot.childDynamic(interfaceId, textX, textY + 15);
            addText(interfaceId++, "", tda, 1, 0xff981f, true, true);
            bingoRoot.childDynamic(interfaceId, textX, textY + 30);
            addText(interfaceId++, "", tda, 1, 0xff981f, true, true);
        }

        // add completed item semi-transparent layer
        int overlayBaseX = 4;
        int overlayBaseY = 31;
        int overlayOffX = 76;
        int overlayOffY = 60;
        for (int i = 0; i < 25; i++) {
            int overlayX = overlayBaseX + overlayOffX * (i % 5);
            int overlayY = overlayBaseY + overlayOffY * (i / 5);
            bingoRoot.childDynamic(interfaceId, overlayX, overlayY);
            addTransparentSprite(interfaceId++, 0, "Interfaces/Bingo/COMPLETED", 75, 59, 100);
        }



        bingoRoot.trimChildren();
    }

    private static void mailboxInterface(TextDrawingArea[] tda) {
        RSInterface rsi = addTabInterface(22047);
        addSprite(22048, 0, "Shop/SHOP");
        addHover(22049, 3, 0, 3826, 1, "Shop/EXIT", 17, 17, "Close Window");
        addHovered(22050, 2, "Shop/EXIT", 17, 17, 3827);
        addText(22051, " ", 0xff981f, false, true, 52, tda, 1);
        addText(22052, "Withdraw All Items", 0xbf751d, false, true, 52, tda, 1);
        addButton(22053, 2, "Shop/SHOP", 100, 19, "Withdraw All Items", 1);
        addToItemGroup(22054, 10, 4, 8, 10, true, new String[]{"Withdraw 1", "Withdraw 5", "Withdraw 10", "Withdraw All", null});

        setChildren(8, rsi);
        setBounds(22048, 12, 12, 0, rsi);
        setBounds(22049, 471, 22, 1, rsi);
        setBounds(22050, 471, 22, 2, rsi);
        setBounds(22051, 42, 51, 3, rsi);
        setBounds(22052, 350, 51, 4, rsi);
        setBounds(22053, 350, 51, 5, rsi);
        setBounds(22054, 60, 75, 6, rsi);
        setBounds(3901, 240, 21, 7, rsi); // Interface Title
    }

    private static void uimBankInterface(TextDrawingArea[] tda) {
        RSInterface rsi = addTabInterface(22147);
        addSprite(22148, 0, "Interfaces/UimStorage/BACKGROUND");
        addHover(22149, 3, 0, 3826, 1, "Shop/EXIT", 17, 17, "Close Window");
        addHovered(22150, 2, "Shop/EXIT", 17, 17, 3827);
        addText(22151, " ", 0xff981f, false, true, 52, tda, 1);
        addToItemGroup(22152, 10, 3, 8, 10, true, new String[]{"Withdraw-1", "Withdraw-5", "Withdraw-10", "Withdraw-All", "Withdraw-X"});
        interfaceCache[22152].deleteOnDrag2 = true; // need this to enable dragging on this interface
        addBankHover(22153, 4, 22154, 9, 11, "Interfaces/BANK/BANK", 35, 25, 115, 1, "Withdraw as Note", 22155, 10, 12, "Interfaces/BANK/BANK", 22156, "Switch to note withdrawal \nmode", "Switch to item withdrawal \nmode", 12, 20, 933, 934);

        addHoverButtonWSpriteLoader(22155, 923, 35, 25, "Deposit carried items", -1, 22156, 1);
        addHoveredImageWSpriteLoader(22156, 924, 35, 25, 22157);

        addHoverButtonWSpriteLoader(22158, 921, 35, 25, "Deposit worn items", -1, 22159, 1);
        addHoveredImageWSpriteLoader(22159, 922, 35, 25, 22160);

        addHoverButtonWSpriteLoader(25161, 919, 35, 25, "Deposit Beast of Burden's inventory", -1, 25162, 1);
        addHoveredImageWSpriteLoader(25162, 920, 35, 25, 25163);

        addBankHover(25164, 4, 25165, 172, 175, 35, 25, 304, 1, "Swap Insert Mode", 25166, 174, 173, 25167, "Switch to insert items \nmode", "Switch to swap items \nmode.", 12, 20);
        //nextid 25168

        setChildren(14, rsi);
        setBounds(22148, 12, 12, 0, rsi);
        setBounds(22149, 471, 22, 1, rsi);
        setBounds(22150, 471, 22, 2, rsi);
        setBounds(22151, 42, 51, 3, rsi);
        setBounds(22152, 60, 75, 4, rsi);
        setBounds(3901, 240, 21, 5, rsi); // Interface Title
        setBounds(25164, 270, 225, 6, rsi);
        setBounds(22153, 310, 225, 7, rsi);
        setBounds(22155, 350, 225, 8, rsi);
        setBounds(22156, 350, 225, 9, rsi);
        setBounds(22158, 390, 225, 10, rsi);
        setBounds(22159, 390, 225, 11, rsi);
        setBounds(25161, 430, 225, 12, rsi);
        setBounds(25162, 430, 225, 13, rsi);
    }

    /*
     * Bank interface
     */
    private static void bankInterface() {
        RSInterface rsinterface = addTabInterface(5292);
        setChildren(40, rsinterface);
        setBounds(5383, 170, 15, 1, rsinterface);
        interfaceCache[5385].height = 206;
        interfaceCache[5385].width = 474;
        interfaceCache[5382].width = 10;
        interfaceCache[5382].invSpritePadX = 12;
        interfaceCache[5382].height = 44;
        interfaceCache[5382].invStackSizes = new int[440];
        interfaceCache[5382].inv = new int[440];
        setBounds(5385, 0, 74, 2, rsinterface);
        addSpriteLoader(5293, 913);
        setBounds(5293, 13, 13, 0, rsinterface);

        addHoverButtonWSpriteLoader(5384, 892, 17, 17, "Close Window", 0, 5380, 1);
        addHoveredImageWSpriteLoader(5380, 914, 17, 17, 5379);

        setBounds(5384, 476, 16, 3, rsinterface);
        setBounds(5380, 476, 16, 4, rsinterface);
        //addButton(5294, 3, "Interfaces/BANK/BANK", "Click here to handle Bank PIN");
        addHoverButtonWSpriteLoader(5294, 915, 114, 25, "Manage Bank PIN", -1, 22045, 1);
        addHoveredImageWSpriteLoader(22045, 916, 114, 25, 22046);

        setBounds(5294, 110, 285, 5, rsinterface);
        setBounds(22045, 110, 285, 37, rsinterface);
        //(27651, "CUSTOM", "Equipment", 1, 40, 40, "Show Equipment Screen", 0, 27652, 1);
        //addHoverButton(22000, "Interfaces/BANK/BANK", 5, 35, 25, "Deposit Money-Pouch", 0, 22001, 4);
        //addHoveredButton(22001, "Interfaces/BANK/BANK", 8 ,35, 25, 22002);
        addHoverButtonWSpriteLoader(27009, 917, 35, 25, "Deposit Money-Pouch", -1, 27010, 1);
        addHoveredImageWSpriteLoader(27010, 918, 35, 25, 27011);

        //addBankHover(22000, 4, 22001, 5, 8, "Interfaces/BANK/BANK", 35, 25, 304, 1, "Deposit Money-Pouch", 22002, 7, 6, "Interfaces/BANK/BANK", 22003, "Switch to insert items \nmode", "Switch to swap items \nmode.", 12, 20);
        setBounds(27009, 25, 285, 6, rsinterface);
        setBounds(27010, 25, 285, 7, rsinterface);
        addBankHover(22004, 4, 22005, 13, 15, "Interfaces/BANK/BANK", 35, 25, 117, 1, "Search", 22006, 14, 16, "Interfaces/BANK/BANK", 22007, "Click here to search your \nbank", "Click here to search your \nbank", 12, 20, 931, 932);

        setBounds(22004, 65, 285, 8, rsinterface);
        setBounds(22005, 50, 225, 9, rsinterface);
        addBankHover(22008, 4, 22009, 9, 11, "Interfaces/BANK/BANK", 35, 25, 115, 1, "Withdraw as Note", 22010, 10, 12, "Interfaces/BANK/BANK", 22011, "Switch to note withdrawal \nmode", "Switch to item withdrawal \nmode", 12, 20, 933, 934);
        setBounds(22008, 285, 285, 10, rsinterface);
        setBounds(22009, 225, 225, 11, rsinterface);
        //addBankHover1(22012, 5, 22013, 17, "Interfaces/BANK/BANK", 35, 25, "Deposit carried tems", 22014, 18, "Interfaces/BANK/BANK", 22015, "Empty your backpack into\nyour bank", 0, 20);

        addHoverButtonWSpriteLoader(22012, 923, 35, 25, "Deposit carried items", -1, 22013, 1);
        addHoveredImageWSpriteLoader(22013, 924, 35, 25, 22014);

        setBounds(22012, 375, 285, 12, rsinterface);
        setBounds(22013, 375, 285, 13, rsinterface);
        //addBankHover1(22016, 5, 22017, 19, "Interfaces/BANK/BANK", 35, 25, "Deposit worn items", 22018, 20, "Interfaces/BANK/BANK", 22019, "Empty the items your are\nwearing into your bank", 0, 20);

        addHoverButtonWSpriteLoader(27005, 921, 35, 25, "Deposit worn items", -1, 27006, 1);
        addHoveredImageWSpriteLoader(27006, 922, 35, 25, 27007);

        setBounds(27005, 415, 285, 14, rsinterface);
        setBounds(27006, 415, 285, 15, rsinterface);
        //addBankHover1(22020, 5, 22021, 21, "Interfaces/BANK/BANK", 35, 25, "Deposit Beast of Burden's inventory.", 22022, 22, "Interfaces/BANK/BANK", 22023, "Empty your BoB's inventory\ninto your bank", 0, 20);

        addHoverButtonWSpriteLoader(27023, 919, 35, 25, "Deposit Beast of Burden's inventory", -1, 27024, 1);
        addHoveredImageWSpriteLoader(27024, 920, 35, 25, 27025);

        setBounds(27023, 455, 285, 16, rsinterface);
        setBounds(27024, 455, 285, 17, rsinterface);

        addButtonWSpriteLoader(27014, 925, "Click here to view the full contents of your bank");
        setBounds(27014, 22, 36, 18, rsinterface);
        addButtonWSpriteLoader(27015, 926, "Drag an item here to create a new tab");
        setBounds(27015, 70, 36, 19, rsinterface);
        addButtonWSpriteLoader(27016, 926, "Drag an item here to create a new tab");
        setBounds(27016, 118, 36, 20, rsinterface);
        addButtonWSpriteLoader(27017, 926, "Drag an item here to create a new tab");
        setBounds(27017, 166, 36, 21, rsinterface);
        addButtonWSpriteLoader(27018, 926, "Drag an item here to create a new tab");
        setBounds(27018, 214, 36, 22, rsinterface);
        addButtonWSpriteLoader(27019, 926, "Drag an item here to create a new tab");
        setBounds(27019, 262, 36, 23, rsinterface);
        addButtonWSpriteLoader(27020, 926, "Drag an item here to create a new tab");
        setBounds(27020, 310, 36, 24, rsinterface);
        addButtonWSpriteLoader(27021, 926, "Drag an item here to create a new tab");
        setBounds(27021, 358, 36, 25, rsinterface);
        addButtonWSpriteLoader(27022, 926, "Drag an item here to create a new tab");

        setBounds(27022, 406, 36, 26, rsinterface);
        addText(22033, "134", fonts, 0, 0xb4965a, true, false);
        setBounds(22033, 473, 42, 27, rsinterface);
        addText(22034, "496", fonts, 0, 0xb4965a, true, false);
        setBounds(22034, 473, 57, 28, rsinterface);
        addBankItem(22035, Boolean.valueOf(false));
        setBounds(22035, 77, 39, 29, rsinterface);
        addBankItem(22036, Boolean.valueOf(false));
        setBounds(22036, 125, 39, 30, rsinterface);
        addBankItem(22037, Boolean.valueOf(false));
        setBounds(22037, 173, 39, 31, rsinterface);
        addBankItem(22038, Boolean.valueOf(false));
        setBounds(22038, 221, 39, 32, rsinterface);
        addBankItem(22039, Boolean.valueOf(false));
        setBounds(22039, 269, 39, 33, rsinterface);
        addBankItem(22040, Boolean.valueOf(false));
        setBounds(22040, 317, 39, 34, rsinterface);
        addBankItem(22041, Boolean.valueOf(false));
        setBounds(22041, 365, 39, 35, rsinterface);
        addBankItem(22042, Boolean.valueOf(false));
        setBounds(22042, 413, 39, 36, rsinterface);
        addBankHover(21000, 4, 21001, 172, 175, 35, 25, 304, 1, "Swap Insert Mode", 21002, 174, 173, 21003, "Switch to insert items \nmode", "Switch to swap items \nmode.", 12, 20);
        setBounds(21000, 240, 285, 37, rsinterface);
        addText(27000, "0", 0xff981f, false, true, 52, fonts, 1);
        addText(27001, "0", 0xff981f, false, true, 52, fonts, 1);
        addText(27002, "0", 0xff981f, false, true, 52, fonts, 1);
        //newBank();


        addBankHover(27026, 4, 27027, 9, 11, "", 35, 25, 324, 1, "Toggle Placeholders", 27028, 10, 12, "", 27029, "Enable placeholders \nmode", "Disable placeholders \nmode", 12, 20, 1034, 1035);
        setBounds(27026, 330, 285, 38, rsinterface);
        setBounds(27027, 330, 285, 39, rsinterface);
    }

    /*
     * Summoning tab
     */
    public static void summoningTabInterface() {
        RSInterface rsi = addTabInterface(54017);
        addText(54019, "Summoning Familiar", fonts, 2, 16750623, true, true);
        addSpriteLoader(54020, 874);

        addFamiliarHead(54021, 75, 50, 875);
        addSpriteLoader(54027, 875);
        addText(54028, "", fonts, 2, 0xFF981F, true, false);

        addHoverButtonWSpriteLoader(54029, 879, 38, 38, "Withdraw BoB", -1, 54030, 1);
        addHoveredImageWSpriteLoader(54030, 880, 38, 38, 54031);

        addHoverButtonWSpriteLoader(54032, 881, 38, 38, "Renew familiar", -1, 54033, 1);
        addHoveredImageWSpriteLoader(54033, 882, 38, 38, 54034);

        addHoverButtonWSpriteLoader(54035, 883, 38, 38, "Call familiar", -1, 54036, 1);
        addHoveredImageWSpriteLoader(54036, 884, 38, 38, 54037);

        addHoverButtonWSpriteLoader(54038, 885, 38, 38, "Dismiss familiar", -1, 54039, 1);
        addHoveredImageWSpriteLoader(54039, 886, 38, 38, 54040);

        addSpriteLoader(54041, 876);
        addSpriteLoader(54042, 877);

        addText(54043, "", fonts, 0, 0xB9B855, false, true);
        addSpriteLoader(54044, 878);
        addText(54045, "", fonts, 0, 0xB9B855, false, true);
        setChildren(18, rsi);
        setBounds(54020, 10, 32, 0, rsi);
        setBounds(54021, 63, 60, 1, rsi);
        setBounds(54027, 12, 144, 2, rsi);
        setBounds(54028, 93, 146, 3, rsi);
        setBounds(54029, 23, 168, 4, rsi);
        setBounds(54030, 23, 168, 5, rsi);
        setBounds(54032, 75, 168, 6, rsi);
        setBounds(54033, 75, 168, 7, rsi);
        setBounds(54035, 23, 213, 8, rsi);
        setBounds(54036, 23, 213, 9, rsi);
        setBounds(54038, 75, 213, 10, rsi);
        setBounds(54039, 75, 213, 11, rsi);
        setBounds(54041, 130, 168, 12, rsi);
        setBounds(54042, 153, 170, 13, rsi);
        setBounds(54043, 148, 198, 14, rsi);
        setBounds(54044, 149, 213, 15, rsi);
        setBounds(54045, 145, 241, 16, rsi);
        setBounds(54019, 91, 9, 17, rsi);
    }

    /*
     * Shop interface
     */
    private static void shopInterface(TextDrawingArea[] tda) {
        RSInterface Interface = addTabInterface(3824);
        setChildren(8, Interface);
        addSprite(3825, 0, "Shop/SHOP");
        addHover(3902, 3, 0, 3826, 1, "Shop/EXIT", 17, 17, "Close Window");
        addHovered(3826, 2, "Shop/EXIT", 17, 17, 3827);
        addText(19679, " ", 0xff981f, false, true, 52, tda, 1);
        addText(19680, " ", 0xbf751d, false, true, 52, tda, 1);
        addButton(19681, 2, "Shop/SHOP", 95, 19, " ", 1);
        setBounds(3825, 12, 12, 0, Interface);
        setBounds(3902, 471, 22, 1, Interface);
        setBounds(3826, 471, 22, 2, Interface);
        setBounds(3900, 60, 75, 3, Interface);
        setBounds(3901, 240, 21, 4, Interface);
        setBounds(19679, 42, 54, 5, Interface);
        setBounds(19680, 150, 54, 6, Interface);
        setBounds(19681, 129, 50, 7, Interface);
        Interface = interfaceCache[3900];
        Interface.invSpritePadX = 8;
        Interface.width = 10;
        Interface.height = 4;
        Interface.invSpritePadY = 10;
        Interface = addTabInterface(19682);
        addSprite(19683, 1, "Shop/SHOP");
        addText(19684, " ", 0xbf751d, false, true, 52, tda, 1);
        addText(19685, " ", 0xff981f, false, true, 52, tda, 1);
        addButton(19686, 2, "Shop/SHOP", 95, 19, " ", 1);
        setChildren(7, Interface);
        setBounds(19683, 12, 12, 0, Interface);
        setBounds(3901, 240, 21, 1, Interface);
        setBounds(19684, 42, 54, 2, Interface);
        setBounds(19685, 150, 54, 3, Interface);
        setBounds(19686, 23, 50, 4, Interface);
        setBounds(3902, 471, 22, 5, Interface);
    }

    /*
     * Friends list tab interface
     */
    private static void friendsTabInterface(TextDrawingArea[] tda) {
        RSInterface tab = addTabInterface(5065);
        RSInterface list = interfaceCache[5066];
        addText(5067, "Friends List", tda, 1, 0xff9933, true, true);
        addText(5070, "Add Friend", tda, 0, 0xff9933, false, true);
        addText(5071, "Delete Friend", tda, 0, 0xff9933, false, true);
        addSpriteLoader(16126, 808);
        addSpriteLoader(16127, 812);
        addHoverButtonWSpriteLoader(5068, 810, 72, 32, "Add Friend", 201, 5072, 1);
        addHoveredImageWSpriteLoader(5072, 811, 72, 32, 5073);
        addHoverButtonWSpriteLoader(5069, 810, 72, 32, "Delete Friend", 202, 5074, 1);
        addHoveredImageWSpriteLoader(5074, 811, 72, 32, 5075);
        tab.totalChildren(11);
        tab.child(0, 5067, 95, 4);
        tab.child(1, 16127, 0, 25);
        tab.child(2, 16126, 0, 221);
        tab.child(3, 5066, 0, 24);
        tab.child(4, 16126, 0, 22);
        tab.child(5, 5068, 15, 226);
        tab.child(6, 5072, 15, 226);
        tab.child(7, 5069, 103, 226);
        tab.child(8, 5074, 103, 226);
        tab.child(9, 5070, 25, 237);
        tab.child(10, 5071, 106, 237);
        list.height = 196;
        list.width = 174;
        int id = 5092;
        for (int i = 0; id <= 5191 && i <= 99; i++) {
            list.children[i] = id;
            list.childX[i] = 3;
            list.childY[i] = list.childY[i] - 7;
            id++;
        }

        id = 5192;
        for (int i = 100; id <= 5291 && i <= 199; i++) {
            list.children[i] = id;
            list.childX[i] = 131;
            list.childY[i] = list.childY[i] - 7;
            id++;
        }
    }

    /**
     * Level up interfaces
     */
    public static void levelUpInterfaces() {
        RSInterface attack = interfaceCache[6247];
        RSInterface defence = interfaceCache[6253];
        RSInterface str = interfaceCache[6206];
        RSInterface hits = interfaceCache[6216];
        RSInterface rng = interfaceCache[4443];
        RSInterface pray = interfaceCache[6242];
        RSInterface mage = interfaceCache[6211];
        RSInterface cook = interfaceCache[6226];
        RSInterface wood = interfaceCache[4272];
        RSInterface flet = interfaceCache[6231];
        RSInterface fish = interfaceCache[6258];
        RSInterface fire = interfaceCache[4282];
        RSInterface craf = interfaceCache[6263];
        RSInterface smit = interfaceCache[6221];
        RSInterface mine = interfaceCache[4416];
        RSInterface herb = interfaceCache[6237];
        RSInterface agil = interfaceCache[4277];
        RSInterface thie = interfaceCache[4261];
        RSInterface slay = interfaceCache[12122];
        RSInterface farm = addTabInterface(5267);
        RSInterface rune = interfaceCache[4267];
        RSInterface cons = interfaceCache[7267];
        RSInterface hunt = addTabInterface(8267);
        RSInterface summ = addTabInterface(9267);
        RSInterface dung = addTabInterface(10267);
        addSkillChatSprite(29578, 0);
        addSkillChatSprite(29579, 1);
        addSkillChatSprite(29580, 2);
        addSkillChatSprite(29581, 3);
        addSkillChatSprite(29582, 4);
        addSkillChatSprite(29583, 5);
        addSkillChatSprite(29584, 6);
        addSkillChatSprite(29585, 7);
        addSkillChatSprite(29586, 8);
        addSkillChatSprite(29587, 9);
        addSkillChatSprite(29588, 10);
        addSkillChatSprite(29589, 11);
        addSkillChatSprite(29590, 12);
        addSkillChatSprite(29591, 13);
        addSkillChatSprite(29592, 14);
        addSkillChatSprite(29593, 15);
        addSkillChatSprite(29594, 16);
        addSkillChatSprite(29595, 17);
        addSkillChatSprite(29596, 18);
        addSkillChatSprite(11897, 19);
        addSkillChatSprite(29598, 20);
        addSkillChatSprite(29599, 21);
        addSkillChatSprite(29600, 22);
        addSkillChatSprite(29601, 23);
        addSkillChatSprite(29602, 24);
        setChildren(4, attack);
        setBounds(29578, 20, 30, 0, attack);
        setBounds(4268, 80, 15, 1, attack);
        setBounds(4269, 80, 45, 2, attack);
        setBounds(358, 95, 75, 3, attack);
        setChildren(4, defence);
        setBounds(29579, 20, 30, 0, defence);
        setBounds(4268, 80, 15, 1, defence);
        setBounds(4269, 80, 45, 2, defence);
        setBounds(358, 95, 75, 3, defence);
        setChildren(4, str);
        setBounds(29580, 20, 30, 0, str);
        setBounds(4268, 80, 15, 1, str);
        setBounds(4269, 80, 45, 2, str);
        setBounds(358, 95, 75, 3, str);
        setChildren(4, hits);
        setBounds(29581, 20, 30, 0, hits);
        setBounds(4268, 80, 15, 1, hits);
        setBounds(4269, 80, 45, 2, hits);
        setBounds(358, 95, 75, 3, hits);
        setChildren(4, rng);
        setBounds(29582, 20, 30, 0, rng);
        setBounds(4268, 80, 15, 1, rng);
        setBounds(4269, 80, 45, 2, rng);
        setBounds(358, 95, 75, 3, rng);
        setChildren(4, pray);
        setBounds(29583, 20, 30, 0, pray);
        setBounds(4268, 80, 15, 1, pray);
        setBounds(4269, 80, 45, 2, pray);
        setBounds(358, 95, 75, 3, pray);
        setChildren(4, mage);
        setBounds(29584, 20, 30, 0, mage);
        setBounds(4268, 80, 15, 1, mage);
        setBounds(4269, 80, 45, 2, mage);
        setBounds(358, 95, 75, 3, mage);
        setChildren(4, cook);
        setBounds(29585, 20, 30, 0, cook);
        setBounds(4268, 80, 15, 1, cook);
        setBounds(4269, 80, 45, 2, cook);
        setBounds(358, 95, 75, 3, cook);
        setChildren(4, wood);
        setBounds(29586, 20, 30, 0, wood);
        setBounds(4268, 80, 15, 1, wood);
        setBounds(4269, 80, 45, 2, wood);
        setBounds(358, 95, 75, 3, wood);
        setChildren(4, flet);
        setBounds(29587, 20, 30, 0, flet);
        setBounds(4268, 80, 15, 1, flet);
        setBounds(4269, 80, 45, 2, flet);
        setBounds(358, 95, 75, 3, flet);
        setChildren(4, fish);
        setBounds(29588, 20, 30, 0, fish);
        setBounds(4268, 80, 15, 1, fish);
        setBounds(4269, 80, 45, 2, fish);
        setBounds(358, 95, 75, 3, fish);
        setChildren(4, fire);
        setBounds(29589, 20, 30, 0, fire);
        setBounds(4268, 80, 15, 1, fire);
        setBounds(4269, 80, 45, 2, fire);
        setBounds(358, 95, 75, 3, fire);
        setChildren(4, craf);
        setBounds(29590, 20, 30, 0, craf);
        setBounds(4268, 80, 15, 1, craf);
        setBounds(4269, 80, 45, 2, craf);
        setBounds(358, 95, 75, 3, craf);
        setChildren(4, smit);
        setBounds(29591, 20, 30, 0, smit);
        setBounds(4268, 80, 15, 1, smit);
        setBounds(4269, 80, 45, 2, smit);
        setBounds(358, 95, 75, 3, smit);
        setChildren(4, mine);
        setBounds(29592, 20, 30, 0, mine);
        setBounds(4268, 80, 15, 1, mine);
        setBounds(4269, 80, 45, 2, mine);
        setBounds(358, 95, 75, 3, mine);
        setChildren(4, herb);
        setBounds(29593, 20, 30, 0, herb);
        setBounds(4268, 80, 15, 1, herb);
        setBounds(4269, 80, 45, 2, herb);
        setBounds(358, 95, 75, 3, herb);
        setChildren(4, agil);
        setBounds(29594, 20, 30, 0, agil);
        setBounds(4268, 80, 15, 1, agil);
        setBounds(4269, 80, 45, 2, agil);
        setBounds(358, 95, 75, 3, agil);
        setChildren(4, thie);
        setBounds(29595, 20, 30, 0, thie);
        setBounds(4268, 80, 15, 1, thie);
        setBounds(4269, 80, 45, 2, thie);
        setBounds(358, 95, 75, 3, thie);
        setChildren(4, slay);
        setBounds(29596, 20, 30, 0, slay);
        setBounds(4268, 80, 15, 1, slay);
        setBounds(4269, 80, 45, 2, slay);
        setBounds(358, 95, 75, 3, slay);
        setChildren(4, farm);
        setBounds(11897, 20, 30, 0, farm);
        setBounds(4268, 80, 15, 1, farm);
        setBounds(4269, 80, 45, 2, farm);
        setBounds(358, 95, 75, 3, farm);
        setChildren(4, rune);
        setBounds(29598, 20, 30, 0, rune);
        setBounds(4268, 80, 15, 1, rune);
        setBounds(4269, 80, 45, 2, rune);
        setBounds(358, 95, 75, 3, rune);
        setChildren(3, cons);
        setBounds(4268, 80, 15, 0, cons);
        setBounds(4269, 80, 45, 1, cons);
        setBounds(358, 95, 75, 2, cons);
        setChildren(4, hunt);
        setBounds(29600, 20, 30, 0, hunt);
        setBounds(4268, 80, 15, 1, hunt);
        setBounds(4269, 80, 45, 2, hunt);
        setBounds(358, 95, 75, 3, hunt);
        setChildren(4, summ);
        setBounds(29601, 20, 30, 0, summ);
        setBounds(4268, 80, 15, 1, summ);
        setBounds(4269, 80, 45, 2, summ);
        setBounds(358, 95, 75, 3, summ);
        setChildren(4, dung);
        setBounds(29602, 20, 30, 0, dung);
        setBounds(4268, 80, 15, 1, dung);
        setBounds(4269, 80, 45, 2, dung);
        setBounds(358, 95, 75, 3, dung);
    }

    /*
     * Ignore list tab interface
     */
    private static void ignoreTabInterface(TextDrawingArea[] tda) {
        RSInterface tab = addTabInterface(5715);
        RSInterface list = interfaceCache[5716];
        addText(5717, "Ignore List", tda, 1, 0xff9933, true, true);
        addText(5720, "Add Name", tda, 0, 0xff9933, false, true);
        addText(5721, "Delete Name", tda, 0, 0xff9933, false, true);
        addHoverButtonWSpriteLoader(5718, 810, 72, 32, "Add Name", 501, 5722, 1);
        addHoveredImageWSpriteLoader(5722, 811, 72, 32, 5723);
        addHoverButtonWSpriteLoader(5719, 810, 72, 32, "Delete Name", 502, 5724, 1);
        addHoveredImageWSpriteLoader(5724, 811, 72, 32, 5725);
        tab.totalChildren(11);
        tab.child(0, 5717, 95, 4);
        tab.child(1, 16127, 0, 25);
        tab.child(2, 16126, 0, 221);
        tab.child(3, 5716, 0, 24);
        tab.child(4, 16126, 0, 22);
        tab.child(5, 5718, 15, 226);
        tab.child(6, 5722, 15, 226);
        tab.child(7, 5719, 103, 226);
        tab.child(8, 5724, 103, 226);
        tab.child(9, 5720, 27, 237);
        tab.child(10, 5721, 108, 237);
        list.height = 196;
        list.width = 174;
        int id = 5742;
        for (int i = 0; id <= 5841 && i <= 99; i++) {
            list.children[i] = id;
            list.childX[i] = 3;
            list.childY[i] = list.childY[i] - 7;
            id++;
        }
    }

    /*
     * Prayer interface
     */
    private static void prayerTabInterface() {
        RSInterface prayerMenu = addTabInterface(5608);
        int index = 0;
        int prayIndex = 0;
        int firstRowXPos = 10;
        int firstRowYPos = 50;
        int secondRowXPos = 10;
        int secondRowYPos = 86;
        int thirdRowXPos = 10;
        int thirdRowYPos = 122;
        int fourthRowXPos = 10;
        int fourthRowYPos = 159;
        int fifthRowXPos = 10;
        int fifthRowYPos = 86;
        int sixthRowXPos = 1;
        int sixthRowYPos = 52;
        addText(687, "", 0xff981f, false, true, -1, fonts, 1);
        addSpriteLoader(25105, 813);
        addPrayerWithTooltip(25000, 0, 83, 0, prayIndex, 25052, "Activate @lre@Thick Skin");
        prayIndex++;
        addPrayerWithTooltip(25002, 0, 84, 3, prayIndex, 25054, "Activate @lre@Burst of Strength");
        prayIndex++;
        addPrayerWithTooltip(25004, 0, 85, 6, prayIndex, 25056, "Activate @lre@Clarity of Thought");
        prayIndex++;
        addPrayerWithTooltip(25006, 0, 601, 7, prayIndex, 25058, "Activate @lre@Sharp Eye");
        prayIndex++;
        addPrayerWithTooltip(25008, 0, 602, 8, prayIndex, 25060, "Activate @lre@Mystic Will");
        prayIndex++;
        addPrayerWithTooltip(25010, 0, 86, 9, prayIndex, 25062, "Activate @lre@Rock Skin");
        prayIndex++;
        addPrayerWithTooltip(25012, 0, 87, 12, prayIndex, 25064, "Activate @lre@Superhuman Strength");
        prayIndex++;
        addPrayerWithTooltip(25014, 0, 88, 15, prayIndex, 25066, "Activate @lre@Improved Reflexes");
        prayIndex++;
        addPrayerWithTooltip(25016, 0, 89, 18, prayIndex, 25068, "Activate @lre@Rapid Restore");
        prayIndex++;
        addPrayerWithTooltip(25018, 0, 90, 21, prayIndex, 25070, "Activate @lre@Rapid Heal");
        prayIndex++;
        addPrayerWithTooltip(25020, 0, 91, 24, prayIndex, 25072, "Activate @lre@Protect Item");
        prayIndex++;
        addPrayerWithTooltip(25022, 0, 603, 25, prayIndex, 25074, "Activate @lre@Hawk Eye");
        prayIndex++;
        addPrayerWithTooltip(25024, 0, 604, 26, prayIndex, 25076, "Activate @lre@Mystic Lore");
        prayIndex++;
        addPrayerWithTooltip(25026, 0, 92, 27, prayIndex, 25078, "Activate @lre@Steel Skin");
        prayIndex++;
        addPrayerWithTooltip(25028, 0, 93, 30, prayIndex, 25080, "Activate @lre@Ultimate Strength");
        prayIndex++;
        addPrayerWithTooltip(25030, 0, 94, 33, prayIndex, 25082, "Activate @lre@Incredible Reflexes");
        prayIndex++;
        addPrayerWithTooltip(25032, 0, 95, 36, prayIndex, 25084, "Activate @lre@Protect from Magic");
        prayIndex++;
        addPrayerWithTooltip(25034, 0, 96, 39, prayIndex, 25086, "Activate @lre@Protect from Missles");
        prayIndex++;
        addPrayerWithTooltip(25036, 0, 97, 42, prayIndex, 25088, "Activate @lre@Protect from Melee");
        prayIndex++;
        addPrayerWithTooltip(25038, 0, 605, 43, prayIndex, 25090, "Activate @lre@Eagle Eye");
        prayIndex++;
        addPrayerWithTooltip(25040, 0, 606, 44, prayIndex, 25092, "Activate @lre@Mystic Might");
        prayIndex++;
        addPrayerWithTooltip(25042, 0, 98, 45, prayIndex, 25094, "Activate @lre@Retribution");
        prayIndex++;
        addPrayerWithTooltip(25044, 0, 99, 48, prayIndex, 25096, "Activate @lre@Redemption");
        prayIndex++;
        addPrayerWithTooltip(25046, 0, 100, 51, prayIndex, 25098, "Activate @lre@Smite");
        prayIndex++;
        addPrayerWithTooltip(25048, 0, 607, 59, prayIndex, 25100, "Activate @lre@Chivalry");
        prayIndex++;
        addPrayerWithTooltip(25050, 0, 608, 69, prayIndex, 25102, "Activate @lre@Piety");
        prayIndex++;
        addTooltip(25052, "Level 01\nThick Skin\nIncreases your Defence by 5%");
        addTooltip(25054, "Level 04\nBurst of Strength\nIncreases your Strength by 5%");
        addTooltip(25056, "Level 07\nClarity of Thought\nIncreases your Attack by 5%");
        addTooltip(25058, "Level 08\nSharp Eye\nIncreases your Ranged by 5%");
        addTooltip(25060, "Level 09\nMystic Will\nIncreases your Magic by 5%");
        addTooltip(25062, "Level 10\nRock Skin\nIncreases your Defence by 10%");
        addTooltip(25064, "Level 13\nSuperhuman Strength\nIncreases your Strength by 10%");
        addTooltip(25066, "Level 16\nImproved Reflexes\nIncreases your Attack by 10%");
        addTooltip(25068, "Level 19\nRapid Restore\n2x restore rate for all stats\nexcept Hitpoints, Summoning\nand Prayer");
        addTooltip(25070, "Level 22\nRapid Heal\n2x restore rate for the\nHitpoints stat");
        addTooltip(25072, "Level 25\nProtect Item\nKeep 1 extra item if you die");
        addTooltip(25074, "Level 26\nHawk Eye\nIncreases your Ranged by 10%");
        addTooltip(25076, "Level 27\nMystic Lore\nIncreases your Magic by 10%");
        addTooltip(25078, "Level 28\nSteel Skin\nIncreases your Defence by 15%");
        addTooltip(25080, "Level 31\nUltimate Strength\nIncreases your Strength by 15%");
        addTooltip(25082, "Level 34\nIncredible Reflexes\nIncreases your Attack by 15%");
        addTooltip(25084, "Level 37\nProtect from Magic\nProtection from magical attacks");
        addTooltip(25086, "Level 40\nProtect from Missles\nProtection from ranged attacks");
        addTooltip(25088, "Level 43\nProtect from Melee\nProtection from melee attacks");
        addTooltip(25090, "Level 44\nEagle Eye\nIncreases your Ranged by 15%");
        addTooltip(25092, "Level 45\nMystic Might\nIncreases your Magic by 15%");
        addTooltip(25094, "Level 46\nRetribution\nInflicts damage to nearby\ntargets if you die");
        addTooltip(25096, "Level 49\nRedemption\nHeals you when damaged\nand Hitpoints falls\nbelow 10%");
        addTooltip(25098, "Level 52\nSmite\n1/4 of damage dealt is\nalso removed from\nopponent's Prayer");
        addTooltip(25100, "Level 60\nChivalry\nIncreases your Defence by 20%,\nStrength by 18%, and Attack by\n15%");
        addTooltip(25102, "Level 70\nPiety\nIncreases your Defence by 25%,\nStrength by 23%, and Attack by\n20%");
        setChildren(80, prayerMenu);
        setBounds(687, 85, 241, index, prayerMenu);
        index++;
        setBounds(25105, 65, 241, index, prayerMenu);
        index++;
        setBounds(25000, 2, 5, index, prayerMenu);
        index++;
        setBounds(25001, 5, 8, index, prayerMenu);
        index++;
        setBounds(25002, 40, 5, index, prayerMenu);
        index++;
        setBounds(25003, 44, 8, index, prayerMenu);
        index++;
        setBounds(25004, 76, 5, index, prayerMenu);
        index++;
        setBounds(25005, 79, 11, index, prayerMenu);
        index++;
        setBounds(25006, 113, 5, index, prayerMenu);
        index++;
        setBounds(25007, 116, 10, index, prayerMenu);
        index++;
        setBounds(25008, 150, 5, index, prayerMenu);
        index++;
        setBounds(25009, 153, 9, index, prayerMenu);
        index++;
        setBounds(25010, 2, 45, index, prayerMenu);
        index++;
        setBounds(25011, 5, 48, index, prayerMenu);
        index++;
        setBounds(25012, 39, 45, index, prayerMenu);
        index++;
        setBounds(25013, 44, 47, index, prayerMenu);
        index++;
        setBounds(25014, 76, 45, index, prayerMenu);
        index++;
        setBounds(25015, 79, 49, index, prayerMenu);
        index++;
        setBounds(25016, 113, 45, index, prayerMenu);
        index++;
        setBounds(25017, 116, 50, index, prayerMenu);
        index++;
        setBounds(25018, 151, 45, index, prayerMenu);
        index++;
        setBounds(25019, 154, 50, index, prayerMenu);
        index++;
        setBounds(25020, 2, 82, index, prayerMenu);
        index++;
        setBounds(25021, 4, 84, index, prayerMenu);
        index++;
        setBounds(25022, 40, 82, index, prayerMenu);
        index++;
        setBounds(25023, 44, 87, index, prayerMenu);
        index++;
        setBounds(25024, 77, 82, index, prayerMenu);
        index++;
        setBounds(25025, 81, 85, index, prayerMenu);
        index++;
        setBounds(25026, 114, 83, index, prayerMenu);
        index++;
        setBounds(25027, 117, 85, index, prayerMenu);
        index++;
        setBounds(25028, 153, 83, index, prayerMenu);
        index++;
        setBounds(25029, 156, 87, index, prayerMenu);
        index++;
        setBounds(25030, 2, 120, index, prayerMenu);
        index++;
        setBounds(25031, 5, 125, index, prayerMenu);
        index++;
        setBounds(25032, 40, 120, index, prayerMenu);
        index++;
        setBounds(25033, 43, 124, index, prayerMenu);
        index++;
        setBounds(25034, 78, 120, index, prayerMenu);
        index++;
        setBounds(25035, 83, 124, index, prayerMenu);
        index++;
        setBounds(25036, 114, 120, index, prayerMenu);
        index++;
        setBounds(25037, 115, 121, index, prayerMenu);
        index++;
        setBounds(25038, 151, 120, index, prayerMenu);
        index++;
        setBounds(25039, 154, 124, index, prayerMenu);
        index++;
        setBounds(25040, 2, 158, index, prayerMenu);
        index++;
        setBounds(25041, 5, 160, index, prayerMenu);
        index++;
        setBounds(25042, 39, 158, index, prayerMenu);
        index++;
        setBounds(25043, 41, 158, index, prayerMenu);
        index++;
        setBounds(25044, 76, 158, index, prayerMenu);
        index++;
        setBounds(25045, 79, 163, index, prayerMenu);
        index++;
        setBounds(25046, 114, 158, index, prayerMenu);
        index++;
        setBounds(25047, 116, 158, index, prayerMenu);
        index++;
        setBounds(25048, 153, 158, index, prayerMenu);
        index++;
        setBounds(25049, 161, 160, index, prayerMenu);
        index++;
        setBounds(25050, 2, 196, index, prayerMenu);
        index++;
        setBounds(25051, 4, 207, index, prayerMenu);
        setBoundry(++index, 25052, firstRowXPos - 2, firstRowYPos, prayerMenu);
        setBoundry(++index, 25054, firstRowXPos - 5, firstRowYPos, prayerMenu);
        setBoundry(++index, 25056, firstRowXPos, firstRowYPos, prayerMenu);
        setBoundry(++index, 25058, firstRowXPos, firstRowYPos, prayerMenu);
        setBoundry(++index, 25060, firstRowXPos, firstRowYPos, prayerMenu);
        setBoundry(++index, 25062, secondRowXPos - 9, secondRowYPos, prayerMenu);
        setBoundry(++index, 25064, secondRowXPos - 11, secondRowYPos, prayerMenu);
        setBoundry(++index, 25066, secondRowXPos, secondRowYPos, prayerMenu);
        setBoundry(++index, 25068, secondRowXPos, secondRowYPos, prayerMenu);
        setBoundry(++index, 25070, secondRowXPos + 25, secondRowYPos, prayerMenu);
        setBoundry(++index, 25072, thirdRowXPos, thirdRowYPos, prayerMenu);
        setBoundry(++index, 25074, thirdRowXPos - 2, thirdRowYPos, prayerMenu);
        setBoundry(++index, 25076, thirdRowXPos, thirdRowYPos, prayerMenu);
        setBoundry(++index, 25078, thirdRowXPos - 7, thirdRowYPos, prayerMenu);
        setBoundry(++index, 25080, thirdRowXPos - 10, thirdRowYPos, prayerMenu);
        setBoundry(++index, 25082, fourthRowXPos, fourthRowYPos, prayerMenu);
        setBoundry(++index, 25084, fourthRowXPos - 8, fourthRowYPos, prayerMenu);
        setBoundry(++index, 25086, fourthRowXPos - 7, fourthRowYPos, prayerMenu);
        setBoundry(++index, 25088, fourthRowXPos - 2, fourthRowYPos, prayerMenu);
        setBoundry(++index, 25090, fourthRowXPos - 2, fourthRowYPos, prayerMenu);
        setBoundry(++index, 25092, fifthRowXPos, fifthRowYPos, prayerMenu);
        setBoundry(++index, 25094, fifthRowXPos, fifthRowYPos - 20, prayerMenu);
        setBoundry(++index, 25096, fifthRowXPos, fifthRowYPos - 25, prayerMenu);
        setBoundry(++index, 25098, fifthRowXPos + 15, fifthRowYPos - 25, prayerMenu);
        setBoundry(++index, 25100, fifthRowXPos - 12, fifthRowYPos - 20, prayerMenu);
        setBoundry(++index, 25102, sixthRowXPos - 2, sixthRowYPos, prayerMenu);
        index++;
    }
			/*private static void chaosRaidInterface() {
				RSInterface tab = addTabInterface(37199);
				//addTransparentSpriteWSpriteLoader(37209, 969, 218);

				addText(37201, "Raid Bosses:", fonts, 1, 0xff981f, true, true);
				addText(37202, "Jungle Demon", fonts, 0, 0x86B404, true, true);
				addText(37203, "Blood Reaver", fonts, 0, 0x86B404, true, true);
				addText(37204, "Glacor", fonts, 0, 0x86B404, true, true);
				addText(37205, "Barrelchest", fonts, 0, 0x86B404, true, true);
				addText(37206, "Bork", fonts, 0, 0x86B404, true, true);
				addText(37207, "Plane-Freezer", fonts, 0, 0x86B404, true, true);
				addText(37208, "Unholy Cursebearer", fonts, 0, 0x86B404, true, true);
				addText(37210, "Skotizo", fonts, 0, 0x86B404, true, true);
				addText(37211, "Bandos Avatar", fonts, 0, 0x86B404, true, true);
				addText(37212, "Alucard", fonts, 0, 0x86B404, true, true);
				addText(37213, "Killcount: 2", fonts, 2, 0xff981f, true, true);
				tab.totalChildren(12);
				//tab.child(0, 37209, 369, 185); //don't touch
				tab.child(0, 37202, 438, 206);
				tab.child(1, 37203, 438, 221);
				tab.child(2, 37204, 438, 236);
				tab.child(3, 37205, 438, 251);
				tab.child(4, 37206, 438, 266);
				tab.child(5, 37207, 438, 281);
				tab.child(6, 37208, 441, 294);
				tab.child(7, 37210, 441, 309);
				tab.child(8, 37211, 441, 324);
				tab.child(9, 37212, 441, 339);
				tab.child(10, 37213, 441, 354);
				tab.child(11, 37201, 438, 187);

			}*/

    /**
     * Quick prayers interface
     */
    private static void quickPrayersInterface() {
        int frame = 0;
        RSInterface tab = addTabInterface(17200);
        addSpriteLoader(17201, 935);
        addText(17230, "Select your quick prayers:", fonts, 0, 0xff981f, false, true);
        addTransparentSpriteWSpriteLoader(17229, 936, 50);
        int i = 17202;
        for (int j = 630; j <= 659; j++) {
            addConfigButtonWSpriteLoader(i, 17200, 938, 937, 14, 15, "Select", 0, 1, j);
            i += i == 17229 ? 50 : 1;
        }

        addHoverButtonWSpriteLoader(17231, 939, 190, 24, "Confirm Selection", -1, 17232, 1);
        addHoveredImageWSpriteLoader(17232, 940, 190, 24, 17233);

        setChildren(62, tab);
        setBounds(25001, 5, 28, frame++, tab);
        setBounds(25003, 44, 28, frame++, tab);
        setBounds(25005, 79, 31, frame++, tab);
        setBounds(25007, 116, 30, frame++, tab);
        setBounds(25009, 153, 29, frame++, tab);
        setBounds(25011, 5, 68, frame++, tab);
        setBounds(25013, 44, 67, frame++, tab);
        setBounds(25015, 79, 69, frame++, tab);
        setBounds(25017, 116, 70, frame++, tab);
        setBounds(25019, 154, 70, frame++, tab);
        setBounds(25021, 4, 104, frame++, tab);
        setBounds(25023, 44, 107, frame++, tab);
        setBounds(25025, 81, 105, frame++, tab);
        setBounds(25027, 117, 105, frame++, tab);
        setBounds(25029, 156, 107, frame++, tab);
        setBounds(25031, 5, 145, frame++, tab);
        setBounds(25033, 43, 144, frame++, tab);
        setBounds(25035, 83, 144, frame++, tab);
        setBounds(25037, 115, 141, frame++, tab);
        setBounds(25039, 154, 144, frame++, tab);
        setBounds(25041, 5, 180, frame++, tab);
        setBounds(25043, 41, 178, frame++, tab);
        setBounds(25045, 79, 183, frame++, tab);
        setBounds(25047, 116, 178, frame++, tab);
        setBounds(25049, 161, 180, frame++, tab);
        setBounds(25051, 4, 219, frame++, tab);
        setBounds(18019, 44, 214, frame++, tab);
        setBounds(18026, 80, 214, frame++, tab);
        setBounds(17229, 0, 25, frame++, tab);
        setBounds(17201, 0, 22, frame++, tab);
        setBounds(17201, 0, 237, frame++, tab);
        setBounds(17202, 2, 25, frame++, tab);
        setBounds(17203, 41, 25, frame++, tab);
        setBounds(17204, 76, 25, frame++, tab);
        setBounds(17205, 113, 25, frame++, tab);
        setBounds(17206, 150, 25, frame++, tab);
        setBounds(17207, 2, 65, frame++, tab);
        setBounds(17208, 41, 65, frame++, tab);
        setBounds(17209, 76, 65, frame++, tab);
        setBounds(17210, 113, 65, frame++, tab);
        setBounds(17211, 150, 65, frame++, tab);
        setBounds(17212, 2, 102, frame++, tab);
        setBounds(17213, 41, 102, frame++, tab);
        setBounds(17214, 76, 102, frame++, tab);
        setBounds(17215, 113, 102, frame++, tab);
        setBounds(17216, 150, 102, frame++, tab);
        setBounds(17217, 2, 141, frame++, tab);
        setBounds(17218, 41, 141, frame++, tab);
        setBounds(17219, 76, 141, frame++, tab);
        setBounds(17220, 113, 141, frame++, tab);
        setBounds(17221, 150, 141, frame++, tab);
        setBounds(17222, 2, 177, frame++, tab);
        setBounds(17223, 41, 177, frame++, tab);
        setBounds(17224, 76, 177, frame++, tab);
        setBounds(17225, 113, 177, frame++, tab);
        setBounds(17226, 150, 177, frame++, tab);
        setBounds(17227, 1, 211, frame++, tab);
        setBounds(17230, 5, 5, frame++, tab);
        setBounds(17231, 0, 237, frame++, tab);
        setBounds(17232, 0, 237, frame++, tab);
        setBounds(17279, 41, 211, frame++, tab);
        setBounds(17280, 76, 211, frame++, tab);
    }

    /*
     * Curse tab
     */
    private static void curseTabInterface() {
        RSInterface Interface = addTabInterface(32500);
        int index = 0;
        addSpriteLoader(688, 814);
        //addTooltip(19021, "This is the effect that prayers\nand curses have during combat.\nIt includes curses that have\nbeen used against you. The\nadjustment has no effect\noutside of combat. The\npercentage shown is relative to\n your skill level, and may vary\ndepending on the enemy you are\nfighting, and the prayers or\n curses used. Partial\npercentages are not shown.");
        addSpriteLoader(689, 815);
        addText(19025, "  Stat Adjustments", 0xFFCC00, false, true, 52, fonts, 0);
        addText(690, "690", 0xFF981F, false, false, -1, fonts, 0);
        addText(691, "691", 0xFF981F, false, false, -1, fonts, 0);
        addText(692, "692", 0xFF981F, false, false, -1, fonts, 0);
        addText(693, "693", 0xFF981F, false, false, -1, fonts, 0);
        addText(694, "694", 0xFF981F, false, false, -1, fonts, 0);
        addText(687, "99/99", 0xFF981F, false, false, -1, fonts, 1);
        addSpriteLoader(32502, 813);
        addPrayer(32503, 0, 610, 49, 7, "Protect Item", 32582);
        addPrayer(32505, 0, 611, 49, 4, "Sap Warrior", 32544);
        addPrayer(32507, 0, 612, 51, 5, "Sap Ranger", 32546);
        addPrayer(32509, 0, 613, 53, 3, "Sap Mage", 32548);
        addPrayer(32511, 0, 614, 55, 2, "Sap Spirit", 32550);
        addPrayer(32513, 0, 615, 58, 18, "Berserker", 32552);
        addPrayer(32515, 0, 616, 61, 15, "Deflect Summoning", 32554);
        addPrayer(32517, 0, 617, 64, 17, "Deflect Magic", 32556);
        addPrayer(32519, 0, 618, 67, 16, "Deflect Missiles", 32558);
        addPrayer(32521, 0, 619, 70, 6, "Deflect Melee", 32560);
        addPrayer(32523, 0, 620, 73, 9, "Leech Attack", 32562);
        addPrayer(32525, 0, 621, 75, 10, "Leech Ranged", 32564);
        addPrayer(32527, 0, 622, 77, 11, "Leech Magic", 32566);
        addPrayer(32529, 0, 623, 79, 12, "Leech Defence", 32568);
        addPrayer(32531, 0, 624, 81, 13, "Leech Strength", 32570);
        addPrayer(32533, 0, 625, 83, 14, "Leech Energy", 32572);
        addPrayer(32535, 0, 626, 85, 19, "Leech Special Attack", 32574);
        addPrayer(32537, 0, 627, 88, 1, "Wrath", 32576);
        addPrayer(32539, 0, 628, 91, 8, "Soul Split", 32578);
        addPrayer(32541, 0, 629, 94, 20, "Turmoil", 32580);
        addTooltip(32582, "Level 50\nProtect Item\nKeep 1 extra item if you die");
        addTooltip(32544, "Level 50\nSap Warrior\nDrains 10% of enemy Attack,\nStrength and Defence,\nincreasing to 20% over time");
        addTooltip(32546, "Level 52\nSap Ranger\nDrains 10% of enemy Ranged\nand Defence, increasing to 20%\nover time");
        addTooltip(32548, "Level 54\nSap Mage\nDrains 10% of enemy Magic\nand Defence, increasing to 20%\nover time");
        addTooltip(32550, "Level 56\nSap Spirit\nDrains enenmy special attack\nenergy");
        addTooltip(32552, "Level 59\nBerserker\nBoosted stats last 15% longer");
        addTooltip(32554, "Level 62\nDeflect Summoning\nReduces damage dealt from\nSummoning scrolls, prevents the\nuse of a familiar's special\nattack, and can deflect some of\ndamage back to the attacker");
        addTooltip(32556, "Level 65\nDeflect Magic\nProtects against magical attacks\nand can deflect some of the\ndamage back to the attacker");
        addTooltip(32558, "Level 68\nDeflect Missiles\nProtects against ranged attacks\nand can deflect some of the\ndamage back to the attacker");
        addTooltip(32560, "Level 71\nDeflect Melee\nProtects against melee attacks\nand can deflect some of the\ndamage back to the attacker");
        addTooltip(32562, "Level 74\nLeech Attack\nBoosts Attack by 5%, increasing\nto 10% over time, while draining\nenemy Attack by 10%, increasing\nto 25% over time");
        addTooltip(32564, "Level 76\nLeech Ranged\nBoosts Ranged by 5%, increasing\nto 10% over time, while draining\nenemy Ranged by 10%,\nincreasing to 25% over\ntime");
        addTooltip(32566, "Level 78\nLeech Magic\nBoosts Magic by 5%, increasing\nto 10% over time, while draining\nenemy Magic by 10%, increasing\nto 25% over time");
        addTooltip(32568, "Level 80\nLeech Defence\nBoosts Defence by 5%, increasing\nto 10% over time, while draining\n enemy Defence by10%,\nincreasing to 25% over\ntime");
        addTooltip(32570, "Level 82\nLeech Strength\nBoosts Strength by 5%, increasing\nto 10% over time, while draining\nenemy Strength by 10%, increasing\n to 25% over time");
        addTooltip(32572, "Level 84\nLeech Energy\nDrains enemy run energy, while\nincreasing your own");
        addTooltip(32574, "Level 86\nLeech Special Attack\nDrains enemy special attack\nenergy, while increasing your\nown");
        addTooltip(32576, "Level 89\nWrath\nInflicts damage to nearby\ntargets if you die");
        addTooltip(32578, "Level 92\nSoul Split\n1/4 of damage dealt is also removed\nfrom opponent's Prayer and\nadded to your Hitpoints");
        addTooltip(32580, "Level 95\nTurmoil\nIncreases Attack and Defence\nby 15%, plus 15% of enemy's\nlevel, and Strength by 23% plus\n10% of enemy's level");
        setChildren(70, Interface);
        /*curse start*/
        setBounds(689, 0, 217, index, Interface);
        index++;
        //setBounds(701, 0, 217, index, Interface);index++;
        setBounds(687, 85, 241, index, Interface);
        index++;
        setBounds(688, 0, 170, index, Interface);
        index++;
        setBounds(690, 2, 200, index, Interface);
        index++;
        setBounds(691, 41, 200, index, Interface);
        index++;
        setBounds(692, 79, 200, index, Interface);
        index++;
        setBounds(693, 117, 200, index, Interface);
        index++;
        setBounds(694, 160, 200, index, Interface);
        index++;
        setBounds(19025, 47, 218, index, Interface);
        index++;
        //setBounds(19030, 47, 219, index, Interface);index++;
        setBounds(32502, 65, 241, index, Interface);
        index++;
        setBounds(32503, 2, 5, index, Interface);
        index++;
        setBounds(32504, 8, 8, index, Interface);
        index++;
        setBounds(32505, 40, 5, index, Interface);
        index++;
        setBounds(32506, 47, 12, index, Interface);
        index++;
        setBounds(32507, 76, 5, index, Interface);
        index++;
        setBounds(32508, 82, 11, index, Interface);
        index++;
        setBounds(32509, 113, 5, index, Interface);
        index++;
        setBounds(32510, 116, 8, index, Interface);
        index++;
        setBounds(32511, 150, 5, index, Interface);
        index++;
        setBounds(32512, 155, 10, index, Interface);
        index++;
        setBounds(32513, 2, 45, index, Interface);
        index++;
        setBounds(32514, 9, 48, index, Interface);
        index++;
        setBounds(32515, 39, 45, index, Interface);
        index++;
        setBounds(32516, 42, 47, index, Interface);
        index++;
        setBounds(32517, 76, 45, index, Interface);
        index++;
        setBounds(32518, 79, 48, index, Interface);
        index++;
        setBounds(32519, 113, 45, index, Interface);
        index++;
        setBounds(32520, 116, 48, index, Interface);
        index++;
        setBounds(32521, 151, 45, index, Interface);
        index++;
        setBounds(32522, 154, 48, index, Interface);
        index++;
        setBounds(32523, 2, 82, index, Interface);
        index++;
        setBounds(32524, 6, 86, index, Interface);
        index++;
        setBounds(32525, 40, 82, index, Interface);
        index++;
        setBounds(32526, 42, 86, index, Interface);
        index++;
        setBounds(32527, 77, 82, index, Interface);
        index++;
        setBounds(32528, 79, 86, index, Interface);
        index++;
        setBounds(32529, 114, 83, index, Interface);
        index++;
        setBounds(32530, 119, 87, index, Interface);
        index++;
        setBounds(32531, 153, 83, index, Interface);
        index++;
        setBounds(32532, 156, 86, index, Interface);
        index++;
        setBounds(32533, 2, 120, index, Interface);
        index++;
        setBounds(32534, 7, 125, index, Interface);
        index++;
        setBounds(32535, 40, 120, index, Interface);
        index++;
        setBounds(32536, 45, 124, index, Interface);
        index++;
        setBounds(32537, 78, 120, index, Interface);
        index++;
        setBounds(32538, 86, 124, index, Interface);
        index++;
        setBounds(32539, 114, 120, index, Interface);
        index++;
        setBounds(32540, 120, 125, index, Interface);
        index++;
        setBounds(32541, 151, 120, index, Interface);
        index++;
        setBounds(32542, 153, 127, index, Interface);
        index++;
        setBounds(32582, 10, 40, index, Interface);
        index++;
        setBounds(32544, 20, 40, index, Interface);
        index++;
        setBounds(32546, 20, 40, index, Interface);
        index++;
        setBounds(32548, 20, 40, index, Interface);
        index++;
        setBounds(32550, 20, 40, index, Interface);
        index++;
        setBounds(32552, 10, 80, index, Interface);
        index++;
        setBounds(32554, 10, 80, index, Interface);
        index++;
        setBounds(32556, 10, 80, index, Interface);
        index++;
        setBounds(32558, 10, 80, index, Interface);
        index++;
        setBounds(32560, 10, 80, index, Interface);
        index++;
        setBounds(32562, 10, 120, index, Interface);
        index++;
        setBounds(32564, 10, 120, index, Interface);
        index++;
        setBounds(32566, 10, 120, index, Interface);
        index++;
        setBounds(32568, 5, 120, index, Interface);
        index++;
        setBounds(32570, 5, 120, index, Interface);
        index++;
        setBounds(32572, 10, 160, index, Interface);
        index++;
        setBounds(32574, 10, 160, index, Interface);
        index++;
        setBounds(32576, 10, 160, index, Interface);
        index++;
        setBounds(32578, 10, 160, index, Interface);
        index++;
        setBounds(32580, 10, 160, index, Interface);
        index++;
    }

    private static void quickCursesInterface() {
        int frame = 0;
        RSInterface tab = addTabInterface(17234);
        addText(17235, "Select your quick curses:", fonts, 0, 0xff981f, false, true);
        int i = 17202;
        for (int j = 630; i <= 17222 || j <= 656; j++) {
            addConfigButtonWSpriteLoader(i, 17200, 938, 937, 14, 15, "Select", 0, 1, j);
            i++;
        }


        addHoverButtonWSpriteLoader(17231, 939, 190, 24, "Confirm Selection", -1, 17232, 1);
        addHoveredImageWSpriteLoader(17232, 940, 190, 24, 17233);


        setChildren(46, tab);
        setBounds(32504, 5, 8 + 17, frame++, tab);
        setBounds(32506, 44, 8 + 20, frame++, tab);
        setBounds(32508, 79, 11 + 19, frame++, tab);
        setBounds(32510, 116, 10 + 18, frame++, tab);
        setBounds(32512, 153, 9 + 20, frame++, tab);
        setBounds(32514, 5, 48 + 18, frame++, tab);
        setBounds(32516, 44, 47 + 21, frame++, tab);
        setBounds(32518, 79, 49 + 20, frame++, tab);
        setBounds(32520, 116, 50 + 19, frame++, tab);
        setBounds(32522, 154, 50 + 20, frame++, tab);
        setBounds(32524, 4, 84 + 21, frame++, tab);
        setBounds(32526, 44, 87 + 19, frame++, tab);
        setBounds(32528, 81, 85 + 20, frame++, tab);
        setBounds(32530, 117, 85 + 20, frame++, tab);
        setBounds(32532, 156, 87 + 18, frame++, tab);
        setBounds(32534, 5, 125 + 19, frame++, tab);
        setBounds(32536, 43, 124 + 19, frame++, tab);
        setBounds(32538, 83, 124 + 20, frame++, tab);
        setBounds(32540, 115, 125 + 21, frame++, tab);
        setBounds(32542, 154, 126 + 22, frame++, tab);
        setBounds(17229, 0, 25, frame++, tab);
        setBounds(17201, 0, 22, frame++, tab);
        setBounds(17201, 0, 237, frame++, tab);
        setBounds(17202, 2, 25, frame++, tab);
        setBounds(17203, 41, 25, frame++, tab);
        setBounds(17204, 76, 25, frame++, tab);
        setBounds(17205, 113, 25, frame++, tab);
        setBounds(17206, 150, 25, frame++, tab);
        setBounds(17207, 2, 65, frame++, tab);
        setBounds(17208, 41, 65, frame++, tab);
        setBounds(17209, 76, 65, frame++, tab);
        setBounds(17210, 113, 65, frame++, tab);
        setBounds(17211, 150, 65, frame++, tab);
        setBounds(17212, 2, 102, frame++, tab);
        setBounds(17213, 41, 102, frame++, tab);
        setBounds(17214, 76, 102, frame++, tab);
        setBounds(17215, 113, 102, frame++, tab);
        setBounds(17216, 150, 102, frame++, tab);
        setBounds(17217, 2, 141, frame++, tab);
        setBounds(17218, 41, 141, frame++, tab);
        setBounds(17219, 76, 141, frame++, tab);
        setBounds(17220, 113, 141, frame++, tab);
        setBounds(17221, 150, 141, frame++, tab);
        setBounds(17235, 5, 5, frame++, tab);
        setBounds(17231, 0, 237, frame++, tab);
        setBounds(17232, 0, 237, frame++, tab);
    }

    public static void addConfigButtonWSpriteLoader(int ID, int pID, int bID, int bID2, int width, int height, String tT, int configID, int aT, int configFrame) {
        RSInterface Tab = addTabInterface(ID);
        Tab.parentID = pID;
        Tab.id = ID;
        Tab.type = 5;
        Tab.atActionType = aT;
        Tab.contentType = 0;
        Tab.width = width;
        Tab.height = height;
        Tab.hoverType = -1;
        Tab.valueCompareType = new int[1];
        Tab.requiredValues = new int[1];
        Tab.valueCompareType[0] = 1;
        Tab.requiredValues[0] = configID;
        Tab.valueIndexArray = new int[1][3];
        Tab.valueIndexArray[0][0] = 5;
        Tab.valueIndexArray[0][1] = configFrame;
        Tab.valueIndexArray[0][2] = 0;
        Tab.disabledSprite = SpriteLoader.sprites[bID];
        Tab.enabledSprite = SpriteLoader.sprites[bID2];
        Tab.tooltip = tT;
    }

    private static void slayerBuyInterface() {
        RSInterface rsInterface = addTabInterface(36000);
        addSpriteLoader(36001, 743);

        addHoverButtonWSpriteLoader(36002, 744, 17, 17, "Close Window", 0, 36003, 1);
        addHoveredImageWSpriteLoader(36003, 745, 17, 17, 36004);

        //Tab Buttons

        addHoverButtonWSpriteLoader(36008, 747, 112, 23, "Experience", 0, 36009, 1);
        addHoveredImageWSpriteLoader(36009, 747, 112, 23, 36010);

        addHoverButtonWSpriteLoader(36005, 746, 112, 23, "Items", 0, 36006, 1);
        addHoveredImageWSpriteLoader(36006, 747, 112, 23, 36007);

        addHoverButtonWSpriteLoader(36014, 748, 72, 19, "Buy", 0, 36015, 1);
        addHoveredImageWSpriteLoader(36015, 749, 72, 19, 36016);

        addHoverButtonWSpriteLoader(36017, 748, 72, 19, "Buy", 0, 36018, 1);
        addHoveredImageWSpriteLoader(36018, 749, 72, 19, 36019);

        //Text
        addText(36029, "Slayer Shop", fonts, 2, 0xFFFFFF);
        addText(36030, "Current Points:   0", fonts, 2, 0xD8D8D8);
        addText(36031, "Slayer XP: 10,000", fonts, 1, 0xFFFFFF);
        addText(36032, "10 Points", fonts, 1, 0xD8D8D8);
        addText(36020, "Experience", fonts, 0, 0xffffff, false, true);
        addText(36021, "Items", fonts, 0, 0xffffff, false, true);
        addText(36033, "Permanent: Double Slayer XP", fonts, 1, 0xFFFFFF);
        addText(36034, "300 Points", fonts, 1, 0xD8D8D8);
				/*addText(36037, "Broad Bolts: 250", fonts, 1, 0xFFFFFF);
				addText(36038, "65 Points", fonts, 1, 0xD8D8D8);
				addText(36039, "Broad Arrows: 250", fonts, 1, 0xFFFFFF);
				addText(36040, "35 Points", fonts, 1, 0xD8D8D8);


				addText(36041, "Slayer Helmet", fonts, 1, 0xFFFFFF);
				addText(36042, "375 Points", fonts, 1, 0xD8D8D8);*/
        setChildren(19, rsInterface);
        rsInterface.child(0, 36001, 6, 7); //Background
        rsInterface.child(1, 36002, 483, 10); //Close Button
        rsInterface.child(2, 36003, 483, 10); // Close Button Hover
        rsInterface.child(3, 36005, 138, 41); // Learn Tab
        rsInterface.child(4, 36006, 138, 41); // Learn Tab Hover
        rsInterface.child(5, 36008, 20, 41); // Learn Tab
        rsInterface.child(6, 36009, 20, 41); // Learn Tab Hover
        //	rsInterface.child(5, 36008, 263, 41); // Assignment Tab
        //	rsInterface.child(6, 36009, 263, 41); // Assignment Tab Hover
        //	rsInterface.child(7, 36011, 384, 41); // Co-op Tab
        //	rsInterface.child(8, 36012, 384, 41); // Co-op Tab Hover
        rsInterface.child(7, 36014, 174, 107); // Slayer Buy
        rsInterface.child(8, 36015, 174, 107); // Slayer Buy Hover
        rsInterface.child(9, 36017, 174, 177); // Ring Buy
        rsInterface.child(10, 36018, 174, 177); // Ring Hover
        rsInterface.child(11, 36029, 215, 15); // Title
        rsInterface.child(12, 36030, 22, 283); // Points
        rsInterface.child(13, 36031, 74, 87); // Slayer XP
        rsInterface.child(14, 36032, 74, 102); // 400 Points
        rsInterface.child(15, 36033, 74, 157); //Slay Ring
        rsInterface.child(16, 36034, 74, 172); // 75 Points
        rsInterface.child(17, 36020, 48, 48);
        rsInterface.child(18, 36021, 180, 48);
    }

    private static void barrowsInterface() {
        RSInterface tab = addTabInterface(37200);
        addTransparentSpriteWSpriteLoader(37209, 969, 218);

        addText(37201, "Barrow Brothers:", fonts, 1, 0xff981f, true, true);
        addText(37202, "Dharoks", fonts, 0, 0x86B404, true, true);
        addText(37203, "Veracs", fonts, 0, 0x86B404, true, true);
        addText(37204, "Ahrims", fonts, 0, 0x86B404, true, true);
        addText(37205, "Torags", fonts, 0, 0x86B404, true, true);
        addText(37206, "Guthans", fonts, 0, 0x86B404, true, true);
        addText(37207, "Karils", fonts, 0, 0x86B404, true, true);
        addText(37208, "Killcount: 2", fonts, 2, 0xff981f, true, true);
        tab.totalChildren(9);
        tab.child(0, 37209, 369, 185);
        tab.child(1, 37202, 438, 206);
        tab.child(2, 37203, 438, 221);
        tab.child(3, 37204, 438, 236);
        tab.child(4, 37205, 438, 251);
        tab.child(5, 37206, 438, 266);
        tab.child(6, 37207, 438, 281);
        tab.child(7, 37208, 441, 294);
        tab.child(8, 37201, 438, 187);

    }

    private static void walkableTextInterface() {
        RSInterface tab = addTabInterface(37300);
        addText(37308, "Killcount: 2", fonts, 2, 0xff981f, true, true);
        tab.totalChildren(1);
        tab.child(0, 37308, 39, 319);
    }

    private static void dungeonInfo() {
        RSInterface tab = addTabInterface(37500);
        addText(37508, "Party deaths: 0", fonts, 2, 0x86B404, true, true);
        addText(37509, "Party kills: 0", fonts, 2, 0x86B404, true, true);
        tab.totalChildren(2);
        tab.child(0, 37508, 50, 39);
        tab.child(1, 37509, 41, 22);
    }

    public static void graveStoneTimerInterface() {
        RSInterface RSinterface = addTabInterface(37400);
        addSprite(37401, 0, "Interfaces/Grave/TIMER");
        addText(37402, "1:20", fonts, 1, 0xff9040, true, true);
        int last = 2;
        RSinterface.children = new int[last];
        RSinterface.childX = new int[last];
        RSinterface.childY = new int[last];
        setBounds(37401, 515 - 52 - 5, 2, 0, RSinterface);
        setBounds(37402, 515 - 52 + 27, 5, 1, RSinterface);
    }

    public static void addText(int id, String text, TextDrawingArea wid[], int idx, int color) {
        RSInterface rsinterface = addTabInterface(id);
        rsinterface.id = id;
        rsinterface.parentID = id;
        rsinterface.type = 4;
        rsinterface.atActionType = 0;
        rsinterface.width = 174;
        rsinterface.height = 11;
        rsinterface.contentType = 0;
        rsinterface.hoverType = -1;
        rsinterface.centerText = false;
        rsinterface.shadowed = true;
        rsinterface.textDrawingAreas = wid[idx];
        rsinterface.message = text;
        rsinterface.textColor(id, color);
    }

    public static void redoSpellBooks() {
        RSInterface newInterface = addTabInterface(11000);
        RSInterface spellButtons = interfaceCache[1151];
        newInterface.totalChildren(15);
        /**
         * Modern spellbook
         */
        spellButtons.scrollMax = 0;
        spellButtons.height = 260;
        spellButtons.width = 190;
        newInterface.child(0, 1151, 5, 34);
        interfaceCache[1164] = interfaceCache[1165];
        interfaceCache[1165] = interfaceCache[1166];
        interfaceCache[1166] = interfaceCache[1168];
        interfaceCache[1167] = interfaceCache[1169];
        interfaceCache[1168] = interfaceCache[1171];
        interfaceCache[1169] = interfaceCache[1172];
        interfaceCache[1170] = interfaceCache[1173];
        interfaceCache[1171] = interfaceCache[1175];
        interfaceCache[1172] = interfaceCache[1176];
        interfaceCache[1173] = interfaceCache[1539];
        interfaceCache[1174] = interfaceCache[1582];
        interfaceCache[1175] = interfaceCache[12037];
        interfaceCache[1176] = interfaceCache[1177];
        interfaceCache[1539] = interfaceCache[1178];
        interfaceCache[1582] = interfaceCache[1179];
        interfaceCache[12037] = interfaceCache[1180];
        interfaceCache[1540] = interfaceCache[1181];
        interfaceCache[1177] = interfaceCache[1182];
        interfaceCache[1178] = interfaceCache[15877];
        interfaceCache[1179] = interfaceCache[1190];
        interfaceCache[1180] = interfaceCache[1191];
        interfaceCache[1541] = interfaceCache[1192];
        interfaceCache[1181] = interfaceCache[1183];
        interfaceCache[1182] = interfaceCache[1184];
        interfaceCache[15877] = interfaceCache[1185];
        interfaceCache[1190] = interfaceCache[1186];
        interfaceCache[1191] = interfaceCache[1542];
        interfaceCache[1192] = interfaceCache[1187];
        interfaceCache[7455] = interfaceCache[1188];
        interfaceCache[1183] = interfaceCache[1543];
        interfaceCache[1184] = interfaceCache[12425];
        interfaceCache[18470] = interfaceCache[1189];
        interfaceCache[1185] = interfaceCache[1592];
        interfaceCache[1186] = interfaceCache[1562];
        interfaceCache[1542] = interfaceCache[1193];
        interfaceCache[1187] = interfaceCache[12435];
        interfaceCache[1188] = interfaceCache[12445];
        interfaceCache[1543] = interfaceCache[6003];
        interfaceCache[12425] = interfaceCache[12455];
        removeSomething(1189);
        removeSomething(1592);
        removeSomething(1562);
        removeSomething(1193);
        removeSomething(12435);
        removeSomething(12445);
        removeSomething(6003);
        removeSomething(12455);
        addHoverButtonWSpriteLoader(11001, 906, 18, 18, "Select", -1, 11002, 1);
        addTooltip(11002, "Home Teleport\nTeleport to set home location.");
        newInterface.child(1, 11001, 8, 8);
        newInterface.child(2, 11002, 10, 39);
        addHoverButtonWSpriteLoader(11004, 907, 18, 18, "Select", -1, 11005, 1);
        addTooltip(11005, "City Teleports\nOpen options of different \ncity teleports.");
        newInterface.child(3, 11004, 34, 8);
        newInterface.child(4, 11005, 30, 39);
        addHoverButtonWSpriteLoader(11008, 908, 18, 18, "Select", -1, 11009, 1);
        addTooltip(11009, "Training Teleport\nOpen options of different \ntraining teleports.");
        newInterface.child(5, 11008, 60, 8);
        newInterface.child(6, 11009, 40, 39);
        addHoverButtonWSpriteLoader(11011, 909, 18, 18, "Select", -1, 11012, 1);
        addTooltip(11012, "Dungeon Teleport\nOpen options of different\ndungeon teleports.");
        newInterface.child(7, 11011, 86, 8);
        newInterface.child(8, 11012, 23, 39);
        addHoverButtonWSpriteLoader(11014, 910, 18, 18, "Select", -1, 11015, 1);
        addTooltip(11015, "Boss Teleport\nOpen options of different\nboss teleports.");
        newInterface.child(9, 11014, 112, 8);
        newInterface.child(10, 11015, 23, 39);
        addHoverButtonWSpriteLoader(11017, 911, 18, 18, "Select", -1, 11018, 1);
        addTooltip(11018, "Minigame Teleport\nOpen options of different\nminigame teleports.");
        newInterface.child(11, 11017, 138, 8);
        newInterface.child(12, 11018, 34, 39);
        addHoverButtonWSpriteLoader(11020, 912, 18, 18, "Select", -1, 11021, 1);
        addTooltip(11021, "Raids Teleport\nOpen options of different\nRaids teleports.");
        newInterface.child(13, 11020, 164, 8);
        newInterface.child(14, 11021, 40, 39);
        /**
         * Ancient spellbook
         */
        newInterface = addTabInterface(11500);
        spellButtons = interfaceCache[12855];
        newInterface.totalChildren(15);
        spellButtons.scrollMax = 0;
        spellButtons.height = 260;
        spellButtons.width = 190;
        newInterface.child(0, 12855, 0, 40);
        interfaceCache[13035] = interfaceCache[12901];
        interfaceCache[12901] = interfaceCache[12861];
        interfaceCache[12861] = interfaceCache[12963];
        interfaceCache[13045] = interfaceCache[13011];
        interfaceCache[12963] = interfaceCache[12919];
        interfaceCache[13011] = interfaceCache[12881];
        interfaceCache[13053] = interfaceCache[12951];
        interfaceCache[12919] = interfaceCache[12999];
        interfaceCache[12881] = interfaceCache[12911];
        interfaceCache[13061] = interfaceCache[12871];
        interfaceCache[12951] = interfaceCache[12975];
        interfaceCache[12999] = interfaceCache[13023];
        interfaceCache[13069] = interfaceCache[12929];
        interfaceCache[12911] = interfaceCache[12891];
        removeSpell(interfaceCache[12871]);
        removeSpell(interfaceCache[13079]);
        removeSpell(interfaceCache[12975]);
        removeSpell(interfaceCache[13023]);
        removeSpell(interfaceCache[13087]);
        removeSpell(interfaceCache[12929]);
        removeSpell(interfaceCache[12891]);
        removeSpell(interfaceCache[13095]);
        /**
         * Add teleports
         */
        addHoverButtonWSpriteLoader(11001, 906, 18, 18, "Select", -1, 11002, 1);
        addTooltip(11002, "Home Teleport\nTeleport to set home location.");
        newInterface.child(1, 11001, 8, 16);
        newInterface.child(2, 11002, 10, 39);
        addHoverButtonWSpriteLoader(11004, 907, 18, 18, "Select", -1, 11005, 1);
        addTooltip(11005, "City Teleports\nOpen options of different \ncity teleports.");
        newInterface.child(3, 11004, 34, 16);
        newInterface.child(4, 11005, 30, 39);
        addHoverButtonWSpriteLoader(11008, 908, 18, 18, "Select", -1, 11009, 1);
        addTooltip(11009, "Training Teleport\nOpen options of different \ntraining teleports.");
        newInterface.child(5, 11008, 60, 16);
        newInterface.child(6, 11009, 40, 39);
        addHoverButtonWSpriteLoader(11011, 909, 18, 18, "Select", -1, 11012, 1);
        addTooltip(11012, "Dungeon Teleport\nOpen options of different\ndungeon teleports.");
        newInterface.child(7, 11011, 86, 16);
        newInterface.child(8, 11012, 23, 39);
        addHoverButtonWSpriteLoader(11014, 910, 18, 18, "Select", -1, 11015, 1);
        addTooltip(11015, "Boss Teleport\nOpen options of different\nboss teleports.");
        newInterface.child(9, 11014, 112, 16);
        newInterface.child(10, 11015, 23, 39);
        addHoverButtonWSpriteLoader(11017, 911, 18, 18, "Select", -1, 11018, 1);
        addTooltip(11018, "Minigame Teleport\nOpen options of different\nminigame teleports.");
        newInterface.child(11, 11017, 138, 16);
        newInterface.child(12, 11018, 34, 39);
        addHoverButtonWSpriteLoader(11020, 912, 18, 18, "Select", -1, 11021, 1);
        addTooltip(11021, "Raids Teleport\nOpen options of different\nRaids teleports.");
        newInterface.child(13, 11020, 164, 16);
        newInterface.child(14, 11021, 40, 39);

        /**
         * Lunar
         */
        lunarSpellbookInterface();
    }

    private static void removeSpell(RSInterface rsInterface) {
        try {
            RSInterface replacement = addInterface(rsInterface.id);
        } catch (Exception e) {
        }
    }

    public static void ExpRewardInterface() {
        RSInterface Interface = addTabInterface(38000);
        setChildren(37, Interface);
        addSpriteLoader(38001, 858);

        addHoverButtonWSpriteLoader(38002, 860, 21, 21, "Exit", 0, 38003, 1);
        addHoveredImageWSpriteLoader(38003, 861, 21, 21, 38004);

        addSpriteLoader(38005, 859);
        addText(38006, "Choose XP Type...", fonts, 1, 0xE3CCCF, true, true);
        addText(38090, "What sort of XP would you like?", fonts, 1, 0xE3CCCF, true, true);
        //Line 1

        addButtonWSpriteLoader(38007, 863, "Choose Attack", 46, 44);
        addButtonWSpriteLoader(38010, 863, "Choose Magic", 46, 44);
        addButtonWSpriteLoader(38013, 863, "Choose Mining", 46, 44);
        addButtonWSpriteLoader(38016, 863, "Choose Woodcutting", 46, 44);
        addButtonWSpriteLoader(38019, 863, "Choose Agility", 46, 44);
        addButtonWSpriteLoader(38022, 863, "Choose Fletching", 46, 44);
        addButtonWSpriteLoader(38025, 863, "Choose Thieving", 46, 44);
        addButtonWSpriteLoader(38028, 863, "Choose Strength", 46, 44);
        addButtonWSpriteLoader(38031, 863, "Choose Ranged", 46, 44);
        addButtonWSpriteLoader(38034, 863, "Choose Smithing", 46, 44);
        addButtonWSpriteLoader(38037, 863, "Choose Firemaking", 46, 44);
        addButtonWSpriteLoader(38040, 863, "Choose Herblore", 46, 44);
        addButtonWSpriteLoader(38043, 863, "Choose Slayer", 46, 44);
        addButtonWSpriteLoader(38046, 863, "Choose Skiller", 46, 44);
        addButtonWSpriteLoader(38049, 863, "Choose Defence", 46, 44);
        addButtonWSpriteLoader(38052, 863, "Choose Prayer", 46, 44);
        addButtonWSpriteLoader(38043, 863, "Choose Slayer", 46, 44);
        addButtonWSpriteLoader(38055, 863, "Choose Fishing", 46, 44);
        addButtonWSpriteLoader(38058, 863, "Choose Crafting", 46, 44);
        addButtonWSpriteLoader(38061, 863, "Choose Farming", 46, 44);
        addButtonWSpriteLoader(38064, 863, "Choose Hunter", 46, 44);
        addButtonWSpriteLoader(38067, 863, "Choose Summoning", 46, 44);
        addButtonWSpriteLoader(38070, 863, "Choose Constitution", 46, 44);
        addButtonWSpriteLoader(38073, 863, "Choose Dungeoneering", 46, 44);
        addButtonWSpriteLoader(38076, 863, "Choose Cooking", 46, 44);
        addButtonWSpriteLoader(38079, 863, "Choose Runecrafting", 46, 44);

        //Other Stuff

        addHoverButtonWSpriteLoader(38082, 864, 127, 21, "Cancel", -1, 38083, 1);
        addHoveredImageWSpriteLoader(38083, 865, 127, 21, 38084);

        addHoverButtonWSpriteLoader(38085, 866, 127, 21, "Confirm", -1, 38086, 1);
        addHoveredImageWSpriteLoader(38086, 867, 127, 21, 38087);

        addText(38088, "Confirm", fonts, 1, 0xE3CCCF, false, true);
        addText(38089, "Not right now", fonts, 1, 0xE3CCCF, false, true);

        setBounds(38001, 10, 14, 0, Interface);//background
        setBounds(38002, 470, 20, 1, Interface);//Close Button
        setBounds(38003, 470, 20, 2, Interface);//Close Button
        setBounds(38005, 181, 48, 3, Interface);
        setBounds(38006, 255, 52, 4, Interface);
        //Line 1
        setBounds(38007, 37, 80, 5, Interface);

        setBounds(38010, 102, 80, 6, Interface);
        setBounds(38013, 167, 80, 7, Interface);
        setBounds(38016, 232, 80, 8, Interface);
        setBounds(38019, 297, 80, 9, Interface);
        setBounds(38022, 362, 80, 10, Interface);
        setBounds(38025, 427, 80, 11, Interface);
        //Line 2
        setBounds(38028, 37, 138, 12, Interface);
        setBounds(38031, 102, 138, 13, Interface);
        setBounds(38034, 167, 138, 14, Interface);
        setBounds(38037, 232, 138, 15, Interface);
        setBounds(38040, 297, 138, 16, Interface);
        setBounds(38043, 362, 138, 17, Interface);
        setBounds(38046, 427, 138, 18, Interface);
        //Line 3
        setBounds(38049, 37, 196, 19, Interface);
        setBounds(38052, 102, 196, 20, Interface);
        setBounds(38055, 167, 196, 21, Interface);
        setBounds(38058, 232, 196, 22, Interface);
        setBounds(38061, 297, 196, 23, Interface);
        setBounds(38064, 362, 196, 24, Interface);
        setBounds(38067, 427, 196, 25, Interface);
        //Line 4
        setBounds(38070, 37, 254, 26, Interface);
        setBounds(38073, 102, 254, 27, Interface);
        setBounds(38076, 167, 254, 28, Interface);
        setBounds(38079, 232, 254, 29, Interface);
        //Other Stuff
        setBounds(38082, 322, 280, 30, Interface);
        setBounds(38083, 322, 280, 31, Interface);
        setBounds(38085, 322, 250, 32, Interface);
        setBounds(38086, 322, 250, 33, Interface);
        setBounds(38088, 360, 253, 34, Interface);
        setBounds(38089, 350, 283, 35, Interface);
        setBounds(38090, 256, 24, 36, Interface);
    }

    private static void colorSelectionInterface() {
        RSInterface tab = addTabInterface(39000);
        addSprite(39001, 0, "Interfaces/colorSelection/SPRITE");
        addHoverButton(39002, "Interfaces/colorSelection/Color", 0, 16, 16, "Choose Color", -1, 39003, 1);
        addHoveredButton(39003, "Interfaces/colorSelection/GoldBox", 0, 19, 20, 39004);
        addHoverButton(39005, "Interfaces/colorSelection/Color", 1, 16, 16, "Choose Color", -1, 39006, 1);
        addHoveredButton(39006, "Interfaces/colorSelection/GoldBox", 0, 19, 20, 39007);
        addHoverButton(39008, "Interfaces/colorSelection/Color", 2, 16, 16, "Choose Color", -1, 39009, 1);
        addHoveredButton(39009, "Interfaces/colorSelection/GoldBox", 0, 19, 20, 39010);
        addHoverButton(39011, "Interfaces/colorSelection/Color", 3, 16, 16, "Choose Color", -1, 39012, 1);
        addHoveredButton(39012, "Interfaces/colorSelection/GoldBox", 0, 19, 20, 39013);
        addHoverButton(39014, "Interfaces/colorSelection/Color", 4, 16, 16, "Choose Color", -1, 39015, 1);
        addHoveredButton(39015, "Interfaces/colorSelection/GoldBox", 0, 19, 20, 39016);
        addHoverButton(39017, "Interfaces/colorSelection/Color", 5, 16, 16, "Choose Color", -1, 39018, 1);
        addHoveredButton(39018, "Interfaces/colorSelection/GoldBox", 0, 19, 20, 39019);
        addHoverButton(39020, "Interfaces/colorSelection/Color", 6, 16, 16, "Choose Color", -1, 39021, 1);
        addHoveredButton(39021, "Interfaces/colorSelection/GoldBox", 0, 19, 20, 39022);
        addHoverButton(39023, "Interfaces/colorSelection/Color", 7, 16, 16, "Choose Color", -1, 39024, 1);
        addHoveredButton(39024, "Interfaces/colorSelection/GoldBox", 0, 19, 20, 39025);
        addHoverButton(39026, "Interfaces/colorSelection/Color", 8, 16, 16, "Choose Color", -1, 39027, 1);
        addHoveredButton(39027, "Interfaces/colorSelection/GoldBox", 0, 19, 20, 39028);
        addHoverButton(39029, "Interfaces/colorSelection/Color", 9, 16, 16, "Choose Color", -1, 39030, 1);
        addHoveredButton(39030, "Interfaces/colorSelection/GoldBox", 0, 19, 20, 39031);
        addHoverButton(39032, "Interfaces/colorSelection/Color", 10, 16, 16, "Choose Color", -1, 39033, 1);
        addHoveredButton(39033, "Interfaces/colorSelection/GoldBox", 0, 19, 20, 39034);
        addHoverButton(39035, "Interfaces/colorSelection/Color", 11, 16, 16, "Choose Color", -1, 39036, 1);
        addHoveredButton(39036, "Interfaces/colorSelection/GoldBox", 0, 19, 20, 39037);
        addHoverButton(39038, "Interfaces/colorSelection/Color", 12, 16, 16, "Choose Color", -1, 39039, 1);
        addHoveredButton(39039, "Interfaces/colorSelection/GoldBox", 0, 19, 20, 39040);
        addHoverButton(39041, "Interfaces/colorSelection/Color", 13, 16, 16, "Choose Color", -1, 39042, 1);
        addHoveredButton(39042, "Interfaces/colorSelection/GoldBox", 0, 19, 20, 39043);
        addHoverButton(39044, "Interfaces/colorSelection/Color", 14, 16, 16, "Choose Color", -1, 39045, 1);
        addHoveredButton(39045, "Interfaces/colorSelection/GoldBox", 0, 19, 20, 39046);
        addText(39047, "Clan Chat Color", fonts, 2, 0xFF9900, false, true);
        addText(39048, "Please select a color below.", fonts, 0, 0xFF9900, false, true);
        addText(39049, "All clan chat messages will be shown", fonts, 0, 0xFF9900, false, true);
        addText(39050, "in the color of your choice.", fonts, 0, 0xFF9900, false, true);
        //addToggleButton(39050, 0, 2, "Interfaces/colorSelection/CHECK", "Toggle-Split Private Chat", 1, 4, 500, 0);
        addCloseButton(39051, 39055, 39056);
        tab.totalChildren(35);
        tab.child(0, 39001, 0, 2);
        tab.child(1, 39002, 26, 119);
        tab.child(2, 39003, 25, 117);
        tab.child(3, 39005, 56, 119);
        tab.child(4, 39006, 55, 117);
        tab.child(5, 39008, 86, 119);
        tab.child(6, 39009, 85, 117);
        tab.child(7, 39011, 116, 119);
        tab.child(8, 39012, 115, 117);
        tab.child(9, 39014, 146, 119);
        tab.child(10, 39015, 145, 117);
        tab.child(11, 39017, 26, 149);
        tab.child(12, 39018, 25, 147);
        tab.child(13, 39020, 56, 149);
        tab.child(14, 39021, 55, 147);
        tab.child(15, 39023, 86, 149);
        tab.child(16, 39024, 85, 147);
        tab.child(17, 39026, 116, 149);
        tab.child(18, 39027, 115, 147);
        tab.child(19, 39029, 146, 149);
        tab.child(20, 39030, 145, 147);
        tab.child(21, 39032, 26, 179);
        tab.child(22, 39033, 25, 177);
        tab.child(23, 39035, 56, 179);
        tab.child(24, 39036, 55, 177);
        tab.child(25, 39038, 86, 179);
        tab.child(26, 39039, 85, 177);
        tab.child(27, 39041, 116, 179);
        tab.child(28, 39042, 115, 177);
        tab.child(29, 39051, 163, 7);
        tab.child(30, 39047, 30, 10);
        tab.child(31, 39048, 28, 58);
        tab.child(32, 39049, 5, 219);
        tab.child(33, 39050, 5, 230);
        tab.child(34, 39055, 163, 7);
    }

    public static void addToggleButton(int id, int bID, int bID2, String bName, String tT, int configID, int aT, int configFrame, int dummy) {
        RSInterface tab = addTabInterface(id);
        tab.parentID = id;
        tab.id = id;
        tab.type = 5;
        tab.atActionType = aT;
        tab.contentType = 0;// anInt214
        tab.hoverType = -1;// anInt230
        tab.valueCompareType = new int[1];
        tab.requiredValues = new int[1];
        tab.valueCompareType[0] = 1;
        tab.requiredValues[0] = configID;
        tab.valueIndexArray = new int[1][3];
        tab.valueIndexArray[0][0] = 5;
        tab.valueIndexArray[0][1] = configFrame;
        tab.valueIndexArray[0][2] = 0;
        tab.disabledSprite = imageLoader(bID, bName);
        tab.enabledSprite = imageLoader(bID2, bName);
        tab.width = tab.disabledSprite.myWidth;
        tab.height = tab.disabledSprite.myHeight;
        tab.tooltip = tT;
    }

    public static void addInAreaHover(int i, String imageName, int sId, int sId2, int w, int h, String text, int contentType, int actionType) {
        RSInterface tab = addTabInterface(i);
        tab.id = i;
        tab.parentID = i;
        tab.type = 5;
        tab.atActionType = actionType;
        tab.contentType = contentType;
        tab.hoverType = i;
        if (sId != -1) tab.disabledSprite = imageLoader(sId, imageName);
        tab.enabledSprite = imageLoader(sId2, imageName);
        tab.width = w;
        tab.height = h;
        tab.tooltip = text;
    }

    public static void addInAreaHoverSpriteLoader(int i, int sprite, int w, int h, String text, int contentType, int actionType) {
        RSInterface tab = addTabInterface(i);
        tab.id = i;
        tab.parentID = i;
        tab.type = 5;
        tab.atActionType = actionType;
        tab.contentType = contentType;
        tab.hoverType = i;
        tab.disabledSprite = SpriteLoader.sprites[sprite];
        tab.enabledSprite = SpriteLoader.sprites[sprite];
        tab.width = w;
        tab.height = h;
        tab.tooltip = text;
    }

    public static void addSummoningText(int i, String s, int k, boolean l, boolean m, int a, TextDrawingArea[] TDA, int j) {
        RSInterface RSInterface = addTabInterface(i);
        RSInterface.parentID = i;
        RSInterface.id = i;
        RSInterface.type = 4;
        RSInterface.atActionType = 0;
        RSInterface.width = 0;
        RSInterface.height = 0;
        RSInterface.contentType = 0;
        RSInterface.hoverType = a;
        RSInterface.centerText = l;
        //RSInterface.dis = m;
        RSInterface.textDrawingAreas = TDA[j];
        RSInterface.message = s;
        RSInterface.message = s;
        RSInterface.textColor(i, k);
        RSInterface.hidden = true;
        RSInterface.hoverType = -1;
    }

    /*
     * Summoning interfaces
     */
    private static void pouchCreation() {
        int totalScrolls = pouchItems.length;
        int xPadding = 53;
        int yPadding = 57;
        int xPos = 13;
        int yPos = 20;
        RSInterface rsinterface = addTabInterface(63471);
        setChildren(8, rsinterface);
        addCloseButton(63450, 63451, 63452);
        addButtonWSpriteLoader(63475, 891, "Transform Scrolls");
        addSpriteLoader(63474, 888);
        addSpriteLoader(63476, 889);
        addSpriteLoader(63473, 890);
        addSpriteLoader(63472, 887);
        RSInterface scroll = addTabInterface(63478);
        setChildren(4 * totalScrolls, scroll);
        scroll.hidden = false;
        int req[] = {1, 2, 3};
        for (int i = 0; i < totalScrolls; i++) {
            addInAreaHoverSpriteLoader(63479 + i * 8, 893, 48, 52, "nothing", -1, 0);
            addPouch(63480 + i * 8, req, 1, pouchItems[i], summoningLevelRequirements[i], pouchNames[i], fonts, i, 5);
            setBounds(63479 + i * 8, 36 + (i % 8) * xPadding, 0 + (i / 8) * yPadding, i, scroll);
            setBounds(63480 + i * 8, 43 + (i % 8) * xPadding, 1 + (i / 8) * yPadding, 156 + i, scroll);
        }
        for (int i = 0; i < shards.length; i++) {
            addSummoningText(72001 + i, "" + shards[i][0], 0xCCCBCB, false, true, 52, fonts, 0);
            setBounds(72001 + i, shards[i][1], shards[i][2], 78 + i, scroll);
        }
        for (int i = 0; i < totalScrolls; i++) {
            int drawX = 5 + (i % 8) * xPadding;
            if (drawX > 292) drawX -= 90;
            int drawY = 55 + (i / 8) * yPadding;
            if (drawY > 160) drawY -= 80;
            setBounds(63481 + i * 8, drawX, drawY, 234 + i, scroll);
        }
        scroll.parentID = 63478;
        scroll.id = 63478;
        scroll.atActionType = 0;
        scroll.contentType = 0;
        scroll.width = 474;
        scroll.height = 257;
        scroll.scrollMax = 570;
        setBounds(63472, xPos, yPos, 0, rsinterface);
        setBounds(63473, xPos + 9, yPos + 9, 1, rsinterface);
        setBounds(63474, xPos + 29, yPos + 10, 2, rsinterface);
        setBounds(63475, xPos + 79, yPos + 9, 3, rsinterface);
        setBounds(63476, xPos + 106, yPos + 10, 4, rsinterface);
        setBounds(63450, xPos + 460, yPos + 8, 5, rsinterface);
        setBounds(63451, xPos + 460, yPos + 8, 6, rsinterface);
        setBounds(63478, 0, yPos + 39, 7, rsinterface);
    }

    public static void collectionLog(TextDrawingArea[] tda) {
        //Config = 519
        RSInterface widget = addInterface(29000);//29000
        int childId = 29001;//29001

        addSprite(childId++, 0, "Interfaces/collectionlog/BACKGROUND");

        addText(childId++, "Collection Log", tda, 2, 0xFF9300, true, true);

        addHoverButton(childId++, "Interfaces/collectionlog/CLOSE", 0, 16, 16, "Close", -1, childId, 1);
        addHoveredButton(childId++, "Interfaces/collectionlog/CLOSE", 1, 16, 16, childId++);

        addConfigButton(childId++, 618, 0, 1, "Interfaces/collectionlog/TAB", 96, 20, "Select Bosses", 1, 1, 519);
        addText(childId++, "Bosses", tda, 1, 0xFF9300, true, true);

        addText(childId++, "Boss Name Here", tda, 2, 0xFF9300, false, true);
        addText(childId++, "Obtained: @red@10/11", tda, 0, 0xFF9300, false, true);
        addText(childId++, "Boss Name count: @whi@125", tda, 0, 0xFF9300, true, true);

        RSInterface tableView = addInterface(childId++);
//		System.out.println("childId: "+childId);
        int scrollChildId = childId;
        int scrollFrame = 0;
        tableView.width = 187;
        tableView.height = 246;
        tableView.scrollMax = 750;
        tableView.totalChildren(50 * 2);

        int scrollX = 0;
        int scrollY = 0;
        for (int i = 0; i < 50; i++) {
            if (i % 2 == 0) {//29012
                addConfigButton(scrollChildId, 29012, 0, 1, "Interfaces/collectionlog/CELL", 187, 15, "Select Boss", 1, 1, 520 + i);
            } else {
                addConfigButton(scrollChildId, 29012, 2, 1, "Interfaces/collectionlog/CELL", 187, 15, "Select Boss", 1, 1, 520 + i);
            }
            tableView.child(scrollFrame++, scrollChildId++, scrollX, scrollY);

            addText(scrollChildId, "Boss Name Here", tda, 1, 0xFF9300, false, true);
            tableView.child(scrollFrame++, scrollChildId++, scrollX + 4, scrollY);

            scrollY += 15;
        }

        widget.totalChildren(childId - 29001 - 1 + (8) + 1);
        childId = 29001;
        int frame = 0;

        widget.child(frame++, childId++, 9, 11);//Background
        widget.child(frame++, childId++, 257, 20);//Title

        widget.child(frame++, childId++, 482, 20);
        widget.child(frame++, childId++, 482, 20);
        childId++;

        widget.child(frame++, childId++, 19, 47);
        widget.child(frame++, childId++, 60, 50);

        widget.child(frame++, childId++, 230, 70);
        widget.child(frame++, childId++, 230, 94);
        widget.child(frame++, childId++, 433, 94);

        widget.child(frame++, childId++, 20, 68);

        //Had to add these on
        int nextChildId = scrollChildId;
        addConfigButton(nextChildId, 29001, 0, 1, "Interfaces/collectionlog/TAB", 96, 20, "Select Wilderness", 1, 1, 571);
        widget.child(frame++, nextChildId++, 19 + 96, 47);
        addText(nextChildId, "Wilderness", tda, 1, 0xFF9300, true, true);
        widget.child(frame++, nextChildId++, 60 + 96, 50);

        addConfigButton(nextChildId, 29001, 0, 1, "Interfaces/collectionlog/TAB", 96, 20, "Select Raids", 1, 1, 572);
        widget.child(frame++, nextChildId++, 19 + 96 + 96, 47);
        addText(nextChildId, "Raids", tda, 1, 0xFF9300, true, true);
        widget.child(frame++, nextChildId++, 60 + 96 + 96, 50);

        addConfigButton(nextChildId, 29001, 0, 1, "Interfaces/collectionlog/TAB", 96, 20, "Select Others", 1, 1, 573);
        widget.child(frame++, nextChildId++, 19 + 96 + 96 + 96, 47);
        addText(nextChildId, "Others", tda, 1, 0xFF9300, true, true);
        widget.child(frame++, nextChildId++, 60 + 96 + 96 + 96, 50);

        addConfigButton(nextChildId, 29001, 0, 1, "Interfaces/collectionlog/TAB", 96, 20, "Select Upgrades", 1, 1, 574);
        widget.child(frame++, nextChildId++, 19 + 96 + 96 + 96 + 96, 47);
        addText(nextChildId, "Upgrades", tda, 1, 0xFF9300, true, true);
        widget.child(frame++, nextChildId++, 60 + 96 + 96 + 96 + 96, 50);

        int itemScrollId = nextChildId;
        RSInterface scroll = addInterface(itemScrollId);
        widget.child(frame++, itemScrollId++, 232, 114);
        collectionLogItems = itemScrollId;
//		System.out.println("itemScrollId="+itemScrollId+" frame="+frame);
        int itemScrollFrame = 0;
        scroll.width = 250;
        scroll.height = 200;
        scroll.scrollMax = 2000;
        scroll.totalChildren(1);

        addToItemGroup(itemScrollId, 6, 33, 9, 6, false, null, null, null);
        scroll.child(itemScrollFrame++, itemScrollId++, 0, 0);
    }

    public static void marketPlace(TextDrawingArea[] tda) {
        int interId = 56000;
        RSInterface store = addInterface(interId);
        interId++;
        int buttonIds = 0;

        addSprite(interId++, 0, "Interfaces/RareShop/SHOPBG");
        addText(interId++, "PaeScape MarketPlace I", tda, 2, 0xff9040, true, true);

        addHoverButton(interId++, "Interfaces/RareShop/CLOSE", 0, 16, 16, "Close", -1, interId, 1);
        addHoveredButton(interId++, "Interfaces/RareShop/CLOSE", 1, 16, 16, interId++);
        buttonIds++;
        addHoverButton(interId++, "Interfaces/RareShop/TAB", 0, 96, 20, "Shop #" + buttonIds, -1, interId, 1);
        addHoveredButton(interId++, "Interfaces/RareShop/TAB", 1, 96, 20, interId++);
        addText(interId++, "Low", tda, 1, 0xFF9300, false, true);
//        System.out.println("shop title id=" + interId);
        buttonIds++;
        addHoverButton(interId++, "Interfaces/RareShop/TAB", 0, 96, 20, "Shop #" + buttonIds, -1, interId, 1);
        addHoveredButton(interId++, "Interfaces/RareShop/TAB", 1, 96, 20, interId++);
        addText(interId++, "Medium", tda, 1, 0xFF9300, false, true);
        buttonIds++;
        addHoverButton(interId++, "Interfaces/RareShop/TAB", 0, 96, 20, "Shop #" + buttonIds, -1, interId, 1);
        addHoveredButton(interId++, "Interfaces/RareShop/TAB", 1, 96, 20, interId++);
        addText(interId++, "High", tda, 1, 0xFF9300, false, true);
        buttonIds++;
        addHoverButton(interId++, "Interfaces/RareShop/TAB", 0, 96, 20, "Shop #" + buttonIds, -1, interId, 1);
        addHoveredButton(interId++, "Interfaces/RareShop/TAB", 1, 96, 20, interId++);
        addText(interId++, "Legendary", tda, 1, 0xFF9300, false, true);
        buttonIds++;
        addHoverButton(interId++, "Interfaces/RareShop/TAB", 0, 96, 20, "Shop #" + buttonIds, -1, interId, 1);
        addHoveredButton(interId++, "Interfaces/RareShop/TAB", 1, 96, 20, interId++);
        addText(interId++, "Master", tda, 1, 0xFF9300, false, true);
        buttonIds++;
//        System.out.println("marketPlace=" + interId);

        store.totalChildren(20);
        interId = interId - store.children.length - buttonIds;
//        System.out.println("marketPlace=" + interId);
        int frame = 0;
        interId++;
        store.child(frame++, interId++, 7, 15);//bg
        store.child(frame++, interId++, 250, 25);//title
        store.child(frame++, interId++, 465, 25);//close
        store.child(frame++, interId++, 465, 25);//close hover
        interId++;

        store.child(frame++, interId++, 14, 50);//tab1
        store.child(frame++, interId++, 14, 50);//tab1 hover
        interId++;
        store.child(frame++, interId++, 30, 52);//tab1 name

        store.child(frame++, interId++, 11 + 96, 50);//tab2
        store.child(frame++, interId++, 11 + 96, 50);//tab2 hover
        interId++;
        store.child(frame++, interId++, 30 + 96, 52);//tab2 name

        store.child(frame++, interId++, 9 + 96 + 96, 50);//tab3
        store.child(frame++, interId++, 9 + 96 + 96, 50);//tab3 hover
        interId++;
        store.child(frame++, interId++, 30 + 96 + 96, 52);//tab3 name

        store.child(frame++, interId++, 8 + 96 + 96 + 96, 50);//tab4
        store.child(frame++, interId++, 8 + 96 + 96 + 96, 50);//tab4 hover
        interId++;
        store.child(frame++, interId++, 30 + 96 + 96 + 96, 52);//tab4 name

        store.child(frame++, interId++, 7 + 96 + 96 + 96 + 96, 50);//tab5
        store.child(frame++, interId++, 7 + 96 + 96 + 96 + 96, 50);//tab5 hover
        interId++;
        store.child(frame++, interId++, 30 + 96 + 96 + 96 + 96, 52);//tab5 name

//        System.out.println("marketPlace=" + interId);

//
        RSInterface scroll = addInterface(interId);
        store.child(frame++, interId++, 20, 72);//scroll/items inter
//        System.out.println("itemScrollId=" + interId + " frame=" + frame);
        marketPlaceShop = interId;
        scroll.width = 450;
        scroll.height = 241;
        scroll.scrollMax = 2000;
        scroll.totalChildren(1);
        addToItemGroup(interId, 11, 33, 9, 6, true, new String[]{"Value", "Buy 1", "Buy 5", "Buy 10", null});
        scroll.child(0, interId++, 0, 0);

        RSInterface inv = addTabInterface(interId);
//        System.out.println("inv=" + interId);
        interId++;
        addToItemGroup(interId, 4, 7, 11, 4, true, new String[]{"Value", "Sell 1", "Sell 5", "Sell 10", "Sell All"});
        marketPlaceInv = interId;
        inv.totalChildren(1);
        inv.child(0, interId++, 15, 8);
    }

    public static void DeathsCoffers(TextDrawingArea[] tda) {
        int interId = 56090;
        RSInterface store = addInterface(interId);
        interId++;
        int buttonIds = 0;

        addSprite(interId++, 0, "Interfaces/RareShop/SHOPBG");
        addText(interId++, "Deaths Coffers", tda, 2, 0xff9040, true, true);

        addHoverButton(interId++, "Interfaces/RareShop/CLOSE", 0, 16, 16, "Close", -1, interId, 1);
        addHoveredButton(interId++, "Interfaces/RareShop/CLOSE", 1, 16, 16, interId++);
        buttonIds++;
        addHoverButton(interId++, "Interfaces/RareShop/TAB", 0, 96, 20, "Shop #" + buttonIds, -1, interId, 1);
        addHoveredButton(interId++, "Interfaces/RareShop/TAB", 1, 96, 20, interId++);
        addText(interId++, "Special", tda, 1, 0xFF9300, false, true);
        buttonIds++;
        addHoverButton(interId++, "Interfaces/RareShop/TAB", 0, 96, 20, "Shop #" + buttonIds, -1, interId, 1);
        addHoveredButton(interId++, "Interfaces/RareShop/TAB", 1, 96, 20, interId++);
        addText(interId++, "PvP", tda, 1, 0xFF9300, false, true);
        buttonIds++;
        addHoverButton(interId++, "Interfaces/RareShop/TAB", 0, 96, 20, "Shop #" + buttonIds, -1, interId, 1);
        addHoveredButton(interId++, "Interfaces/RareShop/TAB", 1, 96, 20, interId++);
        addText(interId++, "Skilling", tda, 1, 0xFF9300, false, true);
        buttonIds++;
        addHoverButton(interId++, "Interfaces/RareShop/TAB", 0, 96, 20, "Shop #" + buttonIds, -1, interId, 1);
        addHoveredButton(interId++, "Interfaces/RareShop/TAB", 1, 96, 20, interId++);
        addText(interId++, "Pets 1", tda, 1, 0xFF9300, false, true);
        buttonIds++;
        addHoverButton(interId++, "Interfaces/RareShop/TAB", 0, 96, 20, "Shop #" + buttonIds, -1, interId, 1);
        addHoveredButton(interId++, "Interfaces/RareShop/TAB", 1, 96, 20, interId++);
        addText(interId++, "Pets 2", tda, 1, 0xFF9300, false, true);
        buttonIds++;
//        System.out.println("DeathsCoffers=" + interId);

        store.totalChildren(20);
        interId = interId - store.children.length - buttonIds;
//        System.out.println("DeathsCoffers=" + interId);
        int frame = 0;
        interId++;
        store.child(frame++, interId++, 7, 15);//bg
        store.child(frame++, interId++, 250, 25);//title
        store.child(frame++, interId++, 465, 25);//close
        store.child(frame++, interId++, 465, 25);//close hover
        interId++;

        store.child(frame++, interId++, 14, 50);//tab1
        store.child(frame++, interId++, 14, 50);//tab1 hover
        interId++;
        store.child(frame++, interId++, 30, 52);//tab1 name

        store.child(frame++, interId++, 11 + 96, 50);//tab2
        store.child(frame++, interId++, 11 + 96, 50);//tab2 hover
        interId++;
        store.child(frame++, interId++, 30 + 96, 52);//tab2 name

        store.child(frame++, interId++, 9 + 96 + 96, 50);//tab3
        store.child(frame++, interId++, 9 + 96 + 96, 50);//tab3 hover
        interId++;
        store.child(frame++, interId++, 30 + 96 + 96, 52);//tab3 name

        store.child(frame++, interId++, 8 + 96 + 96 + 96, 50);//tab4
        store.child(frame++, interId++, 8 + 96 + 96 + 96, 50);//tab4 hover
        interId++;
        store.child(frame++, interId++, 30 + 96 + 96 + 96, 52);//tab4 name

        store.child(frame++, interId++, 7 + 96 + 96 + 96 + 96, 50);//tab5
        store.child(frame++, interId++, 7 + 96 + 96 + 96 + 96, 50);//tab5 hover
        interId++;
        store.child(frame++, interId++, 30 + 96 + 96 + 96 + 96, 52);//tab5 name

//        System.out.println("DeathsCoffers=" + interId);

//
        RSInterface scroll = addInterface(interId);
        store.child(frame++, interId++, 20, 72);//scroll/items inter
//        System.out.println("itemScrollId=" + interId + " frame=" + frame);
        DeathsCoffersShop = interId;
        scroll.width = 450;
        scroll.height = 241;
        scroll.scrollMax = 2000;
        scroll.totalChildren(1);
        addToItemGroup(interId, 11, 33, 9, 6, true, new String[]{"Value", "Buy 1", "Buy 5", "Buy 10", null});
        scroll.child(0, interId++, 0, 0);

        RSInterface inv = addTabInterface(interId);
//        System.out.println("inv=" + interId);
        interId++;
        addToItemGroup(interId, 4, 7, 11, 4, true, new String[]{"Value", "Sell 1", "Sell 5", "Sell 10", "Sell All"});
        marketPlaceInv = interId;
        inv.totalChildren(1);
        inv.child(0, interId++, 15, 8);
    }

    public void swapInventoryItems(int i, int j) {
        int k = inv[i];
        inv[i] = inv[j];

        inv[j] = k;
        k = invStackSizes[i];
        invStackSizes[i] = invStackSizes[j];
        invStackSizes[j] = k;
    }

    public Sprite loadSprite(String s, int i) {
        Sprite sprite;
        try {
            sprite = new Sprite(s + " " + i);
            if (sprite != null) {
                return sprite;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
        return null;
    }

    public RSInterface totalChildrenReturn(int total) {
        this.children = new int[total];
        this.childX = new int[total];
        this.childY = new int[total];
        return this;
    }

    public RSInterface childReturn(int index, int id, int x, int y) {
        this.children[index] = id;
        this.childX[index] = x;
        this.childY[index] = y;
        return this;
    }

    public void children(int total) {
        children = new int[total];
        childX = new int[total];
        childY = new int[total];
    }

    public void drawBlackBox(int xPos, int yPos) {
        DrawingArea.fillRectangle(0x000000, yPos - 2, 178, 72, 220, xPos - 2);// drawPixelsWithOpacity
        DrawingArea.fillPixels(xPos - 1, 177, 71, 0x2E2B23, yPos - 1);// drawUnfilledPixels/method335
        DrawingArea.fillPixels(xPos - 2, 177, 71, 0x726451, yPos - 2);// drawUnfilledPixels/method335
    }

    public void totalChildren(int id, int x, int y) {
        children = new int[id];
        childX = new int[x];
        childY = new int[y];
    }

    public void specialBar(int id) {
        /*
         * addActionButton(ID, SpriteOFF, SpriteON, Width, Height,
         * "SpriteText");
         */
        addActionButton(id - 12, 316, -1, 150, 26, "Use @gre@Special Attack", 0);
        /* removeSomething(ID); */
        for (int i = id - 11; i < id; i++)
            removeSomething(i);

        RSInterface rsi = interfaceCache[id - 12];
        rsi.width = 150;
        rsi.height = 26;

        rsi = interfaceCache[id];
        rsi.width = 150;
        rsi.height = 26;

        rsi.child(0, id - 12, 0, 0);

        rsi.child(12, id + 1, 3, 7);

        rsi.child(23, id + 12, 16, 8);

        for (int i = 13; i < 23; i++) {
            rsi.childY[i] -= 1;
        }

        rsi = interfaceCache[id + 1];
        rsi.type = 5;
        rsi.disabledSpriteId = 301;

        for (int i = id + 2; i < id + 12; i++) {
            rsi = interfaceCache[i];
            rsi.type = 5;
        }

        sprite1(id + 2, 302, 0);
        sprite1(id + 3, 303, 0);
        sprite1(id + 4, 304, 0);
        sprite1(id + 5, 305, 0);
        sprite1(id + 6, 306, 0);
        sprite1(id + 7, 307, 0);
        sprite1(id + 8, 308, 0);
        sprite1(id + 9, 309, 0);
        sprite1(id + 10, 310, 0);
        sprite1(id + 11, 311, 0);
    }

    public void child(int idx, int interID, int x, int y) {
        if (children == null || childX == null || childY == null) {
            children = new int[10];
            childX = new int[10];
            childY = new int[10];
        } else if (idx >= children.length || idx >= childX.length || idx >= childY.length) {
            int[] newChildren = new int[children.length * 2];
            System.arraycopy(children, 0, newChildren, 0, children.length);
            children = newChildren;

            int[] newChildX = new int[childX.length * 2];
            System.arraycopy(childX, 0, newChildX, 0, childX.length);
            childX = newChildX;

            int[] newChildY = new int[childY.length * 2];
            System.arraycopy(childY, 0, newChildY, 0, childY.length);
            childY = newChildY;
        }
        children[idx] = interID;
        childX[idx] = x;
        childY[idx] = y;
    }

    public int childDynamic(int interID, int x, int y) {
        if (children == null || childX == null || childY == null) {
            children = new int[10];
            childX = new int[10];
            childY = new int[10];
        }

        // get last unused id
        int lastUnusedChildId = Arrays.stream(children).boxed().collect(Collectors.toList()).indexOf(0);
        // if last used id is to the length of the arrays minus one, grow array
        // this will grow the array twice its length
        if (lastUnusedChildId == children.length - 1) {
            int[] newChildren = new int[children.length * 2];
            System.arraycopy(children, 0, newChildren, 0, children.length);
            children = newChildren;

            int[] newChildX = new int[childX.length * 2];
            System.arraycopy(childX, 0, newChildX, 0, childX.length);
            childX = newChildX;

            int[] newChildY = new int[childY.length * 2];
            System.arraycopy(childY, 0, newChildY, 0, childY.length);
            childY = newChildY;
        }

        children[lastUnusedChildId] = interID;
        childX[lastUnusedChildId] = x;
        childY[lastUnusedChildId] = y;

        return lastUnusedChildId;
    }

    public void trimChildren() {
        List<Integer> childrenList = Arrays.stream(children).boxed().collect(Collectors.toList());
        int trimLength = childrenList.indexOf(0);
        children = Arrays.copyOf(children, trimLength);

        if (trimLength != childrenList.indexOf(0)) {
            System.out.println("Interface children inconsistency");
        }

        if (Arrays.stream(childX).boxed().collect(Collectors.toList()).indexOf(0) != trimLength) {
            System.out.println("Trouble in paradise X");
        }
        childX = Arrays.copyOf(childX, trimLength);

        if (Arrays.stream(childY).boxed().collect(Collectors.toList()).indexOf(0) != trimLength) {
            System.out.println("Trouble in paradise Y");
        }

        childY = Arrays.copyOf(childY, trimLength);
    }

    public void totalChildren(int t) {
        children = new int[t];
        childX = new int[t];
        childY = new int[t];
    }

    private Model getMediaModel(int drawType, int mediaId) {
        Model model = (Model) modelCache.get((drawType << 16) + mediaId);
        if (model != null) return model;
        if (drawType == DRAW_REGULAR_MODEL) model = Model.fetchModel(mediaId, ItemDef.forID(mediaId).dataType);
        if (drawType == DRAW_NPC_MODEL) model = NPCDef.forID(mediaId).getHeadModel();
        if (drawType == DRAW_PLAYER_MODEL) model = Client.myPlayer.getPlayerModel();
        if (drawType == DRAW_ITEM_MODEL) model = ItemDef.forID(mediaId).getItemModel(50);
        if (drawType == 5) model = null;
        if (drawType == 10) {
            Player p = new Player();
            p.visible = true;
            p.equipment[0] = mediaId + 256;
            if (p.myGender == 0) p.equipment[1] = plrJaw + 256;
            p.myGender = gender;
            model = p.getPlayerModel();
        }
        if (drawType == 11) {
            Player p = new Player();
            p.visible = true;
            p.equipment[0] = mediaId + 512;
            if (p.myGender == 0) p.equipment[1] = plrJaw + 256;
            p.myGender = gender;
            model = p.getPlayerModel();
        }
        if (model != null) modelCache.put(model, (drawType << 16) + mediaId);
        return model;
    }

    public Model getAnimatedModel(int firstFrame, int secondFrame, boolean selected) {
        Model model;
        if (selected) model = getMediaModel(enabledMediaType, enabledMediaID);
        else model = getMediaModel(mediaType, mediaID);
        if (model == null) return null;
        if (secondFrame == -1 && firstFrame == -1 && model.face_color == null) return model;
        Model model_1 = new Model(true, FrameReader.isNullFrame(secondFrame) & FrameReader.isNullFrame(firstFrame), false, model);
        if (secondFrame != -1 || firstFrame != -1) model_1.createBones();
        if (secondFrame != -1) model_1.applyTransform(secondFrame, DataType.REGULAR);
        if (firstFrame != -1) model_1.applyTransform(firstFrame, DataType.REGULAR);
        model_1.light(64, 768, -50, -10, -50, true);
        return model_1;
    }

    public Model getAnimatedModel2(int firstFrame, int secondFrame, boolean selected) {
        Model model;
        if (selected) model = getMediaModel(enabledMediaType, enabledMediaID);
        else model = getMediaModel(mediaType, mediaID);
        if (model == null) return null;
        if (secondFrame == -1 && firstFrame == -1 && model.face_color == null) return model;
        Model model_1 = new Model(true, FrameReader.isNullFrame(secondFrame) & FrameReader.isNullFrame(firstFrame), false, model);

        if (secondFrame != -1 || firstFrame != -1) model_1.createBones();
        if (secondFrame != -1) model_1.applyTransform(secondFrame, DataType.REGULAR);
        if (firstFrame != -1) model_1.applyTransform(firstFrame, DataType.REGULAR);
        //model_1.light(64, 768, -50, -10, -50, true);
        model_1.light(64, 768, 255, 250, 10, true);
        //System.out.println(Arrays.toString(model_1.face_color));
        return model_1;
    }

    public boolean isHidden() {
        return hidden && type == 0;
    }

    public boolean isHideComponent() {
        return hidden && type != 0;
    }

    public enum SummonData {
        SPIRIT_WOLF(1, new int[]{GOLD_CHARM, 2859}, 7), SPIRIT_DREADFOWL(2, new int[]{GOLD_CHARM, 2138}, 8), SPIRIT_SPIDER(3, new int[]{GOLD_CHARM, 6291}, 8), THORNY_SNAIL(4, new int[]{GOLD_CHARM, 3363}, 9), GRANITE_CRAB(5, new int[]{GOLD_CHARM, 440}, 7), SPIRIT_MOSQUITO(6, new int[]{GOLD_CHARM, 6319}, 1), DESERT_WYRM(7, new int[]{GREEN_CHARM, 1783}, 45), SPIRIT_SCORPION(8, new int[]{CRIMSON_CHARM, 3095}, 57), SPIRIT_TZ_KIH(9, new int[]{CRIMSON_CHARM, OBSIDIAN_CHARM}, 64), ALBINO_RAT(10, new int[]{BLUE_CHARM, 2134}, 75), SPIRIT_KALPHITE(11, new int[]{BLUE_CHARM, 3138}, 51), COMPOST_MOUND(12, new int[]{GREEN_CHARM, 6032}, 47), GIANT_CHINCHOMPA(13, new int[]{BLUE_CHARM, 9976}, 84), VAMPIRE_BAT(14, new int[]{CRIMSON_CHARM, 3325}, 81), HONEY_BADGER(15, 92040, 12065, 12433, new int[]{CRIMSON_CHARM, 12156}, 84, 32, 141), BEAVER(16, 92048, 12021, 12429, new int[]{GREEN_CHARM, 1519}, 72, 33, 58), VOID_RAVAGER(17, 92056, 12818, 12443, new int[]{GREEN_CHARM, RAVAGER_CHARM}, 74, 34, 60), VOID_SHIFTER(18, 92080, 12814, 12443, new int[]{BLUE_CHARM, SHIFTER_CHARM}, 74, 34, 60), VOID_SPINNER(19, 92064, 12780, 12443, new int[]{BLUE_CHARM, SPINNER_CHARM}, 74, 34, 60), VOID_TORCHER(20, 92072, 12798, 12443, new int[]{BLUE_CHARM, TORCHER_CHARM}, 74, 34, 60), BRONZE_MINOTAUR(21, 92088, 12073, 12461, new int[]{BLUE_CHARM, 2349}, 102, 36, 317), BULL_ANT(22, 92096, 12087, 12431, new int[]{GOLD_CHARM, 6010}, 11, 40, 53), MACAW(23, 92104, 12071, 12422, new int[]{GREEN_CHARM, 249}, 78, 41, 72), EVIL_TURNIP(24, 92112, 12051, 12442, new int[]{CRIMSON_CHARM, 12153}, 104, 42, 185), SPIRIT_COCKATRICE(25, 92120, 12095, 12458, new int[]{GREEN_CHARM, 12109}, 88, 43, (int) 75.2), SPIRIT_GUTHATRICE(26, 92128, 12097, 12458, new int[]{GREEN_CHARM, 12111}, 88, 43, (int) 75.2), SPIRIT_SARATRICE(27, 92136, 12099, 12458, new int[]{GREEN_CHARM, 12113}, 88, 43, (int) 75.2), SPIRIT_ZAMATRICE(28, 92144, 12101, 12458, new int[]{GREEN_CHARM, 12115}, 88, 43, (int) 75.2), SPIRIT_PENGATRICE(29, 92152, 12103, 12458, new int[]{GREEN_CHARM, 12117}, 88, 43, (int) 75.2), SPIRIT_CORAXATRICE(30, 92160, 12105, 12458, new int[]{GREEN_CHARM, 12119}, 88, 43, (int) 75.2), SPIRIT_VULATRICE(31, 92168, 12107, 12458, new int[]{GREEN_CHARM, 12121}, 88, 43, (int) 75.2), IRON_MINOTAUR(32, 92176, 12075, 12462, new int[]{BLUE_CHARM, 2351}, 125, 46, 405), PYRELORD(33, 92184, 12816, 12829, new int[]{CRIMSON_CHARM, 590}, 111, 46, 202), MAGPIE(34, 92192, 12041, 12426, new int[]{GREEN_CHARM, 1635}, 88, 47, 83), BLOATED_LEECH(35, 92200, 12061, 12444, new int[]{CRIMSON_CHARM, 2132}, 117, 49, 215), SPIRIT_TERRORBIRD(36, 92208, 12007, 12441, new int[]{GOLD_CHARM, 9978}, 12, 52, 68), ABYSSAL_PARASITE(37, 92216, 12035, 12454, new int[]{GREEN_CHARM, ABYSSAL_CHARM}, 106, 54, 95), SPIRIT_JELLY(38, 92224, 12027, 12453, new int[]{BLUE_CHARM, 1937}, 151, 55, 484), IBIS(39, 92240, 12531, 12424, new int[]{GREEN_CHARM, 311}, 109, 56, 99), STEEL_MINOTAUR(40, 92232, 12077, 12463, new int[]{BLUE_CHARM, 2353}, 141, 56, 493), SPIRIT_GRAAHK(41, 92248, 12810, 12835, new int[]{BLUE_CHARM, 10099}, 154, 57, 502), SPIRIT_KYATT(42, 93000, 12812, 12836, new int[]{BLUE_CHARM, 10103}, 153, 57, 502), SPIRIT_LARUPIA(43, 93008, 12784, 12840, new int[]{BLUE_CHARM, 10095}, 155, 57, 502), KHARAMTHULHU_OVERLORD(44, 93016, 12023, 12455, new int[]{BLUE_CHARM, 6667}, 144, 58, 510), SMOKE_DEVIL(45, 93024, 12085, 12468, new int[]{CRIMSON_CHARM, 9736}, 141, 61, 268), ABYSSAL_LURKER(46, 93032, 12037, 12427, new int[]{GREEN_CHARM, ABYSSAL_CHARM}, 119, 62, 110), SPIRIT_COBRA(47, 93040, 12015, 12436, new int[]{CRIMSON_CHARM, 6287}, 116, 63, 269), STRANGER_PLANT(48, 93048, 12045, 12467, new int[]{CRIMSON_CHARM, 8431}, 128, 64, 282), MITHRIL_MINOTAUR(49, 93056, 12079, 12464, new int[]{BLUE_CHARM, 2359}, 152, 66, 581), BARKER_TOAD(50, 93064, 12163, 12452, new int[]{GOLD_CHARM, 2150}, 11, 66, 87), WAR_TORTOISE(51, 93072, 23031, 12439, new int[]{GOLD_CHARM, 7939}, 1, 67, 59), BUNYIP(52, 93080, 12029, 12438, new int[]{GREEN_CHARM, 383}, 110, 68, 120), FRUIT_BAT(53, 93088, 12033, 12423, new int[]{GREEN_CHARM, 1963}, 130, 69, 121), RAVENOUS_LOCUST(54, 93096, 12820, 12830, new int[]{CRIMSON_CHARM, 1933}, 79, 70, 132), ARCTIC_BEAR(55, 93104, 12057, 12451, new int[]{GOLD_CHARM, 10117}, 14, 71, 93), PHOENIX(56, 93112, 14623, 14622, new int[]{CRIMSON_CHARM, 14616}, 165, 72, 301), OBSIDIAN_GOLEM(57, 93120, 12792, 12826, new int[]{BLUE_CHARM, OBSIDIAN_CHARM}, 195, 73, 642), GRANITE_LOBSTER(58, 93128, 12069, 12449, new int[]{CRIMSON_CHARM, 6979}, 166, 74, 326), PRAYING_MANTIS(59, 93136, 12011, 12450, new int[]{CRIMSON_CHARM, 2460}, 168, 75, 330), FORGE_REGENT(60, 93152, 12782, 12841, new int[]{GREEN_CHARM, 10020}, 141, 76, 134), ADAMANT_MINOTAUR(61, 93144, 12081, 12465, new int[]{BLUE_CHARM, 2361}, 144, 76, 669), TALON_BEAST(62, 93160, 12794, 12831, new int[]{CRIMSON_CHARM, TALON_BEAST_CHARM}, 174, 77, 1015), GIANT_ENT(63, 93168, 12013, 12457, new int[]{GREEN_CHARM, 5933}, 124, 78, 137), FIRE_TITAN(64, 93176, 12802, 12824, new int[]{BLUE_CHARM, 1442}, 198, 79, 695), MOSS_TITAN(65, 93184, 12804, 12824, new int[]{BLUE_CHARM, 1440}, 202, 79, 695), ICE_TITAN(66, 93192, 12806, 12824, new int[]{BLUE_CHARM, 1444}, 198, 79, 695), HYDRA(67, 93200, 12025, 12442, new int[]{GREEN_CHARM, 571}, 128, 80, 141), SPIRIT_DAGGANOTH(68, 93208, 12017, 12456, new int[]{CRIMSON_CHARM, 6155}, 1, 83, 365), LAVA_TITAN(69, 93216, 12788, 12837, new int[]{BLUE_CHARM, OBSIDIAN_CHARM}, 219, 83, 730), SWAMP_TITAN(70, 93224, 12776, 12832, new int[]{CRIMSON_CHARM, 10149}, 150, 85, 374), RUNE_MINOTAUR(71, 93232, 12083, 12466, new int[]{BLUE_CHARM, 2363}, 1, 86, 757), UNICORN_STALLION(72, 93240, 12039, 12434, new int[]{GREEN_CHARM, 237}, 140, 88, 154), GEYSER_TITAN(73, 93248, 12786, 12833, new int[]{BLUE_CHARM, 1444}, 222, 89, 783), WOLPERTINGER(74, 94000, 12089, 12437, new int[]{CRIMSON_CHARM, 3226}, 203, 92, 405), ABYSSAL_TITAN(75, 94008, 12796, 12827, new int[]{GREEN_CHARM, ABYSSAL_CHARM}, 113, 93, 163), IRON_TITAN(76, 94016, 12822, 12828, new int[]{CRIMSON_CHARM, 1115}, 198, 95, 418), PACK_YAK(77, 94024, 12093, 12435, new int[]{CRIMSON_CHARM, 10818}, 211, 96, 422), STEEL_TITAN(78, 94032, 12790, 12825, new int[]{CRIMSON_CHARM, 1119}, 178, 99, 435);

        public int shardsRequired;
        public int[] requiredItems;

        private int idx;

        private SummonData(int idx, int i1, int i2, int i3, int[] requiredItems, int shardsRequired, int i4, int i5) {
            this(idx, requiredItems, shardsRequired);
        }

        private SummonData(int idx, int[] requiredItems, int shardsRequired) {
            this.requiredItems = requiredItems;
            this.shardsRequired = shardsRequired;
            this.idx = idx;
        }

        public static SummonData forIdx(int idx) {
            for (SummonData data : values()) {
                if (data.idx == idx + 1) return data;
            }
            return null;
        }

        public int[] getRequiredItems() {
            return requiredItems;
        }

        public int getShardsRequired() {
            return shardsRequired;
        }
    }

    private static void dumpInterface(int id) {
        dumpInterface(id, -1);
    }

    private static void dumpInterface(int id, int childIdx) {
        System.out.println("RSInterface rsi" + (childIdx >= 0 ? childIdx : "") + " = new RSInterface();");
        try {
            RSInterface parent = interfaceCache[id];
            Field[] fields = parent.getClass().getDeclaredFields();
            int[] children = new int[0];
            for (Field f : fields) {
                Class t = f.getType();
                Object v = f.get(parent);
                if (Modifier.isStatic(f.getModifiers()) || (!t.isPrimitive() && v == null) || (t == boolean.class && Boolean.FALSE.equals(v)) || (t.isPrimitive() && t != boolean.class && ((Number) v).doubleValue() == 0)) {
                    continue;
                }

                if (t == int[].class) {
                    StringBuilder sb = new StringBuilder((childIdx >= 0 ? ("rsi" + childIdx + ".") : "rsi.") + f.getName() + " = new int[]{");
                    int[] field = (int[]) v;
                    for (int i = 0; i < field.length; i++) {
                        sb.append(field[i]).append(",");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    sb.append("};");
                    System.out.println(sb);

                    if (f.getName().equals("children"))
                        children = field;
                } else if (t == int.class) {
                    StringBuilder sb = new StringBuilder((childIdx >= 0 ? ("rsi" + childIdx + ".") : "rsi.") + f.getName() + " = ");
                    int field = (int) v;
                    sb.append(field);
                    sb.append(";");
                    System.out.println(sb);
                } else if (t == boolean.class) {
                    StringBuilder sb = new StringBuilder((childIdx >= 0 ? ("rsi" + childIdx + ".") : "rsi.") + f.getName() + " = ");
                    boolean field = (boolean) v;
                    sb.append(field);
                    sb.append(";");
                    System.out.println(sb);
                } else if (t == TextDrawingArea.class) {
                    StringBuilder sb = new StringBuilder((childIdx >= 0 ? ("rsi" + childIdx + ".") : "rsi.") + f.getName() + " = fonts[");
                    TextDrawingArea field = (TextDrawingArea) v;
                    for (int i = 0; i < fonts.length; i++) {
                        if (fonts[i] == field) {
                            sb.append(i);
                            break;
                        }
                    }
                    sb.append("];");
                    System.out.println(sb);
                } else if (t == Sprite[].class) {
                    StringBuilder sb = new StringBuilder((childIdx >= 0 ? ("rsi" + childIdx + ".") : "rsi.") + f.getName() + " = ");
                    Sprite[] field = (Sprite[]) v;
                    if (field.length == 10) {
                        continue;
                    }
                    sb.append("new Sprite[]{");
                    boolean allNull = true;
                    for (int i = 0; i < field.length; i++) {
                        for (QueueNode qn : spriteCache.getHashTable().values()) {
                            if (qn == null) {
                                continue;
                            }

                            Sprite sprite = (Sprite) qn;
                            if (field[i] == sprite) {
                                if (sprite.idx != -1) {
                                    sb.append("loadSprite(");
                                    sb.append(sprite.name + ", ");
                                    sb.append("null, ");
                                    sb.append(sprite.idx + ");");
                                } else {
                                    sb.append("imageLoader(");
                                    String[] split = sprite.name.split(" ");
                                    sb.append(split[1]);
                                    sb.append(", \"" + split[0] + "\"), ");
                                }
                                break;
                            }
                        }
                    }
                    if (allNull) {
                        continue;
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    sb.append("}");
                    System.out.println(sb);
                } else if (t == Sprite.class) {
                    StringBuilder sb = new StringBuilder((childIdx >= 0 ? ("rsi" + childIdx + ".") : "rsi.") + f.getName() + " = ");
                    Sprite field = (Sprite) v;
                    for (QueueNode qn : spriteCache.getHashTable().values()) {
                        if (qn == null) {
                            continue;
                        }

                        Sprite sprite = (Sprite) qn;
                        if (field == sprite) {
                            if (sprite.idx != -1) {
                                sb.append("loadSprite(");
                                sb.append(sprite.name + ", ");
                                sb.append("null, ");
                                sb.append(sprite.idx + ");");
                            } else {
                                sb.append("imageLoader(");
                                String[] split = sprite.name.split(" ");
                                sb.append(split[1]);
                                sb.append(", \"" + split[0] + "\");");
                            }
                            break;
                        }
                    }
                    System.out.println(sb);
                } else if (t == String[].class) {
                    StringBuilder sb = new StringBuilder((childIdx >= 0 ? ("rsi" + childIdx + ".") : "rsi.") + f.getName() + " = ");
                    String[] field = (String[]) v;
                    sb.append("new String[]{");
                    for (int i = 0; i < field.length; i++) {
                        if (field[i] != null)
                            sb.append('"' + field[i] + '"' + ",");
                        else
                            sb.append("null,");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    sb.append("};");
                    System.out.println(sb);
                } else {
                    System.out.println((childIdx >= 0 ? ("rsi" + childIdx + ".") : "rsi.") + f.getName() + " = \"" + v.toString() + "\";");
                }
            }
            if (children.length != 0) {
                for (int i = 0; i < children.length; i++) {
                    dumpInterface(children[i], i);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
