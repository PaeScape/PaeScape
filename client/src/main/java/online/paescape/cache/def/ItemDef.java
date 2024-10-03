package online.paescape.cache.def;

import net.runelite.client.RuneLite;
import online.paescape.cache.CacheArchive;
import online.paescape.cache.Decompressor;
import online.paescape.cache.media.sprite.Sprite;
import online.paescape.collection.MemCache;
import online.paescape.media.DrawingArea;
import online.paescape.media.Rasterizer;
import online.paescape.media.animable.Model;
import online.paescape.net.Stream;
import online.paescape.util.Configuration;
import online.paescape.util.DataType;
import online.paescape.util.FileOperations;
import online.paescape.util.Signlink;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.system.Callback.get;


public final class ItemDef {

    public static final int OSRS_ITEMS_OFFSET = 200_000;
    public static final int CACHE_SIZE = 25_000;
    public static final int[] UNTRADEABLE_ITEMS
            = {13661, 13262,
            6529, 6950, 1464, 2996, 2677, 2678, 2679, 2680, 2682,
            2683, 2684, 2685, 2686, 2687, 2688, 2689, 2690,
            6570, 12158, 12159, 12160, 12163, 12161, 12162,
            19143, 19149, 19146, 19157, 19162, 19152, 4155,
            8850, 10551, 8839, 8840, 8842, 11663, 11664,
            11665, 3842, 3844, 3840, 8844, 8845, 8846, 8847,
            8848, 8849, 8850, 10551, 7462, 7461, 7460,
            7459, 7458, 7457, 7456, 7455, 7454, 7453, 8839,
            8840, 8842, 11663, 11664, 11665, 10499, 9748,
            9754, 9751, 9769, 9757, 9760, 9763, 9802, 9808,
            9784, 9799, 9805, 9781, 9796, 9793, 9775, 9772,
            9778, 9787, 9811, 9766, 9749, 9755, 9752, 9770,
            9758, 9761, 9764, 9803, 9809, 9785, 9800, 9806,
            9782, 9797, 9794, 9776, 9773, 9779, 9788, 9812,
            9767, 9747, 9753, 9750, 9768, 9756, 9759, 9762,
            9801, 9807, 9783, 9798, 9804, 9780, 9795, 9792,
            9774, 9771, 9777, 9786, 9810, 9765, 9948, 9949,
            9950, 12169, 12170, 12171, 20671, 14641, 14642,
            6188, 10954, 10956, 10958,
            3057, 3058, 3059, 3060, 3061,
            7594, 7592, 7593, 7595, 7596,
            14076, 14077, 14081,
            10840, 10836, 6858, 6859, 10837, 10838, 10839,
            9925, 9924, 9923, 9922, 9921,
            4084, 4565, 20046, 20044, 20045,
            1050, 14595, 14603, 14602, 14605, 11789,
            19708, 19706, 19707,
            4860, 4866, 4872, 4878, 4884, 4896, 4890, 4896, 4902,
            4932, 4938, 4944, 4950, 4908, 4914, 4920, 4926, 4956,
            4926, 4968, 4994, 4980, 4986, 4992, 4998,
            18778, 18779, 18780, 18781,
            13450, 13444, 13405, 15502,
            10548, 10549, 10550, 10551, 10555, 10552, 10553, 2412, 2413, 2414,
            20747,
            18365, 18373, 18371, 15246, 12964, 12971, 12978, 14017,
            757, 8851,
            13855, 13848, 13857, 13856, 13854, 13853, 13852, 13851, 13850, 5509, 13653, 14021, 14020, 19111, 14019, 14022,
            19785, 19786, 18782, 18351, 18349, 18353, 18357, 18355, 18359, 18335
    };
    private static final List<Integer> untradeableItems = new ArrayList<Integer>();
    public static int slotOSRS = -1;
    public static MemCache spriteCache = new MemCache(100);
    public static MemCache modelCache = new MemCache(50);
    public static ItemDef[] cache;
    public static ItemDef[] cacheOSRS;
    public static int cacheIndexOSRS;
    public static int cacheIndex;
    public static MemCache modelCacheOSRS = new MemCache(50);
    public static Stream stream;
    public static int[] streamIndices;
    public static Stream streamOSRS;
    public static int[] streamIndicesOSRS;
    public static int totalItems;
    public static int totalItemsOSRS;
    private static int[] prices;
    public int opcode139, opcode140, opcode148, opcode149;
    public byte femaleYOffset;
    public int value;
    public int[] editedModelColor;
    public int id;
    public int[] newModelColor;
    public boolean membersObject;
    public int femaleEquip3;
    public int certTemplateID;
    public int femaleEquip2;
    public int maleEquip1;
    public int maleDialogueModel;
    public int sizeX;
    public String[] groundActions;
    public int modelOffset1;
    public String name;
    public int femaleDialogueModel;
    public int modelID;
    public int maleDialogue;
    public boolean stackable;
    public String description;
    public int certID;
    public int modelZoom;
    public int lightness;
    public int maleEquip3;
    public int maleEquip2;
    public String[] actions;
    public int rotationY;
    public int sizeZ;
    public int sizeY;
    public int[] stackIDs;
    public int modelOffsetY;
    public int shadow;
    public int femaleDialogue;
    public int rotationX;
    public int rotationZ;
    public int femaleEquip1;
    public int[] stackAmounts;
    public int team;
    public int modelOffsetX;
    public byte maleYOffset;
    public byte maleXOffset;
    public int lendID;
    public int lentItemID;
    public boolean untradeable;
    public boolean animateInventory;
    public DataType dataType = DataType.REGULAR;
    private int[] originalModelColor;
    private short[] originalTextureColors;
    private short[] modifiedTextureColors;

    public ItemDef() {
        id = -1;
    }

    public static void nullLoader() {
        modelCache = null;
        modelCacheOSRS = null;
        spriteCache = null;
        streamIndices = null;
        streamIndicesOSRS = null;
        cache = null;
        cacheOSRS = null;
        stream = null;
        streamOSRS = null;
    }

    public static void unpackConfig(CacheArchive streamLoader) {

        stream = new Stream(streamLoader.getDataForName("obj.dat"));
        Stream stream = new Stream(streamLoader.getDataForName("obj.idx"));


        streamOSRS = new Stream(FileOperations.readFile(Signlink.getCacheDirectory() + "obj.dat"));
        Stream streamOSRS1 = new Stream(FileOperations.readFile(Signlink.getCacheDirectory() + "obj.idx"));

        totalItems = stream.readUnsignedWord();
        totalItemsOSRS = streamOSRS1.readUnsignedWord();

        streamIndices = new int[totalItems + 1000];
        streamIndicesOSRS = new int[totalItemsOSRS];

        if (Configuration.DEBUG) {
            System.out.println("Item id total amount : " + totalItems);
            System.out.println("OSRS item id total amount : " + totalItemsOSRS);
        }


        int i = 2;
        for (int j = 0; j < totalItems; j++) {
            streamIndices[j] = i;
            i += stream.readUnsignedWord();
        }
        i = 2;
        for (int j = 0; j < totalItemsOSRS; j++) {
            streamIndicesOSRS[j] = i;
            i += streamOSRS1.readUnsignedWord();
        }

        cache = new ItemDef[CACHE_SIZE];
        cacheOSRS = new ItemDef[CACHE_SIZE];

        for (int k = 0; k < CACHE_SIZE; k++) {
            cache[k] = new ItemDef();
            cacheOSRS[k] = new ItemDef();
        }
        setSettings();
        //item dump definitions
        //itemDump();
    }

    public static ItemDef copyRotations(ItemDef itemDef, int id) {
        ItemDef itemDef2 = ItemDef.forID(id);
        itemDef.modelOffsetY = itemDef2.modelOffsetY;
        itemDef.modelOffsetX = itemDef2.modelOffsetX;
        itemDef.modelOffsetY = itemDef2.modelOffsetY;
        itemDef.modelOffset1 = itemDef2.modelOffset1;
        itemDef.modelZoom = itemDef2.modelZoom;
        itemDef.rotationX = itemDef2.rotationX;
        itemDef.rotationY = itemDef2.rotationY;
        itemDef.rotationZ = itemDef2.rotationZ;
        return itemDef;
    }

    public static String ucFirst(String str) {
        str = str.toLowerCase().replaceAll("_", " ");
        if (str.length() > 1) {
            str = str.substring(0, 1).toUpperCase() + str.substring(1);
        } else {
            return str.toUpperCase();
        }
        return str;
    }

    public static ItemDef forID(int i) {

        if (i >= OSRS_ITEMS_OFFSET) {
            i -= OSRS_ITEMS_OFFSET;
            for (int j = 0; j < CACHE_SIZE; j++) {
                if (cacheOSRS[j].id == i) {
                    return cacheOSRS[j];
                }
            }

            cacheIndexOSRS = (cacheIndexOSRS + 1) % CACHE_SIZE;

            ItemDef itemDef = cacheOSRS[cacheIndexOSRS];

            if (i >= streamIndicesOSRS.length) {
                itemDef.id = 1;
                itemDef.setDefaults();
                return itemDef;
            }
            streamOSRS.currentOffset = streamIndicesOSRS[i];
            itemDef.id = OSRS_ITEMS_OFFSET + i;

            itemDef.dataType = DataType.OLDSCHOOL;
            itemDef.setDefaults();
            itemDef.readValues(streamOSRS);


            int[] newModelColorOsrs; //This fixes OSRS re-colors

            newModelColorOsrs = itemDef.editedModelColor;

            itemDef.editedModelColor = itemDef.newModelColor;

            itemDef.newModelColor = newModelColorOsrs;


            if (itemDef.opcode140 != -1) {
                itemDef.method2789(forID(itemDef.opcode140), forID(itemDef.opcode139));
            }

            if (itemDef.opcode149 != -1) {
                itemDef.method2790(forID(itemDef.opcode149), forID(itemDef.opcode148));
            }

            if (itemDef.certTemplateID != -1) {
                itemDef.toNote();
            }

            if (itemDef.lentItemID != -1) {
                itemDef.toLend();
            }
            if (itemDef.name != null) {
                itemDef.description = "It's a " + itemDef.name + ".";
            }

            if (itemDef.editedModelColor != null) {
                for (int i2 = 0; i2 < itemDef.editedModelColor.length; i2++) {
                    if (itemDef.newModelColor[i2] == 0) {
                        itemDef.newModelColor[i2] = 1;
                    }
                }
            }

            //OSRS Items here!!! ID minus Offset
            switch (i) {

                case 1779:
                    itemDef.name = "Pet Flax";
                    itemDef.groundActions = new String[]{null, null, "Take", null, null};
                    itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                    break;


                case 12335:
                    itemDef.name = "Pet Hatius";
                    itemDef.groundActions = new String[]{null, null, "Take", null, null};
                    itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                    break;

                case 27641:
                    itemDef.setDefaults();
                    itemDef.name = "Saturated Heart";
                    itemDef.modelID = 46577;
                    itemDef.rotationX = 141;
                    itemDef.rotationY = 1690;
                    itemDef.modelZoom = 1068;
                    itemDef.modelOffset1 = 1;
                    itemDef.modelOffsetY = 0;
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[5];
                    itemDef.actions[0] = "Invigorate";
                    itemDef.actions[4] = "Drop";
                    break;

                case 13195:
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[5];
                    itemDef.actions[0] = "Activate";
                    itemDef.actions[4] = "Drop";
                    break;

                case 11941:
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[5];
                    itemDef.actions[0] = "Storage";
                    itemDef.actions[4] = "Destroy";
                    break;


                case 23971:
                case 23975:
                    itemDef.maleYOffset = -4;
                    itemDef.femaleYOffset = -4;
                    break;

                case 19496:
                    itemDef.actions = new String[]{null, null, "Cut", null, null, null};
                    break;
                case 20527:
                    itemDef.name = "Donator Token";
                    break;
                case 13392:
                    itemDef.name = "Slayer's Medallion";
                    itemDef.actions = new String[5];
                    itemDef.actions[1] = "Wear";
                    itemDef.actions[4] = "Drop";
                    break;
                case 21140:
                    itemDef.name = "Ring of Fortune";
                    itemDef.actions = new String[5];
                    itemDef.actions[1] = "Wear";
                    itemDef.actions[4] = "Drop";
                    break;
                case 21177:
                case 21183:
                    itemDef.actions = new String[5];
                    itemDef.actions[1] = "Wear";
                    itemDef.actions[4] = "Drop";
                    break;

                case 20368:
                case 20370:
                case 20372:
                case 20374:
                    itemDef.actions = new String[]{null, "Wield", "Dismantle", null, "Drop"};
                    break;
                case 7582:
                    itemDef.groundActions = new String[]{null, null, "Take", null, null};
                    itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                    break;

                /*case 20062:
                    itemDef.name = "Torture Ornament Kit";
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[5];
                    itemDef.actions[4] = "Drop";
                    break;

                case 20065:
                    itemDef.name = "Occult Ornament Kit";
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[5];
                    itemDef.actions[4] = "Drop";
                    break;

                case 20068:
                    itemDef.name = "Armadyl Godsword Ornament Kit";
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[5];
                    itemDef.actions[4] = "Drop";
                    break;

                case 20071:
                    itemDef.name = "Bandos Godsword Ornament Kit";
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[5];
                    itemDef.actions[4] = "Drop";
                    break;

                case 20074:
                    itemDef.name = "Saradomin Godsword Ornament Kit";
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[5];
                    itemDef.actions[4] = "Drop";
                    break;

                case 20077:
                    itemDef.name = "Zamorak Godsword Ornament Kit";
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[5];
                    itemDef.actions[4] = "Drop";
                    break;

                case 20143:
                    itemDef.name = "Dragon Defender Ornament Kit";
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[5];
                    itemDef.actions[4] = "Drop";
                    break;*/

                case 21275:
                    itemDef.name = "Dark Claw";
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[5];
                    itemDef.actions[4] = "Drop";
                    break;

                case 23077:
                    itemDef.name = "Alchemical Hydra Heads";
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[5];
                    itemDef.actions[4] = "Drop";
                    break;

                case 23348:
                    itemDef.name = "Tormented Ornament Kit";
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[5];
                    itemDef.actions[4] = "Drop";
                    break;

                case 22246:
                    itemDef.name = "Anguish Ornament Kit";
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[5];
                    itemDef.actions[4] = "Drop";
                    break;

                case 6955: //pink dye
                    itemDef.editedModelColor = new int[1];
                    itemDef.newModelColor = new int[1];
                    itemDef.editedModelColor[0] = 61;
                    itemDef.newModelColor[0] = 123770;
                    break;

                case 23804:
                    itemDef.name = "Crystal Dust";
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[5];
                    itemDef.actions[4] = "Drop";
                    break;

                case 23351:
                    itemDef.name = "Cape of Skulls";
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[5];
                    itemDef.actions[1] = "Wear";
                    itemDef.actions[4] = "Drop";
                    break;

                case 23673:
                    itemDef.name = "Crystal hatchet";
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[5];
                    itemDef.actions[1] = "Wear";
                    itemDef.actions[4] = "Drop";
                    break;

                case 23881:
                    itemDef.name = "Leather body (g)";
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[5];
                    itemDef.actions[1] = "Wear";
                    itemDef.actions[4] = "Drop";
                    break;

                case 23884:
                    itemDef.name = "Leather chaps (g)";
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[5];
                    itemDef.actions[1] = "Wear";
                    itemDef.actions[4] = "Drop";
                    break;

                case 13233:
                    itemDef.name = "Smouldering Stone";
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[5];
                    itemDef.actions[4] = "Drop";
                    break;

                case 19669:
                    itemDef.name = "Redwood Logs";
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[5];
                    itemDef.actions[4] = "Drop";
                    break;

                case 21284:
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[5];
                    itemDef.actions[1] = "Wear";
                    itemDef.actions[4] = "Drop";
                    break;

                case 12791:
                    itemDef.actions = new String[]{null, "Wield", "Check", "Unload", "Drop"};
                    itemDef.groundActions = new String[]{null, null, "Take", null, null};
                    break;

                case 21509:
                    itemDef.name = "Herbi";
                    itemDef.groundActions = new String[]{null, null, "Take", null, null};
                    itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                    break;

                case 23757:
                    itemDef.name = "Youngllef";
                    itemDef.groundActions = new String[]{null, null, "Take", null, null};
                    itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                    break;

                case 24491:
                    itemDef.name = "Little nightmare";
                    itemDef.groundActions = new String[]{null, null, "Take", null, null};
                    itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                    break;

                case 12646:
                    itemDef.name = "Baby mole";
                    itemDef.groundActions = new String[]{null, null, "Take", null, null};
                    itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                    break;

                case 19730:
                    itemDef.name = "Bloodhound";
                    itemDef.groundActions = new String[]{null, null, "Take", null, null};
                    itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                    break;

                case 13655:
                    itemDef.name = "Gnome Child Pet";
                    itemDef.groundActions = new String[]{null, null, "Take", null, null};
                    itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                    break;

                case 2959:
                    itemDef.name = "Pet Ghast";
                    itemDef.groundActions = new String[]{null, null, "Take", null, null};
                    itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                    break;

                case 22473:
                    itemDef.name = "Lil' Zik";
                    itemDef.groundActions = new String[]{null, null, "Take", null, null};
                    itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                    break;

                case 20659:
                    itemDef.name = "Giant squirrel";
                    itemDef.groundActions = new String[]{null, null, "Take", null, null};
                    itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                    break;

                case 20661:
                    itemDef.name = "Tangleroot";
                    itemDef.groundActions = new String[]{null, null, "Take", null, null};
                    itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                    break;

                case 20663:
                    itemDef.name = "Rocky";
                    itemDef.groundActions = new String[]{null, null, "Take", null, null};
                    itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                    break;

                case 20665:
                    itemDef.name = "Rift guardian";
                    itemDef.groundActions = new String[]{null, null, "Take", null, null};
                    itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                    break;

                case 20693:
                    itemDef.name = "Phoenix";
                    itemDef.groundActions = new String[]{null, null, "Take", null, null};
                    itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                    break;

                case 21187:
                    itemDef.name = "Rock golem";
                    itemDef.groundActions = new String[]{null, null, "Take", null, null};
                    itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                    break;

                case 13322:
                    itemDef.name = "Beaver";
                    itemDef.groundActions = new String[]{null, null, "Take", null, null};
                    itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                    break;

                case 11320:
                    itemDef.name = "Heron";
                    itemDef.groundActions = new String[]{null, null, "Take", null, null};
                    itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                    break;

                case 20851:
                    itemDef.name = "Olmlet";
                    itemDef.groundActions = new String[]{null, null, "Take", null, null};
                    itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                    break;

                case 78:
                    itemDef.name = "Crystal arrows";
                    break;

                case 21760:
                    itemDef.actions = new String[]{"Read", null, null, null, null};
                    break;

                case 25118: // 225118 in-game
                    itemDef.setDefaults();
                    itemDef.name = "Patriotic party hat";
                    itemDef.editedModelColor = new int[]{6067, 55217, 38835, 27571, 17331, 11187, 55196, 55186};
                    itemDef.newModelColor = new int[]{127, 127, 127, 947, 127, 43955, 127, 127};
                    itemDef.modelID = 16252;
                    itemDef.rotationX = 76;
                    itemDef.rotationY = 1852;
                    itemDef.modelZoom = 440;
                    itemDef.modelOffset1 = 1;
                    itemDef.modelOffsetY = 1;
                    itemDef.femaleEquip1 = 16246;
                    itemDef.maleEquip1 = 16246;
                    //itemDef.maleXOffset = -7;
                    itemDef.maleYOffset = -6;
                    itemDef.femaleYOffset = -6;
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[5];
                    itemDef.actions[1] = "Wear";
                    itemDef.actions[4] = "Drop";
                    break;


                case 20526: // 225118 in-game
                    itemDef.name = "ToB Raid Key";
                    break;

                case 2399:
                    itemDef.name = "CoX Raid Key";
                    break;

                case 11863:
                    itemDef.name = "Rainbow party hat";
                    itemDef.maleYOffset = -6;
                    itemDef.femaleYOffset = -6;
                    break;

                case 24373:
                    itemDef.setDefaults();
                    itemDef.name = "Pet Frog";
                    itemDef.modelID = 6947;
                    itemDef.groundActions = new String[]{null, null, "Take", null, null};
                    itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                    break;

                case 21992:
                    itemDef.name = "Pet Vorki";
                    itemDef.groundActions = new String[]{null, null, "Take", null, null};
                    itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                    break;

                case 11942:
                    itemDef.name = "GWD Raid Key";
                    break;

                case 25316:
                    itemDef.name = "Festive Decorations";
                    break;

                case 25119: // 225119 in-game
                    itemDef.setDefaults();
                    itemDef.name = "H'ween party hat (2021)";
                    itemDef.editedModelColor = new int[]{6067, 55217, 38835, 27571, 17331, 11187, 947, 43955, 55196, 55186};
                    itemDef.newModelColor = new int[]{461770, 461770, 461770, 0, 461770, 0, 0, 0, 461770, 461770};
                    itemDef.modelID = 16252;
                    itemDef.rotationX = 76;
                    itemDef.rotationY = 1852;
                    itemDef.modelZoom = 440;
                    itemDef.modelOffset1 = 1;
                    itemDef.modelOffsetY = 1;
                    itemDef.femaleEquip1 = 16246;
                    itemDef.maleEquip1 = 16246;
                    //itemDef.maleXOffset = -7;
                    itemDef.maleYOffset = -6;
                    itemDef.femaleYOffset = -6;
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[5];
                    itemDef.actions[1] = "Wear";
                    itemDef.actions[4] = "Drop";
                    break;

                case 25120: // 225120 in-game
                    itemDef.setDefaults();
                    itemDef.name = "Reverse H'ween party hat (2023)";
                    itemDef.editedModelColor = new int[]{6067, 55217, 38835, 27571, 17331, 11187, 947, 43955, 55196, 55186};
                    itemDef.newModelColor = new int[]{0, 0, 0, 461770, 0, 461770, 461770, 461770, 0, 0};
                    itemDef.modelID = 16252;
                    itemDef.rotationX = 76;
                    itemDef.rotationY = 1852;
                    itemDef.modelZoom = 440;
                    itemDef.modelOffset1 = 1;
                    itemDef.modelOffsetY = 1;
                    itemDef.femaleEquip1 = 16246;
                    itemDef.maleEquip1 = 16246;
                    //itemDef.maleXOffset = -7;
                    itemDef.maleYOffset = -6;
                    itemDef.femaleYOffset = -6;
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[5];
                    itemDef.actions[1] = "Wear";
                    itemDef.actions[4] = "Drop";
                    break;

                case 25121: // 225121 in-game
                    itemDef.setDefaults();
                    itemDef.name = "H'ween santa hat (2023)";
                    itemDef.editedModelColor = new int[]{10351, 933, -24665};
                    itemDef.newModelColor = new int[]{0, 461770, 0};
                    itemDef.modelID = 2537;
                    itemDef.rotationX = 72;
                    itemDef.rotationY = 136;
                    itemDef.modelZoom = 540;
                    itemDef.modelOffset1 = -3;
                    itemDef.modelOffsetY = -3;
                    itemDef.femaleEquip1 = 189;
                    itemDef.maleEquip1 = 189;
                    //itemDef.maleXOffset = -7;
                    itemDef.maleYOffset = -8;
                    itemDef.femaleYOffset = -8;
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[5];
                    itemDef.actions[1] = "Wear";
                    itemDef.actions[4] = "Drop";
                    break;

                case 25122: // 225122 in-game
                    itemDef.setDefaults();
                    itemDef.name = "Reverse H'ween santa hat (2023)";
                    itemDef.editedModelColor = new int[]{10351, 933, -24665};
                    itemDef.newModelColor = new int[]{461770, 0, 461770,};
                    itemDef.modelID = 2537;
                    itemDef.rotationX = 72;
                    itemDef.rotationY = 136;
                    itemDef.modelZoom = 540;
                    itemDef.modelOffset1 = -3;
                    itemDef.modelOffsetY = -3;
                    itemDef.femaleEquip1 = 189;
                    itemDef.maleEquip1 = 189;
                    //itemDef.maleXOffset = -7;
                    itemDef.maleYOffset = -8;
                    itemDef.femaleYOffset = -8;
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[5];
                    itemDef.actions[1] = "Wear";
                    itemDef.actions[4] = "Drop";
                    break;

                case 22009:
                    itemDef.name = "SHR Raid Key 1";
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.editedModelColor = new int[]{7120, -32700, 960, 22464, -21568};
                    itemDef.newModelColor = new int[]{8128, 8, 8128, 8128, 8128};
                    itemDef.modelID = 4132;
                    itemDef.modelZoom = 1050;
                    itemDef.rotationX = 420;
                    itemDef.rotationY = 2040;
                    itemDef.rotationZ = 0;
                    itemDef.modelOffsetX = 0;
                    itemDef.modelOffsetY = 0;
                    break;

                case 22010:
                    itemDef.name = "SHR Raid Key 2";
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.editedModelColor = new int[]{7120, -32700, 960, 22464, -21568};
                    itemDef.newModelColor = new int[]{8128, -13418, 8128, 8128, 8128};
                    itemDef.modelID = 4132;
                    itemDef.modelZoom = 1050;
                    itemDef.rotationX = 420;
                    itemDef.rotationY = 2040;
                    itemDef.rotationZ = 0;
                    itemDef.modelOffsetX = 0;
                    itemDef.modelOffsetY = 0;
                    break;

                case 22011:
                    itemDef.name = "SHR Raid Key 3";
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.editedModelColor = new int[]{7120, -32700, 960, 22464, -21568};
                    itemDef.newModelColor = new int[]{8128, 7070, 8128, 8128, 8128};
                    itemDef.modelID = 4132;
                    itemDef.modelZoom = 1050;
                    itemDef.rotationX = 420;
                    itemDef.rotationY = 2040;
                    itemDef.rotationZ = 0;
                    itemDef.modelOffsetX = 0;
                    itemDef.modelOffsetY = 0;
                    break;

                case 22012:
                    itemDef.name = "SHR Raid Key 4";
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.editedModelColor = new int[]{7120, -32700, 960, 22464, -21568};
                    itemDef.newModelColor = new int[]{8128, 957, 8128, 8128, 8128};
                    itemDef.modelID = 4132;
                    itemDef.modelZoom = 1050;
                    itemDef.rotationX = 420;
                    itemDef.rotationY = 2040;
                    itemDef.rotationZ = 0;
                    itemDef.modelOffsetX = 0;
                    itemDef.modelOffsetY = 0;
                    break;

                case 23502:
                    itemDef.name = "Stronghold Raid Key";
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    break;

                case 12399:
                    itemDef.name = "Wise Old Man";
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                    break;

                case 1543:
                    itemDef.name = "Chaos Raid Key 5";
                    break;

                case 1544:
                    itemDef.name = "Chaos Raid Key 6";
                    break;

                case 1545:
                    itemDef.name = "Chaos Raid Key 7";
                    break;

                case 1546:
                    itemDef.name = "Chaos Raid Key 8";
                    break;

                case 1547:
                    itemDef.name = "Chaos Raid Key 9";
                    break;

                case 6754:
                    itemDef.name = "Chaos Raid Key 10";
                    break;

                case 24000:
                    itemDef.setDefaults();
                    itemDef.name = "Crystal claws";
                    itemDef.editedModelColor = new int[]{914, 918, 922, 929};
                    itemDef.newModelColor = new int[]{296760, 296762, 296765, 296770};
                    itemDef.modelID = 32784;
                    itemDef.rotationX = 15;
                    itemDef.rotationY = 349;
                    itemDef.modelZoom = 886;
                    itemDef.modelOffset1 = -1;
                    itemDef.modelOffsetY = 8;
                    itemDef.femaleEquip1 = 29191;
                    itemDef.femaleEquip2 = 0;
                    itemDef.maleEquip1 = 29191;
                    itemDef.maleEquip2 = 0;
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[5];
                    itemDef.actions[1] = "Wear";
                    itemDef.actions[4] = "Drop";
                    break;

                case 24002:
                    itemDef.setDefaults();
                    itemDef.name = "Crystal spike";
                    itemDef.editedModelColor = new int[]{94, 111, 127, 7277};
                    itemDef.newModelColor = new int[]{296760, 296762, 296765, 296770};
                    itemDef.modelID = 19401;
                    itemDef.rotationX = 1528;
                    itemDef.rotationY = 624;
                    itemDef.modelZoom = 600;
                    itemDef.modelOffset1 = -4;
                    itemDef.modelOffsetY = 1;
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[5];
                    itemDef.actions[4] = "Drop";
                    break;

                case 24003:
                    itemDef.name = "Zaros hilt";
                    itemDef.editedModelColor = new int[]{5596};
                    itemDef.newModelColor = new int[]{51136};
                    itemDef.modelID = 2449;
                    itemDef.rotationY = 324;
                    itemDef.rotationX = 1980;
                    itemDef.modelZoom = 1150;
                    itemDef.modelOffset1 = -1;
                    itemDef.modelOffsetY = 9;
                    itemDef.groundActions = new String[5];
                    itemDef.groundActions[2] = "Take";
                    itemDef.actions = new String[5];
                    itemDef.actions[4] = "Drop";
                    break;

                case 3230:
                case 3231:
                case 3232:
                case 3233:
                case 3234:
                case 3235:
                case 3236:
                case 3237:
                case 3238:
                case 3239:
                case 3240:
                case 3241:
                case 3242:
                case 3243:
                case 3244:
                case 3245:
                case 3246:
                case 3247:
                case 3248:
                    slayerInstanceKeys(i, itemDef);
                    break;


            }


            return itemDef;
        }
        for (int j = 0; j < 10; j++) {
            if (cache[j].id == i) {
                return cache[j];
            }
        }
        cacheIndex = (cacheIndex + 1) % 10;
        ItemDef itemDef = cache[cacheIndex];
        if (i >= streamIndices.length) {
            itemDef.id = 1;
            itemDef.setDefaults();
            return itemDef;
        }
        stream.currentOffset = streamIndices[i];
        itemDef.id = i;
        itemDef.setDefaults();
        itemDef.readValues(stream);
        if (itemDef.certTemplateID != -1) {
            itemDef.toNote();
        }
        if (itemDef.lentItemID != -1) {
            itemDef.toLend();
        }
        if (itemDef.id == i && itemDef.editedModelColor == null) {
            itemDef.editedModelColor = new int[1];
            itemDef.newModelColor = new int[1];
            itemDef.editedModelColor[0] = 0;
            itemDef.newModelColor[0] = 1;
        }
        if (untradeableItems.contains(itemDef.id)) {
            itemDef.untradeable = true;
        }
        itemDef.value = prices[itemDef.id];
        int custom_start = 18888;
        //System.out.println("Custom items: "+CustomItems.values().length);


        for (CustomItems custom : CustomItems.values()) {
            if (i == custom_start + custom.ordinal()) {
                itemDef = copyRotations(itemDef, custom.getCopy());
                itemDef.name = ucFirst(custom.name());
                if (custom.isCopyDef()) {
                    ItemDef def2 = ItemDef.forID(custom.getCopy());
                    itemDef.modelID = def2.modelID;
                    itemDef.maleEquip1 = def2.maleEquip1;
                    itemDef.femaleEquip1 = def2.femaleEquip1;
                    itemDef.editedModelColor = custom.editedModelColor;
                    itemDef.newModelColor = custom.originalModelColor;
                } else {
                    itemDef.modelID = custom.getInventory();
                    itemDef.maleEquip1 = custom.getMale();
                    itemDef.femaleEquip1 = custom.getFemale();
                }
                itemDef.actions = new String[5];
                itemDef.actions[1] = custom.isWeapon() ? "Wield" : "Wear";
            }
        }
        switch (i) {
            case 2795:
                itemDef.name = "Bingo card";
                itemDef.description = "B I N G O!";
                itemDef.actions = new String[5];
                itemDef.actions[0] = "Open";
                itemDef.actions[3] = "Re-Roll";
                itemDef.actions[4] = "Drop";
                break;
            case 6570:
                itemDef.animateInventory = true;
                break;

            case 2438:
            case 2439:
                itemDef.name = "Overload+ (4)";
                break;
            case 151:
            case 152:
                itemDef.name = "Overload+ (3)";
                break;
            case 153:
            case 154:
                itemDef.name = "Overload+ (2)";
                break;
            case 155:
            case 156:
                itemDef.name = "Overload+ (1)";
                break;
            case 14846:
            case 14845:
                itemDef.name = "Saradomin Brew+ (4)";
                break;
            case 14848:
            case 14849:
                itemDef.name = "Saradomin Brew+ (3)";
                break;
            case 14850:
            case 14851:
                itemDef.name = "Saradomin Brew+ (2)";
                break;
            case 14852:
            case 14853:
                itemDef.name = "Saradomin Brew+ (1)";
                break;

            case 707:
                itemDef.name = "Vial of Dreams";
                break;
            case 4604:
                itemDef.name = "Vial of Blood";
                break;
            case 4560:
                itemDef.name = "Skeleton Candy";
                itemDef.actions = new String[]{"Eat", null, "Eat-all", null, "Drop"};
                break;
            case 4562:
                itemDef.name = "Rare Candy";
                itemDef.actions = new String[]{"Eat", null, "Eat-all", null, "Drop"};
                break;
            case 2957:
                itemDef.name = "Candy Pouch";
                itemDef.actions = new String[]{"Open", null, null, null, "Drop"};
                break;

            case 11157:
                itemDef.name = "Genie lamp";
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                break;

            case 76:
                itemDef.name = "Gift";
                itemDef.modelID = 2426;
                itemDef.modelZoom = 1180;
                itemDef.rotationX = 160;
                itemDef.rotationY = 172;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -14;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[0] = "Open";
                itemDef.actions[4] = "Drop";
                break;

            case 83:
                itemDef.name = "Birthday Cake";
                itemDef.description = "Birthday Cake";
                itemDef.modelID = 91221;
                itemDef.modelZoom = 2150;
                itemDef.rotationX = 72;
                itemDef.rotationY = 2000;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 16;
                itemDef.maleEquip1 = 91221;
                itemDef.femaleEquip1 = 91221;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 1811:
                itemDef.name = "Magic Paper";
                break;

            case 19791:
                itemDef.name = "Ancient d'hide boots";
                itemDef.description = "Boots blessed by Zaros.";
                itemDef.modelID = 91230;
                itemDef.modelZoom = 700;
                itemDef.rotationX = 131;
                itemDef.rotationY = 69;
                itemDef.modelOffset1 = 1;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = -7;
                itemDef.maleEquip1 = 91231;
                itemDef.femaleEquip1 = 91231;
                itemDef.editedModelColor = new int[6];
                itemDef.newModelColor = new int[6];
                itemDef.editedModelColor[0] = 22168;
                itemDef.newModelColor[0] = -12900;
                itemDef.editedModelColor[1] = 8070;
                itemDef.newModelColor[1] = -10329;
                itemDef.editedModelColor[2] = 24082;
                itemDef.newModelColor[2] = -11101;
                itemDef.editedModelColor[3] = 22156;
                itemDef.newModelColor[3] = -10854;
                itemDef.editedModelColor[4] = 920;
                itemDef.newModelColor[4] = -10329;
                itemDef.editedModelColor[5] = 912;
                itemDef.newModelColor[5] = -10329;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 19792:
                itemDef.name = "Armadyl d'hide boots";
                itemDef.description = "Boots blessed by Armadyl.";
                itemDef.modelID = 91230;
                itemDef.modelZoom = 700;
                itemDef.rotationX = 131;
                itemDef.rotationY = 69;
                itemDef.modelOffset1 = 3;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = -7;
                itemDef.maleEquip1 = 91231;
                itemDef.femaleEquip1 = 91231;
                itemDef.editedModelColor = new int[5];
                itemDef.newModelColor = new int[5];
                itemDef.editedModelColor[0] = 22168;
                itemDef.newModelColor[0] = -12900;
                itemDef.editedModelColor[1] = 24082;
                itemDef.newModelColor[1] = 94;
                itemDef.editedModelColor[2] = 22156;
                itemDef.newModelColor[2] = 107;
                itemDef.editedModelColor[3] = 920;
                itemDef.newModelColor[3] = 119;
                itemDef.editedModelColor[4] = 912;
                itemDef.newModelColor[4] = 119;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 19793:
                itemDef.name = "Bandos d'hide boots";
                itemDef.description = "Boots blessed by Bandos.";
                itemDef.modelID = 91230;
                itemDef.modelZoom = 700;
                itemDef.rotationX = 131;
                itemDef.rotationY = 69;
                itemDef.modelOffset1 = 3;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = -7;
                itemDef.maleEquip1 = 91231;
                itemDef.femaleEquip1 = 91231;
                itemDef.editedModelColor = new int[6];
                itemDef.newModelColor = new int[6];
                itemDef.editedModelColor[0] = 27417;
                itemDef.newModelColor[0] = 8377;
                itemDef.editedModelColor[1] = 24082;
                itemDef.newModelColor[1] = 3127;
                itemDef.editedModelColor[2] = 22168;
                itemDef.newModelColor[2] = 8377;
                itemDef.editedModelColor[3] = 22156;
                itemDef.newModelColor[3] = 6323;
                itemDef.editedModelColor[4] = 920;
                itemDef.newModelColor[4] = 6352;
                itemDef.editedModelColor[5] = 912;
                itemDef.newModelColor[5] = 6352;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 19794:
                itemDef.name = "Guthix d'hide boots";
                itemDef.description = "Boots blessed by Guthix.";
                itemDef.modelID = 91230;
                itemDef.modelZoom = 700;
                itemDef.rotationX = 131;
                itemDef.rotationY = 69;
                itemDef.modelOffset1 = 3;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = -7;
                itemDef.maleEquip1 = 91231;
                itemDef.femaleEquip1 = 91231;
                itemDef.editedModelColor = new int[3];
                itemDef.newModelColor = new int[3];
                itemDef.editedModelColor[0] = 920;
                itemDef.newModelColor[0] = 119;
                itemDef.editedModelColor[1] = 912;
                itemDef.newModelColor[1] = 119;
                itemDef.editedModelColor[2] = 8070;
                itemDef.newModelColor[2] = 19788;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 19795:
                itemDef.name = "Saradomin d'hide boots";
                itemDef.description = "Boots blessed by Saradomin.";
                itemDef.modelID = 91230;
                itemDef.modelZoom = 700;
                itemDef.rotationX = 131;
                itemDef.rotationY = 69;
                itemDef.modelOffset1 = 3;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = -7;
                itemDef.maleEquip1 = 91231;
                itemDef.femaleEquip1 = 91231;
                itemDef.editedModelColor = new int[6];
                itemDef.newModelColor = new int[6];
                itemDef.editedModelColor[0] = 22168;
                itemDef.newModelColor[0] = -21864;
                itemDef.editedModelColor[1] = 8070;
                itemDef.newModelColor[1] = -24771;
                itemDef.editedModelColor[2] = 24082;
                itemDef.newModelColor[2] = -24046;
                itemDef.editedModelColor[3] = 22156;
                itemDef.newModelColor[3] = -21876;
                itemDef.editedModelColor[4] = 920;
                itemDef.newModelColor[4] = -25788;
                itemDef.editedModelColor[5] = 912;
                itemDef.newModelColor[5] = -27983;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 19796:
                itemDef.name = "Zamorak d'hide boots";
                itemDef.description = "Boots blessed by Zamorak.";
                itemDef.modelID = 91230;
                itemDef.modelZoom = 700;
                itemDef.rotationX = 131;
                itemDef.rotationY = 69;
                itemDef.modelOffset1 = 3;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = -7;
                itemDef.maleEquip1 = 91231;
                itemDef.femaleEquip1 = 91231;
                itemDef.editedModelColor = new int[6];
                itemDef.newModelColor = new int[6];
                itemDef.editedModelColor[0] = 22168;
                itemDef.newModelColor[0] = 664;
                itemDef.editedModelColor[1] = 8070;
                itemDef.newModelColor[1] = 962;
                itemDef.editedModelColor[2] = 24082;
                itemDef.newModelColor[2] = 910;
                itemDef.editedModelColor[3] = 22156;
                itemDef.newModelColor[3] = 652;
                itemDef.editedModelColor[4] = 920;
                itemDef.newModelColor[4] = 962;
                itemDef.editedModelColor[5] = 912;
                itemDef.newModelColor[5] = 1967;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 18841:
                itemDef.name = "Fremennik Kilt";
                itemDef.modelID = 91234;
                itemDef.modelZoom = 1500;
                itemDef.rotationX = 0;
                itemDef.rotationY = 600;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                itemDef.maleEquip1 = 91235;
                itemDef.femaleEquip1 = 91235;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 18842:
                itemDef.name = "Spiked manacles";
                itemDef.modelID = 91232;
                itemDef.modelZoom = 716;
                itemDef.rotationX = 134;
                itemDef.rotationY = 112;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                itemDef.maleEquip1 = 91233;
                itemDef.femaleEquip1 = 91233;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;


            case 17923:
                itemDef.name = "Demonhunter staff";
                break;

            case 18843:
                itemDef.name = "Dragon hunter lance";
                itemDef.modelID = 91236;
                itemDef.modelZoom = 1500;
                itemDef.rotationX = 0;
                itemDef.rotationY = 1500;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                itemDef.maleEquip1 = 91237;
                itemDef.femaleEquip1 = 91237;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 18844:
                itemDef.name = "Dragon hunter crossbow";
                itemDef.modelID = 91238;
                itemDef.modelZoom = 926;
                itemDef.rotationX = 432;
                itemDef.rotationY = 258;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 9;
                itemDef.maleEquip1 = 91239;
                itemDef.femaleEquip1 = 91239;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 18845:
                itemDef.name = "Amulet of the Damned";
                itemDef.modelID = 91242;
                itemDef.modelZoom = 500;
                itemDef.rotationX = 0;
                itemDef.rotationY = 600;
                itemDef.modelOffsetX = 1;
                itemDef.modelOffsetY = 2;
                itemDef.maleEquip1 = 91243;
                itemDef.femaleEquip1 = 91243;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 18846:
                itemDef.name = "Light Ballista";
                itemDef.modelID = 91240;
                itemDef.modelZoom = 1250;
                itemDef.rotationX = 189;
                itemDef.rotationY = 148;
                itemDef.modelOffsetX = 8;
                itemDef.modelOffsetY = -18;
                itemDef.maleEquip1 = 91241;
                itemDef.femaleEquip1 = 91241;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 18847:
                itemDef.name = "Tormented bracelet";
                itemDef.modelID = 91244;
                itemDef.modelZoom = 659;
                itemDef.rotationX = 184;
                itemDef.rotationY = 200;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -7;
                itemDef.maleEquip1 = 91245;
                itemDef.femaleEquip1 = 91245;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 3702:
                itemDef.name = "Golden Bowstring";
                itemDef.editedModelColor = new int[2];
                itemDef.newModelColor = new int[2];
                itemDef.editedModelColor[0] = 7467;
                itemDef.newModelColor[0] = 7114;
                itemDef.editedModelColor[1] = 7479;
                itemDef.newModelColor[1] = 7120;
                break;

            case 5376:
                itemDef.name = "Easter Basket";
                break;
            case 2714:
                itemDef.name = "Basic Clue Casket";
                break;
            case 2715:
                itemDef.name = "3rd Age Casket";
                break;
            case 2717:
                itemDef.name = "Holiday Item Casket";
                break;
            case 2718:
                itemDef.name = "Dragon Casket";
                break;
            case 2720:
                itemDef.name = "Rune Armor Casket";
                break;
            case 7956:
                itemDef.name = "Supply Chest";
                break;
            case 405:
                itemDef.name = "Slayer Loot Crate";
                break;


            case 6640:
                itemDef.name = "Primordial Crystal";
                itemDef.modelID = 91286;
                itemDef.rotationX = 429;
                itemDef.rotationY = 500;
                itemDef.modelZoom = 740;
                itemDef.modelOffset1 = 5;
                itemDef.modelOffsetY = 5;
                break;
            case 6642:
                itemDef.name = "Pegasian Crystal";
                itemDef.modelID = 91285;
                itemDef.rotationX = 429;
                itemDef.rotationY = 500;
                itemDef.modelZoom = 740;
                itemDef.modelOffset1 = 5;
                itemDef.modelOffsetY = 5;
                break;
            case 6645:
                itemDef.name = "Eternal Crystal";
                itemDef.modelID = 91284;
                itemDef.rotationX = 429;
                itemDef.rotationY = 500;
                itemDef.modelZoom = 740;
                itemDef.modelOffset1 = 5;
                itemDef.modelOffsetY = 5;
                break;


            case 600:
                itemDef.name = "Book of Faith";
                itemDef.actions = new String[]{"Prayers", null, null, "Curses", null, null};
                break;
            case 730:
                itemDef.name = "Book of Knowledge";
                itemDef.actions = new String[]{"Normal", null, "Lunar", "Ancient", null, null};
                break;


            //Raids
            case 1545:
                itemDef.name = "Chaos Raid Key 1";
                break;
            case 1546:
                itemDef.name = "Chaos Raid Key 2";
                break;
            case 1547:
                itemDef.name = "Chaos Raid Key 3";
                break;
            case 1548:
                itemDef.name = "Chaos Raid Key 4";
                break;
            case 1542:
                itemDef.name = "Chaos Raid Key 5";
                break;
            case 4656:
                itemDef.name = "Chaos Raid Key 6";
                break;
            case 5585:
                itemDef.name = "Chaos Raid Key 7";
                break;
            case 6083:
                itemDef.name = "Chaos Raid Key 8";
                break;
            case 6084:
                itemDef.name = "Chaos Raid Key 9";
                break;
            case 6754:
                itemDef.name = "Chaos Raid Key 10";
                break;
            case 2721:
                itemDef.name = "Medium Raid Casket";
                break;
            case 2724:
                itemDef.name = "Hard Raid Casket";
                break;
            case 2726:
                itemDef.name = "Raid Supplies";
                break;
            case 7630:
                itemDef.name = "Raid Armor Crate";
                break;

            case 15356:
                itemDef.name = "$5 Donation Bond";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;
            case 15355:
                itemDef.name = "$10 Donation Bond";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 1;
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;
            case 15359:
                itemDef.name = "$20 Donation Bond";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 1;
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;
            case 15358:
                itemDef.name = "$50 Donation Bond";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 1;
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 2677:
                itemDef.name = "500 Paepoints";
                itemDef.actions = new String[]{"Redeem", null, null, null, null, null};
                break;
            case 2678:
                itemDef.name = "1500 Paepoints";
                itemDef.actions = new String[]{"Redeem", null, null, null, null, null};
                break;
            case 2679:
                itemDef.name = "1000 Paepoints";
                itemDef.actions = new String[]{"Redeem", null, null, null, null, null};
                break;

            case 2680:
                itemDef.name = "2500 Paepoints";
                itemDef.actions = new String[]{"Redeem", null, null, null, null, null};
                break;
            case 2681:
                itemDef.name = "5000 Paepoints";
                itemDef.actions = new String[]{"Redeem", null, null, null, null, null};
                break;
            case 2682:
                itemDef.name = "10000 Paepoints";
                itemDef.actions = new String[]{"Redeem", null, null, null, null, null};
                break;

            //Custom Boxes

            case 603:
                itemDef.name = "Supreme mystery box";
                itemDef.actions = new String[]{"Open", null, null, null, null, null};
                itemDef.modelID = 2426;
                itemDef.modelZoom = 1180;
                itemDef.rotationY = 172;
                itemDef.rotationX = 160;
                itemDef.modelOffsetY = -14;
                itemDef.newModelColor = new int[]{7114, 0}; // gold and black
                itemDef.editedModelColor = new int[]{2999, 22410}; // 2999 = ribbon, 224210 = box
                break;

            case 604:
                itemDef.name = "Custom mystery box";
                itemDef.actions = new String[]{"Open", null, null, null, null, null};
                itemDef.modelID = 2426;
                itemDef.modelZoom = 1180;
                itemDef.rotationY = 172;
                itemDef.rotationX = 160;
                itemDef.modelOffsetY = -14;
                itemDef.newModelColor = new int[]{123770, 147}; // pink and dark grey
                itemDef.editedModelColor = new int[]{2999, 22410}; // 2999 = ribbon, 224210 = box
                break;

            case 2683:
                itemDef.name = "Starter pack";
                itemDef.actions = new String[]{"Open", null, null, null, null, null};
                itemDef.modelID = 2426;
                itemDef.modelZoom = 1180;
                itemDef.rotationY = 172;
                itemDef.rotationX = 160;
                itemDef.modelOffsetY = -14;
                itemDef.newModelColor = new int[]{926, 127}; // red and grey
                itemDef.editedModelColor = new int[]{2999, 22410}; // 2999 = ribbon, 224210 = box
                break;

            case 2684:
                itemDef.name = "Elite pack";
                itemDef.actions = new String[]{"Open", null, null, null, null, null};
                itemDef.modelID = 2426;
                itemDef.modelZoom = 1180;
                itemDef.rotationY = 172;
                itemDef.rotationX = 160;
                itemDef.modelOffsetY = -14;
                itemDef.newModelColor = new int[]{127, 926}; // red and grey
                itemDef.editedModelColor = new int[]{2999, 22410}; // 2999 = ribbon, 224210 = box
                break;

            case 2685:
                itemDef.name = "Event Box";
                itemDef.actions = new String[]{"Open", null, null, null, null, null};
                itemDef.modelID = 2426;
                itemDef.modelZoom = 1180;
                itemDef.rotationY = 172;
                itemDef.rotationX = 160;
                itemDef.modelOffsetY = -14;
                itemDef.newModelColor = new int[]{123770, 374770}; // pink and purple
                itemDef.editedModelColor = new int[]{2999, 22410}; // 2999 = ribbon, 224210 = box
                break;


            //custom skilling
            case 901:
                itemDef.name = "Zenyte";
                itemDef.actions = new String[]{null, null, null, null, null, null};
                itemDef.modelID = 2586;
                itemDef.modelZoom = 780;
                itemDef.rotationY = 688;
                itemDef.rotationX = 340;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 1;
                itemDef.newModelColor = new int[]{5056, 3008, 5056};
                itemDef.editedModelColor = new int[]{127, 931, 478};
                break;
            case 902:  //Needs correct model ID and re-color
                itemDef.name = "Uncut Zenyte";
                itemDef.actions = new String[]{null, null, null, null, null, null};
                itemDef.modelID = 2528;
                itemDef.modelZoom = 700;
                itemDef.rotationY = 400;
                itemDef.rotationX = 300;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 1;
                itemDef.newModelColor = new int[]{5056};
                itemDef.editedModelColor = new int[]{61};
                break;
            //custom skilling
            case 17415:
                itemDef.name = "Iron block";
                itemDef.actions = new String[]{"Carve", null, "Carve-all", null, null, null};
                itemDef.modelID = 53902;
                itemDef.modelZoom = 1124;
                itemDef.rotationY = 183;
                itemDef.rotationX = 311;
                itemDef.modelOffset1 = 1;
                itemDef.modelOffsetY = -16;
                itemDef.newModelColor = new int[]{2471, 2471, 2472};
                itemDef.editedModelColor = new int[]{4158, 4159, 4161};
                break;
            case 17416:
                itemDef.name = "Coal block";
                itemDef.actions = new String[]{"Carve", null, "Carve-all", null, null, null};
                itemDef.modelID = 53902;
                itemDef.modelZoom = 1124;
                itemDef.rotationY = 183;
                itemDef.rotationX = 311;
                itemDef.modelOffset1 = 1;
                itemDef.modelOffsetY = -16;
                itemDef.newModelColor = new int[]{461770, 461770, 461770};
                itemDef.editedModelColor = new int[]{918, 931, 478};
                break;
            case 17422:
                itemDef.name = "Gold block";
                itemDef.actions = new String[]{"Carve", null, "Carve-all", null, null, null};
                itemDef.modelID = 53902;
                itemDef.modelZoom = 1124;
                itemDef.rotationY = 183;
                itemDef.rotationX = 311;
                itemDef.modelOffset1 = 1;
                itemDef.modelOffsetY = -16;
                itemDef.newModelColor = new int[]{461770, 461770, 461770};
                itemDef.editedModelColor = new int[]{918, 931, 478};
                break;
            case 17418:
                itemDef.name = "Mithril block";
                itemDef.actions = new String[]{"Carve", null, "Carve-all", null, null, null};
                itemDef.modelID = 53902;
                itemDef.modelZoom = 1124;
                itemDef.rotationY = 183;
                itemDef.rotationX = 311;
                itemDef.modelOffset1 = 1;
                itemDef.modelOffsetY = -16;
                itemDef.newModelColor = new int[]{461770, 461770, 461770};
                itemDef.editedModelColor = new int[]{918, 931, 478};
                break;
            case 17424:
                itemDef.name = "Adamantite block";
                itemDef.actions = new String[]{"Carve", null, "Carve-all", null, null, null};
                itemDef.modelID = 53902;
                itemDef.modelZoom = 1124;
                itemDef.rotationY = 183;
                itemDef.rotationX = 311;
                itemDef.modelOffset1 = 1;
                itemDef.modelOffsetY = -16;
                itemDef.newModelColor = new int[]{461770, 461770, 461770};
                itemDef.editedModelColor = new int[]{918, 931, 478};
                break;
            case 17420:
                itemDef.name = "Runite block";
                itemDef.actions = new String[]{"Carve", null, "Carve-all", null, null, null};
                itemDef.modelID = 53902;
                itemDef.modelZoom = 1124;
                itemDef.rotationY = 183;
                itemDef.rotationX = 311;
                itemDef.modelOffset1 = 1;
                itemDef.modelOffsetY = -16;
                itemDef.newModelColor = new int[]{461770, 461770, 461770};
                itemDef.editedModelColor = new int[]{918, 931, 478};
                break;
            case 17426:
                itemDef.name = "Amethyst block";
                itemDef.actions = new String[]{"Carve", null, "Carve-all", null, null, null};
                itemDef.modelID = 53902;
                itemDef.modelZoom = 1124;
                itemDef.rotationY = 183;
                itemDef.rotationX = 311;
                itemDef.modelOffset1 = 1;
                itemDef.modelOffsetY = -16;
                itemDef.newModelColor = new int[]{461770, 461770, 461770};
                itemDef.editedModelColor = new int[]{918, 931, 478};
                break;

            case 18884:
                itemDef.name = "Dragon Ore";
                itemDef.actions = new String[]{null, null, null, null, null, null};
                itemDef.modelID = 2748;
                itemDef.modelZoom = 1400;
                itemDef.rotationY = 368;
                itemDef.rotationX = 1576;
                itemDef.modelOffset1 = 1;
                itemDef.modelOffsetY = 15;
                itemDef.newModelColor = new int[]{926};
                itemDef.editedModelColor = new int[]{7062};
                break;
            case 18885:
                itemDef.name = "Dragon bar";
                itemDef.actions = new String[]{null, null, null, null, null, null};
                itemDef.modelID = 2408;
                itemDef.modelZoom = 820;
                itemDef.rotationY = 196;
                itemDef.rotationX = 1180;
                itemDef.modelOffset1 = 1;
                itemDef.modelOffsetY = -8;
                itemDef.newModelColor = new int[]{926};
                itemDef.editedModelColor = new int[]{7062};
                break;
            case 2748:
                itemDef.name = "Pile of Shrimp";
                itemDef.description = "A pile of raw seafood.";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Separate";
                itemDef.modelID = 26673;
                itemDef.rotationX = 290;
                itemDef.rotationY = 282;
                itemDef.modelZoom = 984;
                itemDef.modelOffset1 = -3;
                break;

            case 2749:
                itemDef.name = "Pile of Anchovies";
                itemDef.description = "A pile of raw seafood.";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Separate";
                itemDef.modelID = 26673;
                itemDef.rotationX = 290;
                itemDef.rotationY = 282;
                itemDef.modelZoom = 984;
                itemDef.modelOffset1 = -3;
                break;

            case 2750:
                itemDef.name = "Pile of Sardines";
                itemDef.description = "A pile of raw seafood.";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Separate";
                itemDef.modelID = 26673;
                itemDef.rotationX = 290;
                itemDef.rotationY = 282;
                itemDef.modelZoom = 984;
                itemDef.modelOffset1 = -3;
                break;

            case 2751:
                itemDef.name = "Pile of Herring";
                itemDef.description = "A pile of raw seafood.";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Separate";
                itemDef.modelID = 26673;
                itemDef.rotationX = 290;
                itemDef.rotationY = 282;
                itemDef.modelZoom = 984;
                itemDef.modelOffset1 = -3;
                break;

            case 2752:
                itemDef.name = "Pile of Mackerel";
                itemDef.description = "A pile of raw seafood.";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Separate";
                itemDef.modelID = 26673;
                itemDef.rotationX = 290;
                itemDef.rotationY = 282;
                itemDef.modelZoom = 984;
                itemDef.modelOffset1 = -3;
                break;

            case 2753:
                itemDef.name = "Pile of Cod";
                itemDef.description = "A pile of raw seafood.";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Separate";
                itemDef.modelID = 26673;
                itemDef.rotationX = 290;
                itemDef.rotationY = 282;
                itemDef.modelZoom = 984;
                itemDef.modelOffset1 = -3;
                break;

            case 2754:
                itemDef.name = "Pile of Bass";
                itemDef.description = "A pile of raw seafood.";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Separate";
                itemDef.modelID = 26673;
                itemDef.rotationX = 290;
                itemDef.rotationY = 282;
                itemDef.modelZoom = 984;
                itemDef.modelOffset1 = -3;
                break;

            case 2755:
                itemDef.name = "Pile of Trout";
                itemDef.description = "A pile of raw seafood.";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Separate";
                itemDef.modelID = 26673;
                itemDef.rotationX = 290;
                itemDef.rotationY = 282;
                itemDef.modelZoom = 984;
                itemDef.modelOffset1 = -3;
                break;

            case 2756:
                itemDef.name = "Pile of Salmon";
                itemDef.description = "A pile of raw seafood.";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Separate";
                itemDef.modelID = 26673;
                itemDef.rotationX = 290;
                itemDef.rotationY = 282;
                itemDef.modelZoom = 984;
                itemDef.modelOffset1 = -3;
                break;

            case 2757:
                itemDef.name = "Pile of Lobster";
                itemDef.description = "A pile of raw seafood.";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Separate";
                itemDef.modelID = 26673;
                itemDef.rotationX = 290;
                itemDef.rotationY = 282;
                itemDef.modelZoom = 984;
                itemDef.modelOffset1 = -3;
                break;

            case 2758:
                itemDef.name = "Pile of Tuna";
                itemDef.description = "A pile of raw seafood.";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Separate";
                itemDef.modelID = 26673;
                itemDef.rotationX = 290;
                itemDef.rotationY = 282;
                itemDef.modelZoom = 984;
                itemDef.modelOffset1 = -3;
                break;

            case 2759:
                itemDef.name = "Pile of Swordfish";
                itemDef.description = "A pile of raw seafood.";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Separate";
                itemDef.modelID = 26673;
                itemDef.rotationX = 290;
                itemDef.rotationY = 282;
                itemDef.modelZoom = 984;
                itemDef.modelOffset1 = -3;
                break;

            case 2760:
                itemDef.name = "Pile of Monkfish";
                itemDef.description = "A pile of raw seafood.";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Separate";
                itemDef.modelID = 26673;
                itemDef.rotationX = 290;
                itemDef.rotationY = 282;
                itemDef.modelZoom = 984;
                itemDef.modelOffset1 = -3;
                break;

            case 2761:
                itemDef.name = "Pile of Manta Ray";
                itemDef.description = "A pile of raw seafood.";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Separate";
                itemDef.modelID = 26673;
                itemDef.rotationX = 290;
                itemDef.rotationY = 282;
                itemDef.modelZoom = 984;
                itemDef.modelOffset1 = -3;
                break;

            case 2762:
                itemDef.name = "Pile of Shark";
                itemDef.description = "A pile of raw seafood.";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Separate";
                itemDef.modelID = 26673;
                itemDef.rotationX = 290;
                itemDef.rotationY = 282;
                itemDef.modelZoom = 984;
                itemDef.modelOffset1 = -3;
                break;

            case 2763:
                itemDef.name = "Pile of Rocktail";
                itemDef.description = "A pile of raw seafood.";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Separate";
                itemDef.modelID = 26673;
                itemDef.rotationX = 290;
                itemDef.rotationY = 282;
                itemDef.modelZoom = 984;
                itemDef.modelOffset1 = -3;
                break;
            case 2780:
                itemDef.name = "Pile of Anglerfish";
                itemDef.description = "A pile of raw seafood.";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Separate";
                itemDef.modelID = 26673;
                itemDef.rotationX = 290;
                itemDef.rotationY = 282;
                itemDef.modelZoom = 984;
                itemDef.modelOffset1 = -3;
                break;


            //customs
            case 20556:
                itemDef.name = "3rd age pickaxe";
                itemDef.modelID = 31962;
                itemDef.actions = new String[]{null, "Wield", null, null, "Drop"};
                itemDef.maleEquip1 = 31887;
                itemDef.femaleEquip1 = 31887;
                itemDef.rotationX = 224;
                itemDef.rotationY = 1056;
                itemDef.modelZoom = 1095;
                itemDef.modelOffsetY = 3;
                itemDef.modelOffset1 = 7;
                break;
            case 20580:
                itemDef.name = "Tome of fire";
                itemDef.modelID = 91246;
                itemDef.actions = new String[]{null, "Wield", null, null, "Drop"};
                itemDef.maleEquip1 = 91247;
                itemDef.femaleEquip1 = 91247;
                itemDef.rotationX = 0; //244
                itemDef.rotationY = 600; //116
                itemDef.modelZoom = 830;
                itemDef.modelOffsetX = 1;
                itemDef.modelOffsetY = 0;
                break;
            case 20558:
                itemDef.name = "Mole jaw";
                itemDef.modelID = 91248;
                itemDef.actions = new String[]{null, null, null, null, "Drop"};
                itemDef.rotationX = 84;
                itemDef.rotationY = 12;
                itemDef.modelZoom = 839;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -1;
                break;
            case 20559:
                itemDef.name = "Neitiznot faceguard";
                itemDef.modelID = 91249;
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                itemDef.maleEquip1 = 91250;
                itemDef.femaleEquip1 = 91250;
                itemDef.rotationX = 126;
                itemDef.rotationY = 129;
                itemDef.modelZoom = 984;
                itemDef.modelOffset1 = -1;
                itemDef.modelOffsetY = 1;
                itemDef.maleYOffset = 2;
                itemDef.femaleYOffset = 2;
                break;
            case 20560:
                itemDef.name = "Imbued Heart";
                itemDef.modelID = 91251;
                itemDef.actions = new String[]{"Invigorate", null, null, null, "Drop"};
                itemDef.rotationX = 96;
                itemDef.rotationY = 500;
                itemDef.modelZoom = 1168;
                itemDef.modelOffsetX = 1;
                itemDef.modelOffsetY = -1;
                break;
            case 20561:
                itemDef.name = "Obsidian helmet";
                itemDef.modelID = 91252;
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                itemDef.maleEquip1 = 91253;
                itemDef.femaleEquip1 = 91253;
                itemDef.rotationX = 87;
                itemDef.rotationY = 1953;
                itemDef.modelZoom = 716;
                itemDef.modelOffsetY = -3;
                break;
            case 20562:
                itemDef.name = "Obsidian platebody";
                itemDef.modelID = 91254;
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                itemDef.maleEquip1 = 91255;
                itemDef.femaleEquip1 = 91255;
                itemDef.rotationX = 185;
                itemDef.rotationY = 485;
                itemDef.modelZoom = 1464;
                itemDef.modelOffsetY = 8;
                break;
            case 20563:
                itemDef.name = "Obsidian platelegs";
                itemDef.modelID = 91256;
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                itemDef.maleEquip1 = 91257;
                itemDef.femaleEquip1 = 91257;
                itemDef.rotationX = 133;
                itemDef.rotationY = 485;
                itemDef.modelZoom = 1853;
                break;
            case 20564:
                itemDef.name = "Ancient wyvern shield";
                itemDef.modelID = 91258;
                itemDef.actions = new String[]{null, "Wield", null, null, "Drop"};
                itemDef.maleEquip1 = 91259;
                itemDef.femaleEquip1 = 91259;
                itemDef.rotationX = 1000;
                itemDef.rotationY = 600;
                itemDef.modelZoom = 1936;
                break;
            case 20565:
                itemDef.name = "Brimstone ring";
                itemDef.modelID = 91260;
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                itemDef.rotationX = 287;
                itemDef.rotationY = 500;
                itemDef.modelZoom = 655;
                itemDef.modelOffsetY = -4;
                break;
            case 20566:
                itemDef.name = "Zenyte shard";
                itemDef.modelID = 91261;
                itemDef.rotationX = 0;
                itemDef.rotationY = 300;
                itemDef.modelZoom = 636;
                break;
            case 20568:
                itemDef.name = "Nightmare staff";
                itemDef.modelID = 91264;
                itemDef.actions = new String[]{null, "Wield", null, null, "Drop"};
                itemDef.maleEquip1 = 91265;
                itemDef.femaleEquip1 = 91265;
                itemDef.rotationX = 400;
                itemDef.rotationY = 1400;
                itemDef.modelZoom = 1500;
                //itemDef.modelOffsetX = -5;
                //itemDef.modelOffsetY = 13;
                break;
            case 20569:
                itemDef.name = "Harmonised nightmare staff";
                itemDef.modelID = 91266;
                itemDef.actions = new String[]{null, "Wield", null, null, "Drop"};
                itemDef.maleEquip1 = 91267;
                itemDef.femaleEquip1 = 91267;
                itemDef.rotationX = 400;
                itemDef.rotationY = 1400;
                itemDef.modelZoom = 1500;
                //itemDef.modelOffsetX = -5;
                //itemDef.modelOffsetY = 13;
                break;
            case 20570:
                itemDef.name = "Volatile nightmare staff";
                itemDef.modelID = 91268;
                itemDef.actions = new String[]{null, "Wield", null, null, "Drop"};
                itemDef.maleEquip1 = 91269;
                itemDef.femaleEquip1 = 91269;
                itemDef.rotationX = 400;
                itemDef.rotationY = 1400;
                itemDef.modelZoom = 1500;
                //itemDef.modelOffsetX = -5;
                //itemDef.modelOffsetY = 13;
                break;
            case 20571:
                itemDef.name = "Eldritch nightmare staff";
                itemDef.modelID = 91270;
                itemDef.actions = new String[]{null, "Wield", null, null, "Drop"};
                itemDef.maleEquip1 = 91271;
                itemDef.femaleEquip1 = 91271;
                itemDef.rotationX = 400;
                itemDef.rotationY = 1400;
                itemDef.modelZoom = 1500;
                //itemDef.modelOffsetX = -5;
                //itemDef.modelOffsetY = 13;
                break;
            case 20572:
                itemDef.name = "Harmonised Orb";
                itemDef.actions = new String[]{null, null, null, null, "Drop"};
                itemDef.modelID = 91272;
                itemDef.rotationX = 225;
                itemDef.rotationY = 114;
                itemDef.modelZoom = 1614;
                break;
            case 20573:
                itemDef.name = "Volatile Orb";
                itemDef.actions = new String[]{null, null, null, null, "Drop"};
                itemDef.modelID = 91273;
                itemDef.rotationX = 225;
                itemDef.rotationY = 114;
                itemDef.modelZoom = 1614;
                break;
            case 20574:
                itemDef.name = "Eldritch Orb";
                itemDef.actions = new String[]{null, null, null, null, "Drop"};
                itemDef.modelID = 91274;
                itemDef.rotationX = 225;
                itemDef.rotationY = 114;
                itemDef.modelZoom = 1614;
                break;


            case 21035:
                itemDef.name = "Zaros Godsword";
                itemDef.modelID = 90697;
                itemDef.actions = new String[]{null, "Wield", null, null, "Drop"};
                itemDef.maleEquip1 = 90698;
                itemDef.femaleEquip1 = 90698;
                itemDef.modelZoom = 1957;
                itemDef.rotationY = 498;
                itemDef.rotationX = 484;
                break;

            case 10506:
                itemDef.name = "Imbue Crystal";
                break;
            case 18891:
                itemDef.name = "Blood Godsword";
                itemDef.modelID = 28162;
                itemDef.actions = new String[]{null, "Wield", null, null, "Drop"};
                itemDef.newModelColor = new int[]{935, 933, 933};
                itemDef.editedModelColor = new int[]{22208, 22477, 22464};
                itemDef.maleEquip1 = 27731;
                itemDef.femaleEquip1 = 27731;
                itemDef.modelZoom = 1957;
                itemDef.rotationY = 498;
                itemDef.rotationX = 484;
                break;
            case 18904:
                itemDef.name = "Blessed staff of light";
                itemDef.modelZoom = 1853;
                itemDef.rotationX = 1508;
                itemDef.rotationY = 364;
                itemDef.modelOffsetY = 21;
                itemDef.modelOffset1 = 1;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                itemDef.modelID = 34518;
                itemDef.maleEquip1 = 34508;
                itemDef.femaleEquip1 = 34508;
                break;
            case 18902:
                itemDef.name = "Trident of the swamp";
                itemDef.modelZoom = 2421;
                itemDef.rotationY = 1549;
                itemDef.rotationX = 1818;
                itemDef.modelOffsetY = 9;
                itemDef.modelOffsetX = 290;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                itemDef.modelID = 19223;
                itemDef.maleEquip1 = 14400;
                itemDef.femaleEquip1 = 14400;
                break;
            case 18903:
                itemDef.name = "Granite maul (or)";
                itemDef.modelZoom = 1789;
                itemDef.rotationY = 157;
                itemDef.rotationX = 148;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions[1] = "Wear";
                itemDef.actions[2] = "Check-charges";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 28990;
                itemDef.maleEquip1 = 28992;
                itemDef.femaleEquip1 = 28992;
                break;
            case 18895:
                itemDef.name = "Necklace of anguish";
                itemDef.modelZoom = 1020;
                itemDef.rotationY = 332;
                itemDef.rotationX = 2020;
                itemDef.modelOffsetY = -16;
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[2] = "Check-charges";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 31510;
                itemDef.maleEquip1 = 31228;
                itemDef.femaleEquip1 = 31228;
                break;
            case 18896:
                itemDef.name = "Amulet of torture";
                itemDef.modelZoom = 620;
                itemDef.rotationY = 424;
                itemDef.rotationX = 68;
                itemDef.modelOffset1 = 1;
                itemDef.modelOffsetY = 16;
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[2] = "Check-charges";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 31524;
                itemDef.maleEquip1 = 31227;
                itemDef.femaleEquip1 = 31227;
                break;
            case 18897:
                itemDef.name = "Occult necklace";
                itemDef.modelZoom = 589;
                itemDef.rotationY = 431;
                itemDef.rotationX = 81;
                itemDef.modelOffset1 = 3;
                itemDef.modelOffsetY = 21;
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 28438;
                itemDef.maleEquip1 = 28445;
                itemDef.femaleEquip1 = 28445;
                break;
            case 18898:
                itemDef.name = "Ring of suffering";
                itemDef.modelZoom = 830;
                itemDef.rotationY = 322;
                itemDef.rotationX = 135;
                itemDef.modelOffset1 = -1;
                itemDef.modelOffsetY = 1;
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[2] = "Check-charges";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 31519;
                itemDef.maleEquip1 = 31519;
                itemDef.femaleEquip1 = 31519;
                break;


            case 20998:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[2] = "Check-charges";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 32799;
                itemDef.name = "Twisted bow";
                itemDef.modelZoom = 2000;
                itemDef.rotationY = 720;
                itemDef.rotationX = 1500;
                itemDef.modelOffset1 = 3;
                itemDef.modelOffsetY = 1;
                itemDef.femaleEquip1 = 32674;
                itemDef.maleEquip1 = 32674;
                itemDef.description = "A mystical bow carved from the twisted remains of the Great Olm.";
                break;
            case 20061:
                itemDef.modelID = 10247;
                itemDef.name = "Abyssal vine whip";
                itemDef.description = "Abyssal vine whip";
                itemDef.modelZoom = 848;
                itemDef.rotationY = 324;
                itemDef.rotationX = 1808;
                itemDef.modelOffset1 = 5;
                itemDef.modelOffsetY = 38;
                itemDef.maleEquip1 = 10253;
                itemDef.femaleEquip1 = 10253;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                break;
            case 20010:
                itemDef.name = "Trickster robe";
                itemDef.description = "Its a Trickster robe";
                itemDef.maleEquip1 = 44786;
                itemDef.femaleEquip1 = 44786;
                itemDef.modelID = 45329;
                itemDef.rotationY = 593;
                itemDef.rotationX = 2041;
                itemDef.modelZoom = 1420;
                itemDef.modelOffsetY = 0;
                itemDef.modelOffset1 = 0;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                break;
            case 20011:
                itemDef.name = "Trickster robe legs";
                itemDef.description = "Its a Trickster robe";
                itemDef.maleEquip1 = 44770;
                itemDef.femaleEquip1 = 44770;
                itemDef.modelID = 45335;
                itemDef.rotationY = 567;
                itemDef.rotationX = 1023;
                itemDef.modelZoom = 2105;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 0;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                break;
            case 20012:
                itemDef.name = "Trickster helm";
                itemDef.description = "Its a Trickster helm";
                itemDef.maleEquip1 = 44764;
                itemDef.femaleEquip1 = 44764;
                itemDef.modelID = 45328;
                itemDef.rotationY = 5;
                itemDef.rotationX = 1889;
                itemDef.modelZoom = 738;
                itemDef.modelOffsetY = 0;
                itemDef.modelOffset1 = 0;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                break;
            case 20013:
                itemDef.modelID = 44633;
                itemDef.name = "Vanguard helm";
                itemDef.modelZoom = 855;
                itemDef.rotationY = 1966;
                itemDef.rotationX = 5;
                itemDef.modelOffsetY = 4;
                itemDef.modelOffset1 = -1;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.maleEquip1 = 44769;
                itemDef.femaleEquip1 = 44769;
                break;
            case 20014:
                itemDef.modelID = 44627;
                itemDef.name = "Vanguard body";
                itemDef.modelZoom = 1513;
                itemDef.rotationX = 2041;
                itemDef.rotationY = 593;
                itemDef.modelOffset1 = 3;
                itemDef.modelOffsetY = -11;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.maleEquip1 = 44812;
                itemDef.femaleEquip1 = 44812;
                break;
            case 14062:
                itemDef.modelID = 50011;
                itemDef.name = "Vanguard legs";
                itemDef.modelZoom = 1711;
                itemDef.rotationX = 0;
                itemDef.rotationY = 360;
                itemDef.modelOffset1 = 3;
                itemDef.modelOffsetY = -11;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.maleEquip1 = 44771;
                itemDef.femaleEquip1 = 44771;
                break;
            case 19020:
                itemDef.modelID = 44699;
                itemDef.name = "Vanguard gloves";
                itemDef.modelZoom = 830;
                itemDef.rotationY = 536;
                itemDef.rotationX = 0;
                itemDef.modelOffsetX = 9;
                itemDef.modelOffsetY = 3;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                itemDef.maleEquip1 = 44758;
                itemDef.femaleEquip1 = 44758;
                break;
            case 19021:
                itemDef.modelID = 44700;
                itemDef.name = "Vanguard boots";
                itemDef.modelZoom = 848;
                itemDef.rotationY = 141;
                itemDef.rotationX = 141;
                itemDef.modelOffset1 = 4;
                itemDef.modelOffsetY = 0;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                itemDef.maleEquip1 = 44752;
                itemDef.femaleEquip1 = 44752;
                break;
            case 20016:
                itemDef.modelID = 44704;
                itemDef.name = "Battle-mage helm";
                itemDef.modelZoom = 658;
                itemDef.rotationX = 1898;
                itemDef.rotationY = 2;
                itemDef.modelOffset1 = 12;
                itemDef.modelOffsetY = 3;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.maleEquip1 = 44767;
                itemDef.femaleEquip1 = 44767;
                break;
            case 20017:
                itemDef.modelID = 44631;
                itemDef.name = "Battle-mage robe";
                itemDef.modelZoom = 1382;
                itemDef.rotationX = 3;
                itemDef.rotationY = 488;
                itemDef.modelOffset1 = 1;
                itemDef.modelOffsetY = 0;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.maleEquip1 = 44818;
                itemDef.femaleEquip1 = 44818;
                break;
            case 20018:
                itemDef.modelID = 44672;
                itemDef.name = "Battle-mage robe legs";
                itemDef.modelZoom = 1842;
                itemDef.rotationX = 1024;
                itemDef.rotationY = 498;
                itemDef.modelOffset1 = 4;
                itemDef.modelOffsetY = -1;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.maleEquip1 = 44775;
                itemDef.femaleEquip1 = 44775;
                break;
            case 20019:
                itemDef.modelID = 45316;
                itemDef.name = "Trickster boots";
                itemDef.modelZoom = 848;
                itemDef.rotationY = 141;
                itemDef.rotationX = 141;
                itemDef.modelOffset1 = -9;
                itemDef.modelOffsetY = 0;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.maleEquip1 = 44757;
                itemDef.femaleEquip1 = 44757;
                break;
            case 20020:
                itemDef.modelID = 45317;
                itemDef.name = "Trickster gloves";
                itemDef.modelZoom = 830;
                itemDef.rotationX = 150;
                itemDef.rotationY = 536;
                itemDef.modelOffset1 = 1;
                itemDef.modelOffsetY = 3;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.maleEquip1 = 44761;
                itemDef.femaleEquip1 = 44761;
                break;
            case 20021:
                itemDef.modelID = 44662;
                itemDef.name = "Battle-mage boots";
                itemDef.modelZoom = 987;
                itemDef.rotationX = 1988;
                itemDef.rotationY = 188;
                itemDef.modelOffset1 = -8;
                itemDef.modelOffsetY = 5;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.maleEquip1 = 44755;
                itemDef.femaleEquip1 = 44755;
                break;
            case 20022:
                itemDef.modelID = 44573;
                itemDef.name = "Battle-mage gloves";
                itemDef.modelZoom = 1053;
                itemDef.rotationX = 0;
                itemDef.rotationY = 536;
                itemDef.modelOffset1 = 3;
                itemDef.modelOffsetY = 0;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.maleEquip1 = 44762;
                itemDef.femaleEquip1 = 44762;
                break;
            case 11554:
                itemDef.name = "Abyssal tentacle";
                itemDef.modelZoom = 840;
                itemDef.rotationY = 280;
                itemDef.rotationX = 121;
                itemDef.modelOffsetY = 56;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[2] = "Check-charges";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 28439;
                itemDef.maleEquip1 = 45006;
                itemDef.femaleEquip1 = 43500;
                break;
            case 11926:
                itemDef.name = "Odium ward";
                itemDef.modelZoom = 1200;
                itemDef.rotationY = 568;
                itemDef.rotationX = 1836;
                itemDef.modelOffsetX = 2;
                itemDef.modelOffsetY = 3;
                itemDef.newModelColor = new int[]{15252};
                itemDef.editedModelColor = new int[]{908};
                itemDef.modelID = 9354;
                itemDef.actions[1] = "Wield";
                itemDef.actions[4] = "Drop";
                itemDef.maleEquip1 = 9347;
                itemDef.femaleEquip1 = 9347;
                break;
            case 11288:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 926;
                itemDef.newModelColor[0] = 196608;
                itemDef.modelID = 2438;
                itemDef.modelZoom = 730;
                itemDef.rotationY = 516;
                itemDef.rotationX = 0;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = -10;
                itemDef.maleEquip1 = 3188;
                itemDef.femaleEquip1 = 3192;
                itemDef.name = "Black h'ween Mask";
                itemDef.description = "Aaaarrrghhh... I'm a monster.";
                break;
            case 11924:
                itemDef.name = "Malediction ward";
                itemDef.modelZoom = 1200;
                itemDef.rotationY = 568;
                itemDef.rotationX = 1836;
                itemDef.modelOffsetX = 2;
                itemDef.modelOffsetY = 3;
                itemDef.newModelColor = new int[]{-21608};
                itemDef.editedModelColor = new int[]{908};
                itemDef.modelID = 9354;
                itemDef.actions[1] = "Wield";
                itemDef.actions[4] = "Drop";
                itemDef.maleEquip1 = 9347;
                itemDef.femaleEquip1 = 9347;
                break;
            case 12282:
                itemDef.name = "Serpentine helm";
                itemDef.modelID = 19220;
                itemDef.modelZoom = 700;
                itemDef.rotationX = 1724;
                itemDef.rotationY = 215;
                itemDef.modelOffsetX = 17;
                itemDef.femaleEquip1 = 14398;
                itemDef.maleEquip1 = 14395;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                break;
            case 12279:
                itemDef.name = "Magma helm";
                itemDef.modelID = 29205;
                itemDef.modelZoom = 700;
                itemDef.rotationX = 1724;
                itemDef.rotationY = 215;
                itemDef.modelOffsetX = 17;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.femaleEquip1 = 14426;
                itemDef.maleEquip1 = 14424;
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                break;
            case 12278:
                itemDef.name = "Tanzanite helm";
                itemDef.modelID = 29213;
                itemDef.modelZoom = 700;
                itemDef.rotationX = 1724;
                itemDef.rotationY = 215;
                itemDef.modelOffsetX = 17;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.femaleEquip1 = 23994;
                itemDef.maleEquip1 = 14421;
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                break;
            case 13239:
                itemDef.name = "Primordial boots";
                itemDef.modelID = 29397;
                itemDef.modelZoom = 976;
                itemDef.rotationY = 147;
                itemDef.rotationX = 279;
                itemDef.modelOffsetX = 5;
                itemDef.modelOffsetY = 5;
                itemDef.maleEquip1 = 29250;
                itemDef.femaleEquip1 = 29255;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions[1] = "Wear";
                itemDef.actions[2] = "Check-charges";
                itemDef.actions[4] = "Drop";
                break;
            case 12708:
                itemDef.name = "Pegasian boots";
                itemDef.modelID = 29396;
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[2] = "Check-charges";
                itemDef.actions[4] = "Drop";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.modelZoom = 900;
                itemDef.rotationY = 165;
                itemDef.rotationX = 279;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = -7;
                itemDef.maleEquip1 = 29252;
                itemDef.femaleEquip1 = 29253;
                break;
            case 13235:
                itemDef.name = "Eternal boots";
                itemDef.modelID = 29394;
                itemDef.modelZoom = 976;
                itemDef.rotationY = 147;
                itemDef.rotationX = 279;
                itemDef.modelOffsetX = 5;
                itemDef.modelOffsetY = 5;
                itemDef.maleEquip1 = 29249;
                itemDef.femaleEquip1 = 29254;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions[1] = "Wear";
                itemDef.actions[2] = "Check-charges";
                itemDef.actions[4] = "Drop";
                break;

            case 7020:
                itemDef.name = "Global Accuracy Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 7021:
                itemDef.name = "Global Accelerate Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 7022:
                itemDef.name = "Global Drop Rate Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 7023:
                itemDef.name = "Global 2x Boss PP Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 7024:
                itemDef.name = "Global 2x Skiller PP Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 7025:
                itemDef.name = "Global 2x Slayer PP Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 7026:
                itemDef.name = "Global Lifelink Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 7027:
                itemDef.name = "Global Loaded Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 7028:
                itemDef.name = "Global Double Loot Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 7029:
                itemDef.name = "Global Double XP Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 7030:
                itemDef.name = "Global Event Box Bonanza";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 7031:
                itemDef.name = "Global Boss Kills Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 7032:
                itemDef.name = "Global Max Hit Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 7033:
                itemDef.name = "Global Efficiency Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 7034:
                itemDef.name = "Global Berserker Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 7035:
                itemDef.name = "Global Raider Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 7040:
                itemDef.name = "Global Locked and Loaded Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 7041:
                itemDef.name = "Global Blue Collar Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 7042:
                itemDef.name = "Global Adventurer Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 7043:
                itemDef.name = "Global Slaughter Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 7044:
                itemDef.name = "Global Rags to Riches Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 7045:
                itemDef.name = "Global Armed and Dangerous Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 7046:
                itemDef.name = "Global Flourish Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 7047:
                itemDef.name = "Global Flesh and Blood Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 7048:
                itemDef.name = "Global Loaded Raids Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 7049:
                itemDef.name = "Global Alive and Well Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 7050:
                itemDef.name = "Global Tomb Raider Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 7051:
                itemDef.name = "Global Alive and Well Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 7052:
                itemDef.name = "Global Alive and Well Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 7053:
                itemDef.name = "Global Alive and Well Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 7054:
                itemDef.name = "Global Alive and Well Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{926, 926, 926, 926, 926, 926, 926};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;


            case 4020:
                itemDef.name = "Personal Accuracy Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{-21526, -21526, -21526, -21526, -21526, -21526, -21526};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 4021:
                itemDef.name = "Personal Accelerate Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{-21526, -21526, -21526, -21526, -21526, -21526, -21526};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 4022:
                itemDef.name = "Personal Drop Rate Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{-21526, -21526, -21526, -21526, -21526, -21526, -21526};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 4023:
                itemDef.name = "Personal 2x Boss PP Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{-21526, -21526, -21526, -21526, -21526, -21526, -21526};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 4024:
                itemDef.name = "Personal 2x Skiller PP Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{-21526, -21526, -21526, -21526, -21526, -21526, -21526};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 4025:
                itemDef.name = "Personal 2x Slayer PP Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{-21526, -21526, -21526, -21526, -21526, -21526, -21526};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 4026:
                itemDef.name = "Personal Lifelink Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{-21526, -21526, -21526, -21526, -21526, -21526, -21526};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 4027:
                itemDef.name = "Personal Loaded Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{-21526, -21526, -21526, -21526, -21526, -21526, -21526};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 4028:
                itemDef.name = "Personal Double Loot Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{-21526, -21526, -21526, -21526, -21526, -21526, -21526};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 4029:
                itemDef.name = "Personal Double XP Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{-21526, -21526, -21526, -21526, -21526, -21526, -21526};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 4030:
                itemDef.name = "Personal Event Box Bonanza";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{-21526, -21526, -21526, -21526, -21526, -21526, -21526};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 4031:
                itemDef.name = "Personal Boss Kills Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{-21526, -21526, -21526, -21526, -21526, -21526, -21526};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 4032:
                itemDef.name = "Personal Max Hit Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{-21526, -21526, -21526, -21526, -21526, -21526, -21526};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 2709:
                itemDef.name = "Random Personal Event";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{-21726, -21726, -21726, -21726, -21726, -21726, -21726};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 6854:
                itemDef.name = "Equipment Upgrade Box";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Open";
                break;

            case 4033:
                itemDef.name = "High Unique Upgrade";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{43064, 43064, 43064, 43064, 43064, 43064, 43064};
                itemDef.actions[4] = "Drop";
                break;

            case 4034:
                itemDef.name = "Legendary Unique Upgrade";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{43064, 43064, 43064, 43064, 43064, 43064, 43064};
                itemDef.actions[4] = "Drop";
                break;

            case 4035:
                itemDef.name = "Master Unique Upgrade";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{43064, 43064, 43064, 43064, 43064, 43064, 43064};
                itemDef.actions[4] = "Drop";
                break;

            case 4036:
                itemDef.name = "Medium Unique Upgrade";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{43064, 43064, 43064, 43064, 43064, 43064, 43064};
                itemDef.actions[4] = "Drop";
                break;

            case 4037:
                itemDef.name = "Low Unique Upgrade";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{43064, 43064, 43064, 43064, 43064, 43064, 43064};
                itemDef.actions[4] = "Drop";
                break;

            case 4039:
                itemDef.name = "Wild Unique Upgrade";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{43064, 43064, 43064, 43064, 43064, 43064, 43064};
                itemDef.actions[4] = "Drop";
                break;

            case 4041:
                itemDef.name = "Custom Unique Upgrade";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{43064, 43064, 43064, 43064, 43064, 43064, 43064};
                itemDef.actions[4] = "Drop";
                break;

            case 15008:
                itemDef.name = "DMZ Locator";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{"Locate", null, null, null, "Drop"};
                break;

            case 7930:
                itemDef.name = "Easter Bunny Pet";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                break;

            case 9941:
                itemDef.name = "Pet Tryout";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                break;

            case 6550: //Lazy Cat
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                break;

            case 16137:
                itemDef.name = "Thok's Sword";
                break;
            case 1543:
                itemDef.name = "Wilderness Key";
                break;
            case 7938:
                itemDef.name = "Dense Essence";
                break;
            case 1779:
                itemDef.actions = new String[]{"Spin", null, null, null, null};
                break;
            case 964:
                itemDef.name = "Death Pet";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                break;

            case 2948:
                itemDef.name = "Pet Leprechaun";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                break;

            case 8740:
                itemDef.name = "Pet Duradead";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                break;

            case 5:
                itemDef.name = "Casino rules";
                break;

            case 20581:
                itemDef.name = "PaeScape Shield";
                itemDef.modelID = 40921;
                itemDef.maleEquip1 = 91293;
                itemDef.modelZoom = 1789;
                itemDef.rotationX = 1000;
                itemDef.rotationY = 400;
                itemDef.rotationZ = 400;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 11;
                itemDef.editedModelColor = new int[]{36820, 127, 36411, 36517};
                itemDef.newModelColor = new int[]{933, 51, 926, 919};
                itemDef.editedModelColor= new int[] {36820, 127, 36411, 36517, 15874, 15880, 15885, 15913, 15916, 15919, 15922, 15925, 15928, 15945, 15951};
                itemDef.newModelColor = new int[] {952, 51, 952, 952, 952, 952, 952, 952, 952, 952, 952, 952, 952, 952, 952};
                itemDef.actions = new String[5];
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                break;

            case 20582:
                itemDef.name = "Soul Shield";
                itemDef.modelID = 40921;
                itemDef.maleEquip1 = 91293;
                itemDef.modelZoom = 1789;
                itemDef.rotationX = 1000;
                itemDef.rotationY = 400;
                itemDef.rotationZ = 400;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 11;
                itemDef.editedModelColor= new int[] {36820, 127, 36411, 36517, 15874, 15880, 15885, 15913, 15916, 15919, 15922, 15925, 15928, 15945, 15951};
                itemDef.newModelColor = new int[] {51, 127, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51};
                itemDef.actions = new String[5];
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                break;

            case 20583:
                itemDef.name = "Pink Spirit Shield";
                itemDef.modelID = 40921;
                itemDef.maleEquip1 = 91293;
                itemDef.modelZoom = 1789;
                itemDef.rotationX = 1000;
                itemDef.rotationY = 400;
                itemDef.rotationZ = 400;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 11;
                itemDef.editedModelColor= new int[] {36820, 127, 36411, 36517, 15874, 15880, 15885, 15913, 15916, 15919, 15922, 15925, 15928, 15945, 15951};
                itemDef.newModelColor = new int[] {257770, 127, 123770, 380770, 380770, 380770, 380770, 123770, 123770, 123770, 123770, 123770, 257770, 257770, 257770};
                itemDef.actions = new String[5];
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                break;

            case 18829:
                itemDef.actions = new String[5];
                itemDef.actions = new String[]{"Move to Leader", null, "Move to Entrance", null, "Drop"};
                break;


            //SHR uniques
            case 21061:
            case 21062:
            case 21063:
            case 21064:
            case 21065:
            case 21066:
            case 21067:
            case 21068:
            case 21069:
            case 21070:
            case 21071:
            case 21072:
            case 21073:
            case 21074:
            case 21075:
            case 21076:
            case 21077:
            case 21078:
            case 21079:
            case 21080:
            case 21081:
            case 21082:
            case 21083:
            case 21084:
            case 21085:
            case 21086:
            case 21087:
            case 21088:
            case 21089:
            case 21090:
            case 762:
            case 763:
            case 764:
            case 765:
            case 766:
            case 767:
                customSHRuniques(i, itemDef);
                break;

            //custom Bandos sets
            case 21016:
            case 21017:
            case 21018:
            case 21019:
            case 21020:
            case 21021:
            case 21022:
            case 21023:
            case 21024:
            case 21028:
            case 21029:
            case 21030:
            case 2768:
            case 2769:
            case 2770:
            case 2771:
            case 2772:
            case 2773:
            case 2774:
            case 2775:
            case 2042:
            case 2043:
            case 2044:
                customBandosForID(i, itemDef);
                break;

            //Custom Neitiznot Faceguard
            case 2710:
            case 2711:
            case 2712:
            case 2713:
            case 2716:
                customNeitiznotForID(i, itemDef);
                break;

            //custom Armadyl Sets
            case 21007:
            case 21008:
            case 21009:
            case 21010:
            case 21011:
            case 21012:
            case 21013:
            case 21014:
            case 21015:
            case 21025:
            case 21026:
            case 21027:
            case 2045:
            case 2046:
            case 2047:
                customArmadylForID(i, itemDef);
                break;

            case 18888:
            case 18889:
            case 18890:
            case 18892:
            case 18893:
            case 18894:
            case 18899:
            case 18900:
            case 18901:
            case 18905:
            case 18906:
            case 18907:
            case 2765:
            case 2766:
            case 2767:
            case 2051:
            case 2052:
            case 2053:
                customAncestralForID(i, itemDef);
                break;


            case 2776:
            case 2777:
            case 2778:
            case 2779:
                customHolidayForID(i, itemDef);
                break;

            case 906:
            case 907:
            case 908:
            case 909:
            case 910:
            case 911:
            case 912:
            case 913:
            case 914:
            case 915:
                customSubscriptionItems(i, itemDef);
                break;


            case 21031:
                itemDef.name = "Soul Crystal";
                itemDef.editedModelColor = new int[]{32999, 33003, 33366, 32879, 32995};
                itemDef.newModelColor = new int[]{51136, 51140, 51136, 51336, 51132};
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 20476;
                itemDef.modelZoom = 2384;
                itemDef.rotationX = 1496;
                itemDef.rotationY = 240;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -80;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.value = 0;
                itemDef.team = 0;
                break;


            case 21032:
                itemDef.name = "Blood Crystal";
                itemDef.editedModelColor = new int[]{32999, 33003, 33366, 32879, 32995};
                itemDef.newModelColor = new int[]{929, 933, 929, 929, 925};
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 20476;
                itemDef.modelZoom = 2384;
                itemDef.rotationX = 1496;
                itemDef.rotationY = 240;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -80;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.value = 0;
                itemDef.team = 0;
                break;


            case 21033:
                itemDef.name = "Gilded Crystal";
                itemDef.editedModelColor = new int[]{32999, 33003, 33366, 32879, 32995};
                itemDef.newModelColor = new int[]{7114, 7118, 7114, 7114, 7114};
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 20476;
                itemDef.modelZoom = 2384;
                itemDef.rotationX = 1496;
                itemDef.rotationY = 240;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -80;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.value = 0;
                itemDef.team = 0;
                break;


            case 21034:
                itemDef.name = "Crystal of Enrichment";
                itemDef.editedModelColor = new int[]{32999, 33003, 33366, 32879, 32995};
                itemDef.newModelColor = new int[]{123770, 123770, 123770, 123770, 123770};
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 20476;
                itemDef.modelZoom = 2384;
                itemDef.rotationX = 1496;
                itemDef.rotationY = 240;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -80;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.value = 0;
                itemDef.team = 0;
                break;


            case 2050:
                itemDef.name = "Tainted Crystal";
                itemDef.editedModelColor = new int[]{32999, 33003, 33366, 32879, 32995};
                itemDef.newModelColor = new int[]{0, 0, 0, 0, 0};
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 20476;
                itemDef.modelZoom = 2384;
                itemDef.rotationX = 1496;
                itemDef.rotationY = 240;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -80;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.value = 0;
                itemDef.team = 0;
                break;

            case 21040:
                itemDef.name = "Soul boots of brimstone";
                itemDef.editedModelColor = new int[]{10411, 10407};
                itemDef.newModelColor = new int[]{51136, 51132};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 86930;
                itemDef.maleEquip1 = 86928;
                itemDef.femaleEquip1 = 86928;
                itemDef.maleEquip2 = 0;
                itemDef.femaleEquip2 = 0;
                itemDef.maleEquip3 = 0;
                itemDef.femaleEquip3 = 0;
                itemDef.modelZoom = 852;
                itemDef.rotationX = 228;
                itemDef.rotationY = 135;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -7;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 21041:
                itemDef.name = "Blood boots of brimstone";
                itemDef.editedModelColor = new int[]{10411, 10407};
                itemDef.newModelColor = new int[]{933, 929};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 86930;
                itemDef.maleEquip1 = 86928;
                itemDef.femaleEquip1 = 86928;
                itemDef.maleEquip2 = 0;
                itemDef.femaleEquip2 = 0;
                itemDef.maleEquip3 = 0;
                itemDef.femaleEquip3 = 0;
                itemDef.modelZoom = 852;
                itemDef.rotationX = 228;
                itemDef.rotationY = 135;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -7;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 21042:
                itemDef.name = "Gilded boots of brimstone";
                itemDef.editedModelColor = new int[]{10411, 10407};
                itemDef.newModelColor = new int[]{7118, 7114};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 86930;
                itemDef.maleEquip1 = 86928;
                itemDef.femaleEquip1 = 86928;
                itemDef.maleEquip2 = 0;
                itemDef.femaleEquip2 = 0;
                itemDef.maleEquip3 = 0;
                itemDef.femaleEquip3 = 0;
                itemDef.modelZoom = 852;
                itemDef.rotationX = 228;
                itemDef.rotationY = 135;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -7;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 21043:
                itemDef.name = "Enriched boots of brimstone";
                itemDef.editedModelColor = new int[]{10411, 10407};
                itemDef.newModelColor = new int[]{123770, 123770};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 86930;
                itemDef.maleEquip1 = 86928;
                itemDef.femaleEquip1 = 86928;
                itemDef.maleEquip2 = 0;
                itemDef.femaleEquip2 = 0;
                itemDef.maleEquip3 = 0;
                itemDef.femaleEquip3 = 0;
                itemDef.modelZoom = 852;
                itemDef.rotationX = 228;
                itemDef.rotationY = 135;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -7;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 2056:
                itemDef.name = "Tainted boots of brimstone";
                itemDef.editedModelColor = new int[]{10411, 10407};
                itemDef.newModelColor = new int[]{0, 20};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 86930;
                itemDef.maleEquip1 = 86928;
                itemDef.femaleEquip1 = 86928;
                itemDef.maleEquip2 = 0;
                itemDef.femaleEquip2 = 0;
                itemDef.maleEquip3 = 0;
                itemDef.femaleEquip3 = 0;
                itemDef.modelZoom = 852;
                itemDef.rotationX = 228;
                itemDef.rotationY = 135;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -7;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 21044:
                itemDef.name = "Soul barrows gloves";
                itemDef.editedModelColor = new int[]{10394, 8656};
                itemDef.newModelColor = new int[]{51136, 51143};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 13631;
                itemDef.maleEquip1 = 13307;
                itemDef.femaleEquip1 = 13307;
                itemDef.maleEquip2 = 0;
                itemDef.femaleEquip2 = 0;
                itemDef.maleEquip3 = 0;
                itemDef.femaleEquip3 = 0;
                itemDef.modelZoom = 789;
                itemDef.rotationX = 111;
                itemDef.rotationY = 609;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -1;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.value = 0;
                itemDef.team = 0;
                break;

            case 21045:
                itemDef.name = "Blood barrows gloves";
                itemDef.editedModelColor = new int[]{10394, 8656};
                itemDef.newModelColor = new int[]{926, 933};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 13631;
                itemDef.maleEquip1 = 13307;
                itemDef.femaleEquip1 = 13307;
                itemDef.maleEquip2 = 0;
                itemDef.femaleEquip2 = 0;
                itemDef.maleEquip3 = 0;
                itemDef.femaleEquip3 = 0;
                itemDef.modelZoom = 789;
                itemDef.rotationX = 111;
                itemDef.rotationY = 609;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -1;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.value = 0;
                itemDef.team = 0;
                break;

            case 21046:
                itemDef.name = "Gilded barrows gloves";
                itemDef.editedModelColor = new int[]{10394, 8656};
                itemDef.newModelColor = new int[]{7114, 7121};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 13631;
                itemDef.maleEquip1 = 13307;
                itemDef.femaleEquip1 = 13307;
                itemDef.maleEquip2 = 0;
                itemDef.femaleEquip2 = 0;
                itemDef.maleEquip3 = 0;
                itemDef.femaleEquip3 = 0;
                itemDef.modelZoom = 789;
                itemDef.rotationX = 111;
                itemDef.rotationY = 609;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -1;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.value = 0;
                itemDef.team = 0;
                break;

            case 21047:
                itemDef.name = "Enriched barrows gloves";
                itemDef.editedModelColor = new int[]{10394, 8656};
                itemDef.newModelColor = new int[]{123768, 123774};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 13631;
                itemDef.maleEquip1 = 13307;
                itemDef.femaleEquip1 = 13307;
                itemDef.maleEquip2 = 0;
                itemDef.femaleEquip2 = 0;
                itemDef.maleEquip3 = 0;
                itemDef.femaleEquip3 = 0;
                itemDef.modelZoom = 789;
                itemDef.rotationX = 111;
                itemDef.rotationY = 609;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -1;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.value = 0;
                itemDef.team = 0;
                break;

            case 2057:
                itemDef.name = "Tainted barrows gloves";
                itemDef.editedModelColor = new int[]{10394, 8656};
                itemDef.newModelColor = new int[]{0, 20};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.certTemplateID = -1;
                itemDef.stackable = false;
                itemDef.modelID = 13631;
                itemDef.maleEquip1 = 13307;
                itemDef.femaleEquip1 = 13307;
                itemDef.maleEquip2 = 0;
                itemDef.femaleEquip2 = 0;
                itemDef.maleEquip3 = 0;
                itemDef.femaleEquip3 = 0;
                itemDef.modelZoom = 789;
                itemDef.rotationX = 111;
                itemDef.rotationY = 609;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -1;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.value = 0;
                itemDef.team = 0;
                break;


            case 21048:
                itemDef.name = "Bow of faerdhinen";
                itemDef.actions = new String[]{null, "Wield", null, null, "Drop"};
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.modelZoom = 1570;
                itemDef.rotationX = 100;
                itemDef.rotationY = 192;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 1;
                itemDef.modelOffsetY = 0;
                itemDef.modelID = 92605;
                itemDef.femaleEquip1 = 92602;
                itemDef.maleEquip1 = 92602;
                break;

            case 21049:
                itemDef.name = "Holy ornament kit";
                itemDef.modelID = 92296;
                itemDef.modelOffsetX = -1;
                itemDef.modelOffsetY = 9;
                itemDef.rotationY = 360;
                itemDef.rotationX = 1811;
                itemDef.modelZoom = 1360;
                break;
            case 21050:
                itemDef.name = "Sanguine ornament kit";
                itemDef.modelID = 92289;
                itemDef.modelOffsetX = -2;
                itemDef.modelOffsetY = 5;
                itemDef.rotationY = 399;
                itemDef.rotationX = 429;
                itemDef.modelZoom = 1872;
                break;
            case 21051:
                itemDef.name = "Sanguine dust";
                itemDef.editedModelColor = new int[]{6573, 5660, 6697, 6693, 5908};
                itemDef.newModelColor = new int[]{540, 24, 528, 528, 12};
                itemDef.modelID = 91315;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = -15;
                itemDef.rotationY = 142;
                itemDef.rotationX = 1603;
                itemDef.modelZoom = 1438;
                break;
            case 21052:
                itemDef.name = "Lil' maiden";
                itemDef.modelID = 92288;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = -42;
                itemDef.rotationY = 102;
                itemDef.rotationX = 3;
                itemDef.modelZoom = 3075;
                break;
            case 21053:
                itemDef.name = "Lil' bloat";
                itemDef.modelID = 92287;
                itemDef.modelOffsetX = 5;
                itemDef.modelOffsetY = -28;
                itemDef.rotationY = 12;
                itemDef.rotationX = 1991;
                itemDef.modelZoom = 2819;
                break;
            case 21054:
                itemDef.name = "Lil' nylo";
                itemDef.modelID = 92285;
                itemDef.modelOffsetX = 18;
                itemDef.modelOffsetY = -20;
                itemDef.rotationX = 1949;
                itemDef.rotationY = 0;
                itemDef.modelZoom = 2563;
                break;
            case 21055:
                itemDef.name = "Lil' sot";
                itemDef.modelID = 92286;
                itemDef.modelOffsetX = 1;
                itemDef.modelOffsetY = -51;
                itemDef.rotationY = 66;
                itemDef.rotationX = 1928;
                itemDef.modelZoom = 3331;
                break;
            case 21056:
                itemDef.name = "Lil' xarp";
                itemDef.modelID = 92284;
                itemDef.modelOffsetX = 15;
                itemDef.modelOffsetY = -35;
                itemDef.rotationY = 6;
                itemDef.rotationX = 1961;
                itemDef.modelZoom = 3203;
                break;
            case 21057:
                itemDef.name = "Holy scythe of vitur";
                itemDef.actions = new String[]{null, "Wield", null, null, "Drop"};
                itemDef.modelZoom = 2105;
                itemDef.rotationX = 23;
                itemDef.rotationY = 327;
                itemDef.modelOffsetX = 1;
                itemDef.modelOffsetY = 17;
                itemDef.modelID = 92293;
                itemDef.maleEquip1 = 92277;
                itemDef.femaleEquip1 = 92270;
                break;
            case 21058:
                itemDef.name = "Saguine scythe of vitur";
                itemDef.actions = new String[]{null, "Wield", null, null, "Drop"};
                itemDef.modelZoom = 2105;
                itemDef.rotationX = 23;
                itemDef.rotationY = 327;
                itemDef.modelOffsetX = 1;
                itemDef.modelOffsetY = 17;
                itemDef.modelID = 92295;
                itemDef.maleEquip1 = 92279;
                itemDef.femaleEquip1 = 92272;
                break;
            case 21059:
                itemDef.name = "Holy ghrazi rapier";
                itemDef.actions = new String[]{null, "Wield", null, null, "Drop"};
                itemDef.modelZoom = 2064;
                itemDef.rotationY = 0;
                itemDef.rotationX = 1603;
                itemDef.rotationZ = 552;
                itemDef.modelOffsetX = 5;
                itemDef.modelOffsetY = -18;
                itemDef.modelID = 92294;
                itemDef.maleEquip1 = 92273;
                itemDef.femaleEquip1 = 92278;
                itemDef.editedModelColor = new int[]{90,};
                itemDef.newModelColor = new int[]{-9762};
                break;
            case 21060:
                itemDef.name = "Holy sanguinesti staff";
                itemDef.actions = new String[]{null, "Wield", null, null, "Drop"};
                itemDef.modelZoom = 2258;
                itemDef.rotationY = 552;
                itemDef.rotationX = 1558;
                itemDef.modelOffsetX = -5;
                itemDef.modelOffsetY = 3;
                itemDef.modelID = 92292;
                itemDef.maleEquip1 = 92271;
                itemDef.femaleEquip1 = 92276;
                break;


            case 10662:
                itemDef.name = "Support Cape";
                break;

            case 12150:
                itemDef.name = "Mod Cape";
                itemDef.description = "A cape worn by player Moderators.";
                itemDef.actions = new String[5];
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                itemDef.modelID = 91309;
                itemDef.modelZoom = 2000;
                itemDef.rotationY = 572;
                itemDef.rotationX = 0;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 1;
                itemDef.maleEquip1 = 91310;
                itemDef.femaleEquip1 = 91310;
                break;

            case 12151:
                itemDef.name = "Admin Cape";
                itemDef.description = "A cape worn by player Admins.";
                itemDef.actions = new String[5];
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                itemDef.modelID = 91311;
                itemDef.modelZoom = 2000;
                itemDef.rotationY = 572;
                itemDef.rotationX = 0;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 1;
                itemDef.maleEquip1 = 91312;
                itemDef.femaleEquip1 = 91312;
                break;

            case 12152:
                itemDef.name = "Owner Cape";
                itemDef.description = "A cape worn by the Owner.";
                itemDef.actions = new String[5];
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                itemDef.modelID = 91313;
                itemDef.modelZoom = 2000;
                itemDef.rotationY = 572;
                itemDef.rotationX = 0;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 1;
                itemDef.maleEquip1 = 91314;
                itemDef.femaleEquip1 = 91314;
                break;


            case 20059:
                itemDef.name = "Drygore rapier";
                itemDef.modelZoom = 1145;
                itemDef.rotationX = 2047;
                itemDef.rotationY = 254;
                itemDef.modelOffset1 = -3;
                itemDef.modelOffsetY = -45;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, "Wield", "Check-charges", null, "Drop"};
                itemDef.modelID = 14000;
                itemDef.maleEquip1 = 14001;
                itemDef.femaleEquip1 = 14001;
                break;
            case 20057:
                itemDef.name = "Drygore longsword";
                itemDef.modelZoom = 1645;
                itemDef.rotationX = 377;
                itemDef.rotationY = 444;
                itemDef.modelOffset1 = 3;
                itemDef.modelOffsetY = 0;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, "Wield", "Check-charges", null, "Drop"};
                itemDef.modelID = 14022;
                itemDef.maleEquip1 = 14023;
                itemDef.femaleEquip1 = 14023;
                break;
            case 20058:
                itemDef.name = "Drygore mace";
                itemDef.modelZoom = 1118;
                itemDef.rotationX = 28;
                itemDef.rotationY = 235;
                itemDef.modelOffset1 = -1;
                itemDef.modelOffsetY = -47;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, "Wield", "Check-charges", null, "Drop"};
                itemDef.modelID = 14024;
                itemDef.maleEquip1 = 14025;
                itemDef.femaleEquip1 = 14025;
                break;

            case 19670:
                itemDef.name = "Vote scroll";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Claim";
                itemDef.actions[2] = "Claim-All";
                break;
            case 10034:
            case 10033:
                itemDef.actions = new String[]{null, null, null, null, "Drop"};
                break;
            case 13727:
                itemDef.actions = new String[]{null, null, null, null, "Drop"};
                break;
            case 6500:
                itemDef.modelID = 9123;
                itemDef.name = "Charming imp";
                //	itemDef.modelZoom = 672;
                //	itemDef.rotationY = 85;
                //	itemDef.rotationX = 1867;
                itemDef.actions = new String[]{null, null, "Check", "Config", "Drop"};
                break;
            case 13045:
                itemDef.name = "Abyssal bludgeon";
                itemDef.modelZoom = 2611;
                itemDef.rotationY = 552;
                itemDef.rotationX = 1508;
                itemDef.modelOffsetY = 3;
                itemDef.modelOffset1 = -17;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, "Wield", "Check", "Uncharge", "Drop"};
                itemDef.modelID = 29597;
                itemDef.maleEquip1 = 29424;
                itemDef.femaleEquip1 = 29424;
                break;
            case 13047:
                itemDef.name = "Abyssal dagger";
                itemDef.modelZoom = 1347;
                itemDef.rotationY = 1589;
                itemDef.rotationX = 781;
                itemDef.modelOffsetY = 3;
                itemDef.modelOffset1 = -5;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, "Wield", "Check", "Uncharge", "Drop"};
                itemDef.modelID = 29598;
                itemDef.maleEquip1 = 29425;
                itemDef.femaleEquip1 = 29425;
                break;
            case 12284:
                itemDef.name = "Toxic staff of the dead";
                itemDef.modelID = 19224;
                itemDef.modelZoom = 2150;
                itemDef.rotationX = 1010;
                itemDef.rotationY = 512;
                itemDef.femaleEquip1 = 14402;
                itemDef.maleEquip1 = 49001;
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wield";
                itemDef.actions[2] = "Check";
                itemDef.actions[4] = "Uncharge";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.editedModelColor = new int[1];
                itemDef.editedModelColor[0] = 17467;
                itemDef.newModelColor = new int[1];
                itemDef.newModelColor[0] = 21947;
                break;
            case 12605:
                itemDef.name = "Treasonous ring";
                itemDef.modelZoom = 750;
                itemDef.rotationY = 342;
                itemDef.rotationX = 252;
                itemDef.modelOffset1 = -3;
                itemDef.modelOffsetY = -12;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                itemDef.modelID = 43900;
                break;


            case 6183:
                itemDef.name = "@red@Donation Box";
                break;

            case 13051:
                itemDef.name = "Armadyl crossbow";
                itemDef.modelZoom = 1325;
                itemDef.rotationY = 240;
                itemDef.rotationX = 110;
                itemDef.modelOffset1 = -6;
                itemDef.modelOffsetY = -40;
                itemDef.newModelColor = new int[]{115, 107, 10167, 10171};
                itemDef.editedModelColor = new int[]{5409, 5404, 6449, 7390};
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, "Wield", null, null, "Drop"};
                itemDef.modelID = 19967;
                itemDef.maleEquip1 = 19839;
                itemDef.femaleEquip1 = 19839;
                break;

            case 4454:
                itemDef.name = "Kodai wand";
                itemDef.modelZoom = 1417;
                itemDef.rotationY = 552;
                itemDef.rotationX = 1006;
                itemDef.modelOffsetY = 1;
                itemDef.actions[1] = "Wear";
                itemDef.actions[2] = "Check-charges";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 32789;
                itemDef.maleEquip1 = 32669;
                itemDef.femaleEquip1 = 32669;
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 0;
                itemDef.newModelColor[0] = 4;
                break;
            case 4453:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wield";
                itemDef.modelID = 32797; // Drop/Inv Model
                itemDef.maleEquip1 = 32668; // Male Wield Model
                itemDef.femaleEquip1 = 32668; // Female Wield
                itemDef.modelZoom = 1230;
                itemDef.rotationY = 236;
                itemDef.rotationX = 236;
                itemDef.modelOffset1 = -5;
                itemDef.modelOffsetY = -36;
                itemDef.stackable = false;
                itemDef.name = "Dragon Crossbow"; // Item Name
                itemDef.description = "A crossbow specialising in hunting dragons."; // Item
                break;
            case 4452:
                itemDef.name = "Twisted buckler";
                itemDef.modelZoom = 920;
                itemDef.rotationY = 291;
                itemDef.rotationX = 2031;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = -3;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 32793;
                itemDef.maleEquip1 = 32667;
                itemDef.femaleEquip1 = 32667;
                break;
            case 4448:
                itemDef.name = "Dinh's bulwark";
                itemDef.modelZoom = 1620;
                itemDef.rotationY = 291;
                itemDef.rotationX = 1224;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = -3;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 32801;
                itemDef.maleEquip1 = 32671;
                itemDef.femaleEquip1 = 32671;
                break;
            case 4450:
                itemDef.name = "Elder maul";
                itemDef.modelZoom = 1800;
                itemDef.rotationY = 308;
                itemDef.rotationX = 36;
                itemDef.modelOffset1 = 7;
                itemDef.actions = new String[]{null, "Wield", null, null, "Destroy"};
                itemDef.modelID = 32792;
                itemDef.maleEquip1 = 32678;
                itemDef.femaleEquip1 = 32678;
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 0;
                itemDef.newModelColor[0] = 4;
                break;

            case 12927:
                itemDef.name = "Magma Blowpipe";
                itemDef.modelZoom = 1158;
                itemDef.rotationY = 768;
                itemDef.rotationX = 189;
                itemDef.modelOffset1 = -7;
                itemDef.modelOffsetY = 4;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, "Wield", "Check", "Unload", "Uncharge"};
                itemDef.newModelColor = new int[]{8134, 5058, 926, 957, 3008, 1321, 86, 41, 49, 7110, 3008, 1317};
                itemDef.editedModelColor = new int[]{48045, 49069, 48055, 49083, 50114, 33668, 29656, 29603, 33674, 33690, 33680, 33692};
                itemDef.modelID = 19219;
                itemDef.maleEquip1 = 14403;
                itemDef.femaleEquip1 = 14403;
                break;
            case 12926:
                itemDef.modelID = 19219;
                itemDef.name = "Toxic blowpipe";
                itemDef.description = "It's a Toxic blowpipe";
                itemDef.modelZoom = 1158;
                itemDef.rotationY = 768;
                itemDef.rotationX = 189;
                itemDef.modelOffset1 = -7;
                itemDef.modelOffsetY = 4;
                itemDef.maleEquip1 = 14403;
                itemDef.femaleEquip1 = 14403;
                itemDef.actions = new String[]{null, "Wield", "Check", "Unload", "Drop"};
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                break;
            case 13058:
                itemDef.name = "Trident of the seas";
                itemDef.description = "A weapon from the deep.";
                itemDef.femaleEquip1 = 28230;
                itemDef.maleEquip1 = 28230;
                itemDef.modelID = 28232;
                itemDef.rotationY = 420;
                itemDef.rotationX = 1130;
                itemDef.modelZoom = 2755;
                itemDef.modelOffsetY = 0;
                itemDef.modelOffset1 = 0;
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wield";
                itemDef.actions[3] = "Check";
                break;
            case 12601:
                itemDef.name = "Ring of the gods";
                itemDef.modelZoom = 900;
                itemDef.rotationY = 393;
                itemDef.rotationX = 1589;
                itemDef.modelOffset1 = -9;
                itemDef.modelOffsetY = -12;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                itemDef.modelID = 33009;
                break;
            case 12603:
                itemDef.name = "Tyrannical ring";
                itemDef.modelZoom = 592;
                itemDef.rotationY = 285;
                itemDef.rotationX = 1163;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                itemDef.modelID = 28823;
                break;
            case 4587:
                itemDef.name = "Dragon Scimitar";
                itemDef.modelID = 91308;
                itemDef.rotationX = 1500;
                itemDef.rotationY = 312;
                itemDef.modelZoom = 1440;
                itemDef.modelOffsetY = 6;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
                itemDef.maleEquip1 = 91307;
                itemDef.femaleEquip1 = 91307;
                break;
            case 20555:
                itemDef.name = "Dragon warhammer";
                itemDef.modelID = 4041;
                itemDef.rotationY = 1450;
                itemDef.rotationX = 1900;
                itemDef.modelZoom = 1605;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, "Wield", null, null, "Drop"};
                itemDef.maleEquip1 = 4037;
                itemDef.femaleEquip1 = 4038;
                break;
            case 19111:
                itemDef.name = "Infernal Cape";
                itemDef.modelID = 91288;
                itemDef.rotationX = 0;
                itemDef.rotationY = 0;
                itemDef.modelZoom = 2086;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, "Wield", null, null, "Drop"};
                itemDef.maleEquip1 = 91288;
                itemDef.femaleEquip1 = 91288;
                itemDef.animateInventory = true;
                break;
            case 11613:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wield";
                itemDef.modelID = 13701;
                itemDef.modelZoom = 1560;
                itemDef.rotationY = 344;
                itemDef.rotationX = 1104;
                itemDef.modelOffsetY = -14;
                itemDef.modelOffsetX = 0;
                itemDef.maleEquip1 = 13700;
                itemDef.femaleEquip1 = 13700;
                itemDef.name = "Dragon Kiteshield";
                itemDef.description = "A Dragon Kiteshield!";
                break;
            case 4151:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wield";
                itemDef.name = "Abyssal whip";
                itemDef.description = "Lowest powered whip";
                itemDef.modelID = 5412;//Inv & Ground
                itemDef.modelZoom = 840;
                itemDef.rotationY = 280;
                itemDef.rotationX = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 56;
                itemDef.maleEquip1 = 5409;//Male Wield View
                itemDef.femaleEquip1 = 5409;//Female Wield View
                break;

            case 11615:
                itemDef.name = "Lucario";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 3000;
                itemDef.rotationX = 0;
                itemDef.rotationY = 0;
                itemDef.modelID = 2781;
                itemDef.modelOffset1 = -3;
                itemDef.modelOffsetY = 0;
                break;

            case 7582:
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                break;

            case 9735:
                itemDef.name = "Pet Goat";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                break;
            case 11995:
                itemDef.name = "Pet Chaos elemental";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 6000;
                itemDef.rotationX = 0;
                itemDef.rotationY = 0;
                itemDef.modelID = 11216;
                itemDef.modelOffset1 = -3;
                itemDef.modelOffsetY = 0;
                break;
            case 11996:
                itemDef.name = "Pet King black dragon";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 3800;
                itemDef.rotationX = 0;
                itemDef.rotationY = 0;
                itemDef.modelID = 17414;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 0;
                break;
            case 11997:
                itemDef.name = "Pet General graardor";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 1250;
                itemDef.rotationX = 0;
                itemDef.rotationY = 0;
                itemDef.modelID = 27789;
                itemDef.modelOffset1 = -3;
                itemDef.modelOffsetY = 200;
                break;
            case 11978:
                itemDef.name = "Pet TzTok-Jad";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 10000;
                itemDef.rotationX = 553;
                itemDef.rotationY = 0;
                itemDef.modelID = 34131;
                itemDef.modelOffset1 = -3;
                itemDef.modelOffsetY = -30;
                break;
            case 12001:
                itemDef.name = "Pet Corporeal beast";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 10000;
                itemDef.rotationX = 553;
                itemDef.rotationY = 0;
                itemDef.modelID = 40955;
                itemDef.modelOffset1 = -3;
                itemDef.modelOffsetY = -30;
                break;
            case 12002:
                itemDef.name = "Pet Kree'arra";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 8000;
                itemDef.rotationX = 0;
                itemDef.rotationY = 0;
                itemDef.modelID = 28003;
                itemDef.modelOffset1 = -20;
                itemDef.modelOffsetY = 0;
                break;
            case 12003:
                itemDef.name = "Pet K'ril tsutsaroth";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 8000;
                itemDef.rotationX = 0;
                itemDef.rotationY = 0;
                itemDef.modelID = 27768;
                itemDef.modelOffset1 = 5;
                itemDef.modelOffsetY = 0;
                break;
            case 12004:
                itemDef.name = "Pet Commander zilyana";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 2500;
                itemDef.rotationX = 0;
                itemDef.rotationY = 0;
                itemDef.modelID = 28057;
                itemDef.modelOffset1 = 5;
                itemDef.modelOffsetY = 0;
                break;
            case 12005:
                itemDef.name = "Pet Dagannoth supreme";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 4560;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelID = 9941;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 0;
                break;
            case 12006:
                itemDef.name = "Pet Dagannoth prime";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 4560;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelID = 9941;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 0;
                itemDef.newModelColor = new int[]{5931, 1688, 21530, 21534};
                itemDef.editedModelColor = new int[]{11930, 27144, 16536, 16540};
                break;
            case 11990:
                itemDef.name = "Pet Dagannoth rex";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 4560;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelID = 9941;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 0;
                itemDef.newModelColor = new int[]{7322, 7326, 10403, 2595};
                itemDef.editedModelColor = new int[]{16536, 16540, 27144, 2477};
                break;
            case 11991:
                itemDef.name = "Pet Frost dragon";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 5060;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelID = 56767;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 0;
                break;
            case 11992:
                itemDef.name = "Pet Tormented demon";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 5060;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelID = 44733;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 0;
                break;
            case 11993:
                itemDef.name = "Pet Kalphite queen";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 7060;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelID = 24597;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 0;
                break;
            case 11994:
                itemDef.name = "Pet Slash bash";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 7060;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelID = 46141;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 0;
                break;
            case 11989:
                itemDef.name = "Pet Phoenix";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 7060;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelID = 45412;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 0;
                break;
            case 11987:
                itemDef.name = "Pet Nex";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 5000;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelID = 62717;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 0;
                break;
            case 11986:
                itemDef.name = "Pet Jungle strykewyrm";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 7060;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelID = 51852;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 0;
                break;
            case 11985:
                itemDef.name = "Pet Desert strykewyrm";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 7060;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelID = 51848;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 0;
                break;
            case 11984:
                itemDef.name = "Pet Ice strykewyrm";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 7060;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelID = 51847;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 0;
                break;
            case 11983:
                itemDef.name = "Pet Green dragon";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 5060;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelID = 49142;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 0;
                break;
            case 11982:
                itemDef.name = "Pet Baby blue dragon";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 3060;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelID = 57937;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 0;
                break;
            case 11981:
                itemDef.name = "Pet Blue dragon";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 5060;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelID = 49137;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 0;
                break;
            case 11979:
                itemDef.name = "Pet Black dragon";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 5060;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelID = 9294;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 0;
                break;
            case 20079:
                itemDef.name = "Pet Vet'ion";
                itemDef.modelID = 28300;
                itemDef.modelZoom = 8060;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelZoom = 8060;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                break;
            case 20080:
                itemDef.name = "Pet Cerberus";
                itemDef.modelID = 29270;
                itemDef.modelZoom = 8060;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelZoom = 8060;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                break;
            case 20081:
                itemDef.name = "Pet Scorpia";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 3347;
                itemDef.rotationX = 189;
                itemDef.rotationY = 121;
                itemDef.modelID = 28293;
                itemDef.modelOffset1 = 12;
                itemDef.modelOffsetY = -10;
                break;
            case 20082:
                itemDef.name = "Pet Skotizo";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 13000;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelID = 31653;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 0;
                break;
            case 20083:
                itemDef.name = "Pet Venenatis";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 4080;
                itemDef.rotationX = 67;
                itemDef.rotationY = 67;
                itemDef.modelID = 28294;
                itemDef.modelOffset1 = 9;
                itemDef.modelOffsetY = -4;
                break;
            case 20085:
                itemDef.name = "Pet Callisto";
                itemDef.modelID = 28298;
                itemDef.modelZoom = 8060;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelZoom = 8060;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                break;
            case 20086:
                itemDef.name = "Pet Toxic Snakeling";
                itemDef.modelID = 14408;
                itemDef.modelZoom = 8060;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelZoom = 8060;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                break;
            case 14914:
                itemDef.name = "Pet Magma Snakeling";
                itemDef.modelID = 14409;
                itemDef.modelZoom = 8060;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelZoom = 8060;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                break;
            case 20103:
                itemDef.name = "Pet Kraken";
                itemDef.modelID = 28231;
                itemDef.modelZoom = 8060;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelZoom = 8060;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                break;
            case 20104:
                itemDef.name = "Pet Ahrim";
                itemDef.modelID = 6668;
                itemDef.modelZoom = 2500;
                itemDef.rotationX = 0;
                itemDef.rotationY = 0;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                break;
            case 20105:
                itemDef.name = "Pet Dharok";
                itemDef.modelID = 6652;
                itemDef.modelZoom = 2500;
                itemDef.rotationX = 0;
                itemDef.rotationY = 0;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                break;
            case 20106:
                itemDef.name = "Pet Guthan";
                itemDef.modelID = 6654;
                itemDef.modelZoom = 2500;
                itemDef.rotationX = 0;
                itemDef.rotationY = 0;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                break;
            case 20107:
                itemDef.name = "Pet Karil";
                itemDef.modelID = 6675;
                itemDef.modelZoom = 2500;
                itemDef.rotationX = 0;
                itemDef.rotationY = 0;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                break;
            case 20108:
                itemDef.name = "Pet Torag";
                itemDef.modelID = 6657;
                itemDef.modelZoom = 2500;
                itemDef.rotationX = 0;
                itemDef.rotationY = 0;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                break;
            case 20109:
                itemDef.name = "Pet Verac";
                itemDef.modelID = 6678;
                itemDef.modelZoom = 2500;
                itemDef.rotationX = 0;
                itemDef.rotationY = 0;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                break;
            case 14916:
                itemDef.name = "Pet Snakeling";
                itemDef.modelID = 14407;
                itemDef.modelZoom = 8060;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelZoom = 8060;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                break;
            case 20087:
                itemDef.name = "Pet Lizardman Shaman";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 8060;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelID = 4039;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 0;
                break;
            case 20088:
                itemDef.name = "Pet WildyWyrm";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 6060;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelID = 63604;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 0;
                break;
            case 20089:
                itemDef.name = "Pet smoke devil";
                itemDef.modelID = 28442;
                itemDef.modelZoom = 3984;
                itemDef.rotationY = 9;
                itemDef.rotationX = 1862;
                itemDef.modelOffsetY = 20;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                break;
            case 20090:
                itemDef.name = "Pet Abyssal Sire";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 12060;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelID = 29477;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 0;
                break;
            case 11971:
                itemDef.name = "Pet Bork";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 6560;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelID = 40590;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 0;
                break;
            case 11972:
                itemDef.name = "Pet Barrelchest";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 5560;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelID = 22790;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 0;
                break;
            case 20091:
                itemDef.name = "Pet Mithril Dragon";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 24575;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelID = 22790;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 0;
                break;
            case 14667:
                itemDef.name = "Zombie fragment";
                itemDef.modelID = ItemDef.forID(14639).modelID;
                break;
            case 15182:
                itemDef.actions[0] = "Bury";
                break;

            case 2996:
                itemDef.name = "Agility ticket";
                break;
            case 11998:
                itemDef.name = "Scimitar";
                itemDef.actions = new String[]{null, null, null, null, null, null};
                break;
            case 11999:
                itemDef.name = "Scimitar";
                itemDef.actions = new String[]{null, null, null, null, null, null};
                itemDef.modelZoom = 700;
                itemDef.rotationX = 0;
                itemDef.rotationY = 350;
                itemDef.modelID = 2429;
                itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
                itemDef.stackable = true;
                itemDef.certID = 11998;
                itemDef.certTemplateID = 799;
                break;
            case 10025:
                itemDef.name = "Charm Box";
                itemDef.actions = new String[]{"Open", null, null, null, null};
                break;
            case 1389:
                itemDef.name = "Staff";
                itemDef.actions = new String[]{null, null, null, null, null, null};
                break;
            case 1390:
                itemDef.name = "Staff";
                itemDef.actions = new String[]{null, null, null, null, null, null};
                break;
            case 17401:
                itemDef.name = "Damaged Hammer";
                itemDef.actions = new String[]{null, null, null, null, null, null};
                break;
            case 17402:
                itemDef.name = "Damaged Hammer";
                itemDef.actions = new String[]{null, null, null, null, null, null};
                itemDef.modelZoom = 760;
                itemDef.rotationX = 28;
                itemDef.rotationY = 552;
                itemDef.modelID = 2429;
                itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
                itemDef.stackable = true;
                itemDef.certID = 17401;
                itemDef.certTemplateID = 799;
                break;
            case 15009:
                itemDef.name = "Gold Ring";
                itemDef.actions = new String[]{null, null, null, null, null, null};
                break;
            case 15010:
                itemDef.modelID = 2429;
                itemDef.name = "Gold Ring";
                itemDef.actions = new String[]{null, null, null, null, null, null};
                itemDef.modelZoom = 760;
                itemDef.rotationX = 28;
                itemDef.rotationY = 552;
                itemDef.modelOffsetX = itemDef.modelOffset1 = 0;
                itemDef.stackable = true;
                itemDef.certID = 15009;
                itemDef.certTemplateID = 799;
                break;

            case 11884:
                itemDef.actions = new String[]{"Open", null, null, null, null, null};
                break;

            case 15262:
                itemDef.actions = new String[5];
                itemDef.actions[0] = "Open";
                itemDef.actions[2] = "Open-All";
                break;

            case 4155:
                itemDef.name = "Slayer gem";
                itemDef.actions = new String[]{"Activate", null, "Social-Slayer", null, "Destroy"};
                break;
            case 13663:
                itemDef.name = "Stat reset cert.";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Open";
                break;
            case 13653:
                itemDef.name = "Energy fragment";
                break;
            case 292:
                itemDef.name = "Ingredients book";
                break;
            case 15707:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[0] = "Create Party";
                break;

            //fixing party hat models on players' heads
            case 1038:
            case 1040:
            case 1042:
            case 1044:
            case 1046:
            case 1048:

                itemDef.maleYOffset = -6;
                itemDef.femaleYOffset = -6;
                break;

            case 619:
                itemDef.name = "Scratch-off Ticket";
                itemDef.actions = new String[]{"Scratch", null, "Scratch-all", null, null};
                break;

            case 18349:
                itemDef.name = "Crystal rapier";
                break;

            case 18351:
                itemDef.name = "Crystal longsword";
                break;

            case 18353:
                itemDef.name = "Crystal maul";
                break;

            case 18355:
                itemDef.name = "Crystal staff";
                break;

            case 18357:
                itemDef.name = "Crystal crossbow";
                break;

            case 18359:
                itemDef.name = "Crystal kiteshield";
                break;

            case 14044:
                itemDef.name = "Black Party Hat (2021)";
                itemDef.modelID = 2635;
                itemDef.description = "A rare black party hat";
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 926;
                itemDef.newModelColor[0] = 0;
                itemDef.modelZoom = 440;
                itemDef.rotationX = 1852;
                itemDef.rotationY = 76;
                itemDef.modelOffsetX = 1;
                itemDef.modelOffsetY = 1;
                itemDef.maleYOffset = -6;
                itemDef.femaleYOffset = -6;
                itemDef.maleEquip1 = 187;
                itemDef.femaleEquip1 = 363;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                break;

            case 14045:
                itemDef.name = "Orange Party Hat (2021)";
                itemDef.modelID = 2635;
                itemDef.description = "A rare Orange party hat";
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 926;
                itemDef.newModelColor[0] = 461770;
                itemDef.modelZoom = 440;
                itemDef.rotationX = 1852;
                itemDef.rotationY = 76;
                itemDef.modelOffsetX = 1;
                itemDef.modelOffsetY = 1;
                itemDef.maleYOffset = -6;
                itemDef.femaleYOffset = -6;
                itemDef.maleEquip1 = 187;
                itemDef.femaleEquip1 = 363;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                break;

            case 14046:
                itemDef.name = "Pink Party Hat (2021)";
                itemDef.modelID = 2635;
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 926;
                itemDef.newModelColor[0] = 123770;
                itemDef.modelZoom = 440;
                itemDef.rotationX = 1852;
                itemDef.rotationY = 76;
                itemDef.modelOffsetX = 1;
                itemDef.modelOffsetY = 1;
                itemDef.maleYOffset = -6;
                itemDef.femaleYOffset = -6;
                itemDef.maleEquip1 = 187;
                itemDef.femaleEquip1 = 363;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                break;

            case 14047:
                itemDef.name = "Brown Party Hat (2021)";
                itemDef.modelID = 2635;
                itemDef.description = "A rare brown party hat";
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 926;
                itemDef.newModelColor[0] = 266770;
                itemDef.modelZoom = 440;
                itemDef.rotationX = 1852;
                itemDef.rotationY = 76;
                itemDef.modelOffsetX = 1;
                itemDef.modelOffsetY = 1;
                itemDef.maleYOffset = -6;
                itemDef.femaleYOffset = -6;
                itemDef.maleEquip1 = 187;
                itemDef.femaleEquip1 = 363;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                break;

            case 14048:
                itemDef.name = "Grey Party Hat (2021)";
                itemDef.modelID = 2635;
                itemDef.description = "A rare grey party hat";
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 926;
                itemDef.newModelColor[0] = 50;
                itemDef.modelZoom = 440;
                itemDef.rotationX = 1852;
                itemDef.rotationY = 76;
                itemDef.modelOffsetX = 1;
                itemDef.modelOffsetY = 1;
                itemDef.maleYOffset = -6;
                itemDef.femaleYOffset = -6;
                itemDef.maleEquip1 = 187;
                itemDef.femaleEquip1 = 363;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                break;

            case 14049:
                itemDef.name = "Lava Party Hat (2021)";
                itemDef.modelID = 2635;
                itemDef.description = "A rare teal party hat";
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 926;
                itemDef.newModelColor[0] = 6073;
                itemDef.modelZoom = 440;
                itemDef.rotationX = 1852;
                itemDef.rotationY = 76;
                itemDef.modelOffsetX = 1;
                itemDef.modelOffsetY = 1;
                itemDef.maleYOffset = -6;
                itemDef.femaleYOffset = -6;
                itemDef.maleEquip1 = 187;
                itemDef.femaleEquip1 = 363;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                break;

            case 14050:
                itemDef.name = "Black Santa Hat (2021)";
                itemDef.modelID = 2537;
                itemDef.description = "A rare black santa hat!";
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 933;
                itemDef.newModelColor[0] = 0;
                itemDef.modelZoom = 540;
                itemDef.rotationX = 136;
                itemDef.rotationY = 72;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -3;
                itemDef.maleEquip1 = 189;
                itemDef.femaleEquip1 = 366;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                break;

            case 14051:
                itemDef.name = "Orange Santa Hat (2021)";
                itemDef.modelID = 2537;
                itemDef.description = "A rare orange santa hat!";
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 933;
                itemDef.newModelColor[0] = 461770;
                itemDef.modelZoom = 540;
                itemDef.rotationX = 136;
                itemDef.rotationY = 72;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -3;
                itemDef.maleEquip1 = 189;
                itemDef.femaleEquip1 = 366;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                break;

            case 14052:
                itemDef.name = "Pink Santa Hat (2021)";
                itemDef.modelID = 2537;
                itemDef.description = "A rare pink santa hat!";
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 933;
                itemDef.newModelColor[0] = 123770;
                itemDef.modelZoom = 540;
                itemDef.rotationX = 136;
                itemDef.rotationY = 72;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -3;
                itemDef.maleEquip1 = 189;
                itemDef.femaleEquip1 = 366;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                break;

            case 14053:
                itemDef.name = "Brown Santa Hat (2021)";
                itemDef.modelID = 2537;
                itemDef.description = "A rare brown santa hat!";
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 933;
                itemDef.newModelColor[0] = 266770;
                itemDef.modelZoom = 540;
                itemDef.rotationX = 136;
                itemDef.rotationY = 72;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -3;
                itemDef.maleEquip1 = 189;
                itemDef.femaleEquip1 = 366;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                break;

            case 14054:
                itemDef.name = "Grey Santa Hat (2021)";
                itemDef.modelID = 2537;
                itemDef.description = "A rare grey santa hat!";
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 933;
                itemDef.newModelColor[0] = 50;
                itemDef.modelZoom = 540;
                itemDef.rotationX = 136;
                itemDef.rotationY = 72;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -3;
                itemDef.maleEquip1 = 189;
                itemDef.femaleEquip1 = 366;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                break;

            case 14055:
                itemDef.name = "Lava Santa Hat (2021)";
                itemDef.modelID = 2537;
                itemDef.description = "A rare lava santa hat!";
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 933;
                itemDef.newModelColor[0] = 6073;
                itemDef.modelZoom = 540;
                itemDef.rotationX = 136;
                itemDef.rotationY = 72;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -3;
                itemDef.maleEquip1 = 189;
                itemDef.femaleEquip1 = 366;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                break;

            case 2764:
                itemDef.name = "Blue Santa Hat (2021)";
                itemDef.modelID = 2537;
                itemDef.description = "A rare blue santa hat!";
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 933;
                itemDef.newModelColor[0] = -21588;
                itemDef.modelZoom = 540;
                itemDef.rotationX = 136;
                itemDef.rotationY = 72;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -3;
                itemDef.maleEquip1 = 189;
                itemDef.femaleEquip1 = 366;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                break;


            case 3619:
                itemDef.name = "Pet Santa";
                itemDef.modelID = 2537;
                itemDef.description = "Have you been Naughty or Nice?";
                itemDef.modelZoom = 600;
                itemDef.rotationX = 160;
                itemDef.rotationY = 90;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -3;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                break;


            case 14065:
                itemDef.name = "Gilded Party Hat";
                itemDef.modelID = 2635;
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 926;
                itemDef.newModelColor[0] = 7114;
                itemDef.modelZoom = 440;
                itemDef.rotationX = 1852;
                itemDef.rotationY = 76;
                itemDef.modelOffsetX = 1;
                itemDef.modelOffsetY = 1;
                itemDef.maleYOffset = -6;
                itemDef.femaleYOffset = -6;
                itemDef.maleEquip1 = 187;
                itemDef.femaleEquip1 = 363;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                break;

            case 14063:
                itemDef.name = "Gilded santa Hat";
                itemDef.modelID = 2537;
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 933;
                itemDef.newModelColor[0] = 7114;
                itemDef.modelZoom = 540;
                itemDef.rotationX = 0;
                itemDef.rotationY = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -3;
                itemDef.maleEquip1 = 189;
                itemDef.femaleEquip1 = 366;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                break;

            case 14064:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 926;
                itemDef.newModelColor[0] = 7114;
                itemDef.modelID = 2438;
                itemDef.modelZoom = 730;
                itemDef.rotationY = 0;
                itemDef.rotationX = 0;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = -10;
                itemDef.maleEquip1 = 3188;
                itemDef.femaleEquip1 = 3192;
                itemDef.name = "Gilded h'ween Mask";
                break;

            case 11289:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 926;
                itemDef.newModelColor[0] = 461770;
                itemDef.modelID = 2438;
                itemDef.modelZoom = 730;
                itemDef.rotationY = 516;
                itemDef.rotationX = 0;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = -10;
                itemDef.maleEquip1 = 3188;
                itemDef.femaleEquip1 = 3192;
                itemDef.name = "Orange h'ween Mask (2021)";
                itemDef.description = "Aaaarrrghhh... I'm a monster.";
                break;


            case 11290:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 926;
                itemDef.newModelColor[0] = 123770;
                itemDef.modelID = 2438;
                itemDef.modelZoom = 730;
                itemDef.rotationY = 516;
                itemDef.rotationX = 0;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = -10;
                itemDef.maleEquip1 = 3188;
                itemDef.femaleEquip1 = 3192;
                itemDef.name = "Pink h'ween Mask (2021)";
                itemDef.description = "Aaaarrrghhh... I'm a monster.";
                break;


            case 11291:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 926;
                itemDef.newModelColor[0] = 266770;
                itemDef.modelID = 2438;
                itemDef.modelZoom = 730;
                itemDef.rotationY = 516;
                itemDef.rotationX = 0;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = -10;
                itemDef.maleEquip1 = 3188;
                itemDef.femaleEquip1 = 3192;
                itemDef.name = "Brown h'ween Mask (2021)";
                itemDef.description = "Aaaarrrghhh... I'm a monster.";
                break;


            case 11292:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 926;
                itemDef.newModelColor[0] = 50;
                itemDef.modelID = 2438;
                itemDef.modelZoom = 730;
                itemDef.rotationY = 516;
                itemDef.rotationX = 0;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = -10;
                itemDef.maleEquip1 = 3188;
                itemDef.femaleEquip1 = 3192;
                itemDef.name = "Grey h'ween Mask (2021)";
                itemDef.description = "Aaaarrrghhh... I'm a monster.";
                break;


            case 11293:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 926;
                itemDef.newModelColor[0] = 6073;
                itemDef.modelID = 2438;
                itemDef.modelZoom = 730;
                itemDef.rotationY = 516;
                itemDef.rotationX = 0;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = -10;
                itemDef.maleEquip1 = 3188;
                itemDef.femaleEquip1 = 3192;
                itemDef.name = "Lava h'ween Mask (2021)";
                itemDef.description = "Aaaarrrghhh... I'm a monster.";
                break;


            case 2689:
                itemDef.name = "Ranger Boots (black)";
                itemDef.modelID = 91228;
                itemDef.description = "Some nice ranger boots!";
                itemDef.editedModelColor = new int[2];
                itemDef.newModelColor = new int[2];
                itemDef.editedModelColor[0] = 15252;
                itemDef.editedModelColor[1] = 17294;
                itemDef.newModelColor[0] = 0;
                itemDef.newModelColor[1] = 10;
                itemDef.modelZoom = 770;
                itemDef.rotationX = 152;
                itemDef.rotationY = 160;
                itemDef.modelOffsetX = 1;
                itemDef.modelOffsetY = -6;
                itemDef.maleEquip1 = 91229;
                itemDef.femaleEquip1 = 91229;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 2690:
                itemDef.name = "Ranger Boots (red)";
                itemDef.modelID = 91228;
                itemDef.description = "Some nice ranger boots!";
                itemDef.editedModelColor = new int[2];
                itemDef.newModelColor = new int[2];
                itemDef.editedModelColor[0] = 15252;
                itemDef.editedModelColor[1] = 17294;
                itemDef.newModelColor[0] = 933;
                itemDef.newModelColor[1] = 929;
                itemDef.modelZoom = 770;
                itemDef.rotationX = 152;
                itemDef.rotationY = 160;
                itemDef.modelOffsetX = 1;
                itemDef.modelOffsetY = -6;
                itemDef.maleEquip1 = 91229;
                itemDef.femaleEquip1 = 91229;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 2691:
                itemDef.name = "Ranger Boots (pink)";
                itemDef.modelID = 91228;
                itemDef.description = "Some nice ranger boots!";
                itemDef.editedModelColor = new int[2];
                itemDef.newModelColor = new int[2];
                itemDef.editedModelColor[0] = 15252;
                itemDef.editedModelColor[1] = 17294;
                itemDef.newModelColor[0] = 123770;
                itemDef.newModelColor[1] = 123775;
                itemDef.modelZoom = 770;
                itemDef.rotationX = 152;
                itemDef.rotationY = 160;
                itemDef.modelOffsetX = 1;
                itemDef.modelOffsetY = -6;
                itemDef.maleEquip1 = 91229;
                itemDef.femaleEquip1 = 91229;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 2692:
                itemDef.name = "Ranger Boots (white)";
                itemDef.modelID = 91228;
                itemDef.description = "Some nice ranger boots!";
                itemDef.editedModelColor = new int[2];
                itemDef.newModelColor = new int[2];
                itemDef.editedModelColor[0] = 15252;
                itemDef.editedModelColor[1] = 17294;
                itemDef.newModelColor[0] = 127;
                itemDef.newModelColor[1] = 115;
                itemDef.modelZoom = 770;
                itemDef.rotationX = 152;
                itemDef.rotationY = 160;
                itemDef.modelOffsetX = 1;
                itemDef.modelOffsetY = -6;
                itemDef.maleEquip1 = 91229;
                itemDef.femaleEquip1 = 91229;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 2693:
                itemDef.name = "Ranger Boots (blue)";
                itemDef.modelID = 91228;
                itemDef.description = "Some nice ranger boots!";
                itemDef.editedModelColor = new int[2];
                itemDef.newModelColor = new int[2];
                itemDef.editedModelColor[0] = 15252;
                itemDef.editedModelColor[1] = 17294;
                itemDef.newModelColor[0] = -21568;
                itemDef.newModelColor[1] = -21588;
                itemDef.modelZoom = 770;
                itemDef.rotationX = 152;
                itemDef.rotationY = 160;
                itemDef.modelOffsetX = 1;
                itemDef.modelOffsetY = -6;
                itemDef.maleEquip1 = 91229;
                itemDef.femaleEquip1 = 91229;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 2694:
                itemDef.name = "Ranger Boots (purple)";
                itemDef.modelID = 91228;
                itemDef.description = "Some nice ranger boots!";
                itemDef.editedModelColor = new int[2];
                itemDef.newModelColor = new int[2];
                itemDef.editedModelColor[0] = 15252;
                itemDef.editedModelColor[1] = 17294;
                itemDef.newModelColor[0] = -14400;
                itemDef.newModelColor[1] = -14420;
                itemDef.modelZoom = 770;
                itemDef.rotationX = 152;
                itemDef.rotationY = 160;
                itemDef.modelOffsetX = 1;
                itemDef.modelOffsetY = -6;
                itemDef.maleEquip1 = 91229;
                itemDef.femaleEquip1 = 91229;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 2695:
                itemDef.name = "Ranger Boots (yellow)";
                itemDef.modelID = 91228;
                itemDef.description = "Some nice ranger boots!";
                itemDef.editedModelColor = new int[2];
                itemDef.newModelColor = new int[2];
                itemDef.editedModelColor[0] = 15252;
                itemDef.editedModelColor[1] = 17294;
                itemDef.newModelColor[0] = 11200;
                itemDef.newModelColor[1] = 11190;
                itemDef.modelZoom = 770;
                itemDef.rotationX = 152;
                itemDef.rotationY = 160;
                itemDef.modelOffsetX = 1;
                itemDef.modelOffsetY = -6;
                itemDef.maleEquip1 = 91229;
                itemDef.femaleEquip1 = 91229;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 2696:
                itemDef.name = "White dye";
                itemDef.modelID = 2705;
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 61;
                itemDef.newModelColor[0] = 127;
                itemDef.modelZoom = 760;
                itemDef.rotationX = 124;
                itemDef.rotationY = 1920;
                itemDef.modelOffsetY = -2;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                break;

            case 2697:
                itemDef.name = "Black dye";
                itemDef.modelID = 2705;
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 61;
                itemDef.newModelColor[0] = 0;
                itemDef.modelZoom = 760;
                itemDef.rotationX = 124;
                itemDef.rotationY = 1920;
                itemDef.modelOffsetY = -2;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                break;

            case 2698:
                itemDef.name = "Ranger Boots (green)";
                itemDef.modelID = 91228;
                itemDef.description = "Some nice ranger boots!";
                itemDef.editedModelColor = new int[2];
                itemDef.newModelColor = new int[2];
                itemDef.editedModelColor[0] = 15252;
                itemDef.editedModelColor[1] = 17294;
                itemDef.newModelColor[0] = 22464;
                itemDef.newModelColor[1] = 22444;
                itemDef.modelZoom = 770;
                itemDef.rotationX = 152;
                itemDef.rotationY = 160;
                itemDef.modelOffsetX = 1;
                itemDef.modelOffsetY = -6;
                itemDef.maleEquip1 = 91229;
                itemDef.femaleEquip1 = 91229;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 2699:
                itemDef.name = "Blue scarf";
                itemDef.modelID = 17125;
                itemDef.description = "A cozy scarf";
                itemDef.editedModelColor = new int[4];
                itemDef.newModelColor = new int[4];
                itemDef.editedModelColor[0] = 119;
                itemDef.editedModelColor[1] = 103;
                itemDef.editedModelColor[2] = 127;
                itemDef.editedModelColor[3] = 111;
                itemDef.newModelColor[0] = -21568;
                itemDef.newModelColor[1] = -21588;
                itemDef.newModelColor[2] = -21568;
                itemDef.newModelColor[3] = -21588;
                itemDef.modelZoom = 919;
                itemDef.rotationX = 595;
                itemDef.rotationY = 595;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 2;
                itemDef.maleEquip1 = 17155;
                itemDef.femaleEquip1 = 17155;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 2700:
                itemDef.name = "Purple scarf";
                itemDef.modelID = 17125;
                itemDef.description = "A cozy scarf";
                itemDef.editedModelColor = new int[4];
                itemDef.newModelColor = new int[4];
                itemDef.editedModelColor[0] = 119;
                itemDef.editedModelColor[1] = 103;
                itemDef.editedModelColor[2] = 127;
                itemDef.editedModelColor[3] = 111;
                itemDef.newModelColor[0] = -14400;
                itemDef.newModelColor[1] = -14420;
                itemDef.newModelColor[2] = -14400;
                itemDef.newModelColor[3] = -14420;
                itemDef.modelZoom = 919;
                itemDef.rotationX = 595;
                itemDef.rotationY = 595;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 2;
                itemDef.maleEquip1 = 17155;
                itemDef.femaleEquip1 = 17155;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 2701:
                itemDef.name = "White Scarf";
                itemDef.modelID = 17125;
                itemDef.description = "A cozy scarf";
                itemDef.editedModelColor = new int[4];
                itemDef.newModelColor = new int[4];
                itemDef.editedModelColor[0] = 119;
                itemDef.editedModelColor[1] = 103;
                itemDef.editedModelColor[2] = 127;
                itemDef.editedModelColor[3] = 111;
                itemDef.newModelColor[0] = 127;
                itemDef.newModelColor[1] = 129;
                itemDef.newModelColor[2] = 127;
                itemDef.newModelColor[3] = 129;
                itemDef.modelZoom = 919;
                itemDef.rotationX = 595;
                itemDef.rotationY = 595;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 2;
                itemDef.maleEquip1 = 17155;
                itemDef.femaleEquip1 = 17155;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 2702:
                itemDef.name = "Pink scarf";
                itemDef.modelID = 17125;
                itemDef.description = "A cozy scarf";
                itemDef.editedModelColor = new int[4];
                itemDef.newModelColor = new int[4];
                itemDef.editedModelColor[0] = 119;
                itemDef.editedModelColor[1] = 103;
                itemDef.editedModelColor[2] = 127;
                itemDef.editedModelColor[3] = 111;
                itemDef.newModelColor[0] = 123770;
                itemDef.newModelColor[1] = 123775;
                itemDef.newModelColor[2] = 123770;
                itemDef.newModelColor[3] = 123775;
                itemDef.modelZoom = 919;
                itemDef.rotationX = 595;
                itemDef.rotationY = 595;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 2;
                itemDef.maleEquip1 = 17155;
                itemDef.femaleEquip1 = 17155;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 2703:
                itemDef.name = "Red scarf";
                itemDef.modelID = 17125;
                itemDef.description = "A cozy scarf";
                itemDef.editedModelColor = new int[4];
                itemDef.newModelColor = new int[4];
                itemDef.editedModelColor[0] = 119;
                itemDef.editedModelColor[1] = 103;
                itemDef.editedModelColor[2] = 127;
                itemDef.editedModelColor[3] = 111;
                itemDef.newModelColor[0] = 933;
                itemDef.newModelColor[1] = 929;
                itemDef.newModelColor[2] = 933;
                itemDef.newModelColor[3] = 929;
                itemDef.modelZoom = 919;
                itemDef.rotationX = 595;
                itemDef.rotationY = 595;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 2;
                itemDef.maleEquip1 = 17155;
                itemDef.femaleEquip1 = 17155;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 2704:
                itemDef.name = "Black scarf";
                itemDef.modelID = 17125;
                itemDef.description = "A cozy scarf";
                itemDef.editedModelColor = new int[4];
                itemDef.newModelColor = new int[4];
                itemDef.editedModelColor[0] = 119;
                itemDef.editedModelColor[1] = 103;
                itemDef.editedModelColor[2] = 127;
                itemDef.editedModelColor[3] = 111;
                itemDef.newModelColor[0] = 0;
                itemDef.newModelColor[1] = 0;
                itemDef.newModelColor[2] = 0;
                itemDef.newModelColor[3] = 0;
                itemDef.modelZoom = 919;
                itemDef.rotationX = 595;
                itemDef.rotationY = 595;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 2;
                itemDef.maleEquip1 = 17155;
                itemDef.femaleEquip1 = 17155;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 2705:
                itemDef.name = "Yellow scarf";
                itemDef.modelID = 17125;
                itemDef.description = "A cozy scarf";
                itemDef.editedModelColor = new int[4];
                itemDef.newModelColor = new int[4];
                itemDef.editedModelColor[0] = 119;
                itemDef.editedModelColor[1] = 103;
                itemDef.editedModelColor[2] = 127;
                itemDef.editedModelColor[3] = 111;
                itemDef.newModelColor[0] = 11200;
                itemDef.newModelColor[1] = 11190;
                itemDef.newModelColor[2] = 11200;
                itemDef.newModelColor[3] = 11190;
                itemDef.modelZoom = 919;
                itemDef.rotationX = 595;
                itemDef.rotationY = 595;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 2;
                itemDef.maleEquip1 = 17155;
                itemDef.femaleEquip1 = 17155;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 2706:
                itemDef.name = "Green scarf";
                itemDef.modelID = 17125;
                itemDef.description = "A cozy scarf";
                itemDef.editedModelColor = new int[4];
                itemDef.newModelColor = new int[4];
                itemDef.editedModelColor[0] = 119;
                itemDef.editedModelColor[1] = 103;
                itemDef.editedModelColor[2] = 127;
                itemDef.editedModelColor[3] = 111;
                itemDef.newModelColor[0] = 22464;
                itemDef.newModelColor[1] = 22444;
                itemDef.newModelColor[2] = 22464;
                itemDef.newModelColor[3] = 22444;
                itemDef.modelZoom = 919;
                itemDef.rotationX = 595;
                itemDef.rotationY = 595;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 2;
                itemDef.maleEquip1 = 17155;
                itemDef.femaleEquip1 = 17155;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 14056:
                itemDef.name = "Death Cape";
                itemDef.modelID = 91306;
                itemDef.description = "A brutal cape";
                itemDef.modelZoom = 3000;
                itemDef.rotationX = 1080;
                itemDef.rotationY = 72;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                itemDef.maleEquip1 = 91306;
                itemDef.femaleEquip1 = 91306;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                break;


            case 1523:
                itemDef.name = "Tainted lockpick";
                break;

            case 14057:
                itemDef.name = "Heavy Ballista";
                itemDef.modelID = 91317;
                itemDef.description = "This bad boy is HEAVY";
                itemDef.modelZoom = 1284;
                itemDef.rotationX = 189;
                itemDef.rotationY = 148;
                itemDef.modelOffsetX = 8;
                itemDef.modelOffsetY = -18;
                itemDef.maleEquip1 = 91316;
                itemDef.femaleEquip1 = 91316;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 14058:
                itemDef.name = "Imbued Zamorak Cape";
                itemDef.modelID = 84166;
                itemDef.description = "Imbued Zamorak Cape";
                itemDef.modelZoom = 2140;
                itemDef.rotationX = 424;
                itemDef.rotationY = 528;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                itemDef.maleYOffset = -7;
                itemDef.maleEquip1 = 84148;
                itemDef.femaleEquip1 = 84148;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 14059:
                itemDef.name = "Imbued Saradomin Cape";
                itemDef.modelID = 91295;
                itemDef.modelZoom = 2140;
                itemDef.rotationX = 424;
                itemDef.rotationY = 528;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                itemDef.maleYOffset = -7;
                itemDef.maleEquip1 = 91296;
                itemDef.femaleEquip1 = 91296;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;

            case 14060:
                itemDef.name = "Imbued Guthix Cape";
                itemDef.modelID = 91297;
                itemDef.modelZoom = 2140;
                itemDef.rotationX = 424;
                itemDef.rotationY = 528;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                itemDef.maleYOffset = -7;
                itemDef.maleEquip1 = 91298;
                itemDef.femaleEquip1 = 91298;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[4] = "Drop";
                break;


            case 14501:
                itemDef.modelID = 44574;
                itemDef.maleEquip1 = 43693;
                itemDef.femaleEquip1 = 43693;
                break;
            case 13262:

                ItemDef itemDef2 = ItemDef.forID(12458);
                itemDef.modelID = itemDef2.modelID;
                itemDef.modelOffset1 = itemDef2.modelOffset1;
                itemDef.modelOffsetX = itemDef2.modelOffsetX;
                itemDef.modelOffsetY = itemDef2.modelOffsetY;
                itemDef.modelZoom = itemDef2.modelZoom;
                itemDef.name = itemDef2.name;
                itemDef.actions = itemDef2.actions;
                break;
            case 15084:
                itemDef.actions[0] = "Roll";
                itemDef.name = "Dice (up to 100)";
                itemDef2 = ItemDef.forID(15098);
                itemDef.modelID = itemDef2.modelID;
                itemDef.modelOffset1 = itemDef2.modelOffset1;
                itemDef.modelOffsetX = itemDef2.modelOffsetX;
                itemDef.modelOffsetY = itemDef2.modelOffsetY;
                itemDef.modelZoom = itemDef2.modelZoom;
                break;
            case 8851:
                itemDef.name = "Donator Store Coins";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                break;
            case 19864:
                itemDef.name = "Donator Rank Tokens";
                itemDef.actions = new String[]{"Redeem", null, "Redeem-all", null, "Drop"};
                break;
            case 995:
                itemDef.name = "Coins";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.actions[3] = "Add-to-pouch";
                break;
            case 8890:
            case 8891:
            case 8892:
            case 8893:
            case 8894:
            case 8895:
            case 8896:
            case 8897:
            case 8898:
            case 8899:
            case 18201:
                itemDef.name = "PaeCoins";
                itemDef.editedModelColor[0] = 8128;
                itemDef.newModelColor[0] = 926;
                break;
            case 17291:
                itemDef.name = "Blood necklace";
                itemDef.actions = new String[]{null, "Wear", null, null, null, null};
                break;
            case 20084:
                itemDef.name = "Golden Maul";
                break;
            case 6199:
                itemDef.name = "Mystery Box";
                itemDef.actions = new String[5];
                itemDef.actions[0] = "Open";
                break;
            /*case 6200:
                itemDef.name = "Mystery Box";
                itemDef.actions = new String[5];
                itemDef.actions[0] = "Open";
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 0;
                itemDef.newModelColor[0] = 2059;
                break;*/
            case 15501:
                itemDef.name = "Elite Mystery Box";
                itemDef.actions = new String[5];
                itemDef.actions[0] = "Open";
                break;
            case 6568: // To replace Transparent black with opaque black.
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 0;
                itemDef.newModelColor[0] = 2059;
                break;
            case 14479: // To replace Transparent black with opaque black.
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 0;
                itemDef.newModelColor[0] = 2059;
                break;
            case 996:
            case 997:
            case 998:
            case 999:
            case 1000:
            case 1001:
            case 1002:
            case 1003:
            case 1004:
                itemDef.name = "Coins";
                break;

            case 14017:
                itemDef.name = "Brackish blade";
                itemDef.modelZoom = 1488;
                itemDef.rotationY = 276;
                itemDef.rotationX = 1580;
                itemDef.modelOffsetY = 1;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, "Wield", null, null, "Drop"};
                itemDef.modelID = 64593;
                itemDef.maleEquip1 = 64704;
                itemDef.femaleEquip2 = 64704;
                break;

            case 15220:
                itemDef.name = "Berserker ring (i)";
                itemDef.modelZoom = 600;
                itemDef.rotationY = 324;
                itemDef.rotationX = 1916;
                itemDef.modelOffset1 = 3;
                itemDef.modelOffsetY = -15;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 7735; // if it doesn't work try 7735
                itemDef.maleEquip1 = -1;
                // itemDefinition.maleArm = -1;
                itemDef.femaleEquip1 = -1;
                // itemDefinition.femaleArm = -1;
                break;

            case 14019:
                itemDef.modelID = 65262;
                itemDef.name = "Max Cape";
                itemDef.description = "A cape worn by those who've achieved 99 in all skills.";
                itemDef.modelZoom = 1385;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 24;
                itemDef.rotationY = 279;
                itemDef.rotationX = 948;
                itemDef.maleEquip1 = 65300;
                itemDef.femaleEquip1 = 65322;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[2] = "Customize";
                itemDef.editedModelColor = new int[4];
                itemDef.newModelColor = new int[4];
                itemDef.editedModelColor[0] = 65214; //red
                itemDef.editedModelColor[1] = 65200; // darker red
                itemDef.editedModelColor[2] = 65186; //dark red
                itemDef.editedModelColor[3] = 62995; //darker red
                itemDef.newModelColor[0] = 65214;//cape
                itemDef.newModelColor[1] = 65200;//cape
                itemDef.newModelColor[2] = 65186;//outline
                itemDef.newModelColor[3] = 62995;//cape
                break;
            case 14020:
                itemDef.name = "Veteran hood";
                itemDef.description = "A hood worn by Chivalry's veterans.";
                itemDef.modelZoom = 760;
                itemDef.rotationY = 11;
                itemDef.rotationX = 81;
                itemDef.modelOffset1 = 1;
                itemDef.modelOffsetY = -3;
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                itemDef.modelID = 65271;
                itemDef.maleEquip1 = 65289;
                itemDef.femaleEquip1 = 65314;
                break;
            case 14021:
                itemDef.modelID = 65261;
                itemDef.name = "Veteran Cape";
                itemDef.description = "A cape worn by Chivalry's veterans.";
                itemDef.modelZoom = 760;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 24;
                itemDef.rotationY = 279;
                itemDef.rotationX = 948;
                itemDef.maleEquip1 = 65305;
                itemDef.femaleEquip1 = 65318;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                break;
            case 14022:
                itemDef.modelID = 65270;
                itemDef.name = "Completionist Cape";
                itemDef.description = "We'd pat you on the back, but this cape would get in the way.";
                itemDef.modelZoom = 1385;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 24;
                itemDef.rotationY = 279;
                itemDef.rotationX = 948;
                itemDef.maleEquip1 = 65297;
                itemDef.femaleEquip1 = 65297;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                break;
            case 962:
            case 1959:
            case 9666:
            case 11814:
            case 11816:
            case 11818:
            case 11820:
            case 11822:
            case 11824:
            case 11826:
            case 11828:
            case 11830:
            case 11832:
            case 11834:
            case 11836:
            case 11838:
            case 11840:
            case 11842:
            case 11844:
            case 11846:
            case 11848:
            case 11850:
            case 11852:
            case 11854:
            case 11856:
            case 11858:
            case 11860:
            case 11862:
            case 11864:
            case 11866:
            case 11868:
            case 11870:
            case 11874:
            case 11876:
            case 11878:
            case 11882:
            case 11886:
            case 11890:
            case 11894:
            case 11898:
            case 11902:
            case 11904:
            case 11906:
            case 11928:
            case 11930:
            case 11938:
            case 11942:
            case 11944:
            case 11946:
            case 14525:
            case 14527:
            case 14529:
            case 14531:
            case 19588:
            case 19592:
            case 19596:
            case 11908:
            case 11910:
            case 11912:
            case 11914:
            case 11916:
            case 11918:
            case 11920:
            case 11922:
            case 11960:
            case 11962:
            case 11967:
            case 19586:
            case 19584:
            case 19590:
            case 19594:
            case 19598:
                itemDef.actions = new String[5];
                itemDef.actions[0] = "Open";
                break;

            case 20008:
                itemDef.name = "Flashback potion";
                break;

            case 20027:
                itemDef.name = "Aggressor potion";
                break;

            case 2707:
                itemDef.name = "Anguish scroll";
                break;
            case 2708:
                itemDef.name = "Torment scroll";
                break;
            case 2380:
                itemDef.name = "Dragonfire crystal";
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 18522;
                itemDef.newModelColor[0] = 6073;
                break;
            case 2381:
                itemDef.name = "Frost crystal";
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 18522;
                itemDef.newModelColor[0] = 38310;
                break;
            case 2382:
                itemDef.name = "Vampiric crystal";
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 18522;
                itemDef.newModelColor[0] = 127;
                break;
            case 2383:
                itemDef.name = "Venomous crystal";
                break;

            case 15441:
                itemDef.name = "Dragonfire Whip";
                break;
            case 15442:
                itemDef.name = "Frost Whip";
                break;
            case 15443:
                itemDef.name = "Vampiric Whip";
                break;
            case 15444:
                itemDef.name = "Venomous Whip";
                break;

            case 15701:
                itemDef.name = "Dragonfire Bow";
                break;
            case 15702:
                itemDef.name = "Frost Bow";
                break;
            case 15703:
                itemDef.name = "Vampiric Bow";
                break;
            case 15704:
                itemDef.name = "Venomous Bow";
                break;

            case 14004:
                itemDef.name = "Dragonfire Staff";
                itemDef.modelID = 51845;
                itemDef.editedModelColor = new int[11];
                itemDef.newModelColor = new int[11];
                itemDef.editedModelColor[0] = 7860;
                itemDef.newModelColor[0] = 6073;
                itemDef.editedModelColor[1] = 7876;
                itemDef.newModelColor[1] = 6073;
                itemDef.editedModelColor[2] = 7892;
                itemDef.newModelColor[2] = 6073;
                itemDef.editedModelColor[3] = 7884;
                itemDef.newModelColor[3] = 6073;
                itemDef.editedModelColor[4] = 7868;
                itemDef.newModelColor[4] = 6073;
                itemDef.editedModelColor[5] = 7864;
                itemDef.newModelColor[5] = 6073;
                itemDef.editedModelColor[6] = 7880;
                itemDef.newModelColor[6] = 6073;
                itemDef.editedModelColor[7] = 7848;
                itemDef.newModelColor[7] = 6073;
                itemDef.editedModelColor[8] = 7888;
                itemDef.newModelColor[8] = 6073;
                itemDef.editedModelColor[9] = 7872;
                itemDef.newModelColor[9] = 6073;
                itemDef.editedModelColor[10] = 7856;
                itemDef.newModelColor[10] = 6073;
                itemDef.modelZoom = 2256;
                itemDef.rotationX = 456;
                itemDef.rotationY = 513;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffset1 = 0;
                itemDef.maleEquip1 = 51795;
                itemDef.femaleEquip1 = 51795;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                break;

            case 14005:
                itemDef.name = "Frost Staff";
                itemDef.modelID = 51845;
                itemDef.editedModelColor = new int[11];
                itemDef.newModelColor = new int[11];
                itemDef.editedModelColor[0] = 7860;
                itemDef.newModelColor[0] = 38310;
                itemDef.editedModelColor[1] = 7876;
                itemDef.newModelColor[1] = 38310;
                itemDef.editedModelColor[2] = 7892;
                itemDef.newModelColor[2] = 38310;
                itemDef.editedModelColor[3] = 7884;
                itemDef.newModelColor[3] = 38310;
                itemDef.editedModelColor[4] = 7868;
                itemDef.newModelColor[4] = 38310;
                itemDef.editedModelColor[5] = 7864;
                itemDef.newModelColor[5] = 38310;
                itemDef.editedModelColor[6] = 7880;
                itemDef.newModelColor[6] = 38310;
                itemDef.editedModelColor[7] = 7848;
                itemDef.newModelColor[7] = 38310;
                itemDef.editedModelColor[8] = 7888;
                itemDef.newModelColor[8] = 38310;
                itemDef.editedModelColor[9] = 7872;
                itemDef.newModelColor[9] = 38310;
                itemDef.editedModelColor[10] = 7856;
                itemDef.newModelColor[10] = 38310;
                itemDef.modelZoom = 2256;
                itemDef.rotationX = 456;
                itemDef.rotationY = 513;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffset1 = 0;
                itemDef.maleEquip1 = 51795;
                itemDef.femaleEquip1 = 51795;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                break;

            case 14006:
                itemDef.name = "Vampiric Staff";
                itemDef.modelID = 51845;
                itemDef.editedModelColor = new int[11];
                itemDef.newModelColor = new int[11];
                itemDef.editedModelColor[0] = 7860;
                itemDef.newModelColor[0] = 127;
                itemDef.editedModelColor[1] = 7876;
                itemDef.newModelColor[1] = 127;
                itemDef.editedModelColor[2] = 7892;
                itemDef.newModelColor[2] = 127;
                itemDef.editedModelColor[3] = 7884;
                itemDef.newModelColor[3] = 127;
                itemDef.editedModelColor[4] = 7868;
                itemDef.newModelColor[4] = 127;
                itemDef.editedModelColor[5] = 7864;
                itemDef.newModelColor[5] = 127;
                itemDef.editedModelColor[6] = 7880;
                itemDef.newModelColor[6] = 127;
                itemDef.editedModelColor[7] = 7848;
                itemDef.newModelColor[7] = 127;
                itemDef.editedModelColor[8] = 7888;
                itemDef.newModelColor[8] = 127;
                itemDef.editedModelColor[9] = 7872;
                itemDef.newModelColor[9] = 127;
                itemDef.editedModelColor[10] = 7856;
                itemDef.newModelColor[10] = 127;
                itemDef.modelZoom = 2256;
                itemDef.rotationX = 456;
                itemDef.rotationY = 513;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffset1 = 0;
                itemDef.maleEquip1 = 51795;
                itemDef.femaleEquip1 = 51795;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                break;

            case 14007:
                itemDef.name = "Venomous Staff";
                itemDef.modelID = 51845;
                itemDef.editedModelColor = new int[11];
                itemDef.newModelColor = new int[11];
                itemDef.editedModelColor[0] = 7860;
                itemDef.newModelColor[0] = 479770;
                itemDef.editedModelColor[1] = 7876;
                itemDef.newModelColor[1] = 479770;
                itemDef.editedModelColor[2] = 7892;
                itemDef.newModelColor[2] = 479770;
                itemDef.editedModelColor[3] = 7884;
                itemDef.newModelColor[3] = 479770;
                itemDef.editedModelColor[4] = 7868;
                itemDef.newModelColor[4] = 479770;
                itemDef.editedModelColor[5] = 7864;
                itemDef.newModelColor[5] = 479770;
                itemDef.editedModelColor[6] = 7880;
                itemDef.newModelColor[6] = 479770;
                itemDef.editedModelColor[7] = 7848;
                itemDef.newModelColor[7] = 479770;
                itemDef.editedModelColor[8] = 7888;
                itemDef.newModelColor[8] = 479770;
                itemDef.editedModelColor[9] = 7872;
                itemDef.newModelColor[9] = 479770;
                itemDef.editedModelColor[10] = 7856;
                itemDef.newModelColor[10] = 479770;
                itemDef.modelZoom = 2256;
                itemDef.rotationX = 456;
                itemDef.rotationY = 513;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffset1 = 0;
                itemDef.maleEquip1 = 51795;
                itemDef.femaleEquip1 = 51795;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                break;


            case 14003:
                itemDef.name = "Robin hood hat";
                itemDef.modelID = 3021;
                itemDef.editedModelColor = new int[3];
                itemDef.newModelColor = new int[3];
                itemDef.editedModelColor[0] = 15009;
                itemDef.newModelColor[0] = 30847;
                itemDef.editedModelColor[1] = 17294;
                itemDef.newModelColor[1] = 32895;
                itemDef.editedModelColor[2] = 15252;
                itemDef.newModelColor[2] = 30847;
                itemDef.modelZoom = 650;
                itemDef.rotationY = 2044;
                itemDef.rotationX = 256;
                itemDef.modelOffset1 = -3;
                itemDef.modelOffsetY = -5;
                itemDef.maleEquip1 = 3378;
                itemDef.femaleEquip1 = 3382;
                itemDef.maleDialogue = 3378;
                itemDef.femaleDialogue = 3382;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                break;

            case 14001:
                itemDef.name = "Robin hood hat";
                itemDef.modelID = 3021;
                itemDef.editedModelColor = new int[3];
                itemDef.newModelColor = new int[3];
                itemDef.editedModelColor[0] = 15009;
                itemDef.newModelColor[0] = 10015;
                itemDef.editedModelColor[1] = 17294;
                itemDef.newModelColor[1] = 7730;
                itemDef.editedModelColor[2] = 15252;
                itemDef.newModelColor[2] = 7973;
                itemDef.modelZoom = 650;
                itemDef.rotationY = 2044;
                itemDef.rotationX = 256;
                itemDef.modelOffset1 = -3;
                itemDef.modelOffsetY = -5;
                itemDef.maleEquip1 = 3378;
                itemDef.femaleEquip1 = 3382;
                itemDef.maleDialogue = 3378;
                itemDef.femaleDialogue = 3382;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                break;

            case 14002:
                itemDef.name = "Robin hood hat";
                itemDef.modelID = 3021;
                itemDef.editedModelColor = new int[3];
                itemDef.newModelColor = new int[3];
                itemDef.editedModelColor[0] = 15009;
                itemDef.newModelColor[0] = 35489;
                itemDef.editedModelColor[1] = 17294;
                itemDef.newModelColor[1] = 37774;
                itemDef.editedModelColor[2] = 15252;
                itemDef.newModelColor[2] = 35732;
                itemDef.modelZoom = 650;
                itemDef.rotationY = 2044;
                itemDef.rotationX = 256;
                itemDef.modelOffset1 = -3;
                itemDef.modelOffsetY = -5;
                itemDef.maleEquip1 = 3378;
                itemDef.femaleEquip1 = 3382;
                itemDef.maleDialogue = 3378;
                itemDef.femaleDialogue = 3382;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                break;

            case 14000:
                itemDef.name = "Robin hood hat";
                itemDef.modelID = 3021;
                itemDef.editedModelColor = new int[3];
                itemDef.newModelColor = new int[3];
                itemDef.editedModelColor[0] = 15009;
                itemDef.newModelColor[0] = 3745;
                itemDef.editedModelColor[1] = 17294;
                itemDef.newModelColor[1] = 3982;
                itemDef.editedModelColor[2] = 15252;
                itemDef.newModelColor[2] = 3988;
                itemDef.modelZoom = 650;
                itemDef.rotationY = 2044;
                itemDef.rotationX = 256;
                itemDef.modelOffsetX = 1;
                itemDef.modelOffsetY = -5;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.maleEquip1 = 3378;
                itemDef.femaleEquip1 = 3382;
                itemDef.maleDialogue = 3378;
                itemDef.femaleDialogue = 3382;
                break;

            case 20000:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 53835;
                itemDef.name = "Steadfast boots";
                itemDef.modelZoom = 900;
                itemDef.rotationY = 165;
                itemDef.rotationX = 99;
                itemDef.modelOffset1 = 3;
                itemDef.modelOffsetY = -7;
                itemDef.maleEquip1 = 53327;
                itemDef.femaleEquip1 = 53643;
                itemDef.description = "A pair of Steadfast boots.";
                break;

            case 20001:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 53828;
                itemDef.name = "Glaiven boots";
                itemDef.modelZoom = 900;
                itemDef.rotationY = 165;
                itemDef.rotationX = 99;
                itemDef.modelOffset1 = 3;
                itemDef.modelOffsetY = -7;
                itemDef.femaleEquip1 = 53309;
                itemDef.maleEquip1 = 53309;
                itemDef.description = "A pair of Glaiven boots.";
                break;

            case 20002:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.description = "A pair of Ragefire boots.";
                itemDef.modelID = 53897;
                itemDef.name = "Ragefire boots";
                itemDef.modelZoom = 900;
                itemDef.rotationY = 165;
                itemDef.rotationX = 99;
                itemDef.modelOffset1 = 3;
                itemDef.modelOffsetY = -7;
                itemDef.maleEquip1 = 53330;
                itemDef.femaleEquip1 = 53651;
                break;

            case 14018:
                itemDef.modelID = 5324;
                itemDef.name = "Ornate katana";
                itemDef.modelZoom = 2025;
                itemDef.rotationX = 593;
                itemDef.rotationY = 2040;
                itemDef.modelOffset1 = 5;
                itemDef.modelOffsetY = 1;
                itemDef.value = 50000;
                itemDef.membersObject = true;
                itemDef.maleEquip1 = 5324;
                itemDef.femaleEquip1 = 5324;
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wield";
                itemDef.actions[4] = "Destroy";
                break;
            case 14008:
                itemDef.modelID = 62714;
                itemDef.name = "Torva full helm";
                itemDef.description = "Torva full helm";
                itemDef.modelZoom = 672;
                itemDef.rotationY = 85;
                itemDef.rotationX = 1867;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = -3;
                itemDef.maleEquip1 = 62738;
                itemDef.femaleEquip1 = 62754;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[2] = "Check-charges";
                itemDef.actions[4] = "Drop";
                itemDef.maleDialogue = 62729;
                itemDef.femaleDialogue = 62729;
                break;

            case 14009:
                itemDef.modelID = 62699;
                itemDef.name = "Torva platebody";
                itemDef.description = "Torva platebody";
                itemDef.modelZoom = 1506;
                itemDef.rotationY = 473;
                itemDef.rotationX = 2042;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 0;
                itemDef.maleEquip1 = 62746;
                itemDef.femaleEquip1 = 62762;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[2] = "Check-charges";
                itemDef.actions[4] = "Drop";
                break;

            case 14010:
                itemDef.modelID = 62701;
                itemDef.name = "Torva platelegs";
                itemDef.description = "Torva platelegs";
                itemDef.modelZoom = 1740;
                itemDef.rotationY = 474;
                itemDef.rotationX = 2045;
                itemDef.stackable = false;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = -5;
                itemDef.maleEquip1 = 62743;
                itemDef.femaleEquip1 = 62760;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[2] = "Check-charges";
                itemDef.actions[4] = "Drop";
                break;

            case 14011:
                itemDef.modelID = 62693;
                itemDef.name = "Pernix cowl";
                itemDef.description = "Pernix cowl";
                itemDef.modelZoom = 800;
                itemDef.rotationY = 532;
                itemDef.rotationX = 14;
                itemDef.modelOffset1 = -1;
                itemDef.modelOffsetY = 1;
                itemDef.maleEquip1 = 62739;
                itemDef.femaleEquip1 = 62756;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[2] = "Check-charges";
                itemDef.actions[4] = "Drop";
                itemDef.maleDialogue = 62731;
                itemDef.femaleDialogue = 62727;
                itemDef.editedModelColor = new int[2];
                itemDef.newModelColor = new int[2];
                itemDef.editedModelColor[0] = 4550;
                itemDef.newModelColor[0] = 0;
                itemDef.editedModelColor[1] = 4540;
                itemDef.newModelColor[1] = 0;
                break;

            case 14012:
                itemDef.modelID = 62709;
                itemDef.name = "Pernix body";
                itemDef.description = "Pernix body";
                itemDef.modelZoom = 1378;
                itemDef.rotationY = 485;
                itemDef.rotationX = 2042;
                itemDef.modelOffset1 = -1;
                itemDef.modelOffsetY = 7;
                itemDef.maleEquip1 = 62744;
                itemDef.femaleEquip1 = 62765;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[2] = "Check-charges";
                itemDef.actions[4] = "Drop";
                break;

            case 14013:
                itemDef.modelID = 62695;
                itemDef.name = "Pernix chaps";
                itemDef.description = "Pernix chaps";
                itemDef.modelZoom = 1740;
                itemDef.rotationY = 504;
                itemDef.rotationX = 0;
                itemDef.modelOffset1 = 4;
                itemDef.modelOffsetY = 3;
                itemDef.maleEquip1 = 62741;
                itemDef.femaleEquip1 = 62757;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[2] = "Check-charges";
                itemDef.actions[4] = "Drop";
                break;
            case 14014:
                itemDef.modelID = 62710;
                itemDef.name = "Virtus mask";
                itemDef.description = "Virtus mask";
                itemDef.modelZoom = 928;
                itemDef.rotationY = 406;
                itemDef.rotationX = 2041;
                itemDef.modelOffset1 = 1;
                itemDef.modelOffsetY = -5;
                itemDef.maleEquip1 = 62736;
                itemDef.femaleEquip1 = 62755;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[2] = "Check-charges";
                itemDef.actions[4] = "Drop";
                itemDef.maleDialogue = 62728;
                itemDef.femaleDialogue = 62728;
                break;

            case 14015:
                itemDef.modelID = 62704;
                itemDef.name = "Virtus robe top";
                itemDef.description = "Virtus robe top";
                itemDef.modelZoom = 1122;
                itemDef.rotationY = 488;
                itemDef.rotationX = 3;
                itemDef.modelOffset1 = 1;
                itemDef.modelOffsetY = 0;
                itemDef.maleEquip1 = 62748;
                itemDef.femaleEquip1 = 62764;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[2] = "Check-charges";
                itemDef.actions[4] = "Drop";
                break;
            case 14016:
                itemDef.modelID = 62700;
                itemDef.name = "Virtus robe legs";
                itemDef.description = "Virtus robe legs";
                itemDef.modelZoom = 1740;
                itemDef.rotationY = 498;
                itemDef.rotationX = 2045;
                itemDef.modelOffset1 = -1;
                itemDef.modelOffsetY = 4;
                itemDef.maleEquip1 = 62742;
                itemDef.femaleEquip1 = 62758;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                break;
            //Raids 2
            case 21000:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 35751;
                itemDef.name = "Justiciar faceguard";
                itemDef.modelZoom = 777;
                itemDef.rotationY = 22;
                itemDef.rotationX = 1972;
                itemDef.modelOffset1 = 1;
                itemDef.modelOffsetY = -1;
                itemDef.femaleEquip1 = 35361;
                itemDef.maleEquip1 = 35349;
                itemDef.maleYOffset = -2;
                itemDef.femaleYOffset = -2;
                break;
            case 21001:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 35750;
                itemDef.name = "Justiciar chestguard";
                itemDef.modelZoom = 1310;
                itemDef.rotationY = 432;
                itemDef.rotationX = 0;
                itemDef.modelOffset1 = 1;
                itemDef.modelOffsetY = 4;
                itemDef.femaleEquip1 = 35368;
                itemDef.maleEquip1 = 35359;
                break;
            case 21002:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 35752;
                itemDef.name = "Justiciar legguards";
                itemDef.modelZoom = 1720;
                itemDef.rotationY = 468;
                itemDef.rotationX = 0;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 0;
                itemDef.femaleEquip1 = 35367;
                itemDef.maleEquip1 = 35356;
                break;
            case 21003:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wield";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 35739;
                itemDef.name = "Ghrazi rapier";
                itemDef.modelZoom = 2064;
                itemDef.rotationY = 0;
                itemDef.rotationX = 1603;
                itemDef.modelOffset1 = 5;
                itemDef.modelOffsetX = 552;
                itemDef.modelOffsetY = -18;
                itemDef.femaleEquip1 = 35369;
                itemDef.maleEquip1 = 35374;
                break;
            case 21004:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wield";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 35745;
                itemDef.name = "Avernic defender";
                itemDef.modelZoom = 717;
                itemDef.rotationY = 498;
                itemDef.rotationX = 256;
                itemDef.modelOffset1 = 8;
                itemDef.modelOffsetX = 2047;
                itemDef.modelOffsetY = 8;
                itemDef.femaleEquip1 = 35377;
                itemDef.maleEquip1 = 35376;
                break;
            case 21005:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wield";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 35744;
                itemDef.name = "Sanguinesti staff";
                itemDef.modelZoom = 2258;
                itemDef.rotationY = 552;
                itemDef.rotationX = 1558;
                itemDef.modelOffset1 = 1;
                itemDef.modelOffsetY = 7;
                itemDef.femaleEquip1 = 35372;
                itemDef.maleEquip1 = 35372;
                break;
            case 21006:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wield";
                itemDef.actions[2] = "Charges";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 35742;
                itemDef.name = "Scythe of Vitur";
                itemDef.modelZoom = 2105;
                itemDef.rotationY = 327;
                itemDef.rotationX = 23;
                itemDef.modelOffset1 = 1;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 17;
                itemDef.femaleEquip1 = 35371;
                itemDef.maleEquip1 = 35371;
                break;
            case 18338: //gem bag
                itemDef.actions[0] = "Deposit Gems";
                itemDef.actions[1] = null;
                break;

            case 612:
                itemDef.actions = new String[5];
                itemDef.actions[0] = "Teleport";
                itemDef.actions[2] = "Deactivate";
                itemDef.name = "Last Recall (activated)";
                break;
            case 611:
                itemDef.actions[0] = "Activate";
                itemDef.name = "Last Recall";
                break;

            //Rename
            case 4428:
                itemDef.name = "Ancient Scroll";
                break;
            case 6749:
                itemDef.name = "Mystery Pass";
                break;
            case 6758:
                itemDef.name = "Battle Pass";
                break;
            case 6769:
                itemDef.name = "Event Pass";
                break;
            case 18812:
                itemDef.name = "Godwars Teleport";
                break;
            case 18813:
                itemDef.name = "Barrows Teleport";
                break;
            case 18814:
                itemDef.name = "DKS Teleport";
                break;
            case 19977:
                itemDef.name = "Twisted Vine";
                break;
            case 19981:
                itemDef.name = "Whip vine";
                break;
            case 19982:
                itemDef.name = "Kalphite Tentacle";
                itemDef.modelID = 91294;
                itemDef.rotationX = 593;
                itemDef.rotationY = 741;
                itemDef.modelZoom = 900;
                break;
            case 11988:
                itemDef.name = "Pet Glacio";
                itemDef.groundActions = new String[]{null, null, "Take", null, null};
                itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
                itemDef.modelZoom = 6060;
                itemDef.rotationX = 1868;
                itemDef.rotationY = 2042;
                itemDef.modelID = 46058;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = 0;
                break;
            case 6522:
                itemDef.name = "Obsidian ring";
                break;
            case 6523:
                itemDef.name = "Obsidian sword";
                break;
            case 6524:
                itemDef.name = "Obsidian shield";
                break;
            case 6525:
                itemDef.name = "Obsidian knife";
                break;
            case 6526:
                itemDef.name = "Obsidian staff";
                break;
            case 6527:
                itemDef.name = "Obsidian mace";
                break;
            case 6528:
                itemDef.name = "Obsidian maul";
                break;
            case 19675:
                itemDef.actions[0] = null;
                itemDef.actions[1] = "Equip";
                break;
            case 18339:
                itemDef.name = "Dirt bag";
                itemDef.actions[0] = "Fill";
                itemDef.actions[1] = null;
                itemDef.actions[2] = "Check";
                itemDef.actions[3] = null;
                break;

            case 11137:
            case 18782:
                itemDef.actions = new String[5];
                itemDef.actions[0] = "Rub";
                itemDef.actions[1] = null;
                itemDef.actions[2] = "Rub-All";
                itemDef.actions[3] = null;
                break;

            //DMZ customs
            case 11046:
                itemDef.name = "Stronghold rope";
                break;
            case 15276:
                itemDef.name = "Stronghold plank";
                break;
            case 681:
                itemDef.name = "Stronghold talisman";
                break;

            //player custom items
            case 14061:
                itemDef.name = "Heath's Party Hat";
                itemDef.modelID = 2635;
                itemDef.editedModelColor = new int[1];
                itemDef.newModelColor = new int[1];
                itemDef.editedModelColor[0] = 926;
                itemDef.newModelColor[0] = 126770;
                itemDef.modelZoom = 440;
                itemDef.rotationX = 1852;
                itemDef.rotationY = 76;
                itemDef.modelOffsetX = 1;
                itemDef.modelOffsetY = 1;
                itemDef.maleEquip1 = 187;
                itemDef.femaleEquip1 = 363;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                break;

            case 19868:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Equip";
                itemDef.actions[2] = "Fill";
                itemDef.actions[4] = "Drop";
                break;

            case 1617:
            case 1619:
            case 1621:
            case 1623:
            case 1625:
            case 1627:
            case 1629:
            case 1631:
                itemDef.actions = new String[]{null, null, "Cut", null, null, null};
                break;


        }
        return itemDef;
    }

    public static ItemDef customAncestralForID(int i, ItemDef itemDef) {


        switch (i) {
            case 18888:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 32794;
                itemDef.name = "Ancestral hat";
                itemDef.modelZoom = 1236;
                itemDef.rotationY = 118;
                itemDef.rotationX = 10;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = -12;
                itemDef.maleYOffset = -13;
                itemDef.femaleYOffset = -13;
                itemDef.femaleEquip1 = 32663;
                itemDef.maleEquip1 = 32655;
                itemDef.description = "The hat of a powerful sorceress from a bygone era.";
                break;
            case 18889:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 32790;
                itemDef.name = "Ancestral robe top";
                itemDef.modelZoom = 1358;
                itemDef.rotationY = 514;
                itemDef.rotationX = 2041;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = -3;
                itemDef.femaleEquip1 = 32664;
                itemDef.maleEquip1 = 32657;
                itemDef.maleEquip2 = 32658; // male arms
                itemDef.femaleEquip2 = 32665; // female arms
                itemDef.description = "The robe top of a powerful sorceress from a bygone era.";
                break;
            case 18890:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 32787;
                itemDef.name = "Ancestral robe bottom";
                itemDef.modelZoom = 1690;
                itemDef.rotationY = 435;
                itemDef.rotationX = 9;
                itemDef.modelOffset1 = 1;
                itemDef.modelOffsetY = 7;
                itemDef.femaleEquip1 = 32653;
                itemDef.maleEquip1 = 32662;
                itemDef.description = "The robe bottom of a powerful sorceress from a bygone era.";
                break;
            case 18892:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 32794;
                itemDef.name = "Gilded ancestral hat";
                itemDef.editedModelColor = new int[]{6364, 6356, 6348, 6340, 6331, 6323};
                itemDef.newModelColor = new int[]{7114, 7106, 7100, 7092, 7084, 7076};
                itemDef.modelZoom = 1236;
                itemDef.rotationY = 118;
                itemDef.rotationX = 10;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = -12;
                itemDef.maleYOffset = -13;
                itemDef.femaleYOffset = -13;
                itemDef.femaleEquip1 = 32663;
                itemDef.maleEquip1 = 32655;
                itemDef.description = "The hat of a powerful sorceress from a bygone era.";
                break;
            case 18893:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 32790;
                itemDef.name = "Gilded ancestral robe top";
                itemDef.editedModelColor = new int[]{6364, 6356, 6348, 6340, 6331, 6323, 43311, 43305, 43301};
                itemDef.newModelColor = new int[]{7114, 7106, 7100, 7092, 7084, 5268, 7080, 7076, 7072};
                itemDef.modelZoom = 1358;
                itemDef.rotationY = 514;
                itemDef.rotationX = 2041;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = -3;
                itemDef.femaleEquip1 = 32664;
                itemDef.maleEquip1 = 32657;
                itemDef.maleEquip2 = 32658; // male arms
                itemDef.femaleEquip2 = 32665; // female arms
                itemDef.description = "The robe top of a powerful sorceress from a bygone era.";
                break;
            case 18894:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 32787;
                itemDef.name = "Gilded ancestral robe bottom";
                itemDef.editedModelColor = new int[]{6364, 6356, 6348, 6340, 6331, 6323, 43311, 43305, 43301};
                itemDef.newModelColor = new int[]{7114, 7106, 7100, 7092, 7084, 5268, 7080, 7076, 7072};
                itemDef.modelZoom = 1690;
                itemDef.rotationY = 435;
                itemDef.rotationX = 9;
                itemDef.modelOffset1 = 1;
                itemDef.modelOffsetY = 7;
                itemDef.femaleEquip1 = 32653;
                itemDef.maleEquip1 = 32662;
                itemDef.description = "The robe bottom of a powerful sorceress from a bygone era.";
                break;
            case 18899:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 32794;
                itemDef.name = "Blood ancestral hat";
                itemDef.editedModelColor = new int[]{6364, 6356, 6348, 6340, 6331, 6323, 6973, 7108, 43311, 43305, 43301};
                itemDef.newModelColor = new int[]{933, 932, 931, 930, 929, 928, 6073, 6073, 927, 926, 925};
                itemDef.modelZoom = 1236;
                itemDef.rotationY = 118;
                itemDef.rotationX = 10;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = -12;
                itemDef.maleYOffset = -13;
                itemDef.femaleYOffset = -13;
                itemDef.femaleEquip1 = 32663;
                itemDef.maleEquip1 = 32655;
                itemDef.description = "The hat of a powerful sorceress from a bygone era.";
                break;
            case 18900:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 32790;
                itemDef.name = "Blood ancestral robe top";
                itemDef.editedModelColor = new int[]{6364, 6356, 6348, 6340, 6331, 6323, 6973, 7108, 43311, 43305, 43301};
                itemDef.newModelColor = new int[]{933, 932, 931, 930, 20, 0, 0, 0, 927, 926, 925};
                itemDef.modelZoom = 1358;
                itemDef.rotationY = 514;
                itemDef.rotationX = 2041;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = -3;
                itemDef.femaleEquip1 = 32664;
                itemDef.maleEquip1 = 32657;
                itemDef.maleEquip2 = 32658; // male arms
                itemDef.femaleEquip2 = 32665; // female arms
                itemDef.description = "The robe top of a powerful sorceress from a bygone era.";
                break;
            case 18901:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 32787;
                itemDef.name = "Blood ancestral robe bottom";
                itemDef.editedModelColor = new int[]{6364, 6356, 6348, 6340, 6331, 6323, 6973, 7108, 43311, 43305, 43301};
                itemDef.newModelColor = new int[]{933, 932, 931, 930, 20, 0, 0, 0, 927, 926, 925};
                itemDef.modelZoom = 1690;
                itemDef.rotationY = 435;
                itemDef.rotationX = 9;
                itemDef.modelOffset1 = 1;
                itemDef.modelOffsetY = 7;
                itemDef.femaleEquip1 = 32653;
                itemDef.maleEquip1 = 32662;
                itemDef.description = "The robe bottom of a powerful sorceress from a bygone era.";
                break;
            case 18905:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 32794;
                itemDef.name = "Soul ancestral hat";
                itemDef.editedModelColor = new int[]{6364, 6356, 6348, 6340, 6331, 6323, 6973, 7108};
                itemDef.newModelColor = new int[]{51136, 51135, 51134, 51133, 51132, 51131, 123770, 123770};
                itemDef.modelZoom = 1236;
                itemDef.rotationY = 118;
                itemDef.rotationX = 10;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = -12;
                itemDef.maleYOffset = -13;
                itemDef.femaleYOffset = -13;
                itemDef.femaleEquip1 = 32663;
                itemDef.maleEquip1 = 32655;
                itemDef.description = "The hat of a powerful sorceress from a bygone era.";
                break;
            case 18906:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 32790;
                itemDef.name = "Soul ancestral robe top";
                itemDef.editedModelColor = new int[]{6364, 6356, 6348, 6340, 6331, 6323, 6973, 7108, 43311, 43305, 43301};
                itemDef.newModelColor = new int[]{51180, 51172, 51164, 51156, 51148, 51140, 123770, 123770, 50020, 50010, 50000};
                itemDef.modelZoom = 1358;
                itemDef.rotationY = 514;
                itemDef.rotationX = 2041;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = -3;
                itemDef.femaleEquip1 = 32664;
                itemDef.maleEquip1 = 32657;
                itemDef.maleEquip2 = 32658; // male arms
                itemDef.femaleEquip2 = 32665; // female arms
                itemDef.description = "The robe top of a powerful sorceress from a bygone era.";
                break;
            case 18907:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 32787;
                itemDef.name = "Soul ancestral robe bottom";
                itemDef.editedModelColor = new int[]{6364, 6356, 6348, 6340, 6331, 6323, 6973, 7108, 43311, 43305, 43301};
                itemDef.newModelColor = new int[]{51180, 51172, 51164, 51156, 51148, 51140, 123770, 123770, 50020, 50010, 50000};
                itemDef.modelZoom = 1690;
                itemDef.rotationY = 435;
                itemDef.rotationX = 9;
                itemDef.modelOffset1 = 1;
                itemDef.modelOffsetY = 7;
                itemDef.femaleEquip1 = 32653;
                itemDef.maleEquip1 = 32662;
                itemDef.description = "The robe bottom of a powerful sorceress from a bygone era.";
                break;
            case 2765:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 32794;
                itemDef.name = "Enriched ancestral hat";
                itemDef.editedModelColor = new int[]{6364, 6356, 6348, 6340, 6331, 6323, 6973, 7108, 43311, 43305, 43301};
                itemDef.newModelColor = new int[]{123770, 123762, 123754, 123746, 123738, 123730, 129770, 129770, 129780, 129780, 129780};
                itemDef.modelZoom = 1236;
                itemDef.rotationY = 118;
                itemDef.rotationX = 10;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = -12;
                itemDef.maleYOffset = -13;
                itemDef.femaleYOffset = -13;
                itemDef.femaleEquip1 = 32663;
                itemDef.maleEquip1 = 32655;
                itemDef.description = "The hat of a powerful sorceress from a bygone era.";
                break;
            case 2766:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 32790;
                itemDef.name = "Enriched ancestral robe top";
                itemDef.editedModelColor = new int[]{6364, 6356, 6348, 6340, 6331, 6323, 6973, 7108, 43311, 43305, 43301};
                itemDef.newModelColor = new int[]{123770, 123762, 123754, 123746, 123738, 123730, 129770, 129770, 129780, 129780, 129780};
                itemDef.modelZoom = 1358;
                itemDef.rotationY = 514;
                itemDef.rotationX = 2041;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = -3;
                itemDef.femaleEquip1 = 32664;
                itemDef.maleEquip1 = 32657;
                itemDef.maleEquip2 = 32658; // male arms
                itemDef.femaleEquip2 = 32665; // female arms
                itemDef.description = "The robe top of a powerful sorceress from a bygone era.";
                break;
            case 2767:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 32787;
                itemDef.name = "Enriched ancestral robe bottom";
                itemDef.editedModelColor = new int[]{6364, 6356, 6348, 6340, 6331, 6323, 6973, 7108, 43311, 43305, 43301};
                itemDef.newModelColor = new int[]{123770, 123762, 123754, 123746, 123738, 123730, 129770, 129770, 129780, 129780, 129780};
                itemDef.modelZoom = 1690;
                itemDef.rotationY = 435;
                itemDef.rotationX = 9;
                itemDef.modelOffset1 = 1;
                itemDef.modelOffsetY = 7;
                itemDef.femaleEquip1 = 32653;
                itemDef.maleEquip1 = 32662;
                itemDef.description = "The robe bottom of a powerful sorceress from a bygone era.";
                break;
            case 2051:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 32794;
                itemDef.name = "Tainted ancestral hat";
                itemDef.editedModelColor = new int[]{6364, 6356, 6348, 6340, 6331, 6323, 6973, 7108, 43311, 43305, 43301};
                itemDef.newModelColor = new int[]{0, 60, 50, 40, 30, 20, 0, 0, 10, 10, 10};
                itemDef.modelZoom = 1236;
                itemDef.rotationY = 118;
                itemDef.rotationX = 10;
                itemDef.stackable = false;
                itemDef.certTemplateID = -1;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = -12;
                itemDef.maleYOffset = -13;
                itemDef.femaleYOffset = -13;
                itemDef.femaleEquip1 = 32663;
                itemDef.maleEquip1 = 32655;
                itemDef.description = "The hat of a powerful sorceress from a bygone era.";
                break;
            case 2052:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 32790;
                itemDef.name = "Tainted ancestral robe top";
                itemDef.editedModelColor = new int[]{6364, 6356, 6348, 6340, 6331, 6323, 6973, 7108, 43311, 43305, 43301};
                itemDef.newModelColor = new int[]{0, 60, 50, 40, 30, 20, 0, 0, 10, 10, 10};
                itemDef.modelZoom = 1358;
                itemDef.rotationY = 514;
                itemDef.rotationX = 2041;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffsetY = -3;
                itemDef.femaleEquip1 = 32664;
                itemDef.maleEquip1 = 32657;
                itemDef.maleEquip2 = 32658; // male arms
                itemDef.femaleEquip2 = 32665; // female arms
                itemDef.description = "The robe top of a powerful sorceress from a bygone era.";
                break;
            case 2053:
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 32787;
                itemDef.name = "Tainted ancestral robe bottom";
                itemDef.editedModelColor = new int[]{6364, 6356, 6348, 6340, 6331, 6323, 6973, 7108, 43311, 43305, 43301};
                itemDef.newModelColor = new int[]{0, 60, 50, 40, 30, 20, 0, 0, 10, 10, 10};
                itemDef.modelZoom = 1690;
                itemDef.rotationY = 435;
                itemDef.rotationX = 9;
                itemDef.stackable = false;
                itemDef.certTemplateID = -1;
                itemDef.modelOffset1 = 1;
                itemDef.modelOffsetY = 7;
                itemDef.femaleEquip1 = 32653;
                itemDef.maleEquip1 = 32662;
                itemDef.description = "The robe bottom of a powerful sorceress from a bygone era.";
                break;
        }
        return itemDef;
    }

    public static ItemDef customSHRuniques(int i, ItemDef itemDef) {

        switch (i) {


            case 21061:
                itemDef.name = "Tumeken's shadow";
                itemDef.femaleEquip1 = 95282;
                itemDef.actions = new String[]{null, "Wield", "Check", "Charge", "Uncharge"};
                itemDef.modelID = 96465;
                itemDef.maleEquip1 = 95281;
                itemDef.rotationX = 465;
                itemDef.modelOffsetX = 0;
                itemDef.rotationY = 1481;
                itemDef.modelOffsetY = 16;
                itemDef.rotationZ = 290;
                itemDef.modelZoom = 1960;
                break;

            case 21062:
                itemDef.name = "Tumeken's shadow (uncharged)";
                itemDef.femaleEquip1 = 95282;
                itemDef.actions = new String[]{null, "Wield", "Charge", null, "Drop"};
                itemDef.modelID = 96464;
                itemDef.maleEquip1 = 95281;
                itemDef.rotationX = 465;
                itemDef.modelOffsetX = 0;
                itemDef.rotationY = 1481;
                itemDef.modelOffsetY = 16;
                itemDef.rotationZ = 290;
                itemDef.modelZoom = 1960;
                break;

            case 21063:
                itemDef.name = "Osmumten's fang";
                itemDef.femaleEquip1 = 95285;
                itemDef.actions = new String[]{null, "Wield", null, null, "Drop"};
                itemDef.modelID = 96468;
                itemDef.maleEquip1 = 95278;
                itemDef.modelOffsetX = 5;
                itemDef.rotationX = 100;
                itemDef.rotationY = 1603;
                itemDef.rotationZ = 0;
                itemDef.modelZoom = 2150;
                break;

            case 21064:
                itemDef.name = "Osmumten's fang (or)";
                itemDef.femaleEquip1 = 95273;
                itemDef.actions = new String[]{null, "Wield", "Dismantle", null, "Drop"};
                itemDef.modelID = 96474;
                itemDef.maleEquip1 = 95279;
                itemDef.modelOffsetX = 3;
                itemDef.rotationX = 100;
                itemDef.rotationY = 1603;
                itemDef.rotationZ = 0;
                itemDef.modelZoom = 2400;
                break;

            case 21065:
                itemDef.name = "Cursed phalanx";
                itemDef.actions = new String[]{"Inspect", "Attach", null, null, "Drop"};
                itemDef.modelID = 96483;
                itemDef.modelZoom = 1050;
                itemDef.rotationX = 26;
                itemDef.rotationY = 548;
                itemDef.rotationZ = 1991;
                itemDef.modelOffsetX = 8;
                itemDef.modelOffsetY = -5;
                break;


            case 21066:
                itemDef.name = "Masori assembler max cape";
                itemDef.femaleEquip1 = 96501;
                itemDef.actions = new String[]{null, "Wear", null, "Commune", "Drop"};
                itemDef.modelID = 96521;
                itemDef.maleEquip1 = 96501;
                itemDef.modelZoom = 2500;
                itemDef.rotationX = 0;
                itemDef.rotationY = 530;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -2;
                break;

            case 21067:
                itemDef.name = "Masori assembler max hood";
                itemDef.femaleEquip1 = 94455;
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                itemDef.modelID = 96522;
                itemDef.maleEquip1 = 94455;
                itemDef.rotationX = 21;
                itemDef.rotationY = 18;
                itemDef.modelZoom = 720;
                break;

            case 21068:
                itemDef.name = "Masori crafting kit";
                itemDef.actions = new String[]{"Inspect", null, null, null, "Drop"};
                itemDef.modelID = 96527;
                itemDef.modelZoom = 1500;
                itemDef.rotationX = 148;
                itemDef.rotationY = 320;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 12;
                break;

            case 21069:
                itemDef.name = "Masori assembler";
                itemDef.editedModelColor = new int[]{-27483, -27492, -27500};
                itemDef.newModelColor = new int[]{-32746, -32626, -32630};
                itemDef.femaleEquip1 = 96502;
                itemDef.actions = new String[]{null, "Wear", "Dismantle", "Commune", "Drop"};
                itemDef.modelID = 96525;
                itemDef.maleEquip1 = 96502;
                itemDef.modelZoom = 1122;
                itemDef.rotationX = 1159;
                itemDef.rotationY = 520;
                itemDef.rotationZ = 1904;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -4;
                break;

            case 21070:
                itemDef.name = "Masori mask";
                itemDef.femaleEquip1 = 95248;
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                itemDef.modelID = 96485;
                itemDef.maleEquip1 = 95248;
                itemDef.rotationX = 41;
                itemDef.rotationY = 102;
                itemDef.modelOffsetY = -2;
                itemDef.modelZoom = 800;

                break;

            case 21071:
                itemDef.name = "Masori body";
                itemDef.femaleEquip1 = 95257;
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                itemDef.modelID = 96476;
                itemDef.maleEquip1 = 95257;
                itemDef.rotationX = 0;
                itemDef.rotationY = 453;
                itemDef.modelZoom = 1100;
                break;

            case 21072:
                itemDef.name = "Masori chaps";
                itemDef.femaleEquip1 = 95256;
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                itemDef.modelID = 96482;
                itemDef.maleEquip1 = 95256;
                itemDef.rotationX = 0;
                itemDef.rotationY = 400;
                itemDef.modelZoom = 1300;
                //itemDef.modelOffsetY = 4;
                break;

            case 21073:
                itemDef.name = "Masori mask (f)";
                itemDef.femaleEquip1 = 95249;
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                itemDef.modelID = 96466;
                itemDef.maleEquip1 = 95249;
                itemDef.modelZoom = 1000;
                itemDef.rotationX = 10;
                itemDef.rotationY = 138;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = -3;
                break;

            case 21074:
                itemDef.name = "Masori body (f)";
                itemDef.femaleEquip1 = 95258;
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                itemDef.modelID = 96469;
                itemDef.maleEquip1 = 95258;
                itemDef.modelZoom = 1330;
                itemDef.rotationX = 0;
                itemDef.rotationY = 496;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 6;
                break;

            case 21075:
                itemDef.name = "Masori chaps (f)";
                itemDef.femaleEquip1 = 95255;
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                itemDef.modelID = 96475;
                itemDef.maleEquip1 = 95255;
                itemDef.modelZoom = 1780;
                itemDef.rotationX = 0;
                itemDef.rotationY = 617;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                break;

            case 21076:
                itemDef.name = "Mask of rebirth";
                //itemDef.femaleHeadModel = 46518;
                itemDef.femaleEquip1 = 96504;
                itemDef.actions = new String[]{null, "Wear", null, null, "Destroy"};
                itemDef.modelID = 96523;
                //itemDef.maleHeadModel = 46518;
                itemDef.maleEquip1 = 94533;
                itemDef.rotationX = 89;
                itemDef.rotationY = 42;
                itemDef.modelOffsetY = -7;
                itemDef.modelZoom = 832;
                break;

            case 21077:
                itemDef.name = "Lightbearer";
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                itemDef.modelID = 96473;
                itemDef.modelZoom = 1000;
                itemDef.rotationX = 165;
                itemDef.rotationY = 478;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -3;
                break;

            case 21078:
                itemDef.name = "Keris partisan";
                itemDef.femaleEquip1 = 94195;
                itemDef.actions = new String[]{null, "Wield", null, null, "Drop"};
                itemDef.modelID = 94613;
                itemDef.maleEquip1 = 94195;
                itemDef.modelZoom = 2217;
                itemDef.rotationX = 496;
                itemDef.rotationY = 513;
                itemDef.rotationZ = 61;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                break;

            case 21079:
                itemDef.name = "Breach of the scarab";
                itemDef.editedModelColor = new int[]{1374, 1500, 962, 931, 945, 922};
                itemDef.newModelColor = new int[]{-30244, -30120, -29738, -29781, -29760, -29794};
                itemDef.actions = new String[]{"Attach", "Inspect", null, null, "Drop"};
                itemDef.modelID = 96487;
                itemDef.modelZoom = 757;
                itemDef.rotationX = 0;
                itemDef.rotationY = 461;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                break;

            case 21080:
                itemDef.name = "Keris partisan of breaching";
                itemDef.editedModelColor = new int[]{4915, 3879, 1500, 962, 945, 922};
                itemDef.newModelColor = new int[]{-31451, -30436, -30244, -29738, -29760, -29794};
                itemDef.femaleEquip1 = 95275;
                itemDef.actions = new String[]{null, "Wield", "Dismantle", null, "Drop"};
                itemDef.modelID = 96480;
                itemDef.maleEquip1 = 95275;
                itemDef.modelZoom = 2217;
                itemDef.rotationX = 496;
                itemDef.rotationY = 513;
                itemDef.rotationZ = 61;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                break;

            case 21081:
                itemDef.name = "Eye of the corruptor";
                itemDef.editedModelColor = new int[]{61};
                itemDef.newModelColor = new int[]{-18499};
                itemDef.actions = new String[]{"Attach", "Inspect", null, null, "Drop"};
                itemDef.modelID = 96487;
                itemDef.modelZoom = 757;
                itemDef.rotationX = 0;
                itemDef.rotationY = 461;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                break;

            case 21082: //glitched
                itemDef.name = "Keris partisan of corruption";
                itemDef.femaleEquip1 = 95275;
                itemDef.actions = new String[]{null, "Wield", "Dismantle", null, "Drop"};
                itemDef.modelID = 96480;
                itemDef.maleEquip1 = 95275;
                itemDef.modelZoom = 2217;
                itemDef.rotationX = 496;
                itemDef.rotationY = 513;
                itemDef.rotationZ = 61;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                break;

            case 21083:
                itemDef.name = "Jewel of the sun";
                itemDef.editedModelColor = new int[]{1374, 1500, 962, 931, 945, 922};
                itemDef.newModelColor = new int[]{7646, 7768, 8152, 8103, 8128, 8097};
                itemDef.actions = new String[]{"Attach", "Inspect", null, null, "Drop"};
                itemDef.modelID = 96487;
                itemDef.modelZoom = 757;
                itemDef.rotationX = 0;
                itemDef.rotationY = 461;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                break;

            case 21084:
                itemDef.name = "Keris partisan of the sun";
                itemDef.editedModelColor = new int[]{4915, 3879, 1500, 962, 945, 922};
                itemDef.newModelColor = new int[]{7081, 5923, 7646, 8152, 8128, 8097};
                itemDef.femaleEquip1 = 95275;
                itemDef.actions = new String[]{null, "Wield", "Dismantle", null, "Drop"};
                itemDef.modelID = 96480;
                itemDef.maleEquip1 = 95275;
                itemDef.modelZoom = 2217;
                itemDef.rotationX = 496;
                itemDef.rotationY = 513;
                itemDef.rotationZ = 61;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                break;

            case 765:
                itemDef.name = "Elidinis' ward";
                itemDef.editedModelColor = new int[]{908};
                itemDef.newModelColor = new int[]{-10304};
                itemDef.femaleEquip1 = 95284;
                itemDef.actions = new String[]{null, "Wield", null, null, "Drop"};
                itemDef.modelID = 96477;
                itemDef.maleEquip1 = 95272;
                itemDef.modelZoom = 1296;
                itemDef.rotationX = 1783;
                itemDef.rotationY = 478;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 1;
                itemDef.modelOffsetY = 0;
                break;

            case 766:
                itemDef.name = "Elidinis' ward (f)";
                itemDef.editedModelColor = new int[]{908};
                itemDef.newModelColor = new int[]{-10304};
                itemDef.femaleEquip1 = 95276;
                itemDef.actions = new String[]{null, "Wield", null, "Dismantle", "Drop"};
                itemDef.modelID = 96472;
                itemDef.maleEquip1 = 95274;
                itemDef.modelZoom = 1226;
                itemDef.rotationX = 1773;
                itemDef.rotationY = 575;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                break;

            case 767:
                itemDef.name = "Elidinis' ward (or)";
                itemDef.femaleEquip1 = 95280;
                itemDef.actions = new String[]{null, "Wield", "Dismantle", null, "Drop"};
                itemDef.modelID = 96486;
                itemDef.maleEquip1 = 95280;
                itemDef.modelZoom = 1220;
                itemDef.rotationX = 1783;
                itemDef.rotationY = 391;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 1;
                itemDef.modelOffsetY = 1;
                break;

            case 764:
                itemDef.name = "Menaphite ornament kit";
                itemDef.editedModelColor = new int[]{28126, 28482};
                itemDef.newModelColor = new int[]{-3401, 9026};
                itemDef.actions = new String[]{"Inspect", "Attach", null, null, "Drop"};
                itemDef.modelID = 96491;
                itemDef.modelZoom = 739;
                itemDef.rotationX = 1678;
                itemDef.rotationY = 78;
                itemDef.rotationZ = 43;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                break;

            case 763:
                itemDef.name = "Thread of elidinis";
                itemDef.editedModelColor = new int[]{74};
                itemDef.newModelColor = new int[]{9168};
                itemDef.actions = new String[]{"Inspect", null, null, null, "Destroy"};
                itemDef.modelID = 96478;
                itemDef.modelZoom = 826;
                itemDef.rotationX = 1452;
                itemDef.rotationY = 1609;
                itemDef.rotationZ = 948;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                break;

            case 762:
                itemDef.name = "Divine rune pouch";
                itemDef.actions = new String[]{null, "Wield", "Check", "Unload", "Drop"};
                itemDef.modelID = 96463;
                itemDef.modelZoom = 843;
                itemDef.rotationX = 409;
                itemDef.rotationY = 513;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 1;
                break;
        }

        return itemDef;

    }

    public static ItemDef customBandosForID(int i, ItemDef itemDef) {

        switch (i) {

            case 21028:
                itemDef.name = "Enriched Bandos chestplate";
                itemDef.editedModelColor = new int[]{8384, 8367, 8375, 9523, 9515};
                itemDef.newModelColor = new int[]{123770, 123770, 123770, 123770, 123770};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 28133;
                itemDef.maleEquip1 = 27732;
                itemDef.maleEquip2 = 27748;
                itemDef.maleEquip3 = -1;
                itemDef.femaleEquip1 = 27732;
                itemDef.femaleEquip2 = 27748;
                itemDef.femaleEquip3 = -1;
                itemDef.modelZoom = 984;
                itemDef.rotationX = 6;
                itemDef.rotationY = 501;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 4;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 21029:
                itemDef.name = "Enriched Bandos tassets";
                itemDef.editedModelColor = new int[]{9523, 163};
                itemDef.newModelColor = new int[]{123770, 123770};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 28131;
                itemDef.maleEquip1 = 27739;
                itemDef.femaleEquip1 = 27755;
                itemDef.modelZoom = 854;
                itemDef.rotationX = 2039;
                itemDef.rotationY = 540;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 3;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 21030:
                itemDef.name = "Enriched Bandos boots";
                itemDef.editedModelColor = new int[]{10291};
                itemDef.newModelColor = new int[]{123770};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 28135;
                itemDef.maleEquip1 = 27737;
                itemDef.femaleEquip1 = 19951;
                itemDef.modelZoom = 724;
                itemDef.rotationX = 0;
                itemDef.rotationY = 171;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -7;
                itemDef.description = "Bandos boots";
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 21016:
                itemDef.name = "Soul Bandos chestplate";
                itemDef.editedModelColor = new int[]{8384, 8367, 8375, 9523, 9515};
                itemDef.newModelColor = new int[]{51136, 51136, 51136, 51136, 51136};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 28133;
                itemDef.maleEquip1 = 27732;
                itemDef.maleEquip2 = 27748;
                itemDef.maleEquip3 = -1;
                itemDef.femaleEquip1 = 27753;
                itemDef.femaleEquip2 = 27759;
                itemDef.femaleEquip3 = -1;
                itemDef.modelZoom = 984;
                itemDef.rotationX = 6;
                itemDef.rotationY = 501;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 4;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 21017:

                itemDef.name = "Soul Bandos tassets";
                itemDef.editedModelColor = new int[]{9523, 163};
                itemDef.newModelColor = new int[]{51136, 51136};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 28131;
                itemDef.maleEquip1 = 27739;
                itemDef.femaleEquip1 = 27755;
                itemDef.modelZoom = 854;
                itemDef.rotationX = 2039;
                itemDef.rotationY = 540;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 3;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 21018:
                itemDef.name = "Soul Bandos boots";
                itemDef.editedModelColor = new int[]{10291};
                itemDef.newModelColor = new int[]{51136};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 28135;
                itemDef.maleEquip1 = 27737;
                itemDef.femaleEquip1 = 19951;
                itemDef.modelZoom = 724;
                itemDef.rotationX = 0;
                itemDef.rotationY = 171;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -7;
                itemDef.description = "Bandos boots";
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 21019:
                itemDef.name = "Blood Bandos chestplate";
                itemDef.editedModelColor = new int[]{8384, 8367, 8375, 9523, 9515};
                itemDef.newModelColor = new int[]{933, 931, 931, 929, 927};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 28133;
                itemDef.maleEquip1 = 27732;
                itemDef.maleEquip2 = 27748;
                itemDef.maleEquip3 = -1;
                itemDef.femaleEquip1 = 27732;
                itemDef.femaleEquip2 = 27748;
                itemDef.femaleEquip3 = -1;
                itemDef.modelZoom = 984;
                itemDef.rotationX = 6;
                itemDef.rotationY = 501;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 4;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 21020:
                itemDef.name = "Blood Bandos tassets";
                itemDef.editedModelColor = new int[]{9523, 163};
                itemDef.newModelColor = new int[]{929, 929};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 28131;
                itemDef.maleEquip1 = 27739;
                itemDef.femaleEquip1 = 27755;
                itemDef.modelZoom = 854;
                itemDef.rotationX = 2039;
                itemDef.rotationY = 540;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 3;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 21021:
                itemDef.name = "Blood Bandos boots";
                itemDef.editedModelColor = new int[]{10291};
                itemDef.newModelColor = new int[]{925};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 28135;
                itemDef.maleEquip1 = 27737;
                itemDef.femaleEquip1 = 19951;
                itemDef.modelZoom = 724;
                itemDef.rotationX = 0;
                itemDef.rotationY = 171;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -7;
                itemDef.description = "Bandos boots";
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 21022:
                itemDef.name = "Gilded Bandos chestplate";
                itemDef.editedModelColor = new int[]{8384, 8367, 8375, 9523, 9515};
                itemDef.newModelColor = new int[]{7114, 7114, 7114, 7114, 7114};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 28133;
                itemDef.maleEquip1 = 27732;
                itemDef.maleEquip2 = 27748;
                itemDef.maleEquip3 = -1;
                itemDef.femaleEquip1 = 27732;
                itemDef.femaleEquip2 = 27748;
                itemDef.femaleEquip3 = -1;
                itemDef.modelZoom = 984;
                itemDef.rotationX = 6;
                itemDef.rotationY = 501;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 4;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 21023:
                itemDef.name = "Gilded Bandos tassets";
                itemDef.editedModelColor = new int[]{9523, 163};
                itemDef.newModelColor = new int[]{7114, 7114};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 28131;
                itemDef.maleEquip1 = 27739;
                itemDef.femaleEquip1 = 27755;
                itemDef.modelZoom = 854;
                itemDef.rotationX = 2039;
                itemDef.rotationY = 540;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 3;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 21024:
                itemDef.name = "Gilded Bandos boots";
                itemDef.editedModelColor = new int[]{10291};
                itemDef.newModelColor = new int[]{7114};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 28135;
                itemDef.maleEquip1 = 27737;
                itemDef.femaleEquip1 = 19951;
                itemDef.modelZoom = 724;
                itemDef.rotationX = 0;
                itemDef.rotationY = 171;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -7;
                itemDef.description = "Bandos boots";
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 2768:
                itemDef.name = "Festive Bandos chestplate";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 91298;
                itemDef.maleEquip1 = 91297;
                //itemDef.maleEquip2 = -1;
                //itemDef.maleEquip3 = -1;
                itemDef.femaleEquip1 = 91297;
                //itemDef.femaleEquip2 = -1;
                //itemDef.femaleEquip3 = -1;
                itemDef.modelZoom = 1484;
                itemDef.rotationX = 6;
                itemDef.rotationY = 501;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 4;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 2769:
                itemDef.name = "Festive Bandos tassets";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 91300;
                itemDef.maleEquip1 = 91299;
                itemDef.femaleEquip1 = 91299;
                itemDef.modelZoom = 1454;
                itemDef.rotationX = 2039;
                itemDef.rotationY = 540;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 3;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 2770:
                itemDef.name = "Festive Bandos boots";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 91305;
                itemDef.maleEquip1 = 91305;
                itemDef.femaleEquip1 = 91305;
                itemDef.modelZoom = 724;
                itemDef.rotationX = 0;
                itemDef.rotationY = 171;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -7;
                itemDef.description = "Bandos boots";
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 2771:
                itemDef.name = "Festive Slayer helm";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 91296;
                itemDef.maleEquip1 = 91295;
                itemDef.femaleEquip1 = 91295;
                itemDef.modelZoom = 800;
                //itemDef.rotationX = 0;
                itemDef.rotationY = 240;
                //itemDef.rotationZ = 0;
                //itemDef.modelOffsetX = 0;
                //itemDef.modelOffsetY = -7;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 2772:
                itemDef.name = "Festive Faceguard";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 91296;
                itemDef.maleEquip1 = 91295;
                itemDef.femaleEquip1 = 91295;
                itemDef.modelZoom = 800;
                //itemDef.rotationX = 0;
                itemDef.rotationY = 240;
                //itemDef.rotationZ = 0;
                //itemDef.modelOffsetX = 0;
                //itemDef.modelOffsetY = -7;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 2773:
                itemDef.name = "Festive Infernal Max Cape";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 91304;
                itemDef.maleEquip1 = 91303;
                itemDef.femaleEquip1 = 91303;
                itemDef.modelZoom = 1400;
                //itemDef.rotationX = 0;
                itemDef.rotationY = 240;
                //itemDef.rotationZ = 0;
                //itemDef.modelOffsetX = 0;
                //itemDef.modelOffsetY = -7;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 2774:
                itemDef.name = "Festive Fire Max Cape";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 91304;
                itemDef.maleEquip1 = 91303;
                itemDef.femaleEquip1 = 91303;
                itemDef.modelZoom = 1400;
                //itemDef.rotationX = 0;
                itemDef.rotationY = 240;
                //itemDef.rotationZ = 0;
                //itemDef.modelOffsetX = 0;
                //itemDef.modelOffsetY = -7;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 2775:
                itemDef.name = "Festive Gloves";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 91302;
                itemDef.maleEquip1 = 91301;
                itemDef.femaleEquip1 = 91301;
                itemDef.modelZoom = 800;
                //itemDef.rotationX = 0;
                itemDef.rotationY = 240;
                //itemDef.rotationZ = 0;
                //itemDef.modelOffsetX = 0;
                //itemDef.modelOffsetY = -7;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 2776:
                itemDef.name = "Wintumber axe a";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 20491;
                itemDef.maleEquip1 = 20491;
                itemDef.femaleEquip1 = 20491;
                itemDef.modelZoom = 800;
                //itemDef.rotationX = 0;
                itemDef.rotationY = 240;
                //itemDef.rotationZ = 0;
                //itemDef.modelOffsetX = 0;
                //itemDef.modelOffsetY = -7;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 2777:
                itemDef.name = "Wintumber axe b";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 20492;
                itemDef.maleEquip1 = 20492;
                itemDef.femaleEquip1 = 20492;
                itemDef.modelZoom = 800;
                //itemDef.rotationX = 0;
                itemDef.rotationY = 240;
                //itemDef.rotationZ = 0;
                //itemDef.modelOffsetX = 0;
                //itemDef.modelOffsetY = -7;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 2778:
                itemDef.name = "Wintumber axe c";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 20493;
                itemDef.maleEquip1 = 20493;
                itemDef.femaleEquip1 = 20493;
                itemDef.modelZoom = 800;
                //itemDef.rotationX = 0;
                itemDef.rotationY = 240;
                //itemDef.rotationZ = 0;
                //itemDef.modelOffsetX = 0;
                //itemDef.modelOffsetY = -7;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 2779:
                itemDef.name = "Wintumber axe d";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 20494;
                itemDef.maleEquip1 = 20494;
                itemDef.femaleEquip1 = 20494;
                itemDef.modelZoom = 800;
                //itemDef.rotationX = 0;
                itemDef.rotationY = 240;
                //itemDef.rotationZ = 0;
                //itemDef.modelOffsetX = 0;
                //itemDef.modelOffsetY = -7;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 2042:
                itemDef.name = "Tainted Bandos chestplate";
                itemDef.editedModelColor = new int[]{8384, 8367, 8375, 9523, 9515};
                itemDef.newModelColor = new int[]{0, 0, 0, 0, 0};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 28133;
                itemDef.maleEquip1 = 27732;
                itemDef.maleEquip2 = 27748;
                itemDef.maleEquip3 = -1;
                itemDef.femaleEquip1 = 27732;
                itemDef.femaleEquip2 = 27748;
                itemDef.femaleEquip3 = -1;
                itemDef.modelZoom = 984;
                itemDef.rotationX = 6;
                itemDef.rotationY = 501;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 4;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 2043:
                itemDef.name = "Tainted Bandos tassets";
                itemDef.editedModelColor = new int[]{9523, 163};
                itemDef.newModelColor = new int[]{0, 0};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 28131;
                itemDef.stackable = false;
                itemDef.certTemplateID = -1;
                itemDef.maleEquip1 = 27739;
                itemDef.femaleEquip1 = 27755;
                itemDef.modelZoom = 854;
                itemDef.rotationX = 2039;
                itemDef.rotationY = 540;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 3;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 2044:
                itemDef.name = "Tainted Bandos boots";
                itemDef.editedModelColor = new int[]{10291};
                itemDef.newModelColor = new int[]{0};
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 28135;
                itemDef.maleEquip1 = 27737;
                itemDef.femaleEquip1 = 19951;
                itemDef.modelZoom = 724;
                itemDef.rotationX = 0;
                itemDef.rotationY = 171;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -7;
                itemDef.description = "Bandos boots";
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

        }

        return itemDef;

    }

    public static ItemDef customNeitiznotForID(int i, ItemDef itemDef) {

        switch (i) {

            case 2710:
                itemDef.name = "Blood Neitiznot faceguard";
                itemDef.modelID = 91249;
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                itemDef.newModelColor = new int[]{930, 933, 936, 939, 8340, 8344, 8348, 8352, 8356, 8320, 929};
                itemDef.editedModelColor = new int[]{8625, 8635, 8644, 8646, 55, 68, 72, 76, 80, 35, 119};
                itemDef.maleEquip1 = 91250;
                itemDef.femaleEquip1 = 91250;
                itemDef.rotationX = 126;
                itemDef.rotationY = 129;
                itemDef.modelZoom = 984;
                itemDef.modelOffset1 = -1;
                itemDef.modelOffsetY = 1;
                itemDef.maleYOffset = 2;
                itemDef.femaleYOffset = 2;
                break;
            case 2711:
                itemDef.name = "Soul Neitiznot faceguard";
                itemDef.modelID = 91249;
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                itemDef.newModelColor = new int[]{51128, 51136, 51140, 51144, 8340, 8344, 8348, 8352, 8356, 8320, 51128};
                itemDef.editedModelColor = new int[]{8625, 8635, 8644, 8646, 55, 68, 72, 76, 80, 35, 119};
                itemDef.maleEquip1 = 91250;
                itemDef.femaleEquip1 = 91250;
                itemDef.rotationX = 126;
                itemDef.rotationY = 129;
                itemDef.modelZoom = 984;
                itemDef.modelOffset1 = -1;
                itemDef.modelOffsetY = 1;
                itemDef.maleYOffset = 2;
                itemDef.femaleYOffset = 2;
                break;
            case 2712:
                itemDef.name = "Gilded Neitiznot faceguard";
                itemDef.modelID = 91249;
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                itemDef.newModelColor = new int[]{7110, 7114, 7118, 7122, 8340, 8344, 8348, 8352, 8356, 8320, 7110};
                itemDef.editedModelColor = new int[]{8625, 8635, 8644, 8646, 55, 68, 72, 76, 80, 35, 119};
                itemDef.maleEquip1 = 91250;
                itemDef.femaleEquip1 = 91250;
                itemDef.rotationX = 126;
                itemDef.rotationY = 129;
                itemDef.modelZoom = 984;
                itemDef.modelOffset1 = -1;
                itemDef.modelOffsetY = 1;
                itemDef.maleYOffset = 2;
                itemDef.femaleYOffset = 2;
                break;
            case 2713:
                itemDef.name = "Enriched Neitiznot faceguard";
                itemDef.modelID = 91249;
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                itemDef.newModelColor = new int[]{123770, 123770, 123770, 123770, 8340, 8344, 8348, 8352, 8356, 8320, 123770};
                itemDef.editedModelColor = new int[]{8625, 8635, 8644, 8646, 55, 68, 72, 76, 80, 35, 119};
                itemDef.maleEquip1 = 91250;
                itemDef.femaleEquip1 = 91250;
                itemDef.rotationX = 126;
                itemDef.rotationY = 129;
                itemDef.modelZoom = 984;
                itemDef.modelOffset1 = -1;
                itemDef.modelOffsetY = 1;
                itemDef.maleYOffset = 2;
                itemDef.femaleYOffset = 2;
                break;
            case 2716:
                itemDef.name = "Tainted Neitiznot faceguard";
                itemDef.modelID = 91249;
                itemDef.actions = new String[]{null, "Wear", null, null, "Drop"};
                itemDef.newModelColor = new int[]{0, 10, 20, 30, 8340, 8344, 8348, 8352, 8356, 8320, 0};
                itemDef.editedModelColor = new int[]{8625, 8635, 8644, 8646, 55, 68, 72, 76, 80, 35, 119};
                itemDef.maleEquip1 = 91250;
                itemDef.femaleEquip1 = 91250;
                itemDef.rotationX = 126;
                itemDef.rotationY = 129;
                itemDef.modelZoom = 984;
                itemDef.modelOffset1 = -1;
                itemDef.modelOffsetY = 1;
                itemDef.maleYOffset = 2;
                itemDef.femaleYOffset = 2;
                break;

        }

        return itemDef;
    }

    public static ItemDef customArmadylForID(int i, ItemDef itemDef) {

        switch (i) {

            case 21007:
                itemDef.name = "Soul Armadyl helmet";
                itemDef.editedModelColor = new int[]{43084, 43076, 43030, 8650, 43080, 43088};
                itemDef.newModelColor = new int[]{43064, 43056, 43030, 51136, 43050, 43068};
                itemDef.modelID = 28139;
                itemDef.maleEquip1 = 27747;
                itemDef.femaleEquip1 = 27747;
                itemDef.modelZoom = 789;
                itemDef.rotationX = 372;
                itemDef.rotationY = 66;
                itemDef.rotationZ = 0;
                itemDef.modelOffset1 = 3;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = -7;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.stackable = false;
                break;

            case 21008:
                itemDef.name = "Soul Armadyl chestplate";
                itemDef.editedModelColor = new int[]{43096, 43047, 43088, 8658, 8650};
                itemDef.newModelColor = new int[]{43076, 43047, 43068, 51136, 51136};
                itemDef.modelID = 28141;
                itemDef.maleEquip1 = 27734;
                itemDef.maleEquip2 = 27750;
                itemDef.maleEquip3 = -1;
                itemDef.femaleEquip1 = 27734;
                itemDef.femaleEquip2 = 27750;
                itemDef.femaleEquip3 = -1;
                itemDef.modelZoom = 854;
                itemDef.rotationX = 0;
                itemDef.rotationY = 453;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -5;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.stackable = false;
                break;

            case 21009:
                itemDef.name = "Soul Armadyl plateskirt";
                itemDef.editedModelColor = new int[]{43096, 8650, 43047, 43088, 43072, 43063, 43080};
                itemDef.newModelColor = new int[]{43076, 51136, 43037, 43068, 43052, 43043, 43060};
                itemDef.modelID = 28132;
                itemDef.maleEquip1 = 27742;
                itemDef.femaleEquip1 = 27756;
                itemDef.modelZoom = 1957;
                itemDef.rotationX = 2036;
                itemDef.rotationY = 555;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -3;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.stackable = false;
                break;


            case 21010:
                itemDef.name = "Blood Armadyl helmet";
                itemDef.editedModelColor = new int[]{43084, 43076, 43030, 8650, 43080, 43088};
                itemDef.newModelColor = new int[]{43054, 43046, 43030, 929, 43050, 43058};
                itemDef.modelID = 28139;
                itemDef.maleEquip1 = 27747;
                itemDef.femaleEquip1 = 27747;
                itemDef.modelZoom = 789;
                itemDef.rotationX = 372;
                itemDef.rotationY = 66;
                itemDef.rotationZ = 0;
                itemDef.modelOffset1 = 3;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = -7;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.stackable = false;
                break;

            case 21011:
                itemDef.name = "Blood Armadyl chestplate";
                itemDef.editedModelColor = new int[]{43096, 43047, 43088, 8658, 8650};
                itemDef.newModelColor = new int[]{43066, 43030, 43058, 933, 929};
                itemDef.modelID = 28141;
                itemDef.maleEquip1 = 27734;
                itemDef.maleEquip2 = 27750;
                itemDef.maleEquip3 = -1;
                itemDef.femaleEquip1 = 27734;
                itemDef.femaleEquip2 = 27750;
                itemDef.femaleEquip3 = -1;
                itemDef.modelZoom = 854;
                itemDef.rotationX = 0;
                itemDef.rotationY = 453;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -5;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.stackable = false;
                break;

            //43000 sweeeeet blue

            case 21012:
                itemDef.name = "Blood Armadyl plateskirt";
                itemDef.editedModelColor = new int[]{43096, 8650, 43047, 43088, 43072, 43063, 43080};
                itemDef.newModelColor = new int[]{43066, 929, 43030, 43058, 43042, 43033, 43050};
                itemDef.modelID = 28132;
                itemDef.maleEquip1 = 27742;
                itemDef.femaleEquip1 = 27756;
                itemDef.modelZoom = 1957;
                itemDef.rotationX = 2036;
                itemDef.rotationY = 555;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -3;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.stackable = false;
                break;


            case 21013:
                itemDef.name = "Gilded Armadyl helmet";
                itemDef.editedModelColor = new int[]{8650};
                itemDef.newModelColor = new int[]{7114};
                itemDef.modelID = 28139;
                itemDef.maleEquip1 = 27747;
                itemDef.femaleEquip1 = 27747;
                itemDef.modelZoom = 789;
                itemDef.rotationX = 372;
                itemDef.rotationY = 66;
                itemDef.rotationZ = 0;
                itemDef.modelOffset1 = 3;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = -7;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.stackable = false;
                break;

            case 21014:
                itemDef.name = "Gilded Armadyl chestplate";
                itemDef.editedModelColor = new int[]{8658, 8650, 43096, 43088, 43047};
                itemDef.newModelColor = new int[]{7118, 7114, 43070, 43060, 43030};
                itemDef.modelID = 28141;
                itemDef.maleEquip1 = 27734;
                itemDef.maleEquip2 = 27750;
                itemDef.maleEquip3 = -1;
                itemDef.femaleEquip1 = 27734;
                itemDef.femaleEquip2 = 27750;
                itemDef.femaleEquip3 = -1;
                itemDef.modelZoom = 854;
                itemDef.rotationX = 0;
                itemDef.rotationY = 453;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -5;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.stackable = false;
                break;

            case 21015:
                itemDef.name = "Gilded Armadyl plateskirt";
                itemDef.editedModelColor = new int[]{8650, 43096};
                itemDef.newModelColor = new int[]{7114, 43070};
                itemDef.modelID = 28132;
                itemDef.maleEquip1 = 27742;
                itemDef.femaleEquip1 = 27756;
                itemDef.modelZoom = 1957;
                itemDef.rotationX = 2036;
                itemDef.rotationY = 555;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -3;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.stackable = false;
                break;

            case 21025:
                itemDef.name = "Enriched Armadyl helmet";
                itemDef.editedModelColor = new int[]{8650};
                itemDef.newModelColor = new int[]{123770};
                itemDef.modelID = 28139;
                itemDef.maleEquip1 = 27747;
                itemDef.femaleEquip1 = 27747;
                itemDef.modelZoom = 789;
                itemDef.rotationX = 372;
                itemDef.rotationY = 66;
                itemDef.rotationZ = 0;
                itemDef.modelOffset1 = 3;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = -7;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.stackable = false;
                break;

            case 21026:
                itemDef.name = "Enriched Armadyl chestplate";
                itemDef.editedModelColor = new int[]{8658, 8650, 43096, 43088, 43047};
                itemDef.newModelColor = new int[]{123770, 123770, 43070, 43060, 43030};
                itemDef.modelID = 28141;
                itemDef.maleEquip1 = 27734;
                itemDef.maleEquip2 = 27750;
                itemDef.maleEquip3 = -1;
                itemDef.femaleEquip1 = 27734;
                itemDef.femaleEquip2 = 27750;
                itemDef.femaleEquip3 = -1;
                itemDef.modelZoom = 854;
                itemDef.rotationX = 0;
                itemDef.rotationY = 453;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -5;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.stackable = false;
                break;

            case 21027:
                itemDef.name = "Enriched Armadyl plateskirt";
                itemDef.editedModelColor = new int[]{8650, 43096};
                itemDef.newModelColor = new int[]{123770, 43070};
                itemDef.modelID = 28132;
                itemDef.maleEquip1 = 27742;
                itemDef.femaleEquip1 = 27756;
                itemDef.modelZoom = 1957;
                itemDef.rotationX = 2036;
                itemDef.rotationY = 555;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -3;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.stackable = false;
                break;

            case 2045:
                itemDef.name = "Tainted Armadyl helmet";
                itemDef.editedModelColor = new int[]{8650};
                itemDef.newModelColor = new int[]{0};
                itemDef.modelID = 28139;
                itemDef.maleEquip1 = 27747;
                itemDef.femaleEquip1 = 27747;
                itemDef.stackable = false;
                itemDef.certTemplateID = -1;
                itemDef.modelZoom = 789;
                itemDef.rotationX = 372;
                itemDef.rotationY = 66;
                itemDef.rotationZ = 0;
                itemDef.modelOffset1 = 3;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = -7;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.stackable = false;
                break;

            case 2046:
                itemDef.name = "Tainted Armadyl chestplate";
                itemDef.editedModelColor = new int[]{8658, 8650, 43096, 43088, 43047};
                itemDef.newModelColor = new int[]{0, 0, 20, 30, 40};
                itemDef.modelID = 28141;
                itemDef.maleEquip1 = 27734;
                itemDef.maleEquip2 = 27750;
                itemDef.maleEquip3 = -1;
                itemDef.femaleEquip1 = 27734;
                itemDef.femaleEquip2 = 27750;
                itemDef.femaleEquip3 = -1;
                itemDef.modelZoom = 854;
                itemDef.rotationX = 0;
                itemDef.rotationY = 453;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -5;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.stackable = false;
                break;

            case 2047:
                itemDef.name = "Tainted Armadyl plateskirt";
                itemDef.editedModelColor = new int[]{8650, 43096};
                itemDef.newModelColor = new int[]{0, 20};
                itemDef.modelID = 28132;
                itemDef.maleEquip1 = 27742;
                itemDef.femaleEquip1 = 27756;
                itemDef.modelZoom = 1957;
                itemDef.rotationX = 2036;
                itemDef.stackable = false;
                itemDef.certTemplateID = -1;
                itemDef.rotationY = 555;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = -3;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.stackable = false;
                break;

        }
        return itemDef;
    }

    public static ItemDef slayerInstanceKeys(int i, ItemDef itemDef) {

        switch (i) {

            case 3230:
                itemDef.name = "Slayer key (Crawling Hand)";
                itemDef.modelID = 28252; //Ecumenical key model
                itemDef.modelZoom = 820;
                itemDef.rotationX = 0;
                itemDef.rotationY = 443;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                break;
            case 3231:
                itemDef.name = "Slayer key (Cave Crawler)";
                itemDef.modelID = 28252; //Ecumenical key model
                itemDef.modelZoom = 820;
                itemDef.rotationX = 0;
                itemDef.rotationY = 443;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                break;
            case 3232:
                itemDef.name = "Slayer key (Banshee)";
                itemDef.modelID = 28252; //Ecumenical key model
                itemDef.modelZoom = 820;
                itemDef.rotationX = 0;
                itemDef.rotationY = 443;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                break;
            case 3233:
                itemDef.name = "Slayer key (Cockatrice)";
                itemDef.modelID = 28252; //Ecumenical key model
                itemDef.modelZoom = 820;
                itemDef.rotationX = 0;
                itemDef.rotationY = 443;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                break;
            case 3234:
                itemDef.name = "Slayer key (Pyrefiend)";
                itemDef.modelID = 28252; //Ecumenical key model
                itemDef.modelZoom = 820;
                itemDef.rotationX = 0;
                itemDef.rotationY = 443;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                break;
            case 3235:
                itemDef.name = "Slayer key (Basilisk)";
                itemDef.modelID = 28252; //Ecumenical key model
                itemDef.modelZoom = 820;
                itemDef.rotationX = 0;
                itemDef.rotationY = 443;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                break;
            case 3236:
                itemDef.name = "Slayer key (Infernal Mage)";
                itemDef.modelID = 28252; //Ecumenical key model
                itemDef.modelZoom = 820;
                itemDef.rotationX = 0;
                itemDef.rotationY = 443;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                break;
            case 3237:
                itemDef.name = "Slayer key (Bloodveld)";
                itemDef.modelID = 28252; //Ecumenical key model
                itemDef.modelZoom = 820;
                itemDef.rotationX = 0;
                itemDef.rotationY = 443;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                break;
            case 3238:
                itemDef.name = "Slayer key (Jelly)";
                itemDef.modelID = 28252; //Ecumenical key model
                itemDef.modelZoom = 820;
                itemDef.rotationX = 0;
                itemDef.rotationY = 443;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                break;
            case 3239:
                itemDef.name = "Slayer key (Turoth)";
                itemDef.modelID = 28252; //Ecumenical key model
                itemDef.modelZoom = 820;
                itemDef.rotationX = 0;
                itemDef.rotationY = 443;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                break;
            case 3240:
                itemDef.name = "Slayer key (Cave Horror)";
                itemDef.modelID = 28252; //Ecumenical key model
                itemDef.modelZoom = 820;
                itemDef.rotationX = 0;
                itemDef.rotationY = 443;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                break;
            case 3241:
                itemDef.name = "Slayer key (Abberant Spectre)";
                itemDef.modelID = 28252; //Ecumenical key model
                itemDef.modelZoom = 820;
                itemDef.rotationX = 0;
                itemDef.rotationY = 443;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                break;
            case 3242:
                itemDef.name = "Slayer key (Dust Devil)";
                itemDef.modelID = 28252; //Ecumenical key model
                itemDef.modelZoom = 820;
                itemDef.rotationX = 0;
                itemDef.rotationY = 443;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                break;
            case 3243:
                itemDef.name = "Slayer key (Kurask)";
                itemDef.modelID = 28252; //Ecumenical key model
                itemDef.modelZoom = 820;
                itemDef.rotationX = 0;
                itemDef.rotationY = 443;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                break;
            case 3244:
                itemDef.name = "Slayer key (Gargoyle)";
                itemDef.modelID = 28252; //Ecumenical key model
                itemDef.modelZoom = 820;
                itemDef.rotationX = 0;
                itemDef.rotationY = 443;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                break;
            case 3245:
                itemDef.name = "Slayer key (Nechryael)";
                itemDef.modelID = 28252; //Ecumenical key model
                itemDef.modelZoom = 820;
                itemDef.rotationX = 0;
                itemDef.rotationY = 443;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                break;
            case 3246:
                itemDef.name = "Slayer key (Abyssal Demon)";
                itemDef.modelID = 28252; //Ecumenical key model
                itemDef.modelZoom = 820;
                itemDef.rotationX = 0;
                itemDef.rotationY = 443;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                break;
            case 3247:
                itemDef.name = "Slayer key (Dark Beast)";
                itemDef.modelID = 28252; //Ecumenical key model
                itemDef.modelZoom = 820;
                itemDef.rotationX = 0;
                itemDef.rotationY = 443;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                break;
            case 3248:
                itemDef.name = "Slayer key (Smoke Devil)";
                itemDef.modelID = 28252; //Ecumenical key model
                itemDef.modelZoom = 820;
                itemDef.rotationX = 0;
                itemDef.rotationY = 443;
                itemDef.rotationZ = 0;
                itemDef.modelOffsetX = 0;
                itemDef.modelOffsetY = 0;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                break;
        }

        return itemDef;

    }

    public static ItemDef customSubscriptionItems(int i, ItemDef itemDef) {


        switch (i) {

            case 906:
                itemDef.name = "Sapphire Box";
                itemDef.actions = new String[]{"Open", null, null, null, null, null};
                itemDef.modelID = 2426;
                itemDef.modelZoom = 1180;
                itemDef.rotationY = 172;
                itemDef.rotationX = 160;
                itemDef.modelOffsetY = -14;
                itemDef.newModelColor = new int[]{-21588, 0}; // Blue and black
                itemDef.editedModelColor = new int[]{2999, 22410}; // 2999 = ribbon, 224210 = box
                break;

            case 907:
                itemDef.name = "Emerald Box";
                itemDef.actions = new String[]{"Open", null, null, null, null, null};
                itemDef.modelID = 2426;
                itemDef.modelZoom = 1180;
                itemDef.rotationY = 172;
                itemDef.rotationX = 160;
                itemDef.modelOffsetY = -14;
                itemDef.newModelColor = new int[]{22464, 0}; // Green and black
                itemDef.editedModelColor = new int[]{2999, 22410}; // 2999 = ribbon, 224210 = box
                break;

            case 908:
                itemDef.name = "Ruby Box";
                itemDef.actions = new String[]{"Open", null, null, null, null, null};
                itemDef.modelID = 2426;
                itemDef.modelZoom = 1180;
                itemDef.rotationY = 172;
                itemDef.rotationX = 160;
                itemDef.modelOffsetY = -14;
                itemDef.newModelColor = new int[]{929, 0}; // Red and black
                itemDef.editedModelColor = new int[]{2999, 22410}; // 2999 = ribbon, 224210 = box
                break;

            case 909:
                itemDef.name = "Diamond Box";
                itemDef.actions = new String[]{"Open", null, null, null, null, null};
                itemDef.modelID = 2426;
                itemDef.modelZoom = 1180;
                itemDef.rotationY = 172;
                itemDef.rotationX = 160;
                itemDef.modelOffsetY = -14;
                itemDef.newModelColor = new int[]{127, 0}; // White and black
                itemDef.editedModelColor = new int[]{2999, 22410}; // 2999 = ribbon, 224210 = box
                break;

            case 910:
                itemDef.name = "Dragonstone Box";
                itemDef.actions = new String[]{"Open", null, null, null, null, null};
                itemDef.modelID = 2426;
                itemDef.modelZoom = 1180;
                itemDef.rotationY = 172;
                itemDef.rotationX = 160;
                itemDef.modelOffsetY = -14;
                itemDef.newModelColor = new int[]{-14400, 0}; // Purple and black
                itemDef.editedModelColor = new int[]{2999, 22410}; // 2999 = ribbon, 224210 = box
                break;


            case 911:
                itemDef.name = "Sapphire Subscription";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{11224, 9164, 11092, 9152, 7087, 7997, 8117, 22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{123770, 123770, 123770, 123770, 123770, 123770, 123770, -21588, -21588, -21588, -21588, -21588, -21588, -21588};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 912:
                itemDef.name = "Emerald Subscription";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{11224, 9164, 11092, 9152, 7087, 7997, 8117, 22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{123770, 123770, 123770, 123770, 123770, 123770, 123770, 22464, 22464, 22464, 22464, 22464, 22464, 22464};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 913:
                itemDef.name = "Ruby Subscription";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{11224, 9164, 11092, 9152, 7087, 7997, 8117, 22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{123770, 123770, 123770, 123770, 123770, 123770, 123770, 929, 929, 929, 929, 929, 929, 929};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 914:
                itemDef.name = "Diamond Subscription";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{11224, 9164, 11092, 9152, 7087, 7997, 8117, 22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{123770, 123770, 123770, 123770, 123770, 123770, 123770, 127, 127, 127, 127, 127, 127, 127};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

            case 915:
                itemDef.name = "Dragonstone Subscription";
                itemDef.actions = new String[5];
                itemDef.modelID = 91226;
                itemDef.modelZoom = 2300;
                itemDef.rotationY = 512;
                itemDef.rotationX = 512;
                itemDef.modelOffsetX = 3;
                itemDef.modelOffsetY = 10;
                itemDef.editedModelColor = new int[]{11224, 9164, 11092, 9152, 7087, 7997, 8117, 22464, 20416, 22451, 22181, 22449, 22305, 21435};
                itemDef.newModelColor = new int[]{123770, 123770, 123770, 123770, 123770, 123770, 123770, -14400, -14400, -14400, -14400, -14400, -14400, -14400};
                itemDef.actions[4] = "Drop";
                itemDef.actions[0] = "Redeem";
                break;

        }

        return itemDef;

    }

    public static ItemDef customHolidayForID(int i, ItemDef itemDef) {


        switch (i) {

            case 2776:
                itemDef.name = "Wintumber legs";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 20491;
                itemDef.maleEquip1 = 20491;
                itemDef.femaleEquip1 = 20491;
                itemDef.modelZoom = 1500;
                //itemDef.rotationX = 0;
                //itemDef.rotationY = 240;
                //itemDef.rotationZ = 0;
                //itemDef.modelOffsetX = 0;
                //itemDef.modelOffsetY = -7;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 2777:
                itemDef.name = "Wintumber body";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 20492;
                itemDef.maleEquip1 = 20492;
                itemDef.femaleEquip1 = 20492;
                itemDef.modelZoom = 1500;
                //itemDef.rotationX = 0;
                itemDef.rotationY = 240;
                //itemDef.rotationZ = 0;
                //itemDef.modelOffsetX = 0;
                //itemDef.modelOffsetY = -7;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 2778:
                itemDef.name = "Wintumber hat";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 20493;
                itemDef.maleEquip1 = 20493;
                itemDef.femaleEquip1 = 20493;
                itemDef.modelZoom = 1200;
                //itemDef.rotationX = 0;
                //itemDef.rotationY = 240;
                //itemDef.rotationZ = 0;
                //itemDef.modelOffsetX = 0;
                //itemDef.modelOffsetY = -7;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;

            case 2779:
                itemDef.name = "Wintumber stars";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wear";
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 20494;
                itemDef.maleEquip1 = 20494;
                itemDef.femaleEquip1 = 20494;
                itemDef.modelZoom = 1200;
                //itemDef.rotationX = 0;
                itemDef.rotationY = 240;
                //itemDef.rotationZ = 0;
                //itemDef.modelOffsetX = 0;
                //itemDef.modelOffsetY = -7;
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                break;


        }

        return itemDef;

    }


    public static void setSettings() {
        try {
            prices = new int[22694];
            int index = 0;
            for (String line : Files.readAllLines(Paths.get(Signlink.findcachedir() + "data/data.txt"), Charset.defaultCharset())) {
                prices[index] = Integer.parseInt(line);
                index++;
            }
            for (int i : UNTRADEABLE_ITEMS) {
                untradeableItems.add(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Sprite getSprite(int i, int j, int k, int zoom) {
        if (k == 0 && zoom != -1) {
            Sprite sprite = (Sprite) spriteCache.get(i);
            if (sprite != null && sprite.maxHeight != j && sprite.maxHeight != -1) {
                sprite.unlink();
                sprite = null;
            }
            if (sprite != null) {
                return sprite;
            }
        }
        ItemDef itemDef = forID(i);
        if (itemDef.stackIDs == null) {
            j = -1;
        }
        if (j > 1) {
            int i1 = -1;
            for (int j1 = 0; j1 < 10; j1++) {
                if (j >= itemDef.stackAmounts[j1] && itemDef.stackAmounts[j1] != 0) {
                    i1 = itemDef.stackIDs[j1];
                }
            }

            if (i1 != -1) {
                itemDef = forID(i1);
            }
        }
        Model model = itemDef.getItemModelFinalised(1);
        if (model == null) {
            return null;
        }
        Sprite sprite = null;
        if (itemDef.certTemplateID != -1) {
            sprite = getSprite(itemDef.certID, 10, -1);
            if (sprite == null) {
                return null;
            }
        }
        if (itemDef.lendID != -1) {
            sprite = getSprite(itemDef.lendID, 50, 0);
            if (sprite == null) {
                return null;
            }
        }
        Sprite sprite2 = new Sprite(32, 32);
        int k1 = Rasterizer.textureInt1;
        int l1 = Rasterizer.textureInt2;
        int[] ai = Rasterizer.scanOffsets;
        int[] ai1 = DrawingArea.pixels;
        int i2 = DrawingArea.width;
        int j2 = DrawingArea.height;
        int k2 = DrawingArea.topX;
        int l2 = DrawingArea.bottomX;
        int i3 = DrawingArea.topY;
        int j3 = DrawingArea.bottomY;
        Rasterizer.aBoolean1464 = false;
        DrawingArea.initDrawingArea(32, 32, sprite2.myPixels);
        DrawingArea.drawPixels(32, 0, 0, 0, 32);
        Rasterizer.setDefaultBounds();
        int k3 = itemDef.modelZoom;
        if (zoom != -1 && zoom != 0) {
            k3 = (itemDef.modelZoom * 100) / zoom;
        }
        if (k == -1) {
            k3 = (int) ((double) k3 * 1.5D);
        }
        if (k > 0) {
            k3 = (int) ((double) k3 * 1.04D);
        }
        int l3 = Rasterizer.anIntArray1470[itemDef.rotationY] * k3 >> 16;
        int i4 = Rasterizer.anIntArray1471[itemDef.rotationY] * k3 >> 16;
        model.renderSingle(itemDef.rotationX, itemDef.modelOffsetX, itemDef.rotationY, itemDef.modelOffset1, l3 + model.modelBaseY / 2 + itemDef.modelOffsetY, i4 + itemDef.modelOffsetY);
        for (int i5 = 31; i5 >= 0; i5--) {
            for (int j4 = 31; j4 >= 0; j4--) {
                if (sprite2.myPixels[i5 + j4 * 32] != 0) {
                    continue;
                }
                if (i5 > 0 && sprite2.myPixels[(i5 - 1) + j4 * 32] > 1) {
                    sprite2.myPixels[i5 + j4 * 32] = 1;
                    continue;
                }
                if (j4 > 0 && sprite2.myPixels[i5 + (j4 - 1) * 32] > 1) {
                    sprite2.myPixels[i5 + j4 * 32] = 1;
                    continue;
                }
                if (i5 < 31 && sprite2.myPixels[i5 + 1 + j4 * 32] > 1) {
                    sprite2.myPixels[i5 + j4 * 32] = 1;
                    continue;
                }
                if (j4 < 31 && sprite2.myPixels[i5 + (j4 + 1) * 32] > 1) {
                    sprite2.myPixels[i5 + j4 * 32] = 1;
                }
            }

        }

        if (k > 0) {
            for (int j5 = 31; j5 >= 0; j5--) {
                for (int k4 = 31; k4 >= 0; k4--) {
                    if (sprite2.myPixels[j5 + k4 * 32] != 0) {
                        continue;
                    }
                    if (j5 > 0 && sprite2.myPixels[(j5 - 1) + k4 * 32] == 1) {
                        sprite2.myPixels[j5 + k4 * 32] = k;
                        continue;
                    }
                    if (k4 > 0 && sprite2.myPixels[j5 + (k4 - 1) * 32] == 1) {
                        sprite2.myPixels[j5 + k4 * 32] = k;
                        continue;
                    }
                    if (j5 < 31 && sprite2.myPixels[j5 + 1 + k4 * 32] == 1) {
                        sprite2.myPixels[j5 + k4 * 32] = k;
                        continue;
                    }
                    if (k4 < 31 && sprite2.myPixels[j5 + (k4 + 1) * 32] == 1) {
                        sprite2.myPixels[j5 + k4 * 32] = k;
                    }
                }

            }

        } else if (k == 0) {
            for (int k5 = 31; k5 >= 0; k5--) {
                for (int l4 = 31; l4 >= 0; l4--) {
                    if (sprite2.myPixels[k5 + l4 * 32] == 0 && k5 > 0 && l4 > 0 && sprite2.myPixels[(k5 - 1) + (l4 - 1) * 32] > 0) {
                        sprite2.myPixels[k5 + l4 * 32] = 0x302020;
                    }
                }

            }

        }
        if (itemDef.certTemplateID != -1) {
            int l5 = sprite.maxWidth;
            int j6 = sprite.maxHeight;
            sprite.maxWidth = 32;
            sprite.maxHeight = 32;
            sprite.drawSprite(0, 0);
            sprite.maxWidth = l5;
            sprite.maxHeight = j6;
        }
        if (itemDef.lendID != -1) {
            int l5 = sprite.maxWidth;
            int j6 = sprite.maxHeight;
            sprite.maxWidth = 32;
            sprite.maxHeight = 32;
            sprite.drawSprite(0, 0);
            sprite.maxWidth = l5;
            sprite.maxHeight = j6;
        }
        if (k == 0) {
            spriteCache.put(sprite2, i);
        }
        DrawingArea.initDrawingArea(j2, i2, ai1);
        DrawingArea.setDrawingArea(j3, k2, l2, i3);
        Rasterizer.textureInt1 = k1;
        Rasterizer.textureInt2 = l1;
        Rasterizer.scanOffsets = ai;
        Rasterizer.aBoolean1464 = true;
        sprite2.maxWidth = itemDef.stackable ? 33 : 32;
        sprite2.maxHeight = j;
        return sprite2;
    }

    public static Sprite getSprite(int i, int j, int k) {
        if (k == 0) {
            Sprite sprite = (Sprite) spriteCache.get(i);
            if (sprite != null && sprite.maxHeight != j && sprite.maxHeight != -1) {
                sprite.unlink();
                sprite = null;
            }
            if (sprite != null) {
                return sprite;
            }
        }
        ItemDef itemDef = forID(i);
        if (itemDef.stackIDs == null) {
            j = -1;
        }
        if (j > 1) {
            int i1 = -1;
            for (int j1 = 0; j1 < 10; j1++) {
                if (j >= itemDef.stackAmounts[j1] && itemDef.stackAmounts[j1] != 0) {
                    i1 = itemDef.stackIDs[j1];
                }
            }
            if (i1 != -1) {
                itemDef = forID(i1);
            }
        }
        Model model = itemDef.getItemModelFinalised(1);
        if (model == null) {
            return null;
        }
        Sprite sprite = null;
        if (itemDef.certTemplateID != -1) {
            sprite = getSprite(itemDef.certID, 10, -1);
            if (sprite == null) {
                return null;
            }
        }
        if (itemDef.opcode140 != -1) {
            sprite = getSprite(itemDef.opcode139, j, -1);
            if (sprite == null)
                return null;
        }
        if (itemDef.opcode149 != -1) {
            sprite = getSprite(itemDef.opcode148, j, -1);
            if (sprite == null)
                return null;
        }
        if (itemDef.lentItemID != -1) {
            sprite = getSprite(itemDef.lendID, 50, 0);
            if (sprite == null) {
                return null;
            }
        }
        Sprite sprite2 = new Sprite(32, 32);
        int k1 = Rasterizer.textureInt1;
        int l1 = Rasterizer.textureInt2;
        int[] ai = Rasterizer.scanOffsets;
        int[] ai1 = DrawingArea.pixels;
        int i2 = DrawingArea.width;
        int j2 = DrawingArea.height;
        int k2 = DrawingArea.topX;
        int l2 = DrawingArea.bottomX;
        int i3 = DrawingArea.topY;
        int j3 = DrawingArea.bottomY;
        Rasterizer.aBoolean1464 = false;
        DrawingArea.initDrawingArea(32, 32, sprite2.myPixels);
        DrawingArea.drawPixels(32, 0, 0, 0, 32);
        Rasterizer.setDefaultBounds();
        int k3 = itemDef.modelZoom;
        if (k == -1) {
            k3 = (int) ((double) k3 * 1.5D);
        }
        if (k > 0) {
            k3 = (int) ((double) k3 * 1.04D);
        }
        int l3 = Rasterizer.anIntArray1470[itemDef.rotationY] * k3 >> 16;
        int i4 = Rasterizer.anIntArray1471[itemDef.rotationY] * k3 >> 16;
        model.renderSingle(itemDef.rotationX, itemDef.modelOffsetX, itemDef.rotationY, itemDef.modelOffset1, l3 + model.modelBaseY / 2 + itemDef.modelOffsetY, i4 + itemDef.modelOffsetY);
        if (itemDef.opcode140 != -1) {
            int l5 = sprite.maxWidth;
            int j6 = sprite.maxHeight;
            sprite.maxWidth = 32;
            sprite.maxHeight = 32;
            sprite.drawSprite(0, 0);
            sprite.maxWidth = l5;
            sprite.maxHeight = j6;
        }
        for (int i5 = 31; i5 >= 0; i5--) {
            for (int j4 = 31; j4 >= 0; j4--) {
                if (sprite2.myPixels[i5 + j4 * 32] == 0) {
                    if (i5 > 0 && sprite2.myPixels[(i5 - 1) + j4 * 32] > 1) {
                        sprite2.myPixels[i5 + j4 * 32] = 1;
                    } else if (j4 > 0 && sprite2.myPixels[i5 + (j4 - 1) * 32] > 1) {
                        sprite2.myPixels[i5 + j4 * 32] = 1;
                    } else if (i5 < 31 && sprite2.myPixels[i5 + 1 + j4 * 32] > 1) {
                        sprite2.myPixels[i5 + j4 * 32] = 1;
                    } else if (j4 < 31 && sprite2.myPixels[i5 + (j4 + 1) * 32] > 1) {
                        sprite2.myPixels[i5 + j4 * 32] = 1;
                    }
                }
            }
        }
        if (k > 0) {
            for (int j5 = 31; j5 >= 0; j5--) {
                for (int k4 = 31; k4 >= 0; k4--) {
                    if (sprite2.myPixels[j5 + k4 * 32] == 0) {
                        if (j5 > 0 && sprite2.myPixels[(j5 - 1) + k4 * 32] == 1) {
                            sprite2.myPixels[j5 + k4 * 32] = k;
                        } else if (k4 > 0 && sprite2.myPixels[j5 + (k4 - 1) * 32] == 1) {
                            sprite2.myPixels[j5 + k4 * 32] = k;
                        } else if (j5 < 31 && sprite2.myPixels[j5 + 1 + k4 * 32] == 1) {
                            sprite2.myPixels[j5 + k4 * 32] = k;
                        } else if (k4 < 31 && sprite2.myPixels[j5 + (k4 + 1) * 32] == 1) {
                            sprite2.myPixels[j5 + k4 * 32] = k;
                        }
                    }
                }
            }
        } else if (k == 0) {
            for (int k5 = 31; k5 >= 0; k5--) {
                for (int l4 = 31; l4 >= 0; l4--) {
                    if (sprite2.myPixels[k5 + l4 * 32] == 0 && k5 > 0 && l4 > 0 && sprite2.myPixels[(k5 - 1) + (l4 - 1) * 32] > 0) {
                        sprite2.myPixels[k5 + l4 * 32] = 0x302020;
                    }
                }
            }
        }
        if (itemDef.certTemplateID != -1) {
            int l5 = sprite.maxWidth;
            int j6 = sprite.maxHeight;
            sprite.maxWidth = 32;
            sprite.maxHeight = 32;
            sprite.drawSprite(0, 0);
            sprite.maxWidth = l5;
            sprite.maxHeight = j6;
        }
        if (itemDef.lentItemID != -1) {
            int l5 = sprite.maxWidth;
            int j6 = sprite.maxHeight;
            sprite.maxWidth = 32;
            sprite.maxHeight = 32;
            sprite.drawSprite(0, 0);
            sprite.maxWidth = l5;
            sprite.maxHeight = j6;
        }
        if (k == 0 && !itemDef.animateInventory) {
            spriteCache.put(sprite2, i);
        }
        DrawingArea.initDrawingArea(j2, i2, ai1);
        DrawingArea.setDrawingArea(j3, k2, l2, i3);
        Rasterizer.textureInt1 = k1;
        Rasterizer.textureInt2 = l1;
        Rasterizer.scanOffsets = ai;
        Rasterizer.aBoolean1464 = true;
        if (itemDef.stackable) {
            sprite2.maxWidth = 33;
        } else {
            sprite2.maxWidth = 32;
        }
        sprite2.maxHeight = j;
        return sprite2;
    }

    public static void itemDump() {
        try {
            System.out.println("Dumping item info...");
            FileWriter fw = new FileWriter(RuneLite.CACHE_DIR + "\\itemdump.txt");
            for (int ee = 10000; ee < 22000; ee++) {
                //ItemDef item = null;
                ItemDef item = ItemDef.forID(ee);
                fw.write("case " + ee + ":");
                fw.write(System.getProperty("line.separator"));
                fw.write("itemDef.name = \"" + item.name + "\";");
                fw.write(System.getProperty("line.separator"));
                fw.write("itemDef.actions = new String[" + item.actions.length + "]");
                fw.write(System.getProperty("line.separator"));
                for (int act = 0; act < item.actions.length; act++) {
                    if (item.actions[act] != null) {
                        fw.write("itemDef.actions[" + act + "] = \"" + item.actions[act] + "\";");
                        fw.write(System.getProperty("line.separator"));
                    }
                }
                fw.write("itemDef.modelID= " + item.modelID + ";");
                fw.write(System.getProperty("line.separator"));


                if (item.maleEquip1 > 0) {
                    fw.write("itemDef.maleEquip1= " + item.maleEquip1 + ";");
                    fw.write(System.getProperty("line.separator"));
                }
                if (item.maleEquip2 > 0) {
                    fw.write("itemDef.maleEquip2= " + item.maleEquip2 + ";");
                    fw.write(System.getProperty("line.separator"));
                }
                if (item.maleEquip3 > 0) {
                    fw.write("itemDef.maleEquip3= " + item.maleEquip3 + ";");
                    fw.write(System.getProperty("line.separator"));
                }

                if (item.femaleEquip1 > 0) {
                    fw.write("itemDef.femaleEquip1= " + item.femaleEquip1 + ";");
                    fw.write(System.getProperty("line.separator"));
                }

                if (item.femaleEquip2 > 0) {
                    fw.write("itemDef.femaleEquip2= " + item.femaleEquip2 + ";");
                    fw.write(System.getProperty("line.separator"));
                }

                if (item.femaleEquip3 > 0) {
                    fw.write("itemDef.femaleEquip3= " + item.femaleEquip3 + ";");
                    fw.write(System.getProperty("line.separator"));
                }


                fw.write("itemDef.modelZoom = " + item.modelZoom + ";");
                fw.write(System.getProperty("line.separator"));
                fw.write("itemDef.rotationX = " + item.rotationX + ";");
                fw.write(System.getProperty("line.separator"));
                fw.write("itemDef.rotationY = " + item.rotationY + ";");
                fw.write(System.getProperty("line.separator"));
                fw.write("itemDef.rotationZ = " + item.rotationZ + ";");
                fw.write(System.getProperty("line.separator"));
                fw.write("itemDef.modelOffsetX = " + item.modelOffsetX + ";");
                fw.write(System.getProperty("line.separator"));
                fw.write("itemDef.modelOffsetY = " + item.modelOffsetY + ";");
                fw.write(System.getProperty("line.separator"));
                fw.write("itemDef.description = \"" + item.description + "\";");


                for (int ogColor = 0; ogColor < 10; ogColor++) {
                    if (item.originalModelColor[ogColor] > 0) {
                        fw.write(System.getProperty("line.separator"));
                        fw.write("itemDef.originalModelColor[" + ogColor + "] = " + item.originalModelColor[ogColor] + ";");
                    }
                }

                for (int editColor = 0; editColor < 10; editColor++) {
                    if (item.editedModelColor[editColor] > 0) {
                        fw.write(System.getProperty("line.separator"));
                        fw.write("itemDef.editedModelColor[" + editColor + "] = " + item.editedModelColor[editColor] + ";");
                    }
                }


                fw.write(System.getProperty("line.separator"));
                fw.write("itemDef.groundActions = new String[" + item.groundActions.length + "];");
                fw.write(System.getProperty("line.separator"));
                for (int f = 0; f < item.groundActions.length; f++) {
                    if (item.groundActions[f] != null) {
                        fw.write("itemDef.groundActions[" + f + "] = \"" + item.groundActions[f] + "\";");
                    }
                }
                fw.write(System.getProperty("line.separator"));
                fw.write("itemDef.value = " + item.value + ";");
                fw.write(System.getProperty("line.separator"));
                fw.write("itemDef.team = " + item.team + ";");
                fw.write(System.getProperty("line.separator"));
                fw.write("break;");
                fw.write(System.getProperty("line.separator"));
                fw.write(System.getProperty("line.separator"));
            }
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean dialogueModelFetched(int j) {
        int k = maleDialogue;
        int l = maleDialogueModel;
        if (j == 1) {
            k = femaleDialogue;
            l = femaleDialogueModel;
        }
        if (k == -1) {
            return true;
        }
        boolean flag = Model.modelIsFetched(k, dataType);
        if (l != -1 && !Model.modelIsFetched(l, dataType)) {
            flag = false;
        }
        return flag;
    }

    public Model getDialogueModel(int gender) {
        int k = maleDialogue;
        int l = maleDialogueModel;
        if (gender == 1) {
            k = femaleDialogue;
            l = femaleDialogueModel;
        }
        if (k == -1) {
            return null;
        }
        Model model = Model.fetchModel(k, dataType);
        if (l != -1) {
            Model model_1 = Model.fetchModel(l, dataType);
            Model[] models = {model, model_1};
            model = new Model(2, models);
        }
        if (editedModelColor != null) {
            for (int i1 = 0; i1 < editedModelColor.length; i1++) {
                model.recolour(editedModelColor[i1], newModelColor[i1]);
            }
        }
        return model;
    }

    public boolean equipModelFetched(int gender, DataType dataType) {
        int fistModel = maleEquip1;
        int secondModel = maleEquip2;
        int thirdModel = maleEquip3;
        if (gender == 1) {
            fistModel = femaleEquip1;
            secondModel = femaleEquip2;
            thirdModel = femaleEquip3;
        }
        if (fistModel == -1) {
            return true;
        }
        boolean flag = Model.modelIsFetched(fistModel, dataType);
        if (secondModel != -1 && !Model.modelIsFetched(secondModel, dataType)) {
            flag = false;
        }
        if (thirdModel != -1 && !Model.modelIsFetched(thirdModel, dataType)) {
            flag = false;
        }
        return flag;
    }

    public Model getEquipModel(int gender) {
        int j = maleEquip1;
        int k = maleEquip2;
        int l = maleEquip3;
        if (gender == 1) {
            j = femaleEquip1;
            k = femaleEquip2;
            l = femaleEquip3;
        }
        if (j == -1) {
            return null;
        }
        Model model = Model.fetchModel(j, dataType);
        if (k != -1) {
            if (l != -1) {
                Model model_1 = Model.fetchModel(k, dataType);
                Model model_3 = Model.fetchModel(l, dataType);
                Model[] model_1s = {model, model_1, model_3};
                model = new Model(3, model_1s);
            } else {
                Model model_2 = Model.fetchModel(k, dataType);
                Model[] models = {model, model_2};
                model = new Model(2, models);
            }
        }
        //if (j == 62367)
        //model.translate(68, 7, -8);
        if (gender == 0 && maleYOffset != 0) {
            model.translate(0, maleYOffset, 0);
        } else if (gender == 1 && femaleYOffset != 0) {
            model.translate(0, femaleYOffset, 0);
        }
        if (editedModelColor != null) {
            for (int i1 = 0; i1 < editedModelColor.length; i1++) {
                model.recolour(editedModelColor[i1], newModelColor[i1]);
            }
        }
        return model;
    }

    public void setDefaults() {
        animateInventory = false;
        untradeable = false;
        modelID = 0;
        name = null;
        description = null;
        editedModelColor = null;
        newModelColor = null;
        modelZoom = 2000;
        rotationY = 0;
        rotationZ = 0;
        rotationX = 0;
        modelOffsetX = 0;
        modelOffset1 = 0;
        modelOffsetY = 0;
        stackable = false;
        value = 0;
        membersObject = false;
        groundActions = null;
        actions = null;
        maleEquip1 = -1;
        maleEquip2 = -1;
        maleYOffset = 0;
        maleXOffset = 0;
        femaleEquip1 = -1;
        femaleEquip2 = -1;
        femaleYOffset = 0;
        maleEquip3 = -1;
        femaleEquip3 = -1;
        maleDialogue = -1;
        maleDialogueModel = -1;
        femaleDialogue = -1;
        femaleDialogueModel = -1;
        stackIDs = null;
        stackAmounts = null;
        certID = -1;
        certTemplateID = -1;
        sizeX = 128;
        sizeY = 128;
        sizeZ = 128;
        shadow = 0;
        lightness = 0;
        team = 0;
        lendID = -1;
        lentItemID = -1;
        opcode140 = -1;
        opcode139 = -1;
        opcode148 = -1;
        opcode149 = -1;
    }

    void method2789(ItemDef var1, ItemDef var2) {
        modelID = var1.modelID;
        modelZoom = var1.modelZoom;
        rotationX = var1.rotationX;
        rotationY = var1.rotationY;
        modelOffsetX = var1.modelOffsetX;
        modelOffset1 = var1.modelOffset1;
        modelOffsetY = var1.modelOffsetY;
        editedModelColor = var2.editedModelColor;
        newModelColor = var2.newModelColor;
        //  originalTextureColors = var2.originalTextureColors;
        //	modifiedTextureColors = var2.modifiedTextureColors;
        name = var2.name;
        membersObject = var2.membersObject;
        stackable = var2.stackable;
        this.maleEquip1 = var2.maleEquip1;
        maleEquip2 = var2.maleEquip2;
        maleEquip3 = var2.maleEquip3;
        femaleEquip1 = var2.femaleEquip1;
        femaleEquip2 = var2.femaleEquip2;
        femaleEquip3 = var2.femaleEquip3;
        maleDialogue = var2.maleDialogue;
        maleDialogueModel = var2.maleDialogueModel;
        femaleDialogue = var2.femaleDialogue;
        femaleDialogueModel = var2.femaleDialogueModel;
        team = var2.team;
        groundActions = var2.groundActions;
        actions = new String[5];
        if (null != var2.actions) {
            for (int var4 = 0; var4 < 4; ++var4) {
                actions[var4] = var2.actions[var4];
            }
        }

        actions[4] = "Drop";
        value = 0;
    }

    void method2790(ItemDef var1, ItemDef var2) {
        modelID = var1.modelID * 1;
        modelZoom = 1 * var1.modelZoom;
        rotationX = var1.rotationX * 1;
        rotationY = var1.rotationY * 1;
        modelOffsetX = var1.modelOffsetX * 1;
        modelOffsetX = 1 * var1.modelOffsetX;
        modelOffsetY = var1.modelOffsetY * 1;
        editedModelColor = var1.editedModelColor;
        newModelColor = var1.newModelColor;
        originalTextureColors = var1.originalTextureColors;
        modifiedTextureColors = var1.modifiedTextureColors;
        stackable = var1.stackable;
        name = var2.name;
        value = 0;

    }

    private void readValues(Stream stream) {
        boolean osrs = stream == streamOSRS;

        do {
            int i = stream.readUnsignedByte();
            if (i == 0) {
                return;
            }
            if (i == 1) {
                modelID = stream.readUnsignedWord();
            } else if (i == 2) {
                name = stream.readString();
            } else if (i == 3) {
                description = stream.readString();
            } else if (i == 4) {
                modelZoom = stream.readUnsignedWord();
            } else if (i == 5) {
                rotationY = stream.readUnsignedWord();
            } else if (i == 6) {
                rotationX = stream.readUnsignedWord();
            } else if (i == 7) {
                modelOffset1 = stream.readUnsignedWord();
                if (modelOffset1 > 32767) {
                    modelOffset1 -= 0x10000;
                }
            } else if (i == 8) {
                modelOffsetY = stream.readUnsignedWord();
                if (modelOffsetY > 32767) {
                    modelOffsetY -= 0x10000;
                }
            } else if (i == 10) {
                stream.readUnsignedWord();
            } else if (i == 11) {
                stackable = true;
            } else if (i == 12) {
                value = osrs ? stream.readDWord() : stream.readUnsignedWord();
            } else if (i == 16) {
                membersObject = true;
            } else if (i == 23) {
                maleEquip1 = stream.readUnsignedWord();
                maleYOffset = stream.readSignedByte();
            } else if (i == 24) {
                maleEquip2 = stream.readUnsignedWord();
            } else if (i == 25) {
                femaleEquip1 = stream.readUnsignedWord();
                femaleYOffset = stream.readSignedByte();
            } else if (i == 26) {
                femaleEquip2 = stream.readUnsignedWord();
            } else if (i >= 30 && i < 35) {
                if (groundActions == null) {
                    groundActions = new String[5];
                }
                groundActions[i - 30] = stream.readString();
                if (groundActions[i - 30].equalsIgnoreCase("hidden")) {
                    groundActions[i - 30] = null;
                }
            } else if (i >= 35 && i < 40) {
                if (actions == null) {
                    actions = new String[5];
                }
                actions[i - 35] = stream.readString();
                if (actions[i - 35].equalsIgnoreCase("null")) {
                    actions[i - 35] = null;
                }
            } else if (i == 40) {
                int j = stream.readUnsignedByte();
                editedModelColor = new int[j];
                newModelColor = new int[j];
                for (int k = 0; k < j; k++) {
                    editedModelColor[k] = stream.readUnsignedWord();
                    newModelColor[k] = stream.readUnsignedWord();
                }
            } else if (i == 41) {
                int size = stream.readUnsignedByte();
                originalTextureColors = new short[size];
                modifiedTextureColors = new short[size];
                for (int index = 0; index < size; index++) {
                    originalTextureColors[index] = (short) stream.readUnsignedWord();
                    modifiedTextureColors[index] = (short) stream.readUnsignedWord();
                }
            } else if (i == 78) {
                maleEquip3 = stream.readUnsignedWord();
            } else if (i == 79) {
                femaleEquip3 = stream.readUnsignedWord();
            } else if (i == 90) {
                maleDialogue = stream.readUnsignedWord();
            } else if (i == 91) {
                femaleDialogue = stream.readUnsignedWord();
            } else if (i == 92) {
                maleDialogueModel = stream.readUnsignedWord();
            } else if (i == 93) {
                femaleDialogueModel = stream.readUnsignedWord();
            } else if (i == 95) {
                modelOffsetX = stream.readUnsignedWord();
            } else if (i == 97) {
                certID = osrs ? OSRS_ITEMS_OFFSET + stream.readUnsignedWord() : stream.readUnsignedWord();
            } else if (i == 98) {
                certTemplateID = osrs ? OSRS_ITEMS_OFFSET + stream.readUnsignedWord() : stream.readUnsignedWord();
            } else if (i >= 100 && i < 110) {
                if (stackIDs == null) {
                    stackIDs = new int[10];
                    stackAmounts = new int[10];
                }
                stackIDs[i - 100] = stream.readUnsignedWord() + (osrs ? OSRS_ITEMS_OFFSET : 0);
                stackAmounts[i - 100] = stream.readUnsignedWord();
            } else if (i == 110) {
                sizeX = stream.readUnsignedWord();
            } else if (i == 111) {
                sizeY = stream.readUnsignedWord();
            } else if (i == 112) {
                sizeZ = stream.readUnsignedWord();
            } else if (i == 113) {
                shadow = stream.readSignedByte();
            } else if (i == 114) {
                lightness = stream.readSignedByte() * 5;
            } else if (i == 115) {
                team = stream.readUnsignedByte();
            } else if (i == 116) {
                lendID = stream.readUnsignedWord();
            } else if (i == 117) {
                lentItemID = stream.readUnsignedWord();
            } else if (i == 139) {
                opcode139 = stream.readUnsignedWord();
            } else if (i == 140) {
                opcode140 = stream.readUnsignedWord();
            } else if (i == 148) {
                opcode148 = stream.readUnsignedWord();
            } else if (i == 149) {
                opcode149 = stream.readUnsignedWord();
            }
        } while (true);
    }

    public void toNote() {
        ItemDef itemDef = forID(certTemplateID);
        modelID = itemDef.modelID;
        modelZoom = itemDef.modelZoom;
        rotationY = itemDef.rotationY;
        rotationX = itemDef.rotationX;
        modelOffsetX = itemDef.modelOffsetX;
        modelOffset1 = itemDef.modelOffset1;
        modelOffsetY = itemDef.modelOffsetY;
        editedModelColor = itemDef.editedModelColor;
        newModelColor = itemDef.newModelColor;
        ItemDef itemDef_1 = forID(certID);
        name = itemDef_1.name;
        membersObject = itemDef_1.membersObject;
        value = itemDef_1.value;
        String s = "a";
        char c = itemDef_1.name.charAt(0);
        if (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
            s = "an";
        }
        description = ("Swap this note at any bank for " + s + " " + itemDef_1.name + ".");
        stackable = true;
    }

    private void toLend() {
        ItemDef itemDef = forID(lentItemID);
        actions = new String[5];
        modelID = itemDef.modelID;
        modelOffset1 = itemDef.modelOffset1;
        rotationX = itemDef.rotationX;
        modelOffsetY = itemDef.modelOffsetY;
        modelZoom = itemDef.modelZoom;
        rotationY = itemDef.rotationY;
        rotationZ = itemDef.rotationZ;
        modelOffsetX = itemDef.modelOffsetX;
        value = 0;
        ItemDef itemDef_1 = forID(lendID);
        maleDialogueModel = itemDef_1.maleDialogueModel;
        editedModelColor = itemDef_1.editedModelColor;
        maleEquip3 = itemDef_1.maleEquip3;
        maleEquip2 = itemDef_1.maleEquip2;
        femaleDialogueModel = itemDef_1.femaleDialogueModel;
        maleDialogue = itemDef_1.maleDialogue;
        groundActions = itemDef_1.groundActions;
        maleEquip1 = itemDef_1.maleEquip1;
        name = itemDef_1.name;
        femaleEquip1 = itemDef_1.femaleEquip1;
        membersObject = itemDef_1.membersObject;
        femaleDialogue = itemDef_1.femaleDialogue;
        femaleEquip2 = itemDef_1.femaleEquip2;
        femaleEquip3 = itemDef_1.femaleEquip3;
        newModelColor = itemDef_1.newModelColor;
        team = itemDef_1.team;
        if (itemDef_1.actions != null) {
            for (int i_33_ = 0; i_33_ < 4; i_33_++) {
                actions[i_33_] = itemDef_1.actions[i_33_];
            }
        }
        actions[4] = "Discard";
    }

    public Model getItemModelFinalised(int amount) {
        if (stackIDs != null && amount > 1) {
            int stackId = -1;
            for (int k = 0; k < 10; k++) {
                if (amount >= stackAmounts[k] && stackAmounts[k] != 0) {
                    stackId = stackIDs[k];
                }
            }
            if (stackId != -1) {
                return forID(stackId).getItemModelFinalised(1);
            }
        }
        Model model = (Model) modelCache.get(id);
        if (model != null) {
            return model;
        }
        model = Model.fetchModel(modelID, dataType);
        if (model == null) {
            return null;
        }
        if (sizeX != 128 || sizeY != 128 || sizeZ != 128) {
            model.scaleT(sizeX, sizeZ, sizeY);
        }
        if (editedModelColor != null) {
            for (int l = 0; l < editedModelColor.length; l++) {
                model.recolour(editedModelColor[l], newModelColor[l]);
            }
        }
        model.light(64 + shadow, 768 + lightness, -50, -10, -50, true);
        model.rendersWithinOneTile = true;
        modelCache.put(model, id);
        return model;
    }

    public Model getItemModel(int i) {
        if (stackIDs != null && i > 1) {
            int j = -1;
            for (int k = 0; k < 10; k++) {
                if (i >= stackAmounts[k] && stackAmounts[k] != 0) {
                    j = stackIDs[k];
                }
            }
            if (j != -1) {
                return forID(j).getItemModel(1);
            }
        }
        Model model = Model.fetchModel(modelID, dataType);
        if (model == null) {
            return null;
        }
        if (editedModelColor != null) {
            for (int l = 0; l < editedModelColor.length; l++) {
                model.recolour(editedModelColor[l], newModelColor[l]);
            }
        }
        return model;
    }

    public enum CustomItems {
        //PINK_DILDO(18351, 20, 20, 20, true), // 18983
        ;

        private int copy;
        private int inventory;
        private int female;
        private int male;
        private boolean weapon;
        private int[] editedModelColor;
        private int[] originalModelColor;
        private boolean copyDef;


        public int getCopy() {
            return copy;
        }

        public void setCopy(int copy) {
            this.copy = copy;
        }

        public int getInventory() {
            return inventory;
        }

        public void setInventory(int inventory) {
            this.inventory = inventory;
        }

        public int getFemale() {
            return female;
        }

        public void setFemale(int female) {
            this.female = female;
        }

        public int getMale() {
            return male;
        }

        public void setMale(int male) {
            this.male = male;
        }

        public boolean isWeapon() {
            return weapon;
        }

        public void setWeapon(boolean weapon) {
            this.weapon = weapon;
        }

        public int[] getEditedModelColor() {
            return editedModelColor;
        }

        public void setEditedModelColor(int[] editedModelColor) {
            this.editedModelColor = editedModelColor;
        }

        public int[] getOriginalModelColor() {
            return originalModelColor;
        }

        public void setOriginalModelColor(int[] originalModelColor) {
            this.originalModelColor = originalModelColor;
        }

        public boolean isCopyDef() {
            return copyDef;
        }

        public void setCopyDef(boolean copyDef) {
            this.copyDef = copyDef;
        }
    }

}
