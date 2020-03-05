/*
  Name: Colton Hagan
  Date: 3/4/20
  Class: 241
  Description: Integer Heap with limited size and no delate
*/
public class HeapMax {
   public int[] Heap;
   private int size;
   private int maxSize;
   //Constructor -- Takes in maxSize and sets it
   public HeapMax(int maxSize){ 
      this.maxSize = maxSize;
      size = 0;
      Heap = new int[maxSize+1];
   }
   //Uses given position to calculate and return position of parent
   private int getParent(int pos) { 
      return pos/2;
   }
   //Uses give position to calculate and return the positions of the left child
   private int getLeftChild(int pos) { 
      return pos*2;
   }
   //Uses give position to calculate and return the positions of the right child
   private int getRightChild(int pos) { 
      return pos*2+1;
   }
   //returns boolean if given position is pointing to a leaf
   private boolean isLeaf(int pos) { 
      if(pos * 2 > size) {
         System.out.println(pos * 2);
         return true;
      } 
      return false;
   }
   //takes ib two positions of heap and swaps them
   private void swap(int index1, int index2) {
      int temp = Heap[index2];
      Heap[index2] = Heap[index1];
      Heap[index1] = temp;
   }
   
   //Takes in a element and inserts it into the heap
   public void insert(int element){ 
      size++;
      Heap[size] = element;
      int pos = size;
      while(pos > 1) {
         int parent = getParent(pos);
         //Move up and swaps if value > then parrent
         if(Heap[pos] > Heap[parent]) {
            swap(parent, pos);
            pos = getParent(pos);
         //Uses as a way to break loop if it is a max-heap
         } else {
            pos = 1;
         }
      }
   }
   //Prints the contents of current heap
   public void printMaxHeap(){ 
      for(int i = 1; i <= size; i++) {
         String leftValue;
         String rightValue;
         //If left or right child does not exist prints NA instead of 0
         if(getLeftChild(i) > size) {
            leftValue = "NA";
         } else {
            leftValue = "" + Heap[getLeftChild(i)];
         }
         if(getRightChild(i) > size) {
            rightValue = "NA";
         } else {
            rightValue = "" + Heap[getRightChild(i)];
         }
         System.out.println("Heap Node : " + Heap[i] + ", left child : " + leftValue+ ", right child : " + rightValue);
      }
   }
}