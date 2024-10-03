package com.arlania.world.content;

import com.arlania.model.Animation;
import com.arlania.util.MathUtils;
import com.arlania.world.entity.impl.player.Player;

/**
 * Class ToyHorses Handles random messages that appear when you click on a Toy
 * Horse
 *
 * @author Levi Patton <www.rune-server.org/members/AuguryPS>
 */

public class ToyHorses {

    /**
     * String contains the data for the random messages that the player says
     * when the player clicks on a toy horse.
     */

    public static String toyHorseText() {
        final int random = MathUtils.random(3);
        switch (random) {
            case 0:
                return "Come on Dobbin, we can win the race!";
            case 1:
                return "Neaahhhyyy!";
            case 2:
                return "Giddy-up horsey!";
            default:
                return "Hi-ho Silver, and away!";
        }
    }

    /**
     * This array contains the data for the toy horses contains itemID, then the
     * animation.
     */

    private static final int[][] TOY_ID = {{2520, 918}, {2522, 919},
            {2524, 920}, {2526, 921},};

    /**
     * This boolean contains the data so it will read the array, reads the
     * itemID then in contains the animation start, text, and removing windows.
     */

    public static boolean handleToyHorsesActions(final Player player, final int itemId) {
        for (final int[] data : TOY_ID) {
            if (data[0] == itemId) {
                player.performAnimation(new Animation(data[1]));
                player.forceChat(toyHorseText());
                player.getPacketSender().sendInterfaceRemoval();
                return true;
            }
        }
        return false;
    }

}