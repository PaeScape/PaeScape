package com.arlania.world.content.skill.impl.fletching;

public enum StringingData {

    SHORT_BOW(50, 841, 5, 5, 6678),
    LONG_BOW(48, 839, 10, 10, 6684),

    OAK_SHORT_BOW(54, 843, 20, 17, 6679),
    OAK_LONG_BOW(56, 845, 25, 25, 6685),

    WILLOW_SHORT_BOW(60, 849, 35, 33, 6680),
    WILLOW_LONG_BOW(58, 847, 40, 42, 6686),

    MAPLE_SHORT_BOW(64, 853, 50, 50, 6681),
    MAPLE_LONG_BOW(62, 851, 55, 58, 6687),

    YEW_SHORT_BOW(68, 857, 65, 68, 6682),
    YEW_LONG_BOW(66, 855, 70, 75, 6688),

    MAGIC_SHORT_BOW(72, 861, 80, 83, 6683),
    MAGIC_LONG_BOW(70, 859, 85, 92, 6689),

    NOTED_SHORT_BOW(51, 842, 5, 5, 6678),
    NOTED_LONG_BOW(49, 840, 10, 10, 6684),

    NOTED_OAK_SHORT_BOW(55, 844, 20, 17, 6679),
    NOTED_OAK_LONG_BOW(57, 846, 25, 25, 6685),

    NOTED_WILLOW_SHORT_BOW(61, 850, 35, 33, 6680),
    NOTED_WILLOW_LONG_BOW(59, 848, 40, 42, 6686),

    NOTED_MAPLE_SHORT_BOW(65, 854, 50, 50, 6681),
    NOTED_MAPLE_LONG_BOW(63, 852, 55, 58, 6687),

    NOTED_YEW_SHORT_BOW(69, 858, 65, 68, 6682),
    NOTED_YEW_LONG_BOW(67, 856, 70, 75, 6688),

    NOTED_MAGIC_SHORT_BOW(73, 862, 80, 83, 6683),
    NOTED_MAGIC_LONG_BOW(71, 860, 85, 92, 6689);


    private final int unstrung;
    private final int strung;
    private final int level;
    private final int animation;
    private final double xp;

    StringingData(final int unstrung, final int strung, final int level, final double xp, final int animation) {
        this.unstrung = unstrung;
        this.strung = strung;
        this.level = level;
        this.xp = xp;
        this.animation = animation;
    }

    public int unStrung() {
        return unstrung;
    }

    public int Strung() {
        return strung;
    }

    public int getLevel() {
        return level;
    }

    public double getXP() {
        return xp;
    }

    public int getAnimation() {
        return animation;
    }
}
