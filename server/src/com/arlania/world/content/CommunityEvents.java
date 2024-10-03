package com.arlania.world.content;

import com.arlania.GameLoader;
import com.arlania.world.entity.impl.player.Player;

public class CommunityEvents {

    public static void checkEvent(Player player) {

        int week = GameLoader.getWeekOfYear();

        if(player.isDev())
            player.getPacketSender().sendMessage("Current week of the year: " + week);

        if(week % 4 == 0) {
            if(player.communityEvents2024[week] == 0) {
                if(player.getInventory().getFreeSlots() > 0) {
                    player.getPacketSender().sendMessage("This week's event is a free BINGO card!");
                    player.getInventory().add(2795, 1);
                    player.communityEvents2024[week] = 1;
                } else {
                    player.getPacketSender().sendMessage("You need a free inventory space to claim this week's free event!");
                }

            } else {
                player.getPacketSender().sendMessage("You've already claimed this week's event!");
            }
        } else if(week % 4 == 1) {
            if(player.communityEvents2024[week] == 0) {
                if(player.getInventory().getFreeSlots() > 0) {
                    player.getPacketSender().sendMessage("This week's event is a free Battle Pass!");
                    player.getInventory().add(6758, 1);
                    player.communityEvents2024[week] = 1;
                } else {
                    player.getPacketSender().sendMessage("You need a free inventory space to claim this week's free event!");
                }

            } else {
                player.getPacketSender().sendMessage("You've already claimed this week's event!");
            }

        } else if(week % 4 == 2) {
            if(player.communityEvents2024[week] == 0) {
                if(player.getInventory().getFreeSlots() > 0) {
                    player.getPacketSender().sendMessage("This week's event is a free Event Pass!");
                    player.getInventory().add(6769, 1);
                    player.communityEvents2024[week] = 1;
                } else {
                    player.getPacketSender().sendMessage("You need a free inventory space to claim this week's free event!");
                }

            } else {
                player.getPacketSender().sendMessage("You've already claimed this week's event!");
            }

        } else if(week % 4 == 3) {
            if(player.communityEvents2024[week] == 0) {
                if(player.getInventory().getFreeSlots() > 0) {
                    player.getPacketSender().sendMessage("This week's event is a free Personal Max Hit!");
                    player.getInventory().add(4032, 1);
                    player.communityEvents2024[week] = 1;
                } else {
                    player.getPacketSender().sendMessage("You need a free inventory space to claim this week's free event!");
                }

            } else {
                player.getPacketSender().sendMessage("You've already claimed this week's event!");
            }

        }

    }



}



/*

    switch (week) {

            case 1:
                player.getPacketSender().sendMessage("The current Community event is the Meme Event on Discord!");
                break;
            case 2:
            case 3:
            case 4:
                if(player.communityEvents2024[2] == 0 && player.communityEvents2024[3] == 0) {
                    if(player.getInventory().getFreeSlots() > 0) {
                        player.getPacketSender().sendMessage("This week's event is a free BINGO card!");
                        player.getInventory().add(2795, 1);
                        player.communityEvents2024[0] = 1;
                        player.communityEvents2024[1] = 1;
                        player.communityEvents2024[2] = 1;
                        player.communityEvents2024[3] = 1;
                    } else {
                        player.getPacketSender().sendMessage("You need a free inventory space to claim this week's free event!");
                    }

                } else {
                    player.getPacketSender().sendMessage("You've already claimed this week's event!");
                }
                break;
            case 5:
                if(player.communityEvents2024[4] == 0) {
                    if(player.getInventory().getFreeSlots() > 0) {
                        player.getPacketSender().sendMessage("This week's event is a free Battle Pass!");
                        player.getInventory().add(6758, 1);
                        player.communityEvents2024[4] = 1;
                    } else {
                        player.getPacketSender().sendMessage("You need a free inventory space to claim this week's free event!");
                    }

                } else {
                    player.getPacketSender().sendMessage("You've already claimed this week's event!");
                }
                break;
            case 6:
            case 7:
                player.getPacketSender().sendMessage("The current Community event is the Design-A-Perk Event on Discord!");
                break;
            case 8:
                if(player.communityEvents2024[7] == 0) {
                    if(player.getInventory().getFreeSlots() > 0) {
                        player.getPacketSender().sendMessage("This week's event is a free Personal Max Hit Event!");
                        player.getInventory().add(4032, 1);
                        player.communityEvents2024[7] = 1;
                    } else {
                        player.getPacketSender().sendMessage("You need a free inventory space to claim this week's free event!");
                    }

                } else {
                    player.getPacketSender().sendMessage("You've already claimed this week's event!");
                }
                break;
            case 9:
                if(player.communityEvents2024[8] == 0) {
                    if(player.getInventory().getFreeSlots() > 0) {
                        player.getPacketSender().sendMessage("This week's event is a free Event Pass!");
                        player.getInventory().add(6769, 1);
                        player.communityEvents2024[8] = 1;
                    } else {
                        player.getPacketSender().sendMessage("You need a free inventory space to claim this week's free event!");
                    }

                } else {
                    player.getPacketSender().sendMessage("You've already claimed this week's event!");
                }
                break;

            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                //seasonal
                player.getPacketSender().sendMessage("Use command ::seasonal username  to create a seasonal linked to your account.");
                break;
            case 15:
            case 16:
                if(player.communityEvents2024[14] == 0 && player.communityEvents2024[15] == 0) {
                    if(player.getInventory().getFreeSlots() > 0) {
                        player.getPacketSender().sendMessage("This week's event is a free BINGO card!");
                        player.getInventory().add(2795, 1);
                        player.communityEvents2024[14] = 1;
                        player.communityEvents2024[15] = 1;
                    } else {
                        player.getPacketSender().sendMessage("You need a free inventory space to claim this week's free event!");
                    }

                } else {
                    player.getPacketSender().sendMessage("You've already claimed this week's event!");
                }
                break;
            case 17:
            case 18:
                player.getPacketSender().sendMessage("The current Community event is the Design-A-Raid Event on Discord!");
                break;
            case 19:
                if(player.communityEvents2024[18] == 0) {
                    if(player.getInventory().getFreeSlots() > 0) {
                        player.getPacketSender().sendMessage("This week's event is a free Battle Pass!");
                        player.getInventory().add(6758, 1);
                        player.communityEvents2024[18] = 1;
                    } else {
                        player.getPacketSender().sendMessage("You need a free inventory space to claim this week's free event!");
                    }

                } else {
                    player.getPacketSender().sendMessage("You've already claimed this week's event!");
                }
                break;
            case 20:
                if(player.communityEvents2024[19] == 0) {
                    if(player.getInventory().getFreeSlots() > 0) {
                        player.getPacketSender().sendMessage("This week's event is a free Personal Max Hit Event!");
                        player.getInventory().add(4032, 1);
                        player.communityEvents2024[19] = 1;
                    } else {
                        player.getPacketSender().sendMessage("You need a free inventory space to claim this week's free event!");
                    }

                } else {
                    player.getPacketSender().sendMessage("You've already claimed this week's event!");
                }
                break;
            case 21:
                if(player.communityEvents2024[20] == 0) {
                    if(player.getInventory().getFreeSlots() > 0) {
                        player.getPacketSender().sendMessage("This week's event is a free Event Pass!");
                        player.getInventory().add(6769, 1);
                        player.communityEvents2024[20] = 1;
                    } else {
                        player.getPacketSender().sendMessage("You need a free inventory space to claim this week's free event!");
                    }

                } else {
                    player.getPacketSender().sendMessage("You've already claimed this week's event!");
                }
                break;
            case 22:
                player.getPacketSender().sendMessage("The current Community event is the Meme Event on Discord!");
                break;

            case 23:
            case 24:
            case 25:
            case 26:
                //seasonal
                player.getPacketSender().sendMessage("Use command ::seasonal username  to create a seasonal linked to your account.");
                break;
        }


 */