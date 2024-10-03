package com.arlania.world.content.minigames.impl.chaosraids;

import com.arlania.GameSettings;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.*;
import com.arlania.model.Locations.Location;
import com.arlania.model.RegionInstance.RegionInstanceType;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.content.minigames.MinigameAttributes;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

import java.util.ArrayList;

/**
 * @author Gabriel Hannason
 * Wrote this quickly!!
 * Handles the RFD quest
 */
public class ChaosRaid {

    public static void Fight(Player player, int height) {

        player.getPacketSender().sendMessage("@red@Welcome to Chaos Raids!");
        player.moveTo(new Position(1686, 9885, height));
        player.setRegionInstance(new RegionInstance(player, RegionInstanceType.CHAOS_RAID));

        final RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();

        if (player == party.getOwner())
            spawn(player);

    }

    private final static void spawn(Player player) {

        final RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();

        TaskManager.submit(new Task(2, player, true) {
            @Override
            public void execute() {
                if (party.getOwner().chaosRaid) {

                    if (party.getOwner().chaosRaidWave <= 10) {

                        for (Player member : party.getPlayers()) {
                            member.getPacketSender().sendMessage("@red@Spawning Wave: " + player.chaosRaidWave);
                        }

                        if (party.getOwner().chaosRaidWave >= 6) {
                            for (Player member : party.getPlayers()) {
                                member.chaosRaidLoot = true;
                                member.chaosLootWave = party.getOwner().chaosRaidWave - 1;
                                member.getPacketSender().sendMessage("Your party has been granted loot!");
                            }
                        }

                        if (party.getOwner().chaosRaidWave == 1 || party.getOwner().chaosRaidWave == 5 || party.getOwner().chaosRaidWave == 10)
                            randomMechanic(player);

                        int bosses = party.getOwner().chaosRaidWave;

                        for (int i = 0; i < bosses; i++) {
                            randomBoss(player);
                        }
                    } else if (party.getOwner().chaosRaidWave >= 11) {

                        for (Player member : party.getPlayers()) {
                            member.chaosRaidLoot = true;
                            member.chaosRaidWave = 10;
                            member.chaosLootWave = 10;
                            member.moveTo(GameSettings.RAIDS_LOBBY_POSITION);
                            if (member.getRegionInstance() != null)
                                member.getRegionInstance().destruct();

                        }

                    }
                }

                stop();
            }
        });
    }


    public static void handleNPCDeath(final Player player, NPC n) {
        if (player.getRegionInstance() == null)
            return;

        player.getRegionInstance().getNpcsList().remove(n);

        final RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();


        party.getOwner().chaosRaidKills++;

        if (party.getOwner().chaosRaidKills >= (party.getOwner().chaosRaidWave)) {

            for (Player member : party.getPlayers()) {
                member.chaosLootWave = party.getOwner().chaosRaidWave;
            }

            party.getOwner().chaosRaidWave++;
            party.getOwner().chaosRaidKills = 0;

            spawn(party.getOwner());
        }

    }


    public static void randomMechanic(Player player) {

        int randMechanic = RandomUtility.inclusiveRandom(5) + 1;

        final RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();

        switch (randMechanic) {

            case 1:
                if (player.fallingObjects)
                    randomMechanic(player);

                for (Player member : party.getPlayers()) {
                    member.fallingObjects = true;
                    member.getPacketSender().sendMessage("@red@Watch out for falling boulders!");
                }
                break;
            case 2:
                if (player.bleed)
                    randomMechanic(player);

                for (Player member : party.getPlayers()) {
                    member.bleed = true;
                    member.getPacketSender().sendMessage("@red@You are starting to slowly bleed!");
                }
                break;
            case 3:
                if (player.prayerDrain)
                    randomMechanic(player);

                for (Player member : party.getPlayers()) {
                    member.prayerDrain = true;
                    member.getPacketSender().sendMessage("@red@You feel your prayer slowly draining!");
                }
                break;
            case 4:
                if (player.npcImmuneMelee || player.npcImmuneRange || player.npcImmuneMagic) {
                    randomMechanic(player);
                    break;
                }

                for (Player member : party.getPlayers()) {
                    member.npcImmuneMelee = true;
                    member.getPacketSender().sendMessage("@red@BOSSES ARE NOW IMMUNE TO YOUR MELEE ATTACKS!");
                }
                break;
            case 5:
                if (player.npcImmuneMelee || player.npcImmuneRange || player.npcImmuneMagic) {
                    randomMechanic(player);
                    break;
                }

                for (Player member : party.getPlayers()) {
                    member.npcImmuneRange = true;
                    member.getPacketSender().sendMessage("@red@BOSSES ARE NOW IMMUNE TO YOUR RANGED ATTACKS!");
                }
                break;
            case 6:
                if (player.npcImmuneMelee || player.npcImmuneRange || player.npcImmuneMagic) {
                    randomMechanic(player);
                    break;
                }

                for (Player member : party.getPlayers()) {
                    member.npcImmuneMagic = true;
                    member.getPacketSender().sendMessage("@red@BOSSES ARE NOW IMMUNE TO YOUR MAGIC ATTACKS!");
                }
                break;

        }


    }

    public static void randomBoss(Player player) {

        final RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();

        //Warmonger, Cursebearer, Skotizo, plane-freezer
        int[] bosses = {2882, 2881, 2883, 50, 3200, 1999, 2000, 2001, 6203, 6247, 6260,
                8133, 5886, 8349, 2006, 2009, 7286, 2042, 2043, 2044, 3334, 3340, 499};

        int randBoss = bosses[RandomUtility.inclusiveRandom(0, 21)];
        int randSpawn = RandomUtility.inclusiveRandom(3) + 1;

        if (randBoss < 50 || randBoss > 7286)
            randBoss = 50;

        int spawnx = SpawnPos0.getX() + RandomUtility.inclusiveRandom(-5, 5);
        int spawny = SpawnPos0.getY() + RandomUtility.inclusiveRandom(-5, 5);
        int spawnz = party.getInstanceLevel();

        NPC n1 = new NPC(randBoss, new Position(spawnx, spawny, spawnz));
        n1.setConstitution(2500 * player.teamSize);
        n1.setDefaultConstitution(2500 * player.teamSize);
        World.register(n1);

        if (player.difficulty > 0)
            n1.difficultyPerks(n1, player.difficulty);

        player.getRegionInstance().getNpcsList().add(n1);
        n1.getCombatBuilder().attack(player);
    }

    public static void start(final Player p) {
        p.getPacketSender().sendInterfaceRemoval();
        if (p.getMinigameAttributes().getRaidsAttributes().getParty() == null) {
            DialogueManager.sendStatement(p, "You need to be in a Raids party to begin.");
            return;
        }
        final RaidsParty party = p.getMinigameAttributes().getRaidsAttributes().getParty();


        if (party.getOwner() != p) {
            p.getPacketSender().sendMessage("Only the party leader can start the fight.");
            return;
        }

        int teamsize = 0;


        final int height = p.getIndex() * 4;
        for (Player member : party.getPlayers()) {

            if (member.getLocation() == Location.RAIDS_LOBBY) {
                member.getPacketSender().sendInterfaceRemoval();
                MinigameAttributes.RaidsAttributes raidsAttributes = member.getMinigameAttributes().getRaidsAttributes();
                raidsAttributes.setInsideRaid(true);
                member.setRegionInstance(null);
                member.getMovementQueue().reset();
                member.getClickDelay().reset();
                member.moveTo(new Position(1686, 9885, height));
                member.getPacketSender().sendInteractionOption("null", 2, true);
                member.chaosRaid = true;
                member.chaosRaidWave = 1;
                member.chaosRaidKills = 0;
                member.chaosRaidLoot = false;
                member.prayerDrain = false;
                member.bleed = false;
                member.npcMaxAccuracy = false;
                member.npcMaxHit = false;
                member.fallingObjects = false;
                member.npcImmuneMelee = false;
                member.npcImmuneRange = false;
                member.npcImmuneMagic = false;

                member.getSkillManager().stopSkilling();
                member.getPacketSender().sendClientRightClickRemoval();
                teamsize++;
            } else {
                member.getMinigameAttributes().getRaidsAttributes().getParty().remove(member, true, true);
                return;
            }

            for (Player members : party.getPlayers()) {
                members.teamSize = teamsize;
            }
        }

        party.setInstanceLevel(height);

        World.getNpcs().forEach(n -> n.removeInstancedNpcs(Location.CHAOS_RAIDS, party.getInstanceLevel()));

        for (Player player : party.getPlayers()) {
            player.getPacketSender().sendCameraNeutrality();
            player.getMinigameAttributes().getRaidsAttributes().setParty(party);
            player.getPacketSender().sendInteractionOption("null", 2, true);
            party.getPlayersInRaids().add(player);
            player.instanceHeight = height;
            player.tempRaidsDifficulty = party.getOwner().difficulty;
            Fight(player, height);
        }
    }

    public static void mechanicCrystals(Player player, int height) {
        Position posPlayer = player.getPosition();
        NPC spawn = NPC.of(5090, posPlayer);
        World.register(spawn);
        NPC spawn1 = NPC.of(5090, new Position(posPlayer.getX(), posPlayer.getY() - 1, posPlayer.getZ()));
        World.register(spawn1);
        //new Projectile(spawn1, spawn, 1358 + GameSettings.OSRS_GFX_OFFSET, 140, 1, 220, 0, 0).sendProjectile();
        new Projectile(spawn1, spawn, 570, 140, 1, 220, 0, 0).sendProjectile();
        //new Projectile(spawn1, spawn, 559, 140, 1, 220, 0, 0).sendProjectile();

        TaskManager.submit(new Task(4) {
            @Override
            public void execute() {
                player.getPacketSender().sendGlobalGraphic(new Graphic(311), posPlayer);

                if (player.getPosition().sameAs(posPlayer)) {
                    player.dealDamage(new Hit(RandomUtility.inclusiveRandom(150, 300), Hitmask.RED, CombatIcon.NONE));
                } else if (Locations.goodDistance(player.getPosition(), posPlayer, 1)) {
                    player.dealDamage(new Hit(RandomUtility.inclusiveRandom(100, 200), Hitmask.RED, CombatIcon.NONE));
                }


                World.deregister(spawn);
                World.deregister(spawn1);
                stop();
            }
        });

        dropCrystal(player, 0);

    }

    public static void dropCrystal(Player player, int height) {

        Position pos = randomLocation(height);
        NPC spawn = NPC.of(5090, pos);
        World.register(spawn);
        NPC spawn1 = NPC.of(5090, new Position(pos.getX(), pos.getY() - 1, height));
        World.register(spawn1);
        new Projectile(spawn1, spawn, 559, 140, 1, 220, 0, 0).sendProjectile();

        TaskManager.submit(new Task(4) {

            @Override
            public void execute() {
                player.getPacketSender().sendGlobalGraphic(new Graphic(311), pos);


                if (player.getPosition().sameAs(pos)) {
                    player.dealDamage(new Hit(RandomUtility.inclusiveRandom(150, 300), Hitmask.RED, CombatIcon.NONE));
                } else if (Locations.goodDistance(player.getPosition(), pos, 1)) {
                    player.dealDamage(new Hit(RandomUtility.inclusiveRandom(100, 200), Hitmask.RED, CombatIcon.NONE));
                }

                World.deregister(spawn);
                World.deregister(spawn1);
                stop();
            }
        });
    }


    public static Position randomLocation(int height) {
        ArrayList<Position> positions = new ArrayList<>();

        for (int x = 0; x < 34; x++) {
            for (int y = 0; y < 34; y++) {
                if (x != 1679 && y != 9872)
                    positions.add(new Position(2462 + x, 4781 + y, height));
            }
        }

        return positions.get(RandomUtility.inclusiveRandom(positions.size() - 1));
    }


    //private static Position lobbyTele = new Position(2442, 3087, 0);
    private static final Position SpawnPos1 = new Position(1702, 9892, 0);
    private static final Position SpawnPos2 = new Position(1702, 9874, 0);
    private static final Position SpawnPos3 = new Position(1688, 9892, 0);
    private static final Position SpawnPos4 = new Position(1688, 9874, 0);
    private static final Position SpawnPos0 = new Position(1693, 9887, 0);
    //private static Position SpawnPosFinal = new Position(2442, 3087, 0);


}
