package online.paescape;

import lombok.extern.slf4j.Slf4j;
import online.paescape.cache.ResourceLoader;
import online.paescape.util.Configuration;
import online.paescape.util.Signlink;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.InetAddress;
import java.net.URI;

@Slf4j
public class MainGameWindow {

    public static JFrame frame;
    public static TrayIcon trayIcon;
    public static Toolkit toolkit = Toolkit.getDefaultToolkit();
    public static Dimension screenSize = toolkit.getScreenSize();
    public static int screenWidth = (int) screenSize.getWidth();
    public static int screenHeight = (int) screenSize.getHeight();

    private static final ButtonHandler buttonHandler = new ButtonHandler();

    public static void main(String args[]) {
        if (args.length > 0) {
            Configuration.DEBUG = args[0].equals("-debug");
            if (Configuration.DEBUG) {
                Configuration.HOST = "127.0.0.1";
            } else if (args[0].equals("-server")) {
                Configuration.HOST = args[1];
            }
        }
        try {
            Client.instance = new Client(Client.clientWidth, Client.clientHeight, false);
            setTray();
            Signlink.startpriv(InetAddress.getByName(Configuration.HOST));
            initUI(Client.clientWidth, Client.clientHeight, false);
        } catch (Exception exception) {
            log.error("Something went wrong loading the client.", exception);
        }
    }

    public static void setClientIcon() {
        Image img = ResourceLoader.loadImage("icon.png");
        if (img == null)
            return;
        frame.setIconImage(img);
    }

    /**
     * Opens a URL in your default web browser
     *
     * @param url The url of the website to open
     */
    public static void openURL(String url) {
        Desktop d = Desktop.getDesktop();
        try {
            d.browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setTray() {
        if (SystemTray.isSupported() && !System.getProperty("os.version").contains("Android")) {
            Image icon = ResourceLoader.loadImage("icon.png");
            trayIcon = new TrayIcon(icon, Configuration.CLIENT_NAME);
            trayIcon.setImageAutoSize(true);
            try {
                SystemTray tray = SystemTray.getSystemTray();
                tray.add(trayIcon);
                trayIcon.displayMessage(Configuration.CLIENT_NAME, Configuration.CLIENT_NAME + " has been launched!",
                        TrayIcon.MessageType.INFO);

                final MenuItem minimizeItem = new MenuItem("Hide " + Configuration.CLIENT_NAME);
                MenuItem BLANK = new MenuItem("-");
                MenuItem exitItem = new MenuItem("Quit");
                ActionListener minimizeListener = new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (frame.isVisible()) {
                            frame.setVisible(false);
                            minimizeItem.setLabel("Show 1# " + Configuration.CLIENT_NAME + ".");
                        } else {
                            frame.setVisible(true);
                            minimizeItem.setLabel("Hide 1# " + Configuration.CLIENT_NAME + ".");
                        }
                    }
                };
                minimizeItem.addActionListener(minimizeListener);
                ActionListener exitListener = new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                };
                exitItem.addActionListener(exitListener);
            } catch (AWTException e) {
                System.err.println(e);
            }
        }
    }

    public static void addWindowCloseListener() {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                String[] options = {"Yes", "No"};
                int userPrompt = JOptionPane.showOptionDialog(null, "Are you sure you wish to exit?", "PaeScape",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
                if (userPrompt == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    // TODO
    public static void initUI(int width, int height, boolean resizable) {
        if (!System.getProperty("os.version").contains("Android")) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                JPopupMenu.setDefaultLightWeightPopupEnabled(false);
                frame = new JFrame(Configuration.CLIENT_NAME);
                frame.setLayout(new BorderLayout());
                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                addWindowCloseListener();
                Client.instance.setFocusTraversalKeysEnabled(false);
                frame.getContentPane().add(Client.instance, BorderLayout.CENTER);
                initializeMenuBar(frame);
                Client.instance.setPreferredSize(new Dimension(width, height));
                frame.pack();
                frame.setResizable(resizable);
                Client.instance.init();
                setClientIcon();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            frame = new JFrame(Configuration.CLIENT_NAME);
            Client.instance.setFocusTraversalKeysEnabled(false);
            Client.instance.setPreferredSize(new Dimension(width, height));
            frame.getContentPane().add(Client.instance, BorderLayout.CENTER);
            frame.pack();
            frame.setResizable(false);
            Client.instance.init();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }

    public static void rebuildFrame(int width, int height, boolean resizable, boolean undecorated) {
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        frame = new JFrame(Configuration.CLIENT_NAME);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowCloseListener();
        frame.setUndecorated(undecorated);
        Client.instance.setFocusTraversalKeysEnabled(false);
        Client.instance.setPreferredSize(new Dimension(width, height));
        if (!undecorated) {
            initializeMenuBar(frame);
        }
        frame.getContentPane().add(Client.instance, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(resizable);
        Client.instance.graphics = Client.instance.getGameComponent().getGraphics();
        frame.setLocation((screenWidth - width) / 2, ((screenHeight - height) / 2));
        frame.setVisible(true);


        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Client.instance.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));
                Client.instance.revalidate();
                Client.instance.repaint();
                Client.instance.graphics = Client.instance.getGameComponent().getGraphics();
            }
        });
        setClientIcon();
    }

    /**
     * Initializes the menu bar
     */
    public static void initializeMenuBar(JFrame jFrame) {

        /*
         * Initialize our menu panel to hold our menu buttons
         */
        /**
         * Our jpanel for the menu bar
         */
        JPanel menuPanel = new JPanel();

        /*
         * Set the menu panel as non focusable
         */
        menuPanel.setFocusable(false);

        /*
         * Disable focus traversal keys
         */
        menuPanel.setFocusTraversalKeysEnabled(false);

        menuPanel.setBackground(Color.decode("#021c47"));

        menuPanel.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        menuPanel.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);

        /*
         * Create our buttons
         */

        JButton wikiButton = createButton("Wiki", "House_icon.png", "Open the official PaeScape wiki.");
//        JButton forumsButton = createButton("Forum", "forums.png", "Open the official PaeScape forums.");
//        JButton knowledgeBaseButton = createButton("Knowledge Base", "kb.gif", "Open the PaeScape Knowledge Base on the forums.");
        JButton storeButton = createButton("Store", "cart_icon.gif", "Open the official PaeScape store.");
        JButton voteButton = createButton("Vote", "Small-checkmark.png", "Open the official PaeScape voting page.");
        JButton scoresButton = createButton("High Scores", "hiscores.png", "Open the official PaeScape Hiscores");
        JButton discordButton = createButton("Join Discord", "discord.png", "Join the PaeScape discord.");

        /*
         * Add our buttons to the menu panel
         */

        menuPanel.add(discordButton);
        menuPanel.add(wikiButton);
        menuPanel.add(voteButton);
        menuPanel.add(storeButton);
        //menuPanel.add(forumsButton);
        //menuPanel.add(knowledgeBaseButton);
        menuPanel.add(scoresButton);

        /*
         * Add our menu panel to our frame
         */
        jFrame.getContentPane().add(menuPanel, BorderLayout.NORTH);
    }

    /**
     * Creates a button on the menu panel
     *
     * @param title   The Title of the button
     * @param image   The image to display
     * @param tooltip The tooltip when hovering over the button
     * @return The created button
     */
    private static JButton createButton(String title, String image, String tooltip) {
        JButton button = new JButton(title);
        if (image != null)
            button.setIcon(new ImageIcon(ResourceLoader.loadImage(image)));
        button.addActionListener(buttonHandler);
        if (tooltip != null)
            button.setToolTipText(tooltip);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setForeground(Color.WHITE);
        return button;
    }

    private static class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            String cmd = evt.getActionCommand();
            try {
                if (cmd != null) {
                    switch (cmd) {
                        case "Wiki":
                            openURL("https://wiki.paescape.online");
                            break;
                        case "Forum":
                            //openURL("http://www.PaeScape.org/forum");
                            break;
                        case "Knowledge Base":
                            //openURL("http://www.PaeScape.org/forum");
                            break;
                        case "Store":
                            openURL(Configuration.STORE_URL);
                            break;
                        case "Vote":
//                            openURL("");
                            break;
                        case "High Scores":
                            openURL("https://highscores.paescape.online/");
                            break;
                        case "Join Discord":
                            //String nickname = (Client.instance.getMyUsername() != null && Client.loggedIn && Client.instance.getMyUsername().length() > 2) ? TextClass.fixName(Client.instance.getMyUsername().replaceAll(" ", "%20")) : "ForumGuest";
//                            openURL("");
                            break;
                    }

                }
            } catch (Exception e) {
            }
        }
    }
}