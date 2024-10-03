package com.arlania.world.entity.impl.player.antibotting.actions;

import java.util.Objects;

public class ActionPlayerRelation extends Action {
    private final int opcode;
    private final long username;

    public ActionPlayerRelation(int opcode, long username) {
        super(ActionType.PLAYER_RELATION);
        this.opcode = opcode;
        this.username = username;
    }

    public int getOpcode() {
        return opcode;
    }

    public long getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActionPlayerRelation that = (ActionPlayerRelation) o;
        return opcode == that.opcode && username == that.username;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), opcode, username);
    }

    @Override
    public boolean functionallyEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionPlayerRelation that = (ActionPlayerRelation) o;
        return type == that.type && opcode == that.opcode && username == that.username;
    }

    @Override
    public String toString() {
        return "ActionPlayerRelation{" +
                "opcode=" + opcode +
                ", username=" + username +
                '}';
    }
}
