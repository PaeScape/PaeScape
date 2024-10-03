package com.arlania.world.entity.impl.player.antibotting.actions;

import java.util.Objects;

public class ActionUseItem extends Action {
    private final int interfaceId;
    private final int slot;
    private final int id;

    public ActionUseItem(int interfaceId, int slot, int id) {
        super(ActionType.USE_ITEM);
        this.interfaceId = interfaceId;
        this.slot = slot;
        this.id = id;
    }

    public int getInterfaceId() {
        return interfaceId;
    }

    public int getSlot() {
        return slot;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActionUseItem that = (ActionUseItem) o;
        return interfaceId == that.interfaceId && slot == that.slot && id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), interfaceId, slot, id);
    }

    @Override
    public boolean functionallyEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionUseItem that = (ActionUseItem) o;
        return interfaceId == that.interfaceId && slot == that.slot && id == that.id;
    }

    @Override
    public String toString() {
        return "ActionUseItem{" +
                "interfaceId=" + interfaceId +
                ", slot=" + slot +
                ", id=" + id +
                '}';
    }
}
