package com.arlania.world.entity.impl.player.antibotting.actions;

import java.util.Objects;

public class ActionMagicOnNPC extends Action {
    private final int npcIndex;
    private final int spellId;

    public ActionMagicOnNPC(int npcIndex, int spellId) {
        super(ActionType.MAGE_NPC);
        this.npcIndex = npcIndex;
        this.spellId = spellId;
    }

    public int getNpcIndex() {
        return npcIndex;
    }

    public int getSpellId() {
        return spellId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActionMagicOnNPC that = (ActionMagicOnNPC) o;
        return npcIndex == that.npcIndex && spellId == that.spellId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), npcIndex, spellId);
    }

    @Override
    public boolean functionallyEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionMagicOnNPC that = (ActionMagicOnNPC) o;
        return npcIndex == that.npcIndex && spellId == that.spellId;
    }

    @Override
    public String toString() {
        return "ActionMagicOnNPC{" +
                "npcIndex=" + npcIndex +
                ", spellId=" + spellId +
                '}';
    }
}
