package com.arlania.world.content;

import com.arlania.model.container.impl.Equipment;
import com.arlania.world.content.ItemDegrading.DegradingItem;
import com.arlania.world.entity.impl.player.Player;

public class BrawlingGloves {

    private static final int[][] GLOVES_SKILLS =
            {{13855, 13}, {13848, 5}, {13857, 7},
                    {13856, 10}, {13854, 17}, {13853, 21},
                    {13852, 14}, {13851, 11}, {13850, 8}, {13849, 16}};

    public static int getExperienceIncrease(Player p, int skill, int experience) {
        int playerGloves = p.getEquipment().getItems()[Equipment.HANDS_SLOT].getId();
        if (playerGloves <= 0)
            return experience;
        for (int i = 0; i < GLOVES_SKILLS.length; i++) {
            if ((playerGloves == GLOVES_SKILLS[i][0]) && (skill == GLOVES_SKILLS[i][1]) && ItemDegrading.handleItemDegrading(p, DegradingItem.forNonDeg(playerGloves))) {
                return (int) (experience * 1.25);
            }
        }
        return experience;
    }
}
