package com.arlania.world.content.combat.pvp;

import com.arlania.model.GroundItem;
import com.arlania.model.Item;
import com.arlania.model.Locations.Location;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.Artifacts;
import com.arlania.world.content.LoyaltyProgramme;
import com.arlania.world.content.LoyaltyProgramme.LoyaltyTitles;
import com.arlania.world.content.PlayerPanel;
import com.arlania.world.entity.impl.GroundItemManager;
import com.arlania.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerKillingAttributes {

    private final Player player;
    private Player target;
    private int playerKills;
    private int playerKillStreak;
    private int playerDeaths;
    private int targetPercentage;
    private long lastPercentageIncrease;
    private int safeTimer;

    Location loc;

    private final int WAIT_LIMIT = 2;
    private List<String> killedPlayers = new ArrayList<String>();

    public PlayerKillingAttributes(Player player) {
        this.player = player;
    }



    public void add(Player other) {
        if (other.getAppearance().getBountyHunterSkull() >= 0)
            other.getAppearance().setBountyHunterSkull(-1);

        boolean target = player.getPlayerKillingAttributes().getTarget() != null && player.getPlayerKillingAttributes().getTarget().getIndex() == other.getIndex() || other.getPlayerKillingAttributes().getTarget() != null && other.getPlayerKillingAttributes().getTarget().getIndex() == player.getIndex();
        if (target)
            killedPlayers.clear();

        if (killedPlayers.size() >= WAIT_LIMIT) {
            killedPlayers.clear();
            handleReward(other, target);
        } else {
            if (!killedPlayers.contains(other.getUsername()))
                handleReward(other, target);
            else
                player.getPacketSender().sendMessage("You were not given points because you have recently defeated " + other.getUsername() + ".");
        }

        int randloot = RandomUtility.inclusiveRandom(1, 100);
        int loot = 995;

        if (randloot >= 36) {
            loot = 14883;
        } else if (randloot >= 28) {
            loot = 14882;
        } else if (randloot >= 21) {
            loot = 14881;
        } else if (randloot >= 15) {
            loot = 14880;
        } else if (randloot >= 10) {
            loot = 14879;
        } else if (randloot >= 6) {
            loot = 14878;
        } else if (randloot >= 3) {
            loot = 14877;
        } else if (randloot >= 1) {
            loot = 14876;
        }

        Item targetLoot = new Item(loot, 1);

        if (target) {
            GroundItemManager.spawnGroundItem(player, new GroundItem(targetLoot, player.getPosition(), player.getUsername(), false, 150, false, 200));
            BountyHunter.resetTargets(player, other, true, "You have defeated your target!");
        }
    }

    private void handleReward(Player o, boolean targetKilled) {
        if (!player.getHostAddress().equalsIgnoreCase(o.getHostAddress()) && player.getLocation() == Location.WILDERNESS) {
            if (!killedPlayers.contains(o.getUsername()))
                killedPlayers.add(o.getUsername());
            player.getPacketSender().sendMessage(getRandomKillMessage(o.getUsername()));
            player.WildyPoints += 5;
            this.playerKills += 1;
            this.playerKillStreak += 1;
            player.getPacketSender().sendMessage("You've received 5 Wildy Points.");
            player.getPacketSender().sendMessage("You've now have " + player.WildyPoints + " Wildy Points");
            Artifacts.handleDrops(player, o, targetKilled);
            if (player.getAppearance().getBountyHunterSkull() < 4)
                player.getAppearance().setBountyHunterSkull(player.getAppearance().getBountyHunterSkull() + 1);
            PlayerPanel.refreshPanel(player);

            /** ACHIEVEMENTS AND LOYALTY TITLES **/
            LoyaltyProgramme.unlock(player, LoyaltyTitles.REAPER);
            if (this.playerKills >= 20) {
                LoyaltyProgramme.unlock(player, LoyaltyTitles.ASSASSIN);
            }
            if (this.playerKills >= 50) {
                LoyaltyProgramme.unlock(player, LoyaltyTitles.CORRUPT);
            }

            if (this.playerKillStreak == 3) {
                World.sendMessage("pvp", "@blu@[Killstreak]@bla@ " + player.getUsername() + " is on a kill streak of 3! Kill them to end their streak!");
            }
            if (this.playerKillStreak == 5) {
                World.sendMessage("pvp", "@blu@[Killstreak]@bla@ " + player.getUsername() + " is on a kill streak of 5! Kill them to end their streak!");
            }
            if (this.playerKillStreak == 7) {
                World.sendMessage("pvp", "@blu@[Killstreak]@bla@ " + player.getUsername() + " is on a kill streak of 7! Kill them to end their streak!");
            }
            if (this.playerKillStreak == 10) {
                World.sendMessage("pvp", "@blu@[Killstreak]@bla@ " + player.getUsername() + " is on a kill streak of 10! Kill them to end their streak!");
            }
            if (this.playerKillStreak == 15) {
                World.sendMessage("pvp", "@blu@[Killstreak]@bla@ " + player.getUsername() + " is on a kill streak of 15! Kill them to end their streak!");
            }
            if (this.playerKillStreak == 20) {
                World.sendMessage("pvp", "@blu@[Killstreak]@bla@ " + player.getUsername() + " is on a kill streak of 20! Kill them to end their streak!");
            }
            if (this.playerKillStreak == 25) {
                World.sendMessage("pvp", "@blu@[Killstreak]@bla@ " + player.getUsername() + " is on a kill streak of 25! Kill them to end their streak!");
            }
            if (this.playerKillStreak == 30) {
                World.sendMessage("pvp", "@blu@[Killstreak]@bla@ " + player.getUsername() + " is on a kill streak of 30! Kill them to end their streak!");
            }
            if (this.playerKillStreak == 40) {
                World.sendMessage("pvp", "@blu@[Killstreak]@bla@ " + player.getUsername() + " is on a kill streak of 40! Kill them to end their streak!");
            }
            if (this.playerKillStreak == 50) {
                World.sendMessage("pvp", "@blu@[Killstreak]@bla@ " + player.getUsername() + " is on a kill streak of 50! Kill them to end their streak!");
            }
            if (this.playerKillStreak == 60) {
                World.sendMessage("pvp", "@blu@[Killstreak]@bla@ " + player.getUsername() + " is on a kill streak of 60! Kill them to end their streak!");
            }
            if (this.playerKillStreak == 70) {
                World.sendMessage("pvp", "@blu@[Killstreak]@bla@ " + player.getUsername() + " is on a kill streak of 70! Kill them to end their streak!");
            }
            if (this.playerKillStreak == 75) {
                World.sendMessage("pvp", "@blu@[Killstreak]@bla@ " + player.getUsername() + " is on a kill streak of 75! Kill them to end their streak!");
            }
            if (this.playerKillStreak == 80) {
                World.sendMessage("pvp", "@blu@[Killstreak]@bla@ " + player.getUsername() + " is on a kill streak of 80! Kill them to end their streak!");
            }
            if (this.playerKillStreak == 90) {
                World.sendMessage("pvp", "@blu@[Killstreak]@bla@ " + player.getUsername() + " is on a kill streak of 90! Kill them to end their streak!");
            }
            if (this.playerKillStreak == 100) {
                World.sendMessage("pvp", "@blu@[Killstreak]@bla@ " + player.getUsername() + " is on a kill streak of 100! Kill them to end their streak!");
            }
            if (this.playerKillStreak == 125) {
                World.sendMessage("pvp", "@blu@[Killstreak]@bla@ " + player.getUsername() + " is on a kill streak of 125! Kill them to end their streak!");
            }
            if (this.playerKillStreak == 150) {
                World.sendMessage("pvp", "@blu@[Killstreak]@bla@ " + player.getUsername() + " is on a kill streak of 150! Kill them to end their streak!");
            }
            if (this.playerKillStreak == 175) {
                World.sendMessage("pvp", "@blu@[Killstreak]@bla@ " + player.getUsername() + " is on a kill streak of 175! Kill them to end their streak!");
            }
            if (this.playerKillStreak == 200) {
                World.sendMessage("pvp", "@blu@[Killstreak]@bla@ " + player.getUsername() + " is on a kill streak of 200! Kill them to end their streak!");
            }
            if (this.playerKillStreak == 250) {
                World.sendMessage("pvp", "@blu@[Killstreak]@bla@ " + player.getUsername() + " is on a kill streak of 250! Kill them to end their streak!");
            }
            if (this.playerKillStreak >= 15) {
                LoyaltyProgramme.unlock(player, LoyaltyTitles.WARCHIEF);
            }
        }
    }

    public List<String> getKilledPlayers() {
        return killedPlayers;
    }

    public void setKilledPlayers(List<String> list) {
        killedPlayers = list;
    }

    /**
     * Gets a random message after killing a player
     *
     * @param killedPlayer The player that was killed
     */
    public static String getRandomKillMessage(String killedPlayer) {
        int deathMsgs = RandomUtility.inclusiveRandom(8);
        switch (deathMsgs) {
            case 0:
                return "With a crushing blow, you defeat " + killedPlayer + ".";
            case 1:
                return "It's humiliating defeat for " + killedPlayer + ".";
            case 2:
                return killedPlayer + " didn't stand a chance against you.";
            case 3:
                return "You've defeated " + killedPlayer + ".";
            case 4:
                return killedPlayer + " regrets the day they met you in combat.";
            case 5:
                return "It's all over for " + killedPlayer + ".";
            case 6:
                return killedPlayer + " falls before you might.";
            case 7:
                return "Can anyone defeat you? Certainly not " + killedPlayer + ".";
            case 8:
                return "You were clearly a better fighter than " + killedPlayer + ".";
        }
        return null;
    }

    public int getPlayerKills() {
        return playerKills;
    }

    public void setPlayerKills(int playerKills) {
        this.playerKills = playerKills;
    }

    public int getPlayerKillStreak() {
        return playerKillStreak;
    }

    public void setPlayerKillStreak(int playerKillStreak) {
        this.playerKillStreak = playerKillStreak;
    }

    public int getPlayerDeaths() {
        return playerDeaths;
    }


    public void setPlayerDeaths(int playerDeaths) {
        this.playerDeaths = playerDeaths;
    }

    public Player getTarget() {
        return target;
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public int getTargetPercentage() {
        return targetPercentage;
    }

    public void setTargetPercentage(int targetPercentage) {
        this.targetPercentage = targetPercentage;
    }

    public long getLastTargetPercentageIncrease() {
        return lastPercentageIncrease;
    }

    public void setLastTargetPercentageIncrease(long lastPercentageIncrease) {
        this.lastPercentageIncrease = lastPercentageIncrease;
    }

    public int getSafeTimer() {
        return safeTimer;
    }

    public void setSafeTimer(int safeTimer) {
        this.safeTimer = safeTimer;
    }
}

