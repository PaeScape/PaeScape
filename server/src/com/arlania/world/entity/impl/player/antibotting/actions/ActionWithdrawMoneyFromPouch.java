package com.arlania.world.entity.impl.player.antibotting.actions;

import java.util.Objects;

public class ActionWithdrawMoneyFromPouch extends Action {
    private final int amount;

    public ActionWithdrawMoneyFromPouch(int amount) {
        super(ActionType.WITHDRAW_MONEY_FROM_POUCH);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActionWithdrawMoneyFromPouch that = (ActionWithdrawMoneyFromPouch) o;
        return amount == that.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), amount);
    }

    @Override
    public boolean functionallyEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActionWithdrawMoneyFromPouch that = (ActionWithdrawMoneyFromPouch) o;
        return amount == that.amount;
    }

    @Override
    public String toString() {
        return "ActionWithdrawMoneyFromPouch{" +
                "amount=" + amount +
                '}';
    }
}
