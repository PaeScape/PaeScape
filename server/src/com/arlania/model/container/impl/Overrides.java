package com.arlania.model.container.impl;

import com.arlania.model.container.ItemContainer;
import com.arlania.model.container.StackType;
import com.arlania.world.entity.impl.player.Player;

/**
 * Represents a player's equipment item container.
 *
 * @author relex lawl
 */

public class Overrides extends ItemContainer {

    /**
     * The Equipment constructor.
     *
     * @param player The player who's equipment is being represented.
     */
    public Overrides(Player player) {
        super(player);
    }

    @Override
    public int capacity() {
        return 14;
    }

    @Override
    public StackType stackType() {
        return StackType.DEFAULT;
    }

    @Override
    public ItemContainer refreshItems() {
        getPlayer().getPacketSender().sendItemContainer(this, INVENTORY_INTERFACE_ID);
        return this;
    }

    @Override
    public Overrides full() {
        return this;
    }

    /**
     * The equipment inventory interface id.
     */
    public static final int INVENTORY_INTERFACE_ID = 1688;
    public static final int HEAD_SLOT = 0;
    public static final int CAPE_SLOT = 1;
    public static final int AMULET_SLOT = 2;
    public static final int WEAPON_SLOT = 3;
    public static final int BODY_SLOT = 4;
    public static final int SHIELD_SLOT = 5;
    public static final int LEG_SLOT = 7;
    public static final int HANDS_SLOT = 9;
    public static final int FEET_SLOT = 10;
    public static final int RING_SLOT = 12;
    public static final int AMMUNITION_SLOT = 13;


}
