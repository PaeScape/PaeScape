package com.arlania.world.content.skill.impl.construction;

import com.arlania.GameSettings;
import com.arlania.model.Animation;
import com.arlania.model.Position;
import com.arlania.model.Skill;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.world.content.dialogue.Dialogue;
import com.arlania.world.content.dialogue.DialogueExpression;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.content.dialogue.DialogueType;
import com.arlania.world.content.skill.impl.construction.ConstructionData.Furniture;
import com.arlania.world.content.skill.impl.prayer.BonesOnAltar;
import com.arlania.world.content.skill.impl.prayer.Prayer;
import com.arlania.world.content.transportation.TeleportHandler;
import com.arlania.world.content.transportation.TeleportType;
import com.arlania.world.entity.impl.player.Player;

import java.util.Calendar;

/**
 * @author Keagan
 */
public class ConstructionActions {

    /*
     * DIALOGUE ACTION IDS WOODEN LARDER: 700 (2) OAK LOARDER: 701 (4) TEAK
     * LARDER: 702 (4) 703 (4) 704 (2)
     */

    public static boolean handleFirstObjectClick(Player p, int objectId) {

        if (handleLarder(p, objectId))
            return true;

        if (handleShelves(p, objectId))
            return true;

        switch (objectId) {
            case 15477:
                DialogueManager.start(p, ConstructionDialogues.mainPortalDialogue());
                p.setDialogueActionId(442);
                break;
            case 13405:
                p.getRegionInstance().remove(p);
                TeleportHandler.teleportPlayer(p, GameSettings.DEFAULT_POSITION.copy(), TeleportType.NORMAL);
                break;
            // Bookcases
            case 13599:
            case 13598:
            case 13597:
                DialogueManager.start(p, 203);
                break;

            // Wardrobes and dressers
            case 13162:
            case 13163:
            case 13164:
            case 13165:
            case 13166:
            case 13167:
            case 13168:
            case 13155:
            case 13156:
            case 13157:
            case 13158:
            case 13159:
            case 13160:
            case 13161:
                p.getPacketSender().sendInterface(3559);
                p.getAppearance().setCanChangeAppearance(true);
                break;
            // Clocks
            case 13169:
            case 13170:
            case 13171:
                p.getPacketSender()
                        .sendMessage("DING DONG DING DONG!")
                        .sendMessage("IT'S TIME TO EARN XP!")
                        .sendMessage(
                                "And it's also " + Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" + Calendar.getInstance().get(Calendar.MINUTE) + ":" + Calendar.getInstance().get(Calendar.SECOND));
                break;
            // Altars
            case 13179:
            case 13182:
            case 13185:
            case 13188:
            case 13191:
            case 13194:
            case 13197:
                p.performAnimation(new Animation(645));
                if (p.getSkillManager().getCurrentLevel(Skill.PRAYER) < p.getSkillManager().getMaxLevel(Skill.PRAYER)) {
                    p.getSkillManager().setCurrentLevel(Skill.PRAYER, p.getSkillManager().getMaxLevel(Skill.PRAYER), true);
                    p.getPacketSender().sendMessage("You recharge your Prayer points.");
                }
                break;
            case 13640:
                DialogueManager.start(p, ConstructionDialogues.redirectPortalsDialogue());
                p.setDialogueActionId(645);
                break;

            // Varrock portal
            case 13615:
            case 13622:
            case 13629:
                if (TeleportHandler.checkReqs(p, new Position(ConstructionConstants.VARROCK_X, ConstructionConstants.VARROCK_Y, 0))) {
                    p.getRegionInstance().remove(p);
                    TeleportHandler.teleportPlayer(p, new Position(ConstructionConstants.VARROCK_X, ConstructionConstants.VARROCK_Y, 0), TeleportType.NORMAL);
                }
                break;

            // Lumbridge portal
            case 13616:
            case 13623:
            case 13630:
                if (TeleportHandler.checkReqs(p, new Position(ConstructionConstants.LUMBY_X, ConstructionConstants.LUMBY_Y, 0))) {
                    p.getRegionInstance().remove(p);
                    TeleportHandler.teleportPlayer(p, new Position(ConstructionConstants.LUMBY_X, ConstructionConstants.LUMBY_Y, 0), TeleportType.NORMAL);
                }
                break;

            // Falador portal
            case 13617:
            case 13624:
            case 13631:
                if (TeleportHandler.checkReqs(p, new Position(ConstructionConstants.FALADOR_X, ConstructionConstants.FALADOR_Y, 0))) {
                    p.getRegionInstance().remove(p);
                    TeleportHandler.teleportPlayer(p, new Position(ConstructionConstants.FALADOR_X, ConstructionConstants.FALADOR_Y, 0), TeleportType.NORMAL);
                }
                break;

            // Camelot portal
            case 13618:
            case 13625:
            case 13632:
                if (TeleportHandler.checkReqs(p, new Position(ConstructionConstants.CAMELOT_X, ConstructionConstants.CAMELOT_Y, 0))) {
                    p.getRegionInstance().remove(p);
                    TeleportHandler.teleportPlayer(p, new Position(ConstructionConstants.CAMELOT_X, ConstructionConstants.CAMELOT_Y, 0), TeleportType.NORMAL);
                }
                break;

            // Ardougne portal
            case 13619:
            case 13626:
            case 13633:
                if (TeleportHandler.checkReqs(p, new Position(ConstructionConstants.ARDOUGNE_X, ConstructionConstants.ARDOUGNE_Y, 0))) {
                    p.getRegionInstance().remove(p);
                    TeleportHandler.teleportPlayer(p, new Position(ConstructionConstants.ARDOUGNE_X, ConstructionConstants.ARDOUGNE_Y, 0), TeleportType.NORMAL);
                }
                break;

            // Yanille portal
            case 13620:
            case 13627:
            case 13634:
                if (TeleportHandler.checkReqs(p, ConstructionConstants.YANILLE)) {
                    p.getRegionInstance().remove(p);
                    TeleportHandler.teleportPlayer(p, ConstructionConstants.YANILLE, TeleportType.NORMAL);
                }
                break;

            // Kharyrll portal
            case 13621:
            case 13628:
            case 13635:
                if (TeleportHandler.checkReqs(p, new Position(ConstructionConstants.KHARYRLL_X, ConstructionConstants.KHARYRLL_Y, 0))) {
                    p.getRegionInstance().remove(p);
                    TeleportHandler.teleportPlayer(p, new Position(ConstructionConstants.KHARYRLL_X, ConstructionConstants.KHARYRLL_Y, 0), TeleportType.NORMAL);
                }
                break;

        }

        return false;
    }

    public static boolean handleItemOnObject(Player p, int objectId, int item) {

        if (handleBarrel(p, objectId, item))
            return true;

        switch (objectId) {

            // Altars
            case 13179:
            case 13182:
            case 13185:
            case 13188:
            case 13191:
            case 13194:
            case 13197:
                if (Prayer.isBone(item))
                    BonesOnAltar.openInterface(p, item);
                break;

        }

        return false;
    }

    /*
     * BEER ANIM: 1327 GFX: TAP ANIM: 9044 / 9045
     */
    private static boolean handleBarrel(Player p, int objectId, int item) {
        if (item != 7742 || !p.getInventory().contains(item))
            return false;

        for (int i = 0; i < BARRELS.length; i++) {
            if (objectId == BARRELS[i][0]) {
                p.getInventory().delete(item, 1).add(BARRELS[i][1], 1);
                p.getPacketSender().sendMessage("You pour yourself a glass of " + ItemDefinition.forId(BARRELS[i][1]).getName() + ".");
                p.performAnimation(new Animation(9044));
                return true;
            }
        }
        return false;
    }

    private static boolean handleLarder(Player p, int objectId) {
        if (objectId == Furniture.WOODEN_LARDER.getFurnitureId()) {
            p.setDialogueActionId(700);
            DialogueManager.start(p, new Dialogue() {

                @Override
                public DialogueType type() {
                    return DialogueType.OPTION;
                }

                @Override
                public DialogueExpression animation() {
                    return null;
                }

                @Override
                public String[] dialogue() {
                    return new String[]{"Tea leaves", "Bucket of milk"};
                }
            });
            return true;
        } else if (objectId == Furniture.OAK_LARDER.getFurnitureId()) {
            p.setDialogueActionId(701);
            DialogueManager.start(p, new Dialogue() {

                @Override
                public DialogueType type() {
                    return DialogueType.OPTION;
                }

                @Override
                public DialogueExpression animation() {
                    return null;
                }

                @Override
                public String[] dialogue() {
                    return new String[]{"Tea leaves", "Milk", "Eggs", "Flour"};
                }
            });
            return true;
        } else if (objectId == Furniture.TEAK_LARDER.getFurnitureId()) {
            p.setDialogueActionId(702);
            DialogueManager.start(p, new Dialogue() {

                @Override
                public DialogueType type() {
                    return DialogueType.OPTION;
                }

                @Override
                public DialogueExpression animation() {
                    return null;
                }

                @Override
                public String[] dialogue() {
                    return new String[]{"Tea leaves", "Milk", "Eggs", "More..."};
                }

                @Override
                public Dialogue nextDialogue() {
                    p.setDialogueActionId(703);
                    return new Dialogue() {

                        @Override
                        public DialogueType type() {
                            return DialogueType.OPTION;
                        }

                        @Override
                        public DialogueExpression animation() {
                            return null;
                        }

                        @Override
                        public String[] dialogue() {
                            return new String[]{"Flour", "Potatoes", "Garlic", "More..."};
                        }

                        @Override
                        public Dialogue nextDialogue() {
                            p.setDialogueActionId(704);
                            return new Dialogue() {

                                @Override
                                public DialogueType type() {
                                    return DialogueType.OPTION;
                                }

                                @Override
                                public DialogueExpression animation() {
                                    return null;
                                }

                                @Override
                                public String[] dialogue() {
                                    return new String[]{"Onions", "Cheese"};
                                }
                            };
                        }
                    };
                }
            });
            return true;
        }

        return false;
    }

    private static boolean handleShelves(Player p, int objectId) {
        Object[] result = null;

        for (int i = 0; i < SHELVES.length; i++) {
            if ((int) SHELVES[i][0] == objectId)
                result = SHELVES[i];
        }
        if (result == null)
            return false;
        final String[] lines = (String[]) result[2];
        final String[] lines2 = result.length == 4 ? (String[]) result[3] : null;
        p.setDialogueActionId((int) result[1]);
        DialogueManager.start(p, new Dialogue() {

            @Override
            public DialogueType type() {
                return DialogueType.OPTION;
            }

            @Override
            public DialogueExpression animation() {
                return null;
            }

            @Override
            public String[] dialogue() {

                return lines;
            }

            @Override
            public Dialogue nextDialogue() {
                if (lines2 == null)
                    return null;

                return new Dialogue() {

                    @Override
                    public DialogueType type() {
                        return DialogueType.OPTION;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return null;
                    }

                    @Override
                    public String[] dialogue() {
                        return lines2;
                    }

                };
            }

        });

        return true;
    }

    private static final int[][] BARRELS = {{13568, 7740}, {13569, 7752}, {13570, 7744}, {13571, 7746}, {13572, 7748}, {13573, 7754}};
    private static final Object[][] SHELVES = {
            {Furniture.WOODEN_SHELVES_1.getFurnitureId(), 705, new String[]{"Kettle", "Teapot", "Cup"}},
            {Furniture.WOODEN_SHELVES_2.getFurnitureId(), 706, new String[]{"Kettle", "Teapot", "Cup", "Beer glass"}},
            {Furniture.WOODEN_SHELVES_3.getFurnitureId(), 707, new String[]{"Kettle", "Teapot", "Porcelain cup", "Beer glass", "Cake tin"}},
            {Furniture.OAK_SHELVES_1.getFurnitureId(), 708, new String[]{"Kettle", "Teapot", "Cup", "Beer glass", "Bowl"}},
            {Furniture.OAK_SHELVES_2.getFurnitureId(), 709, new String[]{"Kettle", "Teapot", "Porcelain cup", "Beer glass", "More..."}, new String[]{"Bowl", "Cake tin"}},
            {Furniture.TEAK_SHELVES_1.getFurnitureId(), 710, new String[]{"Kettle", "Teapot", "Porcelain cup", "Beer glass", "More..."}, new String[]{"Bowl", "Pie dish", "Pot", "Chef's hat"}},
            {Furniture.TEAK_SHELVES_2.getFurnitureId(), 711, new String[]{"Kettle", "Teapot", "Porcelain cup", "Beer glass", "More..."}, new String[]{"Bowl", "Pie dish", "Pot", "Chef's hat"}}};

}
