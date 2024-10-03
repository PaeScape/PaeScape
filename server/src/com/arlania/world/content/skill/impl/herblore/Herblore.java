package com.arlania.world.content.skill.impl.herblore;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.Item;
import com.arlania.model.Skill;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.util.Misc;
import com.arlania.world.content.achievements.AchievementData;
import com.arlania.world.entity.impl.player.Player;

public class Herblore {

    public static final int VIAL = 227;
    private static final Animation ANIMATION = new Animation(363);

    public static boolean cleanHerb(final Player player, final int herbId) {
        Herbs herb = Herbs.forId(herbId);
        if (herb == null) {
            return false;
        }
        if (player.getInventory().contains(herb.getGrimyHerb())) {
            if (player.getSkillManager().getCurrentLevel(Skill.HERBLORE) < herb.getLevelReq()) {
                player.getPacketSender().sendMessage("You need a Herblore level of at least " + herb.getLevelReq() + " to clean this leaf.");
                return false;
            }

            int amount = player.getInventory().getAmount(herb.getGrimyHerb());


            if (amount > player.acceleratedProcessing())
                amount = player.acceleratedProcessing();

            int addxp = herb.getExp() * amount;

            if (player.getEquipment().contains(20046))
                addxp *= 1.25;


            player.getInventory().delete(herb.getGrimyHerb(), amount);
            player.getInventory().add(herb.getCleanHerb(), amount);
            player.getSkillManager().addExperience(Skill.HERBLORE, addxp);

            return true;
        }
        return false;
    }

    public static boolean makeUnfinishedPotion(final Player player, final int herbId) {
        final UnfinishedPotions unf = UnfinishedPotions.forId(herbId);
        if (unf == null)
            return false;
        if (player.getSkillManager().getCurrentLevel(Skill.HERBLORE) < unf.getLevelReq()) {
            player.getPacketSender().sendMessage("You need a Herblore level of at least " + unf.getLevelReq() + " to make this potion.");
            return false;
        }

        if(player.checkAchievementAbilities(player, "processor")) {
            if (player.getInventory().contains(VIAL) && player.getInventory().contains(unf.getGrimyHerbNeeded())) {
                player.getSkillManager().stopSkilling();
                player.performAnimation(ANIMATION);

                int amount = Math.min(player.getInventory().getAmount(VIAL), player.getInventory().getAmount(unf.getGrimyHerbNeeded()));
                TaskManager.submit(new Task(1, player, false) {
                    public void execute() {
                        player.getInventory().delete(VIAL, amount).delete(unf.getGrimyHerbNeeded(), amount).add(unf.getUnfPotion(), amount);
                        player.getSkillManager().addExperience(Skill.HERBLORE, 1);
                        this.stop();
                    }
                });
                return true;
            }
        }

        if (player.getInventory().contains(VIAL) && player.getInventory().contains(unf.getHerbNeeded())) {
            player.getSkillManager().stopSkilling();
            player.performAnimation(ANIMATION);

            int amount = Math.min(player.getInventory().getAmount(VIAL), player.getInventory().getAmount(unf.getHerbNeeded()));
            TaskManager.submit(new Task(1, player, false) {
                public void execute() {
                    player.getInventory().delete(VIAL, amount).delete(unf.getHerbNeeded(), amount).add(unf.getUnfPotion(), amount);
                    player.getSkillManager().addExperience(Skill.HERBLORE, 1);
                    this.stop();
                }
            });
            return true;
        }
        return false;
    }

    public static boolean isPotion(final Player player, final int itemUsed, final int usedWith) {
        final FinishedPotions pot = FinishedPotions.forId(itemUsed, usedWith);
        if (pot == FinishedPotions.MISSING_INGRIDIENTS) {
            player.getPacketSender().sendMessage("You don't have the required items to make this potion.");
            return false;
        }
        if (pot == null) {
            handleSpecialPotion(player, itemUsed, usedWith);
            return false;
        }
        if (player.getSkillManager().getCurrentLevel(Skill.HERBLORE) < pot.getLevelReq()) {
            player.getPacketSender().sendMessage("You need a Herblore level of at least " + pot.getLevelReq() + " to make this potion.");
            return false;
        }

        return true;
    }


    public static void finishPotion(final Player player, final int itemUsed, final int usedWith) {
        final FinishedPotions pot = FinishedPotions.forId(itemUsed, usedWith);
        if (pot == FinishedPotions.MISSING_INGRIDIENTS) {
            player.getPacketSender().sendMessage("You don't have the required items to make this potion.");
            return;
        }
        if (pot == null) {
            handleSpecialPotion(player, itemUsed, usedWith);
            return;
        }
        if (player.getSkillManager().getCurrentLevel(Skill.HERBLORE) < pot.getLevelReq()) {
            player.getPacketSender().sendMessage("You need a Herblore level of at least " + pot.getLevelReq() + " to make this potion.");
            return;
        }
        if (ItemDefinition.forId(pot.getFinishedPotion()).isNoted() && !player.checkAchievementAbilities(player, "processor")) {
            player.getPacketSender().sendMessage("You need Processer (Achievement Ability) to make noted potions.");
            return;
        }

        if (player.getInventory().contains(pot.getUnfinishedPotion()) && player.getInventory().contains(pot.getItemNeeded())) {
            player.getSkillManager().stopSkilling();

            int ingredient1 = pot.getUnfinishedPotion();
            int ingredient2 = pot.getItemNeeded();
            int amountIngredient1 = player.getInventory().getAmount(ingredient1);
            int amountIngredient2 = player.getInventory().getAmount(ingredient2);
            int product = pot.getFinishedPotion();
            int xpReward = pot.getExpGained();
            int levelReq = pot.getLevelReq();
            Skill skill = Skill.HERBLORE;

            //Bonus XP
            if (player.getEquipment().contains(20046))
                xpReward *= 1.25;

            int maxProducts = player.acceleratedProcessing();

            if (amountIngredient1 < maxProducts)
                maxProducts = amountIngredient1;
            if (amountIngredient2 < maxProducts)
                maxProducts = amountIngredient2;
            //if (player.getInventory().getFreeSlots() < maxProducts)
            //maxProducts = player.getInventory().getFreeSlots();

            final int addXP = xpReward * maxProducts;
            final int productsMade = maxProducts;

            player.setCurrentTask(new Task(2, player, false) {
                int amount = 0;

                @Override
                public void execute() {
                    if (!player.getInventory().contains(ingredient1) || !player.getInventory().contains(ingredient2)) {
                        stop();
                        return;
                    }
                    if (player.getSkillManager().getCurrentLevel(skill) < levelReq) {
                        stop();
                        return;
                    }
                    if (amount == 28) {
                        stop();
                        return;
                    }
                    /*if (player.getInventory().getFreeSlots() == 0) {
                        stop();
                        return;
                    }*/

                    player.performAnimation(ANIMATION);

                    player.getInventory().delete(ingredient1, productsMade);
                    player.getInventory().delete(ingredient2, productsMade);
                    player.getInventory().add(product, productsMade);
                    player.getSkillManager().addExperience(skill, addXP);

                    amount++;

                    if (product == FinishedPotions.ATTACK_POTION.getFinishedPotion() || product == FinishedPotions.NOTED_ATTACK_POTION.getFinishedPotion()) {
                        player.getAchievementTracker().progress(AchievementData.MAKE_10_ATTACK_POTIONS, productsMade);
                    } else if (product == FinishedPotions.STRENGTH_POTION.getFinishedPotion() || product == FinishedPotions.NOTED_STRENGTH_POTION.getFinishedPotion()) {
                        player.getAchievementTracker().progress(AchievementData.MAKE_25_STRENGTH_POTIONS, productsMade);
                    } else if (product == FinishedPotions.PRAYER_POTION.getFinishedPotion() || product == FinishedPotions.NOTED_PRAYER_POTION.getFinishedPotion()) {
                        player.getAchievementTracker().progress(AchievementData.MAKE_50_PRAYER_POTIONS, productsMade);
                    } else if (product == FinishedPotions.SUPER_ATTACK.getFinishedPotion() || product == FinishedPotions.NOTED_SUPER_ATTACK.getFinishedPotion()) {
                        player.getAchievementTracker().progress(AchievementData.MAKE_100_SUPER_ATTACKS, productsMade);
                    } else if (product == FinishedPotions.RANGING_POTION.getFinishedPotion() || product == FinishedPotions.NOTED_RANGING_POTION.getFinishedPotion()) {
                        player.getAchievementTracker().progress(AchievementData.MAKE_250_RANGING_POTIONS, productsMade);
                    } else if (product == FinishedPotions.SARADOMIN_BREW.getFinishedPotion() || product == FinishedPotions.NOTED_SARADOMIN_BREW.getFinishedPotion()) {
                        player.getAchievementTracker().progress(AchievementData.MAKE_1000_SARADOMIN_BREW, productsMade);
                    }
                }
            });
            TaskManager.submit(player.getCurrentTask());
        }
    }

    enum SpecialPotion {
        OVERLOAD(new Item[]{new Item(15309), new Item(15313), new Item(15317), new Item(15321), new Item(15325)}, new Item(15333), 96, 1000);

        SpecialPotion(Item[] ingridients, Item product, int lvlReq, int exp) {
            this.ingridients = ingridients;
            this.product = product;
            this.lvlReq = lvlReq;
            this.exp = exp;
        }

        private final Item[] ingridients;

        public Item[] getIngridients() {
            return ingridients;
        }

        private final Item product;

        public Item getProduct() {
            return product;
        }

        private final int lvlReq;
        private final int exp;

        public int getLevelReq() {
            return lvlReq;
        }

        public int getExperience() {
            return exp;
        }

        public static SpecialPotion forItems(int item1, int item2) {
            for (SpecialPotion potData : SpecialPotion.values()) {
                int found = 0;
                for (Item it : potData.getIngridients()) {
                    if (it.getId() == item1 || it.getId() == item2)
                        found++;
                }
                if (found >= 2)
                    return potData;
            }
            return null;
        }
    }

    public static void handleSpecialPotion(Player p, int item1, int item2) {
        if (item1 == item2)
            return;
        if (!p.getInventory().contains(item1) || !p.getInventory().contains(item2))
            return;
        SpecialPotion specialPotData = SpecialPotion.forItems(item1, item2);
        if (specialPotData == null)
            return;
        if (p.getSkillManager().getCurrentLevel(Skill.HERBLORE) < specialPotData.getLevelReq()) {
            p.getPacketSender().sendMessage("You need a Herblore level of at least " + specialPotData.getLevelReq() + " to make this potion.");
            return;
        }
        if (!p.getClickDelay().elapsed(500))
            return;
        for (Item ingridients : specialPotData.getIngridients()) {
            if (!p.getInventory().contains(ingridients.getId()) || p.getInventory().getAmount(ingridients.getId()) < ingridients.getAmount()) {
                p.getPacketSender().sendMessage("You do not have all ingridients for this potion.");
                p.getPacketSender().sendMessage("Remember: You can purchase an Ingridient's book from the Druid Spirit.");
                return;
            }
        }

        int addxp = specialPotData.getExperience();

        if (p.getEquipment().contains(20046))
            addxp *= 1.25;


        for (Item ingridients : specialPotData.getIngridients()) {
            p.getInventory().delete(ingridients);
        }


        p.getInventory().add(specialPotData.getProduct());
        p.performAnimation(new Animation(363));
        p.getSkillManager().addExperience(Skill.HERBLORE, addxp);
        String name = specialPotData.getProduct().getDefinition().getName();
        p.getPacketSender().sendMessage("You make " + Misc.anOrA(name) + " " + name + ".");
        p.getClickDelay().reset();
    }
}