package com.arlania.world.content;

import com.arlania.model.Position;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.clip.region.RegionClipping;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

import java.util.*;

public class Boundary {

    int minX, minY, highX, highY;
    int height;

    /**
     * @param minX  The south-west x coordinate
     * @param minY  The south-west y coordinate
     * @param highX The north-east x coordinate
     * @param highY The north-east y coordinate
     */
    public Boundary(int minX, int minY, int highX, int highY) {
        this.minX = minX;
        this.minY = minY;
        this.highX = highX;
        this.highY = highY;
    }

    /**
     * @param minX   The south-west x coordinate
     * @param minY   The south-west y coordinate
     * @param highX  The north-east x coordinate
     * @param highY  The north-east y coordinate
     * @param height The height of the boundary
     */
    public Boundary(int minX, int minY, int highX, int highY, int height) {
        this.minX = minX;
        this.minY = minY;
        this.highX = highX;
        this.highY = highY;
        this.height = height;
    }

    public int getMinimumX() {
        return minX;
    }

    public int getMinimumY() {
        return minY;
    }

    public int getMaximumX() {
        return highX;
    }

    public int getMaximumY() {
        return highY;
    }

    /**
     * @param player     The player object
     * @param boundaries The array of Boundary objects
     * @return
     */
    public static boolean isIn(Player player, Boundary[] boundaries) {
        for (Boundary b : boundaries) {
            if (b.height > 0) {
                if (player.getPosition().getZ() != b.height) {
                    return false;
                }
            }
            if (player.getPosition().getX() >= b.minX && player.getPosition().getX() <= b.highX && player.getPosition().getY() >= b.minY && player.getPosition().getY() <= b.highY) {
                return true;
            }
        }
        return false;
    }

    public static boolean isIn1(Player player, Boundary[] boundaries) {
        for (Boundary b : boundaries) {
            if (b.height > 0) {
                if (player.getPosition().getZ() != b.height) {
                    return false;
                }
            }
            if (player.getPosition().getX() >= b.minX && player.getPosition().getX() <= b.highX && player.getPosition().getY() >= b.minY && player.getPosition().getY() <= b.highY) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param player     The player object
     * @param boundaries The boundary object
     * @return
     */
    public static boolean isIn(Player player, Boundary boundaries) {
        if (boundaries.height > 0) {
            if (player.getPosition().getZ() != boundaries.height) {
                return false;
            }
            return player.getPosition().getX() > boundaries.minX || player.getPosition().getX() < boundaries.highX || player.getPosition().getY() > boundaries.minY || player.getPosition().getY() < boundaries.highY;
        }
        return false;
    }

    public List<Position> getRandomLocations(int count, int height) {
        List<Position> locations = new ArrayList<Position>();
        int lock = 50; //prevents deadlocks
        while (locations.size() < count && lock-- > 0) {
            int x = RandomUtility.inclusiveRandom(minX, highX);
            int y = RandomUtility.inclusiveRandom(minY, highY);
            Position location = new Position(x, y, height);

            if (RegionClipping.getGameObject(location) != null && !locations.contains(location)) {
                locations.add(location);
            }
        }
        return locations;
    }

    /**
     * @param npc        The npc object
     * @param boundaries The boundary object
     * @return
     */
    public static boolean isIn(NPC npc, Boundary boundaries) {
        if (boundaries.height > 0) {
            if (npc.getPosition().getZ() != boundaries.height) {
                return false;
            }
        }
        return npc.getPosition().getX() >= boundaries.minX && npc.getPosition().getX() <= boundaries.highX
                && npc.getPosition().getY() >= boundaries.minY && npc.getPosition().getY() <= boundaries.highY;
    }

    /**
     * @param npc        The npc object
     * @param boundaries The boundary object
     * @return
     */
    public static boolean isInNoHeight(Player npc, Boundary boundaries) {
        return npc.getPosition().getX() >= boundaries.minX && npc.getPosition().getX() <= boundaries.highX
                && npc.getPosition().getY() >= boundaries.minY && npc.getPosition().getY() <= boundaries.highY;
    }

    public static boolean isIn(NPC npc, Boundary[] boundaries) {
        for (Boundary boundary : boundaries) {
            if (boundary.height > 0) {
                if (npc.getPosition().getZ() != boundary.height) {
                    return false;
                }
            }
            if (npc.getPosition().getX() >= boundary.minX && npc.getPosition().getX() <= boundary.highX
                    && npc.getPosition().getY() >= boundary.minY && npc.getPosition().getY() <= boundary.highY) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInSameBoundary(Player player1, Player player2, Boundary[] boundaries) {
        Optional<Boundary> boundary1 = Arrays.asList(boundaries).stream().filter(b -> isIn(player1, b)).findFirst();
        Optional<Boundary> boundary2 = Arrays.asList(boundaries).stream().filter(b -> isIn(player2, b)).findFirst();
        if (!boundary1.isPresent() || !boundary2.isPresent()) {
            return false;
        }
        return Objects.equals(boundary1.get(), boundary2.get());
    }

    public static int entitiesInArea(Boundary boundary) {
        int i = 0;
        for (Player player : World.getPlayers())
            if (player != null)
                if (isIn(player, boundary))
                    i++;
        return i;
    }

    public static final Boundary BANDOS_GODWARS = new Boundary(2864, 5351, 2876, 5369);
    public static final Boundary ARMADYL_GODWARS = new Boundary(2824, 5296, 2842, 5308);
    public static final Boundary ZAMORAK_GODWARS = new Boundary(2918, 5318, 2936, 5331);
    public static final Boundary SARADOMIN_GODWARS = new Boundary(2889, 5258, 2907, 5276);
    public static final Boundary BOUNDARY_CORP = new Boundary(3000, 4400, 3000, 4400);
    public static final Boundary BOUNDARY_BOSS = new Boundary(3254, 3870, 3272, 3888);

    public static final Boundary[] GODWARS_BOSSROOMS = {
            BANDOS_GODWARS,
            ARMADYL_GODWARS,
            ZAMORAK_GODWARS,
            SARADOMIN_GODWARS
    };

    public static final Boundary RESOURCE_AREA = new Boundary(3174, 3924, 3196, 3944);
    public static final Boundary KBD_AREA = new Boundary(2251, 4675, 2296, 4719);
    public static final Boundary PEST_CONTROL_AREA = new Boundary(2650, 2635, 2675, 2655);
    public static final Boundary FIGHT_CAVE = new Boundary(2365, 5052, 2429, 5122);
    public static final Boundary EDGEVILLE_PERIMETER = new Boundary(3073, 3465, 3108, 3518);

    public static final Boundary[] DUEL_ARENAS = new Boundary[]{
            new Boundary(3332, 3244, 3359, 3259),
            new Boundary(3364, 3244, 3389, 3259)
    };
}