package com.arlania.world.content;

import com.arlania.GameLoader;
import com.arlania.GameSettings;
import com.arlania.model.GameMode;
import com.arlania.model.Locations;
import com.arlania.util.Misc;
import com.arlania.world.World;
import com.arlania.world.content.globalevents.GlobalEventHandler;
import com.arlania.world.content.globalevents.GlobalEvent;
import com.arlania.world.entity.impl.player.Player;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class InformationPanel {

    public static final String LINE_START = "@red@   > ";

    public static void refreshPanel(Player player) {

        player.getPacketSender().sendString(45001, "Information");

        player.getPacketSender().sendString(45777, "@or3@-@whi@ General");

        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);

        int eventTime = GlobalEventHandler.globalEventTimeRemaining / 100;
        int playerEventTime = player.playerEventTimer / 100;
        int playerTimeLeft = 150 - playerEventTime;

        int counter = 45005;

        String goodwill = "Inactive";

        if (GameSettings.wellGoodwill)
            goodwill = "Active";
        else
            goodwill = "Inactive";

        int xprate = player.getexprate();

        if (GlobalEventHandler.effectActive(GlobalEvent.Effect.DOUBLE_EXP) || player.doubleExpEvent || player.activeDoubleXP)
            xprate *= 2;

        int daysLeft = player.subscriptionEndDate - GameLoader.getEpoch();

        if (daysLeft < 0)
            daysLeft = 0;

        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Players online: @yel@" + World.getPlayers().size());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Players in Wilderness: @yel@" + Locations.PLAYERS_IN_WILD);
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Server Time: @yel@" + Misc.getCurrentServerTime());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Time Played: @yel@" + Misc.getHoursPlayed((player.getTotalPlayTime() + player.getRecordedLogin().elapsed())));
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Experience Rate: @yel@" + xprate);
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Username: @yel@" + player.getUsername());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Staff Rank: @yel@" + player.getStaffRights().toString());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Donated: @yel@" + player.getPointsHandler().getAmountDonated());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Subscription: @yel@" + player.getSubscription());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Days Left: @yel@" + daysLeft);
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Completed C-Logs: @yel@" + player.completedLogs);
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Completed Prestiges: @yel@" + player.prestige);
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bonus Prestiges: @yel@" + player.extraPrestiges);

        player.getPacketSender().sendString(counter++, "");

        player.getPacketSender().sendString(counter++, "@or3@-@whi@ Daily");
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Evil Tree: @yel@" + (EvilTrees.getLocation() != null ? EvilTrees.getLocation().playerPanelFrame : "N/A"));
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Crashed Star: @yel@" + (ShootingStar.getLocation() != null ? ShootingStar.getLocation().playerPanelFrame : "N/A"));
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Daily Boss: @yel@" + GameLoader.getSpecialBossDay());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Daily Skill: @yel@" + GameLoader.getSkillDay());


        player.getPacketSender().sendString(counter++, "");
        player.getPacketSender().sendString(counter++, "@or3@-@whi@Events");
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Global Event Timer: @yel@" + eventTime);
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Global Event: " + (GlobalEventHandler.globalEventTimeRemaining > 0 ? "@gre@On" : "@red@Off"));
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Personal Event Timer: @yel@" + playerTimeLeft);
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Double XP Time: @yel@" + player.doubleExpTimer);
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bonus Time: @yel@" + player.getBonusTime());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Well of Goodwill: @yel@" + goodwill);

        player.getPacketSender().sendString(counter++, "");
        player.getPacketSender().sendString(counter++, "@or3@-@whi@Currencies");
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Perk Upgrades: @yel@" + player.perkUpgrades);
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Loyalty Points:@yel@ " + player.getPointsHandler().getLoyaltyPoints());
        if (player.getGameMode() == GameMode.SEASONAL_IRONMAN)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Seasonal Points: @yel@" + player.seasonPoints);
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@PaePoints: @yel@" + player.getPaePoints());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Wildy Points: @yel@" + player.WildyPoints);
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Seasonal: @yel@" + player.seasonalCurrency);
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Achievement Points: @yel@" + player.getAchievementPoints());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Vote Points: @yel@" + player.getVotePoints());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Candy Credits: @yel@" + player.candyCredit);


        player.getPacketSender().sendString(counter++, "");
        player.getPacketSender().sendString(counter++, "@or3@-@whi@PvM");
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Total Boss Kills: @yel@" + player.getPointsHandler().gettotalbosskills());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Barrows Completions: @yel@" + player.getCompletedBarrows());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@GWD Raid Completions: @yel@" + player.getGwdRaidKC());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@CoX Completions: @yel@" + player.getCoxKC());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@ToB Completions: @yel@" + player.getTobKC());
        //player.getPacketSender().sendString(counter++, LINE_START + "@or1@Gauntlet Completions: @yel@" + player.GauntletKC);

        player.getPacketSender().sendString(counter++, "");

        player.getPacketSender().sendString(counter++, "@or3@-@whi@PvP");
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Wilderness Kills:@yel@ " + player.getPlayerKillingAttributes().getPlayerKills());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Wilderness Deaths:@yel@ " + player.getPlayerKillingAttributes().getPlayerDeaths());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Killstreak:@yel@ " + player.getPlayerKillingAttributes().getPlayerKillStreak());
        //player.getPacketSender().sendString(counter++, LINE_START + "@or1@Arena Victories:@yel@ "+player.
        //player.getPacketSender().sendString(counter++, LINE_START + "@or1@Arena Losses:@yel@ "+player.getPointsHandler().getLoyaltyPoints());

        player.getPacketSender().sendString(counter++, "");

        double d = player.wealthBoost * 100;
        DecimalFormat df = new DecimalFormat("##.##");

        player.getPacketSender().sendString(counter++, "@or3@-@whi@Misc");
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@RNG Boost: @yel@" + df.format(d) + "%");
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Presents opened: @yel@" + player.getPointsHandler().getopenedpresents());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Rare Candy used: @yel@" + player.getRareCandy());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Crystal Chests: @yel@" + player.CrystalChests);
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Total Slayer Tasks: @yel@" + player.totalSlayerTasks);
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Total Skiller tasks: @yel@" + player.totalSkillerTasks);
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Idle Time: @yel@" + player.getIdleTime() / 60000 + " min");
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Time Left: @yel@" + ((player.getIdleTime() - (System.currentTimeMillis() - player.getActionTracker().getActions().getFirst().getWhen())) / 60000) + " minutes");


        player.getPacketSender().sendString(counter++, "");

        player.getPacketSender().sendString(counter++, "@or3@-@whi@Charged Equipment");
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Scythe of Vitur: @yel@" + player.getScytheCharges());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Sanguinesti Staff: @yel@" + player.getSanguinestiCharges());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Surgebox: @yel@" + player.getSurgeboxCharges());

        player.getPacketSender().sendString(counter++, "");

        player.getPacketSender().sendString(counter++, "@or3@-@whi@Max Hit Calculation");
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Melee: @yel@" + player.meleeMaxHit);
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Ranged: @yel@" + player.rangedMaxHit);
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Magic: @yel@" + player.magicMaxHit);

        player.getPacketSender().sendString(counter++, "");

        player.getPacketSender().sendString(counter++, "@or3@-@whi@Other Combat Bonuses");
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Damage Absorb: @yel@" + player.absorbBonus + "%");
        //player.getPacketSender().sendString(counter++, LINE_START + "@or1@Nobility Boost: @yel@" + player.nobilityBoost + "%");
        //player.getPacketSender().sendString(counter++, LINE_START + "@or1@Upgrade Boost: @yel@" + player.upgradeBoost + "%");
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Perk Boost: @yel@" + player.perkBoost + "%");
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@PVM Boost: @yel@" + player.pvmBoost + "%");
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Slayer Boost: @yel@" + player.slayerBoost + "%");


        player.getPacketSender().sendString(counter++, "");

        player.getPacketSender().sendString(counter++, "@whi@Total Experience: @yel@");
        player.getPacketSender().sendString(counter++, LINE_START + "@gre@" + myFormat.format(player.totalXP));


    }


}