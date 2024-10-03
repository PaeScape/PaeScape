package com.arlania;

import com.arlania.engine.GameEngine;
import com.arlania.engine.task.TaskManager;
import com.arlania.engine.task.impl.ServerTimeUpdateTask;
import com.arlania.model.container.impl.Shop.ShopManager;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.model.definitions.NPCDrops;
import com.arlania.model.definitions.NpcDefinition;
import com.arlania.model.definitions.WeaponInterfaces;
import com.arlania.net.PipelineFactory;
import com.arlania.net.security.ConnectionHandler;
import com.arlania.world.clip.region.RegionClipping;
import com.arlania.world.content.*;
import com.arlania.world.content.achievements.AchievementData;
import com.arlania.world.content.clan.ClanChatManager;
import com.arlania.world.content.clog.CollectionLog;
import com.arlania.world.content.combat.effect.CombatPoisonEffect.CombatPoisonData;
import com.arlania.world.content.combat.strategy.CombatStrategies;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.content.grandexchange.GrandExchangeOffers;
import com.arlania.world.content.marketplace.Marketplace;
import com.arlania.world.content.minigames.impl.RevenantArena;
import com.arlania.world.content.minigames.impl.kingdom.NobilitySystem;
import com.arlania.world.content.pos.TradingPostManager;
import com.arlania.world.content.transportation.FairyRings;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.regionalspawns.karamja.KaramjaNpcData;
import com.arlania.world.regionalspawns.karamja.KaramjaObjectData;
import com.arlania.world.regionalspawns.misthalin.MisthalinNpcData;
import com.arlania.world.regionalspawns.misthalin.MisthalinObjectData;
import com.arlania.world.regionalspawns.tirannwn.GauntletObjectData;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.util.HashedWheelTimer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Credit: lare96, Gabbe
 */
public final class GameLoader {
    /*
     * Daily events
     * Handles the checking of the day to represent
     * which event will be active on such day
     */
    public static final int YEAR = 2022;
    public static final int SUNDAY = 1;
    public static final int MONDAY = 2;
    public static final int TUESDAY = 3;
    public static final int WEDNESDAY = 4;
    public static final int THURSDAY = 5;
    public static final int FRIDAY = 6;
    public static final int SATURDAY = 7;
    public static Object getSpecialSkillDay;
    public static Object getSpecialBossDay;
    public static Object getIncreasedLootDay;

    //Double EXP days
	/*public static int getDoubleEXPWeekend() {
		return (getDay() == FRIDAY || getDay() == SATURDAY) ? 2 : 1;
		//return (getDay() == FRIDAY || getDay() == SATURDAY) ? 2 : 2;
	}*/
    //Finds the day of the week
    public static int getDay() {
        Calendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static int getDayOfTheMonth() {
        Calendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getEpoch() {
        LocalDate date = LocalDate.of(GameLoader.getYear(), GameLoader.getMonth(), GameLoader.getDayOfTheMonth()); // The date you want to convert

        // Calculate the number of days since the epoch (January 1, 1970)
        long epochDays = date.toEpochDay();

        // Convert to int
        int epochInteger = (int) epochDays;

        return epochInteger;
    }

    public static int getDayOfTheYear() {
        Calendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    public static int getMonth() {
        Calendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getYear() {
        Calendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.YEAR);
    }

    public static int getWeekOfYear() {
        Calendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    //Skilling events for each day
    public static String getSkillDay() {
        switch (getDayOfTheMonth()) {
            case 1:
                return "AGILITY";
            case 2:
                return "SKILLER";
            case 3:
                return "COOKING";
            case 4:
                return "CRAFTING";
            case 5:
                return "DUNGEONEERING";
            case 6:
                return "FARMING";
            case 7:
                return "FIREMAKING";
            case 8:
                return "FISHING";
            case 9:
                return "FLETCHING";
            case 10:
                return "HERBLORE";
            case 11:
                return "HUNTER";
            case 12:
                return "MINING";
            case 13:
                return "RUNECRAFTING";
            case 14:
                return "SLAYER";
            case 15:
                return "SMITHING";
            case 16:
                return "SUMMONING";
            case 17:
                return "THIEVING";
            case 18:
                return "WOODCUTTING";
            case 19:
                return "AGILITY";
            case 20:
                return "SKILLER";
            case 21:
                return "COOKING";
            case 22:
                return "CRAFTING";
            case 23:
                return "DUNGEONEERING";
            case 24:
                return "FARMING";
            case 25:
                return "FIREMAKING";
            case 26:
                return "FISHING";
            case 27:
                return "FLETCHING";
            case 28:
                return "HERBLORE";
            case 29:
                return "HUNTER";
            case 30:
                return "MINING";
            case 31:
                return "RUNECRAFTING";
        }
        return "SKILL";
    }

    public static String getSpecialBossDay() {
        switch (getDayOfTheMonth()) {
            case 1:
                return "Chaos Raid";
            case 2:
                return "Theatre of Blood";
            case 3:
                return "Dagannoth Kings";
            case 4:
                return "Bandos GWD";
            case 5:
                return "Armadyl GWD";
            case 6:
                return "Saradomin GWD";
            case 7:
                return "Zamorak GWD";
            case 8:
                return "Barrows RAID";
            case 9:
                return "Zulrah";
            case 10:
                return "GWD Raid";
            case 11:
                return "Cerberus";
            case 12:
                return "Abyssal Sire";
            case 13:
                return "Thermo";
            case 14:
                return "Venenatis";
            case 15:
                return "KBD";
            case 16:
                return "Corporeal Beast";
            case 17:
                return "Vet'ion";
            case 18:
                return "Callisto";
            case 19:
                return "Scorpia";
            case 20:
                return "Wildywyrm";
            case 21:
                return "Zulrah";
            case 22:
                return "Giant Mole";
            case 23:
                return "Kalphite Queen";
            case 24:
                return "Vorkath";
            case 25:
                return "Bandos GWD";
            case 26:
                return "Armadyl GWD";
            case 27:
                return "Saradomin GWD";
            case 28:
                return "Zamorak GWD";
            case 29:
                return "GWD Raid";
            case 30:
                return "COX";
            case 31:
                return "Barrows Raid";
        }
        return "BOSS";
    }

    public static int getIncreasedLootDay() {
        switch (getDayOfTheMonth()) {
            case 1:
                return 7286;
            case 2:
                return 22374;
            case 3:
                return 2883;
            case 4:
                return 6260;
            case 5:
                return 6222;
            case 6:
                return 6247;
            case 7:
                return 6203;
            case 8:
                return 2025;
            case 9:
                return 2042;
            case 10:
                return 13447;
            case 11:
                return 1999;
            case 12:
                return 5886;
            case 13:
                return 499;
            case 14:
                return 7287;
            case 15:
                return 50;
            case 16:
                return 8133;
            case 17:
                return 2009;
            case 18:
                return 2006;
            case 19:
                return 2001;
            case 20:
                return 2000;
            case 21:
                return 3334;
            case 22:
                return 3340;
            case 23:
                return 1158;
            case 24:
                return 22061;
            case 25:
                return 6260;
            case 26:
                return 6222;
            case 27:
                return 6247;
            case 28:
                return 6203;
            case 29:
                return 13447;
            case 30:
                return 21554;
            case 31:
                return 2025;
        }
        return 1;
    }


    private final ExecutorService serviceLoader = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat("GameLoadingThread").build());
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat("GameThread").build());
    private final GameEngine engine;
    private final int port;

    GameLoader(int port) {
        this.port = port;
        this.engine = new GameEngine();
    }

    public void init() {
        Preconditions.checkState(!serviceLoader.isShutdown(), "The bootstrap has been bound already!");
        executeServiceLoad();
        serviceLoader.shutdown();
    }

    public void finish() throws IOException, InterruptedException {
        if (!serviceLoader.awaitTermination(15, TimeUnit.MINUTES))
            throw new IllegalStateException("The background service load took too long!");
        ExecutorService networkExecutor = Executors.newCachedThreadPool();
        ServerBootstrap serverBootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(networkExecutor, networkExecutor));
        serverBootstrap.setPipelineFactory(new PipelineFactory(new HashedWheelTimer()));
        serverBootstrap.bind(new InetSocketAddress(port));
        executor.scheduleAtFixedRate(engine, 0, GameSettings.ENGINE_PROCESSING_CYCLE_RATE, TimeUnit.MILLISECONDS);
        TaskManager.submit(new ServerTimeUpdateTask());
    }

    private void executeServiceLoad() {

        if (GameSettings.DISCORD) {
            GameServer.getLogger().info("Initiating Discord Bot...");
            serviceLoader.execute(() -> DiscordBot.initialize());
            // VoteTracker should be initialized after DiscordBot
            serviceLoader.execute(() -> VoteTracker.initialize());
        }

        serviceLoader.execute(() -> ConnectionHandler.init());
        serviceLoader.execute(() -> PlayerPunishment.init());
        serviceLoader.execute(() -> {
            try {
                RegionClipping.init();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serviceLoader.execute(() -> CustomObjects.init());
        serviceLoader.execute(() -> ItemDefinition.init());
        serviceLoader.execute(() -> AchievementData.checkDuplicateIds());
        serviceLoader.execute(() -> Lottery.init());
        serviceLoader.execute(() -> GrandExchangeOffers.init());
        serviceLoader.execute(() -> WellOfGoodwill.init());
        serviceLoader.execute(() -> ClanChatManager.init());
        serviceLoader.execute(() -> CombatPoisonData.init());
        serviceLoader.execute(() -> CombatStrategies.init());
        serviceLoader.execute(() -> NpcDefinition.parseNpcs().load());
        serviceLoader.execute(() -> NPCDrops.parseDrops().load());
        serviceLoader.execute(() -> WeaponInterfaces.parseInterfaces().load());
        serviceLoader.execute(() -> ShopManager.parseShops().load());
        serviceLoader.execute(() -> DialogueManager.parseDialogues().load());
        serviceLoader.execute(() -> NPC.init());
        serviceLoader.execute(() -> FairyRings.load());
        serviceLoader.execute(() -> MisthalinNpcData.load());
        serviceLoader.execute(() -> MisthalinObjectData.load());
        serviceLoader.execute(() -> KaramjaNpcData.load());
        serviceLoader.execute(() -> KaramjaObjectData.load());
        serviceLoader.execute(() -> ProfileViewing.init());
        serviceLoader.execute(() -> TradingPostManager.loadShops());
        serviceLoader.execute(() -> MonsterDrops.initialize());
        //serviceLoader.execute(() -> DumpBonuses.main(null));
        //serviceLoader.execute(() -> WildyWyrmEvent.initialize());

        serviceLoader.execute(() -> CollectionLog.init());
        serviceLoader.execute(() -> Marketplace.init());
        serviceLoader.execute(() -> RevenantArena.start());
        //serviceLoader.execute(() -> DMZ.spawnDMZ());
        serviceLoader.execute(() -> NobilitySystem.init());
    }

    public GameEngine getEngine() {
        return engine;
    }
}