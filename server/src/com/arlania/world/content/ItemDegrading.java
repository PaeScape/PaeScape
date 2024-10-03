package com.arlania.world.content;

import com.arlania.model.Flag;
import com.arlania.model.Item;
import com.arlania.model.container.impl.Equipment;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.world.entity.impl.player.Player;

public class ItemDegrading {

    public static boolean handleItemDegrading(Player p, DegradingItem d) {
        int equipId = p.getEquipment().getItems()[d.equipSlot].getId();
        if (equipId == d.nonDeg || equipId == d.deg) {
            int maxCharges = d.degradingCharges;
            int currentCharges = getAndIncrementCharge(p, d, false);
            boolean degradeCompletely = currentCharges >= maxCharges;
            if (equipId == d.deg && !degradeCompletely) {
                return true;
            }
            degradeCompletely = degradeCompletely && equipId == d.deg;
            p.getEquipment().setItem(d.equipSlot, new Item(degradeCompletely ? -1 : d.deg)).refreshItems();
            getAndIncrementCharge(p, d, true);
            p.getUpdateFlag().flag(Flag.APPEARANCE);
            String ext = !degradeCompletely ? "degraded slightly" : "turned into dust";
            p.getPacketSender().sendMessage("Your " + ItemDefinition.forId(equipId).getName().replace(" (deg)", "") + " has " + ext + "!");
            return true;
        } else {
            return false;
        }
    }

    public static int getAndIncrementCharge(Player p, DegradingItem d, boolean reset) {
        switch (d) {
            case BRAWLING_GLOVES_COOKING:
            case BRAWLING_GLOVES_FIREMAKING:
            case BRAWLING_GLOVES_FISHING:
            case BRAWLING_GLOVES_HUNTER:
            case BRAWLING_GLOVES_MINING:
            case BRAWLING_GLOVES_PRAYER:
            case BRAWLING_GLOVES_SMITHING:
            case BRAWLING_GLOVES_THIEVING:
            case BRAWLING_GLOVES_WOODCUTTING:
                int index = d.ordinal() - 1;
                if (reset) {
                    return p.getBrawlerChargers()[index] = 0;
                } else {
                    return p.getBrawlerChargers()[index]++;
                }
            case RING_OF_RECOIL:
                if (reset) {
                    return p.setRecoilCharges(0);
                } else {
                    return p.setRecoilCharges(p.getRecoilCharges() + 1);
                }
            case CORRUPT_STATIUS_FULL_HELM:
            case CORRUPT_STATIUS_PLATEBODY:
            case CORRUPT_STATIUS_PLATELEGS:
            case CORRUPT_STATIUS_WARHAMMER:
            case CORRUPT_VESTAS_CHAINBODY:
            case CORRUPT_VESTAS_PLATESKIRT:
            case CORRUPT_VESTAS_LONGSWORD:
            case CORRUPT_VESTAS_SPEAR:
            case CORRUPT_ZURIELS_HOOD:
            case CORRUPT_ZURIELS_ROBE_TOP:
            case CORRUPT_ZURIELS_ROBE_BOTTOM:
            case CORRUPT_ZURIELS_STAFF:
            case CORRUPT_MORRIGANS_COIF:
            case CORRUPT_MORRIGANS_LEATHER_BODY:
            case CORRUPT_MORRIGANS_LEATHER_CHAPS:
                int indexTwo = d.ordinal() - 11;
                if (reset) {
                    return p.getPvpEquipmentChargers()[indexTwo] = 0;
                } else {
                    return p.getPvpEquipmentChargers()[indexTwo]++;
                }
        }
        return d.degradingCharges;
    }

    public static void degradeInCombat(Player player, int itemId) {
        switch (itemId) {

            case 13920:
                handleItemDegrading(player, DegradingItem.CORRUPT_STATIUS_FULL_HELM);
            case 13908:
                handleItemDegrading(player, DegradingItem.CORRUPT_STATIUS_PLATEBODY);
            case 13914:
                handleItemDegrading(player, DegradingItem.CORRUPT_STATIUS_PLATELEGS);
            case 13926:
                handleItemDegrading(player, DegradingItem.CORRUPT_STATIUS_WARHAMMER);
            case 13911:
                handleItemDegrading(player, DegradingItem.CORRUPT_VESTAS_CHAINBODY);
            case 13917:
                handleItemDegrading(player, DegradingItem.CORRUPT_VESTAS_PLATESKIRT);
            case 13923:
                handleItemDegrading(player, DegradingItem.CORRUPT_VESTAS_LONGSWORD);
            case 13929:
                handleItemDegrading(player, DegradingItem.CORRUPT_VESTAS_SPEAR);
            case 13938:
                handleItemDegrading(player, DegradingItem.CORRUPT_ZURIELS_HOOD);
            case 13932:
                handleItemDegrading(player, DegradingItem.CORRUPT_ZURIELS_ROBE_TOP);
            case 13935:
                handleItemDegrading(player, DegradingItem.CORRUPT_ZURIELS_ROBE_BOTTOM);
            case 13941:
                handleItemDegrading(player, DegradingItem.CORRUPT_ZURIELS_STAFF);
            case 13950:
                handleItemDegrading(player, DegradingItem.CORRUPT_MORRIGANS_COIF);
            case 13944:
                handleItemDegrading(player, DegradingItem.CORRUPT_MORRIGANS_LEATHER_BODY);
            case 13947:
                handleItemDegrading(player, DegradingItem.CORRUPT_MORRIGANS_LEATHER_CHAPS);


        }
    }

    /*
     * The enum holding all degradeable equipment
     */
    public enum DegradingItem {

        /*
         * Recoil
         */
        RING_OF_RECOIL(2550, 2550, Equipment.RING_SLOT, 100),
        /*
         * Brawling gloves
         */
        BRAWLING_GLOVES_SMITHING(13855, 13855, Equipment.HANDS_SLOT, 600),
        BRAWLING_GLOVES_PRAYER(13848, 13848, Equipment.HANDS_SLOT, 600),
        BRAWLING_GLOVES_COOKING(13857, 13857, Equipment.HANDS_SLOT, 600),
        BRAWLING_GLOVES_FISHING(13856, 13856, Equipment.HANDS_SLOT, 600),
        BRAWLING_GLOVES_THIEVING(13854, 13854, Equipment.HANDS_SLOT, 600),
        BRAWLING_GLOVES_HUNTER(13853, 13853, Equipment.HANDS_SLOT, 600),
        BRAWLING_GLOVES_MINING(13852, 13852, Equipment.HANDS_SLOT, 600),
        BRAWLING_GLOVES_FIREMAKING(13851, 13851, Equipment.HANDS_SLOT, 600),
        BRAWLING_GLOVES_WOODCUTTING(13850, 13850, Equipment.HANDS_SLOT, 600),

        /*
         * PvP Equipment
         */

        CORRUPT_STATIUS_FULL_HELM(13920, 13920, Equipment.HEAD_SLOT, 1000),
        CORRUPT_STATIUS_PLATEBODY(13908, 13908, Equipment.BODY_SLOT, 1000),
        CORRUPT_STATIUS_PLATELEGS(13914, 13914, Equipment.LEG_SLOT, 1000),
        CORRUPT_STATIUS_WARHAMMER(13926, 13926, Equipment.WEAPON_SLOT, 1000),

        CORRUPT_VESTAS_CHAINBODY(13911, 13911, Equipment.BODY_SLOT, 1000),
        CORRUPT_VESTAS_PLATESKIRT(13917, 13917, Equipment.LEG_SLOT, 1000),
        CORRUPT_VESTAS_LONGSWORD(13923, 13923, Equipment.WEAPON_SLOT, 1000),
        CORRUPT_VESTAS_SPEAR(13929, 13929, Equipment.WEAPON_SLOT, 1000),

        CORRUPT_ZURIELS_HOOD(13938, 13938, Equipment.HEAD_SLOT, 1000),
        CORRUPT_ZURIELS_ROBE_TOP(13932, 13932, Equipment.BODY_SLOT, 1000),
        CORRUPT_ZURIELS_ROBE_BOTTOM(13935, 13935, Equipment.LEG_SLOT, 1000),
        CORRUPT_ZURIELS_STAFF(13941, 13941, Equipment.WEAPON_SLOT, 1000),

        CORRUPT_MORRIGANS_COIF(13950, 13950, Equipment.HEAD_SLOT, 1000),
        CORRUPT_MORRIGANS_LEATHER_BODY(13944, 13944, Equipment.BODY_SLOT, 1000),
        CORRUPT_MORRIGANS_LEATHER_CHAPS(13947, 13947, Equipment.LEG_SLOT, 1000);


        DegradingItem(int nonDeg, int deg, int equipSlot, int degradingCharges) {
            this.nonDeg = nonDeg;
            this.deg = deg;
            this.equipSlot = equipSlot;
            this.degradingCharges = degradingCharges;
        }

        private final int nonDeg;
        private final int deg;
        private final int equipSlot;
        private final int degradingCharges;

        public static DegradingItem forNonDeg(int item) {
            for (DegradingItem d : DegradingItem.values()) {
                if (d.nonDeg == item) {
                    return d;
                }
            }
            return null;
        }
    }
}






/*
		CORRUPT_STATIUS_FULL_HELM(13920, 13922, Equipment.HEAD_SLOT, 1000),
		CORRUPT_STATIUS_PLATEBODY(13908, 13910, Equipment.BODY_SLOT, 1000),
		CORRUPT_STATIUS_PLATELEGS(13914, 13916, Equipment.LEG_SLOT, 1000),
		CORRUPT_STATIUS_WARHAMMER(13926, 13928, Equipment.WEAPON_SLOT, 1000),

		CORRUPT_VESTAS_CHAINBODY(13911, 13913, Equipment.BODY_SLOT, 1000),
		CORRUPT_VESTAS_PLATESKIRT(13917, 13919, Equipment.LEG_SLOT, 1000),
		CORRUPT_VESTAS_LONGSWORD(13923, 13925, Equipment.WEAPON_SLOT, 1000),
		CORRUPT_VESTAS_SPEAR(13929, 13931, Equipment.WEAPON_SLOT, 1000),

		CORRUPT_ZURIELS_HOOD(13938, 13940, Equipment.HEAD_SLOT, 1000),
		CORRUPT_ZURIELS_ROBE_TOP(13932, 13934, Equipment.BODY_SLOT, 1000),
		CORRUPT_ZURIELS_ROBE_BOTTOM(13935, 13937, Equipment.LEG_SLOT, 1000),
		CORRUPT_ZURIELS_STAFF(13941, 13943, Equipment.WEAPON_SLOT, 1000),

		CORRUPT_MORRIGANS_COIF(13950, 13952, Equipment.HEAD_SLOT, 1000),
		CORRUPT_MORRIGANS_LEATHER_BODY(13944, 13946, Equipment.BODY_SLOT, 1000),
		CORRUPT_MORRIGANS_LEATHER_CHAPS(13947, 13949, Equipment.LEG_SLOT, 1000);



*/