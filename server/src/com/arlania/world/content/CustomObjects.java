package com.arlania.world.content;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.GameObject;
import com.arlania.model.GroundItem;
import com.arlania.model.Item;
import com.arlania.model.Locations.Location;
import com.arlania.model.Position;
import com.arlania.world.World;
import com.arlania.world.clip.region.RegionClipping;
import com.arlania.world.entity.impl.GroundItemManager;
import com.arlania.world.entity.impl.player.Player;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Handles customly spawned objects (mostly global but also privately for players)
 *
 * @author Gabriel Hannason
 */
public class CustomObjects {

    private static final int DISTANCE_SPAWN = 70; //Spawn if within 70 squares of distance
    public static final CopyOnWriteArrayList<GameObject> CUSTOM_OBJECTS = new CopyOnWriteArrayList<GameObject>();

    public static void init() {
        for (int i = 0; i < CLIENT_OBJECTS.length; i++) {
            int id = CLIENT_OBJECTS[i][0];
            int x = CLIENT_OBJECTS[i][1];
            int y = CLIENT_OBJECTS[i][2];
            int z = CLIENT_OBJECTS[i][3];
            int face = CLIENT_OBJECTS[i][4];
            GameObject object = new GameObject(id, new Position(x, y, z));
            object.setFace(face);
            RegionClipping.addObject(object);
        }
    }

    /**
     * Spawns a temporary object
     *
     * @param ob
     * @param player
     * @param cycles
     */
    public static void spawnTempObject(final GameObject ob, final Player player, final int cycles) {
        spawnGlobalObject(ob);
        TaskManager.submit(new Task(cycles) {
            @Override
            public void execute() {
                deleteGlobalObject(ob);


                if (player.getInteractingObject() != null && player.getInteractingObject().getId() == 32000) {
                    player.setInteractingObject(null);
                }
                this.stop();
            }

            @Override
            public void stop() {
                setEventRunning(false);
            }
        });
    }

    /**
     * Vorkath's Acid Pools
     **/
    public static void acidPool(final GameObject ob, final Player player, final int delay, final int cycles) {
        TaskManager.submit(new Task(delay) {
            @Override
            public void execute() {

                spawnGlobalObject(ob);
                TaskManager.submit(new Task(cycles) {
                    @Override
                    public void execute() {
                        deleteGlobalObject(ob);
                        player.getPacketSender().sendObject(new GameObject(-1, ob.getPosition()));
                        if (player.getInteractingObject() != null && player.getInteractingObject().getId() == 32000) {
                            player.setInteractingObject(null);
                        }
                        this.stop();
                    }

                    @Override
                    public void stop() {
                        setEventRunning(false);
                    }
                });
                stop();
            }
        });
    }

    private static void handleList(GameObject object, String handleType) {
        switch (handleType.toUpperCase()) {
            case "DELETE":
                for (GameObject objects : CUSTOM_OBJECTS) {
                    if (objects.getId() == object.getId() && object.getPosition().equals(objects.getPosition())) {
                        CUSTOM_OBJECTS.remove(objects);
                    }
                }
                break;
            case "ADD":
                if (!CUSTOM_OBJECTS.contains(object)) {
                    CUSTOM_OBJECTS.add(object);
                }
                break;
            case "EMPTY":
                CUSTOM_OBJECTS.clear();
                break;
        }
    }

    public static void spawnObject(Player p, GameObject object) {
        if (object.getId() != -1) {
            p.getPacketSender().sendObject(object);
            if (!RegionClipping.objectExists(object)) {
                RegionClipping.addObject(object);
            }
        } else {
            deleteObject(p, object);
        }
    }

    public static void deleteObject(Player p, GameObject object) {
        p.getPacketSender().sendObjectRemoval(object);
        if (RegionClipping.objectExists(object)) {
            RegionClipping.removeObject(object);
        }
    }

    public static void deleteGlobalObject(GameObject object) {
        handleList(object, "delete");
        World.deregister(object);
    }

    public static void spawnGlobalObject(GameObject object) {
        handleList(object, "add");
        World.register(object);
    }

    public static void spawnGlobalObjectWithinDistance(GameObject object) {
        for (Player player : World.getPlayers()) {
            if (player == null)
                continue;
            if (object.getPosition().isWithinDistance(player.getPosition(), DISTANCE_SPAWN)) {
                spawnObject(player, object);
            }
        }
    }

    public static void deleteGlobalObjectWithinDistance(GameObject object) {
        for (Player player : World.getPlayers()) {
            if (player == null)
                continue;
            if (object.getPosition().isWithinDistance(player.getPosition(), DISTANCE_SPAWN)) {
                deleteObject(player, object);
            }
        }
    }

    public static boolean objectExists(Position pos) {
        return getGameObject(pos) != null;
    }

    public static GameObject getGameObject(Position pos) {
        for (GameObject objects : CUSTOM_OBJECTS) {
            if (objects != null && objects.getPosition().equals(pos)) {
                return objects;
            }
        }
        return null;
    }

    public static void handleRegionChange(Player p) {
        for (GameObject object : CUSTOM_OBJECTS) {
            if (object == null)
                continue;
            if (object.getPosition().isWithinDistance(p.getPosition(), DISTANCE_SPAWN)) {
                spawnObject(p, object);
            }
        }
    }

    public static void objectRespawnTask(Player p, final GameObject tempObject, final GameObject mainObject, final int cycles) {
        deleteObject(p, mainObject);
        spawnObject(p, tempObject);


        //World.getPlayers().stream().filter(pl -> pl != null && pl.getInteractingObject() == mainObject).forEach(pl -> pl.getSkillManager().stopSkilling());


        TaskManager.submit(new Task(cycles) {
            @Override
            public void execute() {
                deleteObject(p, tempObject);
                spawnObject(p, mainObject);
                this.stop();
            }

        });
    }


    public static void globalObjectRespawnTask(final GameObject tempObject, final GameObject mainObject, final int cycles) {
        deleteGlobalObject(mainObject);
        spawnGlobalObject(tempObject);
        TaskManager.submit(new Task(cycles) {
            @Override
            public void execute() {
                deleteGlobalObject(tempObject);
                spawnGlobalObject(mainObject);
                this.stop();
            }
        });
    }

    public static void globalObjectRemovalTask(final GameObject object, final int cycles) {
        spawnGlobalObject(object);
        TaskManager.submit(new Task(cycles) {
            @Override
            public void execute() {
                deleteGlobalObject(object);
                this.stop();
            }
        });
    }

    public static void globalFiremakingTask(final GameObject fire, final Player player, final int cycles) {
        spawnGlobalObject(fire);
        TaskManager.submit(new Task(cycles) {
            @Override
            public void execute() {
                deleteGlobalObject(fire);
                if (player.getInteractingObject() != null && player.getInteractingObject().getId() == 2732) {
                    player.setInteractingObject(null);
                }
                this.stop();
            }

            @Override
            public void stop() {
                setEventRunning(false);
                GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(592), fire.getPosition(), player.getUsername(), false, 150, true, 100));
            }
        });
    }

    /**
     * Contains
     *
     * @param ObjectId - The object ID to spawn
     * @param absX - The X position of the object to spawn
     * @param absY - The Y position of the object to spawn
     * @param Z - The Z position of the object to spawn
     * @param face - The position the object will face
     */

    //Only adds clips to these objects, they are spawned clientsided
    //NOTE: You must add to the client's customobjects array to make them spawn, this is just clipping!
    private static final int[][] CLIENT_OBJECTS = {

            //Home
            {333355, 3221, 3210, 0, 0}, //Portal Nexus
            {333355, 3221, 3226, 0, 0}, //Portal Nexus

            //Trailblazer Archways
            //{340400, 3069, 3275, 0, 1}, //Asgarnia Archway (Fally/Draynor)
            //{-1, 3065, 3327, 0, 0}, //remove tree (Fally/Draynor archway area)
            //{340398, 3061, 3331, 0, 0}, //Rocks (Fally/Draynor)
            //{340397, 3061, 3329, 0, 0}, //Rocks (Fally/Draynor)
            //{340401, 3063, 3329, 0, 0}, //Misthalin Archway (Fally/Draynor)
            //{340398, 3068, 3329, 0, 0}, //Rocks (Fally/Draynor)
            //{340396, 3068, 3327, 0, 0}, //Rocks (Fally/Draynor)
            //{340399, 3067, 3328, 0, 0}, //Rocks (Fally/Draynor)

            //Wilderness Skilling
            {2732, 3368, 3613, 0, 0}, //Fire
            {2732, 3373, 3602, 0, 0}, //Fire-9
            {315250, 3248, 3779, 0, 0}, //Amethyst Crystal
            {315250, 3252, 3779, 0, 0}, //Amethyst Crystal
            {315250, 3248, 3775, 0, 0}, //Amethyst Crystal
            {315250, 3252, 3775, 0, 0}, //Amethyst Crystal
            {334773, 3337, 3881, 0, 0}, //Rune Essence
            {334772, 3373, 3891, 0, 0}, //Wrath Altar
            {304122, 3027, 3632, 0, 1}, //Tainted Chest
            {590, 2950, 3824, 0, 0}, //Exchange Table
            {334828, 3281, 3659, 0, 1}, //Larran's Small Chest
            {334829, 3018, 3962, 0, 0}, //Larran's Big Chest


            //DMZ
            {303828, 3226, 3107, 0, 2}, //Kalphite Tunnel Entrance
            {313289, 3466, 9484, 2, 3}, //Gilded Chest (Kalphite)


            {313289, 3376, 2971, 0, 1}, //Gilded Chest (Pollnivneach)

            //Remove Skeletons at Revs
            {-1, 3108, 3931, 0, 0}, //Remove Skeleton
            {-1, 3099, 3931, 0, 0}, //Remove Skeleton
            {-1, 3110, 3927, 0, 0}, //Remove Skeleton
            {-1, 3105, 3925, 0, 0}, //Remove Skeleton
            {-1, 3097, 3938, 0, 0}, //Remove Skeleton
            {-1, 3104, 3938, 0, 0}, //Remove Skeleton
            {-1, 3111, 3938, 0, 0}, //Remove Skeleton


            {326372, 2916, 3747, 0, 2}, //GWD Entrance
            {310230, 1911, 4367, 0, 0}, //DKS Entrance
            {310227, 3097, 3468, 0, 1}, //Edgeville Dungeon Entrance
            {334713, 2742, 3153, 0, 0}, //Brimhaven Dungeon Entrance
            {310227, 3008, 3151, 0, 0}, //Asgarnia Ice Dungeon Entrance
            {-1, 2877, 2951, 0, 0}, //Shilo Village Entrance
            {303828, 3296, 3142, 0, 0}, //Kalphite Queen Entrance

            //Rope Exits
            {326370, 2377, 4683, 0, 0}, //Themonuclear rope
            {326370, 2461, 4768, 0, 0}, //Nightmare rope
            {326370, 2871, 5318, 2, 0}, //GWD rope
            {326370, 1755, 5184, 0, 0}, //Mole rope
            {326370, 1240, 1242, 0, 0}, //Cerberus rope
            {326370, 3487, 9516, 0, 0}, //Kalphite Queen rope


            //Boss Instance Lobby
            {-1, 2339, 9630, 0, 0}, //Delete object
            {339674, 2335, 9627, 0, 0}, //Delete object
            {75, 2339, 9629, 0, 2}, //Bank Chest
            {4005, 2341, 9622, 0, 2}, //Divine Pool


            //Stronghold Raids
            {-1, 3080, 3420, 0, 0}, //Delete object
            {-1, 3079, 3421, 0, 0}, //Delete object
            {-1, 3081, 3422, 0, 0}, //Delete object
            {-1, 3082, 3421, 0, 0}, //Delete object
            {-1, 3083, 3421, 0, 0}, //Delete object
            {-1, 3083, 3420, 0, 0}, //Delete object
            {-1, 3082, 3420, 0, 0}, //Delete object
            {-1, 3081, 3419, 0, 0}, //Delete object
            {-1, 3080, 3418, 0, 0}, //Delete object


            //SHR Floor 1
            //{336087, 1870, 5191, 0, 2}, //Reward Chest


            {4005, 3032, 6123, 1, 0}, //Divine pool

            {409, 3704, 3801, 0, 1}, //Prayer altar (DZ)

            //{336566, 1792, 3778, 0, 0}, //Combat Dummy

            {4005, 2440, 5168, 0, 0}, //rejuv pool
            {13639, 2440, 5170, 0, 0}, //Divine pool

            {13639, 3724, 3811, 0, 0}, //rejuv pool
            {4005, 1800, 3777, 0, 0}, //Divine pool
            {28716, 3734, 3798, 0, 0}, //summoning obelisk
            {28716, 1815, 3777, 0, 0}, //summoning obelisk
            {23093, 2860, 5354, 2, 0}, //Bandos portal


            {2479, 2791, 4830, 0, 0}, //Mind altar

            //COX
            {13639, 1237, 3555, 0, 0}, //rejuv pool
            {4005, 1237, 3561, 0, 0}, //Divine pool

            //TOB
            {13639, 3670, 3222, 0, 0}, //rejuv pool
            {4005, 3670, 3214, 0, 0}, //Divine pool

            //Event island
            {13639, 3590, 3833, 0, 0}, //rejuv pool
            {4005, 3590, 3826, 0, 0}, //Divine pool

            //Gauntlet
            {13639, 1633, 3948, 0, 0}, //rejuv pool
            {4005, 1627, 3948, 0, 0}, //Divine pool


            {354, 3748, 5659, 0, 0}, //MLM crate
            {-1, 3748, 5672, 0, 0}, //MLM Delete Hopper
            {326674, 3748, 5673, 0, 0}, //MLM Hopper


            {-1, 2794, 9327, 0, 0}, //Delete table


            //Raids Lobby
            {13639, 2443, 3087, 0, 0}, //rejuv pool
            {4005, 2443, 3085, 0, 0}, //Divine pool


            //Stronghold Lobby
            {13639, 3085, 3419, 0, 0}, //rejuv pool
            {4005, 3085, 3423, 0, 0}, //Divine pool


            //Equipment Upgrades
            {334682, 2792, 9328, 0, 0}, //The Eternal Flame
            {75, 2792, 9331, 0, 2}, //Bank Chest


            //{563, 2793, 9331, 0, 1}, //Pat King statue
            //{569, 2795, 9328, 0, 2}, //Gladile Goblin statue


            //Piscatoris home
            {312897, 1793, 3773, 0, 2}, //Well of Events
            {312897, 3095, 3502, 0, 0}, //Well of Events
            {409, 1820, 3788, 0, 0}, //altar
            {411, 1827, 3788, 0, 0}, //Curse altar
            {6552, 1820, 3784, 0, 0}, //Ancient altar
            {13179, 1827, 3784, 0, 0}, //Lunar altar
            {13639, 1815, 3779, 0, 0}, //Resto Pool
            {330914, 1833, 3768, 0, 1}, //DZ Boat
            {2732, 1817, 3775, 0, 0}, //Fire
            {2182, 1818, 3780, 0, 0}, //RFD Chest
            {9398, 1800, 3789, 0, 0}, //GE
            {6420, 1902, 5345, 6, 0}, //GWD Raid Chest
            {172, 1806, 3789, 0, 0}, //Crystal Chest
            {590, 1807, 3789, 0, 0}, //Exchange Table
            {340383, 1809, 3789, 0, 1}, //Magical Pumpkin


            //PC Donator zone stuff
            {409, 2654, 2595, 0, 0}, //altar
            {9086, 2658, 2595, 0, 0}, //Furnace
            {28716, 2654, 2590, 0, 0}, //Summoning Obelisk
            {302097, 2659, 2590, 0, 0}, //Anvil
            {2732, 2649, 2606, 0, 0}, //Fire
            {13639, 2647, 2591, 0, 0}, //Resto Pool
            {13639, 2665, 2591, 0, 0}, //Resto Pool


            //PC Donator zone Magic Trees
            {1306, 2667, 2598, 0, 0},
            {1306, 2664, 2601, 0, 0},
            {1306, 2664, 2596, 0, 0},


            //PC Donator zone Yew Trees
            {1309, 2652, 2599, 0, 0},
            {1309, 2647, 2602, 0, 0},
            {1309, 2648, 2598, 0, 0},


            //PC Donator zone Runite Rocks
            {2107, 2659, 2587, 0, 0},
            {2107, 2659, 2588, 0, 0},
            {2107, 2660, 2588, 0, 0},
            {2107, 2661, 2589, 0, 0},
            {2107, 2661, 2590, 0, 0},
            {2107, 2662, 2590, 0, 0},


            //PC Donator zone Adamantite Rocks
            {2105, 2651, 2590, 0, 0},
            {2105, 2652, 2590, 0, 0},
            {2105, 2652, 2589, 0, 0},
            {2105, 2653, 2588, 0, 0},
            {2105, 2654, 2588, 0, 0},
            {2105, 2654, 2587, 0, 0},


            //PC Donator zone Mithril Rocks
            {2102, 2659, 2598, 0, 0},
            {2102, 2659, 2597, 0, 0},
            {2102, 2660, 2597, 0, 0},
            {2102, 2661, 2596, 0, 0},
            {2102, 2661, 2595, 0, 0},
            {2102, 2662, 2595, 0, 0},


            //PC Donator zone Gold
            {11183, 2651, 2595, 0, 0},
            {11183, 2652, 2595, 0, 0},
            {11183, 2652, 2596, 0, 0},
            {11183, 2653, 2597, 0, 0},
            {11183, 2654, 2597, 0, 0},
            {11183, 2654, 2598, 0, 0},


            //Donator zone Coal
            //{2097, 3756, 3816, 0, 0},
            //{2097, 3757, 3815, 0, 0},
            //{2097, 3756, 3814, 0, 0},
            {-1, 2666, 2590, 0, 1},
            {75, 2656, 2592, 0, 1},


            //PC Donator zone Essence
            {334773, 2655, 2575, 0, 0},

            //PC Donator zone Blood Altar
            {327978, 2650, 2581, 0, 0},

            //PC Donator zone Soul Altar
            {327980, 2661, 2581, 0, 1},

            //Donator Furance
            {11666, 3735, 3816, 0, 1},

            //Donator Anvil
            {4306, 3733, 3814, 0, 2},

            //PC Donator rocktails
            {10091, 2651, 2608, 0, 0},

            //Donator banks
            {75, 3735, 3810, 0, 0},
            {75, 3708, 3801, 0, 0},
            {75, 3730, 3790, 0, 0},
            {75, 3037, 4534, 0, 0},


            //Donator Rejuv pool
            {13639, 3751, 3798, 0, 0},


            //Raid chests
            {332994, 2442, 3092, 0, 1}, //raids lobby
            {332994, 1229, 3557, 0, 3}, //CoX lobby
            {332994, 3662, 3218, 0, 3}, //ToB lobby
            {332994, 3082, 3412, 0, 2}, //Stronghold lobby


            {-1, 1631, 3939, 0, 2}, //Gauntlet lobby


            {-1, 1232, 3557, 0, 3}, //null CoX statue


            {9093, 1940, 4963, 0, 0},


            {75, 1238, 3558, 0, 3},
            {75, 3673, 3222, 0, 0},
            {75, 3077, 3421, 0, 1},

            //Obelisk Bank Chests
            {75, 3155, 3621, 0, 3},
            {75, 3034, 3733, 0, 3},
            {75, 3105, 3795, 0, 3},
            {75, 2979, 3867, 0, 3},
            {75, 3306, 3917, 0, 3},
            {75, 3225, 3666, 0, 3},

            //Saradomin portal
            {23093, 2912, 5300, 2, 0},


            //zulrah safespots
            {25136, 2272, 3075, 0, 0},
            {25136, 2273, 3075, 0, 0},
            {25136, 2274, 3075, 0, 0},
            {25136, 2262, 3075, 0, 0},
            {25136, 2263, 3075, 0, 0},
            {25136, 2264, 3075, 0, 0},


            //cerberus safespots
            {14675, 1239, 1241, 0, 0},
            {14675, 1240, 1241, 0, 0},
            {14675, 1241, 1241, 0, 0},


            //Donator Boss Portals
            {2465, 2339, 9624, 0, 2},//Giant Mole
            {2466, 2339, 9623, 0, 2},//Kalphite Queen
            {2467, 2339, 9622, 0, 2},//DKS
            {2468, 2339, 9621, 0, 2},//GWD
            {2469, 2340, 9620, 0, 2},//TDS
            {2470, 2341, 9620, 0, 2},//Cerberus
            {2471, 2342, 9620, 0, 2},//Abyssal Sire
            {2472, 2343, 9620, 0, 2},//Thermonuclear Smoke Devil
            {2473, 2344, 9621, 0, 2},//Zulrah
            {2474, 2344, 9622, 0, 2},//Corporeal Beast
            {2475, 2344, 9623, 0, 2},//Nex
            {2476, 2344, 9624, 0, 2},//Nemesis


            //Runecrafting area
            {2478, 2886, 4819, 0, 0},
            {2479, 2891, 4825, 0, 0},
            {2480, 2897, 4807, 0, 0},
            {2481, 2897, 4856, 0, 0},
            {2482, 2891, 4840, 0, 0},
            {2483, 2897, 4844, 0, 0},
            {2484, 2920, 4845, 0, 0},
            {2487, 2919, 4852, 0, 0},
            {2486, 2929, 4854, 0, 0},
            {2485, 2928, 4808, 0, 0},
            {2488, 2921, 4810, 0, 0},


            {6552, 3090, 3507, 0, 3},
            {13179, 3090, 3511, 0, 3},
            {409, 3085, 3508, 0, 1},
            {411, 3085, 3511, 0, 1},
            {3192, 3083, 3484, 0, 0},
            {172, 3732, 3810, 0, 2}, //crystal chest
            {6420, 3733, 3810, 0, 0}, //raid chest
            {590, 3735, 3801, 0, 0}, //exchange table
            {590, 2656, 2600, 0, 0}, //exchange table
            {9398, 3737, 3806, 0, 0}, //grand exchange
            {2732, 3733, 3801, 0, 0}, //fire at home

            {-1, 3088, 3509, 0, 0}, //remove cart near altars
            {13639, 3087, 3510, 0, 0}, //rejuvination pool

            {75, 3044, 4971, 1, 0}, //Rogue's Den Bank
            {4306, 3431, 2872, 0, 2}, //Anvil
            {6189, 3433, 2871, 0, 3}, //Furnace
            {10091, 3116, 3494, 0, 0}, //Rocktail fishing
            {409, 3443, 2918, 0, 2}, //Altar
            {6552, 3439, 2918, 0, 2}, //Altar
            {8749, 3445, 2913, 0, 3}, //Altar
            {411, 3441, 2910, 0, 0}, //Altar
            {13179, 3439, 2912, 0, 1}, //Altar
            {172, 3208, 3438, 0, 2}, //crystal key chest
            {13179, 3226, 3433, 0, 3}, //veng
            {6552, 3226, 3435, 0, 3}, //ancient
            {409, 3205, 3434, 0, 1}, //pray altar
            {411, 3205, 3436, 0, 1}, //turmoil
            {11666, 3195, 3436, 0, 2}, //furnace (varrock)
            {6420, 3091, 3500, 0, 0}, //key chest
            //{3192, 3090, 3503, 0, 2}, //scoreboard
            {1746, 3215, 3438, 0, 0}, //ladder

            {1746, 3191, 3674, 0, 0}, //corporeal beast ladder


            {-1, 3217, 3436, 0, 0}, //remove stall
            {-1, 3219, 3436, 0, 0}, //remove stall
            {-1, 3220, 3431, 0, 0}, //remove stall
            {-1, 3220, 3425, 0, 0}, //remove stall
            {-1, 3223, 3434, 0, 0}, //remove oak
            {-1, 3226, 3431, 0, 0}, //remove plant

            {8749, 2307, 9806, 0, 1}, //special attack altar
            {411, 2307, 9807, 0, 1}, //pray altar
            {409, 2307, 9805, 0, 1}, //pray switch altar

            {8702, 3350, 9636, 0, 0},//Fish barrel
            {12269, 3350, 9634, 0, 0},//Cook
            {210, 3361, 9642, 0, 0},//Ice Light
            {210, 3365, 9642, 0, 0},//Ice Light
            {210, 3361, 9638, 0, 0},//Ice Light
            {210, 3365, 9638, 0, 0},//Ice Light
            {586, 3363, 9640, 0, 2},//SOMETHING???

            //lumby cows gate
            {3192, 3084, 3485, 0, 4},
            {-1, 3084, 3487, 0, 2},

            //fishing spots

            {318, 3113, 3493, 0, 0},
            {312, 3120, 3497, 0, 0},
            {316, 3130, 3511, 0, 0},


            {2732, 2342, 3697, 0, 0}, //fire at piscatoris

            //Custom Edgeville
            {-1, 3090, 3503, 0, 0}, //delete
            {314830, 3090, 3502, 0, 0}, //Teleport Obelisk

            //SHR
            {336076, 3363, 9640, 0, 0}, //Boss spawn object
            {323955, 3363, 9650, 0, 2}, //Magical Animator
            {323956, 3362, 9649, 0, 3}, //Magical Animator (corner)
            {323956, 3362, 9651, 0, 0}, //Magical Animator (corner)
            {323956, 3364, 9649, 0, 2}, //Magical Animator (corner)
            {323956, 3364, 9651, 0, 1}, //Magical Animator (corner)
            {323955, 3363, 9629, 0, 0}, //Magical Animator
            {323956, 3362, 9628, 0, 3}, //Magical Animator (corner)
            {323956, 3362, 9630, 0, 0}, //Magical Animator (corner)
            {323956, 3364, 9628, 0, 2}, //Magical Animator (corner)
            {323956, 3364, 9630, 0, 1}, //Magical Animator (corner)



    };


    /**
     * Contains
     *
     * @param ObjectId - The object ID to spawn
     * @param absX - The X position of the object to spawn
     * @param absY - The Y position of the object to spawn
     * @param Z - The Z position of the object to spawn
     * @param face - The position the object will face
     */

    //Objects that are handled by the server on regionchange
    private static final int[][] CUSTOM_OBJECTS_SPAWNS = {
            {2079, 2576, 9876, 0, 0},
            {6420, 3091, 3500, 0, 0}, //key chest

            //DEV area
            {13639, 2716, 9622, 0, 0}, //rejuvination pool
            {13639, 2722, 9622, 0, 0}, //rejuvination pool
            {13568, 2715, 9624, 0, 0}, //Beer Barrel


            {8135, 3058, 3311, 0, 0},//Herb Patch


            /**
             * ZULRAH
            */
            {25136, 2278, 3070, 0, 0},
            {25136, 2278, 3065, 0, 0},
            {25138, 2273, 3066, 0, 0},
            {25136, 2272, 3065, 0, 0},
            {25139, 2267, 3065, 0, 0},
            {25136, 2260, 3081, 0, 0},
            {1, 3038, 3415, 0, 0},
            {357, 3034, 3422, 0, 0},
            {-1, 2265, 3071, 0, 0},
            {-1, 2271, 3071, 0, 0},


            {-1, 3084, 3487, 0, 2},

            {2274, 3652, 3488, 0, 0},
            {2274, 3039, 9555, 0, 0},
            {2274, 3039, 9554, 0, 0},

            /*
            * Remove Uber Zone Objects
            */
            {-1, 2425, 4714, 0, 0},
            {-1, 2420, 4716, 0, 0},
            {-1, 2426, 4726, 0, 0},
            {-1, 2420, 4709, 0, 0},
            {-1, 2419, 4698, 0, 0},
            {-1, 2420, 4700, 0, 0},
            {-1, 2399, 4721, 0, 0},
            {-1, 2398, 4721, 0, 0},
            {-1, 2399, 4720, 0, 0},
            {-1, 2395, 4722, 0, 0},
            {-1, 2398, 4717, 0, 0},
            {-1, 2396, 4717, 0, 0},
            {-1, 2396, 4718, 0, 0},
            {-1, 2396, 4719, 0, 0},
            {-1, 2395, 4718, 0, 0},
            {-1, 2394, 4711, 0, 0},
            {-1, 2396, 4711, 0, 0},
            {-1, 2397, 4711, 0, 0},
            {-1, 2397, 4713, 0, 0},
            {-1, 2399, 4713, 0, 0},
            {-1, 2402, 4726, 0, 0},
            {-1, 2407, 4728, 0, 0},
            {-1, 2405, 4724, 0, 0},
            {-1, 2409, 4705, 0, 0},
            {-1, 2410, 4704, 0, 0},
            {-1, 2407, 4702, 0, 0},
            {-1, 2407, 4701, 0, 0},
            {-1, 2408, 4701, 0, 0},
            {-1, 2412, 4701, 0, 0},
            {-1, 2413, 4701, 0, 0},
            {-1, 2414, 4703, 0, 0},
            {-1, 2416, 4714, 0, 0},
            {-1, 2412, 4732, 0, 0},
            {-1, 2413, 4729, 0, 0},
            {-1, 2414, 4733, 0, 0},
            {-1, 2415, 4730, 0, 0},
            {-1, 2416, 4730, 0, 0},
            {-1, 2416, 4731, 0, 0},
            {-1, 2419, 4731, 0, 0},
            {-1, 2420, 4731, 0, 0},
            {-1, 2420, 4732, 0, 0},
            {-1, 2415, 4725, 0, 0},
            {-1, 2417, 4729, 0, 0},
            {-1, 2418, 4727, 0, 0},
            {-1, 2418, 4723, 0, 0},
            {-1, 2419, 4722, 0, 0},
            {-1, 2420, 4726, 0, 0},
            {-1, 2415, 4725, 0, 0},
            {-1, 2417, 4729, 0, 0},
            {-1, 2418, 4727, 0, 0},
            {-1, 2418, 4723, 0, 0},
            {-1, 2419, 4722, 0, 0},
            {-1, 2420, 4726, 0, 0},


            //gamble zone
            {2213, 2842, 5143, 0, 0},
            {2213, 2843, 5143, 0, 0},
            {2213, 2844, 5143, 0, 0},
            {2213, 2845, 5143, 0, 0},
            {2213, 2846, 5143, 0, 0},
            {2213, 2847, 5143, 0, 0},
            {2213, 2848, 5143, 0, 0},
            {2213, 2849, 5143, 0, 0},
            {2213, 2850, 5143, 0, 0},
            {2213, 2851, 5143, 0, 0},


            {2274, 3652, 3488, 0, 0},
    };

    public static boolean cloudExists(Location loc) {
        return getCloudObject(loc);
    }


    public static boolean getCloudObject(Location loc) {
        for (GameObject objects : CUSTOM_OBJECTS) {
            System.out.println(loc);
			/*if (objects.inLocation(objects.getPosition().getX(), objects.getPosition().getY(), Locations.Location.ZULRAH_CLOUD_FIVE)) {
				return true;
			} else {
				return false;
			}*/
        }
        return false;
    }


    public static void zulrahToxicClouds(final GameObject cloud, final Player player, final int cycles) {
        player.setInteractingObject(cloud);
        spawnGlobalObject(cloud);
        TaskManager.submit(new Task(cycles) {
            @Override
            public void execute() {
                deleteGlobalObject(cloud);
                player.setCloudsSpawned(false);
                if (player.getInteractingObject() != null
                        && player.getInteractingObject().getId() == 11700) {
                    player.setInteractingObject(null);
                }
                this.stop();
            }

            @Override
            public void stop() {
                setEventRunning(false);
            }
        });

    }
}
