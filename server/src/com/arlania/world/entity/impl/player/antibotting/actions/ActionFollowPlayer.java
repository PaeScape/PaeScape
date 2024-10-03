package com.arlania.world.entity.impl.player.antibotting.actions;

import java.util.Objects;

public class ActionFollowPlayer extends Action {
    private final String otherPlayersUsername;

    public ActionFollowPlayer(String otherPlayersUsername) {
        super(ActionType.FOLLOW_PLAYER);
        this.otherPlayersUsername = otherPlayersUsername;
    }

    public String getOtherPlayersUsername() {
        return otherPlayersUsername;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActionFollowPlayer that = (ActionFollowPlayer) o;
        return Objects.equals(otherPlayersUsername, that.otherPlayersUsername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), otherPlayersUsername);
    }

    @Override
    public boolean functionallyEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionFollowPlayer that = (ActionFollowPlayer) o;
        return Objects.equals(otherPlayersUsername, that.otherPlayersUsername);
    }

    @Override
    public String toString() {
        return "ActionFollowPlayer{" +
                "otherPlayersUsername='" + otherPlayersUsername + '\'' +
                '}';
    }
}
