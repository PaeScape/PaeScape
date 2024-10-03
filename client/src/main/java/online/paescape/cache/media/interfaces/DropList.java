package online.paescape.cache.media.interfaces;

import online.paescape.cache.media.TextDrawingArea;

import static online.paescape.cache.media.interfaces.RSInterface.*;

public class DropList {
    public static void unpack(TextDrawingArea[] tda) {
        RSInterface tab = addInterface(24000);
        addSpriteLoader(24001, 1036);

        addHoverButtonWSpriteLoader(24002, 1037, 21, 21, "Close", 0, 24003, 1);
        addHoveredImageWSpriteLoader(24003, 1038, 21, 21, 24004);

        addText(24006, "Loot Viewer", tda, 2, 16753920, true, true);
        int x = 7;
        int y = 7;
        tab.totalChildren(11);
        tab.child(0, 24001, 0 + x, 0 + y);
        tab.child(1, 24002, 472 + x, 7 + y);
        tab.child(2, 24003, 472 + x, 7 + y);
        tab.child(3, 24006, 250 + x, 11 + y);

        addClickableText(24007, "@whi@NPC Search", "Filter", tda, 0, 16711680, true, true, 134);
        tab.child(4, 24007, 14 + x, 44 + y);

        tab.child(5, 24008, 150 + x, 58 + y);

        addClickableText(19687, "@whi@Item Search", "Filter", tda, 0, 16711680, true, true, 134);
        tab.child(7, 19687, 15 + x, 73 + y);

        tab.child(6, 24009, 6 + x, 94 + y);

        addText(24114, "Name", tda, 2, 16748608, true, true);
        addText(24112, "Amount", tda, 2, 16748608, true, true);
        addText(24113, "Avg kills", tda, 2, 16748608, true, true);
        tab.child(8, 24114, 213, 45);
        tab.child(9, 24112, 388, 45);
        tab.child(10, 24113, 460, 45);

        RSInterface results = addInterface(24009);
        results.width = 122;
        results.height = 218;
        results.scrollMax = 1430;
        results.totalChildren(100);

        for (int j = 0; j < 100; ++j) {
            addClickableText(24010 + j, "", "View Drops", tda, 0, 0xffffff, false, true, 110);
            results.child(j, 24010 + j, 3, 6 + j * 14);
        }

        RSInterface main = addInterface(24008);
        main.totalChildren(480);
        main.width = 328;
        main.height = 257;
        main.scrollMax = 32 * 96;
        addSpriteLoader(24110, 1039);
        addSpriteLoader(24111, 1040);

        int frame = 0;
        int i;
        for (i = 0; i < 48; ++i) {
            main.child(frame++, 24110, 0, i * 64);
            main.child(frame++, 24111, 0, 32 + i * 64);
        }

        int j = 0;
        for (i = 0; i < 96; ++i) {
            addToItemGroup(24115 + j, 1, 1, 1, 1, false, new String[]{null, null, null});
            addText(24115 + j + 1, "Name", tda, 1, 16753920, false, true);
            addText(24115 + j + 2, "Amount", tda, 0, 16777215, true, true);
            addText(24115 + j + 3, "Kills", tda, 0, 16777215, true, true);
            int yy = i * 32;
            main.child(frame++, 24115 + j, 1, 0 + yy);
            main.child(frame++, 24115 + j + 1, 39, 8 + yy);
            main.child(frame++, 24115 + j + 2, 230, 11 + yy);
            main.child(frame++, 24115 + j + 3, 300, 11 + yy);
            j += 4;
        }
    }
}
