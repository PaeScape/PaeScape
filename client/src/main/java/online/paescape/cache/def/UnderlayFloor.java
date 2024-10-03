package online.paescape.cache.def;

import online.paescape.cache.CacheArchive;
import online.paescape.net.Stream;

public final class UnderlayFloor {

    public static UnderlayFloor[] floor;
    public int rgb;
    public int texture;
    public boolean occlude;
    public int hue;
    public int saturation;
    public int lightness;
    public int hueAdj;
    public int brightness;
    public int hsl;

    public UnderlayFloor() {
        texture = -1;
        occlude = true;
    }

    public static void unpackConfig(CacheArchive archive) {
        Stream buffer = new Stream(archive.getDataForName("flo.dat"));
        int size = buffer.readUnsignedWord();
        if (floor == null) {
            floor = new UnderlayFloor[size];
        }
        for (int i = 0; i < size; i++) {
            if (floor[i] == null) {
                floor[i] = new UnderlayFloor();
            }
            floor[i].readValues(buffer);
        }
    }

    public static int hslCode(int h, int s, int l) {
        if (l > 179) {
            s /= 2;
        }
        if (l > 192) {
            s /= 2;
        }
        if (l > 217) {
            s /= 2;
        }
        if (l > 243) {
            s /= 2;
        }
        return ((h / 4) << 10) + ((s / 32) << 7) + (l / 2);
    }

    private void calculateValues(int color) {
        final double red = ((color >> 16) & 0xff) / 256D;
        final double green = ((color >> 8) & 0xff) / 256D;
        final double blue = (color & 0xff) / 256D;
        double min = red;
        if (green < min) {
            min = green;
        }
        if (blue < min) {
            min = blue;
        }
        double max = red;
        if (green > max) {
            max = green;
        }
        if (blue > max) {
            max = blue;
        }
        double h = 0.0D;
        double s = 0.0D;
        double l = (min + max) / 2D;
        if (min != max) {
            if (l < 0.5D) {
                s = (max - min) / (max + min);
            }
            if (l >= 0.5D) {
                s = (max - min) / (2D - max - min);
            }
            if (red == max) {
                h = (green - blue) / (max - min);
            } else if (green == max) {
                h = 2D + ((blue - red) / (max - min));
            } else if (blue == max) {
                h = 4D + ((red - green) / (max - min));
            }
        }
        h /= 6D;
        hue = (int) (h * 256D);
        saturation = (int) (s * 256D);
        lightness = (int) (l * 256D);
        if (saturation < 0) {
            saturation = 0;
        } else if (saturation > 255) {
            saturation = 255;
        }
        if (lightness < 0) {
            lightness = 0;
        } else if (lightness > 255) {
            lightness = 255;
        }
        if (l > 0.5D) {
            brightness = (int) ((1.0D - l) * s * 512D);
        } else {
            brightness = (int) (l * s * 512D);
        }
        if (brightness < 1) {
            brightness = 1;
        }
        hueAdj = (int) (h * brightness);
        int hr = hue;
        if (hr < 0) {
            hr = 0;
        } else if (hr > 255) {
            hr = 255;
        }
        int sr = saturation;
        if (sr < 0) {
            sr = 0;
        } else if (sr > 255) {
            sr = 255;
        }
        int lr = lightness;
        if (lr < 0) {
            lr = 0;
        } else if (lr > 255) {
            lr = 255;
        }
        hsl = hslCode(hr, sr, lr);
    }

    private void readValues(Stream buffer) {
        do {
            int code = buffer.readUnsignedByte();
            if (code == 0) {
                return;
            } else if (code == 1) {
                rgb = buffer.read3Bytes();
                calculateValues(rgb);
            } else if (code == 2) {
                texture = buffer.readUnsignedByte();
            } else if (code == 3) {
                /* empty */
            } else if (code == 5) {
                occlude = false;
            } else if (code == 6) {
                buffer.readString();
            } else if (code == 7) {
                final int h = hue;
                final int s = saturation;
                final int l = lightness;
                final int adj = hueAdj;
                final int c = buffer.read3Bytes();
                calculateValues(c);
                hue = h;
                saturation = s;
                lightness = l;
                hueAdj = adj;
                brightness = adj;
            } else {
                System.out.println("Error unrecognised flo config code: " + code);
            }
        }
        while (true);
    }
}