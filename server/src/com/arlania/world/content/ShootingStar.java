package com.arlania.world.content;

import com.arlania.model.Animation;
import com.arlania.model.GameObject;
import com.arlania.model.Position;
import com.arlania.util.RandomUtility;
import com.arlania.util.Stopwatch;
import com.arlania.world.World;
import com.arlania.world.entity.impl.player.Player;

public class ShootingStar {

    private static final int TIME = 750000;
    public static final int MAXIMUM_MINING_AMOUNT = 800;

    private static final Stopwatch timer = new Stopwatch().reset();
    public static CrashedStar CRASHED_STAR = null;
    private static LocationData LAST_LOCATION = null;

    public static class CrashedStar {

        public CrashedStar(GameObject starObject, LocationData starLocation) {
            this.starObject = starObject;
            this.starLocation = starLocation;
        }

        private final GameObject starObject;
        private final LocationData starLocation;

        public GameObject getStarObject() {
            return starObject;
        }

        public LocationData getStarLocation() {
            return starLocation;
        }
    }

    public enum LocationData {
        LOCATION_1(new Position(3186, 3937), "in the Wildy Resource Area", "Resource"),
        LOCATION_2(new Position(2960, 3822), "near the Chaos Altar", "Chaos Altar"),
        LOCATION_3(new Position(3267, 3659), "near Larran's Chest", "Larran's Chest"),
        LOCATION_4(new Position(3244, 3776), "near the Amethyst mining area", "Amethyst"),
        LOCATION_5(new Position(3364, 3604), "near the Anglerfish area", "Anglerfish"),
        LOCATION_6(new Position(3371, 3895), "near the Wrath altar", "Wrath altar");

        LocationData(Position spawnPos, String clue, String playerPanelFrame) {
            this.spawnPos = spawnPos;
            this.clue = clue;
            this.playerPanelFrame = playerPanelFrame;
        }

        private final Position spawnPos;
        private final String clue;
        public String playerPanelFrame;
    }

    public static LocationData getLocation() {
        return LAST_LOCATION;
    }

    public static LocationData getRandom() {
        LocationData star = LocationData.values()[RandomUtility.inclusiveRandom(LocationData.values().length - 1)];
        return star;
    }

    public static void sequence() {
        if (CRASHED_STAR == null) {
            if (timer.elapsed(TIME)) {
                LocationData locationData = getRandom();
                if (LAST_LOCATION != null) {
                    if (locationData == LAST_LOCATION) {
                        locationData = getRandom();
                    }
                }
                LAST_LOCATION = locationData;
                CRASHED_STAR = new CrashedStar(new GameObject(38660, locationData.spawnPos), locationData);
                CustomObjects.spawnGlobalObject(CRASHED_STAR.starObject);
                World.sendMessage("minigames", "");
                World.sendMessage("minigames", "<img=90> @blu@[Shooting Star]@bla@ A star has just crashed " + locationData.clue + "!");
                World.sendMessage("minigames", "");
                //World.getPlayers().forEach(p -> p.getPacketSender().sendString(26623, "@or2@Crashed star: @gre@"+ShootingStar.CRASHED_STAR.getStarLocation().playerPanelFrame+""));
                timer.reset();
            }
        } else {
            if (CRASHED_STAR.starObject.getPickAmount() >= MAXIMUM_MINING_AMOUNT) {
                despawn(false);
                timer.reset();
            }
        }
    }


    public static void despawn(boolean respawn) {
        if (respawn) {
            timer.reset(0);
        } else {
            timer.reset();
        }
        if (CRASHED_STAR != null) {
            for (Player p : World.getPlayers()) {
                if (p == null) {
                    continue;
                }
                p.getPacketSender().sendString(26623, "@or2@Crashed star: @gre@N/A ");
                if (p.getInteractingObject() != null && p.getInteractingObject().getId() == CRASHED_STAR.starObject.getId()) {
                    p.performAnimation(new Animation(65535));
                    p.getPacketSender().sendClientRightClickRemoval();
                    p.getSkillManager().stopSkilling();
                    p.getPacketSender().sendMessage("The star has been fully mined.");
                }
            }
            CustomObjects.deleteGlobalObject(CRASHED_STAR.starObject);
            CRASHED_STAR = null;
        }
    }
}
