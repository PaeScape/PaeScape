package com.arlania.world.content.teleport;

import java.util.HashMap;
import java.util.Map;

public enum TeleportCategory {
    CITIES(0),
    TRAINING(1),
    DUNGEONS(2),
    BOSSES(3),
    MINIGAMES(4),
    RAIDS(5),
    ;

    private static final Map<Integer, TeleportCategory> map = new HashMap<>();

    static {
        for (TeleportCategory category : values()) {
            map.put(category.index, category);
        }
    }

    final int index;

    TeleportCategory(int index) {
        this.index = index;
    }

    public static TeleportCategory forIndex(int index) {
        return map.get(index);
    }
}
