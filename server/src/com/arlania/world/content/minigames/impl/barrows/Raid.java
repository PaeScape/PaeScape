package com.arlania.world.content.minigames.impl.barrows;

import com.arlania.GameSettings;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Locations.Location;
import com.arlania.model.Position;
import com.arlania.model.RegionInstance;
import com.arlania.model.RegionInstance.RegionInstanceType;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.content.minigames.MinigameAttributes;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

/**
 * @author Gabriel Hannason
 * Wrote this quickly!!
 * Handles the RFD quest
 */
public class Raid {

    public static void Fight(Player player, int height) {
        player.getPacketSender().sendMessage("@red@Welcome to Barrows Raids!");
        player.moveTo(new Position(3501, 3572, height));
        player.setRegionInstance(new RegionInstance(player, RegionInstanceType.GRAVEYARD));

        final RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();

        if (player == party.getOwner())
            spawn(player);

    }

    private final static void spawn(Player player) {

        final RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();

        TaskManager.submit(new Task(2, player, true) {
            @Override
            public void execute() {
                if (party.getOwner().barrowsRaid) {

                    if (party.getOwner().barrowsRaidWave <= 20) {
                        party.getOwner().getPacketSender().sendMessage("@red@Spawning Wave: " + player.barrowsRaidWave);

                        int bosses = party.getOwner().barrowsRaidWave;

                        for (int i = 0; i < bosses; i++) {
                            randomBoss(player);
                        }
                    } else if (party.getOwner().barrowsRaidWave >= 21) {

                        String message = "<col=996633>" + party.getOwner().getUsername() + "'s raid party has just completed a " + player.barrowsLootWave + " wave Barrows Raid!";

                        for (Player member : party.getPlayers()) {
                            member.barrowsRaidLoot = true;
                            Rewards.grantLoot(member);
                            member.moveTo(GameSettings.RAIDS_LOBBY_POSITION);
                        }

                        for (Player member : party.getPlayers()) {
                            if (member.getRegionInstance() != null)
                                member.getRegionInstance().destruct();
                        }

                        //	World.sendMessage(message);
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

        party.getOwner().barrowsRaidKills++;

        for (Player member : party.getPlayers()) {
            member.totalBarrowsRaidKills++;
        }

        if (party.getOwner().barrowsRaidKills >= (party.getOwner().barrowsRaidWave)) {
            party.getOwner().barrowsRaidWave++;
            party.getOwner().barrowsRaidKills = 0;

            spawn(party.getOwner());
        }

    }


    public static void randomBoss(Player player) {

        final RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();

        //barrows brother ids
        int[] bosses = {2025, 2026, 2027, 2028, 2029, 2030};

        int randBoss = bosses[RandomUtility.inclusiveRandom(5)];
        int randSpawn = RandomUtility.inclusiveRandom(3) + 1;

        if (randBoss < 2025 || randBoss > 2030)
            randBoss = 2025;

        int spawnx = SpawnPos0.getX() + RandomUtility.inclusiveRandom(-3, 3);
        int spawny = SpawnPos0.getY() + RandomUtility.inclusiveRandom(-1, 1);
        int spawnz = party.getInstanceLevel();

        NPC n1 = new NPC(randBoss, new Position(spawnx, spawny, spawnz));
        World.register(n1);
        player.getRegionInstance().getNpcsList().add(n1);
        n1.setConstitution(n1.getConstitution() + (player.teamSize * 250));
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
                member.moveTo(new Position(3501, 3572, height));
                member.getPacketSender().sendInteractionOption("null", 2, true);
                member.barrowsRaid = true;
                member.barrowsRaidWave = 10;
                member.barrowsRaidKills = 0;
                member.barrowsLootWave = 0;
                member.getSkillManager().stopSkilling();
                member.getPacketSender().sendClientRightClickRemoval();
                teamsize++;
            } else {
                member.getMinigameAttributes().getRaidsAttributes().getParty().remove(member, true, true);
                return;
            }

        }

        for (Player members : party.getPlayers()) {
            members.teamSize = teamsize;
        }

        party.setInstanceLevel(height);

        World.getNpcs().forEach(n -> n.removeInstancedNpcs(Location.GRAVEYARD, party.getInstanceLevel()));

        for (Player player : party.getPlayers()) {
            player.getPacketSender().sendCameraNeutrality();
            player.getMinigameAttributes().getRaidsAttributes().setParty(party);
            player.getPacketSender().sendInteractionOption("null", 2, true);
            party.getPlayersInRaids().add(player);
            player.instanceHeight = height;
            player.barrowsRaidKills = 0;
            player.totalBarrowsRaidKills = 0;
            Fight(player, height);
        }


    }


    //private static Position lobbyTele = new Position(2442, 3087, 0);
    private static final Position SpawnPos0 = new Position(3501, 3572, 0);
    //private static Position SpawnPosFinal = new Position(2442, 3087, 0);


}
