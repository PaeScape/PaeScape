package online.paescape.scene.tile;


import net.runelite.api.Model;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.rs.api.RSFloorDecoration;
import net.runelite.rs.api.RSModel;
import net.runelite.rs.api.RSRenderable;
import online.paescape.cache.def.ObjectDef;
import online.paescape.media.animable.Animable;
import online.paescape.util.ObjectKeyUtil;

import java.awt.*;

public final class GroundDecoration implements RSFloorDecoration {

    public Animable node;
    public int uid;
    public int zPos;
    public int xPos;
    public int yPos;
    public int groundDecorUID;
    public byte objConfig;

    public GroundDecoration() {
    }

    @Override
    public Model getModel() {
        RSRenderable entity = getRenderable();
        if (entity == null)
        {
            return null;
        }

        if (entity instanceof Model)
        {
            return (RSModel) entity;
        }
        else
        {
            return entity.getModel();
        }
    }

    @Override
    public Shape getConvexHull() {
        RSModel model = (RSModel) getModel();

        if (model == null)
        {
            return null;
        }

        int tileHeight = Perspective.getTileHeight(online.paescape.Client.instance, new LocalPoint(getX(), getY()), online.paescape.Client.instance.getPlane());

        return model.getConvexHull(getX(), getY(), 0, tileHeight);
    }

    @Override
    public int getPlane() {
        return zPos;
    }

    @Override
    public int getId() {
        return ObjectKeyUtil.getObjectId(uid);
    }

    @Override
    public net.runelite.api.Point getCanvasLocation() {
        return Perspective.localToCanvas(online.paescape.Client.instance, getLocalLocation(), getPlane(), 0);
    }

    @Override
    public net.runelite.api.Point getCanvasLocation(int zOffset) {
        return Perspective.localToCanvas((net.runelite.api.Client) online.paescape.Client.instance, this.getLocalLocation(), this.getPlane(), zOffset);
    }

    @Override
    public Polygon getCanvasTilePoly() {
        return Perspective.getCanvasTilePoly(online.paescape.Client.instance, this.getLocalLocation());
    }

    @Override
    public net.runelite.api.Point getCanvasTextLocation(Graphics2D graphics, String text, int zOffset) {
        return Perspective.getCanvasTextLocation(online.paescape.Client.instance, graphics, getLocalLocation(), text, zOffset);
    }

    @Override
    public Point getMinimapLocation() {
        return Perspective.localToMinimap(online.paescape.Client.instance, getLocalLocation());
    }

    @Override
    public Shape getClickbox() {
        return Perspective.getClickbox(online.paescape.Client.instance, getModel(), 0, getLocalLocation());
    }

    @Override
    public String getName() {
        return ObjectDef.forID(getId()).name;
    }

    @Override
    public String[] getActions() {
        return ObjectDef.forID(getId()).actions;
    }

    @Override
    public WorldPoint getWorldLocation() {
        return WorldPoint.fromLocal(online.paescape.Client.instance, this.getX(), this.getY(), this.getPlane());
    }

    @Override
    public LocalPoint getLocalLocation() {
        return new LocalPoint(this.getX(), this.getY());
    }

    @Override
    public long getHash() {
        return uid;
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
    public RSRenderable getRenderable() {
        return node;
    }

    @Override
    public void setPlane(int plane) {
        zPos = plane;
    }

    @Override
    public int getConfig()
    {
        return objConfig;
    }
}
