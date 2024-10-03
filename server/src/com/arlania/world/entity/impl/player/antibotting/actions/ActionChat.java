package com.arlania.world.entity.impl.player.antibotting.actions;

public class ActionChat extends Action {
    public ActionChat() {
        super(ActionType.CHAT);
    }

    @Override
    public boolean functionallyEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionChat that = (ActionChat) o;
        return type == that.type;
    }

    @Override
    public String toString() {
        return "ActionChat{" +
                "type=" + type +
                '}';
    }
}
