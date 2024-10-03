package com.arlania.world.content.skill.impl.construction;

import com.arlania.world.content.skill.impl.construction.ConstructionData.HouseTheme;
import com.arlania.world.content.skill.impl.construction.ConstructionData.RoomData;

/**
 * @author Owner Blade
 */
public class Room implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8607150709202743351L;
    private int rotation;
    private final int type;
    private HouseTheme theme;
    private int x;
    private int y;
    private final int z;
    private boolean[] doors;

    public Room(int rotation, int type, HouseTheme houseTheme, int k) {
        this.rotation = rotation;
        this.type = type;
        this.theme = houseTheme;
        this.z = theme.getHeight();
        getVarData();
    }

    public Room(int rotation, int type, int z) {
        this.rotation = rotation;
        this.type = type;
        this.z = z;
        getVarData();
    }

    private void getVarData() {
        RoomData rd = ConstructionData.RoomData.forID(type);
        x = rd.getX();
        y = rd.getY();
        doors = rd.getRotatedDoors(rotation);
    }

    public boolean[] getDoors() {
        return doors;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public void setTheme(HouseTheme theme) {
        this.theme = theme;
    }

    public int getType() {
        return type;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
}