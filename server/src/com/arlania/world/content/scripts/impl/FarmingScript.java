package com.arlania.world.content.scripts.impl;


import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.Position;
import com.arlania.model.Skill;
import com.arlania.model.movement.PathFinder;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.entity.impl.player.Player;

public class FarmingScript {


    private static final Animation gathering_anim = new Animation(2286);
    private static final Animation walking_anim = new Animation(6395);
    private static final Animation tele_anim = new Animation(8939);
    private static final int delay = 10;
    private static final int[] telex = {3052};
    private static final int[] teley = {3304};
    private static final int[] sourcex = {3058};
    private static final int[] sourcey = {3310};
    private static final int homex = 3088;
    private static final int homey = 3504;
    private static final int bankx = 3096;
    private static final int banky = 3494;
    public static int herbs = 0;
    private static final int qtyneeded = 28;

    public static void start(Player player) {
        player.getPacketSender().sendInterfaceRemoval();
        spawn(player);

    }


    private final static void spawn(Player player) {

        player.performAnimation(tele_anim);
        player.moveTo(new Position(homex, homey));
        player.getPacketSender().sendMessage("Just testing out my bot script.");

        TaskManager.submit(new Task(4, player, false) {
            @Override
            public void execute() {

                if ((player.getPosition().getX() == homex) && (player.getPosition().getY() == homey) && (player.isRegistered())) {
                    walkToBank(player);
                } else if ((player.getPosition().getX() == bankx) && (player.getPosition().getY() == banky) && (player.isRegistered())) {
                    teleToSource(player);
                } else if ((player.getPosition().getX() == player.getMinigameAttributes().getFarmingBotAttributes().getTeleLocationX()) &&
                        (player.getPosition().getY() == player.getMinigameAttributes().getFarmingBotAttributes().getTeleLocationY()) &&
                        (player.isRegistered())) {
                    walkToResource(player);
                } else if (((player.getPosition().getX() == player.getPosition().getX()) &&
                        (player.getPosition().getY() == player.getPosition().getY())) &&
                        (player.getMinigameAttributes().getFarmingBotAttributes().getQtyGathered() < 28) && (player.isRegistered())) {
                    gatherResources(player);
                } else if (((player.getPosition().getX() == player.getPosition().getX()) &&
                        (player.getPosition().getY() == player.getPosition().getY())) &&
                        (player.getMinigameAttributes().getFarmingBotAttributes().getQtyGathered() >= 28) && (player.isRegistered())) {
                    teleHome(player);
                } else if ((player.getMinigameAttributes().getFarmingBotAttributes().getResourcesLeftToGather() > 0) &&
                        (!player.getMinigameAttributes().getFarmingBotAttributes().getHasFarmingBot())) {
                    spawn(player);
                } else if (player.getMinigameAttributes().getFarmingBotAttributes().getResourcesLeftToGather() <= 0) {
                    player.getPacketSender().sendMessage("Your player is all done!");
                    World.deregister(player.getMinigameAttributes().getFarmingBotAttributes().getBot());
                    player.getMinigameAttributes().getFarmingBotAttributes().setHasFarmingBot(false);
                    stop();
                }
            }
        });

    }

    private final static Position getSpawnPos() {

        return new Position(3088, 3504, 0);
    }

    public static void teleHome(Player player) {
        player.forceChat("Time to go to the Bank!");
        player.getMinigameAttributes().getFarmingBotAttributes().setQtyGathered(0);
        player.getPacketSender().sendMessage("Your Farmer has " + player.getMinigameAttributes().getFarmingBotAttributes().getResourcesLeftToGather() + " herbs left to farm for you.");
        //player.getPacketSender().sendMessage("Your Farmer is teleing to home to bank herbs.");
        player.performAnimation(tele_anim);
        player.moveTo(new Position(homex, homey));
    }

    public static void walkToBank(Player player) {
        player.forceChat("Time to clear my inventory!");
        player.getPacketSender().sendMessage("Your Farmer is walking to the bank.");
        TaskManager.submit(new Task(4, player, false) {
            @Override
            public void execute() {
                PathFinder.findPath(player, bankx, banky, false, 1, 1);
            }
        });
        player.getPacketSender().sendMessage("Your Farmer has farmed " + player.getMinigameAttributes().getFarmingBotAttributes().getResourcesLeftToCollect() + " herbs so far.");
    }

    public static void teleToSource(Player player) {
        int loc = RandomUtility.inclusiveRandom(telex.length - 1);
        player.getMinigameAttributes().getFarmingBotAttributes().setSourceLocationX(sourcex[loc]);
        player.getMinigameAttributes().getFarmingBotAttributes().setSourceLocationY(sourcey[loc]);
        player.getMinigameAttributes().getFarmingBotAttributes().setTeleLocationX(telex[loc]);
        player.getMinigameAttributes().getFarmingBotAttributes().setTeleLocationY(teley[loc]);
        player.forceChat("Time to go to the farm!");
        //player.getPacketSender().sendMessage("Your Farmer is teleing to the farm.");
        player.performAnimation(tele_anim);
        player.moveTo(new Position(player.getMinigameAttributes().getFarmingBotAttributes().getTeleLocationX(), player.getMinigameAttributes().getFarmingBotAttributes().getTeleLocationY()));
    }

    public static void walkToResource(Player player) {
        player.forceChat("Time to get some herbs!");
        //player.getPacketSender().sendMessage("Your Farmer is walking to the herbs.");
        PathFinder.findPath(player, player.getPosition().getX(), player.getPosition().getY(), false, 1, 1);
    }

    private static void gatherResources(Player player) {
        //player.getPacketSender().sendMessage("Your Farmer is farming herbs.");
        TaskManager.submit(new Task(delay, player, true) {
            @Override
            public void execute() {
                player.performAnimation(gathering_anim);
            }
        });

        player.getMinigameAttributes().getFarmingBotAttributes().setQtyGathered(player.getMinigameAttributes().getFarmingBotAttributes().getQtyGathered() + 1);
        player.getSkillManager().addExperience(Skill.FARMING, player.getSkillManager().getCurrentLevel(Skill.FARMING));
        player.getMinigameAttributes().getFarmingBotAttributes().setResourcesLeftToCollect(player.getMinigameAttributes().getFarmingBotAttributes().getResourcesLeftToCollect() + 1);
        player.getMinigameAttributes().getFarmingBotAttributes().setResourcesLeftToGather(player.getMinigameAttributes().getFarmingBotAttributes().getResourcesLeftToGather() - 1);
        player.forceChat("I have " + player.getMinigameAttributes().getFarmingBotAttributes().getQtyGathered() + " herbs in my inventory!");
        if (player.getMinigameAttributes().getFarmingBotAttributes().getQtyGathered() >= qtyneeded) {
            player.getMinigameAttributes().getFarmingBotAttributes().setQtyGathered(qtyneeded);
            TaskManager.submit(new Task(4, player, true) {
                @Override
                public void execute() {
                    player.performAnimation(walking_anim);
                }
            });
        }
    }


    public static void endBot(Player player) {
        player.logout();
    }

}