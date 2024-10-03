package com.arlania.net.packet.impl;

import com.arlania.model.Animation;
import com.arlania.model.Position;
import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketListener;
import com.arlania.world.content.minigames.impl.Dueling;
import com.arlania.world.content.minigames.impl.Dueling.DuelRule;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.antibotting.actions.Action;

/**
 * This packet listener is called when a player has clicked on
 * either the mini-map or the actual game map to move around.
 *
 * @author Gabriel Hannason
 */

public class MovementPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        int size = packet.getSize();

        if (packet.getOpcode() == 248)
            size -= 14;

        player.setEntityInteraction(null);
        player.getSkillManager().stopSkilling();
        player.getMovementQueue().setFollowCharacter(null);

        player.removeAttribute("pickingUp");

        if (packet.getOpcode() != COMMAND_MOVEMENT_OPCODE) {
            player.setWalkToTask(null);
            player.setCastSpell(null);
            player.getCombatBuilder().cooldown(false);
        }

        if (!checkReqs(player, packet.getOpcode()))
            return;

        player.getActionTracker().offer(new Action(Action.ActionType.MOVEMENT));
        player.getPacketSender().sendInterfaceRemoval();
        player.setTeleporting(false);

        final int steps = (size - 5) / 2;
        if (steps < 0)
            return;
        final int firstStepX = packet.readLEShortA();
        final int[][] path = new int[steps][2];
        for (int i = 0; i < steps; i++) {
            path[i][0] = packet.readByte();
            path[i][1] = packet.readByte();
        }
        final int firstStepY = packet.readLEShort();
        final Position[] positions = new Position[steps + 1];
        positions[0] = new Position(firstStepX, firstStepY, player.getPosition().getZ());


        boolean invalidStep = false;

        if (!player.getPosition().isViewableFrom(positions[0])) {
            invalidStep = true;
        } else {
            for (int i = 0; i < steps; i++) {
                positions[i + 1] = new Position(path[i][0] + firstStepX, path[i][1] + firstStepY, player.getPosition().getZ());
                int distance = player.getPosition().getDistance(positions[i + 1]);
                if (distance < -22 || distance > 22) {
                    invalidStep = true;
                    break;
                }
            }
        }

        if (invalidStep) {
            player.getMovementQueue().reset();
            //System.out.println(""+player.getUsername()+" invalid step at "+player.getLocation().toString());
            return;
        }

        if (player.getMovementQueue().addFirstStep(positions[0])) {
            for (int i = 1; i < positions.length; i++) {
                player.getMovementQueue().addStep(positions[i]);
            }
        }
    }

    public boolean checkReqs(Player player, int opcode) {
        if (player.isFrozen()) {
            if (opcode != COMMAND_MOVEMENT_OPCODE)
                player.getPacketSender().sendMessage("A magical spell has made you unable to move.");
            return false;
        }
        if (player.getTrading().inTrade() && System.currentTimeMillis() - player.getTrading().lastAction <= 1000) {
            return false;
        }
        if (Dueling.checkRule(player, DuelRule.NO_MOVEMENT)) {
            if (opcode != COMMAND_MOVEMENT_OPCODE)
                player.getPacketSender().sendMessage("Movement has been turned off in this duel!");
            return false;
        }
        if (player.isResting()) {
            player.setResting(false);
            player.performAnimation(new Animation(11788));
            return false;
        }
        if (player.isPlayerLocked() || player.isCrossingObstacle())
            return false;
        if (player.isNeedsPlacement()) {
            return false;
        }
        return !player.getMovementQueue().isLockMovement();
    }

    public static final int COMMAND_MOVEMENT_OPCODE = 98;
    public static final int GAME_MOVEMENT_OPCODE = 164;
    public static final int MINIMAP_MOVEMENT_OPCODE = 248;

}