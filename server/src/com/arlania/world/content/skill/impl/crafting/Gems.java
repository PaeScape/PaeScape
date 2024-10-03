package com.arlania.world.content.skill.impl.crafting;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.GroundItem;
import com.arlania.model.Item;
import com.arlania.model.Skill;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.world.entity.impl.GroundItemManager;
import com.arlania.world.entity.impl.player.Player;

public class Gems {

    enum GEM_DATA {

        OPAL(1625, 1609, 1, 15, new Animation(886)),
        JADE(1627, 1611, 7, 25, new Animation(886)),
        RED_TOPAZ(1629, 1613, 14, 50, new Animation(887)),
        SAPPHIRE(1623, 1607, 20, 75, new Animation(888)),
        EMERALD(1621, 1605, 27, 100, new Animation(889)),
        RUBY(1619, 1603, 34, 150, new Animation(892)),
        DIAMOND(1617, 1601, 43, 200, new Animation(886)),
        DRAGONSTONE(1631, 1615, 55, 300, new Animation(885)),
        ONYX(6571, 6573, 67, 400, new Animation(885)),
        ZENYTE(219496, 219493, 80, 500, new Animation(885)),

        NOTED_OPAL(1626, 1610, 1, 15, new Animation(886)),
        NOTED_JADE(1628, 1612, 7, 25, new Animation(886)),
        NOTED_RED_TOPAZ(1630, 1614, 14, 50, new Animation(887)),
        NOTED_SAPPHIRE(1624, 1608, 20, 75, new Animation(888)),
        NOTED_EMERALD(1622, 1606, 27, 100, new Animation(889)),
        NOTED_RUBY(1620, 1604, 34, 150, new Animation(892)),
        NOTED_DIAMOND(1618, 1602, 43, 200, new Animation(886)),
        NOTED_DRAGONSTONE(1632, 1616, 55, 300, new Animation(885)),
        NOTED_ONYX(6572, 6574, 67, 400, new Animation(885));

        GEM_DATA(int uncutGem, int cutGem, int levelReq, int xpReward, Animation animation) {
            this.uncutGem = uncutGem;
            this.cutGem = cutGem;
            this.levelReq = levelReq;
            this.xpReward = xpReward;
            this.animation = animation;
        }


        private final int uncutGem;
        private final int cutGem;
        private final int levelReq;
        private final int xpReward;
        private final Animation animation;

        public int getUncutGem() {
            return uncutGem;
        }

        public int getCutGem() {
            return cutGem;
        }

        public int getLevelReq() {
            return levelReq;
        }

        public int getXpReward() {
            return xpReward;
        }

        public Animation getAnimation() {
            return animation;
        }

        public static GEM_DATA forUncutGem(int uncutGem) {
            for (GEM_DATA data : GEM_DATA.values()) {
                if (data.getUncutGem() == uncutGem)
                    return data;
            }
            return null;
        }
    }

    public static boolean canCut(Player player, int id) {
        GEM_DATA gemData = GEM_DATA.forUncutGem(id);
        if (gemData == null)
            return false;
        if (player.getSkillManager().getCurrentLevel(Skill.CRAFTING) < gemData.getLevelReq()) {
            player.getPacketSender().sendMessage("You need a Crafting level of atleast " + gemData.getLevelReq() + " to cut this.");
            return false;
        }
        if (!player.getInventory().contains(id)) {
            player.getPacketSender().sendMessage("You have run out of gems.");
            return false;
        }
        return true;
    }

    public static void cutGem(final Player player, final int amount, final int uncutGem) {
        final GEM_DATA gemData = GEM_DATA.forUncutGem(uncutGem);
        if (gemData == null)
            return;
        player.getSkillManager().stopSkilling();
        player.getPacketSender().sendInterfaceRemoval();
        if (!canCut(player, uncutGem))
            return;

        if (ItemDefinition.forId(gemData.getUncutGem()).isNoted() && !player.checkAchievementAbilities(player, "processor")) {
            player.getPacketSender().sendMessage("You must unlock the Processor achievement ability to cut noted gems.");
            return;
        }

        player.setCurrentTask(new Task(2, player, false) {
            int amountCut = 0;

            @Override
            public void execute() {
                if (!canCut(player, uncutGem)) {
                    stop();
                    return;
                }

                player.performAnimation(new Animation(896));

                int amountToProcess = player.acceleratedProcessing();

                if (player.getInventory().getAmount(uncutGem) < amountToProcess)
                    amountToProcess = player.getInventory().getAmount(uncutGem);


                player.performAnimation(new Animation(896));
                player.getInventory().delete(uncutGem, amountToProcess);

                if (ItemDefinition.forId(gemData.cutGem).isNoted() && (player.getInventory().contains(gemData.cutGem) || player.getInventory().getFreeSlots() > 0)) {
                    player.getInventory().add(gemData.cutGem, amountToProcess);
                } else if (player.getInventory().getFreeSlots() >= amountToProcess)
                    player.getInventory().add(gemData.cutGem, amountToProcess);
                else
                    GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(gemData.cutGem, amountToProcess), player.getPosition().copy(), player.getUsername(), false, 120, true, 120));

                player.getSkillManager().addExperience(Skill.CRAFTING, gemData.xpReward * amountToProcess);

                amountCut += amountToProcess;

                if (amountCut >= 27 * (player.acceleratedProcessing()))
                    stop();
            }

            @Override
            public void stop() {
                setEventRunning(false);
                player.setSelectedSkillingItem(-1);
                player.performAnimation(new Animation(65535));
            }
        });
        TaskManager.submit(player.getCurrentTask());
    }

}
