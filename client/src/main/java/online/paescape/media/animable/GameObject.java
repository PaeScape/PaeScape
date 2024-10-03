package online.paescape.media.animable;

public class GameObject {


    public int id;
    public int x, y, z;
    public int face;

    public GameObject(int id, int x, int y, int z, int face) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.face = face;
    }
}
