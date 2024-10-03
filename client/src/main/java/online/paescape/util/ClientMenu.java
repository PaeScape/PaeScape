package online.paescape.util;

import net.runelite.api.*;
import net.runelite.api.widgets.Widget;
import online.paescape.Client;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * @author <a href="https://rune-server.org/members/294280-era_/">Era</a> || 2/21/2024 - 8:07 AM
 * @Discord <a href="https://discordapp.com/users/232266021413584896">era_rsps</a>
 */
public class ClientMenu implements MenuEntry {

    String option, target;
    MenuAction menuAction;
    long identifier;
    int type, param0, param1;
    boolean isForceLeftClick, deprioritized;
    MenuEntry instance;
    Client client;
    public ClientMenu(){

    }
    public ClientMenu(Client client, String option, String target, MenuAction menuAction, long indentifier, int param0, int param1) {
        this.client = client;
        this.option = option;
        this.target = target;
        this.menuAction = menuAction;
        this.type = type;
        this.identifier = identifier;
        this.param0 = param0;
        this.param1 = param1;
        this.isForceLeftClick = false;
        this.deprioritized = false;
    }

    public ClientMenu(String option, String target, MenuAction menuAction, long indentifier, int param0, int param1, boolean isForceLeftClick) {
        this.client = client;
        this.option = option;
        this.target = target;
        this.menuAction = menuAction;
        this.type = type;
        this.identifier = identifier;
        this.param0 = param0;
        this.param1 = param1;
        this.isForceLeftClick = isForceLeftClick;
        this.deprioritized = false;
    }
    public ClientMenu(String option, String target, MenuAction menuAction, long indentifier, int param0, int param1, boolean isForceLeftClick, boolean deproritize) {
        this.client = client;
        this.option = option;
        this.target = target;
        this.menuAction = menuAction;
        this.type = type;
        this.identifier = identifier;
        this.param0 = param0;
        this.param1 = param1;
        this.isForceLeftClick = isForceLeftClick;
        this.deprioritized = deprioritized;
    }

    /**
     * The option text added to the menu. (ie. "Walk here", "Use")
     */
    @Override
    public String getOption() {
        return option;
    }

    @Override
    public MenuEntry setOption(String option) {
        this.option = option;
        update();
        return this;
    }

    private void update() {
//        client.
    }

    /**
     * The target of the action. (ie. Item or Actor name)
     * <p>
     * If the option does not apply to any target, this field
     * will be set to empty string.
     */
    @Override
    public String getTarget() {
        return target;
    }

    @Override
    public MenuEntry setTarget(String target) {
        this.target = target;
        return this;
    }

    /**
     * An identifier value for the target of the action.
     */
    @Override
    public long getIdentifier() {
        return identifier;
    }

    @Override
    public MenuEntry setIdentifier(long identifier) {
        this.identifier = identifier;
        return this;
    }

    /**
     * The action the entry will trigger.
     */
    @Override
    public MenuAction getType() {
        return menuAction;
    }

    @Override
    public MenuEntry setType(MenuAction type) {
        this.type = type.getId();
        return this;
    }

    /**
     * An additional parameter for the action.
     */
    @Override
    public int getParam0() {
        return param0;
    }

    @Override
    public MenuEntry setParam0(int param0) {
        this.param0 = param0;
        return this;
    }

    /**
     * A second additional parameter for the action.
     */
    @Override
    public int getParam1() {
        return param1;
    }

    @Override
    public MenuEntry setParam1(int param1) {
        this.param1 = param1;
        return this;
    }

    /**
     * If this is true and you have single mouse button on and this entry is
     * the top entry the right click menu will not be opened when you left click
     * <p>
     * This is used  for shift click
     */
    @Override
    public boolean isForceLeftClick() {
        return isForceLeftClick;
    }

    @Override
    public MenuEntry setForceLeftClick(boolean forceLeftClick) {
        this.isForceLeftClick = forceLeftClick;
        return this;
    }

    /**
     * Deprioritized menus are sorted in the menu to be below the other menu entries.
     *
     * @return
     */
    @Override
    public boolean isDeprioritized() {
        return false;
    }

    @Override
    public MenuEntry setDeprioritized(boolean deprioritized) {
        return null;
    }

    /**
     * Set a callback to be called when this menu option is clicked
     *
     * @param callback
     * @return
     */
    @Override
    public MenuEntry onClick(Consumer<MenuEntry> callback) {
        return this;
    }

}
