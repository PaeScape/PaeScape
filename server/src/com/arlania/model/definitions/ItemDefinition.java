package com.arlania.model.definitions;


import com.arlania.GameSettings;
import com.arlania.model.container.impl.Equipment;
import com.arlania.model.container.impl.Overrides;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * This file manages every item definition, which includes
 * their name, description, value, skill requirements, etc.
 *
 * @author relex lawl
 */

public class ItemDefinition {

    /**
     * The directory in which item definitions are found.
     */
    private static final String FILE_DIRECTORY = "./data/def/txt/Items.txt";

    /**
     * The max amount of items that will be loaded.
     */
    //private static final int MAX_AMOUNT_OF_ITEMS = 25000+250000;
    private static final int MAX_AMOUNT_OF_ITEMS = 300000;


    private static final String[] fullbody = {"top", "chestplate", "shirt", "platebody", "Ahrims robetop",
            "Karils leathertop", "brassard", "Robe top", "robetop", "platebody (t)", "platebody (g)", "chestplate",
            "Guthix body", "Saradomin body", "Zamorak body", "Armadyl body", "Bandos body", "Ancient body", "torso",
            "hauberk", "Dragon chainbody", "Corrupt dragon chainbody", "Morrigan's leather body", "poncho",
            "Pernix body", "wings", "Primal chainbody", "Vesta chainbody", "Inquisitor's hauberk", "Justiciar chestguard", "Pyromancer garb", "coat",
            "Crystal body"};

    /**
     * ItemDefinition array containing all items' definition values.
     */
    public static ItemDefinition[] definitions = new ItemDefinition[MAX_AMOUNT_OF_ITEMS];

    /**
     * Loading all item definitions
     */
    public static void init() {
        ItemDefinition definition = new ItemDefinition();
        try {
            File file = new File(FILE_DIRECTORY);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("inish")) {
                    definitions[definition.id] = definition;
                    continue;
                }
                String[] args = line.split(": ");
                if (args.length <= 1)
                    continue;
                String token = args[0], value = args[1];
                if (line.contains("Bonus[")) {
                    String[] other = line.split("]");
                    int index = Integer.valueOf(line.substring(6, other[0].length()));
                    double bonus = Double.valueOf(value);
                    definition.bonus[index] = bonus;
                    continue;
                }
                if (line.contains("Requirement[")) {
                    String[] other = line.split("]");
                    int index = Integer.valueOf(line.substring(12, other[0].length()));
                    int requirement = Integer.valueOf(value);
                    definition.requirement[index] = requirement;
                    continue;
                }
                switch (token.toLowerCase()) {
                    case "item id":
                        int id = Integer.valueOf(value);
                        definition = new ItemDefinition();
                        definition.id = id;
                        break;
                    case "name":
                        if (value == null || definition == null)
                            continue;
                        definition.name = value;
                        break;
                    case "examine":
                        if (value == null || definition == null)
                            continue;
                        definition.description = value;
                        break;
                    case "value":
                        if (value == null || definition == null)
                            continue;
                        int price = Integer.valueOf(value);
                        definition.value = price;
                        break;
                    case "stackable":
                        if (value == null || definition == null)
                            continue;
                        definition.stackable = Boolean.valueOf(value);
                        break;
                    case "noted":
                        if (value == null || definition == null)
                            continue;
                        definition.noted = Boolean.valueOf(value);
                        break;
                    case "double-handed":
                        if (value == null || definition == null)
                            continue;
                        definition.isTwoHanded = Boolean.valueOf(value);
                        break;
                    case "equipment type":
                        if (value == null || definition == null)
                            continue;
                        definition.equipmentType = EquipmentType.valueOf(value);
                        break;
                    case "is weapon":
                        if (value == null || definition == null)
                            continue;
                        definition.weapon = Boolean.valueOf(value);
                        break;
                }
            }
            reader.close();

            ItemDefinitionOSRS.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ItemDefinition[] getDefinitions() {
        return definitions;
    }

    /**
     * Gets the item definition correspondent to the id.
     *
     * @param id The id of the item to fetch definition for.
     * @return definitions[id].
     */
    public static ItemDefinition forId(int id) {
        //return (id < 0 || id > definitions.length || definitions[id] == null) ? new ItemDefinition() : definitions[id];
        return (id < 0 || id > 300000 || definitions[id] == null) ? new ItemDefinition() : definitions[id];
    }

    /**
     * Gets the max amount of items that will be loaded
     * in Niobe.
     *
     * @return The maximum amount of item definitions loaded.
     */
    public static int getMaxAmountOfItems() {
        return MAX_AMOUNT_OF_ITEMS;
    }

    /**
     * The id of the item.
     */
    public int id = 0;

    /**
     * Gets the item's id.
     *
     * @return id.
     */
    public int getId() {
        return id;
    }

    /**
     * The name of the item.
     */
    private String name = "None";

    /**
     * Gets the item's name.
     *
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * The item's description.
     */
    private String description = "Null";

    /**
     * Gets the item's description.
     *
     * @return description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Flag to check if item is stackable.
     */
    public boolean stackable;

    /**
     * Checks if the item is stackable.
     *
     * @return stackable.
     */
    public boolean isStackable() {

        if (noted)
            return true;
        return stackable;
    }

    /**
     * The item's shop value.
     */
    public int value;

    /**
     * Gets the item's shop value.
     *
     * @return value.
     */
    public int getValue() {
        return isNoted() ? forId(getId() - 1).value : value;
    }

    /**
     * Gets the item's equipment slot index.
     *
     * @return equipmentSlot.
     */
    public int getEquipmentSlot() {
        if (getId() >= GameSettings.OSRS_ITEMS_OFFSET) {
            return getSlot();
        }
        return equipmentType.slot;
    }


    /**
     * Gets the item's overrides slot index.
     *
     * @return overrideSlot.
     */
    public int getOverrideSlot() {
        if (getId() >= GameSettings.OSRS_ITEMS_OFFSET) {
            return getSlot();
        }
        return overrideType.slot;
    }

    /**
     * The slot the item goes in.
     */
    private byte slot;

    /**
     * Gets the slot
     *
     * @return The slot.
     */
    public byte getSlot() {
        return slot;
    }


    /**
     * Flag that checks if item is noted.
     */
    public boolean noted;

    /**
     * Checks if item is noted.
     *
     * @return noted.
     */
    public boolean isNoted() {
        if (getId() >= GameSettings.OSRS_ITEMS_OFFSET) {
            return isNoted(getId());
        }
        return noted;
    }


    /**
     * Check to see if an item is noted.
     *
     * @param itemId The item ID of the item which is to be checked.
     * @return True in case the item is noted, False otherwise.
     */
    public boolean isNoted(int itemId) {

        if (itemId < 0) {
            return false;
        }
        return getDescription().startsWith("Swap this note at any bank");
    }

    public boolean isTwoHanded;

    /**
     * Checks if item is two-handed
     */
    public boolean isTwoHanded() {

        return isTwoHanded;
    }

    public boolean weapon;

    public boolean isWeapon() {
        return weapon;
    }

    public EquipmentType equipmentType = EquipmentType.WEAPON;

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public OverrideType overrideType = OverrideType.WEAPON;

    public OverrideType getOverrideType() {
        return overrideType;
    }

    public boolean fullmask;

    /**
     * Checks if item is full body.
     */
    public boolean isFullBody() {
        if (getId() >= GameSettings.OSRS_ITEMS_OFFSET) {
            return isFullBodyOSRS();
        }
        return equipmentType.equals(EquipmentType.PLATEBODY);
    }


    public boolean isFullBodyOSRS() {
        String equipmentName = forId(getId()).getName();

        if(getId() == 225004)
            return true;

        if (equipmentName == null) {
            return false;
        }
        for (int i = 0; i < fullbody.length; i++) {
            if (equipmentName.endsWith(fullbody[i]) || equipmentName.contains(fullbody[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if item is full helm.
     */
    public boolean isFullHelm() {
        if (getId() == 142411) {
            return true;
        }
        if (getId() >= GameSettings.OSRS_ITEMS_OFFSET) {
            return fullmask;
        }
        return equipmentType.equals(EquipmentType.FULL_HELMET);
    }

    public double[] bonus = new double[18];

    public double[] getBonus() {
        return bonus;
    }

    public int[] requirement = new int[25];

    public int[] getRequirement() {
        return requirement;
    }

    public enum EquipmentType {
        HAT(Equipment.HEAD_SLOT),
        CAPE(Equipment.CAPE_SLOT),
        SHIELD(Equipment.SHIELD_SLOT),
        GLOVES(Equipment.HANDS_SLOT),
        BOOTS(Equipment.FEET_SLOT),
        AMULET(Equipment.AMULET_SLOT),
        RING(Equipment.RING_SLOT),
        ARROWS(Equipment.AMMUNITION_SLOT),
        FULL_MASK(Equipment.HEAD_SLOT),
        FULL_HELMET(Equipment.HEAD_SLOT),
        BODY(Equipment.BODY_SLOT),
        PLATEBODY(Equipment.BODY_SLOT),
        LEGS(Equipment.LEG_SLOT),
        WEAPON(Equipment.WEAPON_SLOT);

        EquipmentType(int slot) {
            this.slot = slot;
        }

        private final int slot;
    }



    public enum OverrideType {
        HAT(Overrides.HEAD_SLOT),
        CAPE(Overrides.CAPE_SLOT),
        SHIELD(Overrides.SHIELD_SLOT),
        GLOVES(Overrides.HANDS_SLOT),
        BOOTS(Overrides.FEET_SLOT),
        AMULET(Overrides.AMULET_SLOT),
        RING(Overrides.RING_SLOT),
        ARROWS(Overrides.AMMUNITION_SLOT),
        FULL_MASK(Overrides.HEAD_SLOT),
        FULL_HELMET(Overrides.HEAD_SLOT),
        BODY(Overrides.BODY_SLOT),
        PLATEBODY(Overrides.BODY_SLOT),
        LEGS(Overrides.LEG_SLOT),
        WEAPON(Overrides.WEAPON_SLOT);

        OverrideType(int slot) {
            this.slot = slot;
        }

        private final int slot;
    }

    @Override
    public String toString() {
        return "[ItemDefinition(" + id + ")] - Name: " + name + "; equipment slot: " + getEquipmentSlot() + "; value: "
                + value + "; stackable ? " + stackable + "; noted ? " + noted + "; 2h ? " + isTwoHanded;
    }

    public static int getItemId(String itemName) {
        for (int i = 0; i < MAX_AMOUNT_OF_ITEMS; i++) {
            if (definitions[i] != null) {
                if (definitions[i].getName().equalsIgnoreCase(itemName)) {
                    return definitions[i].getId();
                }
            }
        }
        return -1;
    }
}
