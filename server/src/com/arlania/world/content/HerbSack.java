package com.arlania.world.content;

import com.arlania.model.Item;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.world.entity.impl.player.Player;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * This class handles all functionality for the herb sack item from OSRS.
 *
 * @author Andys1814
 */
public final class HerbSack {

    /**
     * The player participating in this action.
     */
    private final Player player;

    /**
     * Constructs a new instance of this class that assigns {@link #player} to
     * the paramaterized value.
     *
     * @param player The player participating in this action.
     */
    public HerbSack(Player player) {
        this.player = player;
    }

    /**
     * An {@link Multiset} of Integer's (Item IDs) which handles the contents of
     * the herb sack.
     */
    private final Multiset<Integer> herbSack = HashMultiset.create();

    /**
     * Handles the action of filling the herb sack.
     */
    public void handleFillSack() {
        player.getPacketSender().sendMessage("You search your inventory for herbs appropriate to put in the sack...");
        Stream<Item> inventory = Arrays.stream(player.getInventory().getItems());
        if (inventory.noneMatch(isGrimyHerb())) {
            player.getPacketSender().sendMessage("There is no herbs in your inventory that can be added to the sack.");
            return;
        }
        inventory.filter(isGrimyHerb()).forEach(herb -> {
            if (herbSack.count(herb.getId()) < 30) {
                player.getInventory().delete(herb);
                herbSack.add(herb.getId());
            }
        });
        player.getPacketSender().sendMessage("You add the herb(s) to your sack.");
    }

    /**
     * Handles the action of emptying the herb sack.
     */
    public void handleEmptySack() {
        if (herbSack.isEmpty()) {
            player.getPacketSender().sendMessage("The herb sack is already empty.");
            return;
        }
        if (player.getInventory().getFreeSlots() <= 0) {
            player.getPacketSender()
                    .sendMessage("You don't have enough inventory space to empty the contents of this sack.");
            return;
        }
        for (Iterator<Integer> i = herbSack.iterator(); i.hasNext(); ) {
            if (player.getInventory().getFreeSlots() <= 0) {
                return;
            }
            int herb = i.next();
            player.getInventory().add(new Item(herb));
            i.remove();
        }
    }

    /**
     * Handles the action of checking the contents of the herb sack. This method
     * will automatically print the herbs in the highest order first
     * {@see Multisets.copyHighestCountFirst}.
     */
    public void handleCheckSack() {
        player.getPacketSender().sendMessage("You look in your herb sack and see:");
        if (herbSack.isEmpty()) {
            player.getPacketSender().sendMessage("The herb sack is empty.");
            return;
        }
        for (int herbId : Multisets.copyHighestCountFirst(herbSack).elementSet()) {
            player.getPacketSender()
                    .sendMessage(herbSack.count(herbId) + " x " + ItemDefinition.forId(herbId).getName());
        }
    }

    /**
     * Determines whether the provided instance of {@link Item} is considered a
     * grimy herb (Based on it's name). This predicate will be used to filter
     * out the items from the player's inventory in order to only utilize the
     * grimy herbs.
     *
     * @return <true> if the instance of {@link Item} contains "Grimy" in it's
     * name, <false> otherwise.
     */
    private Predicate<Item> isGrimyHerb() {
        return herb -> Objects.nonNull(herb) && herb.getDefinition().getName().contains("Grimy");
    }

}