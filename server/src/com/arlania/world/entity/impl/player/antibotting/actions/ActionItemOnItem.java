package com.arlania.world.entity.impl.player.antibotting.actions;

import java.util.Objects;

public class ActionItemOnItem extends Action {
    private final int firstItemId;
    private final int secondItemId;

    public ActionItemOnItem(int firstItemId, int secondItemId) {
        super(ActionType.ITEM_ON_ITEM);
        this.firstItemId = firstItemId;
        this.secondItemId = secondItemId;
    }

    public int getFirstItemId() {
        return firstItemId;
    }

    public int getSecondItemId() {
        return secondItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActionItemOnItem that = (ActionItemOnItem) o;
        return firstItemId == that.firstItemId && secondItemId == that.secondItemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstItemId, secondItemId);
    }

    @Override
    public boolean functionallyEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActionItemOnItem that = (ActionItemOnItem) o;
        return (firstItemId == that.firstItemId && secondItemId == that.secondItemId) ||
                (firstItemId == that.secondItemId && secondItemId == that.firstItemId);
    }

    @Override
    public String toString() {
        return "ActionItemOnItem{" +
                "used=" + firstItemId +
                ", with=" + secondItemId +
                '}';
    }
}
