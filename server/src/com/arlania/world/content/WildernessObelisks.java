package com.arlania.world.content;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.GameObject;
import com.arlania.model.Locations;
import com.arlania.model.Locations.Location;
import com.arlania.model.Position;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.entity.impl.player.Player;

/**
 * Handles the Wilderness teleport obelisks.
 *
 * @author Gabriel Hannason
 */
public class WildernessObelisks {

    /**
     * Activates the Wilderness obelisks.
     *
     * @param objectId The object id
     * @return true if the object is an obelisk
     */
    public static boolean handleObelisk(int objectId) {
        final int index = getObeliskIndex(objectId);
        if (index >= 0) {
            if (!OBELISK_ACTIVATED[index]) {
                OBELISK_ACTIVATED[index] = true;
                obelisks[0] = new GameObject(14825, new Position(OBELISK_COORDS[index][0], OBELISK_COORDS[index][1]));
                obelisks[1] = new GameObject(14825, new Position(OBELISK_COORDS[index][0] + 4, OBELISK_COORDS[index][1]));
                obelisks[2] = new GameObject(14825, new Position(OBELISK_COORDS[index][0], OBELISK_COORDS[index][1] + 4));
                obelisks[3] = new GameObject(14825, new Position(OBELISK_COORDS[index][0] + 4, OBELISK_COORDS[index][1] + 4));
                int obeliskX, obeliskY;
                for (int i = 0; i < obelisks.length; i++) {
                    obeliskX = i == 1 || i == 3 ? OBELISK_COORDS[index][0] + 4 : OBELISK_COORDS[index][0];
                    obeliskY = i >= 2 ? OBELISK_COORDS[index][1] + 4 : OBELISK_COORDS[index][1];
                    CustomObjects.globalObjectRespawnTask(obelisks[i], new GameObject(OBELISK_IDS[index], new Position(obeliskX, obeliskY)), 4);
                }
                TaskManager.submit(new Task(4, false) {
                    @Override
                    public void execute() {
                        handleTeleport(index);
                        stop();
                    }

                    @Override
                    public void stop() {
                        setEventRunning(false);
                        OBELISK_ACTIVATED[index] = false;
                    }
                });
            }
            return true;
        }
        return false;
    }

    public static void handleTeleport(int index) {
        int random = RandomUtility.inclusiveRandom(5);
        while (random == index)
            random = RandomUtility.inclusiveRandom(5);
        for (Player player : World.getPlayers()) {
            if (player == null || player.getLocation() == null || player.getLocation() != Location.WILDERNESS)
                continue;
            if (Locations.goodDistance(player.getPosition().copy(), new Position(OBELISK_COORDS[index][0] + 2, OBELISK_COORDS[index][1] + 2), 1))
                player.moveTo(new Position(OBELISK_COORDS[random][0] + 2, OBELISK_COORDS[random][1] + 2));
        }
    }

    /*
     * Gets the array index for an obelisk
     */
    public static int getObeliskIndex(int id) {
        for (int j = 0; j < OBELISK_IDS.length; j++) {
            if (OBELISK_IDS[j] == id)
                return j;
        }
        return -1;
    }

    /*
     * Obelisk ids
     */
    private static final int[] OBELISK_IDS = {
            314829, 314830,
            314827, 314828,
            314826, 314831
    };

    /*
     * The obelisks
     */
    public static final GameObject[] obelisks = new GameObject[4];

    /*
     * Are the obelisks activated?
     */
    private static final boolean[] OBELISK_ACTIVATED = new boolean[OBELISK_IDS.length];

    /*
     * Obelisk coords
     */
    private static final int[][] OBELISK_COORDS = {
            {3154, 3618}, {3225, 3665},
            {3033, 3730}, {3104, 3792},
            {2978, 3864}, {3305, 3914}
    };

}
