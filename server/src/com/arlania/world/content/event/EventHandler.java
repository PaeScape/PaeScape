package com.arlania.world.content.event;

import com.arlania.world.World;
import com.google.common.base.Preconditions;

import java.util.HashSet;
import java.util.Set;

/**
 * Handles all the event that may happen within the server.
 *
 * @author Andys1814.
 */
public class EventHandler {

    /**
     * Represents all the events that are currently enabled.
     */
    private final Set<ServerEvent> events = new HashSet<>();

    /**
     * Sets the specified {@link ServerEvent} to the specified true/false value.
     *
     * @param event The event to set.
     */
    public void bindEvent(ServerEvent event) {
        Preconditions.checkNotNull(event, "Events are not permitted to hold null values.");

        World.getPlayers().forEach(player -> {
            player.getPacketSender().sendMessage("<img=10> <col=008FB2>The server is now enjoying "
                    + event.getType().toString() + ", thanks to the staff team!");
        });

        events.add(event);

    }

    /**
     * Binds the paramaterized {@link ServerEvent}.
     *
     * @param event the event to bind in {@link #events}.
     */
    public void unbindEvent(ServerEvent event) {
        events.remove(event);
    }

    /**
     * Returns the contents of {@link #events}.
     *
     * @return the contents of {@link #events}.
     */
    public Set<ServerEvent> getCurrentEvents() {
        return events;
    }

    /**
     * Returns whether or not the specified {@link ServerEvent} is enabled.
     *
     * @param event the event that we are checking
     * @return <true> if the specified event is enabled, <false> otherwise.
     */
    public boolean isEnabled(ServerEvent event) {
        return events.contains(event);
    }

}
