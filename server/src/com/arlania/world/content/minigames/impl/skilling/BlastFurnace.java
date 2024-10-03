package com.arlania.world.content.minigames.impl.skilling;

import com.arlania.model.Item;
import com.arlania.model.Skill;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.world.content.achievements.AchievementData;
import com.arlania.world.entity.impl.player.Player;

/**
 * @author Gabriel Hannason
 * Wrote this quickly!!
 * Handles the RFD quest
 */
public class BlastFurnace {

    public static void depositOre(Player player, int Ore) {
        boolean Processor = false;

        if (ItemDefinition.forId(Ore).isNoted() && player.checkAchievementAbilities(player, "processor")) {
            Ore -= 1;
            Processor = true;
        } else if (ItemDefinition.forId(Ore).isNoted() && !player.checkAchievementAbilities(player, "processor")) {
            player.getPacketSender().sendMessage("You need the Processor ability to use noted ores.");
            return;
        }

        if (player.BFbarQty == 0 && player.BForeQty == 0) {
            player.BForeType = Ore;
            Item OreItem = new Item(player.BForeType);
            String itemName = OreItem.getDefinition().getName();
            player.getPacketSender().sendMessage("@gre@You try to add " + itemName + " to the Conveyor belt.");
        } else if (player.BForeType == Ore) {
            player.BForeType = Ore;
            Item OreItem = new Item(player.BForeType);
            String itemName = OreItem.getDefinition().getName();
            player.getPacketSender().sendMessage("@gre@You try to add " + itemName + " to the Conveyor belt.");
        } else if (player.BForeQty > 0 || player.BFbarQty > 0) {
            Item OreItem = new Item(player.BForeType);
            String itemName = OreItem.getDefinition().getName();
            player.getPacketSender().sendMessage("@red@You can only add " + itemName + " to the Conveyor belt until it's gone.");
            return;
        }

        int oreQty = player.getInventory().getAmount(player.BForeType);

        if(Processor) {
            oreQty = player.getInventory().getAmount(Ore + 1);
        }

        // 444/2357 gold
        // 447/2359 mithril
        // 449/2361 adamant
        // 451/2363 rune

        if ((Ore == 451 || Ore == 452) && player.getSkillManager().getCurrentLevel(Skill.SMITHING) < 85) {
            player.getPacketSender().sendMessage("You must have 85 smithing to add runite ore to the belt.");
            return;
        }
        if ((Ore == 449 || Ore == 450) && player.getSkillManager().getCurrentLevel(Skill.SMITHING) < 70) {
            player.getPacketSender().sendMessage("You must have 70 smithing to add adamantite ore to the belt.");
            return;
        }
        if ((Ore == 447 || Ore == 448) && player.getSkillManager().getCurrentLevel(Skill.SMITHING) < 50) {
            player.getPacketSender().sendMessage("You must have 50 smithing to add mithril ore to the belt.");
            return;
        }
        if ((Ore == 444 || Ore == 445) && player.getSkillManager().getCurrentLevel(Skill.SMITHING) < 40) {
            player.getPacketSender().sendMessage("You must have 40 smithing to add gold ore to the belt.");
            return;
        }

        int maxOre = 120;

        if (player.getStaffRights().getStaffRank() >= 1)
            maxOre += 120;
        if (player.getStaffRights().getStaffRank() >= 3)
            maxOre += 120;
        if (player.getStaffRights().getStaffRank() >= 5)
            maxOre += 120;
        if (player.getStaffRights().getStaffRank() >= 6)
            maxOre += 120;


        for (int i = 0; i < oreQty; i++) {
            if (player.BForeQty + player.BFbarQty >= maxOre) {
                player.getPacketSender().sendMessage("@red@You can't deposit anymore ores!");
                return;
            }

            if (player.BForeQty < maxOre) {
                if(Processor) {
                    if (player.getInventory().contains(Ore + 1)) {
                        player.getInventory().delete(Ore + 1, 1);
                    } else if (player.getInventory().contains(Ore)) {
                        player.getInventory().delete(Ore, 1);
                    } else {
                        player.getPacketSender().sendMessage("You have no more ores.");
                        return;
                    }
                } else {
                    player.getInventory().delete(Ore, 1);
                }
                player.BForeQty += 1;
            } else {
                return;
            }
        }
        player.getPacketSender().sendMessage("@red@You can't deposit anymore ores!");
    }

    public static void processBars(Player player) {

        // 444/2357 gold
        // 447/2359 mithril
        // 449/2361 adamant
        // 451/2363 rune

        if (player.BForeType == 444)
            player.BFbarType = 2357;
        else if (player.BForeType == 447)
            player.BFbarType = 2359;
        else if (player.BForeType == 449)
            player.BFbarType = 2361;
        else if (player.BForeType == 451)
            player.BFbarType = 2363;

        for (int i = 0; i < player.BForeQty; i++) {
            //gold ore
            if ((player.getSkillManager().getCurrentLevel(Skill.SMITHING) >= 40) && player.BFbarType == 2357) {
                if (player.getEquipment().contains(776))
                    player.getSkillManager().addExperience(Skill.SMITHING, 150);
                else
                    player.getSkillManager().addExperience(Skill.SMITHING, 50);
                player.BFbarQty++;
                player.BForeQty--;
            }

            //mithril ore
            if ((player.getSkillManager().getCurrentLevel(Skill.SMITHING) >= 50) && player.BFbarType == 2359) {
                player.getSkillManager().addExperience(Skill.SMITHING, 75);
                player.BFbarQty++;
                player.BForeQty--;

                player.getAchievementTracker().progress(AchievementData.SMELT_250_MITHRIL_BARS, 1);
            }

            //adamantite ore
            if ((player.getSkillManager().getCurrentLevel(Skill.SMITHING) >= 70) && player.BFbarType == 2361) {
                player.getSkillManager().addExperience(Skill.SMITHING, 120);
                player.BFbarQty++;
                player.BForeQty--;

                player.getAchievementTracker().progress(AchievementData.SMELT_500_ADDY_BARS, 1);
            }

            //runite ore
            if ((player.getSkillManager().getCurrentLevel(Skill.SMITHING) >= 85) && player.BFbarType == 2363) {
                player.getSkillManager().addExperience(Skill.SMITHING, 170);
                player.BFbarQty++;
                player.BForeQty--;

                player.getAchievementTracker().progress(AchievementData.SMELT_1000_RUNE_BARS, 1);
            }
        }
    }

    public static void withdrawBars(Player player) {

        if (!player.getEquipment().contains(1580)) {
            player.getPacketSender().sendMessage("@red@You must wear Ice Gloves to withdraw bars!");
            return;
        }

        /*if (player.BFbarType == 2363 && player.getSkillManager().getCurrentLevel(Skill.SMITHING) < 85) {
            player.getPacketSender().sendMessage("@red@You need 85 smithing to collect Rune bars!");
            return;
        } else if (player.BFbarType == 2361 && player.getSkillManager().getCurrentLevel(Skill.SMITHING) < 70) {
            player.getPacketSender().sendMessage("@red@You need 70 smithing to collect Adamantite bars!");
            return;
        } else if (player.BFbarType == 2359 && player.getSkillManager().getCurrentLevel(Skill.SMITHING) < 50) {
            player.getPacketSender().sendMessage("@red@You need 50 smithing to collect Mithril bars!");
            return;
        } else if (player.BFbarType == 2357 && player.getSkillManager().getCurrentLevel(Skill.SMITHING) < 40) {
            player.getPacketSender().sendMessage("@red@You need 40 smithing to collect Gold bars!");
            return;
        } else if (player.BFbarType == 2353 && player.getSkillManager().getCurrentLevel(Skill.SMITHING) < 30) {
            player.getPacketSender().sendMessage("@red@You need 30 smithing to collect Steel bars!");
            return;
        } else if (player.BFbarType == 2355 && player.getSkillManager().getCurrentLevel(Skill.SMITHING) < 20) {
            player.getPacketSender().sendMessage("@red@You need 20 smithing to collect Silver bars!");
            return;
        } else if (player.BFbarType == 2351 && player.getSkillManager().getCurrentLevel(Skill.SMITHING) < 15) {
            player.getPacketSender().sendMessage("@red@You need 15 smithing to collect Iron bars!");
            return;
        }*/

        int barqty = player.getInventory().getFreeSlots();

        if (player.BFbarQty < 1) {
            player.getPacketSender().sendMessage("@red@You have no bars to collect.");
            return;
        }

        for (int i = 0; i < barqty; i++) {
            if (player.BFbarQty < 1)
                return;

            if (player.getInventory().getFreeSlots() == 0)
                return;

            if (player.getMoneyInPouch() < 500) {
                player.getPacketSender().sendMessage("@red@Bars cost 500 each to collect!");
                return;
            }

            player.setMoneyInPouch(player.getMoneyInPouch() - 500);
            player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());

            player.getInventory().add(player.BFbarType, 1);
            player.BFbarQty--;
        }
    }


    public static void withdrawNotedBars(Player player) {

        if (!player.getEquipment().contains(1580)) {
            player.getPacketSender().sendMessage("@red@You must wear Ice Gloves to withdraw bars!");
            return;
        }

        /*if (player.BFbarType == 2363 && player.getSkillManager().getCurrentLevel(Skill.SMITHING) < 85) {
            player.getPacketSender().sendMessage("@red@You need 85 smithing to collect Rune bars!");
            return;
        } else if (player.BFbarType == 2361 && player.getSkillManager().getCurrentLevel(Skill.SMITHING) < 70) {
            player.getPacketSender().sendMessage("@red@You need 70 smithing to collect Adamantite bars!");
            return;
        } else if (player.BFbarType == 2359 && player.getSkillManager().getCurrentLevel(Skill.SMITHING) < 50) {
            player.getPacketSender().sendMessage("@red@You need 50 smithing to collect Mithril bars!");
            return;
        } else if (player.BFbarType == 2357 && player.getSkillManager().getCurrentLevel(Skill.SMITHING) < 40) {
            player.getPacketSender().sendMessage("@red@You need 40 smithing to collect Gold bars!");
            return;
        } else if (player.BFbarType == 2353 && player.getSkillManager().getCurrentLevel(Skill.SMITHING) < 30) {
            player.getPacketSender().sendMessage("@red@You need 30 smithing to collect Steel bars!");
            return;
        } else if (player.BFbarType == 2355 && player.getSkillManager().getCurrentLevel(Skill.SMITHING) < 20) {
            player.getPacketSender().sendMessage("@red@You need 20 smithing to collect Silver bars!");
            return;
        } else if (player.BFbarType == 2351 && player.getSkillManager().getCurrentLevel(Skill.SMITHING) < 15) {
            player.getPacketSender().sendMessage("@red@You need 15 smithing to collect Iron bars!");
            return;
        }*/

        if (player.BFbarQty < 1) {
            player.getPacketSender().sendMessage("@red@You have no bars to collect.");
            return;
        }

        if (player.getInventory().getAmount(1811) < 1) {
            player.getPacketSender().sendMessage("@red@You are out of Magic Paper.");
            return;
        }

        int barqty = player.BFbarQty;

        for (int i = 0; i < barqty; i++) {
            if (player.BFbarQty < 1)
                return;

            if (player.getInventory().getFreeSlots() == 0)
                return;

            if (player.getMoneyInPouch() < 500) {
                player.getPacketSender().sendMessage("@red@Bars cost 500 each to collect!");
                return;
            }

            if (player.getInventory().getAmount(1811) < 1) {
                player.getPacketSender().sendMessage("@red@You've ran out of Magic Paper!");
                return;
            }

            player.setMoneyInPouch(player.getMoneyInPouch() - 500);
            player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());

            player.getInventory().add(player.BFbarType + 1, 1);
            if (!player.checkAchievementAbilities(player, "processor")) {
                player.getInventory().delete(1811, 1);
            }
            player.BFbarQty--;


        }
    }


}
