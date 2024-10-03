package com.arlania.world.content.skill.impl.dungeoneering;

import com.arlania.GameSettings;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.*;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.CustomObjects;
import com.arlania.world.content.combat.prayer.CurseHandler;
import com.arlania.world.content.combat.prayer.PrayerHandler;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.entity.impl.GroundItemManager;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

/**
 * yeye
 *
 * @author Gabriel Hannason
 */
public class Dungeoneering {

    public static void start(final Player p) {
        p.getPacketSender().sendInterfaceRemoval();
		/*if(p.getRights() != PlayerRights.DEVELOPER) {
			p.getPacketSender().sendMessage("Dungeoneering isn't out yet.");
			return;
		}*/
        if (p.getMinigameAttributes().getDungeoneeringAttributes().getParty() == null) {
            DialogueManager.start(p, 111);
            return;
        }
        final DungeoneeringParty party = p.getMinigameAttributes().getDungeoneeringAttributes().getParty();
        if (party.hasEnteredDungeon())
            return;
        if (party.getDungeoneeringFloor() == null || party.getOwner() == null) {
            DialogueManager.start(p, 112);
            return;
        } else if (party.getComplexity() == -1) {
            DialogueManager.start(p, 113);
            return;
        }
        if (party.getOwner() != p) {
            p.getPacketSender().sendMessage("Only the party leader can start the dungeon.");
            return;
        }
        String plrCannotEnter = null;
        for (Player member : party.getPlayers()) {
            if (member != null) {
                member.getPacketSender().sendInterfaceRemoval();
                if (member.getSummoning().getFamiliar() != null) {
                    member.getPacketSender().sendMessage("You must dismiss your familiar before being allowed to enter a dungeon.");
                    p.getPacketSender().sendMessage(p.getUsername() + " has to dismiss their familiar before you can enter the dungeon.");
                    return;
                }
                for (Item t : member.getEquipment().getItems()) {
                    if (t != null && t.getId() > 0 && t.getId() != 15707) {
                        plrCannotEnter = member.getUsername();
                        break;
                    }
                }
                for (Item t : member.getInventory().getItems()) {
                    if (t != null && t.getId() > 0 && t.getId() != 15707) {
                        plrCannotEnter = member.getUsername();
                        break;
                    }
                }
                if (plrCannotEnter != null) {
                    p.getPacketSender().sendMessage("Your team cannot enter the dungeon because " + plrCannotEnter + " hasn't banked").sendMessage("all of their items.");
                    return;
                }
            }
        }
        party.enteredDungeon(true);
        final int height = p.getIndex() * 4;
        final int amt = party.getPlayers().size() >= 2 ? 35000 : 45000;
        for (Player member : party.getPlayers()) {
            if (member != null) {
                member.getPacketSender().sendInterfaceRemoval();
                member.getMinigameAttributes().getDungeoneeringAttributes().setDamageDealt(0);
                member.getMinigameAttributes().getDungeoneeringAttributes().setDeaths(0);
                member.setRegionInstance(null);
                member.getMovementQueue().reset();
                member.getClickDelay().reset();
                member.moveTo(new Position(party.getDungeoneeringFloor().getEntrance().getX(), party.getDungeoneeringFloor().getEntrance().getY(), height));
                member.getEquipment().resetItems().refreshItems();
                member.getInventory().resetItems().refreshItems();
                member.getInventory().add(18201, amt);
                ItemBinding.onDungeonEntrance(member);
                PrayerHandler.deactivateAll(member);
                CurseHandler.deactivateAll(member);
                for (Skill skill : Skill.values())
                    member.getSkillManager().setCurrentLevel(skill, member.getSkillManager().getMaxLevel(skill));
                member.getSkillManager().stopSkilling();
                member.getPacketSender().sendClientRightClickRemoval();
            }
        }
        party.setDeaths(0);
        party.setKills(0);
        party.sendMessage("Welcome to Dungeoneering floor " + (party.getDungeoneeringFloor().ordinal() + 1) + ", complexity level " + party.getComplexity() + ".");
        party.sendFrame(37508, "Party deaths: 0");
        party.sendFrame(37509, "Party kills: 0");
        TaskManager.submit(new Task(1) {
            @Override
            public void execute() {
                setupFloor(party, height);
                stop();
            }
        });
        p.getInventory().add(new Item(17489));
    }

    public static void leave(Player p, boolean resetTab, boolean leaveParty) {
        if (p.getMinigameAttributes().getDungeoneeringAttributes().getParty() != null) {
            p.getMinigameAttributes().getDungeoneeringAttributes().getParty().remove(p, resetTab, leaveParty);
        } else if (resetTab) {
            p.getPacketSender().sendTabInterface(GameSettings.QUESTS_TAB, 639);//
            p.getPacketSender().sendDungeoneeringTabIcon(false);
            p.getPacketSender().sendTab(GameSettings.QUESTS_TAB);
        }
    }

    public static void setupFloor(DungeoneeringParty party, int height) {
        /*
         * Spawning npcs
         */
        NPC smuggler = new NPC(11226, new Position(party.getDungeoneeringFloor().getSmugglerPosition().getX(), party.getDungeoneeringFloor().getSmugglerPosition().getY(), height));
        World.register(smuggler);
        party.getNpcs().add(smuggler);
        for (NPC n : party.getDungeoneeringFloor().getNpcs()[party.getComplexity() - 1]) {
            NPC npc = new NPC(n.getId(), n.getPosition().copy().setZ(height));
            World.register(npc);
            party.getNpcs().add(npc);
        }
        /*
         * Spawning objects
         */
        for (GameObject obj : party.getDungeoneeringFloor().getObjects()) {
            CustomObjects.spawnGlobalObjectWithinDistance(new GameObject(obj.getId(), obj.getPosition().copy().setZ(height)));
        }
    }


    public static boolean doingDungeoneering(Player p) {
        return p.getMinigameAttributes().getDungeoneeringAttributes().getParty() != null && p.getMinigameAttributes().getDungeoneeringAttributes().getParty().hasEnteredDungeon();
    }

    public static void handlePlayerDeath(Player player) {
        player.getMinigameAttributes().getDungeoneeringAttributes().incrementDeaths();

        DungeoneeringParty party = player.getMinigameAttributes().getDungeoneeringAttributes().getParty();
        Position pos = party.getDungeoneeringFloor().getEntrance();
        player.moveTo(new Position(pos.getX(), pos.getY(), player.getPosition().getZ()));
        party.sendMessage("@red@" + player.getUsername() + " has died and been moved to the lobby.");
        party.setDeaths(party.getDeaths() + 1);
        party.sendFrame(37508, "Party deaths: " + party.getDeaths());
    }

    private static final Item[] misc = {new Item(555, 121), new Item(557, 87), new Item(554, 81), new Item(565, 63), new Item(5678), new Item(560, 97), new Item(861, 1), new Item(892, 127), new Item(18161, 2), new Item(18159, 2), new Item(139, 1)};

    public static void handleNpcDeath(Player p, NPC n) {
        if (n.getPosition().getZ() == p.getPosition().getZ()) {
            DungeoneeringParty party = p.getMinigameAttributes().getDungeoneeringAttributes().getParty();
            if (!party.getNpcs().contains(n))
                return;
            party.getNpcs().remove(n);
            party.setKills(party.getKills() + 1);
            boolean boss = n.getId() == 2060 || n.getId() == 8549 || n.getId() == 1382 || n.getId() == 9939;
            if (boss) {
                party.setKilledBoss(true);
            }
            party.sendFrame(37509, "Party kills: " + party.getKills());
            int random = RandomUtility.inclusiveRandom(300);
            if (random >= 20 && random <= 45 || boss) {
                GroundItemManager.spawnGroundItem(p, new GroundItem(new Item(ItemBinding.getRandomBindableItem()), n.getPosition().copy(), "Dungeoneering", false, -1, false, -1));
                if (boss) {
                    party.sendMessage("@red@The boss has been slain! Feel free to exit the dungeon via tha northern ladder.");
                }
            } else if (random >= 100 && random <= 150) {
                int amt = 3000 + RandomUtility.inclusiveRandom(10000);
                GroundItemManager.spawnGroundItem(p, new GroundItem(new Item(18201, amt), n.getPosition().copy(), "Dungeoneering", false, -1, false, -1));
            } else if (random > 150 && random < 250)
                GroundItemManager.spawnGroundItem(p, new GroundItem(misc[RandomUtility.inclusiveRandom(misc.length - 1)], n.getPosition().copy(), "Dungeoneering", false, -1, false, -1));
        }
    }

    public static final int FORM_PARTY_INTERFACE = 27224;
    public static final int PARTY_INTERFACE = 26224;
    public static final int DUNGEONEERING_GATESTONE_ID = 17489;
}
