package com.arlania.world.content;

import com.arlania.GameSettings;
import com.arlania.model.Item;
import com.arlania.world.entity.impl.player.Player;

import java.io.File;

public class UniqueTables {

    public static final File DIRECTORY = new File(System.getProperty("user.home") + File.separator + "Saves" + File.separator + "Pets");

    public static boolean isMasterUnique(final Player p, final Item item) {

        int itemid = item.getId();

        int[] MasterUniques = GameSettings.MASTERUNIQUES;

        if (item.getDefinition().isNoted()) {
            itemid -= 1;
        }
        for (int i = 0; i < MasterUniques.length; i++) {
            if (MasterUniques[i] == itemid && item.getDefinition().isNoted())
                return true;

            else if (MasterUniques[i] == itemid)
                return true;
        }
        return false;
    }

    public static boolean isLegendaryUnique(final Player p, final Item item) {

        int itemid = item.getId();

        int[] LegendaryUniques = GameSettings.LEGENDARYUNIQUES;

        if (item.getDefinition().isNoted()) {
            itemid -= 1;
        }
        for (int i = 0; i < LegendaryUniques.length; i++) {
            if (LegendaryUniques[i] == itemid && item.getDefinition().isNoted())
                return true;

            else if (LegendaryUniques[i] == itemid)
                return true;
        }
        return false;
    }

    public static boolean isHighUnique(final Player p, final Item item) {

        int itemid = item.getId();

        int[] HighUniques = GameSettings.HIGHUNIQUES;

        if (item.getDefinition().isNoted()) {
            itemid -= 1;
        }
        for (int i = 0; i < HighUniques.length; i++) {
            if (HighUniques[i] == itemid && item.getDefinition().isNoted())
                return true;

            else if (HighUniques[i] == itemid)
                return true;
        }
        return false;
    }

    public static boolean isMediumUnique(final Player p, final Item item) {

        int itemid = item.getId();

        int[] MediumUniques = GameSettings.MEDIUMUNIQUES;

        if (item.getDefinition().isNoted()) {
            itemid -= 1;
        }
        for (int i = 0; i < MediumUniques.length; i++) {
            if (MediumUniques[i] == itemid && item.getDefinition().isNoted())
                return true;

            else if (MediumUniques[i] == itemid)
                return true;
        }
        return false;
    }

    public static boolean isLowUnique(final Player p, final Item item) {

        int itemid = item.getId();

        int[] LowUniques = GameSettings.LOWUNIQUES;

        if (item.getDefinition().isNoted()) {
            itemid -= 1;
        }
        for (int i = 0; i < LowUniques.length; i++) {
            if (LowUniques[i] == itemid && item.getDefinition().isNoted())
                return true;

            else if (LowUniques[i] == itemid)
                return true;
        }
        return false;
    }


}
