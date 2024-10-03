package com.arlania.world.entity.impl.player;

import com.arlania.GameSettings;
import com.arlania.model.*;
import com.arlania.model.Locations.Location;
import com.arlania.model.RegionInstance.RegionInstanceType;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.*;
import com.arlania.world.content.globalevents.GlobalEventHandler;
import com.arlania.world.content.globalevents.GlobalEvent;
import com.arlania.world.content.interfaces.GambleInterface;
import com.arlania.world.content.interfaces.HUD;
import com.arlania.world.content.minigames.impl.gauntlet.GauntletRaid;
import com.arlania.world.content.minigames.impl.skilling.BlastFurnace;
import com.arlania.world.content.minigames.impl.theatreofblood.TobRaid;
import com.arlania.world.content.skill.impl.construction.House;
import com.arlania.world.content.skill.impl.summoning.SummoningTab;
import com.arlania.world.content.transportation.TeleportHandler;
import com.arlania.world.content.transportation.TeleportType;
import com.arlania.world.entity.impl.GroundItemManager;

public class PlayerProcess {

    /*
     * The player (owner) of this instance
     */
    private final Player player;

    /*
     * The loyalty tick, once this reaches 6, the player
     * will be given loyalty points.
     * 6 equals 3.6 seconds.
     */
    private int loyaltyTick;

    /*
     * The timer tick, once this reaches 2, the player's
     * total play time will be updated.
     * 2 equals 1.2 seconds.
     */
    private int timerTick;

    /*
     * The restoration tick, once this reaches 100,
     * the player will gain 1 hp.
     */
    private int restorationTick;


    /*
     * The minion tick, once this reaches 70,
     * a minion will spawn.
     */
    private int minionTick;

    /*
     * Makes sure ground items are spawned on height change
     */
    private int previousHeight;

    public PlayerProcess(Player player) {
        this.player = player;
        this.previousHeight = player.getPosition().getZ();
    }

    public void sequence() {

        /** SKILLS **/
        //if(player.shouldProcessFarming()) {
        //player.getFarming().sequence();
        //}
        /*if (player.inFFA) {
            player.getPacketSender().sendInteractionOption("Attack", 2, true);
        }*/
        if (!player.getCombatBuilder().isAttacking() && player.walkableInterfaceList.contains(41020)) {
            player.sendParallellInterfaceVisibility(41020, false);
        }

        /** MISC **/

        if (previousHeight != player.getPosition().getZ()) {
            GroundItemManager.handleRegionChange(player);
            previousHeight = player.getPosition().getZ();
        }

        if (!player.getActionTracker().isIdle(player) || player.aggressorTime > 0) {
            if (loyaltyTick >= 100) {
                LoyaltyProgramme.incrementPoints(player);
                loyaltyTick = 0;
                //player.getPacketSender().sendMessage(""+player.getTotalLoyaltyPointsEarned() + " " + Misc.getCurrentServerTime());
            }
            loyaltyTick++;
            player.activityTicks++;

            if (!player.inRandom && player.getStaffRights().getStaffRank() < StaffRights.MODERATOR.getStaffRank() && !player.busy() && !player.activeBonusRNG
                    && player.getLocation() != Location.BARROWS && player.getLocation() != Location.CERBERUS && player.getLocation() != Location.CORPOREAL_BEAST
                    && player.getLocation() != Location.DAGANNOTH_DUNGEON && player.getLocation() != Location.DAILY && player.getLocation() != Location.FIGHT_CAVES
                    //&& player.getLocation() != Location.GAUNTLET && player.getLocation() != Location.GODWARS_DUNGEON && player.getLocation() != Location.KALPHITE_QUEEN
                    && player.getLocation() != Location.WINTERTODT && player.getLocation() != Location.KALPHITE_QUEEN
                    && player.getLocation() != Location.KALPHITE_QUEEN && player.getLocation() != Location.KING_BLACK_DRAGON
                    && player.getLocation() != Location.ZULRAH && player.getLocation() != Location.GRAVEYARD && player.getLocation() != Location.GWD_RAID
                    && player.getLocation() != Location.INSTANCEDBOSSES && player.getLocation() != Location.CHAOS_RAIDS && player.getLocation() != Location.SHR) {
                player.setRandomEventTimer(player.getRandomEventTimer() + 1);

                if ((player.getLocation() != Location.WILDERNESS) &&
                        (player.getLocation() != Location.TEKTON) &&
                        (player.getLocation() != Location.NIGHTMARE) &&
                        (player.getLocation() != Location.SKELETAL_MYSTICS) &&
                        (player.getLocation() != Location.OLM) &&
                        (player.getLocation() != Location.RECIPE_FOR_DISASTER) &&
                        (player.getLocation() != Location.GRAVEYARD) &&
                        (player.getLocation() != Location.INSTANCEDBOSSES) &&
                        (player.getLocation() != Location.DAILY) &&
                        //(player.getLocation() != Location.DUEL_ARENA) &&
                        (player.getLocation() != Location.MAZE_RANDOM) &&
                        (player.getLocation() != Location.PESTILENT_BLOAT) &&
                        (player.getLocation() != Location.MAIDEN_SUGADINTI) &&
                        (player.getLocation() != Location.VERZIK_VITUR) &&
                        (player.getLocation() != Location.RAIDS_LOBBY) &&
                        (player.getLocation() != Location.INSTANCED_SLAYER) &&
                        (player.getLocation() != Location.SHR)) {
                    if (player.getRandomEventTimer() > (12000 + RandomUtility.inclusiveRandom(30000)))
                    //if(player.getRandomEventTimer() > (50))
                    {
                        if (player.getInteractingEntity() == null && player.aggressorTime < 1) {
                            player.makeIdle();
                            RandomEvent.randomStart(player);
                        }
                    }
                }
            }
        } else {
            player.makeIdle();
        }


        if (player.getSummoning().getFamiliar() != null) {
            if (player.getConstitution() < player.maxHealth()) {
                //bunyip
                if (player.getSummoning().getFamiliar().getSummonNpc().getId() == 6813 && player.summonHeal >= 25) {
                    player.performGraphic(new Graphic(1507));
                    player.heal(20);
                    player.summonHeal = 0;
                }
                //unicorn
                else if (player.getSummoning().getFamiliar().getSummonNpc().getId() == 6822 && player.summonHeal >= 25) {
                    player.performGraphic(new Graphic(1356));
                    player.heal(40);
                    player.summonHeal = 0;
                }
                player.summonHeal++;
            }

        }

        BlastFurnace.processBars(player);

        if (player.getBonusTime() < 1 && player.activeBonusXP) {
            player.activeBonusXP = false;
            player.getPacketSender().sendMessage("@red@You have no more bonus time!");
        }

        if (player.getLocation() == Location.DAILY) {
            player.dailyEvent--;

            if (player.dailyEvent < 1) {
                player.dailyEvent = 0;
                TeleportHandler.teleportPlayer(player, GameSettings.DEFAULT_POSITION, TeleportType.NORMAL);
            }

        }

        if (player.getOverloadPotionTimer() > 0) {
            int timer = player.getOverloadPotionTimer();
		
			/*if((timer == 600 || timer == 598 || timer == 596 || timer == 594 || timer == 592) && (player.Botanist == 0)) {
				player.performAnimation(new Animation(3170));
				player.dealDamage(new Hit(100, Hitmask.RED, CombatIcon.NONE));
			}*/

            double statBoost = .27;

            if (player.overloadPlus)
                statBoost += .08;
            if (GlobalEventHandler.effectActive(GlobalEvent.Effect.LOADED) || player.loadedEvent)
                statBoost += .05;

            if (timer % 10 == 0) {
                Consumables.overloadIncrease(player, Skill.ATTACK, statBoost);
                Consumables.overloadIncrease(player, Skill.STRENGTH, statBoost);
                Consumables.overloadIncrease(player, Skill.DEFENCE, statBoost);
                Consumables.overloadIncrease(player, Skill.RANGED, statBoost);
                Consumables.overloadIncrease(player, Skill.MAGIC, statBoost);
            }

            player.setOverloadPotionTimer(timer - 1);
            if (player.getOverloadPotionTimer() == 20)
                player.getPacketSender().sendMessage("@red@Your Overload's effect is about to run out.");

            if (player.getOverloadPotionTimer() <= 0) {
                player.getPacketSender().sendMessage("@red@Your Overload's effect has run out.");
                player.setOverloadPotionTimer(0);
                for (int i = 0; i < 7; i++) {
                    if (i == 3 || i == 5)
                        continue;
                    player.getSkillManager().setCurrentLevel(Skill.forId(i), player.getSkillManager().getMaxLevel(i));
                }
            }


        }

        if (player.aggressorTime > 0) {
            player.aggressorTime--;

            if (player.aggressorTime % 500 == 0) {
                player.getPacketSender().sendMessage("@red@You have " + player.aggressorTime / 100 + " minutes left on your Aggressor potion.");
            }

        }

        if (player.flashbackTime > 0) {
            player.flashbackTime--;

            if (player.flashbackTime % 500 == 0) {
                player.getPacketSender().sendMessage("@red@You have " + player.flashbackTime / 100 + " minutes left on your Flashback potion.");
            }
        }


        if (player.restorationTick >= 50) {
//			player.getPacketSender().sendString(39166, "@or2@Time played:  @yel@"+Misc.getTimePlayed((player.getTotalPlayTime() + player.getRecordedLogin().elapsed())));

            int heal = 10;
            int prayerRestore = 5;

            if (player.getEquipment().contains(11133)) {
                heal = 30;
                prayerRestore = 15;
            }

            if (player.healingEffects && player.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) + heal <= player.maxHealth()) {
                int max = player.maxHealth();

                if (heal + player.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) > max) {
                    player.getSkillManager().setCurrentLevel(Skill.CONSTITUTION, max);
                } else {
                    player.getSkillManager().setCurrentLevel(Skill.CONSTITUTION, player.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) + heal);
                }
            }

            if (player.getSkillManager().getCurrentLevel(Skill.PRAYER) + prayerRestore < player.maxPrayer()) {
                int max = player.maxPrayer();

                if (prayerRestore + player.getSkillManager().getCurrentLevel(Skill.PRAYER) > max) {
                    player.getSkillManager().setCurrentLevel(Skill.PRAYER, max);
                } else {
                    player.getSkillManager().setCurrentLevel(Skill.PRAYER, player.getSkillManager().getCurrentLevel(Skill.PRAYER) + prayerRestore);
                }
            }

            if (player.getLocation() == Location.TUTORIAL_ISLAND && player.tutorialIsland == 0) {
                Tutorial.newPlayer(player);
            }

            player.restorationTick = 0;
        }
        player.restorationTick++;


        if (player.getLocation() == Location.PESTILENT_BLOAT) {
            int height = player.getPosition().getZ();

            if ((minionTick >= 45) && (player.tobWave == 0)) {
                minionTick = 0;
                TobRaid.spawnMinion(player, height);
            }
            if (RandomUtility.inclusiveRandom(0, 1) == 1)
                minionTick++;
        }

        if (player.globalBossKills >= 25) {
            player.globalBossKills -= 25;

            if (player.getInventory().getFreeSlots() > 0) {
                player.getInventory().add(2685, 1);
                player.getPacketSender().sendMessage("You receive an Event Box in your inventory for 25 Boss kills!");
            } else {
                GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(2685, 1), player.getPosition().copy(), player.getUsername(), false, 150, true, 200));
                player.getPacketSender().sendMessage("You receive an Event Box on the ground for 50 Boss kills!");
            }
        }


        if (player.getLocation() == Location.MAIDEN_SUGADINTI) {
            int height = player.getPosition().getZ();

            if ((minionTick >= 30) && (player.tobWave == 1)) {
                minionTick = 0;
                TobRaid.spawnMinion(player, height);
            }
            minionTick++;
        }

        if (player.getLocation() == Location.VERZIK_VITUR) {
            int height = player.getPosition().getZ();

            if ((minionTick >= 30) && (player.tobWave == 2)) {
                minionTick = 0;
                TobRaid.spawnMinion(player, height);
            }
            minionTick++;
        }

        if (player.getWildernessLevel() == 40 && player.getPosition().getY() > 9800) {
            player.sendMessage("@red@Rev caves are currently under construction.");
            player.moveTo(GameSettings.DEFAULT_POSITION);
        }

        if (timerTick >= 1) {
//			player.getPacketSender().sendString(39166, "@or2@Time played:  @yel@"+Misc.getTimePlayed((player.getTotalPlayTime() + player.getRecordedLogin().elapsed())));
            timerTick = 0;
        }
        timerTick++;


        if (player.logins > 1) {
            player.dispose();
        }

        if (player.getSkillerSkill() == null && player.getSkiller().getSkillerTask() != null)
            player.setSkillerSkill(player.getSkiller().getSkillerTask().getSkill());


        //player.getPacketSender().sendString(114, "@bla@Overload Timer: " + player.getOverloadPotionTimer());
        //player.getPacketSender().sendWalkableInterface(368);


        if (player.getLocation() != Location.BLAST_FURNACE && player.getLocation() != Location.MOTHERLODE_MINE && player.getLocation() != Location.WINTERTODT)
            HUD.showHUD(player);


        if (player.Prodigy > 0 && player.prodigyDisplay && PerkHandler.canUseNormalPerks(player)) {
            for (Skill skill : Skill.values()) {

                int prodigyBonus = (player.Prodigy * 3) + 6;

                if ((skill != Skill.ATTACK) && (skill != Skill.STRENGTH) && (skill != Skill.DEFENCE) && (skill != Skill.MAGIC) && (skill != Skill.RANGED) && (skill != Skill.CONSTITUTION) && (skill != Skill.PRAYER) && (skill != Skill.SUMMONING)) {
                    int prodigy = player.getSkillManager().getMaxLevel(skill) + prodigyBonus;
                    int level = player.getSkillManager().getCurrentLevel(skill);

                    if ((player.Prodigy > 0) && (prodigy > level))
                        level = prodigy;
                    player.getSkillManager().setCurrentLevel(skill, level, true);
                } else {
                    int level = player.getSkillManager().getCurrentLevel(skill);
                    player.getSkillManager().setMaxLevel(skill, player.getSkillManager().getMaxLevel(skill));
                    player.getSkillManager().setCurrentLevel(skill, level, true);
                }
            }

        } else {
            for (Skill skill : Skill.values()) {
                int level = player.getSkillManager().getCurrentLevel(skill);
                player.getSkillManager().setMaxLevel(skill, player.getSkillManager().getMaxLevel(skill));
                player.getSkillManager().setCurrentLevel(skill, level, true);
            }
        }

        if (player.getLocation() == Location.WILDERNESS) {
            String wildyLevel = String.valueOf(player.getWildernessLevel());
            player.getPacketSender().sendString(25349, "");
            player.getPacketSender().sendString(25350, "");

            player.getPacketSender().sendString(25351, "Wilderness Level:");
            player.getPacketSender().sendString(25352, wildyLevel);

            player.getPacketSender().sendString(25353, "");
            player.getPacketSender().sendString(25354, "");

            int maxPvp = player.getSkillManager().getCombatLevel() + player.getWildernessLevel();
            int minPvp = player.getSkillManager().getCombatLevel() - player.getWildernessLevel();

            if (maxPvp > 126)
                maxPvp = 126;
            if (minPvp < 3)
                minPvp = 3;

            player.getPacketSender().sendString(25355, "Levels: " + minPvp + " - " + maxPvp);
        }


        if (((player.accelerateEvent && GlobalEventHandler.effectActive(GlobalEvent.Effect.ACCELERATE))) ||
                ((player.accuracyEvent && GlobalEventHandler.effectActive(GlobalEvent.Effect.ACCURACY))) ||
                ((player.doubleExpEvent && GlobalEventHandler.effectActive(GlobalEvent.Effect.DOUBLE_EXP))) ||
                ((player.droprateEvent && GlobalEventHandler.effectActive(GlobalEvent.Effect.DROP_RATE))) ||
                ((player.maxHitEvent && GlobalEventHandler.effectActive(GlobalEvent.Effect.MAX_HIT))) ||
                ((player.doubleBossPointEvent && GlobalEventHandler.effectActive(GlobalEvent.Effect.DOUBLE_BOSS_PP))) ||
                ((player.doubleLoot && GlobalEventHandler.effectActive(GlobalEvent.Effect.DOUBLE_LOOT))) ||
                ((player.doubleSkillerPointsEvent && GlobalEventHandler.effectActive(GlobalEvent.Effect.DOUBLE_SKILLER_PP))) ||
                ((player.doubleSlayerPointsEvent && GlobalEventHandler.effectActive(GlobalEvent.Effect.DOUBLE_SLAYER_PP))) ||
                ((player.eventBoxEvent && GlobalEventHandler.effectActive(GlobalEvent.Effect.EVENT_BOX))) ||
                ((player.bossKillsEvent && GlobalEventHandler.effectActive(GlobalEvent.Effect.GLOBAL_BOSS_KILLS))) ||
                ((player.loadedEvent && GlobalEventHandler.effectActive(GlobalEvent.Effect.LOADED)))) {

            if (player.personalEvent) {
                player.personalEvent = false;
                player.getPacketSender().sendMessage("@red@You can't have a Personal event running that matches a global.");
            }
        }


        if (player.getPoisonImmunity() > 0)
            player.setPoisonImmunity(player.getPoisonImmunity() - 1);

        if (player.getDragonfireImmunity() > 0)
            player.setDragonfireImmunity(player.getDragonfireImmunity() - 1);


        if (!player.masteryFix) {
            player.masteryPerksUnlocked = 0;
            player.Detective = 0;
            player.Critical = 0;
            player.Flurry = 0;
            player.Consistent = 0;
            player.Dexterity = 0;
            player.Stability = 0;
            player.Absorb = 0;
            player.Efficiency = 0;
            player.Efficacy = 0;
            player.Devour = 0;
            player.Wealthy = 0;
            player.Reflect = 0;
            player.masteryFix = true;
        }
		
		/*if(player.getEquipment().contains(220056))
		{
				Emotes.doEmote(player, 2769);
		}*/

        if (player.getStaffRights().getStaffRank() < 3) {
            player.autosupply = false;
            player.autobone = false;
            player.autokey = false;
        }


        if (player.eventPass) {
            if (player.isMaxExperience(player))
                player.epExperience = 250000000;
        }
        if (player.battlePass) {
            if (player.isMaxExperience(player))
                player.bpExperience = 250000000;
        }

        if (player.getLocation() == Location.CASINO)
            GambleInterface.refreshPanel(player);
        else
            PlayerPanel.refreshPanel(player);

        if (player.getSummoning().getFamiliar() != null) {
            if (!Locations.goodDistance(player.getSummoning().getFamiliar().getOwner().getPosition(), player.getSummoning().getFamiliar().getSummonNpc().getPosition(), 15)) {
                SummoningTab.callFollower(player);
            }
        }

        PersonalEvent.process(player);

        if (player.doubleExpEvent || GlobalEventHandler.effectActive(GlobalEvent.Effect.DOUBLE_EXP))
            player.activeDoubleXP = false;

        if (player.droprateEvent || GlobalEventHandler.effectActive(GlobalEvent.Effect.DROP_RATE))
            player.activeBonusRNG = false;

        if (player.activeBonusRNG) {
            player.setBonusTime(player.getBonusTime() - 1);

            if (player.getBonusTime() < 1 && player.activeBonusRNG) {
                player.activeBonusRNG = false;
                player.getPacketSender().sendMessage("@red@You have no more bonus time!");
            }
        }

        if (player.activeBonusXP) {
            player.setBonusTime(player.getBonusTime() - 1);

            if (player.getBonusTime() < 1 && player.activeBonusRNG) {
                player.activeBonusRNG = false;
                player.getPacketSender().sendMessage("@red@You have no more bonus time!");
            }
        }

        if (player.activeDoubleXP) {
            player.doubleExpTimer -= 1;

            if (player.doubleExpTimer < 1 && player.activeDoubleXP) {
                player.activeDoubleXP = false;
                player.getPacketSender().sendMessage("@red@You have no more Double XP!");
            }

        }

        InformationPanel.refreshPanel(player);
        //BountyHunter.sequence(player);

        if (player.getRegionInstance() != null && (player.getRegionInstance().getType() == RegionInstanceType.CONSTRUCTION_HOUSE || player.getRegionInstance().getType() == RegionInstanceType.CONSTRUCTION_DUNGEON)) {
            ((House) player.getRegionInstance()).process();
        }
    }
}
