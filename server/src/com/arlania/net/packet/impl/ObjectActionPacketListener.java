package com.arlania.net.packet.impl;

import com.arlania.GameSettings;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.engine.task.impl.WalkToTask;
import com.arlania.engine.task.impl.WalkToTask.FinalizedMovementTask;
import com.arlania.model.*;
import com.arlania.model.Locations.Location;
import com.arlania.model.container.impl.Equipment;
import com.arlania.model.container.impl.Shop.ShopManager;
import com.arlania.model.definitions.GameObjectDefinition;
import com.arlania.model.input.impl.DonateToWellGoodwill;
import com.arlania.model.input.impl.EnterAmountOfLogsToAdd;
import com.arlania.model.input.impl.EnterFairyCode;
import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketListener;
import com.arlania.util.RandomUtility;
import com.arlania.world.clip.region.RegionClipping;
import com.arlania.world.content.*;
import com.arlania.world.content.combat.magic.Autocasting;
import com.arlania.world.content.combat.prayer.CurseHandler;
import com.arlania.world.content.combat.prayer.PrayerHandler;
import com.arlania.world.content.combat.range.DwarfMultiCannon;
import com.arlania.world.content.combat.weapon.CombatSpecial;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.content.globalevents.GlobalEventHandler;
import com.arlania.world.content.globalevents.GlobalEvent;
import com.arlania.world.content.interfaces.EquipmentUpgrades1;
import com.arlania.world.content.interfaces.EquipmentUpgrades2;
import com.arlania.world.content.interfaces.HeroesStatue;
import com.arlania.world.content.marketplace.DeathsCoffer;
import com.arlania.world.content.marketplace.Marketplace;
import com.arlania.world.content.minigames.impl.*;
import com.arlania.world.content.minigames.impl.barrows.Minigame;
import com.arlania.world.content.minigames.impl.barrows.Raid;
import com.arlania.world.content.minigames.impl.chambersofxeric.CoxRaid;
import com.arlania.world.content.minigames.impl.chaosraids.ChaosRaid;
import com.arlania.world.content.minigames.impl.gwdraids.GwdRaid;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.content.minigames.impl.skilling.BlastFurnace;
import com.arlania.world.content.minigames.impl.skilling.BlastMine;
import com.arlania.world.content.minigames.impl.skilling.Wintertodt;
import com.arlania.world.content.minigames.impl.strongholdraids.Objects;
import com.arlania.world.content.minigames.impl.theatreofblood.TobRaid;
import com.arlania.world.content.skill.impl.agility.Agility;
import com.arlania.world.content.skill.impl.construction.Construction;
import com.arlania.world.content.skill.impl.crafting.Flax;
import com.arlania.world.content.skill.impl.dungeoneering.Dungeoneering;
import com.arlania.world.content.skill.impl.fishing.Fishing;
import com.arlania.world.content.skill.impl.fishing.Fishing.Spot;
import com.arlania.world.content.skill.impl.hunter.Hunter;
import com.arlania.world.content.skill.impl.hunter.PuroPuro;
import com.arlania.world.content.skill.impl.mining.Mining;
import com.arlania.world.content.skill.impl.mining.MiningData;
import com.arlania.world.content.skill.impl.mining.Prospecting;
import com.arlania.world.content.skill.impl.runecrafting.Runecrafting;
import com.arlania.world.content.skill.impl.runecrafting.RunecraftingData;
import com.arlania.world.content.skill.impl.smithing.EquipmentMaking;
import com.arlania.world.content.skill.impl.smithing.Smelting;
import com.arlania.world.content.skill.impl.thieving.Stalls;
import com.arlania.world.content.skill.impl.woodcutting.Woodcutting;
import com.arlania.world.content.skill.impl.woodcutting.WoodcuttingData;
import com.arlania.world.content.skill.impl.woodcutting.WoodcuttingData.Hatchet;
import com.arlania.world.content.transportation.FairyRings;
import com.arlania.world.content.transportation.StaircaseData;
import com.arlania.world.content.transportation.TeleportHandler;
import com.arlania.world.content.transportation.TeleportType;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.antibotting.actions.Action;
import com.arlania.world.entity.impl.player.antibotting.actions.ActionObjectAction;

/**
 * This packet listener is called when a player clicked
 * on a game object.
 *
 * @author relex lawl
 */

public class ObjectActionPacketListener implements PacketListener {

    public static final int FIRST_CLICK = 132, SECOND_CLICK = 252, THIRD_CLICK = 70, FOURTH_CLICK = 234, FIFTH_CLICK = 228;

    /**
     * The PacketListener logger to debug information and print out errors.
     */
    //private final static Logger logger = Logger.getLogger(ObjectActionPacketListener.class);
    private static void firstClick(final Player player, Packet packet) {
        final int x = packet.readLEShortA();
        final int id = packet.readInt();

        final int y = packet.readUnsignedShortA();
        final Position position = new Position(x, y, player.getPosition().getZ());
        final GameObject gameObject = new GameObject(id, position);

        if (player.getLocation() != Location.CONSTRUCTION) {
            if (id > 0 && id != 6 && !RegionClipping.objectExists(gameObject) && id != 16510) {
                player.getPacketSender().sendMessage("An error occured. Error code: " + id).sendMessage("Please report the error to a staff member.");
                return;
            }
        }

		/*if(gameObject.getId() == 9086)
		{
			gameObject.setType(10);
			gameObject.setSize(1);
		}*/
        int distanceX = (player.getPosition().getX() - position.getX());
        int distanceY = (player.getPosition().getY() - position.getY());
        if (distanceX < 0)
            distanceX = -(distanceX);
        if (distanceY < 0)
            distanceY = -(distanceY);
        int size = distanceX > distanceY ? GameObjectDefinition.forId(id).getSizeX() : GameObjectDefinition.forId(id).getSizeY();
        if (size <= 0)
            size = 1;
        gameObject.setSize(size);
        if (player.getMovementQueue().isLockMovement())
            return;
        if (player.getStaffRights() == StaffRights.DEVELOPER) {
            player.getPacketSender().sendMessage("First click object id; [id, position] : [" + id + ", " + position + "]");
            player.getPacketSender().sendMessage("[Face] : " + gameObject.getFace());
        }

        player.getActionTracker().offer(new ActionObjectAction(Action.ActionType.FIRST_OBJECT_ACTION, x, y, id));

        gameObject.clickObject(player, GameObjectClickType.FIRST_CLICK);

        player.setInteractingObject(gameObject).setWalkToTask(new WalkToTask(player, position, gameObject.getSize(), new FinalizedMovementTask() {
            @Override
            public void execute() {
                player.setPositionToFace(gameObject.getPosition());
                /*if (player.getRegionInstance() != null) {
                    Construction.handleFifthObjectClick(x, y, id, player);
                }*/
                if (WoodcuttingData.Trees.forId(id) != null) {
                    Woodcutting.cutWood(player, gameObject, false);
                    return;
                }
                if (MiningData.forRock(gameObject.getId()) != null) {
                    Mining.startMining(player, gameObject);
                    return;
                }
                //if (player.getFarming().click(player, x, y, 1))
                //return;
                if (Runecrafting.runecraftingAltar(player, gameObject.getId())) {
                    RunecraftingData.RuneData rune = RunecraftingData.RuneData.forId(gameObject.getId());
                    if (rune == null)
                        return;
                    Runecrafting.craftRunes(player, rune);
                    return;
                }
                if (Agility.handleObject(player, gameObject)) {
                    return;
                }
                if (Minigame.handleObject(player, gameObject)) {
                    return;
                }
                if (player.getLocation() == Location.WILDERNESS && WildernessObelisks.handleObelisk(gameObject.getId())) {
                    return;
                }
                if (StaircaseData.forPosition(gameObject.getPosition(), true, false) != null) {
                    StaircaseData.climb(player, StaircaseData.forPosition(gameObject.getPosition(), true, false).getEndPosition());
                }
                if (ItemSpawnData.forPosition(gameObject.getPosition()) != null) {
                    ItemSpawnData.gather(player, ItemSpawnData.forPosition(gameObject.getPosition()).getItemId());
                }


                int objId = id;

                if (objId > 300000)
                    objId -= 300000;

                if (gameObject.getPosition().getX() == 3748 && gameObject.getPosition().getY() == 5672) {
                    MotherLodeMine.depositPayDirt(player);
                    return;
                }

                if (x == 3748 && y == 5672) {
                    MotherLodeMine.depositPayDirt(player);
                    return;
                }

                if (player.getLocation() == Location.SHR) {
                    Objects.objectHandler(player);
                    return;
                }

                switch (objId) {

                    case 23549:
                    case 23550:
                        player.getPacketSender().sendMessage("You crush the spikes!");
                        CustomObjects.deleteGlobalObject(gameObject);
                        RegionClipping.removeObject(gameObject);
                        player.getPacketSender().sendObject(new GameObject(-1, gameObject.getPosition()));
                        break;

                    case 20001:
                        player.getPacketSender().sendMessage("You rend the flames!");
                        CustomObjects.deleteGlobalObject(gameObject);
                        RegionClipping.removeObject(gameObject);
                        player.getPacketSender().sendObject(new GameObject(-1, gameObject.getPosition()));
                        break;

                    case 11761:
                        FairyRings.forFairyCode(player,"bks");
                        break;

                    case 33355:
                        player.getTeleportInterface().open();
                        break;
                    case 10061:
                        Marketplace.open(player);
                        break;
                    case 30720:
                        if (player.strongholdLootFloor == 3 && !player.getRaidsParty().shrFloorFourBossSpawned)
                            player.getPacketSender().sendMessage("Talk to Thok to see what key you need next! Then use it here.");
                        else if (player.strongholdLootFloor == 0 && !player.getRaidsParty().shrFloorOneBossSpawned)
                            player.getPacketSender().sendMessage("Talk to Thok to see what key you need next! Then use it here.");
                        else if (player.strongholdLootFloor == 0 && !player.getRaidsParty().shrFloorOneBossSpawned)
                            player.getPacketSender().sendMessage("Talk to Thok to see what key you need next! Then use it here.");
                        else if (player.strongholdLootFloor == 0 && !player.getRaidsParty().shrFloorOneBossSpawned)
                            player.getPacketSender().sendMessage("Talk to Thok to see what key you need next! Then use it here.");
                        break;

                    //Stronghold Raid Enter

                    case 14830:
                        if (player.getLocation() != Location.WILDERNESS) {
                            DialogueManager.start(player, 1024);
                            player.setDialogueActionId(1026);
                        }

                        break;
                    case 23271: //wildy ditch
                        if (y > player.getPosition().getY()) {
                            DialogueManager.start(player, 1027);
                            player.setDialogueActionId(1029);
                        }
                        //player.moveTo(new Position(x, y + 2));

                        else if (y < player.getPosition().getY())
                            player.moveTo(new Position(x, y - 2));

                        break;
                    case 20790:
                        /*if (player.getGameMode() == GameMode.SEASONAL_IRONMAN) {
                            if (player.seasonalRaidsTeleports[2] == 0) {
                                player.getPacketSender().sendMessage("You must unlock the Stronghold Raid teleport to unlock this.");
                                return;
                            }
                        }*/

                        if (player.getRaidsParty() != null && player.getRaidsParty().getOwner() == player) {
                            RaidsParty raidsParty = player.getRaidsParty();
                            com.arlania.world.content.minigames.impl.strongholdraids.Raid.start(raidsParty);
                        } else {
                            player.getPacketSender().sendMessage("@red@You must be the party owner to start the raid.");
                        }
                        break;

                    //Kalphite entrance
                    case 3828:
                        /*if (player.getGameMode() != GameMode.SEASONAL_IRONMAN || player.seasonalBossTeleports[6] == 1) {
                            KalphiteQueenInstance.enter(player);
                            player.getPacketSender().sendMessage("You enter the Kalphite Lair.");
                        }*/
                        KalphiteQueenInstance.enter(player);
                        player.getPacketSender().sendMessage("You enter the Kalphite Lair.");
                        break;
                    case 30180:
                        /*if (gameObject.getPosition().getX() == 3319 && gameObject.getPosition().getY() == 3121) {
                            if (player.getGameMode() != GameMode.SEASONAL_IRONMAN || player.seasonalBossTeleports[6] == 1) {
                                KalphiteQueenInstance.enter(player);
                                player.getPacketSender().sendMessage("You enter the Kalphite Lair.");
                            }
                        }*/
                        if (gameObject.getPosition().getX() == 3319 && gameObject.getPosition().getY() == 3121) {
                            KalphiteQueenInstance.enter(player);
                            player.getPacketSender().sendMessage("You enter the Kalphite Lair.");
                        }
                        break;
                    case 3829:
                        player.moveTo(new Position(3228, 3107, 0), true);
                        player.getPacketSender().sendMessage("You've climbed out of the Kalphite Stronghold!");
                        break;

                    case 12897:
                        int eventTime = GameSettings.wellOfEventsCooldown / 100;
                        int timeLeft = 300 - eventTime;

                        player.getPacketSender().sendMessage("The Well of Events has a cooldown timer with " + timeLeft + " minutes remaining.");
                        break;

                    case 16664:
                        if (gameObject.getPosition().getX() == 3058 && gameObject.getPosition().getY() == 3376) {
                            player.moveTo(new Position(3058, 9776, 0), true);
                            player.getPacketSender().sendMessage("You climb down the stairs!");
                        }
                        break;
                    case 23969:
                        if (gameObject.getPosition().getX() == 3059 && gameObject.getPosition().getY() == 9776) {
                            player.moveTo(new Position(3061, 3376, 0), true);
                            player.getPacketSender().sendMessage("You climb up the stairs!");
                        }
                        break;
                    case 30374:
                        if (gameObject.getPosition().getX() == 3055 && gameObject.getPosition().getY() == 9743) {
                            player.moveTo(new Position(3739, 5676, 0), true);
                            player.getPacketSender().sendMessage("You enter the cave to Motherlode Mine!");
                        }
                        break;
                    case 26654:
                        if (gameObject.getPosition().getX() == 3059 && gameObject.getPosition().getY() == 9764) {
                            player.moveTo(new Position(3739, 5676, 0), true);
                            player.getPacketSender().sendMessage("You enter the cave to Motherlode Mine!");
                        }
                        break;
                    case 28579:
                    case 28580:

                        if (gameObject.getPosition().getY() > 3881 || gameObject.getPosition().getX() < 1498) {
                            player.getPacketSender().sendMessage("@red@Only the EAST side of the mine functions!");
                            return;
                        }

                        if(!player.getClickDelay().elapsed(2500))
                            return;

                        player.getClickDelay().reset();
                        BlastMine.makeCavity(player, gameObject);
                        break;
                    case 32602: //Meiyerditch Boat
                        if (gameObject.getPosition().getX() == 3661 && gameObject.getPosition().getY() == 3279) {
                            player.moveTo(new Position(3671, 3544, 0), true);
                            player.getPacketSender().sendMessage("You travel on the boat to Port Phasmatys");
                            return;
                        }
                        break;
                    case 32601:
                        if (gameObject.getPosition().getX() == 3671 && gameObject.getPosition().getY() == 3545) {
                            player.moveTo(new Position(3661, 3278, 0), true);
                            player.getPacketSender().sendMessage("You travel on the boat to Meiyerditch.");
                            return;
                        }
                        break;
                    case 32637: //Nightmare entrance
                        /*if (gameObject.getPosition().getX() == 3727 && gameObject.getPosition().getY() == 3300) {
                            if (player.getGameMode() != GameMode.SEASONAL_IRONMAN || player.seasonalBossTeleports[8] == 1) {
                                NightmareInstance.enter(player);
                                return;
                            }
                        }*/
                        if (gameObject.getPosition().getX() == 3727 && gameObject.getPosition().getY() == 3300) {
                            NightmareInstance.enter(player);
                        }
                        break;
                    case 30176: //Thermonuclear entrance
                        /*if (gameObject.getPosition().getX() == 2411 && gameObject.getPosition().getY() == 3061) {
                            if (player.getGameMode() != GameMode.SEASONAL_IRONMAN || player.seasonalBossTeleports[9] == 1) {
                                player.moveTo(new Position(2377, 4684, 0), true);
                                player.getPacketSender().sendMessage("You enter the cave.");
                                return;
                            }
                        }*/
                        if (gameObject.getPosition().getX() == 2411 && gameObject.getPosition().getY() == 3061) {
                            player.moveTo(new Position(2377, 4684, 0), true);
                            player.getPacketSender().sendMessage("You enter the cave.");
                            return;
                        }
                        break;
                    case 26415: //GWD Boulder
                        if (gameObject.getPosition().getX() == 2898 && gameObject.getPosition().getY() == 3716) {

                            if (player.getPosition().getY() > 3717)
                                player.moveTo(new Position(2898, 3715, 0), true);
                            else if (player.getPosition().getY() < 3717)
                                player.moveTo(new Position(2898, 3719, 0), true);

                            player.getPacketSender().sendMessage("You move past the boulder!");
                            return;
                        }
                        break;
                    case 26372: //GWD entrance
                        /*if (gameObject.getPosition().getX() == 2916 && gameObject.getPosition().getY() == 3747) {
                            if (player.getGameMode() != GameMode.SEASONAL_IRONMAN || player.seasonalBossTeleports[5] == 1) {
                                player.moveTo(new Position(2871, 5318, 2), true);
                                player.getPacketSender().sendMessage("You enter the cave.");
                                return;
                            }
                        }*/
                        if (gameObject.getPosition().getX() == 2916 && gameObject.getPosition().getY() == 3747) {
                            player.moveTo(new Position(2871, 5318, 2), true);
                            player.getPacketSender().sendMessage("You enter the cave.");
                            return;
                        }
                        break;
                    case 12202: //Mole entrance
                        /*if (gameObject.getPosition().getX() == 2996 && gameObject.getPosition().getY() == 3377) {
                            if (player.getGameMode() != GameMode.SEASONAL_IRONMAN || player.seasonalBossTeleports[4] == 1) {
                                player.moveTo(new Position(1755, 5183, 0), true);
                                player.getPacketSender().sendMessage("You enter the cave.");
                                return;
                            }
                        } */
                        if (gameObject.getPosition().getX() == 2996 && gameObject.getPosition().getY() == 3377) {
                            player.moveTo(new Position(1755, 5183, 0), true);
                            player.getPacketSender().sendMessage("You enter the cave.");
                            return;

                        } else
                            player.getPacketSender().sendMessage("Try to climb down a different Mole hill.");
                        break;
                    case 16680: //Taverly Dungeon entrance
                        if (gameObject.getPosition().getX() == 2884 && gameObject.getPosition().getY() == 3397) {
                            player.moveTo(new Position(2884, 9798, 0), true);
                            player.getPacketSender().sendMessage("You climb down the ladder..");
                            return;
                        }
                        break;
                    case 2123: //Slayer Dungeon entrance
                        if (gameObject.getPosition().getX() == 2797 && gameObject.getPosition().getY() == 3614) {
                            player.moveTo(new Position(2808, 10002, 0), true);
                            player.getPacketSender().sendMessage("You enter the cave.");
                            return;
                        }
                        break;
                    case 2141: //Slayer Dungeon exit
                        if (gameObject.getPosition().getX() == 2809 && gameObject.getPosition().getY() == 10001) {
                            player.moveTo(new Position(2796, 3614, 0), true);
                            player.getPacketSender().sendMessage("You enter the cave.");
                            return;
                        }
                        break;
                    case 25274: //Ancient Cavern entrance
                        if (gameObject.getPosition().getX() == 2510 && gameObject.getPosition().getY() == 3506) {
                            player.moveTo(new Position(1747, 5324, 0), true);
                            player.getPacketSender().sendMessage("You enter the cave.");
                            return;
                        }
                        break;


                    case 26370: //rope exit
                        if (gameObject.getPosition().getX() == 2377 && gameObject.getPosition().getY() == 4683) {
                            player.moveTo(new Position(2411, 3060, 0), true);
                            player.getPacketSender().sendMessage("You exit the cave.");
                            return;
                        } else if (gameObject.getPosition().getX() == 2461 && gameObject.getPosition().getY() == 4768) {
                            player.moveTo(new Position(3727, 3302, 0), true);
                            player.getPacketSender().sendMessage("You exit the cave.");
                            return;
                        } else if (gameObject.getPosition().getX() == 2871 && gameObject.getPosition().getY() == 5318) {
                            player.moveTo(new Position(2916, 3746, 0), true);
                            player.getPacketSender().sendMessage("You exit the cave.");
                            return;
                        } else if (gameObject.getPosition().getX() == 1755 && gameObject.getPosition().getY() == 5184) {
                            player.moveTo(new Position(2996, 3376, 0), true);
                            player.getPacketSender().sendMessage("You exit the cave.");
                            return;
                        } else if (gameObject.getPosition().getX() == 1240 && gameObject.getPosition().getY() == 1242) {
                            player.moveTo(new Position(2873, 9847, 0), true);
                            player.getPacketSender().sendMessage("You exit the cave.");
                            return;
                        } else if (gameObject.getPosition().getX() == 3487 && gameObject.getPosition().getY() == 9516) {
                            player.moveTo(new Position(3295, 3142, 0), true);
                            player.getPacketSender().sendMessage("You exit the cave.");
                            return;
                        }


                        break;
                    case 28581:
                        if (gameObject.getPosition().getY() > 3881 || gameObject.getPosition().getX() < 1498) {
                            player.getPacketSender().sendMessage("@red@Only the EAST side of the mine functions!");
                            return;
                        }

                        BlastMine.fillCavity(player, gameObject);
                        break;
                    case 332994:
                    case 32994:

                        if (gameObject.getPosition().getX() == 1229 && gameObject.getPosition().getY() == 3557)
                            RaidChest.coxChest(player, gameObject);
                        else if (gameObject.getPosition().getX() == 3662 && gameObject.getPosition().getY() == 3218)
                            RaidChest.tobChest(player, gameObject);
                        else if (gameObject.getPosition().getX() == 2442 && gameObject.getPosition().getY() == 3092)
                            RaidChest.raidsLobbyChest(player, gameObject);
                        else if (gameObject.getPosition().getX() == 1630 && gameObject.getPosition().getY() == 3939)
                            RaidChest.gauntletChest(player, gameObject);
                        else if (gameObject.getPosition().getX() == 3082 && gameObject.getPosition().getY() == 3412)
                            RaidChest.strongholdRaidChest(player, gameObject);

                        return;
                    case 334682:
                    case 34682:
                        EquipmentUpgrades1.showInterface(player);
                        return;

                    case 1817: //KBD
                        if (player.getPosition().getY() <= 4682 && player.getPosition().getY() >= 4680)
                            player.getPacketSender().sendMessage("This ladder has been disabled.");
                        // player.moveTo(new Position(3017, 3848, 0));

                        return;
                    case 329301:
                    case 29301:
                        //player.getInventory().add(6685, 1);
                        return;
                    case 18987:
                        if (player.getPosition().getX() <= 3019 && player.getPosition().getX() >= 3017)
                            player.moveTo(new Position(2271, 4681, 0));

                        return;
                    case 21727:
                        if (player.getPosition().getY() < 9498)
                            player.moveTo(new Position(2700, 9500, 0));
                        else if (player.getPosition().getY() > 9498)
                            player.moveTo(new Position(2700, 9492, 0));


                        //Chaos Raids
                    case 860:
                        if (player.prestige > 0) {


                        } else
                            player.getPacketSender().sendMessage("You must prestige to access this content!");

                        break;
                    case 32656:
                        if (player.getTobKC() >= 100) {
                            player.getPacketSender().sendMessage("You take a Death cape from the chest!");
                            player.getInventory().add(14056, 1);
                        } else
                            player.getPacketSender().sendMessage("You must have 100 completions of Theatre of Blood to access this.");
                        break;
                    case 9092:
                    case 9093:
                        BlastFurnace.withdrawBars(player);
                        break;
                    case 4651:
                        player.getPacketSender().sendMessage("@red@Coming soon.");
                        break;
                    case 4650:
                        Casino.roulette(player);
                        break;
                    case 38660:
                        if (ShootingStar.CRASHED_STAR != null) {

                        }
                        break;
                    case 14985:
                        RandomEvent.randomEnd(player);
                        break;
                    case 11434:
                        if (EvilTrees.SPAWNED_TREE != null) {

                        }
                        break;
                    case 32655: //Limited Rares
                        LimitedRares.openRareShop(player);
                        break;
                    case 31555:
                    case 31558:
                    case 31561:
                        player.sendMessage("@red@Rev caves are currently under construction.");
                        break;
                    case 30914:

                        if (player.getStaffRights().getStaffRank() > 0 || player.getGameMode() == GameMode.SEASONAL_IRONMAN) {
                            if (x == 3723)
                                player.moveTo(GameSettings.HOMEBOAT_POSITION);
                            else if (x == 1833)
                                player.moveTo(GameSettings.DZBOAT_POSITION);
                        } else
                            player.getPacketSender().sendMessage("You must be a dontor to use this boat.");

                        break;
                    case 26761:
                        /*if ((x == 3090) && (y == 3475)) {
                            if (player.WildyPoints >= 25) {
                                player.setDirection(Direction.WEST);
                                player.performAnimation(new Animation(3415));
                                TeleportHandler.teleportPlayer(player, new Position(3090, 3956), TeleportType.LEVER);
                                player.WildyPoints -= 25;
                                player.getPacketSender().sendMessage("@or2@You consume 25 Wildy Points to use the Lever.");
                            } else {
                                player.getPacketSender().sendMessage("@red@You need to have at least 25 Wildy Points to use the Lever..");
                            }
                        }*/

                        player.getPacketSender().sendMessage("Please use the wilderness ditch or Obelisk to enter the wilderness.");

                        break;
                    case 39550:
                        player.getCofferData();
                        DeathsCoffer.openInter(player);
                        break;
                    case 26420:
                        player.performAnimation(new Animation(3415));
                        TeleportHandler.teleportPlayer(player, new Position(2919, 5273), TeleportType.LEVER);
                        break;
                    case 9295:
                        if (player.getPosition().getX() == 3149) {
                            TeleportHandler.teleportPlayer(player, new Position(3155, 9906), TeleportType.LEVER);
                        } else if (player.getPosition().getX() == 3155) {
                            TeleportHandler.teleportPlayer(player, new Position(3149, 9906), TeleportType.LEVER);
                        }

                        break;

                    case 13568:
                        if (GlobalEventHandler.effectActive(GlobalEvent.Effect.LOADED) || (player.loadedEvent && player.personalEvent))
                            player.setOverloadPotionTimer(3000);
                        else if (player.Survivalist == 3)
                            player.setOverloadPotionTimer(3000);
                        else if (player.Survivalist == 2)
                            player.setOverloadPotionTimer(1800);
                        else if (player.Survivalist == 1)
                            player.setOverloadPotionTimer(1200);
                        else
                            player.setOverloadPotionTimer(600);
                        break;
                    case 2079:
                        TrioBosses.openChest(player);
                        break;
                    case 2112:
                        if (player.getPosition().getY() == 9756) {
                            player.moveTo(new Position(player.getPosition().getX(), 9757));
                        } else if (player.getPosition().getY() == 9757) {
                            player.moveTo(new Position(player.getPosition().getX(), 9756));
                        }
                        break;
                    case 5259:
                        player.moveTo(GameSettings.DEFAULT_POSITION.copy());
                        break;
                    case 16544:
                        if (gameObject.getPosition().getX() == 2774) {
                            player.moveTo(new Position(2767, 10003));
                            return;
                        }
                        break;
                    case 16539:
                        if (gameObject.getPosition().getX() == 2734) {
                            player.moveTo(new Position(2730, 10008));
                            return;
                        }
                        break;
                    case 13405:
                        /*if (player.Prestige > 0) {
                            player.activeInterface = "instancedbosses";
                            InstancedBossInterface.showInterface(player);
                        } else
                            player.getPacketSender().sendMessage("@red@You must Prestige to operate this. Talk to Max!");
                        */
                        break;
                    case 9398:
                        //GrandExchange.open(player);
                        break;
                    case 39674:
                        HeroesStatue.showInterface(player);
                        break;
                    case 21505:
                    case 21507:
                        player.moveTo(new Position(2329, 3804));
                        break;

                    case 38700:
                        player.moveTo(new Position(3092, 3502));
                        break;
                    case 45803:
                    case 1767:
                        DialogueManager.start(player, 114);
                        player.setDialogueActionId(72);
                        break;
                    case 7352:
                        if (Dungeoneering.doingDungeoneering(player) && player.getMinigameAttributes().getDungeoneeringAttributes().getParty().getGatestonePosition() != null) {
                            player.moveTo(player.getMinigameAttributes().getDungeoneeringAttributes().getParty().getGatestonePosition());
                            player.setEntityInteraction(null);
                            player.getPacketSender().sendMessage("You are teleported to your party's gatestone.");
                            player.performGraphic(new Graphic(1310));
                        } else
                            player.getPacketSender().sendMessage("Your party must drop a Gatestone somewhere in the dungeon to use this portal.");
                        break;
                    case 7353:
                        player.moveTo(new Position(2439, 4956, player.getPosition().getZ()));
                        break;
                    case 7321:
                        player.moveTo(new Position(2452, 4944, player.getPosition().getZ()));
                        break;
                    case 7322:
                        player.moveTo(new Position(2455, 4964, player.getPosition().getZ()));
                        break;
                    case 7315:
                        player.moveTo(new Position(2447, 4956, player.getPosition().getZ()));
                        break;
                    case 7316:
                        player.moveTo(new Position(2471, 4956, player.getPosition().getZ()));
                        break;
                    case 7318:
                        player.moveTo(new Position(2464, 4963, player.getPosition().getZ()));
                        break;
                    case 7319:
                        player.moveTo(new Position(2467, 4940, player.getPosition().getZ()));
                        break;
                    case 7324:
                        player.moveTo(new Position(2481, 4956, player.getPosition().getZ()));
                        break;
                    case 11356:
                        player.moveTo(new Position(2860, 9741));
                        player.getPacketSender().sendMessage("You step through the portal..");
                        break;
                    case 10068: //Zulrah Boat
                        player.getPacketSender().sendMessage("You board the boat..");
                        ZulrahInstance.enter(player);
                        break;
                    case 10091:
                        Fishing.setupFishing(player, Spot.ROCKTAIL);
                        break;
                    case 8702:
                        Fishing.setupFishing(player, Spot.SHR);
                        break;
                    case 16537:
                        if (player.getSkillManager().getCurrentLevel(Skill.AGILITY) < 61) {
                            player.getPacketSender().sendMessage("You need an Agility level of at least 61 or higher to climb this");
                            return;
                        }
                        if (player.getPosition().getZ() == 0)
                            player.moveTo(new Position(3422, 3549, 1));
                        else if (player.getPosition().getZ() == 1) {
                            if (gameObject.getPosition().getX() == 3447)
                                player.moveTo(new Position(3447, 3575, 2));
                            else
                                player.moveTo(new Position(3447, 3575, 0));
                        }
                        break;

                    case 9320:
                        if (player.getSkillManager().getCurrentLevel(Skill.AGILITY) < 61) {
                            player.getPacketSender().sendMessage("You need an Agility level of at least 61 or higher to climb this");
                            return;
                        }
                        if (player.getPosition().getZ() == 1)
                            player.moveTo(new Position(3422, 3549, 0));
                        else if (player.getPosition().getZ() == 0)
                            player.moveTo(new Position(3447, 3575, 1));
                        else if (player.getPosition().getZ() == 2)
                            player.moveTo(new Position(3447, 3575, 1));
                        player.performAnimation(new Animation(828));
                        break;
                    case 2274:
                        if (gameObject.getPosition().getX() == 2912 && gameObject.getPosition().getY() == 5300) {
                            player.moveTo(new Position(2914, 5300, 1));
                        } else if (gameObject.getPosition().getX() == 2914 && gameObject.getPosition().getY() == 5300) {
                            player.moveTo(new Position(2912, 5300, 2));
                        } else if (gameObject.getPosition().getX() == 2919 && gameObject.getPosition().getY() == 5276) {
                            player.moveTo(new Position(2918, 5274));
                        } else if (gameObject.getPosition().getX() == 2918 && gameObject.getPosition().getY() == 5274) {
                            player.moveTo(new Position(2919, 5276, 1));
                        } else if (gameObject.getPosition().getX() == 3001 && gameObject.getPosition().getY() == 3931 || gameObject.getPosition().getX() == 3652 && gameObject.getPosition().getY() == 3488) {
                            player.moveTo(GameSettings.DEFAULT_POSITION.copy());
                            player.getPacketSender().sendMessage("The portal teleports you home.");
                        }
                        break;
                    case 7836:
                    case 7808:
                        int amt = player.getInventory().getAmount(6055);
                        if (amt > 0) {
                            player.getInventory().delete(6055, amt);
                            player.getPacketSender().sendMessage("You put the weed in the compost bin.");
                            player.getSkillManager().addExperience(Skill.FARMING, 20 * amt);
                        } else {
                            player.getPacketSender().sendMessage("You do not have any weeds in your inventory.");
                        }
                        break;
                    case 5096:
                        if (gameObject.getPosition().getX() == 2644 && gameObject.getPosition().getY() == 9593)
                            player.moveTo(new Position(2649, 9591));
                        break;

                    case 5094:
                        if (gameObject.getPosition().getX() == 2648 && gameObject.getPosition().getY() == 9592)
                            player.moveTo(new Position(2643, 9594, 2));
                        break;

                    case 5098:
                        if (gameObject.getPosition().getX() == 2635 && gameObject.getPosition().getY() == 9511)
                            player.moveTo(new Position(2637, 9517));
                        break;

                    case 5097:
                        if (gameObject.getPosition().getX() == 2635 && gameObject.getPosition().getY() == 9514)
                            player.moveTo(new Position(2636, 9510, 2));
                        break;
                    case 26461:
                    case 26518:
                    case 23093:
                    case 26380:
                        String bossRoom = "";
                        int index = 0;
                        Position movePos = GameSettings.DEFAULT_POSITION;
                        if (id == 326380) {
                            bossRoom = "Armadyl";
                            index = 0;
                            movePos = new Position(2839, 5296, 2);
                        } else if (id == 23093 && gameObject.getPosition().getX() == 2860) {
                            bossRoom = "Bandos";
                            index = 1;
                            movePos = new Position(2864, 5354, 2);
                        } else if (id == 23093 && gameObject.getPosition().getX() == 2912) {
                            bossRoom = "Saradomin";
                            index = 2;
                            movePos = new Position(2907, 5265);
                        } else if (id == 326518) {
                            bossRoom = "Zamorak";
                            index = 3;
                            movePos = new Position(2925, 5331, 2);
                        }
                        player.moveTo(movePos);
                        player.getMinigameAttributes().getGodwarsDungeonAttributes().setHasEnteredRoom(true);
                        player.getMinigameAttributes().getGodwarsDungeonAttributes().getKillcount()[index] = 0;
                        player.getPacketSender().sendString(16216 + index, "0");
                        break;
                    case 26289:
                    case 26286:
                    case 26288:
                    case 26287:
                        if ((System.currentTimeMillis() - player.getMinigameAttributes().getGodwarsDungeonAttributes().getAltarDelay() < 300000) && player.getPointsHandler().getAchievementPoints() > 79) {
                            player.getPacketSender().sendMessage("");
                            player.getPacketSender().sendMessage("You can only pray at a God's altar once every 5 minutes.");
                            player.getPacketSender().sendMessage("You must wait another " + (int) ((300 - (System.currentTimeMillis() - player.getMinigameAttributes().getGodwarsDungeonAttributes().getAltarDelay()) * 0.001)) + " seconds before being able to do this again.");
                            return;
                        } else if (System.currentTimeMillis() - player.getMinigameAttributes().getGodwarsDungeonAttributes().getAltarDelay() < 600000) {
                            player.getPacketSender().sendMessage("");
                            player.getPacketSender().sendMessage("You can only pray at a God's altar once every 10 minutes.");
                            player.getPacketSender().sendMessage("You must wait another " + (int) ((600 - (System.currentTimeMillis() - player.getMinigameAttributes().getGodwarsDungeonAttributes().getAltarDelay()) * 0.001)) + " seconds before being able to do this again.");
                            return;
                        }
                        int itemCount = id == 26289 ? Equipment.getItemCount(player, "Bandos", false) : id == 26286 ? Equipment.getItemCount(player, "Zamorak", false) : id == 26288 ? Equipment.getItemCount(player, "Armadyl", false) : id == 26287 ? Equipment.getItemCount(player, "Saradomin", false) : 0;
                        int toRestore = player.getSkillManager().getMaxLevel(Skill.PRAYER) + (itemCount * 10);
                        if (player.getSkillManager().getCurrentLevel(Skill.PRAYER) >= toRestore) {
                            player.getPacketSender().sendMessage("You do not need to recharge your Prayer points at the moment.");
                            return;
                        }
                        player.performAnimation(new Animation(645));
                        player.getSkillManager().setCurrentLevel(Skill.PRAYER, toRestore);
                        player.getMinigameAttributes().getGodwarsDungeonAttributes().setAltarDelay(System.currentTimeMillis());
                        break;

                    case 2114:
                        if (player.getPosition().getX() >= 3432) {
                            player.moveTo(new Position(3433, 3538, 1));
                        }
                        break;
                    case 2118:
                        player.moveTo(new Position(3438, 3538, 0));
                        break;
                    case 2119:
                        player.moveTo(new Position(3417, 3541, 2));
                        break;
                    case 2120:
                        player.moveTo(new Position(3412, 3541, 1));
                        break;
                    case 25339:
                    case 25340:
                        player.moveTo(new Position(1778, 5346, player.getPosition().getZ() == 0 ? 1 : 0));
                        break;
                    case 10229:
                        if (gameObject.getPosition().getX() == 2899 && gameObject.getPosition().getY() == 4449) {
                            player.performAnimation(new Animation(828));
                            player.getPacketSender().sendMessage("You climb up the ladder..");
                            player.moveTo(new Position(1912, 4367));
                        }
                        break;
                    case 10227:
                        /*if (gameObject.getPosition().getX() == 1911 && gameObject.getPosition().getY() == 4367) {
                            if (player.getGameMode() != GameMode.SEASONAL_IRONMAN || player.seasonalBossTeleports[3] == 1) {
                                player.performAnimation(new Animation(827));
                                player.getPacketSender().sendMessage("You climb down the ladder..");
                                player.moveTo(new Position(2900, 4449));
                            }
                        } */
                        if (gameObject.getPosition().getX() == 1911 && gameObject.getPosition().getY() == 4367) {
                            player.performAnimation(new Animation(827));
                            player.getPacketSender().sendMessage("You climb down the ladder..");
                            player.moveTo(new Position(2900, 4449));

                        } else if (gameObject.getPosition().getX() == 3097 && gameObject.getPosition().getY() == 3468) {
                            player.performAnimation(new Animation(827));
                            player.getPacketSender().sendMessage("You climb down the ladder..");
                            player.moveTo(new Position(3097, 9868));
                        } else if (gameObject.getPosition().getX() == 3008 && gameObject.getPosition().getY() == 3151) {
                            player.performAnimation(new Animation(827));
                            player.getPacketSender().sendMessage("You climb down the ladder..");
                            player.moveTo(new Position(3007, 9550));
                        }
                        break;
                    case 10216:
                        if (gameObject.getPosition().getX() == 1890 && gameObject.getPosition().getY() == 4408) {
                            player.performAnimation(new Animation(828));
                            player.getPacketSender().sendMessage("You climb up the ladder..");
                            player.moveTo(new Position(2523, 3739));
                        }
                        break;
                    case 8929:
                        if (gameObject.getPosition().getX() == 2519 && gameObject.getPosition().getY() == 3738) {
                            player.performAnimation(new Animation(827));
                            player.getPacketSender().sendMessage("You climb down the ladder..");
                            player.moveTo(new Position(1890, 4409));
                        }
                        break;
                    case 677:
                        /*if (player.getPosition().getX() == 2974 && player.getLocation() == Location.CORPOREAL_BEAST) {
                            player.performAnimation(new Animation(827));
                            player.getPacketSender().sendMessage("You leave Corporeal Beast's area.");
                            player.moveTo(new Position(2970, 4382, 2));
                        } else if ((player.getPosition().getX() == 2970 || player.getPosition().getX() == 2971) && player.getLocation() == Location.CORPOREAL_BEAST) {
                            player.performAnimation(new Animation(827));
                            player.getPacketSender().sendMessage("You enter Corporeal Beast's area.");
                            player.moveTo(new Position(2974, 4382, 2));
                        }
                        break;*/

                        if(player.getPosition().getX() > 2971) {
                            DialogueManager.start(player, 1027);
                            player.setDialogueActionId(1029);
                        }
                        break;
                    case 678:
                        if (gameObject.getPosition().getX() == 3201 && gameObject.getPosition().getY() == 3679) {
                            player.performAnimation(new Animation(827));
                            player.getPacketSender().sendMessage("You enter the cave..");
                            player.moveTo(new Position(2974, 4382, 2));
                        }
                        break;
                    case 679:
                        if (gameObject.getPosition().getX() == 2963 && gameObject.getPosition().getY() == 4382) {
                            player.performAnimation(new Animation(827));
                            player.getPacketSender().sendMessage("You leave the cave..");
                            player.moveTo(new Position(3205, 3680, 0));
                        }
                        break;
                    case 6970:
                        if (gameObject.getPosition().getX() == 3498 && gameObject.getPosition().getY() == 3377) {
                            player.performAnimation(new Animation(827));
                            player.getPacketSender().sendMessage("You take the boat..");
                            player.moveTo(new Position(3522, 3285, 0));
                        }
                        break;
                    case 6969:
                        if (gameObject.getPosition().getX() == 3523 && gameObject.getPosition().getY() == 3284) {
                            player.performAnimation(new Animation(827));
                            player.getPacketSender().sendMessage("You take the boat..");
                            player.moveTo(new Position(3499, 3380, 0));
                        }
                        break;
                    case 9084:
                        /*if (player.getGameMode() == GameMode.SEASONAL_IRONMAN) {
                            if (player.seasonalMinigameTeleports[1] == 0) {
                                player.getPacketSender().sendMessage("You must unlock the Blast Furnace teleport to unlock this.");
                                return;
                            }
                        }*/

                        if (gameObject.getPosition().getX() == 2930 && gameObject.getPosition().getY() == 10196) {
                            player.performAnimation(new Animation(827));
                            player.getPacketSender().sendMessage("You take the boat..");
                            player.moveTo(new Position(1939, 4958, 0));
                        }
                        break;
                    case 9138:
                        if (gameObject.getPosition().getX() == 1939 && gameObject.getPosition().getY() == 4956) {
                            player.performAnimation(new Animation(827));
                            player.getPacketSender().sendMessage("You take the boat..");
                            player.moveTo(new Position(2931, 10196, 0));
                        }
                        break;
                    case 20878:
                        if (gameObject.getPosition().getX() == 2714 && gameObject.getPosition().getY() == 9564) {
                            player.performAnimation(new Animation(827));
                            player.getPacketSender().sendMessage("You take the boat..");
                            player.moveTo(new Position(2744, 3152, 0));
                        }
                        break;
                    case 34713:
                        if (gameObject.getPosition().getX() == 2742 && gameObject.getPosition().getY() == 3153) {
                            player.performAnimation(new Animation(827));
                            player.getPacketSender().sendMessage("You leave the dungeon..");
                            player.moveTo(new Position(2713, 9564, 0));
                        }
                        break;
                    case 10595:
                        if (gameObject.getPosition().getX() == 3055 && gameObject.getPosition().getY() == 9556) {
                            player.performAnimation(new Animation(827));
                            player.getPacketSender().sendMessage("You enter the cave..");
                            player.moveTo(new Position(3055, 9561, 0));
                        }
                        break;
                    case 10596:
                        if (gameObject.getPosition().getX() == 3055 && gameObject.getPosition().getY() == 9560) {
                            player.performAnimation(new Animation(827));
                            player.getPacketSender().sendMessage("You enter the cave..");
                            player.moveTo(new Position(3055, 9555, 0));
                        }
                        break;
                    case 20210:
                        if (gameObject.getPosition().getX() == 2552 && gameObject.getPosition().getY() == 3559) {

                            if (player.getPosition().getY() >= 3560) {
                                player.performAnimation(new Animation(844));
                                player.getPacketSender().sendMessage("You enter Barbarian Agility.");
                                player.moveTo(new Position(2552, 3558, 0));
                            } else if (player.getPosition().getY() <= 3559) {
                                player.performAnimation(new Animation(844));
                                player.getPacketSender().sendMessage("You leave Barbarian Agility.");
                                player.moveTo(new Position(2552, 3561, 0));
                            }
                        }
                        break;
                    case 12656:
                        if (gameObject.getPosition().getX() == 2344 && gameObject.getPosition().getY() == 3654) {
                            player.performAnimation(new Animation(844));
                            player.getPacketSender().sendMessage("You enter Piscatoris Fishing Colony.");
                            player.moveTo(new Position(2344, 3650, 0));
                        } else if (gameObject.getPosition().getX() == 2344 && gameObject.getPosition().getY() == 3651) {
                            player.performAnimation(new Animation(844));
                            player.getPacketSender().sendMessage("You leave Piscatoris Fishing Colony.");
                            player.moveTo(new Position(2344, 3655, 0));
                        }
                        break;
                    case 30367:
                        if (player.getInteractingObject().getPosition().getX() == 3019 && player.getInteractingObject().getPosition().getY() == 3338) { //Mining Guild
                            player.moveTo(new Position(3019, 9737, 0));
                        } else if (player.getInteractingObject().getPosition().getX() == 3020 && player.getInteractingObject().getPosition().getY() == 3339) { //Mining Guild
                            player.moveTo(new Position(3021, 9739, 0));
                        } else if (player.getInteractingObject().getPosition().getX() == 3019 && player.getInteractingObject().getPosition().getY() == 3340) { //Mining Guild
                            player.moveTo(new Position(3019, 9741, 0));
                        } else if (player.getInteractingObject().getPosition().getX() == 3018 && player.getInteractingObject().getPosition().getY() == 3339) { //Mining Guild
                            player.moveTo(new Position(3017, 9739, 0));
                        }
                        break;
                    case 26567:
                    case 26568:
                    case 26569:
                        /*if (player.getGameMode() != GameMode.SEASONAL_IRONMAN || player.seasonalBossTeleports[1] == 1) {
                            if (gameObject.getPosition().getX() == 2874 && gameObject.getPosition().getY() >= 9846 && gameObject.getPosition().getY() <= 9848) {
                                player.performAnimation(new Animation(827));
                                player.getPacketSender().sendMessage("You enter the dungeon..");
                                player.moveTo(new Position(1240, 1243, 0));
                            }
                        }*/
                        if (gameObject.getPosition().getX() == 2874 && gameObject.getPosition().getY() >= 9846 && gameObject.getPosition().getY() <= 9848) {
                            player.performAnimation(new Animation(827));
                            player.getPacketSender().sendMessage("You enter the dungeon..");
                            player.moveTo(new Position(1240, 1243, 0));
                        }
                        break;
                    case 1568:
                        player.moveTo(new Position(3097, 9868));
                        break;
                    case 5103: //Brimhaven vines
                    case 5104:
                    case 5105:
                    case 5106:
                    case 5107:
                    case 21731:
                    case 21732:
                    case 21733:
                    case 21734:
                    case 21735:
                        if (!player.getClickDelay().elapsed(4000))
                            return;
                        if (player.getSkillManager().getCurrentLevel(Skill.WOODCUTTING) < 30) {
                            player.getPacketSender().sendMessage("You need a Woodcutting level of at least 30 to do this.");
                            return;
                        }
                        if (WoodcuttingData.getHatchet(player) < 0) {
                            player.getPacketSender().sendMessage("You do not have a hatchet which you have the required Woodcutting level to use.");
                            return;
                        }
                        final Hatchet axe = Hatchet.forId(WoodcuttingData.getHatchet(player));
                        player.performAnimation(new Animation(axe.getAnim()));
                        gameObject.setFace(-1);
                        TaskManager.submit(new Task(3 + RandomUtility.inclusiveRandom(4), player, false) {
                            @Override
                            protected void execute() {
                                if (player.getMovementQueue().isMoving()) {
                                    stop();
                                    return;
                                }
                                int x = 0;
                                int y = 0;
                                if (player.getPosition().getX() == 2689 && player.getPosition().getY() == 9564) {
                                    x = 2;
                                    y = 0;
                                } else if (player.getPosition().getX() == 2691 && player.getPosition().getY() == 9564) {
                                    x = -2;
                                    y = 0;
                                } else if (player.getPosition().getX() == 2683 && player.getPosition().getY() == 9568) {
                                    x = 0;
                                    y = 2;
                                } else if (player.getPosition().getX() == 2683 && player.getPosition().getY() == 9570) {
                                    x = 0;
                                    y = -2;
                                } else if (player.getPosition().getX() == 2674 && player.getPosition().getY() == 9479) {
                                    x = 2;
                                    y = 0;
                                } else if (player.getPosition().getX() == 2676 && player.getPosition().getY() == 9479) {
                                    x = -2;
                                    y = 0;
                                } else if (player.getPosition().getX() == 2693 && player.getPosition().getY() == 9482) {
                                    x = 2;
                                    y = 0;
                                } else if (player.getPosition().getX() == 2672 && player.getPosition().getY() == 9499) {
                                    x = 2;
                                    y = 0;
                                } else if (player.getPosition().getX() == 2674 && player.getPosition().getY() == 9499) {
                                    x = -2;
                                    y = 0;
                                }
                                CustomObjects.objectRespawnTask(player, new GameObject(-1, gameObject.getPosition().copy()), gameObject, 10);
                                player.getPacketSender().sendMessage("You chop down the vines..");
                                player.getSkillManager().addExperience(Skill.WOODCUTTING, 45);
                                player.performAnimation(new Animation(65535));
                                player.getMovementQueue().walkStep(x, y);
                                stop();
                            }
                        });
                        player.getClickDelay().reset();
                        break;
                    case 20882:
                        if (x == 2683)
                            player.moveTo(new Position(2687, 9506, 0), true);
                        if (x == 2686)
                            player.moveTo(new Position(2682, 9506, 0), true);

                        break;
                    case 23566:
                        if (y == 9964)
                            player.moveTo(new Position(3119, 9970, 0), true);
                        if (y == 9969)
                            player.moveTo(new Position(3119, 9963, 0), true);

                        break;
                    case 16682:
                        if (y == 3545)
                            player.moveTo(new Position(2532, 3546, 0), true);

                        break;
                    case 29942:
                        if (player.getSkillManager().getCurrentLevel(Skill.SUMMONING) == player.getSkillManager().getMaxLevel(Skill.SUMMONING)) {
                            player.getPacketSender().sendMessage("You do not need to recharge your Summoning points right now.");
                            return;
                        }
                        player.performGraphic(new Graphic(1517));
                        player.getSkillManager().setCurrentLevel(Skill.SUMMONING, player.getSkillManager().getMaxLevel(Skill.SUMMONING), true);
                        player.getPacketSender().sendString(18045, " " + player.getSkillManager().getCurrentLevel(Skill.SUMMONING) + "/" + player.getSkillManager().getMaxLevel(Skill.SUMMONING));
                        player.getPacketSender().sendMessage("You recharge your Summoning points.");
                        break;
                    case 29993:
                        if (y > player.getPosition().getY())
                            player.moveTo(new Position(x, y + 2));

                        else if (y < player.getPosition().getY())
                            player.moveTo(new Position(x, y - 2));

                        break;
                    case 36061:
                        ShopManager.getShops().get(96).open(player);
                        break;
                    case 36062:
                        ShopManager.getShops().get(95).open(player);
                        break;
                    case 37340:
                    case 329322:
                    case 29322:

                        /*if (player.getGameMode() == GameMode.SEASONAL_IRONMAN) {
                            if (player.seasonalMinigameTeleports[5] == 0) {
                                player.getPacketSender().sendMessage("You must unlock the Wintertodt teleport to unlock this.");
                                return;
                            }
                        }*/

                        if (player.getPosition().getY() > 3964)
                            player.moveTo(new Position(1631, 3962, 0));
                        else
                            player.moveTo(new Position(1630, 3968, 0));
                        break;
                    case 31481:
                        player.moveTo(new Position(3031, 6121, 1));
                        player.getPacketSender().sendMessage("@cya@You must Prestige to enter the Gauntlet");
                        break;
                    case 28858: //top of redwood trees
                        if (x == 1575)
                            if (player.getPosition().getZ() == 1)
                                player.moveTo(new Position(1575, y, 0));
                        break;
                    case 28857: //bottom of redwood trees
                        if (x == 1575)
                            if (player.getPosition().getZ() == 0)
                                player.moveTo(new Position(1574, y, 1));
                        break;
                    case 57225:
                        if (!player.getMinigameAttributes().getGodwarsDungeonAttributes().hasEnteredRoom()) {
                            player.setDialogueActionId(44);
                            DialogueManager.start(player, 79);
                        } else {
                            player.moveTo(new Position(2906, 5204));
                            player.getMinigameAttributes().getGodwarsDungeonAttributes().setHasEnteredRoom(false);
                        }
                        break;
                    case 884:
                        player.setDialogueActionId(41);
                        DialogueManager.start(player, 75);
                        break;
                    case 31316:
                        player.moveTo(new Position(3086, 3502));
                        player.forceChat("That was a tough rope to climb...");
                        break;
                    case 16510:
                        if (player.getSkillManager().getCurrentLevel(Skill.AGILITY) < 80) {
                            player.getPacketSender().sendMessage("You need an Agility level of at least 80 to use this shortcut.");
                            return;
                        }
                        player.performAnimation(new Animation(769));
                        TaskManager.submit(new Task(1, player, false) {
                            @Override
                            protected void execute() {
                                player.moveTo(new Position(player.getPosition().getX() >= 2880 ? 2878 : 2880, 9813));
                                stop();
                            }
                        });
                        break;
                    case 16509:
                        boolean back = player.getPosition().getX() > 2888;
                        player.moveTo(back ? new Position(2886, 9799) : new Position(2891, 9799));
                        break;
                    case 2320:
                        back = player.getPosition().getY() == 9969 || player.getPosition().getY() == 9970;
                        player.moveTo(back ? new Position(3120, 9963) : new Position(3120, 9969));
                        break;
                    case 1755:
                        player.performAnimation(new Animation(828));
                        player.getPacketSender().sendMessage("You climb the stairs..");
                        TaskManager.submit(new Task(1, player, false) {
                            @Override
                            protected void execute() {
                                if (gameObject.getPosition().getX() == 2547 && gameObject.getPosition().getY() == 9951) {
                                    player.moveTo(new Position(2548, 3551));
                                } else if (gameObject.getPosition().getX() == 3005 && gameObject.getPosition().getY() == 10363) {
                                    player.moveTo(new Position(3005, 3962));
                                } else if (gameObject.getPosition().getX() == 3084 && gameObject.getPosition().getY() == 9672) {
                                    player.moveTo(new Position(3117, 3244));
                                } else if (gameObject.getPosition().getX() == 3097 && gameObject.getPosition().getY() == 9867) {
                                    player.moveTo(new Position(3096, 3468));
                                }
                                stop();
                            }
                        });
                        break;
                    case 6434:
                        player.performAnimation(new Animation(827));
                        player.getPacketSender().sendMessage("You enter the trapdoor..");
                        TaskManager.submit(new Task(1, player, false) {
                            @Override
                            protected void execute() {
                                player.moveTo(new Position(3085, 9672));
                                stop();
                            }
                        });
                        break;
                    case 19187:
                    case 19175:
                        Hunter.dismantle(player, gameObject);
                        break;
                    case 25029:
                    case 325029:
                    case 25017:
                    case 325017:
                    case 25016:
                    case 325016:
                    case 25019:
                    case 325019:
                        PuroPuro.goThroughWheat(player, gameObject);
                        break;
                    case 47976:
                        Nomad.endFight(player, false);
                        break;
                    case 2182:
                        RecipeForDisaster.openRFDShop(player);
                        break;
                    //GWD Raids
                    case 4387:
                        /*if (player.getGameMode() == GameMode.SEASONAL_IRONMAN) {
                            if (player.seasonalRaidsTeleports[1] == 0) {
                                player.getPacketSender().sendMessage("You must unlock the Raids Lobby teleport to unlock this.");
                                return;
                            }
                        }*/
                        GwdRaid.start(player);
                        break;
                    //Barrows Raids
                    case 4408:
                        /*if (player.getGameMode() == GameMode.SEASONAL_IRONMAN) {
                            if (player.seasonalRaidsTeleports[1] == 0) {
                                player.getPacketSender().sendMessage("You must unlock the Raids Lobby teleport to unlock this.");
                                return;
                            }
                        }*/
                        player.barrowsRaid = true;
                        Raid.start(player);
                        break;
                    //Chaos Raids
                    case 4388:
                        /*if (player.getGameMode() == GameMode.SEASONAL_IRONMAN) {
                            if (player.seasonalRaidsTeleports[1] == 0) {
                                player.getPacketSender().sendMessage("You must unlock the Raids Lobby teleport to unlock this.");
                                return;
                            }
                        }*/
                        if (player.prestige < 1) {
                            player.sendMessage("You must Prestige to access Chaos Raids!");
                            return;
                        } else if (player.getMinigameAttributes().getRaidsAttributes().getParty() == null) {
                            player.sendMessage("You need to be in a party to enter Chaos Raids.");
                            return;
                        } else if (player.getMinigameAttributes().getRaidsAttributes().getParty().getOwner() != player) {
                            player.sendMessage("Only the party leader can begin Chaos Raids.");
                            return;
                        }
					/*else{
						player.sendMessage("Chaos Raids are not ready yet.");
						return;
					}*/


                        player.chaosRaid = true;
                        ChaosRaid.start(player);
                        break;
                    case 26674:
                        /*if (player.getGameMode() == GameMode.SEASONAL_IRONMAN) {
                            if (player.seasonalMinigameTeleports[4] == 0) {
                                player.getPacketSender().sendMessage("You must unlock the Motherlode Mine teleport to unlock this.");
                                return;
                            }
                        }*/
                        MotherLodeMine.depositPayDirt(player);
                        break;
                    case 354:
                        if (gameObject.getPosition().getX() == 3748)
                            MotherLodeMine.processPayDirt(player);
                        break;
                    case 9369:
                        if (player.getPosition().getY() > 5175) {
                            FightPit.addPlayer(player);
                        } else {
                            FightPit.removePlayer(player, "leave room");
                        }
                        break;
                    case 9368:
                        if (player.getPosition().getY() < 5169) {
                            FightPit.removePlayer(player, "leave game");
                        }
                        break;
                    case 357:

                        break;
                    case 1:

                        break;
                    case 9357:
                        FightCave.leaveCave(player);
                        break;
                    case 311833:
                    case 11833:
                    case 9356:
                        /*if (player.getGameMode() == GameMode.SEASONAL_IRONMAN) {
                            if (player.seasonalMinigameTeleports[3] == 0) {
                                player.getPacketSender().sendMessage("You must unlock the Fight Caves teleport to unlock this.");
                                return;
                            }
                        }*/

                        FightCave.enterCave(player);
                        break;
                    case 20669:
                        player.moveTo(new Position(3577, 3282, 0));
                        break;
                    case 20671:
                        player.moveTo(new Position(3554, 3283, 0));
                        break;
                    case 20670:
                        player.moveTo(new Position(3566, 3275, 0));
                        break;
                    case 20667:
                        player.moveTo(new Position(3564, 3289, 0));
                        break;
                    case 20668:
                        player.moveTo(new Position(3574, 3298, 0));
                        break;
                    case 20672:
                        player.moveTo(new Position(3556, 3298, 0));
                        break;
				/*case 3203:
					if(player.getLocation() == Location.DUEL_ARENA && player.getDueling().duelingStatus == 5) {
						if(Dueling.checkRule(player, DuelRule.NO_FORFEIT)) {
							player.getPacketSender().sendMessage("Forfeiting has been disabled in this duel.");
							return;
						}
						player.getCombatBuilder().reset(true);
						if(player.getDueling().duelingWith > -1) {
							Player duelEnemy = World.getPlayers().get(player.getDueling().duelingWith);
							if(duelEnemy == null)
								return;
							duelEnemy.getCombatBuilder().reset(true);
							duelEnemy.getMovementQueue().reset();
							duelEnemy.getDueling().duelVictory();
						}
						player.moveTo(new Position(3368 + RandomUtility.getRandom(5), 3267+ RandomUtility.getRandom(3), 0));
						player.getDueling().reset();
						player.getCombatBuilder().reset(true);
						player.restart();
					}
					break;*/

                    case 1738:
                        if (player.getLocation() == Location.LUMBRIDGE && player.getPosition().getZ() == 0) {
                            player.moveTo(new Position(player.getPosition().getX(), player.getPosition().getY(), 1));
                        } else {
                            player.moveTo(new Position(2840, 3539, 2));
                        }
                        break;
                    case 15638:
                        player.moveTo(new Position(2840, 3539, 0));
                        break;
                    case 15644:
                    case 15641:
                    case 16671:
                        break;
                    case 28714:
                        player.performAnimation(new Animation(828));
                        player.delayedMoveTo(new Position(3089, 3492), 2);
                        break;
                    case 1746:
                        player.performAnimation(new Animation(827));
                        player.moveTo(new Position(2917, 4384, 0));  //Corporeal Beast
                        //player.performAnimation(new Animation(827));
                        //player.delayedMoveTo(new Position(2209, 5348), 2);  //Summoning Obelisk
                        break;
                    case 19191:
                    case 19189:
                    case 19180:
                    case 19184:
                    case 19182:
                    case 19178:
                        Hunter.lootTrap(player, gameObject);
                        break;
                    case 3192:
                        player.setDialogueActionId(11);
                        DialogueManager.start(player, 20);
                        break;
                    case 28716:
                        if (!player.busy()) {
                            player.getSkillManager().updateSkill(Skill.SUMMONING);
                            player.getPacketSender().sendInterface(63471);
                        } else
                            player.getPacketSender().sendMessage("Please finish what you're doing before opening this.");
                        break;
                    case 6:
                        DwarfCannon cannon = player.getCannon();
                        if (cannon == null || cannon.getOwnerIndex() != player.getIndex()) {
                            player.getPacketSender().sendMessage("This is not your cannon!");
                        } else {
                            DwarfMultiCannon.startFiringCannon(player, cannon);
                        }
                        break;
                    case 2:
                        player.moveTo(new Position(player.getPosition().getX() > 2690 ? 2687 : 2694, 3714));
                        player.getPacketSender().sendMessage("You walk through the entrance..");
                        break;
                    case 2026:
                    case 2028:
                    case 2029:
                    case 2030:
                    case 2031:
                        player.setEntityInteraction(gameObject);
                        Fishing.setupFishing(player, Fishing.forSpot(gameObject.getId(), false));
                        return;
                    case 12692:
                    case 2783:
                    case 4306:
                    case 302097:
                    case 2097:
                    case 306150:
                    case 6150:
                        player.setInteractingObject(gameObject);
                        EquipmentMaking.handleAnvil(player);

                        break;
                    case 2732:
                        EnterAmountOfLogsToAdd.openInterface(player);
                        break;
                    case 9727: //Tutorial Dungeon Ladder
                        player.moveTo(new Position(3139, 3087, 0));
                        player.tutorialIsland = 10;
                        DialogueManager.start(player, 1018);
                        break;
                    case 409:
                    case 4008:
                    case 27661:
                    case 2640:
                    case 4859:
                    case 36972:
                        player.performAnimation(new Animation(645));
                        if (player.getSkillManager().getCurrentLevel(Skill.PRAYER) < player.getSkillManager().getMaxLevel(Skill.PRAYER)) {
                            player.getSkillManager().setCurrentLevel(Skill.PRAYER, player.getSkillManager().getMaxLevel(Skill.PRAYER), true);
                            player.getPacketSender().sendMessage("You recharge your Prayer points.");
                        }
                        break;
                    case 13639://rejuvation pool
                        player.performAnimation(new Animation(645));
                        player.performGraphic(new Graphic(6));
                        player.setSpecialPercentage(100);
                        CombatSpecial.updateBar(player);

                        player.setPoisonImmunity(1000);
                        player.setDragonfireImmunity(1000);

                        for (int i = 0; i <= 24; i++) {
                            if (player.getSkillManager().getCurrentLevel(Skill.forId(i)) < player.getSkillManager().getMaxLevel(i)) {
                                player.getSkillManager().setCurrentLevel(Skill.forId((i)), player.getSkillManager().getMaxLevel(Skill.forId(i)));
                            }
                        }
                        player.getPacketSender().sendMessage("You feel completely rejuvinated!");

                        if (!player.healingEffects) {
                            player.healingEffects = true;
                            player.getPacketSender().sendMessage("Your healing effects have been enabled.");
                        }

                        break;
                    case 4005: //donator pvm well

                        if (player.getStaffRights().getStaffRank() > 3 || GlobalEventHandler.effectActive(GlobalEvent.Effect.LOADED) || (player.loadedEvent && player.personalEvent)) {
                            player.performAnimation(new Animation(645));
                            player.performGraphic(new Graphic(6));
                            player.setSpecialPercentage(100);
                            CombatSpecial.updateBar(player);

                            if (player.boostedDivinePool)
                                player.overloadPlus = true;

                            if (GlobalEventHandler.effectActive(GlobalEvent.Effect.LOADED) || (player.loadedEvent && player.personalEvent))
                                player.setOverloadPotionTimer(3000);
                            else if (player.Survivalist == 4)
                                player.setOverloadPotionTimer(3000);
                            else if (player.Survivalist == 3)
                                player.setOverloadPotionTimer(2100);
                            else if (player.Survivalist == 2)
                                player.setOverloadPotionTimer(1500);
                            else if (player.Survivalist == 1)
                                player.setOverloadPotionTimer(900);
                            else
                                player.setOverloadPotionTimer(600);

                            player.setPoisonImmunity(1000);
                            player.setDragonfireImmunity(1000);

                            for (int i = 0; i <= 24; i++) {
                                if (player.getSkillManager().getCurrentLevel(Skill.forId(i)) < player.getSkillManager().getMaxLevel(i)) {
                                    player.getSkillManager().setCurrentLevel(Skill.forId((i)), player.getSkillManager().getMaxLevel(Skill.forId(i)));
                                }
                            }
                            player.getSkillManager().setCurrentLevel(Skill.CONSTITUTION, player.maxHealth());
                            player.getPacketSender().sendMessage("You feel completely rejuvinated!");

                            if (!player.healingEffects) {
                                player.healingEffects = true;
                                player.getPacketSender().sendMessage("Your healing effects have been enabled.");
                            }
                        } else {
                            player.getPacketSender().sendMessage("You must be a level 4 donator to use this.");
                        }
                        break;
                    case 8749:

                        player.setSpecialPercentage(100);
                        CombatSpecial.updateBar(player);
                        player.getPacketSender().sendMessage("Your special attack energy has been restored.");
                        player.performGraphic(new Graphic(1302));
                        break;
                    case 411:
                        if (player.getSkillManager().getMaxLevel(Skill.DEFENCE) < 30) {
                            player.getPacketSender().sendMessage("You need a Defence level of at least 30 to use this altar.");
                            return;
                        }

                        if (player.getGameMode() == GameMode.SEASONAL_IRONMAN) {
                            if (!player.Ruinous) {
                                player.getPacketSender().sendMessage("You must unlock Ruinous to use this altar.");
                                return;
                            }
                        }

                        player.performAnimation(new Animation(645));
                        if (player.getPrayerbook() == Prayerbook.NORMAL) {
                            player.getPacketSender().sendMessage("You sense a surge of power flow through your body!");
                            player.setPrayerbook(Prayerbook.CURSES);
                        } else {
                            player.getPacketSender().sendMessage("You sense a surge of purity flow through your body!");
                            player.setPrayerbook(Prayerbook.NORMAL);
                        }
                        player.getPacketSender().sendTabInterface(GameSettings.PRAYER_TAB, player.getPrayerbook().getInterfaceId());
                        PrayerHandler.deactivateAll(player);
                        CurseHandler.deactivateAll(player);


                        break;
                    case 6552:
                        if (player.getGameMode() == GameMode.SEASONAL_IRONMAN) {
                            if (!player.Summoner) {
                                player.getPacketSender().sendMessage("You must unlock Summoner to use this altar.");
                                return;
                            }
                        }

                        player.performAnimation(new Animation(645));
                        player.setSpellbook(player.getSpellbook() == MagicSpellbook.ANCIENT ? MagicSpellbook.NORMAL : MagicSpellbook.ANCIENT);
                        player.getPacketSender().sendTabInterface(GameSettings.MAGIC_TAB, player.getSpellbook().getInterfaceId()).sendMessage("Your magic spellbook is changed..");
                        Autocasting.resetAutocast(player, true);
                        player.setAutocastSpell(null);

                        break;

                    /**
                     * Theatre of Blood (Raids 2)
                     */
                    case 32653: // ToB Entrance

                        /*if (player.getGameMode() == GameMode.SEASONAL_IRONMAN) {
                            if (player.seasonalRaidsTeleports[3] == 0) {
                                player.getPacketSender().sendMessage("You must unlock the Theatre of Blood teleport to unlock this.");
                                return;
                            }
                        }*/

                        if (player.getMinigameAttributes().getRaidsAttributes().getParty() == null) {
                            player.sendMessage("You need to be in a party to enter the Theatre of Blood.");
                            return;
                        }
                        if (player.getMinigameAttributes().getRaidsAttributes().getParty().getOwner() != player) {
                            player.sendMessage("Only the party leader can begin the Theatre of Blood.");
                            return;
                        }
                        TobRaid.start(player);
                        //player.getPacketSender().sendMessage("Theatre of Blood is temporarily under construction.");
                        break;

                    /**
                     * Chambers of Xeric (Raids 1)
                     */
                    case 29777: // new olm entrace
                        if (player.getMinigameAttributes().getRaidsAttributes().getParty() == null) {
                            player.sendMessage("You need to be in a party to battle the Great Olm.");
                            return;
                        }
                        if (player.getMinigameAttributes().getRaidsAttributes().getParty().getOwner() != player) {
                            player.sendMessage("Only the party leader can initiate the Great Olm fight.");
                            return;
                        }

                        /*if (player.getGameMode() == GameMode.SEASONAL_IRONMAN) {
                            if (player.seasonalRaidsTeleports[1] == 0) {
                                player.getPacketSender().sendMessage("You must unlock the Chambers of Xeric teleport to unlock this.");
                                return;
                            }
                        }*/

                        CoxRaid.start(player);
                        //GreatOlm.start(player);
                        break;
                    case 30028:
                        if (player.getCoxRaidsLoot() == null && player.getCoxRaidsLootSecond() == null) {
                            return;
                        }
                        if (player.getInventory().getFreeSlots() < 3) {
                            player.sendMessage("Please have 3 free slots before looting the chest!");
                            return;
                        }
                        if (player.getCoxRaidsLoot() != null) {
                            player.getInventory().add(player.getCoxRaidsLoot());
                            player.setCoxRaidsLoot(null);
                        }
                        if (player.getCoxRaidsLootSecond() != null) {
                            player.getInventory().add(player.getCoxRaidsLootSecond());
                            player.setCoxRaidsLootSecond(null);
                        }

                        player.sendMessage("@red@You have looted the chest!");

                        break;
                    case 29879: // olm
                        if (player.getRaidsParty() == null) {
                            player.sendMessage("You must be inside a party to do this.");
                            return;
                        }
                        int olmX = player.getPosition().getX();
                        if (olmX <= 3231)
                            olmX = 3231;
                        if (olmX >= 3234)
                            olmX = 3234;
                        if (player.getPosition().getY() < gameObject.getPosition().getY()) {
                            player.moveTo(new Position(olmX, 5730, player.getPosition().getZ()));
                            player.getMinigameAttributes().getRaidsAttributes().setInsideRaid(true);
                            player.getPacketSender().sendInteractionOption("null", 2, true);
                            player.getRaidsParty().getPlayersInRaids().add(player);
                        } else {
                            player.sendMessage("The only way to leave is to kill or be killed!");
                        }
                        break;
                    case 29996: // olm
                        player.moveTo(new Position(1254, 3571));
                        player.getPacketSender().sendInteractionOption("Invite", 2, true);

                        player.getMinigameAttributes().getRaidsAttributes().setInsideRaid(false);
                        if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null)
                            player.getMinigameAttributes().getRaidsAttributes().getParty().remove(player, false,
                                    true);
                        break;
                    case 29778: // olm
                        player.moveTo(new Position(1254, 3571));
                        player.getPacketSender().sendInteractionOption("Invite", 2, true);

                        player.getMinigameAttributes().getRaidsAttributes().setInsideRaid(false);
                        if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null)
                            player.getMinigameAttributes().getRaidsAttributes().getParty().remove(player, false,
                                    true);
                        break;
                    case 30203: // olm
                        player.moveTo(GameSettings.DEFAULT_POSITION);
                        player.getMinigameAttributes().getRaidsAttributes().setInsideRaid(false);
                        if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null)
                            player.getMinigameAttributes().getRaidsAttributes().getParty().remove(player, false,
                                    true);
                        break;

                    case 38426:
                    case 39547:
                        TeleportHandler.teleportPlayer(player, new Position(3172, 5727, 0), TeleportType.NORMAL);
                        break;

                    case 39549:
                        TeleportHandler.teleportPlayer(player, new Position(3096, 3478, 0), TeleportType.NORMAL);
                        break;

                    case 9706:
                        if (player.getTeleblockTimer() < 1)
                            player.moveTo(new Position(3105, 3951, 0));
                        else
                            player.getPacketSender().sendMessage("@red@You can't use this while teleblocked!");
                        break;
                    case 9707:
                        if (player.getTeleblockTimer() < 1)
                            player.moveTo(new Position(3105, 3956, 0));
                        else
                            player.getPacketSender().sendMessage("@red@You can't use this while teleblocked!");
                        break;


                    case 5960: //Levers
                    case 5959:

                        if (player.getLocation() == Location.WILDERNESS) {
                            if (player.WildyPoints >= 25) {

                                player.setDirection(Direction.WEST);
                                player.moveTo(new Position(3090, 3475));
                                player.WildyPoints -= 25;
                                player.getPacketSender().sendMessage("@or2@You consume 25 Wildy Points to use the Lever.");
                            } else {
                                player.getPacketSender().sendMessage("@red@You need to have at least 25 Wildy Points to use the Lever..");
                            }
                        }
                        //TeleportHandler.teleportPlayer(player, new Position(3090, 3475), TeleportType.LEVER);
                        break;


                    case 2465://Mole
                        if (player.getGameMode() == GameMode.SEASONAL_IRONMAN && player.seasonalBossTeleports[4] == 0) {
                            player.getPacketSender().sendMessage("@red@You have to unlock this boss teleport to use this instance!");
                            break;
                        }
                        player.inSaradomin = false;
                        player.inZamorak = false;
                        player.inArmadyl = false;
                        player.inBandos = false;
                        player.inCerberus = false;
                        player.inSire = false;
                        player.inThermo = false;
                        player.inCorporeal = false;
                        player.inKBD = false;
                        player.inDKS = false;
                        player.inKQ = false;
                        player.inMole = false;

                        player.inMole = true;
                        DialogueManager.start(player, 229);
                        player.setDialogueActionId(229);
                        break;
                    case 2466://KQ
                        if (player.getGameMode() == GameMode.SEASONAL_IRONMAN && player.seasonalBossTeleports[9] == 0) {
                            player.getPacketSender().sendMessage("@red@You have to unlock this boss teleport to use this instance!");
                            break;
                        }
                        player.inSaradomin = false;
                        player.inZamorak = false;
                        player.inArmadyl = false;
                        player.inBandos = false;
                        player.inCerberus = false;
                        player.inSire = false;
                        player.inThermo = false;
                        player.inCorporeal = false;
                        player.inKBD = false;
                        player.inDKS = false;
                        player.inKQ = false;
                        player.inMole = false;

                        player.inKQ = true;
                        DialogueManager.start(player, 229);
                        player.setDialogueActionId(229);
                        break;
                    case 2467://DKS

                        if (player.getGameMode() == GameMode.SEASONAL_IRONMAN && player.seasonalBossTeleports[3] == 0) {
                            player.getPacketSender().sendMessage("@red@You have to unlock this boss teleport to use this instance!");
                            break;
                        }
                        player.inSaradomin = false;
                        player.inZamorak = false;
                        player.inArmadyl = false;
                        player.inBandos = false;
                        player.inCerberus = false;
                        player.inSire = false;
                        player.inThermo = false;
                        player.inCorporeal = false;
                        player.inKBD = false;
                        player.inDKS = false;
                        player.inKQ = false;
                        player.inMole = false;

                        player.inDKS = true;
                        DialogueManager.start(player, 229);
                        player.setDialogueActionId(229);
                        break;
                    case 2468://Corporeal Beast

                        if (player.getGameMode() == GameMode.SEASONAL_IRONMAN && player.seasonalBossTeleports[2] == 0) {
                            player.getPacketSender().sendMessage("@red@You have to unlock this boss teleport to use this instance!");
                            break;
                        }
                        player.inSaradomin = false;
                        player.inZamorak = false;
                        player.inArmadyl = false;
                        player.inBandos = false;
                        player.inCerberus = false;
                        player.inSire = false;
                        player.inThermo = false;
                        player.inCorporeal = false;
                        player.inKBD = false;
                        player.inDKS = false;
                        player.inKQ = false;
                        player.inMole = false;

                        player.inCorporeal = true;
                        DialogueManager.start(player, 229);
                        player.setDialogueActionId(229);
                        break;
                    case 2469://KBD

                        if(player.getGameMode() == GameMode.SEASONAL_IRONMAN && player.seasonalBossTeleports[6] == 0) {
                            player.getPacketSender().sendMessage("@red@You have to unlock this boss teleport to use this instance!");
                            break;
                        }
                    player.inSaradomin = false;
                    player.inZamorak = false;
                    player.inArmadyl = false;
                    player.inBandos = false;
                    player.inCerberus = false;
                    player.inSire = false;
                    player.inThermo = false;
                    player.inCorporeal = false;
                    player.inKBD = false;
                    player.inDKS = false;
                    player.inKQ = false;
                    player.inMole = false;

                    player.inKBD = true;
                    DialogueManager.start(player, 229);
                    player.setDialogueActionId(229);
                    break;
                    case 2470://Cerb

                        if (player.getGameMode() == GameMode.SEASONAL_IRONMAN && player.seasonalBossTeleports[1] == 0) {
                            player.getPacketSender().sendMessage("@red@You have to unlock this boss teleport to use this instance!");
                            break;
                        }
                        player.inSaradomin = false;
                        player.inZamorak = false;
                        player.inArmadyl = false;
                        player.inBandos = false;
                        player.inCerberus = false;
                        player.inSire = false;
                        player.inThermo = false;
                        player.inCorporeal = false;
                        player.inKBD = false;
                        player.inDKS = false;
                        player.inKQ = false;
                        player.inMole = false;

                        player.inCerberus = true;
                        DialogueManager.start(player, 229);
                        player.setDialogueActionId(229);
                        break;
                    case 2471://Sire

                        if (player.getGameMode() == GameMode.SEASONAL_IRONMAN && player.seasonalBossTeleports[0] == 0) {
                            player.getPacketSender().sendMessage("@red@You have to unlock this boss teleport to use this instance!");
                            break;
                        }
                        player.inSaradomin = false;
                        player.inZamorak = false;
                        player.inArmadyl = false;
                        player.inBandos = false;
                        player.inCerberus = false;
                        player.inSire = false;
                        player.inThermo = false;
                        player.inCorporeal = false;
                        player.inKBD = false;
                        player.inDKS = false;
                        player.inKQ = false;
                        player.inMole = false;

                        player.inSire = true;
                        DialogueManager.start(player, 229);
                        player.setDialogueActionId(229);
                        break;
                    case 2472://Thermo

                        if (player.getGameMode() == GameMode.SEASONAL_IRONMAN && player.seasonalBossTeleports[7] == 0) {
                            player.getPacketSender().sendMessage("@red@You have to unlock this boss teleport to use this instance!");
                            break;
                        }
                        player.inSaradomin = false;
                        player.inZamorak = false;
                        player.inArmadyl = false;
                        player.inBandos = false;
                        player.inCerberus = false;
                        player.inSire = false;
                        player.inThermo = false;
                        player.inCorporeal = false;
                        player.inKBD = false;
                        player.inDKS = false;
                        player.inKQ = false;
                        player.inMole = false;

                        player.inThermo = true;
                        DialogueManager.start(player, 229);
                        player.setDialogueActionId(229);
                        break;
                    case 2473://Kree'arra

                        if (player.getGameMode() == GameMode.SEASONAL_IRONMAN && player.seasonalBossTeleports[5] == 0) {
                            player.getPacketSender().sendMessage("@red@You have to unlock this boss teleport to use this instance!");
                            break;
                        }
                        player.inSaradomin = false;
                        player.inZamorak = false;
                        player.inArmadyl = false;
                        player.inBandos = false;
                        player.inCerberus = false;
                        player.inSire = false;
                        player.inThermo = false;
                        player.inCorporeal = false;
                        player.inKBD = false;
                        player.inDKS = false;
                        player.inKQ = false;
                        player.inMole = false;

                        player.inArmadyl = true;
                        DialogueManager.start(player, 229);
                        player.setDialogueActionId(229);
                        break;
                    case 2474://Graar'dor

                        if (player.getGameMode() == GameMode.SEASONAL_IRONMAN && player.seasonalBossTeleports[5] == 0) {
                            player.getPacketSender().sendMessage("@red@You have to unlock this boss teleport to use this instance!");
                            break;
                        }
                        player.inSaradomin = false;
                        player.inZamorak = false;
                        player.inArmadyl = false;
                        player.inBandos = false;
                        player.inCerberus = false;
                        player.inSire = false;
                        player.inThermo = false;
                        player.inCorporeal = false;
                        player.inKBD = false;
                        player.inDKS = false;
                        player.inKQ = false;
                        player.inMole = false;

                        player.inBandos = true;
                        DialogueManager.start(player, 229);
                        player.setDialogueActionId(229);
                        break;
                    case 2475://K'ril

                        if (player.getGameMode() == GameMode.SEASONAL_IRONMAN && player.seasonalBossTeleports[5] == 0) {
                            player.getPacketSender().sendMessage("@red@You have to unlock this boss teleport to use this instance!");
                            break;
                        }
                        player.inSaradomin = false;
                        player.inZamorak = false;
                        player.inArmadyl = false;
                        player.inBandos = false;
                        player.inCerberus = false;
                        player.inSire = false;
                        player.inThermo = false;
                        player.inCorporeal = false;
                        player.inKBD = false;
                        player.inDKS = false;
                        player.inKQ = false;
                        player.inMole = false;

                        player.inZamorak = true;
                        DialogueManager.start(player, 229);
                        player.setDialogueActionId(229);
                        break;
                    case 2476://Zilyana

                        if (player.getGameMode() == GameMode.SEASONAL_IRONMAN && player.seasonalBossTeleports[5] == 0) {
                            player.getPacketSender().sendMessage("@red@You have to unlock this boss teleport to use this instance!");
                            break;
                        }
                        player.inSaradomin = false;
                        player.inZamorak = false;
                        player.inArmadyl = false;
                        player.inBandos = false;
                        player.inCerberus = false;
                        player.inSire = false;
                        player.inThermo = false;
                        player.inCorporeal = false;
                        player.inKBD = false;
                        player.inDKS = false;
                        player.inKQ = false;
                        player.inMole = false;

                        player.inSaradomin = true;
                        DialogueManager.start(player, 229);
                        player.setDialogueActionId(229);
                        break;


                    case 13179:
                        if (player.getSkillManager().getMaxLevel(Skill.DEFENCE) < 40) {
                            player.getPacketSender().sendMessage("You need a Defence level of at least 40 to use this altar.");
                            return;
                        } else {
                            player.performAnimation(new Animation(645));
                            player.setSpellbook(player.getSpellbook() == MagicSpellbook.LUNAR ? MagicSpellbook.NORMAL : MagicSpellbook.LUNAR);
                            player.getPacketSender().sendTabInterface(GameSettings.MAGIC_TAB, player.getSpellbook().getInterfaceId()).sendMessage("Your magic spellbook is changed..");
                            Autocasting.resetAutocast(player, true);
                            player.setAutocastSpell(null);
                        }
                        break;
                    case 172:
                        CrystalChest.handleChest(player, gameObject);
                        break;
                    case 4122:
                    case 34829:
                    case 34828:
                        LarranChest.openChest(player, gameObject);
                        break;
                    case 590:
                    case 300590:
                        player.getPacketSender().sendMessage("Use an item on this table to exchange it.");
                        break;
                    case 40383:
                    case 340383:
                        MagicalPumpkin.takeCandy(player);
                        break;

                    case 29312:

                        if (player.getInventory().contains(590) || player.getInventory().contains(2946)) {
                            Wintertodt.lightBrazier(player, player.getInteractingObject());
                        }

                        break;

                    case 29313:
                        if (player.getInventory().contains(2347)) {
                            Wintertodt.fixBrazier(player, player.getInteractingObject());
                        }
                        break;

                    case 29314:
                        if (player.getInventory().contains(220696)) {
                            Wintertodt.feedBrazier(player, player.getInteractingObject());
                        } else
                            player.getPacketSender().sendMessage("You need kindling to feed the lit brazier!");
                        break;


                    case 6910:
                    case 4483:
                    case 3193:
                    case 2213:
                    case 11758:
                    case 14367:
                    case 42192:
                    case 75:
                    case 10355:
                    case 10583:
                    case 28861:
                    case 27718:
                    case 27719:
                    case 27720:
                    case 27721:
                    case 30087:
                    case 32666:
                    case 36086:
                    case 26707:
                    case 329321:
                    case 29321:
                    case 28595:
                    case 14886:
                    case 2693:
                    case 24101:
                    case 19051:
                    case 18491:

                        if (player.getLocation() == Location.WILDERNESS) {
                            if (player.WildyPoints >= 10) {
                                if (player.getGameMode() != GameMode.ULTIMATE_IRONMAN)
                                    player.getBank(player.getCurrentBankTab()).open();
                                player.WildyPoints -= 10;
                                player.getPacketSender().sendMessage("@or2@You consume 10 Wildy Points to use the bank chest.");
                            } else {
                                player.getPacketSender().sendMessage("@red@You need to have at least 10 Wildy Points to use the bank chest..");
                            }


                        } else {
                            if (player.getGameMode() == GameMode.ULTIMATE_IRONMAN) {
                                if (!player.getInventory().contains(211941)) {
                                    player.getInventory().add(211941, 1);
                                }
                            } else {
                                player.getBank(player.getCurrentBankTab()).open();
                            }
                        }

                        break;

                    case 4031: //shantay pass

                        if (!player.getInventory().contains(1854)) {
                            player.getPacketSender().sendMessage("You need a Shantay pass to access this.");
                        } else if (player.getInventory().contains(1854)) {

                            player.getInventory().delete(1854, 1);
                            player.moveTo(new Position(3303, 3115, 0), true);
                            //DMZ.start(player);
                            //player.getPacketSender().sendMessage("This content is not ready.");
                        }


                        break;

                    case 6189:
                    case 26814:
                    case 11666:
                    case 16469:
                    case 24009:
                    case 9086:
                        Smelting.openInterface(player);
                        break;

                    case 2216:
                        if (player.getPosition().getX() >= 2878)
                            player.moveTo(new Position(2875, player.getPosition().getY(), player.getPosition().getZ()), true);
                        else if (player.getPosition().getX() <= 2877)
                            player.moveTo(new Position(2879, player.getPosition().getY(), player.getPosition().getZ()), true);
                        break;
                    case 16511:
                        if (player.getPosition().getX() < 3150)
                            player.moveTo(new Position(3154, player.getPosition().getY(), player.getPosition().getZ()), true);
                        else if (player.getPosition().getX() > 3153)
                            player.moveTo(new Position(3149, player.getPosition().getY(), player.getPosition().getZ()), true);
                        break;
                }
            }
        }));
    }

    private static void secondClick(final Player player, Packet packet) {
        final int id = packet.readInt();
        final int y = packet.readLEShort();
        final int x = packet.readUnsignedShortA();
        final Position position = new Position(x, y, player.getPosition().getZ());
        final GameObject gameObject = new GameObject(id, position);
        if (id > 0 && id != 6 && !RegionClipping.objectExists(gameObject)) {
            //player.getPacketSender().sendMessage("An error occured. Error code: "+id).sendMessage("Please report the error to a staff member.");
            return;
        }
        if (player.getStaffRights() == StaffRights.DEVELOPER) {
            player.getPacketSender().sendMessage("Second click object id; [id, position] : [" + id + ", " + position + "]");
            player.getPacketSender().sendMessage("[Face] : " + gameObject.getFace());
        }

        player.setPositionToFace(gameObject.getPosition());
        int distanceX = (player.getPosition().getX() - position.getX());
        int distanceY = (player.getPosition().getY() - position.getY());
        if (distanceX < 0)
            distanceX = -(distanceX);
        if (distanceY < 0)
            distanceY = -(distanceY);
        int size = distanceX > distanceY ? distanceX : distanceY;
        gameObject.setSize(size);
        player.getActionTracker().offer(new ActionObjectAction(Action.ActionType.SECOND_OBJECT_ACTION, x, y, id));
        gameObject.clickObject(player, GameObjectClickType.SECOND_CLICK);
        player.setInteractingObject(gameObject).setWalkToTask(new WalkToTask(player, position, gameObject.getSize(), new FinalizedMovementTask() {
            public void execute() {
                if (MiningData.forRock(gameObject.getId()) != null) {
                    Prospecting.prospectOre(player, id);
                    return;
                }
                //if (player.getFarming().click(player, x, y, 1))
                //return;

                int objId = gameObject.getId();

                if (objId > 300000)
                    objId -= 300000;

                if (StaircaseData.forPosition(gameObject.getPosition(), true, true) != null) {
                    StaircaseData.climb(player, StaircaseData.forPosition(gameObject.getPosition(), true, true).getEndPosition());
                }

                switch (objId) {


                    case 11761:
                        player.setInputHandling(new EnterFairyCode());
                        player.getPacketSender().sendEnterInputPrompt("What's your Fairy Ring Code?");
                        break;

                    case 10061:
                        player.getTradingPostManager().open();
                        break;

                    case 4031: //shantay pass
                        player.getPacketSender().sendMessage("[Boss name here] is not currently spawned!");

                        break;

                    case 332994:
                    case 32994:

                        if (gameObject.getPosition().getX() == 1229 && gameObject.getPosition().getY() == 3557)
                            RaidChest.coxChest(player, gameObject);
                        else if (gameObject.getPosition().getX() == 3662 && gameObject.getPosition().getY() == 3218)
                            RaidChest.tobChest(player, gameObject);
                        else if (gameObject.getPosition().getX() == 2442 && gameObject.getPosition().getY() == 3092)
                            RaidChest.raidsLobbyChest(player, gameObject);
                        else if (gameObject.getPosition().getX() == 1630 && gameObject.getPosition().getY() == 3939)
                            RaidChest.gauntletChest(player, gameObject);
                        else if (gameObject.getPosition().getX() == 3082 && gameObject.getPosition().getY() == 3412)
                            RaidChest.strongholdRaidChest(player, gameObject);


                        return;

                    case 329301:
                    case 29301:
                        //player.getInventory().add(3024, 1);
                        return;

                    case 334682:
                    case 34682:
                        EquipmentUpgrades2.showInterface(player);
                        return;


                    case 4651:
                        Casino.blackjack(player);
                        break;

                    case 39674:
                        HeroesStatue.showInterface(player);
                        break;
                    case 884:
                        player.setDialogueActionId(41);
                        player.setInputHandling(new DonateToWellGoodwill());
                        player.getPacketSender().sendInterfaceRemoval().sendEnterAmountPrompt("How much money would you like to contribute with?");
                        break;
                    case 24009:
                        Smelting.openInterface(player);
                        break;
                    case 2216:
                        if (player.getPosition().getX() >= 2878)
                            player.moveTo(new Position(2875, player.getPosition().getY(), player.getPosition().getZ()), true);
                        else if (player.getPosition().getX() <= 2877)
                            player.moveTo(new Position(2879, player.getPosition().getY(), player.getPosition().getZ()), true);
                        break;

                    case 6910:
                    case 4483:
                    case 3193:
                    case 2213:
                    case 11758:
                    case 14367:
                    case 42192:
                    case 75:
                    case 10355:
                    case 10583:
                    case 28861:
                    case 27718:
                    case 27719:
                    case 27720:
                    case 27721:
                    case 25808:
                    case 32666:
                    case 24101:
                    case 18491:
                        if (player.getLocation() == Location.WILDERNESS) {
                            if (player.WildyPoints >= 10) {
                                if (player.getGameMode() != GameMode.ULTIMATE_IRONMAN)
                                    player.getBank(player.getCurrentBankTab()).open();
                                player.WildyPoints -= 10;
                                player.getPacketSender().sendMessage("@or2@You consume 10 Wildy Points to use the bank chest.");
                            } else {
                                player.getPacketSender().sendMessage("@red@You need to have at least 10 Wildy Points to use the bank chest..");
                            }


                        } else {
                            if (player.getGameMode() == GameMode.ULTIMATE_IRONMAN) {
                                if (!player.getInventory().contains(211941)) {
                                    player.getInventory().add(211941, 1);
                                }
                            } else {
                                player.getBank(player.getCurrentBankTab()).open();
                            }
                        }

                        break;

                    case 25029:
                    case 325029:
                        PuroPuro.goThroughWheat(player, gameObject);
                        break;
                    case 21505:
                    case 21507:
                        player.moveTo(new Position(2328, 3804));
                        break;
                    case 14896:

                    case 312:
                        if (!player.getClickDelay().elapsed(600))
                            return;
                        if (player.getInventory().isFull()) {
                            player.getPacketSender().sendMessage("You don't have enough free inventory space.");
                            return;
                        }
                        String type = gameObject.getId() == 312 ? "Potato" : "Flax";
                        player.performAnimation(new Animation(827));

                        int qty = 1;
                        qty = 1 + player.Accelerate;

                        if (GlobalEventHandler.effectActive(GlobalEvent.Effect.ACCELERATE) || (player.accelerateEvent && player.personalEvent))
                            qty += 2;

                        player.getInventory().add(gameObject.getId() == 312 ? 1942 : 1779, qty);
                        player.getPacketSender().sendMessage("You pick some " + type);
                        gameObject.setPickAmount(gameObject.getPickAmount() + 1);
                        if (RandomUtility.inclusiveRandom(3) == 1 && gameObject.getPickAmount() >= 1 || gameObject.getPickAmount() >= 6) {
                            player.getPacketSender().sendClientRightClickRemoval();
                            gameObject.setPickAmount(0);
                            CustomObjects.globalObjectRespawnTask(new GameObject(-1, gameObject.getPosition()), gameObject, 10);
                        }
                        player.getClickDelay().reset();
                        break;
                    case 2644:
                        Flax.showSpinInterface(player);
                        break;
                    case 6:
                        DwarfCannon cannon = player.getCannon();
                        if (cannon == null || cannon.getOwnerIndex() != player.getIndex()) {
                            player.getPacketSender().sendMessage("This is not your cannon!");
                        } else {
                            DwarfMultiCannon.pickupCannon(player, cannon, false);
                        }
                        break;
				/*case 4875:
					Stalls.stealFromStall(player, 1, 100, 18199, "You steal a banana.");
					break;
				case 4874:
					int[] jewelryloot = {1635, 1654, 1692, 11069};
					int randomjewelry = jewelryloot[RandomUtility.getRandom(jewelryloot.length-1)];
					Stalls.stealFromStall(player, 15, 250, randomjewelry, "You steal some jewelry.");
					break;
				case 4876:
					int[] generalloot = {2349, 2351, 2353, 1511, 1521, 1519};
					int randomgeneral = generalloot[RandomUtility.getRandom(generalloot.length-1)];
					Stalls.stealFromStall(player, 30, 500, randomgeneral, "You steal some basic loot.");
					break;
				case 4877:
					int[] staffloot = {1381, 1383, 1385, 1387, 1393, 1395, 1397, 1399};
					int randomstaff = staffloot[RandomUtility.getRandom(staffloot.length-1)];
					Stalls.stealFromStall(player, 50, 750, randomstaff, "You steal a staff.");
					break;
				case 4878:
					int[] scimloot = {1321, 1323, 1325, 1327, 1329, 1331, 1333, 4587};
					int randomscim = scimloot[RandomUtility.getRandom(scimloot.length-1)];
					Stalls.stealFromStall(player, 75, 1000, randomscim, "You steal a scimitar.");
					break;*/

                    case 11729: //silk
                        Stalls.stealFromStall(player, 11729);
                        break;
                    case 11730:
                        Stalls.stealFromStall(player, 11730);
                        break;
                    case 11731:
                        Stalls.stealFromStall(player, 11731);
                        break;
                    case 11732:
                        Stalls.stealFromStall(player, 11732);
                        break;
                    case 11733:
                        Stalls.stealFromStall(player, 11733);
                        break;
                    case 6189:
                    case 26814:
                    case 11666:
                    case 16469:
                    case 316469:
                        Smelting.openInterface(player);
                        break;
                    case 2152:
                        player.performAnimation(new Animation(8502));
                        player.performGraphic(new Graphic(1308));
                        player.getSkillManager().setCurrentLevel(Skill.SUMMONING, player.getSkillManager().getMaxLevel(Skill.SUMMONING));
                        player.getPacketSender().sendMessage("You renew your Summoning points.");
                        break;
                }
            }
        }));
    }

    private static void thirdClick(Player player, Packet packet) {
        final int x = packet.readLEShort();
        final int y = packet.readShort();
        final int id = packet.readInt();
        final Position position = new Position(x, y, player.getPosition().getZ());
        final GameObject gameObject = new GameObject(id, position);

        if (player.getLocation() != Location.CONSTRUCTION) {
            if (id > 0 && id != 6 && !RegionClipping.objectExists(gameObject) && id != 16510) {
                player.getPacketSender().sendMessage("An error occured. Error code: " + id).sendMessage("Please report the error to a staff member.");
                return;
            }
        }

        int distanceX = (player.getPosition().getX() - position.getX());
        int distanceY = (player.getPosition().getY() - position.getY());
        if (distanceX < 0)
            distanceX = -(distanceX);
        if (distanceY < 0)
            distanceY = -(distanceY);
        int size = distanceX > distanceY ? GameObjectDefinition.forId(id).getSizeX() : GameObjectDefinition.forId(id).getSizeY();
        if (size <= 0)
            size = 1;
        gameObject.setSize(size);
        if (player.getMovementQueue().isLockMovement())
            return;
        if (player.getStaffRights() == StaffRights.DEVELOPER) {
            player.getPacketSender().sendMessage("Third click object id; [id, position] : [" + id + ", " + position + "]");
            player.getPacketSender().sendMessage("[Face] : " + gameObject.getFace());
        }

        player.getActionTracker().offer(new ActionObjectAction(Action.ActionType.THIRD_OBJECT_ACTION, x, y, id));

        gameObject.clickObject(player, GameObjectClickType.THIRD_CLICK);

        player.setInteractingObject(gameObject).setWalkToTask(new WalkToTask(player, position, gameObject.getSize(), new FinalizedMovementTask() {
            @Override
            public void execute() {
                int objId = id;

                if (objId > 300000)
                    objId -= 300000;


                if (StaircaseData.forPosition(gameObject.getPosition(), false, true) != null) {
                    StaircaseData.climb(player, StaircaseData.forPosition(gameObject.getPosition(), false, true).getEndPosition());
                }

                switch (objId) {

                    case 10061:
                        player.getTradingPostManager().openEditor();
                        break;
                    // Mailbox
                    case 14886:
                    case 27720:
                    case 28861:
                    case 10355:
                    case 25808:
                    case 24347:
                    case 24101:
                    case 6084:
                    case 10583:
                    case 26707:
                        player.getMailBox().open();
                        break;
                }
            }
        }));
    }

    private static void fourthClick(Player player, Packet packet) {

    }

    private static void fifthClick(final Player player, Packet packet) {
        final int id = packet.readInt();
        final int y = packet.readUnsignedShortA();
        final int x = packet.readShort();
        final Position position = new Position(x, y, player.getPosition().getZ());
        final GameObject gameObject = new GameObject(id, position);
        if (!Construction.buildingHouse(player)) {
            if (id > 0 && !RegionClipping.objectExists(gameObject)) {
                //player.getPacketSender().sendMessage("An error occured. Error code: "+id).sendMessage("Please report the error to a staff member.");
                return;
            }
        }
        player.setPositionToFace(gameObject.getPosition());
        int distanceX = (player.getPosition().getX() - position.getX());
        int distanceY = (player.getPosition().getY() - position.getY());
        if (distanceX < 0)
            distanceX = -(distanceX);
        if (distanceY < 0)
            distanceY = -(distanceY);
        int size = distanceX > distanceY ? distanceX : distanceY;
        gameObject.setSize(size);
        player.setInteractingObject(gameObject);
        player.setWalkToTask(new WalkToTask(player, position, gameObject.getSize(), new FinalizedMovementTask() {
            @Override
            public void execute() {
                switch (id) {
                }
                player.getActionTracker().offer(new ActionObjectAction(Action.ActionType.FIFTH_OBJECT_ACTION, x, y, id));
                Construction.handleFifthObjectClick(x, y, id, player);
            }
        }));
    }

    @Override
    public void handleMessage(Player player, Packet packet) {
        if (player.isTeleporting() || player.isPlayerLocked() || player.getMovementQueue().isLockMovement())
            return;


        switch (packet.getOpcode()) {
            case FIRST_CLICK:
                firstClick(player, packet);
                break;
            case SECOND_CLICK:
                secondClick(player, packet);
                break;
            case THIRD_CLICK:
                thirdClick(player, packet);
                break;
            case FOURTH_CLICK:
                fourthClick(player, packet);
                break;
            case FIFTH_CLICK:
                fifthClick(player, packet);
                break;
        }
    }
}
