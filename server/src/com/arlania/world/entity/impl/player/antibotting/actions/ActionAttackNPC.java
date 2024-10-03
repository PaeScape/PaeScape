package com.arlania.world.entity.impl.player.antibotting.actions;

import java.util.Objects;

public class ActionAttackNPC extends Action {
    private final int npcId;

    public ActionAttackNPC(int npcId) {
        super(ActionType.ATTACK_NPC);
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
        ActionAttackNPC that = (ActionAttackNPC) o;
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
        ActionAttackNPC that = (ActionAttackNPC) o;
        return npcId == that.npcId;
    }

    @Override
    public String toString() {
        return "ActionAttackNPC{" +
                "npcId=" + npcId +
                '}';
    }
}
