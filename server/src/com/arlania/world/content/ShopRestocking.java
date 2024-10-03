package com.arlania.world.content;

import com.arlania.model.container.impl.Shop;
import com.arlania.model.container.impl.Shop.ShopManager;
import com.arlania.world.World;

/**
 * Handles the task of restocking all the shops within the server.
 *
 * @author Andys1814.
 */
public class ShopRestocking {

    /**
     * Represents the timer, which will be used to determine when to restock the
     * shops.
     */
    private static final int TIMER = 80;

    /**
     * Represents the dynamically changing stock timer, which is defaulted to
     * the value of {@link #TIMER}.
     */
    private static int restockTimer = TIMER;

    /**
     * Processes the timer for shop restocking.
     */
    public static void sequence() {
        if (restockTimer > 0)
            restockTimer--;
        if (restockTimer <= 0) {
            restockTimer = TIMER;
            restock();
        }
    }

    /**
     * Handles the action of restocking all the shops in the server. This
     * function will automatically skip over shops that are already fully
     * stocked to avoid expensive operations.
     */
    private static void restock() {
        for (Shop shop : ShopManager.getShops().values()) {
            if (shop.fullyRestocked()) {
                continue;
            }
            shop.restockShop();
        }
        World.getPlayers().forEach(player -> player.sendMessage("All shops have been fully restocked!"));
    }

}
