package online.paescape.media;

import net.runelite.rs.api.RSVertexNormal;

public class VertexNormal implements RSVertexNormal {
    public int anInt602;
    public int anInt603;
    public int anInt604;
    public int anInt605;

    public VertexNormal() {
    }

    @Override
    public int getX() {
        return anInt602;
    }

    @Override
    public int getY() {
        return anInt603;
    }

    @Override
    public int getZ() {
        return anInt604;
    }
}