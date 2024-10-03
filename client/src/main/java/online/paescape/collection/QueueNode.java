package online.paescape.collection;


public class QueueNode extends Node {

    public QueueNode next;
    public QueueNode previous;

    public QueueNode() {
    }

    public final void unlinkQueue() {
        if (previous == null) {
        } else {
            previous.next = next;
            next.previous = previous;
            next = null;
            previous = null;
        }
    }
}
