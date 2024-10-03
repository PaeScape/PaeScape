package com.arlania.world.content;

public class EmojiHandler {

    public static boolean checkEmoji(String emoji) {

        switch (emoji) {

            case "heart":
            case "smile":
            case "happy":
            case "sad":
            case "coins":
                return true;


        }

        return false;
    }


}
