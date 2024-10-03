package com.arlania.world.content.combat.magic;

import com.arlania.model.Item;
import com.arlania.util.RandomUtility;
import com.arlania.world.entity.impl.player.Player;

/**
 * A set of constants representing the staves that can be used in place of
 * runes.
 *
 * @author lare96
 */

public class PlayerRunePouch {

    public static Item[] suppressRunes(Player player, Item[] runesRequired) {

        int runeReduction = 0;

        runeReduction = player.Prophetic * 20;

        for (int i = 0; i < runesRequired.length; i++) {
            if (runesRequired[i] != null && runesRequired[i].getId() == player.getRunePouchTypeOne() && runesRequired[i].getAmount() > 0 && player.getRunePouchQtyOne() >= runesRequired[i].getAmount()) {
                if ((player.Prophetic == 0) || (player.Prophetic >= 1 && RandomUtility.inclusiveRandom(100) >= runeReduction)) {
                    player.setRunePouchQtyOne(player.getRunePouchQtyOne() - runesRequired[i].getAmount());
                }
                runesRequired[i] = null;
            } else if (runesRequired[i] != null && runesRequired[i].getId() == player.getRunePouchTypeTwo() && runesRequired[i].getAmount() > 0 && player.getRunePouchQtyTwo() >= runesRequired[i].getAmount()) {
                if ((player.Prophetic == 0) || (player.Prophetic >= 1 && RandomUtility.inclusiveRandom(100) >= runeReduction)) {
                    player.setRunePouchQtyTwo(player.getRunePouchQtyTwo() - runesRequired[i].getAmount());
                }
                runesRequired[i] = null;
            } else if (runesRequired[i] != null && runesRequired[i].getId() == player.getRunePouchTypeThree() && runesRequired[i].getAmount() > 0 && player.getRunePouchQtyThree() >= runesRequired[i].getAmount()) {
                if ((player.Prophetic == 0) || (player.Prophetic >= 1 && RandomUtility.inclusiveRandom(100) >= runeReduction)) {
                    player.setRunePouchQtyThree(player.getRunePouchQtyThree() - runesRequired[i].getAmount());
                }
                runesRequired[i] = null;
            }else if (runesRequired[i] != null && runesRequired[i].getId() == player.getRunePouchTypeFour() && runesRequired[i].getAmount() > 0 && player.getRunePouchQtyFour() >= runesRequired[i].getAmount()) {
                if ((player.Prophetic == 0) || (player.Prophetic >= 1 && RandomUtility.inclusiveRandom(100) >= runeReduction)) {
                    player.setRunePouchQtyFour(player.getRunePouchQtyFour() - runesRequired[i].getAmount());
                }
                runesRequired[i] = null;
            }

        }


        return runesRequired;

    }

}