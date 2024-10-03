package com.arlania.world.content.skill.impl.prayer;

public enum BonesData {
    BONES(526, 10),
    BAT_BONES(530, 20),
    WOLF_BONES(2859, 20),
    BIG_BONES(532, 40),
    FEMUR_BONES(15182, 50),
    BABYDRAGON_BONES(534, 100),
    JOGRE_BONE(3125, 100),
    ZOGRE_BONES(4812, 100),
    LONG_BONES(10976, 150),
    CURVED_BONE(10977, 150),
    SHAIKAHAN_BONES(3123, 150),
    DRAGON_BONES(536, 200),
    FAYRG_BONES(4830, 200),
    RAURG_BONES(4832, 200),
    WYVERN_BONES(6812, 200),
    DAGANNOTH_BONES(6729, 250),
    OURG_BONES(14793, 250),
    FROST_DRAGON_BONES(18830, 300),
    SUPERIOR_DRAGON_BONES(222124, 500),

    NOTED_BONES(527, 10),
    NOTED_BAT_BONES(531, 20),
    NOTED_WOLF_BONES(2860, 20),
    NOTED_BIG_BONES(533, 40),
    NOTED_BABYDRAGON_BONES(535, 100),
    NOTED_JOGRE_BONE(3126, 100),
    NOTED_ZOGRE_BONES(4813, 100),
    NOTED_SHAIKAHAN_BONES(3121, 150),
    NOTED_DRAGON_BONES(537, 200),
    NOTED_FAYRG_BONES(4831, 200),
    NOTED_RAURG_BONES(4833, 200),
    NOTED_WYVERN_BONES(6813, 200),
    NOTED_DAGANNOTH_BONES(6730, 250),
    NOTED_OURG_BONES(14794, 250),
    NOTED_FROST_DRAGON_BONES(18831, 300);

    BonesData(int boneId, int buryXP) {
        this.boneId = boneId;
        this.buryXP = buryXP;
    }

    private final int boneId;
    private final int buryXP;

    public int getBoneID() {
        return this.boneId;
    }

    public int getBuryingXP() {
        return this.buryXP;
    }

    public static BonesData forId(int bone) {
        for (BonesData prayerData : BonesData.values()) {
            if (prayerData.getBoneID() == bone) {
                return prayerData;
            }
        }
        return null;
    }

}
