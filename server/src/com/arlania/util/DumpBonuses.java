package com.arlania.util;

import com.arlania.model.definitions.ItemDefinition;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DumpBonuses {

    public static void main(String[] args) {
        new DumpBonuses().run();
    }

    public void run() {
        try {
            ItemDefinition.init();
            DataOutputStream out = new DataOutputStream(new FileOutputStream("./ItemDefs.dat", false));

            out.writeShort(ItemDefinition.getMaxAmountOfItems());

            int totalEquipable = 0;
            for (ItemDefinition item : ItemDefinition.getDefinitions()) {
                if (item == null || item.getEquipmentType() == ItemDefinition.EquipmentType.WEAPON && !item.isWeapon())
                    continue;
                totalEquipable++;
            }

            out.writeShort(totalEquipable);

            for (ItemDefinition item : ItemDefinition.getDefinitions()) {
                if (item == null || item.getEquipmentType() == ItemDefinition.EquipmentType.WEAPON && !item.isWeapon())
                    continue;
                System.out.println("dumping " + item.getId());

                int[] bonuses = new int[14]; // 5 att, 5 def, 1 pray, 1 str, 1 ranged str, 1 magic dmg
                int index = 0;
                for (int i = 0; i < item.getBonus().length; i++) {
                    if (i >= 10 && i <= 13) {
                        continue;
                    }
                    int value = (int) item.getBonus()[i];
                    bonuses[index] = Math.min(value, 30000);
                    index++;
                }

                out.writeShort(item.getId());
                for (int bonus : bonuses) {
                    out.writeShort(bonus);
                }
            }


            out.close();
            System.out.println();
            System.out.println(ItemDefinition.getMaxAmountOfItems());
            System.out.println(totalEquipable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
