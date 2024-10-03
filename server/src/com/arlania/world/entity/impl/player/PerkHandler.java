package com.arlania.world.entity.impl.player;

import com.arlania.model.Locations.Location;

public class PerkHandler {

    public static boolean canUseNormalPerks(Player player) {

        if (player.getInteractingEntity() != null && player.getInteractingEntity().isPlayer())
            return false;

        return player.getLocation() != Location.WILDERNESS;
    }

    public static boolean canUseMasteryPerks(Player player) {

        if (player.getInteractingEntity() != null && player.getInteractingEntity().isPlayer())
            return false;

        return player.getLocation() != Location.WILDERNESS;
    }


    public static boolean canUseWildernessPerks(Player player) {

        if (player.getLocation() == Location.WILDERNESS) {
            return player.getInteractingEntity() == null || !player.getInteractingEntity().isPlayer();
        }
        return false;
    }


    public static boolean canUseRaidPerks(Player player) {

        if (player.getInteractingEntity() != null && player.getInteractingEntity().isPlayer())
            return false;

        if (player.getLocation() == Location.WILDERNESS)
            return false;

        return player.inRaid(player);
    }

    public static boolean canUseEquipmentUpgrades(Player player) {

        if (player.getInteractingEntity() != null && player.getInteractingEntity().isPlayer())
            return false;

        return player.getLocation() != Location.WILDERNESS;
    }


}
