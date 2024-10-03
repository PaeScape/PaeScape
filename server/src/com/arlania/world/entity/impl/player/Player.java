package com.arlania.world.entity.impl.player;

import com.arlania.DiscordBot;
import com.arlania.GameLoader;
import com.arlania.GameServer;
import com.arlania.GameSettings;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.engine.task.impl.PlayerDeathTask;
import com.arlania.engine.task.impl.WalkToTask;
import com.arlania.model.*;
import com.arlania.model.Locations.Location;
import com.arlania.model.container.impl.*;
import com.arlania.model.container.impl.Bank.BankSearchAttributes;
import com.arlania.model.definitions.WeaponAnimations;
import com.arlania.model.definitions.WeaponInterfaces;
import com.arlania.model.definitions.WeaponInterfaces.WeaponInterface;
import com.arlania.model.input.Input;
import com.arlania.net.PlayerSession;
import com.arlania.net.SessionState;
import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketSender;
import com.arlania.util.FrameUpdater;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.util.Stopwatch;
import com.arlania.util.everythingrs.voting.Vote;
import com.arlania.world.World;
import com.arlania.world.content.BankPin.BankPinAttributes;
import com.arlania.world.content.*;
import com.arlania.world.content.DropLog.DropLogEntry;
import com.arlania.world.content.LoyaltyProgramme.LoyaltyTitles;
import com.arlania.world.content.StartScreen.GameModes;
import com.arlania.world.content.achievements.AchievementInterface;
import com.arlania.world.content.achievements.AchievementTrackers;
import com.arlania.world.content.clan.ClanChat;
import com.arlania.world.content.clog.CollectionLog;
import com.arlania.world.content.combat.CombatFactory;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.magic.CombatSpell;
import com.arlania.world.content.combat.prayer.CurseHandler;
import com.arlania.world.content.combat.prayer.PrayerHandler;
import com.arlania.world.content.combat.pvp.PlayerKillingAttributes;
import com.arlania.world.content.combat.range.CombatRangedAmmo.RangedWeaponData;
import com.arlania.world.content.combat.strategy.CombatStrategies;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.content.combat.weapon.CombatSpecial;
import com.arlania.world.content.combat.weapon.FightType;
import com.arlania.world.content.dialogue.Dialogue;
import com.arlania.world.content.globalevents.GlobalEventHandler;
import com.arlania.world.content.globalevents.GlobalEvent;
import com.arlania.world.content.grandexchange.GrandExchangeSlot;
import com.arlania.world.content.marketplace.DeathsCoffer;
import com.arlania.world.content.minigames.Minigame;
import com.arlania.world.content.minigames.MinigameAttributes;
import com.arlania.world.content.minigames.impl.Dueling;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.content.pos.TradingPostManager;
import com.arlania.world.content.scripts.ScriptAttributes;
import com.arlania.world.content.skill.SkillManager;
import com.arlania.world.content.skill.impl.construction.ConstructionData.HouseLocation;
import com.arlania.world.content.skill.impl.construction.ConstructionData.HouseTheme;
import com.arlania.world.content.skill.impl.construction.HouseFurniture;
import com.arlania.world.content.skill.impl.construction.Portal;
import com.arlania.world.content.skill.impl.construction.Room;
import com.arlania.world.content.skill.impl.prayer.BonesData;
import com.arlania.world.content.skill.impl.skiller.Skiller;
import com.arlania.world.content.skill.impl.skiller.SkillerTasks;
import com.arlania.world.content.skill.impl.slayer.Slayer;
import com.arlania.world.content.skill.impl.slayer.SlayerTasks;
import com.arlania.world.content.skill.impl.summoning.CharmingImp;
import com.arlania.world.content.skill.impl.summoning.Pouch;
import com.arlania.world.content.skill.impl.summoning.Summoning;
import com.arlania.world.content.teleport.TeleportInterface;
import com.arlania.world.content.transportation.TeleportHandler;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.GroundItemManager;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.antibotting.ActionTracker;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import org.javacord.api.entity.message.MessageBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;

public class Player extends Character {

    private final TeleportInterface teleportInterface = new TeleportInterface(this);
    private final AchievementTrackers achievementTracker = new AchievementTrackers(this);
    private final TradingPostManager tradingPostManager = new TradingPostManager(this);
    private final Map<String, Object> attributes = new HashMap<>();
    private final UIMStorage uimStorage = new UIMStorage(this);
    private final Bank bank = new Bank(this);
    //Timers (Stopwatches)
    private final Stopwatch sqlTimer = new Stopwatch();
    private final Stopwatch foodTimer = new Stopwatch();
    private final Stopwatch potionTimer = new Stopwatch();
    private final Stopwatch lastRunRecovery = new Stopwatch();
    private final Stopwatch clickDelay = new Stopwatch();
    private final Stopwatch oddskullDelay = new Stopwatch();
    private final Stopwatch lastItemPickup = new Stopwatch();
    private final Stopwatch lastYell = new Stopwatch();
    private final Stopwatch lastSql = new Stopwatch();
    private final Stopwatch afkAttack = new Stopwatch();
    private final Stopwatch lastVengeance = new Stopwatch();
    private final Stopwatch emoteDelay = new Stopwatch();
    private final Stopwatch specialRestoreTimer = new Stopwatch();
    private final Stopwatch lastSummon = new Stopwatch();
    private final Stopwatch recordedLogin = new Stopwatch();
    private final Stopwatch creationDate = new Stopwatch();
    private final Stopwatch tolerance = new Stopwatch();
    private final Stopwatch lougoutTimer = new Stopwatch();
    /**
     * * INSTANCES **
     */
    private final CopyOnWriteArrayList<DropLogEntry> dropLog = new CopyOnWriteArrayList<DropLogEntry>();
    private final List<Player> localPlayers = new LinkedList<Player>();
    private final List<NPC> localNpcs = new LinkedList<NPC>();
    private final List<NPC> localAggroNpcs = new LinkedList<NPC>();
    private final PlayerSession session;
    private final PlayerProcess process = new PlayerProcess(this);
    private final PlayerKillingAttributes playerKillingAttributes = new PlayerKillingAttributes(this);
    private final MinigameAttributes minigameAttributes = new MinigameAttributes();
    private final ScriptAttributes scriptAttributes = new ScriptAttributes();
    private final BankPinAttributes bankPinAttributes = new BankPinAttributes();
    private final BankSearchAttributes bankSearchAttributes = new BankSearchAttributes();
    private final BonusManager bonusManager = new BonusManager();
    private final PointsHandler pointsHandler = new PointsHandler(this);
    private final ActionTracker actionTracker = new ActionTracker(this);
    private final PacketSender packetSender = new PacketSender(this);
    private final Appearance appearance = new Appearance(this);
    private final FrameUpdater frameUpdater = new FrameUpdater();
    private final PlayerRate rate = PlayerRate.FIVE;
    private final SkillManager skillManager = new SkillManager(this);
    private final PlayerRelations relations = new PlayerRelations(this);
    private final ChatMessage chatMessages = new ChatMessage();
    private final Inventory inventory = new Inventory(this);
    private final Equipment equipment = new Equipment(this);
    private final Overrides overrides = new Overrides(this);
    private final PriceChecker priceChecker = new PriceChecker(this);
    private final Trading trading = new Trading(this);
    private final Dueling dueling = new Dueling(this);
    private final Slayer slayer = new Slayer(this);
    private final Skiller skiller = new Skiller(this);
    //private Farming farming = new Farming(this);
    private final Summoning summoning = new Summoning(this);
    private final Bank[] bankTabs = new Bank[9];
    private final BlowpipeLoading blowpipeLoading = new BlowpipeLoading(this);
    private final int[] leechedBonuses = new int[7];
    //Holiday Events
    public boolean halloween23 = false;
    public int restorationTick = 0;
    public int activityTicks = 0;
    public int doubleExpTimer = 0;
    public boolean[] lightbearerOptions = {false, false, false, false, false, false, false, false, false, false};
    public int[] achievementAbilities = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public String lastGameMessage = "";

    public boolean boxScape = false;

    public int overrideAppearance = 0;
    public int WeekOfYear = 0;
    public int WildyPoints = 0;
    public int seasonMonth = 0;
    public int seasonYear = 0;
    public int seasonPoints = 0;
    public int seasonalTier = 0;
    public String linkedMain = "";
    public int baseLevelGoal = 0;
    public String seasonalLeader = "";
    public int seasonalTeleportUnlocks = 0;
    public int seasonalPP = 0;
    public int seasonalMBox = 0;
    public int seasonalEMBox = 0;
    public int seasonalCrystalKeys = 0;
    public int seasonalSBox = 0;
    public int seasonalEventPass = 0;
    public int seasonalDonationTokens = 0;
    public int seasonalRing = 0;
    public int[] seasonalPerks = {0, 0, 0, 0, 0, 0, 0, 0};
    public int[] seasonalTrainingTeleports = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    public int[] seasonalDungeonTeleports = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public int[] seasonalBossTeleports = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public int[] seasonalMinigameTeleports = {0, 0, 0, 0, 0, 0};
    public int[] seasonalRaidsTeleports = {0, 0, 0, 0, 0};
    public int[] donatorPets = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public int[] activeDonatorPets = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public int[] holidayPets = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public boolean worldFilterDrops = true;
    public boolean worldFilterLevels = true;
    public boolean worldFilterSeasonal = true;
    public boolean worldFilterMinigames = true;
    public boolean worldFilterStatus = true;
    public boolean worldFilterEvents = true;
    public boolean worldFilterYell = true;
    public boolean worldFilterPVP = true;
    public boolean worldFilterReminders = true;
    public boolean personalFilterPaepoints = true;
    public boolean personalFilterWildypoints = true;
    public boolean personalFilterBossKills = true;
    public boolean personalFilterImp = true;
    public boolean personalFilterHerbicide = true;
    public boolean personalFilterBonecrusher = true;
    public boolean personalFilterMasteryTriggers = true;
    public boolean personalFilterPouch = true;
    public boolean personalFilterKeyLoot = true;
    public boolean personalFilterDirtBag = true;
    public boolean personalFilterCurses = true;
    public boolean personalFilterGathering = true;
    public boolean personalFilterAdze = true;
    //Subscriptions
    public int subscriptionStartDate = 0;
    public int subscriptionEndDate = 0;
    //Quests
    public int tutorialIsland = 0;
    public Player interactingPlayer;
    public String homeTele = "Home";
    public int logins = 0;
    public boolean inRandom = false;
    public boolean notingItems = false;
    public long candyCredit = 0;
    public double wealthBoost = 0;
    public int meleeMaxHit = 0;
    public int rangedMaxHit = 0;
    public int magicMaxHit = 0;
    public int dailyFreebie = 0;
    public int monthlyFreebie = 0;
    public int savedSpell = -1;
    public boolean presetLoad = true;
    public boolean presetSave = false;
    public int presetOption = 0;
    public int trivia = 0;
    public double totalXP = 0;
    public int specialAnimTimer = 25;
    public String questTab = "playerpanel";
    public String slayerQty = "random";
    public String artisanQty = "random";
    public boolean inDaily = false;
    public int DailyKills = 0;
    public boolean tempRngBoost = false;
    public int slayerInstanceWave = 0;
    public int slayerWaveQty = 0;
    public int slayerWaveKills = 0;
    public int slayerInstanceKey = 0;
    public boolean inMole = false;
    public boolean inKQ = false;
    public boolean inDKS = false;
    public boolean inSaradomin = false;
    public boolean inZamorak = false;
    public boolean inArmadyl = false;
    public boolean inBandos = false;
    public boolean inCerberus = false;
    public boolean inSire = false;
    public boolean inThermo = false;
    public boolean inCorporeal = false;
    public boolean inKBD = false;
    public int instanceSpawns = 0;
    public int instanceKC = 0;
    public int instanceBoost = 0;
    public boolean minionSpawn1 = false;
    public boolean minionSpawn2 = false;
    public boolean minionSpawn3 = false;
    public int minionID1 = 0;
    public int minionID2 = 0;
    public int minionID3 = 0;
    public boolean chatFilter = false;
    public boolean personalEvent = false;
    public String personalEventType = "";
    public int playerEventTimer = 0;
    public boolean accelerateEvent = false;
    public boolean maxHitEvent = false;
    public boolean accuracyEvent = false;
    public boolean loadedEvent = false;
    public boolean doubleExpEvent = false;
    public boolean doubleLoot = false;
    public boolean droprateEvent = false;
    public boolean doubleBossPointEvent = false;
    public boolean doubleSlayerPointsEvent = false;
    public boolean doubleSkillerPointsEvent = false;
    public boolean eventBoxEvent = false;
    public boolean bossKillsEvent = false;
    public int accelerateEventTimer = 0;
    public int maxHitEventTimer = 0;
    public int accuracyEventTimer = 0;
    public int loadedEventTimer = 0;
    public int doubleExpEventTimer = 0;
    public int doubleLootTimer = 0;
    public int droprateEventTimer = 0;
    public int doubleBossPointEventTimer = 0;
    public int doubleSlayerPointsEventTimer = 0;
    public int doubleSkillerPointsEventTimer = 0;
    public int universalDropEventTimer = 0;
    public int bossKillsEventTimer = 0;
    public int globalBossKills = 0;
    public int totalGlobalBossKills = 0;
    public double totalUpgradeTiers = 0;
    public int idHelmUpgrade = 0;
    public int qtyHelmUpgrade = 0;
    public int idBodyUpgrade = 0;
    public int qtyBodyUpgrade = 0;
    public int idLegsUpgrade = 0;
    public int qtyLegsUpgrade = 0;
    public int idWeaponUpgrade = 0;
    public int qtyWeaponUpgrade = 0;
    public int idShieldUpgrade = 0;
    public int qtyShieldUpgrade = 0;
    public int idBootsUpgrade = 0;
    public int qtyBootsUpgrade = 0;
    public int idRingUpgrade = 0;
    public int qtyRingUpgrade = 0;
    public int idCapeUpgrade = 0;
    public int qtyCapeUpgrade = 0;
    public int idGlovesUpgrade = 0;
    public int qtyGlovesUpgrade = 0;
    public int idAmuletUpgrade = 0;
    public int qtyAmuletUpgrade = 0;
    public String withdrawAmount = "1";
    public int magicPaperAmount = 1;
    public boolean rsog21 = false;
    public boolean noobsown21 = false;
    public boolean walkchaos21 = false;
    public boolean wr3ckedyou = false;
    public boolean ipkmaxjr = false;
    public int randomx = 0;
    public int randomy = 0;
    public int randomz = 0;
    public boolean displayHUD = false;
    public int hudChoices = 0;
    public boolean hudOvl = false;
    public boolean hudFire = false;
    public boolean hudPoison = false;
    public boolean hudBonus = false;
    public boolean hudSlayer = false;
    public boolean hudSkiller = false;
    public boolean hudCballs = false;
    public boolean hudBPexp = false;
    public boolean hudBPkills = false;
    public boolean hudEPexp = false;
    public boolean hudEPkills = false;
    public boolean hudPETimer = false;
    public String hudColor = "cya";
    //Nightmare staff specials
    public boolean Volatile = false;
    public boolean Eldritch = false;
    public boolean Harmonised = false;
    //Custom Potion Effects
    public int aggressorTime = 0;
    public int aggressorRadius = 1;
    public int flashbackTime = 0;
    //Slayer specials
    public boolean dragonfireSpec = false;
    public boolean frostSpec = false;
    public boolean vampiricSpec = false;
    public boolean venomousSpec = false;
    public int instanceHeight = 0;
    public int nexWave = 0;
    public int barrowsRaidWave = 0;
    public int tobWave = 0;
    public int coxWave = 0;
    public int gauntletTick = 0;
    public Position gauntletSpikes;
    public boolean inGauntlet = false;
    public boolean GauntletLoot = false;
    public int GauntletTimer = 0;
    public int GauntletPoints = 0;
    public int GauntletKC = 0;
    public int HunllefKills = 0;
    public boolean inWintertodt = false;
    public boolean WintertodtLoot = false;
    public int WintertodtPoints = 0;
    public int WintertodtKC = 0;
    public boolean addingToBrazier = false;
    public int petCount = 0;
    public boolean petStorageUpdate = false;
    public boolean petStorageUpdate2 = false;
    public int petStorage1 = -1;
    public int petStorage2 = -1;
    public int petStorage3 = -1;
    public int petStorage4 = -1;
    public int petStorage5 = -1;
    public int petStorage6 = -1;
    public int petStorage7 = -1;
    public int petStorage8 = -1;
    public int petStorage9 = -1;
    public int petStorage10 = -1;
    public int petStorage11 = -1;
    public int petStorage12 = -1;
    public int petStorage13 = -1;
    public int petStorage14 = -1;
    public int petStorage15 = -1;
    public int petStorage16 = -1;
    public int petStorage17 = -1;
    public int petStorage18 = -1;
    public int petStorage19 = -1;
    public int petStorage20 = -1;
    public int petStorage21 = -1;
    public int petStorage22 = -1;
    public int petStorage23 = -1;
    public int petStorage24 = -1;
    public int petStorage25 = -1;
    public int petStorage26 = -1;
    public int petStorage27 = -1;
    public int petStorage28 = -1;
    public int petStorage29 = -1;
    public int petStorage30 = -1;
    public int petStorage31 = -1;
    public int petStorage32 = -1;
    public int petStorage33 = -1;
    public int petStorage34 = -1;
    public int petStorage35 = -1;
    public int petStorage36 = -1;
    public int petStorage37 = -1;
    public int petStorage38 = -1;
    public int petStorage39 = -1;
    public int petStorage40 = -1;
    public boolean gambleWin = false;
    public int betPoker = 0;
    public int betBlackjack = 0;
    public int betHL = 0;
    public int prizeHL = 0;
    public int currentHL = 0;
    public int previousHL = 0;
    public int betRouletteRed = 0;
    public int betRouletteBlack = 0;
    public int betRoulette00 = 0;
    public int betRoulette0 = 0;
    public int betRoulette1 = 0;
    public int betRoulette2 = 0;
    public int betRoulette3 = 0;
    public int betRoulette4 = 0;
    public int betRoulette5 = 0;
    public int betRoulette6 = 0;
    public int betRoulette7 = 0;
    public int betRoulette8 = 0;
    public int betRoulette9 = 0;
    public int betRoulette10 = 0;
    public int betRoulette11 = 0;
    public int betRoulette12 = 0;
    public int betRoulette13 = 0;
    public int betRoulette14 = 0;
    public int betRoulette15 = 0;
    public int betRoulette16 = 0;
    public int betRoulette17 = 0;
    public int betRoulette18 = 0;
    public int betRoulette19 = 0;
    public int betRoulette20 = 0;
    public int betRoulette21 = 0;
    public int betRoulette22 = 0;
    public int betRoulette23 = 0;
    public int betRoulette24 = 0;
    public int betRoulette25 = 0;
    public int betRoulette26 = 0;
    public int betRoulette27 = 0;
    public int betRoulette28 = 0;
    public int betRoulette29 = 0;
    public int betRoulette30 = 0;
    public int betRoulette31 = 0;
    public int betRoulette32 = 0;
    public int betRoulette33 = 0;
    public int betRoulette34 = 0;
    public int betRoulette35 = 0;
    public int betRoulette36 = 0;
    public int fishActions = 0;
    public boolean healingEffects = true;
    public boolean overloadPlus = false;
    public int[] communityEvents2024 = {
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0
    };

    public int[] looterSettings = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public int bingoCompletions = 0;
    public int coxRaidBonus = 0;
    public int tobRaidBonus = 0;
    public int shrRaidBonus = 0;
    public int gwdRaidBonus = 0;
    public int chaosRaidBonus = 0;
    public boolean looterBanking = false;
    public int dailyEvent = 0;
    public int summonHeal = 0;
    public boolean gwdRaidLoot = false;
    public boolean barrowsRaidLoot = false;
    public boolean chaosRaidLoot = false;
    public int chaosLootWave = 0;
    public int barrowsLootWave = 0;
    public boolean gwdRaid = false;
    public boolean barrowsRaid = false;
    public boolean tobRaid = false;
    public boolean coxRaid = false;
    public boolean chaosRaid = false;
    public boolean instancedBosses = false;
    public int gwdRaidKills = 0;
    public int gwdRaidWave = 0;
    public int chaosRaidKills = 0;
    public int chaosRaidWave = 0;
    public int barrowsRaidKills = 0;
    public int totalBarrowsRaidKills = 0;
    public int teamSize = 0;
    public boolean prayerDrain = false;
    public boolean bleed = false;
    public boolean npcMaxAccuracy = false;
    public boolean npcMaxHit = false;
    public boolean fallingObjects = false;
    public boolean npcImmuneMelee = false;
    public boolean npcImmuneRange = false;
    public boolean npcImmuneMagic = false;
    public int fallingObjectsTimer = 0;
    public int bleedTimer = 0;
    public int prayerDrainTimer = 0;
    public int absorbBonus = 0;
    public int nobilityBoost = 0;
    public int upgradeBoost = 0;
    public int pvmBoost = 0;
    public int slayerBoost = 0;
    public int perkBoost = 0;
    public boolean activeDoubleXP = false;
    public boolean activeBonusXP = false;
    public boolean activeBonusRNG = false;
    public boolean autosupply = false;
    public boolean autobone = false;
    public boolean autokey = false;
    public boolean alchablecoins = false;
    public int totalSlayerTasks = 0;
    public int totalSkillerTasks = 0;
    public int BForeType = 0;
    public int BFbarType = 0;
    public int BForeQty = 0;
    public int BFbarQty = 0;
    public int CrystalChests = 0;
    public int TaintedChests = 0;
    public String activeInterface = "";
    public String activeMenu = "";
    public int prestige = 0;
    public int extraPrestiges = 0;
    public int perkUpgrades = 0;
    public boolean perkReset = false;
    public int difficulty = 0;
    public int partyDifficulty = 0;

    public String lastSeasonal = "";
    public int seasonalCurrency = 0;

    //seasonal perks
    public boolean Harvester = false;
    public boolean Producer = false;
    public boolean Contender = false;
    public boolean Strategist = false;
    public boolean Gilded = false;
    public boolean Shoplifter = false;
    public boolean Impulsive = false;
    public boolean Rapid = false;
    public boolean Bloodthirsty = false;
    public boolean Infernal = false;
    public boolean Summoner = false;
    public boolean Ruinous = false;
    public boolean Gladiator = false;
    public boolean Warlord = false;
    public boolean Deathless = false;
    public boolean Executioner = false;

    //perks
    public int Berserker = 0;
    public int Bullseye = 0;
    public int Prophetic = 0;
    public int Vampirism = 0;
    public int Survivalist = 0;
    public int Accelerate = 0;
    public int Lucky = 0;
    public int Prodigy = 0;
    public int Unnatural = 0;
    public int Artisan = 0;
    public int Looter = 0;
    public int Botanist = 0;
    public int Specialist = 0;
    public int Fabricator = 0;
    public int Equilibrium = 0;
    public int Flash = 0;
    public int Loaded = 0;
    public int Lifelink = 0;
    public int FluidStrike = 0;
    public int QuickShot = 0;
    public int DoubleCast = 0;
    public int Merchant = 0;
    //abilities
    public int respawnTime = 20;
    public int instanceRespawnBoost = 0;
    //mastery perks
    public int completedLogs = 0;
    public int masteryPerksUnlocked = 0;
    public int Detective = 0;
    public int Critical = 0;
    public int Flurry = 0;
    public int Consistent = 0;
    public int Dexterity = 0;
    public int Stability = 0;
    public int Absorb = 0;
    public int Efficiency = 0;
    public int Efficacy = 0;
    public int Devour = 0;
    public int Wealthy = 0;
    public int Reflect = 0;
    public boolean masteryFix = false;
    public boolean passFix = false;
    //wilderness perks
    public int wildAccelerate = 0;
    public int wildLooter = 0;
    public int wildSpecialist = 0;
    public int wildHoarder = 0;
    public int wildVesta = 0;
    public int wildStatius = 0;
    public int wildMorrigan = 0;
    public int wildZuriel = 0;
    public int wildSavior = 0;
    public int wildAlchemize = 0;
    public int wildBotanist = 0;
    public int wildEnraged = 0;
    public int wildEnragedKills = 0;
    public int wildTainted = 0;
    public int wildBoost = 0;
    public int wildRisk = 0;
    public boolean boostedDivinePool = false;
    public boolean prodigyDisplay = true;
    //unlocks
    public boolean turmoilRanged = false;
    public boolean turmoilMagic = false;
    //Nobility
    public long kingdomDonation = 0;
    public long kingdomExperience = 0;
    public long kingdomKC = 0;
    public long kingdomGatherer = 0;
    public boolean tobCheck = false;
    public int tobCount = 0;
    public int tobRemaining = 0;
    public boolean coxCheck = false;
    public int coxCount = 0;
    public int coxRemaining = 0;
    public boolean chaosCheck = false;
    public int chaosCount = 0;
    public int chaosRemaining = 0;
    public int crystalCharges = 0;
    public boolean achievementCheck = false;
    public boolean christmas2021start = false;
    public boolean christmas2021complete = false;
    public boolean gauntletBoss = false;
    public boolean snow = false;
    //Stronghold Raid
    public int strongholdRaidPoints;
    public int floorOneKey;
    public int floorTwoKey;
    public int floorThreeKey;
    public int floorFourKey;
    public int strongholdRaidDeaths;
    public int strongholdRaidFloor;
    public int strongholdLootFloor;
    public boolean strongholdRaid;
    public Map<Integer, String> cofferData;
    public boolean isBot = false;
    /*
     * Variables for DropTable & Player Profiling
     *@author Levi Patton
     *@www.rune-server.org/members/auguryps
     */
    public Player dropLogPlayer;
    public boolean dropLogOrder;
    public int clue1Amount;
    public int clue2Amount;
    public int clue3Amount;
    public int clueLevel;
    public Item[] puzzleStoredItems;
    public int sextantGlobalPiece;
    public double sextantBarDegree;
    public int rotationFactor;
    public int sextantLandScapeCoords;
    public int sextantSunCoords;
    public int tempRaidsDifficulty = 0;

    //Farming
    public int[] farmingHerbPatchID = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public int[] farmingHerbPatchTimer = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public int[] farmingHerbPatchQty = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    /**
     * * INTS **
     */
    public int destination = 0;
    public int lastClickedTab = 0;
    public int timeOnline;
    public boolean mysteryPass = false;
    public int mpRaidsDone = 0;
    public int mpBossKills = 0;
    public boolean mpRaidEasy = false;
    public boolean mpRaidMedium = false;
    public boolean mpRaidHard = false;
    public boolean mpRaidExpert = false;
    public boolean mpBossEasy = false;
    public boolean mpBossMedium = false;
    public boolean mpBossHard = false;
    public boolean mpBossExpert = false;
    public boolean mpComplete = false;
    public boolean battlePass = false;
    public int bpExperience = 0;
    public int bpBossKills = 0;
    public boolean bpSkillEasy = false;
    public boolean bpSkillMedium = false;
    public boolean bpSkillHard = false;
    public boolean bpSkillExpert = false;
    public boolean bpBossEasy = false;
    public boolean bpBossMedium = false;
    public boolean bpBossHard = false;
    public boolean bpBossExpert = false;
    public boolean bpComplete = false;
    public boolean eventPass = false;
    public int epExperience = 0;
    public int epBossKills = 0;
    public boolean epSkillEasy = false;
    public boolean epSkillMedium = false;
    public boolean epSkillHard = false;
    public boolean epSkillExpert = false;
    public boolean epBossEasy = false;
    public boolean epBossMedium = false;
    public boolean epBossHard = false;
    public boolean epBossExpert = false;
    public boolean epComplete = false;
    public ArrayList<Integer> walkableInterfaceList = new ArrayList<>();
    public long lastHelpRequest;
    public long lastAuthClaimed;
    public GameModes selectedGameMode;
    public int[] oldSkillLevels = new int[25];
    public int[] oldSkillXP = new int[25];
    public int[] oldSkillMaxLevels = new int[25];
    //CoX
    private Item coxRaidsLoot;
    private Item coxRaidsLootSecond;
    private int graphicSwap;
    private long dragonScimInjury;
    private AchievementInterface achievementInterface;
    private DropTableInterface dropTableInterface;
    private boolean placeholders = false;
    private int[] maxCapeColors = {65214, 65200, 65186, 62995};
    private String title = "";
    private boolean active;
    private Minigame minigame = null;
    private int hardwareNumber;
    private int achievementPoints;
    private int totalLoyaltyPointsEarned;
    private int bossPoints;
    private int votePoints;
    private int daily;
    private int completedBarrows;
    private int rfdRaidWave;
    private int chaosKC;
    private int shrKC;
    private int coxKC;
    private int tobKC;
    private int gwdRaidKC;
    private int chaosRaid1;
    private int chaosRaid2;
    private int chaosRaid3;
    private int chaosRaid4;
    private int chaosRaid5;
    private int chaosRaid6;
    private int chaosRaid7;
    private int chaosRaid8;
    private int chaosRaid9;
    private int chaosRaid10;
    private int completedChaos;
    private int PayDirt;
    private int RandomEventTimer;
    private int BonusTime;
    private int RareCandy;
    private int PaePoints;
    private int exprate;
    private int LastRecallx;
    private int LastRecally;
    private int LastRecallz;
    private int raidPoints;
    private int LearnAugury;
    private int LearnRigour;
    private int LearnCurses;
    private int LearnAncients;


    //Relics and Upgrades
    private int LearnLunars;
    private int BossTeleports;
    private int MinigameTeleports;
    private int WildernessTeleports;
    private int AncientPages;
    private int ArmadylPages;
    private int BandosPages;
    private int GuthixPages;
    private int SaradominPages;
    private int ZamorakPages;
    private PlayerDropLog playerDropLog = new PlayerDropLog();
    private ProfileViewing profile = new ProfileViewing();
    private MailBox mailBox = new MailBox(this);
    private Bingo bingo = new Bingo(this);
    /**
     * * STRINGS **
     */
    private String username;
    private byte[] passwordHash;
    private String hardwareID;
    private String emailAddress;
    private String hostAddress;
    private String clanChatName;
    private HouseLocation houseLocation;
    private HouseTheme houseTheme;
    /**
     * * LONGS *
     */
    private Long longUsername;
    private long moneyInPouch;
    private long totalPlayTime;
    private ArrayList<HouseFurniture> houseFurniture = new ArrayList<HouseFurniture>();
    private ArrayList<Portal> housePortals = new ArrayList<>();
    private CharacterAnimations characterAnimations = new CharacterAnimations();
    private Presets presets = new Presets(new int[12][28][2], new int[12][14][2]);
    private StaffRights staffRights = StaffRights.PLAYER;
    //Update this for subscriptions
    //TODO
    private Subscriptions subscription = Subscriptions.PLAYER;
    private Room[][][] houseRooms = new Room[5][13][13];
    private PlayerInteractingOption playerInteractingOption = PlayerInteractingOption.NONE;
    private GameMode gameMode = GameMode.NORMAL;
    private CombatType lastCombatType = CombatType.MELEE;
    private FightType fightType = FightType.UNARMED_PUNCH;
    private Prayerbook prayerbook = Prayerbook.NORMAL;
    private MagicSpellbook spellbook = MagicSpellbook.NORMAL;
    private LoyaltyTitles loyaltyTitle = LoyaltyTitles.NONE;
    private ClanChat currentClanChat;
    private Input inputHandling;
    private WalkToTask walkToTask;
    private Shop shop;
    private GameObject interactingObject;
    private Item interactingItem;
    private Dialogue dialogue;
    private DwarfCannon cannon;
    private CombatSpell autocastSpell, castSpell, previousCastSpell;
    private RangedWeaponData rangedWeaponData;
    private CombatSpecial combatSpecial;
    private WeaponInterface weapon;
    private Item untradeableDropItem;
    private Object[] usableObject;
    private GrandExchangeSlot[] grandExchangeSlots = new GrandExchangeSlot[6];
    private Task currentTask;
    private Position resetPosition;
    private Pouch selectedPouch;
    private int[] pvpEquipmentCharges = new int[15];
    private int[] brawlerCharges = new int[9];
    private int[] forceMovement = new int[7];
    private int[] ores = new int[2];
    private int[] constructionCoords;
    private int recoilCharges;
    private int runEnergy = 100;
    private int currentBankTab;
    private int interfaceId, walkableInterfaceId, multiIcon;
    private int dialogueActionId;
    private int overloadPotionTimer, prayerRenewalPotionTimer;
    private int amountDonated;
    private int wildernessLevel;
    private int fireAmmo;
    private int specialPercentage = 100;
    private int skullIcon = -1, skullTimer;
    private int teleblockTimer;
    private int poisonImmunity;
    private int dragonfireImmunity;
    private int shadowState;
    private int effigy;
    private int dfsCharges;

    /*
     * Fields
     */
    private int playerViewingIndex;
    private int staffOfLightEffect;
    private int minutesBonusExp = -1;
    private int selectedGeSlot = -1;
    private int selectedGeItem = -1;
    private int geQuantity;
    private int gePricePerItem;
    private int selectedSkillingItem;
    private int currentBookPage;
    private int storedRuneEssence, storedPureEssence;
    private int coalBag;
    private int BagDragonstone, BagDiamond, BagRuby, BagEmerald, BagSapphire;
    private int RunePouchTypeOne, RunePouchTypeTwo, RunePouchTypeThree, RunePouchTypeFour;
    private int RunePouchQtyOne, RunePouchQtyTwo, RunePouchQtyThree, RunePouchQtyFour;
    private int scytheCharges;
    private int sanguinestiCharges;
    private int nightmareCharges;
    private int surgeboxCharges;
    private int trapsLaid;
    private int skillAnimation;
    private int houseServant;
    private int houseServantCharges;
    private int servantItemFetch;
    private int portalSelected;
    private int constructionInterface;
    private int buildFurnitureId;
    private int buildFurnitureX;
    private int buildFurnitureY;
    private int combatRingType;
    /**
     * * BOOLEANS **
     */
    private boolean[] unlockedLoyaltyTitles = new boolean[12];
    private boolean[] crossedObstacles = new boolean[7];
    private boolean processFarming;
    private boolean crossingObstacle;
    private boolean targeted;
    private boolean isBanking, noteWithdrawal, swapMode;
    private boolean isMailBoxOpen;
    private boolean regionChange, allowRegionChangePacket;
    private boolean isDying;
    private boolean isRunning = true, isResting;
    private boolean experienceLocked = false;
    private boolean clientExitTaskActive;
    private boolean drainingPrayer;
    private boolean shopping;
    private boolean settingUpCannon;
    private boolean hasVengeance;
    private boolean acceptingAid;
    private boolean autoRetaliate;
    private boolean autocast;
    private boolean specialActivated;
    private boolean isCoughing;
    private boolean playerLocked;
    private boolean recoveringSpecialAttack;
    private boolean soundsActive, musicActive;
    private boolean newPlayer;
    private boolean openBank;
    private boolean inActive;
    private boolean inConstructionDungeon;
    private boolean isBuildingMode;
    private boolean voteMessageSent;
    private boolean receivedStarter;
    private boolean areCloudsSpawned;
    private Skill SkillerSkill;
    private CollectionLog collectionLog = new CollectionLog(this);
    private String discordName;
    private boolean discordLinked;

    public Player(PlayerSession playerIO) {
        super(GameSettings.DEFAULT_POSITION.copy());
        this.session = playerIO;
    }

    public static void activateSpecial(Player player) {

        if (!player.isSpecialActivated() && player.getEquipment().contains(19669) && player.getSpecialPercentage() == 100)
            CombatSpecial.activate(player);

    }

    public static void playerAutoAlchemize(Player player, Item item) {

        boolean alchable = false;

        for(int i = 0; i < GameSettings.ALCHABLES.length - 1; i++) {
            if (GameSettings.ALCHABLES[i] == item.getId()) {
                alchable = true;
            }
        }

        if (!alchable) {
            if (item.getDefinition().getName().contains("pet")) {
                playerAutoPickup(player, item);
                return;
            }
            if (item.getDefinition().getValue() > 100000) {
                playerAutoPickup(player, item);
                return;
            }
        }

        int alchValue = (int) (item.getDefinition().getValue() * .5 * item.getAmount());

        if(player.personalFilterGathering)
            player.getPacketSender().sendMessage("Your " + item.getName() + " is converted to " + alchValue + " coins!");

        player.setMoneyInPouch(player.getMoneyInPouch() + alchValue);
        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
    }

    public static void playerAutoPickup(Player player, Item item) {

        if ((player.getEquipment().contains(221140) || (player.getEquipment().contains(21077) && player.lightbearerOptions[4])) || player.getStaffRights().getStaffRank() > 2) {
            player.getInventory().add(item.getId(), item.getAmount());
            return;
        } else {
            for (int i = 0; i < item.getAmount(); i++) {
                GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(item.getId(), 1), player.getPosition(), player.getUsername(), false, 150, true, 200));
            }
        }
    }

    public static void checkLooterSettings(Player player, Item item) {

        int amount = item.getAmount();
        int itemId = item.getId();
        Position pos = player.getPosition();
        int type = 0;



        for (int i = 0; i < GameSettings.ALCHABLES.length - 1; i++) {
            if (item.getId() == GameSettings.ALCHABLES[i]) {
                type = 7;
            }
        }

        for (int i = 0; i < GameSettings.AUTO_ALCHS.length - 1; i++) {
            if (item.getId() == GameSettings.AUTO_ALCHS[i]) {
                type = 7;
            }
        }

        //player.looterSettings
        //[0] leather
        //[1] bars
        //[2] herbs
        //[3] logs
        //[4] ores
        //[5] gems
        //[6] fish
        //[7] seed
        //[8] ammo
        //[9] charms
        //[10] bones
        //[11] keys

        //0 = ground item (also for skilling relics)
        //1 = alchemize
        //2 = inventory

        if (type != 7) {
            if (item.getName().toLowerCase().contains("leather"))
                type = 0;
            else if (item.getName().toLowerCase().contains("bar"))
                type = 1;
            else if (item.getName().toLowerCase().contains("grimy"))
                type = 2;
            else if (item.getName().toLowerCase().contains("log"))
                type = 3;
            else if (item.getName().toLowerCase().contains("ore") || item.getName().toLowerCase().contains("essence"))
                type = 4;
            else if (item.getName().toLowerCase().contains("uncut"))
                type = 5;
            else if (item.getName().toLowerCase().contains("raw"))
                type = 6;
            else if (item.getName().toLowerCase().contains("runes") || item.getName().toLowerCase().contains("arrow"))
                type = 8;
            else if (item.getName().toLowerCase().contains("charm"))
                type = 9;
            else if (item.getName().toLowerCase().contains("bone"))
                type = 10;
            else if (item.getName().toLowerCase().contains("key"))
                type = 11;
            else
                type = 7;
        }

        switch (type) {

            case 0: //leather
                if (player.looterSettings[0] == 1) {
                    playerAutoAlchemize(player, item);
                    return;
                } else if (player.looterSettings[0] == 2) {
                    playerAutoPickup(player, item);
                    return;
                }
                break;

            case 1: //bars
                if (player.looterSettings[1] == 1) {
                    playerAutoAlchemize(player, item);
                    return;
                } else if (player.looterSettings[1] == 2) {
                    playerAutoPickup(player, item);
                    return;
                }
                break;

            case 2: //herbs
                //herbicide
                if (player.looterSettings[2] == 0) {
                    List<Integer> validHerbs = Arrays.asList(199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 215, 216, 217, 218, 219, 220, 2485, 2486, 3049, 3050, 3051, 3052);
                    if ((player.getInventory().contains(19675) || player.getEquipment().contains(19675)) && validHerbs.contains(item.getId())) {
                        Herbicide.dissolveHerb(player, itemId, amount);
                        return;
                    }
                } else if (player.looterSettings[2] == 1) {
                    playerAutoAlchemize(player, item);
                    return;
                } else if (player.looterSettings[2] == 2) {
                    playerAutoPickup(player, item);
                    return;
                }
                break;

            case 3: //logs
                if (player.looterSettings[3] == 1) {
                    playerAutoAlchemize(player, item);
                    return;
                } else if (player.looterSettings[3] == 2) {
                    playerAutoPickup(player, item);
                    return;
                }
                break;

            case 4: //ores
                if (player.looterSettings[4] == 1) {
                    playerAutoAlchemize(player, item);
                    return;
                } else if (player.looterSettings[4] == 2) {
                    playerAutoPickup(player, item);
                    return;
                }
                break;

            case 5: //gems
                if (player.looterSettings[5] == 1) {
                    playerAutoAlchemize(player, item);
                    return;
                } else if (player.looterSettings[5] == 2) {
                    playerAutoPickup(player, item);
                    return;
                }
                break;

            case 6: //fish
                if (player.looterSettings[6] == 1) {
                    playerAutoAlchemize(player, item);
                    return;
                } else if (player.looterSettings[6] == 2) {
                    playerAutoPickup(player, item);
                    return;
                }
                break;

            case 7: //alchables
                if (player.looterSettings[7] == 1) {
                    playerAutoAlchemize(player, item);
                    return;
                } else if (player.looterSettings[7] == 2) {
                    playerAutoPickup(player, item);
                    return;
                }
                for (int i = 0; i < item.getAmount(); i++) {
                    GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(item.getId(), 1), player.getPosition(), player.getUsername(), false, 150, true, 200));
                }
                break;

            case 8: //ammo
                if (player.looterSettings[8] == 1) {
                    playerAutoAlchemize(player, item);
                    return;
                } else if (player.looterSettings[8] == 2) {
                    playerAutoPickup(player, item);
                    return;
                }
                break;

            case 9: //charms
                //charming imp
                if (player.looterSettings[9] == 0) {
                    if (item.getId() == CharmingImp.GOLD_CHARM || item.getId() == CharmingImp.GREEN_CHARM || item.getId() == CharmingImp.CRIM_CHARM || item.getId() == CharmingImp.BLUE_CHARM) {
                        if (player.getInventory().contains(6500) && CharmingImp.handleCharmDrop(player, item.getId(), amount))
                            return;
                    }
                } else if (player.looterSettings[9] == 1) {
                    playerAutoAlchemize(player, item);
                    return;
                } else if (player.looterSettings[9] == 2) {
                    playerAutoPickup(player, item);
                    return;
                }
                break;

            case 10: //bones
                //bonecrusher
                if (player.looterSettings[10] == 0) {
                    //unnoted bones
                    if (player.getInventory().contains(18337) && BonesData.forId(item.getId()) != null) {
                        if (player.personalFilterBonecrusher) {
                            player.getPacketSender().sendMessage("Your Bonecrusher buries the <col=999999>" + item.getName() + "</col> automatically.");
                        }
                        player.getPacketSender().sendGlobalGraphic(new Graphic(777), pos);
                        player.getSkillManager().addExperience(Skill.PRAYER, BonesData.forId(item.getId()).getBuryingXP() * 2 * amount);
                        return;
                    }

                    //noted bones
                    if (player.getInventory().contains(18337) && (BonesData.forId(item.getId() - 1) != null) && amount < 10) {
                        player.getPacketSender().sendGlobalGraphic(new Graphic(777), pos);
                        player.getSkillManager().addExperience(Skill.PRAYER, BonesData.forId(item.getId() - 1).getBuryingXP() * 2 * amount);
                        return;
                    }
                } else if (player.looterSettings[10] == 1) {
                    playerAutoAlchemize(player, item);
                    return;
                } else if (player.looterSettings[10] == 2) {
                    playerAutoPickup(player, item);
                    return;
                }
                break;

            case 11: //keys

                if (player.looterSettings[11] == 2) {
                    playerAutoPickup(player, item);
                    return;
                }
                break;
        }

        //Add item as a ground item
        for (int i = 0; i < amount; i++) {
            GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(itemId, 1), player.getPosition(), player.getUsername(), false, 150, true, 200));
        }

    }

    public static boolean checkAchievementAbilities(Player player, String ability) {

        if (player.getLocation() == Location.SHR) {
            return false;
        }

        switch (ability) {

            case "banker":
                if (player.achievementAbilities[0] > 0) {
                    return true;
                }
                break;
            case "processor":
                if (player.achievementAbilities[1] > 0) {
                    return true;
                } else if(player.Producer) {
                    return true;
                }
                break;
            case "gatherer":
                if (player.achievementAbilities[2] > 0) {
                    return true;
                }
                break;
            case "sweeten":
                if (player.achievementAbilities[3] > 0) {
                    return true;
                }
                break;
            case "entertainer":
                if (player.achievementAbilities[4] > 0) {
                    return true;
                }
                break;
            case "combatant":
                if (player.achievementAbilities[5] > 0) {
                    return true;
                }
                break;
            case "cryptic":
                if (player.achievementAbilities[6] > 0) {
                    return true;
                }
                break;
            case "gambler":
                if (player.achievementAbilities[7] > 0) {
                    return true;
                }
                break;
            case "prosperous":
                if (player.achievementAbilities[8] > 0) {
                    return true;
                }
                break;
            case "bountiful":
                if (player.achievementAbilities[9] > 0) {
                    return true;
                }
                break;
        }

        return false;
    }

    public Item getCoxRaidsLoot() {
        return coxRaidsLoot;
    }

    public void setCoxRaidsLoot(Item coxRaidsLoot) {
        this.coxRaidsLoot = coxRaidsLoot;
    }

    public Item getCoxRaidsLootSecond() {
        return coxRaidsLootSecond;
    }

    public void setCoxRaidsLootSecond(Item coxRaidsLootSecond) {
        this.coxRaidsLootSecond = coxRaidsLootSecond;
    }

    public int getGraphicSwap() {
        return graphicSwap;
    }

    public void setGraphicSwap(int graphicSwap) {
        this.graphicSwap = graphicSwap;
    }

    /*
     * Grabs the player's RegionID
     */
    public int getRegionID() {
        int regionX = getPosition().getX() >> 3;
        int regionY = getPosition().getY() >> 3;
        return ((regionX / 8) << 8) + (regionY / 8);
    }

    public Map<Integer, String> getCofferData() {
        if (cofferData == null) {
            cofferData = new HashMap<>();
            DeathsCoffer.ensureConsistency(cofferData);
        }
        return cofferData;
    }

    public void loadCofferData(JsonElement coffers) {
        try {
            Type listType = new TypeToken<HashMap<Integer, String>>() {
            }.getType();

            cofferData = new Gson().fromJson(coffers, listType);
            DeathsCoffer.ensureConsistency(cofferData);
        } catch (Exception e) {
            GameServer.getLogger().log(Level.SEVERE, "No Deaths Coffer found!", e);
            this.sendMessage("@red@Tell an admin that your Coffers did not load!");
            cofferData = new HashMap<>();
        }
    }

    public long getDragonScimInjury() {
        return dragonScimInjury;
    }

    public void setDragonScimInjury(long dragonScimInjury) {
        this.dragonScimInjury = dragonScimInjury;
    }

    public TeleportInterface getTeleportInterface() {
        return teleportInterface;
    }

    public AchievementInterface getAchievementInterface() {
        return this.achievementInterface;
    }

    public void setAchievementInterface(AchievementInterface achievementInterface) {
        this.achievementInterface = achievementInterface;
    }

    public AchievementTrackers getAchievementTracker() {
        return this.achievementTracker;
    }

    public DropTableInterface getDropTableInterface() {
        if (this.dropTableInterface == null)
            this.dropTableInterface = new DropTableInterface(this);
        return this.dropTableInterface;
    }

    public void setDropTableInterface(DropTableInterface table) {
        this.dropTableInterface = table;
    }

    public boolean isPlaceholders() {
        return placeholders;
    }

    public void setPlaceholders(boolean placeholders) {
        this.placeholders = placeholders;
    }

    public int[] getMaxCapeColors() {
        return maxCapeColors;
    }

    public void setMaxCapeColors(int[] maxCapeColors) {
        this.maxCapeColors = maxCapeColors;
    }

    public TradingPostManager getTradingPostManager() {
        return tradingPostManager;
    }

    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String key) {
        return (T) attributes.get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String key, T fail) {
        Object object = attributes.get(key);
        return object == null ? fail : (T) object;
    }

    public boolean hasAttribute(String key) {
        return attributes.containsKey(key);
    }

    public void removeAttribute(String key) {
        attributes.remove(key);
    }

    public int getHardwareNumber() {
        return hardwareNumber;
    }

    public Player setHardwareNumber(int hardwareNumber) {
        this.hardwareNumber = hardwareNumber;
        return this;
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    @Override
    public void appendDeath() {
        if (!isDying) {
            setDying(true);
            TaskManager.submit(new PlayerDeathTask(this));
        }
    }

    public int getAchievementPoints() {
        return achievementPoints;
    }

    public void setAchievementPoints(int achievementPoints) {
        this.achievementPoints = achievementPoints;
    }

    public int getTotalLoyaltyPointsEarned() {
        return totalLoyaltyPointsEarned;
    }

    public void setTotalLoyaltyPointsEarned(int totalLoyaltyPointsEarned) {
        this.totalLoyaltyPointsEarned = totalLoyaltyPointsEarned;
    }

    public void incrementTotalLoyaltyPointsEarned(double amount) {
        totalLoyaltyPointsEarned += amount;
    }

    public int getBossPoints() {
        return bossPoints;
    }

    public void setBossPoints(int bossPoints) {
        this.bossPoints = bossPoints;
    }

    public int getVotePoints() {
        return votePoints;
    }

    public void setVotePoints(int votePoints) {
        this.votePoints = votePoints;
    }

    public int getDaily() {
        return daily;
    }

    public void setDaily(int daily) {
        this.daily = daily;
    }

    public int getCompletedBarrows() {
        return completedBarrows;
    }

    public void setCompletedBarrows(int completedBarrows) {
        this.completedBarrows = completedBarrows;
    }

    public int getrfdRaidWave() {
        return rfdRaidWave;
    }

    public void setrfdRaidWave(int rfdRaidWave) {
        this.rfdRaidWave = rfdRaidWave;
    }

    public int getchaosKC() {
        return chaosKC;
    }

    public void setchaosKC(int chaosKC) {
        this.chaosKC = chaosKC;
    }

    public int getshrKC() {
        return shrKC;
    }

    public void setshrKC(int shrKC) {
        this.shrKC = shrKC;
    }

    public int getCoxKC() {
        return coxKC;
    }

    public void setCoxKC(int coxKC) {
        this.coxKC = coxKC;
    }

    public int getTobKC() {
        return tobKC;
    }

    public void setTobKC(int tobKC) {
        this.tobKC = tobKC;
    }

    public int getGwdRaidKC() {
        return gwdRaidKC;
    }

    public void setGwdRaidKC(int gwdRaidKC) {
        this.gwdRaidKC = gwdRaidKC;
    }

    public int getChaosRaid1() {
        return chaosRaid1;
    }

    public void setChaosRaid1(int chaosRaid1) {
        this.chaosRaid1 = chaosRaid1;
    }

    public int getChaosRaid2() {
        return chaosRaid2;
    }

    public void setChaosRaid2(int chaosRaid2) {
        this.chaosRaid2 = chaosRaid2;
    }

    public int getChaosRaid3() {
        return chaosRaid3;
    }

    public void setChaosRaid3(int chaosRaid3) {
        this.chaosRaid3 = chaosRaid3;
    }

    public int getChaosRaid4() {
        return chaosRaid4;
    }

    public void setChaosRaid4(int chaosRaid4) {
        this.chaosRaid4 = chaosRaid4;
    }

    public int getChaosRaid5() {
        return chaosRaid5;
    }

    public void setChaosRaid5(int chaosRaid5) {
        this.chaosRaid5 = chaosRaid5;
    }

    public int getChaosRaid6() {
        return chaosRaid6;
    }

    public void setChaosRaid6(int chaosRaid6) {
        this.chaosRaid6 = chaosRaid6;
    }

    public int getChaosRaid7() {
        return chaosRaid7;
    }

    public void setChaosRaid7(int chaosRaid7) {
        this.chaosRaid7 = chaosRaid7;
    }

    public int getChaosRaid8() {
        return chaosRaid8;
    }

    public void setChaosRaid8(int chaosRaid8) {
        this.chaosRaid8 = chaosRaid8;
    }

    public int getChaosRaid9() {
        return chaosRaid9;
    }

    public void setChaosRaid9(int chaosRaid9) {
        this.chaosRaid9 = chaosRaid9;
    }

    public int getChaosRaid10() {
        return chaosRaid10;
    }

    public void setChaosRaid10(int chaosRaid10) {
        this.chaosRaid10 = chaosRaid10;
    }

    public int getCompletedChaos() {
        return completedChaos;
    }

    public void setCompletedChaos(int completedChaos) {
        this.completedChaos = completedChaos;
    }

    public int getPayDirt() {
        return PayDirt;
    }

    public void setPayDirt(int PayDirt) {
        this.PayDirt = PayDirt;
    }

    public int getRandomEventTimer() {
        return RandomEventTimer;
    }

    public void setRandomEventTimer(int RandomEventTimer) {
        this.RandomEventTimer = RandomEventTimer;
    }

    public int getBonusTime() {
        return BonusTime;
    }

    public void setBonusTime(int BonusTime) {
        this.BonusTime = BonusTime;
    }

    public int getRareCandy() {
        return RareCandy;
    }

    public void setRareCandy(int RareCandy) {
        this.RareCandy = RareCandy;
    }

    public int getPaePoints() {
        return PaePoints;
    }

    public void setPaePoints(int PaePoints) {
        this.PaePoints = PaePoints;
    }

    public int getexprate() {
        return exprate;
    }

    public void setexprate(int exprate) {
        this.exprate = exprate;
    }

    public int getLastRecallx() {
        return LastRecallx;
    }

    public void setLastRecallx(int LastRecallx) {
        this.LastRecallx = LastRecallx;
    }

    public int getLastRecally() {
        return LastRecally;
    }

    public void setLastRecally(int LastRecally) {
        this.LastRecally = LastRecally;
    }

    public int getLastRecallz() {
        return LastRecallz;
    }

    public void setLastRecallz(int LastRecallz) {
        this.LastRecallz = LastRecallz;
    }

    public int getRaidPoints() {
        return raidPoints;
    }

    public void setRaidPoints(int raidPoints) {
        this.raidPoints = raidPoints;
    }

    public int getLearnAugury() {
        return LearnAugury;
    }

    public void setLearnAugury(int LearnAugury) {
        this.LearnAugury = LearnAugury;
    }

    public int getLearnRigour() {
        return LearnRigour;
    }

    public void setLearnRigour(int LearnRigour) {
        this.LearnRigour = LearnRigour;
    }

    public int getLearnCurses() {
        return LearnCurses;
    }

    public void setLearnCurses(int LearnCurses) {
        this.LearnCurses = LearnCurses;
    }

    public int getLearnAncients() {
        return LearnAncients;
    }

    public void setLearnAncients(int LearnAncients) {
        this.LearnAncients = LearnAncients;
    }

    public int getLearnLunars() {
        return LearnLunars;
    }

    public void setLearnLunars(int LearnLunars) {
        this.LearnLunars = LearnLunars;
    }

    public int getBossTeleports() {
        return BossTeleports;
    }

    public void setBossTeleports(int BossTeleports) {
        this.BossTeleports = BossTeleports;
    }

    public int getMinigameTeleports() {
        return MinigameTeleports;
    }

    public void setMinigameTeleports(int MinigameTeleports) {
        this.MinigameTeleports = MinigameTeleports;
    }

    public int getWildernessTeleports() {
        return WildernessTeleports;
    }

    public void setWildernessTeleports(int WildernessTeleports) {
        this.WildernessTeleports = WildernessTeleports;
    }

    public int getAncientPages() {
        return AncientPages;
    }

    public void setAncientPages(int AncientPages) {
        this.AncientPages = AncientPages;
    }

    public int getArmadylPages() {
        return ArmadylPages;
    }

    public void setArmadylPages(int ArmadylPages) {
        this.ArmadylPages = ArmadylPages;
    }

    public int getBandosPages() {
        return BandosPages;
    }

    public void setBandosPages(int BandosPages) {
        this.BandosPages = BandosPages;
    }

    public int getGuthixPages() {
        return GuthixPages;
    }

    public void setGuthixPages(int GuthixPages) {
        this.GuthixPages = GuthixPages;
    }

    public int getSaradominPages() {
        return SaradominPages;
    }

    public void setSaradominPages(int SaradominPages) {
        this.SaradominPages = SaradominPages;
    }

    public int getZamorakPages() {
        return ZamorakPages;
    }

    public void setZamorakPages(int ZamorakPages) {
        this.ZamorakPages = ZamorakPages;
    }

    /*
     * Variables for the DropLog
     * @author Levi Patton
     */
    public PacketSender getPA() {
        return getPacketSender();
    }

    public PlayerDropLog getPlayerDropLog() {
        return playerDropLog;
    }

    public void setPlayerDropLog(PlayerDropLog playerDropLog) {
        this.playerDropLog = playerDropLog;
    }

    public ProfileViewing getProfile() {
        return profile;
    }

    public void setProfile(ProfileViewing profile) {
        this.profile = profile;
    }

    @Override
    public int getConstitution() {
        return getSkillManager().getCurrentLevel(Skill.CONSTITUTION);
    }

    @Override
    public Character setConstitution(int constitution) {
        if (isDying) {
            return this;
        }
        skillManager.setCurrentLevel(Skill.CONSTITUTION, constitution);
        packetSender.sendSkill(Skill.CONSTITUTION);
        if (getConstitution() <= 0 && !isDying) {
            if (getGameMode() == GameMode.SEASONAL_IRONMAN && Deathless) {
                skillManager.setCurrentLevel(Skill.CONSTITUTION, 10); //TODO SEASONAL check Deathless ability
            } else if (getSummoning().getFamiliar() != null && getSummoning().getFamiliar().getSummonNpc().getId() == 2862 && RandomUtility.inclusiveRandom(3) == 1) {
                performGraphic(new Graphic(1898, GraphicHeight.LOW));
                skillManager.setCurrentLevel(Skill.CONSTITUTION, maxHealth() / 2);
                getPacketSender().sendMessage("@red@Death has saved you from Dying!");
            } else if (getEquipment().contains(21076) && RandomUtility.inclusiveRandom(3) == 1) {
                performGraphic(new Graphic(1898, GraphicHeight.LOW));
                skillManager.setCurrentLevel(Skill.CONSTITUTION, maxHealth() / 2);
                getPacketSender().sendMessage("@red@Your Mask of Rebirth saves you from Dying!");
            } else {
                appendDeath();
            }
        }
        return this;
    }

    public int maxHealth() {
        int max = skillManager.getMaxLevel(Skill.CONSTITUTION);

        if (Survivalist >= 4 && PerkHandler.canUseNormalPerks(this))
            max += 250;
        else if (Survivalist >= 2 && PerkHandler.canUseNormalPerks(this))
            max += 100;

        if (equipment.wearingNexAmours())
            max += 250;

        return max;
    }

    public int maxPrayer() {
        int max = skillManager.getMaxLevel(Skill.CONSTITUTION);

        if (Survivalist == 1)
            max += 100;
        if (getEquipment().wearingSpiritShield())
            max += 200;

        return max;
    }



    public boolean getHatiusLootRoll() {

        boolean chance = false;

        boolean hasPet = this.getInventory().contains(212355) || (this.getSummoning().getFamiliar() != null && this.getSummoning().getFamiliar().getSummonNpc().getId() == 19523);

        if (hasPet) {
            chance = true;
        }

        return chance;
    }

    public long getIdleTime() {

        long idleTime = ActionTracker.IDLE_THRESHOLD;

        boolean hasCat = PetAbilities.checkPetAbilities(this, "lazycat");

        if (hasCat) {
            idleTime += (10 * 60 * 1000);
        }

        if (this.getStaffRights().getStaffRank() > 1) {
            idleTime += ((this.getStaffRights().getStaffRank() - 1) * 2 * 60 * 1000);
        }

        if (this.aggressorTime > 0)
            idleTime += (15 * 60 * 1000);

        return idleTime;
    }

    public double getWealthBonus(Player p, int npcId, boolean adventurerBoost, int raidBonusRNG) {

        double boost = 0;

        switch (p.getStaffRights()) {
            case LEGENDARY_DONATOR:
            case UBER_DONATOR:
            case MASTER_DONATOR:
            case MODERATOR:
            case ADMINISTRATOR:
            case DEVELOPER:
            case OWNER:
                boost += .05;
                break;
            case SUPER_DONATOR:
            case EXTREME_DONATOR:
            case PLAYER:
            case DONATOR:
                break;
        }

        if (p.Lucky >= 4 && PerkHandler.canUseNormalPerks(p)) {
            boost += .10;
        } else if (p.Lucky >= 2 && PerkHandler.canUseNormalPerks(p)) {
            boost += .05;
        } else if (p.wildLooter > 0 && p.getLocation() == Location.WILDERNESS) {
            boost += (.05 * p.wildLooter);
        }

        if (GlobalEventHandler.effectActive(GlobalEvent.Effect.DROP_RATE) || p.droprateEvent) {
            boost += .10;
        } else if (p.activeBonusRNG) {
            boost += .10;
        }

        if (p.getLocation() == Location.INSTANCEDBOSSES || (p.inKQ && p.getLocation() == Location.KALPHITE_QUEEN)) {
            boost += (.01 * p.instanceBoost);
        }

        /*if (p.getGameMode() == GameMode.SEASONAL_IRONMAN && World.getSeasonalTeamBonus(p)) {
            boost += .05;
        }*/

        if (p.getEquipment().gildedEquipBonus() > 0) {
            boost += (p.getEquipment().gildedEquipBonus() * .01);
        }

        if (getEquipment().get(Equipment.RING_SLOT).getId() == 21077 || getInventory().contains(21077)) {
            if (lightbearerOptions[4]) {
                boost += .10;
            }
        } else if (getEquipment().get(Equipment.RING_SLOT).getId() == 221140 || getInventory().contains(221140)) {
            boost += .10;
        } else if (getEquipment().get(Equipment.RING_SLOT).getId() == 2572 || getInventory().contains(2572)) {
            boost += .05;
        }


        if (p.tempRngBoost) {
            boost += .10;
            p.tempRngBoost = false;
        }

        if (npcId == 2881 || npcId == 2882)
            npcId = 2883;

        if (GameLoader.getIncreasedLootDay() == npcId) {
            boost += .10;
        }

        if (p.getRaidsParty() != null) {

            int teamSize = p.getRaidsParty().getPlayers().size();

            boost += (.01 * teamSize);

            boost += (.01 * raidBonusRNG);

            if (adventurerBoost)
                boost += .10;
        } else if (p.getCollectionLog().getKills(npcId) >= 250) {
                boost += .10;
        }

        if (p.checkAchievementAbilities(p, "prosperous")) {
            boost += .05;
        }

        boolean hasWom = PetAbilities.checkPetAbilities(p, "WoM");

        if (hasWom) {
            boost += .05;
        }

        double wildLoot = (p.wildBoost * .05);

        boost += wildLoot;



        if(p.getGameMode() == GameMode.SEASONAL_IRONMAN) {
            if(p.Gilded) {
                boost += .1;
            }

            if (p.seasonPoints >= 1000) {
                boost += .25;
            } else if (p.seasonPoints >= 900) {
                boost += .2;
            } else if (p.seasonPoints >= 800) {
                boost += .15;
            } else if (p.seasonPoints >= 700) {
                boost += .1;
            } else if (p.seasonPoints >= 600) {
                boost += .05;
            }
        } else if (boost > .5) {
            boost = .5;
        }

        if(p.getGameMode() == GameMode.SEASONAL_IRONMAN && boost > .95)
            boost = .95;

        p.wealthBoost = boost;
        InformationPanel.refreshPanel(p);

        return boost;
    }

    public int acceleratedResources() {

        int qty = 1;

        if (getGameMode() == GameMode.SEASONAL_IRONMAN) {
            if (Harvester) {
                qty += 2;
            }
        }

        if (PerkHandler.canUseNormalPerks(this))
            qty += Accelerate;
        else if (PerkHandler.canUseWildernessPerks(this))
            qty += (wildAccelerate * 2);

        if (GlobalEventHandler.effectActive(GlobalEvent.Effect.ACCELERATE) || (accelerateEvent && personalEvent))
            qty += 2;

        return qty;
    }

    public int acceleratedProcessing() {

        int qty = 1;

        if (PerkHandler.canUseNormalPerks(this))
            qty += 1 + Accelerate;
        else if (PerkHandler.canUseWildernessPerks(this))
            qty += 1 + (wildAccelerate * 2);

        if (GlobalEventHandler.effectActive(GlobalEvent.Effect.ACCELERATE) || (accelerateEvent && personalEvent))
            qty += 2;

        if (getGameMode() == GameMode.SEASONAL_IRONMAN) {
            if (Producer) {
                qty = 28;
            }
        }

        return qty;
    }

    public boolean activeArea(Location location) {


        return false;
    }

    public void makeIdle() {

        this.getPacketSender().sendMessage("@red@Your bonus timers, events, and double XP were turned off due to inactivity.");

        this.activeBonusRNG = false;
        this.activeBonusXP = false;
        this.activeDoubleXP = false;
        this.personalEvent = false;

        this.accelerateEvent = false;
        this.accuracyEvent = false;
        this.bossKillsEvent = false;
        this.doubleBossPointEvent = false;
        this.doubleSkillerPointsEvent = false;
        this.doubleSlayerPointsEvent = false;
        this.doubleExpEvent = false;
        this.droprateEvent = false;
        this.loadedEvent = false;
        this.maxHitEvent = false;
        this.eventBoxEvent = false;
        this.doubleLoot = false;

        if (this.getMinigameAttributes().getRaidsAttributes().getParty() != null)
            this.getMinigameAttributes().getRaidsAttributes().getParty().remove(this, true, true);

        if (this.getMinigameAttributes().getRaidsAttributes().getParty() != null)
            this.getMinigameAttributes().getRaidsAttributes().getParty().getPlayersInRaids().remove(this);
    }

    @Override
    public void heal(int amount) {
        if ((skillManager.getCurrentLevel(Skill.CONSTITUTION) + amount) >= maxHealth()) {
            setConstitution(maxHealth());
        } else {
            setConstitution(skillManager.getCurrentLevel(Skill.CONSTITUTION) + amount);
        }
    }

    @Override
    public int getBaseAttack(CombatType type) {
        if (type == CombatType.RANGED) {
            return skillManager.getCurrentLevel(Skill.RANGED);
        } else if (type == CombatType.MAGIC) {
            return skillManager.getCurrentLevel(Skill.MAGIC);
        }
        return skillManager.getCurrentLevel(Skill.ATTACK);
    }

    @Override
    public int getBaseDefence(CombatType type) {
        if (type == CombatType.MAGIC) {
            return skillManager.getCurrentLevel(Skill.MAGIC);
        }
        return skillManager.getCurrentLevel(Skill.DEFENCE);
    }

    @Override
    public int getAttackSpeed() {
        String s = equipment.get(Equipment.WEAPON_SLOT).getDefinition().getName().toLowerCase();
        int id = equipment.get(Equipment.WEAPON_SLOT).getDefinition().getId();
        int ticks = 5;
        String type = "";

        if (s.contains("halberd") || s.contains("greataxe") || s.contains("maul") || s.contains("anchor")) {
            ticks = 8;
            type = "melee";
        } else if (s.contains("godsword") || s.contains("2h")) {
            ticks = 6;
            type = "melee";
        } else if (s.contains("dark bow") || s.contains("hand cannon") || s.contains("javelin") || s.contains("ballista")) {
            ticks = 6;
            type = "ranged";
        } else if (s.contains("long")) {
            ticks = 5;
            type = "melee";
        } else if (s.contains("scimitar") || s.contains("katana") || s.contains("rapier") || s.contains("sword") || s.contains("rclight") || s.contains("fang")) {
            ticks = 4;
            type = "melee";
        } else if (s.contains("mace") || s.contains("whip") || s.contains("tentacle") || s.contains("claw") || s.contains("scythe") || s.contains("lance")) {
            ticks = 4;
            type = "melee";
        } else if (s.contains("crossbow") || s.contains("cbow") || s.contains("shortbow") || s.contains("seercull")) {
            ticks = 4;
            type = "ranged";
        } else if (s.contains("aradomin bow") || s.contains("amorak bow") || s.contains("uthix bow")) {
            ticks = 4;
            type = "ranged";
        } else if (s.contains("dagger") || s.contains("bludgeon")) {
            ticks = 4;
            type = "melee";
        } else if (s.contains("knife") || s.contains("dart")) {
            ticks = 3;
            type = "ranged";
        } else if (s.contains("blowpipe") || s.contains("thrownaxe")) {
            ticks = 3;
            type = "ranged";
        } else if (s.contains("esta's long")) {
            ticks = 4;
            type = "melee";
        } else if (s.contains("esta's spea")) {
            ticks = 6;
            type = "melee";
        } else if (s.contains("tatius's warham")) {
            ticks = 5;
            type = "melee";
        } else if (s.contains("klight")) {
            ticks = 4;
            type = "melee";
        } else if (s.contains("clight")) {
            ticks = 4;
            type = "melee";
        } else if (s.contains("bow")) {
            ticks = 4;
            type = "ranged";
        }

        //obsidian weapons
        else if (s.equals("toktz-xil-ak"))// sword
        {
            ticks = 4;
            type = "melee";
        } else if (s.equals("tzhaar-ket-em"))// mace
        {
            ticks = 4;
            type = "melee";
        } else if (s.equals("tzhaar-ket-om"))// maul
        {
            ticks = 7;
            type = "melee";
        } else if (s.equals("toktz-xil-ek"))// knife
        {
            ticks = 3;
            type = "ranged";
        } else if (s.equals("toktz-xil-ul"))// rings
        {
            ticks = 3;
            type = "ranged";
        } else if (s.contains("Crystal"))// rings
        {
            ticks = 4;
            type = "melee";
        } else if (s.contains("armonise")) {
            ticks = 4;
            type = "magic";
        } else if (s.contains("sanguin") && getAutocastSpell() != null && getAutocastSpell().spellId() == 12447) {
            ticks = 4;
            type = "magic";
        } else if (s.contains("rident") && getAutocastSpell() != null && getAutocastSpell().spellId() == 12446) {
            ticks = 4;
            type = "magic";
        } else if (id == 21061) {
            ticks = 4;
            type = "magic";
        } else if ((s.contains("wand") || s.contains("staff") || s.contains("Staff") || s.contains("Wand")) && getAutocastSpell() == null) {
            ticks = 5;
            type = "melee";
        } else if (s.contains("staff") && getAutocastSpell() != null && (getAutocastSpell().spellId() == 1190 || getAutocastSpell().spellId() == 1191 || getAutocastSpell().spellId() == 1192)) {
            ticks = 4;
            type = "magic";
        } else if (s.contains("wand") || s.contains("staff") || s.contains("Staff") || s.contains("Wand")) {
            ticks = 5;
            type = "magic";
        } else {
            ticks = 5;
            type = "melee";
        }


        if (fightType == FightType.SHORTBOW_RAPID || fightType == FightType.DART_RAPID || fightType == FightType.KNIFE_RAPID || fightType == FightType.THROWNAXE_RAPID || fightType == FightType.JAVELIN_RAPID)
            ticks--;

        if ((Berserker >= 1) && (type.equals("melee")) && getInteractingEntity().isNpc() && getAutocastSpell() == null && PerkHandler.canUseNormalPerks(this)) {

            if (ticks == 8 || ticks == 7)
                ticks = 4;
            else if (ticks == 6 || ticks == 5)
                ticks = 3;
            else if (ticks == 4 || ticks == 3)
                ticks = 2;
            else if (ticks == 2 || ticks == 1)
                ticks = 1;
        }


        if ((Bullseye >= 1) && (type.equals("ranged")) && getInteractingEntity().isNpc() && getAutocastSpell() == null && PerkHandler.canUseNormalPerks(this)) {
            if (ticks == 8 || ticks == 7)
                ticks = 4;
            else if (ticks == 6 || ticks == 5)
                ticks = 3;
            else if (ticks == 4 || ticks == 3)
                ticks = 2;
            else if (ticks == 2 || ticks == 1)
                ticks = 1;
        }

        if ((Prophetic >= 1) && (type.equals("magic")) && getInteractingEntity().isNpc() && PerkHandler.canUseNormalPerks(this)) {
            if (ticks == 8 || ticks == 7)
                ticks = 4;
            else if (ticks == 6 || ticks == 5)
                ticks = 3;
            else if (ticks == 4 || ticks == 3)
                ticks = 2;
            else if (ticks == 2 || ticks == 1)
                ticks = 1;

        }

        //if (getStaffRights() == StaffRights.DEVELOPER)
            //getPacketSender().sendMessage("Your " + s + " attack was " + ticks + " ticks.");

        if ((Rapid && ticks > 2) || GlobalEventHandler.effectActive(GlobalEvent.Effect.RAPID))
            ticks = 2;

        if (this.getLocation() == Location.WILDERNESS && this.wildEnragedKills >= 100) {

            switch (this.wildEnraged) {
                case 1:
                    ticks -= 1;
                    break;
                case 2:
                    ticks -= 2;
                    break;
                case 3:
                    ticks -= 3;
                    break;
            }
        }

        if (ticks < 2)
            ticks = 2;

        return ticks;
    }

    public UIMStorage getUimBank() {
        return uimStorage;
    }

    public Bank getBank() {
        return bank;
    }

    public MailBox getMailBox() {
        return mailBox;
    }

    public Player setMailBox(MailBox mailBox) {
        this.mailBox = mailBox;
        return this;
    }

    public Bingo getBingo() {
        return bingo;
    }

    /*
     * Getters and setters
     */

    @Override
    public boolean isPlayer() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Player)) {
            return false;
        }

        Player p = (Player) o;
        return p.getIndex() == getIndex() || p.getUsername().equals(username);
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public void poisonVictim(Character victim, CombatType type) {
        if (type == CombatType.MELEE || weapon == WeaponInterface.DART || weapon == WeaponInterface.KNIFE || weapon == WeaponInterface.THROWNAXE || weapon == WeaponInterface.JAVELIN) {
            //CombatFactory.poisonEntity(victim, CombatPoisonData.getPoisonType(equipment.get(Equipment.WEAPON_SLOT)));
        } else if (type == CombatType.RANGED) {
            //CombatFactory.poisonEntity(victim, CombatPoisonData.getPoisonType(equipment.get(Equipment.AMMUNITION_SLOT)));
        }
    }

    @Override
    public CombatStrategy determineStrategy() {
        if (specialActivated && castSpell == null) {

            if (combatSpecial.getCombatType() == CombatType.MELEE) {
                return CombatStrategies.getDefaultMeleeStrategy();
            } else if (combatSpecial.getCombatType() == CombatType.RANGED) {
                setRangedWeaponData(RangedWeaponData.getData(this));
                return CombatStrategies.getDefaultRangedStrategy();
            } else if (combatSpecial.getCombatType() == CombatType.MAGIC) {
                return CombatStrategies.getDefaultMagicStrategy();
            }
        }

        if (castSpell != null || autocastSpell != null) {
            return CombatStrategies.getDefaultMagicStrategy();
        }

        RangedWeaponData data = RangedWeaponData.getData(this);
        if (data != null) {
            setRangedWeaponData(data);
            return CombatStrategies.getDefaultRangedStrategy();
        }

        if (CombatFactory.crystalBow(this))
            return CombatStrategies.getDefaultRangedStrategy();

        return CombatStrategies.getDefaultMeleeStrategy();
    }

    public void process() {
        process.sequence();
    }

    public void dispose() {
        save();
        packetSender.sendLogout();
    }

    public void save() {
        if (session.getState() != SessionState.LOGGED_IN && session.getState() != SessionState.LOGGING_OUT) {
            return;
        }
        PlayerSaving.save(this);
    }

    public boolean logout() {
        if (getCombatBuilder().isBeingAttacked()) {
            getPacketSender().sendMessage("You must wait a few seconds after being out of combat before doing this.");
            return false;
        }
        if (getConstitution() <= 0 || isDying || settingUpCannon || crossingObstacle) {
            getPacketSender().sendMessage("You cannot log out at the moment.");
            return false;
        }
        return true;
    }

    public void restart() {
        setFreezeDelay(0);
        setOverloadPotionTimer(0);
        setPrayerRenewalPotionTimer(0);
        setSpecialPercentage(100);
        setSpecialActivated(false);
        CombatSpecial.updateBar(this);
        setHasVengeance(false);
        setSkullTimer(0);
        setSkullIcon(0);
        setTeleblockTimer(0);
        setPoisonDamage(0);
        setStaffOfLightEffect(0);
        performAnimation(new Animation(65535));
        WeaponInterfaces.assign(this, getEquipment().get(Equipment.WEAPON_SLOT));
        WeaponAnimations.assign(this, getEquipment().get(Equipment.WEAPON_SLOT));
        PrayerHandler.deactivateAll(this);
        CurseHandler.deactivateAll(this);
        getEquipment().refreshItems();
        getInventory().refreshItems();
        for (Skill skill : Skill.values()) {
            getSkillManager().setCurrentLevel(skill, getSkillManager().getMaxLevel(skill));
        }
        setRunEnergy(100);
        setDying(false);
        getMovementQueue().setLockMovement(false).reset();
        getUpdateFlag().flag(Flag.APPEARANCE);
    }

    public boolean busy() {
        return interfaceId > 0 || isBanking || isMailBoxOpen || isTradingPostOpen() || shopping || trading.inTrade() || dueling.inDuelScreen || isResting;
    }

    /*
     * Getters & Setters
     */
    public PlayerSession getSession() {
        return session;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public Overrides getOverrides() {
        return overrides;
    }

    public PriceChecker getPriceChecker() {
        return priceChecker;
    }

    public Presets getPresets() {
        return presets;
    }

    public void setPresets(Presets presets) {
        this.presets = presets;
    }

    public String getUsername() {
        return username;
    }

    public Player setUsername(String username) {
        this.username = username;
        return this;
    }

    public Long getLongUsername() {
        return longUsername;
    }

    public Player setLongUsername(Long longUsername) {
        this.longUsername = longUsername;
        return this;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String address) {
        this.emailAddress = address;
    }

    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(byte[] passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public Player setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
        return this;
    }

    public String getHardwareID() {
        return hardwareID;
    }

    public Player setHardwareID(String hardwareID) {
        this.hardwareID = hardwareID;
        return this;
    }

    public FrameUpdater getFrameUpdater() {
        return this.frameUpdater;
    }

    public StaffRights getStaffRights() {
        return staffRights;
    }

    public Player setStaffRights(StaffRights staffRights) {
        this.staffRights = staffRights;
        return this;
    }

    public Subscriptions getSubscription() {
        return subscription;
    }

    public Player setSubscription(Subscriptions subscription) {
        this.subscription = subscription;
        return this;
    }

    public ChatMessage getChatMessages() {
        return chatMessages;
    }

    public PacketSender getPacketSender() {
        return packetSender;
    }

    public SkillManager getSkillManager() {
        return skillManager;
    }

    public Appearance getAppearance() {
        return appearance;
    }

    public PlayerRelations getRelations() {
        return relations;
    }

    public PlayerKillingAttributes getPlayerKillingAttributes() {
        return playerKillingAttributes;
    }

    public PointsHandler getPointsHandler() {
        return pointsHandler;
    }

    public int getPoisonImmunity() {
        return poisonImmunity;
    }

    public void setPoisonImmunity(int poisonImmunity) {
        //getPacketSender().sendMessage("You are now immune to Poison for " + poisonImmunity / 100 + " minutes!");
        this.poisonImmunity = poisonImmunity;
    }

    public void incrementPoisonImmunity(int amount) {
        poisonImmunity += amount;
    }

    public void decrementPoisonImmunity(int amount) {
        poisonImmunity -= amount;
    }

    public int getDragonfireImmunity() {
        return dragonfireImmunity;
    }

    public void setDragonfireImmunity(int dragonfireImmunity) {
        //getPacketSender().sendMessage("You are now immune to Dragonfire for " + dragonfireImmunity / 100 + " minutes!");
        this.dragonfireImmunity = dragonfireImmunity;
    }

    public void incrementDragonfireImmunity(int amount) {
        dragonfireImmunity += amount;
    }

    public void decrementDragonfireImmunity(int amount) {
        dragonfireImmunity -= amount;
    }

    public boolean isAutoRetaliate() {
        return autoRetaliate;
    }

    public void setAutoRetaliate(boolean autoRetaliate) {
        this.autoRetaliate = autoRetaliate;
    }

    /**
     * @return the castSpell
     */
    public CombatSpell getCastSpell() {
        return castSpell;
    }

    /**
     * @param castSpell the castSpell to set
     */
    public void setCastSpell(CombatSpell castSpell) {
        this.castSpell = castSpell;
    }

    public CombatSpell getPreviousCastSpell() {
        return previousCastSpell;
    }

    public void setPreviousCastSpell(CombatSpell previousCastSpell) {
        this.previousCastSpell = previousCastSpell;
    }

    /**
     * @return the autocast
     */
    public boolean isAutocast() {
        return autocast;
    }

    /**
     * @param autocast the autocast to set
     */
    public void setAutocast(boolean autocast) {
        this.autocast = autocast;
    }

    /**
     * @return the skullTimer
     */
    public int getSkullTimer() {
        return skullTimer;
    }

    /**
     * @param skullTimer the skullTimer to set
     */
    public void setSkullTimer(int skullTimer) {
        this.skullTimer = skullTimer;
    }

    public void decrementSkullTimer() {
        skullTimer -= 50;
    }

    /**
     * @return the skullIcon
     */
    public int getSkullIcon() {
        return skullIcon;
    }

    /**
     * @param skullIcon the skullIcon to set
     */
    public void setSkullIcon(int skullIcon) {
        this.skullIcon = skullIcon;
    }

    /**
     * @return the teleblockTimer
     */
    public int getTeleblockTimer() {
        return teleblockTimer;
    }

    /**
     * @param teleblockTimer the teleblockTimer to set
     */
    public void setTeleblockTimer(int teleblockTimer) {
        this.teleblockTimer = teleblockTimer;
    }

    public void decrementTeleblockTimer() {
        teleblockTimer--;
    }

    /**
     * @return the autocastSpell
     */
    public CombatSpell getAutocastSpell() {
        return autocastSpell;
    }

    /**
     * @param autocastSpell the autocastSpell to set
     */
    public void setAutocastSpell(CombatSpell autocastSpell) {
        this.autocastSpell = autocastSpell;
    }

    /**
     * @return the specialPercentage
     */
    public int getSpecialPercentage() {
        return specialPercentage;
    }

    /**
     * @param specialPercentage the specialPercentage to set
     */
    public void setSpecialPercentage(int specialPercentage) {
        this.specialPercentage = specialPercentage;
    }

    /**
     * @return the fireAmmo
     */
    public int getFireAmmo() {
        return fireAmmo;
    }

    /**
     * @param fireAmmo the fireAmmo to set
     */
    public void setFireAmmo(int fireAmmo) {
        this.fireAmmo = fireAmmo;
    }

    public int getWildernessLevel() {
        return wildernessLevel;
    }

    public void setWildernessLevel(int wildernessLevel) {
        this.wildernessLevel = wildernessLevel;
    }

    /**
     * @return the combatSpecial
     */
    public CombatSpecial getCombatSpecial() {
        return combatSpecial;
    }

    /**
     * @param combatSpecial the combatSpecial to set
     */
    public void setCombatSpecial(CombatSpecial combatSpecial) {
        this.combatSpecial = combatSpecial;
    }

    /**
     * @return the specialActivated
     */
    public boolean isSpecialActivated() {
        return specialActivated;
    }

    /**
     * @param specialActivated the specialActivated to set
     */
    public void setSpecialActivated(boolean specialActivated) {
        this.specialActivated = specialActivated;
    }

    public void decrementSpecialPercentage(int drainAmount) {
        this.specialPercentage -= drainAmount;

        if (specialPercentage < 0) {
            specialPercentage = 0;
        }
    }

    public void incrementSpecialPercentage(int gainAmount) {
        this.specialPercentage += gainAmount;

        if (specialPercentage > 100) {
            specialPercentage = 100;
        }
    }

    /**
     * @return the rangedAmmo
     */
    public RangedWeaponData getRangedWeaponData() {
        return rangedWeaponData;
    }

    /**
     * //* @param rangedAmmo the rangedAmmo to set
     */
    public void setRangedWeaponData(RangedWeaponData rangedWeaponData) {
        this.rangedWeaponData = rangedWeaponData;
    }

    /**
     * @return the weapon.
     */
    public WeaponInterface getWeapon() {
        return weapon;
    }

    /**
     * @param weapon the weapon to set.
     */
    public void setWeapon(WeaponInterface weapon) {
        this.weapon = weapon;
    }

    public void resetInterfaces() {
        walkableInterfaceList.stream().filter((i) -> !(i == 41005 || i == 41000)).forEach((i) -> {
            getPacketSender().sendWalkableInterface(i, false);
        });

        walkableInterfaceList.clear();
    }

    public void sendParallellInterfaceVisibility(int interfaceId, boolean visible) {
        if (this != null && this.getPacketSender() != null) {
            if (visible) {
                if (walkableInterfaceList.contains(interfaceId)) {
                    return;
                } else {
                    walkableInterfaceList.add(interfaceId);
                }
            } else {
                if (!walkableInterfaceList.contains(interfaceId)) {
                    return;
                } else {
                    walkableInterfaceList.remove((Object) interfaceId);
                }
            }

            getPacketSender().sendWalkableInterface(interfaceId, visible);
        }
    }

    /**
     * @return the fightType
     */
    public FightType getFightType() {
        return fightType;
    }

    /**
     * @param fightType the fightType to set
     */
    public void setFightType(FightType fightType) {
        this.fightType = fightType;
    }

    public Bank[] getBanks() {
        return bankTabs;
    }

    public Bank getBank(int index) {
        return bankTabs[index];
    }

    public Player setBank(int index, Bank bank) {
        this.bankTabs[index] = bank;
        return this;
    }

    public boolean isAcceptAid() {
        return acceptingAid;
    }

    public void setAcceptAid(boolean acceptingAid) {
        this.acceptingAid = acceptingAid;
    }

    public Trading getTrading() {
        return trading;
    }

    public Dueling getDueling() {
        return dueling;
    }

    public CopyOnWriteArrayList<DropLogEntry> getDropLog() {
        return dropLog;
    }

    public WalkToTask getWalkToTask() {
        return walkToTask;
    }

    public void setWalkToTask(WalkToTask walkToTask) {
        this.walkToTask = walkToTask;
    }

    public MagicSpellbook getSpellbook() {
        return spellbook;
    }

    public Player setSpellbook(MagicSpellbook spellbook) {
        this.spellbook = spellbook;
        return this;
    }

    public Prayerbook getPrayerbook() {
        return prayerbook;
    }

    public Player setPrayerbook(Prayerbook prayerbook) {
        this.prayerbook = prayerbook;
        return this;
    }

    /**
     * The player's local players list.
     */
    public List<Player> getLocalPlayers() {
        return localPlayers;
    }

    /**
     * The player's local npcs list getter
     */
    public List<NPC> getLocalNpcs() {
        return localNpcs;
    }

    /**
     * The player's local npcs list getter (NPCs within 4 tiles)
     */
    public List<NPC> getLocalAggroNpcs() {
        return localAggroNpcs;
    }

    public int getInterfaceId() {
        return this.interfaceId;
    }

    public Player setInterfaceId(int interfaceId) {
        this.interfaceId = interfaceId;
        return this;
    }

    public boolean isDying() {
        return isDying;
    }

    public void setDying(boolean isDying) {
        this.isDying = isDying;
    }

    public int[] getForceMovement() {
        return forceMovement;
    }

    public Player setForceMovement(int[] forceMovement) {
        this.forceMovement = forceMovement;
        return this;
    }

    /**
     * @return the equipmentAnimation
     */
    public CharacterAnimations getCharacterAnimations() {
        return characterAnimations;
    }

    /**
     * @return the equipmentAnimation
     */
    public void setCharacterAnimations(CharacterAnimations equipmentAnimation) {
        this.characterAnimations = equipmentAnimation.clone();
    }

    public LoyaltyTitles getLoyaltyTitle() {
        return loyaltyTitle;
    }

    public void setLoyaltyTitle(LoyaltyTitles loyaltyTitle) {
        this.loyaltyTitle = loyaltyTitle;
    }

    public PlayerInteractingOption getPlayerInteractingOption() {
        return playerInteractingOption;
    }

    public Player setPlayerInteractingOption(PlayerInteractingOption playerInteractingOption) {
        this.playerInteractingOption = playerInteractingOption;
        return this;
    }

    public int getMultiIcon() {
        return multiIcon;
    }

    public Player setMultiIcon(int multiIcon) {
        this.multiIcon = multiIcon;
        return this;
    }

    public int getWalkableInterfaceId() {
        return walkableInterfaceId;
    }

    public void setWalkableInterfaceId(int interfaceId2) {
        this.walkableInterfaceId = interfaceId2;
    }

    public boolean soundsActive() {
        return soundsActive;
    }

    public boolean getSoundsActive() {
        return soundsActive;
    }

    public void setSoundsActive(boolean soundsActive) {
        this.soundsActive = soundsActive;
    }

    public boolean musicActive() {
        return musicActive;
    }

    public boolean getMusicActive() {
        return musicActive;
    }

    public void setMusicActive(boolean musicActive) {
        this.musicActive = musicActive;
    }

    public BonusManager getBonusManager() {
        return bonusManager;
    }

    public int getRunEnergy() {
        return runEnergy;
    }

    public Player setRunEnergy(int runEnergy) {
        this.runEnergy = runEnergy;
        return this;
    }

    public Stopwatch getLastRunRecovery() {
        return lastRunRecovery;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public Player setRunning(boolean isRunning) {
        this.isRunning = isRunning;
        return this;
    }

    public boolean isResting() {
        return isResting;
    }

    public Player setResting(boolean isResting) {
        this.isResting = isResting;
        return this;
    }

    public long getMoneyInPouch() {
        return moneyInPouch;
    }

    public void setMoneyInPouch(long moneyInPouch) {
        this.moneyInPouch = moneyInPouch;
    }

    public int getMoneyInPouchAsInt() {
        return moneyInPouch > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) moneyInPouch;
    }

    public boolean experienceLocked() {
        return experienceLocked;
    }

    public void setExperienceLocked(boolean experienceLocked) {
        this.experienceLocked = experienceLocked;
    }

    public boolean isClientExitTaskActive() {
        return clientExitTaskActive;
    }

    public void setClientExitTaskActive(boolean clientExitTaskActive) {
        this.clientExitTaskActive = clientExitTaskActive;
    }

    public ClanChat getCurrentClanChat() {
        return currentClanChat;
    }

    public Player setCurrentClanChat(ClanChat clanChat) {
        this.currentClanChat = clanChat;
        return this;
    }

    public String getClanChatName() {
        return clanChatName;
    }

    public Player setClanChatName(String clanChatName) {
        this.clanChatName = clanChatName;
        return this;
    }

    public Input getInputHandling() {
        return inputHandling;
    }

    public void setInputHandling(Input inputHandling) {
        this.inputHandling = inputHandling;
    }

    public boolean isDrainingPrayer() {
        return drainingPrayer;
    }

    public void setDrainingPrayer(boolean drainingPrayer) {
        this.drainingPrayer = drainingPrayer;
    }

    public Stopwatch getClickDelay() {
        return clickDelay;
    }

    public Stopwatch getAfkAttack() {
        return afkAttack;
    }

    public int[] getLeechedBonuses() {
        return leechedBonuses;
    }

    public Stopwatch getLastItemPickup() {
        return lastItemPickup;
    }

    public Stopwatch getLastSummon() {
        return lastSummon;
    }

    public BankSearchAttributes getBankSearchingAttribtues() {
        return bankSearchAttributes;
    }

    public BankPinAttributes getBankPinAttributes() {
        return bankPinAttributes;
    }

    public int getCurrentBankTab() {
        return currentBankTab;
    }

    public Player setCurrentBankTab(int tab) {
        this.currentBankTab = tab;
        return this;
    }

    public boolean isBanking() {
        return isBanking;
    }

    public Player setBanking(boolean isBanking) {
        this.isBanking = isBanking;
        return this;
    }

    public void setNoteWithdrawal(boolean noteWithdrawal) {
        this.noteWithdrawal = noteWithdrawal;
    }

    public boolean withdrawAsNote() {
        return noteWithdrawal;
    }

    public void setSwapMode(boolean swapMode) {
        this.swapMode = swapMode;
    }

    public boolean swapMode() {
        return swapMode;
    }

    public boolean isMailBoxOpen() {
        return isMailBoxOpen;
    }

    public Player setMailBoxOpen(boolean mailBoxOpen) {
        isMailBoxOpen = mailBoxOpen;
        return this;
    }

    public boolean isTradingPostOpen() {
        return (boolean) attributes.getOrDefault("trading-post-open", false);
    }

    public Player setTradingPostOpen(boolean tradingPostOpen) {
        attributes.put("trading-post-open", tradingPostOpen);
        return this;
    }

    public boolean isShopping() {
        return shopping;
    }

    public void setShopping(boolean shopping) {
        this.shopping = shopping;
    }

    public Shop getShop() {
        return shop;
    }

    public Player setShop(Shop shop) {
        this.shop = shop;
        return this;
    }

    public GameObject getInteractingObject() {
        return interactingObject;
    }

    public Player setInteractingObject(GameObject interactingObject) {
        this.interactingObject = interactingObject;
        return this;
    }

    public Item getInteractingItem() {
        return interactingItem;
    }

    public void setInteractingItem(Item interactingItem) {
        this.interactingItem = interactingItem;
    }

    public Dialogue getDialogue() {
        return this.dialogue;
    }

    public void setDialogue(Dialogue dialogue) {
        this.dialogue = dialogue;
    }

    public int getDialogueActionId() {
        return dialogueActionId;
    }

    public void setDialogueActionId(int dialogueActionId) {
        this.dialogueActionId = dialogueActionId;
    }

    public boolean isSettingUpCannon() {
        return settingUpCannon;
    }

    public void setSettingUpCannon(boolean settingUpCannon) {
        this.settingUpCannon = settingUpCannon;
    }

    public DwarfCannon getCannon() {
        return cannon;
    }

    public Player setCannon(DwarfCannon cannon) {
        this.cannon = cannon;
        return this;
    }

    public int getOverloadPotionTimer() {
        return overloadPotionTimer;
    }

    public void setOverloadPotionTimer(int overloadPotionTimer) {
        this.overloadPotionTimer = overloadPotionTimer;
    }

    public int getPrayerRenewalPotionTimer() {
        return prayerRenewalPotionTimer;
    }

    public void setPrayerRenewalPotionTimer(int prayerRenewalPotionTimer) {
        this.prayerRenewalPotionTimer = prayerRenewalPotionTimer;
    }

    public Stopwatch getSpecialRestoreTimer() {
        return specialRestoreTimer;
    }

    public boolean[] getUnlockedLoyaltyTitles() {
        return unlockedLoyaltyTitles;
    }

    public void setUnlockedLoyaltyTitles(boolean[] unlockedLoyaltyTitles) {
        this.unlockedLoyaltyTitles = unlockedLoyaltyTitles;
    }

    public void setUnlockedLoyaltyTitle(int index) {
        unlockedLoyaltyTitles[index] = true;
    }

    public Stopwatch getEmoteDelay() {
        return emoteDelay;
    }

    public MinigameAttributes getMinigameAttributes() {
        return minigameAttributes;
    }

    public ScriptAttributes getScriptAttributes() {
        return scriptAttributes;
    }

    public Minigame getMinigame() {
        return minigame;
    }

    public void setMinigame(Minigame minigame) {
        this.minigame = minigame;
    }

    public boolean hasVengeance() {
        return hasVengeance;
    }

    public void setHasVengeance(boolean hasVengeance) {
        this.hasVengeance = hasVengeance;
    }

    public Stopwatch getLastVengeance() {
        return lastVengeance;
    }

    /*
     * Construction instancing Arlania
     */
    public boolean isVisible() {
        return getLocation() != Location.CONSTRUCTION;
    }

    public void setHouseFurtinture(ArrayList<HouseFurniture> houseFurniture) {
        this.houseFurniture = houseFurniture;
    }

    public Stopwatch getTolerance() {
        return tolerance;
    }

    public boolean isTargeted() {
        return targeted;
    }

    public void setTargeted(boolean targeted) {
        this.targeted = targeted;
    }

    public Stopwatch getLastYell() {
        return lastYell;
    }

    public Stopwatch getLastSql() {
        return lastSql;
    }

    public long getTotalPlayTime() {
        return totalPlayTime;
    }

    public void setTotalPlayTime(long amount) {
        this.totalPlayTime = amount;
    }

    public Stopwatch getRecordedLogin() {
        return recordedLogin;
    }

    public Player setRegionChange(boolean regionChange) {
        this.regionChange = regionChange;
        return this;
    }

    public boolean isChangingRegion() {
        return this.regionChange;
    }

    public boolean isAllowRegionChangePacket() {
        return allowRegionChangePacket;
    }

    public void setAllowRegionChangePacket(boolean allowRegionChangePacket) {
        this.allowRegionChangePacket = allowRegionChangePacket;
    }

    public boolean isCoughing() {
        return isCoughing;
    }

    public void setCoughing(boolean isCoughing) {
        this.isCoughing = isCoughing;
    }

    public int getShadowState() {
        return shadowState;
    }

    public void setShadowState(int shadow) {
        this.shadowState = shadow;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public boolean isPlayerLocked() {
        return playerLocked;
    }

    public Player setPlayerLocked(boolean playerLocked) {
        this.playerLocked = playerLocked;
        return this;
    }

    public Stopwatch getSqlTimer() {
        return sqlTimer;
    }

    public Stopwatch getFoodTimer() {
        return foodTimer;
    }

    public Stopwatch getPotionTimer() {
        return potionTimer;
    }

    public Item getUntradeableDropItem() {
        return untradeableDropItem;
    }

    public void setUntradeableDropItem(Item untradeableDropItem) {
        this.untradeableDropItem = untradeableDropItem;
    }

    public boolean isRecoveringSpecialAttack() {
        return recoveringSpecialAttack;
    }

    public void setRecoveringSpecialAttack(boolean recoveringSpecialAttack) {
        this.recoveringSpecialAttack = recoveringSpecialAttack;
    }

    public CombatType getLastCombatType() {
        return lastCombatType;
    }

    public void setLastCombatType(CombatType lastCombatType) {
        this.lastCombatType = lastCombatType;
    }

    public int getEffigy() {
        return this.effigy;
    }

    public void setEffigy(int effigy) {
        this.effigy = effigy;
    }

    public int getDfsCharges() {
        return dfsCharges;
    }

    public void incrementDfsCharges(int amount) {
        this.dfsCharges += amount;
    }

    public void setNewPlayer(boolean newPlayer) {
        this.newPlayer = newPlayer;
    }

    public boolean newPlayer() {
        return newPlayer;
    }

    public Stopwatch getLogoutTimer() {
        return lougoutTimer;
    }

    public Player setUsableObject(int index, Object usableObject) {
        this.usableObject[index] = usableObject;
        return this;
    }

    public Object[] getUsableObject() {
        return usableObject;
    }

    public Player setUsableObject(Object[] usableObject) {
        this.usableObject = usableObject;
        return this;
    }

    public int getPlayerViewingIndex() {
        return playerViewingIndex;
    }

    public void setPlayerViewingIndex(int playerViewingIndex) {
        this.playerViewingIndex = playerViewingIndex;
    }

    public boolean hasStaffOfLightEffect() {
        return staffOfLightEffect > 0;
    }

    public int getStaffOfLightEffect() {
        return staffOfLightEffect;
    }

    public void setStaffOfLightEffect(int staffOfLightEffect) {
        this.staffOfLightEffect = staffOfLightEffect;
    }

    public void decrementStaffOfLightEffect() {
        this.staffOfLightEffect--;
    }

    public boolean openBank() {
        return openBank;
    }

    public void setOpenBank(boolean openBank) {
        this.openBank = openBank;
    }

    public int getMinutesBonusExp() {
        return minutesBonusExp;
    }

    public void setMinutesBonusExp(int minutesBonusExp, boolean add) {
        this.minutesBonusExp = (add ? this.minutesBonusExp + minutesBonusExp : minutesBonusExp);
    }

    public int getSelectedGeItem() {
        return selectedGeItem;
    }

    public void setSelectedGeItem(int selectedGeItem) {
        this.selectedGeItem = selectedGeItem;
    }

    public int getGeQuantity() {
        return geQuantity;
    }

    public void setGeQuantity(int geQuantity) {
        this.geQuantity = geQuantity;
    }

    public int getGePricePerItem() {
        return gePricePerItem;
    }

    public void setGePricePerItem(int gePricePerItem) {
        this.gePricePerItem = gePricePerItem;
    }

    public GrandExchangeSlot[] getGrandExchangeSlots() {
        return grandExchangeSlots;
    }

    public void setGrandExchangeSlots(GrandExchangeSlot[] GrandExchangeSlots) {
        this.grandExchangeSlots = GrandExchangeSlots;
    }

    public void setGrandExchangeSlot(int index, GrandExchangeSlot state) {
        this.grandExchangeSlots[index] = state;
    }

    public int getSelectedGeSlot() {
        return selectedGeSlot;
    }

    public void setSelectedGeSlot(int slot) {
        this.selectedGeSlot = slot;
    }

    public Task getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }

    public int getSelectedSkillingItem() {
        return selectedSkillingItem;
    }

    public void setSelectedSkillingItem(int selectedItem) {
        this.selectedSkillingItem = selectedItem;
    }

    public boolean shouldProcessFarming() {
        return processFarming;
    }

    public void setProcessFarming(boolean processFarming) {
        this.processFarming = processFarming;
    }

    public Pouch getSelectedPouch() {
        return selectedPouch;
    }

    public void setSelectedPouch(Pouch selectedPouch) {
        this.selectedPouch = selectedPouch;
    }

    public int getCurrentBookPage() {
        return currentBookPage;
    }

    public void setCurrentBookPage(int currentBookPage) {
        this.currentBookPage = currentBookPage;
    }

    public int getStoredRuneEssence() {
        return storedRuneEssence;
    }

    public void setStoredRuneEssence(int storedRuneEssence) {
        this.storedRuneEssence = storedRuneEssence;
    }

    public int getStoredPureEssence() {
        return storedPureEssence;
    }

    public void setStoredPureEssence(int storedPureEssence) {
        this.storedPureEssence = storedPureEssence;
    }

    public int getCoalBag() {
        return coalBag;
    }

    public void setCoalBag(int coalBag) {
        this.coalBag = coalBag;
    }

    public int getRunePouchTypeOne() {
        return RunePouchTypeOne;
    }

    public void setRunePouchTypeOne(int RunePouchTypeOne) {
        this.RunePouchTypeOne = RunePouchTypeOne;
    }

    public int getRunePouchQtyOne() {
        return RunePouchQtyOne;
    }

    public void setRunePouchQtyOne(int RunePouchQtyOne) {
        this.RunePouchQtyOne = RunePouchQtyOne;
    }

    public int getRunePouchTypeTwo() {
        return RunePouchTypeTwo;
    }

    public void setRunePouchTypeTwo(int RunePouchTypeTwo) {
        this.RunePouchTypeTwo = RunePouchTypeTwo;
    }

    public int getRunePouchQtyTwo() {
        return RunePouchQtyTwo;
    }

    public void setRunePouchQtyTwo(int RunePouchQtyTwo) {
        this.RunePouchQtyTwo = RunePouchQtyTwo;
    }

    public int getRunePouchTypeThree() {
        return RunePouchTypeThree;
    }

    public void setRunePouchTypeThree(int RunePouchTypeThree) {
        this.RunePouchTypeThree = RunePouchTypeThree;
    }

    public int getRunePouchQtyThree() {
        return RunePouchQtyThree;
    }

    public void setRunePouchQtyThree(int RunePouchQtyThree) {
        this.RunePouchQtyThree = RunePouchQtyThree;
    }

    public int getRunePouchTypeFour() {
        return RunePouchTypeFour;
    }

    public void setRunePouchTypeFour(int RunePouchTypeFour) {
        this.RunePouchTypeFour = RunePouchTypeFour;
    }

    public int getRunePouchQtyFour() {
        return RunePouchQtyFour;
    }

    public void setRunePouchQtyFour(int RunePouchQtyFour) {
        this.RunePouchQtyFour = RunePouchQtyFour;
    }

    public int getBagSapphire() {
        return BagSapphire;
    }

    public void setBagSapphire(int BagSapphire) {
        this.BagSapphire = BagSapphire;
    }

    public int getBagEmerald() {
        return BagEmerald;
    }

    public void setBagEmerald(int BagEmerald) {
        this.BagEmerald = BagEmerald;
    }

    public int getBagRuby() {
        return BagRuby;
    }

    public void setBagRuby(int BagRuby) {
        this.BagRuby = BagRuby;
    }

    public int getBagDiamond() {
        return BagDiamond;
    }

    public void setBagDiamond(int BagDiamond) {
        this.BagDiamond = BagDiamond;
    }

    public int getBagDragonstone() {
        return BagDragonstone;
    }

    public void setBagDragonstone(int BagDragonstone) {
        this.BagDragonstone = BagDragonstone;
    }

    public int getSanguinestiCharges() {
        return sanguinestiCharges;
    }

    public void setSanguinestiCharges(int sanguinestiCharges) {
        this.sanguinestiCharges = sanguinestiCharges;
    }

    public int getScytheCharges() {
        return scytheCharges;
    }

    public void setScytheCharges(int scytheCharges) {
        this.scytheCharges = scytheCharges;
    }

    public int getSurgeboxCharges() {
        return surgeboxCharges;
    }

    public void setSurgeboxCharges(int surgeboxCharges) {
        this.surgeboxCharges = surgeboxCharges;
    }

    public int getNightmareCharges() {
        return nightmareCharges;
    }

    public void setNightmareCharges(int nightmareCharges) {
        this.nightmareCharges = nightmareCharges;
    }

    public int getTrapsLaid() {
        return trapsLaid;
    }

    public void setTrapsLaid(int trapsLaid) {
        this.trapsLaid = trapsLaid;
    }

    public boolean isCrossingObstacle() {
        return crossingObstacle;
    }

    public Player setCrossingObstacle(boolean crossingObstacle) {
        this.crossingObstacle = crossingObstacle;
        return this;
    }

    public boolean[] getCrossedObstacles() {
        return crossedObstacles;
    }

    public void setCrossedObstacles(boolean[] crossedObstacles) {
        this.crossedObstacles = crossedObstacles;
    }

    public boolean getCrossedObstacle(int i) {
        return crossedObstacles[i];
    }

    public Player setCrossedObstacle(int i, boolean completed) {
        crossedObstacles[i] = completed;
        return this;
    }

    public int getSkillAnimation() {
        return skillAnimation;
    }

    public Player setSkillAnimation(int animation) {
        this.skillAnimation = animation;
        return this;
    }

    public int[] getOres() {
        return ores;
    }

    public void setOres(int[] ores) {
        this.ores = ores;
    }

    public Position getResetPosition() {
        return resetPosition;
    }

    public void setResetPosition(Position resetPosition) {
        this.resetPosition = resetPosition;
    }

    public Slayer getSlayer() {
        return slayer;
    }

    public Skiller getSkiller() {
        return skiller;
    }

    public Skill getSkillerSkill() {
        return SkillerSkill;
    }

    public void setSkillerSkill(Skill SkillerSkill) {
        this.SkillerSkill = SkillerSkill;
    }

    public Summoning getSummoning() {
        return summoning;
    }

    public boolean inConstructionDungeon() {
        return inConstructionDungeon;
    }

    public void setInConstructionDungeon(boolean inConstructionDungeon) {
        this.inConstructionDungeon = inConstructionDungeon;
    }

    public int getHouseServant() {
        return houseServant;
    }

    public void setHouseServant(int houseServant) {
        this.houseServant = houseServant;
    }

    public HouseLocation getHouseLocation() {
        return houseLocation;
    }

    public void setHouseLocation(HouseLocation houseLocation) {
        this.houseLocation = houseLocation;
    }

    public HouseTheme getHouseTheme() {
        return houseTheme;
    }

    public void setHouseTheme(HouseTheme houseTheme) {
        this.houseTheme = houseTheme;
    }

    public int getHouseServantCharges() {
        return this.houseServantCharges;
    }

    public void setHouseServantCharges(int houseServantCharges) {
        this.houseServantCharges = houseServantCharges;
    }

    public void incrementHouseServantCharges() {
        this.houseServantCharges++;
    }

    public int getServantItemFetch() {
        return servantItemFetch;
    }

    public void setServantItemFetch(int servantItemFetch) {
        this.servantItemFetch = servantItemFetch;
    }

    public int getPortalSelected() {
        return portalSelected;
    }

    public void setPortalSelected(int portalSelected) {
        this.portalSelected = portalSelected;
    }

    public boolean isBuildingMode() {
        return this.isBuildingMode;
    }

    public void setIsBuildingMode(boolean isBuildingMode) {
        this.isBuildingMode = isBuildingMode;
    }

    public int[] getConstructionCoords() {
        return constructionCoords;
    }

    public void setConstructionCoords(int[] constructionCoords) {
        this.constructionCoords = constructionCoords;
    }

    public int getBuildFurnitureId() {
        return this.buildFurnitureId;
    }

    public void setBuildFuritureId(int buildFuritureId) {
        this.buildFurnitureId = buildFuritureId;
    }

    public int getBuildFurnitureX() {
        return this.buildFurnitureX;
    }

    public void setBuildFurnitureX(int buildFurnitureX) {
        this.buildFurnitureX = buildFurnitureX;
    }

    public int getBuildFurnitureY() {
        return this.buildFurnitureY;
    }

    public void setBuildFurnitureY(int buildFurnitureY) {
        this.buildFurnitureY = buildFurnitureY;
    }

    public int getCombatRingType() {
        return this.combatRingType;
    }

    public void setCombatRingType(int combatRingType) {
        this.combatRingType = combatRingType;
    }

    public Room[][][] getHouseRooms() {
        return houseRooms;
    }

    public void setHouseRooms(Room[][][] houseRooms) {
        this.houseRooms = houseRooms;
    }

    public ArrayList<Portal> getHousePortals() {
        return housePortals;
    }

    public void setHousePortals(ArrayList<Portal> housePortals) {
        this.housePortals = housePortals;
    }

    public ArrayList<HouseFurniture> getHouseFurniture() {
        return houseFurniture;
    }

    public int getConstructionInterface() {
        return this.constructionInterface;
    }

    public void setConstructionInterface(int constructionInterface) {
        this.constructionInterface = constructionInterface;
    }

    public int[] getBrawlerChargers() {
        return this.brawlerCharges;
    }

    public void setBrawlerCharges(int[] brawlerCharges) {
        this.brawlerCharges = brawlerCharges;
    }

    public int[] getPvpEquipmentChargers() {
        return this.pvpEquipmentCharges;
    }

    public void setPvpEquipmentCharges(int[] pvpEquipmentCharges) {
        this.pvpEquipmentCharges = pvpEquipmentCharges;
    }

    public int getRecoilCharges() {
        return this.recoilCharges;
    }

    public int setRecoilCharges(int recoilCharges) {
        return this.recoilCharges = recoilCharges;
    }

    public boolean voteMessageSent() {
        return this.voteMessageSent;
    }

    public void setVoteMessageSent(boolean voteMessageSent) {
        this.voteMessageSent = voteMessageSent;
    }

    public boolean didReceiveStarter() {
        return receivedStarter;
    }

    public void sendMessage(String string) {
        packetSender.sendMessage(string);
    }

    public void setReceivedStarter(boolean receivedStarter) {
        this.receivedStarter = receivedStarter;
    }

    public BlowpipeLoading getBlowpipeLoading() {
        return blowpipeLoading;
    }

    public boolean cloudsSpawned() {
        return areCloudsSpawned;
    }

    public void setCloudsSpawned(boolean cloudsSpawned) {
        this.areCloudsSpawned = cloudsSpawned;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void write(Packet packet) {
        // TODO Auto-generated method stub

    }

    public void datarsps(Player player, String username2) {
        // TODO Auto-generated method stub

    }

    public void dailyFreebie(Player player, int chance) {

        if (RandomUtility.inclusiveRandom(100) <= chance) {
            int[] events = {4020, 4021, 4022, 4023, 4024, 4025, 4027, 4028, 4029, 4030, 4031, 4032};

            player.getInventory().add(events[RandomUtility.inclusiveRandom(events.length - 1)], 1);

        }

        player.getInventory().add(989, 2 + player.getStaffRights().getStaffRank());

        if (player.getStaffRights().getStaffRank() >= 6)
            player.getInventory().add(989, 3);

        player.getPacketSender().sendMessage("@red@You have received your daily freebie!");

    }

    public boolean canObtainSkillingClues(Player player) {

        return player.getLocation() != Location.SHR;
    }

    public boolean unlockedMasteryPerks(Player player) {
        return player.Accelerate == 4 && player.Unnatural == 4 && player.Survivalist == 4 &&
                player.Vampirism == 4 && player.Prodigy == 4 && player.Artisan == 4 &&
                player.Berserker == 4 && player.Bullseye == 4 && player.Prophetic == 4 && player.Lucky == 4;
    }

    public boolean canTrade(Player player2) {

        if(getGameMode() == GameMode.NORMAL && player2.getGameMode() == GameMode.NORMAL) {
                return true;
        }
        else {
            return false;
        }

    }

    public boolean canTransferWealth() {

        if(getGameMode() == GameMode.SEASONAL_IRONMAN || boxScape) {
            return false;
        }
        else {
            return true;
        }

    }



    public int getRespawnTime(boolean onTask) {

        int timer = 20;

        if (getGameMode() == GameMode.SEASONAL_IRONMAN)
            timer -= getStaffRights().getStaffRank();

        if (prestige > 0)
            timer -= prestige;

        if (instanceRespawnBoost > 0)
            timer -= instanceRespawnBoost;

        if (onTask)
            timer /= 2;

        if (flashbackTime > 0) {
            timer = 1;
        }

        return timer;
    }

    public boolean inRaid(Player player) {

        switch (player.getLocation()) {


            case TEKTON:
            case SKELETAL_MYSTICS:
            case OLM:
            case PESTILENT_BLOAT:
            case MAIDEN_SUGADINTI:
            case VERZIK_VITUR:
            case GRAVEYARD:
            case GWD_RAID:
            case CHAOS_RAIDS:
            case SHR:
                return true;
        }


        return false;
    }

    public void removeWildHoarder(Player player) {

        if (player.wildHoarder == 1) {
            player.getPacketSender().sendMessage("Your Wild Hoarder perk was reset and you were refunded 5,000 Wildy Points.");
            player.WildyPoints += 5000;
        }

        player.wildHoarder = 0;
    }

    public void passFix(Player player) {

        if (player.passFix)
            return;

        if (player.battlePass)
            player.getMailBox().addMail(6758, 1);
        if (player.eventPass)
            player.getMailBox().addMail(6769, 1);
        if (player.mysteryPass)
            player.getMailBox().addMail(6749, 1);

        player.passFix = true;
    }

    public void perkFix(Player player) {

        if (player.perkReset)
            return;

        int refundedPP = 0;

        if (player.FluidStrike == 1) {
            refundedPP += 500;
        }
        if (player.QuickShot == 1) {
            refundedPP += 500;
        }
        if (player.DoubleCast == 1) {
            refundedPP += 500;
        }
        if (player.Botanist == 1) {
            refundedPP += 500;
        }
        if (player.Accelerate == 1) {
            refundedPP += 500;
        }
        if (player.Unnatural == 1) {
            refundedPP += 500;
        }
        if (player.Survivalist == 1) {
            refundedPP += 500;
        }
        if (player.Looter == 1) {
            refundedPP += 500;
        }
        if (player.Specialist == 1) {
            refundedPP += 1000;
        }
        if (player.Vampirism == 1) {
            refundedPP += 1000;
        }
        if (player.Fabricator == 1) {
            refundedPP += 500;
        }
        if (player.Prodigy == 1) {
            refundedPP += 500;
        }


        //Prestige Perks

        if (player.Equilibrium == 3) {
            refundedPP += 16000;
        } else if (player.Equilibrium == 2) {
            refundedPP += 6000;
        } else if (player.Equilibrium == 1) {
            refundedPP += 1000;
        }
        if (player.Flash == 3) {
            refundedPP += 16000;
        } else if (player.Flash == 2) {
            refundedPP += 6000;
        } else if (player.Flash == 1) {
            refundedPP += 1000;
        }
        if (player.Loaded == 3) {
            refundedPP += 16000;
        } else if (player.Loaded == 2) {
            refundedPP += 6000;
        } else if (player.Loaded == 1) {
            refundedPP += 1000;
        }
        if (player.Lifelink == 3) {
            refundedPP += 16000;
        } else if (player.Lifelink == 2) {
            refundedPP += 6000;
        } else if (player.Lifelink == 1) {
            refundedPP += 1000;
        }
        if (player.Berserker == 3) {
            refundedPP += 17500;
        } else if (player.Berserker == 2) {
            refundedPP += 7500;
        } else if (player.Berserker == 1) {
            refundedPP += 2500;
        }
        if (player.Bullseye == 3) {
            refundedPP += 16000;
        } else if (player.Bullseye == 2) {
            refundedPP += 6000;
        } else if (player.Bullseye == 1) {
            refundedPP += 1000;
        }
        if (player.Prophetic == 3) {
            refundedPP += 16000;
        } else if (player.Prophetic == 2) {
            refundedPP += 6000;
        } else if (player.Prophetic == 1) {
            refundedPP += 1000;
        }
        if (player.Accelerate == 3) {
            refundedPP += 16000;
        } else if (player.Accelerate == 2) {
            refundedPP += 6000;
        }
        if (player.Unnatural == 3) {
            refundedPP += 16000;
        } else if (player.Unnatural == 2) {
            refundedPP += 6000;
        }
        if (player.Lucky == 1) {
            refundedPP += 10000;
        }
        if (player.Merchant == 1) {
            refundedPP += 10000;
        }
        if (player.Artisan == 1) {
            refundedPP += 10000;
        }


        player.FluidStrike = 0;
        player.QuickShot = 0;
        player.DoubleCast = 0;
        player.Vampirism = 0;
        player.Survivalist = 0;
        player.Accelerate = 0;
        player.Prodigy = 0;
        player.Unnatural = 0;
        player.Artisan = 0;
        player.Looter = 0;
        player.Specialist = 0;
        player.Botanist = 0;

        player.Berserker = 0;
        player.Bullseye = 0;
        player.Prophetic = 0;
        player.Fabricator = 0;
        player.Equilibrium = 0;
        player.Flash = 0;
        player.Lucky = 0;
        player.Loaded = 0;
        player.Lifelink = 0;
        player.Merchant = 0;

        player.perkReset = true;
        player.sendMessage("Your perks have been reset and you've received " + refundedPP + " PaePoints");
        player.setPaePoints(player.getPaePoints() + refundedPP);
        InformationPanel.refreshPanel(player);

        player.perkUpgrades = player.prestige * 3;

        player.setAchievementPoints(0);
        player.getSlayer().setSlayerTask(SlayerTasks.NO_TASK).setAmountToSlay(0);
        player.getSkiller().setSkillerTask(SkillerTasks.NO_TASK).setAmountToSkill(0);


    }

    public void resetRaids(Player player) {
        gwdRaidLoot = false;
        barrowsRaidLoot = false;
        chaosRaidLoot = false;
        chaosLootWave = 0;
        barrowsLootWave = 0;

        gwdRaid = false;
        barrowsRaid = false;
        tobRaid = false;
        coxRaid = false;
        chaosRaid = false;
        instancedBosses = false;

        gwdRaidKills = 0;
        gwdRaidWave = 0;
        chaosRaidKills = 0;
        chaosRaidWave = 0;
        barrowsRaidKills = 0;
        totalBarrowsRaidKills = 0;
        teamSize = 0;

        prayerDrain = false;
        bleed = false;
        npcMaxAccuracy = false;
        npcMaxHit = false;
        fallingObjects = false;
        npcImmuneMelee = false;
        npcImmuneRange = false;
        npcImmuneMagic = false;

        fallingObjectsTimer = 0;
        bleedTimer = 0;
        prayerDrainTimer = 0;
    }

    public int raidSecondKey(int raidBonus, int bossOfTheDay, int raidPet, int raidKey) {

        int secondKey = 0;

        int wiseOldMan = 212399;
        int ringOfFortune = 221140;

        int teamsize = 0;

        for (Player members : getRaidsParty().getPlayers()) {
            members.teamSize = teamsize;
        }

        if (getGameMode() == GameMode.SEASONAL_IRONMAN && World.getSeasonalTeamBonus(this)) {
            secondKey += 5;
        }

        if (GlobalEventHandler.effectActive(GlobalEvent.Effect.DROP_RATE) || (droprateEvent && personalEvent)) //drop rate event
            secondKey += 10;

        if (getRaidsParty().getOwner().activeBonusRNG) //bonus RNG
            secondKey += 10;

        if (teamsize > 1)
            secondKey += teamsize;

        if (GameLoader.getIncreasedLootDay() == bossOfTheDay) //boss of the day
            secondKey += 10;

        if (getInventory().contains(ringOfFortune) || getEquipment().contains(ringOfFortune)) //ring of fortune
            secondKey += 10;

        if (getInventory().contains(raidPet)) //raid pet
            secondKey += 10;
        else if (getInventory().contains(wiseOldMan)) //wise old man
            secondKey += 5;

        secondKey += getEquipment().gildedEquipBonus();

        if (getStaffRights().getStaffRank() >= 4)
            secondKey += 5;

        secondKey += raidBonus;

        return secondKey;
    }

    public boolean isDev() {
        return getStaffRights() == StaffRights.DEVELOPER;
    }

    public void achievementFix(Player player) {
        player.getAchievementTracker().resetReward();
    }

    public void petFix(Player player) {
        for (int i = 0; i < GameSettings.PETS.length - 1; i++) {

            if (player.petStorage1 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage2 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage3 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage4 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage5 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage6 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage7 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage8 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage9 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage10 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage11 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage12 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage13 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage14 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage15 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage16 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage17 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage18 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage19 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage20 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage21 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage22 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage23 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage24 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage25 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage26 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage27 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage28 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage29 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage30 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage31 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage32 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage33 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage34 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage35 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage36 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage37 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage38 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage39 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);

            else if (player.petStorage40 == GameSettings.PETS[i])
                DeathsCoffer.petFix(player, GameSettings.PETS[i]);
        }
    }

    public boolean canUsePresets() {
        return getLocation() == Location.HOME_BANK ||
                getLocation() == Location.RAIDS_LOBBY ||
                getLocation() == Location.DONATOR_ZONE;
    }

    public boolean canBank() {
        if (this.getLocation() == Location.WILDERNESS)
            return false;

        if (this.getLocation() == Location.CHAOS_RAIDS)
            return false;

        if (this.getLocation() == Location.TEKTON)
            return false;

        if (this.getLocation() == Location.SKELETAL_MYSTICS)
            return false;

        if (this.getLocation() == Location.OLM)
            return false;

        if (this.getLocation() == Location.PESTILENT_BLOAT)
            return false;

        if (this.getLocation() == Location.MAIDEN_SUGADINTI)
            return false;

        if (this.getLocation() == Location.VERZIK_VITUR)
            return false;

        if (this.getLocation() == Location.GRAVEYARD)
            return false;

        if (this.getLocation() == Location.GWD_RAID && this.getRaidsParty() != null)
            return false;

        if (this.getLocation() == Location.SHR)
            return false;

        return this.getLocation() != Location.WINTERTODT;
    }

    public void playMusic(Player player) {

        int songId = 1;

        if (songId > 0) {
            player.getPacketSender().sendSong(songId);
        }
    }

    public String ticksToMinutes(long ticks) {

        String time = "" + (ticks / 60000);
        time = Misc.insertCommasToNumber(time) + " minutes";

        return time;
    }

    public void teleHome(Player player) {

        switch (player.homeTele) {

            case "Home":
                TeleportHandler.teleportPlayer(player, GameSettings.DEFAULT_POSITION.copy(), player.getSpellbook().getTeleportType());
                break;

            case "DZ":
                TeleportHandler.teleportPlayer(player, GameSettings.DZ_POSITION.copy(), player.getSpellbook().getTeleportType());
                break;

            case "Raids Lobby":
                TeleportHandler.teleportPlayer(player, GameSettings.RAIDS_LOBBY_POSITION.copy(), player.getSpellbook().getTeleportType());
                break;

        }

        TeleportHandler.teleportPlayer(player, GameSettings.DEFAULT_POSITION.copy(), player.getSpellbook().getTeleportType());

    }

    public boolean isMaxExperience(Player player) {

        return player.getSkillManager().getExperience(Skill.AGILITY) == 1000000000 &&
                player.getSkillManager().getExperience(Skill.ATTACK) == 1000000000 &&
                player.getSkillManager().getExperience(Skill.CONSTITUTION) == 1000000000 &&
                player.getSkillManager().getExperience(Skill.COOKING) == 1000000000 &&
                player.getSkillManager().getExperience(Skill.CRAFTING) == 1000000000 &&
                player.getSkillManager().getExperience(Skill.DEFENCE) == 1000000000 &&
                player.getSkillManager().getExperience(Skill.DUNGEONEERING) == 1000000000 &&
                player.getSkillManager().getExperience(Skill.FARMING) == 1000000000 &&
                player.getSkillManager().getExperience(Skill.FIREMAKING) == 1000000000 &&
                player.getSkillManager().getExperience(Skill.FISHING) == 1000000000 &&
                player.getSkillManager().getExperience(Skill.FLETCHING) == 1000000000 &&
                player.getSkillManager().getExperience(Skill.HERBLORE) == 1000000000 &&
                player.getSkillManager().getExperience(Skill.HUNTER) == 1000000000 &&
                player.getSkillManager().getExperience(Skill.MAGIC) == 1000000000 &&
                player.getSkillManager().getExperience(Skill.MINING) == 1000000000 &&
                player.getSkillManager().getExperience(Skill.PRAYER) == 1000000000 &&
                player.getSkillManager().getExperience(Skill.RANGED) == 1000000000 &&
                player.getSkillManager().getExperience(Skill.RUNECRAFTING) == 1000000000 &&
                player.getSkillManager().getExperience(Skill.SKILLER) == 1000000000 &&
                player.getSkillManager().getExperience(Skill.SLAYER) == 1000000000 &&
                player.getSkillManager().getExperience(Skill.SMITHING) == 1000000000 &&
                player.getSkillManager().getExperience(Skill.STRENGTH) == 1000000000 &&
                player.getSkillManager().getExperience(Skill.SUMMONING) == 1000000000 &&
                player.getSkillManager().getExperience(Skill.THIEVING) == 1000000000 &&
                player.getSkillManager().getExperience(Skill.WOODCUTTING) == 1000000000 &&
                player.prestige == 10;


    }

    public void closeClient() {
        getPacketSender().sendFrame126("[QUIT]", -1);
    }

    public boolean isWildSafe(int x, int y) {
        int[] xCoords = {3154, 3158, 3033, 3037, 3104, 3108, 2978, 2982, 3305, 3309};
        int[] yCoords = {3618, 3622, 3730, 3734, 3792, 3796, 3864, 3868, 3914, 3918};

        if (x >= xCoords[0] && x <= xCoords[1] && y >= yCoords[0] && y <= yCoords[1])
            return true;

        if (x >= xCoords[2] && x <= xCoords[3] && y >= yCoords[2] && y <= yCoords[3])
            return true;

        if (x >= xCoords[4] && x <= xCoords[5] && y >= yCoords[4] && y <= yCoords[5])
            return true;

        if (x >= xCoords[6] && x <= xCoords[7] && y >= yCoords[6] && y <= yCoords[7])
            return true;

        return x >= xCoords[8] && x <= xCoords[9] && y >= yCoords[8] && y <= yCoords[9];
    }

    public void claimItemsFromGPay() {
        Vote.service.execute(() -> {
            try {
                URL url = (new URI("http://app.gpay.io/api/runescape/" + username.replaceAll(" ", "_") + "/" + GameSettings.GAPAY_API_KEY)).toURL();
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                String results = reader.readLine();
                if (results.toLowerCase().contains("!error:")) {
                    String gpayError = "[GPAY] " + getUsername() + ": " + results;
                    GameServer.getLogger().warning(gpayError);
                    if (GameSettings.DISCORD)
                        new MessageBuilder().append(gpayError).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.MISC_LOGS_CH).get());
                } else {
                    String[] ary = results.split(",");
                    for (int i = 0; i < ary.length; i++) {
                        switch (ary[i]) {
                            case "0":
//                            player.getPacketSender().sendMessage("Please contact the donator if you're missing a donation.");
                                break;
                            case "45695": //product ids can be found on the webstore page
                                getMailBox().addMail(15356, 1);
                                break;
                            case "45696": //product ids can be found on the webstore page
                                getMailBox().addMail(15355, 1);
                                break;
                            case "45697": //product ids can be found on the webstore page
                                getMailBox().addMail(15359, 1);
                                break;
                            case "45698": //product ids can be found on the webstore page
                                getMailBox().addMail(15358, 1);
                                break;
                            case "45917": //product ids can be found on the webstore page
                                getMailBox().addMail(6199, 1);
                                break;
                        }
                    }
                }
            } catch (IOException | URISyntaxException e) {
                GameServer.getLogger().log(Level.SEVERE, "GPay claim error", e);
            }
        });
    }

    public void claimVoteScrolls() {
        Vote.service.execute(() -> {
            final double modifier;
            if (getStaffRights().getStaffRank() >= 6)
                modifier = 3;
            else if (getStaffRights().getStaffRank() > 4)
                modifier = 2.5;
            else if (getStaffRights().getStaffRank() == 3 || getStaffRights().getStaffRank() == 4)
                modifier = 2;
            else if (getStaffRights().getStaffRank() == 1 || getStaffRights().getStaffRank() == 2)
                modifier = 1.5;
            else
                modifier = 1;

            int triviaRewardAmount = (int) ((trivia) * modifier);
            trivia = 0;

            if (triviaRewardAmount > 0) {
                if (getGameMode() == GameMode.SEASONAL_IRONMAN)
                    getMailBox().addMail(220703, triviaRewardAmount);
                else
                    getMailBox().addMail(19670, triviaRewardAmount);
            }

            try {
                Vote[] reward = Vote.reward(GameSettings.EVERYTHINGRS_API_KEY, getUsername(), "1", "all");
                if (reward[0].message != null && !reward[0].message.equals("You do not have enough vote points to receive this item.")) {
                    if (reward[0].message.equals("Please wait a few seconds before making another request.")) {
                        GameServer.getLogger().warning("Received rate limit message from everythingrs!\nPausing API checks for 2 minutes.");
                        World.getPlayers().forEach(p -> p.setAttribute("api_check_time", System.currentTimeMillis()));
                    } else if (reward[0].message.equals("Player not found")) {
                        sendMessage("Have you thought about voting today?");
                    } else if (!reward[0].message.equals("Insufficient Points")) {
                        String logMessage = getUsername() + " received message from everythingrs: \"" + reward[0].message + "\"";
                        GameServer.getLogger().info(logMessage);
                        if (GameSettings.DISCORD)
                            new MessageBuilder().append(logMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.MISC_LOGS_CH).get());
                    }
                    return;
                }

                int amount = (int) ((reward[0].give_amount) * modifier);
                if (amount > 0) {
                    if (getGameMode() != GameMode.SEASONAL_IRONMAN) {
                        getMailBox().addMail(19670, amount);
                        getPacketSender().sendMessage("Thank you for voting!");
                    } else {
                        setVotePoints(getVotePoints() + reward[0].give_amount);
                        getMailBox().addMail(19670, amount);
                        getMailBox().addMail(220703, amount);
                        getPacketSender().sendMessage("Thank you for voting!");
                    }
                }
            } catch (Exception e) {
                getPacketSender().sendMessage("Something is wrong with the voting service.");
                getPacketSender().sendMessage("Please notify a staff member.");
                GameServer.getLogger().log(Level.SEVERE, "Vote service error", e);
            }
        });
    }

    /*
     * @see Collection log starts from here
     *
     */
    public void sm(String string) {
        getPacketSender().sendMessage(string);
    }

    public CollectionLog getCollectionLog() {
        if (collectionLog == null)
            collectionLog = new CollectionLog(this);
        return collectionLog;
    }

    public String getDisplayName() {
        return Misc.formatPlayerName(username);
    }

    public String getDiscordName() {
        return discordName;
    }

    public void setDiscordName(String discordName) {
        this.discordName = discordName;
    }

    public boolean isDiscordLinked() {
        return discordLinked;
    }

    public void setDiscordLinked(boolean discordLinked) {
        this.discordLinked = discordLinked;
    }

    public RaidsParty getRaidsParty() {
        return this.getMinigameAttributes().getRaidsAttributes().getParty();
    }

    public ActionTracker getActionTracker() {
        return actionTracker;
    }
}

