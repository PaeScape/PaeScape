package com.arlania.world.entity.impl.player.antibotting.actions;

import java.util.Objects;

public class ActionItemOnPlayer extends Action {
    private final String username;
    private final int interfaceId;
    private final int slot;
    private final int itemId;

    public ActionItemOnPlayer(String username, int interfaceId, int slot, int itemId) {
        super(ActionType.ITEM_ON_PLAYER);
        this.username = username;
        this.interfaceId = interfaceId;
        this.slot = slot;
        this.itemId = itemId;
    }

    public String getUsername() {
        return username;
    }

    public int getInterfaceId() {
        return interfaceId;
    }

    public int getSlot() {
        return slot;
    }

    public int getItemId() {
        return itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActionItemOnPlayer that = (ActionItemOnPlayer) o;
        return interfaceId == that.interfaceId && slot == that.slot && itemId == that.itemId && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, interfaceId, slot, itemId);
    }

    @Override
    public boolean functionallyEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActionItemOnPlayer that = (ActionItemOnPlayer) o;
        return interfaceId == that.interfaceId && slot == that.slot && itemId == that.itemId && Objects.equals(username, that.username);
    }

    @Override
    public String toString() {
        return "ActionItemOnPlayer{" +
                "username='" + username + '\'' +
                ", interfaceId=" + interfaceId +
                ", slot=" + slot +
                ", itemId=" + itemId +
                '}';
    }
}
