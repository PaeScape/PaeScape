package com.arlania.world.content;

import com.arlania.DiscordBot;
import com.arlania.GameSettings;
import com.arlania.model.Item;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.minigames.impl.EquipmentUpgrades;
import com.arlania.world.entity.impl.player.Player;
import org.javacord.api.entity.message.MessageBuilder;

public class SubscriptionBox {

    /*
     * Boxes
     */

    public static final int sapphireBox = 906;
    public static final int emeraldBox = 907;
    public static final int rubyBox = 908;
    public static final int diamondBox = 909;
    public static final int dragonstoneBox = 910;

    /*
     * Rewards
     */
    public static final int crystalKey = 989;
    public static final int rareCandy = 4562;
    public static final int mysteryBox = 6199;
    public static final int eliteBox = 15501;
    public static final int supremeBox = 603;
    public static final int ancientEffigy = 18778;
    public static final int easyClue = ClueScrolls.easyClue;
    public static final int mediumClue = ClueScrolls.mediumClue;
    public static final int hardClue = ClueScrolls.hardClue;
    public static final int eliteClue = ClueScrolls.eliteClue;
    public static final int masterClue = ClueScrolls.masterClue;
    public static final int lowUpgrade = EquipmentUpgrades.lowUpgrade;
    public static final int mediumUpgrade = EquipmentUpgrades.mediumUpgrade;
    public static final int highUpgrade = EquipmentUpgrades.highUpgrade;
    public static final int legendaryUpgrade = EquipmentUpgrades.legendaryUpgrade;
    public static final int masterUpgrade = EquipmentUpgrades.masterUpgrade;
    public static final int randomGlobal = 420;
    public static final int randomPersonal = 69;


    /*
     * Box Breakdown
     */
    public static final int[][] sapphireRewards = {{crystalKey, 5}, {rareCandy, 1}, {mysteryBox, 1}, {ancientEffigy, 1},
            {easyClue, 1}, {mediumClue, 1}, {hardClue, 1}, {lowUpgrade, 1}, {mediumUpgrade, 1}, {randomPersonal, 1}};

    public static final int[][] emeraldRewards = {{crystalKey, 10}, {rareCandy, 3}, {mysteryBox, 1}, {eliteBox, 1},
            {easyClue, 2}, {mediumClue, 1}, {hardClue, 1}, {lowUpgrade, 1}, {mediumUpgrade, 1}, {randomPersonal, 1}};

    public static final int[][] rubyRewards = {{rareCandy, 5}, {mysteryBox, 2}, {eliteBox, 1}, {supremeBox, 1},
            {mediumClue, 2}, {hardClue, 1}, {lowUpgrade, 2}, {mediumUpgrade, 1}, {highUpgrade, 1}, {randomPersonal, 1}};

    public static final int[][] diamondRewards = {{mysteryBox, 2}, {eliteBox, 1}, {supremeBox, 1}, {hardClue, 2},
            {eliteClue, 1}, {mediumUpgrade, 2}, {highUpgrade, 1}, {legendaryUpgrade, 1}, {randomPersonal, 2}, {randomGlobal, 1}};

    public static final int[][] dragonstoneRewards = {{eliteBox, 1}, {supremeBox, 1}, {hardClue, 3}, {eliteClue, 1}, {masterClue, 1},
            {highUpgrade, 2}, {legendaryUpgrade, 1}, {masterUpgrade, 1}, {randomPersonal, 2}, {randomGlobal, 1}};

    /*
     * Handles the opening of the subscription box
     */
    public static void open(Player player, int itemId) {
        if (player.getInventory().getFreeSlots() < 1) {
            player.getPacketSender().sendMessage("You need at least 1 inventory space");
            return;
        }

        int[][] rewards = new int[10][2];

        switch (itemId) {
            case sapphireBox:
                rewards = sapphireRewards;
                break;
            case emeraldBox:
                rewards = emeraldRewards;
                break;
            case rubyBox:
                rewards = rubyRewards;
                break;
            case diamondBox:
                rewards = diamondRewards;
                break;
            case dragonstoneBox:
                rewards = dragonstoneRewards;
                break;
        }

        player.getInventory().delete(itemId, 1);

        int random = RandomUtility.inclusiveRandom(0, 9);

        if (random > 9)
            random = 9;

        int reward = 0;
        int qty = 0;

        //random personal
        if (rewards[random][0] == 69) {
            int[] events = {4020, 4021, 4022, 4023, 4024, 4025, 4027, 4028, 4029, 4030, 4031, 4032};
            reward = events[RandomUtility.inclusiveRandom(events.length - 1)];
            qty = 1;
        }

        //random global
        else if (rewards[random][0] == 420) {
            int[] events = {7020, 7021, 7022, 7023, 7024, 7025, 7027, 7028, 7029, 7032, 7040, 7041, 7042, 7043, 7044, 7045, 7046, 7047, 7048, 7049};

            int rand = RandomUtility.inclusiveRandom(0, events.length - 1);

            if (rand > 19)
                rand = 19;

            reward = events[rand];

            qty = rewards[random][1];;
        }

        //other rewards
        else {
            reward = rewards[random][0];
            qty = rewards[random][1];
        }

        player.getInventory().add(reward, qty);
        player.getPacketSender().sendMessage("Congratulations on your reward!");

        String rewardName = new Item(reward, 1).getName();
        String boxName = new Item(itemId, 1).getName();
        String discordMessage = player.getUsername()
                + " just received (" + qty + ") " + rewardName + " from a " + boxName + ".";

        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.SUBSCRIPTION_BOX_DROPS_CH).get());

    }


}
