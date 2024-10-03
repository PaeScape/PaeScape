package com.arlania.world.entity.impl.player;

import com.arlania.engine.task.impl.FamiliarSpawnTask;
import com.arlania.model.*;
import com.arlania.model.PlayerRelations.PrivateChatStatus;
import com.arlania.model.container.impl.Bank;
import com.arlania.net.login.LoginDetailsMessage;
import com.arlania.net.login.LoginResponses;
import com.arlania.util.Misc;
import com.arlania.util.PasswordUtils;
import com.arlania.world.content.ClueScrolls;
import com.arlania.world.content.DonationBonds;
import com.arlania.world.content.DropLog;
import com.arlania.world.content.DropLog.DropLogEntry;
import com.arlania.world.content.LoyaltyProgramme.LoyaltyTitles;
import com.arlania.world.content.Presets;
import com.arlania.world.content.combat.magic.CombatSpells;
import com.arlania.world.content.combat.weapon.FightType;
import com.arlania.world.content.grandexchange.GrandExchangeSlot;
import com.arlania.world.content.skill.SkillManager.Skills;
import com.arlania.world.content.skill.impl.skiller.SkillerTasks;
import com.arlania.world.content.skill.impl.slayer.SlayerMaster;
import com.arlania.world.content.skill.impl.slayer.SlayerTasks;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PlayerLoading {

    public static Player getOfflinePlayer(String username) {
        username = Misc.formatText(username);
        Player loadedPlayer = new Player(null);
        loadedPlayer.setUsername(username);
        if (getResultWithoutLogin(loadedPlayer) == LoginResponses.LOGIN_SUCCESSFUL) {
            return loadedPlayer;
        }
        return null;
    }

    public static int getResultWithoutLogin(Player player) {
        return getResult(player, null);
    }

    public static int getResult(Player player, LoginDetailsMessage msg) {
        // Create the path and file objects.
        Path path = Paths.get(System.getProperty("user.home") + File.separator + "Saves" + File.separator, player.getUsername() + ".json");
        File file = path.toFile();

        // If the file doesn't exist, we're logging in for the first
        // time and can skip all of this.
        if (!file.exists()) {
            if (msg == null) {
                System.out.printf("Unable to load %s without login. Player does not exist.%n", player.getUsername());
                return LoginResponses.NEW_ACCOUNT;
            }
            player.setPasswordHash(PasswordUtils.hash(msg.getPassword()));
            return LoginResponses.NEW_ACCOUNT;
        }

        // Now read the properties from the json parser.
        try (FileReader fileReader = new FileReader(file)) {
            Gson builder = new GsonBuilder().create();
            JsonObject reader = (JsonObject) JsonParser.parseReader(fileReader);

            if (reader.has("total-play-time-ms")) {
                player.setTotalPlayTime(reader.get("total-play-time-ms").getAsLong());
            }

            if (reader.has("username")) {
                player.setUsername(reader.get("username").getAsString());
            }

            if (reader.has("passwordHash")) {
                player.setPasswordHash(reader.get("passwordHash").getAsString().getBytes(StandardCharsets.UTF_8));
                if (msg != null && !PasswordUtils.verifyAndUpdateHash(player, msg.getPassword())) {
                    return LoginResponses.LOGIN_INVALID_CREDENTIALS;
                }
            }

            if (reader.has("lastSeasonal")) {
                player.lastSeasonal = reader.get("lastSeasonal").getAsString();
            }

            if (reader.has("linkedMain")) {
                player.linkedMain = reader.get("linkedMain").getAsString();
            }

            if (reader.has("overrideAppearance")) {
                player.overrideAppearance = reader.get("overrideAppearance").getAsInt();
            }

            if (reader.has("seasonMonth")) {
                player.seasonMonth = reader.get("seasonMonth").getAsInt();
            }
            if (reader.has("seasonYear")) {
                player.seasonYear = reader.get("seasonYear").getAsInt();
            }
            if (reader.has("seasonPoints")) {
                player.seasonPoints = reader.get("seasonPoints").getAsInt();
            }
            if (reader.has("baseLevelGoal")) {
                player.baseLevelGoal = reader.get("baseLevelGoal").getAsInt();
            }
            if (reader.has("seasonalTier")) {
                player.seasonalTier = reader.get("seasonalTier").getAsInt();
            }
            if (reader.has("seasonalCurrency")) {
                player.seasonalCurrency = reader.get("seasonalCurrency").getAsInt();
            }

            if (reader.has("seasonalPerks")) {
                player.seasonalPerks = builder.fromJson(reader.get("seasonalPerks").getAsJsonArray(), int[].class);
            }
            if (reader.has("seasonalTrainingTeleports")) {
                player.seasonalTrainingTeleports = builder.fromJson(reader.get("seasonalTrainingTeleports").getAsJsonArray(), int[].class);
            }
            if (reader.has("seasonalDungeonTeleports")) {
                player.seasonalDungeonTeleports = builder.fromJson(reader.get("seasonalDungeonTeleports").getAsJsonArray(), int[].class);
            }
            if (reader.has("seasonalBossTeleports")) {
                player.seasonalBossTeleports = builder.fromJson(reader.get("seasonalBossTeleports").getAsJsonArray(), int[].class);
            }
            if (reader.has("seasonalMinigameTeleports")) {
                player.seasonalMinigameTeleports = builder.fromJson(reader.get("seasonalMinigameTeleports").getAsJsonArray(), int[].class);
            }
            if (reader.has("seasonalRaidsTeleports")) {
                player.seasonalRaidsTeleports = builder.fromJson(reader.get("seasonalRaidsTeleports").getAsJsonArray(), int[].class);
            }

            if (reader.has("seasonalHarvester")) {
                player.Harvester = reader.get("seasonalHarvester").getAsBoolean();
            }
            if (reader.has("seasonalProducer")) {
                player.Producer = reader.get("seasonalProducer").getAsBoolean();
            }
            if (reader.has("seasonalContender")) {
                player.Contender = reader.get("seasonalContender").getAsBoolean();
            }
            if (reader.has("seasonalHarvester")) {
                player.Strategist = reader.get("seasonalStrategist").getAsBoolean();
            }
            if (reader.has("seasonalGilded")) {
                player.Gilded = reader.get("seasonalGilded").getAsBoolean();
            }
            if (reader.has("seasonalHarvester")) {
                player.Shoplifter = reader.get("seasonalShoplifter").getAsBoolean();
            }
            if (reader.has("seasonalImpulsive")) {
                player.Impulsive = reader.get("seasonalImpulsive").getAsBoolean();
            }
            if (reader.has("seasonalRapid")) {
                player.Rapid = reader.get("seasonalRapid").getAsBoolean();
            }
            if (reader.has("seasonalBloodthirsty")) {
                player.Bloodthirsty = reader.get("seasonalBloodthirsty").getAsBoolean();
            }
            if (reader.has("seasonalInfernal")) {
                player.Infernal = reader.get("seasonalInfernal").getAsBoolean();
            }
            if (reader.has("seasonalSummoner")) {
                player.Summoner = reader.get("seasonalSummoner").getAsBoolean();
            }
            if (reader.has("seasonalRuinous")) {
                player.Ruinous = reader.get("seasonalRuinous").getAsBoolean();
            }
            if (reader.has("seasonalGladiator")) {
                player.Gladiator = reader.get("seasonalGladiator").getAsBoolean();
            }
            if (reader.has("seasonalWarlord")) {
                player.Warlord = reader.get("seasonalWarlord").getAsBoolean();
            }
            if (reader.has("seasonalDeathless")) {
                player.Deathless = reader.get("seasonalDeathless").getAsBoolean();
            }
            if (reader.has("seasonalExecutioner")) {
                player.Executioner = reader.get("seasonalExecutioner").getAsBoolean();
            }

            if (reader.has("seasonalTeleportUnlocks")) {
                player.seasonalTeleportUnlocks = reader.get("seasonalTeleportUnlocks").getAsInt();
            }
            if (reader.has("seasonalPP")) {
                player.seasonalPP = reader.get("seasonalPP").getAsInt();
            }
            if (reader.has("seasonalMBox")) {
                player.seasonalMBox = reader.get("seasonalMBox").getAsInt();
            }
            if (reader.has("seasonalEMBox")) {
                player.seasonalEMBox = reader.get("seasonalEMBox").getAsInt();
            }
            if (reader.has("seasonalCrystalKeys")) {
                player.seasonalCrystalKeys = reader.get("seasonalCrystalKeys").getAsInt();
            }
            if (reader.has("seasonalSBox")) {
                player.seasonalSBox = reader.get("seasonalSBox").getAsInt();
            }
            if (reader.has("seasonalEventPass")) {
                player.seasonalEventPass = reader.get("seasonalEventPass").getAsInt();
            }
            if (reader.has("seasonalRing")) {
                player.seasonalRing = reader.get("seasonalRing").getAsInt();
            }
            if (reader.has("seasonalDonationTokens")) {
                player.seasonalDonationTokens = reader.get("seasonalDonationTokens").getAsInt();
            }



            if (reader.has("donatorPets")) {
                player.donatorPets = builder.fromJson(reader.get("donatorPets").getAsJsonArray(), int[].class);
            }
            if (reader.has("activeDonatorPets")) {
                player.activeDonatorPets = builder.fromJson(reader.get("activeDonatorPets").getAsJsonArray(), int[].class);
            }
            if (reader.has("holidayPets")) {
                player.holidayPets = builder.fromJson(reader.get("holidayPets").getAsJsonArray(), int[].class);
            }

            if (reader.has("bingoCompletions")) {
                player.bingoCompletions = reader.get("bingoCompletions").getAsInt();
            }

            if (reader.has("petStorageUpdate2")) {
                player.petStorageUpdate2 = reader.get("petStorageUpdate2").getAsBoolean();
            }

            if (reader.has("presets")) {
                player.setPresets(Presets.loadJson(reader.getAsJsonObject("presets")));
            }

            if (reader.has("hometele")) {
                player.homeTele = reader.get("hometele").getAsString();
            }

            if (reader.has("tutorialIsland")) {
                player.tutorialIsland = reader.get("tutorialIsland").getAsInt();
            }

            if (reader.has("boss-points")) {
                player.setBossPoints(reader.get("boss-points").getAsInt());
            }

            if (reader.has("worldFilterDrops")) {
                player.worldFilterDrops = reader.get("worldFilterDrops").getAsBoolean();
            }
            if (reader.has("worldFilterReminders")) {
                player.worldFilterReminders = reader.get("worldFilterReminders").getAsBoolean();
            }
            if (reader.has("worldFilterLevels")) {
                player.worldFilterLevels = reader.get("worldFilterLevels").getAsBoolean();
            }
            if (reader.has("worldFilterMinigames")) {
                player.worldFilterMinigames = reader.get("worldFilterMinigames").getAsBoolean();
            }
            if (reader.has("worldFilterYell")) {
                player.worldFilterYell = reader.get("worldFilterYell").getAsBoolean();
            }
            if (reader.has("worldFilterEvents")) {
                player.worldFilterEvents = reader.get("worldFilterEvents").getAsBoolean();
            }
            if (reader.has("worldFilterStatus")) {
                player.worldFilterStatus = reader.get("worldFilterStatus").getAsBoolean();
            }
            if (reader.has("worldFilterPVP")) {
                player.worldFilterPVP = reader.get("worldFilterPVP").getAsBoolean();
            }
            if (reader.has("worldFilterSeasonal")) {
                player.worldFilterSeasonal = reader.get("worldFilterSeasonal").getAsBoolean();
            }
            if (reader.has("personalFilterPaepoints")) {
                player.personalFilterPaepoints = reader.get("personalFilterPaepoints").getAsBoolean();
            }
            if (reader.has("personalFilterWildypoints")) {
                player.personalFilterWildypoints = reader.get("personalFilterWildypoints").getAsBoolean();
            }
            if (reader.has("personalFilterBossKills")) {
                player.personalFilterBossKills = reader.get("personalFilterBossKills").getAsBoolean();
            }
            if (reader.has("personalFilterImp")) {
                player.personalFilterImp = reader.get("personalFilterImp").getAsBoolean();
            }
            if (reader.has("personalFilterHerbicide")) {
                player.personalFilterHerbicide = reader.get("personalFilterHerbicide").getAsBoolean();
            }
            if (reader.has("personalFilterBonecrusher")) {
                player.personalFilterBonecrusher = reader.get("personalFilterBonecrusher").getAsBoolean();
            }
            if (reader.has("personalFilterMasteryTriggers")) {
                player.personalFilterMasteryTriggers = reader.get("personalFilterMasteryTriggers").getAsBoolean();
            }
            if (reader.has("personalFilterPouch")) {
                player.personalFilterPouch = reader.get("personalFilterPouch").getAsBoolean();
            }
            if (reader.has("personalFilterKeyLoot")) {
                player.personalFilterKeyLoot = reader.get("personalFilterKeyLoot").getAsBoolean();
            }
            if (reader.has("personalFilterDirtBag")) {
                player.personalFilterDirtBag = reader.get("personalFilterDirtBag").getAsBoolean();
            }
            if (reader.has("personalFilterCurses")) {
                player.personalFilterCurses = reader.get("personalFilterCurses").getAsBoolean();
            }
            if (reader.has("personalFilterGathering")) {
                player.personalFilterGathering = reader.get("personalFilterGathering").getAsBoolean();
            }
            if (reader.has("personalFilterAdze")) {
                player.personalFilterAdze = reader.get("personalFilterAdze").getAsBoolean();
            }

            if (reader.has("swapMode")) {
                player.setSwapMode(reader.get("swapMode").getAsBoolean());
            }

			/*if (reader.has("petStorage")) {
				player.petStorage = reader.get("petStorage").getAsInt();
			}*/
            if (reader.has("paydirt")) {
                player.setPayDirt(reader.get("paydirt").getAsInt());
            }
            if (reader.has("BForeType")) {
                player.BForeType = reader.get("BForeType").getAsInt();
            }
            if (reader.has("BForeQty")) {
                player.BForeQty = reader.get("BForeQty").getAsInt();
            }
            if (reader.has("BFbarType")) {
                player.BFbarType = reader.get("BFbarType").getAsInt();
            }
            if (reader.has("BFbarQty")) {
                player.BFbarQty = reader.get("BFbarQty").getAsInt();
            }
            if (reader.has("WeekOfYear")) {
                player.WeekOfYear = reader.get("WeekOfYear").getAsInt();
            }
            if (reader.has("doubleExpTimer")) {
                player.doubleExpTimer = reader.get("doubleExpTimer").getAsInt();
            }
            if (reader.has("bonustime")) {
                player.setBonusTime(reader.get("bonustime").getAsInt());
            }
            if (reader.has("rarecandy")) {
                player.setRareCandy(reader.get("rarecandy").getAsInt());
            }
            if (reader.has("candycredit")) {
                player.candyCredit = reader.get("candycredit").getAsInt();
            }
            if (reader.has("dailyEvent")) {
                player.dailyEvent = reader.get("dailyEvent").getAsInt();
            }
            if (reader.has("dailyFreebie")) {
                player.dailyFreebie = reader.get("dailyFreebie").getAsInt();
            }
            if (reader.has("monthlyFreebie")) {
                player.monthlyFreebie = reader.get("monthlyFreebie").getAsInt();
            }
            if (reader.has("DailyKills")) {
                player.DailyKills = reader.get("DailyKills").getAsInt();
            }
            if (reader.has("totalSlayerTasks")) {
                player.totalSlayerTasks = reader.get("totalSlayerTasks").getAsInt();
            }
            if (reader.has("totalSkillerTasks")) {
                player.totalSkillerTasks = reader.get("totalSkillerTasks").getAsInt();
            }
            if (reader.has("paepoints")) {
                player.setPaePoints(reader.get("paepoints").getAsInt());
            }
            if (reader.has("wildypoints")) {
                player.WildyPoints = reader.get("wildypoints").getAsInt();
            }
            if (reader.has("wildRisk")) {
                player.wildRisk = reader.get("wildRisk").getAsInt();
            }
            if (reader.has("wildBoost")) {
                player.wildBoost = reader.get("wildBoost").getAsInt();
            }
            if (reader.has("boostedDivinePool")) {
                player.boostedDivinePool = reader.get("boostedDivinePool").getAsBoolean();
            }
            if (reader.has("totalxp")) {
                player.totalXP = reader.get("totalxp").getAsDouble();
            }
            if (reader.has("vote-points")) {
                player.setVotePoints(reader.get("vote-points").getAsInt());
            }
            if (reader.has("raid-points")) {
                player.setRaidPoints(reader.get("raid-points").getAsInt());
            }
            if (reader.has("Learn-Augury")) {
                player.setLearnAugury(reader.get("Learn-Augury").getAsInt());
            }
            if (reader.has("Learn-Rigour")) {
                player.setLearnRigour(reader.get("Learn-Rigour").getAsInt());
            }
            if (reader.has("Learn-Curses")) {
                player.setLearnCurses(reader.get("Learn-Curses").getAsInt());
            }
            if (reader.has("Learn-Lunars")) {
                player.setLearnLunars(reader.get("Learn-Lunars").getAsInt());
            }
            if (reader.has("Learn-Ancients")) {
                player.setLearnAncients(reader.get("Learn-Ancients").getAsInt());
            }
            if (reader.has("turmoilRanged")) {
                player.turmoilRanged = reader.get("turmoilRanged").getAsBoolean();
            }
            if (reader.has("turmoilMagic")) {
                player.turmoilMagic = reader.get("turmoilMagic").getAsBoolean();
            }
            if (reader.has("chatFilter")) {
                player.chatFilter = reader.get("Learn-Lunars").getAsBoolean();
            }
            if (reader.has("BossTeleports")) {
                player.setBossTeleports(reader.get("BossTeleports").getAsInt());
            }
            if (reader.has("MinigameTeleports")) {
                player.setMinigameTeleports(reader.get("MinigameTeleports").getAsInt());
            }
            if (reader.has("WildernessTeleports")) {
                player.setWildernessTeleports(reader.get("WildernessTeleports").getAsInt());
            }
            /*if (reader.has("slayerQty")) {
                player.slayerQty = (reader.get("slayerQty").getAsString());
            }
            if (reader.has("artisanQty")) {
                player.artisanQty = (reader.get("artisanQty").getAsString());
            }*/
            if (reader.has("achievementPoints")) {
                player.setAchievementPoints(reader.get("achievementPoints").getAsInt());
            }
            if (reader.has("playerEventTimer")) {
                player.playerEventTimer = (reader.get("playerEventTimer").getAsInt());
            }
            if (reader.has("prodigyDisplay")) {
                player.prodigyDisplay = (reader.get("prodigyDisplay").getAsBoolean());
            }
            if (reader.has("perkReset")) {
                player.perkReset = (reader.get("perkReset").getAsBoolean());
            }
            if (reader.has("passFix")) {
                player.passFix = (reader.get("passFix").getAsBoolean());
            }


            if (reader.has("accelerateEventTimer")) {
                player.accelerateEventTimer = (reader.get("accelerateEventTimer").getAsInt());
            }
            if (reader.has("maxHitEventTimer")) {
                player.maxHitEventTimer = (reader.get("maxHitEventTimer").getAsInt());
            }
            if (reader.has("accuracyEventTimer")) {
                player.accuracyEventTimer = (reader.get("accuracyEventTimer").getAsInt());
            }
            if (reader.has("bossKillsEventTimer")) {
                player.bossKillsEventTimer = (reader.get("bossKillsEventTimer").getAsInt());
            }
            if (reader.has("loadedEventTimer")) {
                player.loadedEventTimer = (reader.get("loadedEventTimer").getAsInt());
            }
            if (reader.has("doublexpEventTimer")) {
                player.doubleExpEventTimer = (reader.get("doublexpEventTimer").getAsInt());
            }
            if (reader.has("doubleLootTimer")) {
                player.doubleLootTimer = (reader.get("doubleLootTimer").getAsInt());
            }
            if (reader.has("droprateEventTimer")) {
                player.droprateEventTimer = (reader.get("droprateEventTimer").getAsInt());
            }
            if (reader.has("doubleBossPointEventTimer")) {
                player.doubleBossPointEventTimer = (reader.get("doubleBossPointEventTimer").getAsInt());
            }
            if (reader.has("doubleSlayerPointsEventTimer")) {
                player.doubleSlayerPointsEventTimer = (reader.get("doubleSlayerPointsEventTimer").getAsInt());
            }
            if (reader.has("doubleSkillerPointsEventTimer")) {
                player.doubleSkillerPointsEventTimer = (reader.get("doubleSkillerPointsEventTimer").getAsInt());
            }
            if (reader.has("universalDropEventTimer")) {
                player.universalDropEventTimer = (reader.get("universalDropEventTimer").getAsInt());
            }

            if (reader.has("trivia")) {
                player.trivia = (reader.get("trivia").getAsInt());
            }
            if (reader.has("MysteryPass")) {
                player.mysteryPass = (reader.get("MysteryPass").getAsBoolean());
            }
            if (reader.has("EventPass")) {
                player.eventPass = (reader.get("EventPass").getAsBoolean());
            }
            if (reader.has("BattlePass")) {
                player.battlePass = (reader.get("BattlePass").getAsBoolean());
            }
            if (reader.has("christmas2021start")) {
                player.christmas2021start = (reader.get("christmas2021start").getAsBoolean());
            }
            if (reader.has("christmas2021complete")) {
                player.christmas2021complete = (reader.get("christmas2021complete").getAsBoolean());
            }
            if (reader.has("rsog21")) {
                player.rsog21 = (reader.get("rsog21").getAsBoolean());
            }
            if (reader.has("noobsown21")) {
                player.noobsown21 = (reader.get("noobsown21").getAsBoolean());
            }
            if (reader.has("walkchaos21")) {
                player.walkchaos21 = (reader.get("walkchaos21").getAsBoolean());
            }
            if (reader.has("wr3ckedyou")) {
                player.wr3ckedyou = (reader.get("wr3ckedyou").getAsBoolean());
            }
            if (reader.has("ipkmaxjr")) {
                player.ipkmaxjr = (reader.get("ipkmaxjr").getAsBoolean());
            }
            if (reader.has("bpBosses")) {
                player.bpBossKills = (reader.get("bpBosses").getAsInt());
            }
            if (reader.has("bpXP")) {
                player.bpExperience = (reader.get("bpXP").getAsInt());
            }
            if (reader.has("bpXPeasy")) {
                player.bpSkillEasy = (reader.get("bpXPeasy").getAsBoolean());
            }
            if (reader.has("bpXPmedium")) {
                player.bpSkillMedium = (reader.get("bpXPmedium").getAsBoolean());
            }
            if (reader.has("bpXPhard")) {
                player.bpSkillHard = (reader.get("bpXPhard").getAsBoolean());
            }
            if (reader.has("bpXPexpert")) {
                player.bpSkillExpert = (reader.get("bpXPexpert").getAsBoolean());
            }
            if (reader.has("bpPVMeasy")) {
                player.bpBossEasy = (reader.get("bpPVMeasy").getAsBoolean());
            }
            if (reader.has("bpPVMmedium")) {
                player.bpBossMedium = (reader.get("bpPVMmedium").getAsBoolean());
            }
            if (reader.has("bpPVMhard")) {
                player.bpBossHard = (reader.get("bpPVMhard").getAsBoolean());
            }
            if (reader.has("bpPVMexpert")) {
                player.bpBossExpert = (reader.get("bpPVMexpert").getAsBoolean());
            }
            if (reader.has("epBosses")) {
                player.epBossKills = (reader.get("epBosses").getAsInt());
            }
            if (reader.has("epXP")) {
                player.epExperience = (reader.get("epXP").getAsInt());
            }
            if (reader.has("epXPeasy")) {
                player.epSkillEasy = (reader.get("epXPeasy").getAsBoolean());
            }
            if (reader.has("epXPmedium")) {
                player.epSkillMedium = (reader.get("epXPmedium").getAsBoolean());
            }
            if (reader.has("epXPhard")) {
                player.epSkillHard = (reader.get("epXPhard").getAsBoolean());
            }
            if (reader.has("epXPexpert")) {
                player.epSkillExpert = (reader.get("epXPexpert").getAsBoolean());
            }
            if (reader.has("epPVMeasy")) {
                player.epBossEasy = (reader.get("epPVMeasy").getAsBoolean());
            }
            if (reader.has("epPVMmedium")) {
                player.epBossMedium = (reader.get("epPVMmedium").getAsBoolean());
            }
            if (reader.has("epPVMhard")) {
                player.epBossHard = (reader.get("epPVMhard").getAsBoolean());
            }
            if (reader.has("epPVMexpert")) {
                player.epBossExpert = (reader.get("epPVMexpert").getAsBoolean());
            }

            if (reader.has("mpBosses")) {
                player.mpBossKills = (reader.get("mpBosses").getAsInt());
            }
            if (reader.has("mpRaids")) {
                player.mpRaidsDone = (reader.get("mpRaids").getAsInt());
            }
            if (reader.has("mpRaideasy")) {
                player.mpRaidEasy = (reader.get("mpRaideasy").getAsBoolean());
            }
            if (reader.has("mpRaidmedium")) {
                player.mpRaidMedium = (reader.get("mpRaidmedium").getAsBoolean());
            }
            if (reader.has("mpRaidhard")) {
                player.mpRaidHard = (reader.get("mpRaidhard").getAsBoolean());
            }
            if (reader.has("mpRaidexpert")) {
                player.mpRaidExpert = (reader.get("mpRaidexpert").getAsBoolean());
            }
            if (reader.has("mpPVMeasy")) {
                player.mpBossEasy = (reader.get("mpPVMeasy").getAsBoolean());
            }
            if (reader.has("mpPVMmedium")) {
                player.mpBossMedium = (reader.get("mpPVMmedium").getAsBoolean());
            }
            if (reader.has("mpPVMhard")) {
                player.mpBossHard = (reader.get("mpPVMhard").getAsBoolean());
            }
            if (reader.has("mpPVMexpert")) {
                player.mpBossExpert = (reader.get("mpPVMexpert").getAsBoolean());
            }

            if (reader.has("AutoKey")) {
                player.autokey = (reader.get("AutoKey").getAsBoolean());
            }
            if (reader.has("AutoBone")) {
                player.autobone = (reader.get("AutoBone").getAsBoolean());
            }
            if (reader.has("AutoSupply")) {
                player.autosupply = (reader.get("AutoSupply").getAsBoolean());
            }
            if (reader.has("AlchableCoins")) {
                player.alchablecoins = (reader.get("AlchableCoins").getAsBoolean());
            }
            if (reader.has("displayHUD")) {
                player.displayHUD = (reader.get("displayHUD").getAsBoolean());
            }
            if (reader.has("hudColor")) {
                player.hudColor = (reader.get("hudColor").getAsString());
            }
            if (reader.has("hudOvl")) {
                player.hudOvl = (reader.get("hudOvl").getAsBoolean());
            }
            if (reader.has("hudFire")) {
                player.hudFire = (reader.get("hudFire").getAsBoolean());
            }
            if (reader.has("hudPoison")) {
                player.hudPoison = (reader.get("hudPoison").getAsBoolean());
            }
            if (reader.has("hudBonus")) {
                player.hudBonus = (reader.get("hudBonus").getAsBoolean());
            }
            if (reader.has("hudSlayer")) {
                player.hudSlayer = (reader.get("hudSlayer").getAsBoolean());
            }
            if (reader.has("hudSkiller")) {
                player.hudSkiller = (reader.get("hudSkiller").getAsBoolean());
            }
            if (reader.has("hudBPexp")) {
                player.hudBPexp = (reader.get("hudBPexp").getAsBoolean());
            }
            if (reader.has("hudBPkills")) {
                player.hudBPkills = (reader.get("hudBPkills").getAsBoolean());
            }
            if (reader.has("hudEPexp")) {
                player.hudEPexp = (reader.get("hudEPexp").getAsBoolean());
            }
            if (reader.has("hudEPkills")) {
                player.hudEPkills = (reader.get("hudEPkills").getAsBoolean());
            }
            if (reader.has("hudCballs")) {
                player.hudCballs = (reader.get("hudCballs").getAsBoolean());
            }
            if (reader.has("hudChoices")) {
                player.hudChoices = reader.get("hudChoices").getAsInt();
            }

            if (reader.has("InRandom")) {
                player.inRandom = (reader.get("InRandom").getAsBoolean());
            }
            if (reader.has("randomx")) {
                player.randomx = reader.get("randomx").getAsInt();
            }
            if (reader.has("randomy")) {
                player.randomy = reader.get("randomy").getAsInt();
            }
            if (reader.has("randomz")) {
                player.randomz = reader.get("randomz").getAsInt();
            }
            if (reader.has("RandomEventTimer")) {
                player.setRandomEventTimer(reader.get("RandomEventTimer").getAsInt());
            }
            if (reader.has("CrystalChests")) {
                player.CrystalChests = reader.get("CrystalChests").getAsInt();
            }
            if (reader.has("TaintedChests")) {
                player.TaintedChests = reader.get("TaintedChests").getAsInt();
            }

            if (reader.has("AcceleratedAccrual")) {
                player.Accelerate = reader.get("AcceleratedAccrual").getAsInt();
            }

            if (reader.has("Lucky")) {
                player.Lucky = reader.get("Lucky").getAsInt();
            }
            if (reader.has("FluidStrikes")) {
                player.FluidStrike = reader.get("FluidStrikes").getAsInt();
            }
            if (reader.has("QuickShot")) {
                player.QuickShot = reader.get("QuickShot").getAsInt();
            }
            if (reader.has("DoubleCast")) {
                player.DoubleCast = reader.get("DoubleCast").getAsInt();
            }
            if (reader.has("Survivalist")) {
                player.Survivalist = reader.get("Survivalist").getAsInt();
            }
            if (reader.has("TreasureSeeker")) {
                player.Looter = reader.get("TreasureSeeker").getAsInt();
            }
            if (reader.has("UnnaturalSelection")) {
                player.Unnatural = reader.get("UnnaturalSelection").getAsInt();
            }
            if (reader.has("Botanist")) {
                player.Botanist = reader.get("Botanist").getAsInt();
            }
            if (reader.has("DrainingStrikes")) {
                player.Vampirism = reader.get("DrainingStrikes").getAsInt();
            }
            if (reader.has("WeaponSpecialist")) {
                player.Specialist = reader.get("WeaponSpecialist").getAsInt();
            }
            if (reader.has("Fabricator")) {
                player.Fabricator = reader.get("Fabricator").getAsInt();
            }
            if (reader.has("Prodigy")) {
                player.Prodigy = reader.get("Prodigy").getAsInt();
            }
            if (reader.has("Equilibrium")) {
                player.Equilibrium = reader.get("Equilibrium").getAsInt();
            }
            if (reader.has("Flash")) {
                player.Flash = reader.get("Flash").getAsInt();
            }
            if (reader.has("Loaded")) {
                player.Loaded = reader.get("Loaded").getAsInt();
            }
            if (reader.has("Lifelink")) {
                player.Lifelink = reader.get("Lifelink").getAsInt();
            }
            if (reader.has("Berserker")) {
                player.Berserker = reader.get("Berserker").getAsInt();
            }
            if (reader.has("Bullseye")) {
                player.Bullseye = reader.get("Bullseye").getAsInt();
            }
            if (reader.has("Prophetic")) {
                player.Prophetic = reader.get("Prophetic").getAsInt();
            }
            if (reader.has("Merchant")) {
                player.Merchant = reader.get("Merchant").getAsInt();
            }
            if (reader.has("Artisan")) {
                player.Artisan = reader.get("Artisan").getAsInt();
            }


            if (reader.has("Detective")) {
                player.Detective = reader.get("Detective").getAsInt();
            }
            if (reader.has("Critical")) {
                player.Critical = reader.get("Critical").getAsInt();
            }
            if (reader.has("Flurry")) {
                player.Flurry = reader.get("Flurry").getAsInt();
            }
            if (reader.has("Consistent")) {
                player.Consistent = reader.get("Consistent").getAsInt();
            }
            if (reader.has("Dexterity")) {
                player.Dexterity = reader.get("Dexterity").getAsInt();
            }
            if (reader.has("Stability")) {
                player.Stability = reader.get("Stability").getAsInt();
            }
            if (reader.has("Absorb")) {
                player.Absorb = reader.get("Absorb").getAsInt();
            }
            if (reader.has("Efficiency")) {
                player.Efficiency = reader.get("Efficiency").getAsInt();
            }
            if (reader.has("Efficacy")) {
                player.Efficacy = reader.get("Efficacy").getAsInt();
            }
            if (reader.has("Devour")) {
                player.Devour = reader.get("Devour").getAsInt();
            }
            if (reader.has("Wealthy")) {
                player.Wealthy = reader.get("Wealthy").getAsInt();
            }
            if (reader.has("Reflect")) {
                player.Reflect = reader.get("Reflect").getAsInt();
            }
            if (reader.has("masteryPerksUnlocked")) {
                player.masteryPerksUnlocked = reader.get("masteryPerksUnlocked").getAsInt();
            }
            if (reader.has("masteryFix")) {
                player.masteryFix = reader.get("masteryFix").getAsBoolean();
            }

            if (reader.has("wildAccelerate")) {
                player.wildAccelerate = reader.get("wildAccelerate").getAsInt();
            }
            if (reader.has("wildLooter")) {
                player.wildLooter = reader.get("wildLooter").getAsInt();
            }
            if (reader.has("wildSpecialist")) {
                player.wildSpecialist = reader.get("wildSpecialist").getAsInt();
            }
            if (reader.has("wildHoarder")) {
                player.wildHoarder = reader.get("wildHoarder").getAsInt();
            }
            if (reader.has("wildVesta")) {
                player.wildVesta = reader.get("wildVesta").getAsInt();
            }
            if (reader.has("wildStatius")) {
                player.wildStatius = reader.get("wildStatius").getAsInt();
            }
            if (reader.has("wildMorrigan")) {
                player.wildMorrigan = reader.get("wildMorrigan").getAsInt();
            }
            if (reader.has("wildZuriel")) {
                player.wildZuriel = reader.get("wildZuriel").getAsInt();
            }
            if (reader.has("wildSavior")) {
                player.wildSavior = reader.get("wildSavior").getAsInt();
            }
            if (reader.has("wildAlchemize")) {
                player.wildAlchemize = reader.get("wildAlchemize").getAsInt();
            }
            if (reader.has("wildEnraged")) {
                player.wildEnraged = reader.get("wildEnraged").getAsInt();
            }
            if (reader.has("wildTainted")) {
                player.wildTainted = reader.get("wildTainted").getAsInt();
            }
            if (reader.has("wildBotanist")) {
                player.wildBotanist = reader.get("wildBotanist").getAsInt();
            }


            if (reader.has("coxRaidBonus")) {
                player.coxRaidBonus = reader.get("coxRaidBonus").getAsInt();
            }
            if (reader.has("tobRaidBonus")) {
                player.tobRaidBonus = reader.get("tobRaidBonus").getAsInt();
            }
            if (reader.has("gwdRaidBonus")) {
                player.gwdRaidBonus = reader.get("gwdRaidBonus").getAsInt();
            }
            if (reader.has("chaosRaidBonus")) {
                player.chaosRaidBonus = reader.get("chaosRaidBonus").getAsInt();
            }
            if (reader.has("shrRaidBonus")) {
                player.shrRaidBonus = reader.get("shrRaidBonus").getAsInt();
            }


            if (reader.has("looterBanking")) {
                player.looterBanking = reader.get("looterBanking").getAsBoolean();
            }


            if (reader.has("AncientPages")) {
                player.setAncientPages(reader.get("AncientPages").getAsInt());
            }
            if (reader.has("ArmadylPages")) {
                player.setArmadylPages(reader.get("ArmadylPages").getAsInt());
            }
            if (reader.has("BandosPages")) {
                player.setBandosPages(reader.get("BandosPages").getAsInt());
            }
            if (reader.has("GuthixPages")) {
                player.setGuthixPages(reader.get("GuthixPages").getAsInt());
            }
            if (reader.has("SaradominPages")) {
                player.setSaradominPages(reader.get("SaradominPages").getAsInt());
            }
            if (reader.has("ZamorakPages")) {
                player.setZamorakPages(reader.get("ZamorakPages").getAsInt());
            }


            if (reader.has("barrows")) {
                player.setCompletedBarrows(reader.get("barrows").getAsInt());
            }

            if (reader.has("coxcheck")) {
                player.coxCheck = reader.get("coxcheck").getAsBoolean();
            }
            if (reader.has("coxcount")) {
                player.coxCount = reader.get("coxcount").getAsInt();
            }
            if (reader.has("coxremaining")) {
                player.coxRemaining = reader.get("coxremaining").getAsInt();
            }
            if (reader.has("tobcheck")) {
                player.tobCheck = reader.get("tobcheck").getAsBoolean();
            }
            if (reader.has("tobcount")) {
                player.tobCount = reader.get("tobcount").getAsInt();
            }
            if (reader.has("tobremaining")) {
                player.tobRemaining = reader.get("tobremaining").getAsInt();
            }
            if (reader.has("chaoscheck")) {
                player.chaosCheck = reader.get("chaoscheck").getAsBoolean();
            }
            if (reader.has("chaoscount")) {
                player.chaosCount = reader.get("chaoscount").getAsInt();
            }
            if (reader.has("chaosremaining")) {
                player.chaosRemaining = reader.get("chaosremaining").getAsInt();
            }
            if (reader.has("achievementCheck")) {
                player.achievementCheck = reader.get("achievementCheck").getAsBoolean();
            }


            if (reader.has("WTKC")) {
                player.WintertodtKC = reader.get("WTKC").getAsInt();
            }
            if (reader.has("CoxKC")) {
                player.setCoxKC(reader.get("CoxKC").getAsInt());
            }
            if (reader.has("TobKC")) {
                player.setTobKC(reader.get("TobKC").getAsInt());
            }
            if (reader.has("GauntletKC")) {
                player.GauntletKC = (reader.get("GauntletKC").getAsInt());
            }
            if (reader.has("GwdRaidKC")) {
                player.setGwdRaidKC(reader.get("GwdRaidKC").getAsInt());
            }
            if (reader.has("chaosKC")) {
                player.setchaosKC(reader.get("chaosKC").getAsInt());
            }
            if (reader.has("shrKC")) {
                player.setshrKC(reader.get("shrKC").getAsInt());
            }
            if (reader.has("ChaosRaid1")) {
                player.setChaosRaid1(reader.get("ChaosRaid1").getAsInt());
            }
            if (reader.has("ChaosRaid2")) {
                player.setChaosRaid2(reader.get("ChaosRaid2").getAsInt());
            }
            if (reader.has("ChaosRaid3")) {
                player.setChaosRaid3(reader.get("ChaosRaid3").getAsInt());
            }
            if (reader.has("ChaosRaid4")) {
                player.setChaosRaid4(reader.get("ChaosRaid4").getAsInt());
            }
            if (reader.has("ChaosRaid5")) {
                player.setChaosRaid5(reader.get("ChaosRaid5").getAsInt());
            }
            if (reader.has("ChaosRaid6")) {
                player.setChaosRaid6(reader.get("ChaosRaid6").getAsInt());
            }
            if (reader.has("ChaosRaid7")) {
                player.setChaosRaid7(reader.get("ChaosRaid7").getAsInt());
            }
            if (reader.has("ChaosRaid8")) {
                player.setChaosRaid8(reader.get("ChaosRaid8").getAsInt());
            }
            if (reader.has("ChaosRaid9")) {
                player.setChaosRaid9(reader.get("ChaosRaid9").getAsInt());
            }
            if (reader.has("ChaosRaid10")) {
                player.setChaosRaid10(reader.get("ChaosRaid10").getAsInt());
            }
            if (reader.has("CompletedChaos")) {
                player.setCompletedChaos(reader.get("CompletedChaos").getAsInt());
            }


            if (reader.has("daily")) {
                player.setDaily(reader.get("daily").getAsInt());
            }


            if (reader.has("LastRecallx")) {
                player.setLastRecallx(reader.get("LastRecallx").getAsInt());
            }
            if (reader.has("LastRecally")) {
                player.setLastRecally(reader.get("LastRecally").getAsInt());
            }
            if (reader.has("LastRecallz")) {
                player.setLastRecallz(reader.get("LastRecallz").getAsInt());
            }


            if (reader.has("email")) {
                player.setEmailAddress(reader.get("email").getAsString());
            }

            if (reader.has("staff-rights")) {
                player.setStaffRights(StaffRights.valueOf(reader.get("staff-rights").getAsString()));
            }

            if (reader.has("subscriptionType")) {
                player.setSubscription(Subscriptions.valueOf(reader.get("subscriptionType").getAsString()));
            }

            if (reader.has("subscriptionStart")) {
                player.subscriptionStartDate = (reader.get("subscriptionStart").getAsInt());
            }

            if (reader.has("subscriptionEnd")) {
                player.subscriptionEndDate = (reader.get("subscriptionEnd").getAsInt());
            }

            if (reader.has("game-mode")) {
                player.setGameMode(GameMode.valueOf(reader.get("game-mode").getAsString()));
            }

            if (reader.has("boxScape")) {
                player.boxScape = reader.get("boxScape").getAsBoolean();
            }

            if (reader.has("exprate")) {
                player.setexprate(reader.get("exprate").getAsInt());
            }

            if (reader.has("difficulty")) {
                player.difficulty = reader.get("difficulty").getAsInt();
            }

            if (reader.has("prestige")) {
                player.prestige = reader.get("prestige").getAsInt();
            }

            if (reader.has("extraPrestiges")) {
                player.extraPrestiges = reader.get("extraPrestiges").getAsInt();
            }

            if (reader.has("perkUpgrades")) {
                player.perkUpgrades = reader.get("perkUpgrades").getAsInt();
            }

            if (reader.has("loyalty-title")) {
                player.setLoyaltyTitle(LoyaltyTitles.valueOf(reader.get("loyalty-title").getAsString()));
            }

            if (reader.has("position")) {
                player.getPosition().setAs(builder.fromJson(reader.get("position"), Position.class));
            }

            if (reader.has("online-status")) {
                player.getRelations().setStatus(PrivateChatStatus.valueOf(reader.get("online-status").getAsString()),
                        false);
            }

            if (reader.has("money-pouch")) {
                player.setMoneyInPouch(reader.get("money-pouch").getAsLong());
            }

            if (reader.has("given-starter")) {
                player.setReceivedStarter(reader.get("given-starter").getAsBoolean());
            }


            if (reader.has("minutes-bonus-exp")) {
                player.setMinutesBonusExp(reader.get("minutes-bonus-exp").getAsInt(), false);
            }

            if (reader.has("total-gained-exp")) {
                player.getSkillManager().setTotalGainedExp(reader.get("total-gained-exp").getAsInt());
            }

            if (reader.has("dung-tokens")) {
                player.getPointsHandler().setDungeoneeringTokens(reader.get("dung-tokens").getAsInt(), false);
            }

            if (reader.has("CoalBag")) {
                player.setCoalBag(reader.get("CoalBag").getAsInt());
            }

            if (reader.has("RunePouchTypeOne")) {
                player.setRunePouchTypeOne(reader.get("RunePouchTypeOne").getAsInt());
            }

            if (reader.has("RunePouchQtyOne")) {
                player.setRunePouchQtyOne(reader.get("RunePouchQtyOne").getAsInt());
            }

            if (reader.has("RunePouchTypeTwo")) {
                player.setRunePouchTypeTwo(reader.get("RunePouchTypeTwo").getAsInt());
            }

            if (reader.has("RunePouchQtyTwo")) {
                player.setRunePouchQtyTwo(reader.get("RunePouchQtyTwo").getAsInt());
            }

            if (reader.has("RunePouchTypeThree")) {
                player.setRunePouchTypeThree(reader.get("RunePouchTypeThree").getAsInt());
            }

            if (reader.has("RunePouchQtyThree")) {
                player.setRunePouchQtyThree(reader.get("RunePouchQtyThree").getAsInt());
            }

            if (reader.has("RunePouchTypeFour")) {
                player.setRunePouchTypeFour(reader.get("RunePouchTypeFour").getAsInt());
            }

            if (reader.has("RunePouchQtyFour")) {
                player.setRunePouchQtyFour(reader.get("RunePouchQtyFour").getAsInt());
            }

            if (reader.has("BagSapphire")) {
                player.setBagSapphire(reader.get("BagSapphire").getAsInt());
            }

            if (reader.has("BagEmerald")) {
                player.setBagEmerald(reader.get("BagEmerald").getAsInt());
            }

            if (reader.has("BagRuby")) {
                player.setBagRuby(reader.get("BagRuby").getAsInt());
            }

            if (reader.has("BagDiamond")) {
                player.setBagDiamond(reader.get("BagDiamond").getAsInt());
            }

            if (reader.has("BagDragonstone")) {
                player.setBagDragonstone(reader.get("BagDragonstone").getAsInt());
            }

            if (reader.has("kingdomDonation")) {
                player.kingdomDonation = reader.get("kingdomDonation").getAsLong();
            }

            if (reader.has("kingdomExperience")) {
                player.kingdomExperience = reader.get("kingdomExperience").getAsLong();
            }

            if (reader.has("kingdomKC")) {
                player.kingdomKC = reader.get("kingdomKC").getAsLong();
            }

            if (reader.has("kingdomGatherer")) {
                player.kingdomGatherer = reader.get("kingdomGatherer").getAsLong();
            }

            if (reader.has("ScytheCharges")) {
                player.setScytheCharges(reader.get("ScytheCharges").getAsInt());
            }

            if (reader.has("SanguinestiCharges")) {
                player.setSanguinestiCharges(reader.get("SanguinestiCharges").getAsInt());
            }

            if (reader.has("SurgeboxCharges")) {
                player.setSurgeboxCharges(reader.get("SurgeboxCharges").getAsInt());
            }

            if (reader.has("NightmareCharges")) {
                player.setNightmareCharges(reader.get("NightmareCharges").getAsInt());
            }

            if (reader.has("amount-donated")) {
                player.getPointsHandler().setAmountDonated(0, reader.get("amount-donated").getAsInt());
            }

            if (reader.has("donator-points")) {
                player.getPointsHandler().setdonatorpoints(0, reader.get("donator-points").getAsInt());
            }

            if (reader.has("prestige-points")) {
                player.getPointsHandler().setPrestigePoints(reader.get("prestige-points").getAsInt(), false);
            }

            if (reader.has("achievement-points")) {
                player.getPointsHandler().setAchievementPoints(reader.get("achievement-points").getAsInt(), false);
            }

            if (reader.has("supplycaskets")) {
                player.getPointsHandler().setsupplycaskets(reader.get("supplycaskets").getAsInt(), false);
            }

            if (reader.has("bosscaskets")) {
                player.getPointsHandler().setbosscaskets(reader.get("bosscaskets").getAsInt(), false);
            }

            if (reader.has("openedpresents")) {
                player.getPointsHandler().setopenedpresents(reader.get("openedpresents").getAsInt(), false);
            }

            if (reader.has("opened3rdage")) {
                player.getPointsHandler().setopened3rdage(reader.get("opened3rdage").getAsInt(), false);
            }

            if (reader.has("openedholiday")) {
                player.getPointsHandler().setopenedholiday(reader.get("openedholiday").getAsInt(), false);
            }


            if (reader.has("loyalty-points")) {
                player.getPointsHandler().setLoyaltyPoints(reader.get("loyalty-points").getAsInt(), false);
            }

            if (reader.has("total-loyalty-points")) {
                player.incrementTotalLoyaltyPointsEarned(reader.get("total-loyalty-points").getAsDouble());
            }

            if (reader.has("totalbosskills")) {
                player.getPointsHandler().settotalbosskills(reader.get("totalbosskills").getAsInt(), false);
            }

            if (reader.has("slayer-points")) {
                player.getPointsHandler().setSlayerPoints(reader.get("slayer-points").getAsInt(), false);
            }

            if (reader.has("PC-points")) {
                player.getPointsHandler().setPCPoints(reader.get("PC-points").getAsInt(), false);
            }

            if (reader.has("halloween23")) {
                player.halloween23 = (reader.get("halloween23").getAsBoolean());
            }

            if (reader.has("pk-points")) {
                player.getPointsHandler().setPkPoints(reader.get("pk-points").getAsInt(), false);
            }
            if (reader.has("trivia-points")) {
                player.getPointsHandler().setTriviaPoints(reader.get("trivia-points").getAsInt(), false);
            }

            if (reader.has("cluescomplted")) {
                ClueScrolls.setCluesCompleted(reader.get("cluescompleted").getAsInt(), false);
            }

            if (reader.has("player-kills")) {
                player.getPlayerKillingAttributes().setPlayerKills(reader.get("player-kills").getAsInt());
            }

            if (reader.has("player-killstreak")) {
                player.getPlayerKillingAttributes().setPlayerKillStreak(reader.get("player-killstreak").getAsInt());
            }

            if (reader.has("player-deaths")) {
                player.getPlayerKillingAttributes().setPlayerDeaths(reader.get("player-deaths").getAsInt());
            }

            if (reader.has("target-percentage")) {
                player.getPlayerKillingAttributes().setTargetPercentage(reader.get("target-percentage").getAsInt());
            }

            if (reader.has("bh-rank")) {
                player.getAppearance().setBountyHunterSkull(reader.get("bh-rank").getAsInt());
            }

            if (reader.has("gender")) {
                player.getAppearance().setGender(Gender.valueOf(reader.get("gender").getAsString()));
            }

            if (reader.has("spell-book")) {
                player.setSpellbook(MagicSpellbook.valueOf(reader.get("spell-book").getAsString()));
            }

            if (reader.has("prayer-book")) {
                player.setPrayerbook(Prayerbook.valueOf(reader.get("prayer-book").getAsString()));
            }
            if (reader.has("running")) {
                player.setRunning(reader.get("running").getAsBoolean());
            }
            if (reader.has("run-energy")) {
                player.setRunEnergy(reader.get("run-energy").getAsInt());
            }
            if (reader.has("music")) {
                player.setMusicActive(reader.get("music").getAsBoolean());
            }
            if (reader.has("sounds")) {
                player.setSoundsActive(reader.get("sounds").getAsBoolean());
            }
            if (reader.has("aggressorTime")) {
                player.aggressorTime = reader.get("aggressorTime").getAsInt();
            }
            if (reader.has("aggressorRadius")) {
                player.aggressorRadius = reader.get("aggressorRadius").getAsInt();
            }
            if (reader.has("flashbackTime")) {
                player.flashbackTime = reader.get("flashbackTime").getAsInt();
            }
            if (reader.has("auto-retaliate")) {
                player.setAutoRetaliate(reader.get("auto-retaliate").getAsBoolean());
            }
            if (reader.has("xp-locked")) {
                player.setExperienceLocked(reader.get("xp-locked").getAsBoolean());
            }
            if (reader.has("veng-cast")) {
                player.setHasVengeance(reader.get("veng-cast").getAsBoolean());
            }
            if (reader.has("last-veng")) {
                player.getLastVengeance().reset(reader.get("last-veng").getAsLong());
            }
            if (reader.has("fight-type")) {
                player.setFightType(FightType.valueOf(reader.get("fight-type").getAsString()));
            }
            if (reader.has("sol-effect")) {
                player.setStaffOfLightEffect(reader.get("sol-effect").getAsInt());
            }
            if (reader.has("skull-timer")) {
                player.setSkullTimer(reader.get("skull-timer").getAsInt());
            }
            if (reader.has("accept-aid")) {
                player.setAcceptAid(reader.get("accept-aid").getAsBoolean());
            }
            if (reader.has("poison-damage")) {
                player.setPoisonDamage(reader.get("poison-damage").getAsInt());
            }
            if (reader.has("poison-immunity")) {
                player.setPoisonImmunity(reader.get("poison-immunity").getAsInt());
            }
            if (reader.has("overload-timer")) {
                player.setOverloadPotionTimer(reader.get("overload-timer").getAsInt());
            }
            if (reader.has("overloadPlus")) {
                player.overloadPlus = reader.get("overloadPlus").getAsBoolean();
            }
            if (reader.has("fire-immunity")) {
                player.setDragonfireImmunity(reader.get("fire-immunity").getAsInt());
            }
            if (reader.has("prayer-renewal-timer")) {
                player.setPrayerRenewalPotionTimer(reader.get("prayer-renewal-timer").getAsInt());
            }
            if (reader.has("teleblock-timer")) {
                player.setTeleblockTimer(reader.get("teleblock-timer").getAsInt());
            }
            if (reader.has("special-amount")) {
                player.setSpecialPercentage(reader.get("special-amount").getAsInt());
            }

            if (reader.has("entered-gwd-room")) {
                player.getMinigameAttributes().getGodwarsDungeonAttributes()
                        .setHasEnteredRoom(reader.get("entered-gwd-room").getAsBoolean());
            }

            if (reader.has("gwd-altar-delay")) {
                player.getMinigameAttributes().getGodwarsDungeonAttributes()
                        .setAltarDelay(reader.get("gwd-altar-delay").getAsLong());
            }

            if (reader.has("gwd-killcount")) {
                player.getMinigameAttributes().getGodwarsDungeonAttributes()
                        .setKillcount(builder.fromJson(reader.get("gwd-killcount"), int[].class));
            }

            if (reader.has("mining-bot")) {
                player.getMinigameAttributes().getMiningBotAttributes()
                        .setHasMiningBot(false);
            }

            if (reader.has("mining-bot-owed")) {
                player.getMinigameAttributes().getMiningBotAttributes()
                        .setResourcesLeftToCollect(reader.get("mining-bot-owed").getAsInt());
            }

            if (reader.has("mining-bot-left")) {
                player.getMinigameAttributes().getMiningBotAttributes()
                        .setResourcesLeftToGather(reader.get("mining-bot-left").getAsInt());
            }


            if (reader.has("wcing-bot")) {
                player.getMinigameAttributes().getWCingBotAttributes()
                        .setHasWCingBot(false);
            }

            if (reader.has("wcing-bot-owed")) {
                player.getMinigameAttributes().getWCingBotAttributes()
                        .setResourcesLeftToCollect(reader.get("wcing-bot-owed").getAsInt());
            }

            if (reader.has("wcing-bot-left")) {
                player.getMinigameAttributes().getWCingBotAttributes()
                        .setResourcesLeftToGather(reader.get("wcing-bot-left").getAsInt());
            }

            if (reader.has("farming-bot")) {
                player.getMinigameAttributes().getFarmingBotAttributes()
                        .setHasFarmingBot(false);
            }

            if (reader.has("farming-bot-owed")) {
                player.getMinigameAttributes().getFarmingBotAttributes()
                        .setResourcesLeftToCollect(reader.get("farming-bot-owed").getAsInt());
            }

            if (reader.has("farming-bot-left")) {
                player.getMinigameAttributes().getFarmingBotAttributes()
                        .setResourcesLeftToGather(reader.get("farming-bot-left").getAsInt());
            }

            if (reader.has("fishing-bot")) {
                player.getMinigameAttributes().getFishingBotAttributes()
                        .setHasFishingBot(false);
            }

            if (reader.has("fishing-bot-owed")) {
                player.getMinigameAttributes().getFishingBotAttributes()
                        .setResourcesLeftToCollect(reader.get("fishing-bot-owed").getAsInt());
            }

            if (reader.has("fishing-bot-left")) {
                player.getMinigameAttributes().getFishingBotAttributes()
                        .setResourcesLeftToGather(reader.get("fishing-bot-left").getAsInt());
            }

            if (reader.has("effigy")) {
                player.setEffigy(reader.get("effigy").getAsInt());
            }

            if (reader.has("summon-npc")) {
                int npc = reader.get("summon-npc").getAsInt();
                if (npc > 0)
                    player.getSummoning().setFamiliarSpawnTask(new FamiliarSpawnTask(player)).setFamiliarId(npc);
            }
            if (reader.has("summon-death")) {
                int death = reader.get("summon-death").getAsInt();
                if (death > 0 && player.getSummoning().getSpawnTask() != null)
                    player.getSummoning().getSpawnTask().setDeathTimer(death);
            }
            if (reader.has("process-farming")) {
                player.setProcessFarming(reader.get("process-farming").getAsBoolean());
            }

            if (reader.has("clanchat")) {
                String clan = reader.get("clanchat").getAsString();
                if (!clan.equals("null"))
                    player.setClanChatName(clan);
            }
            if (reader.has("autocast")) {
                player.setAutocast(reader.get("autocast").getAsBoolean());
            }
            if (reader.has("autocast-spell")) {
                int spell = reader.get("autocast-spell").getAsInt();
                if (spell != -1)
                    player.setAutocastSpell(CombatSpells.getSpell(spell));
            }

            if (reader.has("dfs-charges")) {
                player.incrementDfsCharges(reader.get("dfs-charges").getAsInt());
            }

            if (reader.has("deaths-coffer")) {
                player.loadCofferData(reader.get("deaths-coffer"));
            }

            if (reader.has("clogs")) {
                player.getCollectionLog().loadCollections(reader.get("clogs"));
            }

            if (reader.has("kills")) {
                player.getCollectionLog().loadKillsTracker(reader.get("kills"));
            }

            if (reader.has("drops")) {
                DropLog.submit(player, builder.fromJson(reader.get("drops").getAsJsonArray(), DropLogEntry[].class));
            }

            if (reader.has("bingo")) {
                player.getBingo().load(reader.get("bingo").getAsJsonObject());
            }

            if (reader.has("skiller-task")) {
                player.getSkiller().setSkillerTask(SkillerTasks.valueOf(reader.get("skiller-task").getAsString()));
            }

            if (reader.has("prev-skiller-task")) {
                player.getSkiller().setLastTask(SkillerTasks.valueOf(reader.get("prev-skiller-task").getAsString()));
            }

            if (reader.has("skiller-task-amount")) {
                player.getSkiller().setAmountToSkill(reader.get("skiller-task-amount").getAsInt());
            }

            if (reader.has("skiller-task-streak")) {
                player.getSkiller().setTaskStreak(reader.get("skiller-task-streak").getAsInt());
            }

            if (reader.has("slayer-master")) {
                player.getSlayer().setSlayerMaster(SlayerMaster.valueOf(reader.get("slayer-master").getAsString()));
            }


            if (player.perkReset) {
                if (reader.has("slayer-task")) {
                    player.getSlayer().setSlayerTask(SlayerTasks.valueOf(reader.get("slayer-task").getAsString()));
                }
            }

            if (player.perkReset) {
                if (reader.has("prev-slayer-task")) {
                    player.getSlayer().setLastTask(SlayerTasks.valueOf(reader.get("prev-slayer-task").getAsString()));
                }
            }

            if (reader.has("task-amount")) {
                player.getSlayer().setAmountToSlay(reader.get("task-amount").getAsInt());
            }

            if (reader.has("task-streak")) {
                player.getSlayer().setTaskStreak(reader.get("task-streak").getAsInt());
            }

            if (reader.has("duo-partner")) {
                String partner = reader.get("duo-partner").getAsString();
                player.getSlayer().setDuoPartner(partner.equals("null") ? null : partner);
            }

            if (reader.has("double-slay-xp")) {
                player.getSlayer().doubleSlayerXP = reader.get("double-slay-xp").getAsBoolean();
            }

            if (reader.has("communityEvents24")) {
                player.communityEvents2024 = builder.fromJson(reader.get("communityEvents24").getAsJsonArray(), int[].class);
            }

            if (reader.has("recoil-deg")) {
                player.setRecoilCharges(reader.get("recoil-deg").getAsInt());
            }

            if (reader.has("brawler-deg")) {
                player.setBrawlerCharges(builder.fromJson(reader.get("brawler-deg").getAsJsonArray(), int[].class));
            }

            if (reader.has("pvp-deg")) {
                player.setPvpEquipmentCharges(builder.fromJson(reader.get("pvp-deg").getAsJsonArray(), int[].class));
            }

            if (reader.has("killed-players")) {
                List<String> list = new ArrayList<>();
                String[] killed_players = builder.fromJson(reader.get("killed-players").getAsJsonArray(),
                        String[].class);
                Collections.addAll(list, killed_players);
                player.getPlayerKillingAttributes().setKilledPlayers(list);
            }

            if (reader.has("barrows-brother")) {
                player.getMinigameAttributes().getBarrowsMinigameAttributes().setBarrowsData(
                        builder.fromJson(reader.get("barrows-brother").getAsJsonArray(), int[][].class));
            }

            if (reader.has("random-coffin")) {
                player.getMinigameAttributes().getBarrowsMinigameAttributes()
                        .setRandomCoffin((reader.get("random-coffin").getAsInt()));
            }

            if (reader.has("barrows-killcount")) {
                player.getMinigameAttributes().getBarrowsMinigameAttributes()
                        .setKillcount((reader.get("barrows-killcount").getAsInt()));
            }

            if (reader.has("chaosraid-bosses")) {
                player.getMinigameAttributes().getChaosRaidMinigameAttributes().setChaosRaidData(
                        builder.fromJson(reader.get("chaosraid-bosses").getAsJsonArray(), int[].class));
            }

            if (reader.has("chaosraid-killcount")) {
                player.getMinigameAttributes().getChaosRaidMinigameAttributes()
                        .setKillcount((reader.get("chaosraid-killcount").getAsInt()));
            }

            if (reader.has("nomad")) {
                player.getMinigameAttributes().getNomadAttributes()
                        .setQuestParts(builder.fromJson(reader.get("nomad").getAsJsonArray(), boolean[].class));
            }

            if (reader.has("lightbearerOptions")) {
                player.lightbearerOptions = builder.fromJson(reader.get("lightbearerOptions").getAsJsonArray(), boolean[].class);
            }

            if (reader.has("recipe-for-disaster")) {
                player.getMinigameAttributes().getRecipeForDisasterAttributes().setQuestParts(
                        builder.fromJson(reader.get("recipe-for-disaster").getAsJsonArray(), boolean[].class));
            }

            if (reader.has("recipe-for-disaster-wave")) {
                player.getMinigameAttributes().getRecipeForDisasterAttributes()
                        .setWavesCompleted((reader.get("recipe-for-disaster-wave").getAsInt()));
            }

            if (reader.has("dung-items-bound")) {
                player.getMinigameAttributes().getDungeoneeringAttributes()
                        .setBoundItems(builder.fromJson(reader.get("dung-items-bound").getAsJsonArray(), int[].class));
            }

            if (reader.has("achievementAbilities")) {
                player.achievementAbilities = builder.fromJson(reader.get("achievementAbilities").getAsJsonArray(), int[].class);
            }

            if (reader.has("looterSettings")) {
                player.looterSettings = builder.fromJson(reader.get("looterSettings").getAsJsonArray(), int[].class);
            }

            if (reader.has("magicPaperAmount")) {
                player.magicPaperAmount = reader.get("magicPaperAmount").getAsInt();
            }

            if (reader.has("rune-ess")) {
                player.setStoredRuneEssence((reader.get("rune-ess").getAsInt()));
            }

            if (reader.has("pure-ess")) {
                player.setStoredPureEssence((reader.get("pure-ess").getAsInt()));
            }

            if (reader.has("pet1")) {
                player.petStorage1 = (reader.get("pet1").getAsInt());
            }

            if (reader.has("pet2")) {
                player.petStorage2 = (reader.get("pet2").getAsInt());
            }

            if (reader.has("pet3")) {
                player.petStorage3 = (reader.get("pet3").getAsInt());
            }

            if (reader.has("pet4")) {
                player.petStorage4 = (reader.get("pet4").getAsInt());
            }

            if (reader.has("pet5")) {
                player.petStorage5 = (reader.get("pet5").getAsInt());
            }

            if (reader.has("pet6")) {
                player.petStorage6 = (reader.get("pet6").getAsInt());
            }

            if (reader.has("pet7")) {
                player.petStorage7 = (reader.get("pet7").getAsInt());
            }

            if (reader.has("pet8")) {
                player.petStorage8 = (reader.get("pet8").getAsInt());
            }

            if (reader.has("pet9")) {
                player.petStorage9 = (reader.get("pet9").getAsInt());
            }

            if (reader.has("pet10")) {
                player.petStorage10 = (reader.get("pet10").getAsInt());
            }

            if (reader.has("pet11")) {
                player.petStorage11 = (reader.get("pet11").getAsInt());
            }

            if (reader.has("pet12")) {
                player.petStorage12 = (reader.get("pet12").getAsInt());
            }

            if (reader.has("pet13")) {
                player.petStorage13 = (reader.get("pet13").getAsInt());
            }

            if (reader.has("pet14")) {
                player.petStorage14 = (reader.get("pet14").getAsInt());
            }

            if (reader.has("pet15")) {
                player.petStorage15 = (reader.get("pet15").getAsInt());
            }

            if (reader.has("pet16")) {
                player.petStorage16 = (reader.get("pet16").getAsInt());
            }

            if (reader.has("pet17")) {
                player.petStorage17 = (reader.get("pet17").getAsInt());
            }

            if (reader.has("pet18")) {
                player.petStorage18 = (reader.get("pet18").getAsInt());
            }

            if (reader.has("pet19")) {
                player.petStorage19 = (reader.get("pet19").getAsInt());
            }

            if (reader.has("pet20")) {
                player.petStorage20 = (reader.get("pet20").getAsInt());
            }

            if (reader.has("pet21")) {
                player.petStorage21 = (reader.get("pet21").getAsInt());
            }

            if (reader.has("pet22")) {
                player.petStorage22 = (reader.get("pet22").getAsInt());
            }

            if (reader.has("pet23")) {
                player.petStorage23 = (reader.get("pet23").getAsInt());
            }

            if (reader.has("pet24")) {
                player.petStorage24 = (reader.get("pet24").getAsInt());
            }

            if (reader.has("pet25")) {
                player.petStorage25 = (reader.get("pet25").getAsInt());
            }

            if (reader.has("pet26")) {
                player.petStorage26 = (reader.get("pet26").getAsInt());
            }

            if (reader.has("pet27")) {
                player.petStorage27 = (reader.get("pet27").getAsInt());
            }

            if (reader.has("pet28")) {
                player.petStorage28 = (reader.get("pet28").getAsInt());
            }

            if (reader.has("pet29")) {
                player.petStorage29 = (reader.get("pet29").getAsInt());
            }

            if (reader.has("pet30")) {
                player.petStorage30 = (reader.get("pet30").getAsInt());
            }

            if (reader.has("pet31")) {
                player.petStorage31 = (reader.get("pet31").getAsInt());
            }

            if (reader.has("pet32")) {
                player.petStorage32 = (reader.get("pet32").getAsInt());
            }

            if (reader.has("pet33")) {
                player.petStorage33 = (reader.get("pet33").getAsInt());
            }

            if (reader.has("pet34")) {
                player.petStorage34 = (reader.get("pet34").getAsInt());
            }

            if (reader.has("pet35")) {
                player.petStorage35 = (reader.get("pet35").getAsInt());
            }

            if (reader.has("pet36")) {
                player.petStorage36 = (reader.get("pet36").getAsInt());
            }

            if (reader.has("pet37")) {
                player.petStorage37 = (reader.get("pet37").getAsInt());
            }

            if (reader.has("pet38")) {
                player.petStorage38 = (reader.get("pet38").getAsInt());
            }

            if (reader.has("pet39")) {
                player.petStorage39 = (reader.get("pet39").getAsInt());
            }

            if (reader.has("pet40")) {
                player.petStorage40 = (reader.get("pet40").getAsInt());
            }

            if (reader.has("petCount")) {
                player.petCount = (reader.get("petCount").getAsInt());
            }

            if (reader.has("bank-pin")) {
                player.getBankPinAttributes()
                        .setBankPin(builder.fromJson(reader.get("bank-pin").getAsJsonArray(), int[].class));
            }

            if (reader.has("placeholders")) {
                player.setPlaceholders((reader.get("placeholders").getAsBoolean()));
            }

            if (reader.has("has-bank-pin")) {
                player.getBankPinAttributes().setHasBankPin(reader.get("has-bank-pin").getAsBoolean());
            }
            if (reader.has("last-pin-attempt")) {
                player.getBankPinAttributes().setLastAttempt(reader.get("last-pin-attempt").getAsLong());
            }
            if (reader.has("invalid-pin-attempts")) {
                player.getBankPinAttributes().setInvalidAttempts(reader.get("invalid-pin-attempts").getAsInt());
            }

            if (reader.has("appearance")) {
                player.getAppearance().set(builder.fromJson(reader.get("appearance").getAsJsonArray(), int[].class));
            }

            if (reader.has("agility-obj")) {
                player.setCrossedObstacles(
                        builder.fromJson(reader.get("agility-obj").getAsJsonArray(), boolean[].class));
            }

            if (reader.has("skills")) {
                player.getSkillManager().setSkills(builder.fromJson(reader.get("skills"), Skills.class));
            }
            if (reader.has("inventory")) {
                player.getInventory()
                        .setItems(builder.fromJson(reader.get("inventory").getAsJsonArray(), Item[].class));
            }
            if (reader.has("equipment")) {
                player.getEquipment()
                        .setItems(builder.fromJson(reader.get("equipment").getAsJsonArray(), Item[].class));
            }
            if (reader.has("overrides")) {
                player.getOverrides()
                        .setItems(builder.fromJson(reader.get("overrides").getAsJsonArray(), Item[].class));
            }

            /** BANK **/
            for (int i = 0; i < 9; i++) {
                if (reader.has("bank-" + i))
                    player.setBank(i, new Bank(player)).getBank(i).addItems(
                            builder.fromJson(reader.get("bank-" + i).getAsJsonArray(), Item[].class), false);
            }

            if (reader.has("bank-0")) {
                player.setBank(0, new Bank(player)).getBank(0)
                        .addItems(builder.fromJson(reader.get("bank-0").getAsJsonArray(), Item[].class), false);
            }
            if (reader.has("bank-1")) {
                player.setBank(1, new Bank(player)).getBank(1)
                        .addItems(builder.fromJson(reader.get("bank-1").getAsJsonArray(), Item[].class), false);
            }
            if (reader.has("bank-2")) {
                player.setBank(2, new Bank(player)).getBank(2)
                        .addItems(builder.fromJson(reader.get("bank-2").getAsJsonArray(), Item[].class), false);
            }
            if (reader.has("bank-3")) {
                player.setBank(3, new Bank(player)).getBank(3)
                        .addItems(builder.fromJson(reader.get("bank-3").getAsJsonArray(), Item[].class), false);
            }
            if (reader.has("bank-4")) {
                player.setBank(4, new Bank(player)).getBank(4)
                        .addItems(builder.fromJson(reader.get("bank-4").getAsJsonArray(), Item[].class), false);
            }
            if (reader.has("bank-5")) {
                player.setBank(5, new Bank(player)).getBank(5)
                        .addItems(builder.fromJson(reader.get("bank-5").getAsJsonArray(), Item[].class), false);
            }
            if (reader.has("bank-6")) {
                player.setBank(6, new Bank(player)).getBank(6)
                        .addItems(builder.fromJson(reader.get("bank-6").getAsJsonArray(), Item[].class), false);
            }
            if (reader.has("bank-7")) {
                player.setBank(7, new Bank(player)).getBank(7)
                        .addItems(builder.fromJson(reader.get("bank-7").getAsJsonArray(), Item[].class), false);
            }
            if (reader.has("bank-8")) {
                player.setBank(8, new Bank(player)).getBank(8)
                        .addItems(builder.fromJson(reader.get("bank-8").getAsJsonArray(), Item[].class), false);
            }

            if (reader.has("uim-bank")) {
                player.getUimBank().addItems(builder.fromJson(reader.get("uim-bank").getAsJsonArray(), Item[].class), false);
            }

            if (reader.has("mailbox")) {
                player.getMailBox().addItems(builder.fromJson(reader.get("mailbox").getAsJsonArray(), Item[].class), false);
            }

            if (reader.has("ge-slots")) {
                GrandExchangeSlot[] slots = builder.fromJson(reader.get("ge-slots").getAsJsonArray(),
                        GrandExchangeSlot[].class);
                player.setGrandExchangeSlots(slots);
            }

            if (reader.has("store")) {
                Item[] validStoredItems = builder.fromJson(reader.get("store").getAsJsonArray(), Item[].class);
                if (player.getSummoning().getSpawnTask() != null)
                    player.getSummoning().getSpawnTask().setValidItems(validStoredItems);
            }

            if (reader.has("charm-imp")) {
                int[] charmImpConfig = builder.fromJson(reader.get("charm-imp").getAsJsonArray(), int[].class);
                player.getSummoning().setCharmimpConfig(charmImpConfig);
            }

            if (reader.has("blowpipe-charge-item") || reader.has("blowpipe-charge-amount")) {
                player.getBlowpipeLoading().getContents().setCount(reader.get("blowpipe-charge-item").getAsInt(),
                        reader.get("blowpipe-charge-amount").getAsInt());
            }

            if (reader.has("friends")) {
                long[] friends = builder.fromJson(reader.get("friends").getAsJsonArray(), long[].class);

                for (long l : friends) {
                    player.getRelations().getFriendList().add(l);
                }
            }
            if (reader.has("ignores")) {
                long[] ignores = builder.fromJson(reader.get("ignores").getAsJsonArray(), long[].class);

                for (long l : ignores) {
                    player.getRelations().getIgnoreList().add(l);
                }
            }

            if (reader.has("loyalty-titles")) {
                player.setUnlockedLoyaltyTitles(
                        builder.fromJson(reader.get("loyalty-titles").getAsJsonArray(), boolean[].class));
            }

            if (reader.has("max-cape-colors")) {
                int[] colors = builder.fromJson(reader.get("max-cape-colors").getAsJsonArray(), int[].class);
                player.setMaxCapeColors(colors);
            }

            if (reader.has("player-title")) {
                player.setTitle(reader.get("player-title").getAsString());
            }

            if (player.perkReset) {
                if (reader.has("achievements")) {
                    player.getAchievementTracker().load(reader.get("achievements"));
                }
            }

            if (reader.has("favourite-teles")) {
                player.getTeleportInterface().setFavourites(builder.fromJson(reader.get("favourite-teles").getAsJsonArray(), String[].class));
            }


            DonationBonds.checkForRankUpdate(player);

        } catch (Exception e) {
            e.printStackTrace();
            return LoginResponses.LOGIN_COULD_NOT_COMPLETE;
        }

        return LoginResponses.LOGIN_SUCCESSFUL;
    }

}