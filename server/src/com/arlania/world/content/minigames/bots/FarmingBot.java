package com.arlania.world.content.minigames.bots;


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

public class FarmingBot {


    private static final Animation gathering_anim = new Animation(2286);
    private static final Animation walking_anim = new Animation(6395);
    private static final Animation tele_anim = new Animation(8939);
    private static final int delay = 10;
    private static final int npcmodel = 452;
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
    private NPC bot;

    public static void start(Player player) {
        player.getPacketSender().sendInterfaceRemoval();
        spawn(player);
        player.getPacketSender().sendMessage("Your Farmer has arrived!");
        player.getMinigameAttributes().getFarmingBotAttributes().setHasFarmingBot(true);

    }


    private final static void spawn(Player player) {

        NPC bot = new NPC(npcmodel, getSpawnPos()).setSpawnedFor(player);
        player.getMinigameAttributes().getFarmingBotAttributes().setBot(bot);
        World.register(bot);

        player.getMinigameAttributes().getFarmingBotAttributes().setHasFarmingBot(true);
        player.getMinigameAttributes().getFarmingBotAttributes().setSourceLocationX(sourcex[0]);
        player.getMinigameAttributes().getFarmingBotAttributes().setSourceLocationY(sourcey[0]);
        player.getMinigameAttributes().getFarmingBotAttributes().setTeleLocationX(telex[0]);
        player.getMinigameAttributes().getFarmingBotAttributes().setTeleLocationY(teley[0]);

        TaskManager.submit(new Task(4, player, false) {
            @Override
            public void execute() {

                if ((player == null) || (player.getSession().getState() == SessionState.LOGGING_OUT) || (!player.logout())) {

                    World.deregister(player.getMinigameAttributes().getFarmingBotAttributes().getBot());
                    player.getMinigameAttributes().getFarmingBotAttributes().setHasFarmingBot(false);
                    stop();
                } else if ((bot.getPosition().getX() == homex) && (bot.getPosition().getY() == homey) && (bot.isRegistered())) {
                    walkToBank(bot, player);
                } else if ((bot.getPosition().getX() == bankx) && (bot.getPosition().getY() == banky) && (bot.isRegistered())) {
                    teleToSource(bot, player);
                } else if ((bot.getPosition().getX() == player.getMinigameAttributes().getFarmingBotAttributes().getTeleLocationX()) &&
                        (bot.getPosition().getY() == player.getMinigameAttributes().getFarmingBotAttributes().getTeleLocationY()) &&
                        (bot.isRegistered())) {
                    walkToResource(bot, player);
                } else if (((bot.getPosition().getX() == player.getMinigameAttributes().getFarmingBotAttributes().getSourceLocationX()) &&
                        (bot.getPosition().getY() == player.getMinigameAttributes().getFarmingBotAttributes().getSourceLocationY())) &&
                        (player.getMinigameAttributes().getFarmingBotAttributes().getQtyGathered() < 28) && (bot.isRegistered())) {
                    mineResources(bot, player);
                } else if (((bot.getPosition().getX() == player.getMinigameAttributes().getFarmingBotAttributes().getSourceLocationX()) &&
                        (bot.getPosition().getY() == player.getMinigameAttributes().getFarmingBotAttributes().getSourceLocationY())) &&
                        (player.getMinigameAttributes().getFarmingBotAttributes().getQtyGathered() >= 28) && (bot.isRegistered())) {
                    teleHome(bot, player);
                } else if ((player.getMinigameAttributes().getFarmingBotAttributes().getResourcesLeftToGather() > 0) &&
                        (!player.getMinigameAttributes().getFarmingBotAttributes().getHasFarmingBot())) {
                    spawn(player);
                } else if (player.getMinigameAttributes().getFarmingBotAttributes().getResourcesLeftToGather() <= 0) {
                    player.getPacketSender().sendMessage("Your bot is all done!");
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

    public static void teleHome(NPC bot, Player player) {
        bot.forceChat("Time to go to the Bank!");
        player.getMinigameAttributes().getFarmingBotAttributes().setQtyGathered(0);
        player.getPacketSender().sendMessage("Your Farmer has " + player.getMinigameAttributes().getFarmingBotAttributes().getResourcesLeftToGather() + " herbs left to farm for you.");
        //player.getPacketSender().sendMessage("Your Farmer is teleing to home to bank herbs.");
        bot.performAnimation(tele_anim);
        bot.moveTo(new Position(homex, homey));
    }

    public static void walkToBank(NPC bot, Player player) {
        bot.forceChat("Time to clear my inventory!");
        player.getPacketSender().sendMessage("Your Farmer is walking to the bank.");
        TaskManager.submit(new Task(4, bot, false) {
            @Override
            public void execute() {
                PathFinder.findPath(bot, bankx, banky, false, 1, 1);
            }
        });
        player.getPacketSender().sendMessage("Your Farmer has farmed " + player.getMinigameAttributes().getFarmingBotAttributes().getResourcesLeftToCollect() + " herbs so far.");
    }

    public static void teleToSource(NPC bot, Player player) {
        int loc = RandomUtility.inclusiveRandom(telex.length - 1);
        player.getMinigameAttributes().getFarmingBotAttributes().setSourceLocationX(sourcex[loc]);
        player.getMinigameAttributes().getFarmingBotAttributes().setSourceLocationY(sourcey[loc]);
        player.getMinigameAttributes().getFarmingBotAttributes().setTeleLocationX(telex[loc]);
        player.getMinigameAttributes().getFarmingBotAttributes().setTeleLocationY(teley[loc]);
        bot.forceChat("Time to go to the farm!");
        //player.getPacketSender().sendMessage("Your Farmer is teleing to the farm.");
        bot.performAnimation(tele_anim);
        bot.moveTo(new Position(player.getMinigameAttributes().getFarmingBotAttributes().getTeleLocationX(), player.getMinigameAttributes().getFarmingBotAttributes().getTeleLocationY()));
    }

    public static void walkToResource(NPC bot, Player player) {
        bot.forceChat("Time to get some herbs!");
        //player.getPacketSender().sendMessage("Your Farmer is walking to the herbs.");
        PathFinder.findPath(bot, player.getMinigameAttributes().getFarmingBotAttributes().getSourceLocationX(), player.getMinigameAttributes().getFarmingBotAttributes().getSourceLocationY(), false, 1, 1);
    }

    private static void mineResources(NPC bot, Player player) {
        //player.getPacketSender().sendMessage("Your Farmer is farming herbs.");
        TaskManager.submit(new Task(delay, bot, true) {
            @Override
            public void execute() {
                bot.performAnimation(gathering_anim);
            }
        });
        player.getMinigameAttributes().getFarmingBotAttributes().setQtyGathered(player.getMinigameAttributes().getFarmingBotAttributes().getQtyGathered() + 1);
        player.getMinigameAttributes().getFarmingBotAttributes().setResourcesLeftToCollect(player.getMinigameAttributes().getFarmingBotAttributes().getResourcesLeftToCollect() + 1);
        player.getMinigameAttributes().getFarmingBotAttributes().setResourcesLeftToGather(player.getMinigameAttributes().getFarmingBotAttributes().getResourcesLeftToGather() - 1);
        bot.forceChat("I have " + player.getMinigameAttributes().getFarmingBotAttributes().getQtyGathered() + " herbs in my inventory!");
        if (player.getMinigameAttributes().getFarmingBotAttributes().getQtyGathered() >= qtyneeded) {
            player.getMinigameAttributes().getFarmingBotAttributes().setQtyGathered(qtyneeded);
            TaskManager.submit(new Task(4, bot, true) {
                @Override
                public void execute() {
                    bot.performAnimation(walking_anim);
                }
            });
        }
    }


    public static void endBot(NPC bot, Player player) {
        World.deregister(player.getMinigameAttributes().getFarmingBotAttributes().getBot());
    }

}