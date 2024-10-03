package com.arlania.world.entity.impl.player.antibotting.actions;

import java.util.Objects;

public class ActionItemOnObject extends Action {
    private final int objectId;
    private final int objectX;
    private final int objectY;
    private final int lastItemSelectedInterface;
    private final int itemSlot;
    private final int itemId;

    public ActionItemOnObject(int objectId, int objectX, int objectY, int lastItemSelectedInterface, int itemSlot, int itemId) {
        super(ActionType.ITEM_ON_OBJECT);
        this.objectId = objectId;
        this.objectX = objectX;
        this.objectY = objectY;
        this.lastItemSelectedInterface = lastItemSelectedInterface;
        this.itemSlot = itemSlot;
        this.itemId = itemId;
    }

    public int getObjectId() {
        return objectId;
    }

    public int getObjectX() {
        return objectX;
    }

    public int getObjectY() {
        return objectY;
    }

    public int getLastItemSelectedInterface() {
        return lastItemSelectedInterface;
    }

    public int getItemSlot() {
        return itemSlot;
    }

    public int getItemId() {
        return itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActionItemOnObject that = (ActionItemOnObject) o;
        return objectId == that.objectId && objectX == that.objectX && objectY == that.objectY && lastItemSelectedInterface == that.lastItemSelectedInterface && itemSlot == that.itemSlot && itemId == that.itemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), objectId, objectX, objectY, lastItemSelectedInterface, itemSlot, itemId);
    }

    @Override
    public boolean functionallyEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActionItemOnObject that = (ActionItemOnObject) o;
        return objectId == that.objectId && objectX == that.objectX && objectY == that.objectY && itemSlot == that.itemSlot && itemId == that.itemId;
    }

    @Override
    public String toString() {
        return "ActionItemOnObject{" +
                "objectId=" + objectId +
                ", objectX=" + objectX +
                ", objectY=" + objectY +
                ", lastItemSelectedInterface=" + lastItemSelectedInterface +
                ", itemSlot=" + itemSlot +
                ", itemId=" + itemId +
                '}';
    }
}
