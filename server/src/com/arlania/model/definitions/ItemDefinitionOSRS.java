package com.arlania.model.definitions;

import com.arlania.GameSettings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Holds information regarding items
 *
 * @author Stuart
 * @created 03/08/2012
 */
public class ItemDefinitionOSRS {


    /**
     * The array that contains all of the item definitions.
     */
    public static final ItemDefinition[] DEFINITIONS = new ItemDefinition[25000];

    /**
     * Adds a new {@link ItemDefinition} to the memory.
     *
     * @param index the index to add the definition on.
     * @param def   the definition to add.
     */
    public static void add(int index, ItemDefinition def) {
        DEFINITIONS[index] = def;
    }

    /**
     * The definitions.
     */
    public static Map<Integer, ItemDefinition> definitions = new HashMap<>();

    /**
     * Loads item definitions from item_defs.json
     */

    private static final String FILE_DIRECTORY = "./data/def/osrs_item_definitions.json";

    public static void load() throws IOException {
        System.out.println("Loading item definitions...");

        ArrayList<ItemDefinition> list = new Gson().fromJson(FileUtils.readFileToString(new File("./data/osrs_item_definitions.json")), new TypeToken<List<ItemDefinition>>() {
        }.getType());

        //ArrayList<ItemDefinition> list = new Gson().fromJson(FileUtils.readFileToString(new File("./data/osrs_item_data.json")), new TypeToken<List<ItemDefinition>>() {
        //}.getType());

        list.stream().filter(Objects::nonNull).forEach(item -> definitions.put(item.getId() + GameSettings.OSRS_ITEMS_OFFSET, item));
        list.stream().filter(Objects::nonNull).forEach(item -> item.id = item.getId() + GameSettings.OSRS_ITEMS_OFFSET);

        ItemDefinition[] definitionsOsrs = new ItemDefinition[list.size()];
        list.toArray(definitionsOsrs);

        for (int i = 0; i < definitionsOsrs.length + 25000; i++) {
            boolean TwoHanded = false;
            boolean stackable = false;
            boolean noted = false;
            double[] newBonus = new double[18];
            int[] newRequirement = new int[25];

            ItemDefinition.definitions[i + GameSettings.OSRS_ITEMS_OFFSET] = definitions.get(i + GameSettings.OSRS_ITEMS_OFFSET);

            ItemDefinition definition = ItemDefinition.definitions[i + GameSettings.OSRS_ITEMS_OFFSET];

            if (definition != null) {

                String itemName = ItemDefinition.definitions[i + GameSettings.OSRS_ITEMS_OFFSET].getName();


                //bonuses
                if (definition.bonus != null) {
                    System.arraycopy(ItemDefinition.definitions[i + GameSettings.OSRS_ITEMS_OFFSET].bonus, 0, newBonus, 0, ItemDefinition.definitions[i + GameSettings.OSRS_ITEMS_OFFSET].bonus.length);
                    newBonus[14] = ItemDefinition.definitions[i + GameSettings.OSRS_ITEMS_OFFSET].bonus[10];
                    newBonus[16] = ItemDefinition.definitions[i + GameSettings.OSRS_ITEMS_OFFSET].bonus[11];

                }

                //requirements
				/*if (definition.requirement != null) {
					for (int j = 0; j < ItemDefinition.definitions[i + GameSettings.OSRS_ITEMS_OFFSET].requirement.length; j++) 
					{
						newRequirement[j] = ItemDefinition.definitions[i + GameSettings.OSRS_ITEMS_OFFSET].requirement[j];
					}
					newRequirement[21] = 0;
					newRequirement[22] = 0;
					newRequirement[23] = 0;
					newRequirement[24] = 0;
				}*/

                if (itemName.toLowerCase().contains("nquisitor")) {
                    if (itemName.toLowerCase().contains("ace")) {
                        newRequirement[0] = 80;
                        TwoHanded = false;
                    } else
                        newRequirement[1] = 30;
                }

                if (itemName.toLowerCase().contains("tentacle")) {
                    newRequirement[0] = 75;
                    TwoHanded = false;
                }

                if (itemName.toLowerCase().contains("neitiznot faceg"))
                    newRequirement[1] = 75;

                if (itemName.toLowerCase().contains("godsword"))
                    newRequirement[0] = 75;

                if (itemName.toLowerCase().contains("trident"))
                    newRequirement[6] = 70;

                if (itemName.toLowerCase().contains("rimstone"))
                    newRequirement[1] = 70;

                if (itemName.toLowerCase().contains("helm") ||
                        itemName.toLowerCase().contains("body") ||
                        itemName.toLowerCase().contains("leg") ||
                        itemName.toLowerCase().contains("shield") ||
                        itemName.toLowerCase().contains("defend")) {
                    if (itemName.toLowerCase().contains("rune"))
                        newRequirement[1] = 40;

                    if (itemName.toLowerCase().contains("adam"))
                        newRequirement[1] = 30;

                    if (itemName.toLowerCase().contains("mithril "))
                        newRequirement[1] = 20;

                    if (itemName.toLowerCase().contains("black "))
                        newRequirement[1] = 10;

                    if (itemName.toLowerCase().contains("steel "))
                        newRequirement[1] = 5;

                }

                //two-handed
                if (itemName.toLowerCase().contains("godsword"))
                    TwoHanded = true;
                if (itemName.toLowerCase().contains("longbow"))
                    TwoHanded = true;
                if (itemName.toLowerCase().contains("claw"))
                    TwoHanded = true;

                //stackable
                if (itemName.toLowerCase().contains("key"))
                    stackable = true;
                if (itemName.toLowerCase().contains("clue"))
                    stackable = true;
                if (itemName.toLowerCase().contains("seed"))
                    stackable = true;
                if (itemName.toLowerCase().contains("crate"))
                    stackable = true;
                if (itemName.toLowerCase().contains("bolt"))
                    stackable = true;
                if (itemName.toLowerCase().contains(" rune"))
                    stackable = true;
                if (itemName.toLowerCase().contains("amethyst"))
                    stackable = true;
                if (itemName.toLowerCase().contains("amethyst"))
                    stackable = true;

                switch (definition.getId()) {

                    case 213440:
                    case 213442:
                        stackable = true;
                        break;

                    case 221347:
                        stackable = false;
                        break;

                    case 219675:
                        TwoHanded = false;
                        break;
                }

                //noted
                if(definition.getDescription().equals("Swap this note at any bank for the equivalent item."))
                    noted = true;

                if(noted)
                    stackable = true;

                if(ItemDefinition.forId(i + GameSettings.OSRS_ITEMS_OFFSET).noted)
                    noted = true;

                if(ItemDefinition.forId(i + GameSettings.OSRS_ITEMS_OFFSET).stackable)
                    stackable = true;

                switch (definition.getId()) {

                    case 212011:
                    case 220695:
                    case 220696:
                    case 213441:
                        stackable = false;
                        break;
                }

                ItemDefinition.definitions[i + GameSettings.OSRS_ITEMS_OFFSET].bonus = newBonus;
                ItemDefinition.definitions[i + GameSettings.OSRS_ITEMS_OFFSET].requirement = newRequirement;
                ItemDefinition.definitions[i + GameSettings.OSRS_ITEMS_OFFSET].isTwoHanded = TwoHanded;
                ItemDefinition.definitions[i + GameSettings.OSRS_ITEMS_OFFSET].stackable = stackable;
                ItemDefinition.definitions[i + GameSettings.OSRS_ITEMS_OFFSET].noted = noted;

            }

        }


        System.out.println("Loaded " + definitions.size() + " OSRS item definitions.");
    }

    /**
     * Get an items definition by id.
     *
     * @param id The id.
     * @return The item definition.
     */
    public static ItemDefinition forId(int id) {
        return definitions.get(id);
    }

    /**
     * A map of all definitions
     *
     * @return the map
     */
    public static Map<Integer, ItemDefinition> getDefinitions() {
        return definitions;
    }

    /**
     * The id.
     */
    public int id;

    /**
     * The name.
     */
    private String name;

    /**
     * The description.
     */
    private String desc;

    /**
     * The value.
     */
    private int value;

    /**
     * The value of the drop
     */
    private int dropValue;

    /**
     * The bonuses.
     */
    private double[] bonus;

    /**
     * The slot the item goes in.
     */
    private byte slot;

    /**
     * Full mask flag.
     */
    private boolean fullmask;

    /**
     * Stackable flag
     */
    private boolean stackable;

    /**
     * Notable flag
     */
    private boolean noted;

    /**
     * Tradeable flag
     */
    private boolean tradable;

    /**
     * Wearable flag
     */
    private boolean wearable;

    /**
     * Show beard flag
     */
    private boolean showBeard;

    /**
     * Members flag
     */
    private boolean members;

    /**
     * Two handed flag
     */
    private boolean twoHanded;

    /**
     * Level requirements
     */
    private final int[] requirements = new int[25];

/*    public ItemDefinition(short id, String name, String description, Equipment.Slot equipmentSlot, boolean stackable, int shopValue, int lowAlchValue, int highAlchValue, int[] bonus, boolean twoHanded, boolean fullHelm, boolean fullMask, boolean platebody) {
        this.id = id;
        this.name = name;
        this.desc = description;
        this.slot = equipmentSlot;
        this.stackable = stackable;
        this.shopValue = shopValue;
        this.lowAlchValue = lowAlchValue;
        this.highAlchValue = highAlchValue;
        this.bonus = bonus;
        this.twoHanded = twoHanded;
        this.fullHelm = fullHelm;
        this.fullMask = fullMask;
        this.platebody = platebody;
    }*/

    /**
     * Get the id.
     *
     * @return The id.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the name.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the description.
     *
     * @return The description.
     */
    public String getDescription() {
        return desc;
    }

    /**
     * Get the value.
     *
     * @return The value.
     */
    public int getValue() {
        return value;
    }

    /**
     * Get the bonus.
     *
     * @return The bonus.
     */
    public double[] getBonus() {
        return bonus;
    }

    /**
     * Gets the slot
     *
     * @return The slot.
     */
    public byte getSlot() {
        return slot;
    }

    /**
     * Gets the fullmask flag
     *
     * @return The fullmask flag
     */
    public boolean isFullmask() {
        return fullmask;
    }

    /**
     * Is this item stackable?
     *
     * @return
     */
    public boolean isStackable() {
        return stackable;
    }

    /**
     * Can this item be noted?
     *
     * @return
     */
    public boolean isnoted() {
        return noted;
    }

    /**
     * Is this item tradable?
     *
     * @return
     */
    public boolean isTradable() {
        return tradable;
    }

    /**
     * Get the level requirements
     *
     * @return
     */
    public int[] getRequirements() {
        return requirements;
    }

    /**
     * Can this item be equipped
     *
     * @return
     */
    public boolean isWearable() {
        return wearable;
    }

    /**
     * Does this item show the players beard
     *
     * @return
     */
    public boolean showBeard() {
        return showBeard;
    }

    /**
     * Is this item two handed
     *
     * @return
     */
	/*public static boolean isTwoHanded() {
		if (this.getId() == 20997)
			return true;
		return twoHanded;
	}*/

    /**
     * Gets the drop value
     *
     * @return
     */
    public int getDropValue() {
        return dropValue;
    }

    /**
     * Is this a members item
     *
     * @return
     */
    public boolean isMembers() {
        return members;
    }

}
