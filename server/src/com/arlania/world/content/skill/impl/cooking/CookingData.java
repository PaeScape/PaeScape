package com.arlania.world.content.skill.impl.cooking;

import com.arlania.model.Skill;
import com.arlania.util.RandomUtility;
import com.arlania.world.entity.impl.player.Player;

/**
 * Data for the cooking skill.
 *
 * @author Admin Gabriel
 */
public enum CookingData {

    SHRIMP(317, 315, 7954, 1, 50, 15, "shrimp"),
    ANCHOVIES(321, 319, 323, 1, 60, 15, "anchovies"),
    SARDINES(327, 325, 323, 1, 80, 20, "sardines"),
    HERRING(345, 347, 357, 5, 100, 25, "herring"),
    MACKEREL(353, 355, 357, 10, 100, 30, "mackerel"),
    TROUT(335, 333, 343, 15, 140, 35, "trout"),
    COD(341, 339, 343, 18, 150, 38, "cod"),
    SALMON(331, 329, 343, 25, 180, 45, "salmon"),
    TUNA(359, 361, 367, 30, 200, 50, "tuna"),
    LOBSTER(377, 379, 381, 40, 240, 60, "lobster"),
    BASS(363, 365, 367, 43, 260, 63, "bass"),
    SWORDFISH(371, 373, 375, 45, 280, 65, "swordfish"),
    MONKFISH(7944, 7946, 7948, 62, 340, 82, "monkfish"),
    SHARK(383, 385, 387, 80, 400, 94, "shark"),
    MANTA_RAY(389, 391, 393, 91, 500, 99, "manta ray"),
    SEA_TURTLE(395, 397, 399, 94, 540, 101, "sea turtle"),
    ROCKTAIL(15270, 15272, 15274, 96, 600, 103, "rocktail"),
    ANGLERFISH(213439, 213441, 213443, 98, 700, 105, "anglerfish"),

    NOTED_SHRIMP(318, 316, 7954, 1, 50, 15, "noted shrimp"),
    NOTED_ANCHOVIES(322, 320, 323, 1, 60, 15, "noted anchovies"),
    NOTED_SARDINES(328, 326, 323, 1, 80, 20, "noted sardines"),
    NOTED_HERRING(346, 348, 357, 5, 100, 25, "noted herring"),
    NOTED_MACKEREL(354, 356, 357, 10, 100, 30, "noted mackerel"),
    NOTED_TROUT(336, 334, 343, 15, 140, 35, "noted trout"),
    NOTED_COD(342, 340, 343, 18, 150, 38, "noted cod"),
    NOTED_SALMON(332, 330, 343, 25, 180, 45, "noted salmon"),
    NOTED_TUNA(360, 362, 367, 30, 200, 50, "noted tuna"),
    NOTED_LOBSTER(378, 380, 381, 40, 240, 60, "noted lobster"),
    NOTED_BASS(364, 366, 367, 43, 260, 63, "noted bass"),
    NOTED_SWORDFISH(372, 374, 375, 45, 280, 65, "noted swordfish"),
    NOTED_MONKFISH(7945, 7947, 7948, 62, 340, 82, "noted monkfish"),
    NOTED_SHARK(384, 386, 387, 80, 400, 94, "noted shark"),
    NOTED_MANTA_RAY(390, 392, 393, 91, 500, 99, "noted manta ray"),
    NOTED_SEA_TURTLE(396, 398, 399, 94, 540, 101, "noted sea turtle"),
    NOTED_ROCKTAIL(15271, 15273, 15274, 96, 600, 103, "noted rocktail"),
    NOTED_ANGLERFISH(213440, 213442, 213443, 98, 700, 105, "noted anglerfish"),


    HEIM_CRAB(17797, 18159, 18179, 5, 20, 40, "heim crab"),
    RED_EYE(17799, 18161, 18181, 10, 40, 45, "red-eye"),
    DUSK_EEL(17801, 18163, 18183, 12, 60, 47, "dusk eel"),
    GIANT_FLATFISH(17803, 18165, 18185, 15, 80, 50, "giant flatfish"),
    SHORT_FINNED_EEL(17805, 18167, 18187, 100, 114, 54, "short-finned eel"),
    WEB_SNIPPER(17807, 18169, 18189, 30, 120, 60, "web snipper"),
    BOULDABASS(17809, 18171, 18191, 40, 150, 75, "bouldabass"),
    SALVE_EEL(17811, 18173, 18193, 60, 200, 81, "salve eel"),
    BLUE_CRAB(17813, 18175, 18195, 75, 250, 92, "blue crab"),
    ;

    int rawItem, cookedItem, burntItem, levelReq, xp, stopBurn;
    String name;

    CookingData(int rawItem, int cookedItem, int burntItem, int levelReq, int xp, int stopBurn, String name) {
        this.rawItem = rawItem;
        this.cookedItem = cookedItem;
        this.burntItem = burntItem;
        this.levelReq = levelReq;
        this.xp = xp;
        this.stopBurn = stopBurn;
        this.name = name;
    }

    public int getRawItem() {
        return rawItem;
    }

    public int getCookedItem() {
        return cookedItem;
    }

    public int getBurntItem() {
        return burntItem;
    }

    public int getLevelReq() {
        return levelReq;
    }

    public int getXp() {
        return xp;
    }

    public int getStopBurn() {
        return stopBurn;
    }

    public String getName() {
        return name;
    }

    public static CookingData forFish(int fish) {
        for (CookingData data : CookingData.values()) {
            if (data.getRawItem() == fish) {
                return data;
            }
        }
        return null;
    }

    public static final int[] cookingRanges = {12269, 2732, 114, 14919, 300114, 312269, 302732, 314919, 307183, -20497};

    public static boolean isRange(int object) {
        for (int i : cookingRanges)
            if (object == i)
                return true;
        return false;
    }

    /**
     * Get's the rate for burning or successfully cooking food.
     *
     * @param player Player cooking.
     * @param food   Consumables's enum.
     * @return Successfully cook food.
     */
    public static boolean success(Player player, int burnBonus, int levelReq, int stopBurn) {
        if (player.getSkillManager().getCurrentLevel(Skill.COOKING) >= stopBurn) {
            return true;
        }
        double burn_chance = (45.0 - burnBonus);
        double cook_level = player.getSkillManager().getCurrentLevel(Skill.COOKING);
        double lev_needed = levelReq;
        double burn_stop = stopBurn;
        double multi_a = (burn_stop - lev_needed);
        double burn_dec = (burn_chance / multi_a);
        double multi_b = (cook_level - lev_needed);
        burn_chance -= (multi_b * burn_dec);
        double randNum = RandomUtility.RANDOM.nextDouble() * 100.0;
        return burn_chance <= randNum;
    }

    public static boolean canCook(Player player, int id) {
        CookingData fish = forFish(id);
        if (fish == null)
            return false;
        if (player.getSkillManager().getCurrentLevel(Skill.COOKING) < fish.getLevelReq()) {
            player.getPacketSender().sendMessage("You need a Cooking level of atleast " + fish.getLevelReq() + " to cook this.");
            return false;
        }
        if (!player.getInventory().contains(id)) {
            player.getPacketSender().sendMessage("You have run out of fish.");
            return false;
        }
        return true;
    }

}
