package com.arlania.world.entity.impl.player.antibotting.actions;

import java.util.Objects;

public class ActionTradeInvitation extends Action {
    private final String username;

    public ActionTradeInvitation(String username) {
        super(ActionType.TRADE_INVITATION);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActionTradeInvitation that = (ActionTradeInvitation) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username);
    }

    @Override
    public boolean functionallyEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActionTradeInvitation that = (ActionTradeInvitation) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public String toString() {
        return "ActionTradeInvitation{" +
                "username='" + username + '\'' +
                '}';
    }
}
