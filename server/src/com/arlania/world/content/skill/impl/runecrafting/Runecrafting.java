package com.arlania.world.content.skill.impl.runecrafting;

import com.arlania.model.Animation;
import com.arlania.model.Graphic;
import com.arlania.model.Position;
import com.arlania.model.Skill;
import com.arlania.world.content.achievements.AchievementData;
import com.arlania.world.content.skill.impl.runecrafting.RunecraftingData.RuneData;
import com.arlania.world.content.skill.impl.runecrafting.RunecraftingData.TalismanData;
import com.arlania.world.content.transportation.TeleportHandler;
import com.arlania.world.entity.impl.player.Player;

/**
 * Handles the Runecrafting skill
 *
 * @author Gabriel Hannason
 */
public class Runecrafting {

    public static void craftRunes(final Player player, RunecraftingData.RuneData rune) {
        if (!canRuneCraft(player, rune))
            return;
        int essence = -1;
        int multi = 1;
        if (player.getInventory().contains(1436) && essence < 0)
            essence = 1436;
        if (player.getInventory().contains(7936) && essence < 0)
            essence = 7936;
        if (player.getInventory().contains(7938) && essence < 0)
            essence = 7938;
        if (essence == -1)
            return;
        player.performGraphic(new Graphic(186));
        player.performAnimation(new Animation(791));
        int amountToMake = RunecraftingData.getMakeAmount(rune, player);

        if (essence == 7936) {
            amountToMake *= 2;
            multi = 2;
        }
        if (essence == 7938) {
            amountToMake *= 3;
            multi = 3;

        }

        int amountMade = 0;
        for (int i = 28; i > 0; i--) {
            if (!player.getInventory().contains(essence))
                break;
            player.getInventory().delete(essence, 1);
            player.getInventory().add(rune.getRuneID(), amountToMake);
            amountMade += amountToMake;

            int xp = rune.getXP();

            player.getSkillManager().addExperience(Skill.RUNECRAFTING, xp * multi);


            if (rune == RuneData.FIRE_RUNE) {
                player.getAchievementTracker().progress(AchievementData.CRAFT_25_FIRE_RUNES, amountToMake);
            }
            if (rune == RuneData.CHAOS_RUNE) {
                player.getAchievementTracker().progress(AchievementData.CRAFT_50_CHAOS_RUNES, amountToMake);
            }
            if (rune == RuneData.NATURE_RUNE) {
                player.getAchievementTracker().progress(AchievementData.CRAFT_100_NATURE_RUNES, amountToMake);
            }
            if (rune == RuneData.DEATH_RUNE) {
                player.getAchievementTracker().progress(AchievementData.CRAFT_250_DEATH_RUNES, amountToMake);
            }
            if (rune == RuneData.BLOOD_RUNE) {
                player.getAchievementTracker().progress(AchievementData.CRAFT_500_BLOOD_RUNES, amountToMake);
            }
            if (rune == RuneData.SOUL_RUNE) {
                player.getAchievementTracker().progress(AchievementData.CRAFT_1000_SOUL_RUNES, amountToMake);
            }

            if (player.getSkiller().getSkillerTask().getObjId()[0] == rune.getRuneID()) {
                for (int k = 0; k < amountToMake; k++) {
                    player.getSkiller().handleSkillerTaskGather(true);
                    player.getSkillManager().addExperience(Skill.SKILLER, player.getSkiller().getSkillerTask().getXP());
                }
            }

        }

        player.performGraphic(new Graphic(129));
        player.getPacketSender().sendMessage("You bind the altar's power into " + rune.getName() + "..");
        player.getClickDelay().reset();
    }

    public static void handleTalisman(Player player, int ID) {
        TalismanData talisman = RunecraftingData.TalismanData.forId(ID);
        if (talisman == null)
            return;
        if (player.getSkillManager().getCurrentLevel(Skill.RUNECRAFTING) < talisman.getLevelRequirement()) {
            player.getPacketSender().sendMessage("You need a Runecrafting level of at least " + talisman.getLevelRequirement() + " to use this Talisman's teleport function.");
            return;
        }
        Position targetLocation = talisman.getLocation();
        TeleportHandler.teleportPlayer(player, targetLocation, player.getSpellbook().getTeleportType());
    }

    public static boolean canRuneCraft(Player player, RunecraftingData.RuneData rune) {
        if (rune == null)
            return false;
        if (player.getSkillManager().getCurrentLevel(Skill.RUNECRAFTING) < rune.getLevelRequirement()) {
            player.getPacketSender().sendMessage("You need a Runecrafting level of at least " + rune.getLevelRequirement() + " to craft this.");
            return false;
        }
        if (!player.getInventory().contains(7936) && !player.getInventory().contains(1436) && !player.getInventory().contains(7938)) {
            player.getPacketSender().sendMessage("You do not have any Rune or Pure essence in your inventory.");
            return false;
        }
        return player.getClickDelay().elapsed(1000);
    }

    public static boolean runecraftingAltar(Player player, int ID) {
        return ID >= 2478 && ID < 2489 || ID == 17010 || ID == 30624 || ID == 47120 || ID == 327980 || ID == 327978 || ID == 334772;
    }

}
