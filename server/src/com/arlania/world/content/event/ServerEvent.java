package com.arlania.world.content.event;

/**
 * Represents a single event that the server may experience.
 *
 * @author Andys1814.
 */
public class ServerEvent {

    /**
     * The type of event.
     */
    private final EventType type;

    /**
     * Whether or not the event is enabled or disabled.
     */
    private final int duration;

    public ServerEvent(EventType type, int duration) {
        this.type = type;
        this.duration = duration;
    }

    /**
     * Returns the type of this event.
     *
     * @return the type.
     */
    public EventType getType() {
        return type;
    }

    /**
     * Returns the duration of this {@link ServerEvent}.
     *
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

}
