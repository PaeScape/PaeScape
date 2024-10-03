package com.arlania.world.entity.impl.player.antibotting.actions;

import java.util.Objects;

public class ActionDropItem extends Action {
    private final int id;
    private final int interfaceIndex;
    private final int itemSlot;

    public ActionDropItem(int id, int interfaceIndex, int itemSlot) {
        super(ActionType.DROP_ITEM);
        this.id = id;
        this.interfaceIndex = interfaceIndex;
        this.itemSlot = itemSlot;
    }

    public int getId() {
        return id;
    }

    public int getInterfaceIndex() {
        return interfaceIndex;
    }

    public int getItemSlot() {
        return itemSlot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActionDropItem that = (ActionDropItem) o;
        return id == that.id && interfaceIndex == that.interfaceIndex && itemSlot == that.itemSlot;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, interfaceIndex, itemSlot);
    }

    @Override
    public boolean functionallyEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionDropItem that = (ActionDropItem) o;
        return id == that.id && interfaceIndex == that.interfaceIndex && itemSlot == that.itemSlot;
    }

    @Override
    public String toString() {
        return "ActionDropItem{" +
                "id=" + id +
                ", interfaceIndex=" + interfaceIndex +
                ", itemSlot=" + itemSlot +
                '}';
    }
}
