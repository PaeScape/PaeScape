package com.arlania.world.entity.impl.player.antibotting.actions;

import java.util.Objects;

public class ActionExamineItem extends Action {
    private final int item;

    public ActionExamineItem(int item) {
        super(ActionType.EXAMINE_ITEM);
        this.item = item;
    }

    public int getItem() {
        return item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActionExamineItem that = (ActionExamineItem) o;
        return item == that.item;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), item);
    }

    @Override
    public boolean functionallyEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionExamineItem that = (ActionExamineItem) o;
        return item == that.item;
    }

    @Override
    public String toString() {
        return "ActionExamineItem{" +
                "item=" + item +
                '}';
    }
}
