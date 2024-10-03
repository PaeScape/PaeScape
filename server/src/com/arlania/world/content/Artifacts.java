package com.arlania.world.content;

import com.arlania.model.GameMode;
import com.arlania.model.GroundItem;
import com.arlania.model.Item;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.entity.impl.GroundItemManager;
import com.arlania.world.entity.impl.player.Player;

public class Artifacts {

    public static int[] artifacts = {14876, 14877, 14878, 14879, 14880, 14881, 14882, 14883, 14884, 14885, 14886, 14887, 14888, 14889, 14890, 14891, 14892};

    public static void sellArtifacts(Player c) {
        c.getPacketSender().sendInterfaceRemoval();
        boolean artifact = false;
        for (int k = 0; k < artifacts.length; k++) {
            if (c.getInventory().contains(artifacts[k])) {
                artifact = true;
            }
        }
        if (!artifact) {
            c.getPacketSender().sendMessage("You do not have any Artifacts in your inventory to sell to Mandrith.");
            return;
        }

        int coins = 0;

        for (int i = 0; i < artifacts.length; i++) {
            for (Item item : c.getInventory().getValidItems()) {
                if (item.getId() == artifacts[i]) {

                    if (coins >= 2000000000) {
                        c.setMoneyInPouch((c.getMoneyInPouch() + coins));
                        c.getPacketSender().sendString(8135, "" + c.getMoneyInPouch());
                        coins = 0;
                    }

                    coins = coins + ItemDefinition.forId(artifacts[i]).getValue() * 5000;
                    c.getInventory().delete(artifacts[i], 1);
                    c.getInventory().refreshItems();
                }
            }
        }

        c.setMoneyInPouch((c.getMoneyInPouch() + coins));
        c.getPacketSender().sendString(8135, "" + c.getMoneyInPouch());
        c.getPacketSender().sendMessage("You've sold your artifacts for " + coins + " coins!");
        coins = 0;

    }

    public static void tradeArtifacts(Player c) {
        c.getPacketSender().sendInterfaceRemoval();
        boolean artifact = false;
        for (int k = 0; k < artifacts.length; k++) {
            if (c.getInventory().contains(artifacts[k])) {
                artifact = true;
            }
        }
        if (!artifact) {
            c.getPacketSender().sendMessage("You do not have any Artifacts in your inventory to trade to Mandrith.");
            return;
        }

        int points = 0;

        for (int i = 0; i < artifacts.length; i++) {
            for (Item item : c.getInventory().getValidItems()) {
                if (item.getId() == artifacts[i]) {

                    points = points + ItemDefinition.forId(artifacts[i]).getValue();
                    c.getInventory().delete(artifacts[i], 1);
                    c.getInventory().refreshItems();
                }
            }
        }

        c.WildyPoints = c.WildyPoints + points;
        PlayerPanel.refreshPanel(c);
        c.getPacketSender().sendMessage("You've traded your artifacts for " + points + " Wildy Points!");
        points = 0;

    }

    public static int convertArtifactsToCoins(Player c, int itemId) {
        c.getPacketSender().sendInterfaceRemoval();
        boolean artifact = false;
        for (int k = 0; k < artifacts.length; k++) {
            if (c.getInventory().contains(artifacts[k])) {
                artifact = true;
            }
        }
        if (!artifact) {
            c.getPacketSender().sendMessage("You do not have any Artifacts in your inventory to sell to Mandrith.");
            return 0;
        }

        int coins = 0;

        for (int i = 0; i < artifacts.length; i++) {
            for (Item item : c.getInventory().getValidItems()) {
                if (itemId == artifacts[i]) {

                    if (coins >= 1000000000) {
                        coins = 1000000000;
                    }

                    coins = coins + ItemDefinition.forId(artifacts[i]).getValue() * 2500 * item.getAmount();
                    //c.getInventory().delete(artifacts[i], 1);
                    //c.getInventory().refreshItems();
                }
            }
        }

        return coins;
    }


    /*
     * Artifacts
     */
    private final static int[] LOW_ARTIFACTS = {14882, 14883};
    private final static int[] MED_ARTIFACTS = {14880, 14881};
    private final static int[] HIGH_ARTIFACTS = {14878, 14879};
    private final static int[] EXR_ARTIFACTS = {14876, 14877};
    private final static int[] PVP_ARMORS = {13899, 13893, 13887, 13902, 13896, 13890, 13858, 13861, 13864, 13870, 13873, 13876, 13884, 13867};

    /**
     * Handles a target drop
     *
     * @param Player player		Player who has killed Player o
     * @param Player o			Player who has been killed by Player player
     */
    public static void handleDrops(Player killer, Player death, boolean targetKill) {

        int rareDrop = getRandomItem(PVP_ARMORS);
        String itemName = Misc.formatText(ItemDefinition.forId(rareDrop).getName());

        if (killer.getGameMode() != GameMode.NORMAL)
            return;
        if (RandomUtility.inclusiveRandom(100) >= 85 || targetKill)
            GroundItemManager.spawnGroundItem(killer, new GroundItem(new Item(getRandomItem(LOW_ARTIFACTS)), death.getPosition().copy(), killer.getUsername(), false, 110, true, 100));
        if (RandomUtility.inclusiveRandom(100) >= 90)
            GroundItemManager.spawnGroundItem(killer, new GroundItem(new Item(getRandomItem(MED_ARTIFACTS)), death.getPosition().copy(), killer.getUsername(), false, 110, true, 100));
        if (RandomUtility.inclusiveRandom(100) >= 97)
            GroundItemManager.spawnGroundItem(killer, new GroundItem(new Item(getRandomItem(HIGH_ARTIFACTS)), death.getPosition().copy(), killer.getUsername(), false, 110, true, 100));
        if (RandomUtility.inclusiveRandom(100) >= 98)
            GroundItemManager.spawnGroundItem(killer, new GroundItem(new Item(getRandomItem(EXR_ARTIFACTS)), death.getPosition().copy(), killer.getUsername(), false, 110, true, 100));
        if (RandomUtility.inclusiveRandom(100) >= 99) {
            GroundItemManager.spawnGroundItem(killer, new GroundItem(new Item(rareDrop), death.getPosition().copy(), killer.getUsername(), false, 110, true, 100));
            World.sendMessage("drops", "<img=10> @blu@[Bounty Hunter]@bla@ " + killer.getUsername() + " has just received " + Misc.anOrA(itemName) + " " + itemName + "!");
        }
    }


    /**
     * Get's a random int from the array specified
     *
     * @param array The array specified
     * @return The random integer
     */
    public static int getRandomItem(int[] array) {
        return array[RandomUtility.inclusiveRandom(array.length - 1)];
    }

}
