package com.fges.ckonsoru.data;

import java.util.UUID;

/**
 * This class defines the methods to generate identifiers
 */
public class Id {
    /**
     * Generate a uniq identifier
     * @return the generated identifier
     */
    public static UUID makeId() {
        return UUID.randomUUID();
    }
}
