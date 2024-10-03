package com.arlania.world.content;

import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.content.interfaces.PerkInterface;
import com.arlania.world.entity.impl.player.Player;

public class Perks {
    // Berserker
    // Bullseye
    // Prophetic
    // Vampirism
    // Survivalist
    // Accelerate
    // Lucky
    // Prodigy
    // Unnatural
    // Artisan

    public static void processPerk(Player player, String perk) {
        if (
                ((perk.equals("Berserker") && player.Berserker == 0) ||
                        (perk.equals("Bullseye") && player.Bullseye == 0) ||
                        (perk.equals("Prophetic") && player.Prophetic == 0) ||
                        (perk.equals("Vampirism") && player.Vampirism == 0) ||
                        (perk.equals("Survivalist") && player.Survivalist == 0) ||
                        (perk.equals("Accelerate") && player.Accelerate == 0) ||
                        (perk.equals("Lucky") && player.Lucky == 0) ||
                        (perk.equals("Prodigy") && player.Prodigy == 0) ||
                        (perk.equals("Unnatural") && player.Unnatural == 0) ||
                        (perk.equals("Artisan") && player.Artisan == 0))
        ) {
        } else if (player.perkUpgrades == 0 && player.prestige > 0 && player.prestige < 10) {
            player.getPacketSender().sendInterfaceRemoval();
            DialogueManager.sendStatement(player, "You must prestige before upgrading any perks.");
            return;
        }

        switch (perk) {

            case "Berserker":
                if (player.Berserker == 0 && player.tutorialIsland == 7) {
                    player.Berserker = 1;
                    player.tutorialIsland = 8;
                    player.getPacketSender().sendMessage("@blu@You have just unlocked Berserker!");
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if (player.Berserker == 0 && player.getPaePoints() >= 500) {
                    player.Berserker = 1;
                    player.setPaePoints(player.getPaePoints() - 500);
                    player.getPacketSender().sendMessage("@blu@You have just unlocked Berserker!");
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Berserker == 1) && (player.getPaePoints() >= 2500) && (player.perkUpgrades > 0)) {
                    player.Berserker = 2;
                    player.perkUpgrades -= 1;
                    player.getPacketSender().sendMessage("@blu@You have just upgraded Berserker!");
                    player.setPaePoints(player.getPaePoints() - 2500);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Berserker == 2) && (player.getPaePoints() >= 5000) && (player.perkUpgrades > 0)) {
                    player.Berserker = 3;
                    player.perkUpgrades -= 1;
                    player.getPacketSender().sendMessage("@blu@You have just upgraded Berserker!");
                    player.setPaePoints(player.getPaePoints() - 5000);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Berserker == 3) && (player.getPaePoints() >= 10000) && (player.perkUpgrades > 0)) {
                    player.Berserker = 4;
                    player.perkUpgrades -= 1;
                    player.getPacketSender().sendMessage("@blu@You have just upgraded Berserker!");
                    player.setPaePoints(player.getPaePoints() - 10000);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Berserker == 0) && (player.getPaePoints() < 500)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 500 PaePoints to unlock!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else if ((player.Berserker == 1) && (player.getPaePoints() < 2500)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 2500 PaePoints to upgrade!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else if ((player.Berserker == 2) && (player.getPaePoints() < 5000)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 5000 PaePoints to upgrade!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else if ((player.Berserker == 3) && (player.getPaePoints() < 10000)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 10000 PaePoints to upgrade!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else {
                    player.getPacketSender().sendInterfaceRemoval();
                    DialogueManager.sendStatement(player, "You must prestige before earning any level 2 perks.");
                }
                break;

            case "Bullseye":
                if (player.Bullseye == 0 && player.tutorialIsland == 7) {
                    player.Bullseye = 1;
                    player.tutorialIsland = 8;
                    player.getPacketSender().sendMessage("@blu@You have just unlocked Bullseye!");
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if (player.Bullseye == 0 && player.getPaePoints() >= 500) {
                    player.Bullseye = 1;
                    player.setPaePoints(player.getPaePoints() - 500);
                    player.getPacketSender().sendMessage("@blu@You have just unlocked Bullseye!");
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Bullseye == 1) && (player.getPaePoints() >= 2500) && (player.perkUpgrades > 0)) {
                    player.Bullseye = 2;
                    player.perkUpgrades -= 1;
                    player.getPacketSender().sendMessage("@blu@You have just upgraded Bullseye!");
                    player.setPaePoints(player.getPaePoints() - 2500);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Bullseye == 2) && (player.getPaePoints() >= 5000) && (player.perkUpgrades > 0)) {
                    player.Bullseye = 3;
                    player.perkUpgrades -= 1;
                    player.getPacketSender().sendMessage("@blu@You have just upgraded Bullseye!");
                    player.setPaePoints(player.getPaePoints() - 5000);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Bullseye == 3) && (player.getPaePoints() >= 10000) && (player.perkUpgrades > 0)) {
                    player.Bullseye = 4;
                    player.perkUpgrades -= 1;
                    player.getPacketSender().sendMessage("@blu@You have just upgraded Bullseye!");
                    player.setPaePoints(player.getPaePoints() - 10000);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Bullseye == 0) && (player.getPaePoints() < 500)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 500 PaePoints to unlock!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else if ((player.Bullseye == 1) && (player.getPaePoints() < 2500)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 2500 PaePoints to upgrade!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else if ((player.Bullseye == 2) && (player.getPaePoints() < 5000)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 5000 PaePoints to upgrade!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else if ((player.Bullseye == 3) && (player.getPaePoints() < 10000)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 10000 PaePoints to upgrade!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else {
                    player.getPacketSender().sendInterfaceRemoval();
                    DialogueManager.sendStatement(player, "You must prestige before earning any level 2 perks.");
                }
                break;

            case "Prophetic":
                if (player.Prophetic == 0 && player.tutorialIsland == 7) {
                    player.Prophetic = 1;
                    player.tutorialIsland = 8;
                    player.getPacketSender().sendMessage("@blu@You have just unlocked Prophetic!");
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if (player.Prophetic == 0 && player.getPaePoints() >= 500) {
                    player.Prophetic = 1;
                    player.getPacketSender().sendMessage("@blu@You have just unlocked Prophetic!");
                    player.setPaePoints(player.getPaePoints() - 500);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Prophetic == 1) && (player.getPaePoints() >= 2500) && (player.perkUpgrades > 0)) {
                    player.Prophetic = 2;
                    player.perkUpgrades -= 1;
                    player.getPacketSender().sendMessage("@blu@You have just upgraded Prophetic!");
                    player.setPaePoints(player.getPaePoints() - 2500);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Prophetic == 2) && (player.getPaePoints() >= 5000) && (player.perkUpgrades > 0)) {
                    player.Prophetic = 3;
                    player.perkUpgrades -= 1;
                    player.getPacketSender().sendMessage("@blu@You have just upgraded Prophetic!");
                    player.setPaePoints(player.getPaePoints() - 5000);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Prophetic == 3) && (player.getPaePoints() >= 10000) && (player.perkUpgrades > 0)) {
                    player.Prophetic = 4;
                    player.perkUpgrades -= 1;
                    player.getPacketSender().sendMessage("@blu@You have just upgraded Prophetic!");
                    player.setPaePoints(player.getPaePoints() - 10000);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Prophetic == 0) && (player.getPaePoints() < 500)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 500 PaePoints to unlock!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else if ((player.Prophetic == 1) && (player.getPaePoints() < 2500)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 2500 PaePoints to upgrade!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else if ((player.Prophetic == 2) && (player.getPaePoints() < 5000)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 5000 PaePoints to upgrade!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else if ((player.Prophetic == 3) && (player.getPaePoints() < 10000)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 10000 PaePoints to upgrade!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else {
                    player.getPacketSender().sendInterfaceRemoval();
                    DialogueManager.sendStatement(player, "You must prestige before earning any level 2 perks.");
                }
                break;

            case "Vampirism":
                if ((player.Vampirism == 0) && (player.getPaePoints() >= 500)) {
                    player.Vampirism = 1;
                    player.getPacketSender().sendMessage("@blu@You have just unlocked Vampirism!");
                    player.setPaePoints(player.getPaePoints() - 500);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Vampirism == 1) && (player.getPaePoints() >= 2500) && (player.perkUpgrades > 0)) {
                    player.Vampirism = 2;
                    player.perkUpgrades -= 1;
                    player.getPacketSender().sendMessage("@blu@You have just upgraded Vampirism!");
                    player.setPaePoints(player.getPaePoints() - 2500);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Vampirism == 2) && (player.getPaePoints() >= 5000) && (player.perkUpgrades > 0)) {
                    player.Vampirism = 3;
                    player.perkUpgrades -= 1;
                    player.getPacketSender().sendMessage("@blu@You have just upgraded Vampirism!");
                    player.setPaePoints(player.getPaePoints() - 5000);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Vampirism == 3) && (player.getPaePoints() >= 10000) && (player.perkUpgrades > 0)) {
                    player.Vampirism = 4;
                    player.perkUpgrades -= 1;
                    player.getPacketSender().sendMessage("@blu@You have just upgraded Vampirism!");
                    player.setPaePoints(player.getPaePoints() - 10000);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Vampirism == 0) && (player.getPaePoints() < 500)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 500 PaePoints to unlock!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else if ((player.Vampirism == 1) && (player.getPaePoints() < 2500)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 2500 PaePoints to upgrade!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else if ((player.Vampirism == 2) && (player.getPaePoints() < 5000)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 5000 PaePoints to upgrade!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else if ((player.Vampirism == 3) && (player.getPaePoints() < 10000)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 10000 PaePoints to upgrade!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else {
                    player.getPacketSender().sendInterfaceRemoval();
                    DialogueManager.sendStatement(player, "You must prestige before earning any level 2 perks.");
                }
                break;

            case "Survivalist":
                if ((player.Survivalist == 0) && (player.getPaePoints() >= 500)) {
                    player.Survivalist = 1;
                    player.getPacketSender().sendMessage("@blu@You have just unlocked Survivalist!");
                    player.setPaePoints(player.getPaePoints() - 500);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Survivalist == 1) && (player.getPaePoints() >= 2500) && (player.perkUpgrades > 0)) {
                    player.Survivalist = 2;
                    player.perkUpgrades -= 1;
                    player.getPacketSender().sendMessage("@blu@You have just upgraded Survivalist!");
                    player.setPaePoints(player.getPaePoints() - 2500);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Survivalist == 2) && (player.getPaePoints() >= 5000) && (player.perkUpgrades > 0)) {
                    player.Survivalist = 3;
                    player.perkUpgrades -= 1;
                    player.getPacketSender().sendMessage("@blu@You have just upgraded Survivalist!");
                    player.setPaePoints(player.getPaePoints() - 5000);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Survivalist == 3) && (player.getPaePoints() >= 10000) && (player.perkUpgrades > 0)) {
                    player.Survivalist = 4;
                    player.perkUpgrades -= 1;
                    player.getPacketSender().sendMessage("@blu@You have just upgraded Survivalist!");
                    player.setPaePoints(player.getPaePoints() - 10000);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Survivalist == 0) && (player.getPaePoints() < 500)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 500 PaePoints to unlock!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else if ((player.Survivalist == 1) && (player.getPaePoints() < 2500)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 2500 PaePoints to upgrade!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else if ((player.Survivalist == 2) && (player.getPaePoints() < 5000)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 5000 PaePoints to upgrade!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else if ((player.Survivalist == 3) && (player.getPaePoints() < 10000)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 10000 PaePoints to upgrade!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else {
                    player.getPacketSender().sendInterfaceRemoval();
                    DialogueManager.sendStatement(player, "You must prestige before earning any level 2 perks.");
                }
                break;

            case "Accelerate":
                if ((player.Accelerate == 0) && (player.getPaePoints() >= 0) && player.tutorialIsland == 3) {
                    player.Accelerate = 1;
                    player.tutorialIsland = 4;
                    player.getPacketSender().sendMessage("@blu@You have just unlocked Accelerate!");
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                    DialogueManager.start(player, 1008);
                } else if ((player.Accelerate == 0) && (player.getPaePoints() >= 500)) {
                    player.Accelerate = 1;
                    player.setPaePoints(player.getPaePoints() - 500);
                    player.getPacketSender().sendMessage("@blu@You have just unlocked Accelerate!");
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Accelerate == 1) && (player.getPaePoints() >= 2500) && (player.perkUpgrades > 0)) {
                    player.Accelerate = 2;
                    player.perkUpgrades -= 1;
                    player.getPacketSender().sendMessage("@blu@You have just upgraded Accelerate!");
                    player.setPaePoints(player.getPaePoints() - 2500);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Accelerate == 2) && (player.getPaePoints() >= 5000) && (player.perkUpgrades > 0)) {
                    player.Accelerate = 3;
                    player.perkUpgrades -= 1;
                    player.getPacketSender().sendMessage("@blu@You have just upgraded Accelerate!");
                    player.setPaePoints(player.getPaePoints() - 5000);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Accelerate == 3) && (player.getPaePoints() >= 10000) && (player.perkUpgrades > 0)) {
                    player.Accelerate = 4;
                    player.perkUpgrades -= 1;
                    player.getPacketSender().sendMessage("@blu@You have just upgraded Accelerate!");
                    player.setPaePoints(player.getPaePoints() - 10000);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Accelerate == 0) && (player.getPaePoints() < 500)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 500 PaePoints to unlock!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else if ((player.Accelerate == 1) && (player.getPaePoints() < 2500)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 2500 PaePoints to upgrade!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else if ((player.Accelerate == 2) && (player.getPaePoints() < 5000)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 5000 PaePoints to upgrade!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else if ((player.Accelerate == 3) && (player.getPaePoints() < 10000)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 10000 PaePoints to upgrade!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else {
                    player.getPacketSender().sendInterfaceRemoval();
                    DialogueManager.sendStatement(player, "You must prestige before earning any level 2 perks.");
                }
                break;

            case "Lucky":
                if ((player.Lucky == 0) && (player.getPaePoints() >= 500)) {
                    player.Lucky = 1;
                    player.getPacketSender().sendMessage("@blu@You have just unlocked Lucky!");
                    player.setPaePoints(player.getPaePoints() - 500);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Lucky == 1) && (player.getPaePoints() >= 2500) && (player.perkUpgrades > 0)) {
                    player.Lucky = 2;
                    player.perkUpgrades -= 1;
                    player.getPacketSender().sendMessage("@blu@You have just upgraded Lucky!");
                    player.setPaePoints(player.getPaePoints() - 2500);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Lucky == 2) && (player.getPaePoints() >= 5000) && (player.perkUpgrades > 0)) {
                    player.Lucky = 3;
                    player.perkUpgrades -= 1;
                    player.getPacketSender().sendMessage("@blu@You have just upgraded Lucky!");
                    player.setPaePoints(player.getPaePoints() - 5000);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Lucky == 3) && (player.getPaePoints() >= 10000) && (player.perkUpgrades > 0)) {
                    player.Lucky = 4;
                    player.perkUpgrades -= 1;
                    player.getPacketSender().sendMessage("@blu@You have just upgraded Lucky!");
                    player.setPaePoints(player.getPaePoints() - 10000);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Lucky == 0) && (player.getPaePoints() < 500)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 500 PaePoints to unlock!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else if ((player.Lucky == 1) && (player.getPaePoints() < 2500)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 2500 PaePoints to upgrade!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else if ((player.Lucky == 2) && (player.getPaePoints() < 5000)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 5000 PaePoints to upgrade!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else if ((player.Lucky == 3) && (player.getPaePoints() < 10000)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 10000 PaePoints to upgrade!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else {
                    player.getPacketSender().sendInterfaceRemoval();
                    DialogueManager.sendStatement(player, "You must prestige before earning any level 2 perks.");
                }
                break;

            case "Prodigy":
                if ((player.Prodigy == 0) && (player.getPaePoints() >= 500)) {
                    player.Prodigy = 1;
                    player.getPacketSender().sendMessage("@blu@You have just unlocked Prodigy!");
                    player.setPaePoints(player.getPaePoints() - 500);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Prodigy == 1) && (player.getPaePoints() >= 2500) && (player.perkUpgrades > 0)) {
                    player.Prodigy = 2;
                    player.perkUpgrades -= 1;
                    player.getPacketSender().sendMessage("@blu@You have just upgraded Prodigy!");
                    player.setPaePoints(player.getPaePoints() - 2500);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Prodigy == 2) && (player.getPaePoints() >= 5000) && (player.perkUpgrades > 0)) {
                    player.Prodigy = 3;
                    player.perkUpgrades -= 1;
                    player.getPacketSender().sendMessage("@blu@You have just upgraded Prodigy!");
                    player.setPaePoints(player.getPaePoints() - 5000);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Prodigy == 3) && (player.getPaePoints() >= 10000) && (player.perkUpgrades > 0)) {
                    player.Prodigy = 4;
                    player.perkUpgrades -= 1;
                    player.getPacketSender().sendMessage("@blu@You have just upgraded Prodigy!");
                    player.setPaePoints(player.getPaePoints() - 10000);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Prodigy == 0) && (player.getPaePoints() < 500)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 500 PaePoints to unlock!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else if ((player.Prodigy == 1) && (player.getPaePoints() < 2500)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 2500 PaePoints to upgrade!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else if ((player.Prodigy == 2) && (player.getPaePoints() < 5000)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 5000 PaePoints to upgrade!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else if ((player.Prodigy == 3) && (player.getPaePoints() < 10000)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 10000 PaePoints to upgrade!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else {
                    player.getPacketSender().sendInterfaceRemoval();
                    DialogueManager.sendStatement(player, "You must prestige before earning any level 2 perks.");
                }
                break;

            case "Unnatural":
                if ((player.Unnatural == 0) && (player.getPaePoints() >= 500)) {
                    player.Unnatural = 1;
                    player.getPacketSender().sendMessage("@blu@You have just unlocked Unnatural!");
                    player.setPaePoints(player.getPaePoints() - 500);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Unnatural == 1) && (player.getPaePoints() >= 2500) && (player.perkUpgrades > 0)) {
                    player.Unnatural = 2;
                    player.perkUpgrades -= 1;
                    player.getPacketSender().sendMessage("@blu@You have just upgraded Unnatural!");
                    player.setPaePoints(player.getPaePoints() - 2500);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Unnatural == 2) && (player.getPaePoints() >= 5000) && (player.perkUpgrades > 0)) {
                    player.Unnatural = 3;
                    player.perkUpgrades -= 1;
                    player.getPacketSender().sendMessage("@blu@You have just upgraded Unnatural!");
                    player.setPaePoints(player.getPaePoints() - 5000);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Unnatural == 3) && (player.getPaePoints() >= 10000) && (player.perkUpgrades > 0)) {
                    player.Unnatural = 4;
                    player.perkUpgrades -= 1;
                    player.getPacketSender().sendMessage("@blu@You have just upgraded Unnatural!");
                    player.setPaePoints(player.getPaePoints() - 10000);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Unnatural == 0) && (player.getPaePoints() < 500)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 500 PaePoints to unlock!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else if ((player.Unnatural == 1) && (player.getPaePoints() < 2500)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 2500 PaePoints to upgrade!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else if ((player.Unnatural == 2) && (player.getPaePoints() < 5000)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 5000 PaePoints to upgrade!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else if ((player.Unnatural == 3) && (player.getPaePoints() < 10000)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 10000 PaePoints to upgrade!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else {
                    player.getPacketSender().sendInterfaceRemoval();
                    DialogueManager.sendStatement(player, "You must prestige before earning any level 2 perks.");
                }
                break;

            case "Artisan":
                if ((player.Artisan == 0) && (player.getPaePoints() >= 500)) {
                    player.Artisan = 1;
                    player.getPacketSender().sendMessage("@blu@You have just unlocked Artisan!");
                    player.setPaePoints(player.getPaePoints() - 500);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Artisan == 1) && (player.getPaePoints() >= 2500) && (player.perkUpgrades > 0)) {
                    player.Artisan = 2;
                    player.perkUpgrades -= 1;
                    player.getPacketSender().sendMessage("@blu@You have just upgraded Artisan!");
                    player.setPaePoints(player.getPaePoints() - 2500);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Artisan == 2) && (player.getPaePoints() >= 5000) && (player.perkUpgrades > 0)) {
                    player.Artisan = 3;
                    player.perkUpgrades -= 1;
                    player.getPacketSender().sendMessage("@blu@You have just upgraded Artisan!");
                    player.setPaePoints(player.getPaePoints() - 5000);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Artisan == 3) && (player.getPaePoints() >= 10000) && (player.perkUpgrades > 0)) {
                    player.Artisan = 4;
                    player.perkUpgrades -= 1;
                    player.getPacketSender().sendMessage("@blu@You have just upgraded Artisan!");
                    player.setPaePoints(player.getPaePoints() - 10000);
                    InformationPanel.refreshPanel(player);
                    PerkInterface.showPerkInterface(player);
                } else if ((player.Artisan == 0) && (player.getPaePoints() < 500)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 500 PaePoints to unlock!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else if ((player.Artisan == 1) && (player.getPaePoints() < 2500)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 2500 PaePoints to upgrade!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else if ((player.Artisan == 2) && (player.getPaePoints() < 5000)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 5000 PaePoints to upgrade!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else if ((player.Artisan == 3) && (player.getPaePoints() < 10000)) {
                    player.getPacketSender().sendMessage("@red@This perk costs 10000 PaePoints to upgrade!");
                    player.getPacketSender().sendInterfaceRemoval();
                } else {
                    player.getPacketSender().sendInterfaceRemoval();
                    DialogueManager.sendStatement(player, "You must prestige before earning any level 2 perks.");
                }
                break;


        }
    }


}