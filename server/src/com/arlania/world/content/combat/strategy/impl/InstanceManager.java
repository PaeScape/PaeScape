package com.arlania.world.content.combat.strategy.impl;


import com.arlania.engine.task.TaskManager;
import com.arlania.world.content.combat.strategy.BossInstance;
import com.arlania.world.entity.impl.player.Player;

import java.util.AbstractCollection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class InstanceManager<E extends BossInstance> extends AbstractCollection<E> {

    private static final InstanceManager<?> INSTANCE = new InstanceManager<>(2048);

    private static final int BASE_OFFSET = 1;

    private final BossInstance[] instances;

    private final Set<Integer> indices;

    private final int pointer = 0;

    public InstanceManager(int capacity) {
        this.instances = new BossInstance[capacity];
        this.indices = new HashSet<>();
    }

    @Override
    public Iterator<E> iterator() {
        return new InstanceIterator<E>(instances, indices, this);
    }

    @Override
    public int size() {
        int size = 0;
        for (BossInstance instance : instances) {
            if (instance != null)
                size++;
        }
        return size;
    }

    @Override
    public boolean add(BossInstance instance) {
        int slot = getFreeSlot();
        if (slot == -1) {
            return false;
        }
        instances[slot] = instance;
        instance.setPlane(slot * 4);
        TaskManager.submit(instance);
        return true;
    }

    private int getFreeSlot() {
        for (int i = BASE_OFFSET; i < instances.length; i++) {
            if (instances[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public Set<Integer> getIndices() {
        return indices;
    }

    public static InstanceManager<?> get() {
        return INSTANCE;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = BASE_OFFSET; i < instances.length; i++) {
            if (instances[i] == null) {
                continue;
            }
            if (instances[i] == o) {
                instances[i].stop();
                instances[i] = null;
                return true;
            }
        }
        return false;
    }

    public <T> T getInstance(Player player) {
        for (int i = BASE_OFFSET; i < instances.length; i++) {
            if (instances[i] == null) {
                continue;
            }
            if (instances[i].getPlayer() == player) {
                return (T) instances[i];
            }
        }
        return null;
    }

    public void prepare(Player asPlayer) {


    }
}
