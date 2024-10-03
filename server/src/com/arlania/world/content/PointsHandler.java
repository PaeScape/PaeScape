package com.arlania.world.content;

import com.arlania.world.entity.impl.player.Player;

public class PointsHandler {

    private final Player p;

    public PointsHandler(Player p) {
        this.p = p;
    }

    public void reset() {
        dungTokens = openedpresents = (int) (loyaltyPoints = totalbosskills = slayerPoints = pkPoints = PCPoints = 0);
        p.getPlayerKillingAttributes().setPlayerKillStreak(0);
        p.getPlayerKillingAttributes().setPlayerKills(0);
        p.getPlayerKillingAttributes().setPlayerDeaths(0);
        p.getDueling().arenaStats[0] = p.getDueling().arenaStats[1] = 0;
    }

    public PointsHandler refreshPanel() {
        //p.getPacketSender().sendString(26703, "@or2@Trivia Points: @gre@"+triviaPoints);

		/*p.getPacketSender().sendString(26702, "@or2@Amount Donated: @gre@$"+amountdonated);
		p.getPacketSender().sendString(26702, "@or2@Donator Points: @gre@"+donatorpoints);
		p.getPacketSender().sendString(26701, "@or2@Loyalty Points: @gre@"+(int)loyaltyPoints);
		p.getPacketSender().sendString(26704, "@or2@Total Boss Kills: @gre@ "+totalbosskills);
		p.getPacketSender().sendString(26707, "@or2@Boss Points: @gre@ "+p.getBossPoints());
		p.getPacketSender().sendString(26707, "@or2@Raid Points: @gre@ "+p.getRaidPoints());
		p.getPacketSender().sendString(26705, "@or2@Slayer Points: @gre@"+slayerPoints);
		p.getPacketSender().sendString(26705, "@or2@Pest Control Points: @gre@"+PCPoints);
		p.getPacketSender().sendString(26709, "@or2@Pk Points: @gre@"+pkPoints);

		p.getPacketSender().sendString(26711, "@or2@Wilderness Killstreak: @gre@"+p.getPlayerKillingAttributes().getPlayerKillStreak());
		p.getPacketSender().sendString(26712, "@or2@Wilderness Kills: @gre@"+p.getPlayerKillingAttributes().getPlayerKills());		
		p.getPacketSender().sendString(26713, "@or2@Wilderness Deaths: @gre@"+p.getPlayerKillingAttributes().getPlayerDeaths());
		p.getPacketSender().sendString(26714, "@or2@Arena Victories: @gre@"+p.getDueling().arenaStats[0]);
		p.getPacketSender().sendString(26715, "@or2@Arena Losses: @gre@"+p.getDueling().arenaStats[1]);
		*/
        return this;
    }

    private int prestigePoints;
    private int triviaPoints;
    private int slayerPoints;
    private int PCPoints;
    private int bosscaskets;
    private int supplycaskets;
    private int openedpresents;
    private int opened3rdage;
    private int openedholiday;
    private int totalbosskills;
    private int dungTokens;
    private int pkPoints;
    private double loyaltyPoints;
    private int achievementPoints;
    private int amountdonated;
    private int donatorpoints;

    public int getPrestigePoints() {
        return prestigePoints;
    }

    public void setPrestigePoints(int points, boolean add) {
        if (add)
            this.prestigePoints += points;
        else
            this.prestigePoints = points;
    }

    public int getSlayerPoints() {
        return slayerPoints;
    }

    public int getPCPoints() {
        return PCPoints;
    }

    public int getTriviaPoints() {
        return triviaPoints;
    }

    public void setTriviaPoints(int triviaPoints, boolean add) {
        if (add)
            this.triviaPoints += triviaPoints;
        else
            this.triviaPoints = triviaPoints;
    }

    public void incrementTriviaPoints(double amount) {
        this.triviaPoints += amount;
    }

    public void setSlayerPoints(int slayerPoints, boolean add) {
        if (add)
            this.slayerPoints += slayerPoints;
        else
            this.slayerPoints = slayerPoints;
    }

    public void setPCPoints(int PCPoints, boolean add) {
        if (add)
            this.PCPoints += PCPoints;
        else
            this.PCPoints = PCPoints;
    }

    public int getopenedpresents() {
        return this.openedpresents;
    }

    public void setopenedpresents(int openedpresents, boolean add) {
        if (add)
            this.openedpresents += openedpresents;
        else
            this.openedpresents = openedpresents;
    }

    public int getbosscaskets() {
        return this.bosscaskets;
    }

    public void setbosscaskets(int bosscaskets, boolean add) {
        if (add)
            this.bosscaskets += bosscaskets;
        else
            this.bosscaskets = bosscaskets;
    }

    public int getsupplycaskets() {
        return this.supplycaskets;
    }

    public void setsupplycaskets(int supplycaskets, boolean add) {
        if (add)
            this.supplycaskets += supplycaskets;
        else
            this.supplycaskets = supplycaskets;
    }

    public int getopened3rdage() {
        return this.opened3rdage;
    }

    public void setopened3rdage(int opened3rdage, boolean add) {
        if (add)
            this.opened3rdage += opened3rdage;
        else
            this.opened3rdage = opened3rdage;
    }

    public int getopenedholiday() {
        return this.openedholiday;
    }

    public void setopenedholiday(int openedholiday, boolean add) {
        if (add)
            this.openedholiday += openedholiday;
        else
            this.openedholiday = openedholiday;
    }

    public int getAmountDonated() {
        return this.amountdonated;
    }

    public void setAmountDonated(int amountdonated, int add) {
        this.amountdonated += add;
    }

    public int getdonatorpoints() {
        return this.donatorpoints;
    }

    public void setdonatorpoints(int donatorpoints, int add) {
        this.donatorpoints += add;
    }

    public int getLoyaltyPoints() {
        return (int) this.loyaltyPoints;
    }

    public void setLoyaltyPoints(int points, boolean add) {
        if (add)
            this.loyaltyPoints += points;
        else
            this.loyaltyPoints = points;
    }

    public void incrementLoyaltyPoints(double amount) {
        this.loyaltyPoints += amount;
    }

    public int getPkPoints() {
        return this.pkPoints;
    }

    public void setPkPoints(int points, boolean add) {
        if (add)
            this.pkPoints += points;
        else
            this.pkPoints = points;
    }

    public int getDungeoneeringTokens() {
        return dungTokens;
    }

    public void setDungeoneeringTokens(int dungTokens, boolean add) {
        if (add)
            this.dungTokens += dungTokens;
        else
            this.dungTokens = dungTokens;
    }

    public int gettotalbosskills() {
        return totalbosskills;
    }

    public void settotalbosskills(int points, boolean add) {
        if (add)
            this.totalbosskills += points;
        else
            this.totalbosskills = points;
    }

    public int getAchievementPoints() {
        return achievementPoints;
    }

    public void setAchievementPoints(int points, boolean add) {
        if (add)
            this.achievementPoints += points;
        else
            this.achievementPoints = points;
    }
}
