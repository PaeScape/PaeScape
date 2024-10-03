package net.runelite.client.plugins.groundmarker;

import com.google.common.base.Strings;
import com.google.common.util.concurrent.Runnables;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Provides;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.chatbox.ChatboxPanelManager;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.components.colorpicker.ColorPickerManager;
import net.runelite.client.ui.components.colorpicker.RuneliteColorPicker;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.ColorUtil;

import javax.inject.Inject;
import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="https://rune-server.org/members/294280-era_/">Era</a> || 2/18/2024 - 1:06 PM
 * @Discord <a href="https://discordapp.com/users/232266021413584896">era_rsps</a>
 */
@Slf4j
@PluginDescriptor(
        name = "Ground Markers",
        description = "Enable marking of tiles using the Shift key",
        tags = {"overlay", "tiles"}
)
public class GroundMarkerPlugin extends Plugin {
    private static final String CONFIG_GROUP = "groundMarker";
    private static final String WALK_HERE = "Walk here";
    private static final String REGION_PREFIX = "region_";

    @Getter(AccessLevel.PACKAGE)
    private final List<ColorTileMarker> points = new ArrayList<>();

    @Inject
    private Client client;

    @Inject
    private GroundMarkerConfig config;

    @Inject
    private ConfigManager configManager;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private GroundMarkerOverlay overlay;
    @Inject
    private ChatboxPanelManager chatboxPanelManager;

    @Inject
    private EventBus eventBus;
    @Inject
    private Gson gson;

    @Inject
    private ColorPickerManager colorPickerManager;

    void savePoints(int regionId, Collection<GroundMarkerPoint> points)
    {
        if (points == null || points.isEmpty())
        {
            configManager.unsetConfiguration(CONFIG_GROUP, REGION_PREFIX + regionId);
            return;
        }

        String json = gson.toJson(points);
        configManager.setConfiguration(CONFIG_GROUP, REGION_PREFIX + regionId, json);
    }

    Collection<GroundMarkerPoint> getPoints(int regionId)
    {
        String json = configManager.getConfiguration(CONFIG_GROUP, REGION_PREFIX + regionId);
        if (Strings.isNullOrEmpty(json))
        {
            return Collections.emptyList();
        }

        // CHECKSTYLE:OFF
        return gson.fromJson(json, new TypeToken<List<GroundMarkerPoint>>(){}.getType());
        // CHECKSTYLE:ON
    }

    @Provides
    GroundMarkerConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(GroundMarkerConfig.class);
    }

    void loadPoints()
    {
        points.clear();

        int[] regions = client.getMapRegions();

        if (regions == null)
        {
            return;
        }

        for (int regionId : regions)
        {
            // load points for region
            log.debug("Loading points for region {}", regionId);
            Collection<GroundMarkerPoint> regionPoints = getPoints(regionId);
            Collection<ColorTileMarker> colorTileMarkers = translateToColorTileMarker(regionPoints);
            points.addAll(colorTileMarkers);
        }
    }

    /**
     * Translate a collection of ground marker points to color tile markers, accounting for instances
     *
     * @param points {@link GroundMarkerPoint}s to be converted to {@link ColorTileMarker}s
     * @return A collection of color tile markers, converted from the passed ground marker points, accounting for local
     *         instance points. See {@link WorldPoint#toLocalInstance(Client, WorldPoint)}
     */
    private Collection<ColorTileMarker> translateToColorTileMarker(Collection<GroundMarkerPoint> points)
    {
        if (points.isEmpty())
        {
            return Collections.emptyList();
        }

        return points.stream()
                .map(point -> new ColorTileMarker(
                        WorldPoint.fromRegion(point.getRegionId(), point.getRegionX(), point.getRegionY(), point.getZ()),
                        point.getColor(), point.getLabel()))
                .flatMap(colorTile ->
                {
                    final Collection<WorldPoint> localWorldPoints = WorldPoint.toLocalInstance(client, colorTile.getWorldPoint());
                    return localWorldPoints.stream().map(wp -> new ColorTileMarker(wp, colorTile.getColor(), colorTile.getLabel()));
                })
                .collect(Collectors.toList());
    }

    @Override
    public void startUp()
    {
        overlayManager.add(overlay);
        loadPoints();
    }

    @Override
    public void shutDown()
    {
        overlayManager.remove(overlay);
        points.clear();
    }

    @Subscribe
    public void onGameStateChanged(GameStateChanged gameStateChanged)
    {
        if (gameStateChanged.getGameState() != GameState.LOGGED_IN)
        {
            return;
        }

        // map region has just been updated
        loadPoints();
    }

    @Subscribe
    public void onMenuEntryAdded(MenuEntryAdded event)
    {
        final boolean hotKeyPressed = client.isKeyPressed(KeyEvent.VK_SHIFT);
        System.out.println("hotKeyPressed="+hotKeyPressed);
        if (hotKeyPressed)
        {
            final Tile selectedSceneTile = event.getSelectedTile();

            if (selectedSceneTile == null)
            {
                System.out.println("selectedSceneTile == null");
                return;
            }

            final WorldPoint worldPoint = WorldPoint.fromLocalInstance(client, selectedSceneTile.getLocalLocation());
            final int regionId = worldPoint.getRegionID();
            var existingOpt = getPoints(regionId).stream()
                    .filter(p -> p.getRegionX() == worldPoint.getRegionX() && p.getRegionY() == worldPoint.getRegionY() && p.getZ() == worldPoint.getPlane())
                    .findFirst();

            client.createMenu(-1,existingOpt.isPresent() ? "Unmark" : "Mark",
                    "Tile",
                    MenuAction.RUNELITE,
                    -1,
                    -1,
                    e ->
                    {
                        Tile target = event.getSelectedTile();
                        if (target != null)
                        {
                            markTile(target.getLocalLocation());

                        }
                    });

            if (existingOpt.isPresent())
            {
                var existing = existingOpt.get();
                client.createMenu(-3,"Pick Color",
                        "",
                        MenuAction.RUNELITE,
                        -1,
                        -1,
                        e ->
                        {
                            Color color = existing.getColor();
                            SwingUtilities.invokeLater(() ->
                            {
                                RuneliteColorPicker colorPicker = colorPickerManager.create(SwingUtilities.windowForComponent((Applet) client),
                                        color, "Tile marker color", true);
                                colorPicker.setOnClose(c -> colorTile(existing, c));
                                colorPicker.setVisible(true);
                            });
                        });
            }
        }
    }

    private void markTile(LocalPoint localPoint)
    {
        if (localPoint == null)
        {
            return;
        }

        WorldPoint worldPoint = WorldPoint.fromLocalInstance(client, localPoint);

        int regionId = worldPoint.getRegionID();
        GroundMarkerPoint point = new GroundMarkerPoint(regionId, worldPoint.getRegionX(), worldPoint.getRegionY(), worldPoint.getPlane(), config.markerColor(), null);
        log.debug("Updating point: {} - {}", point, worldPoint);

        List<GroundMarkerPoint> groundMarkerPoints = new ArrayList<>(getPoints(regionId));
        if (groundMarkerPoints.contains(point))
        {
            groundMarkerPoints.remove(point);
        }
        else
        {
            groundMarkerPoints.add(point);
        }
        savePoints(regionId, groundMarkerPoints);

        loadPoints();
    }

    private void colorTile(GroundMarkerPoint existing, Color newColor)
    {
        var newPoint = new GroundMarkerPoint(existing.getRegionId(), existing.getRegionX(), existing.getRegionY(), existing.getZ(), newColor, existing.getLabel());
        Collection<GroundMarkerPoint> points = new ArrayList<>(getPoints(existing.getRegionId()));
        points.remove(newPoint);
        points.add(newPoint);
        savePoints(existing.getRegionId(), points);

        loadPoints();
    }
}
