package online.paescape.collection;


import java.util.Hashtable;

public final class MemCache {

    private final QueueNode queue_head;
    private final int max_size;
    private final Hashtable<Long, QueueNode> hashTable;
    private final Queue usageList;
    private int free_slots;

    public MemCache(int size) {
        queue_head = new QueueNode();
        usageList = new Queue();
        max_size = size;
        free_slots = size;
        hashTable = new Hashtable<>(1024);
    }

    public QueueNode get(long hash) {
        QueueNode node = hashTable.get(hash);
        if (node != null) {
            usageList.insertBack(node);
        }
        return node;
    }

    public void put(QueueNode entry, long hash) {
        try {
            if (free_slots == 0) {
                QueueNode dropEntry = usageList.popFront();
                dropEntry.unlink();
                dropEntry.unlinkQueue();
                if (dropEntry == queue_head) {
                    dropEntry = usageList.popFront();
                    dropEntry.unlink();
                    dropEntry.unlinkQueue();
                }
            } else {
                free_slots--;
            }
            hashTable.put(hash, entry);
            usageList.insertBack(entry);
            return;
        } catch (RuntimeException runtimeexception) {
            System.out.println("47547, " + entry + ", " + hash + ", " + (byte) 2 + ", " + runtimeexception);
        }
        throw new RuntimeException();
    }

    public void clear() {
        do {
            QueueNode node = usageList.popFront();
            if (node != null) {
                node.unlink();
                node.unlinkQueue();
            } else {
                free_slots = max_size;
                return;
            }
        } while (true);
    }

    public Queue getUsageList() {
        return usageList;
    }

    public Hashtable<Long, QueueNode> getHashTable() {
        return hashTable;
    }
}
