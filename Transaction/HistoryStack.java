package Transaction;

public class HistoryStack { // stack singlelinkedlist yang digunakan untuk menyimpan histori penyewaan
    private static class Node {
        Session data;
        Node next;
        Node(Session data) { this.data = data; }
    }

    private Node top;
    private int size = 0;

    public void push(Session session) { // untuk menyimpan data baru(push)
        Node node = new Node(session);
        node.next = top;
        top = node;
        size++;
    }

    public Session pop() { // untuk mengeluarkan data terakhir(pop)
        if (top == null) {
            return null;
        }
        Session s = top.data;
        top = top.next;
        size--;
        return s;
    }

    public boolean isEmpty() { // untuk memeriksa apakah stack kosong
        return top == null;
    }

    public int size() { // untuk mengembalikan ukuran list seperti menggunakan arraylist
        return size;
    }

    public void printHistory() {  // untuk menampilkan seluruh riwayat yang ada
        System.out.println("\nRiwayat Sesi");
        Node curr = top;
        while (curr != null) {
            curr.data.printSessionInfo();
            curr = curr.next;
        }
    }
}