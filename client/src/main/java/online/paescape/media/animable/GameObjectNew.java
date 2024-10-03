package online.paescape.media.animable;

import online.paescape.cache.media.Position;

public class GameObjectNew {


    public int id;
    public Position spawnPosition;
    public int face;

    public GameObjectNew(int id, Position spawnPosition, int face) {
        this.id = id;
        this.spawnPosition = spawnPosition;
        this.face = face;
    }

}
