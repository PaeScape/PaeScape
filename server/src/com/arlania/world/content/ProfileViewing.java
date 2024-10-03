package com.arlania.world.content;

import com.arlania.GameServer;
import com.arlania.model.Item;
import com.arlania.model.Skill;
import com.arlania.model.container.impl.Bank;
import com.arlania.util.Misc;
import com.arlania.world.World;
import com.arlania.world.entity.impl.player.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Handles the Profile viewing
 *
 * @author Levi Patton
 * @ www.rune-server.org/members/auguryps
 */
public class ProfileViewing {

    /**
     * The saving directory
     */
    public static final String DIRECTORY = "./data/saves/profiles/";

    /**
     * All the profiles
     */
    public static ArrayList<Profile> profiles = new ArrayList<>();

    /**
     * The profile being viewed
     */
    private Profile viewing;

    /**
     * Rating a user
     *
     * @param c    the player
     * @param like whether liked or disliked
     */
    public static void rate(Player c, boolean like) {
        /**
         * No profile
         */
        if (c.getProfile().getViewing() == null) {
            return;
        }
        /**
         * The other player
         */
        Player other = World.getPlayerByName(c.getProfile().getViewing().getUsername());
        /**
         * The other profile
         */
        Profile p = getProfile(other.getUsername());
        /**
         * Profile doesn't exist
         */
        if (p == null) {
            return;
        }
        /**
         * The profile
         */
        Profile my = getProfile(c.getUsername());
        /**
         * Profile doesn't exist
         */
        if (my == null) {
            my = new Profile(c.getUsername(), 0, 0, 0);
            profiles.add(my);
            save(my);
        }
        /**
         * Checks if already rated
         */
        for (String s : my.getUsersRated()) {
            if (s == null) {
                continue;
            }
            if (s.equalsIgnoreCase(other.getUsername())) {
                c.sendMessage("You have already rated this user in the past.");
                return;
            }
        }
        /**
         * Rates the user
         */
        if (like) {
            int timeOnline = (c.timeOnline / 60) / 120;
            if (timeOnline < 5 && !c.getStaffRights().isStaff()) {
                c.sendMessage(
                        "You need 5 hours of gameplay to rate a user positively or donator rank");
                return;
            }
            p.setLikes(p.getLikes() + 1);
            c.getPA().sendFrame126("Likes: " + p.getLikes(), 36513);
            other.sendMessage("You have recieved a like from " + Misc.ucFirst(c.getUsername()));
        } else {
            if (!c.getStaffRights().isDonator() || !c.getStaffRights().isStaff()) {
                c.sendMessage("You need to be at least a donator plus to leave a negative rate.");
                return;
            }
            p.setDislikes(p.getDislikes() + 1);
            c.getPA().sendFrame126("Dislikes: " + p.getDislikes(), 36514);
            other.sendMessage("You have been recieved a dislike from " + Misc.ucFirst(c.getUsername()));
        }
        my.getUsersRated().add(other.getUsername());
        save(my);
        save(p);
    }

    /**
     * Viewing a profile
     *
     * @param c     the player
     * @param other the other player
     */
    public static void view(Player c, Player other) {
        for (Player o : World.getPlayers()) {
            if (o != null) {
                long gold = 0;
                for (Item item : o.getInventory().getItems()) {
                    if (item != null && item.getId() > 0 && item.tradeable()) {
                        gold += item.getDefinition().getValue();
                    }
                }
                for (Item item : o.getEquipment().getItems()) {
                    if (item != null && item.getId() > 0 && item.tradeable()) {
                        gold += item.getDefinition().getValue();
                    }
                }
                for (int i = 0; i < 9; i++) {
                    for (Item item : o.getBank(i).getItems()) {
                        if (item != null && item.getId() > 0 && item.tradeable()) {
                            gold += item.getDefinition().getValue();
                        }
                    }
                }
                gold += o.getMoneyInPouch();
                /**
                 * The other profile
                 */
                Profile p = getProfile(other.getUsername());
                /**
                 * Profile doesn't exist
                 */
                if (p == null) {
                    p = new Profile(other.getUsername(), 0, 0, 0);
                    profiles.add(p);
                    save(p);
                }
                p.setViews(p.getViews() + 1);
                /**
                 * Sends the information
                 */
                c.getPA().sendFrame126("Viewing: " + Misc.ucFirst(other.getUsername()), 36512);
                c.getPA().sendFrame126("Likes: " + p.getLikes(), 36513);
                c.getPA().sendFrame126("Dislikes: " + p.getDislikes(), 36514);
                c.getPA().sendFrame126("Views: " + p.getViews(), 36515);


                c.getPA().sendFrame126("Combat: " + other.getSkillManager().getCombatLevel(), 36519);
                c.getPA().sendFrame126("Kills: " + other.getPlayerKillingAttributes().getPlayerKills(), 36521);
                c.getPA().sendFrame126("Deaths: " + other.getPlayerKillingAttributes().getPlayerDeaths(), 36522);
                int d = other.getPlayerKillingAttributes().getPlayerDeaths();
                int k = other.getPlayerKillingAttributes().getPlayerKills();
                if (d < 1) {
                    d = 1;
                }
                if (k < 1) {
                    k = 1;
                }
                double kc = (d / k);
                c.getPA().sendFrame126("KDR: " + kc, 36523);
                c.getPA().sendFrame126("Killstreak: " + other.getPlayerKillingAttributes().getPlayerKillStreak(), 36524);
                c.getPA().sendFrame126("PvP Points: " + other.getPointsHandler().getPkPoints(), 36525);
                c.getPA().sendFrame126("Total: " + other.getSkillManager().getTotalLevel(), 36520);

                c.getPA().sendInterface(36500);

                for (int i = 0; i < 7; i++) {
                    c.getPA().sendFrame126(Skill.values()[i].getFormatName() + ": "
                            + other.getSkillManager().getCurrentLevel(Skill.values()[i]), 36531 + i);
                }
                c.getPA().sendFrame126("Summoning: " + other.getSkillManager().getCurrentLevel(Skill.SUMMONING), 36539);
                c.getPA().sendFrame126("Slayer: " + other.getSkillManager().getCurrentLevel(Skill.SLAYER), 36538);
                c.getPA().sendFrame126("Dungeoneering: " + other.getSkillManager().getCurrentLevel(Skill.DUNGEONEERING), 36540);

                //c.getPA().sendFrame126("Vote points: " + other.getPointsHandler().getVotingPoints(), 36526);
                c.getPA().sendFrame126("Total Boss Kills: " + other.getPointsHandler().gettotalbosskills(), 36526);
                c.getPA().sendFrame126("Donator points: " + other.getPointsHandler().getdonatorpoints(), 36527);
                c.getPA().sendFrame126("Donated: $" + other.getPointsHandler().getAmountDonated(), 36528);
                c.getPA().sendFrame126("Net worth: \\n" + Misc.setupMoney(getAccountWorth(other)), 36529);
                c.getProfile().setViewing(p);
                c.getPA().sendFrame126("", 36530);
            }
        }
    }

    private static long getAccountWorth(Player other) {
        int amount = 0;
        for (Item item : other.getInventory().getValidItemsArray()) {
            if (item == null) {
                continue;
            }
            amount += item.getDefinition().getValue();
        }
        for (Item item : other.getEquipment().getValidItemsArray()) {
            if (item == null) {
                continue;
            }
            amount += item.getDefinition().getValue();
        }
        for (Bank bank : other.getBanks()) {
            if (bank == null) {
                continue;
            }
            for (Item item : bank.getValidItemsArray()) {
                amount += item.getDefinition().getValue();
            }
        }
        return amount;
    }

    /**
     * Gets a profile by username
     *
     * @param username the username
     * @return the profile
     */
    public static Profile getProfile(String username) {
        for (Profile p : profiles) {
            if (p == null) {
                continue;
            }
            if (p.getUsername().equalsIgnoreCase(username)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Loads the profiles
     */
    public static void init() {
        long dick = System.currentTimeMillis();
        File directory = new File(DIRECTORY);
        try {
            for (File files : directory.listFiles()) {
                DataInputStream input = new DataInputStream(new FileInputStream(DIRECTORY + files.getName()));
                String username = input.readUTF();
                int likes = input.readInt();
                int dislikes = input.readInt();
                int views = input.readInt();
                int usersRated = input.readInt();

                Profile p = new Profile(username, likes, dislikes, views);

                for (int i = 0; i < usersRated; i++) {
                    p.getUsersRated().add(input.readUTF());
                }

                profiles.add(p);

                input.close();
            }
            System.out.println("Loaded " + profiles.size() + " in " + (System.currentTimeMillis() - dick) + " ms");
        } catch (FileNotFoundException e) {
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
        } catch (IOException e) {
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
        }
    }

    /**
     * Saves the profile
     *
     * @param profile the profile
     */
    public static void save(Profile profile) {
        try {
            File f = new File(DIRECTORY + profile.getUsername());
            if (f.exists()) {
                f.delete();
            }
            DataOutputStream output = new DataOutputStream(new FileOutputStream(DIRECTORY + profile.getUsername()));
            output.writeUTF(profile.getUsername());
            output.writeInt(profile.getLikes());
            output.writeInt(profile.getDislikes());
            output.writeInt(profile.getViews());
            output.writeInt(profile.getUsersRated().size());
            for (int i = 0; i < profile.getUsersRated().size(); i++) {
                output.writeUTF(profile.getUsersRated().get(i));
            }
            output.flush();
            output.close();
        } catch (FileNotFoundException e) {
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
        } catch (IOException e) {
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
        }
    }

    /**
     * Gets the viewing
     *
     * @return the viewing
     */
    public Profile getViewing() {
        return viewing;
    }

    /**
     * Sets the viewing
     *
     * @param viewing the viewing
     */
    public void setViewing(Profile viewing) {
        this.viewing = viewing;
    }

    /**
     * Represents a profile
     */
    public static class Profile {
        /**
         * The username
         */
        private String username;

        /**
         * The likes
         */
        private int likes;

        /**
         * The dislikes
         */
        private int dislikes;

        /**
         * The views
         */
        private int views;

        /**
         * The rated users
         */
        private ArrayList<String> usersRated;

        /**
         * Represents a new profile
         *
         * @param username the username
         * @param likes    the likes
         * @param dislikes the dislikes
         * @param views    the views
         */
        public Profile(String username, int likes, int dislikes, int views) {
            this.setUsername(username);
            this.setLikes(likes);
            this.setDislikes(dislikes);
            this.setViews(views);
            this.setUsersRated(new ArrayList<String>());
        }

        /**
         * Gets the likes
         *
         * @return the likes
         */
        public int getLikes() {
            return likes;
        }

        /**
         * Sets the likes
         *
         * @param likes the likes
         */
        public void setLikes(int likes) {
            this.likes = likes;
        }

        /**
         * Gets the dislikes
         *
         * @return the dislikes
         */
        public int getDislikes() {
            return dislikes;
        }

        /**
         * Sets the dislikes
         *
         * @param dislikes the dislikes
         */
        public void setDislikes(int dislikes) {
            this.dislikes = dislikes;
        }

        /**
         * Gets the views
         *
         * @return the views
         */
        public int getViews() {
            return views;
        }

        /**
         * Sets the views
         *
         * @param views the views
         */
        public void setViews(int views) {
            this.views = views;
        }

        /**
         * Gets the username
         *
         * @return the username
         */
        public String getUsername() {
            return username;
        }

        /**
         * Sets the username
         *
         * @param username the username
         */
        public void setUsername(String username) {
            this.username = username;
        }

        /**
         * Gets the usersRated
         *
         * @return the usersRated
         */
        public ArrayList<String> getUsersRated() {
            return usersRated;
        }

        /**
         * Sets the usersRated
         *
         * @param usersRated the usersRated
         */
        public void setUsersRated(ArrayList<String> usersRated) {
            this.usersRated = usersRated;
        }
    }
}
