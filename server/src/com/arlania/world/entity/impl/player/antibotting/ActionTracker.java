package com.arlania.world.entity.impl.player.antibotting;

import com.arlania.DiscordBot;
import com.arlania.GameSettings;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.antibotting.actions.Action;
import com.arlania.world.entity.impl.player.antibotting.actions.ActionCommand;
import com.arlania.world.entity.impl.player.antibotting.actions.ActionItemOnItem;
import org.javacord.api.entity.message.MessageBuilder;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.LongStream;

public class ActionTracker {
    private static final int TRACKING_LIMIT = 500;
    private static final int MAX_PATTERN_LENGTH = 10; // must be less than TRACKING_LIMIT*2
    public static final long IDLE_THRESHOLD = 15 * 60 * 1000; // 15 minutes
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private final LinkedList<Action> actions = new LinkedList<>();
    private final Player player;

    public ActionTracker(Player player) {
        this.player = player;
    }

    public LinkedList<Action> getActions() {
        return actions;
    }

    public void offer(Action action) {
        if (actions.size() >= TRACKING_LIMIT) {
            actions.removeLast();
        }
        actions.addFirst(action);

//        System.out.printf("%s performed %s%n", player.getUsername(), action.toString());
//        List<Pattern> patterns = getPatterns();
//        if (patterns.size() > 0) {
//            System.out.println("action list size: " + actions.size());
//            System.out.println("Found patterns:");
//            for (Pattern pattern : patterns) {
//                if (isActionable(pattern)) {
//                    System.out.println("\tLength: " + pattern.getActionSequence().size() + " Repetitions: " + pattern.getRepetitions());
//                    StringBuilder sb = new StringBuilder();
//                    for (Action a : pattern.getActionSequence()) {
//                        sb.append("\t\t").append(a.toString()).append("\n");
//                    }
//                    System.out.println(sb);
//                }
//            }
//        }
    }

    public String lastNActions(int n) {
        if (n >= actions.size())
            return null;
        StringBuilder sb = new StringBuilder();
        for (int i = actions.size() - 1; i > Math.max(actions.size() - n, 0); i--) {
            Action action = actions.get(i);
            sb.append(action.getType().toString()).append(" ").append(action.getWhen()).append("\n");
        }
        return sb.toString();
    }

    public static void openActionLog(Player p, Player target) {
        List<Action> lastNActions = target.getActionTracker().getActions();
        if (lastNActions.size() > 100)
            lastNActions = lastNActions.subList(0, 100);
        for (int i = 8145; i < 8196; i++)
            p.getPacketSender().sendString(i, "");
        for (int i = 12174; i < 12224; i++)
            p.getPacketSender().sendString(i, "");
        // how many strings can I send into this interface?
        p.getPacketSender().sendInterface(8134);
        p.getPacketSender().sendString(8136, "Close window");
        p.getPacketSender().sendString(8144, "Action Log for " + target.getUsername());
        p.getPacketSender().sendString(8145, "");

        sendActionsToDiscord(lastNActions, target.getUsername());

        int actionIdx = 0;
        for (int interfaceIdx = 8147; interfaceIdx < 8196 && actionIdx < lastNActions.size(); interfaceIdx++, actionIdx++) {
            p.getPacketSender().sendString(interfaceIdx, formatAction(lastNActions.get(actionIdx))); //"@dre@"
        }
        for (int interfaceIdx = 12174; interfaceIdx < 12224 && actionIdx < lastNActions.size(); interfaceIdx++, actionIdx++) {
            p.getPacketSender().sendString(interfaceIdx, formatAction(lastNActions.get(actionIdx))); //"@dre@"
        }

    }

    public static void discordActionLog(Player target) {
        List<Action> lastNActions = target.getActionTracker().getActions();
        sendActionsToDiscord(lastNActions, target.getUsername());
    }

    private static void sendActionsToDiscord(List<Action> actions, String target) {
        if (!GameSettings.DISCORD) {
            return;
        }
        if (actions.size() > 100)
            actions = actions.subList(0, 100);

        int actionIdx = 0;
        StringBuilder discordMessage = new StringBuilder();
        for (int interfaceIdx = 8147; interfaceIdx < 8196 && actionIdx < actions.size(); interfaceIdx++, actionIdx++) {
            discordMessage.append(formatDiscordAction(actions.get(actionIdx), target)).append('\n');
            if (discordMessage.length() > 1500) {
                new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ACTION_LOGS_CH).get());
                discordMessage.setLength(0);
            }
        }
        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ACTION_LOGS_CH).get());
    }

    private static double avgMillisBetweenActions(List<Action> actns) {
        int size = actns.size();
        return LongStream.range(1, size)
                .map(x -> (actns.get((int) (size - x)).getWhen() - actns.get((int) (size - x - 1)).getWhen()))
                .average()
                .orElse(0d);
    }

    public boolean isIdle(Player player) {

        long idleTime = player.getIdleTime();

        return !actions.isEmpty() && System.currentTimeMillis() - actions.getFirst().getWhen() >= idleTime;
    }

    public List<Pattern> getPatterns() {
        LinkedList<Pattern> patterns = new LinkedList<>();
        for (int i = 1; i <= MAX_PATTERN_LENGTH; i++) {
            if (actions.size() >= i * 2) {
                List<Action> needle = actions.subList(0, i);
                List<Action> haystack = actions.subList(i, actions.size());
                int repetitions = maxRepeating(haystack, needle);
                if (repetitions > 1) {
                    patterns.add(new Pattern(
                            repetitions + 1,
                            needle
                    ));
                }
            }
        }
        return patterns;
    }

    private static String formatAction(Action action) {
        String when = dateFormat.format(new Date(action.getWhen()));
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(when).append("] ");
        switch (action.getType()) {
            case ITEM_ON_ITEM:
                ActionItemOnItem ioi = (ActionItemOnItem) action;
                sb.append("Used ").append(ItemDefinition.forId(ioi.getFirstItemId()).getName())
                        .append(" on ").append(ItemDefinition.forId(ioi.getSecondItemId()).getName());
                break;
            case COMMAND:
                ActionCommand ac = (ActionCommand) action;
                sb.append("Command: ").append(ac.getCommand());
                break;

            default:
                sb.append(action);
        }

        return sb.toString();
    }

    private static String formatDiscordAction(Action action, String player) {
        String when = dateFormat.format(new Date(action.getWhen()));
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(when).append("] ");
        sb.append(" " + player + " ");
        switch (action.getType()) {
            case ITEM_ON_ITEM:
                ActionItemOnItem ioi = (ActionItemOnItem) action;
                sb.append("Used ").append(ItemDefinition.forId(ioi.getFirstItemId()).getName())
                        .append(" on ").append(ItemDefinition.forId(ioi.getSecondItemId()).getName());
                break;
            case COMMAND:
                ActionCommand ac = (ActionCommand) action;
                sb.append("Command: ").append(ac.getCommand());
                break;

            default:
                sb.append(action);
        }

        return sb.toString();
    }

    // https://www.geeksforgeeks.org/maximum-consecutive-occurrences-of-a-string-in-another-given-string/
    static int maxRepeating(List<Action> haystack, List<Action> needle) {
        int occurrences = KMPSearch(needle, haystack);

        if (occurrences == 0)
            return 0;

        List<Action> nTimesSublist = new LinkedList<>();
        for (int i = 0; i < occurrences; i++)
            nTimesSublist.addAll(needle);

        boolean found = Collections.indexOfSubList(haystack, nTimesSublist) != -1;
        while (!found) {
            if (nTimesSublist.size() < haystack.size()) {
                found = Collections.indexOfSubList(haystack, nTimesSublist) != -1;
            }

            occurrences -= 1;
//            nTimesSublist.subList(0, needle.size()).clear();
//            nTimesSublist = new LinkedList<>();
            nTimesSublist.clear();
            for (int i = 0; i < occurrences; i++)
                nTimesSublist.addAll(needle);
        }
        return occurrences;
    }

    // https://www.geeksforgeeks.org/frequency-substring-string/
    static int KMPSearch(List<Action> needle, List<Action> haystack) {
        int M = needle.size();
        int N = haystack.size();

        int[] lps = new int[M];
        int j = 0;

        computeLPSArray(needle, M, lps);

        int i = 0;
        int res = 0;
        int next_i = 0;

        while (i < N) {
            if (needle.get(j).functionallyEquals(haystack.get(i))) {
                j++;
                i++;
            }
            if (j == M) {
                j = lps[j - 1];
                res++;
                if (lps[j] != 0)
                    i = ++next_i;
                j = 0;
            } else if (i < N && !needle.get(j).functionallyEquals(haystack.get(i))) {
                if (j != 0)
                    j = lps[j - 1];
                else
                    i = i + 1;
            }
        }
        return res;
    }

    // https://www.geeksforgeeks.org/frequency-substring-string/
    static void computeLPSArray(List<Action> needle, int M, int[] lps) {
        int len = 0;
        int i = 1;
        lps[0] = 0;
        while (i < M) {
            if (needle.get(i).functionallyEquals(needle.get(len))) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = len;
                    i++;
                }
            }
        }
    }

    public static boolean isActionable(Pattern pattern) {
        if (pattern.getActionSequence().stream().allMatch(x -> x.getType() == Action.ActionType.MOVEMENT) && pattern.getRepetitions() < 50) {
            return false;
        } else
            return !pattern.getActionSequence().stream().allMatch(x -> x.getType() == Action.ActionType.ITEM_ON_ITEM) || pattern.getRepetitions() >= 200;
    }

    final static class Pattern {
        private final int repetitions;
        private final List<Action> actionSequence;

        Pattern(int repetitions, List<Action> actionSequence) {
            this.repetitions = repetitions;
            this.actionSequence = actionSequence;
        }

        public int getRepetitions() {
            return repetitions;
        }

        public List<Action> getActionSequence() {
            return actionSequence;
        }
    }
}
