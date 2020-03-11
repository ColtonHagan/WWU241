//Node to self ints automatically convert to floor so no extra comand is needed
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
public class TakeoffQueue {
   private static ArrayList<Airplane> heap;
   private static int method;
   public static void main(String[] args) throws IOException { 
      String arg[] = {"flightsA.txt", "3"};
      method = Integer.parseInt(arg[1]);
      heap = new ArrayList<Airplane>();
      //filler value used for error reading
      Airplane filler = new Airplane("filler", "filer", "filler", "filler", -1, -1);
      heap.add(filler);
      readFile(arg[0]);
      String lastTime = "12:59";
      int lastTimeInMin = 0;
      while (heap.size() > 1) {
         if(method == 3 && !heap.get(1).getRequested()) {
            method = 4;
            buildHeap();
            method = 3;
         } 
         buildHeap();
         if (method == 3) {
            int timeToTakeoff = (int)Math.ceil((float)(heap.get(1).getNumPassengers()/2)/60);
            if(lastTimeInMin > heap.get(1).timeInMinutes()) {
               heap.get(1).changeTime(lastTime);
            }
            heap.get(1).addTime(timeToTakeoff);
            for(int i = 1; i < heap.size(); i++) {
               if(heap.get(i).timeInMinutes() <= heap.get(1).timeInMinutes() && heap.get(i).getRequested() == false) {
                  heap.get(i).changeRequestToTrue();
               }
            }
         }
         heap.get(1).printFlight();
         lastTime = heap.get(1).getTime();
         lastTimeInMin = heap.get(1).timeInMinutes();
         removeRoot();
      }
   }
   
   public static void readFile(String fileName) throws IOException {
      BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
      int inputPrio = Integer.MAX_VALUE;
      String currLine;
      while((currLine = fileReader.readLine()) != null) {
         String[] words = currLine.split(" ");
         Airplane currPlane = new Airplane(words[0], words[1], words[2], words[3], Integer.parseInt(words[4]), inputPrio);
         heap.add(currPlane);
         inputPrio--;
      }
   }
   public static void swap(int index1, int index2) {
      Airplane temp = heap.get(index2);
      heap.set(index2, heap.get(index1));
      heap.set(index1, temp);
   }
   public static int exists(int index) {
      if (index < heap.size()) {
         return index;
      }
      return 0;
   }
   
   public static void heapify(int index) {
      //System.out.println(index);
      int leftChild = getLeftChild(index);
      int rightChild = getRightChild(index);
      int leftChildValue = 0;
      int rightChildValue = 0;
      int maxValue = 0;
      
//this changes depending on mode
      //try doing this via index position somehow...
      if(method == 1) {
         leftChildValue = heap.get(exists(leftChild)).getInputPrio();
         rightChildValue = heap.get(exists(rightChild)).getInputPrio();
         maxValue = heap.get(index).getInputPrio();
      } else if (method == 2 || method == 3) {
         leftChildValue = heap.get(exists(leftChild)).getNumPassengers();
         rightChildValue = heap.get(exists(rightChild)).getNumPassengers();
         maxValue = heap.get(index).getNumPassengers();
      } else if(method == 4) {
         leftChildValue = -heap.get(exists(leftChild)).timeInMinutes();
         rightChildValue = -heap.get(exists(rightChild)).timeInMinutes();
         maxValue = -heap.get(index).timeInMinutes();
      }
      if(method == 3) {
         if(!heap.get(exists(leftChild)).getRequested()) {
            leftChildValue = 0;
         } 
         if(!heap.get(exists(rightChild)).getRequested()) {
            rightChildValue = 0;
         }
         if(!heap.get(index).getRequested()) {
            maxValue = 0;
         }
      }
//***
      //System.out.println(heap.get(index).timeInMinutes());
      //System.out.println(maxValue);
      int maxIndex = index;
      if (leftChildValue > maxValue) {
         maxValue = leftChildValue;
         maxIndex = leftChild;
      } 
      if (rightChildValue > maxValue) {
         maxValue = rightChildValue;
         maxIndex = rightChild;
      }
      //System.out.println("maxIndex " + maxIndex);
      //System.out.println("maxValue " + maxValue);
      if(heap.get(index) != heap.get(maxIndex)) {
         swap(index, maxIndex);
         heapify(maxIndex);
      }
   }
   public static void buildHeap() {
      for(int i = (int)Math.floor(((double)heap.size() - 1)/2); i >= 1; i--) {
         heapify(i);
      }
   }
   
   public static void removeRoot() {
      heap.set(1, heap.get(heap.size()-1));
      heap.remove(heap.size()-1);
      if(heap.size() > 1) {
         heapify(1);
      }
   }
   public static void insert(Airplane plane) {
      heap.add(plane);
      int pos = heap.size() - 1;
      while(pos > 1) {
         int parent = getParent(pos);
         int currValue = heap.get(pos).getNumPassengers();
         int parentValue = heap.get(parent).getNumPassengers();
         //Move up and swaps if value > then parrent
         if(currValue > parentValue) {
            swap(parent, pos);
            pos = getParent(pos);
         //Uses as a way to break loop if it is a max-heap
         } else {
            pos = 1;
         }
      }
   }
   
   public static void remove(Airplane plane) {
   
   }
   //Uses given position to calculate and return position of parent
   public static int getParent(int pos) { 
      return (int)Math.floor(((double)pos)/2);
   }
   //Uses give position to calculate and return the positions of the left child
   public static int getLeftChild(int pos) { 
      return (int)Math.floor(((double)pos)*2);
   }
   //Uses give position to calculate and return the positions of the right child
   public static int getRightChild(int pos) { 
      return (int)Math.floor(((double)pos)*2+1);
   }
   //returns boolean if given position is pointing to a leaf
   public static boolean isLeaf(int pos) { 
      if(pos * 2 > heap.size()) {
         return true;
      } 
      return false;
   }
}
