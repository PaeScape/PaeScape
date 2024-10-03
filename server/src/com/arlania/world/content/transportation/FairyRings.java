package com.arlania.world.content.transportation;

import com.arlania.model.GameObject;
import com.arlania.model.Position;
import com.arlania.model.Regions;
import com.arlania.world.content.CustomObjects;
import com.arlania.world.entity.impl.player.Player;

import java.util.Objects;

public enum FairyRings {


    MUDSKIPPER("aiq", new Position(2996, 3114, 0), Regions.ASGARNIA),
    ARDOUGNE_ISLAND("air", new Position(2700, 3247, 0), Regions.KANDARIN),
    DORGESH_KAAN_CAVE("ajq", new Position(2735, 5221, 0), Regions.MISTHALIN),
    SLAYER_CAVE("ajr", new Position(2780, 3613, 0), Regions.FREMENNIK),
    //MISCELLANIA_ISLAND("ajs", new Position(2513, 3884, 0), Regions.FREMENNIK),
    PISCATORIS_HUNTER("akq", new Position(2319, 3619, 0), Regions.KANDARIN),
    HOSIDIUS_VINERY("akr", new Position(1822, 3538, 0), Regions.KOUREND),
    FELDIP_HILLS("aks", new Position(2571, 2956, 0), Regions.KANDARIN),
    LIGHTHOUSE("alp", new Position(2503, 3636, 0), Regions.FREMENNIK),
    HAUNTED_WOODS("alq", new Position(3597, 3495, 0), Regions.MORYTANIA),
    ABYSS("alr", new Position(3059, 4875, 0), Regions.MISTHALIN),
    MCGRUBORS_WOOD("als", new Position(2644, 3495, 0), Regions.KANDARIN),
    MORT_MYRE_ISLAND("bip", new Position(3410, 3324, 0), Regions.MORYTANIA),
    KHARIDIAN_DESERT("biq", new Position(3251, 3095, 0), Regions.KHARIDIAN),
    ARDOUGNE_ZOO("bis", new Position(2635, 3266, 0), Regions.KANDARIN),
    ZUL_ANDRA("bjs", new Position(2150, 3070, 0), Regions.TIRANNWN),
    CASTLE_WARS("bkp", new Position(2385, 3035, 0), Regions.KANDARIN),
    MORT_MYRE_SWAMP("bkr", new Position(3469, 3431, 0), Regions.MORYTANIA),
    ZANARIS("bks", new Position(2412, 4434, 0), Regions.MISTHALIN),
    TZHAAR_CITY("blp", new Position(2437, 5126, 0), Regions.KARAMJA),
    LEGENDS_GUILD("blr", new Position(2740, 3351, 0), Regions.KANDARIN),
    MOUNT_QUIDAMORTEM("bls", new Position(1294, 3493, 0), Regions.KOUREND),
    MISCELLANIA("cip", new Position(2513, 3884, 0), Regions.FREMENNIK),
    YANILLE("ciq", new Position(2528, 3127, 0), Regions.KANDARIN),
    MOUNT_KARUULM("cir", new Position(1302, 3762, 0), Regions.KOUREND),
    ARCEUUS("cis", new Position(1636, 3873, 0), Regions.KOUREND),
    SINCLAIR_MANSION("cis", new Position(2705, 3756, 0), Regions.KANDARIN),
    TAI_BWO_WANNAI("ckr", new Position(2801, 3003, 0), Regions.KARAMJA),
    CANIFIS("cks", new Position(3447, 3470, 0), Regions.MORYTANIA),
    APE_ATOLL("clr", new Position(2740, 2738, 0), Regions.KANDARIN),
    HAZELMERE("cls", new Position(2682, 3081, 0), Regions.KANDARIN),
    ABYSSAL_NEXUS("dip", new Position(3037, 4763, 0), Regions.MISTHALIN),
    WIZARD_TOWER("dis", new Position(3108, 3149, 0), Regions.MISTHALIN),
    TOWER_OF_LIFE("djp", new Position(2658, 3230, 0), Regions.KANDARIN),
    CHASM_OF_FIRE("djr", new Position(1455, 3658, 0), Regions.KOUREND),
    KARAMJA("dkp", new Position(2900, 3111, 0), Regions.KARAMJA),
    EDGEVILLE("dkp", new Position(3129, 3496, 0), Regions.MISTHALIN),
    KELDAGRIM_ENTRANCE("dks", new Position(2744, 3719, 0), Regions.FREMENNIK),
    NARDAH("dlq", new Position(3423, 3016, 0), Regions.KHARIDIAN),



    ;


    FairyRings(String fairyCode, Position telePosition, Regions region) {
        this.fairyCode = fairyCode;
        this.telePosition = telePosition;
        this.region = region;
    }

    private final String fairyCode;
    private final Position telePosition;
    private final Regions region;


    public static void load() {
        for (FairyRings fairyRings : FairyRings.values()) {
            CustomObjects.spawnGlobalObject(new GameObject(311761, fairyRings.telePosition));
        }
    }

    public static void teleport(final Player player, final FairyRings fairyRings) {
        TeleportHandler.teleportPlayer(player, fairyRings.telePosition, TeleportType.RING_TELE);
    }

    public static void forFairyCode(Player player, String fairyCode) {

        for (FairyRings fairyRings : FairyRings.values()) {
            if (Objects.equals(fairyRings.fairyCode, fairyCode)) {
                teleport(player, fairyRings);
                return;
            }
        }

        player.getPacketSender().sendMessage("That Fairy Ring Code does not exist.");
    }
}