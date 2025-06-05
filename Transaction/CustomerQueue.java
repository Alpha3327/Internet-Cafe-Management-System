package Transaction;
import Master.User.*;

public class CustomerQueue {
    private static class Node {
        Customer data;
        Node next;
        Node(Customer data) { this.data = data; }
    }
    private Node head, tail;
    private int size = 0;

    public void enqueue(Customer c) {
        Node node = new Node(c);
        if (tail != null) tail.next = node;
        tail = node;
        if (head == null) head = node;
        size++;
        System.out.println("Customer " + c.getName() + " masuk antrian.");
    }

    public Customer dequeue() {
        if (isEmpty()) {
            System.out.println("Queue kosong");
        }

        Customer data = head.data;
        head = head.next;
        if (head == null) tail = null;
        size--;
        System.out.println("Customer " + data.getName() + " di-dequeue.");
        return data;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }
}