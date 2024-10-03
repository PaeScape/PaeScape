package com.arlania.world.entity.impl.player.antibotting.actions;

import java.util.Objects;

public class ActionMagicOnPlayer extends Action {
    private final int playerIndex;
    private final int spellId;

    public ActionMagicOnPlayer(int playerIndex, int spellId) {
        super(ActionType.MAGIC_ON_PLAYER);

        this.playerIndex = playerIndex;
        this.spellId = spellId;
    }

    public int getSpellId() {
        return spellId;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActionMagicOnPlayer that = (ActionMagicOnPlayer) o;
        return playerIndex == that.playerIndex && spellId == that.spellId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), playerIndex, spellId);
    }

    @Override
    public boolean functionallyEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionMagicOnPlayer that = (ActionMagicOnPlayer) o;
        return playerIndex == that.playerIndex && spellId == that.spellId;
    }

    @Override
    public String toString() {
        return "ActionMagicOnPlayer{" +
                "playerIndex=" + playerIndex +
                ", spellId=" + spellId +
                '}';
    }
}
