package com.arlania.world.content.skill.impl.skiller;

import com.arlania.model.Skill;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.entity.impl.player.Player;


public enum SkillerTasks {

    // 1 - 7
    NO_TASK(Skill.WOODCUTTING, "gather", 0, 1, new int[]{-69}, 0),
    CHOP_NORMAL_LOGS(Skill.WOODCUTTING, "gather", 10, 1, new int[]{301276, 301278, 301279, 310041, 314309, 314308}, 40),
    CHOP_OAK_LOGS(Skill.WOODCUTTING, "gather", 30, 15, new int[]{301751, 301281, 303037, 310820}, 60),
    CHOP_WILLOW_LOGS(Skill.WOODCUTTING, "gather", 60, 30, new int[]{1308, 5551, 5552, 5553, 310819, 301756, 310829, 310833, 310831}, 80),
    CHOP_MAPLE_LOGS(Skill.WOODCUTTING, "gather", 90, 45, new int[]{1307, 4677, 310832}, 100),
    CHOP_YEW_LOGS(Skill.WOODCUTTING, "gather", 120, 60, new int[]{1309, 10822, 310822}, 150),
    CHOP_MAGIC_LOGS(Skill.WOODCUTTING, "gather", 150, 75, new int[]{1306, 10834, 310834}, 200),
    CHOP_REDWOOD_LOGS(Skill.WOODCUTTING, "gather", 180, 90, new int[]{329668, 329670}, 250),

    // 9 - 16
    NO_TASK_MINING(Skill.MINING, "gather", 0, 1, new int[]{-69}, 0),
    MINE_COPPER_ORE(Skill.MINING, "gather", 10, 1, new int[]{9708, 9709, 9710, 11936, 11960, 11961, 11962, 11189, 11190, 11191, 29231, 29230, 2090, 311161, 310943}, 20),
    MINE_TIN_ORE(Skill.MINING, "gather", 10, 1, new int[]{9714, 9715, 9716, 11933, 11957, 11958, 11959, 11186, 11187, 11188, 2094, 29227, 29229, 311360, 311361}, 20),
    MINE_IRON_ORE(Skill.MINING, "gather", 30, 15, new int[]{9717, 9718, 9719, 2093, 2092, 11954, 11955, 11956, 29221, 29222, 29223, 311364, 311365}, 30),
    MINE_COAL(Skill.MINING, "gather", 60, 30, new int[]{2096, 2097, 5770, 29216, 29215, 29217, 11965, 11964, 11963, 11930, 11931, 11932, 311366, 311367}, 50),
    MINE_GOLD_ORE(Skill.MINING, "gather", 80, 40, new int[]{9720, 9721, 9722, 11951, 11183, 11184, 11185, 2099, 311370, 311371}, 60),
    MINE_MITHRIL_ORE(Skill.MINING, "gather", 110, 55, new int[]{2102, 2103, 25370, 25368, 5786, 5784, 11942, 11943, 11944, 11945, 11946, 29236, 11947, 311372, 311373}, 70),
    MINE_ADMANTITE_ORE(Skill.MINING, "gather", 140, 70, new int[]{2105, 11941, 11939, 29233, 29235, 311374, 311375}, 85),
    MINE_RUNITE_ORE(Skill.MINING, "gather", 170, 85, new int[]{14860, 14859, 4860, 2106, 2107, 311376, 311377}, 100),

    // 18 - 26
    NO_TASK_FISHING(Skill.FISHING, "gather", 0, 1, new int[]{-69}, 0),
    FISH_SHRIMP(Skill.FISHING, "gather", 10, 1, new int[]{-1}, 25),
    FISH_TROUT(Skill.FISHING, "gather", 40, 20, new int[]{-1}, 37),
    FISH_SALMON(Skill.FISHING, "gather", 60, 30, new int[]{-1}, 50),
    FISH_TUNA(Skill.FISHING, "gather", 70, 35, new int[]{-1}, 75),
    FISH_LOBSTER(Skill.FISHING, "gather", 80, 40, new int[]{-1}, 100),
    FISH_SWORDFISH(Skill.FISHING, "gather", 100, 50, new int[]{-1}, 150),
    FISH_MONKFISH(Skill.FISHING, "gather", 124, 62, new int[]{-1}, 200),
    FISH_SHARK(Skill.FISHING, "gather", 152, 76, new int[]{-1}, 250),
    FISH_ROCKTAIL(Skill.FISHING, "gather", 202, 91, new int[]{-1}, 300),

    // 28 - 33
    NO_TASK_THIEVING(Skill.THIEVING, "gather", 0, 1, new int[]{-69}, 0),
    PICKPOCKET_MAN(Skill.THIEVING, "gather", 10, 1, new int[]{2}, 40),
    PICKPOCKET_MASTER_FARMER(Skill.THIEVING, "gather", 76, 38, new int[]{2234}, 80),
    PICKPOCKET_ARDY_KNIGHT(Skill.THIEVING, "gather", 100, 50, new int[]{23}, 100),
    PICKPOCKET_HERO(Skill.THIEVING, "gather", 130, 65, new int[]{21}, 150),
    PICKPOCKET_TZHAAR(Skill.THIEVING, "gather", 160, 70, new int[]{2595}, 200),
    PICKPOCKET_VYREWATCH(Skill.THIEVING, "gather", 200, 90, new int[]{23698}, 250),

    // 35 - 39
    NO_TASK_DG(Skill.DUNGEONEERING, "gather", 0, 1, new int[]{-69}, 0),
    BARROWS_MINIGAME(Skill.DUNGEONEERING, "barrowsmini", 2000, 1, new int[]{-1}, 4),
    BARROWS_RAIDS(Skill.DUNGEONEERING, "barrowsraid", 5000, 20, new int[]{-1}, 4),
    GWD_RAIDS(Skill.DUNGEONEERING, "gwd", 10000, 40, new int[]{-1}, 4),
    CHAMBERS_OF_XERIC(Skill.DUNGEONEERING, "cox", 20000, 60, new int[]{-1}, 4),
    THEATRE_OF_BLOOD(Skill.DUNGEONEERING, "tob", 30000, 80, new int[]{-1}, 4),

    // 41 - 55
    NO_TASK_HUNTER(Skill.HUNTER, "gather", 0, 1, new int[]{-69}, 0),
    BABY_IMPLING(Skill.HUNTER, "gather", 25, 1, new int[]{6055}, 20),
    YOUNG_IMPLING(Skill.HUNTER, "gather", 50, 14, new int[]{6056}, 25),
    GOURMET_IMPLING(Skill.HUNTER, "gather", 75, 26, new int[]{6057}, 30),
    EARTH_IMPLING(Skill.HUNTER, "gather", 100, 33, new int[]{6058}, 35),
    ESSENCE_IMPLING(Skill.HUNTER, "gather", 125, 40, new int[]{6059}, 40),
    ECLECTIC_IMPLING(Skill.HUNTER, "gather", 150, 50, new int[]{6060}, 45),
    NATURE_IMPLING(Skill.HUNTER, "gather", 200, 58, new int[]{6061}, 50),
    MAGPIE_IMPLING(Skill.HUNTER, "gather", 250, 65, new int[]{6062}, 60),
    NINJA_IMPLING(Skill.HUNTER, "gather", 300, 74, new int[]{6063}, 70),
    DRAGON_IMPLING(Skill.HUNTER, "gather", 350, 83, new int[]{6064}, 80),
    KINGLY_IMPLING(Skill.HUNTER, "gather", 400, 91, new int[]{7903}, 100),
    SPEAR_KYATT(Skill.HUNTER, "gather", 200, 80, new int[]{5103}, 200),
    SPEAR_GRAAHK(Skill.HUNTER, "gather", 120, 60, new int[]{5105}, 150),
    SPEAR_LARUPIA(Skill.HUNTER, "gather", 80, 40, new int[]{5104}, 120),
    SPEAR_HERBIBOAR(Skill.HUNTER, "gather", 180, 90, new int[]{21785}, 300),

    // 57+

    NO_TASK_WILD(Skill.WOODCUTTING, "wild", 0, -1, new int[]{-69}, 0),
    MAKE_WRATH_RUNES(Skill.RUNECRAFTING, "wild", 250, 90, new int[]{221880}, 250),
    MINE_AMETHYST(Skill.MINING, "wild", 250, 90, new int[]{315250}, 150),
    FISH_ANGLERFISH(Skill.FISHING, "wild", 250, 95, new int[]{-1}, 120),
    COOK_ANGLERFISH(Skill.COOKING, "wild", 250, 98, new int[]{213439}, 120)

    //craft
    //RC
    //Fletching
    //FM
    //Cooking
    //Smithing
    //Farming
    //Herblore
    //Agility


    ;


    SkillerTasks(Skill skill, String Type, int XP, int reqLvl, int[] objId, int taskAmount) {
        this.skill = skill;
        this.Type = Type;
        this.XP = XP;
        this.reqLvl = reqLvl;
        this.objId = objId;
        this.taskAmount = taskAmount;
    }

    private final Skill skill;
    private final String Type;
    private final int XP;
    private final int reqLvl;
    private final int[] objId;
    private final int taskAmount;


    public Skill getSkill() {
        return this.skill;
    }

    public String getType() {
        return this.Type;
    }

    public int getXP() {
        return this.XP;
    }

    public int getreqLvl() {
        return this.reqLvl;
    }

    public int[] getObjId() {
        return this.objId;
    }

    public int getTaskAmount() {
        return this.taskAmount;
    }

    public static SkillerTasks forId(int id) {
        for (SkillerTasks tasks : SkillerTasks.values()) {
            if (tasks.ordinal() == id) {
                return tasks;
            }
        }
        return null;
    }

    public static int[] getNewTaskData(Player player, boolean wilderness) {
        int skillerTaskId = 1, skillerTaskAmount = 20;
        int totalTasks = 0;

        //Calculating amount of tasks
        for (SkillerTasks task : SkillerTasks.values()) {
            if (task.getType() != "wild")
                totalTasks++;
        }

        skillerTaskId = 1 + RandomUtility.inclusiveRandom(totalTasks - 1);

        if (skillerTaskId >= totalTasks)
            skillerTaskId = totalTasks - 1;

        if (skillerTaskId == 0)
            skillerTaskId = 1;

        if (wilderness)
            skillerTaskId = 57 + RandomUtility.inclusiveRandom(0, 3);

        if (skillerTaskId > 60)
            skillerTaskId = 60;

        if (SkillerTasks.forId(skillerTaskId).getObjId()[0] == -69)
            skillerTaskId += 1;


        int taskQuantity = SkillerTasks.forId(skillerTaskId).getTaskAmount();

        if (!wilderness)
            taskQuantity = (taskQuantity) * (1 + (player.Accelerate / 2));

        else
            taskQuantity = (taskQuantity) * (1 + (player.wildAccelerate / 2));

        if (player.artisanQty == "max")
            skillerTaskAmount = taskQuantity;
        else if (player.artisanQty == "min")
            skillerTaskAmount = taskQuantity / 2;
        else
            skillerTaskAmount = RandomUtility.inclusiveRandom(taskQuantity / 2, taskQuantity);


        return new int[]{skillerTaskId, skillerTaskAmount};
    }

    public static int[] getNewArtisanTaskData(int skillerTaskId, Player player) {
        int skillerTaskAmount = 20;

        int taskQuantity = SkillerTasks.forId(skillerTaskId).getTaskAmount();

        taskQuantity = (taskQuantity) * (1 + (player.Accelerate / 2));

        if (player.artisanQty == "max")
            skillerTaskAmount = taskQuantity;
        else if (player.artisanQty == "min")
            skillerTaskAmount = taskQuantity / 2;
        else
            skillerTaskAmount = RandomUtility.inclusiveRandom(taskQuantity / 2, taskQuantity);

        return new int[]{skillerTaskId, skillerTaskAmount};
    }

    @Override
    public String toString() {
        return Misc.ucFirst(name().toLowerCase().replaceAll("_", " "));
    }
}
