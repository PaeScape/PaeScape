package com.arlania.world.content;

import com.arlania.model.Item;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.world.entity.impl.player.Player;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;

/**
 * Handles the action of loading darts into the Toxic Blowpipe item from OSRS.
 *
 * @author Levi <rune-server.org/members/auguryps>
 */
public class BlowpipeLoading {

    /**
     * The player participating in this action.
     */
    private final Player player;

    /**
     * Constructs a new instance of this class which assigns {@link #player} to
     * the parameterized value.
     *
     * @param player The player participating in this action.
     */
    public BlowpipeLoading(Player player) {
        this.player = player;
    }

    /**
     * An {@link ImmutableSet} which specifies which {@link Item#getId}'s are
     * considered loadable darts.
     */
    public static final ImmutableSet<Integer> LOADABLE_DARTS = ImmutableSet.of(806, 807, 808, 809, 810, 811,
            11230);

    /**
     * An {@link Multiset} of {@link Item}'s which handles the contents of the
     * Toxic Blowpipe.
     */
    private final Multiset<Integer> blowpipeContents = HashMultiset.create();

    /**
     * Returns {@link #blowpipeContents} for public(global) use.
     *
     * @return {@link #blowpipeContents}.
     */
    public Multiset<Integer> getContents() {
        return blowpipeContents;
    }

    /**
     * Handles the action of loading darts into the Toxic Blowpipe.
     */
    public void handleLoadBlowpipe(Item item) {
        if (LOADABLE_DARTS.contains(item.getId())) {
            for (int dart : blowpipeContents) {
                if (item.getId() != dart) {
                    player.getPacketSender().sendMessage("There are already darts loaded in your blowpipe.");
                    return;
                }
            }
            blowpipeContents.setCount(item.getId(), blowpipeContents.count(item.getId()) + item.getAmount());
            player.getPacketSender().sendMessage("Darts: <col=329500>" + ItemDefinition.forId(item.getId()).getName()
                    + " x " + blowpipeContents.count(item.getId()) + "</col>.");
            player.getInventory().delete(item);
        }
    }

    /**
     * Handles the action of checking the contents of the Toxic Blowpipe.
     */
    public void handleCheckBlowpipe() {
        if (blowpipeContents.isEmpty()) {
            player.getPacketSender().sendMessage("The blowpipe has no darts in it.");
            return;
        }

        for (Entry<Integer> dart : blowpipeContents.entrySet()) {
            player.getPacketSender()
                    .sendMessage("Darts: <col=329500>" + ItemDefinition.forId(dart.getElement()).getName() + " x "
                            + blowpipeContents.count(dart.getElement()) + "</col>.");
        }

    }

    /**
     * Handles the action of unloading the contents of the Toxic Blowpipe.
     */
    public void handleUnloadBlowpipe() {
        if (blowpipeContents.isEmpty()) {
            player.getPacketSender().sendMessage("Your blowpipe is already empty!");
            return;
        }

        for (Entry<Integer> dart : blowpipeContents.entrySet()) {
            player.getInventory().add(new Item(dart.getElement(), blowpipeContents.count(dart.getElement())));
            blowpipeContents.remove(dart.getElement(), blowpipeContents.count(dart.getElement()));
        }

    }

}
