package online.paescape.media;

import online.paescape.Client;
import online.paescape.cache.media.Background;

public class TextureAnimating {

    private static final int[] Animated_Textures = {17, 24, 34, 40, 59, 1};

    private static byte[] pixels = new byte[16384];

    /**
     * Animates on screen textures with a certain id.
     */
    public static void animateTexture() {
        try {
            for (int textureId : Animated_Textures) {
                Background image = Rasterizer.aBackgroundArray1474s[textureId];
                int indexes = image.imgWidth * image.imgHeight - 1;
                int noise = image.imgWidth * Client.instance.cycleTimer * 2;
                byte[] current = image.imgPixels;
                byte[] next = pixels;
                for (int i2 = 0; i2 <= indexes; i2++) {
                    next[i2] = current[i2 - noise & indexes];
                }
                image.imgPixels = next;
                pixels = current;
                Rasterizer.method370(textureId);
            }
        } catch (Exception ex) {

        }
    }
}
