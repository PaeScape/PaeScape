package com.arlania.world.content.minigames.impl;

import com.arlania.model.Skill;
import com.arlania.util.RandomUtility;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

/**
 * @author Gabriel Hannason
 * Wrote this quickly!!
 * Handles the RFD quest
 */
public class MotherLodeMine {

    public static void depositPayDirt(Player player) {
        int paydirt = player.getInventory().getAmount(212011);

        int maxPayDirt = 250;


        if (player.getStaffRights().getStaffRank() >= 1)
            maxPayDirt += 250;
        if (player.getStaffRights().getStaffRank() >= 3)
            maxPayDirt += 250;
        if (player.getStaffRights().getStaffRank() >= 5)
            maxPayDirt += 250;
        if (player.getStaffRights().getStaffRank() >= 6)
            maxPayDirt += 250;

        if (player.getInventory().contains(212011)) {
            for (int i = 0; i < paydirt; i++) {
                if (player.getPayDirt() < maxPayDirt) {
                    player.getInventory().delete(212011, 1);
                    player.setPayDirt(player.getPayDirt() + 1);
                } else {
                    player.getPacketSender().sendMessage("@red@You can't deposit anymore Pay Dirt!");
                    break;
                }
            }
        }

        if (player.getInventory().contains(18339)) {
            paydirt = player.getCoalBag();

            for (int i = 0; i < paydirt; i++) {
                if (player.getPayDirt() < maxPayDirt) {
                    player.setCoalBag(player.getCoalBag() - 1);
                    player.setPayDirt(player.getPayDirt() + 1);
                } else {
                    player.getPacketSender().sendMessage("@red@You can't deposit anymore Pay Dirt!");
                    break;
                }
            }
        }
    }

    public static void processPayDirt(Player player) {
        int paydirt = player.getPayDirt();


        for (int i = 0; i < paydirt; i++) {

            if (player.getMoneyInPouch() < 200) {
                player.getPacketSender().sendMessage("@red@You need 200 coins in your money pouch to process each ore.");
                return;
            }

            player.setMoneyInPouch(player.getMoneyInPouch() - 200);
            player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());

            //coal
            player.getInventory().add(454, 1);

            //gold ore
            if ((player.getSkillManager().getCurrentLevel(Skill.MINING) >= 40) && (RandomUtility.inclusiveRandom(100) > 50)) {
                player.getInventory().add(445, 1);
                player.getSkillManager().addExperience(Skill.MINING, 20);
            }

            //mithril ore
            if ((player.getSkillManager().getCurrentLevel(Skill.MINING) >= 55) && (RandomUtility.inclusiveRandom(100) > 60)) {
                player.getInventory().add(448, 1);
                player.getSkillManager().addExperience(Skill.MINING, 70);
            }

            //adamantite ore
            if ((player.getSkillManager().getCurrentLevel(Skill.MINING) >= 70) && (RandomUtility.inclusiveRandom(100) > 70)) {
                player.getInventory().add(450, 1);
                player.getSkillManager().addExperience(Skill.MINING, 120);
            }

            //runite ore
            if ((player.getSkillManager().getCurrentLevel(Skill.MINING) >= 85) && (RandomUtility.inclusiveRandom(100) > 80)) {
                player.getInventory().add(452, 1);
                player.getSkillManager().addExperience(Skill.MINING, 170);
            }
            player.setPayDirt(player.getPayDirt() - 1);
        }
    }

    public static void trashTalkingDwarf(NPC npc) {

        String[] messages = {
                "Be careful you�re gonna break a nail *hic*",
                "You call that a pickaxe *hic*",
                "You swing that thing like my granny *hic*",
                "Do you even know what to do with mithril *hic*",
                "Who taught you how to mine?! hic*",
                "1-2-3-4 pick that gem up off my floor *hic*",
                "Look who got out there miner pants *hic*",
                "Be careful you�re gonna break a nail hic*",
                "Are you swinging two pickaxes hic*",
                "You humans are funny creatures hic*",
                "I wager you only make daggers super chief hic*",
                "I�ve met gnomes stronger than you hic*",
                "Make yourself useful and pour me another beer hic*",
                "You look like my sister Rulvana - hideous hic*"
        };


        if (npc.getId() == 2203)
            npc.forceChat(messages[RandomUtility.inclusiveRandom(messages.length - 1)]);

    }

}
