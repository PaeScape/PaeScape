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

public class MiningScript {


    private static final Animation gathering_anim = new Animation(627);
    private static final Animation walking_anim = new Animation(6395);
    private static final Animation tele_anim = new Animation(8939);
    private static final int delay = 10;
    //mining guild, mining guild,
    //coal, mithril
    private static final int[] telex = {3023, 3023};
    private static final int[] teley = {9740, 9740};
    private static final int[] sourcex = {3044, 3049};
    private static final int[] sourcey = {9740, 9738};
    private static final int homex = 3088;
    private static final int homey = 3504;
    private static final int bankx = 3096;
    private static final int banky = 3494;
    public static int ores = 0;
    private static final int qtyneeded = 28;
    private static final int npcmodel = 1;

    public static void start(Player player) {
        player.getPacketSender().sendInterfaceRemoval();
        spawn(player);
        player.getPacketSender().sendMessage("Your Miner has arrived!");
        player.getMinigameAttributes().getMiningBotAttributes().setHasMiningBot(true);

    }


    private final static void spawn(Player player) {

        NPC bot = new NPC(npcmodel, getSpawnPos()).setSpawnedFor(player);
        player.getMinigameAttributes().getMiningBotAttributes().setBot(bot);
        World.register(bot);

        player.getMinigameAttributes().getMiningBotAttributes().setHasMiningBot(true);
        player.getMinigameAttributes().getMiningBotAttributes().setSourceLocationX(sourcex[0]);
        player.getMinigameAttributes().getMiningBotAttributes().setSourceLocationY(sourcey[0]);
        player.getMinigameAttributes().getMiningBotAttributes().setTeleLocationX(telex[0]);
        player.getMinigameAttributes().getMiningBotAttributes().setTeleLocationY(teley[0]);

        TaskManager.submit(new Task(4, player, false) {
            @Override
            public void execute() {

                if ((player == null) || (player.getSession().getState() == SessionState.LOGGING_OUT) || (!player.logout())) {

                    World.deregister(player.getMinigameAttributes().getMiningBotAttributes().getBot());
                    player.getMinigameAttributes().getMiningBotAttributes().setHasMiningBot(false);
                    stop();
                } else if ((bot.getPosition().getX() == homex) && (bot.getPosition().getY() == homey) && (bot.isRegistered())) {
                    walkToBank(bot, player);
                } else if ((bot.getPosition().getX() == bankx) && (bot.getPosition().getY() == banky) && (bot.isRegistered())) {
                    teleToSource(bot, player);
                } else if ((bot.getPosition().getX() == player.getMinigameAttributes().getMiningBotAttributes().getTeleLocationX()) &&
                        (bot.getPosition().getY() == player.getMinigameAttributes().getMiningBotAttributes().getTeleLocationY()) &&
                        (bot.isRegistered())) {
                    walkToResource(bot, player);
                } else if (((bot.getPosition().getX() == player.getMinigameAttributes().getMiningBotAttributes().getSourceLocationX()) &&
                        (bot.getPosition().getY() == player.getMinigameAttributes().getMiningBotAttributes().getSourceLocationY())) &&
                        (player.getMinigameAttributes().getMiningBotAttributes().getQtyGathered() < 28) && (bot.isRegistered())) {
                    mineResources(bot, player);
                } else if (((bot.getPosition().getX() == player.getMinigameAttributes().getMiningBotAttributes().getSourceLocationX()) &&
                        (bot.getPosition().getY() == player.getMinigameAttributes().getMiningBotAttributes().getSourceLocationY())) &&
                        (player.getMinigameAttributes().getMiningBotAttributes().getQtyGathered() >= 28) && (bot.isRegistered())) {
                    teleHome(bot, player);
                } else if ((player.getMinigameAttributes().getMiningBotAttributes().getResourcesLeftToGather() > 0) &&
                        (!player.getMinigameAttributes().getMiningBotAttributes().getHasMiningBot())) {
                    spawn(player);
                } else if (player.getMinigameAttributes().getMiningBotAttributes().getResourcesLeftToGather() <= 0) {
                    player.getPacketSender().sendMessage("Your bot is all done!");
                    World.deregister(player.getMinigameAttributes().getMiningBotAttributes().getBot());
                    player.getMinigameAttributes().getMiningBotAttributes().setHasMiningBot(false);
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
        player.getMinigameAttributes().getMiningBotAttributes().setQtyGathered(0);
        player.getPacketSender().sendMessage("Your Miner has " + player.getMinigameAttributes().getMiningBotAttributes().getResourcesLeftToGather() + " ores left to mine for you.");
        //player.getPacketSender().sendMessage("Your Miner is teleing to home to bank ores.");
        bot.performAnimation(tele_anim);
        bot.moveTo(new Position(homex, homey));
    }

    public static void walkToBank(NPC bot, Player player) {
        bot.forceChat("Time to clear my inventory!");
        player.getPacketSender().sendMessage("Your Miner is walking to the bank.");
        TaskManager.submit(new Task(4, bot, false) {
            @Override
            public void execute() {
                PathFinder.findPath(bot, bankx, banky, false, 1, 1);
            }
        });
        player.getPacketSender().sendMessage("Your Miner has mined " + player.getMinigameAttributes().getMiningBotAttributes().getResourcesLeftToCollect() + " ores so far.");
    }

    public static void teleToSource(NPC bot, Player player) {
        int loc = RandomUtility.inclusiveRandom(telex.length - 1);
        player.getMinigameAttributes().getMiningBotAttributes().setSourceLocationX(sourcex[loc]);
        player.getMinigameAttributes().getMiningBotAttributes().setSourceLocationY(sourcey[loc]);
        player.getMinigameAttributes().getMiningBotAttributes().setTeleLocationX(telex[loc]);
        player.getMinigameAttributes().getMiningBotAttributes().setTeleLocationY(teley[loc]);
        bot.forceChat("Time to go to the mine!");
        //player.getPacketSender().sendMessage("Your Miner is teleing to the mine.");
        bot.performAnimation(tele_anim);
        bot.moveTo(new Position(player.getMinigameAttributes().getMiningBotAttributes().getTeleLocationX(), player.getMinigameAttributes().getMiningBotAttributes().getTeleLocationY()));
    }

    public static void walkToResource(NPC bot, Player player) {
        bot.forceChat("Time to get some ores!");
        //player.getPacketSender().sendMessage("Your Miner is walking to the ore.");
        PathFinder.findPath(bot, player.getMinigameAttributes().getMiningBotAttributes().getSourceLocationX(), player.getMinigameAttributes().getMiningBotAttributes().getSourceLocationY(), false, 1, 1);
    }

    private static void mineResources(NPC bot, Player player) {
        //player.getPacketSender().sendMessage("Your Miner is mining ore.");
        TaskManager.submit(new Task(delay, bot, true) {
            @Override
            public void execute() {
                bot.performAnimation(gathering_anim);
            }
        });
        player.getMinigameAttributes().getMiningBotAttributes().setQtyGathered(player.getMinigameAttributes().getMiningBotAttributes().getQtyGathered() + 1);
        player.getMinigameAttributes().getMiningBotAttributes().setResourcesLeftToCollect(player.getMinigameAttributes().getMiningBotAttributes().getResourcesLeftToCollect() + 1);
        player.getMinigameAttributes().getMiningBotAttributes().setResourcesLeftToGather(player.getMinigameAttributes().getMiningBotAttributes().getResourcesLeftToGather() - 1);
        bot.forceChat("I have " + player.getMinigameAttributes().getMiningBotAttributes().getQtyGathered() + " ores in my inventory!");
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


    public static void endBot(NPC bot, Player player) {
        World.deregister(player.getMinigameAttributes().getMiningBotAttributes().getBot());
    }

}