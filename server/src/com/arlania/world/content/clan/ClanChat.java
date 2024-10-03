package com.arlania.world.content.clan;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.util.Stopwatch;
import com.arlania.world.World;
import com.arlania.world.entity.impl.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * An instance of a clanchat channel, holding all fields.
 *
 * @author Gabriel Hannason
 */
public class ClanChat {

    public ClanChat(Player owner, String name, int index) {
        this.owner = owner;
        this.name = name;
        this.index = index;
        this.ownerName = owner.getUsername();
    }

    public ClanChat(String ownerName, String name, int index) {
        this.owner = World.getPlayerByName(ownerName);
        this.ownerName = ownerName;
        this.name = name;
        this.index = index;
    }

    private String name;
    private Player owner;
    private final String ownerName;
    private final int index;
    private boolean lootShare;
    private final Stopwatch lastAction = new Stopwatch();

    private final ClanChatRank[] rankRequirement = new ClanChatRank[3];
    private final CopyOnWriteArrayList<Player> members = new CopyOnWriteArrayList<Player>();
    private final CopyOnWriteArrayList<String> bannedNames = new CopyOnWriteArrayList<String>();
    private final Map<String, ClanChatRank> rankedNames = new HashMap<String, ClanChatRank>();
    private int wins;

    public Player getOwner() {
        return owner;
    }

    public ClanChat setOwner(Player owner) {
        this.owner = owner;
        return this;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public ClanChat setName(String name) {
        this.name = name;
        return this;
    }

    public boolean getLootShare() {
        return lootShare;
    }

    public void setLootShare(boolean lootShare) {
        this.lootShare = lootShare;
    }

    public Stopwatch getLastAction() {
        return lastAction;
    }

    public ClanChat addMember(Player member) {
        members.add(member);
        return this;
    }

    public ClanChat removeMember(String name) {
        for (int i = 0; i < members.size(); i++) {
            Player member = members.get(i);
            if (member == null)
                continue;
            if (member.getUsername().equals(name)) {
                members.remove(i);
                break;
            }
        }
        return this;
    }

    public ClanChatRank getRank(Player player) {
        return rankedNames.get(player.getUsername());
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void addWin() {
        wins++;
    }

    public ClanChat giveRank(Player player, ClanChatRank rank) {
        rankedNames.put(player.getUsername(), rank);
        return this;
    }

    public CopyOnWriteArrayList<Player> getMembers() {
        return members;
    }

    public Map<String, ClanChatRank> getRankedNames() {
        return rankedNames;
    }

    public CopyOnWriteArrayList<String> getBannedNames() {
        return bannedNames;
    }

    public void addBannedName(String name) {
        if (!bannedNames.contains(name)) {
            bannedNames.add(name);
            TaskManager.submit(new Task(1) {
                int tick = 0;

                @Override
                public void execute() {
                    if (tick == 2000) { // 20 minutes
                        stop();
                        return;
                    }
                    tick++;
                }

                @Override
                public void stop() {
                    setEventRunning(false);
                    bannedNames.remove(name);
                }
            });
        }
    }

    public boolean isBanned(String name) {
        return bannedNames.contains(name);
    }

    public ClanChatRank[] getRankRequirement() {
        return rankRequirement;
    }

    public ClanChat setRankRequirements(int index, ClanChatRank rankRequirement) {
        this.rankRequirement[index] = rankRequirement;
        return this;
    }

    public static final int RANK_REQUIRED_TO_ENTER = 0, RANK_REQUIRED_TO_KICK = 1, RANK_REQUIRED_TO_TALK = 2;
}