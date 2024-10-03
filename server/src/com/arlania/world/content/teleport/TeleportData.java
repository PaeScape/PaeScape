package com.arlania.world.content.teleport;

import com.arlania.model.Item;
import com.arlania.model.Position;
import org.apache.commons.lang3.text.WordUtils;

public enum TeleportData {

    AL_KHARID(TeleportCategory.CITIES, new Position(3291, 3182, 0)),
    ARDOUGNE(TeleportCategory.CITIES, new Position(2662, 3305, 0)),
    CAMELOT(TeleportCategory.CITIES, new Position(2757, 3477, 0)),
    CANIFIS(TeleportCategory.CITIES, new Position(3506, 3496, 0)),
    DRAYNOR(TeleportCategory.CITIES, new Position(3093, 3244, 0)),
    EDGEVILLE(TeleportCategory.CITIES, new Position(3088, 3504, 0)),
    FALADOR(TeleportCategory.CITIES, new Position(2964, 3378, 0)),
    GNOME_STRONGHOLD(TeleportCategory.CITIES, new Position(2449, 3434, 0)),
    KARAMJA(TeleportCategory.CITIES, new Position(2948, 3147, 0)),
    KELDAGRIM(TeleportCategory.CITIES, new Position(2847, 10218, 0)),
    LUMBRIDGE(TeleportCategory.CITIES, new Position(3222, 3218, 0)),
    RELLEKKA(TeleportCategory.CITIES, new Position(2658, 3661, 0)),
    SHANTY_PASS(TeleportCategory.CITIES, new Position(3303, 3124, 0)),
    SHILO_VILLAGE(TeleportCategory.CITIES, new Position(2831, 2969, 0)),
    SLEPE(TeleportCategory.CITIES, new Position(3722, 3323, 0)),
    TAVERLY(TeleportCategory.CITIES, new Position(2894, 3444, 0)),
    TROLLHEIM(TeleportCategory.CITIES, new Position(2891, 3679, 0)),
    TZHAAR(TeleportCategory.CITIES, new Position(2445, 5177, 0)),
    VARROCK(TeleportCategory.CITIES, new Position(3210, 3424, 0)),
    YANILLE(TeleportCategory.CITIES, new Position(2574, 3089, 0)),
    ZUL_ANDRA(TeleportCategory.CITIES, new Position(2199, 3056, 0)),

    BARBARIAN_AGILITY(TeleportCategory.TRAINING, new Position(2552, 3556, 0)),
    CRAFTING_GUILD(TeleportCategory.TRAINING, new Position(2933, 3285, 0)),
    ESSENCE_MINE(TeleportCategory.TRAINING, new Position(2911, 4832, 0)),
    FARMING_PATCH(TeleportCategory.TRAINING, new Position(3056, 3309, 0)),
    IMPLINGS(TeleportCategory.TRAINING, new Position(2282, 3578, 0)),
    MINING_GUILD(TeleportCategory.TRAINING, new Position(3040, 9741, 0)),
    PISCATORIS_FISHING(TeleportCategory.TRAINING, new Position(2345, 3694, 0)),
    RIMMINGTON_MINE(TeleportCategory.TRAINING, new Position(2977, 3237, 0)),
    WOODCUTTING_GUILD(TeleportCategory.TRAINING, new Position(1592, 3488, 0)),

    ANCIENT_CAVERN(TeleportCategory.DUNGEONS, new Position(1745, 5325, 0), 5363, 2000, itemsFromIds(11335, 1149, 14479, 3140, 4087, 4585, 1187, 11613, 4587, 1215, 1377, 11286, 4453)),
    APE_ATOLL(TeleportCategory.DUNGEONS, new Position(2805, 9143, 0)),
    ASGARNIA_ICE_CAVE(TeleportCategory.DUNGEONS, new Position(3055, 9555, 0), 3071, 2000, new Item(20564), new Item(11283)),
    BRIMHAVEN_DUNGEON(TeleportCategory.DUNGEONS, new Position(2713, 9564, 0), 1592, 2000, itemsFromIds(11335, 1149, 14479, 3140, 4087, 4585, 1187, 11613, 4587, 1215, 1377, 11286, 4453)),
    EDGEVILLE_DUNGEON(TeleportCategory.DUNGEONS, new Position(3097, 9868, 0), 1880, 1500),
    SLAYER_DUNGEON(TeleportCategory.DUNGEONS, new Position(2805, 10001, 0)),
    SLAYER_TOWER(TeleportCategory.DUNGEONS, new Position(3429, 3538, 0), 1615, 2000, new Item(4153), new Item(4131), new Item(4151), new Item(13047)),
    STRYKEWYRM_CAVERN(TeleportCategory.DUNGEONS, new Position(2738, 5081, 0)),
    TAVERLY_DUNG(TeleportCategory.DUNGEONS, new Position(2884, 9798, 0)),
    WATERBIRTH_DUNGEON(TeleportCategory.DUNGEONS, new Position(2527, 3739, 0), 2883, 2000, itemsFromIds(15126)),

    ABYSSAL_SIRE(TeleportCategory.BOSSES, new Position(2970, 4770, 0), 5886, 2500, new Item(4151), new Item(13045), new Item(13047), new Item(20090)),
    CERBERUS(TeleportCategory.BOSSES, new Position(1240, 1243, 0), 1999, 2500, new Item(6640), new Item(6642), new Item(6645), new Item(20080)),
    CORPOREAL_BEAST(TeleportCategory.BOSSES, new Position(2974, 4382, 2), 8133, 3000, itemsFromIds(13734, 13754, 13752, 13746, 13750, 13748, 12001)),
    DAGANNOTH_KINGS(TeleportCategory.BOSSES, new Position(2900, 4448, 0), 2883, 2000, itemsFromIds(6731, 6733, 6735, 6737, 6739, 11990, 12005, 12006)),
    GIANT_MOLE(TeleportCategory.BOSSES, new Position(1761, 5186, 0), 3340, 2000, new Item(5073, 100), new Item(20558)),
    GODWARS_DUNGEON(TeleportCategory.BOSSES, new Position(2871, 5319, 2), 6260, 2500, itemsFromIds(11732, 11716, 11718, 11720, 11722, 11730, 13051, 11724, 11726, 11728, 11690, 11702, 11704, 11706, 11708, 11997, 12002, 12003, 12004)),
    //KALPHITE_QUEEN(TeleportCategory.BOSSES, new Position(3488, 9516, 0), 1158, 2000, new Item(20555), new Item(19982), new Item(11993)),
    KING_BLACK_DRAGON(TeleportCategory.BOSSES, new Position(2273, 4681, 0), 50, 2000, itemsFromIds(11335, 1149, 14479, 3140, 4087, 4585, 1187, 11613, 4587, 1215, 1377, 11286, 4453, 11996)),
    //NIGHTMARE(TeleportCategory.BOSSES, new Position(2460, 4772, 0), 23425, 2000, itemsFromIds(224419, 224420, 224421, 224417, 20568, 20572, 20573, 20574)),
    THERMONUCLEAR(TeleportCategory.BOSSES, new Position(2378, 4689, 0), 499, 1500, new Item(18897), new Item(213233), new Item(221028), new Item(20089)),


    BARROWS(TeleportCategory.MINIGAMES, new Position(3565, 3313, 0)),
    BLAST_FURNACE(TeleportCategory.MINIGAMES, new Position(1938, 4960, 0), itemsFromIds(2357, 2359, 2361, 2363)),
    BLAST_MINE(TeleportCategory.MINIGAMES, new Position(1508, 3849, 0), itemsFromIds(221345, 219988)),
    BOSS_INSTANCES(TeleportCategory.MINIGAMES, new Position(2341, 9625, 0)),
    DEATHS_COFFERS(TeleportCategory.MINIGAMES, new Position(3172, 5727, 0)),
    EQUIPMENT_UPGRADES(TeleportCategory.MINIGAMES, new Position(2790, 9328, 0)),
    FIGHT_CAVES(TeleportCategory.MINIGAMES, new Position(2445, 5177, 0), itemsFromIds(6570, 221295)),
    GAMES_ROOM(TeleportCategory.MINIGAMES, new Position(2207, 4960, 0)),
    MARKETPLACE(TeleportCategory.MINIGAMES, new Position(3164, 3483, 0)),
    MOTHERLODE_MINE(TeleportCategory.MINIGAMES, new Position(3739, 5676, 0)),
    NOBILITY(TeleportCategory.MINIGAMES, new Position(2506, 3860, 1)),
    //SHANTAY_PASS(TeleportCategory.MINIGAMES, new Position(3304, 3123, 0)),
    WINTERTODT(TeleportCategory.MINIGAMES, new Position(1631, 3944, 0), itemsFromIds(220704, 220706, 220708, 220710, 220712)),


    CHAMBERS_OF_XERIC(TeleportCategory.RAIDS, new Position(1234, 3558, 0), itemsFromIds(18888, 18889, 18890, 4452, 18844, 14484, 4454, 4450, 20998)),
    RAIDS_LOBBY(TeleportCategory.RAIDS, new Position(2442, 3087, 0), itemsFromIds(21031, 21032, 21033, 20012, 20010, 20011, 20013, 20014, 14062, 20016, 20017, 20018, 222951, 14008, 14009, 14010, 14011, 14012, 14013, 14014, 14015, 14016, 2415, 2416, 2417, 19143, 19146, 19149, 224003, 11987)),
    STRONGHOLD_RAIDS(TeleportCategory.RAIDS, new Position(3080, 3424, 0), itemsFromIds(21061, 21063, 21065, 21068, 21069, 21070, 21071, 21072, 21076, 21077, 21078, 21079, 21081, 21083)),
    THEATRE_OF_BLOOD(TeleportCategory.RAIDS, new Position(3669, 3218, 0), itemsFromIds(21003, 21004, 21005, 21006, 21000, 21001, 21002)),
    ;

    //TORMENTED_DEMON(TeleportCategory.BOSSES, new Position(2540, 5777, 0), 8349, 2500, new Item(14484), new Item(20565), new Item(11992)),
    //LIGHTHOUSE(TeleportCategory.DUNGEONS, new Position(2521, 4645, 0)),
    //WATERBIRTH_DUNGEON(TeleportCategory.DUNGEONS, new Position(2446, 10147, 0)),
    //LIGHT_DUNGEON(TeleportCategory.DUNGEONS, new Position(2005, 4644, 0)),
    //NOMADS_REQUEIM("Nomad's Requeim", TeleportCategory.MINIGAMES, new Position(1891, 3177, 0)),
    //WARRIORS_GUILD("Warrior's Guild", TeleportCategory.MINIGAMES, new Position(2855, 3543, 0)),
    //RECIPE_FOR_DISASTER(TeleportCategory.MINIGAMES, new Position(1847, 3786, 0)),
    //CHAOS_RAID(TeleportCategory.MINIGAMES, new Position(3102, 5535, 0)),
    //DUEL_ARENA(TeleportCategory.MINIGAMES, new Position(3366, 3268, 0)),
    //CHINCOMPHA_HUNTING(TeleportCategory.TRAINING, new Position(2820, 2927, 0)),
    //GEM_ROCKS(TeleportCategory.TRAINING, new Position(2824, 2997, 0)),
    //HERBIBOAR(TeleportCategory.TRAINING, new Position(2902, 2947, 0)),
    //ROCK_CRABS(TeleportCategory.TRAINING, new Position(2674, 3716, 0), 1265),
    //SUMMONING_OBELISK(TeleportCategory.TRAINING, new Position(2209, 5348, 0)),
    //NEX_DUNGEON(TeleportCategory.BOSSES, new Position(2332, 9575, 0), 13447, 2000, itemsFromIds(14008, 14009, 14010, 14011, 14012, 14013, 14014, 14015, 14016, 12601, 11987)),
    //REVENANT_CAVE(TeleportCategory.WILDERNESS, new Position(3074, 3651, 0)),
    //MAGE_BANK("Mage Bank (Level 52)", TeleportCategory.WILDERNESS, new Position(3099, 3958, 0)),
    //WEST_DRAGONS(TeleportCategory.WILDERNESS, new Position(2980, 3599, 0), 941, 2000, new Item(536), new Item(11983)),
    //EAST_DRAGONS(TeleportCategory.WILDERNESS, new Position(3339, 3667, 0), 941, 2000, new Item(536), new Item(11983)),
    //REVENANT_TOWN(TeleportCategory.WILDERNESS, new Position(3660, 3503, 0), 6725, 2000, itemsFromIds(13899, 13902, 13867, 13879, 14876, 14877, 14878, 14879, 14880, 14881, 14882, 14883)),
    //WILDYWYRM(TeleportCategory.WILDERNESS, new Position(3195, 3831, 0), 3334, 2000, itemsFromIds(15259, 12603, 12605, 11924, 11926, 14499, 14497, 14501, 7158, 15241, 20566, 19780, 18846, 20088)),
    //ICE_PLATEAU(TeleportCategory.WILDERNESS, new Position(2953, 3901, 0), 51, 2000, new Item(18830)),
    //VETION(TeleportCategory.WILDERNESS, new Position(2984, 3633, 0), 2006, 2000, itemsFromIds(15259, 12603, 12605, 11924, 11926, 14499, 14497, 14501, 7158, 20079)),
    //CALLISTO(TeleportCategory.WILDERNESS, new Position(3284, 3839, 0), 2009, 2000, itemsFromIds(15259, 12603, 12605, 11924, 11926, 14499, 14497, 14501, 7158, 20085)),
    //SCORPIA(TeleportCategory.WILDERNESS, new Position(3233, 3945, 0), 2001, 2000, itemsFromIds(15259, 12603, 12605, 11924, 11926, 14499, 14497, 14501, 7158, 20081)),
    //VENENATIS(TeleportCategory.WILDERNESS, new Position(3317, 3760, 0), 2000, 2000, itemsFromIds(15259, 12603, 12605, 11924, 11926, 14499, 14497, 14501, 7158, 20083)),
    //CHAOS_ELEMENTAL(TeleportCategory.WILDERNESS, new Position(3245, 3918, 0), 3200, 2000, itemsFromIds(15259, 12603, 12605, 11924, 11926, 14499, 14497, 14501, 7158, 11995)),

    public final static TeleportData[] values = TeleportData.values();

    private final String name;
    private final TeleportCategory category;
    private final int npcId;
    private final int npcZoom;
    private final Position position;
    private final Item[] items;

    TeleportData(String name, TeleportCategory category, Position position, int npcId, int npcZoom, Item... items) {
        this.name = name == null ? WordUtils.capitalize(this.name().toLowerCase().replaceAll("_", " ")) : name;
        this.category = category;
        this.npcId = npcId;
        this.npcZoom = npcZoom;
        this.position = position;
        this.items = items;
    }

    TeleportData(String name, TeleportCategory category, Position position, Item... items) {
        this(name, category, position, -1, 2000, items);
    }

    TeleportData(TeleportCategory category, Position position, Item... items) {
        this(null, category, position, items);
    }

    TeleportData(TeleportCategory category, Position position, int npcId, Item... items) {
        this(null, category, position, npcId, 2000, items);
    }

    TeleportData(TeleportCategory category, Position position, int npcId, int npcZoom, Item... items) {
        this(null, category, position, npcId, npcZoom, items);
    }

    public Position getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public TeleportCategory getCategory() {
        return category;
    }

    public int getNpcId() {
        return npcId;
    }

    public int getNpcZoom() {
        return npcZoom;
    }

    public Item[] getItems() {
        return items;
    }

    private static Item[] itemsFromIds(int... ids) {
        if (ids.length == 0)
            return null;
        Item[] items = new Item[ids.length];
        for (int i = 0; i < ids.length; i++) {
            items[i] = new Item(ids[i]);
        }
        return items;
    }
}