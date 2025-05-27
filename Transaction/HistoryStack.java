package Transaction;

import Master.Session;

public class HistoryStack {
    private static class Node {
        Session data;
        Node next;
        Node(Session data) { this.data = data; }
    }

    private Node top;
    private int size = 0;

    public void push(Session session) {
        Node node = new Node(session);
        node.next = top;
        top = node;
        size++;
    }

    public Session pop() {
        if (top == null) {
            System.out.println("History kosong.");
            return null;
        }
        Session s = top.data;
        top = top.next;
        size--;
        return s;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }

    public void printHistory() {
        System.out.println("=== History Stack ===");
        Node curr = top;
        while (curr != null) {
            curr.data.printSessionInfo();
            curr = curr.next;
        }
    }
}
