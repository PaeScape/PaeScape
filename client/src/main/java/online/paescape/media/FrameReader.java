package online.paescape.media;


import online.paescape.Client;
import online.paescape.net.Stream;
import online.paescape.util.DataType;

@SuppressWarnings("all")
public final class FrameReader {

    public static FrameReader animationlist[][];
    public static byte[][] frameData = null;
    public static byte[][] skinData = null;
    public static Client instance;
    public static boolean[] aBooleanArray643;
    static FrameReader framesOSRS[][];
    public int displayLength;
    public SkinList mySkinList;
    public int stepCount;
    public int opCodeLinkTable[];
    public int xOffset[];
    public int yOffset[];
    public int zOffset[];

    private FrameReader() {
    }

    public static byte[] getData(int type, int index) {
        if (type == 0) {
            return frameData[index];
        } else {
            return skinData[index];
        }
    }

    public static void initialise(int i) {
        animationlist = new FrameReader[5000][0];
        framesOSRS = new FrameReader[5000][0];
    }

    public static void load(int file, byte[] fileData, DataType dataType) {
        try {
            //	System.out.println(file + ".dat");
            Stream stream = new Stream(fileData);
            SkinList skinList = new SkinList(stream, 0);
            int k1 = stream.readUnsignedWord();
            if (dataType == DataType.OLDSCHOOL) {
                framesOSRS[file] = new FrameReader[(int) (k1 * 3.0)];
            } else {
                animationlist[file] = new FrameReader[(int) (k1 * 3.0)];
            }
            int ai[] = new int[500];
            int ai1[] = new int[500];
            int ai2[] = new int[500];
            int ai3[] = new int[500];
            for (int l1 = 0; l1 < k1; l1++) {
                int i2 = stream.readUnsignedWord();
                FrameReader frameReader;
                if (dataType == DataType.OLDSCHOOL) {
                    frameReader = framesOSRS[file][i2] = new FrameReader();
                } else {
                    frameReader = animationlist[file][i2] = new FrameReader();
                }

                frameReader.mySkinList = skinList;
                int j2 = stream.readUnsignedByte();
                int l2 = 0;
                int k2 = -1;
                for (int i3 = 0; i3 < j2; i3++) {
                    int j3 = stream.readUnsignedByte();

                    if (j3 > 0) {
                        if (skinList.opcodes[i3] != 0) {
                            for (int l3 = i3 - 1; l3 > k2; l3--) {
                                if (skinList.opcodes[l3] != 0)
                                    continue;
                                ai[l2] = l3;
                                ai1[l2] = 0;
                                ai2[l2] = 0;
                                ai3[l2] = 0;
                                l2++;
                                break;
                            }

                        }

                        ai[l2] = i3;
                        int c = 0;
                        if (skinList.opcodes[i3] == 3)
                            c = 128;

                        if ((j3 & 0x1) != 0x0)
                            ai1[l2] = stream.readShort2();
                        else
                            ai1[l2] = c;

                        if ((j3 & 0x2) != 0x0)
                            ai2[l2] = stream.readShort2();
                        else
                            ai2[l2] = c;

                        if ((j3 & 0x4) != 0x0)
                            ai3[l2] = stream.readShort2();
                        else {
                            ai3[l2] = c;
                        }
                        if (skinList.opcodes[i3] == 2) {
                            ai1[l2] = ((ai1[l2] & 0xff) << 3) + (ai1[l2] >> 8 & 0x7);
                            ai2[l2] = ((ai2[l2] & 0xff) << 3) + (ai2[l2] >> 8 & 0x7);
                            ai3[l2] = ((ai3[l2] & 0xff) << 3) + (ai3[l2] >> 8 & 0x7);
                        }
                        k2 = i3;
                        l2++;
                    }
                }

                frameReader.stepCount = l2;
                frameReader.opCodeLinkTable = new int[l2];
                frameReader.xOffset = new int[l2];
                frameReader.yOffset = new int[l2];
                frameReader.zOffset = new int[l2];
                for (int k3 = 0; k3 < l2; k3++) {
                    frameReader.opCodeLinkTable[k3] = ai[k3];
                    frameReader.xOffset[k3] = ai1[k3];
                    frameReader.yOffset[k3] = ai2[k3];
                    frameReader.zOffset[k3] = ai3[k3];
                }

            }
        } catch (Exception exception) {
        }
    }

    public static void nullLoader() {
        animationlist = null;
        framesOSRS = null;
    }

    public static FrameReader forID(int int1, DataType dataType) {
        try {
            String s = "";
            int file = 0;
            int k = 0;
            s = Integer.toHexString(int1);
            file = Integer.parseInt(s.substring(0, s.length() - 4), 16);
            k = Integer.parseInt(s.substring(s.length() - 4), 16);

            if (dataType == DataType.OLDSCHOOL) {
                if (framesOSRS[file].length == 0 || framesOSRS[file].length < k) {
                    Client.instance.onDemandFetcher.requestFileData(Client.OSRS_ANIM_IDX - 1, file);
                    return null;
                }

                return framesOSRS[file][k];
            } else {
                if (animationlist[file].length == 0) {
                    Client.instance.onDemandFetcher.requestFileData(Client.ANIM_IDX - 1, file);
                    return null;
                }
                return animationlist[file][k];
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static FrameReader getTween(FrameReader f1, FrameReader f2) {
        FrameReader newFrame = new FrameReader();
        newFrame.displayLength = f1.displayLength;
        newFrame.stepCount = f1.stepCount;
        newFrame.opCodeLinkTable = f1.opCodeLinkTable;
        newFrame.aBooleanArray643 = f1.aBooleanArray643;
        newFrame.xOffset = new int[f1.xOffset.length];
        newFrame.mySkinList = f1.mySkinList;
        for (int i = 0; i < f1.xOffset.length; i++) {
            try {
                int middleXOffset = (f2.xOffset[i] - f1.xOffset[i]) / 2 + f1.xOffset[i];
                newFrame.xOffset[i] = middleXOffset;
            } catch (Exception e) {
                newFrame.xOffset[i] = f1.xOffset[i];
            }
        }
        newFrame.yOffset = new int[f1.yOffset.length];
        for (int i = 0; i < f1.yOffset.length; i++) {
            try {
                int middleYOffset = (f2.yOffset[i] - f1.yOffset[i]) / 2 + f1.yOffset[i];
                newFrame.yOffset[i] = middleYOffset;
            } catch (Exception e) {
                newFrame.yOffset[i] = f1.yOffset[i];
            }

        }
        newFrame.zOffset = new int[f1.zOffset.length];
        for (int i = 0; i < f1.zOffset.length; i++) {
            try {
                int middleZOffset = (f2.zOffset[i] - f1.zOffset[i]) / 2 + f1.zOffset[i];
                newFrame.zOffset[i] = middleZOffset;

            } catch (Exception e) {
                newFrame.zOffset[i] = f1.zOffset[i];
            }

        }
        return newFrame;
    }

    public static boolean isNullFrame(int frame) {
        return frame == -1;
    }

}
