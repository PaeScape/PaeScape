package com.arlania.world.content;

import com.arlania.DiscordBot;
import com.arlania.GameSettings;
import com.arlania.model.Item;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.model.definitions.NPCDrops;
import com.arlania.net.packet.PacketSender;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.clog.CollectionLog;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.entity.impl.player.Player;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.javacord.api.entity.message.MessageBuilder;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Bingo {
    private static final int TITLE_ID = 33627;
    private static final int ITEM_START_ID = 33658;
    private static final int LINE_START_ID = 33683;
    private static final int OVERLAY_START_ID = 33758;
    private static final int INTERFACE_ID = 33622;
    private static final int[] CLAIM_BUTTON_IDS = {33630, 33640, 33650};
    private static final int[] INFO_BUTTON_IDS = {33634, 33644, 33654};

    private static final int[] REWARD_ITEM_IDS = {33628, 33638, 33648};
    private static final int EXIT_BUTTON_ID = 33624;
    private static final int[] DESCRIPTION_IDS = {33629, 33639, 33649};

    private static final int[] EXCLUDED_CLOG_NPCS = {-7, -8, -9, -10, -11, -12, -13, -14, 6715, -101, -102, -103, -104, -105, -106, -107};
    private static final int[] EXCLUDED_CLOG_ITEM_IDS = {
            995, // coins
            219782, // elite clue
            219835, // master clue
            221992, // pet vork
            20079, // vetion pet
            20083, // ven pet
            20081, // scorpia pet
            20085, // calisto pet
            11995, // chaos ele pet
            11992, // td pet
            11690, // godsword blade
            6739, // dragon hatchet
            15259, // dragon pickaxe
            15271, // Noted raw rocktail
            15273, // Noted rocktail
            14876, // start wildy statuettes
            14877,
            14878,
            14879,
            14880,
            14881,
            14882,
            14883, // end wildy statuettes
            2677  // 500 Paepoint scroll
    };
    private static final List<Square> possibleSquares = new ArrayList<>();

    private final Player player;

    public List<Square> getBoard() {
        return board;
    }

    private List<Square> board;
    private List<Reward> rewards;

    public Bingo(Player player) {
        this.player = player;
    }

    public static void init(Map<CollectionLog.CollectionTabType, ArrayList<Integer>> collectionNPCS) {
        collectionNPCS.forEach((category, npcIds) -> {
                    if (category == CollectionLog.CollectionTabType.UPGRADES)
                        return;

                    // only include an 'any' square for raids
                    if (category == CollectionLog.CollectionTabType.RAIDS) {
                        npcIds.forEach(npcId ->
                                possibleSquares.add(new Square(npcId, "Any\n" + npcNameForSquare(npcId) + "\nDrop", false))
                        );
                        return;
                    }

                    for (int npcId : npcIds) {
                        // dont include certain npcs/drop sources. e.g. rev imp, mystery boxes, etc
                        if (Arrays.stream(EXCLUDED_CLOG_NPCS).anyMatch(excluded -> excluded == npcId))
                            continue;

                        // only include an 'any' square for superior slayer
                        if (npcId == CollectionLog.CustomCollection.SUPERIOR_SLAYER.getId()) {
                            possibleSquares.add(new Square(npcId, "Any\n" + npcNameForSquare(npcId) + "\nDrop", false));
                            continue;
                        }

                        List<Item> rareDropList = CollectionLog.getRareDropList(npcId);

                        // remove drops that are too rare and not rare enough
                        NPCDrops drops = MonsterDrops.npcDrops.get(CollectionLog.getCollectionName(npcId).toLowerCase());
                        if (drops != null) {
                            for (int i = 0; i < drops.getDropList().length; i++) {
                                if (drops.getDropList()[i].getItem().getId() <= 0 || drops.getDropList()[i].getItem().getId() > ItemDefinition.getMaxAmountOfItems() || drops.getDropList()[i].getItem().getAmount() <= 0) {
                                    continue;
                                }
                                if (drops.getDropList()[i].getChance().ordinal() < NPCDrops.DropChance.UNCOMMON.ordinal() || drops.getDropList()[i].getChance().ordinal() > NPCDrops.DropChance.ULTRA_RARE.ordinal()) {
                                    final int fi = i;
                                    rareDropList.removeIf(item -> item.getId() == drops.getDropList()[fi].getItem().getId());
                                }
                            }
                        }


                        // remove raid pets and whatnot
                        rareDropList.removeIf(drop -> Arrays.stream(EXCLUDED_CLOG_ITEM_IDS).anyMatch(id -> drop.getId() == id));

                        // add all drops as possible squares
                        rareDropList.forEach(drop -> possibleSquares.add(new Square(drop.getId(), null, false)));

                        // add a square for any of the drops from this npcId
                        if (!rareDropList.isEmpty()) {
                            possibleSquares.add(new Square(npcId, "Any\n" + npcNameForSquare(npcId) + "\nDrop", false));
                        }
                    }
                }
        );
    }

    public static String npcNameForSquare(int npcId) {
        String name = CollectionLog.getCollectionName(npcId);

        switch (name) {
            case "Thermonuclear Smoke Devil":
                return "Thermo";
            case "Corporeal Beast":
                return "Corp";
            case "King Black Dragon":
                return "KBD";
            case "Tormented Demon":
                return "TD";
            case "Stronghold Raids":
                return "SHR";
            case "Chambers of Xeric":
                return "CoX";
            case "Theatre of Blood":
                return "ToB";
            case "Chaos Elemental":
                return "Chaos Ele";
            case "Kalphite Queen":
                return "KQ";
            case "Superior Slayer":
                return "Superior Slay";
        }

        name = name.replace("Revenant", "Rev");
        name = name.replace("Dark", "D");
        name = name.replace("Dagannoth", "Dag");
        name = name.replace("Commander ", "");
        name = name.replace("General ", "");
        name = name.replace("Tsutsuroth", "");

        return name;
    }


    public void open() {
        if (board == null && player.getInventory().contains(2795)) {
            board = generateBoard();
            rewards = Arrays.asList(Reward.random(RewardTier.ANY_LINE), Reward.random(RewardTier.P), Reward.random(RewardTier.BLACKOUT));
        }
        sendBoardElements(player, board, rewards);
        player.getPacketSender().sendInterface(INTERFACE_ID);
    }

    private static void sendBoardElements(Player p, List<Square> b, List<Reward> rewards) {
        final int completed = (int) b.stream().filter(Square::isCompleted).count();
        final PacketSender packetSender = p.getPacketSender();
        if (rewards.stream().allMatch(reward -> reward.getItemId() == 0)) {
            packetSender.sendString(TITLE_ID, "BINGO - EXAMPLE");
        } else {
            packetSender.sendString(TITLE_ID, "BINGO - " + completed + "/25");
        }
        for (int i = 0; i < b.size(); i++) {
            Square square = b.get(i);

            String[] lines = {"", "", ""};
            if (square.getText() != null) {
                lines = square.getText().split("\n");
            }

            for (int j = 0; j < lines.length; j++) {
                packetSender.sendString(LINE_START_ID + (i * 3) + j, lines[j]);
            }
            for (int j = lines.length; j < 3; j++) {
                packetSender.sendString(LINE_START_ID + (i * 3) + j, "");
            }

            if (square.getText() == null) {
                // send item to the board
                packetSender.sendItemOnInterface(ITEM_START_ID + i, square.getRequirementId(), 1);
            } else {
                packetSender.sendItemOnInterface(ITEM_START_ID + i, 0, 0);
            }

            // clear the overlays
            packetSender.sendInterfaceDisplayState(OVERLAY_START_ID + i, !square.isCompleted());
        }

        // send reward items and descriptions to interface
        for (int i = 0; i < rewards.size(); i++) {
            final Reward reward = rewards.get(i);
            packetSender.sendString(DESCRIPTION_IDS[i], reward.getRewardTier().getDescription());
            packetSender.sendItemOnInterface(REWARD_ITEM_IDS[i], reward.getItemId(), reward.getAmount());
        }
    }


    public void handleDrop(int dropId, int npcId) {
        if (!isActive() || Arrays.stream(EXCLUDED_CLOG_NPCS).anyMatch(id -> id == npcId)) {
            return;
        }
        board.stream()
                .filter(square -> !square.isCompleted() && square.requirementId == (square.getText() == null ? dropId : npcId))
                .forEach(square -> {
                    square.setCompleted(true);
                    player.getPacketSender().sendMessage("<col=a15200>You've completed a square on your BINGO card.");
                });

    }

    public void load(JsonObject bingoBoardJson) {
        final Gson builder = new Gson();
        final Type listType = new TypeToken<ArrayList<Square>>() {
        }.getType();
        final Type rewardType = new TypeToken<ArrayList<Reward>>() {
        }.getType();

        board = builder.fromJson(bingoBoardJson.get("squares"), listType);
        rewards = builder.fromJson(bingoBoardJson.get("rewards"), rewardType);
    }

    public JsonElement save() {
        final Gson builder = new Gson();
        JsonObject boardJson = new JsonObject();
        boardJson.add("squares", builder.toJsonTree(board));
        boardJson.add("rewards", builder.toJsonTree(rewards));
        return boardJson;
    }

    public boolean handleButton(int id) {
        if (id == EXIT_BUTTON_ID) {
            player.getPacketSender().sendInterfaceRemoval();
            return true;
        }
        for (int i = 0; i < INFO_BUTTON_IDS.length; i++) {
            if (id == INFO_BUTTON_IDS[i]) {
                sendExample(rewards.get(i).getRewardTier());
                return true;
            }
        }
        for (int i = 0; i < CLAIM_BUTTON_IDS.length; i++) {
            if (id == CLAIM_BUTTON_IDS[i]) {
                claimCompletion(rewards.get(i));

                return true;
            }
        }
        return false;
    }

    private void sendExample(RewardTier rewardTier) {
        List<Reward> exampleRewards = IntStream.range(0, 3).mapToObj(idx -> new Reward(RewardTier.values()[idx], 0, 0, false)).collect(Collectors.toList());
        List<Square> exampleBoard = IntStream.range(0, 25).mapToObj(idx -> new Square(0, "", false)).collect(Collectors.toList());
        switch (rewardTier) {
            case ANY_LINE:
                int randExample = RandomUtility.inclusiveRandom(2);
                int randModifier = RandomUtility.exclusiveRandom(5);
                switch (randExample) {
                    case 0:
                        // column
                        IntStream.range(0, board.size())
                                .filter(n -> n % 5 == 0)
                                .map(idx -> idx + randModifier)
                                .mapToObj(exampleBoard::get)
                                .forEach(square -> {
                                    square.setCompleted(true);
                                    square.setText("Example\nCompletion");
                                });
                        break;
                    case 1:
                        // row
                        IntStream.range(randModifier * 5, (randModifier * 5) + 5)
                                .mapToObj(exampleBoard::get)
                                .forEach(square -> {
                                    square.setCompleted(true);
                                    square.setText("Example\nCompletion");
                                });
                        break;
                    case 2:
                        IntStream.range(0, 5)
                                .map(idx -> randModifier % 2 == 0 ? (20 - idx * 4) : idx * 6)
                                .mapToObj(exampleBoard::get)
                                .forEach(square -> {
                                    square.setCompleted(true);
                                    square.setText("Example\nCompletion");
                                });
                        break;
                }
                break;
            case P:
                final int[] pSquares = {0, 5, 10, 15, 20, 1, 2, 7, 11, 12};
                Arrays.stream(pSquares).mapToObj(exampleBoard::get).forEach(square -> {
                    square.setCompleted(true);
                    square.setText("Example\nCompletion");
                });

                break;
            case BLACKOUT:
                exampleBoard.forEach(square -> {
                    square.setCompleted(true);
                    square.setText("Example\nCompletion");
                });
                break;
        }
        sendBoardElements(player, exampleBoard, exampleRewards);
    }

    public void claimCompletion(Reward reward) {
        if (checkCompletion(reward) && player.getInventory().contains(2795)) {
            if (!reward.isClaimed()) {
                player.sendMessage("@red@You have claimed your reward for the " + reward.getRewardTier().getFormattedName() + " BINGO tier.");
                player.sendMessage("@red@" + reward.getAmount() + "x " + ItemDefinition.forId(reward.getItemId()).getName() + (reward.getAmount() > 1 ? "@bla@ have" : "@bla@ has") + " been sent to your Mailbox.");

                String discordMessage = "[BINGO] " + player.getUsername() + " has claimed a reward!" + " " + reward.getRewardTier().getFormattedName();

                if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                    new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_BINGO_LOG).get());

                reward.setClaimed(true);
                player.getMailBox().addMail(reward.getItemId(), reward.getAmount());
                if (rewards.stream().allMatch(Reward::isClaimed)) {
                    player.getInventory().delete(2795, 1, true);
                    player.getPacketSender().sendInterfaceRemoval();
                    player.sendMessage("You have completed your BINGO card!");

                    World.sendMessage("drops", "<img=10> <col=008FB2>" + player.getUsername() + " has just completed a BINGO card!");

                    String discordMessage2 = "[BINGO] " + player.getUsername() + " has completed a BINGO Card!";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage2).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_BINGO_LOG).get());

                    player.bingoCompletions++;
                    reset();
                }
            } else {
                player.sendMessage("You have already claimed the reward for the " + reward.getRewardTier().getFormattedName() + " BINGO tier.");
            }
        } else {
            player.sendMessage("@red@You have not completed the requirements for the " + reward.getRewardTier().getFormattedName() + " BINGO tier.");
            player.sendMessage("Click the \"Info\" button to see examples of completions for this type.");
        }
    }

    public boolean checkCompletion(Reward reward) {
        switch (reward.getRewardTier()) {
            case ANY_LINE:
                for (int i = 0; i < 5; i++) {
                    // check every column
                    int finalI = i;
                    if (IntStream.range(0, board.size())
                            .filter(n -> n % 5 == 0)
                            .map(idx -> idx + finalI)
                            .mapToObj(idx -> board.get(idx).isCompleted())
                            .allMatch(Boolean::booleanValue)) {
                        return true;
                    }
                    // check every row
                    if (IntStream.range(i * 5, (i * 5) + 5)
                            .mapToObj(idx -> board.get(idx).isCompleted())
                            .allMatch(Boolean::booleanValue)) {
                        return true;
                    }
                }
                // check L-to-R positive slope diagonal
                if (IntStream.range(0, 5)
                        .map(idx -> 20 - idx * 4)
                        .mapToObj(idx -> board.get(idx).isCompleted())
                        .allMatch(Boolean::booleanValue)) {
                    return true;
                }
                // check R-to-L negative slope diagonal
                if (IntStream.range(0, 5)
                        .map(idx -> idx * 6)
                        .mapToObj(idx -> board.get(idx).isCompleted())
                        .allMatch(Boolean::booleanValue)) {
                    return true;
                }
                break;
            case P:
                final int[] pSquares = {0, 5, 10, 15, 20, 1, 2, 7, 11, 12};
                if (Arrays.stream(pSquares).mapToObj(idx -> board.get(idx).isCompleted()).allMatch(Boolean::booleanValue)) {
                    return true;
                }
                break;
            case BLACKOUT:
                if (board.stream().allMatch(Square::isCompleted)) {
                    return true;
                }
                break;
        }

        return false;
    }

    static List<Square> generateBoard() {
        Collections.shuffle(possibleSquares);
        return possibleSquares.stream().limit(25).map(Square::copy).collect(Collectors.toList());
    }

    public void reset() {
        board = null;
        rewards = null;
    }

    public boolean isActive() {
        return board != null;
    }

    public void reroll(boolean confirmed, int currencyId, int amount) {
        if (confirmed) {
            if (player.getInventory().getAmount(currencyId) >= amount) {
                player.getInventory().delete(currencyId, amount, true);
            } else if (currencyId == 995 && player.getMoneyInPouch() >= amount) {
                player.setMoneyInPouch(player.getMoneyInPouch() - amount);
                player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
            } else if (currencyId == 19864 && player.getPointsHandler().getAmountDonated() >= amount) {
                player.getPointsHandler().setAmountDonated(player.getPointsHandler().getAmountDonated(), -amount);
            } else {
                player.sendMessage("You do not have enough " + ItemDefinition.forId(currencyId).getName() + " to re-roll your BINGO card.");
                player.getPacketSender().sendInterfaceRemoval();
                return;
            }

            String discordMessage = "[BINGO] " + player.getUsername() + " has rerolled a BINGO Card!";

            if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_BINGO_LOG).get());

            player.sendMessage("You have re-rolled your BINGO card.");
            player.getPacketSender().sendInterfaceRemoval();
            board = null;
            open();
        } else {
            DialogueManager.start(player, 1035);
            player.setDialogueActionId(1035);
        }
    }

    @Data
    @AllArgsConstructor
    public static class Square {
        int requirementId;
        String text;
        boolean completed;

        public Square copy() {
            return new Square(requirementId, text, completed);
        }
    }

    @Data
    @AllArgsConstructor
    public static class Reward {
        RewardTier rewardTier;
        int itemId;
        int amount;
        boolean claimed;

        public static Reward random(RewardTier rewardTier) {
            int randReward = RandomUtility.exclusiveRandom(rewardTier.getPossibleRewards().length);
            return new Reward(
                    rewardTier,
                    rewardTier.getPossibleRewards()[randReward][0],
                    rewardTier.getPossibleRewards()[randReward][1],
                    false
            );
        }
    }

    @Getter
    @AllArgsConstructor
    public enum RewardTier {
        ANY_LINE("Any Line", new int[][]{
                {2709, 3}
        }),
        P("The \"P\"", new int[][]{
                {6854, 1}
        }),
        BLACKOUT("Blackout", new int[][]{
                {604, 1}
        });

        final String description;
        final int[][] possibleRewards;

        public String getFormattedName() {
            return Misc.formatText(name().toLowerCase().replace("_", " "));
        }
    }
}
