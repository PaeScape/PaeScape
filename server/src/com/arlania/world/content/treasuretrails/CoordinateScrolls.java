package com.arlania.world.content.treasuretrails;

import com.arlania.model.Item;
import com.arlania.model.Position;
import com.arlania.util.RandomUtility;
import com.arlania.world.entity.impl.player.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA. User: levi Date: 11/14/16 Time: 11:03 To change
 * this template use File | Settings | File Templates.
 */
public class CoordinateScrolls {

    /* the observatory center Position */

    public static final Position OBSERVATORY_Position = new Position(2440,
            3161, 0);

    /* constants for mathematical use */

    public static final double ONE_MINUTE_TO_SQUARE = 1.875;

    public static final int ONE_DEGREE_TO_MINUTE = 60;

    /* set of constants for the tools */

    public static final int SEXTANT = 2574;
    public static final int WATCH = 2575;
    public static final int CHART = 2576;

    /* contains all the coordinate clues */

    public enum CoordinateData {
        COORDINATE_1(2723, 25, 3, 23, 24, "north", "east", 3), COORDINATE_2(
                2725, 25, 3, 17, 5, "north", "east", 3), COORDINATE_3(2731, 24,
                58, 18, 43, "north", "east", 3), COORDINATE_4(2733, 24, 56, 22,
                58, "north", "east", 3), COORDINATE_5(2735, 24, 24, 26, 24,
                "north", "east", 3), COORDINATE_6(2737, 22, 45, 26, 33,
                "north", "east", 3), COORDINATE_7(2739, 22, 35, 19, 18,
                "north", "east", 3),
        // COORDINATE_8(2741, 22, 30, 3, 1, "north", "east", 2),
        COORDINATE_9(2743, 21, 24, 17, 54, "north", "east", 3), COORDINATE_10(
                2745, 20, 33, 15, 45, "north", "east", 3), COORDINATE_11(2801,
                20, 7, 18, 33, "north", "east", 3), COORDINATE_12(2805, 20, 5,
                21, 52, "north", "east", 3), COORDINATE_13(2807, 19, 43, 25,
                07, "north", "east", 3), COORDINATE_14(2809, 18, 22, 16, 33,
                "north", "east", 3), COORDINATE_15(2811, 18, 3, 25, 16,
                "north", "east", 3), COORDINATE_16(2813, 17, 50, 8, 30,
                "north", "east", 3), COORDINATE_17(2815, 16, 43, 19, 13,
                "north", "east", 3), COORDINATE_18(2819, 16, 35, 27, 1,
                "north", "east", 3), COORDINATE_19(2817, 16, 20, 12, 45,
                "north", "east", 3), COORDINATE_20(2821, 15, 48, 13, 52,
                "north", "east", 3), COORDINATE_21(2823, 14, 54, 9, 13,
                "north", "east", 2), COORDINATE_22(2825, 13, 46, 21, 1,
                "north", "east", 3), COORDINATE_23(3526, 12, 48, 20, 20,
                "north", "east", 3), COORDINATE_24(3528, 11, 41, 14, 58,
                "north", "east", 2), COORDINATE_25(3530, 11, 5, 0, 45, "north",
                "west", 2), COORDINATE_26(3532, 11, 3, 31, 20, "north", "east",
                2), COORDINATE_27(3534, 9, 48, 17, 39, "north", "east", 2), COORDINATE_29(
                3536, 8, 33, 1, 39, "north", "east", 2),
        // COORDINATE_30(3538, 8, 26, 10, 28, "south", "east", 3),
        // COORDINATE_31(3540, 8, 5, 15, 56, "south", "east", 3),
        COORDINATE_32(3542, 8, 3, 31, 16, "north", "east", 3),
        // COORDINATE_33(3544, 7, 43, 12, 26, "south", "east", 3),
        COORDINATE_34(3546, 7, 33, 15, 0, "north", "east", 2), COORDINATE_35(
                3548, 7, 5, 30, 56, "north", "east", 2), COORDINATE_36(3550, 6,
                31, 1, 45, "north", "west", 2),
        // COORDINATE_37(3552, 6, 11, 15, 7, "south", "east", 3),
        COORDINATE_38(3554, 6, 0, 21, 48, "south", "east", 3),
        // COORDINATE_39(3556, 5, 50, 10, 5, "south", "east", 3),
        COORDINATE_40(3558, 5, 43, 23, 5, "north", "east", 2),
        // COORDINATE_41(3560, 5, 37, 31, 15, "north", "east", 3),
        COORDINATE_42(3562, 5, 20, 4, 28, "south", "east", 2),
        // COORDINATE_43(3582, 4, 41, 3, 9, "north", "west", 3),
        // COORDINATE_44(3584, 4, 16, 16, 16, "south", "east", 3),
        COORDINATE_45(3586, 4, 13, 12, 45, "north", "east", 2),
        // COORDINATE_46(3588, 4, 5, 4, 24, "south", "east", 3),
        // COORDINATE_47(3590, 4, 3, 3, 11, "south", "east", 3),
        COORDINATE_48(3592, 4, 0, 12, 46, "south", "east", 2), COORDINATE_49(
                3594, 3, 45, 22, 45, "south", "east", 3), COORDINATE_50(7256,
                3, 35, 13, 35, "south", "east", 2), COORDINATE_51(7258, 2, 50,
                6, 20, "north", "east", 2), COORDINATE_52(7260, 2, 48, 22, 30,
                "north", "east", 2), COORDINATE_53(7262, 2, 46, 29, 11,
                "north", "east", 2),
        // COORDINATE_54(7264, 1, 35, 7, 28, "south", "east", 3),
        // COORDINATE_55(7266, 1, 26, 8, 1, "north", "east", 2),
        // COORDINATE_56(7305, 1, 24, 8, 5, "north", "west", 3),
        // COORDINATE_57(7307, 1, 18, 14, 15, "south", "east", 2),
        COORDINATE_58(7309, 0, 31, 17, 43, "south", "east", 2), COORDINATE_59(
                7311, 0, 30, 24, 16, "north", "east", 2), COORDINATE_60(7313,
                0, 20, 23, 15, "south", "east", 2), COORDINATE_61(7315, 0, 18,
                9, 28, "south", "east", 2), COORDINATE_62(7317, 0, 13, 13, 15,
                "south", "east", 2), COORDINATE_63(2747, 0, 5, 1, 13, "south",
                "east", 2),
        // COORDINATE_64(2803, 0, 0, 7, 13, "north", "west", 3),
        ;
        private final int clueId;
        private final int degree1;
        private final int minutes1;
        private final int degree2;
        private final int minutes2;
        private final String hint1;
        private final String hint2;
        private final int level;

        private Position diggingPosition;

        private static final Map<Integer, CoordinateData> clues = new HashMap<Integer, CoordinateData>();
        private static final Map<Position, CoordinateData> positions = new HashMap<Position, CoordinateData>();

        public static CoordinateData forIdPosition(Position position) {
            for (int i = 0; i < CoordinateData.values().length; i++) {
                if (CoordinateData.values()[i].getDiggingPosition().equals(
                        position)) {
                    return CoordinateData.values()[i];
                }
            }
            return null;
        }

        public static CoordinateData forIdClue(int clueId) {
            return clues.get(clueId);
        }

        static {
            for (CoordinateData data : CoordinateData.values()) {
                data.diggingPosition = calculateDiggingPosition(data.degree1,
                        data.minutes1, data.degree2, data.minutes2, data.hint1,
                        data.hint2);
                clues.put(data.clueId, data);
                positions.put(data.diggingPosition, data);

            }
        }

        CoordinateData(int clueId, int degree1, int minutes1, int degree2,
                       int minutes2, String hint1, String hint2, int level) {
            this.clueId = clueId;
            this.degree1 = degree1;
            this.minutes1 = minutes1;
            this.degree2 = degree2;
            this.minutes2 = minutes2;
            this.hint1 = hint1;
            this.hint2 = hint2;
            this.level = level;
        }

        public int getClueId() {
            return clueId;
        }

        public int getDegree1() {
            return degree1;
        }

        public int getMinutes1() {
            return minutes1;
        }

        public int getDegree2() {
            return degree2;
        }

        public int getMinutes2() {
            return minutes2;
        }

        public String getHint1() {
            return hint1;
        }

        public String getHint2() {
            return hint2;
        }

        public int getLevel() {
            return level;
        }

        public Position getDiggingPosition() {
            return diggingPosition;
        }
    }

    /* loading the clue scroll interfaces */

    public static boolean loadClueInterface(Player player, int itemId) {
        CoordinateData coordinateData = CoordinateData.forIdClue(itemId);
        if (coordinateData == null) {
            return false;
        }
        InactiveClueScroll.cleanClueInterface(player);
        player.getPacketSender()
                .sendInterface(InactiveClueScroll.CLUE_SCROLL_INTERFACE);
        player.getPacketSender().sendString(
                6971, putZeroToNumber(coordinateData.getDegree1()) + " degrees "
                        + putZeroToNumber(coordinateData.getMinutes1())
                        + " minutes " + coordinateData.getHint1());
        player.getPacketSender().sendString(
                6972, putZeroToNumber(coordinateData.getDegree2()) + " degrees "
                        + putZeroToNumber(coordinateData.getMinutes2())
                        + " minutes " + coordinateData.getHint2());
        return true;
    }

    /* handling digging */

    public static boolean digClue(Player player) {
        CoordinateData coordinateData = CoordinateData
                .forIdPosition(new Position(player.getPosition().getX(), player
                        .getPosition().getY()));
        if (coordinateData == null) {
            return false;
        }
        if (!player.getInventory().contains(
                coordinateData.getClueId())) {
            return false;
        }

        player.getInventory().delete(
                new Item(coordinateData.getClueId(), 1));
        switch (coordinateData.getLevel()) {
            case 1:
                player.getInventory().add(new Item(InactiveClueScroll.CASKET_LV1, 1));
                break;
            case 2:
                player.getInventory().add(new Item(InactiveClueScroll.CASKET_LV2, 1));
                break;
            case 3:
                player.getInventory().add(new Item(InactiveClueScroll.CASKET_LV3, 1));
                break;
        }
        player.getPacketSender().sendMessage("You've found a casket!");
        return true;
    }

    /* put a 0 next to the number if its under 10 */

    private static String putZeroToNumber(int number) {
        return number < 10 ? "0" + number : "" + number;
    }

    /* calculating the position of digging place with hint provided */

    public static Position calculateDiggingPosition(int degree1, int minutes1,
                                                    int degree2, int minutes2, String firstHint, String secondHint) {
        int obsX = OBSERVATORY_Position.getX();
        int obsY = OBSERVATORY_Position.getY();

        /* first hint handling */

        if (firstHint == "north") {
            obsY += (int) Math
                    .ceil(((degree1 * ONE_DEGREE_TO_MINUTE + minutes1) / ONE_MINUTE_TO_SQUARE));
        }
        if (firstHint == "south") {
            obsY -= (int) Math
                    .ceil(((degree1 * ONE_DEGREE_TO_MINUTE + minutes1) / ONE_MINUTE_TO_SQUARE));
        }
        if (firstHint == "east") {
            obsX += (int) Math
                    .ceil(((degree1 * ONE_DEGREE_TO_MINUTE + minutes1) / ONE_MINUTE_TO_SQUARE));
        }
        if (firstHint == "west") {
            obsX -= (int) Math
                    .ceil(((degree1 * ONE_DEGREE_TO_MINUTE + minutes1) / ONE_MINUTE_TO_SQUARE));
        }

        /* second hint handling */

        if (secondHint == "north") {
            obsY += (int) Math
                    .ceil(((degree2 * ONE_DEGREE_TO_MINUTE + minutes2) / ONE_MINUTE_TO_SQUARE));
        }
        if (secondHint == "south") {
            obsY -= (int) Math
                    .ceil(((degree2 * ONE_DEGREE_TO_MINUTE + minutes2) / ONE_MINUTE_TO_SQUARE));
        }
        if (secondHint == "east") {
            obsX += (int) Math
                    .ceil(((degree2 * ONE_DEGREE_TO_MINUTE + minutes2) / ONE_MINUTE_TO_SQUARE));
        }
        if (secondHint == "west") {
            obsX -= (int) ((degree2 * ONE_DEGREE_TO_MINUTE + minutes2) / ONE_MINUTE_TO_SQUARE);
        }
        return new Position(obsX, obsY);

    }

    /* gets the hint with coordinate provided */
    public static String[] calculateActualPosition(int x, int y) {
        int obsX = OBSERVATORY_Position.getX();
        int obsY = OBSERVATORY_Position.getY();
        int differenceX = x - obsX;
        int differenceY = y - obsY;
        double minutesX = Math.abs(differenceX) * ONE_MINUTE_TO_SQUARE;
        double minutesY = Math.abs(differenceY) * ONE_MINUTE_TO_SQUARE;
        int finalMinutesX = (int) Math.ceil(minutesX) % ONE_DEGREE_TO_MINUTE;
        int finalMinutesY = (int) Math.ceil(minutesY) % ONE_DEGREE_TO_MINUTE;
        int degreeX = (int) (minutesX / ONE_DEGREE_TO_MINUTE);
        int degreeY = (int) (minutesY / ONE_DEGREE_TO_MINUTE);
        /* setting the first strings */
        String XAxis = (differenceX < 0 ? "west" : "east");
        String YAxis = (differenceY < 0 ? "south" : "north");

        /* returning the final strings */
        return new String[]{
                degreeY + " degrees, " + finalMinutesY + " minutes " + YAxis,
                degreeX + " degrees, " + finalMinutesX + " minutes " + XAxis};
    }

    /* getting a random coordinate clue */

    public static int getRandomScroll(int level) {
        int pick = RandomUtility.RANDOM.nextInt(CoordinateData.values().length);
        while (CoordinateData.values()[pick].getLevel() != level) {
            pick = RandomUtility.RANDOM.nextInt(CoordinateData.values().length);
        }

        return CoordinateData.values()[pick].getClueId();
    }

}
