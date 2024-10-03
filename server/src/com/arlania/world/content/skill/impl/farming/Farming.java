package com.arlania.world.content.skill.impl.farming;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.Item;
import com.arlania.model.Skill;
import com.arlania.world.entity.impl.player.Player;

public class Farming {


    public static boolean isSeed(int seed) {
        return SeedsData.forId(seed) != null;
    }

    public static void plantSeed(final Player player, final int itemId) {
        if (!player.getClickDelay().elapsed(2000))
            return;
        final SeedsData currentSeed = SeedsData.forId(itemId);
        if (currentSeed == null)
            return;
        player.getSkillManager().stopSkilling();
        player.getPacketSender().sendInterfaceRemoval();
        player.performAnimation(new Animation(827));
        player.getPacketSender().sendMessage("You plant the seed in the ground..");
        final Item seed = new Item(itemId);
        player.getInventory().delete(seed, 1);
        TaskManager.submit(new Task(3, player, false) {
            @Override
            public void execute() {
                player.getPacketSender().sendMessage("..and plant the " + seed.getDefinition().getName() + ".");

                int addxp = currentSeed.getPlantingXP();

                if ((player.getSummoning().getFamiliar() != null) && (player.getSummoning().getFamiliar().getSummonNpc().getId() == 21352)) {
                    addxp *= 1.25;
                }

                player.getSkillManager().addExperience(Skill.FARMING, addxp);
                stop();
            }
        });
        player.getClickDelay().reset();
    }
}
