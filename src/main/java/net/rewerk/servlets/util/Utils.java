package net.rewerk.servlets.util;

import java.util.UUID;

public abstract class Utils {
    public static boolean isValidUUID(String uuid) {
        try {
            if (uuid == null || uuid.isEmpty()) {
                return false;
            }
            UUID.fromString(uuid);
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }
}
