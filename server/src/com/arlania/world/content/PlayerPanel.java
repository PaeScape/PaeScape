package com.arlania.world.content;

import com.arlania.model.Flag;
import com.arlania.model.Item;
import com.arlania.model.Locations.Location;
import com.arlania.model.Skill;
import com.arlania.model.container.impl.Bank;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.world.content.Artisan.ArtisanMenu;
import com.arlania.world.content.unnatural.UnnaturalMenu;
import com.arlania.world.content.achievements.AchievementInterface;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.content.interfaces.*;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.updating.NPCUpdating;

public class PlayerPanel {

    public static final String LINE_START = "   > ";

    public static void refreshPanel(Player player) {
        int counter = 39159;
        player.getPacketSender().sendString(counter++, "@or3@-@whi@ Tools");
        player.getPacketSender().sendString(counter++, LINE_START.replace(">", "*") + "@or1@Drop Tables");
        player.getPacketSender().sendString(counter++, LINE_START.replace(">", "*") + "@or1@Perks");
        player.getPacketSender().sendString(counter++, LINE_START.replace(">", "*") + "@or1@Achievements");
        player.getPacketSender().sendString(counter++, LINE_START.replace(">", "*") + "@or1@Collection Log");
        //player.getPacketSender().sendString(counter++, LINE_START.replace(">", "*") + "@or1@General settings");
        player.getPacketSender().sendString(counter++, "");
        player.getPacketSender().sendString(counter++, "@or3@-@whi@ Personal Event");
        player.getPacketSender().sendString(counter++, LINE_START.replace(">", "*") + "@or1@Event Type: " + (player.personalEvent ? "@gre@" : "@red@") + player.personalEventType);
        player.getPacketSender().sendString(counter++, LINE_START.replace(">", "*") + "@or1@Personal Event: " + (player.personalEvent ? "@gre@Active" : "@red@Inactive"));

        player.getPacketSender().sendString(counter++, "");
        player.getPacketSender().sendString(counter++, "@or3@-@whi@ Settings");
        player.getPacketSender().sendString(counter++, LINE_START.replace(">", "*") + "@or1@General settings");
        player.getPacketSender().sendString(counter++, LINE_START.replace(">", "*") + "@or1@HUD settings");
        player.getPacketSender().sendString(counter++, LINE_START.replace(">", "*") + "@or1@World Message Filter");
        player.getPacketSender().sendString(counter++, LINE_START.replace(">", "*") + "@or1@Personal Message Filter");
        player.getPacketSender().sendString(counter++, LINE_START.replace(">", "*") + "@or1@Client settings");
        player.getPacketSender().sendString(counter++, LINE_START.replace(">", "*") + "@or1@Difficulty settings");
        //player.getPacketSender().sendString(counter++, LINE_START.replace(">", "*") + "@or1@General settings");
        player.getPacketSender().sendString(counter++, "");

        player.getPacketSender().sendString(counter++, "-@whi@ Slayer Information");
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Master: @yel@" + player.getSlayer().getSlayerMaster());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Task: @yel@" + player.getSlayer().getSlayerTask());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Task Amount: @yel@" + player.getSlayer().getAmountToSlay());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Task Streak: @yel@" + player.getSlayer().getTaskStreak());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Task Quantity: @yel@" + player.slayerQty);
        player.getPacketSender().sendString(counter++, LINE_START + "@or2@Repeat Last Slayer Task");

        player.getPacketSender().sendString(counter++, "");

        player.getPacketSender().sendString(counter++, "-@whi@ Skiller Information");
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Skill: @yel@" + player.getSkiller().getSkillerTask().getSkill());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Task: @yel@" + player.getSkiller().getSkillerTask());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Task Amount: @yel@" + player.getSkiller().getAmountToSkill());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Task Streak: @yel@" + player.getSkiller().getTaskStreak());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Task Quantity: @yel@" + player.artisanQty);
        player.getPacketSender().sendString(counter++, LINE_START + "@or2@Repeat Last Skiller Task");


        player.getPacketSender().sendString(counter++, "");
        player.getPacketSender().sendString(counter++, "-@whi@ Other");
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Double XP: @yel@" + (player.activeDoubleXP ? "@gre@Active" : "@red@Inactive"));
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bonus XP: @yel@" + (player.activeBonusXP ? "@gre@Active" : "@red@Inactive"));
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bonus RNG: @yel@" + (player.activeBonusRNG ? "@gre@Active" : "@red@Inactive"));
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Alchables: @yel@" + (player.alchablecoins ? "@gre@Coins" : "@red@Items"));
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Exp Lock: @yel@" + (player.experienceLocked() ? "@red@Locked" : "@gre@Unlocked"));
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Prodigy Display: @yel@" + (player.prodigyDisplay ? "@gre@On" : "@red@Off"));
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@HUD: @yel@" + (player.displayHUD ? "@gre@On" : "@red@Off"));
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Magic Paper: @yel@" + player.magicPaperAmount);
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bank Amount: @yel@" + player.withdrawAmount);
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Home Tele: @yel@" + player.homeTele);
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Looter Banking: @yel@" + (player.looterBanking ? "@gre@On" : "@red@Off"));
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Aggro Radius: @yel@" + "@gre@" + player.aggressorRadius);
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Appearance: @yel@" + (player.overrideAppearance == 0? "@gre@Equipment" : "@gre@Overrides"));


        player.getPacketSender().sendString(counter++, "");
        player.getPacketSender().sendString(counter++, "");
        player.getPacketSender().sendString(counter++, "");
        player.getPacketSender().sendString(counter++, "");
        player.getPacketSender().sendString(counter++, "");

        player.getPacketSender().sendString(counter++, "");
        player.getPacketSender().sendString(counter++, "");
        player.getPacketSender().sendString(counter++, "");
        player.getPacketSender().sendString(counter++, "");
        player.getPacketSender().sendString(counter++, "");


    }

    private static long getAccountWorth(Player other) {
        int amount = 0;
        for (Item item : other.getInventory().getValidItemsArray()) {
            if (item == null) {
                continue;
            }
            amount += item.getDefinition().getValue();
        }
        for (Item item : other.getEquipment().getValidItemsArray()) {
            if (item == null) {
                continue;
            }
            amount += item.getDefinition().getValue();
        }
        for (Bank bank : other.getBanks()) {
            if (bank == null) {
                continue;
            }
            for (Item item : bank.getValidItemsArray()) {
                amount += item.getDefinition().getValue();
            }
        }
        return amount;
    }


    public static void buttons(Player player, int button) {


        switch (button) {

            /*case 39160:
                player.getPacketSender().sendTabInterface(GameSettings.QUESTS_TAB, 46343);
                StaffList.updateInterface(player);
                break;*/

            case 39160:
                player.getDropTableInterface().open(true, true);
                break;

            case 39161:
                player.activeInterface = "perkmenu";
                PerkMenu.showPerkInterface(player);
                break;

            case 39162:
                AchievementInterface.open(player);
                break;

            case 39163:
                player.getCollectionLog().openInterface();
                break;

            case 39164:
                if (player.mysteryPass) {
                    MysteryPass.showMysteryPass(player);
                    player.activeInterface = "mysterypass";
                } else if (!player.mysteryPass) {
                    player.getPacketSender().sendMessage("You must activate a Mystery Pass!");
                }
                break;

            /*case 39165:
                if (player.battlePass) {
                    BattlePass.showBattlePass(player);
                    player.activeInterface = "battlepass";
                } else if (!player.battlePass) {
                    player.getPacketSender().sendMessage("You must activate a Battle Pass!");
                }
                break;*/

            case 39166: //Events settings
                EventInterface.showEventInterface(player);
                player.activeInterface = "events";
                break;

            case 39167: //Activate personal event

                if (player.personalEvent && (player.droprateEvent || player.doubleLoot) && player.getLocation() != Location.HOME_BANK && player.getLocation() != Location.RAIDS_LOBBY) {
                    player.getPacketSender().sendMessage("@red@You must deactivate this event at the Home Bank or a Raids Lobby!");
                }  else if (player.personalEvent && !player.droprateEvent && !player.doubleLoot) {
                    PersonalEvent.endPersonal(player);
                } else {
                    PersonalEvent.activate(player);
                }
                PlayerPanel.refreshPanel(player);
                break;

            case 39170: //General settings
                GeneralSettings.showInterface(player);
                player.activeInterface = "settings";
                break;

            case 930: //HUD settings
            case 934: //HUD settings
                HUDinterface.showHUDInterface(player);
                player.activeInterface = "hud";
                break;

            case 941:
            case 942: //World Message Filter
                WorldMessageFilter.showInterface(player);
                player.activeInterface = "worldmessagefilter";
                break;

            case 39173: //Personal Message Filter
                PersonalMessageFilter.showInterface(player);
                player.activeInterface = "personalmessagefilter";
                break;

            case 39174:
                if (player.getInterfaceId() <= 0) {
                    player.getPacketSender().sendInterface(40030);
                    player.activeInterface = "clientSettings";
                } else
                    player.getPacketSender().sendMessage("Please close the interface you have open before doing this.");
                break;

            case 39175:
                if (player.getInterfaceId() <= 0 && player.getRegionInstance() == null) {
                    DifficultySettings.showInterface(player);
                    player.activeInterface = "difficultySettings";
                } else if (player.getRegionInstance() != null) {
                    player.getPacketSender().sendMessage("You can't edit your Difficulty in an instance. Your Difficulty is set to level: " + player.difficulty);
                } else
                    player.getPacketSender().sendMessage("Please close the interface you have open before doing this.");

                break;

            case 39178:
                DialogueManager.start(player, 61);
                player.setDialogueActionId(28);
                break;

            case 39179:
                if (player.Unnatural >= 2) {
                    player.activeMenu = "slayer";
                    UnnaturalMenu.openTab(player, 60602);
                }
                break;


            case 39182:
                if (player.Unnatural >= 4) {

                    switch (player.slayerQty) {
                        case "random":
                            player.slayerQty = "max";
                            break;
                        case "max":
                            player.slayerQty = "min";
                            break;
                        case "min":
                            player.slayerQty = "random";
                            break;
                        case "default":
                            player.slayerQty = "random";
                            break;
                    }
                }
                break;

            case 39183:
                if (player.Unnatural >= 3) {
                    player.getSlayer().assignRepeatTask(player);
                } else {
                    player.sendMessage("@red@You must have at least Unnatural 3 unlocked for this feature.");
                }

                break;

            case 39186:
            case 39187:
                if (player.Artisan >= 2) {
                    player.activeMenu = "skiller";
                    ArtisanMenu.openTab(player, 60602);
                } else
                    player.sendMessage("You can talk to Max at home to reset your task.");
                break;


            case 39190:
                if (player.Artisan >= 4) {

                    switch (player.artisanQty) {
                        case "random":
                            player.artisanQty = "max";
                            break;
                        case "max":
                            player.artisanQty = "min";
                            break;
                        case "min":
                            player.artisanQty = "random";
                            break;
                        default:
                            player.artisanQty = "random";
                            break;
                    }
                } else {
                    player.sendMessage("@red@You must have Artisan 4 unlocked for this feature.");
                }
                break;

            case 39191:
                if (player.Artisan >= 3) {
                    player.getSkiller().assignRepeatTask(player);
                } else {
                    player.sendMessage("@red@You must have Artisan unlocked for this feature.");
                }

                break;

            case 39194:
                if (player.doubleExpTimer > 0) {
                    player.activeDoubleXP = !player.activeDoubleXP;
                    player.sendMessage("Your Double XP is now: " + (player.activeDoubleXP ? "@gre@Active" : "@red@Inactive"));
                } else
                    player.sendMessage("@red@You are out of Double Experience!");

                break;
            case 39195:
                if (player.getBonusTime() > 0) {
                    player.activeBonusXP = !player.activeBonusXP;
                    player.sendMessage("Your bonus XP is now: " + (player.activeBonusXP ? "@gre@Active" : "@red@Inactive"));
                } else
                    player.sendMessage("@red@You have no bonus time!");

                break;
            case 39196:
                if (player.getBonusTime() < 0)
                    player.sendMessage("@red@You have no bonus time!");

                else if (!player.activeBonusRNG) {

                    if (player.getLocation() == Location.PESTILENT_BLOAT ||
                            player.getLocation() == Location.MAIDEN_SUGADINTI ||
                            player.getLocation() == Location.VERZIK_VITUR ||
                            player.getLocation() == Location.TEKTON ||
                            player.getLocation() == Location.SKELETAL_MYSTICS ||
                            player.getLocation() == Location.OLM ||
                            player.getLocation() == Location.GWD_RAID ||
                            player.getLocation() == Location.GRAVEYARD ||
                            player.getLocation() == Location.CHAOS_RAIDS) {
                        player.getPacketSender().sendMessage("@red@You can't activate Bonus RNG here.");

                    } else {
                        player.activeBonusRNG = true;
                        player.sendMessage("Your bonus RNG is now: " + (player.activeBonusRNG ? "@gre@Active" : "@red@Inactive"));
                    }

                } else
                    player.sendMessage("@red@You must log out to turn off bonus RNG.");
                break;

            //alchables into coins
            case 39197:
                if (player.getStaffRights().getStaffRank() >= 1) {
                    player.alchablecoins = !player.alchablecoins;
                    player.sendMessage("Your alchables will now drop as: " + (player.alchablecoins ? "@gre@Coins" : "@red@Items"));
                    PlayerPanel.refreshPanel(player);
                } else {
                    player.sendMessage("@red@You need the Looter perk to activate this!");
                }

                break;
            case 39198:
                player.setExperienceLocked(!player.experienceLocked());
                player.sendMessage("Your experience is now: " + (player.experienceLocked() ? "@red@Locked" : "@gre@Unlocked"));
                break;

            case 39199:
                player.prodigyDisplay = !player.prodigyDisplay;
                player.sendMessage("Your +12 Prodigy level boost display is now: " + (player.prodigyDisplay ? "@gre@On" : "@red@Off"));


                if (!player.prodigyDisplay) {
                    for (Skill skill : Skill.values()) {
                        int level = player.getSkillManager().getMaxLevel(skill);
                        player.getSkillManager().setMaxLevel(skill, player.getSkillManager().getMaxLevel(skill));
                        player.getSkillManager().setCurrentLevel(skill, level, true);
                    }


                }

                break;

            case 39200:
                player.displayHUD = !player.displayHUD;
                player.sendMessage("Your HUD is now: " + (player.displayHUD ? "@gre@On" : "@red@Off"));
                PlayerPanel.refreshPanel(player);
                break;

            //Banker's Note
            case 39201:

                switch (player.magicPaperAmount) {

                    case 1:
                        player.magicPaperAmount = 5;
                        break;

                    case 5:
                        player.magicPaperAmount = 12;
                        break;

                    case 12:
                        player.magicPaperAmount = 28;
                        break;

                    case 28:
                        player.magicPaperAmount = 1;
                        break;

                }

                PlayerPanel.refreshPanel(player);
                break;

            case 39202:

                switch (player.withdrawAmount) {

                    case "1":
                        player.withdrawAmount = "5";
                        break;

                    case "5":
                        player.withdrawAmount = "10";
                        break;

                    case "10":
                        player.withdrawAmount = "All";
                        break;

                    case "All":
                        player.withdrawAmount = "1";
                        break;

                }

                PlayerPanel.refreshPanel(player);
                break;

            case 39203:

                switch (player.homeTele) {

                    case "Home":
                        if (player.getStaffRights().getStaffRank() > 0)
                            player.homeTele = "DZ";
                        else
                            player.homeTele = "Raids Lobby";
                        break;

                    case "DZ":
                        player.homeTele = "Raids Lobby";
                        break;

                    case "Raids Lobby":
                        player.homeTele = "Home";
                        break;

                }

                PlayerPanel.refreshPanel(player);
                break;

            case 39204:
                if(player.getStaffRights().getStaffRank() > 2  || player.getInventory().contains(221140) || player.getEquipment().contains(221140)) {
                    player.looterBanking = !player.looterBanking;
                }

                break;

            case 39205:

                if (player.aggressorRadius < 10) {
                    player.aggressorRadius++;
                } else {
                    player.aggressorRadius = 1;
                }

                NPCUpdating.update(player);

                break;

            case 39206:
                if(player.overrideAppearance == 1) {
                    player.overrideAppearance = 0;
                    player.getPacketSender().sendInterfaceRemoval();
                    player.getUpdateFlag().flag(Flag.APPEARANCE);
                } else {

                    for(int i = 0; i < player.getOverrides().capacity(); i++) {
                        if (player.getOverrides().forSlot(i).getId() > 0) {

                            String itemName = ItemDefinition.forId(player.getOverrides().forSlot(i).getId()).getName();
                            boolean hasItem = false;
                            
                            
                            if (Bank.getTabForItem(player, player.getOverrides().forSlot(i).getId()) > 0) {
                                hasItem = true;
                            }

                            if (player.getBank(Bank.getTabForItem(player, player.getOverrides().forSlot(i).getId())).getAmount(player.getOverrides().forSlot(i).getId()) > 0) {
                                hasItem = true;
                            }

                            if (player.getEquipment().contains(player.getOverrides().forSlot(i).getId())) {
                                hasItem = true;
                            }

                            if (player.getInventory().contains(player.getOverrides().forSlot(i).getId())) {
                                hasItem = true;
                            }


                            if (!hasItem) {
                                player.getPacketSender().sendMessage("Your bank does not contain: " + itemName);
                                return;
                            }
                        }
                    }

                    player.overrideAppearance = 1;
                    player.getPacketSender().sendInterfaceRemoval();
                    player.getUpdateFlag().flag(Flag.APPEARANCE);
                }

                break;

            case 39207:

                break;

            case 39208:

                break;
        }
    }
}