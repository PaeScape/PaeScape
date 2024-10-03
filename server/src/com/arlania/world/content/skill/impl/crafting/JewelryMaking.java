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

public class JewelryMaking {

    enum JewelryData {

        GOLD_RING(1592, 2357, 1635, 15, 135, new Animation(886)),
        GOLD_NECKLACE(1597, 2357, 1654, 18, 150, new Animation(886)),
        GOLD_AMULET(1595, 2357, 1692, 21, 165, new Animation(886)),
        GOLD_BRACELET(11065, 2357, 11069, 24, 180, new Animation(886)),

        SAPPHIRE_RING(1635, 1607, 2550, 15, 135, new Animation(886)),
        SAPPHIRE_NECKLACE(1654, 1607, 3853, 18, 150, new Animation(886)),
        SAPPHIRE_AMULET(1692, 1607, 1727, 21, 165, new Animation(886)),
        SAPPHIRE_BRACELET(11069, 1607, 11074, 24, 180, new Animation(886)),

        EMERALD_RING(1635, 1605, 2552, 27, 195, new Animation(886)),
        EMERALD_NECKLACE(1654, 1605, 5521, 30, 210, new Animation(886)),
        EMERALD_AMULET(1692, 1605, 1729, 33, 225, new Animation(886)),
        EMERALD_BRACELET(11069, 1605, 11079, 36, 240, new Animation(886)),

        RUBY_RING(1635, 1603, 2568, 39, 255, new Animation(886)),
        RUBY_NECKLACE(1654, 1603, 11194, 42, 270, new Animation(886)),
        RUBY_AMULET(1692, 1603, 1725, 45, 285, new Animation(886)),
        RUBY_BRACELET(11069, 1603, 11088, 48, 300, new Animation(886)),

        DIAMOND_RING(1635, 1601, 2570, 51, 330, new Animation(886)),
        DIAMOND_NECKLACE(1654, 1601, 11090, 54, 360, new Animation(886)),
        DIAMOND_AMULET(1692, 1601, 1731, 57, 390, new Animation(886)),
        DIAMOND_BRACELET(11069, 1601, 11095, 60, 420, new Animation(886)),

        DRAGONSTONE_RING(1635, 1615, 2572, 63, 450, new Animation(886)),
        DRAGONSTONE_NECKLACE(1654, 1615, 11105, 66, 480, new Animation(886)),
        DRAGONSTONE_AMULET(1692, 1615, 1704, 69, 510, new Animation(886)),
        DRAGONSTONE_BRACELET(11069, 1615, 11126, 72, 540, new Animation(886)),

        ONYX_RING(1635, 6573, 15017, 75, 570, new Animation(886)),
        ONYX_NECKLACE(1654, 6573, 11128, 78, 600, new Animation(886)),
        ONYX_AMULET(1692, 6573, 6585, 81, 675, new Animation(886)),
        ONYX_BRACELET(11069, 6573, 11133, 84, 750, new Animation(886)),

        ZENYTE_RING(1635, 219493, 18898, 87, 825, new Animation(886)),
        ZENYTE_NECKLACE(1654, 219493, 18895, 91, 900, new Animation(886)),
        ZENYTE_AMULET(1692, 219493, 18896, 95, 1050, new Animation(886)),
        ZENYTE_BRACELET(11069, 219493, 18847, 93, 950, new Animation(886)),

        NOTED_GOLD_RING(1592, 2358, 1636, 15, 135, new Animation(886)),
        NOTED_GOLD_NECKLACE(1597, 2358, 1654, 18, 150, new Animation(886)),
        NOTED_GOLD_AMULET(1595, 2358, 1693, 21, 165, new Animation(886)),
        NOTED_GOLD_BRACELET(11065, 2358, 11070, 24, 180, new Animation(886)),

        NOTED_SAPPHIRE_RING(1636, 1608, 2551, 15, 135, new Animation(886)),
        NOTED_SAPPHIRE_NECKLACE(1655, 1608, 3854, 18, 150, new Animation(886)),
        NOTED_SAPPHIRE_AMULET(1693, 1608, 1728, 21, 165, new Animation(886)),
        NOTED_SAPPHIRE_BRACELET(11070, 1608, 11075, 24, 180, new Animation(886)),

        NOTED_EMERALD_RING(1636, 1606, 2553, 27, 195, new Animation(886)),
        NOTED_EMERALD_NECKLACE(1655, 1606, 5522, 30, 210, new Animation(886)),
        NOTED_EMERALD_AMULET(1693, 1606, 1730, 33, 225, new Animation(886)),
        NOTED_EMERALD_BRACELET(11070, 1606, 11080, 36, 240, new Animation(886)),

        NOTED_RUBY_RING(1636, 1604, 2569, 39, 255, new Animation(886)),
        NOTED_RUBY_NECKLACE(1655, 1604, 11195, 42, 270, new Animation(886)),
        NOTED_RUBY_AMULET(1693, 1604, 1726, 45, 285, new Animation(886)),
        NOTED_RUBY_BRACELET(11070, 1604, 11089, 48, 300, new Animation(886)),

        NOTED_DIAMOND_RING(1636, 1602, 2571, 51, 330, new Animation(886)),
        NOTED_DIAMOND_NECKLACE(1655, 1602, 11091, 54, 360, new Animation(886)),
        NOTED_DIAMOND_AMULET(1693, 1602, 1732, 57, 390, new Animation(886)),
        NOTED_DIAMOND_BRACELET(11070, 1602, 11096, 60, 420, new Animation(886)),

        NOTED_DRAGONSTONE_RING(1636, 1616, 2573, 63, 450, new Animation(886)),
        NOTED_DRAGONSTONE_NECKLACE(1655, 1616, 11106, 66, 480, new Animation(886)),
        NOTED_DRAGONSTONE_AMULET(1693, 1616, 1705, 69, 510, new Animation(886)),
        NOTED_DRAGONSTONE_BRACELET(11070, 1616, 11127, 72, 540, new Animation(886)),

        NOTED_ONYX_RING(1636, 6574, 15017, 75, 570, new Animation(886)),
        NOTED_ONYX_NECKLACE(1655, 6574, 11129, 78, 600, new Animation(886)),
        NOTED_ONYX_AMULET(1693, 6574, 6586, 81, 675, new Animation(886)),
        NOTED_ONYX_BRACELET(11070, 6574, 11134, 84, 750, new Animation(886)),

        NOTED_ZENYTE_RING(1636, 219494, 18898, 87, 825, new Animation(886)),
        NOTED_ZENYTE_NECKLACE(1655, 219494, 18895, 91, 900, new Animation(886)),
        NOTED_ZENYTE_AMULET(1693, 219494, 18896, 95, 1050, new Animation(886)),
        NOTED_ZENYTE_BRACELET(11070, 219494, 18847, 93, 950, new Animation(886));

        JewelryData(int goldJewelry, int cutGem, int gemJewelry, int levelReq, int xpReward, Animation animation) {

            this.goldJewelry = goldJewelry;
            this.cutGem = cutGem;
            this.gemJewelry = gemJewelry;
            this.levelReq = levelReq;
            this.xpReward = xpReward;
            this.animation = animation;
        }

        private final int cutGem;
        private final int goldJewelry;
        private final int gemJewelry;
        private final int levelReq;
        private final int xpReward;
        private final Animation animation;

        public int getCutGem() {
            return cutGem;
        }

        public int getGoldJewelry() {
            return goldJewelry;
        }

        public int getGemJewelry() {
            return gemJewelry;
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

        public static JewelryData forGemJewelry(int gemJewelry) {
            for (JewelryData data : JewelryData.values()) {
                if (data.getGemJewelry() == gemJewelry)
                    return data;
            }
            return null;
        }
    }

    public static JewelryData getDataForItems(int item1, int item2) {
        for (JewelryData data : JewelryData.values()) {
            if (data.cutGem == item1 && data.goldJewelry == item2) {
                return data;
            } else if (data.cutGem == item2 && data.goldJewelry == item1) {
                return data;
            }
        }

        return null;
    }

    public static void craftJewelry(final Player player, final int item1, final int item2) {

        if (item1 == item2)
            return;

        JewelryData data = getDataForItems(item1, item2);

        if (data == null || !player.getInventory().contains(item1) || !player.getInventory().contains(item2))
            return;
        if (player.getInterfaceId() > 0) {
            player.getPacketSender().sendMessage("Please close the interface you have open before doing this.");
            return;
        }

        if (player.getInventory().getFreeSlots() < 1) {
            return;
        }

        player.getSkillManager().stopSkilling();

        final int amount = 28;

        player.setCurrentTask(new Task(2, player, false) {
            int amountCut = 0;

            @Override
            public void execute() {

                if (!player.getInventory().contains(data.cutGem) || !player.getInventory().contains(data.goldJewelry)) {
                    stop();
                    return;
                }

                int amountToProcess = Math.min(player.acceleratedProcessing(), Math.min(player.getInventory().getAmount(data.cutGem), player.getInventory().getAmount(data.goldJewelry)));

                if (ItemDefinition.forId(data.goldJewelry).getName().contains("mould")) {
                    amountToProcess = Math.min(player.acceleratedProcessing(), player.getInventory().getAmount(data.cutGem));
                }

                if (player.getInventory().getAmount(data.cutGem) >= amountToProcess) {
                    player.performAnimation(data.getAnimation());

                    if (!ItemDefinition.forId(data.goldJewelry).getName().contains("mould")) {
                        player.getInventory().delete(data.goldJewelry, amountToProcess);
                    }

                    if (!ItemDefinition.forId(data.cutGem).getName().contains("mould")) {
                        player.getInventory().delete(data.cutGem, amountToProcess);
                    }

                    if (ItemDefinition.forId(data.getGemJewelry()).isNoted() && (player.getInventory().contains(data.getGemJewelry()) || player.getInventory().getFreeSlots() > 0)) {
                        player.getInventory().add(data.getGemJewelry(), amountToProcess);
                    } else if(player.getInventory().getFreeSlots() >= amountToProcess) {
                        player.getInventory().add(data.getGemJewelry(), amountToProcess);
                    } else {
                        GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(data.getGemJewelry(), amountToProcess), player.getPosition(), player.getUsername(), false, 150, true, 200));
                    }

                    player.getSkillManager().addExperience(Skill.CRAFTING, data.getXpReward() * amountToProcess);
                    amountCut++;
                    if (amountCut >= amount)
                        stop();

                    if (amountCut >= 28)
                        stop();
                }
            }
        });
        TaskManager.submit(player.getCurrentTask());

    }

}
