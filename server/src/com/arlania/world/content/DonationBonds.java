package com.arlania.world.content;

import com.arlania.model.GameMode;
import com.arlania.model.StaffRights;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.dialogue.Dialogue;
import com.arlania.world.content.dialogue.DialogueExpression;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.content.dialogue.DialogueType;
import com.arlania.world.entity.impl.player.Player;

public class DonationBonds {

    public static void checkForRankUpdate(Player player) {
        if (player.getStaffRights().isStaff() && player.getGameMode() != GameMode.SEASONAL_IRONMAN) {
            return;
        }

        StaffRights rights = null;

        if (player.getPointsHandler().getAmountDonated() >= 1000 || (player.getGameMode() == GameMode.SEASONAL_IRONMAN && player.seasonPoints >= 800))
            rights = StaffRights.MASTER_DONATOR;
        else if (player.getPointsHandler().getAmountDonated() >= 500 || (player.getGameMode() == GameMode.SEASONAL_IRONMAN && player.seasonPoints >= 600))
            rights = StaffRights.UBER_DONATOR;
        else if (player.getPointsHandler().getAmountDonated() >= 250 || (player.getGameMode() == GameMode.SEASONAL_IRONMAN && player.seasonPoints >= 400))
            rights = StaffRights.LEGENDARY_DONATOR;
        else if (player.getPointsHandler().getAmountDonated() >= 100 || (player.getGameMode() == GameMode.SEASONAL_IRONMAN && player.seasonPoints >= 300))
            rights = StaffRights.EXTREME_DONATOR;
        else if (player.getPointsHandler().getAmountDonated() >= 25 || (player.getGameMode() == GameMode.SEASONAL_IRONMAN && player.seasonPoints >= 200))
            rights = StaffRights.SUPER_DONATOR;
        else if (player.getPointsHandler().getAmountDonated() >= 5 || (player.getGameMode() == GameMode.SEASONAL_IRONMAN && player.seasonPoints >= 100))
            rights = StaffRights.DONATOR;
        else if (player.getPointsHandler().getAmountDonated() < 5)
            rights = StaffRights.PLAYER;


        if (rights != null && rights != player.getStaffRights()) {
            player.getPacketSender().sendMessage("You've become a " + Misc.formatText(rights.toString().toLowerCase()) + "! Congratulations!");
            player.setStaffRights(rights);
            player.getPacketSender().sendRights();
        }

    }

    public static boolean handleBond(Player player, int item) {

        switch (item) {
            case 15356:
            case 15355:
            case 15359:
            case 15358:
                if (player.getInventory().getFreeSlots() < 6) {
                    player.getPacketSender().sendMessage("@red@You need to have 6 inventory spaces empty!");
                    return false;
                }

                DialogueManager.start(player, handleBondRedemption(player));
                player.setSelectedSkillingItem(item);
        }

        return false;
    }

    public static void handleFreebie(Player player) {
        int item = player.getSelectedSkillingItem();
        int randomFreebie = 995;

        switch(item){

            case 15356: //$5
            case 15355: //$10
                return;
            case 15359: //$20
                int[][] freebies20 = {{913, 1}, {4032, 1}, {15501, 1}, {6758, 1}, {6769, 1}, {2957, 1}, {995, 250000000}};
                randomFreebie = RandomUtility.inclusiveRandom(0, freebies20.length - 1);

                player.getInventory().add(freebies20[randomFreebie][0], freebies20[randomFreebie][1]);
                player.getPacketSender().sendMessage("You receive a random Freebie for redeeming your $20 bond!");

                break;
            case 15358: //$50
                int[][] freebies50 = {{915, 1}, {4032, 3}, {603, 1}, {6758, 3}, {6769, 3}, {6749, 1}, {995, 1000000000}};
                randomFreebie = RandomUtility.inclusiveRandom(0, freebies50.length - 1);

                player.getInventory().add(freebies50[randomFreebie][0], freebies50[randomFreebie][1]);
                player.getPacketSender().sendMessage("You receive a random Freebie for redeeming your $50 bond!");

                break;
        }

    }

    public static void claimReward(Player player, int option) {

        int item = player.getSelectedSkillingItem();

        int funds = item == 15356 ? 5 : item == 15355 ? 10 : item == 15359 ? 20 : item == 15358 ? 50 : -1;

        player.getInventory().delete(item, 1);


        if(option == 1) {
            player.getInventory().add(19864, funds);
            player.getPacketSender().sendMessage("@blu@You have received " + funds + " Donator Rank Points! These are used to increase your donator rank.");
        }

        //Donator rank bonus for Donator store coins

        if (player.getStaffRights().getStaffRank() > 5)
            funds *= 1.3;
        if (player.getStaffRights().getStaffRank() == 5)
            funds *= 1.25;
        else if (player.getStaffRights().getStaffRank() == 4)
            funds *= 1.2;
        else if (player.getStaffRights().getStaffRank() == 3)
            funds *= 1.15;
        else if (player.getStaffRights().getStaffRank() == 2)
            funds *= 1.1;
        else if (player.getStaffRights().getStaffRank() == 1)
            funds *= 1.05;

        if(option == 2) {
            funds *= 2;
        }

        player.getInventory().add(8851, funds);
        player.getPacketSender().sendMessage("@blu@You have received " + funds + " Donator Store Coins! These can be spent in the donator shop.");

        handleFreebie(player);

        DonationBonds.checkForRankUpdate(player);
        PlayerPanel.refreshPanel(player);

        player.getPacketSender().sendInterfaceRemoval();
    }

    public static Dialogue getTotalFunds(final Player player) {
        return new Dialogue() {

            @Override
            public DialogueType type() {
                return DialogueType.NPC_STATEMENT;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.NORMAL;
            }

            @Override
            public int npcId() {
                return 4657;
            }

            @Override
            public String[] dialogue() {
                return player.getPointsHandler().getAmountDonated() > 0 ? new String[]{"Your account has claimed scrolls worth $" + player.getPointsHandler().getAmountDonated() + " in total.", "Thank you for supporting us!"} : new String[]{"Your account has claimed scrolls worth $" + player.getPointsHandler().getAmountDonated() + " in total."};
            }

            @Override
            public Dialogue nextDialogue() {
                return DialogueManager.getDialogues().get(5);
            }
        };
    }

    public static Dialogue handleBondRedemption(final Player player) {

        return new Dialogue() {
            @Override
            public DialogueType type() {
                return DialogueType.NPC_STATEMENT;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.NORMAL;
            }

            @Override
            public int npcId() {
                return 2917;
            }

            @Override
            public String[] dialogue() {
                return new String[]{
                        "Thank you for supporting PaeScape!",
                        "You are able to redeem for rank and",
                        "coins or double coins. Which would",
                        "you prefer?"
                };
            }

            @Override
            public void specialAction() {

            }

            @Override
            public Dialogue nextDialogue() {
                return new Dialogue() {

                    @Override
                    public DialogueType type() {
                        return DialogueType.OPTION;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return null;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[]{
                                "Donator Rank + Coins",
                                "2x Donator Coins"

                        };
                    }

                    @Override
                    public void specialAction() {
                        player.setDialogueActionId(42069);
                    }
                };
            }
        };
    }
}
