package online.paescape.scene.tile;


import net.runelite.api.Model;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.rs.api.RSItemLayer;
import net.runelite.rs.api.RSRenderable;
import online.paescape.Client;
import online.paescape.cache.def.ItemDef;
import online.paescape.media.animable.Animable;

import java.awt.*;

public final class GroundItem implements RSItemLayer {

    public int zPos;
    public int xPos;
    public int yPos;
    public Animable firstGroundItem;
    public Animable secondGroundItem;
    public Animable thirdGroundItem;
    public int uid;
    public int newuid;
    public int topItem;

    public GroundItem() {
    }

    @Override
    public Model getModelBottom() {
        Animable renderable = (Animable) getBottom();
        if (renderable == null)
        {
            return null;
        }

        if (renderable instanceof Model)
        {
            return (Model) renderable;
        }
        else
        {
            return renderable.getModel();
        }
    }

    @Override
    public Model getModelMiddle() {
        Animable renderable = (Animable) getMiddle();
        if (renderable == null)
        {
            return null;
        }

        if (renderable instanceof Model)
        {
            return (Model) renderable;
        }
        else
        {
            return renderable.getModel();
        }
    }

    @Override
    public Model getModelTop() {
        Animable renderable = (Animable) getTop();
        if (renderable == null)
        {
            return null;
        }

        if (renderable instanceof Model)
        {
            return (Model) renderable;
        }
        else
        {
            return renderable.getModel();
        }
    }

    @Override
    public int getPlane() {
        return zPos;
    }

    @Override
    public int getId() {
        return topItem;
    }

    @Override
    public Point getCanvasLocation() {
        return Perspective.localToCanvas(Client.instance, getLocalLocation(), getPlane(), 0);
    }

    @Override
    public Point getCanvasLocation(int zOffset) {
        return Perspective.localToCanvas(Client.instance, getLocalLocation(), getPlane(), zOffset);
    }

    @Override
    public Polygon getCanvasTilePoly() {
        return Perspective.getCanvasTilePoly(Client.instance, this.getLocalLocation());
    }

    @Override
    public Point getCanvasTextLocation(Graphics2D graphics, String text, int zOffset) {
        return Perspective.getCanvasTextLocation(Client.instance, graphics, getLocalLocation(), text, zOffset);
    }

    @Override
    public Point getMinimapLocation() {
        return Perspective.localToMinimap(Client.instance, getLocalLocation());
    }

    @Override
    public Shape getClickbox() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getName() {
        return ItemDef.forID(getId()).name;
    }

    @Override
    public String[] getActions() {
        return ItemDef.forID(getId()).groundActions;
    }

    @Override
    public int getConfig() {
        return 0;
    }

    @Override
    public WorldPoint getWorldLocation() {
        return WorldPoint.fromLocal(Client.instance, getX(), getY(), getPlane());
    }

    @Override
    public LocalPoint getLocalLocation() {
        return new LocalPoint(getX(), getY());
    }

    @Override
    public int getX() {
        return xPos;
    }

    @Override
    public int getY() {
        return yPos;
    }

    @Override
    public long getHash() {
        return uid;
    }

    @Override
    public int getHeight() {
        return topItem;
    }

    @Override
    public RSRenderable getBottom() {
        return firstGroundItem;
    }

    @Override
    public RSRenderable getMiddle() {
        return secondGroundItem;
    }

    @Override
    public RSRenderable getTop() {
        return thirdGroundItem;
    }

    @Override
    public void setPlane(int plane) {
        zPos = plane;
    }
}
