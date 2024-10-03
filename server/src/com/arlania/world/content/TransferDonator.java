package com.arlania.world.content;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.model.input.impl.EnterAmountOfTokensToTransfer;
import com.arlania.world.entity.impl.player.Player;

public class TransferDonator {

    public static void selectionInterface(Player player) {

        player.setInputHandling(new EnterAmountOfTokensToTransfer());
        player.getPacketSender().sendString(2799, ItemDefinition.forId(19864).getName()).sendInterfaceModel(1746, 19864, 150).sendChatboxInterface(4429);
        player.getPacketSender().sendString(2800, "How many tokens would you like to transfer?");
    }

    public static void transferTokens(final Player player, final int amount) {

        if (!player.getInventory().contains(19864))
            return;

        Player target = player.interactingPlayer;

        player.setCurrentTask(new Task(2, player, false) {

            @Override
            public void execute() {
                if (!player.getInventory().contains(19864)) {
                    stop();
                    return;
                }

                int transferAmount = amount;

                if (player.getInventory().getAmount(19864) < transferAmount)
                    transferAmount = player.getInventory().getAmount(19864);


                player.getInventory().delete(19864, transferAmount);
                target.getInventory().add(19864, transferAmount);
                player.getPacketSender().sendInterfaceRemoval();
                String eventLog = player.getUsername() + " gave " + transferAmount + " tokens to " + target.getUsername();
                PlayerLogs.log("Token Swap", eventLog);
                stop();
            }

            @Override
            public void stop() {
                setEventRunning(false);
                player.setSelectedSkillingItem(-1);
                player.performAnimation(new Animation(65535));
            }
        });
        TaskManager.submit(player.getCurrentTask());


    }

}
