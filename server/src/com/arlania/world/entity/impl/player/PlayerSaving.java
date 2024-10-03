package com.arlania.world.entity.impl.player;

import com.arlania.GameServer;
import com.arlania.model.GameMode;
import com.arlania.world.content.ClueScrolls;
import com.google.common.collect.Multiset.Entry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;


public class PlayerSaving {

    public static void save(Player player) {
        if (player.newPlayer())
            return;
        // Create the path and file objects.
        Path path = Paths.get(GameServer.getSaveDirectory(), player.getUsername() + ".json");
        File file = path.toFile();
        file.getParentFile().setWritable(true);

        // Attempt to make the player save directory if it doesn't
        // exist.
        if (!file.getParentFile().exists()) {
            try {
                file.getParentFile().mkdirs();
            } catch (SecurityException e) {
                GameServer.getLogger().log(Level.SEVERE, "Unable to create directory for player data!", e);
            }
        }
        try (FileWriter writer = new FileWriter(file)) {

            Gson builder = new GsonBuilder().setPrettyPrinting().create();
            JsonObject object = new JsonObject();
            object.addProperty("total-play-time-ms", player.getTotalPlayTime());
            object.addProperty("username", player.getUsername().trim());
            object.addProperty("passwordHash", new String(player.getPasswordHash(), StandardCharsets.UTF_8));
            object.addProperty("email", player.getEmailAddress() == null ? "null" : player.getEmailAddress().trim());
            object.addProperty("staff-rights", player.getStaffRights().name());
            object.addProperty("subscriptionType", player.getSubscription().name());
            object.addProperty("subscriptionStart", player.subscriptionStartDate);
            object.addProperty("subscriptionEnd", player.subscriptionEndDate);
            object.addProperty("game-mode", player.getGameMode().name());
            object.addProperty("boxScape", player.boxScape);
            object.addProperty("lastSeasonal", player.lastSeasonal);
            object.addProperty("linkedMain", player.linkedMain);
            object.addProperty("overrideAppearance", player.overrideAppearance);
            object.addProperty("seasonPoints", player.seasonPoints);

            if(player.getGameMode() == GameMode.SEASONAL_IRONMAN) {
                object.addProperty("seasonMonth", player.seasonMonth);
                object.addProperty("seasonYear", player.seasonYear);
                object.addProperty("seasonalTier", player.seasonalTier);

                object.add("seasonalPerks", builder.toJsonTree(Arrays.stream(player.seasonalPerks).toArray()));
                object.add("seasonalTrainingTeleports", builder.toJsonTree(Arrays.stream(player.seasonalTrainingTeleports).toArray()));
                object.add("seasonalDungeonTeleports", builder.toJsonTree(Arrays.stream(player.seasonalDungeonTeleports).toArray()));
                object.add("seasonalBossTeleports", builder.toJsonTree(Arrays.stream(player.seasonalBossTeleports).toArray()));
                object.add("seasonalMinigameTeleports", builder.toJsonTree(Arrays.stream(player.seasonalMinigameTeleports).toArray()));
                object.add("seasonalRaidsTeleports", builder.toJsonTree(Arrays.stream(player.seasonalRaidsTeleports).toArray()));

                object.addProperty("seasonalHarvester", player.Harvester);
                object.addProperty("seasonalProducer", player.Producer);
                object.addProperty("seasonalContender", player.Contender);
                object.addProperty("seasonalStrategist", player.Strategist);
                object.addProperty("seasonalGilded", player.Gilded);
                object.addProperty("seasonalShoplifter", player.Shoplifter);
                object.addProperty("seasonalImpulsive", player.Impulsive);
                object.addProperty("seasonalRapid", player.Rapid);
                object.addProperty("seasonalBloodthirsty", player.Bloodthirsty);
                object.addProperty("seasonalInfernal", player.Infernal);
                object.addProperty("seasonalSummoner", player.Summoner);
                object.addProperty("seasonalRuinous", player.Ruinous);
                object.addProperty("seasonalGladiator", player.Gladiator);
                object.addProperty("seasonalWarlord", player.Warlord);
                object.addProperty("seasonalDeathless", player.Deathless);
                object.addProperty("seasonalExecutioner", player.Executioner);

                object.addProperty("seasonalTeleportUnlocks", player.seasonalTeleportUnlocks);
                object.addProperty("seasonalPP", player.seasonalPP);
                object.addProperty("seasonalMBox", player.seasonalMBox);
                object.addProperty("seasonalEMBox", player.seasonalEMBox);
                object.addProperty("seasonalCrystalKeys", player.seasonalCrystalKeys);
                object.addProperty("seasonalSBox", player.seasonalSBox);
                object.addProperty("seasonalEventPass", player.seasonalEventPass);
                object.addProperty("seasonalRing", player.seasonalRing);
                object.addProperty("seasonalDonationTokens", player.seasonalDonationTokens);
            }

            object.addProperty("seasonalCurrency", player.seasonalCurrency);

            object.add("donatorPets", builder.toJsonTree(Arrays.stream(player.donatorPets).toArray()));
            object.add("activeDonatorPets", builder.toJsonTree(Arrays.stream(player.activeDonatorPets).toArray()));
            object.add("holidayPets", builder.toJsonTree(Arrays.stream(player.holidayPets).toArray()));

            object.addProperty("petStorageUpdate2", player.petStorageUpdate2);
            JsonObject jo = player.getPresets().toJson();
            object.add("presets", builder.toJsonTree(jo));

            object.addProperty("exprate", player.getexprate());
            object.addProperty("hometele", player.homeTele);
            object.addProperty("difficulty", player.difficulty);
            object.addProperty("prestige", player.prestige);
            object.addProperty("extraPrestiges", player.extraPrestiges);
            object.addProperty("perkUpgrades", player.perkUpgrades);
            object.addProperty("masteryPerksUnlocked", player.masteryPerksUnlocked);
            object.addProperty("masteryFix", player.masteryFix);
            object.addProperty("bingoCompletions", player.bingoCompletions);
            object.addProperty("paydirt", player.getPayDirt());
            object.addProperty("BForeType", player.BForeType);
            object.addProperty("BForeQty", player.BForeQty);
            object.addProperty("BFbarType", player.BFbarType);
            object.addProperty("BFbarQty", player.BFbarQty);
            object.addProperty("bonustime", player.getBonusTime());
            object.addProperty("rarecandy", player.getRareCandy());
            object.addProperty("candycredit", player.candyCredit);
            object.addProperty("dailyEvent", player.dailyEvent);
            object.addProperty("dailyFreebie", player.dailyFreebie);
            object.addProperty("monthlyFreebie", player.monthlyFreebie);
            object.addProperty("DailyKills", player.DailyKills);
            object.addProperty("paepoints", player.getPaePoints());
            object.addProperty("wildypoints", player.WildyPoints);
            object.addProperty("wildRisk", player.wildRisk);
            object.addProperty("wildBoost", player.wildBoost);
            object.addProperty("boostedDivinePool", player.boostedDivinePool);
            object.addProperty("totalxp", player.totalXP);
            object.addProperty("vote-points", player.getVotePoints());
            object.addProperty("boss-points", player.getBossPoints());

            object.addProperty("tutorialIsland", player.tutorialIsland);

            object.addProperty("worldFilterDrops", player.worldFilterDrops);
            object.addProperty("worldFilterEvents", player.worldFilterEvents);
            object.addProperty("worldFilterLevels", player.worldFilterLevels);
            object.addProperty("worldFilterMinigames", player.worldFilterMinigames);
            object.addProperty("worldFilterPVP", player.worldFilterPVP);
            object.addProperty("worldFilterReminders", player.worldFilterReminders);
            object.addProperty("worldFilterSeasonal", player.worldFilterSeasonal);
            object.addProperty("worldFilterStatus", player.worldFilterStatus);
            object.addProperty("worldFilterYell", player.worldFilterYell);
            object.addProperty("personalFilterPaepoints", player.personalFilterPaepoints);
            object.addProperty("personalFilterWildypoints", player.personalFilterWildypoints);
            object.addProperty("personalFilterBossKills", player.personalFilterBossKills);
            object.addProperty("personalFilterImp", player.personalFilterImp);
            object.addProperty("personalFilterBonecrusher", player.personalFilterBonecrusher);
            object.addProperty("personalFilterHerbicide", player.personalFilterHerbicide);
            object.addProperty("personalFilterMasteryTriggers", player.personalFilterMasteryTriggers);
            object.addProperty("personalFilterPouch", player.personalFilterPouch);
            object.addProperty("personalFilterKeyLoot", player.personalFilterKeyLoot);
            object.addProperty("personalFilterDirtBag", player.personalFilterDirtBag);
            object.addProperty("personalFilterCurses", player.personalFilterCurses);
            object.addProperty("personalFilterGathering", player.personalFilterGathering);
            object.addProperty("personalFilterAdze", player.personalFilterAdze);

            object.addProperty("swapMode", player.swapMode());

            object.addProperty("raid-points", player.getRaidPoints());
            object.addProperty("Learn-Augury", player.getLearnAugury());
            object.addProperty("Learn-Rigour", player.getLearnRigour());
            object.addProperty("Learn-Curses", player.getLearnCurses());
            object.addProperty("Learn-Ancients", player.getLearnAncients());
            object.addProperty("Learn-Lunars", player.getLearnLunars());
            object.addProperty("turmoilRanged", player.turmoilRanged);
            object.addProperty("turmoilMagic", player.turmoilMagic);
            object.addProperty("chatFilter", player.chatFilter);
            /*object.addProperty("slayerQty", player.slayerQty);
            object.addProperty("artisanQty", player.artisanQty);*/
            object.addProperty("achievementPoints", player.getAchievementPoints());

            object.addProperty("WildernessTeleports", player.getWildernessTeleports());
            object.addProperty("MinigameTeleports", player.getMinigameTeleports());

            object.addProperty("playerEventTimer", player.playerEventTimer);
            object.addProperty("prodigyDisplay", player.prodigyDisplay);
            object.addProperty("perkReset", player.perkReset);
            object.addProperty("passFix", player.passFix);

            object.addProperty("accelerateEvent", player.accelerateEvent);
            object.addProperty("maxHitEvent", player.maxHitEvent);
            object.addProperty("accuracyEvent", player.accuracyEvent);
            object.addProperty("bossKillsEvent", player.bossKillsEvent);
            object.addProperty("loadedEvent", player.loadedEvent);
            object.addProperty("doublexpEvent", player.doubleExpEvent);
            object.addProperty("doubleLoot", player.doubleLoot);
            object.addProperty("droprateEvent", player.droprateEvent);
            object.addProperty("doubleBossPointEvent", player.doubleBossPointEvent);
            object.addProperty("doubleSlayerPointsEvent", player.doubleSlayerPointsEvent);
            object.addProperty("doubleSkillerPointsEvent", player.doubleSkillerPointsEvent);
            object.addProperty("universalDropEvent", player.eventBoxEvent);

            object.addProperty("accelerateEventTimer", player.accelerateEventTimer);
            object.addProperty("maxHitEventTimer", player.maxHitEventTimer);
            object.addProperty("accuracyEventTimer", player.accuracyEventTimer);
            object.addProperty("bossKillsEventTimer", player.bossKillsEventTimer);
            object.addProperty("loadedEventTimer", player.loadedEventTimer);
            object.addProperty("doublexpEventTimer", player.doubleExpEventTimer);
            object.addProperty("doubleLootTimer", player.doubleLootTimer);
            object.addProperty("droprateEventTimer", player.droprateEventTimer);
            object.addProperty("doubleBossPointEventTimer", player.doubleBossPointEventTimer);
            object.addProperty("doubleSlayerPointsEventTimer", player.doubleSlayerPointsEventTimer);
            object.addProperty("doubleSkillerPointsEventTimer", player.doubleSkillerPointsEventTimer);
            object.addProperty("universalDropEventTimer", player.universalDropEventTimer);

            object.addProperty("coxRaidBonus", player.coxRaidBonus);
            object.addProperty("tobRaidBonus", player.tobRaidBonus);
            object.addProperty("gwdRaidBonus", player.gwdRaidBonus);
            object.addProperty("chaosRaidBonus", player.chaosRaidBonus);
            object.addProperty("shrRaidBonus", player.shrRaidBonus);
            object.addProperty("looterBanking", player.looterBanking);

            object.addProperty("trivia", player.trivia);
            object.addProperty("BattlePass", player.battlePass);
            object.addProperty("EventPass", player.eventPass);
            object.addProperty("MysteryPass", player.mysteryPass);
            object.addProperty("bpBosses", player.bpBossKills);
            object.addProperty("bpXP", player.bpExperience);
            object.addProperty("bpXPeasy", player.bpSkillEasy);
            object.addProperty("bpXPmedium", player.bpSkillMedium);
            object.addProperty("bpXPhard", player.bpSkillHard);
            object.addProperty("bpXPexpert", player.bpSkillExpert);
            object.addProperty("bpPVMeasy", player.bpBossEasy);
            object.addProperty("bpPVMmedium", player.bpBossMedium);
            object.addProperty("bpPVMhard", player.bpBossHard);
            object.addProperty("bpPVMexpert", player.bpBossExpert);
            object.addProperty("epBosses", player.epBossKills);
            object.addProperty("epXP", player.epExperience);
            object.addProperty("epXPeasy", player.epSkillEasy);
            object.addProperty("epXPmedium", player.epSkillMedium);
            object.addProperty("epXPhard", player.epSkillHard);
            object.addProperty("epXPexpert", player.epSkillExpert);
            object.addProperty("epPVMeasy", player.epBossEasy);
            object.addProperty("epPVMmedium", player.epBossMedium);
            object.addProperty("epPVMhard", player.epBossHard);
            object.addProperty("epPVMexpert", player.epBossExpert);
            object.addProperty("mpBosses", player.mpBossKills);
            object.addProperty("mpRaids", player.mpRaidsDone);
            object.addProperty("mpRaideasy", player.mpRaidEasy);
            object.addProperty("mpRaidmedium", player.mpRaidMedium);
            object.addProperty("mpRaidhard", player.mpRaidHard);
            object.addProperty("mpRaidexpert", player.mpRaidExpert);
            object.addProperty("mpPVMeasy", player.mpBossEasy);
            object.addProperty("mpPVMmedium", player.mpBossMedium);
            object.addProperty("mpPVMhard", player.mpBossHard);
            object.addProperty("mpPVMexpert", player.mpBossExpert);


            object.addProperty("AutoKey", player.autokey);
            object.addProperty("AutoBone", player.autobone);
            object.addProperty("AutoSupply", player.autosupply);
            object.addProperty("AlchableCoins", player.alchablecoins);
            object.addProperty("displayHUD", player.displayHUD);
            object.addProperty("hudColor", player.hudColor);

            object.addProperty("hudOvl", player.hudOvl);
            object.addProperty("hudFire", player.hudFire);
            object.addProperty("hudPoison", player.hudPoison);
            object.addProperty("hudBonus", player.hudBonus);
            object.addProperty("hudSlayer", player.hudSlayer);
            object.addProperty("hudSkiller", player.hudSkiller);
            object.addProperty("hudBPexp", player.hudBPexp);
            object.addProperty("hudBPkills", player.hudBPkills);
            object.addProperty("hudEPexp", player.hudEPexp);
            object.addProperty("hudEPkills", player.hudEPkills);
            object.addProperty("hudCballs", player.hudCballs);
            object.addProperty("hudChoices", player.hudChoices);

            object.addProperty("InRandom", player.inRandom);
            object.addProperty("randomx", player.randomx);
            object.addProperty("randomy", player.randomy);
            object.addProperty("randomz", player.randomz);
            object.addProperty("RandomEventTimer", player.getRandomEventTimer());
            object.addProperty("FluidStrikes", player.FluidStrike);
            object.addProperty("QuickShot", player.QuickShot);
            object.addProperty("DoubleCast", player.DoubleCast);
            object.addProperty("Survivalist", player.Survivalist);
            object.addProperty("TreasureSeeker", player.Looter);
            object.addProperty("UnnaturalSelection", player.Unnatural);
            object.addProperty("Botanist", player.Botanist);
            object.addProperty("AcceleratedAccrual", player.Accelerate);
            object.addProperty("DrainingStrikes", player.Vampirism);
            object.addProperty("WeaponSpecialist", player.Specialist);
            object.addProperty("Fabricator", player.Fabricator);
            object.addProperty("Prodigy", player.Prodigy);

            object.addProperty("Equilibrium", player.Equilibrium);
            object.addProperty("Flash", player.Flash);
            object.addProperty("Loaded", player.Loaded);
            object.addProperty("Lifelink", player.Lifelink);
            object.addProperty("Berserker", player.Berserker);
            object.addProperty("Bullseye", player.Bullseye);
            object.addProperty("Prophetic", player.Prophetic);
            object.addProperty("Lucky", player.Lucky);
            object.addProperty("Merchant", player.Merchant);
            object.addProperty("Artisan", player.Artisan);

            object.addProperty("Detective", player.Detective);
            object.addProperty("Critical", player.Critical);
            object.addProperty("Flurry", player.Flurry);
            object.addProperty("Consistent", player.Consistent);
            object.addProperty("Dexterity", player.Dexterity);
            object.addProperty("Stability", player.Stability);
            object.addProperty("Absorb", player.Absorb);
            object.addProperty("Efficiency", player.Efficiency);
            object.addProperty("Efficacy", player.Efficacy);
            object.addProperty("Devour", player.Devour);
            object.addProperty("Wealthy", player.Wealthy);
            object.addProperty("Reflect", player.Reflect);

            object.addProperty("wildAccelerate", player.wildAccelerate);
            object.addProperty("wildLooter", player.wildLooter);
            object.addProperty("wildSpecialist", player.wildSpecialist);
            object.addProperty("wildHoarder", player.wildHoarder);
            object.addProperty("wildVesta", player.wildVesta);
            object.addProperty("wildStatius", player.wildStatius);
            object.addProperty("wildMorrigan", player.wildMorrigan);
            object.addProperty("wildZuriel", player.wildZuriel);
            object.addProperty("wildSavior", player.wildSavior);
            object.addProperty("wildAlchemize", player.wildAlchemize);
            object.addProperty("wildEnraged", player.wildEnraged);
            object.addProperty("wildTainted", player.wildTainted);
            object.addProperty("wildBotanist", player.wildBotanist);

            object.addProperty("AncientPages", player.getAncientPages());
            object.addProperty("ArmadylPages", player.getArmadylPages());
            object.addProperty("BandosPages", player.getBandosPages());
            object.addProperty("GuthixPages", player.getGuthixPages());
            object.addProperty("SaradominPages", player.getSaradominPages());
            object.addProperty("ZamorakPages", player.getZamorakPages());


            object.addProperty("CrystalChests", player.CrystalChests);
            object.addProperty("TaintedChests", player.TaintedChests);
            object.addProperty("totalSlayerTasks", player.totalSlayerTasks);
            object.addProperty("totalSkillerTasks", player.totalSkillerTasks);

            object.addProperty("barrows", player.getCompletedBarrows());


            object.addProperty("coxcheck", player.coxCheck);
            object.addProperty("coxcount", player.coxCount);
            object.addProperty("coxremaining", player.coxRemaining);
            object.addProperty("tobcheck", player.tobCheck);
            object.addProperty("tobcount", player.tobCount);
            object.addProperty("tobremaining", player.tobRemaining);
            object.addProperty("chaoscheck", player.chaosCheck);
            object.addProperty("chaoscount", player.chaosCount);
            object.addProperty("chaosremaining", player.chaosRemaining);
            object.addProperty("achievementCheck", player.achievementCheck);


            object.addProperty("WTKC", player.WintertodtKC);
            object.addProperty("CoxKC", player.getCoxKC());
            object.addProperty("TobKC", player.getTobKC());
            object.addProperty("GauntletKC", player.GauntletKC);
            object.addProperty("GwdRaidKC", player.getGwdRaidKC());
            object.addProperty("chaosKC", player.getchaosKC());
            object.addProperty("shrKC", player.getshrKC());
            object.addProperty("ChaosRaid1", player.getChaosRaid1());
            object.addProperty("ChaosRaid2", player.getChaosRaid2());
            object.addProperty("ChaosRaid3", player.getChaosRaid3());
            object.addProperty("ChaosRaid4", player.getChaosRaid4());
            object.addProperty("ChaosRaid5", player.getChaosRaid5());
            object.addProperty("ChaosRaid6", player.getChaosRaid6());
            object.addProperty("ChaosRaid7", player.getChaosRaid7());
            object.addProperty("ChaosRaid8", player.getChaosRaid8());
            object.addProperty("ChaosRaid9", player.getChaosRaid9());
            object.addProperty("ChaosRaid10", player.getChaosRaid10());
            object.addProperty("CompletedChaos", player.getCompletedChaos());


            object.addProperty("daily", player.getDaily());
            object.addProperty("LastRecallx", player.getLastRecallx());
            object.addProperty("LastRecally", player.getLastRecally());
            object.addProperty("LastRecallz", player.getLastRecallz());
            object.addProperty("loyalty-title", player.getLoyaltyTitle().name());
            object.add("position", builder.toJsonTree(player.getPosition()));
            object.addProperty("online-status", player.getRelations().getStatus().name());
            object.addProperty("given-starter", player.didReceiveStarter());
            object.addProperty("christmas2021start", player.christmas2021start);
            object.addProperty("christmas2021complete", player.christmas2021complete);
            object.addProperty("rsog21", player.rsog21);
            object.addProperty("noobsown21", player.noobsown21);
            object.addProperty("walkchaos21", player.walkchaos21);
            object.addProperty("wr3ckedyou", player.wr3ckedyou);
            object.addProperty("ipkmaxjr", player.ipkmaxjr);
            object.addProperty("money-pouch", player.getMoneyInPouch());

            object.addProperty("amount-donated", player.getPointsHandler().getAmountDonated());
            object.addProperty("donator-points", player.getPointsHandler().getdonatorpoints());


            object.addProperty("minutes-bonus-exp", player.getMinutesBonusExp());
            object.addProperty("total-gained-exp", player.getSkillManager().getTotalGainedExp());
            object.addProperty("prestige-points", player.getPointsHandler().getPrestigePoints());
            object.addProperty("achievement-points", player.getPointsHandler().getAchievementPoints());
            object.addProperty("dung-tokens", player.getPointsHandler().getDungeoneeringTokens());
            object.addProperty("supplycaskets", player.getPointsHandler().getsupplycaskets());
            object.addProperty("bosscaskets", player.getPointsHandler().getbosscaskets());
            object.addProperty("openedpresents", player.getPointsHandler().getopenedpresents());
            object.addProperty("opened3rdage", player.getPointsHandler().getopened3rdage());
            object.addProperty("openedholiday", player.getPointsHandler().getopenedholiday());
            object.addProperty("loyalty-points", player.getPointsHandler().getLoyaltyPoints());
            object.addProperty("total-loyalty-points", player.getTotalLoyaltyPointsEarned());
            object.addProperty("totalbosskills", player.getPointsHandler().gettotalbosskills());
            object.addProperty("slayer-points", player.getPointsHandler().getSlayerPoints());
            object.addProperty("PC-points", player.getPointsHandler().getPCPoints());

            object.addProperty("halloween23", player.halloween23);

            object.addProperty("pk-points", player.getPointsHandler().getPkPoints());
            object.addProperty("trivia-points", player.getPointsHandler().getTriviaPoints());
            object.addProperty("cluescompleted", ClueScrolls.getCluesCompleted());

            object.addProperty("player-kills", player.getPlayerKillingAttributes().getPlayerKills());
            object.addProperty("player-killstreak", player.getPlayerKillingAttributes().getPlayerKillStreak());
            object.addProperty("player-deaths", player.getPlayerKillingAttributes().getPlayerDeaths());
            object.addProperty("target-percentage", player.getPlayerKillingAttributes().getTargetPercentage());
            object.addProperty("bh-rank", player.getAppearance().getBountyHunterSkull());
            object.addProperty("gender", player.getAppearance().getGender().name());
            object.addProperty("spell-book", player.getSpellbook().name());
            object.addProperty("prayer-book", player.getPrayerbook().name());
            object.addProperty("running", player.isRunning());
            object.addProperty("run-energy", player.getRunEnergy());
            object.addProperty("music", player.musicActive());
            object.addProperty("sounds", player.soundsActive());
            object.addProperty("aggressorTime", player.aggressorTime);
            object.addProperty("aggressorRadius", player.aggressorRadius);
            object.addProperty("flashbackTime", player.flashbackTime);
            object.addProperty("auto-retaliate", player.isAutoRetaliate());
            object.addProperty("xp-locked", player.experienceLocked());
            object.addProperty("veng-cast", player.hasVengeance());
            object.addProperty("last-veng", player.getLastVengeance().elapsed());
            object.addProperty("fight-type", player.getFightType().name());
            object.addProperty("sol-effect", player.getStaffOfLightEffect());
            object.addProperty("skull-timer", player.getSkullTimer());
            object.addProperty("accept-aid", player.isAcceptAid());
            object.addProperty("poison-damage", player.getPoisonDamage());
            object.addProperty("poison-immunity", player.getPoisonImmunity());
            object.addProperty("overload-timer", player.getOverloadPotionTimer());
            object.addProperty("overloadPlus", player.overloadPlus);
            object.addProperty("fire-immunity", player.getDragonfireImmunity());
            object.addProperty("prayer-renewal-timer", player.getPrayerRenewalPotionTimer());
            object.addProperty("teleblock-timer", player.getTeleblockTimer());
            object.addProperty("special-amount", player.getSpecialPercentage());
            object.addProperty("entered-gwd-room", player.getMinigameAttributes().getGodwarsDungeonAttributes().hasEnteredRoom());
            object.addProperty("gwd-altar-delay", player.getMinigameAttributes().getGodwarsDungeonAttributes().getAltarDelay());
            object.add("gwd-killcount", builder.toJsonTree(player.getMinigameAttributes().getGodwarsDungeonAttributes().getKillcount()));
            object.addProperty("mining-bot", player.getMinigameAttributes().getMiningBotAttributes().getHasMiningBot());
            object.addProperty("mining-bot-owed", player.getMinigameAttributes().getMiningBotAttributes().getResourcesLeftToCollect());
            object.addProperty("mining-bot-left", player.getMinigameAttributes().getMiningBotAttributes().getResourcesLeftToGather());
            object.addProperty("wcing-bot", player.getMinigameAttributes().getWCingBotAttributes().getHasWCingBot());
            object.addProperty("wcing-bot-owed", player.getMinigameAttributes().getWCingBotAttributes().getResourcesLeftToCollect());
            object.addProperty("wcing-bot-left", player.getMinigameAttributes().getWCingBotAttributes().getResourcesLeftToGather());
            object.addProperty("farming-bot", player.getMinigameAttributes().getFarmingBotAttributes().getHasFarmingBot());
            object.addProperty("farming-bot-owed", player.getMinigameAttributes().getFarmingBotAttributes().getResourcesLeftToCollect());
            object.addProperty("farming-bot-left", player.getMinigameAttributes().getFarmingBotAttributes().getResourcesLeftToGather());
            object.addProperty("fishing-bot", player.getMinigameAttributes().getFishingBotAttributes().getHasFishingBot());
            object.addProperty("fishing-bot-owed", player.getMinigameAttributes().getFishingBotAttributes().getResourcesLeftToCollect());
            object.addProperty("fishing-bot-left", player.getMinigameAttributes().getFishingBotAttributes().getResourcesLeftToGather());
            object.addProperty("effigy", player.getEffigy());
            object.addProperty("summon-npc", player.getSummoning().getFamiliar() != null ? player.getSummoning().getFamiliar().getSummonNpc().getId() : -1);
            object.addProperty("summon-death", player.getSummoning().getFamiliar() != null ? player.getSummoning().getFamiliar().getDeathTimer() : -1);
            object.addProperty("process-farming", player.shouldProcessFarming());
            object.addProperty("clanchat", player.getClanChatName() == null ? "null" : player.getClanChatName().trim());
            object.addProperty("autocast", player.isAutocast());
            object.addProperty("autocast-spell", player.getAutocastSpell() != null ? player.getAutocastSpell().spellId() : -1);
            object.addProperty("dfs-charges", player.getDfsCharges());


            object.addProperty("skiller-task", player.getSkiller().getSkillerTask().name());
            object.addProperty("prev-skiller-task", player.getSkiller().getLastTask().name());
            object.addProperty("skiller-task-amount", player.getSkiller().getAmountToSkill());
            object.addProperty("skiller-task-streak", player.getSkiller().getTaskStreak());

            object.addProperty("WeekOfYear", player.WeekOfYear);
            object.addProperty("doubleExpTimer", player.doubleExpTimer);

            object.addProperty("pet1", player.petStorage1);
            object.addProperty("pet2", player.petStorage2);
            object.addProperty("pet3", player.petStorage3);
            object.addProperty("pet4", player.petStorage4);
            object.addProperty("pet5", player.petStorage5);
            object.addProperty("pet6", player.petStorage6);
            object.addProperty("pet7", player.petStorage7);
            object.addProperty("pet8", player.petStorage8);
            object.addProperty("pet9", player.petStorage9);
            object.addProperty("pet10", player.petStorage10);
            object.addProperty("pet11", player.petStorage11);
            object.addProperty("pet12", player.petStorage12);
            object.addProperty("pet13", player.petStorage13);
            object.addProperty("pet14", player.petStorage14);
            object.addProperty("pet15", player.petStorage15);
            object.addProperty("pet16", player.petStorage16);
            object.addProperty("pet17", player.petStorage17);
            object.addProperty("pet18", player.petStorage18);
            object.addProperty("pet19", player.petStorage19);
            object.addProperty("pet20", player.petStorage20);
            object.addProperty("pet21", player.petStorage21);
            object.addProperty("pet22", player.petStorage22);
            object.addProperty("pet23", player.petStorage23);
            object.addProperty("pet24", player.petStorage24);
            object.addProperty("pet25", player.petStorage25);
            object.addProperty("pet26", player.petStorage26);
            object.addProperty("pet27", player.petStorage27);
            object.addProperty("pet28", player.petStorage28);
            object.addProperty("pet29", player.petStorage29);
            object.addProperty("pet30", player.petStorage30);
            object.addProperty("pet31", player.petStorage31);
            object.addProperty("pet32", player.petStorage32);
            object.addProperty("pet33", player.petStorage33);
            object.addProperty("pet34", player.petStorage34);
            object.addProperty("pet35", player.petStorage35);
            object.addProperty("pet36", player.petStorage36);
            object.addProperty("pet37", player.petStorage37);
            object.addProperty("pet38", player.petStorage38);
            object.addProperty("pet39", player.petStorage39);
            object.addProperty("pet40", player.petStorage40);
            object.addProperty("petCount", player.petCount);

            if (player.getBingo().isActive())
                object.add("bingo", player.getBingo().save());
            object.add("communityEvents24", builder.toJsonTree(player.communityEvents2024));
            object.addProperty("slayer-master", player.getSlayer().getSlayerMaster().name());
            object.addProperty("slayer-task", player.getSlayer().getSlayerTask().name());
            object.addProperty("prev-slayer-task", player.getSlayer().getLastTask().name());
            object.addProperty("task-amount", player.getSlayer().getAmountToSlay());
            object.addProperty("task-streak", player.getSlayer().getTaskStreak());
            object.addProperty("duo-partner",
                    player.getSlayer().getDuoPartner() == null ? "null" : player.getSlayer().getDuoPartner());
            object.addProperty("double-slay-xp", player.getSlayer().doubleSlayerXP);
            object.addProperty("recoil-deg", player.getRecoilCharges());
            object.add("brawler-deg", builder.toJsonTree(player.getBrawlerChargers()));
            object.add("pvp-deg", builder.toJsonTree(player.getPvpEquipmentChargers()));
            object.add("killed-players", builder.toJsonTree(player.getPlayerKillingAttributes().getKilledPlayers()));
            object.add("barrows-brother", builder.toJsonTree(player.getMinigameAttributes().getBarrowsMinigameAttributes().getBarrowsData()));
            object.addProperty("random-coffin", player.getMinigameAttributes().getBarrowsMinigameAttributes().getRandomCoffin());
            object.addProperty("barrows-killcount", player.getMinigameAttributes().getBarrowsMinigameAttributes().getKillcount());
            object.add("chaosraid-bosses", builder.toJsonTree(player.getMinigameAttributes().getChaosRaidMinigameAttributes().getChaosRaidData()));
            object.addProperty("chaosraid-killcount", player.getMinigameAttributes().getChaosRaidMinigameAttributes().getKillcount());
            object.add("nomad", builder.toJsonTree(player.getMinigameAttributes().getNomadAttributes().getQuestParts()));
            object.add("lightbearerOptions", builder.toJsonTree(player.lightbearerOptions));
            object.add("recipe-for-disaster", builder.toJsonTree(player.getMinigameAttributes().getRecipeForDisasterAttributes().getQuestParts()));
            object.addProperty("recipe-for-disaster-wave", player.getMinigameAttributes().getRecipeForDisasterAttributes().getWavesCompleted());
            object.add("dung-items-bound", builder.toJsonTree(player.getMinigameAttributes().getDungeoneeringAttributes().getBoundItems()));
            object.add("achievementAbilities", builder.toJsonTree(player.achievementAbilities));
            object.add("looterSettings", builder.toJsonTree(player.looterSettings));
            object.addProperty("magicPaperAmount", player.magicPaperAmount);
            object.addProperty("rune-ess", player.getStoredRuneEssence());
            object.addProperty("pure-ess", player.getStoredPureEssence());
            object.addProperty("CoalBag", player.getCoalBag());
            object.addProperty("RunePouchTypeOne", player.getRunePouchTypeOne());
            object.addProperty("RunePouchQtyOne", player.getRunePouchQtyOne());
            object.addProperty("RunePouchTypeTwo", player.getRunePouchTypeTwo());
            object.addProperty("RunePouchQtyTwo", player.getRunePouchQtyTwo());
            object.addProperty("RunePouchTypeThree", player.getRunePouchTypeThree());
            object.addProperty("RunePouchQtyThree", player.getRunePouchQtyThree());
            object.addProperty("RunePouchTypeFour", player.getRunePouchTypeFour());
            object.addProperty("RunePouchQtyFour", player.getRunePouchQtyFour());
            object.addProperty("BagSapphire", player.getBagSapphire());
            object.addProperty("BagEmerald", player.getBagEmerald());
            object.addProperty("BagRuby", player.getBagRuby());
            object.addProperty("BagDiamond", player.getBagDiamond());
            object.addProperty("BagDragonstone", player.getBagDragonstone());
            object.addProperty("kingdomDonation", player.kingdomDonation);
            object.addProperty("kingdomExperience", player.kingdomExperience);
            object.addProperty("kingdomKC", player.kingdomKC);
            object.addProperty("kingdomGatherer", player.kingdomGatherer);
            object.addProperty("ScytheCharges", player.getScytheCharges());
            object.addProperty("SanguinestiCharges", player.getSanguinestiCharges());
            object.addProperty("NightmareCharges", player.getNightmareCharges());
            object.addProperty("SurgeboxCharges", player.getSurgeboxCharges());
            object.addProperty("has-bank-pin", player.getBankPinAttributes().hasBankPin());
            object.addProperty("last-pin-attempt", player.getBankPinAttributes().getLastAttempt());
            object.addProperty("invalid-pin-attempts", player.getBankPinAttributes().getInvalidAttempts());
            object.add("bank-pin", builder.toJsonTree(player.getBankPinAttributes().getBankPin()));
            object.addProperty("placeholders", player.isPlaceholders());
            object.add("appearance", builder.toJsonTree(player.getAppearance().getLook()));
            object.add("agility-obj", builder.toJsonTree(player.getCrossedObstacles()));
            object.add("skills", builder.toJsonTree(player.getSkillManager().getSkills()));
            object.add("inventory", builder.toJsonTree(player.getInventory().getItems()));
            object.add("equipment", builder.toJsonTree(player.getEquipment().getItems()));
            object.add("overrides", builder.toJsonTree(player.getOverrides().getItems()));
            object.add("bank-0", builder.toJsonTree(player.getBank(0).getValidItems()));
            object.add("bank-1", builder.toJsonTree(player.getBank(1).getValidItems()));
            object.add("bank-2", builder.toJsonTree(player.getBank(2).getValidItems()));
            object.add("bank-3", builder.toJsonTree(player.getBank(3).getValidItems()));
            object.add("bank-4", builder.toJsonTree(player.getBank(4).getValidItems()));
            object.add("bank-5", builder.toJsonTree(player.getBank(5).getValidItems()));
            object.add("bank-6", builder.toJsonTree(player.getBank(6).getValidItems()));
            object.add("bank-7", builder.toJsonTree(player.getBank(7).getValidItems()));
            object.add("bank-8", builder.toJsonTree(player.getBank(8).getValidItems()));
            object.add("uim-bank", builder.toJsonTree(player.getUimBank().getValidItems()));

            object.add("mailbox", builder.toJsonTree(player.getMailBox().getValidItems()));

            object.add("ge-slots", builder.toJsonTree(player.getGrandExchangeSlots()));

            /** STORE SUMMON **/
            if (player.getSummoning().getBeastOfBurden() != null) {
                object.add("store", builder.toJsonTree(player.getSummoning().getBeastOfBurden().getValidItems()));
            }
            object.add("charm-imp", builder.toJsonTree(player.getSummoning().getCharmImpConfigs()));

            for (Entry<Integer> dartItem : player.getBlowpipeLoading().getContents().entrySet()) {
                object.addProperty("blowpipe-charge-item", dartItem.getElement());
                object.addProperty("blowpipe-charge-amount", player.getBlowpipeLoading().getContents().count(dartItem.getElement()));
            }

            object.add("friends", builder.toJsonTree(player.getRelations().getFriendList().toArray()));
            object.add("ignores", builder.toJsonTree(player.getRelations().getIgnoreList().toArray()));
            object.add("loyalty-titles", builder.toJsonTree(player.getUnlockedLoyaltyTitles()));
            object.add("deaths-coffer", builder.toJsonTree(player.getCofferData()));
            object.add("clogs", builder.toJsonTree(player.getCollectionLog().getCollections()));
            object.add("kills", builder.toJsonTree(player.getCollectionLog().getKillsTracker()));
            object.add("drops", builder.toJsonTree(player.getDropLog().toArray()));
            object.add("max-cape-colors", builder.toJsonTree(player.getMaxCapeColors()));
            object.addProperty("player-title", player.getTitle());
            object.add("achievements", player.getAchievementTracker().jsonSave());
            List<String> teleNames = player.getTeleportInterface().getFavourites();
            if (teleNames != null) {
                object.add("favourite-teles", builder.toJsonTree(teleNames));
            }

            writer.write(builder.toJson(object));
        } catch (Exception e) {
            // An error happened while saving.
            GameServer.getLogger().log(Level.WARNING, "An error has occured while saving a character file!", e);
        }
    }

    public static boolean playerExists(String p) {
        //p = Misc.formatPlayerName(p.toLowerCase());
        return new File(System.getProperty("user.home") + File.separator + "Saves" + File.separator + p + ".json").exists();
    }
}
