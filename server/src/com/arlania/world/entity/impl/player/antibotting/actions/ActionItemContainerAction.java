package com.arlania.world.entity.impl.player.antibotting.actions;

import java.util.Objects;

public class ActionItemContainerAction extends Action {
    private final int interfaceId;
    private final int slot;
    private final int itemId;

    public ActionItemContainerAction(ActionType type, int interfaceId, int slot, int itemId) {
        super(type);
        this.interfaceId = interfaceId;
        this.slot = slot;
        this.itemId = itemId;
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
        ActionItemContainerAction that = (ActionItemContainerAction) o;
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
        ActionItemContainerAction that = (ActionItemContainerAction) o;
        return type == that.type && interfaceId == that.interfaceId && slot == that.slot && itemId == that.itemId;
    }

    @Override
    public String toString() {
        return "ActionItemContainerAction{" +
                "interfaceId=" + interfaceId +
                ", slot=" + slot +
                ", itemId=" + itemId +
                '}';
    }
}
