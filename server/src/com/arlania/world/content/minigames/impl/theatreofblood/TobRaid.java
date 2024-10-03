package com.arlania.world.content.minigames.impl.theatreofblood;

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
public class TobRaid {

    public static void Bloat(Player player, int height) {
        player.getPacketSender().sendMessage("@red@The Pestilent Bloat appears!");
        player.moveTo(new Position(3301, 4447, height));
        player.setRegionInstance(new RegionInstance(player, RegionInstanceType.PESTILENT_BLOAT));

        if (player.getRaidsParty().getOwner() == player) {
            World.getNpcs().forEach(n -> n.removeInstancedNpcs(Location.PESTILENT_BLOAT, player.getPosition().getZ()));
            NPC bloat = new NPC(22359, new Position(3290, 4447, height));
            bloat.setDefaultConstitution(6000 * player.teamSize);
            bloat.setConstitution(6000 * player.teamSize);
            World.register(bloat);

            if (player.difficulty > 0)
                bloat.difficultyPerks(bloat, player.difficulty);

            player.getRegionInstance().getNpcsList().add(bloat);
        }

    }

    public static void Maiden(Player player, int height) {
        player.getPacketSender().sendMessage("@red@The Maiden of Sugadinti appears!");
        player.moveTo(new Position(3184, 4446, height));
        player.setRegionInstance(new RegionInstance(player, RegionInstanceType.MAIDEN_SUGADINTI));

        if (player.getRaidsParty().getOwner() == player) {
            World.getNpcs().forEach(n -> n.removeInstancedNpcs(Location.MAIDEN_SUGADINTI, player.getPosition().getZ()));
            NPC maiden = new NPC(22360, new Position(3162, 4444, height));
            maiden.setDefaultConstitution(6000 * player.teamSize);
            maiden.setConstitution(6000 * player.teamSize);
            World.register(maiden);

            if (player.difficulty > 0)
                maiden.difficultyPerks(maiden, player.difficulty);

            player.getRegionInstance().getNpcsList().add(maiden);
        }
    }

    public static void Vitur(Player player, int height) {
        player.getPacketSender().sendMessage("@red@Verzik Vitur appears!");
        player.moveTo(new Position(3168, 4309, height));
        player.setRegionInstance(new RegionInstance(player, RegionInstanceType.VERZIK_VITUR));

        if (player.getRaidsParty().getOwner() == player) {
            World.getNpcs().forEach(n -> n.removeInstancedNpcs(Location.VERZIK_VITUR, player.getPosition().getZ()));
            NPC vitur = new NPC(22374, new Position(3168, 4318, height));
            vitur.setDefaultConstitution(25000 * player.teamSize);
            vitur.setConstitution(25000 * player.teamSize);
            World.register(vitur);

            if (player.difficulty > 0)
                vitur.difficultyPerks(vitur, player.difficulty);

            player.getRegionInstance().getNpcsList().add(vitur);
        }
    }

    public static void leave(Player player) {
        Location.RAIDS_LOBBY.leave(player);
        if (player.getRegionInstance() != null)
            player.getRegionInstance().destruct();
        player.tobWave = 0;
    }

    public static void handleNPCDeath(final Player player, NPC n) {
        if (player.getRegionInstance() == null)
            return;

        player.getRegionInstance().getNpcsList().remove(n);

        int height = player.instanceHeight;

        final RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();

        if (player.tobWave == 0 && player.getLocation() == Location.PESTILENT_BLOAT && n.getId() == 22359) {
            for (Player member : party.getPlayers()) {
                if (member.getRegionInstance() != null)
                    member.getRegionInstance().destruct();
                member.tobWave = 1;
                Maiden(member, height);
            }
        } else if (player.tobWave == 1 && player.getLocation() == Location.MAIDEN_SUGADINTI && n.getId() == 22360) {
            for (Player member : party.getPlayers()) {
                if (member.getRegionInstance() != null)
                    member.getRegionInstance().destruct();
                member.tobWave = 2;
                Vitur(member, height);
            }
        } else if (player.tobWave == 2 && player.getLocation() == Location.VERZIK_VITUR && n.getId() == 22374) {
            for (Player member : party.getPlayers()) {

                member.getPacketSender().sendMessage("@red@You've complete the Theatre of Blood!");
                member.moveTo(lobbyTele);
                TobRewards.grantLoot(party, member);

                if (member.getRegionInstance() != null)
                    member.getRegionInstance().destruct();
                member.tobWave = 0;
                member.tobRaid = false;
                member.getMinigameAttributes().getRaidsAttributes().setInsideRaid(false);

                if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null)
                    player.getMinigameAttributes().getRaidsAttributes().getParty().getPlayersInRaids().remove(member);

            }
            String message = "<col=996633>" + party.getOwner().getUsername() + "'s raid party has just completed the Theatre of Blood!";
            //World.sendMessage(message);
        }

    }

    public static void spawnMinion(final Player p, final int height) {
        if (p.getRegionInstance() == null)
            return;
        TaskManager.submit(new Task(2, p, false) {
            @Override
            public void execute() {
                if (p.getRegionInstance() == null) {
                    stop();
                    return;
                }


                if (p.tobRaid) {
                    if (p.tobWave == 0) {
                        p.getPacketSender().sendMessage("@red@A Crawling Hand appears!");
                        int minion = 1648;
                        NPC n = new NPC(minion, new Position(3296, 4442, height));
                        World.register(n);
                        n.getCombatBuilder().attack(p);
                    } else if (p.tobWave == 1) {
                        p.getPacketSender().sendMessage("@red@A Blood Spawn appears!");
                        int minion = 22367;
                        NPC n = new NPC(minion, new Position(3170, 4444, height));
                        World.register(n);
                        n.getCombatBuilder().attack(p);
                    } else if (p.tobWave == 2) {
                        p.getPacketSender().sendMessage("@red@A Nylocas appears!");
                        int[] minion = {22382, 22383, 22385};
                        NPC n = new NPC(minion[RandomUtility.inclusiveRandom(minion.length - 1)], new Position(3162, 4318, height));
                        World.register(n);
                        n.getCombatBuilder().attack(p);
                    }
                }

                stop();
            }
        });
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
        final int height = p.getIndex() * 4;
        for (Player member : party.getPlayers()) {

            if (member.getLocation() == Location.RAIDS_LOBBY) {
                /*if(member.getGameMode() == GameMode.SEASONAL_IRONMAN) {
                    if (member.seasonalRaidsTeleports[3] == 0) {
                        member.getPacketSender().sendMessage("You can't join this raid without unlocking the teleport.");
                        member.getRaidsParty().remove(member, false, true);
                    }
                }*/
                member.getPacketSender().sendInterfaceRemoval();
                MinigameAttributes.RaidsAttributes raidsAttributes = member.getMinigameAttributes().getRaidsAttributes();
                raidsAttributes.setInsideRaid(true);
                member.setRegionInstance(null);
                member.getMovementQueue().reset();
                member.getClickDelay().reset();
                member.moveTo(new Position(3301, 4447, height));
                member.getPacketSender().sendInteractionOption("null", 2, true);
                member.tobRaid = true;
                member.tobWave = 0;
                member.getSkillManager().stopSkilling();
                member.getPacketSender().sendClientRightClickRemoval();
            } else {
                member.getMinigameAttributes().getRaidsAttributes().getParty().remove(member, true, true);
                return;
            }

        }

        int teamsize = 0;

        for (Player members : party.getPlayers()) {
            teamsize++;
        }

        for (Player members : party.getPlayers()) {
            members.teamSize = teamsize;
        }

        for (Player player : party.getPlayers()) {
            player.getPacketSender().sendCameraNeutrality();
            player.getMinigameAttributes().getRaidsAttributes().setParty(party);
            player.getPacketSender().sendInteractionOption("null", 2, true);
            party.getPlayersInRaids().add(player);
            player.tobWave = 0;
            player.instanceHeight = height;
            Bloat(player, height);
        }

        party.sendMessage("@red@Welcome to Theatre of Blood!");

        party.setInstanceLevel(height);


    }


    private static final int bloatId = 22359;
    private static final int maidenId = 22360;
    private static final int viturId = 22374;

    //private static final Position spawnPos = new Position(3165, 4309, 0);
    private static final Position bloatPos = new Position(3290, 4447, 0);
    private static final Position maidenPos = new Position(3162, 4444, 0);
    private static final Position verzikPos = new Position(3168, 4318, 0);

    private static final Position lobbyTele = new Position(3669, 3218, 0);
    private static final Position bloatTele = new Position(3301, 4447, 0);
    private static final Position maidenTele = new Position(3184, 4446, 0);
    private static final Position verzikTele = new Position(3168, 4309, 0);

    private static final Position BloatMinionSpawnPos = new Position(3296, 4442, 0);
    private static final Position MaidenMinionSpawnPos = new Position(3170, 4444, 0);
    private static final Position VerzikMinionSpawnPos = new Position(3162, 4318, 0);


}
