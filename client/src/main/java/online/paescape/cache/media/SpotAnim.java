package online.paescape.cache.media;

import online.paescape.cache.CacheArchive;
import online.paescape.collection.MemCache;
import online.paescape.media.animable.Model;
import online.paescape.net.Stream;
import online.paescape.util.Configuration;
import online.paescape.util.DataType;
import online.paescape.util.FileOperations;
import online.paescape.util.Signlink;

public final class SpotAnim {
    public static final int OSRS_GFX_OFFSET = 2963;
    public static SpotAnim[] cache;
    public static MemCache modelCache = new MemCache(30);
    private static Stream streamOSRS;
    private final int[] originalColours;
    private final int[] destColours;
    public DataType dataType = DataType.REGULAR;
    public int anInt400;
    public Animation animation;
    public int sizeXY;
    public int sizeZ;
    public int rotation;
    public int shadow;
    public int lightness;
    private int rdc;
    private int rdc2;
    private int rdc3;
    private int id;
    private int modelId;
    private int animationId;

    private SpotAnim() {
        anInt400 = 9;
        animationId = -1;
        originalColours = new int[6];
        destColours = new int[6];
        sizeXY = 128;
        sizeZ = 128;
    }

    public static void unpackConfig(CacheArchive streamLoader) {
        Stream stream = new Stream(streamLoader.getDataForName("spotanim.dat"));
        streamOSRS = new Stream(FileOperations.readFile(Signlink.getCacheDirectory() + "spotanim.dat"));

        int length = stream.readUnsignedWord();
        int lengthOSRS = streamOSRS.readUnsignedWord();

        if (Configuration.DEBUG) {
            System.out.println("Gfx id total :" + length);
            System.out.println("OSRS Gfx id total :" + lengthOSRS);
        }

        if (cache == null) {
            cache = new SpotAnim[length + lengthOSRS];
        }
        for (int j = 0; j < cache.length; j++) {
            if (cache[j] == null) {
                cache[j] = new SpotAnim();
            }
            if (j >= length) {
                cache[j].dataType = DataType.OLDSCHOOL;
            }

            cache[j].id = j;
            cache[j].rdc = 0;
            cache[j].rdc2 = 0;
            cache[j].rdc3 = 0;

            if (cache[j].dataType == DataType.OLDSCHOOL) {
                cache[j].readValuesOSRS(streamOSRS);
            } else {
                cache[j].readValues(stream);
            }
        }
        custom();
    }

    private static void custom() {
        cache[2274].modelId = cache[2281].modelId;
        cache[2274].animationId = cache[2281].animationId;
        cache[2274].rotation = 90;
        cache[2274].animation = cache[2281].animation;


        // Inferno
        cache[1374].modelId = 33013;
        cache[1374].animationId = 7615;
        cache[1374].sizeXY = 90;
        cache[1374].sizeZ = 90;
        cache[1374].shadow = 40;
        cache[1374].lightness = 160;
        cache[1374].rotation = 0;
        cache[1374].animation = Animation.anims[7615];

        // Inferno
        cache[1375].modelId = 33006;
        cache[1375].animationId = 7571;
        cache[1375].sizeXY = 128;
        cache[1375].sizeZ = 128;
        cache[1375].shadow = 80;
        cache[1375].lightness = 160;
        cache[1375].animation = Animation.anims[7571];

        // Inferno
        cache[1376].modelId = 33007;
        cache[1376].animationId = 7571;
        cache[1376].sizeXY = 90;
        cache[1376].sizeZ = 90;
        cache[1376].shadow = 40;
        cache[1376].lightness = 160;
        cache[1376].animation = Animation.anims[7571];

        // Inferno
        cache[1377].modelId = 33013;
        cache[1377].animationId = 7615;
        cache[1377].sizeXY = 90;
        cache[1377].sizeZ = 90;
        cache[1377].shadow = 40;
        cache[1377].lightness = 160;
        cache[1377].animation = Animation.anims[7615];

        // Inferno
        cache[1378].modelId = 33015;
        cache[1378].animationId = 7615;
        cache[1378].sizeXY = 128;
        cache[1378].sizeZ = 128;
        cache[1378].rotation = 0;
        cache[1378].shadow = 0;
        cache[1378].lightness = 0;
        cache[1378].animation = Animation.anims[7615];
        // Inferno
        cache[1379].modelId = 33016;
        cache[1379].animationId = 7614;
        cache[1379].sizeXY = 128;
        cache[1379].sizeZ = 128;
        cache[1379].rotation = 0;
        cache[1379].shadow = 0;
        cache[1379].lightness = 0;
        cache[1379].animation = Animation.anims[7614];
        // Inferno
        cache[1380].modelId = 33008;
        cache[1380].animationId = 7616;
        cache[1380].sizeXY = 128;
        cache[1380].sizeZ = 128;
        cache[1380].rotation = 0;
        cache[1380].shadow = 0;
        cache[1380].lightness = 0;
        cache[1380].animation = Animation.anims[7616];
        // Inferno
        cache[1381].modelId = 33009;
        cache[1381].animationId = 7616;
        cache[1381].sizeXY = 128;
        cache[1381].sizeZ = 128;
        cache[1381].rotation = 0;
        cache[1381].shadow = 0;
        cache[1381].lightness = 0;
        cache[1381].animation = Animation.anims[7616];
        // Inferno
        cache[1382].modelId = 33017;
        cache[1382].animationId = 7614;
        cache[1382].sizeXY = 128;
        cache[1382].sizeZ = 128;
        cache[1382].rotation = 0;
        cache[1382].shadow = 0;
        cache[1382].lightness = 0;
        cache[1382].animation = Animation.anims[7614];

    }

    private void readValuesOSRS(Stream stream) {
        do {
            int i = stream.readUnsignedByte();
            if (i == 0)
                return;
            if (i == 1)
                modelId = stream.readUnsignedWord();
            else if (i == 2) {
                animationId = stream.readUnsignedWord();

                if (dataType == DataType.OLDSCHOOL) {
                    animationId += Animation.OSRS_ANIM_OFFSET;
                }

                if (Animation.anims != null) {
                    animation = Animation.anims[animationId];
                }
            } else if (i == 4)
                sizeXY = stream.readUnsignedWord();
            else if (i == 5)
                sizeZ = stream.readUnsignedWord();
            else if (i == 6)
                rotation = stream.readUnsignedWord();
            else if (i == 7)
                shadow = stream.readUnsignedByte();
            else if (i == 8)
                lightness = stream.readUnsignedByte();
            else if (i == 40) {
                int j = stream.readUnsignedByte();
                for (int k = 0; k < j; k++) {
                    originalColours[k] = stream.readUnsignedWord();
                    destColours[k] = stream.readUnsignedWord();
                }
            } else if (i == 41) {
                int length = stream.readUnsignedByte();
                int[] retextureToFind = new int[length];
                int[] retextureToReplace = new int[length];
                for (int index = 0; index < length; ++index) {
                    retextureToFind[index] = stream.readUnsignedWord();
                    retextureToReplace[index] = stream.readUnsignedWord();
                }
            } else
                System.out.println("Error unrecognised spotanim config code: "
                        + i);
        } while (true);
    }

    private void readValues(Stream stream) {
        do {
            int i = stream.readUnsignedByte();
            if (i == 0)
                return;
            if (i == 1)
                modelId = stream.readUnsignedWord();
            else if (i == 2) {
                animationId = stream.readUnsignedWord();
                if (Animation.anims != null)
                    animation = Animation.anims[animationId];
            } else if (i == 4)
                sizeXY = stream.readUnsignedWord();
            else if (i == 5)
                sizeZ = stream.readUnsignedWord();
            else if (i == 6)
                rotation = stream.readUnsignedWord();
            else if (i == 7)
                shadow = stream.readUnsignedByte();
            else if (i == 8)
                lightness = stream.readUnsignedByte();
            else if (i == 40) {
                int j = stream.readUnsignedByte();
                for (int k = 0; k < j; k++) {
                    originalColours[k] = stream.readUnsignedWord();
                    destColours[k] = stream.readUnsignedWord();
                }
            } else
                System.out.println("Error unrecognised spotanim config code: "
                        + i);
        } while (true);
    }

    public Model getModel() {
        Model model = (Model) modelCache.get(id);
        if (model != null)
            return model;
        model = Model.fetchModel(modelId, dataType);
        if (model == null)
            return null;
        for (int i = 0; i < 6; i++)
            if (originalColours[0] != 0)
                model.recolour(originalColours[i], destColours[i]);
        modelCache.put(model, id);
        return model;

    }
}