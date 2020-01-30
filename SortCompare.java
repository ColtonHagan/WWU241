/*
Author: Colton hagan
Class: CS 241
Date: 1/26/20
Purpose: Generate a area of n size of numbers between -n and n where n is given by user input
         the try out merge, insertion, quick, and or radix sort on the aformentioned array.
*/

import java.io.*; 
import java.util.*;
import java.lang.*;
public class  SortCompare {
	public static void main(String[] args) {
      /* Gets user input for size then creates an array of that size
         with random numbers (negetive and positive of that size */
      Scanner user_input = new Scanner(System.in);		
      System.out.println("Input Params");
      System.out.println("============");
      System.out.print("How many entries? ");
      int size = user_input.nextInt();
      int[] unsorted = new int[size];
      for (int i = 0; i < size; i++) {
         unsorted[i] = (int)(Math.random() * ((size * 2) + 1)) + - size;
      }
      //Takes user input of type of sort and then runs that sort
      System.out.print("Which sort [m,i,q,r,all]? ");
      String sort_type = user_input.next();
      System.out.println("");
      if(sort_type.equals("m")) {
         mergeSort(unsorted);
      } else if (sort_type.equals("q")) {
         quickSort(unsorted);
      } else if (sort_type.equals("i")) {
         insertionSort(unsorted);
      } else if (sort_type.equals("r")) {
         radixSort(unsorted);
      } else {
         mergeSort(unsorted.clone());
         quickSort(unsorted.clone());
         insertionSort(unsorted.clone());
         radixSort(unsorted);
      } 
	}
   /* Takes in an array and then passes it on to merge sort function with additional
       information and collects information you will need to print */
   public static void mergeSort(int[] array) {
      int[] comp = new int[1];
      int[] unsorted = array.clone();
      MergeHelper(array, 0, array.length-1, comp);
      printStats("merge", unsorted, comp[0], array);
   }
   
   /* Takes in an unsorted array. first int of that array, last int of that array
      and number of current comparisons that have been made . */
   public static void MergeHelper(int[] unsortedArray, int first, int last, int[] comp) {
      if (first < last) {
         int mid = (first+last)/2;
         MergeHelper(unsortedArray, first, mid, comp);
         MergeHelper(unsortedArray,mid+1,last, comp);
         Merge(unsortedArray,first,mid,last, comp);
      }
   }
   
   /* Takes in an unsorted array. first int of that array, last int of that array
      and number of current comparisons that have been made and then creates temporary arrays
      for each half of original array and then sorts the arrays */
   public static void Merge(int [] unsortedArray, int first, int mid, int last, int[] comp){
      //creates a temporary left and right array with last element being int's max value
      int leftSize = mid - first + 1;
      int rightSize = last - mid;
      int leftArray[] = new int [leftSize + 1]; 
      int rightArray[] = new int [rightSize + 1]; 
      for (int i = 0; i < leftSize; i++) {
         leftArray[i] = unsortedArray[first+i];
      }
      for (int i = 0; i < rightSize; i++) {
         rightArray[i] = unsortedArray[mid+1+i];
      }
      //uses ints max value in place of infinity
      leftArray[leftSize] = Integer.MAX_VALUE; 
      rightArray[rightSize] = Integer.MAX_VALUE;
      int i = 0;
      int j = 0;
      //sorts temporary arrays
      for (int k = first; k <= last; k++) {
         comp[0]++;
         if (leftArray[i] <= rightArray[j]) {
            unsortedArray[k] = leftArray[i];
            i++;
         } else {
            unsortedArray[k] = rightArray[j];
            j++;
         }
      }   
   }
   
   /* Takes in an array and then passes it on to quick sort function with additional
      information and collects information you will need to print */
   public static void quickSort(int[] array) {
      int[] comp = new int[1];
      int[] unsorted = array.clone();
      quickHelper(array, 0, array.length - 1, comp); 
      printStats("quick", unsorted, comp[0], array);
   }
   
   /* Takes in an unsorted array. first int of that array, last int of that array
      and number of current comparisons that have been made and breaks the array
      in parts to sort. */
   public static void quickHelper(int[] unsortedArray, int first, int last, int[] comp) {
      if(first < last) {
         int index = partition(unsortedArray,first,last, comp);
         quickHelper(unsortedArray,first, index - 1, comp);
         quickHelper(unsortedArray, index + 1, last, comp);
      }
   }
   
   /* Takes in an unsorted array. first int of that array, last int of that array
      and number of current comparisons that have been made and  then moves through
      the area swapping if current spot is less then the pivot*/
   public static int partition(int[] unsortedArray, int first, int last, int[] comp) {
        int pivot = unsortedArray[last];
        int temp;
        int i = (first-1);
        for (int j=first; j<last; j++) {
            //if current spot is smaller then pivot swap
            if (unsortedArray[j] < pivot) { 
                comp[0]++;
                i++; 
                temp = unsortedArray[j]; 
                unsortedArray[j] = unsortedArray[i]; 
                unsortedArray[i] = temp; 
            } 
        }
        //swap pivot
        temp = unsortedArray[last]; 
        unsortedArray[last] = unsortedArray[i+1]; 
        unsortedArray[i+1] = temp;
        return i+1;
   }
   
   // Takes in unsorted array and sorts it using insertion sort
   public static void insertionSort(int[] array) {
      int[] unsorted = array.clone();
      int len = array.length;
      int comp = 0;
      for(int i = 1; i < len; i++) {
         int j = i;;
         //swaps elements n with prevous element until all elements before n are sorted
         while ((j > 0) && (array[j-1] > array[j])) {
            int temp = array[j-1];
            array[j-1] = array[j];
            array[j] = temp;
            j--;
            comp++;
         }
      }
      printStats("insertion", unsorted, comp, array);
   }

   /* Takes in unsorted array and find how many digits the biggest number has
      and then runs counting sort from LSD to MSD */
   public static void radixSort(int[] array) {
      int[] unsorted = array.clone();
      int max = getMax(array);
      int maxDigits;
      //gets how many digits bigest number has
      for (maxDigits = 0; max > 0; maxDigits++) {
		   max = (int) (max/10);
		}
      //runs counting sort on digits   
      for (int digit = 1; digit <= maxDigits; digit++)
         countingSort(array, (int) Math.pow(digit,10));
      printStats("radix", unsorted, 0, array);
    }
    
    //takes in unsorted array and current digit and then sorts
    public static void countingSort(int[] unsortedArray, int digit) {
      int length = unsortedArray.length; 
      int[] count = new int[20];
      
      //count number of occurances of number
      for (int i = 0; i < length; i++) {
         count[((unsortedArray[i]/digit)%10)+10]++;
      }
      for (int i = 1; i < 20; i++) {
         count[i] += count[i - 1];
      }
      //create sorted array
      int[] sortedArray = new int[length];
      for (int i = length - 1; i >= 0; i--) {
         sortedArray[count[(unsortedArray[i]/digit)%10+10]-1] = unsortedArray[i];
         count[((unsortedArray[i]/digit)%10)+10]--;
      }
      
      //why doesnt this work --> unsortedArray = sortedArray.clone();??????
      System.arraycopy(sortedArray, 0, unsortedArray, 0, sortedArray.length);
   }
   
   //Prints out array
   //Note: I used this over Array.toString due to my prefence in appearance
   public static String arrayPrint(int[] array) {
      String output = "" + array[0];
      for(int i = 1; i < array.length; i++) {
         output = output + " " + array[i];
      }
      return output;
   }
   
   //Takes in a array and finds biggest number in it
   public static int getMax(int[] array) {
      int max = array[0];
      for (int i  = 1; i < array.length; i++) {
         if(array[i] > max) {
            max = array[i];
         }
      } 
      return max;
   }
   /* Takes name of sort, unsorted array (pre-sort), sorted array(post-sort),
      and number of comparisons name and prints them out to present to the user */
   public static void printStats(String name, int[] unsorted, int comp, int[] sorted) {
      System.out.println(name + " sort");
      System.out.println("============");
      if(unsorted.length < 20) {
         System.out.println("Unsorted array: " + arrayPrint(unsorted));
         System.out.println("Sorted array: " + arrayPrint(sorted));
      }
      System.out.println("Num Comparisons: " + comp);
      System.out.println("");
   }
   
}
      
