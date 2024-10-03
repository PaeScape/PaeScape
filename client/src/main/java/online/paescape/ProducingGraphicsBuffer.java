package online.paescape;

import net.runelite.rs.api.RSRasterProvider;
import online.paescape.media.DrawingArea;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.util.Hashtable;

/**
 * @author <a href="https://rune-server.org/members/294280-era_/">Era</a> || 1/31/2024 - 4:41 PM
 * @Discord <a href="https://discordapp.com/users/232266021413584896">era_rsps</a>
 */
public class ProducingGraphicsBuffer extends AbstractRasterProvider implements RSRasterProvider {
    private Component component;
    private Image image;

    public ProducingGraphicsBuffer(int width, int height, Component component) {

        super.width = width;
        super.height = height;
        super.myPixels = new int[height * width + 1];
        DataBufferInt var4 = new DataBufferInt(super.myPixels, super.myPixels.length);
        DirectColorModel var5 = new DirectColorModel(32, 16711680, 65280, 255);
        WritableRaster var6 = Raster.createWritableRaster(var5.createCompatibleSampleModel(super.width, super.height), var4, (Point) null);
        this.image = new BufferedImage(var5, var6, false, new Hashtable());
        this.setComponent(component);
        this.apply();
        this.init(this.width, this.height, component);
    }

    public void init(int width, int height, Component canvas) {
        if (!Client.instance.isGpu()) {
            return;
        }

        final int[] pixels = getPixels();

        // we need to make our own buffered image for the client with the alpha channel enabled in order to
        // have alphas for the overlays applied correctly
        DataBufferInt dataBufferInt = new DataBufferInt(pixels, pixels.length);
        DirectColorModel directColorModel = new DirectColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
                32, 0xff0000, 0xff00, 0xff, 0xff000000,
                true, DataBuffer.TYPE_INT);
        WritableRaster writableRaster = Raster.createWritableRaster(directColorModel.createCompatibleSampleModel(width, height), dataBufferInt, null);
        BufferedImage bufferedImage = new BufferedImage(directColorModel, writableRaster, true, new Hashtable());

        setImage(bufferedImage);
    }

    public final void setComponent(Component var1) {
        this.component = var1;
    }

    public void drawFull(int var1, int var2) {
        this.drawGraphics(this.component.getGraphics(), var1, var2);
    }

    public final void draw(int var1, int var2, int var3, int var4) {
        this.draw0(this.component.getGraphics(), var1, var2, var3, var4); // L: 45
    }

    final void drawGraphics(Graphics var1, int var2, int var3) {//drawFull0
        Client.instance.getCallbacks().draw(this, var1, var2, var3);
    }

    final void draw0(Graphics var1, int var2, int var3, int var4, int var5) {
        try {
            Shape var6 = var1.getClip();
            var1.clipRect(var2, var3, var4, var5);
            var1.drawImage(this.image, 0, 0, this.component);
            var1.setClip(var6);
        } catch (Exception var7) {
            this.component.repaint();
        }

    }

    @Override
    public int[] getPixels() {
        return myPixels;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setRaster() {
        DrawingArea.initDrawingArea(height, width, myPixels);
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public Component getCanvas() {
        return component;
    }
}
