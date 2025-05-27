package Transaction;
import java.util.*;
import Master.*;

public class ComputerCircularList {
    private static class Node {
        Computer data;
        Node next;
        Node(Computer data) { this.data = data; }
    }

    private Node head;
    private Node current;  // pointer untuk next available
    private int size = 0;

    // Tambah komputer di akhir list
    public void add(Computer computer) {
        Node node = new Node(computer);
        if (head == null) {
            head = node;
            head.next = head;
        } else {
            Node tail = head;
            while (tail.next != head) {
                tail = tail.next;
            }
            tail.next = node;
            node.next = head;
        }
        size++;
        if (current == null) current = head;
    }

    // Hapus komputer by ID, return true kalau berhasil
    public boolean remove(int compId) {
        if (head == null) return false;
        Node prev = head, tmp = head;
        do {
            if (tmp.data.getNumber() == compId) {
                if (tmp == head && size==1) {
                    head = null;
                } else {
                    Node p = head;
                    while (p.next != tmp) p = p.next;
                    p.next = tmp.next;
                    if (tmp == head) head = tmp.next;
                }
                size--;
                if (current == tmp) current = head;
                return true;
            }
            prev = tmp;
            tmp = tmp.next;
        } while (tmp != head);
        return false;
    }

    // Cari dan kembalikan komputer pertama yang available
    public Computer getNextAvailable() {
        if (head == null) return null;
        Node start = current;
        do {
            if (current.data.isAvailable()) {
                Computer found = current.data;
                current = current.next;
                return found;
            }
            current = current.next;
        } while (current != start);
        return null;
    }

    // Untuk menampilkan semua komputer
    public List<Computer> toList() {
        List<Computer> list = new ArrayList<>();
        if (head == null) return list;
        Node tmp = head;
        do {
            list.add(tmp.data);
            tmp = tmp.next;
        } while (tmp != head);
        return list;
    }

    public int size() { return size; }
}
