package com.arlania.world.content.teleport;

import com.arlania.model.GameMode;
import com.arlania.net.packet.PacketSender;
import com.arlania.world.content.seasonal.SeasonalTeleportHandler;
import com.arlania.world.content.transportation.TeleportHandler;
import com.arlania.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TeleportInterface {
    private static final int MAX_TELES = 50;
    private static final int MAX_FAVOURITES = 10;
    private static final int CATEGORY_CONFIG = 1096;
    private static final int SELECTED_TELE_CONFIG = 1095;
    private static final int SELECTED_FAVOURITE_CONFIG = 1094;
    private static final int INTERFACE_ID = 20200;
    private static final int CLOSE_BUTTON = 20202;
    private static final int TELEPORT_BUTTON = 20206;
    private static final int NPC_DISPLAY = 20212;
    private static final int FAVOURITE_BUTTON_START_ID = 20213;
    private static final int FAVOURITE_TEXT_START_ID = 20233;
    private static final int FAVOURITE_CROSS_START_ID = 20243;
    private static final int ITEM_CONTAINER_ID = 20264;
    private static final int CATEGORY_START_ID = 20265;
    private static final int SCROLL_BAR = 20283;
    private static final int BUTTON_START_ID = 20284;
    private static final int TEXT_START_ID = 20284 + MAX_TELES * 2;
    private static final int ADD_FAVOURITE_START_ID = 20284 + MAX_TELES * 3;

    private final Player player;
    private final List<TeleportData> favourites = new ArrayList<>();
    private final List<TeleportData> currentTeles = new ArrayList<>();
    private TeleportData selected = null;
    private TeleportCategory category = null;


    public TeleportInterface(Player player) {
        this.player = player;
    }

    public void open() {
        player.getPacketSender().sendInterface(INTERFACE_ID);
        if (category == null) {
            changeCategory(TeleportCategory.CITIES);
        }
        refresh();
        if (selected == null) {
            selectTele(0);
        }
    }

    public void open(TeleportCategory category) {
        player.getPacketSender().sendInterface(INTERFACE_ID);
        changeCategory(category);
        refresh();
        if (selected == null) {
            selectTele(0);
        }
    }

    private void refresh() {
        refreshCategoryAndList();
        refreshFavourites();
        refreshSelectedTele();
    }

    private void refreshCategoryAndList() {
        PacketSender out = player.getPacketSender();
        out.sendConfig(CATEGORY_CONFIG, category.index);
        int index = 0;

        for (TeleportData tele : currentTeles) {
            out.sendString(TEXT_START_ID + index, tele.getName());
            index++;
        }
        for (; index < MAX_TELES; index++) {
            out.sendString(TEXT_START_ID + index, "");
        }

        int teleSize = currentTeles.size();
        out.sendScrollMaxChange(SCROLL_BAR, Math.max(20 * teleSize, 201));

        // hide the hearts if there is no tele next to it only required for the first 10 as scroll bar max size change hides the rest.
        for (int i = 0; i < 10; i++) {
            out.sendInterfaceDisplayState(ADD_FAVOURITE_START_ID + i, i >= teleSize);
        }

        int i = 0;
        for (TeleportData tele : currentTeles) {
            out.sendConfig(1097 + i, favourites.contains(tele) ? 1 : 0);
            i++;
        }
        refreshSelectedTele();
        refreshSelectedFavourite();
    }

    private void refreshFavourites() {
        PacketSender out = player.getPacketSender();
        int i = 0;
        for (TeleportData favourite : favourites) {
            out.sendString(FAVOURITE_TEXT_START_ID + i, favourite.getName());
            out.sendInterfaceDisplayState(FAVOURITE_CROSS_START_ID + i, false);
            i++;
        }
        for (; i < MAX_FAVOURITES; i++) {
            out.sendString(FAVOURITE_TEXT_START_ID + i, "");
            out.sendInterfaceDisplayState(FAVOURITE_CROSS_START_ID + i, true);
        }
        refreshSelectedFavourite();
    }

    private void refreshSelectedTele() {
        if (selected == null) {
            return;
        }
        PacketSender out = player.getPacketSender();
        out.sendConfig(SELECTED_TELE_CONFIG, currentTeles.indexOf(selected));
        out.sendItemsOnInterface(ITEM_CONTAINER_ID, selected.getItems());
        out.sendNpcDisplay(NPC_DISPLAY, selected.getNpcId(), selected.getNpcZoom());
    }

    public boolean handleButton(int id) {
        if (id == CLOSE_BUTTON) {
            player.getPacketSender().sendInterfaceRemoval();
            return true;
        }
        if (id == TELEPORT_BUTTON) {
            if (selected != null) {

                if (player.getGameMode() != GameMode.SEASONAL_IRONMAN)
                    TeleportHandler.teleportPlayer(player, selected.getPosition(), player.getSpellbook().getTeleportType());
                else
                    SeasonalTeleportHandler.Teleport(player, selected.getPosition(), player.getSpellbook().getTeleportType());
            }
            return true;
        }
        if (id >= BUTTON_START_ID && id <= BUTTON_START_ID + MAX_TELES) {
            int index = id - BUTTON_START_ID;
            selectTele(index);
            return true;
        }
        if (id >= FAVOURITE_BUTTON_START_ID && id <= FAVOURITE_BUTTON_START_ID + MAX_FAVOURITES) {
            int index = id - FAVOURITE_BUTTON_START_ID;
            selectFavourite(index);
            return true;
        }
        if (id >= FAVOURITE_CROSS_START_ID && id <= FAVOURITE_CROSS_START_ID + MAX_FAVOURITES) {
            int index = id - FAVOURITE_CROSS_START_ID;
            removeFavourite(index);
            return true;
        }
        if (id >= ADD_FAVOURITE_START_ID && id <= ADD_FAVOURITE_START_ID + MAX_TELES) {
            int index = id - ADD_FAVOURITE_START_ID;
            tryToggleFavourite(index);
            return true;
        }
        if (id >= CATEGORY_START_ID && id <= CATEGORY_START_ID + 6) {
            int index = id - CATEGORY_START_ID;
            changeCategory(TeleportCategory.forIndex(index));
            refreshCategoryAndList();
            player.getPacketSender().setScrollBar(SCROLL_BAR, 0);
            return true;
        }
        return false;
    }

    private void removeFavourite(int index) {
        if (index >= favourites.size())
            return;
        TeleportData removed = favourites.remove(index);
        sendRemoveFavouriteMsg(removed);
        refreshFavourites();
        refreshCategoryAndList();
    }

    private void selectFavourite(int index) {
        if (index >= favourites.size()) {
            refreshSelectedFavourite();
            return;
        }
        selected = favourites.get(index);
        refreshSelectedTele();
        refreshSelectedFavourite();
    }

    private void refreshSelectedFavourite() {
        int index = favourites.indexOf(selected);
        player.getPacketSender().sendConfig(SELECTED_FAVOURITE_CONFIG, index < 0 ? 11 : index);
    }

    private void sendRemoveFavouriteMsg(TeleportData tele) {
        player.sendMessage("You removed " + tele.getName() + " from your favourite teleports.");
    }

    private void tryToggleFavourite(int index) {
        if (index >= currentTeles.size())
            return;
        TeleportData toToggle = currentTeles.get(index);

        if (favourites.remove(toToggle)) {
            sendRemoveFavouriteMsg(toToggle);
            refreshFavourites();
            refreshCategoryAndList();
            return;
        }

        if (favourites.size() >= MAX_FAVOURITES) {
            player.sendMessage("Your favourites are currently full.");
            return;
        }

        favourites.add(toToggle);
        player.sendMessage("You added " + toToggle.getName() + " to your favourite teleports.");
        refreshFavourites();
        refreshCategoryAndList();
    }

    private void selectTele(int index) {
        if (index >= currentTeles.size()) {
            refreshSelectedTele();
            return;
        }
        selected = currentTeles.get(index);
        refreshSelectedTele();
        refreshSelectedFavourite();
    }

    private void changeCategory(TeleportCategory newCategory) {
        category = newCategory;
        currentTeles.clear();
        for (TeleportData data : TeleportData.values) {
            if (data.getCategory().equals(category)) {
                currentTeles.add(data);
            }
        }
    }

    public List<String> getFavourites() {
        List<String> list = new LinkedList<>();
        for (TeleportData favourite : favourites) {
            if (favourite != null) {
                list.add(favourite.name());
            }
        }
        return list;
    }

    public void setFavourites(String[] names) {
        for (String name : names) {
            for (TeleportData tele : TeleportData.values) {
                if (tele.name().equals(name)) {
                    favourites.add(tele);
                }
            }
        }
    }
}
