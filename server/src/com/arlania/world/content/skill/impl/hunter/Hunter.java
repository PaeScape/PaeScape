package com.arlania.world.content.skill.impl.hunter;

import com.arlania.engine.task.TaskManager;
import com.arlania.engine.task.impl.HunterTrapsTask;
import com.arlania.engine.task.impl.NPCRespawnTask;
import com.arlania.model.*;
import com.arlania.model.container.impl.Equipment;
import com.arlania.model.movement.MovementQueue;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.CustomObjects;
import com.arlania.world.content.skill.impl.hunter.Trap.TrapState;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Hunter {

    /**
     * Registers a new Trap
     *
     * @param trap
     */
    public static void register(final Trap trap) {
        CustomObjects.spawnGlobalObject(trap.getGameObject());
        traps.add(trap);
        if (trap.getOwner() != null)
            trap.getOwner().setTrapsLaid(trap.getOwner().getTrapsLaid() + 1);
    }

    /**
     * Unregisters a trap
     *
     * @param trap
     */
    public static void deregister(Trap trap) {
        CustomObjects.deleteGlobalObject(trap.getGameObject());
        traps.remove(trap); // Remove the Trap
        if (trap.getOwner() != null)
            trap.getOwner().setTrapsLaid(trap.getOwner().getTrapsLaid() - 1);
    }

    /**
     * The list which contains all Traps
     */
    public static List<Trap> traps = new CopyOnWriteArrayList<Trap>();

    /**
     * The Hash map which contains all Hunting NPCS
     */
    public static List<NPC> HUNTER_NPC_LIST = new CopyOnWriteArrayList<NPC>();

    private static final int[] exps = {25, 50, 75, 100, 125, 150, 200, 250};


    /**
     * Can this client lay a trap here?
     *
     * @param client
     */
    public static boolean canLay(Player client) {
        if (!goodArea(client)) {
            client.getPacketSender().sendMessage(
                    "You need to be in a hunting area to lay a trap.");
            return false;
        }
        if (!client.getClickDelay().elapsed(2000))
            return false;
        for (final Trap trap : traps) {
            if (trap == null)
                continue;
            if (trap.getGameObject().getPosition().getX() == client.getPosition().getX()
                    && trap.getGameObject().getPosition().getY() == client
                    .getPosition().getY()) {
                client.getPacketSender()
                        .sendMessage(
                                "There is already a trap here, please place yours somewhere else.");
                return false;
            }
        }
        int x = client.getPosition().getX();
        int y = client.getPosition().getY();
        for (final NPC npc : HUNTER_NPC_LIST) {
            if (npc == null || !npc.isVisible())
                continue;
            if (x == npc.getPosition().getX() && y == npc.getPosition().getY() || x == npc.getDefaultPosition().getX() && y == npc.getDefaultPosition().getY()) {
                client.getPacketSender().sendMessage(
                        "You cannot place your trap right here, try placing it somewhere else.");

                return false;
            }
        }
        if (client.getTrapsLaid() >= getMaximumTraps(client)) {
            client.getPacketSender().sendMessage(
                    "You can only have a max of " + getMaximumTraps(client)
                            + " traps setup at once.");
            return false;
        }
        return true;
    }


    public static void handleRegionChange(Player client) {
        if (client.getTrapsLaid() > 0) {
            for (final Trap trap : traps) {
                if (trap == null)
                    continue;
                if (trap.getOwner() != null && trap.getOwner().getUsername().equals(client.getUsername()) && !Locations.goodDistance(trap.getGameObject().getPosition(), client.getPosition(), 50)) {
                    deregister(trap);
                    client.getPacketSender().sendMessage("You didn't watch over your trap well enough, it has collapsed.");
                }
            }
        }
    }

    /**
     * Checks if the user is in the area where you can lay boxes.
     *
     * @param client
     * @return
     */
    public static boolean goodArea(Player client) {
        int x = client.getPosition().getX();
        int y = client.getPosition().getY();
        return x >= 2758 && x <= 2965 && y >= 2880 && y <= 2954;
    }

    /**
     * Returns the maximum amount of traps this player can have
     *
     * @param client
     * @return
     */
    public static int getMaximumTraps(Player client) {
        return client.getSkillManager().getCurrentLevel(Skill.HUNTER) / 20 + 1;
    }

    /**
     * Gets the ObjectID required by NPC ID
     *
     * @param npcId
     */
    public static int getObjectIDByNPCID(int npcId) {
        switch (npcId) {
            case 5073:
                return 19180;
            case 5079:
                return 19191;
            case 5080:
                return 19189;
            case 5075:
                return 19184;
            case 5076:
                return 19186;
            case 5074:
                return 19182;
            case 5072:
                return 19178;
        }
        return 0;
    }

    /**
     * Searches the specific Trap that belongs to this WorldObject
     *
     * @param object
     */
    public static Trap getTrapForGameObject(final GameObject object) {
        for (final Trap trap : traps) {
            if (trap == null)
                continue;
            if (trap.getGameObject().getPosition().equals(object.getPosition()))
                return trap;
        }
        return null;
    }

    /**
     * Dismantles a Trap
     *
     * @param client
     */
    public static void dismantle(Player client, GameObject trap) {
        if (trap == null)
            return;
        final Trap theTrap = getTrapForGameObject(trap);
        if (theTrap != null && theTrap.getOwner() == client) {
            deregister(theTrap);
            if (theTrap instanceof SnareTrap)
                client.getInventory().add(10006, 1);
            else if (theTrap instanceof BoxTrap) {
                client.getInventory().add(10008, 1);
                client.performAnimation(new Animation(827));
            }
            client.getPacketSender().sendMessage("You dismantle the trap..");
        } else
            client.getPacketSender().sendMessage(
                    "You cannot dismantle someone else's trap.");
    }

    /**
     * Sets up a trap
     *
     * @param client
     * @param trap
     */
    public static void layTrap(Player client, Trap trap) {
        int id = 10006;
        if (trap instanceof BoxTrap) {
            id = 10008;
            if (client.getSkillManager().getCurrentLevel(Skill.HUNTER) < 60) {
                client.getPacketSender().sendMessage("You need a Hunter level of at least 60 to lay this trap.");
                return;
            }
        }
        if (!client.getInventory().contains(id))
            return;
        if (canLay(client)) {
            register(trap);
            client.getClickDelay().reset();
            client.getMovementQueue().reset();
            MovementQueue.stepAway(client);
            client.setPositionToFace(trap.getGameObject().getPosition());
            client.performAnimation(new Animation(827));
            if (trap instanceof SnareTrap) {
                client.getPacketSender().sendMessage("You set up a bird snare..");
                client.getInventory().delete(10006, 1);
            } else if (trap instanceof BoxTrap) {
                if (client.getSkillManager().getCurrentLevel(Skill.HUNTER) < 27) {
                    client.getPacketSender().sendMessage("You need a Hunter level of at least 27 to do this.");
                    return;
                }
                client.getPacketSender().sendMessage("You set up a box trap..");
                client.getInventory().delete(10008, 1);
            }
            HunterTrapsTask.fireTask();
        }
    }

    /**
     * Gets the required level for the NPC.
     *
     * @param npcType
     */
    public static int requiredLevel(int npcType) {
        int levelToReturn = 1;
        if (npcType == 5072)
            levelToReturn = 19;
        else if (npcType == 5072)
            levelToReturn = 1;
        else if (npcType == 5074)
            levelToReturn = 11;
        else if (npcType == 5075)
            levelToReturn = 5;
        else if (npcType == 5076)
            levelToReturn = 9;
        else if (npcType == 5079)
            levelToReturn = 53;
        else if (npcType == 5080)
            levelToReturn = 63;
        return levelToReturn;
    }

    public static boolean isHunterNPC(int npc) {
        return npc >= 5072 && npc <= 5080;
    }

    public static void lootTrap(Player client, GameObject trap) {
        if (trap != null) {
            client.setPositionToFace(trap.getPosition());
            final Trap theTrap = getTrapForGameObject(trap);
            if (theTrap != null) {
                if (theTrap.getOwner() != null)
                    if (theTrap.getOwner() == client) {
                        if (theTrap instanceof SnareTrap) {
                            client.getInventory().add(10006, 1);
                            client.getInventory().add(526, 1);
                            if (theTrap.getGameObject().getId() == 19180) {
                                client.getInventory().add(10088, 20 + RandomUtility.inclusiveRandom(30));
                                client.getInventory().add(9978, 1);
                                client.getPacketSender()
                                        .sendMessage("You've succesfully caught a crimson swift.");
                                client.getSkillManager().addExperience(Skill.HUNTER, exps[0]);
                            } else if (theTrap.getGameObject().getId() == 19184) {
                                client.getInventory().add(10090, 20 + RandomUtility.inclusiveRandom(30));
                                client.getInventory().add(9978, 1);
                                client.getPacketSender()
                                        .sendMessage(
                                                "You've succesfully caught a Golden Warbler.");
                                client.getSkillManager().addExperience(Skill.HUNTER, exps[1]);
                            } else if (theTrap.getGameObject().getId() == 19186) {
                                client.getInventory().add(10091,
                                        20 + RandomUtility.inclusiveRandom(50));
                                client.getInventory().add(9978, 1);
                                client.getPacketSender()
                                        .sendMessage(
                                                "You've succesfully caught a Copper Longtail.");
                                client.getSkillManager().addExperience(Skill.HUNTER, exps[2]);
                            } else if (theTrap.getGameObject().getId() == 19182) {
                                client.getInventory().add(10089,
                                        20 + RandomUtility.inclusiveRandom(30));
                                client.getInventory().add(9978, 1);
                                client.getPacketSender()
                                        .sendMessage(
                                                "You've succesfully caught a Cerulean Twitch.");
                                client.getSkillManager().addExperience(Skill.HUNTER, (exps[3]));
                            } else if (theTrap.getGameObject().getId() == 19178) {
                                client.getInventory().add(10087,
                                        20 + RandomUtility.inclusiveRandom(30));
                                client.getInventory().add(9978, 1);
                                client.getPacketSender()
                                        .sendMessage(
                                                "You've succesfully caught a Tropical Wagtail.");
                                client.getSkillManager().addExperience(Skill.HUNTER, exps[4]);
                            }
                        } else if (theTrap instanceof BoxTrap) {
                            client.getInventory().add(10008, 1);
                            if (theTrap.getGameObject().getId() == 19191) {
                                client.getInventory().add(10033, RandomUtility.inclusiveRandom(2, 10));
                                client.getSkillManager().addExperience(Skill.HUNTER, exps[6]);
                                client.getPacketSender().sendMessage(
                                        "You've succesfully caught some chinchompas!");
                                NPC chin = new NPC(5080, theTrap.getGameObject().getPosition());
                                TaskManager.submit(new NPCRespawnTask(chin, 10));
                            } else if (theTrap.getGameObject().getId() == 19189) {
                                client.getInventory().add(10034, RandomUtility.inclusiveRandom(2, 10));
                                client.getSkillManager().addExperience(Skill.HUNTER, exps[7]);
                                client.getPacketSender()
                                        .sendMessage(
                                                "You've succesfully caught some red chinchompas!");
                                NPC chin = new NPC(5080, theTrap.getGameObject().getPosition());
                                TaskManager.submit(new NPCRespawnTask(chin, 10));
                            }
                        }
                        deregister(theTrap);
                        client.performAnimation(new Animation(827));
                    } else
                        client.getPacketSender().sendMessage(
                                "This is not your trap.");
            }
        }

    }

    /**
     * Try to catch an NPC
     *
     * @param trap
     * @param npc
     */
    public static void catchNPC(Trap trap, NPC npc) {
        if (trap.getTrapState().equals(TrapState.CAUGHT))
            return;
        if (trap.getOwner() != null) {
            if (trap.getOwner().getSkillManager().getCurrentLevel(Skill.HUNTER) < requiredLevel(npc.getId())) {
                trap
                        .getOwner()
                        .getPacketSender()
                        .sendMessage(
                                "You failed to catch the animal because your Hunter level is too low.");
                trap
                        .getOwner()
                        .getPacketSender()
                        .sendMessage(
                                "You need atleast "
                                        + requiredLevel(npc.getId())
                                        + " Hunter to catch this animal");
                return;
            }
            deregister(trap);
            if (trap instanceof SnareTrap)
                register(new SnareTrap(new GameObject(getObjectIDByNPCID(npc.getId()), new Position(trap.getGameObject().getPosition().getX(), trap.getGameObject().getPosition().getY())), Trap.TrapState.CAUGHT, 100, trap.getOwner()));
            else
                register(new BoxTrap(new GameObject(getObjectIDByNPCID(npc.getId()), new Position(trap.getGameObject().getPosition().getX(), trap.getGameObject().getPosition().getY())), Trap.TrapState.CAUGHT, 100, trap.getOwner()));
            HUNTER_NPC_LIST.remove(npc);
            npc.setVisible(false);
            npc.appendDeath();
        }
    }

    public static boolean hasLarupia(Player client) {
        return client.getEquipment().getItems()[Equipment.HEAD_SLOT].getId() == 10045 && client.getEquipment().getItems()[Equipment.BODY_SLOT].getId() == 10043 && client.getEquipment().getItems()[Equipment.LEG_SLOT].getId() == 10041;
    }

    public static void handleLogout(Player p) {
        if (p.getTrapsLaid() > 0) {
            for (Trap trap : traps) {
                if (trap != null) {
                    if (trap.getOwner() != null && trap.getOwner().getUsername().equals(p.getUsername())) {
                        deregister(trap);
                        if (trap instanceof SnareTrap)
                            p.getInventory().add(10006, 1);
                        else if (trap instanceof BoxTrap) {
                            p.getInventory().add(10008, 1);
                            p.performAnimation(new Animation(827));
                        }
                    }
                }
            }
        }
    }
}
