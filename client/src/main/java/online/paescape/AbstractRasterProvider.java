package online.paescape;

import net.runelite.rs.api.RSAbstractRasterProvider;
import online.paescape.media.DrawingArea;

/**
 * @author <a href="https://rune-server.org/members/294280-era_/">Era</a> || 1/31/2024 - 4:23 PM
 * @Discord <a href="https://discordapp.com/users/232266021413584896">era_rsps</a>
 */
public abstract class AbstractRasterProvider implements RSAbstractRasterProvider {
    public int[] myPixels;
    public int width;
    public int height;

    public abstract void drawFull(int var1, int var2);
    public abstract void draw(int var1, int var2, int var3, int var4);

    public final void apply() {
        DrawingArea.initDrawingArea(this.height, this.width,this.myPixels); // L: 11
    }

}
