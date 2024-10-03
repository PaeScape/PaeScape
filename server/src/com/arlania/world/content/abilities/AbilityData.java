package com.arlania.world.content.abilities;

public enum AbilityData {

    MENU_1("Al Kharid", "", AbilityCategory.MENU, "City"),
    MENU_2("Al Kharid", "", AbilityCategory.MENU, "City"),
    MENU_3("Al Kharid", "", AbilityCategory.MENU, "City"),
    MENU_4("Al Kharid", "", AbilityCategory.MENU, "City"),
    MENU_5("Al Kharid", "", AbilityCategory.MENU, "City"),
    MENU_6("Al Kharid", "", AbilityCategory.MENU, "City"),
    MENU_7("Al Kharid", "", AbilityCategory.MENU, "City"),
    MENU_8("Al Kharid", "", AbilityCategory.MENU, "City"),
    MENU_9("Al Kharid", "", AbilityCategory.MENU, "City"),
    MENU_10("Al Kharid", "", AbilityCategory.MENU, "City"),
    MENU_11("Al Kharid", "", AbilityCategory.MENU, "City"),
    MENU_12("Al Kharid", "", AbilityCategory.MENU, "City"),

    TRAINING_1("Clued In", "", AbilityCategory.TRAINING, "Training"),
    TRAINING_2("Barbarian Agility", "", AbilityCategory.TRAINING, "Training"),
    TRAINING_3("Barbarian Agility", "", AbilityCategory.TRAINING, "Training"),
    TRAINING_4("Barbarian Agility", "", AbilityCategory.TRAINING, "Training"),
    TRAINING_5("Barbarian Agility", "", AbilityCategory.TRAINING, "Training"),
    TRAINING_6("Barbarian Agility", "", AbilityCategory.TRAINING, "Training"),
    TRAINING_7("Barbarian Agility", "", AbilityCategory.TRAINING, "Training"),
    TRAINING_8("Barbarian Agility", "", AbilityCategory.TRAINING, "Training"),
    TRAINING_9("Barbarian Agility", "", AbilityCategory.TRAINING, "Training"),
    TRAINING_10("Barbarian Agility", "", AbilityCategory.TRAINING, "Training"),
    TRAINING_11("Barbarian Agility", "", AbilityCategory.TRAINING, "Training"),
    TRAINING_12("Barbarian Agility", "", AbilityCategory.TRAINING, "Training"),

    DUNGEONS_1("Ancient Cavern", "", AbilityCategory.DUNGEONS, "Dungeons"),
    DUNGEONS_2("Ancient Cavern", "", AbilityCategory.DUNGEONS, "Dungeons"),
    DUNGEONS_3("Ancient Cavern", "", AbilityCategory.DUNGEONS, "Dungeons"),
    DUNGEONS_4("Ancient Cavern", "", AbilityCategory.DUNGEONS, "Dungeons"),
    DUNGEONS_5("Ancient Cavern", "", AbilityCategory.DUNGEONS, "Dungeons"),
    DUNGEONS_6("Ancient Cavern", "", AbilityCategory.DUNGEONS, "Dungeons"),
    DUNGEONS_7("Ancient Cavern", "", AbilityCategory.DUNGEONS, "Dungeons"),
    DUNGEONS_8("Ancient Cavern", "", AbilityCategory.DUNGEONS, "Dungeons"),
    DUNGEONS_9("Ancient Cavern", "", AbilityCategory.DUNGEONS, "Dungeons"),
    DUNGEONS_10("Ancient Cavern", "", AbilityCategory.DUNGEONS, "Dungeons"),
    DUNGEONS_11("Ancient Cavern", "", AbilityCategory.DUNGEONS, "Dungeons"),
    DUNGEONS_12("Ancient Cavern", "", AbilityCategory.DUNGEONS, "Dungeons"),

    BOSSES_1("Abyssal Sire", "", AbilityCategory.BOSSES, "Bosses"),
    BOSSES_2("Abyssal Sire", "", AbilityCategory.BOSSES, "Bosses"),
    BOSSES_3("Abyssal Sire", "", AbilityCategory.BOSSES, "Bosses"),
    BOSSES_4("Abyssal Sire", "", AbilityCategory.BOSSES, "Bosses"),
    BOSSES_5("Abyssal Sire", "", AbilityCategory.BOSSES, "Bosses"),
    BOSSES_6("Abyssal Sire", "", AbilityCategory.BOSSES, "Bosses"),
    BOSSES_7("Abyssal Sire", "", AbilityCategory.BOSSES, "Bosses"),
    BOSSES_8("Abyssal Sire", "", AbilityCategory.BOSSES, "Bosses"),
    BOSSES_9("Abyssal Sire", "", AbilityCategory.BOSSES, "Bosses"),
    BOSSES_10("Abyssal Sire", "", AbilityCategory.BOSSES, "Bosses"),
    BOSSES_11("Abyssal Sire", "", AbilityCategory.BOSSES, "Bosses"),
    BOSSES_12("Abyssal Sire", "", AbilityCategory.BOSSES, "Bosses"),

    MINIGAMES_1("Barrows", "", AbilityCategory.MINIGAMES, "Minigames"),
    MINIGAMES_2("Barrows", "", AbilityCategory.MINIGAMES, "Minigames"),
    MINIGAMES_3("Barrows", "", AbilityCategory.MINIGAMES, "Minigames"),
    MINIGAMES_4("Barrows", "", AbilityCategory.MINIGAMES, "Minigames"),
    MINIGAMES_5("Barrows", "", AbilityCategory.MINIGAMES, "Minigames"),
    MINIGAMES_6("Barrows", "", AbilityCategory.MINIGAMES, "Minigames"),
    MINIGAMES_7("Barrows", "", AbilityCategory.MINIGAMES, "Minigames"),
    MINIGAMES_8("Barrows", "", AbilityCategory.MINIGAMES, "Minigames"),
    MINIGAMES_9("Barrows", "", AbilityCategory.MINIGAMES, "Minigames"),
    MINIGAMES_10("Barrows", "", AbilityCategory.MINIGAMES, "Minigames"),
    MINIGAMES_11("Barrows", "", AbilityCategory.MINIGAMES, "Minigames"),
    MINIGAMES_12("Barrows", "", AbilityCategory.MINIGAMES, "Minigames"),

    WILDERNESS_1("Level 13", "", AbilityCategory.WILDERNESS, "Wilderness"),
    WILDERNESS_2("Level 13", "", AbilityCategory.WILDERNESS, "Wilderness"),
    WILDERNESS_3("Level 13", "", AbilityCategory.WILDERNESS, "Wilderness"),
    WILDERNESS_4("Level 13", "", AbilityCategory.WILDERNESS, "Wilderness"),
    WILDERNESS_5("Level 13", "", AbilityCategory.WILDERNESS, "Wilderness"),
    WILDERNESS_6("Level 13", "", AbilityCategory.WILDERNESS, "Wilderness"),
    WILDERNESS_7("Level 13", "", AbilityCategory.WILDERNESS, "Wilderness"),
    WILDERNESS_8("Level 13", "", AbilityCategory.WILDERNESS, "Wilderness"),
    WILDERNESS_9("Level 13", "", AbilityCategory.WILDERNESS, "Wilderness"),
    WILDERNESS_10("Level 13", "", AbilityCategory.WILDERNESS, "Wilderness"),
    WILDERNESS_11("Level 13", "", AbilityCategory.WILDERNESS, "Wilderness"),
    WILDERNESS_12("Level 13", "", AbilityCategory.WILDERNESS, "Wilderness"),
    ;


    public final static AbilityData[] values = AbilityData.values();

    private final String name;
    private final String nameTwo;
    private final AbilityCategory category;
    private final String description;

    AbilityData(String name, String nameTwo, AbilityCategory category, String description) {
        this.name = name;
        this.nameTwo = nameTwo;
        this.category = category;
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public String getNameTwo() {
        return nameTwo;
    }

    public AbilityCategory getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }
}