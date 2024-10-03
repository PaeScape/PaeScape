package net.runelite.client.plugins.paescape;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.LinkBrowser;
import okhttp3.OkHttpClient;

import javax.inject.Inject;
import java.awt.image.BufferedImage;

@Slf4j
@PluginDescriptor(
        name = "PaeScape",
        description = "",
        tags = {"paescape"},
        hidden = true
)
public class PaeScapePlugin extends Plugin {
    public static final String WIKI_LINK = "https://wiki.paescape.online/";
    public static final String DONATE_LINK = "";
    public static final String HIGHSCORES_LINK = "https://highscores.paescape.online/";
    public static final String VOTE_LINK = "";

    @Inject
    private Client client;

    @Inject
    private PaeScapeConfig config;

    @Inject
    private ClientToolbar clientToolbar;

    private NavigationButton wikiButton;
    private NavigationButton donateButton;
    private NavigationButton highscoresButton;
    private NavigationButton voteButton;

    @Provides
    PaeScapeConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(PaeScapeConfig.class);
    }

    @Override
    protected void startUp() {
        log.info("PaeScape plugin started!");

        final BufferedImage wikiIcon = ImageUtil.getResourceStreamFromClass(getClass(), "wiki.png");
        wikiButton = NavigationButton.builder()
                .tooltip("PaeScape Wiki")
                .priority(0)
                .icon(wikiIcon)
                .onClick(() -> LinkBrowser.browse(WIKI_LINK))
                .build();
        final BufferedImage donateIcon = ImageUtil.getResourceStreamFromClass(getClass(), "donate.png");
        donateButton = NavigationButton.builder()
                .tooltip("Donate to PaeScape")
                .priority(0)
                .icon(donateIcon)
                .onClick(() -> LinkBrowser.browse(DONATE_LINK))
                .build();
        final BufferedImage highscoresIcon = ImageUtil.getResourceStreamFromClass(getClass(), "highscores.png");
        highscoresButton = NavigationButton.builder()
                .tooltip("PaeScape HighScores")
                .priority(0)
                .icon(highscoresIcon)
                .onClick(() -> LinkBrowser.browse(HIGHSCORES_LINK))
                .build();
        final BufferedImage voteIcon = ImageUtil.getResourceStreamFromClass(getClass(), "vote.png");
        voteButton = NavigationButton.builder()
                .tooltip("Vote for PaeScape")
                .priority(0)
                .icon(voteIcon)
                .onClick(() -> LinkBrowser.browse(VOTE_LINK))
                .build();

        clientToolbar.addNavigation(wikiButton);
        clientToolbar.addNavigation(donateButton);
        clientToolbar.addNavigation(highscoresButton);
        clientToolbar.addNavigation(voteButton);

        updateConfig();
    }

    @Override
    protected void shutDown() throws Exception {

        clientToolbar.removeNavigation(wikiButton);
        clientToolbar.removeNavigation(donateButton);
        clientToolbar.removeNavigation(highscoresButton);
        clientToolbar.removeNavigation(voteButton);

        log.info("PaeScape plugin stopped!");
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged event) {
        if (!event.getGroup().equals("paescape")) {
            return;
        }

        updateConfig();
    }

    private void updateConfig() {

    }
}
