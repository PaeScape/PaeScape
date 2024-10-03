package com.arlania.world.entity.impl.player.antibotting.actions;

import java.util.Objects;

public class ActionMagicOnGroundItems extends Action {
    private final int itemX;
    private final int itemY;
    private final int itemId;
    private final int spellId;

    public ActionMagicOnGroundItems(int itemX, int itemY, int itemId, int spellId) {
        super(ActionType.MAGIC_ON_GROUNDITEMS);
        this.itemX = itemX;
        this.itemY = itemY;
        this.itemId = itemId;
        this.spellId = spellId;
    }

    public int getItemX() {
        return itemX;
    }

    public int getItemY() {
        return itemY;
    }

    public int getItemId() {
        return itemId;
    }

    public int getSpellId() {
        return spellId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActionMagicOnGroundItems that = (ActionMagicOnGroundItems) o;
        return itemX == that.itemX && itemY == that.itemY && itemId == that.itemId && spellId == that.spellId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), itemX, itemY, itemId, spellId);
    }

    @Override
    public boolean functionallyEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionMagicOnGroundItems that = (ActionMagicOnGroundItems) o;
        return itemX == that.itemX && itemY == that.itemY && itemId == that.itemId && spellId == that.spellId;
    }

    @Override
    public String toString() {
        return "ActionMagicOnGroundItems{" +
                "itemX=" + itemX +
                ", itemY=" + itemY +
                ", itemId=" + itemId +
                ", spellId=" + spellId +
                '}';
    }
}
