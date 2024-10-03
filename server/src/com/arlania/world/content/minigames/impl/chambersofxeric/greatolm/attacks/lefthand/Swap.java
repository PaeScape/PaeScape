package com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.attacks.lefthand;

import com.arlania.GameSettings;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.*;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.GreatOlm;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.OlmAnimations;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.attacks.Attacks;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.entity.impl.player.Player;

public class Swap {

    public static void performAttack(RaidsParty party, int height) {
        party.setLeftHandAttackTimer(20);

        TaskManager.submit(new Task(1, party, true) {
            int tick = 0;

            @Override
            public void execute() {
                if (party.isLeftHandDead()) {
                    stop();
                }
                if (tick == 1) {
                    party.getLeftHandObject().performAnimation(OlmAnimations.flashingCirclesLeftHand);

                    int playersToChoose = 0;
                    int graphic = Attacks.WHITE_CIRCLE;
                    for (Player member : party.getPlayers()) {
                        if (member != null && member.getMinigameAttributes().getRaidsAttributes().isInsideRaid() && GreatOlm.insideChamber(member)) {
                            if (!party.getSwapPlayers().contains(member)) {
                                party.getSwapPlayers().add(member);
                                playersToChoose++;
                                member.setGraphic(new Graphic(graphic, GraphicHeight.LOW));
                                member.setGraphicSwap(graphic);
                                if (playersToChoose % 2 == 0) {
                                    graphic++;
                                }
                                party.setLonePair(false);

                            }
                        }
                    }

                    if (playersToChoose == 1 || playersToChoose == 3 || playersToChoose == 5) {
                        party.setSwapPosition(Attacks.randomLocation(height));
                        party.setGraphicSwap(graphic);
                        party.getOwner().getPacketSender().sendGlobalGraphic(
                                new Graphic(graphic, GraphicHeight.LOW), party.getSwapPosition());
                        party.setLonePair(true);
                    }

                    for (int i = 0; i < 6; i += 2) {
                        if (i == 0 && party.getSwapPlayers().size() >= 2
                                || i > 0 && party.getSwapPlayers().size() >= (i * 2)) {
                            party.getSwapPlayers().get(i)
                                    .sendMessage("You have been paired with @red@"
                                            + party.getSwapPlayers().get(i + 1).getUsername()
                                            + "</col>! The magical power will enact soon...");
                        } else {
                            if (party.getSwapPlayers().size() - 1 >= i)
                                party.getSwapPlayers().get(i).sendMessage(
                                        "The Great Olm had no one to pair you with! The magical power will enact soon...");
                        }
                    }
                    for (int i = 1; i < 6; i += 2) {
                        if (i == 1 && party.getSwapPlayers().size() >= 2
                                || i > 1 && party.getSwapPlayers().size() >= (i * 2)) {
                            party.getSwapPlayers().get(i)
                                    .sendMessage("You have been paired with @red@"
                                            + party.getSwapPlayers().get(i - 1).getUsername()
                                            + "</col>! The magical power will enact soon...");
                        } else {
                            if (party.getSwapPlayers().size() - 1 >= i)
                                party.getSwapPlayers().get(i).sendMessage(
                                        "The Great Olm had no one to pair you with! The magical power will enact soon...");
                        }
                    }
                }
                if (tick == 4) {
                    for (Player member : party.getPlayers()) {
                        if (member != null && member.getMinigameAttributes().getRaidsAttributes().isInsideRaid() && GreatOlm.insideChamber(member)) {
                            member.setGraphic(new Graphic(member.getGraphicSwap(), GraphicHeight.LOW));
                        }
                    }
                    if (party.isLonePair())
                        party.getOwner().getPacketSender().sendGlobalGraphic(
                                new Graphic(party.getGraphicSwap(), GraphicHeight.LOW), party.getSwapPosition());
                }
                if (tick == 7) {
                    for (Player member : party.getPlayers()) {
                        if (member != null && member.getMinigameAttributes().getRaidsAttributes().isInsideRaid() && GreatOlm.insideChamber(member)) {
                            member.setGraphic(new Graphic(member.getGraphicSwap(), GraphicHeight.LOW));
                        }
                    }
                    if (party.isLonePair())
                        party.getOwner().getPacketSender().sendGlobalGraphic(
                                new Graphic(party.getGraphicSwap(), GraphicHeight.LOW), party.getSwapPosition());
                }
                if (tick == 9) {
                    for (int i = 0; i < 6; i += 2) {
                        if (i == 0 && party.getSwapPlayers().size() >= 2
                                || i > 0 && party.getSwapPlayers().size() >= (i * 2)) {
                            Position firstPlayer = party.getSwapPlayers().get(i).getPosition();
                            Position secondPlayer = party.getSwapPlayers().get(i + 1).getPosition();
                            hitPlayerSwap(party, firstPlayer, secondPlayer, i, true);
                        } else {
                            if (party.getSwapPlayers().size() - 1 >= i) {
                                Position firstPlayer = party.getSwapPlayers().get(i).getPosition();
                                Position secondPlayer = party.getSwapPosition();
                                hitPlayerSwap(party, firstPlayer, secondPlayer, i, false);
                            }
                        }
                    }
                    party.getLeftHandObject().performAnimation(OlmAnimations.leftHand);
                    party.getSwapPlayers().clear();

                    stop();
                }
                tick++;
            }
        });

    }

    public static void hitPlayerSwap(RaidsParty party, Position firstPlayer, Position secondPlayer, int i,
                                     boolean foundPair) {
        if (party.getSwapPlayers().get(i).isDying() || party.getSwapPlayers().get(i).getSkillManager().getCurrentLevel(Skill.CONSTITUTION) < 1) {
            return;
        }
        if (foundPair) {
            if (party.getSwapPlayers().get(i).getPosition() != party.getSwapPlayers().get(i + 1).getPosition()) {
                if (!party.getSwapPlayers().get(i).getMinigameAttributes().getRaidsAttributes().isInsideRaid() || !party.getSwapPlayers().get(i + 1).getMinigameAttributes().getRaidsAttributes().isInsideRaid()) {
                    return;
                }
                party.getSwapPlayers().get(i).moveTo(secondPlayer);
                party.getSwapPlayers().get(i + 1).moveTo(firstPlayer);
                if (!(firstPlayer.equals(secondPlayer))) {
                    party.getSwapPlayers().get(i).sendMessage("Yourself and "
                            + party.getSwapPlayers().get(i + 1).getUsername() + " have swapped places!");

                    party.getSwapPlayers().get(i + 1).sendMessage(
                            "Yourself and " + party.getSwapPlayers().get(i).getUsername() + " have swapped places!");

                    if (Locations.goodDistance(firstPlayer, secondPlayer, 1)) {
                        Attacks.hitPlayer(party.getSwapPlayers().get(i), 100, 100, CombatType
                                        .MAGIC, CombatIcon.NONE, 1,
                                false);
                        Attacks.hitPlayer(party.getSwapPlayers().get(i + 1), 100, 100, CombatType.MAGIC,
                                CombatIcon.NONE, 1, false);
                    } else if (Locations.goodDistance(firstPlayer, secondPlayer, 2)) {
                        Attacks.hitPlayer(party.getSwapPlayers().get(i), 100, 200, CombatType.MAGIC, CombatIcon.NONE, 1,
                                false);
                        Attacks.hitPlayer(party.getSwapPlayers().get(i + 1), 100, 200, CombatType.MAGIC,
                                CombatIcon.NONE, 1, false);
                    } else {
                        Attacks.hitPlayer(party.getSwapPlayers().get(i), 200, 330, CombatType.MAGIC, CombatIcon.NONE, 1,
                                false);
                        Attacks.hitPlayer(party.getSwapPlayers().get(i + 1), 200, 330, CombatType.MAGIC,
                                CombatIcon.NONE, 1, false);
                    }
                    party.getSwapPlayers().get(i + 1).setGraphic(new Graphic(1039 + GameSettings.OSRS_GFX_OFFSET, GraphicHeight.LOW));
                    party.getSwapPlayers().get(i).setGraphic(new Graphic(1039 + GameSettings.OSRS_GFX_OFFSET, GraphicHeight.LOW));

                } else {
                    party.getSwapPlayers().get(i).sendMessage("The teleport attack has no effect!");
                    party.getSwapPlayers().get(i + 1).sendMessage("The teleport attack has no effect!");
                }
            }
        } else {
            if (party.getSwapPlayers().get(i).getPosition() != party.getSwapPosition()) {
                party.getSwapPlayers().get(i).moveTo(party.getSwapPosition());
                if (!(firstPlayer.equals(party.getSwapPosition()))) {
                    party.getSwapPlayers().get(i)
                            .sendMessage("As you had no pairing... you are taken to a random spot.");
                    if (Locations.goodDistance(firstPlayer, party.getSwapPosition(), 1)) {
                        Attacks.hitPlayer(party.getSwapPlayers().get(i), 100, 100, CombatType.MAGIC, CombatIcon.NONE, 1,
                                false);
                    } else if (Locations.goodDistance(firstPlayer, party.getSwapPosition(), 2)) {
                        Attacks.hitPlayer(party.getSwapPlayers().get(i), 100, 200, CombatType.MAGIC, CombatIcon.NONE, 1,
                                false);
                    } else {
                        Attacks.hitPlayer(party.getSwapPlayers().get(i), 200, 330, CombatType.MAGIC, CombatIcon.NONE, 1,
                                false);
                    }
                    party.getSwapPlayers().get(i).setGraphic(new Graphic(1039 + GameSettings.OSRS_GFX_OFFSET, GraphicHeight.LOW));

                } else {
                    party.getSwapPlayers().get(i).sendMessage("The teleport attack has no effect!");
                }
            }
        }
    }

}
