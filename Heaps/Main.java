package Heaps;

public class Main {
public static void main(String[] args) {
    Heap heap = new Heap();
    heap.add(5);
    heap.add(3);
    heap.add(8);
    heap.add(1);
    heap.add(7);
    heap.add(6);
    heap.add(4);
    heap.add(2);
    for(int i =0; i<8; i++) {
        System.out.println(heap.removeMax());
    }
}
}
