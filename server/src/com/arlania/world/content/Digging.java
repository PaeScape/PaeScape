package com.arlania.world.content;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.Position;
import com.arlania.world.content.treasuretrails.CoordinateScrolls;
import com.arlania.world.content.treasuretrails.DiggingScrolls;
import com.arlania.world.content.treasuretrails.MapScrolls;
import com.arlania.world.entity.impl.player.Player;

public class Digging {

    public static void dig(final Player player) {
        if (!player.getClickDelay().elapsed(2000))
            return;
        player.getMovementQueue().reset();
        player.getPacketSender().sendMessage("You start digging..");
        player.performAnimation(new Animation(830));
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                /**
                 * Clue scrolls
                 */
                if (/*ClueScrolls.digSpot(player)*/DiggingScrolls.digClue(player) || MapScrolls.digClue(player) || CoordinateScrolls.digClue(player)) {
                    stop();
                    return;
                }
                Position targetPosition = null;

                /**
                 * Barrows
                 */
                if (inArea(player.getPosition(), 3553, 3301, 3561, 3294))
                    targetPosition = new Position(3578, 9706, -1);
                /** clue scrolls** x,y**/
                else if (inClue(player.getPosition(), 3096, 3496))
                    if (player.getInventory().contains(2677)) {
                        player.getInventory().delete(2677, 1);
                        player.getInventory().add(2714, 1);
                        ClueScrolls.incrementCluesCompleted(1);
                        player.getPacketSender().sendMessage("You found a casket, open it for your reward!");
                        stop();
                    } else {

                    }
                else if (inClue(player.getPosition(), 3028, 9741))
                    if (player.getInventory().contains(2678)) {
                        player.getInventory().delete(2678, 1);
                        player.getInventory().add(2714, 1);
                        ClueScrolls.incrementCluesCompleted(1);
                        player.getPacketSender().sendMessage("You found a casket, open it for your reward!");
                        stop();
                    } else {

                    }
                else if (inClue(player.getPosition(), 3366, 3267))
                    if (player.getInventory().contains(2679)) {
                        player.getInventory().delete(2679, 1);
                        player.getInventory().add(2714, 1);
                        ClueScrolls.incrementCluesCompleted(1);
                        player.getPacketSender().sendMessage("You found a casket, open it for your reward!");
                        stop();
                    } else {

                    }
                else if (inClue(player.getPosition(), 3145, 9915))
                    if (player.getInventory().contains(2680)) {
                        player.getInventory().delete(2680, 1);
                        player.getInventory().add(2714, 1);
                        ClueScrolls.incrementCluesCompleted(1);
                        player.getPacketSender().sendMessage("You found a casket, open it for your reward!");
                        stop();
                    } else {

                    }
                else if (inClue(player.getPosition(), 2341, 3698))
                    if (player.getInventory().contains(2681)) {
                        player.getInventory().delete(2681, 1);
                        player.getInventory().add(2714, 1);
                        ClueScrolls.incrementCluesCompleted(1);
                        player.getPacketSender().sendMessage("You found a casket, open it for your reward!");
                        stop();
                    } else {

                    }
                else if (inClue(player.getPosition(), 3451, 3717))
                    if (player.getInventory().contains(2682)) {
                        player.getInventory().delete(2682, 1);
                        player.getInventory().add(2714, 1);
                        ClueScrolls.incrementCluesCompleted(1);
                        player.getPacketSender().sendMessage("You found a casket, open it for your reward!");
                        stop();
                    } else {

                    }
                else if (inClue(player.getPosition(), 2280, 4697))
                    if (player.getInventory().contains(2683)) {
                        player.getInventory().delete(2683, 1);
                        player.getInventory().add(2714, 1);
                        ClueScrolls.incrementCluesCompleted(1);
                        player.getPacketSender().sendMessage("You found a casket, open it for your reward!");
                        stop();
                    } else {

                    }
                else if (inClue(player.getPosition(), 2660, 2651))
                    if (player.getInventory().contains(2684)) {
                        player.getInventory().delete(2684, 1);
                        player.getInventory().add(2714, 1);
                        ClueScrolls.incrementCluesCompleted(1);
                        player.getPacketSender().sendMessage("You found a casket, open it for your reward!");
                        stop();
                    } else {

                    }
                else if (inClue(player.getPosition(), 2441, 3096))
                    if (player.getInventory().contains(2685)) {
                        player.getInventory().delete(2685, 1);
                        player.getInventory().add(2714, 1);
                        ClueScrolls.incrementCluesCompleted(1);
                        player.getPacketSender().sendMessage("You found a casket, open it for your reward!");
                        stop();
                    } else {

                    }
                else if (inArea(player.getPosition(), 3550, 3287, 3557, 3278))
                    targetPosition = new Position(3568, 9683, -1);
                else if (inArea(player.getPosition(), 3561, 3292, 3568, 3285))
                    targetPosition = new Position(3557, 9703, -1);
                else if (inArea(player.getPosition(), 3570, 3302, 3579, 3293))
                    targetPosition = new Position(3556, 9718, -1);
                else if (inArea(player.getPosition(), 3571, 3285, 3582, 3278))
                    targetPosition = new Position(3534, 9704, -1);
                else if (inArea(player.getPosition(), 3562, 3279, 3569, 3273))
                    targetPosition = new Position(3546, 9684, -1);
                else if (inArea(player.getPosition(), 2986, 3370, 3013, 3388))
                    targetPosition = new Position(3546, 9684, -1);
                if (targetPosition != null)
                    player.moveTo(targetPosition);
                else
                    player.getPacketSender().sendMessage("You find nothing of interest.");
                targetPosition = null;
                stop();
            }
        });
        player.getClickDelay().reset();
    }

    private static boolean inArea(Position pos, int x, int y, int x1, int y1) {
        return pos.getX() > x && pos.getX() < x1 && pos.getY() < y && pos.getY() > y1;
    }

    private static boolean inClue(Position pos, int x, int y) {
        return pos.getX() == x && pos.getY() == y;
    }
}
