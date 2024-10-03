package com.arlania.world.entity.impl.player.antibotting.actions;

import java.util.Objects;

public class ActionPickupItem extends Action {
    private final int x;
    private final int y;
    private final int itemId;

    public ActionPickupItem(int x, int y, int itemId) {
        super(ActionType.PICKUP_ITEM);
        this.x = x;
        this.y = y;
        this.itemId = itemId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getItemId() {
        return itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActionPickupItem that = (ActionPickupItem) o;
        return x == that.x && y == that.y && itemId == that.itemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), x, y, itemId);
    }

    @Override
    public boolean functionallyEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionPickupItem that = (ActionPickupItem) o;
        return x == that.x && y == that.y && itemId == that.itemId;
    }

    @Override
    public String toString() {
        return "ActionPickupItem{" +
                "x=" + x +
                ", y=" + y +
                ", itemId=" + itemId +
                '}';
    }
}
