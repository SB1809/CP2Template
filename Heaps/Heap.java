package Heaps;
//Sophia Babayev-2/12/26-creates a working Heap data structure (hopefully)
public class Heap {

//the actual storage structure for your heap

private int[] arr;

 

//constructor for your heap
//feel free to make one that takes in an array if you prefer for your testing purposes.
//note that we will not be inserting more than 100 elements into our array so you need not worry about re-sizing
//the array


// Pre: none
// Post: empty heap created with array of size 100
public Heap() {

arr = new int[100];

}

 
private int size = 0;
 

//create this function to add elements to your heap
//all heap properties must be preserved
// 5 points

// Pre: heap is in valid max-heap state
// Post: add elements to heap and max-heap property maintained
public void add(int toAdd) {
    arr[size] = toAdd;
    siftUp(size);
    size++;
}

 

//remove the largest element of the heap (the root) and re-heapafy
//5 points

// Pre: heap is not empty
// Post: root (max element) removed and re-heapafyed
public int removeMax() {
    if (size == 0) {
      return -1;
    }
    size--;
    int ret = arr[0];
    arr[0] = arr[size];
    siftDown(0);
    return ret;
}

 

//this should check and alter the tree after an item is inserted
//3 points

// Pre: element at index doesn't have heap property with parent
// Post: element at index and all parents satisfy max-heap property
private void siftUp(int index) {
    int parentIndex = (index - 1) / 2;
    if (index > 0 && arr[index] > arr[parentIndex]) {
        int temp = arr[index];
        arr[index] = arr[parentIndex];
        arr[parentIndex] = temp;
        siftUp(parentIndex);
    }
} 

 

//this should check and alter the tree after an item is deleted.
//3 points

// Pre: element at index may doesn't have heap property with children
// Post: element at index and all descendants satisfy max-heap property
private void siftDown(int index) {
    int leftChildIndex = 2 * index + 1;
    int rightChildIndex = 2 * index + 2;
    int largestIndex = index;

    if (leftChildIndex < size && arr[leftChildIndex] > arr[largestIndex]) {
        largestIndex = leftChildIndex;
    }
    if (rightChildIndex < size && arr[rightChildIndex] > arr[largestIndex]) {
        largestIndex = rightChildIndex;
    }
    if (largestIndex != index) {
        int temp = arr[index];
        arr[index] = arr[largestIndex];
        arr[largestIndex] = temp;
        siftDown(largestIndex);
    }
}

 

//4 points for syntax conventions.

 

}
