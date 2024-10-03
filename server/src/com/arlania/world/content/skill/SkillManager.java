package com.arlania.world.content.skill;

import com.arlania.DiscordBot;
import com.arlania.GameLoader;
import com.arlania.GameSettings;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.*;
import com.arlania.model.Locations.Location;
import com.arlania.model.container.impl.Equipment;
import com.arlania.model.definitions.WeaponAnimations;
import com.arlania.model.definitions.WeaponInterfaces;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.*;
import com.arlania.world.content.DropLog.DropLogEntry;
import com.arlania.world.content.Sounds.Sound;
import com.arlania.world.content.achievements.AchievementData;
import com.arlania.world.content.clog.CollectionLog.CustomCollection;
import com.arlania.world.content.combat.prayer.CurseHandler;
import com.arlania.world.content.combat.prayer.PrayerHandler;
import com.arlania.world.content.globalevents.GlobalEventHandler;
import com.arlania.world.content.globalevents.GlobalEvent;
import com.arlania.world.content.minigames.impl.kingdom.NobilitySystem;
import com.arlania.world.entity.impl.GroundItemManager;
import com.arlania.world.entity.impl.player.Player;
import org.javacord.api.entity.message.MessageBuilder;

/**
 * Represents a player's skills in the game, also manages
 * calculations such as combat level and total level.
 *
 * @author relex lawl
 * @editor Gabbe
 */

public class SkillManager {

    /**
     * The skillmanager's constructor
     *
     * @param player The player's who skill set is being represented.
     */
    public SkillManager(Player player) {
        this.player = player;
        newSkillManager();
    }

    /**
     * Creates a new skillmanager for the player
     * Sets current and max appropriate levels.
     */
    public void newSkillManager() {
        this.skills = new Skills();
        for (int i = 0; i < MAX_SKILLS; i++) {
            skills.level[i] = skills.maxLevel[i] = 1;
            skills.experience[i] = 0;
        }
        skills.level[Skill.CONSTITUTION.ordinal()] = skills.maxLevel[Skill.CONSTITUTION.ordinal()] = 100;
        skills.experience[Skill.CONSTITUTION.ordinal()] = 1184;
        skills.level[Skill.PRAYER.ordinal()] = skills.maxLevel[Skill.PRAYER.ordinal()] = 10;
    }


    public static int[] SkillingPets = {220659, 220693, 221187, 221509, 213322, 220661, 220663, 220665, 211320};
	
	
	/*if (skills.maxLevel[skill.ordinal()] == getMaxAchievingLevel(skill)) {
			for(int i = 0; i < Skill.values().length; i++) {
				if(i == 21)
					continue;
				if(player.getSkillManager().getMaxLevel(i) < (i == 3 || i == 5 ? 990 : 99)) {
					return true;
				}
			World.sendMessage("Testing");
				}
			}
	return false;
	}*/

    /**
     * Adds experience to {@code skill} by the {@code experience} amount.
     *
     * @param skill      The skill to add experience to.
     * @param experience The amount of experience to add to the skill.
     * @return The Skills instance.
     */
    public SkillManager addExperience(Skill skill, double experience) {

        if (player.experienceLocked())
            return this;

        int xpModifier = player.getexprate() + player.Prodigy;

        experience *= xpModifier;

        //skill pets
        int petChance = 250000000;

        if (player.getGameMode() == GameMode.SEASONAL_IRONMAN && World.getSeasonalTeamBonus(player)) {
            experience *= 1.1;
        }

        if (player.getLocation() == Location.WILDERNESS) {
            experience *= 1.25;
        }

        experience *= (1 + (.05 * player.wildBoost));

        if (WellOfGoodwill.isActive())
            experience *= 1.3;

        if (player.checkAchievementAbilities(player, "bountiful"))
            experience *= 1.25;

        if (player.getGameMode() == GameMode.SEASONAL_IRONMAN) {
            if (player.Contender) {
                if (skill == Skill.ATTACK || skill == Skill.STRENGTH || skill == Skill.DEFENCE || skill == Skill.CONSTITUTION || skill == Skill.RANGED || skill == Skill.MAGIC || skill == Skill.PRAYER || skill == Skill.SUMMONING) {
                    experience *= 3;
                }
            } else if (player.Strategist) {
                if (skill != Skill.ATTACK && skill != Skill.STRENGTH && skill != Skill.DEFENCE && skill != Skill.CONSTITUTION && skill != Skill.RANGED && skill != Skill.MAGIC && skill != Skill.PRAYER && skill != Skill.SUMMONING) {
                    experience *= 3;
                }
            }
        }


        if (skill == Skill.AGILITY) {
            if (experience >= RandomUtility.inclusiveRandom(petChance)) {
                int petId = 220659;
                Item pet = new Item(petId);
                String petName = pet.getDefinition().getName();
                String message = "@blu@[PET DROP] " + player.getUsername() + " has just received a @red@" + petName + "@blu@.";
                player.getPacketSender().sendMessage("@or2@You feel something special at your feet...");
                World.sendMessage("drops", message);
                DropLog.submit(player, new DropLogEntry(petId, 1));
                GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(petId), player.getPosition(), player.getUsername(), false, 150, true, 200));
                player.getCollectionLog().handleDrop(CustomCollection.SkillingPets.getId(), petId, 1);

                String discordMessage = "[Skilling Pet] " + player.getUsername()
                        + " just received " + petName;

                if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                    new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
            }

            if ((player.getSummoning().getFamiliar() != null) && (player.getSummoning().getFamiliar().getSummonNpc().getId() == 21351)) {
                experience *= 1.25;
            }
        } else if (skill == Skill.FIREMAKING) {
            if (experience >= RandomUtility.inclusiveRandom(petChance)) {
                int petId = 220693;
                Item pet = new Item(petId);
                String petName = pet.getDefinition().getName();
                String message = "@blu@[PET DROP] " + player.getUsername() + " has just received a @red@" + petName + "@blu@.";
                player.getPacketSender().sendMessage("@or2@You feel something special at your feet...");
                World.sendMessage("drops", message);
                DropLog.submit(player, new DropLogEntry(petId, 1));
                GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(petId), player.getPosition(), player.getUsername(), false, 150, true, 200));
                player.getCollectionLog().handleDrop(CustomCollection.SkillingPets.getId(), petId, 1);

                String discordMessage = "[Skilling Pet] " + player.getUsername()
                        + " just received " + petName;

                if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                    new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
            }

            if ((player.getSummoning().getFamiliar() != null) && (player.getSummoning().getFamiliar().getSummonNpc().getId() == 21368)) {
                experience *= 1.25;
            }
        } else if (skill == Skill.MINING) {
            if (experience >= RandomUtility.inclusiveRandom(petChance)) {
                int petId = 221187;
                Item pet = new Item(petId);
                String petName = pet.getDefinition().getName();
                String message = "@blu@[PET DROP] " + player.getUsername() + " has just received a @red@" + petName + "@blu@.";
                player.getPacketSender().sendMessage("@or2@You feel something special at your feet...");
                World.sendMessage("drops", message);
                DropLog.submit(player, new DropLogEntry(petId, 1));
                GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(petId), player.getPosition(), player.getUsername(), false, 150, true, 200));
                player.getCollectionLog().handleDrop(CustomCollection.SkillingPets.getId(), petId, 1);

                String discordMessage = "[Skilling Pet] " + player.getUsername()
                        + " just received " + petName;

                if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                    new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
            }

            if ((player.getSummoning().getFamiliar() != null) && (player.getSummoning().getFamiliar().getSummonNpc().getId() == 21439)) {
                experience *= 1.25;
            }
        } else if (skill == Skill.HUNTER) {
            if (experience >= RandomUtility.inclusiveRandom(petChance)) {
                int petId = 221509;
                Item pet = new Item(petId);
                String petName = pet.getDefinition().getName();
                String message = "@blu@[PET DROP] " + player.getUsername() + " has just received a @red@" + petName + "@blu@.";
                player.getPacketSender().sendMessage("@or2@You feel something special at your feet...");
                World.sendMessage("drops", message);
                DropLog.submit(player, new DropLogEntry(petId, 1));
                GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(petId), player.getPosition(), player.getUsername(), false, 150, true, 200));
                player.getCollectionLog().handleDrop(CustomCollection.SkillingPets.getId(), petId, 1);

                String discordMessage = "[Skilling Pet] " + player.getUsername()
                        + " just received " + petName;

                if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                    new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
            }

            if ((player.getSummoning().getFamiliar() != null) && (player.getSummoning().getFamiliar().getSummonNpc().getId() == 21759)) {
                experience *= 1.25;
            }
        } else if (skill == Skill.WOODCUTTING) {
            if (experience >= RandomUtility.inclusiveRandom(petChance)) {
                int petId = 213322;
                Item pet = new Item(petId);
                String petName = pet.getDefinition().getName();
                String message = "@blu@[PET DROP] " + player.getUsername() + " has just received a @red@" + petName + "@blu@.";
                player.getPacketSender().sendMessage("@or2@You feel something special at your feet...");
                World.sendMessage("drops", message);
                DropLog.submit(player, new DropLogEntry(petId, 1));
                GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(petId), player.getPosition(), player.getUsername(), false, 150, true, 200));
                player.getCollectionLog().handleDrop(CustomCollection.SkillingPets.getId(), petId, 1);

                String discordMessage = "[Skilling Pet] " + player.getUsername()
                        + " just received " + petName;

                if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                    new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
            }

            if ((player.getSummoning().getFamiliar() != null) && (player.getSummoning().getFamiliar().getSummonNpc().getId() == 20717)) {
                experience *= 1.25;
            }
        } else if (skill == Skill.FARMING) {
            if (experience >= RandomUtility.inclusiveRandom(petChance)) {
                int petId = 220661;
                Item pet = new Item(petId);
                String petName = pet.getDefinition().getName();
                String message = "@blu@[PET DROP] " + player.getUsername() + " has just received a @red@" + petName + "@blu@.";
                player.getPacketSender().sendMessage("@or2@You feel something special at your feet...");
                World.sendMessage("drops", message);
                DropLog.submit(player, new DropLogEntry(petId, 1));
                GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(petId), player.getPosition(), player.getUsername(), false, 150, true, 200));
                player.getCollectionLog().handleDrop(CustomCollection.SkillingPets.getId(), petId, 1);

                String discordMessage = "[Skilling Pet] " + player.getUsername()
                        + " just received " + petName;

                if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                    new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
            }
            if ((player.getSummoning().getFamiliar() != null) && (player.getSummoning().getFamiliar().getSummonNpc().getId() == 21352)) {
                experience *= 1.25;
            }
        } else if (skill == Skill.THIEVING) {
            if (experience >= RandomUtility.inclusiveRandom(petChance)) {
                int petId = 220663;
                Item pet = new Item(petId);
                String petName = pet.getDefinition().getName();
                String message = "@blu@[PET DROP] " + player.getUsername() + " has just received a @red@" + petName + "@blu@.";
                player.getPacketSender().sendMessage("@or2@You feel something special at your feet...");
                World.sendMessage("drops", message);
                DropLog.submit(player, new DropLogEntry(petId, 1));
                GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(petId), player.getPosition(), player.getUsername(), false, 150, true, 200));
                player.getCollectionLog().handleDrop(CustomCollection.SkillingPets.getId(), petId, 1);

                String discordMessage = "[Skilling Pet] " + player.getUsername()
                        + " just received " + petName;

                if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                    new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
            }

            if ((player.getSummoning().getFamiliar() != null) && (player.getSummoning().getFamiliar().getSummonNpc().getId() == 21353)) {
                experience *= 1.25;
            }
        } else if (skill == Skill.RUNECRAFTING) {
            if (experience >= RandomUtility.inclusiveRandom(petChance)) {
                int petId = 220665;
                Item pet = new Item(petId);
                String petName = pet.getDefinition().getName();
                String message = "@blu@[PET DROP] " + player.getUsername() + " has just received a @red@" + petName + "@blu@.";
                player.getPacketSender().sendMessage("@or2@You feel something special at your feet...");
                World.sendMessage("drops", message);
                DropLog.submit(player, new DropLogEntry(petId, 1));
                GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(petId), player.getPosition(), player.getUsername(), false, 150, true, 200));
                player.getCollectionLog().handleDrop(CustomCollection.SkillingPets.getId(), petId, 1);

                String discordMessage = "[Skilling Pet] " + player.getUsername()
                        + " just received " + petName;

                if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                    new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
            }

            if ((player.getSummoning().getFamiliar() != null) && (player.getSummoning().getFamiliar().getSummonNpc().getId() == 21354)) {
                experience *= 1.25;
            }
        } else if (skill == Skill.FISHING) {
            if (experience >= RandomUtility.inclusiveRandom(petChance)) {
                int petId = 211320;
                Item pet = new Item(petId);
                String petName = pet.getDefinition().getName();
                String message = "@blu@[PET DROP] " + player.getUsername() + " has just received a @red@Heron@blu@.";
                player.getPacketSender().sendMessage("@or2@You feel something special at your feet...");
                World.sendMessage("drops", message);
                DropLog.submit(player, new DropLogEntry(petId, 1));
                GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(petId), player.getPosition(), player.getUsername(), false, 150, true, 200));
                player.getCollectionLog().handleDrop(CustomCollection.SkillingPets.getId(), petId, 1);

                String discordMessage = "[Skilling Pet] " + player.getUsername()
                        + " just received " + petName;

                if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                    new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
            }

            if ((player.getSummoning().getFamiliar() != null) && (player.getSummoning().getFamiliar().getSummonNpc().getId() == 20722)) {
                experience *= 1.25;
            }
        }


        /*
         * If the experience in the skill is already greater or equal to
         * {@code MAX_EXPERIENCE} then stop.
         */


        int bonusFactor = 1;
        double equipMultiplier = 1;

        if (player.getEquipment().contains(21025)) {
            bonusFactor += .05;
            equipMultiplier += .05;
        }
        if (player.getEquipment().contains(21026)) {
            bonusFactor += .05;
            equipMultiplier += .05;
        }
        if (player.getEquipment().contains(21027)) {
            bonusFactor += .05;
            equipMultiplier += .05;
        }
        if (player.getEquipment().contains(21028)) {
            bonusFactor += .05;
            equipMultiplier += .05;
        }
        if (player.getEquipment().contains(21029)) {
            bonusFactor += .05;
            equipMultiplier += .05;
        }
        if (player.getEquipment().contains(21030)) {
            bonusFactor += .05;
            equipMultiplier += .05;
        }
        if (player.getEquipment().contains(21043)) {
            bonusFactor += .05;
            equipMultiplier += .05;
        }
        if (player.getEquipment().contains(21047)) {
            bonusFactor += .05;
            equipMultiplier += .05;
        }
        if (player.getEquipment().contains(2765)) {
            bonusFactor += .05;
            equipMultiplier += .05;
        }
        if (player.getEquipment().contains(2766)) {
            bonusFactor += .05;
            equipMultiplier += .05;
        }
        if (player.getEquipment().contains(2767)) {
            bonusFactor += .05;
            equipMultiplier += .05;
        }

        experience *= equipMultiplier;


        //experience = BrawlingGloves.getExperienceIncrease(player, skill.ordinal(), experience);

        if (GlobalEventHandler.effectActive(GlobalEvent.Effect.DOUBLE_EXP) || (player.doubleExpEvent && player.personalEvent) || player.activeDoubleXP) {
            experience *= 2;
            bonusFactor *= 2;
        }
        if (GameSettings.BONUS_EXP) {
            experience *= 1.5;
            bonusFactor *= 2;
        }

        experience *= (1 + NobilitySystem.getNobilityBoost(player));

        //else
        //experience *= GameLoader.getDoubleEXPWeekend();



        /*
         * The skill's level before adding experience.
         */
        int startingLevel = isNewSkill(skill) ? (skills.maxLevel[skill.ordinal()] / 10) : skills.maxLevel[skill.ordinal()];
        /*
         * Adds the experience to the skill's experience.
         */

        //genie boost
        if (PetAbilities.checkPetAbilities(player, "genie")) {
            experience *= 1.1;
            bonusFactor *= 1.1;
        }

        if (player.activeBonusXP) {
            experience *= 1.5;
            bonusFactor *= 1.5;
        }
        if (skill.toString() == GameLoader.getSkillDay()) {
            experience *= 1.25;
            bonusFactor *= 1.25;
        }


        if (player.battlePass) {
            player.bpExperience += experience;
        }

        if (player.eventPass) {
            player.epExperience += experience;
        }

        player.totalXP += experience;

        player.getAchievementTracker().progress(AchievementData.GAIN_1M_EXP, experience);
        player.getAchievementTracker().progress(AchievementData.GAIN_10M_EXP, experience);
        player.getAchievementTracker().progress(AchievementData.GAIN_100M_EXP, experience);
        player.getAchievementTracker().progress(AchievementData.GAIN_1B_EXP, experience);


        if (player.totalXP >= 1000000)
            player.getAchievementTracker().progress(AchievementData.GAIN_1M_EXP, 1000000);
        if (player.totalXP >= 10000000)
            player.getAchievementTracker().progress(AchievementData.GAIN_10M_EXP, 10000000);
        if (player.totalXP >= 100000000)
            player.getAchievementTracker().progress(AchievementData.GAIN_100M_EXP, 100000000);
        if (player.totalXP >= 1000000000)
            player.getAchievementTracker().progress(AchievementData.GAIN_1B_EXP, 1000000000);
        if (player.totalXP >= 1e+10d)
            player.getAchievementTracker().progress(AchievementData.GAIN_10B_EXP, 1);
        if (player.totalXP >= 1e+11d)
            player.getAchievementTracker().progress(AchievementData.GAIN_100B_EXP, 1);


        if (getTotalLevel() >= 3000)
            player.getAchievementTracker().progress(AchievementData.REACH_3000_TOTAL_LEVEL, 1);
        if (getTotalLevel() >= 2925)
            player.getAchievementTracker().progress(AchievementData.REACH_2925_TOTAL_LEVEL, 1);
        if (getTotalLevel() >= 2875)
            player.getAchievementTracker().progress(AchievementData.REACH_2875_TOTAL_LEVEL, 1);
        if (getTotalLevel() >= 2825)
            player.getAchievementTracker().progress(AchievementData.REACH_2825_TOTAL_LEVEL, 1);
        if (getTotalLevel() >= 2775)
            player.getAchievementTracker().progress(AchievementData.REACH_2775_TOTAL_LEVEL, 1);
        if (getTotalLevel() >= 2725)
            player.getAchievementTracker().progress(AchievementData.REACH_2725_TOTAL_LEVEL, 1);
        if (getTotalLevel() >= 2675)
            player.getAchievementTracker().progress(AchievementData.REACH_2675_TOTAL_LEVEL, 1);
        if (getTotalLevel() >= 2625)
            player.getAchievementTracker().progress(AchievementData.REACH_2625_TOTAL_LEVEL, 1);
        if (getTotalLevel() >= 2575)
            player.getAchievementTracker().progress(AchievementData.REACH_2575_TOTAL_LEVEL, 1);
        if (getTotalLevel() >= 2525)
            player.getAchievementTracker().progress(AchievementData.REACH_2525_TOTAL_LEVEL, 1);
        if (getTotalLevel() >= 2475)
            player.getAchievementTracker().progress(AchievementData.REACH_2475_TOTAL_LEVEL, 1);
        if (getTotalLevel() >= 2250)
            player.getAchievementTracker().progress(AchievementData.REACH_2250_TOTAL_LEVEL, 1);
        if (getTotalLevel() >= 2000)
            player.getAchievementTracker().progress(AchievementData.REACH_2000_TOTAL_LEVEL, 1);
        if (getTotalLevel() >= 1750)
            player.getAchievementTracker().progress(AchievementData.REACH_1750_TOTAL_LEVEL, 1);
        if (getTotalLevel() >= 1500)
            player.getAchievementTracker().progress(AchievementData.REACH_1500_TOTAL_LEVEL, 1);
        if (getTotalLevel() >= 1250)
            player.getAchievementTracker().progress(AchievementData.REACH_1250_TOTAL_LEVEL, 1);
        if (getTotalLevel() >= 1000)
            player.getAchievementTracker().progress(AchievementData.REACH_1000_TOTAL_LEVEL, 1);
		
		/*if(RandomUtility.getRandom(5000000) < experience && !player.christmas2021complete && player.christmas2021start)
		{
			player.getPacketSender().sendMessage("@red@A present appears for you on the ground!");
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(15420, 1), player.getPosition().copy(), player.getUsername(), false, 150, true, 200));
		}*/
		
		
		/*if (player.inGauntlet && player.getLocation() == Location.GAUNTLET)
		{
			int maxPoints = 1000000;
			
			
			if(skill == skill.MINING || skill == skill.WOODCUTTING || skill == skill.SMITHING)
			{
				player.GauntletPoints += experience;
			
				if (player.GauntletPoints > maxPoints)
					player.GauntletPoints = maxPoints;
			}
		}*/


        if (this.skills.experience[skill.ordinal()] >= MAX_EXPERIENCE)
            return this;

        this.skills.experience[skill.ordinal()] = this.skills.experience[skill.ordinal()] + experience > MAX_EXPERIENCE ? MAX_EXPERIENCE : this.skills.experience[skill.ordinal()] + (int) experience;
        if (this.skills.experience[skill.ordinal()] >= MAX_EXPERIENCE) {
            String skillName = Misc.formatText(skill.toString().toLowerCase());
            player.getPacketSender().sendMessage("Well done! You've achieved the highest possible Experience in this skill!");
            String log = "@red@[Player News] " + player.getexprate() + "x " + player.getGameMode() + " @bla@" + player.getUsername() + " has just achieved Maximum Exp in " + skillName + "!";
            World.sendMessage("levels", log);
            PlayerLogs.log(player.getUsername(), log);
        }



        /*
         * The skill's level after adding the experience.
         */
        int newLevel = getLevelForExperience(this.skills.experience[skill.ordinal()], player);
        /*
         * If the starting level less than the new level, level up.
         */

        int maxLevel = getMaxAchievingLevel(skill, player);

        if (newLevel > startingLevel && startingLevel < maxLevel) {

            if (newLevel > maxLevel)
                newLevel = maxLevel;

            int level = newLevel - startingLevel;
            String skillName = Misc.formatText(skill.toString().toLowerCase());
            skills.maxLevel[skill.ordinal()] += isNewSkill(skill) ? level * 10 : level;
            /*
             * If the skill is not constitution, prayer or summoning, then set the current level
             * to the max level.
             */
			/*if (!isNewSkill(skill) && !skill.equals(Skill.SUMMONING)) {
				setCurrentLevel(skill, skills.maxLevel[skill.ordinal()]);
			}*/
            //player.getPacketSender().sendFlashingSidebar(Constants.SKILLS_TAB);

            int prodigyBonus = (player.Prodigy * 3) + 6;


            if (newLevel > startingLevel) {
                if ((skill == Skill.ATTACK || skill == Skill.STRENGTH ||
                        skill == Skill.DEFENCE || skill == Skill.RANGED || skill == Skill.MAGIC ||
                        skill == Skill.PRAYER || skill == Skill.CONSTITUTION) && skills.maxLevel[skill.ordinal()] > skills.level[skill.ordinal()])
                    setCurrentLevel(skill, skills.maxLevel[skill.ordinal()]);
                else if (player.Prodigy == 0 && newLevel < skills.maxLevel[skill.ordinal()])
                    setCurrentLevel(skill, skills.maxLevel[skill.ordinal()]);
                else if (skill != Skill.ATTACK && skill != Skill.STRENGTH && skill != Skill.DEFENCE &&
                        skill != Skill.RANGED && skill != Skill.MAGIC && skill != Skill.PRAYER &&
                        skill != Skill.CONSTITUTION && player.Prodigy > 0)
                    setCurrentLevel(skill, skills.maxLevel[skill.ordinal()] + prodigyBonus);
                else if (skill != Skill.ATTACK && skill != Skill.STRENGTH && skill != Skill.DEFENCE &&
                        skill != Skill.RANGED && skill != Skill.MAGIC && skill != Skill.PRAYER &&
                        skill != Skill.CONSTITUTION && player.Prodigy == 0)
                    setCurrentLevel(skill, skills.maxLevel[skill.ordinal()]);
            }

            //player.setDialogue(null);
            //player.getPacketSender().sendString(4268, "Congratulations! You have achieved a " + skillName + " level!");
            //player.getPacketSender().sendString(4269, "Well done. You are now level " + newLevel + ".");
            //player.getPacketSender().sendString(358, "Click here to continue.");

            //if(skill != skill.SKILLER)
            //player.getPacketSender().sendChatboxInterface(skill.getChatboxInterface());

            player.performGraphic(new Graphic(312));
            player.getPacketSender().sendMessage("You've just advanced " + skillName + " level! You have reached level " + newLevel);
            Sounds.sendSound(player, Sound.LEVELUP);


            if (getTotalLevel() >= 3000)
                player.getAchievementTracker().progress(AchievementData.REACH_3000_TOTAL_LEVEL, 1);
            if (getTotalLevel() >= 2925)
                player.getAchievementTracker().progress(AchievementData.REACH_2925_TOTAL_LEVEL, 1);
            if (getTotalLevel() >= 2875)
                player.getAchievementTracker().progress(AchievementData.REACH_2875_TOTAL_LEVEL, 1);
            if (getTotalLevel() >= 2825)
                player.getAchievementTracker().progress(AchievementData.REACH_2825_TOTAL_LEVEL, 1);
            if (getTotalLevel() >= 2775)
                player.getAchievementTracker().progress(AchievementData.REACH_2775_TOTAL_LEVEL, 1);
            if (getTotalLevel() >= 2725)
                player.getAchievementTracker().progress(AchievementData.REACH_2725_TOTAL_LEVEL, 1);
            if (getTotalLevel() >= 2675)
                player.getAchievementTracker().progress(AchievementData.REACH_2675_TOTAL_LEVEL, 1);
            if (getTotalLevel() >= 2625)
                player.getAchievementTracker().progress(AchievementData.REACH_2625_TOTAL_LEVEL, 1);
            if (getTotalLevel() >= 2575)
                player.getAchievementTracker().progress(AchievementData.REACH_2575_TOTAL_LEVEL, 1);
            if (getTotalLevel() >= 2525)
                player.getAchievementTracker().progress(AchievementData.REACH_2525_TOTAL_LEVEL, 1);
            if (getTotalLevel() >= 2475)
                player.getAchievementTracker().progress(AchievementData.REACH_2475_TOTAL_LEVEL, 1);
            if (getTotalLevel() >= 2250)
                player.getAchievementTracker().progress(AchievementData.REACH_2250_TOTAL_LEVEL, 1);
            if (getTotalLevel() >= 2000)
                player.getAchievementTracker().progress(AchievementData.REACH_2000_TOTAL_LEVEL, 1);
            if (getTotalLevel() >= 1750)
                player.getAchievementTracker().progress(AchievementData.REACH_1750_TOTAL_LEVEL, 1);
            if (getTotalLevel() >= 1500)
                player.getAchievementTracker().progress(AchievementData.REACH_1500_TOTAL_LEVEL, 1);
            if (getTotalLevel() >= 1250)
                player.getAchievementTracker().progress(AchievementData.REACH_1250_TOTAL_LEVEL, 1);
            if (getTotalLevel() >= 1000)
                player.getAchievementTracker().progress(AchievementData.REACH_1000_TOTAL_LEVEL, 1);


            int aboveGoal = 0;
            int goal = player.baseLevelGoal + 10;

            for (Skill skillz : Skill.values()) {
                /*
                 * If the skill is not equal to constitution or prayer and above goal
                 */
                if (!isNewSkill(skillz)) {
                    if (skills.maxLevel[skillz.ordinal()] >= goal)
                        aboveGoal++;
                    /*
                     * Other-wise maxLevel / 10, used for constitution and prayer * 10
                     */
                } else {
                    if (skills.maxLevel[skillz.ordinal()] / 10 >= goal)
                        aboveGoal++;
                }
            }

            //test

//			CustomEmoji emoji = 926273276706181223;

            //if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
            //new MessageBuilder().append("<:peepolove:926273276706181223>[Player News] something nice happened lol").send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());


            if (skills.maxLevel[skill.ordinal()] == getMaxAchievingLevel(skill, player)) {
                player.getPacketSender().sendMessage("Well done! You've achieved the highest possible level in this skill!");

                int maxlevel = getMaxAchievingLevel(skill, player);

                if(maxlevel > 120)
                    maxlevel /= 10;

                World.sendMessage("levels", "@red@[Player News] " + player.getexprate() + "x " + player.getGameMode() + " @bla@" + player.getUsername() + " has just achieved level " + maxlevel + " in " + skillName + "!");
                if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                    new MessageBuilder().append("[Player News] " + player.getexprate() + "x " + player.getGameMode() + " " + player.getUsername() + " has just achieved level " + maxlevel + " in " + skillName + "!").send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
                TaskManager.submit(new Task(2, player, true) {
                    int localGFX = 1634;

                    @Override
                    public void execute() {
                        player.performGraphic(new Graphic(localGFX));
                        if (localGFX == 1637) {
                            stop();
                            return;
                        }
                        localGFX++;
                        player.performGraphic(new Graphic(localGFX));
                    }
                });


            } else {
                TaskManager.submit(new Task(2, player, false) {
                    @Override
                    public void execute() {
                        player.performGraphic(new Graphic(199));
                        stop();
                    }
                });
            }
            player.getUpdateFlag().flag(Flag.APPEARANCE);


        }
        updateSkill(skill);
        this.totalGainedExp += experience;
        return this;
    }

    public SkillManager stopSkilling() {
        if (player.getCurrentTask() != null) {
            player.getCurrentTask().stop();
            player.setCurrentTask(null);
        }
        player.setResetPosition(null);
        player.setInputHandling(null);
        return this;
    }

    /**
     * Updates the skill strings, for skill tab and orb updating.
     *
     * @param skill The skill who's strings to update.
     * @return The Skills instance.
     */
    public SkillManager updateSkill(Skill skill) {
        int maxLevel = getMaxLevel(skill);
        int currentLevel = getCurrentLevel(skill);


        if (skill == Skill.PRAYER)
            player.getPacketSender().sendString(687, currentLevel + "/" + maxLevel);
        if (isNewSkill(skill)) {
            maxLevel = (maxLevel / 10);
            currentLevel = (currentLevel / 10);
        }

        player.getPacketSender().sendString(31200, "" + getTotalLevel());
        player.getPacketSender().sendString(19000, "Combat level: " + getCombatLevel());
        player.getPacketSender().sendSkill(skill);
        return this;
    }

    public SkillManager resetSkill(Skill skill) {

        /*if (player.getSkillManager().getMaxLevel(skill) < getMaxAchievingLevel(skill, player)) {
            player.getPacketSender().sendMessage("You must have reached the maximum level in a skill to reset it.");
            return this;
        }*/

        if (player.getSummoning().getFamiliar() != null) {
            player.getPacketSender().sendMessage("@red@You can't prestige when something is following you.");
            return this;
        }

        if (player.getLocation() == Location.WILDERNESS || player.getCombatBuilder().isBeingAttacked()) {
            player.getPacketSender().sendMessage("You cannot do this at the moment");
            return this;
        }

        if (!player.getEquipment().isNaked(player)) {
            player.getPacketSender().sendMessage("Please unequip all your items first.");
            return this;
        }

        setCurrentLevel(skill, skill == Skill.PRAYER ? 10 : skill == Skill.CONSTITUTION ? 100 : 1).setMaxLevel(skill, skill == Skill.PRAYER ? 10 : skill == Skill.CONSTITUTION ? 100 : 1).setExperience(skill, SkillManager.getExperienceForLevel(skill == Skill.CONSTITUTION ? 10 : 1));
        PrayerHandler.deactivateAll(player);
        CurseHandler.deactivateAll(player);
        BonusManager.update(player, 0, 0);
        WeaponInterfaces.assign(player, player.getEquipment().get(Equipment.WEAPON_SLOT));
        WeaponAnimations.assign(player, player.getEquipment().get(Equipment.WEAPON_SLOT));
        player.getPacketSender().sendMessage("You have reset your " + skill.getFormatName() + " level.");
        return this;
    }

    /**
     * Gets the minimum experience in said level.
     *
     * @param level The level to get minimum experience for.
     * @return The least amount of experience needed to achieve said level.
     */
    public static int getExperienceForLevel(int level) {
        if (level <= 99) {
            return EXP_ARRAY[--level > 98 ? 98 : level];
        } else {
            int points = 0;
            int output = 0;
            for (int lvl = 1; lvl <= level; lvl++) {
                points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
                if (lvl >= level) {
                    return output;
                }
                output = (int) Math.floor(points / 4);
            }
        }
        return 0;
    }

    /**
     * Gets the level from said experience.
     *
     * @param experience The experience to get level for.
     * @return The level you obtain when you have specified experience.
     */
    public static int getLevelForExperience(int experience, Player player) {

        int maxLevel = 99 + (player.prestige * 2);

        if (player.prestige > 9)
            maxLevel = 120;

        if (experience <= EXPERIENCE_FOR_120) {
            for (int j = 119; j >= 0; j--) {
                if (EXP_ARRAY[j] <= experience) {

                    if (j + 1 >= maxLevel)
                        return maxLevel;
                    else
                        return j + 1;
                }
            }
        } else {
            int points = 0, output = 0;
            for (int lvl = 1; lvl <= maxLevel; lvl++) {
                points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
                output = (int) Math.floor(points / 4);
                if (output >= experience) {

                    if (lvl >= maxLevel)
                        return maxLevel;
                    else
                        return lvl;
                }
            }
        }

        return maxLevel;

        //return 120;
    }

    /**
     * Calculates the player's combat level.
     *
     * @return The average of the player's combat skills.
     */
    public int getCombatLevel() {
        final int attack = skills.maxLevel[Skill.ATTACK.ordinal()];
        final int defence = skills.maxLevel[Skill.DEFENCE.ordinal()];
        final int strength = skills.maxLevel[Skill.STRENGTH.ordinal()];
        final int hp = skills.maxLevel[Skill.CONSTITUTION.ordinal()] / 10;
        final int prayer = skills.maxLevel[Skill.PRAYER.ordinal()] / 10;
        final int ranged = skills.maxLevel[Skill.RANGED.ordinal()];
        final int magic = skills.maxLevel[Skill.MAGIC.ordinal()];
        final int summoning = skills.maxLevel[Skill.SUMMONING.ordinal()];
        int combatLevel = 3;
        combatLevel = (int) ((defence + hp + Math.floor(prayer / 2)) * 0.2535) + 1;
        final double melee = (attack + strength) * 0.325;
        final double ranger = Math.floor(ranged * 1.5) * 0.325;
        final double mage = Math.floor(magic * 1.5) * 0.325;
        if (melee >= ranger && melee >= mage) {
            combatLevel += melee;
        } else if (ranger >= melee && ranger >= mage) {
            combatLevel += ranger;
        } else if (mage >= melee && mage >= ranger) {
            combatLevel += mage;
        }
        if (player.getLocation() != Location.WILDERNESS) {
            combatLevel += summoning * 0.125;
        } else {
            if (combatLevel > 126) {
                return 126;
            }
        }
		/*if (combatLevel > 138) {
			return 138;
		} else if (combatLevel < 3) {
			return 3;
		}*/
        return combatLevel;
    }

    /**
     * Gets the player's total level.
     *
     * @return The value of every skill summed up.
     */
    public int getTotalLevel() {
        int total = 0;
        for (Skill skill : Skill.values()) {
            /*
             * If the skill is not equal to constitution or prayer, total can
             * be summed up with the maxLevel.
             */
            if (!isNewSkill(skill)) {
                total += skills.maxLevel[skill.ordinal()];
                /*
                 * Other-wise add the maxLevel / 10, used for 'constitution' and prayer * 10.
                 */
            } else {
                total += skills.maxLevel[skill.ordinal()] / 10;
            }
        }
        return total;
    }

    /**
     * Gets the player's total experience.
     *
     * @return The experience value from the player's every skill summed up.
     */
    public long getTotalExp() {
        long xp = 0;
        for (Skill skill : Skill.values())
            xp += player.getSkillManager().getExperience(skill);
        return xp;
    }

    /**
     * Checks if the skill is a x10 skill.
     *
     * @param skill The skill to check.
     * @return The skill is a x10 skill.
     */
    public static boolean isNewSkill(Skill skill) {
        return skill == Skill.CONSTITUTION || skill == Skill.PRAYER;
    }

    /**
     * Gets the max level for <code>skill</code>
     *
     * @param skill The skill to get max level for.
     * @return The max level that can be achieved in said skill.
     */
    public static int getMaxAchievingLevel(Skill skill, Player player) {
        int level = 99;
        if (isNewSkill(skill)) {
            level = 990;
        }
		/*if (skill == Skill.DUNGEONEERING) {
			level = 120;
		}*/

        if (player.prestige > 0) {
            level = 99 + (2 * player.prestige);
            if (isNewSkill(skill)) {
                level = 990 + (20 * player.prestige);
            } //(check this)
        }

        if (player.prestige > 9) {
            level = 120;
            if (isNewSkill(skill)) {
                level = 1200;
            }

        }

        return level;
    }

    public boolean hasInfernalTools(Skill skillA, Skill skillB) {

        if (skillA == Skill.MINING || skillB == Skill.MINING) {
            return (player.getInventory().contains(213243) || player.getEquipment().contains(213243) ||
                    player.getInventory().contains(13661) || player.getEquipment().contains(13661)) &&
                    player.getSkillManager().getMaxLevel(skillA) >= 75 && player.getSkillManager().getMaxLevel(skillB) >= 75;
        } else if (skillA == Skill.FISHING || skillB == Skill.FISHING) {
            return (player.getInventory().contains(221031) || player.getEquipment().contains(221031) ||
                    player.getInventory().contains(13661) || player.getEquipment().contains(13661)) &&
                    player.getSkillManager().getMaxLevel(skillA) >= 75 && player.getSkillManager().getMaxLevel(skillB) >= 75;
        } else if (skillA == Skill.WOODCUTTING || skillB == Skill.WOODCUTTING) {
            return (player.getInventory().contains(213241) || player.getEquipment().contains(213241) ||
                    player.getInventory().contains(13661) || player.getEquipment().contains(13661)) &&
                    player.getSkillManager().getMaxLevel(skillA) >= 75 && player.getSkillManager().getMaxLevel(skillB) >= 75;
        }

        return false;

    }


    /**
     * Gets the current level for said skill.
     *
     * @param skill The skill to get current/temporary level for.
     * @return The skill's level.
     */
    public int getCurrentLevel(Skill skill) {
        return skills.level[skill.ordinal()];
    }

    /**
     * Gets the max level for said skill.
     *
     * @param skill The skill to get max level for.
     * @return The skill's maximum level.
     */
    public int getMaxLevel(Skill skill) {
        return skills.maxLevel[skill.ordinal()];
    }

    /**
     * Gets the max level for said skill.
     *
     * @param skill The skill to get max level for.
     * @return The skill's maximum level.
     */
    public int getMaxLevel(int skill) {
        return skills.maxLevel[skill];
    }

    /**
     * Gets the experience for said skill.
     *
     * @param skill The skill to get experience for.
     * @return The experience in said skill.
     */
    public int getExperience(Skill skill) {
        return skills.experience[skill.ordinal()];
    }

    /**
     * Sets the current level of said skill.
     *
     * @param skill   The skill to set current/temporary level for.
     * @param level   The level to set the skill to.
     * @param refresh If <code>true</code>, the skill's strings will be updated.
     * @return The Skills instance.
     */
    public SkillManager setCurrentLevel(Skill skill, int level, boolean refresh) {
        this.skills.level[skill.ordinal()] = level < 0 ? 0 : level;
        if (refresh) {
            updateSkill(skill);
        }

        return this;
    }

    /**
     * Sets the maximum level of said skill.
     *
     * @param skill   The skill to set maximum level for.
     * @param level   The level to set skill to.
     * @param refresh If <code>true</code>, the skill's strings will be updated.
     * @return The Skills instance.
     */
    public SkillManager setMaxLevel(Skill skill, int level, boolean refresh) {
        skills.maxLevel[skill.ordinal()] = level;
        if (refresh)
            updateSkill(skill);

        return this;
    }

    /**
     * Sets the experience of said skill.
     *
     * @param skill      The skill to set experience for.
     * @param experience The amount of experience to set said skill to.
     * @param refresh    If <code>true</code>, the skill's strings will be updated.
     * @return The Skills instance.
     */
    public SkillManager setExperience(Skill skill, int experience, boolean refresh) {
        this.skills.experience[skill.ordinal()] = experience < 0 ? 0 : experience;
        if (refresh)
            updateSkill(skill);
        return this;
    }

    /**
     * Sets the current level of said skill.
     *
     * @param skill The skill to set current/temporary level for.
     * @param level The level to set the skill to.
     * @return The Skills instance.
     */
    public SkillManager setCurrentLevel(Skill skill, int level) {
        setCurrentLevel(skill, level, true);
        return this;
    }

    /**
     * Sets the maximum level of said skill.
     *
     * @param skill The skill to set maximum level for.
     * @param level The level to set skill to.
     * @return The Skills instance.
     */
    public SkillManager setMaxLevel(Skill skill, int level) {
        setMaxLevel(skill, level, true);
        return this;
    }

    /**
     * Sets the experience of said skill.
     *
     * @param skill      The skill to set experience for.
     * @param experience The amount of experience to set said skill to.
     * @return The Skills instance.
     */
    public SkillManager setExperience(Skill skill, int experience) {
        setExperience(skill, experience, true);
        return this;
    }

    /**
     * The player associated with this Skills instance.
     */
    private final Player player;
    private Skills skills;
    private long totalGainedExp;

    public class Skills {

        public Skills() {
            level = new int[MAX_SKILLS];
            maxLevel = new int[MAX_SKILLS];
            experience = new int[MAX_SKILLS];
        }

        public int[] level;
        public int[] maxLevel;
        public int[] experience;

    }

    public Skills getSkills() {
        return skills;
    }

    public void setSkills(Skills skills) {
        this.skills = skills;
    }

    public long getTotalGainedExp() {
        return totalGainedExp;
    }

    public void setTotalGainedExp(long totalGainedExp) {
        this.totalGainedExp = totalGainedExp;
    }

    /**
     * The maximum amount of skills in the game.
     */
    public static final int MAX_SKILLS = 25;

    /**
     * The maximum amount of experience you can
     * achieve in a skill.
     */
    private static final int MAX_EXPERIENCE = 1000000000;

    private static final int EXPERIENCE_FOR_120 = 104273167;

    private static final int[] EXP_ARRAY = {
            0, 83, 174, 276, 388, 512, 650, 801, 969, 1154, 1358, 1584, 1833, 2107, 2411, 2746, 3115, 3523,
            3973, 4470, 5018, 5624, 6291, 7028, 7842, 8740, 9730, 10824, 12031, 13363, 14833, 16456, 18247,
            20224, 22406, 24815, 27473, 30408, 33648, 37224, 41171, 45529, 50339, 55649, 61512, 67983, 75127,
            83014, 91721, 101333, 111945, 123660, 136594, 150872, 166636, 184040, 203254, 224466, 247886,
            273742, 302288, 333804, 368599, 407015, 449428, 496254, 547953, 605032, 668051, 737627, 814445,
            899257, 992895, 1096278, 1210421, 1336443, 1475581, 1629200, 1798808, 1986068, 2192818, 2421087,
            2673114, 2951373, 3258594, 3597792, 3972294, 4385776, 4842295, 5346332, 5902831, 6517253, 7195629,
            7944614, 8771558, 9684577, 10692629, 11805606, 13034431, 14391160,
            15889109, 17542976, 19368992, 21385073, 23611006, 26068632, 28782069,
            31777943, 35085654, 38737661, 42769801, 47221641, 52136869,
            57563718, 63555443, 70170840, 77474828, 85539082, 94442737, 104273167
    };

}