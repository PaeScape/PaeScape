package com.arlania;

import com.arlania.model.Position;
import com.arlania.net.security.ConnectionHandler;

import java.math.BigInteger;

public class GameSettings {

    /*
     * OSRS Offsets
     */
    public static final int OSRS_OBJECTS_OFFSET = 300_000;
    public static final int OSRS_ITEMS_OFFSET = 200_000;
    public static final int OSRS_GFX_OFFSET = 2964;
    public static final int OSRS_ANIM_OFFSET = 21261;
    public static final int OSRS_NPC_OFFSET = 14_000;


    public static int progressiveJackpot = 10000000;

    //public static int activeSeason = 3;

    public static boolean shrOpen = true;

    /**
     * The game port
     */
    public static final int GAME_PORT = 43594;

    /**
     * The game version
     */
    public static final int GAME_VERSION = 13;

    /**
     * The maximum amount of players that can be logged in on a single game
     * sequence.
     */
    public static final int LOGIN_THRESHOLD = 25;

    /**
     * The maximum amount of players that can be logged in on a single game
     * sequence.
     */
    public static final int LOGOUT_THRESHOLD = 50;

    /**
     * The maximum amount of players who can receive rewards on a single game
     * sequence.
     */
    public static final int VOTE_REWARDING_THRESHOLD = 15;

    /**
     * The maximum amount of connections that can be active at a time, or in
     * other words how many clients can be logged in at once per connection.
     * (0 is counted too)
     */
    public static final int CONNECTION_AMOUNT = 4;

    /**
     * The throttle interval for incoming connections accepted by the
     * {@link ConnectionHandler}.
     */
    public static final long CONNECTION_INTERVAL = 1000;

    /**
     * The number of seconds before a connection becomes idle.
     */
    public static final int IDLE_TIME = 15;

    /**
     * The keys used for encryption on login
     */
    public static final BigInteger RSA_MODULUS = new BigInteger("141038977654242498796653256463581947707085475448374831324884224283104317501838296020488428503639086635001378639378416098546218003298341019473053164624088381038791532123008519201622098961063764779454144079550558844578144888226959180389428577531353862575582264379889305154355721898818709924743716570464556076517");
    public static final BigInteger RSA_EXPONENT = new BigInteger("73062137286746919055592688968652930781933135350600813639315492232042839604916461691801305334369089083392538639347196645339946918717345585106278208324882123479616835538558685007295922636282107847991405620139317939255760783182439157718323265977678194963487269741116519721120044892805050386167677836394617891073");

    /**
     * The maximum amount of messages that can be decoded in one sequence.
     */
    public static final int DECODE_LIMIT = 30;

    /** GAME **/

    /**
     * Processing the engine
     */
    public static final int ENGINE_PROCESSING_CYCLE_RATE = 600;
    public static final int GAME_PROCESSING_CYCLE_RATE = 600;

    /**
     * Are the MYSQL services enabled?
     */
    // TODO: remove remaining mysql stuff
    public static boolean MYSQL_ENABLED = false;
    /**
     * Is it currently bonus xp?
     */
    public static boolean BONUS_EXP = false;//Misc.isWeekend();
    /**
     * The default position
     */
    public static final Position DEFAULT_POSITION = new Position(1804, 3781, 0);
    public static final Position DZ_POSITION = new Position(2657, 2593, 0);
    public static final Position DZBOAT_POSITION = new Position(2657, 2611, 0);
    public static final Position HOMEBOAT_POSITION = new Position(1833, 3770, 0);
    public static final Position RAIDS_LOBBY_POSITION = new Position(2442, 3087, 0);
    public static final Position JAIL = new Position(2526, 3374, 0);


    public static int npcMessageTick = 0;
    public static int storeRefreshTick = 0;

    public static int[] soloGlobalEvents = {7020, 7021, 7022, 7023, 7024, 7025, 7026, 7027, 7028, 7029, 7030, 7032, 7033, 7034, 7035};
    public static int[] duoGlobalEvents = {7040, 7041, 7042, 7043, 7044, 7045, 7046, 7047, 7048, 7049};
    public static int[] soloPersonalEvents = {4020, 4021, 4022, 4023, 4024, 4025, 4027, 4028, 4029, 4030, 4031, 4032};

    public static String randomWellEvent = "";
    public static int wellOfEventsCooldown = 30000;

    public static String[] singleEvents = {"accelerate", "accuracy", "doubleloot", "droprate", "2xboss", "2xslayer", "2xskiller",
            "efficiency", "berserker", "raider", "eventbox", "lifelink",
            "sweettooth", "barrowsbash", "godsgifts", "specialized", "investigative"
    };

    //New Events
    //Specialized - 10% specs on weapons

    //name TBD - boost all minigame rewards
    //name TBD - drop rate boosts for specific raids
    //name TBD - use 2 personal events
    //name TBD - use any personal event without using personal event time


    //Can't combat 2 max hit, accuracy, berserker, etc events at the same time

    public static boolean wellGoodwill = false;

    public static boolean holidayEvent = false;


    public static final int MAX_STARTERS_PER_IP = 5;

    /**
     * Untradeable items`
     * Items which cannot be traded or staked
     */

    public static int[] PERKS_RELICS =
            {
                    611, 612, 786, 2686, 2687, 2688, 4428, 6500, 6570, 7409, 8839, 8840, 8842, 10506, 10551, 11663, 11664, 11665, 13263, 18337,
                    18338, 18339, 19998, 20003, 20007, 20015, 20023, 20027, 20072, 19999, 19994, 20039, 19998, 20035, 775, 776, 13663,
                    19711, 19712, 19785, 19786, 19787, 19788, 19789, 19790, 19803, 19804, 19675, 7509, 1580, 5553, 5554, 5555, 5556, 5557
            };

    public static int[] PETS =
            {
                    11971, 11972, 11978, 11979, 11981, 11982, 11983, 11984, 11985, 11986, 11987, 11988, 11989, 11990, 11991,
                    11992, 11993, 11994, 11995, 11996, 11997, 12001, 12002, 12003, 12004, 12005, 12006,
                    14914, 14916, 20079, 20080, 20081, 20082, 20083, 20085, 20086, 20087, 20088, 20089, 20090, 20103, 20104,
                    221509, 220659, 220661, 220663, 220665, 220693, 221187, 213322, 211320, 220851, 11157, 212399,
                    212646, 222473, 224491, 219730, 207582, 9735, 224373, 3619, 221992, 14914, 14916, 7930, 2948, 964, 3619, 212335, 8740, 6550
            };

    //222336, 223398 Pets?

    public static final int[] OTHER_ITEMS =
            {
                    18349, 18351, 18353, 8839, 8840, 8841, 8842, 13262, 8844, 8845, 8846, 8847, 8848, 8849, 8850, 8851,
                    14022, 14020, 14021, 6570, 14019, 19785, 19786, 19787, 19788, 19789, 19790, 19803, 19804, 19711, 19712, 19670,
                    219833, 212021, 212542, 219782, 219835, 2677
            };

    public static int[] UNTRADEABLE_ITEMS =
            {
                    611, 612, 786, 2686, 2687, 2688, 4428, 6500, 6570, 7409, 8839, 8840, 8842, 10506, 10551, 11663, 11664, 11665, 13263, 18337,
                    18338, 18339, 19998, 20003, 20007, 20015, 20023, 20027, 20072, 19999, 19994, 20039, 19998, 20035, 775, 776, 13663,
                    19711, 19712, 19785, 19786, 19787, 19788, 19789, 19790, 19803, 19804, 19675, 224491,
                    11971, 11972, 11978, 11979, 11981, 11982, 11983, 11984, 11985, 11986, 11987, 11988, 11989, 11990, 11991,
                    11992, 11993, 11994, 11995, 11996, 11997, 12001, 12002, 12003, 12004, 12005, 12006,
                    14914, 14916, 20079, 20080, 20081, 20082, 20083, 20085, 20086, 20087, 20088, 20089, 20090, 20103, 20104,
                    221509, 220659, 220661, 220663, 220665, 220693, 221187, 213322, 211320, 220851, 223398, 222336, 212646, 222473,
                    18349, 18351, 18353, 18355, 18357, 18359, 18361, 2996, 5553, 5554, 5555, 5556, 5557, 207582,
                    8839, 8840, 8841, 8842, 13262, 8844, 8845, 8846, 8847, 8848, 8849, 8850, 8851,
                    14022, 14020, 14021, 6570, 14019, 19785, 19786, 19787, 19788, 19789, 19790, 19803, 19804, 19711, 19712,
                    14876, 14877, 14878, 14879, 14880, 14881, 14882, 14883, 14884, 14885, 14886, 14887, 14888, 14889, 14890, 14891, 14892,
                    14058, 14059, 14060, 15246, 10075, 2946, 2949, 2950, 2951, 3702, 19111,
                    213280, 221795, 221791, 221793, 221295, 221780, 221776, 221784, 221285,
                    15492, 219647, 221264, 224370, 219643, 219639, 223073, 221888, 212791, 219722, 221898,
                    220704, 220706, 220708, 220710, 220712, 219988, 221345, 212013, 212014, 212015, 212016,
                    213258, 213259, 213260, 213261, 210941, 210939, 210940, 210933, 213646, 213642, 213640, 213644, 7509, 1580,
                    20046, 10069, 11137, 18782,
                    21025, 21026, 21027, 21028, 21029, 21030, 21034, 21043, 21047,
                    4020, 4021, 4022, 4023, 4024, 4025, 4026, 4027, 4028, 4029, 4030, 4031, 4032,
                    14601, 14602, 14603, 14605,
                    219833, 212021, 212542, 219782, 219835, 220358, 220360, 220362, 220364, 219712, 219714, 219716, 219718, 213648, 213649, 213650, 213651,
                    213392, 600, 730, 13659, 13661, 221140, 221177, 221183, 219730,
                    201543, 201544, 201545, 201546, 201547, 206754, 220526, 202399, 211942, 213329,
                    4033, 4034, 4035, 4036, 4037, 4039, 4041,
                    223971, 223975, 223979, 220695, 220696, 220703,
                    13920, 13908, 13914, 13926, 13911, 13917, 13923, 13929, 13938, 13932, 13935, 13941, 13950, 13944, 13947,
                    221760, 221992, 1854,
                    7930, 2948, 964, 3619, //holiday pets
                    11157, 212399, 212335, 8740, 6550,  //donator pets
                    203230, 203231, 203232, 203233, 203234, 203235, 203236, 203237, 203238, 203239, 203240, 203241, 203242, 203243, 203244, 203245, 203246, 203247,
                    906, 907, 908, 909, 910, 213195, 14852, 14850, 14848, 14846, 155, 153, 151, 2438, 19670,
                    207144, 202959, 207821, 207854, 207827, 207812, 20008, 20027, 211941, 2795, 6758, 6749, 6769, 2709, 219476, 213069,

                    //seasonal
                    224372, 225001, 225004, 225007, 224413, 224370
            };

    public static int[] CORRUPT_PVP_EQUIPMENT =
            {
                    13920, 13908, 13914, 13926, 13911, 13917, 13923, 13929, 13938, 13932, 13935, 13941, 13950, 13944, 13947
            };

    public static final int[] ALCHABLES = {1079, 1093, 1113, 1127, 1147, 1163, 1185, 1201, 1213, 1247, 1275, 1289, 1303, 1319, 1333, 1347, 1359, 1373, 1432,
            2487, 2489, 2491, 2493, 2495, 2497, 2499, 2501, 2503, 861, 9185, 3749, 3751, 10828, 1704, 11126, 1725, 5574, 5575, 5576, 9672, 9674, 9676, 9678,
            1215, 1249, 1305, 1377, 1434, 3204, 4089, 4091, 4093, 4095, 4097, 4099, 4101, 4103, 4105, 4107, 4109, 4111, 4113, 4115, 4117, 4675};

    public static final int[] AUTO_ALCHS = {3749, 3751, 3753, 3755, 4675};

    public static final int[] UPGRADEABLE_UNTRADEABLES = {11663, 11664, 11665, 8839, 8840, 8842, 8850, 20072, 10551, 7462, 10499, 2412, 2413, 2414, 6570};
    public static final int[] CUSTOMUNIQUES = {20012, 20010, 20011, 20013, 20014, 14062, 20016, 20017, 20018, 222951, 21031, 21032, 21033, 21035, 21061, 21063, 21070, 21071, 21072, 21076, 21077, 21078, 765};
    public static final int[] UPGRADEABLECUSTOMUNIQUES = {20012, 20010, 20011, 20013, 20014, 14062, 20016, 20017, 20018, 222951, 21031, 21032, 21033, 21035, 21061, 21063, 21064,
            21070, 21071, 21072, 21073, 21074, 21075, 21076, 21077, 21078, 21080, 21082, 21084, 765};

    public static final int[] WILDYUNIQUES = {13864, 13858, 13861, 13876, 13870, 13873, 13896, 13884, 13890, 13887, 13893,
            13899, 13905, 13902, 13867, 13879};
    public static final int[] MASTERUNIQUES = {21005, 21006, 20998, 4450, 14008, 14009,
            14010, 14011, 14012, 14013, 14014, 14015, 14016, 20569, 20570, 20571};

    public static final int[] LEGENDARYUNIQUES = {14484, 18888, 18889, 18890, 21000, 21001, 21002, 13239, 13235, 12708, 18844,
            13738, 13740, 13742, 13744, 224419, 224420, 224421, 224417, 18347, 222981, 4448, 4454, 4452, 21003, 21004};

    public static final int[] LEGENDARYUNIQUESfromBOX = {14484, 18888, 18889, 18890, 21000, 21001, 21002, 18844,
            13738, 13740, 13742, 13744, 224419, 224420, 224421, 224417, 4448, 4454, 4452, 21003, 21004};

    public static final int[] HIGHUNIQUES = {11724, 11726, 11722, 11720, 13045, 13051, 20555, 12282, 11283, 212006, 11716,
            18843, 20568, 13736, 20564, 12926, 12284, 11694, 11696, 11698, 11700, 219496, 224271, 12601, 222002};

    public static final int[] MEDIUMUNIQUES = {4453, 11730, 11728, 11718, 4151, 15486, 13047, 11235, 6737, 6735, 6733, 6731, 18897,
            11924, 11926, 6739, 15259, 18845, 11690, 12603, 12605, 224780, 20565, 15241, 19780, 14057, 221028, 221298, 221301, 221304, 6746};

    public static final int[] LOWUNIQUES = {4708, 4710, 4712, 4714, 4716, 4718, 4720, 4722, 4724, 4726, 4728, 4730, 4732, 4734, 4736, 4738,
            4745, 4747, 4749, 4751, 4753, 4755, 4757, 4759, 6920, 11732, 6585, 6914, 6889, 2577, 14497, 14499, 14501, 18846, 6724,
            15403, 211905};

    public static final int[] LOWSUPPLYDROPS = {560, 565, 566, 892, 11212, 11230, 5295, 5296, 5297, 5298, 5299, 5300, 5301, 5302, 5303, 5304, 443, 454,
            445, 448, 450, 1624, 1622, 1620, 1618, 7937, 9242, 9243, 9244, 15243, 2, 1516, 1514, 12158, 12159, 12160, 12163};

    public static final int[] WILDYSUPPLYDROPS = {213440, 221348, 221880, 14837};

    public static final int[] HIGHSUPPLYDROPS = {10926, 6686, 18831, 9245, 6693, 452, 1632, 218, 220, 3052, 3050, 537};

    public static final int[] TZHAARDROPS = {6522, 6523, 6524, 6525, 6526, 6527, 6528, 6568, 221298, 221301, 221304};

    public static final int[] SMALL_MYSTERY_EXCHANGE = {20566, 11702, 11704, 11706, 11708, 213241, 213243, 221031, 219493};

    public static final int[] MEDIUM_MYSTERY_EXCHANGE = {18895, 18896, 18898, 18847, 20572, 20753, 20574, 220368, 220370, 220372,
            220374};

    public static final int[] SIX_RARE_CANDY = {20012, 20010, 20011, 20013, 20014, 14062, 20016, 20017, 20018, 222951};

    public static final int[] FOUR_RARE_CANDY = {2415, 2416, 2417, 19143, 19146, 19149};

    public static final int[] TWO_KEY_UNIQUES = {1149, 4087, 4585, 1187, 1215, 1377, 11613};

    public static final int[] SOUL_EQUIP = {21007, 21008, 21009, 21016, 21017, 21018, 21040, 21044, 18905, 18906, 18907, 2711};
    public static final int[] BLOOD_EQUIP = {21010, 21011, 21012, 21019, 21020, 21021, 21041, 21045, 18899, 18900, 18901, 2710};
    public static final int[] GILDED_EQUIP = {21013, 21014, 21015, 21022, 21023, 21024, 21042, 21046, 18892, 18893, 18894, 2712};
    public static final int[] PINK_EQUIP = {21025, 21026, 21027, 21028, 21029, 21030, 21043, 21047, 2765, 2766, 2767, 2713};
    public static final int[] TAINTED_EQUIP = {2042, 2043, 2044, 2045, 2046, 2047, 2051, 2052, 2053, 2716, 2057, 2056};

    public static final int[] gwdUniques = {11724, 11726, 11722, 11720, 11718, 11728, 11730, 11716, 11694, 11696, 11698, 11700};

    public final static int easyClue = 219833;
    public final static int mediumClue = 212021;
    public final static int hardClue = 212542;
    public final static int eliteClue = 219782;
    public final static int masterClue = 219835;
    public final static int mysteryBox = 6199;
    public final static int eliteMysteryBox = 15501;
    public final static int rareCandy = 4562;
    public final static int effigy = 18778;


    public static int rare0 = -1;
    public static int rare0quantity = -1;


    /**
     * Unsellable items
     * Items which cannot be sold to shops
     */
    public static int[] UNSELLABLE_ITEMS = new int[]{
            18349, 18351, 18353, 995, 18349, 18351, 18353, 13262, 19634, 19635, 19642, 19643, 19644, 19711, 19712, 19785, 19786, 19787, 19788, 19789,
            19790, 19803, 19804, 6570, 14019, 20747, 19785, 19786, 19787, 19788, 19789, 19790, 19803, 19804, 8839, 8840, 8841, 8842, 19711, 19712,
            985, 987, 989, 4604, 707, 6199, 4562, 15501, 299, 601, 602, 603, 2677, 2678, 2681, 6758, 6769, 11157, 212399, 2957, 2683, 2684,
            15332, 15328, 15324, 15320, 15316, 15312, 15308, 15300, 13591,
            4020, 4021, 4022, 4023, 4024, 4025, 4026, 4027, 4028, 4029, 4030, 4031, 4032, 4033, 4034, 4035, 4036,
            7020, 7021, 7022, 7023, 7024, 7025, 7026, 7027, 7028, 7029, 7030, 7031, 7032, 7033, 7034, 7035, 7036, 7037, 7038, 7039, 7040,
            1854
    };

    /*
     * 2021 Holiday events
     */
    public static int[] HALLOWEEN_EVENT = new int[]{9921, 9922, 9923, 9924, 9925};
    public static int[] CHRISTMAS_EVENT = new int[]{14601, 14602, 14603, 14605};


    public static final String[] INVALID_NAMES = {"mod", "moderator", "admin", "administrator", "owner", "developer",
            "supporter", "dev", "developer", "nigga", "0wn3r", "4dm1n", "m0d", "adm1n", "a d m i n", "m o d",
            "o w n e r"};

    public static final int[]
            /*
             * 2021 donation promos
             */
            //DONATION_JULY = {225118},
            //DONATION_AUGUST = {213203, 223522},
            //DONATION_SEPTEMBER = {11288, 9920},
            //DONATION_OCTOBER = {225119, 11789},
            //DONATION_NOVEMBER = {4084, 1419},
            //DONATION_DECEMBER = {14050, 220834},

            /*
             * 2022 donation promos
             */

            //DONATION_JANUARY = {14044},
            //DONATION_FEBRUARY = {6769},
            //DONATION_MARCH = {6758},
            //DONATION_APRIL = {6769},
            //DONATION_MAY = {6758},
            //DONATION_JUNE = {211863},
            //DONATION_JULY = {6769},
            //DONATION_AUGUST = {603},
            //DONATION_SEPTEMBER = {7032},
            //DONATION_OCTOBER = {603},
            //DONATION_NOVEMBER = {603},
            //DONATION_DECEMBER = {69},

            /*
             * 2023 $50 donation promos
             */

            FIFTY_DONATION_JANUARY = {15501},
            FIFTY_DONATION_FEBRUARY = {6769},
            FIFTY_DONATION_MARCH = {4035},
            FIFTY_DONATION_APRIL = {603},
            FIFTY_DONATION_MAY = {},
            FIFTY_DONATION_JUNE = {},
            FIFTY_DONATION_JULY = {603},
            FIFTY_DONATION_AUGUST = {},
            FIFTY_DONATION_SEPTEMBER = {},
            FIFTY_DONATION_OCTOBER = {603},
            FIFTY_DONATION_NOVEMBER = {},
            FIFTY_DONATION_DECEMBER = {},
            FIFTY_DONATION_SEASONAL = {4032},

    TWENTY_DONATION_JANUARY = {15501},
            TWENTY_DONATION_FEBRUARY = {6769},
            TWENTY_DONATION_MARCH = {15501},
            TWENTY_DONATION_APRIL = {15501},
            TWENTY_DONATION_MAY = {},
            TWENTY_DONATION_JUNE = {},
            TWENTY_DONATION_JULY = {603},
            TWENTY_DONATION_AUGUST = {},
            TWENTY_DONATION_SEPTEMBER = {},
            TWENTY_DONATION_OCTOBER = {603},
            TWENTY_DONATION_NOVEMBER = {},
            TWENTY_DONATION_DECEMBER = {},
            TWENTY_DONATION_SEASONAL = {4020};

    public static final int
            ATTACK_TAB = 0,
            SKILLS_TAB = 1,
            QUESTS_TAB = 2,
            ACHIEVEMENT_TAB = 14,
            INVENTORY_TAB = 3,
            EQUIPMENT_TAB = 4,
            PRAYER_TAB = 5,
            MAGIC_TAB = 6,

    SUMMONING_TAB = 13,
            FRIEND_TAB = 8,
            IGNORE_TAB = 9,
            CLAN_CHAT_TAB = 7,
            LOGOUT = 10,
            OPTIONS_TAB = 11,
            EMOTES_TAB = 12;

    public static boolean DISCORD = false;

    public static final String SERVER_NAME = "PaeScape";

    public final static String ANNOUNCE_CH = "-1";
    public static final long DISC_CHAT_CH = -1L;
    public static final long DISC_CLAN_CHAT_CH = -1L;
    public static final long DISC_PRIVATE_CHAT_CH = -1L;
    public static final long DISC_SEASONAL_POINTS_CH = -1L;
    public static final long DISC_COMMAND_CH = -1L;
    public static final long DISC_COMMANDLOG_CH = -1L;
    public static final long DISC_MARKETPLACE_CH = -1L;
    public static final long DISC_EXCHANGE_CH = -1L;
    public static final long DISC_TRADELOGS_CH = -1L;
    public static final long DISC_DROPPED_ITEMS_CH = -1L;
    public static final long DISC_NULL_KILLER_CH = -1L;
    public static final long DISC_DEATHSCOFFERS_CH = -1L;
    public static final long DISC_TREASURECHEST_CH = -1L;
    public static final long DISC_LOYALTYLOGS_CH = -1L;
    public static final long DISC_RANDOM_EVENT_LOG_CH = -1L;
    public static final long DISC_GAME_INFO_CH = -1L;
    public static final long DISC_ACTIONS_CH = -1L;
    public static final long DISC_PRESTIGE_CH = -1L;
    public static final long DISC_LOGIN_CH = -1L;
    public static final long SUBSCRIPTION_BOX_DROPS_CH = -1L;
    public static final long ACTION_LOGS_CH = -1L;
    public static final long SUBSCRIPTION_CH = -1L;
    public static final long PASS_LOGS_CH = -1L;
    public static final long GOLD_SINKS_CH = -1L;
    public static final long PLAYER_KILLS_CH = -1L;
    public static final long MISC_LOGS_CH = -1L;
    public static final long MAILBOX_LOGS_CH = -1L;
    public static final long MARKETPLACE_CH = -1L;
    public static final long SHR_LOGS_CH = -1L;
    public static final long GAMBLING_LOGS_CH = -1L;
    public static final long MARKETPLACE_YEET_LOG = -1L;
    public static final long DISC_BINGO_LOG = -1L;

    public static final long CONSOLE_LOG_CH = -1L;

    public static final long ROLE_INGAME_CHAT = -1L;
    public static final long NEW_PLAYERS = -1L;
    public static final String EVENTS_CH = "-1";
    public static final long DONATOR_STORE_LOGS = -1L;
    public static final long PICKUP_LOGS_CH = -1L;
    public static final String SUPPORT_ROLE = "<@&788580098705850418>";
    public static final String MODERATOR_ROLE = "<@&758079512486608946>";
    public static final String PEEPOLOVE = "<:peepolove:873685932363096064>";
    public static final String SUCCESSKID = "<:successkid:823242346207510549>";

    public static final String EVERYTHINGRS_API_KEY = "";
    public static final String GAPAY_API_KEY = "";
}
