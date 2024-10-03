package com.arlania.world.content.minigames.impl.gwdraids;

import com.arlania.GameSettings;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Locations.Location;
import com.arlania.model.Position;
import com.arlania.model.RegionInstance;
import com.arlania.model.RegionInstance.RegionInstanceType;
import com.arlania.world.World;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.content.minigames.MinigameAttributes;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class GwdRaid {

    public static void Fight(Player player, int height) {
        player.getPacketSender().sendMessage("@red@Welcome to GWD Raids!");
        player.gwdRaid = true;
        player.gwdRaidKills = 0;
        player.gwdRaidWave = 0;
        player.getPacketSender().sendInterfaceRemoval();
        player.moveTo(new Position(2897, 5266, height));
        player.setRegionInstance(new RegionInstance(player, RegionInstanceType.GWD_RAID));
        final RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();

        if (player == party.getOwner())
            spawn(player);

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

        for (Player members : party.getPlayers()) {
            teamsize++;
        }

        for (Player members : party.getPlayers()) {
            members.teamSize = teamsize;
        }

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
                member.gwdRaid = true;
                member.gwdRaidWave = 0;
                member.gwdRaidKills = 0;
                member.gwdRaidLoot = false;
                member.getSkillManager().stopSkilling();
                member.getPacketSender().sendClientRightClickRemoval();
            } else {
                member.getMinigameAttributes().getRaidsAttributes().getParty().remove(member, true, true);
                return;
            }

        }

        party.setInstanceLevel(height);

        World.getNpcs().forEach(n -> n.removeInstancedNpcs(Location.GRAVEYARD, party.getInstanceLevel()));

        party.nexMinionKills = 0;

        for (Player player : party.getPlayers()) {
            player.getPacketSender().sendCameraNeutrality();
            player.getMinigameAttributes().getRaidsAttributes().setParty(party);
            player.getPacketSender().sendInteractionOption("null", 2, true);
            player.getMinigameAttributes().getRaidsAttributes().setInsideRaid(true);
            party.getPlayersInRaids().add(player);
            player.instanceHeight = height;
            Fight(player, height);
        }


    }

    public static void leave(Player player) {
        Location.GWD_RAID.leave(player);
        if (player.getRegionInstance() != null)
            player.getRegionInstance().destruct();
    }

    private final static void spawn(Player player) {
        TaskManager.submit(new Task(4, player, false) {
            @Override
            public void execute() {
                if (player.getRegionInstance() == null) {
                    player.getPacketSender().sendMessage("debug1");
                    stop();
                    return;
                }
                if (!player.isRegistered()) {
                    player.getPacketSender().sendMessage("debug2");
                    stop();
                    return;
                }
                if (player.getLocation() != Location.GWD_RAID) {
                    player.getPacketSender().sendMessage("debug3");
                    stop();
                    return;
                }


                if (player.gwdRaid) {
                    if (player.gwdRaidKills == 0) {
                        NPC n1 = new NPC(20492, new Position(bossSpawnPos.getX(), bossSpawnPos.getY(), player.getPosition().getZ()));
                        n1.setDefaultConstitution(10000 * player.teamSize);
                        n1.setConstitution(10000 * player.teamSize);
                        World.register(n1);

                        if (player.difficulty > 0)
                            n1.difficultyPerks(n1, player.difficulty);

                        player.getRegionInstance().getNpcsList().add(n1);
                        n1.getCombatBuilder().attack(player);
                    } else if (player.gwdRaidKills == 1) {
                        NPC n1 = new NPC(20493, new Position(bossSpawnPos.getX(), bossSpawnPos.getY(), player.getPosition().getZ()));
                        n1.setDefaultConstitution(10000 * player.teamSize);
                        n1.setConstitution(10000 * player.teamSize);
                        World.register(n1);

                        if (player.difficulty > 0)
                            n1.difficultyPerks(n1, player.difficulty);

                        player.getRegionInstance().getNpcsList().add(n1);
                        n1.getCombatBuilder().attack(player);
                    } else if (player.gwdRaidKills == 2) {
                        NPC n1 = new NPC(20494, new Position(bossSpawnPos.getX(), bossSpawnPos.getY(), player.getPosition().getZ()));
                        n1.setDefaultConstitution(10000 * player.teamSize);
                        n1.setConstitution(10000 * player.teamSize);
                        World.register(n1);

                        if (player.difficulty > 0)
                            n1.difficultyPerks(n1, player.difficulty);

                        player.getRegionInstance().getNpcsList().add(n1);
                        n1.getCombatBuilder().attack(player);
                    } else if (player.gwdRaidKills == 3) {
                        NPC n1 = new NPC(20495, new Position(bossSpawnPos.getX(), bossSpawnPos.getY(), player.getPosition().getZ()));
                        n1.setDefaultConstitution(10000 * player.teamSize);
                        n1.setConstitution(10000 * player.teamSize);
                        World.register(n1);

                        if (player.difficulty > 0)
                            n1.difficultyPerks(n1, player.difficulty);

                        player.getRegionInstance().getNpcsList().add(n1);
                        n1.getCombatBuilder().attack(player);
                    } else if (player.gwdRaidKills == 4) {
                        //Nex
                        NPC nex = new NPC(13447, new Position(nexSpawnPos.getX(), nexSpawnPos.getY(), player.getPosition().getZ()));
                        nex.setDefaultConstitution(20000 * player.teamSize);
                        nex.setConstitution(20000 * player.teamSize);
                        World.register(nex);

                        if (player.difficulty > 0)
                            nex.difficultyPerks(nex, player.difficulty);

                        player.getRegionInstance().getNpcsList().add(nex);
                        nex.getCombatBuilder().attack(player);

                        //Fumus
                        NPC fumus = new NPC(13451, new Position(fumusSpawnPos.getX(), fumusSpawnPos.getY(), player.getPosition().getZ()));
                        fumus.setDefaultConstitution(5000 * player.teamSize);
                        fumus.setConstitution(5000 * player.teamSize);
                        World.register(fumus);
                        fumus.setFreezeDelay(100000);

                        if (player.difficulty > 0)
                            fumus.difficultyPerks(fumus, player.difficulty);

                        player.getRegionInstance().getNpcsList().add(fumus);
                        fumus.getCombatBuilder().attack(player);

                        //Umbra
                        NPC umbra = new NPC(13452, new Position(umbraSpawnPos.getX(), umbraSpawnPos.getY(), player.getPosition().getZ()));
                        umbra.setDefaultConstitution(5000 * player.teamSize);
                        umbra.setConstitution(5000 * player.teamSize);
                        World.register(umbra);
                        umbra.setFreezeDelay(100000);

                        if (player.difficulty > 0)
                            umbra.difficultyPerks(umbra, player.difficulty);

                        player.getRegionInstance().getNpcsList().add(umbra);
                        umbra.getCombatBuilder().attack(player);

                        //Curor
                        NPC curor = new NPC(13453, new Position(curorSpawnPos.getX(), curorSpawnPos.getY(), player.getPosition().getZ()));
                        curor.setDefaultConstitution(5000 * player.teamSize);
                        curor.setConstitution(5000 * player.teamSize);
                        World.register(curor);
                        curor.setFreezeDelay(100000);

                        if (player.difficulty > 0)
                            curor.difficultyPerks(curor, player.difficulty);

                        player.getRegionInstance().getNpcsList().add(curor);
                        curor.getCombatBuilder().attack(player);

                        //Glacies
                        NPC glacies = new NPC(13454, new Position(glaciesSpawnPos.getX(), glaciesSpawnPos.getY(), player.getPosition().getZ()));
                        glacies.setDefaultConstitution(5000 * player.teamSize);
                        glacies.setConstitution(5000 * player.teamSize);
                        World.register(glacies);
                        glacies.setFreezeDelay(100000);

                        if (player.difficulty > 0)
                            glacies.difficultyPerks(glacies, player.difficulty);

                        player.getRegionInstance().getNpcsList().add(glacies);
                        glacies.getCombatBuilder().attack(player);
                    } else if (player.gwdRaidWave == 5) {
                        final RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();

                        for (Player member : party.getPlayers()) {
                            if (member.getRegionInstance() != null)
                                member.getRegionInstance().destruct();
                            member.gwdRaidLoot = true;
                            member.gwdRaidWave = 0;
                            member.gwdRaidKills = 0;
                            member.moveTo(GameSettings.RAIDS_LOBBY_POSITION);

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


        if(n.getId() == 13454) { //Unfreeze when Glacies dies
            for (Player member : party.getPlayers()) {
                member.setFreezeDelay(0);
            }
        }

        party.getOwner().gwdRaidKills++;

        if (party.getOwner().gwdRaidWave == 4 && n.getId() == 13447) {
            party.getOwner().gwdRaidWave = 5;
            spawn(party.getOwner());
        } else if (party.getOwner().gwdRaidWave == 4 && (n.getId() == 13451 || n.getId() == 13452 || n.getId() == 13453 || n.getId() == 13454)) {
            party.nexMinionKills++;
        } else if (party.getOwner().gwdRaidKills >= (party.getOwner().gwdRaidWave) && (party.getOwner().gwdRaidWave < 4)) {
            party.getOwner().gwdRaidWave++;
            spawn(party.getOwner());
        }


    }

    private static final Position bossSpawnPos = new Position(2895, 5262);
    private static final Position nexSpawnPos = new Position(2901, 5266);
    private static final Position fumusSpawnPos = new Position(2890, 5273);
    private static final Position umbraSpawnPos = new Position(2905, 5273);
    private static final Position curorSpawnPos = new Position(2905, 5260);
    private static final Position glaciesSpawnPos = new Position(2890, 5260);


}
