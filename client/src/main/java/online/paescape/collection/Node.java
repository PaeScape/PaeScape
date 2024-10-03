package online.paescape.collection;

import net.runelite.rs.api.RSNode;

public class Node implements RSNode {

    public long hash;

    public Node prev1;
    public Node next1;

    public Node() {
    }

    public void unlink() {
        if (next1 != null) {
            next1.prev1 = prev1;
            prev1.next1 = next1;
            prev1 = null;
            next1 = null;
        }
    }
    @Override
    public RSNode getNext() {
        return prev1;
    }

    @Override
    public long getHash() {
        return hash;
    }

    @Override
    public RSNode getPrevious() {
        return next1;
    }

    @Override
    public void onUnlink() {

    }

}
