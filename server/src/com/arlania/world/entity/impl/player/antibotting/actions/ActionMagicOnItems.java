package com.arlania.world.entity.impl.player.antibotting.actions;

import java.util.Objects;

public class ActionMagicOnItems extends Action {
    private final int slot;
    private final int itemId;
    private final int childId;
    private final int spellId;

    public ActionMagicOnItems(int slot, int itemId, int childId, int spellId) {
        super(ActionType.MAGIC_ON_ITEMS);
        this.slot = slot;
        this.itemId = itemId;
        this.childId = childId;
        this.spellId = spellId;
    }

    public int getSlot() {
        return slot;
    }

    public int getItemId() {
        return itemId;
    }

    public int getChildId() {
        return childId;
    }

    public int getSpellId() {
        return spellId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActionMagicOnItems that = (ActionMagicOnItems) o;
        return slot == that.slot && itemId == that.itemId && childId == that.childId && spellId == that.spellId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), slot, itemId, childId, spellId);
    }

    @Override
    public boolean functionallyEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionMagicOnItems that = (ActionMagicOnItems) o;
        return slot == that.slot && itemId == that.itemId && childId == that.childId && spellId == that.spellId;
    }

    @Override
    public String toString() {
        return "ActionMagicOnItems{" +
                "slot=" + slot +
                ", itemId=" + itemId +
                ", childId=" + childId +
                ", spellId=" + spellId +
                '}';
    }
}
