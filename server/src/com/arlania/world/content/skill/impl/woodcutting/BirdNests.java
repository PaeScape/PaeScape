package com.arlania.world.content.skill.impl.woodcutting;

import com.arlania.model.GameMode;
import com.arlania.model.GroundItem;
import com.arlania.model.Item;
import com.arlania.model.Skill;
import com.arlania.model.container.impl.Bank;
import com.arlania.util.RandomUtility;
import com.arlania.world.entity.impl.GroundItemManager;
import com.arlania.world.entity.impl.player.Player;


/**
 * @author Optimum
 * I do not give permission to
 * release this anywhere else
 */

public class BirdNests {

    /**
     * Ints.
     */

    //public static final int[] BIRD_NEST_IDS = {5070, 5071, 5072, 5073, 5074};
    public static final int[] BIRD_NEST_IDS = {5073, 5074};
    public static final int[] SEED_REWARDS = {5291, 5292, 5293, 5294, 5295, 5296, 5297, 5298, 5299, 5300, 5301, 5302, 5303, 5304};
    public static final int[] RING_REWARDS = {1635, 1692, 1654, 11069};
    public static final int EMPTY = 6693;
    //public static final int RED = 5076;
    //public static final int BLUE = 5077;
    //public static final int GREEN = 5078;
    public static final int AMOUNT = 1;


    /**
     * Check if the item is a nest
     */
    public static boolean isNest(final int itemId) {
        for (int nest : BIRD_NEST_IDS) {
            if (nest == itemId) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generates the random drop and creates a ground item
     * where the player is standing
     */

    public static void dropNest(Player p) {
		/*if(p.getPosition().getZ() > 0) {
			return;
		}*/
        if (RandomUtility.inclusiveRandom(10) == 1) {
            Item nest = null;
            int r = RandomUtility.inclusiveRandom(960);
            if (r >= 0 && r <= 640) {
                nest = new Item(5073);
            } else if (r >= 641 && r <= 960) {
                nest = new Item(5074);
            }
            if (nest != null) {
                nest.setAmount(1);
                if (p.getInventory().isFull())
                    GroundItemManager.spawnGroundItem(p, new GroundItem(nest, p.getPosition().copy(), p.getUsername(), false, 80, true, 80));
                else
                    p.getInventory().add(nest);
                p.getPacketSender().sendMessage("A bird's nest falls out of the tree!");
                p.getSkillManager().addExperience(Skill.WOODCUTTING, p.getSkillManager().getMaxLevel(Skill.WOODCUTTING) * 10);
            }
        }
    }

    /**
     * Searches the nest.
     */

    public static final void searchNest(Player p, int itemId) {
        if (p.getInventory().getFreeSlots() <= 0) {
            p.getPacketSender().sendMessage("You do not have enough free inventory slots to do this.");
            return;
        }
        p.getInventory().delete(itemId, 1);
        //eggNest(p, itemId);
        seedNest(p, itemId);
        ringNest(p, itemId);
        p.getInventory().add(EMPTY, AMOUNT);
    }

    /**
     * Determines what loot you get
     * from ring bird nests
     */
    public static final void ringNest(Player p, int itemId) {

        int loot = 995;

        if (itemId == 5074) {
            int random = RandomUtility.inclusiveRandom(1000);
            if (random >= 0 && random <= 250) {
                loot = RING_REWARDS[0];
            } else if (random >= 251 && random <= 500) {
                loot = RING_REWARDS[1];
            } else if (random >= 501 && random <= 750) {
                loot = RING_REWARDS[2];
            } else if (random >= 751) {
                loot = RING_REWARDS[3];
            }

            p.getInventory().add(loot, AMOUNT);

            if (p.looterBanking && p.getGameMode() != GameMode.ULTIMATE_IRONMAN)
                p.getInventory().switchItem(p.getBank(Bank.getTabForItem(p, loot)), new Item(loot, AMOUNT), p.getInventory().getSlot(loot), false, true);

        }
    }

    /**
     * Determines what loot you get
     * from seed bird nests
     */

    private static final void seedNest(Player p, int itemId) {

        int loot = 995;

        if (itemId == 5073 || itemId == 5074) {
            int random = RandomUtility.inclusiveRandom(1000);
            if (random >= 0 && random <= 220) {
                loot = SEED_REWARDS[0];
            } else if (random >= 221 && random <= 350) {
                loot = SEED_REWARDS[1];
            } else if (random >= 351 && random <= 400) {
                loot = SEED_REWARDS[2];
            } else if (random >= 401 && random <= 430) {
                loot = SEED_REWARDS[3];
            } else if (random >= 431 && random <= 440) {
                loot = SEED_REWARDS[4];
            } else if (random >= 441 && random <= 600) {
                loot = SEED_REWARDS[5];
            } else if (random >= 601 && random <= 700) {
                loot = SEED_REWARDS[6];
            } else if (random >= 701 && random <= 790) {
                loot = SEED_REWARDS[7];
            } else if (random >= 791 && random <= 850) {
                loot = SEED_REWARDS[8];
            } else if (random >= 851 && random <= 900) {
                loot = SEED_REWARDS[9];
            } else if (random >= 901 && random <= 930) {
                loot = SEED_REWARDS[10];
            } else if (random >= 931 && random <= 950) {
                loot = SEED_REWARDS[11];
            } else if (random >= 951 && random <= 970) {
                loot = SEED_REWARDS[12];
            } else if (random >= 971 && random <= 980) {
                loot = SEED_REWARDS[13];
            } else {
                p.getInventory().add(SEED_REWARDS[0], AMOUNT);
                loot = SEED_REWARDS[0];
            }

            p.getInventory().add(loot, AMOUNT);

            if (p.looterBanking && p.getGameMode() != GameMode.ULTIMATE_IRONMAN)
                p.getInventory().switchItem(p.getBank(Bank.getTabForItem(p, loot)), new Item(loot, AMOUNT), p.getInventory().getSlot(loot), false, true);

        }
    }

    /**
     *
     * Egg nests
     *
     */

	/*public static final void eggNest(Player p, int itemId){
		if(itemId == 5070){
			p.getInventory().add(RED, AMOUNT);
		}
		if(itemId == 5071){
			p.getInventory().add(GREEN, AMOUNT);
		}
		if(itemId == 5072){
			p.getInventory().add(BLUE, AMOUNT);
		}
	}*/

}