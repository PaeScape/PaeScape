package com.arlania.world.regionalspawns.misthalin;

import com.arlania.model.GameObject;
import com.arlania.model.Position;
import com.arlania.world.World;
import com.arlania.world.clip.region.RegionClipping;
import com.arlania.world.content.CustomObjects;

public enum MisthalinObjectData {

    //Courtyard
    NEXUS1(333355, new Position(3221, 3210, 0), 0),
    NEXUS2(333355, new Position(3221, 3226, 0), 0),
    RFD_CHEST(2182, new Position(3209, 3216, 0), 0),


    //{312897, 3095, 3502, 0, 0}, //Well of Events
    //{409, 1820, 3788, 0, 0}, //altar
    //{411, 1827, 3788, 0, 0}, //Curse altar
    //{6552, 1820, 3784, 0, 0}, //Ancient altar
    //{13179, 1827, 3784, 0, 0}, //Lunar altar
    //{13639, 1815, 3779, 0, 0}, //Resto Pool
    //{330914, 1833, 3768, 0, 1}, //DZ Boat
    //{2732, 1817, 3775, 0, 0}, //Fire

    ;


    private final int objectId;
    private final Position spawnPosition;
    private int face;
    MisthalinObjectData(int objectId, Position spawnPosition, int face) {
        this.objectId = objectId;
        this.spawnPosition = spawnPosition;
        this.face = face;
    }


    public static MisthalinObjectData load() {
        for (MisthalinObjectData misthalinObjectData : MisthalinObjectData.values()) {
            CustomObjects.spawnGlobalObject(new GameObject(misthalinObjectData.objectId, misthalinObjectData.spawnPosition));
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