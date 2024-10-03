package com.arlania.world.clip.stream;


import com.arlania.GameServer;

import java.util.logging.Level;

public class MemoryArchive {

    private final ByteStream cache;
    private final ByteStream index;
    private static final int INDEX_DATA_CHUNK_SIZE = 12;

    public MemoryArchive(ByteStream cache, ByteStream index) {
        this.cache = cache;
        this.index = index;
    }

    public byte[] get(int dataIndex) {
        try {
            if (index.length() < (dataIndex * INDEX_DATA_CHUNK_SIZE))
                return null;
            index.setOffset(dataIndex * INDEX_DATA_CHUNK_SIZE);
            long fileOffset = index.getLong();
            int fileSize = index.getInt();
            cache.setOffset(fileOffset);
            byte[] buffer = cache.read(fileSize);
            return buffer;
        } catch (Exception e) {
             GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
            return null;
        }
    }

    public int contentSize() {
        return index.length() / 12;
    }

}




