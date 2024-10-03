package com.arlania.world.entity.impl.player.antibotting.actions;

import java.util.Objects;

public class ActionExamineNPC extends Action {
    private final int npcId;

    public ActionExamineNPC(int npcId) {
        super(ActionType.EXAMINE_ITEM);
        this.npcId = npcId;
    }

    public int getNpcId() {
        return npcId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActionExamineNPC that = (ActionExamineNPC) o;
        return npcId == that.npcId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), npcId);
    }

    @Override
    public boolean functionallyEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionExamineNPC that = (ActionExamineNPC) o;
        return npcId == that.npcId;
    }

    @Override
    public String toString() {
        return "ActionExamineNPC{" +
                "npcId=" + npcId +
                '}';
    }
}
