package Transaction;
import Master.Computer.*;
import java.util.*;

public class ComputerList {
    private static class Node {
        Computer data;
        Node next;
        Node(Computer data) {
            this.data = data;
        }
    }

    private Node head, tail, current;
    private int size = 0;

    // Tambah komputer di akhir list
    public boolean add(Computer computer) {
        if (computer == null || contains(computer.getNumber())) { // akan mengembalikan false(gagal) jika computer null atau sudah ada(duplikat)
            return false;
        }

        Node newNode = new Node(computer);
        if (head == null) { // Jika list masih kosong
            head = tail = newNode;
            newNode.next = newNode; // Menunjuk ke diri sendiri (circular)
            current = head;         // Inisialisasi pointer 'current'
        } else {
            // Jika list tidak kosong, sisipkan node baru secara terurut
            // Kemungkinan 1: ketika komputer baru lebih kecil dari nomor komputer di head (menjadi head baru)
            if (newNode.data.getNumber() < head.data.getNumber()) {
                newNode.next = head;
                tail.next = newNode; // Tail menunjuk ke head baru
                head = newNode;
            }
            // Kemungkinan 2: ketika komputer baru lebih besar atau sama dengan head
            else {
                Node currentSearch = head;
                // Cari posisi yang tepat untuk menyisipkan node baru
                // Loop selama belum kembali ke awal list dan nomor komputer baru lebih besar dari komputer berikutnya
                while (currentSearch.next != head && newNode.data.getNumber() > currentSearch.next.data.getNumber()) {
                    currentSearch = currentSearch.next;
                }
                // Sisipkan newNode setelah currentSearch
                newNode.next = currentSearch.next;
                currentSearch.next = newNode;
                // Jika newNode disisipkan setelah tail lama (menjadi tail baru)
                if (newNode.next == head) {
                    tail = newNode;
                }
            }
        }
        size++;
        return true;
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
        if (head == null){
            return false;
        }
        Node curr = head;
        do {
            if (curr.data.getNumber() == number) {
                return true;
            }
            curr = curr.next;
        } while (curr != head);
        return false;
    }

    // agar bisa menggunakan method size() seperti di arraylist
    public int size() {
        return size;
    }
}