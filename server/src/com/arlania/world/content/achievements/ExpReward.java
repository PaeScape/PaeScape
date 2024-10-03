package com.arlania.world.content.achievements;

import com.arlania.model.Skill;
import com.arlania.world.entity.impl.player.Player;

public class ExpReward implements NonItemReward {

    private final Skill skill;
    private final int experience;

    public ExpReward(Skill skill, int experience) {
        this.skill = skill;
        this.experience = experience;
    }

    @Override
    public void giveReward(Player player) {
        player.getSkillManager().addExperience(skill, experience);
    }

    @Override
    public String rewardDescription() {
        return "-" + experience + " " + skill.getFormatName() + " XP.";
    }
}
