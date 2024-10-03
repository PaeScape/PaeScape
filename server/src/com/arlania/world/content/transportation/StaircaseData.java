package com.arlania.world.content.transportation;

import com.arlania.model.Position;
import com.arlania.world.content.teleport.TeleportData;
import com.arlania.world.entity.impl.player.Player;

import java.util.Objects;

public enum StaircaseData {

    LUMBY_STAIRCASE_S_1(new Position(3204, 3207, 0), 316671, new Position(3205, 3209, 1), true),
    LUMBY_STAIRCASE_S_2_UP(new Position(3204, 3207, 1), 316671, new Position(3205, 3209, 2), true),
    LUMBY_STAIRCASE_S_2_DOWN(new Position(3204, 3207, 1), 316671, new Position(3205, 3209, 0), false),
    LUMBY_STAIRCASE_S_3(new Position(3205, 3208, 2), 316671, new Position(3205, 3209, 1), false),
    LUMBY_STAIRCASE_N_1(new Position(3204, 3229, 0), 316671, new Position(3206, 3229, 1), true),
    LUMBY_STAIRCASE_N_2_UP(new Position(3204, 3229, 1), 316672, new Position(3206, 3229, 2), true),
    LUMBY_STAIRCASE_N_2_DOWN(new Position(3204, 3229, 1), 316672, new Position(3206, 3229, 0), false),
    LUMBY_STAIRCASE_N_3(new Position(3205, 3229, 2), 316673, new Position(3206, 3229, 1), false),
    WIZZY_TOWER_STAIRS_1(new Position(3103, 3159, 0), 312536, new Position(3105, 3160, 2), true),
    WIZZY_TOWER_STAIRS_3(new Position(3104, 3160, 2), 312536, new Position(3104, 3161, 0), true),

    ;


    StaircaseData(Position startPosition, int object, Position endPosition, boolean goingUP) {
        this.startPosition = startPosition;
        this.object = object;
        this.endPosition = endPosition;
        this.goingUP = goingUP;
    }

    private final Position startPosition;
    private final int object;
    private final Position endPosition;
    private final boolean goingUP;

    public int getObject() {
        return object;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public Position getEndPosition() {
        return endPosition;
    }

    public boolean getGoingUP() { return goingUP;}

    public static void climb(final Player player, final Position endPosition) {
        TeleportHandler.teleportPlayer(player, endPosition, TeleportType.CLIMB);
    }

    public static StaircaseData forPosition(Position startPosition, boolean goingUP, boolean choice) {

        for (StaircaseData staircaseData : StaircaseData.values()) {
            if (Objects.equals(staircaseData.getStartPosition(), startPosition))
                if ((choice && goingUP == staircaseData.goingUP) || !choice)
                    return staircaseData;
        }

        return null;
    }
}