package com.arlania.world.content.holiday;

import com.arlania.model.GroundItem;
import com.arlania.model.Item;
import com.arlania.model.Position;
import com.arlania.world.content.dialogue.Dialogue;
import com.arlania.world.content.dialogue.DialogueExpression;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.content.dialogue.DialogueType;
import com.arlania.world.entity.impl.GroundItemManager;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class Halloween23 {

    //talk to rag and bone man (first Dialogue)
    //rag and bone man gives you a quest to retrieve some items
    //talk to rag and bone man when you have all of the items
    //receive pet

    public static void oddOldMan(Player player) {

        boolean allBones = player.getInventory().contains(207821) && player.getInventory().contains(207854) && player.getInventory().contains(207827) && player.getInventory().contains(207812);

        if(!player.halloween23 && allBones)
            DialogueManager.start(player, Halloween23.finalDialogue(player));
        else if (!player.halloween23 && !player.getInventory().contains(207144))
            DialogueManager.start(player, Halloween23.firstDialogue(player));
        else if (!player.halloween23) {
            player.getPacketSender().sendMessage("Read the book that the Odd Old man gave you.");
        } else {
            player.getPacketSender().sendMessage("You've already completed the event!");
        }

    }

    public static void readBook(Player player) {

        boolean allBones = player.getInventory().contains(207821) && player.getInventory().contains(207854) && player.getInventory().contains(207827) && player.getInventory().contains(207812);

        player.getPacketSender().sendInterface(8119); //interface
        player.getPacketSender().sendString(8129, "Close");

        player.getPacketSender().sendString(8121, "Odd Old Man's List");
        player.getPacketSender().sendString(8122, "");
        player.getPacketSender().sendString(8123, (player.getInventory().contains(207821) ? "@str@" : "") + "Unicorn Bone (Edgeville)");
        player.getPacketSender().sendString(8124, (player.getInventory().contains(207854) ? "@str@" : "") + "Monkey Paw (Ape Atoll)");
        player.getPacketSender().sendString(8125, (player.getInventory().contains(207827) ? "@str@" : "") + "Giant Bat Wing (Taverly Dungeon)");
        player.getPacketSender().sendString(8126, (player.getInventory().contains(207812) ? "@str@" : "") + "Goblin Skull (Lumbridge)");
        player.getPacketSender().sendString(8127, "");
        player.getPacketSender().sendString(8128, (allBones ? "Return to the Odd Old Man to complete the event!" : ""));
        //https://oldschool.runescape.wiki/w/Rag_and_Bone_Man_I
    }

    public static void mobLoot(Player p, NPC npc) {

        Position pos = npc.getPosition();

        switch (npc.getId()) {

            case 16837: //unicorn
                GroundItemManager.spawnGroundItem(p, new GroundItem(new Item(207821, 1), pos, p.getUsername(), false, 150, true, 200));
                break;

            case 1471: //monkey skeletons
                GroundItemManager.spawnGroundItem(p, new GroundItem(new Item(207854, 1), pos, p.getUsername(), false, 150, true, 200));
                break;

            case 78: //Giant Bat
                GroundItemManager.spawnGroundItem(p, new GroundItem(new Item(207827, 1), pos, p.getUsername(), false, 150, true, 200));
                break;

            case 14655: //goblin
                GroundItemManager.spawnGroundItem(p, new GroundItem(new Item(207812, 1), pos, p.getUsername(), false, 150, true, 200));
                break;
        }
    }

    public static Dialogue firstDialogue(final Player player) {
        return new Dialogue() {

            @Override
            public DialogueType type() {
                return DialogueType.NPC_STATEMENT;
            }

            @Override
            public int npcId() {
                return 3670;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.TALK_SWING;
            }

            @Override
            public String[] dialogue() {
                return new String[]{
                        "Hello there lad,",
                        "I need a favor from you."
                };
            }

            @Override
            public void specialAction() {

            }


            @Override
            public Dialogue nextDialogue() {
                return new Dialogue() {
                    @Override
                    public DialogueType type() {
                        return DialogueType.NPC_STATEMENT;
                    }

                    @Override
                    public int npcId() {
                        return 3670;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return DialogueExpression.TALK_SWING;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[]{
                                "I need you to gather some",
                                "bones for me."
                        };
                    }

                    @Override
                    public void specialAction() {

                    }

                    @Override
                    public Dialogue nextDialogue() {
                        return new Dialogue() {
                            @Override
                            public DialogueType type() {
                                return DialogueType.NPC_STATEMENT;
                            }

                            @Override
                            public int npcId() {
                                return 3670;
                            }

                            @Override
                            public DialogueExpression animation() {
                                return DialogueExpression.TALK_SWING;
                            }

                            @Override
                            public String[] dialogue() {
                                return new String[]{
                                        "They are all written",
                                        "in this book.",
                                        "Please take it."
                                };
                            }

                            @Override
                            public void specialAction() {
                                if (player.getInventory().getFreeSlots() >= 1) {
                                    player.getInventory().add(207144, 1);
                                }

                            }

                        };
                    }
                };
            }
        };
    }

    public static Dialogue finalDialogue(final Player player) {
        return new Dialogue() {

            @Override
            public DialogueType type() {
                return DialogueType.NPC_STATEMENT;
            }

            @Override
            public int npcId() {
                return 3670;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.TALK_SWING;
            }

            @Override
            public String[] dialogue() {
                return new String[]{
                        "Thanks for grabbing those",
                        "bones for me..."
                };
            }

            @Override
            public void specialAction() {

            }
            @Override
            public Dialogue nextDialogue() {
                return new Dialogue() {
                    @Override
                    public DialogueType type() {
                        return DialogueType.NPC_STATEMENT;
                    }

                    @Override
                    public int npcId() {
                        return 3670;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return DialogueExpression.TALK_SWING;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[]{
                                "Here's some",
                                "rotten food..."
                        };
                    }

                    @Override
                    public void specialAction() {
                        player.halloween23 = true;
                        player.getInventory().delete(207821, 1);
                        player.getInventory().delete(207854, 1);
                        player.getInventory().delete(207827, 1);
                        player.getInventory().delete(207812, 1);
                        player.getInventory().delete(207144, 1);
                        player.getInventory().add(202959, 1);
                    }
                };
            }
        };
    }


}