package online.paescape;

import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.Tile;
import net.runelite.api.events.CanvasSizeChanged;
import net.runelite.api.events.FocusChanged;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.hooks.DrawCallbacks;
import net.runelite.rs.api.RSCanvas;
import net.runelite.rs.api.RSGameEngine;
import net.runelite.rs.api.RSKeyHandler;
import net.runelite.rs.api.RSMouseWheelHandler;
import online.paescape.cache.ResourceLoader;
import online.paescape.cache.media.interfaces.RSInterface;
import online.paescape.media.RSImageProducer;
import online.paescape.util.Bounds;
import online.paescape.util.TaskUtils;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;

@SuppressWarnings("all")
public abstract class RSApplet extends Applet implements Runnable, MouseListener,
        MouseMotionListener, MouseWheelListener, KeyListener, FocusListener,
        WindowListener, RSGameEngine, RSMouseWheelHandler, RSKeyHandler {

    public final static int LEFT = 0;
    public final static int RIGHT = 1;
    public final static int DRAG = 2;
    public final static int RELEASED = 3;
    public final static int MOVE = 4;
    private static final Color FONT_COLOR = Color.white;
    private static final Font LOADING_FONT = new Font("Helvetica", 1, 13);
    private static final Color BACKGROUND_COLOR = Color.black;
    private static final Color LOADING_COLOR = new Color(140, 17, 17);
    public static int hotKey = 508;
    public static int anInt34;
    public static boolean shiftDown = false;
    public static boolean rightClick = false;
    final int keyArray[] = new int[128];
    private final long aLongArray7[] = new long[10];
    final int[] charQueue = new int[128];
    public boolean resizing = false;
    public int mouseX;
    public int mouseY;
    public int clickMode1;
    public int clickX;
    public int clickY;
    public int clickMode3;
    public int saveClickX;
    public int saveClickY;
    public boolean isLoading = true;
    public boolean isApplet;
    public int resizeChatStartY;
    public int clickType;
    public int releasedX;
    public int releasedY;
    public boolean mouseWheelDown;
    public int mouseWheelX;
    public int mouseWheelY;
    int minDelay = 1;
    int fps;
    boolean shouldDebug = false;
    int myWidth;// replacing this with canvasHeight and and width
    int myHeight;
    Graphics graphics;
    ProducingGraphicsBuffer fullGameScreen;
    boolean awtFocus = true;
    int idleTime;
    public double cps = 0;
    int clicks = 0;
    long lastCPSReset = 0;
    int clickMode2;
    long saveClickTime;
    int lastB = -1;
    private int anInt4;
    private int delayTime = 20;
    private boolean shouldClearScreen = true;
    private long clickTime;
    private int readIndex;
    private int writeIndex;
    private Image loadingBuffer;
    private long startTime = 0L;
    private long colorStart = 0L;

    static int field199;

    static {
        field199 = 500;
    }

    protected RSApplet() {
        hasErrored = false;
        canvasX = 0;
        canvasY = 0;
        fullRedraw = true;
        resizeCanvasNextFrame = false;
        isCanvasInvalid = false;
        field185 = 0L;
        EventQueue queue = null;

        try {
            queue = Toolkit.getDefaultToolkit().getSystemEventQueue();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }

        eventQueue = queue;
    }

    private static void options(Graphics g) {
        try {
            if (g instanceof Graphics2D) {
                Graphics2D r = (Graphics2D) g;
                r.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                r.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            }
        } catch (Throwable t) {
        }
    }

    public void setCursor(byte[] data) {
        Image image = getGameComponent().getToolkit().createImage(data);
        getGameComponent().setCursor(
                getGameComponent().getToolkit().createCustomCursor(image,
                        new Point(0, 0), null));
    }

    final void initRunelite(int w, int h) {
        isApplet = true;
        myWidth = w;
        myHeight = h;
        graphics = getGameComponent().getGraphics();
        startRunnable(this, 1);
    }

    final void initClientFrame(int w, int h) {
        isApplet = true;
        myWidth = w;
        myHeight = h;
        graphics = getGameComponent().getGraphics();
        fullGameScreen = new ProducingGraphicsBuffer(myWidth, myHeight, getGameComponent());
        startRunnable(this, 1);
    }

    public void run() {
        thread = Thread.currentThread();
        thread.setName("Client");
        setFocusCycleRoot(true);
        if(Client.instance.runelite){
            addCanvas();
        }else {
            getGameComponent().addMouseListener(this);
            getGameComponent().addMouseMotionListener(this);
            getGameComponent().addKeyListener(this);
            getGameComponent().addFocusListener(this);
            getGameComponent().addMouseWheelListener(this);
        }
//        Client.instance.getCallbacks().post(CanvasSizeChanged.INSTANCE);
        colorStart = startTime = 0L;
        startUp();
        int i = 0;
        int j = 256;
        int k = 1;
        int l = 0;
        int i1 = 0;
        for (int j1 = 0; j1 < 10; j1++) {
            aLongArray7[j1] = System.currentTimeMillis();
        }

//        long currentTimeMillis = System.currentTimeMillis();
        do {
            if (anInt4 < 0) {
                break;
            }
            if (anInt4 > 0) {
                anInt4--;
                if (anInt4 == 0) {
                    exit();
                    return;
                }
            }
            int k1 = j;
            int i2 = k;
            j = 300;
            k = 1;
            long l2 = System.currentTimeMillis();
            if (aLongArray7[i] == 0L) {
                j = k1;
                k = i2;
            } else if (l2 > aLongArray7[i]) {
                j = (int) ((long) (2560 * delayTime) / (l2 - aLongArray7[i]));
            }
            if (j < 25) {
                j = 25;
            }
            if (j > 256) {
                j = 256;
                k = (int) ((long) delayTime - (l2 - aLongArray7[i]) / 10L);
            }
            if (k > delayTime) {
                k = delayTime;
            }
            aLongArray7[i] = l2;
            i = (i + 1) % 10;
            if (k > 1) {
                for (int j2 = 0; j2 < 10; j2++) {
                    if (aLongArray7[j2] != 0L) {
                        aLongArray7[j2] += k;
                    }
                }

            }
            if (k < minDelay) {
                k = minDelay;
            }
            try {
                Thread.sleep(k);
            } catch (InterruptedException interruptedexception) {
                i1++;
            }
            for (; l < 256; l += j) {
                clickMode3 = clickMode1;
                saveClickX = clickX;
                saveClickY = clickY;
                saveClickTime = clickTime;
                clickMode1 = 0;
                processGameLoop();
                readIndex = writeIndex;
            }
            if (++field199 - 1 > 50) {//every 1sec canvas resize check
                field199 -= 50;
                fullRedraw = true;
                canvas.setSize(canvasWidth, canvasHeight);
                canvas.setVisible(true);
                canvas.setLocation(canvasX, canvasY);
            }
            if (isCanvasInvalid) {
                replaceCanvas();
            }
            doResize();
            processDrawing();
            if (fullRedraw) {
                clearBackground();
            }
            fullRedraw = false;

            l &= 0xff;
            if (delayTime > 0) {
                fps = (1000 * j) / (delayTime * 256);
            }
            if (shouldDebug) {
                System.out.println((new StringBuilder()).append("ntime:")
                        .append(l2).toString());
                for (int k2 = 0; k2 < 10; k2++) {
                    int i3 = ((i - k2 - 1) + 20) % 10;
                    System.out.println((new StringBuilder()).append("otim")
                            .append(i3).append(":").append(aLongArray7[i3])
                            .toString());
                }

                System.out.println((new StringBuilder()).append("fps:")
                        .append(fps).append(" ratio:").append(j)
                        .append(" count:").append(l).toString());
                System.out.println((new StringBuilder()).append("del:")
                        .append(k).append(" deltime:").append(delayTime)
                        .append(" mindel:").append(minDelay).toString());
                System.out.println((new StringBuilder()).append("intex:")
                        .append(i1).append(" opos:").append(i).toString());
                shouldDebug = false;
                i1 = 0;
            }
        } while (true);
        if (anInt4 == -1) {
            exit();
        }
    }
    final EventQueue eventQueue;
    public final void post(Object var1) {
        if (!Client.instance.isGpu()) {
            if (eventQueue != null) {
                for (int var2 = 0; var2 < 50 && eventQueue.peekEvent() != null; ++var2) {
                    TaskUtils.sleep(1L);
                }

                if (var1 != null) {
                    eventQueue.postEvent(new ActionEvent(var1, 1001, "dummy"));
                }

            }
        } else {
            DrawCallbacks drawCallbacks = Client.instance.getDrawCallbacks();
            if (drawCallbacks != null) {
                drawCallbacks.draw(viewportColor);
            }
        }
    }
    private static int viewportColor;

    public online.paescape.media.Canvas canvas;
    public static int canvasWidth;
    public static int canvasHeight;
    private void addCanvas() {
        Container container = this;
        if (canvas != null) {
            canvas.removeFocusListener(this);
            container.remove(canvas);
        }
//        System.out.println("container.getWidth()="+container.getWidth()+" container.getHeight()="+container.getHeight());
//        System.out.println("canvasWidth="+canvasWidth+" canvasHeight="+canvasHeight);
        canvasWidth = Math.max(container.getWidth(), 0);
        canvasHeight = Math.max(container.getHeight(), 0);

        Client.instance.getCallbacks().post(CanvasSizeChanged.INSTANCE);
        canvas = new online.paescape.media.Canvas(this);

        this.canvas.addMouseListener(this);
        this.canvas.addMouseMotionListener(this);
        this.canvas.addFocusListener(this);

        this.canvas.setFocusTraversalKeysEnabled(false);
        this.canvas.addKeyListener(this);
        setUpClipboard();

        this.canvas.addMouseWheelListener(this);

        container.setBackground(Color.BLACK);
        container.setLayout(null);
        container.add(canvas);
        canvas.setSize(canvasWidth, canvasHeight);
        canvas.setVisible(true);
        canvas.setBackground(Color.BLACK);
        canvas.setLocation(canvasX, canvasY);
        canvas.addFocusListener(this);
        canvas.requestFocus();
        fullRedraw = true;

        if (Client.rasterProvider != null && canvasWidth == Client.rasterProvider.width && canvasHeight == Client.rasterProvider.height) {
           ((ProducingGraphicsBuffer) Client.rasterProvider).setComponent(canvas);
            Client.rasterProvider.drawFull(0, 0);
        } else {
            Client.rasterProvider = new ProducingGraphicsBuffer(canvasWidth, canvasHeight, canvas);

        }
        isCanvasInvalid = false;
        field185 = method2692();
    }
    long field185 = 0L;
    static long field3170;
    static long field4425;
    public static synchronized long method2692() {
        long var0 = System.currentTimeMillis();
        if (var0 < field3170) {
            field4425 += field3170 - var0;
        }

        field3170 = var0;
        return field4425 + var0;
    }
    public Clipboard clipboard;
    protected void setUpClipboard() {
        this.clipboard = this.getToolkit().getSystemClipboard();
    }

    protected void setClipboardText(String text) {
        this.clipboard.setContents(new StringSelection(text), (ClipboardOwner)null);
    }

    private void exit() {
        anInt4 = -2;
    }

    final void setFPS(int targetFPS) {
        delayTime = 1000 / targetFPS;
    }

    public final void start() {
        if (anInt4 >= 0) {
            anInt4 = 0;
        }
    }

    public final void stop() {
        if (anInt4 >= 0) {
            anInt4 = 4000 / delayTime;
        }
    }

    public final void destroy() {
        anInt4 = -1;
        try {
            Thread.sleep(5000L);
        } catch (Exception exception) {
        }
        if (anInt4 == -1) {
            exit();
        }
    }
    final void replaceCanvas() {
//        System.out.println("replaceCanvas");
        if (Client.instance != null && Client.instance.isGpu()) {
            setFullRedraw(false);
            return;
        }
//        this.canvas.removeMouseListener(this);
//        this.canvas.removeMouseMotionListener(this);
//        this.canvas.removeFocusListener(this);
//        this.canvas.removeMouseWheelListener(this);
        addCanvas();
//        this.addMouseListener(this);
//        this.addMouseMotionListener(this);
//        this.addFocusListener(this);
//        this.addMouseWheelListener(this);

        flagResize();
    }

    public final void update(Graphics g) {
//        paint(g);
        if (graphics == null) {
            graphics = g;
        }
        shouldClearScreen = true;
    }

    public final void paint(Graphics g) {
//        if (this == gameEngine && !isKilled) {
            fullRedraw = true;
            if (method2692() - field185 > 1000L) {
                Rectangle var2 = g.getClipBounds();
                if (var2 == null || var2.width >= canvasWidth && var2.height >= canvasHeight) {
                    isCanvasInvalid = true;
                }
            }

//        }
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        int rotation = e.getWheelRotation();
        try {
            if (!handleInterfaceScrolling(e)) {
                if (mouseX > 0 && mouseX < 512 && mouseY > Client.canvasHeight - 165
                        && mouseY < Client.canvasHeight - 25) {
                    int scrollPos = Client.anInt1089;
                    scrollPos -= rotation * 10;
                    if (scrollPos < 0)
                        scrollPos = 0;

                    if (Client.anInt1089 != scrollPos) {
                        Client.anInt1089 = scrollPos;
                    }
                } else if (Client.loggedIn) {

                    /** ZOOMING **/
                    boolean zoom = !Client.instance.isResized() ? (mouseX < 512) : (mouseX < Client.canvasWidth - 200);
                    if(zoom && Client.openInterfaceID == -1) {
                        int zoom_in = !Client.instance.isResized() ? 195 : 240;

                        int zoom_out = !Client.instance.isResized() ? 1105 : 1220;

                        if (rotation != -1) {
                            if (Client.clientZoom > zoom_in) {
                                Client.clientZoom -= 30;
                            }
                        } else {
                            if (Client.clientZoom < zoom_out) {
                                Client.clientZoom += 30;
                            }
                        }

                    }
                    Client.inputTaken = true;
                }
            }
        }catch (Exception ex) {
            System.out.printf("Issue with Widget MouseWheel: " + e);
        }
    }

    public boolean handleInterfaceScrolling(MouseWheelEvent event) {
        int rotation = event.getWheelRotation();
        int positionX = 0;
        int positionY = 0;
        int width = 0;
        int height = 0;
        int offsetX = 0;
        int offsetY = 0;
        int childID = 0;
        /* Tab interface scrolling */
        int tabInterfaceID = Client.getClient().tabInterfaceIDs[Client.getClient().tabID];
        if (tabInterfaceID == 11000)
            tabInterfaceID = 1151;
        if (tabInterfaceID != -1) {
            RSInterface tab = RSInterface.interfaceCache[tabInterfaceID];
            offsetX = Client.getClient().clientSize == 0 ? Client.getClient().clientWidth - 218
                    : (Client.getClient().clientSize == 0 ? 28 : Client
                    .getClient().clientWidth - 197);
            offsetY = Client.getClient().clientSize == 0 ? Client.getClient().clientHeight - 298
                    : (Client.getClient().clientSize == 0 ? 37 : Client
                    .getClient().clientHeight
                    - (Client.getClient().clientWidth >= 900 ? 37 : 74)
                    - 267);
            if (tab.children == null) {
                return false;
            }
            for (int index = 0; index < tab.children.length; index++) {
                if (RSInterface.interfaceCache[tab.children[index]].scrollMax > 0) {
                    childID = index;
                    positionX = tab.childX[index];
                    positionY = tab.childY[index];
                    width = RSInterface.interfaceCache[tab.children[index]].width;
                    height = RSInterface.interfaceCache[tab.children[index]].height;
                    break;
                }
            }
            if (mouseX > offsetX + positionX && mouseY > offsetY + positionY
                    && mouseX < offsetX + positionX + width
                    && mouseY < offsetY + positionY + height) {
                if (RSInterface.interfaceCache[tab.children[childID]].scrollPosition > 0) {
                    RSInterface.interfaceCache[tab.children[childID]].scrollPosition += rotation * 30;
                    return true;
                } else {
                    if (rotation > 0) {
                        RSInterface.interfaceCache[tab.children[childID]].scrollPosition += rotation * 30;
                        return true;
                    }
                }
            }
        }
        /* Main interface scrolling */
        if (Client.openInterfaceID != -1) {
            RSInterface rsi = RSInterface.interfaceCache[Client.openInterfaceID];
            offsetX = Client.getClient().clientSize == 0 ? 4 : (Client
                    .getClient().clientWidth / 2) - 256;
            offsetY = Client.getClient().clientSize == 0 ? 4 : (Client
                    .getClient().clientHeight / 2) - 167;
            for (int index = 0; index < rsi.children.length; index++) {
                if (RSInterface.interfaceCache[rsi.children[index]].scrollMax > 0) {
                    childID = index;
                    positionX = rsi.childX[index];
                    positionY = rsi.childY[index];
                    width = RSInterface.interfaceCache[rsi.children[index]].width;
                    height = RSInterface.interfaceCache[rsi.children[index]].height;
                    break;
                }
            }
            if (mouseX > offsetX + positionX && mouseY > offsetY + positionY
                    && mouseX < offsetX + positionX + width
                    && mouseY < offsetY + positionY + height) {
                if (RSInterface.interfaceCache[rsi.children[childID]].scrollPosition > 0) {
                    RSInterface.interfaceCache[rsi.children[childID]].scrollPosition += rotation * 30;
                    return true;
                } else {
                    if (rotation > 0) {
                        RSInterface.interfaceCache[rsi.children[childID]].scrollPosition += rotation * 30;
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public final void mousePressed(MouseEvent e) {
        e = Client.instance.getCallbacks().mousePressed(e);
        int x = e.getX();
        int y = e.getY();
        int type = e.getButton();
        if (System.getProperty("os.version").contains("Android"))
            System.out.printf("GOT EVENT: %s at %d%n", e, System.currentTimeMillis());

        if (!e.isConsumed()) {
            resizeChatStartY = -1;
//            if (Client.clientSize != 0) {
//                int offsetY = Client.clientHeight - 165 - Client.chatAreaHeight;
//                if (y >= offsetY - 10 && y <= offsetY + 10) {
//                    if (x <= 500) {
//                        resizeChatStartY = y;
//                    }
//                }
//            }
            if (SwingUtilities.isMiddleMouseButton(e)) {
                mouseWheelDown = true;
                mouseWheelX = x;
                mouseWheelY = y;
                return;
            }
            idleTime = 0;
            clickX = x;
            clickY = y;
            saveClickX = x;
            saveClickY = y;
            clickTime = System.currentTimeMillis();
            if (SwingUtilities.isRightMouseButton(e)) {
                clickType = RIGHT;
                clickMode1 = 2;
                clickMode2 = 2;
                if(!Client.instance.isMenuOpen()) {
                    final Tile targetTile = Client.instance.getSelectedSceneTile();
                    Client.instance.getCallbacks().post(new MenuEntryAdded(targetTile));
                }
            } else if (SwingUtilities.isLeftMouseButton(e)) {
                clickType = LEFT;
                clickMode1 = 1;
                clickMode2 = 1;
            }
        }
//
    }

    public final void mouseReleased(MouseEvent e) {
        if (Client.instance.loggedIn)
            clicks += 1;
        e = Client.instance.getCallbacks().mouseReleased(e);
//        System.out.printf("GOT EVENT: %s at %d%n", e, System.currentTimeMillis());
        if (!e.isConsumed()) {
            int x = e.getX();
            int y = e.getY();
            releasedX = x;
            releasedY = y;
            idleTime = 0;
            clickMode2 = 0;
            clickType = RELEASED;
            mouseWheelDown = false;
            rightClick = false;
//            if (Client.clientSize != 0 && resizeChatStartY != -1) {
//                int offsetY = Client.clientHeight - 165 - Client.chatAreaHeight;
//                int difference = y - resizeChatStartY;
//                Client.chatAreaHeight = offsetY - y;
//            }
            if (e.isPopupTrigger()) {
                e.consume();
            }
        }
    }

    public final void mouseClicked(MouseEvent e) {
        MouseEvent event = Client.instance.getCallbacks().mouseClicked(e);
        if (!event.isConsumed()) {
            if (event.isPopupTrigger()) {
                event.consume();
            }
        }
    }

    public final void mouseEntered(MouseEvent e) {
        e = Client.instance.getCallbacks().mouseEntered(e);
        if (!e.isConsumed()) {
            this.mouseMoved(e);
        }

    }

    public final void mouseExited(MouseEvent mouseevent) {
        shiftDown = false;
        rightClick = false;
        Client.instance.controlIsDown = false;
        idleTime = 0;
        mouseX = -1;
        mouseY = -1;
    }

    public final void mouseDragged(MouseEvent e) {
        e = Client.instance.getCallbacks().mouseDragged(e);
        if (!e.isConsumed()) {
            int x = e.getX();
            int y = e.getY();

            if (mouseWheelDown) {
                y = mouseWheelX - e.getX();
                int k = mouseWheelY - e.getY();
                mouseWheelDragged(y, -k);
                mouseWheelX = e.getX();
                mouseWheelY = e.getY();
                return;
            }
            idleTime = 0;
            mouseX = x;
            mouseY = y;
            clickType = DRAG;
        }
//        if (Client.clientSize != 0 && resizeChatStartY != -1) {
//            int offsetY = Client.clientHeight - 165 - Client.chatAreaHeight;
//            int difference = y - resizeChatStartY;
//            Client.chatAreaHeight = offsetY - y;
//        }
    }

    void mouseWheelDragged(int param1, int param2) {
    }

    public final void mouseMoved(MouseEvent e) {
        e = Client.instance.getCallbacks().mouseMoved(e);
        if (!e.isConsumed()) {
            int x = e.getX();
            int y = e.getY();
            idleTime = 0;
            mouseX = x;
            mouseY = y;
            clickType = MOVE;
            rightClick = false;
        }
    }

    /**
     * Get the String residing on the clipboard.
     *
     * @return any text found on the Clipboard; if none found, return an empty
     * String.
     */
    public static String getClipboardContents() {
        String result = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        // odd: the Object param of getContents is not currently used
        try {
            Transferable contents = clipboard.getContents(null);
            boolean hasTransferableText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
            if (hasTransferableText) {
                result = (String) contents.getTransferData(DataFlavor.stringFlavor);
            }
        } catch (Exception ex) {
        }
        return result;
    }

    public final void keyPressed(KeyEvent keyevent) {
        Client.instance.getCallbacks().keyPressed(keyevent);
        if ((keyevent.isControlDown() && keyevent.getKeyCode() == KeyEvent.VK_V)) {
            if (Client.instance.loggedIn || Client.instance.isLoading)
                return;
            char[] clipContents = getClipboardContents().toCharArray();
            if (clipContents.length <= 50) {
                for (char c : getClipboardContents().toCharArray()) {
                    charQueue[writeIndex] = c;
                    writeIndex = writeIndex + 1 & 0x7f;
                }
                Client.instance.inputTaken = true;
            }
            return;
        }
        idleTime = 0;
        int keyCode = keyevent.getKeyCode();
        int keyChar = keyevent.getKeyChar();
        currentKeyCode = keyCode;
        if (System.getProperty("os.version").contains("Android")) {
            System.out.println("GOT KEYCODE: " + keyCode);
            System.out.println("GOT Char: " + (char) keyChar);
        }

        if (keyCode == KeyEvent.VK_SHIFT) {
            shiftDown = true;
        }
        if (keyCode == KeyEvent.VK_CONTROL) {
            Client.instance.controlIsDown = true;
        }

        if (keyCode == KeyEvent.VK_ESCAPE && Client.openInterfaceID != -1) {
            Client.instance.clearTopInterfaces();
            return;
        }
        if (keyCode == KeyEvent.VK_CONTROL)
            lastB = 1;
        else if (keyCode != KeyEvent.VK_S)
            lastB = 0;
        if (keyCode == 83 && lastB > 0) {
            if (Client.loggedIn) {
                if (Client.openInterfaceID == -1) {
                    Client.stream.createFrame(185);
                    Client.stream.writeWord(10000);
                }
            }
        }
        if (Client.instance.controlIsDown) {
            if (Client.instance.shiftDown) {
                switch (keyCode) {
                    case KeyEvent.VK_R:
                        if (Client.instance.myRights == 9 || Client.instance.myRights == 10)
                            Client.instance.reloadInterfaces();
                        break;
                }
                return;
            } else if (keyCode >= KeyEvent.VK_F1 && keyCode <= KeyEvent.VK_F10) {
                // open preset window
                Client.instance.sendPacket185(27654);
                // select preset
                // TODO: check prestige for preset. currently loads preset 1 if preset is not unlocked
                switch (keyCode) {
                    case KeyEvent.VK_F1:
                        Client.instance.sendPacket185(8846);
                        break;
                    case KeyEvent.VK_F2:
                        Client.instance.sendPacket185(8823);
                        break;
                    case KeyEvent.VK_F3:
                        Client.instance.sendPacket185(8824);
                        break;
                    case KeyEvent.VK_F4:
                        Client.instance.sendPacket185(8827);
                        break;
                    case KeyEvent.VK_F5:
                        Client.instance.sendPacket185(8837);
                        break;
                    case KeyEvent.VK_F6:
                        Client.instance.sendPacket185(8840);
                        break;
                    case KeyEvent.VK_F7:
                        Client.instance.sendPacket185(8843);
                        break;
                    case KeyEvent.VK_F8:
                        Client.instance.sendPacket185(8859);
                        break;
                    case KeyEvent.VK_F9:
                        Client.instance.sendPacket185(8862);
                        break;
                    case KeyEvent.VK_F10:
                        Client.instance.sendPacket185(8865);
                        break;
                }
                // load preset
                Client.instance.sendPacket185(15309);
                // close interface
                Client.instance.clearTopInterfaces();
                return;
            } else if (keyCode == KeyEvent.VK_0) {
                // teleport preset 10
                Client.instance.sendPacket185(20222);
                Client.instance.sendPacket185(20206);
                return;
            } else if (keyCode >= KeyEvent.VK_1 && keyCode <= KeyEvent.VK_9) {
                // teleport preset 1 - 9
                int buttonIDToSend = 20213 + keyCode - KeyEvent.VK_1;
                Client.instance.sendPacket185(buttonIDToSend);
                Client.instance.sendPacket185(20206);
                return;
            } else {
                // other ctrl-<key> combos
                switch (keyCode) {
                    case KeyEvent.VK_B:
                        // open bank
                        Client.instance.sendPacket185(27653);
                        break;
                    case KeyEvent.VK_S:
                        // open client settings
//                        Client.instance.stream.createFrame(185);
//                        Client.instance.stream.writeInt(39174);
//                        byte sent[] = Arrays.copyOfRange(Client.instance.stream.buffer, Client.instance.stream.currentOffset - 4, Client.instance.stream.currentOffset);
//                        System.out.println(Arrays.toString(sent));
                        break;
                    case KeyEvent.VK_E:
                        // open event settings
                        Client.instance.sendPacket185(39166);
                        break;
                    case KeyEvent.VK_C:
                        // open collection log
                        Client.instance.sendPacket185(39163);
                        break;
                    case KeyEvent.VK_P:
                        // open perks
                        Client.instance.sendPacket185(39161);
                        break;
                    case KeyEvent.VK_T:
                        // open teleport menu
                        Client.instance.sendPacket185(10003);
                        break;
                    case KeyEvent.VK_Q:
                        // toggle quick prayers
                        Client.instance.handleQuickAidsActive();
                        break;
                }
                return;
            }
        } else {
            switch (keyCode) {
                case KeyEvent.VK_ESCAPE:
                    if (Client.instance.tabID == 3) {
                        Client.setTab(10);
                    } else {
                        Client.setTab(3);
                    }
                    break;
                case KeyEvent.VK_F1:
                    Client.setTab(3);
                    break;
                case KeyEvent.VK_F2:
                    Client.setTab(4);
                    break;
                case KeyEvent.VK_F3:
                    Client.setTab(5);
                    break;
                case KeyEvent.VK_F4:
                    Client.setTab(6);
                    break;
                case KeyEvent.VK_F5:
                    Client.setTab(0);
                    break;
                case KeyEvent.VK_F6:
                    Client.setTab(2);
                    break;
                case KeyEvent.VK_F7:
                    Client.setTab(14);
                    break;
            }
        }
        if (keyChar < 30)
            keyChar = 0;
        if (keyCode == KeyEvent.VK_LEFT)
            keyChar = 1;
        if (keyCode == KeyEvent.VK_RIGHT)
            keyChar = 2;
        if (keyCode == KeyEvent.VK_UP)
            keyChar = 3;
        if (keyCode == KeyEvent.VK_DOWN)
            keyChar = 4;
        if (keyCode == KeyEvent.VK_CONTROL)
            keyChar = 5;
        if (keyCode == KeyEvent.VK_BACK_SPACE)
            keyChar = 8;
        if (keyCode == KeyEvent.VK_DELETE)
            keyChar = 8;
        if (keyCode == KeyEvent.VK_TAB)
            keyChar = 9;
        if (keyCode == KeyEvent.VK_ENTER)
            keyChar = 10;
        if (keyCode >= KeyEvent.VK_F1 && keyCode <= KeyEvent.VK_F12)
            keyChar = (1008 + keyCode) - 112;
        if (keyCode == KeyEvent.VK_HOME)
            keyChar = 1000;
        if (keyCode == KeyEvent.VK_END)
            keyChar = 1001;
        if (keyCode == KeyEvent.VK_PAGE_UP)
            keyChar = 1002;
        if (keyCode == KeyEvent.VK_PAGE_DOWN)
            keyChar = 1003;
        if (keyChar > 0 && keyChar < 128)
            keyArray[keyChar] = 1;
        if (keyChar > 4) {
            charQueue[writeIndex] = keyChar;
            writeIndex = writeIndex + 1 & 0x7f;
        }
    }

    int currentKeyCode;

    public final void keyReleased(KeyEvent keyevent) {
        Client.instance.getCallbacks().keyReleased(keyevent);
        idleTime = 0;
        currentKeyCode = -1;
        int keyCode = keyevent.getKeyCode();
        char keyChar = keyevent.getKeyChar();
        if (keyCode == KeyEvent.VK_SHIFT) {
            shiftDown = false;
        }
        if (keyCode == KeyEvent.VK_CONTROL) {
            resizing = false;
            Client.instance.controlIsDown = false;
        }
        if (keyChar < '\036') {
            keyChar = '\0';
        }
        if (keyCode == KeyEvent.VK_LEFT) {
            keyChar = '\001';
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            keyChar = '\002';
        }
        if (keyCode == KeyEvent.VK_UP) {
            keyChar = '\003';
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            keyChar = '\004';
        }
        if (keyCode == KeyEvent.VK_CONTROL) {
            keyChar = '\005';
        }
        if (keyCode == KeyEvent.VK_BACK_SPACE) {
            keyChar = '\b';
        }
        if (keyCode == KeyEvent.VK_DELETE) {
            keyChar = '\b';
        }
        if (keyCode == KeyEvent.VK_TAB) {
            keyChar = '\t';
        }
        if (keyCode == KeyEvent.VK_ENTER) {
            keyChar = '\n';
        }
        if (keyChar > 0 && keyChar < '\200') {
            keyArray[keyChar] = 0;
        }
    }

    public final void keyTyped(KeyEvent keyevent) {
        Client.instance.getCallbacks().keyTyped(keyevent);
        if (keyevent.getKeyCode() == KeyEvent.VK_SHIFT) {
            shiftDown = true;
        }
    }

    public final int readChar(int i) {
        while (i >= 0) {
            int j = 1;
            while (j > 0) {
                j++;
            }
        }
        int k = -1;
        if (writeIndex != readIndex) {
            k = charQueue[readIndex];
            readIndex = readIndex + 1 & 0x7f;
        }
        return k;
    }

    public final void focusGained(FocusEvent focusevent) {
        awtFocus = true;
        shouldClearScreen = true;
        fullRedraw = true;
        final FocusChanged focusChanged = new FocusChanged();
        focusChanged.setFocused(true);
        if(Client.instance.runelite)
        Client.instance.getCallbacks().post(focusChanged);
    }

    public final void focusLost(FocusEvent focusevent) {
        final FocusChanged focusChanged = new FocusChanged();
        focusChanged.setFocused(false);
        Client.instance.getCallbacks().post(focusChanged);
        rightClick = false;
        awtFocus = false;
        shiftDown = false;
        Client.instance.controlIsDown = false;

        for (int i = 0; i < 128; i++) {
            keyArray[i] = 0;
        }

    }

    public final void windowActivated(WindowEvent windowevent) {
    }

    public final void windowClosed(WindowEvent windowevent) {
    }

    public final void windowClosing(WindowEvent windowevent) {
    }

    public final void windowDeactivated(WindowEvent windowevent) {
    }

    public final void windowDeiconified(WindowEvent windowevent) {
    }

    public final void windowIconified(WindowEvent windowevent) {
    }

    public final void windowOpened(WindowEvent windowevent) {
    }

    abstract void startUp();

    abstract void processGameLoop();

    abstract void processDrawing();

    Component getGameComponent() {
        return this;
    }

    public void startRunnable(Runnable runnable, int i) {
        Thread thread = new Thread(runnable);
        thread.start();
        thread.setPriority(i);
    }
    static Image image;
    static Font fontHelvetica;
    static FontMetrics loginScreenFontMetrics;
    void prepareGraphics(int percent, String text) {
        try {
        while (graphics == null) {
            graphics = Client.instance.runelite ? canvas.getGraphics() : getGraphics();
            try {
                getGameComponent().repaint();
            } catch (Exception _ex) {
            }
        }
        Font font = new Font("Serif", 1, 16);
        graphics.setFont(font);
        graphics.setColor(Color.WHITE);
        if(Client.runelite){
            if (fontHelvetica == null) {
                fontHelvetica = new Font("Helvetica", Font.BOLD, 13);
                loginScreenFontMetrics = canvas.getFontMetrics(fontHelvetica);
            }
            Color color = new Color(140, 17, 17);
            graphics.drawImage(ResourceLoader.loadImage("load1.png"), 0, 0, null);
            try {
                if (image == null) {
                    image = canvas.createImage(304, 34);
                }
                Graphics imageGraphics = image.getGraphics();
                imageGraphics.setColor(color);
                imageGraphics.drawRect(0, 0, 303, 33);
                imageGraphics.fillRect(2, 2, percent * 3, 30);
                imageGraphics.setColor(Color.black);
                imageGraphics.drawRect(1, 1, 301, 31);
                imageGraphics.fillRect(percent * 3 + 2, 2, 300 - percent * 3, 30);
                imageGraphics.setFont(fontHelvetica);
                imageGraphics.setColor(Color.white);
                imageGraphics.drawString(text, (304 - loginScreenFontMetrics.stringWidth(text)) / 2, 22);
                graphics.drawImage(image, myWidth / 2 - 152, myHeight / 2 - 18, null);
            } catch (Exception exception) {
                int centerX = myWidth / 2 - 152;
                int centerY = myHeight / 2 - 18;
                graphics.setColor(color);
                graphics.drawRect(centerX, centerY, 303, 33);
                graphics.fillRect(centerX + 2, centerY + 2, percent * 3, 30);
                graphics.setColor(Color.black);
                graphics.drawRect(centerX + 1, centerY + 1, 301, 31);
                graphics.fillRect(percent * 3 + centerX + 2, centerY + 2, 300 - percent * 3, 30);
                graphics.setFont(fontHelvetica);
                graphics.setColor(Color.white);
                graphics.drawString(text, centerX + (304 - loginScreenFontMetrics.stringWidth(text)) / 2, centerY + 22);
          }
        }
        } catch (Exception exception) {
            canvas.repaint();
            exception.printStackTrace();
        }
    }

    void resetGraphic() {
        graphics = getGraphics();
        try {
            getGameComponent().repaint();
        } catch (Exception _ex) {
        }
    }

    boolean hasErrored;
    protected int contentWidth;
    protected int contentHeight;
    int canvasX;
    int canvasY;

    final void doResize() {
        Bounds bounds = this.getFrameContentBounds();
        if (this.contentWidth != bounds.highX || bounds.highY != this.contentHeight || this.resizeCanvasNextFrame) {
//            System.out.println("doResize triggered?");
            resizeCanvas();
            resizeCanvasNextFrame = false;
        }

    }

    public final void resizeCanvas() {
        if (Client.instance.isStretchedEnabled()) {
            Client.instance.invalidateStretching(false);

            if (Client.instance.isResized()) {
                Dimension realDimensions = Client.instance.getRealDimensions();

                setMaxCanvasWidth(realDimensions.width);
                setMaxCanvasHeight(realDimensions.height);
            }
        }

        Container container = this;
        if (container != null) {
            Bounds contentBounds = getFrameContentBounds();
            contentWidth = Math.max(contentBounds.highX, 0);
            contentHeight = Math.max(contentBounds.highY, 0);
//            System.out.println("contentWidth="+contentWidth+" contentHeight="+contentHeight);
            if (contentWidth <= 0) {
                contentWidth = 1;
            }

            if (contentHeight <= 0) {
                contentHeight = 1;
            }

            if (Client.instance.isResized()) {
//                System.out.println("Client.instance.isResized()");
                setMaxCanvasSize(contentWidth, contentHeight);
            }
//            System.out.println("maxCanvasWidth="+maxCanvasWidth+" maxCanvasHeight="+maxCanvasHeight);
            canvasWidth = Math.min(contentWidth, maxCanvasWidth);
            canvasHeight = Math.min(contentHeight, maxCanvasHeight);
            Client.instance.getCallbacks().post(CanvasSizeChanged.INSTANCE);
            Client.instance.clientWidth = canvasWidth;
            Client.instance.clientHeight = canvasHeight;
//            System.out.println("canvasWidth="+canvasWidth+" canvasHeight="+canvasHeight);
            canvasX = (contentWidth - canvasWidth) / 2;
            canvasY = 0;
            canvas.setSize(canvasWidth, canvasHeight);
            Client.rasterProvider = new ProducingGraphicsBuffer(canvasWidth, canvasHeight, canvas);
            canvas.setLocation(canvasX, canvasY);
            fullRedraw = true;
            resizeGame();
        }
    }

    void clearBackground() {
//        System.out.println("clearBackground");
        int canvasX = this.canvasX;
        int canvasY = this.canvasY;
        int width = contentWidth - canvasWidth - canvasX;
        int height = contentHeight - canvasHeight - canvasY;
        if (canvasX > 0 || width > 0 || canvasY > 0 || height > 0) {
            try {
                Container container = this;
                int left = 0;
                int top = 0;
//                if (container == frame) {
//                    Insets var8 = frame.getInsets();
//                    left = var8.left;
//                    top = var8.top;
//                }

                Graphics graphics = container.getGraphics();
                graphics.setColor(Color.black);
                if (canvasX > 0) {
                    graphics.fillRect(left, top, canvasX, contentHeight);
                }

                if (canvasY > 0) {
                    graphics.fillRect(left, top, contentWidth, canvasY);
                }

                if (width > 0) {
                    graphics.fillRect(left + contentWidth - width, top, width, contentHeight);
                }

                if (height > 0) {
                    graphics.fillRect(left, top + contentHeight - height, contentWidth, height);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    protected abstract void resizeGame();

    protected final void setMaxCanvasSize(int width, int height) {
//        System.out.println("setMaxCanvasSize");
        if (Client.instance.isStretchedEnabled() && Client.instance.isResized()) {
            return;
        }

        if (maxCanvasWidth != width || height != maxCanvasHeight) {
            flagResize();
        }
        maxCanvasWidth = width;
        maxCanvasHeight = height;
    }

    final void flagResize() {
        resizeCanvasNextFrame = true;
    }
    boolean resizeCanvasNextFrame;
    protected Bounds getFrameContentBounds() {

        Container container = this;
        int boundsX = Math.max(container.getWidth(), 0);
        int boundsY = Math.max(container.getHeight(), 0);
//        if (frame != null) {
//            Insets var4 = frame.getInsets();
//            boundsX -= var4.left + var4.right;
//            boundsY -= var4.top + var4.bottom;
//        }

        return new Bounds(boundsX, boundsY);
    }

    int maxCanvasWidth;
    int maxCanvasHeight;
    volatile boolean fullRedraw;
    volatile boolean isCanvasInvalid;

    @Override
    public boolean isResizeCanvasNextFrame() {
        return resizeCanvasNextFrame;
    }

    @Override
    public void setResizeCanvasNextFrame(boolean resize) {
        resizeCanvasNextFrame = resize;
    }

    @Override
    public boolean isReplaceCanvasNextFrame() {
        return isCanvasInvalid;
    }

    @Override
    public void setReplaceCanvasNextFrame(boolean replace) {
        isCanvasInvalid = replace;
    }

    @Override
    public void setMaxCanvasWidth(int width) {
//        System.out.println("setMaxCanvasWidth="+width);
        maxCanvasWidth = width;
    }

    @Override
    public void setMaxCanvasHeight(int height) {
//        System.out.println("setMaxCanvasHeight="+height);
        maxCanvasHeight = height;
    }

    @Override
    public void setFullRedraw(boolean fullRedraw) {
        this.fullRedraw = fullRedraw;
    }


    @Override
    public Canvas getCanvas() {
        return canvas;
    }


    private Thread thread;

    /**
     * Gets the client main thread.
     *
     * @return the main thread
     */
    @Override
    public Thread getClientThread() {
        return thread;
    }

    /**
     * Checks whether this code is executing on the client main thread.
     *
     * @return true if on the main thread, false otherwise
     */
    @Override
    public boolean isClientThread() {
        return thread == Thread.currentThread();
    }

}
