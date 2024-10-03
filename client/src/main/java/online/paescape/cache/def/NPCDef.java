package online.paescape.cache.def;

import net.runelite.api.HeadIcon;
import net.runelite.api.IterableHashTable;
import net.runelite.rs.api.RSIterableNodeHashTable;
import net.runelite.rs.api.RSNPCComposition;
import online.paescape.Client;
import online.paescape.cache.CacheArchive;
import online.paescape.cache.config.VarBit;
import online.paescape.cache.media.Animation;
import online.paescape.collection.MemCache;
import online.paescape.media.FrameReader;
import online.paescape.media.animable.Model;
import online.paescape.net.Stream;
import online.paescape.util.Configuration;
import online.paescape.util.DataType;
import online.paescape.util.FileOperations;
import online.paescape.util.Signlink;

import java.io.FileWriter;

public final class NPCDef implements RSNPCComposition {

    public static final int OSRS_NPCS_OFFSET = 14_000;
    public static MemCache modelCacheOSRS = new MemCache(80);
    public static int NPCAMOUNT = 11599;
    public static int cacheIndex;
    public static Stream stream;
    public static int[] streamIndices;
    public static NPCDef[] cache;
    public static Client clientInstance;
    public static MemCache modelCache = new MemCache(30);
    private static int cacheIndexOSRS;
    private static Stream streamOSRS;
    private static int[] streamIndicesOSRS;
    private static NPCDef[] cacheOSRS;
    public int frontLight = 68;
    public int backLight = 820;
    public int rightLight = 0;
    public int middleLight = -1; // Cannot be 0
    public int leftLight = 0;
    public DataType dataType = DataType.REGULAR;
    public int turn90CCWAnimIndex;
    public int varbitId;
    public int turn180AnimIndex;
    public int varSettingsId;
    public int combatLevel;
    public String name;
    public String[] actions;
    public int walkAnim;
    public int runAnim;
    public byte squaresNeeded;
    public int[] destColours;
    public int[] npcHeadModels;
    public int headIcon;
    public int[] originalColours;
    public int standAnim;
    public long type;
    public int degreesToTurn;
    public int turn90CWAnimIndex;
    public boolean clickable;
    public int lightning;
    public int sizeY;
    public boolean drawMinimapDot;
    public int[] childrenIDs;
    public String description;
    public int sizeXZ;
    public int shadow;
    public boolean hasRenderPriority;
    public int[] models;
    public int id;

    public NPCDef() {
        turn90CCWAnimIndex = -1;
        varbitId = -1;
        turn180AnimIndex = -1;
        varSettingsId = -1;
        combatLevel = -1;
        walkAnim = -1;
        squaresNeeded = 1;
        headIcon = -1;
        standAnim = -1;
        type = -1L;
        degreesToTurn = 32;
        turn90CWAnimIndex = -1;
        clickable = true;
        sizeY = 128;
        drawMinimapDot = true;
        sizeXZ = 128;
        hasRenderPriority = false;
    }

    public static NPCDef forID(int i) {

        if (i >= OSRS_NPCS_OFFSET) {
            i -= OSRS_NPCS_OFFSET;

            for (int j = 0; j < 20; j++) {
                if (cacheOSRS[j].type == (long) i) {
                    return cacheOSRS[j];
                }
            }

            cacheIndexOSRS = (cacheIndexOSRS + 1) % 20;

            NPCDef npc = cacheOSRS[cacheIndexOSRS] = new NPCDef();

            if (i >= streamIndicesOSRS.length) {
                return null;
            }

            streamOSRS.currentOffset = streamIndicesOSRS[i];
            npc.type = OSRS_NPCS_OFFSET + i;

            npc.dataType = DataType.OLDSCHOOL;
            npc.readValuesOSRS(streamOSRS);

            switch (i) {
                //OSRS NPCS HERE

                case 1: //Pet Flax
                    npc.name = "Pet Flax";
                    npc.actions = new String[]{null, null, null, null, null};
                    npc.models[0] = 1668;
                    npc.sizeXZ = 100;
                    npc.sizeY = 100;
                    break;

                case 8052: //Voidstar
                    npc.name = "Voidstar";
                    npc.actions = new String[]{"Talk to", null, null, null, null};
                    break;

                case 9698: //Vyrewatch
                    npc.actions = new String[]{"Pickpocket", null, null, null, null};
                    break;

                case 3668: //King Vargas
                    npc.actions = new String[]{"Information", null, "Contribute", "Ranks", null};
                    break;

                case 5523:
                    npc.actions = new String[]{null, null, null, null, null};
                    npc.sizeXZ = 75;
                    npc.sizeY = 75;
                    npc.walkAnim = 6395;
                    npc.standAnim = 6395;
                    break;

                case 403: // Vannaka (Tutorial)
                    npc.actions = new String[]{"Tutorial", null, null, null, null};
                    break;

                case 405: //Pet Duradel
                    npc.name = "Pet Duradead";
                    npc.actions = new String[]{null, null, null, null, null};
                    npc.walkAnim = 2750;
                    npc.standAnim = 2995;
                    break;

                case 8025:
                    npc.actions = new String[]{null, null, null, null, null};
                    break;

                case 946:
                    npc.actions = new String[]{null, null, null, null, null};
                    npc.combatLevel = 0;
                    break;

                case 6797:
                    npc.actions[3] = "Set Master";
                    break;


                case 385:
                    npc.name = "Santa Claus";
                    //npc.combatLevel = 69;
                    npc.actions = new String[5];
                    npc.actions = new String[]{null, null, null, null, null};
                    //npc.npcHeadModels[0] = 28976; //santa mask

                    npc.models[0] = 28976; //santa mask
                    npc.models[3] = 28983; //santa jacket
                    npc.models[4] = 28981; //santa jacket
                    npc.models[5] = 28979; //santa pantaloons
                    npc.models[6] = 28978; //santa gloves
                    npc.models[7] = 28989; //santa boots

                    npc.walkAnim = 6395;
                    //npc.models[2] = 28976; //santa mask
                    //npc.models[3] = 28983; //santa jacket
                    //npc.models[4] = 28981; //santa jacket
                    //npc.models[5] = 28979; //santa pantaloons
                    //npc.models[6] = 28978; //santa gloves
                    //npc.models[7] = 28989; //santa boots
                    //npc.sizeXZ = 250;
                    //npc.sizeY = 250;
                    break;

                case 386:
                    npc.name = "Pet Santa";
                    //npc.combatLevel = 69;
                    npc.actions = new String[5];
                    npc.actions = new String[]{null, null, null, null, null};
                    //npc.npcHeadModels[0] = 28976; //santa mask

                    npc.models[0] = 28976; //santa mask
                    npc.models[3] = 28983; //santa jacket
                    npc.models[4] = 28981; //santa jacket
                    npc.models[5] = 28979; //santa pantaloons
                    npc.models[6] = 28978; //santa gloves
                    npc.models[7] = 28989; //santa boots

                    npc.walkAnim = 6395;

                    //npc.models[2] = 28976; //santa mask
                    //npc.models[3] = 28983; //santa jacket
                    //npc.models[4] = 28981; //santa jacket
                    //npc.models[5] = 28979; //santa pantaloons
                    //npc.models[6] = 28978; //santa gloves
                    //npc.models[7] = 28989; //santa boots
                    npc.sizeXZ = 60;
                    npc.sizeY = 60;
                    break;


                case 6668:
                case 7759:
                case 7351:
                case 7352:
                case 7353:
                case 7354:
                case 7368:
                case 7439:
                case 6717:
                case 6722:
                case 7519:
                case 409:
                case 2253:
                case 9398:
                case 5780:
                case 8336:
                case 21232:
                case 22729:
                case 8729:
                    npc.actions = new String[]{null, null, null, null, null};
                    break;

                case 6079:
                    npc.walkAnim = 189;
                    npc.standAnim = 189;
                    npc.actions = new String[]{null, null, null, null, null};
                    break;
            }


            return npc;
        }
        for (int j = 0; j < 20; j++)
            if (cache[j].type == (long) i)
                return cache[j];
        cacheIndex = (cacheIndex + 1) % 20;
        NPCDef npc = cache[cacheIndex] = new NPCDef();
        if (i >= streamIndices.length)
            return null;
        stream.currentOffset = streamIndices[i];
        npc.type = i;
        npc.readValues(stream);


        if (npc.name != null && npc.name.toLowerCase().contains("bank")) {
            if (npc.actions != null) {
                for (int l = 0; l < npc.actions.length; l++) {
                    if (npc.actions[l] != null && npc.actions[l].equalsIgnoreCase("Collect"))
                        npc.actions[l] = null;
                }
            }
        }
        npc.id = i;
        switch (i) {

            case 4904: // Smithing Apprentice
                npc.actions = new String[]{"Smithing Basics", null, null, null, null};
                break;

            case 2663: //Pet Leprechaun
                npc.actions = new String[]{null, null, null, null, null};
                break;

            case 1835: //Pet Easter Bunny
                npc.actions = new String[]{null, null, null, null, null};
                break;

            case 3021: //Pet Leprechaun
                npc.name = "Pet Leprechaun";
                npc.actions = new String[]{null, null, null, null, null};
                break;

            case 22061:
                npc.walkAnim = 29209;
                break;

            case 4225:
                npc.name = "Crazy Archaeologist";
                npc.models = new int[]{6364, 203, 250, 292, 3707, 173, 176, 254, 185, 556};
                npc.actions = new String[]{null, "Attack", null, null, null};
                npc.originalColours = new int[]{43072, 3131, 3127, 3123, 25238, 5018};
                npc.destColours = new int[]{12, 10388, 10514, 10638, 10388, 35619};
                npc.standAnim = 808;
                npc.walkAnim = 819;
                npc.combatLevel = 204;
                break;
            case 3:
                npc.name = "Nightmare";
                npc.combatLevel = 1200;
                npc.standAnim = 1500;
                npc.walkAnim = 1501;
                npc.actions = new String[5];
                npc.actions = new String[]{null, "Attack", null, null, null};
                npc.models[0] = 91227;
                break;
            case 15:
                npc.name = "Klar";
                npc.combatLevel = 375;
                npc.standAnim = 5538;
                npc.walkAnim = 5539;
                npc.actions = new String[5];
                npc.actions = new String[]{null, "Attack", null, null, null};
                npc.models[0] = 21152;
                break;
            case 16:
                npc.name = "Sanita";
                npc.combatLevel = 375;
                npc.standAnim = 5538;
                npc.walkAnim = 5539;
                npc.actions = new String[5];
                npc.actions = new String[]{null, "Attack", null, null, null};
                npc.models[0] = 21152;
                break;
            case 17:
                npc.name = "Timore";
                npc.combatLevel = 375;
                npc.standAnim = 5538;
                npc.walkAnim = 5539;
                npc.actions = new String[5];
                npc.actions = new String[]{null, "Attack", null, null, null};
                npc.models[0] = 21152;
                break;
            case 18:
                npc.name = "Erwach";
                npc.combatLevel = 375;
                npc.standAnim = 5538;
                npc.walkAnim = 5539;
                npc.actions = new String[5];
                npc.actions = new String[]{null, "Attack", null, null, null};
                npc.models[0] = 21152;
                break;
            case 4:
                npc.name = "TzKal-Zuk";
                npc.combatLevel = 1400;
                npc.actions = new String[5];
                npc.actions = new String[]{null, "Attack", null, null, null};
                npc.standAnim = 7564;
                npc.walkAnim = 7564;
                npc.models[0] = 34586;
                npc.models[1] = 34587;
                break;
            case 553:
                npc.actions = new String[5];
                npc.actions = new String[]{"Teleport", null, null, null, null};
                break;
            case 13458:
                npc.sizeXZ = 300;
                npc.sizeY = 300;
                break;
		/*case 14:
			npc.name = "Bad Santa";
			npc.combatLevel = 420;
			npc.actions = new String[] {null, "Attack",  null, null, null};
			npc.models = new int[9];
			// Santa outfit //Chest-45199 sleeves-45197 boots-45198 gloves-45196 Legs-45195 ice ammy-45201
			npc.models[0] = 45197; //Santa sleeves
			npc.models[1] = 366; //Santa hat
			npc.models[2] = 45199; //Santa chest
			npc.models[3] = 45195; //Santa legs
			npc.models[4] = 45201; //Ice ammy
			npc.models[5] = 45196; //Santa gloves
			npc.models[6] = 45198; //Santa boots;
			npc.models[7] = 0; //Npc's Darkness(Shadow)
			npc.models[8] = 35371; //Scythe
			npc.originalColours = new int[] { 933};
			npc.destColours = new int[] { 6020};
			npc.sizeXZ = 250;
			npc.sizeY = 250;
		break;*/
            case 3334:
                npc.name = "WildyWyrm";
                npc.models = new int[]{63604};
                //npc.boundDim = 1;
                npc.standAnim = 12790;
                npc.walkAnim = 12790;
                npc.combatLevel = 382;
                npc.actions = new String[5];
                npc.actions = new String[]{null, "Attack", null, null, null};
                npc.sizeXZ = 225;
                npc.sizeY = 200;
                //npc.sizeXZ = 35;
                //npc.sizeY = 75;
                break;
            case 152:
                npc.models = new int[1];
                npc.models[0] = 2781;
                npc.name = "Lucario";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.combatLevel = 170;
                npc.standAnim = 3989;
                npc.walkAnim = 3989;
                npc.sizeY = 40;
                npc.sizeXZ = 40;
                npc.squaresNeeded = 1;
                npc.drawMinimapDot = false;
                break;
            case 1:
                npc.name = "Poison";
                npc.actions = new String[]{null, null, null, null, null};
                npc.sizeXZ = 1;
                npc.sizeY = 1;
                npc.drawMinimapDot = false;
                break;
            case 0:
                npc.name = " ";
                npc.actions = new String[]{null, null, null, null, null};
                npc.sizeXZ = 1;
                npc.sizeY = 1;
                npc.drawMinimapDot = false;
                break;
            case 1595: //man
                npc.actions = new String[]{"@or2@Metal Dragons", null, null, null, null};
                break;
            case 2: //man
                npc.actions = new String[]{"Pickpocket", null, null, null, null};
                break;
            case 23: //Ardy Knight
                npc.actions = new String[]{"Pickpocket", null, null, null, null};
                break;
            case 21: //Hero
                npc.actions = new String[]{"Pickpocket", null, null, null, null};
                break;
            case 2234: //master farmer
                npc.actions = new String[]{"Pickpocket", null, null, null, null};
                break;
            case 5604: //Elfinlocks
                npc.actions = new String[]{"Pickpocket", null, null, null, null};
                break;
            case 2595: //TzHaar-Pik
                npc.name = "TzHaar-Pik";
                npc.actions = new String[]{"Pickpocket", null, null, null, null};
                break;


            case 2000:
                npc.models = new int[2];
                npc.models[0] = 28294;
                npc.models[1] = 28295;
                npc.name = "Venenatis";
                npc.actions = new String[]{null, "Attack", null, null, null};
                npc.sizeXZ = 200;
                npc.sizeY = 200;
                NPCDef ven = forID(60);
                npc.standAnim = ven.standAnim;
                npc.walkAnim = ven.walkAnim;
                npc.combatLevel = 464;
                npc.squaresNeeded = 3;
                break;

            case 2042://turqoise

                npc.name = "Zulrah";
                npc.actions = new String[]{null, "Attack", null, null, null};
                npc.models = new int[1];
                npc.models[0] = 14407;
                npc.standAnim = 5070;
                npc.walkAnim = 5070;
                npc.combatLevel = 725;
                npc.sizeXZ = 100;
                npc.sizeY = 100;
                break;
            case 2043://regular
                npc.name = "Zulrah";
                npc.actions = new String[]{null, "Attack", null, null, null};
                npc.models = new int[1];
                npc.models[0] = 14408;
                npc.standAnim = 5070;
                npc.walkAnim = 5070;
                npc.combatLevel = 725;
                npc.sizeXZ = 100;
                npc.sizeY = 100;
                break;
            case 2044://melee
                npc.name = "Zulrah";
                npc.actions = new String[]{null, "Attack", null, null, null};
                npc.models = new int[1];
                npc.models[0] = 14409;
                npc.standAnim = 5070;
                npc.walkAnim = 5070;
                npc.combatLevel = 725;
                npc.sizeXZ = 100;
                npc.sizeY = 100;
                break;
            case 2001:
                npc.models = new int[1];
                npc.models[0] = 28293;
                npc.name = "Scorpia";
                npc.actions = new String[]{null, "Attack", null, null, null};
                NPCDef scor = forID(107);
                npc.standAnim = scor.standAnim;
                npc.walkAnim = scor.walkAnim;
                npc.combatLevel = 464;
                npc.squaresNeeded = 3;
                break;
            case 7286:
                npc.name = "Skotizo";
                npc.description = "Badass from the depths of hell";
                npc.combatLevel = 321;
                NPCDef skotizo = forID(4698);
                npc.standAnim = skotizo.standAnim;
                npc.walkAnim = skotizo.walkAnim;
                npc.actions = new String[5];
                npc.actions[1] = "Attack";
                npc.models = new int[1];
                npc.models[0] = 31653;
                npc.sizeXZ = 80; //resize if you wish hes a bit small cause personal preference
                npc.sizeY = 80; // resize
                npc.squaresNeeded = 3;
                break;
            case 7287:
                npc.name = "Nemesis";
                npc.description = "A demon from the depths of hell";
                npc.combatLevel = 1350;
                NPCDef nightmare = forID(4698);
                npc.standAnim = nightmare.standAnim;
                npc.walkAnim = nightmare.walkAnim;
                npc.actions = new String[5];
                npc.actions[1] = "Attack";
                npc.models = new int[1];
                npc.models[0] = 31653;
                npc.originalColours = new int[]{50066, 49946, 49707, 49088, 47382, 49606, 49690, 49827, 49715, 49600, 50112, 49587};
                npc.destColours = new int[]{940, 939, 938, 937, 936, 935, 934, 8, 932, 931, 930, 929};
                npc.sizeXZ = 80; //resize if you wish hes a bit small cause personal preference
                npc.sizeY = 80; // resize
                npc.squaresNeeded = 3;
                break;
            case 1580:
                npc.name = "Alucard the Butcher";
                npc.actions = new String[5];
                npc.actions[1] = "Attack";
                npc.combatLevel = 666;
                npc.sizeXZ = 300;
                npc.sizeY = 300;
                //npc.models = new int[1];
                //npc.models[0] = 35371; //Scythe
                break;
            case 1581:
                npc.name = "Alucard the Butcher*";
                npc.actions = new String[5];
                npc.actions[1] = "Attack";
                npc.combatLevel = 666;
                npc.sizeXZ = 300;
                npc.sizeY = 300;
                //npc.models = new int[1];
                //npc.models[0] = 35371; //Scythe
                break;

            case 6766:
                npc.name = "Lizardman shaman";
                npc.description = "It's a lizardman.";
                npc.combatLevel = 150;
                npc.walkAnim = 7195;
                npc.standAnim = 7191;
                npc.actions = new String[5];
                npc.actions[1] = "Attack";
                npc.models = new int[1];
                npc.models[0] = 4039;
                npc.sizeXZ = 108;
                npc.sizeY = 108;
                npc.squaresNeeded = 3;
                break;
            case 5886:
                npc.name = "Abyssal Sire";
                npc.description = "It's an abyssal sire.";
                npc.combatLevel = 350;
                npc.walkAnim = 4534;
                npc.standAnim = 4533;
                npc.actions = new String[5];
                npc.actions[1] = "Attack";
                npc.models = new int[1];
                npc.models[0] = 29477;
                npc.sizeXZ = 108;
                npc.sizeY = 108;
                npc.squaresNeeded = 6;
                break;
            case 499:
                npc.name = "Thermonuclear smoke devil";
                npc.description = "It looks like its glowing";
                npc.combatLevel = 301;
                npc.walkAnim = 1828;
                npc.standAnim = 1829;
                npc.actions = new String[5];
                npc.actions[1] = "Attack";
                npc.models = new int[1];
                npc.models[0] = 28442;
                npc.sizeXZ = 240;
                npc.sizeY = 240;
                npc.squaresNeeded = 4;
                break;
            case 1999:
                npc.models = new int[2];
                npc.name = "Cerberus";
                npc.models[1] = 29270;
                npc.combatLevel = 318;
                npc.standAnim = 4484;
                npc.walkAnim = 4488;
                npc.actions = new String[5];
                npc.originalColours = new int[]{29270};
                npc.actions = new String[]{null, "Attack", null, null, null};
                npc.sizeXZ = 100;
                npc.sizeY = 100;
                break;
            case 2009:
                npc.name = "Callisto";
                npc.models = new int[]{28298};
                npc.actions = new String[]{null, "Attack", null, null, null};
                npc.combatLevel = 470;
                NPCDef callisto = forID(105);
                npc.standAnim = callisto.standAnim;
                npc.walkAnim = callisto.walkAnim;
                npc.actions = callisto.actions;
                npc.sizeXZ = npc.sizeY = 110;
                npc.squaresNeeded = 4;
                break;
            case 2006:
                npc.models = new int[1];
                npc.models[0] = 28300;
                npc.name = "Vet'ion";
                npc.actions = new String[]{null, "Attack", null, null, null};
                NPCDef vet = forID(90);
                npc.standAnim = vet.standAnim;
                npc.walkAnim = vet.walkAnim;
                npc.combatLevel = 464;
                break;
            case 2005:
                npc.models = new int[1];
                npc.models[0] = 28231;
                npc.name = "Kraken";
                npc.actions = new String[]{null, "Attack", null, null, null};
                NPCDef eld = forID(3847);
                npc.combatLevel = 291;
                npc.standAnim = 3989;
                npc.walkAnim = eld.walkAnim;
                npc.sizeXZ = npc.sizeY = 130;
                npc.squaresNeeded = 4;
                break;
            case 2004:
                npc.models = new int[1];
                npc.models[0] = 28231;
                npc.name = "Cave kraken";
                npc.actions = new String[]{null, "Attack", null, null, null};
                NPCDef cave = forID(3847);
                npc.models = new int[1];
                npc.models[0] = 28233;
                npc.combatLevel = 127;
                npc.standAnim = 3989;
                npc.walkAnim = cave.walkAnim;
                npc.sizeXZ = npc.sizeY = 42;
                break;
            case 2917:
                npc.actions = new String[]{"Talk-to", null, "Events", "Seasonal Shop", null};
                break;
            case 5080:
                npc.description = "I wonder what happens if I throw it?";
                npc.actions = new String[]{"Catch", null, null, null, null};
                break;
            case 457:
                npc.name = "Ghost Town Citizen";
                npc.actions = new String[]{"Talk-to", null, "Teleport", null, null};
                break;
            case 273:
                npc.name = "Untradeables Shop";
                npc.actions = new String[]{"Trade", null, null, null, null};
                break;
            case 263:
                npc.name = "Extreme Donator Shop";
                npc.actions = new String[]{"Trade", null, null, null, null};
                break;
            case 543:
                npc.name = "Decanter";
                break;
            case 4902:
                npc.name = "Expert Miner";
                npc.actions = new String[]{"Talk-To", null, "Trade", null, null};
                break;
            case 5417:
                npc.combatLevel = 210;
                break;
            case 5418:
                npc.combatLevel = 90;
                break;
            case 6715:
                npc.combatLevel = 91;
                break;
            case 6716:
                npc.combatLevel = 128;
                break;
            case 6701:
                npc.combatLevel = 173;
                break;
            case 6725:
                npc.combatLevel = 224;
                break;
            case 6691:
                npc.squaresNeeded = 2;
                npc.combatLevel = 301;
                break;
            case 212:
                npc.name = "Donator Shop";
                npc.actions = new String[]{"View Shop 1", null, "View Shop 2", null, null};
                break;
            case 741:
                npc.name = "Donator Shop 2";
                break;
            case 2998:
                npc.name = "Gambler";
                npc.actions = new String[]{"Trade", null, null, null, null};
                break;
            case 2633:
                npc.name = "Trivia Point Shop";
                break;
            case 8710:
            case 8707:
            case 8706:
            case 8705:
                npc.name = "Musician";
                npc.actions = new String[]{"Listen-to", null, null, null, null};
                break;

            case 5103:
                npc.actions = new String[]{"Spear", null, null, null, null};
                break;
            case 5104:
                npc.actions = new String[]{"Spear", null, null, null, null};
                break;
            case 5105:
                npc.actions = new String[]{"Spear", null, null, null, null};
                break;

            case 648:
                npc.actions = new String[]{"Skillcape", null, "Skillcape (t)", "Vote Shop", null};
                break;

            case 947:
                npc.name = "Trading Post Manager";
                npc.actions = new String[]{"Talk-to", null, "View Shops", "My Shop", null};
                break;
            case 9939:
                npc.combatLevel = 607;
                npc.sizeXZ = 250;
                npc.sizeY = 250;
                break;
            case 251:
                npc.actions = new String[]{"Rare Candy Shop", null, null, null, null};
                break;

            case 688:
                npc.name = "Archer";
                break;
            case 3151:
                npc.name = "@whi@Skeleton";
                npc.combatLevel = 420;
                npc.sizeXZ = 250;
                npc.sizeY = 250;
                npc.squaresNeeded = 1;
                npc.actions = new String[]{null, "Attack", null, null, null};
                break;
            case 4540:
                npc.name = "Glacio";
                npc.combatLevel = 500;
                npc.sizeXZ = 250;
                npc.sizeY = 250;
                break;
            case 3101:
                npc.sizeY = npc.sizeXZ = 80;
                npc.squaresNeeded = 1;
                npc.actions = new String[]{"Talk-to", null, "Start", "Rewards", null};
                break;
            case 6222:
                npc.name = "Kree'arra";
                npc.squaresNeeded = 5;
                npc.standAnim = 6972;
                npc.walkAnim = 6973;
                npc.actions = new String[]{null, "Attack", null, null, null};
                npc.sizeY = npc.sizeXZ = 110;
                break;
            case 6203:
                npc.models = new int[]{27768, 27773, 27764, 27765, 27770};
                npc.name = "K'ril Tsutsaroth";
                npc.squaresNeeded = 5;
                npc.standAnim = 6943;
                npc.walkAnim = 6942;
                npc.actions = new String[]{null, "Attack", null, null, null};
                npc.sizeY = npc.sizeXZ = 110;
                break;
            case 1610:
            case 491:
            case 10216:
                npc.actions = new String[]{null, "Attack", null, null, null};
                break;
            case 7969:
                npc.actions = new String[]{"Talk-to", null, "Trade", null, null};
                break;
            case 1382:
                npc.name = "Glacor";
                npc.models = new int[]{58940};
                npc.squaresNeeded = 3;
                //	npc.anInt86 = 475;
                npc.sizeXZ = npc.sizeY = 180;
                npc.standAnim = 10869;
                npc.walkAnim = 10867;
                npc.actions = new String[]{null, "Attack", null, null, null};
                npc.combatLevel = 123;
                npc.drawMinimapDot = true;
                npc.combatLevel = 188;
                break;
			/*case 1383:
			npc.name = "Unstable glacyte";
			npc.models = new int[]{58942};
			npc.standAnim = 10867;
			npc.walkAnim = 10901;
			npc.actions = new String[]{null, "Attack", null, null, null};
			npc.combatLevel = 101;
			npc.drawMinimapDot = false;
			break;
		case 1384:
			npc.name = "Sapping glacyte";
			npc.models = new int[]{58939};
			npc.standAnim = 10867;
			npc.walkAnim = 10901;
			npc.actions = new String[]{null, "Attack", null, null, null};
			npc.combatLevel = 101;
			npc.drawMinimapDot = true;
			break;
		case 1385:
			npc.name = "Enduring glacyte";
			npc.models = new int[]{58937};
			npc.standAnim = 10867;
			npc.walkAnim = 10901;
			npc.actions = new String[]{null, "Attack", null, null, null};
			npc.combatLevel = 101;
			npc.drawMinimapDot = true;
			break;*/
            case 4249:
                npc.name = "Gambler";
                break;
            case 6970:
                npc.actions = new String[]{"Trade", null, "Exchange Shards", null, null};
                break;
            case 4657:
                npc.actions = new String[]{"Talk-to", null, "Check Total", "Teleport", null};
                break;
            case 364:
                npc.actions = new String[]{"Talk-to", null, "Donator Shop", "Loyalty Shop", null};
                break;
            case 8591:
                npc.actions = new String[]{"Talk-to", null, "Trade", null, null};
                break;
            case 316:
                npc.actions = new String[]{"Shrimp/Anchovy", null, "Herring/Sardine", null, null};
                npc.sizeXZ = 25;
                npc.sizeY = 25;
                break;
            case 315:
                npc.sizeXZ = 25;
                npc.sizeY = 25;
                break;
            case 309:
                npc.actions = new String[]{"Anglerfish", null, null, null, null};
                npc.sizeXZ = 25;
                npc.sizeY = 25;
                break;
            case 310:
                npc.sizeXZ = 25;
                npc.sizeY = 25;
                break;
            case 314:
                npc.sizeXZ = 25;
                npc.sizeY = 25;
                break;
            case 312:
                npc.actions = new String[]{"Lobster", null, "Swordfish/Tuna", null, null};
                npc.sizeXZ = 25;
                npc.sizeY = 25;
                break;
            case 313:
                npc.actions = new String[]{"Cod/Bass", null, "Shark", null, null};
                npc.sizeXZ = 25;
                npc.sizeY = 25;
                break;
            case 318:
                npc.actions = new String[]{"Monkfish", null, "Trout/Salmon", null, null};
                npc.sizeXZ = 25;
                npc.sizeY = 25;
                break;
            case 805:
                npc.actions = new String[]{"Trade", null, "Tan hide", null, null};
                break;
            case 461:
            case 844:
            case 650:
            case 5112:
            case 3789:
            case 802:
            case 520:
            case 521:
            case 11226:
                npc.actions = new String[]{"Trade", null, null, null, null};
                break;
            case 8022:
            case 8028:
                String color = i == 8022 ? "Yellow" : "Green";
                npc.name = "" + color + " energy source";
                npc.actions = new String[]{"Siphon", null, null, null, null};
                break;
            case 8444:
                npc.actions = new String[5];
                npc.actions[0] = "Trade";
                break;
            case 2579:
                npc.name = "Max";
                npc.description = "One of PaeScape's veterans.";
                npc.combatLevel = 200;
                npc.actions = new String[5];
                npc.actions[0] = "Talk-to";
                npc.actions[2] = "Trade";
                npc.models = new int[8];
                npc.models[0] = 65289;
                npc.models[1] = 62746;
                npc.models[2] = 62743;
                npc.models[3] = 65305;
                npc.models[4] = 13307;
                npc.models[5] = 27738;
                npc.models[6] = 20147;
                npc.models[7] = 252;
                npc.standAnim = 808;
                npc.walkAnim = 819;
                npc.npcHeadModels = NPCDef.forID(517).npcHeadModels;
                break;
            case 6830:
            case 6841:
            case 6796:
            case 7331:
            case 6831:
            case 7361:
            case 6847:
            case 6872:
            case 7353:
            case 6835:
            case 6845:
            case 6808:
            case 7370:
            case 7333:
            case 7351:
            case 7367:
            case 6853:
            case 6855:
            case 6857:
            case 6859:
            case 6861:
            case 6863:
            case 9481:
            case 6827:
            case 6889:
            case 6813:
            case 6817:
            case 7372:
            case 6839:
            case 8575:
            case 7345:
            case 6799:
            case 7335:
            case 7347:
            case 6800:
            case 9488:
            case 6804:
            case 6822:
            case 6849:
            case 7355:
            case 7357:
            case 7359:
            case 7341:
            case 7329:
            case 7339:
            case 7349:
            case 7375:
            case 7343:
            case 6820:
            case 6865:
            case 6809:
            case 7363:
            case 7337:
            case 7365:
            case 6991:
            case 6992:
            case 6869:
            case 6818:
            case 6843:
            case 6823:
            case 7377:
            case 6887:
            case 6885:
            case 6883:
            case 6881:
            case 6879:
            case 6877:
            case 6875:
            case 6833:
            case 6851:
            case 5079:
            case 6824:
                npc.actions = new String[]{null, null, null, null, null};
                break;
            case 6806: // thorny snail
            case 6807:
            case 6994: // spirit kalphite
            case 6995:
            case 6867: // bull ant
            case 6868:
            case 6794: // spirit terrorbird
            case 6795:
            case 6815: // war tortoise
            case 6816:
            case 6874:// pack yak
            case 6873: // pack yak
            case 3594: // yak
            case 3590: // war tortoise
            case 3596: // terrorbird
                npc.actions = new String[]{"Store", null, null, null, null};
                break;
            case 548:
                npc.actions = new String[]{"Trade", null, null, null, null};
                break;
            case 3299:
            case 437:
                npc.actions = new String[]{"Trade", null, null, null, null};
                break;
            case 1265:
            case 1267:
            case 8459:
                npc.drawMinimapDot = true;
                break;
            case 961:
                npc.actions = new String[]{null, null, "Buy Consumables", "Restore Stats", null};
                npc.name = "Healer";
                break;
            case 705:
                npc.actions = new String[]{null, null, "Buy Armour", "Buy Weapons", "Buy Jewelries"};
                npc.name = "Warrior";
                break;
            case 1861:
                npc.actions = new String[]{null, null, "Buy Equipment", "Buy Ammunition", null};
                npc.name = "Archer";
                break;
            case 2437:
                npc.actions = new String[]{"Travel-Waterbirth", null, null, null, null};
                break;
            case 946:
                npc.actions = new String[]{null, null, "Buy Equipment", "Buy Runes", null};
                npc.name = "Mage";
                break;
            case 2292:
                npc.actions = new String[]{"Trade", null, null, null, null};
                npc.name = "Merchant";
                break;
            case 2676:
                npc.actions = new String[]{"Makeover", null, null, null, null};
                break;
            case 494:
            case 1360:
                npc.actions = new String[]{"Talk-to", null, null, null, null};
                break;
            case 659:
                npc.actions = new String[]{"Talk-to", null, null, null, null};
                break;
            case 1685:
                npc.name = "Specialty Store";
                npc.actions = new String[]{"PaePoints", null, "Coins", "Prestige", null};
                break;


            case 289:
                npc.name = "Corporeal beast*";
                npc.actions = new String[5];
                npc.combatLevel = 1000;
                npc.actions = new String[]{null, "Attack", null, null, null};
                npc.models = new int[]{40955};
                npc.combatLevel = 785;
                npc.standAnim = 10056;
                npc.walkAnim = 10055;
                npc.sizeY = 150;
                npc.sizeXZ = 150;
                npc.squaresNeeded = 2;
                break;

            case 5384:
                npc.models = new int[4];
                npc.models[0] = 24575;
                npc.models[1] = 24584;
                npc.models[2] = 24580;
                npc.models[3] = 17423;
                npc.originalColours = new int[]{-27364, -27359, 27479, 22233, 27466, 27471};
                npc.destColours = new int[]{-22248, -22111, -22233, -22225, -22219, -22225};
                npc.name = "Pet Mithril Dragon";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.combatLevel = 304;
                npc.standAnim = 90;
                npc.walkAnim = 79;
                //npc.sizeY = 40;
                //npc.sizeXZ = 40;
                //npc.squaresNeeded = 1;
                npc.drawMinimapDot = false;
                break;

            case 409:
                npc.actions = new String[5];
                npc.actions[0] = null;
                break;

            case 2253:
                npc.actions = new String[5];
                npc.actions[0] = null;
                break;

            case 2082:
                npc.name = "@cya@Tryout";
                npc.models = new int[9];
                npc.models[0] = 65305; //veteran cape
                npc.models[1] = 91250; //nezzy helm (enriched)
                npc.models[2] = 27732; //Bandos Chestplate (enriched)
                npc.models[3] = 27739; //Bandos Tassets (enriched)
                npc.models[4] = 27748; //Bandos Chestplate (enriched)
                npc.models[5] = 13307; //barrows gloves (enriched)
                npc.models[6] = 27737; //Brimstone Boots (enriched)
                npc.models[7] = 0; //Npc's Darkness(Shadow)
                npc.models[8] = 0; //staff of light
                npc.originalColours = new int[]{8384, 8367, 8375, 9523, 9515, 9523, 163, 10291, 10394, 8656, 8625, 8635, 8644, 8646, 55, 68, 72, 76, 80, 35, 119};
                npc.destColours = new int[]{123770, 123770, 123770, 123770, 123770,123770, 123770, 123770, 123768, 123774, 123770, 123770, 123770, 123770, 8340, 8344, 8348, 8352, 8356, 8320, 123770};
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.sizeXZ = 70;
                npc.sizeY = 70;
                break;

		/*case 8:
			npc.name = "Zerker Hybrid";
			npc.combatLevel = 99;
			npc.actions = new String[] {null, "Attack", null, null, null};
			npc.models = new int[9];
			npc.models[0] = 9638; //fire cape
			npc.models[1] = 21873; //nezzy helm
			npc.models[2] = 164; //rune platebody
			npc.models[3] = 4844; //mystic robe bottoms
			npc.models[4] = 306; //rune platebody
			npc.models[5] = 13307; //barrows gloves
			npc.models[6] = 4954; //Npc's Boots;
			npc.models[7] = 0; //Npc's Darkness(Shadow)
			npc.models[8] = 34508; //staff of light
			npc.originalColours = new int[] { 61, 41, 24}; //61 - chest, 41 - shoulders/legs, 24 - shoulder trim
			npc.destColours = new int[] { 36133, 36133, 36133};
			npc.description = "A fearsome PKer.";
			break;*/
		/*case 9:
			npc.name = "Zerker";
			npc.combatLevel = 99;
			npc.actions = new String[] {null, "Attack", null, null, null};
			npc.models = new int[9];
			npc.models[0] = 9638; //fire cape
			npc.models[1] = 21873; //nezzy helm
			npc.models[2] = 164; //rune platebody
			npc.models[3] = 268; //rune platelegs
			npc.models[4] = 306; //rune platebody
			npc.models[5] = 13307; //barrows gloves
			npc.models[6] = 4954; //Npc's Boots;
			npc.models[7] = 0; //Npc's Darkness(Shadow)
			npc.models[8] = 5409; //Whip
			npc.originalColours = new int[] { 61, 41, 24}; //61 - chest, 41 - shoulders/legs, 24 - shoulder trim
			npc.destColours = new int[] { 36133, 36133, 36133};
			npc.description = "A fearsome PKer.";
			break;*/
		/*case 10:
			npc.name = "Zerker Hybrid";
			npc.combatLevel = 99;
			npc.actions = new String[] {null, "Attack", null, null, null};
			npc.models = new int[8];
			npc.models[0] = 9638; //fire cape
			npc.models[1] = 21873; //nezzy helm
			npc.models[2] = 164; //rune platebody
			npc.models[3] = 4844; //mystic robe bottoms
			npc.models[4] = 306; //rune platebody
			npc.models[5] = 13307; //barrows gloves
			npc.models[6] = 4954; //Npc's Boots;
			//npc.models[7] = 0; //Npc's Darkness(Shadow)
			npc.models[7] = 27731; //Armadyl Godsword
			npc.originalColours = new int[] { 61, 41, 24}; //61 - chest, 41 - shoulders/legs, 24 - shoulder trim
			npc.destColours = new int[] { 36133, 36133, 36133};
			npc.description = "A fearsome PKer.";
			break;*/
		/*case 11:
			npc.name = "Zerker";
			npc.combatLevel = 99;
			npc.actions = new String[] {null, "Attack", null, null, null};
			npc.models = new int[8];
			npc.models[0] = 9638; //fire cape
			npc.models[1] = 21873; //nezzy helm
			npc.models[2] = 164; //rune platebody
			npc.models[3] = 268; //rune platelegs
			npc.models[4] = 306; //rune platebody
			npc.models[5] = 13307; //barrows gloves
			npc.models[6] = 4954; //Npc's Boots;
			//npc.models[7] = 0; //Npc's Darkness(Shadow)
			npc.models[7] = 27731; //Armadyl Godsword
			npc.originalColours = new int[] { 61, 41, 24}; //61 - chest, 41 - shoulders/legs, 24 - shoulder trim
			npc.destColours = new int[] { 36133, 36133, 36133};
			npc.description = "A fearsome PKer.";
			break;*/
		/*case 12:
			npc.name = "The Man";
			npc.combatLevel = 69;
			npc.actions = new String[] {"Talk-to", null, null, null, null};
			npc.models = new int[8];
			npc.models[0] = 9638; //fire cape
			npc.models[1] = 187; //phat
			npc.models[2] = 20120; //3rd age platebody
			npc.models[3] = 20138; //3rd age platelegs
			npc.models[4] = 20154; //3rd age platebody
			npc.models[5] = 13307; //barrows gloves
			npc.models[6] = 29252; //Npc's Boots;
			//npc.models[7] = 0; //Npc's Darkness(Shadow)
			npc.models[7] = 32674; //Twisted Bow
			//npc.originalColours = new int[] { 926};
			//npc.destColours = new int[] { 359770};
			break;*/


            case 13451:
            case 13452:
            case 13453:
            case 13454:
                npc.sizeY = 200;
                npc.sizeXZ = 200;
                break;

            case 1828:
                npc.name = "Pet Frog";
                npc.actions = new String[5];
                npc.actions[0] = null;
                break;
            case 2458:
                npc.name = "Woody";
                break;
            case 5498:
                npc.name = "Rocky";
                break;
            case 452:
                npc.name = "Herbal Henry";
                break;
            case 4883:
                npc.name = "Harpoon Harry";
                break;
            case 2007:
                npc.models = new int[1];
                npc.models[0] = 28231;
                npc.name = "Pet kraken";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.combatLevel = 170;
                npc.standAnim = 3989;
                npc.walkAnim = 3989;
                npc.sizeY = 40;
                npc.sizeXZ = 40;
                npc.squaresNeeded = 1;
                npc.drawMinimapDot = false;
                break;
            case 3022:
                npc.models = new int[]{28300};
                npc.name = "Vet'ion";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.combatLevel = 464;
                npc.standAnim = 5483;
                npc.walkAnim = 5481;
                npc.sizeXZ = 60;
                npc.sizeY = 60;
                npc.squaresNeeded = 2;
                npc.drawMinimapDot = false;
                break;
            case 3023:
                npc.models = new int[]{29270};
                npc.name = "Cerberus";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.combatLevel = 464;
                npc.standAnim = 4484;
                npc.walkAnim = 4488;
                npc.sizeXZ = 45;
                npc.sizeY = 45;
                npc.squaresNeeded = 2;
                npc.drawMinimapDot = false;
                break;
            case 3024:
                npc.models = new int[]{28293};
                npc.name = "Scorpia";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.combatLevel = 464;
                NPCDef scor2 = forID(107);
                npc.standAnim = scor2.standAnim;
                npc.walkAnim = scor2.walkAnim;
                npc.sizeY = 60;
                npc.sizeXZ = 60;
                npc.squaresNeeded = 2;
                npc.drawMinimapDot = false;
                break;
            case 3025:
                npc.models = new int[]{31653};
                npc.name = "Skotizo";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.combatLevel = 321;
                npc.standAnim = 55;
                npc.walkAnim = 55;
                npc.sizeY = 35;
                npc.sizeXZ = 35;
                npc.squaresNeeded = 2;
                npc.drawMinimapDot = false;
                break;
            case 3026:
                npc.models = new int[]{28294, 28295};
                npc.name = "Venenatis";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.combatLevel = 464;
                npc.standAnim = 5326;
                npc.walkAnim = 5325;
                npc.sizeY = 55;
                npc.sizeXZ = 55;
                npc.squaresNeeded = 2;
                npc.drawMinimapDot = false;
                break;
            case 3027:
                npc.models = new int[]{28298};
                npc.name = "Callisto";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.combatLevel = 470;
                npc.standAnim = 4919;
                npc.walkAnim = 4923;
                npc.sizeY = 47;
                npc.sizeXZ = 47;
                npc.squaresNeeded = 2;
                npc.drawMinimapDot = false;
                break;
            case 3028:
                npc.models = new int[]{14409};
                npc.name = "Snakeling";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.combatLevel = 725;
                npc.standAnim = 5070;
                npc.walkAnim = 5070;
                npc.sizeY = 45;
                npc.sizeXZ = 45;
                npc.squaresNeeded = 2;
                npc.drawMinimapDot = false;
                break;
            case 3029:
                npc.models = new int[]{14408};
                npc.name = "Snakeling";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.combatLevel = 725;
                npc.standAnim = 5070;
                npc.walkAnim = 5070;
                npc.sizeY = 45;
                npc.sizeXZ = 45;
                npc.squaresNeeded = 2;
                npc.drawMinimapDot = false;
                break;

            case 3041:
                npc.models = new int[]{14407};
                npc.name = "Snakeling";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.combatLevel = 725;
                npc.standAnim = 5070;
                npc.walkAnim = 5070;
                npc.sizeY = 45;
                npc.sizeXZ = 45;
                npc.squaresNeeded = 2;
                npc.drawMinimapDot = false;
                break;
            case 2203:
                npc.name = "Fozzy";
                break;
            case 3042:
                npc.models = new int[]{4039};
                npc.name = "Lizardman Shaman";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.combatLevel = 150;
                npc.standAnim = 7191;
                npc.walkAnim = 7195;
                npc.sizeY = 40;
                npc.sizeXZ = 40;
                npc.squaresNeeded = 2;
                npc.drawMinimapDot = false;
                break;
            case 3043:
                npc.models = new int[]{63604};
                npc.name = "Wildywyrm";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.combatLevel = 382;
                npc.standAnim = 12790;
                npc.walkAnim = 12790;
                npc.sizeY = 60;
                npc.sizeXZ = 60;
                npc.squaresNeeded = 2;
                npc.drawMinimapDot = false;
                break;
            case 2909:
                npc.actions = new String[]{"Slayer", null, "Skiller", null, null};
                break;
            case 3044:
                npc.models = new int[]{28442};
                npc.name = "Thermonuclear Smoke Devil";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.combatLevel = 301;
                npc.standAnim = 1829;
                npc.walkAnim = 1828;
                npc.sizeY = 80;
                npc.sizeXZ = 80;
                npc.squaresNeeded = 2;
                npc.drawMinimapDot = false;
                break;
            case 3045:
                npc.models = new int[]{29477};
                npc.name = "Abyssal Sire";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.combatLevel = 350;
                npc.standAnim = 4533;
                npc.walkAnim = 4534;
                npc.sizeY = 35;
                npc.sizeXZ = 35;
                npc.squaresNeeded = 3;
                npc.drawMinimapDot = false;
                break;
            case 3046:
                npc.models = new int[]{6668};
                npc.name = "Pet Ahrim";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.actions[2] = "Transform";
                npc.combatLevel = 98;
                npc.standAnim = 813;
                npc.walkAnim = 1205;
                npc.sizeY = 90;
                npc.sizeXZ = 90;
                npc.squaresNeeded = 2;
                npc.drawMinimapDot = false;
                break;
            case 3062:
                npc.models = new int[]{6652, 6671, 6640, 6661, 6703, 6679};
                npc.name = "Pet Dharok";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.actions[2] = "Transform";
                npc.combatLevel = 115;
                npc.standAnim = 2065;
                npc.walkAnim = 2064;
                npc.sizeY = 90;
                npc.sizeXZ = 90;
                npc.squaresNeeded = 2;
                npc.drawMinimapDot = false;
                break;
            case 3063:
                npc.models = new int[]{6654, 6673, 6642, 6666, 6679, 6710};
                npc.name = "Pet Guthan";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.actions[2] = "Transform";
                npc.combatLevel = 115;
                npc.standAnim = 813;
                npc.walkAnim = 1205;
                npc.sizeY = 90;
                npc.sizeXZ = 90;
                npc.squaresNeeded = 2;
                npc.drawMinimapDot = false;
                break;
            case 3064:
                npc.models = new int[]{6675};
                npc.name = "Pet Karil";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.actions[2] = "Transform";
                npc.combatLevel = 98;
                npc.standAnim = 808;
                npc.walkAnim = 819;
                npc.sizeY = 90;
                npc.sizeXZ = 90;
                npc.squaresNeeded = 2;
                npc.drawMinimapDot = false;
                break;
            case 3065:
                npc.models = new int[]{6657, 6677, 6645, 6663, 6708, 6679};
                npc.name = "Pet Torag";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.actions[2] = "Transform";
                npc.combatLevel = 115;
                npc.standAnim = 808;
                npc.walkAnim = 819;
                npc.sizeY = 90;
                npc.sizeXZ = 90;
                npc.squaresNeeded = 2;
                npc.drawMinimapDot = false;
                break;
            case 3066:
                npc.models = new int[]{6678, 6705};
                npc.name = "Pet Verac";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.actions[2] = "Transform";
                npc.combatLevel = 115;
                npc.standAnim = 2061;
                npc.walkAnim = 2060;
                npc.sizeY = 90;
                npc.sizeXZ = 90;
                npc.squaresNeeded = 2;
                npc.drawMinimapDot = false;
                break;
            case 3030:
                npc.name = "King black dragon";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.models = new int[]{17414, 17415, 17429, 17422};
                npc.combatLevel = 276;
                npc.standAnim = 90;
                npc.walkAnim = 4635;
                npc.sizeY = 63;
                npc.sizeXZ = 63;
                npc.squaresNeeded = 3;
                break;

            case 3031:
                npc.name = "General graardor";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.models = new int[]{27785, 27789};
                npc.combatLevel = 624;
                npc.standAnim = 7059;
                npc.walkAnim = 7058;
                npc.sizeY = 40;
                npc.sizeXZ = 40;
                break;

            case 3032:
                npc.name = "TzTok-Jad";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.models = new int[]{34131};
                npc.combatLevel = 702;
                npc.standAnim = 9274;
                npc.walkAnim = 9273;
                npc.sizeY = 45;
                npc.sizeXZ = 45;
                npc.squaresNeeded = 2;
                break;

            case 3033:
                npc.name = "Chaos elemental";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.models = new int[]{11216};
                npc.combatLevel = 305;
                npc.standAnim = 3144;
                npc.walkAnim = 3145;
                npc.sizeY = 62;
                npc.sizeXZ = 62;
                break;

            case 3034:
                npc.name = "Corporeal beast";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.models = new int[]{40955};
                npc.combatLevel = 785;
                npc.standAnim = 10056;
                npc.walkAnim = 10055;
                npc.sizeY = 45;
                npc.sizeXZ = 45;
                npc.squaresNeeded = 2;
                break;

            case 3035:
                npc.name = "Kree'arra";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.models = new int[]{28003, 28004};
                npc.combatLevel = 580;
                npc.standAnim = 6972;
                npc.walkAnim = 6973;
                npc.sizeY = 43;
                npc.sizeXZ = 43;
                npc.squaresNeeded = 2;
                break;

            case 3036:
                npc.name = "K'ril tsutsaroth";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.models = new int[]{27768, 27773, 27764, 27765, 27770};
                npc.combatLevel = 650;
                npc.standAnim = 6943;
                npc.walkAnim = 6942;
                npc.sizeY = 43;
                npc.sizeXZ = 43;
                npc.squaresNeeded = 2;
                break;
            case 3037:
                npc.name = "Commander zilyana";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.models = new int[]{28057, 28071, 28078, 28056};
                npc.combatLevel = 596;
                npc.standAnim = 6963;
                npc.walkAnim = 6962;
                npc.sizeY = 103;
                npc.sizeXZ = 103;
                npc.squaresNeeded = 2;
                break;
            case 3038:
                npc.name = "Dagannoth supreme";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.models = new int[]{9941, 9943};
                npc.combatLevel = 303;
                npc.standAnim = 2850;
                npc.walkAnim = 2849;
                npc.sizeY = 105;
                npc.sizeXZ = 105;
                npc.squaresNeeded = 2;
                break;

            case 3039:
                npc.name = "Dagannoth prime"; //9940, 9943, 9942
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.models = new int[]{9940, 9943, 9942};
                npc.originalColours = new int[]{11930, 27144, 16536, 16540};
                npc.destColours = new int[]{5931, 1688, 21530, 21534};
                npc.combatLevel = 303;
                npc.standAnim = 2850;
                npc.walkAnim = 2849;
                npc.sizeY = 105;
                npc.sizeXZ = 105;
                npc.squaresNeeded = 2;
                break;

            case 3040:
                npc.name = "Dagannoth rex";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.models = new int[]{9941};
                npc.originalColours = new int[]{16536, 16540, 27144, 2477};
                npc.destColours = new int[]{7322, 7326, 10403, 2595};
                npc.combatLevel = 303;
                npc.standAnim = 2850;
                npc.walkAnim = 2849;
                npc.sizeY = 105;
                npc.sizeXZ = 105;
                npc.squaresNeeded = 2;
                break;
            case 3047:
                npc.name = "Frost dragon";
                npc.combatLevel = 166;
                npc.standAnim = 13156;
                npc.walkAnim = 13157;
                npc.turn180AnimIndex = -1;
                npc.turn90CCWAnimIndex = -1;
                npc.turn90CWAnimIndex = -1;
                //npc.type = 51;
                npc.degreesToTurn = 32;
                npc.models = new int[]{56767, 55294};
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.sizeY = 72;
                npc.sizeXZ = 72;
                npc.squaresNeeded = 2;
                break;

            case 3048:
                npc.models = new int[]{44733};
                npc.name = "Tormented demon";
                npc.combatLevel = 450;
                npc.standAnim = 10921;
                npc.walkAnim = 10920;
                npc.turn180AnimIndex = -1;
                npc.turn90CCWAnimIndex = -1;
                npc.turn90CWAnimIndex = -1;
                //	npc.type = 8349;
                npc.degreesToTurn = 32;
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.sizeY = 60;
                npc.sizeXZ = 60;
                npc.squaresNeeded = 2;
                break;
            case 3050:
                npc.models = new int[]{24602, 24605, 24606};
                npc.name = "Kalphite queen";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.combatLevel = 333;
                npc.standAnim = 6236;
                npc.walkAnim = 6236;
                npc.sizeY = 70;
                npc.sizeXZ = 70;
                npc.squaresNeeded = 2;
                break;
            case 3051:
                npc.models = new int[]{46141};
                npc.name = "Slash bash";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.combatLevel = 111;
                npc.standAnim = 11460;
                npc.walkAnim = 11461;
                npc.sizeY = 65;
                npc.sizeXZ = 65;
                npc.squaresNeeded = 2;
                break;
            case 3052:
                npc.models = new int[]{45412};
                npc.name = "Phoenix";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.combatLevel = 235;
                npc.standAnim = 11074;
                npc.walkAnim = 11075;
                npc.sizeY = 70;
                npc.sizeXZ = 70;
                npc.squaresNeeded = 2;
                break;
            case 3053:
                npc.models = new int[]{46058, 46057};
                npc.name = "Glacio";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.combatLevel = 299;
                npc.standAnim = 11242;
                npc.walkAnim = 11255;
                npc.sizeY = 70;
                npc.sizeXZ = 70;
                npc.squaresNeeded = 2;
                break;
            case 3054:
                npc.models = new int[]{62717};
                npc.name = "Nex";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.combatLevel = 565;
                npc.standAnim = 6320;
                npc.walkAnim = 6319;
                npc.sizeY = 95;
                npc.sizeXZ = 95;
                npc.squaresNeeded = 1;
                break;
            case 3055:
                npc.models = new int[]{51852, 51853};
                npc.name = "Jungle strykewyrm";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.combatLevel = 110;
                npc.standAnim = 12790;
                npc.walkAnim = 12790;
                npc.sizeY = 60;
                npc.sizeXZ = 60;
                npc.squaresNeeded = 1;
                break;
            case 3056:
                npc.models = new int[]{51848, 51850};
                npc.name = "Desert strykewyrm";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.combatLevel = 130;
                npc.standAnim = 12790;
                npc.walkAnim = 12790;
                npc.sizeY = 60;
                npc.sizeXZ = 60;
                npc.squaresNeeded = 1;
                break;
            case 3057:
                npc.models = new int[]{51847, 51849};
                npc.name = "Ice strykewyrm";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.combatLevel = 210;
                npc.standAnim = 12790;
                npc.walkAnim = 12790;
                npc.sizeY = 65;
                npc.sizeXZ = 65;
                npc.squaresNeeded = 1;
                break;
            case 3058:
                npc.models = new int[]{49142, 49144};
                npc.name = "Green dragon";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.combatLevel = 79;
                npc.standAnim = 12248;
                npc.walkAnim = 12246;
                npc.sizeY = 70;
                npc.sizeXZ = 70;
                npc.squaresNeeded = 2;
                break;
            case 3059:
                npc.models = new int[]{57937};
                npc.name = "Baby blue dragon";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.combatLevel = 48;
                npc.standAnim = 14267;
                npc.walkAnim = 14268;
                npc.sizeY = 85;
                npc.sizeXZ = 85;
                npc.squaresNeeded = 1;
                break;
            case 3060:
                npc.models = new int[]{49137, 49144};
                npc.name = "Blue dragon";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.combatLevel = 111;
                npc.standAnim = 12248;
                npc.walkAnim = 12246;
                npc.sizeY = 70;
                npc.sizeXZ = 70;
                npc.squaresNeeded = 2;
                break;
            case 3061:
                npc.models = new int[]{14294, 49144};
                npc.name = "Black dragon";
                npc.actions = new String[5];
                npc.actions[0] = null;
                npc.combatLevel = 227;
                npc.standAnim = 12248;
                npc.walkAnim = 12246;
                npc.sizeY = 70;
                npc.sizeXZ = 70;
                npc.squaresNeeded = 2;
                break;
        }
        return npc;
    }

    public static void unpackConfig(CacheArchive streamLoader) {
        stream = new Stream(streamLoader.getDataForName("npc.dat"));
        Stream stream2 = new Stream(streamLoader.getDataForName("npc.idx"));

        streamOSRS = new Stream(FileOperations.ReadFile(Signlink.findcachedir() + "npc.dat"));
        Stream stream1 = new Stream(FileOperations.ReadFile(Signlink.findcachedir() + "npc.idx"));

        int totalNPCs = stream2.readUnsignedWord();
        int totalOsrsNPCs = stream1.readUnsignedWord();

        if (Configuration.DEBUG) {
            System.out.println("Npc id total amount : " + totalNPCs);
            System.out.println("OSRS npc id total amount : " + totalOsrsNPCs);
        }

        streamIndices = new int[totalNPCs];
        streamIndicesOSRS = new int[totalOsrsNPCs];

        int i = 2;


        for (int j = 0; j < totalNPCs; j++) {
            streamIndices[j] = i;
            i += stream2.readUnsignedWord();
        }
        i = 2;
        for (int j = 0; j < totalOsrsNPCs; j++) {
            streamIndicesOSRS[j] = i;
            i += stream1.readUnsignedWord();
        }
        cache = new NPCDef[20];
        cacheOSRS = new NPCDef[20];
        for (int k = 0; k < 20; k++) {
            cache[k] = new NPCDef();
            cacheOSRS[k] = new NPCDef();
        }
        // NPCDefThing2.initialize();
        //npcDump();
    }

    public static void nullLoader() {
        modelCache = null;
        streamIndices = null;
        cache = null;
        stream = null;
    }

    public static void npcDump() {
        try {
            FileWriter fw = new FileWriter(System.getProperty("user.home") + "/Desktop/NPC Dump.txt");
            for (int ee = 0; ee < 13000; ee++) {
                NPCDef npc = null;
                npc = NPCDef.forID(ee);

                fw.write("case " + ee + ":");
                fw.write(System.getProperty("line.separator"));
                fw.write("npc.name = \"" + npc.name + "\";");
                fw.write(System.getProperty("line.separator"));
                fw.write("npc.sizeXZ = " + npc.sizeXZ + ";");
                fw.write(System.getProperty("line.separator"));
                fw.write("npc.sizeY = " + npc.sizeY + ";");
                fw.write(System.getProperty("line.separator"));
                fw.write("npc.standAnim = " + npc.standAnim + ";");
                fw.write(System.getProperty("line.separator"));
                fw.write("npcDef.walkAnim = " + npc.walkAnim + ";");
                fw.write(System.getProperty("line.separator"));
                fw.write("npcDef.combatLevel = " + npc.combatLevel + ";");
                fw.write(System.getProperty("line.separator"));
                fw.write("npcDef.squaresNeeded = " + npc.squaresNeeded + ";");
                fw.write(System.getProperty("line.separator"));
                fw.write("npc.description = \"" + npc.description + "\";");
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

    private void readValuesOSRS(Stream stream) {
        int last = -1;
        while (true) {
            int opcode = stream.readUnsignedByte();
            if (opcode == 0)
                return;
            if (opcode == 1) {
                int j = stream.readUnsignedByte();
                models = new int[j];
                for (int j1 = 0; j1 < j; j1++)
                    models[j1] = stream.readUnsignedWord();

            } else if (opcode == 2)
                name = stream.readString();
            else if (opcode == 3)
                description = stream.readString();
            else if (opcode == 12)
                squaresNeeded = stream.readSignedByte();
            else if (opcode == 13) {
                standAnim = stream.readUnsignedWord();
                standAnim += Animation.OSRS_ANIM_OFFSET;
            } else if (opcode == 14)
                walkAnim = stream.readUnsignedWord();
            else if (opcode == 15 || opcode == 16)
                stream.readUnsignedWord();
            else if (opcode == 17) {
                this.turn180AnimIndex = stream.readUnsignedWord();
                this.turn90CWAnimIndex = stream.readUnsignedWord();
                this.turn90CCWAnimIndex = stream.readUnsignedWord();
                if (this.turn180AnimIndex == 65535) {
                    this.turn180AnimIndex = -1;
                }
                if (this.turn90CCWAnimIndex == 65535) {
                    this.turn90CCWAnimIndex = -1;
                }
                if (this.turn90CWAnimIndex != 65535)
                    continue;
                this.turn90CWAnimIndex = -1;
                if (walkAnim != -1)
                    walkAnim += Animation.OSRS_ANIM_OFFSET;
                if (turn180AnimIndex != -1)
                    turn180AnimIndex += Animation.OSRS_ANIM_OFFSET;
                if (turn90CWAnimIndex != -1)
                    turn90CWAnimIndex += Animation.OSRS_ANIM_OFFSET;
                if (turn90CCWAnimIndex != -1)
                    turn90CCWAnimIndex += Animation.OSRS_ANIM_OFFSET;
            } else if (opcode >= 30 && opcode < 40) {
                if (actions == null)
                    actions = new String[10];
                actions[opcode - 30] = stream.readString();
                if (actions[opcode - 30].equalsIgnoreCase("hidden"))
                    actions[opcode - 30] = null;
            } else if (opcode == 40) {
                int k = stream.readUnsignedByte();
                this.originalColours = new int[k];
                this.destColours = new int[k];
                for (int k1 = 0; k1 < k; k1++) {
                    originalColours[k1] = stream.readUnsignedWord();
                    destColours[k1] = stream.readUnsignedWord();
                }
            } else if (opcode == 41) {
                int k = stream.readUnsignedByte();
                for (int k1 = 0; k1 < k; k1++) {
                    stream.readUnsignedWord();
                    stream.readUnsignedWord();
                }

            } else if (opcode == 60) {
                int l = stream.readUnsignedByte();
                this.npcHeadModels = new int[l];
                for (int l1 = 0; l1 < l; l1++)
                    this.npcHeadModels[l1] = stream.readUnsignedWord();

            } else if (opcode == 93)
                this.drawMinimapDot = false;
            else if (opcode == 95)
                combatLevel = stream.readUnsignedWord();
            else if (opcode == 97)
                this.sizeXZ = stream.readUnsignedWord();
            else if (opcode == 98)
                this.sizeY = stream.readUnsignedWord();
            else if (opcode == 99)
                this.hasRenderPriority = true;
            else if (opcode == 100)
                this.lightning = stream.readSignedByte();
            else if (opcode == 101)
                this.shadow = stream.readSignedByte();
            else if (opcode == 102)
                this.headIcon = stream.readUnsignedWord();
            else if (opcode == 103)
                this.degreesToTurn = stream.readUnsignedWord();
            else if (opcode == 106 || opcode == 118) {
                this.varbitId = stream.readUnsignedWord();
                if (this.varbitId == 65535)
                    this.varbitId = -1;
                varSettingsId = stream.readUnsignedWord();
                if (varSettingsId == 65535)
                    varSettingsId = -1;

                int var3 = -1;
                if (opcode == 118) {
                    var3 = stream.readUnsignedWord();
                }
                int i1 = stream.readUnsignedByte();
                childrenIDs = new int[i1 + 2];
                for (int i2 = 0; i2 <= i1; i2++) {
                    childrenIDs[i2] = stream.readUnsignedWord();
                    if (childrenIDs[i2] == 65535)
                        childrenIDs[i2] = -1;
                }
                childrenIDs[i1 + 1] = var3;

            } else if (opcode == 107)
                clickable = false;
            else if (opcode == 111 || opcode == 107 || opcode == 109) {

            } else {
                //System.out.println("Missing NPC opcode " + opcode + "last: " + last);
                continue;
            }
            last = opcode;
        }
    }

    public Model getHeadModel() {
        if (childrenIDs != null) {
            NPCDef altered = getAlteredNPCDef();
            if (altered == null)
                return null;
            else
                return altered.getHeadModel();
        }
        if (npcHeadModels == null)
            return null;
        boolean everyFetched = false;
        for (int i = 0; i < npcHeadModels.length; i++)
            if (!Model.modelIsFetched(npcHeadModels[i], dataType))
                everyFetched = true;
        if (everyFetched)
            return null;
        Model[] parts = new Model[npcHeadModels.length];
        for (int j = 0; j < npcHeadModels.length; j++)
            parts[j] = Model.fetchModel(npcHeadModels[j], dataType);
        Model completeModel;
        if (parts.length == 1)
            completeModel = parts[0];
        else
            completeModel = new Model(parts.length, parts);
        if (originalColours != null) {
            for (int k = 0; k < originalColours.length; k++)
                completeModel.recolour(originalColours[k], destColours[k]);
        }
        return completeModel;
    }

    public NPCDef getAlteredNPCDef() {
        try {
            int j = -1;
            if (varbitId != -1) {
                VarBit varBit = VarBit.cache[varbitId];
                int k = varBit.configId;
                int l = varBit.leastSignificantBit;
                int i1 = varBit.mostSignificantBit;
                int j1 = Client.anIntArray1232[i1 - l];
                j = clientInstance.variousSettings[k] >> l & j1;
            } else if (varSettingsId != -1) {
                j = clientInstance.variousSettings[varSettingsId];
            }
            if (j < 0 || j >= childrenIDs.length || childrenIDs[j] == -1) {
                return null;
            } else {
                return forID(childrenIDs[j]);
            }
        } catch (Exception e) {
            return null;
        }
    }

    public Model getAnimatedModel(int j, int k, int[] ai) {
        if (childrenIDs != null) {
            NPCDef npc = getAlteredNPCDef();
            if (npc == null)
                return null;
            else
                return npc.getAnimatedModel(j, k, ai);
        }
        Model completedModel = (Model) modelCache.get(type);
        if (completedModel == null) {
            boolean everyModelFetched = false;
            for (int ptr = 0; ptr < models.length; ptr++)
                if (!Model.modelIsFetched(models[ptr], dataType))
                    everyModelFetched = true;

            if (everyModelFetched)
                return null;
            Model[] parts = new Model[models.length];
            for (int j1 = 0; j1 < models.length; j1++)
                parts[j1] = Model.fetchModel(models[j1], dataType);
            if (parts.length == 1)
                completedModel = parts[0];
            else
                completedModel = new Model(parts.length, parts);
            if (originalColours != null) {
                for (int k1 = 0; k1 < originalColours.length; k1++)
                    completedModel.recolour(originalColours[k1], destColours[k1]);
            }
            completedModel.createBones();
            completedModel.light(frontLight, backLight, rightLight, middleLight, leftLight, true);
            modelCache.put(completedModel, type);
        }
        Model animatedModel = Model.entityModelDesc;
        animatedModel.method464(completedModel, FrameReader.isNullFrame(k) & FrameReader.isNullFrame(j));
        if (k != -1 && j != -1)
            animatedModel.method471(ai, j, k, dataType);
        else if (k != -1)
            animatedModel.applyTransform(k, dataType);
        if (sizeXZ != 128 || sizeY != 128)
            animatedModel.scaleT(sizeXZ, sizeXZ, sizeY);
        animatedModel.calculateDiagonals();
        animatedModel.triangleSkin = null;
        animatedModel.vertexSkin = null;
        if (squaresNeeded == 1)
            animatedModel.rendersWithinOneTile = true;
        return animatedModel;
    }

    public Model method164(int j, int frame, int[] ai, int nextFrame, int idk, int idk2) {
        if (childrenIDs != null) {
            NPCDef npc = getAlteredNPCDef();
            if (npc == null)
                return null;
            else
                return npc.method164(j, frame, ai, nextFrame, idk, idk2);
        }
        Model completedModel = (Model) modelCache.get(type);
        if (completedModel == null) {
            boolean everyModelFetched = false;
            for (int ptr = 0; ptr < models.length; ptr++)
                if (!Model.modelIsFetched(models[ptr], dataType))
                    everyModelFetched = true;

            if (everyModelFetched)
                return null;
            Model[] parts = new Model[models.length];
            for (int j1 = 0; j1 < models.length; j1++)
                parts[j1] = Model.fetchModel(models[j1], dataType);
            if (parts.length == 1)
                completedModel = parts[0];
            else
                completedModel = new Model(parts.length, parts);
            if (originalColours != null) {
                for (int k1 = 0; k1 < originalColours.length; k1++)
                    completedModel.recolour(originalColours[k1], destColours[k1]);
            }
            completedModel.createBones();
            completedModel.light(frontLight, backLight, rightLight, middleLight, leftLight, true);
            modelCache.put(completedModel, type);
        }
        Model animatedModel = Model.entityModelDesc;
        animatedModel.method464(completedModel, FrameReader.isNullFrame(frame) & FrameReader.isNullFrame(j));

        if (frame != -1 && j != -1)
            animatedModel.method471(ai, j, frame, dataType);
        else if (frame != -1 && nextFrame != -1)
            animatedModel.applyTransform(frame, nextFrame, idk, idk2, dataType);
        else if (frame != -1)
            animatedModel.applyTransform(frame, dataType);
        if (sizeXZ != 128 || sizeY != 128)
            animatedModel.scaleT(sizeXZ, sizeXZ, sizeY);
        animatedModel.calculateDiagonals();
        animatedModel.triangleSkin = null;
        animatedModel.vertexSkin = null;
        if (squaresNeeded == 1)
            animatedModel.rendersWithinOneTile = true;
        return animatedModel;
    }

    public void readValues(Stream stream) {
        do {
            int i = stream.readUnsignedByte();
            if (i == 0)
                return;
            if (i == 1) {
                int j = stream.readUnsignedByte();
                models = new int[j];
                for (int j1 = 0; j1 < j; j1++)
                    models[j1] = stream.readUnsignedWord();
            } else if (i == 2)
                name = stream.readNewString();
            else if (i == 3) {
                description = stream.readNewString();
            } else if (i == 12)
                squaresNeeded = stream.readSignedByte();
            else if (i == 13)
                standAnim = stream.readUnsignedWord();
            else if (i == 14) {
                walkAnim = stream.readUnsignedWord();
                runAnim = walkAnim;
            } else if (i == 17) {
                walkAnim = stream.readUnsignedWord();
                turn180AnimIndex = stream.readUnsignedWord();
                turn90CWAnimIndex = stream.readUnsignedWord();
                turn90CCWAnimIndex = stream.readUnsignedWord();
                if (walkAnim == 65535)
                    walkAnim = -1;
                if (turn180AnimIndex == 65535)
                    turn180AnimIndex = -1;
                if (turn90CWAnimIndex == 65535)
                    turn90CWAnimIndex = -1;
                if (turn90CCWAnimIndex == 65535)
                    turn90CCWAnimIndex = -1;
            } else if (i >= 30 && i < 40) {
                if (actions == null)
                    actions = new String[5];
                actions[i - 30] = stream.readNewString();
                if (actions[i - 30].equalsIgnoreCase("hidden"))
                    actions[i - 30] = null;
            } else if (i == 40) {
                int k = stream.readUnsignedByte();
                destColours = new int[k];
                originalColours = new int[k];
                for (int k1 = 0; k1 < k; k1++) {
                    originalColours[k1] = stream.readUnsignedWord();
                    destColours[k1] = stream.readUnsignedWord();
                }
            } else if (i == 60) {
                int l = stream.readUnsignedByte();
                npcHeadModels = new int[l];
                for (int l1 = 0; l1 < l; l1++)
                    npcHeadModels[l1] = stream.readUnsignedWord();
            } else if (i == 90)
                stream.readUnsignedWord();
            else if (i == 91)
                stream.readUnsignedWord();
            else if (i == 92)
                stream.readUnsignedWord();
            else if (i == 93)
                drawMinimapDot = false;
            else if (i == 95)
                combatLevel = stream.readUnsignedWord();
            else if (i == 97)
                sizeXZ = stream.readUnsignedWord();
            else if (i == 98)
                sizeY = stream.readUnsignedWord();
            else if (i == 99)
                hasRenderPriority = true;
            else if (i == 100)
                lightning = stream.readSignedByte();
            else if (i == 101)
                shadow = stream.readSignedByte() * 5;
            else if (i == 102)
                headIcon = stream.readUnsignedWord();
            else if (i == 103)
                degreesToTurn = stream.readUnsignedWord();
            else if (i == 106) {
                varbitId = stream.readUnsignedWord();
                if (varbitId == 65535)
                    varbitId = -1;
                varSettingsId = stream.readUnsignedWord();
                if (varSettingsId == 65535)
                    varSettingsId = -1;
                int i1 = stream.readUnsignedByte();
                childrenIDs = new int[i1 + 1];
                for (int i2 = 0; i2 <= i1; i2++) {
                    childrenIDs[i2] = stream.readUnsignedWord();
                    if (childrenIDs[i2] == 65535)
                        childrenIDs[i2] = -1;
                }
            } else if (i == 107)
                clickable = false;
        } while (true);
    }


    /**
     * Gets the displayed overhead icon of the NPC.
     */
    @Override
    public HeadIcon getOverheadIcon() {
        return null;
    }

    /**
     * Gets the value of a given {@link ParamID}, or its default if it is unset
     *
     * @param paramID
     */
    @Override
    public int getIntValue(int paramID) {
        return 0;
    }

    /**
     * Sets the value of a given {@link ParamID}
     *
     * @param paramID
     * @param value
     */
    @Override
    public void setValue(int paramID, int value) {

    }

    /**
     * Gets the value of a given {@link ParamID}, or its default if it is unset
     *
     * @param paramID
     */
    @Override
    public String getStringValue(int paramID) {
        return null;
    }

    /**
     * Sets the value of a given {@link ParamID}
     *
     * @param paramID
     * @param value
     */
    @Override
    public void setValue(int paramID, String value) {

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int[] getModels() {
        return models;
    }

    @Override
    public String[] getActions() {
        return actions;
    }

    @Override
    public boolean isClickable() {
        return clickable;
    }

    @Override
    public boolean isFollower() {
        return false;
    }

    @Override
    public boolean isInteractible() {
        return false;
    }

    @Override
    public boolean isMinimapVisible() {
        return drawMinimapDot;
    }

    @Override
    public boolean isVisible() {
        return false;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getCombatLevel() {
        return combatLevel;
    }

    @Override
    public int[] getConfigs() {
        return clientInstance.getVarps();
    }

    @Override
    public RSNPCComposition transform() {
        return null;
    }

    @Override
    public int getSize() {
        return sizeXZ;
    }

    @Override
    public int getRsOverheadIcon() {
        return headIcon;
    }

    @Override
    public RSIterableNodeHashTable getParams() {
        return null;
    }

    @Override
    public void setParams(IterableHashTable params) {

    }

    @Override
    public void setParams(RSIterableNodeHashTable params) {

    }
}