package com.arlania.world.content.minigames.impl.raidsparty;

import com.arlania.GameSettings;
import com.arlania.model.GameMode;
import com.arlania.model.GameObject;
import com.arlania.model.Locations;
import com.arlania.model.Position;
import com.arlania.world.World;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.GreatOlm;
import com.arlania.world.content.transportation.TeleportHandler;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A class that will handle Great Olm party system
 *
 * @author Ints
 */
public class RaidsParty {

    public RaidsParty(Player owner) {
        this.owner = owner;
        player_members = new CopyOnWriteArrayList<Player>();
        playersInRaids = new CopyOnWriteArrayList<Player>();
        player_members.add(owner);
    }

    private final Player owner;

    private final CopyOnWriteArrayList<Player> player_members;
    private final CopyOnWriteArrayList<Player> playersInRaids;
    private final CopyOnWriteArrayList<NPC> npc_members = new CopyOnWriteArrayList<NPC>();

    private boolean hasEnteredDungeon;
    private int kills, deaths, instanceLevel;

    private int height;

    public int nexMinionKills = 0;

    public int MysticKills = 0;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private Position greatolmPosition = new Position(3238, 5738);
    private Position leftHandPosition = new Position(3238, 5733);
    private Position rightHandPosition = new Position(3238, 5743);

    NPC leftHandNpc;
    NPC greatolmNpc; // olm head
    NPC rightHandNpc;// right claw

    GameObject greatolmObject = new GameObject(129881, greatolmPosition, 10, 1);
    GameObject leftHandObject = new GameObject(129884, leftHandPosition, 10, 1);
    GameObject rightHandObject = new GameObject(129887, rightHandPosition, 10, 1);

    private boolean canAttackLeftHand;
    private boolean transitionPhase;

    private int currentPhase = 0;

    private int phaseAmount;
    private boolean olmTurning;
    private boolean clenchedHand;



    public String shrColor = "";
    public int shrItem = -1;

    public int shrFloorOneBossKey;
    public Boolean shrFloorOneBossSpawned = false;
    public int shrFloorTwoBossKey;
    public Boolean shrFloorTwoBossSpawned = false;
    public int shrFloorThreeBossKey;
    public Boolean shrFloorThreeBossSpawned = false;
    public int shrFloorFourBossKey;
    public Boolean shrFloorFourBossSpawned = false;

    private Position[] acidPools = new Position[50];
    private NPC[] acidPoolsNpcs = new NPC[10];
    private Player acidDripPlayer;
    private CopyOnWriteArrayList<Position> dripPools = new CopyOnWriteArrayList<Position>();

    private Position[] crystalBursts = new Position[5];
    private Position[] lightningSpots = new Position[5];

    private int crystalAmount;

    private int olmAttackTimer;
    private int leftHandAttackTimer;
    private boolean canAttack;
    private boolean canAttackHand;
    private Position swapPosition;

    private int tektonSpecial;

    private final CopyOnWriteArrayList<Player> swapPlayers = new CopyOnWriteArrayList<Player>();

    private boolean leftHandDead;
    private boolean rightHandDead;

    private int graphicSwap;
    private boolean lonePair;

    private int attackCount;
    private String phaseAttackSecondPhase;

    private boolean leftHandProtected;
    private Player fallingCrystalsPlayer;

    private int currentLeftHandCycle;
    private boolean healingOlmLeftHand;

    private Player fireWallPlayer;
    private CopyOnWriteArrayList<NPC> fireWallNpcs = new CopyOnWriteArrayList<NPC>();
    private final CopyOnWriteArrayList<Position> lifeSiphonPositions = new CopyOnWriteArrayList<Position>();

    public CopyOnWriteArrayList<Position> getLifeSiphonPositions() {
        return lifeSiphonPositions;
    }

    private NPC fireWallSpawn;
    private NPC fireWallSpawn1;

    private CopyOnWriteArrayList<String> phaseAttack = new CopyOnWriteArrayList<String>();

    private boolean lastPhaseStarted;

    private boolean clenchedHandFirst;
    private boolean clenchedHandSecond;

    private boolean unClenchedHandFirst;

    private boolean unClenchedHandSecond;

    private boolean switchingPhases;
    private boolean olmAttacking;
    private boolean switchAfterAttack;

    public boolean isSwitchAfterAttack() {
        return switchAfterAttack;
    }

    public void setSwitchAfterAttack(boolean switchAfterAttack) {
        this.switchAfterAttack = switchAfterAttack;
    }

    public boolean isOlmAttacking() {
        return olmAttacking;
    }

    public void setOlmAttacking(boolean olmAttacking) {
        this.olmAttacking = olmAttacking;
    }

    public boolean isSwitchingPhases() {
        return switchingPhases;
    }

    public void setSwitchingPhases(boolean switchingPhases) {
        this.switchingPhases = switchingPhases;
    }

    public boolean isUnClenchedHandFirst() {
        return unClenchedHandFirst;
    }

    public void setUnClenchedHandFirst(boolean unClenchedHandFirst) {
        this.unClenchedHandFirst = unClenchedHandFirst;
    }

    public boolean isUnClenchedHandSecond() {
        return unClenchedHandSecond;
    }

    public void setUnClenchedHandSecond(boolean unClenchedHandSecond) {
        this.unClenchedHandSecond = unClenchedHandSecond;
    }

    public boolean isClenchedHandFirst() {
        return clenchedHandFirst;
    }

    public void setClenchedHandFirst(boolean clenchedHandFirst) {
        this.clenchedHandFirst = clenchedHandFirst;
    }

    public boolean isClenchedHandSecond() {
        return clenchedHandSecond;
    }

    public void setClenchedHandSecond(boolean clenchedHandSecond) {
        this.clenchedHandSecond = clenchedHandSecond;
    }

    public boolean isLastPhaseStarted() {
        return lastPhaseStarted;
    }

    public void setLastPhaseStarted(boolean lastPhaseStarted) {
        this.lastPhaseStarted = lastPhaseStarted;
    }

    public CopyOnWriteArrayList<String> getPhaseAttack() {
        return phaseAttack;
    }

    public void setPhaseAttack(CopyOnWriteArrayList<String> phaseAttack) {
        this.phaseAttack = phaseAttack;
    }

    public NPC getFireWallSpawn() {
        return fireWallSpawn;
    }

    public void setFireWallSpawn(NPC fireWallSpawn) {
        this.fireWallSpawn = fireWallSpawn;
    }

    public NPC getFireWallSpawn1() {
        return fireWallSpawn1;
    }

    public void setFireWallSpawn1(NPC fireWallSpawn1) {
        this.fireWallSpawn1 = fireWallSpawn1;
    }

    public CopyOnWriteArrayList<NPC> getFireWallNpcs() {
        return fireWallNpcs;
    }

    public void setFireWallNpcs(CopyOnWriteArrayList<NPC> fireWallNpcs) {
        this.fireWallNpcs = fireWallNpcs;
    }

    public Player getFireWallPlayer() {
        return fireWallPlayer;
    }

    public void setFireWallPlayer(Player fireWallPlayer) {
        this.fireWallPlayer = fireWallPlayer;
    }

    public boolean isHealingOlmLeftHand() {
        return healingOlmLeftHand;
    }

    public void setHealingOlmLeftHand(boolean healingOlmLeftHand) {
        this.healingOlmLeftHand = healingOlmLeftHand;
    }

    public String getPhaseAttackSecondPhase() {
        return phaseAttackSecondPhase;
    }

    public void setPhaseAttackSecondPhase(String phaseAttackSecondPhase) {
        this.phaseAttackSecondPhase = phaseAttackSecondPhase;
    }

    public int getCurrentLeftHandCycle() {
        return currentLeftHandCycle;
    }

    public void setCurrentLeftHandCycle(int currentLeftHandCycle) {
        this.currentLeftHandCycle = currentLeftHandCycle;
    }

    public Player getFallingCrystalsPlayer() {
        return fallingCrystalsPlayer;
    }

    public void setFallingCrystalsPlayer(Player fallingCrystalsPlayer) {
        this.fallingCrystalsPlayer = fallingCrystalsPlayer;
    }

    public boolean isLeftHandProtected() {
        return leftHandProtected;
    }

    public void setLeftHandProtected(boolean leftHandProtected) {
        this.leftHandProtected = leftHandProtected;
    }

    public int getAttackCount() {
        return attackCount;
    }

    public void setTektonSpecial(int tektonSpecial) {
        this.tektonSpecial = tektonSpecial;
    }

    public int getTektonSpecial() {
        return tektonSpecial;
    }

    public void setAttackCount(int attackCount) {
        this.attackCount = attackCount;
    }

    public int getMysticKills() {
        return MysticKills;
    }

    public void setMysticKills(int MysticKills) {
        this.MysticKills = MysticKills;
    }

    private CopyOnWriteArrayList<Player> burnPlayers = new CopyOnWriteArrayList<Player>();

    public CopyOnWriteArrayList<Player> getBurnPlayers() {
        return burnPlayers;
    }

    public void setBurnPlayers(CopyOnWriteArrayList<Player> burnPlayers) {
        this.burnPlayers = burnPlayers;
    }

    public CopyOnWriteArrayList<Position> getDripPools() {
        return dripPools;
    }

    public void setDripPools(CopyOnWriteArrayList<Position> dripPools) {
        this.dripPools = dripPools;
    }

    public Player getAcidDripPlayer() {
        return acidDripPlayer;
    }

    public void setAcidDripPlayer(Player acidDripPlayer) {
        this.acidDripPlayer = acidDripPlayer;
    }

    public boolean isLonePair() {
        return lonePair;
    }

    public void setLonePair(boolean lonePair) {
        this.lonePair = lonePair;
    }

    public int getGraphicSwap() {
        return graphicSwap;
    }

    public void setGraphicSwap(int graphicSwap) {
        this.graphicSwap = graphicSwap;
    }

    public Position getSwapPosition() {
        return swapPosition;
    }

    public void setSwapPosition(Position swapPosition) {
        this.swapPosition = swapPosition;
    }

    public CopyOnWriteArrayList<Player> getSwapPlayers() {
        return swapPlayers;
    }

    public boolean isClenchedHand() {
        return clenchedHand;
    }

    public void setClenchedHand(boolean clenchedHand) {
        this.clenchedHand = clenchedHand;
    }

    public boolean isLeftHandDead() {
        return leftHandDead;
    }

    public void setLeftHandDead(boolean leftHandDead) {
        this.leftHandDead = leftHandDead;
    }

    public boolean isRightHandDead() {
        return rightHandDead;
    }

    public void setRightHandDead(boolean rightHandDead) {
        this.rightHandDead = rightHandDead;
    }

    public boolean isCanAttackHand() {
        return canAttackHand;
    }

    public void setCanAttackHand(boolean canAttackHand) {
        this.canAttackHand = canAttackHand;
    }

    public int getLeftHandAttackTimer() {
        return leftHandAttackTimer;
    }

    public void setLeftHandAttackTimer(int leftHandAttackTimer) {
        this.leftHandAttackTimer = leftHandAttackTimer;
    }

    public NPC[] getAcidPoolsNpcs() {
        return acidPoolsNpcs;
    }

    public void setAcidPoolsNpcs(NPC[] acidPoolsNpcs) {
        this.acidPoolsNpcs = acidPoolsNpcs;
    }

    public Position[] getLightningSpots() {
        return lightningSpots;
    }

    public void setLightningSpots(Position[] lightningSpots) {
        this.lightningSpots = lightningSpots;
    }

    public boolean getCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public boolean getOlmTurning() {
        return olmTurning;
    }

    public void setOlmTurning(boolean olmTurning) {
        this.olmTurning = olmTurning;
    }

    public int getOlmAttackTimer() {
        return olmAttackTimer;
    }

    public void setOlmAttackTimer(int olmAttackTimer) {
        this.olmAttackTimer = olmAttackTimer;
    }

    public int getCrystalAmount() {
        return crystalAmount;
    }

    public void setCrystalAmount(int crystalAmount) {
        this.crystalAmount = crystalAmount;
    }

    public Position[] getCrystalBursts() {
        return crystalBursts;
    }

    public void setCrystalBursts(Position[] crystalBursts) {
        this.crystalBursts = crystalBursts;
    }

    private CopyOnWriteArrayList<Player> playersToAttack = new CopyOnWriteArrayList<Player>();

    public CopyOnWriteArrayList<Player> getPlayersToAttack() {
        return playersToAttack;
    }

    public void setPlayersToAttack(CopyOnWriteArrayList<Player> playersToAttack) {
        this.playersToAttack = playersToAttack;
    }

    public int getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(int currentPhase) {
        this.currentPhase = currentPhase;
    }

    public Position[] getAcidPools() {
        return acidPools;
    }

    public void setAcidPools(Position[] acidPools) {
        this.acidPools = acidPools;
    }

    public int getPhaseAmount() {
        return phaseAmount;
    }

    public void setPhaseAmount(int phaseAmount) {
        this.phaseAmount = phaseAmount;
    }

    public boolean isTransitionPhase() {
        return transitionPhase;
    }

    public void setTransitionPhase(boolean transitionPhase) {
        this.transitionPhase = transitionPhase;
    }

    public boolean isCanAttackLeftHand() {
        return canAttackLeftHand;
    }

    public void setCanAttackLeftHand(boolean canAttackLeftHand) {
        this.canAttackLeftHand = canAttackLeftHand;
    }

    public GameObject getGreatOlmObject() {
        return greatolmObject;
    }

    public GameObject getLeftHandObject() {
        return leftHandObject;
    }

    public GameObject getRightHandObject() {
        return rightHandObject;
    }

    public void setGreatOlmObject(GameObject greatolmObject) {
        this.greatolmObject = greatolmObject;
    }

    public void setLeftHandObject(GameObject leftHandObject) {
        this.leftHandObject = leftHandObject;
    }

    public void setRightHandObject(GameObject rightHandObject) {
        this.rightHandObject = rightHandObject;
    }

    public Position getGreatOlmPosition() {
        return greatolmPosition;
    }

    public void setGreatOlmPosition(Position GreatOlmPosition) {
        greatolmPosition = GreatOlmPosition;
    }

    public Position getLeftHandPosition() {
        return leftHandPosition;
    }

    public void setLeftHandPosition(Position LeftHandPosition) {
        leftHandPosition = LeftHandPosition;
    }

    public Position getRightHandPosition() {
        return rightHandPosition;
    }

    public void setRightHandPosition(Position RightHandPosition) {
        rightHandPosition = RightHandPosition;
    }

    public NPC getLeftHandNpc() {
        return leftHandNpc;
    }

    public void setLeftHandNpc(NPC leftHandNpc) {
        this.leftHandNpc = leftHandNpc;
    }

    public NPC getGreatOlmNpc() {
        return greatolmNpc;
    }

    public void setGreatOlmNpc(NPC greatolmNpc) {
        this.greatolmNpc = greatolmNpc;
    }

    public NPC getRightHandNpc() {
        return rightHandNpc;
    }

    public void setRightHandNpc(NPC rightHandNpc) {
        this.rightHandNpc = rightHandNpc;
    }

    public void invite(Player p) {
        if (getOwner() == null || p == getOwner())
            return;
        if (hasEnteredDungeon) {
            getOwner().getPacketSender().sendMessage("You cannot invite anyone right now.");
            return;
        }
        if (getOwner().getGameMode() == GameMode.SEASONAL_IRONMAN && p.getGameMode() != GameMode.SEASONAL_IRONMAN) {
            getOwner().getPacketSender().sendMessage("Only Seasonals can party with other Seasonals.");
            return;
        }
        if (getOwner().getGameMode() != GameMode.SEASONAL_IRONMAN && p.getGameMode() == GameMode.SEASONAL_IRONMAN) {
            getOwner().getPacketSender().sendMessage("Only Seasonals can party with other Seasonals.");
            return;
        }

        if (player_members.size() >= 5) {
            getOwner().getPacketSender().sendMessage("Your party is full.");
            return;
        }
        if ((p.getLocation() != Locations.Location.RAIDS_LOBBY && p.getLocation() != Locations.Location.CRASH_ISLAND) || p.isTeleporting()) {
            getOwner().getPacketSender().sendMessage("That player is not in Raids.");
            return;
        }
        if (player_members.contains(p)) {
            getOwner().getPacketSender().sendMessage("That player is already in your party.");
            return;
        }
        if (p.getMinigameAttributes().getRaidsAttributes().getParty() != null) {
            getOwner().getPacketSender().sendMessage("That player is currently in another party.");
            return;
        }
        if (System.currentTimeMillis()
                - getOwner().getMinigameAttributes().getRaidsAttributes().getLastInvitation() < 2000) {
            getOwner().getPacketSender().sendMessage("You must wait 2 seconds between each party invitation.");
            return;
        }
        if (p.busy()) {
            getOwner().getPacketSender().sendMessage("That player is currently busy.");
            return;
        }
        getOwner().getMinigameAttributes().getRaidsAttributes().setLastInvitation(System.currentTimeMillis());
        DialogueManager.start(p, new RaidsPartyInvitation(getOwner(), p));
        getOwner().getPacketSender().sendMessage("An invitation has been sent to " + p.getUsername() + ".");
    }


    public void duoSeasonal(Player p) {
        /*if (getOwner() == null || p == getOwner())
            return;
        if (hasEnteredDungeon) {
            getOwner().getPacketSender().sendMessage("You cannot invite anyone right now.");
            return;
        }
        if (getOwner().getGameMode() == GameMode.SEASONAL_IRONMAN && p.getGameMode() != GameMode.SEASONAL_IRONMAN) {
            getOwner().getPacketSender().sendMessage("Only Seasonals can party with other Seasonals.");
            return;
        }
        if (getOwner().getGameMode() != GameMode.SEASONAL_IRONMAN && p.getGameMode() == GameMode.SEASONAL_IRONMAN) {
            getOwner().getPacketSender().sendMessage("Only Seasonals can party with other Seasonals.");
            return;
        }*/

        if (player_members.size() >= 2) {
            getOwner().getPacketSender().sendMessage("Your party is full.");
            return;
        }
        if (p.getLocation() != Locations.Location.CRASH_ISLAND || p.isTeleporting()) {
            getOwner().getPacketSender().sendMessage("That player is not on Tutorial Island.");
            return;
        }
        if (player_members.contains(p)) {
            getOwner().getPacketSender().sendMessage("That player is already in your party.");
            return;
        }
        if (p.getMinigameAttributes().getRaidsAttributes().getParty() != null) {
            getOwner().getPacketSender().sendMessage("That player is currently in another party.");
            return;
        }
        if (System.currentTimeMillis()
                - getOwner().getMinigameAttributes().getRaidsAttributes().getLastInvitation() < 2000) {
            getOwner().getPacketSender().sendMessage("You must wait 2 seconds between each party invitation.");
            return;
        }
        if (p.busy()) {
            getOwner().getPacketSender().sendMessage("That player is currently busy.");
            return;
        }

        getOwner().getMinigameAttributes().getRaidsAttributes().setLastInvitation(System.currentTimeMillis());
        DialogueManager.start(p, new RaidsPartyInvitation(getOwner(), p));
        getOwner().getPacketSender().sendMessage("An invitation has been sent to " + p.getUsername() + ".");

    }


    public void add(Player p) {
        if (player_members.size() >= 5) {
            p.getPacketSender().sendMessage("That party is already full.");
            return;
        }
        if (hasEnteredDungeon) {
            p.getPacketSender().sendMessage("This party has already entered a dungeon.");
            return;
        }
        if ((p.getLocation() != Locations.Location.RAIDS_LOBBY && p.getLocation() != Locations.Location.CRASH_ISLAND) || p.isTeleporting()) {
            return;
        }
        sendMessage(p.getUsername() + " has joined the party.");
        p.getPacketSender().sendMessage("You've joined " + getOwner().getUsername() + "'s party.");
        player_members.add(p);
        p.getMinigameAttributes().getRaidsAttributes().setParty(this);
        p.getMinigameAttributes().getRaidsAttributes().setParty(owner.getMinigameAttributes().getRaidsAttributes().getParty());

    }

    public void remove(Player p, boolean reset, boolean fromParty) {

        if (fromParty) {
            player_members.remove(p);
            playersInRaids.remove(p);
            //TODO, why was this here?
            //p.moveTo(GameSettings.DEFAULT_POSITION);
            p.getCombatBuilder().reset(true);
        }
        p.getPacketSender().sendCameraNeutrality();
        p.getPacketSender().sendInterfaceRemoval();
        p.getPacketSender().sendString(65009, "Create");
        int id = 65016;
        for (int i = 65016; i < 65064; i++) {
            id++;
            p.getPacketSender().sendString(id++, "");
            p.getPacketSender().sendString(id++, "");
            p.getPacketSender().sendString(id++, "");
        }
        p.getPacketSender().sendString(65002, "Raiding Party: @whi@0");

        if (p == owner) {
            for (Player member : player_members) {
                p.getCombatBuilder().reset(true);
                if (member != null && member.getMinigameAttributes().getRaidsAttributes().getParty() != null
                        && member.getMinigameAttributes().getRaidsAttributes().getParty() == this) {
                    if (member == owner)
                        continue;
                    if (fromParty) {
                        member.getPacketSender().sendMessage("Your party has been deleted by the party's leader.");
                        remove(member, false, true);
                    } else {
                        remove(member, false, false);
                    }
                }
            }
            if (hasEnteredDungeon) {
                for (NPC npc : p.getMinigameAttributes().getRaidsAttributes().getParty().getNpcs()) {
                    if (npc != null && npc.getPosition().getZ() == p.getPosition().getZ())
                        World.deregister(npc);
                }

            }
        } else {
            if (fromParty) {
                sendMessage(p.getUsername() + " has left the party.");
                if (hasEnteredDungeon) {
                }
            }
            if (reset) {
                p.getPacketSender().sendTabInterface(GameSettings.QUESTS_TAB, 639);
                p.getPacketSender().sendTab(GameSettings.QUESTS_TAB);
            }
        }
        if (fromParty) {
            p.getMinigameAttributes().getRaidsAttributes().setParty(null);
        }
        p.getPacketSender().sendInterfaceRemoval();
        p.getMinigameAttributes().getRaidsAttributes().setInsideRaid(false);
        p.resetRaids(p);
    }

    public void sendMessage(String message) {
        for (Player member : getPlayers()) {
            if (member != null) {
                member.getPacketSender().sendMessage(message);
            }
        }
    }

    public int getInstanceLevel() {
        return instanceLevel;
    }

    public void setInstanceLevel(int level) {
        this.instanceLevel = level;
    }

    public void teleportGroup(Position location) {
        for (Player member : getPlayers()) {
            if (member != null) {
                TeleportHandler.teleportPlayer(member, location, member.getSpellbook().getTeleportType());
            }
        }
    }

    public void create() {
        if (owner.getMinigameAttributes().getRaidsAttributes().getParty() != null) {
            owner.getPacketSender().sendMessage("You are already in a Raids party.");
        } else {
            owner.getMinigameAttributes().getRaidsAttributes().setParty(new RaidsParty(owner));
            owner.getPacketSender().sendMessage("<col=660000>You've created a Raids party.");
        }
    }

    public void sendFrame(int frame, String string) {
        for (Player member : getPlayers()) {
            if (member != null) {
                member.getPacketSender().sendString(frame, string);
            }
        }
    }

    public void refreshInterface() {
        for (Player member : getPlayers()) {
            if (member != null) {
                member.getPacketSender().sendString(65009, "Invite");

                int start = 65016;
                for (int i = 0; i < getPlayers().size(); i++) {
                    start++;
                    member.getPacketSender().sendString(start++, getPlayers().get(i).getUsername());
                    member.getPacketSender().sendString(start++,
                            "" + getPlayers().get(i).getSkillManager().getTotalLevel());
                    member.getPacketSender().sendString(start++,
                            "" + getPlayers().get(i).getSkillManager().getCombatLevel());
                }

                for (int i = start; i < 65064; i++) {
                    start++;
                    member.getPacketSender().sendString(start++, "");
                    member.getPacketSender().sendString(start++, "");
                    member.getPacketSender().sendString(start++, "");
                }

                member.getPacketSender().sendString(65002, "Raiding Party: @whi@" + getPlayers().size());
            }
        }
    }

    public int getPlayersInRaidsLocation(RaidsParty party) {
        int inRaids = 0;
        for (Player member : party.getPlayers()) {
            /*if (member.getLocation().equals(Locations.Location.CHAMBERS_OF_XERIC)) {
                inRaids++;
            }*/
        }
        return inRaids;
    }

    public int getPlayersInRaidsDungeon(RaidsParty party) {
        int inRaids = 0;
        for (Player member : party.getPlayers()) {
            if (member != null && member.getMinigameAttributes().getRaidsAttributes().isInsideRaid() && GreatOlm.insideChamber(member)) {
                inRaids++;
            }
        }
        return inRaids;
    }

    public Player getOwner() {
        return owner;
    }

    public CopyOnWriteArrayList<Player> getPlayers() {
        return player_members;
    }

    public CopyOnWriteArrayList<Player> getPlayersInRaids() {
        return playersInRaids;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public CopyOnWriteArrayList<NPC> getNpcs() {
        return npc_members;
    }

}
