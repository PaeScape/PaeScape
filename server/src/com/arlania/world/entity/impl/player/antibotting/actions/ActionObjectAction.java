package com.arlania.world.entity.impl.player.antibotting.actions;

import java.util.Objects;

public class ActionObjectAction extends Action {
    private final int x;
    private final int y;
    private final int id;

    public ActionObjectAction(ActionType type, int x, int y, int id) {
        super(type);
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActionObjectAction that = (ActionObjectAction) o;
        return x == that.x && y == that.y && id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), x, y, id);
    }

    @Override
    public boolean functionallyEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionObjectAction that = (ActionObjectAction) o;
        return type == that.type && x == that.x && y == that.y && id == that.id;
    }

    @Override
    public String toString() {
        return "ActionObjectAction{" +
                "x=" + x +
                ", y=" + y +
                ", id=" + id +
                '}';
    }
}
