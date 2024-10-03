package com.arlania.model;

import com.arlania.GameSettings;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.PlayerPunishment.Jail;
import com.arlania.world.content.combat.CombatFactory;
import com.arlania.world.content.combat.prayer.CurseHandler;
import com.arlania.world.content.combat.prayer.PrayerHandler;
import com.arlania.world.content.droppreview.NEXX;
import com.arlania.world.content.interfaces.GambleInterface;
import com.arlania.world.content.minigames.MinigameAttributes;
import com.arlania.world.content.minigames.impl.*;
import com.arlania.world.content.minigames.impl.barrows.Minigame;
import com.arlania.world.content.minigames.impl.barrows.Raid;
import com.arlania.world.content.minigames.impl.chambersofxeric.CoxRaid;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.Olm;
import com.arlania.world.content.minigames.impl.chaosraids.ChaosRaid;
import com.arlania.world.content.minigames.impl.chaosraids.ChaosRewards;
import com.arlania.world.content.minigames.impl.gauntlet.GauntletRaid;
import com.arlania.world.content.minigames.impl.gwdraids.GwdRaid;
import com.arlania.world.content.minigames.impl.gwdraids.GwdrRewards;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.content.minigames.impl.skilling.Wintertodt;
import com.arlania.world.content.minigames.impl.strongholdraids.Mobs;
import com.arlania.world.content.minigames.impl.theatreofblood.TobRaid;
import com.arlania.world.content.skill.impl.slayer.SuperiorSlayer;
import com.arlania.world.entity.Entity;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.GroundItemManager;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class Locations {

    public static int PLAYERS_IN_WILD;
    public static int PLAYERS_IN_DUEL_ARENA;

    public static void login(Player player) {
        player.setLocation(Location.getLocation(player));
        player.getLocation().login(player);
        player.getLocation().enter(player);
    }

    public static void logout(Player player) {
        player.getLocation().logout(player);
        if (player.getRegionInstance() != null)
            player.getRegionInstance().destruct();
    }

    public static void process(Character gc) {
        Location newLocation = Location.getLocation(gc);
        if (gc.getLocation() == newLocation) {
            if (gc.isPlayer()) {
                Player player = (Player) gc;
                gc.getLocation().process(player);
                if (Location.inMulti(player)) {
                    if (player.getMultiIcon() != 1)
                        player.getPacketSender().sendMultiIcon(1);
                } else if (player.getMultiIcon() == 1)
                    player.getPacketSender().sendMultiIcon(0);
            }
        } else {
            Location prev = gc.getLocation();
            if (gc.isPlayer()) {
                Player player = (Player) gc;
                if (player.getMultiIcon() > 0)
                    player.getPacketSender().sendMultiIcon(0);
                if (player.walkableInterfaceList.size() > 0)
                    //player.getPacketSender().sendWalkableInterface(-1);
                    player.resetInterfaces();
                if (player.getPlayerInteractingOption() != PlayerInteractingOption.NONE)
                    player.getPacketSender().sendInteractionOption("null", 2, true);
            }
            gc.setLocation(newLocation);
            if (gc.isPlayer()) {
                prev.leave(((Player) gc));
                gc.getLocation().enter(((Player) gc));
            }
        }
    }

    public static boolean goodDistance(int objectX, int objectY, int playerX,
                                       int playerY, int distance) {
        if (playerX == objectX && playerY == objectY)
            return true;
        for (int i = 0; i <= distance; i++) {
            for (int j = 0; j <= distance; j++) {
                if ((objectX + i) == playerX
                        && ((objectY + j) == playerY
                        || (objectY - j) == playerY || objectY == playerY)) {
                    return true;
                } else if ((objectX - i) == playerX
                        && ((objectY + j) == playerY
                        || (objectY - j) == playerY || objectY == playerY)) {
                    return true;
                } else if (objectX == playerX
                        && ((objectY + j) == playerY
                        || (objectY - j) == playerY || objectY == playerY)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean goodDistance(Position pos1, Position pos2, int distanceReq) {
        if (pos1.getZ() != pos2.getZ())
            return false;
        return goodDistance(pos1.getX(), pos1.getY(), pos2.getX(), pos2.getY(), distanceReq);
    }

    public static int distanceTo(Position position, Position destination,
                                 int size) {
        final int x = position.getX();
        final int y = position.getY();
        final int otherX = destination.getX();
        final int otherY = destination.getY();
        int distX, distY;
        if (x < otherX)
            distX = otherX - x;
        else if (x > otherX + size)
            distX = x - (otherX + size);
        else
            distX = 0;
        if (y < otherY)
            distY = otherY - y;
        else if (y > otherY + size)
            distY = y - (otherY + size);
        else
            distY = 0;
        if (distX == distY)
            return distX + 1;
        return distX > distY ? distX : distY;
    }

    public enum Location {

        //TODO: Combine all raids lobbies. Add ToB/CoX locations to this

        RAIDS_LOBBY(new int[]{2433, 2450, 1218, 1262, 3644, 3694, 3070, 3100}, new int[]{3080, 3100, 3536, 3579, 3193, 3243, 3400, 3430}, true, true, true, false,
                true, false) {
            @Override
            public boolean canTeleport(Player player) {
                return true;
            }

            @Override
            public void enter(Player player) {

                player.getPacketSender().sendTabInterface(GameSettings.OPTIONS_TAB, 65000).sendTab(GameSettings.OPTIONS_TAB);

                int id = 65016;
                for (int i = 65016; i < 65064; i++) {
                    id++;
                    player.getPacketSender().sendString(id++, "");
                    player.getPacketSender().sendString(id++, "");
                    player.getPacketSender().sendString(id++, "");
                }
                player.getPacketSender().sendString(65009, "Create");
                player.getPacketSender().sendString(65002, "Raiding Party: @whi@0");
                player.getPacketSender().sendInteractionOption("Invite", 2, true);

                if(player.getRaidsParty() != null) {
                    MinigameAttributes.RaidsAttributes raidsAttributes = player.getMinigameAttributes().getRaidsAttributes();
                    raidsAttributes.setInsideRaid(false);
                }
            }

            @Override
            public void logout(Player player) {

                if ((player.getPosition().getX() >= 3081 && player.getPosition().getX() <= 3085) &&
                        (player.getPosition().getY() >= 3412 && player.getPosition().getY() <= 3415)) {
                    player.moveTo(new Position(3082, 3419, 0));
                }

            }

            @Override
            public boolean handleKilledNPC(Player killer, NPC npc) {
                return true;
            }

            @Override
            public void leave(Player player) {

            }

            @Override
            public void process(Player player) {
                if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null)
                    player.getMinigameAttributes().getRaidsAttributes().getParty().refreshInterface();
            }

            @Override
            public void onDeath(Player player) {
                leave(player);
            }
        },

        TEKTON(new int[]{3260, 3300}, new int[]{5310, 5350}, true, true, false, false, false, false) {
            @Override
            public boolean canTeleport(Player player) {
                //player.getPacketSender().sendMessage("@red@You cannot teleport out of here!");
                return true;
            }

            @Override
            public void enter(Player player) {

                //player.getPacketSender().sendTabInterface(GameSettings.OPTIONS_TAB, 65000).sendTab(GameSettings.OPTIONS_TAB);

                int id = 65016;
                for (int i = 65016; i < 65064; i++) {
                    id++;
                    player.getPacketSender().sendString(id++, "");
                    player.getPacketSender().sendString(id++, "");
                    player.getPacketSender().sendString(id++, "");
                }
                player.getPacketSender().sendString(65009, "Create");
                player.getPacketSender().sendString(65002, "Raiding Party: @whi@0");
                //player.getPacketSender().sendInteractionOption("Invite", 2, true);
            }

            @Override
            public void logout(Player player) {
                leave(player);
            }

            @Override
            public boolean handleKilledNPC(Player killer, NPC npc) {
                CoxRaid.handleNPCDeath(killer, npc);
                return true;
            }

            @Override
            public void leave(Player player) {
                MinigameAttributes.RaidsAttributes raidsAttributes = player.getMinigameAttributes().getRaidsAttributes();
                if (player.getLocation() != SKELETAL_MYSTICS) {
                    player.getPacketSender().sendCameraNeutrality();

                    if (player.getRegionInstance() != null)
                        player.getRegionInstance().destruct();

                    if (raidsAttributes.getParty() != null)
                        raidsAttributes.getParty().remove(player, true, true);

                    raidsAttributes.setInsideRaid(false);
                    if (raidsAttributes.getParty() != null)
                        raidsAttributes.getParty().getPlayersInRaids().remove(player);


                    player.coxWave = 0;
                    player.getMovementQueue().setLockMovement(false);
                    player.getPacketSender().sendTabInterface(GameSettings.ACHIEVEMENT_TAB, 45000);
                    player.getPacketSender().sendDungeoneeringTabIcon(false);
                    player.moveTo(new Position(1234, 3558, 0));

                }
                World.getNpcs().forEach(n -> n.removeInstancedNpcs(TEKTON, player.getPosition().getZ()));

            }

            @Override
            public void process(Player player) {
                if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null)
                    player.getMinigameAttributes().getRaidsAttributes().getParty().refreshInterface();

                if (player.getMinigameAttributes().getRaidsAttributes().getParty() == null)
                    leave(player);
            }

            @Override
            public void onDeath(Player player) {
                leave(player);
            }

        },
        SKELETAL_MYSTICS(new int[]{3280, 3340}, new int[]{5270, 5330}, true, true, false, false, false, false) {
            @Override
            public boolean canTeleport(Player player) {
                //player.getPacketSender().sendMessage("@red@You cannot teleport out of here!");
                return true;
            }

            @Override
            public void enter(Player player) {


                //player.getPacketSender().sendTabInterface(GameSettings.OPTIONS_TAB, 65000).sendTab(GameSettings.OPTIONS_TAB);

                int id = 65016;
                for (int i = 65016; i < 65064; i++) {
                    id++;
                    player.getPacketSender().sendString(id++, "");
                    player.getPacketSender().sendString(id++, "");
                    player.getPacketSender().sendString(id++, "");
                }
                player.getPacketSender().sendString(65009, "Create");
                player.getPacketSender().sendString(65002, "Raiding Party: @whi@0");
                //player.getPacketSender().sendInteractionOption("Invite", 2, true);
            }

            @Override
            public void logout(Player player) {
                leave(player);
            }

            @Override
            public boolean handleKilledNPC(Player killer, NPC npc) {
                CoxRaid.handleNPCDeath(killer, npc);
                return true;
            }

            @Override
            public void leave(Player player) {
                MinigameAttributes.RaidsAttributes raidsAttributes = player.getMinigameAttributes().getRaidsAttributes();
                if (player.getLocation() != OLM) {
                    player.getPacketSender().sendCameraNeutrality();

                    if (player.getRegionInstance() != null)
                        player.getRegionInstance().destruct();

                    if (raidsAttributes.getParty() != null)
                        raidsAttributes.getParty().remove(player, true, true);

                    raidsAttributes.setInsideRaid(false);
                    if (raidsAttributes.getParty() != null)
                        raidsAttributes.getParty().getPlayersInRaids().remove(player);

                    player.coxWave = 0;
                    player.getMovementQueue().setLockMovement(false);
                    player.getPacketSender().sendTabInterface(GameSettings.ACHIEVEMENT_TAB, 45000);
                    player.getPacketSender().sendDungeoneeringTabIcon(false);
                    player.moveTo(new Position(1234, 3558, 0));

                }
                World.getNpcs().forEach(n -> n.removeInstancedNpcs(SKELETAL_MYSTICS, player.getPosition().getZ()));

            }

            @Override
            public void process(Player player) {
                if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null)
                    player.getMinigameAttributes().getRaidsAttributes().getParty().refreshInterface();

                if (player.getMinigameAttributes().getRaidsAttributes().getParty() == null)
                    leave(player);
            }

            @Override
            public void onDeath(Player player) {
                leave(player);
            }

        },

        OLM(new int[]{3200, 3280}, new int[]{5690, 5770}, true, true, false, false, false, false) {
            @Override
            public boolean canTeleport(Player player) {
                //player.getPacketSender().sendMessage("@red@You cannot teleport out of here!");
                return true;
            }

            @Override
            public void enter(Player player) {


                //player.getPacketSender().sendTabInterface(GameSettings.OPTIONS_TAB, 65000).sendTab(GameSettings.OPTIONS_TAB);

                int id = 65016;
                for (int i = 65016; i < 65064; i++) {
                    id++;
                    player.getPacketSender().sendString(id++, "");
                    player.getPacketSender().sendString(id++, "");
                    player.getPacketSender().sendString(id++, "");
                }
                player.getPacketSender().sendString(65009, "Create");
                player.getPacketSender().sendString(65002, "Raiding Party: @whi@0");
                //player.getPacketSender().sendInteractionOption("Invite", 2, true);

            }

            @Override
            public void logout(Player player) {
                leave(player);
            }

            @Override
            public boolean handleKilledNPC(Player killer, NPC npc) {
                CoxRaid.handleNPCDeath(killer, npc);
                return true;
            }

            @Override
            public void leave(Player player) {
                player.getPacketSender().sendCameraNeutrality();

                MinigameAttributes.RaidsAttributes raidsAttributes = player.getMinigameAttributes().getRaidsAttributes();

                if (raidsAttributes.getParty() != null && player == raidsAttributes.getParty().getOwner()) {
                    Olm.destroyInstance(raidsAttributes.getParty(), raidsAttributes.getParty().getHeight());
                }

                player.getPacketSender().sendCameraNeutrality();
                raidsAttributes.setInsideRaid(false);

                if (player.getRegionInstance() != null)
                    player.getRegionInstance().destruct();


                player.coxWave = 0;
                player.getMovementQueue().setLockMovement(false);
                player.getPacketSender().sendTabInterface(GameSettings.ACHIEVEMENT_TAB, 45000);
                player.getPacketSender().sendDungeoneeringTabIcon(false);


            }

            @Override
            public void process(Player player) {
                if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null)
                    player.getMinigameAttributes().getRaidsAttributes().getParty().refreshInterface();

                if (player.getMinigameAttributes().getRaidsAttributes().getParty() == null)
                    leave(player);
            }

            @Override
            public void onDeath(Player player) {
                MinigameAttributes.RaidsAttributes raidsAttributes = player.getMinigameAttributes().getRaidsAttributes();
                if (raidsAttributes.getParty() != null)
                    raidsAttributes.getParty().remove(player, true, true);

                if (raidsAttributes.getParty() != null)
                    raidsAttributes.getParty().getPlayersInRaids().remove(player);
                player.moveTo(new Position(1234, 3558, 0));
//                leave(player);
            }

        },

        DONATOR_ZONE(new int[]{2620, 2690}, new int[]{2550, 2620}, false, true, true, false, true, true) {
        },
        MEMBER_ZONE(new int[]{3415, 3435}, new int[]{2900, 2926}, false, true, true, false, false, true) {
        },
        VARROCK(new int[]{3167, 3272}, new int[]{3376, 3504}, false, true, true, true, true, true) {
        },
        GAMBLING(new int[]{2835, 2855}, new int[]{5130, 5170}, false, true, true, true, true, true) {
        },
        BANK(new int[]{3090, 3099, 3089, 3090, 3248, 3258, 3179, 3191, 2944, 2948, 2942, 2948, 2944, 2950, 3008, 3019, 3017, 3022, 3203, 3213, 3212, 3215, 3215, 3220, 3220, 3227, 3227, 3230, 3226, 3228, 3227, 3229}, new int[]{3487, 3500, 3492, 3498, 3413, 3428, 3432, 3448, 3365, 3374, 3367, 3374, 3365, 3370, 3352, 3359, 3352, 3357, 3200, 3237, 3200, 3235, 3202, 3235, 3202, 3229, 3208, 3226, 3230, 3211, 3208, 3226}, false, true, true, false, false, true) {
        },
        EDGEVILLE(new int[]{3073, 3134}, new int[]{3457, 3518}, false, true, true, false, false, true) {
        },
        LUMBRIDGE(new int[]{3175, 3238}, new int[]{3179, 3302}, false, true, true, true, true, true) {
        },
        DAILY(new int[]{2520, 2600}, new int[]{4930, 4982}, false, true, true, false, false, true) {
        },
        KINGDOM(new int[]{2496, 2624}, new int[]{3840, 3904}, false, true, true, true, true, true) {
        },
        CASINO(new int[]{2150, 2300}, new int[]{4900, 5150}, true, true, true, false, true, false) {
            @Override
            public void enter(Player player) {
                player.getInventory().add(5, 1);
                player.questTab = "gamblingpanel";
                GambleInterface.refreshPanel(player);
            }

            @Override
            public void process(Player player) {
                player.questTab = "gamblingpanel";
                GambleInterface.refreshPanel(player);
            }

            @Override
            public void leave(Player player) {
                player.questTab = "playerpanel";
            }

            @Override
            public void logout(Player player) {
                leave(player);
            }

            @Override
            public void onDeath(Player player) {
                leave(player);
            }

        },
        KING_BLACK_DRAGON(new int[]{2251, 2292}, new int[]{4673, 4717}, true, true, true, false, true, true) {

			/*@Override
			public void enter(Player player) {

				KBD.startPreview(player);
			}
			@Override
			public void leave(Player player) {
				KBD.closeInterface(player);
			}
			@Override
			public void onDeath(Player player) {
				KBD.closeInterface(player);
			}*/
        },
        UNHOLY_CURSEBEARER(new int[]{3047, 3070}, new int[]{4390, 4370}, true, true, true, false, true, true) {
        },
        BORK(new int[]{3080, 3120}, new int[]{5520, 5550}, true, true, true, true, true, true) {

			/*@Override
			public void enter(Player player) {

				BORKS.startPreview(player);
			}
			@Override
			public void leave(Player player) {
				BORKS.closeInterface(player);
			}
			@Override
			public void onDeath(Player player) {
				BORKS.closeInterface(player);
			}*/
        },
        LIZARDMAN(new int[]{2700, 2730}, new int[]{9800, 9829}, true, true, true, false, true, true) {
			/*@Override
			public void enter(Player player) {

				LIZARD.startPreview(player);
			}
			@Override
			public void leave(Player player) {
				LIZARD.closeInterface(player);
			}
			@Override
			public void onDeath(Player player) {
				LIZARD.closeInterface(player);
			}*/
        },
        BARRELCHESTS(new int[]{2960, 2990}, new int[]{9510, 9520}, true, true, true, false, true, true) {
			/*@Override
			public void enter(Player player) {

				BARRELS.startPreview(player);
			}
			@Override
			public void leave(Player player) {
				BARRELS.closeInterface(player);
			}
			@Override
			public void onDeath(Player player) {
				BARRELS.closeInterface(player);
			}*/
        },
        SLASH_BASH(new int[]{2504, 2561}, new int[]{9401, 9473}, true, true, true, false, true, true) {

			/*@Override
			public void enter(Player player) {

				SLASHBASH.startPreview(player);
			}
			@Override
			public void leave(Player player) {
				SLASHBASH.closeInterface(player);
			}
			@Override
			public void onDeath(Player player) {
				SLASHBASH.closeInterface(player);
			}*/
        },
        BANDOS_AVATAR(new int[]{2877, 2928}, new int[]{4734, 4787}, true, true, true, false, true, true) {
			/*@Override
			public void enter(Player player) {

				AVATAR.startPreview(player);

			}
			@Override
			public void leave(Player player) {
				AVATAR.closeInterface(player);
			}
			@Override
			public void onDeath(Player player) {
				AVATAR.closeInterface(player);
			}*/
        },
        TORM_DEMONS(new int[]{2520, 2560}, new int[]{5730, 5799}, true, true, true, false, true, true) {

			/*@Override
			public void enter(Player player) {

			TDS.startPreview(player);

			}
			@Override
			public void leave(Player player) {
				TDS.closeInterface(player);
			}
			@Override
			public void onDeath(Player player) {
				TDS.closeInterface(player);
			}*/

        },
        KALPHITE_QUEEN(new int[]{3464, 3500}, new int[]{9478, 9523}, true, true, true, true, true, true) {
            @Override
            public boolean canTeleport(Player player) {
                return true;
            }

            @Override
            public void logout(Player player) {
                leave(player);
            }

            @Override
            public boolean handleKilledNPC(Player killer, NPC npc) {

                KalphiteQueenInstance.handleNPCDeath(killer, npc);
                return true;
            }

            @Override
            public void leave(Player player) {
                if (player.getRegionInstance() != null)
                    player.getRegionInstance().destruct();

            }

            @Override
            public void onDeath(Player player) {
                leave(player);
            }


        },
        DESERT(new int[]{3200, 3400}, new int[]{2860, 2980}, true, true, true, true, true, true) {
            @Override
            public void process(Player player) {

                if (RandomUtility.inclusiveRandom(10) == 1) {

                    if (player.getEquipment().contains(42069)) {
                        player.getPacketSender().sendMessage("@gre@Your 'item' protects you from the heat.");
                    } else if (player.getInventory().contains(1823) ||
                            player.getInventory().contains(1825) ||
                            player.getInventory().contains(1827) ||
                            player.getInventory().contains(1829)) {

                        player.getPacketSender().sendMessage("@or2@Your waterskins prevent some of the heat damage.");
                        player.dealDamage(new Hit(30, Hitmask.RED, CombatIcon.NONE));

                        if (player.getInventory().contains(1829)) {
                            player.getInventory().delete(1829, 1);
                            player.getInventory().add(1831, 1);
                            player.getPacketSender().sendMessage("@or2@Your waterskin(1) dries up.");
                        } else if (player.getInventory().contains(1827)) {
                            player.getInventory().delete(1827, 1);
                            player.getInventory().add(1829, 1);
                            player.getPacketSender().sendMessage("@or2@Your waterskin(2) evaporates a charge.");
                        } else if (player.getInventory().contains(1825)) {
                            player.getInventory().delete(1825, 1);
                            player.getInventory().add(1827, 1);
                            player.getPacketSender().sendMessage("@or2@Your waterskin(3) evaporates a charge.");
                        } else if (player.getInventory().contains(1823)) {
                            player.getInventory().delete(1823, 1);
                            player.getInventory().add(1825, 1);
                            player.getPacketSender().sendMessage("@or2@Your waterskin(4) evaporates a charge.");
                        }


                    } else {
                        player.getPacketSender().sendMessage("@red@You feel the full pain of the desert heat!");
                        player.dealDamage(new Hit(100, Hitmask.RED, CombatIcon.NONE));
                    }
                }

            }

            @Override
            public void enter(Player player) {
                player.getPacketSender().sendMessage("@red@You feel the heat increase!");

            }

            @Override
            public void leave(Player player) {
                player.getPacketSender().sendMessage("@gre@You feel the heat decrease slightly!");
            }

            @Override
            public void onDeath(Player player) {

            }

        },
        SKOTIZO(new int[]{3350, 3390}, new int[]{9800, 9825}, true, true, true, false, true, true) {
			/*@Override
			public void enter(Player player) {

			SKOT.startPreview(player);

			}
			@Override
			public void leave(Player player) {
				SKOT.closeInterface(player);
			}
			@Override
			public void onDeath(Player player) {
				SKOT.closeInterface(player);
			}*/

        },
        CERBERUS(new int[]{1215, 1265}, new int[]{1220, 1265}, true, true, true, false, true, true) {
			/*@Override
			public void enter(Player player) {

			CERB.startPreview(player);

			}
			@Override
			public void leave(Player player) {
				CERB.closeInterface(player);
			}
			@Override
			public void onDeath(Player player) {
				CERB.closeInterface(player);
			}*/

        },
        NEX(new int[]{2330, 2340}, new int[]{9560, 9585}, true, true, true, false, true, true) {
            @Override
            public void enter(Player player) {
                player.getPacketSender().sendMessage("For some reason, your prayers do not have any effect in here.");
                CurseHandler.deactivateAll(player);
                PrayerHandler.deactivateAll(player);
            }

            @Override
            public void leave(Player player) {
                NEXX.closeInterface(player);
            }

            @Override
            public void onDeath(Player player) {
                NEXX.closeInterface(player);
            }


        },
        NIGHTMARE(new int[]{2440, 2500}, new int[]{4765, 4815}, true, true, true, false, true, true) {
            @Override
            public boolean canTeleport(Player player) {
                return true;
            }

            @Override
            public void logout(Player player) {
                leave(player);
            }

            @Override
            public boolean handleKilledNPC(Player killer, NPC npc) {

                NightmareInstance.handleNPCDeath(killer, npc);
                return true;
            }

            @Override
            public void leave(Player player) {
                if (player.getRegionInstance() != null)
                    player.getRegionInstance().destruct();

            }

            @Override
            public void onDeath(Player player) {
                leave(player);
            }


        },
        ROCK_CRABS(new int[]{2689, 2727}, new int[]{3691, 3730}, true, true, true, true, true, true) {
        },
        ARMOURED_ZOMBIES(new int[]{3077, 3132}, new int[]{9657, 9680}, true, true, true, true, true, true) {
        },
        CORPOREAL_BEAST(new int[]{2970, 3000}, new int[]{4360, 4400}, true, true, true, true, true, true) {
			/*@Override
			public void enter(Player player) {

			CORP.startPreview(player);

			}
			@Override
			public void leave(Player player) {
				CORP.closeInterface(player);
			}
			@Override
			public void onDeath(Player player) {
				CORP.closeInterface(player);
			}*/

        },
        DAGANNOTH_DUNGEON(new int[]{2886, 2938}, new int[]{4431, 4477}, true, true, true, false, true, true) {
			/*@Override
			public void enter(Player player) {

				DAGS.startPreview(player);

			}
			@Override
			public void leave(Player player) {
				DAGS.closeInterface(player);
			}
			@Override
			public void onDeath(Player player) {
				DAGS.closeInterface(player);
			}*/
        },
        ZULRAH(new int[]{2230, 2310}, new int[]{3030, 3110}, true, true, true, false, true, true) {
            @Override
            public boolean canTeleport(Player player) {
                return true;
            }

            @Override
            public void logout(Player player) {
                leave(player);
            }

            @Override
            public boolean handleKilledNPC(Player killer, NPC npc) {

                ZulrahInstance.handleNPCDeath(killer, npc);
                return true;
            }

            @Override
            public void leave(Player player) {
                if (player.getRegionInstance() != null)
                    player.getRegionInstance().destruct();
            }

            @Override
            public void onDeath(Player player) {
                leave(player);
            }


        },

        JAIL(new int[]{2522, 2534}, new int[]{3372, 3378}, true, true, true, false, true, true) {
            @Override
            public boolean canTeleport(Player player) {
                return false;
            }

        },

        TUTORIAL_ISLAND(new int[]{3053, 3149}, new int[]{3057, 3133}, false, true, true, false, false, true) {
            @Override
            public boolean canTeleport(Player player) {
                player.getPacketSender().sendMessage("@red@You can't teleport out of the Tutorial!");
                return false;
            }

        },

        TUTORIAL_DUNGEON(new int[]{3072, 3134}, new int[]{9472, 9534}, false, false, false, false, true, false) {
            @Override
            public boolean canTeleport(Player player) {
                player.getPacketSender().sendMessage("@red@You can't teleport out of the Tutorial!");
                return false;
            }

        },

        WILDERNESS(new int[]{2941, 3400}, new int[]{3523, 3970}, true, true, true, true, true, true) {
            @Override
            public void process(Player player) {
                int x = player.getPosition().getX();
                int y = player.getPosition().getY();


                player.setWildernessLevel(((((y > 6400 ? y - 6400 : y) - 3520) / 8) + 1));
                player.getPacketSender().sendString(25352, "" + player.getWildernessLevel());
                player.getPacketSender().sendString(25355, "Levels: " + CombatFactory.getLevelDifference(player, false) + " - " + CombatFactory.getLevelDifference(player, true));
                CombatFactory.skullPlayer(player);
                //BountyHunter.process(player);

                /*for (Skill skill : Skill.values()) {
                    if (player.getSkillManager().getCurrentLevel(skill) > 120 && !SkillManager.isNewSkill(skill)) {
                        player.getSkillManager().setCurrentLevel(skill, 120);
                    }
                }*/

            }

            @Override
            public void leave(Player player) {
                if (player.getLocation() != this) {
                    player.getPacketSender().sendString(19000, "Combat level: " + player.getSkillManager().getCombatLevel());
                    player.getUpdateFlag().flag(Flag.APPEARANCE);
                    player.getPacketSender().sendMessage("You've been refunded " + player.wildRisk + " Coins.");
                    player.setMoneyInPouch(player.getMoneyInPouch() + player.wildRisk);
                    player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                    player.wildRisk = 0;
                    player.wildBoost = 0;
                    player.wildEnragedKills = 0;
                }
                PLAYERS_IN_WILD--;
            }

            @Override
            public void logout(Player player) {
                leave(player);
            }

            @Override
            public void enter(Player player) {
                player.getPacketSender().sendInteractionOption("Attack", 2, true);
                player.sendParallellInterfaceVisibility(25347, true);

                player.getPacketSender().sendMessage("@red@You will keep your items on death!");
                player.getPacketSender().sendMessage("@red@You will lose your Wildy Risk on Death!");
                player.getPacketSender().sendMessage("@red@You will be refunded half of your Wildy Risk if you leave safely");

                player.getPacketSender().sendString(19000, "Combat level: " + player.getSkillManager().getCombatLevel());
                player.getUpdateFlag().flag(Flag.APPEARANCE);
                player.wildEnragedKills = 0;
                PLAYERS_IN_WILD++;
            }

            @Override
            public boolean canTeleport(Player player) {
                if (player.getWildernessLevel() > 30) {
                    if (player.getStaffRights() == StaffRights.OWNER || player.getStaffRights() == StaffRights.DEVELOPER || player.getWildernessLevel() == 60) {
                        player.getPacketSender().sendMessage("@red@You've teleported out of deep Wilderness.");
                        return true;
                    }
                    player.getPacketSender().sendMessage("Teleport spells are blocked in this level of Wilderness.");
                    player.getPacketSender().sendMessage("You must be below level 30 of Wilderness to use teleportation spells.");
                    return false;
                }
                return true;
            }

            @Override
            public void login(Player player) {
                player.performGraphic(new Graphic(2000, 8));
            }

            @Override
            public boolean canAttack(Player player, Player target) {
                int combatDifference = CombatFactory.combatLevelDifference(player.getSkillManager().getCombatLevel(), target.getSkillManager().getCombatLevel());
                if (combatDifference > player.getWildernessLevel() || combatDifference > target.getWildernessLevel()) {
                    player.getPacketSender().sendMessage("Your combat level difference is too great to attack that player here.");
                    player.getMovementQueue().reset();
                    return false;
                }
                if (target.getLocation() != Location.WILDERNESS) {
                    player.getPacketSender().sendMessage("That player cannot be attacked, because they are not in the Wilderness.");
                    player.getMovementQueue().reset();
                    return false;
                }
                if (Jail.isJailed(player)) {
                    player.getPacketSender().sendMessage("You cannot do that right now.");
                    return false;
                }
                if (Jail.isJailed(target)) {
                    player.getPacketSender().sendMessage("That player cannot be attacked right now.");
                    return false;
                }
				/*if(Misc.getMinutesPlayed(player) < 20) {
					player.getPacketSender().sendMessage("You must have played for at least 20 minutes in order to attack someone.");
					return false;
				}
				if(Misc.getMinutesPlayed(target) < 20) {
					player.getPacketSender().sendMessage("This player is a new player and can therefore not be attacked yet.");
					return false;
				}*/

                if (player.isWildSafe(player.getPosition().getX(), player.getPosition().getY())) {
                    player.getPacketSender().sendMessage("You can't attack from a safe zone.");
                    return false;
                }
                if (target.isWildSafe(target.getPosition().getX(), target.getPosition().getY())) {
                    player.getPacketSender().sendMessage("You can't attack someone that is in a safe zone.");
                    return false;
                }

                return true;
            }
        },
        CONSTRUCTION(new int[]{1880, 1951}, new int[]{5080, 5151}, false, true, true, false, true, true) {
            @Override
            public boolean canTeleport(Player player) {
                return true;
            }

            @Override
            public void login(Player player) {
                player.moveTo(GameSettings.DEFAULT_POSITION.copy());
            }

            @Override
            public void logout(Player player) {
                player.moveTo(GameSettings.DEFAULT_POSITION.copy());
            }
        },

        BARROWS(new int[]{3520, 3598, 3543, 3584, 3543, 3560}, new int[]{9653, 9750, 3265, 3314, 9685, 9702}, false, true, true, true, true, true) {
            @Override
            public void process(Player player) {
                if (player.getWalkableInterfaceId() != 37200)
                    player.sendParallellInterfaceVisibility(37200, true);

                Minigame.updateInterface(player);
            }

            @Override
            public boolean canTeleport(Player player) {
                return true;
            }

            @Override
            public void logout(Player player) {

            }

            @Override
            public boolean handleKilledNPC(Player killer, NPC npc) {
                Minigame.killBarrowsNpc(killer, npc, true);
                return true;
            }
        },


        SOULWARS(new int[]{-1, -1}, new int[]{-1, -1}, true, true, true, false, true, true) {
            @Override
            public void process(Player player) {

            }

            @Override
            public boolean canTeleport(Player player) {
                player.getPacketSender().sendMessage("If you wish to leave, you must use the portal in your team's lobby.");
                return false;
            }

            @Override
            public void logout(Player player) {

            }

            @Override
            public void onDeath(Player player) {

            }

            @Override
            public boolean handleKilledNPC(Player killer, NPC npc) {

                return false;
            }

        },
        SOULWARS_WAIT(new int[]{-1, -1}, new int[]{-1, -1}, false, true, false, false, false, true) {
            @Override
            public void process(Player player) {
            }

            @Override
            public boolean canTeleport(Player player) {
                player.getPacketSender().sendMessage("You must leave the waiting room before being able to teleport.");
                return false;
            }

            @Override
            public void logout(Player player) {
            }
        },
        FIGHT_CAVES(new int[]{2360, 2445}, new int[]{5045, 5125}, true, true, false, false, false, false) {
            @Override
            public void process(Player player) {
            }

            @Override
            public boolean canTeleport(Player player) {
                //player.getPacketSender().sendMessage("Teleport spells are blocked here. If you'd like to leave, use the north-east exit.");
                return true;
            }

            @Override
            public void login(Player player) {
            }

            @Override
            public void leave(Player player) {
                player.getCombatBuilder().reset(true);
                if (player.getRegionInstance() != null) {
                    player.getRegionInstance().destruct();
                }
                player.moveTo(new Position(2439, 5169));
            }

            @Override
            public void onDeath(Player player) {
                FightCave.leaveCave(player);
            }

            @Override
            public boolean handleKilledNPC(Player killer, NPC npc) {
                FightCave.handleJadDeath(killer, npc);
                return true;
            }
        },
        GRAVEYARD(new int[]{3485, 3517}, new int[]{3559, 3580}, true, true, true, true, true, true) {

			/*@Override
			public void enter(Player player) {
			}*/

            @Override
            public boolean canTeleport(Player player) {
                player.getPacketSender().sendMessage("You leave the Raid safely.");
                return true;
            }

            @Override
            public void process(Player player) {

                if (player.getMinigameAttributes().getRaidsAttributes().getParty() == null)
                    leave(player);
            }

            @Override
            public boolean handleKilledNPC(Player killer, NPC npc) {
                Raid.handleNPCDeath(killer, npc);
                return true;
            }

            public void logout(Player player) {
                leave(player);
            }

            @Override
            public void leave(Player player) {

                player.getPacketSender().sendCameraNeutrality();

                if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null) {
                    final RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();

                    World.getNpcs().forEach(n -> n.removeInstancedNpcs(GRAVEYARD, party.getInstanceLevel()));
                }


                /*if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null)
                    player.getMinigameAttributes().getRaidsAttributes().getParty().remove(player, true, true);*/

                player.getMinigameAttributes().getRaidsAttributes().setInsideRaid(false);

                if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null)
                    player.getMinigameAttributes().getRaidsAttributes().getParty().getPlayersInRaids().remove(player);

                player.moveTo(GameSettings.RAIDS_LOBBY_POSITION);


                player.getMovementQueue().setLockMovement(false);

            }

            @Override
            public void onDeath(Player player) {
                leave(player);
            }
        },
        FIGHT_PITS(new int[]{2370, 2425}, new int[]{5133, 5167}, true, true, true, false, false, true) {
            @Override
            public void process(Player player) {
                if (FightPit.inFightPits(player)) {
                    FightPit.updateGame(player);
                    if (player.getPlayerInteractingOption() != PlayerInteractingOption.ATTACK)
                        player.getPacketSender().sendInteractionOption("Attack", 2, true);
                }
            }

            @Override
            public boolean canTeleport(Player player) {
                player.getPacketSender().sendMessage("Teleport spells are blocked here. If you'd like to leave, use the northern exit.");
                return false;
            }

            @Override
            public void logout(Player player) {
                FightPit.removePlayer(player, "leave game");
            }

            @Override
            public void leave(Player player) {
                onDeath(player);
            }

            @Override
            public void onDeath(Player player) {
                if (FightPit.getState(player) != null) {
                    FightPit.removePlayer(player, "death");
                }
            }

            @Override
            public boolean canAttack(Player player, Player target) {
                String state1 = FightPit.getState(player);
                String state2 = FightPit.getState(target);
                return state1 != null && state1.equals("PLAYING") && state2 != null && state2.equals("PLAYING");
            }
        },
        FIGHT_PITS_WAIT_ROOM(new int[]{2393, 2404}, new int[]{5168, 5176}, false, true, false, false, false, true) {
            @Override
            public void process(Player player) {
                FightPit.updateWaitingRoom(player);
            }

            @Override
            public boolean canTeleport(Player player) {
                player.getPacketSender().sendMessage("Teleport spells are blocked here. If you'd like to leave, use the northern exit.");
                return false;
            }

            @Override
            public void logout(Player player) {
                FightPit.removePlayer(player, "leave room");
            }

            @Override
            public void leave(Player player) {
                if (player.getLocation() != FIGHT_PITS) {
                    FightPit.removePlayer(player, "leave room");
                }
            }

        },
        /*DUEL_ARENA(new int[]{3322, 3394, 3311, 3323, 3331, 3391}, new int[]{3195, 3291, 3223, 3248, 3242, 3260}, false, true, false, false, false, false) {
            @Override
            public void process(Player player) {
                if(!player.walkableInterfaceList.contains(201))
                    player.sendParallellInterfaceVisibility(201, true);
                if(player.getDueling().duelingStatus == 0) {
                    if(player.getPlayerInteractingOption() != PlayerInteractingOption.CHALLENGE)
                        player.getPacketSender().sendInteractionOption("Challenge", 2, false);
                } else if(player.getPlayerInteractingOption() != PlayerInteractingOption.ATTACK)
                    player.getPacketSender().sendInteractionOption("Attack", 2, true);
            }

            @Override
            public void enter(Player player) {
                PLAYERS_IN_DUEL_ARENA++;
                player.getPacketSender().sendMessage("<img=10> <col=996633>Warning! Do not stake items which you are not willing to lose.");
            }

            @Override
            public boolean canTeleport(Player player) {
                if(player.getDueling().duelingStatus == 5) {
                    player.getPacketSender().sendMessage("To forfiet a duel, run to the west and use the trapdoor.");
                    return false;
                }
                return true;
            }

            @Override
            public void logout(Player player) {
                boolean dc = false;
                if(player.getDueling().inDuelScreen && player.getDueling().duelingStatus != 5) {
                    player.getDueling().declineDuel(player.getDueling().duelingWith > 0 ? true : false);
                } else if(player.getDueling().duelingStatus == 5) {
                    if(player.getDueling().duelingWith > -1) {
                        Player duelEnemy = World.getPlayers().get(player.getDueling().duelingWith);
                        if(duelEnemy != null) {
                            duelEnemy.getDueling().duelVictory();
                        } else {
                            dc = true;
                        }
                    }
                }
                player.moveTo(new Position(3368, 3268));
                if(dc) {
                    World.getPlayers().remove(player);
                }
            }

            @Override
            public void leave(Player player) {
                if(player.getDueling().duelingStatus == 5) {
                    onDeath(player);
                }
                PLAYERS_IN_DUEL_ARENA--;
            }

            @Override
            public void onDeath(Player player) {
                if(player.getDueling().duelingStatus == 5) {
                    if(player.getDueling().duelingWith > -1) {
                        Player duelEnemy = World.getPlayers().get(player.getDueling().duelingWith);
                        if(duelEnemy != null) {
                            duelEnemy.getDueling().duelVictory();
                            duelEnemy.getPacketSender().sendMessage("You won the duel! Congratulations!");
                        }
                    }
                    player.getPacketSender().sendMessage("You've lost the duel.");
                    player.getDueling().arenaStats[1]++; player.getDueling().reset();
                }
                player.moveTo(new Position(3368 + RandomUtility.getRandom(5), 3267+ RandomUtility.getRandom(3)));
                player.getDueling().reset();
            }

            @Override
            public boolean canAttack(Player player, Player target) {
                if(target.getIndex() != player.getDueling().duelingWith) {
                    player.getPacketSender().sendMessage("That player is not your opponent!");
                    return false;
                }
                if(player.getDueling().timer != -1) {
                    player.getPacketSender().sendMessage("You cannot attack yet!");
                    return false;
                }
                return player.getDueling().duelingStatus == 5 && target.getDueling().duelingStatus == 5;
            }
        },*/
		/*GODWARS_DUNGEON(new int[]{2800, 2950, 2858, 2943}, new int[]{5200, 5400, 5180, 5230}, true, true, true, false, true, true) {
			@Override
			public void process(Player player) {

				if(!player.walkableInterfaceList.contains(16210))
					player.sendParallellInterfaceVisibility(16210, true);
			}

			@Override
			public void enter(Player player) {
				//DialogueManager.start(player, 110);
				//player.getPacketSender().sendMessage("<img=10> If you die in a boss room, you will lose your items. You have been warned.");
				//GWD.startPreview(player);
			}

			@Override
			public boolean canTeleport(Player player) {
				return true;
			}

			@Override
			public void onDeath(Player player) {
				leave(player);
				//GWD.closeInterface(player);
			}

			@Override
			public void leave(Player p) {
				//GWD.closeInterface(p);

				for(int i = 0; i < p.getMinigameAttributes().getGodwarsDungeonAttributes().getKillcount().length; i++) {
					p.getMinigameAttributes().getGodwarsDungeonAttributes().getKillcount()[i] = 0;
					p.getPacketSender().sendString((16216+i), "0");
				}
				p.getMinigameAttributes().getGodwarsDungeonAttributes().setAltarDelay(0).setHasEnteredRoom(false);
				p.getPacketSender().sendMessage("Your Godwars dungeon progress has been reset.");
			}

			@Override
			public boolean handleKilledNPC(Player killer, NPC n) {
				int index = -1;
				int npc = n.getId();
				if(npc == 6246 || npc == 6229 || npc == 6230 || npc == 6231) //Armadyl
					index = 0;
				else if(npc == 102 || npc == 3583 || npc == 115 || npc == 113 || npc == 6273 || npc == 6276 || npc == 6277 || npc == 6288) //Bandos
					index = 1;
				else if(npc == 6258 || npc == 6259 || npc == 6254 || npc == 6255 || npc == 6257 || npc == 6256) //Saradomin
					index = 2;
				else if(npc == 10216 || npc == 6216 || npc == 1220 || npc == 6007 || npc == 6219 ||npc == 6220 || npc == 6221 || npc == 49 || npc == 4418) //Zamorak
					index = 3;
				if(index != -1) {
					killer.getMinigameAttributes().getGodwarsDungeonAttributes().getKillcount()[index]++;
					killer.getPacketSender().sendString((16216+index), ""+killer.getMinigameAttributes().getGodwarsDungeonAttributes().getKillcount()[index]);
				}
				return false;
			}
		},*/
        RECIPE_FOR_DISASTER(new int[]{1885, 1913}, new int[]{5340, 5369}, true, true, false, false, false, false) {
            @Override
            public boolean canTeleport(Player player) {
                //player.getPacketSender().sendMessage("Teleport spells are blocked here. If you'd like to leave, use a portal.");
                return true;
            }

            @Override
            public void process(Player player) {
                if (!player.inDaily) {
                    CurseHandler.deactivateAll(player);
                    PrayerHandler.deactivateAll(player);
                }
            }

            @Override
            public void logout(Player player) {
                leave(player);
            }

            @Override
            public boolean handleKilledNPC(Player killer, NPC npc) {

                RecipeForDisaster.handleNPCDeath(killer, npc);

                return true;
            }

            @Override
            public void leave(Player player) {
                if (player.getRegionInstance() != null)
                    player.getRegionInstance().destruct();
                player.teleHome(player);
                player.inDaily = false;
                player.instancedBosses = false;

            }

            @Override
            public void onDeath(Player player) {
                leave(player);
            }

        },
        INSTANCEDBOSSES(new int[]{2885, 2940}, new int[]{4360, 4410}, true, true, false, false, false, false) {
            @Override
            public boolean canTeleport(Player player) {

                return true;
            }

            @Override
            public void process(Player player) {

                if (!player.displayHUD) {
                    player.getPacketSender().sendWalkableInterface(368, true);

                    player.getPacketSender().sendString(369, "@yel@Overload Timer: " + player.getOverloadPotionTimer());
                    player.getPacketSender().sendString(370, "@yel@Slayer Task: " + player.getSlayer().getSlayerTask().toString());
                    player.getPacketSender().sendString(371, "@yel@Slayer Kills Left: " + player.getSlayer().getAmountToSlay());
                    player.getPacketSender().sendString(372, "@yel@Idle Timer: " + ((player.getIdleTime() - (System.currentTimeMillis() - player.getActionTracker().getActions().getFirst().getWhen())) / 60000) + " minutes");
                    player.getPacketSender().sendString(373, "@yel@Boss Spawns Left: " + (250 - player.instanceKC));
                }
            }

            @Override
            public void logout(Player player) {
                leave(player);
            }

            @Override
            public boolean handleKilledNPC(Player killer, NPC npc) {

                InstancedBosses.handleNPCDeath(killer, npc);

                return true;
            }

            @Override
            public void leave(Player player) {
                if (player.getRegionInstance() != null)
                    player.getRegionInstance().destruct();
                World.getNpcs().forEach(n -> n.removeInstancedNpcs(INSTANCEDBOSSES, player.getPosition().getZ()));
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
                player.instancedBosses = false;
                player.instanceSpawns = 0;
                player.instanceRespawnBoost = 0;
                player.moveTo(new Position(2341, 9625, 0));
            }

            @Override
            public void onDeath(Player player) {
                leave(player);
            }

        },
        INSTANCED_SLAYER(new int[]{1666, 1724}, new int[]{4545, 4605}, true, true, false, false, false, false) {
            @Override
            public boolean canTeleport(Player player) {

                return true;
            }

            @Override
            public void process(Player player) {

            }

            @Override
            public void logout(Player player) {
                leave(player);
            }

            @Override
            public boolean handleKilledNPC(Player killer, NPC npc) {

                SuperiorSlayer.handleNPCDeath(killer, npc);

                return true;
            }

            @Override
            public void leave(Player player) {
                if (player.getRegionInstance() != null)
                    player.getRegionInstance().destruct();
                player.moveTo(GameSettings.DEFAULT_POSITION);
            }

            @Override
            public void onDeath(Player player) {
                leave(player);
            }

        },

        PESTILENT_BLOAT(new int[]{3256, 3336}, new int[]{4403, 4483}, true, true, false, false, false, false) {
            @Override
            public boolean canTeleport(Player player) {
                //player.getPacketSender().sendMessage("@red@You cannot teleport out of here!");
                return true;
            }

            @Override
            public void enter(Player player) {


                //player.getPacketSender().sendTabInterface(GameSettings.OPTIONS_TAB, 65000).sendTab(GameSettings.OPTIONS_TAB);

                int id = 65016;
                for (int i = 65016; i < 65064; i++) {
                    id++;
                    player.getPacketSender().sendString(id++, "");
                    player.getPacketSender().sendString(id++, "");
                    player.getPacketSender().sendString(id++, "");
                }
                player.getPacketSender().sendString(65009, "Create");
                player.getPacketSender().sendString(65002, "Raiding Party: @whi@0");
                //player.getPacketSender().sendInteractionOption("Invite", 2, true);
            }

            @Override
            public void logout(Player player) {
                leave(player);
            }

            @Override
            public boolean handleKilledNPC(Player killer, NPC npc) {
                TobRaid.handleNPCDeath(killer, npc);
                return true;
            }

            @Override
            public void leave(Player player) {

                if (player.getLocation() != MAIDEN_SUGADINTI) {
                    player.getPacketSender().sendCameraNeutrality();


                    if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null)
                        player.getMinigameAttributes().getRaidsAttributes().getParty().remove(player, true, true);

                    player.getMinigameAttributes().getRaidsAttributes().setInsideRaid(false);

                    if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null)
                        player.getMinigameAttributes().getRaidsAttributes().getParty().getPlayersInRaids().remove(player);

                    if (player.getRegionInstance() != null)
                        player.getRegionInstance().destruct();
                    player.tobWave = 0;
                    player.getMovementQueue().setLockMovement(false);
                    player.getPacketSender().sendTabInterface(GameSettings.ACHIEVEMENT_TAB, 45000);
                    player.getPacketSender().sendDungeoneeringTabIcon(false);
                    player.moveTo(new Position(3669, 3218, 0));

                }

            }

            @Override
            public void process(Player player) {
                if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null)
                    player.getMinigameAttributes().getRaidsAttributes().getParty().refreshInterface();

                if (player.getMinigameAttributes().getRaidsAttributes().getParty() == null)
                    leave(player);
            }

            @Override
            public void onDeath(Player player) {
                leave(player);
            }

        },

        MAIDEN_SUGADINTI(new int[]{3135, 3215}, new int[]{4406, 4486}, true, true, false, false, false, false) {
            @Override
            public boolean canTeleport(Player player) {
                //player.getPacketSender().sendMessage("@red@You cannot teleport out of here!");
                return true;
            }

            @Override
            public void enter(Player player) {


                //player.getPacketSender().sendTabInterface(GameSettings.OPTIONS_TAB, 65000).sendTab(GameSettings.OPTIONS_TAB);

                int id = 65016;
                for (int i = 65016; i < 65064; i++) {
                    id++;
                    player.getPacketSender().sendString(id++, "");
                    player.getPacketSender().sendString(id++, "");
                    player.getPacketSender().sendString(id++, "");
                }
                player.getPacketSender().sendString(65009, "Create");
                player.getPacketSender().sendString(65002, "Raiding Party: @whi@0");
                //player.getPacketSender().sendInteractionOption("Invite", 2, true);
            }

            @Override
            public void logout(Player player) {
                leave(player);
            }

            @Override
            public boolean handleKilledNPC(Player killer, NPC npc) {
                TobRaid.handleNPCDeath(killer, npc);
                return true;
            }

            @Override
            public void leave(Player player) {

                if (player.getLocation() != VERZIK_VITUR) {
                    player.getPacketSender().sendCameraNeutrality();

                    if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null)
                        player.getMinigameAttributes().getRaidsAttributes().getParty().remove(player, true, true);

                    player.getMinigameAttributes().getRaidsAttributes().setInsideRaid(false);

                    if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null)
                        player.getMinigameAttributes().getRaidsAttributes().getParty().getPlayersInRaids().remove(player);

                    if (player.getRegionInstance() != null)
                        player.getRegionInstance().destruct();
                    player.tobWave = 0;
                    player.getMovementQueue().setLockMovement(false);
                    player.getPacketSender().sendTabInterface(GameSettings.ACHIEVEMENT_TAB, 45000);
                    player.getPacketSender().sendDungeoneeringTabIcon(false);
                    player.moveTo(new Position(3669, 3218, 0));


                }
            }

            @Override
            public void process(Player player) {
                if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null)
                    player.getMinigameAttributes().getRaidsAttributes().getParty().refreshInterface();

                if (player.getMinigameAttributes().getRaidsAttributes().getParty() == null)
                    leave(player);
            }

            @Override
            public void onDeath(Player player) {
                leave(player);
            }

        },

        VERZIK_VITUR(new int[]{3150, 3190}, new int[]{4290, 4330}, true, true, false, false, false, false) {
            @Override
            public boolean canTeleport(Player player) {
                //player.getPacketSender().sendMessage("@red@You cannot teleport out of here!");
                return true;
            }

            @Override
            public void enter(Player player) {


                //player.getPacketSender().sendTabInterface(GameSettings.OPTIONS_TAB, 65000).sendTab(GameSettings.OPTIONS_TAB);

                int id = 65016;
                for (int i = 65016; i < 65064; i++) {
                    id++;
                    player.getPacketSender().sendString(id++, "");
                    player.getPacketSender().sendString(id++, "");
                    player.getPacketSender().sendString(id++, "");
                }
                player.getPacketSender().sendString(65009, "Create");
                player.getPacketSender().sendString(65002, "Raiding Party: @whi@0");
                //player.getPacketSender().sendInteractionOption("Invite", 2, true);

            }

            @Override
            public void logout(Player player) {
                leave(player);
            }

            @Override
            public boolean handleKilledNPC(Player killer, NPC npc) {
                TobRaid.handleNPCDeath(killer, npc);
                return true;
            }

            @Override
            public void leave(Player player) {

                player.getPacketSender().sendCameraNeutrality();

                if (player.getLocation() != RAIDS_LOBBY) {
                    if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null)
                        player.getMinigameAttributes().getRaidsAttributes().getParty().remove(player, true, true);

                    player.getMinigameAttributes().getRaidsAttributes().setInsideRaid(false);

                    if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null)
                        player.getMinigameAttributes().getRaidsAttributes().getParty().getPlayersInRaids().remove(player);
                }

                if (player.getRegionInstance() != null)
                    player.getRegionInstance().destruct();

                player.tobWave = 0;
                player.getMovementQueue().setLockMovement(false);
                player.getPacketSender().sendTabInterface(GameSettings.ACHIEVEMENT_TAB, 45000);
                player.getPacketSender().sendDungeoneeringTabIcon(false);
                player.moveTo(new Position(3669, 3218, 0));

            }

            @Override
            public void process(Player player) {
                if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null)
                    player.getMinigameAttributes().getRaidsAttributes().getParty().refreshInterface();

                if (player.getMinigameAttributes().getRaidsAttributes().getParty() == null)
                    leave(player);
            }

            @Override
            public void onDeath(Player player) {
                leave(player);
            }

        },

        GWD_RAID(new int[]{2880, 2933}, new int[]{5250, 5280}, true, true, false, false, false, false) {
            @Override
            public boolean canTeleport(Player player) {
                //player.getPacketSender().sendMessage("Teleport spells are blocked here. If you'd like to leave, use a portal.");
                return true;
            }

			/*@Override
			public void process(Player player) {
				if(!player.inDaily && player.gwdRaid) {
					CurseHandler.deactivateAll(player);
					PrayerHandler.deactivateAll(player);
				}
			}*/

            @Override
            public void logout(Player player) {
                leave(player);
            }

            @Override
            public boolean handleKilledNPC(Player killer, NPC npc) {

                if (killer.gwdRaid) {
                    GwdRaid.handleNPCDeath(killer, npc);
                    return true;
                }

                return false;
            }

            @Override
            public void leave(Player player) {

                player.getPacketSender().sendCameraNeutrality();

                final RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();

                player.getMinigameAttributes().getRaidsAttributes().setInsideRaid(false);

                if (player.gwdRaidLoot) {
                    GwdrRewards.grantLoot(party, player);
                } else if (!player.gwdRaidLoot) {
                    if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null)
                        player.getMinigameAttributes().getRaidsAttributes().getParty().remove(player, true, true);

                    if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null)
                        player.getMinigameAttributes().getRaidsAttributes().getParty().getPlayersInRaids().remove(player);
                }

                if (party != null)
                    World.getNpcs().forEach(n -> n.removeInstancedNpcs(GWD_RAID, party.getInstanceLevel()));

                player.moveTo(new Position(2440, 3087, 0));

                player.gwdRaidLoot = false;

                player.gwdRaidKills = 0;
                player.gwdRaidWave = 0;


                player.getMovementQueue().setLockMovement(false);
            }

            @Override
            public void onDeath(Player player) {
                leave(player);
            }

        },

        BLAST_FURNACE(new int[]{1930, 1960}, new int[]{4950, 4980}, true, true, false, false, false, false) {
            @Override
            public void enter(Player player) {
                player.getPacketSender().sendMessage("@red@Bars cost 500 each to collect!");

            }

            @Override
            public void process(Player player) {


                player.getPacketSender().sendWalkableInterface(368, true);

                Item OreItem = new Item(player.BForeType);
                String itemName = OreItem.getDefinition().getName();

                if (player.BForeType == 0) {
                    itemName = "None";
                }


                player.getPacketSender().sendString(369, "@cya@Ore Type: " + itemName);
                player.getPacketSender().sendString(370, "@cya@Ore Quantity " + player.BForeQty);
                player.getPacketSender().sendString(371, "@cya@Bar Quantity: " + player.BFbarQty);
                player.getPacketSender().sendString(372, "@cya@");
                player.getPacketSender().sendString(373, "@cya@");

            }


        },

        MOTHERLODE_MINE(new int[]{3700, 3780}, new int[]{5630, 5700}, true, true, false, false, false, false) {
            @Override
            public void enter(Player player) {
                player.getPacketSender().sendMessage("@red@Ores cost 200 each to process!");

            }

            @Override
            public void process(Player player) {


                player.getPacketSender().sendWalkableInterface(368, true);

                player.getPacketSender().sendString(369, "@cya@Paydirt in Hopper: " + player.getPayDirt());
                player.getPacketSender().sendString(370, "@cya@Paydirt in Dirt-Bag: " + player.getCoalBag());
                player.getPacketSender().sendString(371, "@cya@");
                player.getPacketSender().sendString(372, "@cya@");
                player.getPacketSender().sendString(373, "@cya@");

            }

        },

		GAUNTLET(new int[]{1873, 1887}, new int[]{5649, 5663}, true, true, false, false, false, false) {

            @Override
            public void process(Player player) {
                GauntletRaid.roomMechanics(player);
            }

		},

        WINTERTODT(new int[]{1600, 1663}, new int[]{3968, 4031}, true, true, false, false, false, false) {
            @Override
            public boolean canTeleport(Player player) {
                return true;
            }

            @Override
            public void enter(Player player) {

                Wintertodt.enter(player);

            }

            @Override
            public void logout(Player player) {
                leave(player);
            }


            @Override
            public void leave(Player player) {


                player.getInventory().delete(220695, 28);
                player.getInventory().delete(220696, 28);

                player.getSkillManager().stopSkilling();

                player.snow = false;

                player.inWintertodt = false;
                player.getMovementQueue().setLockMovement(false);

                player.moveTo(new Position(1631, 3944, 0));

                if (player.WintertodtLoot) {
                    player.WintertodtLoot = false;
                    Wintertodt.loot(player);
                }

            }

            @Override
            public void process(Player player) {

                int percentage = 0;
                int burnStrength = 0;
                GameObject litBrazier = new GameObject(329314, new Position(1, 1, 1));


                if (Wintertodt.WintertodtHealth > 0 && Wintertodt.WintertodtBoss) {
                    if (RandomUtility.inclusiveRandom(5) == 1) {
                        Wintertodt.dropSnow(player);
                    }

                    percentage = Wintertodt.WintertodtHealth / 100;
                }


                player.getPacketSender().sendWalkableInterface(368, true);

                player.getPacketSender().sendString(369, "@yel@Wintertodt Energy: " + percentage + "%");
                player.getPacketSender().sendString(370, "@yel@Wintertodt Points: " + player.WintertodtPoints);
                player.getPacketSender().sendString(371, "");
                player.getPacketSender().sendString(372, "");
                player.getPacketSender().sendString(373, "");
            }

            @Override
            public void onDeath(Player player) {
                leave(player);
            }

        },

        MAZE_RANDOM(new int[]{2880, 2943}, new int[]{4545, 4606}, true, true, false, false, false, false) {
            @Override
            public boolean canTeleport(Player player) {
                if (player.getStaffRights().getStaffRank() < 6) {
                    player.getPacketSender().sendMessage("Teleport spells are blocked here. You must complete the maze!");
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public void leave(Player player) {
                player.inRandom = false;
            }

        },
        FREE_FOR_ALL_ARENA(new int[]{2755, 2876}, new int[]{5512, 5627}, true, true, true, false, false, true) {
            @Override
            public boolean canTeleport(Player player) {
                player.getPacketSender().sendMessage("Teleport spells are blocked here, if you wish to teleport, use the portal.");
                return false;
            }

            @Override
            public void onDeath(Player player) {
                player.moveTo(GameSettings.DEFAULT_POSITION);
            }

            @Override
            public boolean canAttack(Player player, Player target) {
                if (target.getLocation() != FREE_FOR_ALL_ARENA) {
                    player.getPacketSender().sendMessage("That player has not entered the dangerous zone yet.");
                    player.getMovementQueue().reset();
                    return false;
                }
                return true;
            }

            @Override
            public void enter(Player player) {
                if (player.getPlayerInteractingOption() != PlayerInteractingOption.ATTACK) {
                    player.getPacketSender().sendInteractionOption("Attack", 2, true);
                }
            }

        },
        FREE_FOR_ALL_WAIT(new int[]{2755, 2876}, new int[]{5507, 5627}, false, true, true, false, false, true) {
            @Override
            public boolean canTeleport(Player player) {
                player.getPacketSender().sendMessage("Teleport spells are blocked here, if you wish to teleport, use the portal.");
                return false;
            }

            @Override
            public void onDeath(Player player) {
                player.moveTo(new Position(2815, 5511));
            }
        },
        CRASH_ISLAND(new int[]{2880, 2950}, new int[]{2680, 2750}, false, true, true, false, false, true) {
            @Override
            public boolean canTeleport(Player player) {
                player.getPacketSender().sendMessage("@red@You can't teleport out of here until you form a party and talk to Waydar!");
                return false;
            }

            public void enter(Player player) {

                player.getPacketSender().sendTabInterface(GameSettings.OPTIONS_TAB, 65000).sendTab(GameSettings.OPTIONS_TAB);

                int id = 65016;
                for (int i = 65016; i < 65064; i++) {
                    id++;
                    player.getPacketSender().sendString(id++, "");
                    player.getPacketSender().sendString(id++, "");
                    player.getPacketSender().sendString(id++, "");
                }
                player.getPacketSender().sendString(65009, "Create");
                player.getPacketSender().sendString(65002, "Raiding Party: @whi@0");
                player.getPacketSender().sendInteractionOption("Invite", 2, true);
            }

            @Override
            public void leave(Player player) {
                if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null)
                    player.getMinigameAttributes().getRaidsAttributes().getParty().remove(player, true, true);

                player.getMinigameAttributes().getRaidsAttributes().setInsideRaid(false);

                if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null)
                    player.getMinigameAttributes().getRaidsAttributes().getParty().getPlayersInRaids().remove(player);

                player.getMovementQueue().setLockMovement(false);
                player.getPacketSender().sendTabInterface(GameSettings.ACHIEVEMENT_TAB, 45000);
                player.getPacketSender().sendDungeoneeringTabIcon(false);
            }

            @Override
            public void process(Player player) {
                if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null)
                    player.getMinigameAttributes().getRaidsAttributes().getParty().refreshInterface();
            }

			/*@Override
			public void onDeath(Player player) {
				player.moveTo(new Position(2815, 5511));
			}*/
        },

        PURO_PURO(new int[]{2556, 2630}, new int[]{4281, 4354}, false, true, true, false, false, true) {
        },
        ALUCARD_EVENT(new int[]{3550, 3650}, new int[]{3750, 3900}, true, true, true, false, false, true) {
            @Override
            public void process(Player player) {
                /*if (!GameSettings.alucardEvent)
                    player.moveTo(GameSettings.DEFAULT_POSITION);*/
            }
        },
        SLAYER_TOWER(new int[]{3400, 3460}, new int[]{3530, 3580}, true, true, true, false, true, true) {
        },
        SLAYER_DUNGEON(new int[]{2680, 2805}, new int[]{9990, 10075}, true, true, true, false, true, true) {
        },
        LIGHT_DUNGEON(new int[]{1978, 2005}, new int[]{4637, 4670}, false, true, true, false, true, true) {
        },
        AQUANITES(new int[]{1875, 1915}, new int[]{4350, 4380}, false, true, true, false, true, true) {
        },
        DEV(new int[]{2650, 2750}, new int[]{9550, 9720}, true, true, true, true, true, true) {
        },
        HOME_BANK(new int[]{1793, 1813}, new int[]{3772, 3799}, true, true, true, false, false, true) {
        },
        DEATHS_COFFERS(new int[]{3156, 3196}, new int[]{5706, 5746}, true, true, true, false, false, true) {
        },

        NEMESIS(new int[]{1800, 1850}, new int[]{5120, 5180}, true, true, true, false, true, true) {
            @Override
            public void enter(Player player) {
            }

            @Override
            public boolean canTeleport(Player player) {
                player.getPacketSender().sendMessage("You leave the Raid safely.");
                return true;
            }

			/*@Override
			public boolean handleKilledNPC(Player killer, NPC npc) {
				RecipeForDisaster.handleNPCDeath(killer, npc);
				return true;
			}*/

            public void logout(Player player) {
                if (player.getRegionInstance() != null)
                    player.getRegionInstance().destruct();
                player.moveTo(GameSettings.RAIDS_LOBBY_POSITION);
                player.gwdRaidLoot = false;
            }

            @Override
            public void leave(Player player) {
                if (player.getRegionInstance() != null)
                    player.getRegionInstance().destruct();
                player.moveTo(GameSettings.RAIDS_LOBBY_POSITION);
                player.gwdRaidLoot = false;
            }

            @Override
            public void onDeath(Player player) {
                leave(player);
            }
        },
        CHAOS_RAIDS(new int[]{1663, 1728}, new int[]{9854, 9920}, true, true, true, true, true, true) {

			/*@Override
			public void enter(Player player) {
			}*/

            @Override
            public boolean canTeleport(Player player) {
                player.getPacketSender().sendMessage("You leave the Raid safely.");
                return true;
            }

            @Override
            public void process(Player player) {

                if (player.getMinigameAttributes().getRaidsAttributes().getParty() == null)
                    leave(player);


                if (player.fallingObjects)
                    player.fallingObjectsTimer++;
                if (player.fallingObjectsTimer > 9) {
                    ChaosRaid.mechanicCrystals(player, player.instanceHeight);
                    player.fallingObjectsTimer = 0;
                }

                if (player.bleed)
                    player.bleedTimer++;
                if (player.bleedTimer > 20) {
                    player.dealDamage(new Hit(50, Hitmask.RED, CombatIcon.NONE));
                    player.bleedTimer = 0;
                }

                if (player.prayerDrain)
                    player.prayerDrainTimer++;
                if (player.prayerDrainTimer > 20) {
                    player.getSkillManager().setCurrentLevel(Skill.PRAYER, player.getSkillManager().getCurrentLevel(Skill.PRAYER) - 50);
                    player.prayerDrainTimer = 0;
                }


            }

            @Override
            public boolean handleKilledNPC(Player killer, NPC npc) {
                ChaosRaid.handleNPCDeath(killer, npc);
                return true;
            }

            public void logout(Player player) {
                leave(player);
            }

            @Override
            public void leave(Player player) {

                player.getPacketSender().sendCameraNeutrality();

                if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null) {
                    final RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();

                    World.getNpcs().forEach(n -> n.removeInstancedNpcs(CHAOS_RAIDS, party.getInstanceLevel()));
                }

                if (player.chaosLootWave >= 5) {
                    ChaosRewards.grantLoot(player);
                } else if (!player.chaosRaidLoot) {
                    if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null)
                        player.getMinigameAttributes().getRaidsAttributes().getParty().remove(player, true, true);

                    if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null)
                        player.getMinigameAttributes().getRaidsAttributes().getParty().getPlayersInRaids().remove(player);
                }

                player.getMinigameAttributes().getRaidsAttributes().setInsideRaid(false);

                player.moveTo(GameSettings.RAIDS_LOBBY_POSITION);

                player.getMovementQueue().setLockMovement(false);

            }

            @Override
            public void onDeath(Player player) {
                leave(player);
            }
        },
        SHR(new int[]{3328, 3391}, new int[]{9600, 9663}, true, true, true, true, true, true) {

			/*@Override
			public void enter(Player player) {
			}*/

            @Override
            public boolean canTeleport(Player player) {
                player.getPacketSender().sendMessage("You leave the Raid safely.");
                return true;
            }

            @Override
            public void process(Player player) {

                if (player.getRaidsParty() == null)
                    return;

                player.getPacketSender().sendWalkableInterface(368, true);

                if (player.strongholdLootFloor == 4) {
                    player.getPacketSender().sendString(369, "@yel@Party Leader: " + player.getRaidsParty().getOwner().getUsername().toString());
                    player.getPacketSender().sendString(370, "@yel@Raid Complete");
                    player.getPacketSender().sendString(371, "");
                    player.getPacketSender().sendString(372, "");
                    player.getPacketSender().sendString(373, "");
                } else {
                    player.getPacketSender().sendString(369, "@yel@Party Leader: " + player.getRaidsParty().getOwner().getUsername().toString());
                    player.getPacketSender().sendString(370, "@yel@Wave: " + player.strongholdRaidFloor);
                    player.getPacketSender().sendString(371, "@yel@Animator item: " + player.getRaidsParty().shrColor);
                    if (player.strongholdRaidFloor == 4)
                        player.getPacketSender().sendString(372, "@yel@Key Needed: " + ItemDefinition.forId(player.getRaidsParty().shrFloorFourBossKey).getName());
                    else if (player.strongholdRaidFloor == 3)
                        player.getPacketSender().sendString(372, "@yel@Key Needed: " + ItemDefinition.forId(player.getRaidsParty().shrFloorThreeBossKey).getName());
                    else if (player.strongholdRaidFloor == 2)
                        player.getPacketSender().sendString(372, "@yel@Key Needed: " + ItemDefinition.forId(player.getRaidsParty().shrFloorTwoBossKey).getName());
                    else if (player.strongholdRaidFloor == 1)
                        player.getPacketSender().sendString(372, "@yel@Key Needed: " + ItemDefinition.forId(player.getRaidsParty().shrFloorOneBossKey).getName());
                    player.getPacketSender().sendString(373, "");
                }


                if (player.getRaidsParty().getOwner() == player && RandomUtility.inclusiveRandom(100) == 1) {
                    com.arlania.world.content.minigames.impl.strongholdraids.Raid.assignColoredItem(player);
                }

            }

            @Override
            public boolean handleKilledNPC(Player killer, NPC npc) {
                Mobs.handleNPCDeath(killer, npc);
                return true;
            }

            public void logout(Player player) {
                leave(player);
            }

            @Override
            public void leave(Player player) {

                GroundItemManager.clearInstance(SHR, player.instanceHeight);
                com.arlania.world.content.minigames.impl.strongholdraids.Raid.leave(player);

            }

            @Override
            public void onDeath(Player player) {

                if (player.strongholdRaidDeaths < 3) {
                    player.strongholdRaidDeaths++;
                    player.getPacketSender().sendMessage("You've died " + player.strongholdRaidDeaths + " times in this raid.");
                    player.moveTo(new Position(3363, 9639, player.instanceHeight));
                } else {
                    leave(player);
                    player.getPacketSender().sendMessage("You were kicked for dying 3 times in the raid.");
                }
            }
        },

        DEFAULT(null, null, true, true, true, true, true, true) {

        };

        private final int[] x;
        private final int[] y;
        private final boolean multi;
        private final boolean summonAllowed;
        private final boolean followingAllowed;
        private final boolean cannonAllowed;
        private final boolean firemakingAllowed;
        private final boolean aidingAllowed;
        Location(int[] x, int[] y, boolean multi, boolean summonAllowed, boolean followingAllowed, boolean cannonAllowed, boolean firemakingAllowed, boolean aidingAllowed) {
            this.x = x;
            this.y = y;
            this.multi = multi;
            this.summonAllowed = summonAllowed;
            this.followingAllowed = followingAllowed;
            this.cannonAllowed = cannonAllowed;
            this.firemakingAllowed = firemakingAllowed;
            this.aidingAllowed = aidingAllowed;
        }

        public static boolean inMulti(Character gc) {
            if (gc.getLocation() == WILDERNESS) {
				/*int x = gc.getPosition().getX(), y = gc.getPosition().getY();

				if(x >= 3141 && x <= 3291 && y >= 10034 && y <= 10234)
					return true;
				if (x >= 3270 && x <= 3300 && y >= 3920 && y <= 3947) {
					return false;
				}
				if (x >= 3195 && x <= 3285 && y >= 3705 && y <= 3785 || x >= 3120 && x <= 3350 && y >= 3865 && y <= 3903
						|| x >= 3250 && x <= 3350 && y >= 3905 && y <= 3960 || x >= 3650 && y <= 3538
						|| x >= 3020 && x <= 3055 && y >= 3684 && y <= 3711
						|| x >= 3150 && x <= 3195 && y >= 2958 && y <= 3003)*/
                return true;

            }

            /*
             * New in multi boolean coords for outsite wilderness
             */
            int x = gc.getPosition().getX(), y = gc.getPosition().getY();
            if (x >= 3145 && x <= 3245 && y >= 3595 && y <= 3700 || x >= 2700 && x <= 2730 && y >= 9800 && y <= 9829
                    || x >= 3080 && x <= 3120 && y >= 5520 && y <= 5550) {
                return true;
            }

            return gc.getLocation().multi;
        }

        public static Location getLocation(Entity gc) {
            for (Location location : Location.values()) {
                if (location != Location.DEFAULT)
                    if (inLocation(gc, location))
                        return location;
            }
            return Location.DEFAULT;
        }

        public static boolean inLocation(Entity gc, Location location) {
            if (location == Location.DEFAULT) {
                return getLocation(gc) == Location.DEFAULT;
            }
			/*if(gc instanceof Player) {
				Player p = (Player)gc;
				if(location == Location.TRAWLER_GAME) {
					String state = FishingTrawler.getState(p);
					return (state != null && state.equals("PLAYING"));
				} else if(location == FIGHT_PITS_WAIT_ROOM || location == FIGHT_PITS) {
					String state = FightPit.getState(p), needed = (location == FIGHT_PITS_WAIT_ROOM) ? "WAITING" : "PLAYING";
					return (state != null && state.equals(needed));
				} else if(location == Location.SOULWARS) {
					return (SoulWars.redTeam.contains(p) || SoulWars.blueTeam.contains(p) && SoulWars.gameRunning);
				} else if(location == Location.SOULWARS_WAIT) {
					return SoulWars.isWithin(SoulWars.BLUE_LOBBY, p) || SoulWars.isWithin(SoulWars.RED_LOBBY, p);
				}
			}
			 */
            return inLocation(gc.getPosition().getX(), gc.getPosition().getY(), location);
        }

        public static boolean inLocation(int absX, int absY, Location location) {
            int checks = location.getX().length - 1;
            for (int i = 0; i <= checks; i += 2) {
                if (absX >= location.getX()[i] && absX <= location.getX()[i + 1]) {
                    if (absY >= location.getY()[i] && absY <= location.getY()[i + 1]) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * SHOULD AN ENTITY FOLLOW ANOTHER ENTITY NO MATTER THE DISTANCE BETWEEN THEM?
         **/

        //TODO add all raids/PVM areas
        public static boolean ignoreFollowDistance(Character character) {
            Location location = character.getLocation();
            return location == Location.FIGHT_CAVES || location == Location.RECIPE_FOR_DISASTER ||
                    location == Location.TEKTON ||
                    location == Location.SKELETAL_MYSTICS ||
                    location == Location.OLM ||
                    location == Location.PESTILENT_BLOAT ||
                    location == Location.MAIDEN_SUGADINTI ||
                    location == Location.VERZIK_VITUR ||
                    location == Location.GWD_RAID ||
                    location == Location.CERBERUS ||
                    location == Location.CHAOS_RAIDS ||
                    location == Location.INSTANCEDBOSSES;
        }

        public int[] getX() {
            return x;
        }

        public int[] getY() {
            return y;
        }

        public boolean canBeMoved(Position position) {
            return true;
        }

        public boolean isSummoningAllowed() {
            return summonAllowed;
        }

        public boolean isFollowingAllowed() {
            return followingAllowed;
        }

        public boolean isCannonAllowed() {
            return cannonAllowed;
        }

        public boolean isFiremakingAllowed() {
            return firemakingAllowed;
        }

        public boolean isAidingAllowed() {
            return aidingAllowed;
        }

        public void process(Player player) {

        }

        public boolean canTeleport(Player player) {
            return true;
        }

        public void login(Player player) {

        }

        public void enter(Player player) {

        }

        public void leave(Player player) {

        }

        public void logout(Player player) {

        }

        public void onDeath(Player player) {

        }

        public boolean handleKilledNPC(Player killer, NPC npc) {
            return false;
        }

        public boolean canAttack(Player player, Player target) {
            return false;
        }
    }
}
