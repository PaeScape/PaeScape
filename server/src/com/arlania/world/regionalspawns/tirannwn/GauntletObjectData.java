package com.arlania.world.regionalspawns.tirannwn;

import com.arlania.model.GameObject;
import com.arlania.model.Position;
import com.arlania.world.content.CustomObjects;

public enum GauntletObjectData {

    //1873
    //1886
    //5649
    //5662
    GAUNTLET_BORDER1(311378, new Position(1873, 5649, 0), 0),
    GAUNTLET_BORDER2(311378, new Position(1873, 5650, 0), 0),
    GAUNTLET_BORDER3(311378, new Position(1873, 5651, 0), 0),
    GAUNTLET_BORDER4(311378, new Position(1873, 5652, 0), 0),
    GAUNTLET_BORDER5(311378, new Position(1873, 5653, 0), 0),
    GAUNTLET_BORDER6(311378, new Position(1873, 5654, 0), 0),
    GAUNTLET_BORDER7(311378, new Position(1873, 5655, 0), 0),
    GAUNTLET_BORDER8(311378, new Position(1873, 5656, 0), 0),
    GAUNTLET_BORDER9(311378, new Position(1873, 5657, 0), 0),
    GAUNTLET_BORDER10(311378, new Position(1873, 5658, 0), 0),
    GAUNTLET_BORDER11(311378, new Position(1873, 5659, 0), 0),
    GAUNTLET_BORDER12(311378, new Position(1873, 5660, 0), 0),
    GAUNTLET_BORDER13(311378, new Position(1873, 5661, 0), 0),
    GAUNTLET_BORDER14(311378, new Position(1873, 5662, 0), 0),
    GAUNTLET_BORDER15(311378, new Position(1886, 5649, 0), 0),
    GAUNTLET_BORDER16(311378, new Position(1886, 5650, 0), 0),
    GAUNTLET_BORDER17(311378, new Position(1886, 5651, 0), 0),
    GAUNTLET_BORDER18(311378, new Position(1886, 5652, 0), 0),
    GAUNTLET_BORDER19(311378, new Position(1886, 5653, 0), 0),
    GAUNTLET_BORDER20(311378, new Position(1886, 5654, 0), 0),
    GAUNTLET_BORDER21(311378, new Position(1886, 5655, 0), 0),
    GAUNTLET_BORDER22(311378, new Position(1886, 5656, 0), 0),
    GAUNTLET_BORDER23(311378, new Position(1886, 5657, 0), 0),
    GAUNTLET_BORDER24(311378, new Position(1886, 5658, 0), 0),
    GAUNTLET_BORDER25(311378, new Position(1886, 5659, 0), 0),
    GAUNTLET_BORDER26(311378, new Position(1886, 5660, 0), 0),
    GAUNTLET_BORDER27(311378, new Position(1886, 5661, 0), 0),
    GAUNTLET_BORDER28(311378, new Position(1886, 5662, 0), 0),
    GAUNTLET_BORDER29(311378, new Position(1873, 5649, 0), 0),
    GAUNTLET_BORDER30(311378, new Position(1874, 5649, 0), 0),
    GAUNTLET_BORDER31(311378, new Position(1875, 5649, 0), 0),
    GAUNTLET_BORDER32(311378, new Position(1876, 5649, 0), 0),
    GAUNTLET_BORDER33(311378, new Position(1877, 5649, 0), 0),
    GAUNTLET_BORDER34(311378, new Position(1878, 5649, 0), 0),
    GAUNTLET_BORDER35(311378, new Position(1879, 5649, 0), 0),
    GAUNTLET_BORDER36(311378, new Position(1880, 5649, 0), 0),
    GAUNTLET_BORDER37(311378, new Position(1881, 5649, 0), 0),
    GAUNTLET_BORDER38(311378, new Position(1882, 5649, 0), 0),
    GAUNTLET_BORDER39(311378, new Position(1883, 5649, 0), 0),
    GAUNTLET_BORDER40(311378, new Position(1884, 5649, 0), 0),
    GAUNTLET_BORDER41(311378, new Position(1885, 5649, 0), 0),
    GAUNTLET_BORDER42(311378, new Position(1886, 5649, 0), 0),
    GAUNTLET_BORDER43(311378, new Position(1873, 5662, 0), 0),
    GAUNTLET_BORDER44(311378, new Position(1874, 5662, 0), 0),
    GAUNTLET_BORDER45(311378, new Position(1875, 5662, 0), 0),
    GAUNTLET_BORDER46(311378, new Position(1876, 5662, 0), 0),
    GAUNTLET_BORDER47(311378, new Position(1877, 5662, 0), 0),
    GAUNTLET_BORDER48(311378, new Position(1878, 5662, 0), 0),
    GAUNTLET_BORDER49(311378, new Position(1879, 5662, 0), 0),
    GAUNTLET_BORDER50(311378, new Position(1880, 5662, 0), 0),
    GAUNTLET_BORDER51(311378, new Position(1881, 5662, 0), 0),
    GAUNTLET_BORDER52(311378, new Position(1882, 5662, 0), 0),
    GAUNTLET_BORDER53(311378, new Position(1883, 5662, 0), 0),
    GAUNTLET_BORDER54(311378, new Position(1884, 5662, 0), 0),
    GAUNTLET_BORDER55(311378, new Position(1885, 5662, 0), 0),
    GAUNTLET_BORDER56(311378, new Position(1886, 5662, 0), 0),

    ;


    private final int objectId;
    private final Position spawnPosition;
    private int face;
    GauntletObjectData(int objectId, Position spawnPosition, int face) {
        this.objectId = objectId;
        this.spawnPosition = spawnPosition;
        this.face = face;
    }


    public static GauntletObjectData load(int height) {
        for (GauntletObjectData tirannwnnObjectData : GauntletObjectData.values()) {
            CustomObjects.spawnGlobalObject(new GameObject(tirannwnnObjectData.objectId, new Position(tirannwnnObjectData.spawnPosition.getX(), tirannwnnObjectData.spawnPosition.getY(), height)));
        }


        return null;
    }

    public int getObjectId() {
        return objectId;
    }

    public Position getSpawnPosition() {
        return spawnPosition;
    }

    public int getFace() {
        return face;
    }
}