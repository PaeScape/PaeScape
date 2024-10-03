package com.arlania.net.packet.impl;

import com.arlania.DiscordBot;
import com.arlania.GameSettings;
import com.arlania.model.*;
import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketListener;
import com.arlania.world.World;
import com.arlania.world.content.PlayerLogs;
import com.arlania.world.content.PlayerPunishment.Jail;
import com.arlania.world.content.Sounds;
import com.arlania.world.content.Sounds.Sound;
import com.arlania.world.content.skill.impl.dungeoneering.ItemBinding;
import com.arlania.world.entity.impl.GroundItemManager;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.antibotting.actions.ActionDropItem;
import org.javacord.api.entity.message.MessageBuilder;

/**
 * This packet listener is called when a player drops an item they
 * have placed in their inventory.
 *
 * @author relex lawl
 */

public class DropItemPacketListener implements PacketListener {

    public static void destroyItemInterface(Player player, Item item) {//Destroy item created by Remco
        player.setUntradeableDropItem(item);
        String[][] info = {//The info the dialogue gives
                {"Are you sure you want to discard this item?", "14174"},
                {"Yes.", "14175"}, {"No.", "14176"}, {"", "14177"},
                {"This item will vanish once it hits the floor.", "14182"}, {"You cannot get it back if discarded.", "14183"},
                {item.getDefinition().getName(), "14184"}};
        player.getPacketSender().sendItemOnInterface(14171, item.getId(), 0, item.getAmount());
        for (int i = 0; i < info.length; i++)
            player.getPacketSender().sendString(Integer.parseInt(info[i][1]), info[i][0]);
        player.getPacketSender().sendChatboxInterface(14170);
    }

    @Override
    public void handleMessage(Player player, Packet packet) {
        int id = packet.readInt();
        @SuppressWarnings("unused")
        int interfaceIndex = packet.readUnsignedShort();
        int itemSlot = packet.readUnsignedShortA();
        if (player.getConstitution() <= 0 || player.getInterfaceId() > 0)
            return;
        if (itemSlot < 0 || itemSlot > player.getInventory().capacity())
            return;
        if (player.getConstitution() <= 0 || player.isTeleporting())
            return;
        Item item = player.getInventory().getItems()[itemSlot];
        if (item.getId() != id) {
            return;
        }

        player.getActionTracker().offer(new ActionDropItem(id, interfaceIndex, itemSlot));
        player.getPacketSender().sendInterfaceRemoval();
        player.getCombatBuilder().cooldown(false);
        if (item != null && item.getId() != -1 && item.getAmount() >= 1) {
            if (item.tradeable() && !ItemBinding.isBoundItem(item.getId())) {
                player.getInventory().setItem(itemSlot, new Item(-1, 0)).refreshItems();
                if (item.getId() == 4045) {
                    if (Jail.isJailed(player)) {
                        return;
                    }
                    player.dealDamage(new Hit((player.getConstitution() - 1) == 0 ? 1 : player.getConstitution() - 1, Hitmask.CRITICAL, CombatIcon.BLUE_SHIELD));
                    player.performGraphic(new Graphic(1750));
                    player.getPacketSender().sendMessage("The potion explodes in your face as you drop it!");
                } else {

                    if (item.getDefinition().value > 10000) {
                        String log = " dropped " + item.getAmount() + " " + item.getDefinition().getName() + " in location: " + player.getLocation();

                        PlayerLogs.log(player.getUsername(), log);

                        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                            new MessageBuilder().append(player.getUsername() + log).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_DROPPED_ITEMS_CH).get());

                        World.getPlayers().stream().filter(p -> p != null && (p.getLocation() == player.getLocation())).forEach(p -> new MessageBuilder().append(p.getUsername() + " is in Location: " + player.getLocation()).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_DROPPED_ITEMS_CH).get()));
                    }

                    if (!player.canTransferWealth()) {
                        GroundItemManager.spawnGroundItem(player, new GroundItem(item, player.getPosition().copy(), player.getUsername(), player.getHostAddress(), false, 100, false, 100));
                    } else if (player.getLocation() == Locations.Location.SHR) {
                        if (item.getId() == 6898 || item.getId() == 6899 || item.getId() == 6900 || item.getId() == 6901) {

                        } else {
                            GroundItemManager.spawnGroundItem(player, new GroundItem(item, player.getPosition().copy(), player.getUsername(), player.getHostAddress(), true, 100, player.getPosition().getZ() >= 0 && player.getPosition().getZ() < 4, 1000));
                        }
                    } else {
                        GroundItemManager.spawnGroundItem(player, new GroundItem(item, player.getPosition().copy(), player.getUsername(), player.getHostAddress(), false, 100, player.getPosition().getZ() >= 0 && player.getPosition().getZ() < 4, 100));
                    }
                    //PlayerLogs.log(player.getUsername(), "Player dropping item: " + item.getId() + ", amount: " + item.getAmount());
                }
                Sounds.sendSound(player, Sound.DROP_ITEM);
            } else
                destroyItemInterface(player, item);
        }
    }
}
