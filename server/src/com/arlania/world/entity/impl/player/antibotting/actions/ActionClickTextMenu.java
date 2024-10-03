package com.arlania.world.entity.impl.player.antibotting.actions;

import java.util.Objects;

public class ActionClickTextMenu extends Action {

    private final int interfaceId;
    private final int menuId;

    public ActionClickTextMenu(int interfaceId, int menuId) {
        super(ActionType.CLICK_TEXT_MENU);
        this.interfaceId = interfaceId;
        this.menuId = menuId;
    }

    public int getInterfaceId() {
        return interfaceId;
    }

    public int getMenuId() {
        return menuId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActionClickTextMenu that = (ActionClickTextMenu) o;
        return interfaceId == that.interfaceId && menuId == that.menuId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), interfaceId, menuId);
    }

    @Override
    public boolean functionallyEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionClickTextMenu that = (ActionClickTextMenu) o;
        return interfaceId == that.interfaceId && menuId == that.menuId;
    }

    @Override
    public String toString() {
        return "ActionClickTextMenu{" +
                "interfaceId=" + interfaceId +
                ", menuId=" + menuId +
                '}';
    }
}
