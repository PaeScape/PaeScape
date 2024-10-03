package com.arlania.world.content.minigames.impl.strongholdraids;

import com.arlania.DiscordBot;
import com.arlania.GameSettings;
import com.arlania.model.Locations.Location;
import com.arlania.model.Position;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.content.minigames.MinigameAttributes;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.content.skill.impl.dungeoneering.ItemBinding;
import com.arlania.world.entity.impl.player.Player;
import org.javacord.api.entity.message.MessageBuilder;


public class Raid {

    public static void start(final RaidsParty party) {


        final int height = party.getOwner().getIndex() * 4;

        party.shrFloorOneBossSpawned = false;
        party.shrFloorTwoBossSpawned = false;
        party.shrFloorThreeBossSpawned = false;
        party.shrFloorFourBossSpawned = false;

        party.shrFloorOneBossKey = 0;
        party.shrFloorTwoBossKey = 0;
        party.shrFloorThreeBossKey = 0;
        party.shrFloorFourBossKey = 0;

        for (Player p : party.getPlayers()) {

            /*if(p.getGameMode() == GameMode.SEASONAL_IRONMAN) {
                if (p.seasonalRaidsTeleports[2] == 0) {
                    p.getPacketSender().sendMessage("You can't join this raid without unlocking the teleport.");
                    p.getRaidsParty().remove(p, false, true);
                }
            }*/

            String discordMessage = "[Leader: " + party.getOwner().getUsername() + "] " + p.getUsername() + " has started a Stronghold Raid";

            if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.SHR_LOGS_CH).get());

            p.partyDifficulty = party.getOwner().difficulty;

            p.getPacketSender().sendInterfaceRemoval();

            if (p.getMinigameAttributes().getRaidsAttributes().getParty() == null) {
                DialogueManager.sendStatement(p, "You need to be in a Raids party to begin.");
                return;
            }
            if (p.getLocation() != Location.SHR && (p.getInventory().getFreeSlots() != 28 || !p.getEquipment().isNaked(p))) {
                p.getPacketSender().sendMessage("You must have a clear inventory and no equipment on to enter.");
                return;
            }
            if (p.getSummoning().getFamiliar() != null) {
                p.getPacketSender().sendMessage("You can't enter with a familiar!");
                return;
            }

            if (p.getLocation() == Location.RAIDS_LOBBY || p.getLocation() == Location.SHR) {
                p.getPacketSender().sendInterfaceRemoval();
                p.setRegionInstance(null);
                p.getMovementQueue().reset();
                p.getClickDelay().reset();
                p.getPacketSender().sendInteractionOption("null", 2, true);

                p.moveTo(new Position(3363, 9636, height), true);

                p.strongholdRaidFloor = 1;
                p.strongholdRaidDeaths = 0;
                p.strongholdLootFloor = 0;
                p.floorOneKey = 0;
                p.floorTwoKey = 0;
                p.floorThreeKey = 0;
                p.floorFourKey = 0;
                p.instanceHeight = height;

                if(p.getLocation() == Location.RAIDS_LOBBY) {
                    ItemBinding.onDungeonEntrance(p);
                    p.getInventory().add(18829, 1); //No longer need Group Gatestone
                    p.getInventory().add(16935, 1);
                    p.getInventory().add(16867, 1);
                    p.getInventory().add(16977, 1);
                }

                p.getSkillManager().stopSkilling();
                p.getPacketSender().sendClientRightClickRemoval();
            } else {
                p.getMinigameAttributes().getRaidsAttributes().getParty().remove(p, true, true);
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


        party.setInstanceLevel(height);
        party.sendMessage("@red@Welcome to Stronghold Raids!");
        assignColoredItem(party.getOwner());


        for (Player player : party.getPlayers()) {
            player.getPacketSender().sendCameraNeutrality();
            MinigameAttributes.RaidsAttributes raidsAttributes = player.getMinigameAttributes().getRaidsAttributes();
            raidsAttributes.setInsideRaid(true);
            player.getMinigameAttributes().getRaidsAttributes().setParty(party);
            player.getPacketSender().sendInteractionOption("null", 2, true);
            party.getPlayersInRaids().add(player);

            if(party.getOwner() == player) {
                RaidFloorOne.createInstance(player, height);
                RaidFloorOne.assignBossKey(player);
            }
        }


    }

    public static void assignColoredItem(Player player) {

        int rand = RandomUtility.inclusiveRandom(3);

        switch(rand) {

            case 0:
                player.getRaidsParty().shrItem = 6898;
                player.getRaidsParty().shrColor = "Green Cylinder";
                for (Player member : player.getRaidsParty().getPlayers())
                    member.getPacketSender().sendMessage("@gre@Your new Animator item is: " + player.getRaidsParty().shrColor + ".");
                break;
            case 1:
                player.getRaidsParty().shrItem = 6899;
                player.getRaidsParty().shrColor = "Yellow Cube";
                for (Player member : player.getRaidsParty().getPlayers())
                    member.getPacketSender().sendMessage("@gre@Your new Animator item is: " + player.getRaidsParty().shrColor + ".");
                break;
            case 2:
                player.getRaidsParty().shrItem = 6900;
                player.getRaidsParty().shrColor = "Blue Icosahedron";
                for (Player member : player.getRaidsParty().getPlayers())
                    member.getPacketSender().sendMessage("@gre@Your new Animator item is: " + player.getRaidsParty().shrColor + ".");
                break;
            case 3:
                player.getRaidsParty().shrItem = 6901;
                player.getRaidsParty().shrColor = "Red Pentamid";
                for (Player member : player.getRaidsParty().getPlayers())
                    member.getPacketSender().sendMessage("@gre@Your new Animator item is: " + player.getRaidsParty().shrColor + ".");
                break;
        }
    }

    public static void leave(final Player p) {
        p.getEquipment().deleteAll();
        p.getInventory().deleteAll();
        p.getEquipment().refreshItems();
        p.getInventory().refreshItems();

        if(p.getRegionInstance() != null)
            p.getRegionInstance().destruct();

        p.moveTo(new Position(3081, 3421, 0), true);

        if(p.strongholdLootFloor == 4)
            Rewards.grantLoot(p.getRaidsParty(), p);

        p.getMinigameAttributes().getRaidsAttributes().setInsideRaid(false);

        if (p.getMinigameAttributes().getRaidsAttributes().getParty() != null)
            p.getMinigameAttributes().getRaidsAttributes().getParty().getPlayersInRaids().remove(p);


    }

}
