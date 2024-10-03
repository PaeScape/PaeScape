package com.arlania.world.content.dialogue.impl;

import com.arlania.GameSettings;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Direction;
import com.arlania.model.GameMode;
import com.arlania.model.Position;
import com.arlania.net.security.ConnectionHandler;
import com.arlania.world.World;
import com.arlania.world.content.clan.ClanChatManager;
import com.arlania.world.content.dialogue.Dialogue;
import com.arlania.world.content.dialogue.DialogueExpression;
import com.arlania.world.content.dialogue.DialogueType;
import com.arlania.world.entity.impl.player.Player;

/**
 * Represents a Dungeoneering party invitation dialogue
 *
 * @author Gabriel Hannason
 */

public class Tutorial {

    public static Dialogue get(Player p, int stage) {
        Dialogue dialogue = null;
        switch (stage) {
            case 0:
                dialogue = new Dialogue() {

                    @Override
                    public DialogueType type() {
                        return DialogueType.NPC_STATEMENT;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return DialogueExpression.NORMAL;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[]{"Ah, a wise choice indeed! So let's get you started out,", "shall we? I'll give you a few tips and once you've finished", "listening to me, I'll give you a @red@powerful Scythe@bla@ for your", "patience. Let's start with the most important aspect; money!"};
                    }

                    @Override
                    public int npcId() {
                        return 6139;
                    }

                    @Override
                    public Dialogue nextDialogue() {
                        return get(p, stage + 1);
                    }
                };
                break;
            case 1:
                dialogue = new Dialogue() {

                    @Override
                    public DialogueType type() {
                        return DialogueType.NPC_STATEMENT;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return DialogueExpression.NORMAL;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[]{"You can earn money doing many different things in", "PaeScape. For example, see those Thieving stalls infront of ", "you? You can steal items from them and sell them to the", "merchant whose standing over there."};
                    }

                    @Override
                    public int npcId() {
                        return 6139;
                    }

                    @Override
                    public void specialAction() {
                        p.moveTo(new Position(3099, 3501));
                    }

                    @Override
                    public Dialogue nextDialogue() {
                        return get(p, stage + 1);
                    }
                };
                break;
            case 2:
                dialogue = new Dialogue() {

                    @Override
                    public DialogueType type() {
                        return DialogueType.NPC_STATEMENT;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return DialogueExpression.NORMAL;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[]{"You can also sell items to the General store."};
                    }

                    @Override
                    public int npcId() {
                        return 6139;
                    }

                    @Override
                    public void specialAction() {
                        p.moveTo(new Position(3082, 3511));
                    }

                    @Override
                    public Dialogue nextDialogue() {
                        return get(p, stage + 1);
                    }
                };
                break;
            case 3:
                dialogue = new Dialogue() {

                    @Override
                    public DialogueType type() {
                        return DialogueType.NPC_STATEMENT;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return DialogueExpression.CONFUSED;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[]{"... Or using the Grand Exchange."};
                    }

                    @Override
                    public int npcId() {
                        return 6139;
                    }

                    @Override
                    public void specialAction() {
                        p.moveTo(new Position(3092, 3495));
                        p.setDirection(Direction.WEST);
                    }

                    @Override
                    public Dialogue nextDialogue() {
                        return get(p, stage + 1);
                    }
                };
                break;
            case 4:
                dialogue = new Dialogue() {

                    @Override
                    public DialogueType type() {
                        return DialogueType.NPC_STATEMENT;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return DialogueExpression.NORMAL;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[]{"The next important thing you need to learn is navigating.", "All important teleports can be found at the top of the", "Spellbook. Take a look, I've opened it for you!"};
                    }

                    @Override
                    public int npcId() {
                        return 6139;
                    }

                    @Override
                    public void specialAction() {
                        p.moveTo(GameSettings.DEFAULT_POSITION.copy());
                        p.setDirection(Direction.SOUTH);
                        p.getPacketSender().sendTab(GameSettings.MAGIC_TAB);
                    }

                    @Override
                    public Dialogue nextDialogue() {
                        return get(p, stage + 1);
                    }
                };
                break;
            case 5:
                dialogue = new Dialogue() {

                    @Override
                    public DialogueType type() {
                        return DialogueType.NPC_STATEMENT;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return DialogueExpression.NORMAL;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[]{"If you wish to navigate to a skill's training location,", "simply press the on the respective skill in the skill tab."};
                    }

                    @Override
                    public int npcId() {
                        return 6139;
                    }

                    @Override
                    public void specialAction() {
                        p.getPacketSender().sendTab(GameSettings.SKILLS_TAB);
                    }

                    @Override
                    public Dialogue nextDialogue() {
                        return get(p, stage + 1);
                    }
                };
                break;
            case 6:
                dialogue = new Dialogue() {

                    @Override
                    public DialogueType type() {
                        return DialogueType.NPC_STATEMENT;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return DialogueExpression.NORMAL;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[]{"Enough of the boring stuff, let's show you some creatures!", "There are a bunch of bosses to fight in PaeScape.", "Every boss drops unique and good gear when killed.", "One example is the mighty Pohenix!"};
                    }

                    @Override
                    public int npcId() {
                        return 6139;
                    }

                    @Override
                    public void specialAction() {
                        p.moveTo(new Position(2833, 9560));
                    }

                    @Override
                    public Dialogue nextDialogue() {
                        return get(p, stage + 1);
                    }
                };
                break;
            case 7:
                dialogue = new Dialogue() {

                    @Override
                    public DialogueType type() {
                        return DialogueType.NPC_STATEMENT;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return DialogueExpression.NORMAL;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[]{"Ah.. The Ghost Town..", "Here, you can find a bunch of revenants.", "You can also fight other players."};
                    }

                    @Override
                    public int npcId() {
                        return 6139;
                    }

                    @Override
                    public void specialAction() {
                        p.moveTo(new Position(3666, 3486));
                    }

                    @Override
                    public Dialogue nextDialogue() {
                        return get(p, stage + 1);
                    }
                };
                break;
            case 8:
                dialogue = new Dialogue() {

                    @Override
                    public DialogueType type() {
                        return DialogueType.NPC_STATEMENT;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return DialogueExpression.NORMAL;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[]{"PaeScape also has a lot of enjoyable minigames.", "This is the Graveyard Arena, an area that's been run over", "by Zombies. Your job is to simply to kill them all.", "Sounds like fun, don't you think?"};
                    }

                    @Override
                    public int npcId() {
                        return 6139;
                    }

                    @Override
                    public void specialAction() {
                        p.moveTo(new Position(3503, 3569));
                    }

                    @Override
                    public Dialogue nextDialogue() {
                        return get(p, stage + 1);
                    }
                };
                break;
            case 9:
                dialogue = new Dialogue() {

                    @Override
                    public DialogueType type() {
                        return DialogueType.NPC_STATEMENT;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return DialogueExpression.NORMAL;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[]{"This is the member's zone.", "Players who have a Member rank can teleport here", "and take advantage of the resources that it has."};
                    }

                    @Override
                    public int npcId() {
                        return 6139;
                    }

                    @Override
                    public void specialAction() {
                        p.moveTo(new Position(2337, 9803));
                    }

                    @Override
                    public Dialogue nextDialogue() {
                        return get(p, stage + 1);
                    }
                };
                break;
            case 10:
                dialogue = new Dialogue() {

                    @Override
                    public DialogueType type() {
                        return DialogueType.NPC_STATEMENT;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return DialogueExpression.NORMAL;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[]{"To receive a member rank, you'd need to claim", "scrolls worth at least $20 (for the Donator rank).", "Scrolls and other items can be purchased from the store", "which can be opened using the ::store command."};
                    }

                    @Override
                    public int npcId() {
                        return 6139;
                    }

                    @Override
                    public void specialAction() {
                        p.moveTo(new Position(3423, 2914));
                    }

                    @Override
                    public Dialogue nextDialogue() {
                        return get(p, stage + 1);
                    }
                };
                break;
            case 11:
                dialogue = new Dialogue() {

                    @Override
                    public DialogueType type() {
                        return DialogueType.NPC_STATEMENT;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return DialogueExpression.NORMAL;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[]{"PaeScape is a competitive game. Next to you is a scoreboard", "which you can use to track other players and their progress."};
                    }

                    @Override
                    public int npcId() {
                        return 6139;
                    }

                    @Override
                    public void specialAction() {
                        p.moveTo(new Position(3086, 3510));
                        p.setDirection(Direction.WEST);
                    }

                    @Override
                    public Dialogue nextDialogue() {
                        return get(p, stage + 1);
                    }
                };
                break;
            case 12:
                dialogue = new Dialogue() {

                    @Override
                    public DialogueType type() {
                        return DialogueType.NPC_STATEMENT;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return DialogueExpression.NORMAL;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[]{"That was almost all.", "I just want to remind you to vote for us on various", "gaming toplists. To do so, simply use the ::vote command.", "You will be rewarded!"};
                    }

                    @Override
                    public int npcId() {
                        return 6139;
                    }

                    @Override
                    public void specialAction() {
                        p.moveTo(GameSettings.DEFAULT_POSITION.copy());
                        p.setDirection(Direction.SOUTH);
                    }

                    @Override
                    public Dialogue nextDialogue() {
                        return get(p, stage + 1);
                    }
                };
                break;
            case 13:
                dialogue = new Dialogue() {

                    @Override
                    public DialogueType type() {
                        return DialogueType.NPC_STATEMENT;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return DialogueExpression.NORMAL;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[]{"If you have any more questions, simply use the ::help", "command and a staff member should get back to you.", "You can also join the clanchat channel 'help' and ask", "other players for help there too. Have fun playing PaeScape!"};
                    }

                    @Override
                    public int npcId() {
                        return 6139;
                    }

                    @Override
                    public Dialogue nextDialogue() {
                        return get(p, stage + 1);
                    }
                };
                break;
            case 14:
                dialogue = new Dialogue() {

                    @Override
                    public DialogueType type() {
                        return DialogueType.NPC_STATEMENT;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return DialogueExpression.NORMAL;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[]{"If you have any more questions, simply use the ::help", "command and a staff member should get back to you.", "You can also join the clanchat channel 'help' and ask", "other players for help there too. Have fun playing PaeScape!"};
                    }

                    @Override
                    public int npcId() {
                        return 6139;
                    }

                    @Override
                    public void specialAction() {
                        p.setNewPlayer(false);
                        if (ConnectionHandler.getStarters(p.getHostAddress()) <= GameSettings.MAX_STARTERS_PER_IP) {
                            if (p.getGameMode() != GameMode.NORMAL) {
                                p.getInventory().add(995, 10000).add(1153, 1).add(1115, 1).add(1067, 1).add(1323, 1).add(1191, 1).add(841, 1).add(884, 50).add(1167, 1).add(1129, 1).add(1095, 1).add(1063, 1).add(579, 1).add(577, 1).add(1011, 1).add(1379, 1).add(556, 50).add(558, 50).add(557, 50).add(555, 50).add(1351, 1).add(1265, 1).add(1712, 1).add(11118, 1).add(1007, 1).add(1061, 1).add(330, 100).add(1419, 1);
                            } else {
                                p.getInventory().add(995, 5000000).add(1153, 1).add(1115, 1).add(1067, 1).add(1323, 1).add(1191, 1).add(841, 1).add(884, 1000).add(1167, 1).add(1129, 1).add(1095, 1).add(1063, 1).add(579, 1).add(577, 1).add(1011, 1).add(1379, 1).add(556, 1000).add(558, 1000).add(557, 1000).add(555, 1000).add(1351, 1).add(1265, 1).add(1712, 1).add(11118, 1).add(1007, 1).add(1061, 1).add(386, 100).add(1419, 1);
                            }
                            p.getPacketSender().sendMessage("<col=FF0066>You've been given a Scythe! It is untradeable and you will keep it on death.");
                            ConnectionHandler.addStarter(p.getHostAddress(), true);
                            p.setReceivedStarter(true); //Incase they want to change game mode, regives starter items
                        } else {
                            p.getPacketSender().sendMessage("Your connection has received enough starting items.");
                        }
                        p.getPacketSender().sendInterface(3559);
                        p.getAppearance().setCanChangeAppearance(true);
                        p.setPlayerLocked(false);
                        ClanChatManager.join(p, "help");
                        World.sendMessage("", "<img=10> <col=6600CC>[New Player]: " + p.getUsername() + " has logged into PaeScape for the first time!");
                        p.sendMessage("Click on any skill to access teleports specific to that skill.");
                        p.sendMessage("Each kill you will receive coins equal to the NPC's level squared.");

                        //reset interface?
                        //resetInterface(player);
                        TaskManager.submit(new Task(20, p, false) {
                            @Override
                            protected void execute() {
                                if (p != null && p.isRegistered()) {
                                    p.getPacketSender().sendMessage("<img=10> @blu@Want to go player killing? Mandrith now sells premade PvP sets.");
                                }
                                stop();
                            }
                        });
                        p.save();
                    }

                    @Override
                    public Dialogue nextDialogue() {
                        return get(p, stage + 1);
                    }
                };
                break;
        }
        return dialogue;
    }


}