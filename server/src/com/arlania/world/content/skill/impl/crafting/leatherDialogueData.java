package com.arlania.world.content.skill.impl.crafting;

public enum leatherDialogueData {

    GREEN_LEATHER(1745, 1065, 1099, 1135),
    BLUE_LEATHER(2505, 2487, 2493, 2499),
    RED_LEATHER(2507, 2489, 2495, 2501),
    BLACK_LEATHER(2509, 2491, 2497, 2503),
    NOTED_GREEN_LEATHER(1746, 1066, 1100, 1136),
    NOTED_BLUE_LEATHER(2506, 2488, 2494, 2500),
    NOTED_RED_LEATHER(2508, 2490, 2496, 2502),
    NOTED_BLACK_LEATHER(2510, 2492, 2498, 2504);

    private final int leather;
    private final int vambraces;
    private final int chaps;
    private final int body;

    leatherDialogueData(final int leather, final int vambraces, final int chaps, final int body) {
        this.leather = leather;
        this.vambraces = vambraces;
        this.chaps = chaps;
        this.body = body;
    }

    public int getLeather() {
        return leather;
    }

    public int getVamb() {
        return vambraces;
    }

    public int getChaps() {
        return chaps;
    }

    public int getBody() {
        return body;
    }
}
