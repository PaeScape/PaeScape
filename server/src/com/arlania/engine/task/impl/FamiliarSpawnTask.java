package com.arlania.engine.task.impl;


import com.arlania.engine.task.Task;
import com.arlania.model.Item;
import com.arlania.world.content.skill.impl.summoning.BossPets.BossPet;
import com.arlania.world.content.skill.impl.summoning.FamiliarData;
import com.arlania.world.entity.impl.player.Player;

/**
 * Familiar spawn on login
 *
 * @author Gabriel Hannason
 */
public class FamiliarSpawnTask extends Task {

    public FamiliarSpawnTask(Player player) {
        super(2, player, false);
        this.player = player;
    }

    private final Player player;
    public int familiarId;
    public int deathTimer;
    public Item[] validItems;

    public FamiliarSpawnTask setFamiliarId(int familiarId) {
        this.familiarId = familiarId;
        return this;
    }

    public FamiliarSpawnTask setDeathTimer(int deathTimer) {
        this.deathTimer = deathTimer;
        return this;
    }

    public FamiliarSpawnTask setValidItems(Item[] validItems) {
        this.validItems = validItems;
        return this;
    }

    @Override
    public void execute() {
        if (player == null || !player.isRegistered()) {
            stop();
            return;
        }
        if (player.getInterfaceId() > 0) {
            player.getPacketSender().sendInterfaceRemoval();
        }
        if (BossPet.forSpawnId(familiarId) != null) {
            player.getSummoning().summonPet(BossPet.forSpawnId(familiarId), true);
        } else {
            player.getSummoning().summon(FamiliarData.forNPCId(familiarId), false, true);
            if (player.getSummoning().getFamiliar() != null && deathTimer > 0)
                player.getSummoning().getFamiliar().setDeathTimer(deathTimer);
            if (this.validItems != null && player.getSummoning().getBeastOfBurden() != null) {
                player.getSummoning().getBeastOfBurden().resetItems();
                for (Item item : validItems) {
                    if (item != null) {
                        player.getSummoning().getBeastOfBurden().add(item, false);
                    }
                }
            }
        }
        stop();
    }
}
