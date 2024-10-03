package online.paescape.media;
// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.awt.*;
import java.awt.image.*;

public final class RSImageProducer
        implements ImageProducer, ImageObserver {

    public final int[] myPixels;
    public final int width;
    public final int height;
    private final ColorModel colorModel;
    private final Image image;
    private ImageConsumer imageConsumer;

    public RSImageProducer(int width, int height, Component component) {
        this.width = width;
        this.height = height;
        myPixels = new int[width * height];
        colorModel = new DirectColorModel(32, 0xff0000, 0x00ff00, 0x0000ff);
        image = component.createImage(this);
        component.prepareImage(image, this);
        update();
        initDrawingArea();
    }

    public void initDrawingArea() {
        DrawingArea.initDrawingArea(height, width, myPixels);
    }

    public void drawGraphics(Graphics g, int x, int y) {
        update();
        g.drawImage(image, x, y, this);
    }

    public synchronized void addConsumer(ImageConsumer imageconsumer) {
        imageConsumer = imageconsumer;
        imageconsumer.setDimensions(width, height);
        imageconsumer.setProperties(null);
        imageconsumer.setColorModel(colorModel);
        imageconsumer.setHints(ImageConsumer.SINGLEFRAME);
    }

    public synchronized boolean isConsumer(ImageConsumer imageconsumer) {
        return imageConsumer == imageconsumer;
    }

    public synchronized void removeConsumer(ImageConsumer imageconsumer) {
        if (imageConsumer == imageconsumer)
            imageConsumer = null;
    }

    public void startProduction(ImageConsumer imageconsumer) {
        addConsumer(imageconsumer);
    }

    public void requestTopDownLeftRightResend(ImageConsumer imageconsumer) {
    }

    private synchronized void update() {
        if (imageConsumer != null) {
            imageConsumer.setPixels(0, 0, width, height, colorModel, myPixels, 0, width);
            imageConsumer.imageComplete(ImageConsumer.SINGLEFRAMEDONE);
        }
    }

    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return true;
    }
}
