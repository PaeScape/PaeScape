package com.arlania.world.content.minigames.impl.barrows;

import com.arlania.engine.task.TaskManager;
import com.arlania.engine.task.impl.CeilingCollapseTask;
import com.arlania.model.*;
import com.arlania.model.Locations.Location;
import com.arlania.model.RegionInstance.RegionInstanceType;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.clog.CollectionLog.CustomCollection;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

/**
 * Handles the Barrows minigame and it's objects, npcs, etc.
 *
 * @editor Gabbe
 */
public class Minigame {

    public static void handleLogin(Player player) {
        updateInterface(player);
    }

    /**
     * Handles all objects in the Barrows minigame: Coffins, doors, etc.
     *
     * @param player The player calling this method
     * @param object The object the player is requesting
     */
    public static boolean handleObject(final Player player, GameObject object) {

        if (player.getGameMode() == GameMode.SEASONAL_IRONMAN) {
            if (player.seasonalMinigameTeleports[0] == 0) {
                return false;
            }
        }

        switch (object.getId()) {
            case 320720:
                searchCoffin(player, object.getId(), 4, 2026, object.getPosition() != null ? new Position(3557, 9715, player.getPosition().getZ()) : new Position(3552, 9693));
                return true;
            case 320772:
                searchCoffin(player, object.getId(), 0, 2030, object.getPosition() != null ? new Position(3575, 9704, player.getPosition().getZ()) : new Position(3552, 9693));
                return true;
            case 320770:
                searchCoffin(player, object.getId(), 5, 2025, object.getPosition() != null ? new Position(3557, 9699, player.getPosition().getZ()) : new Position(3552, 9693));
                return true;
            case 320721:
                searchCoffin(player, object.getId(), 1, 2029, object.getPosition() != null ? new Position(3571, 9684, player.getPosition().getZ()) : new Position(3552, 9693));
                return true;
            case 320771:
                searchCoffin(player, object.getId(), 2, 2028, object.getPosition() != null ? new Position(3549, 9681, player.getPosition().getZ()) : new Position(3552, 9693));
                return true;
            case 320722:
                searchCoffin(player, object.getId(), 3, 2027, object.getPosition() != null ? new Position(3537, 9703, player.getPosition().getZ()) : new Position(3552, 9693));
                return true;
            case 6745:
                if (object.getPosition().getX() == 3535 && object.getPosition().getY() == 9684) {
                    player.moveTo(new Position(3535, 9689));
                    return true;
                } else if (object.getPosition().getX() == 3534 && object.getPosition().getY() == 9688) {
                    player.moveTo(new Position(3534, 9683));
                    return true;
                }
                break;
            case 6726:
                if (object.getPosition().getX() == 3535 && object.getPosition().getY() == 9688) {
                    player.moveTo(new Position(3535, 9683));
                    return true;
                } else if (object.getPosition().getX() == 3534 && object.getPosition().getY() == 9684) {
                    player.moveTo(new Position(3534, 9689));
                    return true;
                }
                break;
            case 6737:
                if (object.getPosition().getX() == 3535 && object.getPosition().getY() == 9701) {
                    player.moveTo(new Position(3535, 9706));
                    return true;
                } else if (object.getPosition().getX() == 3534 && object.getPosition().getY() == 9705) {
                    player.moveTo(new Position(3534, 9700));
                    return true;
                }
                break;
            case 6718:
                if (object.getPosition().getX() == 3534 && object.getPosition().getY() == 9701) {
                    player.moveTo(new Position(3534, 9706));
                    return true;
                } else if (object.getPosition().getX() == 3535 && object.getPosition().getY() == 9705) {
                    player.moveTo(new Position(3535, 9700));
                    return true;
                }
                break;
            case 6719:
                if (object.getPosition().getX() == 3541 && object.getPosition().getY() == 9712) {
                    player.moveTo(new Position(3546, 9712));
                    return true;
                } else if (object.getPosition().getX() == 3545 && object.getPosition().getY() == 9711) {
                    player.moveTo(new Position(3540, 9711));
                    return true;
                }
                break;
            case 6738:
                if (object.getPosition().getX() == 3541 && object.getPosition().getY() == 9711) {
                    player.moveTo(new Position(3546, 9711));
                    return true;
                } else if (object.getPosition().getX() == 3545 && object.getPosition().getY() == 9712) {
                    player.moveTo(new Position(3540, 9712));
                    return true;
                }
                break;
            case 6740:
                if (object.getPosition().getX() == 3558 && object.getPosition().getY() == 9711) {
                    player.moveTo(new Position(3563, 9711));
                    return true;
                } else if (object.getPosition().getX() == 3562 && object.getPosition().getY() == 9712) {
                    player.moveTo(new Position(3557, 9712));
                    return true;
                }
                break;
            case 6721:
                if (object.getPosition().getX() == 3558 && object.getPosition().getY() == 9712) {
                    player.moveTo(new Position(3563, 9712));
                    return true;
                } else if (object.getPosition().getX() == 3562 && object.getPosition().getY() == 9711) {
                    player.moveTo(new Position(3557, 9711));
                    return true;
                }
                break;
            case 6741:
                if (object.getPosition().getX() == 3568 && object.getPosition().getY() == 9705) {
                    player.moveTo(new Position(3568, 9700));
                    return true;
                } else if (object.getPosition().getX() == 3569 && object.getPosition().getY() == 9701) {
                    player.moveTo(new Position(3569, 9706));
                    return true;
                }
                break;
            case 6722:
                if (object.getPosition().getX() == 3569 && object.getPosition().getY() == 9705) {
                    player.moveTo(new Position(3569, 9700));
                    return true;
                } else if (object.getPosition().getX() == 3568 && object.getPosition().getY() == 9701) {
                    player.moveTo(new Position(3568, 9706));
                    return true;
                }
                break;
            case 6747:
                if (object.getPosition().getX() == 3568 && object.getPosition().getY() == 9688) {
                    player.moveTo(new Position(3568, 9683));
                    return true;
                } else if (object.getPosition().getX() == 3569 && object.getPosition().getY() == 9684) {
                    player.moveTo(new Position(3569, 9689));
                    return true;
                }
                break;
            case 6728:
                if (object.getPosition().getX() == 3569 && object.getPosition().getY() == 9688) {
                    player.moveTo(new Position(3569, 9683));
                    return true;
                } else if (object.getPosition().getX() == 3568 && object.getPosition().getY() == 9684) {
                    player.moveTo(new Position(3568, 9689));
                    return true;
                }
                break;
            case 6749:
                if (object.getPosition().getX() == 3562 && object.getPosition().getY() == 9678) {
                    player.moveTo(new Position(3557, 9678));
                    return true;
                } else if (object.getPosition().getX() == 3558 && object.getPosition().getY() == 9677) {
                    player.moveTo(new Position(3563, 9677));
                    return true;
                }
                break;
            case 6730:
                if (object.getPosition().getX() == 3562 && object.getPosition().getY() == 9677) {
                    player.moveTo(new Position(3557, 9677));
                    return true;
                } else if (object.getPosition().getX() == 3558 && object.getPosition().getY() == 9678) {
                    player.moveTo(new Position(3563, 9678));
                    return true;
                }
                break;
            case 6748:
                if (object.getPosition().getX() == 3545 && object.getPosition().getY() == 9678) {
                    player.moveTo(new Position(3540, 9678));
                    return true;
                } else if (object.getPosition().getX() == 3541 && object.getPosition().getY() == 9677) {
                    player.moveTo(new Position(3546, 9677));
                    return true;
                }
                break;
            case 6729:
                if (object.getPosition().getX() == 3545 && object.getPosition().getY() == 9677) {
                    player.moveTo(new Position(3540, 9677));
                    return true;
                } else if (object.getPosition().getX() == 3541 && object.getPosition().getY() == 9678) {
                    player.moveTo(new Position(3546, 9678));
                    return true;
                }
                break;
            case 320973:
                if (player.getMinigameAttributes().getBarrowsMinigameAttributes().getKillcount() < 5)
                    return true;
                if (player.getMinigameAttributes().getBarrowsMinigameAttributes().getBarrowsData()[player.getMinigameAttributes().getBarrowsMinigameAttributes().getRandomCoffin()][1] == 0) {
                    handleObject(player, new GameObject(COFFIN_AND_BROTHERS[player.getMinigameAttributes().getBarrowsMinigameAttributes().getRandomCoffin()][0], null));
                    player.getMinigameAttributes().getBarrowsMinigameAttributes().getBarrowsData()[player.getMinigameAttributes().getBarrowsMinigameAttributes().getRandomCoffin()][1] = 1;
                    return true;
                } else if (player.getMinigameAttributes().getBarrowsMinigameAttributes().getBarrowsData()[player.getMinigameAttributes().getBarrowsMinigameAttributes().getRandomCoffin()][1] == 1) {
                    player.getPacketSender().sendMessage("You cannot loot this whilst in combat!");
                    return true;
                } else if (player.getMinigameAttributes().getBarrowsMinigameAttributes().getBarrowsData()[player.getMinigameAttributes().getBarrowsMinigameAttributes().getRandomCoffin()][1] == 2 && player.getMinigameAttributes().getBarrowsMinigameAttributes().getKillcount() >= 6) {
                    if (player.getInventory().getFreeSlots() < 6) {
                        player.getPacketSender().sendMessage("You need at least 6 free inventory slots to loot this chest.");
                        return true;
                    }
                    resetBarrows(player);
                    player.setCompletedBarrows(player.getCompletedBarrows() + 1);
                    int dungxp = 150;
                    player.getSkillManager().addExperience(Skill.DUNGEONEERING, dungxp);
                    //player.setRaidPoints(player.getRaidPoints() +  10);

                    int loot = 0; //Barrows normal

                    if (player.getCompletedBarrows() >= 100)
                        loot = RandomUtility.inclusiveRandom(1, 6); //Barrows normal
                    else
                        loot = RandomUtility.inclusiveRandom(1, 8); //Barrows normal

                    /*player.getAchievementTracker().progress(AchievementData.COMPLETE_100_BARROWS, 1);
                    player.getAchievementTracker().progress(AchievementData.COMPLETE_500_BARROWS, 1);
                    player.getAchievementTracker().progress(AchievementData.COMPLETE_1000_BARROWS, 1);*/

                    if (loot == 1) {
                        int b = randomBarrows();
                        player.getInventory().add(b, 1);
                        player.getCollectionLog().handleDrop(CustomCollection.BARROWS.getId(), b, 1);
                        World.sendMessage("drops", "@blu@[BARROWS] @bla@" + player.getUsername() + " has just received " + ItemDefinition.forId(b).getName() + " from Barrows Chest #" + player.getCompletedBarrows() + "!");
                    }

                    if (player.getSkiller().getSkillerTask().getType() == "barrowsmini") {
                        player.getSkiller().handleSkillerTaskGather(true);
                        player.getSkillManager().addExperience(Skill.SKILLER, player.getSkiller().getSkillerTask().getXP());
                    }

                    if (RandomUtility.inclusiveRandom(1, 25) == 1)
                        player.getInventory().add(985, 1);
                    if (RandomUtility.inclusiveRandom(1, 25) == 2)
                        player.getInventory().add(987, 1);
                    if (RandomUtility.inclusiveRandom(1, 1500) == 1)
                        player.getInventory().add(20104, 1);

                    if (player.getCompletedBarrows() >= 100) {
                        player.getInventory().add(558, 300 + RandomUtility.inclusiveRandom(100));
                        player.getInventory().add(562, 200 + RandomUtility.inclusiveRandom(100));
                        player.getInventory().add(560, 100 + RandomUtility.inclusiveRandom(100));
                        player.getInventory().add(565, 40 + RandomUtility.inclusiveRandom(100));
                        player.getInventory().add(4740, 100 + RandomUtility.inclusiveRandom(100));
                    } else {
                        player.getInventory().add(558, 150 + RandomUtility.inclusiveRandom(50));
                        player.getInventory().add(562, 100 + RandomUtility.inclusiveRandom(50));
                        player.getInventory().add(560, 50 + RandomUtility.inclusiveRandom(50));
                        player.getInventory().add(565, 20 + RandomUtility.inclusiveRandom(50));
                        player.getInventory().add(4740, 50 + RandomUtility.inclusiveRandom(50));
                    }


                    player.setPaePoints(player.getPaePoints() + 6);
                    player.getPacketSender().sendMessage("You now have " + player.getPaePoints() + " Paepoints!");


                    player.getPacketSender().sendCameraShake(3, 2, 3, 2);
                    player.getPacketSender().sendMessage("The cave begins to collapse!");
                    TaskManager.submit(new CeilingCollapseTask(player));
                }
                break;

        }
        return false;
    }


    /**
     * Handles coffin searching
     *
     * @param player                                 Player searching a coffin
     * @param obj                                    The object (coffin) being searched
     * @param coffinId                               The coffin's array index
     * @param npcId                                  The NPC Id of the NPC to spawn after searching
     * @param constitution                           NPC stat
     * @param attackLevel                            NPC stat
     * @param strengthLevel                          NPC stat
     * @param defenceLevel                           NPC stat
     * @param absorbMelee                            NPC stat
     * @param absorbRanged                           NPC stat
     * @param absorbMagic                            NPC stat
     * @param getCombatAttributes().getAttackDelay() NPC attackspeed
     * @param maxhit                                 NPC Maxhit
     */
    public static void searchCoffin(final Player player, final int obj, final int coffinId, int npcId, Position spawnPos) {
        player.getPacketSender().sendInterfaceRemoval();
        if (player.getPosition().getZ() == -1) {
            if (selectCoffin(player, obj))
                return;
        }
        if (player.getMinigameAttributes().getBarrowsMinigameAttributes().getBarrowsData()[coffinId][1] == 0) {
            if (player.getLocation() == Location.BARROWS) {
                player.setRegionInstance(new RegionInstance(player, RegionInstanceType.BARROWS));
                NPC npc_ = new NPC(npcId, spawnPos);
                npc_.forceChat(player.getPosition().getZ() == -1 ? "You dare disturb my rest!" : "You dare steal from us!");
                npc_.getCombatBuilder().setAttackTimer(3);
                npc_.setSpawnedFor(player);
                npc_.getCombatBuilder().attack(player);
                World.register(npc_);
                player.getRegionInstance().getNpcsList().add(npc_);
                player.getMinigameAttributes().getBarrowsMinigameAttributes().getBarrowsData()[coffinId][1] = 1;
            }
        } else {
            player.getPacketSender().sendMessage("You have already searched this sarcophagus.");
        }
    }

    public static void resetBarrows(Player player) {
        player.getMinigameAttributes().getBarrowsMinigameAttributes().setKillcount(0);
        for (int i = 0; i < player.getMinigameAttributes().getBarrowsMinigameAttributes().getBarrowsData().length; i++)
            player.getMinigameAttributes().getBarrowsMinigameAttributes().getBarrowsData()[i][1] = 0;
        updateInterface(player);
        player.getMinigameAttributes().getBarrowsMinigameAttributes().setRandomCoffin(getRandomCoffin());
    }

    public static final Object[][] data = {
            {"Verac The Defiled", 37203},
            {"Torag The Corrupted", 37205},
            {"Karil The Tainted", 37207},
            {"Guthan The Infested", 37206},
            {"Dharok The Wretched", 37202},
            {"Ahrim The Blighted", 37204}
    };

    /**
     * Deregisters an NPC located in the Barrows minigame
     *
     * @param player        The player that's the reason for deregister
     * @param barrowBrother The NPC to deregister
     * @param killed        Did player kill the NPC?
     */
    public static void killBarrowsNpc(Player player, NPC n, boolean killed) {
        if (player == null || n == null)
            return;
        if (player.getLocation() == Location.GRAVEYARD)
            return;

        if (n.getId() == 58) {
            player.getMinigameAttributes().getBarrowsMinigameAttributes().setKillcount(player.getMinigameAttributes().getBarrowsMinigameAttributes().getKillcount() + 1);
            updateInterface(player);
            return;
        }
        int arrayIndex = getBarrowsIndex(player, n.getId());
        if (arrayIndex < 0)
            return;
        if (killed && player.getRegionInstance() != null) {
            player.getMinigameAttributes().getBarrowsMinigameAttributes().getBarrowsData()[arrayIndex][1] = 2;
            player.getMinigameAttributes().getBarrowsMinigameAttributes().setKillcount(player.getMinigameAttributes().getBarrowsMinigameAttributes().getKillcount() + 1);
            player.getRegionInstance().getNpcsList().remove(player);
        } else if (arrayIndex >= 0)
            player.getMinigameAttributes().getBarrowsMinigameAttributes().getBarrowsData()[arrayIndex][1] = 0;

        if ((n.getId() == 2025) && (player.getMinigameAttributes().getBarrowsMinigameAttributes().getKillcount() < 6))
            player.moveTo(new Position(3565, 3289, 0));
        if ((n.getId() == 2026) && (player.getMinigameAttributes().getBarrowsMinigameAttributes().getKillcount() < 6))
            player.moveTo(new Position(3575, 3297, 0));
        if ((n.getId() == 2027) && (player.getMinigameAttributes().getBarrowsMinigameAttributes().getKillcount() < 6))
            player.moveTo(new Position(3577, 3282, 0));
        if ((n.getId() == 2028) && (player.getMinigameAttributes().getBarrowsMinigameAttributes().getKillcount() < 6))
            player.moveTo(new Position(3566, 3276, 0));
        if ((n.getId() == 2029) && (player.getMinigameAttributes().getBarrowsMinigameAttributes().getKillcount() < 6))
            player.moveTo(new Position(3554, 3285, 0));
        if ((n.getId() == 2030) && (player.getMinigameAttributes().getBarrowsMinigameAttributes().getKillcount() < 6))
            player.moveTo(new Position(3556, 3298, 0));
        updateInterface(player);
    }

    /**
     * Selects the coffin and shows the interface if coffin id matches random
     * coffin
     **/
    public static boolean selectCoffin(Player player, int coffinId) {
        if (player.getMinigameAttributes().getBarrowsMinigameAttributes().getRandomCoffin() == 0)
            player.getMinigameAttributes().getBarrowsMinigameAttributes().setRandomCoffin(getRandomCoffin());
        if (COFFIN_AND_BROTHERS[player.getMinigameAttributes().getBarrowsMinigameAttributes().getRandomCoffin()][0] == coffinId) {
            DialogueManager.start(player, 27);
            player.setDialogueActionId(16);
            return true;
        }
        return false;
    }

    public static int getBarrowsIndex(Player player, int id) {
        int index = -1;
        for (int i = 0; i < player.getMinigameAttributes().getBarrowsMinigameAttributes().getBarrowsData().length; i++) {
            if (player.getMinigameAttributes().getBarrowsMinigameAttributes().getBarrowsData()[i][0] == id) {
                index = i;
            }
        }
        return index;
    }

    public static void updateInterface(Player player) {
        for (int i = 0; i < data.length; i++) {
            boolean killed = player.getMinigameAttributes().getBarrowsMinigameAttributes().getBarrowsData()[i][1] == 2;
            String s = killed ? "@gre@" : "@red@";
            player.getPacketSender().sendString((int) data[i][1], s + data[i][0]);
        }
        player.getPacketSender().sendString(37208, "Killcount: " + player.getMinigameAttributes().getBarrowsMinigameAttributes().getKillcount());
    }

    public static void fixBarrows(Player player) {
        player.getPacketSender().sendInterfaceRemoval();
        int totalCost = 0;
        int money = player.getInventory().getAmount(995);
        boolean breakLoop = false;
        for (Item items : player.getInventory().getItems()) {
            if (items == null)
                continue;
            for (int i = 0; i < brokenBarrows.length; i++) {
                if (player.getInventory().getSlot(items.getId()) > 0) {
                    if (items.getId() == brokenBarrows[i][1]) {
                        if (totalCost + 45000 > money) {
                            breakLoop = true;
                            player.getPacketSender().sendMessage("You need at least 45000 coins to fix this item.");
                            break;
                        } else {
                            totalCost += 45000;
                            player.getInventory().setItem(player.getInventory().getSlot(items.getId()), new Item(brokenBarrows[i][0], 1));
                            player.getInventory().refreshItems();
                        }
                    }
                }
            }
            if (breakLoop)
                break;
        }
        if (totalCost > 0)
            player.getInventory().delete(995, totalCost);
    }

    public static int[] runes = {4740, 558, 560, 565};

    public static int[] barrows = {4708, 4710, 4712, 4714, 4716, 4718, 4720, 4722, 4724, 4726, 4728, 4730, 4732, 4734, 4736, 4738,
            4745, 4747, 4749, 4751, 4753, 4755, 4757, 4759, 18845};

    public static final int[][] brokenBarrows = {{4708, 4860}, {4710, 4866},
            {4712, 4872}, {4714, 4878}, {4716, 4884}, {4720, 4896},
            {4718, 4890}, {4720, 4896}, {4722, 4902}, {4732, 4932},
            {4734, 4938}, {4736, 4944}, {4738, 4950}, {4724, 4908},
            {4726, 4914}, {4728, 4920}, {4730, 4926}, {4745, 4956},
            {4747, 4926}, {4749, 4968}, {4751, 4994}, {4753, 4980},
            {4755, 4986}, {4757, 4992}, {4759, 4998}};

    public static final int[][] COFFIN_AND_BROTHERS = {{320772, 2030},
            {320721, 2029}, {320771, 2028}, {320722, 2027}, {320720, 2026},
            {320770, 2025}
    };

    public static boolean isBarrowsNPC(int id) {
        for (int i = 0; i < COFFIN_AND_BROTHERS.length; i++) {
            if (COFFIN_AND_BROTHERS[i][1] == id)
                return true;
        }
        return false;
    }

    public static final Position[] UNDERGROUND_SPAWNS = {
            new Position(3569, 9677),
            new Position(3535, 9677),
            new Position(3534, 9711),
            new Position(3569, 9712)
    };

    public static int getRandomCoffin() {
        return RandomUtility.inclusiveRandom(COFFIN_AND_BROTHERS.length - 1);
    }

    public static int randomRunes() {
        return runes[(int) (Math.random() * runes.length)];
    }

    public static int randomBarrows() {
        return barrows[(int) (Math.random() * barrows.length)];
    }

}
