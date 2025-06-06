package Transaction;

import Master.User.Customer;

public class CustomerQueue {

    public static class QueueRequest {
        private final Customer customer;
        private final String requestedType;
        private final int duration;

        public QueueRequest(Customer customer, String requestedType, int duration) {
            this.customer = customer;
            this.requestedType = requestedType;
            this.duration = duration;
        }

        public Customer getCustomer() {
            return customer;
        }

        public String getRequestedType() {
            return requestedType;
        }
        
        public int getDuration() {
            return duration;
        }
    }

    private static class Node {
        QueueRequest data;
        Node next;
        Node(QueueRequest data) { this.data = data; }
    }

    private Node head, tail;
    private int size = 0;

    public void enqueue(QueueRequest request) {
        Node newNode = new Node(request);
        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;
        if (head == null) {
            head = newNode;
        }
        size++;
        System.out.println("Customer " + request.getCustomer().getName() + " masuk antrian untuk komputer " + request.getRequestedType() + ".");
    }

    public QueueRequest dequeueFor(String availableComputerType) {
        if (isEmpty()) {
            return null;
        }

        Node prev = null;
        Node current = head;

        // Iterasi melalui antrian untuk mencari yang cocok pertama kali
        while (current != null) {
            if (current.data.getRequestedType().equalsIgnoreCase(availableComputerType)) {
                // Ditemukan, hapus dari linked list
                if (prev == null) {
                    head = current.next; // Jika yang dihapus adalah head
                } else {
                    prev.next = current.next;
                }

                if (current == tail) {
                    tail = prev; // Jika yang dihapus adalah tail
                }
                
                if (head == null) {
                    tail = null; // Jika antrian menjadi kosong
                }

                size--;
                System.out.println("Customer " + current.data.getCustomer().getName() + " keluar dari antrian untuk dilayani.");
                return current.data;
            }
            // Lanjut ke node berikutnya
            prev = current;
            current = current.next;
        }

        // Tidak ada pelanggan yang menunggu tipe komputer ini
        return null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }
}
