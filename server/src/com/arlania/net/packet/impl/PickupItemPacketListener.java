package com.arlania.net.packet.impl;

import com.arlania.engine.task.impl.WalkToTask;
import com.arlania.engine.task.impl.WalkToTask.FinalizedMovementTask;
import com.arlania.model.GroundItem;
import com.arlania.model.Item;
import com.arlania.model.Position;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketListener;
import com.arlania.world.entity.impl.GroundItemManager;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.antibotting.actions.ActionPickupItem;

/**
 * This packet listener is used to pick up ground items
 * that exist in the world.
 *
 * @author relex lawl
 */

public class PickupItemPacketListener implements PacketListener {

    @Override
    public void handleMessage(final Player player, Packet packet) {
        final int y = packet.readLEShort();
        final int itemId = packet.readInt();
        final int x = packet.readLEShort();
        if (player.isTeleporting())
            return;
        final Position position = new Position(x, y, player.getPosition().getZ());

        player.setAttribute("pickingUp", -1);

        if (!player.getLastItemPickup().elapsed(500))
            return;
        if (player.getConstitution() <= 0 || player.isTeleporting())
            return;
        player.setWalkToTask(new WalkToTask(player, position, 1, new FinalizedMovementTask() {
            @Override
            public void execute() {
                if (Math.abs(player.getPosition().getX() - x) > 25 || Math.abs(player.getPosition().getY() - y) > 25) {
                    player.getMovementQueue().reset();
                    return;
                }
                GroundItem gItem = GroundItemManager.getGroundItem(player, new Item(itemId), position);
                boolean canPickup = player.getInventory().getFreeSlots() > 0 || (player.getInventory().getFreeSlots() == 0 && ItemDefinition.forId(itemId).isStackable() && player.getInventory().contains(itemId));

                if (!canPickup) {
                    player.getInventory().full();
                    return;
                }
                if (gItem != null) {
                    if (player.getInventory().getAmount(gItem.getItem().getId()) + gItem.getItem().getAmount() > Integer.MAX_VALUE || player.getInventory().getAmount(gItem.getItem().getId()) + gItem.getItem().getAmount() <= 0) {
                        player.getPacketSender().sendMessage("You cannot hold that amount of this item. Clear your inventory!");
                        return;
                    }

                    player.getActionTracker().offer(new ActionPickupItem(x, y, itemId));
                    GroundItemManager.pickupGroundItem(player, new Item(itemId), new Position(x, y, player.getPosition().getZ()));
                }
            }
        }));
    }
}
