package com.arlania.world.content;

import com.arlania.model.*;
import com.arlania.model.container.impl.Bank;
import com.arlania.util.Misc;
import com.arlania.world.entity.impl.player.Player;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Presets {

    private final int[][][] savedInventories;
    private final int[][][] savedEquipments;

    public Presets(int[][][] savedInventories, int[][][] savedEquipments) {
        this.savedEquipments = savedEquipments;
        this.savedInventories = savedInventories;
    }

    public JsonObject toJson() {
        Gson builder = new Gson();
        JsonObject presetsJson = new JsonObject();
        presetsJson.add("savedInventories", builder.toJsonTree(savedInventories));
        presetsJson.add("savedEquipments", builder.toJsonTree(savedEquipments));
        return presetsJson;
    }

    public static Presets loadJson(JsonObject jo) {
        Gson builder = new Gson();
        int[][][] si = builder.fromJson(jo.get("savedInventories"), int[][][].class);
        int[][][] se = builder.fromJson(jo.get("savedEquipments"), int[][][].class);
        return new Presets(si, se);
    }

    public static void load(Player player) {
        if (player.getStaffRights() != StaffRights.DEVELOPER)
            return;
        if (player.getGameMode() == GameMode.ULTIMATE_IRONMAN) {
            player.sendMessage("UIM may not use presets.");
            return;
        }

        int option = player.presetOption;
        if (player.prestige < player.presetOption) {
            player.sendMessage(String.format("You need Prestige %d to use this Preset.", option));
            return;
        }

        bankEverything(player);

        //Inventory
        for (int i = 0; i < 28; i++) {
            int itemId = player.getPresets().savedInventories[option][i][0];
            int presetAmount = player.getPresets().savedInventories[option][i][1];

            if (itemId > 0 && presetAmount > 0) {
                //compare quantity of item in bank with the Preset amount
                if (player.getInventory().getItems()[i].getId() == -1) {
                    Bank bankTabForItem = player.getBank(Bank.getTabForItem(player, itemId));
                    int bankSlotForItem = bankTabForItem.getSlot(itemId);
                    int amountAvailable = bankTabForItem.getAmount(itemId);
                    Item item = new Item(itemId, amountAvailable);
                    String itemName = item.getName();

                    if (amountAvailable == 0) {
                        // none found in bank
                        player.sendMessage("@red@No " + itemName + " found in your bank to withdraw!");
                        continue;
                    }

                    presetAmount = Math.min(amountAvailable, presetAmount);

                    bankTabForItem.delete(item, bankSlotForItem, true, player.getInventory());
                    player.getInventory().add(itemId, presetAmount);
                    player.sendMessage("@red@Withdrew " + amountAvailable + "x " + itemName + " from your bank.");

                }
            }
        }

        // equipment
        for (int i = 0; i < player.getEquipment().capacity(); i++) {
            int itemId = player.getPresets().savedEquipments[option][i][0];
            int presetAmount = player.getPresets().savedEquipments[option][i][1];

            if (itemId > 0 && presetAmount > 0) {
                if (player.getEquipment().getItems()[i].getId() == -1) {
                    Bank bankTabForItem = player.getBank(Bank.getTabForItem(player, itemId));
                    int bankSlotForItem = bankTabForItem.getSlot(itemId);
                    int amountAvailable = bankTabForItem.getAmount(itemId);
                    Item item = new Item(itemId, amountAvailable);
                    String itemName = item.getName();

                    if (amountAvailable == 0) {
                        // none found in bank
                        player.sendMessage("@red@No " + itemName + " found in your bank to equip!");
                        continue;
                    }

                    presetAmount = Math.min(amountAvailable, presetAmount);

                    // equipping an item should really be extracted to its own function to unify this and EquipPacketListener
                    boolean equippable = true;
                    for (Skill skill : Skill.values()) {
                        if (skill == Skill.SKILLER)
                            continue;
                        if (item.getDefinition().getRequirement()[skill.ordinal()] > player.getSkillManager().getMaxLevel(skill)) {
                            StringBuilder vowel = new StringBuilder();
                            if (skill.getName().startsWith("a") || skill.getName().startsWith("e") || skill.getName().startsWith("i") || skill.getName().startsWith("o") || skill.getName().startsWith("u")) {
                                vowel.append("an ");
                            } else {
                                vowel.append("a ");
                            }
                            player.sendMessage("You need " + vowel + Misc.formatText(skill.getName()) + " level of at least " + item.getDefinition().getRequirement()[skill.ordinal()] + " to wear this.");
                            equippable = false;
                            break;
                        }
                    }

                    if (equippable) {
                        bankTabForItem.delete(item, bankSlotForItem, true, player.getEquipment());
                        player.getEquipment().setItem(i, new Item(itemId, presetAmount));
                        player.sendMessage("@red@Equipped " + amountAvailable + "x " + itemName + " from your bank.");
                    }
                }
            }
        }

        player.getEquipment().refreshItems();
        player.getInventory().refreshItems();
        player.getUpdateFlag().flag(Flag.APPEARANCE);
    }

    public static void save(Player player) {
        if (player.getStaffRights() != StaffRights.DEVELOPER)
            return;
        if (player.getGameMode() == GameMode.ULTIMATE_IRONMAN) {
            player.sendMessage("UIM may not use presets.");
            return;
        }

        int option = player.presetOption;

        player.getPresets().savedInventories[option] = new int[28][2];
        player.getPresets().savedEquipments[option] = new int[14][2];

        //save inventory
        for (int i = 0; i < player.getInventory().capacity(); i++) {
            int itemId = player.getInventory().forSlot(i).getId();
            int amount = player.getInventory().getAmountForSlot(i);

            player.getPresets().savedInventories[option][i][0] = itemId;
            player.getPresets().savedInventories[option][i][1] = amount;
        }

        //save equipment
        for (int i = 0; i < player.getEquipment().capacity(); i++) {
            int itemId = player.getEquipment().forSlot(i).getId();
            int amount = player.getEquipment().getAmountForSlot(i);

            player.getPresets().savedEquipments[option][i][0] = itemId;
            player.getPresets().savedEquipments[option][i][1] = amount;
        }

        sendPresetInterface(player);
    }

    public static void bankEverything(Player player) {

        for (Item it : player.getEquipment().getItems()) {
            if (player.getBank(player.getCurrentBankTab()).getFreeSlots() <= 0 && !player.getBank(player.getCurrentBankTab()).contains(it.getId())) { // n steps for contains
                player.getPacketSender().sendMessage("@red@We are unable to bank everything because your current bank tab is full.");
                return;
            }
        }

        Bank.depositItems(player, player.getEquipment(), true);

        for (Item it : player.getInventory().getItems()) {
            if (player.getBank(player.getCurrentBankTab()).getFreeSlots() <= 0 && !player.getBank(player.getCurrentBankTab()).contains(it.getId())) { // n steps for contains
                player.getPacketSender().sendMessage("@red@We are unable to bank everything because your current bank tab is full.");
                return;
            }
        }

        Bank.depositItems(player, player.getInventory(), true);
    }

    public static void sendPresetInterface(Player player) {

        if (!player.canUsePresets()) {
            player.getPacketSender().sendMessage("You must be near a bank to use Presets.");
            return;
        }

        player.getPacketSender().sendInterface(8714); //interface

        player.getPacketSender().sendString(8812, "Close Window"); //Button header
        player.getPacketSender().sendString(8716, "Presets"); //title header
        player.getPacketSender().sendString(8718, ""); //1st column header
        player.getPacketSender().sendString(8719, "Item"); //2nd column header

        player.getPacketSender().sendString(8846, "Preset 1"); //1st Button
        player.getPacketSender().sendString(8823, "Preset 2"); //2nd Button
        player.getPacketSender().sendString(8824, "Preset 3"); //3rd Button
        player.getPacketSender().sendString(8827, "Preset 4"); //4th Button
        player.getPacketSender().sendString(8837, "Preset 5"); //5th Button
        player.getPacketSender().sendString(8840, "Preset 6"); //6th Button
        player.getPacketSender().sendString(8843, "Preset 7"); //7th Button
        player.getPacketSender().sendString(8859, "Preset 8"); //8th Button
        player.getPacketSender().sendString(8862, "Preset 9"); //9th Button
        player.getPacketSender().sendString(8865, "Preset 10"); //10th Button
        player.getPacketSender().sendString(15303, ""); //11th Button
        player.getPacketSender().sendString(15306, ""); //12th Button
        player.getPacketSender().sendString(15309, "Load"); //13th Button
        //player.getPacketSender().sendString(15306, (player.presetLoad ? "@gre@Load" : "@red@Load")); //12th Button
        //player.getPacketSender().sendString(15309, (player.presetSave ? "@gre@Save" : "@red@Save")); //13th Button


        int option = player.presetOption;
        int string = 8760;
        int qty = 8720;

        player.getPacketSender().sendString(qty++, ""); //
        player.getPacketSender().sendString(string++, "Preset " + (option + 1)); //
        player.getPacketSender().sendString(qty++, ""); //
        player.getPacketSender().sendString(string++, ""); //

        for (int i = 0; i < player.getEquipment().capacity(); i++) {
            String itemName = new Item(player.getPresets().savedEquipments[option][i][0]).getName();
            int amount = player.getPresets().savedEquipments[option][i][1];

            if (amount < 1) {
                player.getPacketSender().sendString(qty++, ""); //
                player.getPacketSender().sendString(string++, ""); //
            } else {
                player.getPacketSender().sendString(qty++, ""); //
                if (amount == 1)
                    player.getPacketSender().sendString(string++, itemName); //
                else if (amount > 1)
                    player.getPacketSender().sendString(string++, itemName + " (" + amount + ")"); //
            }
        }

        /*for (int i = 0; i < player.getInventory().capacity(); i++) {
            player.getPacketSender().sendString(qty++, "" + player.getPresets().savedInventories[option][i][1]); //
            player.getPacketSender().sendString(string++, "" + player.getPresets().savedInventories[option][i][0]); //
        }*/


        player.getPacketSender().sendString(qty++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(qty++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(qty++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(qty++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(qty++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(qty++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(qty++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(qty++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(qty++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(qty++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(qty++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(qty++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(qty++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(qty++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(qty++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(qty++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(qty++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(qty++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(qty++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(qty++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(qty++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(qty++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(qty++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(qty++, ""); //
        player.getPacketSender().sendString(string++, ""); //


    }

    public static void handleButton(Player player, int button) {

        switch (button) {
            case 8846:
                player.presetOption = 0;
                sendPresetInterface(player);
                break;
            case 8823:
                player.presetOption = 1;
                sendPresetInterface(player);
                break;
            case 8824:
                player.presetOption = 2;
                sendPresetInterface(player);
                break;
            case 8827:
                player.presetOption = 3;
                sendPresetInterface(player);
                break;
            case 8837:
                player.presetOption = 4;
                sendPresetInterface(player);
                break;
            case 8840:
                player.presetOption = 5;
                sendPresetInterface(player);
                break;
            case 8843:
                player.presetOption = 6;
                sendPresetInterface(player);
                break;
            case 8859:
                player.presetOption = 7;
                sendPresetInterface(player);
                break;
            case 8862:
                player.presetOption = 8;
                sendPresetInterface(player);
                break;
            case 8865:
                player.presetOption = 9;
                sendPresetInterface(player);
                break;
            case 15303:
                break;
            case 15306:
                //player.presetLoad = true;
                //player.presetSave = false;
                //sendPresetInterface(player, 0);
//                break;
            case 15309:
                load(player);
                break;

        }

    }
}


