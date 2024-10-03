package com.arlania.world.content.skill.impl.construction;

import com.arlania.world.entity.impl.player.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class ConstructionSave implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -505532834397483631L;

    private Room[][][] houseRooms;

    private ArrayList<HouseFurniture> houseFurniture;

    private ArrayList<Portal> housePortals;

    public void supply(Player player) {
        houseRooms = player.getHouseRooms();
        houseFurniture = player.getHouseFurniture();
        housePortals = player.getHousePortals();
    }

    public Room[][][] getHouseRooms() {
        return houseRooms;
    }

    public void setHouseRooms(Room[][][] houseRooms) {
        this.houseRooms = houseRooms;
    }

    public ArrayList<HouseFurniture> getHouseFurniture() {
        return houseFurniture;
    }

    public void setHouseFurniture(ArrayList<HouseFurniture> houseFurniture) {
        this.houseFurniture = houseFurniture;
    }

    public ArrayList<Portal> getHousePortals() {
        return housePortals;
    }

    public void setHousePortals(ArrayList<Portal> housePortals) {
        this.housePortals = housePortals;
    }

}
