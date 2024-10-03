package com.arlania.world.content.combat.strategy.impl;


import com.arlania.world.content.combat.strategy.BossInstance;

import java.util.Iterator;
import java.util.Set;

/**
 * Iterator for collection.
 *
 * @param <T> player.
 * @author Adil
 * @since 01/13/2019
 */
public class InstanceIterator<T extends BossInstance> implements Iterator<T> {

    /**
     * The current instances to iterate through.
     */
    private final Object[] instances;

    /**
     * The container.
     */
    private final InstanceManager<T> container;

    private final Integer[] indicies;

    /**
     * Element pointers.
     */
    private int last;

    public InstanceIterator(BossInstance[] instances, Set<Integer> indicies, InstanceManager<T> container) {
        this.instances = instances;
        this.indicies = indicies.toArray(new Integer[indicies.size()]);
        this.container = container;
    }

    @Override
    public boolean hasNext() {
        return indicies.length != last;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T next() {
        Object temp = instances[indicies[last]];
        last++;
        return (T) temp;
    }

    @Override
    public void remove() {
        if (last >= 1) {
            container.remove(indicies[last - 1]);
        }
    }


}