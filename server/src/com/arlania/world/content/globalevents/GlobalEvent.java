package com.arlania.world.content.globalevents;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;


@Getter
public enum GlobalEvent {

    ACCURACY("Accuracy", 7020, false, true, Arrays.asList(Effect.ACCURACY)),
    ACCELERATE("Accelerate", 7021, false, false, Arrays.asList(Effect.ACCELERATE)),
    DROP_RATE("Drop Rate", 7022, false, false, Arrays.asList(Effect.DROP_RATE)),
    DOUBLE_BOSS_PP("2x Boss PP", 7023, false, false, Arrays.asList(Effect.DOUBLE_BOSS_PP)),
    DOUBLE_SKILLER_PP("2x Skiller PP", 7024, false, false, Arrays.asList(Effect.DOUBLE_SKILLER_PP)),
    DOUBLE_SLAYER_PP("2x Slayer PP", 7025, false, false, Arrays.asList(Effect.DOUBLE_SLAYER_PP)),
    LIFELINK("Lifelink", 7026, false, false, Arrays.asList(Effect.LIFELINK)),
    LOADED("Loaded", 7027, false, false, Arrays.asList(Effect.LOADED)),
    DOUBLE_LOOT("2x Loot", 7028, false, true, Arrays.asList(Effect.DOUBLE_LOOT)),
    DOUBLE_EXP("2x Experience", 7029, false, false,Arrays.asList(Effect.DOUBLE_EXP)),
    EVENT_BOX("Event Box", 7030, false, false, Arrays.asList(Effect.EVENT_BOX)),
    GLOBAL_BOSS_KILLS("Boss Kills", 7031, false, true, Arrays.asList(Effect.GLOBAL_BOSS_KILLS)),
    MAX_HIT("Max Hit", 7032, false, true, Arrays.asList(Effect.MAX_HIT)),
    EFFICIENCY("Efficiency", 7033, false, false, Arrays.asList(Effect.EFFICIENCY)),
    BERSERKER("Berserker", 7034, false, true, Arrays.asList(Effect.BERSERKER)),
    RAIDER("Raider", 7035, false, false, Arrays.asList(Effect.RAIDER)),
    DOUBLE_CLUES("2x Clues", -1, false, false, Arrays.asList(Effect.DOUBLE_CLUES)),
    SWEET_TOOTH("Sweet Tooth", -1, false, false, Arrays.asList(Effect.SWEET_TOOTH)),
    SPECIALIZED("Specialized", -1, false, false, Arrays.asList(Effect.SPECIALIZED)),
    BARROWS_BASH("Barrows Bash", -1, false, false, Arrays.asList(Effect.BARROWS_BASH)),
    GODS_GIFTS("Gods Gifts", -1, false, false, Arrays.asList(Effect.GODS_GIFTS)),
    RAPID("Rapid", -1, false, false, Arrays.asList(Effect.RAPID)),
    //GOLIATH("Goliath", -1, false, false, Arrays.asList(Effects.GOLIATH)),
    WARRIOR("Warrior", -1, false, false, Arrays.asList(Effect.WARRIOR)),
    MARKSMAN("Marksman", -1, false, false, Arrays.asList(Effect.MARKSMAN)),
    SORCERER("Sorcerer", -1, false, false, Arrays.asList(Effect.SORCERER)),
    //ENERGIZE("Energize", -1, false, false, Arrays.asList(Effects.ENERGIZE)),
    //JUSTICIAR("Justiciar", -1, false, false, Arrays.asList(Effects.JUSTICIAR)),
    COLLATERAL("Collateral", -1, false, false, Arrays.asList(Effect.COLLATERAL)),
    COMPANION("Companion", -1, false, false, Arrays.asList(Effect.COMPANION)),
    //GLADIATOR("Gladiator", -1, false, false, Arrays.asList(Effect.GLADIATOR)),
    //EXECUTIONER("Executioner", -1, false, false, Arrays.asList(Effect.EXECUTIONER)),


    //combined
    LOCKED_AND_LOADED("Locked and Loaded", 7040, true, false, Arrays.asList(Effect.ACCURACY, Effect.LOADED)),
    BLUE_COLLAR("Blue Collar", 7041, true, false, Arrays.asList(Effect.ACCELERATE, Effect.DOUBLE_EXP)),
    ADVENTURER("Adventurer", 7042, true, false, Arrays.asList(Effect.DOUBLE_SLAYER_PP, Effect.DOUBLE_SKILLER_PP)),
    SLAUGHTER("Slaughter", 7043, true, false, Arrays.asList(Effect.DOUBLE_BOSS_PP, Effect.EVENT_BOX)),
    RAGS_TO_RICHES("Rags to Riches", 7044, true, true, Arrays.asList(Effect.DOUBLE_LOOT, Effect.DROP_RATE)),
    ARMED_AND_DANGEROUS("Armed and Dangerous", 7045, true, true, Arrays.asList(Effect.ACCURACY, Effect.BERSERKER)),
    FLOURISH("Flourish", 7046, true, false, Arrays.asList(Effect.ACCELERATE, Effect.DOUBLE_SKILLER_PP)),
    FLESH_AND_BLOOD("Flesh and Blood", 7047, true, false, Arrays.asList(Effect.DOUBLE_SLAYER_PP, Effect.DOUBLE_BOSS_PP)),
    LOADED_RAIDS("Loaded Raids", 7048, true, false, Arrays.asList(Effect.LOADED, Effect.RAIDER)),
    ALIVE_AND_WELL("Alive and Well", 7049, true, false, Arrays.asList(Effect.LIFELINK, Effect.LOADED));

    GlobalEvent(String eventName, int globalID, boolean combinedGlobal, boolean special, List<Effect> effects) {
        this.eventName = eventName;
        this.globalID = globalID;
        this.combinedGlobal = combinedGlobal;
        this.special = special;
        this.effects = effects;
    }

    private final String eventName;
    private final int globalID;
    private final boolean combinedGlobal;
    private final boolean special;
    private final List<Effect> effects;

    public static GlobalEvent forId(int id) {
        for (GlobalEvent events : GlobalEvent.values()) {
            if (events.globalID == id) {
                return events;
            }
        }
        return null;
    }

    @Getter
    public enum Effect {

        ACCURACY("Accuracy", "Perfect accuracy on all attacks."),
        ACCELERATE("Accelerate", "+2 to resource gathering and processing."),
        DROP_RATE("Drop Rate", "10 percent boost to drop rate."),
        DOUBLE_BOSS_PP("2x Boss PP", "2x PaePoints from boss kills."),
        DOUBLE_SKILLER_PP("2x Skiller PP", "2x PaePoints from completing Skiller tasks."),
        DOUBLE_SLAYER_PP("2x Slayer PP", "2x PaePoints from completing Slayer tasks."),
        LIFELINK("Lifelink", "Players heal 10 percent of damage dealt."),
        LOADED("Loaded", "Overload ability is boosted and all players gain access to Divine Pools."),
        DOUBLE_LOOT("2x Loot", "All loot from NPCs are doubled."),
        DOUBLE_EXP("2x Experience", "2x all Experience gained."),
        EVENT_BOX("Event Box", "1:125 chance for any NPC to drop an Event Box."),
        GLOBAL_BOSS_KILLS("Boss Kills", "Event box given to each player when 100 bosses are killed."),
        MAX_HIT("Max Hit", "Players will always hit their Max Hit."),
        EFFICIENCY("Efficiency", "Players minimum hit increased by 50 percent."),
        BERSERKER("Berserker", "Players Max Hit increased by 50 percent."),
        RAIDER("Raider", "Players gain extra raid key 50 percent of the time."),
        DOUBLE_CLUES("2x Clues", "Double the amount of clues drop from PvM and Skilling."),
        SWEET_TOOTH("Sweet Tooth", "1 in 50 chance to receive a Rare Candy from a Boss."),
        SPECIALIZED("Specialized", "Special attacks only drain 10 percent."),
        BARROWS_BASH("Barrows Bash", "25 percent boost to all Barrows equipment."),
        GODS_GIFTS("Gods Gifts", "25 percent boost to all GWD equipment."),
        RAPID("Rapid", "All attacks are 2-tick speed."),
        //GOLIATH("Goliath", "All attacks get 30% boosted max hit for each tick of Weapon Speed slower than 2 ticks."),
        WARRIOR("Warrior", "Double Melee max hit."),
        MARKSMAN("Marksman", "Double Ranged max hit."),
        SORCERER("Sorcerer", "Double Magic max hit."),
        //ENERGIZE("Energize", "All player damage is typeless damage. (Highest calculated max hit of Melee/Ranged/Magic is used)"),
        //JUSTICIAR("Justiciar", "Defense bonus used to assign damage instead of Strength bonus."),
        COLLATERAL("Collateral", "All attack styles gain AoE Damage."),
        COMPANION("Companion", "Summoning Familiars have higher max hit."),
        GLADIATOR("Gladiator", "Players always hit their Max when NPC has full HP."),
        EXECUTIONER("Executioner", "Player always hit their Max when an NPC's HP is at or below that amount.");

        Effect(String effectName, String effectDescription) {
            this.effectName = effectName;
            this.effectDescription = effectDescription;
        }

        private final String effectName;
        private final String effectDescription;
    }

}

