package com.arlania.world.content.abilities;

import java.util.HashMap;
import java.util.Map;

public enum AbilityCategory {
    MENU(0),
    TRAINING(1),
    DUNGEONS(2),
    BOSSES(3),
    MINIGAMES(4),
    WILDERNESS(5),
    ;

    private static final Map<Integer, AbilityCategory> map = new HashMap<>();

    static {
        for (AbilityCategory category : values()) {
            map.put(category.index, category);
        }
    }

    final int index;

    AbilityCategory(int index) {
        this.index = index;
    }

    public static AbilityCategory forIndex(int index) {
        return map.get(index);
    }
}
