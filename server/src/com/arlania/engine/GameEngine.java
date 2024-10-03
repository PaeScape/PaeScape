package com.arlania.engine;

import com.arlania.GameServer;
import com.arlania.engine.task.TaskManager;
import com.arlania.world.World;
import com.arlania.world.content.clan.ClanChatManager;
import com.arlania.world.content.grandexchange.GrandExchangeOffers;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * @author lare96
 * @author Gabriel Hannason
 */
public final class GameEngine implements Runnable {

    private final ScheduledExecutorService logicService = GameEngine.createLogicService();

    //private static final int PROCESS_GAME_TICK = 2;

    //private EngineState engineState = EngineState.PACKET_PROCESSING;

    //private int engineTick = 0;

    @Override
    public void run() {
        try {
			/*switch(engineState) {
			case PACKET_PROCESSING:
				World.getPlayers().forEach($it -> $it.getSession().handlePrioritizedMessageQueue());
				break;
			case GAME_PROCESSING:
				TaskManager.sequence();
				World.sequence();
				break;
			}
			engineState = next();*/
            TaskManager.sequence();
            World.sequence();
        } catch (Throwable e) {
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
            World.savePlayers();
            World.saveWorldState();
        }
    }

	/*private EngineState next() {
		if (engineTick == PROCESS_GAME_TICK) {
			engineTick = 0;
			return EngineState.GAME_PROCESSING;
		}
		engineTick++;
		return EngineState.PACKET_PROCESSING;
	}

	private enum EngineState {
		PACKET_PROCESSING,
		GAME_PROCESSING;
	}*/

    public void submit(Runnable t) {
        try {
            logicService.execute(t);
        } catch (Throwable e) {
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
        }
    }

    /**
     * STATIC
     **/

    public static ScheduledExecutorService createLogicService() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.setRejectedExecutionHandler(new CallerRunsPolicy());
        executor.setThreadFactory(new ThreadFactoryBuilder().setNameFormat("LogicServiceThread").build());
        executor.setKeepAliveTime(45, TimeUnit.SECONDS);
        executor.allowCoreThreadTimeOut(true);
        return Executors.unconfigurableScheduledExecutorService(executor);
    }
}