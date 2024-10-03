package online.paescape.cache.def;


import online.paescape.cache.CacheArchive;
import online.paescape.net.Stream;
import online.paescape.util.Configuration;
import online.paescape.util.FileOperations;
import online.paescape.util.Signlink;

public class FloorDefinitionOSRS {

    public static FloorDefinitionOSRS[] overlays;
    public static FloorDefinitionOSRS[] underlays;

    public int texture;
    public int rgb;
    public boolean occlude;
    public int anotherRgb;

    public int hue;
    public int saturation;
    public int luminance;

    public int anotherHue;
    public int anotherSaturation;
    public int anotherLuminance;

    public int blendHue;
    public int blendHueMultiplier;
    public int hsl16;

    private FloorDefinitionOSRS() {
        texture = -1;
        occlude = true;
    }

    public static void unpackConfig(CacheArchive streamLoader) {
        Stream stream = new Stream(FileOperations.ReadFile(Signlink.findcachedir() + "flo.dat"));

        int underlayAmount = stream.readUnsignedWord();

        if (Configuration.DEBUG)
            System.out.println("OSRS Floor underlays : " + underlayAmount);
        underlays = new FloorDefinitionOSRS[underlayAmount];
        for (int i = 0; i < underlayAmount; i++) {
            if (underlays[i] == null) {
                underlays[i] = new FloorDefinitionOSRS();
            }
            underlays[i].readValuesUnderlay(stream);
            underlays[i].generateHsl();
        }

        int overlayAmount = stream.readUnsignedWord();
        if (Configuration.DEBUG)
            System.out.println("OSRS Floor overlays : " + overlayAmount);
        overlays = new FloorDefinitionOSRS[overlayAmount];
        for (int i = 0; i < overlayAmount; i++) {
            if (overlays[i] == null) {
                overlays[i] = new FloorDefinitionOSRS();
            }
            overlays[i].readValuesOverlay(stream);
            overlays[i].generateHsl();
        }
        System.out.println("Floors read -> (" + underlayAmount + " underlays) | (" + overlayAmount + " overlays)");
    }

    private final static int hsl24to16(int h, int s, int l) {
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
        return (h / 4 << 10) + (s / 32 << 7) + l / 2;
    }

    private void generateHsl() {
        if (anotherRgb != -1) {
            rgbToHsl(anotherRgb);
            anotherHue = hue;
            anotherSaturation = saturation;
            anotherLuminance = luminance;
        }
        rgbToHsl(rgb);
    }

    private void readValuesUnderlay(Stream stream) {
        for (; ; ) {
            int opcode = stream.readSignedByte();
            if (opcode == 0) {
                break;
            } else if (opcode == 1) {
                rgb = ((stream.readUnsignedByte()) << 16) + ((stream.readUnsignedByte()) << 8) + (stream.readUnsignedByte());
            } else {
                System.out.println("Error unrecognised underlay code: " + opcode);
            }
        }
    }

    private void readValuesOverlay(Stream stream) {
        for (; ; ) {
            int opcode = stream.readSignedByte();
            if (opcode == 0) {
                break;
            } else if (opcode == 1) {
                rgb = ((stream.readUnsignedByte()) << 16) + ((stream.readUnsignedByte()) << 8) + (stream.readUnsignedByte());
            } else if (opcode == 2) {
                texture = stream.readUnsignedByte();
            } else if (opcode == 5) {
                occlude = false;
            } else if (opcode == 7) {
                anotherRgb = ((stream.readUnsignedByte()) << 16) + ((stream.readUnsignedByte()) << 8) + (stream.readUnsignedByte());
            } else {
                System.out.println("Error unrecognised overlay code: " + opcode);
            }
        }
    }

    private void rgbToHsl(int rgb) {
        double r = (rgb >> 16 & 0xff) / 256.0;
        double g = (rgb >> 8 & 0xff) / 256.0;
        double b = (rgb & 0xff) / 256.0;
        double min = r;
        if (g < min) {
            min = g;
        }
        if (b < min) {
            min = b;
        }
        double max = r;
        if (g > max) {
            max = g;
        }
        if (b > max) {
            max = b;
        }
        double h = 0.0;
        double s = 0.0;
        double l = (min + max) / 2.0;
        if (min != max) {
            if (l < 0.5) {
                s = (max - min) / (max + min);
            }
            if (l >= 0.5) {
                s = (max - min) / (2.0 - max - min);
            }
            if (r == max) {
                h = (g - b) / (max - min);
            } else if (g == max) {
                h = 2.0 + (b - r) / (max - min);
            } else if (b == max) {
                h = 4.0 + (r - g) / (max - min);
            }
        }
        h /= 6.0;
        hue = (int) (h * 256.0);
        saturation = (int) (s * 256.0);
        luminance = (int) (l * 256.0);
        if (saturation < 0) {
            saturation = 0;
        } else if (saturation > 255) {
            saturation = 255;
        }
        if (luminance < 0) {
            luminance = 0;
        } else if (luminance > 255) {
            luminance = 255;
        }
        if (l > 0.5) {
            blendHueMultiplier = (int) ((1.0 - l) * s * 512.0);
        } else {
            blendHueMultiplier = (int) (l * s * 512.0);
        }
        if (blendHueMultiplier < 1) {
            blendHueMultiplier = 1;
        }
        blendHue = (int) (h * blendHueMultiplier);
        hsl16 = hsl24to16(hue, saturation, luminance);
    }
}

