package com.arlania;

import com.arlania.util.ShutdownHook;
import com.arlania.util.everythingrs.backup.EverythingRSBackups;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The starting point of Arlania.
 *
 * @author Gabriel
 * @author Samy
 */
public class GameServer {

    private static final GameLoader loader = new GameLoader(GameSettings.GAME_PORT);
    private static final Logger logger = Logger.getLogger("PaeScape");
    private static boolean updating;
    public static boolean debug = false;

    public static void main(String[] params) {
        debug = params.length == 1 && params[0].equals("-debug");
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
        try {

            if (!debug) {
                EverythingRSBackups backups = new EverythingRSBackups();
                String userHome = System.getProperty("user.home");
                backups.setSource(userHome + File.separator + "Saves");
                backups.setOutput("./backups");
                //Every minute = 60
                //Every hour = 60 * 60
                //Every day  = 60 * 60 * 24
                //Default backups every 3 hours
                backups.setTime(60 * 60 * 3);
                backups.setMaxBackups(56);
                //backups.enableDropbox("auth_key");
                backups.init();
            }


            logger.info("Initializing the loader...");
            loader.init();
            loader.finish();
            logger.info("The loader has finished loading utility tasks.");
            logger.info("PaeScape is now online on port " + GameSettings.GAME_PORT + "!");
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Could not start PaeScape! Program terminated.", ex);
            System.exit(1);
        }
    }

    public static GameLoader getLoader() {
        return loader;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setUpdating(boolean updating) {
        GameServer.updating = updating;
    }

    public static boolean isUpdating() {
        return GameServer.updating;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static String getSaveDirectory() {
        return System.getProperty("user.home") + File.separator + "Saves" + File.separator;
    }

}