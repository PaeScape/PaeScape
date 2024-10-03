package com.arlania.net.packet.impl;

import com.arlania.GameSettings;
import com.arlania.engine.task.impl.WalkToTask;
import com.arlania.engine.task.impl.WalkToTask.FinalizedMovementTask;
import com.arlania.model.*;
import com.arlania.model.Locations.Location;
import com.arlania.model.definitions.GameObjectDefinition;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketListener;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.clip.region.RegionClipping;
import com.arlania.world.content.*;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.content.minigames.impl.DungeoneeringEquipment;
import com.arlania.world.content.minigames.impl.EquipmentUpgrades;
import com.arlania.world.content.minigames.impl.WildyEquipmentUpgrades;
import com.arlania.world.content.minigames.impl.skilling.BlastFurnace;
import com.arlania.world.content.minigames.impl.skilling.Wintertodt;
import com.arlania.world.content.minigames.impl.strongholdraids.Objects;
import com.arlania.world.content.skill.impl.cooking.Cooking;
import com.arlania.world.content.skill.impl.cooking.CookingData;
import com.arlania.world.content.skill.impl.crafting.Gems;
import com.arlania.world.content.skill.impl.crafting.JewelryMaking;
import com.arlania.world.content.skill.impl.crafting.LeatherMaking;
import com.arlania.world.content.skill.impl.farming.Farming;
import com.arlania.world.content.skill.impl.farming.SeedsOnPatch;
import com.arlania.world.content.skill.impl.firemaking.Firemaking;
import com.arlania.world.content.skill.impl.fletching.Fletching;
import com.arlania.world.content.skill.impl.herblore.Herblore;
import com.arlania.world.content.skill.impl.herblore.PotionCombinating;
import com.arlania.world.content.skill.impl.herblore.WeaponPoison;
import com.arlania.world.content.skill.impl.prayer.BonesOnAltar;
import com.arlania.world.content.skill.impl.prayer.Prayer;
import com.arlania.world.content.skill.impl.slayer.SlayerDialogues;
import com.arlania.world.content.skill.impl.slayer.SlayerTasks;
import com.arlania.world.content.skill.impl.slayer.SuperiorSlayer;
import com.arlania.world.content.skill.impl.smithing.EquipmentMaking;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.antibotting.actions.ActionItemOnItem;
import com.arlania.world.entity.impl.player.antibotting.actions.ActionItemOnObject;
import com.arlania.world.entity.impl.player.antibotting.actions.ActionItemOnPlayer;
import com.arlania.world.entity.impl.player.antibotting.actions.ActionUseItem;

import static com.arlania.world.content.skill.impl.fletching.Fletching.makeAmethystAmmo;
import static com.arlania.world.content.skill.impl.fletching.Fletching.makeNotedAmethystAmmo;

/**
 * This packet listener is called when a player 'uses' an item on another
 * entity.
 *
 * @author relex lawl
 */

public class UseItemPacketListener implements PacketListener {

    /**
     * The PacketListener logger to debug information and print out errors.
     */
    // private final static Logger logger =
    // Logger.getLogger(UseItemPacketListener.class);
    private static void useItem(Player player, Packet packet) {
        if (player.isTeleporting() || player.getConstitution() <= 0)
            return;
        int interfaceId = packet.readInt();
        int slot = packet.readShortA();
        int id = packet.readInt();
        player.getActionTracker().offer(new ActionUseItem(interfaceId, slot, id));
    }

    private static void itemOnItem(Player player, Packet packet) {
        int firstItemSlot = packet.readUnsignedShort();
        int secondItemSlot = packet.readUnsignedShortA();
        int firstItemId = packet.readInt();
        int firstInterfaceIdMaybe = packet.readInt();
        int secondItemId = packet.readInt();
        int secondInterfaceIdMaybe = packet.readInt();
        if (firstItemSlot < 0 || secondItemSlot < 0 || secondItemSlot > player.getInventory().capacity()
                || firstItemSlot > player.getInventory().capacity())
            return;
        Item firstItem = player.getInventory().getItems()[firstItemSlot];
        Item secondItem = player.getInventory().getItems()[secondItemSlot];
        player.getActionTracker().offer(new ActionItemOnItem(secondItem.getId(), firstItem.getId()));

        // charge scythe
        if (firstItem.getId() == 21006) {
            if (secondItem.getId() == 4604) {
                int amount = Math.min(player.getInventory().getAmount(4604), 1_000_000);
                player.setScytheCharges(player.getScytheCharges() + 100 * amount);
                player.getInventory().delete(4604, amount);
                player.getPacketSender().sendMessage("You now have " + player.getScytheCharges() + " charges in your Scythe of Vitur.");
                return;
            }
            if (secondItem.getId() == 224777) {
                player.setScytheCharges(player.getScytheCharges() + 1000);
                player.getPacketSender().sendMessage("You now have " + player.getScytheCharges() + " charges in your Scythe of Vitur.");
                player.getInventory().delete(224777, 1);
                return;
            }
        }

        // charge sang
        if (firstItem.getId() == 21005) {
            if (secondItem.getId() == 4604) {
                int amount = Math.min(player.getInventory().getAmount(4604), 1_000_000);
                player.setSanguinestiCharges(player.getSanguinestiCharges() + 100 * amount);
                player.getInventory().delete(4604, amount);
                player.getPacketSender().sendMessage("You now have " + player.getSanguinestiCharges() + " charges in your Sanguinesti Staff.");
                return;
            }
            if (secondItem.getId() == 224777) {
                player.setSanguinestiCharges(player.getSanguinestiCharges() + 1000);
                player.getPacketSender().sendMessage("You now have " + player.getSanguinestiCharges() + " charges in your Sanguinesti Staff.");
                player.getInventory().delete(224777, 1);
                return;
            }
        }

        // lightbearer upgrades
        if (firstItem.getId() == 21077 || secondItem.getId() == 21077) {

            int upgradeItem = -1;

            if (firstItem.getId() == 21077)
                upgradeItem = secondItem.getId();
            else if (secondItem.getId() == 21077)
                upgradeItem = firstItem.getId();

            player.getEquipment().handleLightbearer(upgradeItem);

        }

        // amethyst fletching
        if (firstItem.getId() == 221347 || secondItem.getId() == 221347) {
            makeAmethystAmmo(player, firstItem.getId(), secondItem.getId());
            return;
        }

        // noted amethyst fletching
        if (firstItem.getId() == 221348 || secondItem.getId() == 221348) {
            makeNotedAmethystAmmo(player, firstItem.getId(), secondItem.getId());
            return;
        }

        // amulet of blood fury
        if (firstItem.getId() == 6585) {
            if (secondItem.getId() == 224777) {
                player.getPacketSender().sendMessage("You combine the Amulet of Fury and Blood shard!");
                player.getInventory().delete(224777, 1);
                player.getInventory().delete(6585, 1);
                player.getInventory().add(224780, 1);
                return;
            }
        }

        //Wintertodt
        if ((secondItem.getId() == 946 && firstItem.getId() == 220695) || (firstItem.getId() == 946 && secondItem.getId() == 220695)) {
            Wintertodt.makeKindling(player);
            return;
        }

        //Equipment Upgrades
        if (secondItem.getId() == 4033 || secondItem.getId() == 4034 || secondItem.getId() == 4035 || secondItem.getId() == 4036 || secondItem.getId() == 4037 || secondItem.getId() == 4039 || secondItem.getId() == 4041) {
            int itemId = firstItem.getId();

            if (itemId > 0) {
                if (itemId >= 15775 && itemId <= 17361) {
                    DungeoneeringEquipment.upgradeItem(player, itemId, true);
                    return;
                }

                switch (itemId) {

                    case 18895:
                    case 18896:
                    case 18898:
                    case 18847:
                        player.getInventory().delete(itemId, 1);
                        player.getInventory().add(219496, 1);
                        return;
                    case 22885:
                        player.getInventory().delete(219493, 1);
                        player.getInventory().add(219496, 1);
                        return;
                }


                if(secondItem.getId() == 4037) {
                    for (int i = 0; i < GameSettings.LOWUNIQUES.length; i++) {
                        if (GameSettings.LOWUNIQUES[i] == itemId) {
                            EquipmentUpgrades.sacrificeItem(player, itemId, true, 0);
                            return;
                        }
                    }
                }

                if(secondItem.getId() == 4036) {
                    for (int i = 0; i < GameSettings.MEDIUMUNIQUES.length; i++) {
                        if (GameSettings.MEDIUMUNIQUES[i] == itemId) {
                            EquipmentUpgrades.sacrificeItem(player, itemId, true, 0);
                            return;
                        }
                    }
                }

                if(secondItem.getId() == 4033) {
                    for (int i = 0; i < GameSettings.HIGHUNIQUES.length; i++) {
                        if (GameSettings.HIGHUNIQUES[i] == itemId) {
                            EquipmentUpgrades.sacrificeItem(player, itemId, true, 0);
                            return;
                        }
                    }
                }

                if(secondItem.getId() == 4034) {
                    for (int i = 0; i < GameSettings.LEGENDARYUNIQUES.length; i++) {
                        if (GameSettings.LEGENDARYUNIQUES[i] == itemId) {
                            EquipmentUpgrades.sacrificeItem(player, itemId, true, 0);
                            return;
                        }
                    }
                }

                if(secondItem.getId() == 4035) {
                    for (int i = 0; i < GameSettings.MASTERUNIQUES.length; i++) {
                        if (GameSettings.MASTERUNIQUES[i] == itemId) {
                            EquipmentUpgrades.sacrificeItem(player, itemId, true, 0);
                            return;
                        }
                    }
                }

                if(secondItem.getId() == 4039) {
                    for (int i = 0; i < GameSettings.WILDYUNIQUES.length; i++) {
                        if (GameSettings.WILDYUNIQUES[i] == itemId) {
                            WildyEquipmentUpgrades.sacrificeItem(player, itemId, true);
                            return;
                        }
                    }
                }

                if(secondItem.getId() == 4041) {
                    for (int i = 0; i < GameSettings.UPGRADEABLECUSTOMUNIQUES.length; i++) {
                        if (GameSettings.UPGRADEABLECUSTOMUNIQUES[i] == itemId) {
                            EquipmentUpgrades.sacrificeItem(player, itemId, true, 0);
                            return;
                        }
                    }
                }


                player.getPacketSender().sendMessage("That item cannot be upgraded like this.");

            } else
                player.getPacketSender().sendMessage("That does not work here.");

            return;
        }

        // nightmare charging
        if (firstItem.getId() == 20568)
            if (secondItem.getId() == 707) {
                player.setNightmareCharges(player.getNightmareCharges() + 100);
                player.getPacketSender().sendMessage("You now have " + player.getNightmareCharges() + " charges in your Nightmare Staff.");
                player.getInventory().delete(707, 1);
                return;
            }
        if (firstItem.getId() == 20569)
            if (secondItem.getId() == 707) {
                player.setNightmareCharges(player.getNightmareCharges() + 100);
                player.getPacketSender().sendMessage("You now have " + player.getNightmareCharges() + " charges in your Nightmare Staff.");
                player.getInventory().delete(707, 1);
                return;
            }
        if (firstItem.getId() == 20570)
            if (secondItem.getId() == 707) {
                player.setNightmareCharges(player.getNightmareCharges() + 100);
                player.getPacketSender().sendMessage("You now have " + player.getNightmareCharges() + " charges in your Nightmare Staff.");
                player.getInventory().delete(707, 1);
                return;
            }
        if (firstItem.getId() == 20571)
            if (secondItem.getId() == 707) {
                player.setNightmareCharges(player.getNightmareCharges() + 100);
                player.getPacketSender().sendMessage("You now have " + player.getNightmareCharges() + " charges in your Nightmare Staff.");
                player.getInventory().delete(707, 1);
                return;
            }
        // blowpipe loading
        if (firstItem.getId() == 12926 || firstItem.getId() == 12927) {
            player.getBlowpipeLoading().handleLoadBlowpipe(secondItem);
            return;
        }
        if (secondItem.getId() == 12926 || firstItem.getId() == 12927) {
            player.getBlowpipeLoading().handleLoadBlowpipe(firstItem);
            return;
        }
        WeaponPoison.execute(player, secondItem.getId(), firstItem.getId());
        if (secondItem.getId() == 590 || firstItem.getId() == 590)
            Firemaking.lightFire(player, secondItem.getId() == 590 ? firstItem.getId() : secondItem.getId(), false, 1);
        if (firstItem.getId() == 2946 || secondItem.getId() == 2946)
            Firemaking.lightFire(player, secondItem.getId() == 2946 ? firstItem.getId() : secondItem.getId(), false, 1);
        if (secondItem.getDefinition().getName().contains("(") && firstItem.getDefinition().getName().contains("("))
            PotionCombinating.combinePotion(player, firstItem.getId(), secondItem.getId());
        if (firstItem.getId() == Herblore.VIAL || secondItem.getId() == Herblore.VIAL) {
            if (Herblore.makeUnfinishedPotion(player, firstItem.getId())
                    || Herblore.makeUnfinishedPotion(player, secondItem.getId()))
                return;
        }
        if (Herblore.isPotion(player, firstItem.getId(), secondItem.getId()))
            Herblore.finishPotion(player, firstItem.getId(), secondItem.getId());
        if (Herblore.isPotion(player, secondItem.getId(), firstItem.getId()))
            Herblore.finishPotion(player, secondItem.getId(), firstItem.getId());

        if (firstItem.getId() == 946 || secondItem.getId() == 946)
            Fletching.openSelection(player, firstItem.getId() == 946 ? secondItem.getId() : firstItem.getId());
        if (firstItem.getId() == 1777 || secondItem.getId() == 1777 || firstItem.getId() == 3702 || secondItem.getId() == 3702)
            Fletching.openBowStringSelection(player, firstItem.getId() == 1777 ? secondItem.getId() : firstItem.getId());
        if (firstItem.getId() == 53 || secondItem.getId() == 53 || firstItem.getId() == 52 || secondItem.getId() == 52)
            Fletching.makeArrows(player, firstItem.getId(), secondItem.getId());
        if (firstItem.getId() == 314 || firstItem.getId() == 2950 || secondItem.getId() == 314 || secondItem.getId() == 2950) {
            Fletching.makeDarts(player, secondItem.getId(), firstItem.getId());
            Fletching.makeDarts(player, firstItem.getId(), secondItem.getId());
            Fletching.makeBolts(player, secondItem.getId(), firstItem.getId());
            Fletching.makeBolts(player, firstItem.getId(), secondItem.getId());
        }

        if (secondItem.getId() == 2950 && firstItem.getId() == 52) {
            if (player.getInventory().getAmount(52) >= 100) {
                player.getInventory().delete(52, 100);
                player.getInventory().add(53, 100);
            } else {
                int amount = player.getInventory().getAmount(52);

                player.getInventory().delete(52, amount);
                player.getInventory().add(53, amount);
            }
        }
        if (secondItem.getId() == 1755 || firstItem.getId() == 1755)
            Gems.cutGem(player, 28, firstItem.getId() == 1755 ? secondItem.getId() : firstItem.getId());


        if (firstItem.getId() == 1733 || secondItem.getId() == 1733 || firstItem.getId() == 2951 || secondItem.getId() == 2951)
            LeatherMaking.craftLeatherDialogue(player, firstItem.getId(), secondItem.getId());
        if ((firstItem.getId() == 985 && secondItem.getId() == 987) || (firstItem.getId() == 987 && secondItem.getId() == 985)) {
            int teeth = player.getInventory().getAmount(985);
            int loops = player.getInventory().getAmount(987);
            int maxkeys = 0;

            if (teeth > loops)
                maxkeys = loops;
            else if (loops > teeth)
                maxkeys = teeth;
            else
                maxkeys = teeth;

            if (player.getInventory().getAmount(987) >= maxkeys && player.getInventory().getAmount(985) >= maxkeys) {
                player.getInventory().delete(985, maxkeys);
                player.getInventory().delete(987, maxkeys);
                player.getInventory().add(989, maxkeys);
            }
        }
        if (firstItem.getId() == 227) {
            int[] grimyHerbs = {199, 201, 203, 205, 207, 209, 211, 213, 215, 217, 219, 2485, 3049, 3051};
            int[] unfPots = {91, 93, 95, 97, 99, 101, 103, 105, 107, 109, 111, 2483, 3002, 3004};
            int maxpots = 0;
            int unf = 0;
            int herb = 0;

            for (int i = 0; i < grimyHerbs.length; i++) {
                if (secondItem.getId() == grimyHerbs[i]) {
                    unf = unfPots[i];
                    herb = grimyHerbs[i];
                }
            }

            int herbs = player.getInventory().getAmount(herb);
            int vials = player.getInventory().getAmount(227);

            if (herb > 0 && unf > 0) {

                if (herbs > vials)
                    maxpots = vials;
                else if (vials > herbs)
                    maxpots = herbs;
                else
                    maxpots = herbs;

                if (herbs >= maxpots && vials >= maxpots) {
                    player.getInventory().delete(herb, maxpots);
                    player.getInventory().delete(227, maxpots);
                    player.getInventory().add(unf, maxpots);
                }
            }
        }
        // magic notepaper
        if (firstItem.getId() == 1811 || secondItem.getId() == 1811) {

            if (firstItem.getId() == 1811) {
                if (ItemDefinition.forId(secondItem.getId()).isNoted()) {
                    MagicPaper.unNoteItems(player, secondItem.getId());
                } else if (!ItemDefinition.forId(secondItem.getId()).isNoted()) {
                    MagicPaper.noteItems(player, secondItem.getId());
                }
            }
            else if (secondItem.getId() == 1811) {
                if (ItemDefinition.forId(firstItem.getId()).isNoted()) {
                    MagicPaper.unNoteItems(player, firstItem.getId());
                } else if (!ItemDefinition.forId(firstItem.getId()).isNoted()) {
                    MagicPaper.noteItems(player, firstItem.getId());
                }
            }
        }
        // loading runepouch
        if (firstItem.getId() == 212791 || secondItem.getId() == 212791) {
            if (secondItem.getId() == 212791)
                RunePouch.loadPouch(player, firstItem.getId(), false);
            if (firstItem.getId() == 212791)
                RunePouch.loadPouch(player, secondItem.getId(), false);
        }
        if (firstItem.getId() == 762 || secondItem.getId() == 762) {
            if (secondItem.getId() == 762)
                RunePouch.loadPouch(player, firstItem.getId(), true);
            if (firstItem.getId() == 762)
                RunePouch.loadPouch(player, secondItem.getId(), true);
        }


        //CANNONBALL(new Item[] {new Item(4), new Item(2353)}, new Item(2, 12), new int[] {13, 1, 30}),
        if (firstItem.getId() == 4 || secondItem.getId() == 4) {
            if (firstItem.getId() == 2353 || secondItem.getId() == 2353) {
                int bars = player.getInventory().getAmount(2353);

                player.getInventory().delete(2353, bars);
                player.getInventory().add(2, bars * 8);
                player.getSkillManager().addExperience(Skill.SMITHING, 25 * bars);

            }

        }

        // dirtbag filling
        if (firstItem.getId() == 18339)
            if (secondItem.getId() == 212011) {
                int inventoryCoal = player.getInventory().getAmount(212011);
                for (int i = 0; i < inventoryCoal; i++) {
                    if (player.getCoalBag() > 249) {
                        player.getPacketSender().sendMessage("Your Dirt bag is full.");
                        return;
                    } else {
                        player.getInventory().delete(212011, 1);
                        player.setCoalBag(player.getCoalBag() + 1);
                    }
                }
                player.getPacketSender().sendMessage("You now have " + player.getCoalBag() + " pay-dirt in your Dirt bag.");
            }
        Herblore.handleSpecialPotion(player, secondItem.getId(), firstItem.getId());
        ItemForging.forgeItem(player, secondItem.getId(), firstItem.getId());
        JewelryMaking.craftJewelry(player, secondItem.getId(), firstItem.getId());


        if (player.getStaffRights() == StaffRights.DEVELOPER)
            player.getPacketSender().sendMessage(
                    "ItemOnItem - [firstItem, secondItem] : [" + firstItem.getId() + ", " + secondItem.getId() + "]");
    }

    private static void itemOnObject(Player player, Packet packet) {
        final int lastItemSelectedInterface = packet.readInt();
        final int objectId = packet.readInt();
        final int objectY = packet.readLEShortA();
        final int itemSlot = packet.readLEShort();
        final int objectX = packet.readLEShortA();
        final int itemId = packet.readInt();

        if (itemSlot < 0 || itemSlot > player.getInventory().capacity())
            return;
        final Item item = player.getInventory().getItems()[itemSlot];
        if (item == null)
            return;
        final GameObject gameObject = new GameObject(objectId,
                new Position(objectX, objectY, player.getPosition().getZ()));
        if (objectId > 0 && objectId != 6 && !RegionClipping.objectExists(gameObject)) {
            // player.getPacketSender().sendMessage("An error occured. Error
            // code: "+id).sendMessage("Please report the error to a staff
            // member.");
            return;
        }

        if (player.getStaffRights() == StaffRights.DEVELOPER) {
            player.getPacketSender().sendMessage("item Id: " + itemId + " object id: " + gameObject.getId());
            player.getPacketSender().sendMessage("X: " + objectX + " Y: " + objectY);
        }

        player.getActionTracker().offer(new ActionItemOnObject(objectId, objectX, objectY, lastItemSelectedInterface, itemSlot, itemId));
        player.setInteractingObject(gameObject);
        player.setWalkToTask(new WalkToTask(player, gameObject.getPosition().copy(), gameObject.getSize(),
                new FinalizedMovementTask() {
                    @Override
                    public void execute() {

                        if (player.getLocation() == Location.SHR) {

                            if (player.getRaidsParty() == null) {
                                Location.SHR.leave(player);
                                player.getPacketSender().sendMessage("You were kicked for not being in a Raid Party.");
                            }

                            if (objectX == 3374 && objectY == 9640) {
                                Objects.handleErector(player, itemId);
                            } else if (objectX == 3353 && objectY == 9640) {
                                Objects.handleErector(player, itemId);
                            }

                            if (objectX == 3363 && objectY == 9640) {
                                Objects.handleBossChest(player, itemId);
                            }

                            if (objectX == 3363 && objectY == 9650 && player.getInventory().contains(player.getRaidsParty().shrItem)) {
                                Objects.spawnMob(player);
                            } else if (objectX == 3363 && objectY == 9629 && player.getInventory().contains(player.getRaidsParty().shrItem)) {
                                Objects.spawnMob(player);
                            }


                        }


                        if (CookingData.forFish(item.getId()) != null && CookingData.isRange(objectId)) {
                            player.setPositionToFace(gameObject.getPosition());

                            if (item.getId() == 213439)
                                Cooking.cook(player, 213439, 28);
                            else {
                                Cooking.selectionInterface(player, CookingData.forFish(item.getId()));
                            }

                            return;
                        }
                        if (Prayer.isBone(itemId) && objectId == 409) {
                            BonesOnAltar.offerBones(player, itemId, 28);
                            return;
                        }
                        if (Prayer.isBone(itemId) && gameObject.getPosition().getX() == 2947 && gameObject.getPosition().getY() == 3820) {
                            BonesOnAltar.offerBones(player, itemId, 28);
                            return;
                        }
                        if (Farming.isSeed(itemId) && objectId == 8135) {
                            //SeedsOnPatch.openInterface(player, itemId);
                            player.setSelectedSkillingItem(itemId);
                            SeedsOnPatch.offerSeeds(player, 28);
                            return;
                        }
                        if (gameObject.getPosition().getX() == 1809 && gameObject.getPosition().getY() == 3789 && !ItemDefinition.forId(itemId).isNoted()) {
                            MagicalPumpkin.tryExchange(player, item);
                            return;
                        }
                        if (gameObject.getPosition().getX() == 1809 && gameObject.getPosition().getY() == 3789 && ItemDefinition.forId(itemId).isNoted()) {
                            player.sendMessage("@red@You must use unnoted items on the Magical Pumpkin.");
                            return;
                        }
                        if (gameObject.getPosition().getX() == 1807 && gameObject.getPosition().getY() == 3789 && !ItemDefinition.forId(itemId).isNoted()) {
                            ExchangeTable.tryExchange(player, item);
                            return;
                        }
                        if (gameObject.getPosition().getX() == 1807 && gameObject.getPosition().getY() == 3789 && ExchangeTable.isExchangeable(itemId)) {
                            ExchangeTable.tryExchange(player, item);
                            return;
                        }
                        if (gameObject.getPosition().getX() == 2656 && gameObject.getPosition().getY() == 2600 && !ItemDefinition.forId(itemId).isNoted()) {
                            ExchangeTable.tryExchange(player, item);
                            return;
                        }
                        if (gameObject.getPosition().getX() == 2656 && gameObject.getPosition().getY() == 2600 && ExchangeTable.isExchangeable(itemId)) {
                            ExchangeTable.tryExchange(player, item);
                            return;
                        }

                        //if (player.getFarming().plant(itemId, objectId, objectX, objectY))
                        //return;
                        //if (player.getFarming().useItemOnPlant(itemId, objectX, objectY))
                        //return;
                        if (GameObjectDefinition.forId(objectId) != null && objectId < 300000) {
                            GameObjectDefinition def = GameObjectDefinition.forId(objectId);

                            //GameServer.getLogger().info("ID: " + def.id);
                            //GameServer.getLogger().info("ID: " + def.getName());

                            //Boosted Divine Pool
                            //if(gameObject.getId() == 4005 && itemId == 2439){
                            if ((gameObject.getPosition().getX() == 1800) && (gameObject.getPosition().getY() == 3777)) {

                                if (player.getInventory().getAmount(2439) >= 100 && !player.boostedDivinePool) {
                                    player.getInventory().delete(2439, 100);
                                    player.boostedDivinePool = true;
                                    player.getPacketSender().sendMessage("Your Divine Pools now have the power of Overload+!");
                                    return;
                                } else if (player.boostedDivinePool) {
                                    player.getPacketSender().sendMessage("@red@You have already upgraded the Divine Pool.");
                                    return;
                                } else {
                                    player.getPacketSender().sendMessage("@red@You can use 100 noted Overload+ (4) on this pool to upgrade it.");
                                    return;
                                }


                            }


                            if (def.getName() != null && def.name.toLowerCase().contains("bank")) {
                                ItemDefinition def1 = ItemDefinition.forId(itemId);
                                ItemDefinition def2;
                                int newId = def1.isNoted() ? itemId - 1 : itemId + 1;
                                def2 = ItemDefinition.forId(newId);
                                if (def2 != null && def1.getName().equals(def2.getName())) {
                                    int amt = player.getInventory().getAmount(itemId);
                                    if (!def2.isNoted() && !def2.isStackable()) {
                                        if (amt > player.getInventory().getFreeSlots())
                                            amt = player.getInventory().getFreeSlots();
                                    }
                                    if (amt == 0) {
                                        player.getPacketSender().sendMessage(
                                                "You do not have enough space in your inventory to do that.");
                                        return;
                                    }
                                    player.getInventory().delete(itemId, amt).add(newId, amt);

                                } else {
                                    player.getPacketSender().sendMessage("You cannot do this with that item.");
                                }
                                return;
                            }
								/*else
								{
									player.getPacketSender().sendMessage("Nothing interesting happens..");
									return;
								}*/
                        }


                        //Equipment Upgrades
                        if ((gameObject.getPosition().getX() == 2792) && (gameObject.getPosition().getY() == 9328)) {
                            //player.getPacketSender().sendMessage("item id: " + itemId);

                            if (itemId > 0) {
                                int upgradeType = 100000000;
                                int[] CustomUniques = GameSettings.CUSTOMUNIQUES;
                                int[] MasterUniques = GameSettings.MASTERUNIQUES;
                                int[] LegendaryUniques = GameSettings.LEGENDARYUNIQUES;
                                int[] HighUniques = GameSettings.HIGHUNIQUES;
                                int[] MediumUniques = GameSettings.MEDIUMUNIQUES;
                                int[] LowUniques = GameSettings.LOWUNIQUES;
                                int[] UpgradeableUntradeables = GameSettings.UPGRADEABLE_UNTRADEABLES;
                                int[] WildyUniques = GameSettings.WILDYUNIQUES;


                                switch (itemId) {

                                    case 18895:
                                    case 18896:
                                    case 18898:
                                    case 18847:
                                        player.getInventory().delete(itemId, 1);
                                        player.getInventory().add(219496, 1);
                                        return;
                                    case 22885:
                                        player.getInventory().delete(219493, 1);
                                        player.getInventory().add(219496, 1);
                                        return;
                                }

                                for (int i = 0; i < UpgradeableUntradeables.length; i++) {
                                    if (UpgradeableUntradeables[i] == itemId) {
                                        upgradeType = 7;
                                        break;
                                    }
                                }

                                for (int i = 0; i < CustomUniques.length; i++) {
                                    if (CustomUniques[i] == itemId) {
                                        upgradeType = 6;
                                        break;
                                    }
                                }

                                for (int i = 0; i < MasterUniques.length; i++) {
                                    if (MasterUniques[i] == itemId) {
                                        upgradeType = 5;
                                        break;
                                    }
                                }
                                for (int i = 0; i < LegendaryUniques.length; i++) {
                                    if (LegendaryUniques[i] == itemId) {
                                        upgradeType = 4;
                                        break;
                                    }
                                }
                                for (int i = 0; i < HighUniques.length; i++) {
                                    if (HighUniques[i] == itemId) {
                                        upgradeType = 3;
                                        break;
                                    }
                                }
                                for (int i = 0; i < MediumUniques.length; i++) {
                                    if (MediumUniques[i] == itemId) {
                                        upgradeType = 2;
                                        break;
                                    }
                                }
                                for (int i = 0; i < LowUniques.length; i++) {
                                    if (LowUniques[i] == itemId) {
                                        upgradeType = 1;
                                        break;
                                    }
                                }

                                for (int i = 0; i < WildyUniques.length; i++) {
                                    if (WildyUniques[i] == itemId) {
                                        WildyEquipmentUpgrades.sacrificeItem(player, itemId, false);
                                        return;
                                    }
                                }

                                if (upgradeType > 7) {
                                    player.getPacketSender().sendMessage("@red@This item is not working, please contact an admin.");
                                    return;
                                }

                                EquipmentUpgrades.sacrificeItem(player, itemId, false, upgradeType);
                            } else
                                player.getPacketSender().sendMessage("That does not work here.");

                            return;
                        }


                        //blast furance
                        if ((gameObject.getPosition().getX() == 1943) && ((gameObject.getPosition().getY() == 4966) || (gameObject.getPosition().getY() == 4967))) {
                            if (itemId == 445 || itemId == 448 || itemId == 450 || itemId == 452)
                                BlastFurnace.depositOre(player, itemId);
                            else
                                player.getPacketSender().sendMessage("That does not work here.");
                            return;
                        }

                        if ((gameObject.getPosition().getX() == 1940) && (gameObject.getPosition().getY() == 4963)) {
                            if (itemId == 1811)
                                BlastFurnace.withdrawNotedBars(player);
                            else
                                player.getPacketSender().sendMessage("You can use Magic Paper on this to collect noted bars.");
                            return;
                        }

                        if (objectId > 300000 && (gameObject.getDefinition().getName().toLowerCase().contains("range") || gameObject.getDefinition().getName().toLowerCase().contains("Range"))) {
                            player.setPositionToFace(gameObject.getPosition());
                            Cooking.selectionInterface(player, CookingData.forFish(item.getId()));
                            return;
                        }


                        switch (objectId) {
                            case 312897:
                                WellOfEvents.donate(player, item.getId());
                                return;
                            case 309101:
                            case 309100:
                                if (itemId == 444 || itemId == 447 || itemId == 449 || itemId == 451)
                                    BlastFurnace.depositOre(player, itemId);
                                else
                                    player.getPacketSender().sendMessage("That does not work here.");
                                return;
                            case 409:
                                if (itemId == 222124)
                                    BonesOnAltar.offerBones(player, itemId, player.getInventory().getAmount(222124));
                                return;
                            case 7836:
                            case 7808:
                                if (itemId == 6055) {
                                    int amt = player.getInventory().getAmount(6055);
                                    if (amt > 0) {
                                        player.getInventory().delete(6055, amt);
                                        player.getPacketSender().sendMessage("You put the weed in the compost bin.");
                                        player.getSkillManager().addExperience(Skill.FARMING, 20 * amt);
                                    }
                                }
                                break;
                            case 4306:
                                EquipmentMaking.handleAnvil(player);
                                break;


                        }
                    }
                }));
    }

    private static void itemOnNpc(final Player player, Packet packet) {
        int itemId = packet.readInt();
        int npcId = packet.readUnsignedShortA();
        final int slot = packet.readLEShort();
        int lastItemSelectedInterface = packet.readInt();

        Item usedItem = player.getInventory().forSlot(slot);

        //player.getPacketSender().sendMessage("itemId: " + itemId + ", npcId: " + npcId + ", slot: " + slot + ", lastItem: " + lastItemSelectedInterface);


        switch (npcId) {
            case 367:
                GamblingAction.handleGambleItem(player, usedItem);
                break;
            case 1597:
            case 9085:
            case 8275:
            case 7780:
            case 2909:
                if (player.getInventory().contains(itemId) && SuperiorSlayer.isSlayerKey(itemId)) {
                    player.slayerInstanceKey = itemId;
                    DialogueManager.start(player, 311);
                    player.setDialogueActionId(311);
                }
                break;
            case 2917:

                switch (usedItem.getId()) {


                    case 2948:
                    case 7930:
                    case 964:
                    case 3619:
                        PetAbilities.checkHolidayPets(player, usedItem.getId());
                        break;

                    case 11157:
                    case 212399:
                    case 6550:
                    case 8740:
                    case 212335:
                    case 9941:
                        PetAbilities.checkDonatorPets(player, usedItem.getId());
                        break;

                    default:
                        break;

                }

                break;
        }
    }

    @SuppressWarnings("unused")
    private static void itemOnPlayer(Player player, Packet packet) {
        int interfaceId = packet.readInt();
        int targetIndex = packet.readUnsignedShort();
        int itemId = packet.readInt();
        int slot = packet.readLEShort();
        if (slot < 0 || slot > player.getInventory().capacity() || targetIndex > World.getPlayers().capacity())
            return;
        Player target = World.getPlayers().get(targetIndex);
        if (target == null)
            return;
        player.getActionTracker().offer(new ActionItemOnPlayer(target.getUsername(), interfaceId, slot, itemId));
        switch (itemId) {
            case 962:
                if (!player.getInventory().contains(962) || player.getStaffRights() == StaffRights.ADMINISTRATOR)
                    return;
                player.setPositionToFace(target.getPosition());
                player.performGraphic(new Graphic(1006));
                player.performAnimation(new Animation(451));
                player.getPacketSender().sendMessage("You pull the Christmas cracker...");
                target.getPacketSender().sendMessage(player.getUsername() + " pulls a Christmas cracker on you..");
                player.getInventory().delete(962, 1);
                player.getPacketSender().sendMessage("The cracker explodes and you receive a Party hat!");
                player.getInventory().add(1038 + RandomUtility.inclusiveRandom(11), 1);
                target.getPacketSender().sendMessage(player.getUsername() + " has received a Party hat!");
                /*
                 * if(RandomUtility.getRandom(1) == 1) {
                 * target.getPacketSender().sendMessage(
                 * "The cracker explodes and you receive a Party hat!");
                 * target.getInventory().add((1038 + RandomUtility.getRandom(5)*2), 1);
                 * player.getPacketSender().sendMessage(""+target.getUsername()+
                 * " has received a Party hat!"); } else {
                 * player.getPacketSender().sendMessage(
                 * "The cracker explodes and you receive a Party hat!");
                 * player.getInventory().add((1038 + RandomUtility.getRandom(5)*2), 1);
                 * target.getPacketSender().sendMessage(""+player.getUsername()+
                 * " has received a Party hat!"); }
                 */
                break;
            case 4155:
                if (player.getSlayer().getDuoPartner() != null) {
                    player.getPacketSender().sendMessage("You already have a duo partner.");
                    return;
                }
                if (player.getSlayer().getSlayerTask() != SlayerTasks.NO_TASK) {
                    player.getPacketSender().sendMessage("You already have a Slayer task. You must reset it first.");
                    return;
                }
                Player duoPartner = World.getPlayers().get(targetIndex);
                if (duoPartner != null) {

                    if (duoPartner.getGameMode() == GameMode.SEASONAL_IRONMAN) {

                        if (player.seasonalLeader != duoPartner.seasonalLeader) {
                            player.getPacketSender().sendMessage("Seasonal ironman accounts can only Duo with their Seasonal partner.");
                            return;
                        }

                    }
                    if (duoPartner.getSlayer().getDuoPartner() != null) {
                        player.getPacketSender().sendMessage("This player already has a duo partner.");
                        return;
                    }
                    if (duoPartner.getSlayer().getSlayerTask() != SlayerTasks.NO_TASK) {
                        player.getPacketSender().sendMessage("This player already has a Slayer task.");
                        return;
                    }
                    if (duoPartner.getSlayer().getSlayerMaster() != player.getSlayer().getSlayerMaster()) {
                        player.getPacketSender().sendMessage("You do not have the same Slayer master as that player.");
                        return;
                    }
                    if (duoPartner.busy() || duoPartner.getLocation() == Location.WILDERNESS) {
                        player.getPacketSender().sendMessage("This player is currently busy.");
                        return;
                    }
                    DialogueManager.start(duoPartner, SlayerDialogues.inviteDuo(duoPartner, player));
                    player.getPacketSender()
                            .sendMessage("You have invited " + duoPartner.getUsername() + " to join your Slayer duo team.");
                }
                break;

            //Donation tokens
            case 19864:
                if (player.getGameMode() == GameMode.SEASONAL_IRONMAN || target.getGameMode() == GameMode.SEASONAL_IRONMAN) {
                    player.getPacketSender().sendMessage("Seasonal Ironman accounts can't send/receive donations.");
                    target.getPacketSender().sendMessage("Seasonal Ironman accounts can't send/receive donations.");
                    break;
                }

                if (target.getInventory().getFreeSlots() > 0) {
                    if (player.getInventory().contains(19864)) {
                        player.interactingPlayer = target;
                        TransferDonator.selectionInterface(player);
                    }
                }
                break;
            //add donator scrolls here
            case 15356:
            case 15355:
            case 15359:
            case 15358:

                if(player.getGameMode() == GameMode.SEASONAL_IRONMAN || target.getGameMode() == GameMode.SEASONAL_IRONMAN) {
                    player.getPacketSender().sendMessage("Seasonal Ironman accounts can't send/receive donations.");
                    target.getPacketSender().sendMessage("Seasonal Ironman accounts can't send/receive donations.");
                }

                if (target.getInventory().getFreeSlots() > 0) {
                    if (player.getInventory().contains(itemId)) {
                        String bondType = "";

                        switch (itemId) {
                            case 15356:
                                bondType = "$5";
                                break;
                            case 15355:
                                bondType = "$10";
                                break;
                            case 15359:
                                bondType = "$20";
                                break;
                            case 15358:
                                bondType = "$50";
                                break;
                        }

                        player.getInventory().delete(itemId, 1);
                        target.getInventory().add(itemId, 1);
                        String eventLog = player.getUsername() + " gave a " + bondType + " bond to " + target.getUsername();
                        PlayerLogs.log("Bond Swap", eventLog);
                    } else {
                        player.getPacketSender().sendMessage("@red@This event has been logged. Do not do this again.");
                        String eventLog = player.getUsername() + " attempted to give a bond to " + target.getUsername() + " without having a bond.";
                        PlayerLogs.log("Event Log", eventLog);
                    }
                }
                break;
        }
    }

    @Override
    public void handleMessage(Player player, Packet packet) {
        if (player.getConstitution() <= 0)
            return;
        switch (packet.getOpcode()) {
            case ITEM_ON_ITEM:
                itemOnItem(player, packet);
                break;
            case USE_ITEM:
                useItem(player, packet);
                break;
            case ITEM_ON_OBJECT:
                itemOnObject(player, packet);
                break;
            case ITEM_ON_GROUND_ITEM:
                // TODO
                break;
            case ITEM_ON_NPC:
                itemOnNpc(player, packet);
                break;
            case ITEM_ON_PLAYER:
                itemOnPlayer(player, packet);
                break;
        }
    }

    public final static int USE_ITEM = 122;

    public final static int ITEM_ON_NPC = 57;

    public final static int ITEM_ON_ITEM = 53;

    public final static int ITEM_ON_OBJECT = 192;

    public final static int ITEM_ON_GROUND_ITEM = 25;

    public static final int ITEM_ON_PLAYER = 14;
}
