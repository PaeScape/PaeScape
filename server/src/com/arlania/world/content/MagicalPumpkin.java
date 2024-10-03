package com.arlania.world.content;

import com.arlania.model.Animation;
import com.arlania.model.Item;
import com.arlania.model.input.impl.EnterAmountToCredit;
import com.arlania.world.content.achievements.AchievementData;
import com.arlania.world.content.marketplace.Marketplace;
import com.arlania.world.entity.impl.player.Player;

public class MagicalPumpkin {

    public static void takeCandy(final Player p) {

        int candy = (int) Math.min(p.candyCredit / 5000, Integer.MAX_VALUE - p.getInventory().getAmount(4562));

        if (p.getInventory().getFreeSlots() > 0) {

            p.candyCredit -= candy * 5000L;

            p.getInventory().add(4562, candy);

            p.getPacketSender().sendMessage("You claim " + candy + " Rare Candy from the Magical Pumpkin!");

            InformationPanel.refreshPanel(p);
        } else {
            p.getPacketSender().sendMessage("@red@Your inventory is full.");
        }

    }

    public static void tryExchange(final Player p, final Item item) {
        if (p.getInventory().contains(item.getId()) && p.getInventory().getAmount(item.getId()) == 1) {
            handleCredit(p, item.getId(), 1);
        } else if (p.getInventory().contains(item.getId()) && p.getInventory().getAmount(item.getId()) > 1) {
            p.setInputHandling(new EnterAmountToCredit(item.getId()));
            p.getPacketSender().sendString(2799, item.getName()).sendInterfaceModel(1746, item.getId(), 150).sendChatboxInterface(4429);
            p.getPacketSender().sendString(2800, "How many would you like to credit?");
        } else {
            p.getPacketSender().sendMessage("@red@Something went wrong with this exchange. Let Pat know.");
        }
    }

    public static void handleCredit(final Player p, int itemId, int amount) {
        p.getSkillManager().stopSkilling();
        p.getPacketSender().sendInterfaceRemoval();

        if (!p.getClickDelay().elapsed(2000))
            return;

        if (!p.getInventory().contains(itemId)) {
            p.getPacketSender().sendMessage("@red@Your inventory does not contain this item.");
            return;
        }

        amount = Math.min(amount, p.getInventory().getAmount(itemId));

        if (itemId == 223804) {
            p.performAnimation(new Animation(827));
            p.getInventory().delete(itemId, amount);
            p.getInventory().add(4562, amount);
        } else {
            long candyCredit = Marketplace.getCandyCredit(p, itemId, amount);
            if (candyCredit > 0) {
                p.candyCredit += candyCredit;
                p.getAchievementTracker().progress(AchievementData.COLLECT_25000_CANDY_CREDIT, candyCredit);
                p.getAchievementTracker().progress(AchievementData.COLLECT_50000_CANDY_CREDIT, candyCredit);
                p.getAchievementTracker().progress(AchievementData.COLLECT_100000_CANDY_CREDIT, candyCredit);
                p.getAchievementTracker().progress(AchievementData.COLLECT_250000_CANDY_CREDIT, candyCredit);
                p.getAchievementTracker().progress(AchievementData.COLLECT_500000_CANDY_CREDIT, candyCredit);
                p.getAchievementTracker().progress(AchievementData.COLLECT_1000000_CANDY_CREDIT, candyCredit);
            }
        }
    }

}
