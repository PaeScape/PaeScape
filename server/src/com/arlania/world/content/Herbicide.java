package com.arlania.world.content;

import com.arlania.model.Skill;
import com.arlania.world.entity.impl.player.Player;

import java.util.Arrays;
import java.util.List;

public class Herbicide {

    public static void dissolveHerb(final Player p, final int herb, final int qty) {
        List<Integer> validHerbs = Arrays.asList(199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 215, 216, 217, 218, 219, 220, 2485, 2486, 3049, 3050, 3051, 3052);
        if (validHerbs.contains(herb)) {
            final int xpGained = (validHerbs.indexOf(herb) + 3) * 2 * qty;
            p.getSkillManager().addExperience(Skill.HERBLORE, xpGained);
            if (p.personalFilterHerbicide) {
                p.getPacketSender().sendMessage("@or2@Your Herbicide dissolves your herbs into experience.");
            }
        }
    }

}
