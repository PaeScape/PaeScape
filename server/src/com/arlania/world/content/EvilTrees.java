package com.arlania.world.content;

import com.arlania.model.Animation;
import com.arlania.model.GameObject;
import com.arlania.model.Position;
import com.arlania.util.RandomUtility;
import com.arlania.util.Stopwatch;
import com.arlania.world.World;
import com.arlania.world.entity.impl.player.Player;

//import sun.net.www.content.text.plain;

/**
 * Evil Tree's Spawning every 40 minutes
 **/
/*@author Levi <www.rune-server.org/members/AuguryPS>
 */

/*
 * Evil Trees
 * Object id: 11434
 */

public class EvilTrees {


    private static final int TIME = 1000000; //40 minutes? not sure lol
    public static final int MAX_CUT_AMOUNT = 600;//Amount of logs the tree will give before
    //despawning

    public static Stopwatch timer = new Stopwatch().reset();
    public static EvilTree SPAWNED_TREE = null;
    public static LocationData LAST_LOCATION = null;

    public static class EvilTree {

        public EvilTree(GameObject treeObject, LocationData treeLocation) {
            this.treeObject = treeObject;
            this.treeLocation = treeLocation;
        }

        private final GameObject treeObject;
        private final LocationData treeLocation;

        public GameObject getTreeObject() {
            return treeObject;
        }

        public LocationData getTreeLocation() {
            return treeLocation;
        }
    }

    /**
     * Holds the location data in an enum for where the treee's will spawn
     */
    public enum LocationData {

        LOCATION_1(new Position(3176, 3931), "in the Wildy Resource Area", "Resource"),
        LOCATION_2(new Position(2960, 3818), "near the Chaos Altar", "Chaos Altar"),
        LOCATION_3(new Position(3265, 3667), "near Larran's Chest", "Larran's Chest"),
        LOCATION_4(new Position(3254, 3777), "near the Amethyst mining area", "Amethyst"),
        LOCATION_5(new Position(3372, 3614), "near the Anglerfish area", "Anglerfish"),
        LOCATION_6(new Position(3377, 3882), "near the Wrath altar", "Wrath altar");

        LocationData(Position spawnPos, String clue, String playerPanelFrame) {
            this.spawnPos = spawnPos;
            this.clue = clue;
            this.playerPanelFrame = playerPanelFrame;
        }

        private final Position spawnPos;
        private final String clue;
        public String playerPanelFrame;
    }

    public static LocationData getRandom() {
        LocationData tree = LocationData.values()[RandomUtility.inclusiveRandom(LocationData.values().length - 1)];
        return tree;
    }

    /*
     * Sequences the spawning so you don't have the same location back to back
     *
     */
    public static void sequence() {
        if (SPAWNED_TREE == null) {
            if (timer.elapsed(TIME)) {
                LocationData locationData = getRandom();
                if (LAST_LOCATION != null) {
                    if (locationData == LAST_LOCATION) {
                        locationData = getRandom();
                    }
                }
                LAST_LOCATION = locationData;
                SPAWNED_TREE = new EvilTree(new GameObject(11434, locationData.spawnPos), locationData);
                CustomObjects.spawnGlobalObject(SPAWNED_TREE.treeObject);
                World.sendMessage("minigames", "");
                World.sendMessage("minigames", "<img=99> @blu@[Evil Tree]@bla@ The Evil Tree has sprouted " + locationData.clue + "!");
                timer.reset();
                World.getPlayers().forEach(p -> PlayerPanel.refreshPanel(p));
            }
        } else {
            if (SPAWNED_TREE.treeObject.getCutAmount() >= MAX_CUT_AMOUNT) {
                despawn(false);
                timer.reset();
            }
        }
    }

    /**
     * Handles the despawning of the tree and resets the timer
     */
    public static void despawn(boolean respawn) {
        if (respawn) {
            timer.reset(0);
        } else {
            timer.reset();
        }
        if (SPAWNED_TREE != null) {
            for (Player p : World.getPlayers()) {
                if (p == null) {
                    continue;
                }
                if (p.getInteractingObject() != null && p.getInteractingObject().getId() == SPAWNED_TREE.treeObject.getId()) {
                    p.performAnimation(new Animation(65535));
                    p.getPacketSender().sendClientRightClickRemoval();
                    p.getSkillManager().stopSkilling();
                    p.getPacketSender().sendMessage("@blu@[EVIL TREES]@bla@The Evil Tree has been chopped down");
                }
            }
            CustomObjects.deleteGlobalObject(SPAWNED_TREE.treeObject);
            SPAWNED_TREE = null;
        }
    }

    public static LocationData getLocation() {
        return LAST_LOCATION;
    }
}

