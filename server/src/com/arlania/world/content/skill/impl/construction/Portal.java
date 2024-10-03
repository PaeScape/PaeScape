package com.arlania.world.content.skill.impl.construction;

/**
 * @author Owner Blade
 */
public class Portal implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1502720931401016768L;
    private int id, roomX, roomY, roomZ;
    private int type;

    public int getRoomZ() {
        return roomZ;
    }

    public void setRoomZ(int roomZ) {
        this.roomZ = roomZ;
    }

    public int getRoomY() {
        return roomY;
    }

    public void setRoomY(int roomY) {
        this.roomY = roomY;
    }

    public int getRoomX() {
        return roomX;
    }

    public void setRoomX(int roomX) {
        this.roomX = roomX;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
