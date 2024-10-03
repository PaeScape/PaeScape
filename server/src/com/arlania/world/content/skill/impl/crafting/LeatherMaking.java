package com.arlania.world.content.skill.impl.crafting;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.Skill;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.model.input.impl.EnterAmountOfLeatherToCraft;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.achievements.AchievementData;
import com.arlania.world.entity.impl.player.Player;

public class LeatherMaking {

    public static void craftLeatherDialogue(final Player player, final int itemUsed, final int usedWith) {
        player.getSkillManager().stopSkilling();
        for (final leatherData l : leatherData.values()) {
            final int leather = (itemUsed == 1733 ? usedWith : itemUsed);
            if (leather == l.getLeather()) {
                if(ItemDefinition.forId(l.getLeather()).isNoted() && !player.checkAchievementAbilities(player, "processor")) {
                    player.getPacketSender().sendMessage("You need Processor (Achievement Ability) to craft noted Leather.");
                    return;
                }

                if (l.getLeather() == 1741 || l.getLeather() == 1742) {
                    player.getPacketSender().sendInterfaceModel(8654, 1, 150);
                    player.getPacketSender().sendInterface(2311);
                    player.setInputHandling(new EnterAmountOfLeatherToCraft());
                    player.setSelectedSkillingItem(leather);
                    break;
                } else if (l.getLeather() == 1743 || l.getLeather() == 1744) {
                    player.getPacketSender().sendString(2799, ItemDefinition.forId(1131).getName()).sendInterfaceModel(1746, 1131, 150).sendChatboxInterface(4429);
                    player.getPacketSender().sendString(2800, "How many would you like to make?");
                    player.setInputHandling(new EnterAmountOfLeatherToCraft());
                    player.setSelectedSkillingItem(leather);
                    break;
                }
                String[] name = {
                        "Body", "Chaps", "Bandana", "Boots", "Vamb",
                };
                if (l.getLeather() == 6289 || l.getLeather() == 6290) {

                    player.getPacketSender().sendChatboxInterface(8938);
                    player.getPacketSender().sendInterfaceModel(8941, 6322, 180);
                    player.getPacketSender().sendInterfaceModel(8942, 6324, 180);
                    player.getPacketSender().sendInterfaceModel(8943, 6326, 180);
                    player.getPacketSender().sendInterfaceModel(8944, 6328, 180);
                    player.getPacketSender().sendInterfaceModel(8945, 6330, 180);
                    for (int i = 0; i < name.length; i++) {
                        player.getPacketSender().sendString(8949 + (i * 4), name[i]);
                    }
                    player.setInputHandling(new EnterAmountOfLeatherToCraft());
                    player.setSelectedSkillingItem(leather);
                    return;
                }
            }
        }
        for (final leatherDialogueData d : leatherDialogueData.values()) {
            final int leather = (itemUsed == 1733 ? usedWith : itemUsed);
            String[] name = {
                    "Vamb", "Chaps", "Body",
            };
            final int leatherG = (itemUsed == 2951 ? usedWith : itemUsed);
            if (leather == d.getLeather() || leatherG == d.getLeather()) {
                if(ItemDefinition.forId(d.getLeather()).isNoted() && !player.checkAchievementAbilities(player, "processor")) {
                    player.getPacketSender().sendMessage("You need Processor (Achievement Ability) to craft noted Leather.");
                    return;
                }

                player.getPacketSender().sendChatboxInterface(8880);
                player.getPacketSender().sendInterfaceModel(8883, d.getVamb(), 180);
                player.getPacketSender().sendInterfaceModel(8884, d.getChaps(), 180);
                player.getPacketSender().sendInterfaceModel(8885, d.getBody(), 180);
                for (int i = 0; i < name.length; i++) {
                    player.getPacketSender().sendString(8889 + (i * 4), name[i]);
                }
                player.setInputHandling(new EnterAmountOfLeatherToCraft());
                player.setSelectedSkillingItem(leather);
                return;
            }
        }
    }

    public static boolean handleButton(Player player, int button) {

        if (button == 8894)
            button = 8895;
        if (button == 8890)
            button = 8891;

        if (player.getSelectedSkillingItem() < 0)
            return false;
        for (final leatherData l : leatherData.values()) {
            if (button == l.getButtonId(button) && player.getSelectedSkillingItem() == l.getLeather()) {
                craftLeather(player, l, l.getAmount(button));
                return true;
            }
        }
        return false;
    }

    public static void craftLeather(final Player player, final leatherData l, final int amount) {
        player.getPacketSender().sendInterfaceRemoval();
        if (l.getLeather() == player.getSelectedSkillingItem()) {
            if (player.getSkillManager().getCurrentLevel(Skill.CRAFTING) < l.getLevel()) {
                player.getPacketSender().sendMessage("You need a Crafting level of at least " + l.getLevel() + " to make this.");
                return;
            }
            if (!player.getInventory().contains(1734)) {
                player.getPacketSender().sendMessage("You need some thread to make this.");
                player.getPacketSender().sendInterfaceRemoval();
                return;
            }
            if (player.getInventory().getAmount(l.getLeather()) < l.getHideAmount()) {
                player.getPacketSender().sendMessage("You need some " + ItemDefinition.forId(l.getLeather()).getName().toLowerCase() + " to make this item.");
                player.getPacketSender().sendInterfaceRemoval();
                return;
            }


            player.setCurrentTask(new Task(2, player, true) {

                int piecesToCraft = 28 * player.acceleratedProcessing();
                int piecesCrafted = 0;
                int toMake = player.acceleratedProcessing();

                @Override
                public void execute() {

                    if (piecesCrafted >= 28* player.acceleratedProcessing())
                        stop();

                    if (!player.getInventory().contains(1734) || player.getInventory().getAmount(l.getLeather()) < l.getHideAmount()) {
                        player.getPacketSender().sendMessage("You have run out of materials.");
                        stop();
                        return;
                    }

                    if (toMake * l.getHideAmount() > player.getInventory().getAmount(l.getLeather())) {
                        toMake = player.getInventory().getAmount(l.getLeather()) / l.getHideAmount();
                    }


                    if (player.getInventory().getAmount(l.getLeather()) >= (l.getHideAmount() * toMake)) {
                        if (RandomUtility.inclusiveRandom(5) <= 3)
                            player.getInventory().delete(1734, 1);
                        if (player.getInventory().contains(2951) && RandomUtility.inclusiveRandom(2) == 1) {
                            player.getPacketSender().sendMessage("Your needle's magic saves your materials!");
                        } else {
                            player.getInventory().delete(l.getLeather(), l.getHideAmount() * toMake);
                        }
                        player.getInventory().add(l.getProduct(), toMake);
                        player.getSkillManager().addExperience(Skill.CRAFTING, (int) l.getXP() * toMake);

                        if (l == leatherData.GREEN_DHIDE_BODY || l == leatherData.NOTED_GREEN_DHIDE_BODY) {
                            player.getAchievementTracker().progress(AchievementData.CRAFT_25_GREEN_DHIDE_BODY, toMake);
                        } else if (l == leatherData.BLUE_DHIDE_BODY || l == leatherData.NOTED_BLUE_DHIDE_BODY) {
                            player.getAchievementTracker().progress(AchievementData.CRAFT_50_BLUE_DHIDE_BODY, toMake);
                        } else if (l == leatherData.RED_DHIDE_BODY || l == leatherData.NOTED_RED_DHIDE_BODY) {
                            player.getAchievementTracker().progress(AchievementData.CRAFT_100_RED_DHIDE_BODY, toMake);
                        } else if (l == leatherData.BLACK_DHIDE_BODY || l == leatherData.NOTED_BLACK_DHIDE_BODY) {
                            player.getAchievementTracker().progress(AchievementData.CRAFT_250_BLACK_DHIDE_BODY, toMake);
                        }

                        player.performAnimation(new Animation(1249));
                        piecesCrafted += toMake;
                    }
                }
            });
            TaskManager.submit(player.getCurrentTask());

        }
    }

}
