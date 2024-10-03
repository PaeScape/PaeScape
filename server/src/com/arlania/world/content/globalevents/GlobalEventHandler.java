package com.arlania.world.content.globalevents;

import com.arlania.DiscordBot;
import com.arlania.GameServer;
import com.arlania.GameSettings;
import com.arlania.world.World;
import com.arlania.world.entity.impl.player.Player;
import org.javacord.api.entity.message.MessageBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GlobalEventHandler {

    public static int globalEventTimeRemaining = 0;
    private static final List<GlobalEvent> activeGlobalEvents = new ArrayList<>();
    public static int totalGlobalBossKills = 0;

    public static void sequence() {

        if (globalEventTimeRemaining < 1) {
            endGlobalEvent();
        }

        globalEventTimeRemaining--;
    }

    public static void useEventToken(Player player, int itemID) {
        if (GlobalEventHandler.globalEventTimeRemaining > 0 || !activeGlobalEvents.isEmpty()) {
            player.getPacketSender().sendMessage("@red@There is already a Global Event active!");
            return;
        }

        if (GlobalEvent.forId(itemID) == null) {
            player.getPacketSender().sendMessage("@red@Your event token is not valid.");
            return;
        }

        player.getInventory().delete(itemID, 1);

        startTokenGlobalEvent(player, GlobalEvent.forId(itemID));
    }


    public static void startTokenGlobalEvent(Player player, GlobalEvent event) {
        globalEventTimeRemaining = 15000;
        activeGlobalEvents.add(event);
        listAllActiveEffects(player);

        String eventLog = player.getUsername() + " activated Global " + event.getEventName() + " Event!";

        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
            new MessageBuilder().append("<@&911737660559736873> " + eventLog).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.EVENTS_CH).get());

    }

    public static void useTokenOnWell(Player player, int numberOfEventsToTrigger, int itemID) {

        int activeEffects = activeGlobalEvents.stream().map(GlobalEvent::getEffects).mapToInt(List::size).sum();

        if (activeEffects + numberOfEventsToTrigger > 3) {
            player.getPacketSender().sendMessage("@red@There are " + activeEffects + " global effects active.");
            player.getPacketSender().sendMessage("@red@You attempted to use an event that would go over the limit of 3 effects.");
            return;
        }

        if (!player.getInventory().contains(itemID)) {
            player.getPacketSender().sendMessage("@red@Something went wrong with your donation.");
            return;
        }

        player.getInventory().delete(itemID, 1);

        for (int i = 0; i < numberOfEventsToTrigger; i++) {
            addRandomSingleEvent(player);
        }
    }

    public static void addRandomSingleEvent(Player player) {
        // list of current active effects derived from current active global events
        List<GlobalEvent.Effect> activeEffects = activeGlobalEvents.stream().map(GlobalEvent::getEffects)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        if (activeEffects.size() >= 3) {
            player.getPacketSender().sendMessage("@red@There are " + activeEffects + " global effects active.");
            player.getPacketSender().sendMessage("@red@You attempted to use an event that would go over the limit of 3 effects.");
            return;
        }

        // list of single, non-special events that do not share effects with current effects
        List<GlobalEvent> singleEvents = Arrays.stream(GlobalEvent.values())
                .filter(event -> !event.isCombinedGlobal())
                .filter(event -> !event.isSpecial())
                .filter(event -> event.getEffects().stream().noneMatch(activeEffects::contains))
                .collect(Collectors.toList());

        if (singleEvents.isEmpty()) {
            player.getPacketSender().sendMessage("@red@Unable to activate another event as all possible events are already active!");
            return;
        }

        Collections.shuffle(singleEvents);

        activeGlobalEvents.add(singleEvents.get(0));
        if (globalEventTimeRemaining < 1) {
            globalEventTimeRemaining = 15000;
        }

        String eventLog = player.getUsername() + " activated Global " + singleEvents.get(0).getEventName() + " Event!";

        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
            new MessageBuilder().append("<@&911737660559736873> " + eventLog).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.EVENTS_CH).get());


        listAllActiveEffects(player);
    }

    public static void startDiscordGlobalEvent() {
        // list of current active effects derived from current active global events
        // effectively loops over all active events, grabs their effects and puts them all into a list
        List<GlobalEvent.Effect> activeEffects = activeGlobalEvents.stream().map(GlobalEvent::getEffects)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        // list of single, non-special events that do not share effects with current effects
        // loops over all possible events, filters (removes) all events that are combined, are special, or share effects with currently active events, then puts the remainder into a list
        List<GlobalEvent> singleEvents = Arrays.stream(GlobalEvent.values())
                .filter(event -> !event.isCombinedGlobal())
                .filter(event -> !event.isSpecial())
                .filter(event -> event.getEffects().stream().noneMatch(activeEffects::contains))
                .collect(Collectors.toList());

        if (singleEvents.isEmpty()) {
            GameServer.getLogger().warning("Unable to activate another event as all possible events are already active!");
            return;
        }

        Collections.shuffle(singleEvents);

        GlobalEvent randomEvent = singleEvents.get(0);

        activeGlobalEvents.add(randomEvent);

        if (globalEventTimeRemaining < 1) {
            globalEventTimeRemaining = 15000;
        }

        String eventLog = "[Discord Staff] activated Global " + randomEvent.getEventName() + " Event!";
        World.sendMessage("events", "[Discord Staff] activated Global " + randomEvent.getEventName() + " Event!");

        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
            new MessageBuilder().append("<@&911737660559736873> " + eventLog).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.EVENTS_CH).get());
    }

    public static boolean effectActive(GlobalEvent.Effect effect) {
        if (activeGlobalEvents.isEmpty()) {
            return false;
        }

        for (GlobalEvent events : activeGlobalEvents) {
            if (events.getEffects().contains(effect))
                return true;
        }

        return false;
    }

    public static void endGlobalEvent() {
        if (!activeGlobalEvents.isEmpty()) {
            globalEventTimeRemaining = 0;
            activeGlobalEvents.clear();
            World.sendMessage("events", "@red@[EVENT] @blu@The current event has ended!");
        }
    }

    public static void listAllActiveEffects(Player player) {
        GlobalEventHandler.activeGlobalEvents.stream().map(GlobalEvent::getEffects).flatMap(List::stream).forEach(effect ->
                player.getPacketSender().sendMessage("@or2@" + effect.getEffectName() + ": " + effect.getEffectDescription())
        );
    }
}
