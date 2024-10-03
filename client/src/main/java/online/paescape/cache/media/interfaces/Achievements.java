package online.paescape.cache.media.interfaces;

import online.paescape.cache.media.TextDrawingArea;

public class Achievements {
    private static final int ORANGE = 0xFF8624;
    private static final int GREY = 0x858585;
    private static final int LIGHT_GREY = 0xC2C2C2;
    private static final int PROGRESS_BAR_COLOR = 0xFFAE00;

    public static void unpack(TextDrawingArea[] tda) {
        int id = 23150;
        int frame = 0;
        RSInterface rs = RSInterface.addInterface(id++);
        rs.totalChildren(26);

        //background
        RSInterface.addSpriteLoader(id, 1041);
        rs.child(frame++, id++, 14, 15);

        //title
        RSInterface.addText(id, "Achievement Diary", RSInterface.fonts, 2, ORANGE, true, true);
        rs.child(frame++, id++, 259, 24);


        //title
        RSInterface.addText(id, "Achievements", RSInterface.fonts, 2, ORANGE, true, true);
        rs.child(frame++, id++, 107, 76);

        RSInterface.addText(id, "Points/Exp you'll receive", RSInterface.fonts, 0, ORANGE, true, true);
        rs.child(frame++, id++, 266, 176);

        RSInterface.addText(id, "Items you'll receive", RSInterface.fonts, 0, ORANGE, true, true);
        rs.child(frame++, id++, 413, 176);

        //Close Button
        RSInterface.addHoverButtonWSpriteLoader(id, 273, 16, 16, "Close Window", 0, id + 1, 1);
        RSInterface.addHoveredImageWSpriteLoader(id + 1, 274, 16, 16, id + 2);
        rs.child(frame++, id, 472, 24);
        rs.child(frame++, id + 1, 472, 24);
        id += 3;

        //collect button
        RSInterface.addHoverButtonWSpriteLoader(id, 1044, 35, 25, "Collect Reward", 0, id + 1, 1);
        RSInterface.addHoveredImageWSpriteLoader(id + 1, 1045, 35, 25, id + 2);
        rs.child(frame++, id, 199, 59);
        rs.child(frame++, id + 1, 199, 59);
        id += 3;

        //achievement title
        RSInterface.addText(id, "Achievement Name", RSInterface.fonts, 2, ORANGE, false, true);
        rs.child(frame++, id++, 240, 55);

        //achievement subtitle
        RSInterface.addText(id, "Skill", RSInterface.fonts, 1, GREY, false, true);
        rs.child(frame++, id++, 240, 70);


        //tabs
        RSInterface.addConfigButtonWSpriteLoader(id, id, 1042, 1043, 42, 20, "Skill", 0, 5, 1086);
        RSInterface.addConfigButtonWSpriteLoader(id + 1, id, 1042, 1043, 42, 20, "Boss", 1, 5, 1086);
        RSInterface.addConfigButtonWSpriteLoader(id + 2, id, 1042, 1043, 42, 20, "PvM", 2, 5, 1086);
        RSInterface.addConfigButtonWSpriteLoader(id + 3, id, 1042, 1043, 42, 20, "Misc", 3, 5, 1086);
        rs.child(frame++, id++, 24, 53);
        rs.child(frame++, id++, 65, 53);
        rs.child(frame++, id++, 106, 53);
        rs.child(frame++, id++, 147, 53);

        //tabs text
        RSInterface.addText(id, "Skill", RSInterface.fonts, 1, GREY, true, true);
        rs.child(frame++, id++, 44, 56);
        RSInterface.addText(id, "Boss", RSInterface.fonts, 1, GREY, true, true);
        rs.child(frame++, id++, 85, 56);
        RSInterface.addText(id, "Raid", RSInterface.fonts, 1, GREY, true, true);
        rs.child(frame++, id++, 126, 56);
        RSInterface.addText(id, "Misc", RSInterface.fonts, 1, GREY, true, true);
        rs.child(frame++, id++, 167, 56);

        //wrappable description
        RSInterface.addWrappingText(id, "A simple description of the achievement. A simple description of the achievement. A simple description of the achievement.", tda, 1, LIGHT_GREY, false, true, 279, 14);

        rs.child(frame++, id++, 200, 95);


        //wrappable points/exp description
        RSInterface.addWrappingText(id, "-X Points.\\n-X Points.\\n-X Points.", tda, 1, LIGHT_GREY, false, true, 129, 14);
        rs.child(frame++, id++, 200, 194);

        //item container
        RSInterface.addContainer(id, 0, 3, 3, 10, 6, false, null, null, null, null, null);
        for (int i = 0; i < 9; i++) {
            RSInterface.interfaceCache[id].inv[i] = i % 2 == 0 ? 4152 : 4154;
            RSInterface.interfaceCache[id].invStackSizes[i] = 1;
        }
        rs.child(frame++, id++, 357, 194);

        //progress bar
        RSInterface.addRectangle(id, 0, 0x000000, true, 276, 6);
        rs.child(frame++, id++, 202, 156);
        createProgressBar(23178);
        rs.child(frame++, 23178, 203, 157);

        //progress text
        RSInterface.addText(id, "45% | 45/100", RSInterface.fonts, 2, 0xFFFFFF, true, true);
        rs.child(frame++, id++, 341, 152);
        RSInterface.interfaceCache[id - 1].superShadow = true;

        createList();
        rs.child(frame++, 23180, 26, 95);
    }

    private static void createProgressBar(int id) {
        int frame = 0;
        RSInterface rs = RSInterface.addInterface(id++);
        rs.totalChildren(1);
        rs.width = 280;
        rs.height = 4;

        RSInterface.addRectangle(id, 0, PROGRESS_BAR_COLOR, true, 274, 4);
        rs.child(frame++, id++, 0, 0);
    }

    private static void createList() {
        int id = 23180;
        int frame = 0;
        RSInterface rs = RSInterface.addInterface(id++);
        int totalSlots = 80;
        rs.totalChildren(totalSlots * 2);
        rs.width = 146;
        rs.height = 212;
        rs.scrollMax = totalSlots * 22;

        for (int i = 0; i < totalSlots; i++) {
            RSInterface.addButtonWSpriteLoader(id, i % 2 == 0 ? 1046 : 1047, "Select");
            rs.child(frame++, id++, 0, i * 22);
            RSInterface.addText(id, "Achievement " + i, RSInterface.fonts, 0, ORANGE, false, true);
            rs.child(frame++, id++, 4, i * 22 + 6);
        }
    }
}
