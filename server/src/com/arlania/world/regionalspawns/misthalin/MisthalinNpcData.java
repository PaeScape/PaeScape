package com.arlania.world.regionalspawns.misthalin;

import com.arlania.model.Position;
import com.arlania.world.World;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.npc.NPCMovementCoordinator;

public enum MisthalinNpcData {

    //GOBLIN(14677, new Position(3204, 3207, 0), 15),
    //COW(81, new Position(3204, 3207, 0), 10),


    SHOPKEEPER_LUMBY_1(521, new Position(3211, 3248, 0), true, 3),
    SMITHING_APPRENTICE_LUMBY_1(4904, new Position(3224, 3256, 0), true, 2),
    BARTENDER_LUMBY_1(731, new Position(3232, 3241, 0), true, 1),

    //Courtyard
    MAN_LUMBY_1(2, new Position(3221, 3215, 0), true, 4),
    MAN_LUMBY_2(2, new Position(3221, 3220, 0),true, 4),
    MAN_LUMBY_3(2, new Position(3223, 3217, 0),true, 4),
    MAN_LUMBY_4(2, new Position(3223, 3222, 0), true, 4),
    WOMAN_LUMBY_1(25, new Position(3235, 3211, 0), true, 4),
    WOMAN_LUMBY_2(25, new Position(3235, 3213, 0), true, 4),
    WOMAN_LUMBY_3(25, new Position(3237, 3221, 0), true, 4),
    WOMAN_LUMBY_4(25, new Position(3237, 3223, 0), true, 4),

    //North of Castle
    COW_LUMBY_1(81, new Position(3196, 3286, 0), true, 3),
    COW_LUMBY_2(81, new Position(3196, 3293, 0), true, 3),
    COW_LUMBY_3(81, new Position(3196, 3299, 0), true, 3),
    COW_LUMBY_4(81, new Position(3201, 3286, 0), true, 3),
    COW_LUMBY_5(81, new Position(3201, 3293, 0), true, 3),
    COW_LUMBY_6(81, new Position(3201, 3299, 0), true, 3),
    COW_LUMBY_7(81, new Position(3207, 3286, 0), true, 3),
    COW_LUMBY_8(81, new Position(3207, 3293, 0), true, 3),
    COW_LUMBY_9(81, new Position(3207, 3299, 0), true, 3),
    CHICKEN_LUMBY_1(41, new Position(3172, 3290, 0), true, 3),
    CHICKEN_LUMBY_2(41, new Position(3177, 3295, 0), true, 3),
    CHICKEN_LUMBY_3(41, new Position(3182, 3300, 0), true, 3),
    CHICKEN_LUMBY_4(41, new Position(3172, 3290, 0), true, 3),
    CHICKEN_LUMBY_5(41, new Position(3177, 3295, 0), true, 3),
    CHICKEN_LUMBY_6(41, new Position(3182, 3300, 0), true, 3),
    CHICKEN_LUMBY_7(41, new Position(3172, 3290, 0), true, 3),
    CHICKEN_LUMBY_8(41, new Position(3172, 3295, 0), true, 3),
    CHICKEN_LUMBY_9(41, new Position(3182, 3300, 0), true, 3),

    //Swamp
    GIANT_RAT_LUMBY_1(86, new Position(3225, 3162, 0), true, 4),
    GIANT_RAT_LUMBY_2(86, new Position(3220, 3162, 0), true, 4),
    GIANT_RAT_LUMBY_3(86, new Position(3232, 3168, 0), true, 4),
    GIANT_RAT_LUMBY_4(86, new Position(3219, 3175, 0), true, 4),
    GIANT_RAT_LUMBY_5(86, new Position(3231, 3189, 0), true, 4),


    //Fishing Spots
    SHRIMP_LUMBY_1(316, new Position(3240, 3146, 0), false, 4),
    SHRIMP_LUMBY_2(316, new Position(3240, 3147, 0), false, 4),
    SHRIMP_LUMBY_3(316, new Position(3244, 3150, 0), false, 4),
    TROUT_LUMBY_1(318, new Position(3240, 3241, 0), false, 4),
    TROUT_LUMBY_2(318, new Position(3240, 3244, 0), false, 4),



    //DRAYNOR
    MASTER_FARMER_DRAYNOR_1(2234, new Position(3078, 3251, 0), true, 3),


    //Fishing Spots
    SHRIMP_DRAYNOR_1(316, new Position(3085, 3230, 0), false, 4),
    SHRIMP_DRAYNOR_2(316, new Position(3086, 3227, 0), false, 4),

    ;


    private final int npcId;
    private final Position spawnPosition;
    private boolean movement;
    private int radius;
    MisthalinNpcData(int npcId, Position spawnPosition, boolean movement, int radius) {
        this.npcId = npcId;
        this.spawnPosition = spawnPosition;
        this.movement = movement;
        this.radius = radius;
    }

    public static void spawnNPC(final MisthalinNpcData misthalinNpcData) {

        final Position spawnPosition = misthalinNpcData.getSpawnPosition();
        final int npcId = misthalinNpcData.getNpcId();

        NPC npc = new NPC(npcId, spawnPosition);
        npc.getMovementCoordinator().setCoordinator(new NPCMovementCoordinator.Coordinator(misthalinNpcData.getMovement(), misthalinNpcData.getRadius()));
        World.register(npc);


    }

    public static MisthalinNpcData load() {
        for (MisthalinNpcData misthalinNpcData : MisthalinNpcData.values()) {
            spawnNPC(misthalinNpcData);
        }
        return null;
    }

    public int getNpcId() {
        return npcId;
    }

    public Position getSpawnPosition() {
        return spawnPosition;
    }

    public boolean getMovement() {
        return movement;
    }

    public int getRadius() {
        return radius;
    }
}



/*

MAN(2, new Position(3220, 3212, 0), new Position(3224, 3225, 0), 5, true, 4),
    WOMAN(25, new Position(3232, 3210, 0), new Position(3238, 3221, 0), 4, true, 4),
    //GOBLIN(14677, new Position(3204, 3207, 0), new Position(3205, 3209, 0), 15),
    //COW(81, new Position(3204, 3207, 0), new Position(3205, 3209, 0), 10),
    SHRIMP(316, new Position(3240, 3146, 0), new Position(3240, 3146, 0), 1, false, 4),
    SHRIMP2(316, new Position(3240, 3145, 0), new Position(3240, 3145, 0), 1, false, 4),
    SHRIMP3(316, new Position(3244, 3150, 0), new Position(3244, 3150, 0), 1, false, 4),

    ;


    private final int npcId;
    private final Position startPosition;
    private final Position endPosition;
    private final int quantity;
    private boolean movement;
    private int radius;
    LumbridgeData(int npcId, Position startPosition, Position endPosition, int quantity, boolean movement, int radius) {
        this.npcId = npcId;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.quantity = quantity;
        this.movement = movement;
        this.radius = radius;
    }


public static void spawn(final LumbridgeData lumbridgeData, final int npcId, final Position spawnPosition) {
        int x = spawnPosition.getX();
        int y = spawnPosition.getY();
        int z = spawnPosition.getZ();

        if (RegionClipping.canMove(x, y, x-1, y-1, z, 1, 1)) {
            NPC npc = new NPC(npcId, spawnPosition);
            npc.getMovementCoordinator().setCoordinator(new NPCMovementCoordinator.Coordinator(lumbridgeData.getMovement(), lumbridgeData.getRadius()));
            World.register(npc);
        } else {
            spawn(lumbridgeData, npcId, randomPosition(lumbridgeData));
        }

    }

    public static LumbridgeData load() {
        for (LumbridgeData lumbridgeData : LumbridgeData.values()) {
            for (int spawns = 0; spawns < lumbridgeData.getQuantity(); spawns++) {
                spawn(lumbridgeData, lumbridgeData.getNpcId(), randomPosition(lumbridgeData));
            }
        }
        return null;
    }

    public static Position randomPosition(LumbridgeData lumbridgeData) {
        int x = RandomUtility.inclusiveRandom(lumbridgeData.getStartPosition().getX(), lumbridgeData.getEndPosition().getX());
        int y = RandomUtility.inclusiveRandom(lumbridgeData.getStartPosition().getY(), lumbridgeData.getEndPosition().getY());
        int z = lumbridgeData.getStartPosition().getZ();
        Position spawnPosition = new Position(x, y, z);

        return spawnPosition;
    }
 */