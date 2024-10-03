package online.paescape.cache.media;

import online.paescape.cache.CacheArchive;
import online.paescape.media.FrameReader;
import online.paescape.net.Stream;
import online.paescape.util.DataType;
import online.paescape.util.FileOperations;
import online.paescape.util.Signlink;

public final class Animation {
    public static final int OSRS_ANIM_OFFSET = 21261;
    public static Animation[] anims;
    public static int anInt367;
    private static Stream streamOSRS;
    public DataType dataType;
    public int frameCount;
    public int[] frameIDs;
    public int[] frameIDs2;
    public int[] delays;
    public int loopDelay;
    public int[] animationFlowControl;
    public boolean oneSquareAnimation;
    public int forcedPriority;
    public int leftHandItem;
    public int rightHandItem;
    public int frameStep;
    public int resetWhenWalk;
    public int priority;
    public int delayType;

    private Animation() {
        loopDelay = -1;
        oneSquareAnimation = false;
        forcedPriority = 5;
        leftHandItem = -1;
        rightHandItem = -1;
        frameStep = 99;
        resetWhenWalk = -1;
        priority = -1;
        delayType = 2;
    }

    public static void unpackConfig(CacheArchive streamLoader) {
        Stream stream = new Stream(streamLoader.getDataForName("seq.dat"));
        streamOSRS = new Stream(FileOperations.readFile(Signlink.getCacheDirectory() + "seq.dat"));
        int length = stream.readUnsignedWord();
        int lengthOSRS = streamOSRS.readUnsignedWord();

        if (anims == null)
            anims = new Animation[length + 6000 + lengthOSRS];
        for (int j = 0; j < anims.length; j++) {
            if (j >= length && j <= length + 6000) {
                continue;
            }
            if (anims[j] == null)
                anims[j] = new Animation();
            if (j > length + 6000) {
                anims[j].dataType = DataType.OLDSCHOOL;
                anims[j].readValuesOSRS(j, streamOSRS);
            } else {
                anims[j].readValues(stream);
            }

            if (j == 884) {
                anims[j].leftHandItem = -1;
                anims[j].rightHandItem = -1;
            }
            if (j == 4495) {// cerberus death anim
                anims[j].frameCount = 14;
                anims[j].frameIDs = new int[]{117309441, 117309557, 117309612, 117309536, 117309603, 117309563,
                        117309609, 117309567, 117309502, 117309607, 117309516, 117309626, 117309463, 117309514};
                anims[j].frameIDs2 = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                anims[j].delays = new int[]{5, 5, 5, 5, 5, 5, 3, 3, 5, 5, 3, 3, 4, 4};
                anims[j].loopDelay = 1;
                anims[j].animationFlowControl = null;
                anims[j].oneSquareAnimation = false;
                anims[j].forcedPriority = 10;
                anims[j].leftHandItem = -1;
                anims[j].rightHandItem = -1;
                anims[j].frameStep = 99;
                anims[j].resetWhenWalk = 0;
                anims[j].priority = 0;
                anims[j].delayType = 2;
            }
            if (j == 5070) { // Zulrah
                anims[j] = new Animation();
                anims[j].frameCount = 9;
                anims[j].loopDelay = -1;
                anims[j].forcedPriority = 5;
                anims[j].leftHandItem = -1;
                anims[j].rightHandItem = -1;
                anims[j].frameStep = 99;
                anims[j].resetWhenWalk = 0;
                anims[j].priority = 0;
                anims[j].delayType = 2;
                anims[j].oneSquareAnimation = false;
                anims[j].frameIDs = new int[]{11927608, 11927625, 11927598, 11927678, 11927582, 11927600, 11927669,
                        11927597, 11927678};
                anims[j].delays = new int[]{5, 4, 4, 4, 5, 5, 5, 4, 4};
            }
            if (j == 5069) {
                anims[j].frameCount = 15;
                anims[j].loopDelay = -1;
                anims[j].forcedPriority = 6;
                anims[j].leftHandItem = -1;
                anims[j].rightHandItem = -1;
                anims[j].frameStep = 99;
                anims[j].resetWhenWalk = 0;
                anims[j].priority = 0;
                anims[j].delayType = 1;
                anims[j].oneSquareAnimation = false;
                anims[j].frameIDs = new int[]{11927613, 11927599, 11927574, 11927659, 11927676, 11927562, 11927626,
                        11927628, 11927684, 11927647, 11927602, 11927576, 11927586, 11927653, 11927616};
                anims[j].delays = new int[]{3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5};
            }
            if (j == 5072) {
                anims[j].frameCount = 21;
                anims[j].loopDelay = 1;
                anims[j].forcedPriority = 8;
                anims[j].leftHandItem = -1;
                anims[j].rightHandItem = -1;
                anims[j].frameStep = 99;
                anims[j].resetWhenWalk = 0;
                anims[j].priority = 0;
                anims[j].delayType = 2;
                anims[j].oneSquareAnimation = false;
                anims[j].frameIDs = new int[]{11927623, 11927595, 11927685, 11927636, 11927670, 11927579, 11927664,
                        11927666, 11927661, 11927673, 11927633, 11927624, 11927555, 11927588, 11927692, 11927667,
                        11927556, 11927630, 11927695, 11927643, 11927640};
                anims[j].delays = new int[]{2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
            }
            if (j == 5073) {
                anims[j].frameCount = 21;
                anims[j].loopDelay = -1;
                anims[j].forcedPriority = 9;
                anims[j].leftHandItem = -1;
                anims[j].rightHandItem = -1;
                anims[j].frameStep = 99;
                anims[j].resetWhenWalk = 0;
                anims[j].priority = 0;
                anims[j].delayType = 2;
                anims[j].oneSquareAnimation = false;
                anims[j].frameIDs = new int[]{11927640, 11927643, 11927695, 11927630, 11927556, 11927667, 11927692,
                        11927588, 11927555, 11927624, 11927633, 11927673, 11927661, 11927666, 11927664, 11927579,
                        11927670, 11927636, 11927685, 11927595, 11927623};
                anims[j].delays = new int[]{3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 2, 2};
            }
            if (j == 5806) {
                anims[j].frameCount = 55;
                anims[j].loopDelay = -1;
                anims[j].forcedPriority = 6;
                anims[j].leftHandItem = -1;
                anims[j].rightHandItem = -1;
                anims[j].frameStep = 99;
                anims[j].resetWhenWalk = 0;
                anims[j].priority = 0;
                anims[j].delayType = 2;
                anims[j].oneSquareAnimation = true;
                anims[j].frameIDs = new int[]{11927612, 11927677, 11927615, 11927573, 11927618, 11927567, 11927564,
                        11927606, 11927675, 11927657, 11927690, 11927583, 11927672, 11927552, 11927563, 11927683,
                        11927639, 11927635, 11927668, 11927614, 11927627, 11927610, 11927693, 11927644, 11927656,
                        11927660, 11927629, 11927635, 11927668, 11927614, 11927627, 11927610, 11927693, 11927644,
                        11927656, 11927660, 11927635, 11927668, 11927614, 11927560, 11927687, 11927577, 11927569,
                        11927557, 11927569, 11927577, 11927687, 11927560, 11927651, 11927611, 11927680, 11927622,
                        11927691, 11927571, 11927601};
                anims[j].delays = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
                        4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 3};
            }
            if (j == 5807) {
                anims[j].frameCount = 53;
                anims[j].loopDelay = -1;
                anims[j].forcedPriority = 6;
                anims[j].leftHandItem = -1;
                anims[j].rightHandItem = -1;
                anims[j].frameStep = 99;
                anims[j].resetWhenWalk = 0;
                anims[j].priority = 0;
                anims[j].delayType = 2;
                anims[j].oneSquareAnimation = true;
                anims[j].frameIDs = new int[]{11927612, 11927677, 11927615, 11927573, 11927618, 11927567, 11927564,
                        11927606, 11927675, 11927657, 11927690, 11927583, 11927672, 11927552, 11927563, 11927683,
                        11927639, 11927635, 11927668, 11927614, 11927627, 11927610, 11927693, 11927644, 11927656,
                        11927660, 11927629, 11927635, 11927668, 11927614, 11927627, 11927610, 11927693, 11927644,
                        11927656, 11927604, 11927637, 11927688, 11927696, 11927681, 11927605, 11927681, 11927696,
                        11927688, 11927637, 11927604, 11927656, 11927611, 11927680, 11927622, 11927691, 11927571,
                        11927601};
                anims[j].delays = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
                        4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 3};
            } // End Of Zulrah
            if (j == 618) {//start fishing
                anims[j].frameIDs = new int[]{19267634, 19267645, 19267656, 19267658, 19267659, 19267660, 19267661, 19267662, 19267663, 19267635, 19267636, 19267637, 19267638, 19267639, 19267640, 19267641, 19267642, 19267643, 19267644, 19267646, 19267647, 19267648, 19267649, 19267650, 19267651, 19267650, 19267649, 19267648, 19267647, 19267648, 19267649, 19267650, 19267651, 19267652, 19267653, 19267654, 19267655, 19267657, 19267763, 19267764, 19267765, 19267766};
            }
            if (j == 619) {
                anims[j].frameIDs = new int[]{19267664, 19267675, 19267686, 19267691, 19267692, 19267693, 19267694, 19267695, 19267696, 19267665, 19267666, 19267667, 19267668, 19267669, 19267670, 19267671, 19267672, 19267673, 19267674, 19267676, 19267677, 19267678, 19267679, 19267668, 19267669, 19267670, 19267671, 19267672, 19267673, 19267674, 19267676, 19267677, 19267678, 19267679, 19267680, 19267681, 19267682, 19267683, 19267684, 19267685, 19267687, 19267688, 19267689, 19267690};
            }
            if (j == 620) {
                anims[j].frameIDs = new int[]{19267697, 19267708, 19267719, 19267724, 19267725, 19267726, 19267727, 19267728, 19267729, 19267698, 19267699, 19267700, 19267701, 19267702, 19267703, 19267704, 19267705, 19267706, 19267707, 19267709, 19267710, 19267711, 19267712, 19267701, 19267702, 19267703, 19267704, 19267705, 19267706, 19267707, 19267709, 19267710, 19267711, 19267712, 19267713, 19267714, 19267715, 19267716, 19267717, 19267718, 19267720, 19267721, 19267722, 19267723};
            }
            if (j == 621) {
                anims[j].frameIDs = new int[]{19267697, 19267708, 19267719, 19267724, 19267725, 19267726, 19267727, 19267728, 19267729, 19267698, 19267699, 19267700, 19267701, 19267702, 19267703, 19267704, 19267705, 19267706, 19267707, 19267709, 19267710, 19267711, 19267712, 19267701, 19267702, 19267703, 19267704, 19267705, 19267706, 19267707, 19267709, 19267710, 19267711, 19267712, 19267713, 19267714, 19267715, 19267716, 19267717, 19267718, 19267720, 19267721, 19267722, 19267723};
            }
            if (j == 622) {
                anims[j].frameCount = 19;
                anims[j].frameIDs = new int[]{19267585, 19267586, 19267587, 19267588, 19267589, 19267590, 19267591, 19267592, 19267591, 19267592, 19267591, 19267592, 19267591, 19267592, 19267591, 19267591, 19267592, 19267591, 19267592};
                anims[j].delays = new int[]{15, 4, 4, 4, 12, 4, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15};
            }
            if (j == 623) {
                anims[j].frameIDs = new int[]{19267585, 19267586, 19267587, 19267588, 19267589, 19267590, 19267591, 19267592, 19267591, 19267592, 19267591, 19267592, 19267591, 19267592, 19267591, 19267591, 19267592, 19267591, 19267592};
                anims[j].delays = new int[]{15, 4, 4, 4, 12, 4, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15};
            }//end of fishing
            if (j == 5061) { //blowpipe
                anims[5061] = new Animation();
                anims[5061].frameCount = 13;
                anims[5061].frameIDs = new int[]{19267601, 19267602, 19267603, 19267604, 19267605, 19267606, 19267607, 19267606, 19267605, 19267604, 19267603, 19267602, 19267601};
                anims[5061].frameIDs2 = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                anims[5061].delays = new int[]{4, 3, 3, 4, 10, 10, 15, 10, 10, 4, 3, 3, 4};
                anims[5061].loopDelay = -1;
                anims[5061].animationFlowControl = new int[]{1, 2, 9, 11, 13, 15, 17, 19, 37, 39, 41, 43, 45, 164, 166, 168, 170, 172, 174, 176, 178, 180, 182, 183, 185, 191, 192, 9999999};
                anims[5061].oneSquareAnimation = false;
                anims[5061].forcedPriority = 6;
                anims[5061].leftHandItem = 0;
                anims[5061].rightHandItem = 13438;
                anims[5061].frameStep = 99;
                anims[5061].resetWhenWalk = 0;
                anims[5061].priority = 2;
                anims[5061].delayType = 1;
            }
            if (j == 4484) {// cerberus stand
                anims[j].frameCount = 14;
                anims[j].frameIDs = new int[]{117309461, 117309547, 117309462, 117309623, 117309548, 117309621,
                        117309454, 117309599, 117309473, 117309488, 117309559, 117309541, 117309588, 117309622};
                anims[j].frameIDs2 = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                anims[j].delays = new int[]{3, 5, 7, 7, 11, 7, 7, 5, 7, 5, 6, 9, 8, 4};
                anims[j].loopDelay = -1;
                anims[j].animationFlowControl = null;
                anims[j].oneSquareAnimation = false;
                anims[j].forcedPriority = 5;
                anims[j].leftHandItem = -1;
                anims[j].rightHandItem = -1;
                anims[j].frameStep = 99;
                anims[j].resetWhenWalk = 0;
                anims[j].priority = 0;
                anims[j].delayType = 2;
            }
            if (j == 4488) { // cerberus walk
                anims[j].frameCount = 15;
                anims[j].frameIDs = new int[]{117309535, 117309468, 117309534, 117309569, 117309581, 117309507,
                        117309443, 117309598, 117309444, 117309466, 117309576, 117309551, 117309464, 117309543,
                        117309446};
                anims[j].frameIDs2 = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                anims[j].delays = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};
                anims[j].loopDelay = -1;
                anims[j].animationFlowControl = null;
                anims[j].oneSquareAnimation = false;
                anims[j].forcedPriority = 5;
                anims[j].leftHandItem = -1;
                anims[j].rightHandItem = -1;
                anims[j].frameStep = 99;
                anims[j].resetWhenWalk = 0;
                anims[j].priority = 0;
                anims[j].delayType = 2;
            }
            if (j == 4492) { // cerberus attack
                anims[j].frameCount = 18;
                anims[j].frameIDs = new int[]{117309553, 117309490, 117309485, 117309530, 117309592, 117309531,
                        117309594, 117309583, 117309458, 117309614, 117309538, 117309524, 117309521, 117309537,
                        117309562, 117309513, 117309484, 117309616};
                anims[j].frameIDs2 = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1};
                anims[j].delays = new int[]{7, 6, 6, 7, 9, 9, 9, 6, 6, 6, 7, 6, 6, 6, 6, 6, 6, 6};
                anims[j].loopDelay = -1;
                anims[j].animationFlowControl = null;
                anims[j].oneSquareAnimation = false;
                anims[j].forcedPriority = 6;
                anims[j].leftHandItem = -1;
                anims[j].rightHandItem = -1;
                anims[j].frameStep = 99;
                anims[j].resetWhenWalk = 0;
                anims[j].priority = 0;
                anims[j].delayType = 2;
            } // end of cerberus
            if (j == 7195) {
                anims[j].frameCount = 14;
                anims[j].frameIDs = new int[]{120193037, 120193029, 120193052, 120193080, 120193048, 120193117,
                        120193047, 120193040, 120193112, 120193025, 120193090, 120193098, 120193071, 120193067};
                anims[j].frameIDs2 = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                anims[j].delays = new int[]{4, 4, 5, 5, 4, 4, 4, 4, 4, 5, 5, 4, 4, 4};
                anims[j].loopDelay = -1;
                anims[j].animationFlowControl = null;
                anims[j].oneSquareAnimation = false;
                anims[j].forcedPriority = 5;
                anims[j].leftHandItem = -1;
                anims[j].rightHandItem = -1;
                anims[j].frameStep = 99;
                anims[j].resetWhenWalk = 0;
                anims[j].priority = 0;
                anims[j].delayType = 1;
            }
            if (j == 7191) {
                anims[j].frameCount = 12;
                anims[j].frameIDs = new int[]{120193116, 120193084, 120193032, 120193046, 120193045, 120193102,
                        120193068, 120193109, 120193058, 120193086, 120193038, 120193093};
                anims[j].frameIDs2 = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                anims[j].delays = new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
                anims[j].loopDelay = -1;
                anims[j].animationFlowControl = null;
                anims[j].oneSquareAnimation = false;
                anims[j].forcedPriority = 5;
                anims[j].leftHandItem = -1;
                anims[j].rightHandItem = -1;
                anims[j].frameStep = 99;
                anims[j].resetWhenWalk = 0;
                anims[j].priority = 0;
                anims[j].delayType = 1;
            }
            if (j == 7192) { // jump
                anims[7192].frameCount = 15;
                anims[7192].frameIDs = new int[]{120193089, 120193074, 120193105, 120193063, 120193078, 120193049,
                        120193104, 120193043, 120193062, 120193054, 120193099, 120193101, 120193085, 120193030,
                        120193072};
                anims[7192].frameIDs2 = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                anims[7192].delays = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};
                anims[7192].loopDelay = -1;
                anims[7192].animationFlowControl = null;
                anims[7192].oneSquareAnimation = false;
                anims[7192].forcedPriority = 6;
                anims[7192].leftHandItem = -1;
                anims[7192].rightHandItem = -1;
                anims[7192].frameStep = 99;
                anims[7192].resetWhenWalk = 0;
                anims[7192].priority = 0;
                anims[7192].delayType = 1;
            }
            if (j == 7193) { // poison
                anims[7193].frameCount = 24;
                anims[7193].frameIDs = new int[]{120193060, 120193057, 120193113, 120193024, 120193087, 120193031,
                        120193070, 120193094, 120193066, 120193083, 120193075, 120193026, 120193061, 120193044,
                        120193108, 120193036, 120193096, 120193107, 120193056, 120193065, 120193103, 120193027,
                        120193035, 120193053};
                anims[7193].frameIDs2 = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1};
                anims[7193].delays = new int[]{3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 11, 3, 3,
                        3};
                anims[7193].loopDelay = -1;
                anims[7193].animationFlowControl = null;
                anims[7193].oneSquareAnimation = false;
                anims[7193].forcedPriority = 6;
                anims[7193].leftHandItem = -1;
                anims[7193].rightHandItem = -1;
                anims[7193].frameStep = 99;
                anims[7193].resetWhenWalk = 0;
                anims[7193].priority = 0;
                anims[7193].delayType = 1;
            }
            if (j == 4533) { // sire
                anims[j].frameCount = 20;
                anims[j].frameIDs = new int[]{119406846, 119407068, 119407215, 119406592, 119407105, 119407099,
                        119406975, 119407198, 119407023, 119406677, 119407267, 119407258, 119407023, 119406798,
                        119406975, 119407218, 119407105, 119406977, 119407215, 119406756};
                anims[j].frameIDs2 = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1};
                anims[j].delays = new int[]{3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
                anims[j].loopDelay = -1;
                anims[j].animationFlowControl = null;
                anims[j].oneSquareAnimation = false;
                anims[j].forcedPriority = 5;
                anims[j].leftHandItem = -1;
                anims[j].rightHandItem = -1;
                anims[j].frameStep = 99;
                anims[j].resetWhenWalk = 0;
                anims[j].priority = 0;
                anims[j].delayType = 2;
            }
            if (j == 4534) {
                anims[j].frameCount = 19;
                anims[j].frameIDs = new int[]{119406741, 119406935, 119406823, 119407208, 119406647, 119406777,
                        119406623, 119406805, 119407264, 119407008, 119406898, 119406743, 119407040, 119407253,
                        119406899, 119407138, 119406901, 119406719, 119406852};
                anims[j].frameIDs2 = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1};
                anims[j].delays = new int[]{3, 3, 4, 4, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3, 3, 3};
                anims[j].loopDelay = -1;
                anims[j].animationFlowControl = null;
                anims[j].oneSquareAnimation = false;
                anims[j].forcedPriority = 5;
                anims[j].leftHandItem = -1;
                anims[j].rightHandItem = -1;
                anims[j].frameStep = 99;
                anims[j].resetWhenWalk = 0;
                anims[j].priority = 0;
                anims[j].delayType = 2;
            } // end sire
            if (j == 1828) { // thermonuclear
                anims[j].frameCount = 16;
                anims[j].frameIDs = new int[]{118095921, 118095916, 118096259, 118096320, 118096299, 118096329,
                        118096036, 118095925, 118096180, 118096105, 118096311, 118095880, 118096084, 118096269,
                        118095905, 118096227};
                anims[j].frameIDs2 = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                anims[j].delays = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};
                anims[j].loopDelay = -1;
                anims[j].animationFlowControl = null;
                anims[j].oneSquareAnimation = false;
                anims[j].forcedPriority = 5;
                anims[j].leftHandItem = -1;
                anims[j].rightHandItem = -1;
                anims[j].frameStep = 99;
                anims[j].resetWhenWalk = 0;
                anims[j].priority = 0;
                anims[j].delayType = 2;
            }

            if (j == 1829) {
                anims[j].frameCount = 16;
                anims[j].frameIDs = new int[]{118096000, 118096073, 118095928, 118095889, 118095894, 118096223,
                        118096337, 118095979, 118096087, 118095980, 118096314, 118096202, 118095950, 118096110,
                        118096328, 118096221};
                anims[j].frameIDs2 = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                anims[j].delays = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};
                anims[j].loopDelay = -1;
                anims[j].animationFlowControl = null;
                anims[j].oneSquareAnimation = false;
                anims[j].forcedPriority = 5;
                anims[j].leftHandItem = -1;
                anims[j].rightHandItem = -1;
                anims[j].frameStep = 99;
                anims[j].resetWhenWalk = 0;
                anims[j].priority = 0;
                anims[j].delayType = 2;
            } // end of thermo
            if (j == 618) {
                anims[j].frameIDs = new int[]{19267634, 19267645, 19267656, 19267658, 19267659, 19267660, 19267661,
                        19267662, 19267663, 19267635, 19267636, 19267637, 19267638, 19267639, 19267640, 19267641,
                        19267642, 19267643, 19267644, 19267646, 19267647, 19267648, 19267649, 19267650, 19267651,
                        19267650, 19267649, 19267648, 19267647, 19267648, 19267649, 19267650, 19267651, 19267652,
                        19267653, 19267654, 19267655, 19267657, 19267763, 19267764, 19267765, 19267766};
            }
            if (j == 619) {
                anims[j].frameIDs = new int[]{19267664, 19267675, 19267686, 19267691, 19267692, 19267693, 19267694,
                        19267695, 19267696, 19267665, 19267666, 19267667, 19267668, 19267669, 19267670, 19267671,
                        19267672, 19267673, 19267674, 19267676, 19267677, 19267678, 19267679, 19267668, 19267669,
                        19267670, 19267671, 19267672, 19267673, 19267674, 19267676, 19267677, 19267678, 19267679,
                        19267680, 19267681, 19267682, 19267683, 19267684, 19267685, 19267687, 19267688, 19267689,
                        19267690};
            }
            if (j == 620) {
                anims[j].frameIDs = new int[]{19267697, 19267708, 19267719, 19267724, 19267725, 19267726, 19267727,
                        19267728, 19267729, 19267698, 19267699, 19267700, 19267701, 19267702, 19267703, 19267704,
                        19267705, 19267706, 19267707, 19267709, 19267710, 19267711, 19267712, 19267701, 19267702,
                        19267703, 19267704, 19267705, 19267706, 19267707, 19267709, 19267710, 19267711, 19267712,
                        19267713, 19267714, 19267715, 19267716, 19267717, 19267718, 19267720, 19267721, 19267722,
                        19267723};
            }
            if (j == 621) {
                anims[j].frameIDs = new int[]{19267697, 19267708, 19267719, 19267724, 19267725, 19267726, 19267727,
                        19267728, 19267729, 19267698, 19267699, 19267700, 19267701, 19267702, 19267703, 19267704,
                        19267705, 19267706, 19267707, 19267709, 19267710, 19267711, 19267712, 19267701, 19267702,
                        19267703, 19267704, 19267705, 19267706, 19267707, 19267709, 19267710, 19267711, 19267712,
                        19267713, 19267714, 19267715, 19267716, 19267717, 19267718, 19267720, 19267721, 19267722,
                        19267723};
            }
            if (j == 622) {
                anims[j].frameCount = 19;
                anims[j].frameIDs = new int[]{19267585, 19267586, 19267587, 19267588, 19267589, 19267590, 19267591,
                        19267592, 19267591, 19267592, 19267591, 19267592, 19267591, 19267592, 19267591, 19267591,
                        19267592, 19267591, 19267592};
                anims[j].delays = new int[]{15, 4, 4, 4, 12, 4, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15};
            }
            if (j == 623) {
                anims[j].frameIDs = new int[]{19267585, 19267586, 19267587, 19267588, 19267589, 19267590, 19267591,
                        19267592, 19267591, 19267592, 19267591, 19267592, 19267591, 19267592, 19267591, 19267591,
                        19267592, 19267591, 19267592};
                anims[j].delays = new int[]{15, 4, 4, 4, 12, 4, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15};
            }

            /*
             * Glacor anims
             */
            /*
             * if(j == 10867) { anims[j].frameCount = 19; anims[j].loopDelay =
             * 19; anims[j].delays = new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
             * 5, 5, 5, 5, 5, 5, 5, 5}; anims[j].frameIDs = new int[]{244252686,
             * 244252714, 244252760, 244252736, 244252678, 244252780, 244252817,
             * 244252756, 244252700, 244252774, 244252834, 244252715, 244252732,
             * 244252836, 244252776, 244252701, 244252751, 244252743,
             * 244252685}; }
             *
             * if(j == 10901) { anims[j].frameCount = 19; anims[j].loopDelay =
             * 19; anims[j].delays = new int[]{3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
             * 3, 3, 3, 3, 3, 3, 3, 3}; anims[j].frameIDs = new int[]{244252826,
             * 244252833, 244252674, 244252724, 244252793, 244252696, 244252787,
             * 244252753, 244252703, 244252800, 244252752, 244252744, 244252680,
             * 244252815, 244252829, 244252769, 244252699, 244252757,
             * 244252695}; }
             */
        }
    }

    private void readValuesOSRS(int id, Stream buffer) {
        try {
            id -= OSRS_ANIM_OFFSET;
            int opcode;
            while ((opcode = buffer.readUnsignedByte()) != 0) {

                if (opcode == 1) {
                    frameCount = buffer.readUnsignedWord();
                    frameIDs = new int[frameCount];
                    frameIDs2 = new int[frameCount];
                    delays = new int[frameCount];
                    for (int i = 0; i < frameCount; i++) {
                        delays[i] = buffer.readUnsignedWord();
                    }
                    for (int i = 0; i < frameCount; i++) {
                        frameIDs[i] = buffer.readUnsignedWord();
                        frameIDs2[i] = -1;
                    }
                    for (int i = 0; i < frameCount; i++) {
                        frameIDs[i] += buffer.readUnsignedWord() << 16;
                    }
                } else if (opcode == 2) {
                    loopDelay = buffer.readUnsignedWord();
                } else if (opcode == 3) {
                    int length = buffer.readUnsignedByte();
                    animationFlowControl = new int[length + 1];
                    for (int i = 0; i < length; i++) {
                        animationFlowControl[i] = buffer.readUnsignedByte();
                    }
                    animationFlowControl[length] = 9999999;
                } else if (opcode == 4) {
                    oneSquareAnimation = true;
                } else if (opcode == 5) {
                    forcedPriority = buffer.readUnsignedByte();
                } else if (opcode == 6) {
                    leftHandItem = buffer.readUnsignedWord();
                } else if (opcode == 7) {
                    rightHandItem = buffer.readUnsignedWord();
                } else if (opcode == 8) {
                    frameStep = buffer.readUnsignedByte();
                } else if (opcode == 9) {
                    resetWhenWalk = buffer.readUnsignedByte();
                } else if (opcode == 10) {
                    priority = buffer.readUnsignedByte();
                } else if (opcode == 11) {
                    delayType = buffer.readUnsignedByte();
                } else if (opcode == 12) {
                    int len = buffer.readUnsignedByte();

                    for (int i = 0; i < len; i++) {
                        buffer.readUnsignedWord();
                    }

                    for (int i = 0; i < len; i++) {
                        buffer.readUnsignedWord();
                    }
                } else if (opcode == 13) {
                    int len = buffer.readUnsignedByte();

                    for (int i = 0; i < len; i++) {
                        buffer.read3Bytes();
                    }
                } else {
                    //System.out.println("Error unrecognised OSRS anim config code: " + opcode + " for anim " + id);
                }
            }
            if (frameCount == 0) {
                frameCount = 1;
                frameIDs = new int[1];
                frameIDs[0] = -1;
                frameIDs2 = new int[1];
                frameIDs2[0] = -1;
                delays = new int[1];
                delays[0] = -1;
            }
            if (resetWhenWalk == -1)
                if (animationFlowControl != null)
                    resetWhenWalk = 2;
                else
                    resetWhenWalk = 0;
            if (priority == -1) {
                if (animationFlowControl != null) {
                    priority = 2;
                    return;
                }
                priority = 0;
            }
            if (leftHandItem == 65535)
                leftHandItem = 0;
            if (rightHandItem == 65535)
                rightHandItem = 0;
        } catch (Exception e) {
            System.out.println("Error while loading an OSRS animation(ID: " + id + ").");
            e.printStackTrace();
        }
    }

    public int getFrameLength(int i) {
        if (i > delays.length)
            return 1;
        int j = delays[i];
        if (j == 0) {
            FrameReader reader = FrameReader.forID(frameIDs[i], dataType);
            if (reader != null)
                j = delays[i] = reader.displayLength;
        }
        if (j == 0)
            j = 1;
        return j;
    }

    public void readValues(Stream stream) {
        do {
            int i = stream.readUnsignedByte();
            if (i == 0)
                break;
            if (i == 1) {
                frameCount = stream.readUnsignedWord();
                frameIDs = new int[frameCount];
                frameIDs2 = new int[frameCount];
                delays = new int[frameCount];
                for (int i_ = 0; i_ < frameCount; i_++) {
                    frameIDs[i_] = stream.readDWord();
                    frameIDs2[i_] = -1;
                }
                for (int i_ = 0; i_ < frameCount; i_++)
                    delays[i_] = stream.readUnsignedByte();
            } else if (i == 2)
                loopDelay = stream.readUnsignedWord();
            else if (i == 3) {
                int k = stream.readUnsignedByte();
                animationFlowControl = new int[k + 1];
                for (int l = 0; l < k; l++)
                    animationFlowControl[l] = stream.readUnsignedByte();
                animationFlowControl[k] = 0x98967f;
            } else if (i == 4)
                oneSquareAnimation = true;
            else if (i == 5)
                forcedPriority = stream.readUnsignedByte();
            else if (i == 6)
                leftHandItem = stream.readUnsignedWord();
            else if (i == 7)
                rightHandItem = stream.readUnsignedWord();
            else if (i == 8)
                frameStep = stream.readUnsignedByte();
            else if (i == 9)
                resetWhenWalk = stream.readUnsignedByte();
            else if (i == 10)
                priority = stream.readUnsignedByte();
            else if (i == 11)
                delayType = stream.readUnsignedByte();
            else
                System.out.println("Unrecognized seq.dat config code: " + i);
        } while (true);
        if (frameCount == 0) {
            frameCount = 1;
            frameIDs = new int[1];
            frameIDs[0] = -1;
            frameIDs2 = new int[1];
            frameIDs2[0] = -1;
            delays = new int[1];
            delays[0] = -1;
        }
        if (resetWhenWalk == -1)
            if (animationFlowControl != null)
                resetWhenWalk = 2;
            else
                resetWhenWalk = 0;
        if (priority == -1) {
            if (animationFlowControl != null) {
                priority = 2;
                return;
            }
            priority = 0;
        }
    }
}