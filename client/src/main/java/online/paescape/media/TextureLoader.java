package online.paescape.media;

/**
 * @author <a href="https://rune-server.org/members/294280-era_/">Era</a> || 2/1/2024 - 1:12 AM
 * @Discord <a href="https://discordapp.com/users/232266021413584896">era_rsps</a>
 */
public interface TextureLoader {

    int[] getTexturePixels(int var1);

    int getAverageTextureRGB(int var1);

    boolean isTransparent(int var1);

    boolean isLowDetail(int var1);

}
