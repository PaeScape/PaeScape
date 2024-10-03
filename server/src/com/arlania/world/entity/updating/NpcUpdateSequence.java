package com.arlania.world.entity.updating;

import com.arlania.GameServer;
import com.arlania.world.World;
import com.arlania.world.entity.impl.npc.NPC;

import java.util.logging.Level;

/**
 * A {@link WorldUpdateSequence} implementation for {@link Npc}s that provides
 * code for each of the updating stages. The actual updating stage is not
 * supported by this implementation because npc's are updated for players.
 *
 * @author lare96
 */
public class NpcUpdateSequence implements UpdateSequence<NPC> {

    @Override
    public void executePreUpdate(NPC t) {
        try {
            t.getMovementCoordinator().sequence();
            t.getMovementQueue().sequence();
            t.sequence();
        } catch (Exception e) {
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
            World.deregister(t);
        }
    }

    @Override
    public void executeUpdate(NPC t) {
        throw new UnsupportedOperationException(
                "NPCs cannot be updated for NPCs!");
    }

    @Override
    public void executePostUpdate(NPC t) {
        try {
            NPCUpdating.resetFlags(t);
        } catch (Exception e) {
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
            World.deregister(t);
        }
    }
}