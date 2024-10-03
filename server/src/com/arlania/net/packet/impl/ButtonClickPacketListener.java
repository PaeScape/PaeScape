package com.arlania.net.packet.impl;

import com.arlania.GameSettings;
import com.arlania.model.Flag;
import com.arlania.model.GameMode;
import com.arlania.model.Locations.Location;
import com.arlania.model.StaffRights;
import com.arlania.model.container.impl.Bank;
import com.arlania.model.container.impl.Bank.BankSearchAttributes;
import com.arlania.model.container.impl.MailBox;
import com.arlania.model.container.impl.UIMStorage;
import com.arlania.model.definitions.WeaponInterfaces.WeaponInterface;
import com.arlania.model.input.impl.EnterClanChatToJoin;
import com.arlania.model.input.impl.EnterSyntaxToBankSearchFor;
import com.arlania.model.input.impl.PosInput;
import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketListener;
import com.arlania.world.World;
import com.arlania.world.content.Artisan.ArtisanMenu;
import com.arlania.world.content.*;
import com.arlania.world.content.globalevents.GlobalEventHandler;
import com.arlania.world.content.interfaces.Seasonal.SeasonalPerkInterface;
import com.arlania.world.content.seasonal.Interface.Seasonal;
import com.arlania.world.content.Sounds.Sound;
import com.arlania.world.content.unnatural.UnnaturalMenu;
import com.arlania.world.content.clan.ClanChat;
import com.arlania.world.content.clan.ClanChatManager;
import com.arlania.world.content.combat.magic.Autocasting;
import com.arlania.world.content.combat.magic.MagicSpells;
import com.arlania.world.content.combat.prayer.CurseHandler;
import com.arlania.world.content.combat.prayer.PrayerHandler;
import com.arlania.world.content.combat.weapon.CombatSpecial;
import com.arlania.world.content.combat.weapon.FightType;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.content.dialogue.DialogueOptions;
import com.arlania.world.content.droppreview.*;
import com.arlania.world.content.grandexchange.GrandExchange;
import com.arlania.world.content.interfaces.*;
import com.arlania.world.content.marketplace.DeathsCoffer;
import com.arlania.world.content.marketplace.Marketplace;
import com.arlania.world.content.minigames.impl.raidsparty.InviteRaidsPlayer;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.content.skill.ChatboxInterfaceSkillAction;
import com.arlania.world.content.skill.impl.construction.Construction;
import com.arlania.world.content.skill.impl.crafting.LeatherMaking;
import com.arlania.world.content.skill.impl.crafting.Tanning;
import com.arlania.world.content.skill.impl.dungeoneering.Dungeoneering;
import com.arlania.world.content.skill.impl.dungeoneering.DungeoneeringParty;
import com.arlania.world.content.skill.impl.dungeoneering.ItemBinding;
import com.arlania.world.content.skill.impl.fletching.Fletching;
import com.arlania.world.content.skill.impl.herblore.IngridientsBook;
import com.arlania.world.content.skill.impl.slayer.Slayer;
import com.arlania.world.content.skill.impl.smithing.SmithingData;
import com.arlania.world.content.skill.impl.summoning.PouchMaking;
import com.arlania.world.content.skill.impl.summoning.SummoningTab;
import com.arlania.world.content.teleport.TeleportCategory;
import com.arlania.world.content.transportation.TeleportHandler;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.antibotting.actions.ActionButtonClick;

import static com.arlania.net.packet.impl.EquipPacketListener.resetWeapon;

/**
 * This packet listener manages a button that the player has clicked upon.
 *
 * @author Gabriel Hannason
 */

public class ButtonClickPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {

        // TODO: fix negatives: 0xFFFF & negativeId
        int id = packet.readInt();

        player.getActionTracker().offer(new ActionButtonClick(id));

        if (player.getStaffRights() == StaffRights.DEVELOPER) {
            player.getPacketSender().sendMessage("Clicked button: " + id);
        }

        if (checkHandlers(player, id))
            return;

        if (id >= 39159 && id <= 39209) {
            if (player.questTab == "playerpanel")
                PlayerPanel.buttons(player, id);
            else if (player.questTab == "gamblingpanel")
                GambleInterface.buttons(player, id);
            else
                player.questTab = "playerpanel";

        }

        if (id == 934 || id == 941 || id == 942 || id == 930) {

            if (player.activeInterface == "clientSettings")
                Sounds.handleButton(player, id);

            else if (player.questTab == "playerpanel")
                PlayerPanel.buttons(player, id);

            else if (player.questTab == "gamblingpanel")
                GambleInterface.buttons(player, id);

            else
                player.questTab = "playerpanel";

        }

        if (id == 8846 || id == 8823 || id == 8824 || id == 8827 || id == 8837 || id == 8840 || id == 8843 ||
                id == 8859 || id == 8862 || id == 8865 || id == 15303 || id == 15306 || id == 15309) {

            switch (player.activeInterface) {

                case "hud":
                    HUDinterface.handleButton(player, id);
                    break;

                case "perkmenu":
                    PerkMenu.handleButton(player, id);
                    break;

                case "perks":
                    PerkInterface.handleButton(player, id);
                    break;

                case "pets":
                    PetsInterface.handleButton(player, id);
                    break;

                case "achievementabilities":
                    AbilitiesInterface.handleButton(player, id);
                    break;

                case "donatorperks":
                    DonatorPerksInterface.handleButton(player, id);
                    break;

                case "wildernessperks":
                    WildernessPerkInterface.handleButton(player, id);
                    break;

                case "masteryperks":
                    MasteryPerkInterface.handleButton(player, id);
                    break;

                case "seasonalperks":
                    SeasonalPerkInterface.handleButton(player, id);
                    break;

                case "battlepass":
                    BattlePass.handleButton(player, id);
                    break;

                case "mysterypass":
                    MysteryPass.handleButton(player, id);
                    break;

                case "eventpass":
                    EventPass.handleButton(player, id);
                    break;

                case "events":
                    EventInterface.handleButton(player, id);
                    break;

                case "hl":
                    HigherLowerInterface.handleButton(player, id);
                    break;

                case "instancedbosses":
                    InstancedBossInterface.handleButton(player, id);
                    break;

                case "presets":
                    Presets.handleButton(player, id);
                    break;

                case "droprate":
                    DropRateInterface.handleButton(player, id);
                    break;

                case "worldmessagefilter":
                    WorldMessageFilter.handleButton(player, id);
                    break;

                case "personalmessagefilter":
                    PersonalMessageFilter.handleButton(player, id);
                    break;

                case "difficultySettings":
                    DifficultySettings.handleButton(player, id);
                    break;

                case "settings":
                    GeneralSettings.handleButton(player, id);
                    break;

                case "looteroptions":
                    LooterOptions.handleButton(player, id);
                    break;

            }

        }
        switch (id) {
            case MailBox.WITHDRAW_ALL_ITEMS_BUTTON_ID:
                if (player.isMailBoxOpen()) {
                    player.getMailBox().withdrawAllItems();
                }
                break;
            case 26113:
                player.dropLogOrder = !player.dropLogOrder;
                if (player.dropLogOrder) {
                    player.getPA().sendFrame126(26113, "Oldest to Newest");
                } else {
                    player.getPA().sendFrame126(26113, "Newest to Oldest");
                }
                break;
            case 36505:
                ProfileViewing.rate(player, true);
                break;
            case 36508:
                ProfileViewing.rate(player, false);
                break;
            case 38082:
            case 38002:
            case 12729:
                player.getPacketSender().sendInterfaceRemoval();
                break;
            case 5384:
                Bank.BankSearchAttributes.stopSearchProcess(player);
                player.getPacketSender().sendInterfaceRemoval();
                break;
            case 47905:
                player.getPacketSender().sendMessage("Good luck with your drops!");
                player.getPacketSender().sendInterfaceRemoval();
                break;
            case 54098:
                player.getTradingPostManager().openEditor();
                break;


            case 47907:
                if (player.getLocation() == Location.KING_BLACK_DRAGON) {
                    KBD.nextItem(player);
                }
                if (player.getLocation() == Location.CORPOREAL_BEAST) {
                    CORP.nextItem(player);
                }
                if (player.getLocation() == Location.DAGANNOTH_DUNGEON) {
                    DAGS.nextItem(player);
                }
                if (player.getLocation() == Location.KALPHITE_QUEEN) {
                    KALPH.nextItem(player);
                }
                if (player.getLocation() == Location.CERBERUS) {
                    CERB.nextItem(player);
                }
                if (player.getLocation() == Location.NEX) {
                    NEXX.nextItem(player);
                }
                break;

            case 45027:
                GlobalEventHandler.listAllActiveEffects(player);
                break;

            case 35590:
                player.chatFilter = !player.chatFilter;
                player.getPacketSender().sendMessage("Chat Filter: " + (player.chatFilter ? "On" : "Off"));
                break;

            case 47906:
                if (player.getLocation() == Location.KING_BLACK_DRAGON) {
                    KBD.previousItem(player);
                }
                if (player.getLocation() == Location.CORPOREAL_BEAST) {
                    CORP.previousItem(player);
                }
                if (player.getLocation() == Location.DAGANNOTH_DUNGEON) {
                    DAGS.previousItem(player);
                }
                if (player.getLocation() == Location.KALPHITE_QUEEN) {
                    KALPH.previousItem(player);
                }
                if (player.getLocation() == Location.CERBERUS) {
                    CERB.previousItem(player);
                }
                if (player.getLocation() == Location.NEX) {
                    NEXX.previousItem(player);
                }
			/*if (player.getLocation() == Location.GODWARS_DUNGEON) {
				GWD.previousItem(player);
			}*/
                break;

//		case 39163:
//			DropLog.open(player);
//			break;
            case 8658:
                player.getPacketSender().sendFrame126("https://wiki.paescape.online/wiki/Agility", -1);
                break;
            case 8654:
                player.getPacketSender().sendFrame126("https://wiki.paescape.online/wiki/Attack", -1);
                break;
            case 8665:
                player.getPacketSender().sendFrame126("https://wiki.paescape.online/wiki/Cooking", -1);
                break;
            case 8667:
                player.getPacketSender().sendFrame126("https://wiki.paescape.online/wiki/Crafting", -1);
                break;
            case 8660:
                player.getPacketSender().sendFrame126("https://wiki.paescape.online/wiki/Defence", -1);
                break;
            case 28180:
                player.getPacketSender().sendFrame126("https://wiki.paescape.online/wiki/Dungeoneering", -1);
                break;
            case 13928:
                player.getPacketSender().sendFrame126("https://wiki.paescape.online/wiki/Farming", -1);
                break;
            case 8668:
                player.getPacketSender().sendFrame126("https://wiki.paescape.online/wiki/Firemaking", -1);
                break;
            case 8662:
                player.getPacketSender().sendFrame126("https://wiki.paescape.online/wiki/Fishing", -1);
                break;
            case 8670:
                player.getPacketSender().sendFrame126("https://wiki.paescape.online/wiki/Fletching", -1);
                break;
            case 8861:
                player.getPacketSender().sendFrame126("https://wiki.paescape.online/wiki/Herblore", -1);
                break;
            case 8655:
                player.getPacketSender().sendFrame126("https://wiki.paescape.online/wiki/Hitpoints", -1);
                break;
            case 28178:
                player.getPacketSender().sendFrame126("https://wiki.paescape.online/wiki/Hunter", -1);
                break;
            case 8669:
                player.getPacketSender().sendFrame126("https://wiki.paescape.online/wiki/Magic", -1);
                break;
            case 8656:
                player.getPacketSender().sendFrame126("https://wiki.paescape.online/wiki/Mining", -1);
                break;
            case 8666:
                player.getPacketSender().sendFrame126("https://wiki.paescape.online/wiki/Prayer", -1);
                break;
            case 8663:
                player.getPacketSender().sendFrame126("https://wiki.paescape.online/wiki/Ranged", -1);
                break;
            case 8672:
                player.getPacketSender().sendFrame126("https://wiki.paescape.online/wiki/Runecraft", -1);
                break;
            case 28177:
                player.getPacketSender().sendFrame126("https://wiki.paescape.online/wiki/Skiller", -1);
                break;
            case 12162:
                player.getPacketSender().sendFrame126("https://wiki.paescape.online/wiki/Slayer", -1);
                break;
            case 8659:
                player.getPacketSender().sendFrame126("https://wiki.paescape.online/wiki/Smithing", -1);
                break;
            case 8657:
                player.getPacketSender().sendFrame126("https://wiki.paescape.online/wiki/Strength", -1);
                break;
            case 28179:
                player.getPacketSender().sendFrame126("https://wiki.paescape.online/wiki/Summoning", -1);
                break;
            case 8664:
                player.getPacketSender().sendFrame126("https://wiki.paescape.online/wiki/Thieving", -1);
                break;
            case 8671:
                player.getPacketSender().sendFrame126("https://wiki.paescape.online/wiki/Woodcutting", -1);
                break;
            case 1036:
                EnergyHandler.rest(player);
                break;
            case 32388:
                player.getPacketSender().sendTabInterface(GameSettings.QUESTS_TAB, 639); // 26600
                break;
            case 27229:
                DungeoneeringParty.create(player);
                break;
            case 3229:
                player.sendMessage("Common Costs 50 PaeScape Points.");
                break;
            case 3218:
                player.sendMessage("Uncommon Package Costs 100 PaeScape Points.");
                break;
            case 3215:
                player.sendMessage("Extreme Package Costs 200 PaeScape Points.");
                break;
            case 3221:
                player.sendMessage("Rare Package Costs 150 PaeScape Points.");
                break;
            case 3235:
                player.sendMessage("Legendary Package Costs 250 PaeScape Points.");
                break;
            case 3260:
                //player.getPacketSender().sendString(1, "https://app.gpay.io/store/paescape");
                //player.getPacketSender().sendMessage("Attempting to open: https://app.gpay.io/store/paescape");
                break;
            case 26226:
            case 26229:
                if (Dungeoneering.doingDungeoneering(player)) {
                    DialogueManager.start(player, 114);
                    player.setDialogueActionId(71);
                } else {
                    Dungeoneering.leave(player, false, true);
                }
                break;
            case 26244:
            case 26247:
                if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty() != null) {
                    if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty().getOwner().getUsername()
                            .equals(player.getUsername())) {
                        DialogueManager.start(player, id == 26247 ? 106 : 105);
                        player.setDialogueActionId(id == 26247 ? 68 : 67);
                    } else {
                        player.getPacketSender().sendMessage("Only the party owner can change this setting.");
                    }
                }
                break;
            // RAIDS
            case 65003:
                if (player.getLocation() == Location.RAIDS_LOBBY || player.getLocation() == Location.CRASH_ISLAND) {
                    if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null) {
                        if (player.getMinigameAttributes().getRaidsAttributes().getParty().getOwner() != player) {
                            player.getPacketSender().sendMessage("Only the party leader can invite other players.");
                        } else {
                            player.setInputHandling(new InviteRaidsPlayer());
                            player.getPacketSender().sendEnterInputPrompt("Invite Player");
                        }
                    } else {
                        new RaidsParty(player).create();
                    }
                } else
                    player.getPacketSender().sendMessage("You need to be in a Raids lobby to create a Raids party!");
                return;
            case 65006:
                if (player.getLocation() == Location.RAIDS_LOBBY || player.getLocation() == Location.CRASH_ISLAND) {
                    RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();
                    if (party != null) {
                        if (player.getMinigameAttributes().getRaidsAttributes().isInsideRaid()) {
                            player.sendMessage("You can't do this right now.");
                            return;
                        }
                        party.remove(player, false, true);
                    }
                }
                return;
            case 65016:
            case 65020:
            case 65024:
            case 65028:
            case 65032:
                if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null) {
                    if (player.equals(player.getRaidsParty().getOwner())) {
                        if (player.getMinigameAttributes().getRaidsAttributes().getParty().getPlayers()
                                .size() >= ((id - 65016) / 4) + 1) {
                            Player playerToKick = player.getMinigameAttributes().getRaidsAttributes().getParty()
                                    .getPlayers().get((id - 65016) / 4);
                            if (playerToKick == player) {
                                player.sendMessage("You cannot kick yourself!");
                            } else {
                                player.getMinigameAttributes().getRaidsAttributes().getParty().remove(playerToKick, false,
                                        true);

                            }
                        }
                    } else {
                        player.sendMessage("Only the leader of the party can kick players!");
                    }
                }
                return;
            case 14176:
                player.setUntradeableDropItem(null);
                player.getPacketSender().sendInterfaceRemoval();
                break;
            case 14175:
                player.getPacketSender().sendInterfaceRemoval();
                if (player.getUntradeableDropItem() != null && player.getInventory().contains(player.getUntradeableDropItem().getId())) {
                    ItemBinding.unbindItem(player, player.getUntradeableDropItem().getId());
                    player.getInventory().delete(player.getUntradeableDropItem());
                    player.getPacketSender().sendMessage("Your item vanishes as it hits the floor.");
                    Sounds.sendSound(player, Sound.DROP_ITEM);
                }
                player.setUntradeableDropItem(null);
                break;
            case 1013:
                player.getSkillManager().setTotalGainedExp(0);
                break;


            case 35255:
                player.getPacketSender().sendInterfaceRemoval();
                break;
            case 26614:
                DropLog.open(player);
                break;


            case 55005:
//                if (player.isKillsTrackerOpen()) {
//                    player.setKillsTrackerOpen(false);
//                    player.getPacketSender().sendTabInterface(GameSettings.QUESTS_TAB, 639);
//                    PlayerPanel.refreshPanel(player);
//                }
                break;

		/*case 28177:
			if (!TeleportHandler.checkReqs(player, null)) {
				player.getSkillManager().stopSkilling();
				return;
			}
			if (!player.getClickDelay().elapsed(4500) || player.getMovementQueue().isLockMovement()) {
				player.getSkillManager().stopSkilling();
				return;
			}
			if (player.getLocation() == Location.CONSTRUCTION) {
				player.getSkillManager().stopSkilling();
				return;
			}
			Construction.newHouse(player);
			Construction.enterHouse(player, player, true, true);
			player.getSkillManager().stopSkilling();
			break;*/

//            case 35253:
//            case 55253:
//                KillsTracker.open(player);
//                break;
//            case 35254:
//                KillsTracker.openBoss(player);
//                break;

//		case 39203:
//			player.getPacketSender().sendString(1, "https://app.gpay.io/store/paescape");
//			player.getPacketSender().sendMessage("Attempting to open: https://app.gpay.io/store/paescape");
//			break;
            case 39204:
                //player.getPacketSender().sendString(1, "https://app.gpay.io/store/paescape");
                //player.getPacketSender().sendMessage("Attempting to open: https://app.gpay.io/store/paescape");
                break;
            case 39205:
                //player.getPacketSender().sendString(1, "https://app.gpay.io/store/paescape");
                //player.getPacketSender().sendMessage("Attempting to open: https://app.gpay.io/store/paescape");
                break;
            case 39206:
                //player.getPacketSender().sendString(1, "https://app.gpay.io/store/paescape");
                //player.getPacketSender().sendMessage("Attempting to open: https://app.gpay.io/store/paescape");
                break;
            case 39207:
                //player.getPacketSender().sendString(1, "https://app.gpay.io/store/paescape");
                //player.getPacketSender().sendMessage("Attempting to open: https://app.gpay.io/store/paescape");
                break;
            case 39208:
                //player.getPacketSender().sendString(1, "https://app.gpay.io/store/paescape");
                //player.getPacketSender().sendMessage("Attempting to open: https://app.gpay.io/store/paescape");
                break;
            case 350:
                player.getPacketSender()
                        .sendMessage("To autocast a spell, please right-click it and choose the autocast option.")
                        .sendTab(GameSettings.MAGIC_TAB).sendConfig(108, player.getAutocastSpell() == null ? 3 : 1);
                break;
            case 29335:
                if (player.getInterfaceId() > 0) {
                    player.getPacketSender()
                            .sendMessage("Please close the interface you have open before opening another one.");
                    return;
                }
                DialogueManager.start(player, 60);
                player.setDialogueActionId(27);
                break;
            case 29455:
                if (player.getInterfaceId() > 0) {
                    player.getPacketSender()
                            .sendMessage("Please close the interface you have open before opening another one.");
                    return;
                }
                ClanChatManager.toggleLootShare(player);
                break;
            //case 8658:
            //DialogueManager.start(player, 55);
            //player.setDialogueActionId(26);
            //break;
            case 11001:

                if (player.getLocation() == Location.RAIDS_LOBBY)
                    TeleportHandler.teleportPlayer(player, GameSettings.DEFAULT_POSITION.copy(), player.getSpellbook().getTeleportType());
                else
                    player.teleHome(player);
                break;
            //case 8667:
            //TeleportHandler.teleportPlayer(player, new Position(2742, 3443), player.getSpellbook().getTeleportType());
            //break;
            //case 8672:
            //TeleportHandler.teleportPlayer(player, new Position(2911, 4832), player.getSpellbook().getTeleportType());
            //player.getPacketSender().sendMessage(
            //"<img=10> To get started with Runecrafting, walk down the southwest path and");
            //player.getPacketSender().sendMessage(
            //"<img=10> Mine some pure essence then click on the air altar.");
            //break;
            //case 8861:
            //TeleportHandler.teleportPlayer(player, new Position(2914, 3450), player.getSpellbook().getTeleportType());
            //break;
            //case 8656:
            //player.setDialogueActionId(47);
            //DialogueManager.start(player, 86);
            //break;
            //case 8659:
            //player.setDialogueActionId(72);
            //DialogueManager.start(player, 161);
            //TeleportHandler.teleportPlayer(player, new Position(2974, 3370), player.getSpellbook().getTeleportType());
            //break;
            //case 8664:
            //TeleportHandler.teleportPlayer(player, new Position(2662, 3308), player.getSpellbook().getTeleportType());
            //break;
            //case 8666:
            //TeleportHandler.teleportPlayer(player, new Position(3085, 3496), player.getSpellbook().getTeleportType());
            //break;

            /*
             * Teleporting Called Below
             */

            case 60622:
            case 60625:
            case 60628:
            case 60631:
            case 60634:
            case 60637:
            case 60640:
            case 60643:
            case 60646:
            case 60691:
            case 60694:
            case 60697:
                if (player.activeMenu == "slayer")
                    UnnaturalMenu.assign(player, id);
                else if (player.activeMenu == "skiller")
                    ArtisanMenu.assign(player, id);
                else if (player.activeMenu == "seasonal")
                    Seasonal.assign(player, id);
                break;

            case 10003:
                player.getTeleportInterface().open();
                break;
            case 10004:
                if (player.getGameMode() == GameMode.SEASONAL_IRONMAN) {
                    player.activeMenu = "seasonal";
                    Seasonal.openTab(player, 60602);
                }
                break;

            case 60602:
            case 60605:
            case 60608:
            case 60611:
            case 60614:
            case 60617:
                if (player.activeMenu == "slayer")
                    UnnaturalMenu.openTab(player, id);
                else if (player.activeMenu == "skiller")
                    ArtisanMenu.openTab(player, id);
                else if (player.activeMenu == "seasonal")
                    Seasonal.openTab(player, id);
                break;

            /*
             * End Teleporting
             */

		/*case 8671:
			player.setDialogueActionId(56);
			DialogueManager.start(player, 89);
			break;
		case 8670:
			TeleportHandler.teleportPlayer(player, new Position(2717, 3499), player.getSpellbook().getTeleportType());
			break;
		case 8668:
			TeleportHandler.teleportPlayer(player, new Position(2709, 3437), player.getSpellbook().getTeleportType());
			break;
		case 8665:
			TeleportHandler.teleportPlayer(player, new Position(3044, 4973, 1), player.getSpellbook().getTeleportType());
			break;
		case 8662:
			TeleportHandler.teleportPlayer(player, new Position(2345, 3698), player.getSpellbook().getTeleportType());
			break;
		case 13928:
			TeleportHandler.teleportPlayer(player, new Position(3052, 3304), player.getSpellbook().getTeleportType());
			break;
		case 28179:
			TeleportHandler.teleportPlayer(player, new Position(2209, 5348), player.getSpellbook().getTeleportType());
			break;
		case 28178:
			DialogueManager.start(player, 54);
			player.setDialogueActionId(25);
			break;*/
            case 1159: // Bones to Bananas
            case 15877:// Bones to peaches
            case 30306:
                MagicSpells.handleMagicSpells(player, id);
                break;
            case 10001:
                if (player.getInterfaceId() == -1) {
                    Consumables.handleHealAction(player);
                } else {
                    player.getPacketSender().sendMessage("You cannot heal yourself right now.");
                }
                break;
            case 18025:
                if (PrayerHandler.isActivated(player, PrayerHandler.AUGURY)) {
                    PrayerHandler.deactivatePrayer(player, PrayerHandler.AUGURY);
                } else {
                    PrayerHandler.activatePrayer(player, PrayerHandler.AUGURY);
                }
                break;
            case 18018:
                if (PrayerHandler.isActivated(player, PrayerHandler.RIGOUR)) {
                    PrayerHandler.deactivatePrayer(player, PrayerHandler.RIGOUR);
                } else {
                    PrayerHandler.activatePrayer(player, PrayerHandler.RIGOUR);
                }
                break;
            case 3546:
            case 3420:
                if (System.currentTimeMillis() - player.getTrading().lastAction <= 300)
                    return;
                player.getTrading().lastAction = System.currentTimeMillis();
                if (player.getTrading().inTrade()) {
                    player.getTrading().acceptTrade(id == 3546 ? 2 : 1);
                } else {
                    player.getPacketSender().sendInterfaceRemoval();
                }
                break;
            case 10162:
            case 47267:
            case 11729:
                player.getPacketSender().sendInterfaceRemoval();
                break;
            case 841:
                IngridientsBook.readBook(player, player.getCurrentBookPage() + 2, true);
                break;
            case 839:
                IngridientsBook.readBook(player, player.getCurrentBookPage() - 2, true);
                break;
            case 14922:
                player.getPacketSender().sendClientRightClickRemoval().sendInterfaceRemoval();
                break;
            case 14921:
                player.getPacketSender().sendMessage("Please visit the forums and ask for help in the support section.");
                break;
            case 5294:
                player.getPacketSender().sendClientRightClickRemoval().sendInterfaceRemoval();
                player.setDialogueActionId(player.getBankPinAttributes().hasBankPin() ? 8 : 7);
                DialogueManager.start(player,
                        DialogueManager.getDialogues().get(player.getBankPinAttributes().hasBankPin() ? 12 : 9));
                break;
            case 27652:
                if (player.getInterfaceId() > 0) {
                    player.getPacketSender()
                            .sendMessage("Please close the interface you have open before opening another one.");
                    return;
                }
                player.getSkillManager().stopSkilling();
                for(int i = 0; i < player.getOverrides().capacity(); i++) {
                    if (i != 3 && i != 5) {
                        player.getOverrides().set(i, player.getEquipment().forSlot(i));
                    }

                }
                player.getPacketSender().sendMessage("You've saved a new Cosmetic Override.");

                break;
            case 27653:
                player.getSkillManager().stopSkilling();

                if (player.canBank() && player.getStaffRights().getStaffRank() >= StaffRights.UBER_DONATOR.getStaffRank())
                    if (player.getGameMode() == GameMode.ULTIMATE_IRONMAN)
                        player.getUimBank().open();
                    else
                        player.getBank(player.getCurrentBankTab()).open();
                else if (player.getStaffRights().getStaffRank() < StaffRights.UBER_DONATOR.getStaffRank())
                    player.getPacketSender().sendMessage("@red@You must be an Uber donator to use this.");
                else
                    player.getPacketSender().sendMessage("@red@You can't do that here.");

                break;
            case 27655:
                player.getSkillManager().stopSkilling();

                if (player.canBank() && player.getStaffRights().getStaffRank() >= StaffRights.UBER_DONATOR.getStaffRank())
                    player.getMailBox().open();
                else if (player.getStaffRights().getStaffRank() < StaffRights.UBER_DONATOR.getStaffRank())
                    player.getPacketSender().sendMessage("@red@You must be an Uber donator to use this.");
                else
                    player.getPacketSender().sendMessage("@red@You can't do that here.");

                break;
            case 2735:
            case 1511:
                if (player.getSummoning().getBeastOfBurden() != null) {
                    player.getSummoning().toInventory();
                    player.getPacketSender().sendInterfaceRemoval();
                } else {
                    player.getPacketSender().sendMessage("You do not have a familiar who can hold items.");
                }
                break;
            case 54035:
            case 54032:
            case 54038:
            case 54029:
            case 1020:
            case 1021:
            case 1019:
            case 1018:
                if (id == 1020 || id == 54032)
                    SummoningTab.renewFamiliar(player);
                else if (id == 1019 || id == 54035)
                    SummoningTab.callFollower(player);
                else if (id == 1021 || id == 54038)
                    SummoningTab.handleDismiss(player, false);
                else if (id == 54029)
                    player.getSummoning().store();
                else if (id == 1018)
                    player.getSummoning().toInventory();
                break;
            case 11004:
                player.getTeleportInterface().open(TeleportCategory.CITIES);
                break;
            case 11008:
                player.getTeleportInterface().open(TeleportCategory.TRAINING);
                break;
            case 11011:
                player.getTeleportInterface().open(TeleportCategory.DUNGEONS);
                break;
            case 11020:
                player.getTeleportInterface().open(TeleportCategory.RAIDS);
                break;
            case 11014:
                player.getTeleportInterface().open(TeleportCategory.BOSSES);
                break;
            case 11017:
                player.getTeleportInterface().open(TeleportCategory.MINIGAMES);
                break;
            case 2799:
            case 2798:
            case 1747:
            case 1748:
            case 8890:
            case 8886:
            case 8875:
            case 8871:
            case 8894:
                ChatboxInterfaceSkillAction.handleChatboxInterfaceButtons(player, id);
                break;
            case 14873:
            case 14874:
            case 14875:
            case 14876:
            case 14877:
            case 14878:
            case 14879:
            case 14880:
            case 14881:
            case 14882:
                BankPin.clickedButton(player, id);
                break;
            case 27005:
            case 22012:
                if (!player.isBanking() || player.getInterfaceId() != 5292)
                    return;
                Bank.depositItems(player, id == 27005 ? player.getEquipment() : player.getInventory(), false);
                player.getEquipment().refreshItems();
                resetWeapon(player);
                player.getUpdateFlag().flag(Flag.APPEARANCE);
                break;
            case 27023:
                if (!player.isBanking() || player.getInterfaceId() != 5292)
                    return;
                if (player.getSummoning().getBeastOfBurden() == null) {
                    player.getPacketSender().sendMessage("You do not have a familiar which can hold items.");
                    return;
                }
                Bank.depositItems(player, player.getSummoning().getBeastOfBurden(), false);
                break;
            case 22155:
            case 22158:
                if (!player.isBanking() || player.getInterfaceId() != UIMStorage.UIM_BANK_INTERFACE)
                    return;
                player.getUimBank().depositItems(id == 22158 ? player.getEquipment() : player.getInventory(), false);
                player.getEquipment().refreshItems();
                player.getUpdateFlag().flag(Flag.APPEARANCE);
                break;
            case 25161:
                if (!player.isBanking() || player.getInterfaceId() != UIMStorage.UIM_BANK_INTERFACE)
                    return;
                if (player.getSummoning().getBeastOfBurden() == null) {
                    player.getPacketSender().sendMessage("You do not have a familiar which can hold items.");
                    return;
                }
                player.getUimBank().depositItems(player.getSummoning().getBeastOfBurden(), false);
                break;
            case 27026:
                if (!player.isBanking() || player.getInterfaceId() != 5292)
                    return;
                player.setPlaceholders(!player.isPlaceholders());
                player.getPacketSender().sendMessage("Your bank place holders are now: " + (player.isPlaceholders() ? "on." : "off."));
                break;
            case 22008:
                if (!player.isBanking() || player.getInterfaceId() != 5292)
                    return;
                player.setNoteWithdrawal(!player.withdrawAsNote());
                break;
            case 21000:
                if (!player.isBanking() || player.getInterfaceId() != 5292)
                    return;
                player.setSwapMode(!player.swapMode());
                break;
            case 22153:
                if (!player.isBanking() || player.getInterfaceId() != UIMStorage.UIM_BANK_INTERFACE)
                    return;
                player.setNoteWithdrawal(!player.withdrawAsNote());
                break;
            case 25164:
                if (!player.isBanking() || player.getInterfaceId() != UIMStorage.UIM_BANK_INTERFACE)
                    return;
                player.setSwapMode(!player.swapMode());
                break;
            case 27009:
                MoneyPouch.toBank(player);
                break;
            case 27014:
            case 27015:
            case 27016:
            case 27017:
            case 27018:
            case 27019:
            case 27020:
            case 27021:
            case 27022:
                if (!player.isBanking())
                    return;
                if (player.getBankSearchingAttribtues().isSearchingBank())
                    BankSearchAttributes.stopSearch(player, true);
                int bankId = id - 27014;
                boolean empty = bankId > 0 && Bank.isEmpty(player.getBank(bankId));
                if (!empty || bankId == 0) {
                    player.setCurrentBankTab(bankId);
                    player.getPacketSender().sendString(5385, "scrollreset");
                    player.getPacketSender().sendString(27002, Integer.toString(player.getCurrentBankTab()));
                    player.getPacketSender().sendString(27000, "1");
                    player.getBank(bankId).open();
                } else
                    player.getPacketSender().sendMessage("To create a new tab, please drag an item here.");
                break;
            case 22004:
                if (!player.isBanking())
                    return;
                if (!player.getBankSearchingAttribtues().isSearchingBank()) {
                    player.getBankSearchingAttribtues().setSearchingBank(true);
                    player.setInputHandling(new EnterSyntaxToBankSearchFor());
                    player.getPacketSender().sendEnterInputPrompt("What would you like to search for?");
                } else {
                    BankSearchAttributes.stopSearch(player, true);
                }
                break;
            case 32602:
                player.setInputHandling(new PosInput());
                player.getPacketSender().sendEnterInputPrompt("What/Who would you like to search for?");
                break;
            case 22845:
            case 24115:
            case 24010:
            case 24041:
            case 150:
                player.setAutoRetaliate(!player.isAutoRetaliate());
                break;
            case 29332:
                ClanChat clan = player.getCurrentClanChat();
                if (clan == null) {
                    player.getPacketSender().sendMessage("You are not in a clanchat channel.");
                    return;
                }
                ClanChatManager.leave(player, false);
                player.setClanChatName(null);
                break;
            case 29329:
                if (player.getInterfaceId() > 0) {
                    player.getPacketSender()
                            .sendMessage("Please close the interface you have open before opening another one.");
                    return;
                }
                player.setInputHandling(new EnterClanChatToJoin());
                player.getPacketSender().sendEnterInputPrompt("Enter the name of the clanchat channel you wish to join:");
                break;
            case 19158:
            case 152:
                if (player.getRunEnergy() <= 1) {
                    player.getPacketSender().sendMessage("You do not have enough energy to do this.");
                    player.setRunning(false);
                } else
                    player.setRunning(!player.isRunning());
                player.getPacketSender().sendRunStatus();
                break;

            case 65254:
                DropLog.openRare(player);
                break;

            case 27651:
            case 21341:
                if (player.getInterfaceId() == -1) {
                    player.getSkillManager().stopSkilling();
                    BonusManager.update(player, 0, 0);
                    player.getUpdateFlag().flag(Flag.APPEARANCE);
                    player.getPacketSender().sendInterface(21172);
                } else
                    player.getPacketSender().sendMessage("Please close the interface you have open before doing this.");
                break;
            case 27654:
                if (player.getStaffRights() != StaffRights.DEVELOPER)
                    break;
                if (player.canBank()) {
                    Presets.sendPresetInterface(player);
                    player.activeInterface = "presets";
                } else {
                    player.getPacketSender().sendMessage("You can't do that here.");
                }

                break;
            case 27658:
                Presets.save(player);
                break;
            case 2458: // Logout
                if (player.logout()) {
                    World.getPlayers().remove(player);
                }
                break;
            case 29138:
            case 29038:
            case 29063:
            case 29113:
            case 29163:
            case 29188:
            case 29213:
            case 29238:
            case 30007:
            case 48023:
            case 33033:
            case 30108:
            case 7473:
            case 7562:
            case 7487:
            case 7788:
            case 8481:
            case 7612:
            case 7587:
            case 7662:
            case 7462:
            case 7548:
            case 7687:
            case 7537:
            case 12322:
            case 7637:
            case 12311:
            case 41006:
                CombatSpecial.activate(player);
                break;
            case 1772: // shortbow & longbow
                if (player.getWeapon() == WeaponInterface.SHORTBOW) {
                    player.setFightType(FightType.SHORTBOW_ACCURATE);
                } else if (player.getWeapon() == WeaponInterface.LONGBOW) {
                    player.setFightType(FightType.LONGBOW_ACCURATE);
                } else if (player.getWeapon() == WeaponInterface.CROSSBOW) {
                    player.setFightType(FightType.CROSSBOW_ACCURATE);
                }
                break;
            case 1771:
                if (player.getWeapon() == WeaponInterface.SHORTBOW) {
                    player.setFightType(FightType.SHORTBOW_RAPID);
                } else if (player.getWeapon() == WeaponInterface.LONGBOW) {
                    player.setFightType(FightType.LONGBOW_RAPID);
                } else if (player.getWeapon() == WeaponInterface.CROSSBOW) {
                    player.setFightType(FightType.CROSSBOW_RAPID);
                }
                break;
            case 1770:
                if (player.getWeapon() == WeaponInterface.SHORTBOW) {
                    player.setFightType(FightType.SHORTBOW_LONGRANGE);
                } else if (player.getWeapon() == WeaponInterface.LONGBOW) {
                    player.setFightType(FightType.LONGBOW_LONGRANGE);
                } else if (player.getWeapon() == WeaponInterface.CROSSBOW) {
                    player.setFightType(FightType.CROSSBOW_LONGRANGE);
                }
                break;
            case 2282: // dagger & sword
                if (player.getWeapon() == WeaponInterface.DAGGER) {
                    player.setFightType(FightType.DAGGER_STAB);
                } else if (player.getWeapon() == WeaponInterface.SWORD) {
                    player.setFightType(FightType.SWORD_STAB);
                }
                break;
            case 2285:
                if (player.getWeapon() == WeaponInterface.DAGGER) {
                    player.setFightType(FightType.DAGGER_LUNGE);
                } else if (player.getWeapon() == WeaponInterface.SWORD) {
                    player.setFightType(FightType.SWORD_LUNGE);
                }
                break;
            case 2284:
                if (player.getWeapon() == WeaponInterface.DAGGER) {
                    player.setFightType(FightType.DAGGER_SLASH);
                } else if (player.getWeapon() == WeaponInterface.SWORD) {
                    player.setFightType(FightType.SWORD_SLASH);
                }
                break;
            case 2283:
                if (player.getWeapon() == WeaponInterface.DAGGER) {
                    player.setFightType(FightType.DAGGER_BLOCK);
                } else if (player.getWeapon() == WeaponInterface.SWORD) {
                    player.setFightType(FightType.SWORD_BLOCK);
                }
                break;
            case 2429: // scimitar & longsword
                if (player.getWeapon() == WeaponInterface.SCIMITAR) {
                    player.setFightType(FightType.SCIMITAR_CHOP);
                } else if (player.getWeapon() == WeaponInterface.LONGSWORD) {
                    player.setFightType(FightType.LONGSWORD_CHOP);
                }
                break;
            case 2432:
                if (player.getWeapon() == WeaponInterface.SCIMITAR) {
                    player.setFightType(FightType.SCIMITAR_SLASH);
                } else if (player.getWeapon() == WeaponInterface.LONGSWORD) {
                    player.setFightType(FightType.LONGSWORD_SLASH);
                }
                break;
            case 2431:
                if (player.getWeapon() == WeaponInterface.SCIMITAR) {
                    player.setFightType(FightType.SCIMITAR_LUNGE);
                } else if (player.getWeapon() == WeaponInterface.LONGSWORD) {
                    player.setFightType(FightType.LONGSWORD_LUNGE);
                }
                break;
            case 2430:
                if (player.getWeapon() == WeaponInterface.SCIMITAR) {
                    player.setFightType(FightType.SCIMITAR_BLOCK);
                } else if (player.getWeapon() == WeaponInterface.LONGSWORD) {
                    player.setFightType(FightType.LONGSWORD_BLOCK);
                }
                break;
            case 3802: // mace
                player.setFightType(FightType.MACE_POUND);
                break;
            case 3805:
                player.setFightType(FightType.MACE_PUMMEL);
                break;
            case 3804:
                player.setFightType(FightType.MACE_SPIKE);
                break;
            case 3803:
                player.setFightType(FightType.MACE_BLOCK);
                break;
            case 4454: // knife, thrownaxe, dart & javelin
                if (player.getWeapon() == WeaponInterface.KNIFE) {
                    player.setFightType(FightType.KNIFE_ACCURATE);
                } else if (player.getWeapon() == WeaponInterface.THROWNAXE) {
                    player.setFightType(FightType.THROWNAXE_ACCURATE);
                } else if (player.getWeapon() == WeaponInterface.DART) {
                    player.setFightType(FightType.DART_ACCURATE);
                } else if (player.getWeapon() == WeaponInterface.JAVELIN) {
                    player.setFightType(FightType.JAVELIN_ACCURATE);
                }
                break;
            case 4453:
                if (player.getWeapon() == WeaponInterface.KNIFE) {
                    player.setFightType(FightType.KNIFE_RAPID);
                } else if (player.getWeapon() == WeaponInterface.THROWNAXE) {
                    player.setFightType(FightType.THROWNAXE_RAPID);
                } else if (player.getWeapon() == WeaponInterface.DART) {
                    player.setFightType(FightType.DART_RAPID);
                } else if (player.getWeapon() == WeaponInterface.JAVELIN) {
                    player.setFightType(FightType.JAVELIN_RAPID);
                }
                break;
            case 4452:
                if (player.getWeapon() == WeaponInterface.KNIFE) {
                    player.setFightType(FightType.KNIFE_LONGRANGE);
                } else if (player.getWeapon() == WeaponInterface.THROWNAXE) {
                    player.setFightType(FightType.THROWNAXE_LONGRANGE);
                } else if (player.getWeapon() == WeaponInterface.DART) {
                    player.setFightType(FightType.DART_LONGRANGE);
                } else if (player.getWeapon() == WeaponInterface.JAVELIN) {
                    player.setFightType(FightType.JAVELIN_LONGRANGE);
                }
                break;
            case 4685: // spear
                player.setFightType(FightType.SPEAR_LUNGE);
                break;
            case 4688:
                player.setFightType(FightType.SPEAR_SWIPE);
                break;
            case 4687:
                player.setFightType(FightType.SPEAR_POUND);
                break;
            case 4686:
                player.setFightType(FightType.SPEAR_BLOCK);
                break;
            case 4711: // 2h sword
                player.setFightType(FightType.TWOHANDEDSWORD_CHOP);
                break;
            case 4714:
                player.setFightType(FightType.TWOHANDEDSWORD_SLASH);
                break;
            case 4713:
                player.setFightType(FightType.TWOHANDEDSWORD_SMASH);
                break;
            case 4712:
                player.setFightType(FightType.TWOHANDEDSWORD_BLOCK);
                break;
            case 5576: // pickaxe
                player.setFightType(FightType.PICKAXE_SPIKE);
                break;
            case 5579:
                player.setFightType(FightType.PICKAXE_IMPALE);
                break;
            case 5578:
                player.setFightType(FightType.PICKAXE_SMASH);
                break;
            case 5577:
                player.setFightType(FightType.PICKAXE_BLOCK);
                break;
            case 7768: // claws
                player.setFightType(FightType.CLAWS_CHOP);
                break;
            case 7771:
                player.setFightType(FightType.CLAWS_SLASH);
                break;
            case 7770:
                player.setFightType(FightType.CLAWS_LUNGE);
                break;
            case 7769:
                player.setFightType(FightType.CLAWS_BLOCK);
                break;
            case 8466: // halberd
                player.setFightType(FightType.HALBERD_JAB);
                break;
            case 8468:
                player.setFightType(FightType.HALBERD_SWIPE);
                break;
            case 8467:
                player.setFightType(FightType.HALBERD_FEND);
                break;
            case 5862: // unarmed
                player.setFightType(FightType.UNARMED_PUNCH);
                break;
            case 5861:
                player.setFightType(FightType.UNARMED_KICK);
                break;
            case 5860:
                player.setFightType(FightType.UNARMED_BLOCK);
                break;
            case 12298: // whip
                player.setFightType(FightType.WHIP_FLICK);
                break;
            case 12297:
                player.setFightType(FightType.WHIP_LASH);
                break;
            case 12296:
                player.setFightType(FightType.WHIP_DEFLECT);
                break;
            case 336: // staff
                player.setFightType(FightType.STAFF_BASH);
                break;
            case 335:
                player.setFightType(FightType.STAFF_POUND);
                break;
            case 334:
                player.setFightType(FightType.STAFF_FOCUS);
                break;
            case 433: // warhammer
                player.setFightType(FightType.WARHAMMER_POUND);
                break;
            case 432:
                player.setFightType(FightType.WARHAMMER_PUMMEL);
                break;
            case 431:
                player.setFightType(FightType.WARHAMMER_BLOCK);
                break;
            case 782: // scythe
                player.setFightType(FightType.SCYTHE_REAP);
                break;
            case 784:
                player.setFightType(FightType.SCYTHE_CHOP);
                break;
            case 785:
                player.setFightType(FightType.SCYTHE_JAB);
                break;
            case 783:
                player.setFightType(FightType.SCYTHE_BLOCK);
                break;
            case 1704: // battle axe
                player.setFightType(FightType.BATTLEAXE_CHOP);
                break;
            case 1707:
                player.setFightType(FightType.BATTLEAXE_HACK);
                break;
            case 1706:
                player.setFightType(FightType.BATTLEAXE_SMASH);
                break;
            case 1705:
                player.setFightType(FightType.BATTLEAXE_BLOCK);
                break;
        }
    }

    private boolean checkHandlers(Player player, int id) {
        if (Construction.handleButtonClick(id, player)) {
            return true;
        }
        switch (id) {
            case 2494:
            case 2495:
            case 2496:
            case 2497:
            case 2498:
            case 2471:
            case 2472:
            case 2473:
            case 2461:
            case 2462:
            case 2482:
            case 2483:
            case 2484:
            case 2485:
                DialogueOptions.handle(player, id);
                return true;
        }
        if (id >= 32410 && id <= 32460) {
            StaffList.handleButton(player, id);
            return true;
        }
        if (id >= 32623 && id <= 32722) {
            player.getTradingPostManager().handleButton(id);
            return true;
        }
        if (player.isPlayerLocked() && id != 2458 && id != 52756 && id != 52757 && id != 52758 && id != 35769) {
            return true;
        }
        if (player.getTeleportInterface().handleButton(id)) {
            return true;
        }
        if (player.getDropTableInterface().handleButtonClick(id)) {
            return true;
        }
        if (player.getAchievementInterface() != null && player.getAchievementInterface().handleButton(id)) {
            return true;
        }
        if (PrayerHandler.isButton(id)) {
            PrayerHandler.togglePrayerWithActionButton(player, id);
            return true;
        }
        if (CurseHandler.isButton(player, id)) {
            return true;
        }
        if (StartScreen.handleButton(player, id)) {
            return true;
        }
        if (Autocasting.handleAutocast(player, id)) {
            return true;
        }
        if (SmithingData.handleButtons(player, id)) {
            return true;
        }
        if (PouchMaking.pouchInterface(player, id)) {
            return true;
        }
        if (LoyaltyProgramme.handleButton(player, id)) {
            return true;
        }
        if (Fletching.fletchingButton(player, id)) {
            return true;
        }
        if (LeatherMaking.handleButton(player, id) || Tanning.handleButton(player, id)) {
            return true;
        }
        if (Emotes.doEmote(player, id)) {
            return true;
        }
		/*if (player.getLocation() == Location.DUEL_ARENA && Dueling.handleDuelingButtons(player, id)) {
			return true;
		}*/
        if (Slayer.handleRewardsInterface(player, id)) {
            return true;
        }
        if (ExperienceLamps.handleButton(player, id)) {
            return true;
        }
        if (PlayersOnlineInterface.handleButton(player, id)) {
            return true;
        }
        if (GrandExchange.handleButton(player, id)) {
            return true;
        }
        if (ClanChatManager.handleClanChatSetupButton(player, id)) {
            return true;
        }
        if (player.getCollectionLog().handleActionButtons(id)) {
            return true;
        }
        if (Marketplace.handleButtons(player, id)) {
            return true;
        }
        if (DeathsCoffer.handleButtons(player, id)) {
            return true;
        }
        if (player.getBingo().handleButton(id)) {
            return true;
        }

        return false;
    }

    public static final int OPCODE = 185;
}
