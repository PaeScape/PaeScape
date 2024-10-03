package com.arlania.world.content.scripts.impl;


import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.Position;
import com.arlania.model.movement.PathFinder;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class WCingScript {


    private static final Animation gathering_anim = new Animation(875);
    private static final Animation walking_anim = new Animation(6395);
    private static final Animation tele_anim = new Animation(8939);
    private static final int delay = 10;
    private static final int[] telex = {2711, 2711};
    private static final int[] teley = {3463, 3463};
    private static final int[] sourcex = {2708, 2714};
    private static final int[] sourcey = {3462, 3462};
    private static final int homex = 3088;
    private static final int homey = 3504;
    private static final int bankx = 3096;
    private static final int banky = 3494;
    public static int resources = 0;
    private static final int qtyneeded = 28;
    private static final int npcmodel = 1;

    public static void start(Player player) {
        player.getPacketSender().sendInterfaceRemoval();
        spawn(player);
        player.getPacketSender().sendMessage("Your Woodcutter has arrived!");
        player.getMinigameAttributes().getWCingBotAttributes().setHasWCingBot(true);

    }


    private final static void spawn(Player player) {

        NPC bot = new NPC(npcmodel, getSpawnPos()).setSpawnedFor(player);
        player.getMinigameAttributes().getWCingBotAttributes().setBot(bot);
        World.register(bot);

        player.getMinigameAttributes().getWCingBotAttributes().setSourceLocationX(sourcex[0]);
        player.getMinigameAttributes().getWCingBotAttributes().setSourceLocationY(sourcey[0]);
        player.getMinigameAttributes().getWCingBotAttributes().setTeleLocationX(telex[0]);
        player.getMinigameAttributes().getWCingBotAttributes().setTeleLocationY(teley[0]);

        TaskManager.submit(new Task(4, player, false) {
            @Override
            public void execute() {

                if ((bot.getPosition().getX() == homex) && (bot.getPosition().getY() == homey)) {
                    walkToBank(bot, player);
                }
                if ((bot.getPosition().getX() == bankx) && (bot.getPosition().getY() == banky)) {
                    teleToSource(bot, player);
                }
                if ((bot.getPosition().getX() == player.getMinigameAttributes().getWCingBotAttributes().getTeleLocationX()) &&
                        (bot.getPosition().getY() == player.getMinigameAttributes().getWCingBotAttributes().getTeleLocationY())) {
                    walkToSource(bot, player);
                }
                if (((bot.getPosition().getX() == player.getMinigameAttributes().getWCingBotAttributes().getSourceLocationX()) &&
                        (bot.getPosition().getY() == player.getMinigameAttributes().getWCingBotAttributes().getSourceLocationY())) &&
                        (player.getMinigameAttributes().getWCingBotAttributes().getQtyGathered() < 28)) {
                    gatherResources(bot, player);
                }
                if (((bot.getPosition().getX() == player.getMinigameAttributes().getWCingBotAttributes().getSourceLocationX()) &&
                        (bot.getPosition().getY() == player.getMinigameAttributes().getWCingBotAttributes().getSourceLocationY())) &&
                        (player.getMinigameAttributes().getWCingBotAttributes().getQtyGathered() >= 28)) {
                    teleHome(bot, player);
                }
                if (player.getMinigameAttributes().getWCingBotAttributes().getResourcesLeftToGather() <= 0) {
                    player.getPacketSender().sendMessage("Your bot is all done!");
                    endBot(bot, player);
                    stop();
                }
                if (!player.isRegistered()) //stop bot when player resources out
                {
                    World.deregister(bot);
                    player.getMinigameAttributes().getWCingBotAttributes().setHasWCingBot(false);
                    endBot(player.getMinigameAttributes().getWCingBotAttributes().getBot(), player);
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
        player.getMinigameAttributes().getWCingBotAttributes().setResourcesLeftToCollect(player.getMinigameAttributes().getWCingBotAttributes().getResourcesLeftToCollect() + 28);
        player.getMinigameAttributes().getWCingBotAttributes().setResourcesLeftToGather(player.getMinigameAttributes().getWCingBotAttributes().getResourcesLeftToGather() - 28);
        player.getMinigameAttributes().getWCingBotAttributes().setQtyGathered(0);
        player.getPacketSender().sendMessage("Your Woodcutter has " + player.getMinigameAttributes().getWCingBotAttributes().getResourcesLeftToGather() + " logs left to cut for you.");
        //player.getPacketSender().sendMessage("Your Woodcutter is teleing to home to bank resources.");
        bot.performAnimation(tele_anim);
        bot.moveTo(new Position(homex, homey));
    }

    public static void walkToBank(NPC bot, Player player) {
        bot.forceChat("Time to clear my inventory!");
        //player.getPacketSender().sendMessage("Your Woodcutter is walking to the bank.");
        TaskManager.submit(new Task(4, bot, false) {
            @Override
            public void execute() {
                PathFinder.findPath(bot, bankx, banky, false, 1, 1);
            }
        });
        player.getPacketSender().sendMessage("Your Woodcutter has cut " + player.getMinigameAttributes().getWCingBotAttributes().getResourcesLeftToCollect() + " logs so far.");
    }

    public static void teleToSource(NPC bot, Player player) {
        int loc = RandomUtility.inclusiveRandom(telex.length - 1);
        player.getMinigameAttributes().getWCingBotAttributes().setSourceLocationX(sourcex[loc]);
        player.getMinigameAttributes().getWCingBotAttributes().setSourceLocationY(sourcey[loc]);
        player.getMinigameAttributes().getWCingBotAttributes().setTeleLocationX(telex[loc]);
        player.getMinigameAttributes().getWCingBotAttributes().setTeleLocationY(teley[loc]);
        bot.forceChat("Time to go to the treees!");
        //player.getPacketSender().sendMessage("Your Woodcutter is teleing to the sources.");
        bot.performAnimation(tele_anim);
        bot.moveTo(new Position(player.getMinigameAttributes().getWCingBotAttributes().getTeleLocationX(), player.getMinigameAttributes().getWCingBotAttributes().getTeleLocationY()));
    }

    public static void walkToSource(NPC bot, Player player) {
        bot.forceChat("Time to get some logs!");
        //player.getPacketSender().sendMessage("Your Woodcutter is walking to the source.");
        PathFinder.findPath(bot, player.getMinigameAttributes().getWCingBotAttributes().getSourceLocationX(), player.getMinigameAttributes().getWCingBotAttributes().getSourceLocationY(), false, 1, 1);
    }

    private static void gatherResources(NPC bot, Player player) {
        //player.getPacketSender().sendMessage("Your Woodcutter is cutting resources.");
        TaskManager.submit(new Task(delay, bot, true) {
            @Override
            public void execute() {
                bot.performAnimation(gathering_anim);
            }
        });
        player.getMinigameAttributes().getWCingBotAttributes().setQtyGathered(player.getMinigameAttributes().getWCingBotAttributes().getQtyGathered() + 1);
        bot.forceChat("I have " + player.getMinigameAttributes().getWCingBotAttributes().getQtyGathered() + " logs in my inventory!");
        if (player.getMinigameAttributes().getWCingBotAttributes().getQtyGathered() >= qtyneeded) {
            player.getMinigameAttributes().getWCingBotAttributes().setQtyGathered(qtyneeded);
            TaskManager.submit(new Task(4, bot, true) {
                @Override
                public void execute() {
                    bot.performAnimation(walking_anim);
                }
            });
        }
    }


    public static void endBot(NPC npc, Player player) {
        World.deregister(player.getMinigameAttributes().getWCingBotAttributes().getBot());
    }

}