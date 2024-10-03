package com.arlania;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Item;
import com.arlania.model.Skill;
import com.arlania.model.StaffRights;
import com.arlania.model.container.impl.MailBox;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.net.login.LoginResponses;
import com.arlania.util.Misc;
import com.arlania.util.PasswordUtils;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.PlayerPunishment;
import com.arlania.world.content.globalevents.GlobalEventHandler;
import com.arlania.world.content.pos.TradingPost;
import com.arlania.world.content.pos.TradingPostManager;
import com.arlania.world.content.seasonal.SeasonalHandler;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.PlayerLoading;
import com.arlania.world.entity.impl.player.PlayerSaving;
import com.arlania.world.entity.impl.player.antibotting.ActionTracker;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

public class DiscordBot {

    private static final String FILE_PATH = System.getProperty("user.home") + File.separator + "Saves/Logs/discordlogs/commands/";

    private static DiscordBot singleton;

    public static DiscordApi api;
    public String game;
    final String token = "";

    public static void initialize() {
        if (singleton == null) {
            singleton = new DiscordBot();
        }
    }

    public static DiscordBot getInstance() {
        return singleton;
    }

    public DiscordBot() {
        new File(FILE_PATH).mkdirs();
        try {
            api = new DiscordApiBuilder().setToken(token).login().join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // add logging for SEVER and WARNING messsages
        GameServer.getLogger().addHandler(new Handler() {
            @Override
            public void publish(LogRecord record) {
                if (GameSettings.DISCORD && record.getLevel().intValue() >= Level.WARNING.intValue()) {
                    SimpleFormatter formatter = new SimpleFormatter();
                    String messageToChunk = formatter.format(record);

                    // number of messages to send with max characters of message being 2000,
                    // reserving 6 characters for the formatting graves
                    int noMessages = (int) Math.ceil((double) messageToChunk.length() / (double) 1994);

                    String[] arr = messageToChunk.split("\n");

                    int messageSize = arr.length / noMessages;

                    List<String> messages = new ArrayList<>();

                    for (int i = 0, j = 0; i < noMessages; i++) {
                        StringBuilder builder = new StringBuilder("```");
                        for (int x = 0; x < messageSize; x++) {
                            builder.append(arr[j]).append("\n");
                            j++;
                        }
                        builder.append("```");
                        messages.add(builder.toString());
                    }

                    // send the chunked messages
                    for (String message : messages) {
                        new MessageBuilder().append(message).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.CONSOLE_LOG_CH).get());
                    }
                }
            }

            @Override
            public void flush() {

            }

            @Override
            public void close() throws SecurityException {

            }
        });

        api.addMessageCreateListener(event -> {
            if (event.getMessageAuthor().isYourself() || event.getMessageAuthor().isBotUser() || event.getReadableMessageContent().length() == 0)
                return;
            if (event.getReadableMessageContent().charAt(0) != '-') {//command prefix
                if (event.getChannel().getId() == GameSettings.DISC_CHAT_CH) {
                    World.sendMessage("", "<col=fdff00>" + "<img=" + getRoleId(event) + ">" + "<col=" + getRoleColour(event) + ">" + getRoleName(event) + event.getMessageAuthor().getDisplayName() + "</col>" + ": " + event.getReadableMessageContent() + "</col>");
//						event.getChannel().sendMessage(event.getMessageAuthor().getName());
                    return;
                } else {
                    return;
                }
            }
            Player player;
            long messageId = event.getMessageId();
            String[] cmd = event.getReadableMessageContent().substring(1).split(" ");
            try {
                String location = "";
                location = FILE_PATH + event.getMessageAuthor().getDiscriminatedName() + ".txt";
                String afterCMD = "";
                for (int i = 1; i < cmd.length; i++)
                    afterCMD += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                BufferedWriter writer = new BufferedWriter(new FileWriter(location, true));
                writer.write("[" + currentTime("dd MMMMM yyyy 'at' hh:mm:ss ") + "] - ::" + cmd[0] + " " + afterCMD);
                writer.newLine();
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (event.getChannel().getId() != GameSettings.DISC_COMMAND_CH) {
                event.getChannel().sendMessage("You can only do discord commands on commands channel -> DISCORDBOT category");
                return;
            }
            switch (cmd[0]) {
                case "update":
                    int time = 1000;

                    GameServer.setUpdating(true);
                    for (Player players : World.getPlayers()) {
                        if (players == null) {
                            continue;
                        }
                        players.getPacketSender().sendSystemUpdate(time);
                    }
                    TaskManager.submit(new Task(time) {
                        @Override
                        protected void execute() {
                            for (Player player : World.getPlayers()) {
                                if (player != null) {
                                    World.deregister(player);
                                }
                            }
                            World.saveWorldState();
                            GameServer.getLogger().info("Update task finished!");
                            stop();
                        }
                    });

                    return;
                case "prune":
                    int msgCount = Integer.parseInt(cmd[1]);
                    event.getMessage().getMessagesAfter(msgCount);

                    //event.getChannel().getmessage;
                    return;
                case "ping":
                    event.getChannel().sendMessage("pong!");
                    return;
                case "well":
                    if (event.getMessageAuthor().canManageServer()) {
                        event.getChannel().sendMessage("You just popped the Well of Events!");
                        GlobalEventHandler.startDiscordGlobalEvent();
                    }
                    return;
                case "actions":
                    if (event.getMessageAuthor().canKickUsersFromServer()) {
                        if (cmd.length < 2) {
                            event.getChannel().sendMessage("Command usage: '-actions (target)'");
                            break;
                        }
                        StringBuilder username = new StringBuilder(cmd[1].toLowerCase());
                        if (cmd.length > 2) {
                            for (int i = 2; i < cmd.length; i++) {
                                username.append(' ').append(cmd[i].toLowerCase());
                            }
                        }
                        Player target = World.getPlayerByName(username.toString());
                        if (target == null) {
                            event.getChannel().sendMessage(username + " is offline.");
                            break;
                        }
                        ActionTracker.discordActionLog(target);
                        break;
                    }
                case "resetseasonal":
                    if (event.getMessageAuthor().canKickUsersFromServer()) {
                        if (cmd.length < 2) {
                            event.getChannel().sendMessage("Command usage: '-actions (target)'");
                            break;
                        }
                        StringBuilder username = new StringBuilder(cmd[1].toLowerCase());
                        if (cmd.length > 2) {
                            for (int i = 2; i < cmd.length; i++) {
                                username.append(' ').append(cmd[i].toLowerCase());
                            }
                        }
                        Player target = World.getPlayerByName(username.toString());
                        if (target == null) {
                            event.getChannel().sendMessage(username + " is offline.");
                            break;
                        }
                        SeasonalHandler.resetSeasonal(target);
                        break;
                    }

                    break;
                case "kick":
                    if (event.getMessageAuthor().canManageServer()) {
                        StringBuilder name = new StringBuilder();
                        for (int i = 1; i < cmd.length; i++)
                            name.append(cmd[i]).append((i == cmd.length - 1) ? "" : " ");
                        Player target = World.getPlayerByName(name.toString());

                        if (target == null) {
                            event.getChannel().sendMessage("Player is offline.");
                            return;
                        }
                        target.dispose();
                        event.getChannel().sendMessage(cmd[1] + " has been kicked.");
                    } else {
                        event.getChannel().sendMessage("You don't have permission to do that.");
                    }
                    break;
                case "mute":
                    if (event.getMessageAuthor().asUser().get().getRoles(event.getServer().get()).stream().filter(role -> role == event.getApi().getRoleById(GameSettings.SUPPORT_ROLE).get()) != null) {

                        Player target = World.getPlayerByName(cmd[1].toLowerCase());

                        if (target == null) {
                            event.getChannel().sendMessage("Player is offline.");
                            return;
                        }

                        String player2 = target.getUsername();
                        PlayerPunishment.mute(player2);
                        target.getPacketSender().sendMessage("@red@You have been muted.");
                        event.getChannel().sendMessage(cmd[1] + " has been muted.");
                    } else {
                        event.getChannel().sendMessage("You don't have permission to do that.");
                    }
                    break;
                case "bxp":
                    if (event.getMessageAuthor().canManageServer()) {
                        boolean bxp;
                        bxp = !GameSettings.BONUS_EXP;
                        GameSettings.BONUS_EXP = bxp;
                        event.getChannel().sendMessage("Bonus XP is " + (GameSettings.BONUS_EXP ? "activated." : "deactivated."));
                        return;
                    } else {
                        event.getChannel().sendMessage("You don't have permission to do that.");
                    }
                    break;
					
				  /*case "start-game":
		                DiscordBot.getInstance().getGameManager().startNewGame(command.substring(11));
		                break;*/

                case "searchitem":
                    if (event.getMessageAuthor().isServerAdmin()) {
                        String itemName = "";
                        StringBuilder sb1 = new StringBuilder();
                        StringBuilder sb2 = new StringBuilder();
                        StringBuilder sb3 = new StringBuilder();
                        int count = 0;

                        for (int i = 1; i < cmd.length; i++)
                            itemName += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                        event.getChannel().sendMessage("Searching for items containing name: " + itemName);
                        for (int i = 0; i < ItemDefinition.getDefinitions().length; i++) {
                            if (ItemDefinition.forId(i).getName().toLowerCase().contains(itemName.toLowerCase())) {
                                if (count < 30)
                                    sb1.append("Result found: " + i + " - " + ItemDefinition.forId(i).getName() + " " + (ItemDefinition.forId(i).isNoted() ? "(noted)" : "") + "\n");
                                if (count >= 30 && count < 60)
                                    sb2.append("Result found: " + i + " - " + ItemDefinition.forId(i).getName() + " " + (ItemDefinition.forId(i).isNoted() ? "(noted)" : "") + "\n");
                                if (count >= 60 && count < 100)
                                    sb3.append("Result found: " + i + " - " + ItemDefinition.forId(i).getName() + " " + (ItemDefinition.forId(i).isNoted() ? "(noted)" : "") + "\n");
                                //								event.getChannel().sendMessage("Result found: " + i + " - " + ItemDefinition.forId(i).getName() + " " + (ItemDefinition.forId(i).isNoted() ? "(noted)" : "")+"\n");
                                count++;
                            }
                        }
                        event.getChannel().sendMessage(sb1.toString());
                        event.getChannel().sendMessage(sb2.toString());
                        event.getChannel().sendMessage(sb3.toString());
                        event.getChannel().sendMessage("Finished with " + count + " results");
                        return;
                    } else {
                        event.getChannel().sendMessage("You don't have permission to do that.");
                    }
                    break;

                case "reaction":
                    event.getMessage().addReaction("");
                    event.getChannel().sendMessage("You don't have permission to do that.");
                    break;
                case "shopmute":
                    if (event.getMessageAuthor().isServerAdmin()) {
                        boolean err = cmd.length < 2;

                        if (err) {
                            event.getMessage().reply("Incorrect syntax for -shopmute\n-shopmute <playerName>");
                            break;
                        }
                        StringBuilder name = new StringBuilder();
                        for (int i = 1; i < cmd.length; i++)
                            name.append(cmd[i]).append((i == cmd.length - 1) ? "" : " ");
                        Optional<TradingPost> shopOpt = TradingPostManager.findByOwnerName(name.toString());

                        if (shopOpt.isPresent() && TradingPostManager.mute(name.toString())) {
                            event.getMessage().reply("You have " + (shopOpt.get().isMuted() ? "muted " : "unmuted ") + Misc.formatText(name.toString()) + "'s shop.");
                        } else {
                            event.getMessage().reply("Unable to mute/unmute " + Misc.formatText(name.toString()) + "'s shop.");
                        }
                    } else {
                        event.getMessage().reply("You don't have permission to do that.");
                    }
                    break;
                case "giveitem":
                    if (event.getMessageAuthor().isServerAdmin()) {
                        boolean err = cmd.length < 3;

                        int item = 0;
                        int amount = 0;
                        try {
                            item = Integer.parseInt(cmd[1]);
                            amount = Integer.parseInt(cmd[2]);
                        } catch (Exception e) {
                            err = true;
                        }
                        if (err || item <= 0 || amount <= 0) {
                            event.getMessage().reply("Incorrect syntax for -giveitem\n-giveitem <id> <amount> <playerName>");
                            break;
                        }
                        StringBuilder name = new StringBuilder();
                        for (int i = 3; i < cmd.length; i++)
                            name.append(cmd[i]).append((i == cmd.length - 1) ? "" : " ");
                        if (MailBox.addMail(name.toString(), new Item(item, amount))) {
                            event.getMessage().reply("You have sent " + ItemDefinition.forId(item).getName() + " to " + Misc.formatText(name.toString()) + ".");
                        } else {
                            event.getMessage().reply("Unable to send " + ItemDefinition.forId(item).getName() + " to " + Misc.formatText(name.toString()) + ".");
                        }
                    } else {
                        event.getMessage().reply("You don't have permission to do that.");
                    }
                    break;
                case "takeitem":
                    if (event.getMessageAuthor().isServerAdmin()) {
                        int itemId = Integer.parseInt(cmd[1]);
                        int itemAmount = Integer.parseInt(cmd[2]);
                        String name = "";
                        for (int i = 3; i < cmd.length; i++)
                            name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                        Player target = World.getPlayerByName(name);
                        if (target == null) {
                            event.getChannel().sendMessage("Player is offline.");
                            return;
                        }
                        target.getInventory().delete(itemId, itemAmount);
                        target.sm("You have had " + ItemDefinition.forId(itemId).getName() + " taken by " + event.getMessageAuthor().getDiscriminatedName() + ".");
                        event.getChannel().sendMessage("You have taken " + ItemDefinition.forId(itemId).getName() + " from " + target.getDisplayName() + ".");
                        return;
                    } else {
                        event.getChannel().sendMessage("You don't have permission to do that.");
                    }
                    break;
                case "givebank":
                    if (event.getMessageAuthor().isServerAdmin()) {
                        int itemId = Integer.parseInt(cmd[1]);
                        int itemAmount = Integer.parseInt(cmd[2]);
                        String name = "";
                        for (int i = 3; i < cmd.length; i++)
                            name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                        Player target = World.getPlayerByName(name);
                        if (target == null) {
                            event.getChannel().sendMessage("Player is offline.");
                            return;
                        }
                        target.getBank().add(itemId, itemAmount);
                        target.sm("You have been given " + ItemDefinition.forId(itemId).getName() + " by " + event.getMessageAuthor().getDiscriminatedName() + ". It is in your bank.");
                        event.getChannel().sendMessage("You have sent " + ItemDefinition.forId(itemId).getName() + " to " + target.getDisplayName() + "'s bank.");
                    } else {
                        event.getChannel().sendMessage("You don't have permission to do that.");
                    }
                    break;
                case "takebank":
                    if (event.getMessageAuthor().isServerAdmin()) {
                        int itemId = Integer.parseInt(cmd[1]);
                        int itemAmount = Integer.parseInt(cmd[2]);
                        String name = "";
                        for (int i = 3; i < cmd.length; i++)
                            name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");

                        Player target = World.getPlayerByName(name);

                        if (target == null) {
                            event.getChannel().sendMessage("Player is offline.");
                            return;
                        }

                        target.getBank().delete(target.getBank().getSlot(itemId), itemAmount);
                        target.sm("You have had " + ItemDefinition.forId(itemId).getName() + " taken from your bank by " + event.getMessageAuthor().getDiscriminatedName() + ". It is in your bank.");
                        event.getChannel().sendMessage("You have taken " + ItemDefinition.forId(itemId).getName() + " from " + target.getDisplayName() + "'s bank.");
                    } else {
                        event.getChannel().sendMessage("You don't have permission to do that.");
                    }
                    break;
                case "players":
                    String list = "Players Online: \n";
                    if (World.getPlayers().size() == 0)
                        list += "none" + "\n";
                    for (Player p : World.getPlayers()) {
                        if (p == null)
                            continue;
                        String username = p.getUsername();
                        if (username != null)
                            list += (getMessageIcon(username) + username.toUpperCase().charAt(0) + username.substring(1) + "\n");
                    }
                    list += "There " + (World.getPlayers().size() == 1 ? "is" : "are") + " currently " + World.getPlayers().size() + " " + (World.getPlayers().size() == 1 ? "person" : "people") + " playing on " + GameSettings.SERVER_NAME;
                    event.getChannel().sendMessage(list);
                    break;
                case "staff":


                    List<String> moderators = new ArrayList<>(), administrators = new ArrayList<>(), developers = new ArrayList<>(), owners = new ArrayList<>();
                    for (Player p : World.getPlayers()) {
                        if (p == null)
                            continue;
                        if (p.getStaffRights() == StaffRights.OWNER)//your player  rights handled?
                            owners.add(p.getDisplayName().substring(0, 1).toUpperCase() + p.getDisplayName().substring(1));
                        else if (p.getStaffRights() == StaffRights.DEVELOPER)
                            developers.add(p.getDisplayName().substring(0, 1).toUpperCase() + p.getDisplayName().substring(1));
                        else if (p.getStaffRights() == StaffRights.MODERATOR)
                            moderators.add(p.getDisplayName().substring(0, 1).toUpperCase() + p.getDisplayName().substring(1));
                        else if (p.getStaffRights() == StaffRights.ADMINISTRATOR)
                            administrators.add(p.getDisplayName().substring(0, 1).toUpperCase() + p.getDisplayName().substring(1));
                    }
                    int staffAmt = moderators.size() + administrators.size() + developers.size() + owners.size();
                    event.getChannel().sendMessage("There " + (World.getPlayers().size() == 1 ? "is" : "are")
                            + " currently " + staffAmt + " staff " + (staffAmt == 1 ? "member" : "members")
                            + " on " + GameSettings.SERVER_NAME + "!\n" + "Owners: "
                            + owners.stream().map(Object::toString).collect(Collectors.joining(", ")) + "\n"
                            + "Developers: "
                            + developers.stream().map(Object::toString).collect(Collectors.joining(", "))
                            + "\n" + "Administrators: "
                            + administrators.stream().map(Object::toString).collect(Collectors.joining(", "))
                            + "\n" + "Moderators: "
                            + moderators.stream().map(Object::toString).collect(Collectors.joining(", ")));
                    break;
                case "players1":
                    event.getChannel()
                            .sendMessage(
                                    "There "
                                            + (World.getPlayers().size() == 1 ? "is"
                                            : "are")
                                            + " currently "
                                            + World.getPlayers().size()
                                            + " "
                                            + (World.getPlayers().size() == 1 ? "person"
                                            : "people")
                                            + " playing " + GameSettings.SERVER_NAME + "!");

                    break;

                case "stats":
                    if (cmd.length < 2) {
                        event.getChannel()
                                .sendMessage(
                                        "Use proper formatting: .stats <player_name>");
                        break;
                    }
                    player = World.getPlayerByName(cmd[1].toLowerCase());
                    if (player == null)
                        event.getChannel().sendMessage(
                                "This player is currently offline.");
                    else {
                        String statsMessage = "Current stats for "
                                + (player.getUsername().substring(0, 1)
                                .toUpperCase() + player
                                .getUsername().substring(1)) + "\nPrestige: " + player.prestige + "\nCombat: "
                                + player.getSkillManager().getCombatLevel()
                                + "\n";
                        for (Skill skill : Skill.values()) {
                            statsMessage += (skill.getName()
                                    + " - Level: "
                                    + player.getSkillManager().getCurrentLevel(skill)
                                    + ", Exp: "
                                    + Misc.format(player.getSkillManager().getExperience(skill)) + "\n");
                        }
                        statsMessage += "Total Level: "
                                + Misc.format(player.getSkillManager()
                                .getTotalLevel())
                                + ", Total Exp: "
                                + Misc.format(player.getSkillManager().getTotalExp());
                        event.getChannel().sendMessage(statsMessage);
                    }
                    break;

                case "online":
                    player = World.getPlayerByName(cmd[1].toLowerCase());
                    if (player == null)
                        event.getChannel().sendMessage("This player is currently offline.");
                    else
                        event.getChannel().sendMessage("This player is currently online.");
                    break;
//				case "reportbug"://this is for another private channel
//					if (event.getChannel().getId() == 651733630485397519L) {
//					String bug = "";
//					for (int i = 1; i < cmd.length; i++)
//						bug += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
//					EmbedBuilder eb = new EmbedBuilder();
//					eb.setColor(Color.RED);
//					eb.setThumbnail(event.getMessageAuthor().getAvatar());
//					eb.addField("Submitter", event.getMessageAuthor().getDiscriminatedName(), false);
//					eb.addField("Bug Report", String.valueOf(bug), false);
//					eb.setFooter("ID: " + messageId);
//					eb.setTimestamp(Instant.now());
//					new MessageBuilder().setEmbed(eb).send(DiscordBot.getInstance().getAPI().getTextChannelById("651733630485397519").get());
//					}
//					break;

                case "my-id":
                    event.getChannel().sendMessage("Your Discord ID: " + event.getMessageAuthor().getId());
                    break;


                case "commands":
                    event.getChannel().sendMessage("Current " + GameSettings.SERVER_NAME + " commands:\n" + "-players\n" + "-event\n" + "-Stats <player_name>\n" + "-staff\n" + "-online <player_name>\n" + "-link <player_name>\n");
                    break;
//				case "link":
//					player = World.getPlayer(cmd[1].toLowerCase());
//					if(player == null) {
//						event.getChannel().sendMessage("target is null");
//						return;
//					}
//					if (player != null) {
//						DialogueManager.start(player, Mandrith.getDialogue(player));
////						player.getDialogue().nextDialogue()
//						player.setDiscordName(event.getMessageAuthor().getDiscriminatedName());
//						player.sm("Discord name has been set!");
//					}
//					break;
                case "testrole":
//					event.
//					event.getApi().role.add(event.getApi().getRoleById(926124619432353823L).get());
                    return;
                case "roles":
                    event.getChannel().sendMessage(Arrays.toString(event.getApi().getRoles().toArray()));
                    return;
                case "link":
                    if (cmd.length < 2) {
                        event.getChannel().sendMessage("Please use -link ingameName");
                        return;
                    }
                    player = World.getPlayerByName(cmd[1].toLowerCase());
                    if (player == null) {
                        event.getChannel().sendMessage("target is null");
                        return;
                    }
                    if (player != null) {
                        if (player.getDiscordName() != null && player.getDiscordName().equalsIgnoreCase(event.getMessageAuthor().getDiscriminatedName())) {
                            if (player.isDiscordLinked() && player.getDiscordName().equalsIgnoreCase(event.getMessageAuthor().getDiscriminatedName())) {
                                event.getChannel().sendMessage("Your account is already linked.");
                                return;
                            } else if (!player.isDiscordLinked() && player.getDiscordName().equalsIgnoreCase(event.getMessageAuthor().getDiscriminatedName())) {
                                //event.getChannel().sendMessage("Please use ::unlink to remove the old disc integration");
                                player.setDiscordLinked(true);
                                player.sm("Discord name has been set!");
                                event.getServer().get().addRoleToUser(event.getMessageAuthor().asUser().get(), event.getApi().getRoleById(GameSettings.ROLE_INGAME_CHAT).get());
                                return;
                            }
                        } else {
                            event.getChannel().sendMessage("In game, please use ::linkdisc discordName ex: example#1000");
                        }
                    }
                    break;
                case "approve":
                    long id = messageId;
                    event.deleteMessage(String.valueOf(id));
                    break;
                default:

                    break;
                case "resetpw":
                    StringBuilder name = new StringBuilder();
                    for (int i = 1; i < cmd.length; i++)
                        name.append(cmd[i]).append((i == cmd.length - 1) ? "" : " ");
                    Player target = new Player(null).setUsername(com.arlania.util.NameUtils.capitalizeWords(name.toString()));
                    if (PlayerLoading.getResultWithoutLogin(target) == LoginResponses.NEW_ACCOUNT) {
                        event.getMessage().reply("That player doesn't exist!");
                        return;
                    }

                    char[] chars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
                            '6', '7', '8', '9'};
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < 11; i++) {
                        sb.append(chars[RandomUtility.exclusiveRandom(chars.length)]);
                    }

                    target.setPasswordHash(PasswordUtils.hash(sb.toString()));
                    PlayerSaving.save(target);
                    event.getMessage().reply("Set " + target.getUsername() + "'s password to '" + sb + "'");
                    break;
            }
        });
    }

    public void shutdown() {
        if (GameSettings.DISCORD)
            new MessageBuilder().append("Server shutting down").send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.CONSOLE_LOG_CH).get());
        api.disconnect();
    }

    public interface Helper {

        int getRights(String username);

        String getDisplayName(String username);

        void sendFriendsChatMessage(String owner, String username, String message);

        int getPlayersOnline();

        int getLevel(String username, int skill);

        double getXp(String username, int skill);

        String getEquip(String username, int index);

        String[] getStatuses(String username);

        boolean isDynamicRegion(int x, int y, int z);

        static String getStatus() {
            return "!help";
        }
    }


    public DiscordApi getAPI() {
        return api;
    }

    private String getMessageIcon(String players) {
        String icon = "";
        StaffRights rights = World.getPlayerByName(players).getStaffRights();
        switch (rights) {
            case OWNER:
                icon = "owner";
                break;
            case MODERATOR:
                icon = "mod";
                break;
            case DEVELOPER:
                icon = "dev";
                break;
            default:
                break;
        }
        if (icon != "")
            return ":" + icon + ": ";
        else
            return "";
    }

    @SuppressWarnings("unlikely-arg-type")
    public int getRoleId(MessageCreateEvent message) {//this is for crown ids - ingame chat
        int iconId = 3;
        if (!message.getMessageAuthor().canManageRolesOnServer() && !message.getMessageAuthor().getName().contains("paepay"))
            iconId = 3;
        if (message.getMessageAuthor().isServerAdmin())
            iconId = 1;
        if (message.getMessageAuthor().getRoleColor().toString() == "#6f06c2") {

        }

        return iconId;
    }

    @SuppressWarnings("unlikely-arg-type")
    public String getRoleName(MessageCreateEvent message) {//for tag names for ingame chat
        String roleName = "[Member] ";
        if (!message.getMessageAuthor().canManageRolesOnServer())
            roleName = "[Member] ";
        else
            roleName = "[" + GameSettings.SERVER_NAME + " Staff] ";

        if (message.getMessageAuthor().isServerAdmin() && message.getMessageAuthor().getName().equalsIgnoreCase("paepay"))
            roleName = "[" + GameSettings.SERVER_NAME + " Founder] ";
        return roleName;
    }

    @SuppressWarnings("unlikely-arg-type")
    public String getRoleColour(MessageCreateEvent message) {
        String roleColour = "418363";
        if (!message.getMessageAuthor().canManageRolesOnServer())
            roleColour = "836341";
        else
            roleColour = "7c53dd";
        if (message.getMessageAuthor().isServerAdmin())
            roleColour = "634F4F";
        return roleColour;
    }

    public static String currentTime(String dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(cal.getTime());
    }

    public static String setTrueDiscordName(String trueDiscordName) {
        return trueDiscordName;
    }
}
