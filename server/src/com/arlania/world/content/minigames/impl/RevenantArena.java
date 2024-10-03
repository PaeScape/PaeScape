package com.arlania.world.content.minigames.impl;

import com.arlania.model.Position;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.entity.impl.npc.NPC;

import java.util.ArrayList;

/**
 * @author Patrick McDonnell
 * Wrote this quickly!!
 * Handles the Revenant Arena
 */
public class RevenantArena {

    private static Position[] positions = {
            new Position(3098, 3943, 0),
            new Position(3102, 3943, 0),
            new Position(3100, 3941, 0),
            new Position(3098, 3939, 0),
            new Position(3102, 3939, 0),

            new Position(3109, 3943, 0),
            new Position(3113, 3943, 0),
            new Position(3111, 3941, 0),
            new Position(3109, 3939, 0),
            new Position(3113, 3939, 0),

            new Position(3103, 3935, 0),
            new Position(3107, 3935, 0),
            new Position(3105, 3933, 0),
            new Position(3103, 3931, 0),
            new Position(3107, 3931, 0),

            new Position(3098, 3928, 0),
            new Position(3102, 3928, 0),
            new Position(3100, 3926, 0),
            new Position(3098, 3924, 0),
            new Position(3102, 3924, 0),

            new Position(3109, 3928, 0),
            new Position(3113, 3928, 0),
            new Position(3111, 3926, 0),
            new Position(3109, 3924, 0),
            new Position(3113, 3924, 0),
    };

    private static int[] revenants = {
            6691, 6691, 6691, 6691, 6691,
            6725, 6725, 6725, 6725, 6725,
            6701, 6701, 6701, 6701, 6701,
            6716, 6716, 6716, 6716, 6716,
            6715, 6715, 6715, 6715, 6715
    };

    public static void start() {
        for (int i = 0; i < 25; i++)
            spawn(i);

    }

    public static void spawn(int type) {
        NPC spawn = NPC.of(revenants[type], positions[type]);
        World.register(spawn);
    }
}
