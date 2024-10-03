package com.arlania.world.content.skill.impl.construction.sawmill;

import com.arlania.world.content.dialogue.Dialogue;
import com.arlania.world.content.dialogue.DialogueExpression;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.content.dialogue.DialogueType;
import com.arlania.world.entity.impl.player.Player;

public class SawmillOperator {

    /**
     * Exchanges logs for planks
     *
     * @param player
     * @param plank
     * @param amount
     */
    public static void exchange(Player player, Plank plank, int amount) {
        int logs = player.getInventory().getAmount(plank.getLogId());
        if (logs <= 0) {
            return;
        }
        if (amount < logs)
            logs = amount;
        int toPay = logs * plank.getCost();

        player.getPacketSender().sendInterfaceRemoval();

        if (player.getInventory().getAmount(995) < toPay && player.getMoneyInPouch() < toPay) {
            DialogueManager.start(player, new Dialogue() {

                @Override
                public DialogueType type() {
                    return DialogueType.STATEMENT;
                }

                @Override
                public DialogueExpression animation() {
                    return DialogueExpression.NO_EXPRESSION;
                }

                @Override
                public String[] dialogue() {
                    return new String[]{"You do not have enough coins to buy this", "amount of planks."};
                }

            });
            return;
        }

        if (player.getMoneyInPouch() >= toPay) {
            player.setMoneyInPouch(player.getMoneyInPouch() - (long) toPay);
            player.getPacketSender().sendString(8135,
                    "" + player.getMoneyInPouch());
        } else
            player.getInventory().delete(995, toPay);

        player.getInventory().delete(plank.getLogId(), logs).add(plank.getPlankId(), logs);

        player.getPacketSender().sendMessage("You receive your planks.");
    }

    public static boolean handleButtonClick(int id, int menuId, Player player) {

        switch (id) {
            case -4533:
            case -4532:
            case -4531:
            case -4530:

                int amount = 0;
                Plank plank = Plank.values()[4533 + id];

                if (plank == null) {
                    return true;
                }

                switch (menuId) {
                    case 5:
                        amount = 1;
                        break;
                    case 4:
                        amount = 5;
                        break;
                    case 3:
                        amount = 10;
                        break;
                    case 2:
                        //amount = 1;
                        //x amount
                        break;
                    case 1:
                        amount = player.getInventory().getAmount(plank.getLogId());
                        break;
                }

                if (amount <= 0) {
                    return true;
                }

                exchange(player, plank, amount);

                return true;

        }

        return false;
    }

}
