package online.paescape.cache.media;

import online.paescape.media.animable.GameObject;
import online.paescape.media.animable.GameObjectNew;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Gabriel Hannason
 */
public enum CustomObjectsNew {

    //Courtyard
    NEXUS1(333355, new Position(3221, 3210, 0), 0),
    NEXUS2(333355, new Position(3221, 3226, 0), 0),
    RFD_CHEST(2182, new Position(3209, 3216, 0), 0),


    //Swamp
    FAIRY_RING_SHED(311761, new Position(3203, 3168, 0), 0),



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
    CustomObjectsNew(int objectId, Position spawnPosition, int face) {
        this.objectId = objectId;
        this.spawnPosition = spawnPosition;
        this.face = face;
    }

    public static List<GameObjectNew> CUSTOM_OBJECT_LIST_NEW = new ArrayList<GameObjectNew>();
    public static void init() {
        for (CustomObjectsNew customObjectsNew : CustomObjectsNew.values()) {
            int id = customObjectsNew.getObjectId();
            Position spawnPosition = customObjectsNew.getSpawnPosition();
            int face = customObjectsNew.getFace();
            CUSTOM_OBJECT_LIST_NEW.add(new GameObjectNew(id, spawnPosition, face));
        }
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