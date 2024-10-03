package com.arlania.world.content.minigames.impl.ccg;

import com.arlania.GameServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;

public class CardDefinitions {

    /**
     * The directory in which card definitions are found.
     */
    private static final String FILE_DIRECTORY = "./src/com/arlania/world/content/minigames/impl/CCG/cardDatabase.txt";

    /**
     * The max amount of items that will be loaded.
     */
    private static final int MAX_AMOUNT_OF_CARDS = 1000;


    public static CardDefinitions[] definitions = new CardDefinitions[MAX_AMOUNT_OF_CARDS];

    /**
     * Loading all item definitions
     */
    public static void init() {
        CardDefinitions definition = new CardDefinitions();
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

                switch (token.toLowerCase()) {
                    case "item id":
                        int id = Integer.valueOf(value);
                        definition = new CardDefinitions();
                        definition.id = id;
                        break;
                    case "name":
                        if (value == null || definition == null)
                            continue;
                        definition.name = value;
                        break;
                    case "value":
                        if (value == null || definition == null)
                            continue;
                        int price = Integer.valueOf(value);
                        definition.value = price;
                        break;
                    case "power":
                        if (value == null || definition == null)
                            continue;
                        int power = Integer.valueOf(value);
                        definition.value = power;
                        break;
                    case "level":
                        if (value == null || definition == null)
                            continue;
                        int level = Integer.valueOf(value);
                        definition.value = level;
                        break;
                    case "ongoing":
                        if (value == null || definition == null)
                            continue;
                        definition.ongoing = value;
                        break;
                    case "on reveal":
                        if (value == null || definition == null)
                            continue;
                        definition.onReveal = value;
                        break;
                    case "characteristic":
                        if (value == null || definition == null)
                            continue;
                        definition.characteristic = value;
                        break;
                    case "card type":
                        if (value == null || definition == null)
                            continue;
                        definition.cardType = CardDefinitions.CardType.valueOf(value);
                        break;
                }
            }
            reader.close();

        } catch (IOException e) {
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
        }
    }

    public static CardDefinitions[] getDefinitions() {
        return definitions;
    }


    public static CardDefinitions forId(int id) {
        return (id < 0 || id > MAX_AMOUNT_OF_CARDS || definitions[id] == null) ? new CardDefinitions() : definitions[id];
    }


    public static int getMaxAmountOfItems() {
        return MAX_AMOUNT_OF_CARDS;
    }


    public int id = 0;

    public int getId() {
        return id;
    }


    private String name = "None";

    public String getName() {
        return name;
    }


    private final String description = "Null";

    public String getDescription() {
        return description;
    }


    private String onReveal = "Null";

    public String getOnReveal() {
        return onReveal;
    }


    private String ongoing = "Null";

    public String getOngoing() {
        return ongoing;
    }


    private String characteristic = "Null";

    public String getCharacteristic() {
        return characteristic;
    }

    public int value;

    public int getValue() {
        return value;
    }


    public int power;

    public int getPower() {
        return power;
    }

    public int level;

    public int getLevel() {
        return level;
    }


    public CardType cardType = CardType.CHARACTER;

    public CardType getCardType() {
        return cardType;
    }


    public double[] bonus = new double[18];

    public double[] getBonus() {
        return bonus;
    }

    public int[] requirement = new int[25];

    public int[] getRequirement() {
        return requirement;
    }

    public enum CardType {
        CHARACTER, ITEM, EQUIPMENT, LOCATION
    }

    @Override
    public String toString() {
        return "[CardDefinition(" + id + ")] - Name: " + name + "; card type: " + getCardType() + "; value: " + value;
    }

    public static int getItemId(String itemName) {
        for (int i = 0; i < MAX_AMOUNT_OF_CARDS; i++) {
            if (definitions[i] != null) {
                if (definitions[i].getName().equalsIgnoreCase(itemName)) {
                    return definitions[i].getId();
                }
            }
        }
        return -1;
    }

}
