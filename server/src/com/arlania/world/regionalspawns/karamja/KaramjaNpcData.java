package com.arlania.world.regionalspawns.karamja;

import com.arlania.model.Position;
import com.arlania.world.World;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.npc.NPCMovementCoordinator;

public enum KaramjaNpcData {

    //GOBLIN(14677, new Position(3204, 3207, 0), 15),
    //COW(81, new Position(3204, 3207, 0), 10),


    //SHOPKEEPER_LUMBY_1(521, new Position(3211, 3248, 0), true, 3),
    //SMITHING_APPRENTICE_LUMBY_1(4904, new Position(3224, 3256, 0), true, 2),
    //BARTENDER_LUMBY_1(731, new Position(3232, 3241, 0), true, 1),
    //MASTER_FARMER_DRAYNOR_1(2234, new Position(3078, 3251, 0), true, 3),

    //Moss Giant Island
    MOSS_GIANT_1(4534, new Position(2702, 3207, 0), true, 3),
    MOSS_GIANT_2(4534, new Position(2692, 3217, 0), true, 3),
    MOSS_GIANT_3(4534, new Position(2691, 3213, 0), true, 3),
    MOSS_GIANT_4(4534, new Position(2698, 3205, 0), true, 3),
    MOSS_GIANT_5(4534, new Position(2692, 3203, 0), true, 3),
    MOSS_GIANT_6(4534, new Position(2699, 3213, 0), true, 3),

    ;


    private final int npcId;
    private final Position spawnPosition;
    private boolean movement;
    private int radius;
    KaramjaNpcData(int npcId, Position spawnPosition, boolean movement, int radius) {
        this.npcId = npcId;
        this.spawnPosition = spawnPosition;
        this.movement = movement;
        this.radius = radius;
    }

    public static void spawnNPC(final KaramjaNpcData misthalinNpcData) {

        final Position spawnPosition = misthalinNpcData.getSpawnPosition();
        final int npcId = misthalinNpcData.getNpcId();

        NPC npc = new NPC(npcId, spawnPosition);
        npc.getMovementCoordinator().setCoordinator(new NPCMovementCoordinator.Coordinator(misthalinNpcData.getMovement(), misthalinNpcData.getRadius()));
        World.register(npc);


    }

    public static KaramjaNpcData load() {
        for (KaramjaNpcData misthalinNpcData : KaramjaNpcData.values()) {
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