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
        int number = computer.getNumber();
        // 1. Cek duplikat
        if (contains(number)) {
            System.out.printf("Komputer dengan nomor %d sudah ada.\n", number);
            return;  // keluar tanpa menambahkan
        }

        Node newNode = new Node(computer);
        if (head == null) {
            // list kosong: head=tail=newNode, self-loop
            head = tail = newNode;
            newNode.next = newNode;
        } else {
            // cari posisi sisip: sebelum first yang > num, atau di akhir
            Node curr = head, prev = tail;
            do {
                if (curr.data.getNumber() > number) break;
                prev = curr;
                curr = curr.next;
            } while (curr != head);

            // sisip di antara prev dan curr
            prev.next = newNode;
            newNode.next = curr;

            // update head/tail jika perlu
            if (curr == head && number < head.data.getNumber()) {
                head = newNode;
            }
            if (prev == tail && curr == head) {
                tail = newNode;
            }
        }
        size++;
        System.out.printf("Komputer nomor %d berhasil ditambahkan.\n", number);
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

    private boolean contains(int number) {
        if (head == null) return false;
        Node current = head;
        do {
            if (current.data.getNumber() == number) return true;
            current = current.next;
        } while (current != head);
        return false;
    }

    public int size() {
        return size;
    }
}
