package com.arlania.world.content.achievements;

import com.arlania.world.content.InformationPanel;
import com.arlania.world.content.abilities.AbilityCategory;
import com.arlania.world.content.interfaces.AbilitiesInterface;
import com.arlania.world.content.interfaces.PerkInterface;
import com.arlania.world.entity.impl.player.Player;

public class AchievementAbilities {

    public static String[] abilityNames =
            {"Banker", "Processer", "Gatherer", "Sweeten",
                    "Entertainer",
                    "Combatant",
                    "Cryptic",
                    "Gambler",
                    "Prosperous",
                    "Bountiful"};

    public static boolean processAbility(Player player, String ability) {

        switch (ability) {

            case "banker":
                if (player.achievementAbilities[0] == 0 && player.getAchievementPoints() >= 25) {
                    player.achievementAbilities[0] = 1;
                    player.setAchievementPoints(player.getAchievementPoints() - 25);
                } else if (player.getAchievementPoints() < 25) {
                    player.getPacketSender().sendMessage("You need 25 Achievement points to buy this ability.");
                }
                break;
            case "processor":
                if (player.achievementAbilities[1] == 0 && player.getAchievementPoints() >= 25) {
                    player.achievementAbilities[1] = 1;
                    player.setAchievementPoints(player.getAchievementPoints() - 25);
                } else if (player.getAchievementPoints() < 25) {
                    player.getPacketSender().sendMessage("You need 25 Achievement points to buy this ability.");
                }
                break;
            case "gatherer":
                if (player.achievementAbilities[2] == 0 && player.getAchievementPoints() >= 25) {
                    player.achievementAbilities[2] = 1;
                    player.setAchievementPoints(player.getAchievementPoints() - 25);
                } else if (player.getAchievementPoints() < 25) {
                    player.getPacketSender().sendMessage("You need 25 Achievement points to buy this ability.");
                }
                break;
            case "sweeten":
                if (player.achievementAbilities[3] == 0 && player.getAchievementPoints() >= 25) {
                    player.achievementAbilities[3] = 1;
                    player.setAchievementPoints(player.getAchievementPoints() - 25);
                } else if (player.getAchievementPoints() < 25) {
                    player.getPacketSender().sendMessage("You need 25 Achievement points to buy this ability.");
                }
                break;
            case "entertainer":
                if (player.achievementAbilities[4] == 0 && player.getAchievementPoints() >= 25) {
                    player.achievementAbilities[4] = 1;
                    player.setAchievementPoints(player.getAchievementPoints() - 25);
                } else if (player.getAchievementPoints() < 25) {
                    player.getPacketSender().sendMessage("You need 25 Achievement points to buy this ability.");
                }
                break;
            case "combatant":
                if (player.achievementAbilities[5] == 0 && player.getAchievementPoints() >= 25) {
                    player.achievementAbilities[5] = 1;
                    player.setAchievementPoints(player.getAchievementPoints() - 25);
                } else if (player.getAchievementPoints() < 25) {
                    player.getPacketSender().sendMessage("You need 25 Achievement points to buy this ability.");
                }
                break;
            case "cryptic":
                if (player.achievementAbilities[6] == 0 && player.getAchievementPoints() >= 25) {
                    player.achievementAbilities[6] = 1;
                    player.setAchievementPoints(player.getAchievementPoints() - 25);
                } else if (player.getAchievementPoints() < 25) {
                    player.getPacketSender().sendMessage("You need 25 Achievement points to buy this ability.");
                }
                break;
            case "gambler":
                if (player.achievementAbilities[7] == 0 && player.getAchievementPoints() >= 25) {
                    player.achievementAbilities[7] = 1;
                    player.setAchievementPoints(player.getAchievementPoints() - 25);
                } else if (player.getAchievementPoints() < 25) {
                    player.getPacketSender().sendMessage("You need 25 Achievement points to buy this ability.");
                }
                break;
            case "prosperous":
                if (player.achievementAbilities[8] == 0 && player.getAchievementPoints() >= 25) {
                    player.achievementAbilities[8] = 1;
                    player.setAchievementPoints(player.getAchievementPoints() - 25);
                } else if (player.getAchievementPoints() < 25) {
                    player.getPacketSender().sendMessage("You need 25 Achievement points to buy this ability.");
                }
                break;
            case "bountiful":
                if (player.achievementAbilities[9] == 0 && player.getAchievementPoints() >= 25) {
                    player.achievementAbilities[9] = 1;
                    player.setAchievementPoints(player.getAchievementPoints() - 25);
                } else if (player.getAchievementPoints() < 25) {
                    player.getPacketSender().sendMessage("You need 25 Achievement points to buy this ability.");
                }
                break;
        }

        player.getPacketSender().sendInterfaceRemoval();
        InformationPanel.refreshPanel(player);
        AbilitiesInterface.showPerkInterface(player);
        player.activeInterface = "achievementabilities";

        return false;
    }

}



//Achievement Abilities

//270 achievements

/*
0 - (25) Banker's Note (Magic paper is no longer consumed) *
1 - (25) Process noted items (where possible) *
2 - (25) Gather noted items (where possible) *
3 - (25) Rare Candy are 1:1,000 from Bosses *
4 - (25) Event Passes are 1:5,000 from Bosses *
5 - (25) Battle Passes are 1:5,000 from Bosses *
6 - (25) Mystery Passes 1:2,500 from CoX/ToB/GWD/SHR Raids *
7 - (25) Bingo Cards 1:1,000 from CoX/ToB/GWD/SHR Raids *
8 - (25) Permanent 5% Drop Rate Boost *
9 - (25) Permanent 25% XP Boost *
*/
