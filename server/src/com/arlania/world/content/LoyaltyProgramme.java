package com.arlania.world.content;

import com.arlania.model.Flag;
import com.arlania.model.Skill;
import com.arlania.util.Misc;
import com.arlania.world.entity.impl.player.Player;

public class LoyaltyProgramme {

    public enum LoyaltyTitles {

        NONE(0, 0, 0) {
            @Override
            boolean canBuy(Player p, boolean sendMessage) {
                return true;
            }
        },
        REAPER(-1, 43011, 43008) {
            @Override
            boolean canBuy(Player p, boolean sendMessage) {
                if (!p.getUnlockedLoyaltyTitles()[1]) {
                    if (sendMessage)
                        p.getPacketSender().sendMessage("To unlock this title, you must kill another player.");
                    return false;
                }
                return true;
            }
        },
        ASSASSIN(-1, 43015, 43012) {
            @Override
            boolean canBuy(Player p, boolean sendMessage) {
                if (!p.getUnlockedLoyaltyTitles()[2]) {
                    if (sendMessage)
                        p.getPacketSender().sendMessage("To unlock this title, you must kill 20 other players.");
                    return false;
                }
                return true;
            }
        },
        CORRUPT(-1, 43019, 43016) {
            @Override
            boolean canBuy(Player p, boolean sendMessage) {
                if (!p.getUnlockedLoyaltyTitles()[3]) {
                    if (sendMessage)
                        p.getPacketSender().sendMessage("To unlock this title, you must kill 50 other players.");
                    return false;
                }
                return true;
            }
        },
        WARCHIEF(-1, 43023, 43020) {
            @Override
            boolean canBuy(Player p, boolean sendMessage) {
                if (!p.getUnlockedLoyaltyTitles()[4]) {
                    if (sendMessage)
                        p.getPacketSender().sendMessage("To unlock this title, you must kill 15 players without dying.");
                    return false;
                }
                return true;
            }
        },
        SKILLER(-1, 43027, 43024) {
            @Override
            boolean canBuy(Player p, boolean sendMessage) {
                if (!p.getUnlockedLoyaltyTitles()[5]) {
                    for (int i = 7; i < Skill.values().length; i++) {
                        if (i == 21 || i == 24 || i == 23 || i == 18)
                            continue;
                        if (p.getSkillManager().getMaxLevel(i) < 99) {
                            if (sendMessage)
                                p.getPacketSender().sendMessage("You must be at least level 99 in every non-combat skill for this title.");
                            return false;
                        }
                    }
                }
                unlock(p, this);
                return true;
            }
        },
        SUPREME(-1, 43031, 43028) {
            @Override
            boolean canBuy(Player p, boolean sendMessage) {
                if (!p.getUnlockedLoyaltyTitles()[6]) {
                    for (int i = 0; i <= 6; i++) {
                        if (p.getSkillManager().getMaxLevel(i) < (i == 3 || i == 5 ? 990 : 99)) {
                            if (sendMessage)
                                p.getPacketSender().sendMessage("You must be at least level 99 in every combat skill for this title.");
                            return false;
                        }
                    }
                }
                unlock(p, this);
                return true;
            }
        },
        MAXED(-1, 43035, 43032) {
            @Override
            boolean canBuy(Player p, boolean sendMessage) {
                if (!p.getUnlockedLoyaltyTitles()[7]) {
                    for (int i = 0; i < Skill.values().length; i++) {
//						if(i == 21)
//							continue;
                        if (p.getSkillManager().getMaxLevel(i) < (i == 3 || i == 5 ? 990 : 99)) {
                            if (sendMessage)
                                p.getPacketSender().sendMessage("You must be at least level 99 in every skill for this title.");
                            return false;
                        }
                    }
                }
                unlock(p, this);
                return true;
            }
        },
        GODSLAYER(-1, 43039, 43036) {
            @Override
            boolean canBuy(Player p, boolean sendMessage) {
                if (!p.getUnlockedLoyaltyTitles()[8]) {
                    if (p.getPointsHandler().gettotalbosskills() < 1000) {
                        if (sendMessage)
                            p.getPacketSender().sendMessage("To unlock this title, you must have at least 1,000 Boss Kills.");
                        return false;
                    }
                }
                unlock(p, this);
                return true;
            }
        },
        ADMIRAL(-1, 43043, 43040) {
            @Override
            boolean canBuy(Player p, boolean sendMessage) {
                if (!p.getUnlockedLoyaltyTitles()[9]) {
                    if (p.getPointsHandler().getLoyaltyPoints() < 100000) {
                        if (sendMessage)
                            p.getPacketSender().sendMessage("To unlock this title, you must gain 100,000 Loyalty Points simultaneously.");
                        return false;
                    }
                }
                unlock(p, this);
                return true;
            }
        },
        DIVINE(-1, 43047, 43044) {
            @Override
            boolean canBuy(Player p, boolean sendMessage) {
                if (!p.getUnlockedLoyaltyTitles()[10]) {
                    if (p.getTotalLoyaltyPointsEarned() < 500000) {
                        if (sendMessage)
                            p.getPacketSender().sendMessage("To unlock this title, you must have earned 500,000 Loyalty Points in total.");
                        return false;
                    }
                }
                unlock(p, this);
                return true;
            }
        },
        DICER(-1, 43051, 43048) {
            @Override
            boolean canBuy(Player p, boolean sendMessage) {
                if (!p.getUnlockedLoyaltyTitles()[11]) {
                    if (!p.getInventory().contains(15084)) {
                        if (sendMessage)
                            p.getPacketSender().sendMessage("To unlock this title, you must have a Dice in your inventory.");
                        return false;
                    }
                }
                unlock(p, this);
                return true;
            }
        },


        KING(25000, 43055, 43052) {
            @Override
            boolean canBuy(Player p, boolean sendMessage) {
                return true;
            }
        },
        QUEEN(25000, 43059, 43056) {
            @Override
            boolean canBuy(Player p, boolean sendMessage) {
                return true;
            }
        },
        PRINCE(20000, 43063, 43060) {
            @Override
            boolean canBuy(Player p, boolean sendMessage) {
                return true;
            }
        },
        PRINCESS(15000, 43067, 43064) {
            @Override
            boolean canBuy(Player p, boolean sendMessage) {
                return true;
            }
        },
        LORD(15000, 43071, 43068) {
            @Override
            boolean canBuy(Player p, boolean sendMessage) {
                return true;
            }
        },
        BARON(10000, 43075, 43072) {
            @Override
            boolean canBuy(Player p, boolean sendMessage) {
                return true;
            }
        },
        DUKE(10000, 43079, 43076) {
            @Override
            boolean canBuy(Player p, boolean sendMessage) {
                return true;
            }
        },
        SIR(8000, 43083, 43080) {
            @Override
            boolean canBuy(Player p, boolean sendMessage) {
                return true;
            }
        },
        LADY(8000, 43087, 43084) {
            @Override
            boolean canBuy(Player p, boolean sendMessage) {
                return true;
            }
        },
        EVIL(5000, 43091, 43088) {
            @Override
            boolean canBuy(Player p, boolean sendMessage) {
                return true;
            }
        },
        GOOD(5000, 43095, 43092) {
            @Override
            boolean canBuy(Player p, boolean sendMessage) {
                return true;
            }
        };

        LoyaltyTitles(int cost, int frame, int button) {
            this.cost = cost;
            this.frame = frame;
            this.button = button;
        }

        private final int cost;
        private final int frame;
        private final int button;

        abstract boolean canBuy(Player p, boolean sendMessage);

        public static LoyaltyTitles getTitle(int button) {
            for (LoyaltyTitles t : LoyaltyTitles.values()) {
                if (t.button == button)
                    return t;
            }
            return null;
        }
    }

    public static void unlock(Player player, LoyaltyTitles title) {
        if (player.getUnlockedLoyaltyTitles()[title.ordinal()])
            return;
        player.setUnlockedLoyaltyTitle(title.ordinal());
        player.getPacketSender().sendMessage("You've unlocked the " + Misc.formatText(title.name().toLowerCase()) + " loyalty title!");
    }

    public static boolean handleButton(Player player, int button) {
        LoyaltyTitles title = LoyaltyTitles.getTitle(button);
        if (title != null) {
            if (title.canBuy(player, true)) {

                if (player.getPointsHandler().getLoyaltyPoints() >= title.cost) {

                    player.setTitle("@or2@" + title);

                    if (title.cost > -1) {
                        player.getPointsHandler().setLoyaltyPoints(-title.cost, true);
                        player.getPointsHandler().refreshPanel();
                    }
                    player.getPacketSender().sendMessage("You've changed your title.");
                    player.getUpdateFlag().flag(Flag.APPEARANCE);
                    open(player);
                } else {
                    player.getPacketSender().sendMessage("You need at least " + title.cost + " Loyalty Points to buy this title.");
                }
            }
            return true;
        }
        return false;
    }

    public static void open(Player player) {
        for (LoyaltyTitles title : LoyaltyTitles.values()) {
            if (title.cost > 0) {
                player.getPacketSender().sendString(title.frame, title.cost + " LP");
            } else {
                if (title.canBuy(player, false)) {
                    player.getPacketSender().sendString(title.frame, "@gre@Unlocked");
                } else {
                    player.getPacketSender().sendString(title.frame, "  @red@Locked");
                }
            }
        }
        player.getPacketSender().sendString(43120, "Your Loyalty Points: " + player.getPointsHandler().getLoyaltyPoints()).sendInterface(43000);
    }

    public static void reset(Player player) {
        player.setLoyaltyTitle(LoyaltyTitles.NONE);
        player.getUpdateFlag().flag(Flag.APPEARANCE);
    }

    public static void incrementPoints(Player player) {
        double pts = 50;
        if (player.getStaffRights().getStaffRank() >= 6)
            pts = 200;
        else if (player.getStaffRights().getStaffRank() >= 4)
            pts = 150;
        else if (player.getStaffRights().getStaffRank() >= 2)
            pts = 100;


        player.getPointsHandler().incrementLoyaltyPoints(pts);
        player.incrementTotalLoyaltyPointsEarned(pts);

        int totalPoints = player.getPointsHandler().getLoyaltyPoints();
        if (totalPoints >= 100000) {
            unlock(player, LoyaltyTitles.ADMIRAL);
        }
		
		/*if(player.getInterfaceId() == 43000) {
			player.getPacketSender().sendString(43120, "Your Loyalty Points: "+totalPoints);
		}
		player.getPacketSender().sendString(39178, "@or2@Loyalty Points: @yel@"+totalPoints);
		player.getPacketSender().sendString(39180, PlayerPanel.LINE_START + "@or1@Loyalty Points:@yel@ "+totalPoints);*/

        if (player.getTotalLoyaltyPointsEarned() >= 500000) {
            unlock(player, LoyaltyTitles.DIVINE);
        }
    }
}