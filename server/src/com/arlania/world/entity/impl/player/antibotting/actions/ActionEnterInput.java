package com.arlania.world.entity.impl.player.antibotting.actions;

import java.util.Objects;

public class ActionEnterInput extends Action {

    private final String input;

    public ActionEnterInput(String input) {
        super(ActionType.ENTER_INPUT);
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActionEnterInput that = (ActionEnterInput) o;
        return Objects.equals(input, that.input);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), input);
    }

    @Override
    public boolean functionallyEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionEnterInput that = (ActionEnterInput) o;
        return Objects.equals(input, that.input);
    }

    @Override
    public String toString() {
        return "ActionEnterInput{" +
                "input='" + input + '\'' +
                '}';
    }
}
