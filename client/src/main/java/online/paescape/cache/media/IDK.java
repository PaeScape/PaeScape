package online.paescape.cache.media;


import online.paescape.cache.CacheArchive;
import online.paescape.media.animable.Model;
import online.paescape.net.Stream;
import online.paescape.util.Configuration;
import online.paescape.util.DataType;

public final class IDK {

    public static int length;
    public static IDK[] cache;
    private final int[] recolourOriginal;
    private final int[] recolourTarget;
    private final int[] headModelIDs = {-1, -1, -1, -1, -1};
    public int bodyPartID;
    public int[] bodyModelIDs;
    public boolean notSelectable;

    private IDK() {
        bodyPartID = -1;
        recolourOriginal = new int[6];
        recolourTarget = new int[6];
        notSelectable = false;
    }

    public static void unpackConfig(CacheArchive streamLoader) {
        Stream stream = new Stream(streamLoader.getDataForName("idk.dat"));
        //Stream stream = new Stream(FileOperations.readFile(signlink.getCacheDirectory() + "idk.dat"));

        length = stream.readUnsignedWord();
        if (Configuration.DEBUG)
            System.out.println("IDK length : " + length);
        if (cache == null)
            cache = new IDK[length];
        for (int j = 0; j < length; j++) {
            if (cache[j] == null)
                cache[j] = new IDK();
            cache[j].readValues(stream);
        }
    }

    private void readValues(Stream stream) {
        do {
            int i = stream.readUnsignedByte();
            if (i == 0)
                return;
            if (i == 1)
                bodyPartID = stream.readUnsignedByte();
            else if (i == 2) {
                int j = stream.readUnsignedByte();
                bodyModelIDs = new int[j];
                for (int k = 0; k < j; k++)
                    bodyModelIDs[k] = stream.readUnsignedWord();

            } else if (i == 3)
                notSelectable = true;
            else if (i >= 40 && i < 50)
                recolourOriginal[i - 40] = stream.readUnsignedWord();
            else if (i >= 50 && i < 60)
                recolourTarget[i - 50] = stream.readUnsignedWord();
            else if (i >= 60 && i < 70)
                headModelIDs[i - 60] = stream.readUnsignedWord();
            else
                System.out.println("Error unrecognised config code: " + i);
        } while (true);
    }

    public boolean bodyModelIsFetched() {
        if (bodyModelIDs == null)
            return true;
        boolean flag = true;
        for (int j = 0; j < bodyModelIDs.length; j++) {
            if (!Model.modelIsFetched(bodyModelIDs[j], DataType.REGULAR))
                flag = false;
        }
        return flag;
    }

    public Model fetchBodyModel() {
        if (bodyModelIDs == null)
            return null;
        Model[] aclass30_sub2_sub4_sub6s = new Model[bodyModelIDs.length];
        for (int i = 0; i < bodyModelIDs.length; i++)
            aclass30_sub2_sub4_sub6s[i] = Model.fetchModel(bodyModelIDs[i], DataType.REGULAR);

        Model model;
        if (aclass30_sub2_sub4_sub6s.length == 1)
            model = aclass30_sub2_sub4_sub6s[0];
        else
            model = new Model(aclass30_sub2_sub4_sub6s.length,
                    aclass30_sub2_sub4_sub6s);


        for (int j = 0; j < 6; j++) {
            if (recolourOriginal[j] == 0)
                break;
            model.recolour(recolourOriginal[j], recolourTarget[j]);
        }

        return model;
    }

    public boolean headModelFetched() {
        boolean flag1 = true;
        for (int i = 0; i < 5; i++)
            if (headModelIDs[i] != -1 && !Model.modelIsFetched(headModelIDs[i], DataType.REGULAR)) {
                flag1 = false;
            }
        return flag1;
    }

    public Model fetchHeadModel() {
        Model[] aclass30_sub2_sub4_sub6s = new Model[5];
        int j = 0;
        for (int k = 0; k < 5; k++)
            if (headModelIDs[k] != -1)
                aclass30_sub2_sub4_sub6s[j++] = Model
                        .fetchModel(headModelIDs[k], DataType.REGULAR);

        Model model = new Model(j, aclass30_sub2_sub4_sub6s);
        for (int l = 0; l < 6; l++) {
            if (recolourOriginal[l] == 0)
                break;
            model.recolour(recolourOriginal[l], recolourTarget[l]);
        }


        return model;
    }
}
