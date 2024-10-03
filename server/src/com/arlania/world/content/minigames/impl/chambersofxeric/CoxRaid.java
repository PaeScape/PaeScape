package com.arlania.world.content.minigames.impl.chambersofxeric;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Locations.Location;
import com.arlania.model.Position;
import com.arlania.model.RegionInstance;
import com.arlania.model.RegionInstance.RegionInstanceType;
import com.arlania.world.World;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.content.minigames.MinigameAttributes;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.GreatOlmCombat;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.Olm;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

/**
 * @author Gabriel Hannason
 * Wrote this quickly!!
 * Handles the RFD quest
 */
public class CoxRaid {

    public static void Tekton(Player player, int height) {
        player.getPacketSender().sendMessage("@red@Tekton appears!");
        player.moveTo(new Position(3282, 5319, height));
        player.setRegionInstance(new RegionInstance(player, RegionInstanceType.TEKTON));


        if (player.getRaidsParty().getOwner() == player) {
            World.getNpcs().forEach(n -> n.removeInstancedNpcs(Location.TEKTON, player.getPosition().getZ()));
            NPC tekton = new NPC(21541, new Position(3280, 5328, height));
            int tektonHP = player.teamSize * 5000;
            tekton.setDefaultConstitution(tektonHP);
            tekton.setConstitution(tektonHP);
            World.register(tekton);

            if (player.difficulty > 0)
                tekton.difficultyPerks(tekton, player.difficulty);

            player.getRegionInstance().getNpcsList().add(tekton);

            int i = 0;
            String[] PartyNames = new String[5];

            for (Player member : player.getRaidsParty().getPlayers()) {
                PartyNames[i] = member.getUsername();
                i++;
            }
        }

    }

    public static void Mystics(Player player, int height) {
        player.getPacketSender().sendMessage("@red@The Skeletal Mystics appear!");
        player.moveTo(new Position(3311, 5294, height));
        player.setRegionInstance(new RegionInstance(player, RegionInstanceType.SKELETAL_MYSTICS));

        if (player.getRaidsParty().getOwner() == player) {
            World.getNpcs().forEach(n -> n.removeInstancedNpcs(Location.SKELETAL_MYSTICS, player.getPosition().getZ()));
            NPC mystic1 = new NPC(21604, new Position(3305, 5304, height));
            NPC mystic2 = new NPC(21605, new Position(3301, 5290, height));
            NPC mystic3 = new NPC(21606, new Position(3317, 5286, height));
            int mysticHP = player.teamSize * 5000;
            mystic1.setDefaultConstitution(mysticHP);
            mystic1.setConstitution(mysticHP);
            mystic2.setDefaultConstitution(mysticHP);
            mystic2.setConstitution(mysticHP);
            mystic3.setDefaultConstitution(mysticHP);
            mystic3.setConstitution(mysticHP);
            World.register(mystic1);
            World.register(mystic2);
            World.register(mystic3);

            if (player.difficulty > 0) {
                mystic1.difficultyPerks(mystic1, player.difficulty);
                mystic2.difficultyPerks(mystic2, player.difficulty);
                mystic3.difficultyPerks(mystic3, player.difficulty);
            }

            player.getRegionInstance().getNpcsList().add(mystic1);
            player.getRegionInstance().getNpcsList().add(mystic2);
            player.getRegionInstance().getNpcsList().add(mystic3);

            for (Player member : player.getRaidsParty().getPlayers()) {
                mystic1.getCombatBuilder().attack(member);
                mystic2.getCombatBuilder().attack(member);
                mystic3.getCombatBuilder().attack(member);
            }
        }


    }

    public static void spawnOlm(Player player, int height) {
        player.getPacketSender().sendMessage("@red@The Great Olm appears!");

        String[] partyNames = new String[5];

        if (player.getRaidsParty().getOwner() == player) {
            //GreatOlm.animationFix(player.getCoxRaidsParty(), height);
			/*NPC olm = new NPC(22374, new Position(3168, 4318, height));
			World.register(olm);	
			player.getRegionInstance().getNpcsList().add(olm);*/
            partyNames = player.getMinigameAttributes().getRaidsAttributes().getPartyMembers();

            Olm.startPhase1(player.getRaidsParty(), height);
        }

        player.getPacketSender().sendInterfaceRemoval();
        final RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();


        for (Player member : party.getPlayers()) {
            member.getPacketSender().sendInterfaceRemoval(); MinigameAttributes.RaidsAttributes raidsAttributes = player.getMinigameAttributes().getRaidsAttributes();
            raidsAttributes.setInsideRaid(true);
            member.setRegionInstance(null);
            member.getMovementQueue().reset();
            member.getClickDelay().reset();
            member.moveTo(new Position(3232, 5730, height));
            member.setRegionInstance(new RegionInstance(player, RegionInstanceType.OLM));
            member.getPacketSender().sendInteractionOption("null", 2, true);
            member.getMinigameAttributes().getRaidsAttributes().setInsideRaid(true);
            member.getSkillManager().stopSkilling();
            member.getPacketSender().sendClientRightClickRemoval();
        }
        for (Player players : party.getPlayers()) {
            players.getPacketSender().sendCameraNeutrality();
            players.getMinigameAttributes().getRaidsAttributes().setParty(party);
            players.getMinigameAttributes().getRaidsAttributes().setInsideRaid(true);
            players.getPacketSender().sendInteractionOption("null", 2, true);
            party.getPlayersInRaids().add(players);
            players.getMinigameAttributes().getRaidsAttributes().setPartyMembers(partyNames);
        }
        party.setInstanceLevel(height);
        party.setDeaths(0);
        party.setKills(0);
        party.setCanAttackLeftHand(false);
        party.setCanAttack(false);
        party.setOlmAttackTimer(6);
        party.setLeftHandAttackTimer(6);
        party.setCurrentPhase(0);
        party.setClenchedHand(false);
        party.setLeftHandProtected(false);
        party.setHeight(height);
        party.setClenchedHandFirst(false);
        party.setClenchedHandSecond(false);
        party.setUnClenchedHandFirst(false);
        party.setUnClenchedHandSecond(false);
        party.setLastPhaseStarted(false);
        party.setSwitchingPhases(false);

        TaskManager.submit(new Task(1) {
            int tick = 0;

            @Override
            public void execute() {
                if (tick == 2) {
                    Olm.animationFix(party, height);
                }
                if (tick == 10)
                    Olm.startTask(party, height);
                if (tick == 20) {
                    GreatOlmCombat.sequence(party, height);
                    stop();
                }
                tick++;
            }
        });


    }


    public static void handleNPCDeath(final Player player, NPC n) {
        if (player.getRegionInstance() == null)
            return;

        player.getRegionInstance().getNpcsList().remove(n);

        int height = player.instanceHeight;

        final RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();

        if (player.coxWave == 0 && player.getLocation() == Location.TEKTON && n.getId() == 21541) {
            for (Player member : party.getPlayers()) {
                if (member.getRegionInstance() != null)
                    member.getRegionInstance().destruct();
                member.coxWave = 1;
                Mystics(member, height);
            }
        } else if (player.coxWave == 1 && player.getLocation() == Location.SKELETAL_MYSTICS && (n.getId() == 21604 || n.getId() == 21605 || n.getId() == 21606)) {
            party.setMysticKills(party.getMysticKills() + 1);

            if (party.getMysticKills() == 3) {
                for (Player member : party.getPlayers()) {
                    if (member.getRegionInstance() != null)
                        member.getRegionInstance().destruct();

                    member.coxWave = 2;
                    spawnOlm(member, height);
                }
            }
        } else if (player.coxWave == 2 && player.getLocation() == Location.OLM && n.getId() == 21554) {
            for (Player member : party.getPlayers()) {
                if (member.getRegionInstance() != null)
                    member.getRegionInstance().destruct();

                member.coxWave = 0;
                member.coxRaid = false;

                member.getMinigameAttributes().getRaidsAttributes().setInsideRaid(false);

                if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null)
                    player.getMinigameAttributes().getRaidsAttributes().getParty().getPlayersInRaids().remove(member);

                member.moveTo(lobbyTele);
                //member.getPacketSender().sendMessage("@red@You've completed the Chambers of Xeric!");
                CoxRewards.grantLoot(party, member);
            }
            String message = "<col=996633>" + party.getOwner().getUsername() + "'s raid party has just completed the Chambers of Xeric!";
            //World.sendMessage(message);
        } else if (player.coxWave == 2 && player.getLocation() == Location.OLM && n.getId() == 21553) {
            for (Player member : party.getPlayers()) {
                party.setRightHandDead(true);
                member.getPacketSender().sendMessage("@red@You've killed the Right Hand!");
            }
        } else if (player.coxWave == 2 && player.getLocation() == Location.OLM && n.getId() == 21555) {
            for (Player member : party.getPlayers()) {
                party.setLeftHandDead(true);
                member.getPacketSender().sendMessage("@red@You've killed the Left Hand!");
            }
        } else {
            player.getPacketSender().sendMessage("@red@Cox Wave: " + player.coxWave);
            player.getPacketSender().sendMessage("@red@Location: " + player.getLocation().toString());
            player.getPacketSender().sendMessage("@red@Instance: " + player.getRegionInstance().toString());
        }

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


        party.sendMessage("@red@Welcome to Chambers of Xeric!");


        int teamsize = 0;
        party.MysticKills = 0;

        final int height = p.getIndex() * 4;
        for (Player member : party.getPlayers()) {

            if (member.getLocation() == Location.RAIDS_LOBBY) {

                /*if(member.getGameMode() == GameMode.SEASONAL_IRONMAN) {
                    if (member.seasonalRaidsTeleports[1] == 0) {
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
                member.coxRaid = true;
                member.coxWave = 0;
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

        for (Player player : party.getPlayers()) {
            player.getPacketSender().sendCameraNeutrality();
            player.getMinigameAttributes().getRaidsAttributes().setParty(party);
            player.getPacketSender().sendInteractionOption("null", 2, true);
            party.getPlayersInRaids().add(player);
            player.coxWave = 0;
            player.instanceHeight = height;
            Tekton(player, height);
        }

        party.setInstanceLevel(height);


    }


    private static final Position lobbyTele = new Position(1234, 3558, 0);


}
