package com.arlania.world.content.minigames;

import com.arlania.world.content.minigames.MinigameManager.AllowedType;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

/**
 * Represents a minigame
 *
 * @author Levi Patton
 * @www.rune-server.org/members/auguryps
 */
public interface Minigame {

    /**
     * Handles death
     */
    boolean handleDeath(Player player);

    /**
     * Handles teleport
     *
     * @return can teleport
     */
    boolean handleTeleport(Player player);

    /**
     * Handles logout
     *
     * @param logout sends logout action
     * @return can logout
     */
    boolean handleLogout(Player player, boolean logout);

    /**
     * Lose items on death
     *
     * @return lose items
     */
    boolean safeMinigame(Player player);

    /**
     * If players can fight in this minigame
     *
     * @param player the player
     * @return can fight
     */
    boolean canFight(Player player);

    /**
     * Killed the other npc
     *
     * @param player the player
     * @param npc    the npc
     */
    void killedNpc(Player player, NPC npc);

    /**
     * Killed the other player
     *
     * @param player      the player
     * @param otherPlayer the other player
     */
    void killedPlayer(Player player, Player otherPlayer);

    /**
     * Sends the interface
     *
     * @param player the player
     */
    void sendInterface(Player player);

    /**
     * Gets the allowed type
     *
     * @return the allowed type
     */
    AllowedType getAllowedType();
}
