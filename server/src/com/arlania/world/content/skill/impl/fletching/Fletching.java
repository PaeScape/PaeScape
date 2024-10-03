package com.arlania.world.content.skill.impl.fletching;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.Item;
import com.arlania.model.Skill;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.model.input.impl.EnterAmountOfBowsToString;
import com.arlania.model.input.impl.EnterAmountToFletch;
import com.arlania.world.content.Sounds;
import com.arlania.world.content.Sounds.Sound;
import com.arlania.world.entity.impl.player.Player;

/**
 * Handles the Fletching skill
 *
 * @author Gabriel Hannason
 */
public class Fletching {

    /**
     * Handles the Fletching interface
     *
     * @param player The player Fletching
     * @param log    The log to fletch
     */
    public static void openSelection(final Player player, int log) {
        player.getSkillManager().stopSkilling();
        player.setSelectedSkillingItem(log);
        BowData shortBow = BowData.forLog(log, false);
        BowData longBow = BowData.forLog(log, true);

        if (ItemDefinition.forId(log).isNoted() && !player.checkAchievementAbilities(player, "processor")) {
            player.getPacketSender().sendMessage("You must unlock the Processor achievement ability to cook noted fish.");
            return;
        }

        if (shortBow == null || longBow == null)
            return;
        if (log == 1511) {
            player.getPacketSender().sendChatboxInterface(8880);
            player.getPacketSender().sendInterfaceModel(8884, longBow.getBowID(), 250);
            player.getPacketSender().sendInterfaceModel(8883, shortBow.getBowID(), 250);
            player.getPacketSender().sendString(8889, ItemDefinition.forId(shortBow.getBowID()).getName());
            player.getPacketSender().sendString(8893, ItemDefinition.forId(longBow.getBowID()).getName());
            player.getPacketSender().sendString(8897, "Shafts");
            player.getPacketSender().sendInterfaceModel(8885, 52, 250);
        } else {
            player.getPacketSender().sendChatboxInterface(8866);
            player.getPacketSender().sendInterfaceModel(8870, longBow.getBowID(), 250);
            player.getPacketSender().sendInterfaceModel(8869, shortBow.getBowID(), 250);
            player.getPacketSender().sendString(8874, ItemDefinition.forId(shortBow.getBowID()).getName());
            player.getPacketSender().sendString(8878, ItemDefinition.forId(longBow.getBowID()).getName());
        }
        player.setInputHandling(new EnterAmountToFletch());
    }

    /**
     * Checks if a button that was clicked is from the Fletching interface
     *
     * @param player  The Player clicking a button
     * @param clickId The button the player clicked
     * @return
     */
    public static boolean fletchingButton(final Player player, int button) {

        BowData shortBow = BowData.forLog(player.getSelectedSkillingItem(), false);
        BowData longBow = BowData.forLog(player.getSelectedSkillingItem(), true);

        switch (button) {
            case 8889:
                switch (player.getSelectedSkillingItem()) {
                    case 1511: //Normal log
                        fletchBow(player, 48, 1);
                        return true;
                }
                return false;
            case 8888:
                switch (player.getSelectedSkillingItem()) {
                    case 1511: //Normal log
                        fletchBow(player, 48, 5);
                        return true;
                }
                return false;
            case 8887:
                switch (player.getSelectedSkillingItem()) {
                    case 1511: //Normal log
                        fletchBow(player, 48, 10);
                        return true;
                }
                return false;
            case 8893:
                switch (player.getSelectedSkillingItem()) {
                    case 1511: //Normal log
                        fletchBow(player, 50, 1);
                        return true;
                }
                return false;
            case 8892:
                switch (player.getSelectedSkillingItem()) {
                    case 1511: //Normal log
                        fletchBow(player, 50, 5);
                        return true;
                }
                return false;
            case 8891:
                switch (player.getSelectedSkillingItem()) {
                    case 1511: //Normal log
                        fletchBow(player, 50, 10);
                        return true;
                }
                return false;

            //Longbows
            case 8878:
                fletchBow(player, longBow.getBowID(), 28);
                return true;
            case 8877:
                fletchBow(player, longBow.getBowID(), 5);
                return true;
            case 8876:
                fletchBow(player, longBow.getBowID(), 10);
                return true;

            //Shortbows
            case 8874:
                fletchBow(player, shortBow.getBowID(), 28);
                return true;
            case 8873:
                fletchBow(player, shortBow.getBowID(), 5);
                return true;
            case 8872:
                fletchBow(player, shortBow.getBowID(), 10);
                return true;
            case 8897: //Arrow shafts
            case 8896: //Arrow shafts
            case 8895: //Arrow shafts
                if (player.getSelectedSkillingItem() == 1511) {
                    int amt = button == 8897 ? 1 : button == 8896 ? 5 : 10;
                    fletchBow(player, 52, amt);
                    return true;
                }
                return false;
        }
        return false;
    }

    public static void fletchBow(final Player player, final int product, final int amountToMake) {
        player.getPacketSender().sendInterfaceRemoval();
        final int log = player.getSelectedSkillingItem();
        player.getSkillManager().stopSkilling();

        player.setCurrentTask(new Task(2, player, true) {
            int amount = 0;

            @Override
            public void execute() {
                BowData bow = BowData.forBow(product);
                boolean shafts = product == 52;
                if (bow == null && !shafts || !player.getInventory().contains(log)) {
                    player.performAnimation(new Animation(65535));
                    stop();
                    return;
                }
                if (bow != null && player.getSkillManager().getCurrentLevel(Skill.FLETCHING) < bow.getLevelReq()) {
                    player.getPacketSender().sendMessage("You need a Fletching level of at least " + bow.getLevelReq() + " to make this.");
                    player.performAnimation(new Animation(65535));
                    stop();
                    return;
                }
                if (!player.getInventory().contains(946)) {
                    player.getPacketSender().sendMessage("You need a Knife to fletch this log.");
                    player.performAnimation(new Animation(65535));
                    stop();
                    return;
                }
                player.performAnimation(new Animation(1248));


                int amountToFletch = player.acceleratedProcessing();

                if (player.getInventory().getAmount(log) < amountToFletch)
                    amountToFletch = player.getInventory().getAmount(log);

                player.getInventory().delete(log, amountToFletch);

                if (log == 1511)
                    player.getInventory().add(product, (shafts ? 15 : 1) * amountToFletch);
                else
                    player.getInventory().add(product, amountToFletch);

                player.getSkillManager().addExperience(Skill.FLETCHING, (shafts ? 5 : bow.getXp()) * amountToFletch);
                Sounds.sendSound(player, Sound.FLETCH_ITEM);
                amount++;
                if ((amount >= amountToMake) || (amount >= 28) || player.getInventory().isFull())
                    stop();
            }
        });
        TaskManager.submit(player.getCurrentTask());

    }

    /**
     * Bow stringing
     */
    private static final int BOW_STRING = 1777;
    private static final int GOLDEN_BOW_STRING = 3702;

    public static void openBowStringSelection(Player player, int log) {
        for (final StringingData g : StringingData.values()) {
            if (log == g.unStrung()) {
                player.getSkillManager().stopSkilling();
                player.setSelectedSkillingItem(log);
                player.setInputHandling(new EnterAmountOfBowsToString());
                player.getPacketSender().sendString(2799, ItemDefinition.forId(g.Strung()).getName()).sendInterfaceModel(1746, g.Strung(), 150).sendChatboxInterface(4429);
                player.getPacketSender().sendString(2800, "How many would you like to make?");
            }
        }
    }

    public static void stringBow(final Player player, final int amount) {
        final int log = player.getSelectedSkillingItem();
        player.getSkillManager().stopSkilling();
        player.getPacketSender().sendInterfaceRemoval();
        for (final StringingData g : StringingData.values()) {
            if (log == g.unStrung()) {
                if (player.getSkillManager().getCurrentLevel(Skill.FLETCHING) < g.getLevel()) {
                    player.getPacketSender().sendMessage("You need a Fletching level of at least " + g.getLevel() + " to make this.");
                    return;
                }
                if (!player.getInventory().contains(log) || (!player.getInventory().contains(BOW_STRING) && !player.getInventory().contains(GOLDEN_BOW_STRING)))
                    return;
                player.performAnimation(new Animation(g.getAnimation()));


                player.setCurrentTask(new Task(2, player, false) {
                    int amountMade = 0;

                    @Override
                    public void execute() {
                        int amountToFletch = player.acceleratedProcessing();

                        if (!player.getInventory().contains(log) || (!player.getInventory().contains(BOW_STRING) && !player.getInventory().contains(GOLDEN_BOW_STRING)))
                            return;

                        if (player.getInventory().getAmount(log) < amountToFletch)
                            amountToFletch = player.getInventory().getAmount(log);

                        if (!player.getInventory().contains(GOLDEN_BOW_STRING) && player.getInventory().getAmount(BOW_STRING) < amountToFletch)
                            amountToFletch = player.getInventory().getAmount(BOW_STRING);


                        player.getInventory().delete(log, amountToFletch);

                        if (!player.getInventory().contains(GOLDEN_BOW_STRING))
                            player.getInventory().delete(BOW_STRING, amountToFletch);

                        player.getInventory().add(g.Strung(), amountToFletch);

                        player.getSkillManager().addExperience(Skill.FLETCHING, (int) (g.getXP() * amountToFletch));
                        player.getPacketSender().sendMessage("You attach the Bow string on to the bow.");
                        amountMade++;
                        if (amountMade >= amount)
                            stop();

                        if (amountMade >= 28)
                            stop();
                    }
                });
                TaskManager.submit(player.getCurrentTask());

            }
        }
    }

    /**
     * Arrows making
     */
    public static int getPrimary(int item1, int item2) {
        return item1 == 52 || item1 == 53 ? item2 : item1;
    }

    public static void makeArrows(final Player player, int item1, int item2) {
        player.getSkillManager().stopSkilling();
        ArrowData arr = ArrowData.forArrow(getPrimary(item1, item2));
        if (arr != null) {
            if (player.getSkillManager().getCurrentLevel(Skill.FLETCHING) >= arr.getLevelReq()) {
                if (player.getInventory().getAmount(arr.getItem1()) >= 15 * player.acceleratedProcessing() && player.getInventory().getAmount(arr.getItem2()) >= 15 * player.acceleratedProcessing()) {
                    player.getInventory().delete(new Item(arr.getItem1()).setAmount(15 * player.acceleratedProcessing()), player.getInventory().getSlot(arr.getItem1()), true);
                    player.getInventory().delete(new Item(arr.getItem2()).setAmount(15 * player.acceleratedProcessing()), player.getInventory().getSlot(arr.getItem2()), true);
                    player.getInventory().add(arr.getOutcome(), 15 * player.acceleratedProcessing());
                    player.getSkillManager().addExperience(Skill.FLETCHING, arr.getXp() * player.acceleratedProcessing());

                } else if (player.getInventory().getAmount(arr.getItem1()) >= 15 && player.getInventory().getAmount(arr.getItem2()) >= 15) {
                    player.getInventory().delete(new Item(arr.getItem1()).setAmount(15), player.getInventory().getSlot(arr.getItem1()), true);
                    player.getInventory().delete(new Item(arr.getItem2()).setAmount(15), player.getInventory().getSlot(arr.getItem2()), true);
                    player.getInventory().add(arr.getOutcome(), 15);
                    player.getSkillManager().addExperience(Skill.FLETCHING, arr.getXp());

                } else {
                    player.getPacketSender().sendMessage("You must have at least 15 of each supply to make arrows.");
                }
            } else {
                player.getPacketSender().sendMessage("You need a Fletching level of at least " + arr.getLevelReq() + " to fletch this.");
            }
        }
    }

    /**
     * Bolts making
     */
    public static int getPrimaryBolts(int item1, int item2) {
        return item1 == 52 || item1 == 53 ? item2 : item1;
    }

    public static void makeBolts(final Player player, int item1, int item2) {
        player.getSkillManager().stopSkilling();
        BoltData arr = BoltData.forBolt(getPrimaryBolts(item1, item2));
        if (arr != null) {
            if (player.getSkillManager().getCurrentLevel(Skill.FLETCHING) >= arr.getLevelReq()) {

                int qty = 10;

                if (player.getInventory().contains(2950))
                    qty = 100;

                int addXP = arr.getXp() * qty / 10;

                if (player.getInventory().getAmount(arr.getItem1()) >= qty * player.acceleratedProcessing() && player.getInventory().getAmount(arr.getItem2()) >= qty * player.acceleratedProcessing()) {

                    if (arr.getItem1() != 2950)
                        player.getInventory().delete(new Item(arr.getItem1()).setAmount(qty * player.acceleratedProcessing()), player.getInventory().getSlot(arr.getItem1()), true);
                    if (arr.getItem2() != 2950)
                        player.getInventory().delete(new Item(arr.getItem2()).setAmount(qty * player.acceleratedProcessing()), player.getInventory().getSlot(arr.getItem2()), true);

                    player.getInventory().add(arr.getOutcome(), qty * player.acceleratedProcessing());
                    player.getSkillManager().addExperience(Skill.FLETCHING, addXP * player.acceleratedProcessing());

                } else if (player.getInventory().getAmount(arr.getItem1()) >= qty && player.getInventory().getAmount(arr.getItem2()) >= qty) {

                    if (arr.getItem1() != 2950)
                        player.getInventory().delete(new Item(arr.getItem1()).setAmount(qty), player.getInventory().getSlot(arr.getItem1()), true);
                    if (arr.getItem2() != 2950)
                        player.getInventory().delete(new Item(arr.getItem2()).setAmount(qty), player.getInventory().getSlot(arr.getItem2()), true);

                    player.getInventory().add(arr.getOutcome(), qty);
                    player.getSkillManager().addExperience(Skill.FLETCHING, addXP);

                } else {
                    player.getPacketSender().sendMessage("You must have at least 10 of each supply to make bolts.");
                }
            } else {
                player.getPacketSender().sendMessage("You need a Fletching level of at least " + arr.getLevelReq() + " to fletch this.");
            }
        }
    }

    public static void makeDarts(final Player player, int item1, int item2) {
        player.getSkillManager().stopSkilling();
        DartData dar = DartData.forDart(getPrimary(item1, item2));
        if (dar != null) {
            if (player.getSkillManager().getCurrentLevel(Skill.FLETCHING) >= dar.getLevelReq()) {

                int amountToProcess = player.acceleratedProcessing() * 10;

                if (player.getInventory().getAmount(dar.getItem1()) < amountToProcess)
                    amountToProcess = player.getInventory().getAmount(dar.getItem1());

                if (player.getInventory().getAmount(dar.getItem2()) < amountToProcess)
                    amountToProcess = player.getInventory().getAmount(dar.getItem2());

                if (!player.getInventory().contains(2950)) {
                    if (player.getInventory().getAmount(dar.getItem1()) >= amountToProcess && player.getInventory().getAmount(dar.getItem2()) >= amountToProcess) {
                        player.getInventory().delete(new Item(dar.getItem1()).setAmount(amountToProcess), player.getInventory().getSlot(dar.getItem1()), true);
                        player.getInventory().delete(new Item(dar.getItem2()).setAmount(amountToProcess), player.getInventory().getSlot(dar.getItem2()), true);
                        player.getInventory().add(dar.getOutcome(), amountToProcess);
                        player.getSkillManager().addExperience(Skill.FLETCHING, dar.getXp() * amountToProcess / 10);
                    } else {
                        player.getPacketSender().sendMessage("You must have at least 10 of each supply to make arrows.");
                    }
                } else if (player.getInventory().contains(2950)) {

                    amountToProcess = player.acceleratedProcessing() * 100;

                    if (player.getInventory().getAmount(dar.getItem2()) < amountToProcess)
                        amountToProcess = player.getInventory().getAmount(dar.getItem2());

                    if (player.getInventory().getAmount(dar.getItem2()) >= amountToProcess) {
                        player.getInventory().delete(new Item(dar.getItem2()).setAmount(amountToProcess), player.getInventory().getSlot(dar.getItem2()), true);
                        player.getInventory().add(dar.getOutcome(), amountToProcess);
                        player.getSkillManager().addExperience(Skill.FLETCHING, dar.getXp() * amountToProcess / 10);
                    } else {
                        int amountToProcessy = player.getInventory().getAmount(dar.getItem2());
                        int xp = dar.getXp() * amountToProcess / 10;
                        player.getInventory().delete(new Item(dar.getItem2()).setAmount(amountToProcess), player.getInventory().getSlot(dar.getItem2()), true);
                        player.getInventory().add(dar.getOutcome(), amountToProcess);
                        player.getSkillManager().addExperience(Skill.FLETCHING, dar.getXp() * amountToProcess / 10);
                    }
                }
            } else {
                player.getPacketSender().sendMessage("You need a Fletching level of at least " + dar.getLevelReq() + " to fletch this.");
            }
        }
    }

    public static void makeAmethystAmmo(final Player player, int firstItem, int secondItem) {

        //bolts
        //some day

        //darts
        if (firstItem == 1755 || secondItem == 1755) {
            if (player.getSkillManager().getCurrentLevel(Skill.FLETCHING) >= 95) {
                int qty = player.acceleratedProcessing();

                if (player.getInventory().getAmount(221347) < qty)
                    qty = player.getInventory().getAmount(221347);

                player.getInventory().delete(221347, qty);
                player.getInventory().add(221316, 10 * qty);
                player.getSkillManager().addExperience(Skill.FLETCHING, 350 * qty);
            } else
                player.getPacketSender().sendMessage("You need 95 Fletching to do this.");
        }

        if (firstItem == 946 || secondItem == 946) {
            if (player.getSkillManager().getCurrentLevel(Skill.FLETCHING) >= 95) {

                int qtyAmethyst = player.acceleratedProcessing();

                if (player.getInventory().getAmount(221347) < qtyAmethyst)
                    qtyAmethyst = player.getInventory().getAmount(221347);

                player.getInventory().delete(221347, qtyAmethyst);
                player.getInventory().add(221326, 15 * qtyAmethyst);
                player.getSkillManager().addExperience(Skill.FLETCHING, 350 * qtyAmethyst);
            } else
                player.getPacketSender().sendMessage("You need 95 Fletching to do this.");
            return;
        }

    }

    public static void makeNotedAmethystAmmo(final Player player, int firstItem, int secondItem) {

        //bolts
        //some day

        //darts
        if (firstItem == 1755 || secondItem == 1755) {
            if (player.getSkillManager().getCurrentLevel(Skill.FLETCHING) >= 95) {
                int qty = player.acceleratedProcessing();

                if (player.getInventory().getAmount(221348) < qty)
                    qty = player.getInventory().getAmount(221348);

                player.getInventory().delete(221348, qty);
                player.getInventory().add(221316, 10 * qty);
                player.getSkillManager().addExperience(Skill.FLETCHING, 350 * qty);
            } else
                player.getPacketSender().sendMessage("You need 95 Fletching to do this.");
        }

        if (firstItem == 946 || secondItem == 946) {
            if (player.getSkillManager().getCurrentLevel(Skill.FLETCHING) >= 95) {

                int qtyAmethyst = player.acceleratedProcessing();

                if (player.getInventory().getAmount(221348) < qtyAmethyst)
                    qtyAmethyst = player.getInventory().getAmount(221347);

                player.getInventory().delete(221348, qtyAmethyst);
                player.getInventory().add(221326, 15 * qtyAmethyst);
                player.getSkillManager().addExperience(Skill.FLETCHING, 350 * qtyAmethyst);
            } else
                player.getPacketSender().sendMessage("You need 95 Fletching to do this.");
            return;
        }

    }

}
