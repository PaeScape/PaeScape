package com.arlania.world.entity.impl.player.antibotting.actions;

import java.util.Objects;

public class ActionSwitchItemSlot extends Action {
    private final int interfaceId;
    private final int fromSlot;
    private final int toSlot;

    public ActionSwitchItemSlot(int interfaceId, int fromSlot, int toSlot) {
        super(ActionType.SWITCH_ITEM_SLOT);
        this.interfaceId = interfaceId;
        this.fromSlot = fromSlot;
        this.toSlot = toSlot;
    }

    public int getInterfaceId() {
        return interfaceId;
    }

    public int getFromSlot() {
        return fromSlot;
    }

    public int getToSlot() {
        return toSlot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActionSwitchItemSlot that = (ActionSwitchItemSlot) o;
        return interfaceId == that.interfaceId && fromSlot == that.fromSlot && toSlot == that.toSlot;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), interfaceId, fromSlot, toSlot);
    }

    @Override
    public boolean functionallyEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActionSwitchItemSlot that = (ActionSwitchItemSlot) o;
        return interfaceId == that.interfaceId && fromSlot == that.fromSlot && toSlot == that.toSlot;
    }

    @Override
    public String toString() {
        return "ActionSwitchItemSlot{" +
                "interfaceId=" + interfaceId +
                ", fromSlot=" + fromSlot +
                ", toSlot=" + toSlot +
                '}';
    }
}
