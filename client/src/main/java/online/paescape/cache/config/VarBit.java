package online.paescape.cache.config;


import online.paescape.cache.CacheArchive;
import online.paescape.net.Stream;
import online.paescape.util.Configuration;
import online.paescape.util.FileOperations;
import online.paescape.util.Signlink;

public final class VarBit {

    public static VarBit[] cache;
    public static VarBit[] cacheOSRS;
    public int configId;
    public int leastSignificantBit;
    public int mostSignificantBit;
    public int configIdOSRS;
    public int leastSignificantBitOSRS;
    public int mostSignificantBitOSRS;
    boolean aBoolean651;
    boolean aBoolean651OSRS;

    private VarBit() {
        aBoolean651 = false;
    }

    public static void unpackConfig(CacheArchive streamLoader) {
        Stream stream = new Stream(streamLoader.getDataForName("varbit.dat"));
        //Stream stream = new Stream(FileOperations.ReadFile(signlink.findcachedir() + "osrs/varbit.dat"));

        int cacheSize = stream.readUnsignedWord();
        if (cache == null)
            cache = new VarBit[cacheSize];
        for (int j = 0; j < cacheSize; j++) {
            if (cache[j] == null)
                cache[j] = new VarBit();
            if (cache[j].aBoolean651)
                Varp.cache[cache[j].configId].aBoolean713 = true;

            cache[j].readValues(stream);
        }

        if (stream.currentOffset != stream.buffer.length)
            System.out.println("varbit load mismatch");
    }

    public static void unpackConfigOSRS(CacheArchive streamLoader) {
        Stream stream = new Stream(FileOperations.ReadFile(Signlink.findcachedir() + "varbit.dat"));
        int cacheSize = stream.readUnsignedWord();

        if (cacheOSRS == null) {
            cacheOSRS = new VarBit[cacheSize];
        }
        if (Configuration.DEBUG)
            System.out.println(String.format("Loaded: %d  osrs varbits", cacheSize));

        for (int i = 0; i < cacheSize; i++) {
            if (cacheOSRS[i] == null)
                cacheOSRS[i] = new VarBit();
            cacheOSRS[i].readValuesOSRS(stream);
        }

        if (stream.currentOffset != stream.buffer.length && Configuration.DEBUG)
            System.out.println("varbit load mismatch");
    }

    private void readValues(Stream stream) {
        do {
            int j = stream.readUnsignedByte();
            if (j == 0)
                return;
            if (j == 1) {
                configId = stream.readUnsignedWord();
                leastSignificantBit = stream.readUnsignedByte();
                mostSignificantBit = stream.readUnsignedByte();
            } else if (j == 10)
                stream.readString();
            else if (j == 3)
                stream.readDWord();
            else if (j == 4)
                stream.readDWord();
            //else
            //	System.out.println("Error unrecognised config code: " + j);
        } while (true);
    }

    private void readValuesOSRS(Stream stream) {
        configIdOSRS = stream.readUnsignedWord(); //ConfigId
        leastSignificantBitOSRS = stream.readUnsignedByte(); //lsb
        mostSignificantBitOSRS = stream.readUnsignedByte(); //msb
    }
}
