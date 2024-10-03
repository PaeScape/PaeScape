package com.arlania.net.packet.impl;

import com.arlania.GameLoader;
import com.arlania.GameSettings;
import com.arlania.engine.task.TaskManager;
import com.arlania.engine.task.impl.NPCRespawnTask;
import com.arlania.engine.task.impl.WalkToTask;
import com.arlania.engine.task.impl.WalkToTask.FinalizedMovementTask;
import com.arlania.model.*;
import com.arlania.model.Locations.Location;
import com.arlania.model.container.impl.Shop.ShopManager;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.model.definitions.NpcDefinition;
import com.arlania.model.input.impl.DonateToKingdom;
import com.arlania.model.input.impl.GambleAmount;
import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketListener;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.*;
import com.arlania.world.content.combat.CombatFactory;
import com.arlania.world.content.combat.magic.CombatSpell;
import com.arlania.world.content.combat.magic.CombatSpells;
import com.arlania.world.content.combat.weapon.CombatSpecial;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.content.holiday.Halloween23;
import com.arlania.world.content.minigames.impl.strongholdraids.RaidFloorFour;
import com.arlania.world.content.minigames.impl.strongholdraids.RaidFloorOne;
import com.arlania.world.content.minigames.impl.strongholdraids.RaidFloorThree;
import com.arlania.world.content.minigames.impl.strongholdraids.RaidFloorTwo;
import com.arlania.world.content.skill.impl.crafting.Tanning;
import com.arlania.world.content.skill.impl.fishing.Fishing;
import com.arlania.world.content.skill.impl.hunter.PuroPuro;
import com.arlania.world.content.skill.impl.hunter.Spearing;
import com.arlania.world.content.skill.impl.runecrafting.DesoSpan;
import com.arlania.world.content.skill.impl.slayer.SlayerDialogues;
import com.arlania.world.content.skill.impl.slayer.SlayerMaster;
import com.arlania.world.content.skill.impl.slayer.SlayerMobs;
import com.arlania.world.content.skill.impl.slayer.SlayerTasks;
import com.arlania.world.content.skill.impl.summoning.BossPets;
import com.arlania.world.content.skill.impl.summoning.Summoning;
import com.arlania.world.content.skill.impl.summoning.SummoningData;
import com.arlania.world.content.skill.impl.thieving.Pickpocket;
import com.arlania.world.content.transportation.TeleportHandler;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.antibotting.actions.Action;
import com.arlania.world.entity.impl.player.antibotting.actions.ActionAttackNPC;
import com.arlania.world.entity.impl.player.antibotting.actions.ActionMagicOnNPC;
import com.arlania.world.entity.impl.player.antibotting.actions.ActionNPCOption;

public class NPCOptionPacketListener implements PacketListener {


    public static final int ATTACK_NPC = 72, FIRST_CLICK_OPCODE = 155, MAGE_NPC = 131, SECOND_CLICK_OPCODE = 17, THIRD_CLICK_OPCODE = 21, FOURTH_CLICK_OPCODE = 18;

    private static void firstClick(Player player, Packet packet) {
        int index = packet.readInt();
        if (index < 0 || index > World.getNpcs().capacity())
            return;
        final NPC npc = World.getNpcs().get(index);
        if (npc == null)
            return;
        player.setEntityInteraction(npc);
        if (player.getStaffRights() == StaffRights.DEVELOPER)
            player.getPacketSender().sendMessage("First click npc id: " + npc.getId());
        player.getActionTracker().offer(new ActionNPCOption(Action.ActionType.FIRST_NPC_OPTION, npc.getId()));
        if (BossPets.pickup(player, npc)) {
            player.getMovementQueue().reset();
            return;
        }
        npc.clickNpc(player, NpcClickType.FIRST_CLICK);

        player.removeAttribute("pickingUp");
        player.setWalkToTask(new WalkToTask(player, npc.getPosition(), npc.getSize(), new FinalizedMovementTask() {
            @Override
            public void execute() {
                if (SummoningData.beastOfBurden(npc.getId())) {
                    Summoning summoning = player.getSummoning();
                    if (summoning.getBeastOfBurden() != null && summoning.getFamiliar() != null && summoning.getFamiliar().getSummonNpc() != null && summoning.getFamiliar().getSummonNpc().getIndex() == npc.getIndex()) {
                        summoning.store();
                        player.getMovementQueue().reset();
                    } else {
                        player.getPacketSender().sendMessage("That familiar is not yours!");
                    }
                    return;
                }
                switch (npc.getId()) {

                    case 1972:
                        player.getPacketSender().sendMessage("Rasolo teleports you to the Strykewyrm dungeon.");
                        TeleportHandler.teleportPlayer(player, new Position(2738, 5081), player.getSpellbook().getTeleportType());
                        break;

                    case 2258:
                        player.getPacketSender().sendMessage("The Mage of Zamorak teleports you to Abyssal Sire.");
                        TeleportHandler.teleportPlayer(player, new Position(2970, 4770), player.getSpellbook().getTeleportType());
                        break;

                    case 22052: //Voidstar
                        if (player.getAchievementTracker().hasCompletedAll()) {
                            DialogueManager.start(player, 1038);
                            player.setDialogueActionId(1038);
                        }
                        else {
                            DialogueManager.start(player, 1036);
                        }
                    break;

                    case 17668: //King Vargas
                        DialogueManager.start(player, 1031);
                        break;

                    case 3670:
                        if (GameLoader.getMonth() == 10 && GameLoader.getYear() == 2023)
                            Halloween23.oddOldMan(player);
                        else
                            player.getPacketSender().sendMessage("The Odd Old Man ignores you...");
                        break;

                    case 2437: //Jarvald
                        if (npc.getPosition().getX() == 2620 && npc.getPosition().getY() == 3685)
                            TeleportHandler.teleportPlayer(player, new Position(2548, 3759), player.getSpellbook().getTeleportType());
                        else if (npc.getPosition().getX() == 2550 && npc.getPosition().getY() == 3759)
                            TeleportHandler.teleportPlayer(player, new Position(2620, 3686), player.getSpellbook().getTeleportType());
                        break;
                    case 553: //Aubury
                        npc.forceChat("Senventior Disthine Molenko!");
                        TeleportHandler.teleportPlayer(player, new Position(2911, 4832), player.getSpellbook().getTeleportType());
                        break;
                    case 2: //man
                        Pickpocket.pickpocketNPC(player, 2, npc);
                        break;
                    case 21: //hero
                        Pickpocket.pickpocketNPC(player, 21, npc);
                        break;
                    case 23: //ardougne knight
                        Pickpocket.pickpocketNPC(player, 23, npc);
                        break;
                    case 2234: //master farmer
                        Pickpocket.pickpocketNPC(player, 2234, npc);
                        break;
                    case 5604: //elfinlocks
                        Pickpocket.pickpocketNPC(player, 5604, npc);
                        break;
                    case 2595: //TzHaar-Pik
                        Pickpocket.pickpocketNPC(player, 2595, npc);
                        break;
                    case 23698: //vyrewatch
                        Pickpocket.pickpocketNPC(player, 23698, npc);
                        break;
                    case 5103:
                        Spearing.spearNPC(player, 5103, npc);
                        break;
                    case 5104:
                        Spearing.spearNPC(player, 5104, npc);
                        break;
                    case 5105:
                        Spearing.spearNPC(player, 5105, npc);
                        break;
                    case 21785:
                        Spearing.spearNPC(player, 21785, npc);
                        break;
                    case 28: //Zookeeper
                        //player.getPacketSender().sendMessage("@red@One day this Zookeeper will be used to reclaim pets!");
                        PetStorage.openPetShop(player);
                        //player.getPlayerOwnedPetsManager().openEditor();
                        break;

                    case 1408: //Waydar Group Ironman/Seasonal
                        DialogueManager.start(player, 310);
                        player.setDialogueActionId(310);
                        break;

                    case 4904:
                        DialogueManager.start(player, 1200);
                        player.setDialogueActionId(1200);
                        break;

                    case 731:
                        ShopManager.getShops().get(99).open(player);
                        break;
                    case 251:
                        ShopManager.getShops().get(93).open(player);
                        break;
                    case 1152:
                        ShopManager.getShops().get(29).open(player);
                        break;
                    case 212:
                        ShopManager.getShops().get(48).open(player);
                        break;
                    case 4559:
                        ShopManager.getShops().get(46).open(player);
                        break;
                    case 3797:
                        ShopManager.getShops().get(69).open(player);
                        break;
                    case 3192:
                        PlayerPanel.refreshPanel(player);
                        player.getPacketSender().sendInterface(3200);
                        break;
                    case 1552:
                        DialogueManager.start(player, 138);
                        player.setDialogueActionId(77);
                        break;
                    case 8540:
                        if (!player.christmas2021start) {
                            DialogueManager.start(player, 192);
                            player.christmas2021start = true;
                        } else if (player.christmas2021start && !player.christmas2021complete && player.getInventory().getAmount(15420) < 4) {
                            DialogueManager.start(player, 195);
                        } else if (player.christmas2021start && !player.christmas2021complete && player.getInventory().getAmount(15420) >= 4) {
                            player.christmas2021complete = true;
                            player.getInventory().delete(15420, 4);
                            player.getInventory().add(GameSettings.CHRISTMAS_EVENT[0], 1);
                            player.getInventory().add(GameSettings.CHRISTMAS_EVENT[1], 1);
                            player.getInventory().add(GameSettings.CHRISTMAS_EVENT[2], 1);
                            player.getInventory().add(GameSettings.CHRISTMAS_EVENT[3], 1);
                            DialogueManager.start(player, 196);
                        } else if (player.christmas2021complete) {
                            DialogueManager.start(player, 196);
                        }


                        break;
                    case 541:
                        if (player.getGameMode() != GameMode.NORMAL) {
                            ShopManager.getShops().get(77).open(player);
                        } else {
                            ShopManager.getShops().get(5).open(player);
                        }
                        break;
                    case 2633:
                        ShopManager.getShops().get(50).open(player);
                        break;
                    case 741:
                        ShopManager.getShops().get(49).open(player);
                        break;
                    case 2998:
                        ShopManager.getShops().get(41).open(player);
                        break;
                    case 5441:
                        ShopManager.getShops().get(51).open(player);
                        break;
                    case 461:
                        if (player.getGameMode() != GameMode.NORMAL) {
                            ShopManager.getShops().get(77).open(player);
                        } else {
                            ShopManager.getShops().get(1).open(player);
                        }
                        break;

                    case 1595:
                        if (player.getMoneyInPouchAsInt() >= 5000) {
                            player.setMoneyInPouch(player.getMoneyInPouch() - 5000);
                            player.getPacketSender().sendMessage("Saniboch takes 5,000 coins for his service.");
                            player.moveTo(new Position(2709, 9471, 0));
                        } else {
                            player.getPacketSender().sendMessage("Saniboch charges 5,000 coins for this service.");
                        }


                        break;
                    case 5080:
                        if (player.getInterfaceId() > 0 || player == null || !player.getClickDelay().elapsed(1000))
                            return;
                        if (player.getSkillManager().getCurrentLevel(Skill.HUNTER) < 63) {
                            player.getPacketSender().sendMessage("You need a Hunter level of at least 63 to catch this chinchompa");
                            return;
                        }
                        if (!player.getInventory().contains(10010) && !player.getEquipment().contains(10010)) {
                            player.getPacketSender().sendMessage("You do not have a net to catch this chinchompa with.");
                            return;
                        }
                        player.performAnimation(new Animation(6605));
                        boolean sucess = player.getSkillManager().getCurrentLevel(Skill.HUNTER) < 63 || player.getSkillManager().getCurrentLevel(Skill.HUNTER) > RandomUtility.inclusiveRandom(63) + 20;
                        if (sucess) {
                            if (npc.isRegistered()) {
                                World.deregister(npc);
                                TaskManager.submit(new NPCRespawnTask(npc, npc.getDefinition().getRespawnTime()));
                                player.getInventory().add(10034, 2);
                                player.getPacketSender().sendMessage("You successfully catch the chinchompa.");
                                player.getSkillManager().addExperience(Skill.HUNTER, 225);
                            }
                        } else
                            player.getPacketSender().sendMessage("You failed to catch the chinchompa.");
                        player.getClickDelay().reset();
                        break;

                    case 278:
                        ShopManager.getShops().get(6).open(player);
                        break;
                    case 12:
                        player.setDialogueActionId(163);
                        DialogueManager.start(player, 163);
                        break;
                    case 4902:

                        DialogueManager.start(player, 157);
                        player.setDialogueActionId(-1);
                        break;

                    case 543:
                        DialogueManager.start(player, 159);
                        player.setDialogueActionId(709);
                        break;

                    case 4247:
                        ShopManager.getShops().get(56).open(player);

                        break;
                    case 457:
                        DialogueManager.start(player, 117);
                        player.setDialogueActionId(74);
                        break;

                    case 648:
                        ShopManager.getShops().get(9).open(player);
                        break;

                    case 8710:
                    case 8707:
                    case 8706:
                    case 8705:
                        EnergyHandler.rest(player);
                        break;
                    case 17222:
                        if (player.getLocation() == Location.TUTORIAL_DUNGEON) {
                            Tutorial.miningTutorial(player);
                        }
                        break;
                    case 14403:
                        if (player.getLocation() == Location.TUTORIAL_DUNGEON) {

                            if (player.tutorialIsland > 4)
                                Tutorial.combatTutorial(player);
                            else
                                player.getPacketSender().sendMessage("@red@You must complete the previous part of the tutorial.");
                        }
                        break;
                    case 2917:
                        if (player.getLocation() == Location.TUTORIAL_ISLAND || player.getLocation() == Location.TUTORIAL_DUNGEON) {
                            Tutorial.questProgress(player);
                        } else {
                            if (player.getPointsHandler().getdonatorpoints() > 0) {
                                player.getInventory().add(8851, player.getPointsHandler().getdonatorpoints());
                                player.getPointsHandler().setdonatorpoints(player.getPointsHandler().getdonatorpoints(), -player.getPointsHandler().getdonatorpoints());
                            }

                            if (player.getPointsHandler().getAmountDonated() > 0) {
                                player.setDialogueActionId(214);
                                DialogueManager.start(player, 213);
                            } else
                                player.getPacketSender().sendMessage("@red@You don't have any Donator Rank!");
                        }

                        if (player.getStaffRights().getStaffRank() == 4) {
                            if (player.monthlyFreebie != GameLoader.getMonth()) {
                                if (player.getInventory().getFreeSlots() >= 1) {
                                    player.getPacketSender().sendMessage("@blu@Thank you for your support. Take these free Passes!");
                                    player.getInventory().add(6769, 1); //Event Pass
                                    player.monthlyFreebie = GameLoader.getMonth();
                                } else
                                    player.getPacketSender().sendMessage("@red@You need 1 inventory space for Pat's Monthly Freebie!");
                            }
                        }

                        if (player.getStaffRights().getStaffRank() == 5) {
                            if (player.monthlyFreebie != GameLoader.getMonth()) {
                                if (player.getInventory().getFreeSlots() >= 2) {
                                    player.getPacketSender().sendMessage("@blu@Thank you for your support. Take these free Passes!");
                                    player.getInventory().add(6769, 1); //Event Pass
                                    player.getInventory().add(6758, 1); //Battle Pass
                                    player.monthlyFreebie = GameLoader.getMonth();
                                } else
                                    player.getPacketSender().sendMessage("@red@You need 2 inventory spaces for Pat's Monthly Freebie!");
                            }
                        }

                        if (player.getStaffRights().getStaffRank() >= 6) {
                            if (player.monthlyFreebie != GameLoader.getMonth()) {
                                if (player.getInventory().getFreeSlots() >= 3) {
                                    player.getPacketSender().sendMessage("@blu@Thank you for your support. Take this free Event Pass!");
                                    player.getInventory().add(6769, 1); //Event Pass
                                    player.getInventory().add(6758, 1); //Battle Pass
                                    player.getInventory().add(6749, 1); //Mystery Pass
                                    player.monthlyFreebie = GameLoader.getMonth();
                                } else
                                    player.getPacketSender().sendMessage("@red@You need 3 inventory spaces for Pat's Monthly Freebie!");

                            }
                        }

                        break;
                    case 11226:
                        ShopManager.getShops().get(45).open(player);
                        break;
                    case 273: //Boss point shop npc id can be anything as long as it opens shop 92
                        ShopManager.getShops().get(92).open(player);
                        player.sendMessage("<img=0>You currently have @red@" + player.getPaePoints() + " PaePoints!");
                        player.sendMessage("You can use untradeable items on the Exchange Table to receive a 50% PP refund.");
                        break;
                    case 241:
                        ShopManager.getShops().get(77).open(player);
                        break;
                    case 9713:
                        if (player.getRaidsParty() != null) {
                            if (player.getLocation() == Location.SHR && player.strongholdLootFloor == 0) {
                                if (player.getRaidsParty().shrFloorOneBossKey < 18000)
                                    RaidFloorOne.assignBossKey(player);
                                else
                                    player.getPacketSender().sendMessage("Your Floor One Boss Key is: " + ItemDefinition.forId(player.getRaidsParty().shrFloorOneBossKey).getName() + ".");
                            } else if (player.getLocation() == Location.SHR && player.strongholdLootFloor == 1) {
                                if (player.getRaidsParty().shrFloorTwoBossKey < 18000)
                                    RaidFloorTwo.assignBossKey(player);
                                else
                                    player.getPacketSender().sendMessage("Your Floor Two Boss Key is: " + ItemDefinition.forId(player.getRaidsParty().shrFloorTwoBossKey).getName() + ".");
                            } else if (player.getLocation() == Location.SHR && player.strongholdLootFloor == 2) {
                                if (player.getRaidsParty().shrFloorThreeBossKey < 18000)
                                    RaidFloorThree.assignBossKey(player);
                                else
                                    player.getPacketSender().sendMessage("Your Floor Three Boss Key is: " + ItemDefinition.forId(player.getRaidsParty().shrFloorThreeBossKey).getName() + ".");
                            } else if (player.getLocation() == Location.SHR && player.strongholdLootFloor == 3) {
                                if (player.getRaidsParty().shrFloorFourBossKey < 18000)
                                    RaidFloorFour.assignBossKey(player);
                                else
                                    player.getPacketSender().sendMessage("Your Floor Four Boss Key is: " + ItemDefinition.forId(player.getRaidsParty().shrFloorFourBossKey).getName() + ".");
                            }
                        }
                        break;
                    case 683:
                        if (player.getGameMode() != GameMode.NORMAL) {
                            ShopManager.getShops().get(77).open(player);
                        } else {
                            ShopManager.getShops().get(3).open(player);
                        }
                        break;
                    case 836:
                        ShopManager.getShops().get(97).open(player);
                        break;
                    case 2622:
                        ShopManager.getShops().get(43).open(player);
                        break;
                    case 3101:
                        DialogueManager.start(player, 90);
                        player.setDialogueActionId(57);
                        break;
                    case 7969:
                        ShopManager.getShops().get(28).open(player);
                        //DialogueManager.start(player, ExplorerJack.getDialogue(player));
                        break;
                    case 1597:
                    case 8275:
                    case 9085:
                    case 7780:
                    case 2909:
                    case 20797:
                        if (npc.getId() != player.getSlayer().getSlayerMaster().getNpcId()) {
                            player.getPacketSender().sendMessage("This is not your current Slayer master.");
                            return;
                        }
                        DialogueManager.start(player, SlayerDialogues.dialogue(player));
                        break;
                    case 437:
                        DialogueManager.start(player, 99);
                        player.setDialogueActionId(58);
                        break;
                    case 5112:
                        ShopManager.getShops().get(38).open(player);
                        break;
                    case 8591:
                        //player.nomadQuest[0] = player.nomadQuest[1] = player.nomadQuest[2] = false;
                        if (!player.getMinigameAttributes().getNomadAttributes().hasFinishedPart(0)) {
                            DialogueManager.start(player, 48);
                            player.setDialogueActionId(23);
                        } else if (player.getMinigameAttributes().getNomadAttributes().hasFinishedPart(0) && !player.getMinigameAttributes().getNomadAttributes().hasFinishedPart(1)) {
                            DialogueManager.start(player, 50);
                            player.setDialogueActionId(24);
                        } else if (player.getMinigameAttributes().getNomadAttributes().hasFinishedPart(1))
                            DialogueManager.start(player, 53);
                        break;
                    case 3385:
                        if (player.getMinigameAttributes().getRecipeForDisasterAttributes().hasFinishedPart(0) && player.getMinigameAttributes().getRecipeForDisasterAttributes().getWavesCompleted() < 6) {
                            DialogueManager.start(player, 39);
                            return;
                        }
                        if (player.getMinigameAttributes().getRecipeForDisasterAttributes().getWavesCompleted() == 6) {
                            DialogueManager.start(player, 46);
                            return;
                        }
                        DialogueManager.start(player, 38);
                        player.setDialogueActionId(20);
                        break;
                    case 6139:
                        DialogueManager.start(player, 29);
                        player.setDialogueActionId(17);
                        break;
                    case 650:
                        ShopManager.getShops().get(35).open(player);
                        break;
                    case 6055:
                    case 6056:
                    case 6057:
                    case 6058:
                    case 6059:
                    case 6060:
                    case 6061:
                    case 6062:
                    case 6063:
                    case 6064:
                    case 7903:

                        PuroPuro.catchImpling(player, npc);
                        break;
                    case 8022:
                    case 8028:
                        DesoSpan.siphon(player, npc);
                        break;
                    case 2579:
                        player.setDialogueActionId(13);
                        DialogueManager.start(player, 24);
                        break;
                    case 8725:
                        player.setDialogueActionId(10);
                        DialogueManager.start(player, 19);
                        break;
                    case 4249:
                        player.setDialogueActionId(9);
                        DialogueManager.start(player, 64);
                        break;
                    case 6807:
                    case 6994:
                    case 6995:
                    case 6867:
                    case 6868:
                    case 6794:
                    case 6795:
                    case 6815:
                    case 6816:
                    case 6874:
                    case 6873:
                    case 3594:
                    case 3590:
                    case 3596:
                        if (player.getSummoning().getFamiliar() == null || player.getSummoning().getFamiliar().getSummonNpc() == null || player.getSummoning().getFamiliar().getSummonNpc().getIndex() != npc.getIndex()) {
                            player.getPacketSender().sendMessage("That is not your familiar.");
                            return;
                        }
                        player.getSummoning().store();
                        break;
                    case 364:
                        player.setDialogueActionId(8);
                        DialogueManager.start(player, 13);
                        break;
                    case 6970:
                        player.setDialogueActionId(3);
                        DialogueManager.start(player, 3);
                        break;
                    case 23855:
                        player.setDialogueActionId(312);
                        DialogueManager.start(player, 312);
                        break;
                    case 4657:
                        player.setDialogueActionId(5);
                        DialogueManager.start(player, 5);
                        break;
                    case 318:
                    case 316:
                    case 313:
                    case 312:
                    case 309:
                        player.setEntityInteraction(npc);
                        Fishing.setupFishing(player, Fishing.forSpot(npc.getId(), false));
                        break;
                    case 805:
                        ShopManager.getShops().get(34).open(player);
                        break;
                    case 462:
                        ShopManager.getShops().get(33).open(player);
                        break;
                    case 1263:
                        ShopManager.getShops().get(32).open(player);
                        break;
                    case 8444:

                        ShopManager.getShops().get(31).open(player);
                        break;
                    case 8459:
                        ShopManager.getShops().get(30).open(player);
                        break;
                    case 3299:
                        ShopManager.getShops().get(21).open(player);
                        break;
                    case 548:
                        ShopManager.getShops().get(20).open(player);
                        break;
                    case 1685:
                        if (player.getGameMode() == GameMode.NORMAL)
                            ShopManager.getShops().get(19).open(player);
                        else
                            ShopManager.getShops().get(53).open(player);
                        player.sendMessage("<img=0>You currently have @red@" + player.getPaePoints() + " PaePoints!");
                        break;
                    case 308:
                        ShopManager.getShops().get(18).open(player);
                        break;
                    case 802:
                        ShopManager.getShops().get(17).open(player);
                        break;
                    case 794:
                        ShopManager.getShops().get(16).open(player);
                        break;
                    case 4946:
                        ShopManager.getShops().get(15).open(player);
                        break;
                    case 948:
                        ShopManager.getShops().get(13).open(player);
                        break;
                    case 4906:
                        ShopManager.getShops().get(14).open(player);
                        break;
                    case 520:
                    case 521:
                        ShopManager.getShops().get(12).open(player);
                        break;
                    case 2292:
                        ShopManager.getShops().get(11).open(player);
                        break;
                    case 2676:
                        player.getPacketSender().sendInterface(3559);
                        player.getAppearance().setCanChangeAppearance(true);
                        break;
                    case 494:
                    case 1360:
                        if (player.getGameMode() == GameMode.ULTIMATE_IRONMAN)
                            player.getUimBank().open();
                        else
                            player.getBank(player.getCurrentBankTab()).open();
                        break;

                }
                if (!(npc.getId() >= 8705 && npc.getId() <= 8710)) {
                    npc.setPositionToFace(player.getPosition());
                }
                player.setPositionToFace(npc.getPosition());
            }
        }));
    }

    private static void attackNPC(Player player, Packet packet) {
        int index = packet.readShortA();
        if (index < 0 || index > World.getNpcs().capacity())
            return;
        final NPC interact = World.getNpcs().get(index);
        if (interact == null)
            return;

        if (player.getStaffRights() == StaffRights.DEVELOPER)
            player.getPacketSender().sendMessage("Attack npc id: " + interact.getId());

        if (!NpcDefinition.getDefinitions()[interact.getId()].isAttackable()) {
            return;
        }

        if (interact.getConstitution() <= 0) {
            player.getMovementQueue().reset();
            return;
        }

        player.getAfkAttack().reset();


        if (interact.getDefinition().getName().contains("pvp")) {
            if (player.getSkillManager().getCombatLevel() > interact.getDefinition().getCombatLevel() + player.getWildernessLevel()) {
                player.getPacketSender().sendMessage("Your combat level difference is too much to attack this bot.");
                return;
            }
        }

        if (SlayerMobs.slayerReqForId(interact.getId()) > 0) {

            if (player.getSkillManager().getCurrentLevel(Skill.SLAYER) < SlayerMobs.slayerReqForId(interact.getId())) {
                player.getPacketSender().sendMessage("You need " + SlayerMobs.slayerReqForId(interact.getId()) + " Slayer to kill this NPC.");
                return;
            }

        }

        player.getActionTracker().offer(new ActionAttackNPC(interact.getId()));

        if (player.getCombatBuilder().getStrategy() == null) {
            player.getCombatBuilder().determineStrategy();
        }
        if (CombatFactory.checkAttackDistance(player, interact)) {
            player.getMovementQueue().reset();
        }

        player.getCombatBuilder().attack(interact);
    }

    public void handleSecondClick(Player player, Packet packet) {
        int index = packet.readInt();
        if (index < 0 || index > World.getNpcs().capacity())
            return;
        final NPC npc = World.getNpcs().get(index);
        if (npc == null)
            return;
        player.setEntityInteraction(npc);
        final int npcId = npc.getId();
        if (player.getStaffRights() == StaffRights.DEVELOPER)
            player.getPacketSender().sendMessage("Second click npc id: " + npcId);
        player.getActionTracker().offer(new ActionNPCOption(Action.ActionType.SECOND_NPC_OPTION, npc.getId()));
        npc.clickNpc(player, NpcClickType.SECOND_CLICK);
        player.setWalkToTask(new WalkToTask(player, npc.getPosition(), npc.getSize(), new FinalizedMovementTask() {
            @Override
            public void execute() {
                switch (npc.getId()) {

                    case 17668: //King Vargas

                        player.setInputHandling(new DonateToKingdom());
                        player.getPacketSender().sendInterfaceRemoval().sendEnterAmountPrompt("How much money would you like to contribute with? (Millions)");

                        break;
                    case 2909:
                        player.getSkiller().assignWildernessTask();
                        break;
                    case 2917:
                        if (player.getLocation() == Location.TUTORIAL_ISLAND || player.getLocation() == Location.TUTORIAL_DUNGEON) {
                            player.getPacketSender().sendMessage("Talk to Pat to progress through the tutorial.");
                        } else {
                            CommunityEvents.checkEvent(player);
                        }
                        break;
                    case 3046:

                        int randomBarrows = 3062 + RandomUtility.inclusiveRandom(4);

                        npc.setTransformationId(randomBarrows);
                        npc.getUpdateFlag().flag(Flag.TRANSFORM);

                        if (randomBarrows == 3062)
                            npc.forceChat("Dharok the Wretched!");
                        else if (randomBarrows == 3063)
                            npc.forceChat("Guthan the Infested!");
                        else if (randomBarrows == 3064)
                            npc.forceChat("Karil the Tainted!");
                        else if (randomBarrows == 3065)
                            npc.forceChat("Torag the Corrupted");
                        else if (randomBarrows == 3066)
                            npc.forceChat("Verac the Defiled");

                        break;
                    case 3062:
                        npc.setTransformationId(3063);
                        npc.getUpdateFlag().flag(Flag.TRANSFORM);
                        npc.forceChat("Guthan the Infested!");
                        break;

                    case 3063:
                        npc.setTransformationId(3064);
                        npc.getUpdateFlag().flag(Flag.TRANSFORM);
                        npc.forceChat("Karil the Tainted!");
                        break;
                    case 3064:
                        npc.setTransformationId(3065);
                        npc.getUpdateFlag().flag(Flag.TRANSFORM);
                        npc.forceChat("Torag the Corrupted!");
                        break;

                    case 3065:
                        npc.setTransformationId(3066);
                        npc.getUpdateFlag().flag(Flag.TRANSFORM);
                        npc.forceChat("Verac the Defiled!");
                        break;

                    case 3066:
                        npc.setTransformationId(3046);
                        npc.getUpdateFlag().flag(Flag.TRANSFORM);
                        npc.forceChat("Ahrim the Blighted!");
                        break;


                    case 1685:
                        if (player.getGameMode() == GameMode.NORMAL)
                            ShopManager.getShops().get(52).open(player);
                        else
                            ShopManager.getShops().get(54).open(player);
                        break;
                    case 23698: //vyrewatch
                        Pickpocket.pickpocketNPC(player, 23698, npc);
                        break;
                    case 2998:
                        //DialogueManager.start(player, 155);
                        player.getPacketSender().sendEnterAmountPrompt("How many coins would you like to gamble?");
                        player.setInputHandling(new GambleAmount());
                        break;
                    case 3192:
                        PlayerPanel.refreshPanel(player);
                        player.getPacketSender().sendInterface(3200);
                        break;
                    case 4559:
                        ShopManager.getShops().get(46).open(player);
                        break;
                    case 961:
                        ShopManager.getShops().get(6).open(player);
                        break;
                    case 2579:
                        ShopManager.getShops().get(72).open(player);
                        //player.getPacketSender().sendMessage("<col=255>You currently have "+player.getPointsHandler().getPrestigePoints()+" Prestige points!");
                        break;
                    case 212:
                        ShopManager.getShops().get(49).open(player);
                        break;
                    case 457:
                        player.getPacketSender().sendMessage("The ghost teleports you away.");
                        player.getPacketSender().sendInterfaceRemoval();
                        player.moveTo(new Position(3651, 3486));
                        break;
                    case 2622:
                        ShopManager.getShops().get(43).open(player);
                        break;
                    case 4902:
                        ShopManager.getShops().get(55).open(player);
                        break;
                    case 462:
                        npc.performAnimation(CombatSpells.CONFUSE.getSpell().castAnimation().get());
                        npc.forceChat("Off you go!");
                        TeleportHandler.teleportPlayer(player, new Position(2911, 4832), player.getSpellbook().getTeleportType());
                        break;
                    case 3101:
                        DialogueManager.start(player, 95);
                        player.setDialogueActionId(57);
                        break;
                    case 648:
                        ShopManager.getShops().get(10).open(player); //trimmed skillcape shop
                        break;
                    case 7969:
                        ShopManager.getShops().get(28).open(player);
                        break;
                    case 364:
                        //player.getPacketSender().sendMessage("").sendMessage("You currently have "+player.getPointsHandler().getVotingPoints()+" Voting points.").sendMessage("You can earn points and coins by voting. To do so, simply use the ::vote command.");;
                        ShopManager.getShops().get(27).open(player);

                        break;
                    case 4657:
                        DialogueManager.start(player, DonationBonds.getTotalFunds(player));
                        break;
                    case 1597:
                    case 9085:
                    case 7780:
                    case 20797:
                        if (npc.getId() != player.getSlayer().getSlayerMaster().getNpcId()) {
                            player.getPacketSender().sendMessage("This is not your current Slayer master.");
                            return;
                        }
                        if (player.getSlayer().getSlayerTask() == SlayerTasks.NO_TASK)
                            player.getSlayer().assignTask();
                        else
                            DialogueManager.start(player, SlayerDialogues.findAssignment(player));
                        break;
                    case 8591:
                        if (!player.getMinigameAttributes().getNomadAttributes().hasFinishedPart(1)) {
                            player.getPacketSender().sendMessage("You must complete Nomad's quest before being able to use this shop.");
                            return;
                        }
                        ShopManager.getShops().get(37).open(player);
                        break;
                    case 805:
                        Tanning.selectionInterface(player);
                        break;
                    case 318:
                    case 316:
                    case 313:
                    case 312:
                        player.setEntityInteraction(npc);
                        Fishing.setupFishing(player, Fishing.forSpot(npc.getId(), true));
                        break;
                    case 4946:
                        ShopManager.getShops().get(15).open(player);
                        break;


                    case 683:
                        if (player.getGameMode() != GameMode.NORMAL) {
                            ShopManager.getShops().get(77).open(player);
                        } else {
                            ShopManager.getShops().get(3).open(player);
                        }
                        break;
                    case 705:
                        ShopManager.getShops().get(4).open(player);
                        break;
                    case 2253:
                        ShopManager.getShops().get(70).open(player);
                        break;
                    case 6970:
                        if(player.getGameMode() == GameMode.SEASONAL_IRONMAN && player.Shoplifter) {
                            player.getPacketSender().sendMessage("Seasonal players with Shopfliter can't use this.");
                            return;
                        }

                        player.setDialogueActionId(35);
                        DialogueManager.start(player, 63);
                        break;
                }
                npc.setPositionToFace(player.getPosition());
                player.setPositionToFace(npc.getPosition());
            }
        }));
    }

    public void handleThirdClick(Player player, Packet packet) {
        int index = packet.readShort();
        if (index < 0 || index > World.getNpcs().capacity())
            return;
        final NPC npc = World.getNpcs().get(index);
        if (npc == null)
            return;
        player.setEntityInteraction(npc).setPositionToFace(npc.getPosition().copy());
        npc.setPositionToFace(player.getPosition());
        if (player.getStaffRights() == StaffRights.DEVELOPER)
            player.getPacketSender().sendMessage("Third click npc id: " + npc.getId());
        player.getActionTracker().offer(new ActionNPCOption(Action.ActionType.THIRD_NPC_OPTION, npc.getId()));
        npc.clickNpc(player, NpcClickType.THIRD_CLICK);
        player.setWalkToTask(new WalkToTask(player, npc.getPosition(), npc.getSize(), new FinalizedMovementTask() {
            @Override
            public void execute() {
                switch (npc.getId()) {


                    case 17668: //King Vargas
                        DialogueManager.start(player, 20);
                        player.setDialogueActionId(11);
                        break;
                    case 3101:
                        ShopManager.getShops().get(42).open(player);
                        break;
                    case 2917:
                        ShopManager.getShops().get(98).open(player);
                        break;
                    case 1597:
                    case 8275:
                    case 9085:
                    case 7780:
                        ShopManager.getShops().get(40).open(player);
                        break;
                    case 364:
                        ShopManager.getShops().get(73).open(player);

                        break;
                    case 20797:
                        if (player.getSkillManager().getCurrentLevel(Skill.SLAYER) >= 99) {

                            if (player.getSlayer().getSlayerTask() == SlayerTasks.NO_TASK) {
                                player.getSlayer().setSlayerMaster(SlayerMaster.NIEVE);
                                player.getPacketSender().sendMessage("You change your Slayer Master to Nieve.");
                            } else {
                                player.getPacketSender().sendMessage("You must finish your current task before changing your Slayer Master.");
                            }

                        } else {
                            player.getPacketSender().sendMessage("You need 99 Slayer to use Nieve.");
                        }
                        break;
                    case 4657:
                        if (player.getStaffRights() == StaffRights.PLAYER) {
                            player.getPacketSender().sendMessage("You need to be a member to teleport to this zone.").sendMessage("To become a member, visit www.Morytania.org/forum and purchase a scroll.");
                            return;
                        }
                        TeleportHandler.teleportPlayer(player, new Position(3363, 9638), player.getSpellbook().getTeleportType());
                        break;
                    case 648:
                        ShopManager.getShops().get(71).open(player); //vote shop
                        break;
                    case 946:
                        ShopManager.getShops().get(0).open(player);
                        break;
                    case 1861:
                        ShopManager.getShops().get(2).open(player);
                        break;
                    case 1685:
                        if (player.prestige > 0 && player.getGameMode() != GameMode.SEASONAL_IRONMAN) {
                            ShopManager.getShops().get(94).open(player);
                            player.sendMessage("<img=0>You currently have @red@" + player.getPaePoints() + " PaePoints!");
                        } else
                            player.getPacketSender().sendMessage("@red@You must Prestige before using this shop!");
                        break;
                    case 961:
                        if (player.getStaffRights() == StaffRights.PLAYER) {
                            player.getPacketSender().sendMessage("This feature is currently only available for members.");
                            return;
                        }
                        boolean restore = player.getSpecialPercentage() < 100;
                        if (restore) {
                            player.setSpecialPercentage(100);
                            CombatSpecial.updateBar(player);
                            player.getPacketSender().sendMessage("Your special attack energy has been restored.");
                        }
                        for (Skill skill : Skill.values()) {
                            if (player.getSkillManager().getCurrentLevel(skill) < player.getSkillManager().getMaxLevel(skill)) {
                                player.getSkillManager().setCurrentLevel(skill, player.getSkillManager().getMaxLevel(skill));
                                restore = true;
                            }
                        }
                        if (restore) {
                            player.performGraphic(new Graphic(1302));
                            player.getPacketSender().sendMessage("Your stats have been restored.");
                        } else
                            player.getPacketSender().sendMessage("Your stats do not need to be restored at the moment.");
                        break;
                    case 705:
                        ShopManager.getShops().get(5).open(player);
                        break;
                    case 2253:
                        ShopManager.getShops().get(10).open(player);
                        break;
                }
                npc.setPositionToFace(player.getPosition());
                player.setPositionToFace(npc.getPosition());
            }
        }));
    }

    public void handleFourthClick(Player player, Packet packet) {
        int index = packet.readLEShort();
        if (index < 0 || index > World.getNpcs().capacity())
            return;
        final NPC npc = World.getNpcs().get(index);
        if (npc == null)
            return;
        player.setEntityInteraction(npc);
        if (player.getStaffRights() == StaffRights.DEVELOPER)
            player.getPacketSender().sendMessage("Fourth click npc id: " + npc.getId());
        player.getActionTracker().offer(new ActionNPCOption(Action.ActionType.FOURTH_NPC_OPTION, npc.getId()));
        npc.clickNpc(player, NpcClickType.FOURTH_CLICK);
        player.setWalkToTask(new WalkToTask(player, npc.getPosition(), npc.getSize(), new FinalizedMovementTask() {
            @Override
            public void execute() {
                switch (npc.getId()) {
                    case 4657:
                        if (player.getStaffRights() == StaffRights.PLAYER) {
                            player.getPacketSender().sendMessage("You need to be a member to teleport to this zone.").sendMessage("To become a member, visit www.Morytania.org/forum and purchase a scroll.");
                            return;
                        }
                        TeleportHandler.teleportPlayer(player, new Position(3363, 9638), player.getSpellbook().getTeleportType());
                        break;
                    case 364:
                        player.getPacketSender().sendMessage("You currently have " + player.getPointsHandler().getLoyaltyPoints() + " Loyalty points.");
                        ShopManager.getShops().get(73).open(player);

                        break;
                    case 705:
                        ShopManager.getShops().get(7).open(player);
                        break;
                    case 2253:
                        ShopManager.getShops().get(8).open(player);
                        break;
                    case 1597:
                    case 9085:
                    case 8275:
                    case 7780:
                    case 20797:
                        ShopManager.getShops().get(92).open(player);
                        player.sendMessage("<img=0>You currently have @red@" + player.getPaePoints() + " PaePoints!");
                        break;
                }
                npc.setPositionToFace(player.getPosition());
                player.setPositionToFace(npc.getPosition());
            }
        }));
    }

    @Override
    public void handleMessage(Player player, Packet packet) {
        if (player.isTeleporting() || player.isPlayerLocked() || player.getMovementQueue().isLockMovement())
            return;
        switch (packet.getOpcode()) {
            case ATTACK_NPC:
                attackNPC(player, packet);
                break;
            case FIRST_CLICK_OPCODE:
                firstClick(player, packet);
                break;
            case SECOND_CLICK_OPCODE:
                handleSecondClick(player, packet);
                break;
            case THIRD_CLICK_OPCODE:
                handleThirdClick(player, packet);
                break;
            case FOURTH_CLICK_OPCODE:
                handleFourthClick(player, packet);
                break;
            case MAGE_NPC:
                int npcIndex = packet.readLEShortA();
                int spellId = packet.readShortA();

                if (npcIndex < 0 || spellId < 0 || npcIndex > World.getNpcs().capacity()) {
                    return;
                }

                NPC n = World.getNpcs().get(npcIndex);
                player.setEntityInteraction(n);

                CombatSpell spell = CombatSpells.getSpell(spellId);

                if (n == null || spell == null) {
                    player.getMovementQueue().reset();
                    return;
                }

                if (!NpcDefinition.getDefinitions()[n.getId()].isAttackable()) {
                    player.getMovementQueue().reset();
                    return;
                }

                if (n.getConstitution() <= 0) {
                    player.getMovementQueue().reset();
                    return;
                }
                player.getActionTracker().offer(new ActionMagicOnNPC(npcIndex, spellId));
                player.setPositionToFace(n.getPosition());
                player.setCastSpell(spell);
                if (player.getCombatBuilder().getStrategy() == null) {
                    player.getCombatBuilder().determineStrategy();
                }
                if (CombatFactory.checkAttackDistance(player, n)) {
                    player.getMovementQueue().reset();
                }
                player.getCombatBuilder().resetCooldown();
                player.getCombatBuilder().attack(n);
                break;
        }
    }
}
