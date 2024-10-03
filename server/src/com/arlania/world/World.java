package com.arlania.world;

import com.arlania.DiscordBot;
import com.arlania.GameSettings;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.GameMode;
import com.arlania.model.Locations.Location;
import com.arlania.model.StaffRights;
import com.arlania.model.container.impl.Shop;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.util.Stopwatch;
import com.arlania.world.content.*;
import com.arlania.world.content.clan.ClanChatManager;
import com.arlania.world.content.globalevents.GlobalEventHandler;
import com.arlania.world.content.globalevents.GlobalEvent;
import com.arlania.world.content.grandexchange.GrandExchangeOffers;
import com.arlania.world.content.marketplace.Marketplace;
import com.arlania.world.content.minigames.impl.MotherLodeMine;
import com.arlania.world.content.minigames.impl.kingdom.NobilitySystem;
import com.arlania.world.content.minigames.impl.skilling.Wintertodt;
import com.arlania.world.content.pos.TradingPostManager;
import com.arlania.world.entity.Entity;
import com.arlania.world.entity.EntityHandler;
import com.arlania.world.entity.impl.CharacterList;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.PlayerHandler;
import com.arlania.world.entity.updating.NpcUpdateSequence;
import com.arlania.world.entity.updating.PlayerUpdateSequence;
import com.arlania.world.entity.updating.UpdateSequence;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.javacord.api.entity.message.MessageBuilder;

import java.util.Iterator;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * @author Gabriel Hannason
 * Thanks to lare96 for help with parallel updating system
 */
public class World {

    /**
     * All of the registered players.
     */
    private static final CharacterList<Player> players = new CharacterList<>(1000);

    /**
     * All of the registered NPCs.
     */
    private static final CharacterList<NPC> npcs = new CharacterList<>(2027);

    /**
     * Used to block the game thread until updating has completed.
     */
    private static final Phaser synchronizer = new Phaser(1);

    /**
     * A thread pool that will update players in parallel.
     */
    private static final ExecutorService updateExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new ThreadFactoryBuilder().setNameFormat("UpdateThread").setPriority(Thread.MAX_PRIORITY).build());

    /**
     * The queue of {@link Player}s waiting to be logged in.
     **/
    private static final Queue<Player> logins = new ConcurrentLinkedQueue<>();

    /**
     * The queue of {@link Player}s waiting to be logged out.
     **/
    private static final Queue<Player> logouts = new ConcurrentLinkedQueue<>();

    private static final int WORLD_STATE_SAVE_TIME = 450000; //5 minutes
    private static final Stopwatch worldSaveStateTimer = new Stopwatch().reset();
    public static long taxes = 0;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public static void register(Entity entity) {
        EntityHandler.register(entity);
    }

    public static void deregister(Entity entity) {
        EntityHandler.deregister(entity);
    }

    public static Player getPlayerByName(String username) {
        Optional<Player> op = players.search(p -> p != null && p.getUsername().equals(Misc.formatText(username)));
        return op.isPresent() ? op.get() : null;
    }

    public static Player getPlayerByIndex(int username) {
        Optional<Player> op = players.search(p -> p != null && p.getIndex() == username);
        return op.isPresent() ? op.get() : null;
    }

    public static boolean getSeasonalTeamBonus(Player player) {
        Optional<Player> op = players.search(p -> p != null && p.getGameMode() == GameMode.SEASONAL_IRONMAN && p.seasonalLeader == player.seasonalLeader && p.getLocation() == player.getLocation());
        return op.isPresent();
    }

    public static Player getPlayerByLong(long encodedName) {
        Optional<Player> op = players.search(p -> p != null && p.getLongUsername().equals(encodedName));
        return op.isPresent() ? op.get() : null;
    }

    public static void sendMessage(String messageType, String message) {
        switch (messageType) {
            case "drops":
                players.stream().filter(p -> p != null && p.worldFilterDrops).forEach(p -> p.getPacketSender().sendMessage(message));
                break;
            case "levels":
                players.stream().filter(p -> p != null && p.worldFilterLevels).forEach(p -> p.getPacketSender().sendMessage(message));
                break;
            case "status":
                players.stream().filter(p -> p != null && p.worldFilterStatus).forEach(p -> p.getPacketSender().sendMessage(message));
                break;
            case "seasonal":
                players.stream().filter(p -> p != null && p.worldFilterSeasonal).forEach(p -> p.getPacketSender().sendMessage(message));
                break;
            case "events":
                players.stream().filter(p -> p != null && p.worldFilterEvents).forEach(p -> p.getPacketSender().sendMessage(message));
                break;
            case "yell":
                players.stream().filter(p -> p != null && p.worldFilterYell).forEach(p -> p.getPacketSender().sendMessage(message));
                break;
            case "pvp":
                players.stream().filter(p -> p != null && p.worldFilterPVP).forEach(p -> p.getPacketSender().sendMessage(message));
                break;
            case "minigames":
                players.stream().filter(p -> p != null && p.worldFilterMinigames).forEach(p -> p.getPacketSender().sendMessage(message));
                break;
            case "reminders":
                players.stream().filter(p -> p != null && p.worldFilterReminders).forEach(p -> p.getPacketSender().sendMessage(message));
                break;
            case "developer":
                players.stream().filter(p -> p != null && p.getStaffRights() == StaffRights.DEVELOPER).forEach(p -> p.getPacketSender().sendMessage(message));
                break;
            default:
                players.stream().filter(p -> p != null).forEach(p -> p.getPacketSender().sendMessage(message));
                break;
        }

        //PlayerLogs.log("World Log", message);
    }

    private static String cleanDiscMessage(String message) {
        message.replaceAll("[^a-zA-Z0-9]", " ");
        String needsClean = "@blu@,@red@,@gre@,<col=996633>";
        String[] words = needsClean.split(",");
        int count = 0;
        for (String s : words) {
            if (message.contains(s)) {
                message.replace(s, "");
                System.out.println("replaced: " + s);
                count++;
            }
        }
        System.out.println("cleaned " + count + " messages");
        System.out.println(message);
        return message;
    }

    public static void sendStaffMessage(String message) {
        players.stream().filter(p -> p != null && (p.getStaffRights() == StaffRights.OWNER || p.getStaffRights() == StaffRights.DEVELOPER || p.getStaffRights() == StaffRights.ADMINISTRATOR || p.getStaffRights() == StaffRights.MODERATOR)).forEach(p -> p.getPacketSender().sendMessage(message));
    }

    public static void sendDroppedItemMessage(Location location) {
        players.stream().filter(p -> p != null && (p.getLocation() == location)).forEach(p -> new MessageBuilder().append(p.getUsername() + "is in Location: " + location).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_DROPPED_ITEMS_CH).get()));
    }

    public static void updateServerTime() {
        //players.forEach(p-> p.getPacketSender().sendString(39172, PlayerPanel.LINE_START + "@or1@Server Time: @yel@"+Misc.getCurrentServerTime()));
    }

    public static void updatePlayersOnline() {
        //players.forEach(p-> p.getPacketSender().sendString(39173, PlayerPanel.LINE_START + "@or1@Players Online: @yel@"+players.size()));
        players.forEach(p -> p.getPacketSender().sendString(26608, "@or2@Players Online: @gre@" + players.size()));
        players.forEach(p -> p.getPacketSender().sendString(57003, "Players:  @gre@" + World.getPlayers().size()));
        updateStaffList();
    }

    public static void updateStaffList() {
        TaskManager.submit(new Task(false) {
            @Override
            protected void execute() {
                players.forEach(p -> StaffList.updateInterface(p));
                stop();
            }
        });
    }

    public static void savePlayers() {
        players.forEach(p -> p.save());
    }

    public static void saveWorldState() {
        TradingPostManager.saveShops();
        WellOfGoodwill.save();
        WellOfWealth.save();
        GrandExchangeOffers.save();
        ClanChatManager.save();
        NobilitySystem.save();
        Marketplace.save();
    }

    public static CharacterList<Player> getPlayers() {
        return players;
    }

    public static CharacterList<NPC> getNpcs() {
        return npcs;
    }

    public static void sequence() {

        // Handle queued logins.
        for (int amount = 0; amount < GameSettings.LOGIN_THRESHOLD; amount++) {
            Player player = logins.poll();
            if (player == null)
                break;
            PlayerHandler.handleLogin(player);
        }

        // Handle queued logouts.
        int amount = 0;
        Iterator<Player> $it = logouts.iterator();
        while ($it.hasNext()) {
            Player player = $it.next();
            if (player == null || amount >= GameSettings.LOGOUT_THRESHOLD)
                break;
            if (PlayerHandler.handleLogout(player)) {
                $it.remove();
                amount++;
            }
        }

        // Handle queued vote rewards
//        for (int i = 0; i < GameSettings.VOTE_REWARDING_THRESHOLD; i++) {
//            Player player = voteRewards.poll();
//            if (player == null)
//                break;
//            Voting.handleQueuedReward(player);
//        }

        NobilitySystem.sequence();
        //FightPit.sequence();
        //Cows.sequence();
        RandomUtility.sequence();
        Reminders.sequence();
        //Cows.spawnMainNPCs();
        ShootingStar.sequence();
        EvilTrees.sequence();
        GlobalEventHandler.sequence();
        //TriviaBot.sequence();
        //ShopRestocking.sequence();
        // First we construct the update sequences.
        UpdateSequence<Player> playerUpdate = new PlayerUpdateSequence(synchronizer, updateExecutor);
        UpdateSequence<NPC> npcUpdate = new NpcUpdateSequence();
        // Then we execute pre-updating code.
        players.forEach(playerUpdate::executePreUpdate);
        npcs.forEach(npcUpdate::executePreUpdate);
        // Then we execute parallelized updating code.
        synchronizer.bulkRegister(players.size());
        players.forEach(playerUpdate::executeUpdate);
        synchronizer.arriveAndAwaitAdvance();
        // Then we execute post-updating code.
        players.forEach(playerUpdate::executePostUpdate);
        npcs.forEach(npcUpdate::executePostUpdate);



        if (GameSettings.npcMessageTick == 15) {
            GameSettings.npcMessageTick = 0;
            World.getNpcs().forEach(n -> MotherLodeMine.trashTalkingDwarf(n));
        }

        if (GameSettings.storeRefreshTick == 500) {
            GameSettings.storeRefreshTick = 0;
            Shop.ShopManager.parseShops().load();
        }

        if (GameSettings.wellOfEventsCooldown < 30000)
            GameSettings.wellOfEventsCooldown++;

        if (GlobalEventHandler.effectActive(GlobalEvent.Effect.GLOBAL_BOSS_KILLS)) {
            World.sendMessage("events", "@red@" + GlobalEventHandler.totalGlobalBossKills + " total bosses have been killed!");
            players.forEach(p -> p.getPacketSender().sendMessage("@red@[EVENT] Each player has been rewarded with an Event box for 100 Boss Kills!"));
            players.forEach(p -> p.getInventory().add(2685, 1));
            GlobalEventHandler.totalGlobalBossKills -= 100;
        }

        if (Wintertodt.WintertodtBoss && Wintertodt.WintertodtHealth < 10000) {
            Wintertodt.WintertodtHealth += 10;
            if (Wintertodt.WintertodtHealth > 10000)
                Wintertodt.WintertodtHealth = 10000;
        }

        if (Wintertodt.WintertodtHealth > 0 && Wintertodt.WintertodtBoss) {
            World.getPlayers().stream().filter(p -> p != null && p.getLocation() == Location.WINTERTODT).forEach(p -> Wintertodt.WintertodtHealth += 15);

            if (Wintertodt.WintertodtHealth > 10000)
                Wintertodt.WintertodtHealth = 10000;
        }

        if (Wintertodt.WintertodtHealth < 1 && Wintertodt.WintertodtBoss) {
            Wintertodt.WintertodtBoss = false;
            World.sendMessage("minigames", "");
            World.sendMessage("minigames", "<img=83> @or2@[Wintertodt]@bla@ The Wintertodt has fallen and will respawn momentarily!");
            World.getPlayers().stream().filter(p -> p != null && p.getLocation() == Location.WINTERTODT).forEach(p -> p.WintertodtLoot = true);
            World.getPlayers().stream().filter(p -> p != null && p.getLocation() == Location.WINTERTODT && p.WintertodtPoints >= 500).forEach(p -> Location.WINTERTODT.leave(p));
        }

        if (Wintertodt.burnTimer > 0)
            Wintertodt.burnTimer--;

        if (RandomUtility.inclusiveRandom(500) == 1) {
            Wintertodt.brokenBrazier();
        }

        GameSettings.npcMessageTick++;
        GameSettings.storeRefreshTick++;

        if (worldSaveStateTimer.elapsed(WORLD_STATE_SAVE_TIME)) {
            worldSaveStateTimer.reset();
            saveWorldState();
        }
    }

    public static Queue<Player> getLoginQueue() {
        return logins;
    }

    public static Queue<Player> getLogoutQueue() {
        return logouts;
    }
}
