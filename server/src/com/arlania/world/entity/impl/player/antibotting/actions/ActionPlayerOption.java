package com.arlania.world.entity.impl.player.antibotting.actions;

import java.util.Objects;

public class ActionPlayerOption extends Action {
    private final String username;

    public ActionPlayerOption(String username) {
        super(ActionType.PLAYER_OPTION);
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
        ActionPlayerOption that = (ActionPlayerOption) o;
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
        ActionPlayerOption that = (ActionPlayerOption) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public String toString() {
        return "ActionPlayerOption{" +
                "username='" + username + '\'' +
                '}';
    }
}
