package com.arlania.world.entity.impl.player.antibotting.actions;

import java.util.Objects;

public class ActionButtonClick extends Action {

    private final int buttonId;

    public ActionButtonClick(int buttonId) {
        super(ActionType.BUTTON_CLICK);
        this.buttonId = buttonId;
    }

    public int getButtonId() {
        return buttonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActionButtonClick that = (ActionButtonClick) o;
        return buttonId == that.buttonId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), buttonId);
    }

    @Override
    public boolean functionallyEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionButtonClick that = (ActionButtonClick) o;
        return buttonId == that.buttonId && type == that.type;
    }

    @Override
    public String toString() {
        return "ActionButtonClick{" +
                "buttonId=" + buttonId +
                '}';
    }
}
