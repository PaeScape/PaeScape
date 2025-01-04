package com.arlania.util;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.arlania.GameServer;
import com.arlania.world.entity.impl.player.Player;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.logging.Level;

public class PasswordUtils {
    private static final String salt = "voidstarisgreat!";
    private static final int cost = 13;

    public static byte[] hash(String password) {
        return BCrypt.withDefaults().hash(cost, salt.getBytes(StandardCharsets.UTF_8), password.getBytes(StandardCharsets.UTF_8));
    }

    private static boolean checkPassword(String password, byte[] hash) {
        return BCrypt.verifyer().verify(password.getBytes(StandardCharsets.UTF_8), hash).verified;
    }

    public static boolean verifyAndUpdateHash(Player player, String password) {
        int hashCost;
        try {
            hashCost = getCost(player.getPasswordHash());
        } catch (Exception e) {
            GameServer.getLogger().log(Level.SEVERE, player.getUsername() + " unable to log in due to ", e);
            return false;
        }
        if (checkPassword(password, player.getPasswordHash())) {
            if (hashCost < cost) {
                GameServer.getLogger().info(String.format("Updating %s's password from %d rounds to %d hash cost.", player.getUsername(), hashCost, cost));
                player.setPasswordHash(hash(password));
            }
            return true;
        }
        return false;
    }

    private static int getCost(byte[] hash) {
        byte minor = 0;
        int off = 0;

        if (hash[0] != '$' || hash[1] != '2')
            throw new IllegalArgumentException("Invalid hash version");
        if (hash[2] == '$') {
            off = 3;
        } else {
            minor = hash[2];
            if (minor != 'a' || hash[3] != '$')
                throw new IllegalArgumentException("Invalid hash revision");
            off = 4;
        }

        // Extract number of rounds
        if (hash[off + 2] > '$')
            throw new IllegalArgumentException("Missing hash rounds");
        return Integer.parseInt(new String(Arrays.copyOfRange(hash, off, off + 2), StandardCharsets.UTF_8));
    }
}
