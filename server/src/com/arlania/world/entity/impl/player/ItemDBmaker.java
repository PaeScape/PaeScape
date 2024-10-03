package com.arlania.world.entity.impl.player;

import com.arlania.GameServer;

import java.io.*;
import java.util.logging.Level;


public class ItemDBmaker {


    /**
     * The directory in which item definitions are found.
     */
    private static final String FILE_DIRECTORY = "./data/def/txt/Items.txt";
    private static final String dbFILE_DIRECTORY = "./data/def/txt/ItemsDB.txt";

    /**
     * The max amount of items that will be loaded.
     */
    private static final int MAX_AMOUNT_OF_ITEMS = 22694;

    /**
     * ItemDBmaker array containing all items' definition values.
     */
    private static final ItemDBmaker[] definitions = new ItemDBmaker[MAX_AMOUNT_OF_ITEMS];

    /**
     * Loading all item definitions
     */
    public static void init() {
        ItemDBmaker definition = definitions[0];
        try {
            File file = new File(FILE_DIRECTORY);
            File dbfile = new File(dbFILE_DIRECTORY);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            FileWriter writer = new FileWriter(dbfile);
            String line = "";
            dbfile.getParentFile().setWritable(true);

            if (!dbfile.getParentFile().exists()) {
                try {
                    dbfile.getParentFile().mkdirs();
                } catch (SecurityException e) {
                    GameServer.getLogger().log(Level.SEVERE, "Unable to create directory for player data!", e);
                }
            }


            while (line != "end") {

                while (line != "finish") {
                    line = reader.readLine();

                    if (line.contains("Item Id:")) {
                        writer.write(line);
                    } else {
                        writer.write("Item Id: null");
                    }
                    line = reader.readLine();

                    if (!line.contains("Name:")) {
                        writer.write("Name: null");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();

                    if (!line.contains("Examine:")) {
                        writer.write("Examine: null");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Value:")) {
                        writer.write("Value: null");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Stackable:")) {
                        writer.write("Stackable: null");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Noted:")) {
                        writer.write("Noted: null");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Double-handed:")) {
                        writer.write("Double-handed: null");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Equipment type:")) {
                        writer.write("Equipment type: null");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Is Weapon:")) {
                        writer.write("Is Weapon: null");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Bonus[0]:")) {
                        writer.write("Bonus[0]: 0");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Bonus[1]:")) {
                        writer.write("Bonus[1]: 0");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Bonus[2]:")) {
                        writer.write("Bonus[2]: 0");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Bonus[3]:")) {
                        writer.write("Bonus[3]: 0");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Bonus[4]:")) {
                        writer.write("Bonus[4]: 0");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Bonus[5]:")) {
                        writer.write("Bonus[5]: 0");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Bonus[6]:")) {
                        writer.write("Bonus[6]: 0");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Bonus[7]:")) {
                        writer.write("Bonus[7]: 0");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Bonus[8]:")) {
                        writer.write("Bonus[8]: 0");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Bonus[9]:")) {
                        writer.write("Bonus[9]: 0");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Bonus[10]:")) {
                        writer.write("Bonus[10]: 0");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Bonus[11]:")) {
                        writer.write("Bonus[11]: 0");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Bonus[12]:")) {
                        writer.write("Bonus[12]: 0");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Bonus[13]:")) {
                        writer.write("Bonus[13]: 0");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Bonus[14]:")) {
                        writer.write("Bonus[14]: 0");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Bonus[15]:")) {
                        writer.write("Bonus[15]: 0");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Bonus[16]:")) {
                        writer.write("Bonus[16]: 0");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Bonus[17]:")) {
                        writer.write("Bonus[17]: 0");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Bonus[18]:")) {
                        writer.write("Bonus[18]: 0");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Requirement[0]:")) {
                        writer.write("Requirement[0]: 1");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Requirement[1]:")) {
                        writer.write("Requirement[1]: 1");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Requirement[2]:")) {
                        writer.write("Requirement[2]: 1");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Requirement[3]:")) {
                        writer.write("Requirement[3]: 1");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Requirement[4]:")) {
                        writer.write("Requirement[4]: 1");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Requirement[5]:")) {
                        writer.write("Requirement[5]: 1");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Requirement[6]:")) {
                        writer.write("Requirement[6]: 1");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Requirement[7]:")) {
                        writer.write("Requirement[7]: 1");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Requirement[8]:")) {
                        writer.write("Requirement[8]: 1");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Requirement[9]:")) {
                        writer.write("Requirement[9]: 1");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Requirement[10]:")) {
                        writer.write("Requirement[10]: 1");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Requirement[11]:")) {
                        writer.write("Requirement[11]: 1");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Requirement[12]:")) {
                        writer.write("Requirement[12]: 1");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Requirement[13]:")) {
                        writer.write("Requirement[13]: 1");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Requirement[14]:")) {
                        writer.write("Requirement[14]: 1");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Requirement[15]:")) {
                        writer.write("Requirement[15]: 1");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Requirement[16]:")) {
                        writer.write("Requirement[16]: 1");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Requirement[17]:")) {
                        writer.write("Requirement[17]: 1");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Requirement[18]:")) {
                        writer.write("Requirement[18]: 1");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Requirement[19]:")) {
                        writer.write("Requirement[19]: 1");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Requirement[20]:")) {
                        writer.write("Requirement[20]: 1");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Requirement[21]:")) {
                        writer.write("Requirement[21]: 1");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Requirement[22]:")) {
                        writer.write("Requirement[22]: 1");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Requirement[23]:")) {
                        writer.write("Requirement[23]: 1");
                    } else {
                        writer.write(line);
                    }
                    line = reader.readLine();


                    if (!line.contains("Requirement[24]:")) {
                        writer.write("Requirement[24]: 1");
                    } else {
                        writer.write(line);
                    }
                }

            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
        }
    }

}