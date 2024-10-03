package com.arlania.net.packet.impl;

import com.arlania.model.Locations.Location;
import com.arlania.model.StaffRights;
import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketListener;
import com.arlania.world.World;
import com.arlania.world.content.combat.CombatFactory;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.antibotting.actions.ActionPlayerOption;

/**
 * This packet listener is called when a player has clicked on another player's
 * menu actions.
 *
 * @author relex lawl
 */

public class PlayerOptionPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        if (player.getConstitution() <= 0)
            return;
        if (player.isTeleporting())
            return;

        if (player.getStaffRights() == StaffRights.DEVELOPER)
            player.getPacketSender().sendMessage("packet code: " + packet.getOpcode());

        switch (packet.getOpcode()) {
            case 153:
                attack(player, packet);
                break;
            case 128:
                option1(player, packet);
                break;
            case 37:
                option2(player, packet);
                break;
            case 227:
                option3(player, packet);
                break;
        }
    }

    private static void attack(Player player, Packet packet) {
        int index = packet.readLEShort();
        if (index > World.getPlayers().capacity() || index < 0)
            return;
        final Player attacked = World.getPlayers().get(index);

        if (attacked == null || attacked.getConstitution() <= 0 || attacked.equals(player)) {
            player.getMovementQueue().reset();
            return;
        }

		/*if(player.getLocation() == Location.DUEL_ARENA && player.getDueling().duelingStatus == 0) {
			player.getDueling().challengePlayer(attacked);
			return;
		}*/

        if (player.getLocation() == Location.RAIDS_LOBBY) {
            Player invite = attacked;
            if (player.getMinigameAttributes().getRaidsAttributes().getParty() == null) {
                player.getPacketSender().sendMessage("You are not the leader of a party.");
                return;
            }
            if (player.getMinigameAttributes().getRaidsAttributes().getParty().getPlayers().contains(invite)) {
                player.getPacketSender().sendMessage("That player is already in your party.");
                return;
            }
            player.sendMessage("Sent invite to " + attacked.getUsername());

            player.getMinigameAttributes().getRaidsAttributes().getParty().invite(invite);
        }


        if (player.getLocation() == Location.CRASH_ISLAND) {
            Player invite = attacked;
            if (player.getMinigameAttributes().getRaidsAttributes().getParty().getPlayers().contains(invite)) {
                player.getPacketSender().sendMessage("That player is already in your party.");
                return;
            }
            player.sendMessage("Sent invite to " + attacked.getUsername());

            player.getMinigameAttributes().getRaidsAttributes().getParty().duoSeasonal(invite);
        }

        player.getActionTracker().offer(new ActionPlayerOption(attacked.getUsername()));

        if (player.getCombatBuilder().getStrategy() == null) {
            player.getCombatBuilder().determineStrategy();
        }
        if (CombatFactory.checkAttackDistance(player, attacked)) {
            player.getMovementQueue().reset();
        }

        player.getCombatBuilder().attack(attacked);
    }

    /**
     * Manages the first option click on a player option menu.
     *
     * @param player The player clicking the other entity.
     * @param packet The packet to read values from.
     */
    private static void option1(Player player, Packet packet) {
        int id = packet.readShort() & 0xFFFF;
        if (id < 0 || id > World.getPlayers().capacity())
            return;
        Player victim = World.getPlayers().get(id);
        if (victim == null)
            return;
        player.getActionTracker().offer(new ActionPlayerOption(victim.getUsername()));
		/*GameServer.getTaskScheduler().schedule(new WalkToTask(player, victim.getPosition(), new FinalizedMovementTask() {
			@Override
			public void execute() {
				//do first option here
			}
		}));*/
    }

    /**
     * Manages the second option click on a player option menu.
     *
     * @param player The player clicking the other entity.
     * @param packet The packet to read values from.
     */
    private static void option2(Player player, Packet packet) {
        int id = packet.readShort() & 0xFFFF;
        if (id < 0 || id > World.getPlayers().capacity())
            return;
        Player victim = World.getPlayers().get(id);
        if (victim == null)
            return;
        player.getActionTracker().offer(new ActionPlayerOption(victim.getUsername()));
		/*GameServer.getTaskScheduler().schedule(new WalkToTask(player, victim.getPosition(), new FinalizedMovementTask() {
			@Override
			public void execute() {
				//do second option here
			}
		}));*/
    }

    /**
     * Manages the third option click on a player option menu.
     *
     * @param player The player clicking the other entity.
     * @param packet The packet to read values from.
     */
    private static void option3(Player player, Packet packet) {
        int id = packet.readLEShortA() & 0xFFFF;
        if (id < 0 || id > World.getPlayers().capacity())
            return;
        Player victim = World.getPlayers().get(id);
        if (victim == null)
            return;
        player.getActionTracker().offer(new ActionPlayerOption(victim.getUsername()));
		/*GameServer.getTaskScheduler().schedule(new WalkToTask(player, victim.getPosition(), new FinalizedMovementTask() {
			@Override
			public void execute() {
				//do third option here
			}
		}));*/
    }
}
