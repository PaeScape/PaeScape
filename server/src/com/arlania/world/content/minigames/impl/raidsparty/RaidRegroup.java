package com.arlania.world.content.minigames.impl.raidsparty;

import com.arlania.model.Locations.Location;
import com.arlania.world.World;
import com.arlania.world.entity.impl.player.Player;

/**
 * @author Gabriel Hannason
 * Wrote this quickly!!
 * Handles the RFD quest
 */
public class RaidRegroup {

    public static void Regroup(Player player) {

        String[] partyMembers = player.getMinigameAttributes().getRaidsAttributes().getPartyMembers();

        Player p0 = World.getPlayerByName(partyMembers[0]);
        Player p1 = World.getPlayerByName(partyMembers[1]);
        Player p2 = World.getPlayerByName(partyMembers[2]);
        Player p3 = World.getPlayerByName(partyMembers[3]);
        Player p4 = World.getPlayerByName(partyMembers[4]);


        if (player.getMinigameAttributes().getRaidsAttributes().getParty() == null) {
            player.getPacketSender().sendMessage("You need to be in a raids party.");
            return;
        }
        if (player.getMinigameAttributes().getRaidsAttributes().getParty().getOwner() != player) {
            player.getPacketSender().sendMessage("You must be the party owner to do this.");
            return;
        }
        player.getPacketSender().sendMessage("prob x");

        player.getPacketSender().sendMessage(p0.getUsername() + " " + p0.getLocation());
        player.getPacketSender().sendMessage(p1.getUsername() + " " + p1.getLocation());
        player.getPacketSender().sendMessage(p2.getUsername() + " " + p2.getLocation());
        player.getPacketSender().sendMessage(p3.getUsername() + " " + p3.getLocation());
        player.getPacketSender().sendMessage(p4.getUsername() + " " + p4.getLocation());

        if (player.getLocation() == p0.getLocation() && p0 != null && p0 != player) {
            player.getPacketSender().sendMessage("prob 5");
            if (player.getLocation() == Location.RAIDS_LOBBY) {
                player.getPacketSender().sendMessage("prob 6");
                Player invite = p0;
                if (player.getMinigameAttributes().getRaidsAttributes().getParty().getPlayers().contains(invite)) {
                    player.getPacketSender().sendMessage("That player is already in your party.");
                }
                player.sendMessage("Sent invite to " + invite.getUsername());

                player.getMinigameAttributes().getRaidsAttributes().getParty().invite(invite);
            }
        }

        if (player.getLocation() == p1.getLocation() && p1 != null && p1 != player) {
            if (player.getLocation() == Location.RAIDS_LOBBY) {
                Player invite = p1;
                if (player.getMinigameAttributes().getRaidsAttributes().getParty().getPlayers().contains(invite)) {
                    player.getPacketSender().sendMessage("That player is already in your party.");
                }
                player.sendMessage("Sent invite to " + invite.getUsername());

                player.getMinigameAttributes().getRaidsAttributes().getParty().invite(invite);
            }
        }

        if (player.getLocation() == p2.getLocation() && p2 != null && p2 != player) {
            if (player.getLocation() == Location.RAIDS_LOBBY) {
                Player invite = p2;
                if (player.getMinigameAttributes().getRaidsAttributes().getParty().getPlayers().contains(invite)) {
                    player.getPacketSender().sendMessage("That player is already in your party.");
                }
                player.sendMessage("Sent invite to " + invite.getUsername());

                player.getMinigameAttributes().getRaidsAttributes().getParty().invite(invite);
            }
        }

        if (player.getLocation() == p3.getLocation() && p3 != null && p3 != player) {
            if (player.getLocation() == Location.RAIDS_LOBBY) {
                Player invite = p3;
                if (player.getMinigameAttributes().getRaidsAttributes().getParty().getPlayers().contains(invite)) {
                    player.getPacketSender().sendMessage("That player is already in your party.");
                }
                player.sendMessage("Sent invite to " + invite.getUsername());

                player.getMinigameAttributes().getRaidsAttributes().getParty().invite(invite);
            }
        }

        if (player.getLocation() == p4.getLocation() && p4 != null && p4 != player) {
            if (player.getLocation() == Location.RAIDS_LOBBY) {
                Player invite = p4;
                if (player.getMinigameAttributes().getRaidsAttributes().getParty().getPlayers().contains(invite)) {
                    player.getPacketSender().sendMessage("That player is already in your party.");
                }
                player.sendMessage("Sent invite to " + invite.getUsername());

                player.getMinigameAttributes().getRaidsAttributes().getParty().invite(invite);
            }
        }


    }


}
