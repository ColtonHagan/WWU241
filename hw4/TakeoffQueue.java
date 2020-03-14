/*
Author: Colton Hagan
Class: CS 241
Date: 3/12/2020
Purpose: Takes in a file of airplan takeoffs and a method/protocal number ranging from 1 to 3
         reads file into a heap. Then orders the heap from max to min  based in the given method.
         If method equals1 then orders and prints takeoffs based on order given in file. 
         If method equals 2 then orders and prints takeoffs based on number of passenger on 
         flight. If method equals 3 then orders and prints takeoffs based on time request
         is made and number of passengers on flight.
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
public class TakeoffQueue {
   //ArrayList heap
   private static ArrayList<Airplane> heap;
   //Method used to sort
   private static int method;
   public static void main(String[] args) throws IOException { 
      method = Integer.parseInt(args[1]);
      heap = new ArrayList<Airplane>();
      //Filler value of index 0
      Airplane filler = new Airplane("filler", "filler", -1);
      heap.add(filler);
      readFile(args[0]);
      findAndPrintOrder();
  
   }
   
   /*Depending on the method/protocal orders the heap in the correct way
     and then prints max value repeating into nothing is left in the heap*/
   public static void findAndPrintOrder() {
      //String and minute value of the last time ran
      String lastTime = "";
      int lastTimeInMin = 0;
      //Runs while heap has values in it
      while (heap.size() > 1) {
         /*if there are no times that have been requested, finds next requested time
           this works on the assumption that requested times are not in order
           double check with teacher*/
         if(method == 3 && !heap.get(1).getRequested()) {
            /*Temperally cahnges the method to 4 to sort by time to 
            get the smallest time value to start on */
            method = 4;
            buildHeap();
            //Resets to finding method by number of passengers
            method = 3;
         } 
         //Builds heap unless method=1, then keeps original order
         if(method != 1) {
            buildHeap();
         }
         //If it is the third method calculates the time for it
         if (method == 3) {
            int timeToTakeoff = (int)Math.ceil(((double)heap.get(1).getNumPassengers()/2)/60);
            //Finds what time the plan is taking off (scheduled or time it is delayed to)
            if(lastTimeInMin > heap.get(1).getTimeInMinutes()) {
               heap.get(1).changeTime(lastTime);
            }
            heap.get(1).addTime(timeToTakeoff);
            
            //Notes all requested take off times that occur while current plane is taking off
            for(int i = 1; i < heap.size(); i++) {
               if(heap.get(i).getTimeInMinutes() <= heap.get(1).getTimeInMinutes() &&
                  !heap.get(i).getRequested()) {
                  heap.get(i).changeRequestToTrue();
               }
            }
         }
         heap.get(1).printFlight(method);
         lastTime = heap.get(1).getTime();
         lastTimeInMin = heap.get(1).getTimeInMinutes();
         //If it is the first method keeps order, else removes root and reorders
         if (method == 1) {
            heap.remove(1);
         } else {
            removeRoot();
         }
      }
   }
   //Takes in a fileName and reads that file into a heap
   public static void readFile(String fileName) throws IOException {
      BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
      //Is there a way with indexing to work out prio instead of using inputPrio
      String currLine;
      while((currLine = fileReader.readLine()) != null) {
         String[] words = currLine.split(" ");
         Airplane currPlane = new Airplane(words[0], words[3], Integer.parseInt(words[4]));
         heap.add(currPlane);
      }
   }
   //Takes in two indexes and swaps there place on the heap
   public static void swap(int index1, int index2) {
      Airplane temp = heap.get(index2);
      heap.set(index2, heap.get(index1));
      heap.set(index1, temp);
   }
   //Takes in index and checks if it is in the heap, returning 0 if it isnt
   public static int exists(int index) {
      if (index < heap.size() && index > -heap.size()) {
         return index;
      }
      return 0;
   }
   /*Takes in given index and currentValue and returns 0
     if the request Time has not been passed, else returns passed value*/
   public static int beenRequested (int index, int currentValue) {
      if(heap.get(index).getRequested()) {
         return currentValue;
      } 
      return 0;
   }
   /*Takes in an index of the heap and depending and the method/protocal called
     evaluates if the parrent is bigger or smaller then its child, 
     swaping and reruning if it is smaller*/
   public static void heapify(int index) {
      //left and right child index --> 0 if they don't exist
      int leftChild = exists(getLeftChild(index));
      int rightChild = exists(getRightChild(index));
      index = exists(index);
      int leftChildValue = 0;
      int rightChildValue = 0;
      int maxValue = 0;
      //If the method is 2/3 sort by number of passengers
      if (method == 2 || method == 3) {
         leftChildValue = heap.get(leftChild).getNumPassengers();
         rightChildValue = heap.get(rightChild).getNumPassengers();
         maxValue = heap.get(index).getNumPassengers();
         //If the method 3 it makes all values who requestime hasnt been called 0
         if(method == 3) {
               leftChildValue = beenRequested(leftChild,leftChildValue);
               rightChildValue = beenRequested(rightChild,rightChildValue);
               maxValue = beenRequested(index,maxValue);
         }
      //If it is method 4 sorts by time
      } else if(method == 4) {
         //Uses negetive values to make max heaping into min heaping
         leftChildValue = -heap.get(leftChild).getTimeInMinutes();
         rightChildValue = -heap.get(rightChild).getTimeInMinutes();
         maxValue = -heap.get(index).getTimeInMinutes();
      }
      //Finds which of the 3 values is biggest
      int maxIndex = index;
      if (rightChildValue > maxValue) {
         maxValue = rightChildValue;
         maxIndex = rightChild;
      }
      if (leftChildValue > maxValue) {
         maxValue = leftChildValue;
         maxIndex = leftChild;
      } 
      //if heap is smaller then one of its child values swap
      if(heap.get(index) != heap.get(maxIndex)) {
         swap(index, maxIndex);
         heapify(maxIndex);
      }
   }
   //Bulds heap so highest value is at top
   public static void buildHeap() {
      for(int i = (heap.size() - 1)/2; i >= 1; i--) {
         heapify(i);
      }
   }
   
   //Removes the root (max value) and then sorts to find next biggest valalue
   public static void removeRoot() {
      heap.set(1, heap.get(heap.size()-1));
      heap.remove(heap.size()-1);
      if(heap.size() > 1) {
         heapify(1);
      }
   }
   //Uses given index of heap to calculate and return position of parent
   public static int getParent(int index) { 
      return index/2;
   }
   //Uses give index of heap to calculate and return the positions of the left child
   public static int getLeftChild(int index) { 
      return index*2;
   }
   //Uses give index of heap to calculate and return the positions of the right child
   public static int getRightChild(int index) { 
      return index*2+1;
   }
}
