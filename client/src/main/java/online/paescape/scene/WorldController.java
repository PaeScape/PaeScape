package online.paescape.scene;

import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.hooks.DrawCallbacks;
import net.runelite.rs.api.*;
import online.paescape.Client;
import online.paescape.collection.Deque;
import online.paescape.media.DrawingArea;
import online.paescape.media.Rasterizer;
import online.paescape.media.VertexNormal;
import online.paescape.media.animable.Animable;
import online.paescape.media.animable.InteractableObject;
import online.paescape.media.animable.Model;
import online.paescape.scene.tile.*;
import online.paescape.scene.tile.Tile;
import online.paescape.scene.tile.WallObject;

import java.util.HashSet;
import java.util.Set;

import static net.runelite.api.Constants.*;
import static net.runelite.api.Constants.ROOF_FLAG_BETWEEN;

@SuppressWarnings("all")
public final class WorldController implements RSScene {

    private static final int[] faceXoffset2 = {53, -53, -53, 53};
    private static final int[] faceYOffset2 = {-53, -53, 53, 53};
    private static final int[] faceXOffset3 = {-45, 45, 45, -45};
    private static final int[] faceYOffset3 = {45, 45, -45, -45};
    private static final int amountOfCullingClusters;
    private static final CullingCluster[] processedClusters = new CullingCluster[500];
    private static final int[] anIntArray478 = {19, 55, 38, 155, 255, 110, 137, 205, 76};
    private static final int[] anIntArray479 = {160, 192, 80, 96, 0, 144, 80, 48, 160};
    private static final int[] anIntArray480 = {76, 8, 137, 4, 0, 1, 38, 2, 19};
    private static final int[] anIntArray481 = {0, 0, 2, 0, 0, 2, 1, 1, 0};
    private static final int[] anIntArray482 = {2, 0, 0, 2, 0, 0, 0, 4, 4};
    private static final int[] anIntArray483 = {0, 4, 4, 8, 0, 0, 8, 0, 0};
    private static final int[] anIntArray484 = {1, 1, 0, 0, 0, 8, 0, 0, 8};
    private static final int[] anIntArray485 = {41, 39248, 41, 4643, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41,
            43086, 41, 41, 41, 41, 41, 41, 41, 8602, 41, 28992, 41, 41, 41, 41, 41, 5056, 41, 41, 41, 7079, 41, 41, 41,
            41, 41, 41, 41, 41, 41, 41, 3131, 41, 41, 41};
    public static boolean lowMem = true;
    public static int clickedTileX = -1;
    public static int clickedTileY = -1;
    //runelite
    public static int hoverX = -1;
    public static int hoverY = -1;
    public static int skyboxColor;
    public static boolean pitchRelaxEnabled;
    public static final Set<RSTile> tilesToRemove = new HashSet<RSTile>();
    public static int tileUpdateCount;
    public static int currentRenderPlane;
    public static int cycle;
    public static int minTileX;
    public static int maxTileX;
    public static int minTileZ;
    public static int maxTileZ;
    public static int screenCenterX;
    public static int screenCenterZ;
    private static int anInt446;
    private static int plane__;
    private static int anInt448;
    private static int anInt449;
    private static int anInt450;
    private static int anInt451;
    private static int anInt452;
    private static int xCamPosTile;
    private static int yCamPosTile;
    public static int xCameraPos;
    public static int zCameraPos;
    public static int yCameraPos;
    public static int camUpDownY;
    public static int camUpDownX;
    public static int camLeftRightY;
    public static int camLeftRightX;
    private static InteractableObject[] aClass28Array462 = new InteractableObject[100];
    public static boolean aBoolean467;
    public static int clickX;
    public static int clickY;
    private static int[] sceneClusterCounts;
    private static CullingCluster[][] cullingClusters;
    private static int anInt475;
    private static Deque tileDeque = new Deque();
    public static boolean[][][][] tile_visibility_maps = new boolean[8][32][51][51];
    public static boolean[][] renderArea;
    private static int midX;
    private static int midY;
    private static int left;
    private static int top;
    private static int right;
    private static int bottom;

    static {
        amountOfCullingClusters = 4;
        sceneClusterCounts = new int[amountOfCullingClusters];
        cullingClusters = new CullingCluster[amountOfCullingClusters][500];
    }

    private final int maxY;
    private final int maxX;
    private final int maxZ;
    private final int[][][] heightMap;
    private final Tile[][][] tileArray;
    private final InteractableObject[] interactableObjectCache;
    private final int[][][] anIntArrayArrayArray445;
    private final int[] anIntArray486;
    private final int[] anIntArray487;
    private final int[][] tileSHapePoints = {new int[16], {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1}, {1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1}, {0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0}, {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1},
            {1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1}};
    private final int[][] tileShapeIndices = {{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15},
            {12, 8, 4, 0, 13, 9, 5, 1, 14, 10, 6, 2, 15, 11, 7, 3},
            {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0},
            {3, 7, 11, 15, 2, 6, 10, 14, 1, 5, 9, 13, 0, 4, 8, 12}};
    /*
     * public void drawTileMinimap(int pixels[], int ptr, int z, int x, int y) { int
     * j = 512;// was parameter Tile tile_ = tileArray[z][x][y]; if (tile_ == null)
     * return; PlainTile tile = tile_.plainTile; if (tile != null) { int j1 =
     * tile.rgbColour; if (j1 == 0) return; for (int k1 = 0; k1 < 4; k1++) {
     * pixels[ptr] = j1; pixels[ptr + 1] = j1; pixels[ptr + 2] = j1; pixels[ptr + 3]
     * = j1; ptr += j; }
     *
     * return; } ShapedTile shapedTile = tile_.shapedTile; if (shapedTile == null)
     * return; int l1 = shapedTile.shape; int i2 = shapedTile.rotation; int j2 =
     * shapedTile.colourRGB; int k2 = shapedTile.colourRGBA; int ai1[] =
     * tileSHapePoints[l1]; int ai2[] = tileShapeIndices[i2]; int l2 = 0; if (j2 !=
     * 0) { for (int i3 = 0; i3 < 4; i3++) { pixels[ptr] = ai1[ai2[l2++]] != 0 ? k2
     * : j2; pixels[ptr + 1] = ai1[ai2[l2++]] != 0 ? k2 : j2; pixels[ptr + 2] =
     * ai1[ai2[l2++]] != 0 ? k2 : j2; pixels[ptr + 3] = ai1[ai2[l2++]] != 0 ? k2 :
     * j2; ptr += j; }
     *
     * return; } for (int j3 = 0; j3 < 4; j3++) { if (ai1[ai2[l2++]] != 0)
     * pixels[ptr] = k2; if (ai1[ai2[l2++]] != 0) pixels[ptr + 1] = k2; if
     * (ai1[ai2[l2++]] != 0) pixels[ptr + 2] = k2; if (ai1[ai2[l2++]] != 0)
     * pixels[ptr + 3] = k2; ptr += j; }
     *
     * }
     */
    public boolean hdMinimap = true;
    private int currentHL;
    private int amountOfInteractableObjects;
    private int anInt488;

    public static int[] tmpX = new int[6];
    public static int[] tmpY = new int[6];

    public static int roofRemovalMode = 0;

    public WorldController(int ai[][][]) {
        int height = 104;// was parameter
        int width = 104;// was parameter
        int depth = 4;// was parameter
        interactableObjectCache = new InteractableObject[5000];
        anIntArray486 = new int[10000];
        anIntArray487 = new int[10000];
        maxY = depth;
        maxX = width;
        maxZ = height;
        tileArray = new Tile[depth][width][height];
        anIntArrayArrayArray445 = new int[depth][width + 1][height + 1];
        heightMap = ai;
        initToNull();
    }

    public static void nullLoader() {
        aClass28Array462 = null;
        sceneClusterCounts = null;
        cullingClusters = null;
        tileDeque = null;
        tile_visibility_maps = null;
        renderArea = null;
    }

    public static void createCullingCluster(int id, int tileStartX, int worldEndZ, int tileEndX, int tileEndY,
                                            int worldStartZ, int tileStartY, int searchMask) {
        CullingCluster cluster = new CullingCluster();
        cluster.startXLoc = tileStartX / 128;
        cluster.tileEndX = tileEndX / 128;
        cluster.startYLoc = tileStartY / 128;
        cluster.tileEndY = tileEndY / 128;
        cluster.orientation = searchMask;
        cluster.startXPos = tileStartX;
        cluster.worldEndX = tileEndX;
        cluster.startYPos = tileStartY;
        cluster.endYPos = tileEndY;
        cluster.startZPos = worldStartZ;
        cluster.endZPos = worldEndZ;
        cullingClusters[id][sceneClusterCounts[id]++] = cluster;
    }

    public static void setupViewport(int minZ, int maxZ, int width, int height, int ai[]) {
        left = 0;
        top = 0;
        right = width;
        bottom = height;
        midX = width / 2;
        midY = height / 2;
        boolean isOnScreen[][][][] = new boolean[9][32][53][53];
        for (int yAngle = 128; yAngle <= 384; yAngle += 32) {
            for (int xAngle = 0; xAngle < 2048; xAngle += 64) {
                camUpDownY = Model.SINE[yAngle];
                camUpDownX = Model.COSINE[yAngle];
                camLeftRightY = Model.SINE[xAngle];
                camLeftRightX = Model.COSINE[xAngle];
                int l1 = (yAngle - 128) / 32;
                int j2 = xAngle / 64;
                for (int l2 = -26; l2 <= 26; l2++) {
                    for (int j3 = -26; j3 <= 26; j3++) {
                        int k3 = l2 * 128;
                        int i4 = j3 * 128;
                        boolean flag2 = false;
                        for (int k4 = -minZ; k4 <= maxZ; k4 += 128) {
                            if (!isOnScreen(ai[l1] + k4, i4, k3))
                                continue;
                            flag2 = true;
                            break;
                        }

                        isOnScreen[l1][j2][l2 + 25 + 1][j3 + 25 + 1] = flag2;
                    }

                }

            }

        }

        for (int k1 = 0; k1 < 8; k1++) {
            for (int i2 = 0; i2 < 32; i2++) {
                for (int k2 = -25; k2 < 25; k2++) {
                    for (int i3 = -25; i3 < 25; i3++) {
                        boolean flag1 = false;
                        label0:
                        for (int l3 = -1; l3 <= 1; l3++) {
                            for (int j4 = -1; j4 <= 1; j4++) {
                                if (isOnScreen[k1][i2][k2 + l3 + 25 + 1][i3 + j4 + 25 + 1])
                                    flag1 = true;
                                else if (isOnScreen[k1][(i2 + 1) % 31][k2 + l3 + 25 + 1][i3 + j4 + 25 + 1])
                                    flag1 = true;
                                else if (isOnScreen[k1 + 1][i2][k2 + l3 + 25 + 1][i3 + j4 + 25 + 1]) {
                                    flag1 = true;
                                } else {
                                    if (!isOnScreen[k1 + 1][(i2 + 1) % 31][k2 + l3 + 25 + 1][i3 + j4 + 25 + 1])
                                        continue;
                                    flag1 = true;
                                }
                                break label0;
                            }

                        }

                        tile_visibility_maps[k1][i2][k2 + 25][i3 + 25] = flag1;
                    }

                }

            }

        }

    }

    private static boolean isOnScreen(int z, int y, int x) {
        int l = y * camLeftRightY + x * camLeftRightX >> 16;
        int i1 = y * camLeftRightX - x * camLeftRightY >> 16;
        int dist = z * camUpDownY + i1 * camUpDownX >> 16;
        int k1 = z * camUpDownX - i1 * camUpDownY >> 16;
        if (dist < 50 || dist > 4000)
            return false;
        int l1 = midX + l * Rasterizer.fieldOfView / dist;
        int i2 = midY + k1 * Rasterizer.fieldOfView / dist;
        return l1 >= left && l1 <= right && i2 >= top && i2 <= bottom;
    }

    public WallObject fetchWallObject(int i, int j, int k) {
        Tile tile = tileArray[i][j][k];
        if (tile == null || tile.wallObject == null)
            return null;
        else
            return tile.wallObject;
    }

    public WallDecoration fetchWallDecoration(int i, int j, int l) {
        Tile tile = tileArray[i][j][l];
        if (tile == null || tile.wallDecoration == null)
            return null;
        else
            return tile.wallDecoration;
    }

    public InteractableObject fetchInteractableObject(int z, int x, int y) {
        Tile tile = tileArray[z][x][y];
        if (tile == null)
            return null;
        for (int l = 0; l < tile.entityCount; l++) {
            InteractableObject interactableObject = tile.gameObjects[l];
            if (interactableObject.xLocLow == x && interactableObject.yLocHigh == y) {
                return interactableObject;
            }
        }
        return null;
    }

    public GroundDecoration fetchGroundDecoration(int i, int j, int k) {
        Tile tile = tileArray[i][j][k];
        if (tile == null || tile.groundDecoration == null)
            return null;
        else
            return tile.groundDecoration;
    }

    public int fetchWallObjectNewUID(int i, int j, int k) {
        Tile tile = tileArray[i][j][k];
        if (tile == null || tile.wallObject == null)
            return 0;
        else
            return tile.wallObject.wallObjUID;
    }

    public int fetchWallDecorationNewUID(int i, int j, int l) {
        Tile tile = tileArray[i][j][l];
        if (tile == null || tile.wallDecoration == null)
            return 0;
        else
            return tile.wallDecoration.wallDecorUID;
    }

    public int fetchObjectMeshNewUID(int z, int x, int y) {
        Tile tile = tileArray[z][x][y];
        if (tile == null)
            return 0;
        for (int l = 0; l < tile.entityCount; l++) {
            InteractableObject interactableObject = tile.gameObjects[l];
            if (interactableObject.xLocLow == x && interactableObject.yLocHigh == y) {
                return interactableObject.interactiveObjUID;
            }
        }
        return 0;
    }

    public int fetchGroundDecorationNewUID(int i, int j, int k) {
        Tile tile = tileArray[i][j][k];
        if (tile == null || tile.groundDecoration == null)
            return 0;
        else
            return tile.groundDecoration.groundDecorUID;
    }

    public void initToNull() {
        for (int z = 0; z < maxY; z++) {
            for (int x = 0; x < maxX; x++) {
                for (int y = 0; y < maxZ; y++)
                    tileArray[z][x][y] = null;

            }

        }
        for (int l = 0; l < amountOfCullingClusters; l++) {
            for (int j1 = 0; j1 < sceneClusterCounts[l]; j1++)
                cullingClusters[l][j1] = null;

            sceneClusterCounts[l] = 0;
        }

        for (int k1 = 0; k1 < amountOfInteractableObjects; k1++)
            interactableObjectCache[k1] = null;

        amountOfInteractableObjects = 0;
        for (int l1 = 0; l1 < aClass28Array462.length; l1++)
            aClass28Array462[l1] = null;

    }

    public void initTiles(int hl) {
        currentHL = hl;
        for (int k = 0; k < maxX; k++) {
            for (int l = 0; l < maxZ; l++)
                if (tileArray[hl][k][l] == null)
                    tileArray[hl][k][l] = new Tile(hl, k, l);

        }

    }

    public void applyBridgeMode(int y, int x) {
        Tile tile = tileArray[0][x][y];
        for (int l = 0; l < 3; l++) {
            Tile tile_ = tileArray[l][x][y] = tileArray[l + 1][x][y];
            if (tile_ != null) {
                tile_.tileZ--;
                for (int entityPtr = 0; entityPtr < tile_.entityCount; entityPtr++) {
                    InteractableObject iObject = tile_.gameObjects[entityPtr];
                    if ((iObject.uid >> 29 & 3) == 2 && iObject.xLocLow == x && iObject.yLocHigh == y)
                        iObject.zLoc--;
                }

            }
        }
        if (tileArray[0][x][y] == null)
            tileArray[0][x][y] = new Tile(0, x, y);
        tileArray[0][x][y].tileBelowThisTile = tile;
        tileArray[3][x][y] = null;
    }

    public void setVisiblePlanesFor(int z, int x, int y, int logicHeight) {
        Tile tile = tileArray[z][x][y];
        if (tile != null) {
            tileArray[z][x][y].logicHeight = logicHeight;
        }
    }

    public void method279(int i, int j, int k, int l, int i1, int overlaytex,
                          int underlaytex, int k1, int l1, int i2, int j2, int k2, int l2,
                          int i3, int j3, int k3, int l3, int i4, int j4, int k4, int l4,
                          boolean tex) {
        if (l == 0) {
            PlainTile class43 = new PlainTile(k2, l2, i3, j3, underlaytex, k4, false, tex);

            for (int i5 = i; i5 >= 0; i5--) {
                if (tileArray[i5][j][k] == null) {
                    tileArray[i5][j][k] = new Tile(i5, j, k);
                }
            }

            tileArray[i][j][k].plainTile = class43;
            return;
        }

        if (l == 1) {
            PlainTile class43_1 = new PlainTile(k3, l3, i4, j4, overlaytex, l4,
                    k1 == l1 && k1 == i2 && k1 == j2, tex);

            for (int j5 = i; j5 >= 0; j5--) {
                if (tileArray[j5][j][k] == null) {
                    tileArray[j5][j][k] = new Tile(j5, j, k);
                }
            }

            tileArray[i][j][k].plainTile = class43_1;
            return;
        }

        ShapedTile class40 = new ShapedTile(k, k3, j3, i2, overlaytex, underlaytex,
                i4, i1, k2, k4, i3, j2, l1, k1, l, j4, l3, l2, j, l4, tex);

        for (int k5 = i; k5 >= 0; k5--) {
            if (tileArray[k5][j][k] == null) {
                tileArray[k5][j][k] = new Tile(k5, j, k);
            }
        }

        tileArray[i][j][k].shapedTile = class40;
    }

    public void addGroundDecoration(int plane, int zPos, int yPos, Animable animable, byte byte0, int uid, int xPos,
                                    int groundDecorUID) {
        if (animable == null)
            return;
        GroundDecoration decoration = new GroundDecoration();
        decoration.node = animable;
        decoration.groundDecorUID = groundDecorUID;
        decoration.xPos = xPos * 128 + 64;
        decoration.yPos = yPos * 128 + 64;
        decoration.zPos = zPos;
        decoration.uid = uid;
        decoration.objConfig = byte0;
        if (tileArray[plane][xPos][yPos] == null)
            tileArray[plane][xPos][yPos] = new Tile(plane, xPos, yPos);
        tileArray[plane][xPos][yPos].groundDecoration = decoration;
    }

    public void addGroundItemTile(int xPos, int uid, Animable secondItem, int zPos, Animable thirdItem,
                                  Animable firstItem, int plane, int yPos) {
        GroundItem groundItem = new GroundItem();
        groundItem.firstGroundItem = firstItem;
        groundItem.xPos = xPos * 128 + 64;
        groundItem.yPos = yPos * 128 + 64;
        groundItem.zPos = zPos;
        groundItem.uid = uid;
        groundItem.secondGroundItem = secondItem;
        groundItem.thirdGroundItem = thirdItem;
        int isHighestPriority = 0;
        Tile tile = tileArray[plane][xPos][yPos];
        if (tile != null) {
            for (int k1 = 0; k1 < tile.entityCount; k1++)
                if (tile.gameObjects[k1].renderable instanceof Model) {
                    int tempInt = ((Model) tile.gameObjects[k1].renderable).myPriority;
                    if (tempInt > isHighestPriority)
                        isHighestPriority = tempInt;
                }

        }
        groundItem.topItem = isHighestPriority;
        if (tileArray[plane][xPos][yPos] == null)
            tileArray[plane][xPos][yPos] = new Tile(plane, xPos, yPos);
        tileArray[plane][xPos][yPos].groundItemTile = groundItem;
    }

    public void addWallObject(int orientation, Animable node, int uid, int yPos, byte objConfig, int xPos,
                              Animable node2, int zPos, int orientation_2, int plane, int wallObjUID) {
        if (node == null && node2 == null)
            return;
        WallObject wallObject = new WallObject();
        wallObject.uid = uid;
        wallObject.objConfig = objConfig;
        wallObject.xPos = xPos * 128 + 64;
        wallObject.yPos = yPos * 128 + 64;
        wallObject.tileHeights = zPos;
        wallObject.node1 = node;
        wallObject.node2 = node2;
        wallObject.wallObjUID = wallObjUID;
        wallObject.orientation = orientation;
        wallObject.orientation1 = orientation_2;
        for (int zPtr = plane; zPtr >= 0; zPtr--)
            if (tileArray[zPtr][xPos][yPos] == null) {
                tileArray[zPtr][xPos][yPos] = new Tile(zPtr, xPos, yPos);
                wallObject.zLoc = zPtr;
            }

        tileArray[plane][xPos][yPos].wallObject = wallObject;
    }

    public void addWallDecoration(int uid, int yPos, int rotation, int plane, int xOff, int zPos, Animable node,
                                  int xPos, byte config, int yOff, int configBits, int wallDecorUID) {
        if (node == null)
            return;
        WallDecoration dec = new WallDecoration();
        dec.uid = uid;
        dec.objConfig = config;
        dec.xPos = xPos * 128 + 64 + xOff;
        dec.yPos = yPos * 128 + 64 + yOff;
        dec.zPos = zPos;
        dec.node = node;
        dec.wallDecorUID = wallDecorUID;
        dec.configurationBits = configBits;
        dec.rotation = rotation;
        for (int zPtr = plane; zPtr >= 0; zPtr--)
            if (tileArray[zPtr][xPos][yPos] == null)
                tileArray[zPtr][xPos][yPos] = new Tile(zPtr, xPos, yPos);

        tileArray[plane][xPos][yPos].wallDecoration = dec;
    }

    public boolean addInteractableEntity(int ui, byte config, int worldZ, int tileBottom, Animable node, int tileRight,
                                         int z, int rotation, int tileTop, int tileLeft, int interactiveUID) {
        if (node == null) {
            return true;
        } else {
            int worldX = tileLeft * 128 + 64 * tileRight;
            int worldY = tileTop * 128 + 64 * tileBottom;
            return addEntity(z, tileLeft, tileTop, tileRight, tileBottom, worldX, worldY, worldZ, node, rotation, false,
                    ui, config, interactiveUID);
        }
    }

    public boolean addMutipleTileEntity(int z, int rotation, int worldZ, int ui, int worldY, int j1, int worldX,
                                        Animable nodeToAdd, boolean flag) {
        if (nodeToAdd == null)
            return true;
        if(!Client.instance.addEntityMarker(worldX, worldY,nodeToAdd)) {
            return true;
        }
        int tileLeft = worldX - j1;
        int tileTop = worldY - j1;
        int tileRight = worldX + j1;
        int tileBottom = worldY + j1;
        if (flag) {
            if (rotation > 640 && rotation < 1408)
                tileBottom += 128;
            if (rotation > 1152 && rotation < 1920)
                tileRight += 128;
            if (rotation > 1664 || rotation < 384)
                tileTop -= 128;
            if (rotation > 128 && rotation < 896)
                tileLeft -= 128;
        }
        tileLeft /= 128;
        tileTop /= 128;
        tileRight /= 128;
        tileBottom /= 128;
        return addEntity(z, tileLeft, tileTop, (tileRight - tileLeft) + 1, (tileBottom - tileTop) + 1, worldX, worldY,
                worldZ, nodeToAdd, rotation, true, ui, (byte) 0, 0);
    }

    public boolean addSingleTileEntity(int z, int worldY, Animable node, int rotation, int tileBottom, int worldX,
                                       int worldZ, int tileLeft, int tileRight, int ui, int tileTop) {
        return node == null || addEntity(z, tileLeft, tileTop, (tileRight - tileLeft) + 1, (tileBottom - tileTop) + 1,
                worldX, worldY, worldZ, node, rotation, true, ui, (byte) 0, 0);
    }

    private boolean addEntity(int z, int tileLeft, int tileTop, int tileRight, int tileBottom, int worldX, int worldY,
                              int worldZ, Animable node, int rotation, boolean flag, int ui, byte objConf, int interactiveObjUID) {

        if(!Client.instance.addEntityMarker(worldX, worldY,node)) {
            return true;
        }
        /**
         * Max entities on coord is 5 i guess
         */
        for (int _x = tileLeft; _x < tileLeft + tileRight; _x++) {
            for (int _y = tileTop; _y < tileTop + tileBottom; _y++) {
                if (_x < 0 || _y < 0 || _x >= maxX || _y >= maxZ)
                    return false;
                Tile tile = tileArray[z][_x][_y];
                if (tile != null && tile.entityCount >= 5)
                    return false;
            }

        }

        InteractableObject io = new InteractableObject();
        io.uid = ui;
        io.mask = objConf;
        io.zLoc = z;
        io.xPos = worldX;
        io.yPos = worldY;
        io.interactiveObjUID = interactiveObjUID;
        io.tileHeight = worldZ;
        io.renderable = node;
        io.turnValue = rotation;
        io.xLocLow = tileLeft;
        io.yLocHigh = tileTop;
        io.xLocHigh = (tileLeft + tileRight) - 1;
        io.yLocLow = (tileTop + tileBottom) - 1;
        for (int x = tileLeft; x < tileLeft + tileRight; x++) {
            for (int y = tileTop; y < tileTop + tileBottom; y++) {
                int position = 0;
                if (x > tileLeft)
                    position++;
                if (x < (tileLeft + tileRight) - 1)
                    position += 4;
                if (y > tileTop)
                    position += 8;
                if (y < (tileTop + tileBottom) - 1)
                    position += 2;
                for (int zPtr = z; zPtr >= 0; zPtr--)
                    if (tileArray[zPtr][x][y] == null)
                        tileArray[zPtr][x][y] = new Tile(zPtr, x, y);

                Tile tile = tileArray[z][x][y];
                tile.gameObjects[tile.entityCount] = io;
                tile.tiledObjectMasks[tile.entityCount] = position;
                tile.anInt1320 |= position;
                tile.entityCount++;
            }

        }

        if (flag)
            interactableObjectCache[amountOfInteractableObjects++] = io;
        return true;
    }

    public void clearInteractableObjects() {
        for (int i = 0; i < amountOfInteractableObjects; i++) {
            InteractableObject iObject = interactableObjectCache[i];
            updateObjectEntities(iObject);
            interactableObjectCache[i] = null;
        }

        amountOfInteractableObjects = 0;
    }

    private void updateObjectEntities(InteractableObject iObject) {
        for (int j = iObject.xLocLow; j <= iObject.xLocHigh; j++) {
            for (int k = iObject.yLocHigh; k <= iObject.yLocLow; k++) {
                Tile tile = tileArray[iObject.zLoc][j][k];
                if (tile != null) {
                    for (int l = 0; l < tile.entityCount; l++) {
                        if (tile.gameObjects[l] != iObject)
                            continue;
                        tile.entityCount--;
                        for (int entityPtr = l; entityPtr < tile.entityCount; entityPtr++) {
                            tile.gameObjects[entityPtr] = tile.gameObjects[entityPtr + 1];
                            tile.tiledObjectMasks[entityPtr] = tile.tiledObjectMasks[entityPtr + 1];
                        }

                        tile.gameObjects[tile.entityCount] = null;
                        break;
                    }

                    tile.anInt1320 = 0;
                    for (int j1 = 0; j1 < tile.entityCount; j1++)
                        tile.anInt1320 |= tile.tiledObjectMasks[j1];

                }
            }

        }

    }

    public void moveWallDec(int y, int moveAmt, int x, int z) {
        Tile tile = tileArray[z][x][y];
        if (tile == null)
            return;
        WallDecoration wallDec = tile.wallDecoration;
        if (wallDec != null) {
            int xCoord = x * 128 + 64;
            int yCoord = y * 128 + 64;
            wallDec.xPos = xCoord + ((wallDec.xPos - xCoord) * moveAmt) / 16;
            wallDec.yPos = yCoord + ((wallDec.yPos - yCoord) * moveAmt) / 16;
        }
    }

    public void removeWallObject(int x, int y, int z) {
        Tile tile = tileArray[y][x][z];
        if (tile != null) {
            tile.wallObject = null;
        }
    }

    public void removeWallDecoration(int y, int z, int x) {
        Tile tile = tileArray[z][x][y];
        if (tile != null) {
            tile.wallDecoration = null;
        }
    }

    public void removeInteractableObject(int z, int x, int y) {
        Tile tile = tileArray[z][x][y];
        if (tile == null)
            return;
        for (int i = 0; i < tile.entityCount; i++) {
            InteractableObject subObject = tile.gameObjects[i];
            if ((subObject.uid >> 29 & 3) == 2 && subObject.xLocLow == x && subObject.yLocHigh == y) {
                updateObjectEntities(subObject);
                return;
            }
        }

    }

    public void removeGroundDecoration(int z, int y, int x) {
        Tile tile = tileArray[z][x][y];
        if (tile == null)
            return;
        tile.groundDecoration = null;
    }

    public void removeGroundItemFromTIle(int z, int x, int y) {
        Tile tile = tileArray[z][x][y];
        if (tile != null) {
            tile.groundItemTile = null;
        }
    }

    public WallObject getWallObject(int z, int x, int y) {
        Tile tile = tileArray[z][x][y];
        if (tile == null)
            return null;
        else
            return tile.wallObject;
    }

    public WallDecoration getWallDecoration(int x, int y, int z) {
        Tile tile = tileArray[z][x][y];
        if (tile == null)
            return null;
        else
            return tile.wallDecoration;
    }

    public InteractableObject getInteractableObject(int x, int y, int z) {
        Tile tile = tileArray[z][x][y];
        if (tile == null)
            return null;
        for (int l = 0; l < tile.entityCount; l++) {
            InteractableObject subObject = tile.gameObjects[l];
            if ((subObject.uid >> 29 & 3) == 2 && subObject.xLocLow == x && subObject.yLocHigh == y)
                return subObject;
        }
        return null;
    }

    public GroundDecoration getGroundDecoration(int y, int x, int z) {
        Tile tile = tileArray[z][x][y];
        if (tile == null || tile.groundDecoration == null)
            return null;
        else
            return tile.groundDecoration;
    }

    public int getWallObjectUID(int z, int x, int y) {
        Tile tile = tileArray[z][x][y];
        if (tile == null || tile.wallObject == null)
            return 0;
        else
            return tile.wallObject.uid;
    }

    public int getWallDecorationUID(int z, int x, int y) {
        Tile tile = tileArray[z][x][y];
        if (tile == null || tile.wallDecoration == null)
            return 0;
        else
            return tile.wallDecoration.uid;
    }

    public int getInteractableObjectUID(int plane, int x, int y) {
        Tile tile = tileArray[plane][x][y];
        if (tile == null)
            return 0;
        for (int i = 0; i < tile.entityCount; i++) {
            InteractableObject iObject = tile.gameObjects[i];
            if (iObject.xLocLow == x && iObject.yLocHigh == y)
                return iObject.uid;
        }

        return 0;
    }

    public int getGroundDecorationUID(int z, int x, int y) {
        Tile tile = tileArray[z][x][y];
        if (tile == null || tile.groundDecoration == null)
            return 0;
        else
            return tile.groundDecoration.uid;
    }

    public int getIDTagForXYZ(int z, int x, int y, int uidMatch) {
        Tile tile = tileArray[z][x][y];
        if (tile == null)
            return -1;
        if (tile.wallObject != null && tile.wallObject.uid == uidMatch)
            return tile.wallObject.objConfig & 0xff;
        if (tile.wallDecoration != null && tile.wallDecoration.uid == uidMatch)
            return tile.wallDecoration.objConfig & 0xff;
        if (tile.groundDecoration != null && tile.groundDecoration.uid == uidMatch)
            return tile.groundDecoration.objConfig & 0xff;
        for (int entityPtr = 0; entityPtr < tile.entityCount; entityPtr++)
            if (tile.gameObjects[entityPtr].uid == uidMatch)
                return tile.gameObjects[entityPtr].mask & 0xff;

        return -1;
    }

    public void shadeModels(int i, int k, int i1) {
        int j = 100;
        int l = 5500;
        int j1 = (int) Math.sqrt(k * k + i * i + i1 * i1);
        int k1 = l >> 4;
        for (int l1 = 0; l1 < maxY; l1++) {
            for (int i2 = 0; i2 < maxX; i2++) {
                for (int j2 = 0; j2 < maxZ; j2++) {
                    Tile class30_sub3 = tileArray[l1][i2][j2];
                    if (class30_sub3 != null) {
                        WallObject class10 = class30_sub3.wallObject;
                        if (class10 != null && class10.node1 != null && class10.node1.vertexNormals != null) {
                            mergeModels(l1, 1, 1, i2, j2, (Model) class10.node1);
                            if (class10.node2 != null && class10.node2.vertexNormals != null) {
                                mergeModels(l1, 1, 1, i2, j2, (Model) class10.node2);
                                renderModels((Model) class10.node1, (Model) class10.node2, 0, 0, 0, false);
                                ((Model) class10.node2).method480(j, k1, k, i, i1);
                            }
                            ((Model) class10.node1).method480(j, k1, k, i, i1);
                        }
                        for (int k2 = 0; k2 < class30_sub3.entityCount; k2++) {
                            InteractableObject class28 = class30_sub3.gameObjects[k2];
                            if (class28 != null && class28.renderable != null && class28.renderable.vertexNormals != null) {
                                mergeModels(l1, (class28.xLocHigh - class28.xLocLow) + 1,
                                        (class28.yLocLow - class28.yLocHigh) + 1, i2, j2, (Model) class28.renderable);
                                ((Model) class28.renderable).method480(j, k1, k, i, i1);
                            }
                        }

                        GroundDecoration class49 = class30_sub3.groundDecoration;
                        if (class49 != null && class49.node.vertexNormals != null) {
                            renderGrounDec(i2, l1, (Model) class49.node, j2);
                            ((Model) class49.node).method480(j, k1, k, i, i1);
                        }
                    }
                }

            }

        }

    }

    private void renderGrounDec(int i, int j, Model model, int k) {
        if (i < maxX) {
            Tile class30_sub3 = tileArray[j][i + 1][k];
            if (class30_sub3 != null && class30_sub3.groundDecoration != null
                    && class30_sub3.groundDecoration.node.vertexNormals != null)
                renderModels(model, (Model) class30_sub3.groundDecoration.node, 128, 0, 0, true);
        }
        if (k < maxX) {
            Tile class30_sub3_1 = tileArray[j][i][k + 1];
            if (class30_sub3_1 != null && class30_sub3_1.groundDecoration != null
                    && class30_sub3_1.groundDecoration.node.vertexNormals != null)
                renderModels(model, (Model) class30_sub3_1.groundDecoration.node, 0, 0, 128, true);
        }
        if (i < maxX && k < maxZ) {
            Tile class30_sub3_2 = tileArray[j][i + 1][k + 1];
            if (class30_sub3_2 != null && class30_sub3_2.groundDecoration != null
                    && class30_sub3_2.groundDecoration.node.vertexNormals != null)
                renderModels(model, (Model) class30_sub3_2.groundDecoration.node, 128, 0, 128, true);
        }
        if (i < maxX && k > 0) {
            Tile class30_sub3_3 = tileArray[j][i + 1][k - 1];
            if (class30_sub3_3 != null && class30_sub3_3.groundDecoration != null
                    && class30_sub3_3.groundDecoration.node.vertexNormals != null)
                renderModels(model, (Model) class30_sub3_3.groundDecoration.node, 128, 0, -128, true);
        }
    }

    private void mergeModels(int z, int j, int k, int x, int y, Model model) {
        boolean flag = true;
        int j1 = x;
        int k1 = x + j;
        int l1 = y - 1;
        int i2 = y + k;
        for (int j2 = z; j2 <= z + 1; j2++)
            if (j2 != maxY) {
                for (int k2 = j1; k2 <= k1; k2++)
                    if (k2 >= 0 && k2 < maxX) {
                        for (int l2 = l1; l2 <= i2; l2++)
                            if (l2 >= 0 && l2 < maxZ && (!flag || k2 >= k1 || l2 >= i2 || l2 < y && k2 != x)) {
                                Tile class30_sub3 = tileArray[j2][k2][l2];
                                if (class30_sub3 != null) {
                                    int i3 = (heightMap[j2][k2][l2] + heightMap[j2][k2 + 1][l2]
                                            + heightMap[j2][k2][l2 + 1] + heightMap[j2][k2 + 1][l2 + 1]) / 4
                                            - (heightMap[z][x][y] + heightMap[z][x + 1][y] + heightMap[z][x][y + 1]
                                            + heightMap[z][x + 1][y + 1]) / 4;
                                    WallObject class10 = class30_sub3.wallObject;
                                    if (class10 != null && class10.node1 != null && class10.node1.vertexNormals != null)
                                        renderModels(model, (Model) class10.node1, (k2 - x) * 128 + (1 - j) * 64, i3,
                                                (l2 - y) * 128 + (1 - k) * 64, flag);
                                    if (class10 != null && class10.node2 != null && class10.node2.vertexNormals != null)
                                        renderModels(model, (Model) class10.node2, (k2 - x) * 128 + (1 - j) * 64, i3,
                                                (l2 - y) * 128 + (1 - k) * 64, flag);
                                    for (int j3 = 0; j3 < class30_sub3.entityCount; j3++) {
                                        InteractableObject class28 = class30_sub3.gameObjects[j3];
                                        if (class28 != null && class28.renderable != null
                                                && class28.renderable.vertexNormals != null) {
                                            int k3 = (class28.xLocHigh - class28.xLocLow) + 1;
                                            int l3 = (class28.yLocLow - class28.yLocHigh) + 1;
                                            renderModels(model, (Model) class28.renderable,
                                                    (class28.xLocLow - x) * 128 + (k3 - j) * 64, i3,
                                                    (class28.yLocHigh - y) * 128 + (l3 - k) * 64, flag);
                                        }
                                    }

                                }
                            }

                    }

                j1--;
                flag = false;
            }

    }

    private void renderModels(Model model, Model model_1, int i, int j, int k, boolean flag) {
        anInt488++;
        int l = 0;
        int ai[] = model_1.verticesX;
        int amtOfVertices = model_1.verticesCount;
        for (int verticeId = 0; verticeId < model.verticesCount; verticeId++) {
            VertexNormal vertexNormal = model.vertexNormals[verticeId];
            VertexNormal vertexNormalOff = model.vertexNormalOffset[verticeId];
            if (vertexNormalOff.anInt605 != 0) {
                int vertY = model.verticesY[verticeId] - j;
                if (vertY <= model_1.maxY) {
                    int vertX = model.verticesX[verticeId] - i;
                    if (vertX >= model_1.anInt1646 && vertX <= model_1.anInt1647) {
                        int vertZ = model.verticesZ[verticeId] - k;
                        if (vertZ >= model_1.anInt1649 && vertZ <= model_1.anInt1648) {
                            for (int vertId_1 = 0; vertId_1 < amtOfVertices; vertId_1++) {
                                VertexNormal class33_2 = model_1.vertexNormals[vertId_1];
                                VertexNormal class33_3 = model_1.vertexNormalOffset[vertId_1];
                                if (vertX == ai[vertId_1] && vertZ == model_1.verticesZ[vertId_1]
                                        && vertY == model_1.verticesY[vertId_1] && class33_3.anInt605 != 0) {
                                    vertexNormal.anInt602 += class33_3.anInt602;
                                    vertexNormal.anInt603 += class33_3.anInt603;
                                    vertexNormal.anInt604 += class33_3.anInt604;
                                    vertexNormal.anInt605 += class33_3.anInt605;
                                    class33_2.anInt602 += vertexNormalOff.anInt602;
                                    class33_2.anInt603 += vertexNormalOff.anInt603;
                                    class33_2.anInt604 += vertexNormalOff.anInt604;
                                    class33_2.anInt605 += vertexNormalOff.anInt605;
                                    l++;
                                    anIntArray486[verticeId] = anInt488;
                                    anIntArray487[vertId_1] = anInt488;
                                }
                            }

                        }
                    }
                }
            }
        }

        if (l < 3 || !flag)
            return;
        for (int k1 = 0; k1 < model.numberOfTriangleFaces; k1++)
            if (anIntArray486[model.face_a[k1]] == anInt488 && anIntArray486[model.face_b[k1]] == anInt488
                    && anIntArray486[model.face_c[k1]] == anInt488)
                model.face_render_type[k1] = -1;

        for (int l1 = 0; l1 < model_1.numberOfTriangleFaces; l1++)
            if (anIntArray487[model_1.face_a[l1]] == anInt488 && anIntArray487[model_1.face_b[l1]] == anInt488
                    && anIntArray487[model_1.face_c[l1]] == anInt488)
                model_1.face_render_type[l1] = -1;

    }

    public void drawTileMinimap(int pixels[], int pixelOffset, int z, int x, int y) {
        if (!Client.instance.isHdMinimapEnabled())
        {
            drawTileMinimapSD(pixels, pixelOffset, z, x, y);
            return;
        }
        RSTile tile = getTiles()[z][x][y];
        if (tile == null)
        {
            return;
        }
        SceneTilePaint sceneTilePaint = tile.getSceneTilePaint();
        if (sceneTilePaint != null)
        {
            int rgb = sceneTilePaint.getRBG();
            if (sceneTilePaint.getSwColor() != INVALID_HSL_COLOR)
            {
                // hue and saturation
                int hs = sceneTilePaint.getSwColor() & ~0x7F;
                // I know this looks dumb (and it probably is) but I don't feel like hunting down the problem
                int seLightness = sceneTilePaint.getNwColor() & 0x7F;
                int neLightness = sceneTilePaint.getNeColor() & 0x7F;
                int southDeltaLightness = (sceneTilePaint.getSwColor() & 0x7F) - seLightness;
                int northDeltaLightness = (sceneTilePaint.getSeColor() & 0x7F) - neLightness;
                seLightness <<= 2;
                neLightness <<= 2;
                for (int i = 0; i < 4; i++)
                {
                    if (sceneTilePaint.getTexture() == -1)
                    {
                        pixels[pixelOffset] = Rasterizer.hsl2rgb[hs | seLightness >> 2];
                        pixels[pixelOffset + 1] = Rasterizer.hsl2rgb[hs | seLightness * 3 + neLightness >> 4];
                        pixels[pixelOffset + 2] = Rasterizer.hsl2rgb[hs | seLightness + neLightness >> 3];
                        pixels[pixelOffset + 3] = Rasterizer.hsl2rgb[hs | seLightness + neLightness * 3 >> 4];
                    }
                    else
                    {
                        int lig = 0xFF - ((seLightness >> 1) * (seLightness >> 1) >> 8);
                        pixels[pixelOffset] = ((rgb & 0xFF00FF) * lig & ~0xFF00FF) + ((rgb & 0xFF00) * lig & 0xFF0000) >> 8;
                        lig = 0xFF - ((seLightness * 3 + neLightness >> 3) * (seLightness * 3 + neLightness >> 3) >> 8);
                        pixels[pixelOffset + 1] = ((rgb & 0xFF00FF) * lig & ~0xFF00FF) + ((rgb & 0xFF00) * lig & 0xFF0000) >> 8;
                        lig = 0xFF - ((seLightness + neLightness >> 2) * (seLightness + neLightness >> 2) >> 8);
                        pixels[pixelOffset + 2] = ((rgb & 0xFF00FF) * lig & ~0xFF00FF) + ((rgb & 0xFF00) * lig & 0xFF0000) >> 8;
                        lig = 0xFF - ((seLightness + neLightness * 3 >> 3) * (seLightness + neLightness * 3 >> 3) >> 8);
                        pixels[pixelOffset + 3] = ((rgb & 0xFF00FF) * lig & ~0xFF00FF) + ((rgb & 0xFF00) * lig & 0xFF0000) >> 8;
                    }
                    seLightness += southDeltaLightness;
                    neLightness += northDeltaLightness;

                    pixelOffset += 512;
                }
            }
            else if (rgb != 0)
            {
                for (int i = 0; i < 4; i++)
                {
                    pixels[pixelOffset] = rgb;
                    pixels[pixelOffset + 1] = rgb;
                    pixels[pixelOffset + 2] = rgb;
                    pixels[pixelOffset + 3] = rgb;
                    pixelOffset += 512;
                }
            }
            return;
        }

        SceneTileModel sceneTileModel = tile.getSceneTileModel();
        if (sceneTileModel != null)
        {
            int shape = sceneTileModel.getShape();
            int rotation = sceneTileModel.getRotation();
            int overlayRgb = sceneTileModel.getModelOverlay();
            int underlayRgb = sceneTileModel.getModelUnderlay();
            int[] points = getTileShape2D()[shape];
            int[] indices = getTileRotation2D()[rotation];

            int shapeOffset = 0;

            if (sceneTileModel.getOverlaySwColor() != INVALID_HSL_COLOR)
            {
                // hue and saturation
                int hs = sceneTileModel.getOverlaySwColor() & ~0x7F;
                int seLightness = sceneTileModel.getOverlaySeColor() & 0x7F;
                int neLightness = sceneTileModel.getOverlayNeColor() & 0x7F;
                int southDeltaLightness = (sceneTileModel.getOverlaySwColor() & 0x7F) - seLightness;
                int northDeltaLightness = (sceneTileModel.getOverlayNwColor() & 0x7F) - neLightness;
                seLightness <<= 2;
                neLightness <<= 2;
                for (int i = 0; i < 4; i++)
                {
                    if (sceneTileModel.getTriangleTextureId() == null)
                    {
                        if (points[indices[shapeOffset++]] != 0)
                        {
                            pixels[pixelOffset] = Rasterizer.hsl2rgb[hs | (seLightness >> 2)];
                        }
                        if (points[indices[shapeOffset++]] != 0)
                        {
                            pixels[pixelOffset + 1] = Rasterizer.hsl2rgb[hs | (seLightness * 3 + neLightness >> 4)];
                        }
                        if (points[indices[shapeOffset++]] != 0)
                        {
                            pixels[pixelOffset + 2] = Rasterizer.hsl2rgb[hs | (seLightness + neLightness >> 3)];
                        }
                        if (points[indices[shapeOffset++]] != 0)
                        {
                            pixels[pixelOffset + 3] = Rasterizer.hsl2rgb[hs | (seLightness + neLightness * 3 >> 4)];
                        }
                    }
                    else
                    {
                        if (points[indices[shapeOffset++]] != 0)
                        {
                            int lig = 0xFF - ((seLightness >> 1) * (seLightness >> 1) >> 8);
                            pixels[pixelOffset] = ((overlayRgb & 0xFF00FF) * lig & ~0xFF00FF) +
                                    ((overlayRgb & 0xFF00) * lig & 0xFF0000) >> 8;
                        }
                        if (points[indices[shapeOffset++]] != 0)
                        {
                            int lig = 0xFF - ((seLightness * 3 + neLightness >> 3) *
                                    (seLightness * 3 + neLightness >> 3) >> 8);
                            pixels[pixelOffset + 1] = ((overlayRgb & 0xFF00FF) * lig & ~0xFF00FF) +
                                    ((overlayRgb & 0xFF00) * lig & 0xFF0000) >> 8;
                        }
                        if (points[indices[shapeOffset++]] != 0)
                        {
                            int lig = 0xFF - ((seLightness + neLightness >> 2) *
                                    (seLightness + neLightness >> 2) >> 8);
                            pixels[pixelOffset + 2] = ((overlayRgb & 0xFF00FF) * lig & ~0xFF00FF) +
                                    ((overlayRgb & 0xFF00) * lig & 0xFF0000) >> 8;
                        }
                        if (points[indices[shapeOffset++]] != 0)
                        {
                            int lig = 0xFF - ((seLightness + neLightness * 3 >> 3) *
                                    (seLightness + neLightness * 3 >> 3) >> 8);
                            pixels[pixelOffset + 3] = ((overlayRgb & 0xFF00FF) * lig & ~0xFF00FF) +
                                    ((overlayRgb & 0xFF00) * lig & 0xFF0000) >> 8;
                        }
                    }
                    seLightness += southDeltaLightness;
                    neLightness += northDeltaLightness;

                    pixelOffset += 512;
                }
                if (underlayRgb != 0 && sceneTileModel.getUnderlaySwColor() != INVALID_HSL_COLOR)
                {
                    pixelOffset -= 512 << 2;
                    shapeOffset -= 16;
                    hs = sceneTileModel.getUnderlaySwColor() & ~0x7F;
                    seLightness = sceneTileModel.getUnderlaySeColor() & 0x7F;
                    neLightness = sceneTileModel.getUnderlayNeColor() & 0x7F;
                    southDeltaLightness = (sceneTileModel.getUnderlaySwColor() & 0x7F) - seLightness;
                    northDeltaLightness = (sceneTileModel.getUnderlayNwColor() & 0x7F) - neLightness;
                    seLightness <<= 2;
                    neLightness <<= 2;
                    for (int i = 0; i < 4; i++)
                    {
                        if (points[indices[shapeOffset++]] == 0)
                        {
                            pixels[pixelOffset] = Rasterizer.hsl2rgb[hs | (seLightness >> 2)];
                        }
                        if (points[indices[shapeOffset++]] == 0)
                        {
                            pixels[pixelOffset + 1] = Rasterizer.hsl2rgb[hs | (seLightness * 3 + neLightness >> 4)];
                        }
                        if (points[indices[shapeOffset++]] == 0)
                        {
                            pixels[pixelOffset + 2] = Rasterizer.hsl2rgb[hs | (seLightness + neLightness >> 3)];
                        }
                        if (points[indices[shapeOffset++]] == 0)
                        {
                            pixels[pixelOffset + 3] = Rasterizer.hsl2rgb[hs | (seLightness + neLightness * 3 >> 4)];
                        }
                        seLightness += southDeltaLightness;
                        neLightness += northDeltaLightness;

                        pixelOffset += 512;
                    }
                }
            }
            else if (underlayRgb != 0)
            {
                for (int i = 0; i < 4; i++)
                {
                    pixels[pixelOffset] = points[indices[shapeOffset++]] != 0 ? overlayRgb : underlayRgb;
                    pixels[pixelOffset + 1] =
                            points[indices[shapeOffset++]] != 0 ? overlayRgb : underlayRgb;
                    pixels[pixelOffset + 2] =
                            points[indices[shapeOffset++]] != 0 ? overlayRgb : underlayRgb;
                    pixels[pixelOffset + 3] =
                            points[indices[shapeOffset++]] != 0 ? overlayRgb : underlayRgb;
                    pixelOffset += 512;
                }
            }
            else
            {
                for (int i = 0; i < 4; i++)
                {
                    if (points[indices[shapeOffset++]] != 0)
                    {
                        pixels[pixelOffset] = overlayRgb;
                    }
                    if (points[indices[shapeOffset++]] != 0)
                    {
                        pixels[pixelOffset + 1] = overlayRgb;
                    }
                    if (points[indices[shapeOffset++]] != 0)
                    {
                        pixels[pixelOffset + 2] = overlayRgb;
                    }
                    if (points[indices[shapeOffset++]] != 0)
                    {
                        pixels[pixelOffset + 3] = overlayRgb;
                    }
                    pixelOffset += 512;
                }
            }
        }
    }

    public void drawTileMinimapSD(int pixels[], int pixelOffset, int z, int x, int y) {
        if (hdMinimap) {
            Tile class30_sub3 = tileArray[z][x][y];
            if (class30_sub3 == null) {
                return;
            }
            PlainTile class43 = class30_sub3.plainTile;
            if (class43 != null) {
                if (class43.northEastColor != 12345678 && class43.centerColor != 12345678) {
                    if (class43.colorRGB == 0) {
                        return;
                    }
                    int hs = class43.northEastColor & ~0x7f;
                    int l1 = class43.eastColor & 0x7f;
                    int l2 = class43.centerColor & 0x7f;
                    int l3 = (class43.northEastColor & 0x7f) - l1;
                    int l4 = (class43.northColor & 0x7f) - l2;
                    l1 <<= 2;
                    l2 <<= 2;
                    for (int k1 = 0; k1 < 4; k1++) {
                        if (!class43.textured) {
                            pixels[pixelOffset] = Rasterizer.hsl2rgb[hs | (l1 >> 2)];
                            pixels[pixelOffset + 1] = Rasterizer.hsl2rgb[hs | (l1 * 3 + l2 >> 4)];
                            pixels[pixelOffset + 2] = Rasterizer.hsl2rgb[hs | (l1 + l2 >> 3)];
                            pixels[pixelOffset + 3] = Rasterizer.hsl2rgb[hs | (l1 + l2 * 3 >> 4)];
                        } else {
                            int j1 = class43.colorRGB;
                            int lig = 0xff - ((l1 >> 1) * (l1 >> 1) >> 8);
                            pixels[pixelOffset] = ((j1 & 0xff00ff) * lig & ~0xff00ff) + ((j1 & 0xff00) * lig & 0xff0000) >> 8;
                            lig = 0xff - ((l1 * 3 + l2 >> 3) * (l1 * 3 + l2 >> 3) >> 8);
                            pixels[pixelOffset + 1] = ((j1 & 0xff00ff) * lig & ~0xff00ff) + ((j1 & 0xff00) * lig & 0xff0000) >> 8;
                            lig = 0xff - ((l1 + l2 >> 2) * (l1 + l2 >> 2) >> 8);
                            pixels[pixelOffset + 2] = ((j1 & 0xff00ff) * lig & ~0xff00ff) + ((j1 & 0xff00) * lig & 0xff0000) >> 8;
                            lig = 0xff - ((l1 + l2 * 3 >> 3) * (l1 + l2 * 3 >> 3) >> 8);
                            pixels[pixelOffset + 3] = ((j1 & 0xff00ff) * lig & ~0xff00ff) + ((j1 & 0xff00) * lig & 0xff0000) >> 8;
                        }
                        l1 += l3;
                        l2 += l4;
                        pixelOffset += 512;
                    }
                    return;
                }
                int mapColor = class43.colorRGB;
                if (mapColor == 0) {
                    return;
                }
                for (int k1 = 0; k1 < 4; k1++) {
                    pixels[pixelOffset] = mapColor;
                    pixels[pixelOffset + 1] = mapColor;
                    pixels[pixelOffset + 2] = mapColor;
                    pixels[pixelOffset + 3] = mapColor;
                    pixelOffset += 512;
                }
                return;
            }
            ShapedTile class40 = class30_sub3.shapedTile;
            if (class40 == null) {
                return;
            }
            int l1 = class40.shape;
            int i2 = class40.rotation;
            int j2 = class40.colourRGB;
            int k2 = class40.colourRGBA;
            int ai1[] = tileSHapePoints[l1];
            int ai2[] = tileShapeIndices[i2];
            int l2 = 0;
            if (class40.color62 != 12345678) {
                int hs1 = class40.color62 & ~0x7f;
                int l11 = class40.color92 & 0x7f;
                int l21 = class40.color82 & 0x7f;
                int l31 = (class40.color62 & 0x7f) - l11;
                int l41 = (class40.color72 & 0x7f) - l21;
                l11 <<= 2;
                l21 <<= 2;
                for (int k1 = 0; k1 < 4; k1++) {
                    if (!class40.textured) {
                        if (ai1[ai2[l2++]] != 0) {
                            pixels[pixelOffset] = Rasterizer.hsl2rgb[hs1 | (l11 >> 2)];
                        }
                        if (ai1[ai2[l2++]] != 0) {
                            pixels[pixelOffset + 1] = Rasterizer.hsl2rgb[hs1 | (l11 * 3 + l21 >> 4)];
                        }
                        if (ai1[ai2[l2++]] != 0) {
                            pixels[pixelOffset + 2] = Rasterizer.hsl2rgb[hs1 | (l11 + l21 >> 3)];
                        }
                        if (ai1[ai2[l2++]] != 0) {
                            pixels[pixelOffset + 3] = Rasterizer.hsl2rgb[hs1 | (l11 + l21 * 3 >> 4)];
                        }
                    } else {
                        int j1 = k2;
                        if (ai1[ai2[l2++]] != 0) {
                            int lig = 0xff - ((l11 >> 1) * (l11 >> 1) >> 8);
                            pixels[pixelOffset] = ((j1 & 0xff00ff) * lig & ~0xff00ff) + ((j1 & 0xff00) * lig & 0xff0000) >> 8;
                        }
                        if (ai1[ai2[l2++]] != 0) {
                            int lig = 0xff - ((l11 * 3 + l21 >> 3) * (l11 * 3 + l21 >> 3) >> 8);
                            pixels[pixelOffset + 1] = ((j1 & 0xff00ff) * lig & ~0xff00ff) + ((j1 & 0xff00) * lig & 0xff0000) >> 8;
                        }
                        if (ai1[ai2[l2++]] != 0) {
                            int lig = 0xff - ((l11 + l21 >> 2) * (l11 + l21 >> 2) >> 8);
                            pixels[pixelOffset + 2] = ((j1 & 0xff00ff) * lig & ~0xff00ff) + ((j1 & 0xff00) * lig & 0xff0000) >> 8;
                        }
                        if (ai1[ai2[l2++]] != 0) {
                            int lig = 0xff - ((l11 + l21 * 3 >> 3) * (l11 + l21 * 3 >> 3) >> 8);
                            pixels[pixelOffset + 3] = ((j1 & 0xff00ff) * lig & ~0xff00ff) + ((j1 & 0xff00) * lig & 0xff0000) >> 8;
                        }
                    }
                    l11 += l31;
                    l21 += l41;
                    pixelOffset += 512;
                }
                if (j2 != 0 && class40.color61 != 12345678) {
                    pixelOffset -= 512 << 2;
                    l2 -= 16;
                    hs1 = class40.color61 & ~0x7f;
                    l11 = class40.color91 & 0x7f;
                    l21 = class40.color81 & 0x7f;
                    l31 = (class40.color61 & 0x7f) - l11;
                    l41 = (class40.color71 & 0x7f) - l21;
                    l11 <<= 2;
                    l21 <<= 2;
                    for (int k1 = 0; k1 < 4; k1++) {
                        if (ai1[ai2[l2++]] == 0) {
                            pixels[pixelOffset] = Rasterizer.hsl2rgb[hs1 | (l11 >> 2)];
                        }
                        if (ai1[ai2[l2++]] == 0) {
                            pixels[pixelOffset + 1] = Rasterizer.hsl2rgb[hs1 | (l11 * 3 + l21 >> 4)];
                        }
                        if (ai1[ai2[l2++]] == 0) {
                            pixels[pixelOffset + 2] = Rasterizer.hsl2rgb[hs1 | (l11 + l21 >> 3)];
                        }
                        if (ai1[ai2[l2++]] == 0) {
                            pixels[pixelOffset + 3] = Rasterizer.hsl2rgb[hs1 | (l11 + l21 * 3 >> 4)];
                        }
                        l11 += l31;
                        l21 += l41;
                        pixelOffset += 512;
                    }
                }
                return;
            }
            if (j2 != 0) {
                for (int i3 = 0; i3 < 4; i3++) {
                    pixels[pixelOffset] = ai1[ai2[l2++]] != 0 ? k2 : j2;
                    pixels[pixelOffset + 1] = ai1[ai2[l2++]] != 0 ? k2 : j2;
                    pixels[pixelOffset + 2] = ai1[ai2[l2++]] != 0 ? k2 : j2;
                    pixels[pixelOffset + 3] = ai1[ai2[l2++]] != 0 ? k2 : j2;
                    pixelOffset += 512;
                }
                return;
            }
            for (int j3 = 0; j3 < 4; j3++) {
                if (ai1[ai2[l2++]] != 0) {
                    pixels[pixelOffset] = k2;
                }
                if (ai1[ai2[l2++]] != 0) {
                    pixels[pixelOffset + 1] = k2;
                }
                if (ai1[ai2[l2++]] != 0) {
                    pixels[pixelOffset + 2] = k2;
                }
                if (ai1[ai2[l2++]] != 0) {
                    pixels[pixelOffset + 3] = k2;
                }
                pixelOffset += 512;
            }
        } else {
            int j = 512;//was parameter
            Tile class30_sub3 = tileArray[z][x][y];
            if (class30_sub3 == null)
                return;
            PlainTile class43 = class30_sub3.plainTile;
            if (class43 != null) {
                int j1 = class43.colorRGB;
                if (j1 == 0)
                    return;
                for (int k1 = 0; k1 < 4; k1++) {
                    pixels[pixelOffset] = j1;
                    pixels[pixelOffset + 1] = j1;
                    pixels[pixelOffset + 2] = j1;
                    pixels[pixelOffset + 3] = j1;
                    pixelOffset += j;
                }

                return;
            }
            ShapedTile class40 = class30_sub3.shapedTile;
            if (class40 == null)
                return;
            int shape = class40.shape;
            int rotation = class40.rotation;
            int colourRGB = class40.colourRGB;
            int colourRGBA = class40.colourRGBA;
            int ai1[] = tileSHapePoints[shape];
            int ai2[] = tileShapeIndices[rotation];
            int l2 = 0;
            if (colourRGB != 0) {
                for (int i3 = 0; i3 < 4; i3++) {
                    pixels[pixelOffset] = ai1[ai2[l2++]] != 0 ? colourRGBA : colourRGB;
                    pixels[pixelOffset + 1] = ai1[ai2[l2++]] != 0 ? colourRGBA : colourRGB;
                    pixels[pixelOffset + 2] = ai1[ai2[l2++]] != 0 ? colourRGBA : colourRGB;
                    pixels[pixelOffset + 3] = ai1[ai2[l2++]] != 0 ? colourRGBA : colourRGB;
                    pixelOffset += j;
                }

                return;
            }
            for (int j3 = 0; j3 < 4; j3++) {
                if (ai1[ai2[l2++]] != 0)
                    pixels[pixelOffset] = colourRGBA;
                if (ai1[ai2[l2++]] != 0)
                    pixels[pixelOffset + 1] = colourRGBA;
                if (ai1[ai2[l2++]] != 0)
                    pixels[pixelOffset + 2] = colourRGBA;
                if (ai1[ai2[l2++]] != 0)
                    pixels[pixelOffset + 3] = colourRGBA;
                pixelOffset += j;
            }
        }
    }

    public void request2DTrace(int x, int y) {
        aBoolean467 = true;
        clickX = y;
        clickY = x;
        clickedTileX = -1;
        clickedTileY = -1;
    }

    private static final int INVALID_HSL_COLOR = 12345678;
    private static final int DEFAULT_DISTANCE = 25;
    private static final int PITCH_LOWER_LIMIT = 128;
    private static final int PITCH_UPPER_LIMIT = 383;


    /**
     * Renders the terrain.
     * The coordinates use the WorldCoordinate Axes but the modelWorld coordinates.
     * @param cameraXPos The cameraViewpoint's X-coordinate.
     * @param cameraYPos The cameraViewpoint's Y-coordinate.
     * @param camAngleXY The cameraAngle in the XY-plain.
     * @param cameraZPos The cameraViewpoint's X-coordinate.
     * @param planeZ The plain the camera's looking at.
     * @param camAngleZ  The cameraAngle on the Z-axis.
     */
    public void render2(int cameraXPos, int cameraYPos, int camAngleXY, int cameraZPos, int planeZ, int camAngleZ) {
        Rasterizer.resetDepthBuffer();
        final DrawCallbacks drawCallbacks = Client.instance.getDrawCallbacks();
        if (drawCallbacks != null)
        {
            Client.instance.getDrawCallbacks().drawScene(cameraXPos, cameraZPos, cameraYPos, camAngleZ, camAngleXY, planeZ);
        }

        final boolean isGpu = Client.instance.isGpu();
        final boolean checkClick = Client.instance.isCheckClick();
        final boolean menuOpen = Client.instance.isMenuOpen();

        if (!menuOpen && !checkClick)
        {
            Client.instance.getScene().menuOpen(Client.instance.getPlane(), Client.instance.getMouseX() - Client.instance.getViewportXOffset(), Client.instance.getMouseY() - Client.instance.getViewportYOffset(), false);
        }

        if (!isGpu && skyboxColor != 0)
        {
            Client.instance.rasterizerFillRectangle(
                    Client.instance.getViewportXOffset(),
                    Client.instance.getViewportYOffset(),
                    Client.instance.getViewportWidth(),
                    Client.instance.getViewportHeight(),
                    skyboxColor
            );
        }

        final int maxX = getMaxX();
        final int maxY = getMaxY();
        final int maxZ = getMaxZ();

        final int minLevel = getMinLevel();

        final RSTile[][][] tiles = getTiles();
        final int distance = isGpu ? drawDistance : DEFAULT_DISTANCE;

        if (cameraXPos < 0)
        {
            cameraXPos = 0;
        }
        else if (cameraXPos >= maxX * Perspective.LOCAL_TILE_SIZE)
        {
            cameraXPos = maxX * Perspective.LOCAL_TILE_SIZE - 1;
        }

        if (cameraYPos < 0)
        {
            cameraYPos = 0;
        }
        else if (cameraYPos >= maxZ * Perspective.LOCAL_TILE_SIZE)
        {
            cameraYPos = maxZ * Perspective.LOCAL_TILE_SIZE - 1;
        }


        // we store the uncapped pitch for setting camera angle for the pitch relaxer
        // we still have to cap the pitch in order to access the visibility map, though
        int realPitch = camAngleZ;
        if (camAngleZ < PITCH_LOWER_LIMIT)
        {
            camAngleZ = PITCH_LOWER_LIMIT;
        }
        else if (camAngleZ > PITCH_UPPER_LIMIT)
        {
            camAngleZ = PITCH_UPPER_LIMIT;
        }
        if (!pitchRelaxEnabled)
        {
            realPitch = camAngleZ;
        }


        Client.instance.setCycle(Client.instance.getCycle() + 1);
        Client.instance.setPitchSin(Perspective.SINE[realPitch]);
        Client.instance.setPitchCos(Perspective.COSINE[realPitch]);
        Client.instance.setYawSin(Perspective.SINE[camAngleXY]);
        Client.instance.setYawCos(Perspective.COSINE[camAngleXY]);


        final int[][][] tileHeights = Client.instance.getTileHeights();
        boolean[][] renderArea = Client.instance.getVisibilityMaps()[(camAngleZ - 128) / 32][camAngleXY / 64];
        Client.instance.setRenderArea(renderArea);

        Client.instance.setCameraX2(cameraXPos);
        Client.instance.setCameraY2(cameraZPos);
        Client.instance.setCameraZ2(cameraYPos);

        int screenCenterX = cameraXPos / Perspective.LOCAL_TILE_SIZE;
        int screenCenterZ = cameraYPos / Perspective.LOCAL_TILE_SIZE;

        Client.instance.setScreenCenterX(screenCenterX);
        Client.instance.setScreenCenterZ(screenCenterZ);
        Client.instance.setScenePlane(planeZ);

        int minTileX = screenCenterX - distance;
        if (minTileX < 0)
        {
            minTileX = 0;
        }

        int minTileZ = screenCenterZ - distance;
        if (minTileZ < 0)
        {
            minTileZ = 0;
        }

        int maxTileX = screenCenterX + distance;
        if (maxTileX > maxX)
        {
            maxTileX = maxX;
        }

        int maxTileZ = screenCenterZ + distance;
        if (maxTileZ > maxZ)
        {
            maxTileZ = maxZ;
        }

        Client.instance.setMinTileX(minTileX);
        Client.instance.setMinTileZ(minTileZ);
        Client.instance.setMaxTileX(maxTileX);
        Client.instance.setMaxTileZ(maxTileZ);

        updateOccluders();

        Client.instance.setTileUpdateCount(0);

        if (roofRemovalMode != 0)
        {
            tilesToRemove.clear();
            RSPlayer localPlayer = Client.instance.getLocalPlayer();
            if (localPlayer != null && (roofRemovalMode & ROOF_FLAG_POSITION) != 0)
            {
                LocalPoint localLocation = localPlayer.getLocalLocation();
                if (localLocation.isInScene())
                {
                    tilesToRemove.add(tileArray[Client.instance.getPlane()][localLocation.getSceneX()][localLocation.getSceneY()]);
                }
            }

            if (hoverX >= 0 && hoverX < 104 && hoverY >= 0 && hoverY < 104 && (roofRemovalMode & ROOF_FLAG_HOVERED) != 0)
            {
                tilesToRemove.add(tileArray[Client.instance.getPlane()][hoverX][hoverY]);
            }

            LocalPoint localDestinationLocation = Client.instance.getLocalDestinationLocation();
            if (localDestinationLocation != null && localDestinationLocation.isInScene() && (roofRemovalMode & ROOF_FLAG_DESTINATION) != 0)
            {
                tilesToRemove.add(tileArray[Client.instance.getPlane()][localDestinationLocation.getSceneX()][localDestinationLocation.getSceneY()]);
            }

            if (Client.instance.getCameraPitch() < 310 && (roofRemovalMode & ROOF_FLAG_BETWEEN) != 0 && localPlayer != null)
            {
                int playerX = localPlayer.getX() >> 7;
                int playerY = localPlayer.getY() >> 7;
                int var29 = Client.instance.getCameraX() >> 7;
                int var30 = Client.instance.getCameraY() >> 7;
                if (playerX >= 0 && playerY >= 0 && var29 >= 0 && var30 >= 0 && playerX < 104 && playerY < 104 && var29 < 104 && var30 < 104)
                {
                    int var31 = Math.abs(playerX - var29);
                    int var32 = Integer.compare(playerX, var29);
                    int var33 = -Math.abs(playerY - var30);
                    int var34 = Integer.compare(playerY, var30);
                    int var35 = var31 + var33;

                    while (var29 != playerX || var30 != playerY)
                    {
                        if (blocking(Client.instance.getPlane(), var29, var30))
                        {
                            tilesToRemove.add(tileArray[Client.instance.getPlane()][var29][var30]);
                        }

                        int var36 = 2 * var35;
                        if (var36 >= var33)
                        {
                            var35 += var33;
                            var29 += var32;
                        }
                        else
                        {
                            var35 += var31;
                            var30 += var34;
                        }
                    }
                }
            }
        }

        if (!menuOpen)
        {
            hoverY = -1;
            hoverX = -1;
        }

        for (int z = minLevel; z < maxY; ++z)
        {
            RSTile[][] planeTiles = tileArray[z];



            for (int x = minTileX; x < maxTileX; ++x)
            {
                for (int y = minTileZ; y < maxTileZ; ++y)
                {
                    RSTile tile = planeTiles[x][y];
                    if (tile != null)
                    {

                        RSTile var30 = tileArray[Client.instance.getPlane()][x][y];
                        if (tile.getPhysicalLevel() > planeZ && roofRemovalMode == 0
                                || !isGpu && !renderArea[x - screenCenterX + DEFAULT_DISTANCE][y - screenCenterZ + DEFAULT_DISTANCE]
                                && tileHeights[z][x][y] - cameraYPos < 2000
                                || roofRemovalMode != 0 && Client.instance.getPlane() < tile.getPhysicalLevel()
                                && tilesToRemove.contains(var30))
                        {
                            tile.setDraw(false);
                            tile.setVisible(false);
                            tile.setWallCullDirection(0);
                        }
                        else
                        {
                            tile.setDraw(true);
                            tile.setVisible(true);
                            tile.setDrawEntities(true);
                            Client.instance.setTileUpdateCount(Client.instance.getTileUpdateCount() + 1);
                        }
                    }
                }
            }
        }

        for (int z = minLevel; z < maxY; ++z)
        {
            RSTile[][] planeTiles = tileArray[z];

            for (int x = -distance; x <= 0; ++x)
            {
                int var10 = x + screenCenterX;
                int var16 = screenCenterX - x;
                if (var10 >= minTileX || var16 < maxTileX)
                {
                    for (int y = -distance; y <= 0; ++y)
                    {
                        int var13 = y + screenCenterZ;
                        int var14 = screenCenterZ - y;
                        if (var10 >= minTileX)
                        {
                            if (var13 >= minTileZ)
                            {
                                RSTile tile = planeTiles[var10][var13];
                                if (tile != null && tile.isDraw())
                                {
                                    draw(tile, true);
                                }
                            }

                            if (var14 < maxTileZ)
                            {
                                RSTile tile = planeTiles[var10][var14];
                                if (tile != null && tile.isDraw())
                                {
                                    draw(tile, true);
                                }
                            }
                        }

                        if (var16 < maxTileX)
                        {
                            if (var13 >= minTileZ)
                            {
                                RSTile tile = planeTiles[var16][var13];
                                if (tile != null && tile.isDraw())
                                {
                                    draw(tile, true);
                                }
                            }

                            if (var14 < maxTileZ)
                            {
                                RSTile tile = planeTiles[var16][var14];
                                if (tile != null && tile.isDraw())
                                {
                                    draw(tile, true);
                                }
                            }
                        }

                        if (Client.instance.getTileUpdateCount() == 0)
                        {
                            if (!isGpu && (Client.instance.getOculusOrbState() != 0 && !Client.instance.getComplianceValue("orbInteraction")))
                            {
                                Client.instance.setEntitiesAtMouseCount(0);
                            }
                            Client.instance.setCheckClick(false);
                            Client.instance.getCallbacks().drawScene();

                            if (Client.instance.getDrawCallbacks() != null)
                            {
                                Client.instance.getDrawCallbacks().postDrawScene();
                            }

                            return;
                        }
                    }
                }
            }
        }

        outer:
        for (int z = minLevel; z < maxY; ++z)
        {
            RSTile[][] planeTiles = tiles[z];

            for (int x = -distance; x <= 0; ++x)
            {
                int var10 = x + screenCenterX;
                int var16 = screenCenterX - x;
                if (var10 >= minTileX || var16 < maxTileX)
                {
                    for (int y = -distance; y <= 0; ++y)
                    {
                        int var13 = y + screenCenterZ;
                        int var14 = screenCenterZ - y;
                        if (var10 >= minTileX)
                        {
                            if (var13 >= minTileZ)
                            {
                                RSTile tile = planeTiles[var10][var13];
                                if (tile != null && tile.isDraw())
                                {
                                    draw(tile, false);
                                }
                            }

                            if (var14 < maxTileZ)
                            {
                                RSTile tile = planeTiles[var10][var14];
                                if (tile != null && tile.isDraw())
                                {
                                    draw(tile, false);
                                }
                            }
                        }

                        if (var16 < maxTileX)
                        {
                            if (var13 >= minTileZ)
                            {
                                RSTile tile = planeTiles[var16][var13];
                                if (tile != null && tile.isDraw())
                                {
                                    draw(tile, false);
                                }
                            }

                            if (var14 < maxTileZ)
                            {
                                RSTile tile = planeTiles[var16][var14];
                                if (tile != null && tile.isDraw())
                                {
                                    draw(tile, false);
                                }
                            }
                        }

                        if (Client.instance.getTileUpdateCount() == 0)
                        {
                            // exit the loop early and go straight to "if (!isGpu && (Client.instance..."
                            break outer;
                        }
                    }
                }
            }
        }

        if (!isGpu && (Client.instance.getOculusOrbState() != 0 && !Client.instance.getComplianceValue("orbInteraction")))
        {
            Client.instance.setEntitiesAtMouseCount(0);
        }
        Client.instance.setCheckClick(false);
        Client.instance.getCallbacks().drawScene(); // causing
        if (Client.instance.getDrawCallbacks() != null)
        {
            Client.instance.getDrawCallbacks().postDrawScene();
        }
    }

    public static boolean blocking(int plane, int x, int y)
    {
        return (Client.instance.getTileSettings()[plane][x][y] & 4) != 0;
    }
    
    public void render(int xCam, int yCam, int xCurve, int zCam, int plane, int yCurve) {
        Rasterizer.resetDepthBuffer();
        if (xCam < 0)
            xCam = 0;
        else if (xCam >= maxX * 128)
            xCam = maxX * 128 - 1;
        if (yCam < 0)
            yCam = 0;
        else if (yCam >= maxZ * 128)
            yCam = maxZ * 128 - 1;
        anInt448++;
        camUpDownY = Model.SINE[yCurve];
        camUpDownX = Model.COSINE[yCurve];
        camLeftRightY = Model.SINE[xCurve];
        camLeftRightX = Model.COSINE[xCurve];
        renderArea = tile_visibility_maps[(yCurve - 128) / 32][xCurve / 64];
        xCameraPos = xCam;
        zCameraPos = zCam;
        yCameraPos = yCam;
        xCamPosTile = xCam / 128;
        yCamPosTile = yCam / 128;
        plane__ = plane;
        anInt449 = xCamPosTile - 25;
        if (anInt449 < 0)
            anInt449 = 0;
        anInt451 = yCamPosTile - 25;
        if (anInt451 < 0)
            anInt451 = 0;
        anInt450 = xCamPosTile + 25;
        if (anInt450 > maxX)
            anInt450 = maxX;
        anInt452 = yCamPosTile + 25;
        if (anInt452 > maxZ)
            anInt452 = maxZ;
        processCulling();
        anInt446 = 0;
        for (int k1 = currentHL; k1 < maxY; k1++) {
            Tile tiles[][] = tileArray[k1];
            for (int x_ = anInt449; x_ < anInt450; x_++) {
                for (int y_ = anInt451; y_ < anInt452; y_++) {
                    Tile tile = tiles[x_][y_];
                    if (tile != null)
                        if (tile.logicHeight > plane
                                || !renderArea[(x_ - xCamPosTile) + 25][(y_ - yCamPosTile) + 25]
                                && heightMap[k1][x_][y_] - zCam < 2000) {
                            tile.aBoolean1322 = false;
                            tile.aBoolean1323 = false;
                            tile.wallCullDirection = 0;
                        } else {
                            tile.aBoolean1322 = true;
                            tile.aBoolean1323 = true;
                            tile.aBoolean1324 = tile.entityCount > 0;
                            anInt446++;
                        }
                }

            }

        }

        for (int l1 = currentHL; l1 < maxY; l1++) {
            Tile aclass30_sub3_1[][] = tileArray[l1];
            for (int l2 = -25; l2 <= 0; l2++) {
                int i3 = xCamPosTile + l2;
                int k3 = xCamPosTile - l2;
                if (i3 >= anInt449 || k3 < anInt450) {
                    for (int i4 = -25; i4 <= 0; i4++) {
                        int k4 = yCamPosTile + i4;
                        int i5 = yCamPosTile - i4;
                        if (i3 >= anInt449) {
                            if (k4 >= anInt451) {
                                Tile class30_sub3_1 = aclass30_sub3_1[i3][k4];
                                if (class30_sub3_1 != null && class30_sub3_1.aBoolean1322)
                                    renderTile(class30_sub3_1, true);
                            }
                            if (i5 < anInt452) {
                                Tile class30_sub3_2 = aclass30_sub3_1[i3][i5];
                                if (class30_sub3_2 != null && class30_sub3_2.aBoolean1322)
                                    renderTile(class30_sub3_2, true);
                            }
                        }
                        if (k3 < anInt450) {
                            if (k4 >= anInt451) {
                                Tile class30_sub3_3 = aclass30_sub3_1[k3][k4];
                                if (class30_sub3_3 != null && class30_sub3_3.aBoolean1322)
                                    renderTile(class30_sub3_3, true);
                            }
                            if (i5 < anInt452) {
                                Tile class30_sub3_4 = aclass30_sub3_1[k3][i5];
                                if (class30_sub3_4 != null && class30_sub3_4.aBoolean1322)
                                    renderTile(class30_sub3_4, true);
                            }
                        }
                        if (anInt446 == 0) {
                            aBoolean467 = false;
                            return;
                        }
                    }

                }
            }

        }

        for (int j2 = currentHL; j2 < maxY; j2++) {
            Tile aclass30_sub3_2[][] = tileArray[j2];
            for (int j3 = -25; j3 <= 0; j3++) {
                int l3 = xCamPosTile + j3;
                int j4 = xCamPosTile - j3;
                if (l3 >= anInt449 || j4 < anInt450) {
                    for (int l4 = -25; l4 <= 0; l4++) {
                        int j5 = yCamPosTile + l4;
                        int k5 = yCamPosTile - l4;
                        if (l3 >= anInt449) {
                            if (j5 >= anInt451) {
                                Tile class30_sub3_5 = aclass30_sub3_2[l3][j5];
                                if (class30_sub3_5 != null && class30_sub3_5.aBoolean1322)
                                    renderTile(class30_sub3_5, false);
                            }
                            if (k5 < anInt452) {
                                Tile class30_sub3_6 = aclass30_sub3_2[l3][k5];
                                if (class30_sub3_6 != null && class30_sub3_6.aBoolean1322)
                                    renderTile(class30_sub3_6, false);
                            }
                        }
                        if (j4 < anInt450) {
                            if (j5 >= anInt451) {
                                Tile class30_sub3_7 = aclass30_sub3_2[j4][j5];
                                if (class30_sub3_7 != null && class30_sub3_7.aBoolean1322)
                                    renderTile(class30_sub3_7, false);
                            }
                            if (k5 < anInt452) {
                                Tile tile = aclass30_sub3_2[j4][k5];
                                if (tile != null && tile.aBoolean1322)
                                    renderTile(tile, false);
                            }
                        }
                        if (anInt446 == 0) {
                            aBoolean467 = false;
                            return;
                        }
                    }

                }
            }
        }
        aBoolean467 = false;
    }

    private void renderTile(Tile mainTile, boolean flag) {
        tileDeque.insertHead(mainTile);
        do {
            Tile tempTile;
            do {
                tempTile = (Tile) tileDeque.popHead();
                if (tempTile == null)
                    return;
            } while (!tempTile.aBoolean1323);
            int i = tempTile.tileX;
            int j = tempTile.tileY;
            int k = tempTile.tileZ;
            int l = tempTile.plane;
            Tile tiles[][] = tileArray[k];
            int objectBufferOffset = 10;
            if (tempTile.aBoolean1322) {
                if (flag) {
                    if (k > 0) {
                        Tile tile = tileArray[k - 1][i][j];
                        if (tile != null && tile.aBoolean1323)
                            continue;
                    }
                    if (i <= screenCenterX && i > minTileX) {
                        Tile tile = tiles[i - 1][j];
                        if (tile != null && tile.aBoolean1323 && (tile.aBoolean1322 || (tempTile.anInt1320 & 1) == 0))
                            continue;
                    }
                    if (i >= screenCenterX && i < maxTileX - 1) {
                        Tile tile = tiles[i + 1][j];
                        if (tile != null && tile.aBoolean1323 && (tile.aBoolean1322 || (tempTile.anInt1320 & 4) == 0))
                            continue;
                    }
                    if (j <= screenCenterZ && j > minTileZ) {
                        Tile class30_sub3_5 = tiles[i][j - 1];
                        if (class30_sub3_5 != null && class30_sub3_5.aBoolean1323
                                && (class30_sub3_5.aBoolean1322 || (tempTile.anInt1320 & 8) == 0))
                            continue;
                    }
                    if (j >= screenCenterZ && j < maxTileZ - 1) {
                        Tile class30_sub3_6 = tiles[i][j + 1];
                        if (class30_sub3_6 != null && class30_sub3_6.aBoolean1323
                                && (class30_sub3_6.aBoolean1322 || (tempTile.anInt1320 & 2) == 0))
                            continue;
                    }
                } else {
                    flag = true;
                }
                tempTile.aBoolean1322 = false;
//                tileUpdateCount--;
                if (tempTile.tileBelowThisTile != null) {
                    Tile lowerTile = tempTile.tileBelowThisTile;
                    if (lowerTile.plainTile != null) {
                        if (!method320(0, i, j)) {
                            drawTileUnderlay(lowerTile.plainTile, 0, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX, i, j);
                        }
                    } else if (lowerTile.shapedTile != null && !method320(0, i, j))
                        drawTileOverlay(i, camUpDownY, camLeftRightY, lowerTile.shapedTile, camUpDownX, j, camLeftRightX);
                    WallObject wallObject = lowerTile.wallObject;
                    if (wallObject != null)
                        wallObject.node1.renderAtPoint(0, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX,
                                wallObject.xPos - xCameraPos, wallObject.tileHeights - zCameraPos, wallObject.yPos - yCameraPos,
                                wallObject.uid, wallObject.wallObjUID, 10);
                    for (int i2 = 0; i2 < lowerTile.entityCount; i2++) {
                        InteractableObject iObject = lowerTile.gameObjects[i2];
                        if (iObject != null)
                            iObject.renderable.renderAtPoint(iObject.turnValue, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX,
                                    iObject.xPos - xCameraPos, iObject.tileHeight - zCameraPos, iObject.yPos - yCameraPos,
                                    iObject.uid, iObject.interactiveObjUID, objectBufferOffset);
                    }

                }
                boolean flag1 = false;
                if (tempTile.plainTile != null) {
                    if (!method320(l, i, j)) {
                        flag1 = true;
                        drawTileUnderlay(tempTile.plainTile, l, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX, i, j);
                    }
                } else if (tempTile.shapedTile != null && !method320(l, i, j)) {
                    flag1 = true;
                    drawTileOverlay(i, camUpDownY, camLeftRightY, tempTile.shapedTile, camUpDownX, j, camLeftRightX);
                }
                int j1 = 0;
                int j2 = 0;
                WallObject class10_3 = tempTile.wallObject;
                WallDecoration class26_1 = tempTile.wallDecoration;
                if (class10_3 != null || class26_1 != null) {
                    if (screenCenterX == i)
                        j1++;
                    else if (screenCenterX < i)
                        j1 += 2;
                    if (screenCenterZ == j)
                        j1 += 3;
                    else if (screenCenterZ > j)
                        j1 += 6;
                    j2 = anIntArray478[j1];
                    tempTile.anInt1328 = anIntArray480[j1];
                }
                if (class10_3 != null) {
                    if ((class10_3.orientation & anIntArray479[j1]) != 0) {
                        if (class10_3.orientation == 16) {
                            tempTile.wallCullDirection = 3;
                            tempTile.anInt1326 = anIntArray481[j1];
                            tempTile.anInt1327 = 3 - tempTile.anInt1326;
                        } else if (class10_3.orientation == 32) {
                            tempTile.wallCullDirection = 6;
                            tempTile.anInt1326 = anIntArray482[j1];
                            tempTile.anInt1327 = 6 - tempTile.anInt1326;
                        } else if (class10_3.orientation == 64) {
                            tempTile.wallCullDirection = 12;
                            tempTile.anInt1326 = anIntArray483[j1];
                            tempTile.anInt1327 = 12 - tempTile.anInt1326;
                        } else {
                            tempTile.wallCullDirection = 9;
                            tempTile.anInt1326 = anIntArray484[j1];
                            tempTile.anInt1327 = 9 - tempTile.anInt1326;
                        }
                    } else {
                        tempTile.wallCullDirection = 0;
                    }
                    if ((class10_3.orientation & j2) != 0 && !method321(l, i, j, class10_3.orientation))
                        class10_3.node1.renderAtPoint(0, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX,
                                class10_3.xPos - xCameraPos, class10_3.tileHeights - zCameraPos, class10_3.yPos - yCameraPos,
                                class10_3.uid, class10_3.wallObjUID, 10);
                    if ((class10_3.orientation1 & j2) != 0 && !method321(l, i, j, class10_3.orientation1))
                        class10_3.node2.renderAtPoint(0, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX,
                                class10_3.xPos - xCameraPos, class10_3.tileHeights - zCameraPos, class10_3.yPos - yCameraPos,
                                class10_3.uid, class10_3.wallObjUID, 10);
                }
                if (class26_1 != null && !method322(l, i, j, class26_1.node.modelBaseY))
                    if ((class26_1.configurationBits & j2) != 0)
                        class26_1.node.renderAtPoint(class26_1.rotation, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX,
                                class26_1.xPos - xCameraPos, class26_1.zPos - zCameraPos, class26_1.yPos - yCameraPos,
                                class26_1.uid, class26_1.wallDecorUID, 15);
                    else if ((class26_1.configurationBits & 0x300) != 0) {
                        int j4 = class26_1.xPos - xCameraPos;
                        int l5 = class26_1.zPos - zCameraPos;
                        int k6 = class26_1.yPos - yCameraPos;
                        int i8 = class26_1.rotation;
                        int k9;
                        if (i8 == 1 || i8 == 2)
                            k9 = -j4;
                        else
                            k9 = j4;
                        int k10;
                        if (i8 == 2 || i8 == 3)
                            k10 = -k6;
                        else
                            k10 = k6;
                        if ((class26_1.configurationBits & 0x100) != 0 && k10 < k9) {
                            int i11 = j4 + faceXoffset2[i8];
                            int k11 = k6 + faceYOffset2[i8];
                            class26_1.node.renderAtPoint(i8 * 512 + 256, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX,
                                    i11, l5, k11, class26_1.uid, class26_1.wallDecorUID, 15);
                        }
                        if ((class26_1.configurationBits & 0x200) != 0 && k10 > k9) {
                            int j11 = j4 + faceXOffset3[i8];
                            int l11 = k6 + faceYOffset3[i8];
                            class26_1.node.renderAtPoint(i8 * 512 + 1280 & 0x7ff, camUpDownY, camUpDownX, camLeftRightY,
                                    camLeftRightX, j11, l5, l11, class26_1.uid, class26_1.wallDecorUID, 15);
                        }
                    }
                if (flag1) {
                    GroundDecoration class49 = tempTile.groundDecoration;
                    if (class49 != null)
                        class49.node.renderAtPoint(0, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX,
                                class49.xPos - xCameraPos, class49.zPos - zCameraPos, class49.yPos - yCameraPos, class49.uid,
                                class49.groundDecorUID, 15);
                    GroundItem object4_1 = tempTile.groundItemTile;
                    if (object4_1 != null && object4_1.topItem == 0) {
                        if (object4_1.secondGroundItem != null)
                            object4_1.secondGroundItem.renderAtPoint(0, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX,
                                    object4_1.xPos - xCameraPos, object4_1.zPos - zCameraPos, object4_1.yPos - yCameraPos,
                                    object4_1.uid, object4_1.newuid, 5);
                        if (object4_1.thirdGroundItem != null)
                            object4_1.thirdGroundItem.renderAtPoint(0, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX,
                                    object4_1.xPos - xCameraPos, object4_1.zPos - zCameraPos, object4_1.yPos - yCameraPos,
                                    object4_1.uid, object4_1.newuid, 5);
                        if (object4_1.firstGroundItem != null)
                            object4_1.firstGroundItem.renderAtPoint(0, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX,
                                    object4_1.xPos - xCameraPos, object4_1.zPos - zCameraPos, object4_1.yPos - yCameraPos,
                                    object4_1.uid, object4_1.newuid, 5);
                    }
                }
                int k4 = tempTile.anInt1320;
                if (k4 != 0) {
                    if (i < screenCenterX && (k4 & 4) != 0) {
                        Tile class30_sub3_17 = tiles[i + 1][j];
                        if (class30_sub3_17 != null && class30_sub3_17.aBoolean1323)
                            tileDeque.insertHead(class30_sub3_17);
                    }
                    if (j < screenCenterZ && (k4 & 2) != 0) {
                        Tile class30_sub3_18 = tiles[i][j + 1];
                        if (class30_sub3_18 != null && class30_sub3_18.aBoolean1323)
                            tileDeque.insertHead(class30_sub3_18);
                    }
                    if (i > screenCenterX && (k4 & 1) != 0) {
                        Tile class30_sub3_19 = tiles[i - 1][j];
                        if (class30_sub3_19 != null && class30_sub3_19.aBoolean1323)
                            tileDeque.insertHead(class30_sub3_19);
                    }
                    if (j > screenCenterZ && (k4 & 8) != 0) {
                        Tile class30_sub3_20 = tiles[i][j - 1];
                        if (class30_sub3_20 != null && class30_sub3_20.aBoolean1323)
                            tileDeque.insertHead(class30_sub3_20);
                    }
                }
            }
            if (tempTile.wallCullDirection != 0) {
                boolean flag2 = true;
                for (int k1 = 0; k1 < tempTile.entityCount; k1++) {
                    if (tempTile.gameObjects[k1].height == cycle
                            || (tempTile.tiledObjectMasks[k1] & tempTile.wallCullDirection) != tempTile.anInt1326)
                        continue;
                    flag2 = false;
                    break;
                }

                if (flag2) {
                    WallObject class10_1 = tempTile.wallObject;
                    if (!method321(l, i, j, class10_1.orientation))
                        class10_1.node1.renderAtPoint(0, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX,
                                class10_1.xPos - xCameraPos, class10_1.tileHeights - zCameraPos, class10_1.yPos - yCameraPos,
                                class10_1.uid, class10_1.wallObjUID, 10);
                    tempTile.wallCullDirection = 0;
                }
            }
            if (tempTile.aBoolean1324)
                try {
                    int i1 = tempTile.entityCount;
                    tempTile.aBoolean1324 = false;
                    int l1 = 0;
                    label0:
                    for (int k2 = 0; k2 < i1; k2++) {
                        InteractableObject class28_1 = tempTile.gameObjects[k2];
                        if (class28_1.height == cycle)
                            continue;
                        for (int k3 = class28_1.xLocLow; k3 <= class28_1.xLocHigh; k3++) {
                            for (int l4 = class28_1.yLocHigh; l4 <= class28_1.yLocLow; l4++) {
                                Tile class30_sub3_21 = tiles[k3][l4];
                                if (class30_sub3_21.aBoolean1322) {
                                    tempTile.aBoolean1324 = true;
                                } else {
                                    if (class30_sub3_21.wallCullDirection == 0)
                                        continue;
                                    int l6 = 0;
                                    if (k3 > class28_1.xLocLow)
                                        l6++;
                                    if (k3 < class28_1.xLocHigh)
                                        l6 += 4;
                                    if (l4 > class28_1.yLocHigh)
                                        l6 += 8;
                                    if (l4 < class28_1.yLocLow)
                                        l6 += 2;
                                    if ((l6 & class30_sub3_21.wallCullDirection) != tempTile.anInt1327)
                                        continue;
                                    tempTile.aBoolean1324 = true;
                                }
                                continue label0;
                            }

                        }

                        aClass28Array462[l1++] = class28_1;
                        int i5 = screenCenterX - class28_1.xLocLow;
                        int i6 = class28_1.xLocHigh - screenCenterX;
                        if (i6 > i5)
                            i5 = i6;
                        int i7 = screenCenterZ - class28_1.yLocHigh;
                        int j8 = class28_1.yLocLow - screenCenterZ;
                        if (j8 > i7)
                            class28_1.anInt527 = i5 + j8;
                        else
                            class28_1.anInt527 = i5 + i7;
                    }

                    while (l1 > 0) {
                        int i3 = -50;
                        int l3 = -1;
                        for (int j5 = 0; j5 < l1; j5++) {
                            InteractableObject class28_2 = aClass28Array462[j5];
                            if (class28_2.height != cycle)
                                if (class28_2.anInt527 > i3) {
                                    i3 = class28_2.anInt527;
                                    l3 = j5;
                                } else if (class28_2.anInt527 == i3) {
                                    int j7 = class28_2.xPos - xCameraPos;
                                    int k8 = class28_2.yPos - yCameraPos;
                                    int l9 = aClass28Array462[l3].xPos - xCameraPos;
                                    int l10 = aClass28Array462[l3].yPos - yCameraPos;
                                    if (j7 * j7 + k8 * k8 > l9 * l9 + l10 * l10)
                                        l3 = j5;
                                }
                        }

                        if (l3 == -1)
                            break;
                        InteractableObject class28_3 = aClass28Array462[l3];
                        class28_3.height = cycle;
                        if (!method323(l, class28_3.xLocLow, class28_3.xLocHigh, class28_3.yLocHigh,
                                class28_3.yLocLow, class28_3.renderable.modelBaseY))
                            class28_3.renderable.renderAtPoint(class28_3.turnValue, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX,
                                    class28_3.xPos - xCameraPos, class28_3.tileHeight - zCameraPos, class28_3.yPos - yCameraPos,
                                    class28_3.uid, class28_3.interactiveObjUID, objectBufferOffset);
                        for (int k7 = class28_3.xLocLow; k7 <= class28_3.xLocHigh; k7++) {
                            for (int l8 = class28_3.yLocHigh; l8 <= class28_3.yLocLow; l8++) {
                                Tile class30_sub3_22 = tiles[k7][l8];
                                if (class30_sub3_22.wallCullDirection != 0)
                                    tileDeque.insertHead(class30_sub3_22);
                                else if ((k7 != i || l8 != j) && class30_sub3_22.aBoolean1323)
                                    tileDeque.insertHead(class30_sub3_22);
                            }

                        }

                    }
                    if (tempTile.aBoolean1324)
                        continue;
                } catch (Exception _ex) {
                    _ex.printStackTrace();
                    tempTile.aBoolean1324 = false;
                }
            if (!tempTile.aBoolean1323 || tempTile.wallCullDirection != 0)
                continue;
            if (i <= screenCenterX && i > minTileX) {
                Tile class30_sub3_8 = tiles[i - 1][j];
                if (class30_sub3_8 != null && class30_sub3_8.aBoolean1323)
                    continue;
            }
            if (i >= screenCenterX && i < maxTileX - 1) {
                Tile class30_sub3_9 = tiles[i + 1][j];
                if (class30_sub3_9 != null && class30_sub3_9.aBoolean1323)
                    continue;
            }
            if (j <= screenCenterZ && j > minTileZ) {
                Tile class30_sub3_10 = tiles[i][j - 1];
                if (class30_sub3_10 != null && class30_sub3_10.aBoolean1323)
                    continue;
            }
            if (j >= screenCenterZ && j < maxTileZ - 1) {
                Tile class30_sub3_11 = tiles[i][j + 1];
                if (class30_sub3_11 != null && class30_sub3_11.aBoolean1323)
                    continue;
            }
            tempTile.aBoolean1323 = false;
            tileUpdateCount--;
            GroundItem object4 = tempTile.groundItemTile;
            if (object4 != null && object4.topItem != 0) {
                if (object4.secondGroundItem != null)
                    object4.secondGroundItem.renderAtPoint(0, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX,
                            object4.xPos - xCameraPos, object4.zPos - zCameraPos - object4.topItem, object4.yPos - yCameraPos,
                            object4.uid, object4.newuid, 5);
                if (object4.thirdGroundItem != null)
                    object4.thirdGroundItem.renderAtPoint(0, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX,
                            object4.xPos - xCameraPos, object4.zPos - zCameraPos - object4.topItem, object4.yPos - yCameraPos,
                            object4.uid, object4.newuid, 5);
                if (object4.firstGroundItem != null)
                    object4.firstGroundItem.renderAtPoint(0, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX,
                            object4.xPos - xCameraPos, object4.zPos - zCameraPos - object4.topItem, object4.yPos - yCameraPos,
                            object4.uid, object4.newuid, 5);
            }
            if (tempTile.anInt1328 != 0) {
                WallDecoration class26 = tempTile.wallDecoration;
                if (class26 != null && !method322(l, i, j, class26.node.modelBaseY))
                    if ((class26.configurationBits & tempTile.anInt1328) != 0)
                        class26.node.renderAtPoint(class26.rotation, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX,
                                class26.xPos - xCameraPos, class26.zPos - zCameraPos, class26.yPos - yCameraPos, class26.uid,
                                class26.wallDecorUID, 15);
                    else if ((class26.configurationBits & 0x300) != 0) {
                        int l2 = class26.xPos - xCameraPos;
                        int j3 = class26.zPos - zCameraPos;
                        int i4 = class26.yPos - yCameraPos;
                        int k5 = class26.rotation;
                        int j6;
                        if (k5 == 1 || k5 == 2)
                            j6 = -l2;
                        else
                            j6 = l2;
                        int l7;
                        if (k5 == 2 || k5 == 3)
                            l7 = -i4;
                        else
                            l7 = i4;
                        if ((class26.configurationBits & 0x100) != 0 && l7 >= j6) {
                            int i9 = l2 + faceXoffset2[k5];
                            int i10 = i4 + faceYOffset2[k5];
                            class26.node.renderAtPoint(k5 * 512 + 256, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX, i9,
                                    j3, i10, class26.uid, class26.wallDecorUID, 15);
                        }
                        if ((class26.configurationBits & 0x200) != 0 && l7 <= j6) {
                            int j9 = l2 + faceXOffset3[k5];
                            int j10 = i4 + faceYOffset3[k5];
                            class26.node.renderAtPoint(k5 * 512 + 1280 & 0x7ff, camUpDownY, camUpDownX, camLeftRightY,
                                    camLeftRightX, j9, j3, j10, class26.uid, class26.wallDecorUID, 15);
                        }
                    }
                WallObject class10_2 = tempTile.wallObject;
                if (class10_2 != null) {
                    if ((class10_2.orientation1 & tempTile.anInt1328) != 0
                            && !method321(l, i, j, class10_2.orientation1))
                        class10_2.node2.renderAtPoint(0, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX,
                                class10_2.xPos - xCameraPos, class10_2.tileHeights - zCameraPos, class10_2.yPos - yCameraPos,
                                class10_2.uid, class10_2.wallObjUID, 10);
                    if ((class10_2.orientation & tempTile.anInt1328) != 0 && !method321(l, i, j, class10_2.orientation))
                        class10_2.node1.renderAtPoint(0, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX,
                                class10_2.xPos - xCameraPos, class10_2.tileHeights - zCameraPos, class10_2.yPos - yCameraPos,
                                class10_2.uid, class10_2.wallObjUID, 10);
                }
            }
            if (k < maxY - 1) {
                Tile class30_sub3_12 = tileArray[k + 1][i][j];
                if (class30_sub3_12 != null && class30_sub3_12.aBoolean1323)
                    tileDeque.insertHead(class30_sub3_12);
            }
            if (i < screenCenterX) {
                Tile class30_sub3_13 = tiles[i + 1][j];
                if (class30_sub3_13 != null && class30_sub3_13.aBoolean1323)
                    tileDeque.insertHead(class30_sub3_13);
            }
            if (j < screenCenterZ) {
                Tile class30_sub3_14 = tiles[i][j + 1];
                if (class30_sub3_14 != null && class30_sub3_14.aBoolean1323)
                    tileDeque.insertHead(class30_sub3_14);
            }
            if (i > screenCenterX) {
                Tile class30_sub3_15 = tiles[i - 1][j];
                if (class30_sub3_15 != null && class30_sub3_15.aBoolean1323)
                    tileDeque.insertHead(class30_sub3_15);
            }
            if (j > screenCenterZ) {
                Tile class30_sub3_16 = tiles[i][j - 1];
                if (class30_sub3_16 != null && class30_sub3_16.aBoolean1323)
                    tileDeque.insertHead(class30_sub3_16);
            }
        } while (true);
    }

    private void drawTileUnderlay(PlainTile tile, int z, int pitchSin, int pitchCos, int yawSin, int yawCos, int x, int y) {
        byte[][][] tileSettings = Client.instance.getTileSettings();
        final boolean checkClick = Client.instance.isCheckClick();

        int tilePlane = z;
        if ((tileSettings[1][x][x] & 2) != 0)
        {
            tilePlane = z - 1;
        }

        if (!Client.instance.isGpu())
        {
            try {
                drawTileUnderlaySD(tile, z, pitchSin, pitchCos, yawSin, yawCos, x, y);
            }
            catch (Exception ex)
            {
                Client.instance.getLogger().warn("error during tile underlay rendering", ex);
            }

            if (roofRemovalMode == 0 || !checkClick || Client.instance.getPlane() != tilePlane)
            {
                return;
            }
        }

        final DrawCallbacks drawCallbacks = Client.instance.getDrawCallbacks();

        if (drawCallbacks == null)
        {
            return;
        }

        try
        {
            final int[][][] tileHeights = getTileHeights();

            final int cameraX2 = Client.instance.getCameraX2();
            final int cameraY2 = Client.instance.getCameraY2();
            final int cameraZ2 = Client.instance.getCameraZ2();

            final int zoom = Client.instance.get3dZoom();
            final int centerX = Client.instance.getCenterX();
            final int centerY = Client.instance.getCenterY();

            final int mouseX2 = Client.instance.getMouseX2();
            final int mouseY2 = Client.instance.getMouseY2();

            int var9;
            int var10 = var9 = (x << 7) - cameraX2;
            int var11;
            int var12 = var11 = (y << 7) - cameraZ2;
            int var13;
            int var14 = var13 = var10 + 128;
            int var15;
            int var16 = var15 = var12 + 128;
            int var17 = tileHeights[z][x][y] - cameraY2;
            int var18 = tileHeights[z][x + 1][y] - cameraY2;
            int var19 = tileHeights[z][x + 1][y + 1] - cameraY2;
            int var20 = tileHeights[z][x][y + 1] - cameraY2;
            int var21 = var10 * yawCos + yawSin * var12 >> 16;
            var12 = var12 * yawCos - yawSin * var10 >> 16;
            var10 = var21;
            var21 = var17 * pitchCos - pitchSin * var12 >> 16;
            var12 = pitchSin * var17 + var12 * pitchCos >> 16;
            var17 = var21;
            if (var12 >= 50)
            {
                var21 = var14 * yawCos + yawSin * var11 >> 16;
                var11 = var11 * yawCos - yawSin * var14 >> 16;
                var14 = var21;
                var21 = var18 * pitchCos - pitchSin * var11 >> 16;
                var11 = pitchSin * var18 + var11 * pitchCos >> 16;
                var18 = var21;
                if (var11 >= 50)
                {
                    var21 = var13 * yawCos + yawSin * var16 >> 16;
                    var16 = var16 * yawCos - yawSin * var13 >> 16;
                    var13 = var21;
                    var21 = var19 * pitchCos - pitchSin * var16 >> 16;
                    var16 = pitchSin * var19 + var16 * pitchCos >> 16;
                    var19 = var21;
                    if (var16 >= 50)
                    {
                        var21 = var9 * yawCos + yawSin * var15 >> 16;
                        var15 = var15 * yawCos - yawSin * var9 >> 16;
                        var9 = var21;
                        var21 = var20 * pitchCos - pitchSin * var15 >> 16;
                        var15 = pitchSin * var20 + var15 * pitchCos >> 16;
                        if (var15 >= 50)
                        {
                            int dy = var10 * zoom / var12 + centerX;
                            int dx = var17 * zoom / var12 + centerY;
                            int cy = var14 * zoom / var11 + centerX;
                            int cx = var18 * zoom / var11 + centerY;
                            int ay = var13 * zoom / var16 + centerX;
                            int ax = var19 * zoom / var16 + centerY;
                            int by = var9 * zoom / var15 + centerX;
                            int bx = var21 * zoom / var15 + centerY;

                            drawCallbacks.drawScenePaint(0, pitchSin, pitchCos, yawSin, yawCos,
                                    -cameraX2, -cameraY2, -cameraZ2,
                                    tile, z, x, y,
                                    zoom, centerX, centerY);

                            if ((ay - by) * (cx - bx) - (ax - bx) * (cy - by) > 0)
                            {

                                if (checkClick && Client.instance.containsBounds(mouseX2, mouseY2, ax, bx, cx, ay, by, cy)) {
                                    setTargetTile(x, y);
                                }
                                if (Client.instance.containsBounds(Client.instance.mouseX, Client.instance.mouseY, ax, bx, cx, ay, by, cy)) {
                                    hoverTile(x, y, tilePlane);
                                }

                            }

                            if ((dy - cy) * (bx - cx) - (dx - cx) * (by - cy) > 0)
                            {

                                if (checkClick && inBounds(clickX, clickY, dx, cx, bx, dy, cy, by)) {
                                    setTargetTile(x, y);
                                }
                                if (inBounds(Client.instance.mouseX, Client.instance.mouseY, dx, cx, bx, dy, cy, by)) {
                                    hoverTile(x, y, tilePlane);
                                }

                            }

                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            Client.instance.getLogger().warn("error during underlay rendering", ex);
        }
    }

    public boolean inBounds(int i, int j, int k, int l, int i1, int j1, int k1, int l1) {
        if (j < k && j < l && j < i1)
            return false;
        if (j > k && j > l && j > i1)
            return false;
        if (i < j1 && i < k1 && i < l1)
            return false;
        if (i > j1 && i > k1 && i > l1)
            return false;
        int i2 = (j - k) * (k1 - j1) - (i - j1) * (l - k);
        int j2 = (j - i1) * (j1 - l1) - (i - l1) * (k - i1);
        int k2 = (j - l) * (l1 - k1) - (i - k1) * (i1 - l);
        return i2 * k2 > 0 && k2 * j2 > 0;
    }

    private static void setTargetTile(int targetX, int targetY)
    {
        Client.instance.setSelectedSceneTileX(targetX);
        Client.instance.setSelectedSceneTileY(targetY);
    }

    public static void hoverTile(int x, int y, int plane)
    {
        if (plane == Client.instance.getPlane() && !Client.instance.isMenuOpen())
        {
            hoverX = x;
            hoverY = y;
        }
    }

    private void drawTileUnderlaySD(PlainTile class43, int i, int j, int k, int l, int i1, int j1, int k1) {
        int l1;
        int i2 = l1 = (j1 << 7) - xCameraPos;
        int j2;
        int k2 = j2 = (k1 << 7) - yCameraPos;
        int l2;
        int i3 = l2 = i2 + 128;
        int j3;
        int k3 = j3 = k2 + 128;
        int l3 = heightMap[i][j1][k1] - zCameraPos;
        int i4 = heightMap[i][j1 + 1][k1] - zCameraPos;
        int j4 = heightMap[i][j1 + 1][k1 + 1] - zCameraPos;
        int k4 = heightMap[i][j1][k1 + 1] - zCameraPos;
        int l4 = k2 * l + i2 * i1 >> 16;
        k2 = k2 * i1 - i2 * l >> 16;
        i2 = l4;
        l4 = l3 * k - k2 * j >> 16;
        k2 = l3 * j + k2 * k >> 16;
        l3 = l4;
        if (k2 < 50) {
            return;
        }
        l4 = j2 * l + i3 * i1 >> 16;
        j2 = j2 * i1 - i3 * l >> 16;
        i3 = l4;
        l4 = i4 * k - j2 * j >> 16;
        j2 = i4 * j + j2 * k >> 16;
        i4 = l4;
        if (j2 < 50) {
            return;
        }
        l4 = k3 * l + l2 * i1 >> 16;
        k3 = k3 * i1 - l2 * l >> 16;
        l2 = l4;
        l4 = j4 * k - k3 * j >> 16;
        k3 = j4 * j + k3 * k >> 16;
        j4 = l4;
        if (k3 < 50) {
            return;
        }
        l4 = j3 * l + l1 * i1 >> 16;
        j3 = j3 * i1 - l1 * l >> 16;
        l1 = l4;
        l4 = k4 * k - j3 * j >> 16;
        j3 = k4 * j + j3 * k >> 16;
        k4 = l4;
        if (j3 < 50) {
            return;
        }
        int i5 = Rasterizer.textureInt1 + i2 * Rasterizer.fieldOfView / k2;
        int j5 = Rasterizer.textureInt2 + l3 * Rasterizer.fieldOfView / k2;
        int k5 = Rasterizer.textureInt1 + i3 * Rasterizer.fieldOfView / j2;
        int l5 = Rasterizer.textureInt2 + i4 * Rasterizer.fieldOfView / j2;
        int i6 = Rasterizer.textureInt1 + l2 * Rasterizer.fieldOfView / k3;
        int j6 = Rasterizer.textureInt2 + j4 * Rasterizer.fieldOfView / k3;
        int k6 = Rasterizer.textureInt1 + l1 * Rasterizer.fieldOfView / j3;
        int l6 = Rasterizer.textureInt2 + k4 * Rasterizer.fieldOfView / j3;
        Rasterizer.anInt1465 = 0;
        if ((i6 - k6) * (l5 - l6) - (j6 - l6) * (k5 - k6) > 0) {
            Rasterizer.aBoolean1462 = i6 < 0 || k6 < 0 || k5 < 0 || i6 > DrawingArea.viewportRX || k6 > DrawingArea.viewportRX || k5 > DrawingArea.viewportRX;
            if (aBoolean467 && method318(clickX, clickY, j6, l6, l5, i6, k6, k5)) {
                clickedTileX = j1;
                clickedTileY = k1;
            }
            if (inBounds(Client.instance.getMouseX(), Client.instance.getMouseY(), j6, l6, l5, i6, k6, k5)) {
                hoverX = j1;
                hoverY = k1;
            }
            if (class43.texture == -1 || class43.texture > 50) {
                if (class43.centerColor != 0xbc614e) {
                    if (Client.getOption("hd_tex") && class43.texture != -1) {
                        if (class43.aBoolean721) {
                            Rasterizer.drawMaterializedTriangle(j6, l6, l5, i6, k6, k5, class43.centerColor, class43.eastColor, class43.northColor, i2, i3, l1, l3, i4, k4, k2, j2, j3, class43.texture, (float) k3, (float) j3, (float) j2);
                        } else {
                            Rasterizer.drawMaterializedTriangle(j6, l6, l5, i6, k6, k5, class43.centerColor, class43.eastColor, class43.northColor, l2, l1, i3, j4, k4, i4, k3, j3, j2, class43.texture, (float) k3, (float) j3, (float) j2);
                        }
                    } else {
                        Rasterizer.drawGouraudTriangle(j6, l6, l5, i6, k6, k5, class43.centerColor, class43.eastColor, class43.northColor, (float) k3, (float) j3, (float) j2, 0);
                    }
                }
            } else if (!lowMem) {
                if (class43.aBoolean721) {
                    Rasterizer.drawTexturedTriangle(j6, l6, l5, i6, k6, k5, class43.centerColor, class43.eastColor, class43.northColor, i2, i3, l1, l3, i4, k4, k2, j2, j3, class43.texture, (float) k3, (float) j3, (float) j2, 0);
                } else {
                    Rasterizer.drawTexturedTriangle(j6, l6, l5, i6, k6, k5, class43.centerColor, class43.eastColor, class43.northColor, l2, l1, i3, j4, k4, i4, k3, j3, j2, class43.texture, (float) k3, (float) j3, (float) j2, 0);
                }
            } else {
                int i7 = anIntArray485[class43.texture];
                Rasterizer.drawGouraudTriangle(j6, l6, l5, i6, k6, k5, mixColour(i7, class43.centerColor), mixColour(i7, class43.eastColor), mixColour(i7, class43.northColor), (float) k3, (float) j3, (float) j2, 0);
            }
        }
        if ((i5 - k5) * (l6 - l5) - (j5 - l5) * (k6 - k5) > 0) {
            Rasterizer.aBoolean1462 = i5 < 0 || k5 < 0 || k6 < 0 || i5 > DrawingArea.viewportRX || k5 > DrawingArea.viewportRX || k6 > DrawingArea.viewportRX;
            if (aBoolean467 && method318(clickX, clickY, j5, l5, l6, i5, k5, k6)) {
                clickedTileX = j1;
                clickedTileY = k1;
            }
            if (inBounds(Client.instance.getMouseX(), Client.instance.getMouseY(), j5, l5, l6, i5, k5, k6)) {
                hoverX = j1;
                hoverY = k1;
            }
            if (class43.texture == -1 || class43.texture > 50) {
                if (class43.northEastColor != 0xbc614e) {
                    if (Client.getOption("hd_tex") && class43.texture != -1) {
                        Rasterizer.drawMaterializedTriangle(j5, l5, l6, i5, k5, k6, class43.northEastColor, class43.northColor, class43.eastColor, i2, i3, l1, l3, i4, k4, k2, j2, j3, class43.texture, (float) k2, (float) j2, (float) j3);
                    } else {
                        Rasterizer.drawGouraudTriangle(j5, l5, l6, i5, k5, k6, class43.northEastColor, class43.northColor, class43.eastColor, (float) k2, (float) j2, (float) j3, 0);
                    }
                }
            } else {
                if (!lowMem) {
                    Rasterizer.drawTexturedTriangle(j5, l5, l6, i5, k5, k6, class43.northEastColor, class43.northColor, class43.eastColor, i2, i3, l1, l3, i4, k4, k2, j2, j3, class43.texture, (float) k2, (float) j2, (float) j3, 0);
                    return;
                }
                int j7 = anIntArray485[class43.texture];
                Rasterizer.drawGouraudTriangle(j5, l5, l6, i5, k5, k6, mixColour(j7, class43.northEastColor), mixColour(j7, class43.northColor), mixColour(j7, class43.eastColor), (float) k2, (float) j2, (float) j3, 0);
            }
        }
    }

    private void drawTileOverlay(int tileX, int pitchSin, int yawSin, ShapedTile tile, int pitchCos, int tileY, int yawCos) {
        RSTile rsTile = getTiles()[Client.instance.getPlane()][tileX][tileY];
        final boolean checkClick = Client.instance.isCheckClick();

        if (!Client.instance.isGpu())
        {
            drawTileOverlaySD(tileX,pitchSin,yawSin,tile,pitchCos,tileY, yawCos);

            if (roofRemovalMode == 0 || !checkClick || rsTile == null || rsTile.getSceneTileModel() != tile || rsTile.getPhysicalLevel() != Client.instance.getPlane())
            {
                return;
            }
        }
        final DrawCallbacks drawCallbacks = Client.instance.getDrawCallbacks();

        if (drawCallbacks == null)
        {
            return;
        }

        try
        {
            final int cameraX2 = Client.instance.getCameraX2();
            final int cameraY2 = Client.instance.getCameraY2();
            final int cameraZ2 = Client.instance.getCameraZ2();
            final int zoom = Client.instance.get3dZoom();
            final int centerX = Client.instance.getCenterX();
            final int centerY = Client.instance.getCenterY();

            drawCallbacks.drawSceneModel(0, pitchSin, pitchCos, yawSin, yawCos, -cameraX2, -cameraY2, -cameraZ2,
                    tile, Client.instance.getPlane(), tileX, tileY,
                    zoom, centerX, centerY);



            RSSceneTileModel tileModel = tile;

            final int[] faceX = tileModel.getFaceX();
            final int[] faceY = tileModel.getFaceY();
            final int[] faceZ = tileModel.getFaceZ();

            final int[] vertexX = tileModel.getVertexX();
            final int[] vertexY = tileModel.getVertexY();
            final int[] vertexZ = tileModel.getVertexZ();

            final int vertexCount = vertexX.length;
            final int faceCount = faceX.length;

            final int mouseX2 = Client.instance.getMouseX2();
            final int mouseY2 = Client.instance.getMouseY2();

            for (int i = 0; i < vertexCount; ++i)
            {
                int vx = vertexX[i] - cameraX2;
                int vy = vertexY[i] - cameraY2;
                int vz = vertexZ[i] - cameraZ2;

                int rotA = vz * yawSin + vx * yawCos >> 16;
                int rotB = vz * yawCos - vx * yawSin >> 16;

                int var13 = vy * pitchCos - rotB * pitchSin >> 16;
                int var12 = vy * pitchSin + rotB * pitchCos >> 16;
                if (var12 < 50)
                {
                    return;
                }

                int ax = rotA * zoom / var12 + centerX;
                int ay = var13 * zoom / var12 + centerY;

                tmpX[i] = ax;
                tmpY[i] = ay;
            }

            for (int i = 0; i < faceCount; ++i)
            {
                int va = faceX[i];
                int vb = faceY[i];
                int vc = faceZ[i];

                int x1 = tmpX[va];
                int x2 = tmpX[vb];
                int x3 = tmpX[vc];

                int y1 = tmpY[va];
                int y2 = tmpY[vb];
                int y3 = tmpY[vc];

                if ((x1 - x2) * (y3 - y2) - (y1 - y2) * (x3 - x2) > 0)
                {

                    if (checkClick && Client.instance.containsBounds(mouseX2, mouseY2, y1, y2, y3, x1, x2, x3)) {
                        setTargetTile(tileX, tileY);
                    }
                    if (Client.instance.containsBounds(Client.instance.getMouseX(), Client.instance.getMouseY(), y1, y2, y3, x1, x2, x3)) {
                        hoverTile(tileX, tileY, rsTile.getPhysicalLevel());
                    }

                }
            }
        }
        catch (Exception ex)
        {
            Client.instance.getLogger().warn("error during overlay rendering", ex);
        }
    }

    private void drawTileOverlaySD(int i, int j, int k, ShapedTile class40, int l, int i1, int j1) {
        int k1 = class40.origVertexX.length;
        for (int l1 = 0; l1 < k1; l1++) {
            int i2 = class40.origVertexX[l1] - xCameraPos;
            int k2 = class40.origVertexY[l1] - zCameraPos;
            int i3 = class40.origVertexZ[l1] - yCameraPos;
            int k3 = i3 * k + i2 * j1 >> 16;
            i3 = i3 * j1 - i2 * k >> 16;
            i2 = k3;
            k3 = k2 * l - i3 * j >> 16;
            i3 = k2 * j + i3 * l >> 16;
            k2 = k3;
            if (i3 < 50) {
                return;
            }
            if (Client.getOption("hd_tex") || class40.triangleTexture != null) {
                ShapedTile.anIntArray690[l1] = i2;
                ShapedTile.anIntArray691[l1] = k2;
                ShapedTile.anIntArray692[l1] = i3;
            }
            ShapedTile.anIntArray688[l1] = Rasterizer.textureInt1 + i2 * Rasterizer.fieldOfView / i3;
            ShapedTile.anIntArray689[l1] = Rasterizer.textureInt2 + k2 * Rasterizer.fieldOfView / i3;
            ShapedTile.depthPoint[l1] = i3;
        }

        Rasterizer.anInt1465 = 0;
        k1 = class40.triangleA.length;
        for (int j2 = 0; j2 < k1; j2++) {
            int l2 = class40.triangleA[j2];
            int j3 = class40.triangleB[j2];
            int l3 = class40.triangleC[j2];
            int i4 = ShapedTile.anIntArray688[l2];
            int j4 = ShapedTile.anIntArray688[j3];
            int k4 = ShapedTile.anIntArray688[l3];
            int l4 = ShapedTile.anIntArray689[l2];
            int i5 = ShapedTile.anIntArray689[j3];
            int j5 = ShapedTile.anIntArray689[l3];
            int z1 = ShapedTile.depthPoint[l2];
            int z2 = ShapedTile.depthPoint[j3];
            int z3 = ShapedTile.depthPoint[l3];
            if ((i4 - j4) * (j5 - i5) - (l4 - i5) * (k4 - j4) > 0) {
                Rasterizer.aBoolean1462 = i4 < 0 || j4 < 0 || k4 < 0 || i4 > DrawingArea.viewportRX || j4 > DrawingArea.viewportRX || k4 > DrawingArea.viewportRX;
                if (aBoolean467 && method318(clickX, clickY, l4, i5, j5, i4, j4, k4)) {
                    clickedTileX = i;
                    clickedTileY = i1;
                }
                if (inBounds(Client.instance.getMouseX(), Client.instance.getMouseY(), l4, i5, j5, i4, j4, k4)) {
                    System.out.println("hover OVERLAYSD");
                    hoverX = j1;
                    hoverY = k1;
                }
                if (class40.triangleTexture == null || class40.triangleTexture[j2] == -1 || class40.triangleTexture[j2] > 50) {
                    if (class40.triangleHslA[j2] != 0xbc614e) {
                        if (Client.getOption("hd_tex") && class40.triangleTexture != null && class40.triangleTexture[j2] != -1) {
                            if (class40.aBoolean683 || class40.triangleTexture[j2] == 505) {
                                Rasterizer.drawMaterializedTriangle(l4, i5, j5, i4, j4, k4, class40.triangleHslA[j2], class40.triangleHslB[j2], class40.triangleHslC[j2], ShapedTile.anIntArray690[0], ShapedTile.anIntArray690[1], ShapedTile.anIntArray690[3], ShapedTile.anIntArray691[0], ShapedTile.anIntArray691[1], ShapedTile.anIntArray691[3], ShapedTile.anIntArray692[0], ShapedTile.anIntArray692[1], ShapedTile.anIntArray692[3], class40.triangleTexture[j2], (float) z1, (float) z2, (float) z3);
                            } else {
                                Rasterizer.drawMaterializedTriangle(l4, i5, j5, i4, j4, k4, class40.triangleHslA[j2], class40.triangleHslB[j2], class40.triangleHslC[j2], ShapedTile.anIntArray690[l2], ShapedTile.anIntArray690[j3], ShapedTile.anIntArray690[l3], ShapedTile.anIntArray691[l2], ShapedTile.anIntArray691[j3], ShapedTile.anIntArray691[l3], ShapedTile.anIntArray692[l2], ShapedTile.anIntArray692[j3], ShapedTile.anIntArray692[l3], class40.triangleTexture[j2], (float) z1, (float) z2, (float) z3);
                            }
                        } else {
                            Rasterizer.drawGouraudTriangle(l4, i5, j5, i4, j4, k4, class40.triangleHslA[j2], class40.triangleHslB[j2], class40.triangleHslC[j2], ShapedTile.anIntArray692[l2], ShapedTile.anIntArray692[j3], ShapedTile.anIntArray692[l3], 0);
                        }
                    }
                } else if (!lowMem) {
                    if (class40.aBoolean683) {
                        Rasterizer.drawTexturedTriangle(l4, i5, j5, i4, j4, k4, class40.triangleHslA[j2], class40.triangleHslB[j2], class40.triangleHslC[j2], ShapedTile.anIntArray690[0], ShapedTile.anIntArray690[1], ShapedTile.anIntArray690[3], ShapedTile.anIntArray691[0], ShapedTile.anIntArray691[1], ShapedTile.anIntArray691[3], ShapedTile.anIntArray692[0], ShapedTile.anIntArray692[1], ShapedTile.anIntArray692[3], class40.triangleTexture[j2], (float) z1, (float) z2, (float) z3, 0);
                    } else {
                        Rasterizer.drawTexturedTriangle(l4, i5, j5, i4, j4, k4, class40.triangleHslA[j2], class40.triangleHslB[j2], class40.triangleHslC[j2], ShapedTile.anIntArray690[l2], ShapedTile.anIntArray690[j3], ShapedTile.anIntArray690[l3], ShapedTile.anIntArray691[l2], ShapedTile.anIntArray691[j3], ShapedTile.anIntArray691[l3], ShapedTile.anIntArray692[l2], ShapedTile.anIntArray692[j3], ShapedTile.anIntArray692[l3], class40.triangleTexture[j2], (float) z1, (float) z2, (float) z3, 0);
                    }
                } else {
                    int k5 = anIntArray485[class40.triangleTexture[j2]];
                    Rasterizer.drawGouraudTriangle(l4, i5, j5, i4, j4, k4, mixColour(k5, class40.triangleHslA[j2]), mixColour(k5, class40.triangleHslB[j2]), mixColour(k5, class40.triangleHslC[j2]), (float) z1, (float) z2, (float) z3, 0);
                }
            }
        }
    }

    private int mixColour(int colour1, int colour2) {
        colour2 = 127 - colour2;
        colour2 = (colour2 * (colour1 & 0x7f)) / 160;
        if (colour2 < 2)
            colour2 = 2;
        else if (colour2 > 126)
            colour2 = 126;
        return (colour1 & 0xff80) + colour2;
    }

    public boolean method318(int mouseX, int mouseY, int triangleYA, int triangleYB, int triangleYC,
                             int triangleXA, int triangleXB, int triangleXC) {
        if (mouseY < triangleYA && mouseY < triangleYB && mouseY < triangleYC)
            return false;
        if (mouseY > triangleYA && mouseY > triangleYB && mouseY > triangleYC)
            return false;
        if (mouseX < triangleXA && mouseX < triangleXB && mouseX < triangleXC)
            return false;
        if (mouseX > triangleXA && mouseX > triangleXB && mouseX > triangleXC)
            return false;
        int i2 = (mouseY - triangleYA) * (triangleXB - triangleXA) - (mouseX - triangleXA) * (triangleYB - triangleYA);
        int j2 = (mouseY - triangleYC) * (triangleXA - triangleXC) - (mouseX - triangleXC) * (triangleYA - triangleYC);
        int k2 = (mouseY - triangleYB) * (triangleXC - triangleXB) - (mouseX - triangleXB) * (triangleYC - triangleYB);
        return i2 * k2 > 0 && k2 * j2 > 0;
    }

    private void processCulling() {
        int count = sceneClusterCounts[currentRenderPlane];
        CullingCluster clusters[] = cullingClusters[currentRenderPlane];
        anInt475 = 0;
        for (int k = 0; k < count; k++) {
            CullingCluster cluster = clusters[k];
            if (cluster.orientation == 1) {
                int xDistFromCamStart = (cluster.startXLoc - screenCenterX) + 25;
                if (xDistFromCamStart < 0 || xDistFromCamStart > 50)
                    continue;
                int yDistFromCamStart = (cluster.startYLoc - screenCenterZ) + 25;
                if (yDistFromCamStart < 0)
                    yDistFromCamStart = 0;
                int yDistFromCamEnd = (cluster.tileEndY - screenCenterZ) + 25;
                if (yDistFromCamEnd > 50)
                    yDistFromCamEnd = 50;
                boolean visisble = false;
                while (yDistFromCamStart <= yDistFromCamEnd)
                    if (renderArea[xDistFromCamStart][yDistFromCamStart++]) {
                        visisble = true;
                        break;
                    }
                if (!visisble)
                    continue;
                int xDistFromCamStartReal = xCameraPos - cluster.startXPos;
                if (xDistFromCamStartReal > 32) {
                    cluster.cullDirection = 1;
                } else {
                    if (xDistFromCamStartReal >= -32)
                        continue;
                    cluster.cullDirection = 2;
                    xDistFromCamStartReal = -xDistFromCamStartReal;
                }
                cluster.worldDistanceFromCameraStartY = (cluster.startYPos - yCameraPos << 8) / xDistFromCamStartReal;
                cluster.worldDistanceFromCameraEndY = (cluster.endYPos - yCameraPos << 8) / xDistFromCamStartReal;
                cluster.worldDistanceFromCameraStartZ = (cluster.startZPos - zCameraPos << 8) / xDistFromCamStartReal;
                cluster.worldDistanceFromCameraEndZ = (cluster.endZPos - zCameraPos << 8) / xDistFromCamStartReal;
                processedClusters[anInt475++] = cluster;
                continue;
            }
            if (cluster.orientation == 2) {
                int yDIstFromCamStart = (cluster.startYLoc - screenCenterZ) + 25;
                if (yDIstFromCamStart < 0 || yDIstFromCamStart > 50)
                    continue;
                int xDistFromCamStart = (cluster.startXLoc - screenCenterX) + 25;
                if (xDistFromCamStart < 0)
                    xDistFromCamStart = 0;
                int xDistFromCamEnd = (cluster.tileEndX - screenCenterX) + 25;
                if (xDistFromCamEnd > 50)
                    xDistFromCamEnd = 50;
                boolean visible = false;
                while (xDistFromCamStart <= xDistFromCamEnd)
                    if (renderArea[xDistFromCamStart++][yDIstFromCamStart]) {
                        visible = true;
                        break;
                    }
                if (!visible)
                    continue;
                int yDistFromCamStartReal = yCameraPos - cluster.startYPos;
                if (yDistFromCamStartReal > 32) {
                    cluster.cullDirection = 3;
                } else {
                    if (yDistFromCamStartReal >= -32)
                        continue;
                    cluster.cullDirection = 4;
                    yDistFromCamStartReal = -yDistFromCamStartReal;
                }
                cluster.worldDistanceFromCameraStartX = (cluster.startXPos - xCameraPos << 8) / yDistFromCamStartReal;
                cluster.worldDistanceFromCameraEndX = (cluster.worldEndX - xCameraPos << 8) / yDistFromCamStartReal;
                cluster.worldDistanceFromCameraStartZ = (cluster.startZPos - zCameraPos << 8) / yDistFromCamStartReal;
                cluster.worldDistanceFromCameraEndZ = (cluster.endZPos - zCameraPos << 8) / yDistFromCamStartReal;
                processedClusters[anInt475++] = cluster;
            } else if (cluster.orientation == 4) {
                int yDistFromCamStartReal = cluster.startZPos - zCameraPos;
                if (yDistFromCamStartReal > 128) {
                    int yDistFromCamStart = (cluster.startYLoc - screenCenterZ) + 25;
                    if (yDistFromCamStart < 0)
                        yDistFromCamStart = 0;
                    int yDistFromCamEnd = (cluster.tileEndY - screenCenterZ) + 25;
                    if (yDistFromCamEnd > 50)
                        yDistFromCamEnd = 50;
                    if (yDistFromCamStart <= yDistFromCamEnd) {
                        int xDistFromCamStart = (cluster.startXLoc - screenCenterX) + 25;
                        if (xDistFromCamStart < 0)
                            xDistFromCamStart = 0;
                        int xDistFromCamEnd = (cluster.tileEndX - screenCenterX) + 25;
                        if (xDistFromCamEnd > 50)
                            xDistFromCamEnd = 50;
                        boolean visisble = false;
                        label0:
                        for (int _x = xDistFromCamStart; _x <= xDistFromCamEnd; _x++) {
                            for (int _y = yDistFromCamStart; _y <= yDistFromCamEnd; _y++) {
                                if (!renderArea[_x][_y])
                                    continue;
                                visisble = true;
                                break label0;
                            }

                        }

                        if (visisble) {
                            cluster.cullDirection = 5;
                            cluster.worldDistanceFromCameraStartX = (cluster.startXPos - xCameraPos << 8)
                                    / yDistFromCamStartReal;
                            cluster.worldDistanceFromCameraEndX = (cluster.worldEndX - xCameraPos << 8)
                                    / yDistFromCamStartReal;
                            cluster.worldDistanceFromCameraStartY = (cluster.startYPos - yCameraPos << 8)
                                    / yDistFromCamStartReal;
                            cluster.worldDistanceFromCameraEndY = (cluster.endYPos - yCameraPos << 8)
                                    / yDistFromCamStartReal;
                            processedClusters[anInt475++] = cluster;
                        }
                    }
                }
            }
        }

    }

    private boolean method320(int y, int x, int z) {
        int l = anIntArrayArrayArray445[y][x][z];
        if (l == -cycle)
            return false;
        if (l == cycle)
            return true;
        int i1 = x << 7;
        int j1 = z << 7;
        if (method324(i1 + 1, heightMap[y][x][z], j1 + 1) && method324((i1 + 128) - 1, heightMap[y][x + 1][z], j1 + 1)
                && method324((i1 + 128) - 1, heightMap[y][x + 1][z + 1], (j1 + 128) - 1)
                && method324(i1 + 1, heightMap[y][x][z + 1], (j1 + 128) - 1)) {
            anIntArrayArrayArray445[y][x][z] = cycle;
            return true;
        } else {
            anIntArrayArrayArray445[y][x][z] = -cycle;
            return false;
        }
    }

    private boolean method321(int z, int x, int y, int l) {
        if (!method320(z, x, y))
            return false;
        int i1 = x << 7;
        int j1 = y << 7;
        int k1 = heightMap[z][x][y] - 1;
        int l1 = k1 - 120;
        int i2 = k1 - 230;
        int j2 = k1 - 238;
        if (l < 16) {
            if (l == 1) {
                if (i1 > xCameraPos) {
                    if (!method324(i1, k1, j1))
                        return false;
                    if (!method324(i1, k1, j1 + 128))
                        return false;
                }
                if (z > 0) {
                    if (!method324(i1, l1, j1))
                        return false;
                    if (!method324(i1, l1, j1 + 128))
                        return false;
                }
                return method324(i1, i2, j1) && method324(i1, i2, j1 + 128);
            }
            if (l == 2) {
                if (j1 < yCameraPos) {
                    if (!method324(i1, k1, j1 + 128))
                        return false;
                    if (!method324(i1 + 128, k1, j1 + 128))
                        return false;
                }
                if (z > 0) {
                    if (!method324(i1, l1, j1 + 128))
                        return false;
                    if (!method324(i1 + 128, l1, j1 + 128))
                        return false;
                }
                return method324(i1, i2, j1 + 128) && method324(i1 + 128, i2, j1 + 128);
            }
            if (l == 4) {
                if (i1 < xCameraPos) {
                    if (!method324(i1 + 128, k1, j1))
                        return false;
                    if (!method324(i1 + 128, k1, j1 + 128))
                        return false;
                }
                if (z > 0) {
                    if (!method324(i1 + 128, l1, j1))
                        return false;
                    if (!method324(i1 + 128, l1, j1 + 128))
                        return false;
                }
                return method324(i1 + 128, i2, j1) && method324(i1 + 128, i2, j1 + 128);
            }
            if (l == 8) {
                if (j1 > yCameraPos) {
                    if (!method324(i1, k1, j1))
                        return false;
                    if (!method324(i1 + 128, k1, j1))
                        return false;
                }
                if (z > 0) {
                    if (!method324(i1, l1, j1))
                        return false;
                    if (!method324(i1 + 128, l1, j1))
                        return false;
                }
                return method324(i1, i2, j1) && method324(i1 + 128, i2, j1);
            }
        }
        if (!method324(i1 + 64, j2, j1 + 64))
            return false;
        if (l == 16)
            return method324(i1, i2, j1 + 128);
        if (l == 32)
            return method324(i1 + 128, i2, j1 + 128);
        if (l == 64)
            return method324(i1 + 128, i2, j1);
        if (l == 128) {
            return method324(i1, i2, j1);
        } else {
            System.out.println("Warning unsupported wall type");
            return true;
        }
    }

    private boolean method322(int i, int j, int k, int l) {
        if (!method320(i, j, k))
            return false;
        int i1 = j << 7;
        int j1 = k << 7;
        return method324(i1 + 1, heightMap[i][j][k] - l, j1 + 1)
                && method324((i1 + 128) - 1, heightMap[i][j + 1][k] - l, j1 + 1)
                && method324((i1 + 128) - 1, heightMap[i][j + 1][k + 1] - l, (j1 + 128) - 1)
                && method324(i1 + 1, heightMap[i][j][k + 1] - l, (j1 + 128) - 1);
    }

    private boolean method323(int y, int x, int k, int z, int i1, int j1) {
        if (x == k && z == i1) {
            if (!method320(y, x, z))
                return false;
            int k1 = x << 7;
            int i2 = z << 7;
            return method324(k1 + 1, heightMap[y][x][z] - j1, i2 + 1)
                    && method324((k1 + 128) - 1, heightMap[y][x + 1][z] - j1, i2 + 1)
                    && method324((k1 + 128) - 1, heightMap[y][x + 1][z + 1] - j1, (i2 + 128) - 1)
                    && method324(k1 + 1, heightMap[y][x][z + 1] - j1, (i2 + 128) - 1);
        }
        for (int l1 = x; l1 <= k; l1++) {
            for (int j2 = z; j2 <= i1; j2++)
                if (anIntArrayArrayArray445[y][l1][j2] == -cycle)
                    return false;

        }

        int k2 = (x << 7) + 1;
        int l2 = (z << 7) + 2;
        int i3 = heightMap[y][x][z] - j1;
        if (!method324(k2, i3, l2))
            return false;
        int j3 = (k << 7) - 1;
        if (!method324(j3, i3, l2))
            return false;
        int k3 = (i1 << 7) - 1;
        return method324(k2, i3, k3) && method324(j3, i3, k3);
    }

    private boolean method324(int x, int y, int z) {
        for (int l = 0; l < anInt475; l++) {
            CullingCluster cluster = processedClusters[l];
            if (cluster.cullDirection == 1) {
                int i1 = cluster.startXPos - x;
                if (i1 > 0) {
                    int j2 = cluster.startYPos + (cluster.worldDistanceFromCameraStartY * i1 >> 8);
                    int k3 = cluster.endYPos + (cluster.worldDistanceFromCameraEndY * i1 >> 8);
                    int l4 = cluster.startZPos + (cluster.worldDistanceFromCameraStartZ * i1 >> 8);
                    int i6 = cluster.endZPos + (cluster.worldDistanceFromCameraEndZ * i1 >> 8);
                    if (z >= j2 && z <= k3 && y >= l4 && y <= i6)
                        return true;
                }
            } else if (cluster.cullDirection == 2) {
                int j1 = x - cluster.startXPos;
                if (j1 > 0) {
                    int k2 = cluster.startYPos + (cluster.worldDistanceFromCameraStartY * j1 >> 8);
                    int l3 = cluster.endYPos + (cluster.worldDistanceFromCameraEndY * j1 >> 8);
                    int i5 = cluster.startZPos + (cluster.worldDistanceFromCameraStartZ * j1 >> 8);
                    int j6 = cluster.endZPos + (cluster.worldDistanceFromCameraEndZ * j1 >> 8);
                    if (z >= k2 && z <= l3 && y >= i5 && y <= j6)
                        return true;
                }
            } else if (cluster.cullDirection == 3) {
                int k1 = cluster.startYPos - z;
                if (k1 > 0) {
                    int l2 = cluster.startXPos + (cluster.worldDistanceFromCameraStartX * k1 >> 8);
                    int i4 = cluster.worldEndX + (cluster.worldDistanceFromCameraEndX * k1 >> 8);
                    int j5 = cluster.startZPos + (cluster.worldDistanceFromCameraStartZ * k1 >> 8);
                    int k6 = cluster.endZPos + (cluster.worldDistanceFromCameraEndZ * k1 >> 8);
                    if (x >= l2 && x <= i4 && y >= j5 && y <= k6)
                        return true;
                }
            } else if (cluster.cullDirection == 4) {
                int l1 = z - cluster.startYPos;
                if (l1 > 0) {
                    int i3 = cluster.startXPos + (cluster.worldDistanceFromCameraStartX * l1 >> 8);
                    int j4 = cluster.worldEndX + (cluster.worldDistanceFromCameraEndX * l1 >> 8);
                    int k5 = cluster.startZPos + (cluster.worldDistanceFromCameraStartZ * l1 >> 8);
                    int l6 = cluster.endZPos + (cluster.worldDistanceFromCameraEndZ * l1 >> 8);
                    if (x >= i3 && x <= j4 && y >= k5 && y <= l6)
                        return true;
                }
            } else if (cluster.cullDirection == 5) {
                int i2 = y - cluster.startZPos;
                if (i2 > 0) {
                    int j3 = cluster.startXPos + (cluster.worldDistanceFromCameraStartX * i2 >> 8);
                    int k4 = cluster.worldEndX + (cluster.worldDistanceFromCameraEndX * i2 >> 8);
                    int l5 = cluster.startYPos + (cluster.worldDistanceFromCameraStartY * i2 >> 8);
                    int i7 = cluster.endYPos + (cluster.worldDistanceFromCameraEndY * i2 >> 8);
                    if (x >= j3 && x <= k4 && z >= l5 && z <= i7)
                        return true;
                }
            }
        }

        return false;
    }

    /**
     * Runelite
     */
    private int drawDistance = 25;

    @Override
    public int getDrawDistance() {
        return drawDistance;
    }

    @Override
    public void setDrawDistance(int drawDistance) {
        this.drawDistance = drawDistance;
    }

    @Override
    public void generateHouses() {

    }

    @Override
    public void setRoofRemovalMode(int flags) {
        roofRemovalMode = flags;
    }

    @Override
    public RSGameObject[] getObjects() {
        return interactableObjectCache;
    }

    @Override
    public RSTile[][][] getTiles() {
        return tileArray;
    }

    /**
     * Adds an item to the scene
     *
     * @param id
     * @param quantity
     * @param point
     */
    @Override
    public void addItem(int id, int quantity, WorldPoint point) {
        final int sceneX = point.getX() - Client.instance.getBaseX();
        final int sceneY = point.getY() - Client.instance.getBaseY();
        final int plane = point.getPlane();

        if (sceneX < 0 || sceneY < 0 || sceneX >= 104 || sceneY >= 104)
        {
            return;
        }

        RSTileItem item = Client.instance.newTileItem();
        item.setId(id);
        item.setQuantity(quantity);
        RSNodeDeque[][][] groundItems = Client.instance.getGroundItemDeque();

        if (groundItems[plane][sceneX][sceneY] == null)
        {
            groundItems[plane][sceneX][sceneY] = Client.instance.newNodeDeque();
        }

        groundItems[plane][sceneX][sceneY].addFirst(item);

        if (plane == Client.instance.getPlane())
        {
            Client.instance.updateItemPile(sceneX, sceneY);
        }
    }

    /**
     * Removes an item from the scene
     *
     * @param id
     * @param quantity
     * @param point
     */
    @Override
    public void removeItem(int id, int quantity, WorldPoint point) {
        final int sceneX = point.getX() - Client.instance.getBaseX();
        final int sceneY = point.getY() - Client.instance.getBaseY();
        final int plane = point.getPlane();

        if (sceneX < 0 || sceneY < 0 || sceneX >= 104 || sceneY >= 104)
        {
            return;
        }

        RSNodeDeque items = Client.instance.getGroundItemDeque()[plane][sceneX][sceneY];

        if (items == null)
        {
            return;
        }

        for (RSTileItem item = (RSTileItem) items.last(); item != null; item = (RSTileItem) items.previous())
        {
            if (item.getId() == id && quantity == 1)
            {
                item.unlink();
                break;
            }
        }

        if (items.last() == null)
        {
            Client.instance.getGroundItemDeque()[plane][sceneX][sceneY] = null;
        }

        Client.instance.updateItemPile(sceneX, sceneY);
    }

    @Override
    public int[][] getTileShape2D() {
        return tileSHapePoints;
    }

    @Override
    public int[][] getTileRotation2D() {
        return tileShapeIndices;
    }

    @Override
    public void draw(net.runelite.api.Tile tile, boolean var2) {
        renderTile((Tile) tile, false);
    }

    @Override
    public int[][][] getTileHeights() {
        return heightMap;
    }

    @Override
    public int getBaseX() {
        return Client.instance.getBaseX();
    }

    @Override
    public int getBaseY() {
        return Client.instance.getBaseY();
    }

    @Override
    public boolean isInstance() {
        return Client.instance.isInInstancedRegion();
    }

    @Override
    public int[][][] getInstanceTemplateChunks() {
        return Client.instance.getInstanceTemplateChunks();
    }

    @Override
    public void drawTile(int[] pixels, int pixelOffset, int width, int z, int x, int y) {

    }

    @Override
    public void updateOccluders() {
        processCulling();
    }

    @Override
    public int getMaxX() {
        return maxX;
    }

    @Override
    public int getMaxY() {
        return maxY;
    }

    @Override
    public int getMaxZ() {
        return maxZ;
    }

    @Override
    public int getMinLevel() {
        return currentHL;
    }

    @Override
    public void setMinLevel(int lvl) {
        this.currentHL = lvl;
    }

    @Override
    public void newGroundItemPile(int plane, int x, int y, int hash, RSRenderable var5, long var6, RSRenderable var7, RSRenderable var8) {

    }

    @Override
    public boolean newGameObject(int plane, int startX, int startY, int var4, int var5, int centerX, int centerY,
                                 int height, RSRenderable entity, int orientation, boolean tmp, long tag, int flags) {
        return false;
    }

    @Override
    public void removeGameObject(net.runelite.api.GameObject gameObject) {
        removeGameObject(gameObject.getPlane(),gameObject.getX(),gameObject.getY());
    }

    @Override
    public void removeGameObject(int plane, int x, int y) {

    }

    @Override
    public void removeWallObject(net.runelite.api.WallObject wallObject) {
        removeWallObject(wallObject.getPlane(),wallObject.getX(),wallObject.getY());
    }

    public void removeWallObject(WallObject wallObject)
    {
//        final RSTile[][][] tiles = getTiles();
//
//        for (int y = 0; y < 104; ++y)
//        {
//            for (int x = 0; x < 104; ++x)
//            {
//                RSTile tile = tiles[Client.instance.getPlane()][x][y];
//                if (tile != null && tile.getWallObject() == wallObject)
//                {
//                    tile.setWallObject(null);
//                }
//            }
//        }
    }

    @Override
    public void removeDecorativeObject(DecorativeObject decorativeObject)
    {
        final RSTile[][][] tiles = getTiles();

        for (int y = 0; y < 104; ++y)
        {
            for (int x = 0; x < 104; ++x)
            {
                RSTile tile = tiles[Client.instance.getPlane()][x][y];
                if (tile != null && tile.getDecorativeObject() == decorativeObject)
                {
                    tile.setDecorativeObject(null);
                }
            }
        }
    }

    @Override
    public void removeDecorativeObject(int plane, int x, int y) {

    }


    @Override
    public void removeGroundObject(GroundObject groundObject)
    {
        final RSTile[][][] tiles = getTiles();

        for (int y = 0; y < 104; ++y)
        {
            for (int x = 0; x < 104; ++x)
            {
                RSTile tile = tiles[Client.instance.getPlane()][x][y];
                if (tile != null && tile.getGroundObject() == groundObject)
                {
                    tile.setGroundObject(null);
                }
            }
        }
    }

    @Override
    public void removeGroundObject(int plane, int x, int y) {
    }

    @Override
    public short[][][] getUnderlayIds() {
        return Client.instance.objectManager.underlay;
    }

    @Override
    public void setUnderlayIds(short[][][] underlayIds) {
        Client.instance.objectManager.underlay = underlayIds;
    }

    @Override
    public short[][][] getOverlayIds() {
        return Client.instance.objectManager.overlay;
    }

    @Override
    public void setOverlayIds(short[][][] overlayIds) {
        Client.instance.objectManager.overlay = overlayIds;
    }

    @Override
    public byte[][][] getTileShapes() {
        return Client.instance.objectManager.overlayClippingPaths;
    }

    @Override
    public void setTileShapes(byte[][][] tileShapes) {
        Client.instance.objectManager.overlayClippingPaths = tileShapes;
    }

    @Override
    public void menuOpen(int selectedPlane, int screenX, int screenY, boolean viewportWalking) {

    }
    
}