package com.arlania.world.content;

import com.arlania.util.RandomUtility;
import com.arlania.util.Stopwatch;
import com.arlania.world.World;

/*
 * @author Bas
 * www.Arlania.com
 */

public class Reminders {


    private static final int TIME = 450000; //5 minutes
    private static final Stopwatch timer = new Stopwatch().reset();
    public static String currentMessage;

    /*
     * Random Message Data
     */
    private static final String[][] MESSAGE_DATA = {
            //{"@blu@[Server]@blu@ Use the command ::drop (npcname) for drop tables"},
            {"@blu@[Server]@blu@ Did you know we have a wiki? Wiki.PaeScape.Online"},
            {"@blu@[Server]@blu@ Did you know we have a wiki? Wiki.PaeScape.Online"},
            {"@blu@[Server]@blu@ Did you know that Hardcore Ironman lose their status with any death?!"},
            {"@blu@[Server]@blu@ Did you know that Hardcore Ironman lose their status with any death?!"},
            {"@blu@[Server]@blu@ Please remember to vote every 12 hours for free coins and PaePoints!"},
            {"@blu@[Server]@blu@ Remember to spread the word and invite your friends to play!"},
            {"@blu@[Server]@blu@ Did you know PaePoints are used to buy upgrades for your account?!"},
            {"@blu@[Server]@blu@ Click on the world map to open the teleport menu!"},
            {"@blu@[Server]@blu@ Did you know you can sell nearly anything to the general store?"},
            {"@blu@[Server]@blu@ Did you know you can craft jewelry using gems on gold jewelry?"},
            {"@blu@[Server]@blu@ Did you know there is an <img=32> <img=33> <img=34> mode on this server?"},
            {"@blu@[Server]@blu@ Did you know there is an Ultimate Ironman mode on this server?"},
            {"@blu@[Server]@blu@ Please remember to vote every 12 hours for free coins and PaePoints!"},
            {"@blu@[Server]@blu@ Did you know the Berserker perk increases your melee attack speed?"},
            {"@blu@[Server]@blu@ Did you know the Bullseye perk increases your ranged attack speed?"},
            {"@blu@[Server]@blu@ Did you know we have a Prestige mode?!"},
            {"@blu@[Server]@blu@ Did you know we have Equipment Upgrades?!"},
            {"@blu@[Server]@blu@ Did you know we have Custom Raids?!"},
            {"@blu@[Server]@blu@ Did you know Rare Candy can be used to get bonus XP or bonus drop rate?!"},
            {"@blu@[Server]@blu@ Did you know the Prophetic perk increases your magic attack speed?"},
            {"@blu@[Server]@blu@ Please remember to vote every 12 hours for free coins and PaePoints!"},
            {"@blu@[Server]@blu@ Did you know you can gain PaePoints through Slayer, PVM, voting, and donating?!"},
            {"@blu@[Server]@blu@ Did you know you get Defenders from the Untradeable shop?!"}
    };

    /*
     * Sequence called in world.java
     * Handles the main method
     * Grabs random message and announces it
     */
    public static void sequence() {
        if (timer.elapsed(TIME)) {
            timer.reset();
            currentMessage = MESSAGE_DATA[RandomUtility.inclusiveRandom(MESSAGE_DATA.length - 1)][0];
            World.sendMessage("reminders", currentMessage);
        }
    }

}