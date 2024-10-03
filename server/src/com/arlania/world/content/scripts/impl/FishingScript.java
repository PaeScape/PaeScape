package com.arlania.world.content.scripts.impl;


import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.Position;
import com.arlania.model.movement.PathFinder;
import com.arlania.net.SessionState;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class FishingScript {


    private static final Animation gathering_anim = new Animation(618);
    private static final Animation walking_anim = new Animation(6395);
    private static final Animation tele_anim = new Animation(8939);
    private static final int delay = 10;
    private static final int[] telex = {2345, 2345};
    private static final int[] teley = {3698, 3698};
    private static final int[] sourcex = {2346, 2343};
    private static final int[] sourcey = {3701, 3701};
    private static final int homex = 3088;
    private static final int homey = 3504;
    private static final int bankx = 3096;
    private static final int banky = 3494;
    public static int fish = 0;
    private static final int qtyneeded = 28;
    private static final int npcmodel = 1;

    public static void start(Player player) {
        player.getPacketSender().sendInterfaceRemoval();
        spawn(player);
        player.getPacketSender().sendMessage("Your Fisher has arrived!");
        player.getMinigameAttributes().getFishingBotAttributes().setHasFishingBot(true);

    }


    private final static void spawn(Player player) {

        NPC bot = new NPC(npcmodel, getSpawnPos()).setSpawnedFor(player);
        player.getMinigameAttributes().getFishingBotAttributes().setBot(bot);
        World.register(bot);

        player.getMinigameAttributes().getFishingBotAttributes().setHasFishingBot(true);
        player.getMinigameAttributes().getFishingBotAttributes().setSourceLocationX(sourcex[0]);
        player.getMinigameAttributes().getFishingBotAttributes().setSourceLocationY(sourcey[0]);
        player.getMinigameAttributes().getFishingBotAttributes().setTeleLocationX(telex[0]);
        player.getMinigameAttributes().getFishingBotAttributes().setTeleLocationY(teley[0]);

        TaskManager.submit(new Task(4, player, false) {
            @Override
            public void execute() {

                if ((player == null) || (player.getSession().getState() == SessionState.LOGGING_OUT) || (!player.logout())) {

                    World.deregister(player.getMinigameAttributes().getFishingBotAttributes().getBot());
                    player.getMinigameAttributes().getFishingBotAttributes().setHasFishingBot(false);
                    stop();
                } else if ((bot.getPosition().getX() == homex) && (bot.getPosition().getY() == homey) && (bot.isRegistered())) {
                    walkToBank(bot, player);
                } else if ((bot.getPosition().getX() == bankx) && (bot.getPosition().getY() == banky) && (bot.isRegistered())) {
                    teleToSource(bot, player);
                } else if ((bot.getPosition().getX() == player.getMinigameAttributes().getFishingBotAttributes().getTeleLocationX()) &&
                        (bot.getPosition().getY() == player.getMinigameAttributes().getFishingBotAttributes().getTeleLocationY()) &&
                        (bot.isRegistered())) {
                    walkToResource(bot, player);
                } else if (((bot.getPosition().getX() == player.getMinigameAttributes().getFishingBotAttributes().getSourceLocationX()) &&
                        (bot.getPosition().getY() == player.getMinigameAttributes().getFishingBotAttributes().getSourceLocationY())) &&
                        (player.getMinigameAttributes().getFishingBotAttributes().getQtyGathered() < 28) && (bot.isRegistered())) {
                    mineResources(bot, player);
                } else if (((bot.getPosition().getX() == player.getMinigameAttributes().getFishingBotAttributes().getSourceLocationX()) &&
                        (bot.getPosition().getY() == player.getMinigameAttributes().getFishingBotAttributes().getSourceLocationY())) &&
                        (player.getMinigameAttributes().getFishingBotAttributes().getQtyGathered() >= 28) && (bot.isRegistered())) {
                    teleHome(bot, player);
                } else if ((player.getMinigameAttributes().getFishingBotAttributes().getResourcesLeftToGather() > 0) &&
                        (!player.getMinigameAttributes().getFishingBotAttributes().getHasFishingBot())) {
                    spawn(player);
                } else if (player.getMinigameAttributes().getFishingBotAttributes().getResourcesLeftToGather() <= 0) {
                    player.getPacketSender().sendMessage("Your bot is all done!");
                    World.deregister(player.getMinigameAttributes().getFishingBotAttributes().getBot());
                    player.getMinigameAttributes().getFishingBotAttributes().setHasFishingBot(false);
                    stop();
                }
            }
        });

    }

    private final static Position getSpawnPos() {

        return new Position(3088, 3504, 0);
    }

    public static void teleHome(NPC bot, Player player) {
        bot.forceChat("Time to go to the Bank!");
        player.getMinigameAttributes().getFishingBotAttributes().setQtyGathered(0);
        player.getPacketSender().sendMessage("Your Fisher has " + player.getMinigameAttributes().getFishingBotAttributes().getResourcesLeftToGather() + " fish left to mine for you.");
        //player.getPacketSender().sendMessage("Your Fisher is teleing to home to bank fish.");
        bot.performAnimation(tele_anim);
        bot.moveTo(new Position(homex, homey));
    }

    public static void walkToBank(NPC bot, Player player) {
        bot.forceChat("Time to clear my inventory!");
        player.getPacketSender().sendMessage("Your Fisher is walking to the bank.");
        TaskManager.submit(new Task(4, bot, false) {
            @Override
            public void execute() {
                PathFinder.findPath(bot, bankx, banky, false, 1, 1);
            }
        });
        player.getPacketSender().sendMessage("Your Fisher has mined " + player.getMinigameAttributes().getFishingBotAttributes().getResourcesLeftToCollect() + " fish so far.");
    }

    public static void teleToSource(NPC bot, Player player) {
        int loc = RandomUtility.inclusiveRandom(telex.length - 1);
        player.getMinigameAttributes().getFishingBotAttributes().setSourceLocationX(sourcex[loc]);
        player.getMinigameAttributes().getFishingBotAttributes().setSourceLocationY(sourcey[loc]);
        player.getMinigameAttributes().getFishingBotAttributes().setTeleLocationX(telex[loc]);
        player.getMinigameAttributes().getFishingBotAttributes().setTeleLocationY(teley[loc]);
        bot.forceChat("Time to go to the mine!");
        //player.getPacketSender().sendMessage("Your Fisher is teleing to the mine.");
        bot.performAnimation(tele_anim);
        bot.moveTo(new Position(player.getMinigameAttributes().getFishingBotAttributes().getTeleLocationX(), player.getMinigameAttributes().getFishingBotAttributes().getTeleLocationY()));
    }

    public static void walkToResource(NPC bot, Player player) {
        bot.forceChat("Time to get some fish!");
        //player.getPacketSender().sendMessage("Your Fisher is walking to the ore.");
        PathFinder.findPath(bot, player.getMinigameAttributes().getFishingBotAttributes().getSourceLocationX(), player.getMinigameAttributes().getFishingBotAttributes().getSourceLocationY(), false, 1, 1);
    }

    private static void mineResources(NPC bot, Player player) {
        //player.getPacketSender().sendMessage("Your Fisher is fishing ore.");
        TaskManager.submit(new Task(delay, bot, true) {
            @Override
            public void execute() {
                bot.performAnimation(gathering_anim);
            }
        });
        player.getMinigameAttributes().getFishingBotAttributes().setQtyGathered(player.getMinigameAttributes().getFishingBotAttributes().getQtyGathered() + 1);
        player.getMinigameAttributes().getFishingBotAttributes().setResourcesLeftToCollect(player.getMinigameAttributes().getFishingBotAttributes().getResourcesLeftToCollect() + 1);
        player.getMinigameAttributes().getFishingBotAttributes().setResourcesLeftToGather(player.getMinigameAttributes().getFishingBotAttributes().getResourcesLeftToGather() - 1);
        bot.forceChat("I have " + player.getMinigameAttributes().getFishingBotAttributes().getQtyGathered() + " fish in my inventory!");
        if (player.getMinigameAttributes().getFishingBotAttributes().getQtyGathered() >= qtyneeded) {
            player.getMinigameAttributes().getFishingBotAttributes().setQtyGathered(qtyneeded);
            TaskManager.submit(new Task(4, bot, true) {
                @Override
                public void execute() {
                    bot.performAnimation(walking_anim);
                }
            });
        }
    }


    public static void endBot(NPC bot, Player player) {
        World.deregister(player.getMinigameAttributes().getFishingBotAttributes().getBot());
    }

}