package com.arlania.world.entity.impl.player.antibotting.actions;

import java.util.Objects;

public class ActionCommand extends Action {
    private final String command;
    private final String wholeCommand;

    public ActionCommand(String command, String wholeCommand) {
        super(ActionType.COMMAND);
        this.command = command;
        this.wholeCommand = wholeCommand;
    }

    public String getCommand() {
        return command;
    }

    public String getWholeCommand() {
        return wholeCommand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActionCommand that = (ActionCommand) o;
        return Objects.equals(command, that.command) && Objects.equals(wholeCommand, that.wholeCommand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), command, wholeCommand);
    }

    @Override
    public boolean functionallyEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActionCommand that = (ActionCommand) o;
        return Objects.equals(command, that.command);
    }

    @Override
    public String toString() {
        return "ActionCommand{" +
                "command='" + command + '\'' +
                ", wholeCommand='" + wholeCommand + '\'' +
                '}';
    }
}
