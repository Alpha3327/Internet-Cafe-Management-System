package Transaction;
import Master.*;
import java.util.*;

public class ComputerCircularList {
    private static class Node {
        Computer data;
        Node next;
        Node(Computer data) { this.data = data; }
    }

    private Node head;
    private Node tail;
    private Node current;
    private int size = 0;

    // Tambah komputer di akhir list
    public void add(Computer computer) {
        if (contains(computer.getNumber())) {
            System.out.printf("Komputer dengan nomor %d sudah ada.\n",computer.getNumber());
            System.out.println("Computer dengan nomor " + computer.getNumber() + " sudah ada.");
            return;
        }
        Node node = new Node(computer);
        if (head == null) {
            head = tail = node;
            node.next = node;
            current = head;
        } else {
            tail.next = node;
            node.next = head;
            tail = node;
        }
        size++;
    }

    // Hapus komputer by ID, return true kalau berhasil
    public boolean remove(int number) {
        if (head == null) return false;
        Node prev = tail;
        Node curr = head;
        do {
            if (curr.data.getNumber() == number) {
                if (curr == head) head = head.next;
                if (curr == tail) tail = prev;
                prev.next = curr.next;
                if (current == curr) current = curr.next;
                size--;
                if (size == 0) {
                    head = tail = current = null;
                }
                return true;
            }
            prev = curr;
            curr = curr.next;
        } while (curr != head);
        return false;
    }

    // Mencari komputer yang available
    public Computer getNextAvailable() {
        if (head == null) return null;
        Node start = (current != null ? current : head);
        Node node = start;
        do {
            if (node.data.isAvailable()) {
                current = node.next;
                return node.data;
            }
            node = node.next;
        } while (node != start);
        return null;
    }

    // Untuk menampilkan semua komputer
    public ArrayList<Computer> toList() {
        ArrayList<Computer> list = new ArrayList<>();
        if (head == null) return list;
        Node tmp = head;
        do {
            list.add(tmp.data);
            tmp = tmp.next;
        } while (tmp != head);
        return list;
    }

    private boolean contains(int number) {
        if (head == null) return false;
        Node curr = head;
        do {
            if (curr.data.getNumber() == number) return true;
            curr = curr.next;
        } while (curr != head);
        return false;
    }

    public int size() {
        return size;
    }
}