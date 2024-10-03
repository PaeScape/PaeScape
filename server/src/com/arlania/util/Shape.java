package com.arlania.util;

import com.arlania.model.Position;
import com.arlania.world.World;
import com.arlania.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class Shape {

    private Position[] areas;
    private ShapeType type;

    public abstract boolean inside(Position Position);

    public Position[] areas() {
        return areas;
    }

    public Shape areas(Position[] areas) {
        this.areas = areas;
        return this;
    }

    public ShapeType type() {
        return type;
    }

    public Shape type(ShapeType type) {
        this.type = type;
        return this;
    }

    public enum ShapeType {
        RECTENGLE,
        POLYGON
    }

    public int getNumberOfPlayers() {
        int count = 0;
        for (Player player : World.getPlayers()) {
            if (inside(player.getPosition())) {
                count++;
            }
        }
        return count;
    }

    public List<Player> getPlayers() {
        List<Player> count = new ArrayList<>();
        for (Player player : World.getPlayers()) {
            if (inside(player.getPosition())) {
                count.add(player);
            }
        }
        return count;
    }

}
