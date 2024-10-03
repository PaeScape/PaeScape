package com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.attacks;


import com.arlania.GameSettings;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.CombatIcon;
import com.arlania.model.Hit;
import com.arlania.model.Hitmask;
import com.arlania.model.Position;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.prayer.CurseHandler;
import com.arlania.world.content.combat.prayer.PrayerHandler;
import com.arlania.world.entity.impl.player.Player;

import java.util.ArrayList;

public class Attacks {

    /*
     * OLM ANIMS
     */
    public static final int LEFTOVER_CRYSTALS = 1338 + GameSettings.OSRS_GFX_OFFSET;
    public static final int DARK_GREEN_FLYING = 1339 + GameSettings.OSRS_GFX_OFFSET;
    public static final int GREEN_CRYSTAL_FLYING = 1340 + GameSettings.OSRS_GFX_OFFSET;
    public static final int PURPLE_ORB = 1341 + GameSettings.OSRS_GFX_OFFSET;
    public static final int GREEN_ORB = 1343 + GameSettings.OSRS_GFX_OFFSET;
    public static final int RED_ORB = 1345 + GameSettings.OSRS_GFX_OFFSET;
    // public static final int FALLING_CRYSTAL = 1346;
    public static final int FIRE_BLAST = 1347 + GameSettings.OSRS_GFX_OFFSET;
    public static final int SMALL_FIRE_BLAST = 1348 + GameSettings.OSRS_GFX_OFFSET;
    public static final int GREEN_PROJECTILE = 1349 + GameSettings.OSRS_GFX_OFFSET;
    public static final int SMALL_FIRE = 1351 + GameSettings.OSRS_GFX_OFFSET;

    public static final int CRYSTAL = 1352;

    public static final int DARK_GREEN_SMALL_PROJECTILE = 1354 + GameSettings.OSRS_GFX_OFFSET;
    public static final int BLUE_SMALL_PROJECTILE = 1355 + GameSettings.OSRS_GFX_OFFSET;

    public static final int GREEN_LIGHTNING = 1356 + GameSettings.OSRS_GFX_OFFSET;
    public static final int FALLING_CRYSTAL = 1357 + GameSettings.OSRS_GFX_OFFSET;
    public static final int GREEN_PUFF = 1358 + GameSettings.OSRS_GFX_OFFSET;

    public static final int WHITE_CIRCLE = 1359 + GameSettings.OSRS_GFX_OFFSET;
    public static final int GREEN_CIRCLE = 1360 + GameSettings.OSRS_GFX_OFFSET;
    public static final int ORANGE_CIRCLE = 1361 + GameSettings.OSRS_GFX_OFFSET;
    public static final int PURPLE_CIRCLE = 1362 + GameSettings.OSRS_GFX_OFFSET;
    public static final int RED_CIRCLE = 246 + GameSettings.OSRS_GFX_OFFSET;

    public static final int BLUE_BUBBLES = 1363 + GameSettings.OSRS_GFX_OFFSET;

    public static void hitPlayer(Player player, int min, int max, CombatType combatType, CombatIcon style,
                                 final int delay, boolean protectable) {
        TaskManager.submit(new Task(delay, true) {

            @Override
            public void execute() {
                if (protectable) {
                    if ((PrayerHandler.isActivated(player, PrayerHandler.getProtectingPrayer(combatType))
                            || CurseHandler.isActivated(player, CurseHandler.getProtectingPrayer(combatType)))) {
                        player.dealDamage(
                                new Hit((min / 10) + RandomUtility.inclusiveRandom((max - min) / 10), Hitmask.RED, style));
                    } else {
                        player.dealDamage(new Hit(min + RandomUtility.inclusiveRandom(max - min), Hitmask.RED, style));
                    }
                } else {
                    player.dealDamage(new Hit(min + RandomUtility.inclusiveRandom(max - min), Hitmask.RED, style));
                }

                stop();
            }
        });

    }

    public static Position randomLocation(int height) {
        ArrayList<Position> positions = new ArrayList<>();

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 17; y++) {

                int posX = x + 3228;
                int posY = y + 5731;

                if (posX != 3237 && posY != 5748)
                    positions.add(new Position(posX, posY, height));
            }
        }

        return positions.get(RandomUtility.inclusiveRandom(positions.size() - 1));
    }

}
