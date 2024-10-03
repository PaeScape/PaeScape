package com.arlania.model.container.impl;

import com.arlania.GameSettings;
import com.arlania.model.GameMode;
import com.arlania.model.Item;
import com.arlania.model.StaffRights;
import com.arlania.model.container.ItemContainer;
import com.arlania.model.container.StackType;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.world.content.BankPin;
import com.arlania.world.entity.impl.player.Player;

public class UIMStorage extends ItemContainer {

    public UIMStorage(Player player) {
        super(player);
    }

    @Override
    public int capacity() {
        return 30;
    }

    @Override
    public StackType stackType() {
        return StackType.STACKS;
    }

    public UIMStorage open() {
        getPlayer().getSkillManager().stopSkilling();

        getPlayer().getPacketSender().sendClientRightClickRemoval();

        if (getPlayer().getBankPinAttributes().hasBankPin() && !getPlayer().getBankPinAttributes().hasEnteredBankPin()) {
            BankPin.init(getPlayer(), true);
            return this;
        }
        if (getPlayer().getGameMode() != GameMode.ULTIMATE_IRONMAN && getPlayer().getStaffRights() != StaffRights.DEVELOPER) {
            getPlayer().getPacketSender().sendInterfaceRemoval().sendMessage("Only UIM can use this.");
            return this;
        }
        getPlayer().getPacketSender().sendString(NAME_INTERFACE_CHILD_ID, "UIM Storage");
        sortItems().refreshItems();
        getPlayer().setBanking(true).setInputHandling(null);
        getPlayer().getPacketSender()
                .sendConfig(115, getPlayer().withdrawAsNote() ? 1 : 0)
                .sendConfig(304, getPlayer().swapMode() ? 1 : 0)
                .sendInterfaceSet(UIM_BANK_INTERFACE, UIM_BANK_INVENTORY_INTERFACE_ID);
        return this;
    }

    @Override
    public UIMStorage switchItem(ItemContainer to, Item item, int slot, boolean sort, boolean refresh) {
        if (!getPlayer().isBanking() || getPlayer().getInterfaceId() != UIM_BANK_INTERFACE || to instanceof Inventory && !getPlayer().getUimBank().contains(item.getId())) {
            getPlayer().getPacketSender().sendClientRightClickRemoval();
            return this;
        }
        ItemDefinition def = ItemDefinition.forId(item.getId() + 1);
        if (to.getFreeSlots() <= 0 && (!(to.contains(item.getId()) && item.getDefinition().isStackable())) && !(getPlayer().withdrawAsNote() && def != null && def.isNoted() && to.contains(def.getId()))) {
            to.full();
            return this;
        }
        if (item.getAmount() > to.getFreeSlots() && !item.getDefinition().isStackable()) {
            if (to instanceof Inventory) {
                if (getPlayer().withdrawAsNote()) {
                    if (def == null || !def.isNoted())
                        item.setAmount(to.getFreeSlots());
                } else
                    item.setAmount(to.getFreeSlots());
            }
        }

        if (getItems()[slot].getId() != item.getId() || !contains(item.getId()))
            return this;
        if (item.getAmount() > getAmount(item.getId()))
            item.setAmount(getAmount(item.getId()));
        if (item.getAmount() <= 0) {
            getItems()[slot].setId(-1);
            if (refresh)
                refreshItems();
            return this;
        }
        if (to instanceof Inventory) {
            boolean withdrawAsNote = getPlayer().withdrawAsNote() && def != null && def.isNoted() && item.getDefinition() != null && def.getName().equalsIgnoreCase(item.getDefinition().getName()) && !def.getName().contains("Torva") && !def.getName().contains("Virtus") && !def.getName().contains("Pernix") && !def.getName().contains("Torva");
            int checkId = withdrawAsNote ? item.getId() + 1 : item.getId();
            if (to.getAmount(checkId) + item.getAmount() > Integer.MAX_VALUE || to.getAmount(checkId) + item.getAmount() <= 0) {
                getPlayer().getPacketSender().sendMessage("You cannot withdraw that entire amount into your inventory.");
                return this;
            }
        }

        delete(item, slot, refresh, to);

        if (getPlayer().withdrawAsNote()) {
            if (def != null && def.isNoted() && item.getDefinition() != null && def.getName().equalsIgnoreCase(item.getDefinition().getName()) && !def.getName().contains("Torva") && !def.getName().contains("Virtus") && !def.getName().contains("Pernix") && !def.getName().contains("Torva"))
                item.setId(item.getId() + 1);
            else if (item.getId() > GameSettings.OSRS_ITEMS_OFFSET && ItemDefinition.forId(item.getId() - 1).getName() == ItemDefinition.forId(item.getId()).getName()) {

            } else
                getPlayer().getPacketSender().sendMessage("This item cannot be withdrawn as a note.");
        }
        to.add(item, refresh);
        if (sort)
            sortItems();
        if (refresh) {
            refreshItems();
            to.refreshItems();
        }
        return this;
    }

    public void depositItems(ItemContainer from, boolean ignoreReqs) {
        final Player p = getPlayer();
        if (!ignoreReqs && (!p.isBanking() || p.getInterfaceId() != UIM_BANK_INTERFACE)) {
            return;
        }
        int freeSlots = getFreeSlots();
        boolean fullMsg = false;
        for (Item it : from.getValidItems()) {
            Item toBank = new Item(Item.getUnNoted(it.getId()), it.getAmount());
            int slot = getSlot(toBank.getId());
            if (freeSlots <= 0 && !contains(toBank.getId())) { // n steps for contains
                fullMsg = true;
                continue;
            }
            int bankAmt;
            if (slot >= 0) {
                bankAmt = get(slot).getAmount();
                if (bankAmt + toBank.getAmount() <= 0) {
                    int canBank = (Integer.MAX_VALUE - bankAmt);
                    if (canBank == 0) {
                        getPlayer().getPacketSender().sendMessage("You cannot deposit more of that item.");
                        continue;
                    }
                    toBank.setAmount(canBank);
                }
            } else {
                freeSlots--;
            }

            from.switchItem(this, toBank, from.getSlot(toBank.getId()), false, false);
        }
        if (fullMsg) {
            p.getPacketSender().sendMessage("Storage full.");
        }
        if (!(from instanceof BeastOfBurden))
            from.refreshItems();
        sortItems().refreshItems();
    }

    @Override
    public ItemContainer refreshItems() {
        final Player player = getPlayer();
        player.getPacketSender().sendItemContainer(this, UIM_BANK_ITEM_CHILD_ID);
        player.getPacketSender().sendItemContainer(player.getInventory(), UIM_BANK_INVENTORY_ITEM_CHILD_ID);
        return this;
    }

    @Override
    public ItemContainer full() {
        getPlayer().getPacketSender().sendMessage("Not enough space in storage.");
        return this;
    }

    public final static int UIM_BANK_INTERFACE = 22147;
    public static final int UIM_BANK_ITEM_CHILD_ID = UIM_BANK_INTERFACE + 5;
    public final static int UIM_BANK_INVENTORY_INTERFACE_ID = 5063;
    public final static int UIM_BANK_INVENTORY_ITEM_CHILD_ID = 5064;

    public static final int NAME_INTERFACE_CHILD_ID = 3901;
}
