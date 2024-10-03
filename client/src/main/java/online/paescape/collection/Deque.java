package online.paescape.collection;

import net.runelite.rs.api.RSNode;
import net.runelite.rs.api.RSNodeDeque;

import java.util.Iterator;

public final class Deque implements RSNodeDeque {

    private final Node head;
    private Node current;

    public Deque() {
        head = new Node();
        head.prev1 = head;
        head.next1 = head;
    }

    public void insertHead(Node node) {
        if (node.next1 != null)
            node.unlink();
        node.next1 = head.next1;
        node.prev1 = head;
        node.next1.prev1 = node;
        node.prev1.next1 = node;
    }

    public void insertTail(Node node) {
        if (node.next1 != null)
            node.unlink();
        node.next1 = head;
        node.prev1 = head.prev1;
        node.next1.prev1 = node;
        node.prev1.next1 = node;
    }

    public Node popHead() {
        Node node = head.prev1;
        if (node == head) {
            return null;
        } else {
            node.unlink();
            return node;
        }
    }

    public Node reverseGetFirst() {
        Node node = head.prev1;
        if (node == head) {
            current = null;
            return null;
        } else {
            current = node.prev1;
            return node;
        }
    }

    public Node getFirst() {
        Node node = head.next1;
        if (node == head) {
            current = null;
            return null;
        } else {
            current = node.next1;
            return node;
        }
    }

    public Node reverseGetNext() {
        Node node = current;
        if (node == head) {
            current = null;
            return null;
        } else {
            current = node.prev1;
            return node;
        }
    }

    public Node getNext() {
        Node node = current;
        if (node == head) {
            current = null;
            return null;
        }
        current = node.next1;
        return node;
    }

    public void removeAll() {
        if (head.prev1 == head)
            return;
        do {
            Node node = head.prev1;
            if (node == head)
                return;
            node.unlink();
        } while (true);
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public RSNode getCurrent() {
        return current;
    }

    @Override
    public RSNode getSentinel() {
        return head;
    }

    @Override
    public RSNode last() {
        return reverseGetFirst();
    }

    @Override
    public RSNode previous() {
        return getFirst();
    }

    @Override
    public void addFirst(RSNode val) {
        insertHead((Node) val);
    }

    @Override
    public RSNode removeLast() {
        return reverseGetNext();
    }

    @Override
    public void clear() {
        removeAll();
    }
}
