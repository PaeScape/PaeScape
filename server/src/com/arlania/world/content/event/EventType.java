package com.arlania.world.content.event;

/**
 * Represents a single server event type.
 *
 * @author Andys1814.
 */
public enum EventType {

    DOUBLE_PEST_CONTROL_POINTS, DOUBLE_PK_POINTS;

    @Override
    public String toString() {
        return this.name().toLowerCase().replaceAll("_", " ");
    }
}
