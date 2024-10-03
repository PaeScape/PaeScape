package com.arlania.world.content;

import com.arlania.model.Position;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.world.entity.impl.player.Player;

import java.util.Objects;

public enum ItemSpawnData {

    LUMBRIDGE_ANTIDRAGON_SHIELD(new Position(3210, 3218, 1), 300366, 1540),


    ;

    ItemSpawnData(Position objectPosition, int object, int itemId) {
        this.objectPosition = objectPosition;
        this.object = object;
        this.itemId = itemId;
    }

    private final Position objectPosition;
    private final int object;
    private final int itemId;

    public int getObject() {
        return object;
    }

    public Position getObjectPosition() {
        return objectPosition;
    }

    public int getItemId() {
        return itemId;
    }

    public static void gather(final Player player, int itemId) {
        if(!player.getInventory().contains(itemId) && !player.getEquipment().contains(itemId)) {
            player.getInventory().add(itemId, 1);
            player.getPacketSender().sendMessage("You've received a " + ItemDefinition.forId(itemId).getName() + ".");
        } else {
            player.getPacketSender().sendMessage("You didn't find anything interesting...");
        }
    }

    public static ItemSpawnData forPosition(Position objectPosition) {

        for (ItemSpawnData itemSpawnData : ItemSpawnData.values()) {
            if (Objects.equals(itemSpawnData.getObjectPosition(), objectPosition))
                return itemSpawnData;
        }

        return null;
    }
}