package com.arlania.net.packet.impl;

import com.arlania.GameSettings;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.*;
import com.arlania.model.Locations.Location;
import com.arlania.model.container.impl.Bank;
import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketListener;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.*;
import com.arlania.world.content.DropLog.DropLogEntry;
import com.arlania.world.content.Sounds.Sound;
import com.arlania.world.content.achievements.AchievementData;
import com.arlania.world.content.combat.magic.Autocasting;
import com.arlania.world.content.combat.prayer.CurseHandler;
import com.arlania.world.content.combat.prayer.PrayerHandler;
import com.arlania.world.content.combat.range.DwarfMultiCannon;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.content.globalevents.GlobalEventHandler;
import com.arlania.world.content.holiday.Halloween23;
import com.arlania.world.content.interfaces.*;
import com.arlania.world.content.skill.impl.construction.Construction;
import com.arlania.world.content.skill.impl.crafting.Gems;
import com.arlania.world.content.skill.impl.dungeoneering.ItemBinding;
import com.arlania.world.content.skill.impl.herblore.Herblore;
import com.arlania.world.content.skill.impl.herblore.IngridientsBook;
import com.arlania.world.content.skill.impl.hunter.*;
import com.arlania.world.content.skill.impl.hunter.Trap.TrapState;
import com.arlania.world.content.skill.impl.prayer.Prayer;
import com.arlania.world.content.skill.impl.runecrafting.Runecrafting;
import com.arlania.world.content.skill.impl.slayer.SlayerTasks;
import com.arlania.world.content.skill.impl.summoning.CharmingImp;
import com.arlania.world.content.skill.impl.summoning.SummoningData;
import com.arlania.world.content.skill.impl.woodcutting.BirdNests;
import com.arlania.world.content.transportation.JewelryTeleporting;
import com.arlania.world.content.transportation.TeleportHandler;
import com.arlania.world.content.transportation.TeleportType;
import com.arlania.world.entity.impl.player.PerkHandler;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.antibotting.actions.Action;
import com.arlania.world.entity.impl.player.antibotting.actions.ActionItemAction;


public class ItemActionPacketListener implements PacketListener {


    public int quantity = 0;

    public static void cancelCurrentActions(Player player) {
        player.getPacketSender().sendInterfaceRemoval();
        player.setTeleporting(false);
        player.setWalkToTask(null);
        player.setInputHandling(null);
        player.getSkillManager().stopSkilling();
        player.setEntityInteraction(null);
        player.getMovementQueue().setFollowCharacter(null);
        player.getCombatBuilder().cooldown(false);
        player.setResting(false);
    }

    public static boolean checkReqs(Player player, Location targetLocation) {
        if (player.getConstitution() <= 0)
            return false;
        if (player.getTeleblockTimer() > 0) {
            player.getPacketSender().sendMessage("A magical spell is blocking you from teleporting.");
            return false;
        }
        if (player.getLocation() != null && !player.getLocation().canTeleport(player))
            return false;
        if (player.isPlayerLocked() || player.isCrossingObstacle()) {
            player.getPacketSender().sendMessage("You cannot teleport right now.");
            return false;
        }
        return true;
    }

    public static void firstAction(final Player player, Packet packet) {
        int interfaceId = packet.readInt();
        int slot = packet.readShort();
        int itemId = packet.readInt();

        String itemName = "";
        String itemMessage = "";
        String message = "";
        String eventLog = "";

        Location targetLocation = player.getLocation();

        if (interfaceId == 38274) {
            Construction.handleItemClick(itemId, player);
            return;
        }

        if (slot < 0 || slot > player.getInventory().capacity())
            return;
        if (player.getInventory().getItems()[slot].getId() != itemId)
            return;
        player.getActionTracker().offer(new ActionItemAction(Action.ActionType.FIRST_ITEM_ACTION, interfaceId, slot, itemId));
        player.setInteractingItem(player.getInventory().getItems()[slot]);
        if (Prayer.isBone(itemId)) {
            Prayer.buryBone(player, itemId);
            return;
        }
        if (Consumables.isFood(player, itemId, slot))
            return;
        if (Consumables.isPotion(itemId)) {
            Consumables.handlePotion(player, itemId, slot);
            return;
        }
        if (BirdNests.isNest(itemId)) {
            BirdNests.searchNest(player, itemId);
            return;
        }
        if (Herblore.cleanHerb(player, itemId))
            return;
        if (DonationBonds.handleBond(player, itemId))
            return;
        //if(ClueScroll.handleCasket(player, itemId) || SearchScrolls.loadClueInterface(player, itemId) || MapScrolls.loadClueInterface(player, itemId) || Puzzle.loadClueInterface(player, itemId) || CoordinateScrolls.loadClueInterface(player, itemId) || DiggingScrolls.loadClueInterface(player, itemId))
        //return;
        if (Effigies.isEffigy(itemId)) {
            Effigies.handleEffigy(player, itemId);
            return;
        }
        if (ExperienceLamps.handleLamp(player, itemId)) {
            return;
        }


        switch (itemId) {
            case 2795:
                player.getBingo().open();
//                Bingo.open(player);
                break;

            case 211941:
                if (player.getGameMode() != GameMode.ULTIMATE_IRONMAN)
                    return;
                if (player.getLocation() == Location.HOME_BANK || player.getLocation() == Location.RAIDS_LOBBY)
                    player.getUimBank().open();
                else if (player.getPosition().getX() >= 2654 && player.getPosition().getX() <= 2659 &&
                        player.getPosition().getY() >= 2590 && player.getPosition().getY() <= 2595)
                    player.getUimBank().open();
                else
                    player.getPacketSender().sendMessage("You can use this at the Home Bank, the DZ platform, or at Raids Lobbies.");
                break;

            case 20008:
                player.flashbackTime += 1500;
                player.getPacketSender().sendMessage("You've added 15 minutes to your Flashback timer");
                player.getInventory().delete(20008, 1);
                break;


            case 20027:
                player.aggressorTime = 3000;
                player.getPacketSender().sendMessage("You've set your Aggressor timer to 30 minutes.");
                player.getInventory().delete(20027, 1);
                break;

            case 18829:
                if (player.getRaidsParty() != null) {
                    if (player == player.getRaidsParty().getOwner())
                        player.getPacketSender().sendMessage("You are the Party Leader.");
                    else
                        player.moveTo(player.getRaidsParty().getOwner().getPosition());
                }
                break;

            case 213195: //Oddskull
                if (player.getLocation() == Location.WILDERNESS) {

                    if (player.getCombatBuilder().isBeingAttacked() && player.getStaffRights() != StaffRights.DEVELOPER) {
                        player.getPacketSender().sendMessage("You must wait a few seconds after being out of combat before doing this.");
                        return;
                    }

                    player.setDialogueActionId(1019);
                    DialogueManager.start(player, 1019);
                } else
                    player.getPacketSender().sendMessage("You must be in the Wilderness to activate this.");
                break;

            case 221760: //Kharedst's Memoirs
                player.setDialogueActionId(230);
                DialogueManager.start(player, 230);
                break;

            //Subscriptions
            case 911:
            case 912:
            case 913:
            case 914:
            case 915:
                Subscriptions.handleSubscription(player, itemId);
                break;

            //Subscription Boxes
            case 906:
            case 907:
            case 908:
            case 909:
            case 910:
                Subscriptions.handleSubscriptionBox(player, itemId);
                break;

            //Turmoil Scrolls
            case 2707:
                if (!player.turmoilRanged) {
                    player.getPacketSender().sendMessage("You have unlocked the Ranged abilities of Turmoil!");
                    player.turmoilRanged = true;
                    player.getInventory().delete(2707, 1);
                    break;
                } else
                    player.getPacketSender().sendMessage("You already have the Ranged abilities of Turmoil unlocked.");
                break;
            case 2708:
                if (!player.turmoilMagic) {
                    player.getPacketSender().sendMessage("You have unlocked the Magic abilities of Turmoil!");
                    player.turmoilMagic = true;
                    player.getInventory().delete(2708, 1);
                    break;
                } else
                    player.getPacketSender().sendMessage("You already have the Magic abilities of Turmoil unlocked.");
                break;

            //relics
            case 600:
                player.setPrayerbook(Prayerbook.NORMAL);
                player.getPacketSender().sendTabInterface(GameSettings.PRAYER_TAB, player.getPrayerbook().getInterfaceId());
                PrayerHandler.deactivateAll(player);
                CurseHandler.deactivateAll(player);
                break;
            case 730:
                player.setSpellbook(MagicSpellbook.NORMAL);
                player.getPacketSender().sendTabInterface(GameSettings.MAGIC_TAB, player.getSpellbook().getInterfaceId()).sendMessage("Your magic spellbook is changed..");
                Autocasting.resetAutocast(player, true);
                player.setAutocastSpell(null);
                break;

            case 219833:
                ClueScrolls.addClueRewards(player, itemId);
                break;
            case 212021:
                ClueScrolls.addClueRewards(player, itemId);
                break;
            case 212542:
                ClueScrolls.addClueRewards(player, itemId);
                break;
            case 219782:
                ClueScrolls.addClueRewards(player, itemId);
                break;
            case 219835:
                ClueScrolls.addClueRewards(player, itemId);
                break;
            case 1856:
                GuideBook.showInterface(player);
                break;
            case 207144:
                Halloween23.readBook(player);
                break;
            case 2957:
                player.getInventory().delete(2957, 1);
                player.getInventory().add(4562, 25);
                break;
            case 5:
                CasinoRules.showInterface(player);
                break;
            case 619:
                Lottery.scratchOff(player);
                break;
            case 1917:
                player.forceChat("Mmmmmm Beer!");
                player.performAnimation(new Animation(829));
                player.setOverloadPotionTimer(300);
                if (player.getOverloadPotionTimer() > 0) {  // Prevents decreasing stats
                    Consumables.overloadIncrease(player, Skill.ATTACK, 0.27);
                    Consumables.overloadIncrease(player, Skill.STRENGTH, 0.27);
                    Consumables.overloadIncrease(player, Skill.DEFENCE, 0.27);
                    Consumables.overloadIncrease(player, Skill.RANGED, 0.235);
                    player.getSkillManager().setCurrentLevel(Skill.MAGIC, player.getSkillManager().getMaxLevel(Skill.MAGIC) + 7);
                }
                Sounds.sendSound(player, Sound.DRINK_POTION);
                player.getInventory().delete(1917, 1);
                player.getInventory().add(1919, 1);
                break;
            case 18339:
                if (player.personalFilterDirtBag) {
                    player.getPacketSender().sendMessage("You fill your Dirt bag!");
                }
                int paydirt = player.getInventory().getAmount(212011);
                for (int i = 0; i < paydirt; i++) {

                    if (player.getCoalBag() >= 250) {
                        player.getPacketSender().sendMessage("Your dirt bag is full.");
                        break;
                    } else if (player.getCoalBag() < 250) {
                        player.getInventory().delete(212011, 1);
                        player.setCoalBag(player.getCoalBag() + 1);
                    }
                }

                break;
            case 6749:
                if (player.mysteryPass && player.getInventory().contains(6749)) {
                    MysteryPass.showMysteryPass(player);
                    player.activeInterface = "mysterypass";
                } else if (!player.mysteryPass && player.getInventory().contains(6749)) {
                    player.mysteryPass = true;
                    MysteryPass.showMysteryPass(player);
                    player.activeInterface = "mysterypass";
                }
                break;
            case 6758:
                if (player.battlePass && player.getInventory().contains(6758)) {
                    BattlePass.showBattlePass(player);
                    player.activeInterface = "battlepass";
                } else if (!player.battlePass && player.getInventory().contains(6758)) {
                    player.battlePass = true;
                    BattlePass.showBattlePass(player);
                    player.activeInterface = "battlepass";
                }
                break;
            case 6769:
                if (player.eventPass && player.getInventory().contains(6769)) {
                    EventPass.showEventPass(player);
                    player.activeInterface = "eventpass";
                } else if (!player.eventPass && player.getInventory().contains(6769)) {
                    player.eventPass = true;
                    EventPass.showEventPass(player);
                    player.activeInterface = "eventpass";
                }
                break;
            case 18338:
                player.getPacketSender().sendMessage("You put all of your gems in gem bag.");
                int invSapphire = player.getInventory().getAmount(1623);
                int invEmerald = player.getInventory().getAmount(1621);
                int invRuby = player.getInventory().getAmount(1619);
                int invDiamond = player.getInventory().getAmount(1617);
                for (int i = 0; i < invSapphire; i++) {
                    player.getInventory().delete(1623, 1);

                    if (player.getBagSapphire() > 10000)
                        break;

                    player.setBagSapphire(player.getBagSapphire() + 1);
                }
                for (int i = 0; i < invEmerald; i++) {
                    player.getInventory().delete(1621, 1);

                    if (player.getBagEmerald() > 10000)
                        break;

                    player.setBagEmerald(player.getBagEmerald() + 1);
                }
                for (int i = 0; i < invRuby; i++) {
                    player.getInventory().delete(1619, 1);

                    if (player.getBagRuby() > 10000)
                        break;

                    player.setBagRuby(player.getBagRuby() + 1);
                }
                for (int i = 0; i < invDiamond; i++) {
                    player.getInventory().delete(1617, 1);

                    if (player.getBagDiamond() > 10000)
                        break;

                    player.setBagDiamond(player.getBagDiamond() + 1);
                }
                player.getPacketSender().sendMessage("You have " + player.getBagSapphire() + " Sapphires, " + player.getBagEmerald() + " Emeralds, "
                        + player.getBagRuby() + " Rubies, " + player.getBagDiamond() + " Diamonds, ");
                break;
            case 15246:
                player.setDialogueActionId(165);
                DialogueManager.start(player, 165);
                break;
            case 13663:
                if (player.getInterfaceId() > 0) {
                    player.getPacketSender().sendMessage("Please close the interface you have open before doing this.");
                    return;
                }
                player.setUsableObject(new Object[2]).setUsableObject(0, "reset");
                player.getPacketSender().sendString(38006, "Choose stat to reset!").sendMessage("@red@Please select a skill you wish to reset and then click on the 'Confim' button.").sendString(38090, "Which skill would you like to reset?");
                player.getPacketSender().sendInterface(38000);
                break;
            case 19670:
                player.setVotePoints(player.getVotePoints() + 1);
                player.setPaePoints(player.getPaePoints() + 10);
                player.getInventory().delete(19670, 1);
                player.getInventory().add(995, 250000);
                PlayerPanel.refreshPanel(player);
                player.sendMessage("You've received 1 Vote Point, 10 PaePoints and 250,000 coins!");
                break;
            case 8007: //Varrock
                if (!player.getClickDelay().elapsed(1200) || player.getMovementQueue().isLockMovement()) {
                    return;
                }
                if (player.getLocation() == Location.CONSTRUCTION) {
                    player.getPacketSender().sendMessage("Please use the portal to exit your house");
                    return;
                }


                if (!checkReqs(player, targetLocation)) {
                    return;
                }
                player.setTeleporting(true).getMovementQueue().setLockMovement(true).reset();
                cancelCurrentActions(player);
                player.performAnimation(new Animation(4731));
                player.performGraphic(new Graphic(678));
                player.getInventory().delete(8007, 1);
                player.getClickDelay().reset();

                TaskManager.submit(new Task(2, player, false) {
                    @Override
                    public void execute() {
                        Position position = new Position(3210, 3424, 0);
                        player.moveTo(position);
                        player.getMovementQueue().setLockMovement(false).reset();
                        this.stop();
                    }
                });


                break;

            case 7020:
            case 7021:
            case 7022:
            case 7023:
            case 7024:
            case 7025:
            case 7026:
            case 7027:
            case 7028:
            case 7029:
            case 7030:
            case 7031:
            case 7032:
            case 7033:
            case 7034:
            case 7035:
            case 7040:
            case 7041:
            case 7042:
            case 7043:
            case 7044:
            case 7045:
            case 7046:
            case 7047:
            case 7048:
            case 7049:
                GlobalEventHandler.useEventToken(player, itemId);
                break;

            case 4020:
                player.getInventory().delete(4020, 1);
                player.getPacketSender().sendMessage("You've added 150 minutes to your Accuracy Event timer!");
                player.accuracyEventTimer += 15000;
                eventLog = player.getUsername() + " activated Player Accuracy Event";
                PlayerLogs.log("Event Log", eventLog);
                break;
            case 4021:
                player.getInventory().delete(4021, 1);
                player.getPacketSender().sendMessage("You've added 150 minutes to your Accelerate Event timer!");
                player.accelerateEventTimer += 15000;
                eventLog = player.getUsername() + " activated Player Accelerate Event";
                PlayerLogs.log("Event Log", eventLog);
                break;
            case 4022:
                player.getInventory().delete(4022, 1);
                player.getPacketSender().sendMessage("You've added 150 minutes to your Drop Rate Event timer!");
                player.droprateEventTimer += 15000;
                eventLog = player.getUsername() + " activated Player Drop Rate Event";
                PlayerLogs.log("Event Log", eventLog);
                break;
            case 4023:
                player.getInventory().delete(4023, 1);
                player.getPacketSender().sendMessage("You've added 150 minutes to your 2x Boss PP Event timer!");
                player.doubleBossPointEventTimer += 15000;
                eventLog = player.getUsername() + " activated Player 2x Boss PP Event";
                PlayerLogs.log("Event Log", eventLog);
                break;
            case 4024:
                player.getInventory().delete(4024, 1);
                player.getPacketSender().sendMessage("You've added 150 minutes to your 2x Skiller PP Event timer!");
                player.doubleSkillerPointsEventTimer += 15000;
                eventLog = player.getUsername() + " activated Player 2x Skiller PP Event";
                PlayerLogs.log("Event Log", eventLog);
                break;
            case 4025:
                player.getInventory().delete(4025, 1);
                player.getPacketSender().sendMessage("You've added 150 minutes to your 2x Slayer PP Event timer!");
                player.doubleSlayerPointsEventTimer += 15000;
                eventLog = player.getUsername() + " activated Player 2x Slayer PP Event";
                PlayerLogs.log("Event Log", eventLog);
                break;
            case 4027:
                player.getInventory().delete(4027, 1);
                player.getPacketSender().sendMessage("You've added 150 minutes to your Loaded Event timer!");
                player.loadedEventTimer += 15000;
                eventLog = player.getUsername() + " activated Player Loaded Event";
                PlayerLogs.log("Event Log", eventLog);
                break;
            case 4028:
                player.getInventory().delete(4028, 1);
                player.getPacketSender().sendMessage("You've added 150 minutes to your 2x Loot Event timer!");
                player.doubleLootTimer += 15000;
                eventLog = player.getUsername() + " activated Player 2x Loot Event Event";
                PlayerLogs.log("Event Log", eventLog);
                break;
            case 4029:
                player.getInventory().delete(4029, 1);
                player.getPacketSender().sendMessage("You've added 150 minutes to your 2x XP Event timer!");
                player.doubleExpEventTimer += 15000;
                eventLog = player.getUsername() + " activated Player 2x XP Event";
                PlayerLogs.log("Event Log", eventLog);
                break;
            case 4030:
                player.getInventory().delete(4030, 1);
                player.getPacketSender().sendMessage("You've added 150 minutes to your Event Box Bonanza timer!");
                player.universalDropEventTimer += 15000;
                eventLog = player.getUsername() + " activated Player Event Box Bonanza Event";
                PlayerLogs.log("Event Log", eventLog);
                break;
            case 4031:
                player.getInventory().delete(4031, 1);
                player.getPacketSender().sendMessage("You've added 150 minutes to your Boss Kills Event timer!");
                player.bossKillsEventTimer += 15000;
                eventLog = player.getUsername() + " activated Player Boss Kills Event";
                PlayerLogs.log("Event Log", eventLog);
                break;
            case 4032:
                player.getInventory().delete(4032, 1);
                player.getPacketSender().sendMessage("You've added 150 minutes to your Max Hit Event timer!");
                player.maxHitEventTimer += 15000;
                eventLog = player.getUsername() + " activated Player Max Hit Event";
                PlayerLogs.log("Event Log", eventLog);
                break;
            case 2709:
                player.getInventory().delete(2709, 1);
                player.getInventory().add(GameSettings.soloPersonalEvents[RandomUtility.exclusiveRandom(GameSettings.soloPersonalEvents.length)], 1);
                break;

            case 8015: //Bones to Peaches
                player.performGraphic(new Graphic(8));
                player.getInventory().delete(8015, 1);
                player.getClickDelay().reset();

                while (player.getInventory().contains(526)) //bones
                {
                    player.getInventory().delete(526, 1);
                    player.getInventory().add(6883, 1);
                }

                while (player.getInventory().contains(532)) //big bones
                {
                    player.getInventory().delete(532, 1);
                    player.getInventory().add(6883, 1);
                }


                break;


            case 8008: //Lumbridge
                if (!player.getClickDelay().elapsed(1200) || player.getMovementQueue().isLockMovement()) {
                    return;
                }
                if (player.getLocation() == Location.CONSTRUCTION) {
                    player.getPacketSender().sendMessage("Please use the portal to exit your house");
                    return;
                }


                if (!checkReqs(player, targetLocation)) {
                    return;
                }
                player.setTeleporting(true).getMovementQueue().setLockMovement(true).reset();
                cancelCurrentActions(player);
                player.performAnimation(new Animation(4731));
                player.performGraphic(new Graphic(678));
                player.getInventory().delete(8008, 1);
                player.getClickDelay().reset();

                TaskManager.submit(new Task(2, player, false) {
                    @Override
                    public void execute() {
                        Position position = new Position(3222, 3218, 0);
                        player.moveTo(position);
                        player.getMovementQueue().setLockMovement(false).reset();
                        this.stop();
                    }
                });


                break;
            case 8009: //Falador
                if (!player.getClickDelay().elapsed(1200) || player.getMovementQueue().isLockMovement()) {
                    return;
                }
                if (player.getLocation() == Location.CONSTRUCTION) {
                    player.getPacketSender().sendMessage("Please use the portal to exit your house");
                    return;
                }


                if (!checkReqs(player, targetLocation)) {
                    return;
                }
                player.setTeleporting(true).getMovementQueue().setLockMovement(true).reset();
                cancelCurrentActions(player);
                player.performAnimation(new Animation(4731));
                player.performGraphic(new Graphic(678));
                player.getInventory().delete(8009, 1);
                player.getClickDelay().reset();

                TaskManager.submit(new Task(2, player, false) {
                    @Override
                    public void execute() {
                        Position position = new Position(2964, 3378, 0);
                        player.moveTo(position);
                        player.getMovementQueue().setLockMovement(false).reset();
                        this.stop();
                    }
                });


                break;
            case 8010: //Camelot
                if (!player.getClickDelay().elapsed(1200) || player.getMovementQueue().isLockMovement()) {
                    return;
                }
                if (player.getLocation() == Location.CONSTRUCTION) {
                    player.getPacketSender().sendMessage("Please use the portal to exit your house");
                    return;
                }


                if (!checkReqs(player, targetLocation)) {
                    return;
                }
                player.setTeleporting(true).getMovementQueue().setLockMovement(true).reset();
                cancelCurrentActions(player);
                player.performAnimation(new Animation(4731));
                player.performGraphic(new Graphic(678));
                player.getInventory().delete(8010, 1);
                player.getClickDelay().reset();

                TaskManager.submit(new Task(2, player, false) {
                    @Override
                    public void execute() {
                        Position position = new Position(2757, 3477, 0);
                        player.moveTo(position);
                        player.getMovementQueue().setLockMovement(false).reset();
                        this.stop();
                    }
                });


                break;
            case 8011: //Ardy
                if (!player.getClickDelay().elapsed(1200) || player.getMovementQueue().isLockMovement()) {
                    return;
                }
                if (player.getLocation() == Location.CONSTRUCTION) {
                    player.getPacketSender().sendMessage("Please use the portal to exit your house");
                    return;
                }


                if (!checkReqs(player, targetLocation)) {
                    return;
                }
                player.setTeleporting(true).getMovementQueue().setLockMovement(true).reset();
                cancelCurrentActions(player);
                player.performAnimation(new Animation(4731));
                player.performGraphic(new Graphic(678));
                player.getInventory().delete(8011, 1);
                player.getClickDelay().reset();

                TaskManager.submit(new Task(2, player, false) {
                    @Override
                    public void execute() {
                        Position position = new Position(2662, 3305, 0);
                        player.moveTo(position);
                        player.getMovementQueue().setLockMovement(false).reset();
                        this.stop();
                    }
                });


                break;
            case 8012: //Watchtower Tele
                if (!player.getClickDelay().elapsed(1200) || player.getMovementQueue().isLockMovement()) {
                    return;
                }
                if (player.getLocation() == Location.CONSTRUCTION) {
                    player.getPacketSender().sendMessage("Please use the portal to exit your house");
                    return;
                }


                if (!checkReqs(player, targetLocation)) {
                    return;
                }
                player.setTeleporting(true).getMovementQueue().setLockMovement(true).reset();
                cancelCurrentActions(player);
                player.performAnimation(new Animation(4731));
                player.performGraphic(new Graphic(678));
                player.getInventory().delete(8012, 1);
                player.getClickDelay().reset();

                TaskManager.submit(new Task(2, player, false) {
                    @Override
                    public void execute() {
                        Position position = new Position(2728, 3349, 0);
                        player.moveTo(position);
                        player.getMovementQueue().setLockMovement(false).reset();
                        this.stop();
                    }
                });


                break;
            case 8013: //Home Tele
                TeleportHandler.teleportPlayer(player, new Position(3087, 3491), TeleportType.NORMAL);
                player.getInventory().delete(8013, 1);
                break;
            case 13598: //Runecrafting Tele
                TeleportHandler.teleportPlayer(player, new Position(2595, 4772), TeleportType.NORMAL);
                player.getInventory().delete(13598, 1);
                break;
            case 13599: //Air Altar Tele
                TeleportHandler.teleportPlayer(player, new Position(2845, 4832), TeleportType.NORMAL);
                player.getInventory().delete(13599, 1);
                break;
            case 13600: //Mind Altar Tele
                TeleportHandler.teleportPlayer(player, new Position(2792, 4824), TeleportType.NORMAL);
                player.getInventory().delete(13600, 1);
                break;
            case 13601: //Water Altar Tele
                TeleportHandler.teleportPlayer(player, new Position(2713, 4836), TeleportType.NORMAL);
                player.getInventory().delete(13601, 1);
                break;
            case 13602: //Earth Altar Tele
                TeleportHandler.teleportPlayer(player, new Position(2660, 4839), TeleportType.NORMAL);
                player.getInventory().delete(13602, 1);
                break;
            case 13603: //Fire Altar Tele
                TeleportHandler.teleportPlayer(player, new Position(2584, 4836), TeleportType.NORMAL);
                player.getInventory().delete(13603, 1);
                break;
            case 13604: //Body Altar Tele
                TeleportHandler.teleportPlayer(player, new Position(2527, 4833), TeleportType.NORMAL);
                player.getInventory().delete(13604, 1);
                break;
            case 13605: //Cosmic Altar Tele
                TeleportHandler.teleportPlayer(player, new Position(2162, 4833), TeleportType.NORMAL);
                player.getInventory().delete(13605, 1);
                break;
            case 13606: //Chaos Altar Tele
                TeleportHandler.teleportPlayer(player, new Position(2269, 4843), TeleportType.NORMAL);
                player.getInventory().delete(13606, 1);
                break;
            case 13607: //Nature Altar Tele
                TeleportHandler.teleportPlayer(player, new Position(2398, 4841), TeleportType.NORMAL);
                player.getInventory().delete(13607, 1);
                break;
            case 13608: //Law Altar Tele
                TeleportHandler.teleportPlayer(player, new Position(2464, 4834), TeleportType.NORMAL);
                player.getInventory().delete(13608, 1);
                break;
            case 13609: //Death Altar Tele
                TeleportHandler.teleportPlayer(player, new Position(2207, 4836), TeleportType.NORMAL);
                player.getInventory().delete(13609, 1);
                break;
            case 13610: //Blood Altar Tele
                TeleportHandler.teleportPlayer(player, new Position(1719, 3827, 0), TeleportType.NORMAL);
                player.getInventory().delete(13610, 1);
                break;
            case 18809: //Rimmington Tele
                TeleportHandler.teleportPlayer(player, new Position(2957, 3214), TeleportType.NORMAL);
                player.getInventory().delete(18809, 1);
                break;
            case 18810: //Taverly Tele
                TeleportHandler.teleportPlayer(player, new Position(2894, 3444), TeleportType.NORMAL);
                player.getInventory().delete(18810, 1);
                break;
            case 18811: //Pollnivneach Tele
                TeleportHandler.teleportPlayer(player, new Position(3359, 2910), TeleportType.NORMAL);
                player.getInventory().delete(18811, 1);
                break;
            case 18812: //Godwars Tele
                TeleportHandler.teleportPlayer(player, new Position(2871, 5319, 2), TeleportType.NORMAL);
                player.getInventory().delete(18812, 1);
                break;
            case 18813: //Barrows Tele
                TeleportHandler.teleportPlayer(player, new Position(3564, 3306), TeleportType.NORMAL);
                player.getInventory().delete(18813, 1);
                break;
            case 18814: //DKS Tele
                TeleportHandler.teleportPlayer(player, new Position(1912, 4367), TeleportType.NORMAL);
                player.getInventory().delete(18814, 1);
                break;
            case 2686: //Boss Teleports
                if (player.getBossTeleports() == 0) {
                    player.setBossTeleports(1);
                    player.getPacketSender().sendMessage("You have just unlocked Boss Teleports!");
                    player.getInventory().delete(2686, 1);
                } else {
                    player.getPacketSender().sendMessage("You have already unlocked Boss Teleports!");
                }

                break;
            case 2687: //Wilderness Teleports
                if (player.getWildernessTeleports() == 0) {
                    player.setWildernessTeleports(1);
                    player.getPacketSender().sendMessage("You have just unlocked Wilderness Teleports!");
                    player.getInventory().delete(2687, 1);
                } else {
                    player.getPacketSender().sendMessage("You have already unlocked Wilderness Teleports!");
                }

                break;
            case 2688: //Minigame Teleports
                if (player.getMinigameTeleports() == 0) {
                    player.setMinigameTeleports(1);
                    player.getPacketSender().sendMessage("You have just unlocked Minigame Teleports!");
                    player.getInventory().delete(2688, 1);
                } else {
                    player.getPacketSender().sendMessage("You have already unlocked Minigame Teleports!");
                }

                break;
            case 611: //Last recall
                if ((player.getWildernessLevel() < 1) &&
                        (player.getLocation() != Location.RECIPE_FOR_DISASTER) &&
                        (player.getLocation() != Location.TEKTON) &&
                        (player.getLocation() != Location.SKELETAL_MYSTICS) &&
                        (player.getLocation() != Location.OLM) &&
                        (player.getLocation() != Location.PESTILENT_BLOAT) &&
                        (player.getLocation() != Location.MAIDEN_SUGADINTI) &&
                        (player.getLocation() != Location.VERZIK_VITUR) &&
                        (player.getLocation() != Location.GRAVEYARD) &&
                        (player.getLocation() != Location.MAZE_RANDOM)) {
                    if (player.getRegionInstance() == null) {
                        player.setLastRecallx(player.getPosition().getX());
                        player.setLastRecally(player.getPosition().getY());
                        player.setLastRecallz(player.getPosition().getZ());
                        player.getInventory().delete(611, 1);
                        player.getInventory().add(612, 1);
                    } else
                        player.getPacketSender().sendMessage("@red@You can't activate Last Recall here!");
                } else
                    player.getPacketSender().sendMessage("@red@You can't activate Last Recall here!");
                break;
            case 612: //Last recall with teleport
                TeleportHandler.teleportPlayer(player, new Position(player.getLastRecallx(), player.getLastRecally(), player.getLastRecallz()), TeleportType.TELE_TAB);
                player.performAnimation(new Animation(4731));
                player.performGraphic(new Graphic(678));
                player.getInventory().delete(612, 1);
                player.getInventory().add(611, 1);
                player.setLastRecallx(0);
                player.setLastRecally(0);
                player.setLastRecallz(0);
                break;

            case 220358: //clue geode (easy)
                player.getInventory().delete(220358, 1);
                player.getInventory().add(GameSettings.easyClue, 1);
                break;

            case 220360: //clue geode (medium)
                player.getInventory().delete(220360, 1);
                player.getInventory().add(GameSettings.mediumClue, 1);
                break;

            case 220362: //clue geode (hard)
                player.getInventory().delete(220362, 1);
                player.getInventory().add(GameSettings.hardClue, 1);
                break;

            case 220364: //clue geode (elite)
                player.getInventory().delete(220364, 1);
                player.getInventory().add(GameSettings.eliteClue, 1);
                break;

            case 219712: //clue nest (easy)
                player.getInventory().delete(219712, 1);
                player.getInventory().add(GameSettings.easyClue, 1);
                player.getInventory().add(6693, 1);
                break;

            case 219714: //clue nest (medium)
                player.getInventory().delete(219714, 1);
                player.getInventory().add(GameSettings.mediumClue, 1);
                player.getInventory().add(6693, 1);
                break;

            case 219716: //clue nest (hard)
                player.getInventory().delete(219716, 1);
                player.getInventory().add(GameSettings.hardClue, 1);
                player.getInventory().add(6693, 1);
                break;

            case 219718: //clue nest (elite)
                player.getInventory().delete(219718, 1);
                player.getInventory().add(GameSettings.eliteClue, 1);
                player.getInventory().add(6693, 1);
                break;

            case 213648: //clue bottle (easy)
                player.getInventory().delete(213648, 1);
                player.getInventory().add(GameSettings.easyClue, 1);
                break;

            case 213649: //clue bottle (medium)
                player.getInventory().delete(213649, 1);
                player.getInventory().add(GameSettings.mediumClue, 1);
                break;

            case 213650: //clue bottle (hard)
                player.getInventory().delete(213650, 1);
                player.getInventory().add(GameSettings.hardClue, 1);
                break;

            case 213651: //clue bottle (elite)
                player.getInventory().delete(213651, 1);
                player.getInventory().add(GameSettings.eliteClue, 1);
                break;


            case 20560:
                int boost = player.getSkillManager().getMaxLevel(Skill.MAGIC) / 10;
                player.performGraphic(new Graphic(377));
                player.getSkillManager().setCurrentLevel(Skill.MAGIC, player.getSkillManager().getMaxLevel(Skill.MAGIC) + boost);
                break;

            case 7198:
                player.getSkillManager().setCurrentLevel(Skill.FISHING, player.getSkillManager().getMaxLevel(Skill.FISHING) + 4);
                player.getSkillManager().setCurrentLevel(Skill.COOKING, player.getSkillManager().getMaxLevel(Skill.COOKING) + 4);
                if (player.getConstitution() + 120 > player.getSkillManager().getMaxLevel(Skill.CONSTITUTION)) {
                    player.setConstitution(player.getSkillManager().getMaxLevel(Skill.CONSTITUTION));
                } else {
                    player.setConstitution(player.getConstitution() + 120);
                }
                player.getInventory().delete(7198, 1);
                player.getInventory().add(7200, 1);
                break;
            case 7200:
                player.getSkillManager().setCurrentLevel(Skill.FISHING, player.getSkillManager().getMaxLevel(Skill.FISHING) + 4);
                player.getSkillManager().setCurrentLevel(Skill.COOKING, player.getSkillManager().getMaxLevel(Skill.COOKING) + 4);
                if (player.getConstitution() + 120 > player.getSkillManager().getMaxLevel(Skill.CONSTITUTION)) {
                    player.setConstitution(player.getSkillManager().getMaxLevel(Skill.CONSTITUTION));
                } else {
                    player.setConstitution(player.getConstitution() + 120);
                }
                player.getInventory().delete(7200, 1);
                break;
            case 7208:
                player.getSkillManager().setCurrentLevel(Skill.HUNTER, player.getSkillManager().getMaxLevel(Skill.HUNTER) + 4);
                player.getSkillManager().setCurrentLevel(Skill.SLAYER, player.getSkillManager().getMaxLevel(Skill.SLAYER) + 4);
                if (player.getConstitution() + 120 > player.getSkillManager().getMaxLevel(Skill.CONSTITUTION)) {
                    player.setConstitution(player.getSkillManager().getMaxLevel(Skill.CONSTITUTION));
                } else {
                    player.setConstitution(player.getConstitution() + 120);
                }
                player.getInventory().delete(7208, 1);
                player.getInventory().add(7210, 1);
                break;
            case 7210:
                player.getSkillManager().setCurrentLevel(Skill.HUNTER, player.getSkillManager().getMaxLevel(Skill.HUNTER) + 4);
                player.getSkillManager().setCurrentLevel(Skill.SLAYER, player.getSkillManager().getMaxLevel(Skill.SLAYER) + 4);
                if (player.getConstitution() + 120 > player.getSkillManager().getMaxLevel(Skill.CONSTITUTION)) {
                    player.setConstitution(player.getSkillManager().getMaxLevel(Skill.CONSTITUTION));
                } else {
                    player.setConstitution(player.getConstitution() + 120);
                }
                player.getInventory().delete(7210, 1);
                break;
            case 7178:
                player.getSkillManager().setCurrentLevel(Skill.HERBLORE, player.getSkillManager().getMaxLevel(Skill.HERBLORE) + 4);
                player.getSkillManager().setCurrentLevel(Skill.FARMING, player.getSkillManager().getMaxLevel(Skill.FARMING) + 4);
                if (player.getConstitution() + 120 > player.getSkillManager().getMaxLevel(Skill.CONSTITUTION)) {
                    player.setConstitution(player.getSkillManager().getMaxLevel(Skill.CONSTITUTION));
                } else {
                    player.setConstitution(player.getConstitution() + 120);
                }
                player.getInventory().delete(7178, 1);
                player.getInventory().add(7180, 1);
                break;
            case 7180:
                player.getSkillManager().setCurrentLevel(Skill.HERBLORE, player.getSkillManager().getMaxLevel(Skill.HERBLORE) + 4);
                player.getSkillManager().setCurrentLevel(Skill.FARMING, player.getSkillManager().getMaxLevel(Skill.FARMING) + 4);
                if (player.getConstitution() + 120 > player.getSkillManager().getMaxLevel(Skill.CONSTITUTION)) {
                    player.setConstitution(player.getSkillManager().getMaxLevel(Skill.CONSTITUTION));
                } else {
                    player.setConstitution(player.getConstitution() + 120);
                }
                player.getInventory().delete(7180, 1);
                break;
            case 7218:
                player.getSkillManager().setCurrentLevel(Skill.MINING, player.getSkillManager().getMaxLevel(Skill.MINING) + 4);
                player.getSkillManager().setCurrentLevel(Skill.WOODCUTTING, player.getSkillManager().getMaxLevel(Skill.WOODCUTTING) + 4);
                if (player.getConstitution() + 120 > player.getSkillManager().getMaxLevel(Skill.CONSTITUTION)) {
                    player.setConstitution(player.getSkillManager().getMaxLevel(Skill.CONSTITUTION));
                } else {
                    player.setConstitution(player.getConstitution() + 120);
                }
                player.getInventory().delete(7218, 1);
                player.getInventory().add(7220, 1);
                break;
            case 7220:
                player.getSkillManager().setCurrentLevel(Skill.MINING, player.getSkillManager().getMaxLevel(Skill.MINING) + 4);
                player.getSkillManager().setCurrentLevel(Skill.WOODCUTTING, player.getSkillManager().getMaxLevel(Skill.WOODCUTTING) + 4);
                if (player.getConstitution() + 120 > player.getSkillManager().getMaxLevel(Skill.CONSTITUTION)) {
                    player.setConstitution(player.getSkillManager().getMaxLevel(Skill.CONSTITUTION));
                } else {
                    player.setConstitution(player.getConstitution() + 120);
                }
                player.getInventory().delete(7220, 1);
                break;
            case 7479:
                int randomboost = RandomUtility.inclusiveRandom(3);

                if (randomboost == 1) {
                    player.getSkillManager().setCurrentLevel(Skill.MINING, player.getSkillManager().getMaxLevel(Skill.MINING) + 5);
                    player.getSkillManager().setCurrentLevel(Skill.SMITHING, player.getSkillManager().getMaxLevel(Skill.SMITHING) + 5);
                    player.getSkillManager().setCurrentLevel(Skill.AGILITY, player.getSkillManager().getMaxLevel(Skill.AGILITY) + 5);
                } else if (randomboost == 2) {
                    player.getSkillManager().setCurrentLevel(Skill.WOODCUTTING, player.getSkillManager().getMaxLevel(Skill.WOODCUTTING) + 5);
                    player.getSkillManager().setCurrentLevel(Skill.FLETCHING, player.getSkillManager().getMaxLevel(Skill.FLETCHING) + 5);
                    player.getSkillManager().setCurrentLevel(Skill.THIEVING, player.getSkillManager().getMaxLevel(Skill.THIEVING) + 5);
                } else if (randomboost == 3) {
                    player.getSkillManager().setCurrentLevel(Skill.HERBLORE, player.getSkillManager().getMaxLevel(Skill.HERBLORE) + 5);
                    player.getSkillManager().setCurrentLevel(Skill.FIREMAKING, player.getSkillManager().getMaxLevel(Skill.FIREMAKING) + 5);
                    player.getSkillManager().setCurrentLevel(Skill.CRAFTING, player.getSkillManager().getMaxLevel(Skill.CRAFTING) + 5);
                } else {
                    player.getSkillManager().setCurrentLevel(Skill.SLAYER, player.getSkillManager().getMaxLevel(Skill.SLAYER) + 5);
                    player.getSkillManager().setCurrentLevel(Skill.HUNTER, player.getSkillManager().getMaxLevel(Skill.HUNTER) + 5);
                    player.getSkillManager().setCurrentLevel(Skill.RUNECRAFTING, player.getSkillManager().getMaxLevel(Skill.RUNECRAFTING) + 5);
                    player.getSkillManager().setCurrentLevel(Skill.SKILLER, player.getSkillManager().getMaxLevel(Skill.SKILLER) + 5);
                }
                if (player.getConstitution() + 220 > player.getSkillManager().getMaxLevel(Skill.CONSTITUTION)) {
                    player.setConstitution(player.getSkillManager().getMaxLevel(Skill.CONSTITUTION));
                } else {
                    player.setConstitution(player.getConstitution() + 220);
                }
                player.getInventory().delete(7479, 1);

                break;

		/*case 608:
			if(player.getLearnRigour() == 0)
			{player.setLearnRigour(1);
			player.getPacketSender().sendMessage("You have just learned Rigour!");
			player.getInventory().delete(608, 1);}
			else
			{player.getPacketSender().sendMessage("You have already learned Rigour.");}
			break;
		case 607:
			if(player.getLearnAugury() == 0)
			{player.setLearnAugury(1);
			player.getPacketSender().sendMessage("You have just learned Augury!");
			player.getInventory().delete(607, 1);}
			else
			{player.getPacketSender().sendMessage("You have already learned Augury.");}
			break;
		case 786:
			if(player.getLearnCurses() == 0)
			{player.setLearnCurses(1);
			player.getPacketSender().sendMessage("You have just learned Curses!");
			player.getInventory().delete(786, 1);}
			else
			{player.getPacketSender().sendMessage("You have already learned Curses.");}
			break;
		case 4428:
			if(player.getLearnAncients() == 0)
			{player.setLearnAncients(1);
			player.getPacketSender().sendMessage("You have just learned Ancients!");
			player.getInventory().delete(4428, 1);}
			else
			{player.getPacketSender().sendMessage("You have already learned Ancients.");}
			break;
		case 6758:
			if(player.getLearnLunars() == 0)
			{player.setLearnLunars(1);
			player.getPacketSender().sendMessage("You have just learned Lunars!");
			player.getInventory().delete(6758, 1);}
			else
			{player.getPacketSender().sendMessage("You have already learned Lunars.");}
			break;*/


            case 11967: //Dwarf Cannon set
                player.getInventory().delete(11967, 1);
                player.getInventory().add(6, 1);
                player.getInventory().add(8, 1);
                player.getInventory().add(10, 1);
                player.getInventory().add(12, 1);
                break;

            case 407: //Oyster

                if (player.getUsername().contains("Justi") || player.getUsername().contains("Freelan")) {
                    player.getInventory().delete(407, 1);
                    player.getPacketSender().sendMessage("You have cracked open the oyster for 10 Manta Rays!");
                    player.getInventory().add(391, 10);
                } else
                    player.getPacketSender().sendMessage("This item is only for Freeland. Poor guy can't use a bank. Ask Pat for refund.");
                break;

			/*if (RandomUtility.getRandom(3) < 3) {
				player.getInventory().add(409, 1);
			} else if(RandomUtility.getRandom(4) < 4) {
				player.getInventory().add(411, 1);
			} else
				player.getInventory().add(413, 1);
			break;*/

            case 7956: //Supply Casket
                int[] rewards = {5509, 5511, 5512, 5513, 5514, 5515};
                player.getInventory().delete(7956, 1);
                player.getPointsHandler().setsupplycaskets(1, true);
                player.getPacketSender().sendMessage("You have opened " + player.getPointsHandler().getsupplycaskets() + " Supply caskets!");
                player.getPointsHandler().refreshPanel();
                int rewardPos = RandomUtility.inclusiveRandom(rewards.length - 1);
                player.getInventory().add(rewards[rewardPos], 2);
                break;

            case 220703:
                int lootOne = 2;
                int lootTwo = 2;

                int lootOneQty = 0;
                int lootTwoQty = 0;

                int lootTypeOne = RandomUtility.inclusiveRandom(1, 4);
                int lootTypeTwo = RandomUtility.inclusiveRandom(1, 4);

                if (player.getInventory().getFreeSlots() < 3) {
                    player.getPacketSender().sendMessage("@red@You must have 3 inventory spaces available to open this crate.");
                    break;
                }

                //high supply
                if (lootTypeOne == 1) {
                    lootOne = GameSettings.HIGHSUPPLYDROPS[RandomUtility.inclusiveRandom(GameSettings.HIGHSUPPLYDROPS.length - 1)];
                    lootOneQty = 10 + RandomUtility.inclusiveRandom(30);
                }

                //low supply
                else {
                    lootOne = GameSettings.LOWSUPPLYDROPS[RandomUtility.inclusiveRandom(GameSettings.LOWSUPPLYDROPS.length - 1)];
                    lootOneQty = 100 + RandomUtility.inclusiveRandom(200);
                }


                //high supply
                if (lootTypeTwo == 1) {
                    lootTwo = GameSettings.HIGHSUPPLYDROPS[RandomUtility.inclusiveRandom(GameSettings.HIGHSUPPLYDROPS.length - 1)];
                    lootTwoQty = 10 + RandomUtility.inclusiveRandom(30);
                }

                //low supply
                else {
                    lootTwo = GameSettings.LOWSUPPLYDROPS[RandomUtility.inclusiveRandom(GameSettings.LOWSUPPLYDROPS.length - 1)];
                    lootTwoQty = 100 + RandomUtility.inclusiveRandom(200);
                }

                if (!player.chatFilter && player.personalFilterKeyLoot) {
                    Item itemOne = new Item(lootOne, lootOneQty);
                    Item itemTwo = new Item(lootTwo, lootTwoQty);
                    player.getPacketSender().sendMessage("Your first supply loot is: " + lootOneQty + " " + itemOne.getName());
                    player.getPacketSender().sendMessage("Your second supply loot is: " + lootTwoQty + " " + itemTwo.getName());
                }

                player.getInventory().delete(220703, 1);
                player.getInventory().add(lootOne, lootOneQty);
                player.getInventory().add(lootTwo, lootTwoQty);

                if (player.looterBanking && player.getGameMode() != GameMode.ULTIMATE_IRONMAN) {
                    player.getInventory().switchItem(player.getBank(Bank.getTabForItem(player, lootOne)), new Item(lootOne, lootOneQty), player.getInventory().getSlot(lootOne), false, true);
                    player.getInventory().switchItem(player.getBank(Bank.getTabForItem(player, lootTwo)), new Item(lootTwo, lootTwoQty), player.getInventory().getSlot(lootTwo), false, true);
                }


                int extraLoot = RandomUtility.inclusiveRandom(25);

                if (extraLoot == 1)
                    LootCrate.openCrate(player);


                break;

            case 9475: //mint cake
                player.getInventory().delete(9475, 1);
                player.tempRngBoost = true;
                player.getPacketSender().sendMessage("@blu@You will have 5 percent increased drop rate on your next loot!");

                break;

            case 962: //Christmas Cracker
                int[] phatRewards = {1038, 1040, 1042, 1044, 1046, 1048};
                player.getInventory().delete(962, 1);
                int phatRewardPos = RandomUtility.inclusiveRandom(phatRewards.length - 1);
                int phat = phatRewards[phatRewardPos];
                player.getInventory().add(phat, 1);
                itemName = new Item(phat).getDefinition().getName();
                itemMessage = Misc.anOrA(itemName) + " " + itemName;
                message = "@gre@[HOLIDAY] " + player.getUsername()
                        + " has just received @red@" + itemMessage + "@blu@ from a Christmas Cracker!";
                World.sendMessage("drops", message);
                DropLog.submit(player, new DropLogEntry(phat, 1));
                PlayerLogs.log(player.getUsername(), player.getUsername() + " received " + itemMessage + " from a Christmas Cracker!");
                break;

            case 1959: //Pumpkin
                int[] pumpRewards = {1053, 1055, 1057};
                player.getInventory().delete(1959, 1);
                int pumpRewardPos = RandomUtility.inclusiveRandom(pumpRewards.length - 1);
                int pump = pumpRewards[pumpRewardPos];
                player.getInventory().add(pump, 1);
                itemName = new Item(pump).getDefinition().getName();
                itemMessage = Misc.anOrA(itemName) + " " + itemName;
                message = "@or2@[HOLIDAY] " + player.getUsername()
                        + " has just received @blu@" + itemMessage + "@or2@ from a Pumpkin!";
                World.sendMessage("drops", message);
                DropLog.submit(player, new DropLogEntry(pump, 1));
                PlayerLogs.log(player.getUsername(), player.getUsername() + " received " + itemMessage + " from a Pumpkin!");
                break;

            case 1961: //Easter Egg
                int[] eggRewards = {1037, 223448, 4565, 221214, 222351, 222353};
                player.getInventory().delete(1961, 1);
                int eggRewardPos = RandomUtility.inclusiveRandom(eggRewards.length - 1);
                int egg = eggRewards[eggRewardPos];
                player.getInventory().add(egg, 1);
                itemName = new Item(egg).getDefinition().getName();
                itemMessage = Misc.anOrA(itemName) + " " + itemName;
                message = "@or2@[HOLIDAY] " + player.getUsername()
                        + " has just received @blu@" + itemMessage + "@or2@ from a Easter Egg!";
                World.sendMessage("drops", message);
                DropLog.submit(player, new DropLogEntry(egg, 1));
                PlayerLogs.log(player.getUsername(), player.getUsername() + " received " + itemMessage + " from a Easter Egg!");
                break;

            case 1779:
                player.getInventory().delete(1779, 1);
                player.getInventory().add(1777, 1);
                player.getSkillManager().addExperience(Skill.CRAFTING, 15);
                break;

            case 76: //gift
                if (!player.getInventory().isFull()) {
                    EliteMysteryBox.openBox(player);
                } else {
                    player.getPacketSender().sendMessage("You need to make room for present loot!!");
                }

                break;
            case 4560:
                player.setBonusTime(player.getBonusTime() + 10);
                //player.getPacketSender().sendMessage("You've added 10 minutes to your bonus timer!");

                int randomHween = RandomUtility.inclusiveRandom(5000);


                if (randomHween == 1) {
                    player.getInventory().add(1959, 1);
                    World.sendMessage("drops", "@bla@[Halloween] " + player.getUsername() + " just received a @or2@Pumpkin@bla@ from a Skeletal Candy!");
                } else if (randomHween > 1 && randomHween < 51) {
                    player.getInventory().add(9921, 1);
                    World.sendMessage("drops", "@or2@[Halloween] " + player.getUsername() + " just received @bla@Skeleton Boots@or2@ from a Skeletal Candy!");
                } else if (randomHween > 51 && randomHween < 101) {
                    player.getInventory().add(9922, 1);
                    World.sendMessage("drops", "@or2@[Halloween] " + player.getUsername() + " just received @bla@Skeleton Gloves@or2@ from a Skeletal Candy!");
                } else if (randomHween > 101 && randomHween < 151) {
                    player.getInventory().add(9923, 1);
                    World.sendMessage("drops", "@or2@[Halloween] " + player.getUsername() + " just received @bla@Skeleton Leggings@or2@ from a Skeletal Candy!");
                } else if (randomHween > 151 && randomHween < 201) {
                    player.getInventory().add(9924, 1);
                    World.sendMessage("drops", "@or2@[Halloween] " + player.getUsername() + " just received @bla@Skeleton Shirt@or2@ from a Skeletal Candy!");
                } else if (randomHween > 201 && randomHween < 251) {
                    player.getInventory().add(9925, 1);
                    World.sendMessage("drops", "@or2@[Halloween] " + player.getUsername() + " just received @bla@Skeleton Mask@or2@ from a Skeletal Candy!");
                }


                player.getInventory().delete(4560, 1);

                break;

            case 19864:
                if (player.getInventory().contains(19864)) {
                    player.getPointsHandler().setAmountDonated(player.getPointsHandler().getAmountDonated(), 1);
                    player.getPacketSender().sendMessage("You now have $" + player.getPointsHandler().getAmountDonated() + " donated on this account.");

                    player.getInventory().delete(19864, 1);
                    DonationBonds.checkForRankUpdate(player);
                }
                break;
            case 4562:

                int timeToAdd = 1000;

                if (player.Lucky >= 3)
                    timeToAdd += 500;
                else if (player.Lucky >= 1)
                    timeToAdd += 250;

                if (player.getBonusTime() + timeToAdd >= Integer.MAX_VALUE) {
                    player.setBonusTime(Integer.MAX_VALUE);
                } else {
                    player.setBonusTime(player.getBonusTime() + timeToAdd);
                }
                player.getPacketSender().sendMessage("You've added " + timeToAdd + " ticks to your bonus timer!");

                int chance = 5000;

                if (player.Lucky >= 3)
                    chance = 4500;
                else if (player.Lucky >= 1)
                    chance = 4750;

                int randomHoliday = RandomUtility.inclusiveRandom(chance);

                if (randomHoliday == 1) {
                    player.getInventory().add(962, 1);

                    if (player.getGameMode() != GameMode.SEASONAL_IRONMAN)
                        World.sendMessage("drops", "@gre@[CANDY] " + player.getUsername() + " just received a @red@Christmas Cracker @gre@from a Rare Candy!");
                } else if (randomHoliday == 2 || randomHoliday == 3) {
                    player.getInventory().add(1959, 1);

                    if (player.getGameMode() != GameMode.SEASONAL_IRONMAN)
                        World.sendMessage("drops", "@gre@[CANDY] " + player.getUsername() + " just received a @or2@Pumpkin@gre@ from a Rare Candy!");
                } else if (randomHoliday == 4 || randomHoliday == 5) {
                    player.getInventory().add(1961, 1);

                    if (player.getGameMode() != GameMode.SEASONAL_IRONMAN)
                        World.sendMessage("drops", "@gre@[CANDY] " + player.getUsername() + " just received an @blu@Easter Egg@gre@ from a Rare Candy!");
                } else if (randomHoliday == 19 || randomHoliday == 20) {
                    player.getInventory().add(1050, 1);

                    if (player.getGameMode() != GameMode.SEASONAL_IRONMAN)
                        World.sendMessage("drops", "@gre@[CANDY] " + player.getUsername() + " just received a @red@Santa Hat@gre@ from a Rare Candy!");
                }


                player.getInventory().delete(4562, 1);
                player.setRareCandy(player.getRareCandy() + 1);
                break;

            case 6854: //Equipment Upgrade Box

                if (player.getInventory().getFreeSlots() >= 5) {

                    player.getInventory().delete(6854, 1);
                    int[] euRewards = {4033, 4034, 4035, 4036, 4037};

                    for (int i = 0; i < 5; i++) {
                        int euRewardsPos = RandomUtility.inclusiveRandom(euRewards.length - 1);
                        player.getInventory().add(euRewards[euRewardsPos], 1);
                    }
                } else {
                    player.getPacketSender().sendMessage("@red@You need 5 empty slots to open this box.");
                }
                break;


            case 6542:

                int qty = RandomUtility.inclusiveRandom(4) + 1;
                player.getInventory().add(989, qty);
                player.getInventory().delete(6542, 1);
                break;

            case 2714: //basic clue casket
                player.getInventory().delete(2714, 1);
                int[] ttrewards = {10382, 10378, 10380, 10376, 10390, 10386, 10388, 10384, 10374, 10370, 10372, 10368, 2673, 2669, 2671, 2675, 2665, 2661, 2663, 2667, 2657, 2653, 2655, 2659, 13113, 13109, 19272, 13115, 13111, 13107, 19278, 19275, 2651, 19281, 19284, 19287, 19290, 19293, 19296, 19299, 19302, 19305, 2595, 2591, 2593, 2597, 2613, 2607, 2609, 2611, 2619, 2615, 2617, 2621, 3486, 3481, 3483, 3488, 7394, 7390, 7386, 7370, 7378, 7374, 7382, 10362, 10364, 10366, 2579, 19333, 19346, 19354, 19350, 19358, 19348, 19356, 19352, 19360, 15503, 15505, 15507, 15509, 15511, 19443, 19445, 19447, 19449, 19451, 19453, 19455, 19457, 19459, 19461, 19463, 19465};
                int ttrewardPos = RandomUtility.inclusiveRandom(ttrewards.length - 1);
                player.getInventory().add(ttrewards[ttrewardPos], 1);
                break;

            case 2715: //3rd age casket
                player.getInventory().delete(2715, 1);
                player.getPointsHandler().setopened3rdage(1, true);
                player.getPacketSender().sendMessage("You have opened " + player.getPointsHandler().getopened3rdage() + " 3rd Age caskets!");
                player.getPointsHandler().refreshPanel();
                int[] tarewards = {10350, 10348, 10346, 10352, 10334, 10330, 10332, 10336, 10342, 10338, 10340, 10345, 19311, 19308, 19314, 19317, 19320};
                int tarewardPos = RandomUtility.inclusiveRandom(tarewards.length - 1);
                player.getInventory().add(tarewards[tarewardPos], 1);
                break;

            case 2717: //holiday item casket
                player.getInventory().delete(2717, 1);
                player.getPointsHandler().setopenedholiday(1, true);
                player.getPacketSender().sendMessage("You have opened " + player.getPointsHandler().getopenedholiday() + " Holiday Item caskets!");
                player.getPointsHandler().refreshPanel();
                int[] hirewards = {1050, 1057, 1055, 1053, 1042, 1048, 1038, 1040, 1044, 1046, 9920, 10507, 14050, 14044, 4084, 4565, 10735, 5607, 11288};
                int hirewardPos = RandomUtility.inclusiveRandom(hirewards.length - 1);
                player.getInventory().add(hirewards[hirewardPos], 1);
                break;

            case 2718: //Dragon casket
                player.getInventory().delete(2718, 1);
                int[] dcrewards = {1215, 6739, 1434, 1149, 1305, 11335, 15259, 1187, 4587, 11613, 20555, 1377, 7158, 3140, 4585, 4087, 14484, 14479};
                int dcrewardPos = RandomUtility.inclusiveRandom(dcrewards.length - 1);
                player.getInventory().add(dcrewards[dcrewardPos], 1);
                break;

            case 2720: //Rune Armor Casket
                player.getInventory().delete(2720, 1);
                int[] nacrewards = {1213, 1359, 1432, 1147, 1303, 1163, 1275, 1185, 1333, 1201, 1347, 1373, 1319, 1113, 1093, 1079, 3101, 1127};
                int nacrewardPos = RandomUtility.inclusiveRandom(nacrewards.length - 1);
                player.getInventory().add(nacrewards[nacrewardPos], 1);
                break;

            case 10181: //Dagannoth King Casket
                player.getInventory().delete(10181, 1);
                player.getPointsHandler().setbosscaskets(1, true);
                player.getPacketSender().sendMessage("You have opened " + player.getPointsHandler().getbosscaskets() + " Boss caskets!");
                player.getPointsHandler().refreshPanel();
                int[] dkcrewards = {6731, 6733, 6735, 6737};
                int dkcrewardPos = RandomUtility.inclusiveRandom(dkcrewards.length - 1);
                player.getInventory().add(dkcrewards[dkcrewardPos], 1);
                break;

            case 10183: //King Black Dragon Casket
                player.getInventory().delete(10183, 1);
                player.getPointsHandler().setbosscaskets(1, true);
                player.getPacketSender().sendMessage("You have opened " + player.getPointsHandler().getbosscaskets() + " Boss caskets!");
                player.getPointsHandler().refreshPanel();
                int[] kbdcrewards = {4453, 11286};
                int kbdcrewardPos = RandomUtility.inclusiveRandom(kbdcrewards.length - 1);
                player.getInventory().add(kbdcrewards[kbdcrewardPos], 1);
                break;

		/*case 10185: //Kalphite Queen Casket
			player.getInventory().delete(10185, 1);
			player.getPointsHandler().setbosscaskets(1, true);
			player.getPacketSender().sendMessage("You have opened " + player.getPointsHandler().getbosscaskets() + " Boss caskets!");
			player.getPointsHandler().refreshPanel();
			int[] kqcrewards = 		{20057, 20058, 20059};
			int kqcrewardPos = RandomUtility.getRandom(kqcrewards.length-1);
			player.getInventory().add(kqcrewards[kqcrewardPos],1);
			break;*/

            case 10187: //Armadyl Casket
                player.getInventory().delete(10187, 1);
                player.getPointsHandler().setbosscaskets(1, true);
                player.getPacketSender().sendMessage("You have opened " + player.getPointsHandler().getbosscaskets() + " Boss caskets!");
                player.getPointsHandler().refreshPanel();
                int[] acrewards = {11702, 11718, 11720, 11722};
                int acrewardPos = RandomUtility.inclusiveRandom(acrewards.length - 1);
                player.getInventory().add(acrewards[acrewardPos], 1);
                break;

            case 10189: //Bandos Casket
                player.getInventory().delete(10189, 1);
                player.getPointsHandler().setbosscaskets(1, true);
                player.getPacketSender().sendMessage("You have opened " + player.getPointsHandler().getbosscaskets() + " Boss caskets!");
                player.getPointsHandler().refreshPanel();
                int[] bcrewards = {11704, 11724, 11726, 11728};
                int bcrewardPos = RandomUtility.inclusiveRandom(bcrewards.length - 1);
                player.getInventory().add(bcrewards[bcrewardPos], 1);
                break;

            case 10191: //Saradomin Casket
                player.getInventory().delete(10191, 1);
                player.getPointsHandler().setbosscaskets(1, true);
                player.getPacketSender().sendMessage("You have opened " + player.getPointsHandler().getbosscaskets() + " Boss caskets!");
                player.getPointsHandler().refreshPanel();
                int[] screwards = {11706, 11730, 13051};
                int screwardPos = RandomUtility.inclusiveRandom(screwards.length - 1);
                player.getInventory().add(screwards[screwardPos], 1);
                break;

            case 10193: //Zamorak Casket
                player.getInventory().delete(10193, 1);
                player.getPointsHandler().setbosscaskets(1, true);
                player.getPacketSender().sendMessage("You have opened " + player.getPointsHandler().getbosscaskets() + " Boss caskets!");
                player.getPointsHandler().refreshPanel();
                int[] zcrewards = {11708, 11716};
                int zcrewardPos = RandomUtility.inclusiveRandom(zcrewards.length - 1);
                player.getInventory().add(zcrewards[zcrewardPos], 1);
                break;

            case 10195: //Barrows Casket
                player.getInventory().delete(10195, 1);
                player.getPointsHandler().setbosscaskets(1, true);
                player.getPacketSender().sendMessage("You have opened " + player.getPointsHandler().getbosscaskets() + " Boss caskets!");
                player.getPointsHandler().refreshPanel();
                int[] tdcrewards = {11846, 11848, 11850, 11852, 11854, 11856, 18897};
                int tdcrewardPos = RandomUtility.inclusiveRandom(tdcrewards.length - 1);
                player.getInventory().add(tdcrewards[tdcrewardPos], 1);
                break;

            case 10197: //Corporeal Beast Casket
                player.getInventory().delete(10197, 1);
                player.getPointsHandler().setbosscaskets(1, true);
                player.getPacketSender().sendMessage("You have opened " + player.getPointsHandler().getbosscaskets() + " Boss caskets!");
                player.getPointsHandler().refreshPanel();
                int[] cbcrewards = {13746, 13748, 13750, 13752};
                int cbcrewardPos = RandomUtility.inclusiveRandom(cbcrewards.length - 1);
                player.getInventory().add(cbcrewards[cbcrewardPos], 1);
                break;

            case 10199: //Zulrah Casket
                player.getInventory().delete(10199, 1);
                player.getPointsHandler().setbosscaskets(1, true);
                player.getPacketSender().sendMessage("You have opened " + player.getPointsHandler().getbosscaskets() + " Boss caskets!");
                player.getPointsHandler().refreshPanel();
                int[] zlcrewards = {12926, 12284, 12282};
                int zlcrewardPos = RandomUtility.inclusiveRandom(zlcrewards.length - 1);
                player.getInventory().add(zlcrewards[zlcrewardPos], 1);
                break;

            case 10201: //Nex Casket
                player.getInventory().delete(10201, 1);
                player.getPointsHandler().setbosscaskets(1, true);
                player.getPacketSender().sendMessage("You have opened " + player.getPointsHandler().getbosscaskets() + " Boss caskets!");
                player.getPointsHandler().refreshPanel();
                int[] ncrewards = {14008, 14009, 14010, 14011, 14012, 14013, 14014, 14015, 14016};
                int ncrewardPos = RandomUtility.inclusiveRandom(ncrewards.length - 1);
                player.getInventory().add(ncrewards[ncrewardPos], 1);
                break;

            case 10203: //Abyssal Sire Casket
                player.getInventory().delete(10203, 1);
                player.getPointsHandler().setbosscaskets(1, true);
                player.getPacketSender().sendMessage("You have opened " + player.getPointsHandler().getbosscaskets() + " Boss caskets!");
                player.getPointsHandler().refreshPanel();
                int[] ascrewards = {13045, 13047, 19981};
                int ascrewardPos = RandomUtility.inclusiveRandom(ascrewards.length - 1);
                player.getInventory().add(ascrewards[ascrewardPos], 1);
                break;

            case 10205: //Cerberus Casket
                player.getInventory().delete(10205, 1);
                player.getPointsHandler().setbosscaskets(1, true);
                player.getPacketSender().sendMessage("You have opened " + player.getPointsHandler().getbosscaskets() + " Boss caskets!");
                player.getPointsHandler().refreshPanel();
                int[] ccrewards = {6640, 6642, 6645};
                int ccrewardPos = RandomUtility.inclusiveRandom(ccrewards.length - 1);
                player.getInventory().add(ccrewards[ccrewardPos], 1);
                break;

            case 10207: //Skotizo Casket
                player.getInventory().delete(10207, 1);
                player.getPointsHandler().setbosscaskets(1, true);
                player.getPacketSender().sendMessage("You have opened " + player.getPointsHandler().getbosscaskets() + " Boss caskets!");
                player.getPointsHandler().refreshPanel();
                int[] skcrewards = {12061, 901, 901};
                int skcrewardPos = RandomUtility.inclusiveRandom(skcrewards.length - 1);
                player.getInventory().add(skcrewards[skcrewardPos], 1);
                break;

            case 405: //Slayer Loot Crate
                int[] glcrewards = {4212, 15126, 4151, 11235, 15486, 6571, 11732, 6914, 6889, 6916, 6918, 6920, 6924, 4153, 15403, 2577, 2581, 6, 8, 10, 12};
                int glcrewardPos = RandomUtility.inclusiveRandom(glcrewards.length - 1);
                player.getInventory().delete(405, 1);
                player.getInventory().add(glcrewards[glcrewardPos], 1);
                break;
		/*case 10209: //Noob Casket
			player.getInventory().delete(10209, 1);
			int[] nbcrewards =
			int nbcrewardPos = RandomUtility.getRandom(nbcrewards.length-1);
			player.getInventory().add(nbcrewards[nbcrewardPos],1);
			break;
		case 10211: //Revenant Casket
			player.getInventory().delete(10211, 1);
			int[] rcrewards =
			int rcrewardPos = RandomUtility.getRandom(rcrewards.length-1);
			player.getInventory().add(rcrewards[rcrewardPos],1);
			break;*/
            case 2721: //Medium Raid Casket
                player.setRaidPoints(1000000);

                break;
            case 2724: //Hard Raid Casket
                player.getInventory().delete(2724, 1);
                player.getPacketSender().sendMessage("@red@ Congrats on the Raid Loot!!");
                int[] crtcrewards = {899, 894, 895, 896, 900, 20998};
                int crtcrewardPos = RandomUtility.inclusiveRandom(crtcrewards.length - 1);
                player.getInventory().add(crtcrewards[crtcrewardPos], 1);
                break;
            case 2726: //Raid Supplies
                player.getInventory().delete(2726, 1);
                int[] rsrewards = {};
                int rsrewardPos = RandomUtility.inclusiveRandom(rsrewards.length - 1);
                //add 12 food based on cooking level
                //add potions based on herblore level
                //add melee armor based on smithing level
                //add range armor based on crafting level
                //add mage armor based on magic level
                //add amulet based on crafting level
                //add barrows gloves if in bank
                //add fire cape if in bank
                //add bow and bolts based on fletching
                //add melee weapon based on smithing level
                //add random battlestaff for magic
                player.getInventory().add(rsrewards[rsrewardPos], 1);
                break;
            case 7630: //Raid Armor Crate
                player.getInventory().delete(7630, 1);
                int[] racrewards = {};
                int racrewardPos = RandomUtility.inclusiveRandom(racrewards.length - 1);
                player.getInventory().add(racrewards[racrewardPos], 1);
                break;


            case 17415: //Iron block
                player.getInventory().delete(17415, 1);
                qty = RandomUtility.inclusiveRandom(2) + 3;
                if (player.getInventory().contains(213243) || player.getEquipment().contains(213243) ||
                        player.getInventory().contains(13661) || player.getEquipment().contains(13661)) {
                    player.performGraphic(new Graphic(446, GraphicHeight.MIDDLE));
                    player.getAchievementTracker().progress(AchievementData.SMELT_25_IRON_BARS, qty);
                    player.getInventory().add(2352, qty);
                } else
                    player.getInventory().add(440, qty);
                break;
            case 17416: //Coal block
                player.getInventory().delete(17416, 1);
                qty = RandomUtility.inclusiveRandom(2) + 3;
                if (player.getInventory().contains(213243) || player.getEquipment().contains(213243) ||
                        player.getInventory().contains(13661) || player.getEquipment().contains(13661)) {
                    player.performGraphic(new Graphic(446, GraphicHeight.MIDDLE));
                    player.getAchievementTracker().progress(AchievementData.SMELT_100_STEEL_BARS, qty);
                    player.getInventory().add(2354, qty);
                } else
                    player.getInventory().add(453, qty);
                break;
            case 17422: //Gold block
                player.getInventory().delete(17422, 1);
                qty = RandomUtility.inclusiveRandom(2) + 3;
                if (player.getInventory().contains(213243) || player.getEquipment().contains(213243) ||
                        player.getInventory().contains(13661) || player.getEquipment().contains(13661)) {
                    player.performGraphic(new Graphic(446, GraphicHeight.MIDDLE));
                    player.getInventory().add(2358, qty);
                } else
                    player.getInventory().add(444, qty);
                break;
            case 17418: //Mithril block
                player.getInventory().delete(17418, 1);
                qty = RandomUtility.inclusiveRandom(2) + 3;
                if (player.getInventory().contains(213243) || player.getEquipment().contains(213243) ||
                        player.getInventory().contains(13661) || player.getEquipment().contains(13661)) {
                    player.performGraphic(new Graphic(446, GraphicHeight.MIDDLE));
                    player.getAchievementTracker().progress(AchievementData.SMELT_250_MITHRIL_BARS, qty);
                    player.getInventory().add(2360, qty);
                } else
                    player.getInventory().add(447, qty);
                break;
            case 17424: //Adamantite block
                player.getInventory().delete(17424, 1);
                qty = RandomUtility.inclusiveRandom(2) + 3;
                if (player.getInventory().contains(213243) || player.getEquipment().contains(213243) ||
                        player.getInventory().contains(13661) || player.getEquipment().contains(13661)) {
                    player.performGraphic(new Graphic(446, GraphicHeight.MIDDLE));
                    player.getAchievementTracker().progress(AchievementData.SMELT_500_ADDY_BARS, qty);
                    player.getInventory().add(2362, qty);
                } else
                    player.getInventory().add(449, qty);
                break;
            case 17420: //Runite block
                player.getInventory().delete(17420, 1);
                qty = RandomUtility.inclusiveRandom(2) + 3;
                if (player.getInventory().contains(213243) || player.getEquipment().contains(213243) ||
                        player.getInventory().contains(13661) || player.getEquipment().contains(13661)) {
                    player.performGraphic(new Graphic(446, GraphicHeight.MIDDLE));
                    player.getAchievementTracker().progress(AchievementData.SMELT_1000_RUNE_BARS, qty);
                    player.getInventory().add(2364, qty);
                } else
                    player.getInventory().add(451, qty);
                break;
            case 17426: //Amethyst block
                player.getInventory().delete(17426, 1);
                qty = RandomUtility.inclusiveRandom(2) + 3;
                player.getInventory().add(221347, qty);
                player.getSkillManager().stopSkilling();
                break;

            //fish piles
            case 2748:
                player.getInventory().delete(2748, 1);
                player.getInventory().add(317, RandomUtility.inclusiveRandom(2) + 3);
                break;
            case 2749:
                player.getInventory().delete(2749, 1);
                player.getInventory().add(321, RandomUtility.inclusiveRandom(2) + 3);
                break;
            case 2750:
                player.getInventory().delete(2750, 1);
                player.getInventory().add(327, RandomUtility.inclusiveRandom(2) + 3);
                break;
            case 2751:
                player.getInventory().delete(2751, 1);
                player.getInventory().add(345, RandomUtility.inclusiveRandom(2) + 3);
                break;
            case 2752:
                player.getInventory().delete(2752, 1);
                player.getInventory().add(353, RandomUtility.inclusiveRandom(2) + 3);
                break;
            case 2753:
                player.getInventory().delete(2753, 1);
                player.getInventory().add(341, RandomUtility.inclusiveRandom(2) + 3);
                break;
            case 2754:
                player.getInventory().delete(2754, 1);
                player.getInventory().add(363, RandomUtility.inclusiveRandom(2) + 3);
                break;
            case 2755:
                player.getInventory().delete(2755, 1);
                player.getInventory().add(335, RandomUtility.inclusiveRandom(2) + 3);
                break;
            case 2756:
                player.getInventory().delete(2756, 1);
                player.getInventory().add(331, RandomUtility.inclusiveRandom(2) + 3);
                break;
            case 2757:
                player.getInventory().delete(2757, 1);
                player.getInventory().add(377, RandomUtility.inclusiveRandom(2) + 3);
                break;
            case 2758:
                player.getInventory().delete(2758, 1);
                player.getInventory().add(359, RandomUtility.inclusiveRandom(2) + 3);
                break;
            case 2759:
                player.getInventory().delete(2759, 1);
                player.getInventory().add(371, RandomUtility.inclusiveRandom(2) + 3);
                break;
            case 2760:
                player.getInventory().delete(2760, 1);
                player.getInventory().add(7944, RandomUtility.inclusiveRandom(2) + 3);
                break;
            case 2761:
                player.getInventory().delete(2761, 1);
                player.getInventory().add(389, RandomUtility.inclusiveRandom(2) + 3);
                break;
            case 2762:
                player.getInventory().delete(2762, 1);
                player.getInventory().add(383, RandomUtility.inclusiveRandom(2) + 3);
                break;
            case 2763:
                player.getInventory().delete(2763, 1);
                player.getInventory().add(15270, RandomUtility.inclusiveRandom(2) + 3);
                break;
            case 2780:
                player.getInventory().delete(2780, 1);
                player.getInventory().add(213439, RandomUtility.inclusiveRandom(2) + 3);
                break;


            case 10025:
                CharmBox.open(player);
                break;


            case 2677://500 Paepoints
                player.setPaePoints(player.getPaePoints() + 500);
                player.getInventory().delete(2677, 1);
                player.getPacketSender().sendMessage("You redeem the scroll 500 PaePoints.");
                player.sendMessage("<img=0>You now have @red@" + player.getPaePoints() + " PaePoints!");
                player.getPointsHandler().refreshPanel();
                PlayerPanel.refreshPanel(player);
                break;
            case 2678://1500 Paepoints
                player.setPaePoints(player.getPaePoints() + 1500);
                player.getInventory().delete(2678, 1);
                player.getPacketSender().sendMessage("You redeem the scroll 1500 PaePoints.");
                player.sendMessage("<img=0>You now have @red@" + player.getPaePoints() + " PaePoints!");
                player.getPointsHandler().refreshPanel();
                PlayerPanel.refreshPanel(player);
                break;
            case 2679://1000 Paepoints
                player.setPaePoints(player.getPaePoints() + 1000);
                player.getInventory().delete(2679, 1);
                player.getPacketSender().sendMessage("You redeem the scroll 1000 PaePoints.");
                player.sendMessage("<img=0>You now have @red@" + player.getPaePoints() + " PaePoints!");
                player.getPointsHandler().refreshPanel();
                PlayerPanel.refreshPanel(player);
                break;
            case 2680://2500 Paepoints
                player.setPaePoints(player.getPaePoints() + 2500);
                player.getInventory().delete(2680, 1);
                player.getPacketSender().sendMessage("You redeem the scroll 2500 PaePoints.");
                player.sendMessage("<img=0>You now have @red@" + player.getPaePoints() + " PaePoints!");
                player.getPointsHandler().refreshPanel();
                PlayerPanel.refreshPanel(player);
                break;
            case 2681://5000 Paepoints
                player.setPaePoints(player.getPaePoints() + 5000);
                player.getInventory().delete(2681, 1);
                player.getPacketSender().sendMessage("You redeem the scroll 5000 Paepoints.");
                player.sendMessage("<img=0>You now have @red@" + player.getPaePoints() + " PaePoints!");
                player.getPointsHandler().refreshPanel();
                PlayerPanel.refreshPanel(player);
                break;
            case 2682://10000 Paepoints
                player.setPaePoints(player.getPaePoints() + 10000);
                player.getInventory().delete(2682, 1);
                player.getPacketSender().sendMessage("You redeem the scroll 10000 Paepoints.");
                player.sendMessage("<img=0>You now have @red@" + player.getPaePoints() + " PaePoints!");
                player.getPointsHandler().refreshPanel();
                PlayerPanel.refreshPanel(player);
                break;
            case 2683://Starter pack
                player.getInventory().delete(2683, 1);
                player.getInventory().add(6199, 3);
                player.getInventory().add(989, 25);
                player.setPaePoints(player.getPaePoints() + 1000);
                player.getPacketSender().sendMessage("You redeem the scroll 1000 PaePoints, 3 Mystery boxes, and 25 Crystal keys.");
                player.sendMessage("<img=0>You now have @red@" + player.getPaePoints() + " PaePoints!");
                player.getPointsHandler().refreshPanel();
                PlayerPanel.refreshPanel(player);

                break;
            case 2684://Elite pack
                player.getInventory().delete(2684, 1);
                player.getInventory().add(15501, 1);
                player.getInventory().add(6199, 3);
                player.setPaePoints(player.getPaePoints() + 2500);
                player.getPacketSender().sendMessage("You redeem the scroll 2500 PaePoints, 1 Elite mystery box, and 3 Mystery boxes.");
                player.sendMessage("<img=0>You now have @red@" + player.getPaePoints() + " PaePoints!");
                player.getPointsHandler().refreshPanel();
                PlayerPanel.refreshPanel(player);

                break;
            case 2685://Event box
                player.getInventory().delete(2685, 1);

                int randomEventLoot = RandomUtility.inclusiveRandom(100);

                if (randomEventLoot == 1) {
                    player.getInventory().add(15501, 1);
                    World.sendMessage("drops", "@mag@[EVENT BOX] " + player.getUsername() + " just received an Elite Mystery Box from an Event Box!");
                } else if (randomEventLoot >= 2 && randomEventLoot <= 11) {
                    player.getInventory().add(6199, 1);
                    World.sendMessage("drops", "@mag@[EVENT BOX] " + player.getUsername() + " just received a Mystery Box from an Event Box!");
                } else if (randomEventLoot >= 12 && randomEventLoot <= 25) {
                    player.getInventory().add(4562, 2);
                } else if (randomEventLoot == 26) {
                    player.getInventory().add(2957, 1);
                    World.sendMessage("drops", "@mag@[EVENT BOX] " + player.getUsername() + " just received a Candy Pouch from an Event Box!");
                } else if (randomEventLoot >= 27 && randomEventLoot <= 33) {
                    player.getInventory().add(2677, 1);
                } else if (randomEventLoot >= 34 && randomEventLoot <= 40) {
                    player.getInventory().add(995, 5000000);
                } else if (randomEventLoot <= 70) {
                    player.getInventory().add(995, 1000000);
                } else {
                    player.getInventory().add(989, 5);
                }


                player.getPointsHandler().refreshPanel();
                PlayerPanel.refreshPanel(player);

                break;
            case 7510:
                int newHP = player.getConstitution() - 10;
                player.dealDamage(new Hit(10, Hitmask.RED, CombatIcon.MELEE));

                if (newHP >= 1) {
                    player.setConstitution(newHP);
                } else {
                    player.setConstitution(1);
                }
                break;

            //Donation Box
            case 6183:
                DonationBox.open(player);
                break;
            //Clue Scroll
            case 7509:
                player.setConstitution(10);
                player.forceChat("Ow, that hurt!");
                player.getPacketSender().sendMessage("Biting the rock cake lowered your HP to 1 and healing effects have been Disabled.");
                player.healingEffects = false;
                break;
            case 15387:
                player.getInventory().delete(15387, 1);
                rewards = new int[]{1377, 1149, 7158, 3000, 219, 5016, 6293, 6889, 2205, 3051, 269, 329, 3779, 6371, 2442, 347, 247};
                player.getInventory().add(rewards[RandomUtility.inclusiveRandom(rewards.length - 1)], 1);
                break;
            case 15084:
                Gambling.rollDice(player);
                break;
            case 6307:
                player.setDialogueActionId(79);
                DialogueManager.start(player, 0);
                break;
            case 299:
                if (player.getLocation() == Location.CASINO) {
                    Gambling.plantSeed(player);
                    break;
                } else {
                    player.getPacketSender().sendMessage("@red@You can only plant flowers at the Games Room!");
                    break;
                }
            case 4155:
                DialogueManager.start(player, 61);
                player.setDialogueActionId(28);
                break;
            case 11858:
            case 11860:
            case 11862:
            case 11848:
            case 11856:
            case 11850:
            case 11854:
            case 11852:
            case 11846:
                if (!player.getClickDelay().elapsed(2000) || !player.getInventory().contains(itemId))
                    return;
                if (player.busy()) {
                    player.getPacketSender().sendMessage("You cannot open this right now.");
                    return;
                }

                int[] items = itemId == 11858 ? new int[]{10350, 10348, 10346, 10352} :
                        itemId == 11860 ? new int[]{10334, 10330, 10332, 10336} :
                                itemId == 11862 ? new int[]{10342, 10338, 10340, 10344} :
                                        itemId == 11848 ? new int[]{4716, 4720, 4722, 4718} :
                                                itemId == 11856 ? new int[]{4753, 4757, 4759, 4755} :
                                                        itemId == 11850 ? new int[]{4724, 4728, 4730, 4726} :
                                                                itemId == 11854 ? new int[]{4745, 4749, 4751, 4747} :
                                                                        itemId == 11852 ? new int[]{4732, 4734, 4736, 4738} :
                                                                                itemId == 11846 ? new int[]{4708, 4712, 4714, 4710} :
                                                                                        new int[]{itemId};

                if (player.getInventory().getFreeSlots() < items.length) {
                    player.getPacketSender().sendMessage("You do not have enough space in your inventory.");
                    return;
                }
                player.getInventory().delete(itemId, 1);
                for (int i : items) {
                    player.getInventory().add(i, 1);
                }
                player.getPacketSender().sendMessage("You open the set and find items inside.");
                player.getClickDelay().reset();
                break;
            case 952:
                Digging.dig(player);
                break;
            case 10006:
                // Hunter.getInstance().laySnare(client);
                Hunter.layTrap(player, new SnareTrap(new GameObject(19175, new Position(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ())), TrapState.SET, 200, player));
                break;
            case 10008:
                Hunter.layTrap(player, new BoxTrap(new GameObject(19187, new Position(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ())), TrapState.SET, 200, player));
                break;
            case 292:
                IngridientsBook.readBook(player, 0, false);
                break;
            case 6199:
                if (!player.getInventory().isFull()) {
                    MysteryBox.openBox(player);
                } else {
                    player.getPacketSender().sendMessage("You need to make room for loot!!");
                }
                break;

            case 15501:
                if (!player.getInventory().isFull()) {
                    EliteMysteryBox.openBox(player);
                } else {
                    player.getPacketSender().sendMessage("You need to make room for loot!!");
                }
                break;
            case 601:
                if (!player.getInventory().isFull()) {
                    SmallMysteryBox.openBox(player);
                } else {
                    player.getPacketSender().sendMessage("You need to make room for loot!!");
                }
                break;
            case 602:
                if (!player.getInventory().isFull()) {
                    MediumMysteryBox.openBox(player);
                } else {
                    player.getPacketSender().sendMessage("You need to make room for loot!!");
                }
                break;
            case 603:
                if (!player.getInventory().isFull()) {
                    SupremeMysteryBox.openBox(player);
                } else {
                    player.getPacketSender().sendMessage("You need to make room for loot!!");
                }
                break;
            case 604:
                if (!player.getInventory().isFull()) {
                    CustomMysteryBox.openBox(player);
                } else {
                    player.getPacketSender().sendMessage("You need to make room for loot!!");
                }
                break;
            case 11882:
                player.getInventory().delete(11882, 1);
                player.getInventory().add(2595, 1).refreshItems();
                player.getInventory().add(2591, 1).refreshItems();
                player.getInventory().add(3473, 1).refreshItems();
                player.getInventory().add(2597, 1).refreshItems();
                break;
            case 11884:
                player.getInventory().delete(11884, 1);
                player.getInventory().add(2595, 1).refreshItems();
                player.getInventory().add(2591, 1).refreshItems();
                player.getInventory().add(2593, 1).refreshItems();
                player.getInventory().add(2597, 1).refreshItems();
                break;
            case 11906:
                player.getInventory().delete(11906, 1);
                player.getInventory().add(7394, 1).refreshItems();
                player.getInventory().add(7390, 1).refreshItems();
                player.getInventory().add(7386, 1).refreshItems();
                break;
            case 15262:
                if (!player.getClickDelay().elapsed(1000))
                    return;
                player.getInventory().delete(15262, 1);
                player.getInventory().add(18016, 10000).refreshItems();
                player.getClickDelay().reset();
                break;
            case 6:
                DwarfMultiCannon.setupCannon(player);
                break;

        }
    }

    public static void secondAction(Player player, Packet packet) {
        int interfaceId = packet.readInt();
        int slot = packet.readLEShort();
        int itemId = packet.readInt();
        if (slot < 0 || slot > player.getInventory().capacity())
            return;
        if (player.getInventory().getItems()[slot].getId() != itemId)
            return;
        player.getActionTracker().offer(new ActionItemAction(Action.ActionType.SECOND_ITEM_ACTION, interfaceId, slot, itemId));
        if (SummoningData.isPouch(player, itemId, 2))
            return;

        switch (itemId) {
            case 2795: // bingo card
                player.getBingo().reroll(false, -1, -1);
                break;

            //relics
            case 600:
                player.setPrayerbook(Prayerbook.CURSES);
                player.getPacketSender().sendTabInterface(GameSettings.PRAYER_TAB, player.getPrayerbook().getInterfaceId());
                PrayerHandler.deactivateAll(player);
                CurseHandler.deactivateAll(player);
                break;
            case 730:
                player.setSpellbook(MagicSpellbook.ANCIENT);
                player.getPacketSender().sendTabInterface(GameSettings.MAGIC_TAB, player.getSpellbook().getInterfaceId()).sendMessage("Your magic spellbook is changed..");
                Autocasting.resetAutocast(player, true);
                player.setAutocastSpell(null);
                break;
            case 18339:
                //player.getPacketSender().sendMessage("second click option");
                break;
            case 18338:

                break;
            case 212791:
            case 762:
                RunePouch.emptyPouch(player);
                break;
            case 6500:
                if (player.getCombatBuilder().isAttacking() || player.getCombatBuilder().isBeingAttacked()) {
                    player.getPacketSender().sendMessage("You cannot configure this right now.");
                    return;
                }
                player.getPacketSender().sendInterfaceRemoval();
                DialogueManager.start(player, 101);
                player.setDialogueActionId(60);
                break;
            case 12926:
                player.getBlowpipeLoading().handleUnloadBlowpipe();
                break;
            case 12927:
                player.getBlowpipeLoading().handleUnloadBlowpipe();
                break;


            case 1712:
            case 1710:
            case 1708:
            case 1706:
            case 11118:
            case 11120:
            case 11122:
            case 11124:
                JewelryTeleporting.rub(player, itemId);
                break;
            case 1704:
                player.getPacketSender().sendMessage("Your amulet has run out of charges.");
                break;
            case 11126:
                player.getPacketSender().sendMessage("Your bracelet has run out of charges.");
                break;
            case 13281:
            case 13282:
            case 13283:
            case 13284:
            case 13285:
            case 13286:
            case 13287:
            case 13288:
                player.getSlayer().handleSlayerRingTP(itemId);
                break;

            case 995:
                MoneyPouch.depositMoney(player, player.getInventory().getAmount(995));
                break;
            case 1450:
                Runecrafting.handleTalisman(player, itemId);
                break;
        }
    }

    public void thirdClickAction(Player player, Packet packet) {
        int itemId = packet.readInt();
        int slot = packet.readLEShortA();
        int interfaceId = packet.readInt();
        if (slot < 0 || slot > player.getInventory().capacity())
            return;
        if (player.getInventory().getItems()[slot].getId() != itemId)
            return;
        player.getActionTracker().offer(new ActionItemAction(Action.ActionType.THIRD_ITEM_ACTION, interfaceId, slot, itemId));
        JarData jar = JarData.forJar(itemId);
        if (jar != null) {
            if (player.looterBanking) {
                int quantity = player.getInventory().getAmount(jar.jarId);
                for (int i = 0; i < quantity; i++) {
                    PuroPuro.lootJar(player, new Item(itemId, 1), jar);
                    if (player.getInventory().getAmount(jar.jarId) < 1)
                        return;
                }
            } else {
                PuroPuro.lootJar(player, new Item(itemId, 1), jar);
            }
            return;
        }
        if (SummoningData.isPouch(player, itemId, 3)) {
            return;
        }
        if (ItemBinding.isBindable(itemId)) {
            ItemBinding.bindItem(player, itemId);
            return;
        }

        if (ExperienceLamps.handleAllLamps(player, itemId)) {
            return;
        }

        switch (itemId) {

            case 18829:
                if (player.getRaidsParty() != null) {
                    if (player.getLocation() == Location.SHR)
                        player.moveTo(new Position(3363, 9638, player.instanceHeight));

                    player.getPacketSender().sendMessage("You teleport to the entrance.");
                }
                break;

            //relics
            case 730:
                player.setSpellbook(MagicSpellbook.LUNAR);
                player.getPacketSender().sendTabInterface(GameSettings.MAGIC_TAB, player.getSpellbook().getInterfaceId()).sendMessage("Your magic spellbook is changed..");
                Autocasting.resetAutocast(player, true);
                player.setAutocastSpell(null);
                break;

            case 19868:
                int amount = player.getInventory().getAmount(221880);

                player.setSurgeboxCharges(player.getSurgeboxCharges() + amount);
                player.getInventory().delete(221880, amount);

                break;

            case 219496:
                if (player.Accelerate >= 1 && PerkHandler.canUseNormalPerks(player))
                    Gems.cutGem(player, player.getInventory().getAmount(219496), 219496);
                else
                    player.getPacketSender().sendMessage("You must have the Accelerate perk.");
                break;

            case 1617:
                if ((player.Accelerate >= 1 && PerkHandler.canUseNormalPerks(player)) || (player.wildAccelerate >= 1 && player.getLocation() == Location.WILDERNESS))
                    Gems.cutGem(player, player.getInventory().getAmount(1617), 1617);
                else
                    player.getPacketSender().sendMessage("You must have the Accelerate perk.");
                break;


            case 619:
                Lottery.scratchAll(player);
                break;
		/*case 619:
			Scratchoff.showInterface(player);
			break;*/
            case 1619:
                if ((player.Accelerate >= 1 && PerkHandler.canUseNormalPerks(player)) || (player.wildAccelerate >= 1 && player.getLocation() == Location.WILDERNESS))
                    Gems.cutGem(player, player.getInventory().getAmount(1619), 1619);
                else
                    player.getPacketSender().sendMessage("You must have the Accelerate perk.");
                break;

            case 1621:
                if ((player.Accelerate >= 1 && PerkHandler.canUseNormalPerks(player)) || (player.wildAccelerate >= 1 && player.getLocation() == Location.WILDERNESS))
                    Gems.cutGem(player, player.getInventory().getAmount(1621), 1621);
                else
                    player.getPacketSender().sendMessage("You must have the Accelerate perk.");
                break;

            case 1623:
                if ((player.Accelerate >= 1 && PerkHandler.canUseNormalPerks(player)) || (player.wildAccelerate >= 1 && player.getLocation() == Location.WILDERNESS))
                    Gems.cutGem(player, player.getInventory().getAmount(1623), 1623);
                else if (player.wildAccelerate >= 1 && PerkHandler.canUseWildernessPerks(player))
                    Gems.cutGem(player, player.getInventory().getAmount(1623), 1623);
                else
                    player.getPacketSender().sendMessage("You must have the Accelerate perk.");
                break;

            case 1625:
                if ((player.Accelerate >= 1 && PerkHandler.canUseNormalPerks(player)) || (player.wildAccelerate >= 1 && player.getLocation() == Location.WILDERNESS))
                    Gems.cutGem(player, player.getInventory().getAmount(1625), 1625);
                else if (player.wildAccelerate >= 1 && PerkHandler.canUseWildernessPerks(player))
                    Gems.cutGem(player, player.getInventory().getAmount(1625), 1625);

                else
                    player.getPacketSender().sendMessage("You must have the Accelerate perk.");
                break;

            case 1627:
                if ((player.Accelerate >= 1 && PerkHandler.canUseNormalPerks(player)) || (player.wildAccelerate >= 1 && player.getLocation() == Location.WILDERNESS))
                    Gems.cutGem(player, player.getInventory().getAmount(1627), 1627);
                else if (player.wildAccelerate >= 1 && PerkHandler.canUseWildernessPerks(player))
                    Gems.cutGem(player, player.getInventory().getAmount(1627), 1627);

                else
                    player.getPacketSender().sendMessage("You must have the Accelerate perk.");
                break;

            case 1629:
                if ((player.Accelerate >= 1 && PerkHandler.canUseNormalPerks(player)) || (player.wildAccelerate >= 1 && player.getLocation() == Location.WILDERNESS))
                    Gems.cutGem(player, player.getInventory().getAmount(1629), 1629);
                else if (player.wildAccelerate >= 1 && PerkHandler.canUseWildernessPerks(player))
                    Gems.cutGem(player, player.getInventory().getAmount(1629), 1629);

                else
                    player.getPacketSender().sendMessage("You must have the Accelerate perk.");
                break;

            case 1631:
                if ((player.Accelerate >= 1 && PerkHandler.canUseNormalPerks(player)) || (player.wildAccelerate >= 1 && player.getLocation() == Location.WILDERNESS))
                    Gems.cutGem(player, player.getInventory().getAmount(1631), 1631);
                else if (player.wildAccelerate >= 1 && PerkHandler.canUseWildernessPerks(player))
                    Gems.cutGem(player, player.getInventory().getAmount(1631), 1631);

                else
                    player.getPacketSender().sendMessage("You must have the Accelerate perk.");
                break;


            case 17415: //Iron block
                quantity = player.getInventory().getAmount(17415);

                for (int i = 0; i < quantity; i++) {
                    player.getInventory().delete(17415, 1);
                    if (player.getInventory().contains(213243) || player.getEquipment().contains(213243) ||
                            player.getInventory().contains(13661) || player.getEquipment().contains(13661))
                        player.getInventory().add(2352, RandomUtility.inclusiveRandom(2) + 3);
                    else
                        player.getInventory().add(440, RandomUtility.inclusiveRandom(2) + 3);
                }
                break;
            case 17416: //Coal block
                quantity = player.getInventory().getAmount(17416);

                for (int i = 0; i < quantity; i++) {
                    player.getInventory().delete(17416, 1);
                    if (player.getInventory().contains(213243) || player.getEquipment().contains(213243) ||
                            player.getInventory().contains(13661) || player.getEquipment().contains(13661))
                        player.getInventory().add(2354, RandomUtility.inclusiveRandom(2) + 3);
                    else
                        player.getInventory().add(453, RandomUtility.inclusiveRandom(2) + 3);
                }
                break;
            case 17422: //Gold block
                quantity = player.getInventory().getAmount(17422);

                for (int i = 0; i < quantity; i++) {
                    player.getInventory().delete(17422, 1);
                    if (player.getInventory().contains(213243) || player.getEquipment().contains(213243) ||
                            player.getInventory().contains(13661) || player.getEquipment().contains(13661))
                        player.getInventory().add(2358, RandomUtility.inclusiveRandom(2) + 3);
                    else
                        player.getInventory().add(444, RandomUtility.inclusiveRandom(2) + 3);
                }
                break;
            case 17418: //Mithril block
                quantity = player.getInventory().getAmount(17418);

                for (int i = 0; i < quantity; i++) {
                    player.getInventory().delete(17418, 1);
                    if (player.getInventory().contains(213243) || player.getEquipment().contains(213243) ||
                            player.getInventory().contains(13661) || player.getEquipment().contains(13661))
                        player.getInventory().add(2360, RandomUtility.inclusiveRandom(2) + 3);
                    else
                        player.getInventory().add(447, RandomUtility.inclusiveRandom(2) + 3);
                }
                break;
            case 17424: //Adamantite block
                quantity = player.getInventory().getAmount(17424);

                for (int i = 0; i < quantity; i++) {
                    player.getInventory().delete(17424, 1);
                    if (player.getInventory().contains(213243) || player.getEquipment().contains(213243) ||
                            player.getInventory().contains(13661) || player.getEquipment().contains(13661))
                        player.getInventory().add(2362, RandomUtility.inclusiveRandom(2) + 3);
                    else
                        player.getInventory().add(449, RandomUtility.inclusiveRandom(2) + 3);
                }
                break;
            case 17420: //Runite block
                quantity = player.getInventory().getAmount(17420);

                for (int i = 0; i < quantity; i++) {
                    player.getInventory().delete(17420, 1);
                    if (player.getInventory().contains(213243) || player.getEquipment().contains(213243) ||
                            player.getInventory().contains(13661) || player.getEquipment().contains(13661))
                        player.getInventory().add(2364, RandomUtility.inclusiveRandom(2) + 3);
                    else
                        player.getInventory().add(451, RandomUtility.inclusiveRandom(2) + 3);
                }
                break;
            case 17426: //Amethyst block
                quantity = player.getInventory().getAmount(17426);

                for (int i = 0; i < quantity; i++) {
                    player.getInventory().delete(17426, 1);
                    player.getInventory().add(221347, RandomUtility.inclusiveRandom(2) + 3);
                }
                player.getSkillManager().stopSkilling();
                break;
            case 4560:

                quantity = player.getInventory().getAmount(4560);

                for (int i = 0; i < quantity; i++) {

                    player.setBonusTime(player.getBonusTime() + 10);
                    //player.getPacketSender().sendMessage("You've added 10 minutes to your bonus timer!");

                    int randomHoliday = RandomUtility.inclusiveRandom(5000);


                    if (randomHoliday == 1) {
                        player.getInventory().add(1959, 1);
                        World.sendMessage("drops", "@bla@[Halloween] " + player.getUsername() + " just received a @or2@Pumpkin@bla@ from a Skeletal Candy!");
                    } else if (randomHoliday > 1 && randomHoliday < 51) {
                        player.getInventory().add(9921, 1);
                        World.sendMessage("drops", "@or2@[Halloween] " + player.getUsername() + " just received @bla@Skeleton Boots@or2@ from a Skeletal Candy!");
                    } else if (randomHoliday > 51 && randomHoliday < 101) {
                        player.getInventory().add(9922, 1);
                        World.sendMessage("drops", "@or2@[Halloween] " + player.getUsername() + " just received @bla@Skeleton Gloves@or2@ from a Skeletal Candy!");
                    } else if (randomHoliday > 101 && randomHoliday < 151) {
                        player.getInventory().add(9923, 1);
                        World.sendMessage("drops", "@or2@[Halloween] " + player.getUsername() + " just received @bla@Skeleton Leggings@or2@ from a Skeletal Candy!");
                    } else if (randomHoliday > 151 && randomHoliday < 201) {
                        player.getInventory().add(9924, 1);
                        World.sendMessage("drops", "@or2@[Halloween] " + player.getUsername() + " just received @bla@Skeleton Shirt@or2@ from a Skeletal Candy!");
                    } else if (randomHoliday > 201 && randomHoliday < 251) {
                        player.getInventory().add(9925, 1);
                        World.sendMessage("drops", "@or2@[Halloween] " + player.getUsername() + " just received @bla@Skeleton Mask@or2@ from a Skeletal Candy!");
                    }


                    player.getInventory().delete(4560, 1);
                }
                break;


            case 19864:
                if (player.getInventory().contains(19864)) {

                    quantity = player.getInventory().getAmount(19864);

                    player.getPointsHandler().setAmountDonated(player.getPointsHandler().getAmountDonated(), quantity);
                    player.getInventory().delete(19864, quantity);

                    player.getPacketSender().sendMessage("You now have $" + player.getPointsHandler().getAmountDonated() + " donated on this account.");
                    DonationBonds.checkForRankUpdate(player);
                }
                break;
            case 4562:

                quantity = player.getInventory().getAmount(4562);

                for (int i = 0; i < quantity; i++) {

                    int timeToAdd = 1000;

                    if (player.Lucky >= 3)
                        timeToAdd += 500;
                    else if (player.Lucky >= 1)
                        timeToAdd += 250;

                    if (player.getBonusTime() + timeToAdd >= Integer.MAX_VALUE) {
                        player.setBonusTime(Integer.MAX_VALUE);
                    } else {
                        player.setBonusTime(player.getBonusTime() + timeToAdd);
                    }
                    //player.getPacketSender().sendMessage("You've added 10 minutes to your bonus timer!");

                    int randomHoliday = RandomUtility.inclusiveRandom(5000);


                    if (randomHoliday == 1) {
                        player.getInventory().add(962, 1);

                        if (player.getGameMode() != GameMode.SEASONAL_IRONMAN)
                            World.sendMessage("drops", "@gre@[CANDY] " + player.getUsername() + " just received a @red@Christmas Cracker @gre@from a Rare Candy!");
                    } else if (randomHoliday == 2 || randomHoliday == 3) {
                        player.getInventory().add(1959, 1);

                        if (player.getGameMode() != GameMode.SEASONAL_IRONMAN)
                            World.sendMessage("drops", "@gre@[CANDY] " + player.getUsername() + " just received a @or2@Pumpkin@gre@ from a Rare Candy!");
                    } else if (randomHoliday == 4 || randomHoliday == 5) {
                        player.getInventory().add(1961, 1);

                        if (player.getGameMode() != GameMode.SEASONAL_IRONMAN)
                            World.sendMessage("drops", "@gre@[CANDY] " + player.getUsername() + " just received an @blu@Easter Egg@gre@ from a Rare Candy!");
                    } else if (randomHoliday == 19 || randomHoliday == 20) {
                        player.getInventory().add(1050, 1);

                        if (player.getGameMode() != GameMode.SEASONAL_IRONMAN)
                            World.sendMessage("drops", "@gre@[CANDY] " + player.getUsername() + " just received a @red@Santa Hat@gre@ from a Rare Candy!");
                    }


                    player.getInventory().delete(4562, 1);
                    player.setRareCandy(player.getRareCandy() + 1);
                }
                break;
            case 3841:

                if (player.getInventory().contains(3831) && player.getInventory().contains(3832) &&
                        player.getInventory().contains(3833) && player.getInventory().contains(3834) &&
                        player.getZamorakPages() != 1) {
                    player.getInventory().delete(3831, 1);
                    player.getInventory().delete(3832, 1);
                    player.getInventory().delete(3833, 1);
                    player.getInventory().delete(3834, 1);
                    player.getInventory().delete(3841, 1);
                    player.setZamorakPages(1);
                    player.getInventory().add(3842, 1);
                    player.getPacketSender().sendMessage("You've placed all of the missing pages inside the damaged book!");
                } else if (player.getZamorakPages() == 1) {
                    player.getInventory().delete(3841, 1);
                    player.getInventory().add(3842, 1);
                    player.getPacketSender().sendMessage("You've converted the damaged book!");
                } else {
                    player.getPacketSender().sendMessage("You do not have all of the missing pages to do this.");
                }

                break;

            case 3839:

                if (player.getInventory().contains(3827) && player.getInventory().contains(3828) &&
                        player.getInventory().contains(3829) && player.getInventory().contains(3830) &&
                        player.getSaradominPages() != 1) {
                    player.getInventory().delete(3827, 1);
                    player.getInventory().delete(3828, 1);
                    player.getInventory().delete(3829, 1);
                    player.getInventory().delete(3830, 1);
                    player.getInventory().delete(3839, 1);
                    player.setSaradominPages(1);
                    player.getInventory().add(3840, 1);
                    player.getPacketSender().sendMessage("You've placed all of the missing pages inside the damaged book!");
                } else if (player.getSaradominPages() == 1) {
                    player.getInventory().delete(3839, 1);
                    player.getInventory().add(3840, 1);
                    player.getPacketSender().sendMessage("You've converted the damaged book!");
                } else {
                    player.getPacketSender().sendMessage("You do not have all of the missing pages to do this.");
                }

                break;

            case 3843:

                if (player.getInventory().contains(3835) && player.getInventory().contains(3836) &&
                        player.getInventory().contains(3837) && player.getInventory().contains(3838) &&
                        player.getGuthixPages() != 1) {
                    player.getInventory().delete(3835, 1);
                    player.getInventory().delete(3836, 1);
                    player.getInventory().delete(3837, 1);
                    player.getInventory().delete(3838, 1);
                    player.getInventory().delete(3843, 1);
                    player.setGuthixPages(1);
                    player.getInventory().add(3844, 1);
                    player.getPacketSender().sendMessage("You've placed all of the missing pages inside the damaged book!");
                } else if (player.getGuthixPages() == 1) {
                    player.getInventory().delete(3843, 1);
                    player.getInventory().add(3844, 1);
                    player.getPacketSender().sendMessage("You've converted the damaged book!");
                } else {
                    player.getPacketSender().sendMessage("You do not have all of the missing pages to do this.");
                }

                break;

            case 19612:

                if (player.getInventory().contains(19600) && player.getInventory().contains(19601) &&
                        player.getInventory().contains(19602) && player.getInventory().contains(19603) &&
                        player.getBandosPages() != 1) {
                    player.getInventory().delete(19600, 1);
                    player.getInventory().delete(19601, 1);
                    player.getInventory().delete(19602, 1);
                    player.getInventory().delete(19603, 1);
                    player.getInventory().delete(19612, 1);
                    player.setBandosPages(1);
                    player.getInventory().add(19613, 1);
                    player.getPacketSender().sendMessage("You've placed all of the missing pages inside the damaged book!");
                } else if (player.getBandosPages() == 1) {
                    player.getInventory().delete(19612, 1);
                    player.getInventory().add(19613, 1);
                    player.getPacketSender().sendMessage("You've converted the damaged book!");
                } else {
                    player.getPacketSender().sendMessage("You do not have all of the missing pages to do this.");
                }

                break;

            case 19614:

                if (player.getInventory().contains(19604) && player.getInventory().contains(19605) &&
                        player.getInventory().contains(19606) && player.getInventory().contains(19607) &&
                        player.getArmadylPages() != 1) {
                    player.getInventory().delete(19604, 1);
                    player.getInventory().delete(19605, 1);
                    player.getInventory().delete(19606, 1);
                    player.getInventory().delete(19607, 1);
                    player.getInventory().delete(19614, 1);
                    player.setArmadylPages(1);
                    player.getInventory().add(19615, 1);
                    player.getPacketSender().sendMessage("You've placed all of the missing pages inside the damaged book!");
                } else if (player.getArmadylPages() == 1) {
                    player.getInventory().delete(19614, 1);
                    player.getInventory().add(19615, 1);
                    player.getPacketSender().sendMessage("You've converted the damaged book!");
                } else {
                    player.getPacketSender().sendMessage("You do not have all of the missing pages to do this.");
                }

                break;

            case 19616:

                if (player.getInventory().contains(19608) && player.getInventory().contains(19609) &&
                        player.getInventory().contains(19610) && player.getInventory().contains(19611) &&
                        player.getAncientPages() != 1) {
                    player.getInventory().delete(19608, 1);
                    player.getInventory().delete(19609, 1);
                    player.getInventory().delete(19610, 1);
                    player.getInventory().delete(19611, 1);
                    player.getInventory().delete(19616, 1);
                    player.setAncientPages(1);
                    player.getInventory().add(19617, 1);
                    player.getPacketSender().sendMessage("You've placed all of the missing pages inside the damaged book!");
                } else if (player.getAncientPages() == 1) {
                    player.getInventory().delete(19616, 1);
                    player.getInventory().add(19617, 1);
                    player.getPacketSender().sendMessage("You've converted the damaged book!");
                } else {
                    player.getPacketSender().sendMessage("You do not have all of the missing pages to do this.");
                }

                break;
            case 18339:
                player.getPacketSender().sendMessage("You have " + player.getCoalBag() + " pay-dirt in your Dirt bag.");
                break;
            case 18338:

                if (player.looterBanking && player.getGameMode() != GameMode.ULTIMATE_IRONMAN) {
                    int qtySapphire = player.getBagSapphire();
                    int qtyEmerald = player.getBagEmerald();
                    int qtyRuby = player.getBagRuby();
                    int qtyDiamond = player.getBagDiamond();
                    int qtyDragonstone = player.getBagDragonstone();

                    if (qtySapphire > 0)
                        player.getBank(Bank.getTabForItem(player, 1623)).add(1623, qtySapphire);
                    player.getInventory().depositItem(player.getBank(Bank.getTabForItem(player, 1623)), new Item(1623, qtySapphire));
                    if (qtyEmerald > 0)
                        player.getInventory().depositItem(player.getBank(Bank.getTabForItem(player, 1621)), new Item(1621, qtyEmerald));
                    if (qtyRuby > 0)
                        player.getInventory().depositItem(player.getBank(Bank.getTabForItem(player, 1619)), new Item(1619, qtyRuby));
                    if (qtyDiamond > 0)
                        player.getInventory().depositItem(player.getBank(Bank.getTabForItem(player, 1617)), new Item(1617, qtyDiamond));
                    if (qtyDragonstone > 0)
                        player.getInventory().depositItem(player.getBank(Bank.getTabForItem(player, 1631)), new Item(1631, qtyDiamond));

                    player.getPacketSender().sendMessage("You withdraw some gems directly to your bank!");
                    player.setBagSapphire(0);
                    player.setBagEmerald(0);
                    player.setBagRuby(0);
                    player.setBagDiamond(0);
                    player.setBagDragonstone(0);

                    return;
                }

                player.getPacketSender().sendMessage("You withdraw some gems!");

                int withdrawSapphire = player.getBagSapphire();
                if (player.getInventory().getFreeSlots() > 0) {
                    player.getInventory().add(1624, withdrawSapphire);
                    player.setBagSapphire(0);
                } else {
                    player.getPacketSender().sendMessage("Your inventory is too full to withdraw anymore.");
                    return;
                }
                int withdrawEmerald = player.getBagEmerald();
                if (player.getInventory().getFreeSlots() > 0) {
                    player.getInventory().add(1622, withdrawEmerald);
                    player.setBagEmerald(0);
                } else {
                    player.getPacketSender().sendMessage("Your inventory is too full to withdraw anymore.");
                    return;
                }
                int withdrawRuby = player.getBagRuby();
                if (player.getInventory().getFreeSlots() > 0) {
                    player.getInventory().add(1620, withdrawRuby);
                    player.setBagRuby(0);
                } else {
                    player.getPacketSender().sendMessage("Your inventory is too full to withdraw anymore.");
                    return;
                }
                int withdrawDiamond = player.getBagDiamond();
                if (player.getInventory().getFreeSlots() > 0) {
                    player.getInventory().add(1618, withdrawDiamond);
                    player.setBagDiamond(0);
                } else {
                    player.getPacketSender().sendMessage("Your inventory is too full to withdraw anymore.");
                    return;
                }
                int withdrawDragonstone = player.getBagDragonstone();
                if (player.getInventory().getFreeSlots() > 0) {
                    player.getInventory().add(1632, withdrawDragonstone);
                    player.setBagDragonstone(0);
                } else {
                    player.getPacketSender().sendMessage("Your inventory is too full to withdraw anymore.");
                    return;
                }
                break;
            case 612: //Last recall
                player.setLastRecallx(0);
                player.setLastRecally(0);
                player.setLastRecallz(0);
                player.getInventory().delete(612, 1);
                player.getInventory().add(611, 1);
                break;
            case 14019:
                player.getPacketSender().sendInterface(60000);
                break;
            case 762:
            case 212791:
                RunePouch.checkPouch(player);
                break;
            case 12926:
                player.getBlowpipeLoading().handleCheckBlowpipe();
                break;
            case 12927:
                player.getBlowpipeLoading().handleCheckBlowpipe();
                break;
            case 220368:
                if (player.getInventory().getFreeSlots() >= 1) {
                    player.getInventory().delete(220368, 1);
                    player.getInventory().add(11694, 1);
                    player.getInventory().add(220068, 1);
                } else
                    player.getPacketSender().sendMessage("You must have 2 inventory spaces to do this.");

                break;
            case 220370:
                if (player.getInventory().getFreeSlots() >= 1) {
                    player.getInventory().delete(220370, 1);
                    player.getInventory().add(11696, 1);
                    player.getInventory().add(220071, 1);
                } else
                    player.getPacketSender().sendMessage("You must have 2 inventory spaces to do this.");

                break;
            case 220372:
                if (player.getInventory().getFreeSlots() >= 1) {
                    player.getInventory().delete(220372, 1);
                    player.getInventory().add(11698, 1);
                    player.getInventory().add(220074, 1);
                } else
                    player.getPacketSender().sendMessage("You must have 2 inventory spaces to do this.");

                break;
            case 220374:
                if (player.getInventory().getFreeSlots() >= 1) {
                    player.getInventory().delete(220374, 1);
                    player.getInventory().add(11700, 1);
                    player.getInventory().add(220077, 1);
                } else
                    player.getPacketSender().sendMessage("You must have 2 inventory spaces to do this.");

                break;
            case 21006:
                player.getPacketSender().sendMessage("Your Scythe of Vitur has " + player.getScytheCharges() + " charges.");
                break;
            case 19670:
                if (player.busy()) {
                    player.getPacketSender().sendMessage("You can not do this right now.");
                    return;
                }

                int votes = player.getInventory().getAmount(19670);

                for (int i = 0; i < votes; i++) {
                    player.setVotePoints(player.getVotePoints() + 1);
                    player.setPaePoints(player.getPaePoints() + 10);
                    player.getInventory().delete(19670, 1);
                    player.getInventory().add(995, 250000);
                    PlayerPanel.refreshPanel(player);
                    player.sendMessage("You've received 1 Vote Point, 10 PaePoints and 250,000 coins!");
                }
                break;
            case 6500:
                CharmingImp.sendConfig(player);
                break;
            case 4155:
                player.getPacketSender().sendInterfaceRemoval();
                DialogueManager.start(player, 103);
                player.setDialogueActionId(65);
                break;
            case 13281:
            case 13282:
            case 13283:
            case 13284:
            case 13285:
            case 13286:
            case 13287:
            case 13288:
                player.getPacketSender().sendInterfaceRemoval();
                player.getPacketSender().sendMessage(player.getSlayer().getSlayerTask() == SlayerTasks.NO_TASK ? ("You do not have a Slayer task.") : ("Your current task is to kill another " + (player.getSlayer().getAmountToSlay()) + " " + Misc.formatText(player.getSlayer().getSlayerTask().toString().toLowerCase().replaceAll("_", " ")) + "s."));
                break;
            case 15262:
                if (!player.getClickDelay().elapsed(1300))
                    return;
                int amt = player.getInventory().getAmount(15262);
                if (amt > 0)
                    player.getInventory().delete(15262, amt).add(18016, 10000 * amt);
                player.getClickDelay().reset();
                break;

            case 11283: //DFS
                player.getPacketSender().sendMessage("Your Dragonfire shield has " + player.getDfsCharges() + "/20 dragon-fire charges.");
                break;
            case 11613: //dkite
                player.getPacketSender().sendMessage("Your Dragonfire shield has " + player.getDfsCharges() + "/20 dragon-fire charges.");
                break;
        }

    }

    @Override
    public void handleMessage(Player player, Packet packet) {
        if (player.getConstitution() <= 0)
            return;
        switch (packet.getOpcode()) {
            case SECOND_ITEM_ACTION_OPCODE:
                secondAction(player, packet);
                break;
            case FIRST_ITEM_ACTION_OPCODE:
                firstAction(player, packet);
                break;
            case THIRD_ITEM_ACTION_OPCODE:
                thirdClickAction(player, packet);
                break;
        }
    }

    public static final int SECOND_ITEM_ACTION_OPCODE = 75;

    public static final int FIRST_ITEM_ACTION_OPCODE = 122;

    public static final int THIRD_ITEM_ACTION_OPCODE = 16;

}