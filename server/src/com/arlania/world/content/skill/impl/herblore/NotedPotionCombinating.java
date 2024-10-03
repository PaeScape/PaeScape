package com.arlania.world.content.skill.impl.herblore;

import com.arlania.world.entity.impl.player.Player;

/**
 * Combinates potions into doses
 *
 * @author Gabriel Hannason
 */
public class NotedPotionCombinating {

    public static void combinePotion(Player p, int firstPotID, int secondPotID) {
        CombiningDoses potion = CombiningDoses.getPotionByID(firstPotID);
        if (potion == null || !p.getInventory().contains(firstPotID) || !p.getInventory().contains(secondPotID))
            return;
        if (potion.getDoseForID(secondPotID) > 0) {
            int firstPotAmount = potion.getDoseForID(firstPotID);
            int secondPotAmount = potion.getDoseForID(secondPotID);
            if (firstPotAmount + secondPotAmount <= 4) {
                p.getInventory().delete(firstPotID, 1);
                p.getInventory().delete(secondPotID, 1);
                p.getInventory().add(potion.getIDForDose(firstPotAmount + secondPotAmount), 1);
                p.getInventory().add(EMPTY_VIAL, 1);
            } else {
                int overflow = (firstPotAmount + secondPotAmount) - 4;
                p.getInventory().delete(firstPotID, 1);
                p.getInventory().delete(secondPotID, 1);
                p.getInventory().add(potion.getIDForDose(4), 1);
                p.getInventory().add(potion.getIDForDose(overflow), 1);
            }
        }
    }

    private static final int VIAL = 228;
    private static final int EMPTY_VIAL = 230;

    public enum CombiningDoses {

        STRENGTH(120, 118, 116, 114, VIAL, "Strength"),
        SUPER_STRENGTH(162, 160, 158, 2441, VIAL, "Super strength"),
        ATTACK(126, 124, 122, 2429, VIAL, "Attack"),
        SUPER_ATTACK(150, 148, 146, 2437, VIAL, "Super attack"),
        DEFENCE(138, 136, 134, 2433, VIAL, "Defence"),
        SUPER_DEFENCE(168, 166, 164, 2443, VIAL, "Super defence"),
        RANGING_POTION(174, 172, 170, 2445, VIAL, "Ranging"),
        FISHING(156, 154, 152, 2439, VIAL, "Fishing"),
        PRAYER(144, 142, 140, 2435, VIAL, "Prayer"),
        ANTIFIRE(2459, 2457, 2455, 2453, VIAL, "Antifire"),
        ZAMORAK_BREW(194, 192, 190, 2451, VIAL, "Zamorakian brew"),
        ANTIPOISON(180, 178, 176, 2447, VIAL, "Antipoison"),
        RESTORE(132, 130, 128, 2431, VIAL, "Restoration"),
        MAGIC_POTION(3047, 3045, 3043, 3041, VIAL, "Magic"),
        SUPER_RESTORE(3031, 3029, 3027, 3025, VIAL, "Super Restoration"),
        ENERGY(3015, 3013, 3011, 3009, VIAL, "Energy"),
        SUPER_ENERGY(3023, 3021, 3019, 3017, VIAL, "Super Energy"),
        AGILITY(3039, 3037, 3035, 3033, VIAL, "Agility"),
        SARADOMIN_BREW(6692, 6690, 6688, 6686, VIAL, "Saradomin brew"),
        ANTIPOISON1(5950, 5949, 5946, 5944, VIAL, "Antipoison(+)"),
        ANTIPOISON2(5959, 5957, 5955, 5953, VIAL, "Antipoison(++)"),
        SUPER_ANTIPOISON(186, 184, 182, 2449, VIAL, "Super Antipoison"),
        RELICYMS_BALM(4849, 4847, 4845, 4843, VIAL, "Relicym's balm"),
        SERUM_207(3415, 3413, 3411, 3409, VIAL, "Serum 207"),
        COMBAT(9746, 9744, 9742, 9740, VIAL, "Combat"),
        EXTR_RANGE(15327, 15326, 15325, 15324, VIAL, "Extreme ranging"),
        EXTR_STR(15315, 15314, 15313, 15312, VIAL, "Extreme stength"),
        EXTR_MAGE(15323, 15322, 15321, 15320, VIAL, "Extreme magic"),
        EXTR_ATK(15311, 15310, 15309, 15308, VIAL, "Extreme attack"),
        EXTR_DEF(15319, 15318, 15317, 15316, VIAL, "Extreme defence"),
        SUPER_PRAYER(15331, 15330, 15329, 15328, VIAL, "Super prayer"),
        OVERLOAD(15335, 15334, 15333, 15332, VIAL, "Overload"),
        SUPER_FIRE(15307, 15306, 15305, 15304, VIAL, "Super antifire"),
        REC_SPEC(15303, 15302, 15301, 15300, VIAL, "Recover special");

        /*
         * This is what the data in the above enumeration is, in order. EX:
         * COMBAT(oneDosePotionID, twoDosePotionID, threeDosePotionID,
         * fourDosePotionID, vial, "potionName");
         */

        int oneDosePotionID, twoDosePotionID, threeDosePotionID,
                fourDosePotionID, vial;
        String potionName;

        /**
         * @param oneDosePotionID   - This is the ID for the potion when it contains one dose.
         * @param twoDosePotionID   - This is the ID for the potion when it contains two
         *                          doses.
         * @param threeDosePotionID - This is the ID for the potion when it contains three
         *                          doses.
         * @param fourDosePotionID  - This is the ID for the (full) potion when it contains
         *                          four doses.
         * @param vial              - This is referenced from: private static final int VIAL =
         *                          227; It's a constant and its value never changes.
         * @param potionName        - This is a string which is used to set a name for the
         *                          potion. Within an enumeration you can use the name().
         *                          method to take the name in-front of the stored data. This
         *                          however could not be done because of some potion names.
         */

        CombiningDoses(int oneDosePotionID, int twoDosePotionID,
                       int threeDosePotionID, int fourDosePotionID, int vial,
                       String potionName) {
            this.oneDosePotionID = oneDosePotionID;
            this.twoDosePotionID = twoDosePotionID;
            this.threeDosePotionID = threeDosePotionID;
            this.fourDosePotionID = fourDosePotionID;
            this.vial = vial;
            this.potionName = potionName;
        }

        /*
         * These are code getters to use the data stored in the above
         * enumeration.
         */

        public int getDoseID1() {
            return oneDosePotionID;
        }

        public int getDoseID2() {
            return twoDosePotionID;
        }

        public int getDoseID3() {
            return threeDosePotionID;
        }

        public int getFourDosePotionID() {
            return fourDosePotionID;
        }

        public int getVial() {
            return vial;
        }

        public String getPotionName() {
            return potionName;
        }

        /**
         * @param id
         * @return The dose that this id represents for this potion, or -1 if it
         * doesn't belong to this potion.
         * @date 2/28/12
         * @author 0021sordna
         */

        public int getDoseForID(int id) {
            if (id == this.oneDosePotionID) {
                return 1;
            }
            if (id == this.twoDosePotionID) {
                return 2;
            }
            if (id == this.threeDosePotionID) {
                return 3;
            }
            if (id == this.fourDosePotionID) {
                return 4;
            }
            return -1;
        }

        /**
         * @param dose
         * @return The ID for this dose of the potion, or -1 if this dose
         * doesn't exist.
         * @date 2/28/12
         * @author 0021sordna
         */

        public int getIDForDose(int dose) {
            if (dose == 1) {
                return this.oneDosePotionID;
            }
            if (dose == 2) {
                return this.twoDosePotionID;
            }
            if (dose == 3) {
                return this.threeDosePotionID;
            }
            if (dose == 4) {
                return this.fourDosePotionID;
            }
            if (dose == 0) {
                return EMPTY_VIAL;
            }
            return -1;
        }

        /**
         * @param ID
         * @return The potion that matches the ID. ID can be any dose of the
         * potion.
         * @date 2/28/12
         * @author 0021sordna
         */

        public static CombiningDoses getPotionByID(int id) {
            for (CombiningDoses potion : CombiningDoses.values()) {
                if (id == potion.oneDosePotionID) {
                    return potion;
                }
                if (id == potion.twoDosePotionID) {
                    return potion;
                }
                if (id == potion.threeDosePotionID) {
                    return potion;
                }
                if (id == potion.fourDosePotionID) {
                    return potion;
                }
            }
            return null;
        }
    }
}
