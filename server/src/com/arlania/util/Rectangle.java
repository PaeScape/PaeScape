package com.arlania.util;

import com.arlania.model.Position;
import com.arlania.world.World;
import com.arlania.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Rectangle extends Shape {

    private final Position northEast;

    private final Position southWest;

    public Rectangle(Position northEast, Position southWest) {
        areas(new Position[]{northEast, southWest}).type(ShapeType.RECTENGLE);
        this.northEast = northEast;
        this.southWest = southWest;
    }

    @Override
    public boolean inside(Position Position) {

        if (areas()[0].getX() <= Position.getX() || areas()[1].getX() >= Position.getX())
            return false;

        return areas()[0].getY() > Position.getY() && areas()[1].getY() < Position.getY();
    }

    public List<Position> getBorder(Position Position) {
        List<Position> border = new ArrayList<>();
        if (areas()[0].getX() - 1 <= Position.getX() || areas()[1].getX() + 1 >= Position.getX())
            border.add(Position);

        if (areas()[0].getY() - 1 <= Position.getY() || areas()[1].getY() + 1 >= Position.getY())
            border.add(Position);
        return border;
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

    public Position getNorthEast() {
        return northEast;
    }

    public Position getSouthWest() {
        return southWest;
    }
}
