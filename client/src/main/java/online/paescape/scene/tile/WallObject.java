package online.paescape.scene.tile;


import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.geometry.Shapes;
import net.runelite.rs.api.RSBoundaryObject;
import net.runelite.rs.api.RSRenderable;
import online.paescape.Client;
import online.paescape.cache.def.ObjectDef;
import online.paescape.media.animable.Animable;
import online.paescape.media.animable.Model;
import online.paescape.util.ObjectKeyUtil;

import java.awt.*;

public final class WallObject implements RSBoundaryObject {

    public Animable node1;
    public Animable node2;
    public int uid;
    public int tileHeights;
    public int xPos;
    public int yPos;
    public int orientation;
    public int orientation1;
    public int wallObjUID;
    public byte objConfig;
    public int zLoc;

    public WallObject() {
    }

    @Override
    public Model getModelA() {
        RSRenderable entity = getRenderable1();
        if (entity == null)
        {
            return null;
        }

        if (entity instanceof Model)
        {
            return (Model) entity;
        }
        else
        {
            return (Model) entity.getModel();
        }
    }
    @Override
    public Model getModelB() {
        RSRenderable entity = getRenderable2();
        if (entity == null)
        {
            return null;
        }

        if (entity instanceof Model)
        {
            return (Model) entity;
        }
        else
        {
            return (Model) entity.getModel();
        }
    }
    @Override
    public Shape getConvexHull() {
        Model model = getModelA();

        if (model == null)
        {
            return null;
        }

        int tileHeight = Perspective.getTileHeight(Client.instance, new LocalPoint(getX(), getY()), getPlane());
        return model.getConvexHull(getX(), getY(), 0, tileHeight);
    }
    @Override
    public Shape getConvexHull2() {
        Model model = getModelB();

        if (model == null)
        {
            return null;
        }

        int tileHeight = Perspective.getTileHeight(Client.instance, new LocalPoint(getX(), getY()), getPlane());
        return model.getConvexHull(getX(), getY(), 0, tileHeight);
    }
    @Override
    public int getPlane() {
        return zLoc;
    }
    @Override
    public int getId() {
        return ObjectKeyUtil.getObjectId(uid);
    }
    @Override
    public net.runelite.api.Point getCanvasLocation() {
        return Perspective.localToCanvas((net.runelite.api.Client)Client.instance, this.getLocalLocation(), this.getPlane());
    }
    @Override
    public net.runelite.api.Point getCanvasLocation(int zOffset) {
        return Perspective.localToCanvas((net.runelite.api.Client)Client.instance, this.getLocalLocation(), this.getPlane(), zOffset);
    }
    @Override
    public Polygon getCanvasTilePoly() {
        return Perspective.getCanvasTilePoly(Client.instance, this.getLocalLocation());
    }
    @Override
    public net.runelite.api.Point getCanvasTextLocation(Graphics2D graphics, String text, int zOffset) {
        return Perspective.getCanvasTextLocation(Client.instance, graphics, getLocalLocation(), text, zOffset);
    }
    @Override
    public Point getMinimapLocation() {
        return Perspective.localToMinimap(Client.instance, getLocalLocation());
    }
    @Override
    public Shape getClickbox() {
        Shape clickboxA = Perspective.getClickbox(Client.instance, getModelA(), 0, getLocalLocation());
        Shape clickboxB = Perspective.getClickbox(Client.instance, getModelB(), 0, getLocalLocation());

        if (clickboxA == null && clickboxB == null)
        {
            return null;
        }

        if (clickboxA != null && clickboxB != null)
        {
            return new Shapes(new Shape[]{clickboxA, clickboxB});
        }

        if (clickboxA != null)
        {
            return clickboxA;
        }

        return clickboxB;
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
        return WorldPoint.fromLocal(Client.instance, getLocalLocation());
    }
    @Override
    public LocalPoint getLocalLocation() {
        return new LocalPoint(this.xPos, this.yPos);
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
    public int getOrientationA() {
        return orientation;
    }
    @Override
    public int getOrientationB() {
        return orientation1;
    }

    @Override
    public RSRenderable getRenderable1() {
        return node1;
    }

    @Override
    public RSRenderable getRenderable2() {
        return node2;
    }

    @Override
    public int getConfig() {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public void setPlane(int plane) {
        zLoc = plane;
    }

}
