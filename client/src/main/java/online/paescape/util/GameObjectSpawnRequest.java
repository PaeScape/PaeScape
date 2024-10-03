package online.paescape.util;


import online.paescape.collection.Node;

public final class GameObjectSpawnRequest extends Node {

    public int currentIDRequested;
    public int currentFaceRequested;
    public int currentTypeRequested;
    public int removeTime;
    public int plane;
    public int objectType;
    public int tileX;
    public int tileY;
    public int objectId;
    public int face;
    public int type;
    public int spawnTime;

    public GameObjectSpawnRequest() {
        removeTime = -1;
    }
}
