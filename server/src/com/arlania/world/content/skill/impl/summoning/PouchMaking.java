package com.arlania.world.content.skill.impl.summoning;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.Graphic;
import com.arlania.model.Item;
import com.arlania.model.Skill;
import com.arlania.model.input.impl.EnterAmountToInfuse;
import com.arlania.world.content.transportation.TeleportHandler;
import com.arlania.world.entity.impl.player.Player;

public class PouchMaking {

    private static final int SHARD_ID = 18016;
    private static final int POUCH_ID = 12155;

    public static boolean pouchInterface(Player p, int buttonId) {
        final Pouch pouch = Pouch.get(buttonId);
        if (pouch == null)
            return false;
        p.setSelectedPouch(pouch);
        p.setInputHandling(new EnterAmountToInfuse());
        p.getPacketSender().sendEnterAmountPrompt("Enter amount to infuse:");
        return true;
    }

    private static boolean hasRequirements(final Player player, final Pouch pouch) {
        if (pouch == null)
            return false;
        player.getPacketSender().sendClientRightClickRemoval();
        if (player.getSkillManager().getMaxLevel(Skill.SUMMONING) >= pouch.getLevelRequired()) {
            if (player.getInventory().contains(POUCH_ID)) {
                if (player.getInventory().getAmount(SHARD_ID) >= pouch.getShardsRequired()) {
                    if (player.getInventory().contains(pouch.getCharmId())) {
                        if (player.getInventory().contains(pouch.getsecondIngredientId())) {
                            return true;
                        } else {
                            String msg = new Item(pouch.getsecondIngredientId()).getDefinition().getName().endsWith("s") ? "some" : "a";
                            player.getPacketSender().sendMessage("You need " + msg + " " + new Item(pouch.getsecondIngredientId()).getDefinition().getName() + " for this pouch.");
                            return false;
                        }
                    } else {
                        player.getPacketSender().sendMessage("You need a " + new Item(pouch.getCharmId()).getDefinition().getName() + " for this pouch.");
                        return false;
                    }
                } else {
                    player.getPacketSender().sendMessage("You need " + pouch.getShardsRequired() + " Spirit shards to create this pouch.");
                    return false;
                }
            } else {
                player.getPacketSender().sendMessage("You need to have an empty pouch to do this.");
                return false;
            }
        } else {
            player.getPacketSender().sendMessage("You need a Summoning level of at least " + pouch.getLevelRequired() + " to create this pouch");
            return false;
        }
    }

    public static void infusePouches(final Player player, final int amount) {
        final Pouch pouch = player.getSelectedPouch();
        if (pouch == null)
            return;
        if (!hasRequirements(player, pouch))
            return;
        int maxQty = 28;

        TeleportHandler.cancelCurrentActions(player);
        player.performAnimation(new Animation(725));
        player.performGraphic(new Graphic(1207));
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                int x = amount;
                int made = 0;
                while (x > 0) {
                    if (!hasRequirements(player, pouch))
                        break;
                    //if(player.getInventory().isFull())
                    //break;
                    if (made == maxQty)
                        break;
                    else {
                        player.getInventory().delete(POUCH_ID, 1);
                        player.getInventory().delete(SHARD_ID, pouch.getShardsRequired());
                        player.getInventory().delete(pouch.getCharmId(), 1);
                        player.getInventory().delete(pouch.getsecondIngredientId(), 1);
                        player.getSkillManager().addExperience(Skill.SUMMONING, pouch.getExp());
                        player.getInventory().add(pouch.getPouchId(), 1);
						/*if(pouch == Pouch.SPIRIT_DREADFOWL)
							Achievements.finishAchievement(player, AchievementData.INFUSE_A_DREADFOWL_POUCH);*/
                        x--;
                        made++;
                    }
                }
                stop();
            }
        });
    }
}
