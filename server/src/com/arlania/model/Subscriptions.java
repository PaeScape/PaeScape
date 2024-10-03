package com.arlania.model;

import com.arlania.DiscordBot;
import com.arlania.GameLoader;
import com.arlania.GameSettings;
import com.arlania.world.content.SubscriptionBox;
import com.arlania.world.entity.impl.player.Player;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.javacord.api.entity.message.MessageBuilder;

import java.time.LocalDate;


/**
 * Represents a player's privilege rights.
 *
 * @author Gabriel Hannason
 */

public enum Subscriptions {

    /*
     * A regular member of the server.
     */
    PLAYER(0, -1, -1, -1),

    /*
     * A member who has Subscribed to the server.
     */

    SAPPHIRE(1, 906, 911, 600),
    EMERALD(2, 907, 912, 600),
    RUBY(3, 908, 913, 600),
    DIAMOND(4, 909, 914, 600),
    DRAGONSTONE(5, 910, 915, 600);


    Subscriptions(int subscriptionLevel, int boxType, int bondType, int chance) {
        this.subscriptionLevel = subscriptionLevel;
        this.boxType = boxType;
        this.bondType = bondType;
        this.chance = chance;
    }

    private static final ImmutableSet<Subscriptions> SUBSCRIBER = Sets.immutableEnumSet(SAPPHIRE, EMERALD, RUBY, DIAMOND, DRAGONSTONE);

    private final int subscriptionLevel;
    private final int boxType;
    private final int bondType;
    private final int chance;

    public int getSubscriptionLevel() {
        return subscriptionLevel;
    }

    public int getBoxType() {
        return boxType;
    }

    public int getBondType() {
        return bondType;
    }

    public int getChance() {
        return chance;
    }

    /**
     * The amount of loyalty points the rank gain per 4 seconds
     */
    public boolean isSubscriber() {
        return SUBSCRIBER.contains(this);
    }

    public static Subscriptions forSubscriptionLevel(int rank) {
        for (Subscriptions rights : Subscriptions.values()) {
            if (rights.ordinal() == rank) {
                return rights;
            }
        }
        return null;
    }

    public static void handleSubscription(Player player, int itemID) {

        if (!player.getSubscription().isSubscriber()) {
            player.subscriptionStartDate = GameLoader.getDayOfTheYear();

            player.getPacketSender().sendMessage("Year: " + GameLoader.getYear());
            player.getPacketSender().sendMessage("Date: " + GameLoader.getDayOfTheYear());

            LocalDate date = LocalDate.of(GameLoader.getYear(), GameLoader.getMonth(), GameLoader.getDayOfTheMonth()); // The date you want to convert

            // Calculate the number of days since the epoch (January 1, 1970)
            long epochDays = date.toEpochDay();

            // Convert to int
            int epochInteger = (int) epochDays;

            player.getInventory().delete(itemID, 1);

            switch (itemID) {

                case 911:
                    player.setSubscription(Subscriptions.SAPPHIRE);
                    break;
                case 912:
                    player.setSubscription(Subscriptions.EMERALD);
                    break;
                case 913:
                    player.setSubscription(Subscriptions.RUBY);
                    break;
                case 914:
                    player.setSubscription(Subscriptions.DIAMOND);
                    break;
                case 915:
                    player.setSubscription(Subscriptions.DRAGONSTONE);
                    break;


            }

            player.subscriptionStartDate = epochInteger;
            player.subscriptionEndDate = epochInteger + 30;

            player.getPacketSender().sendMessage("You are now a " + player.getSubscription().name() + " Subscriber for 30 days!");

            String discordMessage = "[" + player.getSubscription().name() + "] " + player.getUsername() + " has redeemed a new subscription.";

            if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.SUBSCRIPTION_CH).get());

        } else if (player.getSubscription().isSubscriber()) {

            if (player.getSubscription().getBondType() == itemID) {

                player.getInventory().delete(itemID, 1);

                player.getPacketSender().sendMessage("You've added 30 days to your current subscription!");
                player.subscriptionEndDate += 30;

                String discordMessage = "[" + player.getSubscription().name() + "] " + player.getUsername() + " has added 30 days to their current subscription.";

                if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                    new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.SUBSCRIPTION_CH).get());
            } else {
                player.getPacketSender().sendMessage("You can only extend a membership of the same tier.");
            }

        }

    }


    public static void handleSubscriptionBox(Player player, int itemID) {
        SubscriptionBox.open(player, itemID);
    }


}
