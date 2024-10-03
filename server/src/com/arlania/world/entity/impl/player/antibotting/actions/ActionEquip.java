package com.arlania.world.entity.impl.player.antibotting.actions;

import java.util.Objects;

public class ActionEquip extends Action {
    private final int interfaceId;
    private final int slot;
    private final int itemId;

    public ActionEquip(int interfaceId, int slot, int itemId) {
        super(ActionType.EQUIP);
        this.interfaceId = interfaceId;
        this.slot = slot;
        this.itemId = itemId;
    }

    public int getItemId() {
        return itemId;
    }

    public int getSlot() {
        return slot;
    }

    public int getInterfaceId() {
        return interfaceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActionEquip that = (ActionEquip) o;
        return interfaceId == that.interfaceId && slot == that.slot && itemId == that.itemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), interfaceId, slot, itemId);
    }

    @Override
    public boolean functionallyEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionEquip that = (ActionEquip) o;
        return interfaceId == that.interfaceId && slot == that.slot && itemId == that.itemId;
    }

    @Override
    public String toString() {
        return "ActionEquip{" +
                "interfaceId=" + interfaceId +
                ", slot=" + slot +
                ", itemId=" + itemId +
                '}';
    }
}
