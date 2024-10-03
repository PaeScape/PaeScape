package com.arlania.util;

import com.arlania.GameServer;
import com.arlania.model.Item;
import com.arlania.model.Position;
import com.arlania.world.content.combat.CombatContainer.CombatHit;
import com.arlania.world.entity.impl.player.Player;
import org.jboss.netty.buffer.ChannelBuffer;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.NumberFormat;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.stream.IntStream;
import java.util.zip.GZIPInputStream;

public class Misc {

    private static ZonedDateTime zonedDateTime;
    public static final int HALF_A_DAY_IN_MILLIS = 43200000;

    public static final String sendCashToString(long j) {
        if (j >= 0 && j < 10000)
            return String.valueOf(j);
        else if (j >= 10000 && j < 10000000)
            return j / 1000 + "K";
        else if (j >= 10000000 && j < 999999999)
            return j / 1000000 + "M";
        else
            return Long.toString(j);
    }

    public static <T> T randomTypeOfList(List<T> list) {
        if (list == null || list.size() == 0)
            return null;
        return list.get(RandomUtility.RANDOM.nextInt(list.size()));
    }

    public static CombatHit[] concat(CombatHit[] a, CombatHit[] b) {
        int aLen = a.length;
        int bLen = b.length;
        CombatHit[] c = new CombatHit[aLen + bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }

    public static List<Player> getCombinedPlayerList(Player p) {
        List<Player> plrs = new LinkedList<Player>();
        for (Player localPlayer : p.getLocalPlayers()) {
            if (localPlayer != null)
                plrs.add(localPlayer);
        }
        plrs.add(p);
        return plrs;
    }

    public static String getCurrentServerTime() {
        zonedDateTime = ZonedDateTime.now();
        int hour = zonedDateTime.getHour();
        String hourPrefix = hour < 10 ? "0" + hour : "" + hour;
        int minute = zonedDateTime.getMinute();
        String minutePrefix = minute < 10 ? "0" + minute : "" + minute;
        return hourPrefix + ":" + minutePrefix;
    }

    public static String getTimePlayed(long totalPlayTime) {
        final int sec = (int) (totalPlayTime / 1000), h = sec / 3600, m = sec / 60 % 60, s = sec % 60;
        return (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
    }

    public static int getMinutesPlayed(Player p) {
        long totalPlayTime = p.getTotalPlayTime() + (p.getRecordedLogin().elapsed());
        int sec = (int) (totalPlayTime / 1000), h = sec / 3600, m = sec / 60 % 60;
        for (int i = 0; i < h; i++)
            m += 60;
        return m;
    }

    public static String getHoursPlayed(long totalPlayTime) {
        final int sec = (int) (totalPlayTime / 1000), h = sec / 3600;
        return (h < 10 ? "0" + h : h) + "h";
    }

    public static int getMinutesPassed(long t) {
        int seconds = (int) ((t / 1000) % 60);
        int minutes = (int) (((t - seconds) / 1000) / 60);
        return minutes;
    }

    public static Item[] concat(Item[] a, Item[] b) {
        int aLen = a.length;
        int bLen = b.length;
        Item[] c = new Item[aLen + bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }

    public static Player getCloseRandomPlayer(List<Player> plrs) {
        int index = RandomUtility.inclusiveRandom(plrs.size() - 1);
        if (index > 0)
            return plrs.get(index);
        return null;
    }

    public static byte[] directionDeltaX = new byte[]{0, 1, 1, 1, 0, -1, -1, -1};
    public static byte[] directionDeltaY = new byte[]{1, 1, 0, -1, -1, -1, 0, 1};
    public static byte[] xlateDirectionToClient = new byte[]{1, 2, 4, 7, 6, 5, 3, 0};

    public static int direction(int srcX, int srcY, int x, int y) {
        double dx = (double) x - srcX, dy = (double) y - srcY;
        double angle = Math.atan(dy / dx);
        angle = Math.toDegrees(angle);
        if (Double.isNaN(angle))
            return -1;
        if (Math.signum(dx) < 0)
            angle += 180.0;
        return (int) ((((90 - angle) / 22.5) + 16) % 16);
		/*int changeX = x - srcX; int changeY = y - srcY;
		for (int j = 0; j < directionDeltaX.length; j++) {
			if (changeX == directionDeltaX[j] &&
				changeY == directionDeltaY[j])
				return j;

		}
		return -1;*/
    }

    public static String ucFirst(String str) {
        str = str.toLowerCase();
        if (str.length() > 1) {
            str = str.substring(0, 1).toUpperCase() + str.substring(1);
        } else {
            return str.toUpperCase();
        }
        return str;
    }

    public static String format(int num) {
        return NumberFormat.getInstance().format(num);
    }

    public static String formatText(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (i == 0) {
                s = String.format("%s%s", Character.toUpperCase(s.charAt(0)),
                        s.substring(1));
            }
            if (!Character.isLetterOrDigit(s.charAt(i))) {
                if (i + 1 < s.length()) {
                    s = String.format("%s%s%s", s.subSequence(0, i + 1),
                            Character.toUpperCase(s.charAt(i + 1)),
                            s.substring(i + 2));
                }
            }
        }
        return s.replace("_", " ");
    }

    public static String getTotalAmount(int j) {
        if (j >= 10000 && j < 10000000) {
            return j / 1000 + "K";
        } else if (j >= 10000000 && j <= 2147483647) {
            return j / 1000000 + "M";
        } else {
            return j + " coins";
        }
    }

    public static String formatPlayerName(String str) {
        String str1 = Misc.ucFirst(str);
        str1.replace("_", " ");
        return str1;
    }

    public static String insertCommasToNumber(String number) {
        return number.length() < 4 ? number : insertCommasToNumber(number
                .substring(0, number.length() - 3))
                + ","
                + number.substring(number.length() - 3);
    }

    private static final char[] decodeBuf = new char[4096];

    public static String textUnpack(byte[] packedData, int size) {
        int idx = 0, highNibble = -1;
        for (int i = 0; i < size * 2; i++) {
            int val = packedData[i / 2] >> (4 - 4 * (i % 2)) & 0xf;
            if (highNibble == -1) {
                if (val < 13)
                    decodeBuf[idx++] = xlateTable[val];
                else
                    highNibble = val;
            } else {
                decodeBuf[idx++] = xlateTable[((highNibble << 4) + val) - 195];
                highNibble = -1;
            }
        }

        return new String(decodeBuf, 0, idx);
    }

    public static char[] xlateTable = {' ', 'e', 't', 'a', 'o', 'i', 'h', 'n',
            's', 'r', 'd', 'l', 'u', 'm', 'w', 'c', 'y', 'f', 'g', 'p', 'b',
            'v', 'k', 'x', 'j', 'q', 'z', '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', ' ', '!', '?', '.', ',', ':', ';', '(', ')', '-',
            '&', '*', '\\', '\'', '@', '#', '+', '=', '\243', '$', '%', '"',
            '[', ']'};

    public static String anOrA(String s) {
        s = s.toLowerCase();
        if (s.equalsIgnoreCase("anchovies") || s.equalsIgnoreCase("soft clay") || s.equalsIgnoreCase("cheese") || s.equalsIgnoreCase("ball of wool") || s.equalsIgnoreCase("spice") || s.equalsIgnoreCase("steel nails") || s.equalsIgnoreCase("snape grass") || s.equalsIgnoreCase("coal"))
            return "some";
        if (s.startsWith("a") || s.startsWith("e") || s.startsWith("i") || s.startsWith("o") || s.startsWith("u"))
            return "an";
        return "a";
    }

    @SuppressWarnings("rawtypes")
    public static Class[] getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    @SuppressWarnings("rawtypes")
    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    public static String removeSpaces(String s) {
        return s.replace(" ", "");
    }

    public static int getMinutesElapsed(int minute, int hour, int day, int year) {
        Calendar i = Calendar.getInstance();

        if (i.get(1) == year) {
            if (i.get(6) == day) {
                if (hour == i.get(11)) {
                    return i.get(12) - minute;
                }
                return (i.get(11) - hour) * 60 + (59 - i.get(12));
            }

            int ela = (i.get(6) - day) * 24 * 60 * 60;
            return ela > 2147483647 ? 2147483647 : ela;
        }

        int ela = getElapsed(day, year) * 24 * 60 * 60;

        return ela > 2147483647 ? 2147483647 : ela;
    }

    public static int getDayOfYear() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int days = 0;
        int[] daysOfTheMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0)) {
            daysOfTheMonth[1] = 29;
        }
        days += c.get(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < daysOfTheMonth.length; i++) {
            if (i < month) {
                days += daysOfTheMonth[i];
            }
        }
        return days;
    }

    public static int getYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }

    /**
     * Sets up money representing it as 1000K (1,000,000)
     *
     * @param quantity the money
     * @return the money
     */
    public static String setupMoney(long quantity) {
        if (quantity < -1) {
            quantity = Long.MAX_VALUE;
        }
        return currency(quantity) + " (" + formatLong(quantity) + " gp)";
    }

    /**
     * Gets symbol for money
     *
     * @param quantity the amount
     * @return the symbol
     */
    public static String currency(final long quantity) {
        if (quantity >= 10000 && quantity < 10000000) {
            return quantity / 1000 + "K";
        } else if (quantity >= 10000000 && quantity <= Integer.MAX_VALUE) {
            return quantity / 1000000 + "M";
        } else if (quantity > Integer.MAX_VALUE && quantity <= Long.MAX_VALUE) {
            return quantity / 10000000 + "B";
        } else {
            return quantity + " gp";
        }
    }

    /**
     * Formats numerals
     *
     * @param num the number
     * @return formated number
     */
    public static String formatLong(final long num) {
        return NumberFormat.getInstance().format(num);
    }

    public static int random(final int[] collection) {
        return collection[RandomUtility.inclusiveRandom(collection.length - 1)];
    }

    public static String random(final String[] collection) {
        return collection[RandomUtility.inclusiveRandom(collection.length - 1)];
    }

    public static Position random(final Position[] collection) {
        return collection[RandomUtility.inclusiveRandom(collection.length - 1)];
    }

    public static int getElapsed(int day, int year) {
        if (year < 2013) {
            return 0;
        }

        int elapsed = 0;
        int currentYear = Misc.getYear();
        int currentDay = Misc.getDayOfYear();

        if (currentYear == year) {
            elapsed = currentDay - day;
        } else {
            elapsed = currentDay;

            for (int i = 1; i < 5; i++) {
                if (currentYear - i == year) {
                    elapsed += 365 - day;
                    break;
                } else {
                    elapsed += 365;
                }
            }
        }

        return elapsed;
    }

    public static boolean isWeekend() {
        int day = Calendar.getInstance().get(7);
        return (day == 1) || (day == 6) || (day == 7);
    }

    public static byte[] readFile(File s) {
        try {
            FileInputStream fis = new FileInputStream(s);
            FileChannel fc = fis.getChannel();
            ByteBuffer buf = ByteBuffer.allocate((int) fc.size());
            fc.read(buf);
            buf.flip();
            fis.close();
            return buf.array();
        } catch (Exception e) {
            GameServer.getLogger().log(Level.SEVERE, "FILE : " + s.getName() + " missing.", e);
            return null;
        }
    }

    public static byte[] getBuffer(File f) throws Exception {
        if (!f.exists())
            return null;
        byte[] buffer = new byte[(int) f.length()];
        DataInputStream dis = new DataInputStream(new FileInputStream(f));
        dis.readFully(buffer);
        dis.close();
        byte[] gzipInputBuffer = new byte[999999];
        int bufferlength = 0;
        GZIPInputStream gzip = new GZIPInputStream(new ByteArrayInputStream(buffer));
        do {
            if (bufferlength == gzipInputBuffer.length) {
                System.out.println("Error inflating data.\nGZIP buffer overflow.");
                break;
            }
            int readByte = gzip.read(gzipInputBuffer, bufferlength, gzipInputBuffer.length - bufferlength);
            if (readByte == -1)
                break;
            bufferlength += readByte;
        } while (true);
        byte[] inflated = new byte[bufferlength];
        System.arraycopy(gzipInputBuffer, 0, inflated, 0, bufferlength);
        buffer = inflated;
        if (buffer.length < 10)
            return null;
        return buffer;
    }

    public static int getTimeLeft(long start, int timeAmount, TimeUnit timeUnit) {
        start -= timeUnit.toMillis(timeAmount);
        long elapsed = System.currentTimeMillis() - start;
        int toReturn = timeUnit == TimeUnit.SECONDS ? (int) ((elapsed / 1000) % 60) - timeAmount : (int) ((elapsed / 1000) / 60) - timeAmount;
        if (toReturn <= 0)
            toReturn = 1;
        return timeAmount - toReturn;
    }

    /**
     * Converts an array of bytes to an integer.
     *
     * @param data the array of bytes.
     * @return the newly constructed integer.
     */
    public static int hexToInt(byte[] data) {
        int value = 0;
        int n = 1000;
        for (int i = 0; i < data.length; i++) {
            int num = (data[i] & 0xFF) * n;
            value += num;
            if (n > 1) {
                n = n / 1000;
            }
        }
        return value;
    }

    public static Position delta(Position a, Position b) {
        return new Position(b.getX() - a.getX(), b.getY() - a.getY());
    }

    /**
     * Picks a random element out of any array type.
     *
     * @param array the array to pick the element from.
     * @return the element chosen.
     */
    public static <T> T randomElement(T[] array) {
        return array[(int) (RandomUtility.RANDOM.nextDouble() * array.length)];
    }

    /**
     * Picks a random element out of any list type.
     *
     * @param list the list to pick the element from.
     * @return the element chosen.
     */
    public static <T> T randomElement(List<T> list) {
        return list.get((int) (RandomUtility.RANDOM.nextDouble() * list.size()));
    }

    /**
     * Reads string from a data input stream.
     *
     * @param inputStream The input stream to read string from.
     * @return The String value.
     */
    public static String readString(ChannelBuffer buffer) {
        StringBuilder builder = new StringBuilder();
        try {
            byte data;
            while ((data = buffer.readByte()) != 10) {
                builder.append((char) data);
            }
        } catch (IndexOutOfBoundsException e) {

        }
        return builder.toString();
    }

    public static final String[] BLOCKED_WORDS = {
            "devious", "firepk", "ventrilica", "ventrili",
            "hade5", "hades5", "pvplegacy", "junglepk", "runeinsanit",
            "n3t", "n e t", "n et", "ne t", "c  0 m", "c 0 m", "C 0  m",
            "c  o  m", "runeinsanity", "runeinsanit", "runeinsan",
            "rune-insanity", "rune-insanit", ".c0", ".c0m",
            "legendsdomain", "createaforum", "vampirez", "-scape", "os-base",
            "rswebclients", "pvplegacy", "junglepk", ".n3t", ".c0m", "c0m",
            "n3t", ".tk", ",net", ",runescape", "pvpmaster", "pvpmasters",
            "PvPMasters.", ",org", ",com", ",be", ",nl", ",info", "dspk",
            ".info", ".org", ".tk", ".net", ".com", ".co.uk", ".be", ".nl",
            ".dk", ".co.cz", ".co", ".us", ".biz", ".eu", ".de", ".cc", ". n e  t",
            ".i n f o", ".o r g", ".t k", ".n e t", ".c o m", ".c o . u k",
            ".b e", ".n l", ".d k", ".c o . c z", ".c o", ".u s", ".b i z",
            ".e u", ".d e", ".c c", ".(c)om", "(.)", "kandarin", "o r g", "www",
            "w w w", "w.w.w", "voidrsps", "void-ps", "desolace", "ikov", "soulsplit",
            "soulspawn", "atiloc", "<img", "@cr", "i k o v", "ik0v", "<img=", "@cr", ":tradereq:", ":duelreq:",
            "<col=", "<shad=", "hostilityps", "hostilityp", "hostility ps"
            , "0s-base", "os -base", "os - base", "o s b a s e", "o s - b a s e"};


    public static boolean blockedWord(String string) {
        for (String s : BLOCKED_WORDS) {
            if (string.contains(s)) {
                return true;
            }
        }
        return false;
    }

    public static String filterMessage(Player player, String message) {
        for (String auto : BLOCKED_WORDS) {
            if (message.toLowerCase().contains(auto) && !player.getStaffRights().isStaff()) {
                String replace = "";
                for (int i = 0; i < auto.length(); i++)
                    replace += "*";
                message = message.replaceAll(auto, replace);
            }
        }
        return message;
    }

    public static String combatDiffColor(int i, int j) {
        int k = i - j;
        if (k < -9) {
            return "@red@";
        }
        if (k < -6) {
            return "@or3@";
        }
        if (k < -3) {
            return "@or2@";
        }
        if (k < 0) {
            return "@or1@";
        }
        if (k > 9) {
            return "@gre@";
        }
        if (k > 6) {
            return "@gr3@";
        }
        if (k > 3) {
            return "@gr2@";
        }
        if (k > 0) {
            return "@gr1@";
        } else {
            return "@yel@";
        }
    }

    public static String format(long num) {
        return NumberFormat.getInstance().format(num);
    }
}
