package com.arlania.world.entity.impl.player.antibotting.actions;

import java.util.Objects;

public class Action {
    protected ActionType type;
    protected long when;

    public Action(ActionType type) {
        this.type = type;
        this.when = System.currentTimeMillis();
    }

    public ActionType getType() {
        return type;
    }

    public long getWhen() {
        return when;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Action)) return false;

        Action action = (Action) o;

        if (when != action.when) return false;
        return type == action.type;
    }

    public boolean functionallyEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action action = (Action) o;
        return type == action.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, when);
    }

    @Override
    public String toString() {
        return "Action{" +
                "type=" + type +
                '}';
    }

    public enum ActionType {
        BUTTON_CLICK, CHANGE_APPEARANCE, CHANGE_RELATION_STATUS, CHAT,
        CLICK_TEXT_MENU, CLOSE_INTERFACE, COMMAND, DIALOGUE, DROP_ITEM,
        ENTER_INPUT, EQUIP, EXAMINE_ITEM, EXAMINE_NPC, FOLLOW_PLAYER,
        FIRST_ITEM_ACTION, SECOND_ITEM_ACTION, THIRD_ITEM_ACTION, FIRST_ITEM_CONTAINER_ACTION,
        SECOND_ITEM_CONTAINER_ACTION, THIRD_ITEM_CONTAINER_ACTION, FOURTH_ITEM_CONTAINER_ACTION,
        FIFTH_ITEM_CONTAINER_ACTION, SIXTH_ITEM_CONTAINER_ACTION, MAGIC_ON_ITEMS, MAGIC_ON_GROUNDITEMS, MAGIC_ON_PLAYER,
        MOVEMENT, FIRST_NPC_OPTION, SECOND_NPC_OPTION, THIRD_NPC_OPTION, FOURTH_NPC_OPTION, MAGE_NPC,
        ATTACK_NPC, FIRST_OBJECT_ACTION, SECOND_OBJECT_ACTION, THIRD_OBJECT_ACTION, FOURTH_OBJECT_ACTION,
        FIFTH_OBJECT_ACTION, PICKUP_ITEM, PLAYER_OPTION, PLAYER_RELATION, SEND_CLAN_CHAT, SWITCH_ITEM_SLOT, TRADE_INVITATION,
        ITEM_ON_ITEM, USE_ITEM, ITEM_ON_OBJECT, ITEM_ON_NPC, ITEM_ON_PLAYER, WITHDRAW_MONEY_FROM_POUCH, LOGIN
    }
}
